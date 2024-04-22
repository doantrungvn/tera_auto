package org.terasoluna.qp.app.screendesign;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.ScreenDesignMessageConst;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.FormulaDefinition;
import org.terasoluna.qp.domain.model.FormulaDetail;
import org.terasoluna.qp.domain.model.FormulaMethodInput;
import org.terasoluna.qp.domain.model.FormulaMethodOutput;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.ScreenArea;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.model.ScreenForm;
import org.terasoluna.qp.domain.model.ScreenItem;
import org.terasoluna.qp.domain.model.ScreenItemStatus;
import org.terasoluna.qp.domain.repository.businessdesign.BusinessDesignRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.FormulaDefinitionRepository;
import org.terasoluna.qp.domain.repository.screenarea.ScreenAreaRepository;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignRepository;
import org.terasoluna.qp.domain.repository.screenform.ScreenFormRepository;
import org.terasoluna.qp.domain.repository.screenitem.ScreenItemRepository;
import org.terasoluna.qp.domain.repository.screenitemstatus.ScreenItemStatusRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignService;
import org.terasoluna.qp.domain.service.decisiontable.DecisionTableUtils;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignService;
import org.terasoluna.qp.domain.service.screenitemstatus.ScreenItemStatusService;

@Controller
@RequestMapping(value="screendesign")
public class ScreenStatusController {
	@Inject
	ScreenDesignService screenDesignService;
	
	@Inject
	ScreenFormRepository screenFormRepository;
	
	@Inject
	ScreenAreaRepository screenAreaRepository;
	
	@Inject
	ScreenItemRepository screenItemRepository;
	
	@Inject
	ScreenDesignRepository screenDesignRepository;
	
	@Inject
	ScreenItemStatusService screenItemStatusService;
	
	@Inject
	FormulaDefinitionRepository formulaDefinitionRepository;
	
	@Inject
	ScreenItemStatusRepository screenItemStatusRepository;
	
	@Inject
	BusinessDesignRepository businessDesignRepository;
	
	@Inject
	BusinessDesignService  businessDesignService;
	
	/*@Inject
	FormulaDefinitionService formulaDefinitionService;*/
	
	/*private static final String SCREEN_STATUS_FORM = "screendesign/screenStatus";*/
	private static final String SEARCH_REDIRECT_PATH = "redirect:/screendesign/search";
	
	private static final Integer FORM = 1;
	private static final Integer AREA = 2;
	private static final Integer ITEM = 3;
	private static final Integer PROCESS = 1;
	
