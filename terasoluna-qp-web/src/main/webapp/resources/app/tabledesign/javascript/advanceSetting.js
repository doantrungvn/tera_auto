/**
 * DungNN - modify 20160804
 * @param obj
 */
function openDialogAutocompleteSetting(obj) {
	var $thisTR = $(obj).closest("tr");

	var dataTypeFlg = $thisTR.find("input[name$='.dataTypeFlg']").val();
	
	
	//using primitive
	if(dataTypeFlg == "0"){
		var precision = parseInt($thisTR.find("input[name$='.decimalPart']").autoNumeric('get'));
		var maxlength = parseInt($thisTR.find("input[name$='.maxlength']").autoNumeric('get'));
		if(maxlength <= precision){
			$.qp.showAlertModal(dbMsgSource['sc.domaindesign.0062']);
			return;
		}

		$($("#dialogAdvanceSetting")).modal(
				{ 
					show: true,
					closable: false,
					keyboard:false,
					backdrop:'static'
				}
		);
		
		/**
		 * DungNN - 20160804 - fixing bug don't clear old setting
		 */
		resetBeforceLoading($thisTR);

	}else if(dataTypeFlg == "1"){
		$($("#dialogAdvanceSettingDomain")).modal(
				{ 
					show: true,
					closable: false,
					keyboard:false,
					backdrop:'static'
				}
		);
		$("#dialogAdvanceSettingDomain").find("#supportValue").hide();
		$("#dialogAdvanceSettingDomain").find("#notSupportValue").hide();
		$("#dialogAdvanceSettingDomain").find("#isSupport").hide();
		$("#dialogAdvanceSettingDomain").find("#typeOfList").hide();
		$("#dialogAdvanceSettingDomain").find("#sqlCode").hide();
		$("#dialogAdvanceSettingDomain").find("#codelistCode").hide();
		$("#dialogAdvanceSettingDomain").find("#default").hide();
		$("#dialogAdvanceSettingDomain").find("#valueAdvanceSetting").hide();
		/*$("#dialogAdvanceSettingDomain").find("#selectConstrains").val("0");*/
		$("#dialogAdvanceSettingDomain").find("#defaultValueAdvanceSetting").removeAttr("style");
		$("#dialogAdvanceSettingDomain").find("#tabledialogAdvanceSettingDomain").find("tbody tr input[type=radio][value='any']").prop('checked', true);
	}

	$("#currentRow").val($thisTR.find("input[name$='.name']").attr("name").match(/\d+/)[0]);
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
}

/**
 * DungNN - 20160804 - fixing bug don't clear old setting
 */
function resetBeforceLoading($thisTR) {
	
	$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").hide();
	$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").hide();
	$("#dialogAdvanceSetting").find("#supportValue").hide();
	$("#dialogAdvanceSetting").find("#notSupportValue").hide();
	$("#dialogAdvanceSetting").find("#isSupport").hide();
	$("#dialogAdvanceSetting").find("#typeOfList").hide();
	$("#dialogAdvanceSetting").find("#sqlCode").hide();
	$("#dialogAdvanceSetting").find("#codelistCode").hide();
	$("#dialogAdvanceSetting").find("#default").hide();
	$("#dialogAdvanceSetting").find("#valueAdvanceSetting").hide();
	/*$("#dialogAdvanceSetting").find("#selectConstrains").val("0");*/
	$("#dialogAdvanceSetting").find("#defaultValueAdvanceSetting").removeAttr("style");
	$("#dialogAdvanceSetting").find("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='any']").prop('checked', true);
	$.qp.initialCatAutocomplete($("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]"));

	$("#dialogAdvanceSetting").find("#datasourceTypeAdvance").val($thisTR.find("input[name$='.datasourceType']").val());
	/*$("#dialogAdvanceSetting").find("#datasourceTypeAdvance").val($thisTR.find("input[name$='.datasourceType']").val());*/
	
	$("#dialogAdvanceSetting").find("#optionLabelAdvance").val($thisTR.find("input[name$='.optionLabel']").val());
	$("#dialogAdvanceSetting").find("#optionValueAdvance").val($thisTR.find("input[name$='.optionValue']").val());
	$("#dialogAdvanceSetting").find("#optionLabelAutocpmleteAdvance").val($thisTR.find("input[name$='.optionLabelAutocomplete']").val());
	$("#dialogAdvanceSetting").find("#optionValueAutocpmleteAdvance").val($thisTR.find("input[name$='.optionValueAutocomplete']").val());

	$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").attr("arg01", "");
	$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").attr("arg01", "");
	$("#dialogAdvanceSetting").find("#sqlCode").find("input[name=sqlCodeAutocomplete]").val("");
	$("#dialogAdvanceSetting").find("#sqlCode").find("input[name=sqlCode]").val("");
	$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").val("");
	$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").val("");
	
	var tableDialogAdvanceSetting = $("#tableDialogAdvanceSetting");
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
	$("#supportValue").find("#tbl_list_Suport tbody").html(genHtmlUserDefineDefault());
	
	// Clear value range
	$(tableDialogAdvanceSetting).find("select[name=operatorCode]").val("0");
	
	
	if($thisTR.find("input[name$='.datasourceType']").val() != "" || $thisTR.find("input[name$='.datasourceType']").val() != "0"){
		$("#dialogAdvanceSetting").find("input[name$='Autocomplete']").each(function(){
			$(this).data("ui-autocomplete")._trigger("close");
		});
	}
	
}



