package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class ScreenTransitionBranchDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String branchDetailsId;
	
	private String branchId;
	
	private String caption;
	
	private String conditionRemark;

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getConditionRemark() {
		return conditionRemark;
	}

	public void setConditionRemark(String conditionRemark) {
		this.conditionRemark = conditionRemark;
	}

	public String getBranchDetailsId() {
		return branchDetailsId;
	}

	public void setBranchDetailsId(String branchDetailsId) {
		this.branchDetailsId = branchDetailsId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
}
