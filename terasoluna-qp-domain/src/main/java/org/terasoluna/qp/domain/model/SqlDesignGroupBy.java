package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class SqlDesignGroupBy implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7527257741293697758L;
	private Long groupById;
	private Long sqlDesignId;
	private Long tableId;
	private String tableName;
	private Long columnId;
	private String columnName;
	private Integer itemSeqNo;
	public Long getGroupById() {
		return groupById;
	}
	public void setGroupById(Long groupById) {
		this.groupById = groupById;
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
	public Long getColumnId() {
		return columnId;
	}
	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public Integer getItemSeqNo() {
		return itemSeqNo;
	}
	public void setItemSeqNo(Integer itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}
	

}
