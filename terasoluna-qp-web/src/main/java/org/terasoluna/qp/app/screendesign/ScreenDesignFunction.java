package org.terasoluna.qp.app.screendesign;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.domain.model.AutocompleteDesign;
import org.terasoluna.qp.domain.model.InfoModuleForScreenDesign;
import org.terasoluna.qp.domain.model.MessageDesign;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.ScreenAction;
import org.terasoluna.qp.domain.model.ScreenActionParam;
import org.terasoluna.qp.domain.model.ScreenArea;
import org.terasoluna.qp.domain.model.ScreenAreaEvent;
import org.terasoluna.qp.domain.model.ScreenAreaSortMapping;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.model.ScreenForm;
import org.terasoluna.qp.domain.model.ScreenFormTabGroup;
import org.terasoluna.qp.domain.model.ScreenFormTabs;
import org.terasoluna.qp.domain.model.ScreenGroupItem;
import org.terasoluna.qp.domain.model.ScreenItem;
import org.terasoluna.qp.domain.model.ScreenItemAutocompleteInput;
import org.terasoluna.qp.domain.model.ScreenItemCodelist;
import org.terasoluna.qp.domain.model.ScreenItemEvent;
import org.terasoluna.qp.domain.model.ScreenItemEventMapping;
import org.terasoluna.qp.domain.model.ScreenItemValidation;
import org.terasoluna.qp.domain.model.ScreenParameter;
import org.terasoluna.qp.domain.model.ScreenTransition;
import org.terasoluna.qp.domain.model.ScreenTransitionBranch;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.model.SqlDesignResult;
import org.terasoluna.qp.domain.service.screendesign.Bean;
import org.terasoluna.qp.domain.service.screendesign.Event;
import org.terasoluna.qp.domain.service.screendesign.FormTab;
import org.terasoluna.qp.domain.service.screendesign.ScreenAreaEventOutput;
import org.terasoluna.qp.domain.service.screendesign.ScreenAreaSortOutput;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignConst;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignConst.ScreenItemBeanTypeConst;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignOutput;
import org.terasoluna.qp.domain.service.screendesign.Tab;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class ScreenDesignFunction {
	public static ScreenArea[] addItemToArea(ScreenArea[] areas, ScreenGroupItem[] groups, ScreenItem[] items) {
		int startElementIndex = 0;
		int totalElement = 0;
		int itemSeqNo = 0;
		if (areas != null) {
			for (int i = 0; i < areas.length; i++) {
				areas[i].setScreenAreaId(Long.parseLong(i + ""));
				// if area is not has element then return
				if (areas[i].getTotalElement() == null || areas[i].getTotalElement() == 0)
					continue;

				// get total element
				totalElement = areas[i].getTotalElement();

				areas[i].setItems(new ArrayList<ScreenItem>());

				for (int j = startElementIndex; j < items.length; j++) {
					if (totalElement == 0) {
						itemSeqNo = 0;
						break;
					}

					items[j].setItemSeqNo(itemSeqNo);
					items[j].setScreenAreaId(Long.parseLong(i + ""));
					areas[i].getItems().add(items[j]);

					startElementIndex++;
					totalElement--;
					itemSeqNo++;
				}

			}
		}
		return areas;
	}

	public static ScreenArea[] addGroupArea(ScreenArea[] areas, ScreenGroupItem[] groups) {

		int startGroupIndex = 0;
		int totalGroup = 0;
		int groupSeqNo = 0;
		if (areas != null) {
			for (int i = 0; i < areas.length; i++) {
				if (areas[i].getAreaType() == -1 || areas[i].getAreaType() == 2 || areas[i].getAreaType() == 3 || areas[i].getTotalGroup() == null || areas[i].getTotalGroup() == 0)
					continue;

				totalGroup = areas[i].getTotalGroup();

				areas[i].setGroups(new ArrayList<ScreenGroupItem>());

				if (groups != null) {
					for (int j = startGroupIndex; j < groups.length; j++) {
						if (totalGroup == 0) {
							groupSeqNo = 0;
							break;
						}

						groups[j].setGroupItemId(Long.parseLong(j + ""));
						groups[j].setGroupSeqNo(groupSeqNo);
						groups[j].setScreenAreaId(areas[i].getScreenAreaId());
						areas[i].getGroups().add(groups[j]);

						startGroupIndex++;
						totalGroup--;
						groupSeqNo++;
					}
				}

			}
		}

		return areas;
	}

	public static ScreenArea[] addItemToGroup(ScreenArea[] areas) {

		if (areas != null) {
			for (int i = 0; i < areas.length; i++) {
				if (areas[i].getAreaType() == -1 || areas[i].getAreaType() == 2 || areas[i].getAreaType() == 3 || areas[i].getTotalGroup() == null || areas[i].getTotalGroup() == 0)
					continue;

				int startElementIndex = 0;
				int totalElement = 0;

				if (areas[i].getGroups() != null) {
					for (int j = 0; j < areas[i].getGroups().size(); j++) {

						totalElement = areas[i].getGroups().get(j).getTotalElement();

						areas[i].getGroups().get(j).setItems(new ArrayList<ScreenItem>());

						for (int k = startElementIndex; k < areas[i].getItems().size(); k++) {
							if (totalElement == 0)
								break;

							areas[i].getGroups().get(j).getItems().add(areas[i].getItems().get(k));

							startElementIndex++;
							totalElement--;
						}
					}
				}
			}
		}
		return areas;
	}

	public static String getJsonScreenItem(ScreenItem screenItem) {

		// fix code
				ScreenDesignOutput screenDesignOutput = new ScreenDesignOutput();
				// screenDesignOutput.setActiontype();
				screenDesignOutput.setAutocompleteid(screenItem.getAutocompleteId() + "");
				screenDesignOutput.setCodelistcode(screenItem.getCodelistId() + "");
				screenDesignOutput.setColspan(screenItem.getColSpan() + "");
				screenDesignOutput.setDatatype(screenItem.getLogicalDataType() + "");
				screenDesignOutput.setPhysicaldatatype(screenItem.getPhysicalDataType() + "");
				// fix code 1
				screenDesignOutput.setGroupitemtype("1");
				screenDesignOutput.setOutputBeanId(screenItem.getOutputBeanId() + "");
				screenDesignOutput.setTabindex(screenItem.getTabIndex() + "");
				screenDesignOutput.setScreenItemId(screenItem.getScreenItemId() + "");
				screenDesignOutput.setStyle(screenItem.getStyle());
				screenDesignOutput.setHoverStyle(screenItem.getHoverStyle());
				screenDesignOutput.setIcon(screenItem.getIcon());
				screenDesignOutput.setShowLabel(screenItem.getShowLabel() + "");
				screenDesignOutput.setAllowAnyInput(screenItem.getAllowAnyInput() + "");
				screenDesignOutput.setEnablePassword(screenItem.getEnablePassword() + "");
				screenDesignOutput.setButtonStyle(screenItem.getButtonType() + "");
				screenDesignOutput.setDisplayFromToOutput(screenItem.getDisplayFromTo() + "");
				screenDesignOutput.setShowBlankItem(screenItem.getShowBlankItem() + "");
				
				if (screenItem.getScreenTransitionId() != null) {
					screenDesignOutput.setScreenTransition(screenItem.getScreenTransitionId() + "");
				}
				
				if (!StringUtils.isEmpty(screenItem.getScreenTransitionName())) {
					screenDesignOutput.setScreenTransitionText(screenItem.getScreenTransitionName());
				}
				
				screenDesignOutput.setScreenDesignIdCodeListId(screenItem.getScreenDesignIdCodeListId());
				screenDesignOutput.setScreenItemIdCodeListId(screenItem.getScreenItemIdCodeListId());
				
				screenDesignOutput.setScreenDesignTextCodeListId(screenItem.getScreenDesignTextCodeListId());
				screenDesignOutput.setScreenItemTextCodeListId(screenItem.getScreenItemTextCodeListId());
				
				screenDesignOutput.setCustomItemContent(screenItem.getCustomItemContent() + "");
				screenDesignOutput.setAreaCustomType(screenItem.getScreenArea().getAreaCustomType() + "");

				if (screenItem.getScreenArea() != null) {
					screenDesignOutput.setInputStyle(screenItem.getScreenArea().getInputStyle());
					screenDesignOutput.setHeaderStyle(screenItem.getScreenArea().getHeaderStyle());
				}

				if (!StringUtils.isEmpty(screenItem.getDefaultValue()))
					screenDesignOutput.setDefaultvalue(screenItem.getDefaultValue());

				if (screenItem.getDataSourceType() != null) {
					screenDesignOutput.setDatasourcetype(screenItem.getDataSourceType() + "");
					if (screenItem.getDataSourceType() == 1) {
						if (screenItem.getSqlDesign() != null) {
							if (DbDomainConst.LogicDataType.AUTOCOMPLETE.equals(screenItem.getLogicalDataType())) {
								if (screenItem.getAutocompleteDesign() != null) {
									screenDesignOutput.setSqldesignidtext(screenItem.getAutocompleteDesign().getAutocompleteName());
									screenDesignOutput.setSqldesignid(screenItem.getSqlDesign().getSqlDesignId() + "");
								}
							} else {
								screenDesignOutput.setSqldesignidtext(screenItem.getSqlDesign().getSqlDesignName());
								screenDesignOutput.setSqldesignid(screenItem.getSqlDesign().getSqlDesignId() + "");
							}
						}

						if (screenItem.getOptionLabel() != null) {
							screenDesignOutput.setOptionlabel(screenItem.getOptionLabel().getResultId() + "");
							screenDesignOutput.setOptionlabeltext(screenItem.getOptionLabel().getColumnName());
						}

						if (screenItem.getOptionValue() != null) {
							screenDesignOutput.setOptionvalue(screenItem.getOptionValue().getResultId() + "");
							screenDesignOutput.setOptionvaluetext(screenItem.getOptionValue().getColumnName());
						}
					}
				}

				if (screenItem.getLogicalDataType() != null && screenItem.getLogicalDataType() == 20) {
					screenDesignOutput.setColumnname(HtmlUtils.htmlEscape(screenItem.getItemCode()));
				} else {
					screenDesignOutput.setColumnname(HtmlUtils.htmlEscape(screenItem.getItemCode()));
				}

				if (StringUtils.isNotEmpty(screenItem.getItemWidthUnit())) {
					String widthUnit = screenItem.getItemWidthUnit();

					if (widthUnit.indexOf("px") != -1) {
						screenDesignOutput.setWidth(screenItem.getItemWidthUnit().replaceAll("px", ""));
						screenDesignOutput.setWidthunit("px");
					} else if (widthUnit.indexOf("%") != -1) {
						screenDesignOutput.setWidth(screenItem.getItemWidthUnit().replaceAll("%", ""));
						screenDesignOutput.setWidthunit("%");
					} else {
						screenDesignOutput.setWidth(screenItem.getItemWidthUnit());
					}
				} else if (screenItem.getLogicalDataType() != null && !screenItem.getLogicalDataType().equals(DbDomainConst.LogicDataType.BUTTON) && screenItem.getScreenArea() != null && screenItem.getScreenArea().getAreaType() != null && !screenItem.getScreenArea().getAreaType().equals(DbDomainConst.AreaType.SUBMIT_ACTION)) {
					screenDesignOutput.setWidth("100");
					screenDesignOutput.setWidthunit("%");
				}

				if (screenItem.getDomainTblMappingId() != null && screenItem.getDomainTblMappingItemId() != null) {
					screenDesignOutput.setTablecode(screenItem.getDomainTblMappingId() + "");
					screenDesignOutput.setTablecolumncode(screenItem.getDomainTblMappingItemId() + "");
				}

				// validation
				if (screenItem.getScreenItemValidation() != null) {
					screenDesignOutput.setMinvalue(screenItem.getScreenItemValidation().getMinVal());
					screenDesignOutput.setMaxvalue(screenItem.getScreenItemValidation().getMaxVal());

					// fix code
					if (screenItem.getScreenItemValidation().getMandatoryFlg() != null && screenItem.getScreenItemValidation().getMandatoryFlg().equals(1)) {
						screenDesignOutput.setMandatory("true");
					} else {
						screenDesignOutput.setMandatory("false");
					}
					screenDesignOutput.setMaxlength(screenItem.getScreenItemValidation().getMaxlength() + "");
					screenDesignOutput.setFormatcode(screenItem.getScreenItemValidation().getFmtCode());

				}

				// fixed code
				if (screenItem.getLogicalDataType() != null) {
					if (screenItem.getLogicalDataType() == 0 || screenItem.getLogicalDataType() == 5 || screenItem.getLogicalDataType() == 6 || screenItem.getLogicalDataType() == 7) {
						screenDesignOutput.setBaseType(screenItem.getPhysicalDataType() + "");
					}
				}

				if (screenItem.getCodelistType() != null) {
					screenDesignOutput.setLocalCodelist(screenItem.getCodelistType() + "");
					if (ScreenDesignConst.ScreenItemConst.CODELIST_TYPE_SYSTEM.equals(screenItem.getCodelistType())) {
						screenDesignOutput.setCodelistText(screenItem.getCodelistText());
						screenDesignOutput.setCodelistCode(screenItem.getCodelistId() + "");
					} else if (ScreenDesignConst.ScreenItemConst.CODELIST_TYPE_CUSTOM.equals(screenItem.getCodelistType())) {
						// fix code
						if (screenItem.getListScreenItemCodelists() != null && screenItem.getListScreenItemCodelists().size() > 0 && screenItem.getListScreenItemCodelists().get(0).getSupportOptionValFlg().equals(0)) {

							screenDesignOutput.setIsSupportOptionValue(ScreenDesignConst.ScreenItemConst.VALUE_TRUE);
						} else {
							screenDesignOutput.setIsSupportOptionValue(ScreenDesignConst.ScreenItemConst.VALUE_FALSE);
						}
					}

					// bind parameter
					if (screenItem.getListScreenItemCodelists() != null && screenItem.getListScreenItemCodelists().size() > 0) {
						String parameters = "";
						String msglabel = "";
						String msgvalue = "";
						for (int i = 0; i < screenItem.getListScreenItemCodelists().size(); i++) {
							String codeListName = "";
							String codeListValue = "";
							if(screenItem.getListScreenItemCodelists().get(i).getCodelistName() != null && screenItem.getListScreenItemCodelists().get(i).getCodelistName() != "null" ) {
								codeListName = screenItem.getListScreenItemCodelists().get(i).getCodelistName();
							}
							if(screenItem.getListScreenItemCodelists().get(i).getCodelistVal() != null && screenItem.getListScreenItemCodelists().get(i).getCodelistVal() != "null" ) {
								codeListValue = screenItem.getListScreenItemCodelists().get(i).getCodelistVal();
							}
							parameters += HtmlUtils.htmlEscape(codeListName + ScreenDesignConst.ITEM_SPLIT + HtmlUtils.htmlEscape(codeListValue + ScreenDesignConst.ROW_SPLIT));

							String label = "";
							if (screenItem.getListScreenItemCodelists() != null && screenItem.getListScreenItemCodelists().size() > 0 && screenItem.getListScreenItemCodelists().get(i) != null && screenItem.getListScreenItemCodelists().get(i).getCodelistName() != null && !screenItem.getListScreenItemCodelists().get(i).getCodelistName().isEmpty()) {
								label = HtmlUtils.htmlEscape(screenItem.getListScreenItemCodelists().get(i).getCodelistName());
							} else {
								label = HtmlUtils.htmlEscape(screenItem.getListScreenItemCodelists().get(i).getCodelistVal());
							}

							msglabel += label + ScreenDesignConst.ROW_SPLIT;
							msgvalue += HtmlUtils.htmlEscape(screenItem.getListScreenItemCodelists().get(i).getCodelistVal()) + ScreenDesignConst.ROW_SPLIT;
						}

						if (screenItem.getListScreenItemCodelists().size() > 0) {
							parameters = parameters.substring(0, parameters.length() - 1);
							msglabel = msglabel.substring(0, msglabel.length() - 1);
							msgvalue = msgvalue.substring(0, msgvalue.length() - 1);
						}
						screenDesignOutput.setParameters(parameters);
						screenDesignOutput.setMsglabel(msglabel);
						screenDesignOutput.setMsgvalue(msgvalue);
					}
				}
				if (screenItem.getAutocompleteDesign() != null) {
					screenDesignOutput.setDialogAutocompleteCode(screenItem.getAutocompleteDesign().getAutocompleteId() + "");
					screenDesignOutput.setDialogAutocompleteText(screenItem.getAutocompleteDesign().getAutocompleteCode());
				}

				if (screenItem.getMessageDesign() != null) {
					screenDesignOutput.setLabel(screenItem.getMessageDesign().getMessageCode());
					screenDesignOutput.setLabelText(HtmlUtils.htmlEscape(screenItem.getMessageDesign().getMessageString()));
					if (screenItem.getLogicalDataType() != null && screenItem.getLogicalDataType() == 11) {
						if (screenItem.getMessageDesign() != null && screenItem.getMessageDesign().getMessageString() != null && screenItem.getMessageDesign().getMessageString().isEmpty()) {
							screenDesignOutput.setLabelText(HtmlUtils.htmlEscape(MessageUtils.getMessage("sc.screendesign.0039")));
						}
					} else if (screenItem.getLogicalDataType() != null && screenItem.getLogicalDataType() == 20 && screenItem.getMessageDesign() != null && screenItem.getMessageDesign().getMessageString() != null && screenItem.getMessageDesign().getMessageString().equals(MessageUtils.getMessage("sc.screendesign.0174"))) {
						// check item is lable
						screenDesignOutput.setIsBlank(ScreenDesignConst.ScreenItemConst.VALUE_TRUE);
					}
					if(screenItem.getMessageDesign() != null && screenItem.getMessageDesign().getMessageLevel() != null) {
						screenDesignOutput.setMessageLevel(String.valueOf(screenItem.getMessageDesign().getMessageLevel()));
						if(screenItem.getMessageDesign().getMessageLevel().equals(DbDomainConst.MessageLevel.PROJECT)) {
							screenDesignOutput.setMessageLevelText(MessageUtils.getMessage("sc.tqp.0011"));
						} else if(screenItem.getMessageDesign().getMessageLevel().equals(DbDomainConst.MessageLevel.MODULE)) {
							screenDesignOutput.setMessageLevelText(MessageUtils.getMessage("sc.generatedocument.0006"));
						} else if(screenItem.getMessageDesign().getMessageLevel().equals(DbDomainConst.MessageLevel.SCREEN)) {
							screenDesignOutput.setMessageLevelText(MessageUtils.getMessage("sc.businesslogicdesign.0159"));
						} else if(screenItem.getMessageDesign().getMessageLevel().equals(DbDomainConst.MessageLevel.BUSINESS_LOGIC)) {
							screenDesignOutput.setMessageLevelText(MessageUtils.getMessage("cl.generation.0007"));
						} else if(screenItem.getMessageDesign().getMessageLevel().equals(DbDomainConst.MessageLevel.MENU_DESIGN)) {
							screenDesignOutput.setMessageLevelText(MessageUtils.getMessage("sc.menudesign.0008"));
						} else if(screenItem.getMessageDesign().getMessageLevel().equals(DbDomainConst.MessageLevel.DESIGN_INFORMATION)) {
							screenDesignOutput.setMessageLevelText(MessageUtils.getMessage("tqp.designinformation"));
						} else {
							screenDesignOutput.setMessageLevelText("");
						}
						
					}
					
				} else {
					if (screenItem.getLogicalDataType() != null && screenItem.getLogicalDataType() == 11) {
						screenDesignOutput.setLabelText(MessageUtils.getMessage("sc.screendesign.0039"));
					}
				}

				if (screenItem.getMessageConfirm() != null) {
					screenDesignOutput.setMessageConfirm(screenItem.getMessageConfirm().getMessageCode());
					screenDesignOutput.setMessageConfirmText(screenItem.getMessageConfirm().getMessageString());
					screenDesignOutput.setMessageConfirmCode(screenItem.getMessageConfirm().getMessageCode());
				}
				screenDesignOutput.setEnableConfirm(screenItem.getEnableConfirm() + "");

				screenDesignOutput.setRowspan(screenItem.getRowSpan() + "");

				// set screen action
				if (screenItem.getScreenAction() != null) {
					ScreenAction screenAction = screenItem.getScreenAction();
					if (screenAction.getToScreenId() != null) {
						screenDesignOutput.setNavigateTo(screenAction.getToScreenId().toString());
						screenDesignOutput.setToScreenCode(screenItem.getScreenAction().getToScreenCode());
					} else {
						screenDesignOutput.setNavigateTo("");
					}

					if (ScreenDesignConst.ScreenActionConst.ACTION_TYPE_SCREEN.equals(screenAction.getActionType())) {
						if (screenAction.getToScreenText() != null) {
							screenDesignOutput.setNavigateToText(screenAction.getToScreenText());
						} else {
							screenDesignOutput.setNavigateToText("");
						}

						if (screenAction.getNavigateToBlogicId() != null) {
							screenDesignOutput.setNavigateToBlogic(screenAction.getNavigateToBlogicId() + "");
						} else {
							screenDesignOutput.setNavigateToBlogic("");
						}

						if (screenAction.getNavigateToBlogicText() != null) {
							screenDesignOutput.setNavigateToBlogicText(screenAction.getNavigateToBlogicText() + "");
						} else {
							screenDesignOutput.setNavigateToBlogicText("");
						}

					} else if (ScreenDesignConst.ScreenActionConst.ACTION_TYPE_BLOGIC.equals(screenAction.getActionType())) {
						if (screenAction.getToBlogicText() != null) {
							screenDesignOutput.setNavigateToText(screenAction.getToBlogicText());
						} else {
							screenDesignOutput.setNavigateToText("");
						}
					}

					screenDesignOutput.setActiontype(screenAction.getActionType() + "");

					if (screenAction.getConnectionMsg() != null) {
						screenDesignOutput.setActionName(screenAction.getConnectionMsg());
					} else {
						screenDesignOutput.setActionName("");
					}
					if (ScreenDesignConst.ScreenActionConst.SUBMIT_METHOD_TYPE_POST == screenAction.getSubmitMethodType()) {
						screenDesignOutput.setIsSubmit(ScreenDesignConst.ScreenActionConst.IS_SUBMIT_TRUE);
					} else {
						screenDesignOutput.setIsSubmit(ScreenDesignConst.ScreenActionConst.IS_SUBMIT_FALSE);
					}

					if (screenAction.getListScreenParameters() != null) {
						// set screen param
						String parameters = "";
						for (int i = 0; i < screenAction.getListScreenParameters().size(); i++) {
							ScreenActionParam param = screenAction.getListScreenParameters().get(i);
							parameters += param.getActionParamCode() + ScreenDesignConst.ITEM_SPLIT + param.getScreenItemCode() + ScreenDesignConst.ITEM_SPLIT + param.getDataType() + ScreenDesignConst.ROW_SPLIT;
						}
						if (screenAction.getListScreenParameters().size() > 0) {
							parameters = parameters.substring(0, parameters.length() - 1);
						}
						screenDesignOutput.setParameters(parameters);
					}
				}

				// mapping event
				if (screenItem.getScreenItemEvents() != null && screenItem.getScreenItemEvents().size() > 0) {
					Event[] events = new Event[screenItem.getScreenItemEvents().size()];

					for (int j = 0; j < screenItem.getScreenItemEvents().size(); j++) {
						ScreenItemEvent event = screenItem.getScreenItemEvents().get(j);
						Event e = new Event();
						if (event.getBlogicId() != null)
							e.setBlogicid(event.getBlogicId().toString());
						if (event.getBusinessLogic() != null)
							e.setBlogicname(event.getBusinessLogic().getBusinessLogicName());
						if (event.getEffectArea() != null)
							e.setEffectarea(event.getEffectArea());
						if (event.getEffectAreaType() != null)
							e.setEffectareatype(event.getEffectAreaType().toString());
						if (event.getEventType() != null)
							e.setEventtype(event.getEventType().toString());

						List<Bean> inputs = new ArrayList<Bean>();
						List<Bean> outputs = new ArrayList<Bean>();

						if (event.getScreenItemEventMappings() != null && event.getScreenItemEventMappings().size() > 0) {

							for (ScreenItemEventMapping bean : event.getScreenItemEventMappings()) {

								if (bean.getBeanType().equals(1)) {
									Bean input = new Bean();
									if (bean.getBeanId() != null)
										input.setBeanid(bean.getBeanId().toString());
									input.setItemcode(bean.getItemCode());
									inputs.add(input);
								} else {
									Bean output = new Bean();
									if (bean.getBeanId() != null)
										output.setBeanid(bean.getBeanId().toString());
									output.setItemcode(bean.getItemCode());
									outputs.add(output);
								}
							}
							Bean[] arrInput = new Bean[0];
							arrInput = inputs.toArray(arrInput);

							Bean[] arrOutput = new Bean[0];
							arrOutput = outputs.toArray(arrOutput);

							e.setInputbeans(arrInput);
							e.setOutputbeans(arrOutput);
						}
						events[j] = e;
					}
					screenDesignOutput.setEvents(events);
				}

				if (screenItem.getLogicalDataType() != null && screenItem.getLogicalDataType().equals(DbDomainConst.LogicDataType.AUTOCOMPLETE) && screenItem.getScreenItemAutocompleteInputs() != null && screenItem.getScreenItemAutocompleteInputs().size() > 0) {

					// set screen param
					String parameters = "";
					for (int i = 0; i < screenItem.getScreenItemAutocompleteInputs().size(); i++) {
						ScreenItemAutocompleteInput param = screenItem.getScreenItemAutocompleteInputs().get(i);
						parameters += param.getInputId() + ScreenDesignConst.ITEM_SPLIT + param.getScreenItemCode() + ScreenDesignConst.ROW_SPLIT;
					}
					if (screenItem.getScreenItemAutocompleteInputs().size() > 0) {
						parameters = parameters.substring(0, parameters.length() - 1);
					}
					screenDesignOutput.setParameters(parameters);
				}

				JsonFactory json = new JsonFactory();
				ObjectMapper mapper = new ObjectMapper(json);
				TypeReference<ScreenDesignOutput> typeRef = new TypeReference<ScreenDesignOutput>() {
				};

				String strJson;
				try {
					strJson = mapper.writeValueAsString(screenDesignOutput);
					return strJson;
				} catch (JsonProcessingException e) {
					e.printStackTrace();
					return "";
				}
	}

	public static ScreenGroupItem[] checkColRowSpan(ScreenArea[] screenAreas, ScreenGroupItem[] screenGroupItems, ScreenItem[] screenItems) {

		List<ScreenGroupItem> groups = new ArrayList<ScreenGroupItem>();

		for (int i = 0; i < screenItems.length; i++) {
			if (screenItems[i].getGroupItemId() != null) {
				if ((screenItems[i].getColSpan() != null && screenItems[i].getColSpan() > 1) || (screenItems[i].getRowSpan() != null && screenItems[i].getRowSpan() > 1)) {

					ScreenGroupItem groupItem = new ScreenGroupItem();
					groupItem.setGroupItemId(screenItems[i].getGroupItemId());

					int indexGroup = Arrays.asList(screenGroupItems).indexOf(groupItem);

					if (indexGroup != -1) {
						groupItem = Arrays.asList(screenGroupItems).get(indexGroup);
						List<ScreenItem> items = new ArrayList<ScreenItem>();
						items.add(screenItems[i]);
						groupItem.setItems(items);

						groups.add(groupItem);
					}
				}
			}
		}

		Map<Long, List<TableTemp>> matrix = new Hashtable<Long, List<TableTemp>>();

		for (int i = 0; i < groups.size(); i++) {
			Integer rowspan = (groups.get(i).getItems().get(0).getRowSpan() != null) ? groups.get(i).getItems().get(0).getRowSpan() : 1;
			Integer colspan = (groups.get(i).getItems().get(0).getColSpan() != null) ? groups.get(i).getItems().get(0).getColSpan() : 1;

			ScreenArea area = groups.get(i).getScreenArea();

			if (area == null) {
				ScreenArea screenArea = new ScreenArea();
				screenArea.setScreenAreaId(groups.get(i).getScreenAreaId());
				int indexArea = Arrays.asList(screenAreas).indexOf(screenArea);

				if (indexArea != -1) {
					area = Arrays.asList(screenAreas).get(indexArea);
				}
			}

			// get rowindex
			int rowIndex = groups.get(i).getGroupSeqNo() / area.getTotalCol();
			int colIndex = groups.get(i).getGroupSeqNo() % area.getTotalCol();

			int tempRow = rowIndex;

			int tempRowSpan = 0;
			int tempColSpan = 0;

			// get matrix col, row span
			List<TableTemp> hiddens = new ArrayList<TableTemp>();
			do {
				int tempCol = colIndex;
				while (tempColSpan < colspan) {
					if (tempRow == rowIndex && tempCol == colIndex) {

					} else {
						TableTemp tableTemp = new TableTemp();
						tableTemp.setRowIndex(tempRow);
						tableTemp.setColIndex(tempCol);
						hiddens.add(tableTemp);
					}
					tempCol++;
					tempColSpan++;

				}
				tempCol = 0;
				tempColSpan = 0;

				tempRow++;
				tempRowSpan++;
			} while (tempRowSpan < rowspan);

			if (matrix.containsKey(area.getScreenAreaId())) {
				matrix.get(area.getScreenAreaId()).addAll(hiddens);
			} else {
				matrix.put(area.getScreenAreaId(), hiddens);
			}

		}

		// debug
		/*
		 * for(Long areaId : matrix.keySet()) { System.out.println("Area: " + areaId); for(TableTemp temp : matrix.get(areaId)) { System.out.println("          Row: " + temp.getRowIndex() + "-Col: " +temp.getColIndex()); } }
		 */

		for (int i = 0; i < screenGroupItems.length; i++) {
			if (matrix.containsKey(screenGroupItems[i].getScreenAreaId())) {

				ScreenGroupItem group = screenGroupItems[i];
				ScreenArea area = group.getScreenArea();
				if (area == null) {
					ScreenArea screenArea = new ScreenArea();
					screenArea.setScreenAreaId(group.getScreenAreaId());
					int indexArea = Arrays.asList(screenAreas).indexOf(screenArea);

					if (indexArea != -1) {
						area = Arrays.asList(screenAreas).get(indexArea);
					}
				}

				Integer rowIndex = group.getGroupSeqNo() / area.getTotalCol();
				Integer colIndex = group.getGroupSeqNo() % area.getTotalCol();

				for (TableTemp temp : matrix.get(group.getScreenAreaId())) {
					if (rowIndex.equals(temp.getRowIndex()) && colIndex.equals(temp.getColIndex())) {
						screenGroupItems[i].setStyleColRowSpan("display: none;");
					}
				}
			}
		}

		return screenGroupItems;
	}

	public static ScreenDesignForm mappingPreview(ScreenDesign screenDesign, ScreenForm[] screenForms, ScreenArea[] screenAreas, ScreenGroupItem[] screenGroupItems, ScreenItem[] screenItems) {
		ScreenDesignForm form = new ScreenDesignForm();

		screenAreas = ScreenDesignFunction.addItemToArea(screenAreas, screenGroupItems, screenItems);
		screenAreas = ScreenDesignFunction.addGroupArea(screenAreas, screenGroupItems);
		screenAreas = ScreenDesignFunction.addItemToGroup(screenAreas);

		for (int i = 0; i < screenForms.length; i++) {
			screenForms[i].setScreenId(screenDesign.getScreenId());
			screenForms[i].setAreas(new ArrayList<ScreenArea>());
			screenForms[i].setScreenFormId(Long.parseLong(i + ""));
		}

		screenDesign.setScreenForms(screenForms);

		ScreenForm[] forms = screenForms;

		// set screen parameters

		for (int i = 0; i < screenDesign.getArrScreenParameter().length; i++) {
			if (screenDesign.getArrScreenParameter()[i] != null) {
				screenDesign.getArrScreenParameter()[i].setScreenId(screenDesign.getScreenId());
			}
		}
		screenDesign.setArrScreenParameter(screenDesign.getArrScreenParameter());

		ScreenArea[] areas = screenAreas;

		ScreenGroupItem[] groups = new ScreenGroupItem[screenGroupItems.length];

		ScreenItem[] items = new ScreenItem[screenItems.length];

		int startGroupIndex = 0;
		int itemIndex = 0;
		for (int i = 0; i < areas.length; i++) {
			ScreenArea area = areas[i];
			if (areas[i].getAreaType() == -1 || areas[i].getAreaType() == 2 || areas[i].getAreaType() == 3 || areas[i].getTotalGroup() == null || areas[i].getTotalGroup() == 0) {
				if (area.getItems() != null) {
					for (int k = 0; k < area.getItems().size(); k++) {
						ScreenItem item = areas[i].getItems().get(k);
						items[itemIndex] = item;
						itemIndex++;
					}
				}
			} else {
				if (areas[i].getGroups() != null) {
					for (int j = 0; j < areas[i].getGroups().size(); j++) {
						ScreenGroupItem group = areas[i].getGroups().get(j);
						groups[startGroupIndex] = group;
						startGroupIndex++;
						if (group.getItems() != null) {
							for (int k = 0; k < group.getItems().size(); k++) {
								ScreenItem item = group.getItems().get(k);
								item.setGroupItemId(group.getGroupItemId());
								items[itemIndex] = item;
								itemIndex++;
							}
						}
					}
				}
			}
		}

		// set total element for screen group id
		for (int i = 0; i < groups.length; i++) {

			ScreenGroupItem group = groups[i];
			int startItemIndex = 0;
			int totalElement = groups[i].getTotalElement();

			for (int j = 0; j < items.length; j++) {
				ScreenItem item = items[j];

				if (item.getGroupItemId() == null || !item.getGroupItemId().equals(group.getGroupItemId()))
					continue;

				if (totalElement == 0)
					break;

				if (startItemIndex == 0) {
					groups[i].setElementStart(item.getItemSeqNo());

				}

				if (totalElement == 1) {
					groups[i].setElementEnd(item.getItemSeqNo());

				}
				startItemIndex++;
				totalElement--;
			}
			startItemIndex = 0;

		}

		form.setAreaNonGroup(new ArrayList<ScreenArea>());
		for (int i = 0; i < screenAreas.length; i++) {
			// get area type link header
			if (screenAreas[i].getAreaType().equals(-1)) {
				form.getAreaNonGroup().add(screenAreas[i]);
			}
		}

		form.setHeaderLinkItems(new ArrayList<ScreenItem>());
		//
		for (int i = 0; i < screenItems.length; i++) {
			
			ScreenItem item = screenItems[i];
			
			for (ScreenArea area: screenAreas) {
				if (item.getScreenAreaId() != null && area.getScreenAreaId() != null &&  item.getScreenAreaId().equals(area.getScreenAreaId())) {
					screenItems[i].setScreenArea(area);
				}
			}
			
			for (int j = 0; j < form.getAreaNonGroup().size(); j++) {
				if (screenItems[i].getScreenAreaId().equals(form.getAreaNonGroup().get(j).getScreenAreaId())) {
					form.getHeaderLinkItems().add(screenItems[i]);
				}
			}

			screenItems[i].setValue(ScreenDesignFunction.getJsonScreenItem(screenItems[i]));
		}

		// add screen item to area
		for (int i = 0; i < screenAreas.length; i++) {
			List<ScreenItem> itemElements = new ArrayList<ScreenItem>();

			for (int j = 0; j < screenItems.length; j++) {
				if (screenAreas[i].getScreenAreaId().equals(screenItems[j].getScreenAreaId())) {
					itemElements.add(screenItems[j]);
				}
			}
			screenAreas[i].setItems(itemElements);
		}

		screenGroupItems = ScreenDesignFunction.checkColRowSpan(screenAreas, screenGroupItems, screenItems);

		for (int i = 0; i < screenGroupItems.length; i++) {
			form.getScreenGroups().put(screenGroupItems[i].getGroupItemId(), screenGroupItems[i]);
		}
		
		
		
		List<ScreenFormTabGroup> screenFormTabGroups = new ArrayList<ScreenFormTabGroup>();
		List<ScreenFormTabs> screenFormTabs =  new ArrayList<ScreenFormTabs>();
		
		for (int i = 0; i < screenForms.length; i++) {
			if (screenForms[i].getScreenFormTabs() != null && screenForms[i].getScreenFormTabs().length > 0) {
				screenFormTabs.addAll( Arrays.asList(screenForms[i].getScreenFormTabs()));
			}
		}
		
		if (screenFormTabs.size() > 0) {
			List<String> groupTabs = new ArrayList<String>();
			groupTabs.add(screenFormTabs.get(0).getTabCode() + "-" + screenFormTabs.get(0).getScreenFormId());
			
			for (int i = 1; i < screenFormTabs.size(); i++) {
				if (groupTabs.indexOf(screenFormTabs.get(i).getTabCode() + "-" + screenFormTabs.get(i).getScreenFormId()) == -1) {
					groupTabs.add(screenFormTabs.get(i).getTabCode() + "-" + screenFormTabs.get(i).getScreenFormId());
				}
			}
			
			for (int i = 0; i < groupTabs.size(); i++) {
				String[] tabKey = groupTabs.get(i).split("-");
				
				if (tabKey.length == 2) {
					ScreenFormTabGroup group = new ScreenFormTabGroup();
					
					group.setTabCode(tabKey[0]);
					group.setScreenFormId(Long.parseLong(tabKey[1]));
					group.setScreenFormTabs(new ArrayList<ScreenFormTabs>());
					
					for (int j = 0; j < screenFormTabs.size(); j++) {
						if (screenFormTabs.get(j).getScreenFormId().equals(group.getScreenFormId()) 
								&& screenFormTabs.get(j).getTabCode().equals(group.getTabCode())) {
							
							group.setTabDirection(screenFormTabs.get(j).getTabDirection());
							group.getScreenFormTabs().add(screenFormTabs.get(j));
						}
					}
					screenFormTabGroups.add(group);
				}
				
			}
		}
		
		for (int i = 0; i < screenForms.length; i++) {
			
			//add group tab
			screenForms[i].setScreenFormTabGroups(new ArrayList<ScreenFormTabGroup>());
			
			for (ScreenFormTabGroup group : screenFormTabGroups) {
				if (group.getScreenFormId().equals(screenForms[i].getScreenFormId())) {
					String formCode = screenForms[i].getFormCode() + "-" + group.getTabCode();
					formCode = formCode.replace(" ", "");
					//setting template
					String startHtml = "<div id=\""+ formCode +"\" style='width: 100%; float:left;' class='area-tab'>";
					String endHtml = "";
					if (group.getTabDirection().equals(1)) {
						startHtml += "<div class=\"menu-tab\" style=\"float: left; width: 20%; margin: 0px; padding: 0px; margin-left: 4px;\"><ul id=\""+formCode+"\"-tab\" class=\"nav nav-tabs tabs-left\">";
					} else if (group.getTabDirection().equals(0)) {
						startHtml += "<ul style=\"margin-left: 4px; margin-right: 4px;\" id=\""+formCode+"-tab\" class=\"nav nav-tabs\">";
					} else {
						startHtml += "<div style=\"margin-left: 4px; margin-right: 4px;\" class=\"panel-group-accordion\" id=\""+formCode+"-tab\">";
					}
					
					if (group.getTabDirection().equals(1) || group.getTabDirection().equals(0)) {
						for (int j = 0; j < group.getScreenFormTabs().size(); j++) {
							ScreenFormTabs tab = group.getScreenFormTabs().get(j);
					
							if (j == 0) {
								startHtml += "<li class=\"active\"><a data-toggle='tab' href='#"+formCode+"tab-"+j+"'>"+tab.getTabTitle()+"</a></li>";
							} else {
								startHtml += "<li><a data-toggle='tab' href='#"+formCode+"tab-"+j+"'>"+tab.getTabTitle()+"</a></li>";
							}
						}
					}
					
					
					if (group.getTabDirection().equals(1)) {
						startHtml += "</ul></div><div class=\"contain-tab-content\" style=\"float: left; width: 79%;margin: 0px; padding: 0px;\"><div id=\""+formCode + "-tab-content\" style=\"border: 1px solid #ddd;\" class=\"tab-content\">";
						endHtml = "</div></div></div>";
					} else if(group.getTabDirection().equals(0)) {
						startHtml += "</ul><div style=\"margin-left: 4px; margin-right: 4px; height: auto;\" id=\""+formCode + "-tab-content\" class=\"tab-content\">";
						endHtml = "</div></div>";
					} else {
						endHtml = "</div></div>";
					}
					
					group.setStartHtml(startHtml);
					group.setEndHtml(endHtml);
					screenForms[i].getScreenFormTabGroups().add(group);
				}
			}
			
			
			//set value
			FormTab formTab = new FormTab();
			
			if (screenFormTabs.size() > 0) {
				List<Tab> tabs = new ArrayList<Tab>();
				List<ScreenFormTabs> formTabs = new ArrayList<ScreenFormTabs>();
				
				for (int j = 0; j < screenFormTabs.size(); j++) {
					if (screenForms[i].getScreenFormId().equals(screenFormTabs.get(j).getScreenFormId())) {
						Tab tab = new Tab();
						tab.setTabCode(screenFormTabs.get(j).getTabCode());
						tab.setTabDirection(screenFormTabs.get(j).getTabDirection() + "");
						tab.setTitle(screenFormTabs.get(j).getTabTitle());
						tab.setAreas(screenFormTabs.get(j).getAreas());
						tabs.add(tab);
						
						formTabs.add(screenFormTabs.get(j));
					}
				}
				
				ScreenFormTabs[] arrFormTabs = new ScreenFormTabs[formTabs.size()];
				arrFormTabs =  formTabs.toArray(arrFormTabs);
				screenForms[i].setScreenFormTabs(arrFormTabs);
				
				Tab[] arrTabs = new Tab[tabs.size()];
				arrTabs = tabs.toArray(arrTabs);
				
				formTab.setTabs(arrTabs);
				JsonFactory json = new JsonFactory();
				ObjectMapper mapper = new ObjectMapper(json);
				TypeReference<FormTab> typeRef = new TypeReference<FormTab>() {
				};

				String strJson;
				try {
					strJson = mapper.writeValueAsString(formTab);
					screenForms[i].setTabValue(strJson);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
					screenForms[i].setTabValue("");
				}
			}
		}
		
		//set tab
		for (int i = 0; i < screenAreas.length; i++) {
			screenAreas[i] = ScreenDesignFunction.setAreaPosition(screenForms, screenAreas[i], true);
		}

		form.setScreenForms(forms);
		form.setScreenAreas(areas);
		form.setScreenGroupItems(groups);
		form.setScreenItems(items);

		return form;
	}

	public static Map<Integer, Object> getScreenElement(HttpServletRequest request, ScreenDesign screenDesign, Timestamp currentTime) {
		Map<Integer, Object> result = new Hashtable<Integer, Object>();

		String[] screenFormFormActionCode = new String[0];
		String[] screenFormEncryptType = new String[0];
		String[] screenFormMethodType = new String[0];
		String[] screenFormFormSeqNo = new String[0];

		String[] formAreaType = new String[0];
		String[] formAreaPatternType = new String[0];
		String[] formElement = new String[0];
		String[] formTableColumnSize = new String[0];
		String[] formTableWidth = new String[0];
		String[] formTablePosition = new String[0];
		String[] formElementHidden = new String[0];
		String[] formDirection = new String[0];
		String[] formDisplayType = new String[0];
		String[] formElementTable = new String[0];
		String[] formAreaCaptionText = new String[0];
		String[] formAreaCaptionId = new String[0];
		String[] formAreaCode = new String[0];
		String[] formFixedRow = new String[0];
		String[] formCustomSectionContent = new String[0];
		String[] formAreaTypeAction = new String[0];
		String[] formHeaderLabelRow = new String[0];
		String[] formHeaderComponentRow = new String[0];
		String[] groupDisplayType = new String[0];
		String[] enableGroup = new String[0];
		String[] formIndex = new String[0];
		String[] groupTotalElement = new String[0];
		String[] formTotalGroup = new String[0];
		String[] formAreaEvent = new String[0];
		String[] screenFormScreenFormId = new String[0];
		String[] screenFormTab = new String[0];
		
		String[] panelStyle = new String[0];
		String[] headerStyle = new String[0];
		String[] rowStyle = new String[0];
		String[] inputStyle = new String[0];
		String[] alternateRowStyle = new String[0];
		String[] areaCustomType = new String[0];
		String[] formObjectMappingId = new String[0];
		String[] formObjectMappingType = new String[0];
		String[] formAreaIdStore = new String[0];
		String[] formAreaIcon = new String[0];
		String formScreenParameter = new String();
		String[] formDisplayPageable = new String[0];
		
		String[] formScreenAreaSortValue = new String[0];
		
		if (request.getParameter("formDisplayPageable") != null) {
			formDisplayPageable = request.getParameterValues("formDisplayPageable");
		}
		if (request.getParameter("formAreaIcon") != null) {
			formAreaIcon = request.getParameterValues("formAreaIcon");
		}
		
		if (request.getParameter("formObjectMappingType") != null) {
			formObjectMappingType = request.getParameterValues("formObjectMappingType");
		}
		
		if (request.getParameter("formObjectMappingId") != null) {
			formObjectMappingId = request.getParameterValues("formObjectMappingId");
		}
		
		if (request.getParameter("formAreaIdStore") != null) {
			formAreaIdStore = request.getParameterValues("formAreaIdStore");
		}
		
		if (request.getParameter("areaCustomType") != null) {
			areaCustomType = request.getParameterValues("areaCustomType");
		}
		
		//start style
		if (request.getParameter("panelStyle") != null) {
			panelStyle = request.getParameterValues("panelStyle");
		}

		if (request.getParameter("headerStyle") != null) {
			headerStyle = request.getParameterValues("headerStyle");
		}

		if (request.getParameter("rowStyle") != null) {
			rowStyle = request.getParameterValues("rowStyle");
		}

		if (request.getParameter("inputStyle") != null) {
			inputStyle = request.getParameterValues("inputStyle");
		}

		if (request.getParameter("alternateRowStyle") != null) {
			alternateRowStyle = request.getParameterValues("alternateRowStyle");
		}
		//end style

		if (request.getParameter("screenFormTab") != null) {
			screenFormTab = request.getParameterValues("screenFormTab");
		}

		if (request.getParameter("screenFormScreenFormId") != null) {
			screenFormScreenFormId = request.getParameterValues("screenFormScreenFormId");
		}

		if (request.getParameter("formAreaEvent") != null) {
			formAreaEvent = request.getParameterValues("formAreaEvent");
		}
		
		if (request.getParameter("formScreenAreaSortValue") != null) {
			formScreenAreaSortValue = request.getParameterValues("formScreenAreaSortValue");
		}

		if (request.getParameter("formAreaPatternType") != null) {
			formAreaPatternType = request.getParameterValues("formAreaPatternType");
		}

		if (request.getParameter("formTotalGroup") != null) {
			formTotalGroup = request.getParameterValues("formTotalGroup");
		}

		if (request.getParameter("formAreaType") != null) {
			formAreaType = request.getParameterValues("formAreaType");
		}

		if (request.getParameter("formElement") != null) {
			formElement = request.getParameterValues("formElement");
		}

		if (request.getParameter("screenFormFormActionCode") != null) {
			screenFormFormActionCode = request.getParameterValues("screenFormFormActionCode");
		}

		if (request.getParameter("screenFormEncryptType") != null) {
			screenFormEncryptType = request.getParameterValues("screenFormEncryptType");
		}

		if (request.getParameter("screenFormMethodType") != null) {
			screenFormMethodType = request.getParameterValues("screenFormMethodType");
		}

		if (request.getParameter("screenFormFormSeqNo") != null) {
			screenFormFormSeqNo = request.getParameterValues("screenFormFormSeqNo");
		}

		if (request.getParameter("formTableColumnSize") != null) {
			formTableColumnSize = request.getParameterValues("formTableColumnSize");
		}

		if (request.getParameter("formTableWidth") != null) {
			formTableWidth = request.getParameterValues("formTableWidth");
		}

		if (request.getParameter("formTablePosition") != null) {
			formTablePosition = request.getParameterValues("formTablePosition");
		}

		if (request.getParameter("formElementHidden") != null) {
			formElementHidden = request.getParameterValues("formElementHidden");
		}

		if (request.getParameter("formDirection") != null) {
			formDirection = request.getParameterValues("formDirection");
		}

		if (request.getParameter("formDisplayType") != null) {
			formDisplayType = request.getParameterValues("formDisplayType");
		}

		if (request.getParameter("formElementTable") != null) {
			formElementTable = request.getParameterValues("formElementTable");
		}

		if (request.getParameter("formAreaCaptionText") != null) {
			formAreaCaptionText = request.getParameterValues("formAreaCaptionText");
		}

		if (request.getParameter("formAreaCaptionId") != null) {
			formAreaCaptionId = request.getParameterValues("formAreaCaptionId");
		}

		if (request.getParameter("formAreaCode") != null) {
			formAreaCode = request.getParameterValues("formAreaCode");
		}
		if (request.getParameter("formFixedRow") != null) {
			formFixedRow = request.getParameterValues("formFixedRow");
		}
		if (request.getParameter("formCustomSectionContent") != null) {
			formCustomSectionContent = request.getParameterValues("formCustomSectionContent");
		}
		if (request.getParameter("formAreaTypeAction") != null) {
			formAreaTypeAction = request.getParameterValues("formAreaTypeAction");
		}

		if (request.getParameter("formHeaderLabelRow") != null) {
			formHeaderLabelRow = request.getParameterValues("formHeaderLabelRow");
		}

		if (request.getParameter("formHeaderComponentRow") != null) {
			formHeaderComponentRow = request.getParameterValues("formHeaderComponentRow");
		}

		if (request.getParameter("groupDisplayType") != null) {
			groupDisplayType = request.getParameterValues("groupDisplayType");
		}

		if (request.getParameter("enableGroup") != null) {
			enableGroup = request.getParameterValues("enableGroup");
		}

		if (request.getParameter("formIndex") != null) {
			formIndex = request.getParameterValues("formIndex");
		}

		if (request.getParameter("groupTotalElement") != null) {
			groupTotalElement = request.getParameterValues("groupTotalElement");
		}

		if (request.getParameter("screenParameters") != null) {
			formScreenParameter = request.getParameter("screenParameters");
		}

		// screen parameter setting
		ScreenParameter[] arrScreenParameter = null;
		String[] formScreenParameters = formScreenParameter.split(ScreenDesignConst.ROW_SPLIT);
		if (formScreenParameters != null) {
			arrScreenParameter = new ScreenParameter[formScreenParameters.length];
			for (int i = 0; i < formScreenParameters.length; i++) {
				if (!formScreenParameters[i].isEmpty()) {
					String[] items = formScreenParameters[i].split(ScreenDesignConst.ITEM_SPLIT);
					if (items.length >= 3) {
						ScreenParameter screenParameter = new ScreenParameter();

						// screenParameter.setScreenId(screenId);
						screenParameter.setScreenParamCode(items[1]); // temp
						screenParameter.setScreenParamName(items[0]);

						screenParameter.setDataType(Integer.parseInt(items[2])); // temp
						
						if (items.length >= 4 && items[3] != null && !StringUtils.isEmpty(items[3])) {
							screenParameter.setScreenParamIdStore(Long.parseLong(items[3]));
						}
						
						screenParameter.setParamSeqNo(i);
						screenParameter.setCreatedBy(SessionUtils.getAccountId());
						screenParameter.setCreatedDate(currentTime);
						screenParameter.setUpdatedBy(SessionUtils.getAccountId());
						screenParameter.setUpdatedDate(currentTime);
						arrScreenParameter[i] = screenParameter;
					}
				}
			}
			screenDesign.setArrScreenParameter(arrScreenParameter);
		}

		// screen form
		ScreenForm[] screenForms = new ScreenForm[screenFormFormActionCode.length];
		for (int i = 0; i < screenFormFormActionCode.length; i++) {
			ScreenForm screenForm = new ScreenForm();
			String formActionCode = (screenFormFormActionCode.length > 0) ? screenFormFormActionCode[i] : "";
			Integer enctypeType = (screenFormEncryptType.length > 0 && screenFormEncryptType[i] != null && !screenFormEncryptType[i].isEmpty()) ? Integer.parseInt(screenFormEncryptType[i]) : null;
			Integer methodType = (screenFormMethodType.length > 0 && screenFormMethodType[i] != null && !screenFormMethodType[i].isEmpty()) ? Integer.parseInt(screenFormMethodType[i]) : null;
			Integer formSeqNo = (screenFormFormSeqNo.length > 0 && screenFormFormSeqNo[i] != null && !screenFormFormSeqNo[i].isEmpty()) ? Integer.parseInt(screenFormFormSeqNo[i]) : null;
			Long screenFormId = (screenFormScreenFormId.length > 0 && screenFormScreenFormId[i] != null && !screenFormScreenFormId[i].isEmpty()) ? Long.parseLong(screenFormScreenFormId[i]) : null;
			if (formSeqNo != null) {
				screenForm.setScreenFormId(Long.parseLong(formSeqNo.toString()));
			}

			if (screenFormTab[i] != null && !StringUtils.isEmpty(screenFormTab[i])) {
				FormTab formTab = new FormTab();

				JsonFactory jsonTab = new JsonFactory();
				ObjectMapper mapperTab = new ObjectMapper(jsonTab);
				TypeReference<FormTab> typeRefTab = new TypeReference<FormTab>() {
				};

				String jsonString = FunctionCommon.getStringJson(screenFormTab[i]);
				try {
					formTab = mapperTab.readValue(jsonString, typeRefTab);
					
					if (formTab != null && formTab.getTabs() != null && formTab.getTabs().length > 0) {
						
						screenForm.setScreenFormTabs(new ScreenFormTabs[formTab.getTabs().length]);
						
						
						for (int formTabIndex = 0; formTabIndex < formTab.getTabs().length; formTabIndex++) {
							Tab tab = formTab.getTabs()[formTabIndex];
							ScreenFormTabs tabform = new ScreenFormTabs();
							
							tabform.setTabDirection((!StringUtils.isEmpty(tab.getTabDirection())?Integer.parseInt(tab.getTabDirection()):0));
							tabform.setTabCode(tab.getTabCode());
							tabform.setTabTitle(tab.getTitle());
							tabform.setAreas(tab.getAreas());
							tabform.setScreenFormId(screenForm.getScreenFormId());
							screenForm.getScreenFormTabs()[formTabIndex] = tabform;
							
						}
					}
				} catch (JsonParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			screenForm.setFormCode(formActionCode);
			screenForm.setFormSeqNo(formSeqNo);
			screenForm.setMethodType(methodType);
			screenForm.setEnctypeType(enctypeType);
			screenForm.setScreenFormIdStore(screenFormId);
			screenForms[i] = screenForm;
			screenForms[i].setCreatedBy(SessionUtils.getAccountId());
			screenForms[i].setCreatedDate(currentTime);
			screenForms[i].setUpdatedBy(SessionUtils.getAccountId());
			screenForms[i].setUpdatedDate(currentTime);

		}

		// screen area
		ScreenArea[] screenAreas = new ScreenArea[formTableColumnSize.length];
		for (int i = 0; i < formTableColumnSize.length; i++) {
			ScreenArea screenArea = new ScreenArea();
			Integer fixedRow = null;
			String customSectionContent = (formCustomSectionContent.length > 0 && formCustomSectionContent[i] != null && !formCustomSectionContent[i].isEmpty()) ? formCustomSectionContent[i] : "";
			Integer areaTypeAction = null;
			String messageDesignId = (formAreaCaptionId.length > 0 && formAreaCaptionId[i] != null && !formAreaCaptionId[i].isEmpty()) ? formAreaCaptionId[i] : "";
			String messageString = (formAreaCaptionText.length > 0) ? formAreaCaptionText[i] : "";
			String areaCode = (formAreaCode.length > 0) ? formAreaCode[i] : "";
			if(formAreaTypeAction != null && formAreaTypeAction.length > 0) {
				if(StringUtils.isNotEmpty(formAreaTypeAction[i])) {
					areaTypeAction = Integer.parseInt(formAreaTypeAction[i]);
				}
			}
			
			if(StringUtils.isNotEmpty(formFixedRow[i])) {
				fixedRow = Integer.parseInt(formFixedRow[i]);
			}
			Integer displayPageable = 0;
			if(formDisplayPageable != null && formDisplayPageable.length > 0) {
				if(StringUtils.isNotEmpty(formDisplayPageable[i])) {
					displayPageable = Integer.parseInt(formDisplayPageable[i]);
				}
			}
			
			Integer areaSeqNo = i;
			String colWidthUnit = (formTableColumnSize.length > 0) ? formTableColumnSize[i] : "";

			Integer totalCol = null;
			Integer totalElement = null;

			Integer elementDipslayType = null;
			Integer elementPositionType = null;

			String tableElement = (formElementTable.length > 0) ? formElementTable[i] : "";

			Long screenAreaIdStore = (formAreaIdStore.length > 0 && formAreaIdStore[i] != null && !formAreaIdStore[i].isEmpty()) ? Long.parseLong(formAreaIdStore[i]) : null;
			
			if (!tableElement.isEmpty()) {
				String[] arr = tableElement.split(",");
				if (arr != null && arr.length == 2) {
					totalCol = (arr[0] != null && !arr[0].isEmpty()) ? Integer.parseInt(arr[0]) : null;
					totalElement = (arr[1] != null && !arr[1].isEmpty()) ? Integer.parseInt(arr[1]) : null;
				}
			}

			elementDipslayType = (formDisplayType.length > 0 && formDisplayType[i] != null && !formDisplayType[i].isEmpty()) ? Integer.parseInt(formDisplayType[i]) : null;

			elementPositionType = (formDirection.length > 0 && formDirection[i] != null && !formDirection[i].isEmpty()) ? Integer.parseInt(formDirection[i]) : null;

			Integer areaType = (formAreaType.length > 0 && formAreaType[i] != null && !formAreaType[i].isEmpty()) ? Integer.parseInt(formAreaType[i]) : null;
			String tbleWidthUnit = (formTableWidth.length > 0) ? formTableWidth[i] : "";
			Integer tblHeaderRow = (formHeaderLabelRow.length > 0 && formHeaderLabelRow[i] != null && !formHeaderLabelRow[i].isEmpty()) ? Integer.parseInt(formHeaderLabelRow[i]) : null;
			Integer tblRow = (formHeaderComponentRow.length > 0 && formHeaderComponentRow[i] != null && !formHeaderComponentRow[i].isEmpty()) ? Integer.parseInt(formHeaderComponentRow[i]) : null;
			Integer alignPositionType = (formTablePosition.length > 0 && formTablePosition[i] != null && !formTablePosition[i].isEmpty()) ? Integer.parseInt(formTablePosition[i]) : null;
			Integer areaPatternType = (formAreaPatternType.length > 0 && formAreaPatternType[i] != null && !formAreaPatternType[i].isEmpty()) ? Integer.parseInt(formAreaPatternType[i]) : null;
			Long screenFormId = (formIndex.length > 0 && formIndex[i] != null && !formIndex[i].isEmpty()) ? Long.parseLong(formIndex[i]) : null;
			Integer screenAreaCustomType = (areaCustomType.length > 0 && areaCustomType[i] != null && !areaCustomType[i].isEmpty()) ? Integer.parseInt(areaCustomType[i]) : null;
			Integer totalGroup = (formTotalGroup.length > 0 && formTotalGroup[i] != null && !formTotalGroup[i].isEmpty()) ? Integer.parseInt(formTotalGroup[i]) : null;
			String areaIcon = (formAreaIcon.length > 0) ? formAreaIcon[i] : "";
			Long objectMappingId = (formObjectMappingId.length > 0 && formObjectMappingId[i] != null && !formObjectMappingId[i].isEmpty()) ? Long.parseLong(formObjectMappingId[i]) : null;
			Integer objectMappingType = (formObjectMappingType.length > 0 && formObjectMappingType[i] != null && !formObjectMappingType[i].isEmpty()) ? Integer.parseInt(formObjectMappingType[i]) : null;
		
			if (!messageDesignId.isEmpty()) {
				MessageDesign messageDesign = new MessageDesign();
				messageDesign.setMessageCode(messageDesignId);
				messageDesign.setMessageString(messageString);
				Module module = new Module();
				module.setModuleId(screenDesign.getModuleId());
				messageDesign.setMessageType("sc");
				// fix code
				messageDesign.setMessageLevel(1);
				messageDesign.setModuleId(screenDesign.getModuleId());
				screenArea.setMessageDesign(messageDesign);
			} else if (!messageString.isEmpty()) {
				MessageDesign messageDesign = new MessageDesign();
				messageDesign.setMessageString(messageString);
				Module module = new Module();
				module.setModuleId(screenDesign.getModuleId());
				messageDesign.setMessageType("sc");
				// fix code
				messageDesign.setMessageLevel(2);
				messageDesign.setModuleId(screenDesign.getModuleId());
				screenArea.setMessageDesign(messageDesign);
			}

			if (formAreaEvent != null && formAreaEvent.length > 0 && !StringUtils.isEmpty(formAreaEvent[i])) {
				// save event
				List<ScreenAreaEvent> screenAreaEvents = new ArrayList<ScreenAreaEvent>();
				JsonFactory json = new JsonFactory();
				ObjectMapper mapper = new ObjectMapper(json);
				TypeReference<ScreenAreaEventOutput> typeRef = new TypeReference<ScreenAreaEventOutput>() {
				};
				ScreenAreaEventOutput events = null;
				String jsonString = FunctionCommon.getStringJson(formAreaEvent[i]);
				try {
					events = mapper.readValue(jsonString, typeRef);
				} catch (JsonParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (events != null && events.getRequireConstraints() != null) {
					for (int j = 0; j < events.getRequireConstraints().length; j++) {
						ScreenAreaEvent event = new ScreenAreaEvent();
						String ifRequire = "";
						for (int k = 0; k < events.getRequireConstraints()[j].getIfRequired().length; k++) {
							ifRequire += events.getRequireConstraints()[j].getIfRequired()[k];

							if (k < events.getRequireConstraints()[j].getIfRequired().length - 1) {
								ifRequire += ",";
							}
						}

						String thenRequire = "";
						for (int k = 0; k < events.getRequireConstraints()[j].getThenMustRequired().length; k++) {
							thenRequire += events.getRequireConstraints()[j].getThenMustRequired()[k];

							if (k < events.getRequireConstraints()[j].getThenMustRequired().length - 1) {
								thenRequire += ",";
							}
						}

						event.setIfRequire(ifRequire);
						event.setThenMustRequire(thenRequire);
						screenAreaEvents.add(event);
					}
				}
				screenArea.setScreenAreaEvents(screenAreaEvents);

			}
			//TungHT
			
			if (formScreenAreaSortValue[i] != null && formScreenAreaSortValue[i].length() > 0 && !StringUtils.isEmpty(formScreenAreaSortValue[i])) {
				List<ScreenAreaSortMapping> areaSortMapps = new ArrayList<ScreenAreaSortMapping>();
				JsonFactory json = new JsonFactory();
				ObjectMapper mapper = new ObjectMapper(json);
				TypeReference<ScreenAreaSortOutput> typeRef = new TypeReference<ScreenAreaSortOutput>() {
				};
				ScreenAreaSortOutput areaSort = null;
				String jsonString = FunctionCommon.getStringJson(formScreenAreaSortValue[i]);
				try {
					areaSort = mapper.readValue(jsonString, typeRef);
				} catch (JsonParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(areaSort != null) {
					if(areaSort.getSqlId() != null && areaSort.getSqlId() != "") {
						screenArea.setSqlColumnId(Long.parseLong(areaSort.getSqlId()));
					}
					if(areaSort.getEnableSort() != null) {
						screenArea.setEnableSort(areaSort.getEnableSort());
					}
				
					if(areaSort.getAreaSorts() != null && areaSort.getAreaSorts().size() > 0) {
						for(ScreenAreaSortMapping output : areaSort.getAreaSorts()) {
							if (output == null)continue;
							ScreenAreaSortMapping areaSortMapp = new ScreenAreaSortMapping();
							if(output.getScreenAreaCode() != null && output.getScreenAreaCode() != "") {
								areaSortMapp.setScreenAreaCode(output.getScreenAreaCode());
							}
							if(output.getScreenItemCode() != null && output.getScreenItemCode() != "") {
								areaSortMapp.setScreenItemCode(output.getScreenItemCode());
							}
							if(output.getSqlColumnCode() != null && output.getSqlColumnCode() != "") {
								areaSortMapp.setSqlColumnCode(output.getSqlColumnCode());
							}
							if(output.getScreenId() != null) {
								areaSortMapp.setScreenId(output.getScreenId());
							}
							areaSortMapps.add(areaSortMapp);
						}
					}
				}
				screenArea.setScreenAreaSorts(areaSortMapps);
			}
			
			screenArea.setObjectMappingId(objectMappingId);
			screenArea.setObjectMappingType(objectMappingType);
			screenArea.setScreenAreaIdStore(screenAreaIdStore);
			screenArea.setAreaIcon(areaIcon);
			screenArea.setAreaCustomType(screenAreaCustomType);
			screenArea.setInputStyle((inputStyle.length > 0) ? inputStyle[i] : "");
			screenArea.setPanelStyle((panelStyle.length > 0) ? panelStyle[i] : "");
			screenArea.setHeaderStyle((headerStyle.length > 0) ? headerStyle[i] : "");
			screenArea.setRowStyle((rowStyle.length > 0) ? rowStyle[i] : "");
			screenArea.setAlternateRowStyle((alternateRowStyle.length > 0) ? alternateRowStyle[i] : "");
			
			screenArea.setElementDipslayType(elementDipslayType);
			screenArea.setElementPositionType(elementPositionType);
			screenArea.setScreenFormId(screenFormId);
			
			screenArea.setAreaCode(areaCode);
			if(fixedRow != null) {
				screenArea.setFixedRow(fixedRow);
			}
			if(areaTypeAction != null) {
				screenArea.setAreaTypeAction(areaTypeAction);
			}
			if(displayPageable != null) {
				screenArea.setDisplayPageable(displayPageable);
			}
			screenArea.setCustomSectionContent(customSectionContent);
			screenArea.setAreaSeqNo(areaSeqNo);
			screenArea.setColWidthUnit(colWidthUnit);
			screenArea.setTotalCol(totalCol);
			screenArea.setTotalElement(totalElement);
			screenArea.setAreaType(areaType);
			if(tbleWidthUnit == "") {
				tbleWidthUnit = "100%";
			}
			
			screenArea.setTblWidthUnit(tbleWidthUnit);
			screenArea.setTblHeaderRow(tblHeaderRow);
			screenArea.setTblComponentRow(tblRow);
			screenArea.setAlignPositionType(alignPositionType);
			screenArea.setAreaPatternType(areaPatternType);
			screenArea.setDisplayPageable(displayPageable);

			screenAreas[i] = screenArea;
			screenAreas[i].setCreatedBy(SessionUtils.getAccountId());
			screenAreas[i].setCreatedDate(currentTime);
			screenAreas[i].setUpdatedBy(SessionUtils.getAccountId());
			screenAreas[i].setUpdatedDate(currentTime);
			screenAreas[i].setTotalGroup(totalGroup);

			// get hidden item in area
			if (formElementHidden[i] != null && !StringUtils.isEmpty(formElementHidden[i])) {
				String[] parameters = formElementHidden[i].split(ScreenDesignConst.ROW_SPLIT);
				List<ScreenItem> lstScreenHiddenItem = new ArrayList<ScreenItem>();
				if (parameters != null) {
					for (int j = 0; j < parameters.length; j++) {
						if (!parameters[j].isEmpty()) {
							String[] items = parameters[j].split(ScreenDesignConst.ITEM_SPLIT);
							
							ScreenItem screenHiddenItem = new ScreenItem();
							screenHiddenItem.setItemSeqNo(-1 - j);
							screenHiddenItem.setItemType(ScreenDesignConst.ScreenItemConst.ITEM_TYPE_HIDDEN);
							if (items.length >= 1) {
								screenHiddenItem.setItemCode(items[0]);
							}

							MessageDesign messageDesign = new MessageDesign();
							// messageDesign.setMessageCode(items[1]);
							if (items.length >= 3) {
								messageDesign.setMessageString(items[2]);
							}
							Module module = new Module();
							module.setModuleId(screenDesign.getModuleId());
							messageDesign.setMessageType("sc");
							messageDesign.setProjectId(SessionUtils.getCurrentProjectId());
							// fix code
							messageDesign.setMessageLevel(2);
							messageDesign.setModuleId(screenDesign.getModuleId());
							if (items.length >= 4) {
								if (!StringUtils.isEmpty(items[3])) {
									screenHiddenItem.setScreenItemStoreId(Long.parseLong(items[3]));
									Integer dataType = Integer.parseInt(items[3]);
									screenHiddenItem.setPhysicalDataType(dataType);
								}
							}
							
							if (items.length >= 5 && !items[4].equals("null")) {
								Integer dataType = Integer.parseInt(items[4]);
								screenHiddenItem.setPhysicalDataType(dataType);
								screenHiddenItem.setLogicalDataType(DbDomainConst.LogicDataType.LABEL_DYNAMIC);
							}
							
							screenHiddenItem.setMessageDesign(messageDesign);
							screenHiddenItem.setCreatedBy(SessionUtils.getAccountId());
							screenHiddenItem.setCreatedDate(currentTime);
							screenHiddenItem.setUpdatedBy(SessionUtils.getAccountId());
							screenHiddenItem.setUpdatedDate(currentTime);
							lstScreenHiddenItem.add(screenHiddenItem);
							
						}
					}
					screenAreas[i].setListHiddenItems(lstScreenHiddenItem);
				}
			}
		}

		// group item
		ScreenGroupItem[] screenGroupItems = new ScreenGroupItem[groupTotalElement.length];
		for (int i = 0; i < groupTotalElement.length; i++) {
			ScreenGroupItem screenGroupItem = new ScreenGroupItem();

			Integer groupSeqNo = i;
			String groupName = "";
			Integer groupType = (groupDisplayType.length > 0 && groupDisplayType[i] != null && !groupDisplayType[i].isEmpty()) ? Integer.parseInt(groupDisplayType[i]) : null;
			Boolean enableGroupelement = false;
			int totalElement = (groupTotalElement.length > 0 && groupTotalElement[i] != null && !groupTotalElement[i].isEmpty()) ? Integer.parseInt(groupTotalElement[i]) : 1;

			if (enableGroup[i] != null && !enableGroup[i].isEmpty()) {
				enableGroupelement = Boolean.parseBoolean(enableGroup[i]);
			}
			enableGroupelement = true;
			// fix code
			if (groupType == null) {
				groupType = 1;
			}

			Integer itemGroupType = (enableGroup.length > 0 && enableGroup[i] != null && !enableGroup[i].isEmpty() && enableGroup[i].equals("true")) ? 1 : 0;
			//default group
			itemGroupType = 1;

			screenGroupItem.setGroupSeqNo(groupSeqNo);
			screenGroupItem.setGroupType(groupType);
			screenGroupItem.setEnableGroup(enableGroupelement);
			screenGroupItem.setItemGroupType(itemGroupType);

			screenGroupItems[i] = screenGroupItem;
			screenGroupItems[i].setCreatedBy(SessionUtils.getAccountId());
			screenGroupItems[i].setCreatedDate(currentTime);
			screenGroupItems[i].setUpdatedBy(SessionUtils.getAccountId());
			screenGroupItems[i].setUpdatedDate(currentTime);
			screenGroupItems[i].setTotalElement(totalElement);
		}

		ScreenItem[] screenItems = new ScreenItem[formElement.length];
		JsonFactory json = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(json);
		TypeReference<ScreenDesignOutput> typeRef = new TypeReference<ScreenDesignOutput>() {
		};
		// screen item
		for (int i = 0; i < formElement.length; i++) {
			if (formElement[i] == null || formElement[i].isEmpty()) {
				ScreenItem itemEmpty = new ScreenItem();
				itemEmpty.setItemType(1);
				screenItems[i] = itemEmpty;
				continue;
			}
			ScreenItem screenItem = new ScreenItem();

			ScreenDesignOutput screenDesignOutput = new ScreenDesignOutput();
			String string = formElement[i];
			String jsonString = FunctionCommon.getStringJson(string);
			try {
				screenDesignOutput = mapper.readValue(jsonString, typeRef);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String customItemContent = "";
			if(StringUtils.isNotEmpty(screenDesignOutput.getCustomItemContent())) {
				customItemContent = screenDesignOutput.getCustomItemContent();
			}
			screenItem.setCustomItemContent(customItemContent);
			
			String screenItemTextCodeListId = "";
			if(StringUtils.isNotEmpty(screenDesignOutput.getScreenItemTextCodeListId())) {
				screenItemTextCodeListId = screenDesignOutput.getScreenItemTextCodeListId();
			}
			screenItem.setScreenItemTextCodeListId(screenItemTextCodeListId);
			
			if (!StringUtils.isEmpty(screenDesignOutput.getScreenItemId())) {
				screenItem.setScreenItemStoreId(Long.parseLong(screenDesignOutput.getScreenItemId()));
			}
			
			// add validation
			ScreenItemValidation screenItemValidation = new ScreenItemValidation();
			if (!screenDesignOutput.getMandatory().isEmpty() && screenDesignOutput.getMandatory().equals("true")) {
				// fix code
				screenItemValidation.setMandatoryFlg(1);
			} else {
				// fix code
				screenItemValidation.setMandatoryFlg(0);
			}

			if (!screenDesignOutput.getMaxlength().isEmpty()) {
				screenItemValidation.setMaxlength(Integer.parseInt(screenDesignOutput.getMaxlength().trim()));
			}

			if (!screenDesignOutput.getMaxvalue().isEmpty()) {
				screenItemValidation.setMaxVal(screenDesignOutput.getMaxvalue().trim());
			}

			if (!screenDesignOutput.getMinvalue().isEmpty()) {
				screenItemValidation.setMinVal(screenDesignOutput.getMinvalue().trim());
			}

			if (!screenDesignOutput.getFormatcode().isEmpty()) {
				screenItemValidation.setFmtCode(screenDesignOutput.getFormatcode());
			}

			String itemCode = screenDesignOutput.getColumnname();
			Integer logicalDataType = null;

			if (screenDesignOutput.getDatatype() != null && !screenDesignOutput.getDatatype().isEmpty()) {
				logicalDataType = Integer.parseInt(screenDesignOutput.getDatatype());
			}

			Integer physicalDataType = null;

			if (screenDesignOutput.getDatatype().equals(DbDomainConst.LogicDataType.INTEGER.toString())) {
				physicalDataType = 5;
				screenItemValidation.setFmtCode("Integer");
				screenItemValidation.setMaxlength(null);
			} else if (screenDesignOutput.getDatatype().equals(DbDomainConst.LogicDataType.DECIMAL.toString())) {
				physicalDataType = 4;
				screenItemValidation.setFmtCode("Decimal");
				screenItemValidation.setMaxlength(null);
			} else if (screenDesignOutput.getDatatype().equals(DbDomainConst.LogicDataType.DATE.toString())) {
				physicalDataType = 10;
				screenItemValidation.setFmtCode("Date");
				screenItemValidation.setMaxlength(null);
			} else if (screenDesignOutput.getDatatype().equals(DbDomainConst.LogicDataType.TIME.toString())) {
				physicalDataType = 11;
				screenItemValidation.setFmtCode("Time");
				screenItemValidation.setMaxlength(null);
			} else if (screenDesignOutput.getDatatype().equals(DbDomainConst.LogicDataType.DATETIME.toString())) {
				physicalDataType = 12;
				screenItemValidation.setFmtCode("DateTime");
				screenItemValidation.setMaxlength(null);
			} else if (screenDesignOutput.getDatatype().equals(DbDomainConst.LogicDataType.CURRENCY.toString())) {
				physicalDataType = 15;
				screenItemValidation.setFmtCode("Currency");
				screenItemValidation.setMaxlength(null);
			} else if (screenDesignOutput.getDatatype().equals(DbDomainConst.LogicDataType.EMAIL.toString())) {
				physicalDataType = 1;
				screenItemValidation.setFmtCode("Email");
			} else if (screenDesignOutput.getDatatype().equals(DbDomainConst.LogicDataType.CODE.toString())) {
				physicalDataType = 1;
				screenItemValidation.setFmtCode("Alphanumeric");
			} else if (screenDesignOutput.getDatatype().equals(DbDomainConst.LogicDataType.FILEUPLOAD.toString())) {
				physicalDataType = 9;
			} else if (screenDesignOutput.getDatatype().equals(DbDomainConst.LogicDataType.PHONE.toString())) {
				physicalDataType = 1;
				screenItemValidation.setFmtCode("Phone");
			} else if (screenDesignOutput.getDatatype().equals(DbDomainConst.LogicDataType.REMARK.toString())) {
				physicalDataType = 1;
				screenItemValidation.setFmtCode("Remark");
			} else if (screenDesignOutput.getDatatype().equals(DbDomainConst.LogicDataType.LABEL.toString())) {
				physicalDataType = 20;
			} else if (screenDesignOutput.getDatatype().equals(DbDomainConst.LogicDataType.LINK.toString())) {
				physicalDataType = 11;
			} else if (screenDesignOutput.getDatatype().equals(DbDomainConst.LogicDataType.BUTTON.toString())) {
				physicalDataType = 13;
			} else if (screenDesignOutput.getDatatype().equals(DbDomainConst.LogicDataType.NAME.toString())) {
				physicalDataType = 1;
			} else if (screenDesignOutput.getDatatype().equals(DbDomainConst.LogicDataType.LABEL_DYNAMIC.toString())) {
				physicalDataType = 1;
			} else if (screenDesignOutput.getDatatype().equals(DbDomainConst.LogicDataType.AUTOCOMPLETE.toString()) || screenDesignOutput.getDatatype().equals(DbDomainConst.LogicDataType.CHECKBOX.toString()) || screenDesignOutput.getDatatype().equals(DbDomainConst.LogicDataType.RADIO.toString()) || screenDesignOutput.getDatatype().equals(DbDomainConst.LogicDataType.SELECT.toString())) {
				physicalDataType = (!screenDesignOutput.getPhysicaldatatype().isEmpty()) ? Integer.parseInt(screenDesignOutput.getPhysicaldatatype()) : 1;
			}	else if (screenDesignOutput.getDatatype().equals(DbDomainConst.LogicDataType.LINK_DYNAMIC.toString())) {
				physicalDataType = 1;
			}

			if (screenDesignOutput.getPhysicaldatatype() != null && screenDesignOutput.getPhysicaldatatype().length() > 0) {
				physicalDataType = Integer.parseInt(screenDesignOutput.getPhysicaldatatype());
			}
			
			/*if (screenDesignOutput.getBaseType() != null && screenDesignOutput.getBaseType().length() > 0) {
				
				physicalDataType = Integer.parseInt(screenDesignOutput.getBaseType());
				if (physicalDataType != 1 && physicalDataType != 3) {
					screenItemValidation.setMaxlength(null);
				}
			}*/

			boolean isBuldle = (screenDesignOutput.getIsBundle().isEmpty()) ? false : Boolean.parseBoolean(screenDesignOutput.getIsBundle());

			Integer colSpan = (screenDesignOutput.getColspan().isEmpty()) ? null : Integer.parseInt(screenDesignOutput.getColspan());
			Integer rowSpan = (screenDesignOutput.getRowspan().isEmpty()) ? null : Integer.parseInt(screenDesignOutput.getRowspan());
			Integer itemType = null;
			String itemWidthUnit = screenDesignOutput.getWidth() + screenDesignOutput.getWidthunit();
			String mandatory = screenDesignOutput.getMandatory();

			Integer tabIndex = (screenDesignOutput.getTabindex().isEmpty()) ? null : Integer.parseInt(screenDesignOutput.getTabindex());

			if (screenDesignOutput.getDatatype().equals(DbDomainConst.LogicDataType.LINK.toString()) || screenDesignOutput.getDatatype().equals(DbDomainConst.LogicDataType.LINK_DYNAMIC.toString())) {
				itemType = 3;
			} else if (screenDesignOutput.getDatatype().equals(DbDomainConst.LogicDataType.BUTTON.toString())) {
				itemType = 4;
			} else {
				itemType = 1;
			}
			
			if (screenDesignOutput.getItemType() != null && !StringUtils.isEmpty(screenDesignOutput.getItemType()) && screenDesignOutput.getItemType().equals("5")) {
				itemType = 5;
			}

			if (!screenDesignOutput.getDialogAutocompleteCode().isEmpty()) {
				AutocompleteDesign autocompleteDesign = new AutocompleteDesign();
				if (!screenDesignOutput.getDialogAutocompleteCode().equals("undefined")) {
					autocompleteDesign.setAutocompleteId(Long.parseLong(screenDesignOutput.getDialogAutocompleteCode()));
					autocompleteDesign.setAutocompleteName(screenDesignOutput.getDialogAutocompleteText());
					screenItem.setAutocompleteDesign(autocompleteDesign);
				}
			}

			if (screenDesignOutput.getTablecode() != null && !screenDesignOutput.getTablecode().isEmpty() && !screenDesignOutput.getTablecolumncode().isEmpty()) {
				//screenItem.setDomainTblMappingId(Long.parseLong(screenDesignOutput.getTablecode()));
				//screenItem.setDomainTblMappingItemId(Long.parseLong(screenDesignOutput.getTablecolumncode()));
			}

			Long outputBeanId = (screenDesignOutput.getOutputBeanId().isEmpty()) ? null : Long.parseLong(screenDesignOutput.getOutputBeanId());
			screenItem.setOutputBeanId(outputBeanId);

			Integer showBlankItem = (screenDesignOutput.getShowBlankItem().isEmpty()) ? null : Integer.parseInt(screenDesignOutput.getShowBlankItem());
			screenItem.setShowBlankItem(showBlankItem);
			
			String defaultValue = screenDesignOutput.getDefaultvalue();
			screenItem.setStyle(screenDesignOutput.getStyle());
			screenItem.setHoverStyle(screenDesignOutput.getHoverStyle());
			screenItem.setDefaultValue(defaultValue);
			screenItem.setTabIndex(tabIndex);
			screenItem.setLogicalDataType(logicalDataType);
			screenItem.setPhysicalDataType(physicalDataType);
			screenItem.setItemType(itemType);
			screenItem.setScreenItemValidation(screenItemValidation);
			screenItem.setColSpan(colSpan);
			screenItem.setRowSpan(rowSpan);
			screenItem.setIcon(screenDesignOutput.getIcon());
			
			if (!StringUtils.isEmpty(screenDesignOutput.getEnableConfirm())) {
				screenItem.setEnableConfirm(Integer.parseInt(screenDesignOutput.getEnableConfirm()));
			}
			
			if (!StringUtils.isEmpty(screenDesignOutput.getMessageConfirmText())) {
				MessageDesign messageDesign = new MessageDesign();
				messageDesign.setMessageString(screenDesignOutput.getMessageConfirmText());
				
				if (!StringUtils.isEmpty(screenDesignOutput.getMessageConfirm())) {
					messageDesign.setMessageCode(screenDesignOutput.getMessageConfirm());
				} else {
					messageDesign.setMessageCode(StringUtils.EMPTY);
				}
				
				Module module = new Module();
				module.setModuleId(screenDesign.getModuleId());
				messageDesign.setMessageType("inf");
				// fix code
				messageDesign.setMessageLevel(2);
				messageDesign.setModuleId(screenDesign.getModuleId());
				screenItem.setMessageConfirm(messageDesign);
			}
			
			screenItem.setAllowAnyInput((screenDesignOutput.getAllowAnyInput().isEmpty()) ? null : Integer.parseInt(screenDesignOutput.getAllowAnyInput()));
			screenItem.setEnablePassword((screenDesignOutput.getEnablePassword().isEmpty()) ? null : Integer.parseInt(screenDesignOutput.getEnablePassword()));
			
			if (screenDesignOutput.getShowLabel() != null && !StringUtils.isEmpty(screenDesignOutput.getShowLabel()))
				screenItem.setShowLabel(Integer.parseInt(screenDesignOutput.getShowLabel()));

			if (!StringUtils.isEmpty(screenDesignOutput.getScreenItemId())) {
				screenItem.setScreenItemStoreId(Long.parseLong(screenDesignOutput.getScreenItemId()));
			}

			/*if (itemWidthUnit == null || itemWidthUnit.isEmpty()) {
				itemWidthUnit = "100%";
			}*/
			screenItem.setItemWidthUnit(itemWidthUnit);

			if(screenDesignOutput.getScreenDesignIdCodeListId() != null) {
				screenItem.setScreenDesignIdCodeListId(screenDesignOutput.getScreenDesignIdCodeListId());
			} 
			if(screenDesignOutput.getScreenItemIdCodeListId() != null) {
				screenItem.setScreenItemIdCodeListId(screenDesignOutput.getScreenItemIdCodeListId());
			}
				// Add display type for [Normal, from to]
			if(screenDesignOutput.getDisplayFromTo() != null && !StringUtils.isEmpty(screenDesignOutput.getDisplayFromTo())) {
				screenItem.setDisplayFromTo(Integer.parseInt(screenDesignOutput.getDisplayFromTo()));
			}
			
			// fix code => change to enum
			if (logicalDataType != null && (!screenDesignOutput.getLabel().isEmpty() || !screenDesignOutput.getLabelText().isEmpty())) {
				MessageDesign messageDesign = new MessageDesign();
				messageDesign.setMessageCode(screenDesignOutput.getLabel());
				messageDesign.setMessageString(screenDesignOutput.getLabelText());
				Module module = new Module();
				module.setModuleId(screenDesign.getModuleId());
				messageDesign.setMessageType("sc");
			
				messageDesign.setMessageLevel(2);
				
				if(screenDesignOutput.getMessageLevelText() != null) {
					messageDesign.setMessageLevelText(screenDesignOutput.getMessageLevelText());
				}
				messageDesign.setModuleId(screenDesign.getModuleId());
				screenItem.setMessageDesign(messageDesign);
			}

			// setting datasource
			Integer dataSourceType = (StringUtils.isEmpty(screenDesignOutput.getDatasourcetype()) ? null : Integer.parseInt(screenDesignOutput.getDatasourcetype()));
			if (dataSourceType != null) {
				screenItem.setDataSourceType(dataSourceType);
				if (dataSourceType == 2) {
					/**
					 * Save code list check codelist system or user define codelist
					 */
					if (screenDesignOutput.getLocalCodelist() != null && !StringUtils.isEmpty(screenDesignOutput.getLocalCodelist())) {
						Integer typeCodelist = Integer.parseInt(screenDesignOutput.getLocalCodelist());
						screenItem.setCodelistType(typeCodelist);
						if (ScreenDesignConst.ScreenItemConst.CODELIST_TYPE_SYSTEM.equals(typeCodelist)) {
							if (screenDesignOutput.getCodelistCode() != null && !screenDesignOutput.getCodelistCode().equals("null") && !screenDesignOutput.getCodelistCode().isEmpty()) {
								Long codelistId = Long.parseLong(screenDesignOutput.getCodelistCode());
								screenItem.setCodelistId(codelistId);
								
								List<ScreenItemCodelist> codelists = new ArrayList<ScreenItemCodelist>();
								if (screenDesignOutput.getParameters() != null && !screenDesignOutput.getParameters().isEmpty()) {
									String[] options = screenDesignOutput.getParameters().split(ScreenDesignConst.ROW_SPLIT);
									if (options != null) {
										for (int j = 0; j < options.length; j++) {
											if (!options[j].isEmpty()) {
												String[] items = options[j].split(ScreenDesignConst.ITEM_SPLIT);
												if (items.length == 2) {
													ScreenItemCodelist codelist = new ScreenItemCodelist();
													codelist.setCodelistSeqNo(j);
													codelist.setCodelistName(items[0]);
													codelist.setCodelistVal(items[1]);

													// fix code support
													if (screenDesignOutput.getIsSupportOptionValue().equals("true")) {
														codelist.setSupportOptionValFlg(0);
													} else {
														codelist.setSupportOptionValFlg(1);
													}

													codelists.add(codelist);
												}
											}
										}
									}
									screenItem.setListScreenItemCodelists(codelists);
								}
							}
						} else if (ScreenDesignConst.ScreenItemConst.CODELIST_TYPE_TABLE.equals(typeCodelist)) {

						} else if (ScreenDesignConst.ScreenItemConst.CODELIST_TYPE_CUSTOM.equals(typeCodelist)) {
							List<ScreenItemCodelist> codelists = new ArrayList<ScreenItemCodelist>();
							if (screenDesignOutput.getParameters() != null && !screenDesignOutput.getParameters().isEmpty()) {
								String[] options = screenDesignOutput.getParameters().split(ScreenDesignConst.ROW_SPLIT);
								if (options != null) {
									for (int j = 0; j < options.length; j++) {
										if (!options[j].isEmpty()) {
											String[] items = options[j].split(ScreenDesignConst.ITEM_SPLIT);
											if (items.length == 2) {
												ScreenItemCodelist codelist = new ScreenItemCodelist();
												codelist.setCodelistSeqNo(j);
												codelist.setCodelistName(items[0]);
												codelist.setCodelistVal(items[1]);

												// fix code support
												if (screenDesignOutput.getIsSupportOptionValue().equals("true")) {
													codelist.setSupportOptionValFlg(0);
												} else {
													codelist.setSupportOptionValFlg(1);
												}

												codelists.add(codelist);
											}
										}
									}
								}
								screenItem.setListScreenItemCodelists(codelists);
							}
						}
					}
				} else {
					Long sqlDesignId = (StringUtils.isEmpty(screenDesignOutput.getSqldesignid())) ? null : Long.parseLong(screenDesignOutput.getSqldesignid());
					Long optionLabelId = (StringUtils.isEmpty(screenDesignOutput.getOptionlabel())) ? null : Long.parseLong(screenDesignOutput.getOptionlabel());
					Long optionValueId = (StringUtils.isEmpty(screenDesignOutput.getOptionvalue())) ? null : Long.parseLong(screenDesignOutput.getOptionvalue());

					if (sqlDesignId != null) {
						SqlDesign sqlDesign = new SqlDesign();
						sqlDesign.setSqlDesignId(sqlDesignId);
						screenItem.setSqlDesign(sqlDesign);

						if (screenItem.getLogicalDataType().equals(DbDomainConst.LogicDataType.AUTOCOMPLETE)) {
							AutocompleteDesign au = new AutocompleteDesign();
							au.setAutocompleteId(sqlDesignId);
							screenItem.setAutocompleteDesign(au);
						}

						if (optionLabelId != null) {
							SqlDesignResult optionLabel = new SqlDesignResult();
							optionLabel.setResultId(optionLabelId);
							screenItem.setOptionLabel(optionLabel);
						}

						if (optionValueId != null) {
							SqlDesignResult optionValue = new SqlDesignResult();
							optionValue.setResultId(optionValueId);
							screenItem.setOptionValue(optionValue);
						}
					}

				}

			}

			// get screen action and screen action param
			// fix code => change to enum
			if (logicalDataType != null && (logicalDataType == 11 || logicalDataType == 13 || logicalDataType == 22)) {
				ScreenAction screenAction = new ScreenAction();
				screenAction.setFromScreenId(new Long(1));
				if (!screenDesignOutput.getNavigateTo().isEmpty()) {
					screenAction.setToScreenId(Long.parseLong(screenDesignOutput.getNavigateTo().trim()));
				}
				if (!screenDesignOutput.getActiontype().isEmpty() && ScreenDesignConst.ScreenActionConst.ACTION_TYPE_BLOGIC == Integer.parseInt(screenDesignOutput.getActiontype().trim())) {
					screenAction.setActionType(ScreenDesignConst.ScreenActionConst.ACTION_TYPE_BLOGIC);
				} else {
					screenAction.setActionType(ScreenDesignConst.ScreenActionConst.ACTION_TYPE_SCREEN); // default
				}
				if (!screenDesignOutput.getActionName().isEmpty()) {
					screenAction.setConnectionMsg(screenDesignOutput.getActionName().trim());
				}
				
				if (!screenDesignOutput.getNavigateToBlogic().isEmpty()) {
					screenAction.setNavigateToBlogicId(Long.parseLong(screenDesignOutput.getNavigateToBlogic()));
				}
				
				if ("true".equals(screenDesignOutput.getIsSubmit())) {
					screenAction.setSubmitMethodType(ScreenDesignConst.ScreenActionConst.SUBMIT_METHOD_TYPE_POST);
				} else {
					screenAction.setSubmitMethodType(ScreenDesignConst.ScreenActionConst.SUBMIT_METHOD_TYPE_GET);
					if (!screenDesignOutput.getParameters().isEmpty()) {
						String[] parameters = screenDesignOutput.getParameters().split(ScreenDesignConst.ROW_SPLIT);
						// screen parameter setting
						List<ScreenActionParam> lstScreenActionParam = new ArrayList<ScreenActionParam>();
						if (parameters != null) {
							for (int j = 0; j < parameters.length; j++) {
								if (!parameters[j].isEmpty()) {
									String[] items = parameters[j].split(ScreenDesignConst.ITEM_SPLIT);
									if (items.length > 2) {
										ScreenActionParam screenParameter = new ScreenActionParam();
										screenParameter.setActionParamCode(items[0]); // temp

										if (items[1] != null && !items[1].isEmpty()) {
											screenParameter.setScreenItemCode(items[1]);
										}

										if (items.length > 3 && !StringUtils.isEmpty(items[3]))
											screenParameter.setActionParamName(items[3]);

										if (items.length > 2 && !StringUtils.isEmpty(items[2]))
											screenParameter.setDataType(Integer.parseInt(items[2])); // temp
										screenParameter.setParamSeqNo(j);
										screenParameter.setCreatedBy(SessionUtils.getAccountId());
										screenParameter.setCreatedDate(currentTime);
										screenParameter.setUpdatedBy(SessionUtils.getAccountId());
										screenParameter.setUpdatedDate(currentTime);
										lstScreenActionParam.add(screenParameter);
									}
								}
							}
						}
						screenAction.setListScreenParameters(lstScreenActionParam);
					}
				}

				screenAction.setCreatedBy(SessionUtils.getAccountId());
				screenAction.setCreatedDate(currentTime);
				screenAction.setUpdatedBy(SessionUtils.getAccountId());
				screenAction.setUpdatedDate(currentTime);
				screenItem.setScreenAction(screenAction);
			}

			// event setting
			Event[] events = screenDesignOutput.getEvents();

			if (events != null && events.length > 0) {
				List<ScreenItemEvent> screenItemEvents = new ArrayList<ScreenItemEvent>();
				for (int j = 0; j < events.length; j++) {

					Integer itemEventSeqNo = j;
					Integer eventType = (StringUtils.isEmpty(events[j].getEventtype())) ? null : Integer.parseInt(events[j].getEventtype());

					Integer effectAreaType = (StringUtils.isEmpty(events[j].getEffectareatype())) ? null : Integer.parseInt(events[j].getEffectareatype());
					String effectArea = events[j].getEffectarea();
					Long blogicId = (StringUtils.isEmpty(events[j].getBlogicid())) ? null : Long.parseLong(events[j].getBlogicid());

					ScreenItemEvent event = new ScreenItemEvent(itemEventSeqNo, eventType, effectAreaType, effectArea, blogicId);
					if (eventType.equals(1)) {
						List<ScreenItemEventMapping> screenItemEventMappings = new ArrayList<ScreenItemEventMapping>();

						if (events[j].getInputbeans() != null) {
							for (int k = 0; k < events[j].getInputbeans().length; k++) {
								Bean input = events[j].getInputbeans()[k];

								Long beanId = (StringUtils.isEmpty(input.getBeanid())) ? null : Long.parseLong(input.getBeanid());
								String itemCodeEvent = input.getItemcode();
								Integer beanType = ScreenItemBeanTypeConst.INPUT;

								screenItemEventMappings.add(new ScreenItemEventMapping(beanId, itemCodeEvent, beanType));
							}
						}

						if (events[j].getOutputbeans() != null) {
							for (int k = 0; k < events[j].getOutputbeans().length; k++) {
								Bean output = events[j].getOutputbeans()[k];

								Long beanId = (StringUtils.isEmpty(output.getBeanid())) ? null : Long.parseLong(output.getBeanid());
								String itemCodeEvent = output.getItemcode();
								Integer beanType = ScreenItemBeanTypeConst.OUTPUT;

								screenItemEventMappings.add(new ScreenItemEventMapping(beanId, itemCodeEvent, beanType));
							}
						}

						event.setScreenItemEventMappings(screenItemEventMappings);
					}
					screenItemEvents.add(event);
				}
				screenItem.setScreenItemEvents(screenItemEvents);
			}

			if (screenItem.getLogicalDataType() != null && screenItem.getLogicalDataType().equals(DbDomainConst.LogicDataType.AUTOCOMPLETE)) {
				
				if (!screenDesignOutput.getParameters().isEmpty()) {
					screenItem.setScreenItemAutocompleteInputs(new ArrayList<ScreenItemAutocompleteInput>());
					
					String[] parameters = screenDesignOutput.getParameters().split(ScreenDesignConst.ROW_SPLIT);
					
					if (parameters != null) {
						for (int j = 0; j < parameters.length; j++) {
							ScreenItemAutocompleteInput input = new ScreenItemAutocompleteInput();
							if (!parameters[j].isEmpty()) {
								String[] items = parameters[j].split(ScreenDesignConst.ITEM_SPLIT);
								if (items.length == 2) {
									
									if (items[0] != null && !items[0].isEmpty() && !items[0].equals("null")) {
										input.setInputId(Long.parseLong(items[0]));
									}

									if (items[1] != null && !items[1].isEmpty() && !items[1].equals("null")) {
										input.setScreenItemCode(items[1]);
									}
								}
							}
							screenItem.getScreenItemAutocompleteInputs().add(input);
						}
					}
					
				}
				
				
			}
			if (!StringUtils.isEmpty(screenDesignOutput.getScreenItemId())) {
				screenItem.setScreenItemStoreId(Long.parseLong(screenDesignOutput.getScreenItemId()));
			}
			Integer buttonStyle = (screenDesignOutput.getButtonStyle().isEmpty()) ? null : Integer.parseInt(screenDesignOutput.getButtonStyle());
			Long screenTransitionNode = (StringUtils.isEmpty(screenDesignOutput.getScreenTransition())) ? null : Long.parseLong(screenDesignOutput.getScreenTransition());
			
			screenItem.setScreenTransitionId(screenTransitionNode);
			screenItem.setButtonType(buttonStyle);
			screenItem.setBuldle(isBuldle);
			
			screenItems[i] = screenItem;
			screenItems[i].setCreatedBy(SessionUtils.getAccountId());
			screenItems[i].setCreatedDate(currentTime);
			screenItems[i].setUpdatedBy(SessionUtils.getAccountId());
			screenItems[i].setUpdatedDate(currentTime);
			screenItems[i].setValue(formElement[i]);
			screenItems[i].setItemCode(itemCode);

		}

		result.put(ScreenDesignConst.ScreenElementConst.SCREEN, screenDesign);
		result.put(ScreenDesignConst.ScreenElementConst.FORM, screenForms);
		result.put(ScreenDesignConst.ScreenElementConst.AREA, screenAreas);
		result.put(ScreenDesignConst.ScreenElementConst.GROUP_ITEM, screenGroupItems);
		result.put(ScreenDesignConst.ScreenElementConst.ITEM, screenItems);

		return result;
	}

	public static List<ScreenTransition> readJson(String stJson) {
		JsonFactory json = new JsonFactory();
		List<ScreenTransition> sdLstUpdt = new ArrayList<ScreenTransition>();
		ObjectMapper mapper = new ObjectMapper(json);
		try {
			sdLstUpdt = mapper.readValue(stJson, TypeFactory.defaultInstance().constructCollectionType(List.class, ScreenTransition.class));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return sdLstUpdt;
	}
	
	public static List<ScreenTransitionBranch> readJsonScreenTransitionBranch(String stJson) {
		JsonFactory json = new JsonFactory();
		List<ScreenTransitionBranch> sdLstUpdt = new ArrayList<ScreenTransitionBranch>();
		ObjectMapper mapper = new ObjectMapper(json);
		try {
			sdLstUpdt = mapper.readValue(stJson, TypeFactory.defaultInstance().constructCollectionType(List.class, ScreenTransitionBranch.class));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return sdLstUpdt;
	}

	public static List<InfoModuleForScreenDesign> parseJson(String stJson) {
		String parttenJson = "[" + stJson + "]";
		List<InfoModuleForScreenDesign> infoModuleForScreenDesign = new ArrayList<InfoModuleForScreenDesign>();
		JsonFactory json = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(json);
		try {
			infoModuleForScreenDesign = mapper.readValue(parttenJson, TypeFactory.defaultInstance().constructCollectionType(List.class, InfoModuleForScreenDesign.class));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return infoModuleForScreenDesign;
	}

	public static ScreenArea setAreaPosition(ScreenForm[] screenForms, ScreenArea screenArea, boolean view) {
		for (ScreenForm screenForm : screenForms) {
			
			int tabIndex = 0;
			for (int i = 0; i < screenForm.getScreenFormTabGroups().size(); i++) {
				ScreenFormTabGroup groupTab = screenForm.getScreenFormTabGroups().get(i);
				String startHtml = "";
				String endHtml = "";
				String formCode = screenForm.getFormCode() + "-" + groupTab.getTabCode();
				formCode = formCode.replace(" ", "");
				
				for (int j = 0; j <groupTab.getScreenFormTabs().size(); j++) {
					ScreenFormTabs tab = groupTab.getScreenFormTabs().get(j);
					
					String [] areaCodes = tab.getAreas().split(",");
					Integer indexArea = Arrays.asList(areaCodes).indexOf(screenArea.getAreaCode()); 
					if (indexArea != -1) {
						
						if (j == 0 && indexArea == 0) {
							startHtml = groupTab.getStartHtml();
						}
						
						if (j == groupTab.getScreenFormTabs().size() - 1 && indexArea == areaCodes.length - 1) {
							
							endHtml = groupTab.getEndHtml();
						}
						
						if (tab.getTabDirection().equals(2) || tab.getTabDirection().equals(3)) {
							
							if (indexArea == 0) {
								String selectedAccordion = "";
								
								String dataParent = "";
								
								if (tab.getTabDirection().equals(3)) {
									dataParent = " data-parent=\"#"+formCode+"-tab\" ";
								}
								
								String title = "        <a data-toggle='collapse' class='collapsed' "+dataParent+" href='#"+formCode+"tab-"+j+"'>" +
										tab.getTabTitle()+" <i class=\"indicator glyphicon  pull-right\"></i></a>";
								if (j == 0 && !view) {
									selectedAccordion = "in";
									
									title = "<div class='form-inline'>" +
									"	<span style='cursor: move;' class='.ui-state-dark qp-glyphicon glyphicon glyphicon-sort sortable srcgenTableSort'></span>" +
									" <span style='cursor: pointer;' onclick='openTabSetting(this)' class='.ui-state-dark qp-glyphicon glyphicon glyphicon-cog'></span>&nbsp;" +
									title +
									"</div>";
								}
								startHtml += "  <div class=\"panel panel-default-accordion\">" +
									"	    <div class=\"panel-heading\">" +
									"      <span class=\"qp-heading-text\">" +
											title +
									"     </span>" +
									"    </div>" +
									"    <div id=\""+formCode+"tab-"+j+"\" class=\"panel-collapse collapse "+selectedAccordion+"\">" +
									"      <div class=\"panel-body\">";
								
								
							} else {
								startHtml = "";
							}
							
							if (indexArea == areaCodes.length - 1) {
								endHtml += "</div></div></div>";
							} else {
								endHtml += "";
							}
						
						} else {
							if (indexArea == 0) {
								String activeTab = "";
								if (j == 0) {
									activeTab = " active";
								}
								startHtml += "<div id='"+formCode+"tab-"+j+"' style='overflow: hidden;' class='tab-pane"+activeTab+"'>";
							} else {
								startHtml += "";
							}
							
							if (indexArea == areaCodes.length - 1) {
								endHtml += "</div>";
							} else {
								endHtml += "";
							}
							
						}
						
						
						screenArea.setStartHtml(startHtml);
						screenArea.setEndHtml(endHtml);
					}
				}
			}
		}
		
		return screenArea;
	}
}
