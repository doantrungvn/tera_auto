// KhanhTH add column
$.qp.addColumnJSByLink = function(link, tableId) {
	var table = $(link).closest("table");
	var columnWidth = 225;
	var lastTh = $(table).find("th:last");
	
	var indexOfLastTH = parseInt($(lastTh).attr("index"));
	var index = indexOfLastTH + 1;
	
/*	if(indexOfLastTH == 0) {
		index = indexOfLastTH + 1;
	} else {
		index = indexOfLastTH;
	}*/
	
	$(table).find("colgroup").append("<col width=" + columnWidth + "px'></col>");
	var listIndex = $(table).attr("index");
	var formId = $(table).attr("screenid");
	table.find('tr').each(function(i) {
		var tableRow = $(this);
		
		if (tableRow.parent('thead').length == 1) {
			
			$(table).find(".glyphicon-plus").remove();
			tableRow.append('<th style="text-align:right" index='+index+'><span class="forPrototype"></span>\
								<input type="hidden" name="listOfScreenItemStatusForm['+listIndex+'].formulars['+index+'].formulaDefinitionContent" value=""/>\
								<input type="hidden" name="listOfScreenItemStatusForm['+listIndex+'].formulars['+index+'].formulaName" value=""/>\
								<input type="hidden" name="listOfScreenItemStatusForm['+listIndex+'].formulars['+index+'].formularDefinitionDetails" />\
								<input type="hidden" name="listOfScreenItemStatusForm['+listIndex+'].formulars['+index+'].screenFormId" value="'+formId+'"/>\
								<a href="javascript:void(0)" onclick="openFomulaSetting($(\'#screenId\').val(), this);" targetlabel="findTargetLabelOfFormula" targetvalue="findTargetValueOfFormula" sourceCallback="$.qp.formulabuilder.buildDataSourceForBusinessLogic">\
									<span class="btn btn-default btn-xs glyphicon glyphicon-list-alt qp-button-action" title="Setting"></span></a>\
								<label style="float: left;margin-left: 5px; margin-top : 4px; display : block" class="qp-output-text"></label>\
								<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" href="javascript:void(0)" title="Remove" onclick="$.qp.removeColumnJSByLink(this, \'' + tableId + '\');"></a>\
								<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" href="javascript:void(0)" title="Add" onclick="$.qp.addColumnJSByLink(this, \'' + tableId + '\');"></a>\
							</th>');
		} else if (tableRow.find('td:eq(3)').text() == 'FORM') {
			tableRow.append('<td style="text-align:right"> </td>');
		} else {
			var itemId = $(this).find('td:eq(4)').find('input[name$="itemId"]').val();
			if(tableRow.attr("type") == 2) {
				var itemTypeArea = $('#' + tableId).find("tr[type]").find("td[type]").find("#firstTypeArea").attr("value");
				
				tableRow.append('<td><input type="hidden" name="listOfScreenItemStatusForm['+listIndex+'].screenItemStatusForms['+(i - 1)+'].screenItemStatuses['+index+'].screenFormId" value="'+formId+'"/>\
									<div class="input-group" style="width:100%;"><select style="float: left; width: 150px;display: none;" class="form-control qp-input-select" onchange="setValue(this)"><option value="0">No display</option><option value="1" selected="true">Display</option></select>\
									<div class="checkbox-default  input-group-addon checkbox-slider--a checkbox-slider--a-rounded" style="line-height: 150%; float: right; width: 60px;">\
									<label><input onclick="enableCheck(this)" id="listOfScreenItemStatusForm'+listIndex+'.screenItemStatusForms1.screenItemStatuses0.enabled1" name="screenItemStatusForms['+(i - 1)+'].screenItemStatuses['+index+'].enabled" type="checkbox" value="true">\
									<input type="hidden" name="_listOfScreenItemStatusForm['+listIndex+'].screenItemStatusForms['+(i - 1)+'].screenItemStatuses['+index+'].enabled" value="on"><span></span></label></div>\
									<input type="hidden" name="listOfScreenItemStatusForm['+listIndex+'].screenItemStatusForms['+(i - 1)+'].screenItemStatuses['+index+'].status" value="1"/>\
									</div></td>');
			}
			if(tableRow.attr("type") == 3) {
				var itemTypeItem = $('#' + tableId).find("tr[type]").find("td[type]").find("#firstTypeItem").attr("value");
				tableRow.append('<td><input type="hidden" name="listOfScreenItemStatusForm['+listIndex+'].screenItemStatusForms['+(i - 1)+'].screenItemStatuses['+index+'].screenFormId" value="'+formId+'"/>\
						<div class="input-group" style="width:100%;"><select style="float: left; width: 150px;display: none;" class="form-control qp-input-select" onchange="setValue(this)"><option value="0">No display</option><option value="1" selected="true">Display</option><option value="2">Read only</option></select>\
									<div class="checkbox-default  input-group-addon checkbox-slider--a checkbox-slider--a-rounded" style="line-height: 150%; float: right; width: 60px;">\
									<label><input onclick="enableCheck(this)" id="screenItemStatusForms1.screenItemStatuses0.enabled1" name="listOfScreenItemStatusForm['+listIndex+'].screenItemStatusForms['+(i - 1)+'].screenItemStatuses['+index+'].enabled" type="checkbox" value="true">\
									<input type="hidden" name="listOfScreenItemStatusForm['+listIndex+']._screenItemStatusForms['+(i - 1)+'].screenItemStatuses['+index+'].enabled" value="on"><span></span></label></div>\
									<input type="hidden" name="listOfScreenItemStatusForm['+listIndex+'].screenItemStatusForms['+(i - 1)+'].screenItemStatuses['+index+'].status" value="1"/>\
									</div></td>');
			}
		}
		
	});
};

