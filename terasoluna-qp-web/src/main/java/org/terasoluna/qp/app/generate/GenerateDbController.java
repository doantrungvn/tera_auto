package org.terasoluna.qp.app.generate;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignSearchCriteria;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignService;

@Controller
@RequestMapping(value = "generatedb")
@TransactionTokenCheck(value = "generatedb")
@SessionAttributes(types = { GenerateDbSearchForm.class})
public class GenerateDbController{

	private static final Logger logger = LoggerFactory.getLogger(GenerateDbController.class);

	@Inject
	ScreenDesignService screenDesignService;

	@Inject
	Mapper beanMapper;

	private static final String ACTION_SEARCH = "/generate/generateDB";
	private static final String SEARCH_LINK = "generate/generateDB";
	/*private static final String VIEW_LINK = "generatedb/viewForm";
	private static final String SEARCH_REDIRECT_PATH = "redirect:/generatedb/search";*/
	
	/**
	 * Identifies methods which initialize the WebDataBinder which will be used for populating command and form object
	 * @param webDataBinder WebDataBinder
	 */
	
	@ModelAttribute
	public GenerateDbSearchForm setUpGenerateDbSearchForm() {
		GenerateDbSearchForm generateDbSearchForm = new GenerateDbSearchForm();
		logger.debug("Init form {}", generateDbSearchForm);
		return generateDbSearchForm;
	}
	
	/**
	 * Initialize search screen design
	 * 
	 * @return SEARCH_LINK
	 */
	@RequestMapping(value = "search", method = { RequestMethod.GET })
	public String displaySearch(
			@RequestParam(value = "init", required = false) String init, 
			@ModelAttribute GenerateDbSearchForm generateDbSearchForm,
			Model model, @PageableDefault Pageable pageable, SessionStatus sessionStatus) {
		
		if(init != null){
			sessionStatus.setComplete();
			generateDbSearchForm = new GenerateDbSearchForm();
			model.addAttribute("generateDbSearchForm", generateDbSearchForm);
		}
		
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(ACTION_SEARCH), pageable.getSort());
		ScreenDesignSearchCriteria screenDesignSearchCriteria = beanMapper.map(generateDbSearchForm, ScreenDesignSearchCriteria.class);
		int[] designMode = {1};
		screenDesignSearchCriteria.setDesignMode(designMode);
		int[] screenParternType = {2};
		screenDesignSearchCriteria.setScreenParternTypes(screenParternType);
		int[] designStatus = {1};
		screenDesignSearchCriteria.setDesignStatus(designStatus);
		Page<ScreenDesign> pageArea = screenDesignService.findPageByCriteria(screenDesignSearchCriteria, pageable, SessionUtils.getCurrentLanguageId());
		model.addAttribute("page", pageArea);

		return SEARCH_LINK;
	}

	/**
	 * Search screen design process
	 * 
	 * @return SEARCH_LINK
	 */
	@RequestMapping(value = "search", method = { RequestMethod.POST })
	public String processSearch(
			@ModelAttribute GenerateDbSearchForm generateDbSearchForm,
			Model model, @PageableDefault Pageable pageable,
			SessionStatus status) {
		
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(ACTION_SEARCH), pageable.getSort());
		ScreenDesignSearchCriteria screenDesignSearchCriteria = beanMapper.map(generateDbSearchForm, ScreenDesignSearchCriteria.class);
		Page<ScreenDesign> pageArea = screenDesignService.findPageByCriteria(screenDesignSearchCriteria, pageable, SessionUtils.getCurrentLanguageId());

		model.addAttribute("page", pageArea);

		return SEARCH_LINK;
	}

	/**
	 * Initialize view screen design screen
	 * 
	 * @return VIEW_LINK
	 *//*
	@RequestMapping(value = "view")
	public String displayView(@RequestParam("screenId") Long screenId,@RequestParam("openOwner") Integer openOwner, Model model
			, RedirectAttributes redirectAttr) {
		
		if (screenId == null) {
			redirectAttr.addFlashAttribute("message", ResultMessages.error().add("err.sys.0037", MessageUtils.getMessage("tqp.screendesign")));
			return SEARCH_REDIRECT_PATH;
		}
		
		ScreenDesign screenDesign = screenDesignService.getScreenDesignById(screenId);
		
		
		if (screenDesign == null) {
			redirectAttr.addFlashAttribute("message", ResultMessages.error().add("err.sys.0037", MessageUtils.getMessage("tqp.screendesign")));
			return SEARCH_REDIRECT_PATH;
		}
		ScreenDesignForm screenDesignForm = mapping(screenDesign, screenDesign.getScreenForms(), screenDesign.getScreenAreas(), screenDesign.getScreenGroupItems(), screenDesign.getScreenItems());
		Module module = moduleService.findModuleById(screenDesign.getModuleId());
		model.addAttribute("module", module);
		screenDesignForm.setOpenOwner(openOwner);	
		model.addAttribute("screenDesignForm", screenDesignForm);
		return VIEW_LINK;
	}	*/

}




