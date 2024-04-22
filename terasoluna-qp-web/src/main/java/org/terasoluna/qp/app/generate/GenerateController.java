package org.terasoluna.qp.app.generate;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.qp.app.common.BaseController;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.project.ProjectForm;
import org.terasoluna.qp.app.tabledesign.TableDesignSearchForm;
import org.terasoluna.qp.domain.model.Log;
import org.terasoluna.qp.domain.model.LogDetail;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignCriteria;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignRepository;
import org.terasoluna.qp.domain.service.generateddl.GenerateOracleDDLService;
import org.terasoluna.qp.domain.service.generateddl.GeneratePosgreDDLService;
import org.terasoluna.qp.domain.service.loggingmanagement.LoggingManagementService;
import org.terasoluna.qp.domain.service.project.ProjectService;
import org.terasoluna.qp.domain.service.tabledesign.TableDesignService;

@Controller
@RequestMapping(value = "generation")
@TransactionTokenCheck(value = "generation")
public class GenerateController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(GenerateController.class);
	private static final String GENERATE_FORM_NAME = "generateForm";
	
	private static final String PROJECT_FORM_NAME = "projectForm";
	private static final String GENERATE_FORM_PATH = "generate/generateDDL";
	private static final String GENERATE_GUIDELINE = "generate/guideline";
	private static final String GENERATE_SEARCH_TABLE = "generation/searchTable";
	private static final String SEARCH_LINK = "generate/generateDDLSearchTable";
	
	public static final Integer ALL = 0;
	public static final Integer CUSTOM_TABLE = 1;
	public static final Integer ALL_FOR_LOG = 2;
	
	private static final Integer DATABASE_TYPE = 3;
	private static final Integer ENABLE_STATUS = 1;
	
	@Inject
	ProjectService projectService;

	
	@Inject
	GeneratePosgreDDLService generatePosgreDDLService;

	@Inject
	GenerateOracleDDLService generateOracleDDLService;
	
	@Inject
	TableDesignService tableDesignService;
	
	@Inject
	TableDesignRepository tableDesignRepository;
	
	@Inject
	LoggingManagementService loggingManagementService;
	
	@Inject
	Mapper beanMapper;

	/**
	 * Identifies methods which initialize the WebDataBinder which will be used for populating command and form object
	 * @param webDataBinder WebDataBinder
	 */
	@InitBinder(GENERATE_FORM_NAME)
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	/**
	 * Identifies methods which initialize the generate form object
	 * @return generateForm
	 */
	@ModelAttribute
	public GenerateForm setUpProjectForm() {
		GenerateForm generateForm = new GenerateForm();
		logger.debug("Init form {0}", generateForm);
		return generateForm;
	}
	
	/**
	 * Initialize generate screen
	 * @return GENERATE_FORM_PATH
	 */
	@RequestMapping(value = "generateddl", method = RequestMethod.GET)
	public String displayGenerate(GenerateForm generateForm, Model model) {
		ProjectForm projectForm = new ProjectForm();
		Project project = projectService.getProjectInformation(SessionUtils.getCurrentProjectId(), SessionUtils.getAccountId());
		projectForm = beanMapper.map(project, ProjectForm.class);
		model.addAttribute(PROJECT_FORM_NAME, projectForm);
		
		if (loggingManagementService.findLogByProjectIdAndTypeAndStatus(SessionUtils.getCurrentProjectId(), DATABASE_TYPE, ENABLE_STATUS) != null){
			generateForm.setDatabaseLog(true);
		} else {
			generateForm.setDatabaseLog(false);
		}
		
		return GENERATE_FORM_PATH;
	}
	
	/**
	 * Initialize generate screen
	 * @return GENERATE_FORM_PATH
	 */
	@RequestMapping(value = "generateddl", method = RequestMethod.POST)
	public String processGenerate(GenerateForm generateForm, Model model, RedirectAttributes redirectAttr, @ModelAttribute TableDesignSearchForm tableDesignSearchForm, @PageableDefault Pageable pageable) {
		
		if(ALL.equals(generateForm.getGenerateMode())){
			try {
				ProjectForm projectForm = new ProjectForm();
				Project project = projectService.getProjectInformation(SessionUtils.getCurrentProjectId(), SessionUtils.getAccountId());
				StringBuilder sqlScripts = new StringBuilder();
				if(DbDomainConst.DatabaseType.PostgreSQL.equals(project.getDbType())){
					sqlScripts = generatePosgreDDLService.generateSQL(project, SessionUtils.getCurrentLanguageDesign(), generateForm.getGenerateMode().toString(), this.listTableId(generateForm.getListTableId()), generateForm.getGenDrop(), this.initCommon());
				}else if(DbDomainConst.DatabaseType.ORACLE.equals(project.getDbType())){
					sqlScripts = generateOracleDDLService.generateSQL(project, SessionUtils.getCurrentLanguageDesign(), generateForm.getGenerateMode().toString(), this.listTableId(generateForm.getListTableId()), generateForm.getGenDrop(), this.initCommon());
				}
				/*project.setSqlScripts(sqlScripts.toString());*/
				generateForm.setProjectId(project.getProjectId());
				generateForm.setSqlScripts(sqlScripts.toString());
				projectForm = beanMapper.map(project, ProjectForm.class);
				model.addAttribute(PROJECT_FORM_NAME, projectForm);
				model.addAttribute(GENERATE_FORM_NAME, generateForm);
			} catch (BusinessException be) {
				redirectAttr.addFlashAttribute("message", be.getResultMessages());
			}
			return GENERATE_FORM_PATH;
		} else if(ALL_FOR_LOG.equals(generateForm.getGenerateMode())){
			try {
				Log log = loggingManagementService.findLogByProjectIdAndTypeAndStatus(SessionUtils.getCurrentProjectId(), DATABASE_TYPE, ENABLE_STATUS);
				List<LogDetail> logDetail= loggingManagementService.findAllLogDetailByLogId(log.getLogId());
				ProjectForm projectForm = new ProjectForm();
				Project project = projectService.getProjectInformation(SessionUtils.getCurrentProjectId(), SessionUtils.getAccountId());
				StringBuilder sqlScripts = new StringBuilder();
				sqlScripts = generatePosgreDDLService.generateSQLForLogging(logDetail.get(0).getDbType());
				generateForm.setProjectId(project.getProjectId());
				generateForm.setSqlScripts(sqlScripts.toString());
				projectForm = beanMapper.map(project, ProjectForm.class);
				model.addAttribute(PROJECT_FORM_NAME, projectForm);
				model.addAttribute(GENERATE_FORM_NAME, generateForm);
			} catch (BusinessException be) {
				redirectAttr.addFlashAttribute("message", be.getResultMessages());
			}
			return GENERATE_FORM_PATH;
		} else{
			if(generateForm.getGenerateFrom().equals(0)){
				super.checkChangeProject(false);
				pageable = new PageRequest(pageable.getPageNumber(),PageSizeUtils.getPageSizeOfAction(GENERATE_SEARCH_TABLE),pageable.getSort());
				tableDesignSearchForm.setGenDrop(generateForm.getGenDrop());
				TableDesignCriteria tableCriteria = beanMapper.map(tableDesignSearchForm, TableDesignCriteria.class);
				tableCriteria.setProjectId(SessionUtils.getCurrentProjectId());
				List<TableDesign> tableDesigns = tableDesignRepository.findTableDDL(tableCriteria);
				
				Page<TableDesign> page = new PageImpl<TableDesign>(tableDesigns, null, tableDesigns.size());
				
				tableDesignSearchForm.setProjectId(SessionUtils.getCurrentProjectId());
				model.addAttribute("tableDesignSearchForm", tableDesignSearchForm);
				model.addAttribute("page", page);
				
				return SEARCH_LINK;
			}else{
				try {
					ProjectForm projectForm = new ProjectForm();
					Project project = projectService.getProjectInformation(SessionUtils.getCurrentProjectId(), SessionUtils.getAccountId());
					StringBuilder sqlScripts = new StringBuilder();
					if(DbDomainConst.DatabaseType.PostgreSQL.equals(project.getDbType())){
						sqlScripts = generatePosgreDDLService.generateSQL(project, SessionUtils.getCurrentLanguageDesign(), generateForm.getGenerateMode().toString(), this.listTableId(generateForm.getListTableId()), tableDesignSearchForm.getGenDrop(), this.initCommon());
					}else if(DbDomainConst.DatabaseType.ORACLE.equals(project.getDbType())){
						sqlScripts = generateOracleDDLService.generateSQL(project, SessionUtils.getCurrentLanguageDesign(), generateForm.getGenerateMode().toString(), this.listTableId(generateForm.getListTableId()), tableDesignSearchForm.getGenDrop(), this.initCommon());
					}
					/*project.setSqlScripts(sqlScripts.toString());*/
					generateForm.setProjectId(project.getProjectId());
					generateForm.setSqlScripts(sqlScripts.toString());
					projectForm = beanMapper.map(project, ProjectForm.class);
					model.addAttribute(PROJECT_FORM_NAME, projectForm);
					model.addAttribute(GENERATE_FORM_NAME, generateForm);
				} catch (BusinessException be) {
					redirectAttr.addFlashAttribute("message", be.getResultMessages());
				}
				return GENERATE_FORM_PATH;
			}
		}
	}
	
	@RequestMapping(value = "searchTable", method = RequestMethod.POST)
	public String displaySearchTable(Model model, RedirectAttributes redirectAttr, @ModelAttribute TableDesignSearchForm tableDesignSearchForm, @PageableDefault Pageable pageable) {
		
		super.checkChangeProject(false);
		List<TableDesign> tableDesigns = tableDesignRepository.getTableDesignByProjectId(SessionUtils.getCurrentProjectId());
		pageable = new PageRequest(pageable.getPageNumber(),tableDesigns.size(),pageable.getSort());
		TableDesignCriteria tableCriteria = beanMapper.map(tableDesignSearchForm, TableDesignCriteria.class);
		tableCriteria.setProjectId(SessionUtils.getCurrentProjectId());
		Page<TableDesign> pageDomain = tableDesignService.findPageByCriteria(tableCriteria, pageable);
		tableDesignSearchForm.setProjectId(SessionUtils.getCurrentProjectId());
		GenerateForm generateForm = new GenerateForm();
		generateForm.setGenerateFrom(1);
		generateForm.setGenerateMode(1);
		model.addAttribute("tableDesignSearchForm", tableDesignSearchForm);
		model.addAttribute(GENERATE_FORM_NAME, generateForm);
		model.addAttribute("page", pageDomain);

		return SEARCH_LINK;
	}
	
	@RequestMapping(value = "guideline", method = RequestMethod.GET)
	public String displayGuideline(Model model) {
		
		return GENERATE_GUIDELINE;
	}
	
	/**
	 * 
	 * @param list
	 * @return
	 */
	private List<Long> listTableId(List<String> list){
		List<Long> arr = new ArrayList<Long>();
		if(FunctionCommon.isNotEmpty(list)){
			for (String str : list) {
				if(str != null){
					arr.add(Long.parseLong(str));
				}
			}
		}
		return arr;
	}
}
