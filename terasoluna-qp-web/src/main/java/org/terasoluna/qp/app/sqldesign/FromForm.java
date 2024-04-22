package org.terasoluna.qp.app.sqldesign;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.Size;

public class FromForm implements Serializable {

	private static final long serialVersionUID = -3482795886220508453L;
	
	private String sqlDesignId;
	private String sqlDesignTableId;
	private String tableId;
	private String tableType;
	private String joinType;
	private String joinTableId;
	private String joinTableType;
	private String tableIdAutocomplete;
	private String tableMissingFlag;
	private String joinTableIdAutocomplete;
	private String joinTableMissingFlag;
	private String itemSeqNo;
	private Boolean isShow;
	
	@Valid
	@Size(min=1)
	private JoinColumnsForm[] joinColumnsForm;
	
	public String getSqlDesignId() {
		return sqlDesignId;
	}
	public void setSqlDesignId(String sqlDesignId) {
		this.sqlDesignId = sqlDesignId;
	}
	public String getSqlDesignTableId() {
		return sqlDesignTableId;
	}
	public void setSqlDesignTableId(String sqlDesignTableId) {
		this.sqlDesignTableId = sqlDesignTableId;
	}
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public String getJoinType() {
		return joinType;
	}
	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}
	public String getJoinTableId() {
		return joinTableId;
	}
	public void setJoinTableId(String joinTableId) {
		this.joinTableId = joinTableId;
	}
	public String getTableIdAutocomplete() {
		return tableIdAutocomplete;
	}
	public void setTableIdAutocomplete(String tableIdAutocomplete) {
		this.tableIdAutocomplete = tableIdAutocomplete;
	}
	public String getJoinTableIdAutocomplete() {
		return joinTableIdAutocomplete;
	}
	public void setJoinTableIdAutocomplete(String joinTableIdAutocomplete) {
		this.joinTableIdAutocomplete = joinTableIdAutocomplete;
	}
	public String getItemSeqNo() {
		return itemSeqNo;
	}
	public void setItemSeqNo(String itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}
	public JoinColumnsForm[] getJoinColumnsForm() {
		return joinColumnsForm;
	}
	public void setJoinColumnsForm(JoinColumnsForm[] joinColumnsForm) {
		this.joinColumnsForm = joinColumnsForm;
	}
	public String getTableMissingFlag() {
		return tableMissingFlag;
	}
	public void setTableMissingFlag(String tableMissingFlag) {
		this.tableMissingFlag = tableMissingFlag;
	}
	public String getJoinTableMissingFlag() {
		return joinTableMissingFlag;
	}
	public void setJoinTableMissingFlag(String joinTableMissingFlag) {
		this.joinTableMissingFlag = joinTableMissingFlag;
	}
	public Boolean getIsShow() {
		return isShow;
	}
	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}
	public String getTableType() {
		return tableType;
	}
	public String getJoinTableType() {
		return joinTableType;
	}
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
	public void setJoinTableType(String joinTableType) {
		this.joinTableType = joinTableType;
	}
}
