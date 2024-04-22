function openDialogAutocompleteSetting(obj){
	var modal = $("#dialog-component-autocomplete-setting");
	$(modal).data("target", obj);
	var value = $(obj).closest("span").find("input[name='formElement']").val();
	if(value==undefined){
	}
	var column = convertToJson(value);
	
	displayStyle(modal, column);
	
	$(modal).data("data", value);
	$(modal).find("#dialog-form-options-error").html("");
	
	var data = $(modal).find("input[name='dialogLabelSettingHidden']").val($(obj).closest("th").find("input").val());
	$("#dialog-form-autocomplete-error").html("");
	$(modal).find("td[basetype='dialog-autocomplete-setting-base-types']").html(setBaseType(column));
	
	//bind data to dialog
	if (column.columnname != undefined) {
		$(modal).find("input[name='columnname']").val(column.columnname);
	} else {
		$(modal).find("input[name='columnname']").val("");
	}

	if (column.width != undefined && column.width != '' && column.width.length > 0 && column.width != "0" && column.width != 'undefined'  ) {
		$(modal).find("input[name='dialog-component-setting-element-width']").val(column.width);
	} else {		
		$(modal).find("input[name='dialog-component-setting-element-width']").val("100");
	}
	
	if (column.width != undefined && column.width != 'undefined' && column.width != '' ) {
		$(modal).find("[name='dialog-component-setting-element-width-unit']").val(column.widthunit);
	} else {		
		$(modal).find("[name='dialog-component-setting-element-width-unit']").val("%");
	}
	if (column.physicaldatatype != undefined && column.physicaldatatype != null) {
		$(modal).find("select[name='baseType']").val(column.physicaldatatype);
	} else {
		$(modal).find("select[name='baseType']").val(1);
	}
		
	$(modal).find("input[name='dialogOnChangeEvent']").val(column.dialogOnChangeEvent);
	$(modal).find("input[name='dialogOnSelectEvent']").val(column.dialogOnSelectEvent);
	$(modal).find("input[name='colspan']").val(column.colspan);
	$(modal).find("input[name='rowspan']").val(column.rowspan);
	var enablegroup = $(obj).closest("td").find("input[name='enableGroup']").val();
	var groupDisplayType = $(obj).closest("td").find("input[name='groupDisplayType']").val();
	if(enablegroup != undefined && enablegroup.length > 0){
		$(modal).find("input[name='enableGroup']").prop("checked",eval(enablegroup));
	}
	else{
		$(modal).find("input[name='enableGroup']").prop("checked",false);
	}
	/*if (groupDisplayType != undefined && groupDisplayType.length > 0) {		
		$(modal).find("select[name='dialog-component-setting-group-display-type'] option[value="+groupDisplayType+"]").attr("selected", "selected");
	}else{
		$(modal).find("select[name='dialog-component-setting-group-display-type'] option[value="+1+"]").attr("selected", "selected");
	}*/
	
	if (groupDisplayType != undefined && groupDisplayType != '' && groupDisplayType.length > 0) {		
		$(modal).find("select[name='dialog-component-setting-group-display-type']").val(groupDisplayType);
	}else{
		$(modal).find("select[name='dialog-component-setting-group-display-type']").val(1);
	}
	
	if (column.mandatory != undefined) {
		if (column.mandatory == "true") {
			$(modal).find("input[name='mandatory']").prop("checked", true);
		} else {
			$(modal).find("input[name='mandatory']").prop("checked", false);
		}
	} else {		
		$(modal).find("input[name='mandatory']").prop("checked", false);
	}
	
	openProperties(modal, column);
	
	$.qp.initialAllPicker($(modal).find("input[name='dialog-component-setting-element-width']"));
	setFirstTabActive($(modal));

	//show datasource
	if (column.datasourcetype != undefined && column.datasourcetype == '1') {
		
		if (column.sqldesignid != undefined) {
			$(modal).find("input[name=sqldesignid]").val(column.sqldesignid);
		} else {
			$(modal).find("input[name=sqldesignid]").val('');
		}
		
		if (column.sqldesignidtext != undefined) {
			$(modal).find("input[name=sqldesignidAutocomplete]").val(column.sqldesignidtext);
		} else {
			$(modal).find("input[name=sqldesignidAutocomplete]").val('');
		}
		
		initOptionAutocomplete($(modal).find("#sql-setting"), column.sqldesignid);
		if (column.allowAnyInput != undefined && column.allowAnyInput != null && column.allowAnyInput != '') {
			$(modal).find("input[name=allowAnyInput][value="+column.allowAnyInput+"]").prop("checked", true);
		}
		
		var itemCode = $(obj).closest('.form-layout').find('.form-content').find('input[name=screenFormFormActionCode]').val() + '.' + 
		$(obj).closest('.areaContent').find('input[name=formAreaCode]').val() + '.' + column.columnname;
		
		$(modal).find("#settingInput tbody tr:first").find("input[name=screenItemDependencyAutocomplete]").val('this.value(search key)');
		$(modal).find("#settingInput tbody tr:first").find("input[name=screenItemDependency]").val(itemCode);
		
		var parameters = column.parameters.split("�");
		for (var i = 0; i < parameters.length; i++) {
			
			var param = parameters[i].split("π");
			if (param.length == 2) {
				var id = param[0];
				var screenItem = param[1];
				
				$(modal).find("#settingInput tbody tr").each(function() {
					if ($(this).find("input[name=inputId]").val() == id) {
						$(this).find("input[name=screenItemDependencyAutocomplete]").val(getCode(screenItem));
						$(this).find("input[name=screenItemDependency]").val(screenItem);
					}
				});
			}
			
		}

		var itemCode = $(obj).closest('.form-layout').find('.form-content').find('input[name=screenFormFormActionCode]').val() + '.' + 
		$(obj).closest('.areaContent').find('input[name=formAreaCode]').val() + '.' + column.columnname;
		
		$(modal).find("#settingInput tbody tr:first").find("input[name=screenItemDependencyAutocomplete]").val('this.value(search key)');
		$(modal).find("#settingInput tbody tr:first").find("input[name=screenItemDependency]").val(itemCode);
		$.qp.disableAutocomplete($(modal).find("#settingInput tbody tr:first").find("input[name=screenItemDependency]"));
		
		if (column.optionlabel != undefined) {
			$(modal).find("input[name=optionlabel]").val(column.optionlabel);
		} else {
			$(modal).find("input[name=optionlabel]").val('');
		}
		
		if (column.optionlabeltext != undefined) {
			$(modal).find("input[name=optionlabelAutocomplete]").val(column.optionlabeltext);
		} else {
			$(modal).find("input[name=optionlabelAutocomplete]").val('');
		}		
		
		if (column.optionvalue != undefined) {
			$(modal).find("input[name=optionvalue]").val(column.optionvalue);
		} else {
			$(modal).find("input[name=optionvalue]").val('');
		}
		
		if (column.optionvaluetext != undefined) {
			$(modal).find("input[name=optionvalueAutocomplete]").val(column.optionvaluetext);
		} else {
			$(modal).find("input[name=optionvalueAutocomplete]").val('');
		}
	} else {
		$(modal).find("input[name=sqldesignid]").val('');
		$(modal).find("input[name=sqldesignidAutocomplete]").val('');
		$(modal).find("td[optionLabel='optionLabel']").html("");
		$(modal).find("td[optionValue='optionValue']").html("");
		
		$(modal).find("#autocompleteTitle").hide();
		$(modal).find("#autocompleteInputSetting").hide();
	}
	
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});
}
function openScreenSetting(obj){
	var modal = $("#dialog-form-parameter");
	$(modal).data("target", obj);
	$(modal).find("#dialog-form-options-error").html("");
	$(modal).find("#dialog-form-parameter-tbl-parameter").find("tbody").find("tr:gt(0)").remove();
	var parameters = $(obj).closest("td").find("[name=screenParameters]").val();
	if(parameters != undefined && parameters.length > 0){
		var arrParameters = parameters.split("�");
		$(arrParameters).each(function(i){
			var dataParameter = arrParameters[i].split("π");
			if(i >= 1){
				$.qp.addRowJSByLink($(modal).find("#dialog-form-parameter-tbl-parameter").next().find("a"));
			}
			$(modal).find("#dialog-form-parameter-tbl-parameter").find("tbody").find("tr:eq("+i+")").find("input[name=parameterAttribute]").val(dataParameter[1]);
			$(modal).find("#dialog-form-parameter-tbl-parameter").find("tbody").find("tr:eq("+i+")").find("input[name=parameterName]").val(dataParameter[0]);
			$(modal).find("#dialog-form-parameter-tbl-parameter").find("tbody").find("tr:eq("+i+")").find("select[name=parameterDatatype]").val(dataParameter[2]);
		});
	} else {
		$(modal).find("#dialog-form-parameter-tbl-parameter").find("tbody").find("tr:eq(0)").find("input[name=parameterAttribute]").val("");
		$(modal).find("#dialog-form-parameter-tbl-parameter").find("tbody").find("tr:eq(0)").find("input[name=parameterName]").val("");
		$(modal).find("#dialog-form-parameter-tbl-parameter").find("tbody").find("tr:eq(0)").find("select[name=parameterDatatype]").val("");
	}
	setFirstTabActive($(modal));
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});
}

