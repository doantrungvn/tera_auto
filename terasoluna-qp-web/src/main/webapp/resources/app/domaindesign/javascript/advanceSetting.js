function openDialogAutocompleteSetting(obj) {
	var precision = parseInt($(domainDesignTbl).find("input[name=precision]").autoNumeric('get'));
	var maxlength = parseInt($(domainDesignTbl).find("input[name=maxLength]").autoNumeric('get'));
	
	if(maxlength <= precision){
		$.qp.showAlertModal(dbMsgSource['sc.domaindesign.0062']);
		return;
	}
	
	if($("input[name=baseType]").val() != "" && $("input[name=baseType]").val() != "9"){
		$($("#dialogAdvanceSetting")).modal(
			{ 
				show: true,
				closable: false,
				keyboard:false,
				backdrop:'static'
			}
		);
		$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").hide();
		$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").hide();
		$("#supportValue").hide();
		$("#notSupportValue").hide();
		$("#isSupport").hide();
		$("#typeOfList").hide();
		$("#sqlCode").hide();
		$("#codelistCode").hide();
		$("#default").hide();
		$("#valueAdvanceSetting").hide();
		$("#selectConstrains").val("0");
		$("#defaultValueAdvanceSetting").removeAttr("style");
		$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='any']").prop('checked', true);
		processingAdvanceSetting(obj);
		loadDataByValue(obj);
		if($("input[name=codelistCode]").val() == ""){
			$("#codelistView").hide();
		}else{
			$("#codelistView").show();
		}
		if($("input[name=sqlCode]").val() == ""){
			$("#sqlView").hide();
		}else{
			$("#sqlView").show();
		}
		
		$.qp.initialCatAutocomplete($("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]"));
		$("#dialogAdvanceSetting").find("#datasourceTypeAdvance").val($(obj).closest("tr").find("input[name$='.datasourceType']").val());
		
		$("#dialogAdvanceSetting").find("#optionLabelAdvance").val($(obj).closest("tr").find("input[name$='.optionLabel']").val());
		$("#dialogAdvanceSetting").find("#optionValueAdvance").val($(obj).closest("tr").find("input[name$='.optionValue']").val());
		$("#dialogAdvanceSetting").find("#optionLabelAutocpmleteAdvance").val($(obj).closest("tr").find("input[name$='.optionLabelAutocomplete']").val());
		$("#dialogAdvanceSetting").find("#optionValueAutocpmleteAdvance").val($(obj).closest("tr").find("input[name$='.optionValueAutocomplete']").val());
		$("#datasourceTypeAdvance").val($("#datasourceType").val());
	}
}

function changeConstrains(obj){
	$("#dialogAdvanceSetting").find("#optionLabelAdvance").val($("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabel]").val());
	$("#dialogAdvanceSetting").find("#optionValueAdvance").val($("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValue]").val());
	$("#dialogAdvanceSetting").find("#optionLabelAutocpmleteAdvance").val($("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").val());
	$("#dialogAdvanceSetting").find("#optionValueAutocpmleteAdvance").val($("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").val());
	var groupTypeId = $("#groupTypeId").val();
	var valueAdvanceSetting = $("#valueAdvanceSetting");
	var modal = $("#dialogAdvanceSetting");
	if(obj.value == "any"){
		
		resetValueAdvanceSetting(modal);
		//$("#domainDesignTbl").find("#constrainsType").val(0);
				
		$("#typeOfList").hide();
		$("#valueAdvanceSetting").hide();
		$("#defaultValueAdvanceSetting").show();
		$("#sqlCode").hide();
		$("#codelistCode").hide();
		$("#default").hide();
		$("#supportValue").hide();
		$("#notSupportValue").hide();
		$("#isSupport").hide();
		$("#defaultValueAdvanceSetting").removeAttr("style");
		$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").hide();
		$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").hide();
	}else if(obj.value == "range"){
		
		resetValueAdvanceSetting(modal);
		//$("#domainDesignTbl").find("#constrainsType").val(1);
		
//		if($("#setTimeNowStatus").val() == "1"){
//			$("#defaultValueAdvanceSetting").css({"border-top-color":"red","border-top-style":"dashed","border-top-width":"2px"})
//			valueAdvanceSetting.hide();
//		}else{
			valueAdvanceSetting.show();
//		}
		$("#defaultValueAdvanceSetting").show();
		$("#sqlCode").hide();
		$("#codelistCode").hide();
		$("#default").hide();
		$("#supportValue").hide();
		$("#notSupportValue").hide();
		$("#isSupport").hide();
		$("#typeOfList").hide();
		$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").hide();
		$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").hide();
		valueAdvanceSetting.css({"border-top-color":"red","border-top-style":"dashed","border-top-width":"2px"})
		valueAdvanceSetting.css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
		valueAdvanceSetting.css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
		
		$("#defaultValueAdvanceSetting").css({"border-bottom-color":"red","border-bottom-style":"dashed","border-bottom-width":"2px"})
		$("#defaultValueAdvanceSetting").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
		$("#defaultValueAdvanceSetting").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
		
		var operator = $('#valueAdvanceSetting').find("select[name=operatorCode]");
		setStatusOperatorcode(operator,true);
		
	}else if(obj.value == "dataSource"){
		resetValueAdvanceSetting(modal);
		$('[data-toggle="tooltip"]').tooltip();
		$("#defaultValueAdvanceSetting").hide();
		valueAdvanceSetting.hide();
		switch(parseInt(groupTypeId)) {
			case DATATYPE.TEXT:
			case DATATYPE.INTEGER:
			case DATATYPE.CHAR:
				$("#typeOfList").show();
				$("#typeOfList").css({"border-top-color":"red","border-top-style":"dashed","border-top-width":"2px"})
				$("#typeOfList").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
				$("#typeOfList").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
				$("#supportValue").css({"border-bottom-color":"red","border-bottom-style":"dashed","border-bottom-width":"2px"})
				$("#supportValue").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
				$("#supportValue").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
				$("#isSupport").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
				$("#isSupport").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
				var typeOfListName = $("#typeOfList input:radio:checked").val();
				$("#supportValue").show();
				if (typeOfListName == "codeList") {
					settingCodelistShow();
				} else if (typeOfListName == "sqlBuilder") {
					settingSqlBuilderShow();
				} else {
					$("#isSupport").show();
					if($("#tbl_list_Suport").html() == ""){
						$("#tbl_list_Suport").html("").append(genHtmlNonSuportTable());
					} else if($("#tbl_list_Suport tbody").html() == ""){
						$("#tbl_list_Suport tbody").html("").append(genHtmlUserDefineDefault());
					}
					$('input:radio[name=typeOfList]').filter('[value=userDefine]').prop('checked', true);
				}

				break;
			case DATATYPE.BOOLEAN:
				htmlVal = "<th>"+dbMsgSource['sc.tabledesign.0044']+"</th>" 
					+ "<td colspan='2'>"
					+ "<label class='radio-inline'><input type='radio' name='typeOfList' value='userDefine' onchange ='click(this)'>"+dbMsgSource['sc.tabledesign.0038']+"&nbsp&nbsp</label> "
					+ "<label class='radio-inline'><input type='radio' name='typeOfList' value='codeList'>Code List&nbsp&nbsp</label> "
					+ "</td>";
				valueAdvanceSetting.html("").append(htmlVal);
			break;
		}
		
		if($('input:radio[name=typeOfList]').filter('[value=sqlBuilder]').prop('checked')){
			if($("#dialogAdvanceSetting").find("#datasourceTypeAdvance").val() == "3"){
				$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").show();
				$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").show();
				$("#dialogAdvanceSetting").find("#sqlCode").removeAttr("style");
				$("#dialogAdvanceSetting").find("#sqlCode").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
				$("#dialogAdvanceSetting").find("#sqlCode").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
				
				$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").attr("arg01", $(obj).closest("tr").find("input[name$='.datasourceId']").val());
				$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").attr("arg01", $(obj).closest("tr").find("input[name$='.datasourceId']").val());
				
				$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabel]").val($("#dialogAdvanceSetting").find("#optionLabelAdvance").val());
				$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").val($("#dialogAdvanceSetting").find("#optionLabelAutocpmleteAdvance").val());
				$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValue]").val($("#dialogAdvanceSetting").find("#optionValueAdvance").val());
				$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").val($("#dialogAdvanceSetting").find("#optionValueAutocpmleteAdvance").val());
			}
		}else{
			$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").hide();
			$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").hide();	
		}
	}
}

function genHtmlNonSuportTable(){
	return "<colgroup>" 
	+ 			"<col width='85px'>"
	+ 			"<col width='50%'class=\"colOptionName\">"
	+	 		"<col width=''>"
	+	 		"<col width='40px'>"
	+ 		"</colgroup>"
	+ 		"<thead>"
	+ 			"<tr>"
	+	 			"<th>"+dbMsgSource['sc.tabledesign.0042']+" </br>(<a id=\"colDefaultValue\" href='javascript:void(0)' onclick='removeDefaultValue(this);' data-toggle='tooltip' title='Click to remove default value'>"+dbMsgSource['sc.tabledesign.0081']+"</a>)</th>"
	+ 				"<th class=\"colOptionName\">"+dbMsgSource['sc.tabledesign.0045']+"<span class='qp-required-field'> (*) </span></th>"
	+ 				"<th>"+dbMsgSource['sc.tabledesign.0046']+"<span class='qp-required-field'> (*) </span></th>"
	+ 				"<th></th>"
	+ 			"</tr>"
	+ 		"</thead>"
	+ 		"<tbody class='ui-sortable'>"
	+ 			"<tr>"
	+		 		"<td><input type='radio' value='supportValue' name='supportValue'></td>"
	+		 		"<td class=\"td-word-wrap colOptionName\"><input type=\"text\" class=\"form-control\" name=\"codelistName\" value=\"\" maxlength=\"200\"/></td>" 
	+		 		"<td class=\"td-word-wrap colOptionValue\"><input type=\"text\" class=\"form-control\" name=\"codelistValue\" value=\"\" maxlength=\"200\"/></td>" 
	+		 		"<td><a class='btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action' title='Remove' onclick='$.qp.removeRowJSEx(this);' style='margin-top: 3px;' href='javascript:void(0)'></a></td>"
	+ 			"</tr>"
	+ 		"</tbody>";
}

