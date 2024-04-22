package org.terasoluna.qp.app.commonobjectdefinition;

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
import org.terasoluna.qp.domain.model.CommonObjectAttribute;
import org.terasoluna.qp.domain.model.CommonObjectDefinition;
import org.terasoluna.qp.domain.repository.commonobjectdefinition.CommonObjectDefinitionSearchCriteria;
import org.terasoluna.qp.domain.service.commonobjectdefinition.CommonObjectDefinitionService;

@Controller
@RequestMapping(value = "commonobjectdefinition")
@TransactionTokenCheck(value = "commonobjectdefinition")
@SessionAttributes(types = { CommonObjectDefinitionSearchForm.class })
public class CommonObjectDefinitionController extends BaseController {

	private static final String SEARCH_FORM_PATH = "commonobjectdefinition/searchForm";

	private static final String SEARCH_REDIRECT_PATH = "redirect:/commonobjectdefinition/search";

	private static final String COMMON_OBJECT_DEFINITION_SEARCH_FORM_NAME = "commonObjectDefinitionSearchForm";

	private static final String REGISTER_FORM_PATH = "commonobjectdefinition/registerForm";

	private static final String MODIFY_FORM_PATH = "commonobjectdefinition/modifyForm";

	private static final String VIEW_FORM_PATH = "commonobjectdefinition/viewForm";

	private static final String COMMON_OBJECT_DEFINITION_FORM_NAME = "commonObjectDefinitionForm";

	private static final String SEARCH_ACTION_PATH = "/commonobjectdefinition/search";

	private static final String LINK_REGISTER = "commonobjectdefinition/registerForm";

	private static final String REDIRECT_DELETECOMPLETE = "redirect:/complete";

	private static final String MODE_SEARCH = "0";

	private static final String MODE_VIEW = "1";

	@Inject
	Mapper beanMapper;

	@Inject
	CommonObjectDefinitionService commonObjectDefinitionService;

	@Inject
	CommonObjectDefinitionValidator commonObjectDefinitionValidator;

	/**
	 * Inits the binder common object definition form.
	 *
	 * @param webDataBinder
	 *            the web data binder
	 */
	@InitBinder(COMMON_OBJECT_DEFINITION_FORM_NAME)
	public void initBinderDesignForm(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(commonObjectDefinitionValidator);
		// register characters trimmer for String-type field in form bean
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}
	
	@InitBinder
	public void init() {
		moduleCode = CommonMessageConst.TQP_COMMONOBJECTDEFINITION;
	}

	@ModelAttribute
	public CommonObjectDefinitionForm setupFormCommonObjectDefintion() {
		CommonObjectDefinitionForm form = new CommonObjectDefinitionForm();
		return form;
	}

	@ModelAttribute
	public CommonObjectDefinitionSearchForm setupFormCriteria() {
		CommonObjectDefinitionSearchForm form = new CommonObjectDefinitionSearchForm();
		return form;
	}

