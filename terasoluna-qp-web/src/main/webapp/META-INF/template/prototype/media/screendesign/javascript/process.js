function saveDialogAutocompleteSetting(thiz, modalName) {
	var modal = $("#dialog-component-autocomplete-setting");
	if (modalName != null && modalName == 'dialog-component-autocomplete-setting-section') {
		modal = $("#dialog-component-autocomplete-setting-section");
	}
	// validate autocomplete code
	if(!validateRequired(modal,"dialogAutocompleteCodeAutocomplete",dbMsgSource['sc.screendesign.0087'])) {
		return;
	}

	if(!validateRequired(modal,"columnname",dbMsgSource['sc.screendesign.0093'])) {
		return;
	}
	
	if(!validateAlphanumeric(modal,"columnname",dbMsgSource['sc.screendesign.0093'])) {
		return;
	}
	
	if(!validateRequired(modal,"sqldesignid",dbMsgSource['sc.screendesign.0041'])) {
		$(modal).find("input[name=sqldesignidAutocomplete]").focus();
		return;
	}
	
	if(!validateRequired(modal,"optionlabel",dbMsgSource['sc.screendesign.0201'])) {
		$(modal).find("input[name=optionlabelAutocomplete]").focus();
		return;
	}
	
	if(!validateRequired(modal,"optionvalue",dbMsgSource['sc.screendesign.0202'])) {
		$(modal).find("input[name=optionvalueAutocomplete]").focus();
		return;
	}
	
	$.qp.undoFormatNumericForm(modal);
	obj = $(modal).data("target");
	data = convertToJson($(modal).data("data"));
	var colSpanOld = data.colspan;
	
	var columnname  = "";
	if($(modal).find("input[name='columnname']").val() != undefined){
		columnname = $(modal).find("input[name='columnname']").val();
	}	
	
	var dialogAutocompleteCode  = "";
	if($(modal).find("input[name='dialogAutocompleteCode']").val() != undefined){
		dialogAutocompleteCode = $(modal).find("input[name='dialogAutocompleteCode']").val();
	}
	var dialogAutocompleteText  = "";
	if($(modal).find("input[name='dialogAutocompleteCodeAutocomplete']").val() != undefined){
		dialogAutocompleteText = $(modal).find("input[name='dialogAutocompleteCodeAutocomplete']").val();
	}
	
	var elementWidth = "";
	if($(modal).find("[name='dialog-component-setting-element-width']").val() != undefined){
		elementWidth = $(modal).find("[name='dialog-component-setting-element-width']").val();
	}
	
	var elementWidthUnit = "";
	if($(modal).find("[name='dialog-component-setting-element-width-unit']").val() != undefined){
		elementWidthUnit = $(modal).find("[name='dialog-component-setting-element-width-unit']").val();
	}
	
	var baseType = "";
	if($(modal).find("select[name='baseType']").val() != undefined){
		baseType = $(modal).find("select[name='baseType']").val();
	}
	
	var dialogOnChangeEvent = "";
	if($(modal).find("input[name='dialogOnChangeEvent']").val() != undefined){
		dialogOnChangeEvent = $(modal).find("input[name='dialogOnChangeEvent']").val();
	}
	var dialogOnSelectEvent = "";
	if($(modal).find("input[name='dialogOnSelectEvent']").val() != undefined){
		dialogOnSelectEvent = $(modal).find("input[name='dialogOnSelectEvent']").val();
	}
	
	var rowspan = "";
	if($(modal).find("input[name='rowspan']").val() != undefined){
		rowspan = $(modal).find("input[name='rowspan']").val();
	}
	
	var colspan = "";
	if($(modal).find("input[name='colspan']").val() != undefined){
		colspan = $(modal).find("input[name='colspan']").val();
	}
	
	if (parseInt(colspan) < 0) {
		alert(dbMsgSource['sc.screendesign.0283']);
		return;
	}
	
	if (parseInt(rowspan) < 0) {
		alert(dbMsgSource['sc.screendesign.0284']);
		return;
	}
	
	if (data.datasourcetype != undefined) {
		data.datasourcetype = 1;
	} else {
		data['datasourcetype'] = 1;
	}
	
	var sqldesignid = '';
	if($(modal).find("input[name='sqldesignid']").val() != undefined){
		sqldesignid = $(modal).find("input[name='sqldesignid']").val();
	}
	
	if (data.sqldesignid != undefined) {
		data.sqldesignid = $(modal).find("input[name='sqldesignid']").val();
	} else {
		data['sqldesignid'] = $(modal).find("input[name='sqldesignid']").val();
	}
	
	if (data.sqldesignidtext != undefined) {
		data.sqldesignidtext = $(modal).find("input[name='sqldesignidAutocomplete']").val();
	} else {
		data['sqldesignidtext'] = $(modal).find("input[name='sqldesignidAutocomplete']").val();
	}
	
	var optionlabel = '';
	if($(modal).find("input[name='optionlabel']").val() != undefined){
		optionlabel = $(modal).find("input[name='optionlabel']").val();
	}
	
	if (data.optionlabel != undefined) {
		data.optionlabel = $(modal).find("input[name='optionlabel']").val();
	} else {
		data['optionlabel'] = $(modal).find("input[name='optionlabel']").val();
	}
	
	if (data.optionlabeltext != undefined) {
		data.optionlabeltext = $(modal).find("input[name='optionlabelAutocomplete']").val();
	} else {
		data['optionlabeltext'] = $(modal).find("input[name='optionlabelAutocomplete']").val();
	}
	
	var optionvalue = '';
	if($(modal).find("input[name='optionvalue']").val() != undefined){
		optionvalue = $(modal).find("input[name='optionvalue']").val();
	}
	
	if (data.optionvalue != undefined) {
		data.optionvalue = $(modal).find("input[name='optionvalue']").val();
	} else {
		data['optionvalue'] = $(modal).find("input[name='optionvalue']").val();
	}
	
	if (data.optionvaluetext != undefined) {
		data.optionvaluetext = $(modal).find("input[name='optionvalueAutocomplete']").val();
	} else {
		data['optionvaluetext'] = $(modal).find("input[name='optionvalueAutocomplete']").val();
	}
	
	result = saveProperties(modal, data);
	if (!result) {
		return;
	}
	data = result;
	
	//init default value
	if(elementWidth == ''){
		elementWidth = 100;
	}
	
	// add to data
	if (data.columnname != undefined) {
		data.columnname = columnname;
	} else {		
		data["columnname"] = columnname;
	}
	
	if (data.dialogAutocompleteCode != undefined) {
		data.dialogAutocompleteCode = dialogAutocompleteCode;
	} else {		
		data["dialogAutocompleteCode"] = dialogAutocompleteCode;
	}
	if (data.dialogAutocompleteText != undefined) {
		data.dialogAutocompleteText = dialogAutocompleteText;
	} else {		
		data["dialogAutocompleteText"] = dialogAutocompleteText;
	}
	
	if (data.width != undefined) {
		data.width = elementWidth;
	} else {		
		data["width"] = elementWidth;
	}
	
	if (data.widthunit != undefined) {
		data.widthunit = elementWidthUnit;
	} else {		
		data["widthunit"] = elementWidthUnit;
	}
	
	if (data.baseType != undefined) {
		data.baseType = baseType;
	} else {		
		data["baseType"] = baseType;
	}
	
	if (data.dialogOnChangeEvent != undefined) {
		data.dialogOnChangeEvent = dialogOnChangeEvent;
	} else {		
		data["dialogOnChangeEvent"] = dialogOnChangeEvent;
	}
	
	if (data.dialogOnSelectEvent != undefined) {
		data.dialogOnSelectEvent = dialogOnSelectEvent;
	} else {		
		data["dialogOnSelectEvent"] = dialogOnSelectEvent;
	}
	
	var enableGroup = "";
	if( $(modal).find("input[name='enableGroup']")!= undefined){
		enableGroup =  $(modal).find("input[name='enableGroup']").prop("checked");
	}
	if (data.enablegroup != undefined) {
		data.enablegroup = enableGroup;
	} else {		
		data["enablegroup"] = enableGroup;
	}
	
	/*var result = saveOutputSetting($(modal), data, obj);
	
	if (!result) {
		return false;
	}
	data = result;*/
	
	var groupDisplayType = $(modal).find("select[name='dialog-component-setting-group-display-type']").val();
	if(groupDisplayType != undefined){
		$(obj).closest("td").find("input[name='groupDisplayType']").val(groupDisplayType);	
		
		if (enableGroup) {
			
			$(obj).closest("td").droppable({
				accept: "#srcgenElements div[id!='divLabel'], #srcgenAction div, #newDragElementTd div",
				activeClass: "state-droppable",
				drop: function(event, ui) {					
					insertComponent(event, ui, $(this));			
				}
			});
			$(obj).closest("td").droppable( "option", "disabled", false );
			$(obj).closest("td").find("input[name='enableGroup']").val(true);
			
			//if inline
			if (groupDisplayType == 1) {
				$(obj).closest("td").children().css("float", "left");
				$(obj).closest("td").children().css("clear", "");
			} else if (groupDisplayType == 2) {			
				$(obj).closest("td").children().css("clear", "both");
			}
		} else {
			if ($(obj).closest("td").find("input[name=formElement]").length == 1) {
				if ($(obj).closest("td").is(".ui-droppable")) {
					$(obj).closest("td").droppable( "option", "disabled", true );
				}
				$(obj).closest("td").find("input[name='enableGroup']").val(false);
			}
		}
		$(obj).closest("td").children().css("float", "left");
		
		if (data.groupDisplayType != undefined) {
			data.groupDisplayType = groupDisplayType;
		} else {		
			data["groupDisplayType"] = groupDisplayType;
		}
	}

	if (modalName == null || modalName == undefined || modalName != 'dialog-component-autocomplete-setting-section') {
		if (data.width != '' && parseFloat(data.width) > 0) {
			
			if ($(obj).closest("div").closest("span") != undefined) {
				$(obj).closest("div").closest("span").css("width", data.width + data.widthunit);
			} else {
				$(obj).closest("div").css("width", data.width + data.widthunit);
			}
		}	
		if (mergeTable(obj, data, rowspan, colspan)) {
			if (data.rowspan != undefined) {
				data.rowspan = rowspan;
			} else {		
				data["rowspan"] = rowspan;
			}
			
			if (data.colspan != undefined) {
				data.colspan = colspan;
			} else {		
				data["colspan"] = colspan;
			}
			var value =  convertToString(data);		
			if (data.datatype == 21) {
				$(obj).closest("span").find("label").html(data.columnname);
			}
			$(obj).closest("span").find("input[name='formElement']").val(value);
			$(modal).modal("hide");
		}
	} else {
		if (data.width != '' && parseFloat(data.width) > 0) {
			$(obj).closest("div").closest("span").css("width", data.width + data.widthunit);
			if ($(obj).closest("div").closest("span") != undefined) {
				$(obj).closest("div").closest("span").closest("span").css("width", data.width + data.widthunit);
			}
		}	
		var value =  convertToString(data);		
		if (data.datatype == 21) {
			$(obj).closest("span").find("label").html(data.columnname);
		}
		$(obj).closest("span").find("input[name='formElement']").val(value);
		$(modal).modal("hide");
	}
}

function deleteDialogAutocompleteSetting(thiz, modalName) {
	var modal = $("#dialog-component-autocomplete-setting");
	if (modalName != null && modalName == 'dialog-component-autocomplete-setting-section') {
		modal = $("#dialog-component-autocomplete-setting-section");
	}
	var obj = $(modal).data("target");
	var data = convertToJson($(modal).data("data"));
	
	//delete element
	deleteElement(obj, data);
	
	$(modal).modal("hide");
}

function saveDialogLinkSetting(obj) {
	saveDialogLinkAreaSetting(obj, "dialog-link-setting");
}

function deleteDialogLinkSetting(obj) {
	var modal = $("#dialog-link-setting");
	var obj = $(modal).data("target");
	var data = convertToJson($(modal).data("data"));
	
	//delete element
	$(obj).parent().remove();
	$(modal).modal("hide");
}

function saveDialogButtonSetting(button) {
	$("#dialog-button-setting").modal("hide");
}

function deleteDialogButtonSetting(button) {
	$("#dialog-button-setting").modal("hide");
}

function changeSubmitDialogAction(obj){
	var table = $(obj).closest("table");
	if($(obj).prop('checked')){
		$(table).find("#dialogActionParamSetting").hide();
	} else {
		$(table).find("#dialogActionParamSetting").show();
		loadParameter($(table).find("input[name=navigateTo]"));
	}
	
	
}