function openDialogLinkAreaSetting(obj){
	var modal = $("#dialog-link-area-setting");
	
	$(modal).data("target", obj);
	$(modal).find('#navigateToScreenBlogic').find("input[name='navigateToBlogicAutocomplete']").attr("arg01","");
	$(modal).data("data",  $(obj).closest("span").find("input[name='formElement']").val());
	$(modal).find("#dialog-form-options-error").html("");
	
	var data;
	var itemsBaseType = CL_PRIMITIVE_DATATYPE;
	var formElement = $(obj).closest("span").find("input[name='formElement']");
	if(formElement.val() == undefined){
		
		var tdIndex = $(obj).closest("td").attr("index");
		var trIndex = $(obj).closest("tr").attr("index");
		formElement = $(obj).closest("table").find("thead").find("tr:eq("+trIndex+")").find("th:eq("+tdIndex+")").find("input[name=formElement]");
	}
	data =  convertToJson($(formElement).val());
	$(modal).find("#screenTransitionAutocompleteId").attr("arg03", "");
	$(modal).find("#screenTransitionAutocompleteId").val("");
	$(modal).find("input[name=screenTransition]").val("");
	
	if (data.tabindex != undefined) {
		$(modal).find("input[name=tabindex]").val(data.tabindex);
	} else {
		$(modal).find("input[name=tabindex]").val('');
	}
	
	if (data.navigateTo != undefined && data.navigateTo != null && data.navigateTo.length > 0) {
		$(modal).find("#screenTransitionAutocompleteId").attr("arg03", data.navigateTo);
	}
	
	if (data.screenTransition != undefined && data.screenTransition != null && data.screenTransition.length > 0) {
		$(modal).find("input[name=screenTransition]").val(data.screenTransition);
		$(modal).find("#screenTransitionAutocompleteId").attr("arg04", data.screenTransition);
	}
	
	if (data.screenTransitionText != undefined && data.screenTransitionText != null && data.screenTransitionText.length > 0) {
		$(modal).find("#screenTransitionAutocompleteId").val(data.screenTransitionText);
	}
	if(data.datatype == 11) {
		$(modal).find("tr[id='linkStatic']").show();
		$(modal).find("tr[name='linkDynamic']").hide();
		$(modal).find("tr[name='baseType']").hide();
	}
	// set base type for [Dynamic link] element
	if(data.datatype == 22) {
		
		$(modal).find("tr[id='linkStatic']").hide();
		$(modal).find("tr[name='linkDynamic']").show();
		$(modal).find("tr[name='baseType']").show();
		
		$(modal).find("select[name='baseType'] option").remove();
		var options = ""
		for (var i = 1; i <= parseInt(itemsBaseType['total']); i++) {
			options+= "<option value=\""+i+"\"><lable>"+itemsBaseType[i]+"</label></option>";
		}
		$(modal).find("#baseStypeDynamicLink").show();
		$(modal).find("select[name='baseType']").append(options);
	} else {
		$(modal).find("#baseStypeDynamicLink").hide();
	}
	
	if (data.physicaldatatype != undefined) {
		$(modal).find("select[name='baseType'] option[value='"+data.physicaldatatype+"']").attr("selected","selected");
	} else {
		$(modal).find("select[name='baseType']").val("");
	}
	displayStyle(modal, data);
	$(modal).find("input[name=linkLabelName]").val(data.label);
	$(modal).find("input[name=linkLabelNameAutocomplete]").val(htmlDecode(data.labelText));
	
	$(modal).find("input[name=linkDynamicLabelNameAutocomplete]").val(htmlDecode(data.labelText));
	if (data.columnname != undefined) {
		$(modal).find("input[name='columnname']").val(data.columnname);
	} else {
		$(modal).find("input[name='columnname']").val(htmlDecode(data.labelText));
	}
	
	$(modal).find("input[name=isBundle]").prop("checked",data.isBundle);
	
	$(modal).find('input[name=enableConfirm]').prop('checked', false);
	$(modal).find('#enableConfirmGroup').hide();
	$(modal).find('input[name=messageConfirm]').val('');
	$(modal).find('input[name=messageConfirmAutocomplete]').val('');
	
	if (data.enableConfirm !=undefined && data.enableConfirm != null && data.enableConfirm == 1) {
		$(modal).find('input[name=enableConfirm]').prop('checked', true);
		$(modal).find('#enableConfirmGroup').show();
		$(modal).find('input[name=messageConfirm]').val(data["messageConfirm"]);
		$(modal).find('input[name=messageConfirmAutocomplete]').val(data["messageConfirmText"]);
	}
	
	$(modal).find("input[name='actionName']").val(data.actionName);
	$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:gt(0)").remove();
	
	$(modal).find("input[name='colspan']").val(data.colspan);
	$(modal).find("input[name='rowspan']").val(data.rowspan);
	var enablegroup = $(obj).closest("td").find("input[name='enableGroup']").val();
	var groupDisplayType = $(obj).closest("td").find("input[name='groupDisplayType']").val();
	if(enablegroup != undefined && enablegroup.length > 0){
		$(modal).find("input[name='enableGroup']").prop("checked",eval(enablegroup));
	}
	else{
		$(modal).find("input[name='enableGroup']").prop("checked",false);
	}
	if (groupDisplayType != undefined && groupDisplayType.length > 0) {		
		$(modal).find("select[name='dialog-link-area-setting-group-display-type'] option[value="+groupDisplayType+"]").attr("selected", "selected");
	}else{
		$(modal).find("select[name='dialog-link-area-setting-group-display-type'] option[value="+1+"]").attr("selected", "selected");
	}
	setFirstTabActive($(modal));
	var formAreaType = $(obj).parent().closest("div").parent().prev("div").find("input[name='formAreaType']").val(); 
	if(formAreaType == undefined) {
		formAreaType = $(obj).parent().closest("div").prev("div").find("input[name='formAreaType']").val(); 
	}
	
	if (data.navigateToBlogic != "" && data.navigateToBlogic != "undefined" && data.navigateToBlogic != undefined && data.navigateToBlogic != null) {
		$(modal).find('#navigateToScreenBlogic').find("input[name='navigateToBlogic']").val(data.navigateToBlogic);
	} else {
		$(modal).find('#navigateToScreenBlogic').find("input[name='navigateToBlogic']").val("");
	}
	if (data.navigateToBlogic != "" && data.navigateToBlogic != "undefined" && data.navigateToBlogicText != undefined && data.navigateToBlogicText != null) {
		$(modal).find('#navigateToScreenBlogic').find("input[name='navigateToBlogicAutocomplete']").val(htmlDecode(data.navigateToBlogicText));
	} else {
		$(modal).find('#navigateToScreenBlogic').find("input[name='navigateToBlogicAutocomplete']").val("");
	}
	if(data.datatype == 22 || data.datatype == 21){
		if (data.datasourcetype == "2") {
			showDataSourceInView(modal, data);
			$(modal).find("a[href=#dialog-link-area-setting-codelist]").closest('li').show();
		} else {
			if ((data.dialogAutocompleteCode == undefined || data.dialogAutocompleteCode.length == 0)) {
				showDataSourceInView(modal, data);
				$(modal).find("a[href=#dialog-link-area-setting-codelist]").closest('li').show();
			} else {
				$(modal).find("a[href=#dialog-link-area-setting-codelist]").closest('li').hide();
			}
		}
	} else {
		$(modal).find("a[href=#dialog-link-area-setting-codelist]").closest('li').hide();
	}
	
	if(data.datatype == 22) {
		if(data.datasourcetype == undefined || data.datasourcetype == "") {
			$(modal).find("#dialog-link-area-setting-codelist").find("input[name='dataSourceType'][value='1']").prop("checked",true);
			$(modal).find("#dialog-link-area-setting-codelist").find("#accordion").hide();
		}
	}
	
	displayParamAction(modal, data);
	if (data.width != undefined && data.width != null && data.width.length > 0 && data.width != "NaN") {		
		$(modal).find("input[name='dialog-component-setting-element-width']").val(data.width);
	} else {		
		$(modal).find("input[name='dialog-component-setting-element-width']").val("100");
	}
	if (data.widthunit != undefined && data.widthunit != null && data.widthunit.length > 0 && data.widthunit != 'undefined') {		
		$(modal).find("[name='dialog-component-setting-element-width-unit']").val(data.widthunit);
	} else {		
		$(modal).find("[name='dialog-component-setting-element-width-unit']").val("%");
	}
	
	if(data.screenDesignTextCodeListId != undefined && data.screenDesignTextCodeListId != "") {
		$(modal).find("input[name='screenNameCodeListAutocomplete']").val(data.screenDesignTextCodeListId);
	} else {
		$(modal).find("input[name='screenNameCodeListAutocomplete']").val("");
	}
	var arr = [];
	
	var navigateTo = "";
	if($(modal).find("input[name='navigateTo']").val() != "" && $(modal).find("input[name='navigateTo']").val() != undefined) {
		navigateTo = $(modal).find("input[name='navigateTo']").val();
	}
	$(modal).find("input[id='navigateToBlogicAutocompleteId']").attr("arg01",navigateTo);
	
	if(data.datatype == 11) {
		if (data.label != undefined){
			$(modal).find("input[name=labelName]").val(data.label);
			
			if (data.label.length == 0) {
				if (data.labelText != undefined && data.labelText != "undefined"){
					$(modal).find("input[name=labelName]").val(htmlDecode(data.labelText));
				} else {
					$(modal).find("input[name=labelName]").val('');
				}
			}
		} else {
			if (data.labelText != undefined && data.labelText != "undefined"){
				$(modal).find("input[name=labelName]").val(htmlDecode(data.labelText));
			} else {
				$(modal).find("input[name=labelName]").val('');
			}
		}
		if(data.messageLevel == "" || data.messageLevel == undefined || data.messageLevel == null) {
			data.messageLevel = 2
		}
		$(modal).find("#labelNameAutocompleteId").attr("arg06",data.messageLevel);
		$(modal).find("#labelNameAutocompleteId").attr("arg08",data.labelText);
		
		var messageLevelText = getMessageLevelText(data.messageLevel);
		$(modal).find("div[class*='level-text']").html(messageLevelText);
		
		if (data.labelText != undefined && data.labelText != "undefined"){
			if (data.labelText.length == 0 && data.label != undefined && data.label != null && data.label.length > 0) {
				$(modal).find("input[name=labelNameAutocomplete]").val(htmlDecode(data.label) );
			} else {
				$(modal).find("input[name=labelNameAutocomplete]").val(htmlDecode(data.labelText) );
			}
		} else {
			if (data.label != undefined) {
				$(modal).find("input[name=labelNameAutocomplete]").val(data.label);
			} else {
				$(modal).find("input[name=labelNameAutocomplete]").val('');
			}
		}
	} else {
		if (data.label != undefined){
			$(modal).find("input[name=linkDynamicLabelName]").val(data.label);
			
			if (data.label.length == 0) {
				if (data.labelText != undefined && data.labelText != "undefined"){
					$(modal).find("input[name=linkDynamicLabelName]").val(htmlDecode(data.labelText));
				} else {
					$(modal).find("input[name=linkDynamicLabelName]").val('');
				}
			}
		} else {
			if (data.labelText != undefined && data.labelText != "undefined"){
				$(modal).find("input[name=linkDynamicLabelName]").val(htmlDecode(data.labelText));
			} else {
				$(modal).find("input[name=linkDynamicLabelName]").val('');
			}
		}
		if(data.messageLevel == "" || data.messageLevel == undefined || data.messageLevel == null) {
			data.messageLevel = 2
		}
		$(modal).find("#linkDynamicLabelNameAutocompleteId").attr("arg06",data.messageLevel);
		$(modal).find("#linkDynamicLabelNameAutocompleteId").attr("arg08",data.labelText);
		
		var messageLevelText = getMessageLevelText(data.messageLevel);
		$(modal).find("div[class*='level-text']").html(messageLevelText);
		
		if (data.labelText != undefined && data.labelText != "undefined"){
			if (data.labelText.length == 0 && data.label != undefined && data.label != null && data.label.length > 0) {
				$(modal).find("input[name=linkDynamicLabelNameAutocomplete]").val(htmlDecode(data.label) );
			} else {
				$(modal).find("input[name=linkDynamicLabelNameAutocomplete]").val(htmlDecode(data.labelText) );
			}
		} else {
			if (data.label != undefined) {
				$(modal).find("input[name=linkDynamicLabelNameAutocomplete]").val(data.label);
			} else {
				$(modal).find("input[name=linkDynamicLabelNameAutocomplete]").val('');
			}
		}
	}
	
	$(modal).find("input[name='columnname']").attr('datatype',data.datatype);
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});
}
function openDialogLinkSetting(obj){
	var modal = $("#dialog-link-setting");
	$(modal).data("target", obj);
	$(modal).data("data", $(obj).closest("span").find("input[name='formElement']").val());
	$(modal).find("#dialog-form-options-error").html("");

	var data;
	var formElement = $(obj).closest("span").find("input[name='formElement']");
	if(formElement.val() == undefined){
		
		var tdIndex = $(obj).closest("td").attr("index");
		var trIndex = $(obj).closest("tr").attr("index");
		formElement = $(obj).closest("table").find("thead").find("tr:eq("+trIndex+")").find("th:eq("+tdIndex+")").find("input[name=formElement]");
	}
	data =  convertToJson($(formElement).val());
	displayStyle(modal, data);
	
	$(modal).find("#screenTransitionAutocompleteId").attr("arg03", "");
	$(modal).find("#screenTransitionAutocompleteId").val("");
	$(modal).find("input[name=screenTransition]").val("");
	
	if (data.navigateTo != undefined && data.navigateTo != null && data.navigateTo.length > 0) {
		$(modal).find("#screenTransitionAutocompleteId").attr("arg03", data.navigateTo);
	}
	
	if (data.screenTransition != undefined && data.screenTransition != null && data.screenTransition.length > 0) {
		$(modal).find("input[name=screenTransition]").val(data.screenTransition);
		$(modal).find("#screenTransitionAutocompleteId").attr("arg04", data.screenTransition);
	}
	
	if (data.screenTransitionText != undefined && data.screenTransitionText != null && data.screenTransitionText.length > 0) {
		$(modal).find("#screenTransitionAutocompleteId").val(data.screenTransitionText);
	}
	
	$(modal).find('input[name=enableConfirm]').prop('checked', false);
	$(modal).find('#enableConfirmGroup').hide();
	$(modal).find('input[name=messageConfirm]').val('');
	$(modal).find('input[name=messageConfirmAutocomplete]').val('');
	
	if (data.enableConfirm !=undefined && data.enableConfirm != null && data.enableConfirm == 1) {
		$(modal).find('input[name=enableConfirm]').prop('checked', true);
		$(modal).find('#enableConfirmGroup').show();
		$(modal).find('input[name=messageConfirm]').val(data["messageConfirm"]);
		$(modal).find('input[name=messageConfirmAutocomplete]').val(data["messageConfirmText"]);
	}
	
	$(modal).find("input[name=linkLabelName]").val(data.label);
	$(modal).find("input[name=linkLabelNameAutocomplete]").val(htmlDecode(data.labelText));
	$(modal).find("input[name=isBundle]").prop("checked",data.isBundle);

	displayParamAction(modal, data);
	
	$(modal).find("input[name='actionName']").val(data.actionName);
	$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:gt(0)").remove();
	
	$(modal).find("input[name='colspan']").val(data.colspan);
	$(modal).find("input[name='rowspan']").val(data.rowspan);
	setFirstTabActive($(modal));
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});
}

function openDialogButtonSetting(button){
	var dialogForm = $("#dialog-button-setting");
	$(dialogForm).data("target", obj);
	
	
	$(dialogForm).find("#dialog-form-options-error").html("");
	
	var input = $(button).next("input");
	var inputVal = $.parseJSON("{"+$(input).val()+"}");
	$(dialogForm).data("data", convertToJson($(input).val()));
	$(dialogForm).find("input[name=actionLabel]").val(inputVal.msgcode);
	$(dialogForm).find("input[name=toScreenId]").val(inputVal.toscreenid);
	$(dialogForm).find("input[name=actionName]").val(inputVal.connectionmsg);
	$(dialogForm).find("input[name=navigateTo]").val(inputVal.navigateto);
	if(inputVal.actiontype == 1){
		$(dialogForm).find("input[name=isSubmit]").prop('checked', true);
		$(dialogForm).find("#dialogActionParamSetting").hide();
	} else {
		$(dialogForm).find("input[name=isSubmit]").prop('checked', false);
		$(dialogForm).find("#dialogActionParamSetting").show();
	}
	
	$(dialogForm).find("#tableDialogActionParameter").find("tbody").find("tr:gt(0)").remove();
	var parameters = inputVal.parameters;
	if(parameters != undefined && parameters.length > 0){
		var arrParameters = parameters.split("�");
		$(arrParameters).each(function(i){
			var dataParameter = arrParameters[i].split(",");
			if(i >= 1){
				addParameterJS($(dialogForm).find("#tableDialogActionParameter").next("a"));
			}
			$(dialogForm).find("#tableDialogActionParameter").find("tbody").find("tr:eq("+i+")").find("input[name=parameterAttribute]").val(dataParameter[0]);
			$(dialogForm).find("#tableDialogActionParameter").find("tbody").find("tr:eq("+i+")").find("input[name=dbTablecode]").val(dataParameter[1]);
			$(dialogForm).find("#tableDialogActionParameter").find("tbody").find("tr:eq("+i+")").find("input[name=dbTablecodeAutocomplete]").val(dataParameter[2]);
			$(dialogForm).find("#tableDialogActionParameter").find("tbody").find("tr:eq("+i+")").find("input[name=dbColumcode]").val(dataParameter[3]);
			$(dialogForm).find("#tableDialogActionParameter").find("tbody").find("tr:eq("+i+")").find("input[name=dbColumcodeAutocomplete]").val(dataParameter[4]);
			$(dialogForm).find("#tableDialogActionParameter").find("tbody").find("tr:eq("+i+")").find("input[name=dbColumcodeAutocomplete]").attr("arg01", dataParameter[1]);
		});
	} else {
		$(dialogForm).find("#tableDialogActionParameter").find("tbody").find("tr:eq(0)").find("input[name=parameterAttribute]").val("");
		$(dialogForm).find("#tableDialogActionParameter").find("tbody").find("tr:eq(0)").find("input[name=dbTablecode]").val("");
		$(dialogForm).find("#tableDialogActionParameter").find("tbody").find("tr:eq(0)").find("input[name=dbTablecodeAutocomplete]").val("");
		$(dialogForm).find("#tableDialogActionParameter").find("tbody").find("tr:eq(0)").find("input[name=dbColumcode]").val("");
		$(dialogForm).find("#tableDialogActionParameter").find("tbody").find("tr:eq(0)").find("input[name=dbColumcodeAutocomplete]").val("");
		$(dialogForm).find("#tableDialogActionParameter").find("tbody").find("tr:eq(0)").find("input[name=dbColumcodeAutocomplete]").attr("arg01", "");
	}
	setFirstTabActive($(modal));
	$(dialogForm).modal("show");	
}