function genHtmlUserDefineDefault(){
	return "<tr>"
	+			"<td><input type='radio' value='supportValue' name='supportValue'></td>"
	+			"<td class=\"td-word-wrap colOptionName\"><input type=\"text\" class=\"form-control\" name=\"codelistName\" value=\"\" maxlength=\"200\"/></td>" 
	+			"<td class=\"td-word-wrap colOptionValue\"><input type=\"text\" class=\"form-control\" name=\"codelistValue\" value=\"\" maxlength=\"200\"/></td>" 
	+			"<td><a class='btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action' title='Remove' onclick='$.qp.removeRowJSEx(this);' style='margin-top: 3px;' href='javascript:void(0)'></a></td>"
	+ 		"</tr>";
}

function changeSupportOptionValue(obj){
	if(obj.checked){
		$('#tbl_list_Suport').find(".colOptionName").hide();
	}else{
		$('#tbl_list_Suport').find(".colOptionName").show();
	}
	$('[data-toggle="tooltip"]').tooltip();
}

function changeDataSource(obj){
	$("#dialogAdvanceSetting").find("#optionLabelAdvance").val($("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabel]").val());
	$("#dialogAdvanceSetting").find("#optionValueAdvance").val($("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValue]").val());
	$("#dialogAdvanceSetting").find("#optionLabelAutocpmleteAdvance").val($("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").val());
	$("#dialogAdvanceSetting").find("#optionValueAutocpmleteAdvance").val($("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").val());

	$("#typeOfList").css({"border-bottom-color":"none","border-bottom-style":"none","border-bottom-width":"none"})
	var domainDesignTbl = $("#domainDesignTbl");
	var tableDialogAdvanceSetting = $("#tableDialogAdvanceSetting");
	var datasourceType = $(domainDesignTbl).find("input[name=datasourceType]").val();
	
	if(datasourceType == '0'){
		$(tableDialogAdvanceSetting).find("input[name=codelistCode]").val("");
		$(tableDialogAdvanceSetting).find("input[name=codelistDefault]").val("");
		$(tableDialogAdvanceSetting).find("input[name=sqlCode]").val("");
	}else if(datasourceType == '1'){
		$(tableDialogAdvanceSetting).find("input[name=sqlCode]").val("");
	}else if(datasourceType == '2' || datasourceType == '3'){
		$(tableDialogAdvanceSetting).find("input[name=codelistCode]").val("");
		$(tableDialogAdvanceSetting).find("input[name=codelistDefault]").val("");
	}
	
	if(obj.value == "userDefine"){
		$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").hide();
		$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").hide();

		$("#isSupport").show();
		$("#tbl_list_Suport").show();
		$("#sqlCode").hide();
		$("#codelistCode").hide();
		$("#default").hide();
		$("#supportValue").show();
		/*if($("#supportValue").find("#tbl_list_Suport tr").length == 0){
			var htmlTableSupport = "<colgroup>" 
				+ 			"<col width='55px' class=\"colDefaultName\">"
				+ 			"<col width='42%' class=\"colOptionName\">"
				+	 		"<col width=''>"
				+	 		"<col width='40px'>"
				+ 		"</colgroup>"
				+ 		"<thead>"
				+ 			"<tr>"
				+	 			"<th>"+dbMsgSource['sc.tabledesign.0042']+" </br>(<a id=\"colDefaultValue\" href='javascript:void(0)' onclick='removeDefaultValue(this);' title='Click to remove default value'>"+dbMsgSource['sc.tabledesign.0081']+"</a>)</th>"
				+ 				"<th class=\"colOptionName\">"+dbMsgSource['sc.tabledesign.0045']+"<span class='qp-required-field'> (*) </span></th>"
				+ 				"<th>"+dbMsgSource['sc.tabledesign.0046']+"<span class='qp-required-field'> (*) </span></th>"
				+ 				"<th></th>"
				+ 			"</tr>"
				+ 		"</thead>"
				+ 		"<tbody class='ui-sortable'>";
			
			$("#supportValue").find("#tbl_list_Suport").append(htmlTableSupport);
		}*/
		
		if($("#tbl_list_Suport").html() == ""){
			$("#tbl_list_Suport").html("").append(genHtmlNonSuportTable());
		} else if($("#tbl_list_Suport tbody").html() == ""){
			$("#tbl_list_Suport tbody").html("").append(genHtmlUserDefineDefault());
		}
		
		$("#isSupport").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
		$("#isSupport").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
		$("#supportValue").css({"border-bottom-color":"red","border-bottom-style":"dashed","border-bottom-width":"2px"})
		$("#supportValue").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
		$("#supportValue").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
		$('[data-toggle="tooltip"]').tooltip();
	}
	if(obj.value == "sqlBuilder"){
		
		settingSqlBuilderShow();
		if($("#datasourceTypeAdvance").val() == "3"){
			$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").show();
			$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").show();
			$("#dialogAdvanceSetting").find("#sqlCode").removeAttr("style");
			$("#dialogAdvanceSetting").find("#sqlCode").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
			$("#dialogAdvanceSetting").find("#sqlCode").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
			
			$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").attr("arg01", $(obj).closest("tr").find("input[name$='.datasourceId']").val());
			$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").attr("arg01", $(obj).closest("tr").find("input[name$='.datasourceId']").val());
			
			$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabel]").val($("#dialogAdvanceSetting").find("#optionLabelAdvance").val());
			$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").val($("#dialogAdvanceSetting").find("#optionLabelAutocpmleteAdvance").val());
			$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValue]").val($("#dialogAdvanceSetting").find("#optionValueAdvance").val());
			$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").val($("#dialogAdvanceSetting").find("#optionValueAutocpmleteAdvance").val());
		}else{
			$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").hide();
			$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").hide();
		}
	}
	
	if(obj.value == "codeList"){
		settingCodelistShow();
		$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").hide();
		$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").hide();

	}
}

function settingCodelistShow(){
	$("#codelistCode").show();
	$("#default").show();
	$("#notSupportValue").hide();
	$("#sqlCode").hide();
	$("#supportValue").hide();
	$("#isSupport").hide();
	
	$("#default").css({"border-bottom-color":"red","border-bottom-style":"dashed","border-bottom-width":"2px"})
	$("#default").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
	$("#default").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
	
	$("#codelistCode").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
	$("#codelistCode").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
}

function settingSqlBuilderShow(){
	$("#sqlCode").show();
	$("#codelistCode").hide();
	$("#default").hide();
	$("#supportValue").hide();
	$("#isSupport").hide();
	$("#notSupportValue").hide();
	
	$("#sqlCode").css({"border-bottom-color":"red","border-bottom-style":"dashed","border-bottom-width":"2px"})
	$("#sqlCode").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
	$("#sqlCode").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
}

function setCodeListDetail(){
	$("#codeListDetailId").attr("arg01", $("input[name=codelistCode]").val());
}

function saveSqlCode(){
	$("#sqlCodeValue").val($("#sqlCodeAutocompleteId").val());
}

function setStatusOperatorcode(obj, keepValue){
	var groupTypeId = $(obj).closest(".modal").find("#groupTypeId").val();
	if(groupTypeId != undefined && (groupTypeId == "4" || groupTypeId == "8" || groupTypeId == "9")){
		$(obj).closest("tr").find("input[name=minVal]").parent().hide();
		$(obj).closest("tr").find("input[name=maxVal]").parent().hide();
		$(obj).closest("tr").find("#separator").hide();
		switch (parseInt($(obj).val())) {
			case 1:
			case 2:
			case 3:
				$(obj).closest("tr").find("input[name=maxVal]").show();
				$(obj).closest("tr").find("input[name=maxVal]").parent().show();
				$(obj).closest("tr").find("input[name=maxVal]").parent().removeClass("pull-right").addClass("pull-left");
				break;
			case 4:
			case 5:
				$(obj).closest("tr").find("input[name=minVal]").show();
				$(obj).closest("tr").find("input[name=minVal]").parent().show();
				break;
			case 6:
			case 7:
				$(obj).closest("tr").find("input[name=minVal]").show();
				$(obj).closest("tr").find("input[name=minVal]").parent().show();
				break;
			case 8:
				$(obj).closest("tr").find("input[name=maxVal]").show();
				$(obj).closest("tr").find("input[name=minVal]").show();
				$(obj).closest("tr").find("input[name=minVal]").parent().show();
				$(obj).closest("tr").find("input[name=maxVal]").parent().show();
				$(obj).closest("tr").find("input[name=maxVal]").parent().removeClass("pull-right").addClass("pull-left");
				$(obj).closest("tr").find("#separator").show();
				break;
			default:
				break;
		}
	}else{
		$(obj).closest("tr").find("input[name=minVal]").hide();
		$(obj).closest("tr").find("input[name=maxVal]").hide();
		$(obj).closest("tr").find("#separator").hide();
		switch (parseInt($(obj).val())) {
			case 1:
			case 2:
			case 3:
				$(obj).closest("tr").find("input[name=maxVal]").show();
				$(obj).closest("tr").find("input[name=maxVal]").removeClass("pull-right").addClass("pull-left");
				break;
			case 4:
			case 5:
				$(obj).closest("tr").find("input[name=minVal]").show();
				break;
			case 6:
			case 7:
				$(obj).closest("tr").find("input[name=minVal]").show();
				break;
			case 8:
				$(obj).closest("tr").find("input[name=minVal]").show();
				$(obj).closest("tr").find("input[name=maxVal]").show();
				$(obj).closest("tr").find("input[name=maxVal]").removeClass("pull-right").addClass("pull-left");
				$(obj).closest("tr").find("#separator").show();
				break;
			default:
				break;
		}
	}
	if(keepValue){
		
	}else{
		$(obj).closest("tr").find("input[name=minVal]").val("");
		$(obj).closest("tr").find("input[name=maxVal]").val("");
	}
}

