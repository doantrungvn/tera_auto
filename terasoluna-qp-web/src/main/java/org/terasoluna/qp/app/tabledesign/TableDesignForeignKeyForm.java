package org.terasoluna.qp.app.tabledesign;

import java.io.Serializable;

public class TableDesignForeignKeyForm implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long foreignKeyId;
	private Long foreignKeyType;
	
	private String fromColumnId;
	private String fromColumnName;
	private String fromColumnCode;
	
	private String fromTableId;
	private String fromTableCode;
	private String fromTableName;
	
	//@NotEmpty (message = TableDesignMessageConst.SC_TABLEDESIGN_0029)
	private String toTableId;
	private String toTableCode;
	private String toTableName;
	
	//@NotEmpty (message = TableDesignMessageConst.SC_TABLEDESIGN_0030)
	private String toColumnId;
	private String toColumnCode;
	private String toColumnName;
	
	//@NotEmpty (message = TableDesignMessageConst.SC_TABLEDESIGN_0027)
	private String foreignKeyCode;
	private String toForeignKeyType;
	
	private int indexRow;

	public Long getForeignKeyId() {
		return foreignKeyId;
	}

	public void setForeignKeyId(Long foreignKeyId) {
		this.foreignKeyId = foreignKeyId;
	}

	public String getFromColumnId() {
		return fromColumnId;
	}

	public void setFromColumnId(String fromColumnId) {
		this.fromColumnId = fromColumnId;
	}

	public String getFromColumnName() {
		return fromColumnName;
	}

	public void setFromColumnName(String fromColumnName) {
		this.fromColumnName = fromColumnName;
	}


	public String getFromTableId() {
		return fromTableId;
	}

	public void setFromTableId(String fromTableId) {
		this.fromTableId = fromTableId;
	}

	public String getFromTableCode() {
		return fromTableCode;
	}

	public void setFromTableCode(String fromTableCode) {
		this.fromTableCode = fromTableCode;
	}

	public String getFromTableName() {
		return fromTableName;
	}

	public void setFromTableName(String fromTableName) {
		this.fromTableName = fromTableName;
	}

	public String getToTableId() {
		return toTableId;
	}

	public void setToTableId(String toTableId) {
		this.toTableId = toTableId;
	}

	public String getToTableCode() {
		return toTableCode;
	}

	public void setToTableCode(String toTableCode) {
		this.toTableCode = toTableCode;
	}

	public String getToTableName() {
		return toTableName;
	}

	public void setToTableName(String toTableName) {
		this.toTableName = toTableName;
	}

	public String getToColumnId() {
		return toColumnId;
	}

	public void setToColumnId(String toColumnId) {
		this.toColumnId = toColumnId;
	}

	public String getToColumnCode() {
		return toColumnCode;
	}

	public void setToColumnCode(String toColumnCode) {
		this.toColumnCode = toColumnCode;
	}

	public String getToColumnName() {
		return toColumnName;
	}

	public void setToColumnName(String toColumnName) {
		this.toColumnName = toColumnName;
	}

	public String getForeignKeyCode() {
		return foreignKeyCode;
	}

	public void setForeignKeyCode(String foreignKeyCode) {
		this.foreignKeyCode = foreignKeyCode;
	}

	public Long getForeignKeyType() {
		return foreignKeyType;
	}

	public void setForeignKeyType(Long foreignKeyType) {
		this.foreignKeyType = foreignKeyType;
	}

	public int getIndexRow() {
		return indexRow;
	}

	public void setIndexRow(int indexRow) {
		this.indexRow = indexRow;
	}

	public String getToForeignKeyType() {
		return toForeignKeyType;
	}

	public void setToForeignKeyType(String toForeignKeyType) {
		this.toForeignKeyType = toForeignKeyType;
	}

	public String getFromColumnCode() {
		return fromColumnCode;
	}

	public void setFromColumnCode(String fromColumnCode) {
		this.fromColumnCode = fromColumnCode;
	}
}
