package org.terasoluna.qp.app.screendesign.ajax;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.domain.model.AutocompleteDesign;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CodeListDetail;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.ScreenArea;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.model.ScreenItem;
import org.terasoluna.qp.domain.model.ScreenItemCodelist;
import org.terasoluna.qp.domain.model.ScreenParameter;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.model.SqlDesignInput;
import org.terasoluna.qp.domain.model.SqlDesignResult;
import org.terasoluna.qp.domain.repository.businessdesign.BusinessDesignRepository;
import org.terasoluna.qp.domain.repository.screenaction.ScreenActionRepository;
import org.terasoluna.qp.domain.repository.screenarea.ScreenAreaRepository;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignOutputBeanForSetting;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignRepository;
import org.terasoluna.qp.domain.repository.screenitem.ScreenItemRepository;
import org.terasoluna.qp.domain.repository.screenitemsequence.ScreenItemSequenceRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignInputRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignRepository;
import org.terasoluna.qp.domain.service.autocomplete.AutocompleteDesignService;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignService;
import org.terasoluna.qp.domain.service.common.AutocompleteInput;
import org.terasoluna.qp.domain.service.languagedesign.LanguageDesignService;
import org.terasoluna.qp.domain.service.messagedesign.MessageDesignService;
import org.terasoluna.qp.domain.service.module.ModuleService;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignConst;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignOutput;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignService;
import org.terasoluna.qp.domain.service.sqldesign.SqlDesignService;
import org.terasoluna.qp.domain.service.tabledesign.TableDesignDetailService;

@Controller
@RequestMapping(value="screendesign")
public class ScreenDesignAjaxController {
	private static final Logger logger = LoggerFactory.getLogger(ScreenDesignAjaxController.class);
	private final Integer ITEM_TYPE_NORMAL = 1;
	private final String MESSAGE_TYPE_SCREEN = "sc";
	
	
	@Inject
	ScreenDesignService screenDesignService;
	
	@Inject
	ScreenActionRepository screenActionRepository;
	
	@Inject
	ScreenDesignRepository screeDesignRepository; 
	
	@Inject
	ScreenAreaRepository screenAreaRepository;
	
	@Inject
	ScreenItemRepository screenItemRepository;
	
	@Inject
	ScreenItemSequenceRepository screenItemSequenceRepository;
	
	@Inject
	MessageDesignService messageDesignService;
	
	@Inject
	LanguageDesignService languageDesignService;
	
	@Inject
	BusinessDesignService businessDesignService;
	
	@Inject
	AutocompleteDesignService autocompleteDesignService;
	
	@Inject
	TableDesignDetailService tableDesignDetailService;
	
	@Inject
	SqlDesignService sqlDesignService;
	
	@Inject
	SqlDesignInputRepository sqlDesignInputRepository;
	
	@Inject
	ModuleService moduleService;
	
	@Inject
	BusinessDesignRepository businessDesignRepository;
	
	@Inject
	SqlDesignRepository sqlDesignRepository;
	
	@RequestMapping(value="test", method=RequestMethod.GET)
	@ResponseBody
	public List<AutocompleteInput> test(AutocompleteInput input ) {
		List<AutocompleteInput> result = new ArrayList<AutocompleteInput>();
		AutocompleteInput o = new AutocompleteInput();
		o.setArg01("arg01");
		o.setArg02("arg02");
		o.setArg03("arg03");
		o.setArg03("arg04");
		o.setArg04("arg05");
		o.setArg05("arg06");
		return result;
	}
	
	@RequestMapping(value="getColumns", method=RequestMethod.GET)
	@ResponseBody
	public List<ScreenDesignOutput> getJson(@RequestParam("tableId") long tableId, @RequestParam("columnId") Long columnId) {
		if (columnId < 0) {
			columnId = null;
		}
		List<ScreenDesignOutput> listOfColumns = screenDesignService.getColumnsByTableId(tableId, columnId);
		for (int i = 0; i < listOfColumns.size(); i++) {
			listOfColumns.get(i).setLabelText(HtmlUtils.htmlEscape(listOfColumns.get(i).getLabelText()));
			listOfColumns.get(i).setColumnname(HtmlUtils.htmlEscape(listOfColumns.get(i).getColumnname()));
		}
		return listOfColumns;
	}
	