function saveDialogComponentListSetting(modalName) {
	var modal = $("#dialog-component-list-setting");
	if (modalName != null && modalName == 'dialog-component-list-setting-section') {
		modal = $("#dialog-component-list-setting-section");
	}
	if(!validateRequired(modal,"labelName",dbMsgSource['sc.screendesign.0199'])) {
		return;
	}
	
	if(!validateRequired(modal,"labelNameAutocomplete",dbMsgSource['sc.screendesign.0199'])) {
		return;
	}
	
	if(!validateRequired(modal,"columnname",dbMsgSource['sc.screendesign.0093'])) {
		return;
	}
	
	if(!validateAlphanumeric(modal,"columnname",dbMsgSource['sc.screendesign.0093'])) {
		return;
	}
	
	if ($(modal).find("input[name=localCodelist][value=3]").prop("checked")) {
		if(!validateDuplication(modal,"parameterOptionValue",dbMsgSource['sc.screendesign.0097'])) {
			return;
		}
	}
	
	var isValid = true;
	$(modal).find('.onchange-event').each(function() {
		if (!checkEffectAreaType($(this))) {
			isValid = false;
			return false;
		}
		
		if (!validateEffectAreaEventSetting($(this))) {
			isValid = false;
			return false;
		}
		
		if (!validateBLogicNameEventSetting($(this))) {
			isValid = false;
			return false;
		}
	});
	
	if (!isValid) {
		return;
	}
	
	if ($(modal).find('input[name=dataSourceType][value=1]:checked').length > 0) {
		if ($(modal).find('input[name=sqldesignid]').val().length == 0) {
			alert(dbMsgSource['sc.screendesign.0319']);
			$(modal).find(".nav-tabs").find('li').removeClass("active");
			$(modal).find(".nav-tabs").find('li:eq(1)').addClass("active");
			$(modal).find(".tab-content").find(".tab-pane").removeClass("active");
			$(modal).find(".tab-content").find(".tab-pane:eq(1)").addClass("active");
			$('input[name=sqldesignidAutocomplete]').focus();
			return;
		}
		
		
		if ($(modal).find('input[name=optionlabel]').val().length == 0) {
			alert(dbMsgSource['sc.screendesign.0320']);
			$(modal).find(".nav-tabs").find('li').removeClass("active");
			$(modal).find(".nav-tabs").find('li:eq(1)').addClass("active");
			$(modal).find(".tab-content").find(".tab-pane").removeClass("active");
			$(modal).find(".tab-content").find(".tab-pane:eq(1)").addClass("active");
			$('input[name=optionlabelAutocomplete]').focus();
			return;
		}
		
		if ($(modal).find('input[name=optionvalue]').val().length == 0) {
			alert(dbMsgSource['sc.screendesign.0321']);
			$(modal).find(".nav-tabs").find('li').removeClass("active");
			$(modal).find(".nav-tabs").find('li:eq(1)').addClass("active");
			$(modal).find(".tab-content").find(".tab-pane").removeClass("active");
			$(modal).find(".tab-content").find(".tab-pane:eq(1)").addClass("active");
			$('input[name=optionvalueAutocomplete]').focus();
			return;
		}
	}
	
	$.qp.undoFormatNumericForm(modal);
	obj = $(modal).data("target");
	data = convertToJson($(modal).data("data"));
	var colSpanOld = data.colspan;
	var rowSpanOld = data.rowspan;
	var msglabel="";
	var msgvalue="";
	var columnname  = "";
	var divtablename="";
	
	if($(modal).find("input[name='columnname']").val() != undefined){
		columnname = $(modal).find("input[name='columnname']").val();
	}
	
	result = saveProperties(modal, data);
	if (!result) {
		return;
	}
	data = result;

	var datatype  = "";
	if($(modal).find("select[name='datatype']").val() != undefined){
		datatype = $(modal).find("select[name='datatype']").val();
	}
	
	var basetype  = "";
	if($(modal).find("select[name='baseType']").val() != undefined){
		basetype = $(modal).find("select[name='baseType']").val();
	}

	var rowspan = "";
	if($(modal).find("input[name='rowspan']").val() != undefined){
		rowspan = $(modal).find("input[name='rowspan']").val();
	}
	
	var colspan = "";
	if($(modal).find("input[name='colspan']").val() != undefined){
		colspan = $(modal).find("input[name='colspan']").val();
	}
	
	var elementWidth = "";
	if($(modal).find("[name='dialog-component-list-setting-width']").val() != undefined){
		elementWidth = $(modal).find("[name='dialog-component-list-setting-width']").val();
	}
	
	var elementWidthUnit = "";
	if($(modal).find("[name='dialog-component-list-setting-width-unit']").val() != undefined){
		elementWidthUnit = $(modal).find("[name='dialog-component-list-setting-width-unit']").val();
	}
	
	
	if (parseInt(colspan) < 0) {
		alert(dbMsgSource['sc.screendesign.0283']);
		return;
	}
	
	if (parseInt(rowspan) < 0) {
		alert(dbMsgSource['sc.screendesign.0284']);
		return;
	}
	
	if (data.columnname != undefined) {
		data.columnname = columnname;
	} else {
		data["columnname"] = columnname;
	}
	
	if (data.width != undefined) {
		data.width = elementWidth;
	} else {		
		data["width"] = elementWidth;
	}
	
	if (data.widthunit != undefined) {
		data.widthunit = elementWidthUnit;
	} else {		
		data["widthunit"] = elementWidthUnit;
	}
	
	if (data.baseType != undefined) {
		data.baseType = basetype;
	} else {
		data["baseType"] = basetype;
	}
	
	var datasourcetype = '';
	
	if($(modal).find("input[name='dataSourceType']:checked").val() != undefined){
		datasourcetype = $(modal).find("input[name='dataSourceType']:checked").val();
	}
	
	if (datasourcetype.length == 0) {
		alert(dbMsgSource['sc.screendesign.0291']);
		return;
	}
	
	if (data.datasourcetype != undefined) {
		data.datasourcetype = $(modal).find("input[name='dataSourceType']:checked").val();
	} else {
		data['datasourcetype'] = $(modal).find("input[name='dataSourceType']:checked").val();
	}
	
	if (data.datasourcetype == '1') {
		var sqldesignid = '';
		if($(modal).find("input[name='sqldesignid']").val() != undefined){
			sqldesignid = $(modal).find("input[name='sqldesignid']").val();
		}
		
		if (data.sqldesignid != undefined) {
			data.sqldesignid = $(modal).find("input[name='sqldesignid']").val();
		} else {
			data['sqldesignid'] = $(modal).find("input[name='sqldesignid']").val();
		}
		
		if (data.sqldesignidtext != undefined) {
			data.sqldesignidtext = $(modal).find("input[name='sqldesignidAutocomplete']").val();
		} else {
			data['sqldesignidtext'] = $(modal).find("input[name='sqldesignidAutocomplete']").val();
		}
		
		var optionlabel = '';
		if($(modal).find("input[name='optionlabel']").val() != undefined){
			optionlabel = $(modal).find("input[name='optionlabel']").val();
		}
		
		if (data.optionlabel != undefined) {
			data.optionlabel = $(modal).find("input[name='optionlabel']").val();
		} else {
			data['optionlabel'] = $(modal).find("input[name='optionlabel']").val();
		}
		
		if (data.optionlabeltext != undefined) {
			data.optionlabeltext = $(modal).find("input[name='optionlabelAutocomplete']").val();
		} else {
			data['optionlabeltext'] = $(modal).find("input[name='optionlabelAutocomplete']").val();
		}
		
		var optionvalue = '';
		if($(modal).find("input[name='optionvalue']").val() != undefined){
			optionvalue = $(modal).find("input[name='optionvalue']").val();
		}
		
		if (data.optionvalue != undefined) {
			data.optionvalue = $(modal).find("input[name='optionvalue']").val();
		} else {
			data['optionvalue'] = $(modal).find("input[name='optionvalue']").val();
		}
		
		if (data.optionvaluetext != undefined) {
			data.optionvaluetext = $(modal).find("input[name='optionvalueAutocomplete']").val();
		} else {
			data['optionvaluetext'] = $(modal).find("input[name='optionvalueAutocomplete']").val();
		}
	} else if (data.datasourcetype == '2') {
		var parameters = "";
		var localCodelist = $(modal).find("input:radio[name='localCodelist']:checked").val();
		if(localCodelist == 2){
			divtablename = "#dialog-component-list-setting-tbl-table-options";
			
		}else if (localCodelist == 3){
			divtablename = "#dialog-component-list-setting-tbl-options";
			var isSupportOptionValue  = "";
			if($(modal).find("input[name='supportOptionValue']")!= undefined){
				isSupportOptionValue = $(modal).find("input[name='supportOptionValue']").prop("checked");
			}
			if (data.isSupportOptionValue != undefined) {
				data.isSupportOptionValue = isSupportOptionValue;
			} else {
				data["isSupportOptionValue"] = isSupportOptionValue;
			}
			if(!validateRequiredAtLeast(modal,"dialog-component-list-setting-tbl-options",dbMsgSource['sc.screendesign.0148'],1)){
				return;
			}
			if(!validateRequired(modal,"parameterOptionValue",dbMsgSource['sc.screendesign.0097'],true)) {
				return;
			}
			if(!isSupportOptionValue){		
				if(!validateRequired(modal,"parameterOptionName",dbMsgSource['sc.screendesign.0096'],true)) {
					return;
				}
				if(!validateDuplication(modal,"parameterOptionName",dbMsgSource['sc.screendesign.0096'])) {
					return;
				}
				
			}
		}else if (localCodelist == 1){
			divtablename = "#dialog-component-list-setting-tbl-system-options";
			var codelistCode  = "";
			if($(modal).find("input[name='codelistCode']").val() != undefined){
				codelistCode = $(modal).find("input[name='codelistCode']").val();
			}
			var codelistText  = "";
			if($(modal).find("input[name='codelistCodeAutocomplete']").val() != undefined){
				codelistText = $(modal).find("input[name='codelistCodeAutocomplete']").val();
			}
			
			if (data.codelistCode != undefined) {
				data.codelistCode = codelistCode;
			} else {
				data["codelistCode"] = codelistCode;
			}
			
			if (data.codelistText != undefined) {
				data.codelistText = codelistText;
			} else {
				data["codelistText"] = codelistText;
			}
			if(!validateRequired(modal,"codelistCodeAutocomplete",dbMsgSource['sc.screendesign.0095'])) {
				return;
			}
		}
		if (localCodelist == 3){
			$(modal).find(divtablename).find("tbody").find("tr").each(function (i){
				var parameterOptionName = "";
				var parameterOptionValue = "";
				
				if($(this).find("[name=parameterOptionValue]").val() != undefined && $(this).find("[name=parameterOptionValue]").val().length > 0){
					parameterOptionValue = $(this).find("[name=parameterOptionValue]").val();
				}
				if(isSupportOptionValue){
					parameterOptionName = parameterOptionValue;
				}else if($(this).find("[name=parameterOptionName]").val() != undefined && $(this).find("[name=parameterOptionName]").val().length > 0 ){
					parameterOptionName = $(this).find("[name=parameterOptionName]").val();
				}
				parameters +=parameterOptionName + "π" + parameterOptionValue + "�";
				msglabel+= parameterOptionName + "�";
				msgvalue+= parameterOptionValue + "�";
			});
			
		}
		else{
			$(modal).find(divtablename).find("tbody").find("tr").each(function (i){
				if($(this).find("[name=parameterOptionValue]").html() != undefined){
					var parameterOptionName = "";
					parameterOptionName = $(this).find("[name=parameterOptionName]").html();
					var parameterOptionValue = "";
					parameterOptionValue = $(this).find("[name=parameterOptionValue]").html();
					
					if ($(this).find("[name=parameterOptionName]").html() != undefined) {
						parameterOptionName = $(this).find("[name=parameterOptionName]").html();
					} else {
						parameterOptionName = parameterOptionValue;
					}
					
					parameters +=parameterOptionName + "π" + parameterOptionValue + "�";
					msglabel+= parameterOptionName + "�";
					msgvalue+= parameterOptionValue + "�";
				}
			});
		}
		
		if(msglabel.charAt(msglabel.length-1)=="�"){
			msglabel = msglabel.substring(0, msglabel.length -1);
		}
		if(msgvalue.charAt(msgvalue.length-1)=="�"){
			msgvalue = msgvalue.substring(0, msgvalue.length -1);
		}
		if (data.msglabel != undefined) {
			data.msglabel = msglabel;
		} else {
			data["msglabel"] = msglabel;
		}
		if (data.msgvalue != undefined) {
			data.msgvalue = msgvalue;
		} else {
			data["msgvalue"] = msgvalue;
		}
		
		if (data.parameters != undefined) {
			data.parameters = parameters;
		} else {
			data["parameters"] = parameters;
		}
		
		if (data.localCodelist != undefined) {
			data.localCodelist = localCodelist;
		} else {
			data["localCodelist"] = localCodelist;
		}
	}
	
	var enableGroup = "";
	if( $(modal).find("input[name='enableGroup']")!= undefined){
		enableGroup =  $(modal).find("input[name='enableGroup']").prop("checked");
	}
	if (data.enablegroup != undefined) {
		data.enablegroup = enableGroup;
	} else {		
		data["enablegroup"] = enableGroup;
	}

	var groupDisplayType = $(modal).find("select[name='dialog-component-list-setting-group-display-type']").val();
	if(groupDisplayType != undefined){
		$(obj).closest("td").find("input[name='groupDisplayType']").val(groupDisplayType);	
		
		if (enableGroup) {
			
			$(obj).closest("td").droppable({
				accept: "#srcgenElements div[id!='divLabel'], #srcgenAction div, #newDragElementTd div",
				activeClass: "state-droppable",
				drop: function(event, ui) {					
					insertComponent(event, ui, $(this));			
				}
			});
			$(obj).closest("td").droppable( "option", "disabled", false );
			$(obj).closest("td").find("input[name='enableGroup']").val(true);
			
			//if inline
			if (groupDisplayType == 1) {
				$(obj).closest("td").children().css("float", "left");
				$(obj).closest("td").children().css("clear", "");
			} else if (groupDisplayType == 2) {			
				$(obj).closest("td").children().css("clear", "both");
			}
		} else {
			if ($(obj).closest("td").find("input[name=formElement]").length == 1) {
				if ($(obj).closest("td").is(".ui-droppable")) {
					$(obj).closest("td").droppable( "option", "disabled", true );
				}
				$(obj).closest("td").find("input[name='enableGroup']").val(false);
			}
		}
		$(obj).closest("td").children().css("float", "left");
		
		if (data.groupDisplayType != undefined) {
			data.groupDisplayType = groupDisplayType;
		} else {		
			data["groupDisplayType"] = groupDisplayType;
		}
	}
	if (data.width != '' && parseFloat(data.width) > 0) {
		$(obj).closest("span").css("width", data.width + data.widthunit);
	}	
	
	//save blogic
	data = saveBlogicSetting($(modal), data);
	
	if (mergeTable(obj, data, rowspan, colspan)) {
		if (data.rowspan != undefined) {
			data.rowspan = rowspan;
		} else {		
			data["rowspan"] = rowspan;
		}
		
		if (data.colspan != undefined) {
			data.colspan = colspan;
		} else {		
			data["colspan"] = colspan;
		}
		
		var result = changeElementType($(modal), obj, data); 
		if (result != false) {
			data = result.data;
			obj = result.obj;
		}
		
		var value =  convertToString(data);		
		if (data.datatype == 21) {
			$(obj).closest("span").find("label").html(data.columnname);
		}
		$(obj).closest("span").find("input[name='formElement']").val(value);
		
		
		redrawComponent(obj,data);
		$(modal).modal("hide");
	}
	
}

function saveBlogicSetting(modal, data) {
	var events = [];
	
	$(modal).find('input[name=blogicSetting]').each(function() {
		
		var event = convertToJson($(this).val());
		
		var eventtype = 1;
		if ($(this).val() == undefined || $(this).val() == null || $(this).val().length == 0) {
			eventtype = 2;
		}
		
		if (event.eventtype != undefined) {
			event.eventtype = eventtype;
		} else {
			event['eventtype'] = eventtype;
		}
		
		if (eventtype == 1) {

			
			if (event.effectareatype != undefined) {
				event.effectareatype = $(this).closest('.onchange-event').find('input[name^=radEffectAreaType]:checked').val();
				event.effectarea = $(this).closest('.onchange-event').find('input[name=effectArea]').val();
				event.blogicname = $(this).closest('.onchange-event').find('input[name=businessLoginAutocompleteAutocomplete]').val();
				event.blogicid = $(this).closest('.onchange-event').find('input[name=businessLoginAutocomplete]').val();
			} else {
				event['effectareatype'] = $(this).closest('.onchange-event').find('input[name^=radEffectAreaType]:checked').val();
				event['effectarea'] = $(this).closest('.onchange-event').find('input[name=effectArea]').val();
				event['blogicid'] = $(this).closest('.onchange-event').find('input[name=businessLoginAutocomplete]').val();
				event['blogicname'] = $(this).closest('.onchange-event').find('input[name=businessLoginAutocompleteAutocomplete]').val();
			}
		}
		
		events.push(event);
	});
	
	if (data.events != undefined) {
		data.events = events;
	} else {
		data['events'] = events;
	}
	
	return data;
}
function deleteDialogComponentListSetting(modalName) {
	var modal = $("#dialog-component-list-setting");
	if (modalName != null && modalName == 'dialog-component-list-setting-section') {
		modal = $("#dialog-component-list-setting-section");
	}
	
	var obj = $(modal).data("target");
	var data = convertToJson($(modal).data("data"));
	
	//delete element
	deleteElement(obj, data);
	
	$(modal).modal("hide");
}

function deleteDialogComponentSetting(modalName) {
	var modal = $("#dialog-component-setting");
	if (modalName != null && modalName == 'dialog-component-setting-section') {
		modal = $("#dialog-component-setting-section");
	}
	var obj = $(modal).data("target");
	var data = convertToJson($(modal).data("data"));
	
	//delete element
	deleteElement(obj, data);
	
	$(modal).modal("hide");
}
function saveDialogComponentSetting(modalName) {	
	var modal = $("#dialog-component-setting");
	if (modalName != null && modalName == 'dialog-component-setting-section') {
		modal = $("#dialog-component-setting-section");
	}
	if(!validateRequired(modal,"columnname",dbMsgSource['sc.screendesign.0093'])) {
		return;
	}
	if(!validateAlphanumeric(modal,"columnname",dbMsgSource['sc.screendesign.0093'])) {
		return;
	}
	if(!validateRequired(modal,"maxlength",dbMsgSource['sc.screendesign.0127'])) {
		return;
	}
	if(!validateFromTo(modal,"minValue","maxValue",dbMsgSource['sc.screendesign.0176'],dbMsgSource['sc.screendesign.0177'])) {
		return;
	}
	$.qp.undoFormatNumericForm(modal);
	obj = $(modal).data("target");
	data = convertToJson($(modal).data("data"));
	var colSpanOld = data.colspan;
	var rowSpanOld = data.rowspan;
	var columnname  = "";
	if($(modal).find("input[name='columnname']").val() != undefined){
		columnname = $(modal).find("input[name='columnname']").val();
	}
	
	var mandatory = "";
	if($(modal).find("input[name='mandatory']").val() != undefined){		
		if ($(modal).find("input[name='mandatory']").prop("checked")) {			
			mandatory = true;
		} else {
			mandatory = false;
		}	
	}
		
	var maxlength = "";
	if($(modal).find("input[name='maxlength']").val() != undefined){
		maxlength = $(modal).find("input[name='maxlength']").val();
	}
	
	var formatcode = "";
	if($(modal).find("select[name='validateRule']").val() != undefined){
		formatcode = $(modal).find("select[name='validateRule']").val();
	}
		
	var rowspan = "";
	if($(modal).find("input[name='rowspan']").val() != undefined){
		rowspan = $(modal).find("input[name='rowspan']").val();
	}
	
	var colspan = "";
	if($(modal).find("input[name='colspan']").val() != undefined){
		colspan = $(modal).find("input[name='colspan']").val();
	}
	
	var minValue = "";
	if($(modal).find("input[name='minValue']").val() != undefined){
		minValue = $(modal).find("input[name='minValue']").val();
	}
	
	var maxValue = "";
	if($(modal).find("input[name='maxValue']").val() != undefined){
		maxValue = $(modal).find("input[name='maxValue']").val();
	}
	
	var enableGroup = "";
	if($(modal).find("input[name='enableGroup']").val() != undefined){
		enableGroup = $(modal).find("input[name='enableGroup']").val();
	}
	
	var elementWidth = "";
	if($(modal).find("[name='dialog-component-setting-element-width']").val() != undefined){
		elementWidth = $(modal).find("[name='dialog-component-setting-element-width']").val();
	}
	
	var elementWidthUnit = "";
	if($(modal).find("[name='dialog-component-setting-element-width-unit']").val() != undefined){
		elementWidthUnit = $(modal).find("[name='dialog-component-setting-element-width-unit']").val();
	}
	
	
	if (data.columnname != undefined) {
		data.columnname = columnname;
	} else {
		data["columnname"] = columnname;
	}
	
	if (data.mandatory != undefined) {
		data.mandatory = mandatory.toString();
	} else {
		data["mandatory"] = mandatory.toString();
	}
	
	if (data.maxlength != undefined) {
		data.maxlength = maxlength;
	} else {		
		data["maxlength"] = maxlength;
	}	
	
	if (data.formatcode != undefined) {
		data.formatcode = formatcode;
	} else {		
		data["formatcode"] = formatcode;
	}
	
	if (data.minvalue != undefined) {
		data.minvalue = minValue;
	} else {		
		data["minvalue"] = minValue;
	}
	
	if (data.maxvalue != undefined) {
		data.maxvalue = maxValue;
	} else {		
		data["maxvalue"] = maxValue;
	}

	if (data.enablegroup != undefined) {
		data.enablegroup = enableGroup;
	} else {		
		data["enablegroup"] = enableGroup;
	}
	
	if (elementWidth == '') {
		elementWidth = "100";
	}
	
	if (data.width != undefined) {
		data.width = elementWidth;
	} else {		
		data["width"] = elementWidth;
	}
	
	if (data.widthunit != undefined) {
		data.widthunit = elementWidthUnit;
	} else {		
		data["widthunit"] = elementWidthUnit;
	}
	
	
	result = saveProperties(modal, data);
	if (!result) {
		return;
	}
	data = result;
	
	if (modalName == null || modalName == undefined || modalName != 'dialog-component-setting-section') {
		var groupDisplayType = $(modal).find("select[name='dialog-component-setting-group-display-type']").val();
		
		$(obj).closest("td").find("input[name='groupDisplayType']").val(groupDisplayType);	
		
		if ($(modal).find("input[name='enableGroup']").prop("checked")) {
			
			$(obj).closest("td").droppable({
				accept: "#srcgenElements div[id!='divLabel'], #srcgenAction div, #newDragElementTd div",
				activeClass: "state-droppable",
				drop: function(event, ui) {					
					insertComponent(event, ui, $(this));			
				}
			});
			$(obj).closest("td").droppable( "option", "disabled", false );
			$(obj).closest("td").find("input[name='enableGroup']").val(true);
			
			//if inline
			if (groupDisplayType == 1) {
				$(obj).closest("td").children().css("float", "left");
				$(obj).closest("td").children().css("clear", "");
			} else if (groupDisplayType == 2) {			
				$(obj).closest("td").children().css("clear", "both");
			}
			
		} else {
			if ($(obj).closest("td").find("input[name=formElement]").length == 1) {
				if ($(obj).closest("td").is(".ui-droppable")) {
					$(obj).closest("td").droppable( "option", "disabled", true );
				}
				$(obj).closest("td").find("input[name='enableGroup']").val(false);
			}
		}
		$(obj).closest("td").children().css("float", "left");
		
		if (mergeTable(obj, data, rowspan, colspan)) {
			if (data.rowspan != undefined) {
				data.rowspan = rowspan;
			} else {		
				data["rowspan"] = rowspan;
			}
			
			if (data.colspan != undefined) {
				data.colspan = colspan;
			} else {		
				data["colspan"] = colspan;
			}
			var value =  convertToString(data);		
			if (data.datatype == 21) {
				$(obj).closest("span").find("label").html(data.columnname);
			}
			$(obj).closest("span").find("input[name='formElement']").val(value);
			
			if (data.width != '' && parseFloat(data.width) > 0) {
				
				if (data.datatype == 21 || data.datatype == 2 || data.datatype == 18 || data.datatype == 15 || data.datatype == 16 || data.datatype == 10 || data.datatype == 3|| data.datatype == 1 || data.datatype == 8) {						
					$(obj).parent().css("width", data.width + data.widthunit);
				} else if (data.datatype == 14 || data.datatype == 9 || data.datatype == 4) {
					$(obj).closest(".date").closest("span").css("width", data.width + data.widthunit);
				}
			}	
			
			
			$(modal).modal("hide");
		}
	} else {
		var value =  convertToString(data);		
		if (data.datatype == 21) {
			$(obj).closest("span").find("label").html(data.columnname);
		}
		$(obj).closest("span").find("input[name='formElement']").val(value);
		
		if (data.width != '' && parseFloat(data.width) > 0) {
			
			if (data.datatype == 21 || data.datatype == 2 || data.datatype == 18 || data.datatype == 15 || data.datatype == 16 || data.datatype == 10 || data.datatype == 3|| data.datatype == 1 || data.datatype == 8) {						
				$(obj).parent().css("width", data.width + data.widthunit);
			} else if (data.datatype == 14 || data.datatype == 9 || data.datatype == 4) {
				$(obj).closest(".date").closest("span").css("width", data.width + data.widthunit);
			}
		}	
		
		
		$(modal).modal("hide");		
	}

}

