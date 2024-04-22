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
	if (column.physicaldatatype != undefined && column.physicaldatatype != null) {
		$(modal).find("select[name='baseType']").val(column.physicaldatatype);
	} else {
		$(modal).find("select[name='baseType']").val(1);
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
	
	//bind data to dialog
	if (column.columnname != undefined) {
		$(modal).find("input[name='columnname']").val(column.columnname);
	} else {
		$(modal).find("input[name='columnname']").val("");
	}
	if(column.dialogAutocompleteCode != undefined && column.dialogAutocompleteCode.length >0 ){
		$(modal).find("input[name='dialogAutocompleteCode']").val(column.dialogAutocompleteCode);
		if(column.dialogAutocompleteText != undefined && column.dialogAutocompleteText.length >0)
			$(modal).find("input[name='dialogAutocompleteCodeAutocomplete']").val(column.dialogAutocompleteText);
		getInforOfAutocompleteCode(column.dialogAutocompleteCode,modal);
	}
	else{
		$(modal).find("input[name='dialogAutocompleteCode']").val('');
		$(modal).find("input[name='dialogAutocompleteCodeAutocomplete']").val('');
		$(modal).find("#srcgenAtcTable").text("");
		$(modal).find("#srcgenAtcSearchColumn").text("");
		$(modal).find("#srcgenAtcDisplayColumn").text("");
		$(modal).find("#srcgenAtcSubmitColumn").text("");
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
	
	if (column.baseType != undefined && column.baseType != null) {
		$(modal).find("select[name='baseType']").val(column.baseType);
	} else {
		$(modal).find("select[name='baseType']").val(1);
	}
	$(modal).find("input[name='dialogOnChangeEvent']").val(column.dialogOnChangeEvent);
	$(modal).find("input[name='dialogOnSelectEvent']").val(column.dialogOnSelectEvent);
	$.qp.initialAllPicker($(modal).find("input[name='dialog-component-setting-element-width']"));
	setFirstTabActive($(modal));
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
	//set basetype
	$(modal).find("td[basetype='dialog-component-list-setting-base-types']").html(setBaseType(data));
	$(modal).find("input[name=columnname]").val(data.columnname);
	
	var localCodelist = data.localCodelist;
	if(localCodelist == undefined || localCodelist.length == 0){
		localCodelist = 1;
	}
	
	$(modal).find("input:radio[name=localCodelist][value=2]").parent().hide();

	$(modal).find(".codelist-system").hide();
	$(modal).find(".codelist-table").hide();
	$(modal).find(".codelist-screen").hide();
	$(modal).find("#dialog-component-list-setting-tbl-options").find("tbody").empty();
	$(modal).find("#dialog-component-list-setting-tbl-table-options").find("tbody").empty();
	$(modal).find("#dialog-component-list-setting-tbl-system-options").find("tbody").empty();
	$(modal).find("input[name=codelistCode]").val("");
	$(modal).find("input[name=codelistCodeAutocomplete]").val("");
	if(data.tablecolumncode != undefined){
		loadTableCodeList($(modal).find('input:radio[name="localCodelist"]'),data.tablecolumncode);
		var table = $(modal).find("#dialog-component-list-setting-tbl-table-options");
		if(table != undefined && $(table).find("tbody").find("tr").length > 0){
			$(modal).find("input:radio[name=localCodelist][value=2]").parent().show();
		}
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
		if (data.width != undefined && data.width.length >0 && data.width != "0") {		
			$(modal).find("input[name='dialog-component-list-setting-width']").val(data.width);
		} else {		
			$(modal).find("input[name='dialog-component-list-setting-width']").val("25");
		}
		
		if (data.widthunit != undefined) {		
			$(modal).find("[name='dialog-component-list-setting-width-unit']").val(data.widthunit);
		} else {		
			$(modal).find("[name='dialog-component-list-setting-width-unit']").val("%");
		}
	} else {
		if (data.width != undefined && data.width.length >0 && data.width != "0") {		
			$(modal).find("input[name='dialog-component-setting-element-width']").val(data.width);
		} else {
			$(modal).find("input[name='dialog-component-setting-element-width']").val("25");
		}
		
		if (data.widthunit != undefined) {		
			$(modal).find("[name='dialog-component-setting-element-width-unit']").val(data.widthunit);
		} else {		
			$(modal).find("[name='dialog-component-setting-element-width-unit']").val("%");
		}
	}
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
	setFirstTabActive($(modal));
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
	
	//set basetype
	$(modal).find("td[basetype='dialog-component-setting-base-types']").html(setBaseType(column));
	if (column.datatype == 21) {
		$(modal).find("select[name='baseType'] option").remove();
		var options = ""
		for (var i = 1; i <= parseInt(itemsBaseType['total']); i++) {
			options+= "<option value=\""+i+"\"><lable>"+itemsBaseType[i]+"</label></option>";
		}
		$(modal).find("#baseStypeDynamicLink").show();
		$(modal).find("select[name='baseType']").append(options);
	} else {
		$(modal).find("tr[id='baseStypeDynamicLabel']").hide();
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
		
		$(modal).find("input[name=maxlength]").val(column.maxlength);
		$(modal).find("select[name=validateRule]").val(column.formatcode);
		
		//fill data
		if(column.datatype != 21 && column.datatype != 18){
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
	
//	$(modal).find("#trOptionList").remove();
//	if(column.msgvalue != undefined &&column.msgvalue.length > 0){
//		if(column.physicaldatatype == 7){
//			var msgLabelArr = column.msglabel.split("�");
//			var msgValArr = column.msgvalue.split("�");
//			
//			var content = "<tr id=\"trOptionList\"><td colspan=\"2\"><b>"+dbMsgSource['sc.screendesign.0094']+" </b><br/>" +
//			"<table id=\"tableDialogFormOptions\" class=\"table table-bordered qp-table-form\" dataType=\""+column.datatype+"\"><thead><tr class=\"style-even-row\">" + 
//				"<th width=\"1%\" class=\"style-header-table\">"+dbMsgSource['sc.sys.0004']+"</th>" + 
//				"<th>"+dbMsgSource['sc.screendesign.0096']+"</th>" +
//				"<th>"+dbMsgSource['sc.screendesign.0097']+" <label class=\"qp-required-field\">"+fcomMsgSource['sc.sys.0029']+"</label></th>" +
//			"</tr></thead><tbody>";
//				
//			$(msgValArr).each(function(i){
//				content += "<tr>"+
//				"<td class=\"com-output-fixlength tableIndex\">"+(i+1)+"</td>"+
//				"<td class=\"com-output-fixlength\"><input type=\"text\" class=\"form-control qp-input-text\" name=\"srcgenOptionName\" value=\""+ msgLabelArr[i] +"\" maxlength=\"200\"/></td>" +
//				"<td class=\"com-output-fixlength\"><input type=\"text\" class=\"form-control qp-input-text com-disable\" readonly=\"readonly\" name=\"srcgenOptionValue\" value=\""+ msgValArr[i] +"\" maxlength=\"200\"/></td>" +
//				"</tr>";
//			});
//			
//			$(modal).find("#trRequire").after(content);
//		} else {
//			var msgLabelArr = column.msglabel.split("�");
//			var msgValArr = column.msgvalue.split("�");
//			var content = "<tr id=\"trOptionList\"><td colspan=\"2\"><b>"+dbMsgSource['sc.screendesign.0094']+" </b><br/><div style=\"width: 95%; padding-left: 3%;\">" +
//							"<table class=\"table table-bordered qp-table-form\">" +
//								"<tbody>" +
//								"<tr>" +
//									"<th width=\"30%\">Codelist code</th>" + 
//									"<td><select class=\"combobox input-large form-control\" style=\"float: left;\"></select><input type=\"checkbox\" style=\"float: left; margin-top: 5px\"/><span>(enable)</span></td>" +
//								"</tr>" +
//								"</tbody>" +
//							"</table>" +
//							"<br/>" +
//						"<div style=\"padding-bottom: 10px;\" class=\"com-sub-title\">" + 
//						"<span style=\"float: left;\"><input type=\"radio\" name=\"localCodelist\" checked=\"checked\"/>Screen Codelist setting&nbsp;&nbsp;&nbsp;<input name=\"localCodelist\" type=\"radio\"/>Table Codelist</span>" + 
//						"<input style=\"float: right;\" type=\"checkbox\" onclick=\"changeSupportOptionValue()\" id=\"supportOptionValue\" " +
//						"name=\"supportOptionValue\" checked=\"checked\"><label style=\"float: right;\" for=\"supportOptionValue\"> "+dbMsgSource['sc.screendesign.0135']+"</label></div>" +
//						"<table id=\"tableDialogFormOptions\" class=\"table table-bordered qp-table-form\" datatype=\""+column.physicaldatatype+"\"><thead><tr class=\"style-even-row\">" + 
//							"<th width=\"1%\" class=\"style-header-table\">"+dbMsgSource['sc.sys.0004']+"</th>";
//			if(column.msglabel.length == 0){
//				content += "<th class=\"style-header-table\" style=\"display: none;\">"+dbMsgSource['sc.screendesign.0096']+" <label class=\"qp-required-field\">"+fcomMsgSource['sc.sys.0029']+"</label></th>";
//			} else {
//				content += "<th class=\"style-header-table\">"+dbMsgSource['sc.screendesign.0096']+" <label class=\"qp-required-field\">"+fcomMsgSource['sc.sys.0029']+"</label></th>";
//			}
//			content += "<th class=\"style-header-table\">"+dbMsgSource['sc.screendesign.0097']+" <label class=\"qp-required-field\">"+fcomMsgSource['sc.sys.0029']+"</label></th>" +
//							"<th width=\"1%\" class=\"style-header-table\">&nbsp;</th>" +
//						"</tr></thead><tbody>";
//			$(msgValArr).each(function(i){
//				content += "<tr>"+
//							"<td class=\"com-output-fixlength tableIndex\">"+(i+1)+"</td>";
//				if(column.msglabel.length == 0){
//					content += "<td class=\"com-output-fixlength\" style=\"display: none;\"><input type=\"text\" class=\"form-control qp-input-text\" name=\"srcgenOptionName\" value=\"\" maxlength=\"200\"/></td>";
//				} else {
//					content += "<td class=\"com-output-fixlength\"><input type=\"text\" class=\"form-control qp-input-text\" name=\"srcgenOptionName\" value=\""+ msgLabelArr[i] +"\" maxlength=\"200\"/></td>";
//				}
//				if(column.physicaldatatype == 2){
//					content += "<td class=\"com-output-fixlength\"><input type=\"text\" class=\"com-input-integer\" name=\"srcgenOptionValue\" value=\""+ msgValArr[i] +"\" maxlength=\"200\"/></td>";
//				} else {
//					content += "<td class=\"com-output-fixlength\"><input type=\"text\" class=\"form-control qp-input-text\" name=\"srcgenOptionValue\" value=\""+ msgValArr[i] +"\" maxlength=\"200\"/></td>";
//				}
//				content += "<td class=\"com-output-fixlength\">" +
//								"<a href=\"javascript:\" style=\"margin-top: 3px;\" onclick=\"removeOptionJS(this);\" class=\"ui-icon ui-icon-minus\" title=\""+dbMsgSource['sc.screendesign.0139']+"\"></a>" +
//							"</td>" +
//						"</tr>";
//			});
//			content += "</tbody></table>" +
//					"<a href=\"javascript:\" style=\"margin-top: 5px;\" onclick=\"addOptionJS(this);\" class=\"ui-icon ui-icon-plus\" title=\""+dbMsgSource['sc.screendesign.0138']+"\"></a>" +
//					"</div></td>";
//			$(modal).find("#trRequire").after(content);
//			
//			if(column.msglabel.length == 0){
//				$(modal).find("input[name=supportOptionValue]").prop('checked', false);
//			}
//		}		
//	}
	
	
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
	
	// daipv: add display default value for width of element [Label]
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
	
	if(data.datatype == 20) {
		data.isBlank = false;
	}
	
	displayStyle(modal, data);
	
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
	$(modal).find('input[name=formcode]').val($(obj).closest('.form-area-content').find('.form-content').find('input[name=screenFormFormActionCode]').val());
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
	openEventArea(modal, obj);
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
	setFirstTabActive($(modal));
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});
}