function openDialogButtonAreaSetting(obj){
	var modal = $("#dialog-button-area-setting");
	$(modal).data("target", obj);
	$(modal).data("data", $(obj).closest("span").find("input[name='formElement']").val());
	$(modal).find("#dialog-form-options-error").html("");
	
	var data;
	var formElement = $(obj).closest("span").find("input[name='formElement']");
	if(formElement.val() == undefined){
		
		var tdIndex = $(obj).closest("td").attr("index");
		var trIndex = $(obj).closest("tr").attr("index");
		formElement = $(obj).closest("table").find("thead").find("tr:eq("+trIndex+")").find("th:eq("+tdIndex+")").find("input[name=formElement]");
	}
	data =  convertToJson($(formElement).val());
	
	$(modal).find("#screenTransitionAutocompleteId").attr("arg03", "");
	$(modal).find("#screenTransitionAutocompleteId").val("");
	$(modal).find("input[name=screenTransition]").val("");
	
	if (data.tabindex != undefined) {
		$(modal).find("input[name=tabindex]").val(data.tabindex);
	} else {
		$(modal).find("input[name=tabindex]").val('');
	}
	
	if (data.navigateTo != undefined && data.navigateTo != null && data.navigateTo.length > 0) {
		$(modal).find("#screenTransitionAutocompleteId").attr("arg03", data.navigateTo);
	}
	
	if (data.screenTransition != undefined && data.screenTransition != null && data.screenTransition.length > 0) {
		$(modal).find("input[name=screenTransition]").val(data.screenTransition);
		$(modal).find("#screenTransitionAutocompleteId").attr("arg04", data.screenTransition);
	}
	
	if (data.screenTransitionText != undefined && data.screenTransitionText != null && data.screenTransitionText.length > 0) {
		$(modal).find("#screenTransitionAutocompleteId").val(data.screenTransitionText);
	}
	
	var buttonTypeText = data["buttonStyle"] + ""; 
	if (data["buttonStyle"] != undefined && data["buttonStyle"] != null && buttonTypeText.length > 0) {
		$(modal).find("input[name='buttonType'][value="+data["buttonStyle"]+"]").prop("checked", true);
	} else {
		$(modal).find("input[name='buttonType'][value=1]").prop("checked", true);
	}
	
	displayStyle(modal, data);
	
	$(modal).find('input[name=isSubmit]').prop('checked', true);
	$(modal).find('input[name=enableConfirm]').prop('checked', false);
	$(modal).find('#enableConfirmGroup').hide();
	$(modal).find('input[name=messageConfirm]').val('');
	$(modal).find('input[name=messageConfirmAutocomplete]').val('');
	
	if (data.enableConfirm !=undefined && data.enableConfirm != null && data.enableConfirm == 1) {
		$(modal).find('input[name=enableConfirm]').prop('checked', true);
		$(modal).find('#enableConfirmGroup').show();
		$(modal).find('input[name=messageConfirm]').val(data["messageConfirm"]);
		$(modal).find('input[name=messageConfirmAutocomplete]').val(data["messageConfirmText"]);
	}
	
	$(modal).find("input[name=isBundle]").prop("checked",data.isBundle);
	
	$(modal).find("input[name='actionName']").val(data.actionName);
	
	if (data.actiontype == 0 || data.actiontype == "0") {
		if(data.navigateTo != undefined && data.navigateTo != null && data.navigateTo.length > 0) {
			$(modal).find("#navigateToScreen").find("input[name='navigateTo']").val(data.navigateTo);
		} else {
			$(modal).find("#navigateToScreen").find("input[name='navigateTo']").val("");
		}
		if(data.navigateToText != undefined && data.navigateToText != null && data.navigateToText.length > 0) {
			$(modal).find("#navigateToScreen").find("input[name='navigateToAutocomplete']").val(htmlDecode(data.navigateToText));
		} else {
			$(modal).find("#navigateToScreen").find("input[name='navigateToAutocomplete']").val("");
		}
		
		if (data.navigateToBlogic != undefined && data.navigateToBlogic != null) {
			$(modal).find('#navigateToScreenBlogic').find("input[name='navigateToBlogic']").val(data.navigateToBlogic);
		} else {
			$(modal).find('#navigateToScreenBlogic').find("input[name='navigateToBlogic']").val("");
		}
		if (data.navigateToBlogicText != undefined && data.navigateToBlogicText != null) {
			$(modal).find('#navigateToScreenBlogic').find("input[name='navigateToBlogicAutocomplete']").val(htmlDecode(data.navigateToBlogicText));
		} else {
			$(modal).find('#navigateToScreenBlogic').find("input[name='navigateToBlogicAutocomplete']").val("");
		}
		
		$(modal).find("#navigateToScreen").show();
		$(modal).find("#navigateToScreenBlogic").show();
		$(modal).find("#navigateToblogic").hide();
		
		
	} else if (data.actiontype == 1 || data.actiontype == "1") {
		$(modal).find("#navigateToblogic").find("input[name='navigateTo']").val(data.navigateTo);
		$(modal).find("#navigateToblogic").find("input[name='navigateToAutocomplete']").val(htmlDecode(data.navigateToText));
		
		
		$(modal).find("#navigateToScreen").hide();
		$(modal).find("#navigateToScreenBlogic").hide();
		$(modal).find("#navigateToblogic").show();
	}
	
	if(data.isSubmit != undefined){
		if(data.isSubmit == "true"){
			
			$(modal).find('#navigateToScreenBlogic').find("input[name='navigateToBlogicAutocomplete']").attr('arg04', 1);
			$(modal).find("input[name=isSubmit]").prop('checked', true);
			$(modal).find("#dialogActionParamSetting").hide();
			setNavigateType(data.actiontype, modal);
			
			if (data.actiontype != undefined && data.actiontype == 1) {
				$(modal).find("input[name=actiontype][value=1]").prop('checked', true);
			} else {
				$(modal).find("input[name=actiontype][value=0]").prop('checked', true);
			}
		}
		else{
			$(modal).find('#navigateToScreenBlogic').find("input[name='navigateToBlogicAutocomplete']").attr('arg04', 0);
			$(modal).find("input[name=isSubmit]").prop('checked', false);
			$(modal).find("#dialogActionParamSetting").show();
			displayParamAction(modal, data);
		}
	}
	else{
		$(modal).find("input[name=isSubmit]").prop('checked', true);
		$(modal).find('#navigateToScreenBlogic').find("input[name='navigateToBlogicAutocomplete']").attr('arg04', 1);
		$(modal).find("#dialogActionParamSetting").show();
		displayParamAction(modal, data);
	}
	$(modal).find("#dialog-form-parameter-tbl-parameter").hide();
	
	$(modal).find("input[name='colspan']").val(data.colspan);
	$(modal).find("input[name='rowspan']").val(data.rowspan);

	var enablegroup = $(obj).closest("td").find("input[name='enableGroup']").val();
	var groupDisplayType = $(obj).closest("td").find("input[name='groupDisplayType']").val();
	if(enablegroup != undefined && enablegroup.length > 0){
		$(modal).find("input[name='enableGroup']").prop("checked",eval(enablegroup));
	}
	else{
		$(modal).find("input[name='enableGroup']").prop("checked",false);
	}
	if (groupDisplayType != undefined && groupDisplayType.length > 0) {		
		$(modal).find("select[name='dialog-button-area-setting-group-display-type'] option[value="+groupDisplayType+"]").attr("selected", "selected");
	}else{
		$(modal).find("select[name='dialog-button-area-setting-group-display-type'] option[value="+1+"]").attr("selected", "selected");
	}
	
	var navigateTo = "";
	if($(modal).find("input[name='navigateTo']").val() != "" && $(modal).find("input[name='navigateTo']").val() != undefined) {
		navigateTo = $(modal).find("input[name='navigateTo']").val();
	}
	$(modal).find("input[id='navigateToBlogicAutocompleteId']").attr("arg01",navigateTo);
	
	setFirstTabActive($(modal));
	
	//Load ajax for navigate
	/*$(obj).closest('table').find("input[name='navigateToBlogic']").val(arr[0]);*/
	/*if (!$(obj).prop('checked')) {
		
		if (screenId != "" && screenId != undefined && screenId != null) {
			url = CONTEXT_PATH + "/screendesign/getBlogicGet?screenId="
					+ screenId + "&r=" + Math.random();

			result = $.qp.getString(url);
			arr = result.split('||');

			if (arr != null && arr.length > 0) {
				$(obj).closest('table').find('#navigateToBlogicAutocompleteId')
						.val(arr[1]);
				$(obj).closest('table').find("input[name='navigateToBlogic']").val(arr[0]);
			}
			$(obj).closest('table').find('#navigateToBlogicAutocompleteId').attr("readOnly",true);
			$(obj).closest('table').find('#navigateToBlogicAutocompleteId').prop('disabled', true);
			$(obj).closest('table').find('#navigateToBlogicAutocompleteId').attr('disabled', true);
			$(obj).closest('table').find('#navigateToBlogicAutocompleteId').addClass('combobox-disabled');
			$(obj).val("")
		}
	} else {
		if (screenId != "" && screenId != undefined && screenId != null) {
			url = CONTEXT_PATH + "/screendesign/getBlogicPost?screenId="
					+ screenId + "&r=" + Math.random();

			result = $.qp.getString(url);
			arr = result.split('||');

			if (arr != null && arr.length > 0) {
				$(obj).closest('table').find('#navigateToBlogicAutocompleteId')
						.val(arr[1]);
				$(obj).closest('table').find("input[name='navigateToBlogic']").val(arr[0]);
			}
		}
	}*/
	
	if (data.label != undefined){
		$(modal).find("input[name=buttonLabelName]").val(data.label);
		
		if (data.label.length == 0) {
			if (data.labelText != undefined && data.labelText != "undefined"){
				$(modal).find("input[name=buttonLabelName]").val(htmlDecode(data.labelText));
			} else {
				$(modal).find("input[name=buttonLabelName]").val('');
			}
		}
	} else {
		if (data.labelText != undefined && data.labelText != "undefined"){
			$(modal).find("input[name=buttonLabelName]").val(htmlDecode(data.labelText));
		} else {
			$(modal).find("input[name=buttonLabelName]").val('');
		}
	}
	if(data.messageLevel == "" || data.messageLevel == undefined || data.messageLevel == null) {
		data.messageLevel = 2
	}
	$(modal).find("#buttonLabelNameAutocompleteId").attr("arg06",data.messageLevel);
	$(modal).find("#buttonLabelNameAutocompleteId").attr("arg08",data.labelText);
	
	var messageLevelText = getMessageLevelText(data.messageLevel);
	$(modal).find("div[class*='level-text']").html(messageLevelText);
	
	if (data.labelText != undefined && data.labelText != "undefined"){
		if (data.labelText.length == 0 && data.label != undefined && data.label != null && data.label.length > 0) {
			$(modal).find("input[name=buttonLabelNameAutocomplete]").val(htmlDecode(data.label) );
		} else {
			$(modal).find("input[name=buttonLabelNameAutocomplete]").val(htmlDecode(data.labelText) );
		}
	} else {
		if (data.label != undefined) {
			$(modal).find("input[name=buttonLabelNameAutocomplete]").val(data.label);
		} else {
			$(modal).find("input[name=buttonLabelNameAutocomplete]").val('');
		}
	}
	
	// check validate, don't validate for button label 
	handleClickLabel();
	
	$(modal).find("input[name=dialog-component-setting-element-width]").val(data.width);
	$(modal).find("input[name=dialog-component-setting-element-width-unit]").val(data.widthunit);
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});
}