	@RequestMapping(value="getOptionByAutocompleteId", method=RequestMethod.GET)
	@ResponseBody
	public String getOptionByAutocompleteId(@RequestParam("autocompleteId") Long autocompleteId) {
		if (autocompleteId == null) return "";
		
		String resultEnd = "";
		
		AutocompleteDesign autocomplete = autocompleteDesignService.findOneById(autocompleteId);
		if(autocomplete != null && autocomplete.getSqlDesign() != null) {
			String result = "";
			SqlDesign sqlDesign = sqlDesignService.findOneById(autocomplete.getSqlDesign().getSqlDesignId());
			String displays = autocomplete.getDisplayColumnFlag();
			String displayArray[] = displays.split(",");
			
			int length = displayArray.length - 20;
			
			for (int i = 0; i < length; i++) {
				displayArray[i] = null;
			}
			
			String submit = autocomplete.getSubmitColumn();
			
			List<Integer> displayIds = new ArrayList<Integer>();
			Integer submitId = null;
			String submitName = "";
			switch(submit) {
			case "1":
				if (sqlDesign.getDesignType() == 2)
					submitId = Integer.parseInt(autocomplete.getOutput01());			
				
				submitName = autocomplete.getOutput01();
				break;
			case "2":
				if (sqlDesign.getDesignType() == 2)
					submitId = Integer.parseInt(autocomplete.getOutput02());
				submitName = autocomplete.getOutput02();
				break;
			case "3":
				if (sqlDesign.getDesignType() == 2)
					submitId = Integer.parseInt(autocomplete.getOutput03());
				submitName = autocomplete.getOutput03();
				break;
			case "4":
				if (sqlDesign.getDesignType() == 2)
				submitId = Integer.parseInt(autocomplete.getOutput04());
				submitName = autocomplete.getOutput04();
				break;
			case "5":
				if (sqlDesign.getDesignType() == 2)
				submitId = Integer.parseInt(autocomplete.getOutput05());
				submitName = autocomplete.getOutput05();
				break;
			case "6":
				if (sqlDesign.getDesignType() == 2)
				submitId = Integer.parseInt(autocomplete.getOutput06());
				submitName = autocomplete.getOutput06();
				break;
			case "7":
				if (sqlDesign.getDesignType() == 2)
				submitId = Integer.parseInt(autocomplete.getOutput07());
				submitName = autocomplete.getOutput07();
				break;
			case "8":
				if (sqlDesign.getDesignType() == 2)
				submitId = Integer.parseInt(autocomplete.getOutput08());
				submitName = autocomplete.getOutput08();
				break;
			case "9":
				if (sqlDesign.getDesignType() == 2)
				submitId = Integer.parseInt(autocomplete.getOutput09());
				submitName = autocomplete.getOutput09();
				break;
			case "10":
				if (sqlDesign.getDesignType() == 2)
				submitId = Integer.parseInt(autocomplete.getOutput10());
				submitName = autocomplete.getOutput10();
				break;
			case "11":
				if (sqlDesign.getDesignType() == 2)
				submitId = Integer.parseInt(autocomplete.getOutput11());
				submitName = autocomplete.getOutput11();
				break;
			case "12":
				if (sqlDesign.getDesignType() == 2)
				submitId = Integer.parseInt(autocomplete.getOutput12());
				submitName = autocomplete.getOutput12();
				break;
			case "13":
				if (sqlDesign.getDesignType() == 2)
				submitId = Integer.parseInt(autocomplete.getOutput13());
				submitName = autocomplete.getOutput13();
				break;
			case "14":
				if (sqlDesign.getDesignType() == 2)
				submitId = Integer.parseInt(autocomplete.getOutput14());
				submitName = autocomplete.getOutput14();
				break;
			case "15":
				if (sqlDesign.getDesignType() == 2)
				submitId = Integer.parseInt(autocomplete.getOutput15());
				submitName = autocomplete.getOutput15();
				break;
			case "16":
				if (sqlDesign.getDesignType() == 2)
				submitId = Integer.parseInt(autocomplete.getOutput16());
				submitName = autocomplete.getOutput16();
				break;
			case "17":
				if (sqlDesign.getDesignType() == 2)
				submitId = Integer.parseInt(autocomplete.getOutput17());
				submitName = autocomplete.getOutput17();
				break;
			case "18":
				if (sqlDesign.getDesignType() == 2)
				submitId = Integer.parseInt(autocomplete.getOutput18());
				submitName = autocomplete.getOutput18();
				break;
			case "19":
				if (sqlDesign.getDesignType() == 2)
				submitId = Integer.parseInt(autocomplete.getOutput19());
				submitName = autocomplete.getOutput19();
				break;
			case "20":
				if (sqlDesign.getDesignType() == 2)
				submitId = Integer.parseInt(autocomplete.getOutput20());
				submitName = autocomplete.getOutput20();
				break;
			}
			
			List<String> displayColumns = new ArrayList<String>();
			int j = 1;
			for (int i = 0; i < displayArray.length; i++) {
				if (displayArray[i] == null) continue;
				switch(j) {
				case 1:
					if ("1".equals(displayArray[i])) {
						if (sqlDesign.getDesignType() == 2)
						displayIds.add(Integer.parseInt(autocomplete.getOutput01()));
						displayColumns.add(autocomplete.getOutput01());
					}
					break;
				case 2:
					if ("1".equals(displayArray[i])) {
						if (sqlDesign.getDesignType() == 2)
						displayIds.add(Integer.parseInt(autocomplete.getOutput02()));
						displayColumns.add(autocomplete.getOutput02());
					}
					break;
				case 3:
					if ("1".equals(displayArray[i])) {
						if (sqlDesign.getDesignType() == 2)
						displayIds.add(Integer.parseInt(autocomplete.getOutput03()));
						displayColumns.add(autocomplete.getOutput03());
					}
					break;
				case 4:
					if ("1".equals(displayArray[i])) {
						if (sqlDesign.getDesignType() == 2)
						displayIds.add(Integer.parseInt(autocomplete.getOutput04()));
						displayColumns.add(autocomplete.getOutput04());
					}
					break;
				case 5:
					if ("1".equals(displayArray[i])) {
						if (sqlDesign.getDesignType() == 2)
						displayIds.add(Integer.parseInt(autocomplete.getOutput05()));
						displayColumns.add(autocomplete.getOutput05());
					}
					break;
				case 6:
					if ("1".equals(displayArray[i])) {
						if (sqlDesign.getDesignType() == 2)
						displayIds.add(Integer.parseInt(autocomplete.getOutput06()));
						displayColumns.add(autocomplete.getOutput06());
					}
					break;
				case 7:
					if ("1".equals(displayArray[i])) {
						if (sqlDesign.getDesignType() == 2)
						displayIds.add(Integer.parseInt(autocomplete.getOutput07()));
						displayColumns.add(autocomplete.getOutput07());
					}
					break;
				case 8:
					if ("1".equals(displayArray[i])) {
						if (sqlDesign.getDesignType() == 2)
						displayIds.add(Integer.parseInt(autocomplete.getOutput08()));
						displayColumns.add(autocomplete.getOutput08());
					}
					break;
				case 9:
					if ("1".equals(displayArray[i])) {
						if (sqlDesign.getDesignType() == 2)
						displayIds.add(Integer.parseInt(autocomplete.getOutput09()));
						displayColumns.add(autocomplete.getOutput09());
					}
					break;
				case 10:
					if ("1".equals(displayArray[i])) {
						if (sqlDesign.getDesignType() == 2)
						displayIds.add(Integer.parseInt(autocomplete.getOutput10()));
						displayColumns.add(autocomplete.getOutput10());
					}
					break;
				case 11:
					if ("1".equals(displayArray[i])) {
						if (sqlDesign.getDesignType() == 2)
						displayIds.add(Integer.parseInt(autocomplete.getOutput11()));
						displayColumns.add(autocomplete.getOutput11());
					}
					break;
				case 12:
					if ("1".equals(displayArray[i])) {
						if (sqlDesign.getDesignType() == 2)
						displayIds.add(Integer.parseInt(autocomplete.getOutput12()));
						displayColumns.add(autocomplete.getOutput12());
					}
					break;
				case 13:
					if ("1".equals(displayArray[i])) {
						if (sqlDesign.getDesignType() == 2)
						displayIds.add(Integer.parseInt(autocomplete.getOutput13()));
						displayColumns.add(autocomplete.getOutput13());
					}
					break;
				case 14:
					if ("1".equals(displayArray[i])) {
						if (sqlDesign.getDesignType() == 2)
						displayIds.add(Integer.parseInt(autocomplete.getOutput14()));
						displayColumns.add(autocomplete.getOutput14());
					}
					break;
				case 15:
					if ("1".equals(displayArray[i])) {
						if (sqlDesign.getDesignType() == 2)
						displayIds.add(Integer.parseInt(autocomplete.getOutput15()));
						displayColumns.add(autocomplete.getOutput15());
					}
					break;
				case 16:
					if ("1".equals(displayArray[i])) {
						if (sqlDesign.getDesignType() == 2)
						displayIds.add(Integer.parseInt(autocomplete.getOutput16()));
						displayColumns.add(autocomplete.getOutput16());
					}
					break;
				case 17:
					if ("1".equals(displayArray[i])) {
						if (sqlDesign.getDesignType() == 2)
						displayIds.add(Integer.parseInt(autocomplete.getOutput17()));
						displayColumns.add(autocomplete.getOutput17());
					}
					break;
				case 18:
					if ("1".equals(displayArray[i])) {
						if (sqlDesign.getDesignType() == 2)
						displayIds.add(Integer.parseInt(autocomplete.getOutput18()));
						displayColumns.add(autocomplete.getOutput18());
					}
					break;
				case 19:
					if ("1".equals(displayArray[i])) {
						if (sqlDesign.getDesignType() == 2)
						displayIds.add(Integer.parseInt(autocomplete.getOutput19()));
						displayColumns.add(autocomplete.getOutput19());
					}
					break;
				case 20:
					if ("1".equals(displayArray[i])) {
						if (sqlDesign.getDesignType() == 2)
						displayIds.add(Integer.parseInt(autocomplete.getOutput20()));
						displayColumns.add(autocomplete.getOutput20());
					}
					break;
				}
				j++;
			}
			
			
			String optionValue = "";
			String optionLabel = "";

			if (sqlDesign.getDesignType() == 2) {
				
				
				SqlDesignResult[] arr = sqlDesignService.findAllBySqlDesignId(sqlDesign.getSqlDesignId());
				
				for (int i = 0; i < displayIds.size(); i++) {
					 for (SqlDesignResult item: arr) {
						 if (displayIds.get(i).equals(item.getItemSeqNo())) {
							 optionLabel += item.getColumnName();
							 
							 if (i < displayIds.size() - 1) {
								 optionLabel += "-";
							 }
							 
							 break;
						 }
					 }
					
				}
				
				for (SqlDesignResult item: arr) {
					 if (submitId.equals(item.getItemSeqNo())) {
						 optionValue = item.getColumnName();
						 break;
					 }
				 }
				
				result = optionLabel + "," + optionValue + "," + autocomplete.getMatchingType();
				
			} else if (sqlDesign.getDesignType() == 3){
				
				for (int i = 0; i < displayColumns.size(); i++) {
					optionLabel += displayColumns.get(i).substring(displayColumns.get(i).indexOf(".") + 1);
					
					if (i < displayColumns.size() - 1) {
						optionLabel += "-";
					}
				}
				
				submitName = submitName.substring(submitName.indexOf(".") + 1);
				result = optionLabel + "," + submitName + "," + autocomplete.getMatchingType();
			}
			
			SqlDesignInput[] inputs = sqlDesignInputRepository.findAllBySqlDesignId(sqlDesign.getSqlDesignId());
			String input = "";
			for (int i = 0; i < inputs.length; i++) {
				input += inputs[i].getSqlDesignInputName() + "|" + inputs[i].getSqlDesignInputCode() + "|" + inputs[i].getDataType() + "|" + inputs[i].getSqlDesignInputId();
				
				if (i < inputs.length - 1) {
					input += "-";
				}
			}
			resultEnd = result + "," + input;
		} else {
			resultEnd = "";
		}
		return resultEnd;
	}

