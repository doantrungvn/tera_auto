package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class ScreenDesign implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long screenId;

	private String screenCode;
	
	private String screenName;

	private Long projectId;
	
	private MessageDesign messageDesign;
	
	private Long moduleId;

	private String screenUrlCode;

	private Integer screenPatternType;

	private Integer templateType;

	private String moduleName;
	
	private Integer xCoordinate;

	private Integer yCoordinate;

	private Long createdBy;
	
	private Timestamp createdDate;
	
	private Long updatedBy;
	
	private Timestamp updatedDate; 
	
	private Long firstFormOfScreen;
	
	private ScreenForm[] screenForms;
	
	private ScreenArea[] screenAreas;
	
	private ScreenGroupItem[] screenGroupItems; 
	
	private ScreenItem[] screenItems;
	
	private ScreenParameter[] arrScreenParameter;
	
	private String screenParameters;
	
	private String remark;
	
	private Timestamp sysDatetime;
	
	private Integer designMode;
	
	//truonglv add
	private Long languageId;
	
	//truonglv add
	private String jsonConnector;
	
	//truonglv add
	private String moduleCode;
	
	private String screenTypeName;
	
	private Integer designStatus;
	
	private Integer confirmationType;
	
	private Integer completionType;
	
	List<ScreenDesign> listScreenActionParameter;

	//TungHT
	private List<ScreenItem> headerLinkItems;
	
	private Long functionDesignId;
	
	private int functionDesignType;
	
	private String functionDesignName;
	
	//VinhHV add
	private String businessTypeName;
	
	private Integer openOwner;
	
	private Boolean enableHomePage;
	
	private String businessLogicCode;
	
	private List<BusinessDesign> businessInitsDefault;
	
	private List<BusinessDesign> businessProcessesDefault;
	
	// daipv
	private String businessLogicId;
	
	private String screenIdTemp;
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public ScreenDesign() {

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

	public MessageDesign getMessageDesign() {
		return messageDesign;
	}

	public void setMessageDesign(MessageDesign messageDesign) {
		this.messageDesign = messageDesign;
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

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
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

	/**
	 * @return the xCoordinate
	 */
	public Integer getxCoordinate() {
		return xCoordinate;
	}

	/**
	 * @param xCoordinate the xCoordinate to set
	 */
	public void setxCoordinate(Integer xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	/**
	 * @return the yCoordinate
	 */
	public Integer getyCoordinate() {
		return yCoordinate;
	}

	/**
	 * @param yCoordinate the yCoordinate to set
	 */
	public void setyCoordinate(Integer yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	
	/**
	 * @return the projectId
	 */
	public Long getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Timestamp getSysDatetime() {
		return sysDatetime;
	}

	public void setSysDatetime(Timestamp sysDatetime) {
		this.sysDatetime = sysDatetime;
	}

	/**
	 * @return the arrScreenParameter
	 */
	public ScreenParameter[] getArrScreenParameter() {
		return arrScreenParameter;
	}

	/**
	 * @param arrScreenParameter the arrScreenParameter to set
	 */
	public void setArrScreenParameter(ScreenParameter[] arrScreenParameter) {
		this.arrScreenParameter = arrScreenParameter;
	}

	/**
	 * @return the screenParameters
	 */
	public String getScreenParameters() {
		return screenParameters;
	}

	/**
	 * @param screenParameters the screenParameters to set
	 */
	public void setScreenParameters(String screenParameters) {
		this.screenParameters = screenParameters;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getJsonConnector() {
		return jsonConnector;
	}

	public void setJsonConnector(String jsonConnector) {
		this.jsonConnector = jsonConnector;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getScreenTypeName() {
	    return screenTypeName;
    }

	public void setScreenTypeName(String screenTypeName) {
	    this.screenTypeName = screenTypeName;
    }

	public Integer getDesignStatus() {
		return designStatus;
	}

	public void setDesignStatus(Integer designStatus) {
		this.designStatus = designStatus;
	}

	public Integer getDesignMode() {
		return designMode;
	}

	public void setDesignMode(Integer designMode) {
		this.designMode = designMode;
	}

	public List<ScreenItem> getHeaderLinkItems() {
		return headerLinkItems;
	}

	public void setHeaderLinkItems(List<ScreenItem> headerLinkItems) {
		this.headerLinkItems = headerLinkItems;
	}

	public Integer getConfirmationType() {
	    return confirmationType;
    }

	public void setConfirmationType(Integer confirmationType) {
	    this.confirmationType = confirmationType;
    }

	public Integer getCompletionType() {
	    return completionType;
    }

	public void setCompletionType(Integer completionType) {
	    this.completionType = completionType;
    }
	

	public List<ScreenDesign> getListScreenActionParameter() {
		return listScreenActionParameter;
	}

	public void setListScreenActionParameter(
			List<ScreenDesign> listScreenActionParameter) {
		this.listScreenActionParameter = listScreenActionParameter;
	}

	public Long getFunctionDesignId() {
		return functionDesignId;
	}

	public void setFunctionDesignId(Long functionDesignId) {
		this.functionDesignId = functionDesignId;
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

	public Integer getOpenOwner() {
		return openOwner;
	}

	public void setOpenOwner(Integer openOwner) {
		this.openOwner = openOwner;
	}

	public Long getFirstFormOfScreen() {
	    return firstFormOfScreen;
    }

	public void setFirstFormOfScreen(Long firstFormOfScreen) {
	    this.firstFormOfScreen = firstFormOfScreen;
    }
	
	public String getBusinessTypeName() {
	    return businessTypeName;
    }

	public void setBusinessTypeName(String businessTypeName) {
	    this.businessTypeName = businessTypeName;
    }

	public Boolean getEnableHomePage() {
		return enableHomePage;
	}

	public void setEnableHomePage(Boolean enableHomePage) {
		this.enableHomePage = enableHomePage;
	}

	public String getBusinessLogicCode() {
		return businessLogicCode;
	}

	public void setBusinessLogicCode(String businessLogicCode) {
		this.businessLogicCode = businessLogicCode;
	}

	public String getBusinessLogicId() {
		return businessLogicId;
	}

	public void setBusinessLogicId(String businessLogicId) {
		this.businessLogicId = businessLogicId;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getScreenIdTemp() {
		return screenIdTemp;
	}

	public void setScreenIdTemp(String screenIdTemp) {
		this.screenIdTemp = screenIdTemp;
	}

	public List<BusinessDesign> getBusinessInitsDefault() {
		return businessInitsDefault;
	}

	public void setBusinessInitsDefault(List<BusinessDesign> businessInitsDefault) {
		this.businessInitsDefault = businessInitsDefault;
	}

	public List<BusinessDesign> getBusinessProcessesDefault() {
		return businessProcessesDefault;
	}

	public void setBusinessProcessesDefault(List<BusinessDesign> businessProcessesDefault) {
		this.businessProcessesDefault = businessProcessesDefault;
	}
	
}
