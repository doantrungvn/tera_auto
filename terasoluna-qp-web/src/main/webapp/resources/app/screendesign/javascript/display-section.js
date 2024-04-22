function openDialogAutocompleteSettingSection(obj){
	var modal = $("#dialog-component-autocomplete-setting-section");
	$(modal).data("target", obj);
	var value = $(obj).closest("span").find("input[name='formElement']").val();
	if(value==undefined){
	}
	var column = convertToJson(value);
	$(modal).data("data", value);
    $(modal).find("#dialog-form-options-error").html("");
    displayStyle(modal, column);
	$("#dialog-form-autocomplete-error").html("");
	$(modal).find("td[basetype='dialog-autocomplete-setting-base-types']").html(setBaseType(column));
	
	//bind data to dialog
	if (column.columnname != undefined) {
		$(modal).find("input[name='columnname']").val(column.columnname);
	} else {
		$(modal).find("input[name='columnname']").val("");
	}

	if (column.width != undefined && column.width.length > 0 && column.width != "0") {		
		$(modal).find("input[name='dialog-component-setting-element-width']").val(column.width);
	} else {		
		$(modal).find("input[name='dialog-component-setting-element-width']").val("25");
	}
	
	if (column.widthunit != undefined) {		
		$(modal).find("[name='dialog-component-setting-element-width-unit']").val(column.widthunit);
	} else {		
		$(modal).find("[name='dialog-component-setting-element-width-unit']").val("%");
	}
	
	openProperties(modal, column);
	
	if (column.physicaldatatype != undefined && column.physicaldatatype != null) {
		$(modal).find("select[name='baseType']").val(column.physicaldatatype);
	} else {
		$(modal).find("select[name='baseType']").val(1);
	}
	$(modal).find("input[name='dialogOnChangeEvent']").val(column.dialogOnChangeEvent);
	$(modal).find("input[name='dialogOnSelectEvent']").val(column.dialogOnSelectEvent);
	$.qp.initialAllPicker($(modal).find("input[name='dialog-component-setting-element-width']"));
	setFirstTabActive($(modal));
	
	if (column.mandatory != undefined) {
		if (column.mandatory == "true") {
			$(modal).find("input[name='mandatory']").prop("checked", true);
		} else {
			$(modal).find("input[name='mandatory']").prop("checked", false);
		}
	} else {		
		$(modal).find("input[name='mandatory']").prop("checked", false);
	}
	
	//show datasource
	if (column.datasourcetype != undefined && column.datasourcetype == '1') {
		
		if (column.sqldesignid != undefined) {
			$(modal).find("input[name=sqldesignid]").val(column.sqldesignid);
		} else {
			$(modal).find("input[name=sqldesignid]").val('');
		}
		
		initOptionAutocomplete($(modal).find("#sql-setting"), column.sqldesignid);
		
		if (column.sqldesignidtext != undefined) {
			$(modal).find("input[name=sqldesignidAutocomplete]").val(column.sqldesignidtext);
		} else {
			$(modal).find("input[name=sqldesignidAutocomplete]").val('');
		}
		
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
		$(modal).find("input[name=optionlabelAutocomplete]").val('');
		$(modal).find("input[name=optionvalueAutocomplete]").val('');
		
		/*$(modal).find("input[name=optionlabel]").val('');*/
		$(modal).find("#sql-setting").find("td[optionlabel='optionLabel']").html("")
		
		/*$(modal).find("input[name=optionvalue]").val('');*/
		$(modal).find("#sql-setting").find("td[optionvalue='optionValue']").html("");
	}
	
	if (column.allowAnyInput != undefined && column.allowAnyInput != null && column.allowAnyInput != "") {
		$(modal).find("input[name=allowAnyInput][value="+column.allowAnyInput+"]").prop('checked', true);
	}
	
	addRadioChooseMappingOutput(modal, obj, column);
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});
}

