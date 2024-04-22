package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class BusinessDesign implements Serializable {


	private static final long serialVersionUID = 1L;

	private Long businessLogicId;

	private String businessLogicCode;

	private String businessLogicName;

	private String businessLogicIdAutocomplete;

	private String businessTypeId;

	private String businessTypeName;

	private String businessTypeCode;

	private Integer returnType;

	private Long screenId;

	private Long moduleId;

	private Integer moduleStatus;

	private String moduleIdAutocomplete;

	private String moduleName;

	private String moduleCode;

	private Long projectId;

	private Boolean customizeFlg;

	private Long uploadFileId;

	private byte[] file = new byte[0];

	private Long createdBy;

	private Timestamp createdDate;

	private Long updatedBy;

	private Timestamp updatedDate;

	private Timestamp sysDatetime;

	private String jsonInputBean;

	private String jsonOutputBean;

	private String jsonObjectDefinition;

	private String jsonConnector;

	private String jsonComponent;

	private String typeRegister;

	private String fileName;

	private Integer designStatus = 1;

	private String messageString;

	private Long functionDesignId;

	private String functionDesignCode;

	private String functionDesignIdAutocomplete;

	private String functionDesignName;

	private Integer moduleType;

	private List<InputBean> lstInputBean = new ArrayList<InputBean>();

	private List<OutputBean> lstOutputBean = new ArrayList<OutputBean>();

	private List<ObjectDefinition> lstObjectDefinition = new ArrayList<ObjectDefinition>();

	private List<SequenceLogic> lstSequenceLogics = new ArrayList<SequenceLogic>();

	private List<SequenceConnector> lstSequenceConnectors = new ArrayList<SequenceConnector>();

	private List<ItemValidation> lstItemValidations = new ArrayList<ItemValidation>();

	private List<BusinessDesign> lstAffectedBlogicCommon = new ArrayList<BusinessDesign>();
	
	private List<BusinessDesign> lstAffectedBlogicNavigator = new ArrayList<BusinessDesign>();

	private Boolean flagChangeFile;

	private String packageName;

	private String screenIdAutocomplete;

	private String screenCode;

	private Integer navigateType;

	private Integer patternType;

	private Boolean confirmFlg;

	private Boolean completeFlg;

	private Boolean clientCheckFlg;

	private String remark;

	private Long screenFormId;

	private String screenFormIdAutocomplete;

	private Boolean flagImpact = false;

	private Integer batchType;

	private List<ScreenItem> lstAffectedScreenItems = new ArrayList<ScreenItem>();

	/* For generate source code */
	private String strDetailsOfServiceImp;

	/* Attribute total cound and list content of pageable */
	private String attributeOfPageable;

	/* Message content for generate message content */
	private String strResultMsg;

	/* Setting model content */
	private String strModelSettingOfNavigator;

	/* Setting model content */
	private String strMethodOfAdvance;

	private Integer blogicType;

	private Integer requestMethod;

	private Boolean authenticatedFlg;

	/* Setting navigator content */
	private List<NavigatorComponent> lstNavigatorDetails = new ArrayList<NavigatorComponent>();

	/* For generate source code */
	private StringBuilder strSessionOutput = new StringBuilder("");
	private StringBuilder strSessionInput = new StringBuilder("");

    private Integer screenTemplateType;
    
    private String screenDesignCode;

    /* Setting mapping output to input two controller is difference */
    private String strOutMappingInContent = StringUtils.EMPTY;
    
    /* Setting data source of output form */
    private String strDataSourceContent;

    /* Setting model attribute from undo to register or modify */
    private String strUndoModelSetting;
    
	private List<OutputBean> lstDataSourceOutputBean = new ArrayList<OutputBean>();

	private List<OutputBean> lstNonDataSourceOutputBean = new ArrayList<OutputBean>();
	
	private Integer designMode = 1;
    
	private String bdDeclarFirstPage;
	
	private String bdDeclarLastPage;
	
	private String bdDeclarMapPage;


	/* Setting navigator content */
	private List<ExceptionComponent> lstExceptionDetails = new ArrayList<ExceptionComponent>();
	
	private String bdModelPageAttr;
	
	private Boolean showImpactFlag;
	
	private String author;

	private Integer httpStatus;

	private String httpStatusValue;
	
	public Boolean getFlagChangeFile() {
		return flagChangeFile;
	}

	public void setFlagChangeFile(Boolean flagChangeFile) {
		this.flagChangeFile = flagChangeFile;
	}

	public Long getBusinessLogicId() {
		return businessLogicId;
	}

	public void setBusinessLogicId(Long businessLogicId) {
		this.businessLogicId = businessLogicId;
	}

	public String getBusinessLogicCode() {
		return businessLogicCode;
	}

	public void setBusinessLogicCode(String businessLogicCode) {
		this.businessLogicCode = businessLogicCode;
	}

	public String getBusinessLogicName() {
		return businessLogicName;
	}

	public void setBusinessLogicName(String businessLogicName) {
		this.businessLogicName = businessLogicName;
	}

	public Long getScreenId() {
		return screenId;
	}

	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
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

	public Integer getReturnType() {
		return returnType;
	}

	public void setReturnType(Integer returnType) {
		this.returnType = returnType;
	}

	public String getJsonInputBean() {
		return jsonInputBean;
	}

	public void setJsonInputBean(String jsonInputBean) {
		this.jsonInputBean = jsonInputBean;
	}

	public String getJsonOutputBean() {
		return jsonOutputBean;
	}

	public void setJsonOutputBean(String jsonOutputBean) {
		this.jsonOutputBean = jsonOutputBean;
	}

	public String getJsonObjectDefinition() {
		return jsonObjectDefinition;
	}

	public void setJsonObjectDefinition(String jsonObjectDefinition) {
		this.jsonObjectDefinition = jsonObjectDefinition;
	}

	public List<InputBean> getLstInputBean() {
		return lstInputBean;
	}

	public void setLstInputBean(List<InputBean> lstInputBean) {
		this.lstInputBean = lstInputBean;
	}

	public String getModuleIdAutocomplete() {
		return moduleIdAutocomplete;
	}

	public void setModuleIdAutocomplete(String moduleIdAutocomplete) {
		this.moduleIdAutocomplete = moduleIdAutocomplete;
	}

	public List<OutputBean> getLstOutputBean() {
		return lstOutputBean;
	}

	public void setLstOutputBean(List<OutputBean> lstOutputBean) {
		this.lstOutputBean = lstOutputBean;
	}

	public List<ObjectDefinition> getLstObjectDefinition() {
		return lstObjectDefinition;
	}

	public void setLstObjectDefinition(List<ObjectDefinition> lstObjectDefinition) {
		this.lstObjectDefinition = lstObjectDefinition;
	}

	public Timestamp getSysDatetime() {
		return sysDatetime;
	}

	public void setSysDatetime(Timestamp sysDatetime) {
		this.sysDatetime = sysDatetime;
	}

	public String getJsonConnector() {
		return jsonConnector;
	}

	public void setJsonConnector(String jsonConnector) {
		this.jsonConnector = jsonConnector;
	}

	public String getJsonComponent() {
		return jsonComponent;
	}

	public void setJsonComponent(String jsonComponent) {
		this.jsonComponent = jsonComponent;
	}

	public String getTypeRegister() {
		return typeRegister;
	}

	public void setTypeRegister(String typeRegister) {
		this.typeRegister = typeRegister;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getUploadFileId() {
		return uploadFileId;
	}

	public void setUploadFileId(Long uploadFileId) {
		this.uploadFileId = uploadFileId;
	}

	public List<SequenceLogic> getLstSequenceLogics() {
		return lstSequenceLogics;
	}

	public void setLstSequenceLogics(List<SequenceLogic> lstSequenceLogics) {
		this.lstSequenceLogics = lstSequenceLogics;
	}

	public List<SequenceConnector> getLstSequenceConnectors() {
		return lstSequenceConnectors;
	}

	public void setLstSequenceConnectors(List<SequenceConnector> lstSequenceConnectors) {
		this.lstSequenceConnectors = lstSequenceConnectors;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Integer getDesignStatus() {
		return designStatus;
	}

	public void setDesignStatus(Integer designStatus) {
		this.designStatus = designStatus;
	}

	public List<ItemValidation> getLstItemValidations() {
		return lstItemValidations;
	}

	public void setLstItemValidations(List<ItemValidation> lstItemValidations) {
		this.lstItemValidations = lstItemValidations;
	}

	public String getMessageString() {
		return messageString;
	}

	public void setMessageString(String messageString) {
		this.messageString = messageString;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getScreenIdAutocomplete() {
		return screenIdAutocomplete;
	}

	public void setScreenIdAutocomplete(String screenIdAutocomplete) {
		this.screenIdAutocomplete = screenIdAutocomplete;
	}

	public Integer getPatternType() {
		return patternType;
	}

	public void setPatternType(Integer patternType) {
		this.patternType = patternType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getBusinessLogicIdAutocomplete() {
		return businessLogicIdAutocomplete;
	}

	public void setBusinessLogicIdAutocomplete(String businessLogicIdAutocomplete) {
		this.businessLogicIdAutocomplete = businessLogicIdAutocomplete;
	}

	public String getScreenCode() {
		return screenCode;
	}

	public void setScreenCode(String screenCode) {
		this.screenCode = screenCode;
	}

	public Integer getModuleStatus() {
		return moduleStatus;
	}

	public void setModuleStatus(Integer moduleStatus) {
		this.moduleStatus = moduleStatus;
	}

	public Long getScreenFormId() {
		return screenFormId;
	}

	public void setScreenFormId(Long screenFormId) {
		this.screenFormId = screenFormId;
	}

	public Boolean getFlagImpact() {
		return flagImpact;
	}

	public void setFlagImpact(Boolean flagImpact) {
		this.flagImpact = flagImpact;
	}

	public List<ScreenItem> getLstAffectedScreenItems() {
		return lstAffectedScreenItems;
	}

	public void setLstAffectedScreenItems(List<ScreenItem> lstAffectedScreenItems) {
		this.lstAffectedScreenItems = lstAffectedScreenItems;
	}

	public Long getFunctionDesignId() {
		return functionDesignId;
	}

	public void setFunctionDesignId(Long functionDesignId) {
		this.functionDesignId = functionDesignId;
	}

	public String getFunctionDesignIdAutocomplete() {
		return functionDesignIdAutocomplete;
	}

	public void setFunctionDesignIdAutocomplete(String functionDesignIdAutocomplete) {
		this.functionDesignIdAutocomplete = functionDesignIdAutocomplete;
	}

	/**
	 * @return the functionDesignName
	 */
	public String getFunctionDesignName() {
		return functionDesignName;
	}

	/**
	 * @param functionDesignName
	 *            the functionDesignName to set
	 */
	public void setFunctionDesignName(String functionDesignName) {
		this.functionDesignName = functionDesignName;
	}

	/**
	 * @return the businessTypeId
	 */
	public String getBusinessTypeId() {
		return businessTypeId;
	}

	/**
	 * @param businessTypeId
	 *            the businessTypeId to set
	 */
	public void setBusinessTypeId(String businessTypeId) {
		this.businessTypeId = businessTypeId;
	}

	/**
	 * @return the businessTypeName
	 */
	public String getBusinessTypeName() {
		return businessTypeName;
	}

	/**
	 * @param businessTypeName
	 *            the businessTypeName to set
	 */
	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}

	/**
	 * @return the businessTypeCode
	 */
	public String getBusinessTypeCode() {
		return businessTypeCode;
	}

	/**
	 * @param businessTypeCode
	 *            the businessTypeCode to set
	 */
	public void setBusinessTypeCode(String businessTypeCode) {
		this.businessTypeCode = businessTypeCode;
	}

	/**
	 * @return the navigateType
	 */
	public Integer getNavigateType() {
		return navigateType;
	}

	/**
	 * @param navigateType
	 *            the navigateType to set
	 */
	public void setNavigateType(Integer navigateType) {
		this.navigateType = navigateType;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public Integer getModuleType() {
		return moduleType;
	}

	public void setModuleType(Integer moduleType) {
		this.moduleType = moduleType;
	}

	public Integer getBatchType() {
		return batchType;
	}

	public void setBatchType(Integer batchType) {
		this.batchType = batchType;
	}

	public String getStrDetailsOfServiceImp() {
		return strDetailsOfServiceImp;
	}

	public void setStrDetailsOfServiceImp(String strDetailsOfServiceImp) {
		this.strDetailsOfServiceImp = strDetailsOfServiceImp;
	}

	/**
	 * @return the strResultMsg
	 */
	public String getStrResultMsg() {
		return strResultMsg;
	}

	/**
	 * @param strResultMsg the strResultMsg to set
	 */
	public void setStrResultMsg(String strResultMsg) {
		this.strResultMsg = strResultMsg;
	}

	/**
	 * @return the strModelSettingOfNavigator
	 */
	public String getStrModelSettingOfNavigator() {
		return strModelSettingOfNavigator;
	}

	/**
	 * @param strModelSettingOfNavigator the strModelSettingOfNavigator to set
	 */
	public void setStrModelSettingOfNavigator(String strModelSettingOfNavigator) {
		this.strModelSettingOfNavigator = strModelSettingOfNavigator;
	}

	public String getStrMethodOfAdvance() {
		return strMethodOfAdvance;
	}

	public void setStrMethodOfAdvance(String strMethodOfAdvance) {
		this.strMethodOfAdvance = strMethodOfAdvance;
	}

	public String getAttributeOfPageable() {
		return attributeOfPageable;
	}

	public void setAttributeOfPageable(String attributeOfPageable) {
		this.attributeOfPageable = attributeOfPageable;
	}

	/**
	 * @return the moduleCode
	 */
	public String getModuleCode() {
		return moduleCode;
	}

	/**
	 * @param moduleCode the moduleCode to set
	 */
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getFunctionDesignCode() {
		return functionDesignCode;
	}

	public void setFunctionDesignCode(String functionDesignCode) {
		this.functionDesignCode = functionDesignCode;
	}

	public Integer getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(Integer requestMethod) {
		this.requestMethod = requestMethod;
	}

	public Boolean getAuthenticatedFlg() {
		return authenticatedFlg;
	}

	public void setAuthenticatedFlg(Boolean authenticatedFlg) {
		this.authenticatedFlg = authenticatedFlg;
	}

	public Integer getBlogicType() {
		return blogicType;
	}

	public void setBlogicType(Integer blogicType) {
		this.blogicType = blogicType;
	}

	public Boolean getClientCheckFlg() {
		return clientCheckFlg;
	}

	public void setClientCheckFlg(Boolean clientCheckFlg) {
		this.clientCheckFlg = clientCheckFlg;
	}

	public Boolean getCompleteFlg() {
		return completeFlg;
	}

	public void setCompleteFlg(Boolean completeFlg) {
		this.completeFlg = completeFlg;
	}

	public Boolean getConfirmFlg() {
		return confirmFlg;
	}

	public void setConfirmFlg(Boolean confirmFlg) {
		this.confirmFlg = confirmFlg;
	}

	public Boolean getCustomizeFlg() {
		return customizeFlg;
	}

	public void setCustomizeFlg(Boolean customizeFlg) {
		this.customizeFlg = customizeFlg;
	}

	/**
	 * @return the lstNavigatorDetails
	 */
	public List<NavigatorComponent> getLstNavigatorDetails() {
		return lstNavigatorDetails;
	}

	/**
	 * @param lstNavigatorDetails the lstNavigatorDetails to set
	 */
	public void setLstNavigatorDetails(List<NavigatorComponent> lstNavigatorDetails) {
		this.lstNavigatorDetails = lstNavigatorDetails;
	}

	public StringBuilder getStrSessionOutput() {
		return strSessionOutput;
	}

	public void setStrSessionOutput(StringBuilder strSessionOutput) {
		this.strSessionOutput = strSessionOutput;
	}

	public StringBuilder getStrSessionInput() {
		return strSessionInput;
	}

	public void setStrSessionInput(StringBuilder strSessionInput) {
		this.strSessionInput = strSessionInput;
	}

	public Integer getScreenTemplateType() {
		return screenTemplateType;
	}

	public void setScreenTemplateType(Integer screenTemplateType) {
		this.screenTemplateType = screenTemplateType;
	}


	public String getScreenFormIdAutocomplete() {
	    return screenFormIdAutocomplete;
    }

	public void setScreenFormIdAutocomplete(String screenFormIdAutocomplete) {
	    this.screenFormIdAutocomplete = screenFormIdAutocomplete;
    }

	public String getScreenDesignCode() {
		return screenDesignCode;
	}

	public void setScreenDesignCode(String screenDesignCode) {
		this.screenDesignCode = screenDesignCode;
	}

	public List<BusinessDesign> getLstAffectedBlogicNavigator() {
		return lstAffectedBlogicNavigator;
	}

	public void setLstAffectedBlogicNavigator(
			List<BusinessDesign> lstAffectedBlogicNavigator) {
		this.lstAffectedBlogicNavigator = lstAffectedBlogicNavigator;
	}

	public List<BusinessDesign> getLstAffectedBlogicCommon() {
		return lstAffectedBlogicCommon;
	}

	public void setLstAffectedBlogicCommon(List<BusinessDesign> lstAffectedBlogicCommon) {
		this.lstAffectedBlogicCommon = lstAffectedBlogicCommon;
	}

	/**
	 * @return the strOutMappingInContent
	 */
	public String getStrOutMappingInContent() {
		return strOutMappingInContent;
	}

	/**
	 * @param strOutMappingInContent the strOutMappingInContent to set
	 */
	public void setStrOutMappingInContent(String strOutMappingInContent) {
		this.strOutMappingInContent = strOutMappingInContent;
	}

	/**
	 * @return the strDataSourceContent
	 */
	public String getStrDataSourceContent() {
		return strDataSourceContent;
	}

	/**
	 * @param strDataSourceContent the strDataSourceContent to set
	 */
	public void setStrDataSourceContent(String strDataSourceContent) {
		this.strDataSourceContent = strDataSourceContent;
	}

	public List<OutputBean> getLstDataSourceOutputBean() {
		return lstDataSourceOutputBean;
	}

	public void setLstDataSourceOutputBean(List<OutputBean> lstDataSourceOutputBean) {
		this.lstDataSourceOutputBean = lstDataSourceOutputBean;
	}

	public List<OutputBean> getLstNonDataSourceOutputBean() {
		return lstNonDataSourceOutputBean;
	}

	public void setLstNonDataSourceOutputBean(
			List<OutputBean> lstNonDataSourceOutputBean) {
		this.lstNonDataSourceOutputBean = lstNonDataSourceOutputBean;
	}

	/**
	 * @return the strUndoModelSetting
	 */
	public String getStrUndoModelSetting() {
		return strUndoModelSetting;
	}

	/**
	 * @param strUndoModelSetting the strUndoModelSetting to set
	 */
	public void setStrUndoModelSetting(String strUndoModelSetting) {
		this.strUndoModelSetting = strUndoModelSetting;
	}

	public Integer getDesignMode() {
	    return designMode;
    }

	public void setDesignMode(Integer designMode) {
	    this.designMode = designMode;
    }

	/**
	 * @return the bdDeclarFirstPage
	 */
	public String getBdDeclarFirstPage() {
		return bdDeclarFirstPage;
	}

	/**
	 * @param bdDeclarFirstPage the bdDeclarFirstPage to set
	 */
	public void setBdDeclarFirstPage(String bdDeclarFirstPage) {
		this.bdDeclarFirstPage = bdDeclarFirstPage;
	}

	/**
	 * @return the bdDeclarLastPage
	 */
	public String getBdDeclarLastPage() {
		return bdDeclarLastPage;
	}

	/**
	 * @param bdDeclarLastPage the bdDeclarLastPage to set
	 */
	public void setBdDeclarLastPage(String bdDeclarLastPage) {
		this.bdDeclarLastPage = bdDeclarLastPage;
	}

	/**
	 * @return the bdDeclarMapPage
	 */
	public String getBdDeclarMapPage() {
		return bdDeclarMapPage;
	}

	/**
	 * @param bdDeclarMapPage the bdDeclarMapPage to set
	 */
	public void setBdDeclarMapPage(String bdDeclarMapPage) {
		this.bdDeclarMapPage = bdDeclarMapPage;
	}

	/**
	 * @return the bdModelPageAttr
	 */
	public String getBdModelPageAttr() {
		return bdModelPageAttr;
	}

	/**
	 * @param bdModelPageAttr the bdModelPageAttr to set
	 */
	public void setBdModelPageAttr(String bdModelPageAttr) {
		this.bdModelPageAttr = bdModelPageAttr;
	}

	public List<ExceptionComponent> getLstExceptionDetails() {
		return lstExceptionDetails;
	}

	public void setLstExceptionDetails(List<ExceptionComponent> lstExceptionDetails) {
		this.lstExceptionDetails = lstExceptionDetails;
	}

	public Boolean getShowImpactFlag() {
	    return showImpactFlag;
    }

	public void setShowImpactFlag(Boolean showImpactFlag) {
	    this.showImpactFlag = showImpactFlag;
    }

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(Integer httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getHttpStatusValue() {
		return httpStatusValue;
	}

	public void setHttpStatusValue(String httpStatusValue) {
		this.httpStatusValue = httpStatusValue;
	}


}
