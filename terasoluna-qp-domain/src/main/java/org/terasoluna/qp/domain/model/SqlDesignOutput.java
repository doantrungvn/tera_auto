package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.List;

public class SqlDesignOutput implements Serializable{

	private static final long serialVersionUID = 7658156223340115480L;
	private Long sqlDesignOutputId;
	private Long sqlDesignId;
	private String sqlDesignOutputName;
	private String sqlDesignOutputCode;
	private Integer dataType;
	private Long sqlDesignOutputParentId;
	private Integer arrayFlag;
	private Integer itemSeqNo;
	private String mappingColumn;
	private String groupId;
	private String groupIndex;
	private String objectDefinitionId;
	private Long tableId;
	private Long columnId;
	private Integer designType;
	private Integer objectType;
	private String objectTypeCode;
	private String packageName;
	private String propertyPath;
	private String outputBeanId;
	
	private Long businessDesignId;
	private Long businessDesignModuleId;
	
	private Long screenDesignId;
	private Long screenDesignModuleId;
	
	private String pakageExternal;
	private String externalObjCode;
	private String commonObjCode;
	
	private String sqlDesignCode;
	private Long moduleId;
	private String moduleCode;
	
	private List<SqlDesignOutput> lstChildrens;
	
	public Long getSqlDesignOutputId() {
		return sqlDesignOutputId;
	}
	public void setSqlDesignOutputId(Long sqlDesignOutputId) {
		this.sqlDesignOutputId = sqlDesignOutputId;
	}
	public Long getSqlDesignId() {
		return sqlDesignId;
	}
	public void setSqlDesignId(Long sqlDesignId) {
		this.sqlDesignId = sqlDesignId;
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
	public Integer getDataType() {
		return dataType;
	}
	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}
	public Long getSqlDesignOutputParentId() {
		return sqlDesignOutputParentId;
	}
	public void setSqlDesignOutputParentId(Long sqlDesignOutputParentId) {
		this.sqlDesignOutputParentId = sqlDesignOutputParentId;
	}
	public Integer getArrayFlag() {
		return arrayFlag;
	}
	public void setArrayFlag(Integer arrayFlag) {
		this.arrayFlag = arrayFlag;
	}
	public Integer getItemSeqNo() {
		return itemSeqNo;
	}
	public void setItemSeqNo(Integer itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}
	public String getMappingColumn() {
		return mappingColumn;
	}
	public void setMappingColumn(String mappingColumn) {
		this.mappingColumn = mappingColumn;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getObjectDefinitionId() {
	    return objectDefinitionId;
    }
	public void setObjectDefinitionId(String objectDefinitionId) {
	    this.objectDefinitionId = objectDefinitionId;
    }
	public Long getBusinessDesignId() {
		return businessDesignId;
	}
	public void setBusinessDesignId(Long businessDesignId) {
		this.businessDesignId = businessDesignId;
	}
	public Long getBusinessDesignModuleId() {
		return businessDesignModuleId;
	}
	public void setBusinessDesignModuleId(Long businessDesignModuleId) {
		this.businessDesignModuleId = businessDesignModuleId;
	}
	public Long getScreenDesignId() {
		return screenDesignId;
	}
	public void setScreenDesignId(Long screenDesignId) {
		this.screenDesignId = screenDesignId;
	}
	public Long getScreenDesignModuleId() {
		return screenDesignModuleId;
	}
	public void setScreenDesignModuleId(Long screenDesignModuleId) {
		this.screenDesignModuleId = screenDesignModuleId;
	}
	public String getGroupIndex() {
		return groupIndex;
	}
	public void setGroupIndex(String groupIndex) {
		this.groupIndex = groupIndex;
	}
	public Long getTableId() {
		return tableId;
	}
	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}
	public Long getColumnId() {
		return columnId;
	}
	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}
	public Integer getDesignType() {
		return designType;
	}
	public void setDesignType(Integer designType) {
		this.designType = designType;
	}
	public Integer getObjectType() {
		return objectType;
	}
	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getPropertyPath() {
		return propertyPath;
	}
	public void setPropertyPath(String propertyPath) {
		this.propertyPath = propertyPath;
	}
	public String getOutputBeanId() {
		return outputBeanId;
	}
	public void setOutputBeanId(String outputBeanId) {
		this.outputBeanId = outputBeanId;
	}
	public String getObjectTypeCode() {
		return objectTypeCode;
	}
	public void setObjectTypeCode(String objectTypeCode) {
		this.objectTypeCode = objectTypeCode;
	}
	/**
	 * @return the pakageExternal
	 */
	public String getPakageExternal() {
		return pakageExternal;
	}
	/**
	 * @param pakageExternal the pakageExternal to set
	 */
	public void setPakageExternal(String pakageExternal) {
		this.pakageExternal = pakageExternal;
	}
	/**
	 * @return the externalObjCode
	 */
	public String getExternalObjCode() {
		return externalObjCode;
	}
	/**
	 * @param externalObjCode the externalObjCode to set
	 */
	public void setExternalObjCode(String externalObjCode) {
		this.externalObjCode = externalObjCode;
	}
	/**
	 * @return the commonObjCode
	 */
	public String getCommonObjCode() {
		return commonObjCode;
	}
	/**
	 * @param commonObjCode the commonObjCode to set
	 */
	public void setCommonObjCode(String commonObjCode) {
		this.commonObjCode = commonObjCode;
	}
	public String getSqlDesignCode() {
	    return sqlDesignCode;
    }
	public void setSqlDesignCode(String sqlDesignCode) {
	    this.sqlDesignCode = sqlDesignCode;
    }
	public Long getModuleId() {
	    return moduleId;
    }
	public void setModuleId(Long moduleId) {
	    this.moduleId = moduleId;
    }
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	public List<SqlDesignOutput> getLstChildrens() {
	    return lstChildrens;
    }
	public void setLstChildrens(List<SqlDesignOutput> lstChildrens) {
	    this.lstChildrens = lstChildrens;
    }
	
}