function changeConstrains(obj){
	
	var groupTypeId = $("#groupTypeId").val();
	var valueAdvanceSetting = $("#valueAdvanceSetting");
	var dialogAdvanceSetting = $("#dialogAdvanceSetting");
	
	if(obj.value == "any"){
		$("#typeOfList").hide();
		$("#valueAdvanceSetting").hide();
		$("#defaultValueAdvanceSetting").show();
		$("#sqlCode").hide();
		$("#codelistCode").hide();
		$("#default").hide();
		$("#supportValue").hide();
		$("#notSupportValue").hide();
		$("#isSupport").hide();
		$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").hide();
		$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").hide();
		$("#defaultValueAdvanceSetting").removeAttr("style");
	}else if(obj.value == "range"){
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
		if($("#setTimeNowStatus").val() == 1){
			if($("#timeValueTemp").val() != ""){
				$("#valueAdvanceSetting").html("").append($("#timeValueTemp").val());
			}
		}
		var operator = $("select[name=operatorCode]");
		setStatusOperatorcode(operator,true);
		$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").hide();
		$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").hide();
	}else if(obj.value == "dataSource"){
		
		$("#dialogAdvanceSetting").find("#optionLabelAdvance").val($("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabel]").val());
		$("#dialogAdvanceSetting").find("#optionValueAdvance").val($("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValue]").val());
		$("#dialogAdvanceSetting").find("#optionLabelAutocpmleteAdvance").val($("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").val());
		$("#dialogAdvanceSetting").find("#optionValueAutocpmleteAdvance").val($("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").val());
		
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
				if(typeOfListName == "codeList"){
					settingCodelistShow();
				}else if(typeOfListName == "sqlBuilder"){
					settingSqlBuilderShow();
				}else{
					$("#isSupport").show();
					/*var curentRow = $('tr', tmp_list_table).eq(parseInt($("#currentRow").val()) +1 );
					var userDefineValue = curentRow.find("input[name$='.userDefineValue']").val();
					var supportOptionFlg = curentRow.find("input[name$='.supportOptionFlg']").val();
					if(userDefineValue == "" || supportOptionFlg == ""){
						$("#tbl_list_Suport").html("").append(genHtmlNonSuportTable());
						$('input:radio[name=typeOfList]').filter('[value=userDefine]').prop('checked', true);
						$("#isSupport").find("input[name=supportOptionValue]").prop('checked', false);
					}*/
					
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
					+ "<td colspan='3'>"
					+ "<label class='radio-inline'><input type='radio' name='typeOfList' value='userDefine' onchange ='click(this)'>&nbsp&nbsp</label> "
					+ "<label class='radio-inline'><input type='radio' name='typeOfList' value='codeList'>"+dbMsgSource['sc.tabledesign.0039']+"&nbsp&nbsp</label> "
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
		
		var datasourceTypeAdvance = $("#dialogAdvanceSetting").find("#datasourceTypeAdvance").val();
		if(datasourceTypeAdvance == ""){
			$("#dialogAdvanceSetting").find("input[name=typeOfList][value=userDefine]").prop('checked', true);
			$("#isSupport").show();
			$("#sqlCode").hide();
			$("#codelistCode").hide();
			$("#default").hide();
			$("#supportValue").show();
			$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").hide();
			$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").hide();
			if($("#supportValue").find("#tbl_list_Suport tr").length == 0){
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
				
				$("#supportValue").find("#tbl_list_Suport").html("").append(htmlTableSupport);
			}
			$("#isSupport").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
			$("#isSupport").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
			$("#supportValue").css({"border-bottom-color":"red","border-bottom-style":"dashed","border-bottom-width":"2px"})
			$("#supportValue").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
			$("#supportValue").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
		}
	}
}

function genHtmlNonSuportTable(){
	return "<colgroup>" 
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
	+		"</tr>";
}

function changeSupportOptionValue(obj){
	if(obj.checked){
		$('#tbl_list_Suport').find(".colOptionName").hide();
		
	}else{
		$('#tbl_list_Suport').find(".colOptionName").show();
	}
}

function changeDataSource(obj){
	$("#dialogAdvanceSetting").find("#optionLabelAdvance").val($("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabel]").val());
	$("#dialogAdvanceSetting").find("#optionValueAdvance").val($("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValue]").val());
	$("#dialogAdvanceSetting").find("#optionLabelAutocpmleteAdvance").val($("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").val());
	$("#dialogAdvanceSetting").find("#optionValueAutocpmleteAdvance").val($("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").val());
	$("#typeOfList").css({"border-bottom-color":"none","border-bottom-style":"none","border-bottom-width":"none"})
	if(obj.value == "userDefine"){
		$("#isSupport").show();
		$("#sqlCode").hide();
		$("#codelistCode").hide();
		$("#default").hide();
		$("#supportValue").show();
		$("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").hide();
		$("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").hide();
		if($("#supportValue").find("#tbl_list_Suport tr").length == 0){
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
			
			$("#supportValue").find("#tbl_list_Suport").html("").append(htmlTableSupport);
		}
		$("#isSupport").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
		$("#isSupport").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
		$("#supportValue").css({"border-bottom-color":"red","border-bottom-style":"dashed","border-bottom-width":"2px"})
		$("#supportValue").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
		$("#supportValue").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
		
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
	$("#codelistRegis").show();
	$("#notSupportValue").hide();
	$("#sqlCode").hide();
	$("#supportValue").hide();
	$("#isSupport").hide();
	
	$("#default").css({"border-bottom-color":"red","border-bottom-style":"dashed","border-bottom-width":"2px"})
	$("#default").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
	$("#default").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
	$("#typeOfList").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
	
	$("#codelistCode").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
	$("#codelistCode").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
}

function settingSqlBuilderShow(){
	$("#sqlCode").show();
	$("#sqlRegis").show();
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

function setStatusOperatorcode(obj,keepValue){
	var groupTypeId = $(obj).closest(".modal").find("#groupTypeId").val();
	if(groupTypeId != undefined && (groupTypeId == "4" || groupTypeId == "8" || groupTypeId == "9")){
//		if($("#setTimeNowStatus").val() != 1){
		$(obj).closest("tr").find("input[name=minVal]").parent().hide();
		$(obj).closest("tr").find("input[name=maxVal]").parent().hide();
		$(obj).closest("tr").find("#separator").hide();
//		}
		switch (parseInt($(obj).val())) {
			case 1:
			case 2:
			case 3:
				$(obj).closest("tr").find("input[name=maxVal]").parent().show();
				$(obj).closest("tr").find("input[name=maxVal]").parent().removeClass("pull-right").addClass("pull-left");
				break;
			case 4:
			case 5:
				$(obj).closest("tr").find("input[name=minVal]").parent().show();
				break;
			case 6:
			case 7:
				$(obj).closest("tr").find("input[name=minVal]").parent().show();
				break;
			case 8:
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
	
	var $thisTR = $(obj).closest("tr");
	
	$("#fmtCode").text($thisTR.find("select[name$='.dataType'] option:selected").attr("validationrule"));
	var groupId = $thisTR.find("select[name$='.dataType'] option:selected").attr("basetypegroup");
	var dataType = $thisTR.find("select[name$='.dataType'] option:selected").val();
	$("#groupTypeId").val(groupId);
	
	var columnDefault = $("#defaultValueAdvanceSetting");
	var htmlDefaultVal = "";
	
	var precision = $thisTR.find("input[name$='.decimalPart']").val();
	var maxlength = $thisTR.find("input[name$='.maxlength']").val();
	var valueAdvanceSetting = $("#valueAdvanceSetting");
	
	htmlVal = "<th>"+dbMsgSource['sc.tabledesign.0043']+"</th>"
		  +		"<td>"
		  +			" <select name='operatorCode' class='form-control qp-input-select' onchange='setStatusOperatorcode(this)'> ";
					$.each($.parseJSON($("#operatorCode").val()), function(key,value){
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
			htmlDefaultVal = "<th>"+dbMsgSource['sc.tabledesign.0042']+"</th>" +
							 "	<td colspan='3'>" +
							 "		<input type='text' class='form-control qp-input-text' name='defaultValue' maxlength='' value=''>" +
							 "	</td>";			
			columnDefault.html("").append(htmlDefaultVal);
			$(columnDefault).find("input[name=defaultValue]").attr("maxlength", maxlength);
			htmlVal = htmlVal 
			  +		"<td colspan='2'>"
			  +			"<input type='text' name='minVal' class='form-control qp-input-from-text pull-left' maxlength=''/> "
			  +			"<div class='qp-separator-from-to' id='separator'>~</div>"
			  +			"<input type='text' name='maxVal' class='form-control qp-input-to-text pull-right' maxlength=''/> "
			  +		"</td>";
			valueAdvanceSetting.html("").append(htmlVal);
			$(valueAdvanceSetting).find("input[name=minVal]").attr("maxlength", maxlength);
			$(valueAdvanceSetting).find("input[name=maxVal]").attr("maxlength", maxlength);
			$("#fmtCode").text("");
			
			valueAdvanceSetting.find("select[name=operatorCode] option").each(function() {
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
			valueAdvanceSetting.find("select[name=operatorCode] option").each(function() {
				var value = $(this).val(); 
				if(value == "7"){
					$(this).remove();
				}
			});
			break;
			
		case DATATYPE.DATE:
			$("#modalDialog").css({"width":"55%"});
			$("#panelBody").css({"height":"400px"});
			$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='dataSource']").attr("disabled",true);
			$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='range']").attr("disabled",false);
			htmlDefaultVal = "<th>"+dbMsgSource['sc.tabledesign.0042']+"</th>" +
							 "<td>" +
							 "<div class='input-group date qp-input-datepicker'>" +
								" <input type='text' class='form-control' id='defaultTime' name='defaultValue'/> " +
								" <span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span> " +
								" </div> " +
							 "</td><td colspan='2'><label for='defaultValue'><input type='checkbox' class='qp-input-checkbox qp-input-checkbox-margin' name='defaultValue' id='defaultValue' onclick='setCurrentTime(this)'><span id='span_click_advance'>now()</span></label></td>";
			columnDefault.html("").append(htmlDefaultVal);
			htmlVal = htmlVal 
			  +		 "<td colspan='2'>"
			  +			"<div class='input-group date qp-input-from-datepicker pull-left'>" 
			  +				" <input type='text' name='minVal' class='form-control' /> " 
			  + 			" <span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span> " 
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
			$('#span_click_advance').on('click', function(e){
				setCurrentTime($(this).prev());
			});
			valueAdvanceSetting.find("select[name=operatorCode] option").each(function() {
				var value = $(this).val(); 
				if(value == "7"){
					$(this).remove();
				}
			});
			break;
			
		case DATATYPE.TIME:
			$("#modalDialog").css({"width":"55%"});
			$("#panelBody").css({"height":"400px"});
			$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='dataSource']").attr("disabled",true);
			$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='range']").attr("disabled",false);
			htmlDefaultVal = "<th>"+dbMsgSource['sc.tabledesign.0042']+"</th>" +
							 "<td>" +
							 "<div class='input-group date qp-input-timepicker'>" +
								" <input type='text' class='form-control' id='defaultTime' name='defaultValue'/> " +
								" <span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span> " +
								" </div> " + 
								"</td><td colspan='2'><label for='defaultValue'><input type='checkbox' class='qp-input-checkbox qp-input-checkbox-margin' name='defaultValue' id='defaultValue' onclick='setCurrentTime(this)'><span id='span_click_advance'>now()</span></label></td>";
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
			$('#span_click_advance').on('click', function(e){
				setCurrentTime($(this).prev());
			});
			valueAdvanceSetting.find("select[name=operatorCode] option").each(function() {
				var value = $(this).val(); 
				if(value == "7"){
					$(this).remove();
				}
			});
			break;
			
		case DATATYPE.DATETIME:
			$("#modalDialog").css({"width":"55%"});
			$("#panelBody").css({"height":"400px"});
			$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='dataSource']").attr("disabled",true);
			$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='range']").attr("disabled",false);
			htmlDefaultVal = "<th>"+dbMsgSource['sc.tabledesign.0042']+"</th>" +
							 "<td>" +
							 "<div class='input-group date qp-input-datetimepicker'>" +
								" <input type='text' class='form-control' id='defaultTime' name='defaultValue'/> " +
								" <span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span> " +
								" </div> " +
								"</td><td colspan='2'><label for='defaultValue'><input type='checkbox' class='qp-input-checkbox qp-input-checkbox-margin' name='defaultValue' id='defaultValue' onclick='setCurrentTime(this)'><span id='span_click_advance'>now()</span></label></td>";
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
			$('#span_click_advance').on('click', function(e){
				setCurrentTime($(this).prev());
			});
			valueAdvanceSetting.find("select[name=operatorCode] option").each(function() {
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
				$.qp.formatFloat($('.qp-input-from-float'));
				$.qp.formatFloat($('.qp-input-to-float'));
				$.qp.formatFloat($('.qp-input-float'));
				valueAdvanceSetting.find("select[name=operatorCode] option").each(function() {
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
			  +			"<input type='text' name='minVal' class='form-control qp-input-from-currency pull-left' qp-min='"+initializeMinlength(maxlength, precision)+"' qp-max='"+initializeMaxlength(maxlength, precision)+"' /> "
			  +			"<div class='qp-separator-from-to' id='separator'>~</div>"
			  +			"<input type='text' name='maxVal' class='form-control qp-input-to-currency pull-right' qp-min='"+initializeMinlength(maxlength, precision)+"' qp-max='"+initializeMaxlength(maxlength, precision)+"' /> "
			  +		"</td>"
			valueAdvanceSetting.html("").append(htmlVal);
			$.qp.formatCurrency($('.qp-input-from-currency'));
			$.qp.formatCurrency($('.qp-input-to-currency'));
			$.qp.formatCurrency($('.qp-input-currency'));
			valueAdvanceSetting.find("select[name=operatorCode] option").each(function() {
				var value = $(this).val(); 
				if(value == "7"){
					$(this).remove();
				}
			});
			break;
			
		case DATATYPE.BOOLEAN:
			$("#modalDialog").css({"width":"55%"});
			$("#panelBody").css({"height":"auto"});
			$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='dataSource']").attr("disabled",true);
			$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='range']").attr("disabled",true);
			var defaultVal = $(obj).closest('td').find("input[name$='.defaultValue']").val();
			var radioDefaultValue = '';
			for (var i = 0; i < booleanDefaultValue.length; i++) {
				var objDefaultValue = booleanDefaultValue[i];
				if (objDefaultValue.value == '' && defaultVal == '') {
					radioDefaultValue += '<label><input name="radDefaultValue" class="qp-input-radio qp-input-radio-margin" type="radio" value="' + objDefaultValue.value + '" checked="checked">' + objDefaultValue.label + '</label>';
				} else if (defaultVal == objDefaultValue.value) {
					radioDefaultValue += '<label><input name="radDefaultValue" class="qp-input-radio qp-input-radio-margin" type="radio" value="' + objDefaultValue.value + '" checked="checked">' + objDefaultValue.label + '</label>';
				} else {
					radioDefaultValue += '<label><input name="radDefaultValue" class="qp-input-radio qp-input-radio-margin" type="radio" value="' + objDefaultValue.value + '">' + objDefaultValue.label + '</label>';
				}
			}
			htmlDefaultVal = "<th>"+dbMsgSource['sc.tabledesign.0042']+"</th>" +
							 "<td colspan='3'>" + radioDefaultValue + "</td>";
			columnDefault.html(htmlDefaultVal);
			htmlVal = "<th>Value</th>" 
				+ 	"<td colspan='3'></td>";
			valueAdvanceSetting.html("").append(htmlVal);
			if($thisTR.find("input[name$='.defaultValue']").val() == "1"){
				$("#panelBody").find("input[name=defaultValue]").prop("checked", true);
			}
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
	var curentTR = $('tr', tmp_list_table).eq(parseInt($("#currentRow").val()) +1 );
	var dialogAdvanceSetting = $("#dialogAdvanceSetting");
	
	if(parseInt(curentTR.find("select[name$='.dataType'] option:selected").attr("datatypeflg")) == 0){
		var groupBaseTypeId = curentTR.find("select[name$='.dataType'] option:selected").attr("basetypegroup");
		
		$.qp.undoFormatNumericForm($("#dialogAdvanceSetting"));
		var constrainsSelected = $('input:radio[name=constrains]:checked').val();
		curentTR.find("input[name$='.fmtCode']").val(curentTR.find("select[name$='.dataType'] option:selected").attr("validationrule"));
		curentTR.find("input[name$='.groupBaseTypeId']").val(groupBaseTypeId);
		
		if(constrainsSelected == "any"){
			curentTR.find("input[name$='.constrainsType']").val(0);
			curentTR.find("input[name$='.userDefineValue']").val("");
			curentTR.find("input[name$='.defaultType']").val($("#defaultType").val());
			curentTR.find("input[name$='.optionLabel']").val("");
			curentTR.find("input[name$='.optionValue']").val("");
			curentTR.find("input[name$='.optionLabelAutocomplete']").val("");
			curentTR.find("input[name$='.optionValueAutocomplete']").val("");
			if($("#setTimeNowStatus").val() == 1){
				curentTR.find("input[name$='.defaultValue']").val("now()");
			}else{
				curentTR.find("input[name$='.defaultValue']").val($("input[name=defaultValue]").val());
			}
			switch (parseInt(groupBaseTypeId)) {
				case DATATYPE.BOOLEAN:
					curentTR.find("input[name$='.defaultValue']").val($("#defaultValueAdvanceSetting").find($('[name="radDefaultValue"][type="radio"]:checked')).val());
					break;
				default:
					curentTR.find("input[name$='.defaultValue']").val($("#tableDialogAdvanceSetting").find("#defaultValueAdvanceSetting").find("input[name=defaultValue]").val());
					break;
			}
			dialogAdvanceSetting.find("#operatorCodeAdvance").val(9);
			curentTR.find("input[name$='.datasourceType']").val("");
			curentTR.find("input[name$='.datasourceId']").val("");
			
		}else if(constrainsSelected == "range"){
			var operatorCode = $("#tableDialogAdvanceSetting").find("select[name=operatorCode] option:selected").val();
//			dialogAdvanceSetting.find("#operatorCodeAdvance").val(0);
			curentTR.find("input[name$='.optionLabel']").val("");
			curentTR.find("input[name$='.optionValue']").val("");
			curentTR.find("input[name$='.optionLabelAutocomplete']").val("");
			curentTR.find("input[name$='.optionValueAutocomplete']").val("");
			switch (parseInt(groupBaseTypeId)) {
				case DATATYPE.INTEGER:
				case DATATYPE.CURRENCY:
				case DATATYPE.DECIMAL:
					var maxVal = parseFloat($("#tableDialogAdvanceSetting").find("#valueAdvanceSetting").find("input[name=maxVal]").val());
					var minVal = parseFloat($("#tableDialogAdvanceSetting").find("#valueAdvanceSetting").find("input[name=minVal]").val());
					if($("#tableDialogAdvanceSetting").find("#defaultValueAdvanceSetting").find("input[name=defaultValue]").val() != ""){
						var defaultVal = parseFloat($("#tableDialogAdvanceSetting").find("#defaultValueAdvanceSetting").find("input[name=defaultValue]").val());
					}else{
						var defaultVal = undefined;
					}
					
					if(checkRange(operatorCode, maxVal, minVal, defaultVal, $("#tableDialogAdvanceSetting"))){
						return;
					}
					break;
				case DATATYPE.DATE:
					var maxVal = convertDate($("#tableDialogAdvanceSetting").find("#valueAdvanceSetting").find("input[name=maxVal]").val(), "DATE") == undefined ? undefined : convertDate($("#tableDialogAdvanceSetting").find("#valueAdvanceSetting").find("input[name=maxVal]").val(), "DATE").getTime();
					var minVal = convertDate($("#tableDialogAdvanceSetting").find("#valueAdvanceSetting").find("input[name=minVal]").val(), "DATE") == undefined ? undefined : convertDate($("#tableDialogAdvanceSetting").find("#valueAdvanceSetting").find("input[name=minVal]").val(), "DATE").getTime();
					if($("#tableDialogAdvanceSetting").find("input[name=defaultValue]").val() != ""){
						var defaultVal = convertDate($("#tableDialogAdvanceSetting").find("#defaultValueAdvanceSetting").find("input[name=defaultValue]").val(), "DATE") == undefined ? undefined : convertDate($("#tableDialogAdvanceSetting").find("#defaultValueAdvanceSetting").find("input[name=defaultValue]").val(), "DATE").getTime();
					}else{
						var defaultVal = undefined;
					}
					/*if($("#tableDialogAdvanceSetting").find("#defaultValueAdvanceSetting").find("input[id=defaultValue]").prop("checked") == false){*/
						if(checkRange(operatorCode, maxVal, minVal, defaultVal, $("#tableDialogAdvanceSetting"))){
							return;
						}	
					/*}*/
					$("#timeValueTemp").val($("#valueAdvanceSetting").html());
					break;
				case DATATYPE.DATETIME:
					var maxVal = convertDate($("#tableDialogAdvanceSetting").find("#valueAdvanceSetting").find("input[name=maxVal]").val(), "DATETIME") == undefined ? undefined : convertDate($("#tableDialogAdvanceSetting").find("#valueAdvanceSetting").find("input[name=maxVal]").val(), "DATETIME").getTime();
					var minVal = convertDate($("#tableDialogAdvanceSetting").find("#valueAdvanceSetting").find("input[name=minVal]").val(), "DATETIME") == undefined ? undefined : convertDate($("#tableDialogAdvanceSetting").find("#valueAdvanceSetting").find("input[name=minVal]").val(), "DATETIME").getTime();
					if($("#tableDialogAdvanceSetting").find("input[name=defaultValue]").val() != ""){
						var defaultVal = convertDate($("#tableDialogAdvanceSetting").find("#defaultValueAdvanceSetting").find("input[name=defaultValue]").val(), "DATETIME") == undefined ? undefined : convertDate($("#tableDialogAdvanceSetting").find("#defaultValueAdvanceSetting").find("input[name=defaultValue]").val(), "DATETIME").getTime();
					}else{
						var defaultVal = undefined;
					}
					/*if($("#tableDialogAdvanceSetting").find("#defaultValueAdvanceSetting").find("input[id=defaultValue]").prop("checked") == false){*/
						if(checkRange(operatorCode, maxVal, minVal, defaultVal, $("#tableDialogAdvanceSetting"))){
							return;
						}	
					/*}*/
					$("#timeValueTemp").val($("#valueAdvanceSetting").html());
					break;
				case DATATYPE.TIME:
					var maxVal = convertDate($("#tableDialogAdvanceSetting").find("#valueAdvanceSetting").find("input[name=maxVal]").val(), "TIME") == undefined ? undefined : convertDate($("#tableDialogAdvanceSetting").find("#valueAdvanceSetting").find("input[name=maxVal]").val(), "TIME").getTime();
					var minVal = convertDate($("#tableDialogAdvanceSetting").find("#valueAdvanceSetting").find("input[name=minVal]").val(), "TIME") == undefined ? undefined : convertDate($("#tableDialogAdvanceSetting").find("#valueAdvanceSetting").find("input[name=minVal]").val(), "TIME").getTime();
					if($("#tableDialogAdvanceSetting").find("input[name=defaultValue]").val() != ""){
						var defaultVal = convertDate($("#tableDialogAdvanceSetting").find("#defaultValueAdvanceSetting").find("input[name=defaultValue]").val(), "TIME") == undefined ? undefined : convertDate($("#tableDialogAdvanceSetting").find("#defaultValueAdvanceSetting").find("input[name=defaultValue]").val(), "TIME").getTime();
					}else{
						var defaultVal = undefined;
					}
					/*if($("#tableDialogAdvanceSetting").find("#defaultValueAdvanceSetting").find("input[id=defaultValue]").prop("checked") == false){*/
						if(checkRange(operatorCode, maxVal, minVal, defaultVal, $("#tableDialogAdvanceSetting"))){
							return;
						}	
					/*}*/
					$("#timeValueTemp").val($("#valueAdvanceSetting").html());
					break;
				case DATATYPE.CHAR:
				case DATATYPE.TEXT:
					var maxVal = $("#tableDialogAdvanceSetting").find("#valueAdvanceSetting").find("input[name=maxVal]").val();
					var minVal = $("#tableDialogAdvanceSetting").find("#valueAdvanceSetting").find("input[name=minVal]").val();
					if($("#tableDialogAdvanceSetting").find("#defaultValueAdvanceSetting").find("input[name=defaultValue]").val() != ""){
						var defaultVal = $("#tableDialogAdvanceSetting").find("#defaultValueAdvanceSetting").find("input[name=defaultValue]").val();
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
			}
			curentTR.find("input[name$='.constrainsType']").val(1);
			if($("#setTimeNowStatus").val() == 1){
				curentTR.find("input[name$='.defaultValue']").val("now()");
			}else{
				curentTR.find("input[name$='.defaultValue']").val($("input[name=defaultValue]").val());
			}
			curentTR.find("input[name$='.minVal']").val($("input[name=minVal]").val());
			curentTR.find("input[name$='.maxVal']").val($("input[name=maxVal]").val());
			curentTR.find("input[name$='.operatorCode']").val($("select[name='operatorCode'] option:selected").val());
			curentTR.find("input[name$='.userDefineValue']").val("");
			curentTR.find("input[name$='.defaultType']").val($("#defaultType").val());
			curentTR.find("input[name$='.datasourceType']").val("");
			curentTR.find("input[name$='.datasourceId']").val("");
			
		}else if(constrainsSelected == "dataSource"){
			
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
				
				if(!isSupportOptionValue){
					//required for option name
					if(!validateRequired(dialogForm,"codelistName",dbMsgSource['sc.tabledesign.0045'],true)) {
						return;
					}
					//check duplication for option name
					if(!validateDuplication(dialogForm,"codelistName",dbMsgSource['sc.tabledesign.0045'],true)) {
						return;
					}
				}
				
				//required for option value
				if(!validateRequired(dialogForm,"codelistValue",dbMsgSource['sc.tabledesign.0046'],true)) {
					return;
				}
				
				//check duplication for option value
				if(!validateDuplication(dialogForm,"codelistValue",dbMsgSource['sc.tabledesign.0046'],true)) {
					return;
				}
				
				// check data type
				if(parseInt(groupBaseTypeId) == DATATYPE.INTEGER){
					var count = 1;
					var strAlert = "";
					$('#tbl_list_Suport').find("tbody tr").each(function() {
						var codelistValue = $(this).find("input[name=codelistValue]").val();
						if(!$.isNumeric(codelistValue)){
							strAlert = strAlert + dbMsgSource['err.tabledesign.0003'].replace("{0}",count);
							strAlert = strAlert + "\n";
						}
						count++;
					});
					
					if(strAlert.length > 0){
						$.qp.showAlertModal(strAlert);
						return false;
					}
				}
				
				var userDefineValue = "";
				
				if($("#supportOptionValue").prop("checked")){
					curentTR.find("input[name$='.supportOptionFlg']").val(0);
				}else{
					curentTR.find("input[name$='.supportOptionFlg']").val(1);
				}
				
				$('#tbl_list_Suport').find("tbody tr").each(function() {
					
					if($(this).find("input[name$=supportValue]").prop("checked")){
						userDefineValue += "1" +"�";
						curentTR.find("input[name$='.defaultValue']").val($(this).find("input[name=codelistValue]").val());
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
				curentTR.find("input[name$='.constrainsType']").val(2);
				curentTR.find("input[name$='.userDefineValue']").val(userDefineValue);
				curentTR.find("input[name$='.datasourceType']").val(0);
				curentTR.find("input[name$='.optionLabel']").val("");
				curentTR.find("input[name$='.optionValue']").val("");
				curentTR.find("input[name$='.optionLabelAutocomplete']").val("");
				curentTR.find("input[name$='.optionValueAutocomplete']").val("");
			}else if(datasourceSelected == "codeList"){
				var codelistCode = $("input[name=codelistCode]").val();
				var codelistDefault = $("input[name=codelistDefault]").val();
				if(codelistCode == ""){
					$.qp.showAlertModal(fcomMsgSource['err.sys.0025'].replace("{0}",dbMsgSource['sc.tabledesign.0047']));
					return;
				}
				clearDataAdvanceSetting(curentTR);
				curentTR.find("input[name$='.constrainsType']").val(2);
				curentTR.find("input[name$='.datasourceType']").val(1);
				curentTR.find("input[name$='.datasourceId']").val(codelistCode);
				curentTR.find("input[name$='.defaultValue']").val(codelistDefault);
				curentTR.find("input[name$='.codelistCodeAutocomplete']").val($("#codelistCodeAutocompleteId").val());
				curentTR.find("input[name$='.codelistDefaultAutocomplete']").val($("#codelistDefaultAutocompleteId").val());
				curentTR.find("input[name$='.optionLabel']").val("");
				curentTR.find("input[name$='.optionValue']").val("");
				curentTR.find("input[name$='.optionLabelAutocomplete']").val("");
				curentTR.find("input[name$='.optionValueAutocomplete']").val("");
			}else if(datasourceSelected == "sqlBuilder"){
				var datasourceId = $("input[name=sqlCode]").val();
				var sqlOutputOptionValue = $("input[name=sqlOutputOptionValue]").val();
				if(datasourceId == ""){
					$.qp.showAlertModal(fcomMsgSource['err.sys.0025'].replace("{0}",dbMsgSource['sc.tabledesign.0050']));
					return;
				}else if(sqlOutputOptionValue == "" && $("#datasourceTypeAdvance").val() == "3"){
					$.qp.showAlertModal(fcomMsgSource['err.sys.0025'].replace("{0}",dbMsgSource['sc.tabledesign.0046']));
					return;
				}else{
					clearDataAdvanceSetting(curentTR);
					curentTR.find("input[name$='.constrainsType']").val(2);
					curentTR.find("input[name$='.datasourceType']").val($("#datasourceTypeAdvance").val());
					curentTR.find("input[name$='.datasourceId']").val(datasourceId);
					curentTR.find("input[name$='.sqlCodeAutocomplete']").val($("#sqlCodeAutocompleteId").val());
					if($("#datasourceTypeAdvance").val() == "3"){
						curentTR.find("input[name$='.optionLabel']").val($("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabel]").val());
						curentTR.find("input[name$='.optionLabelAutocomplete']").val($("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").val());
						curentTR.find("input[name$='.optionValue']").val($("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValue]").val());
						curentTR.find("input[name$='.optionValueAutocomplete']").val($("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").val());
					}
				}
			}
			dialogAdvanceSetting.find("#operatorCodeAdvance").val(9);
		}
	}
	// process save autoincrement
	var keyType = curentTR.find("input[name$='.keyType']").val();
	if(keyType[keyType.length - 1] == "1"){
		if($("#autoIncrementTR").find("#autoIncrementFlagAdvance").prop("checked")){
			curentTR.find("input[name$='.autoIncrementFlag']").val(1);
		}else{
			curentTR.find("input[name$='.autoIncrementFlag']").val(0);
		}
	}
	$("#dialogAdvanceSetting").modal("hide");
}

function loadDataByValue(obj){
	clearValue();
	var groupBaseTypeId = $(obj).closest("tr").find("select[name$='.dataType'] option:selected").val();
	var datatypeflg = $(obj).closest("tr").find("select[name$='.dataType'] option:selected").attr("datatypeflg");
	
	if(parseInt(datatypeflg) != 0){
		var tableDialogAdvanceSetting = $("#dialogAdvanceSettingDomain").find("#tableDialogAdvanceSetting");
		tableDialogAdvanceSetting.find("#fmtCode").html($(obj).closest("tr").find("select[name$='.dataType'] option:selected").attr("validationrule"));
		var constrains = tableDialogAdvanceSetting.find("#constrains");
		var typeOfList = tableDialogAdvanceSetting.find("#typeOfList");
		var dialogAdvanceSettingDomain = $("#dialogAdvanceSettingDomain");
		switch (parseInt($(obj).closest("tr").find("input[name$='.constrainsType']").val())) {
			case 0:
				constrains.find(">td").html(dbMsgSource['sc.tabledesign.0035']);
				tableDialogAdvanceSetting.find("#defaultValueAdvanceSetting").find('td:eq(1)').remove();
				tableDialogAdvanceSetting.find("#defaultValueAdvanceSetting td").attr('colspan',3);
				tableDialogAdvanceSetting.find("#defaultValueAdvanceSetting").find(">td").html($(obj).closest("tr").find("input[name$='.defaultValue']").val());
				if($(obj).closest("tr").find("input[name$='.defaultType']").val() == "1"){
					tableDialogAdvanceSetting.find("#defaultValueAdvanceSetting").find(">td").html("now()");
				}
				break;
			case 1:
				
				$(dialogAdvanceSettingDomain).find("#valueAdvanceSetting").show();
				$(dialogAdvanceSettingDomain).find("#defaultValueAdvanceSetting").show();
				tableDialogAdvanceSetting.find("#defaultValueAdvanceSetting").show();
				$(dialogAdvanceSettingDomain).find("#defaultValueAdvanceSetting").css({"border-bottom-color":"red","border-bottom-style":"dashed","border-bottom-width":"2px"})
				$(dialogAdvanceSettingDomain).find("#defaultValueAdvanceSetting").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
				$(dialogAdvanceSettingDomain).find("#defaultValueAdvanceSetting").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
				$(dialogAdvanceSettingDomain).find("#valueAdvanceSetting").css({"border-top-color":"red","border-top-style":"dashed","border-top-width":"2px"})
				$(dialogAdvanceSettingDomain).find("#valueAdvanceSetting").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
				$(dialogAdvanceSettingDomain).find("#valueAdvanceSetting").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
				
				constrains.find(">td").html(dbMsgSource['sc.tabledesign.0036']);
				tableDialogAdvanceSetting.find("#defaultValueAdvanceSetting").find(">td").html($(obj).closest("tr").find("input[name$='.defaultValue']").val());
				var strValue = "";
				var operatorValue = $(obj).closest("tr").find("input[name$='.operatorCode']").val();
				if(operatorValue == "8"){
					strValue = $("select[name=operatorCode] option[value='"+operatorValue+"']").text() + " : " + $(obj).closest("tr").find("input[name$='.minVal']").val() + " ~ " + $(obj).closest("tr").find("input[name$='.maxVal']").val();
				}else if(operatorValue == "1" || operatorValue == "2" ||operatorValue == "3") {
					strValue = $("select[name=operatorCode] option[value='"+operatorValue+"']").text() + " : " + $(obj).closest("tr").find("input[name$='.maxVal']").val();
				}else if(operatorValue == "0") {
					$("#valueAdvanceSetting").hide();
					$("#defaultValueAdvanceSetting").css({"border-color":"red","border-style":"dashed","border-width":"2px"})
				}else if(operatorValue == "4" || operatorValue == "5" ||operatorValue == "6" ||operatorValue == "7"){
					strValue = $("select[name=operatorCode] option[value='"+operatorValue+"']").text() + " : " + $(obj).closest("tr").find("input[name$='.minVal']").val();
				}
				
				tableDialogAdvanceSetting.find("#valueAdvanceSetting").find('td:eq(1)').remove();
				tableDialogAdvanceSetting.find("#valueAdvanceSetting td").attr('colspan',3);
				tableDialogAdvanceSetting.find("#valueAdvanceSetting").find('td:eq(0)').html(strValue);
				break;
			case 2:
				constrains.find(">td").html(dbMsgSource['sc.tabledesign.0037']);
				$(dialogAdvanceSettingDomain).find("#typeOfList").show();
				$(dialogAdvanceSettingDomain).find("#valueAdvanceSetting").hide();
				$(dialogAdvanceSettingDomain).find("#defaultValueAdvanceSetting").hide();
				
				switch (parseInt($(obj).closest("tr").find("input[name$='.datasourceType']").val())) {
					case 0:
						typeOfList.find(">td").html(dbMsgSource['sc.tabledesign.0038']);
						$(dialogAdvanceSettingDomain).find("#supportValue").show();
						$(dialogAdvanceSettingDomain).find("#isSupport").show();
						
						var userDefineValue = $(obj).closest("tr").find("input[name$='.userDefineValue']").val().split("◘");
						
						var tableSupport = $(dialogAdvanceSettingDomain).find("#tbl_list_Suport");
						tableSupport.html("");
						var htmlTableSupport = "";
						
						if($(obj).closest("tr").find("input[name$='.supportOptionFlg']").val() == "1"){
							$(dialogAdvanceSettingDomain).find("#isSupport").hide();
							$(dialogAdvanceSettingDomain).find(">td").html(dbMsgSource['sc.tabledesign.0053'] + ": NO");
							htmlTableSupport = "<colgroup>" 
									+ 			"<col width='55px' class=\"colDefaultName\">"
									+ 			"<col width='42%' class=\"colOptionName\">"
									+	 		"<col width='42%'>"
									+ 		"</colgroup>"
									+ 		"<thead>"
									+ 			"<tr>"
									+	 			"<th>"+dbMsgSource['sc.tabledesign.0042']+"</a></th>"
									+ 				"<th class=\"colOptionName\">"+dbMsgSource['sc.tabledesign.0045']+"<span class='qp-required-field'> (*) </span></th>"
									+ 				"<th>"+dbMsgSource['sc.tabledesign.0046']+"<span class='qp-required-field'> (*) </span></th>"
									+ 			"</tr>"
									+ 		"</thead>"
									+ 		"<tbody class='ui-sortable'>";
							
							for (var int = 0; int < userDefineValue.length -1; int++) {
								var userDefineValueRow = userDefineValue[int].split("�");
								if (userDefineValueRow[0] == "1") {
									htmlTableSupport = htmlTableSupport + "<tr>"
										+ "<td>YES</td>"
								} else {
									htmlTableSupport = htmlTableSupport + "<td></td>"
								}
								
								alert(userDefineValueRow[2]);
								
								htmlTableSupport = htmlTableSupport 
									+       	 "<td class=\"td-word-wrap colOptionName\"><label style=\"text-align: left;\">"+ $.qp.escapseHTML(userDefineValueRow[2]) +"</label></td>"
									+       	 "<td class=\"td-word-wrap colOptionValue\"><label style=\"text-align: left;\">"+ $.qp.escapseHTML(userDefineValueRow[1]) +"</label></td>"
									+ 			"</tr>";
							}
							htmlTableSupport = htmlTableSupport 
									+ 		"</tbody>";
									
							tableSupport.append(htmlTableSupport);
						}else{
							$(dialogAdvanceSettingDomain).find("#isSupport").find('td:eq(0)').html(dbMsgSource['sc.tabledesign.0053']);
							$(dialogAdvanceSettingDomain).find("#isSupport").find(">td").html(dbMsgSource['sc.tabledesign.0053'] + ": YES");
							htmlTableSupport = "<colgroup>" 
								+ 			"<col width='85px'>"
								+ 			"<col width='50%'class=\"colOptionName\">"
								+	 		"<col width='50%'>"
								+ 		"</colgroup>"
								+ 		"<thead>"
								+ 			"<tr>"
								+	 			"<th>"+dbMsgSource['sc.tabledesign.0042']+"</a></th>"
								+ 				"<th class=\"colOptionName\">"+dbMsgSource['sc.tabledesign.0045']+"<span class='qp-required-field'> (*) </span></th>"
								+ 				"<th>"+dbMsgSource['sc.tabledesign.0046']+"<span class='qp-required-field'> (*) </span></th>"
								+ 			"</tr>"
								+ 		"</thead>"
								+ 		"<tbody class='ui-sortable'>";
						
							for (var int = 0; int < userDefineValue.length -1; int++) {
								var userDefineValueRow = userDefineValue[int].split("�");
								if (userDefineValueRow[0] == "1") {
									htmlTableSupport = htmlTableSupport + "<tr>"
										+ "<td>YES</td>"
								} else {
									htmlTableSupport = htmlTableSupport + "<td></td>"
								}
								
								htmlTableSupport = htmlTableSupport 
								+       	 "<td class=\"td-word-wrap colOptionName\"><label style=\"text-align: left;\">"+ $.qp.escapseHTML(userDefineValueRow[2]) +"</label></td>"
								+       	 "<td class=\"td-word-wrap colOptionValue\"><label style=\"text-align: left;\">"+ $.qp.escapseHTML(userDefineValueRow[1]) +"</label></td>"
									+ 			"</tr>";
							}
							htmlTableSupport = htmlTableSupport 
									+ 		"</tbody>";
									
							tableSupport.append(htmlTableSupport);
							$(dialogAdvanceSettingDomain).find('#tbl_list_Suport').find(".colOptionName").hide();
						}
						
						$(dialogAdvanceSettingDomain).find("#supportValue").css({"border-bottom-color":"red","border-bottom-style":"dashed","border-bottom-width":"2px"})
						$(dialogAdvanceSettingDomain).find("#supportValue").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
						$(dialogAdvanceSettingDomain).find("#supportValue").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
						$(dialogAdvanceSettingDomain).find("#supportValue").find(".qp-add-left").hide();
						$(dialogAdvanceSettingDomain).find("#typeOfList").css({"border-top-color":"red","border-top-style":"dashed","border-top-width":"2px"})
						$(dialogAdvanceSettingDomain).find("#typeOfList").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
						$(dialogAdvanceSettingDomain).find("#typeOfList").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
						$(dialogAdvanceSettingDomain).find("#isSupport").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
						$(dialogAdvanceSettingDomain).find("#isSupport").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
						
					break;
					case 1:
						typeOfList.find(">td").html(dbMsgSource['sc.tabledesign.0039']);
						$(dialogAdvanceSettingDomain).find("#codelistCode").find('td:eq(0)').html($(obj).closest("tr").find("input[name$='.codelistCodeAutocomplete']").val());
						$(dialogAdvanceSettingDomain).find("#default").find('td:eq(0)').html($(obj).closest("tr").find("input[name$='.codelistDefaultAutocomplete']").val());
						$(dialogAdvanceSettingDomain).find("#codelistCode").show();
						$(dialogAdvanceSettingDomain).find("#default").show();
						$(dialogAdvanceSettingDomain).find("#notSupportValue").hide();
						$(dialogAdvanceSettingDomain).find("#sqlCode").hide();
						$(dialogAdvanceSettingDomain).find("#supportValue").hide();
						$(dialogAdvanceSettingDomain).find("#isSupport").hide();
						
						$(dialogAdvanceSettingDomain).find('input[name=codelistCodeAutocomplete]').val($(obj).closest("tr").find("input[name$='.codelistCodeAutocomplete']").val());
						$(dialogAdvanceSettingDomain).find("input[name=codelistCode]").val($(obj).closest("tr").find("input[name$='.datasourceId']").val());
						
						$(dialogAdvanceSettingDomain).find('input[name=codelistDefaultAutocomplete]').val($(obj).closest("tr").find("input[name$='.codelistDefaultAutocomplete']").val());
						$(dialogAdvanceSettingDomain).find('input[name=codelistDefaultAutocomplete]').attr("arg02", $(obj).closest("tr").find("input[name$='.datasourceId']").val());
						$(dialogAdvanceSettingDomain).find("input[name=codelistDefault]").val($(obj).closest("tr").find("input[name$='.defaultValue']").val());
						
						var hrefVal = $(dialogAdvanceSettingDomain).find("#codelistView").attr("href").split("=")[0] + "=" + $(obj).closest("tr").find("input[name$='.datasourceId']").val() + "&openOwner=0";
						$(dialogAdvanceSettingDomain).find("#codelistView").attr("href", hrefVal);
						if(parseInt(datatypeflg) != 0){
							$("#codelistRegis").hide();
							$("#sqlView").hide();
						}
						$(dialogAdvanceSettingDomain).find("#typeOfList").css({"border-top-color":"red","border-top-style":"dashed","border-top-width":"2px"})
						$(dialogAdvanceSettingDomain).find("#typeOfList").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
						$(dialogAdvanceSettingDomain).find("#typeOfList").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
						$(dialogAdvanceSettingDomain).find("#codelistCode").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
						$(dialogAdvanceSettingDomain).find("#codelistCode").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
						$(dialogAdvanceSettingDomain).find("#default").css({"border-bottom-color":"red","border-bottom-style":"dashed","border-bottom-width":"2px"})
						$(dialogAdvanceSettingDomain).find("#default").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
						$(dialogAdvanceSettingDomain).find("#default").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
						
					break;
					case 2:
					case 3:
						typeOfList.find(">td").html(dbMsgSource['sc.tabledesign.0040']);
						$(dialogAdvanceSettingDomain).find("#sqlCode").find('td:eq(0)').html($(obj).closest("tr").find("input[name$='.sqlCodeAutocomplete']").val());
						$(dialogAdvanceSettingDomain).find("#sqlCode").find('td:eq(2)').html("");
						
						$(dialogAdvanceSettingDomain).find("#datasourceTypeAdvance").val($(obj).closest("tr").find("input[name$='.datasourceType']").val());
						$(dialogAdvanceSettingDomain).find('input[name=sqlCodeAutocomplete]').val($(obj).closest("tr").find("input[name$='.sqlCodeAutocomplete']").val());
						$(dialogAdvanceSettingDomain).find("input[name=sqlCode]").val($(obj).closest("tr").find("input[name$='.datasourceId']").val());
						var hrefVal = $(dialogAdvanceSettingDomain).find("#sqlView").attr("href").split("/")[0] + "/" + $("#sqlView").attr("href").split("/")[1];
						switch (parseInt($(obj).closest("tr").find("input[name$='.datasourceType']").val())) {
							case 2:
								hrefVal = hrefVal + "/autocomplete/view?autocompleteForm.autocompleteId=";
								break;
							case 3:
								hrefVal = hrefVal + "/sqldesign/view?sqlDesignForm.sqlDesignId=";
								break;
						}
						hrefVal = hrefVal + $(obj).closest("tr").find("input[name$='.datasourceId']").val() + "&openOwner=0";
						$(dialogAdvanceSettingDomain).find("#sqlView").attr("href", hrefVal);
						$(dialogAdvanceSettingDomain).find("#sqlRegis").show();
						$(dialogAdvanceSettingDomain).find("#sqlCode").show();
						$(dialogAdvanceSettingDomain).find("#codelistCode").hide();
						$(dialogAdvanceSettingDomain).find("#default").hide();
						$(dialogAdvanceSettingDomain).find("#supportValue").hide();
						$(dialogAdvanceSettingDomain).find("#isSupport").hide();
						$(dialogAdvanceSettingDomain).find("#notSupportValue").hide();
						
						$(dialogAdvanceSettingDomain).find("#sqlCode").css({"border-bottom-color":"red","border-bottom-style":"dashed","border-bottom-width":"2px"})
						$(dialogAdvanceSettingDomain).find("#sqlCode").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
						$(dialogAdvanceSettingDomain).find("#sqlCode").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
						$(dialogAdvanceSettingDomain).find("#typeOfList").css({"border-top-color":"red","border-top-style":"dashed","border-top-width":"2px"})
						$(dialogAdvanceSettingDomain).find("#typeOfList").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
						$(dialogAdvanceSettingDomain).find("#typeOfList").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
						
					break;
				}
			break;
				default:
				constrains.find(">td").html(dbMsgSource['sc.tabledesign.0035']);
				tableDialogAdvanceSetting.find("#defaultValueAdvanceSetting").find('td:eq(1)').remove();
				tableDialogAdvanceSetting.find("#defaultValueAdvanceSetting td").attr('colspan',3);
				tableDialogAdvanceSetting.find("#defaultValueAdvanceSetting").find(">td").html($(obj).closest("tr").find("input[name$='.defaultValue']").val());
				if($(obj).closest("tr").find("input[name$='.defaultType']").val() == "1"){
					tableDialogAdvanceSetting.find("#defaultValueAdvanceSetting").find(">td").html("now()");
				}
			break;
		}
		setSelectConstrains($(obj).closest("tr").find("select[name$='.dataType'] option:selected"));
		
		// process for autoIncrement
		var keyType = $(obj).closest("tr").find("input[name$='.keyType']").val();
		if(keyType[keyType.length - 1] == "1"){
			$(dialogAdvanceSettingDomain).find("#autoIncrementTR").show();
			$(dialogAdvanceSettingDomain).find('#autoIncrementTR').find("#autoIncrementFlagAdvance").prop("disabled", false);
			$(dialogAdvanceSettingDomain).find("#saveAdvanceSetting").prop("disabled", false);
			if($(obj).closest("tr").find("input[name$='.autoIncrementFlag']").val() == "1"){
				$(dialogAdvanceSettingDomain).find("#autoIncrementTR").find("#autoIncrementFlagAdvance").prop("checked", true);
			}else{
				$(dialogAdvanceSettingDomain).find("#autoIncrementTR").find("#autoIncrementFlagAdvance").prop("checked", false);
			}
		}else{
			$(dialogAdvanceSettingDomain).find("#autoIncrementTR").hide();
		}
	
	// Prmative Data
	}else{
		var dialogAdvanceSetting = $("#dialogAdvanceSetting");
		switch (parseInt($(obj).closest("tr").find("input[name$='.constrainsType']").val())) {
			case 0:
				if($(obj).closest("tr").find("input[name$='.defaultType']").val() == "1"){
					dialogAdvanceSetting.find("#defaultTime").prop("disabled", true);
					dialogAdvanceSetting.find("#input[name=defaultValue]").prop("checked", true);
					dialogAdvanceSetting.find("#defaultTime").val("");
					dialogAdvanceSetting.find("#setTimeNowStatus").val(1);
					dialogAdvanceSetting.find("#defaultType").val(1);
				}else{
					var defaultValue = dialogAdvanceSetting.find("input[name=defaultValue]");
//					$(defaultValue).attr("class","form-control qp-input-currency");
//					$(defaultValue).attr("qp-min","0");
//					$(defaultValue).attr("qp-max","999.99");
//					$.qp.formatCurrency($(defaultValue));
					$(defaultValue).val($(obj).closest("tr").find("input[name$='.defaultValue']").val());
					dialogAdvanceSetting.find("#setTimeNowStatus").val(0);
					dialogAdvanceSetting.find("#defaultType").val(0);
				}
				if($("#setTimeNowStatus").val() == 1){
					dialogAdvanceSetting.find("#defaultTime").attr("disabled", "disabled");
					dialogAdvanceSetting.find("input[name=defaultValue]").prop("checked", true);
				}else{
					dialogAdvanceSetting.find("input[name=defaultValue]").val($(obj).closest("tr").find("input[name$='.defaultValue']").val());
				}
				break;
			case 1:
				dialogAdvanceSetting.find('input:radio[name=constrains]').filter('[value=range]').prop('checked', true);
				dialogAdvanceSetting.find("#valueAdvanceSetting").show();
				dialogAdvanceSetting.find("#defaultValueAdvanceSetting").show();
				dialogAdvanceSetting.find("#defaultValueAdvanceSetting").css({"border-bottom-color":"red","border-bottom-style":"dashed","border-bottom-width":"2px"})
				dialogAdvanceSetting.find("#defaultValueAdvanceSetting").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
				dialogAdvanceSetting.find("#defaultValueAdvanceSetting").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
				dialogAdvanceSetting.find("#valueAdvanceSetting").css({"border-top-color":"red","border-top-style":"dashed","border-top-width":"2px"})
				dialogAdvanceSetting.find("#valueAdvanceSetting").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
				dialogAdvanceSetting.find("#valueAdvanceSetting").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
				
				dialogAdvanceSetting.find("#valueAdvanceSetting").find("input[name=minVal]").val($(obj).closest("tr").find("input[name$='.minVal']").val());
				dialogAdvanceSetting.find("input[name=maxVal]").val($(obj).closest("tr").find("input[name$='.maxVal']").val());
				
				if($(obj).closest("tr").find("input[name$='.defaultType']").val() == "1"){
					dialogAdvanceSetting.find("#defaultTime").prop("disabled", true);
					dialogAdvanceSetting.find("input[name=defaultValue]").prop("checked", true);
					dialogAdvanceSetting.find("#defaultTime").val("");
					dialogAdvanceSetting.find("#setTimeNowStatus").val(1);
					dialogAdvanceSetting.find("#defaultType").val(1);
				}else{
					dialogAdvanceSetting.find("#defaultTime").val($(obj).closest("tr").find("input[name$='.defaultValue']").val());
					dialogAdvanceSetting.find("#setTimeNowStatus").val(0);
					dialogAdvanceSetting.find("#defaultType").val(0);
				}
				
				if($("#setTimeNowStatus").val() == 1){
//					dialogAdvanceSetting.find("#defaultTime").attr("disabled", "disabled");
//					dialogAdvanceSetting.find('#valueAdvanceSetting').hide();
					dialogAdvanceSetting.find("input[name=defaultValue]").prop("checked", true);
					dialogAdvanceSetting.find("#defaultValueAdvanceSetting").css({"border-bottom-color":"red","border-bottom-style":"dashed","border-bottom-width":"2px"})
					dialogAdvanceSetting.find("#defaultValueAdvanceSetting").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
					dialogAdvanceSetting.find("#defaultValueAdvanceSetting").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
				}else{
					dialogAdvanceSetting.find("input[name=defaultValue]").val($(obj).closest("tr").find("input[name$='.defaultValue']").val());
					dialogAdvanceSetting.find('#valueAdvanceSetting').show();
				}
				
				dialogAdvanceSetting.find("select[name=operatorCode]").val($(obj).closest("tr").find("input[name$='.operatorCode']").val());
				var operator = dialogAdvanceSetting.find("select[name=operatorCode]");
				setStatusOperatorcode(operator,true);
				break;
			case 2:
				dialogAdvanceSetting.find('input:radio[name=constrains]').filter('[value=dataSource]').prop('checked', true);
				dialogAdvanceSetting.find("#typeOfList").show();
				dialogAdvanceSetting.find("#valueAdvanceSetting").hide();
				dialogAdvanceSetting.find("#defaultValueAdvanceSetting").hide();
				
				switch (parseInt($(obj).closest("tr").find("input[name$='.datasourceType']").val())) {
					case 0:
						dialogAdvanceSetting.find('input:radio[name=typeOfList]').filter('[value=userDefine]').prop('checked', true);
						dialogAdvanceSetting.find("#supportValue").show();
						dialogAdvanceSetting.find("#isSupport").show();
						
						var userDefineValue = $(obj).closest("tr").find("input[name$='.userDefineValue']").val().split("◘");
						
						var tableSupport = dialogAdvanceSetting.find("#tbl_list_Suport");
						tableSupport.html("");
						var htmlTableSupport = "";
						
						if($(obj).closest("tr").find("input[name$='.supportOptionFlg']").val() == "1"){
							dialogAdvanceSetting.find("#supportOptionValue").prop('checked', false);
							htmlTableSupport = "<colgroup>" 
									+ 			"<col width='55px' class=\"colDefaultName\">"
									+ 			"<col width='50%' class=\"colOptionName\">"
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
							dialogAdvanceSetting.find("#supportOptionValue").prop('checked', true);
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
							dialogAdvanceSetting.find('#tbl_list_Suport').find(".colOptionName").hide();
						}
						
						dialogAdvanceSetting.find("#supportValue").css({"border-bottom-color":"red","border-bottom-style":"dashed","border-bottom-width":"2px"})
						dialogAdvanceSetting.find("#supportValue").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
						dialogAdvanceSetting.find("#supportValue").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
						dialogAdvanceSetting.find("#typeOfList").css({"border-top-color":"red","border-top-style":"dashed","border-top-width":"2px"})
						dialogAdvanceSetting.find("#typeOfList").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
						dialogAdvanceSetting.find("#typeOfList").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
						dialogAdvanceSetting.find("#isSupport").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
						dialogAdvanceSetting.find("#isSupport").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
					break;
					case 1:
						dialogAdvanceSetting.find('input:radio[name=typeOfList]').filter('[value=codeList]').prop('checked', true);
						dialogAdvanceSetting.find("#codelistCode").show();
						dialogAdvanceSetting.find("#default").show();
						dialogAdvanceSetting.find("#notSupportValue").hide();
						dialogAdvanceSetting.find("#sqlCode").hide();
						dialogAdvanceSetting.find("#supportValue").hide();
						dialogAdvanceSetting.find("#isSupport").hide();
						
						dialogAdvanceSetting.find('input[name=codelistCodeAutocomplete]').val($(obj).closest("tr").find("input[name$='.codelistCodeAutocomplete']").val());
						dialogAdvanceSetting.find("input[name=codelistCode]").val($(obj).closest("tr").find("input[name$='.datasourceId']").val());
						
						dialogAdvanceSetting.find('input[name=codelistDefaultAutocomplete]').val($(obj).closest("tr").find("input[name$='.codelistDefaultAutocomplete']").val());
						dialogAdvanceSetting.find('input[name=codelistDefaultAutocomplete]').attr("arg02", $(obj).closest("tr").find("input[name$='.datasourceId']").val());
						dialogAdvanceSetting.find("input[name=codelistDefault]").val($(obj).closest("tr").find("input[name$='.defaultValue']").val());
						
						var hrefVal = dialogAdvanceSetting.find("#codelistView").attr("href").split("=")[0] + "=" + $(obj).closest("tr").find("input[name$='.datasourceId']").val() + "&openOwner=0";
						dialogAdvanceSetting.find("#codelistView").attr("href", hrefVal);
						if(parseInt(datatypeflg) != 0){
							dialogAdvanceSetting.find("#codelistRegis").hide();
							dialogAdvanceSetting.find("#sqlView").hide();
						}
						dialogAdvanceSetting.find("#typeOfList").css({"border-top-color":"red","border-top-style":"dashed","border-top-width":"2px"})
						dialogAdvanceSetting.find("#typeOfList").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
						dialogAdvanceSetting.find("#typeOfList").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
						dialogAdvanceSetting.find("#codelistCode").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
						dialogAdvanceSetting.find("#codelistCode").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
						dialogAdvanceSetting.find("#default").css({"border-bottom-color":"red","border-bottom-style":"dashed","border-bottom-width":"2px"})
						dialogAdvanceSetting.find("#default").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
						dialogAdvanceSetting.find("#default").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
						
						dialogAdvanceSetting.find("input[name$='Autocomplete']").each(function(){
							$(this).data("ui-autocomplete")._trigger("close");
						});
						
					break;
					case 2:
					case 3:
						dialogAdvanceSetting.find("#datasourceTypeAdvance").val($(obj).closest("tr").find("input[name$='.datasourceType']").val());
						dialogAdvanceSetting.find('input:radio[name=typeOfList]').filter('[value=sqlBuilder]').prop('checked', true);
						dialogAdvanceSetting.find('input[name=sqlCodeAutocomplete]').val($(obj).closest("tr").find("input[name$='.sqlCodeAutocomplete']").val());
						dialogAdvanceSetting.find("input[name=sqlCode]").val($(obj).closest("tr").find("input[name$='.datasourceId']").val());
						var hrefVal = dialogAdvanceSetting.find("#sqlView").attr("href").split("/")[0] + "/" + $("#sqlView").attr("href").split("/")[1];
						switch (parseInt($(obj).closest("tr").find("input[name$='.datasourceType']").val())) {
							case 2:
								hrefVal = hrefVal + "/autocomplete/view?autocompleteForm.autocompleteId=";
								break;
							case 3:
								hrefVal = hrefVal + "/sqldesign/view?sqlDesignForm.sqlDesignId=";
								break;
						}
						hrefVal = hrefVal + $(obj).closest("tr").find("input[name$='.datasourceId']").val() + "&openOwner=0";
						dialogAdvanceSetting.find("#sqlView").attr("href", hrefVal);
						dialogAdvanceSetting.find("#sqlRegis").show();
						dialogAdvanceSetting.find("#sqlCode").show();
						dialogAdvanceSetting.find("#codelistCode").hide();
						dialogAdvanceSetting.find("#default").hide();
						dialogAdvanceSetting.find("#supportValue").hide();
						dialogAdvanceSetting.find("#isSupport").hide();
						dialogAdvanceSetting.find("#notSupportValue").hide();
						
						dialogAdvanceSetting.find("#sqlCode").css({"border-bottom-color":"red","border-bottom-style":"dashed","border-bottom-width":"2px"})
						dialogAdvanceSetting.find("#sqlCode").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
						dialogAdvanceSetting.find("#sqlCode").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
						dialogAdvanceSetting.find("#typeOfList").css({"border-top-color":"red","border-top-style":"dashed","border-top-width":"2px"})
						dialogAdvanceSetting.find("#typeOfList").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
						dialogAdvanceSetting.find("#typeOfList").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
						if(parseInt($(obj).closest("tr").find("input[name$='.datasourceType']").val()) == 3){
							dialogAdvanceSetting.find("#sqlDesignOutputOptionValue").show();
							dialogAdvanceSetting.find("#sqlDesignOutputDisplayValue").show();
							dialogAdvanceSetting.find("#sqlCode").removeAttr("style");
							dialogAdvanceSetting.find("#sqlCode").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
							dialogAdvanceSetting.find("#sqlCode").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
							dialogAdvanceSetting.find("#sqlDesignOutputOptionValue").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
							dialogAdvanceSetting.find("#sqlDesignOutputOptionValue").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
							dialogAdvanceSetting.find("#sqlDesignOutputDisplayValue").css({"border-left-color":"red","border-left-style":"dashed","border-left-width":"2px"})
							dialogAdvanceSetting.find("#sqlDesignOutputDisplayValue").css({"border-right-color":"red","border-right-style":"dashed","border-right-width":"2px"})
							dialogAdvanceSetting.find("#sqlDesignOutputDisplayValue").css({"border-bottom-color":"red","border-bottom-style":"dashed","border-bottom-width":"2px"})
							
							dialogAdvanceSetting.find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").attr("arg01", $(obj).closest("tr").find("input[name$='.datasourceId']").val());
							dialogAdvanceSetting.find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").attr("arg01", $(obj).closest("tr").find("input[name$='.datasourceId']").val());
							
							dialogAdvanceSetting.find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabel]").val($(obj).closest("tr").find("input[name$='.optionLabel']").val());
							dialogAdvanceSetting.find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]").val($(obj).closest("tr").find("input[name$='.optionLabelAutocomplete']").val());
							dialogAdvanceSetting.find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValue]").val($(obj).closest("tr").find("input[name$='.optionValue']").val());
							dialogAdvanceSetting.find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]").val($(obj).closest("tr").find("input[name$='.optionValueAutocomplete']").val());
						}
						
						dialogAdvanceSetting.find("input[name$='Autocomplete']").each(function(){
							$(this).data("ui-autocomplete")._trigger("close");
						});
					break;
				}
			break;
			default:
				dialogAdvanceSetting.find("input[name=defaultValue]").val("");
			break;
		}
		setSelectConstrains($(obj).closest("tr").find("select[name$='.dataType'] option:selected"));
		
		// process for autoIncrement
		var keyType = $(obj).closest("tr").find("input[name$='.keyType']").val();
		if(keyType[keyType.length - 1] == "1"){
			var groupBaseTypeId = $(obj).closest("tr").find("select[name$='.dataType'] option:selected").attr("basetypegroup");
			if(groupBaseTypeId == DATATYPE.CURRENCY || groupBaseTypeId == DATATYPE.DECIMAL || groupBaseTypeId == DATATYPE.TEXT || groupBaseTypeId == DATATYPE.INTEGER){
				dialogAdvanceSetting.find("#autoIncrementTR").show();
				dialogAdvanceSetting.find('#autoIncrementTR').find("#autoIncrementFlagAdvance").prop("disabled", false);
				dialogAdvanceSetting.find("#saveAdvanceSetting").prop("disabled", false);
				if($(obj).closest("tr").find("input[name$='.autoIncrementFlag']").val() == "1"){
					dialogAdvanceSetting.find("#autoIncrementTR").find("#autoIncrementFlagAdvance").prop("checked", true);
				}else{
					dialogAdvanceSetting.find("#autoIncrementTR").find("#autoIncrementFlagAdvance").prop("checked", false);
				}
			}else{
				dialogAdvanceSetting.find("#autoIncrementTR").hide();
			}
		}else{
			dialogAdvanceSetting.find("#autoIncrementTR").hide();
		}
		
		
	}	
}

function clearValue(){
	$("select[name=operatorCode]").val("0");
	$("select[name=maxVal]").val("");
	$("select[name=minVal]").val("");
	$("select[name=defaultValue]").val("");
	$('input[name=codelistCodeAutocomplete]').val("");
	$("input[name=codelistCode]").val("");
	$('input[name=codelistDefaultAutocomplete]').val("");
	$("input[name=codelistDefault]").val("");
	$('input[name=sqlCodeAutocomplete]').val("");
	$("input[name=sqlCode]").val("");
}

function clearDataAdvanceSetting(currentRow){
	currentRow.find("input[name$='.constrainsType']").val("");
	currentRow.find("input[name$='.datasourceId']").val("");
	currentRow.find("input[name$='.datasourceType']").val("");
	currentRow.find("input[name$='.operatorCode']").val("");
	currentRow.find("input[name$='.userDefineValue']").val("");
	currentRow.find("input[name$='.supportOptionFlg']").val("");
	currentRow.find("input[name$='.maxVal']").val("");
	currentRow.find("input[name$='.minVal']").val("");
	currentRow.find("input[name$='.displayType']").val("");
	currentRow.find("input[name$='.defaultType']").val("");
	currentRow.find("input[name$='.defaultValue']").val("");
	currentRow.find("input[name$='.codelistDefaultAutocomplete']").val("");
	currentRow.find("input[name$='.codelistCodeAutocomplete']").val("");
	currentRow.find("input[name$='.sqlCodeAutocomplete']").val("");
}

function valueOfTableDesignDetails(currentRow){
	if(currentRow.find("select[name$='.dataType'] option:selected").attr("datatypeflg") != $("#datatypeflgTemp").val()){
		currentRow.find("input[name$='.fmtCode']").val("");
		currentRow.find("input[name$='.constrainsType']").val("");
		currentRow.find("input[name$='.datasourceId']").val("");
		currentRow.find("input[name$='.datasourceType']").val("");
		currentRow.find("input[name$='.operatorCode']").val("");
		currentRow.find("input[name$='.userDefineValue']").val("");
		currentRow.find("input[name$='.supportOptionFlg']").val("");
		currentRow.find("input[name$='.maxVal']").val("");
		currentRow.find("input[name$='.minVal']").val("");
		currentRow.find("input[name$='.defaultValue']").val("");
		currentRow.find("input[name$='.codelistDefaultAutocomplete']").val("");
		currentRow.find("input[name$='.codelistCodeAutocomplete']").val("");
		currentRow.find("input[name$='.sqlCodeAutocomplete']").val("");
	}else if(currentRow.find("select[name$='.dataType'] option:selected").attr("basetypegroup") != $("#groupBaseTypeTemp").val()){
		currentRow.find("input[name$='.fmtCode']").val("");
		currentRow.find("input[name$='.constrainsType']").val("0");
		currentRow.find("input[name$='.datasourceId']").val("");
		currentRow.find("input[name$='.datasourceType']").val("");
		currentRow.find("input[name$='.operatorCode']").val("");
		currentRow.find("input[name$='.userDefineValue']").val("");
		currentRow.find("input[name$='.supportOptionFlg']").val("");
		currentRow.find("input[name$='.maxVal']").val("");
		currentRow.find("input[name$='.minVal']").val("");
		currentRow.find("input[name$='.displayType']").val("");
		currentRow.find("input[name$='.defaultType']").val("");
		currentRow.find("input[name$='.defaultValue']").val("");
		currentRow.find("input[name$='.codelistDefaultAutocomplete']").val("");
		currentRow.find("input[name$='.codelistCodeAutocomplete']").val("");
		currentRow.find("input[name$='.sqlCodeAutocomplete']").val("");
	}
}

function setSelectConstrains(dataType){
	
	if(dataType.attr("datatypeflg") ==  "0"){
		switch(parseInt(dataType.attr("basetypegroup"))) {
			case DATATYPE.TEXT:
			case DATATYPE.INTEGER:
			case DATATYPE.CHAR:
				$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='range']").attr("disabled",false);
				$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='dataSource']").attr("disabled",false);
				break;
			case DATATYPE.DATE:
			case DATATYPE.TIME:
			case DATATYPE.DATETIME:
			case DATATYPE.DECIMAL:
			case DATATYPE.CURRENCY:
				$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='range']").attr("disabled",false);
				$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='dataSource']").attr("disabled",true);
				break;
			case DATATYPE.BOOLEAN:
			case DATATYPE.BINARY:
				$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='range']").attr("disabled",true);
				$("#tableDialogAdvanceSetting").find("tbody tr input[type=radio][value='dataSource']").attr("disabled",true);
				$("#typeOfList").hide();
				$("#codelistCode").hide();
				$("#default").hide();
				$("#defaultValueAdvanceSetting").show();
			break;
		}
	}
}