function openTableListSetting(obj){
	//var column = convertToJson($(obj).closest("th").find("input").val());
	
	var modal = $("#modal-table-list-setting");
	$(modal).data("data", obj);
	$(modal).find("#dialog-form-table-error").html("");
	$(modal).data("target", obj);
	$(modal).find("[name=tableCaptionAutocomplete]").val($(obj).closest("span").find("[name=formAreaCaptionText]").val());
	$(modal).find("[name=tableCaption]").val($(obj).closest("span").find("[name=formAreaCaptionId]").val());
	$(modal).find("[name=areaCode]").val($(obj).closest("span").find("[name=formAreaCode]").val());
	
	if ($(obj).closest("span").find("[name=formAreaTypeAction]").val() != undefined && $(obj).closest("span").find("[name=formAreaTypeAction]").val() != null 
			&& $(obj).closest("span").find("[name=formAreaTypeAction]").val().length > 0) {
		$(modal).find("input[name=areaTypeActionField][value="+$(obj).closest("span").find("[name=formAreaTypeAction]").val()+"]").prop("checked", true);
		if ($(obj).closest("span").find("[name=formAreaTypeAction]").val() == 0) {
			$(modal).find("[name=fixedRow]").hide();
			$(modal).find("[id=commentFixedRow]").hide();
		} else {
			$(modal).find("[name=fixedRow]").show();
			$(modal).find("[id=commentFixedRow]").show();
		}
	} else {
		$(modal).find("[name=areaTypeActionField][value=0]").prop("checked", true);
		$(modal).find("[name=fixedRow]").hide();
		$(modal).find("[id=commentFixedRow]").hide();
	}
	
	if ($(obj).closest("span").find("[name=formFixedRow]").val() != undefined && $(obj).closest("span").find("[name=formFixedRow]").val() != null && ($(obj).closest("span").find("[name=formFixedRow]").val() == 1 || $(obj).closest("span").find("[name=formFixedRow]").val() == "1")) {
		$(modal).find("[name=fixedRow]").prop('checked', true);
	} else {
		$(modal).find("[name=fixedRow]").prop('checked', false);
	}
	
	openAreaStyle(modal, obj);
	
	var width = $(obj).closest("span").find("input[name=formTableWidth]").val();
	if(width == undefined || width.length == 0){
		width = 100;
		$(modal).find("[name=tableWidthUnit]").val("%");
	}else{
		if(width.indexOf("%") != -1){
			width = width.substring(0, width.length - 1);
			$(modal).find("[name=tableWidthUnit]").val("%");
		}
		if(width.indexOf("px") != -1){
			width = width.substring(0, width.length - 2);
			$(modal).find("[name=tableWidthUnit]").val("px");
		}
	}
	
	$(modal).find("[name=tableWidth]").val(width);
	$(modal).find("[name=tablePosition]").val($(obj).closest("span").find("[name=formTablePosition]").val());
	
	var table = $(obj).closest("span").closest("div").next("div").find("table").first();
	$(modal).find("#trHeaderRow").remove();
	
	$(modal).find("#trColWidthSetting").remove();
	var input = "";
	var tableColumnSize = $(obj).closest("span").closest("div").find("input[name=formTableColumnSize]").val();
	var formTableColumnsSize=[];
	
	var empty = tableColumnSize.replace(/,/g, "");
	if(tableColumnSize == undefined || tableColumnSize.length ==0 || empty.length == 0){
		$(obj).parents("div[class=areaContent]").find("table > colgroup").find("col").each(function (index){
			formTableColumnsSize[index] = $(this).prop("width");
		});
	}else{
		formTableColumnsSize = tableColumnSize.split(",");
	}
	if($(table).attr("class") != undefined && $(table).attr("class") == "table table-bordered qp-table-list-none-action"){
		$(modal).find("#tableWidthUnitPosition").after(returnColWidth());
		$(modal).find("#equitableColumnWidth").attr("colLength", $(table).find("tr:eq(0)").find("th").length);
		$(modal).find("#columnnameSize").html("");
		for(i=0;i<$(table).find("tr:eq(0)").find("th").length; i++){
			if(formTableColumnsSize[i] != undefined && formTableColumnsSize[i].length > 0){
				var colWidth = formTableColumnsSize[i];
				var colWidthVal = "";
				var colWidthUnit = "";
				if(colWidth.indexOf("%") != -1){
					colWidthVal = colWidth.substring(0, colWidth.length - 1);
					colWidthUnit = "%";
				}
				if(colWidth.indexOf("px") != -1){
					colWidthVal = colWidth.substring(0, colWidth.length - 2);
					colWidthUnit = "px";
				}
				input = "<div style=\"width:100%;float: left\"><div style=\"width:100px;float: left;margin-left: 4px;\"><input value=\"" + colWidthVal + "\" type=\"text\" class=\"qp-input-integer qp-numeric-up-down\" name=\"columnnameSize\"/></div><div style=\"float: left;margin-left: 4px;\">"+returnUnitSlection()+"</div><div style=\"float: left;margin-left: 4px;\">("+(i+1)+")</div></div>";
				$(modal).find("#columnnameSize").append(input);
				$(modal).find("#columnnameSize").find("select[name=columnnameSizeUnit]:last").val(colWidthUnit);
			} else {
				input = "<div style=\"width:100%;float: left\"><div style=\"width:100px;float: left;margin-left: 4px;\"><input type=\"text\" class=\"qp-input-integer qp-numeric-up-down\" name=\"columnnameSize\"/></div><div style=\"float: left;margin-left: 4px;\">"+returnUnitSlection()+"</div><div style=\"float: left;margin-left: 4px;\">("+(i+1)+")</div></div>";
				$(modal).find("#columnnameSize").append(input);
			}
		}
		
		$("#uniqueList").show();
	}
	
	$(modal).find("[name=headerLabelRow]").val($(obj).closest("span").find("[name=formHeaderLabelRow]").val());
	$(modal).find("[name=headerComponentRow]").val($(obj).closest("span").find("[name=formHeaderComponentRow]").val());
	
	$(modal).find("#tbl-hiddenAttibutes").find("tbody").empty();
	var parameters = $(obj).closest("span").find("[name=formElementHidden]").val();
	if(parameters != undefined && parameters.length > 0){
		var arrParameters = parameters.split("�");
		$(modal).find("#tbl-hiddenAttibutes").find("tbody").empty();
		$(arrParameters).each(function(i){
			var dataParameter = arrParameters[i].split("π");
			$.qp.addRowJSByLink($(modal).find("#tbl-hiddenAttibutes").next().find("a"));
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:last").find("input[name=parameterAttribute]").val(dataParameter[0]);
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:last").find("input[name=labelName]").val(dataParameter[1]);
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:last").find("input[name=labelNameAutocomplete]").val(dataParameter[2]);
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:last").find("input[name=screenItemStoreId]").val(dataParameter[3]);
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:last").find("[name=parameterDatatype]").val(dataParameter[4]);
			
		});
	} else {
		/*$.qp.addRowJSByLink($(modal).find("#tbl-hiddenAttibutes").next().find("a"));
		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:last").find("input[name=parameterAttribute]").val("");
		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:last").find("input[name=labelName]").val("");
		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:last").find("input[name=labelNameAutocomplete]").val("");*/
	}
	$.qp.initialAllPicker($(modal).find("#trColWidthSetting"));
	
	if($(modal).find("#modal-table-list-setting-form").length >0){
		initSelectFormIndex(obj,$(modal).find("#modal-table-list-setting-form"));
	}
	$(modal).find('input[name=formcode]').val($(obj).closest('.form-area-content').find('.form-content').find('input[name=screenFormFormActionCode]').val());
	openEventArea(modal, obj);
	setFirstTabActive($(modal));
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});
}

function openTableSetting(obj){
//var column = convertToJson($(obj).closest("th").find("input").val());
	
	var modal = $("#modal-table-setting");
	$(modal).data("data", obj);
	$(modal).find("#dialog-form-table-error").html("");
	$(modal).data("target", obj);
	$(modal).find("[name=tableCaptionAutocomplete]").val($(obj).closest("span").find("[name=formAreaCaptionText]").val());
	$(modal).find("[name=tableCaption]").val($(obj).closest("span").find("[name=formAreaCaptionId]").val());
	$(modal).find("[name=areaCode]").val($(obj).closest("span").find("[name=formAreaCode]").val());
	
	if ($(obj).closest("span").find("[name=areaCustomType]").val() != undefined && $(obj).closest("span").find("[name=areaCustomType]").val() != null 
			&& $(obj).closest("span").find("[name=areaCustomType]").val().length > 0) {
		if ($(obj).closest("span").find("[name=areaCustomType]").val() == 0) {
			$(modal).find("[name=areaCustomTypeField][value="+$(obj).closest("span").find("[name=areaCustomType]").val()+"]").prop("checked", true);
		} else {
			$(modal).find("[name=areaCustomTypeField][value="+$(obj).closest("span").find("[name=areaCustomType]").val()+"]").prop("checked", true);
		}
	} else {
		$(modal).find("[name=areaCustomTypeField][value=0]").prop("checked", true);
	}
	
	var width = $(obj).closest("span").find("input[name=formTableWidth]").val();
	
	openAreaStyle(modal, obj);
	
	if(width == undefined || width.length == 0){
		width = 100;
		$(modal).find("[name=tableWidthUnit]").val("%");
	}else{
		if(width.indexOf("%") != -1){
			width = width.substring(0, width.length - 1);
			$(modal).find("[name=tableWidthUnit]").val("%");
		}
		if(width.indexOf("px") != -1){
			width = width.substring(0, width.length - 2);
			$(modal).find("[name=tableWidthUnit]").val("px");
		}
	}
	$(modal).find("[name=tableWidth]").val(width);
	$(modal).find("[name=tablePosition]").val($(obj).closest("span").find("[name=formTablePosition]").val());
	
	var table = $(obj).closest("span").closest("div").next("div").find("table").first();
	$(modal).find("#trHeaderRow").remove();
	$(modal).find("#trColWidthSetting").remove();
	var input = "";
	var tableColumnSize = $(obj).closest("span").closest("div").find("input[name=formTableColumnSize]").val();
	var formTableColumnsSize=[];
	
	var empty = tableColumnSize.replace(/,/g, "");
	if(tableColumnSize == undefined || tableColumnSize.length ==0  || empty.length == 0){
		$(obj).parents("div[class=areaContent]").find("table > colgroup").find("col").each(function (index){
			formTableColumnsSize[index] = $(this).prop("width");
		});
	}else{
		formTableColumnsSize = tableColumnSize.split(",");
	}
	if($(table).attr("class") != undefined && $(table).attr("class") == "table table-bordered qp-table-form"){
		$(modal).find("#tableWidthUnitPosition").after(returnColWidth());
		var countCols = ($(table).find("tbody").find("tr:eq(0)").find("td").length - 2) * 2;
		$(modal).find("#equitableColumnWidth").attr("colLength", countCols);
		$(modal).find("#columnnameSize").html("");
		for(i=0;i<countCols; i++){
			if(formTableColumnsSize[i] != undefined && formTableColumnsSize[i].length > 0){
				var colWidth = formTableColumnsSize[i];
				var colWidthVal = "";
				var colWidthUnit = "";
				if(colWidth.indexOf("%") != -1){
					colWidthVal = colWidth.substring(0, colWidth.length - 1);
					colWidthUnit = "%";
				}
				if(colWidth.indexOf("px") != -1){
					colWidthVal = colWidth.substring(0, colWidth.length - 2);
					colWidthUnit = "px";
				}
				input = "<div style=\"width:100%;float: left\"><div style=\"width:100px;float: left;margin-left: 4px;\"><input value=\"" + colWidthVal + "\" type=\"text\" class=\"qp-input-integer qp-numeric-up-down\" name=\"columnnameSize\"/></div><div style=\"float: left;margin-left: 4px;\">"+returnUnitSlection()+"</div><div style=\"float: left;margin-left: 4px;\">("+(i+1)+")</div></div>";
				$(modal).find("#columnnameSize").append(input);
				$(modal).find("#columnnameSize").find("select[name=columnnameSizeUnit]:last").val(colWidthUnit);
			} else {
				input = "<div style=\"width:100%;float: left\"><div style=\"width:100px;float: left;margin-left: 4px;\"><input type=\"text\" class=\"qp-input-integer qp-numeric-up-down\" name=\"columnnameSize\"/></div><div style=\"float: left;margin-left: 4px;\">"+returnUnitSlection()+"</div><div style=\"float: left;margin-left: 4px;\">("+(i+1)+")</div></div>";
				$(modal).find("#columnnameSize").append(input);
			}
		}
		if($(modal).find("#trHeaderRow").length){
			$(modal).find("#trHeaderRow").remove();
		}
		$(modal).find("#uniqueList").hide();
	}
	//comment prototype
	$(modal).find("#tbl-hiddenAttibutes").find("tbody").empty();
	var parameters = $(obj).closest("span").find("[name=formElementHidden]").val();
	if(parameters != undefined && parameters.length > 0){
		var arrParameters = parameters.split("�");
		$(modal).find("#tbl-hiddenAttibutes").find("tbody").empty();
		$(arrParameters).each(function(i){
			var dataParameter = arrParameters[i].split("π");
			$.qp.addRowJSByLink($(modal).find("#tbl-hiddenAttibutes").next().find("a"));
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:last").find("input[name=parameterAttribute]").val(dataParameter[0]);
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:last").find("input[name=labelName]").val(dataParameter[1]);
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:last").find("input[name=labelNameAutocomplete]").val(dataParameter[2]);
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:last").find("input[name=screenItemStoreId]").val(dataParameter[3]);
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:last").find("[name=parameterDatatype]").val(dataParameter[4]);
			
		});
	} else {
		/*$.qp.addRowJSByLink($(modal).find("#tbl-hiddenAttibutes").next().find("a"));
		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:last").find("input[name=parameterAttribute]").val("");
		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:last").find("input[name=labelName]").val("");
		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:last").find("input[name=labelNameAutocomplete]").val("");*/
	}
	$.qp.initialConventionNameCode();
	$.qp.initialAllPicker($(modal).find("#trColWidthSetting"));

	$(modal).find('input[name=formcode]').val($(obj).closest('.form-area-content').find('.form-content').find('input[name=screenFormFormActionCode]').val());
	
	openEventArea(modal, obj);
	
	if($(modal).find("#modal-table-setting-form").length>0){
		initSelectFormIndex(obj,$(modal).find("#modal-table-setting-form"));
	}
	setFirstTabActive($(modal));
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});
}

