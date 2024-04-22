package org.terasoluna.qp.app.tabledesign;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;
import org.terasoluna.qp.app.common.BaseController;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.DbDomainConst.DisplayType;
import org.terasoluna.qp.app.common.constants.DbDomainConst.YesNoFlg;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.DateUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.GraphicDatabaseDesignMessageConst;
import org.terasoluna.qp.app.message.TableDesignMessageConst;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.SqlDesignOutput;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.model.ValidationRule;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignCriteria;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.domaindesign.DomainDesignService;
import org.terasoluna.qp.domain.service.graphicdatabasedesign.TableDesignUtil;
import org.terasoluna.qp.domain.service.subjectarea.SubjectAreaService;
import org.terasoluna.qp.domain.service.tabledesign.TableDesignDetailService;
import org.terasoluna.qp.domain.service.tabledesign.TableDesignService;

import com.fasterxml.jackson.annotation.JsonProperty;

@Controller
@RequestMapping(value = "tabledesign")
@TransactionTokenCheck(value = "tabledesign")
@SessionAttributes(types = { TableDesignSearchForm.class })
public class TableDesignController extends BaseController{

	private static final String ACTION_SEARCH = "/tabledesign/search";
	private static final String SEARCH_LINK = "tabledesign/searchForm";
	private static final String VIEW_LINK = "tabledesign/viewForm";
	private static final String VIEW_BLOGIC_LINK = "tabledesign/viewListAffectedChangeDesignForm";
	private static final String VIEW_BLOGIC_DELETE_LINK = "tabledesign/viewListAffectedChangeDesignDeleteForm";
	private static final String VIEW_SCREEN_SETTING = "tabledesign/viewScreenSetting";
	private static final String REGISTER_FORM_PATH = "tabledesign/registerForm";
	private static final String REDIRECT_SEARCH = "redirect:/tabledesign/search";
	private static final String MODIFY_LINK = "tabledesign/modifyForm";
	private static final String MODIFY_COMMON_TABLE_LINK = "tabledesign/modifyTableCommonForm";
	private static final String REDIRECT_VIEW = "redirect:/tabledesign/view";
	private static final int MODE_SEARCH = 0;
	private static final int MODE_VIEW = 1;
	private static final int MODE_VIEWSETTING = 2;
	//private static final int MODE_AFFECTLIST = 3;
	private static final String TABLE_MODIFY_SESSION = "tableModify";
	

	private static final Logger logger = LoggerFactory
			.getLogger(TableDesignController.class);

	@Inject
	TableDesignService tableDesignService;

	@Inject
	TableDesignDetailService tableDesignDetailService;
	
	@Inject
	DomainDesignService domainDesignService;
	
	@Inject
	SubjectAreaService subjectAreaService;
	
	@Inject 
	SystemService systemService;
	
	@Inject
	TableDesignValidator tableDesignValidator;

	@Inject
	Mapper beanMapper;
	
	@ModelAttribute
	public void initService() {
		moduleCode = CommonMessageConst.TQP_TABLEDESIGN;
	}
	
