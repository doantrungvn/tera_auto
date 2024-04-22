package org.terasoluna.qp.app.sqldesign;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.PermissionUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.SqlDesignMessageConst;
import org.terasoluna.qp.app.sqldesign.SqlDesignForm.SqlDesignValidationGroup;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignSearchCriteria;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeConst.SQLDesignType;
import org.terasoluna.qp.domain.service.impactchange.ImpactSQLDesign;
import org.terasoluna.qp.domain.service.impactchange.ImpactSQLDesignShareService;
import org.terasoluna.qp.domain.service.sqldesign.SqlDesignCompound;
import org.terasoluna.qp.domain.service.sqldesign.SqlDesignService;

@Controller
@RequestMapping(value = "sqldesign")
@TransactionTokenCheck(value = "sqldesign")
@SessionAttributes(types=SqlDesignSearchForm.class)
public class SqlDesignController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(SqlDesignController.class);
	
	private final String REDIRECT_VIEW = "redirect:/sqldesign/view";
	
	private final String FORWARD_VIEW = "forward:/sqldesign/view";
	
	private final String FORWARD_VIEW_DELETE_AFFECTION = "forward:/sqldesign/deleteConfirm";
	
	private final String REDIRECT_SEARCH = "redirect:/sqldesign/search";
	
	private final String FORM_MODIFY = "sqldesign/modifyForm";
	
	private final String FORM_MODIFY_ADVANCED = "sqldesign/modifyAdvancedForm";
	
	private final String FORM_MODIFY_AFFECTION = "sqldesign/modifyFormChangeAffection";
	
	private final String FORM_MODIFY_ADVANCED_AFFECTION = "sqldesign/modifyAdvancedFormChangeAffection";
	
	private final String FORM_SEARCH = "sqldesign/searchForm";
	
	private final String FORM_REGISTER = "sqldesign/registerForm";
	
	private final String FORM_REGISTER_ADVANCED = "sqldesign/registerAdvancedForm";
	
	private final String FORM_VIEW = "sqldesign/viewForm";
	
	private final String FORM_VIEW_ADVANCED = "sqldesign/viewAdvancedForm";
	
	private final String FORM_VIEW_DELETE_AFFECTION = "sqldesign/viewFormDeleteAffection";
	
	private final String MODE_VIEW = "1";
	
	private final String DESIGN_FORM_NAME = "sqlDesignDesignForm";
	
	private final String ADVANCED_DESIGN_FORM_NAME = "sqlDesignAdvancedDesignForm";
	
	private final String SEARCH_FORM_NAME = "sqlDesignSearchForm";
	
	@Inject
	Mapper beanMapper;
	
	@Inject
	SqlDesignService sqlDesignService;
	
	@Inject
	SqlDesignDesignFormValidator sqlDesignDesignFormValidator;
	
	@Inject
	SqlDesignAdvancedDesignFormValidator sqlDesignAdvancedDesignFormValidator;
	
	@Inject
	ImpactSQLDesignShareService impactSQLDesignShareService;
	
	@InitBinder
	public void init() {
		moduleCode = CommonMessageConst.TQP_SQLDESIGN;
	}
	
	public SqlDesignController() {
		
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@TransactionTokenCheck(value="view",type=TransactionTokenType.IN)
	public String processDelete(SqlDesignDesignForm form, Model model, RedirectAttributes redirectAttr) {
		this.checkChangeProject(true);
		SqlDesignCompound sqlDesignCompound = beanMapper.map(form, SqlDesignCompound.class);
		String destination = null;
		
		if(PermissionUtils.deleteObjectHasFk()){
			boolean isDeleted=false;
			try{
				isDeleted = sqlDesignService.delete(sqlDesignCompound, this.initCommon());
				if(isDeleted) {
					redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_SQLDESIGN));
					redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004, MessageUtils.getMessage(SqlDesignMessageConst.SC_SQLDESIGN_SQL_DESIGN)));
					return DbDomainConst.REDIRECT_DELETION_SUCCESS;
				}
				else {
					redirectAttr.addFlashAttribute("message", ResultMessages.error().add(CommonMessageConst.ERR_SYS_0073, MessageUtils.getMessage(SqlDesignMessageConst.SC_SQLDESIGN_SQL_DESIGN)));
					redirectAttr.addAttribute("sqlDesignForm.sqlDesignId", form.getSqlDesignForm().getSqlDesignId());
					destination = REDIRECT_VIEW;
				}
			} catch(BusinessException ex) {
				/*model.addAttribute("message",ex.getResultMessages());
				form.getSqlDesignForm().setSqlDesignId(null);
				destination = FORM_VIEW;*/
				redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_SQLDESIGN));
				redirectAttr.addFlashAttribute("message", ex.getResultMessages());
				return DbDomainConst.REDIRECT_DELETION_SUCCESS;
			}
		} else {
			sqlDesignService.findAllDeletionAffection(sqlDesignCompound, this.initCommon());
			if(CollectionUtils.isEmpty(sqlDesignCompound.getAffectedBusinessDesigns()) && 
					CollectionUtils.isEmpty(sqlDesignCompound.getAffectedScreenDesigns()) &&
					CollectionUtils.isEmpty(sqlDesignCompound.getAffectedDomainDesigns()) &&
					CollectionUtils.isEmpty(sqlDesignCompound.getAffectedTableDesigns())){
				model.addAttribute("message", ResultMessages.error().add(CommonMessageConst.ERR_SYS_0097));
				destination = FORWARD_VIEW;
			} else {
				model.addAttribute("message", ResultMessages.error().add(CommonMessageConst.ERR_SYS_0097));
				destination = FORWARD_VIEW_DELETE_AFFECTION;
			}
			
		}
		return destination;
	}
	

	@RequestMapping(value = "deleteConfirm", method = {RequestMethod.POST,RequestMethod.GET})
	@TransactionTokenCheck(value="view",type=TransactionTokenType.IN)
	public String processDeleteConfirm(SqlDesignDesignForm form, BindingResult result, RedirectAttributes redirectAttr, Model model) {
  		this.checkChangeProject(true);
		SqlDesignCompound sqlDesignCompound = beanMapper.map(form, SqlDesignCompound.class);
		String destination = null;
		CommonModel common = this.initCommon();
		if(form.getActionDelete()) {
//			try{
//				sqlDesignCompound = sqlDesignService.findCompoundById(sqlDesignCompound.getSqlDesign().getSqlDesignId());
//				sqlDesignService.findAllDeletionAffection(sqlDesignCompound, this.initCommon());
//				boolean isNotShowImpact = form.getShowImpactFlag() == null || form.getShowImpactFlag() == false;
//				if(isNotShowImpact || (CollectionUtils.isEmpty(sqlDesignCompound.getAffectedBusinessDesigns()) && 
//						CollectionUtils.isEmpty(sqlDesignCompound.getAffectedScreenDesigns()) &&
//						CollectionUtils.isEmpty(sqlDesignCompound.getAffectedDomainDesigns()) &&
//						CollectionUtils.isEmpty(sqlDesignCompound.getAffectedTableDesigns()))){
//					
//					destination = processDelete(form, model, redirectAttr);
//				} else {
//					model.addAttribute("affectedBusinessDesigns",sqlDesignCompound.getAffectedBusinessDesigns());
//					model.addAttribute("affectedScreenDesigns",sqlDesignCompound.getAffectedScreenDesigns());
//					model.addAttribute("affectedDomainDesigns",sqlDesignCompound.getAffectedDomainDesigns());
//					model.addAttribute("affectedTableDesigns",sqlDesignCompound.getAffectedTableDesigns());
//					SqlDesignDesignForm sqlForm = beanMapper.map(sqlDesignCompound, SqlDesignDesignForm.class);
//					sqlForm.setShowImpactFlag(form.getShowImpactFlag());
//					model.addAttribute("sqlDesignDesignForm", sqlForm);
//					destination = FORM_VIEW_DELETE_AFFECTION;
//				}
//			} catch(BusinessException ex){
//				model.addAttribute("message",ex.getResultMessages());
//				form.getSqlDesignForm().setSqlDesignId(null);
//				destination = FORM_VIEW;
//			}
			try{
				boolean isNotShowImpact = form.getShowImpactFlag() == null || form.getShowImpactFlag() == false;
				sqlDesignCompound = sqlDesignService.findCompoundById(sqlDesignCompound.getSqlDesign().getSqlDesignId());
				ImpactSQLDesign impact = new ImpactSQLDesign();
				if(!isNotShowImpact){
					impact = impactSQLDesignShareService.detectListAffectedWhenDelete(sqlDesignCompound.getSqlDesign(), common, false);
					if(impact.getImpactFlag()){
						model.addAttribute("affectedBusinessDesigns",impact.getLstUsedBusinessDesign());
						model.addAttribute("affectedDomainDesigns",impact.getLstUsedDomainDesign());
						model.addAttribute("affectedTableDesigns",impact.getLstUsedTableDesign());
						SqlDesignDesignForm sqlForm = beanMapper.map(sqlDesignCompound, SqlDesignDesignForm.class);
						sqlForm.setShowImpactFlag(form.getShowImpactFlag());
						model.addAttribute("sqlDesignDesignForm", sqlForm);
						destination = FORM_VIEW_DELETE_AFFECTION;
						return destination;
					}
				}
				destination = processDelete(form, model, redirectAttr);
			} catch(BusinessException ex){
				redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_SQLDESIGN));
				redirectAttr.addFlashAttribute("message", ex.getResultMessages());
				return DbDomainConst.REDIRECT_DELETION_SUCCESS;
			}
		} else {
			try {
				sqlDesignService.modifyDesignStatus(sqlDesignCompound, this.initCommon());
				redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(SqlDesignMessageConst.SC_SQLDESIGN_SQL_DESIGN)));
				redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_SQLDESIGN));
				destination = DbDomainConst.REDIRECT_MODIFY_SUCCESS;
			} catch(BusinessException ex) {
				redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_SQLDESIGN));
				redirectAttr.addFlashAttribute("message", ex.getResultMessages());
				return DbDomainConst.REDIRECT_MODIFY_SUCCESS;
			}
		}
		
		return destination;
	}
	
	/**
	 * Display modify.
	 *
	 * @param form the form
	 * @param result the result
	 * @param redirectAttr the redirect attr
	 * @param model the model
	 * @param sessionStatus the session status
	 * @return the string
	 */
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	public String displayModify(@Validated SqlDesignDesignForm form,BindingResult result, RedirectAttributes redirectAttr, Model model) {
		this.checkChangeProject(false);
		String destination = null;
		SqlDesign sqlDesign = beanMapper.map(form.getSqlDesignForm(), SqlDesign.class);
		SqlDesignCompound sqlDesignCompound = null;
		try {
			sqlDesignCompound = sqlDesignService.findCompoundById(sqlDesign.getSqlDesignId());
			if(sqlDesignCompound.getSqlDesign().getDesignType()== SQLDesignType.SQL_BUILDER){
				form = beanMapper.map(sqlDesignCompound,SqlDesignDesignForm.class);
				model.addAttribute(DESIGN_FORM_NAME,form);
				destination = FORM_MODIFY;
			} else if(sqlDesignCompound.getSqlDesign().getDesignType()== SQLDesignType.ADVANCED_SQL){
				model.addAttribute(ADVANCED_DESIGN_FORM_NAME,beanMapper.map(sqlDesignCompound,SqlDesignAdvancedDesignForm.class));
				model.addAttribute("sqlDesignFunctionGroups",sqlDesignService.findAllFunctionCode(SessionUtils.getCurrentDatabaseType()));
				destination = FORM_MODIFY_ADVANCED;
			}
		} catch(BusinessException ex){
			if(MODE_VIEW.equals(form.getMode())) {
				model.addAttribute("message", ex.getResultMessages());
				model.addAttribute(DESIGN_FORM_NAME, null);
				destination = FORM_VIEW;
			} else {
				//if(MODE_SEARCH.equals(form.getMode())) {
					redirectAttr.addFlashAttribute("message", ex.getResultMessages());
					destination = REDIRECT_SEARCH;
				//} 
			}
		} 
		return destination;
	}
	
	@RequestMapping(value = {"modify","modifyAdvanced"}, method = RequestMethod.POST,params="jsonBack")
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	public String displayModifyBack(RedirectAttributes redirectAttr, Model model,@RequestParam("formJson")String formJson,@RequestParam Boolean isAdvanced) {
		this.checkChangeProject(false);
		String destination = null;
		if(isAdvanced==null){
			return destination;
		}
		if(isAdvanced){
			model.addAttribute(ADVANCED_DESIGN_FORM_NAME,DataTypeUtils.toObject(formJson, SqlDesignAdvancedDesignForm.class));
			model.addAttribute("sqlDesignFunctionGroups",sqlDesignService.findAllFunctionCode(SessionUtils.getCurrentDatabaseType()));
			destination = FORM_MODIFY_ADVANCED;
		} else {
			model.addAttribute(DESIGN_FORM_NAME,DataTypeUtils.toObject(formJson, SqlDesignDesignForm.class));
			model.addAttribute("sqlDesignFunctionGroups",sqlDesignService.findAllFunctionCode(SessionUtils.getCurrentDatabaseType()));
			destination = FORM_MODIFY;
		}
		return destination;
	}
	
	/**
	 * Display modify advanced.
	 *
	 * @param form the form
	 * @param model the model
	 * @param sessionStatus the session status
	 * @param pageable the pageable
	 * @return the string
	 */
	@RequestMapping(value = "modifyAdvanced", method = RequestMethod.GET)
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	public String displayModifyAdvanced(SqlDesignAdvancedDesignForm form, Model model, SessionStatus sessionStatus,@PageableDefault Pageable pageable) {
		model.addAttribute("sqlDesignFunctionGroups",sqlDesignService.findAllFunctionCode(SessionUtils.getCurrentDatabaseType()));
		return FORM_MODIFY_ADVANCED;
	}
	
	/**
	 * Display register.
	 *
	 * @param form the form
	 * @param model the model
	 * @param redirectAttr the redirect attr
	 * @return the string
	 */
	@RequestMapping(value = "register", method = RequestMethod.GET)
	@TransactionTokenCheck(value="register",type=TransactionTokenType.BEGIN)
	public String displayRegister(SqlDesignDesignForm form, Model model, RedirectAttributes redirectAttr) {
		this.checkChangeProject(true);
		return FORM_REGISTER;
	}
	
	/**
	 * Display register advanced.
	 *
	 * @param form the form
	 * @param model the model
	 * @param redirectAttr the redirect attr
	 * @return the string
	 */
	@RequestMapping(value = "registerAdvanced", method = RequestMethod.GET)
	@TransactionTokenCheck(value="register",type=TransactionTokenType.BEGIN)
	public String displayRegisterAdvanced(SqlDesignAdvancedDesignForm form, Model model, RedirectAttributes redirectAttr) {
		model.addAttribute("sqlDesignFunctionGroups",sqlDesignService.findAllFunctionCode(SessionUtils.getCurrentDatabaseType()));
		return FORM_REGISTER_ADVANCED;
	}
	
	/**
	 * Display search.
	 *
	 * @param init the init
	 * @param form the form
	 * @param model the model
	 * @param sessionStatus the session status
	 * @param pageable the pageable
	 * @return the string
	 */
	@RequestMapping(value = "search")
	public String displaySearch(@RequestParam(value="init",required=false)String init,SqlDesignSearchForm form, Model model,SessionStatus sessionStatus,@PageableDefault Pageable pageable) {
		if(init != null){
			sessionStatus.setComplete();
			form = new SqlDesignSearchForm();
			model.addAttribute(SEARCH_FORM_NAME, form);
			super.setOldProjectId(SessionUtils.getCurrentProject().getProjectId());
		}
		this.checkChangeProject(false);
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction("/sqldesign/search"), pageable.getSort());
		SqlDesignSearchCriteria criteria = beanMapper.map(form, SqlDesignSearchCriteria.class);
		Page<SqlDesign> page=sqlDesignService.findPageByCriteria(criteria, pageable,this.initCommon());
		beanMapper.map(criteria, form);
		model.addAttribute("page", page);
		
		return FORM_SEARCH;
	}
	
	/**
	 * Display view.
	 *
	 * @param form the form
	 * @param model the model
	 * @param redirectAttr the redirect attr
	 * @return the string
	 */
	@RequestMapping(value = "view", method = {RequestMethod.GET,RequestMethod.POST})
	@TransactionTokenCheck(value="view",type=TransactionTokenType.BEGIN)
	public String displayView(SqlDesignDesignForm form,  Model model, RedirectAttributes redirectAttr) {
		this.checkChangeProject(false);
		String destination = null;
		SqlDesignCompound sqlDesignCompound = beanMapper.map(form, SqlDesignCompound.class);
		String openOwner = form.getOpenOwner();
		try {
			sqlDesignCompound = sqlDesignService.findCompoundById(sqlDesignCompound.getSqlDesign().getSqlDesignId());
			if(sqlDesignCompound.getSqlDesign().getDesignType() == SQLDesignType.SQL_BUILDER){
				beanMapper.map(sqlDesignCompound, form);
				form.setOpenOwner(openOwner);
				destination = FORM_VIEW;
			} else if(sqlDesignCompound.getSqlDesign().getDesignType() == SQLDesignType.ADVANCED_SQL) {
				SqlDesignAdvancedDesignForm advancedForm = beanMapper.map(sqlDesignCompound, SqlDesignAdvancedDesignForm.class);
				advancedForm.setOpenOwner(openOwner);
				advancedForm.setShowImpactFlag(form.getShowImpactFlag());
				model.addAttribute(ADVANCED_DESIGN_FORM_NAME,advancedForm);
				destination = FORM_VIEW_ADVANCED;
			}
		} catch(BusinessException ex){
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			destination = REDIRECT_SEARCH;
		}
		return destination;
	}
	
