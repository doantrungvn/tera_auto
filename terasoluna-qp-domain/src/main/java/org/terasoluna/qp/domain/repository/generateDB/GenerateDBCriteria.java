package org.terasoluna.qp.domain.repository.generateDB;

import java.io.Serializable;

public class GenerateDBCriteria implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long projectId;
	
	private Long moduleId;
	
	private int[] templateTypes;
	
	private String screenName;
	
	private String screenCode;
	
	private String projectIdAutocomplete;
	
	private String moduleIdAutocomplete;
	
	private int[] designStatus;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public int[] getTemplateTypes() {
		return templateTypes;
	}

	public void setTemplateTypes(int[] templateTypes) {
		this.templateTypes = templateTypes;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getScreenCode() {
		return screenCode;
	}

	public void setScreenCode(String screenCode) {
		this.screenCode = screenCode;
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

	public int[] getDesignStatus() {
		return designStatus;
	}

	public void setDesignStatus(int[] designStatus) {
		this.designStatus = designStatus;
	}
	
	
}
