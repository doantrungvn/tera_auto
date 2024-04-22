package org.terasoluna.qp.domain.service.generatesourcecode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.FileUtilsQP;
import org.terasoluna.qp.app.processing.Handler;
import org.terasoluna.qp.app.processing.HandlerIo;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.GenerateSourceCode;
import org.terasoluna.qp.domain.model.Log;
import org.terasoluna.qp.domain.model.LogDetail;
import org.terasoluna.qp.domain.repository.loggingmanagement.LogDetailRepository;
import org.terasoluna.qp.domain.repository.loggingmanagement.LogPatternDetailRepository;
import org.terasoluna.qp.domain.repository.loggingmanagement.LogRepository;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component("LoggingGenerationHandler")
public class LoggingGenerationHandler implements Handler<HandlerIo>{

//	@SuppressWarnings("serial")
//	public static final Map<String,String> LAYOUTS = new HashMap<String,String>(){{
//		put("1","org.apache.log4j.helpers.DateLayout");
//		put("2","org.apache.log4j.HTMLLayout");
//		put("3","org.apache.log4j.PatternLayout");
//		put("4","org.apache.log4j.SimpleLayout");
//		put("5","org.apache.log4j.xml.XMLLayout");
//	}};
//	
//	@SuppressWarnings("serial")
//	public static final Map<String,String> LAYOUT_PATTERNS = new HashMap<String,String>(){{
//		put("1","DateFormat");
//		put("2","org.apache.log4j.HTMLLayout");
//		put("3","ConversionPattern");
//		put("4","org.apache.log4j.SimpleLayout");
//		put("5","org.apache.log4j.xml.XMLLayout");
//	}};
	@SuppressWarnings("serial")
	public static final Map<String,String> LAYOUTS = new HashMap<String,String>(){{
		put("1","ch.qos.logback.classic.log4j.XMLLayout");
		put("2","ch.qos.logback.classic.html.HTMLLayout");
		put("3","ConversionPattern");
	}};
	
	@SuppressWarnings("serial")
	public static final Map<String,String> LAYOUT_PATTERNS = new HashMap<String,String>(){{
		put("1","ch.qos.logback.classic.log4j.XMLLayout");
		put("2","ch.qos.logback.classic.html.HTMLLayout");
		put("3","ConversionPattern");
	}};

	
	@SuppressWarnings("serial")
	public static final Map<Integer,String> APPENDERS = new HashMap<Integer,String>(){{
		put(1,"ch.qos.logback.core.ConsoleAppender");
		put(2,"ch.qos.logback.core.FileAppender");
		put(3,"ch.qos.logback.core.rolling.RollingFileAppender");
		put(4,"ch.qos.logback.classic.db.DBAppender");
	}};
	
	@SuppressWarnings("serial")
	public static final Map<Integer,String> APPENDTYPES = new HashMap<Integer,String>(){{
		put(1,"True");
		put(0,"False");
	}};
	
	@SuppressWarnings("serial")
	public static final Map<Integer,String> LEVEL = new HashMap<Integer,String>(){{
		put(0,"TRACE");
		put(1,"DEBUG");
		put(2,"INFO");
		put(3,"WARN");
		put(4,"ERROR");
	}};
	
	@SuppressWarnings("serial")
	public static final Map<Integer,String> ROLLING_POLICY = new HashMap<Integer,String>(){{
		put(1,"ch.qos.logback.core.rolling.TimeBasedRollingPolicy");
		put(2,"ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy");
		put(3,"ch.qos.logback.core.rolling.FixedWindowRollingPolicy");
	}};
	
	@SuppressWarnings("serial")
	public static final Map<Integer,String> DB_DIALECT = new HashMap<Integer,String>(){{
		put(1,"ch.qos.logback.core.db.dialect.PostgreSQLDialect");
		put(2,"ch.qos.logback.core.db.dialect.OracleDialect");
	}};
	
	
	@SuppressWarnings("serial")
	public static final Map<Integer,String> TRIGGERING_POLICY = new HashMap<Integer,String>(){{
		put(1,"ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy");
	}};
	
	@Inject
	LogRepository logRepository;
	
	@Inject 
	LogDetailRepository logDetailRepository;
	
