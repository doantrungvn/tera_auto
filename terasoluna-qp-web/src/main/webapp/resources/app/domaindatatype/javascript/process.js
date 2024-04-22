$(document).ready(function() {
	$('#tbl_list_domain_items').find("tbody").sortable({
		helper : function(e, ui) {
			ui.children().each(function() {
				$(this).width($(this).width());
			});
			return ui;
		},
		update : function(event, ui) {
			$("[id=itemSeqNo]").each(function(i) {
				if (i < $("[id=itemSeqNo]").length) {
					$('[id=itemSeqNo]:eq(' + i + ')').val(i + 1);
					$('span[id=spanItemSeqNo]:eq(' + i + ')').html(i + 1);
				}
			});

			$.qp.reArrayIndex($('#tbl_list_domain_items'));
		},
		items : 'tr',
		cursor : 'move',
		handle : '.sortable'
	});

	//init some data (fmtcode, default value) by domain data type
	$("#tbl_list_domain_items").find("tr:not(tr:first)").each(function() {
		init($(this).find("select[name$=domainDataType]"));
		$(this).find("input[name$=minVal]").focusout();
		$(this).find("input[name$=maxVal]").focusout();
	});
	
});

function changeTypeCodeList(thiz){
	var value = $(thiz).val();
	$(thiz).parents("table").find("tr[class=codelist-screen]").hide();
	$(thiz).parents("table").find("tr[class=codelist-system]").hide();
	if(value == 1){
		$(thiz).parents("table").find("tr[class=codelist-system]").show();
	}else if(value == 3){
		$(thiz).parents("table").find("tr[class=codelist-screen]").show();
	}
}

function changeSupportOptionValue() {
	if ($("#supportOptionValue").is(':checked')) {
		$('.colOptionName').hide();
	} else {
		$('.colOptionName').show();
	}
}

function init(obj) {
	if ($(obj).val() == '') {
		return;
	}
	//get default value and fmt code of domain design and set for domain datatype
	var optionSelected = $(obj).find("option[value="+$(obj).val()+"]");
	
	var fmtCode = optionSelected.attr("fmtCode");
	var defaultval = optionSelected.attr("defaultVal");
	
	var minVal = optionSelected.attr("minVal");
	var maxVal = optionSelected.attr("maxVal");

	fmtCode = (fmtCode == 'undefined') ? '':fmtCode;
	defaultval = (defaultval == 'undefined') ? '':defaultval;

	var oldFmtCode = $(obj).closest("tr").find("input[name$=fmtCode]").val();
	var oldDefaultVal = $(obj).closest("tr").find("input[name$=defaultValue]").val();
	var oldMinVal = $(obj).closest("tr").find("input[name$=minVal]").val();
	var oldMax = $(obj).closest("tr").find("input[name$=maxVal]").val();

	fmtCode = oldFmtCode == ''? fmtCode : oldFmtCode;
	defaultval = oldDefaultVal == ''? defaultval : oldDefaultVal;
	minVal = oldMinVal == ''? minVal : oldMinVal;
	maxVal = oldMax == ''? maxVal : oldMax;

	$(obj).closest("tr").find("input[name$=fmtCode]").val(fmtCode);
	$(obj).closest("tr").find("input[name$=defaultValue]").val(defaultval);
	$(obj).closest("tr").find("input[name$=minVal]").val(minVal);
	$(obj).closest("tr").find("input[name$=maxVal]").val(maxVal);

	// if autocomplte or select or radio or checkbox
	if ($(obj).val() == 0 || $(obj).val() == 5 || $(obj).val() == 6 || $(obj).val() == 7) {
		// if autocomplte
		if ($(obj).val() == 0) {
			$(obj).parent().next("span").attr("data-target", "#dialogAutocomplete");
			$(obj).parent().next("span").attr("onclick", "openDialogAutocompleteSetting(this);");
			
		} else {
			// if phycial data is boolean
			if ($(obj).parent().next("span").attr("id") == 7) {
				$(obj).parent().next("span").attr("data-target", "#dialogBooleanOption");
				$(obj).parent().next("span").attr("onclick", "openDialogBooleanSetting(this);");
			} else {
				$(obj).parent().next("span").attr("data-target", "#dialogOption");
				$(obj).parent().next("span").attr("onclick", "openDialogSetting(this);");
			}
		}
		$(obj).parent().next("span").show();
	} else {
		$(obj).parent().next("span").hide();
	}
}


