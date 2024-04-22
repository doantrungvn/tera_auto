package org.terasoluna.qp.domain.service.screendesign;

import org.apache.commons.lang3.StringUtils;

public class ScreenDesignOutput {
	private String label;

	private String datatype;

	private String physicaldatatype;

	private String columnname;

	private String rowspan;

	private String colspan;

	private String datasource;

	private String minvalue;

	private String maxvalue;

	private String formatcode;

	private String tablename;

	private String tablecode;

	private String tablecolumnname;

	private String tablecolumncode;

	private String connectionmsg;

	private String transitiontype;

	private String actiontype;

	private String parameters;

	private String screenactionid;

	private String linklabel;

	private String toscreenid;

	private String maxlength;

	private String physicalmaxlength;

	private String msglabel;

	private String msgvalue;

	private String require;

	private String elementtype;

	private String onchangemethod;

	private String onselectmethod;

	private String autocompleteid;

	private String codelisttype;

	private String codelistcode;

	private String datatypecode;

	private String width = "";

	private String widthunit = "";

	private String mandatory = "";

	private String validateRule = "";

	private String enablegroup = "";

	private String labelText = "";

	private String isBundle = "";

	private String isBlank = "";
	
	private String msgcode = "";
	
	private String navigateTo = "";
	
	private String navigateToText = "";
	
	private String isSubmit = "";
	
	private String actionName="";
	 
	private String groupitemtype=""; 
	
	private String dialogAutocompleteCode = "";
	
	private String dialogAutocompleteText = "";
	
	private String dialogOnChangeEvent = "";
	
	private String dialogOnSelectEvent = "";
	
	private String baseType = "";
	
	private String isEnable = "";
	
	private String codelistCode = "";
	
	private String codelistText = "";
	
	private String localCodelist = "";
	
	private String isSupportOptionValue = "";
	
	private String groupDisplayType = "";
	
	private String outputBeanId = "";
	
	private String columndisplay = "";
	
	private String outputBeanLevel = "";
	
	private Event[] events;
	
	private String tabindex;
	
	private String datasourcetype;
	
	private String sqldesignid;
	
	private String optionlabel;
	
	private String optionvalue;
	
	private String sqldesignidtext;
	
	private String optionlabeltext;
	
	private String optionvaluetext;
	
	private String defaultvalue;
	
	private String dataSourceId;
	
	private String toScreenCode;
	
	private String screenItemId;
	
	private String style;
	
	private String hoverStyle;
	
	private String icon;
	
	private String showLabel;
	
	private String enablePassword;
	
	private String allowAnyInput;
	
	private String itemType;
	
	private String buttonStyle;
	
	private String inputStyle;
	
	private String headerStyle;
	
	private String navigateToBlogic;
	
	private String navigateToBlogicText;
	
	private String navigateToBlogicCode;
	
	private String navigateToScreenCode;
	
	private String messageConfirm;
	
	private String enableConfirm;
	
	private String messageConfirmText;
	
	private String messageConfirmCode;
	
	private String columnId;
	
	private String showBlankItem;
	
	private Integer buttonType;
	
	private String customItemContent;
	
	private String customSectionContent;
	
	private Long screenItemIdCodeListId;
	
	private String screenItemTextCodeListId;
	
	private Long screenDesignIdCodeListId;
	
	private String screenDesignTextCodeListId;
	
	private String displayFromTo;
	
	private String displayFromToOutput;
	
	private String areaCustomType;
	
	private String tableType;
	
	private String screenTransition;
	
	private String screenTransitionText;
	
	private String messageLevel;
	
	private String messageLevelText;
	
	private String isDomainType;
	
	private Long datatypeIdDomainType;
	
	private String isUsedOnScreen;
	
