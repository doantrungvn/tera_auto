package org.terasoluna.qp.app.screendesign;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.terasoluna.qp.domain.model.MessageDesign;
import org.terasoluna.qp.domain.model.ScreenArea;
import org.terasoluna.qp.domain.model.ScreenForm;
import org.terasoluna.qp.domain.model.ScreenGroupItem;
import org.terasoluna.qp.domain.model.ScreenItem;

public class ScreenDesignItemForm implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long screenId;

	private String screenCode;
	
	private String screenName;
	
	private Integer messageDesignId;

	private MessageDesign messageDesign;
	
	private Long moduleId;

	private String screenUrlCode;

	private Integer screenPatternType;

	private Integer templateType;

	private String moduleName;

	private ScreenForm[] screenForms;
	
	private ScreenArea[] screenAreas;
	
	private ScreenGroupItem[] screenGroupItems; 
	
	private ScreenItem[] screenItems;
	
	private Map<Long, ScreenGroupItem> screenGroups = new Hashtable<Long, ScreenGroupItem>();
	
	private Boolean error = false;
	
	private Long projectId;
	
	private Long tableMappingId;
	
	private List<ScreenItem> headerLinkItems;
	private List<ScreenArea> areaNonGroup;
	private Timestamp updatedDate; 
	
	private String screenParameters;
	
	private String remark;
	
	private ScreenItemForm [] screenItemForms;
	
	public Long getScreenId() {
		return screenId;
	}

	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}

	public String getScreenCode() {
		return screenCode;
	}

	public void setScreenCode(String screenCode) {
		this.screenCode = screenCode;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public Integer getMessageDesignId() {
		return messageDesignId;
	}

	public void setMessageDesignId(Integer messageDesignId) {
		this.messageDesignId = messageDesignId;
	}

	public MessageDesign getMessageDesign() {
		return messageDesign;
	}

	public void setMessageDesign(MessageDesign messageDesign) {
		this.messageDesign = messageDesign;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getScreenUrlCode() {
		return screenUrlCode;
	}

	public void setScreenUrlCode(String screenUrlCode) {
		this.screenUrlCode = screenUrlCode;
	}

	public Integer getScreenPatternType() {
		return screenPatternType;
	}

	public void setScreenPatternType(Integer screenPatternType) {
		this.screenPatternType = screenPatternType;
	}

	public Integer getTemplateType() {
		return templateType;
	}

	public void setTemplateType(Integer templateType) {
		this.templateType = templateType;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public ScreenForm[] getScreenForms() {
		return screenForms;
	}

	public void setScreenForms(ScreenForm[] screenForms) {
		this.screenForms = screenForms;
	}

	public ScreenArea[] getScreenAreas() {
		return screenAreas;
	}

	public void setScreenAreas(ScreenArea[] screenAreas) {
		this.screenAreas = screenAreas;
	}

	public ScreenGroupItem[] getScreenGroupItems() {
		return screenGroupItems;
	}

	public void setScreenGroupItems(ScreenGroupItem[] screenGroupItems) {
		this.screenGroupItems = screenGroupItems;
	}

	public ScreenItem[] getScreenItems() {
		return screenItems;
	}

	public void setScreenItems(ScreenItem[] screenItems) {
		this.screenItems = screenItems;
	}

	public Map<Long, ScreenGroupItem> getScreenGroups() {
		return screenGroups;
	}

	public void setScreenGroups(Map<Long, ScreenGroupItem> screenGroups) {
		this.screenGroups = screenGroups;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public List<ScreenItem> getHeaderLinkItems() {
		return headerLinkItems;
	}

	public void setHeaderLinkItems(List<ScreenItem> headerLinkItems) {
		this.headerLinkItems = headerLinkItems;
	}

	public List<ScreenArea> getAreaNonGroup() {
		return areaNonGroup;
	}

	public void setAreaNonGroup(List<ScreenArea> areaNonGroup) {
		this.areaNonGroup = areaNonGroup;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getTableMappingId() {
		return tableMappingId;
	}

	public void setTableMappingId(Long tableMappingId) {
		this.tableMappingId = tableMappingId;
	}

	public String getScreenParameters() {
		return screenParameters;
	}

	public void setScreenParameters(String screenParameters) {
		this.screenParameters = screenParameters;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public ScreenItemForm[] getScreenItemForms() {
		return screenItemForms;
	}

	public void setScreenItemForms(ScreenItemForm[] screenItemForms) {
		this.screenItemForms = screenItemForms;
	}

	

}