function dialogLableSettingChecked(modalName) {
	var modal = $("#dialog-label-setting");
	if (modalName != null && modalName == 'dialog-label-setting-section') {
		modal = $("#dialog-label-setting-section");
	}
	var autocomplete = $(modal).find("input[name='labelName']"); 
	
	if ($(modal).find("input[name='isBlank']").prop("checked")) {
		$.qp.disableAutocomplete($(autocomplete));		
		$(modal).find("input[name='labelName']").val("");
		$(modal).find("input[name='labelNameAutocomplete']").val("");
	} else {
		$.qp.enableAutocomplete($(autocomplete));
	}
}

function saveDialogLableSetting(modalName) {
	var modal = $("#dialog-label-setting");
	

	if (modalName != null && modalName == 'dialog-label-setting-section') {
		modal = $("#dialog-label-setting-section");
	}
	
	$.qp.undoFormatNumericForm(modal);
	obj = $(modal).data("target");
	data = convertToJson($(modal).data("data"));
	
	var label  = "";
	if($(modal).find("input[name='labelName']").val() != undefined){
		label = $(modal).find("input[name='labelName']").val();
	}
	
	var labelText  = "";
	var lableTextElement = $(modal).find("input[name='labelNameAutocomplete']"); 
	if(lableTextElement.val() != undefined){
		labelText =lableTextElement.val();
	}
	
	var mandatory = "";
	if($(modal).find("input[name='mandatory']").val() != undefined){		
		if ($(modal).find("input[name='mandatory']").prop("checked")) {			
			mandatory = true;
		} else {
			mandatory = false;
		}	
	}
		
	var isBundle = "";
	if($(modal).find("input[name='labelNameAutocomplete']").attr("selectedvalue") != undefined){		
		isBundle = $(modal).find("input[name='labelNameAutocomplete']").attr("selectedvalue");
	}
	
	var isBlank = "";
	if($(modal).find("input[name='isBlank']").val() != undefined){
		if ($(modal).find("input[name='isBlank']").prop("checked")) {
			isBlank = true;			
		} else {
			isBlank = false;
		}		
	}
	if(!isBlank){
		if(!validateRequired(modal,"labelNameAutocomplete",dbMsgSource['sc.screendesign.0103'])) {
			return;
		}
	}	
	var rowspan = "";
	if($(modal).find("input[name='rowspan']").val() != undefined){
		rowspan = $(modal).find("input[name='rowspan']").val();
	}
	
	var colspan = "";
	if($(modal).find("input[name='colspan']").val() != undefined){
		colspan = $(modal).find("input[name='colspan']").val();
	}
	
	if (data.label != undefined) {
		data.label = label;
	} else {
		data["label"] = label;
	}
	
	if (data.labelText != undefined) {
		data.labelText = labelText;
	} else {
		data["labelText"] = labelText;
	}
	
	if (data.mandatory != undefined) {
		data.mandatory = mandatory;
	} else {
		data["mandatory"] = mandatory;
	}
	
	if (data.isBundle != undefined) {
		data.isBundle = isBundle.toString();
	} else {		
		data["isBundle"] = isBundle.toString();
	}
	
	if (data.isBlank != undefined) {
		data.isBlank = isBlank.toString();
	} else {		
		data["isBlank"] = isBlank.toString();
	}
	
	if (eval(data.isBlank) == true) {			
		data.label = "";
		data.labelText = dbMsgSource['sc.screendesign.0174'];
	}
	
	if (rowspan == undefined) {
		rowspan = "";
	}
	
	if (colspan == undefined) {
		colspan = "";
	}
	
	if (mergeTable(obj, data, rowspan, colspan)) {
		if (data.rowspan != undefined) {
			data.rowspan = (rowspan > 0)?rowspan:"";
		} else {		
			data["rowspan"] = (rowspan > 0)?rowspan:"";
		}
		
		if (data.colspan != undefined) {
			data.colspan = (colspan > 0)?colspan:"";
		} else {		
			data["colspan"] = (colspan > 0)?colspan:"";
		}		

		if (data.isBundle == "false" || data.isBundle == "") {
			data.label = "";
		}
		
		var value =  convertToString(data);		
		
		if (modalName != null && modalName == 'dialog-label-setting-section') {
			$(obj).next().val(value);
		} else {
			$(obj).closest("th").find("input[name='formElement']").val(value);
		}
		
		
		var mandatoryText = "";	
		if (eval(data.mandatory)) {
			mandatoryText = "&nbsp;<label class=\"qp-required-field\">"+ fcomMsgSource['sc.sys.0029'] +"</label>";
		}
		
		$(obj).html(data.labelText + mandatoryText);
		
		var table = $(obj).closest("table");
		if ($(table).is("table[class*=qp-table-form]")) {
			checkColRowSpan($(table));
			checkSwap($(table));
		} else if ($(table).is("table[class*=qp-table-list-none-action]")) {
			checkColRowSpan($(table));
			checkSwapListTable($(table));
		}
		
		$(modal).modal("hide");
	} 
	
}

function deleteDialogLableSetting(modalName) {
	//delete
	var modal = $("#dialog-label-setting");
	if (modalName != null && modalName == 'dialog-label-setting-section') {
		modal = $("#dialog-label-setting-section");
	}
	
	var obj = $(modal).data("target");
	var data = convertToJson($(modal).data("data"));
	var th = $(obj).closest("th");
	var table = $(obj).closest("table");
	
	//delete element
	deleteElement(obj, data);
	
	$(th).find(".glyphicon-screenshot").draggable({
		containment: '#' + $(table).attr("id"),
		stack: '#' + $(table).attr("id"),
		revert: function(event, ui) {
			if ($(this).data("uiDraggable") != undefined && $(this).data("uiDraggable").originalPosition != undefined) {
				$(this).data("uiDraggable").originalPosition = {top : 0,left : 0};
			}
			return true;
		},
		stop: function(event, ui) {
    	  	$(this).css("z-index","auto");
    	  } 
	});
	
	$(modal).modal("hide");
}
function dialogLabelSettinglableOnSelect(event) {		
	var dialog = $("#dialog-label-setting");
	$(dialog).find("input[name='isBundle']").val(true);	
}
function dialogButtonAreaSettinglableOnSelect(event) {		
	var dialog = $("#dialog-button-area-setting");
	$(dialog).find("input[name='isBundle']").val(true);	
}
function dialogLinkAreaSettinglableOnSelect(event) {		
	var dialog = $("#dialog-link-area-setting");
	$(dialog).find("input[name='isBundle']").val(true);	
}
function dialogLinkAreaSettingTableCodeOnChange(obj){
	var value = $(obj.target).next("input[type=hidden]").val();
	var nextInput = $(obj.target).closest("tr").find("input[id='columnCodeAutocompleteId']");
	var nextHidden = nextInput.next("input[type=hidden]");
	nextInput.val("");
	nextInput.attr("arg01",value);
	nextHidden.val("");
	nextInput.data("ui-autocomplete")._trigger("change");
};
function onChangeSettingTableCode(obj){
	var value = $(obj.target).next("input[type=hidden]").val();
	var nextInput = $(obj.target).closest("tr").find("input[id='columnCodeAutocompleteId']");
	var nextHidden = nextInput.next("input[type=hidden]");
	nextInput.val("");
	nextInput.attr("arg01",value);
	nextHidden.val("");
	nextInput.data("ui-autocomplete")._trigger("change");
};
function saveDialogTableSetting() {	
	
	var modal = $("#modal-table-setting");
	if(!validateRequired(modal,"parameterAttribute",dbMsgSource['sc.screendesign.0110'],true)) {
		return;
	}
	if(!validateRequired(modal,"areaCode",dbMsgSource['sc.screendesign.0178'])) {
		return;
	}
	if(!validateAlphanumeric(modal,"areaCode",dbMsgSource['sc.screendesign.0178'])) {
		return;
	}
	
	
	
	var setting = $(modal).data('data');
	
	var indexCurrentArea = $(setting).closest('.areaContent').index();
	var countArea =0;
	var areaCode = $(modal).find('input[name=areaCode]').val(); 
	$(setting).closest('.form-area-content').find('.areaContent').each(function(index){
		if (indexCurrentArea != index) {
			if (areaCode == $(this).find('input[name=formAreaCode]').val()) {
				countArea++;
			}
		}
	});
	
	if (countArea > 0) {
		alert(dbMsgSource['sc.screendesign.0292']);
		return;
	}
	
	
	$(setting).next("input").val($(modal).find("[name=tableCaption]").val());
	
	if(isProhibitChar($(modal).find("[name=tableCaption]").val())){
		$(modal).find("#dialog-form-table-error").html("").append("<font color=\"red\">"+fcomMsgSource['err.sys.0033'].replace("{0}", dbMsgSource['sc.screendesign.0118']).replace("{1}", "' \"")+"</font>");
		return false;
	}
	
	var isError = false;
	$(modal).find("#tableDialogHiddenAttribute").find("tbody").find("tr").each(function (i){
		if($(this).find("input[name=parameterAttribute]").val() != undefined && $(this).find("input[name=parameterAttribute]").val().length > 0){
			
			if(isNotAlphanumeric($(this).find("input[name=parameterAttribute]").val())){
				$(modal).find("#dialog-form-table-error").html("").append("<font color=\"red\">"+fcomMsgSource['err.sys.0032'].replace("{0}", (i+1)).replace("{1}", dbMsgSource['sc.screendesign.0110'])+"</font>");
				isError = true;
				return false;
			}
		}
	});
	if(isError){
		return false;
	}
	
	var table =  $(setting).closest("span").closest("div").next("div").find("table").first();
	
	var width = $(modal).find("[name=tableWidth]").val();
		
	if(width != undefined && width.length > 0 && width != "0"){
		width += $(modal).find("[name=tableWidthUnit]").val();
	} else {
		width = "100%"
	}
	$(setting).closest("span").find("input[name=formTableWidth]").val(width);
	$(setting).closest("span").closest("div[class=areaContent]").css("width", width);
	
	if($(modal).find("[name=tablePosition]").val() != ""){
		$(setting).closest("span").find("input[name=formTablePosition]").val($(modal).find("[name=tablePosition]").val());
		if($(modal).find("[name=tablePosition]").val()=="3"){
			$(setting).closest("span").closest("div[class=areaContent]").css("float", "right");
		}else{
			$(setting).closest("span").closest("div[class=areaContent]").css("float", "left");
		}
	}
	
	//add more parameter
	var tableCaption  = "";
	if($(modal).find("input[name='tableCaption']").val() != undefined){
		tableCaption = $(modal).find("input[name='tableCaption']").val();
	}
	
	var tableCaptionText  = "";
	var tableCaptionTextElement = $(modal).find("input[name='tableCaptionAutocomplete']"); 
	if(tableCaptionTextElement.val() != undefined){
		tableCaptionText =tableCaptionTextElement.val();
	}
	
	$(setting).next().html(tableCaptionText);
	
	if($(modal).find("[name=areaCode]").val()  != undefined && $(modal).find("[name=areaCode]").val().length > 0){
		$(setting).closest("span").find("input[name=formAreaCode]").val($(modal).find("[name=areaCode]").val());
	}
	
	var isBundle = "";
	if($(modal).find("input[name='tableCaptionAutocomplete']").attr("selectedvalue") != undefined){		
		isBundle = $(modal).find("input[name='tableCaptionAutocomplete']").attr("selectedvalue");
	}
	
	if (isBundle == "true") {
		$(setting).closest("span").find("input[name=formAreaCaptionId]").val(tableCaption);
	} else {
		$(setting).closest("span").find("input[name=formAreaCaptionId]").val("");
	}
	
	
	$(setting).closest("span").find("input[name=formAreaCaptionText]").val(tableCaptionText);
	$(setting).closest("span").find("input[name=formAreaCaptionIsbundle]").val(isBundle);
	

	
	//
	
	if($(modal).find("[name=tableHeaderRow]").val() != "" && $(modal).find("[name=tableHeaderRow]").val() > 0 && $(modal).find("[name=tableHeaderRow]").val() < 6){
		var headerRow = $(modal).find("[name=tableHeaderRow]").val();
		var formElementTable = $(setting).closest("span").find("input[name=formElementTable]").val();
		var colSizeArr = formElementTable.split(",");
		var trLength = $(table).find("thead").find("tr").length;
		if(headerRow > trLength){
			for(i=trLength;i<headerRow;i++){
				var trHead = "<tr class=\"style-even-row\" index="+i+">";
				var trBody = "<tr class=\"style-odd-row\" index="+i+">";
				for(j=0;j<colSizeArr[0];j++){
					trHead += "<th class=\"style-header-table\" index=" + j + "><span style=\"float: left; cursor: move;\" class=\"ui-icon ui-icon-arrow-4\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></span><input type=\"hidden\" name=\"formElement\" /><div>&nbsp;</div></th>";
					trBody += "<td index=" + j + ">&nbsp;</td>";
				}
				trHead += "</tr>";
				trBody += "</tr>";
				
				$(table).find("thead").append(trHead);
				$(table).find("tbody").append(trBody);
			}
		} 
		if(headerRow < trLength){
			for(i=trLength;i>headerRow-1;i--){
				$(table).find("thead").find("tr:eq("+i+")").remove();
				$(table).find("tbody").find("tr:eq("+i+")").remove();
			}
			$(table).find("thead").find("tr").each(function(i){
				var trIndex = $(this).attr("index");
				$(this).find("th").each(function(j){
					if($(this).attr("rowspan") != undefined && $(this).attr("rowspan") > 1 
							&& $(this).attr("rowspan") > $(table).find("thead").find("tr").length - trIndex){
						var elementVal = $(this).find("input[name=formElement]").val();
						var elementValJSON = $.parseJSON("{"+elementVal+"}");
						$(this).attr("rowspan", $(table).find("thead").find("tr").length - trIndex);
						$(this).find("input[name=formElement]").val(elementVal.replace('"rowspan":"'+elementValJSON.rowspan+'"', '"rowspan":"'+($(table).find("thead").find("tr").length - trIndex)+'"'));
					}
				});
			});
			$(table).find("tbody").find("tr").each(function(i){
				var trIndex = $(this).attr("index");
				$(this).find("td").each(function(j){
					if($(this).attr("rowspan") != undefined && $(this).attr("rowspan") > 1 
							&& $(this).attr("rowspan") > $(table).find("tbody").find("tr").length - trIndex){
						$(this).attr("rowspan", $(table).find("tbody").find("tr").length - trIndex);
					}
				});
			});
		}
		$(setting).closest("span").find("input[name=formTableRow]").val(headerRow);
		
		$(table).find("div").droppable({
			accept: "#srcgenElements div, #srcgenAction div, #newDragElementTd div",
			activeClass: "state-droppable",
			drop: function(event, ui) {
				insertComponentList(event, ui, $(this));
			}
		 });
		 
		 var tableId = $(table).attr('id');
		 
		 $(table).find("span").draggable({
				containment: '#' + tableId,
				stack: '#' + tableId,
				revert: true,
				stop: function(event, ui) {
		    	  	$(this).css("z-index","auto");
		    	  } 
		 });
		
		 $(table).find("span").droppable({
			accept: "#" + tableId + " span",
			activeClass: "state-droppable",
			drop: function(event, ui) {
				if($(this).prev("input").length == 0){
					replaceElementList(event, ui, $(this));
				} else {
					replaceElement(event, ui, $(this));
				}
			}
		});
		
	}
	
	var formTableColumnSize = "";
	$(table).find("colgroup").empty();
	var colsWidth = "";
	
	$(modal).find("[name=columnnameSize]").each(function (i){
		if($(this).val().length > 0){
			colsWidth += "<col width=\"" + $(this).val() + $(this).closest("td").find("[name='columnnameSizeUnit']").eq(i).val() + "\">";
		} else {
			colsWidth += "<col width=\"\">";
		}
		if($(this).val().length == 0){
			formTableColumnSize += ",";
		} else {
			formTableColumnSize += $(this).val() + $(this).closest("td").find("[name='columnnameSizeUnit']").eq(i).val() + ",";
		}
	});
	$(table).find("colgroup").append(colsWidth);
	
	if(formTableColumnSize.length > 0){
		$(setting).closest("span").find("input[name=formTableColumnSize]").val(formTableColumnSize.substring(0, formTableColumnSize.length - 1));
	}
	
	var area = $(setting).closest('.areaContent');
	var codeExists = '';
	var isValid = true;
	$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr").find("input[name=parameterAttribute]").each(function (i){
		var curValue = $(this).val();
		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr").find("input[name=parameterAttribute]").each(function (j){
			if (j > i) {
				if (curValue == $(this).val()) {
					isValid = false;
					codeExists = curValue;
					return;
				}
			}
		});
	});
	
	if (!isValid) {
		alert(dbMsgSource['sc.screendesign.0295'].replace("{0}"),codeExists);
		return;
	}
	
	$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr").find("input[name=parameterAttribute]").each(function (){
		var curValue = $(this).val();
		$(area).find('input[name=formElement]').each(function(){
			var json = convertToJson($(this).val());
			if ((json.datatype != 20 && json.datatype != 13 && json.datatype != 11) && curValue == json.columnname) {
				isValid = false;
				codeExists = curValue;
				return;
			}
		});
	});
	
	if (!isValid) {
		alert(dbMsgSource['sc.screendesign.0296'].replace("{0}"),codeExists);
		return;
	}
	
	var hiddens = "";
	$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr").each(function (i){
		if($(this).find("input[name=parameterAttribute]").val() != undefined && $(this).find("input[name=parameterAttribute]").val().length > 0){
			
			hiddens += $(this).find("input[name=parameterAttribute]").val()
			+ "π" + $(this).find("input[name=labelName]").val()
			+ "π" + $(this).find("input[name=labelNameAutocomplete]").val()
			hiddens += "�";
		}
	});
		
	$(setting).closest("span").find("input[name=formElementHidden]").val(hiddens.substring(0, hiddens.length - 1));
	
	//save event
	saveEventArea(modal, setting);
	//save form 
	saveFormInfor(setting,$(modal).find("#modal-table-setting-form"));
	$(modal).modal("hide");
}
function updateAreaForm(obj,currentIndex,oldIndex){
	var currentArea;
	
	if(currentIndex < oldIndex){
		$(currentArea).prevAll(".areaContent").each(function (){
			var thizFormIndex = $(this).find("input[name=formIndex]").val();
			if(oldIndex == thizFormIndex){
				$(this).find("input[name=formIndex]").val(currentIndex);
			}
		});
		$(currentArea).nextAll(".areaContent").each(function (){
//			var thizFormIndex = $(this).find("input[name=formIndex]").val();
//			if(thizFormIndex != undefined && thizFormIndex.length > 0 ){
//			}else{
//				$(this).find("input[name=formIndex]").val(currentIndex);
//			}
		});
	}
}

