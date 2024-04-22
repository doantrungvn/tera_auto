package org.terasoluna.qp.app.screendesign;

import java.io.Serializable;

public class FormularForm implements Serializable {

	private static final long serialVersionUID = 1L;	
	
	private String formulaName;
	
	private Long formulaDefinitionId;
	
	private String formulaDefinitionContent;
	
	private String formularDefinitionDetails;
	
	private Long screenId;
	
	private Long screenFormId;
	
	public Long getScreenId() {
		return screenId;
	}

	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}
	
	public String getFormularDefinitionDetails() {
		return formularDefinitionDetails;
	}

	public void setFormularDefinitionDetails(String formularDefinitionDetails) {
		this.formularDefinitionDetails = formularDefinitionDetails;
	}

	public String getFormulaDefinitionContent() {
		return formulaDefinitionContent;
	}

	public void setFormulaDefinitionContent(String formulaDefinitionContent) {
		this.formulaDefinitionContent = formulaDefinitionContent;
	}

	public Long getScreenFormId() {
		return screenFormId;
	}

	public void setScreenFormId(Long screenFormId) {
		this.screenFormId = screenFormId;
	}

	public Long getFormulaDefinitionId() {
		return formulaDefinitionId;
	}

	public void setFormulaDefinitionId(Long formulaDefinitionId) {
		this.formulaDefinitionId = formulaDefinitionId;
	}

	public String getFormulaName() {
		return formulaName;
	}

	public void setFormulaName(String formulaName) {
		this.formulaName = formulaName;
	}

	
}
