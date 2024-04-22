package org.terasoluna.qp.batch.generate;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessage;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.DbDomainConst.GenerateAppStatus;
import org.terasoluna.qp.app.common.constants.DbDomainConst.GenerateBLogicAppStatus;
import org.terasoluna.qp.app.common.ultils.FileUtilsQP;
import org.terasoluna.qp.app.common.ultils.FileUtilsQP.FileType;
import org.terasoluna.qp.app.common.ultils.FileUtilsQP.Folder;
import org.terasoluna.qp.app.common.ultils.GenerateUniqueKey;
import org.terasoluna.qp.app.common.ultils.LocaleUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.GenerateManagementMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.GenerateHistory;
import org.terasoluna.qp.domain.model.GenerateSourceCode;
import org.terasoluna.qp.domain.model.LanguageDesign;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.repository.generatemanagement.GenerateManagementRepository;
import org.terasoluna.qp.domain.repository.module.ModuleRepository;
import org.terasoluna.qp.domain.repository.project.ProjectRepository;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignRepository;
import org.terasoluna.qp.domain.service.account.AccountService;
import org.terasoluna.qp.domain.service.accountprofile.AccountProfileService;
import org.terasoluna.qp.domain.service.capture.CaptureService;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.generatedocument.GenerateDocumentService;
import org.terasoluna.qp.domain.service.generatemanagement.GenerateManagementService;
import org.terasoluna.qp.domain.service.generatesourcecode.GenerateSourceCodeConst;
import org.terasoluna.qp.domain.service.generatesourcecode.GenerateSourceCodeService;
import org.terasoluna.qp.domain.service.languagedesign.LanguageDesignService;

import jp.terasoluna.fw.batch.blogic.AbstractTransactionBLogic;
import jp.terasoluna.fw.batch.blogic.vo.BLogicParam;
import jp.terasoluna.fw.exception.SystemException;

@Component
public class GenerateBLogic extends AbstractTransactionBLogic {

	private static final Logger log = LoggerFactory.getLogger(GenerateBLogic.class);
	private static final int BATCH_NORMAL_END = 0;
	private static final int BATCH_ERROR_END = -1;

	@Inject
	GenerateDocumentService generateDocumentService;

	@Inject
	GenerateSourceCodeService generateSourceCodeService;

	@Inject
	GenerateManagementService generateManagementService;

	@Inject
	SystemService systemService;

	@Inject
	ProjectRepository projectRepository;

	@Inject
	ScreenDesignRepository screenDesignRepository;

	@Inject
	CaptureService captureService;

	@Inject
	AccountProfileService accountProfileService;

	@Inject
	AccountService accountService;

	@Inject
	LanguageDesignService languageDesignService;

	@Inject
	ModuleRepository moduleRepository;

	@Inject
	GenerateManagementRepository generateManagementRepository;

	@Inject
	PlatformTransactionManager transactionManager;

