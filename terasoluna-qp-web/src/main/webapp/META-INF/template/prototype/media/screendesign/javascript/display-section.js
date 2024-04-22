function openDialogAutocompleteSettingSection(obj){
	var modal = $("#dialog-component-autocomplete-setting-section");
	$(modal).data("target", obj);
	var value = $(obj).closest("span").find("input[name='formElement']").val();
	if(value==undefined){
	}
	var column = convertToJson(value);
	$(modal).data("data", value);
    $(modal).find("#dialog-form-options-error").html("");
	
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
	
	$(modal).find("select[name='baseType']").val(column.baseType);
	$(modal).find("input[name='dialogOnChangeEvent']").val(column.dialogOnChangeEvent);
	$(modal).find("input[name='dialogOnSelectEvent']").val(column.dialogOnSelectEvent);
	$.qp.initialAllPicker($(modal).find("input[name='dialog-component-setting-element-width']"));
	setFirstTabActive($(modal));
	
	
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
		$(modal).find("input[name=optionlabel]").val('');
		$(modal).find("input[name=optionlabelAutocomplete]").val('');
		$(modal).find("input[name=optionvalue]").val('');
		$(modal).find("input[name=optionvalueAutocomplete]").val('');
	}
	
	addRadioChooseMappingOutput(modal, obj, column);
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});
}

function openDialogLinkAreaSettingSection(obj){
	var modal = $("#dialog-link-area-setting-section");
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
	loadParameter($(modal).find("input[name='navigateTo']"));
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
	
	$(modal).find("input[name=buttonLabelName]").val(data.label);
	$(modal).find("input[name=buttonLabelNameAutocomplete]").val(htmlDecode(data.labelText));
	$(modal).find("input[name=isBundle]").prop("checked",data.isBundle);
	
	$(modal).find("input[name='actionName']").val(data.actionName);
	
	
	if(data.isSubmit != undefined){
		if(data.isSubmit == 'true'){
			$(modal).find("input[name=isSubmit]").prop('checked', true);
			$(modal).find("#dialogActionParamSetting").hide();
			
			if (data.actiontype == 0) {
				$(modal).find("#navigateToScreen").find("input[name='navigateTo']").val(data.navigateTo);
				$(modal).find("#navigateToScreen").find("input[name='navigateToAutocomplete']").val(htmlDecode(data.navigateToText));
				
				$(modal).find("#navigateToScreen").show();
				$(modal).find("#navigateToblogic").hide();
			} else if (data.actiontype == 1) {
				$(modal).find("#navigateToblogic").find("input[name='navigateTo']").val(data.navigateTo);
				$(modal).find("#navigateToblogic").find("input[name='navigateToAutocomplete']").val(htmlDecode(data.navigateToText));
				
				$(modal).find("#navigateToScreen").hide();
				$(modal).find("#navigateToblogic").show();
			}
		}
		else{
			$(modal).find("input[name=isSubmit]").prop('checked', false);
			$(modal).find("#dialogActionParamSetting").show();
			displayParamAction(modal, data);
		}
	}
	else{
		$(modal).find("input[name=isSubmit]").prop('checked', false);
		$(modal).find("#dialogActionParamSetting").show();
		displayParamAction(modal, data);
	}
	$(modal).find("#dialog-button-area-setting-tbl-parameter").find("tbody").find("tr:gt(0)").remove();
	

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
	
	showDataSourceInView(modal, data);
	openProperties(modal, data);
	
	if(data.baseType != undefined){
		$(modal).find("select[name=baseType]").val(data.baseType);
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
	
	//init event
	if (data.events != undefined && data.events != null) {
		loadActionListComponent($(modal), data);
	}
	
	setFirstTabActive($(modal));
	$.qp.initialConventionNameCode();
	addRadioChooseMappingOutput(modal, obj, data);
	showElementToChange($(modal), data);
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});
}


function openDialogComponentSettingSection(obj){	
		
	var modal = $("#dialog-component-setting-section");
	
	$(modal).find("#dialog-form-options-error").html("");	
	var value = $(obj).closest("span").find("input[type='hidden']").val();
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
	
	openProperties(modal, column);
	if(column.datatype == 21 && column.datasourcetype != undefined && column.datasourcetype.length > 0 && column.datasourcetype != 'undefined'){
		if (column.datasourcetype == "2") {
			showDataSourceInView(modal, column);
			$(modal).find("a[href=#dialog-component-setting-codelist]").closest('li').show();
		} else {
			if ((column.dialogAutocompleteCode == undefined || column.dialogAutocompleteCode.length == 0)) {
				showDataSourceInView(modal, column);
				$(modal).find("a[href=#dialog-component-setting-codelist]").closest('li').show();
			} else {
				$(modal).find("a[href=#dialog-component-setting-codelist]").closest('li').hide();
			}
		}
		
	} else {
		$(modal).find("a[href=#dialog-component-setting-codelist]").closest('li').hide();
	}
	//set basetype
	$(modal).find("td[basetype='dialog-component-setting-base-types']").html(setBaseType(column));
	
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
		$.qp.addRowJSByLink($(modal).find("#tbl-hiddenAttibutes").next().find("a"));
		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:last").find("input[name=parameterAttribute]").val("");
		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:last").find("input[name=labelName]").val("");
		$(modal).find("#tbl-hiddenAttibutes").find("tbody").find("tr:last").find("input[name=labelNameAutocomplete]").val("");
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
	
	setFirstTabActive($(modal));
	$(modal).modal({show: true,closable: false,keyboard:false,backdrop:'static'});
}