function openDialogComponentListSetting(obj) {
	var modal = $("#dialog-component-list-setting");
	//Setting default check data source type
	$(modal).find("div[id=dialog-component-list-setting-codelist]").find("input[name='dataSourceType'][value=1]").prop('checked',true);
	$(modal).find("div[id=dialog-component-list-setting-codelist]").find("input[name='dataSourceType'][value=2]").prop('checked',false);
	$(modal).find("input:radio[name=localCodelist][value=1]").closest("label").show();
	$(modal).find("input:radio[name=localCodelist][value=3]").closest("label").show();
	$(modal).data("target", obj);
	$(modal).data("data",  $(obj).closest("span").find("input[name='formElement']").val());
	$(modal).find("#dialog-form-options-error").html("");
	
	var data;
	var formElement = $(obj).closest("span").find("input[name='formElement']");
	if(formElement.length == 0 || formElement.val() == undefined){
		
		var tdIndex = $(obj).closest("td").attr("index");
		var trIndex = $(obj).closest("tr").attr("index");
		formElement = $(obj).closest("table").find("thead").find("tr:eq("+trIndex+")").find("th:eq("+tdIndex+")").find("input[name=formElement]");
	}
	data =  convertToJson($(formElement).val());
	$(modal).find("td[basetype='dialog-component-list-setting-base-types']").html(setBaseType(data));
	$(modal).find("input[name=columnname]").val(data.columnname);
	
	displayStyle(modal, data);
	if (data.datatype != undefined && data.datatype != null && data.datatype == 7) {
		$(modal).find('#showBlankItemLayout').show();
		if (data.showBlankItem != undefined && data.showBlankItem != null && data.showBlankItem == 1) {
			$(modal).find("input[name='showBlankItem']").prop('checked', true);
		} else {
			$(modal).find("input[name='showBlankItem']").prop('checked', false);
		}
	} else {
		$(modal).find('#showBlankItemLayout').hide();
	}
	
	var localCodelist = data.localCodelist;
	if(localCodelist == undefined || localCodelist.length == 0){
		localCodelist = 1;
	}
	
	openProperties(modal, data);
	
	showDataSourceInView(modal, data);
	
	if (data.mandatory != undefined) {
		if (data.mandatory == "true") {
			$(modal).find("input[name='mandatory']").prop("checked", true);
		} else {
			$(modal).find("input[name='mandatory']").prop("checked", false);
		}
	} else {		
		$(modal).find("input[name='mandatory']").prop("checked", false);
	}
	
	if(data.physicaldatatype != undefined){
		$(modal).find("select[name=baseType]").val(data.physicaldatatype);
	}
	$(modal).find("input[name='colspan']").val(data.colspan);
	$(modal).find("input[name='rowspan']").val(data.rowspan);
	if (data.datatype != 7) {
		$(modal).find("[name='dialog-component-list-setting-width']").closest('tr').hide();
	} else {
		$(modal).find("[name='dialog-component-list-setting-width']").closest('tr').show();
	}
	var enablegroup = $(obj).closest("td").find("input[name='enableGroup']").val();
	var groupDisplayType = $(obj).closest("td").find("input[name='groupDisplayType']").val();
	if(enablegroup != undefined && enablegroup.length > 0){
		$(modal).find("input[name='enableGroup']").prop("checked",eval(enablegroup));
	}
	else{
		$(modal).find("input[name='enableGroup']").prop("checked",false);
	}
	/*if (groupDisplayType != undefined && groupDisplayType.length > 0) {		
		$(modal).find("select[name='dialog-component-list-setting-group-display-type'] option[value="+groupDisplayType+"]").attr("selected", "selected");
	}else{
		$(modal).find("select[name='dialog-component-list-setting-group-display-type'] option[value="+1+"]").attr("selected", "selected");
	}*/
	if (groupDisplayType != undefined && groupDisplayType != '' && groupDisplayType.length > 0) {		
		$(modal).find("select[name='dialog-component-list-setting-group-display-type']").val(groupDisplayType);
	}else{
		$(modal).find("select[name='dialog-component-list-setting-group-display-type']").val(1);
	}
	if (data.width != undefined && data.width != 'undefined' && data.width != "" && data.width.length >0 && data.width != "0") {		
		$(modal).find("input[name='dialog-component-list-setting-width']").val(data.width);
	} else {		
		$(modal).find("input[name='dialog-component-list-setting-width']").val("100");
	}
	if (data.width != undefined && data.width != 'undefined' && data.width != '') {		
		$(modal).find("[name='dialog-component-list-setting-width-unit']").val(data.widthunit);
	} else {		
		$(modal).find("[name='dialog-component-list-setting-width-unit']").val("%");
	}
	
	//init event
	if (data.events != undefined && data.events != null) {
		loadActionListComponent($(modal), data);
	} else {
		$(modal).find('#listEvent').empty();
		$(modal).find('#actionType').find('option:eq(0)').prop("selected", "selected");
	}
	
	showElementToChange($(modal), data);
	$.qp.initialConventionNameCode();
	setFirstTabActive($(modal));
	
	if (data.physicaldatatype != undefined && data.physicaldatatype != null && data.physicaldatatype == 8) {
		$(modal).find('.modal-body').find('.nav-tabs').find('li:eq(1)').hide();
		$(modal).find('td[basetype=dialog-component-list-setting-base-types]').closest("tr").show();
		$(modal).find('input[name=elementType]').closest('.qp-div-information').hide();
	} else {
		$(modal).find('.modal-body').find('.nav-tabs').find('li:eq(1)').show();
		$(modal).find('td[basetype=dialog-component-list-setting-base-types]').closest("tr").show();
		$(modal).find('input[name=elementType]').closest('.qp-div-information').show();
	}
	
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});
}