function processingAdvanceSetting(obj) {
	$.qp.undoFormatNumericForm($("#dialogAdvanceSetting"));
	//$("#fmtCode").text($(obj).closest("tr").find("select[name$='.dataType'] option:selected").attr("validationrule"));
	var groupId = $("#groupBasetypeId").val();
	var dataType = $("input[name=baseType]").val();
	$("#groupTypeId").val(groupId);
	
	var columnDefault = $("#defaultValueAdvanceSetting");
	var htmlDefaultVal = "";
	
	var valueAdvanceSetting = $("#valueAdvanceSetting");
	
	var precision = $(obj).closest("tbody").find("input[name=precision]").autoNumeric('get');
	var maxlength = $(obj).closest("tbody").find("input[name=maxLength]").autoNumeric('get');
	
	
	htmlVal = "<th>Value</th>"
		  +		"<td>"
		  +			" <select name='operatorCode' class='form-control qp-input-select' onchange='setStatusOperatorcode(this)'> ";
					$.each($.parseJSON($("#operatorCodeAdvance").val()), function(key,value){
						htmlVal = htmlVal + "<option value='"+key+"'>"+value+"</option>";
					});
	htmlVal = htmlVal	+ "</select></td>";
	
	switch(parseInt(groupId)) {
		case DATATYPE.TEXT:
		case DATATYPE.CHAR:
			$("#modalDialog").css({"width":"55%"});
			$("#panelBody").css({"height":"auto"});
			$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='dataSource']").removeAttr('disabled');
			$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='range']").attr("disabled",false);
			var maxLength = $(obj).closest("tbody").find("input[name=maxLength]").autoNumeric('get');
			htmlDefaultVal = "<th>"+dbMsgSource['sc.tabledesign.0042']+"</th>" +
							 "	<td colspan='3'>" +
							 "		<input type='text' class='form-control qp-input-text' name='defaultValue' maxlength='"+maxLength+"' value=''>" +
							 "	</td>";			
			columnDefault.html("").append(htmlDefaultVal);
			
			htmlVal = htmlVal 
			  +		"<td colspan='2'>"
			  +			"<input type='text' name='minVal' class='form-control qp-input-from-text pull-left' maxlength='"+maxLength+"'/> "
			  +			"<div class='qp-separator-from-to' id='separator'>~</div>"
			  +			"<input type='text' name='maxVal' class='form-control qp-input-to-text pull-right' maxlength='"+maxLength+"'/> "
			  +		"</td>";
			valueAdvanceSetting.html("").append(htmlVal);
			
			$("select[name=operatorCode] option").each(function() {
				var value = $(this).val(); 
				if(value != "0" && value != "1" && value != "6" && value != "7"){
					$(this).remove();
				}
			});
			break;
			
		case DATATYPE.INTEGER:
			console.log ("INTEGER");
			$("#modalDialog").css({"width":"55%"});
			$("#panelBody").css({"height":"auto"});
			$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='dataSource']").removeAttr('disabled');
			$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='range']").attr("disabled",false);
			switch(parseInt(dataType)) {
				case NUMERIC_TYPE.INTEGER:
					htmlDefaultVal = "<th>"+dbMsgSource['sc.tabledesign.0042']+"</th>" +
					 "<td colspan='3'>" +
					 "<input type='text' class='form-control qp-input-integer' name='defaultValue' maxlength='200' value='' />" +
					 "</td>";
					columnDefault.html("").append(htmlDefaultVal);
					htmlVal = htmlVal 
					  +		"<td colspan='2'>"
					  +			"<input type='text' name='minVal' class='form-control qp-input-from-integer pull-left' /> "
					  +			"<div class='qp-separator-from-to' id='separator'>~</div>"
					  +			"<input type='text' name='maxVal' class='form-control qp-input-to-integer pull-right' /> "
					  +		"</td>";
					valueAdvanceSetting.html("").append(htmlVal);
					$.qp.formatInteger($('.qp-input-from-integer'));
					$.qp.formatInteger($('.qp-input-to-integer'));
					$.qp.formatInteger($('.qp-input-integer'));
					break;
				case NUMERIC_TYPE.SMALLINT:
					htmlDefaultVal = "<th>"+dbMsgSource['sc.tabledesign.0042']+"</th>" +
					 "<td colspan='3'>" +
					 "<input type='text' class='form-control qp-input-smallint' name='defaultValue' maxlength='200' value='' />" +
					 "</td>";
					columnDefault.html("").append(htmlDefaultVal);
					htmlVal = htmlVal 
					  +		"<td colspan='2'>"
					  +			"<input type='text' name='minVal' class='form-control qp-input-from-smallint pull-left' /> "
					  +			"<div class='qp-separator-from-to' id='separator'>~</div>"
					  +			"<input type='text' name='maxVal' class='form-control qp-input-to-smallint pull-right' /> "
					  +		"</td>";
					valueAdvanceSetting.html("").append(htmlVal);
					$.qp.formatSmallint($('.qp-input-from-smallint'));
					$.qp.formatSmallint($('.qp-input-to-smallint'));
					$.qp.formatSmallint($('.qp-input-smallint'));				
					break;
				case NUMERIC_TYPE.BIGINT:
					htmlDefaultVal = "<th>"+dbMsgSource['sc.tabledesign.0042']+"</th>" +
					 "<td colspan='3'>" +
					 "<input type='text' class='form-control qp-input-bigint' name='defaultValue' maxlength='200' value='' />" +
					 "</td>";
					columnDefault.html("").append(htmlDefaultVal);
					htmlVal = htmlVal 
					  +		"<td colspan='2'>"
					  +			"<input type='text' name='minVal' class='form-control qp-input-from-bigint pull-left' /> "
					  +			"<div class='qp-separator-from-to' id='separator'>~</div>"
					  +			"<input type='text' name='maxVal' class='form-control qp-input-to-bigint pull-right' /> "
					  +		"</td>";
					valueAdvanceSetting.html("").append(htmlVal);
					$.qp.formatBigint($('.qp-input-from-bigint'));
					$.qp.formatBigint($('.qp-input-to-bigint'));
					$.qp.formatBigint($('.qp-input-bigint'));
					break;
				case NUMERIC_TYPE.SERIAL:
					htmlDefaultVal = "<th>"+dbMsgSource['sc.tabledesign.0042']+"</th>" +
					 "<td colspan='3'>" +
					 "<input type='text' class='form-control qp-input-serial' name='defaultValue' maxlength='200' value='' />" +
					 "</td>";
					columnDefault.html("").append(htmlDefaultVal);
					htmlVal = htmlVal 
					  +		"<td colspan='2'>"
					  +			"<input type='text' name='minVal' class='form-control qp-input-from-serial pull-left' /> "
					  +			"<div class='qp-separator-from-to' id='separator'>~</div>"
					  +			"<input type='text' name='maxVal' class='form-control qp-input-to-serial pull-right' /> "
					  +		"</td>";
					valueAdvanceSetting.html("").append(htmlVal);
					$.qp.formatSerial($('.qp-input-from-serial'));
					$.qp.formatSerial($('.qp-input-to-serial'));
					$.qp.formatSerial($('.qp-input-serial'));
					break;
				case NUMERIC_TYPE.BIGSERIAL:
					htmlDefaultVal = "<th>"+dbMsgSource['sc.tabledesign.0042']+"</th>" +
					 "<td colspan='3'>" +
					 "<input type='text' class='form-control qp-input-bigserial' name='defaultValue' maxlength='200' value='' />" +
					 "</td>";
					columnDefault.html("").append(htmlDefaultVal);
					htmlVal = htmlVal 
					  +		"<td colspan='2'>"
					  +			"<input type='text' name='minVal' class='form-control qp-input-from-bigserial pull-left' /> "
					  +			"<div class='qp-separator-from-to' id='separator'>~</div>"
					  +			"<input type='text' name='maxVal' class='form-control qp-input-to-bigserial pull-right' /> "
					  +		"</td>";
					valueAdvanceSetting.html("").append(htmlVal);
					$.qp.formatBSerial($('.qp-input-from-bigserial'));
					$.qp.formatBSerial($('.qp-input-to-bigserial'));
					$.qp.formatBSerial($('.qp-input-bigserial'));
					break;
			}
			$("select[name=operatorCode] option").each(function() {
				var value = $(this).val(); 
				if(value == "7"){
					$(this).remove();
				}
			});
			break;
			
		case DATATYPE.DATE:
			$("#modalDialog").css({"width":"55%"});
			$("#panelBody").css({"height":"350px"});
			$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='dataSource']").attr("disabled",true);
			$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='range']").attr("disabled",false);
			htmlDefaultVal = "<th>"+dbMsgSource['sc.tabledesign.0042']+"</th>" +
							 "<td>" +
							 "<div class='input-group date qp-input-datepicker'>" +
								" <input type='text' class='form-control' id='defaultTime' name='defaultValue'/> " +
								" <span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span> " +
								" </div> " +
							 "</td><td colspan='2'><label for='defaultValue'><input type='checkbox' class='qp-input-checkbox qp-input-checkbox-margin' name='defaultValue' id='defaultValue' onclick='setCurrentTime(this)'>now()</label></td>";
			columnDefault.html("").append(htmlDefaultVal);
			htmlVal = htmlVal 
			  +		 "<td colspan='2'>"
			  +			"<div class='input-group date qp-input-from-datepicker pull-left' id='tdOfDate'>" 
			  +				" <input type='text' name='minVal' class='form-control' /> " 
			  + 			" <span class='input-group-addon' name='toDate'><span class='glyphicon glyphicon-calendar'></span></span> " 
			  +			" </div> "
			  +			"<div class='qp-separator-from-to' id='separator'>~</div>"
			  +			"<div class='input-group date qp-input-to-datepicker pull-rigth'>" 
			  +				" <input type='text' name='maxVal' class='form-control' /> " 
			  + 			" <span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span> " 
			  +			" </div> "
			  +		"</td>";
			valueAdvanceSetting.html("").append(htmlVal);
			$.qp.initialDatePicker($(".qp-input-from-datepicker"));
			$.qp.initialDatePicker($(".qp-input-to-datepicker"));
			$.qp.initialDatePicker($(".qp-input-datepicker"));
			$("select[name=operatorCode] option").each(function() {
				var value = $(this).val(); 
				if(value == "7"){
					$(this).remove();
				}
			});
			break;
			
		case DATATYPE.TIME:
			$("#modalDialog").css({"width":"55%"});
			$("#panelBody").css({"height":"350px"});
			$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='dataSource']").attr("disabled",true);
			$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='range']").attr("disabled",false);
			htmlDefaultVal = "<th>"+dbMsgSource['sc.tabledesign.0042']+"</th>" +
							 "<td>" +
							 "<div class='input-group date qp-input-timepicker'>" +
								" <input type='text' class='form-control' id='defaultTime' name='defaultValue'/> " +
								" <span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span> " +
								" </div> " + 
								"</td><td colspan='2'><label for='defaultValue'><input type='checkbox' class='qp-input-checkbox qp-input-checkbox-margin' name='defaultValue' id='defaultValue' onclick='setCurrentTime(this)'>now()</label></td>";
			columnDefault.html("").append(htmlDefaultVal);
			htmlVal = htmlVal 
			  +		 "<td colspan='2'>"
			  +			"<div class='input-group date qp-input-from-timepicker pull-left'>" 
			  +				" <input type='text' name='minVal' class='form-control' /> " 
			  + 			" <span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span> " 
			  +			" </div> "
			  +			"<div class='qp-separator-from-to' id='separator'>~</div>"
			  +			"<div class='input-group date qp-input-to-timepicker pull-rigth'>" 
			  +				" <input type='text' name='maxVal' class='form-control' /> " 
			  + 			" <span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span> " 
			  +			" </div> "
			  +		"</td>";
			valueAdvanceSetting.html("").append(htmlVal);
			$.qp.initialTimePicker($(".qp-input-from-timepicker"));
			$.qp.initialTimePicker($(".qp-input-to-timepicker"));
			$.qp.initialTimePicker($(".qp-input-timepicker"));
			$("select[name=operatorCode] option").each(function() {
				var value = $(this).val(); 
				if(value == "7"){
					$(this).remove();
				}
			});
			break;
			
		case DATATYPE.DATETIME:
			$("#modalDialog").css({"width":"55%"});
			$("#panelBody").css({"height":"350px"});
			$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='dataSource']").attr("disabled",true);
			$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='range']").attr("disabled",false);
			htmlDefaultVal = "<th>"+dbMsgSource['sc.tabledesign.0042']+"</th>" +
							 "<td>" +
							 "<div class='input-group date qp-input-datetimepicker'>" +
								" <input type='text' class='form-control' id='defaultTime' name='defaultValue'/> " +
								" <span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span> " +
								" </div> " +
								"</td><td colspan='2'><label for='defaultValue'><input type='checkbox' class='qp-input-checkbox qp-input-checkbox-margin' name='defaultValue' id='defaultValue' onclick='setCurrentTime(this)'>now()</label></td>";
			columnDefault.html("").append(htmlDefaultVal);
			htmlVal = htmlVal 
			  +		 "<td colspan='2'>"
			  +			"<div class='input-group date qp-input-from-datetimepicker pull-left'>" 
			  +				" <input type='text' name='minVal' class='form-control' /> " 
			  + 			" <span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span> " 
			  +			" </div> "
			  +			"<div class='qp-separator-from-to' id='separator'>~</div>"
			  +			"<div class='input-group date qp-input-to-datetimepicker pull-rigth'>" 
			  +				" <input type='text' name='maxVal' class='form-control' /> " 
			  + 			" <span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span> " 
			  +			" </div> "
			  +		"</td>";
			valueAdvanceSetting.html("").append(htmlVal);
			$.qp.initialDateTimePicker($(".qp-input-from-datetimepicker"));
			$.qp.initialDateTimePicker($(".qp-input-to-datetimepicker"));
			$.qp.initialDateTimePicker($(".qp-input-datetimepicker"));
			$("select[name=operatorCode] option").each(function() {
				var value = $(this).val(); 
				if(value == "7"){
					$(this).remove();
				}
			});
			break;
			
		case DATATYPE.DECIMAL:
			$("#modalDialog").css({"width":"55%"});
			$("#panelBody").css({"height":"auto"});
			$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='dataSource']").attr("disabled",true);
			$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='range']").attr("disabled",false);
			if(maxlength != "" && precision != ""){
				htmlDefaultVal = "<th>"+dbMsgSource['sc.tabledesign.0042']+"</th>" +
				"<td colspan='3'>" +
				"<input type='text' class='form-control qp-input-float' name='defaultValue' maxlength='200' value='' qp-min='"+initializeMinlength(maxlength, precision)+"' qp-max='"+initializeMaxlength(maxlength, precision)+"' /> ";
				columnDefault.html("").append (htmlDefaultVal);
				htmlVal = htmlVal 
				+		"<td colspan='2'>"
				+			"<input type='text' name='minVal' class='form-control qp-input-from-float pull-left' qp-min='"+initializeMinlength(maxlength, precision)+"' qp-max='"+initializeMaxlength(maxlength, precision)+"' /> "
				+			"<div class='qp-separator-from-to' id='separator'>~</div>"
				+			"<input type='text' name='maxVal' class='form-control qp-input-to-float pull-right' qp-min='"+initializeMinlength(maxlength, precision)+"' qp-max='"+initializeMaxlength(maxlength, precision)+"' /> "
				+		"</td>";
				valueAdvanceSetting.html("").append(htmlVal);
			}else{
				htmlDefaultVal = "<th>"+dbMsgSource['sc.tabledesign.0042']+"</th>" +
				"<td colspan='3'>" +
				"<input type='text' class='form-control qp-input-float' name='defaultValue' maxlength='200' value=''/> ";
				columnDefault.html("").append (htmlDefaultVal);
				htmlVal = htmlVal 
				+		"<td colspan='2'>"
				+			"<input type='text' name='minVal' class='form-control qp-input-from-float pull-left' /> "
				+			"<div class='qp-separator-from-to' id='separator'>~</div>"
				+			"<input type='text' name='maxVal' class='form-control qp-input-to-float pull-right' /> "
				+		"</td>";
				valueAdvanceSetting.html("").append(htmlVal);
			}
			$.qp.formatFloat($('.qp-input-from-float'));
			$.qp.formatFloat($('.qp-input-to-float'));
			$.qp.formatFloat($('.qp-input-float'));
			$("select[name=operatorCode] option").each(function() {
				var value = $(this).val(); 
				if(value == "7"){
					$(this).remove();
				}
			});
			break;
			
		case DATATYPE.CURRENCY:
			$("#modalDialog").css({"width":"55%"});
			$("#panelBody").css({"height":"auto"});
			$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='dataSource']").attr("disabled",true);
			$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='range']").attr("disabled",false);
			
			htmlDefaultVal = "<th>"+dbMsgSource['sc.tabledesign.0042']+"</th>" +
			 				"<td colspan='3'>" +
			 				"<input type='text' class='form-control qp-input-currency' name='defaultValue' maxlength='200' value='' qp-min='"+initializeMinlength(maxlength, precision)+"' qp-max='"+initializeMaxlength(maxlength, precision)+"' /> ";
			columnDefault.html("").append (htmlDefaultVal);
			htmlVal = htmlVal 
			  +		"<td colspan='2'>"
			  +			"<input type='text' name='minVal' class='form-control qp-input-from-currency pull-left' qp-min='0' qp-min='"+initializeMinlength(maxlength, precision)+"' qp-max='"+initializeMaxlength(maxlength, precision)+"' /> "
			  +			"<div class='qp-separator-from-to' id='separator'>~</div>"
			  +			"<input type='text' name='maxVal' class='form-control qp-input-to-currency pull-right' qp-min='0' qp-min='"+initializeMinlength(maxlength, precision)+"' qp-max='"+initializeMaxlength(maxlength, precision)+"' /> "
			  +		"</td>";
			valueAdvanceSetting.html("").append(htmlVal);
			$.qp.formatCurrency($('.qp-input-from-currency'));
			$.qp.formatCurrency($('.qp-input-to-currency'));
			$.qp.formatCurrency($('.qp-input-currency'));
			$("select[name=operatorCode] option").each(function() {
				var value = $(this).val(); 
				if(value == "7"){
					$(this).remove();
				}
			});
			break;
			
		case DATATYPE.BOOLEAN:
		case DATATYPE.BINARY:
			$("#modalDialog").css({"width":"55%"});
			$("#panelBody").css({"height":"auto"});
			$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='dataSource']").attr("disabled",true);
			$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='range']").attr("disabled",true);
			var radioDefaultValue = '';
			for (var i = 0; i < booleanDefaultValue.length; i++) {
				var obj = booleanDefaultValue[i];
				if (obj.value == '' && $('#defaultValue').val() == '') {
					radioDefaultValue += '<label><input name="defaultValueBoolean" class="qp-input-radio qp-input-radio-margin" type="radio" value="' + obj.value + '" checked="checked">' + obj.label + '</label>';
				} else if ($('#defaultValue').val() == obj.value) {
					radioDefaultValue += '<label><input name="defaultValueBoolean" class="qp-input-radio qp-input-radio-margin" type="radio" value="' + obj.value + '" checked="checked">' + obj.label + '</label>';
				} else {
					radioDefaultValue += '<label><input name="defaultValueBoolean" class="qp-input-radio qp-input-radio-margin" type="radio" value="' + obj.value + '">' + obj.label + '</label>';
				}
			}
			console.log(radioDefaultValue);
			htmlDefaultVal = "<th>"+dbMsgSource['sc.tabledesign.0042']+"</th>" +
							 "<td colspan='3'>" + radioDefaultValue + "</td>";
			columnDefault.html(htmlDefaultVal);
			
			var defaultValueBoolean = $("#domainDesignTbl").find("input[name=defaultValue]").val();
			if(defaultValueBoolean != ""){
				columnDefault.find("input[name=defaultValueBoolean][value=" + defaultValueBoolean + "]").prop('checked', true);
			}
			
			htmlVal = "<th>Value</th>" 
				+ 	"<td colspan='3'></td>";
			valueAdvanceSetting.html("").append(htmlVal);
			break;
		default:
			$("#modalDialog").css({"width":"55%"});
			$("#panelBody").css({"height":"auto"});
			$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='dataSource']").attr("disabled",true);
			htmlDefaultVal = "<th>"+dbMsgSource['sc.tabledesign.0042']+"</th>" +
							 "<td colspan='3'></td>" 
			columnDefault.html("").append(htmlDefaultVal);
			break;
	}
	$("#defaultValueAdvanceSettingTemp").val(columnDefault.html());
}

