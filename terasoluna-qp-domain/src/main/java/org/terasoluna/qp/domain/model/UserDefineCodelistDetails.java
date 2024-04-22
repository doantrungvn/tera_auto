package org.terasoluna.qp.domain.model;

public class UserDefineCodelistDetails {
	
	private Long codelistId;
	private String codelistName;
	private String codelistValue;
	private Integer defaultFlg;
	private Integer supportOptionFlg;
	private Integer itemSeqNo;
	private String otherName;
	public Long getCodelistId() {
		return codelistId;
	}
	public void setCodelistId(Long codelistId) {
		this.codelistId = codelistId;
	}
	public String getCodelistName() {
		return codelistName;
	}
	public void setCodelistName(String codelistName) {
		this.codelistName = codelistName;
	}
	public String getCodelistValue() {
		return codelistValue;
	}
	public void setCodelistValue(String codelistValue) {
		this.codelistValue = codelistValue;
	}
	public Integer getDefaultFlg() {
		return defaultFlg;
	}
	public void setDefaultFlg(Integer defaultFlg) {
		this.defaultFlg = defaultFlg;
	}
	public Integer getSupportOptionFlg() {
		return supportOptionFlg;
	}
	public void setSupportOptionFlg(Integer supportOptionFlg) {
		this.supportOptionFlg = supportOptionFlg;
	}
	public Integer getItemSeqNo() {
		return itemSeqNo;
	}
	public void setItemSeqNo(Integer itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}
	public String getOtherName() {
		return otherName;
	}
	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}
}
