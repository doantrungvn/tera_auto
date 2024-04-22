package org.terasoluna.qp.app.problemlist;

import java.io.Serializable;

public class ProblemListSearchForm implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String problemName;
	
	private Long moduleId;
	
	private String moduleIdAutocomplete;
	
	private Long projectId;
	
	private Integer[] resourceType;
	
	private String resourceName;
	
	private Integer[] problemType;
	
	private Long createdBy;
	private String createdByAutocomplete;

	private String fromCreatedDate;
	private String toCreatedDate;
	
	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedByAutocomplete() {
		return createdByAutocomplete;
	}

	public void setCreatedByAutocomplete(String createdByAutocomplete) {
		this.createdByAutocomplete = createdByAutocomplete;
	}

	public String getFromCreatedDate() {
		return fromCreatedDate;
	}

	public void setFromCreatedDate(String fromCreatedDate) {
		this.fromCreatedDate = fromCreatedDate;
	}

	public String getToCreatedDate() {
		return toCreatedDate;
	}

	public void setToCreatedDate(String toCreatedDate) {
		this.toCreatedDate = toCreatedDate;
	}

	public String getProblemName() {
		return problemName;
	}

	public void setProblemName(String problemName) {
		this.problemName = problemName;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public Integer[] getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer[] resourceType) {
		this.resourceType = resourceType;
	}

	public Integer[] getProblemType() {
		return problemType;
	}

	public void setProblemType(Integer[] problemType) {
		this.problemType = problemType;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleIdAutocomplete() {
		return moduleIdAutocomplete;
	}

	public void setModuleIdAutocomplete(String moduleIdAutocomplete) {
		this.moduleIdAutocomplete = moduleIdAutocomplete;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
}