function openDialogLinkAreaSettingSection(obj){
	var modal = $("#dialog-link-area-setting-section");
	$(modal).data("target", obj);
	$(modal).find('#navigateToScreenBlogic').find("input[name='navigateToBlogicAutocomplete']").attr("arg01","");
	$(modal).data("data", $(obj).closest("span").find("input[name='formElement']").val());
	$(modal).find("#dialog-form-options-error").html("");
	
	var data;
	var itemsBaseType = CL_PRIMITIVE_DATATYPE;
	var formElement = $(obj).closest("span").find("input[name='formElement']");
	data =  convertToJson($(formElement).val());
	
	if (data.tabindex != undefined) {
		$(modal).find("input[name=tabindex]").val(data.tabindex);
	} else {
		$(modal).find("input[name=tabindex]").val('');
	}
	
	// set base type for [Dynamic link] element
	if(data.datatype == 22) {
		if(data.datasourcetype == undefined || data.datasourcetype == "") {
			$(modal).find("#dialog-link-area-setting-codelist").find("input[name='dataSourceType'][value='1']").prop("checked",true);
			$(modal).find("#dialog-link-area-setting-codelist").find("#accordion").hide();
		}
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
	
	if(formElement.val() == undefined){
		
		var tdIndex = $(obj).closest("td").attr("index");
		var trIndex = $(obj).closest("tr").attr("index");
		formElement = $(obj).closest("table").find("thead").find("tr:eq("+trIndex+")").find("th:eq("+tdIndex+")").find("input[name=formElement]");
	}
	
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
	
	displayStyle(modal, data);
	
	if(data.datatype == 11) {
		$(modal).find("tr[id='linkStatic']").show();
		$(modal).find("tr[name='linkDynamic']").hide();
		$(modal).find("tr[class='widthDynamicLink']").hide();
		$(modal).find("tr[name='baseType']").hide();
		
	}
	if(data.datatype == 22) {
		if(data.datasourcetype == undefined || data.datasourcetype == "") {
			$(modal).find("#dialog-link-area-section-setting-codelist").find("input[name='dataSourceType'][value='1']").prop("checked",true);
			$(modal).find("#dialog-link-area-section-setting-codelist").find("#accordion").hide();
		}
		$(modal).find("tr[id='linkStatic']").hide();
		$(modal).find("tr[name='linkDynamic']").show();
		$(modal).find("tr[class='widthDynamicLink']").show();
		$(modal).find("tr[name='baseType']").show();
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
	$(modal).find("input[name=linkDynamicLabelNameAutocomplete]").val(htmlDecode(data.labelText));
	if (data.columnname != undefined) {
		$(modal).find("input[name='columnname']").val(data.columnname);
	} else {
		$(modal).find("input[name='columnname']").val(htmlDecode(data.labelText));
	}
	$(modal).find("input[name=isBundle]").prop("checked",data.isBundle);
	
	if(data.navigateTo != undefined && data.navigateTo != "") {
		$(modal).find("input[name='navigateTo']").val(data.navigateTo);
	} else {
		$(modal).find("input[name='navigateTo']").val("");
	}
	
	if(data.navigateToText != undefined && data.navigateTo != "") {
		$(modal).find("input[name='navigateToAutocomplete']").val(htmlDecode(data.navigateToText));
	} else {
		$(modal).find("input[name='navigateToAutocomplete']").val("");
	}
	
	$(modal).find("input[name='actionName']").val(data.actionName);
	$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:gt(0)").remove();
	if (data.navigateToBlogic != "undefined" && data.navigateToBlogic != undefined && data.navigateToBlogic != null) {
		$(modal).find('#navigateToScreenBlogic').find("input[name='navigateToBlogic']").val(data.navigateToBlogic);
	} else {
		$(modal).find('#navigateToScreenBlogic').find("input[name='navigateToBlogic']").val("");
	}
	
	displayParamAction(modal, data);
	if(data.datatype == 22){
		if (data.datasourcetype == "2") {
			showDataSourceInView(modal, data);
			$(modal).find("a[href=#dialog-link-area-section-setting-codelist]").closest('li').show();
		} else {
			if ((data.dialogAutocompleteCode == undefined || data.dialogAutocompleteCode.length == 0)) {
				showDataSourceInView(modal, data);
				$(modal).find("a[href=#dialog-link-area-section-setting-codelist]").closest('li').show();
			} else {
				$(modal).find("a[href=#dialog-link-area-section-setting-codelist]").closest('li').hide();
			}
		}
	} else {
		$(modal).find("a[href=#dialog-link-area-section-setting-codelist]").closest('li').hide();
	}
	
	if(data.datatype == 22) {
		if(data.datasourcetype == undefined || data.datasourcetype == "") {
			$(modal).find("#dialog-link-area-section-setting-codelist").find("input[name='dataSourceType'][value='1']").prop("checked",true);
			$(modal).find("#dialog-link-area-section-setting-codelist").find("#accordion").hide();
		}
	}
	
	if(data.screenDesignTextCodeListId != undefined && data.screenDesignTextCodeListId != "") {
		$(modal).find("input[name='screenNameCodeListAutocomplete']").val(data.screenDesignTextCodeListId);
	} else {
		$(modal).find("input[name='screenNameCodeListAutocomplete']").val("");
	}
	var arr = [];
	if(data.screenItemTextCodeListId != undefined && data.screenItemTextCodeListId != "") {
		arr = data.screenItemTextCodeListId.split(".");
		$(modal).find("input[name='screenItemCodeListAutocomplete']").val(arr[2]);
	} else {
		$(modal).find("input[name='screenItemCodeListAutocomplete']").val("");
	}
	
	if(data.screenDesignIdCodeListId != undefined && data.screenDesignIdCodeListId != "") {
		$(modal).find("input[name='screenItemCodeListAutocomplete']").attr("arg01",data.screenDesignIdCodeListId);
		$(modal).find("input[name='screenNameCodeList']").val(data.screenDesignIdCodeListId);
	} else {
		$(modal).find("input[name='screenNameCodeList']").val("");
	}
	
	if(data.screenItemTextCodeListId != undefined && data.screenItemTextCodeListId != "") {
		$(modal).find("input[name='screenItemCodeList']").val(data.screenItemTextCodeListId);
	} else {
		$(modal).find("input[name='screenItemCodeList']").val("");
	}
	if(data.screenItemTextCodeListId != undefined) {
		/*getCodelistItemByScreenItemId($(modal),data.screenItemIdCodeListId);*/
		getCodelistItemByScreenItemName($(modal),data.screenItemTextCodeListId,data.screenDesignIdCodeListId);
	}
	
	if (data.width != undefined && data.width != null && data.width.length > 0 && data.width != "NaN") {		
		$(modal).find("input[name='dialog-component-setting-element-width']").val(data.width);
	} else {		
		$(modal).find("input[name='dialog-component-setting-element-width']").val("25");
	}
	if (data.widthunit != undefined && data.widthunit != null && data.widthunit.length > 0 && data.widthunit != 'undefined') {		
		$(modal).find("[name='dialog-component-setting-element-width-unit']").val(data.widthunit);
	} else {		
		$(modal).find("[name='dialog-component-setting-element-width-unit']").val("%");
	}
	
	if (data.navigateToBlogic != "undefined" && data.navigateToBlogicText != undefined && data.navigateToBlogicText != null) {
		$(modal).find('#navigateToScreenBlogic').find("input[name='navigateToBlogicAutocomplete']").val(htmlDecode(data.navigateToBlogicText));
	} else {
		$(modal).find('#navigateToScreenBlogic').find("input[name='navigateToBlogicAutocomplete']").val("");
	}
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
	setFirstTabActive($(modal));
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});
}