function saveDialogSectionSetting() {	
	var modal = $("#modal-section-setting");
	if(!validateRequired(modal,"parameterAttribute",dbMsgSource['sc.screendesign.0110'],true)) {
		return;
	}
	if(!validateRequired(modal,"areaCode",dbMsgSource['sc.screendesign.0178'])) {
		return;
	}
	if(!validateAlphanumeric(modal,"areaCode",dbMsgSource['sc.screendesign.0178'])) {
		return;
	}
	var setting = $(modal).data('target');
	$(setting).next("input").val($(modal).find("[name=tableCaption]").val());
	
	if(isProhibitChar($(modal).find("[name=tableCaption]").val())){
		$(modal).find("#dialog-form-table-error").html("").append("<font color=\"red\">"+fcomMsgSource['err.sys.0033'].replace("{0}", dbMsgSource['sc.screendesign.0118']).replace("{1}", "' \"")+"</font>");
		return false;
	}
	
	var isError = false;
	$(modal).find("#tableDialogHiddenAttribute").find("tbody").find("tr").each(function (i){
		if($(this).find("input[name=parameterAttribute]").val() != undefined && $(this).find("input[name=parameterAttribute]").val().length > 0){
			
			if(isNotAlphanumeric($(this).find("input[name=parameterAttribute]").val())){
				$(modal).find("#dialog-form-table-error").html("").append("<font color=\"red\">"+fcomMsgSource['err.sys.0032'].replace("{0}", (i+1)).replace("{1}", dbMsgSource['sc.screendesign.0110'])+"</font>");
				isError = true;
				return false;
			}
		}
	});
	if(isError){
		return false;
	}
	//add more parameter
	var tableCaption  = "";
	if($(modal).find("input[name='tableCaption']").val() != undefined){
		tableCaption = $(modal).find("input[name='tableCaption']").val();
	}
	
	var tableCaptionText  = "";
	var tableCaptionTextElement = $(modal).find("input[name='tableCaptionAutocomplete']"); 
	if(tableCaptionTextElement.val() != undefined){
		tableCaptionText =tableCaptionTextElement.val();
	}
	
	var isBundle = "";
	if($(modal).find("input[name='tableCaptionAutocomplete']").attr("selectedvalue") != undefined){		
		isBundle = $(modal).find("input[name='tableCaptionAutocomplete']").attr("selectedvalue");
	}
	
	$(setting).closest("span").find("input[name=formAreaCaptionId]").val(tableCaption);
	$(setting).closest("span").find("input[name=formAreaCaptionText]").val(tableCaptionText);
	if($(modal).find("[name=areaCode]").val()  != undefined && $(modal).find("[name=areaCode]").val().length > 0){
		$(setting).closest("span").find("input[name=formAreaCode]").val($(modal).find("[name=areaCode]").val());
	}
	$(setting).closest("span").find("input[name=formAreaCaptionIsbundle]").val(isBundle);
	//
	var table =  $(setting).closest("span").closest("div").next("div").find("table").first();
	
	var width = $(modal).find("[name=tableWidth]").val();
	if(width != undefined && width.length > 0 && width != "0"){
		width += $(modal).find("[name=tableWidthUnit]").val();
	} else {
		width = "100%"
	}
	$(setting).closest("span").find("input[name=formTableWidth]").val(width);
	$(setting).closest("span").closest("div[class=areaContent]").css("width", width);
	
	if($(modal).find("[name=tablePosition]").val() != ""){
		$(setting).closest("span").find("input[name=formTablePosition]").val($(modal).find("[name=tablePosition]").val());
		if($(modal).find("[name=tablePosition]").val()=="3"){
			$(setting).closest("span").closest("div[class=areaContent]").css("float", "right");
		}else{
			$(setting).closest("span").closest("div[class=areaContent]").css("float", "left");
		}
	}
	
	if($(modal).find("[name=tableHeaderRow]").val() != "" && $(modal).find("[name=tableHeaderRow]").val() > 0 && $(modal).find("[name=tableHeaderRow]").val() < 6){
		var headerRow = $(modal).find("[name=tableHeaderRow]").val();
		var formElementTable = $(setting).closest("span").find("input[name=formElementTable]").val();
		var colSizeArr = formElementTable.split(",");
		var trLength = $(table).find("thead").find("tr").length;
		if(headerRow > trLength){
			for(i=trLength;i<headerRow;i++){
				var trHead = "<tr class=\"style-even-row\" index="+i+">";
				var trBody = "<tr class=\"style-odd-row\" index="+i+">";
				for(j=0;j<colSizeArr[0];j++){
					trHead += "<th class=\"style-header-table\" index=" + j + "><span style=\"float: left; cursor: move;\" class=\"ui-icon ui-icon-arrow-4\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></span><input type=\"hidden\" name=\"formElement\" /><div>&nbsp;</div></th>";
					trBody += "<td index=" + j + ">&nbsp;</td>";
				}
				trHead += "</tr>";
				trBody += "</tr>";
				
				$(table).find("thead").append(trHead);
				$(table).find("tbody").append(trBody);
			}
		} 
		if(headerRow < trLength){
			for(i=trLength;i>headerRow-1;i--){
				$(table).find("thead").find("tr:eq("+i+")").remove();
				$(table).find("tbody").find("tr:eq("+i+")").remove();
			}
			$(table).find("thead").find("tr").each(function(i){
				var trIndex = $(this).attr("index");
				$(this).find("th").each(function(j){
					if($(this).attr("rowspan") != undefined && $(this).attr("rowspan") > 1 
							&& $(this).attr("rowspan") > $(table).find("thead").find("tr").length - trIndex){
						var elementVal = $(this).find("input[name=formElement]").val();
						var elementValJSON = $.parseJSON("{"+elementVal+"}");
						$(this).attr("rowspan", $(table).find("thead").find("tr").length - trIndex);
						$(this).find("input[name=formElement]").val(elementVal.replace('"rowspan":"'+elementValJSON.rowspan+'"', '"rowspan":"'+($(table).find("thead").find("tr").length - trIndex)+'"'));
					}
				});
			});
			$(table).find("tbody").find("tr").each(function(i){
				var trIndex = $(this).attr("index");
				$(this).find("td").each(function(j){
					if($(this).attr("rowspan") != undefined && $(this).attr("rowspan") > 1 
							&& $(this).attr("rowspan") > $(table).find("tbody").find("tr").length - trIndex){
						$(this).attr("rowspan", $(table).find("tbody").find("tr").length - trIndex);
					}
				});
			});
		}
		$(setting).closest("span").find("input[name=formTableRow]").val(headerRow);
		
		$(table).find("div").droppable({
			accept: "#srcgenElements div, #srcgenAction div, #newDragElementTd div",
			activeClass: "state-droppable",
			drop: function(event, ui) {
				insertComponentList(event, ui, $(this));
			}
		 });
		 
		 var tableId = $(table).attr('id');
		 
		 $(table).find("span").draggable({
				containment: '#' + tableId,
				stack: '#' + tableId,
				revert: true,
				stop: function(event, ui) {
		    	  	$(this).css("z-index","auto");
		    	  } 
		 });
		
		 $(table).find("span").droppable({
			accept: "#" + tableId + " span",
			activeClass: "state-droppable",
			drop: function(event, ui) {
				if($(this).prev("input").length == 0){
					replaceElementList(event, ui, $(this));
				} else {
					replaceElement(event, ui, $(this));
				}
			}
		});
		
	}
	
	var formTableColumnSize = "";
	$(table).find("colgroup").empty();
	var colsWidth = "";
	
	$(modal).find("[name=columnnameSize]").each(function (i){
		if($(this).val().length > 0){
			colsWidth += "<col width=\"" + $(this).val() + $(this).closest("td").find("[name='columnnameSizeUnit']").eq(i).val() + "\">";
		} else {
			colsWidth += "<col width=\"\">";
		}
		if($(this).val().length == 0){
			formTableColumnSize += ",";
		} else {
			formTableColumnSize += $(this).val() + $(this).closest("td").find("[name='columnnameSizeUnit']").eq(i).val() + ",";
		}
	});
	$(table).find("colgroup").append(colsWidth);
	
	if(formTableColumnSize.length > 0){
		$(setting).closest("span").find("input[name=formTableColumnSize]").val(formTableColumnSize.substring(0, formTableColumnSize.length - 1));
	}
	
	var area = $(setting).closest('.areaContent');
	var codeExists = '';
	var isValid = true;
	$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr").find("input[name=parameterAttribute]").each(function (i){
		var curValue = $(this).val();
		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr").find("input[name=parameterAttribute]").each(function (j){
			if (j > i) {
				if (curValue == $(this).val()) {
					isValid = false;
					codeExists = curValue;
					return;
				}
			}
		});
	});
	
	if (!isValid) {
		alert(dbMsgSource['sc.screendesign.0295'].replace("{0}"),codeExists);
		return;
	}
	
	$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr").find("input[name=parameterAttribute]").each(function (){
		var curValue = $(this).val();
		$(area).find('input[name=formElement]').each(function(){
			var json = convertToJson($(this).val());
			if ((json.datatype != 20 && json.datatype != 13 && json.datatype != 11) && curValue == json.columnname) {
				isValid = false;
				codeExists = curValue;
				return;
			}
		});
	});
	
	if (!isValid) {
		alert(dbMsgSource['sc.screendesign.0296'].replace("{0}"),codeExists);
		return;
	}
	
	var hiddens = "";
	$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr").each(function (i){
		if($(this).find("input[name=parameterAttribute]").val() != undefined && $(this).find("input[name=parameterAttribute]").val().length > 0){
			
			hiddens += $(this).find("input[name=parameterAttribute]").val()
			+ "π" + $(this).find("input[name=labelName]").val()
			+ "π" + $(this).find("input[name=labelNameAutocomplete]").val()
			hiddens += "�";
		}
	});
		
	$(setting).closest("span").find("input[name=formElementHidden]").val(hiddens.substring(0, hiddens.length - 1));
	
	
	var displayType = $(modal).find("[name='modal-section-setting-display-type']").val();
	if(displayType != undefined)
		$(setting).closest("span").find("input[name=formDisplayType]").val(displayType);
	//float: left; width: 25%; padding: 2px; height: 30px;
	if (displayType != undefined && displayType == '0') {
		$(setting).closest("div[class=panel-heading]").next().find("div[class^='section-area']").children().each(function() {			
			$(this).css("width", "25%");
			$(this).css("padding", "2px");
			$(this).css("height", "30px");
			$(this).css("clear", "");
		});
	} else if (displayType == 1) {		
		$(setting).closest("div[class=panel-heading]").next().find("div[class^='section-area']").find("span").each(function() {
			$(this).css("clear", "both");
		});		
	}
	
	var direction = $(modal).find("[name='direction']").val();
	
	if(direction != undefined){
		if (direction == 0) {
			$(setting).closest("div[class=panel-heading]").next().find("div[class^='section-area']").children().each(function() {			
				$(this).css("float", "left");			
			});
		} else {
			$(setting).closest("div[class=panel-heading]").next().find("div[class^='section-area']").children().each(function() {			
				$(this).css("float", "right");			
			});
		}
		$(setting).closest("span").find("input[name=formDirection]").val(direction);
	}
	//save event
	saveEventArea(modal, setting);
	
	$(setting).closest("span").find("input[name=formElementHidden]").val(hiddens.substring(0, hiddens.length - 1));
	saveFormInfor(setting,$(modal).find("#modal-section-setting-form"));	
	$(modal).modal("hide");
}

