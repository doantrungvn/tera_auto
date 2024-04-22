package org.terasoluna.qp.app.sqldesign;

import java.io.Serializable;

public class GroupByForm implements Serializable {

	private static final long serialVersionUID = 4837728032928707956L;
	
	private String groupById;
	private String sqlDesignId;
	private String tableId;
	private String tableIdAutocomplete;
	private String columnId;
	private String columnIdAutocomplete;
	private String itemSeqNo;
	
	public String getGroupById() {
		return groupById;
	}
	public void setGroupById(String groupById) {
		this.groupById = groupById;
	}
	public String getSqlDesignId() {
		return sqlDesignId;
	}
	public void setSqlDesignId(String sqlDesignId) {
		this.sqlDesignId = sqlDesignId;
	}
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public String getTableIdAutocomplete() {
		return tableIdAutocomplete;
	}
	public void setTableIdAutocomplete(String tableIdAutocomplete) {
		this.tableIdAutocomplete = tableIdAutocomplete;
	}
	public String getColumnId() {
		return columnId;
	}
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}
	public String getColumnIdAutocomplete() {
		return columnIdAutocomplete;
	}
	public void setColumnIdAutocomplete(String columnIdAutocomplete) {
		this.columnIdAutocomplete = columnIdAutocomplete;
	}
	public String getItemSeqNo() {
		return itemSeqNo;
	}
	public void setItemSeqNo(String itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}
}
