package org.terasoluna.qp.app.externalobjectdefinition;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
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
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.ExternalObjectAttribute;
import org.terasoluna.qp.domain.model.ExternalObjectDefinition;
import org.terasoluna.qp.domain.repository.externalobjectdefinition.ExternalObjectDefinitionSearchCriteria;
import org.terasoluna.qp.domain.service.externalobjectdefinition.ExternalObjectDefinitionService;

@Controller
@RequestMapping(value = "externalobjectdefinition")
@TransactionTokenCheck(value = "externalobjectdefinition")
@SessionAttributes(types = { ExternalObjectDefinitionSearchForm.class })
public class ExternalObjectDefinitionController extends BaseController {

	private static final String SEARCH_FORM_PATH = "externalobjectdefinition/searchForm";

	private static final String SEARCH_REDIRECT_PATH = "redirect:/externalobjectdefinition/search";

	private static final String EXTERNAL_OBJECT_DEFINITION_SEARCH_FORM_NAME = "externalObjectDefinitionSearchForm";

	private static final String REGISTER_FORM_PATH = "externalobjectdefinition/registerForm";

	private static final String MODIFY_FORM_PATH = "externalobjectdefinition/modifyForm";

	private static final String VIEW_FORM_PATH = "externalobjectdefinition/viewForm";

	private static final String EXTERNAL_OBJECT_DEFINITION_FORM_NAME = "externalObjectDefinitionForm";

	private static final String SEARCH_ACTION_PATH = "/externalobjectdefinition/search";

	private static final String LINK_REGISTER = "externalobjectdefinition/registerForm";

	private static final String REDIRECT_DELETECOMPLETE = "redirect:/complete";

	private static final String MODE_SEARCH = "0";

	private static final String MODE_VIEW = "1";

	@Inject
	Mapper beanMapper;

	@Inject
	ExternalObjectDefinitionService externalObjectDefinitionService;

	@Inject
	ExternalObjectDefinitionValidator externalObjectDefinitionValidator;
	
	@InitBinder
	public void init() {
		moduleCode = CommonMessageConst.TQP_EXTERNALOBJECTDEFINITION;
	}

	/**
	 * Inits the binder external object definition form.
	 *
	 * @param webDataBinder
	 *            the web data binder
	 */
	@InitBinder(EXTERNAL_OBJECT_DEFINITION_FORM_NAME)
	public void initBinderDesignForm(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(externalObjectDefinitionValidator);
		// register characters trimmer for String-type field in form bean
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}

	@ModelAttribute
	public ExternalObjectDefinitionForm setupFormExternalObjectDefintion() {
		ExternalObjectDefinitionForm form = new ExternalObjectDefinitionForm();
		return form;
	}

	@ModelAttribute
	public ExternalObjectDefinitionSearchForm setupFormCriteria() {
		ExternalObjectDefinitionSearchForm form = new ExternalObjectDefinitionSearchForm();
		return form;
	}

