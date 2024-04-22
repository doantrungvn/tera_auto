package org.terasoluna.qp.app.autocomplete;

import java.io.Serializable;

import javax.validation.Valid;

import org.terasoluna.qp.app.sqldesign.InputForm;
import org.terasoluna.qp.app.sqldesign.SqlDesignForm;

public class AutocompleteAdvancedDesignForm implements Serializable{

	private static final long serialVersionUID = 4747509357559454364L;
	
	@Valid
	private AutocompleteForm autocompleteForm;
	
	private SqlDesignForm sqlDesignForm;
	
	private InputForm[] inputForm;
	
	private OutputForm outputForm;
	
	private String mode;

	private String activeTab;
	
	private String sqlContent;
	
	private Boolean actionDelete;
	
	private Integer designStatus;
	
	private String openOwner;
	
	private Boolean showImpactFlag;
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public AutocompleteAdvancedDesignForm(){
		this.autocompleteForm = new AutocompleteForm();
	}
	
	public AutocompleteForm getAutocompleteForm() {
		return autocompleteForm;
	}

	public void setAutocompleteForm(AutocompleteForm autocompleteForm) {
		this.autocompleteForm = autocompleteForm;
	}

	public InputForm[] getInputForm() {
		return inputForm;
	}

	public void setInputForm(InputForm[] inputForm) {
		this.inputForm = inputForm;
	}

	public OutputForm getOutputForm() {
		return outputForm;
	}

	public void setOutputForm(OutputForm outputForm) {
		this.outputForm = outputForm;
	}

	public String getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(String activeTab) {
		this.activeTab = activeTab;
	}

	public String getSqlContent() {
		return sqlContent;
	}

	public void setSqlContent(String sqlContent) {
		this.sqlContent = sqlContent;
	}

	public SqlDesignForm getSqlDesignForm() {
		return sqlDesignForm;
	}

	public void setSqlDesignForm(SqlDesignForm sqlDesignForm) {
		this.sqlDesignForm = sqlDesignForm;
	}

	public Boolean getActionDelete() {
		return actionDelete;
	}

	public void setActionDelete(Boolean actionDelete) {
		this.actionDelete = actionDelete;
	}

	public Integer getDesignStatus() {
		return designStatus;
	}

	public void setDesignStatus(Integer designStatus) {
		this.designStatus = designStatus;
	}

	public String getOpenOwner() {
		return openOwner;
	}

	public void setOpenOwner(String openOwner) {
		this.openOwner = openOwner;
	}

	public Boolean getShowImpactFlag() {
		return showImpactFlag;
	}

	public void setShowImpactFlag(Boolean showImpactFlag) {
		this.showImpactFlag = showImpactFlag;
	}
	
}
