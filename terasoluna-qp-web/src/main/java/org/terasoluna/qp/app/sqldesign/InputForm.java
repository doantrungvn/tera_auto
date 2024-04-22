package org.terasoluna.qp.app.sqldesign;


public class InputForm {
	private Long sqlDesignId;
	private Long sqlDesignInputId;
	private Long sqlDesignInputParentId;
	private String sqlDesignInputName;
	private String sqlDesignInputCode;
	private String dataType;
	private Boolean isArray;
	private String itemSeqNo;
	private String groupId;
	private String groupIndex;
	private String tableId;
	private String columnId;
	private String designType;
	private String objectType;
	private String moduleId;
	
	public Long getSqlDesignId() {
		return sqlDesignId;
	}
	public void setSqlDesignId(Long sqlDesignId) {
		this.sqlDesignId = sqlDesignId;
	}
	public Long getSqlDesignInputId() {
		return sqlDesignInputId;
	}
	public void setSqlDesignInputId(Long sqlDesignInputId) {
		this.sqlDesignInputId = sqlDesignInputId;
	}
	public String getSqlDesignInputName() {
		return sqlDesignInputName;
	}
	public void setSqlDesignInputName(String sqlDesignInputName) {
		this.sqlDesignInputName = sqlDesignInputName;
	}
	public String getSqlDesignInputCode() {
		return sqlDesignInputCode;
	}
	public void setSqlDesignInputCode(String sqlDesignInputCode) {
		this.sqlDesignInputCode = sqlDesignInputCode;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public Boolean getIsArray() {
		return isArray;
	}
	public void setIsArray(Boolean isArray) {
		this.isArray = isArray;
	}
	public String getItemSeqNo() {
		return itemSeqNo;
	}
	public void setItemSeqNo(String itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupIndex() {
		return groupIndex;
	}
	public void setGroupIndex(String groupIndex) {
		this.groupIndex = groupIndex;
	}
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public String getColumnId() {
		return columnId;
	}
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}
	public Long getSqlDesignInputParentId() {
		return sqlDesignInputParentId;
	}
	public void setSqlDesignInputParentId(Long sqlDesignInputParentId) {
		this.sqlDesignInputParentId = sqlDesignInputParentId;
	}
	public String getDesignType() {
		return designType;
	}
	public void setDesignType(String designType) {
		this.designType = designType;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
}