function saveDialogAdvanceSetting(){
	$.qp.undoFormatNumericForm($("#dialogAdvanceSetting"));
	var constrainsSelected = $('input:radio[name=constrains]:checked').val();
	var groupId = $("#groupBasetypeId").val();
	var tblDomainDesign = $("#domainDesignTbl");
	var tblAdvance = $("#dialogAdvanceSetting").find("#tableDialogAdvanceSetting");
	
	if(constrainsSelected == "any"){
		$(tblDomainDesign).find("#constrainsType").val(0);
		$(tblDomainDesign).find("#defaultValue").val(tblAdvance.find("input[name=defaultValue]").val());
		resetValueAdvanceSetting();
		switch(parseInt(groupId)) {
			case DATATYPE.BOOLEAN:
				tblDomainDesign.find("#defaultValue").val($('[name="defaultValueBoolean"][type="radio"]:checked').val());
				break;
			case DATATYPE.DATE:
			case DATATYPE.DATETIME:
			case DATATYPE.TIME:
				if($("#setTimeNowStatus").val() == "1"){
					tblDomainDesign.find("input[name=defaultType]").val($("#defaultTypeAdvance").val());
					tblDomainDesign.find("input[name=defaultValue]").val("now()");
				}else{
					tblDomainDesign.find("input[name=defaultValue]").val(tblAdvance.find("input[name=defaultValue]").val());
				}
				break;
			default:
				tblDomainDesign.find("input[name=defaultValue]").val(tblAdvance.find("input[name=defaultValue]").val());
				break;
		}
		tblDomainDesign.find("input[name=constrainsType]").val(0);
		$("#flagOperation").val(0);
	}else if(constrainsSelected == "range"){
		var operatorCode = tblAdvance.find("select[name=operatorCode] option:selected").val();
		switch(parseInt(groupId)) {
			case DATATYPE.INTEGER:
			case DATATYPE.CURRENCY:
			case DATATYPE.DECIMAL:
				var maxVal = parseFloat(tblAdvance.find("input[name=maxVal]").val());
				var minVal = parseFloat(tblAdvance.find("input[name=minVal]").val());
				if(tblAdvance.find("input[name=defaultValue]").val() != ""){
					var defaultVal = parseFloat(tblAdvance.find("input[name=defaultValue]").val());
				}else{
					var defaultVal = undefined;
				}
				
				if(checkRange(operatorCode, maxVal, minVal, defaultVal, tblAdvance)){
					return;
				}
				break;
			case DATATYPE.CHAR:
			case DATATYPE.TEXT:
				var maxVal = tblAdvance.find("input[name=maxVal]").val();
				var minVal = tblAdvance.find("input[name=minVal]").val();
				if(tblAdvance.find("input[name=defaultValue]").val() != ""){
					var defaultVal = tblAdvance.find("input[name=defaultValue]").val();
				}else{
					var defaultVal = undefined;
				}
				if(defaultVal != undefined){
					if(operatorCode == "1" && maxVal != undefined){
						if(maxVal.toLowerCase() != defaultVal.toLowerCase()){
							$.qp.showAlertModal(dbMsgSource['sc.domaindesign.0054']);
							return;
						}
					}else if(operatorCode == "6" && minVal != undefined){
						if(minVal.toLowerCase() == defaultVal.toLowerCase()){
							$.qp.showAlertModal(dbMsgSource['sc.domaindesign.0059']);
							return;
						}
					}else if(operatorCode == "7"  && minVal != undefined){
						if(defaultVal.toLowerCase().indexOf(minVal.toLowerCase()) < 0){
							$.qp.showAlertModal(dbMsgSource['sc.domaindesign.0061'])
							return;
						}
					}
				}
				break;
			case DATATYPE.DATE:
				var maxVal = convertDate($("input[name=maxVal]").val(), "DATE") == undefined ? undefined : convertDate($("input[name=maxVal]").val(), "DATE").getTime();
				var minVal = convertDate($("input[name=minVal]").val(), "DATE") == undefined ? undefined : convertDate($("input[name=minVal]").val(), "DATE").getTime();
				if(tblAdvance.find("input[name=defaultValue]").val() != ""){
					var defaultVal = convertDate($("input[name=defaultValue]").val(), "DATE") == undefined ? undefined : convertDate($("input[name=defaultValue]").val(), "DATE").getTime();
				}else{
					var defaultVal = undefined;
				}
				/*if($(tblAdvance).find("input[id=defaultValue]").prop("checked") == false){*/
					if(checkRange(operatorCode, maxVal, minVal, defaultVal, tblAdvance)){
						return;
					}	
				/*}*/
				$("#timeValueTemp").val($("#valueAdvanceSetting").html());
				
				
				break;
			case DATATYPE.DATETIME:
				var maxVal = convertDate($("input[name=maxVal]").val(), "DATETIME") == undefined ? undefined : convertDate($("input[name=maxVal]").val(), "DATETIME").getTime();
				var minVal = convertDate($("input[name=minVal]").val(), "DATETIME") == undefined ? undefined : convertDate($("input[name=minVal]").val(), "DATETIME").getTime();
				if(tblAdvance.find("input[name=defaultValue]").val() != ""){
					var defaultVal = convertDate($("input[name=defaultValue]").val(), "DATETIME") == undefined ? undefined : convertDate($("input[name=defaultValue]").val(), "DATETIME").getTime();
				}else{
					var defaultVal = undefined;
				}
				/*if($(tblAdvance).find("input[id=defaultValue]").prop("checked") == false){*/
					if(checkRange(operatorCode, maxVal, minVal, defaultVal, tblAdvance)){
						return;
					}	
				/*}*/
				$("#timeValueTemp").val($("#valueAdvanceSetting").html());
				break;
			case DATATYPE.TIME:
				var maxVal = convertDate($("input[name=maxVal]").val(), "TIME") == undefined ? undefined : convertDate($("input[name=maxVal]").val(), "TIME").getTime();
				var minVal = convertDate($("input[name=minVal]").val(), "TIME") == undefined ? undefined : convertDate($("input[name=minVal]").val(), "TIME").getTime();
				if(tblAdvance.find("input[name=defaultValue]").val() != ""){
					var defaultVal = convertDate($("input[name=defaultValue]").val(), "TIME") == undefined ? undefined : convertDate($("input[name=defaultValue]").val(), "TIME").getTime();
				}else{
					var defaultVal = undefined;
				}
				/*if($(tblAdvance).find("input[id=defaultValue]").prop("checked") == false){*/
					if(checkRange(operatorCode, maxVal, minVal, defaultVal, tblAdvance)){
						return;
					}	
				/*}*/
				$("#timeValueTemp").val($("#valueAdvanceSetting").html());
			break;
		}
		resetValueAdvanceSetting();
		$("#constrainsType").val(1);
		if($("#setTimeNowStatus").val() == "1"){
			tblDomainDesign.find("input[name=defaultType]").val($("#defaultTypeAdvance").val());
			tblDomainDesign.find("input[name=defaultValue]").val("now()");
		}else{
			tblDomainDesign.find("input[name=defaultValue]").val(tblAdvance.find("input[name=defaultValue]").val());
			tblDomainDesign.find("#defaultType").val($("#defaultTypeAdvance").val());
		}
		$("#minVal").val($("input[name=minVal]").val());
		$("#maxVal").val($("input[name=maxVal]").val());
		$("#domainDesignTbl").find("#operatorCode").val($("select[name='operatorCode'] option:selected").val());
		if(tblAdvance.find("select[name=operatorCode] option:selected").val() == "0"){
			$("#flagOperation").val(9);
		}else{
			$("#flagOperation").val(0);
		}
		
	}else if(constrainsSelected == "dataSource"){
		
		$("#flagOperation").val(0);
		var datasourceSelected = $('input:radio[name=typeOfList]:checked').val();
		if(datasourceSelected == "userDefine"){
			var dialogForm = $("#dialogAdvanceSetting");
			var icon = dialogForm.data('icon');
			var value = "";
			
			var isSupportOptionValue  = "";
			if($(dialogForm).find("input[name='supportOptionValue']")!= undefined){
				isSupportOptionValue = $(dialogForm).find("input[name='supportOptionValue']").prop("checked");
			}
			
			//must have a element
			if(!validateRequiredAtLeast(dialogForm,"tbl_list_Suport",dbMsgSource['sc.tabledesign.0038'],1)){
				return;
			}
			
			//required for option value
			if(!validateRequired(dialogForm,"codelistValue",dbMsgSource['sc.domaindesign.0035'],true)) {
				return;
			}
			
			//check duplication for option value
			if(!validateDuplication(dialogForm,"codelistValue",dbMsgSource['sc.domaindesign.0035'],true)) {
				return;
			}
			
			if(!isSupportOptionValue){
				//required for option name
				if(!validateRequired(dialogForm,"codelistName",dbMsgSource['sc.domaindesign.0034'],true)) {
					return;
				}
				//check duplication for option name
				if(!validateDuplication(dialogForm,"codelistName",dbMsgSource['sc.domaindesign.0034'],true)) {
					return;
				}
			}
			var userDefineValue = "";
			if($("#supportOptionValue").prop("checked")){
				$("#supportOptionFlg").val(0);
			}else{
				$("#supportOptionFlg").val(1);
			}
			$('#tbl_list_Suport').find("tbody tr").each(function() {
				
				if($(this).find("input[name$=supportValue]").prop("checked")){
					userDefineValue += "1" +"�";
					$("#defaultValue").val($(this).find("input[name=codelistValue]").val());
				}else{
					userDefineValue += "0" +"�";
				}
				userDefineValue += $(this).find("input[name=codelistValue]").val() +"�";
				if($(this).find("input[name=codelistName]").val() == undefined){
					userDefineValue += "undefined" +"◘";
				}else{
					userDefineValue += $(this).find("input[name=codelistName]").val() +"◘";
				}
			});
			$("#constrainsType").val(2);
			$("#datasourceType").val(0);
			$("#userDefineValue").val(userDefineValue);
			resetValueAdvanceSetting();
		}else if(datasourceSelected == "codeList"){
			var codelistCode = $("input[name=codelistCode]").val();
			var codelistDefault = $("input[name=codelistDefault]").val();
			if(codelistCode == ""){
				$.qp.showAlertModal(fcomMsgSource['err.sys.0025'].replace("{0}",dbMsgSource['sc.domaindesign.0037']));
				return;
			}
			resetValueAdvanceSetting();
			$("#constrainsType").val(2);
			$("#datasourceType").val(1);
			$("#datasourceId").val(codelistCode);
			$("#defaultValue").val(codelistDefault);
			$("#codelistCodeAutocomplete").val($("#codelistCodeAutocompleteId").val());
			$("#codelistDefaultAutocomplete").val($("#codelistDefaultAutocompleteId").val());
		}else if(datasourceSelected == "sqlBuilder"){
			var datasourceId = $("#tableDialogAdvanceSetting").find("input[name=sqlCode]").val();
			var sqlOutputOptionValue = $("input[name=sqlOutputOptionValue]").val();
			if(datasourceId == ""){
				$.qp.showAlertModal(fcomMsgSource['err.sys.0025'].replace("{0}",dbMsgSource['sc.domaindesign.0038']));
				return;
			} else if(sqlOutputOptionValue == "" && $("#datasourceTypeAdvance").val() == "3"){
				$.qp.showAlertModal(fcomMsgSource['err.sys.0025'].replace("{0}",dbMsgSource['sc.tabledesign.0046']));
				return;
			}
			resetValueAdvanceSetting();
			$("#constrainsType").val(2);
			$("#datasourceType").val($("#datasourceTypeAdvance").val());
			$("#datasourceId").val(datasourceId);
			$("#sqlCodeAutocomplete").val($("#sqlCodeAutocompleteId").val());
			if($("#datasourceTypeAdvance").val() == "3"){
				$("input[name=optionLabel]").val($("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabel]").val());
				$("input[name=optionLabelAutocomplete]").val($("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").val());
				$("input[name=optionValue]").val($("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValue]").val());
				$("input[name=optionValueAutocomplete]").val($("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").val());
			}
		}
	}
	$("#dialogAdvanceSetting").modal("hide");
}

