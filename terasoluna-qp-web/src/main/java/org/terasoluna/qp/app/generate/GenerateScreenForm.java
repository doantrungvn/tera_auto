package org.terasoluna.qp.app.generate;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.GenerateMessageConst;
import org.terasoluna.qp.app.message.ModuleMessageConst;
import org.terasoluna.qp.app.module.ModuleTableMappingForm;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.ScreenDesign;

public class GenerateScreenForm implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long moduleId;
	
	private String moduleName;

	private String moduleCode;
	
	private Integer moduleType;
	
	private Long projectId;
	
	private Long businessTypeId;
	
	private String businessTypeName;
	
	private Integer status;
	
	private String remark;
	
	private String projectIdAutocomplete;
	
	private String projectName;
	
	private String confirmationType;

	private String completionType;
	
	private String[] confirmationTypes = new String[0];

	private String[] completionTypes = new String[0];

	@NotEmpty(message = GenerateMessageConst.SC_GENERATION_0010)
	@Size(min = DbDomainConst.MIN_VAL_INPUT, max = DbDomainConst.MAX_LENGTH_OF_NAME, message = ModuleMessageConst.SC_MODULE_0007 + ";" + DbDomainConst.MIN_VAL_INPUT +";" + DbDomainConst.MAX_LENGTH_OF_NAME)
	@Pattern(regexp=CommonMessageConst.PATTERN_FOR_NAME, message= ModuleMessageConst.SC_MODULE_0007 + ";" + CommonMessageConst.NAME_INPUTMASK)
	private String businessGenerateName;
	
	@NotEmpty(message = GenerateMessageConst.SC_GENERATION_0011)
	@Size(min = DbDomainConst.MIN_VAL_INPUT, max = DbDomainConst.MAX_LENGTH_OF_CODE, message = ModuleMessageConst.SC_MODULE_0008 + ";" + DbDomainConst.MIN_VAL_INPUT + ";" + DbDomainConst.MAX_LENGTH_OF_CODE)
	@Pattern(regexp =CommonMessageConst.PATTERN_FOR_CODE, message= ModuleMessageConst.SC_MODULE_0008 + ";" + CommonMessageConst.CODE_INPUTMASK)
	private String businessGenerateCode;
	
	@NotEmpty(message = ModuleMessageConst.SC_MODULE_0017)
	private String[] screenPatternTypes = new String[0];

	@Valid
	private ModuleTableMappingForm[] moduleTableMappings = new ModuleTableMappingForm[0];
	
	private String createdBy;
	
	private Timestamp createdDate;
	
	private String updatedBy;
	
	private Timestamp updatedDate;
	
	private List<BusinessDesign> listBusinessDesign;
	
	private List<ScreenDesign> listScreenDesign;
	
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

	public List<ScreenDesign> getListScreenDesign() {
		return listScreenDesign;
	}

	public void setListScreenDesign(List<ScreenDesign> listScreenDesign) {
		this.listScreenDesign = listScreenDesign;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String[] getScreenPatternTypes() {
		return screenPatternTypes;
	}

	public void setScreenPatternTypes(String[] screenPatternTypes) {
		this.screenPatternTypes = screenPatternTypes;
	}

	public ModuleTableMappingForm[] getModuleTableMappings() {
		return moduleTableMappings;
	}

	public void setModuleTableMappings(ModuleTableMappingForm[] moduleTableMappings) {
		this.moduleTableMappings = moduleTableMappings;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getProjectIdAutocomplete() {
		return projectIdAutocomplete;
	}

	public void setProjectIdAutocomplete(String projectIdAutocomplete) {
		this.projectIdAutocomplete = projectIdAutocomplete;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getConfirmationType() {
		return confirmationType;
	}

	public void setConfirmationType(String confirmationType) {
		this.confirmationType = confirmationType;
	}

	public String getCompletionType() {
		return completionType;
	}

	public void setCompletionType(String completionType) {
		this.completionType = completionType;
	}

	public String[] getConfirmationTypes() {
		return confirmationTypes;
	}

	public void setConfirmationTypes(String[] confirmationTypes) {
		this.confirmationTypes = confirmationTypes;
	}

	public String[] getCompletionTypes() {
		return completionTypes;
	}

	public void setCompletionTypes(String[] completionTypes) {
		this.completionTypes = completionTypes;
	}

	/*public String getModuleIdAutocomplete() {
		return moduleIdAutocomplete;
	}

	public void setModuleIdAutocomplete(String moduleIdAutocomplete) {
		this.moduleIdAutocomplete = moduleIdAutocomplete;
	}*/

	public List<BusinessDesign> getListBusinessDesign() {
		return listBusinessDesign;
	}

	public void setListBusinessDesign(List<BusinessDesign> listBusinessDesign) {
		this.listBusinessDesign = listBusinessDesign;
	}

	public Integer getModuleType() {
		return moduleType;
	}

	public void setModuleType(Integer moduleType) {
		this.moduleType = moduleType;
	}

	public Long getBusinessTypeId() {
		return businessTypeId;
	}

	public void setBusinessTypeId(Long businessTypeId) {
		this.businessTypeId = businessTypeId;
	}

	public String getBusinessTypeName() {
		return businessTypeName;
	}

	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getBusinessGenerateName() {
		return businessGenerateName;
	}

	public void setBusinessGenerateName(String businessGenerateName) {
		this.businessGenerateName = businessGenerateName;
	}

	public String getBusinessGenerateCode() {
		return businessGenerateCode;
	}

	public void setBusinessGenerateCode(String businessGenerateCode) {
		this.businessGenerateCode = businessGenerateCode;
	}
	
}