function changeDataType(obj) {

	//get default value and fmt code of domain design and set for domain datatype
	var optionSelected = $(obj).find("option[value="+$(obj).val()+"]"); 
	var fmtCode = optionSelected.attr("fmtCode");
	var defaultval = optionSelected.attr("defaultVal");
	
	var minVal = optionSelected.attr("minVal");
	var maxVal = optionSelected.attr("maxVal");

	fmtCode = (fmtCode == 'undefined') ? '':fmtCode;
	defaultval = (defaultval == 'undefined') ? '':defaultval;

	$(obj).closest("tr").find("input[name$=fmtCode]").val(fmtCode);
	$(obj).closest("tr").find("input[name$=defaultValue]").val(defaultval);
	$(obj).closest("tr").find("input[name$=minVal]").val(minVal);
	$(obj).closest("tr").find("input[name$=maxVal]").val(maxVal);

	$(obj).closest("td").find("input[name$=msgLabel]").val("");
	$(obj).closest("td").find("input[name$=msgValue]").val("");
	$(obj).closest("td").find("input[name$=dataSource]").val("");

	// if autocomplte or select or radio or checkbox
	if ($(obj).val() == 0 || $(obj).val() == 5 || $(obj).val() == 6 || $(obj).val() == 7) {
		// if autocomplte
		if ($(obj).val() == 0) {
			$(obj).parent().next("span").attr("data-target", "#dialogAutocomplete");
			$(obj).parent().next("span").attr("onclick", "openDialogAutocompleteSetting(this);");
			
		} else {
			// if phycial data is boolean
			if ($(obj).parent().next("span").attr("id") == 7) {
				$(obj).parent().next("span").attr("data-target", "#dialogBooleanOption");
				$(obj).parent().next("span").attr("onclick", "openDialogBooleanSetting(this);");
			} else {
				$(obj).parent().next("span").attr("data-target", "#dialogOption");
				$(obj).parent().next("span").attr("onclick", "openDialogSetting(this);");
			}
		}
		$(obj).parent().next("span").show();
	} else {
		$(obj).parent().next("span").hide();
	}
}

function setDialogAutocompleteDetail(event) {
	var table = $("#tableDialogFormAutocomplete");
	var columnAutocomplete = $("input[name='columnAutocomplete']");

	if (columnAutocomplete.val() != '') {
		$(table).find("#srcgenAtcTable").text(event.item.output04);
		$(table).find("#srcgenAtcSearchColumn").text(event.item.output02);
		$(table).find("#srcgenAtcDisplayColumn").text(event.item.output02);
		$(table).find("#srcgenAtcSubmitColumn").text(event.item.output03);	
	} else {
		cleanAutocompleteDetail(table);
	}
}

function cleanAutocompleteDetail(table) {
	$(table).find("input[name='columnAutocompleteAutocomplete']").val("");
	$(table).find("input[name='columnAutocomplete']").val("");
	$(table).find("#srcgenAtcTable").text("");
	$(table).find("#srcgenAtcSearchColumn").text("");
	$(table).find("#srcgenAtcDisplayColumn").text("");
	$(table).find("#srcgenAtcSubmitColumn").text("");
}