function loadDataByValue(obj){
	$("#groupBaseTypeTemp").val($("input[name=baseType]").attr("output01"));
	var domainDesignTbl = $("#domainDesignTbl");
	var tableDialogAdvanceSetting = $("#tableDialogAdvanceSetting");
	
	var datasourceType = $("#datasourceType").val();
	$("#datasourceTypeAdvance").val(datasourceType);
	
	if(datasourceType == '0'){
		$(tableDialogAdvanceSetting).find("input[name=codelistCode]").val("");
		$(tableDialogAdvanceSetting).find("input[name=codelistDefault]").val("");
		$(tableDialogAdvanceSetting).find("input[name=sqlCode]").val("");
		
		var defaultTR = $(tableDialogAdvanceSetting).find("#default");
		var codelistCodeTR = $(tableDialogAdvanceSetting).find("#codelistCode");
		defaultTR.find(".input-group-addon").html("").append("<span class=\"caret\"></span>");
		codelistCodeTR.find(".input-group-addon").html("").append("<span class=\"caret\"></span>");
		
		var sqlCodeTR = $(tableDialogAdvanceSetting).find("#sqlCode");
		sqlCodeTR.find(".input-group-addon").html("").append("<span class=\"caret\"></span>");
	}else if(datasourceType == '1'){
		$(tableDialogAdvanceSetting).find("input[name=sqlCode]").val("");
		$(tableDialogAdvanceSetting).find("input[name=sqlCodeAutocomplete]").val("");
		
		$(tableDialogAdvanceSetting).find("input[name=sqlOutputOptionLabel]").val("");
		$(tableDialogAdvanceSetting).find("input[name=sqlOutputOptionLabelAutocomplete]").val("");
		
		$(tableDialogAdvanceSetting).find("input[name=sqlOutputOptionValue]").val("");
		$(tableDialogAdvanceSetting).find("input[name=sqlOutputOptionValueAutocomplete]").val("");
		
		var sqlCodeTR = $(tableDialogAdvanceSetting).find("#sqlCode");
		sqlCodeTR.find(".input-group-addon").html("").append("<span class=\"caret\"></span>");
	}else if(datasourceType == '2' || datasourceType == '3'){
		$(tableDialogAdvanceSetting).find("input[name=codelistCode]").val("");
		$(tableDialogAdvanceSetting).find("input[name=codelistCodeAutocomplete]").val("");
		$(tableDialogAdvanceSetting).find("input[name=codelistDefault]").val("");
		$(tableDialogAdvanceSetting).find("input[name=codelistDefaultAutocomplete]").val("");
		var defaultTR = $(tableDialogAdvanceSetting).find("#default");
		var codelistCodeTR = $(tableDialogAdvanceSetting).find("#codelistCode");
		defaultTR.find(".input-group-addon").html("").append("<span class=\"caret\"></span>");
		codelistCodeTR.find(".input-group-addon").html("").append("<span class=\"caret\"></span>");
	}
	
	$("textarea[name=remarkColumn]").val($(obj).closest("tr").find("input[name$='.remark']").val());
	switch (parseInt($(domainDesignTbl).find("#constrainsType").val())) {
		case 0:
			var defaultValue = $("#domainDesignTbl").find("input[name=defaultValue]");
			if($("#setTimeNowStatus").val() == 1){
				$("#defaultTime").attr("disabled", "disabled");
				$("input[name=defaultValue]").prop("checked", true);
			}else{
				$("input[name=defaultValue]").val($(defaultValue).val());
			}
			if($("#defaultType").val() == "1"){
				$("#defaultTime").attr("disabled", "disabled");
				$("input[name=defaultValue]").prop("checked", true);
				$("#defaultTime").val("");
				$("#setTimeNowStatus").val(1);
				$("#defaultTypeAdvance").val(1);
			}else{
				$("input[name=defaultValue]").val($(defaultValue).val());
			}
			$(tableDialogAdvanceSetting).find("input[name=defaultValue]").attr("maxlength", $(domainDesignTbl).find("input[name=maxLength]").val());
			
			// Clear DataSource
			// Clear value SQL
			$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").attr("arg01", "");
			$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").attr("arg01", "");
			$("#dialogAdvanceSetting").find("#sqlCode").find("input[name=sqlCodeAutocomplete]").val("");
			$("#dialogAdvanceSetting").find("#sqlCode").find("input[name=sqlCode]").val("");
			$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").val("");
			$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").val("");
			
			// Clear value Code List
			$(tableDialogAdvanceSetting).find("input[name=codelistCodeAutocomplete]").val("");
			$(tableDialogAdvanceSetting).find("input[name=codelistDefaultAutocomplete]").val("");
			$(tableDialogAdvanceSetting).find("input[name=codelistDefaultAutocomplete]").attr("arg02", "");
			$(tableDialogAdvanceSetting).find("input[name=codelistCode]").val("");
			$(tableDialogAdvanceSetting).find("input[name=codelistCode]").attr("output01", "");
			$(tableDialogAdvanceSetting).find("input[name=codelistDefault]").val("");
			
			// Clear value User Define
			$("#isSupport").find("input[name=supportOptionValue]").prop("checked", false);
			$("#supportValue").find("#tbl_list_Suport tbody").html("");
			
			// Clear value range
			$(tableDialogAdvanceSetting).find("select[name=operatorCode]").val("0");
			$(tableDialogAdvanceSetting).find("input[name=minVal]").hide();
			$(tableDialogAdvanceSetting).find("input[name=maxVal]").hide();
			$(tableDialogAdvanceSetting).find("#separator").hide();
			$('input:radio[name=typeOfList]').filter('[value=userDefine]').prop('checked', true);
			break;
		case 1:
			var defaultValue = $("#domainDesignTbl").find("input[name=defaultValue]");
			$('input:radio[name=constrains]').filter('[value=range]').prop('checked', true);
			$("#valueAdvanceSetting").show();
			$("#defaultValueAdvanceSetting").show();
			$("#defaultValueAdvanceSetting").css({"border-bottom-color":"red","border-bottom-style":"dashed","border-bottom-width":"2px"})
			$("#defaultValueAdvanceSetting").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
			$("#defaultValueAdvanceSetting").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
			$("#valueAdvanceSetting").css({"border-top-color":"red","border-top-style":"dashed","border-top-width":"2px"})
			$("#valueAdvanceSetting").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
			$("#valueAdvanceSetting").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
			
			$("input[name=minVal]").val($("#minVal").val());
			$("input[name=maxVal]").val($("#maxVal").val());
			if($("#setTimeNowStatus").val() == 1){
				$("#defaultTime").attr("disabled", "disabled");
//				$('#valueAdvanceSetting').hide();
				$("input[name=defaultValue]").prop("checked", true);
//				$("#defaultValueAdvanceSetting").css({"border-color":"red","border-style":"dashed","border-width":"2px"})
			}else{
				$("input[name=defaultValue]").val($(defaultValue).val());
				$('#valueAdvanceSetting').show();
			}
			if($("#defaultType").val() == "1"){
				$("#defaultTime").attr("disabled", "disabled");
				$("input[name=defaultValue]").prop("checked", true);
				$("#defaultTime").val("");
				$("#setTimeNowStatus").val(1);
				$("#defaultTypeAdvance").val(1);
			}else{
				$("input[name=defaultValue]").val($(defaultValue).val());
			}
			
			$('#valueAdvanceSetting').find("select[name=operatorCode]").val($("input[name=operatorCode]").val());
			var operator = $('#valueAdvanceSetting').find("select[name=operatorCode]");
			setStatusOperatorcode(operator,true);
			
			// Clear DataSource
			// Clear value SQL
			$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").attr("arg01", "");
			$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").attr("arg01", "");
			
			$("#dialogAdvanceSetting").find("#sqlCode").find("input[name=sqlCodeAutocomplete]").val("");
			$("#dialogAdvanceSetting").find("#sqlCode").find("input[name=sqlCode]").val("");
			
			$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").val("");
			$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").val("");
			// Clear value Code List
			$(tableDialogAdvanceSetting).find("input[name=codelistCodeAutocomplete]").val("");
			$(tableDialogAdvanceSetting).find("input[name=codelistDefaultAutocomplete]").val("");
			$(tableDialogAdvanceSetting).find("input[name=codelistDefaultAutocomplete]").attr("arg02", "");
			$(tableDialogAdvanceSetting).find("input[name=codelistDefault]").val("");
			$(tableDialogAdvanceSetting).find("input[name=codelistCode]").val("");
			$(tableDialogAdvanceSetting).find("input[name=codelistCode]").attr("output01", "");
			// Clear value User Define
			$("#isSupport").find("input[name=supportOptionValue]").prop("checked", false);
			$("#supportValue").find("#tbl_list_Suport tbody").html("");
			$('input:radio[name=typeOfList]').filter('[value=userDefine]').prop('checked', true);
			break;
		case 2:
			$('input:radio[name=constrains]').filter('[value=dataSource]').prop('checked', true);
			$("#typeOfList").show();
			$("#valueAdvanceSetting").hide();
			$("#defaultValueAdvanceSetting").hide();
			
			// Clear value range
			$(tableDialogAdvanceSetting).find("select[name=operatorCode]").val("0");
			$(tableDialogAdvanceSetting).find("input[name=minVal]").hide();
			$(tableDialogAdvanceSetting).find("input[name=maxVal]").hide();
			$(tableDialogAdvanceSetting).find("#separator").hide();
			
			switch (parseInt($("#datasourceType").val())) {
				case 0:
					$('input:radio[name=typeOfList]').filter('[value=userDefine]').prop('checked', true);
					$("#supportValue").show();
					$("#isSupport").show();
					
					var userDefineValue = $("#userDefineValue").val().split("◘");
					
					var tableSupport = $("#tbl_list_Suport");
					tableSupport.html("");
					var htmlTableSupport = "";
					
					if($("#supportOptionFlg").val() == "1"){
						$("#supportOptionValue").prop('checked', false);
						htmlTableSupport = "<colgroup>" 
								+ 			"<col width='85px'>"
								+ 			"<col width='50%'class=\"colOptionName\">"
								+	 		"<col width=''>"
								+	 		"<col width='40px'>"
								+ 		"</colgroup>"
								+ 		"<thead>"
								+ 			"<tr>"
								+	 			"<th>"+dbMsgSource['sc.tabledesign.0042']+" </br>(<a id=\"colDefaultValue\" href='javascript:void(0)' onclick='removeDefaultValue(this);' data-toggle='tooltip' title='Click to remove default value'>"+dbMsgSource['sc.tabledesign.0081']+"</a>)</th>"
								+ 				"<th class=\"colOptionName\">"+dbMsgSource['sc.tabledesign.0045']+"<span class='qp-required-field'> (*) </span></th>"
								+ 				"<th>"+dbMsgSource['sc.tabledesign.0046']+"<span class='qp-required-field'> (*) </span></th>"
								+ 				"<th></th>"
								+ 			"</tr>"
								+ 		"</thead>"
								+ 		"<tbody class='ui-sortable'>";
						
						for (var int = 0; int < userDefineValue.length -1; int++) {
							var userDefineValueRow = userDefineValue[int].split("�");
							if(userDefineValueRow[0] == "1"){
								htmlTableSupport = htmlTableSupport 
								+ 			"<tr>"
								+       	 "<td><input type='radio' value='supportValue' name='supportValue' checked='checked'></td>"
							}else{
								htmlTableSupport = htmlTableSupport 
								+       	 "<td><input type='radio' value='supportValue' name='supportValue'></td>"
							}
							
							htmlTableSupport = htmlTableSupport 
								+       	 "<td class=\"td-word-wrap colOptionName\"><input type=\"text\" class=\"form-control\" name=\"codelistName\" value=\""+ $.qp.escapseHTML(userDefineValueRow[2]) +"\" maxlength=\"200\"/></td>"
								+       	 "<td class=\"td-word-wrap colOptionValue\"><input type=\"text\" class=\"form-control\" name=\"codelistValue\" value=\""+ $.qp.escapseHTML(userDefineValueRow[1]) +"\" maxlength=\"200\"/></td>"
								+		 	 "<td><a class='btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action' title='Remove' onclick='$.qp.removeRowJSEx(this);' style='margin-top: 3px;' href='javascript:void(0)'></a></td>"
								+ 			"</tr>";
						}
						htmlTableSupport = htmlTableSupport 
								+ 		"</tbody>";
								
						tableSupport.append(htmlTableSupport);
					}else{
						$("#supportOptionValue").prop('checked', true);
						htmlTableSupport = "<colgroup>" 
							+ 			"<col width='85px'>"
							+ 			"<col width='50%'class=\"colOptionName\">"
							+	 		"<col width=''>"
							+	 		"<col width='40px'>"
							+ 		"</colgroup>"
							+ 		"<thead>"
							+ 			"<tr>"
							+	 			"<th>"+dbMsgSource['sc.tabledesign.0042']+" </br>(<a id=\"colDefaultValue\" href='javascript:void(0)' onclick='removeDefaultValue(this);' data-toggle='tooltip' title='Click to remove default value'>"+dbMsgSource['sc.tabledesign.0081']+"</a>)</th>"
							+ 				"<th class=\"colOptionName\">"+dbMsgSource['sc.tabledesign.0045']+"<span class='qp-required-field'> (*) </span></th>"
							+ 				"<th>"+dbMsgSource['sc.tabledesign.0046']+"<span class='qp-required-field'> (*) </span></th>"
							+ 				"<th></th>"
							+ 			"</tr>"
							+ 		"</thead>"
							+ 		"<tbody class='ui-sortable'>";

						for (var int = 0; int < userDefineValue.length -1; int++) {
							var userDefineValueRow = userDefineValue[int].split("�");
							if(userDefineValueRow[0] == "1"){
								htmlTableSupport = htmlTableSupport 
								+ 			"<tr>"
								+       	 "<td><input type='radio' value='supportValue' name='supportValue' checked='checked'></td>"
							}else{
								htmlTableSupport = htmlTableSupport 
								+       	 "<td><input type='radio' value='supportValue' name='supportValue'></td>"
							}
							var optionName = "";
							if(userDefineValueRow[2] != "null"){
								optionName += userDefineValueRow[2];
							}
							
							htmlTableSupport = htmlTableSupport 
								+       	 "<td class=\"td-word-wrap colOptionName\"><input type=\"text\" class=\"form-control\" name=\"codelistName\" value=\""+ $.qp.escapseHTML(optionName) +"\" maxlength=\"200\"/></td>"
								+       	 "<td class=\"td-word-wrap colOptionValue\"><input type=\"text\" class=\"form-control\" name=\"codelistValue\" value=\""+ $.qp.escapseHTML(userDefineValueRow[1]) +"\" maxlength=\"200\"/></td>"
								+		 	 "<td><a class='btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action' title='Remove' onclick='$.qp.removeRowJSEx(this);' style='margin-top: 3px;' href='javascript:void(0)'></a></td>"
								+ 			"</tr>";
						}
						htmlTableSupport = htmlTableSupport 
								+ 		"</tbody>";
								
						tableSupport.append(htmlTableSupport);
						$('#tbl_list_Suport').find(".colOptionName").hide();
					}
					
					$("#supportValue").css({"border-bottom-color":"red","border-bottom-style":"dashed","border-bottom-width":"2px"})
					$("#supportValue").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
					$("#supportValue").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
					$("#typeOfList").css({"border-top-color":"red","border-top-style":"dashed","border-top-width":"2px"})
					$("#typeOfList").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
					$("#typeOfList").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
					$("#isSupport").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
					$("#isSupport").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
					$('[data-toggle="tooltip"]').tooltip();
					
					// Clear value SQL
					$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").attr("arg01", "");
					$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").attr("arg01", "");
					
					$("#dialogAdvanceSetting").find("#sqlCode").find("input[name=sqlCodeAutocomplete]").val("");
					$("#dialogAdvanceSetting").find("#sqlCode").find("input[name=sqlCode]").val("");
					
					$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").val("");
					$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").val("");
					// Clear value Code List
					$(tableDialogAdvanceSetting).find("input[name=codelistCodeAutocomplete]").val("");
					$(tableDialogAdvanceSetting).find("input[name=codelistDefaultAutocomplete]").val("");
					$(tableDialogAdvanceSetting).find("input[name=codelistDefaultAutocomplete]").attr("arg02", "");
					$(tableDialogAdvanceSetting).find("input[name=codelistDefault]").val("");
					
				break;
				case 1:
					$('input:radio[name=typeOfList]').filter('[value=codeList]').prop('checked', true);
					$("#codelistCode").show();
					$("#default").show();
					$("#notSupportValue").hide();
					$("#sqlCode").hide();
					$("#supportValue").hide();
					$("#isSupport").hide();
					
					$(tableDialogAdvanceSetting).find("input[name=codelistCodeAutocomplete]").val($("#codelistCodeAutocomplete").val());
					$(tableDialogAdvanceSetting).find("input[name=codelistCode]").val($("#datasourceId").val());
					$(tableDialogAdvanceSetting).find("input[name=codelistDefaultAutocomplete]").val($("#codelistDefaultAutocomplete").val());
					$(tableDialogAdvanceSetting).find("input[name=codelistDefaultAutocomplete]").attr("arg02", $("#datasourceId").val());
					$(tableDialogAdvanceSetting).find("input[name=codelistDefault]").val($("#defaultValue").val());
					
					var hrefVal = $("#codelistView").attr("href").split("=")[0] + "=" + $("#domainDesignTbl").find("#datasourceId").val();
					$("#codelistView").attr("href", hrefVal);
					$("#typeOfList").css({"border-top-color":"red","border-top-style":"dashed","border-top-width":"2px"})
					$("#typeOfList").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
					$("#typeOfList").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
					$("#codelistCode").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
					$("#codelistCode").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
					$("#default").css({"border-bottom-color":"red","border-bottom-style":"dashed","border-bottom-width":"2px"})
					$("#default").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
					$("#default").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
					
					// Clear value User Define
					$("#isSupport").find("input[name=supportOptionValue]").prop("checked", false);
					$("#supportValue").find("#tbl_list_Suport tbody").html("")
					// Clear value SQL
					$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").attr("arg01", "");
					$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").attr("arg01", "");
					
					$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").val("");
					$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").val("");
					
				break;
				case 2:
				case 3:
					$("#datasourceTypeAdvance").val($("#datasourceType").val());
					$("input[name=sqlCode]").val($("#datasourceId").val());
					$('input:radio[name=typeOfList]').filter('[value=sqlBuilder]').prop('checked', true);
					
					var hrefVal = $("#sqlView").attr("href").split("/")[0] + "/" + $("#sqlView").attr("href").split("/")[1];
					switch (parseInt($("#domainDesignTbl").find("#datasourceType").val())) {
						case 2:
							hrefVal = hrefVal + "/autocomplete/view?autocompleteForm.autocompleteId=";
							break;
						case 3:
							hrefVal = hrefVal + "/sqldesign/view?sqlDesignForm.sqlDesignId=";
							break;
					}
					hrefVal = hrefVal + $("#domainDesignTbl").find("#datasourceId").val();
					$("#sqlView").attr("href", hrefVal);
					
					$("#sqlCode").show();
					$("#codelistCode").hide();
					$("#default").hide();
					$("#supportValue").hide();
					$("#isSupport").hide();
					$("#notSupportValue").hide();
					
					// Clear value User Define
					$("#isSupport").find("input[name=supportOptionValue]").prop("checked", false);
					$("#supportValue").find("#tbl_list_Suport tbody").html("")
					// Clear value Code List
					$(tableDialogAdvanceSetting).find("input[name=codelistCodeAutocomplete]").val("");
					$(tableDialogAdvanceSetting).find("input[name=codelistDefaultAutocomplete]").val("");
					$(tableDialogAdvanceSetting).find("input[name=codelistDefaultAutocomplete]").attr("arg02", "");
					$(tableDialogAdvanceSetting).find("input[name=codelistDefault]").val("");
					
					$("#sqlCode").css({"border-bottom-color":"red","border-bottom-style":"dashed","border-bottom-width":"2px"})
					$("#sqlCode").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
					$("#sqlCode").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
					$("#typeOfList").css({"border-top-color":"red","border-top-style":"dashed","border-top-width":"2px"})
					$("#typeOfList").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
					$("#typeOfList").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
					if($("#datasourceTypeAdvance").val() == 3){
						$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").show();
						$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").show();
						$("#dialogAdvanceSetting").find("#sqlCode").removeAttr("style");
						$("#dialogAdvanceSetting").find("#sqlCode").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
						$("#dialogAdvanceSetting").find("#sqlCode").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
						$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
						$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
						$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
						$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
						$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").css({"border-bottom-color":"red","border-bottom-style":"dashed","border-bottom-width":"2px"})

						$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").attr("arg01", $("#datasourceId").val());
						$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").attr("arg01", $("#datasourceId").val());
						
						$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabel]").val($("#optionLabel").val());
						$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").val($("#optionLabelAutocomplete").val());
						$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValue]").val($("#optionValue").val());
						$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").val($("#optionValueAutocomplete").val());
					}
				break;
			}
		break;
		default:
			$("textarea[name=remarkColumn]").val("");