	/**
	 * Initialize search external object definition
	 *
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String displaySearch(@RequestParam(value = "init", required = false) String init, @ModelAttribute ExternalObjectDefinitionSearchForm externalObjectDefinitionSearchForm, Model model, @PageableDefault Pageable pageable, SessionStatus sessionStatus) {
		if (init != null) {
			sessionStatus.setComplete();
			externalObjectDefinitionSearchForm = new ExternalObjectDefinitionSearchForm();
			model.addAttribute(EXTERNAL_OBJECT_DEFINITION_SEARCH_FORM_NAME, externalObjectDefinitionSearchForm);
			super.setOldProjectId(SessionUtils.getCurrentProject().getProjectId());
		}
		super.checkChangeProject(false);
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
		ExternalObjectDefinitionSearchCriteria externalObjectDefinitionSearchCriteria = beanMapper.map(externalObjectDefinitionSearchForm, ExternalObjectDefinitionSearchCriteria.class);

		CommonModel common = this.initCommon();
		Page<ExternalObjectDefinition> page = externalObjectDefinitionService.findPageByCriteria(externalObjectDefinitionSearchCriteria, pageable, common);
		model.addAttribute("page", page);
		return SEARCH_FORM_PATH;
	}

	/**
	 * Search external object definition process
	 *
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "search", method = { RequestMethod.POST })
	public String processSearch(ExternalObjectDefinitionSearchForm externalObjectDefinitionSearchForm, Model model, SessionStatus sessionStatus, @PageableDefault Pageable pageable) {
		super.checkChangeProject(false);
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction("/externalobjectdefinition/search"), pageable.getSort());
		ExternalObjectDefinitionSearchCriteria externalObjectDefinitionSearchCriteria = beanMapper.map(externalObjectDefinitionSearchForm, ExternalObjectDefinitionSearchCriteria.class);

		CommonModel common = this.initCommon();
		Page<ExternalObjectDefinition> page = externalObjectDefinitionService.findPageByCriteria(externalObjectDefinitionSearchCriteria, pageable, common);
		beanMapper.map(externalObjectDefinitionSearchCriteria, externalObjectDefinitionSearchForm);
		model.addAttribute("page", page);

		return SEARCH_FORM_PATH;
	}

	/**
	 * Initialize register external object definition
	 *
	 * @return EXTERNAL_REGISTER_FORM_PATH
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "register", method = { RequestMethod.GET })
	public String displayRegisterExternal(@ModelAttribute(EXTERNAL_OBJECT_DEFINITION_FORM_NAME) ExternalObjectDefinitionForm externalObjectDefinitionForm, Model model) {
		super.checkChangeProject(true);
		return REGISTER_FORM_PATH;
	}

	/**
	 * Process register external object definition
	 *
	 * @return SEARCH_REDIRECT_PATH
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.IN)
	@RequestMapping(value = "register", method = { RequestMethod.POST })
	public String processRegister(@Validated ExternalObjectDefinitionForm externalObjectDefinitionForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		super.checkChangeProject(true);

		ExternalObjectDefinition externalObjectDefinition = beanMapper.map(externalObjectDefinitionForm, ExternalObjectDefinition.class);
		if (result.hasErrors()) {
			model.addAttribute("externalObjectDefinitionForm", externalObjectDefinitionForm);
			return LINK_REGISTER;
		}
		try {
			CommonModel common = this.initCommon();
			externalObjectDefinitionService.registerExternalObjectDefinition(externalObjectDefinition, common);
		} catch (BusinessException ex) {
			model.addAttribute("message", ex.getResultMessages());
			return LINK_REGISTER;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage(CommonMessageConst.TQP_EXTERNALOBJECTDEFINITION)));
		return SEARCH_REDIRECT_PATH;
	}

	/**
	 * Initialize modify external object definition
	 *
	 * @return EXTERNAL_MODIFY_FORM_PATH
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public String displayModify(ExternalObjectDefinitionForm externalObjectDefinitionForm, Model model, RedirectAttributes redirectAttr) {
		if (externalObjectDefinitionForm.getExternalObjectType() == null || "0".equals(externalObjectDefinitionForm.getExternalObjectType())){
			return SEARCH_REDIRECT_PATH;
		}
		
		String destination = StringUtils.EMPTY;

		try {
			super.checkChangeProject(true);			
			CommonModel common = this.initCommon();

			Long accountId = SessionUtils.getAccountId();
			externalObjectDefinitionForm.setCreatedBy(accountId);
			externalObjectDefinitionForm.setUpdatedBy(accountId);
			ExternalObjectDefinition externalObjectDefinition = externalObjectDefinitionService.findExternalObjectDefinition(externalObjectDefinitionForm.getExternalObjectDefinitionId(),0, common);
			externalObjectDefinitionForm = beanMapper.map(externalObjectDefinition, ExternalObjectDefinitionForm.class);
			model.addAttribute(EXTERNAL_OBJECT_DEFINITION_FORM_NAME, externalObjectDefinitionForm);
			destination = MODIFY_FORM_PATH;
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			if (MODE_SEARCH.equals(externalObjectDefinitionForm.getMode())) {
				destination = SEARCH_REDIRECT_PATH;
			} else if (MODE_VIEW.equals(externalObjectDefinitionForm.getMode())) {
				externalObjectDefinitionForm.setHasErrors(true);
				model.addAttribute("notExistFlg", 1);
				model.addAttribute("message", be.getResultMessages());
				destination = VIEW_FORM_PATH;
			}
		}
		return destination;
	}

	/**
	 * Process modify external object definition
	 *
	 * @return SEARCH_REDIRECT_PATH
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String processModify(@Validated ExternalObjectDefinitionForm externalObjectDefinitionForm, BindingResult bindingResult, Model model, RedirectAttributes attributes) {
		super.checkChangeProject(true);
		
		if (externalObjectDefinitionForm.getExternalObjectType() == null || "0".equals(externalObjectDefinitionForm.getExternalObjectType())){
			return SEARCH_REDIRECT_PATH;
		}
		
		ExternalObjectDefinition externalObjectDefinition = beanMapper.map(externalObjectDefinitionForm, ExternalObjectDefinition.class);
		if (bindingResult.hasErrors()) {
			model.addAttribute("externalObjectDefinitionForm", externalObjectDefinitionForm);
			return MODIFY_FORM_PATH;
		}
		try {
			CommonModel common = this.initCommon();
			externalObjectDefinitionService.modifyExternalObjectDefinition(externalObjectDefinition, common);
			attributes.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(CommonMessageConst.TQP_EXTERNALOBJECTDEFINITION)));
			return SEARCH_REDIRECT_PATH;
		} catch (BusinessException ex) {

			if (CommonMessageConst.ERR_SYS_0037.equals(ex.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("notExistFlg", "1");
			}
			model.addAttribute("message", ex.getResultMessages());
			return MODIFY_FORM_PATH;
		}
	}

	/**
	 * return view screen
	 *
	 * @return
	 */
	@RequestMapping(value = "view")
	public String displayView(ExternalObjectDefinitionForm externalObjectDefinitionForm, Model model, RedirectAttributes redirectAttr) {
		ExternalObjectDefinition externalObjectDefinition = beanMapper.map(externalObjectDefinitionForm, ExternalObjectDefinition.class);
		String destination = "";
		try {
			super.checkChangeProject(false);
			CommonModel common = this.initCommon();

			Long accountId = SessionUtils.getAccountId();
			externalObjectDefinition.setCreatedBy(accountId);
			externalObjectDefinition.setUpdatedBy(accountId);
			externalObjectDefinition = externalObjectDefinitionService.findExternalObjectDefinition(externalObjectDefinition.getExternalObjectDefinitionId(),0, common);
			externalObjectDefinitionForm = beanMapper.map(externalObjectDefinition, ExternalObjectDefinitionForm.class);
			model.addAttribute(EXTERNAL_OBJECT_DEFINITION_FORM_NAME, externalObjectDefinitionForm);
			destination = VIEW_FORM_PATH;
		} catch (BusinessException ex) {
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			destination = SEARCH_REDIRECT_PATH;
			model.addAttribute("notExistFlg", 1);
		}
		return destination;
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String processDelete(ExternalObjectDefinitionForm externalObjectDefinitionForm, RedirectAttributes redirectAttr, Model model) {
		super.checkChangeProject(true);

		if (externalObjectDefinitionForm.getExternalObjectType() == null || "0".equals(externalObjectDefinitionForm.getExternalObjectType())){
			return SEARCH_REDIRECT_PATH;
		}
		
		ExternalObjectDefinition externalObjectDefinition = beanMapper.map(externalObjectDefinitionForm, ExternalObjectDefinition.class);
		try {
			CommonModel common = this.initCommon();

			Long accountId = SessionUtils.getAccountId();
			externalObjectDefinition.setCreatedBy(accountId);
			externalObjectDefinition.setUpdatedBy(accountId);
			externalObjectDefinition = externalObjectDefinitionService.deleteExternalObjectDefinition(externalObjectDefinition, common);
			if (externalObjectDefinition == null) {
				redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004, MessageUtils.getMessage(CommonMessageConst.TQP_EXTERNALOBJECTDEFINITION)));
				return REDIRECT_DELETECOMPLETE;
			}

		} catch (BusinessException ex) {
			model.addAttribute("message", ex.getResultMessages());
			model.addAttribute("notExistFlg", "0");

			String errMessageCode = StringUtils.defaultString(ex.getResultMessages().getList().get(0).getCode(), CommonMessageConst.ERR_SYS_0037);
			if (CommonMessageConst.ERR_SYS_0037.equals(errMessageCode) || CommonMessageConst.ERR_SYS_0111.equals(errMessageCode)) {
				model.addAttribute("notExistFlg", "1");
			} else {
			}
		}