function openDialogAutocompleteSetting(obj){
	var dialogForm = $("#dialogAutocomplete");
	$("#dialog-form-autocomplete-error").html("");
	var autoId = $(obj).closest("td").find("input[name$=dataSource]").val();

	var changeEventVal = $(obj).closest("td").find("input[name$=onChangeMethod]").val();
	var selectEventVal = $(obj).closest("td").find("input[name$=onSelectMethod]").val()

	var table = $("#tableDialogFormAutocomplete");
	$(table).find("input[name=dialogOnChangeEvent]").val(changeEventVal);
	$(table).find("input[name=dialogOnSelectEvent]").val(selectEventVal);

	/*dialogForm.modal("show").data("icon", obj);*/
	showDialog(dialogForm, obj);

	if (autoId != '' && autoId != 'undefined') {
		var href = CONTEXT_PATH + "/Autocomplete/";
		var params = {
				"sourceType" : "findAllAutocompleteForDomainDatatype",
				"arg01" :  autoId,
				"arg02" : $(table).find("input[name='columnAutocompleteAutocomplete']").attr("arg02")
			};
		var data = $.qp.getJson(href, params).outputGroup;
		if (data != null && data.length > 0 ) {
			data = data[0];
			$(table).find("input[name='columnAutocomplete']").val(autoId);
			$(table).find("input[name='columnAutocompleteAutocomplete']").val(data.optionLabel);
			$(table).find("#srcgenAtcTable").text(data.output04);
			$(table).find("#srcgenAtcSearchColumn").text(data.output02);
			$(table).find("#srcgenAtcDisplayColumn").text(data.output02);
			$(table).find("#srcgenAtcSubmitColumn").text(data.output03);
		} else {
			cleanAutocompleteDetail(table);
		}
	} else {
		cleanAutocompleteDetail(table);
	}

	$(table).find("input[name=dialogOnChangeEvent]").focus();
}

function openDialogBooleanSetting(obj){
	var dialogForm = $("#dialogBooleanOption");
	$("#dialog-boolean-options-error").html("");
	
	var msgLabel = $(obj).closest("td").find("input[name$=msgLabel]").val();
	var msgLabelArr = msgLabel.split("�");
	
	$("#tableDialogBooleanOptions").find("input[name=srcgenBooleanName]").each(function(i){
		$(this).val(msgLabelArr[i]);
	});

	$("#tableDialogBooleanOptions").find("tbody").find("tr:eq(1)").show();

	//if is checkbox
	if($(obj).closest("td").find("select[name$=domainDataType]").val() == 6){
		$(".notForCheckbox").hide();
	}

	/*$(dialogForm).dialog("open").data("icon", obj);*/
	/*dialogForm.modal("show").data("icon", obj);*/
	showDialog(dialogForm, obj);
}

/**
 * For dialog setting
 * @param obj
 */
