package org.terasoluna.qp.app.businessdesign;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.validation.QpCodePattern;
import org.terasoluna.qp.app.common.validation.QpCodeSize;
import org.terasoluna.qp.app.common.validation.QpNamePattern;
import org.terasoluna.qp.app.common.validation.QpNameSize;
import org.terasoluna.qp.app.common.validation.QpReserved;
import org.terasoluna.qp.app.message.BusinessDesignMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.ItemValidation;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.ScreenItem;
import org.terasoluna.qp.domain.model.SequenceConnector;
import org.terasoluna.qp.domain.model.SequenceLogic;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;

public class BusinessDesignForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long businessLogicId;

	@NotEmpty(message = BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0005)
	@QpNameSize(message = BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0005)
	@QpNamePattern(message = BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0005)
	private String businessLogicName;

	@NotEmpty(message = BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0006)
	@QpCodeSize(message = BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0006)
	@QpCodePattern(message = BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0006)
	@QpReserved(message = BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0006)
	private String businessLogicCode;


	// @NotNull(message = "sc.businesslogicdesign.0013")
	private Integer returnType ;

	private Long screenId;

	private Long moduleId;

	private Integer moduleStatus = 1;

	private Long projectId;

	private Boolean customizeFlg;

	private String contentPath;

	private Long createdBy;

	private Timestamp createdDate;

	private Long updatedBy;

	private Timestamp updatedDate;

	private Boolean hasErrors = false;

	private String mode;

	private String typeRegister;

	private String moduleIdAutocomplete;

	private MultipartFile file;

	private String fileName;

	private Integer designStatus;

	private Long uploadFileId;

	private String jsonInputBean;

	private String jsonOutputBean;

	private String jsonObjectDefinition;

	private String jsonConnector;

	private String jsonComponent;

	private Boolean flagAction;

	private Long functionDesignId;

	private String functionDesignIdAutocomplete;

	private Integer moduleType = 0;

	private List<InputBean> lstInputBean = new ArrayList<InputBean>();

	private List<OutputBean> lstOutputBean = new ArrayList<OutputBean>();

	private List<ObjectDefinition> lstObjectDefinition = new ArrayList<ObjectDefinition>();

	private List<SequenceLogic> lstSequenceLogics = new ArrayList<SequenceLogic>();

	private List<SequenceConnector> lstSequenceConnectors = new ArrayList<SequenceConnector>();

	private List<ItemValidation> lstItemValidations = new ArrayList<ItemValidation>();

	private Boolean flagChangeFile;

	private Integer openOwner;

	private String packageName;

	private String screenIdAutocomplete;

	private Boolean clientCheckFlg;

	private Boolean convertWSFlg;

	@Size(min = DbDomainConst.MIN_VAL_REMARK, max = DbDomainConst.MAX_VAL_REMARK, message = CommonMessageConst.SC_SYS_0028 + ";" + DbDomainConst.MIN_VAL_REMARK + ";" + DbDomainConst.MAX_VAL_REMARK)
	private String remark;

	private List<BusinessDesign> lstAffectedBlogicCommon = new ArrayList<BusinessDesign>();
	
	private List<BusinessDesign> lstAffectedBlogicNavigator = new ArrayList<BusinessDesign>();

	private Long screenFormId;

	private String screenFormIdAutocomplete;

	private Boolean flagImpact = false;

	private List<ScreenItem> lstAffectedScreenItems = new ArrayList<ScreenItem>();

	private Integer batchType = 0;

	private Integer patternType = 0;

	private Integer blogicType;

	private Integer requestMethod;

	private Boolean authenticatedFlg;

	private String moduleCode;

	private String actionPath;
	
	private Integer designMode = BusinessDesignConst.DESIGN_MODE_MANUAL;
	
	/** advance setting regiter Blogic for return type*/
	private Boolean advanceReturnTypeFlag = false;
	
	/** advance setting regiter Blogic for return type*/
    private Boolean advanceRequestMethodFlag = false;
    
    
    private Boolean showImpactFlag = false;

	private Integer httpStatus;
	
	private Boolean isShowWarningFile = false;
    
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

	public String getContentPath() {
		return contentPath;
	}

	public void setContentPath(String contentPath) {
		this.contentPath = contentPath;
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

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getModuleIdAutocomplete() {
		return moduleIdAutocomplete;
	}

	public void setModuleIdAutocomplete(String moduleIdAutocomplete) {
		this.moduleIdAutocomplete = moduleIdAutocomplete;
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

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public Boolean getFlagAction() {
		return flagAction;
	}

	public void setFlagAction(Boolean flagAction) {
		this.flagAction = flagAction;
	}

	public Integer getOpenOwner() {
		return openOwner;
	}

	public void setOpenOwner(Integer openOwner) {
		this.openOwner = openOwner;
	}

	public List<ItemValidation> getLstItemValidations() {
		return lstItemValidations;
	}

	public void setLstItemValidations(List<ItemValidation> lstItemValidations) {
		this.lstItemValidations = lstItemValidations;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Integer getPatternType() {
		return patternType;
	}

	public void setPatternType(Integer patternType) {
		this.patternType = patternType;
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

	public Boolean getConvertWSFlg() {
		return convertWSFlg;
	}

	public void setConvertWSFlg(Boolean convertWSFlg) {
		this.convertWSFlg = convertWSFlg;
	}

	public Boolean getClientCheckFlg() {
		return clientCheckFlg;
	}

	public void setClientCheckFlg(Boolean clientCheckFlg) {
		this.clientCheckFlg = clientCheckFlg;
	}

	public Boolean getHasErrors() {
		return hasErrors;
	}

	public void setHasErrors(Boolean hasErrors) {
		this.hasErrors = hasErrors;
	}

	public Boolean getCustomizeFlg() {
		return customizeFlg;
	}

	public void setCustomizeFlg(Boolean customizeFlg) {
		this.customizeFlg = customizeFlg;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getScreenFormIdAutocomplete() {
		return screenFormIdAutocomplete;
	}

	public void setScreenFormIdAutocomplete(String screenFormIdAutocomplete) {
		this.screenFormIdAutocomplete = screenFormIdAutocomplete;
	}

	public String getActionPath() {
	    return actionPath;
    }

	public void setActionPath(String actionPath) {
	    this.actionPath = actionPath;
    }

    public Boolean getAdvanceReturnTypeFlag() {
        return advanceReturnTypeFlag;
    }

    public void setAdvanceReturnTypeFlag(Boolean advanceReturnTypeFlag) {
        this.advanceReturnTypeFlag = advanceReturnTypeFlag;
    }

    public Boolean getAdvanceRequestMethodFlag() {
        return advanceRequestMethodFlag;
    }

    public void setAdvanceRequestMethodFlag(Boolean advanceRequestMethodFlag) {
        this.advanceRequestMethodFlag = advanceRequestMethodFlag;
    }

	public List<BusinessDesign> getLstAffectedBlogicCommon() {
		return lstAffectedBlogicCommon;
	}

	public void setLstAffectedBlogicCommon(List<BusinessDesign> lstAffectedBlogicCommon) {
		this.lstAffectedBlogicCommon = lstAffectedBlogicCommon;
	}

	public List<BusinessDesign> getLstAffectedBlogicNavigator() {
		return lstAffectedBlogicNavigator;
	}

	public void setLstAffectedBlogicNavigator(
			List<BusinessDesign> lstAffectedBlogicNavigator) {
		this.lstAffectedBlogicNavigator = lstAffectedBlogicNavigator;
	}

	public Integer getDesignMode() {
	    return designMode;
    }

	public void setDesignMode(Integer designMode) {
	    this.designMode = designMode;
    }

	public Boolean getShowImpactFlag() {
	    return showImpactFlag;
    }

	public void setShowImpactFlag(Boolean showImpactFlag) {
	    this.showImpactFlag = showImpactFlag;
    }

	public Integer getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(Integer httpStatus) {
		this.httpStatus = httpStatus;
	}

	public Boolean getIsShowWarningFile() {
	    return isShowWarningFile;
    }

	public void setIsShowWarningFile(Boolean isShowWarningFile) {
	    this.isShowWarningFile = isShowWarningFile;
    }
}
