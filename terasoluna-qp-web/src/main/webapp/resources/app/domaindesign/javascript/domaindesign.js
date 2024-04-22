function changeDataType(event) {
	resetValue();
	if ($("input[name='baseTypeAutocomplete']").val() != null && $("input[name='baseTypeAutocomplete']").val() != '') {
		$("#2").show();
	} else {
		$("#2").hide();
	}
	if ($("input[name=baseType]").val() == "9") {
		$("#2").hide();
	}
		
	if(event.item != null || event.item == ""){
		$("#groupBasetypeId").val(event.item.output01);
		$("#selectValidationRule").removeAttr("disabled");
		$("#actionRemove").removeAttr("disabled");
		var baseType = $("input[name=baseType]").val();
		$("#tblFmtCode tbody").find("tr:first").nextAll().remove();
		var groupBasetypeId = parseInt($("#groupBasetypeId").val());
		processingForChangeDataType(baseType);
		switch(parseInt(groupBasetypeId)) {
			case DATATYPE.TEXT:
				if (CHARACTER_TYPE.TEXT != baseType) {
					$("#maxLength").val(initializeMaxLengthDefault(baseType));
					$("#precision").val(initializePrecisionDefault(baseType));
					break;
				}
			case DATATYPE.CHAR:
			case DATATYPE.CURRENCY:
			case DATATYPE.DECIMAL:
				$("#maxLength").val(initializeMaxLengthDefault(baseType));
				$("#precision").val(initializePrecisionDefault(baseType));
				break;
		}
		var selected = $("#tblFmtCode tbody").find("tr").last().find("td:first").find("select").find("option");
		if(selected.length == 1){
			if(selected.val() == ""){
				$("#fmtCodeRequired").hide();
			}
		}
		if ($("#tblFmtCode tbody").find("tr").last().find("td:first").find("select").val() == "") {
			$("#actionButton").attr("disabled", "disabled");
		} else {
			$("#actionButton").removeAttr("disabled");
		}
	}else {
		$("#tblFmtCode tbody").find("tr:first").nextAll().remove();
		$("#actionButton").attr("disabled", "disabled");
		$("#selectValidationRule").attr("disabled", "disabled");
		$("#selectValidationRule").val("");
		$("#actionRemove").attr("disabled", "disabled");
	}
}

function setReadOnlySelect(flag) {
	$('#fmtCode option:not(:selected)').attr('disabled',flag);
	/*if (flag) {
		$("#fmtCodeRequired").hide();
	} else {
		$("#fmtCodeRequired").show();
	}*/
}

/**
 * processing for intit
 * @param event
 */
function init() {

	var groupId = $("#groupBasetypeId").val();
	var baseType = $("input[name=baseType]").val();
//	if (groupId == '')
//		 return;

	switch(parseInt(groupId)) {
		case DATATYPE.TEXT:
			if (CHARACTER_TYPE.TEXT == baseType) {
				$("#precision").prop("readonly", true);
				$("#maxLength").prop("readonly", true);
				setReadOnlySelect(false);
				break;
			}
		case DATATYPE.CHAR:
			console.log ("char or text");
			$("#precision").prop("readonly", true);
			$("#maxLength").prop("readonly", false);
			setReadOnlySelect(false);
			$("#maxLengthRequired").show();
			$("#fmtCodeRequired").show();
			break;
		/*case DATATYPE.INTEGER:
			console.log ("Integer");
			$("#precision").prop("readonly", true);
			$("#maxLength").prop("readonly", true);
			setReadOnlySelect(true);
			$("#fmtCode").val("Integer");
			$("#maxLengthRequired").show();
			$("#fmtCodeRequired").show();
			break;*/
		case DATATYPE.DECIMAL:
			console.log ("DECIMAL");
			var baseType = $("input[name=baseType]").val();
			$("#precision").prop("readonly", false);
			$("#maxLength").prop("readonly", false);
			if (parseInt(baseType) == NUMERIC_TYPE.NUMBERIC) {
				setReadOnlySelect(true);
			} else {
				disableBaseTypeLength();
			}
			$("#fmtCode").val("Decimal");
			$("#maxLengthRequired").show();
			$("#fmtCodeRequired").show();
			break;
		/*case DATATYPE.DATE:
			console.log ("Date");
			$("#precision").prop("readonly", true);
			$("#maxLength").prop("readonly", true);
			$("#maxLengthRequired").show();
			$("#fmtCodeRequired").show();
			setReadOnlySelect(true);
			$("#fmtCode").val("Date");
			break;
		case DATATYPE.DATETIME:
			console.log ("datetime");
			$("#precision").prop("readonly", true);
			$("#maxLength").prop("readonly", true);
			$("#maxLengthRequired").show();
			$("#fmtCodeRequired").show();
			setReadOnlySelect(true);
			$("#fmtCode").val("DateTime");

			break;
		case DATATYPE.TIME:
			console.log ("Time");
			$("#precision").prop("readonly", true);
			$("#maxLength").prop("readonly", true);
			$("#maxLengthRequired").show();
			setReadOnlySelect(true);
			$("#fmtCode").val("Time");
			$("#fmtCodeRequired").show();
			break;*/
		case DATATYPE.CURRENCY:
			console.log ("CURRENCY");
			$("#precision").prop("readonly", false);
			$("#maxLength").prop("readonly", false);
			$("#maxLengthRequired").show();
			setReadOnlySelect(true);
			$("#fmtCode").val("Currency");
			$("#fmtCodeRequired").show();
			break;
		/*case DATATYPE.BOOLEAN:
			$("#precision").prop("readonly", true);
			$("#maxLength").prop("readonly", true);
			$("#maxLengthRequired").show();
			setReadOnlySelect(true);
			$("#fmtCode").val("");
			$("#fmtCodeRequired").hide();
			break;*/
		default:
			$("#precision").prop("readonly", true);
			$("#maxLength").prop("readonly", true);
			$("#maxLengthRequired").hide();
			$("#fmtCodeRequired").hide();
			setReadOnlySelect(true);
			break;
	}

}