function openDialogSetting(obj){
	if($(obj).closest("td").find("select[name$=domainDataType]").val() == 0){
		openDialogAutocompleteSetting(obj);
	} else {
		$("#dialog-form-options-error").html("");

		var dialogForm = $("#dialogOption");

		var codelistType = $(obj).closest("td").find("input[name$=codelistType]").val();

		if (codelistType == "") {
			codelistType = 3;//default is screen codelist
		}

		var msgLabel = $(obj).closest("td").find("input[name$=msgLabel]").val();
		var msgLabelArr = msgLabel.split("�");

		var msgVal = $(obj).closest("td").find("input[name$=msgValue]").val();
		var msgValArr = msgVal.split("�");

		var msgDatasource = $(obj).closest("td").find("input[name$=dataSource]").val();

		$("#dialogOption").find("input[name=supportOptionValue]").prop('checked', false);
		
		if(msgVal.length > 0 && msgLabel.length == 0){
			$("#dialogOption").find("input[name=supportOptionValue]").prop('checked', true);
		}

		var radioCodelist =$(dialogForm).find('input:radio[name="localCodelist"]'); 
		$(radioCodelist).prop('checked', false);
		$(radioCodelist).filter('[value="'+codelistType+'"]').prop('checked', true);
		changeTypeCodeList($(radioCodelist).filter('[value="'+codelistType+'"]'));
		
		if (msgDatasource.length > 0) {
			dialogForm.find("input[name=codelistSetting]").val(msgDatasource);
			var autocompleteName = $(obj).closest("td").find("input[name$=autocompleteName]").val()
			dialogForm.find("input[name=codelistSettingAutocomplete]").val(autocompleteName);
			loadSystemCodeList(dialogForm.find("input[name=codelistSettingAutocomplete]"));
		} else {
			dialogForm.find("input[name=codelistSettingAutocomplete]").val("");
			dialogForm.find("input[name=codelistSetting]").val('');
			var table = dialogForm.find("#tableDialogFormOptionsSystemCodelist");
			$(table).find("tbody").empty();

			if(msgVal.length > 0){
				if(dialogForm.find("input[name=supportOptionValue]").prop('checked') == true){
					var tr = "";
					$(msgValArr).each(function(i){
						if (msgValArr[i].length == 0) return;
						tr += "<tr>"+
									"<td class=\"com-output-fixlength tableIndex\"> " + (i+1) + "</td>"+
									"<td class=\"td-word-wrap colOptionName\"><input type=\"text\" class=\"form-control\" name=\"srcgenOptionName\" value=\""+ msgLabelArr[i] +"\" maxlength=\"200\"/></td>" +
									"<td class=\"td-word-wrap\"><input type=\"text\" class=\"form-control\" name=\"srcgenOptionValue\" value=\""+ msgValArr[i] +"\" maxlength=\"200\"/></td>" +
									"<td>" +
										"<a class=\"btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action\" title=\"Remove\" onclick=\"$.qp.removeRowJS('tbl_list_dialogOption', this);\" style=\"margin-top: 3px;\" href=\"javascript:void(0)\"></a>" +
									"</td>" +
								"</tr>";
					});
					$("#tableDialogFormOptions").find("tbody").replaceWith("<tbody>"+tr+"</tbody>");
				} else {
					var tr = "";
					$(msgValArr).each(function(i){
						if (msgValArr[i].length == 0) return;
						tr += "<tr>"+
									"<td class=\"com-output-fixlength tableIndex\"> " + (i+1) + "</td>"+
									"<td class=\"td-word-wrap colOptionName input-hidden\"><input type=\"text\" class=\"form-control\" name=\"srcgenOptionName\" value=\""+ msgLabelArr[i] +"\" maxlength=\"200\"/></td>" +
									"<td class=\"td-word-wrap\"><input type=\"text\" class=\"form-control\" name=\"srcgenOptionValue\" value=\""+ msgValArr[i] +"\" maxlength=\"200\"/></td>" +
									"<td>" +
										"<a class=\"btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action\" title=\"Remove\" onclick=\"$.qp.removeRowJS('tbl_list_dialogOption', this);\" style=\"margin-top: 3px;\" href=\"javascript:void(0)\"></a>" +
									"</td>" +
								"</tr>";
					});
					$("#tableDialogFormOptions").find("tbody").replaceWith("<tbody>"+tr+"</tbody>");
				}
			} else {
				// append default value
				var defaultValue = "<tr>"+
										"<td class=\"com-output-fixlength tableIndex\">1</td>"+
										"<td class=\"td-word-wrap colOptionName\"><input type=\"text\" class=\"form-control\" name=\"srcgenOptionName\" value=\""+dbMsgSource['sc.domaindatatype.0056']+"\" maxlength=\"200\"/></td>" +
										"<td class=\"td-word-wrap\"><input type=\"text\" class=\"form-control\" name=\"srcgenOptionValue\" value=\"1\" maxlength=\"200\"/></td>" +
										"<td class=\"td-word-wrap\">" +
										"<a class=\"btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action\" title=\"Remove\" onclick=\"$.qp.removeRowJS('tbl_list_dialogOption', this);\" style=\"margin-top: 3px;\" href=\"javascript:void(0)\"></a>" +
										"</td>" +
									"</tr>";
				$("#tableDialogFormOptions").find("tbody").replaceWith("<tbody>"+defaultValue+"</tbody>");
			}
		}

		changeSupportOptionValue();
		showDialog(dialogForm, obj);

		var dataType = $(obj).closest("td").find("input[name$=groupBasetypeId]").val();
		if(dataType == 2){
			$("#tableDialogFormOptions").find("tr:not(tr:first)").each(function() {
				$(this).find("input[name=srcgenOptionValue]").attr('class','form-control qp-input-integer');
			});
			$.qp.initialAutoNumeric($("#tableDialogFormOptions"));
		}
	}	
}

