package org.terasoluna.qp.app.graphicdatabasedesign;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class SearchForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull (message="err.databasedesign.0099")
	private Long projectId;
	
	private String projectIdAutocomplete;
	private long areaId;
	

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	public long getAreaId() {
		return areaId;
	}

	public void setAreaId(long areaId) {
		this.areaId = areaId;
	}

	public String getProjectIdAutocomplete() {
		return projectIdAutocomplete;
	}

	public void setProjectIdAutocomplete(String projectIdAutocomplete) {
		this.projectIdAutocomplete = projectIdAutocomplete;
	}
	
}
