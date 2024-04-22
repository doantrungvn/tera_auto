function setDialogAutocompleteDetail(event){
	var table = $(event.target).closest("#tableDialogFormAutocomplete");
	if(event.item != undefined){
		$(table).find("#srcgenAtcSearchColumn").text(event.item.output02);
		$(table).find("#srcgenAtcDisplayColumn").text(event.item.output02);
		$(table).find("#srcgenAtcSubmitColumn").text(event.item.output03);
		$(table).find("#srcgenAtcTable").text(event.item.output04);
	} 
	if(event.item == undefined){
		$(table).find("#srcgenAtcTable").text("");
		$(table).find("#srcgenAtcSearchColumn").text("");
		$(table).find("#srcgenAtcDisplayColumn").text("");
		$(table).find("#srcgenAtcSubmitColumn").text("");
	}
}

function returnUnitSlection(){
	return "<select class=\"form-control qp-input-select\" style=\"width: 60px; padding-top: 0.1em; padding-bottom: 0.4em; margin-bottom: 4px;\" name=\"columnnameSizeUnit\">"+
								"<option value=\"%\">%</option>" + 
								"<option value=\"px\">px</option>" + 
							"</select>";
}

function returnColWidth() {
	return "<tr id=\"trColWidthSetting\">" + 
						"<th class=\"com-table-th-text style-header-table\">"+dbMsgSource['sc.screendesign.0131']+"<br/><a href=\"javascript:\" class=\"com-link\" onclick=\"equitableColumnWidth(this)\" id=\"equitableColumnWidth\">"+dbMsgSource['sc.screendesign.0132']+"</a></th>" + 
						"<td id=\"columnnameSize\"></td>" + 
					"</tr>";
}


function returnDataType(obj){
	var datatype = "";
	if(obj == 0){
		datatype = dbMsgSource['sc.screendesign.0041'];
	}
	if(obj == 1){
		datatype = dbMsgSource['sc.screendesign.0051'];
	}
	if(obj == 2){
		datatype = dbMsgSource['sc.screendesign.0038'];
	}
	if(obj == 3){
		datatype = dbMsgSource['sc.screendesign.0044'];
	}
	if(obj == 4){
		datatype = dbMsgSource['sc.screendesign.0053'];
	}
	if(obj == 5){
		datatype = dbMsgSource['sc.screendesign.0049'];
	}
	if(obj == 6){
		datatype = dbMsgSource['sc.screendesign.0047'];
	}
	if(obj == 7){
		datatype = dbMsgSource['sc.screendesign.0048'];
	}
	if(obj == 8){
		datatype = dbMsgSource['sc.screendesign.0042'];
	}
	if(obj == 9){
		datatype = dbMsgSource['sc.screendesign.0046'];
	}
	if(obj == 10){
		datatype = dbMsgSource['sc.screendesign.0057'];
	}
	if(obj == 11){
		datatype = dbMsgSource['sc.screendesign.0039'];
	}
	if(obj == 12){
		datatype = dbMsgSource['sc.screendesign.0043'];
	}
	if(obj == 20){
		datatype = dbMsgSource['sc.screendesign.0050'];
	}
	if(obj == 13){
		datatype = dbMsgSource['sc.screendesign.0128'];
	}
	if(obj == 14){
		datatype = dbMsgSource['sc.screendesign.0045'];
	}
	if(obj == 15){
		datatype = dbMsgSource['sc.screendesign.0055'];
	}
	if(obj == 16){
		datatype = dbMsgSource['sc.screendesign.0056'];
	}
	if(obj == 17){
		datatype = dbMsgSource['sc.screendesign.0129'];
	}
	if(obj == 18){
		datatype = dbMsgSource['sc.screendesign.0054'];
	}
	if(obj == 21){
		datatype = dbMsgSource['sc.screendesign.0038'];
	}
	return datatype;
}

function insertLink(ev, ui, obj){
	var div = $(ui.draggable);
	var dataType = $(div).attr('datatype');
	
	var elementTD = "";
	var tablecolumncode = "";
	if($(div).attr('tablecolumncode') != undefined){
		tablecolumncode = $(div).attr('tablecolumncode');
	}
	var tablename = "";
	if($(div).attr('tablename') != undefined){
		tablename = $(div).attr('tablename');
	}
	var tablecode = "";
	if($(div).attr('tablecode') != undefined){
		tablecode = $(div).attr('tablecode');
	}
	var tablecolumnname = "";
	if($(div).attr('tablecolumnname') != undefined){
		tablecolumnname = $(div).attr('tablecolumnname');
	}
	
	var physicaldatatype = "";
	if($(div).attr('physicaldatatype') != undefined){
		physicaldatatype = $(div).attr('physicaldatatype');
	}
	var minvalue = "";
	if($(div).attr('minvalue') != undefined){
		minvalue = $(div).attr('minvalue');
	}
	var maxvalue = "";
	if($(div).attr('maxvalue') != undefined){
		maxvalue = $(div).attr('maxvalue');
	}
	var formatcode = "";
	if($(div).attr('formatcode') != undefined){
		formatcode = $(div).attr('formatcode');
	}
	var physicalmaxlength = "";
	if($(div).attr('physicalmaxlength') != undefined){
		physicalmaxlength = $(div).attr('physicalmaxlength');
	}
	var maxlength = "";
	if($(div).attr('maxlength') != undefined){
		maxlength = $(div).attr('maxlength');
	}
	var require = "";
	if($(div).attr('require') != undefined){
		require = $(div).attr('require');
	}
	var rowspan = "";
	if($(div).attr('rowspan') != undefined){
		rowspan = $(div).attr('rowspan');
	}
	var colspan = "";
	if($(div).attr('colspan') != undefined){
		colspan = $(div).attr('colspan');
	}
	var datasource = "";
	if($(div).attr('datasource') != undefined){
		datasource = $(div).attr('datasource');
	}
	var msglabel = "";
	if($(div).attr('msglabel') != undefined){
		msglabel = $(div).attr('msglabel');
	}
	
	var element =  "" 
		+ "<input type=\"hidden\" name=\"formElement\" "
		+ "value='\"label\":\""
		+ "\",\"datatype\":\""+$(div).attr('datatype')
		+ "\",\"physicaldatatype\":\""+physicaldatatype
		+ "\",\"columnname\":\""+$(div).attr('columnname')
		+ "\",\"rowspan\":\""+rowspan
		+ "\",\"colspan\":\""+colspan
		+ "\",\"datasource\":\""+datasource
		+ "\",\"minvalue\":\""+minvalue
		+ "\",\"maxvalue\":\""+maxvalue
		+ "\",\"formatcode\":\""+formatcode
		+ "\",\"tablename\":\""+tablename
		+ "\",\"tablecode\":\""+tablecode
		+ "\",\"tablecolumnname\":\""+tablecolumnname
		+ "\",\"tablecolumncode\":\""+tablecolumncode
		+ "\",\"connectionmsg\":\""
		+ "\",\"transitiontype\":\"" + "1"
		+ "\",\"actiontype\":\"" + "0"
		+ "\",\"parameters\":\""
		+ "\",\"screenactionid\":\""
		+ "\",\"linklabel\":\""
		+ "\",\"toscreenid\":\""
		+ "\",\"maxlength\":\""+maxlength
		+ "\",\"labelText\":\""+""
		+ "\",\"msgcode\":\""+dbMsgSource['sc.screendesign.0039']
		+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
	
	
		if($(div).attr('msgvalue') != undefined){
			element += "\",\"msglabel\":\""+msglabel
			+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
		} else {
			element += "\",\"msglabel\":\"\",\"msgvalue\":\"";
		}
		
	element += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
	
	$("#srcgenHeaderLinkArea").append("<span style=\"padding: 0 5px 0 5px;\"><a href=\"javascript:\" class=\"com-link\" ondblclick=\"openDialogLinkSetting(this)\">"+dbMsgSource['sc.screendesign.0039']+"</a>" +
			element + "</span>");
}

function returnLinkItem(obj){

	var dataType = obj.datatype;
	
	var elementTD = "";
	var tablecolumncode = "";
	if(obj.tablecolumncode != undefined){
		tablecolumncode = obj.tablecolumncode;
	}
	var tablename = "";
	if(obj.tablename != undefined){
		tablename = obj.tablename;
	}
	var tablecode = "";
	if(obj.tablecode != undefined){
		tablecode = obj.tablecode;
	}
	var tablecolumnname = "";
	if(obj.tablecolumnname != undefined){
		tablecolumnname = obj.tablecolumnname;
	}
	
	var physicaldatatype = "";
	if(obj.physicaldatatype != undefined){
		physicaldatatype = obj.physicaldatatype;
	}
	var minvalue = "";
	if(obj.minvalue != undefined){
		minvalue = obj.minvalue;
	}
	var maxvalue = "";
	if(obj.maxvalue != undefined){
		maxvalue = obj.maxvalue;
	}
	var formatcode = "";
	if(obj.formatcode != undefined){
		formatcode = obj.formatcode;
	}
	var physicalmaxlength = "";
	if(obj.physicalmaxlength != undefined){
		physicalmaxlength = obj.physicalmaxlength;
	}
	var maxlength = "";
	if(obj.maxlength != undefined){
		maxlength = obj.maxlength;
	}
	var require = "";
	if(obj.require != undefined){
		require = obj.require;
	}
	var rowspan = "";
	if(obj.rowspan != undefined){
		rowspan = obj.rowspan;
	}
	var colspan = "";
	if(obj.colspan != undefined){
		colspan = obj.colspan;
	}
	var datasource = "";
	if(obj.datasource != undefined){
		datasource = obj.datasource;
	}
	var msglabel = "";
	if(obj.msglabel != undefined){
		msglabel = obj.msglabel;
	}
	
	var element =  "" 
		+ "<input type=\"hidden\" name=\"formElement\" "
		+ "value='\"label\":\""+obj.label
		+ "\",\"datatype\":\""+obj.datatype
		+ "\",\"physicaldatatype\":\""+physicaldatatype
		+ "\",\"columnname\":\""+obj.columnname
		+ "\",\"rowspan\":\""+rowspan
		+ "\",\"colspan\":\""+colspan
		+ "\",\"datasource\":\""+datasource
		+ "\",\"minvalue\":\""+minvalue
		+ "\",\"maxvalue\":\""+maxvalue
		+ "\",\"formatcode\":\""+formatcode
		+ "\",\"tablename\":\""+tablename
		+ "\",\"tablecode\":\""+tablecode
		+ "\",\"tablecolumnname\":\""+tablecolumnname
		+ "\",\"tablecolumncode\":\""+tablecolumncode
		+ "\",\"connectionmsg\":\""
		+ "\",\"transitiontype\":\"" + "1"
		+ "\",\"screenactionid\":\""
		+ "\",\"linklabel\":\""
		+ "\",\"toscreenid\":\""
		+ "\",\"maxlength\":\""+maxlength
		+ "\",\"labelText\":\""+obj.labelText
		+ "\",\"msgcode\":\""+dbMsgSource['sc.screendesign.0039']
		+ "\",\"physicalmaxlength\":\""+physicalmaxlength
		+ "\",\"actiontype\":\""+obj.actiontype
		+ "\",\"actionName\":\""+obj.actionName
		+ "\",\"parameters\":\""+obj.parameters
		+ "\",\"navigateTo\":\""+obj.navigateTo
		+ "\",\"navigateToText\":\""+obj.navigateToText
		+ "\",\"style\":\""+obj.style
		+ "\",\"hoverStyle\":\""+obj.hoverStyle
		+ "\",\"screenTransition\":\""+obj.screenTransition
		+ "\",\"screenTransitionText\":\""+obj.screenTransitionText
		+ "\",\"isSubmit\":\""+obj.isSubmit
		;
	
	
		if(obj.msgvalue != undefined){
			element += "\",\"msglabel\":\""+msglabel
			+ "\",\"msgvalue\":\""+obj.msgvalue;
		} else {
			element += "\",\"msglabel\":\"\",\"msgvalue\":\"";
		}
		
	element += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
	
	$("#srcgenHeaderLinkArea").append("<span style=\"padding: 0 5px 0 5px;\"><a style=\""+obj.style+"\" onmouseover=\"this.setAttribute('style', '"+obj.hoverStyle+"')\" onmouseout=\"this.setAttribute('style', '"+obj.style+"')\" href=\"javascript:\" class=\"com-link\" ondblclick=\"openDialogLinkSetting(this)\">"+obj.labelText+"</a>" + element + "</span>");
}

function reIndexCol(table) {
	$(table).find("tr").each(function(i) {		
		if (i > 0) {
			var tr = $(this);
			var columnSize = $(this).children().length; 
			
			$(this).find("td, th").each(function(j){
				
				if (j < columnSize - 2) {
					
					$(this).attr("index", j);
				}
			});
		}
	});
}

function reIndexTable(table) {
	$(table).find("tr").each(function(i) {		
		if (i > 0) {
			$(this).attr("index", (i - 1));
			var tr = $(this);
			var columnSize = $(this).children().length; 
			
			$(this).find("td, th").each(function(j){
				
				if (j < columnSize - 2) {
					console.log($(this).prop("tagName") + j);
					$(this).attr("index", j);
				}
			});
		}
	});
}

function reIndexColTableList(table) {
	$(table).find("tr").each(function(i) {				
		var tr = $(this);
		$(this).attr("index", i);
		var columnSize = $(this).children().length; 
		
		$(this).find("td, th").each(function(j){
			
			if (j < columnSize) {				
				$(this).attr("index", j);
			}
		});		
	});
}

function insertLable(ev, ui, obj){	
	var div = $(ui.draggable);
	var tr = $(obj).closest('tr');
	var tableId = $(tr).closest('table').attr('id');
	var tableType = $(tr).closest('table').attr('type');
	
	$(div).attr("elementtype",0);
	var elementTH = returnElementTH(div);
	
	if ($(tr).closest('table').attr("class") == "table table-bordered qp-table-list-none-action") {
		var headerStyle = $(tr).closest('table').closest('.areaContent').find('input[name=headerStyle]').val();
		if (headerStyle.indexOf('text-align:') != -1) {
			elementTH = returnElementTH(div);
		} else {
			elementTH = returnElementTH(div, true);
		}
	}
	
	var elementTD = "";
	
	$(obj).parent('th').replaceWith(elementTH);
	if ($(tr).closest('table').attr("class") == "table table-bordered qp-table-list-none-action") {
		reIndexColTableList($(tr).closest("table"));
	} else {
		reIndexCol($(tr).closest("table"));
	}
	
	$.qp.initialAutoNumeric($(tr));
	
	var table = $(tr).closest("table");
	if ($(table).is("table[class*=qp-table-form]")) {
		checkColRowSpan($(table));
		checkSwap($(table));
	} else if ($(table).is("table[class*=qp-table-list-none-action]")) {
		checkColRowSpan($(table));
		checkSwapListTable($(table));
	}
	
	$(tr).find(".glyphicon-screenshot").draggable({
		containment: '#' + tableId,
		stack: '#' + tableId,
		revert: function(event, ui) {
			if ($(this).data("uiDraggable") != undefined && $(this).data("uiDraggable").originalPosition != undefined) {
				$(this).data("uiDraggable").originalPosition = {top : 0,left : 0};
			}
			return true;
		},
		stop: function(event, ui) {
    	  	$(this).css("z-index","auto");
    	  } 
	});
	
	reloadStyle($(tr).closest('table'));
	reloadDroppable($(tr).closest('table'));
}

function insertComponent(ev, ui, obj){
	var div = $(ui.draggable);
	var tr = $(obj).closest('tr');	
	var table =$(tr).closest('table');
	var tableId = $(table).attr('id');
	var tableType = $(table).attr('type');
	var index = $(obj).parent('td').attr("index"); 
	
	var td = $(obj).parent('td');
	
	$(div).attr("elementtype",0);
	var elementTH = returnElementTH(div);
	var elementTD = "";
	
	if(tableType != undefined && tableType == "search"){
		elementTD = returnElementTDSearch(div);
	}else { 
		elementTD = returnElementTD(div);
	}
	
	if ($(div).attr("id") == "newDragElement") {
		
		var areaType = $(tr).closest('.areaContent').find("input[name=formAreaType]").val();
		var data = convertToJson($(div).attr("data"));
		
		data["isBlank"] = "false";
		data["isBundle"] = "false";
		data["labelText"] = data.labelText;
		data["label"] = ""; 
		
		switch(areaType) {
		case '0':
			var th = $(obj).prev();
			if ($(th).attr("colspan") == undefined || $(th).attr("colspan") == '' || $(th).attr("colspan") == '0' || $(th).attr("colspan") == '1') {
				
				var thNew = returnElementTHAddEntity(data, "false");
				$(th).empty();
				$(th).append(thNew);
				
				$(th).find(".glyphicon-screenshot").draggable({
					containment: '#' + tableId,
					stack: '#' + tableId,
					revert: function(event, ui) {
							if ($(this).data("uiDraggable") != undefined && $(this).data("uiDraggable").originalPosition != undefined) {
								$(this).data("uiDraggable").originalPosition = {top : 0,left : 0};
							}	
							return true;				
						},
					stop: function(event, ui) {
			  	  	$(this).css("z-index","auto");
			  	  } 
				});
				
				$(th).find(".glyphicon-screenshot").droppable({
					accept: "#" + tableId + " tr:not(:first) .glyphicon-screenshot",
					activeClass: "state-droppable",
					drop: function(event, ui) {
						replaceElement(event, ui, $(this));
					}
				});
				
			}
			break;
		case '1':
			
			break;
		default:
			break;
		}
		
		
	}
	
	var tdNew = $(elementTD).attr("index", index);

	
	if (index == 0) {
		$(tdNew).attr("style", "text-align: left;");
	}
	
	var enableGroup = $(obj).closest("td").find("[name='enableGroup']").val();
	if (enableGroup != undefined) {		
		if (enableGroup != "true") {			
			if ($(table).attr("class") == "table table-bordered qp-table-list-none-action") {
				var widthTd = $(td).width();
				
				var element =  returnElement(div);	
				var elementNew = $(element);
				$(elementNew).css("width", 100 + "%");
				$(elementNew).css("float", "left");
				$(elementNew).css("text-align", "left");
				$(obj).replaceWith(elementNew);
				
			} else {
				$(td).replaceWith(tdNew);
			}
		} else {
			var area = $(obj).closest(".areaContent");
			var areaType = $(obj).closest(".areaContent").find("input[name=formAreaType]").val();
			
			
			
			var colspan = "";
			var rowspan = "";
			$(obj).closest("td").find("input[name=formElement]").each(function(){
				var value = $(this).val();
				var dataJson = convertToJson(value);
				
				if (dataJson.colspan != undefined && dataJson.colspan != null) {
					colspan = dataJson.colspan;
				} 
				
				if (dataJson.rowspan != undefined && dataJson.rowspan != null) {
					rowspan = dataJson.rowspan;
				}
			});
			
			if (areaType != undefined && areaType != null && (areaType == "0" || areaType == 0)) {
				//autogen item code, item name follow static label
				var label = $(obj).closest("td").prev("th").find("input[name=formElement]");
				var labelJson = convertToJson($(label).val());
			}
			
			var isJapanCode = false;
			if (labelJson != undefined && labelJson != null && labelJson.labelText != undefined && labelJson.labelText != null && labelJson.labelText.length > 0) {
				var regex = /[\u3000-\u303F]|[\u3040-\u309F]|[\u30A0-\u30FF]|[\uFF00-\uFFEF]|[\u4E00-\u9FAF]|[\u2605-\u2606]|[\u2190-\u2195]|\u203B/g; 
				
				if(regex.test(labelJson.labelText)) {
					isJapanCode = true;
				}
			}
			
			var labelTextStore = $(div).attr("columnname");
			var columnNameStore = $(div).attr("columnname");
			
			if (areaType == "1" || areaType == 1 || isJapanCode ||  labelJson.labelText == undefined || labelJson.labelText == null || labelJson.labelText.length == 0) {
				//default lable text, case is user drap column
				if ($(div).attr("data") != undefined && $(div).attr("data") != null && $(div).attr("data").length > 0) {
					var data = convertToJson($(div).attr("data"));
					if (data != undefined && data != null && data.labelText != undefined) {
						$(div).attr("labelText", data.labelText);
					}
				} else {
					if ($(div).attr("datatype") != 11 && $(div).attr("datatype") != 13) {
						
						if (areaType == 0) {
							
							var textLabel =  $(div).attr("columnname");
							
							if ($(area).find("input[name=formElement]").length > 0) {
								var max = 0;
								var isExists = false;
								
								$(area).find("input[name=formElement]").each(function() {
									var elementJson = convertToJson($(this).val());
									var itemCode =elementJson.columnname; 
									
									if (itemCode != undefined && itemCode != null && itemCode.indexOf(textLabel) != -1) {
										isExists = true;
										var order = itemCode.replace(textLabel, "");
										try {
											var orderItem = parseInt(order); 
											if (max < orderItem)
											{
												max = orderItem;
											}
										}catch(err) {
											
										}
										
									}
								});
								
								var labelItem = labelTextStore;
								if (max == 0) {
									if (isExists) {
										max++;
										textLabel = textLabel + max;
										labelItem = labelItem + max;
									}
								} else {
									max++;
									textLabel = textLabel + max;
									labelItem = labelItem + max;
								}
								
								$(div).attr("labelText", labelItem);
								$(div).attr("columnname", textLabel);
							} else {
								$(div).attr("labelText", data.labelText);
								$(div).attr("columnname", textLabel);
							}
							
						}
					}
				}
			} else {
				if($(obj).prop("tagName") == "TD") {
					var parent = $(obj);
				} else {
					var parent = $(obj).closest("td");
				}
				
				var countElement = 0;
				$(parent).find("input[name=formElement]").each(function() {
					var dataJson = convertToJson($(this).val());
					if (dataJson != undefined && dataJson != null && dataJson.columnname != undefined && dataJson.columnname != null && dataJson.columnname.length > 0) {
						countElement++;
						return false;
					}
				});
				
				if (countElement > 0) {
					if ($(div).attr("datatype") != 11 && $(div).attr("datatype") != 13) {
						
						if (areaType == 0) {
							
							var textLabel =  $(div).attr("columnname");
							
							if ($(area).find("input[name=formElement]").length > 1) {
								var max = 0;
								var isExists = false;
								
								$(area).find("input[name=formElement]").each(function() {
									var elementJson = convertToJson($(this).val());
									var itemCode =elementJson.columnname; 
									
									if (itemCode != undefined && itemCode != null && itemCode.indexOf(textLabel) != -1) {
										isExists = true;
										var order = itemCode.replace(textLabel, "");
										try {
											var orderItem = parseInt(order); 
											if (max < orderItem)
											{
												max = orderItem;
											}
										}catch(err) {
											
										}
										
									}
								});
								
								var labelItem = labelTextStore;
								if (max == 0) {
									if (isExists) {
										max++;
										textLabel = textLabel + max;
										labelItem = labelItem + max;
									}
								} else {
									max++;
									textLabel = textLabel + max;
									labelItem = labelItem + max;
								}
								
								$(div).attr("labelText", labelItem);
								$(div).attr("columnname", textLabel);
							} else {
								$(div).attr("labelText", data.labelText);
								$(div).attr("columnname", textLabel);
							}
							
						}
					}
				} else {
					var text = labelJson.labelText.capitalize();
					
					$(div).attr("labelText", labelJson.labelText);
					$(div).attr("columnname", text);
					
				}
			}
			
			if ($(div).attr("labelText") == undefined || $(div).attr("labelText") == null || $(div).attr("labelText").length == 0) {
				$(div).attr("labelText", $(div).attr("columnname"));
			}
			
			var element =  returnElement(div);	
			
			
			if(tableType != undefined && tableType == "search"){
				element = returnElementSearch(div);
			}
			
			$(obj).append(element);
			//remove dropComponent
			$(obj).closest("td").find(".dropComponent").remove();
			
			if (labelTextStore == undefined) {
				labelTextStore = "";
			}
			
			$(div).attr("labelText", labelTextStore);
			$(div).attr("columnname", columnNameStore);
			
			$(obj).closest("td").find("input[name=formElement]").each(function(){
				var value = $(this).val();
				var dataJson = convertToJson(value);
				if (dataJson.colspan != undefined) {
					dataJson.colspan = colspan;
				} else {
					dataJson['colspan'] = colspan;
				}
				
				if (dataJson.rowspan != undefined) {
					dataJson.rowspan = rowspan;
				} else {
					dataJson['rowspan'] = rowspan;
				}
				
				$(this).val(convertToString(dataJson));
			});
			
			$(obj).children().css("padding-right", "3px");
			$(obj).children().css("padding-bottom", "3px");
			$(obj).children().css("float", "left");			
		}
	} else {
		if ($(table).attr("class") == "table table-bordered qp-table-list-none-action") {
			var element =  returnElement(div);			
			$(obj).replaceWith(element);
		} else {
			$(td).replaceWith(tdNew);
		}
		
	}		
	
	reloadStyle($(table));
	$.qp.initialAllPicker($(table));
	
	$.qp.initialAutoNumeric($(td));
		
	initBlurEventElement();
}

function returnElementSearch(div){
	var dataType = $(div).attr('datatype');
		
		var elementTD = "";
		var tablecolumncode = "";
		if($(div).attr('tablecolumncode') != undefined){
			tablecolumncode = $(div).attr('tablecolumncode');
		}
		var tablename = "";
		if($(div).attr('tablename') != undefined){
			tablename = $(div).attr('tablename');
		}
		var tablecode = "";
		if($(div).attr('tablecode') != undefined){
			tablecode = $(div).attr('tablecode');
		}
		var tablecolumnname = "";
		if($(div).attr('tablecolumnname') != undefined){
			tablecolumnname = $(div).attr('tablecolumnname');
		}
		
		var physicaldatatype = "";
		if($(div).attr('physicaldatatype') != undefined){
			physicaldatatype = $(div).attr('physicaldatatype');
		}
		var minvalue = "";
		if($(div).attr('minvalue') != undefined){
			minvalue = $(div).attr('minvalue');
		}
		var maxvalue = "";
		if($(div).attr('maxvalue') != undefined){
			maxvalue = $(div).attr('maxvalue');
		}
		var formatcode = "";
		if($(div).attr('formatcode') != undefined){
			formatcode = $(div).attr('formatcode');
		}
		var physicalmaxlength = "";
		if($(div).attr('physicalmaxlength') != undefined){
			physicalmaxlength = $(div).attr('physicalmaxlength');
		}
		var maxlength = "";
		if($(div).attr('maxlength') != undefined){
			maxlength = $(div).attr('maxlength');
		}
		var require = "";
		if($(div).attr('require') != undefined){
			require = $(div).attr('require');
		}
		var rowspan = "";
		if($(div).attr('rowspan') != undefined){
			rowspan = $(div).attr('rowspan');
		}
		var colspan = "";
		if($(div).attr('colspan') != undefined){
			colspan = $(div).attr('colspan');
		}
		var datasource = "";
		if($(div).attr('datasource') != undefined){
			datasource = $(div).attr('datasource');
		}
		var msglabel = "";
		if($(div).attr('msglabel') != undefined){
			msglabel = $(div).attr('msglabel');
		}
		
		var localCodeList = "";
		if($(div).attr('localCodelist') != undefined) {
			localCodeList = $(div).attr('localCodelist');
		} else {
			localCodeList = 3;
		}
		
		var dataSourceType = "";
		if($(div).attr('datasourcetype') != undefined) {
			dataSourceType = $(div).attr('datasourcetype');
		} else {
			dataSourceType = 2;
		}
		
		var parameters = "";
		if ($(div).attr('parameters') == undefined || $(div).attr('parameters') == null || $(div).attr('parameters').length == 0) {
			if (dataType == 5 || dataType == 6 || dataType == 7) {
				parameters = "option1π1�option2π2�";
				$(div).attr('msgvalue',"1�2");
				msglabel = "option1�option2";
				localCodelist = 3;
				datasourcetype = 2;
			} else {
				datasourcetype = checkUndefined($(div).attr('datasourcetype'));
			}
		} else {
			parameters = $(div).attr('parameters');
		}
		var element =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""+$(div).attr('label')
			+ "\",\"datatype\":\""+$(div).attr('datatype')
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+$(div).attr('columnname')
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\""+parameters
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"dialogAutocompleteCode\":\""+""
			+ "\",\"dialogAutocompleteText\":\""+""
			+ "\",\"dialogOnChangeEvent\":\""+""
			+ "\",\"dialogOnSelectEvent\":\""+""
			+ "\",\"navigateTo\":\""
			+ "\",\"navigateToText\":\""
			+ "\",\"codelistCode\":\""+$(div).attr('codelistCode')
			+ "\",\"codelistText\":\""+$(div).attr('codelistText')
			+ "\",\"localCodelist\":\""+localCodeList
			+ "\",\"isSupportOptionValue\":\""+$(div).attr('isSupportOptionValue')
			+ "\",\"datasourcetype\":\""+dataSourceType
			+ "\",\"sqldesignid\":\""+$(div).attr('sqldesignid')
			+ "\",\"sqldesignidtext\":\""+$(div).attr('sqldesignidtext')
			+ "\",\"optionlabel\":\""+$(div).attr('optionlabel')
			+ "\",\"optionlabeltext\":\""+$(div).attr('optionlabeltext')
			+ "\",\"optionvalue\":\""+$(div).attr('optionvalue')
			+ "\",\"labelText\":\""+ $(div).attr("labelText")
			+ "\",\"optionvaluetext\":\""+$(div).attr('optionvaluetext')
			+ "\",\"displayFromTo\":\""+checkUndefinedFromTo($(div).attr('displayFromTo'))
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
			if($(div).attr('msgvalue') != undefined){
				element += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
			} else {
				element += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			element += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
			
		if(dataType == 0){
			elementTD += "<span>" +
					"<div ondblclick=\"openDialogAutocompleteSetting(this)\" class=\"input-group\" style=\"width:100%\"><input type=\"text\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += " class=\"form-control ui-autocomplete-input\" ";
					} else {
						elementTD += " class=\"qp-input-autocomplete\" ";
					}
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength')
					+		" optionvalue=\"optionValue\" optionlabel=\"optionLabel\" selectsqlid=\"\" emptylabel=\"\" onselectevent=\"\" onchangeevent=\"\" onremoveevent=\"\" mustmatch=\"true\" minlength=\"0\" arg01=\"\" arg02=\"\" arg03=\"\" arg4=\"\" arg05=\"\" arg06=\"\" arg07=\"\" arg08=\"\" arg09=\"\" arg10=\"\" arg11=\"\" arg12=\"\" arg13=\"\" arg14=\"\" arg15=\"\" arg16=\"\" arg17=\"\" arg18=\"\" arg19=\"\" arg20=\"\" placeholder=\"\" autocomplete=\"off\">"
					+ "	<input type=\"hidden\" value=\"\">" 
					+ "	<span class=\"input-group-addon dropdown-toggle\" data-dropdown=\"dropdown\">" 
					+ "		<span class=\"caret\"></span> " 
					+ "	</span>"
					+ "</div>"+element+"</span>"
					"";		
		}
		if(dataType == 1 || dataType == 15 || dataType == 16 || dataType == 18){
			elementTD += "<span style=\"width: 100%;\"><input ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
						if($(div).attr('elementtype') == 0){
							elementTD += "class=\"form-control qp-input-text\"";
						} else {
							elementTD += "class=\"form-control qp-input-text-detail\"";
						}
						elementTD += " name=\"" 
						+ $(div).attr('columnname') + "\" maxlength=\"" 
						+ $(div).attr('maxlength') + "\" />"+element+"</span>";
		}
		if(dataType == 21){
			elementTD += "<span style=\"width: 100%;\"><Label ondblclick=\"openDialogComponentSetting(this)\" ";					
						elementTD += " name=\"" 
						+ $(div).attr('columnname') + "\" maxlength=\"" 
						+ $(div).attr('maxlength') + "\" >"+$(div).attr('columnname')+"</label>"+element+"</span>";
		}
		if(dataType == 2){
			elementTD += "<span style=\"width: 100%;\"><div  ondblclick=\"openDialogComponentSetting(this)\" style=\"width: 100%;\">" +
					"<input type=\"text\" ";
						if($(div).attr('elementtype') == 0){
							elementTD += "class=\"form-control qp-input-from pull-left\"";
						} else {
							elementTD += "class=\"com-input-integer-detail\"";
						}
						elementTD += " name=\"" 
						+ $(div).attr('columnname') + "\" maxlength=\"" 
						+ $(div).attr('maxlength') + "\" />" +
						"" + "<div class=\"qp-separator-from-to\">~</div>" +
						"<input  ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
						if($(div).attr('elementtype') == 0){
							elementTD += "class=\"form-control qp-input-to pull-right\"";
						} else {
							elementTD += "class=\"com-input-integer-detail\"";
						}
						elementTD += " name=\"" 
						+ $(div).attr('columnname') + "\" maxlength=\"" 
						+ $(div).attr('maxlength') + "\" />" +
							
					""+element+"</div></span>";
		}
		if(dataType == 3){
			elementTD += "<span style=\"width: 100%;\"><div ondblclick=\"openDialogComponentSetting(this)\" style=\"width: 100%;\">" +
						"<input type=\"text\" ";
						if($(div).attr('elementtype') == 0){
							elementTD += "class=\"form-control qp-input-from pull-left\"";
						} else {
							elementTD += "class=\"com-input-integer-detail\"";
						}
						elementTD += " name=\"" 
						+ $(div).attr('columnname') + "\" maxlength=\"" 
						+ $(div).attr('maxlength') + "\" />" +
						"" + "<div class=\"qp-separator-from-to\">~</div>" +
						"<input  ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
						if($(div).attr('elementtype') == 0){
							elementTD += "class=\"form-control qp-input-to pull-right\"";
						} else {
							elementTD += "class=\"com-input-integer-detail\"";
						}
						elementTD += " name=\"" 
						+ $(div).attr('columnname') + "\" maxlength=\"" 
						+ $(div).attr('maxlength') + "\" />" +
							
					""+element+"</div></span>";
		}
		if(dataType == 8){
				elementTD += "<span style=\"width: 100%;\"><div ondblclick=\"openDialogComponentSetting(this)\" style=\"width: 100%;\">" +
						"<input type=\"text\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"form-control qp-input-from pull-left\"";
				} else {
					elementTD += "class=\"com-input-currency-detail\"";
				}
				elementTD += " name=\"" 
				+ $(div).attr('columnname') + "1\" maxlength=\"" 
				+ $(div).attr('maxlength') + "\" />" +
				"" + "<div class=\"qp-separator-from-to\">~</div>" +
				"<input ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"form-control qp-input-to pull-right\"";
				} else {
					elementTD += "class=\"com-input-currency-detail\"";
				}
				elementTD += " name=\"" 
				+ $(div).attr('columnname') + "1\" maxlength=\"" 
				+ $(div).attr('maxlength') + "\" />" +
					
				""+element+"</div></span>";
		}
		if(dataType == 4){
			elementTD += "<span style=\"width: 100%;\"><div ondblclick=\"openDialogComponentSetting(this)\">" +
			"<div class=\"input-group date qp-input-from pull-left\">";
			elementTD += "<input  type=\"text\" ";
							if($(div).attr('elementtype') == 0){
								elementTD += "class=\"form-control\"";
							} else {
								elementTD += "class=\"form-control \"";
							}
			elementTD += " name=\"" 
							+ $(div).attr('columnname') + "\" maxlength=\"" 
							+ $(div).attr('maxlength') + "\" />";
			elementTD += "<span class=\"input-group-addon\">"
							+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
							+ "</span>"
						+ "</div>"
			+ "<div class=\"qp-separator-from-to\">~</div>" +
			"<div class=\"input-group date qp-input-to pull-right\">";
			elementTD += "<input  type=\"text\" ";
							if($(div).attr('elementtype') == 0){
								elementTD += "class=\"form-control\"";
							} else {
								elementTD += "class=\"form-control \"";
							}
			elementTD += " name=\"" 
							+ $(div).attr('columnname') + "\" maxlength=\"" 
							+ $(div).attr('maxlength') + "\" />";
			elementTD += "<span class=\"input-group-addon\">"
							+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
							+ "</span>"
						+ "</div>"
						;
			elementTD += "</div>"+element+"</span>";
		}
		if(dataType == 14){
			
			elementTD += "<span style=\"width: 100%;\"><div ondblclick=\"openDialogComponentSetting(this)\">" +
					"<div class=\"input-group date qp-input-from pull-left\">";
			elementTD += "<input  type=\"text\" ";
							if($(div).attr('elementtype') == 0){
								elementTD += "class=\"form-control\"";
							} else {
								elementTD += "class=\"form-control \"";
							}
			elementTD += " name=\"" 
							+ $(div).attr('columnname') + "\" maxlength=\"" 
							+ $(div).attr('maxlength') + "\" />";
			elementTD += "<span class=\"input-group-addon\">"
							+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
							+ "</span>"
						+ "</div>"
			+ "<div class=\"qp-separator-from-to\">~</div>" +
			"<div class=\"input-group date qp-input-to pull-right\">";
			elementTD += "<input  type=\"text\" ";
							if($(div).attr('elementtype') == 0){
								elementTD += "class=\"form-control\"";
							} else {
								elementTD += "class=\"form-control \"";
							}
			elementTD += " name=\"" 
							+ $(div).attr('columnname') + "\" maxlength=\"" 
							+ $(div).attr('maxlength') + "\" />";
			elementTD += "<span class=\"input-group-addon\">"
							+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
							+ "</span>"
						+ "</div>"
						;
			elementTD += "</div>"+element+"</span>";
		}
		if(dataType == 9){
			
			elementTD += "<span style=\"width: 100%;\"><div ondblclick=\"openDialogComponentSetting(this)\">" +
			"<div class=\"input-group date qp-input-from pull-left\">";
			elementTD += "<input  type=\"text\" ";
							if($(div).attr('elementtype') == 0){
								elementTD += "class=\"form-control\"";
							} else {
								elementTD += "class=\"form-control \"";
							}
			elementTD += " name=\"" 
							+ $(div).attr('columnname') + "\" maxlength=\"" 
							+ $(div).attr('maxlength') + "\" />";
			elementTD += "<span class=\"input-group-addon\">"
							+ "<span class=\"glyphicon glyphicon-time\"></span>"
							+ "</span>"
						+ "</div>"
			+ "<div class=\"qp-separator-from-to\">~</div>" +
			"<div class=\"input-group date qp-input-to pull-right\">";
			elementTD += "<input  type=\"text\" ";
							if($(div).attr('elementtype') == 0){
								elementTD += "class=\"form-control\"";
							} else {
								elementTD += "class=\"form-control \"";
							}
			elementTD += " name=\"" 
							+ $(div).attr('columnname') + "\" maxlength=\"" 
							+ $(div).attr('maxlength') + "\" />";
			elementTD += "<span class=\"input-group-addon\">"
							+ "<span class=\"glyphicon glyphicon-time\"></span>"
							+ "</span>"
						+ "</div>"
						;
			elementTD += "</div>"+element+"</span>";
		}
		if(dataType == 5){
			if( $(div).attr('msgvalue') != undefined && $(div).attr('msgvalue').length > 0){
				var msgLabelArr = $(div).attr('msglabel').split("�");
				var msgValArr = $(div).attr('msgvalue').split("�");
				elementTD += "<span ondblclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\">";
				$(msgValArr).each(function(j){
					elementTD += "<input  type=\"radio\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
					} else {
						elementTD += "class=\"com-input-radio-detail\"";
					}
					if($(div).attr('msglabel') != undefined && $(div).attr('msglabel').length == 0){					
						elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+msgValArr[j]+"</label>";
					} else {					
						elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+msgLabelArr[j]+"</label>";
					}
				});
				elementTD += ""+element+"</span>";
			} else {
				elementTD += "<span ondblclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\"><input type=\"radio\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
					} else {
						elementTD += "class=\"com-input-radio-detail\"";
					}
				elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
				elementTD += "<input type=\"radio\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
					} else {
						elementTD += "class=\"com-input-radio-detail\"";
					}
				elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>"+element+"</span>";
			}
		}
		if(dataType == 6){
			if($(div).attr('msgvalue') != undefined && $(div).attr('msgvalue').length > 0){
				var msgLabelArr = $(div).attr('msglabel').split("�");
				var msgValArr = $(div).attr('msgvalue').split("�");
				elementTD += "<span ondblclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\">";
				$(msgValArr).each(function(j){
					elementTD += "<input type=\"checkbox\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
					} else {
						elementTD += "class=\"com-input-checkbox-detail\"";
					}				
					if($(div).attr('msglabel') != undefined && $(div).attr('msglabel').length == 0){					
						elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+msgValArr[j]+"</label>";
					} else {					
						elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+msgLabelArr[j]+"</label>";
					}				
				});
				elementTD += ""+element+"</span>";
			} else {
				elementTD += "<span ondblclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\"><input type=\"checkbox\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
					} else {
						elementTD += "class=\"com-input-checkbox-detail\"";
					}
				elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
				elementTD += "<input type=\"checkbox\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
					} else {
						elementTD += "class=\"com-input-checkbox-detail\"";
					}
				elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>"+element+"</span>";
			}
		}
		if(dataType == 7){
			elementTD += "<span style=\"width: 100%;\"><select ondblclick=\"openDialogComponentListSetting(this)\" name=\""+$(div).attr('columnname')+"\" ";
			if($(div).attr('elementtype') == 0){
				elementTD += "class=\"form-control qp-input-select\"";
			} else {
				elementTD += "class=\"form-control qp-input-select-detail\"";
			}
			elementTD += ">";
			if($(div).attr('msgvalue') != undefined && $(div).attr('msgvalue').length > 0){
				var msgLabelArr = $(div).attr('msglabel').split("�");
				var msgValArr = $(div).attr('msgvalue').split("�");
				
				$(msgValArr).each(function(j){
					if($(div).attr('msglabel') != undefined && $(div).attr('msglabel').length == 0){
						elementTD += "<option>" + msgValArr[j] + "</option>";
					} else {
						elementTD += "<option>" + msgLabelArr[j] + "</option>";
					}
				});
			} else {
				elementTD += "<option>"+dbMsgSource['sc.screendesign.0137']+"1</option><option>"+dbMsgSource['sc.screendesign.0137']+"2</option>";
			}
			elementTD += "</select>"+element+"</span>";
		}
		if(dataType == 10){
			elementTD += "<span><textarea ondblclick=\"openDialogComponentSetting(this)\" ";
						if($(div).attr('elementtype') == 0){
							elementTD += "class=\"form-control qp-input-textarea\"";
						} else {
							elementTD += "class=\"form-control qp-input-textarea-detail\"";
						}
						elementTD += " name=\"" 
						+ $(div).attr('columnname') + "1\" maxlength=\"" 
						+ $(div).attr('maxlength') + "\" ></textarea>"+element+"</span>";
		}
		if(dataType == 11){	
			
			var elementButton =  "" 
				+ "<input type=\"hidden\" name=\"formElement\" "
				+ "value='\"label\":\""+""
				+ "\",\"datatype\":\""+$(div).attr('datatype')
				+ "\",\"physicaldatatype\":\""+physicaldatatype
				+ "\",\"columnname\":\""+$(div).attr('columnname')
				+ "\",\"rowspan\":\""+rowspan
				+ "\",\"colspan\":\""+colspan
				+ "\",\"datasource\":\""+datasource
				+ "\",\"minvalue\":\""+minvalue
				+ "\",\"maxvalue\":\""+maxvalue
				+ "\",\"formatcode\":\""+formatcode
				+ "\",\"tablename\":\""+tablename
				+ "\",\"tablecode\":\""+tablecode
				+ "\",\"tablecolumnname\":\""+tablecolumnname
				+ "\",\"tablecolumncode\":\""+tablecolumncode
				+ "\",\"connectionmsg\":\""
				+ "\",\"transitiontype\":\""
				+ "\",\"actiontype\":\""
				+ "\",\"parameters\":\""
				+ "\",\"screenactionid\":\""
				+ "\",\"linklabel\":\""
				+ "\",\"toscreenid\":\""
				+ "\",\"maxlength\":\""+maxlength
				+ "\",\"labelText\":\""+$(div).attr("labeltext")
				+ "\",\"navigateTo\":\""
				+ "\",\"navigateToText\":\""
				+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
			
			
				if($(div).attr('msgvalue') != undefined){
					elementButton += "\",\"msglabel\":\""+msglabel
					+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
				} else {
					elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
				}
				
				elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
			elementTD += "<span><a ondblclick=\"openDialogLinkAreaSetting(this);\" href=\"javascript:\">"+$(div).attr('labelText')+"</a>"+elementButton+"</span>";
		}
		
		if(dataType == 22){
			var elementButton =  "" 
				+ "<input type=\"hidden\" name=\"formElement\" "
				+ "value='\"label\":\""
				+ "\",\"datatype\":\""+$(div).attr('datatype')
				+ "\",\"physicaldatatype\":\""+physicaldatatype
				+ "\",\"columnname\":\""+$(div).attr('columnname')
				+ "\",\"rowspan\":\""+rowspan
				+ "\",\"colspan\":\""+colspan
				+ "\",\"datasource\":\""+datasource
				+ "\",\"minvalue\":\""+minvalue
				+ "\",\"maxvalue\":\""+maxvalue
				+ "\",\"formatcode\":\""+formatcode
				+ "\",\"tablename\":\""+tablename
				+ "\",\"tablecode\":\""+tablecode
				+ "\",\"tablecolumnname\":\""+tablecolumnname
				+ "\",\"tablecolumncode\":\""+tablecolumncode
				+ "\",\"connectionmsg\":\""
				+ "\",\"transitiontype\":\""
				+ "\",\"actiontype\":\""
				+ "\",\"parameters\":\""
				+ "\",\"screenactionid\":\""
				+ "\",\"linklabel\":\""
				+ "\",\"toscreenid\":\""
				+ "\",\"maxlength\":\""+maxlength
				+ "\",\"labelText\":\""+$(div).attr("labeltext")
				+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
			
			
				if($(div).attr('msgvalue') != undefined){
					elementButton += "\",\"msglabel\":\""+msglabel
					+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
				} else {
					elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
				}
				
				elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
			elementTD += "<span style=\"width: 100%;\"><a ondblclick=\"openDialogLinkAreaSetting(this);\" href=\"javascript:\">"+$(div).attr('labelText')+"</a>"+elementButton+"</span>";
		}
		
		if(dataType == 23){
			var elementLabel =  "" 
				+ "<input type=\"hidden\" name=\"formElement\" "
				+ "value='\"label\":\""
				+ "\",\"datatype\":\""+$(div).attr('datatype')
				+ "\",\"physicaldatatype\":\""+physicaldatatype
				+ "\",\"columnname\":\""+$(div).attr('columnname')
				+ "\",\"rowspan\":\""+rowspan
				+ "\",\"colspan\":\""+colspan
				+ "\",\"datasource\":\""+datasource
				+ "\",\"minvalue\":\""+minvalue
				+ "\",\"maxvalue\":\""+maxvalue
				+ "\",\"formatcode\":\""+formatcode
				+ "\",\"tablename\":\""+tablename
				+ "\",\"tablecode\":\""+tablecode
				+ "\",\"tablecolumnname\":\""+tablecolumnname
				+ "\",\"tablecolumncode\":\""+tablecolumncode
				+ "\",\"connectionmsg\":\""
				+ "\",\"transitiontype\":\""
				+ "\",\"actiontype\":\""
				+ "\",\"parameters\":\""
				+ "\",\"screenactionid\":\""
				+ "\",\"linklabel\":\""
				+ "\",\"toscreenid\":\""
				+ "\",\"maxlength\":\""+maxlength
				+ "\",\"labelText\":\""+$(div).attr("labelText")
				+ "\",\"isBundle\":\""+ "false"
				+ "\",\"isSubmit\":\""+ "false"
				+ "\",\"navigateTo\":\""
				+ "\",\"navigateToText\":\""
				+ "\",\"customItemContent\":\""
				+ "\",\"itemType\":\"5"
				+ "\",\"screenItemId\":\""+$(div).attr("screenItemId")
				+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
			
			
			if($(div).attr('msgvalue') != undefined){
				elementLabel += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
			} else {
				elementLabel += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementLabel += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
			elementTD += "<span><label class=\"qp-item-custom\" ondblclick=\"openDialogCustomSetting(this, true)\" style=\"cursor: pointer;\">"+$(div).attr('labelText')+"</label>"+elementLabel+"</span>";
		}	
		
		if(dataType == 12){
			elementTD += "<span><input onclick=\"event.stopPropagation();return false;\" ondblclick=\"event.stopPropagation();openDialogComponentSetting(this);return false;\" type=\"file\" name=\"" + $(div).attr('columnname') + "\" ";
						if($(div).attr('elementtype') == 0){
							elementTD += "class=\"qp-input-file\"";
						} else {
							elementTD += "class=\"com-input-file-detail\"";
						}
			elementTD += "/>"+element+"</span>";
		}
		/*var width = ""
		if($(div).attr("tablewidth") != undefined) {
			width = $(div).attr("tablewidth");
		} else {
			width = "100" ;
		}*/
		if(dataType == 13){
			var elementButton =  "" 
				+ "<input type=\"hidden\" name=\"formElement\" "
				+ "value='\"label\":\""
				+ "\",\"datatype\":\""+$(div).attr('datatype')
				+ "\",\"physicaldatatype\":\""+physicaldatatype
				+ "\",\"columnname\":\""+$(div).attr('columnname')
				+ "\",\"rowspan\":\""+rowspan
				+ "\",\"colspan\":\""+colspan
				+ "\",\"datasource\":\""+datasource
				+ "\",\"minvalue\":\""+minvalue
				+ "\",\"maxvalue\":\""+maxvalue
				+ "\",\"formatcode\":\""+formatcode
				+ "\",\"tablename\":\""+tablename
				+ "\",\"tablecode\":\""+tablecode
				+ "\",\"tablecolumnname\":\""+tablecolumnname
				+ "\",\"tablecolumncode\":\""+tablecolumncode
				+ "\",\"connectionmsg\":\""
				+ "\",\"transitiontype\":\""
				+ "\",\"actiontype\":\""
				+ "\",\"parameters\":\""
				+ "\",\"screenactionid\":\""
				+ "\",\"linklabel\":\""
				+ "\",\"toscreenid\":\""
				+ "\",\"maxlength\":\""+maxlength
				+ "\",\"labelText\":\""+$(div).attr("labeltext")
				+ "\",\"navigateTo\":\""
				+ "\",\"navigateToText\":\""
				+ "\",\"width\":\""
				+ "\",\"widthunit\":\""
				+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
			
			
			if($(div).attr('msgvalue') != undefined){
				elementButton += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
			} else {
				elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
			
			elementTD += "<span><button style=\"width: 100%;\" type=\"button\" class=\"btn qp-button\" ondblclick=\"openDialogButtonAreaSetting(this)\" value=\""+$(div).attr('labelText')+"\">"+$(div).attr('labelText')+"</button>"+elementButton+"</span>";
		}
		if(dataType == 20){
			var elementLabel =  "" 
				+ "<input type=\"hidden\" name=\"formElement\" "
				+ "value='\"label\":\""
				+ "\",\"datatype\":\""+$(div).attr('datatype')
				+ "\",\"physicaldatatype\":\""+physicaldatatype
				+ "\",\"columnname\":\""+$(div).attr('columnname')
				+ "\",\"rowspan\":\""+rowspan
				+ "\",\"colspan\":\""+colspan
				+ "\",\"datasource\":\""+datasource
				+ "\",\"minvalue\":\""+minvalue
				+ "\",\"maxvalue\":\""+maxvalue
				+ "\",\"formatcode\":\""+formatcode
				+ "\",\"tablename\":\""+tablename
				+ "\",\"tablecode\":\""+tablecode
				+ "\",\"tablecolumnname\":\""+tablecolumnname
				+ "\",\"tablecolumncode\":\""+tablecolumncode
				+ "\",\"connectionmsg\":\""
				+ "\",\"transitiontype\":\""
				+ "\",\"actiontype\":\""
				+ "\",\"parameters\":\""
				+ "\",\"screenactionid\":\""
				+ "\",\"linklabel\":\""
				+ "\",\"toscreenid\":\""
				+ "\",\"maxlength\":\""+maxlength
				+ "\",\"labelText\":\""+$(div).attr("label")
				+ "\",\"isBundle\":\""+ "false"
				+ "\",\"isSubmit\":\""+ "false"
				+ "\",\"navigateTo\":\""
				+ "\",\"navigateToText\":\""
				+ "\",\"itemType\":\"5"
				+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
			
			
			if($(div).attr('msgvalue') != undefined){
				elementLabel += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
			} else {
				elementLabel += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementLabel += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
	elementTD += "<span><label ondblclick=\"openDialogLableSetting(this, true)\" style=\"cursor: pointer;\">"+$(div).attr('label')+"</label>"+elementLabel+"</span>";
		}
		
		return elementTD;
}

function insertElementList(ev, ui, obj){
	var div = $(ui.draggable);
	var tr = $(obj).closest('tr');
	var trIndex = $(tr).attr('index');
	var table = $(obj).closest('table');
	var tableId = $(table).attr("id");
	var index = $(obj).parent('th').attr('index');
	
	var tablecolumncode = "";
	if($(div).attr('tablecolumncode') != undefined){
		tablecolumncode = $(div).attr('tablecolumncode');
	}
	var tablename = "";
	if($(div).attr('tablename') != undefined){
		tablename = $(div).attr('tablename');
	}
	var tablecode = "";
	if($(div).attr('tablecode') != undefined){
		tablecode = $(div).attr('tablecode');
	}
	var tablecolumnname = "";
	if($(div).attr('tablecolumnname') != undefined){
		tablecolumnname = $(div).attr('tablecolumnname');
	}
	
	var physicaldatatype = "";
	if($(div).attr('physicaldatatype') != undefined){
		physicaldatatype = $(div).attr('physicaldatatype');
	}
	var minvalue = "";
	if($(div).attr('minvalue') != undefined){
		minvalue = $(div).attr('minvalue');
	}
	var maxvalue = "";
	if($(div).attr('maxvalue') != undefined){
		maxvalue = $(div).attr('maxvalue');
	}
	var formatcode = "";
	if($(div).attr('formatcode') != undefined){
		formatcode = $(div).attr('formatcode');
	}
	var physicalmaxlength = "";
	if($(div).attr('physicalmaxlength') != undefined){
		physicalmaxlength = $(div).attr('physicalmaxlength');
	}
	var require = "";
	if($(div).attr('require') != undefined){
		require = $(div).attr('require');
	}
	var rowspan = "";
	if($(div).attr('rowspan') != undefined){
		rowspan = $(div).attr('rowspan');
	}
	var colspan = "";
	if($(div).attr('colspan') != undefined){
		colspan = $(div).attr('colspan');
	}
	var datasource = "";
	if($(div).attr('datasource') != undefined){
		datasource = $(div).attr('datasource');
	}
	
	var elementTH = "<th index=\""+index+"\"><span style=\"float: left; cursor: move;\" class=\"qp-glyphicon glyphicon glyphicon-screenshot\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></span>"
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""+$(div).attr('label')
			+ "\",\"datatype\":\""+$(div).attr('datatype')
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+$(div).attr('columnname')
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"datasource\":\""+datasource
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"toscreenid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"parameters\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"connectionmsg\":\""
			+ "\",\"navigateto\":\""
			+ "\",\"maxlength\":\""+$(div).attr('maxlength')
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
			if($(div).attr('msgvalue') != undefined){
				elementTH += "\",\"msglabel\":\""+$(div).attr('msglabel')
					+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
			} else {
				elementTH += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			elementTH += "\",\"require\":\""+require+"\",\"elementtype\":\"1\"'>"
			+ "<label onclick=\"openDialogSetting(this)\" style=\"cursor: pointer;\">"
			+ $(div).attr('label') + "</label>";
	if($(div).attr('require') == 1){
		elementTH += "<label class=\"qp-required-field\"> "+fcomMsgSource['sc.sys.0029']+"</label>";
	}
	elementTH += "</th>";
	//$(obj).parent('th').replaceWith(elementTH);
	
	$(div).attr("elementtype",1);
	var elementTD = returnElementTD(div);
	
	$(table).find("tbody").find("tr:eq("+trIndex+")").find("td:eq("+index+")").replaceWith(elementTD);
	$(table).find("tbody").find("tr:eq("+trIndex+")").find("td:eq("+index+")").attr("index", index);
	
	$(tr).find("span").draggable({
		containment: '#' + tableId,
		stack: '#' + tableId,
		revert: true,
		stop: function(event, ui) {
    	  	$(this).css("z-index","auto");
    	  } 
	});
	
	
	$(obj).droppable({
		accept: "#srcgenElements div, #srcgenAction div, #newDragElementTd div",
		activeClass: "state-droppable",
		drop: function(event, ui) {					
			replaceElementList(event, ui, $(this));
		}
	});

	
	$(tr).find("span").droppable({
		accept: "#" + tableId + " span",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			replaceElementList(event, ui, $(this));
		}
	});
	
	$.qp.initialAllPicker($(table).find("tbody").find("tr:eq("+trIndex+")"));
	
	$.qp.initialAutoNumeric($(table).find("tbody").find("tr:eq("+trIndex+")"));
}

function replaceElement(ev, ui, obj){
	
	
	//check single
	if ($(obj).closest("table").attr("class") == "table table-bordered qp-table-form") {
		replaceElementSingle(ev, ui, obj);
	} else if ($(obj).closest("table").attr("class") == "table table-bordered qp-table-list-none-action") {
		replaceElementList(ev, ui, obj);
	} else {
		replaceElementSection(ev, ui, obj);
	}	
}
function replaceElementSection(ev, ui, obj) {
	
}
function replaceElementSingle(ev, ui, obj) {
		
	$(ui.draggable).attr("style", "float: left; cursor: move;");
	$(obj).attr("style", "float: left; cursor: move;");
	
	var sourceTH = $(ui.draggable).closest("th");		
	var sourceTD = $(sourceTH).next();	
	
	var destTH = $(obj).closest("th");
	var destTD = $(destTH).next();
	
	//swap th
	swap(sourceTH, destTH);
	//swap td
	swap(sourceTD, destTD);	
}

function replaceElementList(ev, ui, obj) {
	var table = $(obj).closest("table");
	var tableId = $(table).attr("id");
		
	$(ui.draggable).attr("style", "float: left; cursor: move;");
	$(obj).attr("style", "float: left; cursor: move;");
	var source = {};		
	var dest = {};
	var test = $(ui.draggable).closest("td,th");
	
	if($(ui.draggable).closest("td,th").prop("tagName") == "TD") {
		source = $(ui.draggable).closest("td");
	} else if($(ui.draggable).closest("td,th").prop("tagName") == "TH") {
		source = $(ui.draggable).closest("th");
	}
	
	if($(obj).closest("td,th").prop("tagName") == "TD") {
		dest = $(obj).closest("td");
	} else if($(obj).closest("td,th").prop("tagName") == "TH") {
		dest = $(obj).closest("th");
	}
	
	swap(source, dest);
	
}

function swap(elementSource, elementDest) {
	$(elementSource).find("script").remove();
	$(elementDest).find("script").remove();
	
	var temp = $(elementSource).html();
	$(elementSource).html($(elementDest).html());
	$(elementDest).html(temp);
	
	//init drap, drop source
	$(elementSource).find(".glyphicon-screenshot").draggable({
		containment: "#" + $(elementSource).closest("table").attr("id"),
		stack: "#" + $(elementSource).closest("table").attr("id"),
		revert:function(event, ui) {
			if ($(this).data("uiDraggable") != undefined && $(this).data("uiDraggable").originalPosition != undefined) {
				$(this).data("uiDraggable").originalPosition = {top : 0,left : 0};
			}		
			return true;
		},
		stop: function(event, ui) {			
    	  	$(this).css("z-index","auto");    	  	
    	  } 
	});
		
	$(elementSource).find(".glyphicon-screenshot").droppable({
		accept: "#" + $(elementSource).closest("table").attr("id") + " tr td[class!=srcgenControl] .glyphicon-screenshot",
		activeClass: "state-droppable",
		drop: function(event, ui) {									
			replaceElement(event, ui, $(this));			
		}
	});
	
	$(elementSource).find(".glyphicon-screenshot").droppable({
		accept: "#" + $(elementSource).closest("table").attr("id") + " tr th[class!=srcgenControl] .glyphicon-screenshot",
		activeClass: "state-droppable",
		drop: function(event, ui) {									
			replaceElement(event, ui, $(this));			
		}
	});
	
	$(elementSource).find(".dropComponent").droppable({
		accept: "#srcgenElements div, #srcgenAction div, #newDragElementTd div",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertComponent(event, ui, $(this));
		}
	});
	
	$(elementSource).find(".dropLabel").droppable({
		accept: "#divLabel",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertLable(event, ui, $(this));
		}
	});

	//init drap, drop dest
	$(elementDest).find(".glyphicon-screenshot").draggable({
		containment: "#" + $(elementDest).closest("table").attr("id"),
		stack: "#" + $(elementDest).closest("table").attr("id"),
		revert:function(event, ui) {
			if ($(this).data("uiDraggable") != undefined && $(this).data("uiDraggable").originalPosition != undefined) {
				$(this).data("uiDraggable").originalPosition = {top : 0,left : 0};
			}	
			return true;
		},
		stop: function(event, ui) {			
    	  	$(this).css("z-index","auto");    	  	
    	  } 
	});
		
	$(elementDest).find(".glyphicon-screenshot").droppable({
		accept: "#" + $(elementDest).closest("table").attr("id") + " tr td[class!=srcgenControl] .glyphicon-screenshot",
		activeClass: "state-droppable",
		drop: function(event, ui) {									
			replaceElement(event, ui, $(this));			
		}
	});
	
	$(elementDest).find(".glyphicon-screenshot").droppable({
		accept: "#" + $(elementDest).closest("table").attr("id") + " tr th[class!=srcgenControl] .glyphicon-screenshot",
		activeClass: "state-droppable",
		drop: function(event, ui) {									
			replaceElement(event, ui, $(this));			
		}
	});
	
	$(elementDest).find(".dropComponent").droppable({
		accept: "#srcgenElements div, #srcgenAction div, #newDragElementTd div",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertComponent(event, ui, $(this));
		}
	});
	
	$(elementDest).find(".dropLabel").droppable({
		accept: "#divLabel",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertLable(event, ui, $(this));
		}
	});
	$(elementSource).closest("table").find(".glyphicon-screenshot").removeClass("state-droppable");
}


function returnAddNewRow(){
	return "<a style=\"margin-top: 2px; margin-left:6px;\" href=\"javascript:\" onclick=\"addRowTBJS(this);\" class=\".ui-state-dark qp-glyphicon glyphicon glyphicon-plus com-button-action\" title=\""+dbMsgSource['sc.screendesign.0138']+"\"></a>";
}

function returnElementTHBlank(size){
	var th = "<th class=\"style-header-table\" ";
	if(size != 0){
		th += "width=\"" + size + "px\"";
	}
	th += ">" + returnHiddenTHEmpty()
		+ "<span style=\"float: left; cursor: move;\" class=\"qp-glyphicon glyphicon glyphicon-screenshot\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></span><div class=\"dropLabel\">&nbsp;</div>"
		+ " </th>";
	return th;
}

function returnHiddenTHEmpty() {
	
	var element =  "<input type=\"hidden\" name=\"groupDisplayType\">" +
					"<input type=\"hidden\" name=\"enableGroup\">" +
					"<input type=\"hidden\" name=\"groupTotalElement\">"
		+ "<input type=\"hidden\" name=\"formElement\" "
		+ "value='\"label\":\""
		+ "\",\"datatype\":\""+"20"
		+ "\",\"physicaldatatype\":\""
		+ "\",\"columnname\":\""
		+ "\",\"rowspan\":\""
		+ "\",\"colspan\":\""
		+ "\",\"datasource\":\""
		+ "\",\"minvalue\":\""
		+ "\",\"maxvalue\":\""
		+ "\",\"formatcode\":\""
		+ "\",\"tablename\":\""
		+ "\",\"tablecode\":\""
		+ "\",\"tablecolumnname\":\""
		+ "\",\"tablecolumncode\":\""
		+ "\",\"connectionmsg\":\""
		+ "\",\"transitiontype\":\""
		+ "\",\"actiontype\":\""
		+ "\",\"parameters\":\""
		+ "\",\"screenactionid\":\""
		+ "\",\"linklabel\":\""
		+ "\",\"toscreenid\":\""
		+ "\",\"maxlength\":\""
		+ "\",\"physicalmaxlength\":\"";		
		element += "\",\"require\":\"\",\"elementtype\":\"0\"'>";	
	return element;
} 

function returnHiddenTDEmpty() {
	
	var element =  "<input type=\"hidden\" name=\"groupDisplayType\">" +
					"<input type=\"hidden\" name=\"enableGroup\">" +
					"<input type=\"hidden\" name=\"groupTotalElement\">"
		+ "<input type=\"hidden\" name=\"formElement\" />";	
	return element;
} 

function returnElementTH(div, isList){
	
	var tablecolumncode = "";
	if($(div).attr('tablecolumncode') != undefined){
		tablecolumncode = $(div).attr('tablecolumncode');
	}
	var tablename = "";
	if($(div).attr('tablename') != undefined){
		tablename = $(div).attr('tablename');
	}
	var tablecode = "";
	if($(div).attr('tablecode') != undefined){
		tablecode = $(div).attr('tablecode');
	}
	var tablecolumnname = "";
	if($(div).attr('tablecolumnname') != undefined){
		tablecolumnname = $(div).attr('tablecolumnname');
	}
	
	var physicaldatatype = "";
	if($(div).attr('physicaldatatype') != undefined){
		physicaldatatype = $(div).attr('physicaldatatype');
	}
	var minvalue = "";
	if($(div).attr('minvalue') != undefined){
		minvalue = $(div).attr('minvalue');
	}
	var maxvalue = "";
	if($(div).attr('maxvalue') != undefined){
		maxvalue = $(div).attr('maxvalue');
	}
	var formatcode = "";
	if($(div).attr('formatcode') != undefined){
		formatcode = $(div).attr('formatcode');
	}
	var physicalmaxlength = "";
	if($(div).attr('physicalmaxlength') != undefined){
		physicalmaxlength = $(div).attr('physicalmaxlength');
	}
	var maxlength = "";
	if($(div).attr('maxlength') != undefined){
		maxlength = $(div).attr('maxlength');
	}
	var require = "";
	if($(div).attr('require') != undefined){
		require = $(div).attr('require');
	}
	var rowspan = "";
	if($(div).attr('rowspan') != undefined){
		rowspan = $(div).attr('rowspan');
	}
	var colspan = "";
	if($(div).attr('colspan') != undefined){
		colspan = $(div).attr('colspan');
	}
	var datasource = "";
	if($(div).attr('datasource') != undefined){
		datasource = $(div).attr('datasource');
	}
	var msglabel = "";
	if($(div).attr('msglabel') != undefined){
		msglabel = $(div).attr('msglabel');
	}
	var width = "";
	if($(div).attr('width') != undefined){
		width = $(div).attr('width');
	}
	
	var widthunit = "";
	if($(div).attr('widthunit') != undefined){
		widthunit = $(div).attr('widthunit');
	}
	var messageLevel = "";
	var messageLevelText = "";
	if($(div).attr('messageLevel') != "" && $(div).attr('messageLevel') != undefined) {
		messageLevel = $(div).attr('messageLevel');
	}
	if($(div).attr('messageLevelText') != "" && $(div).attr('messageLevelText') != undefined) {
		messageLevelText = $(div).attr('messageLevelText');
	}
	var alignLeft = "";
	var floatLeft = "";
	if (isList) {		
		var alignLeft = " class=\"align-left\"";
		var floatLeft = " float:left;";
	}
	
	var element =  "<th"+alignLeft+"><span>" +
	"<input type=\"hidden\" name=\"groupDisplayType\">" +
	"<input type=\"hidden\" name=\"enableGroup\">" +
	"<input type=\"hidden\" name=\"groupTotalElement\">"
		+ "<input type=\"hidden\" name=\"formElement\" "
		+ "value='\"label\":\""
		+ "\",\"datatype\":\""+$(div).attr('datatype')
		+ "\",\"physicaldatatype\":\""+physicaldatatype
		+ "\",\"columnname\":\""+$(div).attr('columnname')
		+ "\",\"rowspan\":\""+rowspan
		+ "\",\"colspan\":\""+colspan
		+ "\",\"datasource\":\""+datasource
		+ "\",\"minvalue\":\""+minvalue
		+ "\",\"maxvalue\":\""+maxvalue
		+ "\",\"formatcode\":\""+formatcode
		+ "\",\"tablename\":\""+tablename
		+ "\",\"tablecode\":\""+tablecode
		+ "\",\"tablecolumnname\":\""+tablecolumnname
		+ "\",\"tablecolumncode\":\""+tablecolumncode
		+ "\",\"connectionmsg\":\""
		+ "\",\"transitiontype\":\""
		+ "\",\"actiontype\":\""
		+ "\",\"parameters\":\""
		+ "\",\"screenactionid\":\""
		+ "\",\"linklabel\":\""
		+ "\",\"toscreenid\":\""
		+ "\",\"maxlength\":\""+maxlength
		+ "\",\"isBundle\":\""+"false"
		+ "\",\"isBlank\":\""+"false"
		+ "\",\"width\":\""+width
		+ "\",\"widthunit\":\""+widthunit
		+ "\",\"messageLevel\":\""+messageLevel
		+ "\",\"messageLevelText\":\""+messageLevelText
		+ "\",\"labelText\":\""+dbMsgSource['sc.screendesign.0050']
		+ "\",\"physicalmaxlength\":\""+physicalmaxlength; 
		if($(div).attr('msgvalue') != undefined){
			element += "\",\"msglabel\":\""+msglabel
			+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
		} else {
			element += "\",\"msglabel\":\"\",\"msgvalue\":\"";
		}
		element += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>"
		+ "<span style=\"float: left; cursor: move;\" class=\"qp-glyphicon glyphicon glyphicon-screenshot\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></span>"
		+ "<label ondblclick=\"openDialogLableSetting(this)\" style=\"cursor: pointer;"+floatLeft+"\">"
		+ dbMsgSource['sc.screendesign.0050'] + "</label>";
	if($(div).attr('require') == 1){
		element += "<label class=\"qp-required-field\"> "+fcomMsgSource['sc.sys.0029']+"</label>";
	}
	element += "</span></th>";
	return element;
}

function returnDivSetting(code){
	return "<div class=\"areaContent\"><div class=\"panel panel-default\"><div class=\"panel-heading\"><span class=\"glyphicon\" aria-hidden=\"true\"></span><span class=\"qp-heading-text\">" +
		"<a href=\"javascript:\" style=\"float:left; margin-top: 3px; cursor: move; margin-right: 5px;\" class=\"srcgenTableSort ui-state-default qp-glyphicon-sort glyphicon glyphicon-sort\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></a>" + 
		"<a href=\"javascript:\" style=\"float: left; margin-top: 3px; margin-right: 5px;\" onclick=\"openSectionSetting(this)\" class=\"ui-state-default qp-glyphicon-cog glyphicon glyphicon-cog\" title=\""+dbMsgSource['sc.screendesign.0117']+"\"></a>" +
		'|&nbsp;<i name="icon-display" class="">&nbsp;</i>' + "<span>" + dbMsgSource['sc.screendesign.0060'] + "</span>" +
		"<a href=\"javascript:\" style=\"float: right; margin-top: 3px;\" onclick=\"deleteTable(this)\" class=\"ui-state-default glyphicon glyphicon-remove\" title=\""+dbMsgSource['sc.screendesign.0136']+"\"></a>" +
		"<input type=\"hidden\" name=\"formAreaCaptionText\" value=\""+dbMsgSource['sc.screendesign.0060']+"\"/>" +
		"<input type=\"hidden\" name=\"formAreaCaptionId\"/>" +
		"<input type=\"hidden\" value=\""+code+"\" name=\"formAreaCode\"/>" +
		"<input type=\"hidden\" name=\"formTableColumnSize\"/>" + 
		"<input type=\"hidden\" name=\"formTableWidth\"/>" + 
		"<input type=\"hidden\" name=\"formTableRow\"/>" + 
		"<input type=\"hidden\" name=\"formTablePosition\"/>" + 
		"<input type=\"hidden\" name=\"formElementHidden\"/>" +
		"<input type=\"hidden\" name=\"formDirection\"/>" + 
		"<input type=\"hidden\" name=\"formDisplayType\"/>" + 
		"<input type=\"hidden\" name=\"formElementTable\"/>" + 
		"<input type=\"hidden\" value=\"3\" name=\"formAreaType\"/>" +
		"<input type=\"hidden\" name=\"formHeaderLabelRow\"/>" +
		"<input type=\"hidden\" name=\"formHeaderComponentRow\"/>" + 
		"<input type=\"hidden\" name=\"formIndex\"/>" +
		"<input type=\"hidden\" name=\"formTotalGroup\"/>" +
		"<input type=\"hidden\" name=\"formAreaCaptionIsbundle\"/>" +
		"<input type=\"hidden\" name=\"formAreaPatternType\"/>" +
		"<input type=\"hidden\" name=\"panelStyle\" value=\"\" />" +
		"<input type=\"hidden\" name=\"headerStyle\" value=\"\" />" +
		"<input type=\"hidden\" name=\"rowStyle\" value=\"\" />" +
		"<input type=\"hidden\" name=\"inputStyle\" value=\"\" />" +
		"<input type=\"hidden\" name=\"alternateRowStyle\" value=\"\" />" +
		'<input type="hidden" value=\'\' name="formAreaIcon"/>' +
		'<input type="hidden" value=\'\' name="formFixedRow"/>' +
		"<input type=\"hidden\" name=\"formAreaEvent\"/>" +
		'<input type="hidden" value=\'\' name="areaCustomType"/>' +
		'<input type="hidden" value=\'\' name="formAreaTypeAction"/>' +
		'<input type="hidden" value=\'\' name="formAreaIdStore"/>' +
		'<input type="hidden" value="" name="formObjectMappingId"/>' +
		'<input type="hidden" value="" name="formObjectMappingType"/>' +
		'<input type="hidden" value="" name="formScreenAreaSortValue"/>' +
		'<input type="hidden" value="" name="formCustomSectionContent"/>' +
		"</span></div><div class=\"panel-body\">";
}

function returnActionArea(code){
	return "<div class=\"areaContent\"><div class=\"panel panel-default\"><div class=\"panel-heading\" style=\"background-color:#ebebe1;\"><span class=\"glyphicon\" aria-hidden=\"true\"></span><span class=\"qp-heading-text\">" +
		"<a href=\"javascript:\" style=\"float:left; margin-top: 3px; cursor: move; margin-right: 5px;color: #337AB7;\" class=\"srcgenTableSort ui-state-default qp-glyphicon-sort glyphicon glyphicon-sort\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></a>" + 
		"<a href=\"javascript:\" style=\"float: left; margin-top: 3px; margin-right: 5px;color: #337AB7;\" onclick=\"openActionAreaSetting(this)\" class=\"ui-state-default qp-glyphicon-cog glyphicon glyphicon-cog\" title=\""+dbMsgSource['sc.screendesign.0117']+"\"></a>" +
		"<a href=\"javascript:\" style=\"float: right; margin-top: 3px;color: #337AB7;\" onclick=\"deleteTable(this)\" class=\"ui-state-default glyphicon glyphicon-remove\" title=\""+dbMsgSource['sc.screendesign.0136']+"\"></a>" +
		"<input type=\"hidden\" name=\"formAreaCaptionText\"/>" +
		"<input type=\"hidden\" name=\"formAreaCaptionId\"/>" +
		"<input type=\"hidden\" value=\""+code+"\" name=\"formAreaCode\"/>" + 
		"<input type=\"hidden\" name=\"formTableColumnSize\"/>" + 
		"<input type=\"hidden\" name=\"formTableWidth\"/>" + 
		"<input type=\"hidden\" name=\"formTableRow\"/>" + 
		"<input type=\"hidden\" name=\"formTablePosition\"/>" + 
		"<input type=\"hidden\" name=\"formElementHidden\"/>" +
		"<input type=\"hidden\" name=\"formDirection\" value=\"1\"/>" + 
		"<input type=\"hidden\" name=\"formDisplayType\" value=\"0\"/>" + 
		"<input type=\"hidden\" name=\"formElementTable\"/>" + 
		"<input type=\"hidden\" value=\"2\" name=\"formAreaType\"/>" + 
		"<input type=\"hidden\" name=\"formHeaderLabelRow\"/>" +
		"<input type=\"hidden\" name=\"formHeaderComponentRow\"/>" +
		"<input type=\"hidden\" name=\"formIndex\"/>" +
		"<input type=\"hidden\" name=\"formTotalGroup\"/>" +
		"<input type=\"hidden\" name=\"formAreaCaptionIsbundle\"/>" +
		"<input type=\"hidden\" name=\"formAreaPatternType\"/>" +
		"<input type=\"hidden\" name=\"panelStyle\" value=\"\" />" +
		"<input type=\"hidden\" name=\"headerStyle\" value=\"\" />" +
		"<input type=\"hidden\" name=\"rowStyle\" value=\"\" />" +
		"<input type=\"hidden\" name=\"inputStyle\" value=\"\" />" +
		"<input type=\"hidden\" name=\"alternateRowStyle\" value=\"\" />" +
		'<input type="hidden" value=\'\' name="formAreaIcon"/>' +
		'<input type="hidden" value=\'\' name="formFixedRow"/>' +
		"<input type=\"hidden\" name=\"formAreaEvent\"/>" +
		'<input type="hidden" value=\'\' name="areaCustomType"/>' +
		'<input type="hidden" value=\'\' name="formAreaTypeAction"/>' +
		'<input type="hidden" value=\'\' name="formAreaIdStore"/>' +
		'<input type="hidden" value="" name="formObjectMappingId"/>' +
		'<input type="hidden" value="" name="formObjectMappingType"/>' +
		'<input type="hidden" value="" name="formScreenAreaSortValue"/>' +
		'<input type="hidden" value="" name="formCustomSectionContent"/>' +
		"</span></div><div class=\"panel-body-action\">";
}

function returnSubmitArea(){
	return "<div class=\"com-action-button-panel com-sd-cover\" id=\"srcgenActionArea\" style=\"padding-right: 3px; padding-left: 3px;\">" +
			"<input type=\"hidden\" name=\"srcgenTblDesc\"/>" +
			"<input type=\"hidden\" name=\"formTableColumnSize\"/>" + 
			"<input type=\"hidden\" name=\"formTableWidth\"/>" + 
			"<input type=\"hidden\" name=\"formTableRow\"/>" + 
			"<input type=\"hidden\" name=\"formTablePosition\"/>" + 
			"<input type=\"hidden\" name=\"formElementHidden\"/>" +
			"<input type=\"hidden\" name=\"formDirection\"/>" + 
			"<input type=\"hidden\" name=\"formDisplayType\"/>" + 
			"<input type=\"hidden\" name=\"formElementTable\"/>" + 
			"<input type=\"hidden\" name=\"formIndex\"/>" +
			"<input type=\"hidden\" name=\"formTotalGroup\"/>" +
			"<input type=\"hidden\" name=\"formAreaCaptionIsbundle\"/>" +
			"<input type=\"hidden\" name=\"formAreaPatternType\"/>" +
			"<input type=\"hidden\" name=\"panelStyle\" value=\"\" />" +
			"<input type=\"hidden\" name=\"headerStyle\" value=\"\" />" +
			"<input type=\"hidden\" name=\"rowStyle\" value=\"\" />" +
			"<input type=\"hidden\" name=\"inputStyle\" value=\"\" />" +
			"<input type=\"hidden\" name=\"alternateRowStyle\" value=\"\" />" +
			'<input type="hidden" value=\'\' name="formAreaIcon"/>' +
			'<input type="hidden" value=\'\' name="formFixedRow"/>' +
			"<input type=\"hidden\" name=\"formAreaEvent\"/>" +
			'<input type="hidden" value=\'\' name="areaCustomType"/>' +
			'<input type="hidden" value=\'\' name="formAreaTypeAction"/>' +
			'<input type="hidden" value=\'\' name="formAreaIdStore"/>' +
			'<input type="hidden" value="" name="formObjectMappingId"/>' +
			'<input type="hidden" value="" name="formObjectMappingType"/>' +
			'<input type="hidden" value="" name="formScreenAreaSortValue"/>' +
			'<input type="hidden" value="" name="formCustomSectionContent"/>' +
			"<input type=\"hidden\" value=\"2\" name=\"formAreaType\"/>";	
	
}

function returnCustomSection(code){
	return "<div class=\"areaContent\"><div class=\"panel panel-default\"><div class=\"panel-heading\" style=\"background-color:#ebebe1;color: #337AB7;\"><span class=\"glyphicon\" aria-hidden=\"true\"></span><span class=\"qp-heading-text\">" +
		"<a href=\"javascript:\" style=\"float:left; margin-top: 3px; cursor: move; margin-right: 5px;color: #337AB7;\" class=\"srcgenTableSort ui-state-default qp-glyphicon-sort glyphicon glyphicon-sort\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></a>" + 
		"<a href=\"javascript:\" style=\"float: left; margin-top: 3px; margin-right: 5px;color: #337AB7;\" onclick=\"openDialogCustomSectionSetting(this,true)\" class=\"ui-state-default qp-glyphicon-cog glyphicon glyphicon-cog\" title=\""+dbMsgSource['sc.screendesign.0117']+"\"></a>" +
		"<span style=\"float:left; margin-top: 3px; margin-right: 5px;\">"+dbMsgSource['sc.screendesign.0434']+"</span>" +
		"<a href=\"javascript:\" style=\"float: right; margin-top: 3px;color: #337AB7;\" onclick=\"deleteTable(this)\" class=\"ui-state-default glyphicon glyphicon-remove\" title=\""+dbMsgSource['sc.screendesign.0136']+"\"></a>" +
		"<input type=\"hidden\" name=\"formAreaCaptionText\"/>" +
		"<input type=\"hidden\" name=\"formAreaCaptionId\"/>" +
		"<input type=\"hidden\" value=\""+code+"\" name=\"formAreaCode\"/>" + 
		"<input type=\"hidden\" name=\"formTableColumnSize\"/>" + 
		"<input type=\"hidden\" name=\"formTableWidth\"/>" + 
		"<input type=\"hidden\" name=\"formTableRow\"/>" + 
		"<input type=\"hidden\" name=\"formTablePosition\"/>" + 
		"<input type=\"hidden\" name=\"formElementHidden\"/>" +
		"<input type=\"hidden\" name=\"formDirection\" value=\"1\"/>" + 
		"<input type=\"hidden\" name=\"formDisplayType\" value=\"0\"/>" + 
		"<input type=\"hidden\" name=\"formElementTable\"/>" + 
		"<input type=\"hidden\" value=\"4\" name=\"formAreaType\"/>" + 
		"<input type=\"hidden\" name=\"formHeaderLabelRow\"/>" +
		"<input type=\"hidden\" name=\"formHeaderComponentRow\"/>" +
		"<input type=\"hidden\" name=\"formIndex\"/>" +
		"<input type=\"hidden\" name=\"formTotalGroup\"/>" +
		"<input type=\"hidden\" name=\"formAreaCaptionIsbundle\"/>" +
		"<input type=\"hidden\" name=\"formAreaPatternType\"/>" +
		"<input type=\"hidden\" name=\"formAreaEvent\"/>" +
		"<input type=\"hidden\" name=\"panelStyle\" value=\"\" />" +
		"<input type=\"hidden\" name=\"headerStyle\" value=\"\" />" +
		"<input type=\"hidden\" name=\"rowStyle\" value=\"\" />" +
		"<input type=\"hidden\" name=\"inputStyle\" value=\"\" />" +
		"<input type=\"hidden\" name=\"alternateRowStyle\" value=\"\" />" +
		'<input type="hidden" value=\'\' name="formAreaIcon"/>' +
		'<input type="hidden" value=\'\' name="formFixedRow"/>' +
		'<input type="hidden" value=\'\' name="areaCustomType"/>' +
		'<input type="hidden" value=\'\' name="formAreaTypeAction"/>' +
		'<input type="hidden" value=\'\' name="formAreaIdStore"/>' +
		'<input type="hidden" value="" name="formObjectMappingId"/>' +
		'<input type="hidden" value="" name="formCustomSectionContent"/>' +
		'<input type="hidden" value="" name="formScreenAreaSortValue"/>' +
		'<input type="hidden" value="" name="formObjectMappingType"/>' +
		"</span></div>";
}


function returnTableSetting(code, name, isList){
	
	var tableName = dbMsgSource['sc.screendesign.0058'];
	var remark = "";
	
	if (name != undefined && name != null) {
		tableName = name;
	}
	
	if (isList == undefined || isList == null) {
		isList = 0;
	}
	
	if (isList == 1) {
		remark = "<span style='float: right;' name='addRemoveTable'>(add remove table)&nbsp;&nbsp;</span>";
	}
	
	return "<div class=\"areaContent\"><div class=\"panel panel-default\"><div class=\"panel-heading\"><span class=\"glyphicon\" aria-hidden=\"true\"></span><span class=\"qp-heading-text\">" +
				"<a href=\"javascript:\" style=\"float:left; margin-top: 3px; cursor: move; margin-right: 5px;\" class=\"srcgenTableSort ui-state-default qp-glyphicon-sort glyphicon glyphicon-sort\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></a>" + 
				"<a href=\"javascript:\" style=\"float: left; margin-top: 3px; margin-right: 5px;\" onclick=\"openTableSetting(this)\" class=\"ui-state-default qp-glyphicon-cog glyphicon glyphicon-cog\" title=\""+dbMsgSource['sc.screendesign.0117']+"\"></a>" + 
				'|&nbsp;<i name="icon-display" class="">&nbsp;</i>' + "<span>" + tableName + "</span>" + 
				"<a href=\"javascript:\" style=\"float: right; margin-top: 3px;\" onclick=\"deleteTable(this)\" class=\"ui-state-default glyphicon glyphicon-remove\" title=\""+dbMsgSource['sc.screendesign.0136']+"\"></a>" +
				"<span style='float: right;' name='addRemoveTable'>"+remark+"&nbsp;&nbsp;</span>" +
				"<input type=\"hidden\" name=\"formAreaCaptionText\" value=\"" + tableName + "\"/>" +
				"<input type=\"hidden\" name=\"formAreaCaptionId\"/>" +
				"<input type=\"hidden\" value=\""+code+"\" name=\"formAreaCode\"/>" + 
				"<input type=\"hidden\" name=\"formTableColumnSize\"/>" + 
				"<input type=\"hidden\" name=\"formTableWidth\"/>" + 
				"<input type=\"hidden\" name=\"formTableRow\"/>" + 
				"<input type=\"hidden\" name=\"formTablePosition\"/>" + 
				"<input type=\"hidden\" name=\"formElementHidden\"/>" +
				"<input type=\"hidden\" name=\"formDirection\"/>" + 
				"<input type=\"hidden\" name=\"formDisplayType\"/>" + 
				"<input type=\"hidden\" name=\"formElementTable\"/>" + 
				"<input type=\"hidden\" value=\"0\" name=\"formAreaType\"/>" + 
				"<input type=\"hidden\" name=\"formHeaderLabelRow\"/>" +
				"<input type=\"hidden\" name=\"formHeaderComponentRow\"/>" +
				"<input type=\"hidden\" name=\"formIndex\"/>" +
				"<input type=\"hidden\" name=\"formTotalGroup\"/>" +
				"<input type=\"hidden\" name=\"formAreaCaptionIsbundle\"/>" +
				"<input type=\"hidden\" name=\"formAreaPatternType\"/>" +
				"<input type=\"hidden\" name=\"formAreaEvent\"/>" +
				"<input type=\"hidden\" name=\"panelStyle\" value=\"\" />" +
				"<input type=\"hidden\" name=\"headerStyle\" value=\"\" />" +
				"<input type=\"hidden\" name=\"rowStyle\" value=\"\" />" +
				"<input type=\"hidden\" name=\"inputStyle\" value=\"\" />" +
				"<input type=\"hidden\" name=\"alternateRowStyle\" value=\"\" />" +
				'<input type="hidden" value=\'\' name="formAreaIcon"/>' +
				'<input type="hidden" value=\'\' name="formFixedRow"/>' +
				'<input type="hidden" value=\''+isList+'\' name="areaCustomType"/>' +
				'<input type="hidden" value=\'\' name="formAreaTypeAction"/>' +
				'<input type="hidden" value=\'\' name="formAreaIdStore"/>' +
				'<input type="hidden" value="" name="formObjectMappingId"/>' +
				'<input type="hidden" value="" name="formCustomSectionContent"/>' +
				'<input type="hidden" value="" name="formScreenAreaSortValue"/>' +
				'<input type="hidden" value="" name="formObjectMappingType"/>' +
				"</span></div><div class=\"panel-body\">";
}

function returnTableListSetting(areaType, code){
	var areaPatternType = 1;
	if (areaType != null && areaType != undefined) {
		areaPatternType = areaType;
	}
	return "<div class=\"areaContent\"><div class=\"panel panel-default\"><div class=\"panel-heading\"><span class=\"glyphicon\" aria-hidden=\"true\"></span><span class=\"qp-heading-text\">" +
				"<a href=\"javascript:\" style=\"float:left; margin-top: 3px; cursor: move; margin-right: 5px;\" class=\"srcgenTableSort ui-state-default qp-glyphicon-sort glyphicon glyphicon-sort\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></a>" + 
				"<a href=\"javascript:\" style=\"float: left; margin-top: 3px; margin-right: 5px;\" onclick=\"openTableListSetting(this)\" class=\"ui-state-default qp-glyphicon-cog glyphicon glyphicon-cog\" title=\""+dbMsgSource['sc.screendesign.0117']+"\"></a>" + 
				'|&nbsp;<i name="icon-display" class="">&nbsp;</i>' + "<span>" + dbMsgSource['sc.screendesign.0059'] + "</span>" + 
				"<a href=\"javascript:\" style=\"float: right; margin-top: 3px;\" onclick=\"deleteTable(this)\" class=\"ui-state-default glyphicon glyphicon-remove\" title=\""+dbMsgSource['sc.screendesign.0136']+"\"></a>" +
				"<input type=\"hidden\" name=\"formAreaCaptionText\" value=\"" + dbMsgSource['sc.screendesign.0059'] + "\"/>" +
				"<input type=\"hidden\" name=\"formAreaCaptionId\"/>" +
				"<input type=\"hidden\" value=\""+code+"\" name=\"formAreaCode\"/>" + 
				"<input type=\"hidden\" name=\"formTableColumnSize\"/>" + 
				"<input type=\"hidden\" name=\"formTableWidth\"/>" + 
				"<input type=\"hidden\" name=\"formTableRow\"/>" + 
				"<input type=\"hidden\" name=\"formTablePosition\"/>" + 
				"<input type=\"hidden\" name=\"formElementHidden\"/>" +
				"<input type=\"hidden\" name=\"formDirection\"/>" + 
				"<input type=\"hidden\" name=\"formDisplayType\"/>" + 
				"<input type=\"hidden\" name=\"formElementTable\"/>" + 
				"<input type=\"hidden\" value=\"1\" name=\"formAreaType\"/>" + 
				"<input type=\"hidden\" name=\"formHeaderLabelRow\"/>"  +
				"<input type=\"hidden\" name=\"formHeaderComponentRow\"/>" +
				"<input type=\"hidden\" name=\"formIndex\"/>" +
				"<input type=\"hidden\" name=\"formTotalGroup\"/>" +
				"<input type=\"hidden\" name=\"formAreaCaptionIsbundle\"/>" +
				"<input type=\"hidden\" name=\"panelStyle\" value=\"\" />" +
				"<input type=\"hidden\" name=\"headerStyle\" value=\"\" />" +
				"<input type=\"hidden\" name=\"rowStyle\" value=\"\" />" +
				"<input type=\"hidden\" name=\"inputStyle\" value=\"\" />" +
				"<input type=\"hidden\" name=\"alternateRowStyle\" value=\"\" />" +
				"<input type=\"hidden\" value=\""+areaPatternType+"\" name=\"formAreaPatternType\"/>" +
				'<input type="hidden" value=\'\' name="formAreaIcon"/>' +
				'<input type="hidden" value=\'\' name="formFixedRow"/>' +
				"<input type=\"hidden\" name=\"formAreaEvent\"/>" +
				'<input type="hidden" value=\'\' name="areaCustomType"/>' +
				'<input type="hidden" value=\'0\' name="formAreaTypeAction"/>' +
				'<input type="hidden" value=\'\' name="formAreaIdStore"/>' +
				'<input type="hidden" value="" name="formObjectMappingId"/>' +
				'<input type="hidden" value="" name="formCustomSectionContent"/>' +
				'<input type="hidden" value="" name="formScreenAreaSortValue"/>' +
				'<input type="hidden" value="" name="formObjectMappingType"/>' +
			"</span></div><div class=\"panel-body\">";
}

function returnSortRemoveTD(){
	return "<td class=\"sortable srcgenControl\">" +
				"<a href=\"javascript:\" style=\"margin-top: 3px; cursor: move;\" class=\".ui-state-dark qp-glyphicon glyphicon glyphicon-sort\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></a>" +
			"</td>" +
			"<td class=\"srcgenControl\">" +
				"<a href=\"javascript:\" style=\"margin-top: 3px;\" onclick=\"removeRowTBJS(this);\" class=\".ui-state-dark qp-glyphicon glyphicon glyphicon-minus com-button-action\" title=\""+dbMsgSource['sc.screendesign.0139']+"\"></a>" +
			"</td>";
}

function returnAddRemoveTD(type, i){
	var td = "";
	if(type == 0){		
		// Single Entity
		td = "<td class=\"srcgenControl\" colspan=\"2\" index=\""+i+"\">" + 
				"<a href=\"javascript:\" style=\"float: right; margin-left: 5px;\" onclick=\"removeColumnJS(this);\" class=\".ui-state-dark qp-glyphicon glyphicon glyphicon-minus com-button-action\" title=\""+dbMsgSource['sc.screendesign.0140']+"\"></a>" + 
				"<a href=\"javascript:\" style=\"float: right; margin-left: 5px;\" onclick=\"addColumnJS(this);\" class=\".ui-state-dark qp-glyphicon glyphicon glyphicon-plus com-button-action\" title=\""+dbMsgSource['sc.screendesign.0141']+"\"></a>" + 	
			"</td>";
	} else {
		
		td = "<td class=\"srcgenControl\" index=\""+i+"\">" +
		"<a href=\"javascript:\" style=\"float: right; margin-left: 5px;\" onclick=\"removeColumnTBJS(this);\" class=\".ui-state-dark qp-glyphicon glyphicon glyphicon-minus com-button-action\" title=\""+dbMsgSource['sc.screendesign.0140']+"\"></a>" +
		"<a href=\"javascript:\" style=\"float: right;\" onclick=\"addColumnTBJS(this);\" class=\".ui-state-dark qp-glyphicon glyphicon glyphicon-plus com-button-action\" title=\""+dbMsgSource['sc.screendesign.0141']+"\"></a></td>";
	}
	return td;
}

function returnElementTD(div){	
	var dataType = $(div).attr('datatype');
	
	var elementTD = "<td><input type=\"hidden\" name=\"groupDisplayType\" value=\"1\"  /><input type=\"hidden\" name=\"enableGroup\" value=\"true\"  /><input type=\"hidden\" name=\"groupTotalElement\"  />";
	var tablecolumncode = "";
	if($(div).attr('tablecolumncode') != undefined){
		tablecolumncode = $(div).attr('tablecolumncode');
	}
	var tablename = "";
	if($(div).attr('tablename') != undefined){
		tablename = $(div).attr('tablename');
	}
	var tablecode = "";
	if($(div).attr('tablecode') != undefined){
		tablecode = $(div).attr('tablecode');
	}
	var tablecolumnname = "";
	if($(div).attr('tablecolumnname') != undefined){
		tablecolumnname = $(div).attr('tablecolumnname');
	}
	
	var physicaldatatype = "";
	if($(div).attr('physicaldatatype') != undefined){
		physicaldatatype = $(div).attr('physicaldatatype');
	}
	var minvalue = "";
	if($(div).attr('minvalue') != undefined){
		minvalue = $(div).attr('minvalue');
	}
	var maxvalue = "";
	if($(div).attr('maxvalue') != undefined){
		maxvalue = $(div).attr('maxvalue');
	}
	var formatcode = "";
	if($(div).attr('formatcode') != undefined){
		formatcode = $(div).attr('formatcode');
	}
	var physicalmaxlength = "";
	if($(div).attr('physicalmaxlength') != undefined){
		physicalmaxlength = $(div).attr('physicalmaxlength');
	}
	var maxlength = "";
	if($(div).attr('maxlength') != undefined){
		maxlength = $(div).attr('maxlength');
	}
	var require = "";
	if($(div).attr('require') != undefined){
		require = $(div).attr('require');
	}
	var rowspan = "";
	if($(div).attr('rowspan') != undefined){
		rowspan = $(div).attr('rowspan');
	}
	var colspan = "";
	if($(div).attr('colspan') != undefined){
		colspan = $(div).attr('colspan');
	}
	var datasource = "";
	if($(div).attr('datasource') != undefined){
		datasource = $(div).attr('datasource');
	}
	var msglabel = "";
	if($(div).attr('msglabel') != undefined){
		msglabel = $(div).attr('msglabel');
	}
	
	var element =  "" 
		+ "<input type=\"hidden\" name=\"formElement\" "
		+ "value='\"label\":\""+''
		+ "\",\"datatype\":\""+$(div).attr('datatype')
		+ "\",\"physicaldatatype\":\""+physicaldatatype
		+ "\",\"columnname\":\""+$(div).attr('columnname')
		+ "\",\"rowspan\":\""+rowspan
		+ "\",\"colspan\":\""+colspan
		+ "\",\"datasource\":\""+datasource
		+ "\",\"minvalue\":\""+minvalue
		+ "\",\"maxvalue\":\""+maxvalue
		+ "\",\"formatcode\":\""+formatcode
		+ "\",\"tablename\":\""+tablename
		+ "\",\"tablecode\":\""+tablecode
		+ "\",\"tablecolumnname\":\""+tablecolumnname
		+ "\",\"tablecolumncode\":\""+tablecolumncode
		+ "\",\"connectionmsg\":\""
		+ "\",\"transitiontype\":\""
		+ "\",\"actiontype\":\""
		+ "\",\"parameters\":\""
		+ "\",\"screenactionid\":\""
		+ "\",\"linklabel\":\""
		+ "\",\"toscreenid\":\""
		+ "\",\"maxlength\":\""+maxlength
		+ "\",\"labelText\":\""+ $(div).attr('label')
		+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
	
	
		if($(div).attr('msgvalue') != undefined){
			element += "\",\"msglabel\":\""+msglabel
			+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
		} else {
			element += "\",\"msglabel\":\"\",\"msgvalue\":\"";
		}
		
		element += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		
	if(dataType == 0){
		elementTD += "<span>" +
				"<div ondblclick=\"openDialogAutocompleteSetting(this)\" class=\"input-group date\" style=\"width:100%\"><input type=\"text\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += " class=\"form-control ui-autocomplete-input\" ";
				} else {
					elementTD += " class=\"qp-input-autocomplete\" ";
				}
				elementTD += " name=\"" 
				+ $(div).attr('columnname') + "\" maxlength=\"" 
				+ $(div).attr('maxlength')
				+		" optionvalue=\"optionValue\" optionlabel=\"optionLabel\" selectsqlid=\"\" emptylabel=\"\" onselectevent=\"\" onchangeevent=\"\" onremoveevent=\"\" mustmatch=\"true\" minlength=\"0\" arg01=\"\" arg02=\"\" arg03=\"\" arg4=\"\" arg05=\"\" arg06=\"\" arg07=\"\" arg08=\"\" arg09=\"\" arg10=\"\" arg11=\"\" arg12=\"\" arg13=\"\" arg14=\"\" arg15=\"\" arg16=\"\" arg17=\"\" arg18=\"\" arg19=\"\" arg20=\"\" placeholder=\"\" autocomplete=\"off\">"
				+ "	<input type=\"hidden\" value=\"\">" 
				+ "	<span class=\"input-group-addon dropdown-toggle\" data-dropdown=\"dropdown\">" 
				+ "		<span class=\"caret\"></span> " 
				+ "	</span>"
				+ "</div>"+element+"</span>"
				"";		
	}
	if(dataType == 1 || dataType == 15 || dataType == 16 || dataType == 18){
		elementTD += "<span style=\"width: 100%;\"><input ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"form-control qp-input-text\"";
					} else {
						elementTD += "class=\"form-control qp-input-text-detail\"";
					}
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" />"+element+"</span>";
	}
	if(dataType == 21){
		elementTD += "<span style=\"width: 100%;\"><Label ondblclick=\"openDialogComponentSetting(this)\" ";					
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" >"+$(div).attr('columnname')+"</label>"+element+"</span>";
	}
	if(dataType == 2){
		elementTD += "<span style=\"width: 100%;\"><input  ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"form-control\"";
					} else {
						elementTD += "class=\"com-input-integer-detail\"";
					}
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" />"+element+"</span>";
	}
	if(dataType == 3){
		elementTD += "<span style=\"width: 100%;\"><input  ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"form-control\"";
					} else {
						elementTD += "class=\"com-input-float-detail\"";
					}
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" />"+element+"</span>";
	}
	if(dataType == 8){
		elementTD += "<span style=\"width: 100%;\"><input ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"form-control\"";
					} else {
						elementTD += "class=\"com-input-currency-detail\"";
					}
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" />"+element+"</span>";
	}
	if(dataType == 4){
		elementTD += "<span style=\"width: 100%;\"><div class=\"input-group date\">"
		elementTD += "<span><input ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
						if($(div).attr('elementtype') == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control\"";
						}
		elementTD += " name=\"" 
							+ $(div).attr('columnname') + "\" maxlength=\"" 
							+ $(div).attr('maxlength') + "\" />"+element+"</span>";
		elementTD += "<span class=\"input-group-addon\">"
							+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
							+ "</span>"
						+ "</div></span>";
	}
	if(dataType == 14){
		
		elementTD += "<span style=\"width: 100%;\"><div class=\"input-group date\">"
		elementTD += "<span><input ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
						if($(div).attr('elementtype') == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control \"";
						}
		elementTD += " name=\"" 
						+ $(div).attr('columnname') + "\" maxlength=\"" 
						+ $(div).attr('maxlength') + "\" />"+element+"</span>";
		elementTD += "<span class=\"input-group-addon\">"
						+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
						+ "</span>"
					+ "</div></span>";
	}
	if(dataType == 9){
		
		elementTD += "<span style=\"width: 100%;\"><div class=\"input-group date\">"
			elementTD += "<span><input ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
						if($(div).attr('elementtype') == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"com-input-timepicker-detail\"";
						}
			elementTD += " name=\"" 
							+ $(div).attr('columnname') + "\" maxlength=\"" 
							+ $(div).attr('maxlength') + "\" />"+element+"</span>";
			elementTD += "<span class=\"input-group-addon\">"
							+ "<span class=\"glyphicon glyphicon-time\"></span>"
							+ "</span>"
						+ "</div></span>";			
	}
	if(dataType == 5){
		if($(div).attr('msgvalue') != undefined && $(div).attr('msgvalue').length > 0){
			var msgLabelArr = $(div).attr('msglabel').split("�");
			var msgValArr = $(div).attr('msgvalue').split("�");
			elementTD += "<span ondblclick=\"openDialogComponentListSetting(this)\">";
			$(msgValArr).each(function(j){
				elementTD += "<input  type=\"radio\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"com-input-radio-detail\"";
				}
				if($(div).attr('msglabel') != undefined && $(div).attr('msglabel').length == 0){					
					elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+msgValArr[j]+"</label>";
				} else {					
					elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+msgLabelArr[j]+"</label>";
				}
			});
			elementTD += ""+element+"</span>";
		} else {
			elementTD += "<span ondblclick=\"openDialogComponentListSetting(this)\"><input type=\"radio\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"com-input-radio-detail\"";
				}
			elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
			elementTD += "<input type=\"radio\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"com-input-radio-detail\"";
				}
			elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>"+element+"</span>";
		}
	}
	if(dataType == 6){
		if($(div).attr('msgvalue') != undefined && $(div).attr('msgvalue').length > 0){
			var msgLabelArr = $(div).attr('msglabel').split("�");
			var msgValArr = $(div).attr('msgvalue').split("�");
			elementTD += "<span ondblclick=\"openDialogComponentListSetting(this)\">";
			$(msgValArr).each(function(j){
				elementTD += "<input type=\"checkbox\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				} else {
					elementTD += "class=\"com-input-checkbox-detail\"";
				}				
				if($(div).attr('msglabel') != undefined && $(div).attr('msglabel').length == 0){					
					elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+msgValArr[j]+"</label>";
				} else {					
					elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+msgLabelArr[j]+"</label>";
				}				
			});
			elementTD += ""+element+"</span>";
		} else {
			elementTD += "<span ondblclick=\"openDialogComponentListSetting(this)\"><input type=\"checkbox\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				} else {
					elementTD += "class=\"com-input-checkbox-detail\"";
				}
			elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
			elementTD += "<input type=\"checkbox\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				} else {
					elementTD += "class=\"com-input-checkbox-detail\"";
				}
			elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>"+element+"</span>";
		}
	}
	if(dataType == 7){
		elementTD += "<span><select ondblclick=\"openDialogComponentListSetting(this)\" name=\""+$(div).attr('columnname')+"\" ";
		if($(div).attr('elementtype') == 0){
			elementTD += "class=\"form-control qp-input-select\"";
		} else {
			elementTD += "class=\"form-control qp-input-select-detail\"";
		}
		elementTD += ">";
		if($(div).attr('msgvalue') != undefined && $(div).attr('msgvalue').length > 0){
			var msgLabelArr = $(div).attr('msglabel').split("�");
			var msgValArr = $(div).attr('msgvalue').split("�");
			
			$(msgValArr).each(function(j){
				if($(div).attr('msglabel') != undefined && $(div).attr('msglabel').length == 0){
					elementTD += "<option>" + msgValArr[j] + "</option>";
				} else {
					elementTD += "<option>" + msgLabelArr[j] + "</option>";
				}
			});
		} else {
			elementTD += "<option>"+dbMsgSource['sc.screendesign.0137']+"1</option><option>"+dbMsgSource['sc.screendesign.0137']+"2</option>";
		}
		elementTD += "</select>"+element+"</span>";
	}
	if(dataType == 10){
		elementTD += "<span><textarea ondblclick=\"openDialogComponentSetting(this)\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"form-control qp-input-textarea\"";
					} else {
						elementTD += "class=\"form-control qp-input-textarea-detail\"";
					}
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "1\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" ></textarea>"+element+"</span>";
	}
	if(dataType == 11){	
		
		var elementButton =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""+""
			+ "\",\"datatype\":\""+$(div).attr('datatype')
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+$(div).attr('columnname')
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+ $(div).attr("labeltext")
			+ "\",\"isBundle\":\""+ "false"
			+ "\",\"navigateTo\":\""
			+ "\",\"navigateToText\":\""
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
			if($(div).attr('msgvalue') != undefined){
				elementButton += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
			} else {
				elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<span><a ondblclick=\"openDialogLinkAreaSetting(this);\" href=\"javascript:\">"+$(div).attr('labelText')+"</a>"+elementButton+"</span>";
	}
	if(dataType == 12){
		elementTD += "<span><input onclick=\"event.stopPropagation();return false;\" ondblclick=\"event.stopPropagation();openDialogComponentSetting(this);return false;\" type=\"file\" name=\"" + $(div).attr('columnname') + "\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"qp-input-file\"";
					} else {
						elementTD += "class=\"com-input-file-detail\"";
					}
		elementTD += "/>"+element+"</span>";
	}
	if(dataType == 13){
		var elementButton =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""
			+ "\",\"datatype\":\""+$(div).attr('datatype')
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+$(div).attr('columnname')
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+$(div).attr("labeltext")
			+ "\",\"isBundle\":\""+ "false"
			+ "\",\"isSubmit\":\""+ "false"
			+ "\",\"navigateTo\":\""
			+ "\",\"navigateToText\":\""
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
		if($(div).attr('msgvalue') != undefined){
			elementButton += "\",\"msglabel\":\""+msglabel
			+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
		} else {
			elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
		}
		
		elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		
		elementTD += "<span><button type=\"button\" class=\"btn qp-button\" ondblclick=\"openDialogButtonAreaSetting(this)\" value=\""+$(div).attr('labelText')+"\">"+$(div).attr('labelText')+"</button>"+elementButton+"</span>";
	}
	if(dataType == 20){
		var elementLabel =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""
			+ "\",\"datatype\":\""+$(div).attr('datatype')
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+$(div).attr('columnname')
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+$(div).attr("label")
			+ "\",\"isBundle\":\""+ "false"
			+ "\",\"isSubmit\":\""+ "false"
			+ "\",\"navigateTo\":\""
			+ "\",\"navigateToText\":\""
			+ "\",\"itemType\":\"5"
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
		if($(div).attr('msgvalue') != undefined){
			elementLabel += "\",\"msglabel\":\""+msglabel
			+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
		} else {
			elementLabel += "\",\"msglabel\":\"\",\"msgvalue\":\"";
		}
		
		elementLabel += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
elementTD += "<span><label ondblclick=\"openDialogLableSetting(this, true)\" style=\"cursor: pointer;\">"+$(div).attr('label')+"</label>"+elementLabel+"</span>";
	}
	elementTD += "</td>";
	return elementTD;
}

function returnElementTHPreview(item, isList){
	
	var tablecolumncode = "";
	if(item.tablecolumncode != undefined){
		tablecolumncode = item.tablecolumncode;
	}
	var tablename = "";
	if(item.tablename != undefined){
		tablename = item.tablename;
	}
	var tablecode = "";
	if(item.tablecode != undefined){
		tablecode = item.tablecode;
	}
	var tablecolumnname = "";
	if(item.tablecolumnname != undefined){
		tablecolumnname = item.tablecolumnname;
	}
	
	var physicaldatatype = "";
	if(item.physicaldatatype != undefined){
		physicaldatatype = item.physicaldatatype;
	}
	var minvalue = "";
	if(item.minvalue != undefined){
		minvalue = item.minvalue;
	}
	var maxvalue = "";
	if(item.maxvalue != undefined){
		maxvalue = item.maxvalue;
	}
	var formatcode = "";
	if(item.formatcode != undefined){
		formatcode = item.formatcode;
	}
	var physicalmaxlength = "";
	if(item.physicalmaxlength != undefined){
		physicalmaxlength = item.physicalmaxlength;
	}
	var maxlength = "";
	if(item.maxlength != undefined){
		maxlength = item.maxlength;
	}
	var require = "";
	if(item.require != undefined){
		require = item.require;
	}
	var rowspan = "";
	if(item.rowspan != undefined){
		rowspan = item.rowspan;
	}
	var colspan = "";
	if(item.colspan != undefined){
		colspan = item.colspan;
	}
	var datasource = "";
	if(item.datasource != undefined){
		datasource = item.datasource;
	}
	var msglabel = "";
	if(item.msglabel != undefined){
		msglabel = item.msglabel;
	}
	var alignLeft = "";
	var floatLeft = "";
	if (isList) {		
		var alignLeft = " class=\"align-left\"";
		var floatLeft = " float:left;";
	}
	var element =  "<span><label style=\"cursor: pointer;"+floatLeft+ item.style + "\">" + item.labelText + "</label>";
	if(item.mandatory == "true"){
		element += "&nbsp;<label class=\"qp-required-field\"> "+fcomMsgSource['sc.sys.0029']+"</label>";
	}	
	element += "</span>";
	return element;
}
function htmlDecode(input){
	  var e = document.createElement('div');
	  e.innerHTML = input;
	  return e.childNodes.length === 0 ? "" : e.childNodes[0].nodeValue;
	}
function returnElementTHAddEntity(item, isList){
	
	var tablecolumncode = "";
	if(item.tablecolumncode != undefined){
		tablecolumncode = item.tablecolumncode;
	}
	var tablename = "";
	if(item.tablename != undefined){
		tablename = item.tablename;
	}
	var tablecode = "";
	if(item.tablecode != undefined){
		tablecode = item.tablecode;
	}
	var tablecolumnname = "";
	if(item.tablecolumnname != undefined){
		tablecolumnname = item.tablecolumnname;
	}
	
	var physicaldatatype = "";
	if(item.physicaldatatype != undefined){
		physicaldatatype = item.physicaldatatype;
	}
	var minvalue = "";
	if(item.minvalue != undefined){
		minvalue = item.minvalue;
	}
	var maxvalue = "";
	if(item.maxvalue != undefined){
		maxvalue = item.maxvalue;
	}
	var formatcode = "";
	if(item.formatcode != undefined){
		formatcode = item.formatcode;
	}
	var physicalmaxlength = "";
	if(item.physicalmaxlength != undefined){
		physicalmaxlength = item.physicalmaxlength;
	}
	var maxlength = "";
	if(item.maxlength != undefined){
		maxlength = item.maxlength;
	}
	var require = "";
	if(item.require != undefined){
		require = item.require;
	}
	var rowspan = "";
	if(item.rowspan != undefined){
		rowspan = item.rowspan;
	}
	var colspan = "";
	if(item.colspan != undefined){
		colspan = item.colspan;
	}
	var datasource = "";
	if(item.datasource != undefined){
		datasource = item.datasource;
	}
	var msglabel = "";
	if(item.msglabel != undefined){
		msglabel = item.msglabel;
	}
	
	var alignLeft = "";
	var floatLeft = "";
/*	if (isList) {		
		var alignLeft = " class=\"align-left\"";
		var floatLeft = " float:left;";
	}*/
	
	var element =  "<input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"enableGroup\"  /><input type=\"hidden\" name=\"groupTotalElement\"  /><span>"
		+ "<input type=\"hidden\" name=\"formElement\" "
		+ "value='\"label\":\""+item.label
		+ "\",\"datatype\":\""+20
		+ "\",\"physicaldatatype\":\""+physicaldatatype
		+ "\",\"columnname\":\""+item.columnname
		+ "\",\"rowspan\":\""+rowspan
		+ "\",\"colspan\":\""+colspan
		+ "\",\"datasource\":\""+datasource
		+ "\",\"minvalue\":\""+minvalue
		+ "\",\"maxvalue\":\""+maxvalue
		+ "\",\"formatcode\":\""+formatcode
		+ "\",\"tablename\":\""+tablename
		+ "\",\"tablecode\":\""+tablecode
		+ "\",\"tablecolumnname\":\""+tablecolumnname
		+ "\",\"tablecolumncode\":\""+tablecolumncode
		+ "\",\"connectionmsg\":\""
		+ "\",\"transitiontype\":\""
		+ "\",\"actiontype\":\""
		+ "\",\"parameters\":\""
		+ "\",\"screenactionid\":\""
		+ "\",\"linklabel\":\""
		+ "\",\"toscreenid\":\""
		+ "\",\"mandatory\":\""+item.mandatory
		+ "\",\"maxlength\":\""+maxlength
		+ "\",\"labelText\":\""+item.labelText
		+ "\",\"isBlank\":\""+item.isBlank
		+ "\",\"style\":\""+item.style
		+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		if(item.msgvalue != undefined){
			element += "\",\"msglabel\":\""+msglabel
			+ "\",\"msgvalue\":\""+item.msgvalue;
		} else {
			element += "\",\"msglabel\":\"\",\"msgvalue\":\"";
		}
		element += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		if (isList != null && isList != '' && isList == 'false') {
			element += "<span style=\"float: left; cursor: move; padding-right: 4px;\" class=\"qp-glyphicon glyphicon glyphicon-screenshot\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></span>";
		}
		
		element += "<label ondblclick=\"openDialogLableSetting(this)\" style=\"cursor: pointer;"+floatLeft + ' ' + item.headerStyle+ item.style +" \">"+ htmlDecode(item.labelText);
		if(item.mandatory == "true"){
			element += "&nbsp;<label class=\"qp-required-field\">(*)</label>";
		}
		element += "</label>";
	element += "</span>";
	return element;
}

function returnElementTDAddEntity(item){	
	var dataType = item.datatype;
	
	var elementTD = "<input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"enableGroup\"  /><input type=\"hidden\" name=\"groupTotalElement\"  />";
	var tablecolumncode = "";
	if(item.tablecolumncode != undefined){
		tablecolumncode = item.tablecolumncode;
	}
	var tablename = "";
	if(item.tablename != undefined){
		tablename = item.tablename;
	}
	var tablecode = "";
	if(item.tablecode != undefined){
		tablecode = item.tablecode;
	}
	var tablecolumnname = "";
	if(item.tablecolumnname != undefined){
		tablecolumnname = item.tablecolumnname;
	}
	
	var physicaldatatype = "";
	if(item.physicaldatatype != undefined){
		physicaldatatype = item.physicaldatatype;
	}
	var minvalue = "";
	if(item.minvalue != undefined){
		minvalue = item.minvalue;
	}
	var maxvalue = "";
	if(item.maxvalue != undefined){
		maxvalue = item.maxvalue;
	}
	var formatcode = "";
	if(item.formatcode != undefined){
		formatcode = item.formatcode;
	}
	var physicalmaxlength = "";
	if(item.physicalmaxlength != undefined){
		physicalmaxlength = item.physicalmaxlength;
	}
	var maxlength = "";
	if(item.maxlength != undefined){
		maxlength = item.maxlength;
	}
	var require = "";
	if(item.require != undefined){
		require = item.require;
	}
	var rowspan = "";
	if(item.rowspan != undefined){
		rowspan = item.rowspan;
	}
	var colspan = "";
	if(item.colspan != undefined){
		colspan = item.colspan;
	}
	var datasource = "";
	if(item.datasource != undefined){
		datasource = item.datasource;
	}
	var msglabel = "";
	if(item.msglabel != undefined){
		msglabel = item.msglabel;
	}
	
	var element =  "" 
		+ "<input type=\"hidden\" name=\"formElement\" "
		+ "value='\"label\":\""+item.label
		+ "\",\"datatype\":\""+item.datatype
		+ "\",\"physicaldatatype\":\""+physicaldatatype
		+ "\",\"columnname\":\""+item.columnname
		+ "\",\"rowspan\":\""+rowspan
		+ "\",\"colspan\":\""+colspan
		+ "\",\"datasource\":\""+datasource
		+ "\",\"minvalue\":\""+minvalue
		+ "\",\"maxvalue\":\""+maxvalue
		+ "\",\"formatcode\":\""+formatcode
		+ "\",\"tablename\":\""+tablename
		+ "\",\"tablecode\":\""+tablecode
		+ "\",\"tablecolumnname\":\""+tablecolumnname
		+ "\",\"tablecolumncode\":\""+tablecolumncode
		+ "\",\"connectionmsg\":\""
		+ "\",\"transitiontype\":\""
		+ "\",\"actiontype\":\""
		+ "\",\"parameters\":\""
		+ "\",\"screenactionid\":\""
		+ "\",\"linklabel\":\""
		+ "\",\"toscreenid\":\""
		+ "\",\"maxlength\":\""+maxlength
		+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
	
	
		if(item.msgvalue != undefined){
			element += "\",\"msglabel\":\""+msglabel
			+ "\",\"msgvalue\":\""+item.msgvalue;
		} else {
			element += "\",\"msglabel\":\"\",\"msgvalue\":\"";
		}
		
		element += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		
	if(dataType == 0){
		elementTD += "<span>" +
				"<div ondblclick=\"openDialogAutocompleteSetting(this)\" class=\"input-group date\" style=\"width:100%\"><input type=\"text\" ";
				if(item.elementtype == 0){
					elementTD += " class=\"form-control ui-autocomplete-input\" ";
				} else {
					elementTD += " class=\"qp-input-autocomplete\" ";
				}
				elementTD += " name=\"" 
				+ item.columnname + "1\" maxlength=\"" 
				+ item.maxlength
				+		" optionvalue=\"optionValue\" optionlabel=\"optionLabel\" selectsqlid=\"\" emptylabel=\"\" onselectevent=\"\" onchangeevent=\"\" onremoveevent=\"\" mustmatch=\"true\" minlength=\"0\" arg01=\"\" arg02=\"\" arg03=\"\" arg4=\"\" arg05=\"\" arg06=\"\" arg07=\"\" arg08=\"\" arg09=\"\" arg10=\"\" arg11=\"\" arg12=\"\" arg13=\"\" arg14=\"\" arg15=\"\" arg16=\"\" arg17=\"\" arg18=\"\" arg19=\"\" arg20=\"\" placeholder=\"\" autocomplete=\"off\">"
				+ "	<input type=\"hidden\" value=\"\">" 
				+ "	<span class=\"input-group-addon dropdown-toggle\" data-dropdown=\"dropdown\">" 
				+ "		<span class=\"caret\"></span> " 
				+ "	</span>"
				+ "</div>"+element+"</span>"
				"";		
	}
	if(dataType == 1 || dataType == 15 || dataType == 16 || dataType == 18){
		elementTD += "<span style=\"width: 100%;\"><input ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"form-control qp-input-text\"";
					} else {
						elementTD += "class=\"form-control qp-input-text\"";
					}
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" />"+element+"</span>";
	}
	if(dataType == 21){
		elementTD += "<span style=\"width: 100%;\"><Label ondblclick=\"openDialogComponentSetting(this)\"";					
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" >"+((item.labelText != undefined && item.labelText != null && item.labelText.length > 0)?item.labelText:item.columnname)+"</label>"+element+"</span>";
	}
	if(dataType == 2){
		elementTD += "<span style=\"width: 100%;\"><input  ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"form-control\"";
					} else {
						elementTD += "class=\"form-control\"";
					}
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" />"+element+"</span>";
	}
	if(dataType == 3){
		elementTD += "<span style=\"width: 100%;\"><input  ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"form-control\"";
					} else {
						elementTD += "class=\"form-control\"";
					}
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" />"+element+"</span>";
	}
	if(dataType == 8){
		elementTD += "<span style=\"width: 100%;\"><input ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"form-control\"";
					} else {
						elementTD += "class=\"form-control\"";
					}
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" />"+element+"</span>";
	}
	if(dataType == 4){
		elementTD += "<span><div class=\"input-group date\">"
		elementTD += "<span><input ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control\"";
						}
		elementTD += " name=\"" 
							+ item.columnname + "1\" maxlength=\"" 
							+ item.maxlength + "\" />"+element+"</span>";
		elementTD += "<span class=\"input-group-addon\">"
							+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
							+ "</span>"
						+ "</div></span>";
	}
	if(dataType == 14){
		
		elementTD += "<span><div class=\"input-group date\">"
		elementTD += "<span><input ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control \"";
						}
		elementTD += " name=\"" 
						+ item.columnname + "1\" maxlength=\"" 
						+ item.maxlength + "\" />"+element+"</span>";
		elementTD += "<span class=\"input-group-addon\">"
						+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
						+ "</span>"
					+ "</div></span>";
	}
	if(dataType == 9){
		
		elementTD += "<span><div class=\"input-group date\">"
			elementTD += "<span><input ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control\"";
						}
			elementTD += " name=\"" 
							+ item.columnname + "1\" maxlength=\"" 
							+ item.maxlength + "\" />"+element+"</span>";
			elementTD += "<span class=\"input-group-addon\">"
							+ "<span class=\"glyphicon glyphicon-time\"></span>"
							+ "</span>"
						+ "</div></span>";			
	}
	if(dataType == 5){
		if(item.msgvalue != undefined && item.msgvalue.length > 0){
			var msgLabelArr = item.msglabel.split("�");
			var msgValArr = item.msgvalue.split("�");
			elementTD += "<span ondblclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\">";
			$(msgValArr).each(function(j){
				elementTD += "<input  type=\"radio\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				}
				if(item.msglabel == undefined || item.msglabel.length == 0  || (msgLabelArr[j] == null || msgLabelArr[j] == 'null')){					
					elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+msgValArr[j]+"</label>";
				} else {					
					elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+msgLabelArr[j]+"</label>";
				}
			});
			elementTD += ""+element+"</span>";
		} else {
			elementTD += "<span ondblclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\"><input type=\"radio\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				}
			elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
			elementTD += "<input type=\"radio\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				}
			elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>"+element+"</span>";
		}
	}
	if(dataType == 6){
		if(item.msgvalue != undefined && item.msgvalue.length > 0){
			var msgLabelArr = item.msglabel.split("�");
			var msgValArr = item.msgvalue.split("�");
			elementTD += "<span ondblclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\" >";
			$(msgValArr).each(function(j){
				elementTD += "<input type=\"checkbox\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				} else {
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				}				
				if(item.msglabel == undefined || item.msglabel.length == 0  || (msgLabelArr[j] == null || msgLabelArr[j] == 'null')){					
					elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+msgValArr[j]+"</label>";
				} else {					
					elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+msgLabelArr[j]+"</label>";
				}				
			});
			elementTD += ""+element+"</span>";
		} else {
			if (item.physicaldatatype == undefined || item.physicaldatatype == '' || item.physicaldatatype != 8) {
				elementTD += "<span ondblclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\"><input type=\"checkbox\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
					} else {
						elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
					}
				elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
			}
			elementTD += "<input type=\"checkbox\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				} else {
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				}
			elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>"+element+"</span>";
		}
	}
	if(dataType == 7){
		elementTD += "<span><select ondblclick=\"openDialogComponentListSetting(this)\" name=\""+item.columnname+"\" ";
		if(item.elementtype == 0){
			elementTD += "class=\"form-control qp-input-select\"";
		} else {
			elementTD += "class=\"form-control qp-input-select\"";
		}
		elementTD += ">";
		if(item.msgvalue != undefined && item.msgvalue.length > 0){
			var msgLabelArr = item.msglabel.split("�");
			var msgValArr = item.msgvalue.split("�");
			
			$(msgValArr).each(function(j){
				if(item.msglabel == undefined || item.msglabel.length == 0  || (msgLabelArr[j] == null || msgLabelArr[j] == 'null')){
					elementTD += "<option>" + msgValArr[j] + "</option>";
				} else {
					elementTD += "<option>" + msgLabelArr[j] + "</option>";
				}
			});
		} else {
			elementTD += "<option>"+dbMsgSource['sc.screendesign.0137']+"1</option><option>"+dbMsgSource['sc.screendesign.0137']+"2</option>";
		}
		elementTD += "</select>"+element+"</span>";
	}
	if(dataType == 10){
		elementTD += "<span><textarea ondblclick=\"openDialogComponentSetting(this)\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"form-control qp-input-textarea\"";
					} else {
						elementTD += "class=\"form-control qp-input-textarea\"";
					}
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" ></textarea>"+element+"</span>";
	}
	if(dataType == 11){			
		var elementButton =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""+item.label
			+ "\",\"datatype\":\""+item.datatype
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+item.columnname
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+item.labelText
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
			if(item.msgvalue != undefined){
				elementButton += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+item.msgvalue;
			} else {
				elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<span><a ondblclick=\"openDialogLinkAreaSetting(this);\" href=\"javascript:\">"+item.labelText+"</a>"+elementButton+"</span>";
	}
	if(dataType == 12){
		elementTD += "<span><input onclick=\"event.stopPropagation();return false;\" ondblclick=\"event.stopPropagation();openDialogComponentSetting(this);return false;\" type=\"file\" name=\"" + item.columnname + "1\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-file\"";
					} else {
						elementTD += "class=\"qp-input-file\"";
					}
		elementTD += "/>"+element+"</span>";
	}
	if(dataType == 13){
		var elementButton =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""+item.label
			+ "\",\"datatype\":\""+item.datatype
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+item.columnname
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+item.labelText
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
			if(item.msgvalue != undefined){
				elementButton += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+item.msgvalue;
			} else {
				elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<span><button style=\""+item.style+"\" type=\"button\" class=\"btn qp-button\" ondblclick=\"openDialogButtonAreaSetting(this)\" value=\""+item.labelText+"\">"+item.labelText+"</button>"+elementButton+"</span>";
	}
	if(dataType == 20){
		if (item.labelText == undefined || item.labelText == null) {
			item["labelText"] = dbMsgSource['sc.screendesign.0050'];
		}
		var elementLabel =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""+item.label
			+ "\",\"datatype\":\""+item.datatype
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+item.columnname
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+item.labelText
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength
			+ "\",\"actiontype\":\""+item.actiontype
			+ "\",\"actionName\":\""+item.actionName
			+ "\",\"parameters\":\""+item.parameters
			+ "\",\"navigateTo\":\""+item.navigateTo
			+ "\",\"navigateToText\":\""+item.navigateToText
			+ "\",\"style\":\""+item.style
			+ "\",\"hoverStyle\":\""+item.hoverStyle
			+ "\",\"icon\":\""+item.icon
			+ "\",\"showLabel\":\""+item.showLabel
			+ "\",\"isSubmit\":\""+item.isSubmit
			+ "\",\"itemType\":\"5"
			;
		
		
			if(item.msgvalue != undefined){
				elementLabel += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+item.msgvalue;
			} else {
				elementLabel += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementLabel += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
elementTD += "<span><label style=\"cursor: pointer;"+item.style+"\" ondblclick=\"openDialogLableSetting(this, true)\">"+unescape(item.labelText)+"</label>"+elementLabel+"</span>";
	}	
	return elementTD;
}

function returnElementTDLoad(item, displayGroupType){	
	var dataType = item.datatype;
	// daipv split style for Div and input tag
	var itemStyleDiv = "";
	var itemStyleInput = "";
	var arrStyle = item.style.split(";");
	for (var i = 0; i < arrStyle.length; i++) {
		if(arrStyle[i].indexOf("margin-") > -1){
			itemStyleDiv += arrStyle[i] + ";";
		} else {
			itemStyleInput += arrStyle[i] + ";";
		}
	}
	itemStyleDiv = itemStyleDiv.slice(0, -1);
	itemStyleInput = itemStyleInput.slice(0, -1);
	
	var elementTD = "";
	var tablecolumncode = "";
	if(item.tablecolumncode != undefined){
		tablecolumncode = item.tablecolumncode;
	}
	var tablename = "";
	if(item.tablename != undefined){
		tablename = item.tablename;
	}
	var tablecode = "";
	if(item.tablecode != undefined){
		tablecode = item.tablecode;
	}
	var tablecolumnname = "";
	if(item.tablecolumnname != undefined){
		tablecolumnname = item.tablecolumnname;
	}
	
	var physicaldatatype = "";
	if(item.physicaldatatype != undefined){
		physicaldatatype = item.physicaldatatype;
	}
	var minvalue = "";
	if(item.minvalue != undefined){
		minvalue = item.minvalue;
	}
	var maxvalue = "";
	if(item.maxvalue != undefined){
		maxvalue = item.maxvalue;
	}
	var formatcode = "";
	if(item.formatcode != undefined){
		formatcode = item.formatcode;
	}
	var physicalmaxlength = "";
	if(item.physicalmaxlength != undefined){
		physicalmaxlength = item.physicalmaxlength;
	}
	var maxlength = "";
	if(item.maxlength != undefined){
		maxlength = item.maxlength;
	}
	var require = "";
	if(item.require != undefined){
		require = item.require;
	}
	var rowspan = "";
	if(item.rowspan != undefined){
		rowspan = item.rowspan;
	}
	var colspan = "";
	if(item.colspan != undefined){
		colspan = item.colspan;
	}
	var datasource = "";
	if(item.datasource != undefined){
		datasource = item.datasource;
	}
	var msglabel = "";
	if(item.msglabel != undefined){
		msglabel = item.msglabel;
	}
	
	var labelText = "";
	if(item.labelText != undefined){
		labelText = item.labelText;
	}
	
	var screenDesignIdCodeListId = "";
	if(item.screenDesignIdCodeListId != undefined){
		screenDesignIdCodeListId = item.screenDesignIdCodeListId;
	}
	
	var screenItemIdCodeListId = "";
	if(item.screenItemIdCodeListId != undefined){
		screenItemIdCodeListId = item.screenItemIdCodeListId;
	}
	
	var screenDesignTextCodeListId = "";
	if(item.screenDesignTextCodeListId != undefined){
		screenDesignTextCodeListId = item.screenDesignTextCodeListId;
	}
	
	var screenItemTextCodeListId = "";
	if(item.screenItemTextCodeListId != undefined){
		screenItemTextCodeListId = item.screenItemTextCodeListId;
	}
	var messageLevel = "";
	var messageLevelText = "";
	if(item.messageLevel != "" && item.messageLevel != undefined) {
		messageLevel = item.messageLevel;
	}
	if(item.messageLevelText != "" && item.messageLevelText != undefined) {
		messageLevelText = item.messageLevelText;
	}
	var events = "[]";
	
	if (item.events != undefined && item.events != null && item.events != 'null') {
		events = convertToString(item.events);
	}
	
	if (events == null || events == 'null') {
		events = "[]";
	}
	
	var element =  "" 
		+ "<input type=\"hidden\" name=\"formElement\" "
		+ "value='\"label\":\""+item.label
		+ "\",\"labelText\":\""+labelText
		+ "\",\"datatype\":\""+item.datatype
		+ "\",\"physicaldatatype\":\""+physicaldatatype
		+ "\",\"columnname\":\""+item.columnname
		+ "\",\"rowspan\":\""+rowspan
		+ "\",\"colspan\":\""+colspan
		+ "\",\"datasource\":\""+datasource
		+ "\",\"minvalue\":\""+minvalue
		+ "\",\"maxvalue\":\""+maxvalue
		+ "\",\"formatcode\":\""+formatcode
		+ "\",\"tablename\":\""+tablename
		+ "\",\"tablecode\":\""+tablecode
		+ "\",\"tablecolumnname\":\""+tablecolumnname
		+ "\",\"tablecolumncode\":\""+tablecolumncode
		+ "\",\"connectionmsg\":\""
		+ "\",\"transitiontype\":\""
		+ "\",\"actiontype\":\""
		+ "\",\"parameters\":\"" + item.parameters
		+ "\",\"screenactionid\":\""
		+ "\",\"linklabel\":\""
		+ "\",\"toscreenid\":\""
		+ "\",\"baseType\":\""+item.baseType
		+ "\",\"codelistCode\":\""+item.codelistCode
		+ "\",\"codelistText\":\""+item.codelistText
		+ "\",\"isEnable\":\""+item.isEnable
		+ "\",\"localCodelist\":\""+item.localCodelist
		+ "\",\"isSupportOptionValue\":\""+item.isSupportOptionValue
		+ "\",\"maxlength\":\""+maxlength
		+ "\",\"dialogAutocompleteCode\":\""+item.dialogAutocompleteCode
		+ "\",\"dialogAutocompleteText\":\""+item.dialogAutocompleteText
		+ "\",\"dialogOnChangeEvent\":\""+item.dialogOnChangeEvent
		+ "\",\"width\":\""+item.width
		+ "\",\"widthunit\":\""+item.widthunit
		+ "\",\"baseType\":\""+item.baseType
		+ "\",\"dialogOnSelectEvent\":\""+item.dialogOnSelectEvent
		+ "\",\"mandatory\":\""+item.mandatory
		+ "\",\"outputBeanId\":\""+item.outputBeanId
		+ "\",\"tabindex\":\""+item.tabindex
		+ "\",\"events\":"+events
		+ ",\"physicalmaxlength\":\""+physicalmaxlength
		+ "\",\"datasourcetype\":\""+item.datasourcetype
		+ "\",\"sqldesignid\":\""+item.sqldesignid
		+ "\",\"sqldesignidtext\":\""+item.sqldesignidtext
		+ "\",\"optionlabel\":\""+item.optionlabel
		+ "\",\"optionlabeltext\":\""+item.optionlabeltext
		+ "\",\"optionvalue\":\""+item.optionvalue
		+ "\",\"optionvaluetext\":\""+item.optionvaluetext
		+ "\",\"screenItemId\":\""+item.screenItemId
		+ "\",\"style\":\""+item.style
		+ "\",\"hoverStyle\":\""+item.hoverStyle
		+ "\",\"icon\":\""+item.icon
		+ "\",\"showLabel\":\""+item.showLabel
		+ "\",\"enablePassword\":\""+item.enablePassword
		+ "\",\"allowAnyInput\":\""+item.allowAnyInput
		+ "\",\"buttonStyle\":\""+item.buttonStyle
		+ "\",\"navigateToBlogic\":\""+item.navigateToBlogic
		+ "\",\"navigateToBlogicText\":\""+item.navigateToBlogicText
		+ "\",\"messageConfirm\":\""+item.messageConfirm
		+ "\",\"messageConfirmText\":\""+item.messageConfirmText
		+ "\",\"enableConfirm\":\""+item.enableConfirm
		+ "\",\"messageConfirmCode\":\""+item.messageConfirmCode
		+ "\",\"screenDesignIdCodeListId\":\""+screenDesignIdCodeListId
		+ "\",\"screenItemIdCodeListId\":\""+screenItemIdCodeListId
		+ "\",\"screenDesignTextCodeListId\":\""+screenDesignTextCodeListId
		+ "\",\"screenItemTextCodeListId\":\""+screenItemTextCodeListId
		+ "\",\"showBlankItem\":\""+item.showBlankItem
		+ "\",\"screenTransition\":\""+item.screenTransition
		+ "\",\"screenTransitionText\":\""+item.screenTransitionText
		+ "\",\"messageLevel\":\""+messageLevel
		+ "\",\"messageLevelText\":\""+messageLevelText
		
		+ "\",\"defaultvalue\":\""+item.defaultvalue;
	if(item.msgvalue != undefined){
		element += "\",\"msglabel\":\""+msglabel
		+ "\",\"msgvalue\":\""+item.msgvalue;
	} else {
		element += "\",\"msglabel\":\"\",\"msgvalue\":\"";
	}
	
	element += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
	
	var styleDisplayGroupType = "";
	if (displayGroupType != undefined && displayGroupType != null && displayGroupType == "2") {
		styleDisplayGroupType="clear: both;";
	}
	
	var styleWidth = "100%";
	
	if (item.width != null && item.width != "" && item.width.length > 0) {
		styleWidth = item.width + item.widthunit;
	}
	
	var style = 'style="width: '+styleWidth+'; float: left;'+styleDisplayGroupType+'"';
	var styleButton = 'style="float: left;'+styleDisplayGroupType+'"';
	var buttonWidth = "";
	if(item.width != undefined) {
		buttonWidth += 'width:'+item.width+'';
	}
	if(item.widthunit != undefined) {
		buttonWidth += item.widthunit;
	}
	
	if (item.groupitemtype == 1) {
		style = 'style="width: '+styleWidth+'; float: left; padding-right: 3px; padding-bottom: 3px;'+styleDisplayGroupType+'"';
		styleButton = 'style="float: left; padding-right: 3px; padding-bottom: 3px;'+styleDisplayGroupType+';'+buttonWidth+'"';
	}
	style1 = style.slice(0, -1);
	
	if(dataType == 0){
		elementTD += "<span "+style+">" +
				"<div ondblclick=\"openDialogAutocompleteSetting(this)\" class=\"input-group\" style=\"width:100%;"+ item.inputStyle + ' ' +itemStyleDiv+"\"><input style=\""+ item.inputStyle + ' '+itemStyleInput+"\" type=\"text\"";
				if(item.elementtype == 0){
					elementTD += " class=\"form-control ui-autocomplete-input\" ";
				} else {
					elementTD += " class=\"qp-input-autocomplete\" ";
				}
				elementTD += " name=\"" 
				+ item.columnname + "1\" maxlength=\"" 
				+ item.maxlength
				+		" optionvalue=\"optionValue\" optionlabel=\"optionLabel\" selectsqlid=\"\" emptylabel=\"\" onselectevent=\"\" onchangeevent=\"\" onremoveevent=\"\" mustmatch=\"true\" minlength=\"0\" arg01=\"\" arg02=\"\" arg03=\"\" arg4=\"\" arg05=\"\" arg06=\"\" arg07=\"\" arg08=\"\" arg09=\"\" arg10=\"\" arg11=\"\" arg12=\"\" arg13=\"\" arg14=\"\" arg15=\"\" arg16=\"\" arg17=\"\" arg18=\"\" arg19=\"\" arg20=\"\" placeholder=\"\" autocomplete=\"off\">"
				+ "	<input type=\"hidden\" value=\"\">" 
				+ "	<span class=\"input-group-addon dropdown-toggle\" data-dropdown=\"dropdown\">" 
				+ "		<span class=\"caret\"></span> " 
				+ "	</span>"
				+ "</div>"+element+"</span>"
				"";		
	}
	if(dataType == 1 || dataType == 15 || dataType == 16 || dataType == 18){
		
		var dataTypeElement = "text";
		
		if (dataType == 1) {
			if (item.enablePassword != undefined && item.enablePassword != null && item.enablePassword == 1) {
				dataTypeElement = "password";
			}
		}
		
		elementTD += "<span "+style+"><input style=\""+ item.inputStyle + ' ' +item.style+"\" ondblclick=\"openDialogComponentSetting(this)\" type=\""+dataTypeElement+"\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"form-control qp-input-text\"";
					} else {
						elementTD += "class=\"form-control qp-input-text\"";
					}
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" />"+element+"</span>";
	}
	if(dataType == 21){
		var labelText = ((item.labelText != undefined && item.labelText != null && item.labelText.length > 0)?item.columnname:item.columnname);
		
		if (labelText == undefined || labelText == null || labelText.length == 0){
			labelText="Blank";
		}
		
		elementTD += "<span "+style+"><Label style=\""+ item.inputStyle + ' ' +item.style+"\" ondblclick=\"openDialogComponentSetting(this)\"";					
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" >"+labelText+"</label>"+element+"</span>";
	}
	if(dataType == 2){
		elementTD += "<span "+style+"><input style=\""+ item.inputStyle + ' ' +item.style+" qp-input-integer\"  ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"form-control qp-input-integer\"";
					} else {
						elementTD += "class=\"form-control qp-input-integer\"";
					}
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" />"+element+"</span>";
	}
	if(dataType == 3){
		elementTD += "<span "+style+"><input style=\""+ item.inputStyle + ' ' +item.style+"\" ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"form-control\"";
					} else {
						elementTD += "class=\"form-control\"";
					}
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" />"+element+"</span>";
	}
	if(dataType == 8){
		elementTD += "<span "+style+"><input style=\""+ item.inputStyle + ' ' +item.style+" qp-input-currency\" ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"form-control qp-input-currency\"";
					} else {
						elementTD += "class=\"form-control qp-input-currency\"";
					}
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" />"+element+"</span>";
	}
	if(dataType == 4){
		elementTD += "<span "+style1+ ' ' + itemStyleDiv +  '"' +"><div  class=\"input-group date\">"
		elementTD += "<span><input style=\""+ item.inputStyle + ' ' +itemStyleInput+"\" ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control\"";
						}
		elementTD += " name=\"" 
							+ item.columnname + "1\" maxlength=\"" 
							+ item.maxlength + "\" />"+element+"</span>";
		elementTD += "<span class=\"input-group-addon\">"
							+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
							+ "</span>"
						+ "</div></span>";
	}
	if(dataType == 14){
		
		elementTD += "<span "+style1+ ' ' + itemStyleDiv +  '"' +"><div class=\"input-group date\">"
		elementTD += "<span><input style=\""+ item.inputStyle + ' ' +itemStyleInput+"\" ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control \"";
						}
		elementTD += " name=\"" 
						+ item.columnname + "1\" maxlength=\"" 
						+ item.maxlength + "\" />"+element+"</span>";
		elementTD += "<span class=\"input-group-addon\">"
						+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
						+ "</span>"
					+ "</div></span>";
	}
	if(dataType == 9){
		
		elementTD += "<span "+style1+ ' ' + itemStyleDiv +  '"' +"><div class=\"input-group date\">"
			elementTD += "<span><input style=\""+ item.inputStyle + ' ' +itemStyleInput+"\" ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control\"";
						}
			elementTD += " name=\"" 
							+ item.columnname + "1\" maxlength=\"" 
							+ item.maxlength + "\" />"+element+"</span>";
			elementTD += "<span class=\"input-group-addon\">"
							+ "<span class=\"glyphicon glyphicon-time\"></span>"
							+ "</span>"
						+ "</div></span>";			
	}
	if(dataType == 5){
		if(item.msgvalue != undefined && item.msgvalue.length > 0){
			var msgLabelArr = item.msglabel.split("�");
			var msgValArr = item.msgvalue.split("�");
			elementTD += "<span "+style+" ondblclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\">";
			$(msgValArr).each(function(j){
				elementTD += "<input  type=\"radio\" value=\""+msgValArr[j]+"\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				}
				if(item.msglabel == undefined || item.msglabel.length == 0  || (msgLabelArr[j] == null || msgLabelArr[j] == 'null')){					
					elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' ' +item.style+"\" for=\""+item.columnname+"\">"+deEscapeHTMLForView(msgValArr[j])+"</label>";
				} else {					
					elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' ' +item.style+"\" for=\""+item.columnname+"\">"+deEscapeHTMLForView(msgLabelArr[j])+"</label>";
				}
			});
			elementTD += ""+element+"</span>";
		} else {
			elementTD += "<span "+style+" ondblclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\"><input type=\"radio\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				}
			elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' ' +item.style+"\" for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
			elementTD += "<input type=\"radio\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				}
			elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+ item.inputStyle + ' ' +item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>"+element+"</span>";
		}
	}
	if(dataType == 6){
		if(item.msgvalue != undefined && item.msgvalue.length > 0){
			var msgLabelArr = item.msglabel.split("�");
			var msgValArr = item.msgvalue.split("�");
			elementTD += "<span "+style+" ondblclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\">";
			$(msgValArr).each(function(j){
				elementTD += "<input  type=\"checkbox\" value=\""+msgValArr[j]+"\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				} else {
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				}				
				if(item.msglabel == undefined || item.msglabel.length == 0  || (msgLabelArr[j] == null || msgLabelArr[j] == 'null')){					
					elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' ' +item.style+"\" for=\""+item.columnname+"\">"+deEscapeHTMLForView(msgValArr[j])+"</label>";
				} else {					
					elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' ' +item.style+"\" for=\""+item.columnname+"\">"+deEscapeHTMLForView(msgLabelArr[j])+"</label>";
				}				
			});
			elementTD += ""+element+"</span>";
		} else {
			elementTD += "<span "+style+" ondblclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\">";
			if (item.physicaldatatype == undefined || item.physicaldatatype == '' || item.physicaldatatype != 8) {
				elementTD += "<input type=\"checkbox\"";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
					} else {
						elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
					}
				elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' ' +item.style+"\" for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
			}
			elementTD += "<input type=\"checkbox\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				} else {
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				}
			elementTD += " name=\"" + item.columnname + "1\" />";
			if (item.physicaldatatype == undefined || item.physicaldatatype == '' || item.physicaldatatype != 8) {
				elementTD += "<label style=\""+ item.inputStyle + ' ' +item.style+"\" for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>";
			}
			elementTD += ""+element+"</span>";
		}
		
	}
	if(dataType == 7){
		elementTD += "<span "+style+"><select style=\""+ item.inputStyle + ' ' +item.style+"\" ondblclick=\"openDialogComponentListSetting(this)\" name=\""+item.columnname+"\" ";
		if(item.elementtype == 0){
			elementTD += "class=\"form-control qp-input-select\"";
		} else {
			elementTD += "class=\"form-control qp-input-select\"";
		}
		elementTD += ">";
		if (item.showBlankItem == "1") {
			elementTD += "<option>" + "&nbsp;" + "</option>";
		}
		if(item.msgvalue != undefined && item.msgvalue.length > 0){
			var msgLabelArr = item.msglabel.split("�");
			var msgValArr = item.msgvalue.split("�");
			$(msgValArr).each(function(j){
				if(item.msglabel == undefined || item.msglabel.length == 0  || (msgLabelArr[j] == null || msgLabelArr[j] == 'null')){
					elementTD += "<option value=\""+msgValArr[j]+"\">" + deEscapeHTMLForView(msgValArr[j]) + "</option>";
				} else {
					elementTD += "<option value=\""+msgValArr[j]+"\">" + deEscapeHTMLForView(msgLabelArr[j]) + "</option>";
				}
			});
		} else {
			elementTD += "<option>"+dbMsgSource['sc.screendesign.0137']+"1</option><option>"+dbMsgSource['sc.screendesign.0137']+"2</option>";
		}
		elementTD += "</select>"+element+"</span>";
	}
	if(dataType == 10){
		elementTD += "<span "+style+"><textarea style=\""+ item.inputStyle + ' ' +item.style+"\" ondblclick=\"openDialogComponentSetting(this)\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"form-control qp-input-textarea\"";
					} else {
						elementTD += "class=\"form-control qp-input-textarea\"";
					}
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" ></textarea>"+element+"</span>";
	}
	if(dataType == 11){		
		var elementButton =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""+item.label
			+ "\",\"datatype\":\""+item.datatype
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+item.columnname
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+item.labelText
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength
			+ "\",\"actiontype\":\""+item.actiontype
			+ "\",\"actionName\":\""+item.actionName
			+ "\",\"parameters\":\""+item.parameters
			+ "\",\"navigateTo\":\""+item.navigateTo
			+ "\",\"navigateToText\":\""+item.navigateToText
			+ "\",\"navigateToBlogic\":\""+item.navigateToBlogic
			+ "\",\"navigateToBlogicText\":\""+item.navigateToBlogicText
			+ "\",\"style\":\""+item.style
			+ "\",\"hoverStyle\":\""+item.hoverStyle
			+ "\",\"messageConfirm\":\""+item.messageConfirm
			+ "\",\"messageConfirmText\":\""+item.messageConfirmText
			+ "\",\"enableConfirm\":\""+item.enableConfirm
			+ "\",\"messageConfirmCode\":\""+item.messageConfirmCode
			+ "\",\"screenItemId\":\""+item.screenItemId
			+ "\",\"screenTransition\":\""+item.screenTransition
			+ "\",\"messageLevel\":\""+messageLevel
			+ "\",\"tabindex\":\""+item.tabindex
			+ "\",\"messageLevelText\":\""+messageLevelText
			+ "\",\"screenTransitionText\":\""+item.screenTransitionText
			+ "\",\"isSubmit\":\""+item.isSubmit
			;
		
			if(item.msgvalue != undefined){
				elementButton += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+item.msgvalue;
			} else {
				elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<span "+style+"><a style=\""+ item.inputStyle + ' ' +item.style+"\" onmouseover=\"this.setAttribute('style', '"+item.hoverStyle+"')\" onmouseout=\"this.setAttribute('style', '"+item.style+"')\" ondblclick=\"openDialogLinkAreaSetting(this);\" href=\"javascript:\">"+item.labelText+"</a>"+elementButton+"</span>";
	}
	var width = ""
	if(item.width != undefined) {
		width = item.width;
	} else {
		width = "100%";
	}
	if(dataType == 22){
		var localCodelist = "";
		var datasourcetype = "";
		if(item.localCodelist != undefined) {
			localCodelist = item.localCodelist;
		}
		if(item.datasourcetype != undefined) {
			datasourcetype = item.datasourcetype;
		}
		
		var elementButton =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""+item.label
			+ "\",\"datatype\":\""+item.datatype
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+item.columnname
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+item.labelText
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength
			+ "\",\"actiontype\":\""+item.actiontype
			+ "\",\"actionName\":\""+item.actionName
			+ "\",\"parameters\":\""+item.parameters
			+ "\",\"navigateTo\":\""+item.navigateTo
			+ "\",\"navigateToText\":\""+item.navigateToText
			+ "\",\"navigateToBlogic\":\""+item.navigateToBlogic
			+ "\",\"navigateToBlogicText\":\""+item.navigateToBlogicText
			+ "\",\"style\":\""+item.style
			+ "\",\"hoverStyle\":\""+item.hoverStyle
			+ "\",\"messageConfirm\":\""+item.messageConfirm
			+ "\",\"messageConfirmText\":\""+item.messageConfirmText
			+ "\",\"enableConfirm\":\""+item.enableConfirm
			+ "\",\"messageConfirmCode\":\""+item.messageConfirmCode
			+ "\",\"screenItemId\":\""+item.screenItemId
			+ "\",\"width\":\""+width
			+ "\",\"widthunit\":\""+item.widthunit
			+ "\",\"isSubmit\":\""+item.isSubmit
			+ "\",\"screenDesignIdCodeListId\":\""+screenDesignIdCodeListId
			+ "\",\"screenItemIdCodeListId\":\""+screenItemIdCodeListId
			+ "\",\"screenDesignTextCodeListId\":\""+screenDesignTextCodeListId
			+ "\",\"screenItemTextCodeListId\":\""+screenItemTextCodeListId
			+ "\",\"codelistCode\":\""+item.codelistCode
			+ "\",\"codelistText\":\""+item.codelistText
			+ "\",\"localCodelist\":\""+localCodelist
			+ "\",\"screenTransition\":\""+item.screenTransition
			+ "\",\"screenTransitionText\":\""+item.screenTransitionText
			+ "\",\"messageLevel\":\""+messageLevel
			+ "\",\"tabindex\":\""+item.tabindex
			+ "\",\"messageLevelText\":\""+messageLevelText
			+ "\",\"datasourcetype\":\""+datasourcetype
			;
		
			if(item.msgvalue != undefined){
				elementButton += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+item.msgvalue;
			} else {
				elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<span "+style+"><a style=\""+ item.inputStyle + ' ' +item.style+"\" onmouseover=\"this.setAttribute('style', '"+item.hoverStyle+"')\" onmouseout=\"this.setAttribute('style', '"+item.style+"')\" ondblclick=\"openDialogLinkAreaSetting(this);\" href=\"javascript:\">"+item.columnname+"</a>"+elementButton+"</span>";
	}
	
	if(dataType == 23){
		var elementLabel =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""
			+ "\",\"datatype\":\""+item.datatype
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+item.columnname
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+item.labelText
			+ "\",\"isBundle\":\""+ "false"
			+ "\",\"isSubmit\":\""+ "false"
			+ "\",\"navigateTo\":\""
			+ "\",\"navigateToText\":\""
			+ "\",\"customItemContent\":\"" + item.customItemContent
			+ "\",\"itemType\":\"5"
			+ "\",\"screenItemId\":\""+item.screenItemId
			+ "\",\"screenTransition\":\""+item.screenTransition
			+ "\",\"screenTransitionText\":\""+item.screenTransitionText
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
		if(item.msgvalue != undefined){
			elementLabel += "\",\"msglabel\":\""+msglabel
			+ "\",\"msgvalue\":\""+item.msgvalue;
		} else {
			elementLabel += "\",\"msglabel\":\"\",\"msgvalue\":\"";
		}
		
		elementLabel += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<span "+style+"><label class=\"qp-item-custom\" ondblclick=\"openDialogCustomSetting(this, true)\" style=\"cursor: pointer;\">"+item.labelText+"</label>"+elementLabel+"</span>";
	}	
	
	if(dataType == 12){
		elementTD += "<span "+style+"><input onclick=\"event.stopPropagation();return false;\" ondblclick=\"event.stopPropagation();openDialogComponentSetting(this);return false;\" type=\"file\" name=\"" + item.columnname + "1\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-file\"";
					} else {
						elementTD += "class=\"qp-input-file\"";
					}
		elementTD += "/>"+element+"</span>";
	}
	
	if(dataType == 13){
		/*style += styleButton;*/
		var elementButton =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""+item.label
			+ "\",\"datatype\":\""+item.datatype
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+item.columnname
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+item.labelText
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength
			+ "\",\"actiontype\":\""+item.actiontype
			+ "\",\"actionName\":\""+item.actionName
			+ "\",\"parameters\":\""+item.parameters
			+ "\",\"navigateTo\":\""+item.navigateTo
			+ "\",\"navigateToText\":\""+item.navigateToText
			+ "\",\"style\":\""+item.style
			+ "\",\"hoverStyle\":\""+item.hoverStyle
			+ "\",\"icon\":\""+item.icon
			+ "\",\"showLabel\":\""+item.showLabel
			+ "\",\"buttonStyle\":\""+item.buttonStyle
			+ "\",\"navigateToBlogic\":\""+item.navigateToBlogic
			+ "\",\"navigateToBlogicText\":\""+item.navigateToBlogicText
			+ "\",\"messageConfirm\":\""+item.messageConfirm
			+ "\",\"messageConfirmText\":\""+item.messageConfirmText
			+ "\",\"enableConfirm\":\""+item.enableConfirm
			+ "\",\"messageConfirmCode\":\""+item.messageConfirmCode
			+ "\",\"screenItemId\":\""+item.screenItemId
			+ "\",\"width\":\""+item.width
			+ "\",\"widthunit\":\""+item.widthunit
			+ "\",\"screenTransition\":\""+item.screenTransition
			+ "\",\"screenTransitionText\":\""+item.screenTransitionText
			+ "\",\"messageLevel\":\""+messageLevel
			+ "\",\"tabindex\":\""+item.tabindex
			+ "\",\"messageLevelText\":\""+messageLevelText
			+ "\",\"isSubmit\":\""+item.isSubmit
			;
		
			if(item.msgvalue != undefined){
				elementButton += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+item.msgvalue;
			} else {
				elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
			
			var buttonStyle = "";
			if (item.buttonStyle != undefined) {
				if (item.buttonStyle == 0 || item.buttonStyle == 1) {
					buttonStyle = "qp-button-type-design";
				} else if (item.buttonStyle == 2) {
					buttonStyle = "qp-button-type-warning-design";
				} else if (item.buttonStyle == 3) {
					buttonStyle = "qp-button-type-client-design";
				}
			}
			
		if (item.icon != undefined && item.showLabel != undefined && item.icon.length > 0) {
			var buttonContent = "<i class=\""+item.icon+"\"></i>";
			if ((item.showLabel == '1' || item.showLabel == 1) && item.labelText != undefined && item.labelText != null && item.labelText.length > 0) {
				buttonContent += "&nbsp;&nbsp;" + item.labelText;
			}
			elementTD += "<span "+styleButton+"><button style=\""+ item.inputStyle + ' ' +item.style+" ; width:100% \" type=\"button\" class=\"btn qp-button "+buttonStyle+"\" ondblclick=\"openDialogButtonAreaSetting(this)\" value=\""+item.labelText+"\">"+buttonContent+"</button>"+elementButton+"</span>";
		} else {
			elementTD += "<span "+styleButton+"><button style=\""+ item.inputStyle + ' ' +item.style+" ; width:100%\" type=\"button\" class=\"btn qp-button "+buttonStyle+"\" ondblclick=\"openDialogButtonAreaSetting(this)\" value=\""+item.labelText+"\">"+item.labelText+"</button>"+elementButton+"</span>";
		}
		
	}
	if(dataType == 20){
		if (item.labelText == undefined || item.labelText == null) {
			item["labelText"] = dbMsgSource['sc.screendesign.0050'];
		}
		var width = "";
		if (item.width != undefined || item.width != null) {
			width = item.width;
		}
		var widthunit = "";
		if (item.widthunit != undefined || item.widthunit != null) {
			widthunit = item.widthunit;
		}
		
		var elementLabel =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""+item.label
			+ "\",\"datatype\":\""+item.datatype
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+item.columnname
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+item.labelText
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength
			+ "\",\"actiontype\":\""+item.actiontype
			+ "\",\"actionName\":\""+item.actionName
			+ "\",\"parameters\":\""+item.parameters
			+ "\",\"navigateTo\":\""+item.navigateTo
			+ "\",\"navigateToText\":\""+item.navigateToText
			+ "\",\"style\":\""+item.style
			+ "\",\"hoverStyle\":\""+item.hoverStyle
			+ "\",\"icon\":\""+item.icon
			+ "\",\"showLabel\":\""+item.showLabel
			+ "\",\"isSubmit\":\""+item.isSubmit
			+ "\",\"width\":\""+width
			+ "\",\"widthunit\":\""+widthunit
			+ "\",\"itemType\":\"5"
			;
		
		
			if(item.msgvalue != undefined){
				elementLabel += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+item.msgvalue;
			} else {
				elementLabel += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementLabel += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<span style=\"width: "+width+""+widthunit+";float: left\"><label style=\"cursor: pointer;"+ item.inputStyle + ' ' +item.style+"; width:100%\" ondblclick=\"openDialogLableSetting(this, true)\">"+unescape(item.labelText)+"</label>"+elementLabel+"</span>";
	}	
	return elementTD;
}

function returnElementTDPreview(item, displayGroupType){
	var dataType = item.datatype;
	
	var elementTD = "";
	var tablecolumncode = "";
	if(item.tablecolumncode != undefined){
		tablecolumncode = item.tablecolumncode;
	}
	var tablename = "";
	if(item.tablename != undefined){
		tablename = item.tablename;
	}
	var tablecode = "";
	if(item.tablecode != undefined){
		tablecode = item.tablecode;
	}
	var tablecolumnname = "";
	if(item.tablecolumnname != undefined){
		tablecolumnname = item.tablecolumnname;
	}
	
	var physicaldatatype = "";
	if(item.physicaldatatype != undefined){
		physicaldatatype = item.physicaldatatype;
	}
	var minvalue = "";
	if(item.minvalue != undefined){
		minvalue = item.minvalue;
	}
	var maxvalue = "";
	if(item.maxvalue != undefined){
		maxvalue = item.maxvalue;
	}
	var formatcode = "";
	if(item.formatcode != undefined){
		formatcode = item.formatcode;
	}
	var physicalmaxlength = "";
	if(item.physicalmaxlength != undefined){
		physicalmaxlength = item.physicalmaxlength;
	}
	var maxlength = "";
	if(item.maxlength != undefined){
		maxlength = item.maxlength;
	}
	var require = "";
	if(item.require != undefined){
		require = item.require;
	}
	var rowspan = "";
	if(item.rowspan != undefined){
		rowspan = item.rowspan;
	}
	var colspan = "";
	if(item.colspan != undefined){
		colspan = item.colspan;
	}
	var datasource = "";
	if(item.datasource != undefined){
		datasource = item.datasource;
	}
	var msglabel = "";
	if(item.msglabel != undefined){
		msglabel = item.msglabel;
	}
	
	var element =  "";
	
	var styleDisplayGroupType = "";
	if (displayGroupType != undefined && displayGroupType != null && displayGroupType == "2") {
		styleDisplayGroupType="clear: both;";
	}
	
	var styleWidth = "100%";
	
	if (item.width != null && item.width != "" && item.width.length > 0) {
		styleWidth = item.width + item.widthunit;
	}

	var style = 'style="width: '+styleWidth+'; float: left;'+styleDisplayGroupType+'"';
	var styleButton = 'style="float: left;'+styleDisplayGroupType+'"';
	var buttonWidth = "";
	if(item.width != undefined) {
		buttonWidth += 'width:'+item.width+'';
	}
	if(item.widthunit != undefined) {
		buttonWidth += item.widthunit;
	}
	
	if (item.groupitemtype == 1) {
		style = 'style="width: '+styleWidth+'; float: left; padding-right: 3px; padding-bottom: 3px;'+styleDisplayGroupType+'"';
		styleButton = 'style="float: left; padding-right: 3px; padding-bottom: 3px;'+styleDisplayGroupType+';'+buttonWidth+'"';
	}
	
	if(dataType == 0){
		elementTD += "<span "+style+">" +
				"<div class=\"input-group\" style=\"width:100%;" + itemStyleDiv + "\"><input style=\""+ item.inputStyle + ' '+itemStyleInput+"\" type=\"text\" ";
				if(item.elementtype == 0){
					elementTD += " class=\"form-control ui-autocomplete-input\" ";
				} else {
					elementTD += " class=\"qp-input-autocomplete\" ";
				}
				elementTD += " name=\"" 
				+ item.columnname + "1\" maxlength=\"" 
				+ item.maxlength
				+		" optionvalue=\"optionValue\" optionlabel=\"optionLabel\" selectsqlid=\"\" emptylabel=\"\" onselectevent=\"\" onchangeevent=\"\" onremoveevent=\"\" mustmatch=\"true\" minlength=\"0\" arg01=\"\" arg02=\"\" arg03=\"\" arg4=\"\" arg05=\"\" arg06=\"\" arg07=\"\" arg08=\"\" arg09=\"\" arg10=\"\" arg11=\"\" arg12=\"\" arg13=\"\" arg14=\"\" arg15=\"\" arg16=\"\" arg17=\"\" arg18=\"\" arg19=\"\" arg20=\"\" placeholder=\"\" autocomplete=\"off\">"
				+ "	<input type=\"hidden\" value=\"\">" 
				+ "	<span class=\"input-group-addon dropdown-toggle\" data-dropdown=\"dropdown\">" 
				+ "		<span class=\"caret\"></span> " 
				+ "	</span>"
				+ "</div>"+element+"</span>"
				"";		
	}
	if(dataType == 1 || dataType == 15 || dataType == 16 || dataType == 18){
		elementTD += "<span "+style+"><input style=\""+ item.inputStyle + ' '+item.style+"\" type=\"text\" value=\""+item.defaultvalue+"\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"form-control qp-input-text\"";
					} else {
						elementTD += "class=\"form-control qp-input-text\"";
					}
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" />"+element+"</span>";
	}
	if(dataType == 21){
		elementTD += "<span "+style+"><Label style=\""+ item.inputStyle + ' '+item.style+"\"";					
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" >"+((item.labelText != undefined && item.labelText != null && item.labelText.length > 0)?item.labelText:item.columnname)+"</label>"+element+"</span>";
	}
	if(dataType == 2){
		elementTD += "<span "+style+"><input style=\""+ item.inputStyle + ' '+item.style+" qp-input-integer\" type=\"text\" value=\""+item.defaultvalue+"\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"form-control qp-input-integer\"";
					} else {
						elementTD += "class=\"form-control qp-input-integer\"";
					}
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" />"+element+"</span>";
	}
	if(dataType == 3){
		elementTD += "<span "+style+"><input style=\""+ item.inputStyle + ' '+item.style+"\" type=\"text\" value=\""+item.defaultvalue+"\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"form-control\"";
					} else {
						elementTD += "class=\"form-control\"";
					}
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" />"+element+"</span>";
	}
	if(dataType == 8){
		elementTD += "<span "+style+"><input style=\""+ item.inputStyle + ' '+item.style+" qp-input-currency\" type=\"text\" value=\""+item.defaultvalue+"\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"form-control qp-input-currency\"";
					} else {
						elementTD += "class=\"form-control qp-input-currency\"";
					}
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" />"+element+"</span>";
	}
	if(dataType == 4){
		elementTD += "<span "+style+"><div class=\"input-group date qp-input-datepicker\">"
		elementTD += "<span><input style=\""+ item.inputStyle + ' '+item.style+"\" type=\"text\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control\"";
						}
		elementTD += " name=\"" 
							+ item.columnname + "1\" maxlength=\"" 
							+ item.maxlength + "\" />"+element+"</span>";
		elementTD += "<span class=\"input-group-addon\">"
							+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
							+ "</span>"
						+ "</div></span>";
	}
	if(dataType == 14){
		
		elementTD += "<span "+style+"><div class=\"input-group date qp-input-datetimepicker-detail\">"
		elementTD += "<span><input style=\""+ item.inputStyle + ' '+item.style+"\" type=\"text\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control \"";
						}
		elementTD += " name=\"" 
						+ item.columnname + "1\" maxlength=\"" 
						+ item.maxlength + "\" />"+element+"</span>";
		elementTD += "<span class=\"input-group-addon\">"
						+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
						+ "</span>"
					+ "</div></span>";
	}
	if(dataType == 9){
		
		elementTD += "<span "+style+"><div class=\"input-group date qp-input-timepicker\">"
			elementTD += "<span><input style=\""+ item.inputStyle + ' '+item.style+"\" type=\"text\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control\"";
						}
			elementTD += " name=\"" 
							+ item.columnname + "1\" maxlength=\"" 
							+ item.maxlength + "\" />"+element+"</span>";
			elementTD += "<span class=\"input-group-addon\">"
							+ "<span class=\"glyphicon glyphicon-time\"></span>"
							+ "</span>"
						+ "</div></span>";			
	}
	if(dataType == 5){
		if(item.msgvalue != undefined && item.msgvalue.length > 0){
			var msgLabelArr = item.msglabel.split("�");
			var msgValArr = item.msgvalue.split("�");
			elementTD += "<span "+style+">";
			$(msgValArr).each(function(j){
				elementTD += "<input  type=\"radio\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				}
				if(item.msglabel == undefined || item.msglabel.length == 0  || (msgLabelArr[j] == null || msgLabelArr[j] == 'null')){
					var checked = "";
					if (item.defaultvalue != undefined && item.defaultvalue == msgValArr[j]) {
						checked = 'checked="checked"';
					}
					elementTD += " name=\"" + item.columnname + "1\" "+checked+" /><label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">"+msgValArr[j]+"</label>";
				} else {					
					elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">"+msgLabelArr[j]+"</label>";
				}
			});
			elementTD += ""+element+"</span>";
		} else {
			elementTD += "<span "+style+"><input type=\"radio\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				}
			elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
			elementTD += "<input type=\"radio\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				}
			elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>"+element+"</span>";
		}
	}
	if(dataType == 6){
		if(item.msgvalue != undefined && item.msgvalue.length > 0){
			var msgLabelArr = item.msglabel.split("�");
			var msgValArr = item.msgvalue.split("�");
			elementTD += "<span "+style+">";
			$(msgValArr).each(function(j){
				elementTD += "<input  type=\"checkbox\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				} else {
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				}				
				if(item.msglabel == undefined || item.msglabel.length == 0  || (msgLabelArr[j] == null || msgLabelArr[j] == 'null')){		
					
					var checked = "";
					if (item.defaultvalue != undefined && item.defaultvalue == msgValArr[j]) {
						checked = 'checked="checked"';
					}
					elementTD += " name=\"" + item.columnname + "1\" "+checked+" /><label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">"+msgValArr[j]+"</label>";
				} else {					
					elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">"+msgLabelArr[j]+"</label>";
				}				
			});
			elementTD += ""+element+"</span>";
		} else {
			elementTD += "<span "+style+" >";
			if (item.physicaldatatype == undefined || item.physicaldatatype == '' || item.physicaldatatype != 8) {
				elementTD += "<input type=\"checkbox\"";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
					} else {
						elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
					}
				elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
			}
			elementTD += "<input type=\"checkbox\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				} else {
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				}
			elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>";
			elementTD += ""+element+"</span>";
		}
		
	}
	if(dataType == 7){
		elementTD += "<span "+style+"><select style=\""+ item.inputStyle + ' '+item.style+"\" name=\""+item.columnname+"\" ";
		if(item.elementtype == 0){
			elementTD += "class=\"form-control qp-input-select\"";
		} else {
			elementTD += "class=\"form-control qp-input-select\"";
		}
		elementTD += ">";
		if(item.msgvalue != undefined && item.msgvalue.length > 0){
			var msgLabelArr = item.msglabel.split("�");
			var msgValArr = item.msgvalue.split("�");
			
			$(msgValArr).each(function(j){
				if(item.msglabel == undefined || item.msglabel.length == 0  || (msgLabelArr[j] == null || msgLabelArr[j] == 'null')){
					var checked = "";
					if (item.defaultvalue != undefined && item.defaultvalue == msgValArr[j]) {
						checked = 'selected="selected"';
					}
					elementTD += "<option "+checked+">" + msgValArr[j] + "</option>";
				} else {
					elementTD += "<option>" + msgLabelArr[j] + "</option>";
				}
			});
		} else {
			elementTD += "<option>"+dbMsgSource['sc.screendesign.0137']+"1</option><option>"+dbMsgSource['sc.screendesign.0137']+"2</option>";
		}
		elementTD += "</select>"+element+"</span>";
	}
	if(dataType == 10){
		elementTD += "<span "+style+"><textarea style=\""+ item.inputStyle + ' '+item.style+"\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"form-control qp-input-textarea\"";
					} else {
						elementTD += "class=\"form-control qp-input-textarea\"";
					}
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" ></textarea>"+element+"</span>";
	}
	if(dataType == 11){		
		var elementButton =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""+item.label
			+ "\",\"datatype\":\""+item.datatype
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+item.columnname
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+item.labelText
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength
			+ "\",\"actiontype\":\""+item.actiontype
			+ "\",\"actionName\":\""+item.actionName
			+ "\",\"parameters\":\""+item.parameters
			+ "\",\"navigateTo\":\""+item.navigateTo
			+ "\",\"navigateToText\":\""+item.navigateToText
			+ "\",\"isSubmit\":\""+item.isSubmit
			;
		
		
			if(item.msgvalue != undefined){
				elementButton += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+item.msgvalue;
			} else {
				elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<span "+style+"><a style=\""+ item.inputStyle + ' '+item.style+"\" onmouseover=\"this.setAttribute('style', '"+item.hoverStyle+"')\" onmouseout=\"this.setAttribute('style', '"+item.style+"')\" href=\"javascript:\">"+item.labelText+"</a>"+elementButton+"</span>";
	}
	if(dataType == 12){
		elementTD += "<span "+style+"><input type=\"file\" name=\"" + item.columnname + "1\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-file\"";
					} else {
						elementTD += "class=\"qp-input-file\"";
					}
		elementTD += "/>"+element+"</span>";
	}
	if(dataType == 13){
		var elementButton =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""+item.label
			+ "\",\"datatype\":\""+item.datatype
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+item.columnname
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+item.labelText
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength
			+ "\",\"actiontype\":\""+item.actiontype
			+ "\",\"actionName\":\""+item.actionName
			+ "\",\"parameters\":\""+item.parameters
			+ "\",\"navigateTo\":\""+item.navigateTo
			+ "\",\"navigateToText\":\""+item.navigateToText
			+ "\",\"isSubmit\":\""+item.isSubmit
			;
		
			if(item.msgvalue != undefined){
				elementButton += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+item.msgvalue;
			} else {
				elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
			if (item.icon != undefined && item.showLabel != undefined && item.icon.length > 0) {
				var buttonContent = "<i class=\""+item.icon+"\"></i>";
				if (item.showLabel == '1' || item.showLabel == 1) {
					buttonContent += "&nbsp;&nbsp;" + item.labelText;
				}
				elementTD += "<span "+styleButton+"><button style=\""+ item.inputStyle + ' '+item.style+";width:100%\" type=\"button\" class=\"btn qp-button\" value=\""+item.labelText+"\">"+buttonContent+"</button>"+elementButton+"</span>";
			} else {
				elementTD += "<span "+styleButton+"><button style=\""+ item.inputStyle + ' '+item.style+";width:100%\" type=\"button\" class=\"btn qp-button\" value=\""+item.labelText+"\">"+item.labelText+"</button>"+elementButton+"</span>";
			}
	}
	if(dataType == 20){
		elementTD += "<label style=\"cursor: pointer;"+ item.inputStyle + ' '+item.style+"\">"+item.labelText+"</label>";
	}	
	return elementTD;
}

function returnElementTDListAddEntity(item){	
	var dataType = item.datatype;
	
	var elementTD = "<input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"enableGroup\"  /><input type=\"hidden\" name=\"groupTotalElement\"  />";
	var tablecolumncode = "";
	if(item.tablecolumncode != undefined){
		tablecolumncode = item.tablecolumncode;
	}
	var tablename = "";
	if(item.tablename != undefined){
		tablename = item.tablename;
	}
	var tablecode = "";
	if(item.tablecode != undefined){
		tablecode = item.tablecode;
	}
	var tablecolumnname = "";
	if(item.tablecolumnname != undefined){
		tablecolumnname = item.tablecolumnname;
	}
	
	var physicaldatatype = "";
	if(item.physicaldatatype != undefined){
		physicaldatatype = item.physicaldatatype;
	}
	var minvalue = "";
	if(item.minvalue != undefined){
		minvalue = item.minvalue;
	}
	var maxvalue = "";
	if(item.maxvalue != undefined){
		maxvalue = item.maxvalue;
	}
	var formatcode = "";
	if(item.formatcode != undefined){
		formatcode = item.formatcode;
	}
	var physicalmaxlength = "";
	if(item.physicalmaxlength != undefined){
		physicalmaxlength = item.physicalmaxlength;
	}
	var maxlength = "";
	if(item.maxlength != undefined){
		maxlength = item.maxlength;
	}
	var require = "";
	if(item.require != undefined){
		require = item.require;
	}
	var rowspan = "";
	if(item.rowspan != undefined){
		rowspan = item.rowspan;
	}
	var colspan = "";
	if(item.colspan != undefined){
		colspan = item.colspan;
	}
	var datasource = "";
	if(item.datasource != undefined){
		datasource = item.datasource;
	}
	var msglabel = "";
	if(item.msglabel != undefined){
		msglabel = item.msglabel;
	}
	
	var element =  "" 
		+ "<input type=\"hidden\" name=\"formElement\" "
		+ "value='\"label\":\""+item.label
		+ "\",\"datatype\":\""+item.datatype
		+ "\",\"physicaldatatype\":\""+physicaldatatype
		+ "\",\"columnname\":\""+item.columnname
		+ "\",\"rowspan\":\""+rowspan
		+ "\",\"colspan\":\""+colspan
		+ "\",\"datasource\":\""+datasource
		+ "\",\"minvalue\":\""+minvalue
		+ "\",\"maxvalue\":\""+maxvalue
		+ "\",\"formatcode\":\""+formatcode
		+ "\",\"tablename\":\""+tablename
		+ "\",\"tablecode\":\""+tablecode
		+ "\",\"tablecolumnname\":\""+tablecolumnname
		+ "\",\"tablecolumncode\":\""+tablecolumncode
		+ "\",\"connectionmsg\":\""
		+ "\",\"transitiontype\":\""
		+ "\",\"actiontype\":\""
		+ "\",\"parameters\":\""
		+ "\",\"screenactionid\":\""
		+ "\",\"linklabel\":\""
		+ "\",\"toscreenid\":\""
		+ "\",\"maxlength\":\""+maxlength
		+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
	
	
		if(item.msgvalue != undefined){
			element += "\",\"msglabel\":\""+msglabel
			+ "\",\"msgvalue\":\""+item.msgvalue;
		} else {
			element += "\",\"msglabel\":\"\",\"msgvalue\":\"";
		}
		
		element += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		
	if(dataType == 0){
		elementTD += "<span>" +
				"<div ondblclick=\"openDialogAutocompleteSetting(this)\" class=\"input-group\" style=\"width:100%\"><input type=\"text\" ";
				if(item.elementtype == 0){
					elementTD += " class=\"form-control ui-autocomplete-input\" ";
				} else {
					elementTD += " class=\"qp-input-autocomplete\" ";
				}
				elementTD += " name=\"" 
				+ item.columnname + "1\" maxlength=\"" 
				+ item.maxlength
				+		" optionvalue=\"optionValue\" optionlabel=\"optionLabel\" selectsqlid=\"\" emptylabel=\"\" onselectevent=\"\" onchangeevent=\"\" onremoveevent=\"\" mustmatch=\"true\" minlength=\"0\" arg01=\"\" arg02=\"\" arg03=\"\" arg4=\"\" arg05=\"\" arg06=\"\" arg07=\"\" arg08=\"\" arg09=\"\" arg10=\"\" arg11=\"\" arg12=\"\" arg13=\"\" arg14=\"\" arg15=\"\" arg16=\"\" arg17=\"\" arg18=\"\" arg19=\"\" arg20=\"\" placeholder=\"\" autocomplete=\"off\">"
				+ "	<input type=\"hidden\" value=\"\">" 
				+ "	<span class=\"input-group-addon dropdown-toggle\" data-dropdown=\"dropdown\">" 
				+ "		<span class=\"caret\"></span> " 
				+ "	</span>"
				+ "</div>"+element+"</span>"
				"";		
	}
	if(dataType == 1 || dataType == 15 || dataType == 16 || dataType == 18){
		elementTD += "<span style=\"width: 100%;\"><input ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"form-control qp-input-text\"";
					} else {
						elementTD += "class=\"form-control qp-input-text\"";
					}
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" />"+element+"</span>";
	}
	if(dataType == 21){
		elementTD += "<span style=\"width: 100%;\"><Label ondblclick=\"openDialogComponentSetting(this)\"";					
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" >"+((item.labelText != undefined && item.labelText != null && item.labelText.length > 0)?item.labelText:item.columnname)+"</label>"+element+"</span>";
	}
	if(dataType == 2){
		elementTD += "<span style=\"width: 100%;\"><input  ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"form-control\"";
					} else {
						elementTD += "class=\"form-control\"";
					}
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" />"+element+"</span>";
	}
	if(dataType == 3){
		elementTD += "<span style=\"width: 100%;\"><input  ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"form-control\"";
					} else {
						elementTD += "class=\"form-control\"";
					}
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" />"+element+"</span>";
	}
	if(dataType == 8){
		elementTD += "<span style=\"width: 100%;\"><input ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"form-control\"";
					} else {
						elementTD += "class=\"form-control\"";
					}
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" />"+element+"</span>";
	}
	if(dataType == 4){
		elementTD += "<span><div class=\"input-group date\">"
		elementTD += "<span><input ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control\"";
						}
		elementTD += " name=\"" 
							+ item.columnname + "1\" maxlength=\"" 
							+ item.maxlength + "\" />"+element+"</span>";
		elementTD += "<span class=\"input-group-addon\">"
							+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
							+ "</span>"
						+ "</div></span>";
	}
	if(dataType == 14){
		
		elementTD += "<span><div class=\"input-group date\">"
		elementTD += "<span><input ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control \"";
						}
		elementTD += " name=\"" 
						+ item.columnname + "1\" maxlength=\"" 
						+ item.maxlength + "\" />"+element+"</span>";
		elementTD += "<span class=\"input-group-addon\">"
						+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
						+ "</span>"
					+ "</div></span>";
	}
	if(dataType == 9){
		
		elementTD += "<span><div class=\"input-group date\">"
			elementTD += "<span><input ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control\"";
						}
			elementTD += " name=\"" 
							+ item.columnname + "1\" maxlength=\"" 
							+ item.maxlength + "\" />"+element+"</span>";
			elementTD += "<span class=\"input-group-addon\">"
							+ "<span class=\"glyphicon glyphicon-time\"></span>"
							+ "</span>"
						+ "</div></span>";			
	}
	if(dataType == 5){
		if(item.msgvalue != undefined && item.msgvalue.length > 0){
			var msgLabelArr = item.msglabel.split("�");
			var msgValArr = item.msgvalue.split("�");
			elementTD += "<span onclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\">";
			$(msgValArr).each(function(j){
				elementTD += "<input  type=\"radio\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				}
				if(item.msglabel == undefined || item.msglabel.length == 0  || (msgLabelArr[j] == null || msgLabelArr[j] == 'null')){					
					elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+msgValArr[j]+"</label>";
				} else {					
					elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+msgLabelArr[j]+"</label>";
				}
			});
			elementTD += ""+element+"</span>";
		} else {
			elementTD += "<span onclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\"><input type=\"radio\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				}
			elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
			elementTD += "<input type=\"radio\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				}
			elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>"+element+"</span>";
		}
	}
	if(dataType == 6){
		if(item.msgvalue != undefined && item.msgvalue.length > 0){
			var msgLabelArr = item.msglabel.split("�");
			var msgValArr = item.msgvalue.split("�");
			elementTD += "<span onclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\">";
			$(msgValArr).each(function(j){
				elementTD += "<input type=\"checkbox\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				} else {
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				}				
				if(item.msglabel == undefined || item.msglabel.length == 0  || (msgLabelArr[j] == null || msgLabelArr[j] == 'null')){					
					elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+msgValArr[j]+"</label>";
				} else {					
					elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+msgLabelArr[j]+"</label>";
				}				
			});
			elementTD += ""+element+"</span>";
		} else {
			if (item.physicaldatatype == undefined || item.physicaldatatype == '' || item.physicaldatatype != 8) {
				elementTD += "<span onclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\"><input type=\"checkbox\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
					} else {
						elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
					}
				elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label></span>";
			}
			elementTD += "<span><input type=\"checkbox\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				} else {
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				}
			elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>"+element+"</span>";
		}
	}
	if(dataType == 7){
		elementTD += "<span><select ondblclick=\"openDialogComponentListSetting(this)\" name=\""+item.columnname+"\" ";
		if(item.elementtype == 0){
			elementTD += "class=\"form-control qp-input-select\"";
		} else {
			elementTD += "class=\"form-control qp-input-select\"";
		}
		elementTD += ">";
		if(item.msgvalue != undefined && item.msgvalue.length > 0){
			var msgLabelArr = item.msglabel.split("�");
			var msgValArr = item.msgvalue.split("�");
			
			$(msgValArr).each(function(j){
				if(item.msglabel == undefined || item.msglabel.length == 0  || (msgLabelArr[j] == null || msgLabelArr[j] == 'null')){
					elementTD += "<option>" + msgValArr[j] + "</option>";
				} else {
					elementTD += "<option>" + msgLabelArr[j] + "</option>";
				}
			});
		} else {
			elementTD += "<option>"+dbMsgSource['sc.screendesign.0137']+"1</option><option>"+dbMsgSource['sc.screendesign.0137']+"2</option>";
		}
		elementTD += "</select>"+element+"</span>";
	}
	if(dataType == 10){
		elementTD += "<span><textarea ondblclick=\"openDialogComponentSetting(this)\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"form-control qp-input-textarea\"";
					} else {
						elementTD += "class=\"form-control qp-input-textarea\"";
					}
		elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" ></textarea>"+element+"</span>";
	}
	if(dataType == 11){		
		var elementButton =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""+item.label
			+ "\",\"datatype\":\""+item.datatype
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+item.columnname
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+item.labelText
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
			if(item.msgvalue != undefined){
				elementButton += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+item.msgvalue;
			} else {
				elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		
		elementTD += "<span><a ondblclick=\"openDialogLinkAreaSetting(this);\" href=\"javascript:\">"+item.labelText+"</a>"+elementButton+"</span>";
	}
	if(dataType == 12){
		elementTD += "<span><input onclick=\"event.stopPropagation();return false;\" ondblclick=\"event.stopPropagation();openDialogComponentSetting(this);return false;\" type=\"file\" name=\"" + item.columnname + "1\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-file\"";
					} else {
						elementTD += "class=\"qp-input-file\"";
					}
		elementTD += "/>"+element+"</span>";
	}
	if(dataType == 13){
		var elementButton =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""+item.label
			+ "\",\"datatype\":\""+item.datatype
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+item.columnname
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+item.labelText
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
			if(item.msgvalue != undefined){
				elementButton += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+item.msgvalue;
			} else {
				elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<span><button style=\""+item.style+"\" type=\"button\" class=\"btn qp-button\" ondblclick=\"openDialogButtonAreaSetting(this)\" value=\""+item.labelText+"\">"+item.labelText+"</button>"+elementButton+"</span>";
	}
	if(dataType == 20){
		if (item.labelText == undefined || item.labelText == null) {
			item["labelText"] = dbMsgSource['sc.screendesign.0050'];
		}
		var elementLabel =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""+item.label
			+ "\",\"datatype\":\""+item.datatype
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+item.columnname
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+item.labelText
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength
			+ "\",\"actiontype\":\""+item.actiontype
			+ "\",\"actionName\":\""+item.actionName
			+ "\",\"parameters\":\""+item.parameters
			+ "\",\"navigateTo\":\""+item.navigateTo
			+ "\",\"navigateToText\":\""+item.navigateToText
			+ "\",\"style\":\""+item.style
			+ "\",\"hoverStyle\":\""+item.hoverStyle
			+ "\",\"icon\":\""+item.icon
			+ "\",\"showLabel\":\""+item.showLabel
			+ "\",\"isSubmit\":\""+item.isSubmit
			+ "\",\"itemType\":\"5"
			;
		
		
			if(item.msgvalue != undefined){
				elementLabel += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+item.msgvalue;
			} else {
				elementLabel += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementLabel += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
elementTD += "<span><label style=\"cursor: pointer;"+item.style+"\" ondblclick=\"openDialogLableSetting(this, true)\">"+unescape(item.labelText)+"</label>"+elementLabel+"</span>";
	}	
	return elementTD;
}

function returnElemenActionSection(item, styleArea){	
	var dataType = item.datatype;
	// daipv split style for Div and input tag
	var itemStyleDiv = "";
	var itemStyleInput = "";
	var arrStyle = item.style.split(";");
	for (var i = 0; i < arrStyle.length; i++) {
		if(arrStyle[i].indexOf("margin-") > -1){
			itemStyleDiv += arrStyle[i] + ";";
		} else {
			itemStyleInput += arrStyle[i] + ";";
		}
	}
	itemStyleDiv = itemStyleDiv.slice(0, -1);
	itemStyleInput = itemStyleInput.slice(0, -1);
	
	var elementTD = "";
	var tablecolumncode = "";
	if(item.tablecolumncode != undefined){
		tablecolumncode = item.tablecolumncode;
	}
	var tablename = "";
	if(item.tablename != undefined){
		tablename = item.tablename;
	}
	var tablecode = "";
	if(item.tablecode != undefined){
		tablecode = item.tablecode;
	}
	var tablecolumnname = "";
	if(item.tablecolumnname != undefined){
		tablecolumnname = item.tablecolumnname;
	}
	
	var physicaldatatype = "";
	if(item.physicaldatatype != undefined){
		physicaldatatype = item.physicaldatatype;
	}
	var minvalue = "";
	if(item.minvalue != undefined){
		minvalue = item.minvalue;
	}
	var maxvalue = "";
	if(item.maxvalue != undefined){
		maxvalue = item.maxvalue;
	}
	var formatcode = "";
	if(item.formatcode != undefined){
		formatcode = item.formatcode;
	}
	var physicalmaxlength = "";
	if(item.physicalmaxlength != undefined){
		physicalmaxlength = item.physicalmaxlength;
	}
	var maxlength = "";
	if(item.maxlength != undefined){
		maxlength = item.maxlength;
	}
	var require = "";
	if(item.require != undefined){
		require = item.require;
	}
	var rowspan = "";
	if(item.rowspan != undefined){
		rowspan = item.rowspan;
	}
	var colspan = "";
	if(item.colspan != undefined){
		colspan = item.colspan;
	}
	var datasource = "";
	if(item.datasource != undefined){
		datasource = item.datasource;
	}
	var msglabel = "";
	if(item.msglabel != undefined){
		msglabel = item.msglabel;
	}
	
	var labelText = "";
	if(item.labelText != undefined){
		labelText = item.labelText;
	}
	
	var msgvalue = "";
	if(item.msgvalue != undefined){
		msgvalue = item.msgvalue;
	}
	
	var localCodelist = "";
	if(item.localCodelist != undefined){
		localCodelist = item.localCodelist;
	}
	
	var datasourcetype = "";
	var parameters = "";
	if (item.parameters != undefined && item.parameters != null) {
		parameters = item.parameters;
	}
	var events = "[]";
	
	if (item.events != undefined && item.events != null && item.events != 'null') {
		events = convertToString(item.events);
	}
	
	if (events == null || events == 'null') {
		events = "[]";
	}
	
	var element =  "" 
		+ "<input type=\"hidden\" name=\"formElement\" "
		+ "value='\"label\":\""+item.label
		+ "\",\"labelText\":\""+labelText
		+ "\",\"datatype\":\""+item.datatype
		+ "\",\"physicaldatatype\":\""+physicaldatatype
		+ "\",\"columnname\":\""+item.columnname
		+ "\",\"rowspan\":\""+rowspan
		+ "\",\"colspan\":\""+colspan
		+ "\",\"datasource\":\""+datasource
		+ "\",\"minvalue\":\""+minvalue
		+ "\",\"maxvalue\":\""+maxvalue
		+ "\",\"formatcode\":\""+formatcode
		+ "\",\"tablename\":\""+tablename
		+ "\",\"tablecode\":\""+tablecode
		+ "\",\"tablecolumnname\":\""+tablecolumnname
		+ "\",\"tablecolumncode\":\""+tablecolumncode
		+ "\",\"connectionmsg\":\""
		+ "\",\"transitiontype\":\""
		+ "\",\"actiontype\":\""
		+ "\",\"parameters\":\"" + parameters
		+ "\",\"screenactionid\":\""
		+ "\",\"linklabel\":\""
		+ "\",\"toscreenid\":\""
		+ "\",\"baseType\":\""+item.baseType
		+ "\",\"codelistCode\":\""+item.codelistCode
		+ "\",\"codelistText\":\""+item.codelistText
		+ "\",\"isEnable\":\""+item.isEnable
		+ "\",\"localCodelist\":\""+localCodelist
		+ "\",\"isSupportOptionValue\":\""+item.isSupportOptionValue
		+ "\",\"maxlength\":\""+maxlength
		+ "\",\"dialogAutocompleteCode\":\""+item.dialogAutocompleteCode
		+ "\",\"dialogAutocompleteText\":\""+item.dialogAutocompleteText
		+ "\",\"dialogOnChangeEvent\":\""+item.dialogOnChangeEvent
		+ "\",\"width\":\""+item.width
		+ "\",\"widthunit\":\""+item.widthunit
		+ "\",\"baseType\":\""+item.baseType
		+ "\",\"dialogOnSelectEvent\":\""+item.dialogOnSelectEvent
		+ "\",\"mandatory\":\""+item.mandatory
		+ "\",\"outputBeanId\":\""+item.outputBeanId
		+ "\",\"tabindex\":\""+item.tabindex
		+ "\",\"events\":"+events
		+ ",\"physicalmaxlength\":\""+physicalmaxlength
		+ "\",\"datasourcetype\":\""+item.datasourcetype
		+ "\",\"sqldesignid\":\""+item.sqldesignid
		+ "\",\"sqldesignidtext\":\""+item.sqldesignidtext
		+ "\",\"optionlabel\":\""+item.optionlabel
		+ "\",\"optionlabeltext\":\""+item.optionlabeltext
		+ "\",\"optionvalue\":\""+item.optionvalue
		+ "\",\"optionvaluetext\":\""+item.optionvaluetext
		+ "\",\"screenItemId\":\""+item.screenItemId
		+ "\",\"style\":\""+item.style
		+ "\",\"hoverStyle\":\""+item.hoverStyle
		+ "\",\"icon\":\""+item.icon
		+ "\",\"showLabel\":\""+item.showLabel	
		+ "\",\"enablePassword\":\""+item.enablePassword
		+ "\",\"allowAnyInput\":\""+item.allowAnyInput
		+ "\",\"showBlankItem\":\""+item.showBlankItem
		+ "\",\"defaultvalue\":\""+item.defaultvalue;		
	
		if(item.msgvalue != undefined){
			element += "\",\"msglabel\":\""+msglabel
			+ "\",\"msgvalue\":\""+msgvalue;
		} else {
			element += "\",\"msglabel\":\"\",\"msgvalue\":\"";
		}
		
		element += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		
		var styleWidth = "";
		
		if (item.width != null && item.width != "" && item.width.length > 0) {
			styleWidth = "width:" + item.width + item.widthunit + ";";
		}
		var style = "";
		if (styleArea != undefined && styleArea != null && styleArea.length > 0) {
			style = "style='"+styleArea+" "+styleWidth+"'";
		} /*else {
			var style = "style='width: "+styleWidth+"'";
		}*/
		
	if(dataType == 0){
		elementTD += "<span "+style+" >" +
				"<div ondblclick=\"openDialogAutocompleteSettingSection(this)\" class=\"input-group\" style=\"width:100%;"+ item.inputStyle + ' '+itemStyleDiv+"\"><input style=\""+itemStyleInput+"\" type=\"text\" ";
				if(item.elementtype == 0){
					elementTD += " class=\"form-control ui-autocomplete-input\" ";
				} else {
					elementTD += " class=\"qp-input-autocomplete\" ";
				}
				elementTD += " name=\"" 
				+ item.columnname + "1\" maxlength=\"" 
				+ item.maxlength
				+		" optionvalue=\"optionValue\" optionlabel=\"optionLabel\" selectsqlid=\"\" emptylabel=\"\" onselectevent=\"\" onchangeevent=\"\" onremoveevent=\"\" mustmatch=\"true\" minlength=\"0\" arg01=\"\" arg02=\"\" arg03=\"\" arg4=\"\" arg05=\"\" arg06=\"\" arg07=\"\" arg08=\"\" arg09=\"\" arg10=\"\" arg11=\"\" arg12=\"\" arg13=\"\" arg14=\"\" arg15=\"\" arg16=\"\" arg17=\"\" arg18=\"\" arg19=\"\" arg20=\"\" placeholder=\"\" autocomplete=\"off\">"
				+ "	<input type=\"hidden\" value=\"\">" 
				+ "	<span class=\"input-group-addon dropdown-toggle\" data-dropdown=\"dropdown\">" 
				+ "		<span class=\"caret\"></span> " 
				+ "	</span>"
				+ "</div>"+element+"</span>"
				"";		
	}
	if(dataType == 1 || dataType == 15 || dataType == 16 || dataType == 18){
		elementTD += "<span  "+style+" ><input style=\""+item.style+"\" ondblclick=\"openDialogComponentSettingSection(this)\" type=\"text\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"form-control qp-input-text\"";
					} else {
						elementTD += "class=\"form-control qp-input-text\"";
					}
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" />"+element+"</span>";
	}
	if(dataType == 21){
		elementTD += "<span  "+style+" ><Label style=\""+item.style+"\" ondblclick=\"openDialogComponentSettingSection(this)\"";					
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" >"+((item.labelText != undefined && item.labelText != null && item.labelText.length > 0)?item.labelText:item.columnname)+"</label>"+element+"</span>";
	}
	if(dataType == 2){
		elementTD += "<span  "+style+" ><input style=\""+item.style+" qp-input-integer\" ondblclick=\"openDialogComponentSettingSection(this)\" type=\"text\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"form-control qp-input-integer\"";
					} else {
						elementTD += "class=\"form-control qp-input-integer\"";
					}
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" />"+element+"</span>";
	}
	if(dataType == 3){
		elementTD += "<span  "+style+" ><input style=\""+item.style+"\" ondblclick=\"openDialogComponentSettingSection(this)\" type=\"text\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"form-control\"";
					} else {
						elementTD += "class=\"form-control\"";
					}
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" />"+element+"</span>";
	}
	if(dataType == 8){
		elementTD += "<span  "+style+" ><input style=\""+item.style+" qp-input-currency\" ondblclick=\"openDialogComponentSettingSection(this)\" type=\"text\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"form-control qp-input-currency\"";
					} else {
						elementTD += "class=\"form-control qp-input-currency\"";
					}
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" />"+element+"</span>";
	}
	if(dataType == 4){
		elementTD += "<span  "+style+"><div class=\"input-group date\" style=\"" +itemStyleDiv+ "\">"
		elementTD += "<span><input style=\""+itemStyleInput+"\" ondblclick=\"openDialogComponentSettingSection(this)\" type=\"text\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control\"";
						}
		elementTD += " name=\"" 
							+ item.columnname + "1\" maxlength=\"" 
							+ item.maxlength + "\" />"+element+"</span>";
		elementTD += "<span class=\"input-group-addon\">"
							+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
							+ "</span>"
						+ "</div></span>";
	}
	if(dataType == 14){
		elementTD += "<span  "+style+"><div class=\"input-group date\" style=\"" +itemStyleDiv+ "\">"
		elementTD += "<span><input style=\""+itemStyleInput+"\" ondblclick=\"openDialogComponentSettingSection(this)\" type=\"text\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control \"";
						}
		elementTD += " name=\"" 
						+ item.columnname + "1\" maxlength=\"" 
						+ item.maxlength + "\" />"+element+"</span>";
		elementTD += "<span class=\"input-group-addon\">"
						+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
						+ "</span>"
					+ "</div></span>";
	}
	if(dataType == 9){
		elementTD += "<span  "+style+"><div class=\"input-group date\" style=\"" +itemStyleDiv+ "\">"
			elementTD += "<span><input style=\""+itemStyleInput+"\" ondblclick=\"openDialogComponentSettingSection(this)\" type=\"text\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control\"";
						}
			elementTD += " name=\"" 
							+ item.columnname + "1\" maxlength=\"" 
							+ item.maxlength + "\" />"+element+"</span>";
			elementTD += "<span class=\"input-group-addon\">"
							+ "<span class=\"glyphicon glyphicon-time\"></span>"
							+ "</span>"
						+ "</div></span>";			
	}
	if(dataType == 5){
		if(item.msgvalue != undefined && item.msgvalue.length > 0){
			var msgLabelArr = item.msglabel.split("�");
			var msgValArr = item.msgvalue.split("�");
			elementTD += "<span "+style+" ondblclick=\"this.checked = !this.checked;openDialogComponentListSettingSection(this)\">";
			$(msgValArr).each(function(j){
				elementTD += "<input  type=\"radio\" value=\""+msgValArr[j]+"\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				}
				if(item.msglabel == undefined || item.msglabel.length == 0  || (msgLabelArr[j] == null || msgLabelArr[j] == 'null')){					
					elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+item.style+"\" for=\""+item.columnname+"\">"+deEscapeHTMLForView(msgValArr[j])+"</label>";
				} else {					
					elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+item.style+"\" for=\""+item.columnname+"\">"+deEscapeHTMLForView(msgLabelArr[j])+"</label>";
				}
			});
			elementTD += ""+element+"</span>";
		} else {
			elementTD += "<span "+style+" ondblclick=\"this.checked = !this.checked;openDialogComponentListSettingSection(this)\"><input type=\"radio\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				}
			elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+item.style+"\" for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
			elementTD += "<input type=\"radio\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				}
			elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+item.style+"\" for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>"+element+"</span>";
		}
	}
	if(dataType == 6){
		if(item.msgvalue != undefined && item.msgvalue.length > 0){
			var msgLabelArr = item.msglabel.split("�");
			var msgValArr = item.msgvalue.split("�");
			elementTD += "<span "+style+" ondblclick=\"this.checked = !this.checked;openDialogComponentListSettingSection(this)\">";
			$(msgValArr).each(function(j){
				elementTD += "<input type=\"checkbox\" value=\""+msgValArr[j]+"\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				} else {
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				}				
				if(item.msglabel == undefined || item.msglabel.length == 0  || (msgLabelArr[j] == null || msgLabelArr[j] == 'null')){					
					elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+item.style+"\" for=\""+item.columnname+"\">"+deEscapeHTMLForView(msgValArr[j])+"</label>";
				} else {					
					elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+item.style+"\" for=\""+item.columnname+"\">"+deEscapeHTMLForView(msgLabelArr[j])+"</label>";
				}				
			});
			elementTD += ""+element+"</span>";
		} else {
			elementTD += "<span "+style+" ondblclick=\"this.checked = !this.checked;openDialogComponentListSettingSection(this)\"> ";
			if (item.physicaldatatype == undefined || item.physicaldatatype == '' || item.physicaldatatype != 8) {
				elementTD += "<input type=\"checkbox\"";
				if(item.elementtype == 0){
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				} else {
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				}
				elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+item.style+"\" for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
			}
			elementTD += "<input type=\"checkbox\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				} else {
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				}
			/*elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+item.style+"\" for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>"+element+"</span>";*/
				elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+item.style+"\" for=\""+item.columnname+"\"></label>"+element+"</span>";
		}
	}
	if(dataType == 7){
		elementTD += "<span  "+style+" ><select style=\""+item.style+"\" ondblclick=\"openDialogComponentListSettingSection(this)\" name=\""+item.columnname+"\" ";
		if(item.elementtype == 0){
			elementTD += "class=\"form-control qp-input-select\"";
		} else {
			elementTD += "class=\"form-control qp-input-select\"";
		}
		elementTD += ">";
		if (item.showBlankItem == "1") {
			elementTD += "<option>" + "&nbsp;" + "</option>";
		}
		if(item.msgvalue != undefined && item.msgvalue.length > 0){
			var msgLabelArr = item.msglabel.split("�");
			var msgValArr = item.msgvalue.split("�");
			$(msgValArr).each(function(j){
				if(item.msglabel == undefined || item.msglabel.length == 0  || (msgLabelArr[j] == null || msgLabelArr[j] == 'null')){
					elementTD += "<option value=\""+msgValArr[j]+"\">" + deEscapeHTMLForView(msgValArr[j]) + "</option>";
				} else {
					elementTD += "<option value=\""+msgValArr[j]+"\">" + deEscapeHTMLForView(msgLabelArr[j]) + "</option>";
				}
			});
		} else {
			elementTD += "<option>"+dbMsgSource['sc.screendesign.0137']+"1</option><option>"+dbMsgSource['sc.screendesign.0137']+"2</option>";
		}
		elementTD += "</select>"+element+"</span>";
	}
	if(dataType == 10){
		elementTD += "<span  "+style+" ><textarea style=\""+item.style+"\" ondblclick=\"openDialogComponentSettingSection(this)\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"form-control qp-input-textarea\"";
					} else {
						elementTD += "class=\"form-control qp-input-textarea\"";
					}
		elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" ></textarea>"+element+"</span>";
	}
	if(dataType == 11){		
		var elementButton =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""+item.label
			+ "\",\"datatype\":\""+item.datatype
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+item.columnname
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+item.labelText
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength
			+ "\",\"actiontype\":\""+item.actiontype
			+ "\",\"actionName\":\""+item.actionName
			+ "\",\"parameters\":\""+item.parameters
			+ "\",\"navigateTo\":\""+item.navigateTo
			+ "\",\"navigateToText\":\""+item.navigateToText
			+ "\",\"style\":\""+item.style
			+ "\",\"hoverStyle\":\""+item.hoverStyle
			+ "\",\"tabindex\":\""+item.tabindex
			+ "\",\"messageConfirm\":\""+item.messageConfirm
			+ "\",\"messageConfirmText\":\""+item.messageConfirmText
			+ "\",\"enableConfirm\":\""+item.enableConfirm
			+ "\",\"screenTransition\":\""+item.screenTransition
			+ "\",\"screenTransitionText\":\""+item.screenTransitionText
			+ "\",\"isSubmit\":\""+item.isSubmit
			;
		
			if(item.msgvalue != undefined){
				elementButton += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+item.msgvalue;
			} else {
				elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<span  "+style+" ><a style=\""+item.style+"\" onmouseover=\"this.setAttribute('style', '"+item.hoverStyle+"')\" onmouseout=\"this.setAttribute('style', '"+item.style+"')\" ondblclick=\"openDialogLinkAreaSettingSection(this);\" href=\"javascript:\">"+item.labelText+"</a>"+elementButton+"</span>";
	}
	var styleLink = "";
	if (styleArea != undefined && styleArea != null && styleArea.length > 0) {
		styleLink = "style=\""+styleArea+" width:"+item.width+""+item.widthunit+"\"";
	}
	if(dataType == 22){		
		var elementButton =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""+item.label
			+ "\",\"datatype\":\""+item.datatype
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+item.columnname
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+item.labelText
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength
			+ "\",\"actiontype\":\""+item.actiontype
			+ "\",\"actionName\":\""+item.actionName
			+ "\",\"parameters\":\""+item.parameters
			+ "\",\"navigateTo\":\""+item.navigateTo
			+ "\",\"navigateToText\":\""+item.navigateToText
			+ "\",\"style\":\""+item.style
			+ "\",\"hoverStyle\":\""+item.hoverStyle
			+ "\",\"messageConfirm\":\""+item.messageConfirm
			+ "\",\"messageConfirmText\":\""+item.messageConfirmText
			+ "\",\"enableConfirm\":\""+item.enableConfirm
			+ "\",\"messageConfirmCode\":\""+item.messageConfirmCode
			+ "\",\"screenItemId\":\""+item.screenItemId
			+ "\",\"width\":\""+item.width
			+ "\",\"widthunit\":\""+item.widthunit
			+ "\",\"tabindex\":\""+item.tabindex
			+ "\",\"screenTransition\":\""+item.screenTransition
			+ "\",\"screenTransitionText\":\""+item.screenTransitionText
			+ "\",\"isSubmit\":\""+item.isSubmit
			;
		
			if(item.msgvalue != undefined){
				elementButton += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+item.msgvalue;
			} else {
				elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<span "+styleLink+"><a style=\""+ item.inputStyle + ' ' +item.style+"\" onmouseover=\"this.setAttribute('style', '"+item.hoverStyle+"')\" onmouseout=\"this.setAttribute('style', '"+item.style+"')\" ondblclick=\"openDialogLinkAreaSettingSection(this);\" href=\"javascript:\">"+item.labelText+"</a>"+elementButton+"</span>";
	}
	
	if(dataType == 23){
		var elementLabel =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""
			+ "\",\"datatype\":\""+item.datatype
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+item.columnname
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+item.labelText
			+ "\",\"isBundle\":\""+ "false"
			+ "\",\"isSubmit\":\""+ "false"
			+ "\",\"navigateTo\":\""
			+ "\",\"navigateToText\":\""
			+ "\",\"customItemContent\":\"" + item.customItemContent
			+ "\",\"itemType\":\"5"
			+ "\",\"screenItemId\":\""+item.screenItemId
			+ "\",\"messageConfirm\":\""+item.messageConfirm
			+ "\",\"messageConfirmText\":\""+item.messageConfirmText
			+ "\",\"enableConfirm\":\""+item.enableConfirm
			+ "\",\"screenTransition\":\""+item.screenTransition
			+ "\",\"screenTransitionText\":\""+item.screenTransitionText
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
		if(item.msgvalue != undefined){
			elementLabel += "\",\"msglabel\":\""+msglabel
			+ "\",\"msgvalue\":\""+item.msgvalue;
		} else {
			elementLabel += "\",\"msglabel\":\"\",\"msgvalue\":\"";
		}
		
		elementLabel += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<span><label class=\"qp-item-custom\" ondblclick=\"openDialogCustomSetting(this, true)\" style=\"cursor: pointer;\">"+item.labelText+"</label>"+elementLabel+"</span>";
	}	
	
	if(dataType == 12){
		elementTD += "<span  "+style+" ><input onclick=\"event.stopPropagation();return false;\" ondblclick=\"event.stopPropagation();openDialogComponentSettingSection(this);return false;\" type=\"file\" name=\"" + item.columnname + "1\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-file\"";
					} else {
						elementTD += "class=\"qp-input-file\"";
					}
		elementTD += "/>"+element+"</span>";
	}
	/*var width = ""
	if(item.width != undefined) {
		width = item.width;
	} else {
		width = "100";
	}*/
	var styleButton = "";
	if (styleArea != undefined && styleArea != null && styleArea.length > 0) {
		styleButton = "style=\""+styleArea+" width:"+item.width+""+item.widthunit+"\"";
	}
	if(dataType == 13){
		var elementButton =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""+item.label
			+ "\",\"datatype\":\""+item.datatype
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+item.columnname
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+item.labelText
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength
			+ "\",\"actiontype\":\""+item.actiontype
			+ "\",\"actionName\":\""+item.actionName
			+ "\",\"parameters\":\""+item.parameters
			+ "\",\"navigateTo\":\""+item.navigateTo
			+ "\",\"navigateToText\":\""+item.navigateToText
			+ "\",\"style\":\""+item.style
			+ "\",\"hoverStyle\":\""+item.hoverStyle
			+ "\",\"icon\":\""+item.icon
			+ "\",\"showLabel\":\""+item.showLabel
			+ "\",\"buttonStyle\":\""+item.buttonStyle
			+ "\",\"width\":\""+item.width
			+ "\",\"widthunit\":\""+item.widthunit
			+ "\",\"messageConfirm\":\""+item.messageConfirm
			+ "\",\"messageConfirmText\":\""+item.messageConfirmText
			+ "\",\"enableConfirm\":\""+item.enableConfirm
			+ "\",\"screenTransition\":\""+item.screenTransition
			+ "\",\"tabindex\":\""+item.tabindex
			+ "\",\"screenTransitionText\":\""+item.screenTransitionText
			+ "\",\"isSubmit\":\""+item.isSubmit
			;
		
			if(item.msgvalue != undefined){
				elementButton += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+item.msgvalue;
			} else {
				elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
			
			var buttonStyle = "";
			if (item.buttonStyle != undefined) {
				if (item.buttonStyle == 0 || item.buttonStyle == 1) {
					buttonStyle = "qp-button-type-design";
				} else if (item.buttonStyle == 2) {
					buttonStyle = "qp-button-type-warning-design";
				} else if (item.buttonStyle == 3) {
					buttonStyle = "qp-button-type-client-design";
				}
			}
			
			if (item.icon != undefined && item.showLabel != undefined && item.icon.length > 0) {
				var buttonContent = "<i class=\""+item.icon+"\"></i>";
				if (item.showLabel == '1' || item.showLabel == 1) {
					buttonContent += "&nbsp;&nbsp;" + item.labelText;
				}
				elementTD += "<span "+styleButton+"><button style=\""+item.style+";width:100%\" type=\"button\" class=\"btn qp-button "+buttonStyle+"\" ondblclick=\"openDialogButtonAreaSettingSection(this)\" value=\""+item.labelText+"\">"+buttonContent+"</button>"+elementButton+"</span>";
			} else {
				elementTD += "<span "+styleButton+"><button style=\""+item.style+";width:100%\" type=\"button\" class=\"btn qp-button "+buttonStyle+"\" ondblclick=\"openDialogButtonAreaSettingSection(this)\" value=\""+item.labelText+"\">"+item.labelText+"</button>"+elementButton+"</span>";
			}
	}
	if(dataType == 20){
		var widthLabel = '';
		if (item.width != null && item.width != "") {
			widthLabel = "width:" + item.width + item.widthunit + ";";
		}
		var arrStyle = styleArea.split(";");
		var float = '';
		for (var i = 0; i < arrStyle.length; i++) {
			if(arrStyle[i].indexOf("float") > -1){
				float += arrStyle[i] + ";";
				break;
			}
		}
		elementTD += "<span  style=\""+styleArea+ " " +widthLabel+ "\" ><label style=\"" +float+ " " +item.style+ "\" ondblclick=\"openDialogLableSettingSection(this)\" style=\"cursor: pointer;float: right;\">"+item.labelText;
		var required="";
		if(item.mandatory == "true"){
			required += "<label class=\"qp-required-field\">(*)</label>";
		}
		elementTD +=required+"</label>"+element+"</span>";
	}	
	return elementTD;
}

function returnElement(div){	
	var dataType = $(div).attr('datatype');
	
	var elementTD = "";
	var tablecolumncode = "";
	if($(div).attr('tablecolumncode') != undefined){
		tablecolumncode = $(div).attr('tablecolumncode');
	}
	var tablename = "";
	if($(div).attr('tablename') != undefined){
		tablename = $(div).attr('tablename');
	}
	var tablecode = "";
	if($(div).attr('tablecode') != undefined){
		tablecode = $(div).attr('tablecode');
	}
	var tablecolumnname = "";
	if($(div).attr('tablecolumnname') != undefined){
		tablecolumnname = $(div).attr('tablecolumnname');
	}
	
	var physicaldatatype = "";
	if($(div).attr('physicaldatatype') != undefined){
		physicaldatatype = $(div).attr('physicaldatatype');
	}
	var minvalue = "";
	if($(div).attr('minvalue') != undefined){
		minvalue = $(div).attr('minvalue');
	}
	var maxvalue = "";
	if($(div).attr('maxvalue') != undefined){
		maxvalue = $(div).attr('maxvalue');
	}
	var formatcode = "";
	if($(div).attr('formatcode') != undefined){
		formatcode = $(div).attr('formatcode');
	}
	var physicalmaxlength = "";
	if($(div).attr('physicalmaxlength') != undefined){
		physicalmaxlength = $(div).attr('physicalmaxlength');
	}
	var maxlength = "";
	if($(div).attr('maxlength') != undefined){
		maxlength = $(div).attr('maxlength');
	}
	var require = "";
	if($(div).attr('require') != undefined){
		require = $(div).attr('require');
	}
	var rowspan = "";
	if($(div).attr('rowspan') != undefined){
		rowspan = $(div).attr('rowspan');
	}
	var colspan = "";
	if($(div).attr('colspan') != undefined){
		colspan = $(div).attr('colspan');
	}
	var datasource = "";
	if($(div).attr('datasource') != undefined){
		datasource = $(div).attr('datasource');
	}
	var msglabel = "";
	if($(div).attr('msglabel') != undefined){
		msglabel = $(div).attr('msglabel');
	}
	var msgvalue = "";
	if($(div).attr('msgvalue') != undefined) {
		msgvalue = $(div).attr('msglabel');
	}
	var labelText = "";
	if($(div).attr('labelText') != undefined){
		labelText = $(div).attr('labelText');
	}
	var localCodelist = "";
	if($(div).attr('localCodelist') != undefined){
		localCodelist = $(div).attr('localCodelist');
	}
	var tablewidth = "";
	if($(div).attr('width') != undefined){
		tablewidth = $(div).attr('width');
	}
	var datasourcetype = "";
	var parameters = "";
	if ($(div).attr('parameters') == undefined || $(div).attr('parameters') == null || $(div).attr('parameters').length == 0) {
		if (dataType == 5 || dataType == 6 || dataType == 7) {
			parameters = "option1π1�option2π2�";
			$(div).attr('msgvalue',"1�2");
			msglabel = "option1�option2";
			localCodelist = 3;
			datasourcetype = 2;
		} else {
			datasourcetype = checkUndefined($(div).attr('datasourcetype'));
		}
	} else {
		parameters = $(div).attr('parameters');
	}
	
	
	var element =  "" 
		+ "<input type=\"hidden\" name=\"formElement\" "
		+ "value='\"label\":\""+''
		+ "\",\"datatype\":\""+$(div).attr('datatype')
		+ "\",\"physicaldatatype\":\""+physicaldatatype
		+ "\",\"columnname\":\""+$(div).attr('columnname')
		+ "\",\"rowspan\":\""+rowspan
		+ "\",\"colspan\":\""+colspan
		+ "\",\"datasource\":\""+datasource
		+ "\",\"minvalue\":\""+minvalue
		+ "\",\"maxvalue\":\""+maxvalue
		+ "\",\"formatcode\":\""+formatcode
		+ "\",\"tablename\":\""+tablename
		+ "\",\"tablecode\":\""+tablecode
		+ "\",\"tablecolumnname\":\""+tablecolumnname
		+ "\",\"tablecolumncode\":\""+tablecolumncode
		+ "\",\"connectionmsg\":\""
		+ "\",\"transitiontype\":\""
		+ "\",\"actiontype\":\""
		+ "\",\"parameters\":\"" + parameters
		+ "\",\"screenactionid\":\""
		+ "\",\"linklabel\":\""
		+ "\",\"toscreenid\":\""
		+ "\",\"maxlength\":\""+maxlength
		+ "\",\"labelText\":\""+labelText
		+ "\",\"datasourcetype\":\""+datasourcetype
		+ "\",\"localCodelist\":\""+localCodelist
		+ "\",\"width\":\""+tablewidth
		+ "\",\"widthunit\":\""+checkUndefined($(div).attr('widthunit'))
		+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
	
	
		if($(div).attr('msgvalue') != undefined){
			element += "\",\"msglabel\":\""+msglabel
			+ "\",\"msgvalue\":\""+msgvalue;
		} else {
			element += "\",\"msglabel\":\"\",\"msgvalue\":\"";
		}
		
		element += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		
	if(dataType == 0){
		elementTD += "<span style=\"width: 100%;\">" +
				"<div ondblclick=\"openDialogAutocompleteSetting(this)\" class=\"input-group\" style=\"width:100%\"><input type=\"text\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += " class=\"form-control ui-autocomplete-input\" ";
				} else {
					elementTD += " class=\"qp-input-autocomplete\" ";
				}
				elementTD += " name=\"" 
				+ $(div).attr('columnname') + "\" maxlength=\"" 
				+ $(div).attr('maxlength')
				+		" optionvalue=\"optionValue\" optionlabel=\"optionLabel\" selectsqlid=\"\" emptylabel=\"\" onselectevent=\"\" onchangeevent=\"\" onremoveevent=\"\" mustmatch=\"true\" minlength=\"0\" arg01=\"\" arg02=\"\" arg03=\"\" arg4=\"\" arg05=\"\" arg06=\"\" arg07=\"\" arg08=\"\" arg09=\"\" arg10=\"\" arg11=\"\" arg12=\"\" arg13=\"\" arg14=\"\" arg15=\"\" arg16=\"\" arg17=\"\" arg18=\"\" arg19=\"\" arg20=\"\" placeholder=\"\" autocomplete=\"off\">"
				+ "	<input type=\"hidden\" value=\"\">" 
				+ "	<span class=\"input-group-addon dropdown-toggle\" data-dropdown=\"dropdown\">" 
				+ "		<span class=\"caret\"></span> " 
				+ "	</span>"
				+ "</div>"+element+"</span>"
				"";		
	}
	if(dataType == 1 || dataType == 15 || dataType == 16 || dataType == 18){
		elementTD += "<span style=\"width: 100%;\"><input ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"form-control qp-input-text\"";
					} else {
						elementTD += "class=\"form-control qp-input-text-detail\"";
					}
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" />"+element+"</span>";
	}
	if(dataType == 21){
		elementTD += "<span style=\"width: 100%;\"><Label ondblclick=\"openDialogComponentSetting(this)\"";					
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" >"+$(div).attr('columnname')+"</label>"+element+"</span>";
	}
	if(dataType == 2){
		elementTD += "<span style=\"width: 100%;\"><input  ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"form-control \"";
					} else {
						elementTD += "class=\"com-input-integer-detail\"";
					}
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" />"+element+"</span>";
	}
	if(dataType == 3){
		elementTD += "<span style=\"width: 100%;\"><input  ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"form-control\"";
					} else {
						elementTD += "class=\"com-input-float-detail\"";
					}
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" />"+element+"</span>";
	}
	if(dataType == 8){
		elementTD += "<span style=\"width: 100%;\"><input ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"form-control \"";
					} else {
						elementTD += "class=\"com-input-currency-detail\"";
					}
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" />"+element+"</span>";
	}
	if(dataType == 4){
		elementTD += "<span><div class=\"input-group date\">"
		elementTD += "<span><input ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
						if($(div).attr('elementtype') == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control\"";
						}
		elementTD += " name=\"" 
							+ $(div).attr('columnname') + "\" maxlength=\"" 
							+ $(div).attr('maxlength') + "\" />"+element+"</span>";
		elementTD += "<span class=\"input-group-addon\">"
							+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
							+ "</span>"
						+ "</div></span>";
	}
	if(dataType == 14){
		
		elementTD += "<span><div class=\"input-group date\">"
		elementTD += "<span><input ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
						if($(div).attr('elementtype') == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control \"";
						}
		elementTD += " name=\"" 
						+ $(div).attr('columnname') + "\" maxlength=\"" 
						+ $(div).attr('maxlength') + "\" />"+element+"</span>";
		elementTD += "<span class=\"input-group-addon\">"
						+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
						+ "</span>"
					+ "</div></span>";
	}
	if(dataType == 9){
		
		elementTD += "<span><div class=\"input-group date\">"
			elementTD += "<span><input ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
						if($(div).attr('elementtype') == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"com-input-timepicker-detail\"";
						}
			elementTD += " name=\"" 
							+ $(div).attr('columnname') + "\" maxlength=\"" 
							+ $(div).attr('maxlength') + "\" />"+element+"</span>";
			elementTD += "<span class=\"input-group-addon\">"
							+ "<span class=\"glyphicon glyphicon-time\"></span>"
							+ "</span>"
						+ "</div></span>";			
	}
	if(dataType == 5){
		if($(div).attr('msgvalue') != undefined && $(div).attr('msgvalue').length > 0){
			var msgLabelArr = $(div).attr('msglabel').split("�");
			var msgValArr = $(div).attr('msgvalue').split("�");
			elementTD += "<span onclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\">";
			$(msgValArr).each(function(j){
				elementTD += "<input  type=\"radio\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"com-input-radio-detail\"";
				}
				if($(div).attr('msglabel') != undefined && $(div).attr('msglabel').length == 0){					
					elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+msgValArr[j]+"</label>";
				} else {					
					elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+msgLabelArr[j]+"</label>";
				}
			});
			elementTD += ""+element+"</span>";
		} else {
			elementTD += "<span onclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\"><input type=\"radio\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"com-input-radio-detail\"";
				}
			elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
			elementTD += "<input type=\"radio\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"com-input-radio-detail\"";
				}
			elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>"+element+"</span>";
		}
	}
	if(dataType == 6){
		if($(div).attr('msgvalue') != undefined && $(div).attr('msgvalue').length > 0){
			var msgLabelArr = $(div).attr('msglabel').split("�");
			var msgValArr = $(div).attr('msgvalue').split("�");
			elementTD += "<span onclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\">";
			$(msgValArr).each(function(j){
				elementTD += "<input type=\"checkbox\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				} else {
					elementTD += "class=\"com-input-checkbox-detail\"";
				}				
				if($(div).attr('msglabel') != undefined && $(div).attr('msglabel').length == 0){					
					elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+msgValArr[j]+"</label>";
				} else {					
					elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+msgLabelArr[j]+"</label>";
				}				
			});
			elementTD += ""+element+"</span>";
		} else {
			elementTD += "<span onclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\"><input type=\"checkbox\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				} else {
					elementTD += "class=\"com-input-checkbox-detail\"";
				}
			elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
			elementTD += "<input type=\"checkbox\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				} else {
					elementTD += "class=\"com-input-checkbox-detail\"";
				}
			elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>"+element+"</span>";
		}
	}
	if(dataType == 7){
		elementTD += "<span style=\"width: 100%;\"><select ondblclick=\"openDialogComponentListSetting(this)\" name=\""+$(div).attr('columnname')+"\" ";
		if($(div).attr('elementtype') == 0){
			elementTD += "class=\"form-control qp-input-select\"";
		} else {
			elementTD += "class=\"form-control qp-input-select-detail\"";
		}
		elementTD += ">";
		if( $(div).attr('msgvalue') != undefined && $(div).attr('msgvalue').length > 0){
			var msgLabelArr = $(div).attr('msglabel').split("�");
			var msgValArr = $(div).attr('msgvalue').split("�");
			
			$(msgValArr).each(function(j){
				if($(div).attr('msglabel') != undefined && $(div).attr('msglabel').length == 0){
					elementTD += "<option>" + msgValArr[j] + "</option>";
				} else {
					elementTD += "<option>" + msgLabelArr[j] + "</option>";
				}
			});
		} else {
			elementTD += "<option>"+dbMsgSource['sc.screendesign.0137']+"1</option><option>"+dbMsgSource['sc.screendesign.0137']+"2</option>";
		}
		elementTD += "</select>"+element+"</span>";
	}
	if(dataType == 10){
		elementTD += "<span><textarea ondblclick=\"openDialogComponentSetting(this)\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"form-control qp-input-textarea\"";
					} else {
						elementTD += "class=\"form-control qp-input-textarea-detail\"";
					}
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "1\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" ></textarea>"+element+"</span>";
	}
	if(dataType == 11){		
		var elementButton =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""
			+ "\",\"datatype\":\""+$(div).attr('datatype')
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+$(div).attr('columnname')
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+$(div).attr("labeltext")
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
			if($(div).attr('msgvalue') != undefined){
				elementButton += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
			} else {
				elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<span style=\"width: 100%;\"><a ondblclick=\"openDialogLinkAreaSetting(this);\" href=\"javascript:\">"+$(div).attr('labelText')+"</a>"+elementButton+"</span>";
	}
	var width = ""
	if($(div).attr("tablewidth") != undefined) {
		width = $(div).attr("tablewidth");
	} else {
		width = "100";
	}
	if(dataType == 22){
		var elementButton =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""
			+ "\",\"datatype\":\""+$(div).attr('datatype')
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+$(div).attr('columnname')
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+$(div).attr("labeltext")
			+ "\",\"width\":\""
			+ "\",\"widthunit\":\""
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
			if($(div).attr('msgvalue') != undefined){
				elementButton += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+msgvalue;
			} else {
				elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<span style=\"width: 100%;\"><a ondblclick=\"openDialogLinkAreaSetting(this);\" href=\"javascript:\">"+$(div).attr('labelText')+"</a>"+elementButton+"</span>";
	}
	
	if(dataType == 23){
		var elementLabel =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""
			+ "\",\"datatype\":\""+$(div).attr('datatype')
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+$(div).attr('columnname')
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+$(div).attr("labelText")
			+ "\",\"isBundle\":\""+ "false"
			+ "\",\"isSubmit\":\""+ "false"
			+ "\",\"navigateTo\":\""
			+ "\",\"navigateToText\":\""
			+ "\",\"customItemContent\":\""
			+ "\",\"itemType\":\"5"
			+ "\",\"screenItemId\":\""+$(div).attr("screenItemId")
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
		if($(div).attr('msgvalue') != undefined){
			elementLabel += "\",\"msglabel\":\""+msglabel
			+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
		} else {
			elementLabel += "\",\"msglabel\":\"\",\"msgvalue\":\"";
		}
		
		elementLabel += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<span><label class=\"qp-item-custom\" ondblclick=\"openDialogCustomSetting(this, true)\" style=\"cursor: pointer;\">"+$(div).attr('labelText')+"</label>"+elementLabel+"</span>";
	}	
	
	if(dataType == 12){
		elementTD += "<span><input onclick=\"event.stopPropagation();return false;\" ondblclick=\"event.stopPropagation();openDialogComponentSetting(this);return false;\" type=\"file\" name=\"" + $(div).attr('columnname') + "\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"qp-input-file\"";
					} else {
						elementTD += "class=\"com-input-file-detail\"";
					}
		elementTD += "/>"+element+"</span>";
	}
	if(dataType == 13){
		var elementButton =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""
			+ "\",\"datatype\":\""+$(div).attr('datatype')
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+$(div).attr('columnname')
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+$(div).attr("labeltext")
			+ "\",\"width\":\""
			+ "\",\"widthunit\":\""
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
			if($(div).attr('msgvalue') != undefined){
				elementButton += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
			} else {
				elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		
		elementTD += "<span style=\"\"><button style=\"width: 100%\" type=\"button\" class=\"btn qp-button\" ondblclick=\"openDialogButtonAreaSetting(this)\" value=\""+$(div).attr('labelText')+"\">"+$(div).attr('labelText')+"</button>"+elementButton+"</span>";
	}
	if(dataType == 20){
		var elementLabel =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""
			+ "\",\"datatype\":\""+$(div).attr('datatype')
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+$(div).attr('columnname')
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+$(div).attr("label")
			+ "\",\"isBundle\":\""+ "false"
			+ "\",\"isSubmit\":\""+ "false"
			+ "\",\"navigateTo\":\""
			+ "\",\"navigateToText\":\""
			+ "\",\"itemType\":\"5"
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
		if($(div).attr('msgvalue') != undefined){
			elementLabel += "\",\"msglabel\":\""+msglabel
			+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
		} else {
			elementLabel += "\",\"msglabel\":\"\",\"msgvalue\":\"";
		}
		
		elementLabel += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<span style=\"width: 100%;\"><label ondblclick=\"openDialogLableSetting(this, true)\" style=\"cursor: pointer; width: 100%;\">"+$(div).attr('label')+"</label>"+elementLabel+"</span>";
	}	
	return elementTD;
}

function returnElementTDSearch(div){
var dataType = $(div).attr('datatype');
	
	var elementTD = "<td><input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"enableGroup\"  /><input type=\"hidden\" name=\"groupTotalElement\"  />";
	var tablecolumncode = "";
	if($(div).attr('tablecolumncode') != undefined){
		tablecolumncode = $(div).attr('tablecolumncode');
	}
	var tablename = "";
	if($(div).attr('tablename') != undefined){
		tablename = $(div).attr('tablename');
	}
	var tablecode = "";
	if($(div).attr('tablecode') != undefined){
		tablecode = $(div).attr('tablecode');
	}
	var tablecolumnname = "";
	if($(div).attr('tablecolumnname') != undefined){
		tablecolumnname = $(div).attr('tablecolumnname');
	}
	
	var physicaldatatype = "";
	if($(div).attr('physicaldatatype') != undefined){
		physicaldatatype = $(div).attr('physicaldatatype');
	}
	var minvalue = "";
	if($(div).attr('minvalue') != undefined){
		minvalue = $(div).attr('minvalue');
	}
	var maxvalue = "";
	if($(div).attr('maxvalue') != undefined){
		maxvalue = $(div).attr('maxvalue');
	}
	var formatcode = "";
	if($(div).attr('formatcode') != undefined){
		formatcode = $(div).attr('formatcode');
	}
	var physicalmaxlength = "";
	if($(div).attr('physicalmaxlength') != undefined){
		physicalmaxlength = $(div).attr('physicalmaxlength');
	}
	var maxlength = "";
	if($(div).attr('maxlength') != undefined){
		maxlength = $(div).attr('maxlength');
	}
	var require = "";
	if($(div).attr('require') != undefined){
		require = $(div).attr('require');
	}
	var rowspan = "";
	if($(div).attr('rowspan') != undefined){
		rowspan = $(div).attr('rowspan');
	}
	var colspan = "";
	if($(div).attr('colspan') != undefined){
		colspan = $(div).attr('colspan');
	}
	var datasource = "";
	if($(div).attr('datasource') != undefined){
		datasource = $(div).attr('datasource');
	}
	var msglabel = "";
	if($(div).attr('msglabel') != undefined){
		msglabel = $(div).attr('msglabel');
	}
	
	var element =  "" 
		+ "<input type=\"hidden\" name=\"formElement\" "
		+ "value='\"label\":\""+$(div).attr('label')
		+ "\",\"datatype\":\""+$(div).attr('datatype')
		+ "\",\"physicaldatatype\":\""+physicaldatatype
		+ "\",\"columnname\":\""+$(div).attr('columnname')
		+ "\",\"rowspan\":\""+rowspan
		+ "\",\"colspan\":\""+colspan
		+ "\",\"datasource\":\""+datasource
		+ "\",\"minvalue\":\""+minvalue
		+ "\",\"maxvalue\":\""+maxvalue
		+ "\",\"formatcode\":\""+formatcode
		+ "\",\"tablename\":\""+tablename
		+ "\",\"tablecode\":\""+tablecode
		+ "\",\"tablecolumnname\":\""+tablecolumnname
		+ "\",\"tablecolumncode\":\""+tablecolumncode
		+ "\",\"connectionmsg\":\""
		+ "\",\"transitiontype\":\""
		+ "\",\"actiontype\":\""
		+ "\",\"parameters\":\""
		+ "\",\"screenactionid\":\""
		+ "\",\"linklabel\":\""
		+ "\",\"toscreenid\":\""
		+ "\",\"maxlength\":\""+maxlength
		+ "\",\"dialogAutocompleteCode\":\""+""
		+ "\",\"dialogAutocompleteText\":\""+""
		+ "\",\"dialogOnChangeEvent\":\""+""
		+ "\",\"dialogOnSelectEvent\":\""+""
		+ "\",\"navigateTo\":\""
		+ "\",\"navigateToText\":\""
		+ "\",\"labelText\":\""+ $(div).attr("label")
		+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
	
	
		if($(div).attr('msgvalue') != undefined){
			element += "\",\"msglabel\":\""+msglabel
			+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
		} else {
			element += "\",\"msglabel\":\"\",\"msgvalue\":\"";
		}
		
		element += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		
	if(dataType == 0){
		elementTD += "<span>" +
				"<div ondblclick=\"openDialogAutocompleteSetting(this)\" class=\"input-group\" style=\"width:100%\"><input type=\"text\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += " class=\"form-control ui-autocomplete-input\" ";
				} else {
					elementTD += " class=\"qp-input-autocomplete\" ";
				}
				elementTD += " name=\"" 
				+ $(div).attr('columnname') + "\" maxlength=\"" 
				+ $(div).attr('maxlength')
				+		" optionvalue=\"optionValue\" optionlabel=\"optionLabel\" selectsqlid=\"\" emptylabel=\"\" onselectevent=\"\" onchangeevent=\"\" onremoveevent=\"\" mustmatch=\"true\" minlength=\"0\" arg01=\"\" arg02=\"\" arg03=\"\" arg4=\"\" arg05=\"\" arg06=\"\" arg07=\"\" arg08=\"\" arg09=\"\" arg10=\"\" arg11=\"\" arg12=\"\" arg13=\"\" arg14=\"\" arg15=\"\" arg16=\"\" arg17=\"\" arg18=\"\" arg19=\"\" arg20=\"\" placeholder=\"\" autocomplete=\"off\">"
				+ "	<input type=\"hidden\" value=\"\">" 
				+ "	<span class=\"input-group-addon dropdown-toggle\" data-dropdown=\"dropdown\">" 
				+ "		<span class=\"caret\"></span> " 
				+ "	</span>"
				+ "</div>"+element+"</span>"
				"";		
	}
	if(dataType == 1 || dataType == 15 || dataType == 16 || dataType == 18){
		elementTD += "<span style=\"width: 100%;\"><input ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"form-control qp-input-text\"";
					} else {
						elementTD += "class=\"form-control qp-input-text-detail\"";
					}
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" />"+element+"</span>";
	}
	if(dataType == 21){
		elementTD += "<span style=\"width: 100%;\"><Label ondblclick=\"openDialogComponentSetting(this)\" ";					
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" >"+$(div).attr('columnname')+"</label>"+element+"</span>";
	}
	if(dataType == 2){
		elementTD += "<span style=\"width: 100%;\"><div  ondblclick=\"openDialogComponentSetting(this)\" style=\"width: 100%;\">" +
				"<input type=\"text\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"form-control qp-input-from pull-left\"";
					} else {
						elementTD += "class=\"com-input-integer-detail\"";
					}
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" />" +
					"" + "<div class=\"qp-separator-from-to\">~</div>" +
					"<input  ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"form-control qp-input-to pull-right\"";
					} else {
						elementTD += "class=\"com-input-integer-detail\"";
					}
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" />" +
						
				""+element+"</div></span>";
	}
	if(dataType == 3){
		elementTD += "<span style=\"width: 100%;\"><div ondblclick=\"openDialogComponentSetting(this)\" style=\"width: 100%;\">" +
					"<input type=\"text\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"form-control qp-input-from pull-left\"";
					} else {
						elementTD += "class=\"com-input-integer-detail\"";
					}
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" />" +
					"" + "<div class=\"qp-separator-from-to\">~</div>" +
					"<input  ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"form-control qp-input-to pull-right\"";
					} else {
						elementTD += "class=\"com-input-integer-detail\"";
					}
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" />" +
						
				""+element+"</div></span>";
	}
	if(dataType == 8){
		elementTD += "<span style=\"width: 100%;\"><input ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"form-control\"";
					} else {
						elementTD += "class=\"com-input-currency-detail\"";
					}
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" />"+element+"</span>";
	}
	if(dataType == 4){
		elementTD += "<span style=\"width: 100%;\"><div ondblclick=\"openDialogComponentSetting(this)\">" +
		"<div class=\"input-group date qp-input-from pull-left\">";
		elementTD += "<input  type=\"text\" ";
						if($(div).attr('elementtype') == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control \"";
						}
		elementTD += " name=\"" 
						+ $(div).attr('columnname') + "\" maxlength=\"" 
						+ $(div).attr('maxlength') + "\" />";
		elementTD += "<span class=\"input-group-addon\">"
						+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
						+ "</span>"
					+ "</div>"
		+ "<div class=\"qp-separator-from-to\">~</div>" +
		"<div class=\"input-group date qp-input-to pull-right\">";
		elementTD += "<input  type=\"text\" ";
						if($(div).attr('elementtype') == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control \"";
						}
		elementTD += " name=\"" 
						+ $(div).attr('columnname') + "\" maxlength=\"" 
						+ $(div).attr('maxlength') + "\" />";
		elementTD += "<span class=\"input-group-addon\">"
						+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
						+ "</span>"
					+ "</div>"
					;
		elementTD += "</div>"+element+"</span>";
	}
	if(dataType == 14){
		
		elementTD += "<span style=\"width: 100%;\"><div ondblclick=\"openDialogComponentSetting(this)\">" +
				"<div class=\"input-group date qp-input-from pull-left\">";
		elementTD += "<input  type=\"text\" ";
						if($(div).attr('elementtype') == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control \"";
						}
		elementTD += " name=\"" 
						+ $(div).attr('columnname') + "\" maxlength=\"" 
						+ $(div).attr('maxlength') + "\" />";
		elementTD += "<span class=\"input-group-addon\">"
						+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
						+ "</span>"
					+ "</div>"
		+ "<div class=\"qp-separator-from-to\">~</div>" +
		"<div class=\"input-group date qp-input-to pull-right\">";
		elementTD += "<input  type=\"text\" ";
						if($(div).attr('elementtype') == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control \"";
						}
		elementTD += " name=\"" 
						+ $(div).attr('columnname') + "\" maxlength=\"" 
						+ $(div).attr('maxlength') + "\" />";
		elementTD += "<span class=\"input-group-addon\">"
						+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
						+ "</span>"
					+ "</div>"
					;
		elementTD += "</div>"+element+"</span>";
	}
	if(dataType == 9){
		
		elementTD += "<span style=\"width: 100%;\"><div ondblclick=\"openDialogComponentSetting(this)\">" +
		"<div class=\"input-group date qp-input-from pull-left\">";
		elementTD += "<input  type=\"text\" ";
						if($(div).attr('elementtype') == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control \"";
						}
		elementTD += " name=\"" 
						+ $(div).attr('columnname') + "\" maxlength=\"" 
						+ $(div).attr('maxlength') + "\" />";
		elementTD += "<span class=\"input-group-addon\">"
						+ "<span class=\"glyphicon glyphicon-time\"></span>"
						+ "</span>"
					+ "</div>"
		+ "<div class=\"qp-separator-from-to\">~</div>" +
		"<div class=\"input-group date qp-input-to pull-right\">";
		elementTD += "<input  type=\"text\" ";
						if($(div).attr('elementtype') == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control \"";
						}
		elementTD += " name=\"" 
						+ $(div).attr('columnname') + "\" maxlength=\"" 
						+ $(div).attr('maxlength') + "\" />";
		elementTD += "<span class=\"input-group-addon\">"
						+ "<span class=\"glyphicon glyphicon-time\"></span>"
						+ "</span>"
					+ "</div>"
					;
		elementTD += "</div>"+element+"</span>";
	}
	if(dataType == 5){
		if( $(div).attr('msgvalue') != undefined && $(div).attr('msgvalue').length > 0){
			var msgLabelArr = $(div).attr('msglabel').split("�");
			var msgValArr = $(div).attr('msgvalue').split("�");
			elementTD += "<span onclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\">";
			$(msgValArr).each(function(j){
				elementTD += "<input  type=\"radio\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"com-input-radio-detail\"";
				}
				if($(div).attr('msglabel') != undefined && $(div).attr('msglabel').length == 0){					
					elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+msgValArr[j]+"</label>";
				} else {					
					elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+msgLabelArr[j]+"</label>";
				}
			});
			elementTD += ""+element+"</span>";
		} else {
			elementTD += "<span onclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\"><input type=\"radio\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"com-input-radio-detail\"";
				}
			elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
			elementTD += "<input type=\"radio\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"com-input-radio-detail\"";
				}
			elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>"+element+"</span>";
		}
	}
	if(dataType == 6){
		if($(div).attr('msgvalue') != undefined && $(div).attr('msgvalue').length > 0){
			var msgLabelArr = $(div).attr('msglabel').split("�");
			var msgValArr = $(div).attr('msgvalue').split("�");
			elementTD += "<span onclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\">";
			$(msgValArr).each(function(j){
				elementTD += "<input type=\"checkbox\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				} else {
					elementTD += "class=\"com-input-checkbox-detail\"";
				}				
				if($(div).attr('msglabel') != undefined && $(div).attr('msglabel').length == 0){					
					elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+msgValArr[j]+"</label>";
				} else {					
					elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+msgLabelArr[j]+"</label>";
				}				
			});
			elementTD += ""+element+"</span>";
		} else {
			elementTD += "<span onclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\"><input type=\"checkbox\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				} else {
					elementTD += "class=\"com-input-checkbox-detail\"";
				}
			elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
			elementTD += "<input type=\"checkbox\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				} else {
					elementTD += "class=\"com-input-checkbox-detail\"";
				}
			elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>"+element+"</span>";
		}
	}
	if(dataType == 7){
		elementTD += "<span><select ondblclick=\"openDialogComponentListSetting(this)\" name=\""+$(div).attr('columnname')+"\" ";
		if($(div).attr('elementtype') == 0){
			elementTD += "class=\"form-control qp-input-select\"";
		} else {
			elementTD += "class=\"form-control qp-input-select-detail\"";
		}
		elementTD += ">";
		if($(div).attr('msgvalue') != undefined && $(div).attr('msgvalue').length > 0){
			var msgLabelArr = $(div).attr('msglabel').split("�");
			var msgValArr = $(div).attr('msgvalue').split("�");
			
			$(msgValArr).each(function(j){
				if($(div).attr('msglabel') != undefined && $(div).attr('msglabel').length == 0){
					elementTD += "<option>" + msgValArr[j] + "</option>";
				} else {
					elementTD += "<option>" + msgLabelArr[j] + "</option>";
				}
			});
		} else {
			elementTD += "<option>"+dbMsgSource['sc.screendesign.0137']+"1</option><option>"+dbMsgSource['sc.screendesign.0137']+"2</option>";
		}
		elementTD += "</select>"+element+"</span>";
	}
	if(dataType == 10){
		elementTD += "<span><textarea ondblclick=\"openDialogComponentSetting(this)\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"form-control qp-input-textarea\"";
					} else {
						elementTD += "class=\"form-control qp-input-textarea-detail\"";
					}
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "1\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" ></textarea>"+element+"</span>";
	}
	if(dataType == 11){	
		
		var elementButton =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""+""
			+ "\",\"datatype\":\""+$(div).attr('datatype')
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+$(div).attr('columnname')
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+$(div).attr("labeltext")
			+ "\",\"navigateTo\":\""
			+ "\",\"navigateToText\":\""
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
			if($(div).attr('msgvalue') != undefined){
				elementButton += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
			} else {
				elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<span><a ondblclick=\"openDialogLinkAreaSetting(this);\" href=\"javascript:\">"+$(div).attr('labelText')+"</a>"+elementButton+"</span>";
	}
	
	if(dataType == 22){
		var elementButton =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""
			+ "\",\"datatype\":\""+$(div).attr('datatype')
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+$(div).attr('columnname')
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+$(div).attr("labeltext")
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
			if($(div).attr('msgvalue') != undefined){
				elementButton += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+msgvalue;
			} else {
				elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<span style=\"width: 100%;\"><a ondblclick=\"openDialogLinkAreaSetting(this);\" href=\"javascript:\">"+$(div).attr('labelText')+"</a>"+elementButton+"</span>";
	}
	
	if(dataType == 23){
		var elementLabel =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""
			+ "\",\"datatype\":\""+$(div).attr('datatype')
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+$(div).attr('columnname')
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+$(div).attr("labelText")
			+ "\",\"isBundle\":\""+ "false"
			+ "\",\"isSubmit\":\""+ "false"
			+ "\",\"navigateTo\":\""
			+ "\",\"navigateToText\":\""
			+ "\",\"customItemContent\":\"" + $(div).attr('customItemContent')
			+ "\",\"itemType\":\"5"
			+ "\",\"screenItemId\":\""+$(div).attr("screenItemId")
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
		if($(div).attr('msgvalue') != undefined){
			elementLabel += "\",\"msglabel\":\""+msglabel
			+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
		} else {
			elementLabel += "\",\"msglabel\":\"\",\"msgvalue\":\"";
		}
		
		elementLabel += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<span><label class=\"qp-item-custom\" ondblclick=\"openDialogCustomSetting(this, true)\" style=\"cursor: pointer;\">"+$(div).attr('labelText')+"</label>"+elementLabel+"</span>";
	}	
	
	
	if(dataType == 12){
		elementTD += "<span><input onclick=\"event.stopPropagation();return false;\" ondblclick=\"event.stopPropagation();openDialogComponentSetting(this);return false;\" type=\"file\" name=\"" + $(div).attr('columnname') + "\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"qp-input-file\"";
					} else {
						elementTD += "class=\"com-input-file-detail\"";
					}
		elementTD += "/>"+element+"</span>";
	}
	if(dataType == 13){
		var elementButton =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""
			+ "\",\"datatype\":\""+$(div).attr('datatype')
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+$(div).attr('columnname')
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+$(div).attr("labeltext")
			+ "\",\"navigateTo\":\""
			+ "\",\"navigateToText\":\""
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
		if($(div).attr('msgvalue') != undefined){
			elementButton += "\",\"msglabel\":\""+msglabel
			+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
		} else {
			elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
		}
		
		elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		
		elementTD += "<span><button type=\"button\" class=\"btn qp-button\" ondblclick=\"openDialogButtonAreaSetting(this)\" value=\""+$(div).attr('labelText')+"\">"+$(div).attr('labelText')+"</button>"+elementButton+"</span>";
	}
	if(dataType == 20){
		var elementLabel =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""
			+ "\",\"datatype\":\""+$(div).attr('datatype')
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+$(div).attr('columnname')
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+$(div).attr("label")
			+ "\",\"isBundle\":\""+ "false"
			+ "\",\"isSubmit\":\""+ "false"
			+ "\",\"navigateTo\":\""
			+ "\",\"navigateToText\":\""
			+ "\",\"itemType\":\"5"
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
		if($(div).attr('msgvalue') != undefined){
			elementLabel += "\",\"msglabel\":\""+msglabel
			+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
		} else {
			elementLabel += "\",\"msglabel\":\"\",\"msgvalue\":\"";
		}
		
		elementLabel += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
elementTD += "<span><label ondblclick=\"openDialogLableSetting(this, true)\" style=\"cursor: pointer;\">"+$(div).attr('label')+"</label>"+elementLabel+"</span>";
	}
	elementTD += "</td>";
	return elementTD;
}

function checkUndefined(obj) {
	if (obj != undefined) {
		return obj;
	} else {
		return '';
	}
}

function checkUndefinedFromTo(obj) {
	if (obj != undefined) {
		return obj;
	} else {
		return '1';
	}
}

function returnElementTDSearchEntity(item, displayGroupType){
	var dataType = item.datatype;
	var displayFromTo = item.displayFromToOutput;
	var areaCustomType = item.areaCustomType;
	var itemStyleDiv = "";
	var itemStyleInput = "";
	var arrStyle = item.style.split(";");
	for (var i = 0; i < arrStyle.length; i++) {
		if(arrStyle[i].indexOf("margin-") > -1){
			itemStyleDiv += arrStyle[i] + ";";
		} else {
			itemStyleInput += arrStyle[i] + ";";
		}
	}
	itemStyleDiv = itemStyleDiv.slice(0, -1);
	itemStyleInput = itemStyleInput.slice(0, -1);
		var elementTD = "";
		var tablecolumncode = "";
		if(item.tablecolumncode != undefined){
			tablecolumncode = item.tablecolumncode;
		}
		var tablename = "";
		if(item.tablename != undefined){
			tablename = item.tablename;
		}
		var tablecode = "";
		if(item.tablecode != undefined){
			tablecode = item.tablecode;
		}
		var tablecolumnname = "";
		if(item.tablecolumnname != undefined){
			tablecolumnname = item.tablecolumnname;
		}
		
		var physicaldatatype = "";
		if(item.physicaldatatype != undefined){
			physicaldatatype = item.physicaldatatype;
		}
		var minvalue = "";
		if(item.minvalue != undefined){
			minvalue = item.minvalue;
		}
		var maxvalue = "";
		if(item.maxvalue != undefined){
			maxvalue = item.maxvalue;
		}
		var formatcode = "";
		if(item.formatcode != undefined){
			formatcode = item.formatcode;
		}
		var physicalmaxlength = "";
		if(item.physicalmaxlength != undefined){
			physicalmaxlength = item.physicalmaxlength;
		}
		var maxlength = "";
		if(item.maxlength != undefined){
			maxlength = item.maxlength;
		}
		var require = "";
		if(item.require != undefined){
			require = item.require;
		}
		var rowspan = "";
		if(item.rowspan != undefined){
			rowspan = item.rowspan;
		}
		var colspan = "";
		if(item.colspan != undefined){
			colspan = item.colspan;
		}
		var datasource = "";
		if(item.datasource != undefined){
			datasource = item.datasource;
		}
		var msglabel = "";
		if(item.msglabel != undefined){
			msglabel = item.msglabel;
		}
		var labelText = "";
		if(item.labelText != undefined){
			labelText = item.labelText;
		}
		
		var screenItemId = "";
		if(item.screenItemId != undefined){
			screenItemId = item.screenItemId;
		}
		
		var screenDesignIdCodeListId = "";
		if(item.screenDesignIdCodeListId != undefined){
			screenDesignIdCodeListId = item.screenDesignIdCodeListId;
		}
		
		var screenItemIdCodeListId = "";
		if(item.screenItemIdCodeListId != undefined){
			screenItemIdCodeListId = item.screenItemIdCodeListId;
		}
		
		var screenDesignTextCodeListId = "";
		if(item.screenDesignTextCodeListId != undefined){
			screenDesignTextCodeListId = item.screenDesignTextCodeListId;
		}
		
		var screenItemTextCodeListId = "";
		if(item.screenItemTextCodeListId != undefined){
			screenItemTextCodeListId = item.screenItemTextCodeListId;
		}
		var messageLevel = "";
		var messageLevelText = "";
		if(item.messageLevel != "" && item.messageLevel != undefined) {
			messageLevel = item.messageLevel;
		}
		if(item.messageLevelText != "" && item.messageLevelText != undefined) {
			messageLevelText = item.messageLevelText;
		}
		
		var messageConfirm = "";
		if(item.messageConfirm != "" && item.messageConfirm != undefined) {
			messageConfirm = item.messageConfirm
		}
		
		var messageConfirmText = "";
		if(item.messageConfirmText != "" && item.messageConfirmText != undefined) {
			messageConfirmText = item.messageConfirmText;
		}
		
		var enableConfirm = "";
		if(item.enableConfirm != "" && item.enableConfirm != undefined) {
			enableConfirm = item.enableConfirm;
		}
		
		var events = "[]";
		
		if (item.events != undefined && item.events != null && item.events != 'null') {
			events = convertToString(item.events);
		}
		
		if (events == null || events == 'null') {
			events = "[]";
		}
		
		var element =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""+item.label
			+ "\",\"labelText\":\""+labelText
			+ "\",\"datatype\":\""+item.datatype
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+item.columnname
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\"" + item.parameters
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"baseType\":\""+item.baseType
			+ "\",\"codelistCode\":\""+item.codelistCode
			+ "\",\"codelistText\":\""+item.codelistText
			+ "\",\"isEnable\":\""+item.isEnable
			+ "\",\"localCodelist\":\""+item.localCodelist
			+ "\",\"isSupportOptionValue\":\""+item.isSupportOptionValue
			+ "\",\"dialogAutocompleteCode\":\""+item.dialogAutocompleteCode
			+ "\",\"dialogAutocompleteText\":\""+item.dialogAutocompleteText
			+ "\",\"dialogOnChangeEvent\":\""+item.dialogOnChangeEvent
			+ "\",\"dialogOnSelectEvent\":\""+item.dialogOnSelectEvent
			+ "\",\"mandatory\":\""+item.mandatory
			+ "\",\"width\":\""+item.width
			+ "\",\"widthunit\":\""+item.widthunit
			+ "\",\"outputBeanId\":\""+item.outputBeanId
			+ "\",\"tabindex\":\""+item.tabindex
			+ "\",\"events\":"+events
			+ ",\"physicalmaxlength\":\""+physicalmaxlength
			+ "\",\"datasourcetype\":\""+item.datasourcetype
			+ "\",\"sqldesignid\":\""+item.sqldesignid
			+ "\",\"sqldesignidtext\":\""+item.sqldesignidtext
			+ "\",\"optionlabel\":\""+item.optionlabel
			+ "\",\"optionlabeltext\":\""+item.optionlabeltext
			+ "\",\"optionvalue\":\""+item.optionvalue
			+ "\",\"optionvaluetext\":\""+item.optionvaluetext
			+ "\",\"screenItemId\":\""+item.screenItemId
			+ "\",\"style\":\""+item.style
			+ "\",\"hoverStyle\":\""+item.hoverStyle
			+ "\",\"icon\":\""+item.icon
			+ "\",\"showLabel\":\""+item.showLabel	
			+ "\",\"enablePassword\":\""+item.enablePassword
			+ "\",\"allowAnyInput\":\""+item.allowAnyInput
			+ "\",\"buttonStyle\":\""+item.buttonStyle
			+ "\",\"navigateToBlogic\":\""+item.navigateToBlogic
			+ "\",\"navigateToBlogicText\":\""+item.navigateToBlogicText
			+ "\",\"messageConfirm\":\""+messageConfirm
			+ "\",\"messageConfirmText\":\""+messageConfirmText
			+ "\",\"enableConfirm\":\""+enableConfirm
			+ "\",\"showBlankItem\":\""+item.showBlankItem
			+ "\",\"screenDesignIdCodeListId\":\""+screenDesignIdCodeListId
			+ "\",\"screenItemIdCodeListId\":\""+screenItemIdCodeListId
			+ "\",\"screenDesignTextCodeListId\":\""+screenDesignTextCodeListId
			+ "\",\"screenItemTextCodeListId\":\""+screenItemTextCodeListId
			+ "\",\"displayFromTo\":\""+item.displayFromToOutput
			
			+ "\",\"messageLevel\":\""+messageLevel
			+ "\",\"messageLevelText\":\""+messageLevelText
			
			+ "\",\"defaultvalue\":\""+item.defaultvalue;
		
		
			if(item.msgvalue != undefined){
				element += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+item.msgvalue;
			} else {
				element += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			element += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		
			var styleDisplayGroupType = "";
			if (displayGroupType != undefined && displayGroupType != null && displayGroupType == "2") {
				styleDisplayGroupType="clear: both;";
			}
			
			var styleWidth = "100%";
			
			if (item.width != null && item.width != "" && item.width.length > 0) {
				styleWidth = item.width +item.widthunit;
			}
		
			var style = 'style="width: '+styleWidth+'; float: left;'+styleDisplayGroupType+'"';
			var styleButton = 'style="float: left;'+styleDisplayGroupType+'"';
			var buttonWidth = "";
			if(item.width != undefined) {
				buttonWidth += 'width:'+item.width+'';
			}
			if(item.widthunit != undefined) {
				buttonWidth += item.widthunit;
			}
			
			if (item.groupitemtype == 1) {
				style = 'style="width: '+styleWidth+'; float: left; padding-right: 3px; padding-bottom: 3px;'+styleDisplayGroupType+'"';
			}
			
			var mandatory = "";
			if(item.mandatory == "true"){
				mandatory = "&nbsp;<label class=\"qp-required-field\">(*)</label>";
			}
			
			style1 = style.slice(0, -1);
		if(dataType == 0){
			elementTD += "<span "+style+">" +
					"<div ondblclick=\"openDialogAutocompleteSetting(this)\" class=\"input-group\" style=\"width:100%;" + itemStyleDiv + "\"><input style=\""+ item.inputStyle + ' '+itemStyleInput+"\" type=\"text\" ";
					if(item.elementtype == 0){
						elementTD += " class=\"form-control ui-autocomplete-input\" ";
					} else {
						elementTD += " class=\"qp-input-autocomplete\" ";
					}
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength
					+		" optionvalue=\"optionValue\" optionlabel=\"optionLabel\" selectsqlid=\"\" emptylabel=\"\" onselectevent=\"\" onchangeevent=\"\" onremoveevent=\"\" mustmatch=\"true\" minlength=\"0\" arg01=\"\" arg02=\"\" arg03=\"\" arg4=\"\" arg05=\"\" arg06=\"\" arg07=\"\" arg08=\"\" arg09=\"\" arg10=\"\" arg11=\"\" arg12=\"\" arg13=\"\" arg14=\"\" arg15=\"\" arg16=\"\" arg17=\"\" arg18=\"\" arg19=\"\" arg20=\"\" placeholder=\"\" autocomplete=\"off\">"
					+ "	<input type=\"hidden\" value=\"\">" 
					+ "	<span class=\"input-group-addon dropdown-toggle\" data-dropdown=\"dropdown\">" 
					+ "		<span class=\"caret\"></span> " 
					+ "	</span>"
					+ "</div>"+element+"</span>"
					"";		
		}
		if(dataType == 1 || dataType == 15 || dataType == 16 || dataType == 18){
			var dataTypeElement = "text";
			
			if (dataType == 1) {
				if (item.enablePassword != undefined && item.enablePassword != null && item.enablePassword == 1) {
					dataTypeElement = "password";
				}
			}
			
			elementTD += "<span "+style+"><input style=\""+ item.inputStyle + ' '+item.style+"\" ondblclick=\"openDialogComponentSetting(this)\" type=\""+dataTypeElement+"\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"form-control qp-input-text\"";
						} else {
							elementTD += "class=\"form-control qp-input-text-detail\"";
						}
						elementTD += " name=\"" 
						+ item.columnname + "1\" maxlength=\"" 
						+ item.maxlength + "\" />"+element+"</span>";
		}
		if(dataType == 21){
			var labelText = ((item.labelText != undefined && item.labelText != null && item.labelText.length > 0)?item.columnname:item.columnname);
			
			if (labelText == undefined || labelText == null || labelText.length == 0){
				labelText="Blank";
			}
			
			elementTD += "<span "+style+"><label style=\""+ item.inputStyle + ' '+item.style+"; width: 100%\" ondblclick=\"openDialogComponentSetting(this)\" ";					
						elementTD += " name=\"" 
						+ item.columnname + "1\" maxlength=\"" 
						+ item.maxlength + "\" >"+labelText+"</label>"+element+"</span>";
		}
		if(dataType == 2){
			if (displayFromTo == "0" && areaCustomType == "0") {
				elementTD += "<span "+style1+ ' ' + itemStyleDiv +  '"' +"><div ondblclick=\"openDialogComponentSetting(this)\" style=\"width: 100%;\">";
				elementTD += "<input style=\""+ item.inputStyle + ' ' + itemStyleInput + " qp-input-integer\" type=\"text\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"form-control qp-input-integer\"";
				} else {
					elementTD += "class=\"form-control qp-input-integer\"";
				}
				elementTD += " name=\"" 
				+ item.columnname + "1\" maxlength=\"" 
				+ item.maxlength + "\" />"+element+"</div></span>";
			} else {
				elementTD += "<span "+style1+ ' ' + itemStyleDiv + '"' +"><div  ondblclick=\"openDialogComponentSetting(this)\" style=\"width: 100%;\">" +
						"<input style=\""+ item.inputStyle + ' ' + itemStyleInput + " qp-input-integer\" type=\"text\" ";
							if(item.elementtype == 0){
								elementTD += "class=\"form-control qp-input-from pull-left qp-input-integer\"";
							} else {
								elementTD += "class=\"com-input-integer-detail\"";
							}
							elementTD += " name=\"" 
							+ item.columnname + "1\" maxlength=\"" 
							+ item.maxlength + "\" />" +
							"" + "<div class=\"qp-separator-from-to\">~</div>" +
							"<input style=\""+ item.inputStyle + ' ' + itemStyleInput + " qp-input-integer\" ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
							if(item.elementtype == 0){
								elementTD += "class=\"form-control qp-input-to pull-right qp-input-integer\"";
							} else {
								elementTD += "class=\"com-input-integer-detail\"";
							}
							elementTD += " name=\"" 
							+ item.columnname + "1\" maxlength=\"" 
							+ item.maxlength + "\" />" +
								
						""+element+"</div></span>";
			}
		}
		if(dataType == 3){
			if (displayFromTo == "0" && areaCustomType == "0") {
				elementTD += "<span "+style1+ ' ' + itemStyleDiv +  '"' +"><div ondblclick=\"openDialogComponentSetting(this)\" style=\"width: 100%;\">";
				elementTD += "<input style=\""+ item.inputStyle + ' ' + itemStyleInput + " qp-input-float\" type=\"text\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"form-control qp-input-float\"";
				} else {
					elementTD += "class=\"form-control qp-input-float\"";
				}
				elementTD += " name=\"" 
				+ item.columnname + "1\" maxlength=\"" 
				+ item.maxlength + "\" />"+element+"</div></span>";
			} else {
				elementTD += "<span "+style1+ ' ' + itemStyleDiv+"><div ondblclick=\"openDialogComponentSetting(this)\" style=\"width: 100%;\">" +
							"<input style=\""+ item.inputStyle + ' '+itemStyleInput+"\" type=\"text\" ";
							if(item.elementtype == 0){
								elementTD += "class=\"form-control qp-input-from pull-left qp-input-float\"";
							} else {
								elementTD += "class=\"com-input-float-detail\"";
							}
							elementTD += " name=\"" 
							+ item.columnname + "1\" maxlength=\"" 
							+ item.maxlength + "\" />" +
							"" + "<div class=\"qp-separator-from-to\">~</div>" +
							"<input style=\""+ item.inputStyle + ' '+itemStyleInput+"\" ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
							if(item.elementtype == 0){
								elementTD += "class=\"form-control qp-input-to pull-right qp-input-float\"";
							} else {
								elementTD += "class=\"com-input-float-detail\"";
							}
							elementTD += " name=\"" 
							+ item.columnname + "1\" maxlength=\"" 
							+ item.maxlength + "\" />" +
								
						""+element+"</div></span>";
			}
		}
		if(dataType == 8){
			if (displayFromTo == "0" && areaCustomType == "0") {
				elementTD += "<span "+style1+ ' ' + itemStyleDiv +  '"' +"><div ondblclick=\"openDialogComponentSetting(this)\"  style=\"width: 100%;\">";
				elementTD += "<input style=\""+ item.inputStyle + ' ' + itemStyleInput+ " qp-input-currency\" type=\"text\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"form-control qp-input-currency\"";
				} else {
					elementTD += "class=\"form-control qp-input-currency\"";
				}
				elementTD += " name=\"" 
				+ item.columnname + "1\" maxlength=\"" 
				+ item.maxlength + "\" />"+element+"</div></span>";
			} else {
				elementTD += "<span "+style1+ ' ' + itemStyleDiv +  '"' +"><div ondblclick=\"openDialogComponentSetting(this)\" style=\"width: 100%;\">" +
				"<input style=\""+ item.inputStyle + ' '+itemStyleInput+" qp-input-currency\" type=\"text\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"form-control qp-input-from pull-left qp-input-currency\"";
				} else {
					elementTD += "class=\"com-input-integer-detail\"";
				}
				elementTD += " name=\"" 
				+ item.columnname + "1\" maxlength=\"" 
				+ item.maxlength + "\" />" +
				"" + "<div class=\"qp-separator-from-to\">~</div>" +
				"<input style=\""+ item.inputStyle + ' '+itemStyleInput+" qp-input-currency\" ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"form-control qp-input-to pull-right qp-input-currency\"";
				} else {
					elementTD += "class=\"com-input-integer-detail\"";
				}
				elementTD += " name=\"" 
				+ item.columnname + "1\" maxlength=\"" 
				+ item.maxlength + "\" />" +
					
			""+element+"</div></span>";
			}
		}
		if(dataType == 4){
			if (displayFromTo == "0" && areaCustomType == "0") {
				elementTD += "<span "+style1+ ' ' + itemStyleDiv +  '"' +"><div ondblclick=\"openDialogComponentSetting(this)\"  style=\"width: 100%;\">" +
				"<div class=\"input-group date\">"
				elementTD += "<input style=\""+ item.inputStyle + ' ' +itemStyleInput+"\" type=\"text\" ";
								if(item.elementtype == 0){
									elementTD += "class=\"form-control\"";
								} else {
									elementTD += "class=\"form-control \"";
								}
				elementTD += " name=\"" 
								+ item.columnname + "1\" maxlength=\"" 
								+ item.maxlength + "\" />";
				elementTD += "<span class=\"input-group-addon\">"
					+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
					+ "</span></div></div>"
				elementTD += element+"</span>";
			} else {
				elementTD += "<span "+style1+ ' ' + itemStyleDiv +  '"' +"><div ondblclick=\"openDialogComponentSetting(this)\"  style=\"width: 100%;\">" +
				"<div class=\"input-group date qp-input-from pull-left\">";
				elementTD += "<input style=\""+ item.inputStyle + ' ' +itemStyleInput+"\" type=\"text\" class=\"form-control qp-input-from\"";
				elementTD += " name=\"" 
								+ item.columnname + "1\" maxlength=\"" 
								+ item.maxlength + "\" />";
				elementTD += "<span class=\"input-group-addon\">"
								+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
								+ "</span>"
							+ "</div>"
				+ "<div class=\"qp-separator-from-to\">~</div>" +
				"<div class=\"input-group date qp-input-to pull-right\">";
				elementTD += "<input style=\""+ item.inputStyle + ' ' +itemStyleInput+"\" type=\"text\" ";
								if(item.elementtype == 0){
									elementTD += "class=\"form-control\"";
								} else {
									elementTD += "class=\"form-control \"";
								}
				elementTD += " name=\"" 
								+ item.columnname + "1\" maxlength=\"" 
								+ item.maxlength + "\" />";
				elementTD += "<span class=\"input-group-addon\">"
								+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
								+ "</span>"
							+ "</div>"
							;
				elementTD += "</div>"+element+"</span>";
			}
		}
		if(dataType == 14){
			if (displayFromTo == "0" && areaCustomType == "0") {
				elementTD += "<span "+style1+ ' ' + itemStyleDiv +  '"' +"><div ondblclick=\"openDialogComponentSetting(this)\"  style=\"width: 100%;\">" +
								"<div class=\"input-group date\">"
				elementTD += "<input style=\""+ item.inputStyle + ' ' +itemStyleInput+"\" type=\"text\" ";
								if(item.elementtype == 0){
									elementTD += "class=\"form-control\"";
								} else {
									elementTD += "class=\"form-control \"";
								}
				elementTD += " name=\"" 
								+ item.columnname + "1\" maxlength=\"" 
								+ item.maxlength + "\" />";
				elementTD += "<span class=\"input-group-addon\">"
					+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
					+ "</span></div></div>"
				elementTD += element+"</span>";
			} else {
				elementTD += "<span "+style1+ ' ' + itemStyleDiv +  '"' +"><div ondblclick=\"openDialogComponentSetting(this)\"  style=\"width: 100%;\">" +
						"<div class=\"input-group date qp-input-from pull-left\">";
				elementTD += "<input style=\""+ item.inputStyle + ' ' +itemStyleInput+"\" type=\"text\" ";
								if(item.elementtype == 0){
									elementTD += "class=\"form-control\"";
								} else {
									elementTD += "class=\"form-control \"";
								}
				elementTD += " name=\"" 
								+ item.columnname + "1\" maxlength=\"" 
								+ item.maxlength + "\" />";
				elementTD += "<span class=\"input-group-addon\">"
								+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
								+ "</span>"
							+ "</div>"
				+ "<div class=\"qp-separator-from-to\">~</div>" +
				"<div class=\"input-group date qp-input-to pull-right\">";
				elementTD += "<input style=\""+ item.inputStyle + ' ' +itemStyleInput+"\" type=\"text\" ";
								if(item.elementtype == 0){
									elementTD += "class=\"form-control\"";
								} else {
									elementTD += "class=\"form-control \"";
								}
				elementTD += " name=\"" 
								+ item.columnname + "1\" maxlength=\"" 
								+ item.maxlength + "\" />";
				elementTD += "<span class=\"input-group-addon\">"
								+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
								+ "</span>"
							+ "</div>"
							;
				elementTD += "</div>"+element+"</span>";
			}
		}
		if(dataType == 9){
			if (displayFromTo == "0" && areaCustomType == "0") {
				elementTD += "<span "+style1+ ' ' + itemStyleDiv +  '"' +"><div ondblclick=\"openDialogComponentSetting(this)\"  style=\"width: 100%;\">" +
						"<div class=\"input-group date\">"
				elementTD += "<input style=\""+ item.inputStyle + ' ' +itemStyleInput+"\" type=\"text\" ";
							if(item.elementtype == 0){
								elementTD += "class=\"form-control\"";
							} else {
								elementTD += "class=\"form-control\"";
							}
				elementTD += " name=\"" 
								+ item.columnname + "1\" maxlength=\"" 
								+ item.maxlength + "\" />";
				elementTD += "<span class=\"input-group-addon\">"
								+ "<span class=\"glyphicon glyphicon-time\"></span>"
								+ "</span>"
							+ "</div></div>";
				elementTD += element+"</span>";
			} else {
				elementTD += "<span "+style1+ ' ' + itemStyleDiv +  '"' +"><div ondblclick=\"openDialogComponentSetting(this)\"  style=\"width: 100%;\">" +
				"<div class=\"input-group date qp-input-from pull-left\">";
				elementTD += "<input style=\""+ item.inputStyle + ' ' +itemStyleInput+"\" type=\"text\" ";
								if(item.elementtype == 0){
									elementTD += "class=\"form-control\"";
								} else {
									elementTD += "class=\"form-control \"";
								}
				elementTD += " name=\"" 
								+ item.columnname + "1\" maxlength=\"" 
								+ item.maxlength + "\" />";
				elementTD += "<span class=\"input-group-addon\">"
								+ "<span class=\"glyphicon glyphicon-time\"></span>"
								+ "</span>"
							+ "</div>"
				+ "<div class=\"qp-separator-from-to\">~</div>" +
				"<div class=\"input-group date qp-input-to pull-right\">";
				elementTD += "<input style=\""+ item.inputStyle + ' ' +itemStyleInput+"\" type=\"text\" ";
								if(item.elementtype == 0){
									elementTD += "class=\"form-control\"";
								} else {
									elementTD += "class=\"form-control \"";
								}
				elementTD += " name=\"" 
								+ item.columnname + "1\" maxlength=\"" 
								+ item.maxlength + "\" />";
				elementTD += "<span class=\"input-group-addon\">"
								+ "<span class=\"glyphicon glyphicon-time\"></span>"
								+ "</span>"
							+ "</div>"
							;
				elementTD += "</div>"+element+"</span>";
			}
		}
		if(dataType == 5){
			if(item.msgvalue.length > 0){
				var msgLabelArr = item.msglabel.split("�");
				var msgValArr = item.msgvalue.split("�");
				elementTD += "<span "+style+" ondblclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\">";
				$(msgValArr).each(function(j){
					elementTD += "<input  type=\"radio\" value=\""+msgValArr[j]+"\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
					} else {
						elementTD += "class=\"com-input-radio-detail\"";
					}
					if(item.msglabel == undefined || item.msglabel.length == 0  || (msgLabelArr[j] == null || msgLabelArr[j] == 'null')){					
						elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">"+deEscapeHTMLForView(msgValArr[j])+"</label>";
					} else {					
						elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">"+deEscapeHTMLForView(msgLabelArr[j])+"</label>";
					}
				});
				elementTD += ""+element+"</span>";
			} else {
				elementTD += "<span "+style+" ondblclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\"><input type=\"radio\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
					} else {
						elementTD += "class=\"com-input-radio-detail\"";
					}
				elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
				elementTD += "<input type=\"radio\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
					} else {
						elementTD += "class=\"com-input-radio-detail\"";
					}
				elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>"+element+"</span>";
			}
		}
		if(dataType == 6){
			if(item.physicaldatatype != 8) {
				if(item.msgvalue.length > 0){
					var msgLabelArr = item.msglabel.split("�");
					var msgValArr = item.msgvalue.split("�");
					elementTD += "<span "+style+" ondblclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\">";
					$(msgValArr).each(function(j){
						elementTD += "<input type=\"checkbox\" value=\""+msgValArr[j]+"\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
						} else {
							elementTD += "class=\"com-input-checkbox-detail\"";
						}				
						if(item.msglabel == undefined || item.msglabel.length == 0  || (msgLabelArr[j] == null || msgLabelArr[j] == 'null')){					
							elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">"+deEscapeHTMLForView(msgValArr[j])+"</label>";
						} else {					
							elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">"+deEscapeHTMLForView(msgLabelArr[j])+"</label>";
						}				
					});
					elementTD += ""+element+"</span>";
				} else {
					elementTD += "<span "+style+" ondblclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\">";
					if (item.physicaldatatype == undefined || item.physicaldatatype == '' || item.physicaldatatype != 8) {
						elementTD += "<input type=\"checkbox\" ";
							if(item.elementtype == 0){
								elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
							} else {
								elementTD += "class=\"com-input-checkbox-detail\"";
							}
						elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
					}
					elementTD += "<input type=\"checkbox\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
						} else {
							elementTD += "class=\"com-input-checkbox-detail\"";
						}
					elementTD += " name=\"" + item.columnname + "1\" />";
					if (item.physicaldatatype == undefined || item.physicaldatatype == '' || item.physicaldatatype != 8) {
						elementTD += "<label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>";
					}
					elementTD += element+"</span>";
				}
			} else {
				if(item.msgvalue.length > 0) { 
					var msgLabelArr = item.msglabel.split("�");
					var msgValArr = item.msgvalue.split("�");
					elementTD += "<span "+style+" ondblclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\">";
					$(msgValArr).each(function(j){
						elementTD += "<input type=\"checkbox\" value=\""+msgValArr[j]+"\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
						} else {
							elementTD += "class=\"com-input-checkbox-detail\"";
						}				
						if(item.msglabel == undefined || item.msglabel.length == 0  || (msgLabelArr[j] == null || msgLabelArr[j] == 'null')){					
							elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">"+deEscapeHTMLForView(msgValArr[j])+"</label>";
						} else {					
							elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">"+deEscapeHTMLForView(msgLabelArr[j])+"</label>";
						}				
					});
					elementTD += ""+element+"</span>";
				} else {
					elementTD += "<span "+style+" ondblclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\">";
					if (item.physicaldatatype == undefined || item.physicaldatatype == '' || item.physicaldatatype != 8) {
						elementTD += "<input type=\"checkbox\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
						} else {
							elementTD += "class=\"com-input-checkbox-detail\"";
						}
						elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">true</label>";
					}
					elementTD += "<input type=\"checkbox\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
					} else {
						elementTD += "class=\"com-input-checkbox-detail\"";
					}
					elementTD += " name=\"" + item.columnname + "1\" />";
					/*elementTD += "<label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">false</label>";*/
					elementTD += "<label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\"></label>";
					elementTD += element+"</span>";
				}
			}
		}
		if(dataType == 7){
			elementTD += "<span "+style+"><select style=\""+ item.inputStyle + ' '+item.style+"\" ondblclick=\"openDialogComponentListSetting(this)\" name=\""+item.columnname+"\" ";
			if(item.elementtype == 0){
				elementTD += "class=\"form-control qp-input-select\"";
			} else {
				elementTD += "class=\"form-control qp-input-select-detail\"";
			}
			elementTD += ">";
			if (item.showBlankItem == "1") {
				elementTD += "<option>" + "&nbsp;" + "</option>";
			}
			if(item.msgvalue.length > 0){
				var msgLabelArr = item.msglabel.split("�");
				var msgValArr = item.msgvalue.split("�");
				
				$(msgValArr).each(function(j){
					if(item.msglabel == undefined || item.msglabel.length == 0  || (msgLabelArr[j] == null || msgLabelArr[j] == 'null')){
						elementTD += "<option value=\""+msgValArr[j]+"\">" + deEscapeHTMLForView(msgValArr[j]) + "</option>";
					} else {
						elementTD += "<option value=\""+msgValArr[j]+"\">" + deEscapeHTMLForView(msgLabelArr[j]) + "</option>";
					}
				});
			} else {
				elementTD += "<option>"+dbMsgSource['sc.screendesign.0137']+"1</option><option>"+dbMsgSource['sc.screendesign.0137']+"2</option>";
			}
			elementTD += "</select>"+element+"</span>";
		}
		if(dataType == 10){
			elementTD += "<span "+style+"><textarea style=\""+ item.inputStyle + ' '+item.style+"\" ondblclick=\"openDialogComponentSetting(this)\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"form-control qp-input-textarea\"";
						} else {
							elementTD += "class=\"form-control qp-input-textarea-detail\"";
						}
						elementTD += " name=\"" 
						+ item.columnname + "1\" maxlength=\"" 
						+ item.maxlength + "\" ></textarea>"+element+"</span>";
		}
		if(dataType == 11){	
			
			var elementButton =  "" 
				+ "<input type=\"hidden\" name=\"formElement\" "
				+ "value='\"label\":\""+item.label
				+ "\",\"datatype\":\""+item.datatype
				+ "\",\"physicaldatatype\":\""+physicaldatatype
				+ "\",\"columnname\":\""+item.columnname
				+ "\",\"rowspan\":\""+rowspan
				+ "\",\"colspan\":\""+colspan
				+ "\",\"datasource\":\""+datasource
				+ "\",\"minvalue\":\""+minvalue
				+ "\",\"maxvalue\":\""+maxvalue
				+ "\",\"formatcode\":\""+formatcode
				+ "\",\"tablename\":\""+tablename
				+ "\",\"tablecode\":\""+tablecode
				+ "\",\"tablecolumnname\":\""+tablecolumnname
				+ "\",\"tablecolumncode\":\""+tablecolumncode
				+ "\",\"connectionmsg\":\""
				+ "\",\"transitiontype\":\""
				+ "\",\"screenactionid\":\""
				+ "\",\"linklabel\":\""
				+ "\",\"toscreenid\":\""
				+ "\",\"maxlength\":\""+maxlength
				+ "\",\"labelText\":\""+item.labelText
				+ "\",\"physicalmaxlength\":\""+physicalmaxlength
				+ "\",\"actiontype\":\""+item.actiontype
				+ "\",\"actionName\":\""+item.actionName
				+ "\",\"parameters\":\""+item.parameters
				+ "\",\"navigateTo\":\""+item.navigateTo
				+ "\",\"navigateToText\":\""+item.navigateToText
				+ "\",\"navigateToBlogic\":\""+item.navigateToBlogic
				+ "\",\"navigateToBlogicText\":\""+item.navigateToBlogicText
				+ "\",\"style\":\""+item.style
				+ "\",\"hoverStyle\":\""+item.hoverStyle
				+ "\",\"icon\":\""+item.icon
				+ "\",\"showLabel\":\""+item.showLabel	
				+ "\",\"screenTransition\":\""+item.screenTransition
				+ "\",\"screenTransitionText\":\""+item.screenTransitionText
				+ "\",\"messageLevel\":\""+messageLevel
				+ "\",\"messageLevelText\":\""+messageLevelText
				+ "\",\"messageConfirm\":\""+messageConfirm
				+ "\",\"messageConfirmText\":\""+messageConfirmText
				+ "\",\"enableConfirm\":\""+enableConfirm
				+ "\",\"tabindex\":\""+item.tabindex
				+ "\",\"isSubmit\":\""+item.isSubmit
				;
			
			if(item.msgvalue != undefined){
				elementButton += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+item.msgvalue;
			} else {
				elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<span "+style+"><a style=\""+ item.inputStyle + ' '+item.style+"\" onmouseover=\"this.setAttribute('style', '"+item.hoverStyle+"')\" onmouseout=\"this.setAttribute('style', '"+item.style+"')\" ondblclick=\"openDialogLinkAreaSetting(this);\" href=\"javascript:\">"+item.labelText+"</a>"+elementButton+"</span>";
		}
		var width = ""
		if(item.width != undefined) {
			width = item.width;
		} else {
			width = "100";
		}
		if(dataType == 22){
			var localCodelist = "";
			var datasourcetype = "";
			if(item.localCodelist != undefined) {
				localCodelist = item.localCodelist;
			}
			if(item.datasourcetype != undefined) {
				datasourcetype = item.datasourcetype;
			}
			
			var elementButton =  "" 
				+ "<input type=\"hidden\" name=\"formElement\" "
				+ "value='\"label\":\""+item.label
				+ "\",\"datatype\":\""+item.datatype
				+ "\",\"physicaldatatype\":\""+physicaldatatype
				+ "\",\"columnname\":\""+item.columnname
				+ "\",\"rowspan\":\""+rowspan
				+ "\",\"colspan\":\""+colspan
				+ "\",\"datasource\":\""+datasource
				+ "\",\"minvalue\":\""+minvalue
				+ "\",\"maxvalue\":\""+maxvalue
				+ "\",\"formatcode\":\""+formatcode
				+ "\",\"tablename\":\""+tablename
				+ "\",\"tablecode\":\""+tablecode
				+ "\",\"tablecolumnname\":\""+tablecolumnname
				+ "\",\"tablecolumncode\":\""+tablecolumncode
				+ "\",\"connectionmsg\":\""
				+ "\",\"transitiontype\":\""
				+ "\",\"screenactionid\":\""
				+ "\",\"linklabel\":\""
				+ "\",\"toscreenid\":\""
				+ "\",\"maxlength\":\""+maxlength
				+ "\",\"labelText\":\""+item.labelText
				+ "\",\"physicalmaxlength\":\""+physicalmaxlength
				+ "\",\"actiontype\":\""+item.actiontype
				+ "\",\"actionName\":\""+item.actionName
				+ "\",\"parameters\":\""+item.parameters
				+ "\",\"navigateTo\":\""+item.navigateTo
				+ "\",\"navigateToText\":\""+item.navigateToText
				+ "\",\"navigateToBlogic\":\""+item.navigateToBlogic
				+ "\",\"navigateToBlogicText\":\""+item.navigateToBlogicText
				+ "\",\"style\":\""+item.style
				+ "\",\"hoverStyle\":\""+item.hoverStyle
				+ "\",\"messageConfirm\":\""+item.messageConfirm
				+ "\",\"messageConfirmText\":\""+item.messageConfirmText
				+ "\",\"enableConfirm\":\""+item.enableConfirm
				+ "\",\"messageConfirmCode\":\""+item.messageConfirmCode
				+ "\",\"screenItemId\":\""+item.screenItemId
				+ "\",\"width\":\""+width
				+ "\",\"widthunit\":\""+item.widthunit
				+ "\",\"isSubmit\":\""+item.isSubmit
				+ "\",\"screenDesignIdCodeListId\":\""+screenDesignIdCodeListId
				+ "\",\"screenItemIdCodeListId\":\""+screenItemIdCodeListId
				+ "\",\"screenDesignTextCodeListId\":\""+screenDesignTextCodeListId
				+ "\",\"screenItemTextCodeListId\":\""+screenItemTextCodeListId
				+ "\",\"codelistCode\":\""+item.codelistCode
				+ "\",\"codelistText\":\""+item.codelistText
				+ "\",\"localCodelist\":\""+localCodelist
				+ "\",\"tabindex\":\""+item.tabindex
				+ "\",\"screenTransition\":\""+item.screenTransition
				+ "\",\"screenTransitionText\":\""+item.screenTransitionText
				+ "\",\"datasourcetype\":\""+datasourcetype
				
				;
			
			
				if(item.msgvalue != undefined){
					elementButton += "\",\"msglabel\":\""+msglabel
					+ "\",\"msgvalue\":\""+item.msgvalue;
				} else {
					elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
				}
				
				elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
			elementTD += "<span "+style+"><a style=\""+ item.inputStyle + ' ' +item.style+"\" onmouseover=\"this.setAttribute('style', '"+item.hoverStyle+"')\" onmouseout=\"this.setAttribute('style', '"+item.style+"')\" ondblclick=\"openDialogLinkAreaSetting(this);\" href=\"javascript:\">"+item.columnname+"</a>"+elementButton+"</span>";
		}
		
		if(dataType == 23){
			var elementLabel =  "" 
				+ "<input type=\"hidden\" name=\"formElement\" "
				+ "value='\"label\":\""
				+ "\",\"datatype\":\""+item.datatype
				+ "\",\"physicaldatatype\":\""+physicaldatatype
				+ "\",\"columnname\":\""+item.columnname
				+ "\",\"rowspan\":\""+rowspan
				+ "\",\"colspan\":\""+colspan
				+ "\",\"datasource\":\""+datasource
				+ "\",\"minvalue\":\""+minvalue
				+ "\",\"maxvalue\":\""+maxvalue
				+ "\",\"formatcode\":\""+formatcode
				+ "\",\"tablename\":\""+tablename
				+ "\",\"tablecode\":\""+tablecode
				+ "\",\"tablecolumnname\":\""+tablecolumnname
				+ "\",\"tablecolumncode\":\""+tablecolumncode
				+ "\",\"connectionmsg\":\""
				+ "\",\"transitiontype\":\""
				+ "\",\"actiontype\":\""
				+ "\",\"parameters\":\""
				+ "\",\"screenactionid\":\""
				+ "\",\"linklabel\":\""
				+ "\",\"toscreenid\":\""
				+ "\",\"maxlength\":\""+maxlength
				+ "\",\"labelText\":\""+item.labelText
				+ "\",\"isBundle\":\""+ "false"
				+ "\",\"isSubmit\":\""+ "false"
				+ "\",\"navigateTo\":\""
				+ "\",\"navigateToText\":\""
				+ "\",\"customItemContent\":\"" + item.customItemContent
				+ "\",\"itemType\":\"5"
				+ "\",\"screenItemId\":\""+ screenItemId
				+ "\",\"screenTransition\":\""+item.screenTransition
				+ "\",\"screenTransitionText\":\""+item.screenTransitionText
				+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
			
			
			if(item.msgvalue != undefined){
				elementLabel += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+item.msgvalue;
			} else {
				elementLabel += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementLabel += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
			elementTD += "<span "+style+"><label class=\"qp-item-custom\" ondblclick=\"openDialogCustomSetting(this, true)\" style=\"cursor: pointer;\">"+item.labelText+"</label>"+elementLabel+"</span>";
		}	
		
		if(dataType == 12){
			elementTD += "<span "+style+"><input onclick=\"event.stopPropagation();return false;\" ondblclick=\"event.stopPropagation();openDialogComponentSetting(this);return false;\" type=\"file\" name=\"" + item.columnname + "1\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"qp-input-file\"";
						} else {
							elementTD += "class=\"com-input-file-detail\"";
						}
			elementTD += "/>"+element+"</span>";
		}
		/*var width = ""
		if(item.width != undefined) {
			width = item.width;
		} else {
			width = "100";
		}*/
		if(dataType == 13){
			var elementButton =  "" 
				+ "<input type=\"hidden\" name=\"formElement\" "
				+ "value='\"label\":\""+item.label
				+ "\",\"datatype\":\""+item.datatype
				+ "\",\"physicaldatatype\":\""+physicaldatatype
				+ "\",\"columnname\":\""+item.columnname
				+ "\",\"rowspan\":\""+rowspan
				+ "\",\"colspan\":\""+colspan
				+ "\",\"datasource\":\""+datasource
				+ "\",\"minvalue\":\""+minvalue
				+ "\",\"maxvalue\":\""+maxvalue
				+ "\",\"formatcode\":\""+formatcode
				+ "\",\"tablename\":\""+tablename
				+ "\",\"tablecode\":\""+tablecode
				+ "\",\"tablecolumnname\":\""+tablecolumnname
				+ "\",\"tablecolumncode\":\""+tablecolumncode
				+ "\",\"connectionmsg\":\""
				+ "\",\"transitiontype\":\""
				+ "\",\"screenactionid\":\""
				+ "\",\"linklabel\":\""
				+ "\",\"toscreenid\":\""
				+ "\",\"maxlength\":\""+maxlength
				+ "\",\"labelText\":\""+item.labelText
				+ "\",\"physicalmaxlength\":\""+physicalmaxlength
				+ "\",\"actiontype\":\""+item.actiontype
				+ "\",\"actionName\":\""+item.actionName
				+ "\",\"parameters\":\""+item.parameters
				+ "\",\"navigateTo\":\""+item.navigateTo
				+ "\",\"navigateToText\":\""+item.navigateToText
				+ "\",\"style\":\""+item.style
				+ "\",\"hoverStyle\":\""+item.hoverStyle
				+ "\",\"icon\":\""+item.icon
				+ "\",\"showLabel\":\""+item.showLabel	
				+ "\",\"buttonStyle\":\""+item.buttonStyle
				+ "\",\"navigateToBlogic\":\""+item.navigateToBlogic
				+ "\",\"navigateToBlogicText\":\""+item.navigateToBlogicText
				+ "\",\"width\":\""+item.width
				+ "\",\"widthunit\":\""+item.widthunit
				+ "\",\"screenTransition\":\""+item.screenTransition
				+ "\",\"screenTransitionText\":\""+item.screenTransitionText
				
				+ "\",\"messageConfirm\":\""+messageConfirm
				+ "\",\"messageConfirmText\":\""+messageConfirmText
				+ "\",\"enableConfirm\":\""+enableConfirm
				+ "\",\"tabindex\":\""+item.tabindex
				+ "\",\"messageLevel\":\""+messageLevel
				+ "\",\"messageLevelText\":\""+messageLevelText
				
				+ "\",\"isSubmit\":\""+item.isSubmit
				;
			
			
			if(item.msgvalue != undefined){
				elementButton += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+item.msgvalue;
			} else {
				elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
			
			var buttonStyle = "";
			if (item.buttonStyle != undefined) {
				if (item.buttonStyle == 0 || item.buttonStyle == 1) {
					buttonStyle = "qp-button-type-design";
				} else if (item.buttonStyle == 2) {
					buttonStyle = "qp-button-type-warning-design";
				} else if (item.buttonStyle == 3) {
					buttonStyle = "qp-button-type-client-design";
				}
			}
			
			if (item.width != '' && item.width != undefined) {
				var newButtonStyle = styleButton.slice(0, -1) + " width: " + item.width + '' + item.widthunit + ";" + '"';
				styleButton =  newButtonStyle;
			}
			if (item.icon != undefined && item.showLabel != undefined && item.icon.length > 0) {
				var buttonContent = "<i class=\""+item.icon+"\"></i>";
				if ((item.showLabel == '1' || item.showLabel == 1) && item.labelText != undefined && item.labelText != null && item.labelText.length > 0) {
					buttonContent += "&nbsp;&nbsp;" + item.labelText;
				}
				elementTD += "<span "+styleButton+"><button style=\""+ item.inputStyle + ' '+item.style+";width:100%\" type=\"button\" class=\"btn qp-button "+buttonStyle+"\" ondblclick=\"openDialogButtonAreaSetting(this)\" value=\""+item.labelText+"\">"+buttonContent+"</button>"+elementButton+"</span>";
			} else {
				elementTD += "<span "+styleButton+"><button style=\""+ item.inputStyle + ' '+item.style+";width:100%\" type=\"button\" class=\"btn qp-button "+buttonStyle+"\" ondblclick=\"openDialogButtonAreaSetting(this)\" value=\""+item.labelText+"\">"+item.labelText+"</button>"+elementButton+"</span>";
			}
		}
		if(dataType == 20){
			if (item.labelText == undefined || item.labelText == null) {
				item["labelText"] = dbMsgSource['sc.screendesign.0050'];
			}
			var width = "";
			if (item.width != undefined && item.width != null) {
				width = item.width;
			}
			var widthunit = "";
			if (item.widthunit != undefined && item.widthunit != null) {
				widthunit = item.widthunit;
			}
			
			var elementLabel =  "" 
				+ "<input type=\"hidden\" name=\"formElement\" "
				+ "value='\"label\":\""+item.label
				+ "\",\"datatype\":\""+item.datatype
				+ "\",\"physicaldatatype\":\""+physicaldatatype
				+ "\",\"columnname\":\""+item.columnname
				+ "\",\"rowspan\":\""+rowspan
				+ "\",\"colspan\":\""+colspan
				+ "\",\"datasource\":\""+datasource
				+ "\",\"minvalue\":\""+minvalue
				+ "\",\"maxvalue\":\""+maxvalue
				+ "\",\"formatcode\":\""+formatcode
				+ "\",\"tablename\":\""+tablename
				+ "\",\"tablecode\":\""+tablecode
				+ "\",\"tablecolumnname\":\""+tablecolumnname
				+ "\",\"tablecolumncode\":\""+tablecolumncode
				+ "\",\"connectionmsg\":\""
				+ "\",\"transitiontype\":\""
				+ "\",\"screenactionid\":\""
				+ "\",\"linklabel\":\""
				+ "\",\"toscreenid\":\""
				+ "\",\"maxlength\":\""+maxlength
				+ "\",\"labelText\":\""+item.labelText
				+ "\",\"physicalmaxlength\":\""+physicalmaxlength
				+ "\",\"actiontype\":\""+item.actiontype
				+ "\",\"actionName\":\""+item.actionName
				+ "\",\"parameters\":\""+item.parameters
				+ "\",\"navigateTo\":\""+item.navigateTo
				+ "\",\"navigateToText\":\""+item.navigateToText
				+ "\",\"style\":\""+item.style
				+ "\",\"hoverStyle\":\""+item.hoverStyle
				+ "\",\"icon\":\""+item.icon
				+ "\",\"showLabel\":\""+item.showLabel
				+ "\",\"isSubmit\":\""+item.isSubmit
				+ "\",\"width\":\""+width
				+ "\",\"widthunit\":\""+widthunit
				+ "\",\"tabindex\":\""+item.tabindex
				+ "\",\"messageLevel\":\""+messageLevel
				+ "\",\"messageLevelText\":\""+messageLevelText
				
				+ "\",\"itemType\":\"5"
				;
				if(item.msgvalue != undefined){
					elementLabel += "\",\"msglabel\":\""+msglabel
					+ "\",\"msgvalue\":\""+item.msgvalue;
				} else {
					elementLabel += "\",\"msglabel\":\"\",\"msgvalue\":\"";
				}
				
				elementLabel += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
			elementTD += "<span style=\"width: "+width+""+widthunit+"; float:left \">"+mandatory+"<label style=\"cursor: pointer;"+ item.inputStyle + ' '+item.style+"; width: 100% \" ondblclick=\"openDialogLableSetting(this, true)\">"+unescape(item.labelText)+"</label>"+elementLabel+"</span>";
		}
		
		return elementTD;
	}
function returnElementTDSearchEntityPreview(item, displayGroupType){
	var dataType = item.datatype;
	var displayFromTo = item.displayFromToOutput;
	var areaCustomType = item.areaCustomType;
	var itemStyleDiv = "";
	var itemStyleInput = "";
	var arrStyle = item.style.split(";");
	for (var i = 0; i < arrStyle.length; i++) {
		if(arrStyle[i].indexOf("margin-") > -1){
			itemStyleDiv += arrStyle[i] + ";";
		} else {
			itemStyleInput += arrStyle[i] + ";";
		}
	}
	itemStyleDiv = itemStyleDiv.slice(0, -1);
	itemStyleInput = itemStyleInput.slice(0, -1);
		var elementTD = "";
		var tablecolumncode = "";
		if(item.tablecolumncode != undefined){
			tablecolumncode = item.tablecolumncode;
		}
		var tablename = "";
		if(item.tablename != undefined){
			tablename = item.tablename;
		}
		var tablecode = "";
		if(item.tablecode != undefined){
			tablecode = item.tablecode;
		}
		var tablecolumnname = "";
		if(item.tablecolumnname != undefined){
			tablecolumnname = item.tablecolumnname;
		}
		
		var physicaldatatype = "";
		if(item.physicaldatatype != undefined){
			physicaldatatype = item.physicaldatatype;
		}
		var minvalue = "";
		if(item.minvalue != undefined){
			minvalue = item.minvalue;
		}
		var maxvalue = "";
		if(item.maxvalue != undefined){
			maxvalue = item.maxvalue;
		}
		var formatcode = "";
		if(item.formatcode != undefined){
			formatcode = item.formatcode;
		}
		var physicalmaxlength = "";
		if(item.physicalmaxlength != undefined){
			physicalmaxlength = item.physicalmaxlength;
		}
		var maxlength = "";
		if(item.maxlength != undefined){
			maxlength = item.maxlength;
		}
		var require = "";
		if(item.require != undefined){
			require = item.require;
		}
		var rowspan = "";
		if(item.rowspan != undefined){
			rowspan = item.rowspan;
		}
		var colspan = "";
		if(item.colspan != undefined){
			colspan = item.colspan;
		}
		var datasource = "";
		if(item.datasource != undefined){
			datasource = item.datasource;
		}
		var msglabel = "";
		if(item.msglabel != undefined){
			msglabel = item.msglabel;
		}
		var labelText = "";
		if(item.labelText != undefined){
			labelText = item.labelText;
		}
		
		var screenItemId = "";
		if(item.screenItemId != undefined){
			screenItemId = item.screenItemId;
		}
		
		var screenDesignIdCodeListId = "";
		if(item.screenDesignIdCodeListId != undefined){
			screenDesignIdCodeListId = item.screenDesignIdCodeListId;
		}
		
		var screenItemIdCodeListId = "";
		if(item.screenItemIdCodeListId != undefined){
			screenItemIdCodeListId = item.screenItemIdCodeListId;
		}
		
		var screenDesignTextCodeListId = "";
		if(item.screenDesignTextCodeListId != undefined){
			screenDesignTextCodeListId = item.screenDesignTextCodeListId;
		}
		
		var screenItemTextCodeListId = "";
		if(item.screenItemTextCodeListId != undefined){
			screenItemTextCodeListId = item.screenItemTextCodeListId;
		}
		var messageLevel = "";
		var messageLevelText = "";
		if(item.messageLevel != "" && item.messageLevel != undefined) {
			messageLevel = item.messageLevel;
		}
		if(item.messageLevelText != "" && item.messageLevelText != undefined) {
			messageLevelText = item.messageLevelText;
		}
		
		var messageConfirm = "";
		if(item.messageConfirm != "" && item.messageConfirm != undefined) {
			messageConfirm = item.messageConfirm
		}
		
		var messageConfirmText = "";
		if(item.messageConfirmText != "" && item.messageConfirmText != undefined) {
			messageConfirmText = item.messageConfirmText;
		}
		
		var enableConfirm = "";
		if(item.enableConfirm != "" && item.enableConfirm != undefined) {
			enableConfirm = item.enableConfirm;
		}
		
		var events = "[]";
		
		if (item.events != undefined && item.events != null && item.events != 'null') {
			events = convertToString(item.events);
		}
		
		if (events == null || events == 'null') {
			events = "[]";
		}
		
		var element =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""+item.label
			+ "\",\"labelText\":\""+labelText
			+ "\",\"datatype\":\""+item.datatype
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+item.columnname
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\"" + item.parameters
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"baseType\":\""+item.baseType
			+ "\",\"codelistCode\":\""+item.codelistCode
			+ "\",\"codelistText\":\""+item.codelistText
			+ "\",\"isEnable\":\""+item.isEnable
			+ "\",\"localCodelist\":\""+item.localCodelist
			+ "\",\"isSupportOptionValue\":\""+item.isSupportOptionValue
			+ "\",\"dialogAutocompleteCode\":\""+item.dialogAutocompleteCode
			+ "\",\"dialogAutocompleteText\":\""+item.dialogAutocompleteText
			+ "\",\"dialogOnChangeEvent\":\""+item.dialogOnChangeEvent
			+ "\",\"dialogOnSelectEvent\":\""+item.dialogOnSelectEvent
			+ "\",\"mandatory\":\""+item.mandatory
			+ "\",\"width\":\""+item.width
			+ "\",\"widthunit\":\""+item.widthunit
			+ "\",\"outputBeanId\":\""+item.outputBeanId
			+ "\",\"tabindex\":\""+item.tabindex
			+ "\",\"events\":"+events
			+ ",\"physicalmaxlength\":\""+physicalmaxlength
			+ "\",\"datasourcetype\":\""+item.datasourcetype
			+ "\",\"sqldesignid\":\""+item.sqldesignid
			+ "\",\"sqldesignidtext\":\""+item.sqldesignidtext
			+ "\",\"optionlabel\":\""+item.optionlabel
			+ "\",\"optionlabeltext\":\""+item.optionlabeltext
			+ "\",\"optionvalue\":\""+item.optionvalue
			+ "\",\"optionvaluetext\":\""+item.optionvaluetext
			+ "\",\"screenItemId\":\""+item.screenItemId
			+ "\",\"style\":\""+item.style
			+ "\",\"hoverStyle\":\""+item.hoverStyle
			+ "\",\"icon\":\""+item.icon
			+ "\",\"showLabel\":\""+item.showLabel	
			+ "\",\"enablePassword\":\""+item.enablePassword
			+ "\",\"allowAnyInput\":\""+item.allowAnyInput
			+ "\",\"buttonStyle\":\""+item.buttonStyle
			+ "\",\"navigateToBlogic\":\""+item.navigateToBlogic
			+ "\",\"navigateToBlogicText\":\""+item.navigateToBlogicText
			+ "\",\"messageConfirm\":\""+messageConfirm
			+ "\",\"messageConfirmText\":\""+messageConfirmText
			+ "\",\"enableConfirm\":\""+enableConfirm
			+ "\",\"showBlankItem\":\""+item.showBlankItem
			+ "\",\"screenDesignIdCodeListId\":\""+screenDesignIdCodeListId
			+ "\",\"screenItemIdCodeListId\":\""+screenItemIdCodeListId
			+ "\",\"screenDesignTextCodeListId\":\""+screenDesignTextCodeListId
			+ "\",\"screenItemTextCodeListId\":\""+screenItemTextCodeListId
			+ "\",\"displayFromTo\":\""+item.displayFromToOutput
			
			+ "\",\"messageLevel\":\""+messageLevel
			+ "\",\"messageLevelText\":\""+messageLevelText
			
			+ "\",\"defaultvalue\":\""+item.defaultvalue;
		
		
			if(item.msgvalue != undefined){
				element += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+item.msgvalue;
			} else {
				element += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			element += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		
			var styleDisplayGroupType = "";
			if (displayGroupType != undefined && displayGroupType != null && displayGroupType == "2") {
				styleDisplayGroupType="clear: both;";
			}
			
			var styleWidth = "100%";
			
			if (item.width != null && item.width != "" && item.width.length > 0) {
				styleWidth = item.width +item.widthunit;
			}
		
			var style = 'style="width: '+styleWidth+'; float: left;'+styleDisplayGroupType+'"';
			var styleButton = 'style="float: left;'+styleDisplayGroupType+'"';
			var buttonWidth = "";
			if(item.width != undefined) {
				buttonWidth += 'width:'+item.width+'';
			}
			if(item.widthunit != undefined) {
				buttonWidth += item.widthunit;
			}
			
			if (item.groupitemtype == 1) {
				style = 'style="width: '+styleWidth+'; float: left; padding-right: 3px; padding-bottom: 3px;'+styleDisplayGroupType+'"';
			}
			
			var mandatory = "";
			if(item.mandatory == "true"){
				mandatory = "&nbsp;<label class=\"qp-required-field\">(*)</label>";
			}
			
			style1 = style.slice(0, -1);
		if(dataType == 0){
			elementTD += "<span "+style+">" +
					"<div ondblclick=\"openDialogAutocompleteSetting(this)\" class=\"input-group\" style=\"width:100%;" + itemStyleDiv + "\"><input style=\""+ item.inputStyle + ' '+itemStyleInput+"\" type=\"text\" ";
					if(item.elementtype == 0){
						elementTD += " class=\"form-control ui-autocomplete-input\" ";
					} else {
						elementTD += " class=\"qp-input-autocomplete\" ";
					}
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength
					+		" optionvalue=\"optionValue\" optionlabel=\"optionLabel\" selectsqlid=\"\" emptylabel=\"\" onselectevent=\"\" onchangeevent=\"\" onremoveevent=\"\" mustmatch=\"true\" minlength=\"0\" arg01=\"\" arg02=\"\" arg03=\"\" arg4=\"\" arg05=\"\" arg06=\"\" arg07=\"\" arg08=\"\" arg09=\"\" arg10=\"\" arg11=\"\" arg12=\"\" arg13=\"\" arg14=\"\" arg15=\"\" arg16=\"\" arg17=\"\" arg18=\"\" arg19=\"\" arg20=\"\" placeholder=\"\" autocomplete=\"off\">"
					+ "	<input type=\"hidden\" value=\"\">" 
					+ "	<span class=\"input-group-addon dropdown-toggle\" data-dropdown=\"dropdown\">" 
					+ "		<span class=\"caret\"></span> " 
					+ "	</span>"
					+ "</div>"+element+"</span>"
					"";		
		}
		if(dataType == 1 || dataType == 15 || dataType == 16 || dataType == 18){
			var dataTypeElement = "text";
			
			if (dataType == 1) {
				if (item.enablePassword != undefined && item.enablePassword != null && item.enablePassword == 1) {
					dataTypeElement = "password";
				}
			}
			
			elementTD += "<span "+style+"><input style=\""+ item.inputStyle + ' '+item.style+"\" ondblclick=\"openDialogComponentSetting(this)\" type=\""+dataTypeElement+"\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"form-control qp-input-text\"";
						} else {
							elementTD += "class=\"form-control qp-input-text-detail\"";
						}
						elementTD += " name=\"" 
						+ item.columnname + "1\" maxlength=\"" 
						+ item.maxlength + "\" />"+element+"</span>";
		}
		if(dataType == 21){
			var labelText = ((item.labelText != undefined && item.labelText != null && item.labelText.length > 0)?item.columnname:item.columnname);
			
			if (labelText == undefined || labelText == null || labelText.length == 0){
				labelText="Blank";
			}
			
			elementTD += "<span "+style+"><label style=\""+ item.inputStyle + ' '+item.style+"; width: 100%\" ondblclick=\"openDialogComponentSetting(this)\" ";					
						elementTD += " name=\"" 
						+ item.columnname + "1\" maxlength=\"" 
						+ item.maxlength + "\" >"+labelText+"</label>"+element+"</span>";
		}
		if(dataType == 2){
			if (displayFromTo == "0" && areaCustomType == "0") {
				elementTD += "<span "+style1+ ' ' + itemStyleDiv +  '"' +"><div ondblclick=\"openDialogComponentSetting(this)\" style=\"width: 100%;\">";
				elementTD += "<input style=\""+ item.inputStyle + ' ' + itemStyleInput + " qp-input-integer\" type=\"text\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"form-control qp-input-integer\"";
				} else {
					elementTD += "class=\"form-control qp-input-integer\"";
				}
				elementTD += " name=\"" 
				+ item.columnname + "1\" maxlength=\"" 
				+ item.maxlength + "\" />"+element+"</div></span>";
			} else {
				elementTD += "<span "+style1+ ' ' + itemStyleDiv + '"' +"><div  ondblclick=\"openDialogComponentSetting(this)\" style=\"width: 100%;\">" +
						"<input style=\""+ item.inputStyle + ' ' + itemStyleInput + " qp-input-integer\" type=\"text\" ";
							if(item.elementtype == 0){
								elementTD += "class=\"form-control qp-input-from pull-left qp-input-integer\"";
							} else {
								elementTD += "class=\"com-input-integer-detail\"";
							}
							elementTD += " name=\"" 
							+ item.columnname + "1\" maxlength=\"" 
							+ item.maxlength + "\" />" +
							"" + "<div class=\"qp-separator-from-to\">~</div>" +
							"<input style=\""+ item.inputStyle + ' ' + itemStyleInput + " qp-input-integer\" ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
							if(item.elementtype == 0){
								elementTD += "class=\"form-control qp-input-to pull-right qp-input-integer\"";
							} else {
								elementTD += "class=\"com-input-integer-detail\"";
							}
							elementTD += " name=\"" 
							+ item.columnname + "1\" maxlength=\"" 
							+ item.maxlength + "\" />" +
								
						""+element+"</div></span>";
			}
		}
		if(dataType == 3){
			if (displayFromTo == "0" && areaCustomType == "0") {
				elementTD += "<span "+style1+ ' ' + itemStyleDiv +  '"' +"><div ondblclick=\"openDialogComponentSetting(this)\" style=\"width: 100%;\">";
				elementTD += "<input style=\""+ item.inputStyle + ' ' + itemStyleInput + " qp-input-float\" type=\"text\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"form-control qp-input-float\"";
				} else {
					elementTD += "class=\"form-control qp-input-float\"";
				}
				elementTD += " name=\"" 
				+ item.columnname + "1\" maxlength=\"" 
				+ item.maxlength + "\" />"+element+"</div></span>";
			} else {
				elementTD += "<span "+style1+ ' ' + itemStyleDiv+"><div ondblclick=\"openDialogComponentSetting(this)\" style=\"width: 100%;\">" +
							"<input style=\""+ item.inputStyle + ' '+itemStyleInput+"\" type=\"text\" ";
							if(item.elementtype == 0){
								elementTD += "class=\"form-control qp-input-from pull-left qp-input-float\"";
							} else {
								elementTD += "class=\"com-input-float-detail\"";
							}
							elementTD += " name=\"" 
							+ item.columnname + "1\" maxlength=\"" 
							+ item.maxlength + "\" />" +
							"" + "<div class=\"qp-separator-from-to\">~</div>" +
							"<input style=\""+ item.inputStyle + ' '+itemStyleInput+"\" ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
							if(item.elementtype == 0){
								elementTD += "class=\"form-control qp-input-to pull-right qp-input-float\"";
							} else {
								elementTD += "class=\"com-input-float-detail\"";
							}
							elementTD += " name=\"" 
							+ item.columnname + "1\" maxlength=\"" 
							+ item.maxlength + "\" />" +
								
						""+element+"</div></span>";
			}
		}
		if(dataType == 8){
			if (displayFromTo == "0" && areaCustomType == "0") {
				elementTD += "<span "+style1+ ' ' + itemStyleDiv +  '"' +"><div ondblclick=\"openDialogComponentSetting(this)\"  style=\"width: 100%;\">";
				elementTD += "<input style=\""+ item.inputStyle + ' ' + itemStyleInput+ " qp-input-currency\" type=\"text\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"form-control qp-input-currency\"";
				} else {
					elementTD += "class=\"form-control qp-input-currency\"";
				}
				elementTD += " name=\"" 
				+ item.columnname + "1\" maxlength=\"" 
				+ item.maxlength + "\" />"+element+"</div></span>";
			} else {
				elementTD += "<span "+style1+ ' ' + itemStyleDiv +  '"' +"><div ondblclick=\"openDialogComponentSetting(this)\" style=\"width: 100%;\">" +
				"<input style=\""+ item.inputStyle + ' '+itemStyleInput+" qp-input-currency\" type=\"text\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"form-control qp-input-from pull-left qp-input-currency\"";
				} else {
					elementTD += "class=\"com-input-integer-detail\"";
				}
				elementTD += " name=\"" 
				+ item.columnname + "1\" maxlength=\"" 
				+ item.maxlength + "\" />" +
				"" + "<div class=\"qp-separator-from-to\">~</div>" +
				"<input style=\""+ item.inputStyle + ' '+itemStyleInput+" qp-input-currency\" ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
				if(item.elementtype == 0){
					elementTD += "class=\"form-control qp-input-to pull-right qp-input-currency\"";
				} else {
					elementTD += "class=\"com-input-integer-detail\"";
				}
				elementTD += " name=\"" 
				+ item.columnname + "1\" maxlength=\"" 
				+ item.maxlength + "\" />" +
					
			""+element+"</div></span>";
			}
		}
		if(dataType == 4){
			if (displayFromTo == "0" && areaCustomType == "0") {
				elementTD += "<span "+style1+ ' ' + itemStyleDiv +  '"' +"><div ondblclick=\"openDialogComponentSetting(this)\"  style=\"width: 100%;\">" +
				"<div class=\"input-group date\">"
				elementTD += "<input style=\""+ item.inputStyle + ' ' +itemStyleInput+"\" type=\"text\" ";
								if(item.elementtype == 0){
									elementTD += "class=\"form-control\"";
								} else {
									elementTD += "class=\"form-control \"";
								}
				elementTD += " name=\"" 
								+ item.columnname + "1\" maxlength=\"" 
								+ item.maxlength + "\" />";
				elementTD += "<span class=\"input-group-addon\">"
					+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
					+ "</span></div></div>"
				elementTD += element+"</span>";
			} else {
				elementTD += "<span "+style1+ ' ' + itemStyleDiv +  '"' +"><div ondblclick=\"openDialogComponentSetting(this)\"  style=\"width: 100%;\">" +
				"<div class=\"input-group date qp-input-from pull-left\">";
				elementTD += "<input style=\""+ item.inputStyle + ' ' +itemStyleInput+"\" type=\"text\" class=\"form-control qp-input-from\"";
				elementTD += " name=\"" 
								+ item.columnname + "1\" maxlength=\"" 
								+ item.maxlength + "\" />";
				elementTD += "<span class=\"input-group-addon\">"
								+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
								+ "</span>"
							+ "</div>"
				+ "<div class=\"qp-separator-from-to\">~</div>" +
				"<div class=\"input-group date qp-input-to pull-right\">";
				elementTD += "<input style=\""+ item.inputStyle + ' ' +itemStyleInput+"\" type=\"text\" ";
								if(item.elementtype == 0){
									elementTD += "class=\"form-control\"";
								} else {
									elementTD += "class=\"form-control \"";
								}
				elementTD += " name=\"" 
								+ item.columnname + "1\" maxlength=\"" 
								+ item.maxlength + "\" />";
				elementTD += "<span class=\"input-group-addon\">"
								+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
								+ "</span>"
							+ "</div>"
							;
				elementTD += "</div>"+element+"</span>";
			}
		}
		if(dataType == 14){
			if (displayFromTo == "0" && areaCustomType == "0") {
				elementTD += "<span "+style1+ ' ' + itemStyleDiv +  '"' +"><div ondblclick=\"openDialogComponentSetting(this)\"  style=\"width: 100%;\">" +
								"<div class=\"input-group date\">"
				elementTD += "<input style=\""+ item.inputStyle + ' ' +itemStyleInput+"\" type=\"text\" ";
								if(item.elementtype == 0){
									elementTD += "class=\"form-control\"";
								} else {
									elementTD += "class=\"form-control \"";
								}
				elementTD += " name=\"" 
								+ item.columnname + "1\" maxlength=\"" 
								+ item.maxlength + "\" />";
				elementTD += "<span class=\"input-group-addon\">"
					+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
					+ "</span></div></div>"
				elementTD += element+"</span>";
			} else {
				elementTD += "<span "+style1+ ' ' + itemStyleDiv +  '"' +"><div ondblclick=\"openDialogComponentSetting(this)\"  style=\"width: 100%;\">" +
						"<div class=\"input-group date qp-input-from pull-left\">";
				elementTD += "<input style=\""+ item.inputStyle + ' ' +itemStyleInput+"\" type=\"text\" ";
								if(item.elementtype == 0){
									elementTD += "class=\"form-control\"";
								} else {
									elementTD += "class=\"form-control \"";
								}
				elementTD += " name=\"" 
								+ item.columnname + "1\" maxlength=\"" 
								+ item.maxlength + "\" />";
				elementTD += "<span class=\"input-group-addon\">"
								+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
								+ "</span>"
							+ "</div>"
				+ "<div class=\"qp-separator-from-to\">~</div>" +
				"<div class=\"input-group date qp-input-to pull-right\">";
				elementTD += "<input style=\""+ item.inputStyle + ' ' +itemStyleInput+"\" type=\"text\" ";
								if(item.elementtype == 0){
									elementTD += "class=\"form-control\"";
								} else {
									elementTD += "class=\"form-control \"";
								}
				elementTD += " name=\"" 
								+ item.columnname + "1\" maxlength=\"" 
								+ item.maxlength + "\" />";
				elementTD += "<span class=\"input-group-addon\">"
								+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
								+ "</span>"
							+ "</div>"
							;
				elementTD += "</div>"+element+"</span>";
			}
		}
		if(dataType == 9){
			if (displayFromTo == "0" && areaCustomType == "0") {
				elementTD += "<span "+style1+ ' ' + itemStyleDiv +  '"' +"><div ondblclick=\"openDialogComponentSetting(this)\"  style=\"width: 100%;\">" +
						"<div class=\"input-group date\">"
				elementTD += "<input style=\""+ item.inputStyle + ' ' +itemStyleInput+"\" type=\"text\" ";
							if(item.elementtype == 0){
								elementTD += "class=\"form-control\"";
							} else {
								elementTD += "class=\"form-control\"";
							}
				elementTD += " name=\"" 
								+ item.columnname + "1\" maxlength=\"" 
								+ item.maxlength + "\" />";
				elementTD += "<span class=\"input-group-addon\">"
								+ "<span class=\"glyphicon glyphicon-time\"></span>"
								+ "</span>"
							+ "</div></div>";
				elementTD += element+"</span>";
			} else {
				elementTD += "<span "+style1+ ' ' + itemStyleDiv +  '"' +"><div ondblclick=\"openDialogComponentSetting(this)\"  style=\"width: 100%;\">" +
				"<div class=\"input-group date qp-input-from pull-left\">";
				elementTD += "<input style=\""+ item.inputStyle + ' ' +itemStyleInput+"\" type=\"text\" ";
								if(item.elementtype == 0){
									elementTD += "class=\"form-control\"";
								} else {
									elementTD += "class=\"form-control \"";
								}
				elementTD += " name=\"" 
								+ item.columnname + "1\" maxlength=\"" 
								+ item.maxlength + "\" />";
				elementTD += "<span class=\"input-group-addon\">"
								+ "<span class=\"glyphicon glyphicon-time\"></span>"
								+ "</span>"
							+ "</div>"
				+ "<div class=\"qp-separator-from-to\">~</div>" +
				"<div class=\"input-group date qp-input-to pull-right\">";
				elementTD += "<input style=\""+ item.inputStyle + ' ' +itemStyleInput+"\" type=\"text\" ";
								if(item.elementtype == 0){
									elementTD += "class=\"form-control\"";
								} else {
									elementTD += "class=\"form-control \"";
								}
				elementTD += " name=\"" 
								+ item.columnname + "1\" maxlength=\"" 
								+ item.maxlength + "\" />";
				elementTD += "<span class=\"input-group-addon\">"
								+ "<span class=\"glyphicon glyphicon-time\"></span>"
								+ "</span>"
							+ "</div>"
							;
				elementTD += "</div>"+element+"</span>";
			}
		}
		if(dataType == 5){
			if(item.msgvalue.length > 0){
				var msgLabelArr = item.msglabel.split("�");
				var msgValArr = item.msgvalue.split("�");
				elementTD += "<span "+style+" ondblclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\">";
				$(msgValArr).each(function(j){
					elementTD += "<input  type=\"radio\" value=\""+msgValArr[j]+"\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
					} else {
						elementTD += "class=\"com-input-radio-detail\"";
					}
					if(item.msglabel == undefined || item.msglabel.length == 0  || (msgLabelArr[j] == null || msgLabelArr[j] == 'null')){					
						elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">"+deEscapeHTMLForView(msgValArr[j])+"</label>";
					} else {					
						elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">"+deEscapeHTMLForView(msgLabelArr[j])+"</label>";
					}
				});
				elementTD += ""+element+"</span>";
			} else {
				elementTD += "<span "+style+" ondblclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\"><input type=\"radio\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
					} else {
						elementTD += "class=\"com-input-radio-detail\"";
					}
				elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
				elementTD += "<input type=\"radio\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
					} else {
						elementTD += "class=\"com-input-radio-detail\"";
					}
				elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>"+element+"</span>";
			}
		}
		if(dataType == 6){
			if(item.physicaldatatype != 8) {
				if(item.msgvalue.length > 0){
					var msgLabelArr = item.msglabel.split("�");
					var msgValArr = item.msgvalue.split("�");
					elementTD += "<span "+style+" ondblclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\">";
					$(msgValArr).each(function(j){
						elementTD += "<input type=\"checkbox\" value=\""+msgValArr[j]+"\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
						} else {
							elementTD += "class=\"com-input-checkbox-detail\"";
						}				
						if(item.msglabel == undefined || item.msglabel.length == 0  || (msgLabelArr[j] == null || msgLabelArr[j] == 'null')){					
							elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">"+deEscapeHTMLForView(msgValArr[j])+"</label>";
						} else {					
							elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">"+deEscapeHTMLForView(msgLabelArr[j])+"</label>";
						}				
					});
					elementTD += ""+element+"</span>";
				} else {
					elementTD += "<span "+style+" ondblclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\">";
					if (item.physicaldatatype == undefined || item.physicaldatatype == '' || item.physicaldatatype != 8) {
						elementTD += "<input type=\"checkbox\" ";
							if(item.elementtype == 0){
								elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
							} else {
								elementTD += "class=\"com-input-checkbox-detail\"";
							}
						elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
					}
					elementTD += "<input type=\"checkbox\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
						} else {
							elementTD += "class=\"com-input-checkbox-detail\"";
						}
					elementTD += " name=\"" + item.columnname + "1\" />";
					if (item.physicaldatatype == undefined || item.physicaldatatype == '' || item.physicaldatatype != 8) {
						elementTD += "<label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>";
					}
					elementTD += element+"</span>";
				}
			} else {
				if(item.msgvalue.length > 0) { 
					var msgLabelArr = item.msglabel.split("�");
					var msgValArr = item.msgvalue.split("�");
					elementTD += "<span "+style+" ondblclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\">";
					$(msgValArr).each(function(j){
						elementTD += "<input type=\"checkbox\" value=\""+msgValArr[j]+"\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
						} else {
							elementTD += "class=\"com-input-checkbox-detail\"";
						}				
						if(item.msglabel == undefined || item.msglabel.length == 0  || (msgLabelArr[j] == null || msgLabelArr[j] == 'null')){					
							elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">"+deEscapeHTMLForView(msgValArr[j])+"</label>";
						} else {					
							elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">"+deEscapeHTMLForView(msgLabelArr[j])+"</label>";
						}				
					});
					elementTD += ""+element+"</span>";
				} else {
					elementTD += "<span "+style+" ondblclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\">";
					if (item.physicaldatatype == undefined || item.physicaldatatype == '' || item.physicaldatatype != 8) {
						elementTD += "<input type=\"checkbox\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
						} else {
							elementTD += "class=\"com-input-checkbox-detail\"";
						}
						elementTD += " name=\"" + item.columnname + "1\" /><label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">true</label>";
					}
					elementTD += "<input type=\"checkbox\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
					} else {
						elementTD += "class=\"com-input-checkbox-detail\"";
					}
					elementTD += " name=\"" + item.columnname + "1\" />";
					/*elementTD += "<label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\">false</label>";*/
					elementTD += "<label style=\""+ item.inputStyle + ' '+item.style+"\" for=\""+item.columnname+"\"></label>";
					elementTD += element+"</span>";
				}
			}
		}
		if(dataType == 7){
			elementTD += "<span "+style+"><select style=\""+ item.inputStyle + ' '+item.style+"\" ondblclick=\"openDialogComponentListSetting(this)\" name=\""+item.columnname+"\" ";
			if(item.elementtype == 0){
				elementTD += "class=\"form-control qp-input-select\"";
			} else {
				elementTD += "class=\"form-control qp-input-select-detail\"";
			}
			elementTD += ">";
			if (item.showBlankItem == "1") {
				elementTD += "<option>" + "&nbsp;" + "</option>";
			}
			if(item.msgvalue.length > 0){
				var msgLabelArr = item.msglabel.split("�");
				var msgValArr = item.msgvalue.split("�");
				
				$(msgValArr).each(function(j){
					if(item.msglabel == undefined || item.msglabel.length == 0  || (msgLabelArr[j] == null || msgLabelArr[j] == 'null')){
						elementTD += "<option value=\""+msgValArr[j]+"\">" + deEscapeHTMLForView(msgValArr[j]) + "</option>";
					} else {
						elementTD += "<option value=\""+msgValArr[j]+"\">" + deEscapeHTMLForView(msgLabelArr[j]) + "</option>";
					}
				});
			} else {
				elementTD += "<option>"+dbMsgSource['sc.screendesign.0137']+"1</option><option>"+dbMsgSource['sc.screendesign.0137']+"2</option>";
			}
			elementTD += "</select>"+element+"</span>";
		}
		if(dataType == 10){
			elementTD += "<span "+style+"><textarea style=\""+ item.inputStyle + ' '+item.style+"\" ondblclick=\"openDialogComponentSetting(this)\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"form-control qp-input-textarea\"";
						} else {
							elementTD += "class=\"form-control qp-input-textarea-detail\"";
						}
						elementTD += " name=\"" 
						+ item.columnname + "1\" maxlength=\"" 
						+ item.maxlength + "\" ></textarea>"+element+"</span>";
		}
		if(dataType == 11){	
			
			var elementButton =  "" 
				+ "<input type=\"hidden\" name=\"formElement\" "
				+ "value='\"label\":\""+item.label
				+ "\",\"datatype\":\""+item.datatype
				+ "\",\"physicaldatatype\":\""+physicaldatatype
				+ "\",\"columnname\":\""+item.columnname
				+ "\",\"rowspan\":\""+rowspan
				+ "\",\"colspan\":\""+colspan
				+ "\",\"datasource\":\""+datasource
				+ "\",\"minvalue\":\""+minvalue
				+ "\",\"maxvalue\":\""+maxvalue
				+ "\",\"formatcode\":\""+formatcode
				+ "\",\"tablename\":\""+tablename
				+ "\",\"tablecode\":\""+tablecode
				+ "\",\"tablecolumnname\":\""+tablecolumnname
				+ "\",\"tablecolumncode\":\""+tablecolumncode
				+ "\",\"connectionmsg\":\""
				+ "\",\"transitiontype\":\""
				+ "\",\"screenactionid\":\""
				+ "\",\"linklabel\":\""
				+ "\",\"toscreenid\":\""
				+ "\",\"maxlength\":\""+maxlength
				+ "\",\"labelText\":\""+item.labelText
				+ "\",\"physicalmaxlength\":\""+physicalmaxlength
				+ "\",\"actiontype\":\""+item.actiontype
				+ "\",\"actionName\":\""+item.actionName
				+ "\",\"parameters\":\""+item.parameters
				+ "\",\"navigateTo\":\""+item.navigateTo
				+ "\",\"navigateToText\":\""+item.navigateToText
				+ "\",\"navigateToBlogic\":\""+item.navigateToBlogic
				+ "\",\"navigateToBlogicText\":\""+item.navigateToBlogicText
				+ "\",\"style\":\""+item.style
				+ "\",\"hoverStyle\":\""+item.hoverStyle
				+ "\",\"icon\":\""+item.icon
				+ "\",\"showLabel\":\""+item.showLabel	
				+ "\",\"screenTransition\":\""+item.screenTransition
				+ "\",\"screenTransitionText\":\""+item.screenTransitionText
				+ "\",\"messageLevel\":\""+messageLevel
				+ "\",\"messageLevelText\":\""+messageLevelText
				+ "\",\"messageConfirm\":\""+messageConfirm
				+ "\",\"messageConfirmText\":\""+messageConfirmText
				+ "\",\"enableConfirm\":\""+enableConfirm
				+ "\",\"tabindex\":\""+item.tabindex
				+ "\",\"isSubmit\":\""+item.isSubmit
				;
			
			if(item.msgvalue != undefined){
				elementButton += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+item.msgvalue;
			} else {
				elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<span "+style+"><a style=\""+ item.inputStyle + ' '+item.style+"\" onmouseover=\"this.setAttribute('style', '"+item.hoverStyle+"')\" onmouseout=\"this.setAttribute('style', '"+item.style+"')\" ondblclick=\"openDialogLinkAreaSetting(this);\" href=\"javascript:\">"+item.labelText+"</a>"+elementButton+"</span>";
		}
		var width = ""
		if(item.width != undefined) {
			width = item.width;
		} else {
			width = "100";
		}
		if(dataType == 22){
			var localCodelist = "";
			var datasourcetype = "";
			if(item.localCodelist != undefined) {
				localCodelist = item.localCodelist;
			}
			if(item.datasourcetype != undefined) {
				datasourcetype = item.datasourcetype;
			}
			
			var elementButton =  "" 
				+ "<input type=\"hidden\" name=\"formElement\" "
				+ "value='\"label\":\""+item.label
				+ "\",\"datatype\":\""+item.datatype
				+ "\",\"physicaldatatype\":\""+physicaldatatype
				+ "\",\"columnname\":\""+item.columnname
				+ "\",\"rowspan\":\""+rowspan
				+ "\",\"colspan\":\""+colspan
				+ "\",\"datasource\":\""+datasource
				+ "\",\"minvalue\":\""+minvalue
				+ "\",\"maxvalue\":\""+maxvalue
				+ "\",\"formatcode\":\""+formatcode
				+ "\",\"tablename\":\""+tablename
				+ "\",\"tablecode\":\""+tablecode
				+ "\",\"tablecolumnname\":\""+tablecolumnname
				+ "\",\"tablecolumncode\":\""+tablecolumncode
				+ "\",\"connectionmsg\":\""
				+ "\",\"transitiontype\":\""
				+ "\",\"screenactionid\":\""
				+ "\",\"linklabel\":\""
				+ "\",\"toscreenid\":\""
				+ "\",\"maxlength\":\""+maxlength
				+ "\",\"labelText\":\""+item.labelText
				+ "\",\"physicalmaxlength\":\""+physicalmaxlength
				+ "\",\"actiontype\":\""+item.actiontype
				+ "\",\"actionName\":\""+item.actionName
				+ "\",\"parameters\":\""+item.parameters
				+ "\",\"navigateTo\":\""+item.navigateTo
				+ "\",\"navigateToText\":\""+item.navigateToText
				+ "\",\"navigateToBlogic\":\""+item.navigateToBlogic
				+ "\",\"navigateToBlogicText\":\""+item.navigateToBlogicText
				+ "\",\"style\":\""+item.style
				+ "\",\"hoverStyle\":\""+item.hoverStyle
				+ "\",\"messageConfirm\":\""+item.messageConfirm
				+ "\",\"messageConfirmText\":\""+item.messageConfirmText
				+ "\",\"enableConfirm\":\""+item.enableConfirm
				+ "\",\"messageConfirmCode\":\""+item.messageConfirmCode
				+ "\",\"screenItemId\":\""+item.screenItemId
				+ "\",\"width\":\""+width
				+ "\",\"widthunit\":\""+item.widthunit
				+ "\",\"isSubmit\":\""+item.isSubmit
				+ "\",\"screenDesignIdCodeListId\":\""+screenDesignIdCodeListId
				+ "\",\"screenItemIdCodeListId\":\""+screenItemIdCodeListId
				+ "\",\"screenDesignTextCodeListId\":\""+screenDesignTextCodeListId
				+ "\",\"screenItemTextCodeListId\":\""+screenItemTextCodeListId
				+ "\",\"codelistCode\":\""+item.codelistCode
				+ "\",\"codelistText\":\""+item.codelistText
				+ "\",\"localCodelist\":\""+localCodelist
				+ "\",\"tabindex\":\""+item.tabindex
				+ "\",\"screenTransition\":\""+item.screenTransition
				+ "\",\"screenTransitionText\":\""+item.screenTransitionText
				+ "\",\"datasourcetype\":\""+datasourcetype
				
				;
			
			
				if(item.msgvalue != undefined){
					elementButton += "\",\"msglabel\":\""+msglabel
					+ "\",\"msgvalue\":\""+item.msgvalue;
				} else {
					elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
				}
				
				elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
			elementTD += "<span "+style+"><a style=\""+ item.inputStyle + ' ' +item.style+"\" onmouseover=\"this.setAttribute('style', '"+item.hoverStyle+"')\" onmouseout=\"this.setAttribute('style', '"+item.style+"')\" ondblclick=\"openDialogLinkAreaSetting(this);\" href=\"javascript:\">"+item.columnname+"</a>"+elementButton+"</span>";
		}
		
		if(dataType == 23){
			var elementLabel =  "" 
				+ "<input type=\"hidden\" name=\"formElement\" "
				+ "value='\"label\":\""
				+ "\",\"datatype\":\""+item.datatype
				+ "\",\"physicaldatatype\":\""+physicaldatatype
				+ "\",\"columnname\":\""+item.columnname
				+ "\",\"rowspan\":\""+rowspan
				+ "\",\"colspan\":\""+colspan
				+ "\",\"datasource\":\""+datasource
				+ "\",\"minvalue\":\""+minvalue
				+ "\",\"maxvalue\":\""+maxvalue
				+ "\",\"formatcode\":\""+formatcode
				+ "\",\"tablename\":\""+tablename
				+ "\",\"tablecode\":\""+tablecode
				+ "\",\"tablecolumnname\":\""+tablecolumnname
				+ "\",\"tablecolumncode\":\""+tablecolumncode
				+ "\",\"connectionmsg\":\""
				+ "\",\"transitiontype\":\""
				+ "\",\"actiontype\":\""
				+ "\",\"parameters\":\""
				+ "\",\"screenactionid\":\""
				+ "\",\"linklabel\":\""
				+ "\",\"toscreenid\":\""
				+ "\",\"maxlength\":\""+maxlength
				+ "\",\"labelText\":\""+item.labelText
				+ "\",\"isBundle\":\""+ "false"
				+ "\",\"isSubmit\":\""+ "false"
				+ "\",\"navigateTo\":\""
				+ "\",\"navigateToText\":\""
				+ "\",\"customItemContent\":\"" + item.customItemContent
				+ "\",\"itemType\":\"5"
				+ "\",\"screenItemId\":\""+ screenItemId
				+ "\",\"screenTransition\":\""+item.screenTransition
				+ "\",\"screenTransitionText\":\""+item.screenTransitionText
				+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
			
			
			if(item.msgvalue != undefined){
				elementLabel += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+item.msgvalue;
			} else {
				elementLabel += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementLabel += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
			elementTD += "<span "+style+"><label class=\"qp-item-custom\" ondblclick=\"openDialogCustomSetting(this, true)\" style=\"cursor: pointer;\">"+item.labelText+"</label>"+elementLabel+"</span>";
		}	
		
		if(dataType == 12){
			elementTD += "<span "+style+"><input onclick=\"event.stopPropagation();return false;\" ondblclick=\"event.stopPropagation();openDialogComponentSetting(this);return false;\" type=\"file\" name=\"" + item.columnname + "1\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"qp-input-file\"";
						} else {
							elementTD += "class=\"com-input-file-detail\"";
						}
			elementTD += "/>"+element+"</span>";
		}
		/*var width = ""
		if(item.width != undefined) {
			width = item.width;
		} else {
			width = "100";
		}*/
		if(dataType == 13){
			var elementButton =  "" 
				+ "<input type=\"hidden\" name=\"formElement\" "
				+ "value='\"label\":\""+item.label
				+ "\",\"datatype\":\""+item.datatype
				+ "\",\"physicaldatatype\":\""+physicaldatatype
				+ "\",\"columnname\":\""+item.columnname
				+ "\",\"rowspan\":\""+rowspan
				+ "\",\"colspan\":\""+colspan
				+ "\",\"datasource\":\""+datasource
				+ "\",\"minvalue\":\""+minvalue
				+ "\",\"maxvalue\":\""+maxvalue
				+ "\",\"formatcode\":\""+formatcode
				+ "\",\"tablename\":\""+tablename
				+ "\",\"tablecode\":\""+tablecode
				+ "\",\"tablecolumnname\":\""+tablecolumnname
				+ "\",\"tablecolumncode\":\""+tablecolumncode
				+ "\",\"connectionmsg\":\""
				+ "\",\"transitiontype\":\""
				+ "\",\"screenactionid\":\""
				+ "\",\"linklabel\":\""
				+ "\",\"toscreenid\":\""
				+ "\",\"maxlength\":\""+maxlength
				+ "\",\"labelText\":\""+item.labelText
				+ "\",\"physicalmaxlength\":\""+physicalmaxlength
				+ "\",\"actiontype\":\""+item.actiontype
				+ "\",\"actionName\":\""+item.actionName
				+ "\",\"parameters\":\""+item.parameters
				+ "\",\"navigateTo\":\""+item.navigateTo
				+ "\",\"navigateToText\":\""+item.navigateToText
				+ "\",\"style\":\""+item.style
				+ "\",\"hoverStyle\":\""+item.hoverStyle
				+ "\",\"icon\":\""+item.icon
				+ "\",\"showLabel\":\""+item.showLabel	
				+ "\",\"buttonStyle\":\""+item.buttonStyle
				+ "\",\"navigateToBlogic\":\""+item.navigateToBlogic
				+ "\",\"navigateToBlogicText\":\""+item.navigateToBlogicText
				+ "\",\"width\":\""+item.width
				+ "\",\"widthunit\":\""+item.widthunit
				+ "\",\"screenTransition\":\""+item.screenTransition
				+ "\",\"screenTransitionText\":\""+item.screenTransitionText
				
				+ "\",\"messageConfirm\":\""+messageConfirm
				+ "\",\"messageConfirmText\":\""+messageConfirmText
				+ "\",\"enableConfirm\":\""+enableConfirm
				+ "\",\"tabindex\":\""+item.tabindex
				+ "\",\"messageLevel\":\""+messageLevel
				+ "\",\"messageLevelText\":\""+messageLevelText
				
				+ "\",\"isSubmit\":\""+item.isSubmit
				;
			
			
			if(item.msgvalue != undefined){
				elementButton += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+item.msgvalue;
			} else {
				elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
			
			var buttonStyle = "";
			if (item.buttonStyle != undefined) {
				if (item.buttonStyle == 0 || item.buttonStyle == 1) {
					buttonStyle = "qp-button-type-design";
				} else if (item.buttonStyle == 2) {
					buttonStyle = "qp-button-type-warning-design";
				} else if (item.buttonStyle == 3) {
					buttonStyle = "qp-button-type-client-design";
				}
			}
			
			if (item.width != '' && item.width != undefined) {
				var newButtonStyle = styleButton.slice(0, -1) + " width: " + item.width + '' + item.widthunit + ";" + '"';
				styleButton =  newButtonStyle;
			}
			if (item.icon != undefined && item.showLabel != undefined && item.icon.length > 0) {
				var buttonContent = "<i class=\""+item.icon+"\"></i>";
				if ((item.showLabel == '1' || item.showLabel == 1) && item.labelText != undefined && item.labelText != null && item.labelText.length > 0) {
					buttonContent += "&nbsp;&nbsp;" + item.labelText;
				}
				elementTD += "<span "+styleButton+"><button style=\""+ item.inputStyle + ' '+item.style+";width:100%\" type=\"button\" class=\"btn qp-button "+buttonStyle+"\" ondblclick=\"openDialogButtonAreaSetting(this)\" value=\""+item.labelText+"\">"+buttonContent+"</button>"+elementButton+"</span>";
			} else {
				elementTD += "<span "+styleButton+"><button style=\""+ item.inputStyle + ' '+item.style+";width:100%\" type=\"button\" class=\"btn qp-button "+buttonStyle+"\" ondblclick=\"openDialogButtonAreaSetting(this)\" value=\""+item.labelText+"\">"+item.labelText+"</button>"+elementButton+"</span>";
			}
		}
		if(dataType == 20){
			if (item.labelText == undefined || item.labelText == null) {
				item["labelText"] = dbMsgSource['sc.screendesign.0050'];
			}
			var width = "";
			if (item.width != undefined && item.width != null) {
				width = item.width;
			}
			var widthunit = "";
			if (item.widthunit != undefined && item.widthunit != null) {
				widthunit = item.widthunit;
			}
			
			var elementLabel =  "" 
				+ "<input type=\"hidden\" name=\"formElement\" "
				+ "value='\"label\":\""+item.label
				+ "\",\"datatype\":\""+item.datatype
				+ "\",\"physicaldatatype\":\""+physicaldatatype
				+ "\",\"columnname\":\""+item.columnname
				+ "\",\"rowspan\":\""+rowspan
				+ "\",\"colspan\":\""+colspan
				+ "\",\"datasource\":\""+datasource
				+ "\",\"minvalue\":\""+minvalue
				+ "\",\"maxvalue\":\""+maxvalue
				+ "\",\"formatcode\":\""+formatcode
				+ "\",\"tablename\":\""+tablename
				+ "\",\"tablecode\":\""+tablecode
				+ "\",\"tablecolumnname\":\""+tablecolumnname
				+ "\",\"tablecolumncode\":\""+tablecolumncode
				+ "\",\"connectionmsg\":\""
				+ "\",\"transitiontype\":\""
				+ "\",\"screenactionid\":\""
				+ "\",\"linklabel\":\""
				+ "\",\"toscreenid\":\""
				+ "\",\"maxlength\":\""+maxlength
				+ "\",\"labelText\":\""+item.labelText
				+ "\",\"physicalmaxlength\":\""+physicalmaxlength
				+ "\",\"actiontype\":\""+item.actiontype
				+ "\",\"actionName\":\""+item.actionName
				+ "\",\"parameters\":\""+item.parameters
				+ "\",\"navigateTo\":\""+item.navigateTo
				+ "\",\"navigateToText\":\""+item.navigateToText
				+ "\",\"style\":\""+item.style
				+ "\",\"hoverStyle\":\""+item.hoverStyle
				+ "\",\"icon\":\""+item.icon
				+ "\",\"showLabel\":\""+item.showLabel
				+ "\",\"isSubmit\":\""+item.isSubmit
				+ "\",\"width\":\""+width
				+ "\",\"widthunit\":\""+widthunit
				+ "\",\"tabindex\":\""+item.tabindex
				+ "\",\"messageLevel\":\""+messageLevel
				+ "\",\"messageLevelText\":\""+messageLevelText
				
				+ "\",\"itemType\":\"5"
				;
				if(item.msgvalue != undefined){
					elementLabel += "\",\"msglabel\":\""+msglabel
					+ "\",\"msgvalue\":\""+item.msgvalue;
				} else {
					elementLabel += "\",\"msglabel\":\"\",\"msgvalue\":\"";
				}
				
				elementLabel += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
			elementTD += "<span style=\"width: "+width+""+widthunit+"; float:left \">"+mandatory+"<label style=\"cursor: pointer;"+ item.inputStyle + ' '+item.style+"; width: 100% \" ondblclick=\"openDialogLableSetting(this, true)\">"+unescape(item.labelText)+"</label>"+elementLabel+"</span>";
		}
		
		return elementTD;
	}
function returnElementOptionTD(element, isValue, physicalDataType){
	var elementTD = "";
	if(element.datatype == 5){
		if(element.msgvalue.length > 0){
			var msgLabelArr = element.msglabel.split("�");
			var msgValArr = element.msgvalue.split("�");
			$(msgValArr).each(function(j){
				elementTD += "<input type=\"radio\" ";
				if(element.elementtype == 0){
					elementTD += "class=\"com-input-radio\"";
				} else {
					elementTD += "class=\"com-input-radio-detail\"";
				}
				if(!isValue){
					if(physicalDataType == 7){
						elementTD += " name=\"" + element.columnname + "\" />" + msgLabelArr[j] + " ";
					} else {
						elementTD += " name=\"" + element.columnname + "\" />" + msgValArr[j] + " ";
					}
				} else {
					elementTD += " name=\"" + element.columnname + "\" />" + msgLabelArr[j] + " ";
				}
			});
		} else {
			elementTD += "<input type=\"radio\" ";
						if(element.elementtype == 0){
							elementTD += "class=\"com-input-radio\"";
						} else {
							elementTD += "class=\"com-input-radio-detail\"";
						}
			elementTD += " name=\"" + element.columnname + "\" />"+dbMsgSource['sc.screendesign.0137']+"1  ";
			elementTD += "<input type=\"radio\" ";
						if(element.elementtype == 0){
							elementTD += "class=\"com-input-radio\"";
						} else {
							elementTD += "class=\"com-input-radio-detail\"";
						}
			elementTD += " name=\"" + element.columnname + "\" />"+dbMsgSource['sc.screendesign.0137']+"2";
		}
	}
	if(element.datatype == 6){
		if(element.msgvalue.length > 0){
			var msgLabelArr = element.msglabel.split("�");
			var msgValArr = element.msgvalue.split("�");
			$(msgValArr).each(function(j){
				elementTD += "<input type=\"checkbox\" ";
				if(element.elementtype == 0){
					elementTD += "class=\"com-input-checkbox\"";
				} else {
					elementTD += "class=\"com-input-checkbox-detail\"";
				}
				if(!isValue){
					if(physicalDataType == 7){
						elementTD += " name=\"" + element.columnname + "\" />" + msgLabelArr[j] + " ";
					} else {
						elementTD += " name=\"" + element.columnname + "\" />" + msgValArr[j] + " ";
					}
				} else {
					elementTD += " name=\"" + element.columnname + "\" />" + msgLabelArr[j] + " ";
				}
			});
		} else {
			elementTD += "<input type=\"checkbox\" ";
						if(element.elementtype == 0){
							elementTD += "class=\"com-input-checkbox\"";
						} else {
							elementTD += "class=\"com-input-checkbox-detail\"";
						}
			elementTD += " name=\"" + element.columnname + "\" />"+dbMsgSource['sc.screendesign.0137']+"1  ";
			elementTD += "<input type=\"checkbox\" ";
						if(element.elementtype == 0){
							elementTD += "class=\"com-input-checkbox\"";
						} else {
							elementTD += "class=\"com-input-checkbox-detail\"";
						}
			elementTD += " name=\"" + element.columnname + "\" />"+dbMsgSource['sc.screendesign.0137']+"2";
		}
	}
	if(element.datatype == 7){
		elementTD += "<select name=\""+element.columnname+"\" ";
		if(element.elementtype == 0){
			elementTD += "class=\"form-control qp-input-select\"";
		} else {
			elementTD += "class=\"form-control qp-input-select-detail\"";
		}
		elementTD += ">";
		if(element.msgvalue.length > 0){
			var msgLabelArr = element.msglabel.split("�");
			var msgValArr = element.msgvalue.split("�");
			
			$(msgValArr).each(function(j){
				if(!isValue){
					if(physicalDataType == 7){
						elementTD += "<option>" + msgLabelArr[j] + "</option>";
					} else {
						elementTD += "<option>" + msgValArr[j] + "</option>";
					}
				} else {
					elementTD += "<option>" + msgLabelArr[j] + "</option>";
				}
			});
		} else {
			elementTD += "<option>"+dbMsgSource['sc.screendesign.0137']+"1</option><option>"+dbMsgSource['sc.screendesign.0137']+"2</option>";
		}
		elementTD += "</select>";
	}
	return elementTD;
}

function insertButton(ev, ui, obj){
	var button = "<input type=\"button\" class=\"btn qp-button\" value=\""+dbMsgSource['sc.screendesign.0128']+"\" onclick=\"openDialogButtonSetting(this)\"></input>";
	var hidden = "<input type=\"hidden\" value='\"navigateto\":\"\",\"msgcode\":\""+dbMsgSource['sc.screendesign.0128']+"\",\"toscreenid\":\"\",\"transitiontype\":\"1\",\"connectionmsg\":\""+dbMsgSource['sc.screendesign.0130']+"\",\"actiontype\":\"0\",\"parameters\":\"\"' name=\"formElement\"></input>";
	$(obj).prepend(hidden);
	$(obj).prepend(button);
}

function insertAnyElement(ev, ui, obj){
	var div = $(ui.draggable);
	var dataType = $(div).attr('datatype');
		
	var elementTD = "";
	var tablecolumncode = "";
	if($(div).attr('tablecolumncode') != undefined){
		tablecolumncode = $(div).attr('tablecolumncode');
	}
	var tablename = "";
	if($(div).attr('tablename') != undefined){
		tablename = $(div).attr('tablename');
	}
	var tablecode = "";
	if($(div).attr('tablecode') != undefined){
		tablecode = $(div).attr('tablecode');
	}
	var tablecolumnname = "";
	if($(div).attr('tablecolumnname') != undefined){
		tablecolumnname = $(div).attr('tablecolumnname');
	}
	
	var physicaldatatype = "";
	if($(div).attr('physicaldatatype') != undefined){
		physicaldatatype = $(div).attr('physicaldatatype');
	}
	var minvalue = "";
	if($(div).attr('minvalue') != undefined){
		minvalue = $(div).attr('minvalue');
	}
	var maxvalue = "";
	if($(div).attr('maxvalue') != undefined){
		maxvalue = $(div).attr('maxvalue');
	}
	var formatcode = "";
	if($(div).attr('formatcode') != undefined){
		formatcode = $(div).attr('formatcode');
	}
	var physicalmaxlength = "";
	if($(div).attr('physicalmaxlength') != undefined){
		physicalmaxlength = $(div).attr('physicalmaxlength');
	}
	var maxlength = "";
	if($(div).attr('maxlength') != undefined){
		maxlength = $(div).attr('maxlength');
	}
	var require = "";
	if($(div).attr('require') != undefined){
		require = $(div).attr('require');
	}
	var rowspan = "";
	if($(div).attr('rowspan') != undefined){
		rowspan = $(div).attr('rowspan');
	}
	var colspan = "";
	if($(div).attr('colspan') != undefined){
		colspan = $(div).attr('colspan');
	}
	var datasource = "";
	if($(div).attr('datasource') != undefined){
		datasource = $(div).attr('datasource');
	}
	
	var msglabel = "";
	if($(div).attr('msglabel') != undefined){
		msglabel = $(div).attr('msglabel');
	}
	
	var labelText = "";
	if($(div).attr('labelText') != undefined){
		labelText = $(div).attr('labelText');
	}
	
	var tablewidth = "";
	if($(div).attr('width') != undefined){
		tablewidth = $(div).attr('width');
	}
	
	var tableindex = "";
	if($(div).attr('tabindex') != undefined){
		tableindex = $(div).attr('tabindex');
	}
	
	var codelistCode = "";
	if($(div).attr('codelistCode') != undefined){
		codelistCode = $(div).attr('codelistCode');
	}
	
	var codelistText = "";
	if($(div).attr('codelistText') != undefined){
		codelistText = $(div).attr('codelistText');
	}
	var localCodelist = "";
	if($(div).attr('localCodelist') != undefined){
		localCodelist = $(div).attr('localCodelist');
	}
	var defaultvalue = "";
	if($(div).attr('defaultvalue') != undefined){
		defaultvalue = $(div).attr('defaultvalue');
	}
	
	var parameters = "";
	if($(div).attr('parameters') != undefined){
		parameters = $(div).attr('parameters');
	}
	var labelText = "";
	if($(div).attr('labelText') != undefined){
		labelText = $(div).attr('labelText');
	}
	var localCodelist = "";
	if($(div).attr('localCodelist') != undefined){
		localCodelist = $(div).attr('localCodelist');
	}
	var msgvalue = "";
	if($(div).attr('msgvalue') != undefined) {
		msgvalue = $(div).attr('msglabel');
	}
	
	var labelTextStore = $(div).attr("columnname");
	var area = $(obj).closest(".areaContent");
	var areaType = $(obj).closest(".areaContent").find("input[name=formAreaType]").val();
	//default lable text, case is user drap column
	if ($(div).attr("data") != undefined && $(div).attr("data") != null && $(div).attr("data").length > 0) {
		var data = convertToJson($(div).attr("data"));
		if (data != undefined && data != null && data.labelText != undefined) {
			$(div).attr("labelText", data.labelText);
		}
	} else {
		if ($(div).attr("datatype") != 11 && $(div).attr("datatype") != 13) {
			if (areaType == 3) {
				var textLabel =  $(div).attr("columnname");
				if ($(area).find("input[name=formElement]").length > 0) {
					var max = 0;
					var isExists = false;
					
					$(area).find("input[name=formElement]").each(function() {
						var elementJson = convertToJson($(this).val());
						var itemCode =elementJson.columnname; 
						
						if (itemCode != undefined && itemCode != null && itemCode.indexOf(textLabel) != -1) {
							isExists = true;
							var order = itemCode.replace(textLabel, "");
							try {
								var orderItem = parseInt(order); 
								if (max < orderItem)
								{
									max = orderItem;
								}
							}catch(err) {
								
							}
							
						}
					});
					
					var labelItem = labelTextStore;
					if (max == 0) {
						if (isExists) {
							max++;
							textLabel = textLabel + max;
							labelItem = labelItem + max;
						}
					} else {
						max++;
						textLabel = textLabel + max;
						labelItem = labelItem + max;
					}
					
					$(div).attr("labelText", labelItem);
					$(div).attr("columnname", textLabel);
				} else {
					
				}
				
			}
		}
	}
	
	var datasourcetype = "";
	var parameters = "";
	if ($(div).attr('parameters') == undefined || $(div).attr('parameters') == null || $(div).attr('parameters').length == 0) {
		if (dataType == 5 || dataType == 6 || dataType == 7) {
			parameters = "option1π1�option2π2�";
			$(div).attr('msgvalue',"1�2");
			msglabel = "option1�option2";
			localCodelist = 3;
			datasourcetype = 2;
		} else {
			datasourcetype = checkUndefined($(div).attr('datasourcetype'));
		}
	} else {
		parameters = $(div).attr('parameters');
	}
	
	var element =  "" 
		+ "<input type=\"hidden\" name=\"formElement\" "
		+ "value='\"label\":\""+$(div).attr('label')
		+ "\",\"datatype\":\""+$(div).attr('datatype')
		+ "\",\"parameters\":\"" + parameters
		+ "\",\"physicaldatatype\":\""+physicaldatatype
		+ "\",\"localCodelist\":\""+localCodelist
		+ "\",\"datasourcetype\":\""+datasourcetype
	
	if (dataType == 20) {
		element += "\",\"columnname\":\""+"";
		element += "\",\"labelText\":\""+$(div).attr('label');
	} else if (dataType == 11) {
		element += "\",\"columnname\":\""+"";
		element += "\",\"labelText\":\""+dbMsgSource['sc.screendesign.0039'];
	} else if (dataType == 13) {
		element += "\",\"columnname\":\""+"";
		element += "\",\"labelText\":\""+dbMsgSource['sc.screendesign.0128'];
	} else {
		element += "\",\"columnname\":\""+$(div).attr('columnname');
		
		if ($(div).attr("labelText") != undefined && $(div).attr("labelText") != null && $(div).attr("labelText") != ''
			&& $(div).attr("isBlank") != undefined && $(div).attr("isBlank") != null && $(div).attr("isBlank") != ''
			&& $(div).attr("isBundle") != undefined && $(div).attr("isBundle") != null && $(div).attr("isBundle") != '') {
			element += "\",\"labelText\":\""+$(div).attr("columnname");
			element += "\",\"isBlank\":\""+"false";
			element += "\",\"isBundle\":\""+"false";
		}
		
	}
	
	element +=  ""
		+ "\",\"rowspan\":\""+rowspan
		+ "\",\"labelText\":\""+$(div).attr('label')
		+ "\",\"colspan\":\""+colspan
		+ "\",\"datasource\":\""+datasource
		+ "\",\"minvalue\":\""+minvalue
		+ "\",\"maxvalue\":\""+maxvalue
		+ "\",\"formatcode\":\""+formatcode
		+ "\",\"tablename\":\""+tablename
		+ "\",\"tablecode\":\""+tablecode
		+ "\",\"tablecolumnname\":\""+tablecolumnname
		+ "\",\"tablecolumncode\":\""+tablecolumncode
		+ "\",\"connectionmsg\":\""
		+ "\",\"transitiontype\":\""
		+ "\",\"actiontype\":\""
		+ "\",\"screenactionid\":\""
		+ "\",\"linklabel\":\""
		+ "\",\"toscreenid\":\""
		+ "\",\"width\":\""+"25"
		+ "\",\"widthunit\":\""+"%"
		+ "\",\"maxlength\":\""+maxlength
		+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
	if(dataType == 20){
		element += "\",\"isBundle\":\""+"false"
		+ "\",\"isBlank\":\""+"true"
		+ "\",\"labelText\":\""+$(div).attr('label') ;
	}
		
	if($(div).attr('msgvalue') != undefined){
		element += "\",\"msglabel\":\""+msglabel
		+ "\",\"msgvalue\":\""+msgvalue;
	} else {
		element += "\",\"msglabel\":\"\",\"msgvalue\":\"";
	}
	
	element += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		
	if(dataType == 0){
		elementTD += "" +
				"<div ondblclick=\"openDialogAutocompleteSettingSection(this)\" class=\"input-group\" style=\"width:100%\"><input type=\"text\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += " class=\"form-control ui-autocomplete-input\" ";
				} else {
					elementTD += " class=\"qp-input-autocomplete\" ";
				}
				elementTD += " name=\"" 
				+ $(div).attr('columnname') + "\" maxlength=\"" 
				+ $(div).attr('maxlength')
				+		" optionvalue=\"optionValue\" optionlabel=\"optionLabel\" selectsqlid=\"\" emptylabel=\"\" onselectevent=\"\" onchangeevent=\"\" onremoveevent=\"\" mustmatch=\"true\" minlength=\"0\" arg01=\"\" arg02=\"\" arg03=\"\" arg4=\"\" arg05=\"\" arg06=\"\" arg07=\"\" arg08=\"\" arg09=\"\" arg10=\"\" arg11=\"\" arg12=\"\" arg13=\"\" arg14=\"\" arg15=\"\" arg16=\"\" arg17=\"\" arg18=\"\" arg19=\"\" arg20=\"\" placeholder=\"\" autocomplete=\"off\">"
				+ "	<input type=\"hidden\" value=\"\">" 
				+ "	<span class=\"input-group-addon dropdown-toggle\" data-dropdown=\"dropdown\">" 
				+ "		<span class=\"caret\"></span> " 
				+ "	</span>"
				+ "</div>"+element;		
	}
	if(dataType == 1 || dataType == 15 || dataType == 16 || dataType == 18){
		elementTD += "<input ondblclick=\"openDialogComponentSettingSection(this)\" type=\"text\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"form-control qp-input-text\"";
					} else {
						elementTD += "class=\"form-control qp-input-text-detail\"";
					}
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" />"+element;
	}
	if(dataType == 21){
		elementTD += "<span style=\"width: 100%;\"><Label ondblclick=\"openDialogComponentSettingSection(this)\" ";
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" >"+$(div).attr('columnname')+"</label>"+element+"</span>";
	}
	if(dataType == 2){
		elementTD += "<input  ondblclick=\"openDialogComponentSettingSection(this)\" type=\"text\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"form-control \"";
					} else {
						elementTD += "class=\"com-input-integer-detail\"";
					}
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" />"+element;
	}
	if(dataType == 3){
		elementTD += "<input  ondblclick=\"openDialogComponentSettingSection(this)\" type=\"text\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"form-control\"";
					} else {
						elementTD += "class=\"com-input-float-detail\"";
					}
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" />"+element;
	}
	if(dataType == 8){
		elementTD += "<input ondblclick=\"openDialogComponentSettingSection(this)\" type=\"text\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"form-control \"";
					} else {
						elementTD += "class=\"com-input-currency-detail\"";
					}
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" />"+element+"";
	}
	if(dataType == 4){
		elementTD += "<span><div class=\"input-group date\">"
		elementTD += "<input ondblclick=\"openDialogComponentSettingSection(this)\" type=\"text\" ";
						if($(div).attr('elementtype') == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control\"";
						}
		elementTD += " name=\"" 
							+ $(div).attr('columnname') + "\" maxlength=\"" 
							+ $(div).attr('maxlength') + "\" />"+element+"</span>";
		elementTD += "<span class=\"input-group-addon\">"
							+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
							+ "</span>"
						+ "</div></span>";
	}
	if(dataType == 14){
		
		elementTD += "<span><div class=\"input-group date\">"
		elementTD += "<input ondblclick=\"openDialogComponentSettingSection(this)\" type=\"text\" ";
						if($(div).attr('elementtype') == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control \"";
						}
		elementTD += " name=\"" 
						+ $(div).attr('columnname') + "\" maxlength=\"" 
						+ $(div).attr('maxlength') + "\" />"+element+"</span>";
		elementTD += "<span class=\"input-group-addon\">"
						+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
						+ "</span>"
					+ "</div></span>";
	}
	if(dataType == 9){
		
		elementTD += "<span><div class=\"input-group date\">"
			elementTD += "<input ondblclick=\"openDialogComponentSettingSection(this)\" type=\"text\" ";
						if($(div).attr('elementtype') == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"com-input-timepicker-detail\"";
						}
			elementTD += " name=\"" 
							+ $(div).attr('columnname') + "\" maxlength=\"" 
							+ $(div).attr('maxlength') + "\" />"+element+"</span>";
			elementTD += "<span class=\"input-group-addon\">"
							+ "<span class=\"glyphicon glyphicon-time\"></span>"
							+ "</span>"
						+ "</div></span>";			
	}
	if(dataType == 5){
		if($(div).attr('msgvalue') != undefined && $(div).attr('msgvalue').length > 0){
			var msgLabelArr = $(div).attr('msglabel').split("�");
			var msgValArr = $(div).attr('msgvalue').split("�");
			$(msgValArr).each(function(j){
				elementTD += "<input  type=\"radio\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"com-input-radio-detail\"";
				}
				if($(div).attr('msglabel') != undefined && $(div).attr('msglabel').length == 0){					
					elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+msgValArr[j]+"</label>";
				} else {					
					elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+msgLabelArr[j]+"</label>";
				}
			});
		} else {
			elementTD += "<input  type=\"radio\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"com-input-radio-detail\"";
				}
			elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
			elementTD += "<input  type=\"radio\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"com-input-radio-detail\"";
				}
			elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>";
		}
	}
	if(dataType == 6){
		if($(div).attr('msgvalue') != undefined && $(div).attr('msgvalue').length > 0){
			var msgLabelArr = $(div).attr('msglabel').split("�");
			var msgValArr = $(div).attr('msgvalue').split("�");
			$(msgValArr).each(function(j){
				elementTD += "<input  type=\"checkbox\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				} else {
					elementTD += "class=\"com-input-checkbox-detail\"";
				}				
				if($(div).attr('msglabel') != undefined && $(div).attr('msglabel').length == 0){					
					elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+msgValArr[j]+"</label>";
				} else {					
					elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+msgLabelArr[j]+"</label>";
				}				
			});
		} else {
			elementTD += "<input type=\"checkbox\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				} else {
					elementTD += "class=\"com-input-checkbox-detail\"";
				}
			elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
			elementTD += "<input type=\"checkbox\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				} else {
					elementTD += "class=\"com-input-checkbox-detail\"";
				}
			elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>";
		}
	}
	if(dataType == 7){
		elementTD += "<select ondblclick=\"openDialogComponentListSettingSection(this)\" name=\""+$(div).attr('columnname')+"\" ";
		if($(div).attr('elementtype') == 0){
			elementTD += "class=\"form-control qp-input-select\"";
		} else {
			elementTD += "class=\"form-control qp-input-select-detail\"";
		}
		elementTD += ">";
		if($(div).attr('msgvalue') != undefined && $(div).attr('msgvalue').length > 0){
			var msgLabelArr = $(div).attr('msglabel').split("�");
			var msgValArr = $(div).attr('msgvalue').split("�");
			
			$(msgValArr).each(function(j){
				if($(div).attr('msglabel') != undefined && $(div).attr('msglabel').length == 0){
					elementTD += "<option>" + msgValArr[j] + "</option>";
				} else {
					elementTD += "<option>" + msgLabelArr[j] + "</option>";
				}
			});
		} else {
			elementTD += "<option>"+dbMsgSource['sc.screendesign.0137']+"1</option><option>"+dbMsgSource['sc.screendesign.0137']+"2</option>";
		}
		elementTD += "</select>"+element;
	}
	if(dataType == 10){
		elementTD += "<textarea ondblclick=\"openDialogComponentSettingSection(this)\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"form-control qp-input-textarea\"";
					} else {
						elementTD += "class=\"form-control qp-input-textarea-detail\"";
					}
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "1\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" ></textarea>"+element;
	}
	if(dataType == 11){	
		var elementButton =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""
			+ "\",\"datatype\":\""+$(div).attr('datatype')
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+$(div).attr('columnname')
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"isBundle\":\""+ "false"
			+ "\",\"width\":\""+"25"
			+ "\",\"widthunit\":\""+"%"
			+ "\",\"labelText\":\""+$(div).attr("labeltext")
			+ "\",\"navigateTo\":\""
			+ "\",\"navigateToText\":\""
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
		if($(div).attr('msgvalue') != undefined){
			elementButton += "\",\"msglabel\":\""+msglabel
			+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
		} else {
			elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
		}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<a ondblclick=\"openDialogLinkAreaSettingSection(this);\" href=\"javascript:\">"+$(div).attr('labelText')+"</a>"+elementButton;
	}
	var width = ""
	if($(div).attr("tablewidth") != undefined) {
		width = $(div).attr("tablewidth");
	} else {
		width = "25";
	}
	if(dataType == 22){
		var elementButton =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""
			+ "\",\"datatype\":\""+$(div).attr('datatype')
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+$(div).attr('columnname')
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"width\":\""+width
			+ "\",\"widthunit\":\""+checkUndefined($(div).attr('widthunit'))
			+ "\",\"labelText\":\""+$(div).attr("labeltext")
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
			if($(div).attr('msgvalue') != undefined){
				elementButton += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+msgvalue;
			} else {
				elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<a ondblclick=\"openDialogLinkAreaSettingSection(this);\" href=\"javascript:\">"+$(div).attr('labelText')+"</a>"+elementButton+"";
	}
	
	if(dataType == 23){
		var elementLabel =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""
			+ "\",\"datatype\":\""+$(div).attr('datatype')
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+$(div).attr('columnname')
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+$(div).attr("labelText")
			+ "\",\"isBundle\":\""+ "false"
			+ "\",\"isSubmit\":\""+ "false"
			+ "\",\"navigateTo\":\""
			+ "\",\"navigateToText\":\""
			+ "\",\"customItemContent\":\"" + $(div).attr('customItemContent')
			+ "\",\"itemType\":\"5"
			+ "\",\"screenItemId\":\""+$(div).attr("screenItemId")
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
		if($(div).attr('msgvalue') != undefined){
			elementLabel += "\",\"msglabel\":\""+msglabel
			+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
		} else {
			elementLabel += "\",\"msglabel\":\"\",\"msgvalue\":\"";
		}
		
		elementLabel += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<span><label class=\"qp-item-custom\" ondblclick=\"openDialogCustomSetting(this, true)\" style=\"cursor: pointer;\">"+$(div).attr('labelText')+"</label>"+elementLabel+"</span>";
	}	
	
	if(dataType == 12){
		elementTD += "<input onclick=\"event.stopPropagation();return false;\" ondblclick=\"event.stopPropagation();openDialogComponentSettingSection(this);return false;\" type=\"file\" name=\"" + $(div).attr('columnname') + "\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"qp-input-file\"";
					} else {
						elementTD += "class=\"com-input-file-detail\"";
					}
		elementTD += "/>"+element;
	}
	if(dataType == 13){
		var elementButton =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""
			+ "\",\"datatype\":\""+$(div).attr('datatype')
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+$(div).attr('columnname')
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"isBundle\":\""+ "false"
			+ "\",\"isSubmit\":\""+ "false"
			+ "\",\"width\":\""
			+ "\",\"widthunit\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+$(div).attr("labeltext")
			+ "\",\"navigateTo\":\""
			+ "\",\"navigateToText\":\""
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
		if($(div).attr('msgvalue') != undefined){
			elementButton += "\",\"msglabel\":\""+msglabel
			+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
		} else {
			elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
		}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<button style=\"width:100%\" type=\"button\" class=\"btn qp-button\" ondblclick=\"openDialogButtonAreaSettingSection(this)\" value=\""+$(div).attr('labelText')+"\">"+$(div).attr('labelText')+"</button>"+elementButton;
	}
	if(dataType == 20){
		elementTD += "<label ondblclick=\"openDialogLableSettingSection(this)\" style=\"cursor: pointer;\">"+$(div).attr('label')+"</label>"+element;
	}	
	
	var styleButton = "padding: 2px;";
	var style = "width: 25%; padding: 2px; ";
	var direction = $(obj).closest(".areaContent").find("input[name=formDirection]").val();
	var displayType = $(obj).closest(".areaContent").find("input[name=formDisplayType]").val();
	
	if (displayType == 1) {
		style += "clear: both;";
		styleButton += "clear: both;";
	} else {
		
	}
	if (direction == 0) {
		style += "float: left;";
		styleButton += "float: left;";
	} else {		
		style += "float: right;";
		styleButton += "float: right;";
	}
	// daipv: comment for style of [height] is 30px
//	if (dataType != 10) {
//		style += "height: 30px;";
//		styleButton += "height: 30px;";
//	}
	
	if (dataType == 5 || dataType == 6) {
		$(obj).append("<span ondblclick=\"this.checked = !this.checked;openDialogComponentListSettingSection(this)\" style=\""+style+"\">" + elementTD + element + "</span>");
	} else if(dataType == 13) {
		$(obj).append("<span style=\""+styleButton+"\">" + elementTD + "</span>");
	} else {
		$(obj).append("<span style=\""+style+"\">" + elementTD + "</span>");
	}
		
	$.qp.initialAllPicker($(obj).closest(".section-area"));		
	initBlurEventElement();
}

function insertActionElement(ev, ui, obj){
	var div = $(ui.draggable);
	var dataType = $(div).attr('datatype');
		
	var elementTD = "";
	var tablecolumncode = "";
	if($(div).attr('tablecolumncode') != undefined){
		tablecolumncode = $(div).attr('tablecolumncode');
	}
	var tablename = "";
	if($(div).attr('tablename') != undefined){
		tablename = $(div).attr('tablename');
	}
	var tablecode = "";
	if($(div).attr('tablecode') != undefined){
		tablecode = $(div).attr('tablecode');
	}
	var tablecolumnname = "";
	if($(div).attr('tablecolumnname') != undefined){
		tablecolumnname = $(div).attr('tablecolumnname');
	}
	
	var physicaldatatype = "";
	if($(div).attr('physicaldatatype') != undefined){
		physicaldatatype = $(div).attr('physicaldatatype');
	}
	var minvalue = "";
	if($(div).attr('minvalue') != undefined){
		minvalue = $(div).attr('minvalue');
	}
	var maxvalue = "";
	if($(div).attr('maxvalue') != undefined){
		maxvalue = $(div).attr('maxvalue');
	}
	var formatcode = "";
	if($(div).attr('formatcode') != undefined){
		formatcode = $(div).attr('formatcode');
	}
	var physicalmaxlength = "";
	if($(div).attr('physicalmaxlength') != undefined){
		physicalmaxlength = $(div).attr('physicalmaxlength');
	}
	var maxlength = "";
	if($(div).attr('maxlength') != undefined){
		maxlength = $(div).attr('maxlength');
	}
	var require = "";
	if($(div).attr('require') != undefined){
		require = $(div).attr('require');
	}
	var rowspan = "";
	if($(div).attr('rowspan') != undefined){
		rowspan = $(div).attr('rowspan');
	}
	var colspan = "";
	if($(div).attr('colspan') != undefined){
		colspan = $(div).attr('colspan');
	}
	var datasource = "";
	if($(div).attr('datasource') != undefined){
		datasource = $(div).attr('datasource');
	}
	var msglabel = "";
	if($(div).attr('msglabel') != undefined){
		msglabel = $(div).attr('msglabel');
	}
	
	var labelTextStore = $(div).attr("columnname");
	var area = $(obj).closest(".areaContent");
	var areaType = $(obj).closest(".areaContent").find("input[name=formAreaType]").val();
	//default lable text, case is user drap column
	if ($(div).attr("data") != undefined && $(div).attr("data") != null && $(div).attr("data").length > 0) {
		var data = convertToJson($(div).attr("data"));
		if (data != undefined && data != null && data.labelText != undefined) {
			$(div).attr("labelText", data.labelText);
		}
	} else {
		if ($(div).attr("datatype") != 11 && $(div).attr("datatype") != 13) {
			if (areaType == 3 || areaType == 2) {
				var textLabel =  $(div).attr("columnname");
				if ($(area).find("input[name=formElement]").length > 0) {
					var max = 0;
					var isExists = false;
					
					$(area).find("input[name=formElement]").each(function() {
						var elementJson = convertToJson($(this).val());
						var itemCode =elementJson.columnname; 
						
						if (itemCode != undefined && itemCode != null && itemCode.indexOf(textLabel) != -1) {
							isExists = true;
							var order = itemCode.replace(textLabel, "");
							try {
								var orderItem = parseInt(order); 
								if (max < orderItem)
								{
									max = orderItem;
								}
							}catch(err) {
								
							}
							
						}
					});
					
					var labelItem = labelTextStore;
					if (max == 0) {
						if (isExists) {
							max++;
							textLabel = textLabel + max;
							labelItem = labelItem + max;
						}
					} else {
						max++;
						textLabel = textLabel + max;
						labelItem = labelItem + max;
					}
					
					$(div).attr("labelText", labelItem);
					$(div).attr("columnname", textLabel);
				} else {
					
				}
				
			}
		}
	}
	
	var element =  "" 
		+ "<input type=\"hidden\" name=\"formElement\" "
		+ "value='\"label\":\""+$(div).attr('label')
		+ "\",\"datatype\":\""+$(div).attr('datatype')
		+ "\",\"physicaldatatype\":\""+physicaldatatype
		+ "\",\"columnname\":\""+$(div).attr('columnname')
		+ "\",\"rowspan\":\""+rowspan
		+ "\",\"colspan\":\""+colspan
		+ "\",\"datasource\":\""+datasource
		+ "\",\"minvalue\":\""+minvalue
		+ "\",\"maxvalue\":\""+maxvalue
		+ "\",\"formatcode\":\""+formatcode
		+ "\",\"tablename\":\""+tablename
		+ "\",\"tablecode\":\""+tablecode
		+ "\",\"tablecolumnname\":\""+tablecolumnname
		+ "\",\"tablecolumncode\":\""+tablecolumncode
		+ "\",\"connectionmsg\":\""
		+ "\",\"transitiontype\":\""
		+ "\",\"actiontype\":\""
		+ "\",\"parameters\":\""
		+ "\",\"screenactionid\":\""
		+ "\",\"linklabel\":\""
		+ "\",\"toscreenid\":\""
		+ "\",\"maxlength\":\""+maxlength
		+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
	if(dataType == 20){
		element += "\",\"isBundle\":\""+"false"
		+ "\",\"isBlank\":\""+"true"
		+ "\",\"labelText\":\""+div.attr("label");
	}
	if($(div).attr('msgvalue') != undefined){
		element += "\",\"msglabel\":\""+msglabel
		+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
	} else {
		element += "\",\"msglabel\":\"\",\"msgvalue\":\"";
	}
	
	element += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		
	if(dataType == 0){
		elementTD += "" +
				"<div ondblclick=\"openDialogAutocompleteSettingSection(this)\" class=\"input-group\" style=\"width:100%\"><input type=\"text\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += " class=\"form-control ui-autocomplete-input\" ";
				} else {
					elementTD += " class=\"qp-input-autocomplete\" ";
				}
				elementTD += " name=\"" 
				+ $(div).attr('columnname') + "\" maxlength=\"" 
				+ $(div).attr('maxlength')
				+		" optionvalue=\"optionValue\" optionlabel=\"optionLabel\" selectsqlid=\"\" emptylabel=\"\" onselectevent=\"\" onchangeevent=\"\" onremoveevent=\"\" mustmatch=\"true\" minlength=\"0\" arg01=\"\" arg02=\"\" arg03=\"\" arg4=\"\" arg05=\"\" arg06=\"\" arg07=\"\" arg08=\"\" arg09=\"\" arg10=\"\" arg11=\"\" arg12=\"\" arg13=\"\" arg14=\"\" arg15=\"\" arg16=\"\" arg17=\"\" arg18=\"\" arg19=\"\" arg20=\"\" placeholder=\"\" autocomplete=\"off\">"
				+ "	<input type=\"hidden\" value=\"\">" 
				+ "	<span class=\"input-group-addon dropdown-toggle\" data-dropdown=\"dropdown\">" 
				+ "		<span class=\"caret\"></span> " 
				+ "	</span>"
				+ "</div>"+element;		
	}
	if(dataType == 1 || dataType == 15 || dataType == 16 || dataType == 18){
		elementTD += "<input ondblclick=\"openDialogComponentSettingSection(this)\" type=\"text\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"form-control qp-input-text\"";
					} else {
						elementTD += "class=\"form-control qp-input-text-detail\"";
					}
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" />"+element;
	}
	if(dataType == 21){
		elementTD += "<span style=\"width: 100%;\"><Label ondblclick=\"openDialogComponentSettingSection(this)\" ";					
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" >"+$(div).attr('columnname')+"</label>"+element+"</span>";
	}
	if(dataType == 2){
		elementTD += "<input  ondblclick=\"openDialogComponentSettingSection(this)\" type=\"text\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"form-control \"";
					} else {
						elementTD += "class=\"com-input-integer-detail\"";
					}
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" />"+element;
	}
	if(dataType == 3){
		elementTD += "<input  ondblclick=\"openDialogComponentSettingSection(this)\" type=\"text\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"form-control\"";
					} else {
						elementTD += "class=\"com-input-float-detail\"";
					}
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" />"+element;
	}
	if(dataType == 8){
		elementTD += "<input ondblclick=\"openDialogComponentSettingSection(this)\" type=\"text\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"form-control \"";
					} else {
						elementTD += "class=\"com-input-currency-detail\"";
					}
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" />"+element+"";
	}
	if(dataType == 4){
		elementTD += "<div class=\"input-group date\">"
		elementTD += "<input ondblclick=\"openDialogComponentSettingSection(this)\" type=\"text\" ";
						if($(div).attr('elementtype') == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control\"";
						}
		elementTD += " name=\"" 
							+ $(div).attr('columnname') + "\" maxlength=\"" 
							+ $(div).attr('maxlength') + "\" />"+element+"</span>";
		elementTD += "<span class=\"input-group-addon\">"
							+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
							+ "</span>"
						+ "</div>";
	}
	if(dataType == 14){
		
		elementTD += "<span><div class=\"input-group date\">"
		elementTD += "<input ondblclick=\"openDialogComponentSettingSection(this)\" type=\"text\" ";
						if($(div).attr('elementtype') == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"form-control \"";
						}
		elementTD += " name=\"" 
						+ $(div).attr('columnname') + "\" maxlength=\"" 
						+ $(div).attr('maxlength') + "\" />"+element+"</span>";
		elementTD += "<span class=\"input-group-addon\">"
						+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
						+ "</span>"
					+ "</div></span>";
	}
	if(dataType == 9){
		
		elementTD += "<span><div class=\"input-group date\">"
			elementTD += "<input ondblclick=\"openDialogComponentSettingSection(this)\" type=\"text\" ";
						if($(div).attr('elementtype') == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"com-input-timepicker-detail\"";
						}
			elementTD += " name=\"" 
							+ $(div).attr('columnname') + "\" maxlength=\"" 
							+ $(div).attr('maxlength') + "\" />"+element+"</span>";
			elementTD += "<span class=\"input-group-addon\">"
							+ "<span class=\"glyphicon glyphicon-time\"></span>"
							+ "</span>"
						+ "</div></span>";			
	}
	if(dataType == 5){
		if($(div).attr('msgvalue') != undefined && $(div).attr('msgvalue').length > 0){
			var msgLabelArr = $(div).attr('msglabel').split("�");
			var msgValArr = $(div).attr('msgvalue').split("�");
			elementTD += "<span ondblclick=\"this.checked = !this.checked;openDialogComponentListSettingSection(this)\">";
			$(msgValArr).each(function(j){
				elementTD += "<input  type=\"radio\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"com-input-radio-detail\"";
				}
				if($(div).attr('msglabel') != undefined && $(div).attr('msglabel').length == 0){					
					elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+msgValArr[j]+"</label>";
				} else {					
					elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+msgLabelArr[j]+"</label>";
				}
			});
			elementTD += ""+element+"</span>";
		} else {
			elementTD += "<span ondblclick=\"this.checked = !this.checked;openDialogComponentListSettingSection(this)\">";
			elementTD += "<input type=\"radio\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"com-input-radio-detail\"";
				}
			elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
			elementTD += "<input type=\"radio\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
				} else {
					elementTD += "class=\"com-input-radio-detail\"";
				}
			elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>";
			elementTD += ""+element+"</span>";
		}
	}
	if(dataType == 6){
		if($(div).attr('msgvalue') != undefined && $(div).attr('msgvalue').length > 0){
			var msgLabelArr = $(div).attr('msglabel').split("�");
			var msgValArr = $(div).attr('msgvalue').split("�");
			elementTD += "<span ondblclick=\"this.checked = !this.checked;openDialogComponentListSettingSection(this)\">";
			$(msgValArr).each(function(j){
				elementTD += "<input ondblclick=\"this.checked = !this.checked;openDialogComponentListSettingSection(this)\" type=\"checkbox\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				} else {
					elementTD += "class=\"com-input-checkbox-detail\"";
				}				
				if($(div).attr('msglabel') != undefined && $(div).attr('msglabel').length == 0){					
					elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+msgValArr[j]+"</label>";
				} else {					
					elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+msgLabelArr[j]+"</label>";
				}				
			});
			elementTD += ""+element+"</span>";
		} else {
			elementTD += "<span ondblclick=\"this.checked = !this.checked;openDialogComponentListSettingSection(this)\">";
			elementTD += "<input type=\"checkbox\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				} else {
					elementTD += "class=\"com-input-checkbox-detail\"";
				}
			elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
			elementTD += "<input type=\"checkbox\" ";
				if($(div).attr('elementtype') == 0){
					elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
				} else {
					elementTD += "class=\"com-input-checkbox-detail\"";
				}
			elementTD += " name=\"" + $(div).attr('columnname') + "\" /><label for=\""+$(div).attr('columnname')+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>";
			elementTD += ""+element+"</span>";
		}
	}
	if(dataType == 7){
		elementTD += "<select ondblclick=\"openDialogComponentListSettingSection(this)\" name=\""+$(div).attr('columnname')+"\" ";
		if($(div).attr('elementtype') == 0){
			elementTD += "class=\"form-control qp-input-select\"";
		} else {
			elementTD += "class=\"form-control qp-input-select-detail\"";
		}
		elementTD += ">";
		if($(div).attr('msgvalue') != undefined && $(div).attr('msgvalue').length > 0){
			var msgLabelArr = $(div).attr('msglabel').split("�");
			var msgValArr = $(div).attr('msgvalue').split("�");
			
			$(msgValArr).each(function(j){
				if($(div).attr('msglabel') != undefined && $(div).attr('msglabel').length == 0){
					elementTD += "<option>" + msgValArr[j] + "</option>";
				} else {
					elementTD += "<option>" + msgLabelArr[j] + "</option>";
				}
			});
		} else {
			elementTD += "<option>"+dbMsgSource['sc.screendesign.0137']+"1</option><option>"+dbMsgSource['sc.screendesign.0137']+"2</option>";
		}
		elementTD += "</select>"+element;
	}
	if(dataType == 10){
		elementTD += "<textarea ondblclick=\"openDialogComponentSettingSection(this)\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"form-control qp-input-textarea\"";
					} else {
						elementTD += "class=\"form-control qp-input-textarea-detail\"";
					}
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "1\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" ></textarea>"+element;
	}
	if(dataType == 11){		
		var elementButton =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""
			+ "\",\"datatype\":\""+$(div).attr('datatype')
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+$(div).attr('columnname')
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"isBundle\":\""+ "false"
			+ "\",\"labelText\":\""+$(div).attr("labeltext")
			+ "\",\"navigateTo\":\""
			+ "\",\"navigateToText\":\""
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
			if($(div).attr('msgvalue') != undefined){
				elementButton += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
			} else {
				elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<a ondblclick=\"openDialogLinkAreaSettingSection(this);\" href=\"javascript:\">"+$(div).attr('labelText')+"</a>"+elementButton;
	}
	
	if(dataType == 22){
		var elementButton =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""
			+ "\",\"datatype\":\""+$(div).attr('datatype')
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+$(div).attr('columnname')
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+$(div).attr("labeltext")
			+ "\",\"width\":\""+
			+ "\",\"widthunit\":\""+checkUndefined($(div).attr('widthunit'))
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
			if($(div).attr('msgvalue') != undefined){
				elementButton += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
			} else {
				elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<a ondblclick=\"openDialogLinkAreaSettingSection(this);\" href=\"javascript:\">"+$(div).attr('labelText')+"</a>"+elementButton+"";
	}
	
	if(dataType == 23){
		var elementLabel =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""
			+ "\",\"datatype\":\""+$(div).attr('datatype')
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+$(div).attr('columnname')
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"labelText\":\""+$(div).attr("labelText")
			+ "\",\"isBundle\":\""+ "false"
			+ "\",\"isSubmit\":\""+ "false"
			+ "\",\"navigateTo\":\""
			+ "\",\"navigateToText\":\""
			+ "\",\"customItemContent\":\""
			+ "\",\"itemType\":\"5"
			+ "\",\"screenItemId\":\""+$(div).attr("screenItemId")
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
		if($(div).attr('msgvalue') != undefined){
			elementLabel += "\",\"msglabel\":\""+msglabel
			+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
		} else {
			elementLabel += "\",\"msglabel\":\"\",\"msgvalue\":\"";
		}
		
		elementLabel += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<span><label class=\"qp-item-custom\" ondblclick=\"openDialogCustomSetting(this, true)\" style=\"cursor: pointer;\">"+$(div).attr('labelText')+"</label>"+elementLabel+"</span>";
	}	
	
	if(dataType == 12){
		elementTD += "<input onclick=\"event.stopPropagation();return false;\" ondblclick=\"event.stopPropagation();openDialogComponentSettingSection(this);return false;\" type=\"file\" name=\"" + $(div).attr('columnname') + "\" ";
					if($(div).attr('elementtype') == 0){
						elementTD += "class=\"qp-input-file\"";
					} else {
						elementTD += "class=\"com-input-file-detail\"";
					}
		elementTD += "/>"+element;
	}
	if(dataType == 13){
		var elementButton =  "" 
			+ "<input type=\"hidden\" name=\"formElement\" "
			+ "value='\"label\":\""
			+ "\",\"datatype\":\""+$(div).attr('datatype')
			+ "\",\"physicaldatatype\":\""+physicaldatatype
			+ "\",\"columnname\":\""+$(div).attr('columnname')
			+ "\",\"rowspan\":\""+rowspan
			+ "\",\"colspan\":\""+colspan
			+ "\",\"datasource\":\""+datasource
			+ "\",\"minvalue\":\""+minvalue
			+ "\",\"maxvalue\":\""+maxvalue
			+ "\",\"formatcode\":\""+formatcode
			+ "\",\"tablename\":\""+tablename
			+ "\",\"tablecode\":\""+tablecode
			+ "\",\"tablecolumnname\":\""+tablecolumnname
			+ "\",\"tablecolumncode\":\""+tablecolumncode
			+ "\",\"connectionmsg\":\""
			+ "\",\"transitiontype\":\""
			+ "\",\"actiontype\":\""
			+ "\",\"parameters\":\""
			+ "\",\"screenactionid\":\""
			+ "\",\"linklabel\":\""
			+ "\",\"toscreenid\":\""
			+ "\",\"maxlength\":\""+maxlength
			+ "\",\"isBundle\":\""+ "false"
			+ "\",\"isSubmit\":\""+ "false"
			+ "\",\"labelText\":\""+$(div).attr("labeltext")
			+ "\",\"navigateTo\":\""
			+ "\",\"navigateToText\":\""
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
			if($(div).attr('msgvalue') != undefined){
				elementButton += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
			} else {
				elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		elementTD += "<button type=\"button\" class=\"btn qp-button\" ondblclick=\"openDialogButtonAreaSettingSection(this)\" value=\""+$(div).attr('labelText')+"\">"+$(div).attr('labelText')+"</button>"+elementButton;
	}
	if(dataType == 20){
		elementTD += "<label ondblclick=\"openDialogLableSettingSection(this)\" style=\"cursor: pointer;\">"+div.attr("label")+"</label>"+element;
	}

	var style = "padding:2px;";
	
	var direction = $(obj).closest(".areaContent").find("input[name=formDirection]").val();
	var displayType = $(obj).closest(".areaContent").find("input[name=formDisplayType]").val();
	
	if (displayType == 1) {
		style += "clear: both;";
	} else {
		
	}
	
	if (direction == 1 || direction=="") {
		style += "float: right;";
	} else {		
		style += "float: left";
	}
	
	
	$(obj).append("<span style=\""+style+"\">" + elementTD + "</span>");			
}

function getAreaCode(obj, areaName) {
	var maxArea = 0;
	
	$(obj).closest(".form-area-content").find("input[name=formAreaCode]").each(function() {
		var area = $(this).val(); 
		if (area.indexOf(areaName) != -1) {
			var order = area.replace(areaName, "");
			try {
				var orderArea = parseInt(order); 
				if (maxArea < orderArea)
				{
					maxArea = orderArea;
				}
			}catch(err) {
				
			}
			
		}
	});
	//"singleEntity"
	return areaName + (maxArea + 1);
}

function insertArea(ev, ui, obj){
	var div = $(ui.draggable);	
	
	//addFormArea();
	
	// add single Entity
	if($(div).attr("elementtype") == 0 && ($(div).attr("searchtype") == undefined || $(div).attr("searchtype") != 1)){
		
		var isAddRemoveTable = 0;
		if ($(div).attr('islist') != undefined) {
			isAddRemoveTable = 1;
		}
		
		$(obj).before(returnTableSetting(getAreaCode(obj, 'singleEntity'), null, isAddRemoveTable) + "<table class=\"table table-bordered qp-table-form\">" +
			"<colgroup>" +
				"<col width=\"20%\"/>" +
				"<col width=\"30%\"/>" +
				"<col width=\"20%\"/>" +
				"<col width=\"30%\"/>" +
			"</colgroup>" +
			"<tbody>" +
			"<tr>"+returnAddRemoveTD(0,0)+returnAddRemoveTD(0,1)+"<td class=\"srcgenControl\" width=\"20\" index=\"2\"> </td>" + 
			"<td class=\"srcgenControl\" width=\"20\" index=\"3\">" + 
				"&nbsp;" + 
			"</td></tr>" +
			"<tr index=\"0\">" +
			"<th index=\"0\" class=\"\">" +
				returnHiddenTHEmpty() + "<span style=\"float: left; cursor: move;\" class=\"qp-glyphicon glyphicon glyphicon-screenshot\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></span><div class=\"dropLabel\">&nbsp;</div>" +
			"</th>" +
			"<td index=\"1\"><input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"enableGroup\"  /><input type=\"hidden\" name=\"groupTotalElement\"  /><div class=\"dropComponent\"><input type=\"hidden\" name=\"formElement\" value=\"\"/>&nbsp;</div></td>" +
			"<th index=\"2\" class=\"\">" +
				returnHiddenTHEmpty() + "<span style=\"float: left; cursor: move;\" class=\"qp-glyphicon glyphicon glyphicon-screenshot\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></span><div class=\"dropLabel\">&nbsp;</div></th>" +
			"<td index=\"3\"><input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"enableGroup\"  /><input type=\"hidden\" name=\"groupTotalElement\"  /><div class=\"dropComponent\"><input type=\"hidden\" name=\"formElement\" value=\"\"/>&nbsp;</div></td>" +
			returnSortRemoveTD() + 
			"</tr>" +
			"<tr index=\"1\">" +
			"<th index=\"0\" class=\"\">" +
				returnHiddenTHEmpty() + "<span style=\"float: left; cursor: move;\" class=\"qp-glyphicon glyphicon glyphicon-screenshot\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></span><div class=\"dropLabel\">&nbsp;</div>" +
			"</th>" +
			"<td index=\"1\"><input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"enableGroup\"  /><input type=\"hidden\" name=\"groupTotalElement\"  /><div class=\"dropComponent\"><input type=\"hidden\" name=\"formElement\" value=\"\"/>&nbsp;</div></td>" +
			"<th index=\"2\" class=\"\">" +
				returnHiddenTHEmpty() + "<span style=\"float: left; cursor: move;\" class=\"qp-glyphicon glyphicon glyphicon-screenshot\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></span><div class=\"dropLabel\">&nbsp;</div></th>" +
			"<td index=\"3\"><input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"enableGroup\"  /><input type=\"hidden\" name=\"groupTotalElement\"  /><div class=\"dropComponent\"><input type=\"hidden\" name=\"formElement\" value=\"\"/>&nbsp;</div></td>" +
			returnSortRemoveTD() + 
			"</tr>" +
			"</tbody></table>" +
			returnAddNewRow() + "</div></div>");
		
		$(obj).closest(".form-area-content").find("table:last").find("tbody").sortable({
			helper: function(e, ui) {
				ui.children().each(function() {
					$(this).width($(this).width());
				});
				return ui;
			},
			update: function(e, ui) {
				reIndexTable(e.target.closest("table"));
			},
			cursor: 'move',
			items: 'tr:not(:first,[class=disableSort])',
			handle: '.sortable'
		});
		
		//$("#srcgenScreen fieldset:last").find("legend").find("span").find("input[name=formElementTable]").val("2,2");
		$(obj).closest(".form-area-content").find("div[class='areaContent']:last").find("input[name=formElementTable]").val("2,2");
	}	
	if($(div).attr("elementtype") == 0 && ($(div).attr("searchtype") != undefined && $(div).attr("searchtype") == 1)){
		var countSearchCondition = 0;
		$("input[name=formAreaType]").each(function() {
			if($(this).val() == 0){
				countSearchCondition += 1;
				return false;
			}
		});
		var isAddRemoveTable = 0;
		if ($(div).attr('islist') != undefined) {
			isAddRemoveTable = 1;
		}
		if(true){
			$(obj).before(returnTableSetting(getAreaCode(obj, 'singleEntity'), null, isAddRemoveTable) + "<table type=\"search\" class=\"table table-bordered qp-table-form\">" +
					"<colgroup>" +
						"<col width=\"20%\"/>" +
						"<col width=\"30%\"/>" +
						"<col width=\"20%\"/>" +
						"<col width=\"30%\"/>" +
					"</colgroup>" +
					"<tbody>" +
					"<tr>"+returnAddRemoveTD(0,0)+returnAddRemoveTD(0,1)+"<td class=\"srcgenControl\" width=\"20\" index=\"2\"> </td>" + 
					"<td class=\"srcgenControl\" width=\"20\" index=\"3\">" + 
						"&nbsp;" + 
					"</td></tr>" +
					"<tr index=\"0\">" +
					"<th index=\"0\" class=\"\">" +
						returnHiddenTHEmpty() + "<span style=\"float: left; cursor: move;\" class=\"qp-glyphicon glyphicon glyphicon-screenshot\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></span><div class=\"dropLabel\">&nbsp;</div>" +
					"</th>" +
					"<td index=\"1\"><input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"enableGroup\"  /><input type=\"hidden\" name=\"groupTotalElement\"  /><div class=\"dropComponent\"><input type=\"hidden\" name=\"formElement\" value=\"\"/>&nbsp;</div></td>" +
					"<th index=\"2\" class=\"\">" +
						returnHiddenTHEmpty() + "<span style=\"float: left; cursor: move;\" class=\"qp-glyphicon glyphicon glyphicon-screenshot\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></span><div class=\"dropLabel\">&nbsp;</div></th>" +
					"<td index=\"3\"><input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"enableGroup\"  /><input type=\"hidden\" name=\"groupTotalElement\"  /><div class=\"dropComponent\"><input type=\"hidden\" name=\"formElement\" value=\"\"/>&nbsp;</div></td>" +
					returnSortRemoveTD() + 
					"</tr>" +
					"<tr index=\"1\">" +
					"<th index=\"0\" class=\"\">" +
						returnHiddenTHEmpty() + "<span style=\"float: left; cursor: move;\" class=\"qp-glyphicon glyphicon glyphicon-screenshot\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></span><div class=\"dropLabel\">&nbsp;</div>" +
					"</th>" +
					"<td index=\"1\"><input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"enableGroup\"  /><input type=\"hidden\" name=\"groupTotalElement\"  /><div class=\"dropComponent\"><input type=\"hidden\" name=\"formElement\" value=\"\"/>&nbsp;</div></td>" +
					"<th index=\"2\" class=\"\">" +
						returnHiddenTHEmpty() + "<span style=\"float: left; cursor: move;\" class=\"qp-glyphicon glyphicon glyphicon-screenshot\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></span><div class=\"dropLabel\">&nbsp;</div></th>" +
					"<td index=\"3\"><input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"enableGroup\"  /><input type=\"hidden\" name=\"groupTotalElement\"  /><div class=\"dropComponent\"><input type=\"hidden\" name=\"formElement\" value=\"\"/>&nbsp;</div></td>" +
					returnSortRemoveTD() + 
					"</tr>" +
					"</tbody></table>" +
					returnAddNewRow() + "</div></div>");
				
			$(obj).closest(".form-area-content").find("table:last").find("tbody").sortable({
				helper: function(e, ui) {
					ui.children().each(function() {
						$(this).width($(this).width());
					});
					return ui;
				},
				update: function(e, ui) {
					reIndexTable(e.target.closest("table"));
				},
				cursor: 'move',
				items: 'tr:not(:first,[class=disableSort])',
				handle: '.sortable'
			});
				
				//$("#srcgenScreen fieldset:last").find("legend").find("span").find("input[name=formElementTable]").val("2,2");
			$(obj).closest(".form-area-content").find("div[class='areaContent']:last").find("input[name=formElementTable]").val("2,2");
		}
	}
	
	// add List Entities
	if($(div).attr("elementtype") == 1){
		$(obj).before(returnTableListSetting(null, getAreaCode(obj, 'listEntities')) + "<table class=\"table table-bordered qp-table-list-none-action\">" +
				"<colgroup>" +
					"<col width=\"25%\"/>" +
					"<col width=\"25%\"/>" +
					"<col width=\"25%\"/>" +
					"<col width=\"25%\"/>" +
				"</colgroup>" +
				"<thead><tr class=\"style-even-row\" index='0'><th index='0'><span style=\"float: left; cursor: move;\" class=\"qp-glyphicon  glyphicon glyphicon-screenshot\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></span>"+returnHiddenTHEmpty()+"<div class=\"dropLabel\">&nbsp;</div></th>" +
					"<th index='1'><span style=\"float: left; cursor: move;\" class=\"qp-glyphicon  glyphicon glyphicon-screenshot\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></span>"+returnHiddenTHEmpty()+"<div class=\"dropLabel\">&nbsp;</div></th>" +
					"<th index='2'><span style=\"float: left; cursor: move;\" class=\"qp-glyphicon glyphicon glyphicon-screenshot\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></span>"+returnHiddenTHEmpty()+"<div class=\"dropLabel\">&nbsp;</div></th>" +
					"<th index='3'><span style=\"float: left; cursor: move;\" class=\"qp-glyphicon  glyphicon glyphicon-screenshot\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></span>"+returnHiddenTHEmpty()+"<div class=\"dropLabel\">&nbsp;</div></th></tr></thead>" +
				"<tbody><tr class=\"style-odd-row\" index='1'>" +
					"<td class=\"align-left\" index='0'><span style=\"float: left; cursor: move;\" class=\"qp-glyphicon  glyphicon glyphicon-screenshot\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></span><input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"groupTotalElement\"  /><input type=\"hidden\" name=\"enableGroup\"  /><div class=\"dropComponent\"><input type=\"hidden\" name=\"formElement\" />&nbsp;</div></td>" +
					"<td index='1'><span style=\"float: left; cursor: move;\" class=\"qp-glyphicon  glyphicon glyphicon-screenshot\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></span><input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"groupTotalElement\"  /><input type=\"hidden\" name=\"enableGroup\"  /><div class=\"dropComponent\"><input type=\"hidden\" name=\"formElement\" />&nbsp;</div></td>" +
					"<td index='2'><span style=\"float: left; cursor: move;\" class=\"qp-glyphicon  glyphicon glyphicon-screenshot\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></span><input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"groupTotalElement\"  /><input type=\"hidden\" name=\"enableGroup\"  /><div class=\"dropComponent\"><input type=\"hidden\" name=\"formElement\" />&nbsp;</div></td>" +
					"<td index='3'><span style=\"float: left; cursor: move;\" class=\"qp-glyphicon  glyphicon glyphicon-screenshot\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></span><input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"groupTotalElement\"  /><input type=\"hidden\" name=\"enableGroup\"  /><div class=\"dropComponent\"><input type=\"hidden\" name=\"formElement\" />&nbsp;</div></td>" +
					"</tr></tbody><tfoot>" +
				"<tr>" +
				returnAddRemoveTD(1,0) +
				returnAddRemoveTD(1,1) +
				returnAddRemoveTD(1,2) +
				returnAddRemoveTD(1,3) +
				"</tr>" +
				"</tfoot></table></div>");
		
		if($(div).attr("searchtype") == 2 || $(div).attr("searchtype") == 4) {
			$(obj).closest(".form-area-content").find("div[class='areaContent']:last").find("input[name=formFixedRow]").val(1);
			
		}
		if($(div).attr("searchtype") == 1 ) {
			$(obj).closest(".form-area-content").find("div[class='areaContent']:last").find("input[name=formFixedRow]").val(0);
		}
		
		$(obj).closest(".form-area-content").find("div[class='areaContent']:last").find("input[name=formElementTable]").val("4,4");
//		$("#srcgenScreen").find("div[class='areaContent']:last").find("input[name=formTableColumnSize]").val("25%,25%,25%,25%");
	}
	
	if($(div).attr("elementtype") == 0 || $(div).attr("elementtype") == 1){
		$(obj).closest(".form-area-content").find("table:last").find("tbody td").droppable({
			accept: "#srcgenElements div, #srcgenAction div, #newDragElementTd div",
			activeClass: "state-droppable",
			drop: function(event, ui) {					
				insertComponent(event, ui, $(this));			
			}
		});
		$(obj).closest(".form-area-content").find("table:last").find("tbody td").droppable( "option", "disabled", false );
		$(obj).closest(".form-area-content").find("table:last").find("tbody td").find("input[name='enableGroup']").val(true);
		$(obj).closest(".form-area-content").find("table:last").find("tbody td").find("input[name='groupDisplayType']").val(1);
	}
	
	// add DIV
	if($(div).attr("elementtype") == 2){
		$(obj).before(returnDivSetting(getAreaCode(obj, 'section')) + "<div class=\"section-area\"></div>");
		
		$(obj).closest(".form-area-content").find("div[class='areaContent']:last").find(".section-area").droppable({
			accept: "#srcgenControlDiv tr[class!='srcgenElementsTable'] div[id!=sectionCustomArea], #newDragElementTd div",
			activeClass: "state-droppable",
			drop: function(event, ui) {
				insertAnyElement(event, ui, $(this));				
			}
		});		
		
		$(obj).closest(".form-area-content").find("div[class='areaContent']:last").find(".section-area").sortable({
			helper: function(e, ui) {
				ui.children().each(function() {
					$(this).width($(this).width());
				});
				return ui;
			},
			cursor: 'move',
			items: 'span',
			handle: '.sortable'
		});
	}
	
	if($(div).attr("elementtype") == 3) {
		$(obj).before(returnActionArea(getAreaCode(obj, 'action')) + "<div class=\"action-area\"></div>");
		
		$(obj).closest(".form-area-content").find(".action-area").droppable({
			accept: "#divHtmlButton, #divLabel, #divDynamicLabel, #divHtmlLink, #divHtmlLinkDynamic",
			activeClass: "state-droppable",
			drop: function(event, ui) {
				insertActionElement(event, ui, $(this));				
			}
		});		
		
	}	
	if($(div).attr("elementtype") == 4) {
		$(obj).before(returnCustomSection(getAreaCode(obj, 'action')) + "<div class=\"custom-area\"></div>");
		
	}
	
	//get form index
	var formIndex = $(obj).closest('.form-area-content').find('input[name=screenFormFormSeqNo]').val();
	//update form index
	$(obj).closest('.form-area-content').find('.areaContent:last').find('input[name=formIndex]').val(formIndex);
	
	$(obj).closest(".form-area-content").find("table:last").attr("id", "srcgenTableId" + ($(obj).closest(".form-area-content").find("table").length - 1));
	
	$(obj).closest(".form-area-content").find("table:last").find("tr").find(".glyphicon-screenshot").draggable({
		containment: "#" + $(obj).closest(".form-area-content").find("table:last").attr("id"),
		stack: "#" + $(obj).closest(".form-area-content").find("table:last").attr("id"),
		revert:function(event, ui) {
			if ($(this).data("uiDraggable") != undefined && $(this).data("uiDraggable").originalPosition != undefined) {
				$(this).data("uiDraggable").originalPosition = {top : 0,left : 0};
			}
			return true;
		},
		stop: function(event, ui) {			
    	  	$(this).css("z-index","auto");    	  	
    	  } 
	});
		
	$(obj).closest(".form-area-content").find("table:last").find("tr td").find(".glyphicon-screenshot").droppable({
		accept: "#" + $(obj).closest(".form-area-content").find("table:last").attr("id") + " tr td .glyphicon-screenshot",
		activeClass: "state-droppable",
		drop: function(event, ui) {			
			replaceElement(event, ui, $(this));
		}
	});
	
	$(obj).closest(".form-area-content").find("table:last").find("tr th").find(".glyphicon-screenshot").droppable({
		accept: "#" + $(obj).closest(".form-area-content").find("table:last").attr("id") + " tr th .glyphicon-screenshot",
		activeClass: "state-droppable",
		drop: function(event, ui) {						
			replaceElement(event, ui, $(this));
		}
	});
	
	$(obj).closest(".form-area-content").find("table:last").find("tr").find("td[class=srcgenControl]").find(".glyphicon-screenshot").droppable({
		accept: "#" + $(obj).closest(".form-area-content").find("table:last").attr("id") + " tr td[class=srcgenControl] .glyphicon-screenshot",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			swapColumn(event, ui, $(this));
		}
	});
	
	if($(div).attr("elementtype") != 2){			
		$(obj).closest(".form-area-content").find("table:last").find("div[class='dropComponent']").each(function (){
			if($(this).children().length == 1 && $(this).children().eq(0).prop("type") == "hidden"){
				$(this).droppable({
					accept: "#srcgenElements div, #srcgenAction div, #newDragElementTd div",
					activeClass: "state-droppable",
					drop: function(event, ui) {
						insertComponent(event, ui, $(this));
					}
				});
			}
		});
		
		$(obj).closest(".form-area-content").find("table:last").find("div[class='dropLabel']").each(function (){
			if($(this).children().length == 0){
				$(this).droppable({
					accept: "#divLabel",
					activeClass: "state-droppable",
					drop: function(event, ui) {										
						insertLable(event, ui, $(this));						
					}
				});
			}
		});
	}

	$(".form-area-content").sortable({
        connectWith: '.form-area-content',
        handle: '.srcgenTableSort',
        update: function(e, ui) {
        	refreshFormIndex();
		},
		helper: function(e, ui) {
			ui.width(ui.outerWidth() - 4);
			return ui;
		},
		items: '.areaContent:not(.area-tab .areaContent), .area-tab',
		deactivate: function( event, ui ) {
			var temp = $(this).closest('.form-area-content').find("#srcgenAreaTemplate").clone();
			$(this).closest('.form-area-content').find("#srcgenAreaTemplate").remove();
			$(this).closest('.form-area-content').append(temp);
			
			$(".drap-drop-area").droppable({
				accept: ".srcgenElementsTable div[elementtype!=4]",
				activeClass: "state-droppable",
				drop: function(event, ui) {
					insertArea(event, ui, $(this));
				}
			});
		}
	});
	
	$(".area-tab").sortable({
        handle: '.srcgenTableSort',
        update: function(e, ui) {
        	refreshFormIndex();
		},
		helper: function(e, ui) {
			ui.width(ui.outerWidth());
			return ui;
		},
		items: '.areaContent',
	});
}
function addFormArea(){
	var formSize =$("#srcgenScreen").find("div[class='form-content']").length; 
	if (formSize == 0) {
		var formHidden = "<div class=\"form-content\">";
		formHidden += "<input type=\"hidden\" name=\"screenFormFormActionCode\" />";
		formHidden += "<input type=\"hidden\" name=\"screenFormFormActionName\"/>";
		formHidden += "<input type=\"hidden\" name=\"screenFormEncryptType\" value=\"3\" />";
		formHidden += "<input type=\"hidden\" name=\"screenFormMethodType\" />";
		formHidden += "<input type=\"hidden\" name=\"screenFormFormSeqNo\" value=\"0\" />";
		formHidden += "</div>";
		// one form
		$("#srcgenScreen").append(formHidden);
	}else{
		var isEnable = false;
		var indexForm;
		indexForm =  $("#srcgenScreen").find("div[class='areaContent']:last").find("input[name=formIndex]").val();
		if(indexForm ==undefined || indexForm.length==0){
			indexForm = 0;
		}
		if(indexForm==(formSize-1)){
			var formHidden = "<div class=\"form-content\">";
			formHidden += "<input type=\"hidden\" name=\"screenFormFormActionCode\" />";
			formHidden += "<input type=\"hidden\" name=\"screenFormFormActionName\"/>";
			formHidden += "<input type=\"hidden\" name=\"screenFormEncryptType\" value=\"3\" />";
			formHidden += "<input type=\"hidden\" name=\"screenFormMethodType\" />";
			formHidden += "<input type=\"hidden\" name=\"screenFormFormSeqNo\" value=\""+formSize+"\" />";
			formHidden += "</div>";
			// one form
			$("#srcgenScreen").find("div[class='form-content']:last").after(formHidden);
		}
	}
}
function updateFormIndex(e, ui){
	var screen = $("#srcgenScreen");
	var currentArea = ui.item;
	var prevArea = $(currentArea).prevAll("div[class=areaContent]").first();
	if(prevArea==undefined || prevArea.length ==0){
		$(currentArea).find("input[name=formIndex]").val(0);
	}else{
		var prevAreaFormIndex = $(prevArea).find("input[name=formIndex]").val();
		if(prevAreaFormIndex==undefined || prevAreaFormIndex.length ==0){
			 $(prevArea).find("input[name=formIndex]").val(0);
			 $(currentArea).find("input[name=formIndex]").val(0);
		}else{
			$(currentArea).find("input[name=formIndex]").val(prevAreaFormIndex);
		}
	}
	arrangeFormArea();
	
}
function arrangeFormArea(){
	var size = $("#srcgenScreen").find("input[name=screenFormFormSeqNo]").length;
	var formEnable = [];
	for(i=0;i < size-1;i++){
		var numberFormEnable =$("#srcgenScreen").find("input[name=formIndex][value="+i+"]").length;
		if(numberFormEnable>0)
			formEnable[i]=true;
		else
			formEnable[i]=false;
	}
	for(i=0;i < formEnable.length ;i++){
		if(!formEnable[i]){
			 //delete form
			 $("#srcgenScreen").find("input[name=screenFormFormSeqNo][value="+i+"]").parent().remove();
			 //update index form
			 var lastIndex=  $("#srcgenScreen").find("input[name=screenFormFormSeqNo]:last").val();
			 for(j=i+1;j<=lastIndex ;j++){
				 $("#srcgenScreen").find("input[name=screenFormFormSeqNo][value="+j+"]").val(j-1);
				 $("#srcgenScreen").find("input[name=formIndex][value="+j+"]").val(j-1);
			 }
		}
	}
	
	//re-index form
//	$("#srcgenScreen").find("input[name=formIndex]").each(function (){
//		var currentIndex = $(this).val();
//		var nextIndex = $(this).next("input[name=formIndex]").val();
//		
//		// In case : form index of this area < form index of next area
//		if(currentIndex > nextIndex){
//			 $("#srcgenScreen").find("input[name=screenFormFormSeqNo][value="+currentIndex+"]").val(-1);
//			 $("#srcgenScreen").find("input[name=formIndex][value="+currentIndex+"]").val(-1);
//			 
//			 $("#srcgenScreen").find("input[name=screenFormFormSeqNo][value="+nextIndex+"]").val(currentIndex);
//			 $("#srcgenScreen").find("input[name=formIndex][value="+nextIndex+"]").val(currentIndex);
//			 
//			 $("#srcgenScreen").find("input[name=screenFormFormSeqNo][value="+-1+"]").val(nextIndex);
//			 $("#srcgenScreen").find("input[name=formIndex][value="+-1+"]").val(nextIndex);
//		}
//	});
}
function saveFormInfor(setting,form){
	var formIndex = $(form).find("[name=modal-setting-form-index]").val();
	if(formIndex != undefined && formIndex.length>0){
		$(setting).closest("span").find("input[name=formIndex]").val(formIndex);
		var formArea=  $("#srcgenScreen").find("input[name=screenFormFormSeqNo][value="+formIndex+"]").parent();
		if(formArea.length>0){
			var formType = $(form).find("[name=modal-setting-form-method]").val();
			if(formType != undefined && formType != ""){
				$(formArea).find("[name=screenFormMethodType]").val(formType);
			}
			var formAction = $(form).find("[name=modal-setting-form-action]").val();
			if(formAction != undefined && formAction != ""){
				$(formArea).find("[name=screenFormFormActionCode]").val(formAction);
			}
			var formActionName = $(form).find("[name=modal-setting-form-actionAutocomplete]").val();
			if(formActionName != undefined && formActionName != ""){
				$(formArea).find("[name=screenFormFormActionName]").val(formActionName);
			}
			var formEncryptType = $(form).find("[name=modal-setting-form-enctype]").val();
			if(formEncryptType != undefined && formEncryptType != ""){
				$(formArea).find("[name=screenFormEncryptType]").val(formEncryptType);
			}
		}
	}
	arrangeFormArea();
}
function onChangeFormIndex(thiz){
	var selectedIndex = $(thiz).val();
	var form = $(thiz).parents("div[id$='form']");
	var formArea=  $("#srcgenScreen").find("input[name=screenFormFormSeqNo][value="+selectedIndex+"]").parent();
	if(form.length>0 && formArea.length > 0){
		var methodType = $(formArea).find("input[name=screenFormMethodType]").val();
		var encryptType = $(formArea).find("input[name=screenFormEncryptType]").val();
		var actionCode = $(formArea).find("input[name=screenFormFormActionCode]").val();
		var actionName = $(formArea).find("input[name=screenFormFormActionName]").val();
		
		if(methodType != undefined && methodType.length >0){
			$(form).find("select[name=modal-setting-form-method]").val(methodType);
		}else{
			$(form).find("select[name=modal-setting-form-method]").val("");
		}
		if(encryptType != undefined && encryptType.length >0){
			$(form).find("select[name=modal-setting-form-enctype]").val(encryptType);
		}else{
			$(form).find("select[name=modal-setting-form-enctype]").val("3");
		}
		if(actionCode != undefined && actionCode.length >0){
			$(form).find("input[name=modal-setting-form-actionAutocomplete]").val(actionName);
			$(form).find("input[name=modal-setting-form-action]").val(actionCode);
		}else{
			$(form).find("input[name=modal-setting-form-actionAutocomplete]").val("");
			$(form).find("input[name=modal-setting-form-action]").val("");
		}
	}
}
function initSelectFormIndex(obj,form){
	var trOptionFormIndex = "";
	var currentArea =  $(obj).parents(".areaContent");
	var prevArea = $(currentArea).prevAll(".areaContent").first();
	var nextArea = $(currentArea).nextAll(".areaContent").first();
	var prevAreaFormIndex = '';
	var nextAreaFormIndex = '';
	var formSize = $("#srcgenScreen").find("input[name='screenFormFormSeqNo']").length;
	var currentIndex = $(obj).closest("span").find("input[name=formIndex]").val();
	if(currentIndex == undefined || currentIndex.length == 0){
		currentIndex = 0;
	}
	
	if(prevArea != undefined && prevArea.length >0){
		prevAreaFormIndex = $(prevArea).find("input[name=formIndex]").val();
		if(prevAreaFormIndex == undefined || prevAreaFormIndex.length==0){
			$(obj).parents(".areaContent").prevAll(".areaContent").each(function (){
				if($(this).find("input[name=formIndex]") != undefined ){
					$(this).find("input[name=formIndex]").val(0);
				}
			});
			prevAreaFormIndex = 0;
		}else{
			prevAreaFormIndex = parseInt(prevAreaFormIndex);
		}
	}else{
		prevAreaFormIndex = 0;
	}
	if(nextArea!= undefined && nextArea.length >0){
		nextAreaFormIndex = $(nextArea).find("input[name=formIndex]").val();
		if(nextAreaFormIndex == undefined || nextAreaFormIndex.length==0){
			$(nextArea).find("input[name=formIndex]").val(currentIndex); // check lai
			nextAreaFormIndex ==0;
		}else{
			nextAreaFormIndex = parseInt(nextAreaFormIndex);
		}
		
		//calc
		for(i=0;i<=nextAreaFormIndex-prevAreaFormIndex;i++){
			var temp = prevAreaFormIndex+i;
			trOptionFormIndex += "<option value=\""+temp+"\">"+temp+"</option>";
		}
	}else{
		for(i=0;i<formSize-prevAreaFormIndex;i++){
			var temp = prevAreaFormIndex+i;
			trOptionFormIndex += "<option value=\""+temp+"\">"+temp+"</option>";
		}
	}
	$(form).find("select[name=modal-setting-form-index]").html(trOptionFormIndex);
	$(form).find("select[name=modal-setting-form-index]").val(currentIndex);
	
	//get infor of form
	onChangeFormIndex($(form).find("select[name=modal-setting-form-index]"));
}
function addEntity(searchType){	
	var dynamicTableId = $("[name=tableMapping]").val();
	
	if ($("[name=tableMapping]").val() == undefined || $("[name=tableMapping]").val() == '' || parseInt($("[name=tableMapping]").val()) == 0) {
		alert(dbMsgSource["sc.screendesign.0165"]);
		return;
	}
	
	var url = CONTEXT_PATH + "/screendesign/getColumns?tableId="+dynamicTableId+ "&columnId=-1" +"&r="+Math.random();	
	
	var data = $.qp.getData(url);
	var selectScreenType = $("[name=selectScreenType]").val();
	if (data != '' && data != null){
		var content = "";
		
		if($("[name=radioEntityType]:checked").val() == "0" || $("[name=radioEntityType]:checked").val() == "2"){
			var isList = 0;
			if ($("[name=radioEntityType]:checked").val() == "2") {
				isList = 1;
			}
			var countSearchCondition = 0;
				if (searchType != null && searchType == 1) {
					$("input[name=formAreaType]").each(function() {
						if($(this).val() == 0){
							countSearchCondition += 1;
							return false;
						}
					});
					if(countSearchCondition == 0){
						var searchTypeAttr = "type='search'";
					}
					
				}
				
				var tableName = "";
				
				if (data.length > 0) {
					var obj = data[0];
					if (obj.tablename != undefined) {
						tableName =obj.tablecode; 
						var maxIndex = 0;
						var isExists = false;
						$(".form-area-content:last").find(".areaContent").each(function() {
							var area = $(this);
							var areaCode = $(this).find("input[name=formAreaCode]").val();
							
							if (areaCode.indexOf(tableName) == 0) {
								isExists = true;
								try {
									var maxTemp = parseInt(areaCode.replace(tableName, ""));
								
									if (maxTemp > maxIndex) maxIndex = maxTemp;
								} catch(ex) {
									
								}
							}
						});
						
						if (maxIndex > 0 || isExists) {
							maxIndex++;
							tableName = tableName + maxIndex;
						}
					}
				}
				var name = data[0].tablename;
				
				
				var content = returnTableSetting(tableName,name, isList) + "<table "+searchTypeAttr+" class=\"table table-bordered qp-table-form\">" +
							"<colgroup>" +
							"<col width=\"20%\"/>" +
							"<col width=\"30%\"/>" +
							"<col width=\"20%\"/>" +
							"<col width=\"30%\"/>" +
						"</colgroup>" +
						"<tbody>" +
						"<tr>"+returnAddRemoveTD(0,0)+returnAddRemoveTD(0,1)+"<td class=\"srcgenControl\" width=\"3%\" index=\"2\"> </td>" + 
						"<td class=\"srcgenControl\" width=\"3%\" index=\"3\">" + 
							"&nbsp;" + 
						"</td></tr>";
				if (data.length > 0) {
					content += "<tr index=\"0\">";
					var j = 1;
					var indexRow = 1;
					var indexCol = 0;
					for (i = 0; i < data.length; i++) {
						var obj = data[i];
						
						content += "<th index=\""+indexCol+"\">"+returnElementTHAddEntity(obj, "false")+"</th>";
						if (selectScreenType == "3") {
							obj.datatype= "21";
							content += "<td index=\""+(++indexCol)+"\"><input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"enableGroup\"  /><input type=\"hidden\" name=\"groupTotalElement\"  />"+returnElementTDLoad(obj)+"</td>";
						} else {
							if (obj.datatype == undefined || obj.datatype == '' || obj.datatype > 21) {
								content += "<td index=\""+(++indexCol)+"\"><input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"enableGroup\"  /><input type=\"hidden\" name=\"groupTotalElement\"  /><div class=\"dropComponent\"><input type=\"hidden\" name=\"formElement\" value=\"\"/>&nbsp;</div></td>";
							} else 
							{
								if(countSearchCondition == 0 && searchType == 1){
									content += "<td index=\""+(++indexCol)+"\"><input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"enableGroup\"  /><input type=\"hidden\" name=\"groupTotalElement\"  />"+returnElementTDSearchEntity(obj)+"</td>";
								} else {
									content += "<td index=\""+(++indexCol)+"\"><input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"enableGroup\"  /><input type=\"hidden\" name=\"groupTotalElement\"  />"+returnElementTDLoad(obj)+"</td>";
								}
								
							}
							
						}
						
						
						//add th, td if data not fill table
						if (i == data.length - 1 && j < 2) { 
							content += "<th index=\""+parseInt(++indexCol)+"\">"+returnHiddenTHEmpty()+"<span style=\"float: left; cursor: move;\" class=\"qp-glyphicon glyphicon glyphicon-screenshot\" title=\"Move\"></span><div class=\"dropLabel\">&nbsp;</div></th>";							
							content += "<td index=\""+parseInt(++indexCol)+"\"><input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"groupTotalElement\"  /><input type=\"hidden\" name=\"enableGroup\"  /><input type=\"hidden\" name=\"formElement\" value=\"\"><div class=\"dropComponent\">&nbsp;</div></td>";												
							j++;
						}
						
						if (j == 2) {							
							content += returnSortRemoveTD() + "</tr>";
							j = 0;
						}
						
						if (i < data.length - 1 && j == 0) {
							content += "<tr index=\""+(indexRow++)+"\">";
							indexCol = 0;
						} else {
							indexCol++;
						}
						j++;
						
					}
				} else {
					alert(dbMsgSource["sc.screendesign.0166"]);
				}
				content += "</tbody></table>" +
			returnAddNewRow() + "</div></div>";
				if(countSearchCondition == 0){
					$(".form-area-content:last").find("#srcgenAreaTemplate").before(content);
					
					$(".form-area-content:last").find("table:last").find("tbody").sortable({
						helper: function(e, ui) {
							ui.children().each(function() {
								$(this).width($(this).width());
							});
							return ui;
						},
						cursor: 'move',
						items: 'tr:not(tr:first)',
						handle: '.sortable'
					});
									
					$(".form-area-content:last").find("div[class='areaContent']:last").find("input[name=formElementTable]").val("2,2");
				} else {
					alert(dbMsgSource["sc.screendesign.0167"]);
				}
		} else {
			// checked List Entities
			if (data.length > 0) {
				var width = parseInt(100/data.length);
				
				var content = returnTableListSetting(selectScreenType); 
				content += "<table class=\"table table-bordered qp-table-list-none-action\">" +
								"<colgroup>";
				for (i = 0; i < data.length; i++) {
					content += "<col width=\""+width+"%\"/>";
				}
				content += 
							"</colgroup>" +
							"<thead><tr class=\"style-even-row\" index='0'>";
				for (i = 0; i < data.length; i++) {
					var obj = data[i];
					content += "<th index=\""+i+"\">"+returnElementTHAddEntity(obj, "false")+"</th>";
				}
				content += "</tr></thead><tbody><tr index=\"1\">";
				
				for (i = 0; i < data.length; i++) {
					var obj = data[i];
					
					if (selectScreenType == "3") {
						obj.datatype= "21";
						content += "<td index=\""+i+"\"><input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"enableGroup\"  /><input type=\"hidden\" name=\"groupTotalElement\"  />"+returnElementTDLoad(obj)+"</td>";
					} else {
						content += "<td index=\""+i+"\"><input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"enableGroup\"  /><input type=\"hidden\" name=\"groupTotalElement\"  />"+returnElementTDLoad(obj)+"</td>";
					}
				}
				content += "</tr></tbody>";
				content += "<tfoot>";
				content +=	"<tr>";
				for (i = 0; i < data.length; i++) {
					content += returnAddRemoveTD(1,i);
				}
							
				content +=	"</tr>" +
							"</tfoot></table></div>";
				$(".form-area-content:last").find("#srcgenAreaTemplate").before(content);
				
				$(".form-area-content:last").find("table:last").find("div").each(function (){
	    			if($(this).children().length == 0){
	    				$(this).droppable({
	    					accept: "#srcgenElements div, #srcgenAction div, #newDragElementTd div",
	    					activeClass: "state-droppable",
	    					drop: function(event, ui) {
	    						insertElementList(event, ui, $(this));
	    					}
	    				});
	    			}
	    		});
				
				$(".form-area-content:last").find("table:last").attr("id", "srcgenTableId" + ($(".form-area-content:last").find("table").length - 1));
	    		
				$(".form-area-content:last").find("table:last").find("span").droppable({
	    			accept: "#" + $("#srcgenScreen table:last").attr("id") + " span",
	    			activeClass: "state-droppable",
	    			drop: function(event, ui) {
	    				replaceElementList(event, ui, $(this));
	    			}
	    		});
	    		    		
				$(".form-area-content:last").find("div[class='areaContent']:last").find("input[name=formElementTable]").val(data.length + "," + data.length);
			} else {
				alert(dbMsgSource["sc.screendesign.0166"]);
			}
			 
		}
	} else {
		alert(dbMsgSource["sc.screendesign.0166"]);
	}
	
	$(".areaContent").find("table:last").find("div[class='dropComponent']").droppable({
		accept: "#srcgenElements div, #srcgenAction div, #newDragElementTd div",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertComponent(event, ui, $(this));
		}
	});
	
	$(".areaContent").find("table:last").find("div[class='dropLabel']").droppable({
		accept: "#divLabel",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertLable(event, ui, $(this));
		}
	});
	
	$(".form-area-content:last").find("table:last").find("tbody").sortable({
		helper: function(e, ui) {
			ui.children().each(function() {
				$(this).width($(this).width());
			});
			return ui;
		},
		cursor: 'move',
		items: 'tr:not(tr:first)',
		handle: '.sortable'
	});
	
	$(".form-area-content:last").find("table:last").find("span").draggable({
		containment: '#' + $(".form-area-content:last").find("table:last").attr("id"),
		stack: '#' + $(".form-area-content:last").find("table:last").attr("id"),
		revert: true,
		stop: function(event, ui) {
    	  	$(this).css("z-index","auto");
    	  } 
	});
	$(".form-area-content:last").find("table:last").attr("id", "srcgenTableId" + ($(".form-area-content:last").find("table").length - 1));
	$.qp.initialAllPicker($(".form-area-content:last").find("table:last"));
	
	$.qp.initialAutoNumeric($(".form-area-content:last").find("table:last"));  
	
	$(".form-area-content:last").find("table:last").find("tr").find("td[class=srcgenControl]").find(".glyphicon-screenshot").droppable({
		accept: "#" + $(".form-area-content:last").find("table:last").attr("id") + " tr td[class=srcgenControl] .glyphicon-screenshot",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			swapColumn(event, ui, $(this));
		}
	});
	
	//get form index
	var formIndex = $(".form-area-content:last").find('input[name=screenFormFormSeqNo]').val();
	//update form index
	$(".form-area-content:last").find('.areaContent:last').find('input[name=formIndex]').val(formIndex);
	initBlurEventElement();
}

//Add row into table
function addRowTBJS(obj) {		
	var table = $(obj).prev("table");
	var tableId = $(table).attr("id");
	
	var columnSize = $(table).find("tr:first").children(":visible").length - 2;
	var rowCount = parseInt($(table).find("tr:last").attr("index")) + 1;
	
	 if (rowCount >=  TABLE_SETTING.rowLimit) {
		$.qp.alert(dbMsgSource["sc.screendesign.0168"] + " " + TABLE_SETTING.rowLimit);
		 return;
	 }
	
	var tr = "<tr index=\""+rowCount+"\">";
	for(i=0; i<columnSize*2; i++){
		tr += "<th index=\""+i+"\" class=\"\">" + 
					returnHiddenTHEmpty() + 
					"<span style=\"float: left; cursor: move;\" class=\"qp-glyphicon glyphicon glyphicon-screenshot\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></span><div class=\"dropLabel\">&nbsp;</div> " + 
				"</th>" + 
				"<td index=\""+(++i)+"\"><input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"groupTotalElement\"  /><input type=\"hidden\" name=\"enableGroup\"  /><div class=\"dropComponent\"><input type=\"hidden\" name=\"formElement\" value=\"\"/>&nbsp;</div></td>";
	}
	tr += returnSortRemoveTD() + "</tr>";
	
	$(tr).appendTo(table);
	
	$(table).find("tr:last").find("div[class='dropComponent']").droppable({
		accept: "#srcgenElements div, #srcgenAction div, #newDragElementTd div",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertComponent(event, ui, $(this));
		}
	});
	
	$(table).find("tr:last").find("div[class='dropLabel']").droppable({
		accept: "#divLabel",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertLable(event, ui, $(this));
		}
	});
		
	
	$(table).find("tr:last").find(".glyphicon-screenshot").draggable({
		containment: '#' + tableId,
		stack: '#' + tableId,
		revert: function(event, ui) {
				if ($(this).data("uiDraggable") != undefined && $(this).data("uiDraggable").originalPosition != undefined) {
					$(this).data("uiDraggable").originalPosition = {top : 0,left : 0};
				}	
				return true;				
			},
		stop: function(event, ui) {
  	  	$(this).css("z-index","auto");
  	  } 
	});
	
	$(table).find("tr:last").find(".glyphicon-screenshot").droppable({
		accept: "#" + tableId + " tr:not(:first) .glyphicon-screenshot",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			replaceElement(event, ui, $(this));
		}
	});
	
	//default group
	$(table).find("tbody td").droppable({
		accept: "#srcgenElements div, #srcgenAction div, #newDragElementTd div, #divCustomItem",
		activeClass: "state-droppable",
		drop: function(event, ui) {					
			insertComponent(event, ui, $(this));			
		}
	});
	$(table).find("tbody td").droppable( "option", "disabled", false );
	$(table).find("tbody td").find("input[name='enableGroup']").val(true);
	$(table).find("tbody td").find("input[name='groupDisplayType']").val(1);	
	
	//add style
	var headerStyle = $(table).closest(".areaContent").find("input[name=headerStyle]").val();
	var inputStyle = $(table).closest(".areaContent").find("input[name=inputStyle]").val();
	
	$(table).find("tr:last").find("th").attr("style", headerStyle);
	$(table).find("tr:last").find("td:not(.srcgenControl)").attr("style", inputStyle);
}

/**
 * 
 * @param td
 * @param mergeNew,mergeNew.rowspan,mergeNew.colspan  
 * @param mergeOld,mergeOld.rowspan, mergeOld.colspan 
 */
function merge(td, mergeNew, mergeOld) {
		
	//get table
	var table = $(td).closest("table");
	var tr = $(td).closest("tr");
	var colIndex = parseInt($(td).attr("index"));
	var rowIndex = parseInt($(tr).attr("index"));
	
	//get array new
	var arrMergeNew = [];	
	var rowspan = (mergeNew.rowspan == '')?0:mergeNew.rowspan;
	var rowspanStep = rowIndex;
	var stepRow = 0;
	
	var colspan = (mergeNew.colspan == '')?0:mergeNew.colspan;
	var colspanStep = 0;
	
	//init array
	do {
		arrMergeNew[rowspanStep] = [];		
		colspanStep = colIndex;
		var stepCol = 0;
		do {
			arrMergeNew[rowspanStep][colspanStep] = {row:parseInt(rowspanStep), col: parseInt(colspanStep)};
			colspanStep++;
			stepCol++;
		} while (stepCol < colspan);
		
		rowspanStep++;
		stepRow++;
	} while(stepRow < rowspan);
	
	
	var isValidCol = true;
	var maxCol = 0;
	var maxRow = 0;
	
	var colLastIndex = colIndex + ((mergeNew.colspan == 0)?1:mergeNew.colspan);
	//validate column
	var col = 0;
	for (j = colIndex; j < colLastIndex; j++) {
		if (j == colIndex) continue;
		var obj = arrMergeNew[rowIndex][j];
		var tdValid = $(table).find("tr[index="+obj.row+"]").find("td[index="+obj.col+"],th[index="+obj.col+"]");
		
		if (($(tdValid).attr("colspan") != undefined && $(tdValid).attr("colspan") != ''
			&& parseInt($(tdValid).attr("colspan"))) > 1
			|| ($(tdValid).attr("rowspan") != undefined && $(tdValid).attr("rowspan") != ''
				&& parseInt($(tdValid).attr("rowspan")) > 1)) {
			maxCol = col;
			isValidCol = false;
			break;
		}	
		col++;
	}

	if (!isValidCol) {
		if (maxCol > 0) {
			alert(dbMsgSource['sc.screendesign.0126'].replace("{0}", maxCol));
			return false;
		}		
	}
	var isValidRow = true;
	//validate row
	var row = 0;
	for (j = rowIndex; j < arrMergeNew.length; j++) {
		if (j == rowIndex) continue;			
		var obj = arrMergeNew[j][colIndex];
		var tdValid = $(table).find("tr[index="+obj.row+"]").find("td[index="+obj.col+"],th[index="+obj.col+"]");
		row++;
		if (($(tdValid).attr("colspan") != undefined && $(tdValid).attr("colspan") != ''
			&& parseInt($(tdValid).attr("colspan"))) > 1
			|| ($(tdValid).attr("rowspan") != undefined && $(tdValid).attr("rowspan") != ''
				&& parseInt($(tdValid).attr("rowspan")) > 1)) {
			maxRow = row;
			isValidRow = false;
			break;
		}			
	}
	
	if (!isValidRow) {		
		if (maxRow > 0) {
			alert(dbMsgSource['sc.screendesign.0125'].replace("{0}", maxRow));
			return false;
		}
	}
	
	var isValid = true;
	for (i = colIndex; i < colLastIndex; i++) {
		for (j = rowIndex; j < arrMergeNew.length; j++) {
			if (j == rowIndex && i == colIndex) continue;			
			var obj = arrMergeNew[j][i];
			var tdValid = $(table).find("tr[index="+obj.row+"]").find("td[index="+obj.col+"],th[index="+obj.col+"]");
			row++;
			if (($(tdValid).attr("colspan") != undefined && $(tdValid).attr("colspan") != ''
				&& parseInt($(tdValid).attr("colspan"))) > 1
				|| ($(tdValid).attr("rowspan") != undefined && $(tdValid).attr("rowspan") != ''
					&& parseInt($(tdValid).attr("rowspan")) > 1)) {
				isValid = false;
				break;
			}			
		}
		if (!isValid) {
			break;
		}		
	}
	
	if (!isValid) {		
		alert(dbMsgSource["sc.screendesign.0169"]);
		return false;			
	}
	
	//get array old	
	var arrMergeOld = [];	
	rowspan = (mergeOld.rowspan == '')?0:mergeOld.rowspan;
	rowspanStep = rowIndex;
	stepRow = 0;
	
	colspan = (mergeOld.colspan == '')?0:mergeOld.colspan;
	colspanStep = 0;
	if (mergeOld.rowspan > 0 || mergeOld.colspan > 0) {
	//init array
		do {
			arrMergeOld[rowspanStep] = [];		
			colspanStep = colIndex;
			var stepCol = 0;
			do {
				arrMergeOld[rowspanStep][colspanStep] = {row:parseInt(rowspanStep), col:parseInt(colspanStep)};
				colspanStep++;
				stepCol++;
			} while (stepCol < colspan);
			
			rowspanStep++;
			stepRow++;
		} while(stepRow < rowspan);		
	}	
	
	//get change		
	var maxRowIndex = (( mergeNew.rowspan >= mergeOld.rowspan)?arrMergeNew.length:arrMergeOld.length) - 1;	
	var maxColIndex = ((mergeNew.colspan >= mergeOld.colspan)?colIndex + mergeNew.colspan:colIndex + mergeOld.colspan) - 1;	
	if (mergeNew.colspan == 0 && mergeOld.colspan == 0) {
		maxColIndex = maxColIndex + 1;
	}
	
	var arrChange = [];
	for (i = rowIndex; i <= maxRowIndex; i++) {
		for (j = colIndex; j <= maxColIndex; j++) {
			if (i == rowIndex && j == colIndex) continue;
			
			var valueNew;
			if (i >= arrMergeOld.length || j >= arrMergeOld[i].length) {
				valueNew = {row:i, col:j, "status":"new"};
				arrChange.push(valueNew);
			}
			
			var valueOld;
			if (i >= arrMergeNew.length || j >= arrMergeNew[i].length) {
				valueOld = {row:i, col:j, "status":"old"};
				arrChange.push(valueOld);
			}			
		}
	}
	
	var invalid = true;
	for (i = 0; i < arrChange.length; i++) {
		var obj = arrChange[i];
		if (obj.status == "new") {
			var tdHide = $(table).find("tr[index="+obj.row+"]").find("td[index="+obj.col+"],th[index="+obj.col+"]");
			if (!$(tdHide).is(":visible")) {
				invalid = false;
				break;
			}
		}
	}
	
	if (!invalid) {		
		alert(dbMsgSource["sc.screendesign.0169"]);
		return false;			
	}
	
	for (i = 0; i < arrChange.length; i++) {
		var obj = arrChange[i];
		if (obj.status == "new") {
			var tdHide = $(table).find("tr[index="+obj.row+"]").find("td[index="+obj.col+"],th[index="+obj.col+"]");
			$(tdHide).hide();
		} else {
			var tdShow = $(table).find("tr[index="+obj.row+"]").find("td[index="+obj.col+"],th[index="+obj.col+"]");
			if ($(tdShow).prop("tagName") == "TD") {
				if ($(table).is("table[class*=qp-table-form]")) {
					$(tdShow).html("<input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"groupTotalElement\"  /><input type=\"hidden\" name=\"enableGroup\"  /><div class=\"dropComponent\"><input type=\"hidden\" name=\"formElement\" value=\"\">&nbsp;</div>");
				} else if ($(table).is("table[class*=qp-table-list-none-action]")) {
					$(tdShow).html("<span style=\"float: left; cursor: move;\" class=\"qp-glyphicon glyphicon glyphicon-screenshot\" title=\"Move\"></span><input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"groupTotalElement\"  /><input type=\"hidden\" name=\"enableGroup\"  /><div class=\"dropComponent\"><input type=\"hidden\" name=\"formElement\" value=\"\">&nbsp;</div>");
				}
			} else if ($(tdShow).prop("tagName") == "TH"){
				$(tdShow).html("<span style=\"float: left; cursor: move;\" class=\"qp-glyphicon glyphicon glyphicon-screenshot\" title=\"Move\"></span>"+returnHiddenTHEmpty()+"<div class=\"dropLabel\">&nbsp;</div>");
			}
			$(tdShow).show();
		}
	}
	
	if (mergeOld.rowspan > 1 && mergeNew.rowspan == 0) {
		mergeNew.rowspan = 1;
	}
	if (mergeNew.rowspan == '' || mergeNew.rowspan == 0) {
		mergeNew.rowspan = 1;
	}
	$(td).attr("rowspan", mergeNew.rowspan);

	if (mergeOld.colspan > 1 && mergeNew.colspan == 0) {
		mergeNew.colspan = 1;
	}
	
	if (mergeNew.colspan == '' || mergeNew.colspan == 0) {
		mergeNew.colspan = 1;
	}
	
	$(td).attr("colspan", mergeNew.colspan);
	
	return true;
}

function mergeTable(obj, data, rowspan, colspan) {
	
	if ((rowspan > 1 || colspan > 1) && (data.rowspan != rowspan || data.colspan != colspan)) {
		if (!$.qp.confirm(dbMsgSource['sc.screendesign.0413'])) {
			return false;
		}
	}
	
	if (rowspan.length == 0 && colspan.length == 0 && data.rowspan.length == 0 && data.colspan.length == 0) return true;
	try {
		if (rowspan.length == 0) rowspan = "1";
		rowspan = parseInt(rowspan);
	} catch (ex) {
		rowspan = 1;
	}
	
	try {
		if (colspan.length == 0) colspan = "1";
		colspan = parseInt(colspan);
	} catch (ex) {
		colspan = 1;
	}
	
	var element = {};
	
	if ($(obj).closest("#" + $(obj).closest("table").attr("id") + " td,th") != undefined && $(obj).closest("#" + $(obj).closest("table").attr("id") + " td,th").prop("tagName") == "TD") {
		element = $(obj).closest("td");		
	} else if ($(obj).closest("#" + $(obj).closest("table").attr("id") + " td,th") != undefined && $(obj).closest("#" + $(obj).closest("table").attr("id") + " td,th").prop("tagName") == "TH") {
		element = $(obj).closest("th");	
	}
	
	//validate
	var table = $(element).closest("table");
	var elementIndex = parseInt($(element).attr("index"));
	var tr = $(element).closest("tr");
	var trOfTHead = $(table).find("thead").find("tr").size();
	
	var trIndex = 0;
	if ($(table).hasClass('qp-table-list-none-action') && $(tr).parent("tbody").size() > 0) {
		trIndex = parseInt($(tr).attr("index")) - trOfTHead;
	} else {
		trIndex = parseInt($(tr).attr("index"));
	}
	
	var columnLength = $(tr).children().length - 2; 
	
	if ($(table).attr("class") == "table table-bordered qp-table-list-none-action") {
		columnLength = $(tr).children().length; 
	}
	
	if(data.elementtype == 1 && data.rowspan != rowspan){
		if(rowspan > 1){
			// validate
			var trLength = $(table).find("tr").length;
			if(rowspan > (trLength - trIndex)){
				$.qp.showAlertModal(dbMsgSource['sc.screendesign.0125'].replace("{0}", (trLength - trIndex)));
				return false;
			}
		}
	}
	
	var rowIndex = 0 ;
	
	if ($(table).hasClass('qp-table-list-none-action')) {
		rowIndex = trIndex;
	} else {
		rowIndex = trIndex + 1;
	}
	
	var colIndex = elementIndex;
	if(rowspan > 1){
		var trLength = $(table).find("tr").length;
		
		if ($(table).hasClass('qp-table-list-none-action') && $(tr).parent("thead").size() > 0) {
			trLength = $(table).find('thead').find("tr").length;
		} else if ($(table).hasClass('qp-table-list-none-action') && $(tr).parent("tbody").size() > 0) {
			trLength = $(table).find('tbody').find("tr").length;
		}  else if (!$(table).hasClass('qp-table-list-none-action') && $(tr).parent("tbody").size() > 0) {
			trLength = $(table).find('tbody').find("tr").length;
		} else if (!$(table).hasClass('qp-table-list-none-action') && $(tr).parent("thead").size() > 0) {
			trLength = $(table).find('thead').find("tr").length;
		}
		
		/*if ($(table).hasClass('qp-table-list-none-action') && $(tr).parent("tbody").size() > 0) {
			trLength = $(table).find('thead').find("tr").length;
		} else {
			trLength = $(table).find('thead').find("tr").length;
		}*/
		
		if(trIndex + rowspan > trLength){
			$.qp.showAlertModal(dbMsgSource['sc.screendesign.0125'].replace("{0}", (trLength - rowIndex)));
			return false;
		}
	}
	//validate max colspan
	if(colspan > 1){
		if(colspan > columnLength - elementIndex){
			$.qp.showAlertModal(dbMsgSource['sc.screendesign.0126'].replace("{0}", (columnLength - elementIndex)));
			return false;
		}
	}
	
	//remove swap
	if (rowspan > 1 || colspan > 1) {
		$(element).find(".glyphicon-screenshot").hide();
	} else {
		$(element).find(".glyphicon-screenshot").show();
	}
	
	var mergeNew = {rowspan:parseInt((rowspan != undefined && rowspan != '')?rowspan:0), colspan:parseInt((colspan != undefined && colspan != '')?colspan:0)};
	var mergeOld = {rowspan:parseInt((data.rowspan != undefined &&  data.rowspan != '')?data.rowspan:0), colspan:parseInt((data.colspan != undefined && data.colspan != '')?data.colspan:0)};
	
	if (!merge(element, mergeNew, mergeOld)) {
		return false;
	}
	
	reIndexCol($(table));
	$(table).find("div[class='dropComponent']").each(function (){
		$(this).droppable({
			accept: "#srcgenElements div, #srcgenAction div, #newDragElementTd div",
			activeClass: "state-droppable",
			drop: function(event, ui) {													
				insertComponent(event, ui, $(this));

			}
		});
	});
	
	$(table).find("div[class='dropLabel']").each(function (){
		if($(this).children().length == 0){
			$(this).droppable({
				accept: "#divLabel",
				activeClass: "state-droppable",
				drop: function(event, ui) {										
					insertLable(event, ui, $(this));
				}
			});
		}
	});
	
	//remove swap
	if ($(element).prop("tagName") == "TD" && $(element).attr("index") > 0) {
		var swapIndex = parseInt($(element).attr("index") - 1);
		var rowIndex = parseInt($(tr).attr("index"));
		var currentMaxRowIndex =parseInt($(tr).attr("index"));
		
		var rowCount = 1;
		while(rowCount < rowspan) {
			currentMaxRowIndex++;
			rowCount++;
		}
		
		var rowSpanTem = (rowspan >= mergeOld.rowspan)?rowspan:mergeOld.rowspan;
		do {
			var nextTD = $(table).find("tr[index="+rowIndex+"]").find("td[index="+parseInt($(element).attr("index"))+"]");
			if (rowIndex <= currentMaxRowIndex) {
				var nextColspan = $(nextTD).attr("colspan");
				var nextRowspan = $(nextTD).attr("rowspan");
				
				if($(nextTD).attr("colspan") == undefined ||  $(nextTD).attr("colspan") == '' ||  $(nextTD).attr("colspan") == 0) {
					nextColspan = 1;
				}
				
				if($(nextTD).attr("rowspan") == undefined ||  $(nextTD).attr("rowspan") == '' ||  $(nextTD).attr("rowspan") == 0) {
					nextRowspan = 1;
				}
				
				if (rowIndex == parseInt($(tr).attr("index")) && nextColspan == 1 && nextRowspan == 1) {
					$(table).find("tr[index="+rowIndex+"]").find("th[index="+swapIndex+"]").find(".glyphicon-screenshot").show();
				} else {
					$(table).find("tr[index="+rowIndex+"]").find("th[index="+swapIndex+"]").find(".glyphicon-screenshot").hide();
				}
			} else {
				$(table).find("tr[index="+rowIndex+"]").find("th[index="+swapIndex+"]").find(".glyphicon-screenshot").show();
			}
			rowIndex++;
			rowSpanTem--;
		} while(rowSpanTem > 0);
		
		$(element).find("input[name=formElement]").each(function(){
			var value = $(this).val();
			var dataJson = convertToJson(value);
			if (dataJson.colspan != undefined) {
				dataJson.colspan = colspan;
			} else {
				dataJson['colspan'] = colspan;
			}
			
			if (dataJson.rowspan != undefined) {
				dataJson.rowspan = rowspan;
			} else {
				dataJson['rowspan'] = rowspan;
			}
			
			$(this).val(convertToString(dataJson));
		});
		
	}
	
	 var tableId = $(table).attr('id');
	 
	 $(table).find(".glyphicon-screenshot").draggable({
			containment: '#' + tableId,
			stack: '#' + tableId,
			revert:  function(event, ui) {
				if ($(this).data("uiDraggable") != undefined && $(this).data("uiDraggable").originalPosition != undefined) {
					$(this).data("uiDraggable").originalPosition = {top : 0,left : 0};
				}	
				return true;		
			},
			stop: function(event, ui) {
	    	  	$(this).css("z-index","auto");
	    	  } 
	 });
	 
	 $(table).find("th .glyphicon-screenshot").droppable({
		accept: "#" + tableId + " th .glyphicon-screenshot",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			replaceElement(event, ui, $(this));
		}
	 });
	 
	 $(table).find("td .glyphicon-screenshot").droppable({
			accept: "#" + tableId + " td .glyphicon-screenshot",
			activeClass: "state-droppable",
			drop: function(event, ui) {
				replaceElement(event, ui, $(this));
			}
	});
	$(table).find("tr").find("td[class=srcgenControl]").find(".glyphicon-screenshot").droppable({
		accept: "#" + tableId + " tr td[class=srcgenControl] .glyphicon-screenshot",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			swapColumn(event, ui, $(this));
		}
	});
	if ($(table).is("table[class*=qp-table-form]")) {
		checkColRowSpan($(table));
		checkSwap($(table));
	} else if ($(table).is("table[class*=qp-table-list-none-action]")) {
		checkColRowSpan($(table));
		checkSwapListTable($(table));
	}
	 
	return true;
}

function checkSwapListTable(table) {
	$(table).find("tr").each(function(i){
		$(this).find("td,th").each(function(j){
			var rowspan = $(this).attr("rowspan");
			var colspan = $(this).attr("colspan");
			
			if (rowspan > 1 || colspan > 1) {
				$(this).find(".glyphicon-screenshot").hide();
			} else {
				$(this).find(".glyphicon-screenshot").show();
			}
			
			
		});
	});
}

function checkSwap(table) {
	$(table).find("tr:not(:first)").each(function(i){
		$(this).find("td").each(function(j){
			var rowspan = $(this).attr("rowspan");
			var colspan = $(this).attr("colspan");
			
			if (rowspan == undefined || rowspan == '' || rowspan == 0) {
				rowspan = 1;
			}
			
			if (colspan == undefined || colspan == '' || colspan == 0) {
				colspan = 1;
			}
			
			/*if (rowspan == 1 && colspan == 1) {
				return;
			}*/
			
			if (rowspan > 1 || colspan > 1) {
				$(this).find(".glyphicon-screenshot").remove();
			}
			
			var swapIndex = parseInt($(this).attr("index") - 1);
			var rowIndex = parseInt($(this).closest("tr").attr("index"));
			var currentMaxRowIndex =parseInt($(this).closest("tr").attr("index"));
			
			var rowCount = 1;
			while(rowCount < rowspan) {
				currentMaxRowIndex++;
				rowCount++;
			}
			
			var rowSpanTem = rowspan;
			do {
				var nextTD = $(table).find("tr[index="+rowIndex+"]").find("td[index="+parseInt($(this).attr("index"))+"]");
				if (rowIndex <= currentMaxRowIndex) {
					var nextColspan = $(nextTD).attr("colspan");
					var nextRowspan = $(nextTD).attr("rowspan");
					
					if($(nextTD).attr("colspan") == undefined ||  $(nextTD).attr("colspan") == '' ||  $(nextTD).attr("colspan") == 0) {
						nextColspan = 1;
					}
					
					if($(nextTD).attr("rowspan") == undefined ||  $(nextTD).attr("rowspan") == '' ||  $(nextTD).attr("rowspan") == 0) {
						nextRowspan = 1;
					}
					
					if (rowIndex == parseInt($(this).closest("tr").attr("index")) && nextColspan == 1 && nextRowspan == 1 && $(nextTD).is(":visible")) {
						$(table).find("tr[index="+rowIndex+"]").find("th[index="+swapIndex+"]").find(".glyphicon-screenshot").show();
					} else {
						$(table).find("tr[index="+rowIndex+"]").find("th[index="+swapIndex+"]").find(".glyphicon-screenshot").hide();
					}
				} else {
					$(table).find("tr[index="+rowIndex+"]").find("th[index="+swapIndex+"]").find(".glyphicon-screenshot").show();
				}
				rowIndex++;
				rowSpanTem--;
			} while(rowSpanTem > 0);
		});
	});
}

function checkColRowSpan(table) {
	
	$(table).find("tbody, thead").find("tr").each(function(rowIndex){
		if (rowIndex != 0) {
			var colRowSize = $(this).children(":visible").length - 2;
			var isValid = true;
			$(this).find("td:visible,th:visible").each(function(){
				var td = $(this);
				if ($(td).attr("rowspan") != undefined && parseInt($(td).attr("rowspan")) > 1) {
					  isValid = false;
					  return;
				}
			});
			
			  
			if (!isValid) {
				$(this).find(".glyphicon-sort").hide();
				$(this).find(".glyphicon-sort").closest("td").removeClass("sortable");
				$(this).find(".glyphicon-minus").hide();
				$(this).addClass("disableSort");
			} else {
				$(this).find(".glyphicon-sort").show();
				$(this).find(".glyphicon-minus").show();
				$(this).find(".glyphicon-sort").closest("td").addClass("sortable");
				$(this).removeClass("disableSort");
				var columnSize = parseInt($(table).find("tr:first").children().length - 2);
				
				var totalColspan = 0;
				$(this).children().each(function(){
					if ($(this).attr("colspan") != undefined && parseInt($(this).attr("colspan")) > 0) {
						totalColspan += parseInt($(this).attr("colspan")) - 1;
					}
				});
				
				if ((colRowSize + totalColspan)  != columnSize*2) {
					$(this).find(".glyphicon-sort").hide();
					$(this).find(".glyphicon-sort").closest("td").removeClass("sortable");
					$(this).find(".glyphicon-minus").hide();
					$(this).addClass("disableSort");
				} else {
					$(this).find(".glyphicon-sort").show();
					$(this).find(".glyphicon-minus").show();
					$(this).find(".glyphicon-sort").closest("td").addClass("sortable");
					$(this).removeClass("disableSort");
				}
			}
		}
	});
}

//Remove row from table
function  removeRowTBJS(obj) {
	var colRowSize = $(obj).closest("tr").children(":visible").length - 2; 
	var isValid = true;
	for (i =0; i < colRowSize; i++) {
		var td = $(obj).closest("tr").find("td[index="+i+"], th[index="+i+"]");
		if ($(td).attr("rowspan") != undefined && parseInt($(td).attr("rowspan")) > 1) {
			  isValid = false;
		}
	}
	  
	if (!isValid) {
		alert(dbMsgSource['sc.screendesign.0170']);
		return false;
	}
	  
	var columnSize = parseInt($(obj).closest("table").find("tr:first").children().length - 2);
	
	var totalColspan = 0;
	$(obj).closest("tr").children().each(function(){
		if ($(this).attr("colspan") != undefined && parseInt($(this).attr("colspan")) > 0) {
			totalColspan += parseInt($(this).attr("colspan")) - 1;
		}
	});
	
	if ((colRowSize + totalColspan)  != columnSize*2) {
		alert(dbMsgSource['sc.screendesign.0170']);
		return false;
	}
	  
	var table = $(obj).closest("table");
  $(obj).closest("tr").remove();
  
  if($(table).find("tr").size() == 1){
  	  $(table).closest('div .areaContent').remove();
  	  reloadDroppableSpan();
  }
  
  reIndexTable($(table));
}
function reWidthTable(table) {
	$(table).find("colgroup").empty();	 
	 var columnSize = $(table).find("tr:last").children().length - 2;
	 var width = parseInt(100/columnSize);
	 
	 
	var colgroup = "";
	var parameters="";
	for (var i = 0; i < columnSize; i++) {
		colgroup += "<col width=\""+width+"%\">";
		parameters += width+"%,";
	}
	 colgroup += "";
	 $(table).find("colgroup").append(colgroup);
	 
	 //set formTableColumnSize
	 $(table).parents(".areaContent").find("[name=formTableColumnSize]").val(parameters);
}

function reWidthTableList(table) {
	$(table).find("colgroup").empty();	 
	 var columnSize = $(table).find("tr:last").children().length;
	 var width = parseInt(100/columnSize);
	 
	 
	var colgroup = "";
	var parameters="";
	for (var i = 0; i < columnSize; i++) {
		colgroup += "<col width=\""+width+"%\">";
		parameters += width+"%,";
	}
	 colgroup += "";
	 $(table).find("colgroup").append(colgroup);
	//set formTableColumnSize
	 $(table).parents(".areaContent").find("[name=formTableColumnSize]").val(parameters);
}

function checkColRowSpanAddRemoveCol(obj) {
	var index = $(obj).closest("td").index() * 2;
	var secondColumnIndex = index + 1;
	var result = true;
	
	$(obj).closest("table").find("tr").each(function(i){
		if (i > 0) {
			if ($(this).find("td[index="+index+"], th[index="+index+"]").attr("colspan")  > 1 
					|| $(this).find("td[index="+index+"], th[index="+index+"]").attr("rowspan") > 1) {
				result = false;
				return false;
			}
			
			if ($(this).find("td[index="+secondColumnIndex+"], th[index="+secondColumnIndex+"]").attr("colspan")  > 1 
					|| $(this).find("td[index="+secondColumnIndex+"], th[index="+secondColumnIndex+"]").attr("rowspan") > 1) {
				result = false;
				return false;
			}
			
			if ($(this).find("td[index="+index+"], th[index="+index+"]").css("display") == "none" 
					|| $(this).find("td[index="+secondColumnIndex+"], th[index="+secondColumnIndex+"]").css("display") == "none") {
				result = false;
				return false;
				
			}
		}
	});
	
	return result;
}

function checkColRowSpanAddCol(obj) {
	var index = $(obj).closest("td").index() * 2;
	var secondColumnIndex = index + 1;
	var result = true;
	
	$(obj).closest("table").find("tr").each(function(i){
		if (i > 0) {
			var colSize = $(this).find("td,th").length - 2;
			if (secondColumnIndex + 1 < colSize) {
				if ($(this).find("td[index="+index+"], th[index="+index+"]").attr("colspan")  > 1 
						|| $(this).find("td[index="+index+"], th[index="+index+"]").attr("rowspan") > 1) {
					result = false;
					return false;
				}
				
				if ($(this).find("td[index="+secondColumnIndex+"], th[index="+secondColumnIndex+"]").attr("colspan")  > 1 
						|| $(this).find("td[index="+secondColumnIndex+"], th[index="+secondColumnIndex+"]").attr("rowspan") > 1
						|| !($(this).find("td[index="+secondColumnIndex+"], th[index="+secondColumnIndex+"]").is(":visible"))) {
					result = false;
					return false;
				}
			}
		}
	});
	
	return result;
}

function checkColRowSpanAddRemoveColList(obj) {
	var index = $(obj).closest("td").index();
	var secondColumnIndex = index + 1;
	
	var result = true;
	
	$(obj).closest("table").find("tr").each(function(i){
		if ($(this).find("td[index="+index+"], th[index="+index+"]").attr("colspan")  > 1 
				|| $(this).find("td[index="+index+"], th[index="+index+"]").attr("rowspan") > 1) {
			result = false;
			return false;
		}
		
		if ($(this).find("td[index="+secondColumnIndex+"], th[index="+secondColumnIndex+"]").attr("colspan")  > 1 
				|| $(this).find("td[index="+secondColumnIndex+"], th[index="+secondColumnIndex+"]").attr("rowspan") > 1) {
			result = false;
			return false;
		}
		
		if ($(this).find("td[index="+index+"], th[index="+index+"]").css("display") == "none" 
				|| $(this).find("td[index="+secondColumnIndex+"], th[index="+secondColumnIndex+"]").css("display") == "none") {
			result = false;
			return false;
			
		}
	});
	
	return result;
}

function checkColRowSpanAddColList(obj) {
	var index = $(obj).closest("td").index();
	var secondColumnIndex = index + 1;
	var result = true;
	
	$(obj).closest("table").find("thead, tbody").find("tr").each(function(i){
		var colSize = $(this).find("td,th").length;
		if (secondColumnIndex < colSize) {
			if ($(this).find("td[index="+index+"], th[index="+index+"]").attr("colspan")  > 1 
					|| $(this).find("td[index="+index+"], th[index="+index+"]").attr("rowspan") > 1) {
				result = false;
				return false;
			}
			
			if ($(this).find("td[index="+secondColumnIndex+"], th[index="+secondColumnIndex+"]").attr("colspan")  > 1 
					|| $(this).find("td[index="+secondColumnIndex+"], th[index="+secondColumnIndex+"]").attr("rowspan") > 1
					|| !($(this).find("td[index="+secondColumnIndex+"], th[index="+secondColumnIndex+"]").is(":visible"))) {
				result = false;
				return false;
			}
		}
	});
	
	return result;
}

//Add column into Entity Table
function addColumnJS(obj) {	
	
	if (!checkColRowSpanAddCol($(obj))) {
		alert(dbMsgSource['sc.screendesign.0172']);
		return;
	}
	
	 var index = $(obj).closest("td").index();
	 
	 var tr = $(obj).closest("tr");
	 if (($(tr).children().length-2) * 2>=  TABLE_SETTING.columnLimit) {
		$.qp.alert(dbMsgSource['sc.screendesign.0171'] + " " + TABLE_SETTING.columnLimit);
		 return;
	 }
	 var table = $(obj).closest("table");
	 
	 var td = returnAddRemoveTD(0,0);
	 var thIndex = 0;
	 var tdIndex = 0;
	 $(table).find("tr").each( function(i){
		 if(i == 0){
			$(this).find("td:eq(" + index + ")").after(td);
		 }else {
			 if(i == 1){
				 $(this).find("td:eq(" + index + ")").after(returnElementTHBlank(100) + "<td index=\""+tdIndex+"\" width=\"100px\"><input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"groupTotalElement\"  /><input type=\"hidden\" name=\"enableGroup\"  /><div class=\"dropComponent\"><input type=\"hidden\" name=\"formElement\" value=\"\">&nbsp;</div></td>");
			 }else {
				 $(this).find("td:eq(" + index + ")").after(returnElementTHBlank(0) + "<td index=\""+tdIndex+"\"><input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"groupTotalElement\"  /><input type=\"hidden\" name=\"enableGroup\"  /><div class=\"dropComponent\"><input type=\"hidden\" name=\"formElement\" value=\"\">&nbsp;</div></td>");
			 }
		 }
	 });
	 	
	 reIndexCol($(table));
	 // re width
	 reWidthTable($(table));
	 
	 var tableId = $(table).attr('id');
	 $(table).find("span[class*='glyphicon-screenshot']").draggable({
			containment: '#' + tableId,
			stack: '#' + tableId,
			revert: function(event, ui) {$(this).data("uiDraggable").originalPosition = {top : 0,left : 0};return true;},
			stop: function(event, ui) {
	    	  	$(this).css("z-index","auto");
	    	  } 
	 });
	 
	$(table).find("div[class='dropComponent']").droppable({
		accept: "#srcgenElements div, #srcgenAction div, #newDragElementTd div",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertComponent(event, ui, $(this));
		}
	});
	
	$(table).find("div[class='dropLabel']").droppable({
		accept: "#divLabel",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertLable(event, ui, $(this));
		}
	});
	
	$(table).find("tr").find("td[class=srcgenControl]").find(".glyphicon-screenshot").droppable({
		accept: "#" + tableId + " tr td[class=srcgenControl] .glyphicon-screenshot",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			swapColumn(event, ui, $(this));
		}
	});
	
	//default group
	$(table).find("tbody td").droppable({
		accept: "#srcgenElements div, #srcgenAction div, #newDragElementTd div,#divCustomItem",
		activeClass: "state-droppable",
		drop: function(event, ui) {					
			insertComponent(event, ui, $(this));			
		}
	});
	$(table).find("tbody td").droppable( "option", "disabled", false );
	$(table).find("tbody td").find("input[name='enableGroup']").val(true);
	$(table).find("tbody td").find("input[name='groupDisplayType']").val(1);
	
	reloadDroppableSpan();
	
	//add style
	var headerStyle = $(table).closest(".areaContent").find("input[name=headerStyle]").val();
	var inputStyle = $(table).closest(".areaContent").find("input[name=inputStyle]").val();
	
	$(table).find("tr").find("th").attr("style", headerStyle);
	$(table).find("tr").find("td:not(.srcgenControl)").attr("style", inputStyle);
}

//Remove column from table
function  removeColumnJS(obj) {

	if (!checkColRowSpanAddRemoveCol($(obj))) {
		alert(dbMsgSource['sc.screendesign.0173']);
		return;
	}
	
  var index = $(obj).closest("td").index();
  var tr = $(obj).closest("tr");
  var table = $(obj).closest("table");
  
  $(table).find("tr").each( function(i){
		 if(i == 0){
			$(this).find("td:eq(" + index + ")").remove();
		 }else {
			$(this).find("td:eq("+index+"),th:eq("+index+")").remove();			
		 }
	 });
  
  $(tr).find("td").each(function (i){
		$(this).attr('index', i);
	});
  
  if($(table).find("tr:eq(0)").find("td").length == 2){
  	  $(table).closest('div .areaContent').remove();
  	  reloadDroppableSpan();
  }  
  reIndexCol($(table));
  reWidthTable($(table));
}

//Add column into List Entities Table
function addColumnTBJS(obj) {	
	if (!checkColRowSpanAddColList($(obj))) {
		alert(dbMsgSource['sc.screendesign.0172']);
		return;
	}
	
	 var index = $(obj).closest("td").index();
	 var tr = $(obj).closest("tr");
	 var table = $(obj).closest("table");
	
	 if ($(tr).children().length >=  TABLE_SETTING.columnLimit) {
		$.qp.alert(dbMsgSource['sc.screendesign.0171'] +  " " + TABLE_SETTING.columnLimit);
		 return;
	 }
	 
	 $(table).find("thead").find("tr").each(function (){
		 $(this).find("th:eq("+index+")").after("<th class=\"style-header-table\"><span style=\"float: left; cursor: move;\" class=\"qp-glyphicon glyphicon glyphicon-screenshot\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></span>"+returnHiddenTHEmpty()+"<div class=\"dropLabel\">&nbsp;</div></th>");
	 });
	 $(table).find("tbody").find("tr").each(function (){
		 $(this).find("td:eq("+index+")").after("<td><span style=\"float: left; cursor: move;\" class=\"qp-glyphicon  glyphicon glyphicon-screenshot\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></span><input type=\"hidden\" name=\"groupDisplayType\"  /><input type=\"hidden\" name=\"groupTotalElement\"  /><input type=\"hidden\" name=\"enableGroup\"  /><div class=\"dropComponent\"><input type=\"hidden\" name=\"formElement\" value=\"\">&nbsp;</div></td>");
	 });
	 
	 var td = returnAddRemoveTD(1,0);
	 	
	 $(table).find("tfoot").find("tr:eq(0)").find("td:eq("+index+")").after(td);
	 
	 $(tr).find("td").each(function (i){
		$(this).attr('index', i);
	 });
	 
	 $(table).find("thead").find("tr").each(function (){
		  $(this).find("th").each(function (i){
				$(this).attr('index', i);
		  }); 
	 });
	 
	 $(table).find("tbody").find("tr").each(function (){
		  $(this).find("td").each(function (i){
				$(this).attr('index', i);
		  }); 
	 });

	/*$(table).find("div[class='dropComponent']").droppable({
		accept: "#srcgenElements div, #srcgenAction div, #newDragElementTd div, #divCustomItem",
		activeClass: "state-droppable",
		drop: function(event, ui) {
				insertComponent(event, ui, $(this));			
		}
	});*/

	$(table).find("div[class='dropLabel']").droppable({
		accept: "#divLabel",
		activeClass: "state-droppable",
		drop: function(event, ui) {										
			insertLable(event, ui, $(this));
		}
	});
	
	 var tableId = $(table).attr('id');
	 reWidthTableList($("#" + tableId));
	 $(table).find("span[class*='glyphicon-screenshot']").draggable({
			containment: '#' + tableId,
			stack: '#' + tableId,
			revert:function(event, ui) {
				if ($(this).data("uiDraggable") != undefined && $(this).data("uiDraggable").originalPosition != undefined) {
					$(this).data("uiDraggable").originalPosition = {top : 0,left : 0};
				}
				return true;
			},
			stop: function(event, ui) {
	    	  	$(this).css("z-index","auto");
	    	  } 
	 });
	
	 $(table).find("span[class*='glyphicon-screenshot']").droppable({
		accept: "#" + tableId + " span[class*='glyphicon-screenshot']",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			replaceElementList(event, ui, $(this));
		}
	 });
	 
	 $(table).find("tbody").find("span[class*='glyphicon-screenshot']").droppable({
			accept: "#" + tableId + " tbody span[class*='glyphicon-screenshot']",
			activeClass: "state-droppable",
			drop: function(event, ui) {
				replaceElementList(event, ui, $(this));
			}
		 });
	 
	 $(table).find("thead").find("span[class*='glyphicon-screenshot']").droppable({
			accept: "#" + tableId + " thead span[class*='glyphicon-screenshot']",
			activeClass: "state-droppable",
			drop: function(event, ui) {
				replaceElementList(event, ui, $(this));
			}
		 });
	 
	//default group
	$(table).find("tbody td").droppable({
		accept: "#srcgenElements div, #srcgenAction div, #newDragElementTd div, #divCustomItem",
		activeClass: "state-droppable",
		drop: function(event, ui) {					
			insertComponent(event, ui, $(this));			
		}
	});
	$(table).find("tbody td").droppable( "option", "disabled", false );
	$(table).find("tbody td").find("input[name='enableGroup']").val(true);
	$(table).find("tbody td").find("input[name='groupDisplayType']").val(1);
	
	//add style
	var headerStyle = $(table).closest(".areaContent").find("input[name=headerStyle]").val();
	var inputStyle = $(table).closest(".areaContent").find("input[name=inputStyle]").val();
	if(headerStyle != null && headerStyle != undefined && headerStyle != "") {
		$(table).find("thead th").attr("style", headerStyle);
	}
	if(inputStyle != null && inputStyle != undefined && inputStyle != "") {
		$(table).find("tbody td").attr("style", inputStyle);
	}
	
}

//Remove column from table List
function  removeColumnTBJS(obj) {
	

	if (!checkColRowSpanAddRemoveColList($(obj))) {
		alert(dbMsgSource['sc.screendesign.0173']);
		return;
	}
	
  var index = $(obj).closest("td").index();
  var tr = $(obj).closest("tr");
  var table = $(obj).closest("table");
  
  $(table).find("thead").find("tr").each(function (){
	  $(this).find("th:eq("+index+")").remove(); 
  });
  $(table).find("tbody").find("tr").each(function (){
	  $(this).find("td:eq("+index+")").remove(); 
  });
  $(obj).closest("td").remove();
  
  $(tr).find("td").each(function (i){
		$(this).attr('index', i);
  });
  
  $(table).find("thead").find("tr").each(function (){
	  $(this).find("th").each(function (i){
			$(this).attr('index', i);
	  }); 
  });
  
  $(table).find("tbody").find("tr").each(function (){
	  $(this).find("td").each(function (i){
			$(this).attr('index', i);
	  }); 
  });
	
  if($(table).find("tr:eq(0)").find("th").length == 0){
	  $(table).closest('div .areaContent').remove();
	  reloadDroppableSpan();
  }
  reWidthTableList($(table));
}

function deleteTable(obj){
	if ($(obj).closest(".area-tab") != undefined && $(obj).closest(".area-tab").length > 0) {
		alert(dbMsgSource['sc.screendesign.0379']);
		return false;
	} else {
		if(confirm(fcomMsgSource['inf.sys.0014'])){ 
			$(obj).closest('div .areaContent').remove();
			
			reloadDroppableSpan();
			
			//delete not used form
			arrangeFormArea();
		} else {
			return false;
		}
	}
}

function deleteTableSearch(obj){
	if(confirm(fcomMsgSource['inf.sys.0014'])){ 
		$(obj).closest('div .areaContentSearch').remove();
		
		reloadDroppableSpan();
	} else {
		return false;
	}
}

function reloadDroppable(table){
	var tableId = $(table).attr('id');
	$(table).find("tr").find("td").find(".glyphicon-screenshot").droppable({
		accept: "#" + tableId + " tr td .glyphicon-screenshot",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			if($(this).prev("input").length == 0){
				replaceElementList(event, ui, $(this));
			} else {
				replaceElement(event, ui, $(this));
			}
		}
	});
	
	$(table).find("tr").find("th").find(".glyphicon-screenshot").droppable({
		accept: "#" + tableId + " tr th .glyphicon-screenshot",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			if($(this).prev("input").length == 0){
				replaceElementList(event, ui, $(this));
			} else {
				replaceElement(event, ui, $(this));
			}
		}
	});
	
	$(table).find("tr").find("td[class=srcgenControl]").find(".glyphicon-screenshot").droppable({
		accept: "#" + tableId + " tr td[class=srcgenControl] .glyphicon-screenshot",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			swapColumn(event, ui, $(this));
		}
	});
}

function reloadDroppableSpan(){
	$(".form-area-content").find("table").each(function (i){
		$(this).attr("id", "srcgenTableId"+i);
		var tableId = $(this).attr('id');
		
		$(this).find(".glyphicon-screenshot").draggable({
			containment: "#" + tableId,
			stack: "#" + tableId,
			revert: true,
			stop: function(event, ui) {
	    	  	$(this).css("z-index","auto");
	    	  } 
		});
		
		$(this).find("tr").find("td").find(".glyphicon-screenshot").droppable({
			accept: "#" + tableId + " tr td .glyphicon-screenshot",
			activeClass: "state-droppable",
			drop: function(event, ui) {
				if($(this).prev("input").length == 0){
					replaceElementList(event, ui, $(this));
				} else {
					replaceElement(event, ui, $(this));
				}
			}
		});
		
		$(this).find("tr").find("th").find(".glyphicon-screenshot").droppable({
			accept: "#" + tableId + " tr th .glyphicon-screenshot",
			activeClass: "state-droppable",
			drop: function(event, ui) {
				if($(this).prev("input").length == 0){
					replaceElementList(event, ui, $(this));
				} else {
					replaceElement(event, ui, $(this));
				}
			}
		});
		
		$(this).find("tr").find("td[class=srcgenControl]").find(".glyphicon-screenshot").droppable({
			accept: "#" + tableId + " tr td[class=srcgenControl] .glyphicon-screenshot",
			activeClass: "state-droppable",
			drop: function(event, ui) {
				swapColumn(event, ui, $(this));
			}
		});
	});
}

function srcgenPreview(){
	$("#srcgenAreaTemplate").hide();
	$("#srcgenButtonPanel").hide();
	$("#srcgenScreenDesignInfo").hide();
	$("#srcgenControlElement").hide();
	$("#allDragDropContent").find(".srcgenControl, .srcgenTableSort, .ui-icon-gear, .ui-icon-closethick, .ui-icon-arrow-4, .ui-icon-plus").hide();
	$("[name=srcgenTblDesc]").toggleClass("srcgen-input-label");
	$("#srcgenScreen").toggleClass("com-sd-cover-toggle");
	$("#srcgenActionArea").toggleClass("com-sd-cover-toggle");
	$("#srcgenPreview").hide();
	$("#srcgenDesign").show();
	$("#srcgenScreen").css("width", "997px").css("max-width", "997px");
	$("#srcgenHeaderLinkPanel").hide();
	$("#srcgenHeaderLinkPanel").next("br").hide();
}

function srcgenDesign(){
	showComponentTool();
	$("#srcgenAreaTemplate").show();
	$("#srcgenButtonPanel").show();
	$("#srcgenScreenDesignInfo").show();
	$("#srcgenControlElement").show();
	$("#allDragDropContent").find(".srcgenControl, .srcgenTableSort, .ui-icon-gear, .ui-icon-closethick, .ui-icon-plus").show();
	$("#allDragDropContent").find(".ui-icon-arrow-4").each(function (i){
		if($(this).closest("th").attr("rowspan") == undefined || $(this).closest("th").attr("rowspan") < 2){
			$(this).show();
		}
	});
	$("[name=srcgenTblDesc]").toggleClass("srcgen-input-label");
	$("#srcgenScreen").toggleClass("com-sd-cover-toggle");
	$("#srcgenActionArea").toggleClass("com-sd-cover-toggle");
	$("#srcgenPreview").show();
	$("#srcgenDesign").hide();
	$("#srcgenScreen").css("width", "736px").css("max-width", "736px");
	$("#srcgenHeaderLinkPanel").show();
	$("#srcgenHeaderLinkPanel").next("br").show();
}

function hideComponentTool() {
	$("#srcgenControlElement").parent().hide();
	$("#hideComponentTool").hide();
	$("#showComponentTool").show();
//	$("#srcgenScreenAndAction").css("width", "100%");
}

function showComponentTool() {
	$("#srcgenControlElement").parent().show();
	$("#hideComponentTool").show();
	$("#showComponentTool").hide();
//	$("#srcgenScreen").css("width", "736px").css("max-width", "736px");
	$('.fullScreenDesign').affix({
	    offset: {
	        top: $('.fullScreenDesign').offset().top
	    }
	});
}


function setArgToListColumn(event, ui){
	var tr = $(event.target).closest("tr");
	if($(tr).find("input[name=dbColumcode]").length == 0){
		tr = $(tr).next("tr");
	}
	$(tr).find("input[name=dbColumcode]").val("");
	$(tr).find("input[name=dbColumcodeAutocomplete]").val("");
	$(tr).find("input[name=dbColumcodeAutocomplete]").attr("arg01", ui.item.outputId);
}

function changeDragElementDataType(obj){
	var div = $("#srcgenComponentDiv").find("#newDragElement");
	if($(obj).val() == 1){
		$(div).attr("datatype", 20);
	}
	if($(obj).val() == 0){
		$(div).attr("datatype", $(div).attr("defaultdatatype"));
	}
	if($(obj).val() == 2){
		$(div).attr("datatype", 11);
	}
}

function setDragElement(event, ui){
	var div = $("#srcgenComponentDiv").find("#newDragElement");
	
	var dynamicTableDetailId = ui.item.output01;
	var url = CONTEXT_PATH + "/Srcgen0117BL.do?r="+Math.random();		
	$.ajax({
        url:url,
        dataType: 'json',
        type:'POST',
        data: {
        	dynamicTableDetailId:dynamicTableDetailId
       }, 
        success: function(data){
        	if (data != '' && data != null){
        		var detail = data.dynamicTableDetail;
        		var master = data.dynamicTable;
        		$(div).text(detail.msgCode);
        		$(div).attr("tablecode", master.tableCode);
        		$(div).attr("tablename", master.menuMsgCode);
        		
        		$(div).attr("label", detail.msgCode);
        		$(div).attr("columnname", toCamaleCase(detail.columnCode));
        		$(div).attr("tablecolumncode", detail.columnCode);
        		$(div).attr("tablecolumnname", detail.msgCode);
        		$(div).attr("physicalmaxlength", detail.maxlength);
        		$(div).attr("physicaldatatpe", detail.physicalDataType);
        		$(div).attr("require", detail.displayType);
        		$(div).attr("maxlength", detail.maxlength);
        		if($("#srcgenComponentDiv").find("input:radio[name=radioDataType]:checked").val() == 1){
        			$(div).attr("datatype", 20);
        		} 
        		if($("#srcgenComponentDiv").find("input:radio[name=radioDataType]:checked").val() == 0){
        			$(div).attr("datatype", detail.dataType);
        		}
        		if($("#srcgenComponentDiv").find("input:radio[name=radioDataType]:checked").val() == 2){
        			$(div).attr("datatype", 11);
        		}
        		$(div).attr("defaultdatatype", detail.dataType);
        		$(div).attr("msgvalue", detail.msgValue);
        		$(div).attr("msglabel", detail.msgLabel);
        		$(div).attr("minvalue", detail.minValue);
        		$(div).attr("maxvalue", detail.maxValue);
        	}
        }
	});
	
	$("#srcgenComponentDiv").find("#newDragElementTd").find("div").draggable({
	      containment: '#allDragDropContent',
	      stack: '#srcgenScreen',
	      revert:function(event, ui) {
				if ($(this).data("uiDraggable") != undefined && $(this).data("uiDraggable").originalPosition != undefined) {
					$(this).data("uiDraggable").originalPosition = {top : 0,left : 0};
				}
				return true;
			},
	      appendTo: '#srcgenScreen',
	      stop: function(event, ui) {
	    	  	$(this).css("z-index","auto");
	    	  } 
	}).disableSelection();
}

function equitableColumnWidth(obj){
	$(obj).closest("th").next("td").find("input[name=columnnameSize]").each(function() {
		$(this).val(parseInt(100/$(obj).attr("collength")));
		$(this).closest("span").next("select").val("%");
	});
}

function toCamaleCase(str){
	return str[0].toLowerCase() + str.replace(/_([a-z])/g, function(a, b) {
        return b.toUpperCase();
    }).slice(1);
}

function isNotAlphanumeric(val){
    return val.match(/^[a-zA-Z0-9]+$/) ? false : true;
} 

function isProhibitChar(val){
    if(val.indexOf("'") != -1 || val.indexOf('"') != -1){
    	return true;
    } else {
    	return false;
    }
}
function returnPhysicalMaxlenth(datatype){
	var maxlength = "200";
	switch (datatype) {
		case "1":
			maxlength = "200";
			break;
		case "10":
			maxlength = "1000";
			break;
		case "15":
			maxlength = "50";
			break;
		case "16":
			maxlength = "20";
			break;
		case "18":
			maxlength = "50";
			break;
		default:
			maxlength = "200";
	}
	return maxlength;
}

function convertToJson(string) {
	var json = {};
	try {
		json = JSON.parse(string);
    } catch (e) {
    	json = JSON.parse("{" + string + "}");
    }
    return json;
}

function convertToString(json) {
	return  JSON.stringify(json);
}

function setBaseType(data) {
	var baseType = "";	
	switch (data.datatype) {
	case '0': case '5': case '6': case '7':
		baseType = "<select class=\"form-control qp-input-select\" name=\"baseType\">";
		baseType += "<option value=\"1\">Character varying</option>";
		baseType += "<option value=\"3\">Text</option>";
		baseType += "<option value=\"5\">Integer</option>";
		baseType += "<option value=\"6\">Smallint</option>";
		baseType += "<option value=\"7\">Bigint</option>";
		baseType += "<option value=\"14\">Bigserial</option>";
		if(data.datatype == 6) {
			baseType += "<option value=\"8\">Boolean</option>";
		}
		baseType += "</select>";
		break;
	case '2':
		baseType = "<select class=\"form-control qp-input-select\" name=\"baseType\">";
		baseType += "<option value=\"5\">Integer</option>";
		baseType += "<option value=\"6\">SmallInt</option>";
		baseType += "<option value=\"7\">Long</option>";
		baseType += "<option value=\"14\">Bigserial</option>";
		baseType += "</select>";
		break;
	case '3':
		baseType = "<select class=\"form-control qp-input-select\" name=\"baseType\">";
		baseType += "<option value=\"16\">Float</option>";
		baseType += "<option value=\"17\">Double</option>";
		baseType += "<option value=\"7\">BigDecimal</option>";
		baseType += "<option value=\"14\">Bigserial</option>";
		baseType += "</select>";
		break;
	case '4':
		baseType = "Date";
		break;
	case '8':
		baseType = "Currency";
		break;
	case '9':
		baseType = "Time";
		break;
	case '12':
		baseType = "Binary";
		break;
	case '14':
		baseType = "Datetime";
		break;
	case '1':case '10':case '15':case '16':case '18':case '21':
		baseType = "Text";
		break;
	default:
		baseType = "Text";
		break;
	}
	
	return baseType;
}

function deleteElement(obj, data) {
	var table = $(obj).closest("table");
	var element = {};
	//remove colspan, rowspan
	var mergeNew = {rowspan:1, colspan:1};
	var mergeOld = {rowspan:parseInt((data.rowspan != undefined &&  data.rowspan != '')?data.rowspan:0), colspan:parseInt((data.colspan != undefined && data.colspan != '')?data.colspan:0)};
	
	//re empty	
	if ($(obj).closest("td,th") != undefined && $(obj).closest("td,th").prop("tagName") == "TD") {
		element = $(obj).closest("td");		
	} else if ($(obj).closest("td,th") != undefined && $(obj).closest("td,th").prop("tagName") == "TH") {
		element = $(obj).closest("th");		
	}
	
	if ($(element).find("input[name=formElement]").length == 1) {
		merge($(element), mergeNew, mergeOld);
	}
	
	var areaType = $(obj).closest("div[class='areaContent']").find("input[name='formAreaType']").val();
	if(areaType != 2 && areaType != 3) {
		if ($(element).prop("tagName") == "TD") {
			var parent = $(obj).closest(".date");
			if ($(obj).closest(".date") != undefined && $(obj).closest(".date").prop("tagName") == "DIV") {
				var parent = $(obj).closest(".date").closest("span");
				$(obj).closest(".date").remove();
				
				if (parent != undefined) {
					$(parent).remove();
				}
			} else {
				if (data.datatype==6 || data.datatype==5) {
					$(obj).remove();
				} else {
					$(obj).closest("span").remove();				
				}
			}
			if ($(element).find("input[name=formElement]") == undefined || $(element).find("input[name=formElement]").length == 0) {
				$(element).append("<div class=\"dropComponent\"><input type=\"hidden\" name=\"formElement\" value=\"\">&nbsp;</div>");
				$(element).closest("td").find("input[name=groupDisplayType]").val("1");
				$(element).closest("td").find("input[name=enableGroup]").val("true");
				$(element).closest("td").find("input[name=groupTotalElement]").val("1");
			}
		} else if ($(element).prop("tagName") == "TH"){
			$(element).html(returnHiddenTHEmpty() + "<span style=\"float: left; cursor: move;\" class=\"qp-glyphicon glyphicon glyphicon-screenshot\" title=\"Move\"></span><div class=\"dropLabel\">&nbsp;</div>");
		}
	} else {
		var datatype = data.datatype;
		if(datatype != 14 && datatype != 4 && datatype != 9 && datatype != 23) {
			$(obj).closest("span").remove();
		} else {
			$(obj).closest("span").closest("span").remove();
		}
		
	}
	
	
	//init drap drop
	$(table).find("div[class='dropComponent']").droppable({
		accept: "#srcgenElements div, #srcgenAction div[id!=''], #newDragElementTd div",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertComponent(event, ui, $(this));
		}
	});
	
	$(table).find("div[class='dropLabel']").droppable({
		accept: "#divLabel",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertLable(event, ui, $(this));
		}
	});
	
	$(table).find("tbody tr td .glyphicon-screenshot").droppable({
		accept: "#" + $(table).attr("id") + " tbody tr td .glyphicon-screenshot",
		activeClass: "state-droppable",
		drop: function(event, ui) {									
			replaceElement(event, ui, $(this));			
		}
	});
	
	$(table).find("tr th .glyphicon-screenshot").droppable({
		accept: "#" + $(table).attr("id") + " tr th .glyphicon-screenshot",
		activeClass: "state-droppable",
		drop: function(event, ui) {									
			replaceElement(event, ui, $(this));			
		}
	});
	
	if ($(table).is("table[class*=qp-table-form]")) {
		checkColRowSpan($(table));
		checkSwap($(table));
	} else if ($(table).is("table[class*=qp-table-list-none-action]")) {
		checkColRowSpan($(table));
		checkSwapListTable($(table));
	}
}
function getInforOfAutocompleteCode(autoId,table){
	if (autoId.length >0  && autoId != undefined) {
		var href = CONTEXT_PATH + "/Autocomplete/";
		var params = {
				"sourceType" : "autocompleteGetAutocompleteCodeList",
				"arg01" : $(table).find("input[name='columnAutocompleteAutocomplete']").attr("arg01"),
				"arg02" :  1,
				"arg03" : autoId
			};
		var data = $.qp.getJson(href, params).outputGroup;
		if (data != null && data.length > 0 ) {
			data = data[0];
			$(table).find("input[name='columnAutocomplete']").val(autoId);
			$(table).find("input[name='columnAutocompleteAutocomplete']").val(data.optionLabel);
			$(table).find("#srcgenAtcSearchColumn").text(data.output02);
			$(table).find("#srcgenAtcDisplayColumn").text(data.output02);
			$(table).find("#srcgenAtcSubmitColumn").text(data.output03);
			$(table).find("#srcgenAtcTable").text(data.output04);
		} else {
			cleanAutocompleteDetail(table);
		}
	} else {
		cleanAutocompleteDetail(table);
	}
}
function changeTypeCodeList(thiz){
	var value = $(thiz).val();
	var modal = $("#dialog-component-list-setting");
	var modalDisplaySection = $("#dialog-component-list-setting-section");
	var tbody = $(modal).find("#dialog-component-list-setting-tbl-options").find("tbody");
	var tbodyDisplaySection = $(modalDisplaySection).find("#dialog-component-list-setting-tbl-options").find("tbody");
	var tdContent = ""
		+ "<tr>"
		+ "		<td>1</td>"
		+ "		<td class='colOptionName'><input type=\"text\" class=\"form-control qp-input-text\" name=\"parameterOptionName\" value=\"option1\"/></td>"
		+ "		<td><input type=\"text\" class=\"form-control qp-input-text\" name=\"parameterOptionValue\" value=\"1\"/></td>"
		+ "		<td>"
		+ "			<a class=\"btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action\" title=\"\" onclick=\"$.qp.removeRowJS('dynamic', this);\" style=\"margin-top: 3px;\" href=\"javascript:void(0)\"></a>"
		+ "		</td>"
		+ "	</tr>"
		+ "<tr>"
		+ "		<td>2</td>"
		+ "		<td class='colOptionName'><input type=\"text\" class=\"form-control qp-input-text\" name=\"parameterOptionName\" value=\"option2\"/></td>"
		+ "		<td><input type=\"text\" class=\"form-control qp-input-text\" name=\"parameterOptionValue\" value=\"2\"/></td>"
		+ "		<td>"
		+ "			<a class=\"btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action\" title=\"\" onclick=\"$.qp.removeRowJS('dynamic', this);\" style=\"margin-top: 3px;\" href=\"javascript:void(0)\"></a>"
		+ "		</td>"
		+ "	</tr>"
		+ "";
	
	$(thiz).parents("table").find("tr[class=codelist-screen]").hide();
	$(thiz).parents("table").find("tr[class=codelist-table]").hide();
	$(thiz).parents("table").find("tr[class=codelist-system]").hide();
	if(value == 1){
		$(thiz).parents("table").find("tr[class=codelist-system]").show();
	}else if(value == 3){
		if (tbody.children().length == 0) {
			$(modal).find("#dialog-component-list-setting-tbl-options").find("tbody").append(tdContent);
		}
		if (tbodyDisplaySection.children().length == 0) {
			$(modalDisplaySection).find("#dialog-component-list-setting-tbl-options").find("tbody").append(tdContent);
		}
		$(thiz).parents("table").find("tr[class=codelist-screen]").show();
	}else if(value == 2){
		$(thiz).parents("table").find("tr[class=codelist-table]").show();
		//selectTableCodeList(thiz,145);
	}
	
}

function changeSupportOptionValue(thiz){
	if($(thiz).prop('checked') == true){
		$(thiz).parents("table").find(".codelist-screen").find(".colOptionName").hide();
	} else {
		$(thiz).parents("table").find(".codelist-screen").find(".colOptionName").show();
	}
}

function selectCodeList(obj){
	loadSystemCodeList(obj.target)
}
function loadSystemCodeList(obj){
	var codelistId = $(obj).next("input[type=hidden]").val();
	var href = CONTEXT_PATH + "/screendesign/getSystemCodeListDetailById?r="+Math.random();
	var table = $(obj).parents("table").find("#dialog-component-list-setting-tbl-system-options");
	$.ajax({
		method : "GET",
		url : href,
		data : { data : codelistId },
		context: table,
		async : false
	}).done(function(msg) {
		var table = $(this);
		$(table).find("tbody").empty();
		if(msg.length>0){
			
			var isOnlyValue = true;
			
			for(i = 0; i < msg.length; i++){
				if (msg[i].name != null && msg[i].name.length > 0) {
					isOnlyValue = false;
					break;
				}
			}
			
			$(table).find("th label").hide();
			if (isOnlyValue) {
				$(table).find("colgroup col:last").hide();
				$(table).find("colgroup col:last").attr("width", "100%");
				$(table).find("thead").find("th[class=colOptionName]").hide();
				
				for(i = 0; i < msg.length; i++){
					var name = msg[i].name == null ?"" : msg[i].name;
					var value = msg[i].value == null ?"" : msg[i].value;
					var tdContent = ""
						+ "<tr>"
						+ "		<td>"+(i+1)+"</td>"
						+ "		<td><span name=\"parameterOptionValue\" >"+escapeHTML(value)+"</span></td>"
						+ "	</tr>"
						+ "";
				
					$(table).find("tbody").append(tdContent);
				}
			} else {
				$(table).find("thead").find("th[class=colOptionName]").show();
				$(table).find("colgroup col:eq(1)").show();
				$(table).find("colgroup col:last").attr("width", "50%");
				$(table).find("colgroup col:last").attr("style", "");
				
				for(i = 0; i < msg.length; i++){
					var name = msg[i].name == null ?"" : msg[i].name;
					var value = msg[i].value == null ?"" : msg[i].value;
					var tdContent = ""
						+ "<tr>"
						+ "		<td>"+(i+1)+"</td>"
						+ "		<td><span name=\"parameterOptionName\" >"+escapeHTML(name)+"</span></td>"
						+ "		<td><span name=\"parameterOptionValue\" >"+escapeHTML(value)+"</span></td>"
						+ "	</tr>"
						+ "";
				
					$(table).find("tbody").append(tdContent);
				}
			}

		}else{
			
		}
	});
}
function loadTableCodeList(obj,tableColumnCode){
	var href = CONTEXT_PATH + "/screendesign/getTableCodeListDetailById?r="+Math.random();
	var table = $(obj).parents("table").find("#dialog-component-list-setting-tbl-table-options");
	$.ajax({
		method : "GET",
		url : href,
		data : { data : tableColumnCode },
		context: table,
		async : false
	}).done(function(msg) {
		var table = $(this);
		$(table).find("tbody").empty();
		if(msg.length>0){
			for(i = 0; i < msg.length; i++){
				var name = msg[i].name == null ?"" : msg[i].name;
				var value = msg[i].value == null ?"" : msg[i].value;
				var tdContent = ""
					+ "<tr>"
					+ "		<td>"+(i+1)+"</td>"
					+ "		<td><span name=\"parameterOptionName\" >"+name+"</span></td>"
					+ "		<td><span name=\"parameterOptionValue\" >"+value+"</span></td>"
					+ "	</tr>"
					+ "";
			
				$(table).find("tbody").append(tdContent);
			}
		}
	});
}
function redrawComponent(scope,data){
	var labels = [];
	var values = [];
	var $scope = $(scope);
	if(data.msglabel){
		labels = data.msglabel.split("�");
	}
	var values = [];
	if(data.msgvalue){
		values = data.msgvalue.split("�");
	}
	
	if(labels.length>0) {
		var $input = $scope.find("input[type=hidden]");
		$input.remove();
		switch(data.datatype){
		case "5":
			$scope.find("input[type=radio]").each(function(){
				var thiz= this;
				$(thiz).next("label").remove();
				$(thiz).remove();
			});
			for(var i=0;i<labels.length;i++){
				if (labels[i].length == 0) {
					labels[i] = values[i];
				}
				$scope.append("<input type='radio' name='radio"+data.columnname+"' class='qp-input-radio qp-input-radio-margin' value='"+values[i]+"' ></input><label>"+labels[i]+"</label>");
			}
			
			break;
		case "6":
			$scope.find("input[type=checkbox]").each(function(){
				var thiz= this;
				$(thiz).next("label").remove();
				$(thiz).remove();
			});
			for(var i=0;i<labels.length;i++){
				if (labels[i].length == 0) {
					labels[i] = values[i];
				}
				$scope.append("<input type='checkbox' name='checkbox"+data.columnname+"' class='qp-input-checkbox qp-input-checkbox-margin' value='"+values[i]+"' ></input><label>"+labels[i]+"</label>");
			}
			
			break;
		case "7":
			$scope = $scope.closest("span");
			var $select = $scope.find("select");
			$select.find("option").remove();
			var html="";
			for(var i=0;i<labels.length;i++){
				if (labels[i].length == 0) {
					labels[i] = values[i];
				}
				html +="<option value='"+values[i]+"'>"+labels[i]+"</option>";
			}
			$select.append(html);
			break;
		}
		$scope.append($input);
		initBlurEventElement();
	} else {
		var $input = $scope.find("input[type=hidden]");
		switch(data.datatype){
		case "5":
			$scope.find("input[type=radio]").each(function(){
				var thiz= this;
				$(thiz).next("label").remove();
				$(thiz).remove();
			});
			
			$input.before("<input type='radio' class='qp-input-radio qp-input-radio-margin' ></input><label>"+dbMsgSource['sc.screendesign.0317']+"</label>");
			$input.before("<input type='radio' class='qp-input-radio qp-input-radio-margin'  ></input><label>"+dbMsgSource['sc.screendesign.0318']+"</label>");
			break;
		case "6":
			$scope.find("input[type=checkbox]").each(function(){
				var thiz= this;
				$(thiz).next("label").remove();
				$(thiz).remove();
			});
			
			$input.before("<input type='checkbox' class='qp-input-checkbox qp-input-checkbox-margin'></input><label>"+dbMsgSource['sc.screendesign.0317']+"</label>");
			$input.before("<input type='checkbox' class='qp-input-checkbox qp-input-checkbox-margin'></input><label>"+dbMsgSource['sc.screendesign.0318']+"</label>");
			break;
		}
	}
}
function escapeHTML(character) {
	// List of HTML entities for escaping.
	var htmlEscapes = {
	  '&': '&amp;',
	  '<': '&lt;',
	  '>': '&gt;',
	  '"': '&quot;',
	  "'": '&#x27;',
	  '/': '&#x2F;'
	};
	
	// Regex containing the keys listed immediately above.
	var htmlEscaper = /[&<>"'\/]/g;
	
    return ('' + character).replace(htmlEscaper, function(match) {
        return htmlEscapes[match];
    });
}
function deEscapeHTMLForView(character) {
	
	var check = false;
	if (character != undefined && character != '') {
		if (character.indexOf("&amp;")) {
			check = true;
		}
	}
	if (check) {
		return character.replace(/&amp;/g, '&');
	} else {
		return character;
	}
}
function addRowCodelist(thiz){
	$.qp.addRowJSByLink(thiz);
	if($(thiz).parents("table").find("input[name='supportOptionValue']") != undefined && $(thiz).parents("table").find("input[name='supportOptionValue']").prop('checked') == true){
		$(thiz).parents("table").find(".codelist-screen").find(".colOptionName").hide();
	} else {
		$(thiz).parents("table").find(".codelist-screen").find(".colOptionName").show();
	}
	
}
function setFirstTabActive(modal){
	var ul = $(modal).find(".nav-tabs");
	var tabs = $(modal).find(".tab-content");
	$(ul).children().removeClass("active");
	$(ul).children().first().addClass("active");
	$(tabs).children().removeClass("active");
	$(tabs).children().first().addClass("active");
}
function htmlDecode(input){
	  var e = document.createElement('div');
	  e.innerHTML = input;
	  return e.childNodes.length === 0 ? "" : e.childNodes[0].nodeValue;
	}

function moveColumn (table, from, to) {
    var rows = $('tr:not(:first)', table);
    var cols;
    rows.each(function() {
        var columnFrom = $(this).find("td[index="+from+"], th[index="+from+"]");
        var columnTo = $(this).find("td[index="+to+"], th[index="+to+"]");
        swap(columnFrom, columnTo);
    });
}

function swapColumn(ev, ui, obj){
	
	var table = $(ui.draggable).closest('table');
		
	var indexFrom = $(ui.draggable).closest("td").index() * 2;
	var indexFromNext = indexFrom + 1;
	var indexTo = $(obj).closest("td").index() * 2;
	var indexToNext = indexTo + 1;
	
	if (checkSwapHeader(table, indexFrom)) {
		alert(dbMsgSource['sc.screendesign.0192']);
		return;
	}

	if (checkSwapHeader(table, indexTo)) {
		alert(dbMsgSource['sc.screendesign.0192']);
		return;
	}
	
	moveColumn($(table), indexFrom, indexTo);
	moveColumn($(table), indexFromNext, indexToNext);
	
	//init saw
	$(table).find("tr td:not(.srcgenControl)").find(".glyphicon-screenshot").droppable({
		accept: "#" + $(table).attr("id") + " tr td:not(.srcgenControl) .glyphicon-screenshot",
		activeClass: "state-droppable",
		drop: function(event, ui) {			
			replaceElement(event, ui, $(this));
		}
	});
	
	$(table).find("tr th").find(".glyphicon-screenshot").droppable({
		accept: "#" + $(table).attr("id") + " tr th .glyphicon-screenshot",
		activeClass: "state-droppable",
		drop: function(event, ui) {						
			replaceElement(event, ui, $(this));
		}
	});
}

function checkSwapHeader(table, fromIndex) {
	var isExistsColspan = false;
	$(table).find("tr:not(:first)").each(function(i){
		$(this).find("td[index="+fromIndex+"], th[index="+fromIndex+"]").each(function(j){
			var colspan = $(this).attr("colspan");
			
			if (colspan == undefined || colspan == '' || colspan == 0) {
				colspan = 1;
			}
			
			if (colspan > 1) {
				isExistsColspan = true;
				return;
			}
			
		});
	});
	if (isExistsColspan) {
		return isExistsColspan;
	}
	
	var nextIndex = fromIndex + 1;
	
	$(table).find("tr:not(:first)").each(function(i){
		$(this).find("td[index="+nextIndex+"], th[index="+nextIndex+"]").each(function(j){
			var colspan = $(this).attr("colspan");
			
			if (colspan == undefined || colspan == '' || colspan == 0) {
				colspan = 1;
			}
			
			if (colspan > 1) {
				isExistsColspan = true;
				return;
			}
			
		});
	});
	return isExistsColspan;
}

/***
 * insert form to screen
 */
function insertForm(ev, ui, obj){
	
	var totalCurrentForm = $(".form-content").length;
	var formCodeElements = $(".form-content").find('input[name=screenFormFormActionCode][value^=formCode]');
	var formCode = '';
	
	if (formCodeElements.length == 0) {
		formCode = 'formCode1';
	} else {
		formCode = 'formCode' + formCodeElements.length + 1;
	}
	
	var formHidden = "<div class=\"form-content\">";
	formHidden += "<input type=\"hidden\" name=\"screenFormFormActionCode\" value=\""+formCode+"\" />";
	formHidden += "<input type=\"hidden\" name=\"screenFormFormActionName\"/>";
	formHidden += "<input type=\"hidden\" name=\"screenFormEncryptType\" value=\"3\" />";
	formHidden += "<input type=\"hidden\" name=\"screenFormMethodType\" />";
	formHidden += "<input type=\"hidden\" name=\"screenFormFormSeqNo\" value=\""+totalCurrentForm+"\" />";
	formHidden += "<input type=\"hidden\" name=\"screenFormScreenFormId\" value=\"\" />";
	formHidden += "<input type=\"hidden\" name=\"screenFormTab\" value=\"\" />";
	formHidden += "</div>";
	var setting = "<span class=\"form-setting\">" +
						"<br>" +
						'<a href="javascript:" onclick="openNewTabSetting(this)" class="ui-state-default qp-glyphicon-cog glyphicon glyphicon-folder-close" title="Tab setting"></a>&nbsp;' +
						"<a href=\"javascript:\" onclick=\"deleteForm(this)\" class=\"ui-state-default glyphicon glyphicon-remove\" ></a>" +
						'<a style="cursor: move;" href="javascript:" class="ui-state-default glyphicon glyphicon-sort form-sort" title="Sort form"></a>' + 
					"</span>";
	$(obj).before('<div class="form-layout">' + setting + "<div class=\"com-sd-cover ui-sortable form-area-content\">" + formHidden +
			"<div id=\"srcgenAreaTemplate\" style=\"float: left; line-height: 24px; height: 30px; width: 100%; margin: 2px;\" class=\"ui-droppable drap-drop-area\">&nbsp;</div>" +
			"</div></div>");
	$(".drap-drop-area").droppable({
		accept: ".srcgenElementsTable div[elementtype!=4]",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertArea(event, ui, $(this));
		}
	});
	$(".form-area-content").sortable({
        connectWith: '.form-area-content',
        handle: '.srcgenTableSort',
        update: function(e, ui) {
        	refreshFormIndex();
		},
		helper: function(e, ui) {
			var width = '';
			ui.children().each(function() {
				$(this).width($(this).width());
				width = $(this).width();
			});
			ui.width(width);
			return ui;
		},
	});
	
	$("#dragDropContent").sortable({
        handle: '.form-sort',
        update: function(e, ui) {
        	refreshFormIndex();
		},
		helper: function(e, ui) {
			ui.width(ui.outerWidth());
			return ui;
		},
		items: '.form-layout',
	});
}
function refreshFormIndex() {
	$(".form-area-content").each(function(i){
		$(this).find("input[name=screenFormFormSeqNo]").val(i);
		
		$(this).find(".areaContent").each(function(){
			$(this).find("input[name=formIndex]").val(i);
		});
	});	
}
function saveProperties(modal, data) {
	
	
	if ($(modal).find("input[name='labelName']").val().length == 0) {
		alert(dbMsgSource['sc.screendesign.0278']);
		return false;
	}
	
	var label  = "";
	if($(modal).find("input[name='labelName']").val() != undefined){
		label = $(modal).find("input[name='labelName']").val();
	}
	
	var labelText  = "";
	var lableTextElement = $(modal).find("input[name='labelNameAutocomplete']"); 
	if(lableTextElement.val() != undefined){
		labelText =lableTextElement.val();
	}
	
	var tabindex = '';
	if($(modal).find("input[name='tabindex']").val() != undefined){
		tabindex = $(modal).find("input[name='tabindex']").val();
	}
	
	if (data.label != undefined) {
		data.label = label;
	} else {
		data["label"] = label;
	}
	
	if ($(modal).find("input[name='labelNameAutocomplete']").attr("selectedvalue") != "true") {
		data["label"] = "";
	}
	
	if (data.labelText != undefined) {
		data.labelText = labelText;
	} else {
		data["labelText"] = labelText;
	}
	
	if (data.tabindex != undefined) {
		data.tabindex = tabindex;
	} else {
		data["tabindex"] = tabindex;
	}
	
	if ($(modal).find("input[name=enablePassword]").prop("checked")) {
		data["enablePassword"] = 1;
	} else {
		data["enablePassword"] = 0;
	}
	
	if ($(modal).find("input[name=allowAnyInput]") != undefined && $(modal).find("input[name=allowAnyInput]") != null && $(modal).find("input[name=allowAnyInput]").length > 0) {
		data["allowAnyInput"] = $(modal).find("input[name=allowAnyInput]:checked").val();
	} else {
		data["allowAnyInput"] = 1;
	}
	
	return data;
}

function openProperties(modal, data) {
	
	if(data.datatype != undefined && data.datatype != null && data.datatype == 1) {
		$(modal).find("#enablePassword").show();
	} else {
		$(modal).find("#enablePassword").hide();
	}
	
	if(data.allowAnyInput != undefined && data.allowAnyInput != null && data.allowAnyInput == 0) {
		$(modal).find("input[name=allowAnyInput][value=0]").prop("checked", true);
	} else {
		$(modal).find("input[name=allowAnyInput][value=1]").prop("checked", true);
	}
	
	if (data.enablePassword != undefined && data.enablePassword != null && (data.enablePassword == 1 || data.enablePassword == "1")) {
		$(modal).find("input[name=enablePassword]").prop("checked", true);
	} else {
		$(modal).find("input[name=enablePassword]").prop("checked", false);
	}
	
	if (data.label != undefined){
		$(modal).find("input[name=labelName]").val(data.label);
		
		if (data.label.length == 0) {
			if (data.labelText != undefined && data.labelText != "undefined"){
				$(modal).find("input[name=labelName]").val(htmlDecode(data.labelText));
			} else {
				$(modal).find("input[name=labelName]").val('');
			}
		}
	} else {
		if (data.labelText != undefined && data.labelText != "undefined"){
			$(modal).find("input[name=labelName]").val(htmlDecode(data.labelText));
		} else {
			$(modal).find("input[name=labelName]").val('');
		}
	}
	
	if(data.messageLevel == "" || data.messageLevel == undefined || data.messageLevel == null) {
		data.messageLevel = 2;
	}
	
	$(modal).find("#labelNameAutocompleteId").attr("arg06",data.messageLevel);
	$(modal).find("#labelNameAutocompleteId").attr("arg08",data.labelText);
	
	var messageLevelText = getMessageLevelText(data.messageLevel);
	$(modal).find("div[class*='level-text']").html(messageLevelText);
	
	if (data.labelText != undefined && data.labelText != "undefined"){
		if (data.labelText.length == 0 && data.label != undefined && data.label != null && data.label.length > 0) {
			$(modal).find("input[name=labelNameAutocomplete]").val(htmlDecode(data.label) );
		} else {
			$(modal).find("input[name=labelNameAutocomplete]").val(htmlDecode(data.labelText) );
		}
	} else {
		if (data.label != undefined) {
			$(modal).find("input[name=labelNameAutocomplete]").val(data.label);
		} else {
			$(modal).find("input[name=labelNameAutocomplete]").val('');
		}
	}
	
	if (data.tabindex != undefined) {
		$(modal).find("input[name=tabindex]").val(data.tabindex);
	} else {
		$(modal).find("input[name=tabindex]").val('');
	}
}


function settingDefaultValue(obj, dataType) {
	var typeName = "";
	
	switch(dataType) {
	case 0:
		var formElement = $(obj).closest("div").next();
		var data = convertToJson($(formElement).val());
		
		if (data.physicaldatatype == 5 || data.physicaldatatype == 6 || data.physicaldatatype == 7)  {
			
			switch(data.physicaldatatype) {
			case "5":
				typeName = "Integer";
				break;
			case "6":
				typeName = "Smallint";
				break;
			case "7":
				typeName = "Bigint";
				break;
			}
			
			try {
				
				var valid = parseInt($(obj).val());
				
				if (isNaN(valid) && $(obj).val().length > 0) {
					alert(dbMsgSource['sc.screendesign.0279']);
					return;
				}
			} catch(err) {
				alert(dbMsgSource['sc.screendesign.0280']+typeName);
				return;
			}
			
		}
		
		if ($(obj).val().length > 0) {
			if (data.defaultvalue != undefined) {
				data.defaultvalue = $(obj).autoNumeric('get');
			} else {
				data['defaultvalue'] = $(obj).autoNumeric('get');
			}
		}
		break;
	case 8:
	case 1:
	case 3:
	case 2:
	case 18:
	case 15:
	case 16:
	case 10:
		
		if (dataType == 8) {
			try {
				var valid = parseFloat($(obj).autoNumeric('get'));
				if (isNaN(valid) && $(obj).autoNumeric('get').length > 0) {
					alert(dbMsgSource['sc.screendesign.0281']);
					$(obj).focus();
					return;
				}
			} catch(err) {
				alert(dbMsgSource['sc.screendesign.0281']);
				$(obj).focus();
				return;
			}
		} else if (dataType == 3) {
			try {
				
				var valid = parseFloat($(obj).autoNumeric('get'));
				if (isNaN(valid) && $(obj).autoNumeric('get').length > 0) {
					alert(dbMsgSource['sc.screendesign.0279']);
					$(obj).focus();
					return;
				}
			} catch(err) {
				alert(dbMsgSource['sc.screendesign.0279']);
				$(obj).focus();
				return;
			}
		} else if (dataType == 2) {
			try {
				
				var valid = parseFloat($(obj).autoNumeric('get'));
				
				if (isNaN(valid) && $(obj).autoNumeric('get').length > 0) {
					alert(dbMsgSource['sc.screendesign.0282']);
					$(obj).focus();
					return;
				}
			} catch(err) {
				alert(dbMsgSource['sc.screendesign.0282']);
				$(obj).focus();
				return;
			}
		}
		
		var formElement = $(obj).next();
		var data = convertToJson($(formElement).val());
		
		if ($(obj).val().length > 0) {
			if (data.defaultvalue != undefined) {
				try {
					data.defaultvalue = $(obj).autoNumeric('get');
				} catch(ex) {
					data.defaultvalue = $(obj).val();
				}
				
			} else {
				
				try {
					data['defaultvalue'] = $(obj).autoNumeric('get');
				} catch(ex) {
					data['defaultvalue'] = $(obj).val();
				}
			}
		}
		break;
	case 4:
	case 14:
	case 9:
		
		break;
	case 5:
	case 6:
	case 7:
		var formElement = $(obj).closest("span").find("input[name=formElement]");
		var data = convertToJson($(formElement).val());
		
		switch(data.datatype) {
		case "5":
			if ($(obj).is(":checked")) {
				if (data.defaultvalue != undefined) {
					data.defaultvalue = $(obj).val();
				} else {
					data['defaultvalue'] = $(obj).val();
				}
			}
			break;
		case "6":
			if ($(obj).is(":checked")) {
				if (data.defaultvalue != undefined) {
					data.defaultvalue = $(obj).val();
				} else {
					data['defaultvalue'] = $(obj).val();
				}
			}
			break;
		case "7":
			if (data.defaultvalue != undefined) {
				data.defaultvalue = $(obj).val();
			} else {
				data['defaultvalue'] = $(obj).val();
			}
			break;
		}
		break;
	}
	
	$(formElement).val(convertToString(data));
}

function initBlurEventElement() {
	$(".form-area-content").find("input[name=formElement]").each(function() {
		var data = convertToJson($(this).val());
		
		var dataType = -1;
		try {
			dataType = parseInt(data.datatype);
		}
		catch(err) {
			dataType = -1;
		}

		switch(dataType) {
		case 8:
			$(this).prev().addClass("qp-input-currency");
			$.qp.formatCurrency($(this).prev());
			$(this).prev().prev().prev().addClass("qp-input-currency");
			$.qp.formatCurrency($(this).prev().prev().prev());
			break;
		case 3:
			$(this).prev().addClass("qp-input-float");
			$.qp.formatFloat($(this).prev());
			break;
		case 2:
			$(this).prev().addClass("qp-input-integer");
			$.qp.formatInteger($(this).prev());
			$(this).prev().prev().prev().addClass("qp-input-integer");
			$.qp.formatInteger($(this).prev().prev().prev());
			break;
		}
		
		switch(dataType) {
		case 0:
			if (data.defaultvalue != undefined) {
				$(this).closest("span div").find("input[name=autocomplete]").val(data.defaultvalue);
			}
			$(this).closest("span div").find("input[name=autocomplete]").attr("onblur", "settingDefaultValue(this, "+dataType+")");
			break;
		case 8:
		case 1:
		case 3:
		case 2:
		case 18:
		case 15:
		case 16:
		case 10:
			
			if (data.defaultvalue != undefined) {
				$(this).prev().val(data.defaultvalue);
			}
			
			$(this).prev().attr("onblur", "settingDefaultValue(this, "+dataType+")");
			break;
		case 4:
		case 14:
		case 9:
			
			break;
		case 5:
		case 6:
		case 7:
			$(this).closest("span").find("input[type=checkbox]").attr("onblur", "settingDefaultValue(this, "+dataType+")");
			
			if (data.defaultvalue != undefined) {
				
				$(this).closest("span").find("input[type=checkbox]").each(function() {
					if ($(this).val() == data.defaultvalue) {
						$(this).prop('checked', true);
					}
				});
			}
			
			$(this).closest("span").find("input[type=radio]").attr("onblur", "settingDefaultValue(this, "+dataType+")");
			
			if (data.defaultvalue != undefined) {
				$(this).closest("span").find("input[type=radio]").each(function() {
					if ($(this).val() == data.defaultvalue) {
						$(this).prop('checked', true);
					}
				});
			}
			
			$(this).closest("span").find("select").attr("onblur", "settingDefaultValue(this, "+dataType+")");
			$(this).closest("span").find("select").val(data.defaultvalue);
			
			
			break;
		}
	});
	initEventDblClickSelectElement();
}
function doubleclick(el, onsingle, ondouble) {
    if (el.getAttribute("data-dblclick") == null) {
        el.setAttribute("data-dblclick", 1);
        setTimeout(function () {
            if (el.getAttribute("data-dblclick") == 1) {
                onsingle();
            }
            el.removeAttribute("data-dblclick");
        }, 200);
    } else {
        el.removeAttribute("data-dblclick");
        ondouble();
    }
}

function initEventDblClickSelectElement() {
	$("select[ondblclick][ondblclick!='']").each(function(){
		
		var select = $(this);
		$(select).closest("span").unbind( "click" );
		$(select).closest("span").bind('click', function(){
			doubleclick(this, function(){
				
			}, function(){
				var functionName = $(select).attr('ondblclick').replace("(this)", ""); 
				window[functionName](select);
			});
		});
	});
}
function showDataSourceInView(modal, data) {
	var localCodelist = data.localCodelist;
	if(localCodelist == undefined || localCodelist.length == 0 || localCodelist == "undefined"){
		localCodelist = 3;
	}
	
	$(modal).find("input:radio[name=localCodelist][value=2]").parent().hide();
	$(modal).find(".codelist-system").hide();
	$(modal).find(".codelist-table").hide();
	$(modal).find(".codelist-screen").hide();
	$(modal).find("#dialog-component-list-setting-tbl-options").find("tbody").empty();
	$(modal).find("#dialog-component-list-setting-tbl-table-options").find("tbody").empty();
	$(modal).find("#dialog-component-list-setting-tbl-system-options").find("tbody").empty();
	$(modal).find("input[name=codelistCode]").val("");
	$(modal).find("input[name=codelistCodeAutocomplete]").val("");
	
	if(data.tablecolumncode != undefined){
		loadTableCodeList($(modal).find('input:radio[name="localCodelist"]'),data.tablecolumncode);
		var table = $(modal).find("#dialog-component-list-setting-tbl-table-options");
		if(table != undefined && $(table).find("tbody").find("tr").length > 0){
			$(modal).find("input:radio[name=localCodelist][value=2]").parent().show();
		}
	}
	
	//show datasource
	
/*	if (data.datasourcetype != undefined && data.datasourcetype.length > 0 && data.datasourcetype != 'undefined') {
		$(modal).find("input[name=dataSourceType][value="+data.datasourcetype+"]").prop("checked", true);
		changeSettingDataSource($(modal).find("input[name=dataSourceType][value="+data.datasourcetype+"]"));
	} else {
		$(modal).find("input[name=dataSourceType][value=1]").prop("checked", true);
		changeSettingDataSource($(modal).find('input[name=dataSourceType][value=1]'));
	}*/
	
	if (data.datasourcetype != undefined && data.datasourcetype == '1') {
		
		if (data.sqldesignid != undefined) {
			$(modal).find("input[name=sqldesignid]").val(data.sqldesignid);
		} else {
			$(modal).find("input[name=sqldesignid]").val('');
		}
		
		if (data.sqldesignidtext != undefined) {
			$(modal).find("input[name=sqldesignidAutocomplete]").val(data.sqldesignidtext);
		} else {
			$(modal).find("input[name=sqldesignidAutocomplete]").val('');
		}
		
		if (data.optionlabel != undefined) {
			$(modal).find("input[name=optionlabel]").val(data.optionlabel);
		} else {
			$(modal).find("input[name=optionlabel]").val('');
		}
		
		if (data.optionlabeltext != undefined) {
			$(modal).find("input[name=optionlabelAutocomplete]").val(data.optionlabeltext);
		} else {
			$(modal).find("input[name=optionlabelAutocomplete]").val('');
		}		
		
		if (data.optionvalue != undefined) {
			$(modal).find("input[name=optionvalue]").val(data.optionvalue);
		} else {
			$(modal).find("input[name=optionvalue]").val('');
		}
		
		if (data.optionvaluetext != undefined) {
			$(modal).find("input[name=optionvalueAutocomplete]").val(data.optionvaluetext);
		} else {
			$(modal).find("input[name=optionvalueAutocomplete]").val('');
		}
		
		/*initOptionAutocomplete($(modal).find("#sql-setting"), data.sqldesignid);*/
	} else {
		$(modal).find("input[name=sqldesignid]").val('');
		$(modal).find("input[name=sqldesignidAutocomplete]").val('');
		$(modal).find("input[name=optionlabel]").val('');
		$(modal).find("input[name=optionlabelAutocomplete]").val('');
		$(modal).find("input[name=optionvalue]").val('');
		$(modal).find("input[name=optionvalueAutocomplete]").val('');
	}
	
	//set default
	$(modal).find("input[name='supportOptionValue']").prop("checked",false);
	changeSupportOptionValue($(modal).find("input[name='supportOptionValue']"));
	if(data.physicaldatatype != undefined && "8" == data.physicaldatatype){
		$(modal).find(".codelist-table").show();
		$(modal).find("input:radio[name=localCodelist][value=1]").parent().hide();
		$(modal).find("input:radio[name=localCodelist][value=3]").parent().hide();
		$(modal).find('input:radio[name="localCodelist"]').prop('checked', false);
		$(modal).find('input:radio[name="localCodelist"]').filter('[value="'+2+'"]').prop('checked', true);
	}else{
		if(localCodelist == 2){
			// table codelist
			if(data.tablecolumncode != undefined && data.tablecolumncode.length >0){
				$(modal).find(".codelist-table").show();
			}
		}else if(localCodelist == 3){
			// screen codelist
			$(modal).find(".codelist-screen").show();
			if(data.isSupportOptionValue != undefined){
				$(modal).find("input[name='supportOptionValue']").prop("checked",eval(data.isSupportOptionValue));
			}
			else{
				$(modal).find("input[name='supportOptionValue']").prop("checked",false);
			}
			if(data.showBlankItem == "1"){
				$(modal).find("input[name='showBlankItem']").prop("checked",eval(data.showBlankItem));
			}
			$(modal).find("#dialog-component-list-setting-tbl-options").find("tbody").empty();
			var parameters = data.parameters;
			if(parameters != undefined && parameters.length > 0){
				var arrParameters = parameters.split("�");
				$(arrParameters).each(function(i){
					if (arrParameters[i] != undefined && arrParameters[i] != null && arrParameters[i].length > 0) {
						var dataParameter = arrParameters[i].split("π");
						
						var tdContent = ""
								+ "<tr>"
								+ "		<td>"+(i+1)+"</td>"
								+ "		<td class='colOptionName'><input type=\"text\" class=\"form-control qp-input-text\" name=\"parameterOptionName\" value=\""+dataParameter[0]+"\"/></td>"
								+ "		<td><input type=\"text\" class=\"form-control qp-input-text\" name=\"parameterOptionValue\" value=\""+deEscapeHTMLForView(dataParameter[1])+"\"/></td>"
								+ "		<td>"
								+ "			<a class=\"btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action\" title=\"\" onclick=\"$.qp.removeRowJS('dynamic', this);\" style=\"margin-top: 3px;\" href=\"javascript:void(0)\"></a>"
								+ "		</td>"
								+ "	</tr>"
								+ "";
						
						$(modal).find("#dialog-component-list-setting-tbl-options").find("tbody").append(tdContent);	
					}
				});
			} else {
				$(modal).find("#dialog-component-list-setting-tbl-options").find("tbody").find("tr:eq(0)").find("input[name=parameterOptionName]").val("");
				$(modal).find("#dialog-component-list-setting-tbl-options").find("tbody").find("tr:eq(0)").find("input[name=parameterOptionValue]").val("");
			}
			changeSupportOptionValue($(modal).find("input[name='supportOptionValue']"));
			changeTypeCodeList($(modal).find("input:radio[name=localCodelist][value="+localCodelist+"]"));
		}else{
			// system codelist
			$(modal).find(".codelist-system").show();
			if(data.codelistCode != undefined && data.codelistCode != "undefined"){
				$(modal).find("input[name=codelistCode]").val(data.codelistCode);
			}else{
				$(modal).find("input[name=codelistCode]").val("");
			}
			if(data.codelistText != undefined && data.codelistText != 'undefined'){
				$(modal).find("input[name=codelistCodeAutocomplete]").val(data.codelistText);
			}else{
				$(modal).find("input[name=codelistCodeAutocomplete]").val("");
			}
			loadSystemCodeList($(modal).find("input[name=codelistCodeAutocomplete]"));
		}
		if (localCodelist == undefined || localCodelist == "undefined") {
			$(modal).find('input[name="localCodelist"][value="3"]').prop('checked', true);
		} else {
			$(modal).find('input[name="localCodelist"][value="'+localCodelist+'"]').prop('checked', true);
		}
	}
	
	if (data.datatype == 21) {
		$(modal).find("#codelistCodeAutocompleteId").prop('disabled', true);
		$(modal).find("#sqldesignidAutocompleteId").prop('disabled', true);
		$(modal).find("#optionlabelAutocompleteId").prop('disabled', true);
		$(modal).find("#optionvalueAutocompleteId").prop('disabled', true);
	} else {
		$(modal).find("#codelistCodeAutocompleteId").prop('disabled', false);
		$(modal).find("#sqldesignidAutocompleteId").prop('disabled', false);
		$(modal).find("#optionlabelAutocompleteId").prop('disabled', false);
		$(modal).find("#optionvalueAutocompleteId").prop('disabled', false);
	}
}

function getFormCode(currentForm) {
	return $(currentForm).find(".form-content").find("input[name=screenFormFormActionCode]").val();
}


function addNewTab(currentForm, obj, index, tab, arrOther) {
	
	var title = "";
	var initAreas = [];
	
	if (tab != undefined && tab != null) {
		title = tab['title'];
		initAreas = tab['areas'].split(',');
	}
	
	var modal=$("#modalTabSetting");
	var ul = $(obj).closest("ul");
	
	var areas = [];
	
	var tabContent = $(ul).next();
	
	$(tabContent).find("table tbody tr").each(function() {
		if ($(this).find("input[type=checkbox]").prop("checked")) {
			areas.push($(this).find("td:eq(2)").html());
		}
	});
	
	//add li
	var li = '<li class="active">' +
			'<a href="#tab-design-'+index+'" data-toggle="tab">' +
				'<div class="form-inline">' +
					'<b>Title</b>&nbsp;<label class="qp-required-field">(*)</label>:' + 
					'<input class="form-control" type="text" value="'+title+'" />' +
					'<span style="cursor: move;" class=".ui-state-dark qp-glyphicon glyphicon glyphicon-sort sortable"></span>' +
					'<span style="cursor: pointer;" class=".ui-state-dark qp-glyphicon glyphicon glyphicon-remove" onclick="deleteTab(this)"></span>' +
			'</div>' +
			'</a>' +
		'</li>';
	
	$(obj).before(li);
	
	var tabContent = '<div class="tab-pane in active" id="tab-design-'+index+'">' +
							'<table class="table table-bordered qp-table-list">' +
						'<colgroup>' +
							'<col width="30px" />' +
							'<col width="40%" />' +
							'<col width="40%" />' +
							'<col width="30px" />' +
						'</colgroup>' +
						'<thead>' +
							'<tr>' +
								'<th>No.</th>' +
								'<th>Area name</th>' +
								'<th>Area code</th>' +
								'<th></th>' +
							'</tr>' +
						'</thead>' +
						'<tbody id="listOfArea">' +
							
						'</tbody>' +
					'</table>' +
					'</div>';
	
	$(ul).next().append(tabContent);
	$(modal).find("#tab-" + index).find("#listOfArea").empty();
	
	$(currentForm).find(".areaContent").each(function(i){
		var areaName = $(this).find("input[name=formAreaCode]").val();
		
		style = "style='display: none;'";
		
		var checked = "";
		var isChecked = false;
		
		//check exists in other tab
		if ($(this).closest(".area-tab") == undefined || $(this).closest(".area-tab").length == 0) {
			style = "";
			
			for (var k = 0; k < areas.length; k++) {
				
				if (areaName == areas[k]) {
					style = "style='display: none;'";
					break;
				}
			}
		} else {
			
			for (var k = 0; k < initAreas.length; k++) {
				if (initAreas[k] == areaName) {
					isChecked = true;
					break;
				}
			}
			if (isChecked) {
				style = "";
			} else {
				//check in other tab
				$("#tab-setting").find("table tr").each(function() {
					if ($(this).find("td:eq(2)").html() == areaName) {
						
						var checked = $(this).find("input[type=checkbox]").prop("checked");
						if (checked || $(this).is(":hidden")) {
							style = "style='display: none;'";
						} else {
							style = "";
						}
						
						
					}
					
				});
			}
			
		}	

		if (isChecked && style.length == 0) {
			checked = 'checked="checked"';
		}
		
		var content = "<tr "+style+">" +
			"<td>"+(i + 1)+"</td>" +
			"<td>" +
				$(this).find("input[name=formAreaCaptionText]").val() +
			"</td>" +
			"<td>" +
				areaName +
			"</td>" +
			"<td><input "+checked+" type=\"checkbox\" onclick=\"getAreaTab(this)\" /> </td>" +
		"</tr>";
		$(modal).find("#tab-design-" + index).find("#listOfArea").append(content);
		
	});

	$(ul).find("li").removeClass("active");
	$(ul).next().find("div").removeClass("active");
	$(obj).prev().find("a").tab("show");
	$(obj).prev().find("input").focus();
	
	$(ul).sortable({
		helper: function(e, ui) {
			ui.children().each(function() {
				$(this).width($(this).width());
			});
			return ui;
		},
		update: function(e, ui) {
			
		},
		cursor: 'move',
		items: 'li:not(:last)',
		handle: '.sortable'
	});
}

function initTab(isView) {
	$(".form-content").find("input[name=screenFormTab]").each(function() {
		var value = $(this).val();
		if (value.length > 0) {
			var data = convertToJson(value);
			
			var arrTabs = [];
			
			for (var indexTab = 0; indexTab < data.tabs.length; indexTab++) {
				var tab = data.tabs[indexTab];
				var isExists = false;
				
				for (var k = 0; k < arrTabs.length; k++) {
					if (arrTabs[k].code == tab["tabCode"]) {
						isExists = true;
						break;
					}
				}
				
				if (isExists) {
					for (var k = 0; k < arrTabs.length; k++) {
						if (arrTabs[k]["code"] == tab["tabCode"]) {
							arrTabs[k]["tabs"].push(tab);
							break;
						}
					}
				} else {
					var tabScreen = {};
					tabScreen["code"] = tab["tabCode"];
					tabScreen["tabDirection"] = tab["tabDirection"];
					tabScreen["tabs"] = [];
					tabScreen["tabs"].push(tab);
					arrTabs.push(tabScreen);
				}
			}
			
			for (var indexTab = 0; indexTab < arrTabs.length; indexTab++) {
				var tabs = arrTabs[indexTab];
				
				var startAreaCode = arrTabs[indexTab]["tabs"][0].areas.split(",")[0];
				var areaStart = $($(this).closest(".form-area-content").find(".areaContent").find('input[name=formAreaCode][value="'+startAreaCode+'"]')).closest(".areaContent");
				
				var currentForm = $($(this).closest(".form-area-content"));
				var formCode = $(currentForm).find("input[name=screenFormFormActionCode]").val();
				
				formCode = formCode + "-" + tabs["code"];
				formCode = formCode.replace(/ /g,'');
				
				var content = "";
				
				if (tabs["tabDirection"] == 1) {
					var content = '<div class="menu-tab" style="float: left; width: 20%; margin: 0px; padding: 0px; margin-left: 4px;"><ul id="'+formCode+'-tab" class="nav nav-tabs tabs-left">' +
					  '</ul></div><div class="contain-tab-content" style="float: left; width: 79%;margin: 0px; padding: 0px;"><div id="'+formCode + '-tab-content" style="border: 1px solid #ddd;" class="tab-content"></div></div>';
				} else {
					var content = '<ul style="margin-left: 4px; margin-right: 4px;" id="'+formCode+'-tab" class="nav nav-tabs">' +
					  '</ul><div style="margin-left: 4px; margin-right: 4px; height: auto;" id="'+formCode + '-tab-content" class="tab-content"></div>';
				}

				var content = "<div id=\""+formCode+"\" style='width: 100%; float:left;' class='area-tab'>"+content+"</div>"
				
				$(areaStart).after(content);
				
				var tabForm = $(currentForm).find("#" + formCode + "-tab");
				var tabFormContent = $(currentForm).find("#" + formCode + "-tab-content"); 
				
				for (var j = 0; j < tabs["tabs"].length; j++) {					
					var tab = tabs["tabs"][j]
					var tabTitle = '<div class="form-inline">' +
									'	<span style="cursor: move;" class=".ui-state-dark qp-glyphicon glyphicon glyphicon-sort sortable srcgenTableSort"></span>' +
									' <span style="cursor: pointer;" onclick="openTabSetting(this)" class=".ui-state-dark qp-glyphicon glyphicon glyphicon-cog"></span>&nbsp;' +
									tab.title +
									'</div>';
					
					if (j == 0) {
						$(tabForm).append("<li><a data-toggle='tab' href='#"+formCode+"tab-"+j+"'>"+tabTitle+"</a></li>");
					} else {
						$(tabForm).append("<li><a data-toggle='tab' href='#"+formCode+"tab-"+j+"'>"+tab.title+"</a></li>");
					}
					
					$(tabFormContent).append("<div id='"+formCode+"tab-"+j+"' style='overflow: hidden;' class='tab-pane'></div>");
					
					var tabContent = $(tabFormContent).find("#"+formCode+"tab-" + j).first();
					var height = 0;
					var areas = tab.areas.split(",");
					
					for (var k = 0; k  < areas.length; k++) {
						
						var areaCode = areas[k];
						
						var areaHidden = $(currentForm).find(".areaContent").find("input[name=formAreaCode][value="+areaCode+"]");
						
						if (areaHidden != undefined) {
							var area = $(areaHidden).closest(".areaContent");
							
							$(area).appendTo($(tabContent));
						}
					}
				}
					
				$(tabForm).find("li:eq(0)").find("a").tab("show");
				
				$(".form-area-content").sortable({
			        connectWith: '.form-area-content',
			        handle: '.srcgenTableSort',
			        update: function(e, ui) {
			        	refreshFormIndex();
					},
					helper: function(e, ui) {
						var width = '';
						
					/*	if ($(ui).attr("class") == "area-tab") {
							ui.children("div").each(function() {
								$(this).width($(this).width());
								width += $(this).width();
							});
							ui.width(width);
						} else {
							ui.children().each(function() {
								$(this).width($(this).width());
								width = $(this).width();
							});
							ui.width(width);
						}*/
						ui.width(ui.outerWidth());
						return ui;
					},
					items: '.areaContent, .area-tab',
				});
				
			}
		}
	});
}
function getModuleByScreenId(screenId, modal) {
	var url = CONTEXT_PATH + "/screendesign/getModuleByScreenId?screenId="+screenId +"&r="+Math.random();	
	
	var data = $.qp.getData(url);
	
	$(modal).find("input[name=moduleIdAutocomplete]").val(data.moduleName);
	$(modal).find("input[name=moduleId]").val(data.moduleId);
	
}

function getModuleByBlogicId(blogicId, modal) {
	var url = CONTEXT_PATH + "/screendesign/getModuleByBlogicId?blogicId="+blogicId +"&r="+Math.random();	
	
	var data = $.qp.getData(url);
	
	$(modal).find("input[name=moduleIdAutocomplete]").val(data.moduleName);
	$(modal).find("input[name=moduleId]").val(data.moduleId);
	
}
function changeModule(event) {
	var value = $(event.target).next("input[type=hidden]").val();
	var nextInput = $(event.target).closest("table").find("input[name='navigateToAutocomplete']");
	var nextHidden = nextInput.next("input[type=hidden]");
	nextInput.val("");
	nextInput.attr("arg01",value);
	nextHidden.val("");
	nextInput.data("ui-autocomplete")._trigger("change");	
	
}
function saveStyle(modal, data, obj) {
	var style = "";
	
	var marginLeft = $(modal).find("[name='margin-left']").val();
	var marginTop = $(modal).find("[name='margin-top']").val();
	var marginRight = $(modal).find("[name='margin-right']").val();
	var marginBottom = $(modal).find("[name='margin-bottom']").val();
	var color = $(modal).find("[name='color']").val();
	var fontSize = $(modal).find("[name='font-size']").val();
	var fontStyle = $(modal).find("[name='font-style']").val();
	var fontWeight = $(modal).find("[name='font-weight']").val();
	var backgroundColor = $(modal).find("[name='background-color']").val();
	var height = $(modal).find("[name='height']").val();
	
	var textDecoration = $(modal).find("[name='text-decoration']").val();
	
	if (height != undefined && height != null && height.length > 0) {
		style += "height:" + height + "px;"; 
	}
	
	if (backgroundColor != undefined && backgroundColor != null && backgroundColor.length > 0) {
		style += "background-color:" + backgroundColor + ";"; 
	}
	
	if (textDecoration != undefined && textDecoration != null && textDecoration.length > 0) {
		style += "text-decoration:" + textDecoration + ";"; 
	}
	
	if (marginLeft != undefined && marginLeft != null && marginLeft.length > 0) {
		style += "margin-left:" + marginLeft + "px;"; 
	}
	
	if (marginTop != undefined && marginTop != null && marginTop.length > 0) {
		style += "margin-top:" + marginTop + "px;"; 
	}
	
	if (marginRight != undefined && marginRight != null && marginRight.length > 0) {
		style += "margin-right:" + marginRight + "px;"; 
	}
	
	if (marginBottom != undefined && marginBottom != null && marginBottom.length > 0) {
		style += "margin-bottom:" + marginBottom + "px;"; 
	}
	
	if (color != undefined && color != null && color.length > 0) {
		style += "color:" + color + ";"; 
	}
	
	if (fontSize != undefined && fontSize != null && fontSize.length > 0) {
		style += "font-size:" + fontSize + "px;"; 
	}
	
	if (fontStyle != undefined && fontStyle != null && fontStyle.length > 0) {
		style += "font-style:" + fontStyle + ";"; 
	}
	
	if (fontWeight != undefined && fontWeight != null && fontWeight.length > 0) {
		style += "font-weight:" + fontWeight + ";"; 
	}
	
	var hoverStyle = "";
	
	$(modal).find('[name^="hoverStyle="]').each(function() {
		if ($(this).val() != null && $(this).val().length > 0 && $(this).attr('name').lastIndexOf('Autocomplete') == -1) {
			var px = "";
			if ($(this).next() != undefined && $(this).next() != null && $(this).next().html() == 'px') {
				px = "px";
			}
			var name = $(this).attr('name').replace('hoverStyle=', '');
			hoverStyle += name + ":" +$(this).val() + px + ";";
		}
		
	});
	if (data.datatype == 13) {
		var icon = $(modal).find('input[name=iconButton]').val();
		
		//save label
		data['icon'] = icon;
		var styleIcon = "";
		if ($(modal).find('input[name=showLabel]').prop('checked')) {
			data['showLabel'] = 1;
			$(obj).html(data['labelText']);
			if (data['icon'] != undefined && data['icon'] != null && data['icon'].length > 0) {
				styleIcon = 'style="margin-right: 2px;"';
			}
			
		} else {
			data['showLabel'] = 0;
			$(obj).html("");
		}
		$(obj).prepend('<i class="'+icon+'" '+styleIcon+'></i>');
	}
	data['hoverStyle'] = hoverStyle;
	
	data['style'] = style;
	// daipv split style for Div and input tag
	var itemStyleDiv = "";
	var itemStyleInput = "";
	var arrStyle = style.split(";");
	for (var i = 0; i < arrStyle.length; i++) {
		if(arrStyle[i].indexOf("margin-") > -1){
			itemStyleDiv += arrStyle[i] + ";";
		} else {
			itemStyleInput += arrStyle[i] + ";";
		}
	}
	itemStyleDiv = itemStyleDiv.slice(0, -1);
	itemStyleInput = itemStyleInput.slice(0, -1);
	var areaContent = $(obj).parent().closest('div.areaContent').find("input[name='formAreaType']").val();
	
	if ($(obj).next().prop('name') == 'formElement') {
		 
		if (data.datatype != 23) {
			if (style != null && style.length > 0 ) {
				// daipv add style for div and input tag of datatype is [4, 14, 9]
				if (typeof data.tableType != 'undefined') {
					if (data.datatype == 4 || data.datatype == 14 || data.datatype == 9) {
						$(obj).parent().attr('style', itemStyleDiv  + ";width:100%");
						$(obj).find("input[type='text']").attr('style', itemStyleInput );
					} else if(data.datatype != 20 || data.datatype == 20) {
						if($(obj).closest("th").length == 0) {
							
							$(obj).attr('style', ''  + "width:100%;" + style);
						} else {
							
							$(obj).attr('style', '' + style);
						}
					} else {
						$(obj).attr('style', style);
					}
				} else if (data.areaCustomType == 3) {
					if (data.datatype == 4 || data.datatype == 14 || data.datatype == 9) {
						$(obj).attr('style', itemStyleInput );
						$(obj).closest("div").attr('style', itemStyleDiv  + ";width:100%");
					} else if(data.datatype != 20 || data.datatype == 20) {
						if($(obj).closest("th").length == 0) {
							
							$(obj).attr('style', ''  + "width:100%;" + style);
						} else {
							
							$(obj).attr('style', '' + style);
						}
					} else {
						$(obj).attr('style', style);
					}
				} else if (data.columnname.indexOf('autocomplete') >= 0) {
					if (data.datatype == 0) {
						$(obj).find("input[type='text']").attr('style', itemStyleInput );
						$(obj).attr('style', itemStyleDiv  + ";width:100%");
					} else if(data.datatype != 20 || data.datatype == 20) {
						if($(obj).closest("th").length == 0) {
							
							$(obj).attr('style', ''  + "width:100%;" + style);
						} else {
							
							$(obj).attr('style', '' + style);
						}
					} else {
						$(obj).attr('style', style);
					}
				} else {
					if (data.datatype == 4 || data.datatype == 14 || data.datatype == 9) {
						$(obj).attr('style', itemStyleInput );
						$(obj).parent().parent().closest("div").attr('style', itemStyleDiv  + ";width:100%");
					} else if(data.datatype != 20 || data.datatype == 20) {
						if($(obj).closest("th").length == 0) {
							
							if (areaContent == '2') {
								$(obj).attr('style', ''  + "float: right; width:100%;" + style);
							} else {
								$(obj).attr('style', ''  + "width:100%;" + style);
							}
						} else {
							
							$(obj).attr('style', '' + style);
						}
					} else {
						$(obj).attr('style', style);
					}
				}
			} else {
				if(data.datatype != 20 || data.datatype == 20) {
					if($(obj).closest("th").length == 0) {
						
						if (areaContent == '2') {
							$(obj).attr('style', ''  + "float: right; width:100%;" + style);
						} else {
							$(obj).attr('style', ''  + "width:100%;" + style);
						}
					} else {
						
						$(obj).attr('style', '' + style);
					}
				} else {
					$(obj).attr('style', '');
				}
			}
		} else {
			if (style != null && style.length > 0) {
				$(obj).attr('style', style);
			} else {
				$(obj).attr('style', '');
			}
		}
		if ((data.datatype == 11 || data.datatype == 22) && hoverStyle != null && hoverStyle.length > 0) {
			$(obj).attr('onmouseover', "this.setAttribute('style', '"+hoverStyle+"')");
			$(obj).attr('onmouseout', "this.setAttribute('style', '"+style+"')");
		}
	} else if($(obj).prev().prev().prop('name') == 'formElement'){
		if (style != null && style.length > 0) {
			if(data.datatype != 20 || data.datatype == 20) {
				if ($(obj).closest("th").length == 0) {
					$(obj).attr('style', style + "width:100%");
				} else {
					
					$(obj).attr('style', style + "cursor: pointer;");
				}
			} else {
				$(obj).attr('style', style);
			}
		} else {
			if(data.datatype != 20 || data.datatype == 20) {
				if($(obj).closest("th").length == 0) {
					$(obj).attr('style', ''  + "width:100%");
				} else {
					if ($(obj).attr('style') == "") {
						$(obj).attr('style', ''  + "");
					}
				}
			} else {
				$(obj).attr('style', '');
			}
			
		}
			
		if ((data.datatype == 11 || data.datatype == 22) && hoverStyle != null && hoverStyle.length > 0) {
			$(obj).attr('onmouseover', "this.setAttribute('style', '"+hoverStyle+"')");
			$(obj).attr('onmouseout', "this.setAttribute('style', '"+style+"')");
		}
	} else {
		if (style != null && style.length > 0) {
			// daipv add style for div and input tag of datatype is [2, 8]
			if (data.datatype == 2 || data.datatype == 8) {
				$(obj).parent().attr('style', itemStyleDiv );
				$(obj).find("input[type='text']").attr('style', itemStyleInput );
			} else {
				$(obj).find('input[name=formElement]').prev().attr('style', style);
				if (data.datatype == 5 || data.datatype == 6) {
					$(obj).find('input[name=formElement]').prev().closest("span").find("label").each(function() {
						if(data.datatype != 20 || data.datatype == 20) {
							if($(this).closest("th").length == 0) {
								$(this).attr('style', style);
							} else {
								$(obj).attr('style', style);
							}
						} else {
							$(this).attr('style', style);
						}
					});
				}
			}
			
		} else {
			$(obj).find('input[name=formElement]').prev().attr('style', '');
			if (data.datatype == 5 || data.datatype == 6) {
				$(obj).find('input[name=formElement]').prev().closest("span").find("label").each(function() {
					$(this).attr('style', "");
				});
			}
		}
			
		if ((data.datatype == 11 || data.datatype == 22) && hoverStyle != null && hoverStyle.length > 0) {
			$(obj).find('input[name=formElement]').prev().attr('onmouseover', "this.setAttribute('style', '"+hoverStyle+"')");
			$(obj).find('input[name=formElement]').prev().attr('onmouseout', "this.setAttribute('style', '"+style+"')");
		}
	}
	
	return data;
}

function displayStyle(modal, data) {
	
	$(modal).find("[name='margin-left']").val("");
	
	$(modal).find("[name='margin-top']").val("");
	
	$(modal).find("[name='margin-right']").val("");
	
	$(modal).find("[name='margin-bottom']").val("");
	
	$(modal).find("[name='color']").val("");
	$(modal).find("[name='color']").prev(".input-group-addon").children('i').css('background-color', '');
	
	$(modal).find("[name='font-size']").val("");
	
	$(modal).find("[name='font-style']").val("");
	
	$(modal).find("[name='font-weight']").val("");
	
	$(modal).find("[name='font-weightAutocomplete']").val("");
	
	$(modal).find("[name='text-decoration']").val("");
	
	$(modal).find('[name=iconButton]').val('');
	$(modal).find('span[name=iconPreview]').attr('class', '');
	$(modal).find('[name=showLabel]').prop('checked', true);
	$(modal).find('[name^="hoverStyle="]').val('');
	
	$(modal).find("[name='background-color']").val("");
	$(modal).find("[name='background-color']").prev(".input-group-addon").children('i').css('background-color', '');
	
	
	$(modal).find("[name='height']").val("");
	
	if (data['style'] != undefined && data['style'] != null && data['style'].length > 0) {
		var styles = data['style'].split(';'); 
		
		for (var i = 0; i < styles.length; i++) {
			if (styles[i] == null || styles[i].length == 0) continue;
			
			var style = styles[i].split(':');
			
			if (style[0] != undefined && style[0] != null && style[0].length > 0) {
				$(modal).find("[name='"+style[0]+"']").val(style[1].replace('px', ''));
				$(modal).find("[name='"+style[0]+"']").prev(".input-group-addon").children('i').css('background-color', style[1].replace('px', ''));
			} else {
				$(modal).find("[name='"+style[0]+"']").val('');
				$(modal).find("[name='"+style[0]+"']").prev(".input-group-addon").children('i').css('background-color', '');
			}
			
			if ($(modal).find("[name='"+style[0]+"Autocomplete']") != undefined && $(modal).find("[name='"+style[0]+"Autocomplete']") != null
					&& $(modal).find("[name='"+style[0]+"Autocomplete']").length > 0) {
				$(modal).find("[name='"+style[0]+"Autocomplete']").val(style[1]);
			}
			
		}
	}

	if (data.hoverStyle != undefined && data.hoverStyle != null && data.hoverStyle.length > 0) {
		var hoverStyle = data.hoverStyle;
		var hoverStyleArr = hoverStyle.split(';');
		
		for (var i = 0; i < hoverStyleArr.length; i++) {
			var style = hoverStyleArr[i].split(':');
			if (style != null && style.length ==0) continue;
			var name = 'hoverStyle=' + style[0];
			var nameAutocomplete = name + 'Autocomplete';
			if (style[1] != undefined && style[1] != null && style[1].length > 0) {
				$(modal).find('[name="'+name+'"]').val(style[1].replace('px', ''));
				$(modal).find('[name="'+name+'"]').prev(".input-group-addon").children('i').css('background-color', style[1].replace('px', ''));
			} else {
				$(modal).find('[name="'+name+'"]').val("");
				$(modal).find('[name="'+name+'"]').prev(".input-group-addon").children('i').css('background-color', '');
			}
			
			$(modal).find('[name="'+nameAutocomplete+'"]').val(style[1]);
		}
	}
	
	if (data.datatype == 13) {
		$(modal).find('input[name=iconButton]').val(data['icon']);
		$(modal).find('span[name=iconPreview]').attr('class', data['icon']);
		if (data['icon'] != undefined && data['icon'].length > 0) {
			$(modal).find('span[name=iconPreview]').html('');
		}
		
		var showLabel = "" + data['showLabel'];
		
		if(data['showLabel'] != undefined && data['showLabel'] != null && showLabel.length > 0) {
			if ((data['showLabel'] == '1' || data['showLabel'] == 1)) {
				$(modal).find('input[name=showLabel]').prop('checked', true);
			} else {
				$(modal).find('input[name=showLabel]').prop('checked', false);
			}
		}
	}
	
}

function saveAreaStyle(modal, obj) {
	var style = "";
	var areaType = $(obj).closest('.areaContent').find("input[name='formAreaType']").val();
	//save panel style
	var panelStyle = "";
	var panelSectionStyle = "background-color:#ebebe1;";
	
	$(modal).find('[name^="panelStyle="]').each(function() {
		if ($(this).val() != null && $(this).val().length > 0 && $(this).attr('name').lastIndexOf('Autocomplete') == -1) {
			var name = $(this).attr('name').replace('panelStyle=', '');
			var px = "";
			if ($(this).next() != undefined && $(this).next() != null && $(this).next().html() == 'px') {
				px = "px";
			}
			panelStyle += name + ":" +$(this).val() + px + ";";
		}
		
	});
	
	$(obj).closest('.areaContent').find('input[name=panelStyle]').val(panelStyle);
	if (panelStyle != null && panelStyle != '') {
		
		$(obj).closest('.areaContent').find('.panel-heading').attr('style', panelStyle);
	} 
		
	//save headerStyle
	var headerStyle = "";
	
	$(modal).find('[name^="headerStyle="]').each(function() {
		if ($(this).val() != null && $(this).val().length > 0 && $(this).attr('name').lastIndexOf('Autocomplete') == -1) {
			var name = $(this).attr('name').replace('headerStyle=', '');
			var px = "";
			if ($(this).next() != undefined && $(this).next() != null && $(this).next().html() == 'px') {
				px = "px";
			}
			headerStyle += name + ":" +$(this).val() + px + ";";
		}
	});
	$(obj).closest('.areaContent').find('input[name=headerStyle]').val(headerStyle);
	// daipv: begin process for save header style between area and element in area
	if (headerStyle != null) {
		$(obj).closest('.areaContent').find('.table tbody th, .table thead th:visible').each(function() {
			if (!$(this).is(":visible")) {
				$(this).attr('style',"display: none;" + headerStyle);
			} else {
				$(this).attr('style',headerStyle);
			}
		});
		$(obj).closest('.areaContent').find('.table tbody th, .table thead th').find('label').each(function() {
			var data = $(this).closest('span').find("input[name='formElement']").val();
			if (typeof data !== "undefined") {
				var value = convertToJson(data);
				if (value.style != null && value.style != '' && value.style != undefined) {
					$(this).attr('style', "cursor: pointer;" + value.style);
				}
			}
		});
	}
	
	var tableId = $(obj).closest("div").next("div").find("table").attr("id");
	var styleScoped = "";
	styleScoped += "<style>";
	styleScoped += " #srcgenTableId0 tr th label {";
	styleScoped += " "+headerStyle+" ";
	styleScoped += "}";
	styleScoped += "</style>";
	$(obj).closest('.areaContent').find("#"+tableId+"").find("style").remove();
	$(obj).closest('.areaContent').find("#"+tableId+"").prepend(styleScoped);
	
	//save rowStyle
	var rowStyle = "";
	
	$(modal).find('[name^="rowStyle="]').each(function() {
		if ($(this).val() != null && $(this).val().length > 0 && $(this).attr('name').lastIndexOf('Autocomplete') == -1) {
			var name = $(this).attr('name').replace('rowStyle=', '');
			var px = "";
			if ($(this).next() != undefined && $(this).next() != null && $(this).next().html() == 'px') {
				px = "px";
			}
			rowStyle += name + ":" +$(this).val() + px + ";";
		}
		
	});
	$(obj).closest('.areaContent').find('input[name=rowStyle]').val(rowStyle);
	if (rowStyle != null && rowStyle != "") {
		$(obj).closest('.areaContent').find('.table tbody tr').attr('style', rowStyle);
	}
		
	//save inputStyle
	var inputStyle = "";
	
	$(modal).find('[name^="inputStyle="]').each(function() {
		if ($(this).val() != null && $(this).val().length > 0 && $(this).attr('name').lastIndexOf('Autocomplete') == -1) {
			var name = $(this).attr('name').replace('inputStyle=', '');
			var px = "";
			if ($(this).next() != undefined && $(this).next() != null && $(this).next().html() == 'px') {
				px = "px";
			}
			inputStyle += name + ":" +$(this).val() + px + ";";
		}
		
	});
	$(obj).closest('.areaContent').find('input[name=inputStyle]').val(inputStyle);
	// daipv: begin process for save input style between area and element in area
	if (inputStyle != null) {
		$(obj).closest('.areaContent').find('.table tbody tr td:not(.srcgenControl)').each(function() {
			if (!$(this).is(":visible")) {
				$(this).attr('style',"display: none;" + inputStyle);
			} else {
				$(this).attr('style',inputStyle);
			}
		});
		
		$(obj).closest('.areaContent').find('.table tbody tr td:not(.srcgenControl)').find('input, textarea, label').each(function() {
			var data = $(this).parent().find("input[name='formElement']").val();
			if (typeof data !== "undefined") {
				var value = convertToJson(data.toString());
				if (value.style != null && value.style != '' && value.style != undefined) {
					
					$(this).attr('style', "cursor: pointer;" + value.style);
				}
			}
		});
	}
	// daipv: end process for save input style between area and element in area
	//save alternateRowStyle
	var alternateRowStyle = "";
	
	$(modal).find('[name^="alternateRowStyle="]').each(function() {
		if ($(this).val() != null && $(this).val().length > 0 && $(this).attr('name').lastIndexOf('Autocomplete') == -1) {
			var name = $(this).attr('name').replace('alternateRowStyle=', '');
			var px = "";
			if ($(this).next() != undefined && $(this).next() != null && $(this).next().html() == 'px') {
				px = "px";
			}
			alternateRowStyle += name + ":" +$(this).val() + px + ";";
		}
		
	});
	$(obj).closest('.areaContent').find('input[name=alternateRowStyle]').val(alternateRowStyle);
	
	var icon = $(modal).find('input[name=iconButton]').val();
	
	$(obj).closest('.areaContent').find('input[name=formAreaIcon]').val(icon);
	$(obj).closest('.areaContent').find('i[name="icon-display"]').attr("class", icon);
}



function openAreaStyle(modal, obj) {
	
	$(modal).find('[name^="panelStyle="]').val('');
	$(modal).find('[name^="panelStyle="]').each(function() {
		$(this).prev(".input-group-addon").children('i').css('background-color', '');
	});
	
	$(modal).find('[name^="headerStyle="]').val('');
	$(modal).find('[name^="headerStyle="]').each(function() {
		$(this).prev(".input-group-addon").children('i').css('background-color', '');
	});
	
	$(modal).find('[name^="rowStyle="]').val('');
	$(modal).find('[name^="rowStyle="]').each(function() {
		$(this).prev(".input-group-addon").children('i').css('background-color', '');
	});
	
	$(modal).find('[name^="inputStyle="]').val('');
	$(modal).find('[name^="inputStyle="]').each(function() {
		$(this).prev(".input-group-addon").children('i').css('background-color', '');
	});
	
	$(modal).find('[name^="alternateRowStyle="]').val('');
	$(modal).find('[name^="alternateRowStyle="]').each(function() {
		$(this).prev(".input-group-addon").children('i').css('background-color', '');
	});
	
	var panelStyle = $(obj).closest('.areaContent').find('input[name=panelStyle]').val();
	var panelStyleArr = panelStyle.split(';');
	
	var headerStyle = $(obj).closest('.areaContent').find('input[name=headerStyle]').val();
	var headerStyleArr = headerStyle.split(';');
	
	var rowStyle = $(obj).closest('.areaContent').find('input[name=rowStyle]').val();
	var rowStyleArr = rowStyle.split(';');
	
	var inputStyle = $(obj).closest('.areaContent').find('input[name=inputStyle]').val();
	var inputStyleArr = inputStyle.split(';');
	
	var alternateRowStyle = $(obj).closest('.areaContent').find('input[name=alternateRowStyle]').val();
	var alternateRowStyleArr = alternateRowStyle.split(';');
	
	for (var i = 0; i < panelStyleArr.length; i++) {
		var style = panelStyleArr[i].split(':');
		if (style != null && style.length ==0) continue;
		var name = 'panelStyle=' + style[0];
		var nameAutocomplete = name + 'Autocomplete';
		
		if (style[1] != undefined && style[1] != null && style[1].length > 0) {
			$(modal).find('[name="'+name+'"]').val(style[1].replace('px', ''));
			$(modal).find('[name="'+name+'"]').prev(".input-group-addon").children('i').css('background-color', style[1].replace('px', ''));
		} else {
			$(modal).find('[name="'+name+'"]').val("");
			$(modal).find('[name="'+name+'"]').prev(".input-group-addon").children('i').css('background-color', '');
		}
		
		$(modal).find('[name="'+nameAutocomplete+'"]').val(style[1]);
	}
	
	for (var i = 0; i < headerStyleArr.length; i++) {
		var style = headerStyleArr[i].split(':');
		if (style != null && style.length ==0) continue;
		var name = 'headerStyle=' + style[0];
		var nameAutocomplete = name + 'Autocomplete';
		
		if (style[1] != undefined && style[1] != null && style[1].length > 0) {
			$(modal).find('[name="'+name+'"]').val(style[1].replace('px', ''));
			$(modal).find('[name="'+name+'"]').prev(".input-group-addon").children('i').css('background-color', style[1].replace('px', ''));
		} else {
			$(modal).find('[name="'+name+'"]').val("");
			$(modal).find('[name="'+name+'"]').prev(".input-group-addon").children('i').css('background-color', '');
		}
		$(modal).find('[name="'+nameAutocomplete+'"]').val(style[1]);
	}
	
	for (var i = 0; i < rowStyleArr.length; i++) {
		var style = rowStyleArr[i].split(':');
		if (style != null && style.length ==0) continue;
		var name = 'rowStyle=' + style[0];
		var nameAutocomplete = name + 'Autocomplete';
		
		if (style[1] != undefined && style[1] != null && style[1].length > 0) {
			$(modal).find('[name="'+name+'"]').val(style[1].replace('px', ''));
			$(modal).find('[name="'+name+'"]').prev(".input-group-addon").children('i').css('background-color', style[1].replace('px', ''));
		} else {
			$(modal).find('[name="'+name+'"]').val("");
			$(modal).find('[name="'+name+'"]').prev(".input-group-addon").children('i').css('background-color', '');
		}
		$(modal).find('[name="'+nameAutocomplete+'"]').val(style[1]);
	}
	
	for (var i = 0; i < inputStyleArr.length; i++) {
		var style = inputStyleArr[i].split(':');
		if (style != null && style.length ==0) continue;
		var name = 'inputStyle=' + style[0];
		var nameAutocomplete = name + 'Autocomplete';
		
		if (style[1] != undefined && style[1] != null && style[1].length > 0) {
			$(modal).find('[name="'+name+'"]').val(style[1].replace('px', ''));
			$(modal).find('[name="'+name+'"]').prev(".input-group-addon").children('i').css('background-color', style[1].replace('px', ''));
		} else {
			$(modal).find('[name="'+name+'"]').val("");
			$(modal).find('[name="'+name+'"]').prev(".input-group-addon").children('i').css('background-color', '');
		}
		$(modal).find('[name="'+nameAutocomplete+'"]').val(style[1]);
	}
	
	for (var i = 0; i < alternateRowStyleArr.length; i++) {
		var style = alternateRowStyleArr[i].split(':');
		if (style != null && style.length ==0) continue;
		var name = 'alternateRowStyle=' + style[0];
		var nameAutocomplete = name + 'Autocomplete';
		
		if (style[1] != undefined && style[1] != null && style[1].length > 0) {
			$(modal).find('[name="'+name+'"]').val(style[1].replace('px', ''));
			$(modal).find('[name="'+name+'"]').prev(".input-group-addon").children('i').css('background-color', style[1].replace('px', ''));
		} else {
			$(modal).find('[name="'+name+'"]').val("");
			$(modal).find('[name="'+name+'"]').prev(".input-group-addon").children('i').css('background-color', '');
		}
		$(modal).find('[name="'+nameAutocomplete+'"]').val(style[1]);
	}
	
	var icon = $(obj).closest('.areaContent').find('input[name=formAreaIcon]').val();
	
	$(modal).find('input[name=iconButton]').val(icon);
	$(modal).find('span[name=iconPreview]').attr('class', icon);
	if (icon != undefined && icon.length > 0) {
		$(modal).find('span[name=iconPreview]').html('');
	}
}

function fontWeight() {
	var fontWeight = [
	                  {
	                	  'optionLabel': 'normal',
	                	  'optionValue': 'normal'
	                  },
	                  {
	                	  'optionLabel': 'bold',
	                	  'optionValue': 'bold'
	                  },
	                  {
	                	  'optionLabel': 100,
	                	  'optionValue': 100
	                  },
	                  {
	                	  'optionLabel': 200,
	                	  'optionValue': 200
	                  },
	                  {
	                	  'optionLabel': 300,
	                	  'optionValue': 300
	                  },
	                  {
	                	  'optionLabel': 400,
	                	  'optionValue': 400
	                  },
	                  {
	                	  'optionLabel': 500,
	                	  'optionValue': 500
	                  },
	                  {
	                	  'optionLabel': 600,
	                	  'optionValue': 600
	                  },
	                  {
	                	  'optionLabel': 700,
	                	  'optionValue': 700
	                  },
	                  {
	                	  'optionLabel': 800,
	                	  'optionValue': 800
	                  },
	                  {
	                	  'optionLabel': 900,
	                	  'optionValue': 900
	                  },
	];
	
	return fontWeight;
}
function borderStyle() {
	var borderStyle = [
	                  {
	                	  'optionLabel': 'none',
	                	  'optionValue': 'none'
	                  },
	                  {
	                	  'optionLabel': 'dotted',
	                	  'optionValue': 'dotted'
	                  },
	                  {
	                	  'optionLabel': 'dashed',
	                	  'optionValue': 'dashed'
	                  },
	                  {
	                	  'optionLabel': 'solid',
	                	  'optionValue': 'solid'
	                  },
	                  {
	                	  'optionLabel': 'double',
	                	  'optionValue': 'double'
	                  },
	                  {
	                	  'optionLabel': 'groove',
	                	  'optionValue': 'groove'
	                  },
	                  {
	                	  'optionLabel': 'ridge',
	                	  'optionValue': 'ridge'
	                  },
	                  {
	                	  'optionLabel': 'inset',
	                	  'optionValue': 'inset'
	                  },
	                  {
	                	  'optionLabel': 'outset',
	                	  'optionValue': 'outset'
	                  },
	                  {
	                	  'optionLabel': 'ridge',
	                	  'optionValue': 'ridge'
	                  }
	                  
	];
	
	return borderStyle;
}

function reloadStyle(table) {
	
	var inputStyle = $(table).closest('.areaContent').find('input[name=inputStyle]').val();
	var rowStyle = $(table).closest('.areaContent').find('input[name=rowStyle]').val();
	var headerStyle = $(table).closest('.areaContent').find('input[name=headerStyle]').val();
	var panelStyle = $(table).closest('.areaContent').find('input[name=panelStyle]').val();
	if (inputStyle != undefined && inputStyle != null && inputStyle.length > 0)
		$(table).find('tbody tr td').each(function(){
			$(this).attr('style', inputStyle + $(this).attr('style'));
		});
	
	if (rowStyle != undefined && rowStyle != null && rowStyle.length > 0)
		$(table).find('tbody tr').each(function(){
			$(this).attr('style', rowStyle + $(this).attr('style'));
		});
	
	if (headerStyle != undefined && headerStyle != null && headerStyle.length > 0)
		$(table).find('tbody th,thead th').each(function(){
			$(this).attr('style', headerStyle + $(this).attr('style'));
		});
}

function showElementToChange($modal, data) {
	$modal.find('input[name=elementType]').each(function() {
		if ($(this).attr('value') == data.datatype) {
			$(this).closest('label').hide();
		} else {
			$(this).closest('label').show();
		}
	});
	$modal.find('input[name=elementType][value=""]').prop('checked', true);
}

function changeElementType($modal,obj, data) {
	
	if ($modal.find('input[name=elementType]:checked').val().length == 0) return false;
	
	var areaType = $(obj).closest('.areaContent').find('input[name=formAreaType]').val();
	
	var result = {};
	switch(data.datatype) {
		case '5':
		case '6':
			result['obj'] = obj;
			$(result['obj']).children().each(function() {
				if ($(this).attr('name') == undefined || $(this).attr('name') != 'formElement' && data.physicaldatatype != 8) {
					$(this).remove();
				}
			});
			break;
		case '7':
			var $parent = $(obj).closest('span');
			
			result['obj'] = $parent;
			$(obj).remove();
			break;
	}
	if(data.physicaldatatype != 8) {
		data.datatype = $modal.find('input[name=elementType]:checked').val();
	}
	if (data.datatype == '7' && data.physicaldatatype != 8) {
		if (areaType == '3') {
			$(result['obj']).append('<select ondblclick="openDialogComponentListSettingSection(this)" name="select" class="form-control qp-input-select"><option></option><option>'+dbMsgSource['sc.screendesign.0317']+'</option><option>'+dbMsgSource['sc.screendesign.0318']+'</option></select>');
		} else {
			$(result['obj']).append('<select ondblclick="openDialogComponentListSetting(this)" name="select" class="form-control qp-input-select"><option></option><option>'+dbMsgSource['sc.screendesign.0317']+'</option><option>'+dbMsgSource['sc.screendesign.0318']+'</option></select>');
		}
		$(result['obj']).removeAttr('onclick');
		$(result['obj']).removeAttr('ondblclick');
		result['obj'] = $(result['obj']).find('select');
		
	} else if (data.physicaldatatype != 8){
		if ($(result['obj']).attr('ondblclick') == undefined || $(result['obj']).attr('ondblclick').length == 0) {
			if (areaType == '3') {
				$(result['obj']).attr('ondblclick', 'openDialogComponentListSettingSection(this)');
			} else {
				$(result['obj']).attr('ondblclick', 'openDialogComponentListSetting(this)');
			}
		}
	}
	
	result['data'] = data;
	return result;
}

function changeValue(obj) {
	if($(obj).prop("checked") == true) {
		$(obj).prop("value",1);
	}
	if($(obj).prop("checked") == false) {
		$(obj).prop("value",0);
	}
}

function loadActionListComponent(modal, data) {
	var parent = $(modal);
	$(parent).find('#listEvent').empty();
	$(parent).find('#actionType').find('option:eq(0)').prop("selected", "selected");
	if (data.events != undefined && data.events != null && data.events != 'null') {
		for (var i = 0; i < data.events.length; i++) {
			
			var event = data.events[i];
			
			var actionType = event.eventtype;
			
			if (actionType == 1) {
				var $reloadEvent = $('#action-reload-template').tmpl();
				
				$(parent).find('#listEvent').append($reloadEvent);
			} else if (actionType == 2) {
				var $submitEvent = $('#action-submit-template').tmpl();
				
				$(parent).find('#listEvent').append($submitEvent);
			}
			
			$(parent).find('#listEvent').find('.onchange-event').each(function(index) {
				var event = $(this);
				$(event).find('input[name=radEffectAreaType]').each(function() {
					$(this).attr('name', 'radEffectAreaType' + index);
				});
			});
			
			var newEvent = $(parent).find('#listEvent').find('.onchange-event:last'); 
			
			$(newEvent).find('#effectAreaAutocompleteId').attr('onfocus', 'checkEffectAreaType(this)');
			if (actionType == 1) {
				//init data
				$(newEvent).find('input[name^=radEffectAreaType][value='+event.effectareatype+']').prop('checked', true);
				$(newEvent).find('input[name=effectArea]').val(event.effectarea);
				$(newEvent).find('input[name=effectAreaAutocomplete]').val(getCode(event.effectarea));
				$(newEvent).find('input[name=businessLoginAutocomplete]').val(event.blogicid);
				$(newEvent).find('input[name=businessLoginAutocompleteAutocomplete]').val(event.blogicname);
				$(newEvent).find('input[name=blogicSetting]').val(convertToString(event));
			}
			$.qp.initialCatAutocomplete($(newEvent).find('#effectAreaAutocompleteId'));
			$.qp.initialAllPicker($(newEvent));
		}
		
		$(parent).find('#listEvent').sortable({
			  helper: function(e, ui) {
			   ui.children().each(function() {
			    $(this).width($(this).width());
			   });
			   return ui;
			  },
			  update: function(event, ui) {
			  },
			  items: '.onchange-event',
			  cursor: 'move',
			  handle: '.glyphicon-sort',
			  scroll: false
		});
	}
	
}


function openEventArea(modal, obj) {
	var value = $(obj).closest('span').find('input[name=formAreaEvent]').val();
	
	$(modal).find('#events-setting').empty();
	
	if (value.length == 0) {
		return;
	}
	
	var formCode = $(modal).find('input[name=formcode]').val();
	var areaCode = $(modal).find('input[name=areaCode]').val();
	
	var events = convertToJson(value);
	
	if (events.requireConstraints != undefined) {
		
		for (var i = 0; i < events.requireConstraints.length; i++) {
			var temp = $('#area-event-template').tmpl();
			$(modal).find('#events-setting').append(temp);
			
			$(modal).find('#events-setting').find('table[name=require-constraint]:last').find('#if-constraint').find('tbody').empty();
			for (var j = 0; j < events.requireConstraints[i]['ifRequired'].length; j++) {
				var tempIf = $('#if-constraint-template').tmpl();
				$(modal).find('#events-setting').find('table[name=require-constraint]:last').find('#if-constraint').append(tempIf);
				$(modal).find('#events-setting').find('table[name=require-constraint]:last').find('#if-constraint').find('tr:last').find('input[name=screenItemCodeAutocomplete]').val(getCode(events.requireConstraints[i]['ifRequired'][j]));
				$(modal).find('#events-setting').find('table[name=require-constraint]:last').find('#if-constraint').find('tr:last').find('input[name=screenItemCode]').val(events.requireConstraints[i]['ifRequired'][j]);
			}
			
			$(modal).find('#events-setting').find('table[name=require-constraint]:last').find('#then-constraint').find('tbody').empty();
			for (var j = 0; j < events.requireConstraints[i]['thenMustRequired'].length; j++) {
				var tempThen = $('#then-constraint-template').tmpl();
				$(modal).find('#events-setting').find('table[name=require-constraint]:last').find('#then-constraint').append(tempThen);
				$(modal).find('#events-setting').find('table[name=require-constraint]:last').find('#then-constraint').find('tr:last').find('input[name=screenItemCodeAutocomplete]').val(getCode(events.requireConstraints[i]['thenMustRequired'][j]));
				$(modal).find('#events-setting').find('table[name=require-constraint]:last').find('#then-constraint').find('tr:last').find('input[name=screenItemCode]').val(events.requireConstraints[i]['thenMustRequired'][j]);
			}
			
		}
		
		$(modal).find('#events-setting').find('.qp-input-autocomplete').each(function() {
			$(this).attr('arg02', formCode + '.' + areaCode);
			$.qp.initialCatAutocomplete($(this));
		});
		
	}
	
}

function saveEventArea(modal, obj) {
	var events = {
			requireConstraints :[]
	};
	
	$(modal).find('table[name=require-constraint]').each(function(){
		var ifTr = $(this).find('tr[name=if]');
		var event = {};
		event['ifRequired'] = [];
		$(ifTr).find('input[name=screenItemCode]').each(function() {
			event['ifRequired'].push($(this).val());
		});
		
		var thenTr = $(this).find('tr[name=then]');
		event['thenMustRequired'] = [];
		$(thenTr).find('input[name=screenItemCode]').each(function() {
			event['thenMustRequired'].push($(this).val());
		});
		events.requireConstraints.push(event);
	});
	
	$(obj).closest('span').find('input[name=formAreaEvent]').val(convertToString(events));
}

function addRequireConstraint(obj) {
	
	var areaCode = $(obj).closest('.modal-body').find('input[name=areaCode]').val();
	
	var temp = $('#area-event-template').tmpl();
	
	var parent = $(obj).closest('.panel-body');
	$(parent).find('#events-setting').prepend(temp);
	var target = $(obj).closest('.modal').data("data");
	var indexArea = $(target).closest('.areaContent').index();
	
	var formCode = $(obj).closest('.tab-content').find('input[name=formcode]').val();
	$(parent).find('#events-setting').find('.qp-input-autocomplete').each(function() {
		$(this).attr('arg02', formCode + '.' + areaCode);
		$(this).attr('arg03', formCode + '.' + indexArea);
		$.qp.initialCatAutocomplete($(this));
	});
}
function removeRequireConstraint(obj) {
	if(confirm(fcomMsgSource['inf.sys.0014'])){
		$(obj).closest('table[name=require-constraint]').prev('br').remove();
		$(obj).closest('table[name=require-constraint]').remove();		
	}
}
function getCode(code) {
	var itemCode = '';
	if (code != undefined && code != null && code.length > 0) {
		var startIndex = code.lastIndexOf('.');
		itemCode = code.substring(startIndex + 1, code.length);
	}
	
	return itemCode
}
function displayParamAction(modal, data) {
	
	if (data.navigateTo == undefined || data.navigateTo == 'undefined' ||data.navigateTo == null ) {
		data['navigateTo'] = "";
	}
	
	if (data.navigateToText == undefined || data.navigateToText == 'undefined' ||data.navigateToText == null ) {
		data['navigateToText'] = "";
	}
	
	$(modal).find("input[name='navigateTo']").val(data.navigateTo);
	$(modal).find("input[name='navigateToAutocomplete']").val(htmlDecode(data.navigateToText));
}

function saveActionParameter($modal, data) {
	var parameters = "";
	
	if (data.actiontype == 0) {
		var isValid = true;
		var errorMessage = '';
		var arrScreenItemCode = [];
		$modal.find('#dialog-form-parameter-tbl-parameter').find('tbody tr:visible').each(function (i){
			if($(this).find("input[name=parameterAttribute]").val() != undefined && $(this).find("input[name=parameterAttribute]").val().length > 0){
				
				var screenItemCode = "";
				
				if($(this).find("input[name=screenItemCode]").val() != undefined && $(this).find("input[name=screenItemCode]").val().length > 0){
					screenItemCode = $(this).find("input[name=screenItemCode]").val();
				} 
				
				if (screenItemCode.length == 0) {
					errorMessage = "Please set screen code for " + $(this).find("input[name=parameterAttribute]").val();
					isValid = false;
					return;
				}
				arrScreenItemCode.push(screenItemCode);
				var dataType = $(this).find("input[name=parameterAttributeDatatype]").val();
				var dataTypeId = $(this).find("input[name=parameterAttributeDatatypeId]").val();
				var currentData = getDataByScreenItemCode(screenItemCode);
				//check datatype
				var currentBaseType = getBasetype(currentData);
				if (currentBaseType.accept.indexOf(dataType) == -1  && currentData.datatype != undefined) {
					isValid = false;
					errorMessage = dbMsgSource['sc.screendesign.0294'].replace("{0}",currentBaseType.baseType).replace("{1}",screenItemCode).replace("{2}",$(this).find("input[name=parameterAttribute]").val());
					//return;
				}
				
				parameters += $(this).find("input[name=parameterAttribute]").val() + "π" + screenItemCode + "π" + dataTypeId;
				parameters += "�";
			}
		});
		
		if (!isValid) {
			alert(errorMessage);
			//return false;
		}
		
		var isExists = false;
		var existsCode = '';
		for (var i = 0; i < arrScreenItemCode.length; i++) {
			
			for (var j = i + 1; j < arrScreenItemCode.length; j++) {
				if (arrScreenItemCode[i] == arrScreenItemCode[j]) {
					isExists = true;
					existsCode = arrScreenItemCode[i];
					break;
				}
			}
			if (isExists) {
				break;
			}
		}
		
		if (isExists) {
			alert(existsCode + ' is exists');
			return false;
		}
	} else if (data.actiontype == 1) {
		var isValid = true;
		var errorMessage = '';
		var arrScreenItemCode = [];
		
		$modal.find('#blogic-param').find('tbody tr:visible').each(function (i){
			if ($(this).find("input[name=screenItemCode]") != undefined && $(this).find("input[name=screenItemCode]") != null
					&& $(this).find("input[name=screenItemCode]").length > 0
					&& $(this).find("input[name=screenItemCode]").val() != '') {
				var screenItemCode = $(this).find("input[name=screenItemCode]").val();
				
				var currentData = getDataByScreenItemCode(screenItemCode);
				//check datatype
				var currentBaseType = getBasetype(currentData);
				
				if (currentBaseType == null) return;
				
				if (currentBaseType.accept.indexOf($(this).find("td:eq(2)").find("div").html()) == -1 && currentData.datatype != 21) {
					isValid = false;
					errorMessage = dbMsgSource['sc.screendesign.0294'].replace("{0}",currentBaseType.baseType).replace("{1}", getCode(screenItemCode)).replace("{2}",$(this).find("td:eq(1)").html());
					return;
				}
				
				parameters += $(this).attr("beanid") + "π" + screenItemCode + "π" + $(this).attr("isobjectdatatype") + "π" + $(this).find("td:eq(1)").html();
				parameters += "�";
			}
		});
		
		
		if (!isValid) {
			alert(errorMessage);
			//return false;
		}
		
	}
	
	if(parameters.charAt(parameters.length-1) == '�'){
		parameters = parameters.substring(0, parameters.length - 1);
	}
	
	if (data.parameters != undefined) {
		data.parameters = parameters;
	} else {
		data["parameters"] = parameters;
	}
	
	return data;
}

function addRadioChooseMappingOutput(modal, obj, data) {
	
	//remove radio
	$(modal).find(".mapping-bean").find("tbody tr").each(function(){
		$(this).find('td:last').html('');
	});
	
	var $area = $(obj).closest(".areaContent");
	var formAreaType = $area.find("input[name=formAreaType]").val();
	var whereIsList = "true";
	
	if (formAreaType != 1) {
		whereIsList = "false"
	}
	
	$(modal).find(".mapping-bean").find("tbody tr[isobjectdatatype=0][isobjectarray="+whereIsList+"]").each(function(){
		var $tr = $(this);
		var rowIndex = $tr.index();
		var level = $tr.attr('level');
		var countLevel = (level.match(/\./g) || []).length;
				
		$(modal).find(".mapping-bean").find("tbody tr").each(function(index){
			if (index > rowIndex && $(this).attr('isobjectdatatype') != 0) {
				var levelChild = $(this).attr('level');
				var countLevelChild = (levelChild.match(/\./g) || []).length;
				
				if (levelChild.indexOf(level) != -1 && countLevelChild == countLevel + 1) {
					$(this).find('td:last').html('');
					if (data.outputBeanId != undefined) {
						if ($(this).attr('outputbeanid') == data.outputBeanId) {
							$(this).find('td:last').append("<input type='radio' name='radChooseBean' checked value='"+$(this).attr('outputbeanid')+"' />");
						} else {
							$(this).find('td:last').append("<input type='radio' name='radChooseBean' value='"+$(this).attr('outputbeanid')+"' />");
						}
					} else {
						$(this).find('td:last').append("<input type='radio' name='radChooseBean' value='"+$(this).attr('outputbeanid')+"' />");
					}
					
				}
			}
		});
		
		
	});
}
function checkExistsScreenItemScreenAreaEvent(modal) {
	
	var parent = $(modal).find('tr[name=if]');
	
	var isValid = true;
	
	$(parent).find('input[name=screenItemCode]').each(function(i) {
		var item = $(this);
		$(parent).find('input[name=screenItemCode]').each(function(j) {
			if (j > i) {
				if ($(item).val() == $(this).val()) {
					isValid = false;
					return;
				}
			}
			
		});
	});
	
	if (!isValid) {
		$.qp.showAlertModal(dbMsgSource['sc.screendesign.0290']);
		return isValid;
	}
	
	parent = $(modal).find('tr[name=then]');
	
	$(parent).find('input[name=screenItemCode]').each(function(i) {
		var item = $(this);
		$(parent).find('input[name=screenItemCode]').each(function(j) {
			if (j > i) {
				if ($(item).val() == $(this).val()) {
					isValid = false;
					return;
				}
			}
			
		});
	});
	
	if (!isValid) {
		$.qp.showAlertModal(dbMsgSource['sc.screendesign.0290']);
		return isValid;
	}
	return isValid;
}
function loadScreenItemCodeByArea(obj) {
	var results = [];
	var $forms = $('.form-area-content');
	var parentItemCode = obj.arg02;
	var areaCodeParam = '';
	var formCodeParam = '';
	var itemCodeParam = '';
	var currentIndex = -1;
	
	var params = obj.arg02.split('.');
	var arg03 = obj.arg03.split('.');
	
	if (params.length == 1) {
		itemCodeParam  =  params[0];
	} else if (params.length == 2) {
		formCodeParam = params[0];
		areaCodeParam = params[1];
		
		if (areaCodeParam.length == 0) {
			currentIndex = arg03[1];
		}
	} else if (params.length == 3) {
		formCodeParam = params[0];
		areaCodeParam = params[1];
		itemCodeParam  =  params[2];
	}
	
	$forms.each(function(formIndex) {
		var $form = $(this);
		var formCode = $form.find('input[name=screenFormFormActionCode]').val();
		
		if (formCodeParam == formCode) {
			$form.find('.areaContent').each(function(areaIndex){
				var $area = $(this);
				var areaType = $area.find('input[name=formAreaType]').val();
				if (areaType != 2) {
					var code = $area.find('input[name=formAreaCode]').val();
					var name = $area.find('input[name=formAreaCaptionText]').val();
					
					var areaCode = code;
					if (code.length == 0) {
						areaCode = name;
					}
					var index = $(this).index();
					if (areaCode == areaCodeParam || index == currentIndex) {
						
						$area.find('input[name=formElement]').each(function(itemIndex) {
							if ($(this).val() != undefined && $(this).val().length > 0) {
								var data = convertToJson($(this).val());
								if (data.datatype != undefined && data.datatype.length > 0  && data.datatype != 20 && data.datatype != 13 && data.datatype != 11) {
									var itemCode = formCode + '.' + areaCode + '.' + data.columnname;
									if (data.columnname != undefined) {
										if (params.length == 3) {
											if (itemCodeParam == data.columnname) {
												results.push({
													"optionLabel" :data.columnname,
													"optionValue" : itemCode,
													"level" : itemIndex,
													"hasChild": false
												});
											}
										} else {
											results.push({
												"optionLabel" :data.columnname,
												"optionValue" : itemCode,
												"level" : itemIndex,
												"hasChild": false
											});
										}
									}
								}
							}
							
						});
					}
				}
			});
		}
	});
	
	return results;
}
function initCatAutocomplete(obj) {
	var parent = $(obj).closest('.panel-body');
	
	var areaCode = $(obj).closest('.modal-body').find('input[name=areaCode]').val();
	
	var formCode = $(obj).closest('.tab-content').find('input[name=formcode]').val();
	$(parent).find('#events-setting').find('.qp-input-autocomplete').each(function() {
		$(this).attr('arg02', formCode + '.' + areaCode);
		$.qp.initialCatAutocomplete($(this));
	});
}

function saveHeaderSort(modal,obj,currenHeaderLabel,currentLengthOfTH) {
	
	var screenAreaId = $(obj).closest("div").find("input[name='formAreaIdStore']").val();
	var screenId = $(obj).closest("#screenDesignForm").find("input[name='screenId']").val();
	var formCode = $(modal).find("input[name='formcode']").val();
	var areaCode = $(obj).closest("div[class='panel-heading']").find("input[name='formAreaCode']").val();
	var screenAreaSort = {}; 
	
	/*var arrTH = [];
	var obj = $(modal).data("target");
	var divPanelHeading = $(obj).closest("div[class='panel-heading']");
	arrTH = $(divPanelHeading).next("div").find("th:visible");*/
	
	var newHeaderLabel = "";
	newHeaderLabel = $(modal).find("input[name='headerLabelRow']").val();
	
	screenAreaSort.enableSort = $(modal).find("input[id='enableSort']").prop("checked");
	if(screenAreaSort.enableSort == true) {
		screenAreaSort.sqlId = $(modal).find("input[name='sqlname']").val();
		screenAreaSort.sqlCode = $(modal).find("input[name='sqlnameAutocomplete']").val();
		screenAreaSort.areaSorts = [];
		
		var arrHeaderColumn = $(modal).find("table[id='header-sort']").find("td[name='header-column']");
		var arrSqlColumn = $(modal).find("table[id='header-sort']").find("td[name='sql-column']");
		//Save header sort - sql output
		$(modal).find("table[id='header-sort']").find("tbody tr").each(function() {
			screenAreaSort.areaSorts.push({
				screenAreaCode : formCode + "." + areaCode,
				screenItemCode : $(this).find("td[name='header-column']").find("span").text(),
				sqlColumnCode : $(this).find("input[name='sqlColumnOutput']").val(),
				screenId : screenId,
				screenAreaId : screenAreaId
			});
		});
		if(newHeaderLabel > currenHeaderLabel) {
			var numberAdd = newHeaderLabel - currenHeaderLabel;
			var newLengthOfTH = numberAdd * currentLengthOfTH;
			for(var i = 0; i < newLengthOfTH; i++) {
				screenAreaSort.areaSorts.push({
					screenAreaCode : formCode + "." + areaCode,
					screenItemCode : "",
					sqlColumnCode : "",
					screenId : screenId,
					screenAreaId : screenAreaId
				});
			}
		}
		if(newHeaderLabel < currenHeaderLabel) {
			var numberRemove = currenHeaderLabel - newHeaderLabel;
			var newLengthOfTH = numberRemove * currentLengthOfTH;
			for(var i = 0; i < newLengthOfTH; i++) {
				screenAreaSort.areaSorts.pop({
					screenAreaCode : formCode + "." + areaCode,
					screenItemCode : "",
					sqlColumnCode : "",
					screenId : screenId,
					screenAreaId : screenAreaId
				});
			}
		}
	}
	return convertToString(screenAreaSort);
}

function openHeaderSort(modal,obj) {
	var areaSortContent = $(obj).closest("div").find("input[name='formScreenAreaSortValue']").val();
	var sortcontent = convertToJson(areaSortContent);
	var formCode = $(modal).find("input[name='formcode']").val();
	var areaCode = $(obj).closest("div[class='panel-heading']").find("input[name='formAreaCode']").val();
	if(sortcontent.enableSort == true) {
		$(modal).find("input[id='enableSort']").prop("checked",true);
	} else {
		$(modal).find("input[id='enableSort']").prop("checked",false);
	}
	if(sortcontent.sqlCode != null && sortcontent.sqlCode != "") {
		$(modal).find("input[name='sqlnameAutocomplete']").val(sortcontent.sqlCode);
	} else {
		$(modal).find("input[name='sqlnameAutocomplete']").val("");
	}
	if(sortcontent.sqlId != null && sortcontent.sqlId != "") {
		$(modal).find("input[name='sqlname']").val(sortcontent.sqlId);
	} else {
		$(modal).find("input[name='sqlname']").val("");
	}
	var table = $(modal).find("table[id='header-sort']");
	$(table).find("tbody").empty();
	var index = 1;
	
	var arrTH = [];
	var obj = $(modal).data("target");
	var divPanelHeading = $(obj).closest("div[class='panel-heading']");
	arrTH = $(divPanelHeading).next("div").find("th");
/*	
	var arrTHNotNone = [];
	arrTH.each(function() {
		if($(this).css("display") != "none") {
			arrTHNotNone.add($(this));
		}
	});*/
	
	var arrObj = [];
	arrObj = sortcontent.areaSorts;
	
	for(var i = 0; i < arrTH.length; i++) {
		var sqlColumnCode = "";
		if(arrObj != undefined) {
			if(arrObj[i] != undefined) {
				if(arrObj[i].sqlColumnCode != "" && arrObj[i].sqlColumnCode != undefined) {
					sqlColumnCode = arrObj[i].sqlColumnCode;
				} else {
					sqlColumnCode = "";
				}
			}
		} else {
			sqlColumnCode = "";
		}
		
		var displayNone = "";
		if($(arrTH[i]).find("span").find("label").text() == "" || $(arrTH[i]).find("span").find("label").text() == null || $(arrTH[i]).css("display") == "none") {
			displayNone = " style=\"display:none\" ";
		}
		
		var tdContent = ""
		    + "<tr "+displayNone+">"
				+ "<td>"+index+"</td>"
				+ "<td name=\"header-column\"><span>"+$(arrTH[i]).find("span").find("label").text()+"</span><input type=\"hidden\" name=\"headerColumn\" value=\""+$(arrTH[i]).find("span").find("label").text()+"\"/> "
				+ "<input type=\"hidden\" name=\"hiddenSortValue\" value=\""+formCode+"."+areaCode+"."+$(arrTH[i]).find("span").find("label").text()+"\""
				+ "</td>"
				+ "<td name=\"sql-column\">"
						+ "<div class=\"input-group date\" style=\"width:100%\"><input type=\"text\" class=\"form-control qp-input-autocomplete ui-autocomplete-input\" name=\"sqlColumnOutputAutocomplete\" id=\"sqlColumnOutputAutocompleteId\" " 
						+ " optionvalue=\"optionValue\" value=\""+sqlColumnCode+"\" optionlabel=\"optionLabel\" selectsqlid=\"getSqlColumnOutput\" emptylabel=\"\" onselectevent=\"\" onchangeevent=\"\" onremoveevent=\"\" placeholder=\"Search...\" mustmatch=\"false\" minlength=\"0\" arg01=\"\" arg02=\"\" arg03=\"\" arg4=\"\" arg05=\"\" arg06=\"\" arg07=\"\" arg08=\"\" arg09=\"\" arg10=\"\" arg11=\"\" arg12=\"\" arg13=\"\" arg14=\"\" arg15=\"\" arg16=\"\" arg17=\"\" arg18=\"\" arg19=\"\" arg20=\"\" placeholder=\"\" autocomplete=\"on\">"
						+ "	<input type=\"hidden\" name=\"sqlColumnOutput\" value=\""+sqlColumnCode+"\">" 
						+ "	<span class=\"input-group-addon dropdown-toggle\" data-dropdown=\"dropdown\">" 
						+ "	<span class=\"caret\"></span> " 
						+ "	</span>"
						+ "</div>"
				+ "</td>"
			+ "</tr>";
		$(table).find("tbody").append(tdContent);
		if($(arrTH[i]).find("span").find("label").text() != "" && $(arrTH[i]).find("span").find("label").text() != null && $(arrTH[i]).css("display") != "none") {
			index++;
		}
	}
	
	$.qp.initialAutocomplete($(table).find(".qp-input-autocomplete")); 
	
	var arrTr = $(modal).find("#modal-table-list-setting-header-sort").find("#tbl_list_result > tbody > tr");
	
	if(sortcontent.enableSort == true) {
		arrTr.each(function(){
			$(this).css("display","");
		});
	} else {
		$(modal).find("#modal-table-list-setting-header-sort").find("#tbl_list_result").find("tr[class='sql-name']").css("display","none");
	}
	
}
function checkRequireEvent(modal) {
	
}
function initOptionAutocomplete(modal, value) {
	
	if ($(modal).closest(".tab-pane").find("input[name=dataSourceType]") != undefined && $(modal).closest(".tab-pane").find("input[name=dataSourceType]") != null 
			&& $(modal).closest(".tab-pane").find("input[name=dataSourceType]").length > 0) {
		
		var $column =$(modal).find('#optionlabelAutocompleteId');
		var nextHidden = $column.next("input[type=hidden]");
		$column.val("");
		$column.attr("arg01",value);
		$column.val("");
		$column.data("ui-autocomplete")._trigger("change");
		
		var $columnValue = $(modal).find('#optionvalueAutocompleteId');
		var nextHiddenValue = $columnValue.next("input[type=hidden]");
		$columnValue.val("");
		$columnValue.attr("arg01",value);
		$columnValue.val("");
		$columnValue.data("ui-autocomplete")._trigger("change");
		
	} else {
		//find option label, option value autocomplete
		var url = CONTEXT_PATH + "/screendesign/getOptionByAutocompleteId?autocompleteId="+value +"&r="+Math.random();
		
		var data = $.qp.getTextResult(url);
		if (data.length > 0) {
			var obj = data.split(",");
			if (obj != null && obj.length > 0) {
				$(modal).find("td[optionLabel='optionLabel']").html(obj[0]);
				$(modal).find("td[optionValue='optionValue']").html(obj[1]);
				$(modal).find("input[name=allowAnyInput][value="+obj[2]+"]").prop("checked", true);
			}
			
			var inputs = obj[3];
			var inputArr = obj[3].split('-');
			
			if (inputArr.length > 0) {
				$(modal).find("#settingInput tbody").empty();
				for (var j = 0, k = 0; j < inputArr.length; j++, k++) {
					
					var objInput = inputArr[j].split('|');
					if (objInput.length == 4) {
						var name = objInput[0]; 
						var code = objInput[1];
						var datatype = objInput[2];
						var id = objInput[3];
						
						var content = "<tr>" +
										"	<td>"+(k + 1)+"<input type='hidden' name='inputId' value='"+id+"' /></td>" +
										"	<td>"+name+"<input type='hidden' name='inputName' value='"+name+"' /></td>" +
										"	<td>"+code+"<input type='hidden' name='inputCode' value='"+code+"' /></td>" +
										"	<td><span>"+CL_DATATYPE_PARAMETERS[datatype]+"</span><input type='hidden' name='inputDataType' value='"+datatype+"' /></td>" +
										"   <td>"+
										'	<div class="input-group " style="">' + 
										'		<input sourcecallback="getEffectArea" sourcetype="local" arg01="2"  type="text" name="screenItemDependencyAutocomplete" id="screenItemDependencyAutocompleteId" class="form-control  ui-autocomplete-input" value="" optionvalue="optionValue" optionlabel="optionLabel" selectsqlid="" emptylabel="" onselectevent="" onchangeevent="" onremoveevent="" mustmatch="true" maxlength="" minlength="" arg01="2" arg02="" arg03="" arg04="" arg05="" arg06="" arg07="" arg08="" arg09="" arg10="" arg11="" arg12="" arg13="" arg14="" arg15="" arg16="" arg17="" arg18="" arg19="" arg20="" sourcetype="local" sourcecallback="getEffectArea" placeholder="Search…" autocomplete="off" previousvalue="" previouslabel="" selectedvalue="false">' + 
										'		<input type="hidden" class="" name="screenItemDependency" value=""><span class="input-group-addon dropdown-toggle" data-dropdown="dropdown" style="cursor: pointer;" onclick="$.qp.searchAutocompleteData(this)"> <span class="caret"></span></span>' + 
										'	</div>' + 
										"</td>" + 
										"</tr>";
						$(modal).find('#settingInput tbody').append(content);
					}
				}
				$.qp.initialCatAutocomplete($(modal).find('input[name=screenItemDependencyAutocomplete]'));
				$(modal).find("#autocompleteTitle").show();
				$(modal).find("#autocompleteInputSetting").show();
			} else {
				$(modal).find("#autocompleteInputSetting").hide();
				$(modal).find("#autocompleteTitle").hide();
			}
			
		} else {
			$(modal).find("td[optionLabel='optionLabel']").html("");
			$(modal).find("td[optionValue='optionValue']").html("");
			$(modal).find("input[name=allowAnyInput][value=1]").prop("checked", true);
			
			$(modal).find("#autocompleteTitle").hide();
			$(modal).find("#autocompleteInputSetting").hide();
		}
	}
}
function findColumnByDatasource(obj){
	var value = $(obj.target).next("input[type=hidden]").val();
	var modal = $(obj.target).closest('#sql-setting');
	if (value == undefined || value == '') {
		$(modal).find("td[optionLabel]").toggle();
		$(modal).find("td[optionValue]").toggle();
		$(modal).find("td[optionLabel]").attr("style","");
		$(modal).find("td[optionValue]").attr("style","");
		$(modal).find("tr[id='autocompleteTitle']").hide();
		$(modal).find("tr[id='autocompleteInputSetting']").hide();
	} 
	initOptionAutocomplete(modal, value);
	
	$(modal).find("#settingInput tbody tr:first").find("input[name=screenItemDependencyAutocomplete]").val('this.value');
	$.qp.disableAutocomplete($(modal).find("#settingInput tbody tr:first").find("input[name=screenItemDependency]"));
}

function getBasetype(data) {
	var baseType = '';
	var accept = [];
	switch (data.datatype) {
	case '0': case '5': case '6': case '7':
		if (data.baseType == 1) {
			baseType = 'Character varying';
			accept = ['char', 'String'];
		} else if (data.baseType == 3) {
			baseType = 'Text';
			accept = ['String'];
		} else if (data.baseType == 5) {
			baseType = 'Integer';
			accept = ['short', 'int', 'Integer', , 'Short'];
		} else if (data.baseType == 6) {
			baseType = 'Smallint';
			accept = ['short', 'Short'];
		} else if (data.baseType == 7) {
			baseType = 'Bigint';
			accept = ['short', 'int', 'long', 'Long', 'Integer', 'Short'];
		}
		
		break;
	case '2':
		baseType = "Integer";
		accept = ['short', 'int', 'long', 'Long', 'Integer', 'Short'];
		break;
	case '3':
		baseType = "Decimal";
		accept = ['short', 'int', 'long', 'float', 'double', 'BigDecimal', 'Short', 'Integer', 'Long', 'Float', 'Double'];
		break;
	case '4':
		baseType = "Date";
		accept = ['Date'];
		break;
	case '8':
		baseType = "Currency";
		accept = ['short', 'int', 'long', 'float', 'double','Short', 'Integer', 'Long', 'Float', 'Double'];
		break;
	case '9':
		baseType = "Time";
		accept = ['Time'];
		break;
	case '12':
		baseType = "Binary";
		accept = ['byte'];
		break;
	case '14':
		baseType = "Datetime";
		accept = ['Datetime'];
		break;
	case '1':case '10':case '15':case '16':case '18':case '21':
		baseType = "Text";
		accept = ['String'];
		break;
	default:
		baseType = "Text";
		accept = ['String'];
		break;
	}
	return {baseType: baseType, accept: accept};
}
function getDataByScreenItemCode(screenItemCode) {
	if (screenItemCode.split('.').length == 0) return null;
	var area = screenItemCode.split('.')[1];
	var itemCode = screenItemCode.split('.')[2];
	var data = {};
	var $forms = $('.form-area-content');
	
	$forms.each(function() {
		var $form = $(this);
		$form.find('.areaContent').each(function(){
			var $area = $(this);
			var code = $area.find('input[name=formAreaCode]').val();
			var name = $area.find('input[name=formAreaCaptionText]').val();
			
			var areaCode = code;
			if (code.length == 0) {
				areaCode = name;
			}
			
			if (areaCode == area) {
				$area.find('input[name=formElement]').each(function() {
					var curData = convertToJson($(this).val());
					if (curData.columnname == itemCode) {
						data = curData;
						return false;
					}
				});
				return false;
			}
		});
	});
	return data;
}