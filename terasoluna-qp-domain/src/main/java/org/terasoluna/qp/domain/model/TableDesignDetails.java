package org.terasoluna.qp.domain.model;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeConst;

@XmlRootElement(namespace = "org.terasoluna.qp.domain.model.TableDesign")
public class TableDesignDetails implements Comparable<TableDesignDetails> {

	private Long columnId;
	private Long tableDesignId;
	private String tableDesignName;
	private String tableDesignCode;
	private Integer tableDesignType;
	private String name;
	private String code;
	private Integer isMandatory;
	private Long dataType;
	private String dataTypeName;
	private int autoIncrementFlag;
	private String defaultValue;
	private Integer defaultType;
	private Integer decimalPart;
	private List<TableDesignForeignKey> relationTag;
	private String remark;
	private String keyType;
	private int binKeyType;
	private Integer maxlength;
	/* private Integer groupOfDataType; */

	private Integer itemSeqNo;
	private String seqCode;
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
	private Integer groupBaseTypeId;
	private Integer baseType;
	private String patternFormat;
	
	private Integer itemType;
	private String fmtCode;
	private Boolean used;
	private Map<Integer, String> listItemtype;
	private Integer enabledFlag;
	private Boolean haveFkFlag;
	private Integer javaType;
	private Boolean isPkHidden;
	private String tableCode;
	private int commonColumn;
	private Long optionLabel;
	private Long optionValue;
	private String optionLabelAutocomplete;
	private String optionValueAutocomplete;
	private MessageDesign messageDesign;
	private String statusImport;
	private String contentStatusImport;

	// quangvd- It used for checking impact change design
	private Integer oldBaseType;
	private String domainName;

	// trungdv - It used for generate db & blogic from screen
	private Long screenItemId;

	
	
	public TableDesignDetails() {

	}

	public TableDesignDetails(String name, String code, Integer isMandatory, int autoincrement, Long dataType, String default_, String comment) {
		setName(name);
		setCode(code);
		setDataType(dataType);
		setIsMandatory(isMandatory);
		setDefaultValue(default_);
		setAutoIncrementFlag(autoincrement);
		setRemark(comment);
	}
	
	public TableDesignDetails(Long tableId, Long columnId) {
		setTableDesignId(tableId);
		setColumnId(columnId);
	}

