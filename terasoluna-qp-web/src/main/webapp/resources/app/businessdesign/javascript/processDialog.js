/**
 * process event in dialog
 */
function saveDialogGetScope(thiz){
	var modal = $(thiz).closest(".modal");
	var obj = $(modal).data("target");
	$.qp.undoFormatNumericForm(modal);
	var scopeType = $(modal).find("input[name='scopeType']:checked").val();
	var scopeValue = $(modal).find("input[name='scopeValue']").val();
	var scopeValueAutocomplete = $(modal).find("input[name='scopeValueAutocomplete']").val();

	var dataTypeInput = $(modal).find("input[name='dataTypeInput']").val();
	var flagArrayInput = $(modal).find("input[name='arrayFlgInput']").val();
	var dataTypeSession = $(modal).find("input[name='scopeValue']").attr("datatype");
	var flagArraySession = $(modal).find("input[name='scopeValue']").attr("arrayflag");
	
	if (flagArraySession == "f") {
		flagArraySession = "false";
	}
	if (flagArraySession == "t") {
		flagArraySession = "true";
	}
	
	if(scopeType == 1 && !isEmptyQP(scopeValue)){
		var messages = "";
		if (!isEmptyQP(dataTypeInput)) {
			messages = validateDataTypeInputBean(dataTypeInput, flagArrayInput, dataTypeSession, flagArraySession);
		}
		
		if (messages != "") {
			$.qp.showAlertModal(messages);
			return false;
		}

		//change color
		$(obj).closest("div.dropdown").find(":button.glyphicon-menu-hamburger").removeClass("btn-info");
		$(obj).closest("div.dropdown").find(":button.glyphicon-menu-hamburger").addClass("btn-warning");
		$(obj).closest("td").prev().find("span.bd-in-screenitem-hidden").hide();
		
		$(obj).closest("td").find("input[name$='dataTypeSession']").val(dataTypeSession);
		$(obj).closest("td").find("input[name$='arrayFlagSession']").val(flagArraySession);
	}else{
		$(obj).closest("div.dropdown").find(":button.glyphicon-menu-hamburger").addClass("btn-info");
		$(obj).closest("div.dropdown").find(":button.glyphicon-menu-hamburger").removeClass("btn-warning");
		
		$(obj).closest("td").find("input[name$='dataTypeSession']").val(null);
		$(obj).closest("td").find("input[name$='arrayFlagSession']").val(null);
	}

	$(obj).closest("td").find("input[name*='scopeType']").val(scopeType);
	$(obj).closest("td").find("input[name*='scopeValue']").val(scopeValue);
	$(obj).closest("td").find("input[name*='scopeValueAutocomplete']").val(scopeValueAutocomplete);
	
	$(modal).modal("hide");
}
function saveDialogSetScope(thiz){
	var modal = $(thiz).closest(".modal");
	var obj = $(modal).data("target");
	$.qp.undoFormatNumericForm(modal);
	var scopeType = $(modal).find("input[name='scopeType']:checked").val();
	var scopeValue = $(modal).find("input[name='scopeValue']").val();
	var scopeValueAutocomplete = $(modal).find("input[name='scopeValueAutocomplete']").val();

	var dataTypeOutput = $(modal).find("input[name='dataTypeOutput']").val();
	var flagArrayOutput = $(modal).find("input[name='arrayFlgOutput']").val();
	var dataTypeSession = $(modal).find("input[name='scopeValue']").attr("datatype");
	var flagArraySession = $(modal).find("input[name='scopeValue']").attr("arrayflag");
	
	if (flagArraySession == "f") {
		flagArraySession = "false";
	}
	if (flagArraySession == "t") {
		flagArraySession = "true";
	}
	
	if(scopeType == 1 && !isEmptyQP(scopeValue)){
		var messages = "";
		if (!isEmptyQP(dataTypeOutput)) {
			var messages = validateDataTypeOutputBean(dataTypeSession, flagArraySession, dataTypeOutput, flagArrayOutput);
		}
		
		if (messages != "") {
			$.qp.showAlertModal(messages);
			return false;
		}

		//change color
		$(obj).closest("div.dropdown").find(":button.glyphicon-menu-hamburger").removeClass("btn-info");
		$(obj).closest("div.dropdown").find(":button.glyphicon-menu-hamburger").addClass("btn-warning");
		
		$(obj).closest("td").find("input[name$='dataTypeSession']").val(dataTypeSession);
		$(obj).closest("td").find("input[name$='arrayFlagSession']").val(flagArraySession);
	}else{
		$(obj).closest("div.dropdown").find(":button.glyphicon-menu-hamburger").addClass("btn-info");
		$(obj).closest("div.dropdown").find(":button.glyphicon-menu-hamburger").removeClass("btn-warning");
		
		$(obj).closest("td").find("input[name$='dataTypeSession']").val(null);
		$(obj).closest("td").find("input[name$='arrayFlagSession']").val(null);
	}
	
	$(obj).closest("td").find("input[name*='scopeType']").val(scopeType);
	$(obj).closest("td").find("input[name*='scopeValue']").val(scopeValue);
	$(obj).closest("td").find("input[name*='scopeValueAutocomplete']").val(scopeValueAutocomplete);
	
	$(modal).modal("hide");
}
function onchangeScopeTypeOfGetScope(thiz){
	var value = $(thiz).val();
	var modal = $(thiz).closest(".modal");
	$(modal).find("input[name*='scopeValue']").val("");
	if(value == "1"){
		$(modal).find("input[name*='scopeValue']").attr('disabled', false);
	}else{
		$(modal).find("input[name*='scopeValue']").attr('disabled', true);
	}
	$(modal).find("input[name='scopeValueAutocomplete']").each(function(){
		$(this).data("ui-autocomplete")._trigger("close");
	});
}