function openDialogComponentSetting(obj){
	
	var table = $(obj).closest("table");
	var tableType = $(table).attr('type');
	var modal = $("#dialog-component-setting");
	
	$(modal).find("#dialog-form-options-error").html("");	
	var value = $(obj).closest("span").find("input[name='formElement']").val();
	var column = convertToJson(value);
	var formAreaType = $(obj).parent().closest("div").parent().find('input[name=formAreaType]').val();
	var formAreaCustomType = $(obj).parent().closest("div").parent().find('input[name=areaCustomType]').val();
	var itemsBaseType = CL_PRIMITIVE_DATATYPE;

	displayStyle(modal, column);
	
	$(modal).data("target", obj);
	$(modal).data("data", value);
	
	$(modal).find("#trRequire").show();
	if(column.datatype == 20 || column.datatype == 11 || column.datatype == 13){
		$(modal).find("#trRequire").hide();
	}
	
	$(modal).find("#trMinMaxValue").remove();
	$(modal).find("#trMaxlength").remove();
	$(modal).find("#trCheckString").remove();
	$(modal).find("#trWidth").remove();
	
	//fill base properties
	if (column.columnname != undefined) {
		$(modal).find("input[name='columnname']").val(column.columnname);
	} else {
		$(modal).find("input[name='columnname']").val("");
	}
	
	if (column.mandatory != undefined) {
		if (column.mandatory == "true") {
			$(modal).find("input[name='mandatory']").prop("checked", true);
		} else {
			$(modal).find("input[name='mandatory']").prop("checked", false);
		}
	} else {		
		$(modal).find("input[name='mandatory']").prop("checked", false);
	}	
	
	var enableGroup = $(obj).closest("td").find("input[name='enableGroup']").val(); 
	
	if (enableGroup != undefined && enableGroup == "true") {		
		$(modal).find("input[name='enableGroup']").prop("checked", true);
	} else {		
		$(modal).find("input[name='enableGroup']").prop("checked", false);
	}
	
	if(column.datatype == 21){
		
		$(modal).find("#trRequire").css("display","none");
		$(modal).find("#trRequire").prev("tr").css("display","none");
		
		showDataSourceInView(modal, column);
		$(modal).find("a[href=#dialog-component-setting-codelist]").closest('li').show();
		if(column.datasourcetype == undefined || column.datasourcetype == "") {
			$(modal).find("#dialog-component-setting-codelist").find("input[name='dataSourceType'][value='1']").prop("checked",true);
			$(modal).find("#dialog-component-setting-codelist").find("#accordion").hide();
		}
	} else {
		$(modal).find("a[href=#dialog-component-setting-codelist]").closest('li').hide();
	}
	
	var groupDisplayType = $(obj).closest("td").find("input[name='groupDisplayType']").val(); 
	if (groupDisplayType != undefined && groupDisplayType != '' && groupDisplayType.length > 0) {		
		$(modal).find("select[name='dialog-component-setting-group-display-type']").val(groupDisplayType);
	}else{
		$(modal).find("select[name='dialog-component-setting-group-display-type']").val(1);
	}
	if (column.width != undefined && column.width != null && column.width.length > 0) {		
		$(modal).find("input[name='dialog-component-setting-element-width']").val(column.width);
	} else {		
		$(modal).find("input[name='dialog-component-setting-element-width']").val("100");
	}
	
	if (column.widthunit != undefined && column.widthunit != null && column.widthunit.length > 0 && column.widthunit != 'undefined') {		
		$(modal).find("[name='dialog-component-setting-element-width-unit']").val(column.widthunit);
	} else {		
		$(modal).find("[name='dialog-component-setting-element-width-unit']").val("%");
	}
	
	if (column.rowspan != undefined) {
		$(modal).find("input[name='rowspan']").val(column.rowspan);
	} else {
		$(modal).find("input[name='rowspan']").val("");
	}
	
	if (column.colspan != undefined) {
		$(modal).find("input[name='colspan']").val(column.colspan);
	} else {
		$(modal).find("input[name='colspan']").val("");
	}
	
	if (column.validateRule != undefined) {
		$(modal).find("input[name='validateRule']").val(column.validateRule);
	} else {
		$(modal).find("input[name='validateRule']").val("");
	}
	
	var minValue = "";
	var maxValue = "";
	if (column.minvalue != undefined) {
		minValue = column.minvalue;
	} 
	
	if (column.maxvalue != undefined) {
		maxValue = column.maxvalue;
	} 
	
	var arr = [];
	
	
	/*var strBaseType = $(modal).find("td[basetype='dialog-component-setting-base-types']").find("select[name='baseType']");
	$(modal).find("td[basetype='dialog-component-setting-base-types']").html(strBaseType);*/
	
	//set basetype
	$(modal).find("#baseStypeDynamicLabel").hide();
	$(modal).find("td[basetype='dialog-component-setting-base-types']").html(setBaseType(column));
	if (column.datatype == 21) {
		$(modal).find("select[name='baseType'] option").remove();
		var options = ""
		for (var i = 1; i <= parseInt(itemsBaseType['total']); i++) {
			options+= "<option value=\""+i+"\"><lable>"+itemsBaseType[i]+"</label></option>";
		}
		$(modal).find("#baseStypeDynamicLabel").show();
		$(modal).find("select[name='baseType']").append(options);
	} else {
		$(modal).find("#baseStypeDynamicLabel").hide();
	}
	
	if (column.physicaldatatype != undefined) {
		$(modal).find("select[name='baseType'] option[value='"+column.physicaldatatype+"']").attr("selected","selected");
	} else {
		$(modal).find("select[name='baseType']").val("");
	}
	openProperties(modal, column);
	//display datatype	
	if(column.datatype == 1 || column.datatype == 10 || column.datatype == 15 || column.datatype == 16 || column.datatype == 18){
				
		//fill properties
		if (column.maxlength != undefined) {
			$(modal).find("input[name='maxlength']").val(column.maxlength);
		} else {
			$(modal).find("input[name='maxlength']").val("");
		}
		
		if(column.physicalmaxlength.length == 0){
			column.physicalmaxlength = returnPhysicalMaxlenth(column.datatype);
		} 
		$(modal).find("#trRequire").before("<tr id=\"trMaxlength\"><th class=\"com-table-th-text style-header-table\">"+dbMsgSource['sc.screendesign.0127']+" <label class=\"qp-required-field\">"+fcomMsgSource['sc.sys.0029']+"</label></th>" +
				"<td><input type=\"text\" class=\"form-control qp-input-serial\" style=\"width: 100px; float: left;\" name=\"maxlength\" maxlength=\"4\"/><div style=\"float: left;\" class=\"vertical-middle\"></div></td></tr>");
		var trCheckString = "<tr id=\"trCheckString\"><th class=\"com-table-th-text style-header-table\">"+dbMsgSource['sc.screendesign.0133']+"</th>" +
				"<td><select name=\"validateRule\" class=\"form-control qp-input-select\">";
		var trOptionValidation = "<option value=\"\">&nbsp;</option>";
		for(var i in CL_QP_VALIDATION)
		{
			trOptionValidation += "<option value=\""+i+"\">"+CL_QP_VALIDATION[i]+"</option>";
		}
		trCheckString += trOptionValidation;
		trCheckString += "</select></td></tr>";
		$(modal).find("#trRequire").before(trCheckString);
		
		var currentBaseType = getBasetype(column);
		
		$("#baseStypeNormal").find("td").html(currentBaseType.baseType);
		$(modal).find("input[name=maxlength]").attr("value",column.maxlength);
		$(modal).find("select[name=validateRule]").val(column.formatcode);
		
		//fill data
		if(column.datatype != 21 && column.datatype != 1){
			$(modal).find("select[name=validateRule]").prop("disabled",true);
		}
	}
	if(column.datatype == 2){		
		$(modal).find("#trRequire").before("<tr id=\"trMinMaxValue\"><th class=\"com-table-th-text style-header-table\">"+dbMsgSource['sc.screendesign.0134']+"</th>" +
				"<td><input type=\"text\" datatype=\"number\" class=\"form-control qp-input-from-integer pull-left\" name=\"minValue\" value=\""+minValue+"\" /><div class=\"qp-separator-from-to\">~</div><input type=\"text\" datatype=\"number\" value=\""+minValue+"\" class=\"form-control qp-input-from-integer pull-right\" name=\"maxValue\" /></td></tr>");
	}
	if(column.datatype == 3 || column.datatype == 8){
		$(modal).find("#trRequire").before("<tr id=\"trMinMaxValue\"><th>"+dbMsgSource['sc.screendesign.0134']+"</th>" +
				"<td><input type=\"text\" datatype=\"number\" class=\"form-control qp-input-from-float pull-left\" name=\"minValue\" value=\""+minValue+"\" /><div class=\"qp-separator-from-to\">~</div><input datatype=\"number\" value=\""+maxValue+"\" type=\"text\" class=\"form-control qp-input-to-float pull-right\" name=\"maxValue\" /></td></tr>");
	}
	if(column.datatype == 4){
		$(modal).find("#trRequire").before("<tr id=\"trMinMaxValue\"><th class=\"com-table-th-text style-header-table\">"+dbMsgSource['sc.screendesign.0134']+"</th>" +
				"<td>"
				+ "<div class=\"input-group date qp-input-from-datepicker pull-left\">"
				+ 		"<input datatype=\"date\" type=\"text\"name=\"minValue\" value=\""+minValue+"\" class=\"form-control\" />"
				+ 		"<span class=\"input-group-addon\">"
				+ 			"<span class=\"glyphicon glyphicon-calendar\"></span>"
				+ 		"</span>"
				+ "</div>"
				+ "<div class=\"qp-separator-from-to\">~</div>"
				+ "<div class=\"input-group date qp-input-to-datepicker pull-rigth\">"
				+ 		"<input type=\"text\" name=\"maxValue\"  value=\""+maxValue+"\" class=\"form-control\" />"
				+ 		"<span class=\"input-group-addon\">"
				+ 			"<span class=\"glyphicon glyphicon-calendar\"></span>"
				+ 		"</span>"
				+ "</div>"
				+"</td></tr>");
	}
	if(column.datatype == 14){
		$(modal).find("#trRequire").before("<tr id=\"trMinMaxValue\"><th class=\"com-table-th-text style-header-table\">"+dbMsgSource['sc.screendesign.0134']+"</th>" +
				"<td>" 
					+ "<div class=\"input-group date qp-input-from-datetimepicker-detail pull-left\">"
					+ 		"<input datatype=\"date\" type=\"text\"name=\"minValue\" value=\""+minValue+"\" class=\"form-control\" />"
					+ 		"<span class=\"input-group-addon\">"
					+ 			"<span class=\"glyphicon glyphicon-calendar\"></span>"
					+ 		"</span>"
					+ "</div>"
					+ "<div class=\"qp-separator-from-to\">~</div>"
					+ "<div class=\"input-group date qp-input-to-datetimepicker-detail pull-rigth\">"
					+ 		"<input type=\"text\" name=\"maxValue\" value=\""+maxValue+"\" class=\"form-control\" />"
					+ 		"<span class=\"input-group-addon\">"
					+ 			"<span class=\"glyphicon glyphicon-calendar\"></span>"
					+ 		"</span>"
					+ "</div>"
				+"</td></tr>");
	}
	if(column.datatype == 9){
		$(modal).find("#trRequire").before("<tr id=\"trMinMaxValue\"><th class=\"com-table-th-text style-header-table\">"+dbMsgSource['sc.screendesign.0134']+"</th>" +
				"<td>"
				+ "<div class=\"input-group date qp-input-from-timepicker pull-left\">"
				+ 		"<input datatype=\"date\" type=\"text\"name=\"minValue\" value=\""+minValue+"\" class=\"form-control\" />"
				+ 		"<span class=\"input-group-addon\">"
				+ 			"<span class=\"glyphicon glyphicon-time\"></span>"
				+ 		"</span>"
				+ "</div>"
				+ "<div class=\"qp-separator-from-to\">~</div>"
				+ "<div class=\"input-group date qp-input-to-timepicker pull-rigth\">"
				+ 		"<input datatype=\"date\" type=\"text\" name=\"maxValue\"  value=\""+maxValue+"\" class=\"form-control\" />"
				+ 		"<span class=\"input-group-addon\">"
				+ 			"<span class=\"glyphicon glyphicon-time\"></span>"
				+ 		"</span>"
				+ "</div>"
				+ "</td></tr>");
	}
	// daipv: check and display from to
	var stringDisplayFromto = "<tr id=\"trWidth\"><th>"+dbMsgSource['sc.screendesign.0107']+"</th><td baseType=\"dialog-component-setting-display-types\">" +
									"<input type=\"radio\" id=\"radio0\" value=\"0\" class=\"qp-input-radio qp-input-radio-margin\" name=\"displayTypeFromTo\" /><label for=\"radio0\">"+dbMsgSource['sc.screendesign.0142']+"</label>" +
								    "<input type=\"radio\" id=\"radio1\" value=\"1\" class=\"qp-input-radio qp-input-radio-margin\" name=\"displayTypeFromTo\" /><label for=\"radio1\">"+dbMsgSource['sc.screendesign.0490']+"</label>" +
								"</td></tr>";
	if (tableType != undefined && tableType == "search" && (column.datatype == 9 || column.datatype == 8 || column.datatype == 4 || column.datatype == 14 || column.datatype == 2 || column.datatype == 3) && formAreaType == "0" && (formAreaCustomType == "0" || formAreaCustomType == "")) {
		$(modal).find("#widthDisplay").before(stringDisplayFromto);
	}
	// daipv: check for checked radio
	var displayFromTo = column["displayFromTo"] + "";
	
	if (column["displayFromTo"] != undefined && column["displayFromTo"] != null && displayFromTo.length > 0) {
		$(modal).find("input[name='displayTypeFromTo'][value="+column["displayFromTo"]+"]").prop("checked", true);
	} else {
		if (tableType != undefined && tableType == "search") {
			$(modal).find("input[name='displayTypeFromTo'][value=1]").prop("checked", true);
		} else {
			$(modal).find("input[name='displayTypeFromTo'][value=0]").prop("checked", true);
		}
	}
	
	$(modal).find("input[name=minValue]").val(column.minvalue);
	$(modal).find("input[name=maxValue]").val(column.maxvalue);
		
	if(column.require == 1){
		$(modal).find("input[name=require]").prop('checked', true);
	} else {
		$(modal).find("input[name=require]").prop('checked', false);
	}
	
	//add radio event
	addRadioChooseMappingOutput(modal, obj, column);
	
	$(modal).find("input[name=label]").val(column.label);
	if(column.columnname != undefined && column.columnname.length > 0){
		$(modal).find("input[name=columnname]").val(column.columnname);
	}
	
	if(column.tablecolumncode != undefined){
		$(modal).find("input[name=dbColumcode]").val(column.tablecolumncode);
		$(modal).find("input[name=dbColumcodeAutocomplete]").val(column.tablecolumnname);
		$(modal).find("input[name=dbTablecode]").val(column.tablecode);
		$(modal).find("input[name=dbTablecodeAutocomplete]").val(column.tablename);
	}
	
	$(modal).find("span#datatype").text(returnDataType(column.datatype));
	
	
	$(modal).find("input[name=elementRowspan]").val("");
	$(modal).find("input[name=elementColspan]").val("");
	$(modal).find("input[name=elementRowspan]").closest("tr").hide();
	$(modal).find("input[name=elementColspan]").closest("tr").hide();
	
	var tr = $(obj).closest("tr");
	var th = $(obj).closest("th");
	
	if($(table).attr("class") == "table table-bordered qp-table-form"){
		if(column.colspan != undefined && column.colspan.length > 0 && column.colspan > 1){
			$(modal).find("input[name=elementColspan]").closest("tr").show();
			if(column.colspan != undefined && column.colspan.length > 0){
				$(modal).find("input[name=elementColspan]").val(column.colspan);
			}
		} 
		if(column.rowspan != undefined && column.rowspan.length > 0 && column.rowspan > 1){
			$(modal).find("input[name=elementRowspan]").closest("tr").show();
			if(column.rowspan != undefined && column.rowspan.length > 0){
				$(modal).find("input[name=elementRowspan]").val(column.rowspan);
			}
		} 
		var colSize = $(tr).find("th").length;
		var colIndex = ($(tr).children().index($(th)) + 2) / 2;
		var rowSize = $(table).find("tbody").find("tr").length;
		var rowIndex = $(table).find("tbody").children().index($(tr));
		if(colIndex != colSize){
			$(modal).find("input[name=elementColspan]").closest("tr").show();
		}
		if(rowIndex < (rowSize - 1)){
			$(modal).find("input[name=elementRowspan]").closest("tr").show();
		}
		
	}
	if($(table).attr("class") == "table table-bordered qp-table-form"){
		$(modal).find("input[name=elementColspan]").closest("tr").hide();
		
		if($(table).find("thead").find("tr").length > 1){
			$(modal).find("input[name=elementRowspan]").closest("tr").show();
			$(modal).find("input[name=elementRowspan]").val(column.rowspan);
		}
	}
	if(column.datatype == 21){
		$(modal).find('input[name=tabindex]').closest("tr").hide();
	} else {
		$(modal).find('input[name=tabindex]').closest("tr").show();
	}
	
	$.qp.initialConventionNameCode();
	$.qp.initialAllPicker($(modal));
	$.qp.initialAutoNumeric($(modal));
	setFirstTabActive($(modal));
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});	
}

function openDialogLableSetting(obj, isComponent){		
	var table = $(obj).closest("table");
	var modal = $('#dialog-label-setting');
	
	var value = $(obj).closest("th").find("input[name=formElement]").val();
	
	if (isComponent != undefined && isComponent != null && isComponent) {
		value = $(obj).next().val();
		$(modal).find('input[name=mandatory]').closest("tr").hide();
	} else {
		$(modal).find('input[name=mandatory]').closest("tr").show();
	}
	
	
	$(modal).data("target", obj);
	$(modal).data("data", value);
	$(modal).data("isComponent", isComponent);
		
	var data = convertToJson(value);
	displayStyle(modal, data);
	if (data.isBundle != undefined) {
		$(modal).find("input[name='isBundle']").val(data.isBundle);
	} else {		
		$(modal).find("input[name='isBundle']").val("false");
	}
	
	/*if(data.datatype == 20){
		if ((data.dialogAutocompleteCode == undefined || data.dialogAutocompleteCode.length == 0)) {
			showDataSourceInView(modal, data);
			$(modal).find("a[href=#dialog-label-setting-codelist]").closest('li').show();
		} else {
			$(modal).find("a[href=#dialog-label-setting-codelist]").closest('li').hide();
		}
	} else {
		$(modal).find("a[href=#dialog-label-setting-codelist]").closest('li').hide();
	}*/
		
	if (data.mandatory != undefined && data.mandatory != '' && data.mandatory != false) {
		$(modal).find("input[name='mandatory']").prop("checked",eval(data.mandatory));
	} else {		
		$(modal).find("input[name='mandatory']").prop("checked",false);
	}	
	
	if (data.rowspan != undefined) {
		$(modal).find("input[name='rowspan']").val(data.rowspan);
	} else {				
		$(modal).find("input[name='rowspan']").val("");
	}
	
	if (data.colspan != undefined) {
		$(modal).find("input[name='colspan']").val(data.colspan);
	} else {				
		$(modal).find("input[name='colspan']").val("");
	}
	
	if (data.isBlank != undefined) {
		$(modal).find("input[name='isBlank']").prop('checked', eval(data.isBlank));
	} else {				
		$(modal).find("input[name='isBlank']").prop('checked', false);
	}	

	// daipv: Begin add [width, widthunit] for [Label]

	if($(obj).closest("th") != null && $(obj).closest("th").length == 0) {
		$(modal).find("#widthLabelDisplay").removeAttr("style").show();
		if (data.width != undefined && data.width != null && data.width.length > 0) {		
			$(modal).find("input[name='dialog-component-setting-element-width']").val(data.width);
		} else {		
			$(modal).find("input[name='dialog-component-setting-element-width']").val("100");
		}
		
		if (data.widthunit != undefined && data.widthunit != null && data.widthunit.length > 0 && data.widthunit != 'undefined') {		
			$(modal).find("[name='dialog-component-setting-element-width-unit']").val(data.widthunit);
		} else {		
			$(modal).find("[name='dialog-component-setting-element-width-unit']").val("%");
		}
	} else {
		$(modal).find("#widthLabelDisplay").removeAttr("style").hide();
	}
	// daipv: End add [width, widthunit] for [Label]

	if (data.isBlank != undefined && eval(data.isBlank)) {
		$(modal).find("input[name='labelName']").val("");
		$(modal).find("input[name='labelNameAutocomplete']").val("");
		$.qp.disableAutocomplete($(modal).find("input[name='labelName']"));
	} else {
		$.qp.enableAutocomplete($(modal).find("input[name='labelName']"));
		if (data.isBundle != undefined && eval(data.isBundle)) {						
			$(modal).find("input[name='labelName']").val(data.label);
			if(data.datatype == 20 || data.datatype == 21) {
				$(modal).find("input[name='labelNameAutocomplete']").val(htmlDecode(data.columnname));
			} else {
				$(modal).find("input[name='labelNameAutocomplete']").val(htmlDecode(data.labelText));
			}
		} else {									
			if (data.labelText != undefined && data.labelText != ''){
				if(data.datatype == 20 || data.datatype == 21) {
					$(modal).find("input[name='labelNameAutocomplete']").val(htmlDecode(data.columnname));
				} else {
					$(modal).find("input[name='labelNameAutocomplete']").val(htmlDecode(data.labelText));
				}
			}
		}
		
		openProperties(modal, data);
	}
	
	setFirstTabActive($(modal));
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});
}

