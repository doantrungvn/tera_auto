package org.terasoluna.qp.domain.service.project;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.codelist.SimpleMapCodeList;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.SystemConst;
import org.terasoluna.qp.app.common.constants.TerasolunaQPValidationConst;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.DateUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.GenerateUniqueKey;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.GraphicDatabaseDesignMessageConst;
import org.terasoluna.qp.app.message.ProjectMessageConst;
import org.terasoluna.qp.app.message.SessionManagementMessageConst;
import org.terasoluna.qp.app.message.SqlDesignMessageConst;
import org.terasoluna.qp.app.message.TableDesignMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.ExternalObjectDefinition;
import org.terasoluna.qp.domain.model.FunctionMaster;
import org.terasoluna.qp.domain.model.FunctionMethod;
import org.terasoluna.qp.domain.model.FunctionMethodInput;
import org.terasoluna.qp.domain.model.FunctionMethodOutput;
import org.terasoluna.qp.domain.model.LanguageDesign;
import org.terasoluna.qp.domain.model.Log;
import org.terasoluna.qp.domain.model.MessageDesign;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.ProblemList;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.ProjectItem;
import org.terasoluna.qp.domain.model.ProjectTheme;
import org.terasoluna.qp.domain.model.SessionManagement;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.model.SubjectArea;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.repository.autocomplete.AutocompleteDesignRepository;
import org.terasoluna.qp.domain.repository.licensedesign.LicenseDesignRepository;
import org.terasoluna.qp.domain.repository.module.ModuleRepository;
import org.terasoluna.qp.domain.repository.problemlist.ProblemListRepository;
import org.terasoluna.qp.domain.repository.project.ProjectCriteria;
import org.terasoluna.qp.domain.repository.project.ProjectRepository;
import org.terasoluna.qp.domain.repository.projecttheme.ProjectThemeRepository;
import org.terasoluna.qp.domain.repository.sessionmanagement.SessionManagementRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignRepository;
import org.terasoluna.qp.domain.repository.subjectarea.SubjectAreaRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignDetailRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignRepository;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.externalobjectdefinition.ExternalObjectDefinitionService;
import org.terasoluna.qp.domain.service.externalobjectdefinition.InitExternalObjectDefinitionManagement;
import org.terasoluna.qp.domain.service.functionmaster.FunctionMasterService;
import org.terasoluna.qp.domain.service.graphicdatabasedesign.GraphicDatabaseDesignService;
import org.terasoluna.qp.domain.service.graphicdatabasedesign.GraphicDbDesign;
import org.terasoluna.qp.domain.service.languagedesign.LanguageDesignService;
import org.terasoluna.qp.domain.service.loggingmanagement.InitLoggingManagement;
import org.terasoluna.qp.domain.service.loggingmanagement.LoggingManagementService;
import org.terasoluna.qp.domain.service.messagedesign.MessageDesignService;
import org.terasoluna.qp.domain.service.sessionmanagement.InitSessionManagement;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

	@Inject
	ProjectRepository projectRepository;
	
	@Inject
	LicenseDesignRepository licenseDesignRepository;

	@Inject
	ModuleRepository moduleRepository;

	@Inject
	MessageDesignService messageDesignService;

	@Inject
	FunctionMasterService functionMasterService;

	@Inject
	TableDesignRepository tableDesignRepository;

	@Inject
	SqlDesignRepository sqlDesignRepository;

	@Inject
	AutocompleteDesignRepository autocompleteDesignRepository;

	@Inject
	TableDesignDetailRepository tableDesignDetailRepository;

	@Inject
	ProblemListRepository problemListRepository;

	@Inject
	LanguageDesignService languageDesignService;

	@Inject
	GraphicDatabaseDesignService graphicDbDesignService;

	@Inject
	SubjectAreaRepository areaRepository;

	@Inject
	SessionManagementRepository sessionManagementRepository;
	
	@Inject
	ProjectThemeRepository projectThemeRepository;
	
	@Inject
	LoggingManagementService loggingManagementService;

	@Inject
	@Named(value = "CL_DATABASE_TYPE")
	SimpleMapCodeList simpleMapCodeList;

	@Inject
	SystemService systemService;
	
	@Inject
	ExternalObjectDefinitionService externalObjectDefinitionService;

	private static final String DOMAIN_TABLE_MAPPING_REF_COUNT = "domain_table_mapping_ref_count";
	/* private static final String DOMAIN_DESIGN_REF_COUNT = "domain_design_ref_count"; */
	private static final String MODULE_REF_COUNT = "module_ref_count";
	private static final String SUBJECT_AREA_DESIGN_REF_COUNT = "subject_area_design_ref_count";
	private static final String TABLE_DESIGN_REF_COUNT = "table_design_ref_count";

	private static final String BUSINESS_TYPE_REF_COUNT = "business_type_ref_count";
	private static final String SQL_DESIGN_REF_COUNT = "sql_design_ref_count";
	private static final String DECISION_TABLE_REF_COUNT = "decision_table_ref_count";
	private static final String BUSINESS_LOGIC_REF_COUNT = "business_logic_ref_count";
	private static final String CODELIST_REF_COUNT = "codelist_ref_count";

	// private static final String MENU_REF_COUNT = "menu_ref_count";

	/**
	 * Finds all project information with search criteria
	 *
	 * @param criteria
	 *            Project criteria
	 * @return List of all project
	 */
	@Override
	public Page<Project> searchProject(ProjectCriteria criteria, Pageable pageable) {
		long totalCount = projectRepository.countBySearchCriteria(criteria);

		List<Project> projects;
		if (0 < totalCount) {
			projects = projectRepository.findPageBySearchCriteria(criteria, pageable);
		} else {
			projects = Collections.emptyList();
		}
		Page<Project> page = new PageImpl<Project>(projects, pageable, totalCount);
		return page;
	}

	/**
	 * Finds a project information with project identify
	 *
	 * @param projectId
	 *            identify
	 * @return project information
	 */
	@Override
	public Project getProjectInformation(Long projectId, Long accountId) {
		Project project = projectRepository.findById(projectId, accountId);
		if (project == null) {
			systemService.resetAccountProject();
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(ProjectMessageConst.SC_PROJECT_0010)));
		}
		return project;
	}

	/**
	 * Finds all Modules in project
	 *
	 * @param projectId
	 *            identify
	 * @return list Module
	 */
	@Override
	public List<Module> findAllModuleOfProject(Long projectId) {
		// Finds all related module information to project
		return moduleRepository.findAllModuleOfProject(projectId, null);
	}

	/**
	 * Count child function in Project
	 *
	 * @param projectId
	 *            identify
	 * @return project information
	 */
	@Override
	public long validateChangeStatusToFixed(Long projectId) {
		return projectRepository.validateChangeStatusToFixed(projectId);
	}

	/**
	 * Register project information
	 *
	 * @param project
	 *            Project information
	 * @return a project
	 */
	@Override
	public Project registerProject(Project project) {
		// Project code and name are duplicated
		Long totalCount = projectRepository.countNameCodeByProjectId(project);
		ResultMessages resultMessages = ResultMessages.error();

		if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(ProjectMessageConst.SC_PROJECT_0005));
		} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(ProjectMessageConst.SC_PROJECT_0006));
		} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(ProjectMessageConst.SC_PROJECT_0005));
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(ProjectMessageConst.SC_PROJECT_0006));
		}
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {

			Timestamp systemDate = FunctionCommon.getCurrentTime();
			
			//project.setCreatedBy(accountId);
			project.setCreatedDate(systemDate);
			//project.setUpdatedBy(accountId);
			project.setUpdatedDate(systemDate);
			// if don't config db name then default = project code
			if (StringUtils.isBlank(project.getDbName())) {
				project.setDbName(StringUtils.lowerCase(project.getProjectCode()));
			}

			projectRepository.register(project);

			List<MessageDesign> initMessages = messageDesignService.getDefaultMessageFromQPSystem();
			LanguageDesign defaultLanguageDesign = languageDesignService.registerDefaultLanguageDesign(project.getProjectId(), project.getCreatedBy());

			if (CollectionUtils.isNotEmpty(initMessages)) {

				for (MessageDesign message : initMessages) {
					message.setClassFlg(DbDomainConst.QPCommomFlg.YES);
					message.setLanguageId(defaultLanguageDesign.getLanguageId());
					message.setFromLanguageId(defaultLanguageDesign.getLanguageId());
					message.setProjectId(project.getProjectId());
					message.setCreatedBy(project.getCreatedBy());
					message.setUpdatedBy(project.getCreatedBy());
					message.setCreatedDate(project.getCreatedDate());
					message.setUpdatedDate(project.getUpdatedDate());
					if (StringUtils.isNoneBlank(message.getModuleCode())
						&& !StringUtils.equals(message.getModuleCode(), CommonMessageConst.TQP_SYS)
						&& !StringUtils.equals(message.getModuleCode(), CommonMessageConst.TQP_SYS_JS)
						&& !StringUtils.equals(message.getModuleCode(), CommonMessageConst.TQP_TQP)
					) {
						message.setMessageLevel(DbDomainConst.MessageLevel.MODULE);
					} else {
						message.setMessageLevel(DbDomainConst.MessageLevel.PROJECT);
					}

					if (message.getMessageCode().startsWith(DbDomainConst.MessageType.INFORMATION_MESSAGE)) {
						message.setMessageType(DbDomainConst.MessageType.INFORMATION_MESSAGE);
					} else if (message.getMessageCode().startsWith(DbDomainConst.MessageType.WARINING_MESSAGE)) {
						message.setMessageType(DbDomainConst.MessageType.WARINING_MESSAGE);
					} else if (message.getMessageCode().startsWith(DbDomainConst.MessageType.ERROR_MESSAGE)) {
						message.setMessageType(DbDomainConst.MessageType.ERROR_MESSAGE);
					} else {
						message.setMessageType(DbDomainConst.MessageType.LABEL_MESSAGE);
					}
				}
				/*messageDesignService.initData(project.getProjectId(), project.getCreatedBy());*/
				messageDesignService.registerMessageDesign(initMessages, false);
			}

			// init function master
			initializeFunctionMaster(project.getProjectId(), project.getCreatedBy());

			initDefaultFooterRight(project,defaultLanguageDesign);

			//init QP table
			GraphicDbDesign graphicDbDesign = initQpTable(project, defaultLanguageDesign);

			//init session management
			initSessionManagement(project.getProjectId(), project.getCreatedBy(), graphicDbDesign);

			//initializing external object definition
			initMultipartFileManagement(project.getProjectId(), project.getCreatedBy());
			
			//init logging management
			initLoggingManagement(project.getProjectId(), project.getCreatedBy());
			
			//init style button delete
			List<ProjectTheme> lstProjectTheme = new ArrayList<ProjectTheme>();
			
			ProjectTheme objTempBg = new ProjectTheme();
			objTempBg.setProjectId(project.getProjectId());
			objTempBg.setCode("common-button-delete-bg-color");
			objTempBg.setValue("#eb9316");
			objTempBg.setAccountId(project.getCreatedBy());
			
			ProjectTheme objTempText = new ProjectTheme();
			objTempText.setProjectId(project.getProjectId());
			objTempText.setCode("common-button-delete-text-color");
			objTempText.setValue("#ffffff");
			objTempText.setAccountId(project.getCreatedBy());
			
			lstProjectTheme.add(objTempText);
			lstProjectTheme.add(objTempBg);
			
			projectThemeRepository.insertStyleDelete(lstProjectTheme);
		}
		systemService.resetAccountProject();
		return project;
	}

	/**
	 * Modify project information
	 *
	 * @param project
	 *            Project information
	 */
	@Override
	public void modifyProject(Project project) {
		getProjectInformation(project.getProjectId(), project.getUpdatedBy(), true);

		project.setSystemTime(FunctionCommon.getCurrentTime());
		project.setDbName(StringUtils.lowerCase(project.getDbName()));
		/*project.setUpdatedBy(accountId);*/

		// Project code and name are duplicated
		Long totalCount = projectRepository.countNameCodeByProjectId(project);
		ResultMessages resultMessages = ResultMessages.error();

		if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(ProjectMessageConst.SC_PROJECT_0005));
		} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(ProjectMessageConst.SC_PROJECT_0006));
		} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(ProjectMessageConst.SC_PROJECT_0005));
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(ProjectMessageConst.SC_PROJECT_0006));
		}
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {
			//check insert data
			
			if (projectRepository.modify(project, projectRepository.checkExistProjectMailAccount(project.getProjectId())) <= 0) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(ProjectMessageConst.SC_PROJECT_0010)));
			}
			projectRepository.synchronizeLicenseDesign(project);
			
