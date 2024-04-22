package org.terasoluna.qp.app.capture;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.dozer.Mapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.businessdesign.BusinessDesignForm;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.screendesign.ScreenDesignForm;
import org.terasoluna.qp.app.screendesign.ScreenDesignFunction;
import org.terasoluna.qp.app.screendesign.ScreenDesignSearchForm;
import org.terasoluna.qp.app.screendesign.ScreenItemForm;
import org.terasoluna.qp.app.screendesign.ScreenTransitionForm;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.LanguageDesign;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.ScreenAction;
import org.terasoluna.qp.domain.model.ScreenArea;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.model.ScreenForm;
import org.terasoluna.qp.domain.model.ScreenGroupItem;
import org.terasoluna.qp.domain.model.ScreenItem;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignService;
import org.terasoluna.qp.domain.service.languagedesign.LanguageDesignService;
import org.terasoluna.qp.domain.service.module.ModuleService;
import org.terasoluna.qp.domain.service.project.ProjectService;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignService;

@Controller
@RequestMapping(value = "capture")
@SessionAttributes(types = { ScreenDesignSearchForm.class, ScreenTransitionForm.class, ScreenItemForm.class})
public class CaptureController{

	//private static final Logger logger = LoggerFactory.getLogger(CaptureController.class);
	
	@Inject
	private ScreenDesignService screenDesignService;

	@Inject
	ModuleService moduleService;
	
	@Inject
	BusinessDesignService businessDesignService;
	
	@Inject
	ProjectService projectService;
	
	@Inject
	LanguageDesignService languageDesignService;
	
	@Inject
	Mapper beanMapper;
	
	private static final String SCREEN_VIEW = "screendesign/viewScreenCaptureForm";
	private static final String SCREEN_VIEW_ERROR = "screendesign/viewScreenCaptureForm";
	private static final String TRANSITION_VIEW = "screendesign/transitionCaptureForm";
	private static final String TRANSITION_VIEW_ERROR = "screendesign/transitionCaptureForm";
	private static final String BUSINESS_DESIGN_VIEW = "businessdesign/viewCaptureForm";
	private static final String BUSINESS_DESIGN_VIEW_ERROR = "businessdesign/viewCaptureForm";
	
	@RequestMapping(value = "screen" , method = RequestMethod.GET)
	public String displayScreenViewForCapture(ScreenDesignForm screenDesignForm, @RequestParam("screenId") Long screenId, @RequestParam("languageId") Long languageId, 
			@RequestParam("projectId") Long projectId, Model model, RedirectAttributes redirectAttr) {
		
		screenDesignForm.setScreenId(screenId);

		ScreenDesign screenDesign = screenDesignService.getScreenDesignByIdForPreview(screenDesignForm.getScreenId(),1, languageId, projectId);
		
		if (screenDesign == null) {
			redirectAttr.addFlashAttribute("message", ResultMessages.error().add("err.sys.0037", MessageUtils.getMessage("tqp.screendesign")));
			return SCREEN_VIEW_ERROR;
		}
		try {
			screenDesignForm = mappingView(screenDesign, screenDesign.getScreenForms(), screenDesign.getScreenAreas(), screenDesign.getScreenGroupItems(), screenDesign.getScreenItems());
			Module module = moduleService.findModuleById(screenDesign.getModuleId());
			model.addAttribute("module", module);
			model.addAttribute("screenDesignForm", screenDesignForm);
			model.addAttribute("projectStyle", screenDesignService.getProjectStyle(projectId));
		} catch(BusinessException ex) {
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			return SCREEN_VIEW_ERROR;
		}
		
		return SCREEN_VIEW;
	}
	
	public ScreenDesignForm mappingView(ScreenDesign screenDesign,
			ScreenForm[] screenForms, ScreenArea[] screenAreas,
			ScreenGroupItem[] screenGroupItems, ScreenItem[] screenItems) {
		ScreenDesignForm form = new ScreenDesignForm();
		
		form.setScreenId(screenDesign.getScreenId());
		form.setScreenCode(screenDesign.getScreenCode());
		form.setScreenUrlCode(screenDesign.getScreenUrlCode());
		form.setScreenPatternType(screenDesign.getScreenPatternType());
		form.setTemplateType(screenDesign.getTemplateType());
		form.setRemark(screenDesign.getRemark());
		form.setUpdatedDate(screenDesign.getUpdatedDate());
		
		form.setDesignMode(screenDesign.getDesignMode());
		form.setDesignStatus(screenDesign.getDesignStatus());
		
		if (screenDesign.getFunctionDesignId() != null) {
			form.setFunctionDesignId(screenDesign.getFunctionDesignId() + "");
		}
		form.setFunctionDesignName(screenDesign.getFunctionDesignName());
		
		if (screenDesign.getMessageDesign() != null) {
			form.setScreenName(screenDesign.getMessageDesign().getMessageString());
		}
		
		if (screenDesign.getMessageDesign() != null) {
			form.setMessageDesign(screenDesign.getMessageDesign());
			form.setScreenName(screenDesign.getMessageDesign().getMessageString());
		}
		
		form.setAreaNonGroup(new ArrayList<ScreenArea>());
		for (int i = 0; i < screenAreas.length; i++) {			
			//get area type link header
			if (screenAreas[i].getAreaType().equals(-1)) 
			{
				form.getAreaNonGroup().add(screenAreas[i]);
			}
		}
		
		
		form.setHeaderLinkItems(new ArrayList<ScreenItem>());
		// 
		for (int i = 0; i < screenItems.length; i++) {
			for (int j = 0; j < form.getAreaNonGroup().size(); j++) {
				if (screenItems[i].getScreenAreaId().equals(form.getAreaNonGroup().get(j).getScreenAreaId())) {
					form.getHeaderLinkItems().add(screenItems[i]);
				}
			}
		}
		
		//add screen item to area
		for (int i = 0; i < screenAreas.length; i++) {
			List<ScreenItem> items = new ArrayList<ScreenItem>();
			
			for (int j = 0; j < screenItems.length; j++) {
				if (screenAreas[i].getScreenAreaId().equals(screenItems[j].getScreenAreaId())) {
					items.add(screenItems[j]);
				}
			}
			screenAreas[i].setItems(items);
		}
		
		screenGroupItems = ScreenDesignFunction.checkColRowSpan(screenAreas, screenGroupItems, screenItems);
		
		for (int i = 0; i < screenGroupItems.length; i++) {
			form.getScreenGroups().put(screenGroupItems[i].getGroupItemId(), screenGroupItems[i]);
		}
		
		for (int i = 0; i < screenAreas.length; i++) {
			screenAreas[i] = ScreenDesignFunction.setAreaPosition(screenForms, screenAreas[i], true);
		}
		
		form.setScreenForms(screenForms);
		form.setScreenAreas(screenAreas);
		form.setScreenGroupItems(screenGroupItems);
		form.setScreenItems(screenItems);
		form.setScreenParameters(screenDesign.getScreenParameters());
		return form;
	}
	