function validateDataTypeInputBean(dataTypeAssign, flagArrayAssign, dataTypeParameter, flagArrayParameter){
	var messages = "";
		
	if(!$.qp.businessdesign.validaionComponent.validateCastDataTypeRule(dataTypeAssign, flagArrayAssign, dataTypeParameter, flagArrayParameter)){
		messages += "<br/>";
		var dataTypeStr = CL_BD_DATATYPE[dataTypeAssign];
		if(eval(flagArrayAssign)){
			dataTypeStr += "[]";
		}
		messages +=  $.qp.getMessage('err.sys.0209').replace("{0}",$.qp.getModuleMessage("sc.businesslogicdesign.0039")).replace("{1}", $.qp.getModuleMessage('sc.businesslogicdesign.0111')).replace("{2}", dataTypeStr);
		valid = false;
	}

	return messages;
}

function validateDataTypeOutputBean(dataTypeAssign, flagArrayAssign, dataTypeParameter, flagArrayParameter){
	var messages = "";
		
	if(!$.qp.businessdesign.validaionComponent.validateCastDataTypeRule(dataTypeAssign, flagArrayAssign, dataTypeParameter, flagArrayParameter)){
		messages += "<br/>";
		var dataTypeStr = CL_BD_DATATYPE[dataTypeParameter];
		if(eval(flagArrayParameter)){
			dataTypeStr += "[]";
		}
		messages +=  $.qp.getMessage('err.sys.0209').replace("{0}",$.qp.getModuleMessage("sc.businesslogicdesign.0039")).replace("{1}", $.qp.getModuleMessage('sc.businesslogicdesign.0111')).replace("{2}", dataTypeStr);
		valid = false;
	}

	return messages;
}

function onchangeValue(event){
	var obj = event.target;
	var item = event.item;
	
	if ($(obj).val() != undefined && $(obj).val() != "") {
		$(obj).next().attr("datatype", item.output04);
		$(obj).next().attr("arrayflag", item.output05);
	} else{
		$(obj).next().attr("datatype", null);
		$(obj).next().attr("arrayflag", null);
	}
}