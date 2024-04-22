package org.terasoluna.qp.app.screendesign;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.terasoluna.qp.domain.model.AutocompleteDesign;
import org.terasoluna.qp.domain.model.MessageDesign;
import org.terasoluna.qp.domain.model.ScreenAction;
import org.terasoluna.qp.domain.model.ScreenArea;
import org.terasoluna.qp.domain.model.ScreenForm;
import org.terasoluna.qp.domain.model.ScreenItemCodelist;
import org.terasoluna.qp.domain.model.ScreenItemValidation;

public class ScreenItemForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long screenItemId;
	
	private Integer itemSeqNo;
	
	private Long groupItemId;
	
	private Long screenAreaId;
	
	private Long screenId;
	
	private String itemCode;
	
	private String itemCodeH;
	
	private MessageDesign messageDesign;
	
	private Long logicalDataType;
	
	private Integer physicalDataType;
	
	private Long domainTblMappingId;
	
	private Long domainTblMappingItemId;
	
	private Long tblDesignId;
	
	private Long columnId;
	
	private List<ScreenItemCodelist> listScreenItemCodelists;
	
	private Integer colSpan;
	
	private Integer rowSpan;
	
	private Integer itemType;
	
	private Long autocompleteId;
	
	private Integer arrayFlg;
	
	private Long screenActionId;
	
	private String keyType;
	
	private ScreenItemValidation screenItemValidation;
	
	private Long createdBy;
	
	private Timestamp createdDate;
	
	private Long updatedBy;
	
	private Timestamp updatedDate; 
	
	private String itemWidthUnit;
	
	private Long codelistId;
	
	private Integer codelistType;
	
	private String value;
	
	private Integer itemGroupType;
	
	private boolean isBuldle = false;
	
	private AutocompleteDesign autocompleteDesign;
	
	private String codelistText = "";
	
	private ScreenAction screenAction;
	
	private String domainColumnName;
	
	private String domainTblMappingName;
	
	private String domainCodelistType;
	
	private String domainCodelistId;
	
	private ScreenArea screenArea;
	
	private Long sqlDesignId;
	
	private Long userDefineCodelistId;
	
	private Long outputBeanId;
	
	private ScreenForm screenForms;
	
	private String messageCodeAutocomplete;
	
	private String messageCode;
	
	private String defaultValue;
	
	private String defaultLabel;
	
	private String defaultValueTo;
	
	private Integer tabIndex;
	
	private String nameLogicalDataType;
	
	private Integer delete;
	
	private Integer mandatoryFlg;
	
	private Boolean hasMandatoryFlgCheck;
	
	private Boolean hasSelectItemName;
	
	private String codelistItemInfor;
	
	private Integer dataSourceType;

	public Long getScreenItemId() {
		return screenItemId;
	}

	public void setScreenItemId(Long screenItemId) {
		this.screenItemId = screenItemId;
	}

	public Integer getItemSeqNo() {
		return itemSeqNo;
	}

	public void setItemSeqNo(Integer itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}

	public Long getGroupItemId() {
		return groupItemId;
	}

	public void setGroupItemId(Long groupItemId) {
		this.groupItemId = groupItemId;
	}

	public Long getScreenAreaId() {
		return screenAreaId;
	}

	public void setScreenAreaId(Long screenAreaId) {
		this.screenAreaId = screenAreaId;
	}

	public Long getScreenId() {
		return screenId;
	}

	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public MessageDesign getMessageDesign() {
		return messageDesign;
	}

	public void setMessageDesign(MessageDesign messageDesign) {
		this.messageDesign = messageDesign;
	}

	public Long getLogicalDataType() {
		return logicalDataType;
	}

	public void setLogicalDataType(Long logicalDataType) {
		this.logicalDataType = logicalDataType;
	}

	public Integer getPhysicalDataType() {
		return physicalDataType;
	}

	public void setPhysicalDataType(Integer physicalDataType) {
		this.physicalDataType = physicalDataType;
	}

	public Long getDomainTblMappingId() {
		return domainTblMappingId;
	}

	public void setDomainTblMappingId(Long domainTblMappingId) {
		this.domainTblMappingId = domainTblMappingId;
	}

	public Long getDomainTblMappingItemId() {
		return domainTblMappingItemId;
	}

	public void setDomainTblMappingItemId(Long domainTblMappingItemId) {
		this.domainTblMappingItemId = domainTblMappingItemId;
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

	public List<ScreenItemCodelist> getListScreenItemCodelists() {
		return listScreenItemCodelists;
	}

	public void setListScreenItemCodelists(
			List<ScreenItemCodelist> listScreenItemCodelists) {
		this.listScreenItemCodelists = listScreenItemCodelists;
	}

	public Integer getColSpan() {
		return colSpan;
	}

	public void setColSpan(Integer colSpan) {
		this.colSpan = colSpan;
	}

	public Integer getRowSpan() {
		return rowSpan;
	}

	public void setRowSpan(Integer rowSpan) {
		this.rowSpan = rowSpan;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public Long getAutocompleteId() {
		return autocompleteId;
	}

	public void setAutocompleteId(Long autocompleteId) {
		this.autocompleteId = autocompleteId;
	}

	public Integer getArrayFlg() {
		return arrayFlg;
	}

	public void setArrayFlg(Integer arrayFlg) {
		this.arrayFlg = arrayFlg;
	}

	public Long getScreenActionId() {
		return screenActionId;
	}

	public void setScreenActionId(Long screenActionId) {
		this.screenActionId = screenActionId;
	}

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public ScreenItemValidation getScreenItemValidation() {
		return screenItemValidation;
	}

	public void setScreenItemValidation(ScreenItemValidation screenItemValidation) {
		this.screenItemValidation = screenItemValidation;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getItemWidthUnit() {
		return itemWidthUnit;
	}

	public void setItemWidthUnit(String itemWidthUnit) {
		this.itemWidthUnit = itemWidthUnit;
	}

	public Long getCodelistId() {
		return codelistId;
	}

	public void setCodelistId(Long codelistId) {
		this.codelistId = codelistId;
	}

	public Integer getCodelistType() {
		return codelistType;
	}

	public void setCodelistType(Integer codelistType) {
		this.codelistType = codelistType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getItemGroupType() {
		return itemGroupType;
	}

	public void setItemGroupType(Integer itemGroupType) {
		this.itemGroupType = itemGroupType;
	}

	public boolean isBuldle() {
		return isBuldle;
	}

	public void setBuldle(boolean isBuldle) {
		this.isBuldle = isBuldle;
	}

	public AutocompleteDesign getAutocompleteDesign() {
		return autocompleteDesign;
	}

	public void setAutocompleteDesign(AutocompleteDesign autocompleteDesign) {
		this.autocompleteDesign = autocompleteDesign;
	}

	public String getCodelistText() {
		return codelistText;
	}

	public void setCodelistText(String codelistText) {
		this.codelistText = codelistText;
	}

	public ScreenAction getScreenAction() {
		return screenAction;
	}

	public void setScreenAction(ScreenAction screenAction) {
		this.screenAction = screenAction;
	}

	public String getDomainColumnName() {
		return domainColumnName;
	}

	public void setDomainColumnName(String domainColumnName) {
		this.domainColumnName = domainColumnName;
	}

	public String getDomainTblMappingName() {
		return domainTblMappingName;
	}

	public void setDomainTblMappingName(String domainTblMappingName) {
		this.domainTblMappingName = domainTblMappingName;
	}

	public String getDomainCodelistType() {
		return domainCodelistType;
	}

	public void setDomainCodelistType(String domainCodelistType) {
		this.domainCodelistType = domainCodelistType;
	}

	public String getDomainCodelistId() {
		return domainCodelistId;
	}

	public void setDomainCodelistId(String domainCodelistId) {
		this.domainCodelistId = domainCodelistId;
	}

	public ScreenArea getScreenArea() {
		return screenArea;
	}

	public void setScreenArea(ScreenArea screenArea) {
		this.screenArea = screenArea;
	}

	public Long getSqlDesignId() {
		return sqlDesignId;
	}

	public void setSqlDesignId(Long sqlDesignId) {
		this.sqlDesignId = sqlDesignId;
	}

	public Long getUserDefineCodelistId() {
		return userDefineCodelistId;
	}

	public void setUserDefineCodelistId(Long userDefineCodelistId) {
		this.userDefineCodelistId = userDefineCodelistId;
	}

	public Long getOutputBeanId() {
		return outputBeanId;
	}

	public void setOutputBeanId(Long outputBeanId) {
		this.outputBeanId = outputBeanId;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Integer getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(Integer tabIndex) {
		this.tabIndex = tabIndex;
	}

	public String getNameLogicalDataType() {
		return nameLogicalDataType;
	}

	public void setNameLogicalDataType(String nameLogicalDataType) {
		this.nameLogicalDataType = nameLogicalDataType;
	}

	public Integer getDelete() {
		return delete;
	}

	public void setDelete(Integer delete) {
		this.delete = delete;
	}

	public Integer getMandatoryFlg() {
		return mandatoryFlg;
	}

	public void setMandatoryFlg(Integer mandatoryFlg) {
		this.mandatoryFlg = mandatoryFlg;
	}

	public Boolean getHasMandatoryFlgCheck() {
		return hasMandatoryFlgCheck;
	}

	public void setHasMandatoryFlgCheck(Boolean hasMandatoryFlgCheck) {
		this.hasMandatoryFlgCheck = hasMandatoryFlgCheck;
	}

	public String getDefaultValueTo() {
		return defaultValueTo;
	}

	public void setDefaultValueTo(String defaultValueTo) {
		this.defaultValueTo = defaultValueTo;
	}

	public ScreenForm getScreenForms() {
		return screenForms;
	}

	public void setScreenForms(ScreenForm screenForms) {
		this.screenForms = screenForms;
	}

	public Boolean getHasSelectItemName() {
		return hasSelectItemName;
	}

	public void setHasSelectItemName(Boolean hasSelectItemName) {
		this.hasSelectItemName = hasSelectItemName;
	}

	public String getMessageCodeAutocomplete() {
		if (messageDesign != null && messageDesign.getMessageString() != null) {
			return messageDesign.getMessageString();
		}
		return messageCodeAutocomplete;
	}

	public void setMessageCodeAutocomplete(String messageCodeAutocomplete) {
		this.messageCodeAutocomplete = messageCodeAutocomplete;
	}

	public String getItemCodeH() {
		return itemCodeH;
	}

	public void setItemCodeH(String itemCodeH) {
		this.itemCodeH = itemCodeH;
	}

	public String getCodelistItemInfor() {
		return codelistItemInfor;
	}

	public void setCodelistItemInfor(String codelistItemInfor) {
		this.codelistItemInfor = codelistItemInfor;
	}

	public String getDefaultLabel() {
		return defaultLabel;
	}

	public void setDefaultLabel(String defaultLabel) {
		this.defaultLabel = defaultLabel;
	}

	public Integer getDataSourceType() {
		return dataSourceType;
	}

	public void setDataSourceType(Integer dataSourceType) {
		this.dataSourceType = dataSourceType;
	}
	
}
