package org.terasoluna.qp.app.styledesign;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.codelist.CodeList;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.qp.app.common.BaseController;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.AccountProfileMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.Account;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.ProjectItem;
import org.terasoluna.qp.domain.service.project.ProjectItemSetting;
import org.terasoluna.qp.domain.service.project.ProjectService;
import org.terasoluna.qp.domain.service.projecttheme.ProjectThemeService;

@Controller
@RequestMapping(value = "styledesign")
@TransactionTokenCheck(value = "styledesign")
public class StyleDesignController extends BaseController{
	
	private static final String SEARCH_REDIRECT_PATH = "redirect:/project/search";
	
	@Inject
	@Named("CL_THEME_COLOR")
	private CodeList clstTheme;
	
	@Inject
	ProjectService projectService;
	
	@Inject
	ProjectThemeService projectThemeService;
	
	/**
	 * Initialize view account screen
	 * 
	 * @return VIEW_FORM_PATH
	 */
	@RequestMapping(value = "view")
	public String displayView(StyleDesignForm styleDesignForm, Model model, RedirectAttributes redirectAttr) {
		try {
			
			Project project = projectService.getProjectInformation(styleDesignForm.getProjectId(), SessionUtils.getAccountId(), true);
			
			
			Map<String, String> theme = projectService.findThemeByProjectId(styleDesignForm.getProjectId());
			List<ProjectItem> listProjectItem = projectService.getProjectItemByProjectId(styleDesignForm.getProjectId(), project.getDefaultLanguageId());
			List<ProjectItemSetting> listProjectItemSetting = StyleDesignFunction.parseToListProjectItemSetting(listProjectItem);
			JSONArray listProjectItemSettingJson = StyleDesignFunction.parseToJson(listProjectItemSetting);

			StyleDesignForm form = new StyleDesignForm();
			form.setProjectId(styleDesignForm.getProjectId());
			if (theme.size() > 1) {
				form.setMapTheme(theme);
			} else {
				form.setMapTheme(clstTheme.asMap());
			}
			
			model.addAttribute("projectStatus", project.getStatus());
			model.addAttribute("styleDesignForm", form);
			
			model.addAttribute("projectItem", listProjectItemSettingJson);
			return "styledesign/viewForm";
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return SEARCH_REDIRECT_PATH;
		}
	}

