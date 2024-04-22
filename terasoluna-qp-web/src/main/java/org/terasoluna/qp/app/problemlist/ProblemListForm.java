package org.terasoluna.qp.app.problemlist;

import java.io.Serializable;

public class ProblemListForm implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long problemId;
	
	private String problemName;
	
	private Integer resourceType;
	
	private Long resourceId;
	
	private Integer problemType;
	
	private Integer autofixFlg;
	
	private Long moduleId;
	
	private Long projectId;
	
	private Integer urlId;
	
	private Integer fromResourceType;
	
	private Integer fromResourceId;

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

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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

	public Integer getFromResourceId() {
		return fromResourceId;
	}

	public void setFromResourceId(Integer fromResourceId) {
		this.fromResourceId = fromResourceId;
	}
	
	
}
