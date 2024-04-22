package org.terasoluna.qp.domain.service.generatesourcecode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.FileUtilsQP;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.WriteXmlUltilsQP;
import org.terasoluna.qp.app.common.ultils.ZipUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.processing.HandlerIo;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.GenerateSourceCode;
import org.terasoluna.qp.domain.model.LibraryManagement;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.model.WebServiceToken;
import org.terasoluna.qp.domain.repository.generatesourcecode.GenerateSourceCodeRepository;
import org.terasoluna.qp.domain.repository.librarymanagement.LibraryManagementRepository;
import org.terasoluna.qp.domain.repository.loggingmanagement.LogRepository;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignRepository;
import org.terasoluna.qp.domain.repository.webservicetoken.WebServiceTokenRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;
import org.terasoluna.qp.domain.service.menudesign.MenuDesignService;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignService;

@Component(value="GenerateStructProjectHandler")
public class GenerateStructProjectHandler extends GenerationHandler {

	private static final String AUTHEN = "/authen";
	private static final String NOT_AUTHEN = "/notauthen";
	private static final String CURRENT_PACKAGE_NAME = "\\$\\{org.terasoluna.qp\\}";
	private static final String CURRENT_GROUP_ID = "\\$\\{terasoluna.qp\\}";

	private static final String PROJECT_TITLE_KEY = "\\$\\{projTitleKey\\}";

	private static final String COMPARE_TEXT = "\\<!-- appendHere \\-->";

	private static final String WS_AUTHEN_APPEND = "\\<!-- wsAuthenAppendHere \\-->";
	private static final String WS_NOT_AUTHEN_APPEND = "\\<!-- wsNotAuthenAppendHere \\-->";

	private static final String APOSTROPHE = "'";

	private static final String COMMENT_MODULE = "<!-- {0} -->";
	private static final String PERMISSION_RULE = "<sec:intercept-url pattern=\"/{0}/{1}*\" access=\"hasRole({2})\" />";

	private static final String DATABASE_TYPE = "database={0}";
	private static final String DATABASE_URL_POSTGRESQL = "database.url=jdbc:postgresql://{0}:{1}/{2}";
	private static final String DATABASE_URL_ORACLE = "database.url=jdbc:oracle:thin:@{0}:{1}:{2}";
	private static final String DATABASE_USERNAME = "database.username={0}";
	private static final String DATABASE_PASSWORD = "database.password={0}";
	private static final String DATABASE_DRIVERCLASSNAME = "database.driverClassName={0}";
	private static final String CONFIG_DB_REPLACE = "\\$configDB";

	private static final String SOURCE_DEAULT_PATH = "META-INF/template/source_default/%s" + FileUtilsQP.FileType.ZIP;
	private static final String SOURCE_PATH = "META-INF/template/source/";

	// KhangTM:
	private static final String WS_URL_PATTERN_AUTHEN = "\\$\\{url-pattern/authen\\}";
	private static final String WS_URL_PATTERN_NOT_AUTHEN = "\\$\\{url-pattern/notauthen\\}";
	private static final String WS_CLIENT_ID = "\\$\\{clientId\\}";
	private static final String WS_CLIENT_SECRET = "\\$\\{clientSecret\\}";
	private static final String WS_PERMISSION_RULE = "<intercept-url pattern=\"/{0}/{1}/{2}\" access=\"ROLE_APP\" />";
	// private static final String WS_PERMISSION_ROLE = "ROLE_APP";
	private static final String WS_COMPARE_TEXT = "\\<!-- appendWSAuthentication \\-->";
	private static final String WS_SERVLET_COMPARE_TEXT = "\\<!-- appendWSServlet \\-->";
	private static final String WS_INTERCEPT = "\\<!-- appendWSIntercept \\-->";

	private static final String TEMPLATE_BAT_SCRIPT = "install_custom_lib_cmd.ftl";
	//private static final String TEMPLATE_SH_SCRIPT = "install_custom_lib_sh.ftl";

	private ClassLoader classLoader = getClass().getClassLoader();

	@Inject
	ScreenDesignService screenDesignService;

	@Inject
	MenuDesignService menuDesignService;