function openDialogButtonAreaSettingSection(button){
	var modal = $("#dialog-button-area-setting-section");
	$(modal).data("target", button);
	$(modal).data("data", $(button).closest("span").find("input[name='formElement']").val());
	$(modal).find("#dialog-form-options-error").html("");
	
	var data;
	var formElement = $(button).closest("span").find("input[name='formElement']");
	if(formElement.val() == undefined){
		
		var tdIndex = $(button).closest("td").attr("index");
		var trIndex = $(button).closest("tr").attr("index");
		formElement = $(button).closest("table").find("thead").find("tr:eq("+trIndex+")").find("th:eq("+tdIndex+")").find("input[name=formElement]");
	}
	data =  convertToJson($(formElement).val());
	
	if (data.tabindex != undefined) {
		$(modal).find("input[name=tabindex]").val(data.tabindex);
	} else {
		$(modal).find("input[name=tabindex]").val('');
	}
	
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
		$(modal).find("#navigateToScreen").find("input[name='navigateTo']").val(data.navigateTo);
		$(modal).find("#navigateToScreen").find("input[name='navigateToAutocomplete']").val(htmlDecode(data.navigateToText));
		if (data.navigateTo != undefined && data.navigateTo != null && data.navigateTo.length > 0) {
			getModuleByScreenId(data.navigateTo, $(modal));
		}
		if (data.navigateToBlogic != undefined && data.navigateToBlogic != null) {
			$(modal).find('#navigateToScreenBlogic').find("input[name='navigateToBlogic']").val(data.navigateToBlogic);
		}
		if (data.navigateToBlogicText != undefined && data.navigateToBlogicText != null) {
			$(modal).find('#navigateToScreenBlogic').find("input[name='navigateToBlogicAutocomplete']").val(htmlDecode(data.navigateToBlogicText));
		}
		$(modal).find("#navigateToScreen").show();
		$(modal).find("#navigateToScreenBlogic").show();
		$(modal).find("#navigateToblogic").hide();
		
		$(modal).find("#navigateToScreenBlogic").find("input[name='navigateToBlogic']").val("");
		$(modal).find("#navigateToScreenBlogic").find("input[name='navigateToBlogicAutocomplete']").val("");
		
		if (data.navigateToBlogic != undefined && data.navigateToBlogic != null) {
			$(modal).find('#navigateToBlogicAutocompleteId').next().val(data.navigateToBlogic);
		}
		
		if (data.navigateToBlogicText != undefined && data.navigateToBlogicText != null) {
			$(modal).find('#navigateToBlogicAutocompleteId').val(data.navigateToBlogicText);
		}
	} else if (data.actiontype == 1 || data.actiontype == "1") {
		$(modal).find("#navigateToblogic").find("input[name='navigateTo']").val(data.navigateTo);
		$(modal).find("#navigateToblogic").find("input[name='navigateToAutocomplete']").val(htmlDecode(data.navigateToText));
		
		if (data.navigateTo != undefined && data.navigateTo != null && data.navigateTo.length > 0) {
			getModuleByBlogicId(data.navigateTo, $(modal));
		}
		
		$(modal).find("#navigateToScreen").hide();
		$(modal).find("#navigateToScreenBlogic").hide();
		$(modal).find("#navigateToblogic").show();
	}
	
	if(data.isSubmit != undefined){
		if(data.isSubmit == 'true'){
			$(modal).find("input[name=isSubmit]").prop('checked', true);
			$(modal).find('#navigateToScreenBlogic').find("input[name='navigateToBlogicAutocomplete']").attr('arg04', 1);
			$(modal).find("#dialogActionParamSetting").hide();
			displayParamAction(modal, data);
			if (data.actiontype != undefined && (data.actiontype == 1 || data.actiontype == '1')) {
				$(modal).find("input[name=actiontype][value=1]").prop('checked', true);
			} else {
				$(modal).find("input[name=actiontype][value=0]").prop('checked', true);
			}
		}
		else{
			$(modal).find("input[name=isSubmit]").prop('checked', false);
			$(modal).find('#navigateToScreenBlogic').find("input[name='navigateToBlogicAutocomplete']").attr('arg04', 0);
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
	
	if (data.width != undefined && data.width != null && data.width.length > 0 && data.width != "NaN") {		
		$(modal).find("input[name='dialog-component-setting-element-width']").val(data.width);
	} else {		
		$(modal).find("input[name='dialog-component-setting-element-width']").val("");
	}
	
	if (data.widthunit != undefined && data.widthunit != null && data.widthunit.length > 0 && data.widthunit != 'undefined') {		
		$(modal).find("[name='dialog-component-setting-element-width-unit']").val(data.widthunit);
	} else {		
		$(modal).find("[name='dialog-component-setting-element-width-unit']").val("%");
	}
	
	$(modal).find('#navigateToBlogicAutocompleteId').attr('arg01', $(modal).find("#navigateToScreen").find("input[name='navigateTo']").val());
	$(modal).find("#dialog-button-area-setting-tbl-parameter").find("tbody").find("tr:gt(0)").remove();
	
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
	handleClickLabelSection();
	
	setFirstTabActive($(modal));
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});
}

