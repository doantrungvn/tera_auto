package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.ScreenDesignMessageConst;

public class ScreenDesignForJSP implements Serializable {
	
	@NotEmpty(message=ScreenDesignMessageConst.SC_SCREENDESIGN_0005)
	@Pattern(regexp = CommonMessageConst.PATTERN_FOR_NAME, message = ScreenDesignMessageConst.SC_SCREENDESIGN_0005+";" + CommonMessageConst.CODE_INPUTMASK)
	@Size(min=DbDomainConst.MIN_VAL_INPUT,max = DbDomainConst.MAX_LENGTH_OF_CODE,message=ScreenDesignMessageConst.SC_SCREENDESIGN_0005 + ";" + DbDomainConst.MIN_VAL_INPUT +";" + DbDomainConst.MAX_LENGTH_OF_NAME)
	private String screenName;

	private static final long serialVersionUID = 1L;

	private Long screenId;

	private String screenCode;

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
	
	private String functionDesignId;
	
	private int functionDesignType;
	
	private String functionDesignName;
	
	//VinhHV add
	private String businessTypeName;
	
	private Integer openOwner;
	
	private List<ScreenArea> areaNonGroup;
	
	private Map<Long, ScreenGroupItem> screenGroups = new Hashtable<Long, ScreenGroupItem>();
	
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

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getFunctionDesignId() {
		return functionDesignId;
	}

	public void setFunctionDesignId(String functionDesignId) {
		this.functionDesignId = functionDesignId;
	}

	public List<ScreenArea> getAreaNonGroup() {
		return areaNonGroup;
	}

	public void setAreaNonGroup(List<ScreenArea> areaNonGroup) {
		this.areaNonGroup = areaNonGroup;
	}

	public Map<Long, ScreenGroupItem> getScreenGroups() {
		return screenGroups;
	}

	public void setScreenGroups(Map<Long, ScreenGroupItem> screenGroups) {
		this.screenGroups = screenGroups;
	}



}