	@Inject
	ScreenDesignRepository screenDesignRepository;

	@Inject
	LibraryManagementRepository libraryManagementRepository;

	@Inject
	LogRepository logRepostory;

	@Inject
	WebServiceTokenRepository webServiceRepository;

	@Inject
	GenerateSourceCodeRepository generateSourceCodeRepository;

	@Inject
	@Named("LoggingGenerationHandler")
	LoggingGenerationHandler loggingGenerationHandler;

	private String rootDir = "";
	private boolean generateStructure = true;

	private String pathPackage;
	
	private Integer dbType;

	public String getRootDir() {
		return rootDir;
	}

	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}

	/**
	 * @param generateSourceCode
	 */
	
	@Override
	public void handle(GenerateSourceCode generateSourceCode, CommonModel common) {

		try {
			rootDir = FileUtilsQP.decodePath(generateSourceCode.getSourcePath());
			this.generateStructure = generateSourceCode.getGenAll();

			generateSourceCode.setSourcePath(rootDir);
			generateSourceCode.setLogFile(new File(FileUtilsQP.normalizedPath(rootDir) + GenerateSourceCodeConst.LOG_FOLDER, "generate.log"));
			Project p = generateSourceCode.getProject();
			dbType = p.getDbType();
			
			processGenerateRoot(generateSourceCode, common);
			processGenerateRootWeb(generateSourceCode, common);
			processGenerateRootDomain(generateSourceCode, common);
			processGenerateRootEnv(generateSourceCode, common);
			processGenerateRootBatch(generateSourceCode, common);

			this.replacePackageNameFile(rootDir, generateSourceCode, p.getProjectName(), p.getProjectCode(), CURRENT_GROUP_ID);
		} catch (Exception e) {
			GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
		}
	}

	/*
	 * get template by db type
	 */
	private String getResourceByDbType(Integer dbType) {
		if (DbDomainConst.DatabaseType.ORACLE.equals(dbType)) {
			return "_oracle";
		}
		return StringUtils.EMPTY;
	}

	/**
	 * @param generateSourceCode
	 * @param common
	 * @throws Exception
	 */
	private void processGenerateRootWeb(GenerateSourceCode generateSourceCode, CommonModel common) {
		if (generateSourceCode != null) {
			if (StringUtils.isNotBlank(rootDir)) {
				try {

					Project project = generateSourceCode.getProject();

					generateSourceCode.setSourcePathWeb(rootDir + project.getProjectCode() + "-web" + File.separator);
					/**
					 * If only generate path of project
					 */
					if (!generateStructure)
						return;

					FileUtilsQP.createDirectory(generateSourceCode.getSourcePathWeb());

					InputStream inputStream = classLoader.getResourceAsStream(String.format(SOURCE_DEAULT_PATH, "web" + getResourceByDbType(project.getDbType())));
					ZipUtils.extract(inputStream, generateSourceCode.getSourcePathWeb(), generateSourceCode.getTempFolder());
					IOUtils.closeQuietly(inputStream);

					// append folder java
					StringBuilder pathJava = new StringBuilder(generateSourceCode.getSourcePathWeb() + "src" + File.separator + "main" + File.separator + "java" + File.separator);
					String[] split = null;
					if (StringUtils.isNotBlank(generateSourceCode.getProject().getPackageName())) {
						split = generateSourceCode.getProject().getPackageName().split("\\.");
					}
					if (split != null && split.length > 0) {
						for (String str : split) {
							pathJava.append(str + File.separator);
						}
					} else {
						pathJava.append(generateSourceCode.getProject().getPackageName() + File.separator);
					}

					FileUtilsQP.createDirectory(pathJava.toString());

					String pathWebXml = generateSourceCode.getSourcePathWeb() + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "WEB-INF" + File.separator + "web.xml";

					// KhangTM: generate web.xml for web service
					File fwebXml = new File(pathWebXml);
					StringBuilder content = new StringBuilder(FileUtilsQP.readFileToString(fwebXml, StandardCharsets.UTF_8));
					StringBuilder contentReplace = new StringBuilder();
					if (StringUtils.isNoneBlank(content)) {
						if (Boolean.TRUE.equals(project.getWebserviceFlg())) {
							InputStream restAppServlet = classLoader.getResourceAsStream(SOURCE_PATH + "restAppServlet.ftl");

							String appenAuth = "classpath*:META-INF/spring/spring-security-rest.xml";
							StringBuilder contentXml = new StringBuilder();
							contentXml.append(IOUtils.toString(restAppServlet, StandardCharsets.UTF_8));
							String temp = contentXml.toString().replaceAll(WS_URL_PATTERN_AUTHEN, StringUtils.defaultIfBlank(generateSourceCode.getProject().getWebservicePattern() + AUTHEN, "api" + AUTHEN))
									.replaceAll(WS_URL_PATTERN_NOT_AUTHEN, StringUtils.defaultIfBlank(generateSourceCode.getProject().getWebservicePattern() + NOT_AUTHEN, "api" + NOT_AUTHEN));

							contentReplace.append(content.toString().replaceAll(WS_COMPARE_TEXT, appenAuth).replaceAll(WS_SERVLET_COMPARE_TEXT, temp));
						} else {

							contentReplace.append(content);
						}
						FileUtils.write(fwebXml, contentReplace, StandardCharsets.UTF_8);
					}

				} catch (Exception e) {
					GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
				}
			}
		}
	}

	private void processGenerateRoot(GenerateSourceCode generateSourceCode, CommonModel common) {
		if (generateSourceCode != null && generateStructure) {
			if (StringUtils.isNotBlank(rootDir)) {
				try {
					InputStream inputStream = classLoader.getResourceAsStream(String.format(SOURCE_DEAULT_PATH, "root" + getResourceByDbType(generateSourceCode.getProject().getDbType())));
					ZipUtils.extract(inputStream, rootDir, generateSourceCode.getTempFolder());
					IOUtils.closeQuietly(inputStream);
					if (generateSourceCode.getBatchModuleFlg()) {
						// add batch module to pom file
						String pomFile = FileUtilsQP.normalizedPath(rootDir) + "pom.xml";
						WriteXmlUltilsQP.addBatchModule(pomFile);
					}
				} catch (Exception e) {
					GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
				}
			}
		}
	}

	/**
	 * @param generateSourceCode
	 * @param common
	 * @throws Exception
	 */
	private void processGenerateRootDomain(GenerateSourceCode generateSourceCode, CommonModel common) {
		if (generateSourceCode != null) {
			if (StringUtils.isNotBlank(rootDir)) {

				try {
					Project project = generateSourceCode.getProject();
					String domainDir = rootDir + project.getProjectCode() + "-domain" + File.separator;
					generateSourceCode.setSourcePathDomain(domainDir);

					/**
					 * If only generate path of project
					 */
					if (!generateStructure)
						return;

					FileUtilsQP.createDirectory(generateSourceCode.getSourcePathDomain());

					InputStream inputStream = classLoader.getResourceAsStream(String.format(SOURCE_DEAULT_PATH, "domain" + getResourceByDbType(project.getDbType())));
					ZipUtils.extract(inputStream, generateSourceCode.getSourcePathDomain(), generateSourceCode.getTempFolder());
					IOUtils.closeQuietly(inputStream);

					// append folder java
					StringBuilder pathJava = new StringBuilder(generateSourceCode.getSourcePathDomain() + "src" + File.separator + "main" + File.separator + "java" + File.separator);
					String[] split = null;
					StringBuilder pathPackage = new StringBuilder();
					if (StringUtils.isNotBlank(project.getPackageName())) {
						split = project.getPackageName().split("\\.");
					}
					if (split != null && split.length > 0) {
						for (String str : split) {
							pathPackage.append(str).append(File.separator);
							pathJava.append(str + File.separator);
						}
					} else {
						pathJava.append(project.getPackageName() + File.separator);
					}
					this.setPathPackage(pathPackage.toString());
					FileUtilsQP.createDirectory(pathJava.toString());

					// add library to pom file
					String pomFile = domainDir + "pom.xml";
					 List<LibraryManagement> libraryList = libraryManagementRepository.findAllLibrary(project.getProjectId()); 
					/*List<LibraryManagement> libraryList = libraryManagementRepository.findAllLibraryWithFileContent(project.getProjectId());*/
					this.generateLibraryFiles(rootDir + GenerateSourceCodeConst.LIB_FOLDER, libraryList);

					if (CollectionUtils.isNotEmpty(libraryList)) {
						WriteXmlUltilsQP.processWritePomFile(pomFile, libraryList);
					}
				} catch (Exception e) {
					GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
				}
			}
		}
	}

	private void generateLibraryFiles(String libDir,List<LibraryManagement> libraryList) throws Exception {
		List<LibraryManagement> customLibs = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(libraryList)) {
			for(LibraryManagement libraryManagement : libraryList){
				try {
					if(DataTypeUtils.equals(libraryManagement.getSystemFlag(), "2")){
						FileUtils.writeByteArrayToFile(new File(libDir, libraryManagement.getFileName()), libraryManagement.getUploadFileContent());
						if (StringUtils.isEmpty(libraryManagement.getVersion())) {
							libraryManagement.setVersion("0.0.0");
						}
						
						if (StringUtils.isEmpty(libraryManagement.getType())) {
							libraryManagement.setType(StringUtils.substringAfterLast(libraryManagement.getUploadFileName(), DbDomainConst.CHARACTER_DOT));
						}
						customLibs.add(libraryManagement);
	
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					throw e;
				}
			}
		}
		//write shell script
		if (CollectionUtils.isNotEmpty(customLibs) || DataTypeUtils.equals(DbDomainConst.DatabaseType.ORACLE, dbType)) {
			
			
			if (SystemUtils.IS_OS_WINDOWS) {
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("customLibs", customLibs);
				data.put("dbType", dbType == null ? DbDomainConst.DatabaseType.PostgreSQL : dbType);
				data.put("libDir", FileUtilsQP.normalizedPath(libDir));
				
				this.process(data, TEMPLATE_BAT_SCRIPT, FileUtilsQP.normalizedPath(libDir) + GenerateSourceCodeConst.MAVEN_BAT_SCRIPT);
			} else {
				//this.process(data, TEMPLATE_SH_SCRIPT, FileUtilsQP.normalizedPath(libDir) + GenerateSourceCodeConst.MAVEN_SH_SCRIPT);
				/*DungNN1 - 20160706
				 * when used freemaker build sh file -> wrong some character*/

				StringBuilder shContent = new StringBuilder("#!/bin/bash");
				shContent.append(StringUtils.LF);
				shContent.append("echo \"PATH in install custom lib: $PATH\"");
				shContent.append(StringUtils.LF);
				shContent.append("called_path=${0%/*}");
				shContent.append(StringUtils.LF);
				shContent.append("real_path=${called_path#[^/]*}");
				
				shContent.append(StringUtils.LF);
				shContent.append("echo \"real_path: $real_path\"");
				shContent.append(StringUtils.LF);
				shContent.append("cd $real_path");
				shContent.append(StringUtils.LF);
				if(DataTypeUtils.equals(DbDomainConst.DatabaseType.ORACLE, dbType)) {
					shContent.append("mvn install:install-file -DgroupId=oracle -DartifactId=ojdbc6 -Dversion=11.2.0.2.0 -Dpackaging=jar -Dfile=ojdbc6-11.2.0.2.0.jar");
				}

				for (LibraryManagement lib : libraryList) {
					shContent.append(StringUtils.LF);
					shContent.append(String.format(GenerateSourceCodeConst.MAVEN_COMMAND, lib.getGroupId(), lib.getArtifactId(), lib.getVersion(), lib.getType(), lib.getFileName()));
				}

				shContent.append(StringUtils.LF);
				shContent.append("echo \"End of install custom lib\"");

				FileUtilsQP.writeStringToFile(new File(libDir, GenerateSourceCodeConst.MAVEN_SH_SCRIPT), shContent.toString());
			}
		}
	}

	/**
	 * 
	 * @param generateSourceCode
	 * @param common
	 * @throws Exception
	 */
	private void processGenerateRootEnv(GenerateSourceCode generateSourceCode, CommonModel common) {
		if (StringUtils.isNotBlank(rootDir) && generateStructure) {
			try {
				generateSourceCode.setSourcePathEnv(rootDir + generateSourceCode.getProject().getProjectCode() + "-env" + File.separator);
				FileUtilsQP.createDirectory(generateSourceCode.getSourcePathEnv());
				InputStream inputStream = classLoader.getResourceAsStream(String.format(SOURCE_DEAULT_PATH, "env"));
				ZipUtils.extract(inputStream, generateSourceCode.getSourcePathEnv(), generateSourceCode.getTempFolder());
				IOUtils.closeQuietly(inputStream);

				HandlerIo io = new HandlerIo();
				io.put("generateSourceCode", generateSourceCode);
				io.put("logFilePath", rootDir + generateSourceCode.getProject().getProjectCode() + "-env" + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "logback.xml");
				loggingGenerationHandler.handle(io, common);
			} catch (Exception e) {
				GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
			}
		}
	}

	/**
	 * 
	 * @param sourcePath
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private void replacePackageNameFile(String sourcePath, GenerateSourceCode generateSourceCode, String projectName, String projectCode, String patternProjectCode) throws FileNotFoundException, IOException {
		File folder = new File(sourcePath);
		File[] listOfFiles = folder.listFiles();
		Project project = generateSourceCode.getProject();
		if (ArrayUtils.isEmpty(listOfFiles)) {
			return;
		}

		StringBuilder content = new StringBuilder();

		StringBuilder contentReplace = new StringBuilder();

		for (int i = 0; i < listOfFiles.length; i++) {

			if (listOfFiles[i].isFile()) {
				if (listOfFiles[i].getName().equals("pom.xml")) {
					content.append(FileUtils.readFileToString(listOfFiles[i], StandardCharsets.UTF_8));
					if (StringUtils.isBlank(content)) {
						continue;
					}
					contentReplace.append(content.toString().replaceAll(patternProjectCode, projectCode));
					FileUtils.write(listOfFiles[i], contentReplace, StandardCharsets.UTF_8);

				} else if (listOfFiles[i].getName().equals("spring-security.xml")) {

					content.append(FileUtils.readFileToString(listOfFiles[i], StandardCharsets.UTF_8));
					if (StringUtils.isBlank(content)) {
						continue;
					}

					if (Boolean.TRUE.equals(project.getWebserviceFlg())) {
						String appenAuth = "<sec:authentication-provider user-service-ref=\"oauthUserDetailsService\" />";
						String wsAuthenAppend = "<sec:intercept-url pattern=\"/"+ project.getWebservicePattern() + AUTHEN + "/**\" access=\"permitAll\" />";
						String wsNotAuthenAppend = "<sec:intercept-url pattern=\"/"+ project.getWebservicePattern() + NOT_AUTHEN + "/**\" access=\"permitAll\" />";
						contentReplace.append(content.toString().replaceAll(COMPARE_TEXT, this.writeContentFileSpringSercurity(generateSourceCode)).replaceAll(WS_COMPARE_TEXT, appenAuth).replaceAll(WS_AUTHEN_APPEND, wsAuthenAppend).replaceAll(WS_NOT_AUTHEN_APPEND, wsNotAuthenAppend));
					} else {
						contentReplace.append(content.toString().replaceAll(COMPARE_TEXT, this.writeContentFileSpringSercurity(generateSourceCode)));
					}

					FileUtils.write(listOfFiles[i], contentReplace, StandardCharsets.UTF_8);

				} else if (listOfFiles[i].getName().equals("spring-mvc-rest.xml")) {

					content.append(FileUtils.readFileToString(listOfFiles[i], StandardCharsets.UTF_8));
					if (StringUtils.isBlank(content)) {
						continue;
					}
					contentReplace.append(content.toString().replaceAll(CURRENT_PACKAGE_NAME, generateSourceCode.getProject().getPackageName()));

					FileUtils.write(listOfFiles[i], contentReplace, StandardCharsets.UTF_8);

				} else if (listOfFiles[i].getName().equals("spring-security-rest.xml")) {

					if (Boolean.TRUE.equals(project.getWebserviceFlg())) {

						content.append(FileUtils.readFileToString(listOfFiles[i], StandardCharsets.UTF_8));

						InputStream springSecurityRest = classLoader.getResourceAsStream(SOURCE_PATH + "spring-security-rest.xml.ftl");

						WebServiceToken wsTokenExist = webServiceRepository.getWebServiceTokenByProjectId(project.getProjectId());

						if (wsTokenExist == null) {
							throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage("sc.webservicetokenmanagement.0010")));
						}

						String clientId = wsTokenExist.getClientId();
						String clientSecret = wsTokenExist.getClientSecret();

						StringBuilder contentXml = new StringBuilder();
						contentXml.append(IOUtils.toString(springSecurityRest, StandardCharsets.UTF_8));

						String temp = contentXml.toString().replaceAll(WS_URL_PATTERN_AUTHEN, generateSourceCode.getProject().getWebservicePattern() + AUTHEN).replaceAll(WS_CLIENT_ID, clientId).replaceAll(WS_CLIENT_SECRET, clientSecret).replaceAll(WS_INTERCEPT, this.writeContentFileSpringSercurityRest(generateSourceCode))
								.replaceAll(WS_URL_PATTERN_NOT_AUTHEN, generateSourceCode.getProject().getWebservicePattern() + NOT_AUTHEN);
						contentReplace.append(content.toString().replaceAll(WS_COMPARE_TEXT, temp));

						FileUtils.write(listOfFiles[i], contentReplace, StandardCharsets.UTF_8);
					}

				} else if (listOfFiles[i].getName().equals("terasoluna-qp-infra.properties")) {
					// ConfigDB file
					replaceEnvironmentFile(listOfFiles[i], generateSourceCode.getProject());
				} else if (listOfFiles[i].getName().equals("jdbc.properties")) {
					// Batch ConfigDB file
					replaceEnvironmentFile(listOfFiles[i], generateSourceCode.getProject());
				} else if (hasReplacementContent(listOfFiles[i])) {
					content.append(FileUtils.readFileToString(listOfFiles[i], StandardCharsets.UTF_8));
					if (StringUtils.isBlank(content)) {
						continue;
					}
					contentReplace.append(content.toString().replaceAll(CURRENT_PACKAGE_NAME, generateSourceCode.getProject().getPackageName()));

					FileUtils.write(listOfFiles[i], contentReplace.toString().replaceAll(PROJECT_TITLE_KEY, projectName), StandardCharsets.UTF_8);
				}
				content.delete(0, content.length());
				contentReplace.delete(0, contentReplace.length());
			} else if (listOfFiles[i].isDirectory()) {
				replacePackageNameFile(sourcePath + File.separator + listOfFiles[i].getName(), generateSourceCode, projectName, projectCode, patternProjectCode);
			}
		}
	}

	/**
	 * 
	 * @param sourcePath
	 * @param project
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void replaceEnvironmentFile(File fileToSave, Project project) throws FileNotFoundException, IOException {

		StringBuilder dbConfig = new StringBuilder();
		String content = FileUtils.readFileToString(fileToSave, StandardCharsets.UTF_8);

		if (DbDomainConst.DatabaseType.PostgreSQL.equals(project.getDbType())) {
			dbConfig.append(MessageFormat.format(DATABASE_TYPE, "POSTGRESQL"));
			dbConfig.append(StringUtils.LF);
			dbConfig.append(MessageFormat.format(DATABASE_URL_POSTGRESQL, project.getDbHostName(), project.getDbPort(), project.getDbName()));
			dbConfig.append(StringUtils.LF);
			dbConfig.append(MessageFormat.format(DATABASE_USERNAME, project.getDbUser()));
			dbConfig.append(StringUtils.LF);
			dbConfig.append(MessageFormat.format(DATABASE_PASSWORD, project.getDbPassword()));
			dbConfig.append(StringUtils.LF);
			dbConfig.append(MessageFormat.format(DATABASE_DRIVERCLASSNAME, project.getDbDriver()));
			content = content.replaceAll(CONFIG_DB_REPLACE, dbConfig.toString());
		} else if (DbDomainConst.DatabaseType.ORACLE.equals(project.getDbType())) {
			dbConfig.append(MessageFormat.format(DATABASE_TYPE, "ORACLE"));
			dbConfig.append(StringUtils.LF);
			dbConfig.append(MessageFormat.format(DATABASE_URL_ORACLE, project.getDbHostName(), project.getDbPort(), project.getDbName()));
			dbConfig.append(StringUtils.LF);
			dbConfig.append(MessageFormat.format(DATABASE_USERNAME, project.getDbUser()));
			dbConfig.append(StringUtils.LF);
			dbConfig.append(MessageFormat.format(DATABASE_PASSWORD, project.getDbPassword()));
			dbConfig.append(StringUtils.LF);
			dbConfig.append(MessageFormat.format(DATABASE_DRIVERCLASSNAME, project.getDbDriver()));
			content = content.replaceAll(CONFIG_DB_REPLACE, dbConfig.toString());
		}
		FileUtils.write(fileToSave, content, StandardCharsets.UTF_8);
	}

	/**
	 * 
	 * @param path
	 */
	private String writeContentFileSpringSercurity(GenerateSourceCode generateSourceCode) {
		List<ScreenDesign> lstScreenDesign = screenDesignRepository.getAllScreenOfProjectId(generateSourceCode.getProject().getProjectId());
		if (FunctionCommon.isEmpty(lstScreenDesign)) {
			return StringUtils.EMPTY;
		}
		StringBuilder stringBuilder = new StringBuilder();

		StringBuilder stringPermission = new StringBuilder();

		String currentModuleCode = StringUtils.EMPTY;

		for (ScreenDesign screenDesign : lstScreenDesign) {
			if (StringUtils.isNotBlank(screenDesign.getScreenUrlCode())) {

				if (!DataTypeUtils.equals(currentModuleCode, screenDesign.getModuleCode())) {
					currentModuleCode = screenDesign.getModuleCode();
					stringBuilder.append(MessageFormat.format(COMMENT_MODULE, currentModuleCode));
					stringBuilder.append(StringUtils.LF);
				}

				stringPermission.append(APOSTROPHE);
				stringPermission.append(currentModuleCode);
				stringPermission.append(StringUtils.capitalize(screenDesign.getScreenUrlCode()));
				stringPermission.append(APOSTROPHE);

				stringBuilder.append(MessageFormat.format(PERMISSION_RULE, GenerateSourceCodeUtil.normalizedURL(currentModuleCode), GenerateSourceCodeUtil.normalizedURL(screenDesign.getScreenUrlCode()), GenerateSourceCodeUtil.normalizedURL(stringPermission.toString())));
				stringBuilder.append(StringUtils.LF);

				stringPermission.delete(0, stringPermission.length());
			}
		}
		return stringBuilder.toString();
	}

	/**
	 * 
	 * @param path
	 */
	private String writeContentFileSpringSercurityRest(GenerateSourceCode generateSourceCode) {

		List<BusinessDesign> businessLogicLst = generateSourceCodeRepository.findAllBusinessLogicByModuleLst(generateSourceCode.getModules(), generateSourceCode.getProject().getProjectId(), BusinessDesignConst.MODULE_TYPE_ONLINE);

		if (FunctionCommon.isEmpty(businessLogicLst)) {
			return StringUtils.EMPTY;
		}
		StringBuilder stringBuilder = new StringBuilder();

		// StringBuilder stringPermission = new StringBuilder();

		String currentModuleCode = StringUtils.EMPTY;

		for (BusinessDesign businessLogic : businessLogicLst) {
			if (BusinessDesignConst.BLOGIC_TYPE_WEBSERVICE.equals(businessLogic.getBlogicType()) && businessLogic.getAuthenticatedFlg() == true) {

				if (!DataTypeUtils.equals(currentModuleCode, businessLogic.getModuleCode())) {
					currentModuleCode = businessLogic.getModuleCode();
					stringBuilder.append(MessageFormat.format(COMMENT_MODULE, currentModuleCode));
					stringBuilder.append(StringUtils.LF);
				}

				// stringPermission.append(APOSTROPHE);
				// stringPermission.append(currentModuleCode);
				// stringPermission.append(StringUtils.capitalize(WS_PERMISSION_ROLE));
				// stringPermission.append(APOSTROPHE);

				stringBuilder.append(MessageFormat.format(WS_PERMISSION_RULE, GenerateSourceCodeUtil.normalizedURL(generateSourceCode.getProject().getWebservicePattern() + AUTHEN), GenerateSourceCodeUtil.normalizedURL(currentModuleCode), GenerateSourceCodeUtil.normalizedURL(businessLogic.getBusinessLogicCode())));
				stringBuilder.append(StringUtils.LF);
				stringBuilder.append(MessageFormat.format(WS_PERMISSION_RULE, GenerateSourceCodeUtil.normalizedURL(generateSourceCode.getProject().getWebservicePattern() + NOT_AUTHEN), GenerateSourceCodeUtil.normalizedURL(currentModuleCode), GenerateSourceCodeUtil.normalizedURL(businessLogic.getBusinessLogicCode())));
				stringBuilder.append(StringUtils.LF);

				// stringPermission.delete(0, stringPermission.length());
			}
		}
		return stringBuilder.toString();
	}

	/**
	 * 
	 * @param file
	 * @return
	 */
	private boolean hasReplacementContent(File file) {
		String extentionFile = "";
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
			extentionFile = fileName.substring(fileName.lastIndexOf(".") + 1);
		}
		List<String> listExtentionFile = new ArrayList<>(Arrays.asList("jsp", "xml"));
		if (listExtentionFile.contains(extentionFile)) {
			return true;
		}
		return false;
	}

	public String getPathPackage() {
		return pathPackage;
	}

	public void setPathPackage(String pathPackage) {
		this.pathPackage = pathPackage;
	}

	/**
	 * @param generateSourceCode
	 * @param common
	 * @throws Exception
	 */
	private void processGenerateRootBatch(GenerateSourceCode generateSourceCode, CommonModel common) {
		if (generateSourceCode != null && generateSourceCode.getBatchModuleFlg()) {
			if (StringUtils.isNotBlank(rootDir)) {
				try {
					Project project = generateSourceCode.getProject();
					String batchDir = rootDir + generateSourceCode.getProject().getProjectCode() + "-batch-job" + File.separator;
					generateSourceCode.setSourcePathBatch(batchDir);

					/**
					 * If only generate path of project
					 */
					if (!generateStructure)
						return;

					FileUtilsQP.createDirectory(batchDir);

					InputStream inputStream = classLoader.getResourceAsStream(String.format(SOURCE_DEAULT_PATH, "batch" + getResourceByDbType(generateSourceCode.getProject().getDbType())));
					ZipUtils.extract(inputStream, batchDir, generateSourceCode.getTempFolder());
					IOUtils.closeQuietly(inputStream);

					// append folder java
					StringBuilder pathJava = new StringBuilder(batchDir + "src" + File.separator + "main" + File.separator + "java" + File.separator);
					String[] split = null;
					StringBuilder pathPackage = new StringBuilder();
					if (StringUtils.isNotBlank(generateSourceCode.getProject().getPackageName())) {
						split = generateSourceCode.getProject().getPackageName().split("\\.");
					}
					if (split != null && split.length > 0) {
						for (String str : split) {
							pathPackage.append(str).append(File.separator);
							pathJava.append(str + File.separator);
						}
					} else {
						pathJava.append(generateSourceCode.getProject().getPackageName() + File.separator);
					}
					this.setPathPackage(pathPackage.toString());
					FileUtilsQP.createDirectory(pathJava.toString());
					
					// add library to pom file
					String pomFile = batchDir + "pom.xml";
					 List<LibraryManagement> libraryList = libraryManagementRepository.findAllLibrary(project.getProjectId()); 
					/*List<LibraryManagement> libraryList = libraryManagementRepository.findAllLibraryWithFileContent(project.getProjectId());*/
					this.generateLibraryFiles(rootDir + GenerateSourceCodeConst.LIB_FOLDER, libraryList);
					
					if (CollectionUtils.isNotEmpty(libraryList)) {
						WriteXmlUltilsQP.processWritePomFile(pomFile, libraryList);
					}
					
				} catch (Exception e) {
					GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
				}
			}
		}
	}


}
