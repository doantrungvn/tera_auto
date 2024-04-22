package org.terasoluna.qp.domain.service.importmanagement;

public class ForeignKey {
	private String fromTable;
	private String toTable;
	private String fromColumn;
	private String toColumn;
	
	public String getFromTable() {
		return fromTable;
	}
	public void setFromTable(String fromTable) {
		this.fromTable = fromTable;
	}
	public String getToTable() {
		return toTable;
	}
	public void setToTable(String toTable) {
		this.toTable = toTable;
	}
	public String getFromColumn() {
		return fromColumn;
	}
	public void setFromColumn(String fromColumn) {
		this.fromColumn = fromColumn;
	}
	public String getToColumn() {
		return toColumn;
	}
	public void setToColumn(String toColumn) {
		this.toColumn = toColumn;
	}
}