    @RequestMapping(value = "getSystemCodeListDetailById", method = RequestMethod.GET)
	@ResponseBody
	public List<CodeListDetail> processGetSystemCodeListDetail(@RequestParam("data") String parameters, Model model) {
    	List<CodeListDetail> lstCodelist = new ArrayList<>();
    	if (parameters.isEmpty()) {
			logger.debug("Parammeter empty");
			return lstCodelist;
		}else{
			Long codelistId = DataTypeUtils.convertTo(parameters, Long.class);
			lstCodelist = screenDesignService.getSystemCodeListDetailById(codelistId);
		}
    	return lstCodelist;
    }
    
    @RequestMapping(value = "getTableCodeListDetailById", method = RequestMethod.GET)
	@ResponseBody
	public List<CodeListDetail> processGetTableCodeListDetail(@RequestParam("data") String parameters, Model model) {
    	List<CodeListDetail> lstCodelist = new ArrayList<>();
    	if (parameters.isEmpty()) {
    		logger.debug("Parammeter empty");
			return lstCodelist;
		}else{
			Long columnId = DataTypeUtils.convertTo(parameters, Long.class);
			lstCodelist = screenDesignService.getTableCodeListDetailById(columnId);
		}
    	return lstCodelist;
    }
    	
	@RequestMapping(value = "getScreenParams", method = RequestMethod.GET)
	@ResponseBody
	public String getScreenParameters(@RequestParam("screenId") Long screenId){
		String result = screenDesignService.getScreenParams(screenId);
		return result;
	}
    