	public String getCodelistCodeAutocomplete() {
		return codelistCodeAutocomplete;
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

	public void setCodelistCodeAutocomplete(String codelistCodeAutocomplete) {
		this.codelistCodeAutocomplete = codelistCodeAutocomplete;
	}

	public String getCodelistDefaultAutocomplete() {
		return codelistDefaultAutocomplete;
	}

	public void setCodelistDefaultAutocomplete(String codelistDefaultAutocomplete) {
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

	public void setIndexRow(int indexRow) {
		this.indexRow = indexRow;
	}

	public String getDataTypeAutocomplete() {
		return dataTypeAutocomplete;
	}

	public void setDataTypeAutocomplete(String dataTypeAutocomplete) {
		this.dataTypeAutocomplete = dataTypeAutocomplete;
	}

	@XmlElement(name = "default")
	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	// @XmlAttribute(name = "requiredFlg")
	// public int getRequiredFlg() {
	// return requiredFlg;
	// }
	//
	// public void setRequiredFlg(int requiredFlg) {
	// this.requiredFlg = requiredFlg;
	// }

	@XmlAttribute(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute(name = "code")
	public String getCode() {
		return StringUtils.lowerCase(code);
	}

	public void setCode(String code) {
		this.code = code;
	}

	@XmlAttribute(name = "length")
	public Integer getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(Integer maxLength) {
		this.maxlength = maxLength;
	}

	@XmlElement(name = "datatype")
	public Long getDataType() {
		return dataType;
	}

	public void setDataType(Long dataType) {
		this.dataType = dataType;
	}

	@XmlElement(name = "defaultType")
	public Integer getDefaultType() {
		return defaultType;
	}

	public void setDefaultType(Integer defaultType) {
		this.defaultType = defaultType;
	}

	@XmlAttribute(name = "precision")
	public Integer getDecimalPart() {
		return decimalPart;
	}

	public void setDecimalPart(Integer decimalPart) {
		this.decimalPart = decimalPart;
	}

	@XmlAttribute(name = "null")
	public Integer getIsMandatory() {
		return isMandatory == null ? DbDomainConst.YesNoFlg.NO : isMandatory;
	}

	public void setIsMandatory(Integer isMandatory) {
		this.isMandatory = isMandatory;
	}

	/*
	 * @XmlElement(name = "default") public String getDefault() { return defaultValue; }
	 * 
	 * public void setDefault(String default_) { this.defaultValue = default_; }
	 */

	@XmlAttribute(name = "autoincrement")
	public int getAutoIncrementFlag() {
		return autoIncrementFlag;
	}

	public void setAutoIncrementFlag(int autoIncrementFlag) {
		this.autoIncrementFlag = autoIncrementFlag;
	}

	public boolean hasRelation() {
		if (FunctionCommon.isNotEmpty(relationTag)) {
			return true;
		}
		return false;
	}

	@XmlAttribute(name = "columnid")
	public Long getColumnId() {
		return columnId;
	}

	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}

	@XmlAttribute(name = "tableid")
	public Long getTableDesignId() {
		return tableDesignId;
	}

	public void setTableDesignId(Long tableDesignId) {
		this.tableDesignId = tableDesignId;
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
		keyType = StringUtils.leftPad(keyType, DomainDatatypeConst.DatabaseKeyType.numOfKey, "0");

		this.binKeyType = binKeyType;
	}

	public Integer getItemSeqNo() {
		return itemSeqNo == null ? 0 : itemSeqNo;
	}

	public void setItemSeqNo(Integer itemSeqNo) {
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

	@Override
	public int compareTo(TableDesignDetails o) {
		if (this.getItemSeqNo() < o.getItemSeqNo()) {
			return -1;
		} else {
			return 1;
		}
	}

	/**
	 * check column has changed base type
	 * 
	 * @param o
	 * @return
	 */
	public boolean hasBeenChangedBasetype(TableDesignDetails o) {
		if (!this.groupBaseTypeId.equals(o.getGroupBaseTypeId())) {
			return true;
		}

		return false;
	}

	/**
	 * check column has changed format code
	 * 
	 * @param o
	 * @return
	 */
	public boolean hasBeenChangedFmt(TableDesignDetails o) {
		if (!StringUtils.equalsIgnoreCase(this.fmtCode, o.getFmtCode())) {
			return true;
		}

		return false;
	}

	/**
	 * check column has changed datatype
	 * 
	 * @param o
	 * @return
	 */
	public boolean hasBeenChangedDatatype(TableDesignDetails o) {
		if (!this.dataTypeFlg.equals(o.getDataTypeFlg())) {
			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("name=");
		str.append(getName());
		str.append(";code=");
		str.append(getCode());
		str.append("\n");
		return str.toString();

	}

	@XmlElement(name = "comment")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserDefineValue() {
		return userDefineValue;
	}

	public void setUserDefineValue(String userDefineValue) {
		this.userDefineValue = userDefineValue;
	}

	public String getFmtCode() {
		return fmtCode;
	}

	public void setFmtCode(String fmtCode) {
		this.fmtCode = fmtCode;
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

	public Boolean getUsed() {
		return used;
	}

	public void setUsed(Boolean used) {
		this.used = used;
	}

	@XmlElement(name = "group_base_type")
	public Integer getGroupBaseTypeId() {
		return groupBaseTypeId;
	}

	public void setGroupBaseTypeId(Integer groupBaseTypeId) {
		this.groupBaseTypeId = groupBaseTypeId;
	}

	public Integer getBaseType() {
		return baseType;
	}

	public void setBaseType(Integer baseType) {
		this.baseType = baseType;
	}

	@Override
	public boolean equals(Object object) {
		boolean result = false;
		if (object == null || object.getClass() != getClass()) {
			result = false;
		} else {
			TableDesignDetails tableDesignDetails = (TableDesignDetails) object;
			if (this.columnId.equals(tableDesignDetails.getColumnId())) {
				result = true;
			}
		}
		return result;
	}

	public String getTableDesignName() {
		return tableDesignName;
	}

	public void setTableDesignName(String tableDesignName) {
		this.tableDesignName = tableDesignName;
	}

	public String getTableDesignCode() {
		return tableDesignCode;
	}

	public void setTableDesignCode(String tableDesignCode) {
		this.tableDesignCode = tableDesignCode;
	}

	public Integer getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(Integer enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

	public MessageDesign getMessageDesign() {
		return messageDesign;
	}

	public void setMessageDesign(MessageDesign messageDesign) {
		this.messageDesign = messageDesign;
	}

	public Integer getOldBaseType() {
		return oldBaseType;
	}

	public void setOldBaseType(Integer oldBaseType) {
		this.oldBaseType = oldBaseType;
	}

	public Boolean getHaveFkFlag() {
		return haveFkFlag;
	}

	public void setHaveFkFlag(Boolean haveFkFlag) {
		this.haveFkFlag = haveFkFlag;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	@XmlAttribute(name = "screenitemid")
	public Long getScreenItemId() {
		return screenItemId;
	}

	public void setScreenItemId(Long screenItemId) {
		this.screenItemId = screenItemId;
	}

	public Integer getJavaType() {
		return javaType;
	}

	public void setJavaType(Integer javaType) {
		this.javaType = javaType;
	}

	public Boolean getIsPkHidden() {
		return isPkHidden;
	}

	public void setIsPkHidden(Boolean isPkHidden) {
		this.isPkHidden = isPkHidden;
	}

	@XmlElement(name = "relation")
	public List<TableDesignForeignKey> getForeignKeys() {
		return relationTag;
	}

	public void setForeignKeys(List<TableDesignForeignKey> relationTag) {
		this.relationTag = relationTag;
	}

	public String getSeqCode() {
		return seqCode;
	}

	public void setSeqCode(String seqCode) {
		this.seqCode = seqCode;
	}

	@XmlElement(name = "isCommonFlg")
	public int getCommonColumn() {
		return commonColumn;
	}

	public void setCommonColumn(int commonColumn) {
		this.commonColumn = commonColumn;
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
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

	public String getContentStatusImport() {
		return contentStatusImport;
	}

	public void setContentStatusImport(String contentStatusImport) {
		this.contentStatusImport = contentStatusImport;
	}

	public String getStatusImport() {
		return statusImport;
	}

	public void setStatusImport(String statusImport) {
		this.statusImport = statusImport;
	}

	public Integer getTableDesignType() {
		return tableDesignType;
	}

	public void setTableDesignType(Integer tableDesignType) {
		this.tableDesignType = tableDesignType;
	}

	public String getPatternFormat() {
		return patternFormat;
	}

	public void setPatternFormat(String patternFormat) {
		this.patternFormat = patternFormat;
	}
	
	public int getPrecision() {
		int maxlength = this.maxlength == null ? 0 : this.maxlength.intValue();
		int scale = this.decimalPart == null ? 0 : this.decimalPart.intValue();

		if (scale > maxlength) {
			return maxlength;
		}
		return maxlength - scale;
	}
}