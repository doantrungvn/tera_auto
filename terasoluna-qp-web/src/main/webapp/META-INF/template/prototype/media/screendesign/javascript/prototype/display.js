function openDialogAutocompleteSetting(obj){
	var modal = $("#dialog-component-autocomplete-setting");
	$(modal).data("target", obj);
	var value = $(obj).closest("span").find("input[name='formElement']").val();
	if(value==undefined){
	}
	var column = convertToJson(value);
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
		$(modal).find("input[name='dialog-component-setting-element-width']").val("100");
	}
	
	if (column.widthunit != undefined) {		
		$(modal).find("[name='dialog-component-setting-element-width-unit']").val(column.widthunit);
	} else {		
		$(modal).find("[name='dialog-component-setting-element-width-unit']").val("%");
	}
	$(modal).find("select[name='baseType']").val(column.baseType);
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
	if (groupDisplayType != undefined && groupDisplayType.length > 0) {		
		$(modal).find("select[name='dialog-component-setting-group-display-type'] option[value="+groupDisplayType+"]").attr("selected", "selected");
	}else{
		$(modal).find("select[name='dialog-component-setting-group-display-type'] option[value="+1+"]").attr("selected", "selected");
	}
	
	openProperties(modal, column);
	
	$.qp.initialAllPicker($(modal).find("input[name='dialog-component-setting-element-width']"));
	setFirstTabActive($(modal));
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
			$(modal).find("#dialog-form-parameter-tbl-parameter").find("tbody").find("tr:eq("+i+")").find("input[name=parameterAttribute]").val(dataParameter[0]);
			$(modal).find("#dialog-form-parameter-tbl-parameter").find("tbody").find("tr:eq("+i+")").find("input[name=tableCode]").val(dataParameter[1]);
			$(modal).find("#dialog-form-parameter-tbl-parameter").find("tbody").find("tr:eq("+i+")").find("input[name=tableCodeAutocomplete]").val(dataParameter[2]);
			$(modal).find("#dialog-form-parameter-tbl-parameter").find("tbody").find("tr:eq("+i+")").find("input[name=columnCode]").val(dataParameter[3]);
			$(modal).find("#dialog-form-parameter-tbl-parameter").find("tbody").find("tr:eq("+i+")").find("input[name=columnCodeAutocomplete]").val(dataParameter[4]);
			$(modal).find("#dialog-form-parameter-tbl-parameter").find("tbody").find("tr:eq("+i+")").find("input[name=columnCodeAutocomplete]").attr("arg01", dataParameter[1]);
		});
	} else {
		$(modal).find("#dialog-form-parameter-tbl-parameter").find("tbody").find("tr:eq(0)").find("input[name=parameterAttribute]").val("");
		$(modal).find("#dialog-form-parameter-tbl-parameter").find("tbody").find("tr:eq(0)").find("input[name=tableCode]").val("");
		$(modal).find("#dialog-form-parameter-tbl-parameter").find("tbody").find("tr:eq(0)").find("input[name=tableCodeAutocomplete]").val("");
		$(modal).find("#dialog-form-parameter-tbl-parameter").find("tbody").find("tr:eq(0)").find("input[name=columnCode]").val("");
		$(modal).find("#dialog-form-parameter-tbl-parameter").find("tbody").find("tr:eq(0)").find("input[name=columnCodeAutocomplete]").val("");
		$(modal).find("#dialog-form-parameter-tbl-parameter").find("tbody").find("tr:eq(0)").find("input[name=columnCodeAutocomplete]").attr("arg01", "");
	}
	setFirstTabActive($(modal));
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});
}