//			$("input[name=defaultValue]").val("");
			// Clear DataSource
			// Clear value SQL
			$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").attr("arg01", "");
			$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").attr("arg01", "");
			$("#dialogAdvanceSetting").find("#sqlCode").find("input[name=sqlCodeAutocomplete]").val("");
			$("#dialogAdvanceSetting").find("#sqlCode").find("input[name=sqlCode]").val("");
			$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").val("");
			$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").val("");
			
			// Clear value Code List
			$(tableDialogAdvanceSetting).find("input[name=codelistCodeAutocomplete]").val("");
			$(tableDialogAdvanceSetting).find("input[name=codelistDefaultAutocomplete]").val("");
			$(tableDialogAdvanceSetting).find("input[name=codelistDefaultAutocomplete]").attr("arg02", "");
			$(tableDialogAdvanceSetting).find("input[name=codelistCode]").val("");
			$(tableDialogAdvanceSetting).find("input[name=codelistCode]").attr("output01", "");
			$(tableDialogAdvanceSetting).find("input[name=codelistDefault]").val("");
			
			// Clear value User Define
			$("#isSupport").find("input[name=supportOptionValue]").prop("checked", false);
			$("#supportValue").find("#tbl_list_Suport tbody").html("");
			
			// Clear value range
			$(tableDialogAdvanceSetting).find("select[name=operatorCode]").val("0");
			$(tableDialogAdvanceSetting).find("input[name=minVal]").hide();
			$(tableDialogAdvanceSetting).find("input[name=maxVal]").hide();
			$(tableDialogAdvanceSetting).find("#separator").hide();
			$('input:radio[name=typeOfList]').filter('[value=userDefine]').prop('checked', true);
			$("#defaultValue").val("");
		break;
	}
}