	@RequestMapping(value = "getBusinessDesign", method = RequestMethod.GET)
	@ResponseBody
	public BusinessDesign getBusinessDesign(@RequestParam("businesslogicid") Long businessLogicId){
		try {
			CommonModel com = new CommonModel();
			com.setProjectId(SessionUtils.getCurrentProjectId());
			com.setWorkingLanguageId(SessionUtils.getCurrentLanguageId());
			com.setWorkingProjectId(SessionUtils.getCurrentProjectId());
			BusinessDesign businessDesign = businessDesignService.findBusinessLogicInformation(businessLogicId, false,com,true);
			return businessDesign;
		} catch(Exception ex) {
			//TODO: null not check
			return null;
		}
		
		
	}
	
	@RequestMapping(value = "checkTransition", method = RequestMethod.GET)
	@ResponseBody
	public String checkTransition(@RequestParam("sourceId") Long sourceId, @RequestParam("targetId") Long targetId){
		String status = "true";
		String message = "none";
		
		ScreenDesign source = screenDesignService.findById(sourceId, SessionUtils.getCurrentLanguageId(), SessionUtils.getCurrentProjectId());
		ScreenDesign target = screenDesignService.findById(targetId, SessionUtils.getCurrentLanguageId(), SessionUtils.getCurrentProjectId());
		
		String searchScreen = MessageUtils.getMessage("sc.screendesign.0263");
		String registerScreen = MessageUtils.getMessage("sc.screendesign.0260");
		String viewScreen = MessageUtils.getMessage("sc.screendesign.0030");
		String modifyScreen = MessageUtils.getMessage("sc.screendesign.0029");
		
		Map<Integer, String> mess = new Hashtable<Integer, String>();
		mess.put(1, searchScreen);
		mess.put(2, registerScreen);
		mess.put(3, viewScreen);
		mess.put(4, modifyScreen);
		
		/*if (source.getDesignMode() != null && target.getDesignMode() != null && source.getDesignMode().equals(target.getDesignMode())) {
			
			String mode = "";
			if ("1".equals(source.getDesignMode())) {
				mode = MessageUtils.getMessage("sc.screendesign.0306");
			} else if ("2".equals(source.getDesignMode())) {
				mode = MessageUtils.getMessage("sc.screendesign.0316");
			}
			
			message = MessageUtils.getMessage("sc.screendesign.0325") + mode;
			String result = "{\"status\": "+status+", \"message\": \""+message+"\"}";
			return result;
		}*/
		
		if (source.getDesignMode() != null && target.getDesignMode() != null && target.getDesignMode().equals(ScreenDesignConst.DesignMode.DESIGN)) {
			ScreenParameter[] screenParameters = screeDesignRepository.getScreenParameterByScreenId(target.getScreenId());
			
			if (screenParameters != null && screenParameters.length > 0) {
				status = "false";
				message = MessageUtils.getMessage("sc.screendesign.0314");
			}
			//String result = "{\"status\": "+status+", \"message\": \""+message+"\"}";
			//return result;
		}
		
		/*if (source.getScreenPatternType().equals(1) && target.getScreenPatternType().equals(4)) {
			status = "false";
			message = MessageUtils.getMessage("sc.screendesign.0315");
			String result = "{\"status\": "+status+", \"message\": \""+message+"\"}";
			return result;
		}
		
		switch(source.getScreenPatternType()) {
		case 1:
			status = "true";
			break;
		case 2:
			if (!target.getScreenPatternType().equals(1)) {
				status = "false";
				message = MessageUtils.getMessage("sc.screendesign.0276", mess.get(source.getScreenPatternType()), mess.get(target.getScreenPatternType()));
			}
			break;
		case 3:
			if (target.getScreenPatternType().equals(2) || target.getScreenPatternType().equals(3)) {
				status = "false";
				message = MessageUtils.getMessage("sc.screendesign.0276", mess.get(source.getScreenPatternType()), mess.get(target.getScreenPatternType()));
			}
			break;
		case 4:
			if (!target.getScreenPatternType().equals(1)) {
				status = "false";
				message = MessageUtils.getMessage("sc.screendesign.0276", mess.get(source.getScreenPatternType()), mess.get(target.getScreenPatternType()));
			}
			break;
		}*/
		

		String result = "{\"status\": "+status+", \"message\": \""+message+"\"}";
		return result;
	}
	