function openDialogLinkAreaSetting(obj){
	var modal = $("#dialog-link-area-setting");
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
	
	$(modal).find("input[name=linkLabelName]").val(data.label);
	$(modal).find("input[name=linkLabelNameAutocomplete]").val(htmlDecode(data.labelText));
	$(modal).find("input[name=isBundle]").prop("checked",data.isBundle);
	$(modal).find("input[name='navigateTo']").val(data.navigateTo);
	$(modal).find("input[name='navigateToAutocomplete']").val(htmlDecode(data.navigateToText));
	$(modal).find("input[name='actionName']").val(data.actionName);
	$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:gt(0)").remove();
	var parameters = data.parameters;
	if(parameters != undefined && parameters.length > 0){
		var arrParameters = parameters.split("�");
		$(arrParameters).each(function(i){
			var dataParameter = arrParameters[i].split("π");
			if(i >= 1){
				$.qp.addRowJSByLink($(modal).find("#tbl-hiddenParameter").next().find("a"));
			}
			$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:eq("+i+")").find("input[name=parameterAttribute]").val(dataParameter[0]);
			$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:eq("+i+")").find("input[name=tableCode]").val(dataParameter[1]);
			$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:eq("+i+")").find("input[name=tableCodeAutocomplete]").val(dataParameter[2]);
			$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:eq("+i+")").find("input[name=columnCode]").val(dataParameter[3]);
			$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:eq("+i+")").find("input[name=columnCodeAutocomplete]").val(dataParameter[4]);
			$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:eq("+i+")").find("input[name=columnCodeAutocomplete]").attr("arg01", dataParameter[1]);
		});
	} else {
		$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:eq(0)").find("input[name=parameterAttribute]").val("");
		$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:eq(0)").find("input[name=tableCode]").val("");
		$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:eq(0)").find("input[name=tableCodeAutocomplete]").val("");
		$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:eq(0)").find("input[name=columnCode]").val("");
		$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:eq(0)").find("input[name=columnCodeAutocomplete]").val("");
		$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:eq(0)").find("input[name=columnCodeAutocomplete]").attr("arg01", "");
	}
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
	
	$(modal).find("input[name=linkLabelName]").val(data.label);
	$(modal).find("input[name=linkLabelNameAutocomplete]").val(htmlDecode(data.labelText));
	$(modal).find("input[name=isBundle]").prop("checked",data.isBundle);
	$(modal).find("input[name='navigateTo']").val(data.navigateTo);
	$(modal).find("input[name='navigateToAutocomplete']").val(htmlDecode(data.navigateToText));
	$(modal).find("input[name='actionName']").val(data.actionName);
	$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:gt(0)").remove();
	var parameters = data.parameters;
	if(parameters != undefined && parameters.length > 0){
		var arrParameters = parameters.split("�");
		$(arrParameters).each(function(i){
			var dataParameter = arrParameters[i].split("π");
			if(i >= 1){
				$.qp.addRowJSByLink($(modal).find("#tbl-hiddenParameter").next().find("a"));
			}
			$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:eq("+i+")").find("input[name=parameterAttribute]").val(dataParameter[0]);
			$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:eq("+i+")").find("input[name=tableCode]").val(dataParameter[1]);
			$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:eq("+i+")").find("input[name=tableCodeAutocomplete]").val(dataParameter[2]);
			$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:eq("+i+")").find("input[name=columnCode]").val(dataParameter[3]);
			$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:eq("+i+")").find("input[name=columnCodeAutocomplete]").val(dataParameter[4]);
			$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:eq("+i+")").find("input[name=columnCodeAutocomplete]").attr("arg01", dataParameter[1]);
		});
	} else {
		$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:eq(0)").find("input[name=parameterAttribute]").val("");
		$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:eq(0)").find("input[name=tableCode]").val("");
		$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:eq(0)").find("input[name=tableCodeAutocomplete]").val("");
		$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:eq(0)").find("input[name=columnCode]").val("");
		$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:eq(0)").find("input[name=columnCodeAutocomplete]").val("");
		$(modal).find("#tbl-hiddenParameter").find("tbody").find("tr:eq(0)").find("input[name=columnCodeAutocomplete]").attr("arg01", "");
	}
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
	$(modal).find("input[name=buttonLabelName]").val(data.label);
	$(modal).find("input[name=buttonLabelNameAutocomplete]").val(htmlDecode(data.labelText));
	$(modal).find("input[name=isBundle]").prop("checked",data.isBundle);
	$(modal).find("input[name='navigateTo']").val(data.navigateTo);
	$(modal).find("input[name='navigateToAutocomplete']").val(htmlDecode(data.navigateToText));
	$(modal).find("input[name='actionName']").val(data.actionName);
	
	if(data.isSubmit != undefined){
		if(data.isSubmit == "true"){
			$(modal).find("input[name=isSubmit]").prop('checked', true);
			$(modal).find("#dialogActionParamSetting").hide();
		}
		else{
			$(modal).find("input[name=isSubmit]").prop('checked', false);
			$(modal).find("#dialogActionParamSetting").show();
		}
	}
	else{
		$(modal).find("input[name=isSubmit]").prop('checked', false);
		$(modal).find("#dialogActionParamSetting").show();
	}
	$(modal).find("#dialog-button-area-setting-tbl-parameter").find("tbody").find("tr:gt(0)").remove();
	var parameters = data.parameters;
	if(parameters != undefined && parameters.length > 0){
		var arrParameters = parameters.split("�");
		$(arrParameters).each(function(i){
			var dataParameter = arrParameters[i].split("π");
			if(i >= 1){
				$.qp.addRowJSByLink($(modal).find("#dialog-button-area-setting-tbl-parameter").next().find("a"));
			}
			$(modal).find("#dialog-button-area-setting-tbl-parameter").find("tbody").find("tr:eq("+i+")").find("input[name=parameterAttribute]").val(dataParameter[0]);
			$(modal).find("#dialog-button-area-setting-tbl-parameter").find("tbody").find("tr:eq("+i+")").find("input[name=tableCode]").val(dataParameter[1]);
			$(modal).find("#dialog-button-area-setting-tbl-parameter").find("tbody").find("tr:eq("+i+")").find("input[name=tableCodeAutocomplete]").val(dataParameter[2]);
			$(modal).find("#dialog-button-area-setting-tbl-parameter").find("tbody").find("tr:eq("+i+")").find("input[name=columnCode]").val(dataParameter[3]);
			$(modal).find("#dialog-button-area-setting-tbl-parameter").find("tbody").find("tr:eq("+i+")").find("input[name=columnCodeAutocomplete]").val(dataParameter[4]);
			$(modal).find("#dialog-button-area-setting-tbl-parameter").find("tbody").find("tr:eq("+i+")").find("input[name=columnCodeAutocomplete]").attr("arg01", dataParameter[1]);
		});
	} else {
		$(modal).find("#dialog-button-area-setting-tbl-parameter").find("tbody").find("tr:eq(0)").find("input[name=parameterAttribute]").val("");
		$(modal).find("#dialog-button-area-setting-tbl-parameter").find("tbody").find("tr:eq(0)").find("input[name=tableCode]").val("");
		$(modal).find("#dialog-button-area-setting-tbl-parameter").find("tbody").find("tr:eq(0)").find("input[name=tableCodeAutocomplete]").val("");
		$(modal).find("#dialog-button-area-setting-tbl-parameter").find("tbody").find("tr:eq(0)").find("input[name=columnCode]").val("");
		$(modal).find("#dialog-button-area-setting-tbl-parameter").find("tbody").find("tr:eq(0)").find("input[name=columnCodeAutocomplete]").val("");
		$(modal).find("#dialog-button-area-setting-tbl-parameter").find("tbody").find("tr:eq(0)").find("input[name=columnCodeAutocomplete]").attr("arg01", "");
	}
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
	setFirstTabActive($(modal));
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
				
		/*$.qp.initialAutoNumeric($("input[name=tableHeaderRow]").closest("tr"));
		$("input[name=tableHeaderRow]").val($(obj).closest("span").find("input[name=formTableRow]").val());
		
		var cols = $(obj).closest("fieldset").find("table").find("thead").find("tr:eq(0)").find("th").length;
		$(obj).closest("span").find("input[name=formElementTable]").val(cols+","+cols);*/
		
		$("#uniqueList").show();
	}
		
	$(modal).find("[name=headerLabelRow]").val($(obj).closest("span").find("[name=formHeaderLabelRow]").val());
	$(modal).find("[name=headerComponentRow]").val($(obj).closest("span").find("[name=formHeaderComponentRow]").val());
	$(modal).find("#tbl-hiddenAttibutes").find("tbody").empty();
	var parameters = $(obj).closest("span").find("[name=formElementHidden]").val();
	if(parameters != undefined && parameters.length > 0){
		var arrParameters = parameters.split("�");
		$(arrParameters).each(function(i){
			var dataParameter = arrParameters[i].split("π");
			$.qp.addRowJSByLink($(modal).find("#tbl-hiddenAttibutes").next().find("a"));
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq("+i+")").find("input[name=parameterAttribute]").val(dataParameter[0]);
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq("+i+")").find("input[name=tableCode]").val(dataParameter[1]);
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq("+i+")").find("input[name=tableCodeAutocomplete]").val(dataParameter[2]);
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq("+i+")").find("input[name=columnCode]").val(dataParameter[3]);
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq("+i+")").find("input[name=columnCodeAutocomplete]").val(dataParameter[4]);
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq("+i+")").find("input[name=columnCodeAutocomplete]").attr("arg01", dataParameter[1]);
		});
	} else {
//		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq(0)").find("input[name=parameterAttribute]").val("");
//		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq(0)").find("input[name=tableCode]").val("");
//		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq(0)").find("input[name=tableCodeAutocomplete]").val("");
//		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq(0)").find("input[name=columnCode]").val("");
//		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq(0)").find("input[name=columnCodeAutocomplete]").val("");
//		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq(0)").find("input[name=columnCodeAutocomplete]").attr("arg01", "");
	}
	$.qp.initialAllPicker($(modal).find("#trColWidthSetting"));
	
	if($(modal).find("#modal-table-list-setting-form").length >0){
		initSelectFormIndex(obj,$(modal).find("#modal-table-list-setting-form"));
	}
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
	$(modal).find("#tbl-hiddenAttibutes").find("tbody").empty();
	var parameters = $(obj).closest("span").find("[name=formElementHidden]").val();
	if(parameters != undefined && parameters.length > 0){
		var arrParameters = parameters.split("�");
		$(arrParameters).each(function(i){
			var dataParameter = arrParameters[i].split("π");
			$.qp.addRowJSByLink($(modal).find("#tbl-hiddenAttibutes").next().find("a"));
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq("+i+")").find("input[name=parameterAttribute]").val(dataParameter[0]);
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq("+i+")").find("input[name=tableCode]").val(dataParameter[1]);
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq("+i+")").find("input[name=tableCodeAutocomplete]").val(dataParameter[2]);
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq("+i+")").find("input[name=columnCode]").val(dataParameter[3]);
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq("+i+")").find("input[name=columnCodeAutocomplete]").val(dataParameter[4]);
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq("+i+")").find("input[name=columnCodeAutocomplete]").attr("arg01", dataParameter[1]);
		});
	} else {
//		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq(0)").find("input[name=parameterAttribute]").val("");
//		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq(0)").find("input[name=tableCode]").val("");
//		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq(0)").find("input[name=tableCodeAutocomplete]").val("");
//		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq(0)").find("input[name=columnCode]").val("");
//		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq(0)").find("input[name=columnCodeAutocomplete]").val("");
//		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq(0)").find("input[name=columnCodeAutocomplete]").attr("arg01", "");
	}
	$.qp.initialAllPicker($(modal).find("#trColWidthSetting"));

	if($(modal).find("#modal-table-setting-form").length>0){
		initSelectFormIndex(obj,$(modal).find("#modal-table-setting-form"));
	}
	setFirstTabActive($(modal));
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});
}

