package org.terasoluna.qp.app.subjectarea;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.validation.Valid;

import org.terasoluna.qp.app.common.validation.QpRemarkSize;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.SubjectArea;

public class SubjectAreaForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long areaId;
	
	private String areaName;
	
	private String areaCode;
	
	private Long projectId;
	
/*	@NotEmpty(message = SubjectAreaMessageConst.SC_SUBAREADESIGN_0010)*/
	private String projectIdAutocomplete;
	
	private String projectName;
	
	private Integer defaultFlg = 0;
	
	private Integer itemSeqNo;
	
	@QpRemarkSize(message= CommonMessageConst.SC_SYS_0028)
	private String remark;
	
	private String keyword;
	
	private Long createdBy;
	
	private Timestamp createdDate;
	
	private Long updatedBy;
	
	private Timestamp updatedDate;
	
	private Timestamp sysDatetime;

	private String mode;
	
	private Integer registerFlag = 1;

	private List<SubjectArea> positionLst;
	
	@Valid
	private List<SubjectAreaTableDesignForm> tableLst;

	@Valid
	private List<SubjectAreaKeywordForm> keywordLst;

	/**
	 * @return the areaId
	 */
	public Long getAreaId() {
		return areaId;
	}

	/**
	 * @param areaId the areaId to set
	 */
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * @param areaName the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @param areaCode the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * @return the projectId
	 */
	public Long getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	/**
	 * @return the projectIdAutocomplete
	 */
	public String getProjectIdAutocomplete() {
		return projectIdAutocomplete;
	}

	/**
	 * @param projectIdAutocomplete the projectIdAutocomplete to set
	 */
	public void setProjectIdAutocomplete(String projectIdAutocomplete) {
		this.projectIdAutocomplete = projectIdAutocomplete;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the defaultFlg
	 */
	public Integer getDefaultFlg() {
		return defaultFlg;
	}

	/**
	 * @param defaultFlg the defaultFlg to set
	 */
	public void setDefaultFlg(Integer defaultFlg) {
		this.defaultFlg = defaultFlg;
	}

	/**
	 * @return the itemSeqNo
	 */
	public Integer getItemSeqNo() {
		return itemSeqNo;
	}

	/**
	 * @param itemSeqNo the itemSeqNo to set
	 */
	public void setItemSeqNo(Integer itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the updatedBy
	 */
	public Long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the updatedDate
	 */
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	/**
	 * @return the positionLst
	 */
	public List<SubjectArea> getPositionLst() {
		return positionLst;
	}

	/**
	 * @param positionLst the positionLst to set
	 */
	public void setPositionLst(List<SubjectArea> positionLst) {
		this.positionLst = positionLst;
	}

	/**
	 * @return the sysDatetime
	 */
	public Timestamp getSysDatetime() {
		return sysDatetime;
	}

	/**
	 * @param sysDatetime the sysDatetime to set
	 */
	public void setSysDatetime(Timestamp sysDatetime) {
		this.sysDatetime = sysDatetime;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return the registerFlag
	 */
	public Integer getRegisterFlag() {
		return registerFlag;
	}

	/**
	 * @param registerFlag the registerFlag to set
	 */
	public void setRegisterFlag(Integer registerFlag) {
		this.registerFlag = registerFlag;
	}
	
	/**
	 * @return the tableLst
	 */
	public List<SubjectAreaTableDesignForm> getTableLst() {
		return tableLst;
	}

	/**
	 * @param tableLst the tableLst to set
	 */
	public void setTableLst(List<SubjectAreaTableDesignForm> tableLst) {
		this.tableLst = tableLst;
	}
	
	/**
	 * @return the keywordLst
	 */
	public List<SubjectAreaKeywordForm> getKeywordLst() {
		return keywordLst;
	}

	/**
	 * @param keywordLst the keywordLst to set
	 */
	public void setKeywordLst(List<SubjectAreaKeywordForm> keywordLst) {
		this.keywordLst = keywordLst;
	}
}
