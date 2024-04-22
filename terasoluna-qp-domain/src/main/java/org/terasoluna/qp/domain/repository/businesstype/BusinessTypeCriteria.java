package org.terasoluna.qp.domain.repository.businesstype;

import java.io.Serializable;

public class BusinessTypeCriteria implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String businessTypeName;
	
	private String businessTypeCode;
	
	private Long parentBusinessTypeId;	
	
	private String parentBusinessTypeName;
	
	private Long moduleId;
	
	private Long projectId;

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
	
}
