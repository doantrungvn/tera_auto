package org.terasoluna.qp.app.tabledesign;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.message.TableDesignMessageConst;
import org.terasoluna.qp.domain.model.TableDesignForeignKey;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeConst;

public class TableDesignDetailsForm implements Serializable {

	/**
	 * Declare Serial Version
	 */
	private static final long serialVersionUID = 1L;

	private Long columnId;

	private Long tableDesignId;
	
	private String name;

	private String code;
	
	@NotEmpty(message = TableDesignMessageConst.SC_TABLEDESIGN_0007)
	private String dataType;
	
	private String dataTypeName;
	private boolean isMandatory = false;
	private int autoIncrementFlag;
	private String defaultValue;
	private Integer defaultType;
	private Integer decimalPart;
	private TableDesignForeignKey relationTag;
	private String remark;
	private String keyType;
	private int binKeyType;
	private Integer maxlength;
	private Integer groupOfDataType;
	private int itemSeqNo;
	private int indexRow;
	private String dataTypeAutocomplete;
	private Integer dataTypeFlg;
	private Integer constrainsType;
	private Long datasourceId;
	private Integer datasourceType;
	private String operatorCode;
	private String minVal;
	private String maxVal;
	private String codelistCodeAutocomplete;
	private String codelistDefaultAutocomplete;
	private String sqlCodeAutocomplete;
	private String userDefineValue;
	private Integer supportOptionFlg;
	private Integer displayType;
	private boolean used;
	private Integer baseType;
	private Integer groupBaseTypeId;
	private Integer itemType;
	private String fmtCode;
	private Integer javaType;
	private Map<Integer, String> listItemtype;
	private int commonColumn;
	private Long optionLabel;
	private Long optionValue;
	private String optionLabelAutocomplete;
	private String optionValueAutocomplete;

	public TableDesignDetailsForm() {

	}

	public String getCodelistCodeAutocomplete() {
		return codelistCodeAutocomplete;
	}

	public void setCodelistCodeAutocomplete(String codelistCodeAutocomplete) {
		this.codelistCodeAutocomplete = codelistCodeAutocomplete;
	}

	public String getCodelistDefaultAutocomplete() {
		return codelistDefaultAutocomplete;
	}

	public void setCodelistDefaultAutocomplete(
			String codelistDefaultAutocomplete) {
		this.codelistDefaultAutocomplete = codelistDefaultAutocomplete;
	}

	public String getSqlCodeAutocomplete() {
		return sqlCodeAutocomplete;
	}