function getFormId(obj) {
	var formId = $(obj).find('td:eq(4)').find('input[name$="screenFormId"]').val();
	return formId;
}


// KhanhTH remove column
$.qp.removeColumnJSByLink = function(link, tableCode, formCode) {
	/*var table = $(link).closest("table");
	var cellIndex = $(link).parent().index();
	$(table).find("colgroup").find('col:eq(' + cellIndex + ')').remove();*/
	
	var table = $(link).closest("div").find("table");
	var cellIndex = $(link).parent().index();
	
	$(table).find('col:eq(' + cellIndex + ')').remove();
	
	$(table).find('tr').each(function() {
		var tableRow = $(this);
		tableRow.find('th:eq(' + cellIndex + ')').remove();
		tableRow.find('td:eq(' + cellIndex + ')').remove();
	});
	
	$(table).find('thead tr').each(function() {
		var tableRow = $(this);
		if(tableRow.find('th:last a').hasClass('glyphicon-plus')) {
			// do nothing
		} else {
			tableRow.find('th:last').append('<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" href="javascript:void(0)" title="Add" onclick="$.qp.addColumnJSByLink(this, \''+formCode+'tbl_list_domain_items\');"></a>');
		}
	});
	
	$(table).find('td').each(function() {
		var tdIndex = $(this).index();
		
		if (tdIndex >= 4) {
			var arrayIndex = tdIndex - 4;
			var replacement = 'screenItemStatuses[' + arrayIndex + ']';
			var regex = /screenItemStatuses\[\d*\]/g;
			$(this).find("[name*='['][name*='].']").each(function() {
				$(this).attr("name",$(this).attr("name").replace(regex, replacement));
			});
			
		}
		
	});
	
	$(table).find('th').each(function() {
		var tdIndex = $(this).index();
		
		if (tdIndex >= 4) {
			var arrayIndex = tdIndex - 4;
			var replacement = 'formulars[' + arrayIndex + ']';
			var regex = /formulars\[\d*\]/g;
			$(this).find("[name*='['][name*='].']").each(function() {
				$(this).attr("name",$(this).attr("name").replace(regex, replacement));
			});
			
		}
		
	});
};

//KhanhTH open formula setting modal
function openFomulaSetting(screenId, obj) {
	$.qp.formulabuilder.initialDataForFormulaSetting(obj);
};

// KhanhTH set condition name (for prototype)
$.qp.setConditionName = function() {
	var modal = "#dialog-formula-setting";
	var targetLabel = $(modal).data("targetLabel");
	var targetValue = $(modal).data("targetValue");
	var target = $(modal).data("target");
	var formulaName = $(modal).find("#formulaName").val();
	
	var content = "";
	var formulaDefinitionDetails = [];
	if ($(modal).find("#bdFormulaValueFormula").find("div.qp-formula-component").length > 0) {
		$(modal).find("#bdFormulaValueFormula").find("div.qp-formula-component").each(function (index) {
			var type = $(this).attr("type");
			var value = $(this).find("input[name=formulaitem]").val() == undefined ? null : $(this).find("input[name=formulaitem]").val();
			var item = $.qp.formulabuilder.convertToJson(value);
			item.itemSequenceNo = index;
			
			if(type == "16") {
				var input = $(this).find("div.qp-formula-component-content").find("input[type=text]").val() == undefined ? "" : $(this).find("div.qp-formula-component-content").find("input[type=text]").val();
				content +=  input + " ";
				item.value = input;
			} else if(type == "17") {
				content += $(this).find("div.qp-formula-component-content").text() + " ";
			} else {
				content += $(this).find("div.qp-formula-component-content").text() + " ";
			}
			formulaDefinitionDetails.push(item);
		});
		if($(targetLabel).prop('tagName') == "INPUT") {
			$(targetLabel).val(content.trim());
		} else {
			$(targetLabel).text(content.trim());
		}
		
		$(targetLabel).closest("td").find("label").html(content.trim());
		$(targetLabel).closest("th").find("input[name$='formulaName']").val(formulaName);
		
		$(targetValue).val($.qp.formulabuilder.convertToString(formulaDefinitionDetails));
		$(modal).modal("hide");
		
		var onaftersave = $(target).attr("onaftersave");
		/*$(target).closest('td').find("input[name$=formulaDefinitionContent]").val($(targetLabel));*/
		if(typeof jQuery.namespace(onaftersave) == "function") {
			jQuery.namespace(onaftersave)(target,formulaDefinitionDetails);
		}
	} else {
		alert("The data is invalid");
	}
	if(targetLabel.attr("value") != null && targetLabel.attr("value") != "") {
		$(target).closest("th").css("background-color","#eff5f9");
	}
};