/**
 * processing when change data type
 */
function processingForChangeDataType(baseType) {
	var groupId = $("#groupBasetypeId").val();
	$("#selectValidationRule").prop("required","required");
	listOptionKey =[];
	
	for(var i = 0; i < listObj.length; i++){
		var arrayGroup = listObj[i].group.split(";");
		for(var j = 0; j < arrayGroup.length; j++){
			if(arrayGroup[j]==groupId){
				listOptionKey.push(listObj[i]);
			}
		}
	}
	
	var lastSelect = $("#tblFmtCode tbody").find("tr").last().find("td:first");
	var selectField = lastSelect.find("select");
	var options = '';
	selectField.empty();
	if(listOptionKey.length == 1){
		for ( var i = 0; i < listOptionKey.length; i++) {
			options += '<option value="' + listOptionKey[i].code + '"'+ 'include ="' + listOptionKey[i].include +'"' +'>' + listOptionKey[i].name + '</option>';
		}
		
	}else{
	options += '<option value="' + "" + '">' + dbMsgSource["sc.domaindesign.0052"] + '</option>';
	for ( var i = 0; i < listOptionKey.length; i++) {
			options += '<option value="' + listOptionKey[i].code + '"'+ 'include ="' + listOptionKey[i].include +'"' +'>' + listOptionKey[i].name + '</option>';
		}
	}
	selectField.append(options);
	
	for( var j = 0; j < listObj.length; j++){
		if(listObj[j].code == selectField.val()){
			$("#selectValidationRule").attr("title", listObj[j].remark);
		}
	}
	
	var columnDefault = $("#columnDefault");
	var columnMinMax = $("#columnMinMax");

	var htmlDefaultVal = "";
	var htmlMinMaxVal = "";
	$("#selectValidationRule").prop("disabled", true);
	switch(parseInt(groupId)) {
		case DATATYPE.TEXT:
			if (CHARACTER_TYPE.TEXT == baseType) {
				console.log ("text");
				$("#precision").prop("readonly", true);
				$("#maxLength").prop("readonly", true);
				$("#maxLengthRequired").hide();
				$("#fmtCode").val("");
				setReadOnlySelect(false);
				$("#fmtCodeRequired").show();
				$("#precision").val("");
				$("#maxLength").val("");
				$("#selectValidationRule").removeAttr("disabled");
				htmlDefaultVal = "<input type=\"text\" class=\"form-control qp-input-text\" name=\"defaultValue\" maxlength=\"200\" value=\"\" />";
				columnDefault.html("").append(htmlDefaultVal);
				columnMinMax.html("");
				break;
			} else {
				$("#selectValidationRule").removeAttr("disabled");	
			}
		case DATATYPE.CHAR:
			console.log ("char varying or text");
			$("#precision").prop("readonly", true);
			$("#maxLength").prop("readonly", false);
			$("#maxLengthRequired").show();
			$("#fmtCode").val("");
			setReadOnlySelect(false);
			$("#fmtCodeRequired").show();
			$("#precision").val("");
			/*$("#maxLength").val("");*/
			$("#selectValidationRule").removeAttr("disabled");
			htmlDefaultVal = "<input type=\"text\" class=\"form-control qp-input-text\" name=\"defaultValue\" maxlength=\"200\" value=\"\" />";
			columnDefault.html("").append(htmlDefaultVal);
			columnMinMax.html("");
			break;
		case DATATYPE.INTEGER:
//			$("#precision").prop("readonly", true);
//			$("#maxLength").prop("readonly", true);
//			$("#maxLengthRequired").show();
			disableBaseTypeLength();
			$("#fmtCode").val("Integer");
			setReadOnlySelect(false);
			$("#fmtCodeRequired").show();
			$("#selectValidationRule").removeAttr("disabled");
			
			break;
		case DATATYPE.DECIMAL:
			console.log ("DECIMAL");
			if (parseInt(baseType) == NUMERIC_TYPE.NUMBERIC) {
				$("#precision").prop("readonly", false);
				$("#maxLength").prop("readonly", false);
				$("#maxLengthRequired").show();
				$("#fmtCode").val("Decimal");
				setReadOnlySelect(true);
				$("#fmtCodeRequired").show();
				htmlDefaultVal = "<input type=\"text\" class=\"form-control qp-input-float\" name=\"defaultValue\" maxlength=\"200\" value=\"\" />";
				columnDefault.html("").append (htmlDefaultVal);

				htmlMinMaxVal = "<input type=\"text\" name=\"minVal\" class=\"form-control qp-input-from-float pull-left\" /> ";
				htmlMinMaxVal += "<div class=\"qp-separator-from-to\">~</div>";
				htmlMinMaxVal += "<input type=\"text\" name=\"maxVal\" class=\"form-control qp-input-to-float pull-right\" />";

				columnMinMax.html("").append(htmlMinMaxVal);;
			} else {
				disableBaseTypeLength();
			}
			$.qp.formatFloat($('.qp-input-float'));
			$.qp.formatFloat($('.qp-input-from-float'));
			$.qp.formatFloat($('.qp-input-to-float'));
			$("#tblFmtCode tbody").find('tr').not(':first').remove();
			selectField.empty();
			options = '<option value="'+'" include = "'+'">All</option>';
			selectField.append(options);
			$("#selectValidationRule").prop("disabled", true);
			$("#actionButton").attr("disabled", "disabled");
			if ($("#selectValidationRule").prop('required')){
				$("#selectValidationRule").removeAttr("required");
			}
			break;
		/*case DATATYPE.DATE:
			console.log ("Date");
//			$("#precision").prop("readonly", true);
//			$("#maxLength").prop("readonly", true);
//			$("#maxLengthRequired").show();
			disableBaseTypeLength();
			$("#fmtCodeRequired").show();
			$("#fmtCode").val("Date");
			setReadOnlySelect(true);

			htmlDefaultVal = "<div class='input-group date qp-input-datepicker'>";
			htmlDefaultVal += "<input type=\"text\" name=\"defaultValue\" class=\"form-control\" />";
			htmlDefaultVal += "<span class=\"input-group-addon\">";
			htmlDefaultVal += "<span class=\"glyphicon glyphicon-calendar\"></span>";
			htmlDefaultVal += "</span>";
			htmlDefaultVal += "</div>";
			
			columnDefault.html("").append (htmlDefaultVal);

			htmlMinMaxVal = "<div class='input-group date qp-input-from-datepicker pull-left'>";
			htmlMinMaxVal += "<input type=\"text\" name=\"minVal\" class=\"form-control\" />";
			htmlMinMaxVal += "<span class=\"input-group-addon\">";
			htmlMinMaxVal += "<span class=\"glyphicon glyphicon-calendar\"></span>";
			htmlMinMaxVal += "</span>";
			htmlMinMaxVal += "</div>";
			htmlMinMaxVal += "<div class=\"qp-separator-from-to\">~</div>";
			htmlMinMaxVal += "<div class='input-group date qp-input-to-datepicker pull-rigth'>";
			htmlMinMaxVal += "<input type=\"text\" name=\"maxVal\" class=\"form-control\" />";
			htmlMinMaxVal += "<span class=\"input-group-addon\">";
			htmlMinMaxVal += "<span class=\"glyphicon glyphicon-calendar\"></span>";
			htmlMinMaxVal += "</span>";
			htmlMinMaxVal += "</div>";
			
			columnMinMax.html("").append(htmlMinMaxVal);;

			$.qp.initialDatePicker($(".qp-input-datepicker"));
			$.qp.initialDatePicker($(".qp-input-from-datepicker"));
			$.qp.initialDatePicker($(".qp-input-to-datepicker"));

			break;
		case DATATYPE.DATETIME:
			console.log ("datetime");
//			$("#precision").prop("readonly", true);
//			$("#maxLength").prop("readonly", true);

			$("#fmtCode").val("DateTime");
			setReadOnlySelect(true);
//			$("#maxLengthRequired").show();
			$("#fmtCodeRequired").show();
			disableBaseTypeLength();

			htmlDefaultVal = "<div class='input-group date qp-input-datetimepicker-detail'>";
			htmlDefaultVal += "<input type=\"text\" name=\"defaultValue\" class=\"form-control\" />";
			htmlDefaultVal += "<span class=\"input-group-addon\">";
			htmlDefaultVal += "<span class=\"glyphicon glyphicon-calendar\"></span>";
			htmlDefaultVal += "</span>";
			htmlDefaultVal += "</div>";
			
			columnDefault.html("").append (htmlDefaultVal);

			htmlMinMaxVal = "<div class='input-group date qp-input-from-datetimepicker-detail pull-left'>";
			htmlMinMaxVal += "<input type=\"text\" name=\"minVal\" class=\"form-control\" />";
			htmlMinMaxVal += "<span class=\"input-group-addon\">";
			htmlMinMaxVal += "<span class=\"glyphicon glyphicon-calendar\"></span>";
			htmlMinMaxVal += "</span>";
			htmlMinMaxVal += "</div>";
			htmlMinMaxVal += "<div class=\"qp-separator-from-to\">~</div>";
			htmlMinMaxVal += "<div class='input-group date qp-input-to-datetimepicker-detail pull-rigth'>";
			htmlMinMaxVal += "<input type=\"text\" name=\"maxVal\" class=\"form-control\" />";
			htmlMinMaxVal += "<span class=\"input-group-addon\">";
			htmlMinMaxVal += "<span class=\"glyphicon glyphicon-calendar\"></span>";
			htmlMinMaxVal += "</span>";
			htmlMinMaxVal += "</div>";

			columnMinMax.html("").append(htmlMinMaxVal);;

			$.qp.initialDateTimePicker($(".qp-input-datetimepicker-detail"));
			$.qp.initialDateTimePicker($(".qp-input-from-datetimepicker-detail"));
			$.qp.initialDateTimePicker($(".qp-input-to-datetimepicker-detail"));

			break;
		case DATATYPE.TIME:
			console.log ("Time");
//			$("#precision").prop("readonly", true);
//			$("#maxLength").prop("readonly", true);
//			$("#maxLengthRequired").show();
			$("#fmtCode").val("Time");
			setReadOnlySelect(true);
			$("#fmtCodeRequired").show();
			disableBaseTypeLength();

			htmlDefaultVal = "<div class='input-group date qp-input-timepicker'>";
			htmlDefaultVal += "<input type=\"text\" name=\"defaultValue\" class=\"form-control\" />";
			htmlDefaultVal += "<span class=\"input-group-addon\">";
			htmlDefaultVal += "<span class=\"glyphicon glyphicon-time\"></span>";
			htmlDefaultVal += "</span>";
			htmlDefaultVal += "</div>";

			columnDefault.html("").append (htmlDefaultVal);

			htmlMinMaxVal = "<div class='input-group date qp-input-from-timepicker pull-left'>";
			htmlMinMaxVal += "<input type=\"text\" name=\"minVal\" class=\"form-control\" />";
			htmlMinMaxVal += "<span class=\"input-group-addon\">";
			htmlMinMaxVal += "<span class=\"glyphicon glyphicon-time\"></span>";
			htmlMinMaxVal += "</span>";
			htmlMinMaxVal += "</div>";
			htmlMinMaxVal += "<div class=\"qp-separator-from-to\">~</div>";
			htmlMinMaxVal += "<div class='input-group date qp-input-to-timepicker pull-rigth'>";
			htmlMinMaxVal += "<input type=\"text\" name=\"maxVal\" class=\"form-control\" />";
			htmlMinMaxVal += "<span class=\"input-group-addon\">";
			htmlMinMaxVal += "<span class=\"glyphicon glyphicon-time\"></span>";
			htmlMinMaxVal += "</span>";
			htmlMinMaxVal += "</div>";
			
			columnMinMax.html("").append(htmlMinMaxVal);;

			$.qp.initialTimePicker($(".qp-input-timepicker"));
			$.qp.initialTimePicker($(".qp-input-from-timepicker"));
			$.qp.initialTimePicker($(".qp-input-to-timepicker"));
			break;*/
		case DATATYPE.CURRENCY:
			console.log ("CURRENCY");
			$("#precision").prop("readonly", false);
			$("#maxLength").prop("readonly", false);
			$("#maxLengthRequired").show();
			$("#fmtCode").val("Currency");
			setReadOnlySelect(true);
			$("#fmtCodeRequired").show();
			htmlDefaultVal = "<input type=\"text\" class=\"form-control qp-input-currency\" name=\"defaultValue\" maxlength=\"200\" value=\"\" />";
			columnDefault.html("").append (htmlDefaultVal);

			htmlMinMaxVal = "<input type=\"text\" name=\"minVal\" class=\"form-control qp-input-from-currency pull-left\" /> ";
			htmlMinMaxVal += "<div class=\"qp-separator-from-to\">~</div>";
			htmlMinMaxVal += "<input type=\"text\" name=\"maxVal\" class=\"form-control qp-input-to-currency pull-right\" />";

			columnMinMax.html("").append(htmlMinMaxVal);;

			$.qp.formatCurrency($('.qp-input-currency'));
			$.qp.formatCurrency($('.qp-input-from-currency'));
			$.qp.formatCurrency($('.qp-input-to-currency'));
			$("#tblFmtCode tbody").find('tr').not(':first').remove();
			selectField.empty();
			options = '<option value="'+'" include = "'+'">All</option>';
			selectField.append(options);
			$("#selectValidationRule").removeAttr("disabled");
			$("#actionButton").attr("disabled", "disabled");
			if ($("#selectValidationRule").prop('required')){
				$("#selectValidationRule").removeAttr("required");
			}
			break;
		/*case DATATYPE.BOOLEAN:
//			$("#precision").prop("readonly", true);
//			$("#maxLength").prop("readonly", true);
//			$("#maxLengthRequired").show();
			disableBaseTypeLength();
			$("#fmtCode").val("");
			setReadOnlySelect(true);
			$("#fmtCodeRequired").hide();
			htmlDefaultVal = "<input type=\"checkbox\" class=\"qp-input-checkbox qp-input-checkbox-margin\" name=\"defaultValue\" value=\"TRUE\" />";

			columnDefault.html(htmlDefaultVal);
			columnMinMax.html("");
			break;*/
		default:
			$("#precision").prop("readonly", true);
			$("#maxLength").prop("readonly", true);

			$("#maxLength").val("");
			$("#fmtCode").val("");
			$("#maxLengthRequired").hide();
			setReadOnlySelect(true);
			$("#fmtCodeRequired").hide();
			columnDefault.html("");
			columnMinMax.html("");
			//Add Start 20160302 TuanNN fix validate
			$("#tblFmtCode tbody").find('tr').not(':first').remove();
			selectField.empty();
			options = '<option value="'+'" include = "'+'">All</option>';
			selectField.append(options);
			$("#selectValidationRule").prop("disabled", true);
			$("#actionButton").attr("disabled", "disabled");
			if ($("#selectValidationRule").prop('required')){
				$("#selectValidationRule").removeAttr("required");
			}
			//Add End 20160302 TuanNN fix validate
			break;
	}
}

