package org.terasoluna.qp.domain.service.module;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.DbDomainConst.ProblemType;
import org.terasoluna.qp.app.common.constants.TerasolunaQPValidationConst;
import org.terasoluna.qp.app.common.ultils.DateUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.ModuleMessageConst;
import org.terasoluna.qp.app.message.ProblemListMessageConst;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.DomainDesign;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.ModuleTableMapping;
import org.terasoluna.qp.domain.model.ProblemList;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.repository.businessdesign.BusinessDesignRepository;
import org.terasoluna.qp.domain.repository.domaindesign.DomainDesignRepository;
import org.terasoluna.qp.domain.repository.functiondesign.FunctionDesignRepository;
import org.terasoluna.qp.domain.repository.module.ModuleCriteria;
import org.terasoluna.qp.domain.repository.module.ModuleRepository;
import org.terasoluna.qp.domain.repository.moduletablemapping.ModuleTableMappingRepository;
import org.terasoluna.qp.domain.repository.problemlist.ProblemListRepository;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignRepository;
import org.terasoluna.qp.domain.service.generatescreen.ScreenDesignDefault;
import org.terasoluna.qp.domain.service.generatescreen.ScreenDesignGeneratorService;
import org.terasoluna.qp.domain.service.project.ProjectService;

@Service
@Transactional
public class ModuleServiceImpl implements ModuleService {

	@Inject
	ModuleRepository moduleRepository;

	@Inject
	ModuleTableMappingRepository moduleTableMappingRepository;

	@Inject
	Mapper beanMapper;

	@Inject
	ScreenDesignGeneratorService screenDesignGeneratorService;

	@Inject
	ProjectService projectService;

	@Inject
	FunctionDesignRepository functionDesignRepository;
	
	@Inject
	TableDesignRepository tableDesignRepository;
	
	@Inject
	ScreenDesignRepository screenDesignRepository;
	
	@Inject
	BusinessDesignRepository businessDesignRepository;
	
	@Inject
	DomainDesignRepository domainDesignRepository;
	
	@Inject
	SqlDesignRepository sqlDesignRepository;
	
	@Inject
	ProblemListRepository problemListRepository;

	private static final String MESSAGE_REF_COUNT = "message_ref_count";

	private static final String SCREEN_REF_COUNT = "screen_ref_count";

	private static final String AUTOCOMPLETE_REF_COUNT = "autocomplete_ref_count";

	private static final String CODELIST_REF_COUNT = "codelist_ref_count";

	private static final String MODULE_TABLE_MAPPING_REF_COUNT = "module_table_mapping_ref_count";

	/**
	 * Finds all module information by search condition
	 * 
	 * @param criteria
	 *            moduleCriteria
	 * @param pageable
	 *            Pageable
	 * @return List of modules
	 */
	@Override
	public Page<Module> searchModule(ModuleCriteria moduleCriteria, Pageable pageable) {
		long totalCount = moduleRepository.countByCriteria(moduleCriteria);

		List<Module> modules;
		if (0 < totalCount) {
			modules = moduleRepository.findPageByCriteria(moduleCriteria, pageable);
		} else {
			modules = Collections.emptyList();
		}

		Page<Module> page = new PageImpl<Module>(modules, pageable, totalCount);

		return page;
	}

	/**
	 * Finds all table mappong with module
	 * 
	 * @param moduleId
	 *            Long
	 * @return ModuleTableMapping[]
	 */
	@Override
	public ModuleTableMapping[] findAllTableInModule(Long moduleId) {
		// Finds all related module table mapping information to module
		return moduleTableMappingRepository.findModuleTableMappingByModuleId(moduleId);
	}