	@RequestMapping(value = "screentransition" , method = RequestMethod.GET)
	public String displayScreenTransitionViewForCapture(ScreenTransitionForm screenTransitionForm, @RequestParam("moduleId") Long moduleId, @RequestParam("languageId") Long languageId, 
			@RequestParam("projectId") Long projectId, Model model, RedirectAttributes redirectAttr) {
		
		try {
			List<ScreenDesign> scrDesigns = new ArrayList<ScreenDesign>();
			// Initial variable store all screen transition connector
			//ScreenDesign screenDesign = beanMapper.map(screenTransitionForm, ScreenDesign.class);
			
			/*screenDesignService.setWorkingLanguageId(languageId);
			screenDesignService.setWorkingProjectId(projectId);*/
			if (moduleId == null || moduleId < 0) {
				scrDesigns = screenDesignService.getAllScreenInfoByProjectId(projectId, languageId);
			} else {
				scrDesigns = screenDesignService.getAllScreenInfoByModuleId(moduleId, languageId);
				Module module = moduleService.findModuleById(moduleId);
				String jsonInfo = "{\"moduleId\":" + moduleId + ", \"projectId\": "+module.getProjectId()+", \"moduleName\": \""+module.getModuleName()+"\", \"moduleCode\": \""+module.getModuleCode()+"\"}";
				model.addAttribute("jsonInfo", jsonInfo);
			}
			List<ScreenAction> scrConnects = screenDesignService.findAllActionByScreenId(scrDesigns, languageId, projectId);
			
			model.addAttribute("scrDesigns", scrDesigns);
			model.addAttribute("scrConnects", scrConnects);	
			
			// Init mode
			screenTransitionForm.setMode(null);
		} catch(BusinessException ex) {
			//redirectAttr.addFlashAttribute("message", ResultMessages.error().add("err.sys.0037", MessageUtils.getMessage("sc.screendesign.0322")));
			return TRANSITION_VIEW_ERROR;
		}
		
		return TRANSITION_VIEW;
	}
	
	@RequestMapping(value = "businessdesign", method = RequestMethod.GET)
	public String displayView(BusinessDesignForm form, @RequestParam("businessLogicId") Long businessLogicId, @RequestParam("languageId") Long languageId, 
			@RequestParam("projectId") Long projectId, Model model, RedirectAttributes redirectAttr, HttpServletRequest request) {
		
		BusinessDesign businessDesign = new BusinessDesign();
		String destination = "";
		Integer checkForm = form.getOpenOwner();
		try {
			
			LanguageDesign languageDesign = new LanguageDesign();
			languageDesign.setLanguageId(languageId);
			//LanguageDesign languageDesign = languageDesignService.findByLanguageDesign(languageDesign);
			Project project = projectService.getProjectInformation(projectId, null);
			/*Account account = new Account();
			SessionUtils.set(SessionUtils.CURRENT_LANGUAGE_DESIGN, languageDesign);
			SessionUtils.set(SessionUtils.CURRENT_PROJECT, project);
			SessionUtils.set(SessionUtils.ACCOUNT_INFOR, account);*/
			
			CommonModel commonModel = new CommonModel();
			commonModel.setWorkingProjectId(projectId);
			commonModel.setCreatedBy(project.getCreatedBy());
			commonModel.setWorkingLanguageId(languageId);
			
			businessDesign = businessDesignService.findBusinessLogicInformation(businessLogicId, true, commonModel,true);
			form = beanMapper.map(businessDesign, BusinessDesignForm.class);
			form.setOpenOwner(checkForm);
			model.addAttribute("businessDesignForm", form);
			destination = BUSINESS_DESIGN_VIEW;
		} catch (BusinessException be) {
			destination = BUSINESS_DESIGN_VIEW_ERROR;
		}
		return destination;
	}
}
