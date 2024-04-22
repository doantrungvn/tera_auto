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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.domain.service.common.XmlUtils;

@WebServlet(value = "/dbMsgSource.js")
public class DatabaseExposingServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/*
	 * public static String PATH_APP = "META-INF/spring/applicationContext.xml"; public static ApplicationContext context = new ClassPathXmlApplicationContext(PATH_APP);
	 */

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

		Locale locale = LocaleUtils.getLocale(request);
		String moduleName = request.getParameter("module");

		Object msg = request.getServletContext().getAttribute(moduleName + "_" + locale + "_dbMesssageSource");

		if (msg == null) {
			/* InitializableMessageSource msgSource = (InitializableMessageSource) context.getBean("messageSource"); */
			InitializableMessageSource msgSource = (InitializableMessageSource) ApplicationContextProvider.getApplicationContext().getBean("messageSource");

			Map<String, MessageFormat> mapMessage = new HashMap<String, MessageFormat>();
			Map<String, Map<String, MessageFormat>> mapMessages = msgSource.messages;

			if (mapMessages != null && !mapMessages.isEmpty()) {
				mapMessage = mapMessages.get(locale + "_" + locale);
			} else {
				msgSource.initialize();
				mapMessages = msgSource.messages;
				mapMessage = mapMessages.get(locale + "_" + locale);
			}

			List<Map<String, Object>> msgs = new ArrayList<Map<String, Object>>();

			// Map<String, Object> msgsCommon = new HashMap<String, Object>();
			for (Entry<String, MessageFormat> entry : mapMessage.entrySet()) {
				String key = entry.getKey();
				MessageFormat value = entry.getValue();
				// String prefix = "sc."+ moduleName;
				if (checkExistStringOfModule(key, moduleName)) {
					Map<String, Object> prop = new HashMap<String, Object>();
					prop.put("key", key);
					/* prop.put("value", value.toPattern().replaceAll("\"", "&quot;")); */
					prop.put("value", XmlUtils.xmlEscapeText(value.toPattern()));
					msgs.add(prop);
				}
			}
			request.getServletContext().setAttribute(moduleName + "_" + locale + "_dbMesssageSource", msgs);
		}
		request.getServletContext().setAttribute("moduleName", moduleName);
		request.getRequestDispatcher("/dbMsgSource.jsp").forward(request, resp);
	}

	private boolean checkExistStringOfModule(String key, String moduleName) {
		boolean bReturn = false;
		if (StringUtils.isBlank(moduleName)) {
			return false;
		}

		String[] modules = StringUtils.split(moduleName, "_");
		for (String module : modules) {
			if (StringUtils.contains(key, module)) {
				return true;
			}
		}

		return bReturn;
	}
}
