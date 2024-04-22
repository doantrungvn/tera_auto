package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;

public class SubjectArea implements Serializable, Comparator<SubjectArea> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long areaId;
	
	private String areaName;
	
	private String areaCode;
	
	private Integer defaultFlg;
	
	private String remark;
	
	private Integer itemSeqNo;
	
	private Boolean isActive;
	
	private Long projectId;
	
	private String projectName;
	
	private String projectIdAutocomplete;
	
	private Integer position;
	
	private Project project;
	
	private Long createdBy;
	
	private Timestamp createdDate;
	
	private Long updatedBy;

	private Timestamp updatedDate;
	
	private Timestamp sysDatetime;
	
	private Integer registerFlag;
	
	private String areaIdAutocomplete;

	private List<SubjectArea> positionLst;
	
	private List<TableDesign> tableLst;
	
	private List<SubjectAreaKeyword> keywordLst;
	
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
	 * @return the itemSeqNo
	 */
	public Integer getItemSeqNo() {
		return itemSeqNo == null ? 0 : itemSeqNo;
	}
	
	/**
	 * @param itemSeqNo the itemSeqNo to set
	 */
	public void setItemSeqNo(Integer itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}
	
	/**
	 * @return the isActive
	 */
	public Boolean getIsActive() {
		return isActive;
	}
	
	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}
	
	/**
	 * @param position the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}
	
	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}
	
	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
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
	 * @return the tableLst
	 */
	public List<TableDesign> getTableLst() {
		return tableLst;
	}
	
	/**
	 * @param tableLst2 the tableLst to set
	 */
	public void setTableLst(List<TableDesign> tableLst2) {
		this.tableLst = tableLst2;
	}
	
	/**
	 * @return the keywordLst
	 */
	public List<SubjectAreaKeyword> getKeywordLst() {
		return keywordLst;
	}
	
	/**
	 * @param keywordLst the keywordLst to set
	 */
	public void setKeywordLst(List<SubjectAreaKeyword> keywordLst) {
		this.keywordLst = keywordLst;
	}

	@Override
	public int compare(SubjectArea o1, SubjectArea o2) {
		//ascending order
		return o1.itemSeqNo > o2.itemSeqNo ? 1 : (o1.itemSeqNo < o2.itemSeqNo ? -1 : 0);
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
	 * @return the areaIdAutocomplete
	 */
	public String getAreaIdAutocomplete() {
		return areaIdAutocomplete;
	}

	/**
	 * @param areaIdAutocomplete the areaIdAutocomplete to set
	 */
	public void setAreaIdAutocomplete(String areaIdAutocomplete) {
		this.areaIdAutocomplete = areaIdAutocomplete;
	}
}
