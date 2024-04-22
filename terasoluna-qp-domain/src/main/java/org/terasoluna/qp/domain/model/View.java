package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class View implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Long getViewId() {
		return viewId;
	}

	public void setViewId(Long viewId) {
		this.viewId = viewId;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	
	public String getViewCode() {
		return viewCode;
	}

	public void setViewCode(String viewCode) {
		this.viewCode = viewCode;
	}
	
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	private Long viewId;
	private String viewName;
	private String viewCode;
	private Long projectId;
	private Project project;


}