	@RequestMapping(value="screenStatus" , method = RequestMethod.GET)
	public String displayScreenStatus(@RequestParam("screenId") Long screenId, @ModelAttribute ScreenDesignStatusForm screenDesignStatusForm, Model model, RedirectAttributes redirectAttr) {
		ScreenDesign screenDesign = null;
		try {
			screenDesign = screenDesignService.findById(screenId, SessionUtils.getCurrentLanguageId(), SessionUtils.getCurrentProjectId());
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return SEARCH_REDIRECT_PATH;
		}
		List<Long> screenDesignIds = new ArrayList<Long>();
		screenDesignIds.add(screenDesign.getScreenId());
		// Get screen list items by screenId
		List<ScreenForm> screenForms = screenFormRepository.getScreenFormByScreenId(screenId);
		ScreenArea[] screenareas = screenDesignRepository.getScreenAreaByScreenId(screenId, SessionUtils.getCurrentLanguageDesign(), SessionUtils.getCurrentProjectId());
		ScreenItem[] screenitems = screenDesignRepository.getScreenItemByScreenId(screenId, SessionUtils.getCurrentLanguageDesign(), SessionUtils.getCurrentProjectId());
		List<ScreenItemStatusForm> listOfScreenItemStatusForm = new ArrayList<ScreenItemStatusForm>();
		List<ScreenArea> screenAreas = new ArrayList<ScreenArea>();
		List<ScreenItem> screenItems = new ArrayList<ScreenItem>();
		
		for(ScreenArea screenArea : screenareas) {
			screenAreas.add(screenArea);
		}
		for(ScreenItem screenItem : screenitems) {
			screenItems.add(screenItem);
		}
		
		for(ScreenForm screenForm : screenForms) {
			ScreenItemStatusForm sisForm = new ScreenItemStatusForm();
			sisForm.setItemId(screenForm.getScreenFormId());
			sisForm.setItemCode(screenForm.getFormCode());
			sisForm.setItemName(screenForm.getFormCode());
			sisForm.setItemType(FORM);
			sisForm.setScreenFormId(screenForm.getScreenFormId());
			listOfScreenItemStatusForm.add(sisForm);
			
			for(ScreenArea screenArea : screenAreas) {
				if(screenArea.getScreenFormId().equals(screenForm.getScreenFormId())) {
					ScreenItemStatusForm sisArea = new ScreenItemStatusForm();
					sisArea.setItemId(screenArea.getScreenAreaId());
					sisArea.setItemCode(screenArea.getAreaCode());
					sisArea.setItemType(AREA);
					sisArea.setScreenFormId(screenForm.getScreenFormId());
					if(screenArea.getMessageDesign() != null) {
						sisArea.setItemName(screenArea.getMessageDesign().getMessageString());
					} else {
						sisArea.setItemName(screenArea.getAreaCode());
					}
					listOfScreenItemStatusForm.add(sisArea);
				}
				
				for(ScreenItem screenItem : screenItems) {
					if(screenItem.getScreenAreaId().equals(screenArea.getScreenAreaId()) && screenArea.getScreenFormId().equals(screenForm.getScreenFormId())) {
						ScreenItemStatusForm sisItem = new ScreenItemStatusForm();
						sisItem.setItemId(screenItem.getScreenItemId());
						sisItem.setItemCode(screenItem.getItemCode());
						sisItem.setItemName(screenItem.getItemName());
						sisItem.setScreenFormId(screenForm.getScreenFormId());
						sisItem.setScreenAreaId(screenItem.getScreenAreaId());
						sisItem.setItemType(ITEM);
						if(screenItem.getMessageDesign() != null) {
							sisItem.setItemName(screenItem.getMessageDesign().getMessageString());
						} else {
							sisItem.setItemName(screenItem.getItemCode());
						}
						listOfScreenItemStatusForm.add(sisItem);
					}
				}
			}
		}
		
		List<FormulaDefinition> formulaDefinitions = formulaDefinitionRepository.getFormulaDefinitionByScreenFormId(screenForms);
		List<ScreenItemStatus> screenItemStatusesAll = screenItemStatusRepository.getScreenItemStatusByFormulaDefinitionId(formulaDefinitions);
		List<ScreenItemStatus> screenItemStatusesArea = new ArrayList<ScreenItemStatus>();
		List<ScreenItemStatus> screenItemStatusesItem = new ArrayList<ScreenItemStatus>();
		
		//Convert string formula details to JSON
		List<FormulaDetail> lstFormulaDetailConvertJSON = new ArrayList<FormulaDetail>();
		
		List<FormulaDetail> lstFormulaDetails = formulaDefinitionRepository.findFormulaDetailsByListScreenForms(screenForms);
		List<FormulaMethodInput> lstFormulaMethodInputs = formulaDefinitionRepository.findFormulaMethodInputsByFormulaDetails(lstFormulaDetails);
		List<FormulaMethodOutput> lstFormulaMethodOutputs = formulaDefinitionRepository.findFormulaMethodOutputsByFormulaDetails(lstFormulaDetails);
		
		for (FormulaDetail objDetail : lstFormulaDetails){
			List<FormulaMethodInput> lstInputTemps = new ArrayList<FormulaMethodInput>();
			for(FormulaMethodInput objInput : lstFormulaMethodInputs){
				if(objInput.getFormulaDetailId().equals(objDetail.getFormulaDetailId())){
					lstInputTemps.add(objInput);
				}
			}
			objDetail.setFormulaMethodInputs(lstInputTemps);
			
			List<FormulaMethodOutput> lstOutputTemps = new ArrayList<FormulaMethodOutput>();
			for(FormulaMethodOutput objOutput : lstFormulaMethodOutputs){
				if(objOutput.getFormulaDetailId().equals(objDetail.getFormulaDetailId())){
					lstOutputTemps.add(objOutput);
				}
			}
			objDetail.setFormulaMethodOutputs(lstOutputTemps);
		}
		
		for(FormulaDefinition formulaDefinition : formulaDefinitions) {
			for(FormulaDetail formulaDetail : lstFormulaDetails) {
				if(formulaDefinition.getFormulaDefinitionId().equals(formulaDetail.getFormulaDefinitionId())) {
					lstFormulaDetailConvertJSON.add(formulaDetail);
				}
			}
		}
		
		for(ScreenForm screenForm : screenForms) {
			for(ScreenItemStatusForm screenItemStatusForm : listOfScreenItemStatusForm) {
				if(screenItemStatusForm.getScreenFormId().equals(screenForm.getScreenFormId())) {
					for(ScreenItemStatus screenItemStatus : screenItemStatusesAll) {
						//Formula
						if(screenItemStatusForm.getItemType().equals(FORM) && screenItemStatusForm.getScreenFormId().equals(screenForm.getScreenFormId())) {
							List<FormularForm> formularForms = new ArrayList<FormularForm>();
							for(FormulaDefinition formulaDefinition : formulaDefinitions) {
								if(formulaDefinition.getScreenFormId().equals(screenItemStatusForm.getScreenFormId())) {
									FormularForm formularForm = new FormularForm();
									formularForm.setFormulaDefinitionId(formulaDefinition.getFormulaDefinitionId());
									formularForm.setFormulaDefinitionContent(formulaDefinition.getFormulaDefinitionContent());
									formularForm.setFormulaName(formulaDefinition.getFormulaName());
									formularForm.setScreenFormId(formulaDefinition.getScreenFormId());
									formularForms.add(formularForm);
								}
							}
							screenItemStatusForm.setFormulars(formularForms);
						}
						//Area
						if(screenItemStatusForm.getItemType().equals(AREA) && screenItemStatusForm.getItemId().equals(screenItemStatus.getItemId()) && screenItemStatusForm.getScreenFormId().equals(screenForm.getScreenFormId())) {
							screenItemStatus.setScreenFormId(screenItemStatusForm.getScreenFormId());
							screenItemStatusesArea.add(screenItemStatus);
						}
						//Item
						if(screenItemStatusForm.getItemType().equals(ITEM) && screenItemStatusForm.getItemId().equals(screenItemStatus.getItemId()) && screenItemStatusForm.getScreenFormId().equals(screenForm.getScreenFormId())) {
							screenItemStatus.setScreenFormId(screenItemStatusForm.getScreenFormId());
							screenItemStatusesItem.add(screenItemStatus);
						}
					}
				}
			}
		}
		
		for(ScreenItemStatusForm screenItemStatusForm : listOfScreenItemStatusForm) {
			List<ScreenItemStatus> listScreenItemStatusArea = new ArrayList<ScreenItemStatus>();
			if(screenItemStatusForm.getItemType().equals(AREA)) {
				for(ScreenItemStatus screenItemStatusArea : screenItemStatusesArea) {
					if(screenItemStatusForm.getItemId().equals(screenItemStatusArea.getItemId()) && screenItemStatusArea.getScreenFormId().equals(screenItemStatusForm.getScreenFormId())) {
						listScreenItemStatusArea.add(screenItemStatusArea);
					}
				}
				screenItemStatusForm.setScreenItemStatuses(listScreenItemStatusArea);
			}
			List<ScreenItemStatus> listScreenItemStatusItem = new ArrayList<ScreenItemStatus>();
			if(screenItemStatusForm.getItemType().equals(ITEM)) {
				for(ScreenItemStatus screenItemStatusItem : screenItemStatusesItem) {
					if(screenItemStatusForm.getItemId().equals(screenItemStatusItem.getItemId()) && screenItemStatusItem.getScreenFormId().equals(screenItemStatusForm.getScreenFormId())) {
						listScreenItemStatusItem.add(screenItemStatusItem);
					}
				}
				screenItemStatusForm.setScreenItemStatuses(listScreenItemStatusItem);
			}
		}
		//get id blogic display 
		String outputBeanJSON = "";
		BusinessDesign bdDisplay = businessDesignRepository.findDisplayBDesignByScreenId(screenDesign);
		if(bdDisplay != null) {
			
			CommonModel com = new CommonModel();
			com.setProjectId(SessionUtils.getCurrentProjectId());
			com.setWorkingLanguageId(SessionUtils.getCurrentLanguageId());
			com.setWorkingProjectId(SessionUtils.getCurrentProjectId());
			BusinessDesign bd = businessDesignService.findBusinessLogicInformation(bdDisplay.getBusinessLogicId(), false, com,true);
			
			List<OutputBean> lstOutputBean = bd.getLstOutputBean();
			if (lstOutputBean != null && lstOutputBean.size() > 0) {
				outputBeanJSON = DecisionTableUtils.convertToJsonFromObjList(lstOutputBean);
			}
			//Assign formula definition details one by one by formula_definition_id
			for(ScreenItemStatusForm screenItemStatusForm : listOfScreenItemStatusForm) {
				if(screenItemStatusForm.getFormulars() != null) {
					for(FormularForm formulaForm : screenItemStatusForm.getFormulars()) {
						//Set formula detail
						List<FormulaDetail> lstFDetail = new ArrayList<FormulaDetail>();
						for(FormulaDetail formulaDetail : lstFormulaDetailConvertJSON) {
							if(formulaDetail.getFormulaDefinitionId().equals(formulaForm.getFormulaDefinitionId())) {
								lstFDetail.add(formulaDetail);
							}
						}
						if(lstFDetail != null && lstFDetail.size() > 0) {
							String convertFormulaDetails = DecisionTableUtils.convertToJsonFromObjList(lstFDetail);
							formulaForm.setFormularDefinitionDetails(convertFormulaDetails);
						}
					}
				}
				if(screenItemStatusForm.getItemType().equals(FORM)) {
					//Set outputBeanJSON
					screenItemStatusForm.setJsonOutputBean(outputBeanJSON);
				}
			}
		}
		List<ScreenItemStatusForm> screenItemStatusForms = new ArrayList<ScreenItemStatusForm>(); 
		for(ScreenItemStatusForm screenItemStatusForm: listOfScreenItemStatusForm){
			if(DataTypeUtils.equals(screenItemStatusForm.getItemType(),1)){
				screenItemStatusForms.add(screenItemStatusForm);
				screenItemStatusForm.setScreenItemStatusForms(new ArrayList<ScreenItemStatusForm>());
				for(ScreenItemStatusForm screenItemStatusForm2: listOfScreenItemStatusForm){
					if(DataTypeUtils.equals(screenItemStatusForm2.getScreenFormId(),screenItemStatusForm.getScreenFormId())
							&& !DataTypeUtils.equals(screenItemStatusForm2.getItemType(),1)){
						screenItemStatusForm.getScreenItemStatusForms().add(screenItemStatusForm2);
						
					}
								
				}
			}
		}
		screenDesignStatusForm.setScreenId(screenId);
		screenDesignStatusForm.setListOfScreenItemStatusForm(screenItemStatusForms);
		model.addAttribute("screenDesign", screenDesign);
		model.addAttribute("screenDesignStatusForm", screenDesignStatusForm);
				
		return "/screendesign/screenStatus";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="screenStatus" , method = RequestMethod.POST)
	public String processScreenStatus(@ModelAttribute ScreenDesignStatusForm screenDesignStatusForm, Model model, RedirectAttributes redirectAttr) {
		/*ScreenForm[] arrScreenForm = screenDesignRepository.getScreenFormByScreenId(screenDesignStatusForm.getScreenId());*/
		Long projectId = SessionUtils.getCurrentProjectId();
		List<FormulaDetail> formulaDetails = new ArrayList<FormulaDetail>();
		List<FormulaDefinition> formulaDefinitions = new ArrayList<FormulaDefinition>();
		/*Map<Long,List<ScreenItemStatusForm>> map = new HashMap<Long, List<ScreenItemStatusForm>>();*/
		for(ScreenItemStatusForm screenItemStatusForm :screenDesignStatusForm.getListOfScreenItemStatusForm()){
			int length = screenItemStatusForm.getFormulars()==null?0:screenItemStatusForm.getFormulars().size();
			for (int i = 0; i < length; i++) {
				List<FormularForm> fform = screenItemStatusForm.getFormulars();
				FormularForm formularForm = fform.get(i);
				FormulaDefinition formulaDefinition = new FormulaDefinition();
				formulaDefinition.setProjectId(projectId);
				if (i == 0) {
					formulaDefinition.setFormulaType(0);
				} else {
					formulaDefinition.setFormulaType(1);
				}
				formulaDefinition.setFormulaDefinitionContent(formularForm.getFormulaDefinitionContent());
				formulaDefinition.setFormulaName(formularForm.getFormulaName());
				formulaDefinition.setFormulaType(PROCESS);
				formulaDefinition.setScreenFormId(formularForm.getScreenFormId());
				//set List<FormulaDetails>
				if(formularForm.getFormularDefinitionDetails() != null && !formularForm.getFormularDefinitionDetails().isEmpty()) {
					formulaDetails =  (List<FormulaDetail>)DecisionTableUtils.toObjectLst(formularForm.getFormularDefinitionDetails(), FormulaDetail.class);
					formulaDefinition.setFormulaDefinitionDetails(formulaDetails);
				}
				
				formulaDefinition.setScreenItemStatuses(new ArrayList<ScreenItemStatus>());
				List<ScreenItemStatusForm> statusForm = screenItemStatusForm.getScreenItemStatusForms();
				for(ScreenItemStatusForm form : statusForm){
					//set List<ScreenItemStatus>
					if(form.getScreenItemStatuses() != null) {
						ScreenItemStatus screenItemStatus = form.getScreenItemStatuses().get(i);
						if(screenItemStatus.getScreenFormId() != null){
							if(screenItemStatus.getScreenFormId().equals(form.getScreenFormId())) {
								screenItemStatus.setItemId(form.getItemId());
								screenItemStatus.setItemType(form.getItemType());
								formulaDefinition.getScreenItemStatuses().add(screenItemStatus);
							}
						}
					} else {
						ScreenItemStatus screenItemStatus = new ScreenItemStatus();
						screenItemStatus.setItemId(form.getItemId());
						screenItemStatus.setItemType(form.getItemType());
						formulaDefinition.getScreenItemStatuses().add(screenItemStatus);
					}
				}
				formulaDefinitions.add(formulaDefinition);
			}
		}
		screenItemStatusService.modifyScreenItemStatus(formulaDefinitions, projectId,screenDesignStatusForm.getScreenId());
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0435)));
		return SEARCH_REDIRECT_PATH;
	}
}

