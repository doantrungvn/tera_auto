package org.terasoluna.qp.domain.repository.problemlist;

import java.io.Serializable;
import java.sql.Timestamp;

public class ProblemListCriteria implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String problemName;
	
	private Integer[] resourceType;
	
	private Long moduleId;
	
	private Integer[] problemType;
	
	private Long projectId;
	
	private Long createdBy;
	private Timestamp fromCreatedDate;
	private Timestamp toCreatedDate;

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getFromCreatedDate() {
		return fromCreatedDate;
	}

	public void setFromCreatedDate(Timestamp fromCreatedDate) {
		this.fromCreatedDate = fromCreatedDate;
	}

	public Timestamp getToCreatedDate() {
		return toCreatedDate;
	}

	public void setToCreatedDate(Timestamp toCreatedDate) {
		this.toCreatedDate = toCreatedDate;
	}

	public String getProblemName() {
		return problemName;
	}

	public void setProblemName(String problemName) {
		this.problemName = problemName;
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

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
}