function findTargetLabelOfFormula(thiz){
	return $(thiz).closest("th").find("input[name$='formulaDefinitionContent']");
	
}

function findTargetValueOfFormula(thiz){
	return $(thiz).closest("th").find("input[name$='formularDefinitionDetails']");
}

function enableCheck(obj) {
	
	var itemType = $(obj).closest('tr').attr('type');
	if (itemType == 2) {
		if ($(obj).prop("checked")) {
			$(obj).closest('td').find('select').show();
			var tdIndex = $(obj).closest('td').index(); 
			//get list item on		
			var countItem = 0;
			
			$(obj).closest('table').find('tr[type=3]').each(function() {
				$(this).find('td:eq('+tdIndex+')').find('input[type=checkbox][name$=".enabled"]').each(function() {
					if ($(this).prop('checked')) {
						countItem++;
					}
					
				});	
				
			});	
			
			if (countItem > 0) {
				if($.qp.confirm('Screen item will be disable, Do you want to setting enable for screen area?')) {
					var areaId = $(obj).closest('tr').find("input[name$=itemId]").val();
					
					//$(obj).closest('table').find('tr[area-id='+areaId+']').each(function() {
					$(obj).closest('table').find('tr[type=3]').each(function() {	
						$(this).find('td:eq('+tdIndex+')').find("select").hide();
						$(this).find('td:eq('+tdIndex+')').find("select").next().find("input[name$='.enabled']").prop("checked", false);
					});	
				} else {
					$(obj).closest('td').find('select').hide();
					$(obj).closest('td').find('select').next().find("input[name$='.enabled']").prop("checked", false);
				}
				
			}
			
		} else {
			$(obj).closest('td').find('select').hide();
		}
	} else {
		if ($(obj).prop("checked")) {
			$(obj).closest('td').find('select').show();
			var tdIndex = $(obj).closest('td').index(); 
			
			//get list item on		
			var countItem = 0;
			
			$(obj).closest('table').find('tr[type=2]').each(function() {
				$(this).find('td:eq('+tdIndex+')').find('input[type=checkbox][name$=".enabled"]').each(function() {
					if ($(this).prop('checked')) {
						countItem++;
					}
					
				});	
				
			});	
			
			if (countItem > 0) {
				if($.qp.confirm('Screen area will be disable, Do you want to setting enable for screen item?')) {
					var areaId = $(obj).closest('tr').find("input[name$=itemId]").val();
					$(obj).closest('table').find('tr[type=2]').each(function() {
						$(this).find('td:eq('+tdIndex+')').find("select").hide();
						$(this).find('td:eq('+tdIndex+')').find("select").next().find("input[name$='.enabled']").prop("checked", false);
					});
					
				} else {
					$(obj).closest('td').find('select').hide();
					$(obj).closest('td').find('select').next().find("input[name$='.enabled']").prop("checked", false);
				}
				
			}
			
		} else {
			$(obj).closest('td').find('select').hide();
		}
	}
	
}

function areaStatusChange(obj) {
	/*
	var areaId = $(obj).closest('tr').find("input[name$=itemId]").val();
	if ($(obj).val() == 0) {
		$(obj).closest("table").find('tr[area-id='+areaId+']').find("select").hide();
		$(obj).closest("table").find('tr[area-id='+areaId+']').find("select").next().find("input[name$='.enabled']").prop("checked", false);
	} else {
		$(obj).closest("table").find('tr[area-id='+areaId+']').find("select").show();
		$(obj).closest("table").find('tr[area-id='+areaId+']').find("select").next().find("input[name$='.enabled']").prop("checked", true);
	}
	*/
}
	