//			SessionUtils.set(SessionUtils.CURRENT_LANGUAGE_DESIGN, languageDesign);
			//DungNN - 20160306 - int for old data
			/*if (projectRepository.checkInitQpTable(project.getProjectId()) == 0) {
				LanguageDesign languageDesign = languageDesignService.getLanguageDesignById(project.getDefaultLanguageId(), project.getProjectId());
				//init QP table
				GraphicDbDesign graphicDbDesign = initQpTable(project, languageDesign);
				//init session management
				initSessionManagement(project.getProjectId(), graphicDbDesign);
			}*/
		}
		systemService.resetAccountProject();
	}

	/**
	 * Delete project information
	 *
	 * @param project
	 *            Project information
	 */
	@Override
	public void deleteProject(Project project) {
		//project = getProjectInformation(project.getProjectId(), project.getUpdatedBy(), true);

		validateBeforceDelete(project);
		
		// Data is being used by another function
		ResultMessages resultMessages = ResultMessages.error();
		HashMap<String, Long> referenceHashMap = projectRepository.countReferenceByProjectId(project.getProjectId());
		if (0 < referenceHashMap.get(DOMAIN_TABLE_MAPPING_REF_COUNT)) {
			resultMessages.add(ProjectMessageConst.ERR_PROJECT_0001, project.getProjectName(), MessageUtils.getMessage(CommonMessageConst.TQP_DOMAINDATATYPE));
		}
		if (0 < referenceHashMap.get(MODULE_REF_COUNT)) {
			resultMessages.add(ProjectMessageConst.ERR_PROJECT_0001, project.getProjectName(), MessageUtils.getMessage(CommonMessageConst.TQP_MODULE));
		}
		if (0 < referenceHashMap.get(SUBJECT_AREA_DESIGN_REF_COUNT)) {
			resultMessages.add(ProjectMessageConst.ERR_PROJECT_0001, project.getProjectName(), MessageUtils.getMessage(CommonMessageConst.TQP_SUBAREADESIGN));
		}
		if (0 < referenceHashMap.get(TABLE_DESIGN_REF_COUNT)) {
			resultMessages.add(ProjectMessageConst.ERR_PROJECT_0001, project.getProjectName(), MessageUtils.getMessage(CommonMessageConst.TQP_TABLEDESIGN));
		}
		if (0 < referenceHashMap.get(BUSINESS_TYPE_REF_COUNT)) {
			resultMessages.add(ProjectMessageConst.ERR_PROJECT_0001, project.getProjectName(), MessageUtils.getMessage(CommonMessageConst.TQP_BUSINESSTYPE));
		}
		if (0 < referenceHashMap.get(SQL_DESIGN_REF_COUNT)) {
			resultMessages.add(ProjectMessageConst.ERR_PROJECT_0001, project.getProjectName(), MessageUtils.getMessage(CommonMessageConst.TQP_SQLDESIGN));
		}
		if (0 < referenceHashMap.get(DECISION_TABLE_REF_COUNT)) {
			resultMessages.add(ProjectMessageConst.ERR_PROJECT_0001, project.getProjectName(), MessageUtils.getMessage(CommonMessageConst.TQP_DECISIONTABLE));
		}
		if (0 < referenceHashMap.get(BUSINESS_LOGIC_REF_COUNT)) {
			resultMessages.add(ProjectMessageConst.ERR_PROJECT_0001, project.getProjectName(), MessageUtils.getMessage(CommonMessageConst.TQP_BUSINESSLOGICDESIGN));
		}
		if (0 < referenceHashMap.get(CODELIST_REF_COUNT)) {
			resultMessages.add(ProjectMessageConst.ERR_PROJECT_0001, project.getProjectName(), MessageUtils.getMessage(CommonMessageConst.TQP_CODELIST));
		}

		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {
			projectRepository.delete(project);
			systemService.resetAccountProject();
		}
	}

	private void validateBeforceDelete(Project project) {
		Project projectFromDB = getProjectInformation(project.getProjectId(), project.getUpdatedBy());

		if (DbDomainConst.DesignStatus.FIXED.equals(projectFromDB.getStatus())) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0109, projectFromDB.getProjectName()));
		}
		
		if (DateUtils.compare(projectFromDB.getUpdatedDate(), project.getUpdatedDate()) == 1) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
		}
		
	}
	
	/**
	 * Delete project information
	 *
	 * @param project
	 *            Project information
	 */
	@Override
	public void deleteAssociatedProject(Project project) {
		validateBeforceDelete(project);
		projectRepository.deleteAssociatedProject(project);
		systemService.resetAccountProject();
	}

	@Override
	public List<Project> getAllProjectAssignToAccount(Long accountId) {
		return projectRepository.getAllProjectAssignToAccount(accountId);
	}

	@Override
	public List<Project> findAllProjectByAccount(Long accountId) {
		return projectRepository.findAllProjectByAccount(accountId);
	}

	@Override
	public Project modifyDesignStatus(Project project) {
		Project projectFromDB = getProjectInformation(project.getProjectId(), project.getUpdatedBy());

		/*// if status new = status in db -> success
		if (projectFromDB.getStatus().equals(project.getStatus())) {
			return projectFromDB;
		}*/

		if (DateUtils.compare(projectFromDB.getUpdatedDate(), project.getUpdatedDate()) == 1) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
		}

		projectFromDB.setStatus(project.getStatus());
		projectFromDB.setUpdatedBy(project.getUpdatedBy());
		projectFromDB.setSystemTime(FunctionCommon.getCurrentTime());
		projectFromDB.setUpdatedDate(project.getUpdatedDate());

		if (DbDomainConst.DesignStatus.FIXED.equals(projectFromDB.getStatus())) {
			if (validateChangeStatusToFixed(projectFromDB.getProjectId()) > 0) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0117, MessageUtils.getMessage(CommonMessageConst.SC_TQP_0011)));
			}
		}

		if (projectRepository.modifyDesignStatus(projectFromDB) <= 0) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(ProjectMessageConst.SC_PROJECT_0010)));
		}
		
		systemService.resetAccountProject();
		return projectFromDB;
	}

	/**
	 * check design information belong current project, and check design of status project
	 *
	 * @param projectId
	 * @param checkDesignSatatus
	 *            : if true -> check if project's design status is fixed then throw business exception
	 * @author dungnn1
	 */
	@Override
	public void validateProject(Long projectId, Long accountId, Long workingProjectId, boolean checkDesignStatus) {
		// if project of design dose not belong current project
		if (accountId == null || DataTypeUtils.notEquals(workingProjectId, projectId)) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0074));
		}

		// check current project was deleted or assign to user
		Project project = getProjectInformationFromCache(projectId, accountId);

		// if check design status
		if (checkDesignStatus && DbDomainConst.DesignStatus.FIXED.equals(project.getStatus())) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0111, StringUtils.lowerCase(MessageUtils.getMessage(CommonMessageConst.SC_TQP_0011)), project.getProjectName()));
		}
	}

	@Override
	public void validateProject(Long accountId, Long workingProjectId) {
		validateProject(workingProjectId,accountId, workingProjectId, true);
	}

	
	@Override
	public Project validateProject(CommonModel commonModel) {
		
		Long projectId = commonModel.getProjectId();
		Long accountId = commonModel.getCreatedBy();
		Long workingProjectId = commonModel.getWorkingProjectId();
		boolean checkDesignStatus = commonModel.getDesignStatus();
		
		// if project of design dose not belong current project
		if (FunctionCommon.notEquals(workingProjectId, projectId)) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0074));
		}

		// check current project was deleted or assign to user
		Project project = getProjectInformationFromCache(projectId, accountId);

		// if check design status
		if (checkDesignStatus && DbDomainConst.DesignStatus.FIXED.equals(project.getStatus())) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0111, StringUtils.lowerCase(MessageUtils.getMessage(CommonMessageConst.SC_TQP_0011)), project.getProjectName()));
		}
		return project;
	}
	
	/**
	 *
	 * @param projectId
	 * @param accountId
	 * @param checkDesignSatatus
	 */
	@Override
	public Project getProjectInformation(Long projectId, Long accountId, boolean checkDesignStatus) {
		// check if project was deleted or assign to user
		Project project = getProjectInformationFromCache(projectId, accountId);

		// if check design status
		if (checkDesignStatus && DbDomainConst.DesignStatus.FIXED.equals(project.getStatus())) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0111, StringUtils.lowerCase(MessageUtils.getMessage(CommonMessageConst.SC_TQP_0011)), project.getProjectName()));
		}
		return project;
	}
	
	private Project getProjectInformationFromCache(Long projectId, Long accountId) {
		Project p = null;
		
		List<Project> projets = systemService.getAllProjectByAccount(accountId);
		int numOfProj = projets == null ? 0 : projets.size();
		if (numOfProj == 0) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(ProjectMessageConst.SC_PROJECT_0010)));
		}

		for (int i = 0; i < numOfProj; i++) {
			Project temp = projets.get(i);
			if (DataTypeUtils.equals(projectId, temp.getProjectId())) {
				p = temp;
				break;
			}
		}
		if (p == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(ProjectMessageConst.SC_PROJECT_0010)));
		}

		return p;
	}
	
	/*String insertFMaster = "INSERT INTO function_master (function_master_code,function_master_name, function_master_type, upload_file_id, project_id) VALUES ('%s' , '%s' , %s , %s, proj.project_id)	RETURNING function_master_id INTO fmasId;";
	String insertFMethod = "INSERT INTO function_method (function_master_id ,function_method_name ,function_method_code ,item_seq_no ,remark) VALUES (fmasId , '%s' , '%s' , %s , '%s') RETURNING function_method_id INTO fMethodId;";
	String insertFInput = "INSERT INTO function_method_input (function_method_id ,method_input_code ,method_input_name ,data_type,array_flg) VALUES (fMethodId , '%s' , '%s' , %s , '%s');";
	String insertFoutput = "INSERT INTO function_method_output (function_method_id ,method_output_code, method_output_name ,data_type,array_flg) VALUES (fMethodId, '%s' , '%s' ,  %s , '%s');";
	*/
	private void initializeFunctionMaster(Long projectId, Long accountId) {
		try {
			List<FunctionMaster> listOfFunctionMaster = functionMasterService.findFunctionMasterDefault();
			List<FunctionMethod> listOfMethod = functionMasterService.findFuntionMethodDefault();
			List<FunctionMethodInput> listOfInput = functionMasterService.findFunctionMethodInputDefault();
			List<FunctionMethodOutput> listOfOutput = functionMasterService.findFunctionMethodOutputDefault();

			int numOfMethod = FunctionCommon.isNotEmpty(listOfMethod) ? listOfMethod.size() : 0;
			int numOfInput = FunctionCommon.isNotEmpty(listOfInput) ? listOfInput.size() : 0;
			int numOfOutput = FunctionCommon.isNotEmpty(listOfOutput) ? listOfOutput.size() : 0;

			Timestamp currentTime = FunctionCommon.getCurrentTime();

			/*StringBuilder sql = new StringBuilder();*/
			
			if (FunctionCommon.isNotEmpty(listOfFunctionMaster)) {
				for (FunctionMaster fMaster : listOfFunctionMaster) {

					/*sql.append(String.format(insertFMaster, fMaster.getFunctionMasterCode(), fMaster.getFunctionMasterName(), fMaster.getFunctionMasterType(), fMaster.getUploadFileId()));
					sql.append(StringUtils.LF);*/
					List<FunctionMethod> lFunctionMethod = new ArrayList<FunctionMethod>();

					for (int i = 0; i < numOfMethod; i++) {
						FunctionMethod fMethod = listOfMethod.get(i);

						List<FunctionMethodInput> lInput = new ArrayList<FunctionMethodInput>();
						List<FunctionMethodOutput> lOutput = new ArrayList<FunctionMethodOutput>();

						if (FunctionCommon.equals(fMethod.getFunctionMasterId(), fMaster.getFunctionMasterId())) {
							/*sql.append(String.format(insertFMethod, fMethod.getFunctionMethodName(), fMethod.getFunctionMethodCode(), i, fMethod.getRemark()));
							sql.append(StringUtils.LF);*/
							// process for input
							for (int j = 0; j < numOfInput; j++) {
								FunctionMethodInput functionMethodInput = listOfInput.get(j);
								if (FunctionCommon.equals(fMethod.getFunctionMethodId(), functionMethodInput.getFunctionMethodId())) {
									lInput.add(functionMethodInput);
									
									/*sql.append(String.format(insertFInput,functionMethodInput.getMethodInputCode(), functionMethodInput.getMethodInputName(), functionMethodInput.getDataType(), functionMethodInput.getObjectFlg()));
									sql.append(StringUtils.LF);*/
								}
							}
							fMethod.setFunctionMethodInput(lInput);
							// process for output
							for (int k = 0; k < numOfOutput; k++) {
								FunctionMethodOutput functionMethodOutput = listOfOutput.get(k);
								if (FunctionCommon.equals(fMethod.getFunctionMethodId(), functionMethodOutput.getFunctionMethodId())) {
									lOutput.add(functionMethodOutput);
									/*sql.append(String.format(insertFoutput, functionMethodOutput.getMethodOutputCode(), functionMethodOutput.getMethodOutputName(), functionMethodOutput.getDataType(), functionMethodOutput.getObjectFlg()));
									sql.append(StringUtils.LF);*/
								}
							}
							fMethod.setFunctionMethodOutput(lOutput);

							lFunctionMethod.add(fMethod);
						}
					}
					// register function master
					fMaster.setProjectId(projectId);
					fMaster.setCreatedBy(accountId);
					fMaster.setCreatedDate(currentTime);
					fMaster.setUpdatedBy(accountId);
					fMaster.setUpdatedDate(currentTime);
					fMaster.setFunctionMethod(lFunctionMethod);
					functionMasterService.registerFunctionMasterDefault(fMaster);
				}
			}
			//System.out.print(sql.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public List<Module> findAllModuleOfProject(Long projectId, Integer status) {
		return moduleRepository.findAllModuleOfProject(projectId, status);
	}

	@Override
	public void settingTheme(Map<String, String> mapTheme, Long projectId, Long accountId) {
		
		// check exists
		this.getProjectInformation(projectId, accountId, true);
		
		projectRepository.deleteThemeByProjectId(projectId);
		for (Entry<String, String> entry : mapTheme.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			ProjectTheme objTemp = new ProjectTheme();
			objTemp.setProjectId(projectId);
			objTemp.setCode(key);
			objTemp.setValue(value);
			objTemp.setAccountId(accountId);
			projectRepository.addProjectTheme(objTemp);
		}
	}

	@Override
	public Map<String, String> findThemeByProjectId(Long projectId) {
		List<ProjectTheme> lstTheme = projectRepository.findThemeByProjectId(projectId);

		if (CollectionUtils.isEmpty(lstTheme)) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(ProjectMessageConst.SC_PROJECT_0010)));
		}

		HashMap<String, String> mapTheme = new HashMap<String, String>();
		for (ProjectTheme projectTheme : lstTheme) {
			if(projectTheme.getCode() != null && projectTheme.getValue() != null) {
				if (!projectTheme.getCode().equals("logo") && !projectTheme.getCode().equals("commom-background-image") && !projectTheme.getCode().equals("common-screen-size") && projectTheme.getValue().lastIndexOf("px") != -1 && projectTheme.getValue().lastIndexOf("px") == projectTheme.getValue().length() - 2) {
					mapTheme.put(projectTheme.getCode(), projectTheme.getValue().replace("px", ""));
				} else {
					mapTheme.put(projectTheme.getCode(), projectTheme.getValue());
				}
			}

		}
		return mapTheme;
	}

	/*TungHT*/
	@Override
	public Map<String, String> findThemeByProjectIdForHTML(Long projectId) {
		List<ProjectTheme> lstTheme = projectRepository.findThemeByProjectId(projectId);

		if (CollectionUtils.isEmpty(lstTheme)) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(ProjectMessageConst.SC_PROJECT_0010)));
		}

		HashMap<String, String> mapTheme = new HashMap<String, String>();
		for (ProjectTheme projectTheme : lstTheme) {
			mapTheme.put(projectTheme.getCode(), projectTheme.getValue());
		}
		return mapTheme;
	}

	@Override
	public void addProjectItem(Long projectId, List<ProjectItem> projectItem) {
		projectRepository.deleteProjectItemByProjectId(projectId);
		projectRepository.addProjectItem(projectItem);
	}

	@Override
	public List<ProjectItem> getProjectItemByProjectId(Long projectId, Long languageId) {
		List<ProjectItem> listProjectItem = projectRepository.getProjectItemByProjectId(projectId, languageId);
		return listProjectItem;
	}

	@Override
	public boolean checkListAffected(Project project, Boolean insertFlg, int maxLength) {
		boolean hasProblem = false;
		Timestamp systemDate = FunctionCommon.getCurrentTime();
		String dbType = simpleMapCodeList.asMap().get(project.getDbType().toString());
		String dbTypeOld = simpleMapCodeList.asMap().get(project.getDbTypeOld().toString());
		Long projectId = project.getProjectId();
		Long accountId = project.getUpdatedBy();

		List<ProblemList> listOfAllProblem = new ArrayList<ProblemList>();
		Long getSQLDesignAdvanceByProjectId  = sqlDesignRepository.getSQLDesignAdvanceByProjectId(projectId);

		if (DataTypeUtils.equals(project.getDbType(), DbDomainConst.DatabaseType.ORACLE)) {

			List<TableDesign> listOfTableDesignsProblem = new ArrayList<TableDesign>();
			List<TableDesignDetails> listOfColumnCodeProblem = new ArrayList<TableDesignDetails>();
			List<SqlDesign> listOfSQLDesignProblem = new ArrayList<SqlDesign>();

			List<TableDesign> listOfTableDesigns = tableDesignRepository.getTableDesignByProjectId(projectId);

			List<SqlDesign> listOfSQLDesign = sqlDesignRepository.getSQLDesignByProjectId(projectId);
			
			List<TableDesignDetails> listOfAllColumnCode = null;

			if (CollectionUtils.isNotEmpty(listOfTableDesigns)) {

				for (TableDesign tableDesign : listOfTableDesigns) {
					if (tableDesign.getTableCode().length() > maxLength) {
						listOfTableDesignsProblem.add(tableDesign);
					}
				}

				listOfAllColumnCode = tableDesignDetailRepository.getAllTableColumnByProject(projectId);
				if (CollectionUtils.isNotEmpty(listOfAllColumnCode)) {
					for (TableDesignDetails tableDesignDetail : listOfAllColumnCode) {
						if (tableDesignDetail.getCode().length() > maxLength) {
							listOfColumnCodeProblem.add(tableDesignDetail);
						}
					}
				}
			}
			if (CollectionUtils.isNotEmpty(listOfSQLDesign)) {
				for (SqlDesign sqlDesign : listOfSQLDesign) {
					if (sqlDesign.getSqlDesignCode().length() > maxLength) {
						listOfSQLDesignProblem.add(sqlDesign);
					}
				}
			}
			// Add list problem

			if (FunctionCommon.isNotEmpty(listOfTableDesignsProblem)) {
				hasProblem = true;
				if (insertFlg) {
					for (TableDesign tableDesign : listOfTableDesignsProblem) {
						ProblemList problemList = new ProblemList();
						problemList.setProblemName(MessageUtils.getMessage(TableDesignMessageConst.INF_TABLEDESIGN_0001, MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0002), tableDesign.getTableCode(), dbType));
						problemList.setResourceType(DbDomainConst.ResourceType.OTHER);
						problemList.setResourceId(tableDesign.getTableDesignId());
						problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
						problemList.setUrlId(DbDomainConst.ResourceURL.URL_TABLE_DESIGN);

						problemList.setFromResourceType(DbDomainConst.FromResourceType.PROJECT_CHANGE_DB_TYPE);
						problemList.setFromResourceId(projectId);

						problemList.setProjectId(projectId);
						problemList.setCreatedBy(accountId);
						problemList.setCreatedDate(systemDate);
						listOfAllProblem.add(problemList);
					}
				}
			}

			if (FunctionCommon.isNotEmpty(listOfColumnCodeProblem)) {
				hasProblem = true;
				if (insertFlg) {
					for (TableDesignDetails tableDesignDetails : listOfColumnCodeProblem) {
						ProblemList problemList = new ProblemList();
						problemList.setProblemName(MessageUtils.getMessage(TableDesignMessageConst.INF_TABLEDESIGN_0001, MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0012), tableDesignDetails.getCode(), dbType));
						problemList.setResourceType(DbDomainConst.ResourceType.OTHER);
						problemList.setResourceId(tableDesignDetails.getTableDesignId());
						problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
						problemList.setUrlId(DbDomainConst.ResourceURL.URL_TABLE_DESIGN);
						/* problemList.setModuleId(tableDesign.getModuleId()); */

						problemList.setFromResourceType(DbDomainConst.FromResourceType.PROJECT_CHANGE_DB_TYPE);
						problemList.setFromResourceId(projectId);

						problemList.setProjectId(projectId);
						problemList.setCreatedBy(accountId);
						problemList.setCreatedDate(systemDate);
						listOfAllProblem.add(problemList);
					}
				}
			}

			if (FunctionCommon.isNotEmpty(listOfSQLDesignProblem)) {
				hasProblem = true;
				if (insertFlg) {
					for (SqlDesign sqlDesign : listOfSQLDesignProblem) {
						ProblemList problemList = new ProblemList();
						problemList.setProblemName(MessageUtils.getMessage(TableDesignMessageConst.INF_TABLEDESIGN_0001, MessageUtils.getMessage(SqlDesignMessageConst.SC_SQLDESIGN_SQL_DESIGN_CODE), sqlDesign.getSqlDesignCode(), dbType));
						problemList.setResourceType(DbDomainConst.ResourceType.SQL_DESIGN);
						problemList.setResourceId(sqlDesign.getSqlDesignId());
						problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
						problemList.setUrlId(DbDomainConst.ResourceURL.URL_SQL_DESIGN);

						problemList.setFromResourceType(DbDomainConst.FromResourceType.PROJECT_CHANGE_DB_TYPE);
						problemList.setFromResourceId(projectId);

						/* problemList.setModuleId(tableDesign.getModuleId()); */
						problemList.setProjectId(projectId);
						problemList.setCreatedBy(accountId);
						problemList.setCreatedDate(systemDate);
						listOfAllProblem.add(problemList);
					}
				}
			}
			

			project.setListOfTableDesign(listOfTableDesignsProblem);
			project.setListOfSqlDesign(listOfSQLDesignProblem);
		}else{
			// Delete problem when change Oracle tp Postgres
			problemListRepository.deleteFromResourceTypeOfProject(DbDomainConst.FromResourceType.PROJECT_CHANGE_DB_TYPE, projectId);
		}
		
		if(getSQLDesignAdvanceByProjectId != null && getSQLDesignAdvanceByProjectId.intValue() > 0){
			problemListRepository.deleteFromResourceTypeOfProject(DbDomainConst.FromResourceType.PROJECT_CHANGE_DB_TYPE_SQL, projectId);
			ProblemList problemList = new ProblemList();
			problemList.setProblemName(MessageUtils.getMessage(ProjectMessageConst.SC_PROJECT_0048, dbTypeOld, dbType));
			problemList.setResourceType(DbDomainConst.ResourceType.OTHER);
			problemList.setResourceId(projectId);
			problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
			problemList.setUrlId(DbDomainConst.ResourceURL.URL_TABLE_DESIGN);
			
			problemList.setFromResourceType(DbDomainConst.FromResourceType.PROJECT_CHANGE_DB_TYPE_SQL);
			problemList.setFromResourceId(projectId);
			
			problemList.setProjectId(projectId);
			problemList.setCreatedBy(accountId);
			problemList.setCreatedDate(systemDate);
			listOfAllProblem.add(problemList);
			problemListRepository.multiRegisterProblem(listOfAllProblem);
		}

		if (insertFlg) {
			problemListRepository.deleteFromResourceTypeOfProject(DbDomainConst.FromResourceType.PROJECT_CHANGE_DB_TYPE, projectId);
			if (insertFlg && hasProblem) {
				problemListRepository.multiRegisterProblem(listOfAllProblem);
			}
		}
		
		return hasProblem;
	}

	@Override
	public Map<String, String> findStyleByProjectId(Long projectId) {
		List<ProjectTheme> lstTheme = projectRepository.findThemeByProjectId(projectId);

		if (CollectionUtils.isEmpty(lstTheme)) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(ProjectMessageConst.SC_PROJECT_0010)));
		}

		HashMap<String, String> mapTheme = new HashMap<String, String>();
		for (ProjectTheme projectTheme : lstTheme) {
			mapTheme.put(projectTheme.getCode(), projectTheme.getValue());
		}
		return mapTheme;
	}

	
	private GraphicDbDesign initQpTable(Project project, LanguageDesign languageDesign) {
		GraphicDbDesign graphicDbDesign = new GraphicDbDesign();
		graphicDbDesign.setProject(project);
		InputStream inputStream = null;
		try {
			AccountProfile accountProfile = systemService.getDefaultProfile();
			String dataType = String.valueOf(project.getDbType());
			GenerateUniqueKey generateUniqueKey = new GenerateUniqueKey(DbDomainConst.MAX_LENGTH_OF_NAME, accountProfile.getSqlCodeMaxLengthByDbType(dataType));

			SubjectArea sub = new SubjectArea();
			sub.setAreaName(generateUniqueKey.calculateCodeForManual(MessageUtils.getMessage("cl.sys.0015"), StringUtils.EMPTY));
			sub.setAreaCode("qpCommonTable");
			sub.setCreatedBy(project.getCreatedBy());
			sub.setCreatedDate(project.getCreatedDate());
			sub.setUpdatedBy(project.getCreatedBy());
			sub.setUpdatedDate(project.getCreatedDate());
			sub.setProjectId(project.getProjectId());
			sub.setItemSeqNo(1);
			sub.setDefaultFlg(DbDomainConst.YesNoFlg.NO);
			areaRepository.insertSubAreaInfor(sub);
			graphicDbDesign.setSubjectAreaId(sub.getAreaId());

			inputStream = getClass().getClassLoader().getResourceAsStream("/META-INF/templatescript/initQPTable.xml");
			graphicDbDesign.setXml(IOUtils.toString(inputStream, StandardCharsets.UTF_8));
			graphicDbDesignService.modifyTableByGraphicDesign(graphicDbDesign, false, null, null, true, project.getCreatedBy(), languageDesign, DbDomainConst.YesNoFlg.NO);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
		return graphicDbDesign;
	}

	private void initDefaultFooterRight(Project project, LanguageDesign languageDesign) {
		List<ProjectItem> listProjectItem = this.getProjectItemByProjectId(project.getProjectId(), languageDesign.getLanguageId());
		//DungNN - check null
		if (CollectionUtils.isEmpty(listProjectItem)) {
			listProjectItem = new ArrayList<ProjectItem>();
		}

		ProjectItem item = new ProjectItem();
		item.setItemPosition(5);
		item.setItemType(0);
		item.setMessageString(MessageUtils.getMessage(ProjectMessageConst.SC_PROJECT_0035));
		item.setMessageCode(MessageUtils.getMessage(ProjectMessageConst.SC_PROJECT_0035));
		item.setProjectId(project.getProjectId());

		listProjectItem.add(item);
		this.addProjectItem(project.getProjectId(), listProjectItem);
	}



	private void initMultipartFileManagement(Long projectId, Long accountId) {
		// Get list from XML initial file
		CommonModel commonModel = new CommonModel();
		commonModel.setCreatedBy(accountId);
		commonModel.setWorkingProjectId(projectId);
		
		List<ExternalObjectDefinition> lstExternalObjectDefinition = converFromXMLToInitMultipartFileManagementLst().getListOfExternalObjectDefinition();
		if(CollectionUtils.isNotEmpty(lstExternalObjectDefinition)){
			for (ExternalObjectDefinition externalObjectDefinition : lstExternalObjectDefinition) {
				externalObjectDefinitionService.registerExternalObjectDefinition(externalObjectDefinition, commonModel);
			}
		}
	}

	private InitExternalObjectDefinitionManagement converFromXMLToInitMultipartFileManagementLst() {
		JAXBContext jaxbContext;
		InputStream is = null;
		InitExternalObjectDefinitionManagement initExternalObjectDefinitionManagement = null;
		try {
			jaxbContext = JAXBContext.newInstance(InitExternalObjectDefinitionManagement.class);
			is = getClass().getClassLoader().getResourceAsStream("/META-INF/templatescript/initExternalObjectDefinition.xml");
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			// convert xml to object
			initExternalObjectDefinitionManagement = (InitExternalObjectDefinitionManagement) jaxbUnmarshaller.unmarshal(is);

		} catch (JAXBException e) {
			e.printStackTrace();
			throw new BusinessException(ResultMessages.error().add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0100));
		} finally {
			if (is != null) {
				IOUtils.closeQuietly(is);
			}
		}

		return initExternalObjectDefinitionManagement;
	}

	/**
	 * Process initial session management
	 * @param project
	 * @param graphicDbDesign
	 */
	private void initSessionManagement(Long projectId, Long accountId, GraphicDbDesign graphicDbDesign){
		// Get list from XML initial file
		List<SessionManagement> listSessionManagement =  converFromXMLToInitSessionManagementList().getListOfSessionManagements();

		// Modify list from XML initial file
		for(int i=0 ; i < listSessionManagement.size() ; i++){
			SessionManagement temp =  listSessionManagement.get(i);
			String sessionName =  temp.getSessionManagementName();

			// add common table ID
			if(sessionName.equals(SystemConst.ACCOUNT_INFOR) || sessionName.equals(SystemConst.ACCOUNT_PROFILE)){
				for(TableDesign tableDesign : graphicDbDesign.getTableDesigns()){
					// Only process for qp_account and qp_account_profile
					if((tableDesign.getTableCode().equals("qp_account") && sessionName.equals(SystemConst.ACCOUNT_INFOR)) || (tableDesign.getTableCode().equals("qp_account_profile") && sessionName.equals(SystemConst.ACCOUNT_PROFILE))){
						temp.setObjectId(tableDesign.getTableDesignId());
					}
				}
			}
			// Add create, update infor and project id
			Timestamp systemDate = FunctionCommon.getCurrentTime();
			temp.setCreatedBy(accountId);
			temp.setCreatedDate(systemDate);
			temp.setUpdatedDate(systemDate);
			temp.setProjectId(projectId);
			temp.setSessionManagementType(SessionManagementMessageConst.TYPE_COMMON);

			listSessionManagement.set(i, temp);
		}

		// Call service to insert data into database
		sessionManagementRepository.registerListOfSessionManagement(listSessionManagement);
	}

	/**
	 * Get initial data for session management from XML
	 * @return
	 * @throws BusinessException
	 */
	public InitSessionManagement converFromXMLToInitSessionManagementList() throws BusinessException {
		JAXBContext jaxbContext;
		InputStream is = null;
		InitSessionManagement sessionManagement = null;
		try {
			jaxbContext = JAXBContext.newInstance(InitSessionManagement.class);
			is = getClass().getClassLoader().getResourceAsStream("/META-INF/templatescript/initSessionManagement.xml");
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			// convert xml to object
			sessionManagement = (InitSessionManagement) jaxbUnmarshaller.unmarshal(is);

		} catch (JAXBException e) {
			e.printStackTrace();
			throw new BusinessException(ResultMessages.error().add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0100));
		} finally {
			if (is != null) {
				IOUtils.closeQuietly(is);
			}
		}
		return sessionManagement;
	}
	
	/**
	 * Process initial logging management
	 * @param project
	 */
	private void initLoggingManagement(Long projectId, Long accountId){
		// Get list from XML initial file
		List<Log> listLoggingManagement =  converFromXMLToInitLogginManagementList().getListOfSessionManagements();
		// Modify list from XML initial file
		for (Log temp : listLoggingManagement) {
			// Add create, update infor and project id
			Timestamp systemDate = FunctionCommon.getCurrentTime();
			temp.setCreatedBy(accountId);
			temp.setUpdatedBy(accountId);
			temp.setCreatedDate(systemDate);
			temp.setUpdatedDate(systemDate);
			temp.setSystemTime(systemDate);
			temp.setProjectId(projectId);
			// Call service to insert data into database
			loggingManagementService.registerDefaultLog(temp);
		}
	}

	/**
	 * Get initial data for logging management from XML
	 * @return
	 * @throws BusinessException
	 */
	public InitLoggingManagement converFromXMLToInitLogginManagementList() throws BusinessException {
		JAXBContext jaxbContext;
		InputStream is = null;
		InitLoggingManagement loggingManagement = null;
		try {
			jaxbContext = JAXBContext.newInstance(InitLoggingManagement.class);
			is = getClass().getClassLoader().getResourceAsStream("/META-INF/templatescript/initLoggingManagement.xml");
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			// convert xml to object
			loggingManagement = (InitLoggingManagement) jaxbUnmarshaller.unmarshal(is);

		} catch (JAXBException e) {
			e.printStackTrace();
			throw new BusinessException(ResultMessages.error().add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0100));
		} finally {
			if (is != null) {
				IOUtils.closeQuietly(is);
			}
		}
		return loggingManagement;
	}

}
