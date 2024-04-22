package org.terasoluna.qp.app.subjectarea;

import javax.inject.Inject;

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
import org.terasoluna.qp.app.common.BaseController;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.SubjectAreaMessageConst;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.SubjectArea;
import org.terasoluna.qp.domain.repository.subjectarea.SubjectAreaSearchCriteria;
import org.terasoluna.qp.domain.service.subjectarea.SubjectAreaConstant;
import org.terasoluna.qp.domain.service.subjectarea.SubjectAreaService;

@Controller
@RequestMapping(value = "subjectarea")
@TransactionTokenCheck(value = "subjectarea")
@SessionAttributes(types = { SubjectAreaSearchForm.class })
public class SubjectAreaController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SubjectAreaController.class);
	private static final String ACTION_SEARCH = "/subjectarea/search";
	private static final String SEARCH_LINK = "subjectarea/searchForm";
	private static final String REDIRECT_SEARCH = "redirect:/subjectarea/search";
	private static final String REGISTER_LINK = "subjectarea/registerForm";
	private static final String VIEW_LINK = "subjectarea/viewForm";
	//private static final String REDIRECT_VIEW = "redirect:/subjectarea/view";
	private static final String MODIFY_LINK = "subjectarea/modifyForm";
	// private static final String REDIRECT_DELETECOMPLETE = "redirect:/complete";
	//private static final String MODE_SEARCH = "0";
	private static final String MODE_VIEW = "1";

	@Inject
	SubjectAreaService subjectAreaService;

	@Inject
	Mapper beanMapper;

	@Inject
	SubjectAreaValidator subjectAreaValidator;

	@InitBinder
	public void initService() {
		moduleCode = CommonMessageConst.TQP_SUBAREADESIGN;
	}

	/**
	 * Identifies methods which initialize the WebDataBinder which will be used for populating command and form object
	 * 
	 * @param webDataBinder
	 *            WebDataBinder
	 */
	@InitBinder("subjectAreaForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(subjectAreaValidator);
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	/**
	 * Identifies methods which initialize the subject area form object
	 * 
	 * @return accountForm
	 */
	@ModelAttribute
	public SubjectAreaForm setUpSubjectAreaForm() {

		SubjectAreaForm subjectAreaForm = new SubjectAreaForm();
		logger.debug("Init form {0}", subjectAreaForm);

		return subjectAreaForm;
	}

	/**
	 * Identifies methods which initialize the search form object
	 * 
	 * @return subjectAreaSearchform
	 */
	@ModelAttribute
	public SubjectAreaSearchForm setUpSubjectAreaSearchForm() {

		SubjectAreaSearchForm subjectAreaSearchform = new SubjectAreaSearchForm();
		logger.debug("Init form {0}", subjectAreaSearchform);

		return subjectAreaSearchform;
	}

	/**
	 * Initialize search subject area screen
	 * 
	 * @return SEARCH_LINK
	 */
	@RequestMapping(value = "search", method = { RequestMethod.GET })
	public String displaySearch(@RequestParam(value = "init", required = false) String init, @ModelAttribute SubjectAreaSearchForm subjectAreaSearchForm, 
			Model model, @PageableDefault Pageable pageable, SessionStatus sessionStatus) {

		if (init != null) {
			sessionStatus.setComplete();
			subjectAreaSearchForm = new SubjectAreaSearchForm();
			model.addAttribute("subjectAreaSearchForm", subjectAreaSearchForm);
			super.setOldProjectId(SessionUtils.getCurrentProject().getProjectId());
		}

		checkChangeProject(false);

		subjectAreaSearchForm.setProjectId(SessionUtils.getCurrentProjectId());
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(ACTION_SEARCH), pageable.getSort());
		SubjectAreaSearchCriteria subjectAreaSearchCriteria = beanMapper.map(subjectAreaSearchForm, SubjectAreaSearchCriteria.class);
		Page<SubjectArea> pageArea = subjectAreaService.findPageByCriteria(subjectAreaSearchCriteria, pageable);
		model.addAttribute("page", pageArea);

		return SEARCH_LINK;
	}

	/**
	 * Search subject area process
	 * 
	 * @return SEARCH_LINK
	 */
	@RequestMapping(value = "search", method = { RequestMethod.POST })
	public String processSearch(@ModelAttribute SubjectAreaSearchForm subjectAreaSearchForm, Model model, @PageableDefault Pageable pageable, SessionStatus status) {
		checkChangeProject(false);
		subjectAreaSearchForm.setProjectId(SessionUtils.getCurrentProjectId());
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(ACTION_SEARCH), pageable.getSort());
		SubjectAreaSearchCriteria subjectAreaSearchCriteria = beanMapper.map(subjectAreaSearchForm, SubjectAreaSearchCriteria.class);
		Page<SubjectArea> pageArea = subjectAreaService.findPageByCriteria(subjectAreaSearchCriteria, pageable);
		model.addAttribute("page", pageArea);

		return SEARCH_LINK;
	}

	/**
	 * Initialize register subject area screen
	 * 
	 * @return REGISTER_LINK
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String displayRegister(@ModelAttribute SubjectAreaForm subjectAreaForm, Model model, RedirectAttributes redirectAttr) {
		try {
			checkChangeProject(true);
			subjectAreaForm.setProjectId(SessionUtils.getCurrentProjectId());
			/* subjectAreaForm.setProjectName(SessionUtils.getCurrentProject().getProjectName()); */
			SubjectArea subjectArea = beanMapper.map(subjectAreaForm, SubjectArea.class);
			// Set data for register by project id
			subjectAreaService.assignSetting(subjectArea, SubjectAreaConstant.PROCESS_REG);
			subjectAreaForm = beanMapper.map(subjectArea, SubjectAreaForm.class);
			subjectAreaForm.setTableLst(null);
			model.addAttribute("subjectAreaForm", subjectAreaForm);

			return REGISTER_LINK;
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return REDIRECT_SEARCH;
		}
	}

	/**
	 * Register subject area process
	 * 
	 * @return In the case success : REDIRECT_SEARCH other REGISTER_LINK
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.IN)
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String processRegister(@Validated @ModelAttribute SubjectAreaForm subjectAreaForm, BindingResult result, Model model, RedirectAttributes redirectAttr) {
		
		SubjectArea subjectArea = beanMapper.map(subjectAreaForm, SubjectArea.class);
		try {
			checkChangeProject(true);

			/* subjectAreaForm.setProjectId(SessionUtils.getCurrentProjectId()); */

			if (result.hasErrors()) {
				// Keep value and redirect when modify error
				SubjectArea subjectAreaErr = beanMapper.map(subjectAreaForm, SubjectArea.class);
				subjectAreaService.assignSetting(subjectAreaErr, SubjectAreaConstant.PROCESS_REG_ERR);
				subjectAreaForm = beanMapper.map(subjectAreaErr, SubjectAreaForm.class);
				model.addAttribute("subjectAreaForm", subjectAreaForm);
				ValidationUtils.setBindingResult(result, model);

				return REGISTER_LINK;
			}

			/* subjectAreaForm.setProjectName(SessionUtils.getCurrentProject().getProjectName()); */

			CommonModel common = this.initCommon();

			subjectArea.setCreatedBy(common.getCreatedBy());
			subjectArea.setUpdatedBy(common.getCreatedBy());
			subjectAreaService.register(subjectArea, common);
		} catch (BusinessException ex) {

			subjectAreaService.assignSetting(subjectArea, SubjectAreaConstant.PROCESS_REG_ERR);
			subjectAreaForm = beanMapper.map(subjectArea, SubjectAreaForm.class);
			model.addAttribute("subjectAreaForm", subjectAreaForm);
			model.addAttribute("message", ex.getResultMessages());

			return REGISTER_LINK;
		}

		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage(SubjectAreaMessageConst.SC_SUBAREADESIGN_0017)));

		return REDIRECT_SEARCH;
	}

	/**
	 * Initialize view subject area screen
	 * 
	 * @return VIEW_LINK
	 */
	@RequestMapping(value = "view")
	public String displayView(SubjectAreaForm subjectAreaForm, Model model, RedirectAttributes redirectAttr) {
		SubjectArea subjectArea = new SubjectArea();

		try {
			checkChangeProject(false);
			CommonModel common = this.initCommon();

			subjectArea = subjectAreaService.findOneById(subjectAreaForm.getAreaId(), false, common);
		} catch (BusinessException ex) {
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			return REDIRECT_SEARCH;
		}

		// Assign setting value for model
		subjectAreaService.assignSetting(subjectArea, SubjectAreaConstant.PROCESS_VIEW);
		model.addAttribute("area", subjectArea);

		return VIEW_LINK;
	}

	/**
	 * Initialize modify subject area screen
	 * 
	 * @return MODIFY_LINK
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public String displayModify(SubjectAreaForm subjectAreaForm, Model model, RedirectAttributes redirectAttr, BindingResult result) {

		try {
			checkChangeProject(true);
			CommonModel common = this.initCommon();

			subjectAreaForm.setProjectId(common.getWorkingProjectId());
			/* subjectAreaForm.setProjectName(SessionUtils.getCurrentProject().getProjectName()); */
			SubjectArea subjectArea = subjectAreaService.findOneById(subjectAreaForm.getAreaId(), true, common);
			subjectAreaService.assignSetting(subjectArea, SubjectAreaConstant.PROCESS_MODIFY);
			subjectAreaForm = beanMapper.map(subjectArea, SubjectAreaForm.class);

		} catch (BusinessException be) {
			/*
			 * if (MODE_SEARCH.equals(subjectAreaForm.getMode())) { redirectAttr.addFlashAttribute("message", ex.getResultMessages()); return REDIRECT_SEARCH; } else if (MODE_VIEW.equals(subjectAreaForm.getMode())) { return REDIRECT_VIEW; }
			 */

			if (MODE_VIEW.equals(subjectAreaForm.getMode())) {
				redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_MESSAGEDESIGN)); // message title
				redirectAttr.addFlashAttribute("message", be.getResultMessages());// message content
				return DbDomainConst.REDIRECT_DELETION_SUCCESS;
			}

			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return REDIRECT_SEARCH;
		}

		model.addAttribute("subjectAreaForm", subjectAreaForm);
		return MODIFY_LINK;

	}

	/**
	 * Modify subject area process
	 * 
	 * @return In the case success : REDIRECT_SEARCH other MODIFY_LINK
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String processModify(@Validated @ModelAttribute SubjectAreaForm subjectAreaForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {

		CommonModel common = this.initCommon();
		subjectAreaForm.setProjectId(common.getWorkingProjectId());

		// Check has error
		if (result.hasErrors()) {
			// Keep value when modify error
			SubjectArea subjectAreaErr = beanMapper.map(subjectAreaForm, SubjectArea.class);
			subjectAreaService.assignSetting(subjectAreaErr, SubjectAreaConstant.PROCESS_MODIFY_ERR);
			subjectAreaForm = beanMapper.map(subjectAreaErr, SubjectAreaForm.class);
			model.addAttribute("subjectAreaForm", subjectAreaForm);
			ValidationUtils.setBindingResult(result, model);

			return MODIFY_LINK;
		}

		SubjectArea subjectArea = beanMapper.map(subjectAreaForm, SubjectArea.class);

		try {
			checkChangeProject(true);
			subjectArea.setUpdatedBy(common.getCreatedBy());
			subjectArea.setUpdatedDate(subjectArea.getUpdatedDate());
			subjectAreaService.modify(subjectArea, common);
		} catch (BusinessException ex) {
			subjectAreaService.assignSetting(subjectArea, SubjectAreaConstant.PROCESS_MODIFY_ERR);
			subjectAreaForm = beanMapper.map(subjectArea, SubjectAreaForm.class);
			model.addAttribute("subjectAreaForm", subjectAreaForm);
			/*if (CommonMessageConst.ERR_SYS_0037.equals(ex.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("notExistFlg", 1);
			}*/
			model.addAttribute("message", ex.getResultMessages());
			return MODIFY_LINK;
		}

		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(SubjectAreaMessageConst.SC_SUBAREADESIGN_0017)));

		return REDIRECT_SEARCH;
	}

	/**
	 * Delete subject area process
	 * 
	 * @return In the case success : REDIRECT_DELETECOMPLETE other VIEW_LINK
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String processDelete(SubjectAreaForm subjectAreaForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {

		CommonModel common = this.initCommon();
		subjectAreaForm.setProjectId(common.getWorkingProjectId());
		SubjectArea subjectArea = beanMapper.map(subjectAreaForm, SubjectArea.class);

		try {
			checkChangeProject(true);
			subjectAreaService.delete(subjectArea, common);
		} catch (BusinessException ex) {
			redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_SUBAREADESIGN)); // message title
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());// message content
			return DbDomainConst.REDIRECT_DELETION_SUCCESS;
		}
		redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_SUBAREADESIGN)); // message title
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004, MessageUtils.getMessage(SubjectAreaMessageConst.SC_SUBAREADESIGN_0017)));

		return DbDomainConst.REDIRECT_DELETION_SUCCESS;
	}
}