function saveDialogSetting() {
	var isError = false;
	var dialogForm = $("#dialogOption");
	var icon = dialogForm.data('icon');
	var value = "";
	
	var codelistType =$(dialogForm).find('input:radio[name="localCodelist"]:checked').val(); 
	
	if (codelistType == 3) {
		
		divtablename = "#tableDialogFormOptions";
		var isSupportOptionValue  = "";
		if($(dialogForm).find("input[name='supportOptionValue']")!= undefined){
			isSupportOptionValue = $(dialogForm).find("input[name='supportOptionValue']").prop("checked");
		}
		//must have a element
		if(!validateRequiredAtLeast(dialogForm,"tableDialogFormOptions",dbMsgSource['sc.domaindatatype.0048'],1)){
			return;
		}
		//required for option value
		if(!validateRequired(dialogForm,"srcgenOptionValue",dbMsgSource['sc.domaindatatype.0051'],true)) {
			return;
		}
		
		//check duplication for option name
		if(!validateDuplication(dialogForm,"srcgenOptionValue",dbMsgSource['sc.domaindatatype.0051'],true)) {
			return;
		}
		
		if(!isSupportOptionValue){
			//required for option name
			if(!validateRequired(dialogForm,"srcgenOptionName",dbMsgSource['sc.domaindatatype.0050'],true)) {
				return;
			}
			//check duplication for option name
			if(!validateDuplication(dialogForm,"srcgenOptionName",dbMsgSource['sc.domaindatatype.0050'],true)) {
				return;
			}
		}

		$("#tableDialogFormOptions").find("input[name=srcgenOptionValue]").each(function(i){
			value += $(this).val() + "�";
		});
		
		if(value != ""){
			$(icon).closest("td").find("input[name$=msgValue]").val(value.substring(0,value.length-1));
		} else {
			$(icon).closest("td").find("input[name$=msgValue]").val("");
		}
	
		if($("#dialogOption").find("input[name=supportOptionValue]").prop('checked') == false){
			var name = "";
			$("#tableDialogFormOptions").find("input[name=srcgenOptionName]").each(function(i){
				/*if($(this).val() == "" && $("#dialogOption").find("input[name=supportOptionValue]").prop('checked') == false){
					$("#dialogOption").find("#dialog-form-options-error").html("").append("<font color=\"red\">"+fcomMsgSource['err.sys.0026'].replace("{0}", (i+1)).replace("{1}", dbMsgSource['sc.domaindatatype.0056'])+"</font>");
					isError = true;
					return false;
				}
				if(isProhibitChar($(this).val()) && $("#dialogOption").find("input[name=supportOptionValue]").prop('checked') == false){
					$("#dialogOption").find("#dialog-form-options-error").html("").append("<font color=\"red\">"+fcomMsgSource['err.sys.0034'].replace("{0}", (i+1)).replace("{1}", dbMsgSource['sc.domaindatatype.0056']).replace("{2}", "' \"")+"</font>");
					isError = true;
					return false;
				}*/
				name += $(this).val() + "�";
			});
			
			if(name != ""){
				$(icon).closest("td").find("input[name$=msgLabel]").val(name.substring(0,name.length-1));
			} else {
				$(icon).closest("td").find("input[name$=msgLabel]").val(dbMsgSource['sc.domaindatatype.0056']);
			}
			$(icon).closest("td").find("input[name$=codelistType]").val("3"); //screen code list
			$(icon).closest("td").find("input[name$=supportOptionFlg]").val("0"); //don't require label
		} else {
			$(icon).closest("td").find("input[name$=msgLabel]").val("");
			$(icon).closest("td").find("input[name$=codelistType]").val("3"); //screen code list
			$(icon).closest("td").find("input[name$=supportOptionFlg]").val("1"); //require lable
		}

		$(icon).closest("td").find("input[name$=dataSource]").val("");
	} else {
		$(icon).closest("td").find("input[name$=msgLabel]").val("");
		$(icon).closest("td").find("input[name$=msgValue]").val("");

		var codelist = dialogForm.find("input[name=codelistSetting]").val();
		
		if (codelist == '') {
			alert(fcomMsgSource["err.sys.0025"].replace("{0}", dbMsgSource['sc.domaindatatype.0046']));
			return false;
		}
		
		var autocompleteName = dialogForm.find("input[name=codelistSettingAutocomplete]").val();
		$(icon).closest("td").find("input[name$=autocompleteName]").val(autocompleteName);
		$(icon).closest("td").find("input[name$=dataSource]").val(codelist);
		$(icon).closest("td").find("input[name$=codelistType]").val("1");//system codelist
	}
	
	if(isError){
		return false;
	} else {
		$("#dialog-form-options-error").html("");
	}
	
	dialogForm.modal("hide");
}