function openDialogComponentListSetting(obj) {
	var modal = $("#dialog-component-list-setting");
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
	$(modal).find("td[basetype='dialog-component-list-setting-base-types']").html(setBaseType(data));
	$(modal).find("input[name=columnname]").val(data.columnname);
	

	
	var localCodelist = data.localCodelist;
	if(localCodelist == undefined || localCodelist.length == 0){
		localCodelist = 1;
	}
	
	openProperties(modal, data);
	showDataSourceInView(modal, data);
	
	if(data.tablecolumncode != undefined){
		loadTableCodeList($(modal).find('input:radio[name="localCodelist"]'),data.tablecolumncode);
		var table = $(modal).find("#dialog-component-list-setting-tbl-table-options");
		if(table != undefined && $(table).find("tbody").find("tr").length > 0){
			$(modal).find("input:radio[name=localCodelist][value=2]").parent().show();
		}
	}
	
	if(data.baseType != undefined){
		$(modal).find("select[name=baseType]").val(data.baseType);
	}
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
		$(modal).find("select[name='dialog-component-list-setting-group-display-type'] option[value="+groupDisplayType+"]").attr("selected", "selected");
	}else{
		$(modal).find("select[name='dialog-component-list-setting-group-display-type'] option[value="+1+"]").attr("selected", "selected");
	}
	
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
	setFirstTabActive($(modal));
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});
}


