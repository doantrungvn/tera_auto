package org.terasoluna.qp.app.sqldesign;

import java.io.Serializable;

import javax.validation.Valid;

public class SqlDesignAdvancedDesignForm implements Serializable{

	private static final long serialVersionUID = 4081490716607523111L;

	@Valid
	private SqlDesignForm sqlDesignForm;
	
	@Valid
	private InputForm[] inputForm;
	
	@Valid
	private OutputForm[] outputForm;
	
	private String activeTab;
	
	private String mode;

	private Boolean actionDelete;
	
	private String openOwner;
	
	private Boolean showImpactFlag;
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public SqlDesignAdvancedDesignForm(){
		this.sqlDesignForm = new SqlDesignForm();
	}
	
	public SqlDesignForm getSqlDesignForm() {
		return sqlDesignForm;
	}

	public void setSqlDesignForm(SqlDesignForm sqlDesignForm) {
		this.sqlDesignForm = sqlDesignForm;
	}

	public String getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(String activeTab) {
		this.activeTab = activeTab;
	}

	public InputForm[] getInputForm() {
		return inputForm;
	}

	public void setInputForm(InputForm[] inputForm) {
		this.inputForm = inputForm;
	}

	public OutputForm[] getOutputForm() {
		return outputForm;
	}

	public void setOutputForm(OutputForm[] outputForm) {
		this.outputForm = outputForm;
	}

	public Boolean getActionDelete() {
		return actionDelete;
	}

	public void setActionDelete(Boolean actionDelete) {
		this.actionDelete = actionDelete;
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