	@Inject
	LogPatternDetailRepository logPatternDetailRepository;
	
	@Inject
	SystemService systemService;
	
	private static final String TEMPLATE_LOGGING_MANAGEMENT_LOGBACK= "logging_management_logback_xml.ftl";
	private final String ELEMENT_APPENDER = "appender"; 
	private final String ELEMENT_LAYOUT = "layout"; 
	private final String ELEMENT_CONFIGURATION = "configuration"; 
	private final String ELEMENT_LOGGER = "logger"; 
	private final String ELEMENT_ROOT = "root";
	private final String ELEMENT_APPENDER_REF = "appender-ref";
	private final String ELEMENT_FILTER = "filter";
	private final String ELEMENT_LEVEL = "level";
	private final String ELEMENT_ONMATCH = "onMatch";
	private final String ELEMENT_TARGET = "target";
	private final String ELEMENT_ENCODER = "encoder";
	private final String ELEMENT_PATTERN = "pattern";
	private final String ELEMENT_CHARSET = "charset";
	private final String ELEMENT_FILE = "file";
	private final String ELEMENT_IMMEDIATE_FLUSH = "immediateFlush";
	private final String ELEMENT_APPEND = "append";
	private final String ELEMENT_ROLLING_POLICY = "rollingPolicy";
	private final String ELEMENT_TRIGGERING_POLICY = "triggeringPolicy";
	private final String ELEMENT_FILE_NAME_PATTERN = "fileNamePattern";
	private final String ATTRIBUTE_NAME = "name";
	private final String ATTRIBUTE_CLASS = "class";
	private final String ATTRIBUTE_REF = "ref";
	private final String ATTRIBUTE_LEVEL = "level";
	private final String ELEMENT_MAX_HISTORY= "maxHistory";
	private final String ELEMENT_MIN_INDEX= "minIndex";
	private final String ELEMENT_MAX_INDEX= "maxIndex";
	private final String ELEMENT_MAX_FILE_SIZE= "maxFileSize";
	private final String ELEMENT_CONNECTION_SOURCE= "connectionSource";
	private final String ELEMENT_DRIVER_CLASS= "driverClass";
	private final String ELEMENT_URL= "url";
	private final String ELEMENT_USER= "user";
	private final String ELEMENT_PASSWORD= "password";
	private final String ELEMENT_SQL_DIALECT= "sqlDialect";
	private final String ELEMENT_INSERT_HEADERS= "insertHeaders";
	private final String ELEMENT_INCLUDE_CALLER_DATA= "includeCallerData";
	
	private final String APPENDER_CONSOLE_NAME = "console";
	private final String APPENDER_FILE_NAME = "file";
	private final String APPENDER_DATABASE_NAME = "database";
	
	private final String THRESHOLDFILTER = "ch.qos.logback.classic.filter.ThresholdFilter";
	private final String LAYOUT_WRAPPING_ENCODER = "ch.qos.logback.core.encoder.LayoutWrappingEncoder";
	private final String LEVELFILTER = "ch.qos.logback.classic.filter.LevelFilter";
	
	private final String ACCEPT = "ACCEPT";
	
	@Override
	public void handle(HandlerIo handlePaream, CommonModel comon) {
		Assert.notNull(handlePaream);
		Assert.notNull(handlePaream.get("generateSourceCode"));
		Assert.notNull(handlePaream.get("logFilePath"));
		GenerateSourceCode generateSourceCode = (GenerateSourceCode) handlePaream.get("generateSourceCode");
		String logFilePath = FileUtilsQP.decodePath((String) handlePaream.get("logFilePath"));
		
		Long projectId = generateSourceCode.getProject().getProjectId();
		Log consoleLog = getLog(projectId,DbDomainConst.LogManagementType.LOG_TYPE_CONSOLE);
		Log fileLog = getLog(projectId, DbDomainConst.LogManagementType.LOG_TYPE_FILE);
		Log databaseLog = getLog(projectId, DbDomainConst.LogManagementType.LOG_TYPE_DB);
		
		if((consoleLog == null || consoleLog.getStatus() == 0) 
				&& (fileLog == null || fileLog.getStatus() == 0) 
				&& (databaseLog == null || databaseLog.getStatus() == 0)){
			return;
		}
		generateLog4j(logFilePath);
		
		/*XmlDocumentHelper documentHelper =XmlDocumentHelper.createNew(logFilePath);
		Document document = documentHelper.getDocument();*/
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		
		try {
			docBuilder = docFactory.newDocumentBuilder();
			Document document = docBuilder.parse(logFilePath);

			createAppender(consoleLog, document);
			createAppender(fileLog, document);
			createAppender(databaseLog, document);
			createRoot(document, consoleLog, fileLog, databaseLog);
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new FileOutputStream(logFilePath));
			transformer.transform(source, result);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}
	
