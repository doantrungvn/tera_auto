package org.terasoluna.qp.app.role;

import java.util.List;

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
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.RoleMessageConst;
import org.terasoluna.qp.domain.model.Role;
import org.terasoluna.qp.domain.repository.role.RoleSearchCriteria;
import org.terasoluna.qp.domain.service.permission.PermissionService;
import org.terasoluna.qp.domain.service.role.RoleService;

@Controller
@RequestMapping(value = "role")
@TransactionTokenCheck(value = "role")
@SessionAttributes(types = { RoleSearchForm.class })
public class RoleController {
	
	private static final String ROLE_FORM_NAME = "roleForm";
	private static final String SEARCH_FORM_PATH ="role/searchForm";
	private static final String ROLE_SEARCH_FORM_NAME = "roleSearchForm";
	private static final String SEARCH_ACTION_PATH ="/role/search";
	private static final String REGISTER_FORM_PATH = "role/registerForm";
	private static final String SEARCH_REDIRECT_PATH = "redirect:/role/search";
	private static final String MODIFY_FORM_PATH = "role/modifyForm";
	private static final String VIEW_REDIRECT_PATH = "redirect:/role/view";
	private static final String MODE_SEARCH = "0";
	private static final String MODE_VIEW = "1";
	
	private static final String VIEW_FORM_PATH = "role/viewForm";
	private static final String REDIRECT_DELETION_SUCCESS = "redirect:/complete";

	/**
	 * Identifies methods which initialize the WebDataBinder which will be used for populating command and form object
	 * @param webDataBinder WebDataBinder
	 */
	@InitBinder(ROLE_FORM_NAME)
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	/**
	 * Identifies methods which initialize the role form object
	 * @return roleForm
	 */
	@ModelAttribute
	public RoleForm setUpRoleForm() {
		RoleForm roleForm = new RoleForm();
		logger.debug("Init form {0}", roleForm);
		
		return roleForm;
	}
	
	/**
	 * Identifies methods which initialize the search form object
	 * @return roleSearchForm
	 */
	@ModelAttribute
	public RoleSearchForm setUpRoleSearchForm() {
		RoleSearchForm roleSearchForm = new RoleSearchForm();
		logger.debug("Init form {0}", roleSearchForm);
		
		return roleSearchForm;
	}
	
	@Inject
	RoleService roleService;

	@Inject
	Mapper beanMapper;
	
	@Inject
	PermissionService permissionService;
	
	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

