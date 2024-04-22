package org.terasoluna.qp.app.webservicetokenmanagement;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
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
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.WebServiceTokenManagementMessageConst;
import org.terasoluna.qp.domain.model.WebServiceToken;
import org.terasoluna.qp.domain.service.webservicetoken.WebServiceTokenSearchCriteria;
import org.terasoluna.qp.domain.service.webservicetoken.WebServiceTokenService;

@Controller
@RequestMapping(value = "webservicetokenmanagement")
@TransactionTokenCheck(value = "webservicetokenmanagement")
@SessionAttributes(types = { WebServiceTokenManagementSearchForm.class })
public class WebServiceTokenManagementController{

	private static final String ACTION_SEARCH = "/webservicetokenmanagement/search";
	private static final String LINK_SEARCH = "webservicetokenmanagement/searchForm";
	private static final String WSTOKEN_FORM_NAME = "webServiceTokenManagementForm";
	private static final String LINK_REGISTER = "webservicetokenmanagement/registerForm";
	private static final String LINK_VIEW = "webservicetokenmanagement/viewForm";
	private static final String LINK_MODIFY = "webservicetokenmanagement/modifyForm";
	/*private static final String LINK_REFRESH = "refreshForm";*/
	private static final String SEARCH_REDIRECT_PATH = "redirect:/webservicetokenmanagement/search";
	private static final String REDIRECT_DELETECOMPLETE = "redirect:/complete";
	/*private static final String VIEW_REDIRECT_PATH = "redirect:/codelist/view";*/
	
	private static final String MODE_SEARCH = "0";
	private static final String MODE_VIEW = "1";

	@Inject
	WebServiceTokenService webServiceTokenService;

	@Inject
	Mapper beanMapper;
	
	@Inject
	WebServiceTokenValidator webServiceTokenValidator;

	@InitBinder("webServiceTokenManagementForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(webServiceTokenValidator);
	}

	@ModelAttribute
	public WebServiceTokenManagementSearchForm setUpFormCriteria() {
		WebServiceTokenManagementSearchForm form = new WebServiceTokenManagementSearchForm();
		return form;
	}

	@ModelAttribute
	public WebServiceTokenManagementForm setUpFormWebServiceToken() {
		WebServiceTokenManagementForm form = new WebServiceTokenManagementForm();
		return form;
	}

	
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String displaySearch(@RequestParam(value = "init", required = false) String init, 
			@ModelAttribute WebServiceTokenManagementSearchForm webServiceSearchForm, Model model,@PageableDefault Pageable pageable,SessionStatus sessionStatus) {
	
		pageable = new PageRequest(pageable.getPageNumber(),PageSizeUtils.getPageSizeOfAction(ACTION_SEARCH),pageable.getSort());
		WebServiceTokenSearchCriteria criteria = beanMapper.map(webServiceSearchForm, WebServiceTokenSearchCriteria.class);
		Page<WebServiceToken> page = webServiceTokenService.getBySearchCriteria(criteria,pageable);
		model.addAttribute("page", page);
		return LINK_SEARCH;
	}