	@RequestMapping(value = "getModuleByScreenId", method = RequestMethod.GET)
	@ResponseBody
	public Module getModuleByScreenId(@RequestParam("screenId") Long screenId){
		
		ScreenDesign screenDesign = screenDesignService.findScreenById(screenId, SessionUtils.getCurrentLanguageId(), SessionUtils.getCurrentProjectId());
		if (screenDesign == null) return new Module();
		Module module = moduleService.findModuleById(screenDesign.getModuleId());
		
		return module;
	}
	
	@RequestMapping(value = "getModuleByBlogicId", method = RequestMethod.GET)
	@ResponseBody
	public Module getModuleByBlogicId(@RequestParam("blogicId") Long blogicId){
		
		BusinessDesign businessDesign = businessDesignRepository.findBusinessLogicInformation(blogicId, SessionUtils.getCurrentLanguageId(), SessionUtils.getCurrentProjectId());
		if (businessDesign == null) return new Module();
		Module module = moduleService.findModuleById(businessDesign.getModuleId());
		
		return module;
	}
	
	@RequestMapping(value = "getOutputBeanForSetting", method = RequestMethod.GET)
	@ResponseBody
	public List<ScreenDesignOutputBeanForSetting> getOutputBeanForSetting(@RequestParam("screenId") Long screenId){
		List<ScreenDesignOutputBeanForSetting> outputBean = screeDesignRepository.getOutputBeanForSetting(screenId, SessionUtils.getCurrentLanguageId());
		if (outputBean == null) return new ArrayList<ScreenDesignOutputBeanForSetting>();
		
		return outputBean;
	}
	
