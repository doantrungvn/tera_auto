package org.terasoluna.qp.domain.model;

import java.io.Serializable;

import org.terasoluna.qp.app.common.constants.DbDomainConst;

/**
 * Model class of domain_table_mapping_items.
 * 
 * @author dungnn1
 * @version 1.0
 */
public class DomainDatatypeItem implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long domainDatatypeItemId;

	private Long domainDatatypeId;

	private String domainColumnName;

	private String domainColumnCode;

	private String keyType;

	private Long domainDataType;

	private Integer displayType;

	private Integer maxlength;

	private String minVal;

	private String maxVal;

	private int physicalDataType;

	private Long autocompleteId;
	
	private String autocompleteName;

	private int itemSeqNo;

	private String onChangeMethod;

	private String onSelectMethod;

	private Integer codelistType;

	private String codelistName;

	private Long codelistId;

	private Long domainId;

	private Integer groupBasetypeId;

	private String fmtCode;

	private String defaultValue;
	
	private String dataSource;
	
	private String msgLabel;
	
	private String msgValue;
	
	private Integer itemType;
	
	private Long datasourceId;
	
	private Integer datasourceType;
	
	private String defaultType;
	
	private Integer autoincrementFlg;
	
	private Integer decimalPart;
	
	private Integer constrainsType;
	
	private String domainName;
	
	private Integer dataTypeFlg;
	
	private String operatorCode;
	
	private Integer baseType;

	/**
	 * 0: no change
	 * 1: remove from table design
	 * 2: add new from table design
	 */
	private int status = 0;

	// for physical db
	private Long tblDesignDetailsId;

	private String tblDesignDetailsCode;

	private Integer maxlengthPhysical;

	private Integer requiredFlg;

	private MessageDesign messageDesign;
	
	public MessageDesign getMessageDesign() {
		return messageDesign;
	}

	public void setMessageDesign(MessageDesign messageDesign) {
		this.messageDesign = messageDesign;
	}

	public Long getDomainDatatypeItemId() {
		return domainDatatypeItemId;
	}

	public void setDomainDatatypeItemId(Long domainDatatypeItemId) {
		this.domainDatatypeItemId = domainDatatypeItemId;
	}
	
	/**
	 * Set the Display type.
	 * 
	 * @param displayType
	 *            Display type
	 */
	public void setDisplayType(Integer displayType) {
		this.displayType = displayType;
	}

	/**
	 * Get the Display type.
	 * 
	 * @return Display type
	 */
	public Integer getDisplayType() {
		return this.displayType;
	}

	/**
	 * Set the Max length.
	 * 
	 * @param maxlength
	 *            Max length
	 */
	public void setMaxlength(Integer maxlength) {
		this.maxlength = maxlength;
	}

	/**
	 * Get the Max length.
	 * 
	 * @return Max length
	 */
	public Integer getMaxlength() {
		return this.maxlength;
	}

	/**
	 * Set the Min value.
	 * 
	 * @param minVal
	 *            Min value
	 */
	public void setMinVal(String minVal) {
		this.minVal = minVal;
	}

	/**
	 * Get the Min value.
	 * 
	 * @return Min value
	 */
	public String getMinVal() {
		return this.minVal;
	}

	/**
	 * Set the Max value.
	 * 
	 * @param maxVal
	 *            Max value
	 */
	public void setMaxVal(String maxVal) {
		this.maxVal = maxVal;
	}

	/**
	 * Get the Max value.
	 * 
	 * @return Max value
	 */
	public String getMaxVal() {
		return this.maxVal;
	}

	/**
	 * Set the Physical data type.
	 * 
	 * @param physicalDataType
	 *            Physical data type
	 */
	public void setPhysicalDataType(int physicalDataType) {
		this.physicalDataType = physicalDataType;
	}

	/**
	 * Get the Physical data type.
	 * 
	 * @return Physical data type
	 */
	public int getPhysicalDataType() {
		return this.physicalDataType;
	}

	/**
	 * Set the Autocomplete id.
	 * 
	 * @param autocompleteId
	 *            Autocomplete id
	 */
	public void setAutocompleteId(Long autocompleteId) {
		this.autocompleteId = autocompleteId;
	}

	/**
	 * Get the Autocomplete id.
	 * 
	 * @return Autocomplete id
	 */
	public Long getAutocompleteId() {
		return this.autocompleteId;
	}

	/**
	 * Set the Item sequence no.
	 * 
	 * @param itemSeqNo
	 *            Item sequence no
	 */
	public void setItemSeqNo(int itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}

	/**
	 * Get the Item sequence no.
	 * 
	 * @return Item sequence no
	 */
	public int getItemSeqNo() {
		return this.itemSeqNo;
	}

	/**
	 * Set the On change method.
	 * 
	 * @param onChangeMethod
	 *            On change method
	 */
	public void setOnChangeMethod(String onChangeMethod) {
		this.onChangeMethod = onChangeMethod;
	}

	/**
	 * Get the On change method.
	 * 
	 * @return On change method
	 */
	public String getOnChangeMethod() {
		return this.onChangeMethod;
	}

	/**
	 * Set the On select method.
	 * 
	 * @param onSelectMethod
	 *            On select method
	 */
	public void setOnSelectMethod(String onSelectMethod) {
		this.onSelectMethod = onSelectMethod;
	}

	/**
	 * Get the On select method.
	 * 
	 * @return On select method
	 */
	public String getOnSelectMethod() {
		return this.onSelectMethod;
	}

	/**
	 * Set the Codelist type.
	 * 
	 * @param codelistType
	 *            Codelist type
	 */
	public void setCodelistType(Integer codelistType) {
		this.codelistType = codelistType;
	}

	/**
	 * Get the Codelist type.
	 * 
	 * @return Codelist type
	 */
	public Integer getCodelistType() {
		return this.codelistType;
	}

	public Long getTblDesignDetailsId() {
		return tblDesignDetailsId;
	}

	public void setTblDesignDetailsId(Long tblDesignDetailsId) {
		this.tblDesignDetailsId = tblDesignDetailsId;
	}

	public Long getDomainDatatypeId() {
		return domainDatatypeId;
	}

	public void setDomainDatatypeId(Long domainDatatypeId) {
		this.domainDatatypeId = domainDatatypeId;
	}

	public Long getDomainId() {
		return domainId;
	}

	public void setDomainId(Long domainId) {
		this.domainId = domainId;
	}

	public String getTblDesignDetailsCode() {
		return tblDesignDetailsCode;
	}

	public void setTblDesignDetailsCode(String tblDesignDetailsCode) {
		this.tblDesignDetailsCode = tblDesignDetailsCode;
	}

	public Integer getMaxlengthPhysical() {
		return maxlengthPhysical;
	}

	public void setMaxlengthPhysical(Integer maxlengthPhysical) {
		this.maxlengthPhysical = maxlengthPhysical;
	}

	public Integer getRequiredFlg() {
		return requiredFlg;
	}

	public void setRequiredFlg(Integer requiredFlg) {
		this.requiredFlg = requiredFlg;
	}

	public Integer getGroupBasetypeId() {
		return groupBasetypeId;
	}

	public void setGroupBasetypeId(Integer groupBasetypeId) {
		this.groupBasetypeId = groupBasetypeId;
	}

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public String getDomainColumnCode() {
		return domainColumnCode;
	}

	public void setDomainColumnCode(String domainColumnCode) {
		this.domainColumnCode = domainColumnCode;
	}

	public String getDomainColumnName() {
		return domainColumnName;
	}

	public void setDomainColumnName(String domainColumnName) {
		this.domainColumnName = domainColumnName;
	}

	public Long getDomainDataType() {
		return domainDataType;
	}

	public void setDomainDataType(Long domainDataType) {
		this.domainDataType = domainDataType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	/**
	 *  
	 * @return 0: not primary key - else 1: primary key
	 */
	public int isPrimaryKey() {
		if (keyType == null || keyType.isEmpty()) {
			return 0;
		}
		//convert key type from string to integer
		int iKeyType = Integer.parseInt(keyType, 2);

		//using and bit
		if ((iKeyType & DbDomainConst.TblDesignKeyType.PK) == DbDomainConst.TblDesignKeyType.PK
				&& iKeyType != DbDomainConst.TblDesignKeyType.NONE ) {
			return 1;
		}

		return 0;
	}

	public String getAutocompleteName() {
		return autocompleteName;
	}

	public void setAutocompleteName(String autocompleteName) {
		this.autocompleteName = autocompleteName;
	}

	public Long getCodelistId() {
		return codelistId;
	}

	public void setCodelistId(Long codelistId) {
		this.codelistId = codelistId;
	}

	public String getCodelistName() {
		return codelistName;
	}

	public void setCodelistName(String codelistName) {
		this.codelistName = codelistName;
	}

	public String getFmtCode() {
		return fmtCode;
	}

	public void setFmtCode(String fmtCode) {
		this.fmtCode = fmtCode;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getMsgLabel() {
		return msgLabel;
	}

	public void setMsgLabel(String msgLabel) {
		this.msgLabel = msgLabel;
	}

	public String getMsgValue() {
		return msgValue;
	}

	public void setMsgValue(String msgValue) {
		this.msgValue = msgValue;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public Integer getItemType() {
	    return itemType;
    }

	public void setItemType(Integer itemType) {
	    this.itemType = itemType;
    }

	public Long getDatasourceId() {
	    return datasourceId;
    }

	public void setDatasourceId(Long datasourceId) {
	    this.datasourceId = datasourceId;
    }

	public Integer getDatasourceType() {
	    return datasourceType;
    }

	public void setDatasourceType(Integer datasourceType) {
	    this.datasourceType = datasourceType;
    }

	public String getDefaultType() {
		return defaultType;
	}

	public void setDefaultType(String defaultType) {
		this.defaultType = defaultType;
	}

	public Integer getAutoincrementFlg() {
		return autoincrementFlg;
	}

	public void setAutoincrementFlg(Integer autoincrementFlg) {
		this.autoincrementFlg = autoincrementFlg;
	}

	public Integer getDecimalPart() {
		return decimalPart;
	}

	public void setDecimalPart(Integer decimalPart) {
		this.decimalPart = decimalPart;
	}

	public Integer getConstrainsType() {
		return constrainsType;
	}

	public void setConstrainsType(Integer constrainsType) {
		this.constrainsType = constrainsType;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public Integer getDataTypeFlg() {
		return dataTypeFlg;
	}

	public void setDataTypeFlg(Integer dataTypeFlg) {
		this.dataTypeFlg = dataTypeFlg;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public Integer getBaseType() {
		return baseType;
	}

	public void setBaseType(Integer baseType) {
		this.baseType = baseType;
	}
}
