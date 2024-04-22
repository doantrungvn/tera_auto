package org.terasoluna.qp.app.languagedesign;

import org.hibernate.validator.constraints.NotEmpty;


public class LanguageDesignDesignForm {
	
	@NotEmpty(message="sc.subareadesign.0009")
	private String defaultLanguage;
	
	@NotEmpty
	private LanguageDesignForm[] languageDesignForms;

	private Long projectId;
	
	public LanguageDesignForm[] getLanguageDesignForms() {
		return languageDesignForms;
	}

	public void setLanguageDesignForms(LanguageDesignForm[] languageDesignForms) {
		this.languageDesignForms = languageDesignForms;
	}

	public String getDefaultLanguage() {
		return defaultLanguage;
	}

	public void setDefaultLanguage(String defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
} 
