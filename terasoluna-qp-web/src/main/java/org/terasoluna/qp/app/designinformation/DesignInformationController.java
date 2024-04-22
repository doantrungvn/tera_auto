package org.terasoluna.qp.app.designinformation;

import java.util.Collection;

import javax.inject.Inject;

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
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.DesignInformationMessageConst;
import org.terasoluna.qp.domain.model.Account;
import org.terasoluna.qp.domain.model.DesignInformation;
import org.terasoluna.qp.domain.model.Message;
import org.terasoluna.qp.domain.repository.designinformation.DesignInformationCriteria;
import org.terasoluna.qp.domain.service.account.AccountService;
import org.terasoluna.qp.domain.service.designinformation.DesignInformationService;
import org.terasoluna.qp.domain.service.message.MessageService;
import org.terasoluna.qp.domain.service.project.ProjectService;

@Controller
@RequestMapping(value = "designinformation")
@TransactionTokenCheck(value = "designinformation")
@SessionAttributes(types = { DesignInformationSearchForm.class })
public class DesignInformationController {

	private static final Logger logger = LoggerFactory.getLogger(DesignInformationController.class);
	private static final String DESIGN_INFORMATION_SEARCH_FORM_NAME = "designInformationSearchForm";
	private static final String DESIGN_INFORMATION_FORM_NAME = "designInformationForm";
	private static final String REGISTER_FORM_PATH = "designinformation/registerForm";
	private static final String SEARCH_FORM_PATH = "designinformation/searchForm";
	private static final String VIEW_FORM_PATH = "designinformation/viewForm";
	private static final String SEARCH_ACTION_PATH = "/designinformation/search";
	private static final String MODIFY_FORM_PATH = "designinformation/modifyForm";
	private static final String SEARCH_REDIRECT_PATH = "redirect:/designinformation/search";
	private static final String REDIRECT_DELETECOMPLETE = "redirect:/complete";
	private static final String MODE_SEARCH = "0";
	private static final String MODE_VIEW = "1";

	@Inject
	ProjectService projectService;

	@Inject
	MessageService messageService;

	@Inject
	AccountService accountService;

	@Inject
	DesignInformationService designInformationService;

	@Inject
	Mapper beanMapper;

	@Inject
	DesignInformationValidator designInformationValidator;

	@InitBinder(DESIGN_INFORMATION_FORM_NAME)
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(designInformationValidator);
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@ModelAttribute(DESIGN_INFORMATION_SEARCH_FORM_NAME)
	public DesignInformationSearchForm setUpDesignInformationSearchForm() {
		DesignInformationSearchForm designInformationSearchForm = new DesignInformationSearchForm();
		logger.debug("Init form {0}", designInformationSearchForm);
		return designInformationSearchForm;
	}

	@ModelAttribute("moduleResources")
	public Collection<Message> setUpModuleResource() {
		Collection<Message> moduleResources = messageService.findAllModuleResource();
		return moduleResources;
	}

	@ModelAttribute("accountForm")
	public Account setUpAccount() {
		Account account = accountService.findOneByAccountId(SessionUtils.getAccountId());
		return account;
	}

	/**
	 * pre-initialization of form backed bean
	 * 
	 * @return
	 */
	@ModelAttribute(DESIGN_INFORMATION_FORM_NAME)
	public DesignInformationForm setUpDesignInformationForm() {
		DesignInformationForm designInformationForm = new DesignInformationForm();
		logger.debug("Init form {0}", designInformationForm);
		return designInformationForm;
	}