/*	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		sqlDesignService.setWorkingProjectId(SessionUtils.getCurrentProjectId());
		sqlDesignService.setWorkingLanguageId(SessionUtils.getCurrentLanguageId());
		sqlDesignService.setAccountId(SessionUtils.getAccountId());
	}*/
	/**
	 * Inits the binder design form.
	 *
	 * @param webDataBinder the web data binder
	 */
	@InitBinder(DESIGN_FORM_NAME)
	public void initBinderDesignForm(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(sqlDesignDesignFormValidator);
		// register characters trimmer for String-type field in form bean
	 	webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}
	/**
	 * Inits the binder for advanced design form.
	 *
	 * @param webDataBinder the web data binder
	 */
	@InitBinder(ADVANCED_DESIGN_FORM_NAME)
	public void initBinderForAdvancedDesignForm(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(sqlDesignAdvancedDesignFormValidator);
		// register characters trimmer for String-type field in form bean
	 	webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}
	
	@RequestMapping(value = "modify", method = RequestMethod.POST, params = "isJsonForm")
	@TransactionTokenCheck(value="modify",type=TransactionTokenType.IN)
	public String processModify(RedirectAttributes redirectAttr, Model model,@RequestParam("formJson")String formJson) {
		SqlDesignDesignForm form = DataTypeUtils.toObject(formJson, SqlDesignDesignForm.class);
		this.checkChangeProject(true);
		String destination=null;
		SqlDesignCompound sqlDesignCompound = beanMapper.map(form, SqlDesignCompound.class);

		try {
			sqlDesignService.modifySqlDesign(sqlDesignCompound, this.initCommon());
		} catch(BusinessException ex){
			model.addAttribute("message",ex.getResultMessages());
			model.addAttribute("sqlDesignDesignForm",form);
			destination = FORM_MODIFY;
			return destination;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(SqlDesignMessageConst.SC_SQLDESIGN_SQL_DESIGN)));
		destination = REDIRECT_SEARCH;
		
		return destination;
	}
	
	/**
	 * Process modify.
	 *
	 * @param form the form
	 * @param result the result
	 * @param redirectAttr the redirect attr
	 * @param model the model
	 * @param sessionStatus the session status
	 * @return the string
	 */
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	@TransactionTokenCheck(value="modify",type=TransactionTokenType.IN)
	public String processModify(@Validated(value=SqlDesignValidationGroup.class) SqlDesignDesignForm form,BindingResult result, RedirectAttributes redirectAttr, Model model,SessionStatus sessionStatus) {
		this.checkChangeProject(true);
		String destination=null;
		if (result.hasErrors()) {
			// ValidationUtils.setBindingResult(result,model);
			return FORM_MODIFY;
		}
		SqlDesignCompound sqlDesignCompound = beanMapper.map(form, SqlDesignCompound.class);

		try {
			sqlDesignService.modifySqlDesign(sqlDesignCompound, this.initCommon());
		} catch(BusinessException ex){
			model.addAttribute("message",ex.getResultMessages());
			destination = FORM_MODIFY;
			return destination;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(SqlDesignMessageConst.SC_SQLDESIGN_SQL_DESIGN)));
		destination = REDIRECT_SEARCH;
		
		return destination;
	}
	/**
	 * Process modify advanced.
	 *
	 * @param form the form
	 * @param result the result
	 * @param redirectAttr the redirect attr
	 * @param model the model
	 * @param sessionStatus the session status
	 * @return the string
	 */
	@RequestMapping(value = "modifyAdvanced", method = RequestMethod.POST, params = "isJsonForm")
	@TransactionTokenCheck(value="modify",type=TransactionTokenType.IN)
	public String processModifyAdvanced(SqlDesignAdvancedDesignForm form,BindingResult result,@Param("formJson")String formJson, RedirectAttributes redirectAttr, Model model,SessionStatus sessionStatus) {
		form = DataTypeUtils.toObject(formJson, SqlDesignAdvancedDesignForm.class);
		String destination="";
		model.addAttribute("sqlDesignFunctionGroups",sqlDesignService.findAllFunctionCode(SessionUtils.getCurrentDatabaseType()));
		this.checkChangeProject(true);
		SqlDesignCompound sqlDesignCompound = beanMapper.map(form, SqlDesignCompound.class);
		try {
			sqlDesignService.modifyAdvancedSqlDesign(sqlDesignCompound, this.initCommon());
		} catch(BusinessException ex){
			model.addAttribute("message",ex.getResultMessages());
			model.addAttribute("sqlDesignAdvancedDesignForm",form);
			destination = FORM_MODIFY_ADVANCED;
			return destination;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(SqlDesignMessageConst.SC_SQLDESIGN_SQL_DESIGN)));
		destination = REDIRECT_SEARCH;
		return destination;
	}
	
	@RequestMapping(value = "modifyAdvanced", method = RequestMethod.POST)
	@TransactionTokenCheck(value="modify",type=TransactionTokenType.IN)
	public String processModifyAdvanced(@Validated(value=SqlDesignValidationGroup.class) SqlDesignAdvancedDesignForm form,BindingResult result, RedirectAttributes redirectAttr, Model model,SessionStatus sessionStatus) {
		String destination="";
		model.addAttribute("sqlDesignFunctionGroups",sqlDesignService.findAllFunctionCode(SessionUtils.getCurrentDatabaseType()));
		if (result.hasErrors()) {
			// ValidationUtils.setBindingResult(result,model);
			return FORM_MODIFY_ADVANCED;
		}
		this.checkChangeProject(true);
		SqlDesignCompound sqlDesignCompound = beanMapper.map(form, SqlDesignCompound.class);
		try {
			sqlDesignService.modifyAdvancedSqlDesign(sqlDesignCompound, this.initCommon());
		} catch(BusinessException ex){
			model.addAttribute("message",ex.getResultMessages());
			destination = FORM_MODIFY_ADVANCED;
			return destination;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(SqlDesignMessageConst.SC_SQLDESIGN_SQL_DESIGN)));
		destination = REDIRECT_SEARCH;
		return destination;
	}
	
	@RequestMapping(value = "modifyAdvancedConfirm", method = RequestMethod.POST)
	@TransactionTokenCheck(value="modify",type=TransactionTokenType.IN)
	public String processModifyAdvancedConfirm(@Validated(value=SqlDesignValidationGroup.class) SqlDesignAdvancedDesignForm form,BindingResult result, Model model, RedirectAttributes redirectAttr, SessionStatus sessionStatus) {
		this.checkChangeProject(true);
		String destination=null;
		if (result.hasErrors()) {
			// ValidationUtils.setBindingResult(result,model);
			return FORM_MODIFY_ADVANCED;
		}
		boolean isNotShowImpact = form.getShowImpactFlag() == null || form.getShowImpactFlag() == false;
		SqlDesignCompound sqlDesignCompound = beanMapper.map(form, SqlDesignCompound.class);
//		sqlDesignService.findAllDeletionAffection(sqlDesignCompound, this.initCommon());
//		if(isNotShowImpact || CollectionUtils.isEmpty(sqlDesignCompound.getAffectedBusinessDesigns()) && 
//				CollectionUtils.isEmpty(sqlDesignCompound.getAffectedScreenDesigns())){
//			destination = processModifyAdvanced(form, result, redirectAttr, model, sessionStatus);
//		} else{
//			model.addAttribute("affectedBusinessDesigns",sqlDesignCompound.getAffectedBusinessDesigns());
//			model.addAttribute("affectedScreenDesigns",sqlDesignCompound.getAffectedScreenDesigns());
//			model.addAttribute("formJson", DataTypeUtils.toJson(form));
//			destination=FORM_MODIFY_ADVANCED_AFFECTION;
//		}
		
		CommonModel common = this.initCommon();
		ImpactSQLDesign impact = new ImpactSQLDesign();
		if(!isNotShowImpact){
			impact = impactSQLDesignShareService.detectListAffectedWhenModify(sqlDesignCompound, common, false);
			if(impact.getImpactFlag()){
				model.addAttribute("affectedBusinessDesigns",impact.getLstUsedBusinessDesign());
				model.addAttribute("affectedDomainDesigns",impact.getLstUsedDomainDesign());
				model.addAttribute("affectedTableDesigns",impact.getLstUsedTableDesign());
				model.addAttribute("formJson", DataTypeUtils.toJson(form));
				destination=FORM_MODIFY_ADVANCED_AFFECTION;
				return destination;
			}
		}
		destination = processModifyAdvanced(form, result, redirectAttr, model, sessionStatus);
		return destination;
	}
	
	/**
	 * Process modify.
	 *
	 * @param form the form
	 * @param result the result
	 * @param redirectAttr the redirect attr
	 * @param model the model
	 * @param sessionStatus the session status
	 * @return the string
	 */
	@RequestMapping(value = "modifyConfirm", method = RequestMethod.POST)
	@TransactionTokenCheck(value="modify",type=TransactionTokenType.IN)
	public String processModifyConfirm(@Validated(value=SqlDesignValidationGroup.class) SqlDesignDesignForm form,BindingResult result,Model model, RedirectAttributes redirectAttr, SessionStatus sessionStatus) {
		this.checkChangeProject(true);
		String destination=null;
		if (result.hasErrors()) {
			// ValidationUtils.setBindingResult(result,model);
			return FORM_MODIFY;
		}
		SqlDesignCompound sqlDesignCompound = beanMapper.map(form, SqlDesignCompound.class);
		if(BooleanUtils.isTrue(form.getSqlDesignForm().getIsConversion())){
			SqlDesignAdvancedDesignForm advancedForm = new SqlDesignAdvancedDesignForm();
			advancedForm.setSqlDesignForm(form.getSqlDesignForm());
			advancedForm.setInputForm(form.getInputForm());
			advancedForm.setOutputForm(form.getOutputForm());
			model.addAttribute("sqlDesignAdvancedDesignForm", advancedForm);
			model.addAttribute("sqlDesignFunctionGroups",sqlDesignService.findAllFunctionCode(SessionUtils.getCurrentDatabaseType()));
			destination = FORM_MODIFY_ADVANCED;
		} else {
			boolean isNotShowImpact = form.getShowImpactFlag() == null || form.getShowImpactFlag() == false;
//			sqlDesignService.findAllDeletionAffection(sqlDesignCompound, this.initCommon());
//			if(isNotShowImpact || CollectionUtils.isEmpty(sqlDesignCompound.getAffectedBusinessDesigns()) && 
//					CollectionUtils.isEmpty(sqlDesignCompound.getAffectedScreenDesigns())){
//				destination = processModify(form, result, redirectAttr, model, sessionStatus);
//			} else{
//				model.addAttribute("affectedBusinessDesigns",sqlDesignCompound.getAffectedBusinessDesigns());
//				model.addAttribute("affectedScreenDesigns",sqlDesignCompound.getAffectedScreenDesigns());
//				model.addAttribute("formJson", DataTypeUtils.toJson(form));
//				destination=FORM_MODIFY_AFFECTION;
//			}
			CommonModel common = this.initCommon();
			ImpactSQLDesign impact = new ImpactSQLDesign();
			if(!isNotShowImpact){
				impact = impactSQLDesignShareService.detectListAffectedWhenModify(sqlDesignCompound, common, false);
				if(impact.getImpactFlag()){
					model.addAttribute("affectedBusinessDesigns",impact.getLstUsedBusinessDesign());
					model.addAttribute("affectedDomainDesigns",impact.getLstUsedDomainDesign());
					model.addAttribute("affectedTableDesigns",impact.getLstUsedTableDesign());
					model.addAttribute("formJson", DataTypeUtils.toJson(form));
					destination=FORM_MODIFY_AFFECTION;
					return destination;
				}
			}
			destination = processModify(form, result, redirectAttr, model, sessionStatus);
		}
		return destination;
	}
	
	
	/**
	 * Process register.
	 *
	 * @param form the form
	 * @param result the result
	 * @param redirectAttr the redirect attr
	 * @param model the model
	 * @param sessionStatus the session status
	 * @return the string
	 */
	@RequestMapping(value = "register", method = RequestMethod.POST)
	@TransactionTokenCheck(value="register",type=TransactionTokenType.IN)
	public String processRegister(@Validated(value=SqlDesignValidationGroup.class) SqlDesignDesignForm form,BindingResult result, RedirectAttributes redirectAttr, Model model,SessionStatus sessionStatus) {
		if (result.hasErrors()) {
			// ValidationUtils.setBindingResult(result,model);
			return FORM_REGISTER;
		}
		this.checkChangeProject(true);
		SqlDesignCompound sqlDesignCompound = beanMapper.map(form, SqlDesignCompound.class);

		try {
			CommonModel common = this.initCommon();

			Long accountId = SessionUtils.getAccountId();
			sqlDesignCompound.getSqlDesign().setCreatedBy(accountId);
			sqlDesignCompound.getSqlDesign().setUpdatedBy(accountId);
			sqlDesignService.registerSqlDesign(sqlDesignCompound, common);
		} catch(BusinessException ex){
			model.addAttribute("message",ex.getResultMessages());
			return FORM_REGISTER;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage(SqlDesignMessageConst.SC_SQLDESIGN_SQL_DESIGN)));

		return REDIRECT_SEARCH;
	}
	
	/**
	 * Process register advanced.
	 *
	 * @param form the form
	 * @param result the result
	 * @param redirectAttr the redirect attr
	 * @param model the model
	 * @param sessionStatus the session status
	 * @return the string
	 */
	@RequestMapping(value = "registerAdvanced", method = RequestMethod.POST)
	@TransactionTokenCheck(value="register",type=TransactionTokenType.IN)
	public String processRegisterAdvanced(@Validated(value=SqlDesignValidationGroup.class) SqlDesignAdvancedDesignForm form,BindingResult result, RedirectAttributes redirectAttr, Model model,SessionStatus sessionStatus) {
		model.addAttribute("sqlDesignFunctionGroups",sqlDesignService.findAllFunctionCode(SessionUtils.getCurrentDatabaseType()));
		if (result.hasErrors()) {
			// ValidationUtils.setBindingResult(result,model);
			return FORM_REGISTER_ADVANCED;
		}
		this.checkChangeProject(true);
		SqlDesignCompound sqlDesignCompound = beanMapper.map(form, SqlDesignCompound.class);

		try {
			sqlDesignService.registerAdvancedSqlDesign(sqlDesignCompound,this.initCommon());
		} catch(BusinessException ex){
			model.addAttribute("message",ex.getResultMessages());
			return FORM_REGISTER_ADVANCED;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage(SqlDesignMessageConst.SC_SQLDESIGN_SQL_DESIGN)));

		return REDIRECT_SEARCH;
	}
	
	/**
	 * Process search.
	 *
	 * @param form the form
	 * @param model the model
	 * @param sessionStatus the session status
	 * @param pageable the pageable
	 * @return the string
	 */
	@RequestMapping(value = "search", method = RequestMethod.POST)
	public String processSearch(SqlDesignSearchForm form, Model model, SessionStatus sessionStatus,@PageableDefault Pageable pageable) {
		this.checkChangeProject(false);
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction("/sqldesign/search"), pageable.getSort());
		SqlDesignSearchCriteria criteria = beanMapper.map(form, SqlDesignSearchCriteria.class);
		Page<SqlDesign> page=sqlDesignService.findPageByCriteria(criteria, pageable,this.initCommon());
		beanMapper.map(criteria, form);
		model.addAttribute("page", page);

		return FORM_SEARCH;
	}
	
	@RequestMapping(value = "getSqlDesignResults", method = RequestMethod.GET)
	@ResponseBody
	public SqlDesignDesignForm getSqlDesignResults(@RequestParam Long sqlDesignId) {
		SqlDesignCompound sqlDesignCompound = sqlDesignService.findCompoundById(sqlDesignId);
		SqlDesignDesignForm form = beanMapper.map(sqlDesignCompound,SqlDesignDesignForm.class);
		return form;
		//return sqlDesignService.findAllSqlDesignResults(sqlDesignCompound);
	}

}