function openDialogComponentSetting(obj){	
	
	var table = $(obj).closest("table");
	var modal = $("#dialog-component-setting");
	
	$(modal).find("#dialog-form-options-error").html("");	
	var value = $(obj).closest("span").find("input[name='formElement']").val();
	var column = convertToJson(value);

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
	
	var groupDisplayType = $(obj).closest("td").find("input[name='groupDisplayType']").val(); 
	if (groupDisplayType != undefined && groupDisplayType != '' && groupDisplayType.length > 0) {		
		$(modal).find("select[name='dialog-component-setting-group-display-type'] option[value="+groupDisplayType+"]").attr("selected", "selected");
	}else{
		$(modal).find("select[name='dialog-component-setting-group-display-type'] option[value="+1+"]").attr("selected", "selected");
	}
	if (column.width != undefined) {		
		$(modal).find("input[name='dialog-component-setting-element-width']").val(column.width);
	} else {		
		$(modal).find("input[name='dialog-component-setting-element-width']").val("100");
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
	openProperties(modal, column);
	//display datatype	
	if(column.datatype == 1 || column.datatype == 10 || column.datatype == 15 || column.datatype == 16 || column.datatype == 18 || column.datatype == 21){
				
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
//				"<th width=\"1%\" class=\"style-header-table\">"+fcomMsgSource['sc.sys.0004']+"</th>" + 
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
//							"<th width=\"1%\" class=\"style-header-table\">"+fcomMsgSource['sc.sys.0004']+"</th>";
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
	
	$.qp.initialAllPicker($(modal));
	$.qp.initialAutoNumeric($(modal));
	setFirstTabActive($(modal));
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});	
}

