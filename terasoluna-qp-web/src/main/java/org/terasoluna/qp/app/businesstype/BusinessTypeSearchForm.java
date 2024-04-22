package org.terasoluna.qp.app.businesstype;

import java.io.Serializable;

public class BusinessTypeSearchForm implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long businessTypeId;
	
	private String businessTypeName;
	
	private String businessTypeCode;

	private Long parentBusinessTypeId;
	
	private String parentBusinessTypeName;
	
	private Long moduleId;
	
	private Long projectId;
	
	private String projectIdAutocomplete;
	
	private String moduleIdAutocomplete;
	
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

	public String getBusinessTypeCode() {
		return businessTypeCode;
	}

	public void setBusinessTypeCode(String businessTypeCode) {
		this.businessTypeCode = businessTypeCode;
	}

	public Long getParentBusinessTypeId() {
		return parentBusinessTypeId;
	}

	public void setParentBusinessTypeId(Long parentBusinessTypeId) {
		this.parentBusinessTypeId = parentBusinessTypeId;
	}

	public String getParentBusinessTypeName() {
		return parentBusinessTypeName;
	}

	public void setParentBusinessTypeName(String parentBusinessTypeName) {
		this.parentBusinessTypeName = parentBusinessTypeName;
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

	public String getProjectIdAutocomplete() {
		return projectIdAutocomplete;
	}

	public void setProjectIdAutocomplete(String projectIdAutocomplete) {
		this.projectIdAutocomplete = projectIdAutocomplete;
	}

	public String getModuleIdAutocomplete() {
		return moduleIdAutocomplete;
	}

	public void setModuleIdAutocomplete(String moduleIdAutocomplete) {
		this.moduleIdAutocomplete = moduleIdAutocomplete;
	}
}
