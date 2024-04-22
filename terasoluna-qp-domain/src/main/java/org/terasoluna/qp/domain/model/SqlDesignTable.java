package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class SqlDesignTable implements Serializable{
	
	private static final long serialVersionUID = 356117700658765119L;
	private Long sqlDesignTableId;
	private Long sqlDesignId;
	private Long tableId;
	private String tableName;
	private String tableCode;
	private Integer tableType;
	private String tableMissingFlag;
	private String joinType;
	private Long joinTableId;
	private String joinTableName;
	private String joinTableCode;
	private Integer joinTableType;
	private String joinTableMissingFlag;
	private Integer itemSeqNo;
	private String dataTypeText;
	
	private SqlDesignTableItem[] sqlDesignTableItems;
	public Long getSqlDesignTableId() {
		return sqlDesignTableId;
	}
	public void setSqlDesignTableId(Long sqlDesignTableId) {
		this.sqlDesignTableId = sqlDesignTableId;
	}
	public Long getSqlDesignId() {
		return sqlDesignId;
	}
	public void setSqlDesignId(Long sqlDesignId) {
		this.sqlDesignId = sqlDesignId;
	}
	public Long getTableId() {
		return tableId;
	}
	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableMissingFlag() {
		return tableMissingFlag;
	}
	public void setTableMissingFlag(String tableMissingFlag) {
		this.tableMissingFlag = tableMissingFlag;
	}
	public String getJoinType() {
		return joinType;
	}
	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}
	public Long getJoinTableId() {
		return joinTableId;
	}
	public void setJoinTableId(Long joinTableId) {
		this.joinTableId = joinTableId;
	}
	public String getJoinTableName() {
		return joinTableName;
	}
	public void setJoinTableName(String joinTableName) {
		this.joinTableName = joinTableName;
	}
	public String getJoinTableMissingFlag() {
		return joinTableMissingFlag;
	}
	public void setJoinTableMissingFlag(String joinTableMissingFlag) {
		this.joinTableMissingFlag = joinTableMissingFlag;
	}
	public Integer getItemSeqNo() {
		return itemSeqNo;
	}
	public void setItemSeqNo(Integer itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}
	public SqlDesignTableItem[] getSqlDesignTableItems() {
		return sqlDesignTableItems;
	}
	public void setSqlDesignTableItems(SqlDesignTableItem[] sqlDesignTableItems) {
		this.sqlDesignTableItems = sqlDesignTableItems;
	}
	public String getDataTypeText() {
		return dataTypeText;
	}
	public void setDataTypeText(String dataTypeText) {
		this.dataTypeText = dataTypeText;
	}
	public String getTableCode() {
		return tableCode;
	}
	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}
	public String getJoinTableCode() {
		return joinTableCode;
	}
	public void setJoinTableCode(String joinTableCode) {
		this.joinTableCode = joinTableCode;
	}
	public Integer getTableType() {
		return tableType;
	}
	public Integer getJoinTableType() {
		return joinTableType;
	}
	public void setTableType(Integer tableType) {
		this.tableType = tableType;
	}
	public void setJoinTableType(Integer joinTableType) {
		this.joinTableType = joinTableType;
	}
}