	/**
	 * Initialize search common object definition
	 *
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String displaySearch(@RequestParam(value = "init", required = false) String init, @ModelAttribute CommonObjectDefinitionSearchForm commonObjectDefinitionSearchForm, Model model, @PageableDefault Pageable pageable, SessionStatus sessionStatus) {
		if (init != null) {
			sessionStatus.setComplete();
			commonObjectDefinitionSearchForm = new CommonObjectDefinitionSearchForm();
			model.addAttribute(COMMON_OBJECT_DEFINITION_SEARCH_FORM_NAME, commonObjectDefinitionSearchForm);
			super.setOldProjectId(SessionUtils.getCurrentProject().getProjectId());
		}
		super.checkChangeProject(false);
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
		CommonObjectDefinitionSearchCriteria commonObjectDefinitionSearchCriteria = beanMapper.map(commonObjectDefinitionSearchForm, CommonObjectDefinitionSearchCriteria.class);

		CommonModel common = this.initCommon();
		Page<CommonObjectDefinition> page = commonObjectDefinitionService.findPageByCriteria(commonObjectDefinitionSearchCriteria, pageable, common);
		model.addAttribute("page", page);
		return SEARCH_FORM_PATH;
	}

	/**
	 * Search common object definition process
	 *
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "search", method = { RequestMethod.POST })
	public String processSearch(CommonObjectDefinitionSearchForm commonObjectDefinitionSearchForm, Model model, SessionStatus sessionStatus, @PageableDefault Pageable pageable) {
		super.checkChangeProject(false);
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction("/commonobjectdefinition/search"), pageable.getSort());
		CommonObjectDefinitionSearchCriteria commonObjectDefinitionSearchCriteria = beanMapper.map(commonObjectDefinitionSearchForm, CommonObjectDefinitionSearchCriteria.class);
		commonObjectDefinitionSearchCriteria.setProjectId(SessionUtils.getCurrentProjectId());
		CommonModel common = this.initCommon();
		Page<CommonObjectDefinition> page = commonObjectDefinitionService.findPageByCriteria(commonObjectDefinitionSearchCriteria, pageable, common);
		beanMapper.map(commonObjectDefinitionSearchCriteria, commonObjectDefinitionSearchForm);
		model.addAttribute("page", page);

		return SEARCH_FORM_PATH;
	}

	/**
	 * Initialize register common object definition
	 *
	 * @return COMMON_REGISTER_FORM_PATH
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "register", method = { RequestMethod.GET })
	public String displayRegisterCommon(@ModelAttribute(COMMON_OBJECT_DEFINITION_FORM_NAME) CommonObjectDefinitionForm commonObjectDefinitionForm, Model model) {
		super.checkChangeProject(true);
		return REGISTER_FORM_PATH;
	}

	/**
	 * Process register common object definition
	 *
	 * @return SEARCH_REDIRECT_PATH
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.IN)
	@RequestMapping(value = "register", method = { RequestMethod.POST })
	public String processRegister(@Validated CommonObjectDefinitionForm commonObjectDefinitionForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		super.checkChangeProject(true);

		CommonObjectDefinition commonObjectDefinition = beanMapper.map(commonObjectDefinitionForm, CommonObjectDefinition.class);
        if (result.hasErrors()) {
            model.addAttribute("commonObjectDefinitionForm", commonObjectDefinitionForm);
            return LINK_REGISTER;
        }
		try {
			CommonModel common = this.initCommon();
			commonObjectDefinitionService.registerCommonObjectDefinition(commonObjectDefinition, common);
		} catch (BusinessException ex) {
			model.addAttribute("message", ex.getResultMessages());
			return LINK_REGISTER;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage(CommonMessageConst.TQP_COMMONOBJECTDEFINITION)));
		return SEARCH_REDIRECT_PATH;
	}

	/**
	 * Initialize modify common object definition
	 *
	 * @return COMMON_MODIFY_FORM_PATH
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public String displayModify(CommonObjectDefinitionForm commonObjectDefinitionForm, Model model, RedirectAttributes redirectAttr) {
		String destination = StringUtils.EMPTY;

		try {
			super.checkChangeProject(true);
			CommonModel common = this.initCommon();

			Long accountId = SessionUtils.getAccountId();
			commonObjectDefinitionForm.setCreatedBy(accountId);
			commonObjectDefinitionForm.setUpdatedBy(accountId);
			CommonObjectDefinition commonObjectDefinition = commonObjectDefinitionService.findCommonObjectDefinition(commonObjectDefinitionForm.getCommonObjectDefinitionId(), common);
			commonObjectDefinition.setProjectId(SessionUtils.getCurrentProjectId());
			commonObjectDefinitionForm = beanMapper.map(commonObjectDefinition, CommonObjectDefinitionForm.class);
			model.addAttribute(COMMON_OBJECT_DEFINITION_FORM_NAME, commonObjectDefinitionForm);
			destination = MODIFY_FORM_PATH;
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			if (MODE_SEARCH.equals(commonObjectDefinitionForm.getMode())) {
				destination = SEARCH_REDIRECT_PATH;
			} else if (MODE_VIEW.equals(commonObjectDefinitionForm.getMode())) {
				commonObjectDefinitionForm.setHasErrors(true);
				model.addAttribute("notExistFlg", 1);
				model.addAttribute("message", be.getResultMessages());
				destination = VIEW_FORM_PATH;
			}
		}
		return destination;
	}

	/**
	 * Process modify common object definition
	 *
	 * @return SEARCH_REDIRECT_PATH
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String processModify(@Validated CommonObjectDefinitionForm commonObjectDefinitionForm, BindingResult bindingResult, Model model, RedirectAttributes attributes) {
		super.checkChangeProject(true);
		CommonObjectDefinition commonObjectDefinition = beanMapper.map(commonObjectDefinitionForm, CommonObjectDefinition.class);
		if (bindingResult.hasErrors()) {
            model.addAttribute("commonObjectDefinitionForm", commonObjectDefinitionForm);
            return MODIFY_FORM_PATH;
		}
		try {
			CommonModel common = this.initCommon();
			commonObjectDefinitionService.modifyCommonObjectDefinition(commonObjectDefinition, common);
			attributes.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(CommonMessageConst.TQP_COMMONOBJECTDEFINITION)));
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
	public String displayView(CommonObjectDefinitionForm commonObjectDefinitionForm, Model model, RedirectAttributes redirectAttr) {
		CommonObjectDefinition commonObjectDefinition = beanMapper.map(commonObjectDefinitionForm, CommonObjectDefinition.class);
		String destination = "";
		try {
			super.checkChangeProject(false);
			CommonModel common = this.initCommon();

			Long accountId = SessionUtils.getAccountId();
			commonObjectDefinition.setCreatedBy(accountId);
			commonObjectDefinition.setUpdatedBy(accountId);
			commonObjectDefinition = commonObjectDefinitionService.findCommonObjectDefinition(commonObjectDefinition.getCommonObjectDefinitionId(), common);
			commonObjectDefinitionForm = beanMapper.map(commonObjectDefinition, CommonObjectDefinitionForm.class);
			model.addAttribute(COMMON_OBJECT_DEFINITION_FORM_NAME, commonObjectDefinitionForm);
			destination = VIEW_FORM_PATH;
		} catch (BusinessException ex) {
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			destination = SEARCH_REDIRECT_PATH;
			model.addAttribute("notExistFlg", 1);
		}
		return destination;
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String processDelete(CommonObjectDefinitionForm commonObjectDefinitionForm, RedirectAttributes redirectAttr, Model model) {
		super.checkChangeProject(true);

		CommonObjectDefinition commonObjectDefinition = beanMapper.map(commonObjectDefinitionForm, CommonObjectDefinition.class);
		try {
			CommonModel common = this.initCommon();

			Long accountId = SessionUtils.getAccountId();
			commonObjectDefinition.setCreatedBy(accountId);
			commonObjectDefinition.setUpdatedBy(accountId);
			commonObjectDefinition = commonObjectDefinitionService.deleteCommonObjectDefinition(commonObjectDefinition, common);
			if (commonObjectDefinition == null) {
				redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004, MessageUtils.getMessage(CommonMessageConst.TQP_COMMONOBJECTDEFINITION)));
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

	@RequestMapping(value = "getDetailsCommonObjectDefinition", method = RequestMethod.GET)
	@ResponseBody
	public List<CommonObjectAttribute> getDetailsCommonObjectDefinition(@RequestParam("commonObjectDefinitionId") Long commonObjectDefinitionId,Integer level) {
		CommonModel common = this.initCommon();
		return commonObjectDefinitionService.findCommonObjectAttributeByCommonObject(commonObjectDefinitionId,level, common);
	}
	
	@RequestMapping(value = "checkCommonObjectDefinitionExistById", method = RequestMethod.GET)
	@ResponseBody
	public Boolean checkCommonObjectDefinitionExistById(@RequestParam("commonObjectDefinitionId") Long commonObjectDefinitionId) {
		return commonObjectDefinitionService.checkCommonObjectDefinitionExistById(commonObjectDefinitionId);
	}
	
	@RequestMapping(value = "getCommonObjectDefinitionById", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public CommonObjectDefinition checkCommonObjectDefinitionById(@RequestParam("commonObjectDefinitionId") Long commonObjectDefinitionId) {
		CommonModel common = this.initCommon();
		return commonObjectDefinitionService.findCommonObjectDefinition(commonObjectDefinitionId, common);
	}
}