function saveDialogActionAreaSetting() {	
	
	var modal = $("#modal-action-setting");
	if(!validateRequired(modal,"parameterAttribute",dbMsgSource['sc.screendesign.0110'],true)) {
		return;
	}
	if(!validateRequired(modal,"areaCode",dbMsgSource['sc.screendesign.0178'])) {
		return;
	}
	if(!validateAlphanumeric(modal,"areaCode",dbMsgSource['sc.screendesign.0178'])) {
		return;
	}
	
	var setting = $(modal).data('target');
	$(setting).next("input").val($(modal).find("[name=tableCaption]").val());
	
	if(isProhibitChar($(modal).find("[name=tableCaption]").val())){
		$(modal).find("#dialog-form-table-error").html("").append("<font color=\"red\">"+fcomMsgSource['err.sys.0033'].replace("{0}", dbMsgSource['sc.screendesign.0118']).replace("{1}", "' \"")+"</font>");
		return false;
	}
	
	var isError = false;
	$(modal).find("#tableDialogHiddenAttribute").find("tbody").find("tr").each(function (i){
		if($(this).find("input[name=parameterAttribute]").val() != undefined && $(this).find("input[name=parameterAttribute]").val().length > 0){
			
			if(isNotAlphanumeric($(this).find("input[name=parameterAttribute]").val())){
				$(modal).find("#dialog-form-table-error").html("").append("<font color=\"red\">"+fcomMsgSource['err.sys.0032'].replace("{0}", (i+1)).replace("{1}", dbMsgSource['sc.screendesign.0110'])+"</font>");
				isError = true;
				return false;
			}
		}
	});
	if(isError){
		return false;
	}
	//add more parameter
	var tableCaption  = "";
	if($(modal).find("input[name='tableCaption']").val() != undefined){
		tableCaption = $(modal).find("input[name='tableCaption']").val();
	}
	
	var tableCaptionText  = "";
	var tableCaptionTextElement = $(modal).find("input[name='tableCaptionAutocomplete']"); 
	if(tableCaptionTextElement.val() != undefined){
		tableCaptionText =tableCaptionTextElement.val();
	}
	
	var isBundle = "";
	if($(modal).find("input[name='tableCaptionAutocomplete']").attr("selectedvalue") != undefined){		
		isBundle = $(modal).find("input[name='tableCaptionAutocomplete']").attr("selectedvalue");
	}
	
	$(setting).closest("span").find("input[name=formAreaCaptionId]").val(tableCaption);
	$(setting).closest("span").find("input[name=formAreaCaptionText]").val(tableCaptionText);
	if($(modal).find("[name=areaCode]").val()  != undefined && $(modal).find("[name=areaCode]").val().length > 0){
		$(setting).closest("span").find("input[name=formAreaCode]").val($(modal).find("[name=areaCode]").val());
	}
	$(setting).closest("span").find("input[name=formAreaCaptionIsbundle]").val(isBundle);
	
	var table =  $(setting).closest("span").closest("div").next("div").find("table").first();
	
	var width = $(modal).find("[name=tableWidth]").val();
	if(width != undefined && width.length > 0 && width != "0"){
		width += $(modal).find("[name=tableWidthUnit]").val();
	} else {
		width = "100%"
	}
	$(setting).closest("span").find("input[name=formTableWidth]").val(width);
	$(setting).closest("span").closest("div[class*='areaContent']").css("width", width);
	
	if($(modal).find("[name=tablePosition]").val() != ""){
		$(setting).closest("span").find("input[name=formTablePosition]").val($(modal).find("[name=tablePosition]").val());
		if($(modal).find("[name=tablePosition]").val()=="3"){
			$(setting).closest("span").closest("div[class=areaContent]").css("float", "right");
		}else{
			$(setting).closest("span").closest("div[class=areaContent]").css("float", "left");
		}
	}
	
	if($(modal).find("[name=tableHeaderRow]").val() != "" && $(modal).find("[name=tableHeaderRow]").val() > 0 && $(modal).find("[name=tableHeaderRow]").val() < 6){
		var headerRow = $(modal).find("[name=tableHeaderRow]").val();
		var formElementTable = $(setting).closest("span").find("input[name=formElementTable]").val();
		var colSizeArr = formElementTable.split(",");
		var trLength = $(table).find("thead").find("tr").length;
		if(headerRow > trLength){
			for(i=trLength;i<headerRow;i++){
				var trHead = "<tr class=\"style-even-row\" index="+i+">";
				var trBody = "<tr class=\"style-odd-row\" index="+i+">";
				for(j=0;j<colSizeArr[0];j++){
					trHead += "<th class=\"style-header-table\" index=" + j + "><span style=\"float: left; cursor: move;\" class=\"ui-icon ui-icon-arrow-4\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></span><input type=\"hidden\" name=\"formElement\" /><div>&nbsp;</div></th>";
					trBody += "<td index=" + j + ">&nbsp;</td>";
				}
				trHead += "</tr>";
				trBody += "</tr>";
				
				$(table).find("thead").append(trHead);
				$(table).find("tbody").append(trBody);
			}
		} 
		if(headerRow < trLength){
			for(i=trLength;i>headerRow-1;i--){
				$(table).find("thead").find("tr:eq("+i+")").remove();
				$(table).find("tbody").find("tr:eq("+i+")").remove();
			}
			$(table).find("thead").find("tr").each(function(i){
				var trIndex = $(this).attr("index");
				$(this).find("th").each(function(j){
					if($(this).attr("rowspan") != undefined && $(this).attr("rowspan") > 1 
							&& $(this).attr("rowspan") > $(table).find("thead").find("tr").length - trIndex){
						var elementVal = $(this).find("input[name=formElement]").val();
						var elementValJSON = $.parseJSON("{"+elementVal+"}");
						$(this).attr("rowspan", $(table).find("thead").find("tr").length - trIndex);
						$(this).find("input[name=formElement]").val(elementVal.replace('"rowspan":"'+elementValJSON.rowspan+'"', '"rowspan":"'+($(table).find("thead").find("tr").length - trIndex)+'"'));
					}
				});
			});
			$(table).find("tbody").find("tr").each(function(i){
				var trIndex = $(this).attr("index");
				$(this).find("td").each(function(j){
					if($(this).attr("rowspan") != undefined && $(this).attr("rowspan") > 1 
							&& $(this).attr("rowspan") > $(table).find("tbody").find("tr").length - trIndex){
						$(this).attr("rowspan", $(table).find("tbody").find("tr").length - trIndex);
					}
				});
			});
		}
		$(setting).closest("span").find("input[name=formTableRow]").val(headerRow);
		
		$(table).find("div").droppable({
			accept: "#srcgenElements div, #srcgenAction div, #newDragElementTd div",
			activeClass: "state-droppable",
			drop: function(event, ui) {
				insertComponentList(event, ui, $(this));
			}
		 });
		 
		 var tableId = $(table).attr('id');
		 
		 $(table).find("span").draggable({
				containment: '#' + tableId,
				stack: '#' + tableId,
				revert: true,
				stop: function(event, ui) {
		    	  	$(this).css("z-index","auto");
		    	  } 
		 });
		
		 $(table).find("span").droppable({
			accept: "#" + tableId + " span",
			activeClass: "state-droppable",
			drop: function(event, ui) {
				if($(this).prev("input").length == 0){
					replaceElementList(event, ui, $(this));
				} else {
					replaceElement(event, ui, $(this));
				}
			}
		});
		
	}
	
	var formTableColumnSize = "";
	$(table).find("colgroup").empty();
	var colsWidth = "";
	
	$(modal).find("[name=columnnameSize]").each(function (i){
		if($(this).val().length > 0){
			colsWidth += "<col width=\"" + $(this).val() + $(this).closest("td").find("[name='columnnameSizeUnit']").eq(i).val() + "\">";
		} else {
			colsWidth += "<col width=\"\">";
		}
		if($(this).val().length == 0){
			formTableColumnSize += ",";
		} else {
			formTableColumnSize += $(this).val() + $(this).closest("td").find("[name='columnnameSizeUnit']").eq(i).val() + ",";
		}
	});
	$(table).find("colgroup").append(colsWidth);
	
	if(formTableColumnSize.length > 0){
		$(setting).closest("span").find("input[name=formTableColumnSize]").val(formTableColumnSize.substring(0, formTableColumnSize.length - 1));
	}
	
	var area = $(setting).closest('.areaContent');
	var codeExists = '';
	var isValid = true;
	$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr").find("input[name=parameterAttribute]").each(function (i){
		var curValue = $(this).val();
		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr").find("input[name=parameterAttribute]").each(function (j){
			if (j > i) {
				if (curValue == $(this).val()) {
					isValid = false;
					codeExists = curValue;
					return;
				}
			}
		});
	});
	
	if (!isValid) {
		alert(dbMsgSource['sc.screendesign.0295'].replace("{0}"),codeExists);
		return;
	}
	
	$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr").find("input[name=parameterAttribute]").each(function (){
		var curValue = $(this).val();
		$(area).find('input[name=formElement]').each(function(){
			var json = convertToJson($(this).val());
			if ((json.datatype != 20 && json.datatype != 13 && json.datatype != 11) && curValue == json.columnname) {
				isValid = false;
				codeExists = curValue;
				return;
			}
		});
	});
	
	if (!isValid) {
		alert(dbMsgSource['sc.screendesign.0296'].replace("{0}"),codeExists);
		return;
	}
	
	var hiddens = "";
	$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr").each(function (i){
		if($(this).find("input[name=parameterAttribute]").val() != undefined && $(this).find("input[name=parameterAttribute]").val().length > 0){
			
			hiddens += $(this).find("input[name=parameterAttribute]").val()
			+ "π" + $(this).find("input[name=labelName]").val()
			+ "π" + $(this).find("input[name=labelNameAutocomplete]").val()
			hiddens += "�";
		}
	});
		
	$(setting).closest("span").find("input[name=formElementHidden]").val(hiddens.substring(0, hiddens.length - 1));
	
	
	var displayType = $(modal).find("[name='modal-section-setting-display-type']").val();
	if(displayType != undefined)
		$(setting).closest("span").find("input[name=formDisplayType]").val(displayType);
	//float: left; width: 25%; padding: 2px; height: 30px;
	if (displayType != undefined && displayType == '0') {
		$(setting).closest("div[class=panel-heading]").next().find("div[class^='action-area']").children().each(function() {						
			$(this).css("padding", "2px");
			$(this).css("height", "30px");
			$(this).css("clear", "");
		});
	} else if (displayType == 1) {		
		$(setting).closest("div[class=panel-heading]").next().find("div[class^='action-area']").find("span").each(function() {
			$(this).css("clear", "both");
		});		
	}
	
	var direction = $(modal).find("[name='direction']").val();
	
	if (direction == 0) {
		$(setting).closest("div[class=panel-heading]").next().find("div[class^='action-area']").children().each(function() {			
			$(this).css("float", "left");
			$(this).css("padding-right", "4px");
		});
	} else {
		$(setting).closest("div[class=panel-heading]").next().find("div[class^='action-area']").children().each(function() {			
			$(this).css("float", "right");
			$(this).css("padding-left", "4px");
		});
	}
	
	$(setting).closest("span").find("input[name=formDirection]").val(direction);
	$(setting).closest("span").find("input[name=formDisplayType]").val(displayType);
	$(setting).closest("span").find("input[name=formElementHidden]").val(hiddens.substring(0, hiddens.length - 1));
	saveFormInfor(setting,$(modal).find("#modal-action-setting-form"));	
	$(modal).modal("hide");
}

//list table setting save
function modalTableListSettingSave() {	
	var modal = $("#modal-table-list-setting");
	if(!validateRequired(modal,"parameterAttribute",dbMsgSource['sc.screendesign.0110'],true)) {
		return;
	}
	if(!validateRequired(modal,"areaCode",dbMsgSource['sc.screendesign.0178'])) {
		return;
	}
	if(!validateAlphanumeric(modal,"areaCode",dbMsgSource['sc.screendesign.0178'])) {
		return;
	}
	
	var setting = $("#modal-table-list-setting").data('data');
	$(setting).next("input").val($("#modal-table-list-setting").find("[name=tableCaption]").val());
	
	if(isProhibitChar($("#modal-table-setting").find("[name=tableCaption]").val())){
		$(modal).find("#dialog-form-table-error").html("").append("<font color=\"red\">"+fcomMsgSource['err.sys.0033'].replace("{0}", dbMsgSource['sc.screendesign.0118']).replace("{1}", "' \"")+"</font>");
		return false;
	}
	
	var isError = false;
	$(modal).find("#tableDialogHiddenAttribute").find("tbody").find("tr").each(function (i){
		if($(this).find("input[name=parameterAttribute]").val() != undefined && $(this).find("input[name=parameterAttribute]").val().length > 0){
			
			if(isNotAlphanumeric($(this).find("input[name=parameterAttribute]").val())){
				$("#modal-table-setting").find("#dialog-form-table-error").html("").append("<font color=\"red\">"+fcomMsgSource['err.sys.0032'].replace("{0}", (i+1)).replace("{1}", dbMsgSource['sc.screendesign.0110'])+"</font>");
				isError = true;
				return false;
			}
		}
	});
	if(isError){
		return false;
	}
	//add more parameter
	var tableCaption  = "";
	if($(modal).find("input[name='tableCaption']").val() != undefined){
		tableCaption = $(modal).find("input[name='tableCaption']").val();
	}
	
	var tableCaptionText  = "";
	var tableCaptionTextElement = $(modal).find("input[name='tableCaptionAutocomplete']"); 
	if(tableCaptionTextElement.val() != undefined){
		tableCaptionText =tableCaptionTextElement.val();
	}
	$(setting).next().html(tableCaptionText);
	var isBundle = "";
	if($(modal).find("input[name='tableCaptionAutocomplete']").attr("selectedvalue") != undefined){		
		isBundle = $(modal).find("input[name='tableCaptionAutocomplete']").attr("selectedvalue");
	}
	
	if (isBundle == "true") {
		$(setting).closest("span").find("input[name=formAreaCaptionId]").val(tableCaption);
	} else {
		$(setting).closest("span").find("input[name=formAreaCaptionId]").val("");
	}
	
	$(setting).closest("span").find("input[name=formAreaCaptionText]").val(tableCaptionText);
	if($(modal).find("[name=areaCode]").val()  != undefined && $(modal).find("[name=areaCode]").val().length > 0){
		$(setting).closest("span").find("input[name=formAreaCode]").val($(modal).find("[name=areaCode]").val());
	}
	$(setting).closest("span").find("input[name=formAreaCaptionIsbundle]").val(isBundle);

	var table =  $(setting).closest("span").closest("div").next("div").find("table").first();
	var width = $(modal).find("[name=tableWidth]").val();
	if(width != undefined && width.length > 0 && width != "0"){
		width += $(modal).find("[name=tableWidthUnit]").val();
	} else {
		width = "100%"
	}
	$(setting).closest("span").find("input[name=formTableWidth]").val(width);
	$(setting).closest("span").closest("div[class=areaContent]").css("width", width);
	
	if($(modal).find("[name=tablePosition]").val() != ""){
		$(setting).closest("span").find("input[name=formTablePosition]").val($(modal).find("[name=tablePosition]").val());
		if($(modal).find("[name=tablePosition]").val()=="3"){
			$(setting).closest("span").closest("div[class=areaContent]").css("float", "right");
		}else{
			$(setting).closest("span").closest("div[class=areaContent]").css("float", "left");
		}
	}


	if($(modal).find("[name=headerComponentRow]").val() != "" && $(modal).find("[name=headerComponentRow]").val() > 0 && $(modal).find("[name=headerComponentRow]").val() < 6){
		var headerRow = $(modal).find("[name=headerComponentRow]").val();
		var formElementTable = $(setting).closest("span").find("input[name=formElementTable]").val();
		var colSizeArr = formElementTable.split(",");
		var trLength = $(table).find("tbody").find("tr").length;
		var columnSize = $(table).find("tr:last").find("td").length;
		
		if(headerRow > trLength && parseInt($(modal).find("[name=headerComponentRow]").val()) > 0){
			for(i=trLength;i<parseInt($(modal).find("[name=headerComponentRow]").val());i++){
				var trBody = "<tr class=\"style-odd-row\">";
				for(j=0;j<columnSize;j++){					
					trBody += "<td><span style=\"float: left; cursor: move;\" class=\"qp-glyphicon  glyphicon glyphicon-screenshot\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></span><input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"groupTotalElement\"  /><input type=\"hidden\" name=\"enableGroup\"  /><div class=\"dropComponent\"><input type=\"hidden\" name=\"formElement\"/>&nbsp;</div></td>";
				}				
				trBody += "</tr>";							
				$(table).find("tbody").append(trBody);
			}
		} 
				
		if(headerRow < trLength){
			for(i=trLength;i>headerRow-1;i--){
				$(table).find("thead").find("tr:eq("+i+")").remove();
				$(table).find("tbody").find("tr:eq("+i+")").remove();
			}
			$(table).find("tbody").find("tr").each(function(i){
				var trIndex = $(this).attr("index");
				$(this).find("td").each(function(j){
					if($(this).attr("rowspan") != undefined && $(this).attr("rowspan") > 1 
							&& $(this).attr("rowspan") > $(table).find("tbody").find("tr").length - trIndex){
						$(this).attr("rowspan", $(table).find("tbody").find("tr").length - trIndex);
					}
				});
			});
		}
		$(setting).closest("span").find("input[name=formHeaderComponentRow]").val(headerRow);
		
		$(table).find(".dropComponent").each(function (){
			
			$(this).droppable({
				accept: "#srcgenElements div[id!='divLabel'], #srcgenAction div, #newDragElementTd div",
				activeClass: "state-droppable",
				drop: function(event, ui) {
					insertComponent(event, ui, $(this));
				}
			});
		});
		
		 var tableId = $(table).attr('id');		 
		 $(table).find(".glyphicon-screenshot").draggable({
				containment: '#' + tableId,
				stack: '#' + tableId,
				revert: true,
				stop: function(event, ui) {
		    	  	$(this).css("z-index","auto");
		    	  } 
		 });
		
		 $(table).find("td .glyphicon-screenshot").droppable({
			accept: "#" + tableId + " td .glyphicon-screenshot",
			activeClass: "state-droppable",
			drop: function(event, ui) {
				if($(this).prev("input").length == 0){
					replaceElementList(event, ui, $(this));
				} else {
					replaceElement(event, ui, $(this));
				}
			}
		});
		 
		//default group
		$(table).find("tbody td").droppable({
			accept: "#srcgenElements div[id!='divLabel'], #srcgenAction div, #newDragElementTd div",
			activeClass: "state-droppable",
			drop: function(event, ui) {					
				insertComponent(event, ui, $(this));			
			}
		});
		$(table).find("tbody td").droppable( "option", "disabled", false );
		$(table).find("tbody td").find("input[name='enableGroup']").val(true);
		$(table).find("tbody td").find("input[name='groupDisplayType']").val(1);
		
	}
	
	if($(modal).find("[name=headerLabelRow]").val() != "" && $(modal).find("[name=headerLabelRow]").val() > 0 && $(modal).find("[name=headerLabelRow]").val() < 6){
		var headerRow = $(modal).find("[name=headerLabelRow]").val();
		var formElementTable = $(setting).closest("span").find("input[name=formElementTable]").val();
		var colSizeArr = formElementTable.split(",");
		var trLength = $(table).find("thead").find("tr").length;
		var columnSize = $(table).find("tr:last").find("td").length;
		
		var headerRow = $(modal).find("[name=headerLabelRow]").val();
		if(headerRow != '' && parseInt(headerRow) > trLength){
			for(i=trLength;i<parseInt(headerRow);i++){
				var trHead = "<tr>";				
				for(j=0;j<columnSize;j++){
					trHead += "<th><span style=\"float: left; cursor: move;\" class=\"qp-glyphicon  glyphicon glyphicon-screenshot\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></span>"+returnHiddenTHEmpty()+"<div class=\"dropLabel\">&nbsp;</div></th>";					
				}
				trHead += "</tr>";
				
				$(table).find("thead").append(trHead);				
			}
		}
		
		if(headerRow < trLength){
			for(i=trLength;i>headerRow-1;i--){
				$(table).find("thead").find("tr:eq("+i+")").remove();
				$(table).find("tbody").find("tr:eq("+i+")").remove();
			}
			$(table).find("thead").find("tr").each(function(i){
				var trIndex = $(this).attr("index");
				$(this).find("th").each(function(j){
					if($(this).attr("rowspan") != undefined && $(this).attr("rowspan") > 1 
							&& $(this).attr("rowspan") > $(table).find("thead").find("tr").length - trIndex){
						var elementVal = $(this).find("input[name=formElement]").val();
						var elementValJSON = $.parseJSON("{"+elementVal+"}");
						$(this).attr("rowspan", $(table).find("thead").find("tr").length - trIndex);
						$(this).find("input[name=formElement]").val(elementVal.replace('"rowspan":"'+elementValJSON.rowspan+'"', '"rowspan":"'+($(table).find("thead").find("tr").length - trIndex)+'"'));
					}
				});
			});			
		}
		$(setting).closest("span").find("input[name=formHeaderLabelRow]").val(headerRow);
		
		$(table).find(".dropLabel").each(function (){
			
			$(this).droppable({
				accept: "#divLabel",
				activeClass: "state-droppable",
				drop: function(event, ui) {										
					insertLable(event, ui, $(this));
				}
			});
		});
		 
		 var tableId = $(table).attr('id');
		 
		 $(table).find(".glyphicon-screenshot").draggable({
				containment: '#' + tableId,
				stack: '#' + tableId,
				revert: function(event, ui) {$(this).data("uiDraggable").originalPosition = {top : 0,left : 0};return true;},
				stop: function(event, ui) {
		    	  	$(this).css("z-index","auto");
		    	  } 
		 });
		
		 $(table).find("th .glyphicon-screenshot").droppable({
			accept: "#" + tableId + " th .glyphicon-screenshot",
			activeClass: "state-droppable",
			drop: function(event, ui) {
				if($(this).prev("input").length == 0){
					replaceElementList(event, ui, $(this));
				} else {
					replaceElement(event, ui, $(this));
				}
			}
		});
		
	}
	
	var formTableColumnSize = "";
	$(table).find("colgroup").empty();
	var colsWidth = "";
	
	var isSetWidthCol = false;
	$(modal).find("[name=columnnameSize]").each(function (i){
		if($(this).val().length > 0){
			colsWidth += "<col width=\"" + $(this).val() + $(this).closest("td").find("select[name='columnnameSizeUnit']").eq(i).val() + "\">";
		} else {
			colsWidth += "<col width=\"\">";
		}
		if($(this).val().length == 0){
			formTableColumnSize += ",";
		} else {
			formTableColumnSize += $(this).val() + $(this).closest("td").find("select[name='columnnameSizeUnit']").eq(i).val() + ",";
		}
	});
	$(table).find("colgroup").append(colsWidth);
	
	if(formTableColumnSize.length > 0){
		$(setting).closest("span").find("input[name=formTableColumnSize]").val(formTableColumnSize.substring(0, formTableColumnSize.length - 1));
	}
		
	reIndexColTableList($(table));
	var hiddens = "";
	$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr").each(function (i){
		if($(this).find("input[name=parameterAttribute]").val() != undefined && $(this).find("input[name=parameterAttribute]").val().length > 0){
			var tableCode = "";
			if($(this).find("input[name=tableCode]").val() != undefined && $(this).find("input[name=tableCode]").val().length > 0){
				tableCode = $(this).find("input[name=tableCode]").val();
			}
			var tableCodeAutocomplete = "";
			if($(this).find("input[name=tableCodeAutocomplete]").val() != undefined && $(this).find("input[name=tableCodeAutocomplete]").val().length > 0){
				tableCodeAutocomplete = $(this).find("input[name=tableCodeAutocomplete]").val();
			}
			var columnCode = "";
			if($(this).find("input[name=columnCode]").val() != undefined && $(this).find("input[name=columnCode]").val().length > 0){
				columnCode = $(this).find("input[name=columnCode]").val();
			}
			var columnCodeAutocomplete = "";
			if($(this).find("input[name=columnCodeAutocomplete]").val() != undefined && $(this).find("input[name=columnCodeAutocomplete]").val().length > 0){
				columnCodeAutocomplete = $(this).find("input[name=columnCodeAutocomplete]").val();
			}
			
			hiddens += $(this).find("input[name=parameterAttribute]").val()
			+ "π" + tableCode
			+ "π" + tableCodeAutocomplete
			+ "π" + columnCode
			+ "π" + columnCodeAutocomplete;
			hiddens += "�";
		}
	});
	$(setting).closest("span").find("input[name=formElementHidden]").val(hiddens.substring(0, hiddens.length - 1));
	
	//save event
	saveEventArea(modal, setting);
	//save form 
	saveFormInfor(setting,$(modal).find("#modal-table-list-setting-form"));
	$(modal).modal("hide");
}

