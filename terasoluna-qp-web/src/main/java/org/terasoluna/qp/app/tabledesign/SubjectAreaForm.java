package org.terasoluna.qp.app.tabledesign;

import java.io.Serializable;

public class SubjectAreaForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String areaId;

	private Long projectId;

	private String areaIdAutocomplete;

	private String areaCode;
	
	private String areaName;

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaIdAutocomplete() {
		return areaIdAutocomplete;
	}

	public void setAreaIdAutocomplete(String areaIdAutocomplete) {
		this.areaIdAutocomplete = areaIdAutocomplete;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	

}