function openDialogComponentListSettingSection(obj) {
	var modal = $("#dialog-component-list-setting-section");
	$(modal).data("target", obj);
	$(modal).data("data",  $(obj).closest("span").find("input[name='formElement']").val());
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
	//set basetype
	$(modal).find("td[basetype='dialog-component-list-setting-base-types']").html(setBaseType(data));
	$(modal).find("input[name=columnname]").val(data.columnname);
	showDataSourceInView(modal, data);
	openProperties(modal, data);
	
	if (data.datatype != undefined && data.datatype != null && data.datatype == 7) {
		$(modal).find('#showBlankItemLayout').show();
		if(data.showBlankItem == 1) {
			$(modal).find("input[name='showBlankItem']").attr("checked",true);
		} else {
			$(modal).find("input[name='showBlankItem']").attr("checked",false);
		}
	} else {
		$(modal).find('#showBlankItemLayout').hide();
	}
	
	if(data.baseType != undefined){
		$(modal).find("select[name=baseType]").val(data.baseType);
	}
	if(data.datatype != 7) {
		if(data.datatype == 5 || data.datatype == 6) {
			$(modal).find("tr[name='width']").hide();
		} else {
			$(modal).find("tr[name='width']").show();
			if (data.width != undefined && data.width.length >0 && data.width != "0") {		
				$(modal).find("input[name='dialog-component-list-setting-width']").val(data.width);
			} else {		
				$(modal).find("input[name='dialog-component-list-setting-width']").val("100");
			}
			
			if (data.widthunit != undefined) {		
				$(modal).find("[name='dialog-component-list-setting-width-unit']").val(data.widthunit);
			} else {		
				$(modal).find("[name='dialog-component-list-setting-width-unit']").val("%");
			}
		}
		
	} else {
		$(modal).find("tr[name='width']").show();
		if (data.width != undefined && data.width.length >0 && data.width != "0") {		
			$(modal).find("input[name='dialog-component-setting-element-width']").val(data.width);
		} else {
			$(modal).find("input[name='dialog-component-setting-element-width']").val("100");
		}
		
		if (data.widthunit != undefined) {		
			$(modal).find("[name='dialog-component-setting-element-width-unit']").val(data.widthunit);
		} else {		
			$(modal).find("[name='dialog-component-setting-element-width-unit']").val("%");
		}
	}
	
	if (data.mandatory != undefined) {
		if (data.mandatory == "true") {
			$(modal).find("input[name='mandatory']").prop("checked", true);
		} else {
			$(modal).find("input[name='mandatory']").prop("checked", false);
		}
	} else {		
		$(modal).find("input[name='mandatory']").prop("checked", false);
	}
	
	//init event
	if (data.events != undefined && data.events != null) {
		loadActionListComponent($(modal), data);
	} else {
		$(modal).find('#listEvent').empty();
		$(modal).find('#actionType').find('option:eq(0)').prop("selected", "selected");
	}
	
	setFirstTabActive($(modal));
	$.qp.initialConventionNameCode();
	addRadioChooseMappingOutput(modal, obj, data);
	showElementToChange($(modal), data);
	
	if (data.physicaldatatype != undefined && data.physicaldatatype != null && data.physicaldatatype == 8) {
		$(modal).find('.modal-body').find('.nav-tabs').find('li:eq(1)').hide();
		$(modal).find('td[basetype=dialog-component-list-setting-base-types]').closest("tr").show();
		$(modal).find('input[name=elementType]').closest('.qp-div-information').hide();
	} else {
		$(modal).find('.modal-body').find('.nav-tabs').find('li:eq(1)').show();
		$(modal).find('td[basetype=dialog-component-list-setting-base-types]').closest("tr").show();
		$(modal).find('input[name=elementType]').closest('.qp-div-information').show();
	}
	
	var divSettingCodeList = $(modal).find("div[id='dialog-component-list-setting-section-codelist']");
	if(data.datasourcetype == 1) {
		$(divSettingCodeList).find("input[name='dataSourceType'][value='1']").prop("checked",true);
		$(divSettingCodeList).find("input[name='dataSourceType'][value='2']").attr("checked",false);
		$(divSettingCodeList).find("table[id='codelist-setting']").hide();
	}
	
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});
}


