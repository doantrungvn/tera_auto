package org.terasoluna.qp.app.domaindatatype;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.DomainDatatypeMessage;

/**
 * Model class of domain_table_mapping_items.
 * 
 * @author dungnn1
 * @version 1.0
 */
public class DomainDatatypeItemForm implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private long domainDatatypeItemId;

	private long domainDatatypeId;

	@NotEmpty (message=DomainDatatypeMessage.SC_DOMAINDATATYPE_0010)
	//@Size(min = 1, max = 200, message = DomainDatatypeMessage.SC_DOMAINDATATYPE_0010 + ";" + DomainDatatypeConst.REQUIRED_MIN_INPUT_VAL +";" + CommonMessageConst.NAME_MAX_VAL)
	//@Pattern(regexp=DomainDatatypeConst.REGULAR_EXP_NAME, message = DomainDatatypeMessage.SC_DOMAINDATATYPE_0010 + ";" + CommonMessageConst.NAME_INPUTMASK)
	private String domainColumnName;

	@NotEmpty (message=DomainDatatypeMessage.SC_DOMAINDATATYPE_0011)
	//@Size(min = 1, max = 50, message = DomainDatatypeMessage.SC_DOMAINDATATYPE_0011 + ";" + DomainDatatypeConst.REQUIRED_MIN_INPUT_VAL + ";" + CommonMessageConst.CODE_MAX_VAL)
	//@Pattern(regexp = DomainDatatypeConst.REGULAR_EXP_CODE, message= DomainDatatypeMessage.SC_DOMAINDATATYPE_0011 + ";" + CommonMessageConst.CODE_INPUTMASK)
	private String domainColumnCode;

	private String keyType;

	@NotNull (message=DomainDatatypeMessage.SC_DOMAINDATATYPE_0012)
	private Long domainDataType;

	private Integer displayType;

	@NotNull (message=DomainDatatypeMessage.SC_DOMAINDATATYPE_0014)
	private Integer maxlength;

	private String minVal;

	private String maxVal;

	private int physicalDataType;

	private Long autocompleteId;
	
	private String autocompleteName;

	private int itemSeqNo;
	
	private String defaultValue;
	
	private String fmtCode;

	private String dataSource;
	
	private String msgLabel;
	
	private String msgValue;

	@Pattern(regexp = "[0-9a-zA-Z_]*", message= DomainDatatypeMessage.SC_DOMAINDATATYPE_0043 + ";" + CommonMessageConst.CODE_INPUTMASK)
	private String onChangeMethod;
	
	@Pattern(regexp = "[0-9a-zA-Z_]*", message= DomainDatatypeMessage.SC_DOMAINDATATYPE_0044 + ";" + CommonMessageConst.CODE_INPUTMASK)
	private String onSelectMethod;

	private Integer codelistType;
	
	private int supportOptionFlg;

	private String codelistName;
	
	/**
	 * 0: no change
	 * 1: remove from table design
	 * 2: add new from table design
	 */
	private int status = 0;

	private Integer groupBasetypeId;

	// for physical db
	private Long tblDesignDetailsId;

	private String tblDesignDetailsCode;

	private Integer maxlengthPhysical;

	private int requiredFlg;
	
	public long getDomainDatatypeItemId() {
		return domainDatatypeItemId;
	}

	public void setDomainDatatypeItemId(long domainDatatypeItemId) {
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

	public long getDomainDatatypeId() {
		return domainDatatypeId;
	}

	public void setDomainDatatypeId(long domainDatatypeId) {
		this.domainDatatypeId = domainDatatypeId;
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

	public int getRequiredFlg() {
		return requiredFlg;
	}

	public void setRequiredFlg(int requiredFlg) {
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

	/**
	 *  
	 * @return 0: not primary key - else 1: primary key
	 */
	/**
	 *  
	 * @return 0: not primary key - else 1: primary key
	 */
	public int isPrimaryKey() {
		if (keyType == null || keyType.isEmpty()) {
			return 0;
		}

		int iKeyType = Integer.parseInt(keyType, 2);

		if ((iKeyType & DbDomainConst.TblDesignKeyType.PK) == DbDomainConst.TblDesignKeyType.PK
				&& iKeyType != DbDomainConst.TblDesignKeyType.NONE ) {
			return 1;
		}

		return 0;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAutocompleteName() {
		return autocompleteName;
	}

	public void setAutocompleteName(String autocompleteName) {
		this.autocompleteName = autocompleteName;
	}

	public String getCodelistName() {
		return codelistName;
	}

	public void setCodelistName(String codelistName) {
		this.codelistName = codelistName;
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

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getFmtCode() {
		return fmtCode;
	}

	public void setFmtCode(String fmtCode) {
		this.fmtCode = fmtCode;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public int getSupportOptionFlg() {
		return supportOptionFlg;
	}

	public void setSupportOptionFlg(int supportOptionFlg) {
		this.supportOptionFlg = supportOptionFlg;
	}
	
}
