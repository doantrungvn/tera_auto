package org.terasoluna.qp.domain.model;

public class TableDesignKeyItem {

	private Long tableDesignKeyId;
	private Long columnId;
	private Long keyId;
	private Integer type;
	private Long tableDesignId;
	private String tableName;
	private String columnName;
	
	public Long getTableDesignKeyId() {
		return tableDesignKeyId;
	}
	public void setTableDesignKeyId(Long tableDesignKeyId) {
		this.tableDesignKeyId = tableDesignKeyId;
	}
	public Long getColumnId() {
		return columnId;
	}
	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}
	public Long getKeyId() {
	    return keyId;
    }
	public void setKeyId(Long keyId) {
	    this.keyId = keyId;
    }
	public Integer getType() {
	    return type;
    }
	public void setType(Integer type) {
	    this.type = type;
    }
	public Long getTableDesignId() {
	    return tableDesignId;
    }
	public void setTableDesignId(Long tableDesignId) {
	    this.tableDesignId = tableDesignId;
    }
	public String getTableName() {
	    return tableName;
    }
	public void setTableName(String tableName) {
	    this.tableName = tableName;
    }
	public String getColumnName() {
	    return columnName;
    }
	public void setColumnName(String columnName) {
	    this.columnName = columnName;
    }
	
}