	@RequestMapping(value = "search", method = RequestMethod.POST)
	public String processSearch(@ModelAttribute WebServiceTokenManagementSearchForm webServiceSearchForm, Model model,@PageableDefault Pageable pageable, SessionStatus status) {
		pageable = new PageRequest(pageable.getPageNumber(),PageSizeUtils.getPageSizeOfAction(ACTION_SEARCH),pageable.getSort());
		WebServiceTokenSearchCriteria criteria = beanMapper.map(webServiceSearchForm,WebServiceTokenSearchCriteria.class);
		Page<WebServiceToken> pageCodelist = webServiceTokenService.getBySearchCriteria(criteria, pageable);
		model.addAttribute("page", pageCodelist);
		return LINK_SEARCH;
	}
	
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String displayRegister(@ModelAttribute(WSTOKEN_FORM_NAME) WebServiceTokenManagementForm webServiceTokenManagementForm,Model model) {
		return LINK_REGISTER;
	}
	
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.IN)
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String processRegister(@Validated WebServiceTokenManagementForm webServiceTokenManagementForm,BindingResult bindingResult, Model model,RedirectAttributes redirectAttr) {
		if (bindingResult.hasErrors()) {
			ValidationUtils.setBindingResult(bindingResult, model);
			return LINK_REGISTER;
		}
		WebServiceToken wsToken = beanMapper.map(webServiceTokenManagementForm, WebServiceToken.class);
		try {
			Long accountId = SessionUtils.getAccountId();
			wsToken.setCreatedBy(accountId);
			wsToken.setUpdatedBy(accountId);
			
			webServiceTokenService.registerWebServiceToken(wsToken);
		} catch (BusinessException ex) {
			model.addAttribute("message", ex.getResultMessages());
			return LINK_REGISTER;
		}
		redirectAttr.addFlashAttribute("message",ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage(WebServiceTokenManagementMessageConst.SC_WEBSERVICETOKENMANAGEMENT_0010)));
		return SEARCH_REDIRECT_PATH;
	}

	
	@RequestMapping(value = "view")
	public String displayView(@ModelAttribute(WSTOKEN_FORM_NAME) WebServiceTokenManagementForm webServiceTokenManagementForm, Model model, RedirectAttributes redirectAttr) {
		WebServiceToken wsToken = null;

		try {
			wsToken = webServiceTokenService.getWebServiceToken(webServiceTokenManagementForm.getWsTokenId());
		} catch (BusinessException ex) {
			// Web service token does not exist
			model.addAttribute("notExistFlg", 1);
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			return SEARCH_REDIRECT_PATH;
		}

		webServiceTokenManagementForm = beanMapper.map(wsToken, WebServiceTokenManagementForm.class);
		model.addAttribute("webServiceTokenManagementForm", webServiceTokenManagementForm);
		model.addAttribute("notExistFlg", 0);
		return LINK_VIEW;
	}
	
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modify")
	public String displayModify(@ModelAttribute(WSTOKEN_FORM_NAME) WebServiceTokenManagementForm webServiceTokenManagementForm, Model model, RedirectAttributes redirectAttr) {
		String destination = StringUtils.EMPTY;
		try{
			WebServiceToken wsToken = webServiceTokenService.getWebServiceToken(webServiceTokenManagementForm.getWsTokenId());
			webServiceTokenManagementForm = beanMapper.map(wsToken, WebServiceTokenManagementForm.class);
			model.addAttribute("webServiceTokenManagementForm", webServiceTokenManagementForm);
			destination = LINK_MODIFY;
		} catch (BusinessException be) {
			// Web service token does not exist
			model.addAttribute("notExistFlg", 1);
			
			// Redirect to where it was called from (based on mode)
			if (MODE_SEARCH.equals(webServiceTokenManagementForm.getMode())) {
				redirectAttr.addFlashAttribute("message", be.getResultMessages());
				destination = SEARCH_REDIRECT_PATH;
			} else if (MODE_VIEW.equals(webServiceTokenManagementForm.getMode())) {
				model.addAttribute("message", be.getResultMessages());
				destination = LINK_VIEW;
			}
		}
		return destination;
	}
	
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String processModify(@Validated WebServiceTokenManagementForm webServiceTokenManagementForm,BindingResult bindingResult, Model model,RedirectAttributes attributes) {
		if (bindingResult.hasErrors()) {
			ValidationUtils.setBindingResult(bindingResult, model);
			return LINK_MODIFY;
		}

		WebServiceToken wsToken = beanMapper.map(webServiceTokenManagementForm, WebServiceToken.class);
		try{
			Long accountId = SessionUtils.getAccountId();
			wsToken.setUpdatedBy(accountId);
			webServiceTokenService.modifyWebServiceToken(wsToken);
			attributes.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(WebServiceTokenManagementMessageConst.SC_WEBSERVICETOKENMANAGEMENT_0010)));
			return SEARCH_REDIRECT_PATH;
		} catch(BusinessException ex){
			if(CommonMessageConst.ERR_SYS_0037.equals(ex.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("notExistFlg", "1");
			}
			model.addAttribute("message", ex.getResultMessages());
			return LINK_MODIFY;
		}
	}
	
	@RequestMapping(value = "delete")
	public String processDelete(WebServiceTokenManagementForm webServiceTokenManagementForm, RedirectAttributes redirectAttr, Model model) {
		WebServiceToken wsToken = beanMapper.map(webServiceTokenManagementForm, WebServiceToken.class);
		try {
			webServiceTokenService.deleteWebServiceToken(wsToken);
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004,  MessageUtils.getMessage(WebServiceTokenManagementMessageConst.SC_WEBSERVICETOKENMANAGEMENT_0010)));
			return REDIRECT_DELETECOMPLETE;

		} catch (BusinessException ex) {
			if(CommonMessageConst.ERR_SYS_0037.equals(ex.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("notExistFlg", "1");
			}
			model.addAttribute("message", ex.getResultMessages());
		}
		return LINK_VIEW;
	}
}
