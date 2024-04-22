package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FormulaDefinition implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String formulaName;

	private Long formulaDefinitionId;
	
	private String formulaDefinitionContent;
	
	private Long projectId;
	
//	private FormulaDetail[] formulaDefinitionDetails;
	
	private List<FormulaDetail> formulaDefinitionDetails = new ArrayList<FormulaDetail>();
	
	private List<ScreenItemStatus> screenItemStatuses = new ArrayList<ScreenItemStatus>();
	
	private Long screenFormId;

	private Integer formulaType;
	
	private String formulaCondition;

	public Long getFormulaDefinitionId() {
		return formulaDefinitionId;
	}

	public void setFormulaDefinitionId(Long formulaDefinitionId) {
		this.formulaDefinitionId = formulaDefinitionId;
	}

	public String getFormulaDefinitionContent() {
		return formulaDefinitionContent;
	}

	public void setFormulaDefinitionContent(String formulaDefinitionContent) {
		this.formulaDefinitionContent = formulaDefinitionContent;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public List<FormulaDetail> getFormulaDefinitionDetails() {
		return formulaDefinitionDetails;
	}

	public void setFormulaDefinitionDetails(List<FormulaDetail> formulaDefinitionDetails) {
		this.formulaDefinitionDetails = formulaDefinitionDetails;
	}

	public List<ScreenItemStatus> getScreenItemStatuses() {
		return screenItemStatuses;
	}

	public void setScreenItemStatuses(List<ScreenItemStatus> screenItemStatuses) {
		this.screenItemStatuses = screenItemStatuses;
	}

	public Long getScreenFormId() {
		return screenFormId;
	}

	public void setScreenFormId(Long screenFormId) {
		this.screenFormId = screenFormId;
	}

	public Integer getFormulaType() {
		return formulaType;
	}

	public void setFormulaType(Integer formulaType) {
		this.formulaType = formulaType;
	}

	public String getFormulaName() {
		return formulaName;
	}

	public void setFormulaName(String formulaName) {
		this.formulaName = formulaName;
	}

	public String getFormulaCondition() {
		return formulaCondition;
	}

	public void setFormulaCondition(String formulaCondition) {
		this.formulaCondition = formulaCondition;
	}

	
//	public FormulaDetail[] getFormulaDefinitionDetails() {
//		return formulaDefinitionDetails;
//	}
//
//	public void setFormulaDefinitionDetails(FormulaDetail[] formulaDefinitionDetails) {
//		this.formulaDefinitionDetails = formulaDefinitionDetails;
//	}
	
	

}
