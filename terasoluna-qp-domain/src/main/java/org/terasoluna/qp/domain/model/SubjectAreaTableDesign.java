package org.terasoluna.qp.domain.model;

public class SubjectAreaTableDesign {
	private Long tableId;
	private Long subAreaId;
	private float xCoordinates;
	private float yCoordinates;
	
	public Long getTableId() {
		return tableId;
	}
	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}
	public Long getSubAreaId() {
		return subAreaId;
	}
	public void setSubAreaId(Long subAreaId) {
		this.subAreaId = subAreaId;
	}
	public float getxCoordinates() {
		return xCoordinates;
	}
	public void setxCoordinates(float xCoordinates) {
		this.xCoordinates = xCoordinates;
	}
	public float getyCoordinates() {
		return yCoordinates;
	}
	public void setyCoordinates(float yCoordinates) {
		this.yCoordinates = yCoordinates;
	}
}
