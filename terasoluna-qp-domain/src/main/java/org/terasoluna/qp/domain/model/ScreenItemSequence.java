package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ScreenItemSequence implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer itemSeqNo;
	
	private Integer itemGroupType;
	
	private Long screenAreaId;
	
	private Long createdBy;
	
	private Timestamp createdDate;
	
	private Long updatedBy;
	
	private Timestamp updatedDate; 

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Integer getItemSeqNo() {
		return itemSeqNo;
	}

	public void setItemSeqNo(Integer itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}

	public Integer getItemGroupType() {
		return itemGroupType;
	}

	public void setItemGroupType(Integer itemGroupType) {
		this.itemGroupType = itemGroupType;
	}

	public Long getScreenAreaId() {
		return screenAreaId;
	}

	public void setScreenAreaId(Long screenAreaId) {
		this.screenAreaId = screenAreaId;
	}

}