function saveDialogBooleanSetting() {
	var dialogForm = $("#dialogBooleanOption");
	var isError = false;
	var icon = dialogForm.data('icon');
	var value = "";
	var dataType = $(icon).closest("td").find("select[name$=domainDataType]").val();
	var messages="";

	$("#tableDialogBooleanOptions").find("input[name=srcgenBooleanName]").each(function(i){
		//if is checkbox
		if(dataType != 6){
			if($(this).val() == ""){
				messages += "\r\n" + fcomMsgSource['err.sys.0077'].replace("{0}",dbMsgSource['sc.domaindatatype.0050']).replace("{1}",i+1);
			}
			value += $(this).val() + "�";
		} else {
			if(i == 0){
				if($(this).val() == ""){
					messages += fcomMsgSource['err.sys.0025'].replace("{0}",dbMsgSource['sc.domaindatatype.0050']);
				}
				value += $(this).val() + "�";
			}
		}
	});
	if(value != ""){
		$(icon).closest("td").find("input[name$=msgLabel]").val(value.substring(0,value.length-1));
		if(dataType != 6){
			$(icon).closest("td").find("input[name$=msgValue]").val("TRUE�FALSE");
		} else {
			$(icon).closest("td").find("input[name$=msgValue]").val("TRUE");
		}
		$(icon).closest("td").find("input[name$=codelistType]").val("3"); //screen code list
	}
	
	if(messages != ""){
		alert(messages);
		return;
	}
	//check duplication for option name
	if(!validateDuplication(dialogForm,"srcgenBooleanName",dbMsgSource['sc.domaindatatype.0050'],true)) {
		return;
	}

	$("#dialog-boolean-options-error").html("");
	$("#tableDialogBooleanOptions").find("input[name=srcgenBooleanName]").each(function(i){
		$(this).val("");
	});
	dialogForm.modal("hide");
	
}

function saveAutocomplete () {
	var dialogForm = $("#dialogAutocomplete");
	var isError = false;
	var icon = dialogForm.data('icon');
	var table = $("#tableDialogFormAutocomplete");
	
	if($(table).find("input[name=columnAutocomplete]").val().length == 0){
		$("#dialog-form-autocomplete-error").html("").append("<font color=\"red\">"+fcomMsgSource['err.sys.0025'].replace("{0}", dbMsgSource['sc.domaindatatype.0038'])+"</font>");
		return false;
	}
	if($(table).find("input[name=dialogOnChangeEvent]").val().length > 0 && isNotAlphanumeric($(table).find("input[name=dialogOnChangeEvent]").val())){
		$("#dialog-form-autocomplete-error").html("").append("<font color=\"red\">"+fcomMsgSource['err.sys.0031'].replace("{0}", dbMsgSource['sc.domaindatatype.0057'])+"</font>");
		return false;
	}
	if($(table).find("input[name=dialogOnSelectEvent]").val().length > 0 && isNotAlphanumeric($(table).find("input[name=dialogOnSelectEvent]").val())){
		$("#dialog-form-autocomplete-error").html("").append("<font color=\"red\">"+fcomMsgSource['err.sys.0031'].replace("{0}", dbMsgSource['sc.domaindatatype.0058'])+"</font>");
		return false;
	}

	var datasource = $(table).find("input[name=columnAutocomplete]").val();
	var onChangeEvent = $(table).find("input[name=dialogOnChangeEvent]").val();
	var onSelectEvent = $(table).find("input[name=dialogOnSelectEvent]").val();
	var autocompleteName = $(table).find("input[name=columnAutocompleteAutocomplete]").val();

	$(icon).closest("td").find("input[name$=dataSource]").val(datasource);
	$(icon).closest("td").find("input[name$=onChangeMethod]").val(onChangeEvent);
	$(icon).closest("td").find("input[name$=onSelectMethod]").val(onSelectEvent);
	$(icon).closest("td").find("input[name$=autocompleteName]").val(autocompleteName);

	dialogForm.modal("hide");
}

