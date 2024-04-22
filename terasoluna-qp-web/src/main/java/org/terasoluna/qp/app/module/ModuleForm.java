package org.terasoluna.qp.app.module;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.validation.Valid;

import org.terasoluna.qp.app.common.validation.QpRemarkSize;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.FunctionDesign;
import org.terasoluna.qp.domain.model.ScreenDesign;

public class ModuleForm implements Serializable {
	
    public static interface RegistrationForm {};
	
	private static final long serialVersionUID = 1L;

	private Long moduleId;

	private String moduleName;
	
	private String moduleCode;
	
	private Long projectId;
	
	/*@NotEmpty(message = ProjectMessageConst.SC_PROJECT_0005, groups = { RegistrationForm.class })*/
	private String projectIdAutocomplete;
	
	private String projectName;
	
	private Long businessTypeId;
	
	private String businessTypeName;

	private String status;

	private Integer confirmationType;

	private Integer completionType;
	
	private String[] statuses = new String[0];

	private String[] confirmationTypes = new String[0];

	private String[] completionTypes = new String[0];

	@QpRemarkSize(message = CommonMessageConst.SC_SYS_0028)
	private String remark;

	//@NotEmpty(message = ModuleMessageConst.SC_MODULE_0017, groups = { RegistrationForm.class })
	private String[] screenPatternTypes = new String[0];

	@Valid
	private ModuleTableMappingForm[] moduleTableMappings = new ModuleTableMappingForm[0];
	
	private String createdBy;
	
	private Timestamp createdDate;
	
	private String updatedBy;
	
	private Timestamp updatedDate;
	
	private boolean hasErrors = false;
	
	private String mode;
	
	private Integer openOwner;
	
	private List<BusinessDesign> listBusinessDesign;
	
	private List<ScreenDesign> listScreenDesign;
	
	private List<FunctionDesign> listFunctionDesign;
	
	private Boolean actionDelete;
	
	private Integer moduleType;
	
	private Boolean defaultGeneration = true;
	
	private Integer defaultGenerationSetting;

	/**
	 * @return the moduleId
	 */
	public Long getModuleId() {
		return moduleId;
	}

	/**
	 * @param moduleId the moduleId to set
	 */
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
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

	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * @param moduleName the moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
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

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the businessTypeId
	 */
	public Long getBusinessTypeId() {
		return businessTypeId;
	}

	/**
	 * @param businessTypeId the businessTypeId to set
	 */
	public void setBusinessTypeId(Long businessTypeId) {
		this.businessTypeId = businessTypeId;
	}

	/**
	 * @return the businessTypeName
	 */
	public String getBusinessTypeName() {
		return businessTypeName;
	}

	/**
	 * @param businessTypeName the businessTypeName to set
	 */
	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the confirmationType
	 */
	public Integer getConfirmationType() {
		return confirmationType;
	}

	/**
	 * @param confirmationType the confirmationType to set
	 */
	public void setConfirmationType(Integer confirmationType) {
		this.confirmationType = confirmationType;
	}

	/**
	 * @return the completionType
	 */
	public Integer getCompletionType() {
		return completionType;
	}

	/**
	 * @param completionType the completionType to set
	 */
	public void setCompletionType(Integer completionType) {
		this.completionType = completionType;
	}

	/**
	 * @return the statuses
	 */
	public String[] getStatuses() {
		return statuses;
	}

	/**
	 * @param statuses the statuses to set
	 */
	public void setStatuses(String[] statuses) {
		this.statuses = statuses;
	}

	/**
	 * @return the confirmationTypes
	 */
	public String[] getConfirmationTypes() {
		return confirmationTypes;
	}

	/**
	 * @param confirmationTypes the confirmationTypes to set
	 */
	public void setConfirmationTypes(String[] confirmationTypes) {
		this.confirmationTypes = confirmationTypes;
	}

	/**
	 * @return the completionTypes
	 */
	public String[] getCompletionTypes() {
		return completionTypes;
	}

	/**
	 * @param completionTypes the completionTypes to set
	 */
	public void setCompletionTypes(String[] completionTypes) {
		this.completionTypes = completionTypes;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the moduleTableMappings
	 */
	public ModuleTableMappingForm[] getModuleTableMappings() {
		return moduleTableMappings;
	}

	/**
	 * @param moduleTableMappings the moduleTableMappings to set
	 */
	public void setModuleTableMappings(ModuleTableMappingForm[] moduleTableMappings) {
		this.moduleTableMappings = moduleTableMappings;
	}

	/**
	 * @return the screenPatternTypes
	 */
	public String[] getScreenPatternTypes() {
		return screenPatternTypes;
	}

	/**
	 * @param screenPatternTypes the screenPatternTypes to set
	 */
	public void setScreenPatternTypes(String[] screenPatternTypes) {
		this.screenPatternTypes = screenPatternTypes;
	}


	public String getProjectIdAutocomplete() {
		return projectIdAutocomplete;
	}

	public void setProjectIdAutocomplete(String projectIdAutocomplete) {
		this.projectIdAutocomplete = projectIdAutocomplete;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public boolean isHasErrors() {
		return hasErrors;
	}

	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public List<BusinessDesign> getListBusinessDesign() {
		return listBusinessDesign;
	}

	public void setListBusinessDesign(
			List<BusinessDesign> listBusinessDesign) {
		this.listBusinessDesign = listBusinessDesign;
	}

	public Integer getOpenOwner() {
		return openOwner;
	}

	public void setOpenOwner(Integer openOwner) {
		this.openOwner = openOwner;
	}

	public List<ScreenDesign> getListScreenDesign() {
		return listScreenDesign;
	}

	public void setListScreenDesign(List<ScreenDesign> listScreenDesign) {
		this.listScreenDesign = listScreenDesign;
	}

	public Boolean getActionDelete() {
		return actionDelete;
	}

	public void setActionDelete(Boolean actionDelete) {
		this.actionDelete = actionDelete;
	}

	public List<FunctionDesign> getListFunctionDesign() {
		return listFunctionDesign;
	}

	public void setListFunctionDesign(List<FunctionDesign> listFunctionDesign) {
		this.listFunctionDesign = listFunctionDesign;
	}

	public Integer getModuleType() {
		return moduleType;
	}

	public void setModuleType(Integer moduleType) {
		this.moduleType = moduleType;
	}

	public Boolean getDefaultGeneration() {
		return defaultGeneration;
	}

	public void setDefaultGeneration(Boolean defaultGeneration) {
		this.defaultGeneration = defaultGeneration;
	}

	public Integer getDefaultGenerationSetting() {
		return defaultGenerationSetting;
	}

	public void setDefaultGenerationSetting(Integer defaultGenerationSetting) {
		this.defaultGenerationSetting = defaultGenerationSetting;
	}

	
	
}
