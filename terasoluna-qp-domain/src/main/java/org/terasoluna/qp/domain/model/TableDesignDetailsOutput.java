package org.terasoluna.qp.domain.model;

public class TableDesignDetailsOutput {
	
	private Long columnId;
	
	private Long tableDesignId;
	
	private String columnName;
	
	private String columnCode;
	
	private Integer baseType;
	
	private Integer dataType;
	
	private Integer itemSeqNo;
	
	private Boolean arrayFlg = false;

	public Integer getItemSeqNo() {
		return itemSeqNo;
	}

	public void setItemSeqNo(Integer itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}

	public Long getColumnId() {
		return columnId;
	}

	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}

	public Long getTableDesignId() {
		return tableDesignId;
	}

	public void setTableDesignId(Long tableDesignId) {
		this.tableDesignId = tableDesignId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnCode() {
		return columnCode;
	}

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}

	public Integer getBaseType() {
		return baseType;
	}

	public void setBaseType(Integer baseType) {
		this.baseType = baseType;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Boolean getArrayFlg() {
	    return arrayFlg;
    }

	public void setArrayFlg(Boolean arrayFlg) {
	    this.arrayFlg = arrayFlg;
    }
}