function removeOptionJS(obj){
	if($(obj).closest("table").find("tbody").find("tr").length == 1){
		alert(dbMsgSource['sc.screendesign.0123']);
	} else {
		$(obj).closest("tr").remove();
		$.qp.reCalIndex("tableDialogFormOptions"); // refresh row index (No.)
		$.qp.alternateRowColorInTable("tableDialogFormOptions"); // alternate color
	}
}

function addOptionJS(obj){
	var dataType = $("#tableDialogFormOptions").attr('dataType');
	var content = "";
		content += "<tr><td class=\"com-output-fixlength tableIndex\"> </td><td class=\"com-output-fixlength\" ";
	if($("#dialog-form").find("input[name=supportOptionValue]").prop('checked') == false){
		content += "style=\"display: none;\"";
	}
		content += "><input type=\"text\" class=\"form-control qp-input-text\" name=\"srcgenOptionName\" maxlength=\"200\"/></td><td class=\"com-output-fixlength\"><input type=\"text\" ";
	if(dataType == 2){
		content +=	"class=\"com-input-integer\" ";
	} else {
		content +=	"class=\"form-control qp-input-text\" ";
	}
		content += "name=\"srcgenOptionValue\" maxlength=\"200\"/></td>"+
			"<td class=\"com-output-fixlength\"><a href=\"javascript:\" style=\"margin-top: 3px;\" onclick=\"removeOptionJS(this);\" class=\"ui-icon ui-icon-minus\" title=\""+dbMsgSource['sc.screendesign.0139']+"\"></a></td>"+
			"</tr>";
	$("#tableDialogFormOptions").append(content);
	$.qp.reCalIndex("tableDialogFormOptions"); // refresh row index (No.)
	$.qp.alternateRowColorInTable("tableDialogFormOptions"); // alternate color
	$.qp.initialAutoNumeric($("#tableDialogFormOptions"));
}

function removeParameterJS(obj){
	var table = $(obj).closest("table");
	var tableId = $(obj).closest("table").attr("id");
	$(obj).closest("tr").remove();
	if($(table).find("tbody tr").length == 0){
		addParameterJS($(table).next("a"));
	}
	$.qp.reCalIndex(tableId); // refresh row index (No.)
	$.qp.alternateRowColorInTable(tableId); // alternate color
}

function addParameterJS(obj){
	var table = $(obj).prev("table");
	var tableId = $(table).attr("id");
	var content = "<tr><td class=\"com-output-fixlength tableIndex\"> </td>" + 
				"<td><input type=\"text\" class=\"form-control qp-input-text-detail\" name=\"parameterAttribute\" maxlength=\"200\"/></td>" +
				"<td><input type=\"text\" displaySource=\"outputName\" submitSource=\"outputId\" displayElName=\"dbTablecodeAutocomplete\"" +
				" submitElName=\"dbTablecode\" sourceType=\"srcgenDynamicTableAtc.listTableCode\" onselectAutocomplete=\"setArgToListColumn\" class=\"com-input-autocomplete-detail\" name=\"dbTablecodeAutocomplete\" maxlength=\"200\"/>" +
				"<input type=\"hidden\" name=\"dbTablecode\"/></td>"+
				"<td><input type=\"text\" displaySource=\"outputName\" submitSource=\"outputId\" displayElName=\"dbColumcodeAutocomplete\"" +
				" submitElName=\"dbColumcode\" sourceType=\"srcgenDynamicTableAtc.listColumnCode\" class=\"com-input-autocomplete-detail\" name=\"dbColumcodeAutocomplete\" maxlength=\"200\"/>" +
				"<input type=\"hidden\" name=\"dbColumcode\"/></td>"+
				"<td class=\"com-output-fixlength\"><a href=\"javascript:\" style=\"margin-top: 3px;\" onclick=\"removeParameterJS(this);\" class=\"ui-icon ui-icon-minus\" title=\""+dbMsgSource['sc.screendesign.0139']+"\"></a></td>"+
			"</tr>";
	$(table).find("tbody").append(content);
	$.qp.reCalIndex(tableId); // refresh row index (No.)
	$.qp.alternateRowColorInTable(tableId); // alternate color
	$.qp.initialAutocomplete($(table).find("tbody tr:last").find("input[name=dbTablecodeAutocomplete]"));
	$.qp.initialAutocomplete($(table).find("tbody tr:last").find("input[name=dbColumcodeAutocomplete]"));
	//$.qp.initialAutoNumeric($("#"+tableId));
}


function clickDeleteButton(obj, screenId){
	$.qp.showConfirm(obj, screenId);
}
function clickSaveButton(obj, saveMode){
	
	var errorMsg = "";
	
	var duplicateElement = "";
	
	var arrError = [];
	$(".form-area-content").find("table").each(function (i){
		var table = $(this);
		$(table).find("td input[name=formElement]").each(function(i){
			var json = $(this).val();
			var inputJSON = convertToJson($(this).val());
			var elementName = inputJSON.columnname;
			var count = 0;
			
			if (arrError.length > 0 && arrError.indexOf(i) != -1) {
				return;
			}
			
			var color = getColor(i);
			
			for (var j = i + 1; j < $(table).find("td input[name=formElement]").length; j++) {
				var json = $(table).find("td input[name=formElement]").eq(j).val();
				var inputJSONNext = convertToJson($(table).find("td input[name=formElement]").eq(j).val());
				var elementNameNext = inputJSONNext.columnname;
				var parent = $(table).find("td input[name=formElement]").eq(j).closest("td, th");
				if(elementName == elementNameNext && elementNameNext != "" && elementName != undefined && parent.prop("tagName") == "TD" && $(parent).is(":visible")){
					if(inputJSON.datatype != 11 && inputJSON.datatype != 13 && inputJSONNext.datatype != 11 && inputJSONNext.datatype != 13){
						count += 1;
						arrError.push(j)
						$(table).find("td input[name=formElement]").eq(j).closest("span").css("border", "1px solid " + color);
						if ($(table).find("td input[name=formElement]").eq(j).closest("span").css("float") == undefined 
								|| $(table).find("td input[name=formElement]").eq(j).closest("span").css("float") == "" 
									|| $(table).find("td input[name=formElement]").eq(j).closest("span").css("float") == "none") {
							$(table).find("td input[name=formElement]").eq(j).closest("span").css("float", "left");
						}
						
						if ($(table).find("td input[name=formElement]").eq(j).closest("span").attr("style") != undefined 
								&& $(table).find("td input[name=formElement]").eq(j).closest("span").attr("style").indexOf("width:") == -1 ) {
							$(table).find("td input[name=formElement]").eq(j).closest("span").css("width", "100%");
						}
					}
				}
			}
		
			if(count > 0){
				duplicateElement += inputJSON.columnname + ",";
				$(this).closest("span").css("border", "1px solid " + color);
				if ($(this).closest("span").css("float") == undefined || $(this).closest("span").css("float") == "" || $(this).closest("span").css("float") == "none") {
					$(this).closest("span").css("float", "left");
				}
				
				if ($(this).closest("span").attr("style") != undefined && $(this).closest("span").attr("style").indexOf("width:") == -1 ) {
					$(this).closest("span").css("width", "100%");
				}
				
			} else {
				$(this).closest("span").css("border", "");
			}
		});
	});
	
	if(duplicateElement != ""){
		var msg = dbMsgSource['sc.screendesign.0124'] + "\n";
		var duplicateElementArr = duplicateElement.substring(0,duplicateElement.length - 1).split(",");
		for(i=0;i<duplicateElementArr.length;i++){
			msg += "	"+duplicateElementArr[i] + "\n";
		}
		errorMsg += msg;
	}
	
	if(errorMsg != ""){
		alert(errorMsg);

		$(obj).unbind();
		return false;
	} else {
		
		$.qp.showConfirm(obj);
		
		$(".form-area-content").find("table").each(function (i){ 
			$(this).find("td:not(td[class*='srcgenControl'])").each(function(){
				var totalElement = 0;
				$(this).children().each(function(){
					if ($(this).prop("tagName") == "DIV" || $(this).prop("tagName") == "SPAN" && !$(this).is(".glyphicon-screenshot")) {
						totalElement++;
					}
				});
				$(this).find("input[name=groupTotalElement]").val(totalElement);
			});
			var divParent = $(this).closest("div[class*=panel-body]").prev();
			if($(this).attr("class") == "table table-bordered qp-table-form"){
				var tds = $(this).find("input[name=formElement]").length;
				var cols = ($(this).find("tr:first").find("td").length - 2) * 2;
				
				$(divParent).find("input[name=formElementTable]").val(cols+","+tds);
				
				var totalGroup = $(this).find("input[name=groupTotalElement]").length;
				$(divParent).find("input[name=formTotalGroup]").val(totalGroup);
				// set width
				if($(divParent).find("input[name=formTableColumnSize]").val() == undefined || $(divParent).find("input[name=formTableColumnSize]").val().length == 0){
					var width = "";
					for(y=0;y<$(this).find("tr:eq(1)").children().length - 2; y++){
						if($(this).find("tr:eq(1)").children().eq(y).attr("width") != undefined){
							var widthUnit = $(this).find("tr:eq(1)").children().eq(y).attr("width");
							if(widthUnit == ""){
								widthUnit = parseInt(100/(cols*2)) + "%";
							}
							width += widthUnit;
						}
						width += ",";
					}
					$(divParent).find("input[name=formTableColumnSize]").val(width.substring(0,width.length - 1));
				}
			} else {
				var tds = $(this).find("input[name=formElement]").length;
				var cols = $(this).find("tr:last").find("td").length;
				$(divParent).find("input[name=formElementTable]").val(cols+","+tds);
				
				var totalGroup = $(this).find("input[name=groupTotalElement]").length;
				$(divParent).find("input[name=formTotalGroup]").val(totalGroup);
				
				// set width
				if($(divParent).find("input[name=formTableColumnSize]").val() == undefined || $(divParent).find("input[name=formTableColumnSize]").val().length == 0){
					var width = "";
					for(y=0;y<$(this).find("tr:eq(0)").find("th").length; y++){
						if($(this).find("tr:eq(0)").find("th:eq("+y+")").attr("width") != undefined){
							var widthUnit = $(this).find("tr:eq(0)").find("th:eq("+y+")").attr("width");
							if(widthUnit == ""){
								widthUnit = parseInt(100/cols) + "%";
							}
							width += widthUnit;
						}
						width += ",";
					}
					$(divParent).find("input[name=formTableColumnSize]").val(width.substring(0,width.length - 1));
				}
			}
		});
		$(".form-area-content").find(".section-area,.action-area").each(function (i){
			var cols = $(this).find("input[name=formElement]").length;
			$(this).closest(".areaContent").find("input[name=formElementTable]").val(cols+","+cols);
		});
		
		var headerLinkLength = $("#srcgenHeaderLinkPanel").find("input[name=formElement]").length;
		$("#srcgenHeaderLinkPanel").find("input[name=formElementTable]").val("0," + headerLinkLength);
		$("#srcgenHeaderLinkPanel").find("input[name=formTotalGroup]").val("0");
		
		//check status
		
		switch(saveMode) {
			case 'prototypeGen':
				$(obj).closest('#screenDesignForm').find('input[name=designMode]').val(2);
				break;
			case 'prototypeSave':
				break;
			case 'businessGen':
				break;
			case 'businessSave':
				break;
		}
		
	}
}
function saveDialogButtonAreaSetting(obj,modalName) {
	var modal = $("#dialog-button-area-setting");
	
	if (modalName != null && modalName == 'dialog-button-area-setting-section') {
		modal = $("#dialog-button-area-setting-section");
	}
	if(!validateRequired(modal,"buttonLabelNameAutocomplete",dbMsgSource['sc.screendesign.0082'])) {
		return;
	}
	
	if ($(modal).find("input[name=actiontype]:checked").val() == 0) {
		if(!validateRequired($(modal).find("#navigateToScreen"),"navigateToAutocomplete",dbMsgSource['sc.screendesign.0068'])) {
			return;
		}
	} else {
		if(!validateRequired($(modal).find("#navigateToblogic"),"navigateToAutocomplete",dbMsgSource['sc.screendesign.0068'])) {
			return;
		}
	}
	
	
	$.qp.undoFormatNumericForm(modal);
	obj = $(modal).data("target");
	data = convertToJson($(modal).data("data"));
	var colSpanOld = data.colspan;
	var rowSpanOld = data.rowspan;
	
	var buttonLabel  = "";
	if($(modal).find("input[name='buttonLabelName']").val() != undefined){
		buttonLabel = $(modal).find("input[name='buttonLabelName']").val();
	}
	
	var buttonLabelText  = "";
	var buttonLablelTextElement = $(modal).find("input[name='buttonLabelNameAutocomplete']"); 
	if(buttonLablelTextElement.val() != undefined){
		buttonLabelText =buttonLablelTextElement.val();
	}
	
	var isBundle = "";
	if($(modal).find("input[name='buttonLabelNameAutocomplete']").attr("selectedvalue") != undefined){		
		isBundle = $(modal).find("input[name='buttonLabelNameAutocomplete']").attr("selectedvalue");
	}
	
	var navigateTo  = "";	
	var navigateToText  = "";

	if ($(modal).find("input[name=actiontype]:checked").val() == 0) {
		
		if($(modal).find("#navigateToScreen").find("input[name='navigateTo']").val() != undefined){
			navigateTo = $(modal).find("#navigateToScreen").find("input[name='navigateTo']").val();
		}
		
		if($(modal).find("#navigateToScreen").find("input[name='navigateToAutocomplete']").val() != undefined){
			navigateToText = $(modal).find("#navigateToScreen").find("input[name='navigateToAutocomplete']").val();
		}	
	} else if ($(modal).find("input[name=actiontype]:checked").val() == 1) {
		
		if($(modal).find("#navigateToblogic").find("input[name='navigateTo']").val() != undefined){
			navigateTo = $(modal).find("#navigateToblogic").find("input[name='navigateTo']").val();
		}
		
		if($(modal).find("#navigateToblogic").find("input[name='navigateToAutocomplete']").val() != undefined){
			navigateToText = $(modal).find("#navigateToblogic").find("input[name='navigateToAutocomplete']").val();
		}
	}
	
	var isSubmit  = "";
	if($(modal).find("input[name='isSubmit']") != undefined){
		isSubmit = $(modal).find("input[name='isSubmit']").prop("checked");
	}	
	if(!isSubmit) {
		if(!validateRequired(modal,"parameterAttribute",dbMsgSource['sc.screendesign.0071'],true)) {
			return;
		}
	}
	var actionName  = "";
	if($(modal).find("input[name='actionName']").val() != undefined){
		actionName = $(modal).find("input[name='actionName']").val();
	}	
	
	var rowspan = "";
	if($(modal).find("input[name='rowspan']").val() != undefined){
		rowspan = $(modal).find("input[name='rowspan']").val();
	}
	
	var colspan = "";
	if($(modal).find("input[name='colspan']").val() != undefined){
		colspan = $(modal).find("input[name='colspan']").val();
	}
	
	var actiontype = "";
	if($(modal).find("input[name='actiontype']:checked").val() != undefined){
		actiontype = $(modal).find("input[name='actiontype']:checked").val();
	}
	
	if (parseInt(colspan) < 0) {
		alert(dbMsgSource['sc.screendesign.0283']);
		return;
	}
	
	if (parseInt(rowspan) < 0) {
		alert(dbMsgSource['sc.screendesign.0284']);
		return;
	}
	
	if (data.actiontype != undefined) {
		data.actiontype = actiontype;
	} else {		
		data["actiontype"] = actiontype;
	}
	
	var result = saveActionParameter($(modal), data);
	
	if (!result) {
		return;
	}
	data = result;

	if (data.label != undefined) {
		data.label = buttonLabel;
	} else {
		data["label"] = buttonLabel;
	}
	
	if (data.labelText != undefined) {
		data.labelText = buttonLabelText;
	} else {
		data["labelText"] = buttonLabelText;
	}
	
	if (data.isBundle != undefined) {
		data.isBundle = isBundle;
	} else {		
		data["isBundle"] = isBundle;
	}
	
	if (isBundle == "false" || isBundle == "") {
		data.label = "";
	}
	
	if (data.navigateTo != undefined) {
		data.navigateTo = navigateTo;
	} else {
		data["navigateTo"] = navigateTo;
	}
	if (data.navigateToText != undefined) {
		data.navigateToText = navigateToText;
	} else {
		data["navigateToText"] = navigateToText;
	}
	
	if (data.isSubmit != undefined) {
		data.isSubmit = isSubmit.toString();
	} else {
		data["isSubmit"] = isSubmit.toString();
	}
	
	if (data.actionName != undefined) {
		data.actionName = actionName;
	} else {
		data["actionName"] = actionName;
	}
	
	var enableGroup = "";
	if( $(modal).find("input[name='enableGroup']") != undefined){
		enableGroup =  $(modal).find("input[name='enableGroup']").prop("checked");
	}
	if (data.enablegroup != undefined) {
		data.enablegroup = enableGroup;
	} else {		
		data["enablegroup"] = enableGroup;
	}
	
	var groupDisplayType = $(modal).find("select[name='dialog-button-area-setting-group-display-type']").val();
	if(groupDisplayType != undefined){
		$(obj).closest("td").find("input[name='groupDisplayType']").val(groupDisplayType);	
		
		if (enableGroup) {
			
			$(obj).closest("td").droppable({
				accept: "#srcgenElements div[id!='divLabel'], #srcgenAction div, #newDragElementTd div",
				activeClass: "state-droppable",
				drop: function(event, ui) {					
					insertComponent(event, ui, $(this));			
				}
			});
			$(obj).closest("td").droppable( "option", "disabled", false );
			$(obj).closest("td").find("input[name='enableGroup']").val(true);
			
			//if inline
			if (groupDisplayType == 1) {
				$(obj).closest("td").children().css("float", "left");
				$(obj).closest("td").children().css("clear", "");
			} else if (groupDisplayType == 2) {			
				$(obj).closest("td").children().css("clear", "both");
			}
		} else {
			if ($(obj).closest("td").find("input[name=formElement]").length == 1) {
				if ($(obj).closest("td").is(".ui-droppable")) {
					$(obj).closest("td").droppable( "option", "disabled", true );
				}
				$(obj).closest("td").find("input[name='enableGroup']").val(false);
			}
		}
		$(obj).closest("td").children().css("float", "left");
		
		if (data.groupDisplayType != undefined) {
			data.groupDisplayType = groupDisplayType;
		} else {		
			data["groupDisplayType"] = groupDisplayType;
		}
	}
	if (mergeTable(obj, data, rowspan, colspan)) {
		if (data.rowspan != undefined) {
			data.rowspan = rowspan;
		} else {		
			data["rowspan"] = rowspan;
		}
		
		if (data.colspan != undefined) {
			data.colspan = colspan;
		} else {		
			data["colspan"] = colspan;
		}
		var value =  convertToString(data);		
		if (data.datatype == 21) {
			$(obj).closest("span").find("label").html(data.columnname);
		}
		$(obj).closest("span").find("input[name='formElement']").val(value);
		$(modal).modal("hide");
		$(obj).val(buttonLabelText);
	}
}
function deleteDialogButtonAreaSetting(obj, modalName) {
	var modal = $("#dialog-button-area-setting");
	
	if (modalName != null && modalName == 'dialog-button-area-setting-section') {
		modal = $("#dialog-button-area-setting-section");
	}
	
	var obj = $(modal).data("target");
	var data = convertToJson($(modal).data("data"));
	
	//delete element
	deleteElement(obj, data);
	$(modal).modal("hide");
}
function saveDialogLinkAreaSetting(obj, modalName){
	var modal = $("#dialog-link-area-setting");
	if (modalName != undefined && modalName != null) {
		modal = $("#"+modalName);
		
	}
	if(!validateRequired(modal,"linkLabelNameAutocomplete",dbMsgSource['sc.screendesign.0067'])) {
		return;
	}
	
	if ($(modal).find("input[name=actiontype]:checked").val() == 0) {
		if(!validateRequired($(modal).find("#navigateToScreen"),"navigateToAutocomplete",dbMsgSource['sc.screendesign.0068'])) {
			return;
		}
	} else {
		if(!validateRequired($(modal).find("#navigateToblogic"),"navigateToAutocomplete",dbMsgSource['sc.screendesign.0068'])) {
			return;
		}
	}
	
	if(!validateRequired(modal,"parameterAttribute",dbMsgSource['sc.screendesign.0071'],true)) {
		return;
	}
	$.qp.undoFormatNumericForm(modal);
	obj = $(modal).data("target");
	data = convertToJson($(modal).data("data"));
	var colSpanOld = data.colspan;
	var rowSpanOld = data.rowspan;
	
	var linkLabel  = "";
	if($(modal).find("input[name='linkLabelName']").val() != undefined){
		linkLabel = $(modal).find("input[name='linkLabelName']").val();
	}
	
	var linkLabelText  = "";
	var linkLabelTextElement = $(modal).find("input[name='linkLabelNameAutocomplete']"); 
	if(linkLabelTextElement.val() != undefined){
		linkLabelText =linkLabelTextElement.val();
	}
	
	var isBundle = "";
	if($(modal).find("input[name='linkLabelNameAutocomplete']").attr("selectedvalue") != undefined){		
		isBundle = $(modal).find("input[name='linkLabelNameAutocomplete']").attr("selectedvalue");
	}
	
	var navigateTo  = "";	
	var navigateToText  = "";

	if ($(modal).find("input[name=actiontype]:checked").val() == 0) {
		
		if($(modal).find("#navigateToScreen").find("input[name='navigateTo']").val() != undefined){
			navigateTo = $(modal).find("#navigateToScreen").find("input[name='navigateTo']").val();
		}
		
		if($(modal).find("#navigateToScreen").find("input[name='navigateToAutocomplete']").val() != undefined){
			navigateToText = $(modal).find("#navigateToScreen").find("input[name='navigateToAutocomplete']").val();
		}	
	} else if ($(modal).find("input[name=actiontype]:checked").val() == 1) {
		
		if($(modal).find("#navigateToblogic").find("input[name='navigateTo']").val() != undefined){
			navigateTo = $(modal).find("#navigateToblogic").find("input[name='navigateTo']").val();
		}
		
		if($(modal).find("#navigateToblogic").find("input[name='navigateToAutocomplete']").val() != undefined){
			navigateToText = $(modal).find("#navigateToblogic").find("input[name='navigateToAutocomplete']").val();
		}
	}
	
	var actionName  = "";
	if($(modal).find("input[name='actionName']").val() != undefined){
		actionName = $(modal).find("input[name='actionName']").val();
	}
	
	var rowspan = "";
	if($(modal).find("input[name='rowspan']").val() != undefined){
		rowspan = $(modal).find("input[name='rowspan']").val();
	}
	
	var colspan = "";
	if($(modal).find("input[name='colspan']").val() != undefined){
		colspan = $(modal).find("input[name='colspan']").val();
	}
	
	if (parseInt(colspan) < 0) {
		alert(dbMsgSource['sc.screendesign.0283']);
		return;
	}
	
	if (parseInt(rowspan) < 0) {
		alert(dbMsgSource['sc.screendesign.0284']);
		return;
	}
	
	var actiontype = "";
	if($(modal).find("input[name='actiontype']:checked").val() != undefined){
		actiontype = $(modal).find("input[name='actiontype']:checked").val();
	}
	
	if (data.actiontype != undefined) {
		data.actiontype = actiontype;
	} else {		
		data["actiontype"] = actiontype;
	}
	
	var result = saveActionParameter($(modal), data);
	
	if (!result) {
		return;
	}
	data = result;
	
	if (data.label != undefined) {
		data.label = linkLabel;
	} else {
		data["label"] = linkLabel;
	}
	
	if (data.labelText != undefined) {
		data.labelText = linkLabelText;
	} else {
		data["labelText"] = linkLabelText;
	}
	
	if (data.isBundle != undefined) {
		data.isBundle = isBundle;
	} else {		
		data["isBundle"] = isBundle;
	}
	
	if (data.navigateTo != undefined) {
		data.navigateTo = navigateTo;
	} else {
		data["navigateTo"] = navigateTo;
	}
	if (data.navigateToText != undefined) {
		data.navigateToText = navigateToText;
	} else {
		data["navigateToText"] = navigateToText;
	}
	
	if (data.actionName != undefined) {
		data.actionName = actionName;
	} else {
		data["actionName"] = actionName;
	}
	
	if (data.isSubmit != undefined) {
		data.isSubmit = 1;
	} else {
		data["isSubmit"] = 1;
	}
	
	var enableGroup = "";
	if( $(modal).find("input[name='enableGroup']") != undefined){
		enableGroup =  $(modal).find("input[name='enableGroup']").prop("checked");
	}
	if (data.enablegroup != undefined) {
		data.enablegroup = enableGroup;
	} else {		
		data["enablegroup"] = enableGroup;
	}
	
	if (isBundle == "false" ||isBundle == "") {
		data.label = "";
	}
	
	var groupDisplayType = $(modal).find("select[name='dialog-link-area-setting-group-display-type']").val();
	if(groupDisplayType != undefined){
		$(obj).closest("td").find("input[name='groupDisplayType']").val(groupDisplayType);	
		
		if (enableGroup) {
			
			$(obj).closest("td").droppable({
				accept: "#srcgenElements div[id!='divLabel'], #srcgenAction div, #newDragElementTd div",
				activeClass: "state-droppable",
				drop: function(event, ui) {					
					insertComponent(event, ui, $(this));			
				}
			});
			$(obj).closest("td").droppable( "option", "disabled", false );
			$(obj).closest("td").find("input[name='enableGroup']").val(true);
			
			//if inline
			if (groupDisplayType == 1) {
				$(obj).closest("td").children().css("float", "left");
				$(obj).closest("td").children().css("clear", "");
			} else if (groupDisplayType == 2) {			
				$(obj).closest("td").children().css("clear", "both");
			}
		} else {
			if ($(obj).closest("td").find("input[name=formElement]").length == 1) {
				if ($(obj).closest("td").is(".ui-droppable")) {
					$(obj).closest("td").droppable( "option", "disabled", true );
				}
				$(obj).closest("td").find("input[name='enableGroup']").val(false);
			}
		}
		$(obj).closest("td").children().css("float", "left");
		
		if (data.groupDisplayType != undefined) {
			data.groupDisplayType = groupDisplayType;
		} else {		
			data["groupDisplayType"] = groupDisplayType;
		}
	}
	if (mergeTable(obj, data, rowspan, colspan)) {
		if (data.rowspan != undefined) {
			data.rowspan = rowspan;
		} else {		
			data["rowspan"] = rowspan;
		}
		
		if (data.colspan != undefined) {
			data.colspan = colspan;
		} else {		
			data["colspan"] = colspan;
		}
		var value =  convertToString(data);		
		if (data.datatype == 21) {
			$(obj).closest("span").find("label").html(data.columnname);
		}
		$(obj).closest("span").find("input[name='formElement']").val(value);
		$(modal).modal("hide");
		$(obj).html(linkLabelText);
	}
}
function deleteDialogLinkAreaSetting(obj, modalName){
	var modal = $("#dialog-link-area-setting");
	if (modalName != null && modalName == 'dialog-link-area-setting-section') {
		modal = $("#dialog-link-area-setting-section");
	}
	
	var obj = $(modal).data("target");
	var data = convertToJson($(modal).data("data"));
	
	//delete element
	deleteElement(obj, data);
	$(modal).modal("hide");
}

