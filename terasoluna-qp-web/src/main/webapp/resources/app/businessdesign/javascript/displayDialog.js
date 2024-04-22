/**
 * initial dialog for business design
 */
function openDialogGetScope(obj, isOnlyView) {
	var modal = "#dialog-getscope-setting";
	$(modal).data("target", obj);
	var scopeType = $(obj).closest("td").find("input[name*='scopeType']").val();
	var scopeValue = $(obj).closest("td").find("input[name$='scopeValue']").val();
	var scopeValueAutocomplete = $(obj).closest("td").find("input[name$='scopeValueAutocomplete']").val();
	
	scopeType = (scopeType == undefined || scopeType == "") ? 0 : scopeType;
	//initial
	$(modal).find("input[name='scopeValue']").val("");
	$(modal).find("input[name='scopeValueAutocomplete']").val("");
	$(modal).find("input[name='scopeValueAutocomplete']").attr('disabled', true);
	
	// set data type & array flg
	var dataType = $(obj).closest("tr").find("[name$='dataType']").val();
	var arrayFlg;
	if ($(obj).closest("tr").find("input[name$='arrayFlg']").is(":checkbox")) {
		arrayFlg = $(obj).closest("tr").find("input[name$='arrayFlg']").is(":checked");
	} else {
		arrayFlg = $(obj).closest("tr").find("input[name$='arrayFlg']").val();
	}
	
	var dataTypeSession = $(obj).closest("tr").find("input[name$='dataTypeSession']").val();
	var arrayFlagSession = $(obj).closest("tr").find("input[name$='arrayFlagSession']").val();
	
	$(modal).find("input[name='dataTypeInput']").val(dataType);
	$(modal).find("input[name='arrayFlgInput']").val(arrayFlg);
	
	$(modal).find("input:radio[name='scopeType'][value="+scopeType+"]").prop('checked', true);
	if(scopeType == "1"){
		$(modal).find("input[name='scopeValue']").val(scopeValue);
		$(modal).find("input[name='scopeValueAutocomplete']").val(scopeValueAutocomplete);
		$(modal).find("input[name='scopeValueAutocomplete']").attr('disabled', false);
		
		if (!isEmptyQP(dataTypeSession) && !isEmptyQP(arrayFlagSession)) {
			$(modal).find("input[name='scopeValue']").attr('datatype', dataTypeSession);
			$(modal).find("input[name='scopeValue']").attr('arrayflag', arrayFlagSession);
		}
	}
	
	$.qp.initialAllPicker($(modal));
	$(modal).find("input[name='scopeValueAutocomplete']").each(function(){
		$(this).data("ui-autocomplete")._trigger("close");
	});
	initialViewModal(modal, isOnlyView);
	$(modal).modal({
		show : true,
		closable : false,
		keyboard : false,
		backdrop : 'static'
	});
}

function openDialogSetScope(obj, isOnlyView) {
	var modal = "#dialog-setscope-setting";
	$(modal).data("target", obj);
	var scopeType = $(obj).closest("td").find("input[name*='scopeType']").val();
	var scopeValue = $(obj).closest("td").find("input[name$='scopeValue']").val();
	var scopeValueAutocomplete = $(obj).closest("td").find("input[name$='scopeValueAutocomplete']").val();
	
	scopeType = (scopeType == undefined || scopeType == "") ? 0 : scopeType;
	//initial
	$(modal).find("input[name='scopeValue']").val("");
	$(modal).find("input[name='scopeValueAutocomplete']").val("");
	$(modal).find("input[name='scopeValueAutocomplete']").attr('disabled', true);
	
	// set data type & array flg
	var dataType = $(obj).closest("tr").find("[name$='dataType']").val();
	var arrayFlg;
	if ($(obj).closest("tr").find("input[name$='arrayFlg']").is(":checkbox")) {
		arrayFlg = $(obj).closest("tr").find("input[name$='arrayFlg']").is(":checked");
	} else {
		arrayFlg = $(obj).closest("tr").find("input[name$='arrayFlg']").val();
	}
	
	$(modal).find("input[name='dataTypeOutput']").val(dataType);
	$(modal).find("input[name='arrayFlgOutput']").val(arrayFlg);
	
	$(modal).find("input:radio[name='scopeType'][value="+scopeType+"]").prop('checked', true);
	if(scopeType == "1"){
		$(modal).find("input[name='scopeValue']").val(scopeValue);
		$(modal).find("input[name='scopeValueAutocomplete']").val(scopeValueAutocomplete);
		$(modal).find("input[name='scopeValueAutocomplete']").attr('disabled', false);
	}
	
	$.qp.initialAllPicker($(modal));
	$(modal).find("input[name='scopeValueAutocomplete']").each(function(){
		$(this).data("ui-autocomplete")._trigger("close");
	});
	initialViewModal(modal, isOnlyView);
	$(modal).modal({
		show : true,
		closable : false,
		keyboard : false,
		backdrop : 'static'
	});
}