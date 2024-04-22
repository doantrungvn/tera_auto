package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.List;

import org.terasoluna.qp.app.common.constants.DbDomainConst;

public class ObjectDefinition implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String objectDefinitionId;

	private String objectDefinitionCode;

	private String objectDefinitionName;

	private Integer dataType;

	private Boolean arrayFlg = false;

	private Integer level;

	private String tableIndex;

	private Long businessLogicId;

	private String parentObjectDefinitionId;

	private Integer itemSequenceNo;

	private String groupId;

	private Long tblDesignId;

	private Long columnId;

	private Integer groupBaseTypeId;

	private Integer impactStatus = 1;

	private String tblDesignName;

	private String tblDesignCode;

	private String columnName;

	private String columnCode;

	private Integer baseType;

	private Integer enabledFlag;

	private String mappingObjectDefinitionId;

	private String keyType;
	
	private List<ObjectDefinition> singleList;
	
	private List<ObjectDefinition> objectList;
	
	private Integer designType;
    
    private Integer objectType;
    
    private Long objectId;
    
    private Boolean objectFlg = true;

	private String commonObjDefiCode;
	
	/** module code of common object */
    private String moduleCode;
	
	private String externalObjDefiCode;
	
	private Boolean flagTotalCount;
	
    private Integer displayType = 1;
	
    /** In the case of object external  */
    private String packageNameObjExt;
    
    private Integer autoincrementFlg;
    
    /** In the case of batch module */
    private FileFormat fileFormat; 
    
    private String inputColumnNo; 
    
    private String outputColumnNo;
    
    private ColumnFileFormat columnFileFormat;
    
    /**
     * 1 : list insert, 2 : list update
     */
    private Integer typeListInsertOrUpdate;
    
    private Integer fromOrTo; // From : 0 ; To : 1
    
    private String businessLogicCode;
    
    private Long moduleId;
    
    private List<ObjectDefinition> lstChildrens;
    
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

	public String getObjectDefinitionCode() {
		return objectDefinitionCode;
	}

	public void setObjectDefinitionCode(String objectDefinitionCode) {
		this.objectDefinitionCode = objectDefinitionCode;
	}

	public String getObjectDefinitionName() {
		return objectDefinitionName;
	}

	public void setObjectDefinitionName(String objectDefinitionName) {
		this.objectDefinitionName = objectDefinitionName;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Long getBusinessLogicId() {
		return businessLogicId;
	}

	public void setBusinessLogicId(Long businessLogicId) {
		this.businessLogicId = businessLogicId;
	}

	public String getParentObjectDefinitionId() {
		return parentObjectDefinitionId;
	}

	public void setParentObjectDefinitionId(String parentObjectDefinitionId) {
		this.parentObjectDefinitionId = parentObjectDefinitionId;
	}

	public Integer getItemSequenceNo() {
		return itemSequenceNo;
	}

	public void setItemSequenceNo(Integer itemSequenceNo) {
		this.itemSequenceNo = itemSequenceNo;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getTableIndex() {
		return tableIndex;
	}

	public void setTableIndex(String tableIndex) {
		this.tableIndex = tableIndex;
	}

	public Long getTblDesignId() {
		return tblDesignId;
	}

	public void setTblDesignId(Long tblDesignId) {
		this.tblDesignId = tblDesignId;
	}

	public Long getColumnId() {
		return columnId;
	}

	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}

	public Integer getGroupBaseTypeId() {
		return groupBaseTypeId;
	}

	public void setGroupBaseTypeId(Integer groupBaseTypeId) {
		this.groupBaseTypeId = groupBaseTypeId;
	}

	public Integer getImpactStatus() {
		return impactStatus;
	}

	public void setImpactStatus(Integer impactStatus) {
		this.impactStatus = impactStatus;
	}

	public String getTblDesignName() {
		return tblDesignName;
	}

	public void setTblDesignName(String tblDesignName) {
		this.tblDesignName = tblDesignName;
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

	public Boolean getArrayFlg() {
		return arrayFlg;
	}

	public void setArrayFlg(Boolean arrayFlg) {
		this.arrayFlg = arrayFlg;
	}

	public Integer getBaseType() {
		return baseType;
	}

	public void setBaseType(Integer baseType) {
		this.baseType = baseType;
	}

	public Integer getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(Integer enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

	public String getTblDesignCode() {
		return tblDesignCode;
	}

	public void setTblDesignCode(String tblDesignCode) {
		this.tblDesignCode = tblDesignCode;
	}

	public String getMappingObjectDefinitionId() {
		return mappingObjectDefinitionId;
	}

	public void setMappingObjectDefinitionId(String mappingObjectDefinitionId) {
		this.mappingObjectDefinitionId = mappingObjectDefinitionId;
	}

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	/**
	 * @return 0: is not key - else 1: is key
	 */
	public int isKey(Integer typeOfKey) {
		if (keyType == null || keyType.isEmpty()) {
			return 0;
		}

		int iKeyType = Integer.parseInt(keyType, 2);

		if ((iKeyType & typeOfKey) == typeOfKey && iKeyType != DbDomainConst.TblDesignKeyType.NONE) {
			return 1;
		}

		return 0;
	}


	/**
	 * @return the singleList
	 */
	public List<ObjectDefinition> getSingleList() {
		return singleList;
	}

	/**
	 * @param singleList the singleList to set
	 */
	public void setSingleList(List<ObjectDefinition> singleList) {
		this.singleList = singleList;
	}

	/**
	 * @return the objectList
	 */
	public List<ObjectDefinition> getObjectList() {
		return objectList;
	}

	/**
	 * @param objectList the objectList to set
	 */
	public void setObjectList(List<ObjectDefinition> objectList) {
		this.objectList = objectList;
	}

    /**
     * @return the objectFlg
     */
    public Boolean getObjectFlg() {
        return objectFlg;
    }

    /**
     * @param objectFlg the objectFlg to set
     */
    public void setObjectFlg(Boolean objectFlg) {
        this.objectFlg = objectFlg;
    }

    /**
     * @return the objectId
     */
    public Long getObjectId() {
        return objectId;
    }

    /**
     * @param objectId the objectId to set
     */
    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    /**
     * @return the objectType
     */
    public Integer getObjectType() {
        return objectType;
    }

    /**
     * @param objectType the objectType to set
     */
    public void setObjectType(Integer objectType) {
        this.objectType = objectType;
    }

	public Integer getDesignType() {
	    return designType;
    }

	public void setDesignType(Integer designType) {
	    this.designType = designType;
    }

	/**
	 * @return the commonObjDefiCode
	 */
	public String getCommonObjDefiCode() {
		return commonObjDefiCode;
	}

	/**
	 * @param commonObjDefiCode the commonObjDefiCode to set
	 */
	public void setCommonObjDefiCode(String commonObjDefiCode) {
		this.commonObjDefiCode = commonObjDefiCode;
	}

	/**
	 * @return the externalObjDefiCode
	 */
	public String getExternalObjDefiCode() {
		return externalObjDefiCode;
	}

	/**
	 * @param externalObjDefiCode the externalObjDefiCode to set
	 */
	public void setExternalObjDefiCode(String externalObjDefiCode) {
		this.externalObjDefiCode = externalObjDefiCode;
	}

	/**
	 * @return the packageName
	 */
	public String getPackageNameObjExt() {
		return packageNameObjExt;
	}

	/**
	 * @param packageName the packageName to set
	 */
	public void setPackageNameObjExt(String packageNameObjExt) {
		this.packageNameObjExt = packageNameObjExt;
	}

	public Boolean getFlagTotalCount() {
	    return flagTotalCount;
    }

	public void setFlagTotalCount(Boolean flagTotalCount) {
	    this.flagTotalCount = flagTotalCount;
    }

    /**
     * @return the displayType
     */
    public Integer getDisplayType() {
        return displayType;
    }

    /**
     * @param displayType the displayType to set
     */
    public void setDisplayType(Integer displayType) {
        this.displayType = displayType;
    }

    /**
     * @return the autoincrementFlg
     */
    public Integer getAutoincrementFlg() {
        return autoincrementFlg;
    }

    /**
     * @param autoincrementFlg the autoincrementFlg to set
     */
    public void setAutoincrementFlg(Integer autoincrementFlg) {
        this.autoincrementFlg = autoincrementFlg;
    }

	public String getInputColumnNo() {
		return inputColumnNo;
	}

	public void setInputColumnNo(String inputColumnNo) {
		this.inputColumnNo = inputColumnNo;
	}

	public FileFormat getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(FileFormat fileFormat) {
		this.fileFormat = fileFormat;
	}

	public String getOutputColumnNo() {
		return outputColumnNo;
	}

	public void setOutputColumnNo(String outputColumnNo) {
		this.outputColumnNo = outputColumnNo;
	}

	public ColumnFileFormat getColumnFileFormat() {
		return columnFileFormat;
	}

	public void setColumnFileFormat(ColumnFileFormat columnFileFormat) {
		this.columnFileFormat = columnFileFormat;
	}

	public Integer getTypeListInsertOrUpdate() {
		return typeListInsertOrUpdate;
	}

	public void setTypeListInsertOrUpdate(Integer typeListInsertOrUpdate) {
		this.typeListInsertOrUpdate = typeListInsertOrUpdate;
	}

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

	public Integer getFromOrTo() {
		return fromOrTo;
	}

	public void setFromOrTo(Integer fromOrTo) {
		this.fromOrTo = fromOrTo;
	}

	public String getBusinessLogicCode() {
	    return businessLogicCode;
    }

	public void setBusinessLogicCode(String businessLogicCode) {
	    this.businessLogicCode = businessLogicCode;
    }

	public Long getModuleId() {
	    return moduleId;
    }

	public void setModuleId(Long moduleId) {
	    this.moduleId = moduleId;
    }

	public List<ObjectDefinition> getLstChildrens() {
	    return lstChildrens;
    }

	public void setLstChildrens(List<ObjectDefinition> lstChildrens) {
	    this.lstChildrens = lstChildrens;
    }
    
    

}
