package org.terasoluna.qp.app.sqldesign;


public class OutputForm {
	private Long sqlDesignId;
	private Long sqlDesignOutputId;
	private Long sqlDesignOutputParentId;
	private String sqlDesignOutputName;
	private String sqlDesignOutputCode;
	private String dataType;
	private Boolean isArray;
	private String itemSeqNo;
	private String groupId;
	private String groupIndex;
	private String mappingColumn;
	private String mappingColumnAutocomplete;
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
	public Long getSqlDesignOutputId() {
		return sqlDesignOutputId;
	}
	public void setSqlDesignOutputId(Long sqlDesignOutputId) {
		this.sqlDesignOutputId = sqlDesignOutputId;
	}
	public String getSqlDesignOutputName() {
		return sqlDesignOutputName;
	}
	public void setSqlDesignOutputName(String sqlDesignOutputName) {
		this.sqlDesignOutputName = sqlDesignOutputName;
	}
	public String getSqlDesignOutputCode() {
		return sqlDesignOutputCode;
	}
	public void setSqlDesignOutputCode(String sqlDesignOutputCode) {
		this.sqlDesignOutputCode = sqlDesignOutputCode;
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
	public String getMappingColumn() {
		return mappingColumn;
	}
	public void setMappingColumn(String mappingColumn) {
		this.mappingColumn = mappingColumn;
	}
	public String getMappingColumnAutocomplete() {
		return mappingColumnAutocomplete;
	}
	public void setMappingColumnAutocomplete(String mappingColumnAutocomplete) {
		this.mappingColumnAutocomplete = mappingColumnAutocomplete;
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
	public Long getSqlDesignOutputParentId() {
		return sqlDesignOutputParentId;
	}
	public void setSqlDesignOutputParentId(Long sqlDesignOutputParentId) {
		this.sqlDesignOutputParentId = sqlDesignOutputParentId;
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