function openDialogCustomSetting(obj, isComponent){
	var modal = $('#dialog-custom-setting');
	var ajaxCustomContent;
	var value = $(obj).next().val();
	
	$(modal).data("target", obj);
	$(modal).data("data", value);
	$(modal).data("isComponent", isComponent);
		
	var data = convertToJson(value);
	if(data.screenItemId != null && data.screenItemId != undefined && data.screenItemId != "undefined" && data.screenItemId != "") {
		//Ajax controller
		var url = CONTEXT_PATH + "/screendesign/getCustomContent?screenItemId="+data.screenItemId+"&r="+Math.random();
		ajaxCustomContent = $.qp.getTextResult(url);
	}
	displayStyle(modal, data);
	//Value of ajax
	$(modal).find("input[name='ajaxContent']").val(ajaxCustomContent);
	//Value of form
	$(modal).find("textarea[name='customItemContent']").val(data.customItemContent);
	
	if (data['customItemContent'] == undefined || data['customItemContent'] == null || data['customItemContent'] == ajaxCustomContent || data['customItemContent'].length == 0) {
		$(modal).find("textarea[name='customItemContent']").val(ajaxCustomContent);
	}
	
	if (ajaxCustomContent != undefined && ajaxCustomContent.length > 0 && data['customItemContent'] != undefined &&  data['customItemContent'].length > 0 && data['customItemContent'] != ajaxCustomContent) {
		$(modal).find("textarea[name='customItemContent']").val(data['customItemContent']);
	}

	if (data['customItemContent'] == 'π' || data['customItemContent'] == 'undefined') {
		$(modal).find("textarea[name='customItemContent']").val('');
	}
	
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});
}

function openDialogCustomSectionSetting(obj, isComponent){
	var ajaxCustomSectionContent;
	var modal = $("#dialog-custom-section-setting");
	$(modal).data("target", obj);
	$(modal).data("isComponent", isComponent);
	var url = CONTEXT_PATH + "/screendesign/getCustomSectionContent?screenAreaId="+$(obj).closest("span").find("input[name='formAreaIdStore']").val()+"&r="+Math.random();
	ajaxCustomSectionContent = $.qp.getTextResult(url);
	var customSectionContent = $(obj).closest("span").find("[name=formCustomSectionContent]").val();
	//Value of ajax
	$(modal).find("input[name='ajaxContent']").val(ajaxCustomSectionContent);
	//Value of form
	$(modal).find("textarea[name='customSectionContent']").val($(obj).closest("span").find("[name=formCustomSectionContent]").val());
	
	if (customSectionContent == undefined || customSectionContent == null || customSectionContent == ajaxCustomSectionContent || customSectionContent.length == 0) {
		$(modal).find("textarea[name='customSectionContent']").val(ajaxCustomSectionContent);
	}
	
	if (ajaxCustomSectionContent != undefined && ajaxCustomSectionContent.length > 0 && customSectionContent != undefined &&  customSectionContent.length > 0 && customSectionContent != ajaxCustomSectionContent) {
		$(modal).find("textarea[name='customSectionContent']").val(customSectionContent);
	}

	if (customSectionContent == 'π' || customSectionContent == 'undefined') {
		$(modal).find("textarea[name='customSectionContent']").val('');
	}
	
	openAreaStyle(modal, obj);
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});
}

function openActionAreaSetting(obj){
	//var column = convertToJson($(obj).closest("th").find("input").val());
	
	var modal = $("#modal-action-setting");
	$(modal).data("data", obj);
	$(modal).find("#dialog-form-table-error").html("");
	$(modal).data("target", obj);
	$(modal).find("[name=tableCaptionAutocomplete]").val($(obj).closest("span").find("[name=formAreaCaptionText]").val());
	$(modal).find("[name=tableCaption]").val($(obj).closest("span").find("[name=formAreaCaptionId]").val());
	$(modal).find("[name=areaCode]").val($(obj).closest("span").find("[name=formAreaCode]").val());
	var width = $(obj).closest("span").find("input[name=formTableWidth]").val();
	if(width == undefined || width.length == 0){
		width = 100;
		$(modal).find("[name=tableWidthUnit]").val("%");
	}else{
		if(width.indexOf("%") != -1){
			width = width.substring(0, width.length - 1);
			$(modal).find("[name=tableWidthUnit]").val("%");
		}
		if(width.indexOf("px") != -1){
			width = width.substring(0, width.length - 2);
			$(modal).find("[name=tableWidthUnit]").val("px");
		}
	}
	$(modal).find("[name=tableWidth]").val(width);
	$(modal).find("[name=tablePosition]").val($(obj).closest("span").find("[name=formTablePosition]").val());
	$(modal).find("select[name=modal-section-setting-display-type]").val($(obj).closest("span").find("[name=formDisplayType]").val());
	$(modal).find("select[name=direction]").val($(obj).closest("span").find("[name=formDirection]").val());


	$(modal).find("#tbl-hiddenAttibutes").find("tbody").empty();
	var parameters = $(obj).closest("span").find("[name=formElementHidden]").val();
	if(parameters != undefined && parameters.length > 0){
		var arrParameters = parameters.split("�");
		$(modal).find("#tbl-hiddenAttibutes").find("tbody").empty();
		$(arrParameters).each(function(i){
			var dataParameter = arrParameters[i].split("π");
			$.qp.addRowJSByLink($(modal).find("#tbl-hiddenAttibutes").next().find("a"));
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:last").find("input[name=parameterAttribute]").val(dataParameter[0]);
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:last").find("input[name=labelName]").val(dataParameter[1]);
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:last").find("input[name=labelNameAutocomplete]").val(dataParameter[2]);
			
		});
	} else {
		/*$.qp.addRowJSByLink($(modal).find("#tbl-hiddenAttibutes").next().find("a"));
		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:last").find("input[name=parameterAttribute]").val("");
		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:last").find("input[name=labelName]").val("");
		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:last").find("input[name=labelNameAutocomplete]").val("");*/
	}

	if($(modal).find("#modal-table-list-setting-form").length >0){
		initSelectFormIndex(obj,$(modal).find("#modal-table-list-setting-form"));
	}
	if($(modal).find("#modal-action-setting-form").length>0){
		initSelectFormIndex(obj,$(modal).find("#modal-action-setting-form"));
	}
	setFirstTabActive($(modal));
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});
}
function openFormSetting(obj, blogic) {
	var modal = $("#modal-form-setting");
	
	if (blogic != undefined && blogic != null) {
		$(modal).find("td[set-blogic]").html(blogic);
	}
	
	$(modal).data("target", obj);
	var formType = $(obj).closest('.form-setting').next().find('.form-content').find("[name=screenFormMethodType]").val();
	if(formType != undefined && formType != ""){
		$(modal).find("[name=modal-setting-form-method]").val(formType)
	} else {
		//default post
		$(modal).find("[name=modal-setting-form-method]").val(2)
	}
	
	var formAction = $(obj).closest('.form-setting').next().find('.form-content').find("[name=screenFormFormActionCode]").val();
	if(formAction != undefined && formAction != ""){
		$(modal).find("[name=modal-setting-form-action]").val(formAction);
	} else {
		$(modal).find("[name=modal-setting-form-action]").val("");
	}
	
	var formActionName = $(obj).closest('.form-setting').next().find('.form-content').find("[name=screenFormFormActionName]").val();
	if(formActionName != undefined && formActionName != ""){
		$(modal).find("[name=modal-setting-form-actionAutocomplete]").val(formActionName);
	} else {
		$(modal).find("[name=modal-setting-form-actionAutocomplete]").val("");
	}
	
	var formEncryptType = $(obj).closest('.form-setting').next().find('.form-content').find("[name=screenFormEncryptType]").val();
	if(formEncryptType != undefined && formEncryptType != ""){
		$(modal).find("[name=modal-setting-form-enctype]").val(formEncryptType);
	} else {
		//default text/plain
		$(modal).find("[name=modal-setting-form-enctype]").val(3);
	}
	
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});
}

function openSettingParam(obj) {
	var blogic = $(obj).closest('.onchange-event');
	
	var blogicId = $(blogic).find('input[name=businessLoginAutocomplete]').val();
	
	if (!validateBLogicNameEventSetting(blogic)) {
		return;
	}
	var modal = $("#modal-event-param-setting");
	var dataString = $.qp.getString(CONTEXT_PATH + '/screendesign/getBusinessDesign?businesslogicid=' + blogicId);
	var data = convertToJson(dataString);
	var inputBeans = data.lstInputBean;
	var $screenItemCode = $('#autocomplete-template').tmpl();
	
	var blogicDataString = $(obj).closest('table').closest('td').find('input[name=blogicSetting]').val();
	var blogicData = {};
	
	if (blogicDataString.length > 0) {
		blogicData = convertToJson(blogicDataString);
	}
	
	if (inputBeans != undefined && inputBeans != null) {
		
		$(modal).find('.mapping-bean').find('tbody').empty();
		for (var i = 0; i < inputBeans.length; i++) {
			var dataType = CL_BD_DATATYPE[inputBeans[i].dataType];
			var parentBeanId = inputBeans[i].parentInputBeanId;
			var parentIsArray = $(modal).find('.mapping-bean').find('tbody tr[beanid='+parentBeanId+']').attr('isobjectarray');
			
			if (inputBeans[i].arrayFlg) {
				dataType += '[]';
			}
			
			var content = '<tr beanid="'+inputBeans[i].inputBeanId+'" level="'+inputBeans[i].tableIndex+'" isobjectarray="'+inputBeans[i].arrayFlg+'"  isobjectdatatype="'+inputBeans[i].dataType+'" data-ar-rgroup="'+inputBeans[i].groupId+'">' +
									'<td colspan="2" style="padding: 0px;">' +
								'<div style="height: 100%;">' +
									'<div style="padding: 6px 7px;float:left; width: 56px;height: 100%; border-right-style: solid; border-right-width: 1px; border-right-color: rgb(221, 221, 221); text-align: center;">'+inputBeans[i].tableIndex+'</div>' +
									'<div style="float:left;height:100%;vertical-align: middle; padding: 6px 7px;">' +
										inputBeans[i].messageString+
									'</div>' +
								'</div>' +
							'</td>' +
							'<td>' +
								inputBeans[i].inputBeanCode +
							'</td>' +
							'<td>' +
								'<div class="input-group">' +
									dataType + 
								'</div>' +
							'</td>' +
							'<td style="text-align: center;">';
							if (inputBeans[i].dataType != 14 && inputBeans[i].dataType != 0) {
								content += $screenItemCode.html();
							}
							
				content += '</td>' +
						'</tr>';
			$(modal).find('.mapping-bean').find('tbody').append(content);
			if (parentBeanId == null) {
				$(modal).find('.mapping-bean').find('tbody tr:eq('+i+')').find('input[name=screenItemCodeAutocomplete]').attr('arg03', inputBeans[i].arrayFlg);
			} else {
				$(modal).find('.mapping-bean').find('tbody tr:eq('+i+')').find('input[name=screenItemCodeAutocomplete]').attr('arg03', parentIsArray);
			}
			
			//init data
			if (blogicData.inputbeans != undefined) {
				//set val for text
				$(modal).find('.mapping-bean').find('tbody tr:eq('+i+')').find('input[name=screenItemCodeAutocomplete]').val(getCode(blogicData.inputbeans[i].itemcode));
				$(modal).find('.mapping-bean').find('tbody tr:eq('+i+')').find('input[name=screenItemCode]').val(blogicData.inputbeans[i].itemcode);
			}
			
			$.qp.initialCatAutocomplete($(modal).find('.mapping-bean').find('tbody tr:eq('+i+')').find('input[name=screenItemCodeAutocomplete]'));
		}
	}
	$.qp.screenDesign.initData($(".mapping-bean"));
	
	$(modal).data('data', blogicData);
	$(modal).data('target', obj);
	$(modal).modal('show');
}

