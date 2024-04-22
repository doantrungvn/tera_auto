package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.List;

public class SqlDesignInput implements Serializable{

	private static final long serialVersionUID = 7658156223340115480L;
	private Long sqlDesignInputId;
	private Long sqlDesignId;
	private String sqlDesignInputName;
	private String sqlDesignInputCode;
	private Integer dataType;
	private Long sqlDesignInputParentId;
	private Integer arrayFlag;
	private String groupId;
	private String groupIndex;
	private Integer itemSeqNo;
	private Long tableId;
	private String tableCode;
	private Long columnId;
	private Long tempColumnId;
	private Integer designType;
	private Integer objectType;
	private String objectTypeCode;
	private String packageName;
	
	private Long businessDesignId;
	private Long businessDesignModuleId;
	private Integer fromOrTo; // 0 : From, 1 : To
	
	private String pakageExternal;
	private String externalObjCode;
	private String commonObjCode;
	
	private String sqlDesignCode;
	private Long moduleId;
	private String moduleCode;
	
	private List<SqlDesignInput> lstChildrens;
	
	public Long getSqlDesignInputId() {
		return sqlDesignInputId;
	}
	public void setSqlDesignInputId(Long sqlDesignInputId) {
		this.sqlDesignInputId = sqlDesignInputId;
	}
	public Long getSqlDesignId() {
		return sqlDesignId;
	}
	public void setSqlDesignId(Long sqlDesignId) {
		this.sqlDesignId = sqlDesignId;
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
	public Integer getDataType() {
		return dataType;
	}
	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}
	public Long getSqlDesignInputParentId() {
		return sqlDesignInputParentId;
	}
	public void setSqlDesignInputParentId(Long sqlDesignInputParentId) {
		this.sqlDesignInputParentId = sqlDesignInputParentId;
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
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
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
	public Long getTempColumnId() {
	    return tempColumnId;
    }
	public void setTempColumnId(Long columnId) {
	    this.tempColumnId = columnId;
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
	public String getGroupIndex() {
		return groupIndex;
	}
	public void setGroupIndex(String groupIndex) {
		this.groupIndex = groupIndex;
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
	public String getObjectTypeCode() {
		return objectTypeCode;
	}
	public void setObjectTypeCode(String objectTypeCode) {
		this.objectTypeCode = objectTypeCode;
	}
	/**
	 * @return the tableCode
	 */
	public String getTableCode() {
		return tableCode;
	}
	/**
	 * @param tableCode the tableCode to set
	 */
	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}
	public Integer getFromOrTo() {
		return fromOrTo;
	}
	public void setFromOrTo(Integer fromOrTo) {
		this.fromOrTo = fromOrTo;
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
	public List<SqlDesignInput> getLstChildrens() {
	    return lstChildrens;
    }
	public void setLstChildrens(List<SqlDesignInput> lstChildrens) {
	    this.lstChildrens = lstChildrens;
    }
	
}
