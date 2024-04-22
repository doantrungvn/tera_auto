package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class ScreenItemCodelist implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long screenItemId;
	private Integer codelistSeqNo;
	private String codelistName;
	private String codelistVal;
	private Integer supportOptionValFlg;
	private Long createdBy;
	private Timestamp createdDate;
	private Long updatedBy;
	private Timestamp updatedDate;
	private List<ScreenItemCodelistDetail> screenItemCodelistDetails;
	
	public Long getScreenItemId() {
		return screenItemId;
	}
	public void setScreenItemId(Long screenItemId) {
		this.screenItemId = screenItemId;
	}
	public Integer getCodelistSeqNo() {
		return codelistSeqNo;
	}
	public void setCodelistSeqNo(Integer codelistSeqNo) {
		this.codelistSeqNo = codelistSeqNo;
	}
	public String getCodelistName() {
		return codelistName;
	}
	public void setCodelistName(String codelistName) {
		this.codelistName = codelistName;
	}
	public String getCodelistVal() {
		return codelistVal;
	}
	public void setCodelistVal(String codelistVal) {
		this.codelistVal = codelistVal;
	}
	public Integer getSupportOptionValFlg() {
		return supportOptionValFlg;
	}
	public void setSupportOptionValFlg(Integer supportOptionValFlg) {
		this.supportOptionValFlg = supportOptionValFlg;
	}
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
	public List<ScreenItemCodelistDetail> getScreenItemCodelistDetails() {
		return screenItemCodelistDetails;
	}
	public void setScreenItemCodelistDetails(
			List<ScreenItemCodelistDetail> screenItemCodelistDetails) {
		this.screenItemCodelistDetails = screenItemCodelistDetails;
	} 
}