	/**
	 * Identifies methods which initialize the WebDataBinder which will be used
	 * for populating command and form object
	 * 
	 * @param webDataBinder
	 *            WebDataBinder
	 */
	@InitBinder("tableDesignForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(tableDesignValidator);
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@ModelAttribute
	public TableDesignSearchForm setUpSearchForm() {
		TableDesignSearchForm obj = new TableDesignSearchForm();
		logger.debug("Init form {0}", obj);
		return obj;
	}
	
	@ModelAttribute
	public TableDesignForm setUpTableForm() {
		TableDesignForm obj = new TableDesignForm();
		logger.debug("Init form {0}", obj);
		return obj;
	}

	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String displaySearch(@RequestParam(value = "init", required = false) String init, @ModelAttribute TableDesignSearchForm tableDesignSearchForm,Model model, @PageableDefault Pageable pageable, SessionStatus sessionStatus) {
		if(init != null){
			sessionStatus.setComplete();
			tableDesignSearchForm = setUpSearchForm();
			model.addAttribute("tableDesignSearchForm", tableDesignSearchForm);
			super.setOldProjectId(SessionUtils.getCurrentProject().getProjectId());
		}
		super.checkChangeProject(false);
		pageable = new PageRequest(pageable.getPageNumber(),PageSizeUtils.getPageSizeOfAction(ACTION_SEARCH),pageable.getSort());
		TableDesignCriteria tableCriteria = beanMapper.map(tableDesignSearchForm, TableDesignCriteria.class);
		tableCriteria.setProjectId(SessionUtils.getCurrentProjectId());
		Page<TableDesign> pageDomain = tableDesignService.findPageByCriteria(tableCriteria, pageable);
		tableDesignSearchForm.setProjectId(SessionUtils.getCurrentProjectId());
		model.addAttribute("tableDesignSearchForm", tableDesignSearchForm);
		model.addAttribute("page", pageDomain);

		return SEARCH_LINK;
	}

	@RequestMapping(value = "search", method = RequestMethod.POST)
	public String processSearch(@ModelAttribute TableDesignSearchForm tableDesignSearchForm,Model model, @PageableDefault Pageable pageable) {
		//super.checkChangeProject(false);
		pageable = new PageRequest(pageable.getPageNumber(),PageSizeUtils.getPageSizeOfAction(ACTION_SEARCH),pageable.getSort());
		TableDesignCriteria tableCriteria = beanMapper.map(tableDesignSearchForm, TableDesignCriteria.class);
		tableCriteria.setProjectId(SessionUtils.getCurrentProjectId());
		Page<TableDesign> pageDomain = tableDesignService.findPageByCriteria(tableCriteria, pageable);
		tableDesignSearchForm.setProjectId(SessionUtils.getCurrentProjectId());
		model.addAttribute("tableDesignSearchForm", tableDesignSearchForm);
		model.addAttribute("page", pageDomain);

		return SEARCH_LINK;
	}

	/**
	 * Initialize register module screen
	 * 
	 * @param moduleForm
	 *            ModuleForm
	 * @param model
	 * @return
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String displayRegister(TableDesignForm tableDesignForm, @RequestParam(value = "init", required = false) String init, @ModelAttribute TableDesignSearchForm tableDesignSearchForm,Model model, @PageableDefault Pageable pageable, SessionStatus sessionStatus) {
		try {
			super.checkChangeProject(true);
			tableDesignForm.setListGroupOfDatatype(domainDesignService.getAllBasetype(SessionUtils.getCurrentProjectId(), false));
			tableDesignForm.setProjectId(String.valueOf(SessionUtils.getCurrentProjectId()));
			tableDesignForm.setCommonColumn(0);
			model.addAttribute("reservedWords", systemService.databaseReservedWords(SessionUtils.getCurrentDatabaseType()));
			model.addAttribute("tableDesignForm", tableDesignForm);
			return REGISTER_FORM_PATH;
			
		} catch (BusinessException ex) {
			pageable = new PageRequest(pageable.getPageNumber(),PageSizeUtils.getPageSizeOfAction(ACTION_SEARCH),pageable.getSort());
			TableDesignCriteria tableCriteria = beanMapper.map(tableDesignSearchForm, TableDesignCriteria.class);
			tableCriteria.setProjectId(SessionUtils.getCurrentProjectId());
			Page<TableDesign> pageDomain = tableDesignService.findPageByCriteria(tableCriteria, pageable);
			tableDesignSearchForm.setProjectId(SessionUtils.getCurrentProjectId());
			model.addAttribute("reservedWords", systemService.databaseReservedWords(SessionUtils.getCurrentDatabaseType()));
			model.addAttribute("tableDesignSearchForm", tableDesignSearchForm);
			model.addAttribute("page", pageDomain);
			model.addAttribute("message", ex.getResultMessages());
			return SEARCH_LINK;
		}
	}

	/**
	 * Initialize register module screen
	 * 
	 * @param moduleForm
	 *            ModuleForm
	 * @param model
	 * @return
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.IN)
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String processRegister(@Validated @ModelAttribute TableDesignForm tableDesignForm, BindingResult result, Model model, @PageableDefault Pageable pageable, SessionStatus sessionStatus, RedirectAttributes redirectAttr) {
		try {
			super.checkChangeProject(true);
			//Long projectId = getCurrentProjectId();
			Long projectId = SessionUtils.getCurrentProjectId();
			tableDesignForm.setFlagRegister("1");
			CommonModel commonModel = this.initCommon();
			if (result.hasErrors()) {
				TableDesign tableDesign = beanMapper.map(tableDesignForm, TableDesign.class);
				tableDesignService.loadDataWhenHasErr(tableDesign, projectId);
				tableDesignForm = beanMapper.map(tableDesign, TableDesignForm.class);
				tableDesignForm.setListGroupOfDatatype(domainDesignService.getAllBasetype(SessionUtils.getCurrentProjectId(), false));
				/*tableDesignForm.setProjectId(String.valueOf(projectId));*/
				model.addAttribute("reservedWords", systemService.databaseReservedWords(SessionUtils.getCurrentDatabaseType()));
				model.addAttribute("tableDesignForm",tableDesignForm);
				ValidationUtils.setBindingResult(result, model);
				return REGISTER_FORM_PATH;
			}
			
			TableDesign tableDesign = beanMapper.map(tableDesignForm, TableDesign.class);
			
