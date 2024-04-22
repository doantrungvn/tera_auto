package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ScreenParameter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long screenParamId;
	private Long screenId;
	private Long domainTblMappingId;
	private Long domainTblMappingItemId;
	private String screenParamCode;
	private String screenParamName;
	private Integer dataType;
	private Integer arrayFlg;
	private Integer paramSeqNo;
	private Integer parentScreenParamId;
	private Long createdBy;
	private Timestamp createdDate;
	private Long updatedBy;
	private Timestamp updatedDate;
	private String domainColumnName;
	private String domainTblMappingName;
	private Long screenParamIdStore;
	
	public Long getScreenId() {
		return screenId;
	}
	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}
	public Long getDomainTblMappingId() {
		return domainTblMappingId;
	}
	public void setDomainTblMappingId(Long domainTblMappingId) {
		this.domainTblMappingId = domainTblMappingId;
	}
	public Long getDomainTblMappingItemId() {
		return domainTblMappingItemId;
	}
	public void setDomainTblMappingItemId(Long domainTblMappingItemId) {
		this.domainTblMappingItemId = domainTblMappingItemId;
	}
	public String getScreenParamCode() {
		return screenParamCode;
	}
	public void setScreenParamCode(String screenParamCode) {
		this.screenParamCode = screenParamCode;
	}
	public String getScreenParamName() {
		return screenParamName;
	}
	public void setScreenParamName(String screenParamName) {
		this.screenParamName = screenParamName;
	}
	public Integer getDataType() {
		return dataType;
	}
	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}
	public Integer getArrayFlg() {
		return arrayFlg;
	}
	public void setArrayFlg(Integer arrayFlg) {
		this.arrayFlg = arrayFlg;
	}
	public Integer getParamSeqNo() {
		return paramSeqNo;
	}
	public void setParamSeqNo(Integer paramSeqNo) {
		this.paramSeqNo = paramSeqNo;
	}
	public Integer getParentScreenParamId() {
		return parentScreenParamId;
	}
	public void setParentScreenParamId(Integer parentScreenParamId) {
		this.parentScreenParamId = parentScreenParamId;
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
	public Long getScreenParamId() {
		return screenParamId;
	}
	public void setScreenParamId(Long screenParamId) {
		this.screenParamId = screenParamId;
	}
	public String getDomainColumnName() {
		return domainColumnName;
	}
	public void setDomainColumnName(String domainColumnName) {
		this.domainColumnName = domainColumnName;
	}
	public String getDomainTblMappingName() {
		return domainTblMappingName;
	}
	public void setDomainTblMappingName(String domainTblMappingName) {
		this.domainTblMappingName = domainTblMappingName;
	}
	public Long getScreenParamIdStore() {
		return screenParamIdStore;
	}
	public void setScreenParamIdStore(Long screenParamIdStore) {
		this.screenParamIdStore = screenParamIdStore;
	}
	
}
