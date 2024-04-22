package org.terasoluna.qp.app.viewdesign;

import java.io.Serializable;

import javax.validation.Valid;

import org.terasoluna.qp.app.sqldesign.InputForm;
import org.terasoluna.qp.app.sqldesign.OutputForm;

public class ViewDesignAdvancedDesignForm implements Serializable{

	private static final long serialVersionUID = 4081490716607523111L;

	@Valid
	private ViewDesignForm sqlDesignForm;
	
	@Valid
	private InputForm[] inputForm;
	
	@Valid
	private OutputForm[] outputForm;
	
	private String activeTab;
	
	private String mode;

	private Boolean actionDelete;
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public ViewDesignAdvancedDesignForm(){
		this.sqlDesignForm = new ViewDesignForm();
	}
	
	public ViewDesignForm getSqlDesignForm() {
		return sqlDesignForm;
	}

	public void setSqlDesignForm(ViewDesignForm sqlDesignForm) {
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
	
}