/**
 * 
 */
function processingValidationRule() {
	var validationRules = '';
	var validationRulesSelected = '';
	$('#tblFmtCode tbody>tr').each(function() {
		var objSelected =  $(this).find('.qp-input-select>option:selected');
		var include = $(objSelected).attr("include");
		if (include != undefined && include != '') {
			validationRules += ";" + $(objSelected).attr("include");
		}
		validationRulesSelected += ";" + $(objSelected).val();
	});
	
	var disableActionButton = 1;
	if (validationRules != undefined && validationRules != '') {
		var arrValidationRules = $.qp.arrayUnique(validationRules.split(";"));
		var arrValidationRulesSelected = $.qp.arrayUnique(validationRulesSelected.split(";"));
		
		var validationRulesValid = ";";
		$.each(arrValidationRules, function(i, value){
			
			if (value != '') {
				if(jQuery.inArray(value, arrValidationRulesSelected) != -1) {
					console.log("selected");
				} else {
					validationRulesValid += value + ";";
					disableActionButton = 0;
				}
			}
		})
		
		if (arrValidationRules.length == arrValidationRulesSelected.length + 1 ) {
			disableActionButton = 1;
		}

		$("#validationRulesValid").val(validationRulesValid);
	} else {
		$("#validationRulesValid").val('');
	}
	if (disableActionButton == 1) {
		$("#actionButton").attr("disabled", "disabled");
	} else {
		$("#actionButton").removeAttr("disabled");
	}
}

