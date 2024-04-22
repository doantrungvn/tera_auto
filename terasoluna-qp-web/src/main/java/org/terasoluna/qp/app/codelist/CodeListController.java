package org.terasoluna.qp.app.codelist;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
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
import org.terasoluna.qp.app.message.CodelistMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.CodeList;
import org.terasoluna.qp.domain.repository.codelist.CodeListSearchCriteria;
import org.terasoluna.qp.domain.service.codelist.CodeListService;

@Controller
@RequestMapping(value = "codelist")
@TransactionTokenCheck(value = "codelist")
@SessionAttributes(types = { CodeListSearchForm.class })
public class CodeListController extends BaseController{

	private static final String ACTION_SEARCH = "/codelist/search";
	private static final String LINK_SEARCH = "codelist/searchForm";
	private static final String CODELIST_FORM_NAME = "codeListForm";
	private static final String LINK_REGISTER = "codelist/registerForm";
	private static final String LINK_VIEW = "codelist/viewForm";
	private static final String LINK_MODIFY = "codelist/modifyForm";
	/*private static final String LINK_REFRESH = "refreshForm";*/
	private static final String SEARCH_REDIRECT_PATH = "redirect:/codelist/search";
	/*private static final String REDIRECT_DELETECOMPLETE = "redirect:/complete";*/
	/*private static final String VIEW_REDIRECT_PATH = "redirect:/codelist/view";*/
	
	//private static final String MODE_SEARCH = "0";
	private static final String MODE_VIEW = "1";

	@Inject
	CodeListService codelistService;

	@Inject
	Mapper beanMapper;
	
	@Inject
	CodelistValidator codelistValidator;
	
	@ModelAttribute
	public void init() {
		moduleCode = CommonMessageConst.TQP_CODELIST;
	}
	