	public String getIcon() {
		if (this.icon == null || this.icon.equals("null") || this.icon.equals("undefined")) {
			return "";
		}
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getShowLabel() {
		if (this.showLabel == null || this.showLabel.equals("null") || this.showLabel.equals("undefined")) {
			return "";
		}
		return showLabel;
	}

	public void setShowLabel(String showLabel) {
		this.showLabel = showLabel;
	}

	public String getSqldesignidtext() {
		if (this.sqldesignidtext == null || this.sqldesignidtext.equals("null") || this.sqldesignidtext.equals("undefined")) {
			return "";
		}
		return sqldesignidtext;
	}

	public void setSqldesignidtext(String sqldesignidtext) {
		this.sqldesignidtext = sqldesignidtext;
	}

	public String getOptionlabeltext() {
		if (this.optionlabeltext == null || this.optionlabeltext.equals("null") || this.optionlabeltext.equals("undefined")) {
			return "";
		}
		return optionlabeltext;
	}

	public void setOptionlabeltext(String optionlabeltext) {
		this.optionlabeltext = optionlabeltext;
	}

	public String getOptionvaluetext() {
		if (this.optionvaluetext == null || this.optionvaluetext.equals("null") || this.optionvaluetext.equals("undefined")) {
			return "";
		}
		return optionvaluetext;
	}

	public void setOptionvaluetext(String optionvaluetext) {
		this.optionvaluetext = optionvaluetext;
	}

	public String getDatasourcetype() {
		if (this.datasourcetype == null || this.datasourcetype.equals("null") || this.datasourcetype.equals("undefined")) {
			return "";
		}
		return datasourcetype;
	}

	public void setDatasourcetype(String datasourcetype) {
		this.datasourcetype = datasourcetype;
	}

	public String getSqldesignid() {
		if (this.sqldesignid == null || this.sqldesignid.equals("null") || this.sqldesignid.equals("undefined")) {
			return "";
		}
		return sqldesignid;
	}

	public void setSqldesignid(String sqldesignid) {
		this.sqldesignid = sqldesignid;
	}

	public String getOptionlabel() {
		if (this.optionlabel == null || this.optionlabel.equals("null") || this.optionlabel.equals("undefined")) {
			return "";
		}
		return optionlabel;
	}

	public void setOptionlabel(String optionlabel) {
		this.optionlabel = optionlabel;
	}

	public String getOptionvalue() {
		if (this.optionvalue == null || this.optionvalue.equals("null") || this.optionvalue.equals("undefined")) {
			return "";
		}
		return optionvalue;
	}

	public void setOptionvalue(String optionvalue) {
		this.optionvalue = optionvalue;
	}

	public String getIsEnable() {
		if (this.isEnable == null || this.isEnable.equals("null") || this.isEnable.equals("undefined")) {
			return "";
		}
		return (isEnable);
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = (isEnable);
	}

	public String getCodelistCode() {
		if (this.codelistCode == null || this.codelistCode.equals("null") || this.codelistCode.equals("undefined")) {
			return "";
		}
		return (codelistCode);
	}

	public void setCodelistCode(String codelistCode) {
		this.codelistCode = (codelistCode);
	}

	public String getCodelistText() {
		if (this.codelistText == null || this.codelistText.equals("null") || this.codelistText.equals("undefined")) {
			return "";
		}
		return (codelistText);
	}

	public void setCodelistText(String codelistText) {
		this.codelistText = (codelistText);
	}

	public String getLocalCodelist() {
		if (this.localCodelist == null || this.localCodelist.equals("null") || this.localCodelist.equals("undefined")) {
			return "";
		}
		return (localCodelist);
	}

	public void setLocalCodelist(String localCodelist) {
		this.localCodelist = (localCodelist);
	}

	public String getIsSupportOptionValue() {
		if (this.isSupportOptionValue == null || this.isSupportOptionValue.equals("null") || this.isSupportOptionValue.equals("undefined")) {
			return "";
		}
		return (isSupportOptionValue);
	}

	public void setIsSupportOptionValue(String isSupportOptionValue) {
		this.isSupportOptionValue = (isSupportOptionValue);
	}

	public String getDialogOnChangeEvent() {
		if (this.dialogOnChangeEvent == null || this.dialogOnChangeEvent.equals("null") || this.dialogOnChangeEvent.equals("undefined")) {
			return "";
		}
		return (dialogOnChangeEvent);
	}

	public void setDialogOnChangeEvent(String dialogOnChangeEvent) {
		this.dialogOnChangeEvent = (dialogOnChangeEvent);
	}

	public String getDialogOnSelectEvent() {
		if (this.dialogOnSelectEvent == null || this.dialogOnSelectEvent.equals("null") || this.dialogOnSelectEvent.equals("undefined")) {
			return "";
		}
		return (dialogOnSelectEvent);
	}

	public void setDialogOnSelectEvent(String dialogOnSelectEvent) {
		this.dialogOnSelectEvent = (dialogOnSelectEvent);
	}

	public String getNavigateToText() {
		if (this.navigateToText == null || this.navigateToText.equals("null") || this.navigateToText.equals("undefined")) {
			return "";
		}
		return (navigateToText);
	}

	public void setNavigateToText(String navigateToText) {
		this.navigateToText = (navigateToText);
	}

	public String getIsSubmit() {
		if (this.isSubmit == null || this.isSubmit.equals("null") || this.isSubmit.equals("undefined")) {
			return "";
		}
		return (isSubmit);
	}

	public void setIsSubmit(String isSubmit) {
		this.isSubmit = (isSubmit);
	}

	public String getActionName() {
		if (this.actionName == null || this.actionName.equals("null") || this.actionName.equals("undefined")) {
			return "";
		}
		return (actionName);
	}

	public void setActionName(String actionName) {
		this.actionName = (actionName);
	}

	public String getLabel() {
		if (this.label == null || this.label.equals("null") || this.label.equals("undefined")) {
			return "";
		}
		return (label);
	}

	public void setLabel(String label) {
		this.label = (label);
	}

	public String getDatatype() {
		if (this.datatype == null || this.datatype.equals("null") || this.datatype.equals("undefined")) {
			return "";
		}
		return (datatype);
	}

	public void setDatatype(String datatype) {
		this.datatype = (datatype);
	}

	public String getPhysicaldatatype() {
		if (this.physicaldatatype == null || this.physicaldatatype.equals("null") || this.physicaldatatype.equals("undefined")) {
			return "";
		}
		return (physicaldatatype);
	}

	public void setPhysicaldatatype(String physicaldatatype) {
		this.physicaldatatype = (physicaldatatype);
	}

	public String getColumnname() {
		if (this.columnname == null || this.columnname.equals("null") || this.columnname.equals("undefined")) {
			return "";
		}
		return (columnname);
	}

	public void setColumnname(String columnname) {
		this.columnname = (columnname);
	}

	public String getRowspan() {
		if (this.rowspan == null || this.rowspan.equals("null") || this.rowspan.equals("undefined")) {
			return "";
		}
		return (rowspan);
	}

	public void setRowspan(String rowspan) {
		this.rowspan = (rowspan);
	}

	public String getColspan() {
		if ( this.colspan == null || this.colspan.equals("null") || this.colspan.equals("undefined")) {
			return "";
		}
		return (colspan);
	}

	public void setColspan(String colspan) {
		this.colspan = (colspan);
	}

	public String getDatasource() {
		if ( this.datasource == null || this.datasource.equals("null") || this.datasource.equals("undefined")) {
			return "";
		}
		return (datasource);
	}

	public void setDatasource(String datasource) {
		this.datasource = (datasource);
	}

	public String getMinvalue() {
		if (this.minvalue == null || this.minvalue.equals("null") || this.minvalue.equals("undefined")) {
			return "";
		}
		return (minvalue);
	}

	public void setMinvalue(String minvalue) {
		this.minvalue = (minvalue);
	}

	public String getMaxvalue() {
		if (this.maxvalue == null || this.maxvalue.equals("null") || this.maxvalue.equals("undefined")) {
			return "";
		}
		return (maxvalue);
	}

	public void setMaxvalue(String maxvalue) {
		this.maxvalue = (maxvalue);
	}

	public String getFormatcode() {
		if (this.formatcode == null || this.formatcode.equals("null") || this.formatcode.equals("undefined")) {
			return "";
		}
		return (formatcode);
	}

	public void setFormatcode(String formatcode) {
		this.formatcode = (formatcode);
	}

	public String getTablename() {
		if (this.tablename == null || this.tablename.equals("null") || this.tablename.equals("undefined")) {
			return "";
		}
		return (tablename);
	}

	public void setTablename(String tablename) {
		this.tablename = (tablename);
	}

	public String getTablecode() {
		if (this.tablecode == null || this.tablecode.equals("null") || this.tablecode.equals("undefined")) {
			return "";
		}
		return (tablecode);
	}

	public void setTablecode(String tablecode) {
		this.tablecode = (tablecode);
	}

	public String getTablecolumnname() {
		if (this.tablecolumnname == null || this.tablecolumnname.equals("null") || this.tablecolumnname.equals("undefined")) {
			return "";
		}
		return (tablecolumnname);
	}

	public void setTablecolumnname(String tablecolumnname) {
		this.tablecolumnname = (tablecolumnname);
	}

	public String getTablecolumncode() {
		if (this.tablecolumncode == null || this.tablecolumncode.equals("null") || this.tablecolumncode.equals("undefined")) {
			return "";
		}
		return (tablecolumncode);
	}

	public void setTablecolumncode(String tablecolumncode) {
		this.tablecolumncode = (tablecolumncode);
	}

	public String getConnectionmsg() {
		if (this.connectionmsg == null || this.connectionmsg.equals("null") || this.connectionmsg.equals("undefined")) {
			return "";
		}
		return (connectionmsg);
	}

	public void setConnectionmsg(String connectionmsg) {
		this.connectionmsg = (connectionmsg);
	}

	public String getTransitiontype() {
		if (this.transitiontype == null || this.transitiontype.equals("null") || this.transitiontype.equals("undefined")) {
			return "";
		}
		return (transitiontype);
	}

	public void setTransitiontype(String transitiontype) {
		this.transitiontype = (transitiontype);
	}

	public String getActiontype() {
		if (this.actiontype == null || this.actiontype.equals("null") || this.actiontype.equals("undefined")) {
			return "";
		}
		return (actiontype);
	}

	public void setActiontype(String actiontype) {
		this.actiontype = (actiontype);
	}

	public String getParameters() {
		if (this.parameters == null || this.parameters.equals("null") || this.parameters.equals("undefined")) {
			return "";
		}
		return (parameters);
	}

	public void setParameters(String parameters) {
		this.parameters = (parameters);
	}

	public String getScreenactionid() {
		if (this.screenactionid == null || this.screenactionid.equals("null") || this.screenactionid.equals("undefined")) {
			return "";
		}
		return (screenactionid);
	}

	public void setScreenactionid(String screenactionid) {
		this.screenactionid = (screenactionid);
	}

	public String getLinklabel() {
		if (this.linklabel == null || this.linklabel.equals("null") || this.linklabel.equals("undefined")) {
			return "";
		}
		return (linklabel);
	}

	public void setLinklabel(String linklabel) {
		this.linklabel = (linklabel);
	}

	public String getToscreenid() {
		if (this.toscreenid == null || this.toscreenid.equals("null") || this.toscreenid.equals("undefined")) {
			return "";
		}
		return (toscreenid);
	}

	public void setToscreenid(String toscreenid) {
		this.toscreenid = (toscreenid);
	}

	public String getMaxlength() {
		if (this.maxlength == null || this.maxlength.equals("null") || this.maxlength.equals("undefined")) {
			return "";
		}
		return (maxlength);
	}

	public void setMaxlength(String maxlength) {
		this.maxlength = (maxlength);
	}

	public String getPhysicalmaxlength() {
		if (this.physicalmaxlength == null || this.physicalmaxlength.equals("null") || this.physicalmaxlength.equals("undefined")) {
			return "";
		}
		return (physicalmaxlength);
	}

	public void setPhysicalmaxlength(String physicalmaxlength) {
		this.physicalmaxlength = (physicalmaxlength);
	}

	public String getMsglabel() {
		if (this.msglabel == null || this.msglabel.equals("null") || this.msglabel.equals("undefined")) {
			return "";
		}
		return (msglabel);
	}

	public void setMsglabel(String msglabel) {
		this.msglabel = (msglabel);
	}

	public String getMsgvalue() {
		if (this.msgvalue == null || this.msgvalue.equals("null") || this.msgvalue.equals("undefined")) {
			return "";
		}
		return (msgvalue);
	}

	public void setMsgvalue(String msgvalue) {
		this.msgvalue = (msgvalue);
	}

	public String getRequire() {
		if (this.require == null || this.require.equals("null") || this.require.equals("undefined")) {
			return "";
		}
		return (require);
	}

	public void setRequire(String require) {
		this.require = (require);
	}

	public String getElementtype() {
		if (this.elementtype == null || this.elementtype.equals("null") || this.elementtype.equals("undefined")) {
			return "";
		}
		return (elementtype);
	}

	public void setElementtype(String elementtype) {
		this.elementtype = (elementtype);
	}

	public String getOnchangemethod() {
		if (this.onchangemethod == null || this.onchangemethod.equals("null") || this.onchangemethod.equals("undefined")) {
			return "";
		}
		return (onchangemethod);
	}

	public void setOnchangemethod(String onchangemethod) {
		this.onchangemethod = (onchangemethod);
	}

	public String getOnselectmethod() {
		if (this.onselectmethod == null || this.onselectmethod.equals("null") || this.onselectmethod.equals("undefined")) {
			return "";
		}
		return (onselectmethod);
	}

	public void setOnselectmethod(String onselectmethod) {
		this.onselectmethod = (onselectmethod);
	}

	public String getAutocompleteid() {
		if (this.autocompleteid == null || this.autocompleteid.equals("null") || this.autocompleteid.equals("undefined")) {
			return "";
		}
		return (autocompleteid);
	}

	public void setAutocompleteid(String autocompleteid) {
		this.autocompleteid = (autocompleteid);
	}

	public String getCodelisttype() {
		if (this.codelisttype == null || this.codelisttype.equals("null") || this.codelisttype.equals("undefined")) {
			return "";
		}
		return (codelisttype);
	}

	public void setCodelisttype(String codelisttype) {
		this.codelisttype = (codelisttype);
	}

	public String getCodelistcode() {
		if (this.codelistcode == null || this.codelistcode.equals("null") || this.codelistcode.equals("undefined")) {
			return "";
		}
		return (codelistcode);
	}

	public void setCodelistcode(String codelistcode) {
		this.codelistcode = (codelistcode);
	}

	public String getDatatypecode() {
		if (this.datatypecode == null || this.datatypecode.equals("null") || this.datatypecode.equals("undefined")) {
			return "";
		}
		return (datatypecode);
	}

	public void setDatatypecode(String datatypecode) {
		this.datatypecode = (datatypecode);
	}

	public String getWidth() {
		if (this.width == null || this.width.equals("null") || this.width.equals("undefined")) {
			return "100";
		}
		return (width);
	}

	public void setWidth(String width) {
		this.width = (width);
	}

	public String getWidthunit() {
		if (this.widthunit == null || this.widthunit.equals("null") || this.widthunit.equals("undefined")) {
			return "%";
		}
		return (widthunit);
	}

	public void setWidthunit(String widthunit) {
		this.widthunit = (widthunit);
	}

	public String getMandatory() {
		if (this.mandatory == null || this.mandatory.equals("null") || this.mandatory.equals("undefined")) {
			return "";
		}
		return (mandatory);
	}

	public void setMandatory(String mandatory) {
		this.mandatory = (mandatory);
	}

	public String getValidateRule() {
		if (this.validateRule == null || this.validateRule.equals("null") || this.validateRule.equals("undefined")) {
			return "";
		}
		return (validateRule);
	}

	public void setValidateRule(String validateRule) {
		this.validateRule = (validateRule);
	}

	public String getEnablegroup() {
		if (this.enablegroup == null || this.enablegroup.equals("null") || this.enablegroup.equals("undefined")) {
			return "";
		}
		return (enablegroup);
	}

	public void setEnablegroup(String enablegroup) {
		this.enablegroup = (enablegroup);
	}

	public String getLabelText() {
		if (this.labelText == null || this.labelText.equals("null") || this.labelText.equals("undefined")) {
			return "";
		}
		return (labelText);
	}

	public void setLabelText(String labelText) {
		this.labelText = (labelText);
	}

	public String getIsBundle() {
		if (StringUtils.isEmpty(this.isBundle) || this.isBundle.equals("null") || this.isBundle.equals("undefined")) {
			return ScreenDesignConst.ScreenItemConst.VALUE_FALSE;
		}
		return (isBundle);
	}

	public void setIsBundle(String isBundle) {
		this.isBundle = (isBundle);
	}

	public String getIsBlank() {
		if (StringUtils.isEmpty(this.isBlank) || this.isBlank.equals("null") || this.isBlank.equals("undefined")) {
			return ScreenDesignConst.ScreenItemConst.VALUE_FALSE;
		}
		return (isBlank);
	}

	public void setIsBlank(String isBlank) {
		this.isBlank = (isBlank);
	}

	public String getMsgcode() {
		if (this.msgcode == null || this.msgcode.equals("null") || this.msgcode.equals("undefined")) {
			return "";
		}
		return (msgcode);
	}

	public void setMsgcode(String msgcode) {
		this.msgcode = (msgcode);
	}

	public String getNavigateTo() {
		if (this.navigateTo == null || this.navigateTo.equals("null") || this.navigateTo.equals("undefined")) {
			return "";
		}
		return (navigateTo);
	}

	public void setNavigateTo(String navigateTo) {
		this.navigateTo = (navigateTo);
	}

	public String getGroupitemtype() {
		if (this.groupitemtype == null || this.groupitemtype.equals("null") || this.groupitemtype.equals("undefined")) {
			return "";
		}
		return (groupitemtype);
	}

	public void setGroupitemtype(String groupitemtype) {
		this.groupitemtype = (groupitemtype);
	}

	public String getDialogAutocompleteCode() {
		if (this.dialogAutocompleteCode == null || this.dialogAutocompleteCode.equals("null") || this.dialogAutocompleteCode.equals("undefined")) {
			return "";
		}
		return (dialogAutocompleteCode);
	}

	public void setDialogAutocompleteCode(String dialogAutocompleteCode) {
		this.dialogAutocompleteCode = (dialogAutocompleteCode);
	}

	public String getDialogAutocompleteText() {
		if (this.dialogAutocompleteText == null || this.dialogAutocompleteText.equals("null") || this.dialogAutocompleteText.equals("undefined")) {
			return "";
		}
		return (dialogAutocompleteText);
	}

	public void setDialogAutocompleteText(String dialogAutocompleteText) {
		this.dialogAutocompleteText = (dialogAutocompleteText);
	}

	public String getBaseType() {
		if (this.baseType == null || this.baseType.equals("null") || this.baseType.equals("undefined")) {
			return "";
		}
		return (baseType);
	}

	public void setBaseType(String baseType) {
		this.baseType = (baseType);
	}

	public String getGroupDisplayType() {
		if (this.groupDisplayType == null || this.groupDisplayType.equals("null") || this.groupDisplayType.equals("undefined")) {
			return "";
		}
		return (groupDisplayType);
	}

	public void setGroupDisplayType(String groupDisplayType) {
		this.groupDisplayType = (groupDisplayType);
	}

	public String getOutputBeanId() {
		if (this.outputBeanId == null || this.outputBeanId.equals("null") || this.outputBeanId.equals("undefined")) {
			return "";
		}
		return outputBeanId;
	}

	public void setOutputBeanId(String outputBeanId) {
		this.outputBeanId = outputBeanId;
	}

	public String getColumndisplay() {
		if (this.columndisplay == null || this.columndisplay.equals("null") || this.columndisplay.equals("undefined")) {
			return "";
		}
		return columndisplay;
	}

	public void setColumndisplay(String columndisplay) {
		this.columndisplay = columndisplay;
	}

	public String getOutputBeanLevel() {
		if (this.outputBeanLevel == null || this.outputBeanLevel.equals("null") || this.outputBeanLevel.equals("undefined")) {
			return "";
		}
		
		return outputBeanLevel;
	}

	public void setOutputBeanLevel(String outputBeanLevel) {
		this.outputBeanLevel = outputBeanLevel;
	}

	public Event[] getEvents() {
		return events;
	}

	public void setEvents(Event[] events) {
		this.events = events;
	}

	public String getTabindex() {
		if (this.tabindex == null || this.tabindex.equals("null") || this.tabindex.equals("undefined")) {
			return "";
		}
		return tabindex;
	}

	public void setTabindex(String tabindex) {
		this.tabindex = tabindex;
	}

	public String getDefaultvalue() {
		if (this.defaultvalue == null || this.defaultvalue.equals("null") || this.defaultvalue.equals("undefined")) {
			return "";
		}
		return defaultvalue;
	}

	public void setDefaultvalue(String defaultvalue) {
		this.defaultvalue = defaultvalue;
	}

	public String getDataSourceId() {
		if (this.dataSourceId == null || this.dataSourceId.equals("null") || this.dataSourceId.equals("undefined")) {
			return "";
		}
		return dataSourceId;
	}

	public void setDataSourceId(String dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

	public String getToScreenCode() {
		if (this.toScreenCode == null || this.toScreenCode.equals("null") || this.toScreenCode.equals("undefined")) {
			return "";
		}
		return toScreenCode;
	}

	public void setToScreenCode(String toScreenCode) {
		this.toScreenCode = toScreenCode;
	}

	public String getScreenItemId() {
		if (this.screenItemId == null || this.screenItemId.equals("null") || this.screenItemId.equals("undefined")) {
			return "";
		}
		return screenItemId;
	}

	public void setScreenItemId(String screenItemId) {
		this.screenItemId = screenItemId;
	}

	public String getStyle() {
		if (this.style == null || this.style.equals("null") || this.style.equals("undefined")) {
			return "";
		}
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getHoverStyle() {
		if (this.hoverStyle == null || this.hoverStyle.equals("null") || this.hoverStyle.equals("undefined")) {
			return "";
		}
		return hoverStyle;
	}

	public void setHoverStyle(String hoverStyle) {
		this.hoverStyle = hoverStyle;
	}

	public String getEnablePassword() {
		if (this.enablePassword == null || this.enablePassword.equals("null") || this.enablePassword.equals("undefined")) {
			return "";
		}
		return enablePassword;
	}

	public void setEnablePassword(String enablePassword) {
		this.enablePassword = enablePassword;
	}

	public String getAllowAnyInput() {
		if (this.allowAnyInput == null || this.allowAnyInput.equals("null") || this.allowAnyInput.equals("undefined")) {
			return "";
		}
		return allowAnyInput;
	}

	public void setAllowAnyInput(String allowAnyInput) {
		this.allowAnyInput = allowAnyInput;
	}

	public String getItemType() {
		if (this.itemType == null || this.itemType.equals("null") || this.itemType.equals("undefined")) {
			return "";
		}
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getButtonStyle() {
		if (this.buttonStyle == null || this.buttonStyle.equals("null") || this.buttonStyle.equals("undefined")) {
			return "";
		}
		
		return buttonStyle;
	}

	public void setButtonStyle(String buttonStyle) {
		this.buttonStyle = buttonStyle;
	}

	public String getInputStyle() {
		if (this.inputStyle == null || this.inputStyle.equals("null") || this.inputStyle.equals("undefined")) {
			return "";
		}
		
		return inputStyle;
	}

	public void setInputStyle(String inputStyle) {
		this.inputStyle = inputStyle;
	}

	public String getHeaderStyle() {
		if (this.headerStyle == null || this.headerStyle.equals("null") || this.headerStyle.equals("undefined")) {
			return "";
		}
		
		return headerStyle;
	}

	public void setHeaderStyle(String headerStyle) {
		this.headerStyle = headerStyle;
	}

	public String getNavigateToBlogic() {
		if (this.navigateToBlogic == null || this.navigateToBlogic.equals("null") || this.navigateToBlogic.equals("undefined")) {
			return "";
		}
		
		return navigateToBlogic;
	}

	public void setNavigateToBlogic(String navigateToBlogic) {
		this.navigateToBlogic = navigateToBlogic;
	}

	public String getNavigateToBlogicText() {
		if (this.navigateToBlogicText == null || this.navigateToBlogicText.equals("null") || this.navigateToBlogicText.equals("undefined")) {
			return "";
		}
		
		return navigateToBlogicText;
	}

	public void setNavigateToBlogicText(String navigateToBlogicText) {
		this.navigateToBlogicText = navigateToBlogicText;
	}

	public String getMessageConfirm() {
		if (this.messageConfirm == null || this.messageConfirm.equals("null") || this.messageConfirm.equals("undefined")) {
			return "";
		}
		
		return messageConfirm;
	}

	public void setMessageConfirm(String messageConfirm) {
		this.messageConfirm = messageConfirm;
	}

	public String getEnableConfirm() {
		if (this.enableConfirm == null || this.enableConfirm.equals("null") || this.enableConfirm.equals("undefined")) {
			return "";
		}
		
		return enableConfirm;
	}

	public void setEnableConfirm(String enableConfirm) {
		this.enableConfirm = enableConfirm;
	}

	public String getMessageConfirmText() {
		if (this.messageConfirmText == null || this.messageConfirmText.equals("null") || this.messageConfirmText.equals("undefined")) {
			return "";
		}
		
		return messageConfirmText;
	}

	public void setMessageConfirmText(String messageConfirmText) {
		this.messageConfirmText = messageConfirmText;
	}

	public String getMessageConfirmCode() {
		if (this.messageConfirmCode == null || this.messageConfirmCode.equals("null") || this.messageConfirmCode.equals("undefined")) {
			return "";
		}
		
		return messageConfirmCode;
	}

	public void setMessageConfirmCode(String messageConfirmCode) {
		this.messageConfirmCode = messageConfirmCode;
	}

	public String getColumnId() {
		if (this.columnId == null || this.columnId.equals("null") || this.columnId.equals("undefined")) {
			return "";
		}
		
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getShowBlankItem() {
		if (this.showBlankItem == null || this.showBlankItem.equals("null") || this.showBlankItem.equals("undefined")) {
			return "";
		}
		
		return showBlankItem;
	}

	public void setShowBlankItem(String showBlankItem) {
		this.showBlankItem = showBlankItem;
	}

	public Integer getButtonType() {
		return buttonType;
	}

	public void setButtonType(Integer buttonType) {
		this.buttonType = buttonType;
	}

	public String getCustomSectionContent() {
		
		if (this.customSectionContent == null || this.customSectionContent.equals("null") || this.customSectionContent.equals("undefined")) {
			return "";
		}
		
		return customSectionContent;
	}

	public void setCustomSectionContent(String customSectionContent) {
		this.customSectionContent = customSectionContent;
	}

	public String getCustomItemContent() {
		if (this.customItemContent == null || this.customItemContent.equals("null") || this.customItemContent.equals("undefined")) {
			return "";
		}
		
		return customItemContent;
	}

	public void setCustomItemContent(String customItemContent) {
		this.customItemContent = customItemContent;
	}

	public String getNavigateToBlogicCode() {
		return navigateToBlogicCode;
	}

	public void setNavigateToBlogicCode(String navigateToBlogicCode) {
		this.navigateToBlogicCode = navigateToBlogicCode;
	}

	public String getNavigateToScreenCode() {
		return navigateToScreenCode;
	}

	public void setNavigateToScreenCode(String navigateToScreenCode) {
		this.navigateToScreenCode = navigateToScreenCode;
	}

	public Long getScreenItemIdCodeListId() {
		return screenItemIdCodeListId;
	}

	public void setScreenItemIdCodeListId(Long screenItemIdCodeListId) {
		this.screenItemIdCodeListId = screenItemIdCodeListId;
	}

	public String getScreenItemTextCodeListId() {
		return screenItemTextCodeListId;
	}

	public void setScreenItemTextCodeListId(String screenItemTextCodeListId) {
		this.screenItemTextCodeListId = screenItemTextCodeListId;
	}

	public Long getScreenDesignIdCodeListId() {
		return screenDesignIdCodeListId;
	}

	public void setScreenDesignIdCodeListId(Long screenDesignIdCodeListId) {
		this.screenDesignIdCodeListId = screenDesignIdCodeListId;
	}

	public String getScreenDesignTextCodeListId() {
		return screenDesignTextCodeListId;
	}

	public void setScreenDesignTextCodeListId(String screenDesignTextCodeListId) {
		this.screenDesignTextCodeListId = screenDesignTextCodeListId;
	}
	public String getDisplayFromTo() {
		return displayFromTo;
	}

	public void setDisplayFromTo(String displayFromTo) {
		this.displayFromTo = displayFromTo;
	}

	public String getDisplayFromToOutput() {
		if (this.displayFromToOutput == null || this.displayFromToOutput.equals("null") || this.displayFromToOutput.equals("undefined")) {
			return "";
		}
		return displayFromToOutput;
	}

	public void setDisplayFromToOutput(String displayFromToOutput) {
		this.displayFromToOutput = displayFromToOutput;
	}

	public String getAreaCustomType() {
		if (this.areaCustomType == null || this.areaCustomType.equals("null") || this.areaCustomType.equals("undefined")) {
			return "";
		}
		return areaCustomType;
	}

	public void setAreaCustomType(String areaCustomType) {
		this.areaCustomType = areaCustomType;
	}

	public String getTableType() {
		if (this.tableType == null || this.tableType.equals("null") || this.tableType.equals("undefined")) {
			return "";
		}
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public String getScreenTransition() {
		if (this.screenTransition == null || this.screenTransition.equals("null") || this.screenTransition.equals("undefined")) {
			return "";
		}
		return screenTransition;
	}

	public void setScreenTransition(String screenTransition) {
		this.screenTransition = screenTransition;
	}

	public String getScreenTransitionText() {
		if (this.screenTransitionText == null || this.screenTransitionText.equals("null") || this.screenTransitionText.equals("undefined")) {
			return "";
		}
		return screenTransitionText;
	}

	public void setScreenTransitionText(String screenTransitionText) {
		this.screenTransitionText = screenTransitionText;
	}

	public String getMessageLevel() {
		return messageLevel;
	}

	public void setMessageLevel(String messageLevel) {
		this.messageLevel = messageLevel;
	}

	public String getMessageLevelText() {
		return messageLevelText;
	}

	public void setMessageLevelText(String messageLevelText) {
		this.messageLevelText = messageLevelText;
	}

	public String getIsDomainType() {
		return isDomainType;
	}

	public void setIsDomainType(String isDomainType) {
		this.isDomainType = isDomainType;
	}

	public Long getDatatypeIdDomainType() {
		return datatypeIdDomainType;
	}

	public void setDatatypeIdDomainType(Long datatypeIdDomainType) {
		this.datatypeIdDomainType = datatypeIdDomainType;
	}

	public String getIsUsedOnScreen() {
		return isUsedOnScreen;
	}

	public void setIsUsedOnScreen(String isUsedOnScreen) {
		this.isUsedOnScreen = isUsedOnScreen;
	}

}