	private void createRoot(Document document, Log consoleLog, Log fileLog, Log databaseLog){
		Node configurationNode = document.getElementsByTagName(ELEMENT_CONFIGURATION).item(0);
		Node loggerNode = document.getElementsByTagName(ELEMENT_LOGGER).item(0);
		
		Element appenderElement = null;
		if(!(databaseLog==null || databaseLog.getStatus() == 0 || CollectionUtils.isEmpty(databaseLog.getLstLogDetail()))){
			appenderElement = document.createElement(ELEMENT_APPENDER);
			appenderElement.setAttribute(ATTRIBUTE_NAME, "ASYNC");
			appenderElement.setAttribute(ATTRIBUTE_CLASS, "ch.qos.logback.classic.AsyncAppender");
			for(int i=0;i<databaseLog.getLstLogDetail().size();i++){
				if (databaseLog.getLstLogDetail().get(i) != null){
					Element appenderRefDatabaseElement = ((Element)document.createElement(ELEMENT_APPENDER_REF));
					appenderRefDatabaseElement.setAttribute(ATTRIBUTE_REF, APPENDER_DATABASE_NAME + i);
					appenderElement.appendChild(appenderRefDatabaseElement);
				}
			}
			Element includeCallerData = document.createElement(ELEMENT_INCLUDE_CALLER_DATA);
			includeCallerData.setTextContent("true");
			appenderElement.appendChild(includeCallerData);
			configurationNode.insertBefore(appenderElement,loggerNode);
		}
		
		
		Element rootElement = document.createElement(ELEMENT_ROOT);
		rootElement.setAttribute(ATTRIBUTE_LEVEL, LEVEL.get(0));
		
		if(!(consoleLog==null || consoleLog.getStatus() == 0 || CollectionUtils.isEmpty(consoleLog.getLstLogDetail()))){
			if (consoleLog.getLstLogDetail().get(0) != null){
				Element appenderRefConsoleElement = ((Element)document.createElement(ELEMENT_APPENDER_REF));
				appenderRefConsoleElement.setAttribute(ATTRIBUTE_REF, APPENDER_CONSOLE_NAME);
				rootElement.appendChild(appenderRefConsoleElement);
			}
		}
		
		if(!(fileLog==null || fileLog.getStatus() == 0||CollectionUtils.isEmpty(fileLog.getLstLogDetail()))){
			for(int i=0;i<fileLog.getLstLogDetail().size();i++){
				if (fileLog.getLstLogDetail().get(i) != null){
					Element appenderRefFileElement = ((Element)document.createElement(ELEMENT_APPENDER_REF));
					appenderRefFileElement.setAttribute(ATTRIBUTE_REF, APPENDER_FILE_NAME + i);
					rootElement.appendChild(appenderRefFileElement);
				}
			}
		}
		
		if (appenderElement != null){
			Element appenderRefFileElement = ((Element)document.createElement(ELEMENT_APPENDER_REF));
			appenderRefFileElement.setAttribute(ATTRIBUTE_REF, "ASYNC");
			rootElement.appendChild(appenderRefFileElement);
		}
		
		
		configurationNode.appendChild(rootElement);
	}
	