/**
 * get all column for table
 * @param obj
 * @author dungnn1
 */
function changeTableAC(obj){
	var value = $(obj.target).next("input[type=hidden]").val();
	var tableColumn = $('#tableColumnMappingAutocompleteId');
	var nextHidden = tableColumn.next("input[type=hidden]");
	tableColumn.val("");
	tableColumn.attr("arg01",value);
	nextHidden.val("");
	tableColumn.data("ui-autocomplete")._trigger("change");
};

function enableCodelistCustom(thiz, modalName) {
	if (thiz.checked) {
		$(thiz).closest("#" + modalName).find("#customCodeList").hide();
		$.qp.enableAutocomplete($(thiz).closest("#" + modalName).find("input[name=codelistCode]"));
	} else {
		$(thiz).closest("#" + modalName).find("#customCodeList").show();
		$.qp.disableAutocomplete($(thiz).closest("#" + modalName).find("input[name=codelistCode]"));
	}
}

function selectColumn(data) {
	var tableId = $("#tabs-column").find("input[name=tableMapping]").val();
	var columnId =  $("#tabs-column").find("input[name=tableColumnMapping]").val();
	
	var url = CONTEXT_PATH + "/screendesign/getColumns?tableId="+tableId+ "&columnId=" + columnId +"&r="+Math.random();	
	
	var data = $.qp.getData(url);
	if (data != null && data.length > 0) {
		
		$("#newDragElement").find("font").attr("color", "");
		$("#newDragElementTd").find("div").draggable( {
			disabled: false,
			containment: '#allDragDropContent',
			stack: '#allDragDropContent table',
		      helper: 'clone',
		      revert: "invalid",
		      stop: function(event, ui) {
		    	  	$(this).css("z-index","auto");
		    	  }, 
		      zIndex: 99999999,
		} );
		
		var obj = data[0];
		
		var div = $("#newDragElementTd").find("div");
		
		var value = $("#srcgenComponentDiv").find("input[name=radioDataType]:checked").val();
		if (value == 1) {
			$(div).attr('datatype', 21);
			$(div).attr('label', "Text");
		}
		else if (value == 2) {
			$(div).attr('datatype', 11);
			$(div).attr('label', "Link");
			$(div).attr('labelText', "Link");
		} else {
			$(div).attr('datatype', obj.datatype);
		}
		
		$(div).attr('datatype-backup', obj.datatype);
		$(div).attr('tablecode', obj.tablecode);
		$(div).attr('tablecolumncode', obj.tablecolumncode);
		$(div).attr('physicaldatatype', obj.physicaldatatype);
		$(div).attr('columnname', obj.columnname);
		$(div).attr('minvalue', obj.minvalue); 
		$(div).attr('maxvalue', obj.maxvalue); 
		$(div).attr('formatcode', obj.formatcode);
		$(div).attr('physicalmaxlength', obj.physicalmaxlength);
		$(div).attr('maxlength', obj.maxlength); 
		$(div).attr('require', obj.require); 
		$(div).attr('rowspan', obj.rowspan); 
		$(div).attr('colspan', obj.colspan);
		$(div).attr('datasource', obj.datasource);
		$(div).attr('msglabel', obj.msglabel);
		$(div).attr('msgvalue', obj.msglabel);
		$(div).attr('parameters', obj.msglabel);
		
		
		$(div).attr('codelistCode', obj.codelistCode);
		$(div).attr('codelistText', obj.codelistText);
		$(div).attr('localCodelist', obj.localCodelist);
		$(div).attr('datasourcetype', obj.datasourcetype);
		$(div).attr('sqldesignid', obj.sqldesignid);
		$(div).attr('sqldesignidtext', obj.sqldesignidtext);
		$(div).attr('optionlabel', obj.optionlabel);
		$(div).attr('optionlabeltext', obj.optionlabeltext);
		$(div).attr('optionvalue', obj.optionvalue);
		$(div).attr('optionvaluetext', obj.optionvaluetext);
		$(div).attr('optionvaluetext', obj.optionvaluetext);
		$(div).attr('elementtype', 0);
		
		$(div).attr('isBlank', "false");
		$(div).attr('isBundle', "false");
		$(div).attr('labelText', obj.labelText);
		
		var sJson = convertToString(obj);
		$(div).attr("data", sJson);
		
		$(".dropComponent").droppable({
			accept: "#srcgenElements div[id!='divLabel'], #srcgenAction div, #newDragElementTd div",
			activeClass: "state-droppable",
			drop: function(event, ui) {
				insertComponent(event, ui, $(this));
			}
		});
		
		$(".dropLabel").droppable({
			accept: "#divLabel",
			activeClass: "state-droppable",
			drop: function(event, ui) {
				insertLable(event, ui, $(this));
			}
		});
	}
}

function changeItem(data) {
	if (data != null && data.item != null &&  data.item.optionValue != undefined && data.item.optionValue.length > 0) {
		$("#newDragElement").find("font").attr("color", "");
		$("#newDragElementTd").find("div").draggable( {
			disabled: false,
			containment: '#allDragDropContent',
			stack: '#allDragDropContent table',
		      helper: 'clone',
		      revert: "invalid",
		      stop: function(event, ui) {
		    	  	$(this).css("z-index","auto");
		    	  }, 
		      zIndex: 99999999, 
		} );
	} else {
		$("#newDragElement").find("font").attr("color", "#b3b3b3");
		$("#newDragElementTd").find("div").draggable({ disabled: true });
	}
	
}

function changeDragElementDataType(element) {
	var value = $(element).val();
	var div = $("#newDragElementTd").find("div");
	
	if ($(div).attr("datatype-backup") == "5" || $(div).attr("datatype-backup") == "6" || $(div).attr("datatype-backup") == "7" || $(div).attr("datatype-backup") == "0") {
		$("#newDragElement").find("font").attr("color", "#b3b3b3");
		$("#newDragElementTd").find("div").draggable({ disabled: true });
	} else {
		$("#newDragElement").find("font").attr("color", "");
		$("#newDragElementTd").find("div").draggable( {
			disabled: false,
			containment: '#allDragDropContent',
			stack: '#allDragDropContent table',
		      helper: 'clone',
		      revert: "invalid",
		      stop: function(event, ui) {
		    	  	$(this).css("z-index","auto");
		    	  }, 
		      zIndex: 99999999,
		} );
	}
	
	if (value == 1) {
		
		$(div).attr('datatype', 21);
		$(div).attr('columnname', "text");
		$(div).attr('label', "Text");
	}
	else if (value == 2) {
		
		$(div).attr('datatype', 11);
		$(div).attr('columnname', "Link");
		$(div).attr('label', "Link");
		$(div).attr('labelText', "Link");
	} else {
		$("#newDragElement").find("font").attr("color", "");
		$("#newDragElementTd").find("div").draggable( {
			disabled: false,
			containment: '#allDragDropContent',
			stack: '#allDragDropContent table',
		      helper: 'clone',
		      revert: "invalid",
		      stop: function(event, ui) {
		    	  	$(this).css("z-index","auto");
		    	  }, 
		      zIndex: 99999999, 
		} );
		$(div).attr('datatype', $(div).attr('datatype-backup'));
	}
	
	$(".dropComponent").droppable({
		accept: "#srcgenElements div[id!='divLabel'], #srcgenAction div, #newDragElementTd div",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertComponent(event, ui, $(this));
		}
	});
	
	$(".dropLabel").droppable({
		accept: "#divLabel",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertLable(event, ui, $(this));
		}
	});
}

function saveScreenSetting(obj) {
	var modal = $("#dialog-form-parameter");
	
	if(!validateRequired(modal,"parameterName",dbMsgSource['sc.screendesign.0336'],true)) {
		return;
	}
	
	if(!validateRequired(modal,"parameterAttribute",dbMsgSource['sc.screendesign.0071'],true)) {
		return;
	}
	
	if(!validateAlphanumeric(modal,"parameterAttribute",dbMsgSource['sc.screendesign.0071'],true)) {
		return;
	}
	
	if(!validateSelectRequired(modal,"parameterDatatype",dbMsgSource['sc.screendesign.0196'],true)) {
		return;
	}

	obj = $(modal).data("target");
	var parameters = "";
	$(modal).find("#dialog-form-parameter-tbl-parameter").find("tr").each(function (i){
		if($(this).find("input[name=parameterAttribute]").val() != undefined && $(this).find("input[name=parameterAttribute]").val().length > 0){
			
			parameters += $(this).find("input[name=parameterName]").val() + "π" + $(this).find("input[name=parameterAttribute]").val()
			+ "π" + $(this).find('select[name=parameterDatatype]').val();
			parameters += "�";
		}
	});
	$(obj).closest("td").find("input[name=screenParameters]").val(parameters.substring(0, parameters.length - 1));
	$(modal).modal("hide");
}
function changeUnit(thiz){

}

