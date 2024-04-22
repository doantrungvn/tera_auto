package org.terasoluna.qp.app.screendesign;

import java.io.Serializable;

import org.terasoluna.qp.app.module.ModuleTableMappingForm;

public class ScreenRegisterForm implements Serializable {

	public static interface RegistrationScreenDesignForm {};
	
	private static final long serialVersionUID = 1L;
	private String screenName;
	
	private String screenCode;
	
	private String projectId;
	
	private String moduleId;
	
	private String templateType;
	
	private String screenPatternType;
	
	
	private Integer confirmationType;
	private Integer completionType;
	private ModuleTableMappingForm[] moduleTableMappings = new ModuleTableMappingForm[0];
	private Boolean isCopy;
	private Long copyScreenId;
	private String copyScreenIdAutocomplete;
	private String projectIdAutocomplete;
	private String moduleIdAutocomplete;
	private String remark;
	private Integer designMode;
	
	private String functionDesignId;
	
	public String getFunctionDesignId() {
		return functionDesignId;
	}

	public void setFunctionDesignId(String functionDesignId) {
		this.functionDesignId = functionDesignId;
	}

	private String functionDesignIdAutocomplete;
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getModuleIdAutocomplete() {
		return moduleIdAutocomplete;
	}

	public void setModuleIdAutocomplete(String moduleIdAutocomplete) {
		this.moduleIdAutocomplete = moduleIdAutocomplete;
	}

	public String getProjectIdAutocomplete() {
		return projectIdAutocomplete;
	}

	public void setProjectIdAutocomplete(String projectIdAutocomplete) {
		this.projectIdAutocomplete = projectIdAutocomplete;
	}

	public String getCopyScreenIdAutocomplete() {
		return copyScreenIdAutocomplete;
	}

	public void setCopyScreenIdAutocomplete(String copyScreenIdAutocomplete) {
		this.copyScreenIdAutocomplete = copyScreenIdAutocomplete;
	}

	public Long getCopyScreenId() {
		return copyScreenId;
	}

	public void setCopyScreenId(Long copyScreenId) {
		this.copyScreenId = copyScreenId;
	}

	public ModuleTableMappingForm[] getModuleTableMappings() {
		return moduleTableMappings;
	}

	public void setModuleTableMappings(ModuleTableMappingForm[] moduleTableMappings) {
		this.moduleTableMappings = moduleTableMappings;
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

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getScreenPatternType() {
		return screenPatternType;
	}

	public void setScreenPatternType(String screenPatternType) {
		this.screenPatternType = screenPatternType;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public ScreenRegisterForm() {
	}
	
	public String getScreenName() {
		return screenName;
	}
	
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	
	public Boolean getIsCopy() {
		return isCopy;
	}

	public void setIsCopy(Boolean isCopy) {
		this.isCopy = isCopy;
	}

	public String getScreenCode() {
		return screenCode;
	}

	public void setScreenCode(String screenCode) {
		this.screenCode = screenCode;
	}

	public String getFunctionDesignIdAutocomplete() {
	    return functionDesignIdAutocomplete;
    }

	public void setFunctionDesignIdAutocomplete(String functionDesignIdAutocomplete) {
	    this.functionDesignIdAutocomplete = functionDesignIdAutocomplete;
    }

	public Integer getDesignMode() {
		return designMode;
	}

	public void setDesignMode(Integer designMode) {
		this.designMode = designMode;
	}
	
}
