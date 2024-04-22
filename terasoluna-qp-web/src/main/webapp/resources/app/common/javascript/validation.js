function validateRequired(modal,inputName,inputLabel,isList, isNotFocus) {
	var $Inputs = isList?$(modal).find("input[name="+inputName+"]"):$(modal).find("input[name="+inputName+"]").first();
	var messages="";
	var focusInput = null;
	$Inputs.each(function(index){
		if($.trim($(this).val())==''){
			if(!isList){
				if(focusInput==null) {
					focusInput = this;
				}
				messages += fcomMsgSource['err.sys.0025'].replace("{0}",inputLabel);
			} else {
				if(index>0) {
					messages+="\r\n";
				}
				if(focusInput==null) {
					focusInput = this;
				}
				messages += fcomMsgSource['err.sys.0077'].replace("{0}",inputLabel).replace("{1}",index+1);
			}
		}
	});
	if(messages!="") {
		$Inputs.closest(".modal-body").find("a[href='#"+$Inputs.closest(".tab-pane").attr("id")+"']").tab("show");
		if (isNotFocus == undefined || isNotFocus == null || isNotFocus == false ) {
			$(focusInput).focus();
		}
		$.qp.showAlertModal(messages);
		return false;
	}
	return true;
};
function validateSelectRequired(modal,inputName,inputLabel,isList, isNotFocus) {
	var $Inputs = isList?$(modal).find("select[name="+inputName+"]"):$(modal).find("input[name="+inputName+"]").first();
	var messages="";
	var focusInput = null;
	$Inputs.each(function(index){
		if($.trim($(this).val())==''){
			if(!isList){
				if(focusInput==null) {
					focusInput = this;
				}
				messages += fcomMsgSource['err.sys.0025'].replace("{0}",inputLabel);
			} else {
				if(index>0) {
					messages+="\r\n";
				}
				if(focusInput==null) {
					focusInput = this;
				}
				messages += fcomMsgSource['err.sys.0077'].replace("{0}",inputLabel).replace("{1}",index+1);
			}
		}
	});
	if(messages!="") {
		$Inputs.closest(".modal-body").find("a[href='#"+$Inputs.closest(".tab-pane").attr("id")+"']").tab("show");
		if (isNotFocus == undefined || isNotFocus == null || isNotFocus == false ) {
			$(focusInput).focus();
		}
		$.qp.alert(messages);
		return false;
	}
	return true;
};
function validateAlphanumeric(modal,inputName,inputLabel,isList, isNotFocus) {
	var $Inputs = isList?$(modal).find("input[name="+inputName+"]"):$(modal).find("input[name="+inputName+"]").first();
	var messages="";
	var focusInput = null;
	$Inputs.each(function(index){
		var alphanumericRegex = /^$|^[a-zA-Z0-9a-zA-Z(_)]+$/i;
		if(!alphanumericRegex.test($.trim($(this).val()))){
			if(!isList){
				if(focusInput==null) {
					focusInput = this;
				}
				messages += fcomMsgSource['err.sys.0066'].replace("{0}",inputLabel).replace("{1}","\\ / : * ? < > | white-space").replace('&quot;','').replace('&quot;','');
			} else {
				if(index>0) {
					messages+="\r\n";
				}
				if(focusInput==null) {
					focusInput = this;
				}
				messages += fcomMsgSource['err.sys.0094'].replace("{0}",inputLabel).replace("{1}",index+1).replace("{2}",index+1).replace('&quot;','').replace('&quot;','');
			}
		}
	});
	if(messages!="") {
		$Inputs.closest(".modal-body").find("a[href='#"+$Inputs.closest(".tab-pane").attr("id")+"']").tab("show");
		if (isNotFocus == undefined || isNotFocus == null || isNotFocus == false ) {
			$(focusInput).focus();
		}
		$.qp.showAlertModal(messages);
		return false;
	}
	return true;
};
function validateFromTo(modal ,fromInput ,toInput ,fromLabel ,toLabel, isNotFocus) {
	var $FromInput = $(modal).find("input[name="+fromInput+"]").first();
	var $ToInput = $(modal).find("input[name="+toInput+"]").first();
	var messages="";
	var minValue;
	var maxValue;
	var dataType = $FromInput.attr("datatype");
	try{
		if(dataType=='number') {
			maxValue = parseFloat($ToInput.autoNumeric('get')); 
		} else if(dataType=='date') {
			maxValue = $ToInput.closest(".date").data("DateTimePicker").date(); 
		} else {
			maxValue = $ToInput.val(); 
		}
	} catch(ex) {
		maxValue = $ToInput.val();
	}
	
	try{
		if(dataType=='number') {
			minValue = parseFloat($FromInput.autoNumeric('get')); 
		} else if(dataType=='date') {
			minValue = $FromInput.closest(".date").data("DateTimePicker").date(); 
		} else {
			minValue = $FromInput.val(); 
		}
	} catch(ex) {
		minValue = $FromInput.val();
	}
	
	if(maxValue<minValue){
		messages += fcomMsgSource['err.sys.0042'].replace("{0}",toLabel).replace("{1}",fromLabel);
	}
	if(messages!="") {
		$ToInput.closest(".modal-body").find("a[href='#"+$ToInput.closest(".tab-pane").attr("id")+"']").tab("show");
		if (isNotFocus == undefined || isNotFocus == null || isNotFocus == false ) {
			$ToInput.first().focus();
		}
		
		$.qp.showAlertModal(messages);
		return false;
	}
	return true;
};
function validateRowAndColSpan(modal, inputName,inputLabel, isNotFocus) {
	var $Inputs = $(modal).find("input[name="+inputName+"]").first();
	var messages="";
	if($.trim($Inputs.val())=='0'){
		
		messages += dbMsgSource['sc.screendesign.0501'].replace("{0}",inputLabel);
	}
	if(messages!="") {
		$Inputs.closest(".modal-body").find("a[href='#"+$Inputs.closest(".tab-pane").attr("id")+"']").tab("show");
		if (isNotFocus == undefined || isNotFocus == null || isNotFocus == false ) {
			$Inputs.first().focus();
		}
		$.qp.showAlertModal(messages);
		return false;
	}
	return true;
};
function validateRequiredForButton(){
	var messages = "";
	messages += dbMsgSource['sc.screendesign.0515'];
	$.qp.showAlertModal(messages);
	return false;
};
function maximunLableAndCompnentRow(modal, inputName,inputLabel, isNotFocus) {
	var $Inputs = $(modal).find("input[name="+inputName+"]").first();
	var messages="";
	if($.trim($Inputs.val()) > '5'){
		
		messages += dbMsgSource['sc.screendesign.0502'].replace("{0}",inputLabel);
	}
	if(messages!="") {
		$Inputs.closest(".modal-body").find("a[href='#"+$Inputs.closest(".tab-pane").attr("id")+"']").tab("show");
		if (isNotFocus == undefined || isNotFocus == null || isNotFocus == false ) {
			$Inputs.first().focus();
		}
		$.qp.showAlertModal(messages);
		return false;
	}
	return true;
};
function validateRequiredAtLeast(modal,tableId,tableLabel,atLeastNums){
	var messages = "";
	var $tableScope = $(modal).find("#"+tableId);
	var numberOfRows = $tableScope.find("tbody tr").length;
	if(numberOfRows < atLeastNums){
		messages += fcomMsgSource['err.sys.0104'].replace("{0}",tableLabel);
	}
	if(messages!="") {
		$tableScope.closest(".modal-body").find("a[href='#"+$tableScope.closest(".tab-pane").attr("id")+"']").tab("show");
		$.qp.showAlertModal(messages);
		return false;
	}
	return true;
};
function validateDuplication(modal,inputName,inputLabel, isNotFocus) {
	var $Inputs = $(modal).find("input[name="+inputName+"]");
	var messages="";
	for(var i=$Inputs.length-1;i>0;i--){
		for(var j=0;j<i;j++){
			if($.trim($Inputs.eq(i).val()) == $.trim($Inputs.eq(j).val())){
				messages +="\r\n" + fcomMsgSource['err.sys.0041'].replace("{0}",inputLabel).replace("{1}",i+1);
				break;
			}
		}
	}

	if(messages!="") {
		$($Inputs).closest(".modal-body").find("a[href='#"+$Inputs.closest(".tab-pane").attr("id")+"']").tab("show");
		if (isNotFocus == undefined || isNotFocus == null || isNotFocus == false ) {
			$Inputs.first().focus();
		}
		$.qp.showAlertModal(messages);
		return false;
	}
	return true;
};