function openDialogComponentSettingSection(obj){	
		
	var modal = $("#dialog-component-setting-section");
	
	$(modal).find("#dialog-form-options-error").html("");	
	var value = $(obj).closest("span").find("input[type='hidden']").val();
	var column = convertToJson(value);
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
		if (column.datasourcetype == "2") {
			showDataSourceInView(modal, column);
			$(modal).find("a[href=#dialog-component-setting-section-codelist]").closest('li').show();
		} else {
			
			if ((column.dialogAutocompleteCode == undefined || column.dialogAutocompleteCode.length == 0)) {
				showDataSourceInView(modal, column);
				$(modal).find("a[href=#dialog-component-setting-section-codelist]").closest('li').show();
			} else {
				$(modal).find("a[href=#dialog-component-setting-section-codelist]").closest('li').hide();
			}
		}
		if(column.datasourcetype == undefined || column.datasourcetype == "") {
			$(modal).find("#dialog-component-setting-section-codelist").find("input[name='dataSourceType'][value='1']").prop("checked",true);
			$(modal).find("#dialog-component-setting-section-codelist").find("#accordion").hide();
		}
	} else {
		$(modal).find("a[href=#dialog-component-setting-section-codelist]").closest('li').hide();
	}
	
	var groupDisplayType = $(obj).closest("td").find("input[name='dialog-component-setting-group-display-type']").val(); 
	
	if (groupDisplayType != undefined && groupDisplayType != '' && groupDisplayType.length > 0) {		
		$(modal).find("select[name='dialog-component-setting-group-display-type'] option[value="+groupDisplayType+"]").attr("selected", "selected");
	}else{
		$(modal).find("select[name='dialog-component-setting-group-display-type'] option[value="+1+"]").attr("selected", "selected");
	}
	
	if (column.width != undefined) {		
		$(modal).find("input[name='dialog-component-setting-element-width']").val(column.width);
	} else {		
		$(modal).find("input[name='dialog-component-setting-element-width']").val("25");
	}
	
	if (column.widthunit != undefined) {		
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
	
	if(column.screenDesignTextCodeListId != undefined && column.screenDesignTextCodeListId != "") {
		$(modal).find("input[name='screenNameCodeListAutocomplete']").val(column.screenDesignTextCodeListId);
	} else {
		$(modal).find("input[name='screenNameCodeListAutocomplete']").val("");
	}
	
	if(column.screenItemTextCodeListId != undefined && column.screenItemTextCodeListId != "") {
		arr = column.screenItemTextCodeListId.split(".");
		$(modal).find("input[name='screenItemCodeListAutocomplete']").val(arr[2]);
	} else {
		$(modal).find("input[name='screenItemCodeListAutocomplete']").val("");
	}
	
	if(column.screenDesignIdCodeListId != undefined && column.screenDesignIdCodeListId != "") {
		$(modal).find("input[name='screenItemCodeListAutocomplete']").attr("arg01",column.screenDesignIdCodeListId);
		$(modal).find("input[name='screenNameCodeList']").val(column.screenDesignIdCodeListId);
	} else {
		$(modal).find("input[name='screenNameCodeList']").val("");
	}
	
	if(column.screenItemTextCodeListId != undefined && column.screenItemTextCodeListId != "") {
		$(modal).find("input[name='screenItemCodeList']").val(column.screenItemTextCodeListId);
	} else {
		$(modal).find("input[name='screenItemCodeList']").val("");
	}
	if(column.screenItemIdCodeListId == undefined) {
		column.screenItemIdCodeListId = "";
	}
	if(column.screenItemTextCodeListId == undefined) {
		column.screenItemTextCodeListId = "";
	}
	if(column.screenDesignIdCodeListId == undefined) {
		column.screenDesignIdCodeListId = "";
	}
	if(column.screenItemIdCodeListId != undefined) {
		/*getCodelistItemByScreenItemId($(modal),column.screenItemIdCodeListId);*/
		getCodelistItemByScreenItemName($(modal),column.screenItemTextCodeListId,column.screenDesignIdCodeListId);
	}
	
	openProperties(modal, column);
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
		
		$(modal).find("input[name=maxlength]").val(column.maxlength);
		$(modal).find("select[name=validateRule]").val(column.formatcode);
		
		//fill data
		if(column.datatype != 21 && column.datatype != 1){
			$(modal).find("select[name=validateRule]").prop("disabled",true);
		}
	}
	if(column.datatype == 2){		
		$(modal).find("#trRequire").before("<tr id=\"trMinMaxValue\"><th class=\"com-table-th-text style-header-table\">"+dbMsgSource['sc.screendesign.0134']+"</th>" +
				"<td><input type=\"text\" class=\"form-control qp-input-from-integer pull-left\" name=\"minValue\" value=\""+minValue+"\" /><div class=\"qp-separator-from-to\">~</div><input type=\"text\" value=\""+minValue+"\" class=\"form-control qp-input-from-integer pull-right\" name=\"maxValue\" /></td></tr>");
	}
	if(column.datatype == 3 || column.datatype == 8){
		$(modal).find("#trRequire").before("<tr id=\"trMinMaxValue\"><th>"+dbMsgSource['sc.screendesign.0134']+"</th>" +
				"<td><input type=\"text\" class=\"form-control qp-input-from-float pull-left\" name=\"minValue\" value=\""+minValue+"\" /><div class=\"qp-separator-from-to\">~</div><input value=\""+maxValue+"\" type=\"text\" class=\"form-control qp-input-to-float pull-right\" name=\"maxValue\" /></td></tr>");
	}
	if(column.datatype == 4){
		$(modal).find("#trRequire").before("<tr id=\"trMinMaxValue\"><th class=\"com-table-th-text style-header-table\">"+dbMsgSource['sc.screendesign.0134']+"</th>" +
				"<td>"
				+ "<div class=\"input-group date qp-input-from-datepicker pull-left\">"
				+ 		"<input type=\"text\"name=\"minValue\" value=\""+minValue+"\" class=\"form-control\" />"
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
					+ 		"<input type=\"text\"name=\"minValue\" value=\""+minValue+"\" class=\"form-control\" />"
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
				+ 		"<input type=\"text\"name=\"minValue\" value=\""+minValue+"\" class=\"form-control\" />"
				+ 		"<span class=\"input-group-addon\">"
				+ 			"<span class=\"glyphicon glyphicon-time\"></span>"
				+ 		"</span>"
				+ "</div>"
				+ "<div class=\"qp-separator-from-to\">~</div>"
				+ "<div class=\"input-group date qp-input-to-timepicker pull-rigth\">"
				+ 		"<input type=\"text\" name=\"maxValue\"  value=\""+maxValue+"\" class=\"form-control\" />"
				+ 		"<span class=\"input-group-addon\">"
				+ 			"<span class=\"glyphicon glyphicon-time\"></span>"
				+ 		"</span>"
				+ "</div>"
				+ "</td></tr>");
	}
	
	$(modal).find("input[name=minValue]").val(column.minvalue);
	$(modal).find("input[name=maxValue]").val(column.maxvalue);
		
	if(column.require == 1){
		$(modal).find("input[name=require]").prop('checked', true);
	} else {
		$(modal).find("input[name=require]").prop('checked', false);
	}
	
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

	$.qp.initialConventionNameCode();
	setFirstTabActive($(modal));
	$.qp.initialAllPicker($(modal));
	$.qp.initialAutoNumeric($(modal));
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});
}

