package org.terasoluna.qp.app.menudesign;


import java.util.Collections;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;
import org.terasoluna.qp.app.common.BaseController;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.styledesign.StyleDesignFunction;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.MenuDesign;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.service.menudesign.MenuDesignService;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignService;

@Controller
@RequestMapping(value = "menudesign")
@TransactionTokenCheck(value = "menudesign")
public class MenuDesignController extends BaseController {
	
	private static final String MENU_DESIGN_FORM_PATH ="menudesign/menuDesignForm";
	private static final String PREVIEW_PATH = "menudesign/previewForm";
	
	/** 0: Update. 1: Register**/
	private static final int UPDATE_TYPE_UPDATE = 0;
	
	/** 0: Update. 1: Register**/
	private static final int UPDATE_TYPE_REGISTER = 1;
	
	@Inject
	MenuDesignService menuDesignService; 
	
	@Inject
	ScreenDesignService screenDesignService;
	
	@Inject
	Mapper beanMapper;
	
	@Inject
	MenuDesignValidator menuDesignValidator;
	
	/**
	 * Identifies methods which initialize the WebDataBinder which will be used
	 * for populating command and form object
	 * 
	 * @param webDataBinder WebDataBinder
	 */
	@InitBinder("menuDesignForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(menuDesignValidator);
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@InitBinder
	public void init() {
		moduleCode = CommonMessageConst.TQP_MENUDESIGN;
	}
	
	/**
	 * 
	 * @param menuDesignForm
	 * @param model
	 * @param redirectAttr
	 * @return
	 */
	@RequestMapping(value = "preview")
	public String displayPreView(HttpSession session, Model model, RedirectAttributes redirectAttr) {
		super.checkChangeProject(false);

		CommonModel common = this.initCommon();
		MenuDesignForm menuDesignForm = new MenuDesignForm();
		MenuDesign menuDesign = new MenuDesign();
		
		if (session.getAttribute("menuDesignForm") != null) {
			menuDesignForm = (MenuDesignForm) session.getAttribute("menuDesignForm");
			session.removeAttribute("menuDesignForm");
			menuDesign = beanMapper.map(menuDesignForm, MenuDesign.class);
		}

		Project project = SessionUtils.getCurrentProject();
		menuDesign.setProjectId(project.getProjectId());
		if (StringUtils.isBlank(menuDesign.getHeaderMenuName())) {
			menuDesign.setHeaderMenuName(project.getProjectName());
		}

		model.addAttribute("menuContent", menuDesignService.buildMenu(menuDesign, DbDomainConst.MenuType.PREVIEW));
		model.addAttribute("menuType", menuDesign.getMenuType());
		model.addAttribute("menuDesignForm", menuDesignForm);
		
		model.addAttribute("projectStyle", StyleDesignFunction.getProjectStyle(screenDesignService.getProjectStyle(common.getWorkingProjectId())));
		
		return PREVIEW_PATH;
	}
	
	@RequestMapping(value = "previewTemp")
	public String displayPreviewTemp(HttpServletRequest request, @ModelAttribute MenuDesignForm menuDesignForm, Model model, RedirectAttributes redirectAttr) {
		checkChangeProject(false);
		Collections.sort(menuDesignForm.getListMenuDesignItem());
		request.getSession().setAttribute("menuDesignForm", menuDesignForm);
		model.addAttribute("screenDesignForm", menuDesignForm);
		return PREVIEW_PATH;
		
	}
	
	/**
	 * 
	 * @param menuDesignForm
	 * @param model
	 * @param redirectAttr
	 * @return
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public String displayModify(@RequestParam(value = "init", required = false) String init, MenuDesignForm menuDesignForm, Model model, RedirectAttributes redirectAttr) {
		Project project = SessionUtils.getCurrentProject();
		CommonModel common = this.initCommon();

		if (init != null) {
			super.setOldProjectId(project.getProjectId());
		}
		try {
			super.checkChangeProject(false);

			MenuDesign menuDesign = menuDesignService.getMenuDesignInformation(project.getProjectId(), common.getWorkingLanguageId());

			menuDesignForm = beanMapper.map(menuDesign, MenuDesignForm.class);
			model.addAttribute("menuDesignForm", menuDesignForm);
			model.addAttribute("projectForm", project);
			return MENU_DESIGN_FORM_PATH;
		} catch (BusinessException be) {
			model.addAttribute("message", be.getResultMessages());
			model.addAttribute("menuDesignForm", menuDesignForm);
			return MENU_DESIGN_FORM_PATH;
		}

	}
	
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method=RequestMethod.POST)
	public String processModify(@Validated @ModelAttribute MenuDesignForm menuDesignForm, BindingResult result, Model model, RedirectAttributes redirectAttr) {

		CommonModel common = this.initCommon();
		Project project = SessionUtils.getCurrentProject();
		model.addAttribute("projectForm", project);
		/*Long languageId = common.getWorkingLanguageId();*/

		if (result.hasErrors()){
			MenuDesign menuDesign = beanMapper.map(menuDesignForm, MenuDesign.class);
			menuDesignService.setLevelForMenuDesignItem(menuDesign.getListMenuDesignItem());
			menuDesignForm = beanMapper.map(menuDesign, MenuDesignForm.class);
			model.addAttribute("menuDesignForm", menuDesignForm);
			ValidationUtils.setBindingResult(result, model);
			return MENU_DESIGN_FORM_PATH;
		} else {
			/*menuDesignForm.setProjectId(project.getProjectId());*/
			/*menuDesignForm.setLanguageId(languageId);*/
			
			MenuDesign menuDesign = beanMapper.map(menuDesignForm, MenuDesign.class);
			try {
				super.checkChangeProject(true);
				menuDesignService.registerMenuDesign(menuDesign, common);

				menuDesign = menuDesignService.getMenuDesignInformation(project.getProjectId(), menuDesignForm.getLanguageId());
				menuDesignForm = beanMapper.map(menuDesign, MenuDesignForm.class);
				model.addAttribute("menuDesignForm", menuDesignForm);

				if(menuDesign.getUpdateType() == UPDATE_TYPE_UPDATE){
					model.addAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(CommonMessageConst.TQP_MENUDESIGN)));
				}else if (menuDesign.getUpdateType() == UPDATE_TYPE_REGISTER) {
					model.addAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage(CommonMessageConst.TQP_MENUDESIGN)));
				}
				return MENU_DESIGN_FORM_PATH;
				
			} catch (BusinessException be) {
				/*menuDesign = menuDesignService.getMenuDesignInformation(project.getProjectId(), languageId);
				menuDesignForm = beanMapper.map(menuDesign, MenuDesignForm.class);*/
				model.addAttribute("message", be.getResultMessages());
				model.addAttribute("menuDesignForm", menuDesignForm);
				return MENU_DESIGN_FORM_PATH;
			}
		}
	}
	
	@TransactionTokenCheck(value = "delete", type = TransactionTokenType.IN)
	@RequestMapping(value = "delete", method=RequestMethod.POST)
	public String processDelete(@Validated @ModelAttribute MenuDesignForm menuDesignForm, BindingResult result, Model model, RedirectAttributes redirectAttr) {

		return MENU_DESIGN_FORM_PATH;
	}
}