function clickSaveButton(){
	var error = "";
	if($("#domainDatatypeName").val().length == 0){
		error += fcomMsgSource['err.sys.0025'].replace("{0}", dbMsgSource['sc.domaindatatype.0002']) + "\n";
	}

	if($("#domainDatatypeCode").val().length == 0){
		error += fcomMsgSource['err.sys.0025'].replace("{0}", dbMsgSource['sc.domaindatatype.0003']) + "\n";
	}

	$("input[name=arrMsgCode]").each(function(i){
		if($(this).val().length == 0){
			error += fcomMsgSource['err.sys.0026'].replace("{0}", (i+1)).replace("{1}", dbMsgSource['sc.domaindatatype.0010']) + "\n";
		}
		if(isProhibitChar($(this).val())){
			error += fcomMsgSource['err.sys.0034'].replace("{0}", (i+1)).replace("{1}", dbMsgSource['sc.domaindatatype.0010']).replace("{2}", "' \"") + "\n";
		}
		
		var datatype = $("select[name^=domainDataType]:eq("+i+")").val();
		if(datatype == 5 || datatype == 6 || datatype == 7){
			if($("input[name$=msgValue]:eq("+i+")").val().length == 0){
				error += fcomMsgSource['err.sys.0026'].replace("{0}", (i+1)).replace("{1}", dbMsgSource['sc.domaindatatype.0012']) + "\n";
			}
		}
		if(datatype == 0){
			if($("input[name$=dataSource]:eq("+i+")").val().length == 0){
				error += fcomMsgSource['err.sys.0026'].replace("{0}", (i+1)).replace("{1}", dbMsgSource['sc.domaindatatype.0012']) + "\n";
			}
		}
		var maxlength = $("input[name$=maxlengthPhysical]:eq("+i+")").autoNumeric('get');
		if(maxlength.length == 0){
			error += fcomMsgSource['err.sys.0026'].replace("{0}", (i+1)).replace("{1}", dbMsgSource['sc.domaindatatype.0014']) + "\n";
		} else {
			var maxlengthDef = $("input[name$=maxlengthPhysical]:eq("+i+")").val();
			if(parseInt(maxlength) > maxlengthDef){
				error += fcomMsgSource['err.srcgen.0006'].replace("{0}", (i+1)).replace("{1}", maxlengthDef) + "\n";
			}
		}
	});

	if(error.length > 0){
		alert(error);
		return false;
	} 
}


/**
 * For dialog advance setting
 * @param obj
 */
