package org.terasoluna.qp.domain.service.importmanagement;

import java.util.List;

public class Table {
	private String tableName;
	private int tableType;
	private List<TableField> listColumn;
	private List<PrimaryKey> listPrimaryKey;
	private List<ForeignKey> listForeignKey;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<TableField> getListColumn() {
		return listColumn;
	}
	public void setListColumn(List<TableField> listColumn) {
		this.listColumn = listColumn;
	}
	public List<ForeignKey> getListForeignKey() {
		return listForeignKey;
	}
	public void setListForeignKey(List<ForeignKey> listForeignKey) {
		this.listForeignKey = listForeignKey;
	}
	public List<PrimaryKey> getListPrimaryKey() {
		return listPrimaryKey;
	}
	public void setListPrimaryKey(List<PrimaryKey> listPrimaryKey) {
		this.listPrimaryKey = listPrimaryKey;
	}
	public int getTableType() {
		return tableType;
	}
	public void setTableType(int tableType) {
		this.tableType = tableType;
	}
}