		return VIEW_FORM_PATH;
	}

	@RequestMapping(value = "getDetailsExternalObjectDefinition", method = RequestMethod.GET)
	@ResponseBody
	public List<ExternalObjectAttribute> getDetailsExternalObjectDefinition(@RequestParam("externalObjectDefinitionId") Long externalObjectDefinitionId, Integer level) {
		CommonModel common = this.initCommon();

		ExternalObjectDefinition externalObjectDefinition =  externalObjectDefinitionService.findExternalObjectDefinition(externalObjectDefinitionId,level, common);
		if(externalObjectDefinition != null){
			return externalObjectDefinition.getExternalObjectAttributes();
		}else{
			return new ArrayList<ExternalObjectAttribute>();
		}
	}
	
	@RequestMapping(value = "checkExternalObjectDefinitionById", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkExternalObjectDefinitionById(@RequestParam("externalObjectDefinitionId") Long externalObjectDefinitionId) {
		return externalObjectDefinitionService.checkExternalObjectDefinitionById(externalObjectDefinitionId);
	}
	
	@RequestMapping(value = "getExternalObjectDefinitionById", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ExternalObjectDefinition getExternalObjectDefinitionById(@RequestParam("externalObjectDefinitionId") Long externalObjectDefinitionId){
		CommonModel common = this.initCommon();
		ExternalObjectDefinition externalObjectDefinition = externalObjectDefinitionService.findExternalObjectDefinition(externalObjectDefinitionId,0, common);
		return externalObjectDefinition;
	}

}