function changeChecked(obj) {
	var textSelected = $(obj).find("option:selected").text();
	
	if(textSelected == "All"){
		/*var tableValidation = $(obj).closest("table");
		var count = 0;
		tableValidation.find("tbody tr").each(function() {
			if(count != 0){
				$(this).remove();
			}
			count++;
		});*/
		removeRowsOfTblFmtCode(1);
		$("#actionButton").attr("disabled", "disabled");
	} else {
		var checkFirstRow = $(obj).closest("tr").attr("data-ar-rindex");
		if(checkFirstRow == undefined){
			checkFirstRow = 0;
		}
		
		removeRowsOfTblFmtCode(parseInt(checkFirstRow) + 1);
		
		processingValidationRule();
		

		/*if(checkFirstRow == 0){
			var optionSelected = $(obj).find("option[value="+$(obj).val()+"]");
			var listOfInclude = optionSelected.attr("include");
			if(listOfInclude != null){
				$("#tblFmtCode tbody").find("tr:first").nextAll().remove();
				$("#actionButton").removeAttr("disabled");
			}else {
				$("#actionButton").attr("disabled", "disabled");
			}
		}
		if(checkFirstRow != undefined){
			removeRow($(obj).closest("tr"), false, checkFirstRow);
		}
		var optionSelected = $(obj).find("option[value="+$(obj).val()+"]");
		var listOfInclude = optionSelected.attr("include");
		if(listOfInclude != null){
			listOptionKey = listOfInclude.split(";");
			if(listOptionKey == null || listOptionKey == ""){
				$("#actionButton").attr("disabled", "disabled");
			} else {
				$("#actionButton").removeAttr("disabled");
			}
		} else {
			$("#actionButton").attr("disabled", "disabled");
		}*/
	}
}

