package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class ItemValidation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long screenAreaId;
	private Long screenItemId;
	private String itemCode;
	private String maxlength;
	private boolean mandatoryFlg;
	private String minVal;
	private String maxVal;
	private String fmtCode;
	private String messageString;
	private String messageCode;
	
	private String screenFormCode;
	private Integer formRowspan;
	private String areaName;
	private String areaType;
	private Integer areaRowspan;
	private Long screenFormId;

	public String getScreenFormCode() {
		return screenFormCode;
	}

	public void setScreenFormCode(String screenFormCode) {
		this.screenFormCode = screenFormCode;
	}

	public Integer getFormRowspan() {
		return formRowspan;
	}

	public void setFormRowspan(Integer formRowspan) {
		this.formRowspan = formRowspan;
	}

	public Integer getAreaRowspan() {
		return areaRowspan;
	}

	public void setAreaRowspan(Integer areaRowspan) {
		this.areaRowspan = areaRowspan;
	}

	public Long getScreenFormId() {
		return screenFormId;
	}

	public void setScreenFormId(Long screenFormId) {
		this.screenFormId = screenFormId;
	}

	public Long getScreenAreaId() {
		return screenAreaId;
	}

	public void setScreenAreaId(Long screenAreaId) {
		this.screenAreaId = screenAreaId;
	}

	public Long getScreenItemId() {
		return screenItemId;
	}

	public void setScreenItemId(Long screenItemId) {
		this.screenItemId = screenItemId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}

	public boolean isMandatoryFlg() {
		return mandatoryFlg;
	}

	public void setMandatoryFlg(boolean mandatoryFlg) {
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

	public String getMessageString() {
		return messageString;
	}

	public void setMessageString(String messageString) {
		this.messageString = messageString;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

}
