package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class ProjectModuleTable implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long moduleId;
	
	private Long projectId;
	
	private Long tableDesignId;

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

	public Long getTableDesignId() {
		return tableDesignId;
	}

	public void setTableDesignId(Long tableDesignId) {
		this.tableDesignId = tableDesignId;
	}
	
	
}