/**
 * 
 * @param row
 * @param flag
 * @param checkFirstRow
 */
function removeRowsOfTblFmtCode(index) {
	$('#tblFmtCode tbody>tr').slice(index).remove();
}

function removeRow(row, flag, checkFirstRow){
	var nextRow = row.next();
	var rowCount = parseInt($('#tblFmtCode tr').length) - 1;
	if(flag){
		row.remove();
	}
	if(checkFirstRow < rowCount){
		removeRow(nextRow, true, checkFirstRow);
	}
}

function changeOption(){

	processingValidationRule();

	var validationRulesValid = $("#validationRulesValid").val();

	if (validationRulesValid != undefined && validationRulesValid != '') {
		var arrValidationRulesValid = $.qp.arrayUnique($("#validationRulesValid").val().split(";"));

		$("#tblFmtCode tbody").find("tr").last().find('.qp-input-select>option').each(function() {
			if ($(this).val() == '' || jQuery.inArray($(this).val(), arrValidationRulesValid) == -1) {
				$(this).remove();
			}
		});
		
		if ($("#tblFmtCode tbody").find("tr").last().find('.qp-input-select>option').length == 0) {
			$("#tblFmtCode tbody").find("tr").last().remove();
		}
		
	} else {
		$("#tblFmtCode tbody").find("tr").last().remove();
	}
	
	
	/*$('#tblFmtCode').find("tbody tr").each(function() {
		if($(this).find("select[name=fmtCode] option:selected").attr("include") != undefined){
			var include = $(this).find("select[name=fmtCode] option:selected").attr("include").split(";")
			for (i = 0; i < include.length; i++) {
				listOptionKey.push(include[i]);
			}
		}
	});
	listOptionKey.pop();
	if($("#tblFmtCode tbody").find("tr").last().prev().find("td:first").find("select[name=fmtCode] option:selected").attr("include") != undefined){
		$('#tblFmtCode').find("tbody tr").each(function() {
			var ftmCodeTemp = $(this).find("select[name=fmtCode] option:selected").text();
			listOptionKey = jQuery.grep(listOptionKey, function(value) {
				return value != ftmCodeTemp;
			});
		});
	}
	
	var uniqueNames = [];
	$.each(listOptionKey, function(i, el){
	    if($.inArray(el, uniqueNames) === -1) uniqueNames.push(el);
	});
	
	var lastSelect = $("#tblFmtCode tbody").find("tr").last().find("td:first");
	var selectField = lastSelect.find("select");
	var options = '';
	selectField.empty();
	
	options += '<option value="' + "" + '">' + "-- Select --" + '</option>';
	for ( var i = 0; i < uniqueNames.length; i++) {
		for( var j = 0; j < listObj.length; j++){
			if(uniqueNames[i] == listObj[j].code){
				options += '<option value="' + listObj[j].code + '"'+ 'include ="' + listObj[j].include +'"' +'>' + listObj[j].name + '</option>';
			}
		}
	}
	selectField.append(options);
	
	if(selectField.val() == null || selectField.val() == ""){
		 $("#tblFmtCode tbody").find("tr").last().remove();
	}

	if(options == ""){
		$("#actionButton").attr("disabled", "disabled");
	}else{
		$("#actionButton").attr("disabled", false);
	}
	includeLength = parseInt(includeLength) - 1;*/
}

