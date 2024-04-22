package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.terasoluna.qp.app.common.constants.DbDomainConst;

public class ScreenItem implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Long screenItemId;
	
	private Integer itemSeqNo;
	
	private Long groupItemId;
	
	private String groupItemName;
	
	private Long screenAreaId;
	
	private String screenAreaCode;
	
	private Long screenId;
	
	private String screenCode;
	
	private String screenName;
	
	private Integer screenPatternType;
	
	private String itemCode;
	
	private String itemCodeH;
	
	private String itemName;
	
	private MessageDesign messageDesign;
	
	private Integer logicalDataType;
	
	private Integer physicalDataType;
	
	private Long domainTblMappingId;
	
	private Long domainTblMappingItemId;
	
	private Long tblDesignId;
	
	private String tblDesignName;
	
	private String tblDesignCode;
	
	private Long columnId;
	
	private String columnCode;
	
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
	
	private Timestamp sysDatetime;
	
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
	
	private String messageCode;
	
	private String defaultValue;
	
	private String defaultLabel;
	
	private String defaultValueTo;
	
	private Integer tabIndex;
	
	private Integer mandatoryFlg;
	
	private Integer delete;
	
	private ScreenForm screenForms;
	
	private Integer dataSourceType;
	
	private SqlDesign sqlDesign;
	
	private SqlDesignResult  optionLabel;
	
	private SqlDesignResult  optionValue;
	
	private Long screenItemStoreId;
	
	private String element;
	
	private Long screenFormId;
	
	private Long datasourceId;
	
	private String areaCode;
	
	private Boolean keyFlag;
	
	private Boolean btnModifyFlag;
	
	private Long moduleId;
	
	private String style;
	
	private String hoverStyle;
	
	private String icon;
	
	private Integer showLabel;
	
	private String fieldStyle;
	
	private Long outputBeanId;
	
	private String outputBeanCode;
	
	private String elementTemplate;
	
	private String trTemplate;
	
	private String parentOutputBeanCode;
		
	private String grandParentOutputBeanCode;
	
	private String parentOutputBeanArrayFlag;
	
	private String parentInputBeanCode;
	
	private String parentInputBeanCodeForTemp;
	
	private String grandParentInputBeanCode;
	
	private String parentInputBeanArrayFlag;
	
	private Long intputBeanId;
	
	private String inputBeanCode;
	
	private Integer enablePassword;
	
	private Integer allowAnyInput;
	
	private List<ScreenItemAutocompleteInput> screenItemAutocompleteInputs;
	
	private Integer mappingType;
	
	private String inputOnchange;
	
	private Integer fromScreenPatternType;
	
	private Integer toScreenPatternType;
	
	private Boolean isRegisterConfirm;
	
	private Boolean isPkHidden;
	
	private Integer buttonType;
	
	private Integer enableConfirm;
	
	private MessageDesign messageConfirm;
	
	private List<ScreenItemStatus> screenItemStatusLst;
	
	private Integer showBlankItem;
	
	private String customItemContent;
	
	private String customSectionContent;
	
	private Long inputBeanId;
	
	private String width;
	
	private String widthUnit;
	
	private Long navigateToBlogicId;
	
	private MessageDesign messageScreen;
	
	private String moduleName;
	
	private String codelistItemInfor;
	
	private Long screenItemIdCodeListId;
	
	private Long screenDesignIdCodeListId;
	
	private String screenDesignCodeCodeListId;
	
	private String screenItemTextCodeListId;
	
	private String screenDesignTextCodeListId;
	
	private Integer displayFromTo;
	
	private String localCodelist;
	
	private String codeListCodeNew;
	
	private Long screenTransitionId;
	
	private String ScreenTransitionName;
	
	private List<InputBean> inputBeans;
	
	private Boolean hasMandatoryFlgCheck;
	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Long getDatasourceId() {
		return datasourceId;
	}

	public void setDatasourceId(Long datasourceId) {
		this.datasourceId = datasourceId;
	}

	public SqlDesign getSqlDesign() {
		return sqlDesign;
	}

	public void setSqlDesign(SqlDesign sqlDesign) {
		this.sqlDesign = sqlDesign;
	}

	public SqlDesignResult getOptionLabel() {
		return optionLabel;
	}

	public void setOptionLabel(SqlDesignResult optionLabel) {
		this.optionLabel = optionLabel;
	}

	public SqlDesignResult getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(SqlDesignResult optionValue) {
		this.optionValue = optionValue;
	}

	public Integer getDataSourceType() {
		return dataSourceType;
	}

	public void setDataSourceType(Integer dataSourceType) {
		this.dataSourceType = dataSourceType;
	}

	private List<ScreenItemEvent> screenItemEvents;
	
	public List<ScreenItemCodelist> getListScreenItemCodelists() {
		return listScreenItemCodelists;
	}

	public void setListScreenItemCodelists(
			List<ScreenItemCodelist> listScreenItemCodelists) {
		this.listScreenItemCodelists = listScreenItemCodelists;
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

	public ScreenItemValidation getScreenItemValidation() {
		return screenItemValidation;
	}

	public void setScreenItemValidation(ScreenItemValidation screenItemValidation) {
		this.screenItemValidation = screenItemValidation;
	}

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

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

	public Integer getLogicalDataType() {
		return logicalDataType;
	}

	public void setLogicalDataType(Integer logicalDataType) {
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

	public Long getGroupItemId() {
		return groupItemId;
	}

	public void setGroupItemId(Long groupItemId) {
		this.groupItemId = groupItemId;
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

	public Long getAutocompleteId() {
		return autocompleteId;
	}

	public void setAutocompleteId(Long autocompleteId) {
		this.autocompleteId = autocompleteId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isBuldle() {
		return isBuldle;
	}

	public void setBuldle(boolean isBuldle) {
		this.isBuldle = isBuldle;
	}

	public Integer getItemGroupType() {
		return itemGroupType;
	}

	public void setItemGroupType(Integer itemGroupType) {
		this.itemGroupType = itemGroupType;
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
	
	@Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

	public ScreenArea getScreenArea() {
		return screenArea;
	}

	public void setScreenArea(ScreenArea screenArea) {
		this.screenArea = screenArea;
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

	public Integer getMandatoryFlg() {
		return mandatoryFlg;
	}

	public void setMandatoryFlg(Integer mandatoryFlg) {
		this.mandatoryFlg = mandatoryFlg;
	}

	public Integer getDelete() {
		return delete;
	}

	public void setDelete(Integer delete) {
		this.delete = delete;
	}

	public Timestamp getSysDatetime() {
		return sysDatetime;
	}

	public void setSysDatetime(Timestamp sysDatetime) {
		this.sysDatetime = sysDatetime;
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


	public List<ScreenItemEvent> getScreenItemEvents() {
		return screenItemEvents;
	}

	public void setScreenItemEvents(List<ScreenItemEvent> screenItemEvents) {
		this.screenItemEvents = screenItemEvents;
	}

	public String getItemName() {
	    return itemName;
    }

	public void setItemName(String itemName) {
	    this.itemName = itemName;
    }

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public Long getScreenItemStoreId() {
		return screenItemStoreId;
	}

	public void setScreenItemStoreId(Long screenItemStoreId) {
		this.screenItemStoreId = screenItemStoreId;
	}

	public Long getScreenFormId() {
	    return screenFormId;
    }

	public void setScreenFormId(Long screenFormId) {
	    this.screenFormId = screenFormId;
    }

	public String getAreaCode() {
	    return areaCode;
    }

	public void setAreaCode(String areaCode) {
	    this.areaCode = areaCode;
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

		if ((iKeyType & typeOfKey) == typeOfKey
				&& iKeyType != DbDomainConst.TblDesignKeyType.NONE) {
			return 1;
		}

		return 0;
	}

	public Boolean getKeyFlag() {
	    return keyFlag;
    }

	public void setKeyFlag(Boolean keyFlag) {
	    this.keyFlag = keyFlag;
    }

	public String getTblDesignName() {
	    return tblDesignName;
    }

	public void setTblDesignName(String tblDesignName) {
	    this.tblDesignName = tblDesignName;
    }

	public String getTblDesignCode() {
	    return tblDesignCode;
    }

	public void setTblDesignCode(String tblDesignCode) {
	    this.tblDesignCode = tblDesignCode;
    }

	public Boolean getBtnModifyFlag() {
	    return btnModifyFlag;
    }

	public void setBtnModifyFlag(Boolean btnModifyFlag) {
	    this.btnModifyFlag = btnModifyFlag;
    }

	public String getGroupItemName() {
		return groupItemName;
	}

	public void setGroupItemName(String groupItemName) {
		this.groupItemName = groupItemName;
	}

	public String getScreenAreaCode() {
		return screenAreaCode;
	}

	public void setScreenAreaCode(String screenAreaCode) {
		this.screenAreaCode = screenAreaCode;
	}

	public String getScreenCode() {
		return screenCode;
	}

	public void setScreenCode(String screenCode) {
		this.screenCode = screenCode;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getHoverStyle() {
		return hoverStyle;
	}

	public void setHoverStyle(String hoverStyle) {
		this.hoverStyle = hoverStyle;
	}

	public Integer getShowLabel() {
		return showLabel;
	}

	public void setShowLabel(Integer showLabel) {
		this.showLabel = showLabel;
	}

	public String getOutputBeanCode() {
		return outputBeanCode;
	}

	public void setOutputBeanCode(String outputBeanCode) {
		this.outputBeanCode = outputBeanCode;
	}

	public String getFieldStyle() {
		return fieldStyle;
	}

	public void setFieldStyle(String fieldStyle) {
		this.fieldStyle = fieldStyle;
	}

	public Integer getEnablePassword() {
		return enablePassword;
	}

	public void setEnablePassword(Integer enablePassword) {
		this.enablePassword = enablePassword;
	}

	public Integer getAllowAnyInput() {
		return allowAnyInput;
	}

	public void setAllowAnyInput(Integer allowAnyInput) {
		this.allowAnyInput = allowAnyInput;
	}


	public String getElementTemplate() {
		return elementTemplate;
	}

	public void setElementTemplate(String elementTemplate) {
		this.elementTemplate = elementTemplate;
	}

	public String getParentOutputBeanCode() {
		return parentOutputBeanCode;
	}

	public void setParentOutputBeanCode(String parentOutputBeanCode) {
		this.parentOutputBeanCode = parentOutputBeanCode;
	}

	public String getGrandParentOutputBeanCode() {
		return grandParentOutputBeanCode;
	}

	public void setGrandParentOutputBeanCode(String grandParentOutputBeanCode) {
		this.grandParentOutputBeanCode = grandParentOutputBeanCode;
	}

	public Long getIntputBeanId() {
		return intputBeanId;
	}

	public void setIntputBeanId(Long intputBeanId) {
		this.intputBeanId = intputBeanId;
	}

	public String getInputBeanCode() {
		return inputBeanCode;
	}

	public void setInputBeanCode(String inputBeanCode) {
		this.inputBeanCode = inputBeanCode;
	}

	public String getParentInputBeanCode() {
		return parentInputBeanCode;
	}

	public void setParentInputBeanCode(String parentInputBeanCode) {
		this.parentInputBeanCode = parentInputBeanCode;
	}

	public String getGrandParentInputBeanCode() {
		return grandParentInputBeanCode;
	}

	public void setGrandParentInputBeanCode(String grandParentInputBeanCode) {
		this.grandParentInputBeanCode = grandParentInputBeanCode;
	}

	public String getParentOutputBeanArrayFlag() {
		return parentOutputBeanArrayFlag;
	}

	public void setParentOutputBeanArrayFlag(String parentOutputBeanArrayFlag) {
		this.parentOutputBeanArrayFlag = parentOutputBeanArrayFlag;
	}

	public String getParentInputBeanArrayFlag() {
		return parentInputBeanArrayFlag;
	}

	public void setParentInputBeanArrayFlag(String parentInputBeanArrayFlag) {
		this.parentInputBeanArrayFlag = parentInputBeanArrayFlag;
	}

	public String getParentInputBeanCodeForTemp() {
		return parentInputBeanCodeForTemp;
	}

	public void setParentInputBeanCodeForTemp(String parentInputBeanCodeForTemp) {
		this.parentInputBeanCodeForTemp = parentInputBeanCodeForTemp;
	}

	public List<ScreenItemAutocompleteInput> getScreenItemAutocompleteInputs() {
		return screenItemAutocompleteInputs;
	}

	public void setScreenItemAutocompleteInputs(List<ScreenItemAutocompleteInput> screenItemAutocompleteInputs) {
		this.screenItemAutocompleteInputs = screenItemAutocompleteInputs;
	}

	public Integer getMappingType() {
		return mappingType;
	}

	public void setMappingType(Integer mappingType) {
		this.mappingType = mappingType;
	}

	public String getInputOnchange() {
		return inputOnchange;
	}

	public void setInputOnchange(String inputOnchange) {
		this.inputOnchange = inputOnchange;
	}

	public Integer getFromScreenPatternType() {
		return fromScreenPatternType;
	}

	public void setFromScreenPatternType(Integer fromScreenPatternType) {
		this.fromScreenPatternType = fromScreenPatternType;
	}

	public Integer getToScreenPatternType() {
		return toScreenPatternType;
	}

	public void setToScreenPatternType(Integer toScreenPatternType) {
		this.toScreenPatternType = toScreenPatternType;
	}

	public Boolean getIsRegisterConfirm() {
		return isRegisterConfirm;
	}

	public void setIsRegisterConfirm(Boolean isRegisterConfirm) {
		this.isRegisterConfirm = isRegisterConfirm;
	}

	public Boolean getIsPkHidden() {
	    return isPkHidden;
    }

	public void setIsPkHidden(Boolean isPkHidden) {
	    this.isPkHidden = isPkHidden;
    }

	public Integer getScreenPatternType() {
		return screenPatternType;
	}

	public void setScreenPatternType(Integer screenPatternType) {
		this.screenPatternType = screenPatternType;
	}

	public String getTrTemplate() {
		return trTemplate;
	}

	public void setTrTemplate(String trTemplate) {
		this.trTemplate = trTemplate;
	}

	public Integer getButtonType() {
		return buttonType;
	}

	public void setButtonType(Integer buttonType) {
		this.buttonType = buttonType;
	}

	public Integer getEnableConfirm() {
		return enableConfirm;
	}

	public void setEnableConfirm(Integer enableConfirm) {
		this.enableConfirm = enableConfirm;
	}

	public MessageDesign getMessageConfirm() {
		return messageConfirm;
	}

	public void setMessageConfirm(MessageDesign messageConfirm) {
		this.messageConfirm = messageConfirm;
	}

	public List<ScreenItemStatus> getScreenItemStatusLst() {
		return screenItemStatusLst;
	}

	public void setScreenItemStatusLst(List<ScreenItemStatus> screenItemStatusLst) {
		this.screenItemStatusLst = screenItemStatusLst;
	}

	public String getColumnCode() {
		return columnCode;
	}

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}

	public Integer getShowBlankItem() {
		return showBlankItem;
	}

	public void setShowBlankItem(Integer showBlankItem) {
		this.showBlankItem = showBlankItem;
	}

	public String getCustomItemContent() {
		return customItemContent;
	}

	public void setCustomItemContent(String customItemContent) {
		this.customItemContent = customItemContent;
	}

	public String getCustomSectionContent() {
		return customSectionContent;
	}

	public void setCustomSectionContent(String customSectionContent) {
		this.customSectionContent = customSectionContent;
	}

	public Long getInputBeanId() {
		return inputBeanId;
	}

	public void setInputBeanId(Long inputBeanId) {
		this.inputBeanId = inputBeanId;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getWidthUnit() {
		return widthUnit;
	}

	public void setWidthUnit(String widthUnit) {
		this.widthUnit = widthUnit;
	}

	public Long getNavigateToBlogicId() {
		return navigateToBlogicId;
	}

	public void setNavigateToBlogicId(Long navigateToBlogicId) {
		this.navigateToBlogicId = navigateToBlogicId;
	}

	public String getItemCodeH() {
		return itemCodeH;
	}

	public void setItemCodeH(String itemCodeH) {
		this.itemCodeH = itemCodeH;
	}

	public MessageDesign getMessageScreen() {
		return messageScreen;
	}

	public void setMessageScreen(MessageDesign messageScreen) {
		this.messageScreen = messageScreen;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
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

	public Long getScreenItemIdCodeListId() {
		return screenItemIdCodeListId;
	}

	public void setScreenItemIdCodeListId(Long screenItemIdCodeListId) {
		this.screenItemIdCodeListId = screenItemIdCodeListId;
	}

	public Long getScreenDesignIdCodeListId() {
		return screenDesignIdCodeListId;
	}

	public void setScreenDesignIdCodeListId(Long screenDesignIdCodeListId) {
		this.screenDesignIdCodeListId = screenDesignIdCodeListId;
	}

	public String getScreenItemTextCodeListId() {
		return screenItemTextCodeListId;
	}

	public void setScreenItemTextCodeListId(String screenItemTextCodeListId) {
		this.screenItemTextCodeListId = screenItemTextCodeListId;
	}

	public String getScreenDesignTextCodeListId() {
		return screenDesignTextCodeListId;
	}

	public void setScreenDesignTextCodeListId(String screenDesignTextCodeListId) {
		this.screenDesignTextCodeListId = screenDesignTextCodeListId;
	}
	public Integer getDisplayFromTo() {
		return displayFromTo;
	}

	public void setDisplayFromTo(Integer displayFromTo) {
		this.displayFromTo = displayFromTo;
	}

	public String getScreenDesignCodeCodeListId() {
		return screenDesignCodeCodeListId;
	}

	public void setScreenDesignCodeCodeListId(String screenDesignCodeCodeListId) {
		this.screenDesignCodeCodeListId = screenDesignCodeCodeListId;
	}

	public String getLocalCodelist() {
		return localCodelist;
	}

	public void setLocalCodelist(String localCodelist) {
		this.localCodelist = localCodelist;
	}

	public String getCodeListCodeNew() {
		return codeListCodeNew;
	}

	public void setCodeListCodeNew(String codeListCodeNew) {
		this.codeListCodeNew = codeListCodeNew;
	}

	public Long getScreenTransitionId() {
		return screenTransitionId;
	}

	public void setScreenTransitionId(Long screenTransitionId) {
		this.screenTransitionId = screenTransitionId;
	}

	public String getScreenTransitionName() {
		return ScreenTransitionName;
	}

	public void setScreenTransitionName(String screenTransitionName) {
		ScreenTransitionName = screenTransitionName;
	}

	public List<InputBean> getInputBeans() {
		return inputBeans;
	}

	public void setInputBeans(List<InputBean> inputBeans) {
		this.inputBeans = inputBeans;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public Boolean getHasMandatoryFlgCheck() {
		return hasMandatoryFlgCheck;
	}

	public void setHasMandatoryFlgCheck(Boolean hasMandatoryFlgCheck) {
		this.hasMandatoryFlgCheck = hasMandatoryFlgCheck;
	}
	
	
}