function openAssignParam(obj) {
	
	var parent = $(obj).closest('.onchange-event');
	
	var blogicId = $(parent).find('input[name=businessLoginAutocomplete]').val();
	
	if (!validateEffectAreaEventSetting(parent)) {
		return;
	}
	
	if (!validateBLogicNameEventSetting(parent)) {
		return;
	}
	var modal = $("#modal-event-assign-result");
	var dataString = $.qp.getString(CONTEXT_PATH + '/screendesign/getBusinessDesign?businesslogicid=' + blogicId);
	var data = convertToJson(dataString);
	var outputBeans = data.lstOutputBean;
	var areaName = $(parent).find('input[name=effectArea]').val();
	
	
	var $element = getAreaByCode(areaName);
	var areaType = $element.find('input[name=formAreaType]').val();
	var isArray = "false";
	if (areaType == 1) {
		isArray = "true";
	}
		
	var blogicDataString = $(obj).closest('table').closest('td').find('input[name=blogicSetting]').val();
	var blogicData = {};
	
	if (blogicDataString.length > 0) {
		blogicData = convertToJson(blogicDataString);
	}
		
	var $screenItemCode = $('#autocomplete-template').tmpl();
	
	if (outputBeans != undefined && outputBeans != null) {
		$(modal).find('.mapping-bean').find('tbody').empty();
		for (var i = 0; i < outputBeans.length; i++) {
			var dataType = CL_BD_DATATYPE[outputBeans[i].dataType];
			var parentBeanId = outputBeans[i].parentOutputBeanId;
			var parentIsArray = $(modal).find('.mapping-bean').find('tbody tr[beanid='+parentBeanId+']').attr('isobjectarray');
			var count = (outputBeans[i].tableIndex.match(/\./g) || []).length;
			
			if (count == 0) {
				parentIsArray = outputBeans[i].arrayFlg + "";
			}
			
			if (outputBeans[i].arrayFlg) {
				dataType += '[]';
			}
			
			
			var content = '<tr beanid="'+outputBeans[i].outputBeanId+'" level="'+outputBeans[i].tableIndex+'" isobjectarray="'+outputBeans[i].arrayFlg+'"  isobjectdatatype="'+outputBeans[i].dataType+'" data-ar-rgroup="'+outputBeans[i].groupId+'">' +
									'<td colspan="2" style="padding: 0px;">' +
								'<div style="height: 100%;">' +
									'<div style="padding: 6px 7px;float:left; width: 56px;height: 100%; border-right-style: solid; border-right-width: 1px; border-right-color: rgb(221, 221, 221); text-align: center;">'+outputBeans[i].tableIndex+'</div>' +
									'<div style="float:left;height:100%;vertical-align: middle; padding: 6px 7px;">' +
										outputBeans[i].messageString+
									'</div>' +
								'</div>' +
							'</td>' +
							'<td>' +
								outputBeans[i].outputBeanCode +
							'</td>' +
							'<td>' +
								'<div class="input-group">' +
									dataType + 
								'</div>' +
							'</td>' +
							'<td style="text-align: center;">';
							if (outputBeans[i].dataType != 14 && outputBeans[i].dataType != 0) {
								if (isArray == parentIsArray) {
									content += $screenItemCode.html();
								}
							}
							
							content += '</td>' +
						'</tr>';
			$(modal).find('.mapping-bean').find('tbody').append(content);
			//init data
			if (blogicData.outputbeans != undefined) {
				
				//set val for text
				if (blogicData.outputbeans[i] != null && blogicData.outputbeans[i].itemcode != undefined) {
						$(modal).find('.mapping-bean').find('tbody tr:eq('+i+')').find('input[name=screenItemCodeAutocomplete]').val(getCode(blogicData.outputbeans[i].itemcode));
						$(modal).find('.mapping-bean').find('tbody tr:eq('+i+')').find('input[name=screenItemCode]').val(blogicData.outputbeans[i].itemcode);
				}
				
			}
		}
	}
	
	
	
	$.qp.screenDesign.initData($(".mapping-bean"));
	
	$(modal).find('input[name=screenItemCodeAutocomplete]').attr('sourcecallback', "loadScreenItemCodeByArea");
	$(modal).find('input[name=screenItemCodeAutocomplete]').attr('arg02', areaName);
	
	$.qp.initialCatAutocomplete($(modal).find('input[name=screenItemCodeAutocomplete]'));
	//set effect area 
	$(modal).find('label[name=areaName]').html(areaName);
	
	$(modal).data('data', blogicData);
	$(modal).data('target', obj);
	
	$(modal).modal('show');
}

function openDisplayParam() {
	var modal = $("#modal-display-param-setting");
	$(modal).modal('show');
}

function openModalModelAttributeMapping(obj) {
	var modal = $("#modal-model-attribute-mapping");
	var parentModel = $(obj).closest(".modal-dialog");
	$(modal).find(".modal-dialog").width($(parentModel).width());
	$(modal).modal('show');
}
function openDisplayParamArgument() {
	var modal = $("#modal-display-param-argument-setting");
	$(modal).modal('show');
}

function demoautocompate(){
	$("#removenedd").remove();
//	var template_require_constraint = "<qp:autocomplete optionValue=\"optionValue\" optionLabel=\"optionLabel\" selectSqlId=\"\" value=\"\" name=\"moduleId\" displayValue=\"\" arg01=\"\" mustMatch=\"true\" sourceCallback= \"a\" sourceType=\"local\"></qp:autocomplete>";
//	
//	var element = $(template_require_constraint); 
//	$.qp.initialAutocomplete(element.find(".qp-input-autocomplete"));
//	
//	$("adding-top").append(element);
	
	//$(".onchange-event").append(st);
	
	var htmlTypeOfList = 
	"<div class='input-group' style=''>" + 
	"<input type='text' name='sqlCodeAutocomplete' id='sqlCodeAutocompleteId' displayValue class='form-control qp-input-autocomplete ui-autocomplete-input' value='' optionvalue='optionValue' optionlabel='optionLabel' selectsqlid='' emptylabel='sc.sys.0030' onselectevent='' onchangeevent='saveSqlCode' onremoveevent='' mustmatch='true' maxlength='200' minlength='' arg01='' arg02='' arg03='' arg4='' arg05='' arg06='' arg07='' arg08='' arg09='' arg10='' arg11='' arg12='' arg13='' arg14='' arg15='' arg16='' arg17='' arg18='' arg19='' arg20='' sourceCallback= 'a' sourceType='local' placeholder='Search…' autocomplete='off' previousvalue='' previouslabel='' selectedvalue='false'> " + "<input type='hidden' name='sqlCode' value=''><span class='input-group-addon dropdown-toggle' data-dropdown='dropdown' style='cursor: pointer;' onclick='$.qp.searchAutocompleteData(this)'> <span class='caret'></span></span>" + 
	"</div>";
	
	var element = $(htmlTypeOfList); 
	$.qp.initialAutocomplete(element.find(".qp-input-autocomplete")); 
	$("#specialdiv").append(element);
}

//using when click to choose Item on screen
function selectItem(id){
	var idoption = "#" + id;
	var valueinput="";
	var parent="";
	var jsondata;
	i=0;
	var itemName="";
	// remove option
	$(idoption).find('option').remove();
	$(idoption).empty();
	
	$("input[name='formElement']").closest("div[class='areaContent'").each(function(){
		parent = $(this).find("div[class='panel-heading']").find("input[name='formAreaCaptionText']").val();
		var optgroup = $('<optgroup>');
		optgroup.attr('label',parent);

		$(this).find("table").find("tbody").find("span").find("input[type='hidden']").each(function(){
			valueinput = this.value;
			jsondata = convertToJson(valueinput);
			if("" != jsondata && typeof jsondata != "undefined" && typeof jsondata.columnname != "undefined"){
				var option = $("<option></option>");
				itemName = jsondata.columnname;
                option.val(i);
                option.text(itemName);
                optgroup.append(option);
			}
		});
		// add to option
		$(idoption).append(optgroup);
	});

}

function highLightRow(objRef){
	 var row = objRef.parentNode.parentNode;
	    if(objRef.checked)
	    {
	        //If checked change color to Aqua
	    	row.style.backgroundColor = "#C2D69B";
	    }
	    else
	    {   
	    	 row.style.backgroundColor = "white";
	    }
	   
	    //Get the reference of GridView
	    var GridView = row.parentNode;
	   
	    //Get all input elements in Gridview
	    var inputList = GridView.getElementsByTagName("input");
	   
	    for (var i=0;i<inputList.length;i++)
	    {
	        //The First element is the Header Checkbox
	        var headerCheckBox = inputList[0];
	       
	        //Based on all or none checkboxes
	        //are checked check/uncheck Header Checkbox
	        var checked = true;
	        if(inputList[i].type == "checkbox" && inputList[i] != headerCheckBox)
	        {
	            if(!inputList[i].checked)
	            {
	                checked = false;
	                break;
	            }
	        }
	    }
	    headerCheckBox.checked = checked;
}

function openSettingDefaultValue(obj, datatype) {
	
	var value = $(obj).closest("td").find("input[name^=screenItemForms][type=hidden]").val();
	if (value != undefined && value != null && value.length > 0 ) {
		$("#settingDefaultValue").find("input[name=defaultValue][value="+value+"]").prop("checked", true);
	} else {
		$("#settingDefaultValue").find("input[name=defaultValue]").prop("checked", false);
	}
	
	if (datatype == '9') {
		$("#settingDefaultValue").find("input[name=defaultValue][value=4]").closest("label").show();
		$("#settingDefaultValue").find("input[name=defaultValue][value!=4]").closest("label").hide();
	} else {
		$("#settingDefaultValue").find("input[name=defaultValue][value=4]").closest("label").hide();
		$("#settingDefaultValue").find("input[name=defaultValue][value!=4]").closest("label").show();
	}
	
	$("#settingDefaultValue").data("target", obj);
	$("#settingDefaultValue").modal(
		{ 
			show: true,
			closable: false,
			keyboard:false,
			backdrop:'static'
		}
	);
}

function changeItemName(item) {
	var obj = $(item.target);
	
	$(obj).closest("div").next().val($(obj).attr("selectedvalue"));
}
function settingIcon(obj, modalName) {
	var modal = $("#modal-setting-icon");
	$(modal).find('.modal-footer').empty();
	if (modalName != null && modalName == 'dialog-button-area-setting-section') {
		$(modal).find('.modal-footer').append('<button type=\"button"\ class=\"btn qp-button-client \" onclick=\"saveIcon(this, \'dialog-button-area-setting-section\')\">OK</button>');
	} else {
		$(modal).find('.modal-footer').append('<button type=\"button\" class=\"btn qp-button-client \" onclick=\"saveIcon(this)\">OK</button>');
	}
	
	$(modal).find('.thumbnail').click(function() {
		$(modal).find('.thumbnail').removeAttr('selected');
		$(this).attr('selected', 'true');
	});
	
	var icon = $(obj).closest('td').find('input[name="iconButton"]').val();
	$(modal).find('.thumbnail').find('span[class="'+icon+'"]').closest('.thumbnail').attr('selected', 'true');
	
	$(modal).data("target", obj);
	$(modal).modal("show");
}

function openTabSetting(obj) {
	var modal = $("#modalTabSetting");
	$(modal).data("target", obj);
	$(modal).data("isNewTab", false);
	$(modal).find("span[name=span-name]").html(dbMsgSource['sc.screendesign.0375']);
	//load area
	var currentForm = $(obj).closest(".form-area-content");
	$(modal).data("currentForm", currentForm);
	var value = $(currentForm).find(".form-content").find("input[name=screenFormTab]").val();

	$(modal).find("#tab-setting").find("ul").find("li:not(li[onclick])").remove();
	$(modal).find("#tab-setting").find(".tab-content").empty();
	
	
	if (value != undefined && value != null && value.length > 0) {
		var tabs = convertToJson(value);
		
		var arr = []
		//get list tab item same tab
		for (var i = 0; i < tabs["tabs"].length; i++) {
			if (tabs["tabs"][i]["tabCode"] != undefined && tabs["tabs"][i]["tabCode"] != null && getFormCode(currentForm).replace(/ /g,'') + "-" + tabs["tabs"][i]["tabCode"] == $(obj).closest('.area-tab').attr("id")) {
				arr.push(tabs["tabs"][i]);
			}
		}
		
		var index = 0;
		for (var i = 0; i < tabs["tabs"].length; i++) {
			if (tabs["tabs"][i]["tabCode"] != undefined && tabs["tabs"][i]["tabCode"] != null && getFormCode(currentForm).replace(/ /g,'') + "-" + tabs["tabs"][i]["tabCode"] == $(obj).closest('.area-tab').attr("id")) {
				$(modal).find("input[name=tabType][value='"+tabs["tabs"][i]["tabDirection"]+"']").prop("checked", true);
				var arrOther = [];
				for (var k = 0; k < arr.length; k++) {
					if (arr[k]['areas'] != tabs["tabs"][i]['areas']) {
						arrOther.push(arr[k]);
					}
				}
				addNewTab(currentForm, $(modal).find("li[onclick^=addTab]"), index, tabs["tabs"][i], arrOther);
				index++;
			}
		}
	} else {
		addNewTab(currentForm, $(modal).find("li[onclick^=addTab]"), 0, null);
	}
	
	$(modal).modal("show");
}



function openNewTabSetting(obj) {
	var modal = $("#modalTabSetting");
	
	$(modal).find("span[name=span-name]").html(dbMsgSource['sc.screendesign.0376']);
	
	$(modal).data("target", obj);
	$(modal).data("isNewTab", true);
	
	//load area
	var form = $(obj).closest('.form-setting').next();
	$(modal).data("currentForm", form);
	var value = $(form).find(".form-content").find("input[name=screenFormTab]").val();

	$(modal).find("#tab-setting").find("ul").find("li:not(li[onclick])").remove();
	$(modal).find("#tab-setting").find(".tab-content").empty();
	
	if (value != undefined && value != null && value.length > 0) {
		var tabs = convertToJson(value);
		$(modal).data("data", tabs);
	} 
	var currentForm = $($(modal).data("target")).closest('.form-setting').next(); 
	addNewTab(currentForm, $(modal).find("li[onclick^=addTab]"), 0, null);
	$(modal).find("input[name=tabType][value=0]").prop("checked", true);
	$(modal).modal("show");
}