function checkAction(obj){
	/*var currentRow = $(obj).closest("tr").attr("data-ar-rindex").match(/\d+/)[0];
	var lengthTable = $('#tblFmtCode tr').length;
	for (var int = currentRow; int < lengthTable; int++) {
		$('#tblFmtCode tr').eq(currentRow).remove();
	}
	$("#actionButton").removeAttr("disabled");*/
	
	var checkFirstRow = $(obj).closest("tr").attr("data-ar-rindex");
	if(checkFirstRow == undefined || checkFirstRow == 0){
		checkFirstRow = 1;
	}

	removeRowsOfTblFmtCode(parseInt(checkFirstRow));
	processingValidationRule();
}

function removeItem(array, item) {
	for ( var i in array) {
		if (array[i] == item) {
			array.splice(i, 1);
			break;
		}
	}
	return array;
}



function changeValidation(){
	$("#tblFmtCode tbody").find("tr:first").nextAll().remove();
}

function resetValue(){
	$("#constrainsType").val("");
	$("#datasourceId").val("");
	$("#datasourceType").val("");
	$("#remark").val("");
	$("#defaultValue").val("");
	$("#operatorCode").val("");
	$("#minVal").val("");
	$("#maxVal").val("");
	$("#codelistCodeAutocomplete").val("");
	$("#codelistDefaultAutocomplete").val("");
	$("#sqlCodeAutocomplete").val("");
	$("#userDefineValue").val("");
	$("#supportOptionFlg").val("");
	$("#maxLength").val("");
	$("#precision").val("");
}