	/**
	 * Initialize search role screen
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String displaySearch(@RequestParam(value = "init", required = false) String init, @ModelAttribute RoleSearchForm roleSearchForm, Model model, @PageableDefault Pageable pageable, SessionStatus sessionStatus) {
		if(init != null){
			sessionStatus.setComplete();
			roleSearchForm = new RoleSearchForm();
			model.addAttribute(ROLE_SEARCH_FORM_NAME, roleSearchForm);
		}
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
		RoleSearchCriteria roleCriteria = beanMapper.map(roleSearchForm, RoleSearchCriteria.class);
		Page<Role> page = roleService.searchRoles(roleCriteria, pageable);
		model.addAttribute("page", page);
		
		return SEARCH_FORM_PATH;
	}
	
	/**
	 * Search role process
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "search", method = { RequestMethod.POST })
	public String processSearch(@ModelAttribute(ROLE_SEARCH_FORM_NAME) RoleSearchForm roleSearchForm, @PageableDefault Pageable pageable, Model model){
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
		RoleSearchCriteria roleSearchCriteria = beanMapper.map(roleSearchForm, RoleSearchCriteria.class);
		Page<Role> page = roleService.searchRoles(roleSearchCriteria, pageable);
		model.addAttribute("page", page);
		model.addAttribute("roleSearchForm", roleSearchForm);
		
		return SEARCH_FORM_PATH;
	}
	
	/**
	 * Initialize register role screen
	 * @return REGISTER_FORM_PATH
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String displayRegister(RoleForm roleForm, Model model) {
		Role role = roleService.displayRegister();
		role.setStatus(1);
		roleForm = beanMapper.map(role, RoleForm.class);
		model.addAttribute("roleForm", roleForm);
		return REGISTER_FORM_PATH;
	}
	
	/**
	 * Register role process
	 * @param roleForm Role information
	 * @param result BindingResult
	 * @param redirectAttr RedirectAttributes
	 * @param model Model
	 * @return in case of success SEARCH_REDIRECT_PATH will be returned.
	 *  Otherwise it will be returned to REGISTER_FORM_PATH
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.IN)
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String processRegister(@Validated RoleForm roleForm, BindingResult result, RedirectAttributes redirectAtt, Model model){
		if(result.hasErrors()){
			roleForm.setModuleCd(roleForm.getModuleCd());
			return REGISTER_FORM_PATH;
		}
		Role role = beanMapper.map(roleForm, Role.class);
		try {
			role.setCreatedBy(SessionUtils.getAccountId());
			roleService.register(role);
		} catch (BusinessException be) {
			model.addAttribute("message", be.getResultMessages());
			return REGISTER_FORM_PATH;
		}
		redirectAtt.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage(RoleMessageConst.SC_ROLE_0010)));
		return SEARCH_REDIRECT_PATH;
	}

	/**
	 * Initialize modify role screen
	 * @param roleForm RoleForm
	 * @param redirectAttr RedirectAttributes
	 * @param model Model
	 * @return MODIFY_FORM_PATH
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public String displayModify(RoleForm roleForm, Model model, RedirectAttributes redirectAttr) {
		String destination = StringUtils.EMPTY;
		try {
			Role role = roleService.displayModify(roleForm.getRoleId());
			roleForm = beanMapper.map(role, RoleForm.class);
			model.addAttribute(ROLE_FORM_NAME, roleForm);
			destination = MODIFY_FORM_PATH;
		} catch(BusinessException be) {
			/*if (MODE_SEARCH.equals(roleForm.getMode())) {
				redirectAttr.addFlashAttribute("message",be.getResultMessages());
				destination = SEARCH_REDIRECT_PATH;
			} else*/ if (MODE_VIEW.equals(roleForm.getMode())) {
				model.addAttribute("message", be.getResultMessages());
				model.addAttribute("notExistFlg", 1);
				destination = VIEW_FORM_PATH;
			} else {
				redirectAttr.addFlashAttribute("message",be.getResultMessages());
				destination = SEARCH_REDIRECT_PATH;
			}
		}
		
		return destination;
	}
	
	/**
	 * Modify role process
	 * @param roleForm RoleForm
	 * @param redirectAttr RedirectAttributes
	 * @param model Model
	 * @return SEARCH_REDIRECT_PATH
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String processModify(@Validated RoleForm roleForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		if(result.hasErrors()){
			roleForm.setModuleCd(roleForm.getModuleCd());
			return MODIFY_FORM_PATH;
		}
		Role role = beanMapper.map(roleForm, Role.class);
		try {
			role.setUpdatedBy(SessionUtils.getAccountId());
			roleService.modify(role);
		} catch (BusinessException be) {
			if(CommonMessageConst.ERR_SYS_0037.equals(be.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("notExistFlg", 1);
			}
			model.addAttribute("message", be.getResultMessages());
			return MODIFY_FORM_PATH;
		}
		
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(RoleMessageConst.SC_ROLE_0010)));
		return SEARCH_REDIRECT_PATH;
	}

	/**
	 * View role process
	 * @param roleForm ProjectForm
	 * @param model Model
	 * @return VIEW_FORM_PATH
	 */
	@RequestMapping(value = "view")
	private String processView(RoleForm roleForm, Model model, RedirectAttributes redirectAttr) {
		Role role = beanMapper.map(roleForm, Role.class);
		
		try {
			// get role information
			role = roleService.findRoleInfoAndPermission(role.getRoleId());
		} catch (BusinessException be) {
			roleForm.setHasErrors(true);
			model.addAttribute(ROLE_FORM_NAME, roleForm);
			model.addAttribute("message", be.getResultMessages());
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			if (MODE_SEARCH.equals(roleForm.getMode())) {
				return VIEW_REDIRECT_PATH;
			} else if (MODE_VIEW.equals(roleForm.getMode())) {
				return SEARCH_REDIRECT_PATH;
			}
		}
		model.addAttribute("lstModuleCode", role.getLstDistinctModuleCode());
		roleForm = beanMapper.map(role, RoleForm.class);
		model.addAttribute("notExistFlg", 0);
		model.addAttribute(ROLE_FORM_NAME, roleForm);
		return VIEW_FORM_PATH;
	}
	
	/**
	 * Delete role process
	 * In case role is in-used Delete role process will not be success and forward to view including error message
	 * @param roleForm RoleForm
	 * @param redirectAttr RedirectAttributes
	 * @param model Model
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String processDelete(RoleForm roleForm, Model model, RedirectAttributes redirectAttributes) {
		Role role = beanMapper.map(roleForm, Role.class);
		try {
			roleService.delete(role.getRoleId());
			// Get account list using role
		} catch (BusinessException ex) {
			String errMessageCode = StringUtils.defaultString(ex.getResultMessages().getList().get(0).getCode(), CommonMessageConst.ERR_SYS_0037);
			if(CommonMessageConst.ERR_SYS_0037.equals(errMessageCode) || CommonMessageConst.ERR_SYS_0111.equals(errMessageCode)) {
				model.addAttribute("notExistFlg", "1");
			}else{
				model.addAttribute("notExistFlg", "0");
				model.addAttribute("roleForm", roleForm);
				model.addAttribute("lstModuleCode", role.getLstDistinctModuleCode());
				List<String> accountLst = roleService.getRoleAppliedUserAccount(role.getRoleId());
				model.addAttribute("lstAccountName",accountLst);
			}
			model.addAttribute("message", ex.getResultMessages());
			
			return VIEW_FORM_PATH;
		}
		redirectAttributes.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004, MessageUtils.getMessage(RoleMessageConst.SC_ROLE_0010)));
		return REDIRECT_DELETION_SUCCESS;
	}

}