	@RequestMapping(value = "view", method=RequestMethod.POST)
	public String saveView(@ModelAttribute StyleDesignForm styleDesignForm, @RequestParam("fileUpload") MultipartFile fileUpload, @RequestParam("logoUpload") MultipartFile logoUpload, @RequestParam("formElement") String formElement, Model model, RedirectAttributes redirectAttr) {
		styleDesignForm.setProjectId(styleDesignForm.getProjectId());
		String imageBase64 = "";
		String logo = "";
		
		if (!fileUpload.isEmpty()) {
            try {
                byte[] bytes = fileUpload.getBytes();
                
                imageBase64 = new sun.misc.BASE64Encoder().encode(bytes);
            } catch (Exception e) {
                
            }
        }
		
		if (!logoUpload.isEmpty()) {
            try {
                byte[] bytes = logoUpload.getBytes();
                
                logo = new sun.misc.BASE64Encoder().encode(bytes);
            } catch (Exception e) {
                
            }
        }
		
		
		if (imageBase64.isEmpty() && styleDesignForm.getBackGroundColor() != null && !styleDesignForm.getBackGroundColor().isEmpty()) {
			imageBase64 = styleDesignForm.getBackGroundColor();
		}
		
	
		if (logo.isEmpty()  && styleDesignForm.getLogo() != null && !styleDesignForm.getLogo().isEmpty()) {
			logo = styleDesignForm.getLogo();
		}
		
		List<ProjectItemSetting> listProjectItemSetting = StyleDesignFunction.readJson(formElement);
		//comment, waiting process limit file
		//styleDesignForm.getMapTheme().put("commom-background-image", imageBase64);
		styleDesignForm.getMapTheme().put("logo", logo);
		styleDesignForm.getMapTheme().put("sizeOption", styleDesignForm.getSizeOption());
		projectService.settingTheme(styleDesignForm.getMapTheme(), styleDesignForm.getProjectId(), SessionUtils.getAccountId());
		
		//KhanhTH
		List<ProjectItem> listProjectItem = StyleDesignFunction.parseToListProjectItem(listProjectItemSetting, styleDesignForm.getProjectId());
		projectService.addProjectItem(styleDesignForm.getProjectId(), listProjectItem);
		
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, 
				MessageUtils.getMessage(AccountProfileMessageConst.SC_ACCOUNTPROFILE_0039)));
		
		Map<String, String> theme = projectService.findThemeByProjectId(styleDesignForm.getProjectId());
		styleDesignForm.setMapTheme(theme);
		model.addAttribute("styleDesignForm", styleDesignForm);
		return "redirect:view?projectId=" + styleDesignForm.getProjectId();
	}
	
	@RequestMapping(value = "previewTemp")
	public String displayPreviewTemp(HttpServletRequest request, @ModelAttribute StyleDesignForm styleDesignForm, Model model, RedirectAttributes redirectAttr) {
		styleDesignForm.getMapTheme().put("logo", styleDesignForm.getLogo());
		request.getSession().setAttribute("projectStyle", StyleDesignFunction.getProjectStyle(styleDesignForm.getMapTheme()));
		request.getSession().setAttribute("data", request.getParameter("formElement"));
		return "styledesign/previewForm";
	}
	
	@RequestMapping(value = "modifyTheme", method = { RequestMethod.GET }, params = { "projectId" })
	public String displayModifyTheme(@ModelAttribute StyleDesignForm styleDesignForm,Model model) {
		Project project = projectService.getProjectInformation(styleDesignForm.getProjectId(), SessionUtils.getAccountId(), true);
		// get information of theme
		List<ProjectItem> listProjectItem = projectService.getProjectItemByProjectId(styleDesignForm.getProjectId(), project.getWorkingLanguageId());
		List<ProjectItemSetting> listProjectItemSetting = StyleDesignFunction.parseToListProjectItemSetting(listProjectItem);
		JSONArray listProjectItemSettingJson = StyleDesignFunction.parseToJson(listProjectItemSetting);
		model.addAttribute("projectItem", listProjectItemSettingJson);
		@SuppressWarnings("unchecked")
		Map<String, String> themes = (Map<String, String>) SessionUtils.get(SessionUtils.THEME_INFOR);
		if (themes.size() == 0) {
			themes = clstTheme.asMap();
		}
		
		model.addAttribute("projectStatus", project.getStatus());
		styleDesignForm.setMapTheme(themes);
		return "styledesign/viewForm";
	}
	
	@RequestMapping(value = "/setDefaultTheme", method = { RequestMethod.GET })
	public String processRegisterDefaultTheme(@RequestParam(value = "projectId", required = true) Long projectId, HttpServletRequest request, RedirectAttributes redirectAttr) {
		CommonModel common = this.initCommon();
		common.setProjectId(projectId);
		
		projectThemeService.saveSetting(clstTheme.asMap(), common);
		SessionUtils.set(SessionUtils.THEME_INFOR, clstTheme.asMap());
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003,MessageUtils.getMessage("Project theme")));
		request.getSession().setAttribute("data", request.getParameter("formElement"));
		return "redirect:modifyTheme?projectId="+projectId;
	}
	

	
	
	@RequestMapping(value="preview")
	public String previewStyle(HttpServletRequest request, Model model) {
		model.addAttribute("projectStyle", request.getSession().getAttribute("projectStyle"));
		model.addAttribute("data", request.getSession().getAttribute("data"));
		
		request.getSession().removeAttribute("projectStyle");
		request.getSession().removeAttribute("data");
		
		model.addAttribute("serverTime", FunctionCommon.getCurrentTime());

		model.addAttribute("account", (Account) SessionUtils.get(SessionUtils.ACCOUNT_INFOR));
		
		return "styledesign/previewForm";
	}
}