function disableBaseTypeLength() {
	$("#precision").prop("readonly", true);
	$("#maxLength").prop("readonly", true);
	$("#maxLengthRequired").hide();
}

$(document).ready(
	function() {
		processingForChangeDataType($("input[name=baseType]").val());
		$('.close').on('click', function(e){
			
		});
		
		var classificationModel = $("#classification");
		var classificationObj = {
			majorClassification : classificationModel.find("input[name=majorClassification]").val(),
			subClassification : classificationModel.find("input[name=subClassification]").val(),
			minorClassification : classificationModel.find("input[name=minorClassification]").val()
		};
		
		classificationModel.find("#classificationTemp").val(JSON.stringify(classificationObj));
		
		$('#submitModifyDomain').on('click', function(e){
			$.qp.undoFormatNumericForm($("#domainDesignTbl"));
		});
		

		$('a.codelistRegis').click(function() {
			window.open(this.href);
			return false;
		});

		$('a.sqlRegis').click(function() {
			window.open(this.href);
			return false;
		});

		$('a.colDefaultValue').click(function() {
			// window.open(this.href);
			return false;
		});
		
		init();
		
		if (fmtCode != ""){
			var fmtCodelist = fmtCode.split(",");
			var baseType = $("input[name=baseType]").val();
			var maxlength = $("#maxLength").val();
			$("#maxLength").val(maxlength);
//			processingForChangeDataType(baseType);
			/*$("#selectValidationRule").val(fmtCode);
			$("#selectValidationRule").change();
			if(fmtCodelist.length > 1){
				$("#actionButton" ).click();
			}*/
			
			/**
			 * DungNN 20160719
			 */
			if(fmtCodelist.length > 1){
				$("#tblFmtCode tbody").find("tr").last().find('.qp-input-select').val(fmtCodelist[0]);
				for(i = 1;i<fmtCodelist.length;i++) {
					$("#actionButton").click();
					$("#tblFmtCode tbody").find("tr").last().find('.qp-input-select').val(fmtCodelist[i]);
				}
			} else {
				
				var checkExist = $("#selectValidationRule option[value='"+fmtCode+"']").text();
				
				if (checkExist != undefined &&  checkExist != '') {
					$("#selectValidationRule").val(fmtCode);
					$("#selectValidationRule").change();
					$("#selectValidationRule").removeAttr("disabled");
					$("#actionRemove").removeAttr("disabled");
				} /*else {
					$("#fmtCodeRequired").hide();
					$("#actionButton").attr("disabled", "disabled");
					$("#selectValidationRule").attr("disabled", "disabled");
				}*/
			}
		}else{
			$("#actionButton").attr("disabled", "disabled");
			$("#selectValidationRule").attr("disabled", "disabled");
			$("#actionRemove").attr("disabled", "disabled");
			if($("#baseTypeAutocompleteId").val() != ""){
				var groupId = $("#groupBasetypeId").val();
				var listOptionKey =[];
				for(var i = 0; i < listObj.length; i++){
					var arrayGroup = listObj[i].group.split(";");
					for(var j = 0; j < arrayGroup.length; j++){
						if(arrayGroup[j]==groupId){
							listOptionKey.push(listObj[i]);
						}
					}
				}
				
				var lastSelect = $("#tblFmtCode tbody").find("tr").last().find("td:first");
				var selectField = lastSelect.find("select");
				var options = '';
				selectField.empty();
				var groupId = $("#groupBasetypeId").val();
				switch(parseInt(groupId)) {
					case DATATYPE.TEXT:
					case DATATYPE.CHAR:
					case DATATYPE.INTEGER:	
						$("#selectValidationRule").removeAttr("disabled");
						$("#fmtCodeRequired").show();
						if(listOptionKey.length == 1){
							for ( var i = 0; i < listOptionKey.length; i++) {
								options += '<option value="' + listOptionKey[i].code + '"'+ 'include ="' + listOptionKey[i].include +'"' +'>' + listOptionKey[i].name + '</option>';
							}
						}else{
						options += '<option value="' + "" + '">' + dbMsgSource["sc.domaindesign.0052"] + '</option>';
						for ( var i = 0; i < listOptionKey.length; i++) {
								options += '<option value="' + listOptionKey[i].code + '"'+ 'include ="' + listOptionKey[i].include +'"' +'>' + listOptionKey[i].name + '</option>';
							}
						}
						selectField.append(options);
						break;
					default:
						options = '<option value="'+'" include = "">'+dbMsgSource["sc.domaindesign.0052"]+'</option>';
						selectField.append(options);
						break;
				}
			}
		}
		var selected = $("#tblFmtCode tbody").find("tr").last().find("td:first").find("select").find("option");
		if(selected.length == 1){
			if(selected.val() == ""){
				$("#fmtCodeRequired").hide();
			}
		}
		
		/*var fmtCodeArr = fmtCode.split(",");
		var tblFmtCode = $("#tblFmtCode");
		var rows = $('tr', $(tblFmtCode));
		
		for (var int = 0; int < fmtCodeArr.length; int++) {
			
			rows.eq(int).find("select[name=fmtCode]").val(fmtCodeArr[int]);
			if(int == 0){
				rows.eq(int).find("#actionRemove").hide();
			}
			var includeFtm = rows.eq(int).find("select[name=fmtCode] option:selected").val();
			if(includeFtm != undefined){
				if(parseInt(includeFtm.split(";").length) == 0){
					rows.eq(int).find("#actionButton").hide();
				}
			}
		}
		
		var ftmPrevCode = [];
		var count = 0;
		$('#tblFmtCode').find("tbody tr").each(function() {
//			ftmCodeKey.push($(this).find("select[name=fmtCode] option:selected").val());
			if(count != 0){
				if($(this).find("select[name=fmtCode] option:selected").attr("include") != undefined){
					var listOptionKey = $(this).find("select[name=fmtCode] option:selected").attr("include").split(";");
					
					$.merge(ftmPrevCode, $(this).prev().find("select[name=fmtCode] option:selected").attr("include").split(";"));
					
					var uniqueNames = [];
					$.each(ftmPrevCode, function(i, el){
						if($.inArray(el, uniqueNames) === -1) uniqueNames.push(el);
					});
					
					var lastSelect = $(this).find("td:first");
					var selectField = lastSelect.find("select");
					var ftmDelect = $(this).find("select[name=fmtCode] option:selected").val();
					var options = '';
					selectField.empty();
					
					options += '<option value="' + "" + '">' + "-- Select --" + '</option>';
					for ( var i = 0; i < uniqueNames.length; i++) {
						for( var j = 0; j < listObj.length; j++){
							if(uniqueNames[i] == listObj[j].code){
								options += '<option value="' + listObj[j].code + '"'+ 'include ="' + listObj[j].include +'"' +'>' + listObj[j].name + '</option>';
							}
						}
					}
					selectField.append(options);
					selectField.val(ftmDelect);
//					lastSelect.find("select option").each(function() {
//						for ( var i = 0; i < ftmCodeKey.length; i++) {
//							if($(this).val() == ftmCodeKey[i]){
//								$(this).remove();
//							}
//						}
//					});
				}
			}
			count++;
		});
		count = 0;
		var ftmCodeKey = [];
		$('#tblFmtCode').find("tbody tr").each(function() {
			if(count != 0){
				if($(this).find("select[name=fmtCode] option:selected").attr("include") != undefined){
					var lastSelect = $(this).find("td:first");
					var selectField = lastSelect.find("select");
					
					lastSelect.find("select option").each(function() {
						for ( var i = 0; i < ftmCodeKey.length; i++) {
							if(this.value == ftmCodeKey[i]){
								this.remove();
							}
						}
					});
				}
			}
			ftmCodeKey.push($(this).find("select[name=fmtCode] option:selected").val());
			count++;
		});
		
		if ($("#tblFmtCode tbody").find("tr").last().find("td:first").find("select").val() == "") {
			$("#actionButton").attr("disabled", "disabled");
		} else {
			$("#actionButton").removeAttr("disabled");
		}*/
});
