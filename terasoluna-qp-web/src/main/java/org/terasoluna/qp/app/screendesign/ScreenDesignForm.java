package org.terasoluna.qp.app.screendesign;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.terasoluna.qp.domain.model.MessageDesign;
import org.terasoluna.qp.domain.model.ScreenArea;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.model.ScreenForm;
import org.terasoluna.qp.domain.model.ScreenGroupItem;
import org.terasoluna.qp.domain.model.ScreenItem;

public class ScreenDesignForm implements Serializable {

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
	
	private Integer designMode;
	
	private Integer designStatus;
	
	private Integer openOwner;
	
	private int sourceForm;
	
	private String functionDesignId;
	
	private String functionDesignIdAutocomplete;
	
	private String functionDesignName;
	
	private int functionDesignType;
	
	private List<ScreenDesign> listScreenChangeParameter;
	
	private Boolean enableHomePage;
	
	public Integer getDesignStatus() {
		return designStatus;
	}

	public void setDesignStatus(Integer designStatus) {
		this.designStatus = designStatus;
	}

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

	public Integer getDesignMode() {
		return designMode;
	}

	public void setDesignMode(Integer designMode) {
		this.designMode = designMode;
	}

	public Integer getOpenOwner() {
		return openOwner;
	}

	public void setOpenOwner(Integer openOwner) {
		this.openOwner = openOwner;
	}

	public int getSourceForm() {
		return sourceForm;
	}

	public void setSourceForm(int sourceForm) {
		this.sourceForm = sourceForm;
	}

	public List<ScreenDesign> getListScreenChangeParameter() {
		return listScreenChangeParameter;
	}

	public void setListScreenChangeParameter(
			List<ScreenDesign> listScreenChangeParameter) {
		this.listScreenChangeParameter = listScreenChangeParameter;
	}

	public int getFunctionDesignType() {
		return functionDesignType;
	}

	public void setFunctionDesignType(int functionDesignType) {
		this.functionDesignType = functionDesignType;
	}

	public String getFunctionDesignName() {
	    return functionDesignName;
    }

	public void setFunctionDesignName(String functionDesignName) {
	    this.functionDesignName = functionDesignName;
    }

	public String getFunctionDesignId() {
		return functionDesignId;
	}

	public void setFunctionDesignId(String functionDesignId) {
		this.functionDesignId = functionDesignId;
	}

	public String getFunctionDesignIdAutocomplete() {
		return functionDesignIdAutocomplete;
	}

	public void setFunctionDesignIdAutocomplete(
			String functionDesignIdAutocomplete) {
		this.functionDesignIdAutocomplete = functionDesignIdAutocomplete;
	}

	public Boolean getEnableHomePage() {
		return enableHomePage;
	}

	public void setEnableHomePage(Boolean enableHomePage) {
		this.enableHomePage = enableHomePage;
	}



}