function openDialogAdvanceSetting(obj){
	$("#dialog-form-options-error").html("");
	var dialogForm = $("#dialogAdvanceSetting");

	var fmtCode = $(obj).closest("td").find("input[name$=fmtCode]").val();
	var defaultVal = $(obj).closest("td").find("input[name$=defaultValue]").val(); 

	var table = $("#tableDialogAdvanceSetting");

	var physicalDataType = $(obj).closest("tr").find("input[name$=groupBasetypeId]").val();
	
	$(table).find("input[name=defaultValue]").autoNumeric('destroy');

	var inputColumn = $(table).find("#clumnDefaultVal");
	
	var isDateTime = false;
	
	if(physicalDataType == 2){//if is integer
		var input = "<input type=\"text\" class=\"form-control qp-input-integer\" name=\"defaultValue\" maxlength=\"200\" value=\"\" />";
		inputColumn.html("").append (input);
		$.qp.formatInteger($('.qp-input-integer'));
	} else if(physicalDataType == 6){//Currency
		var input = "<input type=\"text\" class=\"form-control qp-input-currency\" name=\"defaultValue\" maxlength=\"200\" value=\"\" />";
		inputColumn.html("").append (input);
		$.qp.formatCurrency($('.qp-input-currency'));
	} else if(physicalDataType == 3){//numberic
		var input = "<input type=\"text\" class=\"form-control qp-input-float\" name=\"defaultValue\" maxlength=\"200\" value=\"\" />";
		inputColumn.html("").append (input);
		$.qp.formatFloat($('.qp-input-float'));
	} else if(physicalDataType == 4){//Date
		var input = "<div class='input-group date qp-input-datepicker'>";
		input += "<input type=\"text\" name=\"defaultValue\" class=\"form-control\" />";
		input += "<span class=\"input-group-addon\">";
		input += "<span class=\"glyphicon glyphicon-calendar\"></span>";
		input += "</span>";
		input += "</div>";
		inputColumn.html("").append (input);
		$.qp.initialDatePicker($(".qp-input-datepicker"));
		isDateTime = true;
	} else if(physicalDataType == 8){//Time
		var input = "<div class='input-group date qp-input-timepicker'>";
		input += "<input type=\"text\" name=\"defaultValue\" class=\"form-control\" />";
		input += "<span class=\"input-group-addon\">";
		input += "<span class=\"glyphicon glyphicon-time\"></span>";
		input += "</span>";
		input += "</div>";

		inputColumn.html("").append (input);
		isDateTime = true;
		$.qp.initialTimePicker($(".qp-input-timepicker"));
	} else if(physicalDataType == 9){//DateTime
		var input = "<div class='input-group date qp-input-datetimepicker-detail'>";
		input += "<input type=\"text\" name=\"defaultValue\" class=\"form-control\" />";
		input += "<span class=\"input-group-addon\">";
		input += "<span class=\"glyphicon glyphicon-calendar\"></span>";
		input += "</span>";
		input += "</div>";
		
		inputColumn.html("").append (input);
		isDateTime = true;
		$.qp.initialDateTimePicker($(".qp-input-datetimepicker-detail"));
	} else if(physicalDataType == 1 || physicalDataType == 5){//TEXT or CHAR
		var input = "<input type=\"text\" class=\"form-control qp-input-text\" name=\"defaultValue\" maxlength=\"200\" value=\"\" />";
		inputColumn.html("").append (input);
	} else {
		inputColumn.html("");
	}

	$(table).find("#fmtCode").text(fmtCode);
	$(table).find("input[name=defaultValue]").val(defaultVal);

	var dialogModal = dialogForm.find(".modal-body");
	if (isDateTime) {
		if (!dialogModal.hasClass("dialog-with-date")) {
			dialogForm.find(".modal-body").addClass("dialog-with-date");
		}
	} else {
		dialogForm.find(".modal-body").removeClass("dialog-with-date");
	}

	showDialog(dialogForm, obj);
	$(table).find("input[name=defaultValue]").focus();
}

function saveDialogAdvanceSetting(obj) {
	var dialogForm = $("#dialogAdvanceSetting");
	var icon = dialogForm.data('icon');
	$.qp.undoFormatNumericForm(dialogForm);
	$(icon).closest("td").find("input[name$=defaultValue]").val($("#tableDialogAdvanceSetting").find("input[name=defaultValue]").val());
	dialogForm.modal("hide");
}


function selectCodeList(obj){
	loadSystemCodeList(obj.target)
}
function loadSystemCodeList(obj){
	var codelistId = $(obj).next("input[type=hidden]").val();
	var href = CONTEXT_PATH + "/screendesign/getSystemCodeListDetailById?r="+Math.random();

	var table = $(obj).parents("table").find("#tableDialogFormOptionsSystemCodelist");
	$(table).find("tbody").empty();

	$.ajax({
		method : "GET",
		url : href,
		data : { data : codelistId },
		context: table,
		async : false
	}).done(function(msg) {
		if(msg.length>0){
			var tdContent = ""
			for(i = 0; i < msg.length; i++){
				var name = msg[i].name == null ?"" : msg[i].name;
				var value = msg[i].value == null ?"" : msg[i].value;
				tdContent +=  "<tr>"
				+ "		<td>"+(i+1)+"</td>"
				+ "		<td><span name=\"parameterOptionName\" >"+name+"</span></td>"
				+ "		<td><span name=\"parameterOptionValue\" >"+value+"</span></td>"
				+ "	</tr>"
				+ "";
			}
			console.log(tdContent);
			$(this).find("tbody").append(tdContent);
		} else {
			
		}
	});
}


/**
 * 
 * @param dialogForm
 * @param obj
 */
function showDialog(dialogForm, obj) {
	$(dialogForm).data("icon", obj);
	$(dialogForm).modal(
		{ 
			show: true,
			closable: false,
			keyboard:false,
			backdrop:'static'
		}
	);
}
