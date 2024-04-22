package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ProblemList implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long problemId;
	
	private String problemName;
	
	private Integer resourceType;
	
	private String resourceName;
	
	private Long resourceId;
	
	private Integer problemType;
	
	private Integer autofixFlg;
	
	private Long moduleId;
	
	private String moduleIdAutocomplete;
	
	private Integer urlId;
	
	private Long projectId;
	
	private Integer countDisplay;
	
	private Integer fromResourceType;
	
	private Long fromResourceId;
	
	private Long createdBy;

	private String createdByName;

	private Timestamp createdDate;
	
	public Long getProblemId() {
		return problemId;
	}

	public void setProblemId(Long problemId) {
		this.problemId = problemId;
	}

	public String getProblemName() {
		return problemName;
	}

	public void setProblemName(String problemName) {
		this.problemName = problemName;
	}

	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	public Integer getProblemType() {
		return problemType;
	}

	public void setProblemType(Integer problemType) {
		this.problemType = problemType;
	}

	public Integer getAutofixFlg() {
		return autofixFlg;
	}

	public void setAutofixFlg(Integer autofixFlg) {
		this.autofixFlg = autofixFlg;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Integer getUrlId() {
		return urlId;
	}

	public void setUrlId(Integer urlId) {
		this.urlId = urlId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getModuleIdAutocomplete() {
		return moduleIdAutocomplete;
	}

	public void setModuleIdAutocomplete(String moduleIdAutocomplete) {
		this.moduleIdAutocomplete = moduleIdAutocomplete;
	}

	public Integer getCountDisplay() {
		return countDisplay;
	}

	public void setCountDisplay(Integer countDisplay) {
		this.countDisplay = countDisplay;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getFromResourceType() {
		return fromResourceType;
	}

	public void setFromResourceType(Integer fromResourceType) {
		this.fromResourceType = fromResourceType;
	}

	public Long getFromResourceId() {
		return fromResourceId;
	}

	public void setFromResourceId(Long fromResourceId) {
		this.fromResourceId = fromResourceId;
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

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

}
