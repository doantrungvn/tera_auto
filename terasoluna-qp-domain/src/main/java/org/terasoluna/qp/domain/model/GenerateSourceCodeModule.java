package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.List;

public class GenerateSourceCodeModule implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long moduleId;
	
	private String moduleName;
	
	private String moduleCode;
	
	private Integer designStatus;
	
	private String remark;
	
	private List<ScreenDesign> listOfScreenDesign;
	
	private List<BusinessDesign> listOfBusinessDesign;
	
	private List<DecisionTable> listOfDecisionTable;

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<ScreenDesign> getListOfScreenDesign() {
		return listOfScreenDesign;
	}

	public void setListOfScreenDesign(List<ScreenDesign> listOfScreenDesign) {
		this.listOfScreenDesign = listOfScreenDesign;
	}

	public List<BusinessDesign> getListOfBusinessDesign() {
		return listOfBusinessDesign;
	}

	public void setListOfBusinessDesign(List<BusinessDesign> listOfBusinessDesign) {
		this.listOfBusinessDesign = listOfBusinessDesign;
	}

	public List<DecisionTable> getListOfDecisionTable() {
		return listOfDecisionTable;
	}

	public void setListOfDecisionTable(List<DecisionTable> listOfDecisionTable) {
		this.listOfDecisionTable = listOfDecisionTable;
	}

	public Integer getDesignStatus() {
		return designStatus;
	}

	public void setDesignStatus(Integer designStatus) {
		this.designStatus = designStatus;
	}
	
	
}