	@RequestMapping(value = "getScreenItemForSetting", method = RequestMethod.GET)
	@ResponseBody
	public List<ScreenDesignOutputBeanForSetting> getScreenItemForSetting(@RequestParam("screenId") Long screenId){
		List<ScreenDesignOutputBeanForSetting> outputBean = screeDesignRepository.getScreenItemForSetting(screenId, SessionUtils.getCurrentLanguageId());
		if (outputBean == null) return new ArrayList<ScreenDesignOutputBeanForSetting>();
		
		return outputBean;
	}
	
	@RequestMapping(value = "getBlogicGet", method = RequestMethod.GET)
	@ResponseBody
	public String getBlogicGet(@RequestParam("screenId") Long screenId){
		String result = "";
		ScreenDesign screenDesign = new ScreenDesign();
		screenDesign.setScreenId(screenId);
		BusinessDesign businessDesign = businessDesignRepository.findBDesignGetByScreenId(screenDesign);
		
		if (businessDesign != null) {
			result = businessDesign.getBusinessLogicId() + "||" + businessDesign.getBusinessLogicName();
		}
		return result;
	}
	
	@RequestMapping(value = "getBlogicPost", method = RequestMethod.GET)
	@ResponseBody
	public String getBlogicPost(@RequestParam("screenId") Long screenId){
		String result = "";
		ScreenDesign screenDesign = new ScreenDesign();
		screenDesign.setScreenId(screenId);
		BusinessDesign businessDesign = businessDesignRepository.findBDesignPostByScreenId(screenDesign);
		
		if (businessDesign != null) {
			result = businessDesign.getBusinessLogicId() + "||" + businessDesign.getBusinessLogicName();
		}
		return result;
	}
	
