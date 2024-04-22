package org.terasoluna.qp.app.domaindesign;

import java.sql.Timestamp;
import java.text.ParseException;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;
import org.terasoluna.qp.app.account.AccountController;
import org.terasoluna.qp.app.common.BaseController;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.DateUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.Autocomplete;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.DomainDesign;
import org.terasoluna.qp.domain.repository.domaindesign.DomainDesignCriteria;
import org.terasoluna.qp.domain.service.domaindesign.DomainDesignService;

import com.fasterxml.jackson.annotation.JsonProperty;

@Controller
@RequestMapping(value = "domaindesign")
@TransactionTokenCheck(value = "domaindesign")
@SessionAttributes(types = { DomainDesignSearchForm.class })
public class DomainDesignController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
	private static final String ACTION_SEARCH = "/domaindesign/search";
	private static final String SEARCH_LINK = "domaindesign/searchForm";
	private static final String REGISTER_LINK = "domaindesign/registerForm";
	private static final String VIEW_LINK = "domaindesign/viewForm";
	private static final String VIEW_BLOGIC_LINK = "domaindesign/viewListAffectedChangeDesignForm";
	private static final String REDIRECT_BLOGIC_LINK = "domaindesign/viewListAffectedChangeDesignForm";
	private static final String MODIFY_LINK = "domaindesign/modifyForm";
	private static final String REDIRECT_SEARCH ="redirect:/domaindesign/search";
	//private static final String REDIRECT_DELETECOMPLETE = "redirect:/complete";
	//private static final String MODE_SEARCH = "0";
	private static final String MODE_VIEW = "1";

	
	
	@Inject
	DomainDesignService domainDesignService;

	@Inject
	Mapper beanMapper;

	@Inject
	DomainDesignMinMaxValidator domainMinMaxValidator;

	@ModelAttribute
	public void initService() {
		moduleCode = CommonMessageConst.TQP_DOMAINDESIGN;
	}
	
	@InitBinder("domainDesignForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(domainMinMaxValidator);
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	
	@ModelAttribute
	public DomainDesignForm setUpForm() {
		DomainDesignForm domainDesignForm = new DomainDesignForm();
		logger.debug("Init form {0}", domainDesignForm);
		return domainDesignForm;
	}

	@ModelAttribute
	public DomainDesignSearchForm setUpSearchForm() {
		DomainDesignSearchForm obj = new DomainDesignSearchForm();
		logger.debug("Init form {0}", obj);
		return obj;
	}

	/**
	 * return to search screen
	 * 
	 * @param domainDesignSearchForm
	 * @param model
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "search", method=RequestMethod.GET )
	public String displaySearch(@RequestParam(value = "init", required = false) String init, @ModelAttribute DomainDesignSearchForm domainDesignSearchForm,
			Model model,@PageableDefault Pageable pageable, SessionStatus sessionStatus) {
		if(init != null){
			sessionStatus.setComplete();
			domainDesignSearchForm = setUpSearchForm();
			model.addAttribute("domainDesignSearchForm", domainDesignSearchForm);
			super.setOldProjectId(SessionUtils.getCurrentProject().getProjectId());
		}
		
		checkChangeProject(false);
		
		pageable = new PageRequest(pageable.getPageNumber(),PageSizeUtils.getPageSizeOfAction(ACTION_SEARCH),pageable.getSort());
		domainDesignSearchForm.setProjectId(SessionUtils.getCurrentProjectId());
		DomainDesignCriteria domainCriteria = beanMapper.map(domainDesignSearchForm, DomainDesignCriteria.class);
		Page<DomainDesign> pageDomain = domainDesignService.findPageByCriteria(domainCriteria, pageable);
		/*domainService.findAllValidationRule();*/
		model.addAttribute("page", pageDomain);

		return SEARCH_LINK;
	}

	/**
	 * search DomainDesign
	 * 
	 * @param domainDesignSearchForm
	 * @param model
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "search", method =RequestMethod.POST)
	public String processSearch(@ModelAttribute DomainDesignSearchForm domainDesignSearchForm,
			Model model,@PageableDefault Pageable pageable) {
		checkChangeProject(false);
		
		pageable = new PageRequest(pageable.getPageNumber(),PageSizeUtils.getPageSizeOfAction(ACTION_SEARCH),pageable.getSort());
		domainDesignSearchForm.setProjectId(SessionUtils.getCurrentProjectId());
		DomainDesignCriteria domainCriteria = beanMapper.map(domainDesignSearchForm, DomainDesignCriteria.class);
		Page<DomainDesign> pageDomain = domainDesignService.findPageByCriteria(domainCriteria, pageable);
		model.addAttribute("page", pageDomain);
		
		return SEARCH_LINK;
	}
	
	/**
	 * return to create a new DomainDesign screen
	 * 
	 * @param model
	 * @return
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "register")
	public String displayRegister(@ModelAttribute DomainDesignForm domainDesignForm, RedirectAttributes redirectAttr, Model model) {
		try {
			checkChangeProject(true);
			domainDesignForm.setFmtCodelist(domainDesignService.findAllValidationRuleByStatys(DbDomainConst.DisplayType.USED));
			domainDesignForm.setConstrainsType(0);
			model.addAttribute("domainDesignForm", domainDesignForm);
			domainDesignForm.setProjectId(SessionUtils.getCurrentProjectId());
			return REGISTER_LINK;
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return REDIRECT_SEARCH;
		}
	}

	/**
	 * Create a new DomainDesign
	 * 
	 * @param domainDesignForm
	 * @param model
	 * @return
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.IN)
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String processRegister(@Validated @ModelAttribute DomainDesignForm domainDesignForm,
			BindingResult result, RedirectAttributes redirectAttr, Model model) {
		try {
			checkChangeProject(true);
			DomainDesign domain = beanMapper.map(domainDesignForm, DomainDesign.class);

			if (result.hasErrors()) {
				domainDesignService.setFmtCodelist(domain);
				domainDesignForm = beanMapper.map(domain, DomainDesignForm.class);
				model.addAttribute("domainDesignForm", domainDesignForm);
				ValidationUtils.setBindingResult(result, model);
				return REGISTER_LINK;
			}

			CommonModel common = this.initCommon(); 

			Long accountId = SessionUtils.getAccountId();
			domain.setCreatedBy(accountId);
			domain.setUpdatedBy(accountId);
			
			// Check condition for insert, modify into field[pattern_format] of domain design
			checkConditionPatternFormat(domain);
			
			domainDesignService.registerDomain(domain, common);

		} catch(BusinessException ex){
			model.addAttribute("message", ex.getResultMessages());
			domainDesignForm.setFmtCodelist(domainDesignService.findAllValidationRuleByStatys(DbDomainConst.DisplayType.USED));
			return REGISTER_LINK;
		}

		redirectAttr.addFlashAttribute("message",
				ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, 
						MessageUtils.getMessage(CommonMessageConst.TQP_DOMAINDESIGN)));
		
		return REDIRECT_SEARCH;
	}

	/**
	 * return view screen
	 * 
	 * @param domainDesignForm
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "view")
	public String displayView(DomainDesignForm domainDesignForm, Model model, RedirectAttributes redirectAttr) {
		
		checkChangeProject(false);
		
		String destination = "";
		try {
			DomainDesign domain = domainDesignService.findOne(domainDesignForm.getDomainId(), this.initCommon(), false);
			model.addAttribute("domain", domain);
			List<Autocomplete> listOfTableDesign = domainDesignService.listOfTableDeignUsed(domain.getDomainId());
			model.addAttribute("listOfTableDesign", listOfTableDesign);
			destination = VIEW_LINK;
		} catch (BusinessException ex) {
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			destination = REDIRECT_SEARCH;
		}
		return destination;
	}

	/**
	 * delete a domain design
	 * 
	 * @param domainDesignForm
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	public String processDelete(DomainDesignForm domainDesignForm, Model model,RedirectAttributes redirectAttr) {
		
		CommonModel common = this.initCommon();
		DomainDesign domain = null;
		try {
			domain = domainDesignService.findOne(domainDesignForm.getDomainId(), common, true);
			if (DateUtils.compare(domain.getUpdatedDate(), domainDesignForm.getUpdatedDate()) == 1) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
			}
			//checkChangeProject(true);
			domainDesignService.deleteDomain(domain);
		} catch(BusinessException ex){
			/*model.addAttribute("message", ex.getResultMessages());
			//If error when delete then check exist
			try {
				//DomainDesign domain = domainDesignService.findOne(domainDesignForm.getDomainId(), common, true);
				List<Autocomplete> listOfTableDesign = domainDesignService.listOfTableDeignUsed(domainDesignForm.getDomainId());
				model.addAttribute("listOfTableDesign", listOfTableDesign);
				model.addAttribute("domain", domain);
			} catch (BusinessException e) {
				model.addAttribute("message", e.getResultMessages());
			}
			return VIEW_LINK;*/
			
			redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_DOMAINDESIGN));
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			return DbDomainConst.REDIRECT_DELETION_SUCCESS;
			
		}
		redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_DOMAINDESIGN));
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004, MessageUtils.getMessage(CommonMessageConst.TQP_DOMAINDESIGN)));
		return DbDomainConst.REDIRECT_DELETION_SUCCESS;
	}

	/**
	 * Return to edit a domainDesign screen
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public String displayModify(DomainDesignForm domainDesignForm, Model model, RedirectAttributes redirectAttr) {
		String destination = StringUtils.EMPTY;
		String mode = domainDesignForm.getMode();
		try {
			checkChangeProject(true);

			CommonModel common = this.initCommon();
			
			DomainDesign domainDesign = domainDesignService.findOne(domainDesignForm.getDomainId(), common, false);

			if (StringUtils.isNotBlank(domainDesign.getPatternFormat())) {
				// Check date, time, date time
				if (DataTypeUtils.equals(DbDomainConst.BaseType.DATE_BASETYPE, domainDesign.getBaseType())) {
					String currentFormat = DateUtils.getPatternDate(SessionUtils.getCurrentAccountProfile().getDateFormat());
					if (!DataTypeUtils.equalsIgnoreCase(domainDesign.getPatternFormat(), currentFormat)) {
						convertPatternForDesign(domainDesign, currentFormat);
					}
				} else if (DataTypeUtils.equals(DbDomainConst.BaseType.TIME_BASETYPE, domainDesign.getBaseType())) {
					String currentFormat = DateUtils.getPatternTime(SessionUtils.getCurrentAccountProfile().getTimeFormat());
					if (!DataTypeUtils.equalsIgnoreCase(domainDesign.getPatternFormat(), currentFormat)) {
						convertPatternForDesign(domainDesign, currentFormat);
					}
				} else if (DataTypeUtils.equals(DbDomainConst.BaseType.DATETIME_BASETYPE, domainDesign.getBaseType())) {
					String currentFormat = DateUtils.getPatternDateTime(SessionUtils.getCurrentAccountProfile().getDateTimeFormat());
					if (!DataTypeUtils.equalsIgnoreCase(domainDesign.getPatternFormat(), currentFormat)) {
						convertPatternForDesign(domainDesign, currentFormat);
					}
				}
			}

			domainDesign.setProjectId(SessionUtils.getCurrentProjectId());
			domainDesignService.setFmtCodelist(domainDesign);
			domainDesignService.loadUserDefineCodelist(domainDesign, common);
			domainDesignForm = beanMapper.map(domainDesign, DomainDesignForm.class);
			List<Autocomplete> listOfTableDesign = domainDesignService.listOfTableDeignUsed(domainDesignForm.getDomainId());
			model.addAttribute("listOfTableDesign", listOfTableDesign);
			model.addAttribute("domainDesignForm", domainDesignForm);

			destination = MODIFY_LINK;
		} catch (BusinessException be) {
			/*if (MODE_SEARCH.equals(mode)) {
				redirectAttr.addFlashAttribute("message", ex.getResultMessages());
				destination = REDIRECT_SEARCH;
			} else if (MODE_VIEW.equals(mode)) {
				model.addAttribute("message", ex.getResultMessages());
				model.addAttribute("domain", domainDesignForm);
				destination = VIEW_LINK;
			}*/

			if (MODE_VIEW.equals(mode)) {
				redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_DOMAINDESIGN)); // message title
				redirectAttr.addFlashAttribute("message", be.getResultMessages());// message content
				return DbDomainConst.REDIRECT_DELETION_SUCCESS;
			}

			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return REDIRECT_SEARCH;
		}

		/*} catch (BusinessException ex) {
			if (MODE_SEARCH.equals(mode)) {
				redirectAttr.addFlashAttribute("message", ex.getResultMessages());
				destination = REDIRECT_SEARCH;
			} else if (MODE_VIEW.equals(mode)) {
				model.addAttribute("message", ex.getResultMessages());
				model.addAttribute("domain", null);
				destination = VIEW_LINK;
			}
		}*/
		return destination;
	}
	
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST,params="jsonBack")
	public String displayModifyBack(RedirectAttributes redirectAttr, Model model, @JsonProperty @RequestParam("formJson") String formJson) {
		try {
			
			DomainDesignForm domainDesignForm = DataTypeUtils.toObject(formJson, DomainDesignForm.class);
			Boolean showImpactFlag = domainDesignForm.getShowImpactFlag();
			
			DomainDesign domainDesign = beanMapper.map(domainDesignForm, DomainDesign.class);
			domainDesignService.setFmtCodelist(domainDesign);
			List<Autocomplete> listOfTableDesign = domainDesignService.listOfTableDeignUsed(domainDesign.getDomainId());
			model.addAttribute("listOfTableDesign", listOfTableDesign);

			domainDesignForm = beanMapper.map(domainDesign, DomainDesignForm.class);
			domainDesignForm.setShowImpactFlag(showImpactFlag);

			model.addAttribute("domainDesignForm", domainDesignForm);
			this.checkChangeProject(true);
		} catch (BusinessException be) {
			model.addAttribute("message", be.getResultMessages());
		}
		return MODIFY_LINK;
	}

	/**
	 * return viewListAffectedChangeDesignForm screen
	 * 
	 * @param DomainDesignForm
	 * @param model
	 * @return
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "viewListAffectedChangeDesignForm", method = {RequestMethod.POST})
	public String displayViewBlogicPost(@Validated @ModelAttribute DomainDesignForm domainDesignForm, BindingResult result, RedirectAttributes redirectAttr, Model model){
		
		try {
			checkChangeProject(true);
		} catch (BusinessException be) {
			model.addAttribute("message", be.getResultMessages());
			return MODIFY_LINK;
		}

		CommonModel common = this.initCommon();
		DomainDesign domainDesign = beanMapper.map(domainDesignForm, DomainDesign.class);
		List<Autocomplete> listOfTableDesign = domainDesignService.listOfTableDeignUsed(domainDesignForm.getDomainId());
		if (result.hasErrors()) {
			try {
				domainDesignService.setFmtCodelist(domainDesign);
				domainDesignForm = beanMapper.map(domainDesign, DomainDesignForm.class);
				model.addAttribute("listOfTableDesign", listOfTableDesign);
				model.addAttribute("domainDesignForm", domainDesignForm);
				ValidationUtils.setBindingResult(result, model);
			} catch (BusinessException be) {
				model.addAttribute("message", be.getResultMessages());
			}
			return MODIFY_LINK;
		}
		
		/**
		 * DungNN - 20160705
		 * If check box show impact was checked then calculate list affect
		 */
		Boolean showImpactFlag = domainDesignForm.getShowImpactFlag(); 
		if (showImpactFlag) {
			try {
				domainDesignService.loadListAffected(domainDesign, false, common);
				domainDesignForm = beanMapper.map(domainDesign, DomainDesignForm.class);
				model.addAttribute("domainDesignForm", domainDesignForm);
				model.addAttribute("listOfTableDesign", listOfTableDesign);
				domainDesignForm.setShowImpactFlag(showImpactFlag);
				model.addAttribute("formJson", DataTypeUtils.toJson(domainDesignForm));
				
			} catch (BusinessException ex) {
				model.addAttribute("message", ex.getResultMessages());
				domainDesignForm.setFmtCodelist(domainDesignService.findAllValidationRuleByStatys(DbDomainConst.DisplayType.USED));
				//if(CommonMessageConst.ERR_SYS_0037.equals(ex.getResultMessages().getList().get(0).getCode())) {
					/*model.addAttribute("notExistFlg", "1");*/
					return MODIFY_LINK;
				//}
			}
			model.addAttribute("formJson", DataTypeUtils.toJson(domainDesignForm));
			return REDIRECT_BLOGIC_LINK;
		}
		
		if( CollectionUtils.isEmpty(domainDesign.getListBusinessLogics()) && CollectionUtils.isEmpty(domainDesign.getListSqlDesigns())){
			try {
				domainDesign.setUpdatedBy(SessionUtils.getAccountId());

				// Check condition for insert, modify into field[pattern_format] of domain design
				checkConditionPatternFormat(domainDesign);
				
				domainDesignService.modifyDomain(domainDesign, listOfTableDesign, common);
			} catch(BusinessException ex){
				domainDesignForm.setFmtCodelist(domainDesignService.findAllValidationRuleByStatys(DbDomainConst.DisplayType.USED));
				model.addAttribute("message", ex.getResultMessages());
				return MODIFY_LINK;
			}
			redirectAttr.addFlashAttribute("message",ResultMessages.info().add(CommonMessageConst.INF_SYS_0003,
					MessageUtils.getMessage(CommonMessageConst.TQP_DOMAINDESIGN)));
			return REDIRECT_SEARCH;
		}
		else{
			model.addAttribute("formJson", DataTypeUtils.toJson(domainDesignForm));
			return REDIRECT_BLOGIC_LINK;
		}
	}
	
	/**
	 * return viewListAffectedChangeDesignForm screen
	 * 
	 * @param DomainDesignForm
	 * @param model
	 * @return
	 */
	@TransactionTokenCheck(value = "modifyWithAffect", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "viewListAffectedChangeDesignForm", method = {RequestMethod.GET})
	public String displayViewBlogicGet(@Validated @ModelAttribute DomainDesignForm domainDesignForm, BindingResult result, RedirectAttributes redirectAttr, Model model){
		
		DomainDesign domainDesign = beanMapper.map(domainDesignForm, DomainDesign.class);
		try {
			domainDesignService.loadListAffected(domainDesign, false, this.initCommon());
			domainDesignForm = beanMapper.map(domainDesign, DomainDesignForm.class);
			model.addAttribute("domainDesignForm", domainDesignForm);
		} catch (BusinessException ex) {
			model.addAttribute("message", ex.getResultMessages());
			return VIEW_BLOGIC_LINK;
		}
		return VIEW_BLOGIC_LINK;
	}
	
	/**
	 * Modify subject area process
	 * 
	 * @return In the case success : REDIRECT_SEARCH other MODIFY_LINK
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String processModify(@Validated @ModelAttribute DomainDesignForm domainDesignForm, BindingResult result, RedirectAttributes redirectAttr, Model model, @JsonProperty @RequestParam("formJson") String formJson){
		List<Autocomplete> listOfTableDesign = domainDesignService.listOfTableDeignUsed(domainDesignForm.getDomainId());
		
		DomainDesign domaindesign = null;
		
		if(formJson.equals(StringUtils.EMPTY)){
			domaindesign = beanMapper.map(domainDesignForm, DomainDesign.class);
		}else{
			domaindesign = beanMapper.map(DataTypeUtils.toObject(formJson, DomainDesignForm.class), DomainDesign.class);
		}
		
		domaindesign.setDomainId(domainDesignForm.getDomainId());
		domaindesign.setUpdatedDate(domainDesignForm.getUpdatedDate());
		try {
			checkChangeProject(true);
			// Check condition for insert, modify into field[pattern_format] of domain design
			checkConditionPatternFormat(domaindesign);
			
			domainDesignService.modifyDomain(domaindesign, listOfTableDesign, this.initCommon());
		} catch (BusinessException ex) {
			model.addAttribute("message", ex.getResultMessages());
			/*if(CommonMessageConst.ERR_SYS_0037.equals(ex.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("notExistFlg", "1");
				return MODIFY_LINK;
			}*/
			domainDesignForm.setFmtCodelist(domainDesignService.findAllValidationRuleByStatys(DbDomainConst.DisplayType.USED));
			model.addAttribute("listOfTableDesign", listOfTableDesign);
			model.addAttribute("domainDesignForm", domainDesignForm);
			return MODIFY_LINK;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003,
				MessageUtils.getMessage(CommonMessageConst.TQP_DOMAINDESIGN)));
		SessionUtils.remove("domainDesignModify");
		return REDIRECT_SEARCH;
	}
	
	/**
	 * Convert pattern for date, time, date time
	 * 
	 * @return void
	 */
	private void convertPatternForDesign(DomainDesign domainDesign, String newPatternConvert) {
		try {
			// check null and process for default value
			if (StringUtils.isNotBlank(domainDesign.getDefaultValue())) {
				
				Timestamp defaultValue = DateUtils.parse(domainDesign.getDefaultValue(), domainDesign.getPatternFormat());
				domainDesign.setDefaultValue(DateUtils.formatDateTime(defaultValue, newPatternConvert));
			}
			if (StringUtils.isNotBlank(domainDesign.getMinVal())) {
				
				Timestamp defaultValue = DateUtils.parse(domainDesign.getMinVal(), domainDesign.getPatternFormat());
				domainDesign.setMinVal(DateUtils.formatDateTime(defaultValue, newPatternConvert));
			}
			if (StringUtils.isNotBlank(domainDesign.getMaxVal())) {
				
				Timestamp defaultValue = DateUtils.parse(domainDesign.getMaxVal(), domainDesign.getPatternFormat());
				domainDesign.setMaxVal(DateUtils.formatDateTime(defaultValue, newPatternConvert));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Check condition for insert, modify into field[pattern_format] of domain design
	 * 
	 * @return void
	 */
	private void checkConditionPatternFormat(DomainDesign domainDesign) {
		// Check blank
		if (StringUtils.isNotBlank(domainDesign.getMinVal()) || StringUtils.isNotBlank(domainDesign.getMaxVal()) || StringUtils.isNotBlank(domainDesign.getDefaultValue())) {
			// Check date, time, date time
			if (DataTypeUtils.equals(DbDomainConst.BaseType.DATE_BASETYPE, domainDesign.getBaseType())) {
				domainDesign.setPatternFormat(DateUtils.getPatternDate(SessionUtils.getCurrentAccountProfile().getDateFormat()));
			} else if (DataTypeUtils.equals(DbDomainConst.BaseType.TIME_BASETYPE, domainDesign.getBaseType())) {
				domainDesign.setPatternFormat(DateUtils.getPatternTime(SessionUtils.getCurrentAccountProfile().getTimeFormat()));
			} else if (DataTypeUtils.equals(DbDomainConst.BaseType.DATETIME_BASETYPE, domainDesign.getBaseType())) {
				domainDesign.setPatternFormat(DateUtils.getPatternDateTime(SessionUtils.getCurrentAccountProfile().getDateTimeFormat()));
			}
		}
		
	}
}