	private void generateLog4j (String logFilePath) {
		Writer sqlMapWriter = null;
		Map<String, Object> data = new HashMap<String, Object>();
		//data.put("isNormalMode", isNormalMode);
		Template tempLog4j = null;
		Configuration freemarkerConfiguration = systemService.createDefaultFreemarkerConfiguration();
		try {
			tempLog4j =  freemarkerConfiguration.getTemplate(TEMPLATE_LOGGING_MANAGEMENT_LOGBACK);
			File file = new File(logFilePath);
			file.getParentFile().mkdirs();
			sqlMapWriter = new FileWriter(file);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(sqlMapWriter != null){
			try {	
				tempLog4j.process(data, sqlMapWriter);
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				IOUtils.closeQuietly(sqlMapWriter);
			}
		}
	}

	private void createAppender(Log log, Document document) {
		Node configurationNode = document.getElementsByTagName(ELEMENT_CONFIGURATION).item(0);
		Node loggerNode = document.getElementsByTagName(ELEMENT_LOGGER).item(0);
		Element appenderElement = null;
		LogDetail logDetail = null;
		if(!(log==null || log.getStatus() == 0 || CollectionUtils.isEmpty(log.getLstLogDetail()))){
			switch(log.getLogType()){
			case DbDomainConst.LogManagementType.LOG_TYPE_CONSOLE:
				logDetail = log.getLstLogDetail().get(0);
				if(logDetail!=null){
					appenderElement = document.createElement(ELEMENT_APPENDER);
					appenderElement.setAttribute(ATTRIBUTE_NAME, APPENDER_CONSOLE_NAME);
					appenderElement.setAttribute(ATTRIBUTE_CLASS, APPENDERS.get(1));
					
					Element levelFilterElement = ((Element)document.createElement(ELEMENT_FILTER));
					levelFilterElement.setAttribute(ATTRIBUTE_CLASS, LEVELFILTER);
					Element element = ((Element)document.createElement(ELEMENT_LEVEL));
					element.setTextContent(LEVEL.get(0));
					levelFilterElement.appendChild(element);
					element = ((Element)document.createElement(ELEMENT_ONMATCH));
					element.setTextContent(ACCEPT);
					levelFilterElement.appendChild(element);
//					element = ((Element)document.createElement(ELEMENT_ONMISMATCH));
//					element.setTextContent(DENY);
//					levelFilterElement.appendChild(element);
					appenderElement.appendChild(levelFilterElement);
					
					Element filterElement = ((Element)document.createElement(ELEMENT_FILTER));
					filterElement.setAttribute(ATTRIBUTE_CLASS, THRESHOLDFILTER);
					Element levelElement = ((Element)document.createElement(ELEMENT_LEVEL));
					levelElement.setTextContent(LEVEL.get(logDetail.getLogLevel()));
					filterElement.appendChild(levelElement);
					appenderElement.appendChild(filterElement);
					
					if(StringUtils.isNoneBlank(logDetail.getLogTarget())){
						Element targetElement = ((Element)document.createElement(ELEMENT_TARGET));
						targetElement.setTextContent(logDetail.getLogTarget());
						appenderElement.appendChild(targetElement);
					}
					
					//if(StringUtils.isNoneBlank(logDetail.getLayout())){
						//Element encoderElement = ((Element)document.createElement(ELEMENT_ENCODER));
						//encoderElement.setAttribute(ATTRIBUTE_CLASS, LAYOUTS.get(logDetail.getLayout()));
						/*if(DataTypeUtils.equal(logDetail.getLayout(),"3")){
							Element patternParamElement = ((Element)document.createElement(ELEMENT_PARAM));
							patternParamElement.setAttribute(ATTRIBUTE_NAME, LAYOUT_PATTERNS.get(logDetail.getLayout()));
							patternParamElement.setAttribute(ATTRIBUTE_VALUE, logDetail.getConversionPattern());
							layoutElement.appendChild(patternParamElement);
						}*/
						//appenderElement.appendChild(encoderElement);
					//}
					if (StringUtils.isNoneBlank(logDetail.getConversionPattern())){
						Element encoderElement = ((Element)document.createElement(ELEMENT_ENCODER));
						
						if (StringUtils.isNoneBlank(logDetail.getCharset())){
							Element charsetElement = ((Element)document.createElement(ELEMENT_CHARSET));
							charsetElement.setTextContent(logDetail.getCharset());
							encoderElement.appendChild(charsetElement);
						}
						
						Element patternElement = ((Element)document.createElement(ELEMENT_PATTERN));
						CDATASection cdata = document.createCDATASection(logDetail.getConversionPattern());
						patternElement.appendChild(cdata);
						encoderElement.appendChild(patternElement);
						appenderElement.appendChild(encoderElement);
					}
					
					configurationNode.insertBefore(appenderElement,loggerNode);
				}
				break;
			case DbDomainConst.LogManagementType.LOG_TYPE_FILE:
				for(int i=0;i<log.getLstLogDetail().size();i++){
					logDetail = log.getLstLogDetail().get(i);
					if(logDetail!=null){
						appenderElement = ((Element)document.createElement(ELEMENT_APPENDER));
						appenderElement.setAttribute(ATTRIBUTE_NAME, APPENDER_FILE_NAME + i);
						appenderElement.setAttribute(ATTRIBUTE_CLASS, APPENDERS.get(logDetail.getLogFileType()+1));
						
						Element encoderElement = ((Element)document.createElement(ELEMENT_ENCODER));
						
						Element levelFilterElement = ((Element)document.createElement(ELEMENT_FILTER));
						levelFilterElement.setAttribute(ATTRIBUTE_CLASS, LEVELFILTER);
						Element element = ((Element)document.createElement(ELEMENT_LEVEL));
						element.setTextContent(LEVEL.get(0));
						levelFilterElement.appendChild(element);
						element = ((Element)document.createElement(ELEMENT_ONMATCH));
						element.setTextContent(ACCEPT);
						levelFilterElement.appendChild(element);
//						element = ((Element)document.createElement(ELEMENT_ONMISMATCH));
//						element.setTextContent(DENY);
//						levelFilterElement.appendChild(element);
						appenderElement.appendChild(levelFilterElement);
						
						Element filterElement = ((Element)document.createElement(ELEMENT_FILTER));
						filterElement.setAttribute(ATTRIBUTE_CLASS, THRESHOLDFILTER);
						Element levelElement = ((Element)document.createElement(ELEMENT_LEVEL));
						levelElement.setTextContent(LEVEL.get(logDetail.getLogLevel()));
						filterElement.appendChild(levelElement);
						appenderElement.appendChild(filterElement);
						
						if(StringUtils.isNotBlank(logDetail.getFilePath())){
							Element fileElement = ((Element)document.createElement(ELEMENT_FILE));
							fileElement.setTextContent(logDetail.getFilePath());
							appenderElement.appendChild(fileElement);
						}
						
						if (StringUtils.isNoneBlank(logDetail.getCharset())){
							Element charsetElement = ((Element)document.createElement(ELEMENT_CHARSET));
							charsetElement.setTextContent(logDetail.getCharset());
							encoderElement.appendChild(charsetElement);
						}
						
						if(logDetail.getImmediateFlush()>-1 && logDetail.getImmediateFlush()<2){
							Element immediateFlushElement = ((Element)document.createElement(ELEMENT_IMMEDIATE_FLUSH));
							immediateFlushElement.setTextContent(APPENDTYPES.get(logDetail.getImmediateFlush()));
							encoderElement.appendChild(immediateFlushElement);
						}
						
						if(logDetail.getAppendType()>-1 && logDetail.getAppendType()<2){
							Element appendElement = ((Element)document.createElement(ELEMENT_APPEND));
							appendElement.setTextContent(APPENDTYPES.get(logDetail.getAppendType()));
							appenderElement.appendChild(appendElement);
						}
						
						if (logDetail.getLogFileType() == 2){
							Element rollingPolicyElement = ((Element)document.createElement(ELEMENT_ROLLING_POLICY));
							rollingPolicyElement.setAttribute(ATTRIBUTE_CLASS, ROLLING_POLICY.get(Integer.parseInt(logDetail.getRollingPolicy())));
							
							if(StringUtils.isNotBlank(logDetail.getPatternFileName())){
								Element fileNamePatternElement = ((Element)document.createElement(ELEMENT_FILE_NAME_PATTERN));
								fileNamePatternElement.setTextContent(logDetail.getPatternFileName());
								rollingPolicyElement.appendChild(fileNamePatternElement);
							}
							
							if ("1".equals(logDetail.getRollingPolicy()) || "2".equals(logDetail.getRollingPolicy())){
								if (logDetail.getMaxHistory() != null && logDetail.getMaxHistory()  > 0){
									Element maxHistoryElement = ((Element)document.createElement(ELEMENT_MAX_HISTORY));
									maxHistoryElement.setTextContent(logDetail.getMaxHistory().toString());
									rollingPolicyElement.appendChild(maxHistoryElement);
								}
								
								//Current logback version not support
//								if (logDetail.getTotalSizeCap() != null && logDetail.getTotalSizeCap()  > 0){
//									Element totalSizeCap = ((Element)document.createElement(ELEMENT_TOTAL_SIZE_CAP));
//									totalSizeCap.setTextContent(logDetail.getTotalSizeCap().toString());
//									rollingPolicyElement.appendChild(totalSizeCap);
//								}
							} else if ("3".equals(logDetail.getRollingPolicy())){
								if (logDetail.getMinIndex() != null && logDetail.getMinIndex()  > 0){
									Element maxHistoryElement = ((Element)document.createElement(ELEMENT_MIN_INDEX));
									maxHistoryElement.setTextContent(logDetail.getMinIndex().toString());
									rollingPolicyElement.appendChild(maxHistoryElement);
								}
								
								if (logDetail.getMaxIndex() != null && logDetail.getMaxIndex()  > 0){
									Element totalSizeCap = ((Element)document.createElement(ELEMENT_MAX_INDEX));
									totalSizeCap.setTextContent(logDetail.getMaxIndex().toString());
									rollingPolicyElement.appendChild(totalSizeCap);
								}
							}
							appenderElement.appendChild(rollingPolicyElement);
						}
						
						if ("1".equals(logDetail.getTriggeringPolicy())){
							Element triggeringPolicyElement = ((Element)document.createElement(ELEMENT_TRIGGERING_POLICY));
							triggeringPolicyElement.setAttribute(ATTRIBUTE_CLASS, TRIGGERING_POLICY.get(Integer.parseInt(logDetail.getTriggeringPolicy())));
							
							if(StringUtils.isNotBlank(logDetail.getMaxFileSize())){
								Element maxFileSizeElement = ((Element)document.createElement(ELEMENT_MAX_FILE_SIZE));
								maxFileSizeElement.setTextContent(logDetail.getMaxFileSize());
								triggeringPolicyElement.appendChild(maxFileSizeElement);
							}
							appenderElement.appendChild(triggeringPolicyElement);
						}
						
						if ("1".equals(logDetail.getLayout())){
							encoderElement.setAttribute(ATTRIBUTE_CLASS, LAYOUT_WRAPPING_ENCODER);
							Element layoutElement = ((Element)document.createElement(ELEMENT_LAYOUT));
							layoutElement.setAttribute(ATTRIBUTE_CLASS, LAYOUTS.get(logDetail.getLayout()));
							encoderElement.appendChild(layoutElement);
						} else if ("2".equals(logDetail.getLayout())){
							encoderElement.setAttribute(ATTRIBUTE_CLASS, LAYOUT_WRAPPING_ENCODER);
							Element layoutElement = ((Element)document.createElement(ELEMENT_LAYOUT));
							layoutElement.setAttribute(ATTRIBUTE_CLASS, LAYOUTS.get(logDetail.getLayout()));
							Element patternElement = ((Element)document.createElement(ELEMENT_PATTERN));
							patternElement.setTextContent(logDetail.getConversionPattern());
							layoutElement.appendChild(patternElement);
							encoderElement.appendChild(layoutElement);
						} else if ("3".equals(logDetail.getLayout())){
							Element patternElement = ((Element)document.createElement(ELEMENT_PATTERN));
							patternElement.setTextContent(logDetail.getConversionPattern());
							encoderElement.appendChild(patternElement);
						}
						appenderElement.appendChild(encoderElement);
						

						configurationNode.insertBefore(appenderElement,loggerNode);
					}
				}
				break;
			case DbDomainConst.LogManagementType.LOG_TYPE_DB:
				for(int i=0;i<log.getLstLogDetail().size();i++){
					logDetail = log.getLstLogDetail().get(i);
					if(logDetail!=null){
						appenderElement = ((Element)document.createElement(ELEMENT_APPENDER));
						appenderElement.setAttribute(ATTRIBUTE_NAME, APPENDER_DATABASE_NAME + i);
						appenderElement.setAttribute(ATTRIBUTE_CLASS, APPENDERS.get(4));
						
						Element levelFilterElement = ((Element)document.createElement(ELEMENT_FILTER));
						levelFilterElement.setAttribute(ATTRIBUTE_CLASS, LEVELFILTER);
						Element element = ((Element)document.createElement(ELEMENT_LEVEL));
						element.setTextContent(LEVEL.get(0));
						levelFilterElement.appendChild(element);
						element = ((Element)document.createElement(ELEMENT_ONMATCH));
						element.setTextContent(ACCEPT);
						levelFilterElement.appendChild(element);
//						element = ((Element)document.createElement(ELEMENT_ONMISMATCH));
//						element.setTextContent(DENY);
//						levelFilterElement.appendChild(element);
						appenderElement.appendChild(levelFilterElement);
						
						Element filterElement = ((Element)document.createElement(ELEMENT_FILTER));
						filterElement.setAttribute(ATTRIBUTE_CLASS, THRESHOLDFILTER);
						Element levelElement = ((Element)document.createElement(ELEMENT_LEVEL));
						levelElement.setTextContent(LEVEL.get(logDetail.getLogLevel()));
						filterElement.appendChild(levelElement);
						appenderElement.appendChild(filterElement);
						
						Element connectionSourceElement = ((Element)document.createElement(ELEMENT_CONNECTION_SOURCE));
						connectionSourceElement.setAttribute(ATTRIBUTE_CLASS, "ch.qos.logback.core.db.DriverManagerConnectionSource");
						
						if(StringUtils.isNotBlank(logDetail.getDbDriver())){
							Element driverClassElement = ((Element)document.createElement(ELEMENT_DRIVER_CLASS));
							driverClassElement.setTextContent(logDetail.getDbDriver());
							connectionSourceElement.appendChild(driverClassElement);
						}
						
						if(StringUtils.isNotBlank(logDetail.getDbUrl())){
							Element urlElement = ((Element)document.createElement(ELEMENT_URL));
							urlElement.setTextContent(logDetail.getDbUrl());
							connectionSourceElement.appendChild(urlElement);
						}
						
						if(StringUtils.isNotBlank(logDetail.getDbUserName())){
							Element userElement = ((Element)document.createElement(ELEMENT_USER));
							userElement.setTextContent(logDetail.getDbUserName());
							connectionSourceElement.appendChild(userElement);
						}
						
						if(StringUtils.isNotBlank(logDetail.getDbPassword())){
							Element passwordElement = ((Element)document.createElement(ELEMENT_PASSWORD));
							passwordElement.setTextContent(logDetail.getDbPassword());
							connectionSourceElement.appendChild(passwordElement);
						}
						
						appenderElement.appendChild(connectionSourceElement);
						
						if (logDetail.getDbType() != null){
							Element sqlDialectElement = ((Element)document.createElement(ELEMENT_SQL_DIALECT));
							if (logDetail.getDbType() == 1){
								sqlDialectElement.setAttribute(ATTRIBUTE_CLASS, DB_DIALECT.get(1));
							} else if (logDetail.getDbType() == 2){
								sqlDialectElement.setAttribute(ATTRIBUTE_CLASS, DB_DIALECT.get(2));
							}
							appenderElement.appendChild(sqlDialectElement);
						}
						Element insertHeaders = ((Element)document.createElement(ELEMENT_INSERT_HEADERS));
						insertHeaders.setTextContent(logDetail.getInsertHeaders() == 1 ? "true" : "false");
						appenderElement.appendChild(insertHeaders);
						
						configurationNode.insertBefore(appenderElement,loggerNode);
					}
				}
				break;
			}
		}
	}
	
	private Log getLog(Long projectId, Integer logType) {
		Log log = logRepository.findLogByTypeAndProjectId(projectId, logType);
		if (log != null) {
			log.setLstLogDetail(logDetailRepository.findLogDetailByLogId(log.getLogId()));
			if (log.getLstLogDetail() != null && log.getLstLogDetail().size() > 0) {
				for (LogDetail item : log.getLstLogDetail()) {
					item.setLstConversionPattern(logPatternDetailRepository.findAllConversionPatternByLogDetailId(item.getLogDetailId()));
				}
			}
		}
		return log;
	}
	
}