	@Override
	public int doMain(BLogicParam arg0) {

		log.info("Start generate " + arg0.getJobArgNm1());

		String rootPath = FileUtilsQP.normalizedPath(FileUtilsQP.getRootPath());//"D:\\batch\\";
		//String rootPath = "D:\\batch\\";
		String exportPath = rootPath + "export" + File.separator;
		String fileName = "";
		String status = GenerateBLogicAppStatus.SUCCESS;
		if (arg0.getJobArgNm1() != null) {

			// get parameter
			String generateMode = arg0.getJobArgNm1();
			Long projectId = Long.parseLong(arg0.getJobArgNm2());
			Long languageId = null;
			if (arg0.getJobArgNm3() != null) {
				languageId = Long.parseLong(arg0.getJobArgNm3());
			}
			Long generateBy = Long.parseLong(arg0.getJobArgNm4());
			String generateId = arg0.getJobSequenceId();
			GenerateHistory generateHistory = new GenerateHistory();
			generateHistory.setGenerateId(generateId);
			generateHistory.setProjectId(arg0.getJobArgNm2());
			generateHistory.setGenerateMode(arg0.getJobArgNm1());

			Locale locale = LocaleUtils.defaultLocal;
			try {
				// set language design
				LanguageDesign languageDesign = new LanguageDesign();
				if (languageId != null) {
					languageDesign = languageDesignService.getLanguageDesignById(languageId, projectId);
					locale = new Locale(languageDesign.getLanguageCode(), languageDesign.getCountryCode());
				} else {
					languageDesign = new LanguageDesign("en", "US");
					languageDesign.setProjectId(projectId);
					languageDesign = languageDesignService.findByLanguageDesign(languageDesign);
				}

				MockHttpServletRequest request = new MockHttpServletRequest();
				HttpSession session = request.getSession();
				session.setAttribute(LocaleUtils.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
				RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

				Project project = projectRepository.findById(projectId, generateBy);
				AccountProfile accountProfile = accountProfileService.findOne(generateBy);
				accountProfile.setCurrentProjectId(projectId);

				/*
				 * generateSourceCodeService.setWorkingProject(project); generateSourceCodeService.setCurrentAccount(account); generateSourceCodeService.setWorkingLanguageDesign(languageDesign); generateSourceCodeService.setCurrentAccounProfile(accountProfile);
				 */

				CommonModel commonModel = new CommonModel();
				commonModel.setWorkingProject(project);
				commonModel.setCreatedBy(generateBy);
				commonModel.setWorkingLanguageDesign(languageDesign);
				commonModel.setWorkingProjectId(projectId);
				/* commonModel.setAccount(accountService.findOneByAccountId(generateBy)); */

				String filePattern = project.getProjectCode() + GenerateUniqueKey.generateWithDatePrefix();
				String sourceExportPath = exportPath + FileUtilsQP.getFolderName() + File.separator + filePattern + File.separator;

				String tempFolder = exportPath + FileUtilsQP.getFolderName() + File.separator + FileUtilsQP.Folder.TEMP_FOLDER;

				sourceExportPath = FileUtilsQP.decodePath(sourceExportPath);
				exportPath = FileUtilsQP.decodePath(exportPath);

				FileUtilsQP.createDirectory(sourceExportPath);

				log.debug("Project Id = " + projectId + "Export path" + sourceExportPath + " - type " + arg0.getJobArgNm1());

				// call service generate
				if (DbDomainConst.GenerateHistoryMode.SOURCE_CODE.equals(generateMode)) {
					/* generateSourceCodeService.setWorkingLanguageId(languageId); */
					// sourceExportPath = exportPath + getFolderName() + File.separator + project.getProjectCode() + GenerateUniqueKey.generateWithDatePrefix() + File.separator;
					fileName = generateSourceCodeService.processGenerateAllSourceCode(project, exportPath, sourceExportPath, filePattern + GenerateSourceCodeConst.ZIP_EXTEND, tempFolder, commonModel);
				} else if (DbDomainConst.GenerateHistoryMode.WAR_FILE.equals(generateMode)) {
					/* generateSourceCodeService.setWorkingLanguageId(languageId); */
					// sourceExportPath = exportPath + getFolderName() + File.separator + project.getProjectCode() + GenerateUniqueKey.generateWithDatePrefix() + File.separator;

					String batFile = null;

					if (SystemUtils.IS_OS_WINDOWS) {
						batFile = rootPath + GenerateSourceCodeConst.BUILD_WAR_FILE_BAT;
					} else {
						batFile = rootPath + GenerateSourceCodeConst.BUILD_WAR_FILE_SH;
					}

					if (batFile == null || !new File(batFile).exists()) {
						ResultMessages resultMessages = ResultMessages.error();
						resultMessages.add(GenerateManagementMessageConst.SC_GENERATEMANAGEMENT_0024, rootPath);
						throw new BusinessException(resultMessages);
					}

					GenerateSourceCode generate = generateSourceCodeService.processGenerateWarFile(project, tempFolder, sourceExportPath, commonModel);
					log.debug("exportPath: " + generate.getSourcePath());

					// run bat file build war file
					String folderName = generate.getSourcePath();
					String projectWebName = project.getProjectCode() + "-web";

					String warFileName = projectWebName + GenerateUniqueKey.generateWithDatePrefix() + FileType.WAR;
					String logFileName = "build_" + GenerateUniqueKey.generateWithDatePrefix() + FileType.LOG;

					
					String mavenHome = rootPath + Folder.MAVEN_HOME;
					String path = System.getenv("PATH");
					String javaHome = rootPath +  Folder.JDK;

					
					Map<String,String> env = new HashMap<String, String>();
					env.put("JAVA_HOME", javaHome);
					env.put("M2_HOME", mavenHome);
					env.put("MAVEN_OPTS", "-Xms256m -Xms512m");
					
					/*import lib to local repository*/
					
					log.debug("logFile: {}", logFileName);
					File logFile = new File(exportPath, logFileName);
					
					ProcessBuilder pb = null;
					ProcessBuilder pbCustLibs = null;
					
					StringBuilder strPathCustLib = new StringBuilder(FileUtilsQP.normalizedPath(sourceExportPath));
					strPathCustLib.append(GenerateSourceCodeConst.LIB_FOLDER);
					strPathCustLib.append(File.separator);
					File fileEnv = null;
					if (SystemUtils.IS_OS_WINDOWS) {
						pb = new ProcessBuilder("cmd", "/c", batFile, folderName, projectWebName, warFileName, exportPath);
						env.put("M2", "%M2_HOME%\\bin");
						env.put("MAVEN_OPTS", "-Xms256m -Xms512m");
						env.put("PATH", path + ";" + javaHome  + "\\bin;"+ mavenHome +"\\bin;");
						
						
						strPathCustLib.append(GenerateSourceCodeConst.MAVEN_BAT_SCRIPT);
						fileEnv = new File(strPathCustLib.toString());
						if (fileEnv.exists()) {
							fileEnv.setExecutable(true);
							pbCustLibs = new ProcessBuilder("cmd", "/c", strPathCustLib.toString());
						}

					} else {
						pb = new ProcessBuilder("/bin/bash", batFile, folderName, projectWebName, warFileName, exportPath);
						env.put("M2", "$M2_HOME/bin");
						/*env.put("PATH", path + ":$JAVA_HOME/bin:$M2");*/
						env.put("PATH", path + ":" + javaHome  + "/bin:"+ mavenHome +"/bin:");
						
						strPathCustLib.append(GenerateSourceCodeConst.MAVEN_SH_SCRIPT);
						fileEnv = new File(strPathCustLib.toString());
						
						if (fileEnv.exists()) {
							fileEnv.setExecutable(true);
							fileEnv.setWritable(true);
							fileEnv.setReadable(true);
							pbCustLibs = new ProcessBuilder("/bin/bash", strPathCustLib.toString());
							
							/*repare file
							
							String testFile = rootPath + "dungnn.sh";
							
							StringBuilder content = new StringBuilder("#!/bin/bash"); 
							content.append(StringUtils.LF);
							content.append("echo \"PATH in install custom lib: $PATH\"");
							FileUtilsQP.writeStringToFile(new File(testFile), content.toString());
							
							ProcessBuilder pbPre = new ProcessBuilder("/bin/bash", testFile);
							pbPre.redirectErrorStream(true);
							pbPre.redirectOutput(Redirect.appendTo(logFile));
							
							Process pPre = pbPre.start();
							assert pbPre.redirectInput() == Redirect.PIPE;
							assert pbPre.redirectOutput().file() == logFile;
							assert pPre.getInputStream().read() == -1;

							try {
								log.debug("Waiting for repare file");
								if (!pPre.waitFor(1, TimeUnit.MINUTES)) {
									pPre.destroy();
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
								throw new SystemException(e);
							}*/
						}

					}
					

					log.debug("fileEnv: {}", strPathCustLib.toString());
					if (fileEnv != null && fileEnv.exists()) {
						pbCustLibs.environment().putAll(env);
						
						pbCustLibs.directory(new File(exportPath));
						pbCustLibs.redirectErrorStream(true);
						pbCustLibs.redirectOutput(Redirect.appendTo(logFile));
						Process pEnv = pbCustLibs.start();
						
						assert pbCustLibs.redirectInput() == Redirect.PIPE;
						assert pbCustLibs.redirectOutput().file() == logFile;
						assert pEnv.getInputStream().read() == -1;

						// Wait to get exit value
						try {
							log.debug("Waiting for destroy cust lib");
							if (!pEnv.waitFor(15, TimeUnit.MINUTES)) {
								pEnv.destroy();
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
							throw new SystemException(e);
						}
					}

					pb.environment().putAll(env);
					pb.directory(new File(exportPath));
					//File logFile = new File(exportPath, logFileName);
					pb.redirectErrorStream(true);
					pb.redirectOutput(Redirect.appendTo(logFile));
					Process p = pb.start();
					assert pb.redirectInput() == Redirect.PIPE;
					assert pb.redirectOutput().file() == logFile;
					assert p.getInputStream().read() == -1;

					// Wait to get exit value
					try {
						log.debug("Waiting for destroy build war");
						if (!p.waitFor(15, TimeUnit.MINUTES)) {
							p.destroy();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
						throw new SystemException(e);
					}

					// check war file is exist
					File warFile = new File(exportPath + warFileName);

					if (warFile.exists()) {
						fileName = warFileName;
						// remove build file log
						File file = new File(exportPath + logFileName);
						FileUtilsQP.deleteQuietly(file);
					} else {
						fileName = logFileName;
						status = GenerateBLogicAppStatus.ERROR;
					}
					log.debug("File name " + exportPath + fileName);
				} else if (DbDomainConst.GenerateHistoryMode.DOCUMENT.equals(generateMode)) {

					// capture all screen design
					List<ScreenDesign> lstAllScreenDesign = screenDesignRepository.getAllScreenInfoByProjectId(projectId, languageId);
					List<Long> lstScreenId = new ArrayList<Long>();
					for (ScreenDesign screenDesign : lstAllScreenDesign) {
						lstScreenId.add(screenDesign.getScreenId());
					}
					captureService.captureScreenDesign(lstScreenId, projectId, languageId, rootPath, sourceExportPath);
					// capture all screen transition
					List<Module> modules = moduleRepository.findAllModuleOfOnline(projectId, DbDomainConst.FunctionType.ONLINE);
					if (CollectionUtils.isNotEmpty(modules)) {
						List<Long> lstModuleId = new ArrayList<Long>();
						for (Module module : modules) {
							lstModuleId.add(module.getModuleId());
						}
						captureService.captureScreenTransDiagram(lstModuleId, projectId, languageId, rootPath, sourceExportPath);
					}

					/*
					 * generateDocumentService.setAccountId(generateBy); generateDocumentService.setCurrentAccounProfile(accountProfile); generateDocumentService.setCurrentAccount(account); generateDocumentService.setWorkingProject(project); generateDocumentService.setWorkingProjectId(projectId); generateDocumentService.setWorkingLanguageId(languageId); generateDocumentService.setWorkingLanguageDesign(languageDesign);
					 */

					fileName = generateDocumentService.processGenerateAllDocement(project, exportPath, sourceExportPath, commonModel);
				}
				// remove old file name
				String lastFileName = generateManagementService.getLastFileName(generateHistory);
				if (!StringUtils.isEmpty(lastFileName)) {
					File file = new File(exportPath + lastFileName);
					FileUtilsQP.deleteQuietly(file);
				}
				// update download flag
				generateManagementService.updateDownloadFlag(generateHistory);

			} catch (BusinessException be) {
				fileName = "generate_error_" + generateId + FileType.LOG;
				status = GenerateBLogicAppStatus.ERROR;
				StringBuilder sb = new StringBuilder();
				for (ResultMessage resultMessage : be.getResultMessages().getList()) {
					sb.append(MessageUtils.getMessage(resultMessage.getCode(), resultMessage.getArgs())).append("\n");
				}
				String text = sb.toString();
				PrintStream ps = null;
				try {
					File logFile = new File(exportPath + fileName);
					if (!logFile.exists()) {
						logFile.createNewFile();
					}
					ps = new PrintStream(logFile);
					ps.print(text);
				} catch (IOException exp) {
					exp.printStackTrace();
				} finally {
					try {
						if (ps != null) {
							ps.close();
						}
					} catch (Exception ex) {
						/* ignore */
					}
				}
			} catch (Exception e) {
				fileName = "generate_error_" + generateId + FileType.LOG;
				status = GenerateBLogicAppStatus.ERROR;
				PrintStream ps = null;
				try {
					log.info("log file:{0}-{1}", exportPath, fileName);
					File logFile = new File(exportPath + fileName);
					if (!logFile.exists()) {
						logFile.createNewFile();
					}
					ps = new PrintStream(logFile);
					e.printStackTrace(ps);
				} catch (IOException exp) {
					exp.printStackTrace();
				} finally {
					try {
						if (ps != null) {
							ps.close();
						}
					} catch (Exception ex) {
						/* ignore */
					}
				}
			} finally {
				// remove temp folder
				/*
				 * log.debug("cleanup folder: " + exportPath ); FileUtilsQP.cleanup(exportPath);
				 */
			}
			// update generate history
			generateHistory.setFileName(fileName);
			generateHistory.setIsDownload(1);
			generateHistory.setCurAppStatus(GenerateAppStatus.GENERATED);
			generateHistory.setBlogicAppStatus(status);
			try {
				generateManagementService.updateGenerateHistory(generateHistory);
				generateHistory = generateManagementRepository.getGenerateHistoryById(generateId);
				if (generateHistory == null || StringUtils.isEmpty(generateHistory.getFileName())) {
					return BATCH_ERROR_END;
				}

			} catch (Exception e) {
				fileName = "error_" + generateId + FileType.LOG;
				PrintStream ps = null;
				try {
					File logFile = new File(exportPath + fileName);
					if (!logFile.exists()) {
						logFile.createNewFile();
					}
					ps = new PrintStream(logFile);
					e.printStackTrace(ps);
				} catch (IOException exp) {
					exp.printStackTrace();
				} finally {
					try {
						if (ps != null) {
							ps.close();
						}
					} catch (Exception ex) {
						/* ignore */
					}
				}
				return BATCH_ERROR_END;
			} finally {
				// remove temp folder
				log.debug("cleanup folder: " + exportPath);
				FileUtilsQP.cleanup(exportPath);
			}
		} else {
			return BATCH_ERROR_END;
		}

		log.debug("End generate");

		return BATCH_NORMAL_END;
	}

	public static void main(String[] args) {
		System.out.println(FileUtilsQP.getRootPath());
	}
}