	/**
	 * return to search screen
	 * 
	 * @param messageDesignSearchForm
	 * @param model
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String displaySearch(@RequestParam(value = "init", required = false) String init, @ModelAttribute(DESIGN_INFORMATION_SEARCH_FORM_NAME) DesignInformationSearchForm designInformationSearchForm, Model model, @PageableDefault Pageable pageable, SessionStatus sessionStatus) {

		if (init != null) {
			sessionStatus.setComplete();
			designInformationSearchForm = new DesignInformationSearchForm();
			model.addAttribute(DESIGN_INFORMATION_SEARCH_FORM_NAME, designInformationSearchForm);
		}
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
		DesignInformationCriteria designInformationCriteria = beanMapper.map(designInformationSearchForm, DesignInformationCriteria.class);
		Page<DesignInformation> page = designInformationService.searchDesignInformation(designInformationCriteria, pageable);
		model.addAttribute("page", page);

		return SEARCH_FORM_PATH;
	}

	/**
	 * search message design
	 * 
	 * @param messageDesignSearchForm
	 * @param model
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "search", method = RequestMethod.POST)
	public String processSearch(@ModelAttribute(DESIGN_INFORMATION_SEARCH_FORM_NAME) DesignInformationSearchForm designInformationSearchForm, Model model, @PageableDefault Pageable pageable) {

		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
		DesignInformationCriteria designInformationCriteria = beanMapper.map(designInformationSearchForm, DesignInformationCriteria.class);
		Page<DesignInformation> page = designInformationService.searchDesignInformation(designInformationCriteria, pageable);
		model.addAttribute("page", page);

		return SEARCH_FORM_PATH;
	}

	/**
	 * Initialize register business type screen
	 * 
	 * @return REGISTER_FORM_PATH
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String displayRegister(@ModelAttribute(DESIGN_INFORMATION_FORM_NAME) DesignInformationForm designInformationForm, Model model) {

		designInformationForm.setCreatedDate(FunctionCommon.getCurrentTime());
		return REGISTER_FORM_PATH;
	}

	/**
	 * Register message design process
	 * 
	 * @param MessageDesignForm
	 *            business type information
	 * @param result
	 *            BindingResult
	 * @param redirectAttr
	 *            RedirectAttributes
	 * @param model
	 *            Model
	 * @return in case of success SEARCH_REDIRECT_PATH will be returned. Otherwise it will be returned to REGISTER_FORM_PATH
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.IN)
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String processRegister(@Validated DesignInformationForm designInformationForm, BindingResult result, Model model, RedirectAttributes redirectAttr) {

		if (result.hasErrors()) {
			ValidationUtils.setBindingResult(result, model);
			return REGISTER_FORM_PATH;
		}
		try {
			DesignInformation designInformation = beanMapper.map(designInformationForm, DesignInformation.class);
			designInformation.setCreatedBy(SessionUtils.getAccountId());
			designInformationService.register(designInformation);
		} catch (BusinessException be) {
			model.addAttribute("message", be.getResultMessages());
			return REGISTER_FORM_PATH;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage(DesignInformationMessageConst.SC_DESIGNINFORMATION_0004)));
		return SEARCH_REDIRECT_PATH;
	}

	/**
	 * Initialize modify project screen
	 * 
	 * @param projectForm
	 *            ProjectForm
	 * @param redirectAttr
	 *            RedirectAttributes
	 * @param model
	 *            Model
	 * @return MODIFY_FORM_PATH
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public String displayModify(DesignInformationForm designInformationForm, Model model, RedirectAttributes redirectAttr) {
		String destination = StringUtils.EMPTY;
		DesignInformation designInformation = beanMapper.map(designInformationForm, DesignInformation.class);
		try {

			designInformation = designInformationService.findDesignInformationById(designInformation);
			designInformationForm = beanMapper.map(designInformation, DesignInformationForm.class);
			model.addAttribute("designInformationForm", designInformationForm);
			destination = MODIFY_FORM_PATH;
		} catch (BusinessException be) {
			model.addAttribute("notExistFlg", 1);
			if (MODE_SEARCH.equals(designInformationForm.getMode())) {
				redirectAttr.addFlashAttribute("message", be.getResultMessages());
				destination = SEARCH_REDIRECT_PATH;
			} else if (MODE_VIEW.equals(designInformationForm.getMode())) {
				model.addAttribute("message", be.getResultMessages());
				destination = VIEW_FORM_PATH;
			}
		}
		return destination;
	}

	/**
	 * Modify message design process
	 * 
	 * @param messageForm
	 *            MessageForm
	 * @param result
	 *            BindingResult
	 * @param model
	 *            Model
	 * @param redirectAttr
	 *            RedirectAttributes
	 * @param pageable
	 *            Pageable
	 * @return "redirect:search?init"
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String processModify(@Validated @ModelAttribute DesignInformationForm designInformationForm, BindingResult result, Model model, RedirectAttributes redirectAttr, @PageableDefault Pageable pageable) {
		if (result.hasErrors()) {
			ValidationUtils.setBindingResult(result, model);
			return MODIFY_FORM_PATH;
		}
		DesignInformation designInformation = beanMapper.map(designInformationForm, DesignInformation.class);
		try {
			designInformation.setUpdatedBy(SessionUtils.getAccountId());
			designInformationService.modify(designInformation);
		} catch (BusinessException be) {
			if (CommonMessageConst.ERR_SYS_0037.equals(be.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("notExistFlg", 1);
			}
			model.addAttribute("message", be.getResultMessages());
			return MODIFY_FORM_PATH;
		}

		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(DesignInformationMessageConst.SC_DESIGNINFORMATION_0004)));
		return SEARCH_REDIRECT_PATH;
	}

	@RequestMapping(value = "view")
	public String displayView(DesignInformationForm designInformationForm, Model model, RedirectAttributes redirectAttr) {
		DesignInformation designInformation = beanMapper.map(designInformationForm, DesignInformation.class);
		try {

			designInformation = designInformationService.findDesignInformationById(designInformation);
			designInformationForm = beanMapper.map(designInformation, DesignInformationForm.class);
			model.addAttribute("designInformationForm", designInformationForm);
			model.addAttribute("notExistFlg", 0);
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			model.addAttribute("notExistFlg", 1);
			return SEARCH_REDIRECT_PATH;
		}
		return VIEW_FORM_PATH;
	}

	/**
	 * Delete design information
	 * 
	 * @param designInformationForm
	 * @param redirectAttr
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	public String delete(DesignInformationForm designInformationForm, RedirectAttributes redirectAttr, Model model) {
		DesignInformation designInformation = beanMapper.map(designInformationForm, DesignInformation.class);
		try {
			designInformation = designInformationService.deleteDesignInformation(designInformation);
			if (designInformation == null) {
				redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004, MessageUtils.getMessage(DesignInformationMessageConst.SC_DESIGNINFORMATION_0004)));
				return REDIRECT_DELETECOMPLETE;
			}
			if (designInformation.getResultMessages() != null) {
				throw new BusinessException(designInformation.getResultMessages());
			}
		} catch (BusinessException be) {
			model.addAttribute("message", be.getResultMessages());
			model.addAttribute("notExistFlg", "0");

			// if not exist or fixed design status
			String errMessageCode = StringUtils.defaultString(be.getResultMessages().getList().get(0).getCode(), CommonMessageConst.ERR_SYS_0037);
			if (CommonMessageConst.ERR_SYS_0037.equals(errMessageCode) || CommonMessageConst.ERR_SYS_0111.equals(errMessageCode)) {
				model.addAttribute("notExistFlg", "1");
			}
		}
		return VIEW_FORM_PATH;
	}

}