	/**
	 * Find Module by Id
	 * 
	 * @param moduleId
	 *            Long
	 * @return module Module
	 */
	@Override
	public Module findModuleById(Long moduleId) {
		Module module = moduleRepository.findById(moduleId);
		if (module == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0005)));
		}

		return module;
	}

	private Module validate(Long moduleId, boolean checkModuleStatus, CommonModel common) {
		Module module = findModuleById(moduleId);

		if (checkModuleStatus && DbDomainConst.DesignStatus.FIXED.equals(module.getStatus())) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0111, StringUtils.lowerCase(MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0019)), module.getModuleName()));
		}

		common.setProjectId(module.getProjectId());
		projectService.validateProject(common);
		return module;
	}

	/**
	 * Register a module
	 * 
	 * @param module
	 *            Module
	 */
	@Override
	public void registerModule(Module module, CommonModel common) {
		
		common.setProjectId(module.getProjectId());
		common.setDesignStatus(true);
		
		Project project = projectService.validateProject(common);
		
		// Check module code is duplicated or not?
		Long totalCount = moduleRepository.countNameCodeByModuleId(module);
		ResultMessages resultMessages = ResultMessages.error();

		if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0007));
		} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0008));
		} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0007));
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0008));
		}
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {
			Timestamp currentTime = FunctionCommon.getCurrentTime();
			// Setting user information/ first creation date
			module.setCreatedDate(currentTime);
			module.setUpdatedDate(currentTime);
			moduleRepository.register(module);

			if (module.getModuleType().equals(DbDomainConst.FunctionType.ONLINE) && DbDomainConst.DefaultGenerateSetting.NORMAL.equals(module.getDefaultGenerationSetting())) {
				// Register module table mapping
				ModuleTableMapping[] moduleTableMapping = module.getModuleTableMappings();

				if (ArrayUtils.isNotEmpty(moduleTableMapping)) {
					for (int i = 0; i < module.getModuleTableMappings().length; i++) {
						module.getModuleTableMappings()[i].setModuleId(module.getModuleId());
						// Setting user information/ first creation date
						module.getModuleTableMappings()[i].setCreatedBy(module.getCreatedBy());
						module.getModuleTableMappings()[i].setCreatedDate(currentTime);
						module.getModuleTableMappings()[i].setUpdatedBy(module.getUpdatedBy());
						module.getModuleTableMappings()[i].setUpdatedDate(currentTime);
					}
					moduleTableMappingRepository.registerArray(module.getModuleTableMappings());

					// Convert module object before do generate operation
					ScreenDesignDefault screenDesignDefault = new ScreenDesignDefault();
					module.setProject(project);
					screenDesignDefault.toModule(module);

					screenDesignDefault.setCreatedBy(module.getCreatedBy());
					screenDesignDefault.setCreatedDate(currentTime);
					screenDesignDefault.setProjectId(module.getProjectId());
					screenDesignDefault.setProject(project);
					screenDesignDefault.setLanguageDesign(module.getLanguageDesign());
					screenDesignDefault.setCommonModel(common);

					// Generate default screens for new module
					screenDesignGeneratorService.generateDefaultScreen(screenDesignDefault, false);
					// Generate default business logic design for new module
					screenDesignGeneratorService.generateDefaultBusinesslogic(screenDesignDefault);
				}
			}
		}
	}

	/**
	 * Modify a module
	 * 
	 * @param module
	 *            Module
	 */
	@Override
	public void modifyModule(Module module, CommonModel common) {
		//Module md = moduleRepository.findById(module.getModuleId());
		common.setDesignStatus(true);
		Module moduleForUpdate = validate(module.getModuleId(), true, common);
		// Check module code is duplicated or not?
		module.setProjectId(moduleForUpdate.getProjectId());
		Long totalCount = moduleRepository.countNameCodeByModuleId(module);
		ResultMessages resultMessages = ResultMessages.error();

		if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0007));
		} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0008));
		} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0007));
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0008));
		}
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {
			// Setting user information/ last modified date
			moduleForUpdate.setBusinessTypeId(module.getBusinessTypeId());
			moduleForUpdate.setModuleName(module.getModuleName());
			moduleForUpdate.setModuleCode(module.getModuleCode());
			moduleForUpdate.setRemark(module.getRemark());
			moduleForUpdate.setUpdatedBy(module.getUpdatedBy());
			moduleForUpdate.setUpdatedDate(module.getUpdatedDate());
			moduleForUpdate.setSysDatetime(FunctionCommon.getCurrentTime());
			moduleForUpdate.setCompletionType(module.getCompletionType());
			moduleForUpdate.setConfirmationType(module.getConfirmationType());
			moduleForUpdate.setModuleType(moduleForUpdate.getModuleType());
			// modify module
			if (moduleRepository.modify(moduleForUpdate) <= 0) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0005)));
			}
		}
	}

	/**
	 * Delete a business type
	 * 
	 * @param businessType
	 *            BusinessType
	 */
	@Override
	public void deleteModule(Module module, CommonModel common) {

		common.setDesignStatus(true);
		Module moduleForDelete = validate(module.getModuleId(), true, common);
		
		if (DateUtils.compare(moduleForDelete.getUpdatedDate(), module.getUpdatedDate()) == 1) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
		}

		HashMap<String, Long> referenceHashMap = moduleRepository.countReferenceByModuleId(module.getModuleId());
		ResultMessages resultMessages = ResultMessages.error();
		if (0 < referenceHashMap.get(MESSAGE_REF_COUNT)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0097, MessageUtils.getMessage(CommonMessageConst.TQP_MESSAGEDESIGN));
		}
		if (0 < referenceHashMap.get(SCREEN_REF_COUNT)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0097, MessageUtils.getMessage(CommonMessageConst.TQP_SCREENDESIGN));
		}
		if (0 < referenceHashMap.get(AUTOCOMPLETE_REF_COUNT)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0097, MessageUtils.getMessage(CommonMessageConst.TQP_AUTOCOMPLETE));
		}
		if (0 < referenceHashMap.get(CODELIST_REF_COUNT)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0097, MessageUtils.getMessage(CommonMessageConst.TQP_CODELIST));
		}
		if (0 < referenceHashMap.get(MODULE_TABLE_MAPPING_REF_COUNT)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0097, MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0006));
		}
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {
			
			moduleRepository.delete(moduleForDelete);
		}
	}
	
	private ProblemList makeProblemList(Module module, CommonModel common) {
		ProblemList problemList;
		problemList = new ProblemList();
		problemList.setFromResourceType(DbDomainConst.FromResourceType.MODULE_DELETE);
		problemList.setFromResourceId(module.getModuleId());
		problemList.setProblemType(ProblemType.UNMATCHED);
		problemList.setProjectId(common.getWorkingProjectId());
		problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
		problemList.setCreatedBy(module.getCreatedBy());
		problemList.setCreatedDate(FunctionCommon.getCurrentTime());
		return problemList;
	}
	
	@Override
	public List<Module> findByAllPermission() {
		return moduleRepository.findByAllPermission();
	}

	/**
	 * Finds all module information without condition
	 * 
	 * @return List of modules
	 */
	@Override
	public Collection<Module> findAllModule() {
		return moduleRepository.findAll();
	}

	@Override
	public void deleteAssociatedModule(Module moduleForm, CommonModel common) {
		// TODO Auto-generated method stub
		common.setDesignStatus(true);
		
		Module module = validate(moduleForm.getModuleId(), true, common);
		
		if (DateUtils.compare(module.getUpdatedDate(), moduleForm.getUpdatedDate()) == 1) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
		}
		
		this.findAllDeletionAffection(module, common);
		ProblemList problemList = null;
		List<ProblemList> problems = new ArrayList<ProblemList>();
		for(BusinessDesign businessDesign : module.getAffectedBusinessDesigns()){
			problemList = this.makeProblemList(module, common);
			problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
			problemList.setResourceId(businessDesign.getBusinessLogicId());
			problemList.setModuleId(businessDesign.getModuleId());
			problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0021,module.getModuleName()));
			problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
			problems.add(problemList);
		}
		for(ScreenDesign screenDesign : module.getAffectedScreenDesigns()){
			problemList = this.makeProblemList(module, common);
			problemList.setResourceType(DbDomainConst.ResourceType.SCREEN_DESIGN);
			problemList.setResourceId(screenDesign.getScreenId());
			problemList.setModuleId(screenDesign.getModuleId());
			problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0021,module.getModuleName()));
			problemList.setUrlId(DbDomainConst.ResourceURL.URL_SCREEN_DESIGN);
			problems.add(problemList);
		}
		for(DomainDesign domainDesign : module.getAffectedDomainDesigns()){
			problemList = this.makeProblemList(module, common);
			problemList.setResourceType(DbDomainConst.ResourceType.DOMAIN_DESIGN);
			problemList.setResourceId(domainDesign.getDomainId());
			problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0021,module.getModuleName()));
			problemList.setUrlId(DbDomainConst.ResourceURL.URL_DOMAIN_DESIGN);
			problems.add(problemList);
		}
		for(TableDesign tableDesign : module.getAffectedTableDesigns()){
			problemList = this.makeProblemList(module, common);
			problemList.setResourceType(DbDomainConst.ResourceType.TABLE_DESIGN);
			problemList.setResourceId(tableDesign.getTableDesignId());
			problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0021,module.getModuleName()));
			problemList.setUrlId(DbDomainConst.ResourceURL.URL_TABLE_DESIGN);
			problems.add(problemList);
		}
		for(SqlDesign sqlDesign : module.getAffectedSqlDesigns()){
			problemList = this.makeProblemList(module, common);
			problemList.setResourceType(DbDomainConst.ResourceType.SQL_DESIGN);
			problemList.setResourceId(sqlDesign.getSqlDesignId());
			problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0021,module.getModuleName()));
			problemList.setUrlId(DbDomainConst.ResourceURL.URL_SQL_DESIGN);
			problems.add(problemList);
		}
		if(CollectionUtils.isNotEmpty(problems)){
			problemListRepository.multiRegisterProblem(problems);
		}
		moduleRepository.deleteAssociatedModule(module);
	}

	@Override
	public void modifyDesignStatus(Module module, CommonModel common) {
		// check exist and validate project
		common.setDesignStatus(true);
		Module moduleFromDB = validate(module.getModuleId(), false, common);

		if (DateUtils.compare(moduleFromDB.getUpdatedDate(), module.getUpdatedDate()) == 1) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
		}

		// if convert from under to fixed then check all child was fixed and has not problem
		if (DbDomainConst.DesignStatus.UNDER_DESIGN.equals(module.getStatus())) {
			if (validateChangeStatusToFixed(module.getModuleId()) > 0) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0117, MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0005)));
			}
		}

		Timestamp currentTime = FunctionCommon.getCurrentTime();
		module.setUpdatedDate(module.getUpdatedDate());
		module.setSysDatetime(currentTime);

		if (DbDomainConst.DesignStatus.UNDER_DESIGN.equals(module.getStatus())) {
			module.setStatus(DbDomainConst.DesignStatus.FIXED);
		} else {
			module.setStatus(DbDomainConst.DesignStatus.UNDER_DESIGN);
		}

		int count = moduleRepository.modifyDesignStatus(module);
		// check Concurrence
		if (count <= 0) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0005)));
		}
	}

	@Override
	public Module validateModule(Long moduleId, Long accountId, Long workingProjectId, boolean checkDesignStatus) {
		
		CommonModel commonModel = new CommonModel();
		commonModel.setDesignStatus(checkDesignStatus);
		commonModel.setCreatedBy(accountId);
		commonModel.setWorkingProjectId(workingProjectId);
		
		Module module = validate(moduleId, checkDesignStatus, commonModel);
		// if check design status
		if (checkDesignStatus && DbDomainConst.DesignStatus.FIXED.equals(module.getStatus())) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0111, StringUtils.lowerCase(MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0019)), module.getModuleName()));
		}
		return module;
	}

	@Override
	public Module validateModule(Long moduleId, Long accountId, Long workingProjectId) {
		return validateModule(moduleId, accountId, workingProjectId, false);
	}

	/**
	 * Check design status in child function of Module
	 * 
	 * @param Module
	 *            Id
	 * @author sonld
	 */
	@Override
	public long validateChangeStatusToFixed(Long moduleId) {
		return moduleRepository.validateChangeStatusToFixed(moduleId);
	}

	/**
	 * Get List module dependency
	 * 
	 * @param Module
	 *            Id
	 * @param Project
	 *            Id
	 * @author sonld
	 */
	@Override
	public List<Module> findListModuleDependency(Long moduleId, Long projectId) {
		List<Module> listModuleDependency = moduleRepository.findListModuleDependency(moduleId, projectId);
		return listModuleDependency;
	}

	@Override
	public List<Module> getAllModule(Long projectId, List<Long> listModuleId) {
		return moduleRepository.getAllModule(projectId, listModuleId);
	}

	@Override
	public List<Module> findAllModuleOfOnline(Long projectId, Integer moduleType) {
		return moduleRepository.findAllModuleOfOnline(projectId, moduleType);
	}
	
	@Override
	public List<Module> findAllModule(Long projectId, Integer status) {
		return moduleRepository.findAllModuleOfProject(projectId, status);
	}

	@Override
	public void findAllDeletionAffection(Module module, CommonModel common) {
		if(module != null){
			module.setAffectedScreenDesigns(screenDesignRepository.findAllAffactedScreenDesignsByModuleId(module.getModuleId(), common.getWorkingLanguageId()));
			module.setAffectedTableDesigns(tableDesignRepository.findAllTableDesignsByModuleId(module.getModuleId()));
			module.setAffectedBusinessDesigns(businessDesignRepository.findAllBusinessLogicsByModuleId(module.getModuleId()));
			module.setAffectedDomainDesigns(domainDesignRepository.findAllDomainDesignsByModuleId(module.getModuleId(), common.getWorkingProjectId()));
			module.setAffectedSqlDesigns(sqlDesignRepository.findAllAffactedSqlDesignsByModuleId(module.getModuleId()));
		}
	}

	@Override
	public Module validateModule(Long moduleId, CommonModel commonModel) {
		Module module = findModuleById(moduleId);

		if (commonModel.getDesignStatus() && DbDomainConst.DesignStatus.FIXED.equals(module.getStatus())) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0109, module.getModuleName()));
		}
		commonModel.setProjectId(module.getProjectId());
		projectService.validateProject(commonModel);

		return module;
	}
}