function changeCodeListAC(obj){
	var value = $(obj.target).next("input[type=hidden]").val();
	hrefVal = $("#codelistView").attr("href").split("=")[0] + "=" + value + "&openOwner=0";
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
	console.log($("#tableDialogAdvanceSetting").find("#codelistCode").find("input[name=codelistCode]").val());
}

function removeColumnValue(table,direction,row){
	if($("#supportOptionValue").prop("checked")){
		$('#tbl_list_Suport').find(".colOptionName").hide();
	}
}

function changeNameSQL(obj){
	$.qp.showAlertModal("OK");
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
	hrefVal = hrefVal + value + "&openOwner=0";
	
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
	//$("#datasourceTypeAdvance").val($("#sqlCode").find("input[name=sqlCode]").val());
};

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

function removeDefaultValue(obj){
	$('#tbl_list_Suport').find("tbody tr").each(function() {
		$(this).find("input[name=supportValue]").prop("checked", false);
	});
}

function saveDialogAdvanceForDomainSetting(){
	$("#dialogAdvanceSettingDomain").modal("hide");
}

function setCurrentTime(obj){
	if(!$('input:radio[name=constrains]').filter('[value=any]').prop('checked')){
		if(obj.checked){
			$("#defaultTime").val("");
			$("#defaultTime").attr("disabled", "disabled");
//			$("#defaultValueAdvanceSetting").css({"border-color":"red","border-style":"dashed","border-width":"2px"})
//			$('#valueAdvanceSetting').hide();
			$("#setTimeNowStatus").val(1);
			$("#defaultType").val(1);
		}else{
			$("#defaultTime").attr('disabled', false);
//			$('#valueAdvanceSetting').show();
//			$("#defaultValueAdvanceSetting").css({"border-top-color":"none","border-top-style":"none","border-top-width":"none"})
			$("#setTimeNowStatus").val(0);
			$("#defaultType").val(0);
		}
	}else{
		if(obj.checked){
			$("#defaultTime").val("");
			$("#defaultTime").attr("disabled", "disabled");
			$("#setTimeNowStatus").val(1);
			$("#defaultType").val(1);
		}else{
			$("#defaultTime").attr('disabled', false);
			$("#setTimeNowStatus").val(0);
			$("#defaultType").val(0);
		}
	}
}