function deleteEvent(obj){
	if(confirm(fcomMsgSource['inf.sys.0014'])){ 
		$("#dialog-component-autocomplete-tab-onchange").remove();
		
		reloadDroppableSpan();
		
		//delete not used form
		arrangeFormArea();
	} else {
		return false;
	}
	
}
function deleteForm(obj){
	if(confirm(fcomMsgSource['inf.sys.0014'])){
		
		var $parent = $(obj).closest('#srcgenScreenAndAction');
		
		$(obj).closest('.form-setting').next().remove();
		$(obj).closest('.form-setting').remove();
		reloadDroppableSpan();
		//reload form index
		refreshFormIndex();
		
		if ($parent.find('.form-area-content').length == 0) {
			insertForm(null, null, $('#srcgenFormTemplate'));
		}

	} else {
		return false;
	}
}

function saveModalFormSetting(obj) {
	var modal = $("#modal-form-setting");
	
	var target = $(modal).data("target");
	
	var formType = $(modal).find("[name=modal-setting-form-method]").val();
	if(formType != undefined && formType != ""){
		$(target).closest('.form-setting').next().find("input[name=screenFormMethodType]").val(formType);
	}
	var formAction = $(modal).find("[name=modal-setting-form-action]").val();
	if(formAction != undefined && formAction != ""){
		$(target).closest('.form-setting').next().find("input[name=screenFormFormActionCode]").val(formAction);
	}
	var formActionName = $(modal).find("[name=modal-setting-form-actionAutocomplete]").val();
	if(formActionName != undefined && formActionName != ""){
		$(target).closest('.form-setting').next().find("input[name=screenFormFormActionName]").val(formActionName);
	}
	var formEncryptType = $(modal).find("[name=modal-setting-form-enctype]").val();
	if(formEncryptType != undefined && formEncryptType != ""){
		$(target).closest('.form-setting').next().find("input[name=screenFormEncryptType]").val(formEncryptType);
	}
	
	$(modal).modal("hide");
}

function deleteBlock(obj){
	var id = obj.id;
	if(confirm(fcomMsgSource['inf.sys.0014'])){ 
		$(id).remove();
	} else {
		return false;
	}
}

function updateCategories(categories){
    $('#sub_category').empty();
    $.each(categories, function (index) {
       var optgroup = $('<optgroup>');
       optgroup.attr('label',categories[index].name);

        $.each(categories[index].children, function (i) {
           var option = $("<option></option>");
           option.val(i);
           option.text(categories[index].children[i]);

           optgroup.append(option);
        });
        $("#sub_category").append(optgroup);

    });

    $("#sub_category").multiselect('refresh'); //refresh the select here
}

function changeNavigationTo(item) {
	loadParameter($(item.target).next());
}

function saveInputParameterSetting() {
	var modal = $("#modal-event-param-setting");
	
	var obj = $(modal).data('data');
	var target = $(modal).data('target');
	var inputbeans = [];
	
	$(modal).find('.mapping-bean').find('tbody tr').each(function(){
		var $tr = $(this);
		var beanId = $tr.attr('beanid');
		var itemCode = $tr.find('input[name=screenItemCode]').val();
		
		inputbeans.push({
			beanid: beanId,
			itemcode: itemCode
		});
	});
	
	if (obj.inputbeans != undefined) {
		obj.inputbeans = inputbeans;
	} else {
		obj['inputbeans'] = inputbeans;
	}
	
	var data = convertToString(obj);
	$(target).closest('table').closest('td').find('input[name=blogicSetting]').val(data);
	
	$(modal).modal('hide');
}

function saveOutputParameterSetting() {
	var modal = $("#modal-event-assign-result");
	
	var obj = $(modal).data('data');
	var target = $(modal).data('target');
	var outputbeans = [];
	
	$(modal).find('.mapping-bean').find('tbody tr').each(function(){
		var $tr = $(this);
		var beanId = $tr.attr('beanid');
		var itemCode = $tr.find('input[name=screenItemCode]').val();
		
		outputbeans.push({
			beanid: beanId,
			itemcode: itemCode
		});
	});
	
	if (obj.outputbeans != undefined) {
		obj.outputbeans = outputbeans;
	} else {
		obj['outputbeans'] = outputbeans;
	}
	
	var data = convertToString(obj);
	$(target).closest('table').closest('td').find('input[name=blogicSetting]').val(data);
	
	$(modal).modal('hide');
}

function saveDialogSettingDefault(){
	
	var obj = $("#settingDefaultValue").data("target");
	
	var hasCheckRd = $("input[name='defaultValue']:checked").size();
	if(hasCheckRd <= 0){
		alert(dbMsgSource['sc.screendesign.0293']);
		return;
	}
	
	$(obj).closest("td").find("input[name^=screenItemForms][type=hidden]").val( $("input[name='defaultValue']:checked").val());
	$("#settingDefaultValue").modal("hide");
}

function changeNavigateType(obj) {
	var modal = $(obj).closest(".modal-dialog");
	setNavigateType($(obj).val(), modal);

}

function setNavigateType(value, modal) {
	if (value == 0) {
		$(modal).find("#navigateToScreen").show();
		$(modal).find("#navigateToblogic").hide();
		
		$(modal).find("#dialog-form-parameter-tbl-parameter").show();
		$(modal).find("#blogic-param").hide();
	} else if (value == 1) {
		$(modal).find("#navigateToScreen").hide();
		$(modal).find("#navigateToblogic").show();
		
		$(modal).find("#dialog-form-parameter-tbl-parameter").hide();
		$(modal).find("#blogic-param").show();
	}
}

function addTab(obj) {
	var modal=$("#modalTabSetting");
	var currentForm = $(modal).data("currentForm");
	var ul = $(obj).closest("ul");
	var index = $(ul).find("li").length - 1;
	
	addNewTab(currentForm, obj, index);
}

function deleteTab(obj) {
	if ($(obj).closest("ul").find("li").length == 2) {
		alert(dbMsgSource['sc.screendesign.0377']);
		return;
	}
	
	if(confirm(fcomMsgSource['inf.sys.0014'])){
		
		var prevItem = $(obj).closest("li").prev();
		
		var tabContent = $(obj).closest("ul").next();
		var index = $(obj).closest("li").index();
		$(obj).closest("li").remove();
		$(tabContent).find("#tab-design-" + index).remove();
		
		$(prevItem).find("a").tab("show");
	}
	
}

function saveTabSetting(obj) {
	var modal=$("#modalTabSetting");
	
	var isNewTab = $(modal).data("isNewTab");
	var target = $(modal).data("target");
	
	var currentTab = $(target).closest(".area-tab"); 
	var currentForm = $($(modal).data("target")).closest('.form-setting').next();
	
	if (!isNewTab) {
		currentForm = $($(modal).data("target")).closest(".form-area-content");
	}
	
	var formCode = $(currentForm).find("input[name=screenFormFormActionCode]").val();
	var ul = $(modal).find("#tab-setting").find(".nav-tabs"); 
	//validate
	
	var errorIndex = -1;
	$(ul).find("li").each(function(i) {
		
		if (i == $(modal).find("#tab-setting").find(".nav-tabs").find("li").length - 1) {
			return;
		}
		
		var value = $(this).find("input[type=text]").val();
		
		if (value.length == 0) {
			errorIndex = $(this).index();
			return;
		}
	});
	
	if (errorIndex >= 0) {
		alert(dbMsgSource['sc.screendesign.0377']);
		$(ul).find("li:eq("+errorIndex+")").find("input[type=text]").focus();
		
		$(ul).find("li").removeClass("active");
		$(ul).next().find("div").removeClass("active");
		$(ul).find("li:eq("+errorIndex+")").find("a").tab("show");
		
		return;
	}
	
	var checked = false;
	
	$(ul).next().find("table tbody tr").each(function() {
		if ($(this).find("input[type=checkbox]").prop("checked")) {
			checked = true;
			return;
		}
	});
	
	var value = $(currentForm).find("input[name=screenFormTab]").val();
	var data = null;
	if (value.length > 0) {
		data = convertToJson(value);
	}
	
	var tabCode = 0;
	if (data != null) {
		for (var i = 0; i < data.tabs.length; i++) {
			var code = data.tabs[i]["tabCode"];
			if (code != undefined && code != null) {
				if (tabCode < code) {
					tabCode = code;
				}
			}
		}
	}
	
	if (!isNewTab) {
		tabCode = parseInt($(currentTab).attr("id").split("-")[$(currentTab).attr("id").split("-").length - 1]);
	} else {
		tabCode++;
	}
	
	formCode = formCode + "-" + tabCode;
	formCode = formCode.replace(/ /g,'');
	
	if (!isNewTab) {
		
			
		if ($(currentTab).find(".menu-tab") != undefined) {
			$(currentTab).find(".menu-tab").remove();
		}
		
		$(currentTab).find(".areaContent").each(function() {
			
			$(currentTab).before($(this));
		});
		
		$(currentTab).find("#" + formCode + "-tab").remove();
		
		if ($(currentTab).find(".contain-tab-content") != undefined && $(currentForm).find(".contain-tab-content").length > 0) {
			$(currentTab).find(".contain-tab-content").remove();
		}
		$(currentTab).find("#" + formCode + "-tab-content").remove();
	}	
	
	if (!checked) {

		if (data != null) {
			var arr = [];
			for (var i = 0; i < data.tabs.length; i++) {
				if (data.tabs[i]["tabCode"] != tabCode) {
					arr.push(data.tabs[i]);
				}
			}
			data.tabs = arr;
			$(currentForm).find("input[name=screenFormTab]").val(convertToString(data));
		}
		$(currentTab).remove();
		$(modal).modal("hide");
		return;
	}
	
	var content = "";
		
	if ($(modal).find("input[name=tabType]:checked").val() == 1) {
		content = '<div class="menu-tab" style="float: left; width: 20%; margin: 0px; padding: 0px; margin-left: 4px;"><ul id="'+formCode+'-tab" class="nav nav-tabs tabs-left">' +
		  '</ul></div><div class="contain-tab-content" style="float: left; width: 79%;margin: 0px; padding: 0px;"><div id="'+formCode + '-tab-content" style="border: 1px solid #ddd;" class="tab-content"></div></div>';
	} else if ($(modal).find("input[name=tabType]:checked").val() == 0) {
		content = '<ul style="margin-left: 4px; margin-right: 4px;" id="'+formCode+'-tab" class="nav nav-tabs">' +
		  '</ul><div style="margin-left: 4px; margin-right: 4px; height: auto;" id="'+formCode + '-tab-content" class="tab-content"></div>';
	} else {
		content = '<div style="margin-left: 4px; margin-right: 4px;" class="panel-group-accordion" id="'+formCode+'-tab"></div>';
	}

	var content = "<div id=\""+formCode+"\" style='width: 100%; float:left;' class='area-tab' >"+content+"</div>"
	
	if (isNewTab) {
		$(currentForm).prepend(content);
	} else {
		
		$(currentTab).after(content);
		$(currentTab).remove();
	}
	
	
	var tabForm = $(currentForm).find("#" + formCode + "-tab");
	var tabFormContent = $(currentForm).find("#" + formCode + "-tab-content"); 
	
	$(ul).find("li").each(function(i) {
		
		if (i == $(modal).find("#tab-setting").find(".nav-tabs").find("li").length - 1) {
			return;
		}
		
		var tabTitle = '<div class="form-inline">' +
						'	<span style="cursor: move;" class=".ui-state-dark qp-glyphicon glyphicon glyphicon-sort sortable srcgenTableSort"></span>' +
						' <span style="cursor: pointer;" onclick="openTabSetting(this)" class=".ui-state-dark qp-glyphicon glyphicon glyphicon-cog"></span>&nbsp;' +
						$(this).find("input[type=text]").val() +
						'</div>';
		
		var isNotDeleteTab = false;
		
		$(modal).find("#tab-design-" + i).find("table tbody tr").each(function() {
			if ($(this).find("input[type=checkbox]").prop("checked")) {
				isNotDeleteTab = true;
				return;
			}
		});
		
		if (isNotDeleteTab) {
			if ($(modal).find("input[name=tabType]:checked").val() == 2 || $(modal).find("input[name=tabType]:checked").val() == 3) {
				var dataParent = '';
				if ($(modal).find("input[name=tabType]:checked").val() == 3) {
					dataParent = ' data-parent="#'+formCode+'-tab" ';
				}
				var selectedAccordion = "";
				var title = '        <a data-toggle="collapse" '+dataParent+' href="#'+formCode+"tab-"+i+'">' +
							$(this).find("input[type=text]").val()+' <i class=\"indicator glyphicon  pull-right\"></i></a>';
				if (i == 0) {
					selectedAccordion = "in";
					
					title = '<div class="form-inline">' +
					'	<span style="cursor: move;" class=".ui-state-dark qp-glyphicon glyphicon glyphicon-sort sortable srcgenTableSort"></span>' +
					' <span style="cursor: pointer;" onclick="openTabSetting(this)" class=".ui-state-dark qp-glyphicon glyphicon glyphicon-cog"></span>&nbsp;' +
					title +
					'</div>';
				}
				
				var accordion = '  <div class="panel panel-default-accordion">' +
								'	    <div class="panel-heading">' +
								'      <span style="color: white;" class="qp-heading-text">' +
										title +
								'     </span>' +
								'    </div>' +
								'    <div id="'+formCode+"tab-"+i+'" class="panel-collapse collapse '+selectedAccordion+'">' +
								'      <div class="panel-body"></div>' +
								'    </div>' +
								'  </div>';
				$(tabForm).append(accordion);
				
				var tabContent = $(tabForm).find("#" + formCode+"tab-"+i).find('.panel-body');
				
				$(modal).find("#tab-design-" + i).find("table tbody tr").each(function() {
					if ($(this).find("input[type=checkbox]").prop("checked")) {
						var areaCode = $(this).find("td:eq(2)").html();
						
						var areaHidden = $(currentForm).find(".areaContent").find("input[name=formAreaCode][value="+areaCode+"]");
						
						if (areaHidden != undefined) {
							var area = $(areaHidden).closest(".areaContent");
							
							$(area).appendTo($(tabContent));
						}
					}
				});
				
			} else {
				if (i == 0) {
					$(tabForm).append("<li><a data-toggle='tab' href='#"+formCode+"tab-"+i+"'>"+tabTitle+"</a></li>");
				} else {
					$(tabForm).append("<li><a data-toggle='tab' href='#"+formCode+"tab-"+i+"'>"+$(this).find("input[type=text]").val()+"</a></li>");
				}
				
				$(tabFormContent).append("<div id='"+formCode+"tab-"+i+"' style='overflow: hidden;' class='tab-pane'></div>");
				
				var tabContent = $(tabFormContent).find("#"+formCode+"tab-" + i).first();
				var height = 0;
				$(modal).find("#tab-design-" + i).find("table tbody tr").each(function() {
					if ($(this).find("input[type=checkbox]").prop("checked")) {
						var areaCode = $(this).find("td:eq(2)").html();
						
						var areaHidden = $(currentForm).find(".areaContent").find("input[name=formAreaCode][value="+areaCode+"]");
						
						if (areaHidden != undefined) {
							var area = $(areaHidden).closest(".areaContent");
							
							$(area).appendTo($(tabContent));
						}
					}
				});
			}
		}
		
	});
	
	$(tabForm).find("li:eq(0)").find("a").tab("show");
	
	$(".form-area-content").sortable({
        connectWith: '.form-area-content',
        handle: '.srcgenTableSort',
        update: function(e, ui) {
        	refreshFormIndex();
		},
		helper: function(e, ui) {
			ui.width(ui.outerWidth());
			return ui;
		},
		items: '.areaContent, .area-tab',
	});
	
	$(".area-tab").sortable({
        handle: '.srcgenTableSort',
        update: function(e, ui) {
        	refreshFormIndex();
		},
		helper: function(e, ui) {
			ui.width(ui.outerWidth());
			return ui;
		},
		items: '.areaContent',
	});
	
	saveTabData($(modal), $(currentForm), tabCode);
	
	$(modal).modal("hide");
}

function getAreaTab(obj) {
	if ($(obj).prop("checked")) {
		var areaCode = $(obj).closest("tr").find("td:eq(2)").html();
		var currentDiv = $(obj).closest("div[id^=tab-design]");
		$(obj).closest(".tab-content").find(".tab-pane:not([id^="+$(currentDiv).attr("id")+"])").find("table tbody tr").each(function() {
			if ($(this).find("td:eq(2)").html() == areaCode) {
				$(this).hide();
			}
		});
	} else {
		var areaCode = $(obj).closest("tr").find("td:eq(2)").html();
		var currentDiv = $(obj).closest("div[id^=tab-design]");
		$(obj).closest(".tab-content").find(".tab-pane:not([id^="+$(currentDiv).attr("id")+"])").find("table tbody tr").each(function() {
			if ($(this).find("td:eq(2)").html() == areaCode) {
				$(this).show();
			}
		});
	}
	
}

function saveTabData(modal, currentForm, tabCode) {
	var data = {};
	var value = $(currentForm).find("input[name=screenFormTab]").val();
	
	if (value.length > 0) {
		data = convertToJson(value);
	}
	
	data["type"] = $(modal).find("input[name=tabType]:checked").val();
	
	var tabSetting = $(modal).find("#tab-setting");
	
	if (data["tabs"] == undefined || data["tabs"] == null) {
		data["tabs"] = [];
	}
	
	var tabs = [];
	for (var i = 0; i < data["tabs"].length; i++) {
		if (data["tabs"][i]["tabCode"] != tabCode) {
			tabs.push(data["tabs"][i]);
		}
	}
	
	$(tabSetting).find("a[href^='#tab-design']").each(function() {
		var tab = {};
		tab["title"] = $(this).find("input[type=text]").val();
		tab['tabCode'] = tabCode;
		tab['areas'] = "";
		tab['tabDirection'] = $(modal).find("input[name=tabType]:checked").val();
		
		$(tabSetting).find($(this).attr("href")).find("table tbody tr").each(function(i) {
			if ($(this).find("input[type=checkbox]").prop("checked")) {
				tab['areas'] += $(this).find("td:eq(2)").html() + ",";
			} 
		});
		
		tab['areas'] = tab['areas'].substring(0, tab['areas'].length - 1);
		
		if (tab['areas'] != null && tab['areas'].length > 0) {
			tabs.push(tab);
		}
	});
	
	data["tabs"] = tabs;

	$(currentForm).find("input[name=screenFormTab]").val(convertToString(data));
}