	@RequestMapping(value = "getCustomContent", method = RequestMethod.GET)
	@ResponseBody
	public String getCustomContent(@RequestParam("screenItemId") Long screenItemId) {
		String customContent = "";
		ScreenItem screenItem = screenItemRepository.getScreenItemById(screenItemId);
		if(screenItem != null && StringUtils.isNotEmpty(screenItem.getCustomItemContent())) {
			customContent = screenItem.getCustomItemContent();
		}
		return customContent;
	}
	
	@RequestMapping(value = "getCustomSectionContent", method = RequestMethod.GET)
	@ResponseBody
	public String getCustomSectionContent(@RequestParam("screenAreaId") Long screenAreaId) {
		String customSectionContent = "";
		ScreenArea screenArea = screenAreaRepository.getScreenAreaByAreaId(screenAreaId);
		if(screenArea != null && StringUtils.isNotEmpty(screenArea.getCustomSectionContent())) {
			customSectionContent = screenArea.getCustomSectionContent();
		}
		return customSectionContent;
	}
	
	@RequestMapping(value = "getItemCodelist", method = RequestMethod.GET)
	@ResponseBody
	public List<ScreenItemCodelist> getItemCodelist(@RequestParam("screenItemId") Long screenItemId) {
		List<ScreenItemCodelist> lstItemCodeList = screenItemRepository.getItemCodelist(screenItemId);
		return lstItemCodeList;
	}
	@RequestMapping(value = "getItemCodelistByItemName", method = RequestMethod.GET)
	@ResponseBody
	public List<ScreenItemCodelist> getItemCodelistByItemName(@RequestParam("screenItemName") String screenItemName,@RequestParam("screenId") Long screenId) {
		List<ScreenItemCodelist> lstItemCodeList = screenItemRepository.getItemCodelistByItemName(screenItemName,screenId);
		return lstItemCodeList;
	}
	
	@RequestMapping(value = "getSqlDesign", method = RequestMethod.GET)
	@ResponseBody
	public Integer getSqlDesign(@RequestParam("sqlId") Long sqlId) {
		SqlDesign sqlDesign = sqlDesignRepository.findOneById(sqlId);
		return sqlDesign.getDesignType();
	}
}