function removeDefaultValue(obj){
	$('#tbl_list_Suport').find("tbody tr").each(function() {
		$(this).find("input[name=supportValue]").prop("checked", false);
	});
}

function resetValueAdvanceSetting(modal){
	
	if (modal != undefined) {
		
	} else {
		$("#constrainsType").val("");
		$("#datasourceId").val("");
		$("#datasourceType").val("");
	//	$("#remark").val("");
		$("#defaultValue").val("");
		$("#operatorCode").val("");
		$("#minVal").val("");
		$("#maxVal").val("");
		$("#codelistCodeAutocomplete").val("");
		$("#codelistDefaultAutocomplete").val("");
		$("#sqlCodeAutocomplete").val("");
		$("#userDefineValue").val("");
		$("#supportOptionFlg").val("");
	}
}

function changeCodeListAC(obj){
	var value = $(obj.target).next("input[type=hidden]").val();
	hrefVal = $("#codelistView").attr("href").split("=")[0] + "=" + value;
	$("#codelistView").attr("href", hrefVal);
	if(value == ""){
		$("#codelistView").hide();
	}else{
		$("#codelistView").show();
	}
	var nextInput = $("#codelistDefaultAutocompleteId");
	var nextHidden = nextInput.next("input[type=hidden]");
	nextInput.val("");
	nextInput.attr("arg02",value);
	nextHidden.val("");
	nextInput.data("ui-autocomplete")._trigger("change");	
};

