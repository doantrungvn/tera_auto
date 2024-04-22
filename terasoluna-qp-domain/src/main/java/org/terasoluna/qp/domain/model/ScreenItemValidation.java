package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ScreenItemValidation implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long screenItemId;
	private Integer maxlength;
	private Integer mandatoryFlg;
	private String minVal;
	private String maxVal;
	private String fmtCode;
	private Long createdBy;
	private Timestamp createdDate;
	private Long updatedBy;
	private Timestamp updatedDate;
	private Timestamp sysDatetime;
	private Boolean haveMandatory;
	private Boolean haveMaxLength;
	private Boolean haveMinVal;
	private Boolean haveMaxVal;
	
	public Boolean getHaveMandatory() {
		return haveMandatory;
	}

	public void setHaveMandatory(Boolean haveMandatory) {
		this.haveMandatory = haveMandatory;
	}

	public Boolean getHaveMaxLength() {
		return haveMaxLength;
	}

	public void setHaveMaxLength(Boolean haveMaxLength) {
		this.haveMaxLength = haveMaxLength;
	}

	public Boolean getHaveMinVal() {
		return haveMinVal;
	}

	public void setHaveMinVal(Boolean haveMinVal) {
		this.haveMinVal = haveMinVal;
	}

	public Boolean getHaveMaxVal() {
		return haveMaxVal;
	}

	public void setHaveMaxVal(Boolean haveMaxVal) {
		this.haveMaxVal = haveMaxVal;
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

	public Long getScreenItemId() {
		return screenItemId;
	}

	public void setScreenItemId(Long screenItemId) {
		this.screenItemId = screenItemId;
	}

	public Integer getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(Integer maxlength) {
		this.maxlength = maxlength;
	}

	public Integer getMandatoryFlg() {
		return mandatoryFlg;
	}

	public void setMandatoryFlg(Integer mandatoryFlg) {
		this.mandatoryFlg = mandatoryFlg;
	}

	public String getMinVal() {
		return minVal;
	}

	public void setMinVal(String minVal) {
		this.minVal = minVal;
	}

	public String getMaxVal() {
		return maxVal;
	}

	public void setMaxVal(String maxVal) {
		this.maxVal = maxVal;
	}

	public String getFmtCode() {
		return fmtCode;
	}

	public void setFmtCode(String fmtCode) {
		this.fmtCode = fmtCode;
	}

	public Timestamp getSysDatetime() {
		return sysDatetime;
	}

	public void setSysDatetime(Timestamp sysDatetime) {
		this.sysDatetime = sysDatetime;
	}

}