	public void setSqlCodeAutocomplete(String sqlCodeAutocomplete) {
		this.sqlCodeAutocomplete = sqlCodeAutocomplete;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public String getMinVal() {
		return minVal;
	}

	public void setMinVal(String minVal) {
		this.minVal = minVal;
	}

	public String getMaxVal() {
		return maxVal;
	}

	public void setMaxVal(String maxVal) {
		this.maxVal = maxVal;
	}

	public int getIndexRow() {
		return indexRow;
	}

	public String getFmtCode() {
		return fmtCode;
	}

	public void setFmtCode(String fmtCode) {
		this.fmtCode = fmtCode;
	}

	public void setIndexRow(int indexRow) {
		this.indexRow = indexRow;
	}

	public String getDataTypeAutocomplete() {
		return dataTypeAutocomplete;
	}

	public void setDataTypeAutocomplete(String dataTypeAutocomplete) {
		this.dataTypeAutocomplete = dataTypeAutocomplete;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(Integer maxLength) {
		this.maxlength = maxLength;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Integer getDefaultType() {
		return defaultType;
	}

	public void setDefaultType(Integer defaultType) {
		this.defaultType = defaultType;
	}

	public Integer getDecimalPart() {
		return decimalPart;
	}

	public void setDecimalPart(Integer decimalPart) {
		this.decimalPart = decimalPart;
	}

	public boolean getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public int getAutoIncrementFlag() {
		return autoIncrementFlag;
	}

	public void setAutoIncrementFlag(int autoIncrementFlag) {
		this.autoIncrementFlag = autoIncrementFlag;
	}

	public TableDesignForeignKey getForeignKey() {
		return relationTag;
	}

	public void setForeignKey(TableDesignForeignKey relationTag) {
		this.relationTag = relationTag;
	}

	public boolean hasRelation() {
		if (getForeignKey() != null) {
			return true;
		}
		return false;
	}

	public Long getColumnId() {
		return columnId;
	}

	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public int getBinKeyType() {
		return binKeyType;
	}

	public void setBinKeyType(int binKeyType) {
		keyType = Integer.toBinaryString(binKeyType);
		keyType = StringUtils.leftPad(keyType,
				DomainDatatypeConst.DatabaseKeyType.numOfKey, "0");

		this.binKeyType = binKeyType;
	}

	public Integer getGroupOfDataType() {
		return groupOfDataType;
	}

	public void setGroupOfDataType(Integer groupOfDataType) {
		this.groupOfDataType = groupOfDataType;
	}

	public int getItemSeqNo() {
		return itemSeqNo;
	}

	public void setItemSeqNo(int itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}

	/**
	 * 
	 * @return 0: not primary key - else 1: primary key
	 */
	public int isPrimaryKey() {
		return isKey(DbDomainConst.TblDesignKeyType.PK);
	}

	/**
	 * 
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
	
	public String getUserDefineValue() {
		return userDefineValue;
	}

	public void setUserDefineValue(String userDefineValue) {
		this.userDefineValue = userDefineValue;
	}

	public Integer getDataTypeFlg() {
		return dataTypeFlg;
	}

	public void setDataTypeFlg(Integer dataTypeFlg) {
		this.dataTypeFlg = dataTypeFlg;
	}

	public Integer getConstrainsType() {
		return constrainsType;
	}

	public void setConstrainsType(Integer constrainsType) {
		this.constrainsType = constrainsType;
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

	public Integer getSupportOptionFlg() {
		return supportOptionFlg;
	}

	public void setSupportOptionFlg(Integer supportOptionFlg) {
		this.supportOptionFlg = supportOptionFlg;
	}

	public Integer getDisplayType() {
		return displayType;
	}

	public void setDisplayType(Integer displayType) {
		this.displayType = displayType;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public Map<Integer, String> getListItemtype() {
		return listItemtype;
	}

	public void setListItemtype(Map<Integer, String> listItemtype) {
		this.listItemtype = listItemtype;
	}

	public String getDataTypeName() {
		return dataTypeName;
	}

	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public Integer getGroupBaseTypeId() {
		return groupBaseTypeId;
	}

	public void setGroupBaseTypeId(Integer groupBaseTypeId) {
		this.groupBaseTypeId = groupBaseTypeId;
	}

	public Long getTableDesignId() {
		return tableDesignId;
	}

	public void setTableDesignId(Long tableDesignId) {
		this.tableDesignId = tableDesignId;
	}

	public Integer getJavaType() {
		return javaType;
	}

	public void setJavaType(Integer javaType) {
		this.javaType = javaType;
	}

	public int getCommonColumn() {
		return commonColumn;
	}

	public void setCommonColumn(int commonColumn) {
		this.commonColumn = commonColumn;
	}

	public Long getOptionLabel() {
		return optionLabel;
	}

	public void setOptionLabel(Long optionLabel) {
		this.optionLabel = optionLabel;
	}

	public Long getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(Long optionValue) {
		this.optionValue = optionValue;
	}

	public String getOptionLabelAutocomplete() {
		return optionLabelAutocomplete;
	}

	public void setOptionLabelAutocomplete(String optionLabelAutocomplete) {
		this.optionLabelAutocomplete = optionLabelAutocomplete;
	}

	public String getOptionValueAutocomplete() {
		return optionValueAutocomplete;
	}

	public void setOptionValueAutocomplete(String optionValueAutocomplete) {
		this.optionValueAutocomplete = optionValueAutocomplete;
	}

	public Integer getBaseType() {
		return baseType;
	}

	public void setBaseType(Integer baseType) {
		this.baseType = baseType;
	}
}