	@InitBinder("codeListForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(codelistValidator);
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@ModelAttribute
	public CodeListSearchForm setUpFormCriteria() {
		CodeListSearchForm form = new CodeListSearchForm();
		return form;
	}

	@ModelAttribute
	public CodeListForm setUpFormCodeList() {
		CodeListForm form = new CodeListForm();
		return form;
	}

	/**
	 * Initialize search codelist screen
	 * 
	 * @param codelistSearchForm
	 * @param model
	 * @param pageable
	 * @return SEARCH_FORM_PATH
	 */

	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String displaySearch(@RequestParam(value = "init", required = false) String init, 
			@ModelAttribute CodeListSearchForm codeListSearchForm, Model model,@PageableDefault Pageable pageable,SessionStatus sessionStatus) {
	
		if(init != null){
			sessionStatus.setComplete();
			codeListSearchForm = setUpFormCriteria();
			model.addAttribute("codeListSearchForm", codeListSearchForm);
			super.setOldProjectId(SessionUtils.getCurrentProject().getProjectId());
		}
		
		//checkChangeProject(false); 
		
		pageable = new PageRequest(pageable.getPageNumber(),PageSizeUtils.getPageSizeOfAction(ACTION_SEARCH),pageable.getSort());
		codeListSearchForm.setProjectId(SessionUtils.getCurrentProjectId());
		CodeListSearchCriteria criteria = beanMapper.map(codeListSearchForm,CodeListSearchCriteria.class);
		Page<CodeList> page = codelistService.getBySearchCriteria(criteria,pageable);
		model.addAttribute("page", page);
		return LINK_SEARCH;
	}

	/**
	 * Search codeList process
	 * 
	 * @param codelistForm
	 * @param model
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "search", method = RequestMethod.POST)
	public String processSearch(@ModelAttribute CodeListSearchForm codeListSearchForm, Model model,@PageableDefault Pageable pageable, SessionStatus status) {
		//checkChangeProject(false); 
		codeListSearchForm.setProjectId(SessionUtils.getCurrentProjectId());
		pageable = new PageRequest(pageable.getPageNumber(),PageSizeUtils.getPageSizeOfAction(ACTION_SEARCH),pageable.getSort());
		CodeListSearchCriteria criteria = beanMapper.map(codeListSearchForm,CodeListSearchCriteria.class);
		Page<CodeList> pageCodelist = codelistService.getBySearchCriteria(criteria, pageable);
		model.addAttribute("page", pageCodelist);
		return LINK_SEARCH;
	}

	/**
	 * Initialize register codelist screen
	 * 
	 * @param codelistForm
	 * @param model
	 * @return
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String displayRegister(@ModelAttribute(CODELIST_FORM_NAME) CodeListForm codelistForm, Model model, RedirectAttributes redirectAttr) {
		try {
			checkChangeProject(true);
			codelistForm.setProjectId(SessionUtils.getCurrentProjectId());
			return LINK_REGISTER;
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return SEARCH_REDIRECT_PATH;
		}
		
	}

	/**
	 * Register Codelist process
	 * 
	 * @param codelistForm
	 * @param model
	 * @return SEARCH_REDIRECT_PATH in case of success. Otherwise return to
	 *         Initialize register codeList screen
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.IN)
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String processRegister(@Validated CodeListForm codelistForm,BindingResult bindingResult, Model model,RedirectAttributes redirectAttr) {
		 
		//codelistForm.setProjectId(SessionUtils.getCurrentProjectId());

		CodeList codeList = beanMapper.map(codelistForm, CodeList.class);
		if(codeList.getIsOptionValude() == null){
			codeList.setIsOptionValude(new Integer(0));
		}
		if (bindingResult.hasErrors()) {
			ValidationUtils.setBindingResult(bindingResult, model);
			return LINK_REGISTER;
		}
		try {
			checkChangeProject(true);
			codelistService.registerCodelist(codeList, SessionUtils.getAccountId(), SessionUtils.getCurrentProjectId());
		} catch (BusinessException ex) {
			model.addAttribute("message", ex.getResultMessages());
			return LINK_REGISTER;
		}
		redirectAttr.addFlashAttribute("message",ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage("tqp.codelist")));
		return SEARCH_REDIRECT_PATH;
	}

	@RequestMapping(value = "view")
	public String displayView(@ModelAttribute(CODELIST_FORM_NAME) CodeListForm codeListForm, Model model, RedirectAttributes redirectAttr) {
		CodeList codeList = null;

		Integer checkFormOpenOwner = codeListForm.getOpenOwner();

		try {
			checkChangeProject(false);
			codeList = codelistService.getCodeListInformation(codeListForm.getCodeListId(), SessionUtils.getAccountId(), SessionUtils.getCurrentProjectId());

			if( CollectionUtils.isNotEmpty(codeList.getListScreenItem()) || CollectionUtils.isNotEmpty(codeList.getListTableDesignItems())
					|| CollectionUtils.isNotEmpty(codeList.getListDomainDesign())) {
				model.addAttribute("hasFkFlg", 1);
			} else {
				model.addAttribute("hasFkFlg", 0);
			}
			codeList.setCodelistDetails(codelistService.getCodeListDetailByCodeListId(codeListForm.getCodeListId()));
			model.addAttribute("moduleStatus", codeList.getModuleStatus());
		} catch (BusinessException ex) {
			//notExistFlg = 1: show 
			//notExistFlg = 0: hidden
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			return SEARCH_REDIRECT_PATH;
		}

		codeListForm = beanMapper.map(codeList, CodeListForm.class);
		codeListForm.setOpenOwner(checkFormOpenOwner);
		model.addAttribute("codeListForm", codeListForm);
		model.addAttribute("notExistFlg", 0);
		return LINK_VIEW;
	}

	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modify")
	public String displayModify(@ModelAttribute(CODELIST_FORM_NAME) CodeListForm codeListForm, Model model, RedirectAttributes redirectAttr) {
		String destination = StringUtils.EMPTY;
		try{
			checkChangeProject(true); 
			CodeList codeList = codelistService.validateCodeList(codeListForm.getCodeListId(), SessionUtils.getCurrentProjectId(),SessionUtils.getAccountId(), true);
			codeList.setCodelistDetails(codelistService.getCodeListDetailByCodeListId(codeListForm.getCodeListId()));
			/*codeList.setProjectId(SessionUtils.getCurrentProjectId());*/
			codeListForm = beanMapper.map(codeList, CodeListForm.class);
			model.addAttribute("codeListForm", codeListForm);
			destination = LINK_MODIFY;
		} catch (BusinessException be) {
			/*model.addAttribute("notExistFlg", 1);
			if (MODE_SEARCH.equals(codeListForm.getMode())) {
				redirectAttr.addFlashAttribute("message", be.getResultMessages());
				destination = SEARCH_REDIRECT_PATH;
			} else if (MODE_VIEW.equals(codeListForm.getMode())) {
				model.addAttribute("message", be.getResultMessages());
				destination = LINK_VIEW;
			}*/

			if (MODE_VIEW.equals(codeListForm.getMode())) {
				redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_CODELIST)); // message title
				redirectAttr.addFlashAttribute("message", be.getResultMessages());// message content
				return DbDomainConst.REDIRECT_DELETION_SUCCESS;
			}

			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			destination = SEARCH_REDIRECT_PATH;
		}
		return destination;
	}

	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String processModify(@Validated CodeListForm form,BindingResult bindingResult, Model model,RedirectAttributes attributes) {
		
		if (bindingResult.hasErrors()) {
			ValidationUtils.setBindingResult(bindingResult, model);
			return LINK_MODIFY;
		}

		CodeList codeList = beanMapper.map(form, CodeList.class);
		codeList.setProjectId(SessionUtils.getCurrentProjectId());
		try{
			checkChangeProject(true); 
			codelistService.modifyCodelist(codeList, SessionUtils.getAccountId(), SessionUtils.getCurrentProjectId());
			attributes.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(CommonMessageConst.TQP_CODELIST)));
			return SEARCH_REDIRECT_PATH;
		} catch(BusinessException ex){

			/*if(CommonMessageConst.ERR_SYS_0037.equals(ex.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("notExistFlg", "1");
			}*/

			model.addAttribute("message", ex.getResultMessages());
			return LINK_MODIFY;
		}
	}
	
	@RequestMapping(value = "delete")
	public String processDelete(CodeListForm codeListForm, RedirectAttributes redirectAttr, Model model) {
		

		CodeList codeList = beanMapper.map(codeListForm, CodeList.class);
		try {
			checkChangeProject(true); 
			codeList = codelistService.deleteCodelist(codeList, SessionUtils.getAccountId(), SessionUtils.getCurrentProjectId());
			/*if (codeList == null) {
				redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004,  MessageUtils.getMessage(CodelistMessageConst.SC_CODELIST_0005)));
				return REDIRECT_DELETECOMPLETE;
			}*/

			if (codeList != null && codeList.getResultMessages() != null) {
				throw new BusinessException(codeList.getResultMessages());
			}

		} catch (BusinessException ex) {
			/*model.addAttribute("message", ex.getResultMessages());
			model.addAttribute("notExistFlg", "0");
			
			//if not exist or fixed design status
			String errMessageCode = StringUtils.defaultString(ex.getResultMessages().getList().get(0).getCode(), CommonMessageConst.ERR_SYS_0037);
			if(CommonMessageConst.ERR_SYS_0037.equals(errMessageCode) || CommonMessageConst.ERR_SYS_0111.equals(errMessageCode)) {
				model.addAttribute("notExistFlg", "1");
			} else {
				if( !FunctionCommon.isEmpty(codeList.getListScreenItem()) || !FunctionCommon.isEmpty(codeList.getListTableDesignItems())
						|| !FunctionCommon.isEmpty(codeList.getListDomainDesign())) {
					model.addAttribute("notFkFlg", 0);
				}

				codeList.setCodelistDetails(codelistService.getCodeListDetailByCodeListId(codeList.getCodeListId()));
				codeListForm = beanMapper.map(codeList, CodeListForm.class);
				model.addAttribute("codeListForm", codeListForm);
			}*/
			
			redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_CODELIST));
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			return DbDomainConst.REDIRECT_DELETION_SUCCESS;
			
		}

		redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_CODELIST));
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004, MessageUtils.getMessage(CodelistMessageConst.SC_CODELIST_0005)));
		return DbDomainConst.REDIRECT_DELETION_SUCCESS;
	}
}
