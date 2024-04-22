package org.terasoluna.qp.app.common.ultils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.terasoluna.gfw.common.codelist.CodeList;
import org.terasoluna.gfw.common.codelist.SimpleMapCodeList;
import org.terasoluna.qp.domain.service.common.XmlUtils;

public class MessageExposingServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	/*public static String PATH_APP = "META-INF/spring/applicationContext.xml";
	public static ApplicationContext context = new ClassPathXmlApplicationContext(PATH_APP);*/
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		
		Locale locale = LocaleUtils.getLocale(request);
		
		Object msg = request.getServletContext().getAttribute(locale + "_jsMesssageSource");
		
		if (msg == null) {
			InitializableMessageSource msgSource = (InitializableMessageSource) ApplicationContextProvider.getApplicationContext().getBean("messageSource");
		
			Map<String, MessageFormat> mapMessage = new HashMap<String, MessageFormat>();
			Map<String, Map<String, MessageFormat>> mapMessages = msgSource.messages;
			
			if(mapMessages != null && !mapMessages.isEmpty()){
				mapMessage = mapMessages.get(locale + "_" + locale);
			} else {
				msgSource.initialize();
				mapMessages = msgSource.messages;
				mapMessage = mapMessages.get(locale + "_" + locale);
			}
			
			List<Map<String, Object>> msgs = new ArrayList<Map<String, Object>>();
			
			//Map<String, Object> msgsCommon = new HashMap<String, Object>();
			for(Entry<String, MessageFormat> entry : mapMessage.entrySet()) {
				String key = entry.getKey();
				MessageFormat value = entry.getValue();
				
				if (key.startsWith("inf.sys.") || key.startsWith("err.sys.") || key.startsWith("errors.") || key.startsWith("sc.sys.") || key.indexOf(".js.") != -1) {
					Map<String, Object> prop = new HashMap<String, Object>();
					prop.put("key", key);
					/*prop.put("value", value.toPattern().replaceAll("\"", "&quot;"));*/

					prop.put("value", XmlUtils.xmlEscapeText(value.toPattern()));
					msgs.add(prop);
				}
				
			}	
			request.getServletContext().setAttribute(locale + "_jsMesssageSource", msgs);
		}
		CodeList systemSetting = (CodeList) ApplicationContextProvider.getApplicationContext().getBean("CL_SYSTEM_SETTING");
		request.setAttribute("systemSetting", systemSetting.asMap());
		
		SimpleMapCodeList clDateFormat = (SimpleMapCodeList) ApplicationContextProvider.getApplicationContext().getBean("CL_DATE_FORMAT_SETTING");
		request.setAttribute("clDateFormat", clDateFormat.asMap());
		
		SimpleMapCodeList clDateTimeFormat = (SimpleMapCodeList) ApplicationContextProvider.getApplicationContext().getBean("CL_DATETIME_FORMAT_SETTING");
		request.setAttribute("clDateTimeFormat", clDateTimeFormat.asMap());

		SimpleMapCodeList clTimeFormat = (SimpleMapCodeList) ApplicationContextProvider.getApplicationContext().getBean("CL_TIME_FORMAT_SETTING");
		request.setAttribute("clTimeFormat", clTimeFormat.asMap());

		//DungNN - 20160711 - add pattern date of java
		try {
			request.setAttribute("javaTimeFormat", DateUtils.getPatternTime(SessionUtils.getCurrentAccountProfile().getTimeFormat()));
			request.setAttribute("javaDateTimeFormat", DateUtils.getPatternDateTime(SessionUtils.getCurrentAccountProfile().getDateTimeFormat()));
			request.setAttribute("javaDateFormat", DateUtils.getPatternDate(SessionUtils.getCurrentAccountProfile().getDateFormat()));
		} catch (Exception ex) {
			request.setAttribute("javaTimeFormat", DateUtils.getPatternTime(null));
			request.setAttribute("javaDateTimeFormat", DateUtils.getPatternDateTime(null));
			request.setAttribute("javaDateFormat", DateUtils.getPatternDate(null));
		}
		
		request.getRequestDispatcher("/jsMsgSource.jsp").forward(request, resp);
	}
}