			try {
				Long accountId = SessionUtils.getAccountId();
				/*tableDesign.setProjectId(projectId);*/
				tableDesign.setCreatedBy(accountId);
				tableDesign.setUpdatedBy(accountId);
				
				tableDesignService.createTable(tableDesign, commonModel, SessionUtils.getCurrentProject(), SessionUtils.getCurrentAccountProfile());
			} catch (BusinessException ex) {
				tableDesignForm = beanMapper.map(tableDesign, TableDesignForm.class);
				tableDesignForm.setListGroupOfDatatype(domainDesignService.getAllBasetype(SessionUtils.getCurrentProjectId(), false));
				tableDesignForm.setProjectId(String.valueOf(SessionUtils.getCurrentProjectId()));
				model.addAttribute("reservedWords", systemService.databaseReservedWords(SessionUtils.getCurrentDatabaseType()));
				model.addAttribute("tableDesignForm",tableDesignForm);
				model.addAttribute("message", ex.getResultMessages());
				return REGISTER_FORM_PATH;
			}
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage(CommonMessageConst.TQP_TABLEDESIGN)));
			return REDIRECT_SEARCH;
		} catch (BusinessException ex) {
			pageable = new PageRequest(pageable.getPageNumber(),PageSizeUtils.getPageSizeOfAction(ACTION_SEARCH),pageable.getSort());
			TableDesignCriteria tableCriteria = null;
			Page<TableDesign> pageDomain = tableDesignService.findPageByCriteria(tableCriteria, pageable);
			model.addAttribute("page", pageDomain);
			model.addAttribute("message", ex.getResultMessages());
			return SEARCH_LINK;
		}
	}
	
	/**
	 * return view screen
	 * 
	 * @param tableDesignForm
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "view")
	public String displayView(TableDesignForm tableDesignForm, Model model, RedirectAttributes redirectAttr) {
		try {
			super.checkChangeProject(false);
			TableDesign table = tableDesignService.getTableDesignForView(tableDesignForm.getTableDesignId(), this.initCommon());
			table.setActionDelete(false);
			tableDesignForm = beanMapper.map(table, TableDesignForm.class);
			/*this.setBinKeyType(table, tableDesignForm);*/
			model.addAttribute("table", tableDesignForm);
		} catch (BusinessException ex) {
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			if (MODE_SEARCH == tableDesignForm.getMode()) {
				return REDIRECT_SEARCH;
			} else if (MODE_VIEW == tableDesignForm.getMode()) {
				return REDIRECT_VIEW;
			}
		}
		return VIEW_LINK;
	}
	
	@TransactionTokenCheck(value = "modifyTableCommonForm", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modifyTableCommonForm", method = RequestMethod.GET)
	public String displayModifyCommonTable(TableDesignForm tableDesignForm, Model model, RedirectAttributes redirectAttr, BindingResult result){
		CommonModel common = this.initCommon();
		try {
			super.checkChangeProject(true);
			TableDesign tableDesignLoad = new TableDesign();
			tableDesignLoad = tableDesignService.loadTableDesign(tableDesignForm.getTableDesignId(), common);
			tableDesignForm = beanMapper.map(tableDesignLoad, TableDesignForm.class);
			tableDesignForm.setListGroupOfDatatype(domainDesignService.getAllBasetype(SessionUtils.getCurrentProjectId(), false));
			tableDesignForm.setProjectId(String.valueOf(SessionUtils.getCurrentProjectId()));
		} catch (BusinessException ex) {
			
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			if (MODE_SEARCH == tableDesignForm.getMode()) {
				return REDIRECT_SEARCH;
			} else if (MODE_VIEW == tableDesignForm.getMode()) {
				try {
					model.addAttribute("message", ex.getResultMessages());
					TableDesign table = tableDesignService.getTableDesignForView(tableDesignForm.getTableDesignId(), common);
					tableDesignForm = beanMapper.map(table, TableDesignForm.class);
					model.addAttribute("table", tableDesignForm);
				} catch (BusinessException be) {
					model.addAttribute("message", be.getResultMessages());
				}
				return VIEW_LINK;
			}
		}
		SessionUtils.remove(TABLE_MODIFY_SESSION);
		model.addAttribute("tableDesignForm", tableDesignForm);
		return MODIFY_COMMON_TABLE_LINK;
	}
	
	@TransactionTokenCheck(value = "modifyTableCommonForm", type = TransactionTokenType.IN)
	@RequestMapping(value = "modifyTableCommonForm", method = RequestMethod.POST)
	public String processModifyCommonTable(@Validated @ModelAttribute TableDesignForm tableDesignForm, BindingResult result, RedirectAttributes redirectAttr, Model model){
		CommonModel common = this.initCommon();
		if (result.hasErrors()){
			TableDesign tableDesign = tableDesignService.loadTableDesign(tableDesignForm.getTableDesignId(), common);
			TableDesign tableDesignForForm = beanMapper.map(tableDesignForm, TableDesign.class);
			tableDesign.setSubjectAreas(tableDesignForForm.getSubjectAreas());
			tableDesignService.loadDataWhenHasErr(tableDesign, new Long(0));
			tableDesignForm = beanMapper.map(tableDesign, TableDesignForm.class);
			model.addAttribute("tableDesignForm",tableDesignForm);
			ValidationUtils.setBindingResult(result, model);
			return MODIFY_COMMON_TABLE_LINK;
		}
		TableDesign table = beanMapper.map(tableDesignForm, TableDesign.class);
		table.setProjectId(SessionUtils.getCurrentProjectId());
		table.setTableDesignId(tableDesignForm.getTableDesignId());
		table.setUpdatedDate(tableDesignForm.getUpdatedDate());
		try {
			super.checkChangeProject(true);
			tableDesignService.modifyTableCommon(table, common);
		} catch (BusinessException ex) {

			/*if(CommonMessageConst.ERR_SYS_0037.equals(ex.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("notExistFlg", "1");
			}*/
			tableDesignForm.setListGroupOfDatatype(domainDesignService.getAllBasetype(SessionUtils.getCurrentProjectId(), false));
			model.addAttribute("tableDesignForm",tableDesignForm);
			model.addAttribute("message", ex.getResultMessages());
			return MODIFY_COMMON_TABLE_LINK;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003,MessageUtils.getMessage(CommonMessageConst.TQP_TABLEDESIGN)));
		return REDIRECT_SEARCH;
	}
	
	/**
	 * Initialize modify Table Design screen
	 * 
	 * @return MODIFY_LINK
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public String displayModify(TableDesignForm tableDesignForm, Model model, RedirectAttributes redirectAttr, BindingResult result){
		CommonModel common = this.initCommon();
		try {
			super.checkChangeProject(true);
			TableDesign tableDesignLoad = tableDesignService.loadTableDesign(tableDesignForm.getTableDesignId(), common);
			
			// Compare and set again pattern for table design details
			compareAndSetPatternTableDesignDetails(tableDesignLoad);
			
			if (DbDomainConst.DesignStatus.FIXED.equals(tableDesignLoad.getDesignStatus())) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0109, tableDesignLoad.getTableName()));
			}
			
			if (DbDomainConst.TableDesignType.QP_TABLE.equals(tableDesignLoad.getType())) {
				throw new BusinessException(ResultMessages.error().add(
						GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0003, MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0046)
						, MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0048)));
			}

			tableDesignForm = beanMapper.map(tableDesignLoad, TableDesignForm.class);
			model.addAttribute("reservedWords", systemService.databaseReservedWords(SessionUtils.getCurrentDatabaseType()));
			tableDesignForm.setListGroupOfDatatype(domainDesignService.getAllBasetype(SessionUtils.getCurrentProjectId(), false));
			tableDesignForm.setProjectId(String.valueOf(SessionUtils.getCurrentProjectId()));
		} catch (BusinessException ex) {
			if (MODE_VIEW == tableDesignForm.getMode()) {
				model.addAttribute("message", ex.getResultMessages());
				return VIEW_LINK;
			}
			redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_TABLEDESIGN));
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			return REDIRECT_SEARCH;
		}
		SessionUtils.remove(TABLE_MODIFY_SESSION);
		model.addAttribute("tableDesignForm", tableDesignForm);
		return MODIFY_LINK;
	}
	
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST,params="jsonBack")
	public String displayModifyBack(RedirectAttributes redirectAttr, Model model,@JsonProperty @RequestParam("formJson")String formJson) {
		this.checkChangeProject(false);
		TableDesignForm tableDesignForm = DataTypeUtils.toObject(formJson, TableDesignForm.class);
		tableDesignForm.setListGroupOfDatatype(domainDesignService.getAllBasetype(SessionUtils.getCurrentProjectId(), false));
		model.addAttribute("reservedWords", systemService.databaseReservedWords(SessionUtils.getCurrentDatabaseType()));
		model.addAttribute("tableDesignForm",tableDesignForm);
		return MODIFY_LINK;
	}
	
	/**
	 * return viewListAffectedChangeDesignForm screen
	 * 
	 * @param tableDesignForm
	 * @param model
	 * @return
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "viewListAffectedChangeDesignForm", method = {RequestMethod.POST})
	public String displayViewBlogic(@Validated @ModelAttribute TableDesignForm tableDesignForm, BindingResult result, RedirectAttributes redirectAttr, Model model){
		Boolean displayImpact = tableDesignForm.getShowImpactFlag();
		Long projectId = SessionUtils.getCurrentProjectId();
		if (result.hasErrors() && tableDesignForm.getMode() == 0) {
			TableDesign tableDesign = beanMapper.map(tableDesignForm, TableDesign.class);
			tableDesignService.loadDataWhenHasErr(tableDesign, projectId);
			tableDesignForm = beanMapper.map(tableDesign, TableDesignForm.class);
			tableDesignForm.setListGroupOfDatatype(domainDesignService.getAllBasetype(SessionUtils.getCurrentProjectId(), false));
			model.addAttribute("reservedWords", systemService.databaseReservedWords(SessionUtils.getCurrentDatabaseType()));
			model.addAttribute("tableDesignForm",tableDesignForm);
			ValidationUtils.setBindingResult(result, model);
			return MODIFY_LINK;
		}
		TableDesign tableDesign = beanMapper.map(tableDesignForm, TableDesign.class);
		try {
			super.checkChangeProject(true);
			tableDesign.setIsDeleted(false);
			List<TableDesign> listTableDesigns = new ArrayList<TableDesign>();
			listTableDesigns.add(tableDesign);
			tableDesignService.loadListAffected(listTableDesigns, DbDomainConst.FromResourceType.TABLE_DESIGN, projectId);
			tableDesignForm = beanMapper.map(tableDesign, TableDesignForm.class);
			model.addAttribute("tableDesignForm", tableDesignForm);
		} catch (BusinessException ex) {
			model.addAttribute("reservedWords", systemService.databaseReservedWords(SessionUtils.getCurrentDatabaseType()));
			model.addAttribute("message", ex.getResultMessages());
			return MODIFY_LINK;
		}
		
		if(FunctionCommon.isEmpty(tableDesign.getListSqlDesigns()) && FunctionCommon.isEmpty(tableDesign.getListBusinessLogics()) && FunctionCommon.isEmpty(tableDesign.getListTableDesignForeignKeyAffect()) || (displayImpact == null || Boolean.FALSE.equals(displayImpact))){
			
			TableDesign table = beanMapper.map(tableDesignForm, TableDesign.class);
			table.setProjectId(projectId);
			try {
				CommonModel commonModel = this.initCommon();
				commonModel.setAccountProfile(SessionUtils.getCurrentAccountProfile());
				tableDesignService.modifyTable(table, commonModel, SessionUtils.getCurrentProject(), false);
			} catch (BusinessException ex) {
				/*if(CommonMessageConst.ERR_SYS_0037.equals(ex.getResultMessages().getList().get(0).getCode())) {
					model.addAttribute("notExistFlg", "1");
				}*/
				tableDesign = beanMapper.map(tableDesignForm, TableDesign.class);
				//tableDesign = tableDesignService.loadTableDesign(tableDesign.getTableDesignId());
				tableDesignForm = beanMapper.map(tableDesign, TableDesignForm.class);
				tableDesignForm.setListGroupOfDatatype(domainDesignService.getAllBasetype(SessionUtils.getCurrentProjectId(), false));
				model.addAttribute("reservedWords", systemService.databaseReservedWords(SessionUtils.getCurrentDatabaseType()));
				model.addAttribute("tableDesignForm",tableDesignForm);
				model.addAttribute("message", ex.getResultMessages());
				return MODIFY_LINK;
			}
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003,
					MessageUtils.getMessage(CommonMessageConst.TQP_TABLEDESIGN)));
			return REDIRECT_SEARCH;
		}else{
			tableDesignForm.setShowImpactFlag(displayImpact);
			model.addAttribute("formJson", DataTypeUtils.toJson(tableDesignForm));
			return VIEW_BLOGIC_LINK;
		}
	}
	
	/**
	 * Modify subject area process
	 * 
	 * @return In the case success : REDIRECT_SEARCH other MODIFY_LINK
	 */
	//@TransactionTokenCheck(value = "modifyWithAffect", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST, params = "isJsonForm")
	public String processModify(@Validated @ModelAttribute TableDesignForm tableDesignForm, BindingResult result, RedirectAttributes redirectAttr, Model model, @RequestParam("formJson")String formJson){
		
		TableDesignForm form = DataTypeUtils.toObject(formJson, TableDesignForm.class) ;
		TableDesign table = beanMapper.map(form, TableDesign.class);
		Long projectId = SessionUtils.getCurrentProjectId();
		table.setProjectId(projectId);
		table.setTableDesignId(tableDesignForm.getTableDesignId());
		table.setUpdatedDate(tableDesignForm.getUpdatedDate());
		CommonModel common = this.initCommon();
		try {
			super.checkChangeProject(true);
			Long accountId = SessionUtils.getAccountId();
			table.setProjectId(projectId);
			table.setCreatedBy(accountId);
			table.setUpdatedBy(accountId);
			common.setAccountProfile(SessionUtils.getCurrentAccountProfile());
			tableDesignService.modifyTable(table, common, SessionUtils.getCurrentProject(), false);
		} catch (BusinessException ex) {

			/*if(CommonMessageConst.ERR_SYS_0037.equals(ex.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("notExistFlg", "1");
			}*/
			form.setListGroupOfDatatype(domainDesignService.getAllBasetype(SessionUtils.getCurrentProjectId(), false));
			model.addAttribute("tableDesignForm",form);
			model.addAttribute("reservedWords", systemService.databaseReservedWords(SessionUtils.getCurrentDatabaseType()));
			model.addAttribute("message", ex.getResultMessages());
			return MODIFY_LINK;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003,MessageUtils.getMessage(CommonMessageConst.TQP_TABLEDESIGN)));
		return REDIRECT_SEARCH;
	}
	/**
	 * return viewListAffectedChangeDesignForm screen
	 * 
	 * @param tableDesignForm
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "viewListAffectedChangeDesignDelete", method = {RequestMethod.GET})
	public String displayViewBlogicDeleteGet(@ModelAttribute TableDesignForm tableDesignForm, BindingResult result, RedirectAttributes redirectAttr, Model model){
		Boolean displayImpact = tableDesignForm.getShowImpactFlag();
		TableDesign tableDesign = beanMapper.map(tableDesignForm, TableDesign.class);
		
		
		/*CommonModel common = this.initCommon();*/
		try {
			super.checkChangeProject(true);
			Long projectId = SessionUtils.getCurrentProjectId();
			if (displayImpact == null || Boolean.FALSE.equals(displayImpact)) {
				tableDesignService.deleteTable(tableDesign, SessionUtils.getAccountId(), projectId, false);
				redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_TABLEDESIGN));
				redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004, 
						MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0005)));
				return DbDomainConst.REDIRECT_DELETION_SUCCESS;
			}
			
			tableDesign = tableDesignService.findOneById(tableDesignForm.getTableDesignId());
			tableDesign.setIsDeleted(true);
			List<TableDesign> listTableDesigns = new ArrayList<TableDesign>();
			listTableDesigns.add(tableDesign);
			tableDesignService.loadListAffected(listTableDesigns, DbDomainConst.FromResourceType.TABLE_DESIGN, projectId);
			tableDesign = listTableDesigns.get(0);
			tableDesign.setActionDelete(true);

			if(CollectionUtils.isEmpty(tableDesign.getListSqlDesigns()) && CollectionUtils.isEmpty(tableDesign.getListBusinessLogics()) 
					&& CollectionUtils.isEmpty(tableDesign.getListTableDesignForeignKeyAffect())  ) {
				tableDesignService.deleteTable(tableDesign, SessionUtils.getAccountId(), projectId, false);
				redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_TABLEDESIGN));
				redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004, 
						MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0005)));
				return DbDomainConst.REDIRECT_DELETION_SUCCESS;
				
			} else {

				tableDesignForm = beanMapper.map(tableDesign, TableDesignForm.class);
				model.addAttribute("tableDesignForm", tableDesignForm);
				model.addAttribute("formJson", DataTypeUtils.toJson(tableDesignForm));
				return VIEW_BLOGIC_DELETE_LINK;
			}
		
		} catch (BusinessException ex) {
			/*model.addAttribute("message", ex.getResultMessages());
			try {
				TableDesign table = tableDesignService.getTableDesignForView(tableDesign.getTableDesignId(), common);
				tableDesignForm = beanMapper.map(table, TableDesignForm.class);
				model.addAttribute("table", tableDesignForm);
			} catch (BusinessException be) {
				model.addAttribute("message", be.getResultMessages());
			}
			return VIEW_LINK;*/
			
			redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_TABLEDESIGN));
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			return DbDomainConst.REDIRECT_DELETION_SUCCESS;
			
		}

	}
	
	/**
	 * return view screen setting
	 * 
	 * @param tableDesignForm
	 * @param model
	 * @return
	 */
	@TransactionTokenCheck(value = "modifyTableSetting", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "viewScreenSetting", method = RequestMethod.GET)
	public String displayViewScreenSetting(TableDesignForm tableDesignForm, Model model, RedirectAttributes redirectAttr) {
		try {
			super.checkChangeProject(true);
			TableDesign table = tableDesignService.getTableDesignForScreenItemSetting(tableDesignForm.getTableDesignId());
			tableDesignForm = beanMapper.map(table, TableDesignForm.class);
			model.addAttribute("tableDesignForm", tableDesignForm);
		} catch (BusinessException ex) {
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			if (MODE_SEARCH == tableDesignForm.getMode()) {
				return VIEW_SCREEN_SETTING;
			} else if (MODE_VIEWSETTING == tableDesignForm.getMode()) {
				return REDIRECT_SEARCH;
			}
		}
		return VIEW_SCREEN_SETTING;
	}
	
	/**
	 * return view screen setting
	 * 
	 * @param tableDesignForm
	 * @param model
	 * @return
	 */
	@TransactionTokenCheck(value = "modifyTableSetting", type = TransactionTokenType.IN)
	@RequestMapping(value = "modifyTableSetting", method = RequestMethod.POST)
	public String modifyViewScreenSetting(TableDesignForm tableDesignForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		TableDesign tableDesign = beanMapper.map(tableDesignForm, TableDesign.class);
		try {
			super.checkChangeProject(true);
			List<TableDesignDetails> tableDesignDetails = tableDesign.getListTableDesignDetails();
			int numOfKeyCheck = 0;
			int hasPk = 0;
			if(tableDesignDetails != null) {
				for(TableDesignDetails obj : tableDesignDetails){
					if(obj.getUsed() != null && obj.getUsed()) {
						obj.setDisplayType(YesNoFlg.YES);
					} else {
						obj.setDisplayType(YesNoFlg.NO);
					}

					if (YesNoFlg.YES.equals(obj.getIsMandatory()) && tableDesign.getHasCompositeKey() <= 1
							&& YesNoFlg.NO.intValue() == obj.getAutoIncrementFlag() && StringUtils.isBlank(obj.getDefaultValue())) {
						obj.setDisplayType(DisplayType.USED);
					}

					if (YesNoFlg.YES.intValue() == obj.isPrimaryKey() && 
							(YesNoFlg.NO.intValue() == obj.getAutoIncrementFlag() && StringUtils.isBlank(obj.getDefaultValue())) 
								&& !TableDesignUtil.checkSpecialBasetype(obj.getBaseType())
							) {
						hasPk++;
						if( DisplayType.USED.equals(obj.getDisplayType())) {
							numOfKeyCheck++;
						}
					}
				}

				if (hasPk > 0 && numOfKeyCheck == 0) {
					throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0116, 
							MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0011)));
				}

				CommonModel common = this.initCommon();
				tableDesign.setListTableDesignDetails(tableDesignDetails);
				Long accountId = SessionUtils.getAccountId();
				tableDesign.setCreatedBy(accountId);
				tableDesignService.updateItem(tableDesign, common);

				// Reload data when Update succes.
				tableDesign = tableDesignService.getTableDesignForScreenItemSetting(tableDesignForm.getTableDesignId());
				tableDesignForm = beanMapper.map(tableDesign, TableDesignForm.class);
				model.addAttribute("tableDesignForm", tableDesignForm);
				model.addAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0034)));
			}
			return VIEW_SCREEN_SETTING;
		} catch (BusinessException ex) {
			List<TableDesignDetailsForm> listTableDesignDetailsForm = new ArrayList<TableDesignDetailsForm>();
			List<ValidationRule> listValidationRule = domainDesignService.findAllValidationRule();
			for(TableDesignDetailsForm obj : tableDesignForm.getListTableDesignDetails()){
				TableDesignDetails tableDesignDetails = beanMapper.map(obj,TableDesignDetails.class);
				obj.setListItemtype(tableDesignService.getOptionItemType(tableDesignDetails,listValidationRule));
				listTableDesignDetailsForm.add(obj);
			}
			tableDesignForm.setListTableDesignDetails(listTableDesignDetailsForm);
			model.addAttribute("tableDesignForm",tableDesignForm);
			model.addAttribute("message", ex.getResultMessages());
			return VIEW_SCREEN_SETTING;
		}
	}

	/**
	 * Initialize modify Table Design screen
	 * 
	 * @return MODIFY_LINK
	 */
	@TransactionTokenCheck(value = "modifyFromAffected", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modifyFromAffected", method = RequestMethod.GET)
	public String displayModifyFromAffected(TableDesignForm tableDesignForm, Model model, RedirectAttributes redirectAttr, BindingResult result){
		try {
			super.checkChangeProject(true);
			TableDesign tableDesignLoad = beanMapper.map(tableDesignForm, TableDesign.class);
			if (DbDomainConst.DesignStatus.FIXED.equals(tableDesignLoad.getDesignStatus())) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0109, tableDesignLoad.getTableName()));
			}
			tableDesignForm = beanMapper.map(tableDesignLoad, TableDesignForm.class);
			tableDesignForm.setListGroupOfDatatype(domainDesignService.getAllBasetype(SessionUtils.getCurrentProjectId(), false));
			tableDesignForm.setProjectId(String.valueOf(SessionUtils.getCurrentProjectId()));
		} catch (BusinessException ex) {
			
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			if (MODE_SEARCH == tableDesignForm.getMode()) {
				return REDIRECT_SEARCH;
			} else if (MODE_VIEW == tableDesignForm.getMode()) {
				try {
					model.addAttribute("message", ex.getResultMessages());
					
					TableDesign table = tableDesignService.getTableDesignForView(tableDesignForm.getTableDesignId(), this.initCommon());
					tableDesignForm = beanMapper.map(table, TableDesignForm.class);
					model.addAttribute("tableDesignForm", tableDesignForm);
				} catch (BusinessException be) {
					model.addAttribute("message", be.getResultMessages());
				}
				return VIEW_LINK;
			}
		}
		model.addAttribute("tableDesignForm", tableDesignForm);
		return MODIFY_LINK;
	}
	
	/**
	 * Delete Table Design
	 * 
	 * @return In the case success : REDIRECT_DELETECOMPLETE other VIEW_LINK
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String processDelete(TableDesignForm tableDesignForm, BindingResult result, RedirectAttributes redirectAttr, Model model,@JsonProperty @RequestParam("formJson")String formJson){

		if(!formJson.equals(StringUtils.EMPTY)){
			tableDesignForm = DataTypeUtils.toObject(formJson, TableDesignForm.class);
		}
		
		TableDesign tableDesign = beanMapper.map(tableDesignForm, TableDesign.class);
		
		// process when click button "delete" 
		if(tableDesign.getActionDelete()) {
			try {
				super.checkChangeProject(true);
				if(FunctionCommon.isEmpty(tableDesign.getListSqlDesigns()) && FunctionCommon.isEmpty(tableDesign.getListBusinessLogics()) && !tableDesign.getShowImpactFlag()){
					tableDesignService.deleteTable(tableDesign, SessionUtils.getAccountId(), SessionUtils.getCurrentProjectId(), false);
				}else{
					tableDesignService.deleteTable(tableDesign, SessionUtils.getAccountId(), SessionUtils.getCurrentProjectId(), true);
				}
			} catch (BusinessException ex) {
				/*model.addAttribute("message", ex.getResultMessages());
				try {
					TableDesign table = tableDesignService.getTableDesignForView(tableDesign.getTableDesignId(), common);
					tableDesignForm = beanMapper.map(table, TableDesignForm.class);
					model.addAttribute("table", tableDesignForm);
				} catch (BusinessException be) {
					model.addAttribute("message", be.getResultMessages());
				}
				return VIEW_LINK;*/

				redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_TABLEDESIGN));
				redirectAttr.addFlashAttribute("message", ex.getResultMessages());
				return DbDomainConst.REDIRECT_DELETION_SUCCESS;

			}
			redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_TABLEDESIGN));
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004, MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0005)));
			return DbDomainConst.REDIRECT_DELETION_SUCCESS;
		}
		// process when click button "Fixed design" or "Under design"
		else {
			try {
				super.checkChangeProject(true);
				tableDesignService.modifyDesignStatus(tableDesign, SessionUtils.getAccountId(), SessionUtils.getCurrentProjectId());
			} catch(BusinessException ex) {
				/*model.addAttribute("message", ex.getResultMessages());
				try {
					TableDesign table = tableDesignService.getTableDesignForView(tableDesign.getTableDesignId(), common);
					tableDesignForm = beanMapper.map(table, TableDesignForm.class);
					model.addAttribute("table", tableDesignForm);
				} catch (BusinessException be) {
					model.addAttribute("message", be.getResultMessages());
				}
				return VIEW_LINK;*/
				
				redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_TABLEDESIGN));
				redirectAttr.addFlashAttribute("message", ex.getResultMessages());
				return DbDomainConst.REDIRECT_DELETION_SUCCESS;
				
			}

			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0005)));
			redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_TABLEDESIGN));
			return DbDomainConst.REDIRECT_MODIFY_SUCCESS;
		}
	}
	
	@RequestMapping(value = "getSqlDesignOutputsForAdvanceSetting", method = RequestMethod.GET)
	@ResponseBody
	public SqlDesignOutput[] getSqlDesignOutputsForAdvanceSetting(@RequestParam("sqlDesignId") Long sqlDesignId) {
		return tableDesignService.getSqlDesignOutputsForAdvanceSetting(sqlDesignId);
	}
	
	@RequestMapping(value = "getTableDesignByTableId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public TableDesign getTableDesignByTableId(@RequestParam("tableId") Long tableDesignId){
		return this.tableDesignService.findOneById(tableDesignId);
	}
	
	@RequestMapping(value = "findOneTableDesignDetail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public TableDesignDetails findOneTableDesignDetail(@RequestParam("columnId") Long columnId){
		return this.tableDesignService.findOneTableDesignDetail(columnId);
	}
	
	/**
	 * 
	 * @param tableDesign
	 * @param tableDesignForm
	 */
	/*private void setBinKeyType(TableDesign tableDesign, TableDesignForm tableDesignForm){
		for (int i = 0; i < tableDesign.getListTableDesignDetails().size(); i++) {
			tableDesignForm.getListTableDesignDetails().get(i).setKeyType(tableDesign.getListTableDesignDetails().get(i).getKeyType());
		}
	}*/
	/**
	 * 
	 * @param tableDesignDetails
	 * 
	 * Compare and set again pattern for table design details
	 * 
	 */
	private void compareAndSetPatternTableDesignDetails(TableDesign tableDesignLoad){
		List<TableDesignDetails> tableDesignDetails = tableDesignLoad.getListTableDesignDetails();
		List<TableDesignDetails> tableDesignDetailsNew = new ArrayList<TableDesignDetails>();
		if(FunctionCommon.isNotEmpty(tableDesignDetails)){
			
			for (TableDesignDetails details : tableDesignDetails) {
				if (StringUtils.isNotBlank(details.getPatternFormat())) {
					// Check date, time, date time
					if (DataTypeUtils.equals(DbDomainConst.BaseType.DATE_BASETYPE, details.getBaseType())) {
						String currentFormat = DateUtils.getPatternDate(SessionUtils.getCurrentAccountProfile().getDateFormat());
						if (!DataTypeUtils.equalsIgnoreCase(details.getPatternFormat(), currentFormat)) {
							convertPatternForDesign(details, currentFormat);	
						}
					} else if (DataTypeUtils.equals(DbDomainConst.BaseType.TIME_BASETYPE, details.getBaseType())) {
						String currentFormat = DateUtils.getPatternTime(SessionUtils.getCurrentAccountProfile().getTimeFormat());
						if (!DataTypeUtils.equalsIgnoreCase(details.getPatternFormat(), currentFormat)) {
							convertPatternForDesign(details, currentFormat);	
						}
					} else if (DataTypeUtils.equals(DbDomainConst.BaseType.DATETIME_BASETYPE, details.getBaseType())) {
						String currentFormat = DateUtils.getPatternDateTime(SessionUtils.getCurrentAccountProfile().getDateTimeFormat());
						if (!DataTypeUtils.equalsIgnoreCase(details.getPatternFormat(), currentFormat)) {
							convertPatternForDesign(details, currentFormat);	
						}
					}
				}
				tableDesignDetailsNew.add(details);
			}
			tableDesignLoad.setListTableDesignDetails(tableDesignDetailsNew);
		}
	}
	/**
	 * Convert pattern for date, time, date time
	 * 
	 * @return void
	 */
	private void convertPatternForDesign(TableDesignDetails details, String newPatternConvert) {
		try {
			// check null and process for default value
			if (StringUtils.isNotBlank(details.getDefaultValue())) {
				
				Timestamp defaultValue = DateUtils.parse(details.getDefaultValue(), details.getPatternFormat());
				details.setDefaultValue(DateUtils.formatDateTime(defaultValue, newPatternConvert));
			}
			if (StringUtils.isNotBlank(details.getMinVal())) {
				
				Timestamp defaultValue = DateUtils.parse(details.getMinVal(), details.getPatternFormat());
				details.setMinVal(DateUtils.formatDateTime(defaultValue, newPatternConvert));
			}
			if (StringUtils.isNotBlank(details.getMaxVal())) {
				
				Timestamp defaultValue = DateUtils.parse(details.getMaxVal(), details.getPatternFormat());
				details.setMaxVal(DateUtils.formatDateTime(defaultValue, newPatternConvert));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