function openDialogLableSettingSection(obj){			
	var modal = $('#dialog-label-setting-section');
	
	var value =$(obj).closest("span").find("input[name=formElement]").val();
	
	$(modal).data("target", obj);
	$(modal).data("data", $(obj).closest("span").find("input[name=formElement]").val());
		
	var data = convertToJson(value);
	
	if (data.isBundle != undefined) {
		$(modal).find("input[name='isBundle']").val(data.isBundle);
	} else {		
		$(modal).find("input[name='isBundle']").val("false");
	}
		
	if (data.mandatory != undefined) {		
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
	
	$(modal).find("[name='font-style']").val("");
	
	//TungHT
	var areaType = $(obj).closest("div[class^='panel-body']").prev().find("input[name='formAreaType']").val();
	// Section
	if(areaType == 3) {
		if (data.width != undefined && data.width.length > 0 && data.width != "0") {		
			$(modal).find("input[name='dialog-component-setting-element-width']").val(data.width);
		} else {		
			$(modal).find("input[name='dialog-component-setting-element-width']").val("25");
		}
		
		if (data.widthunit != undefined && data.widthunit != "") {		
			$(modal).find("[name='dialog-component-setting-element-width-unit']").val(data.widthunit);
		} else {		
			$(modal).find("[name='dialog-component-setting-element-width-unit']").val("%");
		}
	}
	// Action
	if(areaType == 2) {
		if (data.width != undefined && data.width.length > 0 && data.width != "0") {		
			$(modal).find("input[name='dialog-component-setting-element-width']").val(data.width);
		} else {		
			$(modal).find("input[name='dialog-component-setting-element-width']").val("");
		}
		
		if (data.widthunit != undefined && data.widthunit != "") {		
			$(modal).find("[name='dialog-component-setting-element-width-unit']").val(data.widthunit);
		} else {		
			$(modal).find("[name='dialog-component-setting-element-width-unit']").val("%");
		}
	}
	displayStyle(modal, data);
	if(data.datatype == 20) {
		data.isBlank = false;
	}
	
	if (data.isBlank != undefined && eval(data.isBlank)) {
		$(modal).find("input[name='labelName']").val("");
		$(modal).find("input[name='labelNameAutocomplete']").val("");
		$.qp.disableAutocomplete($(modal).find("input[name='labelName']"));
	} else {
		$.qp.enableAutocomplete($(modal).find("input[name='labelName']"));
		if (data.isBundle != undefined && eval(data.isBundle)) {						
			$(modal).find("input[name='labelName']").val(data.label);
			$(modal).find("input[name='labelNameAutocomplete']").val(htmlDecode(data.labelText));
		} else {									
			if (data.labelText != undefined && data.labelText != ''){
				$(modal).find("input[name='labelNameAutocomplete']").val(htmlDecode(data.labelText));
			}
		}		
		
		openProperties(modal, data);
	}
	setFirstTabActive($(modal));
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});
}
function openSectionSetting(obj){
	//var column = convertToJson($(obj).closest("th").find("input").val());
	
	var modal = $("#modal-section-setting");
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
	openAreaStyle(modal, obj);
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
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:last").find("input[name=screenItemStoreId]").val(dataParameter[3]);
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:last").find("[name=parameterDatatype]").val(dataParameter[4]);
			
		});
	}
	
	$(modal).find("input[name='tableWidth']").trigger("touchspin.updatesettings", {
		verticalbuttons: true,
		min: 1,	    	    
	    stepinterval: 50,
	    step: 10
	});
	if($(modal).find("#modal-section-setting-form").length >0){
		initSelectFormIndex(obj,$(modal).find("#modal-section-setting-form"));
	}
	$(modal).find('input[name=formcode]').val($(obj).closest('.form-area-content').find('.form-content').find('input[name=screenFormFormActionCode]').val());
	openEventArea(modal, obj);
	$.qp.initialConventionNameCode();
	setFirstTabActive($(modal));
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});
}