function changeDefaultCodeListAC(obj){
	$("#tableDialogAdvanceSetting").find("#default").find("input[name=codelistDefaultAutocomplete]").attr("arg02", $("#tableDialogAdvanceSetting").find("#codelistCode").find("input[name=codelistCode]").val());
}

function changeSqlCodeAC(obj){
	var hrefVal = "";
	var isSqlBuilder;
	if(obj.item != undefined){
		switch (parseInt(obj.item.output02)) {
		case 2:
		case 3:
			$("#datasourceTypeAdvance").val(2);	
			hrefVal = CONTEXT_PATH + "/autocomplete/view?autocompleteForm.autocompleteId=";
			isSqlBuilder = false;
			break;
		case 4:
		case 5:
			$("#datasourceTypeAdvance").val(3);
			hrefVal = CONTEXT_PATH + "/sqldesign/view?sqlDesignForm.sqlDesignId=";
			isSqlBuilder = true;
			break;
		}
	}
	var value = $(obj.target).next("input[type=hidden]").val();
	hrefVal = hrefVal + value;
	
	$("#sqlView").attr("href", hrefVal);
	if(value == ""){
		$("#sqlView").hide();
		$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").hide();
		$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").hide();
		$("#dialogAdvanceSetting").find("#sqlCode").removeAttr("style");
		$("#dialogAdvanceSetting").find("#sqlCode").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
		$("#dialogAdvanceSetting").find("#sqlCode").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
		$("#dialogAdvanceSetting").find("#sqlCode").css({"border-bottom-color":"red","border-bottom-style":"dashed","border-bottom-width":"2px"})
		$("#datasourceTypeAdvance").val("");
		$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabel]").val("");
		$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").val("");
		$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValue]").val("");
		$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").val("");
	}else{
		$("#sqlView").show();
		$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").attr("arg01", value);
		$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").attr("arg01", value);
		
		if(isSqlBuilder){
			$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").show();
			$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").show();
			$("#dialogAdvanceSetting").find("#sqlCode").removeAttr("style");
			$("#dialogAdvanceSetting").find("#sqlCode").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
			$("#dialogAdvanceSetting").find("#sqlCode").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
			$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
			$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
			$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
			$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
			$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").css({"border-bottom-color":"red","border-bottom-style":"dashed","border-bottom-width":"2px"})
			
			$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabel]").val("");
			$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").val("");
			$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValue]").val("");
			$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").val("");
			
		}else{
			$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").hide();
			$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").hide();
		}
	}
	
	$.qp.initialCatAutocomplete($("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]"));
	$.qp.initialCatAutocomplete($("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]"));

};

function removeColumnValue(table,direction,row){
	if($("#supportOptionValue").prop("checked")){
		$('#tbl_list_Suport').find(".colOptionName").hide();
	}
}

function getOptionNameOutputSql(obj){
	var url = CONTEXT_PATH + "/tabledesign/getSqlDesignOutputsForAdvanceSetting?sqlDesignId="+obj.arg01;
	var data = $.qp.getData(url);
	var results = [];
	
	if(data.length>0){
		var count = 0;
		
		for ( var key in data) {
			if (data.hasOwnProperty(key)) {
				var hasChild = false;
				
				for ( var item in data) {
					if (data.hasOwnProperty(item)) {
						if (data[item].groupIndex.indexOf(data[key].groupIndex + ".") != -1) {
							hasChild = true;
						}
					}
				}
				results.push({
					"optionLabel" : data[key].sqlDesignOutputName,
					"optionValue" : data[key].sqlDesignOutputId,
					"level" : data[key].groupIndex,
					"hasChild": hasChild
				});
			}
		}
		
		
	}
	return results;
}

function setCurrentTime(obj){
	if(!$('input:radio[name=constrains]').filter('[value=any]').prop('checked')){
		if(obj.checked){
			$("#defaultTime").val("");
			$("#defaultTime").attr("disabled", "disabled");
//			$("#defaultValueAdvanceSetting").css({"border-color":"red","border-style":"dashed","border-width":"2px"})
//			$('#valueAdvanceSetting').hide();
			$("#setTimeNowStatus").val(1);
			$("#defaultTypeAdvance").val(1);
			$("#defaultType").val(1);
		}else{
			$("#defaultTime").attr('disabled', false);
//			$('#valueAdvanceSetting').show();
//			$("#defaultValueAdvanceSetting").css({"border-top-color":"none","border-top-style":"none","border-top-width":"none"})
			$("#setTimeNowStatus").val(0);
			$("#defaultTypeAdvance").val(0);
			$("#defaultType").val(0);
		}
	}else{
		if(obj.checked){
			$("#defaultTime").val("");
			$("#defaultTime").attr("disabled", "disabled");
			$("#setTimeNowStatus").val(1);
			$("#defaultTypeAdvance").val(1);
			$("#defaultType").val(1);
		}else{
			$("#defaultTime").attr('disabled', false);
			$("#setTimeNowStatus").val(0);
			$("#defaultTypeAdvance").val(0);
			$("#defaultType").val(0);
		}
	}
}