function openDialogLableSetting(obj){		
	var table = $(obj).closest("table");
	var modal = $('#dialog-label-setting');
	
	var value = $(obj).closest("th").find("input[name=formElement]").val();
	
	$(modal).data("target", obj);
	$(modal).data("data", $(obj).closest("th").find("input[name=formElement]").val());
		
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
	}
	setFirstTabActive($(modal));
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
		$(arrParameters).each(function(i){
			var dataParameter = arrParameters[i].split("π");
			$.qp.addRowJSByLink($(modal).find("#tbl-hiddenAttibutes").next().find("a"));
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq("+i+")").find("input[name=parameterAttribute]").val(dataParameter[0]);
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq("+i+")").find("input[name=tableCode]").val(dataParameter[1]);
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq("+i+")").find("input[name=tableCodeAutocomplete]").val(dataParameter[2]);
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq("+i+")").find("input[name=columnCode]").val(dataParameter[3]);
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq("+i+")").find("input[name=columnCodeAutocomplete]").val(dataParameter[4]);
			$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq("+i+")").find("input[name=columnCodeAutocomplete]").attr("arg01", dataParameter[1]);
		});
	} else {
//		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq(0)").find("input[name=parameterAttribute]").val("");
//		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq(0)").find("input[name=tableCode]").val("");
//		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq(0)").find("input[name=tableCodeAutocomplete]").val("");
//		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq(0)").find("input[name=columnCode]").val("");
//		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq(0)").find("input[name=columnCodeAutocomplete]").val("");
//		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:eq(0)").find("input[name=columnCodeAutocomplete]").attr("arg01", "");
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
function openFormSetting(obj) {
	var modal = $("#modal-form-setting");
	
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


function addBlockComponentListSetting(){
		var template_reload = 
		"<div id='id-block-reload-data${sequen}'>"+
		"<table class='table table-bordered qp-table-form' id='tbl_list_result'"+
		"	style='background-color: #FFFFFF'>"+
		"	<colgroup>"+
		"		<col width='20%' />"+
		"		<col width='30%' />"+
		"		<col width='20%' />"+
		"		<col width='30%' />"+
		"	</colgroup>"+
		"	<tr >"+
		"		<th class='com-table-th-text' data-toggle='collapse' data-target='#id-reload-data-toggle${sequen}'"+
		"			style='text-align: left; vertical-align: middle;background-color: #ebebe1;' colspan='4'><span class='glyphicon glyphicon-minus toggle_id'></span>  Reload data<span id='id-block-reload-data${sequen}' onclick='removeBlock(this);' class='glyphicon glyphicon-remove-circle pull-right'"+
		"			style='font-size: 20px'></span>"+
		"		</th>"+
		"	</tr>"+
		"	<tr id='id-reload-data-toggle${sequen}' class='collapse in'>"+
		"		<td colspan='4'>"+
		"			<table class='table table-bordered qp-table-form'>"+
		"				<colgroup>"+
		"					<col width='30%' />"+
		"					<col width='65%' />"+
		"					<col width='5%' />"+
		"				</colgroup>"+
		"				<tr>"+
		"					<th>Bussiness logic name</th>"+
		"					<td><select class='form-control qp-input-select'>"+
		"							<option value='1'>Ajax get product by id</option>"+
		"							<option value='2'>Function 1</option>"+
		"							<option value='3'>Function 2</option>"+
		"							<option value='4'>Function 3</option>"+
		"					</select>"+
		"					</td>"+
		"					<td><span class='btn btn-default btn-xs glyphicon glyphicon-list-alt qp-button-action' style='float: right;margin-right: 5px;cursor: pointer;' onclick='openSettingParam()'></span></td>"+
		"				</tr>"+
		"				<tr>"+
		"					<th>Effect area type</th>"+
		"					<td colspan='2'>"+
		"						<input id='isOptionValude1' name='t' class='qp-input-radio qp-input-radio-margin' type='radio' value='0' /><label>Single entity</label>"+
		"						<input id='isOptionValude1' name='t' class='qp-input-radio qp-input-radio-margin' type='radio' value='0' /><label>List entity</label>"+
		"						<input id='isOptionValude1' name='b' class='qp-input-radio qp-input-radio-margin' type='radio' value='0' /><label>Item</label>"+
		"						<input id='isOptionValude1' name='b' class='qp-input-radio qp-input-radio-margin' type='radio' value='0' /><label>Section</label></td>"+
		"				</tr>"+
		"				<tr>"+
		"					<th>Effect area</th>"+
		"					<td><select class='form-control qp-input-select'>"+
		"							<option value='1'>Product info</option>"+
		"					</select></td>"+
		"					<td><span class='btn btn-default btn-xs glyphicon glyphicon-list-alt qp-button-action' style='float: right;margin-right: 5px;cursor: pointer;' onclick='openAssignParam()'></span></td>"+
		"				</tr>"+
		"			</table>"+
		"		</td>"+
		"	</tr>"+
		"</table> <br/>"+
		"</div>";
		
		var template_bindingfield = "<div id='id-block-binding-field${sequen}'>"+
		"<table class='table table-bordered qp-table-form' id='tbl_list_result'"+
		"	style='background-color: #FFFFFF'>"+
		"	<colgroup>"+
		"		<col width='20%' />"+
		"		<col width='30%' />"+
		"		<col width='20%' />"+
		"		<col width='30%' />"+
		"	</colgroup>"+
		"	<tr >"+
		"		<th class='com-table-th-text' data-toggle='collapse' data-target='#id-binding-filed-toggle${sequen}'"+
		"			style='text-align: left; vertical-align: middle;background-color: #ebebe1;' colspan='4'><span class='glyphicon glyphicon-minus toggle_id'></span>  Binding field<span id='id-block-binding-field${sequen}' onclick='removeBlock(this);' class='glyphicon glyphicon-remove-circle pull-right'"+
		"			style='font-size: 20px'></span>"+
		"		</th>"+
		"	</tr>"+
		"	<tr id='id-binding-filed-toggle${sequen}' class='collapse in'>"+
		"		<td colspan='4'>"+
		"			<table class='table table-bordered qp-table-form'>"+
		"				<colgroup>"+
		"					<col width='30%' />"+
		"					<col width='65%' />"+
		"					<col width='5%' />"+
		"				</colgroup>"+
		"				<tr>"+
		"					<th>Bussiness logic name</th>"+
		"					<td><select class='form-control qp-input-select'>"+
		"							<option value='1'>Ajax get product by id</option>"+
		"							<option value='2'>Function 1</option>"+
		"							<option value='3'>Function 2</option>"+
		"							<option value='4'>Function 3</option>"+
		"					</select>"+
		"					</td>"+
		"					<td><span class='btn btn-default btn-xs glyphicon glyphicon-list-alt qp-button-action' style='float: right;margin-right: 5px;cursor: pointer;' onclick='openSettingParam()'></span></td>"+
		"				</tr>"+
		"				<tr>"+
		"					<th>Effect area type</th>"+
		"					<td colspan='2'><input id='isOptionValude1' name='t'"+
		"						class='qp-input-radio qp-input-radio-margin' type='radio'"+
		"						value='0' /><label>Single entity</label><input"+
		"						id='isOptionValude1' name='b'"+
		"						class='qp-input-radio qp-input-radio-margin' type='radio'"+
		"						value='0' /><label>Item</label> <input id='isOptionValude1'"+
		"						name='b' class='qp-input-radio qp-input-radio-margin'"+
		"						type='radio' value='0' /><label>Section</label></td>"+
		"				</tr>"+
		"				<tr>"+
		"					<th>Effect area</th>"+
		"					<td><select class='form-control qp-input-select'>"+
		"							<option value='1'>Product info</option>"+
		"					</select></td>"+
		"					<td><span class='btn btn-default btn-xs glyphicon glyphicon-list-alt qp-button-action' style='float: right;margin-right: 5px;cursor: pointer;' onclick='openAssignParam()'></span></td>"+
		"				</tr>"+
		"			</table>"+
		"		</td>"+
		"	</tr>"+
		"</table> <br/>"+
		"</div>";
		
		var template_submit = "<div id='id-block-submit-data${sequen}'>"+
		"<table class='table table-bordered qp-table-form' id='tbl_list_result'"+
		"	style='background-color: #FFFFFF'>"+
		"	<colgroup>"+
		"		<col width='20%' />"+
		"		<col width='30%' />"+
		"		<col width='20%' />"+
		"		<col width='30%' />"+
		"	</colgroup>"+
		"	<tr >"+
		"		<th class='com-table-th-text' data-toggle='collapse' data-target='#id-submit-data-toggle${sequen}'"+
		"			style='text-align: left; vertical-align: middle;background-color: #ebebe1;' colspan='4'>Submit<span id='id-block-submit-data${sequen}' onclick='removeBlock(this);' class='glyphicon glyphicon-remove-circle pull-right'"+
		"			style='font-size: 20px'></span>"+
		"		</th>"+
		"	</tr>"+
		"	<tr id='id-submit-data-toggle${sequen}' class='collapse in'>"+
		"	</tr>"+
		"</table> <br/>"+
		"</div>";
		
		var x= $("#idAction").val();
		
		if(0 == x){
			alert(dbMsgSource['sc.screendesign.0193']);
		}
		if(3 == x){
			//3:Submit
			var count = $("#adding-top").find("div[id^='id-block-submit-data']").size();
			var data = {sequen: count};
			$.template("template_submit", template_submit);
			$.tmpl( "template_submit", data).prependTo("#adding-top");
			
//			var toggle_id = "#require-constraint-toggle" + count;
//			
//			$(toggle_id).on('shown.bs.collapse', function(){
//				$(this).parent().find(".toggle_id").removeClass("glyphicon-plus").addClass("glyphicon-minus");
//				}).on('hidden.bs.collapse', function(){
//				$(this).parent().find(".toggle_id").removeClass("glyphicon-minus").addClass("glyphicon-plus");
//			});
		}
		if(2 == x){
			//2:Binding field
			var count = $("#adding-top").find("div[id^='id-block-binding-field']").size();
			var data = {sequen: count};
			$.template("template_bindingfield", template_bindingfield);
			$.tmpl( "template_bindingfield", data).prependTo("#adding-top");
			
			var toggle_id = "#id-binding-filed-toggle" + count;
			
			$(toggle_id).on('shown.bs.collapse', function(){
				$(this).parent().find(".toggle_id").removeClass("glyphicon-plus").addClass("glyphicon-minus");
				}).on('hidden.bs.collapse', function(){
				$(this).parent().find(".toggle_id").removeClass("glyphicon-minus").addClass("glyphicon-plus");
			});
		}
		if(1 == x){
			//1:Reload data
			//template_reload
			var count = $("#adding-top").find("div[id^='id-block-reload-data']").size();
			var data = {sequen: count};
			$.template("template_reload", template_reload);
			$.tmpl("template_reload", data).prependTo("#adding-top");
			
			var toggle_id = "#id-reload-data-toggle" + count;
			
			$(toggle_id).on('shown.bs.collapse', function(){
				$(this).parent().find(".toggle_id").removeClass("glyphicon-plus").addClass("glyphicon-minus");
				}).on('hidden.bs.collapse', function(){
				$(this).parent().find(".toggle_id").removeClass("glyphicon-minus").addClass("glyphicon-plus");
			});
		}

}

function addModalTableSetting(id){
	var template_require_constraint = "<div id='id-block-require-constraint${sequen}'>"+
	"	<table class='table table-bordered qp-table-form' id='tbl_list_result'"+
	"		style='background-color: #FFFFFF'>"+
	"		<colgroup>"+
	"			<col width='20%' />"+
	"			<col width='30%' />"+
	"			<col width='20%' />"+
	"			<col width='30%' />"+
	"		</colgroup>"+
	"		<tr >"+
	"			<th class='com-table-th-text'"+
	"				style='text-align: left; vertical-align: middle;background-color: #ebebe1;' colspan='4'>"+
	"				<span class='glyphicon glyphicon-minus toggle_id'></span> <a data-toggle='collapse' data-target='#require-constraint-toggle${sequen}'>Require constraint</a><span id='id-block-require-constraint${sequen}' onclick='removeBlock(this);' class='glyphicon glyphicon-remove-circle pull-right'"+
	"				style='font-size: 20px'></span>"+
	"			</th>"+
	"		</tr>"+
	"		<tr id='require-constraint-toggle${sequen}' class='collapse in'>"+
	"			<td colspan='4'>"+
	"				<table class='table table-bordered qp-table-form'>"+
	"					<colgroup>"+
	"						<col width='30%' />"+
	"						<col width='65%' />"+
	"					</colgroup>"+
	"					<tr>"+
	"						<th>If</th>"+
	"						<td>"+
	"							<table class='table table-bordered qp-table-form'>"+
	"								<colgroup>"+
	"									<col width='45%' />"+
	"									<col width='45%' />"+
	"									<col width='10%' />"+
	"								</colgroup>"+
	"								<tr>"+
	"									<td><div class='input-group ' style='width: 100%;'>"+
	"										<input type='text'"+
	"											class='form-control qp-input-autocomplete ui-autocomplete-input'"+
	"											value='product'> <span"+
	"											class='input-group-addon dropdown-toggle'"+
	"											data-dropdown='dropdown' style='cursor: pointer;'><span"+
	"											class='caret'></span></span>"+
	"									</div></td>"+
	"									<td>is require</td>"+
	"									<td>"+
	"										<a class='btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action' title='' onclick='$.qp.removeRowJS('tbl-hiddenAttibutes', this);' style='margin-top: 3px;' href='javascript:void(0)'></a>"+
	"									</td>"+
	"								</tr>"+
	"							</table>"+
	"							<div class='qp-add-left'>"+
	"								<a class='btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action' onclick='$.qp.addRowJSByLink(this);' style='margin-top: 3px;' href='javascript:void(0)'></a>"+
	"							</div>"+
	"						</td>"+
	"					</tr>"+
	"					<tr>"+
	"						<th>Then</th>"+
	"						<td>"+
	"							<table class='table table-bordered qp-table-form'>"+
	"								<colgroup>"+
	"									<col width='45%' />"+
	"									<col width='45%' />"+
	"									<col width='10%' />"+
	"								</colgroup>"+
	"								<tr>"+
	"									<td><div class='input-group ' style='width: 100%;'>"+
	"										<input type='text'"+
	"											class='form-control qp-input-autocomplete ui-autocomplete-input'"+
	"											value='unitPrice'> <span"+
	"											class='input-group-addon dropdown-toggle'"+
	"											data-dropdown='dropdown' style='cursor: pointer;'><span"+
	"											class='caret'></span></span>"+
	"									</div></td>"+
	"									<td>must be require</td>"+
	"									<td>"+
	"									<a class='btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action' title='' onclick='$.qp.removeRowJS('tbl-hiddenAttibutes', this);' style='margin-top: 3px;' href='javascript:void(0)'></a>"+
	"									</td>"+
	"								</tr>"+
	"								<tr>"+
	"									<td><div class='input-group ' style='width: 100%;'>"+
	"										<input type='text'"+
	"											class='form-control qp-input-autocomplete ui-autocomplete-input'"+
	"											value='quantity'> <span"+
	"											class='input-group-addon dropdown-toggle'"+
	"											data-dropdown='dropdown' style='cursor: pointer;'><span"+
	"											class='caret'></span></span>"+
	"									</div></td>"+
	"									<td>must be require</td>"+
	"									<td>"+
	"									<a class='btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action' title='' onclick='$.qp.removeRowJS('tbl-hiddenAttibutes', this);' style='margin-top: 3px;' href='javascript:void(0)'></a>"+
	"									</td>"+
	"								</tr>"+
	"							</table>"+
	"							<div class='qp-add-left'>"+
	"								<a class='btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action' onclick='$.qp.addRowJSByLink(this);' style='margin-top: 3px;' href='javascript:void(0)'></a>"+
	"							</div>"+
	"						</td>"+
	"					</tr>"+
	"				</table>"+
	"			</td>"+
	"		</tr>"+
	"	</table> <br/>"+
	"</div>";
	var count = $("#" + addModalTableSetting).find("div[id^='id-block-require-constraint']").size();
	var data = {sequen: count};
	$.template("template_require_constraint", template_require_constraint);
	$.tmpl( "template_require_constraint", data).prependTo("#" + addModalTableSetting);
	
	var toggle_id = "#require-constraint-toggle" + count;
	
	$(toggle_id).on('shown.bs.collapse', function(){
		$(this).parent().find(".toggle_id").removeClass("glyphicon-plus").addClass("glyphicon-minus");
		}).on('hidden.bs.collapse', function(){
		$(this).parent().find(".toggle_id").removeClass("glyphicon-minus").addClass("glyphicon-plus");
	});

}

function removeBlock(obj){
	var idRemove = "#"+ obj.id;
	if(confirm(dbMsgSource['inf.sys.0014'])){
		$(idRemove).remove();		
	}
}

function openSettingParam() {
	var modal = $("#modal-event-param-setting");
	$(modal).modal('show');
}

function openAssignParam() {
	var modal = $("#modal-event-assign-result");
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
	$(modal).data("target", obj);
	$(modal).data("isNewTab", true);
	$(modal).find("span[name=span-name]").html(dbMsgSource['sc.screendesign.0376']);
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