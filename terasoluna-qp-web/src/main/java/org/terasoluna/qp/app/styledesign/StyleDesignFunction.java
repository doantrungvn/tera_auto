package org.terasoluna.qp.app.styledesign;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.terasoluna.qp.domain.model.ProjectItem;
import org.terasoluna.qp.domain.service.project.ProjectItemSetting;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class StyleDesignFunction {
	public static List<ProjectItemSetting> readJson(String stringJson) {
		JsonFactory json = new JsonFactory();
		List<ProjectItemSetting> listProjectItem = new ArrayList<ProjectItemSetting>();
		ObjectMapper mapper = new ObjectMapper(json);
		try {
			listProjectItem = mapper.readValue(stringJson, TypeFactory.defaultInstance().constructCollectionType(List.class, ProjectItemSetting.class));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return listProjectItem;
	}
	
	public static List<ProjectItem> parseToListProjectItem(List<ProjectItemSetting> listProjectItemSetting, Long projectId) {
		List<ProjectItem> listProjectItem = new ArrayList<ProjectItem>();
		
		for (int i = 0; i < listProjectItemSetting.size(); i++) {
			ProjectItemSetting projectItemSetting = listProjectItemSetting.get(i);
			ProjectItem projectItem = new ProjectItem();
			
			projectItem.setProjectId(projectId);
			projectItem.setMessageCode(projectItemSetting.getLabelName());
			projectItem.setMessageString(projectItemSetting.getLabelNameAutocomplete());
			projectItem.setStyle(projectItemSetting.getStyle());
			projectItem.setHoverStyle(projectItemSetting.getHoverStyle());
			projectItem.setItemPosition(Integer.parseInt(projectItemSetting.getPosition()));
			projectItem.setItemType(Integer.parseInt(projectItemSetting.getType()));
			
			if (projectItemSetting.getConvertTo() != null && projectItemSetting.getConvertTo() != "") {
				projectItem.setComponentType(Integer.parseInt(projectItemSetting.getConvertTo()));
			} else {
				projectItem.setComponentType(0);
			}
			
			if (projectItemSetting.getModuleId() != null && projectItemSetting.getModuleId() != "") {
				projectItem.setModuleId(Long.parseLong(projectItemSetting.getModuleId()));
			} else {
				projectItem.setModuleId(0);
			}
			
			if (projectItemSetting.getNavigateTo() != null && projectItemSetting.getNavigateTo() != "") {
				projectItem.setScreenId(Long.parseLong(projectItemSetting.getNavigateTo()));
			} else {
				projectItem.setScreenId(0);
			}
			
			listProjectItem.add(projectItem);
		}
		
		return listProjectItem;
	}
	
	public static List<ProjectItemSetting> parseToListProjectItemSetting(List<ProjectItem> listProjectItem) {
		List<ProjectItemSetting> listProjectItemSetting = new ArrayList<ProjectItemSetting>();
		
		for (int i = 0; i < listProjectItem.size(); i++) {
			ProjectItem projectItem = listProjectItem.get(i);
			ProjectItemSetting projectItemSetting = new ProjectItemSetting();
			
			projectItemSetting.setLabelNameAutocomplete(projectItem.getMessageString());
			projectItemSetting.setLabelName(projectItem.getMessageCode());
			projectItemSetting.setStyle(projectItem.getStyle());
			projectItemSetting.setHoverStyle(projectItem.getHoverStyle());
			projectItemSetting.setPosition(Integer.toString(projectItem.getItemPosition()));
			projectItemSetting.setType(Integer.toString(projectItem.getItemType()));
			projectItemSetting.setConvertTo(Integer.toString(projectItem.getComponentType()));
			projectItemSetting.setModuleIdAutocomplete(projectItem.getModuleName());
			projectItemSetting.setModuleId(Long.toString(projectItem.getModuleId()));
			projectItemSetting.setNavigateToAutocomplete(projectItem.getScreenName());
			projectItemSetting.setNavigateTo(Long.toString(projectItem.getScreenId()));
			
			listProjectItemSetting.add(projectItemSetting);
		}
		
		return listProjectItemSetting;
	}
	
	public static JSONArray parseToJson(List<ProjectItemSetting> listProjectItemSetting) {
		JSONArray jsonArray = new JSONArray();
		
		for (int i = 0; i < listProjectItemSetting.size(); i++) {
			JSONObject tempJsonObject = new JSONObject();
			ProjectItemSetting projectItemSetting = listProjectItemSetting.get(i);
			
			tempJsonObject.put("type", projectItemSetting.getType());
			tempJsonObject.put("position", projectItemSetting.getPosition());
			tempJsonObject.put("hoverStyle", projectItemSetting.getHoverStyle());
			tempJsonObject.put("style", projectItemSetting.getStyle());
			tempJsonObject.put("labelNameAutocomplete", projectItemSetting.getLabelNameAutocomplete());
			tempJsonObject.put("labelName", projectItemSetting.getLabelName());
			tempJsonObject.put("convertTo", projectItemSetting.getConvertTo());
			tempJsonObject.put("moduleIdAutocomplete", projectItemSetting.getModuleIdAutocomplete());
			tempJsonObject.put("moduleId", projectItemSetting.getModuleId());
			tempJsonObject.put("navigateToAutocomplete", projectItemSetting.getNavigateToAutocomplete());
			tempJsonObject.put("navigateTo", projectItemSetting.getNavigateTo());
			
			jsonArray.add(tempJsonObject);
		}
		
		return jsonArray;
	}
	
	public static Map<String, String> getProjectStyle(Map<String, String> mapTheme) {
		// TODO Auto-generated method stub
		
		String panelHeader = "";
		String panelBody = "";
		String panelListTable = "";
		String panelListTh = "";
		String panelListTdText = "";
		String panelListTdNumeric = "";
		String panelListTdDate = "";
		String panelListTdDateTime = "";
		String panelListTdNoNumber = "";
		String panelListTdActionColumn = "";
		String panelTableForm = "";
		String panelTableFormTh = "";
		String panelTableFormTd = "";
		String screenStyle = "";
		String menuBgColor = "";
		String menuBrandColor = "";
		String menuBrandSize = "";
		String menuTextColor = "";
		
		String menuSelectedBgColor = "";
		
		String menuStyle = "";
		String menuSelectedStyle = "";
		
		String menuItemStyle = "";
		String menuItemHoverStyle = "";
		String menuItemBgHoverStyle = "";
		String footerStyle = "";
		String logo = "";
		String logoPosition = "";
		/*String logoWidthHeight = "";*/
		
		String width = "";
		String height = "";
		String backgroundColor = "";
		String commonButtonBgColor = "";
		String commonButtonBgActiveColor = "";
		String commonButtonTextColor = "";
		
		String commonButtonDeleteBgColor = "";
		String commonButtonDeleteBgActiveColor = "";
		String commonButtonDeleteTextColor = "";
		
		String clientButtonDeleteBgColor = "";
		String clientButtonDeleteBgActiveColor = "";
		String clientButtonDeleteTextColor = "";
		
		String commonLinkPopupTextColor = "";
		
		String headerTitleColor = "";
		String headerTitleSize = "";
		String headerTitlePosition = "";
		
		String headerLinkPosition = "";
		String headerLinkColor = "";
		String headerLinkFontSize = "";
		
		String panelListThBackgroundColor = "";
		String panelListThFontSize = "";
		
		String panelListTableBorderSpacing = "";
		String panelTableFormTableBorderSpacing = "";
		
		for(Map.Entry<String, String> entry: mapTheme.entrySet()) {
			if (entry.getKey().indexOf("panel-header-") == 0) {
				panelHeader += entry.getKey().replace("panel-header-", "") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().indexOf("panel-body-") == 0) {
				panelBody += entry.getKey().replace("panel-body-", "") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().indexOf("panel-list-table-") == 0) {
				panelListTable += entry.getKey().replace("panel-list-table-", "") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().indexOf("panel-list-th-") == 0) {
				panelListTh += entry.getKey().replace("panel-list-th-", "") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().indexOf("panel-list-td-text") == 0) {
				panelListTdText += entry.getKey().replace("panel-list-td-text", "text-align") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("panel-list-td-numeric")) {
				panelListTdNumeric += entry.getKey().replace("panel-list-td-numeric", "text-align") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("panel-list-td-date")) {
				panelListTdDate += entry.getKey().replace("panel-list-td-date", "text-align") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("panel-list-td-date-time")) {
				panelListTdDateTime += entry.getKey().replace("panel-list-td-date-time", "text-align") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("panel-list-td-no-number")) {
				panelListTdNoNumber += entry.getKey().replace("panel-list-td-no-number", "text-align") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("panel-list-td-action-column")) {
				panelListTdActionColumn += entry.getKey().replace("panel-list-td-action-column", "text-align") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().indexOf("panel-table-form-table-") == 0) {
				panelTableForm += entry.getKey().replace("panel-table-form-table-", "") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().indexOf("panel-table-form-th-") == 0) {
				panelTableFormTh += entry.getKey().replace("panel-table-form-th-", "") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().indexOf("panel-table-form-td-") == 0) {
				panelTableFormTd += entry.getKey().replace("panel-table-form-td-", "") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("common-screen-size")) {
				screenStyle += entry.getKey().replace("common-screen-size", "width") + ":" + entry.getValue() + "; ";
				
			}  else if (entry.getKey().equals("common-font-family")) {
				screenStyle += entry.getKey().replace("common-font-family", "font-family") + ":" + entry.getValue() + "; ";
				
			}  else if (entry.getKey().equals("common-font-size")) {
				screenStyle += entry.getKey().replace("common-font-size", "font-size") + ":" + entry.getValue() + "; ";
				
			}  else if (entry.getKey().equals("commom-background-image")) {
				backgroundColor += entry.getKey().replace("commom-background-image", "background-image") + ":url(data:image/jpeg;base64," + entry.getValue() + "); ";
				
			}  else if (entry.getKey().equals("common-background-color")) {
				backgroundColor += entry.getKey().replace("common-background-color", "background-color") + ":" + entry.getValue() + "; ";
				
			}  else if (entry.getKey().equals("menu-bg-color")) {
				menuBgColor += entry.getValue();
				
			}  else if (entry.getKey().equals("menu-brand-color")) {
				menuBrandColor += entry.getKey().replace("menu-brand-color", "color") + ":" + entry.getValue() + "; ";
				
			}  else if (entry.getKey().equals("menu-brand-size")) {
				menuBrandSize += entry.getKey().replace("menu-brand-size", "font-size") + ":" + entry.getValue() + "; ";
				
			}  else if (entry.getKey().equals("menu-text-color")) {
				menuTextColor += entry.getKey().replace("menu-text-color", "color") + ":" + entry.getValue() + "; ";
				
			}  else if (entry.getKey().equals("menu-font-color")) {
				menuStyle += entry.getKey().replace("menu-font-color", "color") + ":" + entry.getValue() + "; ";
				
			}  else if (entry.getKey().equals("menu-font-size")) {
				menuStyle += entry.getKey().replace("menu-font-size", "font-size") + ":" + entry.getValue() + "; ";
				
			}  else if (entry.getKey().equals("menu-selected-bg-color")) {
				menuSelectedBgColor += entry.getValue();
				
			}  else if (entry.getKey().equals("menu-selected-text-color")) {
				menuSelectedStyle += entry.getKey().replace("menu-selected-text-color", "color") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("item-menu-bg-color")) {
				menuItemStyle += entry.getKey().replace("item-menu-bg-color", "background-color") + ":" + entry.getValue() + "; ";
					
			} else if (entry.getKey().equals("item-menu-font-color")) {
				menuItemStyle += entry.getKey().replace("item-menu-font-color", "color") + ":" + entry.getValue() + "; ";
					
			} else if (entry.getKey().equals("item-menu-font-size")) {
				menuItemStyle += entry.getKey().replace("item-menu-font-size", "font-size") + ":" + entry.getValue() + "; ";
					
			} else if (entry.getKey().equals("item-menu-hover-bg-color")) {
				menuItemBgHoverStyle += entry.getValue();
					
			} else if (entry.getKey().equals("item-menu-hover-text-color")) {
				menuItemHoverStyle += entry.getKey().replace("item-menu-hover-text-color", "color") + ":" + entry.getValue() + "; ";
					
			} else if (entry.getKey().equals("footer-text-color")) {
				footerStyle += entry.getKey().replace("footer-text-color", "color") + ":" + entry.getValue() + "; ";
					
			} else if (entry.getKey().equals("footer-text-size")) {
				footerStyle += entry.getKey().replace("footer-text-size", "font-size") + ":" + entry.getValue() + "; ";
					
			} else if (entry.getKey().equals("logo")) {
				logo += entry.getValue();
					
			} else if (entry.getKey().equals("panel-table-list-td-anotherChild-position")) {
				logoPosition += entry.getValue();
					
			} else if (entry.getKey().equals("panel-table-list-td-firstChild-width")) {
				width += entry.getValue();
					
			} else if (entry.getKey().equals("panel-table-list-td-firstChild-height")) {
				height += entry.getValue();
					
			} else if (entry.getKey().equals("common-button-bg-color")) {
				commonButtonBgColor += entry.getKey().replace("common-button-bg-color", "background-color") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("common-button-bg-active-color")) {
				commonButtonBgActiveColor += entry.getKey().replace("common-button-bg-active-color", "background-color") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("common-button-text-color")) {
				commonButtonTextColor += entry.getKey().replace("common-button-text-color", "color") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("common-button-delete-bg-color")) {
				commonButtonDeleteBgColor += entry.getKey().replace("common-button-delete-bg-color", "background-color") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("common-button-delete-bg-active-color")) {
				commonButtonDeleteBgActiveColor += entry.getKey().replace("common-button-delete-bg-active-color", "background-color") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("common-button-delete-text-color")) {
				commonButtonDeleteTextColor += entry.getKey().replace("common-button-delete-text-color", "color") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("client-button-delete-bg-color")) {
				clientButtonDeleteBgColor += entry.getKey().replace("client-button-delete-bg-color", "background-color") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("client-button-delete-bg-active-color")) {
				clientButtonDeleteBgActiveColor += entry.getKey().replace("client-button-delete-bg-active-color", "background-color") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("client-button-delete-text-color")) {
				clientButtonDeleteTextColor += entry.getKey().replace("client-button-delete-text-color", "color") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("common-link-popup-text-color")) {
				commonLinkPopupTextColor += entry.getKey().replace("common-link-popup-text-color", "color") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("header-title-color")) {
				headerTitleColor += entry.getKey().replace("header-title-color", "color") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("header-title-size")) {
				headerTitleSize += entry.getKey().replace("header-title-size", "font-size") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("header-title-position")) {
				headerTitlePosition += entry.getKey().replace("header-title-position", "position") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("header-link-position")) {
				headerLinkPosition += entry.getKey().replace("header-link-position", "position") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("header-link-color")) {
				headerLinkColor += entry.getKey().replace("header-link-color", "color") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("header-link-font-size")) {
				headerLinkFontSize += entry.getKey().replace("header-link-font-size", "font-size") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("panel-list-th-background-color")) {
				panelListThBackgroundColor += entry.getKey().replace("panel-list-th-background-color", "color") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("panel-list-th-font-size")) {
				panelListThFontSize += entry.getKey().replace("panel-list-th-font-size", "font-size") + ":" + entry.getValue() + "; ";
			}
			
			if (entry.getKey().equals("panel-table-form-table-border-spacing") && !StringUtils.isEmpty(entry.getValue())) {
				panelTableFormTableBorderSpacing += entry.getKey().replace("panel-table-form-table-border-spacing", "border-spacing") + ":" + entry.getValue() + "; ";
			} 
			if (entry.getKey().equals("panel-list-table-border-spacing") && !StringUtils.isEmpty(entry.getValue())) {
				panelListTableBorderSpacing += entry.getKey().replace("panel-list-table-border-spacing", "border-spacing") + ":" + entry.getValue() + "; ";
			} 
			
		}
		
		mapTheme.put("logo", logo);
		
		if (StringUtils.isEmpty(logoPosition)) {
			logoPosition = "right";
		}
		
		mapTheme.put("logoPosition", logoPosition);
		
		if (height.length() > 0) {
			height = "height: " + height + ";";
		} else {
			height = "height: 25px;";
		}
		
		if (width.length() > 0) {
			width = "width: " + width + ";";
		}
		
		mapTheme.put("backgroundColor", backgroundColor);
		mapTheme.put("logoWidth", width);
		mapTheme.put("logoHeight", height);
		
		mapTheme.put("footerStyle", footerStyle);
		mapTheme.put("menuItemBgHoverStyle", menuItemBgHoverStyle);
		mapTheme.put("menuSelectedBgColor", menuSelectedBgColor);
		
		mapTheme.put("menuItemStyle", menuItemStyle);
		mapTheme.put("menuItemHoverStyle", menuItemHoverStyle);
		
		mapTheme.put("menuStyle", menuStyle);
		mapTheme.put("menuSelectedStyle", menuSelectedStyle);
		
		mapTheme.put("menuBgColor", menuBgColor);
		mapTheme.put("menuBrandColor", menuBrandColor);
		mapTheme.put("menuBrandSize", menuBrandSize);
		mapTheme.put("menuTextColor", menuTextColor);
		
		mapTheme.put("panelHeader", panelHeader);
		mapTheme.put("panelBody", panelBody);
		
		mapTheme.put("panelListTable", panelListTable);
		mapTheme.put("panelListTh", panelListTh);
		mapTheme.put("panelListTdText", panelListTdText);
		
		mapTheme.put("panelListTdNumeric", panelListTdNumeric);
		mapTheme.put("panelListTdDate", panelListTdDate);
		mapTheme.put("panelListTdDateTime", panelListTdDateTime);
		mapTheme.put("panelListTdNoNumber", panelListTdNoNumber);
		mapTheme.put("panelListTdActionColumn", panelListTdActionColumn);
		
		mapTheme.put("panelTableForm", panelTableForm);
		mapTheme.put("panelTableFormTh", panelTableFormTh);
		mapTheme.put("panelTableFormTd", panelTableFormTd);
		
		mapTheme.put("commonButtonBgColor", commonButtonBgColor);
		mapTheme.put("commonButtonBgActiveColor", commonButtonBgActiveColor);
		mapTheme.put("commonButtonTextColor", commonButtonTextColor);
		
		mapTheme.put("clientButtonDeleteBgColor", clientButtonDeleteBgColor);
		mapTheme.put("clientButtonDeleteBgActiveColor", clientButtonDeleteBgActiveColor);
		mapTheme.put("clientButtonDeleteTextColor", clientButtonDeleteTextColor);
		
		mapTheme.put("commonButtonDeleteBgColor", commonButtonDeleteBgColor);
		mapTheme.put("commonButtonDeleteBgActiveColor", commonButtonDeleteBgActiveColor);
		mapTheme.put("commonButtonDeleteTextColor", commonButtonDeleteTextColor);
		
		mapTheme.put("commonLinkPopupTextColor", commonLinkPopupTextColor);
		
		mapTheme.put("headerTitleColor", headerTitleColor);
		mapTheme.put("headerTitleSize", headerTitleSize);
		mapTheme.put("headerTitlePosition", headerTitlePosition);
		
		mapTheme.put("headerLinkPosition", headerLinkPosition);
		mapTheme.put("headerLinkColor", headerLinkColor);
		mapTheme.put("headerLinkFontSize", headerLinkFontSize);
		
		mapTheme.put("panelListThBackgroundColor", panelListThBackgroundColor);
		mapTheme.put("panelListThFontSize", panelListThFontSize);
		
		mapTheme.put("panelListTableBorderSpacing", panelListTableBorderSpacing);
		mapTheme.put("panelTableFormTableBorderSpacing", panelTableFormTableBorderSpacing);
		
		mapTheme.put("screenStyle", screenStyle);
		return mapTheme;
	}
}
