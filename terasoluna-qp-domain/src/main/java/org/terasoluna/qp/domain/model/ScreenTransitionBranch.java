package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.List;

public class ScreenTransitionBranch implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String branchId;
	
	private String branchIdTemp;
	
	private String name;
	
	private String remark;
	
	private int status;
	
	private Long moduleId;
	
	private int yCoordinates;
	
	private int xCoordinates;
	
	private String screenDesignElement;
	
	List<ScreenTransitionBranchDetail> objNavigatorInfoDetail;

	public String getName() {
		return name;
	}

	public int getyCoordinates() {
		return yCoordinates;
	}

	public void setyCoordinates(int yCoordinates) {
		this.yCoordinates = yCoordinates;
	}

	public int getxCoordinates() {
		return xCoordinates;
	}

	public void setxCoordinates(int xCoordinates) {
		this.xCoordinates = xCoordinates;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<ScreenTransitionBranchDetail> getObjNavigatorInfoDetail() {
		return objNavigatorInfoDetail;
	}

	public void setObjNavigatorInfoDetail(
			List<ScreenTransitionBranchDetail> objNavigatorInfoDetail) {
		this.objNavigatorInfoDetail = objNavigatorInfoDetail;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getBranchIdTemp() {
		return branchIdTemp;
	}

	public void setBranchIdTemp(String branchIdTemp) {
		this.branchIdTemp = branchIdTemp;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getScreenDesignElement() {
		return screenDesignElement;
	}

	public void setScreenDesignElement(String screenDesignElement) {
		this.screenDesignElement = screenDesignElement;
	}

}
