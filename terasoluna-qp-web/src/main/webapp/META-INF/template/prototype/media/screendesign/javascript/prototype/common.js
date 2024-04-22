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
		+ "\",\"isSubmit\":\""+obj.isSubmit
		;
	
	
		if(obj.msgvalue != undefined){
			element += "\",\"msglabel\":\""+msglabel
			+ "\",\"msgvalue\":\""+obj.msgvalue;
		} else {
			element += "\",\"msglabel\":\"\",\"msgvalue\":\"";
		}
		
	element += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
	
	$("#srcgenHeaderLinkArea").append("<span style=\"padding: 0 5px 0 5px;\"><a href=\"javascript:\" class=\"com-link\" ondblclick=\"openDialogLinkSetting(this)\">"+obj.labelText+"</a>" + element + "</span>");
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
		elementTH = returnElementTH(div, true);
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
				$(elementNew).css("width", "100%");
				$(elementNew).css("float", "left");
				$(elementNew).css("text-align", "left");
				$(obj).replaceWith(elementNew);
				
			} else {
				$(td).replaceWith(tdNew);
			}
		} else {
			
			//remove dropComponent
			$(obj).closest("td").find(".dropComponent").remove();
			
			var element =  returnElement(div);			
			$(obj).append(element);
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
	

	
	$.qp.initialAllPicker($(table));
	
	$.qp.initialAutoNumeric($(td));
		
	initBlurEventElement();
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
		accept: "#srcgenElements div[id!='divLabel'], #srcgenAction div, #newDragElementTd div",
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
		accept: "#srcgenElements div[id!='divLabel'], #srcgenAction div, #newDragElementTd div",
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
		accept: "#srcgenElements div[id!='divLabel'], #srcgenAction div, #newDragElementTd div",
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
		+ "\",\"isBlank\":\""+"true"
		+ "\",\"labelText\":\""+dbMsgSource['sc.screendesign.0174']
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
		+ dbMsgSource['sc.screendesign.0174'] + "</label>";
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
		"<a href=\"javascript:\" style=\"float: right; margin-top: 3px;\" onclick=\"deleteTable(this)\" class=\"ui-state-default glyphicon glyphicon-remove\" title=\""+dbMsgSource['sc.screendesign.0136']+"\"></a>" +
		"<input type=\"hidden\" name=\"formAreaCaptionText\"/>" +
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
			"<input type=\"hidden\" value=\"2\" name=\"formAreaType\"/>";	
}

function returnTableSetting(code){
	return "<div class=\"areaContent\"><div class=\"panel panel-default\"><div class=\"panel-heading\"><span class=\"glyphicon\" aria-hidden=\"true\"></span><span class=\"qp-heading-text\">" +
				"<a href=\"javascript:\" style=\"float:left; margin-top: 3px; cursor: move; margin-right: 5px;\" class=\"srcgenTableSort ui-state-default qp-glyphicon-sort glyphicon glyphicon-sort\" title=\""+fcomMsgSource['sc.sys.0015']+"\"></a>" + 
				"<a href=\"javascript:\" style=\"float: left; margin-top: 3px; margin-right: 5px;\" onclick=\"openTableSetting(this)\" class=\"ui-state-default qp-glyphicon-cog glyphicon glyphicon-cog\" title=\""+dbMsgSource['sc.screendesign.0117']+"\"></a>" + 
				 "<span>" + dbMsgSource['sc.screendesign.0058'] + "</span>" + 
				"<a href=\"javascript:\" style=\"float: right; margin-top: 3px;\" onclick=\"deleteTable(this)\" class=\"ui-state-default glyphicon glyphicon-remove\" title=\""+dbMsgSource['sc.screendesign.0136']+"\"></a>" +
				"<input type=\"hidden\" name=\"formAreaCaptionText\" value=\"" + dbMsgSource['sc.screendesign.0058'] + "\"/>" +
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
				"<span>" + dbMsgSource['sc.screendesign.0059'] + "</span>" + 
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
				"<input type=\"hidden\" value=\""+areaPatternType+"\" name=\"formAreaPatternType\"/>" +
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
			elementTD += "<option></option><option>"+dbMsgSource['sc.screendesign.0137']+"1</option><option>"+dbMsgSource['sc.screendesign.0137']+"2</option>";
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
		
		elementTD += "<span><input type=\"button\" class=\"btn qp-button\" ondblclick=\"openDialogButtonAreaSetting(this)\" value=\""+$(div).attr('labelText')+"\"/>"+elementButton+"</span>";
	}
	if(dataType == 20){
		elementTD += "<label style=\"cursor: pointer;\">"+$(div).attr('label')+"</label>";
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
	var element =  "<span><label style=\"cursor: pointer;"+floatLeft+"\">" + item.labelText + "</label>";
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
		+ "\",\"columnname\":\""+"label"
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
		
		element += "<label ondblclick=\"openDialogLableSetting(this)\" style=\"cursor: pointer;"+floatLeft+"\">"+ htmlDecode(item.labelText);
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
					+ item.maxlength + "\" >"+item.columnname+"</label>"+element+"</span>";
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
			elementTD += "<option></option><option>"+dbMsgSource['sc.screendesign.0137']+"1</option><option>"+dbMsgSource['sc.screendesign.0137']+"2</option>";
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
		elementTD += "<span><input type=\"button\" class=\"btn qp-button\" ondblclick=\"openDialogButtonAreaSetting(this)\" value=\""+item.labelText+"\"/>"+elementButton+"</span>";
	}
	if(dataType == 20){
		elementTD += "<label style=\"cursor: pointer;\">"+unescape(item.label)+"</label>";
	}	
	return elementTD;
}

function returnElementTDLoad(item, displayGroupType){	
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
	
	var labelText = "";
	if(item.labelText != undefined){
		labelText = item.labelText;
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
		+ "\",\"tabindex\":\""+item.tabindex
		+ "\",\"dialogOnSelectEvent\":\""+item.dialogOnSelectEvent
		+ "\",\"mandatory\":\""+item.mandatory
		+ "\",\"datasourcetype\":\""+item.datasourcetype
		+ "\",\"physicalmaxlength\":\""+physicalmaxlength
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
	
	if (item.groupitemtype == 1) {
		style = 'style="width: '+styleWidth+'; float: left; padding-right: 3px; padding-bottom: 3px;'+styleDisplayGroupType+'"';
	}
	

	
	if(dataType == 0){
		elementTD += "<span "+style+">" +
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
		elementTD += "<span "+style+"><input ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
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
		elementTD += "<span "+style+"><Label ondblclick=\"openDialogComponentSetting(this)\"";					
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" >"+item.columnname+"</label>"+element+"</span>";
	}
	if(dataType == 2){
		elementTD += "<span "+style+"><input  ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
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
		elementTD += "<span "+style+"><input  ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
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
		elementTD += "<span "+style+"><input ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
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
		elementTD += "<span "+style+"><div class=\"input-group date\">"
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
		
		elementTD += "<span "+style+"><div class=\"input-group date\">"
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
		
		elementTD += "<span "+style+"><div class=\"input-group date\">"
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
			elementTD += "<span "+style+" ondblclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\">";
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
			elementTD += "<span "+style+" ondblclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\"><input type=\"radio\" ";
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
			elementTD += "<span "+style+" ondblclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\">";
			$(msgValArr).each(function(j){
				elementTD += "<input  type=\"checkbox\" ";
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
			elementTD += "<span "+style+" ondblclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\">";
			if (item.physicaldatatype == undefined || item.physicaldatatype == '' || item.physicaldatatype != 8) {
				elementTD += "<input type=\"checkbox\"";
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
			elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>";
			elementTD += ""+element+"</span>";
		}
		
	}
	if(dataType == 7){
		elementTD += "<span "+style+"><select ondblclick=\"openDialogComponentListSetting(this)\" name=\""+item.columnname+"\" ";
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
			elementTD += "<option></option><option>"+dbMsgSource['sc.screendesign.0137']+"1</option><option>"+dbMsgSource['sc.screendesign.0137']+"2</option>";
		}
		elementTD += "</select>"+element+"</span>";
	}
	if(dataType == 10){
		elementTD += "<span "+style+"><textarea ondblclick=\"openDialogComponentSetting(this)\" ";
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
		elementTD += "<span "+style+"><a ondblclick=\"openDialogLinkAreaSetting(this);\" href=\"javascript:\">"+item.labelText+"</a>"+elementButton+"</span>";
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
		elementTD += "<span "+style+"><input type=\"button\" class=\"btn qp-button\" ondblclick=\"openDialogButtonAreaSetting(this)\" value=\""+item.labelText+"\"/>"+elementButton+"</span>";
	}
	if(dataType == 20){
		elementTD += "<label style=\"cursor: pointer;\">"+item.label+"</label>";
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
	
	if (item.groupitemtype == 1 || displayGroupType == "1" || displayGroupType == 1) {
		style = 'style="width: '+styleWidth+'; float: left; padding-right: 3px; padding-bottom: 3px;'+styleDisplayGroupType+'"';
	}
	

	
	if(dataType == 0){
		elementTD += "<span "+style+">" +
				"<div class=\"input-group\" style=\"width:100%\"><input type=\"text\" ";
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
		elementTD += "<span "+style+"><input type=\"text\" ";
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
		elementTD += "<span "+style+"><Label ";					
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" >"+item.columnname+"</label>"+element+"</span>";
	}
	if(dataType == 2){
		elementTD += "<span "+style+"><input  type=\"text\" ";
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
		elementTD += "<span "+style+"><input type=\"text\" ";
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
		elementTD += "<span "+style+"><input type=\"text\" ";
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
		elementTD += "<span "+style+"><div class=\"input-group date qp-input-datepicker\">"
		elementTD += "<span><input type=\"text\" ";
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
		elementTD += "<span><input type=\"text\" ";
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
			elementTD += "<span><input type=\"text\" ";
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
					elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+msgValArr[j]+"</label>";
				} else {					
					elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+msgLabelArr[j]+"</label>";
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
			elementTD += "<span "+style+">";
			$(msgValArr).each(function(j){
				elementTD += "<input  type=\"checkbox\" ";
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
			elementTD += "<span "+style+" >";
			if (item.physicaldatatype == undefined || item.physicaldatatype == '' || item.physicaldatatype != 8) {
				elementTD += "<input type=\"checkbox\"";
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
			elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>";
			elementTD += ""+element+"</span>";
		}
		
	}
	if(dataType == 7){
		elementTD += "<span "+style+"><select name=\""+item.columnname+"\" ";
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
			elementTD += "<option></option><option>"+dbMsgSource['sc.screendesign.0137']+"1</option><option>"+dbMsgSource['sc.screendesign.0137']+"2</option>";
		}
		elementTD += "</select>"+element+"</span>";
	}
	if(dataType == 10){
		elementTD += "<span "+style+"><textarea ";
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
		elementTD += "<span "+style+"><a href=\"javascript:\">"+item.labelText+"</a>"+elementButton+"</span>";
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
		elementTD += "<span "+style+"><input type=\"button\" class=\"btn qp-button\" value=\""+item.labelText+"\"/>"+elementButton+"</span>";
	}
	if(dataType == 20){
		elementTD += "<label style=\"cursor: pointer;\">"+item.label+"</label>";
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
					+ item.maxlength + "\" >"+item.columnname+"</label>"+element+"</span>";
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
			elementTD += "<option></option><option>"+dbMsgSource['sc.screendesign.0137']+"1</option><option>"+dbMsgSource['sc.screendesign.0137']+"2</option>";
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
		elementTD += "<span><input type=\"button\" class=\"btn qp-button\" ondblclick=\"openDialogButtonAreaSetting(this)\" value=\""+item.labelText+"\"/>"+elementButton+"</span>";
	}
	if(dataType == 20){
		elementTD += "<label style=\"cursor: pointer;\">"+item.label+"</label>";
	}	
	return elementTD;
}

function returnElemenActionSection(item, styleArea){	
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
	
	var labelText = "";
	if(item.labelText != undefined){
		labelText = item.labelText;
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
		+ "\",\"tabindex\":\""+item.tabindex
		+ "\",\"physicalmaxlength\":\""+physicalmaxlength
		+ "\",\"defaultvalue\":\""+item.defaultvalue;
	
	
		if(item.msgvalue != undefined){
			element += "\",\"msglabel\":\""+msglabel
			+ "\",\"msgvalue\":\""+item.msgvalue;
		} else {
			element += "\",\"msglabel\":\"\",\"msgvalue\":\"";
		}
		
		element += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		
		var styleWidth = "100%";
		
		if (item.width != null && item.width != "" && item.width.length > 0) {
			styleWidth = "width:" + item.width + item.widthunit + ";";
		}
		var style = "";
		if (styleArea != undefined && styleArea != null && styleArea.length > 0) {
			style = "style='"+styleArea+"'";
		} /*else {
			var style = "style='width: "+styleWidth+"'";
		}*/
		
	if(dataType == 0){
		elementTD += "<span "+style+" >" +
				"<div ondblclick=\"openDialogAutocompleteSettingSection(this)\" class=\"input-group\" style=\"width:100%\"><input type=\"text\" ";
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
		elementTD += "<span  "+style+" ><input ondblclick=\"openDialogComponentSettingSection(this)\" type=\"text\" ";
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
		elementTD += "<span  "+style+" ><Label ondblclick=\"openDialogComponentSettingSection(this)\"";					
					elementTD += " name=\"" 
					+ item.columnname + "1\" maxlength=\"" 
					+ item.maxlength + "\" >"+item.columnname+"</label>"+element+"</span>";
	}
	if(dataType == 2){
		elementTD += "<span  "+style+" ><input  ondblclick=\"openDialogComponentSettingSection(this)\" type=\"text\" ";
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
		elementTD += "<span  "+style+" ><input  ondblclick=\"openDialogComponentSettingSection(this)\" type=\"text\" ";
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
		elementTD += "<span  "+style+" ><input ondblclick=\"openDialogComponentSettingSection(this)\" type=\"text\" ";
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
		elementTD += "<span  "+style+" ><div class=\"input-group date\">"
		elementTD += "<span><input ondblclick=\"openDialogComponentSettingSection(this)\" type=\"text\" ";
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
		
		elementTD += "<span  "+style+" ><div class=\"input-group date\">"
		elementTD += "<span><input ondblclick=\"openDialogComponentSettingSection(this)\" type=\"text\" ";
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
		
		elementTD += "<span  "+style+" ><div class=\"input-group date\">"
			elementTD += "<span><input ondblclick=\"openDialogComponentSettingSection(this)\" type=\"text\" ";
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
			elementTD += "<span  ondblclick=\"this.checked = !this.checked;openDialogComponentListSettingSection(this)\">";
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
			elementTD += "<span ondblclick=\"this.checked = !this.checked;openDialogComponentListSettingSection(this)\"><input type=\"radio\" ";
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
			elementTD += "<span ondblclick=\"this.checked = !this.checked;openDialogComponentListSettingSection(this)\">";
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
				elementTD += "<span ondblclick=\"this.checked = !this.checked;openDialogComponentListSettingSection(this)\"><input type=\"checkbox\" ";
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
		elementTD += "<span  "+style+" ><select ondblclick=\"openDialogComponentListSettingSection(this)\" name=\""+item.columnname+"\" ";
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
			elementTD += "<option></option><option>"+dbMsgSource['sc.screendesign.0137']+"1</option><option>"+dbMsgSource['sc.screendesign.0137']+"2</option>";
		}
		elementTD += "</select>"+element+"</span>";
	}
	if(dataType == 10){
		elementTD += "<span  "+style+" ><textarea ondblclick=\"openDialogComponentSettingSection(this)\" ";
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
		elementTD += "<span  "+style+" ><a ondblclick=\"openDialogLinkAreaSettingSection(this);\" href=\"javascript:\">"+item.labelText+"</a>"+elementButton+"</span>";
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
		elementTD += "<span "+style+"><input type=\"button\" class=\"btn qp-button\" ondblclick=\"openDialogButtonAreaSettingSection(this)\" value=\""+item.labelText+"\"/>"+elementButton+"</span>";
	}
	if(dataType == 20){
		elementTD += "<span  "+style+" ><label ondblclick=\"openDialogLableSettingSection(this)\" style=\"cursor: pointer;\">"+item.labelText;
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
		elementTD += "<span style=\"width: 100%;\"><Label ondblclick=\"openDialogComponentSetting(this)\"";					
					elementTD += " name=\"" 
					+ $(div).attr('columnname') + "\" maxlength=\"" 
					+ $(div).attr('maxlength') + "\" >Text</label>"+element+"</span>";
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
		elementTD += "<span><select ondblclick=\"openDialogComponentListSetting(this)\" name=\""+$(div).attr('columnname')+"\" ";
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
			elementTD += "<option></option><option>"+dbMsgSource['sc.screendesign.0137']+"1</option><option>"+dbMsgSource['sc.screendesign.0137']+"2</option>";
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
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
		
		
			if($(div).attr('msgvalue') != undefined){
				elementButton += "\",\"msglabel\":\""+msglabel
				+ "\",\"msgvalue\":\""+$(div).attr('msgvalue');
			} else {
				elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
			}
			
			elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
		
		elementTD += "<span style=\"width: 100%;\"><input type=\"button\" class=\"btn qp-button\" ondblclick=\"openDialogButtonAreaSetting(this)\" value=\""+$(div).attr('labelText')+"\"/>"+elementButton+"</span>";
	}
	if(dataType == 20){
		elementTD += "<label style=\"cursor: pointer;\">"+$(div).attr('label')+"</label>";
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
					+ $(div).attr('maxlength') + "\" >Text</label>"+element+"</span>";
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
			elementTD += "<option></option><option>"+dbMsgSource['sc.screendesign.0137']+"1</option><option>"+dbMsgSource['sc.screendesign.0137']+"2</option>";
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
		
		elementTD += "<span><input type=\"button\" class=\"btn qp-button\" ondblclick=\"openDialogButtonAreaSetting(this)\" value=\""+$(div).attr('labelText')+"\"/>"+elementButton+"</span>";
	}
	if(dataType == 20){
		elementTD += "<label style=\"cursor: pointer;\">"+$(div).attr('label')+"</label>";
	}
	elementTD += "</td>";
	return elementTD;
}

function returnElementTDSearchEntity(item, displayGroupType){
	
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
		var labelText = "";
		if(item.labelText != undefined){
			labelText = item.labelText;
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
			+ "\",\"datasourcetype\":\""+item.datasourcetype
			+ "\",\"mandatory\":\""+item.mandatory
			+ "\",\"width\":\""+item.width
			+ "\",\"widthunit\":\""+item.widthunit
			+ "\",\"tabindex\":\""+item.tabindex
			+ "\",\"physicalmaxlength\":\""+physicalmaxlength
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
			
			if (item.groupitemtype == 1) {
				style = 'style="width: '+styleWidth+'; float: left; padding-right: 3px; padding-bottom: 3px;'+styleDisplayGroupType+'"';
			}
		
		if(dataType == 0){
			elementTD += "<span "+style+">" +
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
			elementTD += "<span "+style+"><input ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
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
			elementTD += "<span "+style+"><Label ondblclick=\"openDialogComponentSetting(this)\" ";					
						elementTD += " name=\"" 
						+ item.columnname + "1\" maxlength=\"" 
						+ item.maxlength + "\" >Text</label>"+element+"</span>";
		}
		if(dataType == 2){
			elementTD += "<span "+style+"><div  ondblclick=\"openDialogComponentSetting(this)\" style=\"width: 100%;\">" +
					"<input type=\"text\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"form-control qp-input-from pull-left\"";
						} else {
							elementTD += "class=\"com-input-integer-detail\"";
						}
						elementTD += " name=\"" 
						+ item.columnname + "1\" maxlength=\"" 
						+ item.maxlength + "\" />" +
						"" + "<div class=\"qp-separator-from-to\">~</div>" +
						"<input  ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"form-control qp-input-to pull-right\"";
						} else {
							elementTD += "class=\"com-input-integer-detail\"";
						}
						elementTD += " name=\"" 
						+ item.columnname + "1\" maxlength=\"" 
						+ item.maxlength + "\" />" +
							
					""+element+"</div></span>";
		}
		if(dataType == 3){
			elementTD += "<span "+style+"><div ondblclick=\"openDialogComponentSetting(this)\" style=\"width: 100%;\">" +
						"<input type=\"text\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"form-control qp-input-from pull-left\"";
						} else {
							elementTD += "class=\"com-input-integer-detail\"";
						}
						elementTD += " name=\"" 
						+ item.columnname + "1\" maxlength=\"" 
						+ item.maxlength + "\" />" +
						"" + "<div class=\"qp-separator-from-to\">~</div>" +
						"<input  ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"form-control qp-input-to pull-right\"";
						} else {
							elementTD += "class=\"com-input-integer-detail\"";
						}
						elementTD += " name=\"" 
						+ item.columnname + "1\" maxlength=\"" 
						+ item.maxlength + "\" />" +
							
					""+element+"</div></span>";
		}
		if(dataType == 8){
			elementTD += "<span "+style+"><div ondblclick=\"openDialogComponentSetting(this)\" style=\"width: 100%;\">" +
			"<input type=\"text\" ";
			if(item.elementtype == 0){
				elementTD += "class=\"form-control qp-input-from pull-left\"";
			} else {
				elementTD += "class=\"com-input-integer-detail\"";
			}
			elementTD += " name=\"" 
			+ item.columnname + "1\" maxlength=\"" 
			+ item.maxlength + "\" />" +
			"" + "<div class=\"qp-separator-from-to\">~</div>" +
			"<input  ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" ";
			if(item.elementtype == 0){
				elementTD += "class=\"form-control qp-input-to pull-right\"";
			} else {
				elementTD += "class=\"com-input-integer-detail\"";
			}
			elementTD += " name=\"" 
			+ item.columnname + "1\" maxlength=\"" 
			+ item.maxlength + "\" />" +
				
		""+element+"</div></span>";
		}
		if(dataType == 4){
			elementTD += "<span "+style+"><div ondblclick=\"openDialogComponentSetting(this)\">" +
			"<div class=\"input-group date qp-input-from pull-left\">";
			elementTD += "<input  type=\"text\" class=\"form-control qp-input-from\"";
			elementTD += " name=\"" 
							+ item.columnname + "1\" maxlength=\"" 
							+ item.maxlength + "\" />";
			elementTD += "<span class=\"input-group-addon\">"
							+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
							+ "</span>"
						+ "</div>"
			+ "<div class=\"qp-separator-from-to\">~</div>" +
			"<div class=\"input-group date qp-input-to pull-right\">";
			elementTD += "<input  type=\"text\" ";
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
		if(dataType == 14){
			
			elementTD += "<span "+style+"><div ondblclick=\"openDialogComponentSetting(this)\">" +
					"<div class=\"input-group date qp-input-from pull-left\">";
			elementTD += "<input  type=\"text\" ";
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
			elementTD += "<input  type=\"text\" ";
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
		if(dataType == 9){
			
			elementTD += "<span "+style+"><div ondblclick=\"openDialogComponentSetting(this)\">" +
			"<div class=\"input-group date qp-input-from pull-left\">";
			elementTD += "<input  type=\"text\" ";
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
			elementTD += "<input  type=\"text\" ";
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
		if(dataType == 5){
			if(item.msgvalue.length > 0){
				var msgLabelArr = item.msglabel.split("�");
				var msgValArr = item.msgvalue.split("�");
				elementTD += "<span "+style+" onclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\">";
				$(msgValArr).each(function(j){
					elementTD += "<input  type=\"radio\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
					} else {
						elementTD += "class=\"com-input-radio-detail\"";
					}
					if(item.msglabel == undefined || item.msglabel.length == 0  || (msgLabelArr[j] == null || msgLabelArr[j] == 'null')){					
						elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+msgValArr[j]+"</label>";
					} else {					
						elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+msgLabelArr[j]+"</label>";
					}
				});
				elementTD += ""+element+"</span>";
			} else {
				elementTD += "<span "+style+" onclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\"><input type=\"radio\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
					} else {
						elementTD += "class=\"com-input-radio-detail\"";
					}
				elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
				elementTD += "<input type=\"radio\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
					} else {
						elementTD += "class=\"com-input-radio-detail\"";
					}
				elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>"+element+"</span>";
			}
		}
		if(dataType == 6){
			if(item.msgvalue.length > 0){
				var msgLabelArr = item.msglabel.split("�");
				var msgValArr = item.msgvalue.split("�");
				elementTD += "<span "+style+" onclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\">";
				$(msgValArr).each(function(j){
					elementTD += "<input type=\"checkbox\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
					} else {
						elementTD += "class=\"com-input-checkbox-detail\"";
					}				
					if(item.msglabel == undefined || item.msglabel.length == 0  || (msgLabelArr[j] == null || msgLabelArr[j] == 'null')){					
						elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+msgValArr[j]+"</label>";
					} else {					
						elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+msgLabelArr[j]+"</label>";
					}				
				});
				elementTD += ""+element+"</span>";
			} else {
				elementTD += "<span "+style+" onclick=\"this.checked = !this.checked;openDialogComponentListSetting(this)\"><input type=\"checkbox\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
					} else {
						elementTD += "class=\"com-input-checkbox-detail\"";
					}
				elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
				elementTD += "<input type=\"checkbox\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
					} else {
						elementTD += "class=\"com-input-checkbox-detail\"";
					}
				elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>"+element+"</span>";
			}
		}
		if(dataType == 7){
			elementTD += "<span "+style+"><select ondblclick=\"openDialogComponentListSetting(this)\" name=\""+item.columnname+"\" ";
			if(item.elementtype == 0){
				elementTD += "class=\"form-control qp-input-select\"";
			} else {
				elementTD += "class=\"form-control qp-input-select-detail\"";
			}
			elementTD += ">";
			if(item.msgvalue.length > 0){
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
				elementTD += "<option></option><option>"+dbMsgSource['sc.screendesign.0137']+"1</option><option>"+dbMsgSource['sc.screendesign.0137']+"2</option>";
			}
			elementTD += "</select>"+element+"</span>";
		}
		if(dataType == 10){
			elementTD += "<span "+style+"><textarea ondblclick=\"openDialogComponentSetting(this)\" ";
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
				+ "\",\"isSubmit\":\""+item.isSubmit
				;
			
				if(item.msgvalue != undefined){
					elementButton += "\",\"msglabel\":\""+msglabel
					+ "\",\"msgvalue\":\""+item.msgvalue;
				} else {
					elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
				}
				
				elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
			elementTD += "<span "+style+"><a ondblclick=\"openDialogLinkAreaSetting(this);\" href=\"javascript:\">"+item.labelText+"</a>"+elementButton+"</span>";
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
			
			elementTD += "<span "+style+"><input type=\"button\" class=\"btn qp-button\" ondblclick=\"openDialogButtonAreaSetting(this)\" value=\""+item.labelText+"\"/>"+elementButton+"</span>";
		}
		if(dataType == 20){
			elementTD += "<label style=\"cursor: pointer;\">"+item.label+"</label>";
		}
		
		return elementTD;
	}
function returnElementTDSearchEntityPreview(item, displayGroupType){
	
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
					styleWidth = item.width +item.widthunit;
				}
			var style = 'style="width: '+styleWidth+'; float: left;'+styleDisplayGroupType+'"';
			
			if (item.groupitemtype == 1) {
				style = 'style="width: '+styleWidth+'; float: left; padding-right: 3px; padding-bottom: 3px;'+styleDisplayGroupType+'"';
			}
		
		if(dataType == 0){
			elementTD += "<span "+style+">" +
					"<div class=\"input-group\" style=\"width:100%\"><input type=\"text\" ";
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
			elementTD += "<span "+style+"><input type=\"text\" ";
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
			elementTD += "<span "+style+"><Label ";					
						elementTD += " name=\"" 
						+ item.columnname + "1\" maxlength=\"" 
						+ item.maxlength + "\" >Text</label>"+element+"</span>";
		}
		if(dataType == 2){
			elementTD += "<span "+style+"><div  style=\"width: 100%;\">" +
					"<input type=\"text\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"form-control qp-input-from pull-left\"";
						} else {
							elementTD += "class=\"com-input-integer-detail\"";
						}
						elementTD += " name=\"" 
						+ item.columnname + "1\" maxlength=\"" 
						+ item.maxlength + "\" />" +
						"" + "<div class=\"qp-separator-from-to\">~</div>" +
						"<input  type=\"text\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"form-control qp-input-to pull-right\"";
						} else {
							elementTD += "class=\"com-input-integer-detail\"";
						}
						elementTD += " name=\"" 
						+ item.columnname + "1\" maxlength=\"" 
						+ item.maxlength + "\" />" +
							
					""+element+"</div></span>";
		}
		if(dataType == 3){
			elementTD += "<span "+style+"><div style=\"width: 100%;\">" +
						"<input type=\"text\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"form-control qp-input-from pull-left\"";
						} else {
							elementTD += "class=\"com-input-integer-detail\"";
						}
						elementTD += " name=\"" 
						+ item.columnname + "1\" maxlength=\"" 
						+ item.maxlength + "\" />" +
						"" + "<div class=\"qp-separator-from-to\">~</div>" +
						"<input  type=\"text\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"form-control qp-input-to pull-right\"";
						} else {
							elementTD += "class=\"com-input-integer-detail\"";
						}
						elementTD += " name=\"" 
						+ item.columnname + "1\" maxlength=\"" 
						+ item.maxlength + "\" />" +
							
					""+element+"</div></span>";
		}
		if(dataType == 8){
			elementTD += "<span "+style+"><input type=\"text\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"form-control\"";
						} else {
							elementTD += "class=\"com-input-currency-detail\"";
						}
						elementTD += " name=\"" 
						+ item.columnname + "1\" maxlength=\"" 
						+ item.maxlength + "\" />"+element+"</span>";
		}
		if(dataType == 4){
			elementTD += "<span "+style+"><div>" +
			"<div class=\"input-group date qp-input-from-datepicker pull-left\">";
			elementTD += "<input  type=\"text\" ";
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
			"<div class=\"input-group date qp-input-to-datepicker pull-right\">";
			elementTD += "<input  type=\"text\" ";
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
		if(dataType == 14){
			
			elementTD += "<span "+style+"><div>" +
					"<div class=\"input-group date qp-input-from-datetimepicker-detail pull-left\">";
			elementTD += "<input  type=\"text\" ";
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
			"<div class=\"input-group date qp-input-to-datetimepicker-detail pull-right\">";
			elementTD += "<input  type=\"text\" ";
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
		if(dataType == 9){
			
			elementTD += "<span "+style+"><div>" +
			"<div class=\"input-group date qp-input-from-timepicker pull-left\">";
			elementTD += "<input  type=\"text\" ";
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
			"<div class=\"input-group date qp-input-to-timepicker pull-right\">";
			elementTD += "<input  type=\"text\" ";
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
		if(dataType == 5){
			if(item.msgvalue.length > 0){
				var msgLabelArr = item.msglabel.split("�");
				var msgValArr = item.msgvalue.split("�");
				elementTD += "<span "+style+">";
				$(msgValArr).each(function(j){
					elementTD += "<input  type=\"radio\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
					} else {
						elementTD += "class=\"com-input-radio-detail\"";
					}
					if(item.msglabel == undefined || item.msglabel.length == 0  || (msgLabelArr[j] == null || msgLabelArr[j] == 'null')){					
						elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+msgValArr[j]+"</label>";
					} else {					
						elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+msgLabelArr[j]+"</label>";
					}
				});
				elementTD += ""+element+"</span>";
			} else {
				elementTD += "<span "+style+" ><input type=\"radio\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
					} else {
						elementTD += "class=\"com-input-radio-detail\"";
					}
				elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
				elementTD += "<input type=\"radio\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-radio qp-input-radio-margin\"";
					} else {
						elementTD += "class=\"com-input-radio-detail\"";
					}
				elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>"+element+"</span>";
			}
		}
		if(dataType == 6){
			if(item.msgvalue.length > 0){
				var msgLabelArr = item.msglabel.split("�");
				var msgValArr = item.msgvalue.split("�");
				elementTD += "<span "+style+" >";
				$(msgValArr).each(function(j){
					elementTD += "<input type=\"checkbox\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
					} else {
						elementTD += "class=\"com-input-checkbox-detail\"";
					}				
					if(item.msglabel == undefined || item.msglabel.length == 0  || (msgLabelArr[j] == null || msgLabelArr[j] == 'null')){					
						elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+msgValArr[j]+"</label>";
					} else {					
						elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+msgLabelArr[j]+"</label>";
					}				
				});
				elementTD += ""+element+"</span>";
			} else {
				elementTD += "<span "+style+" ><input type=\"checkbox\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
					} else {
						elementTD += "class=\"com-input-checkbox-detail\"";
					}
				elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"1</label>";
				elementTD += "<input type=\"checkbox\" ";
					if(item.elementtype == 0){
						elementTD += "class=\"qp-input-checkbox-margin qp-input-checkbox\"";
					} else {
						elementTD += "class=\"com-input-checkbox-detail\"";
					}
				elementTD += " name=\"" + item.columnname + "1\" /><label for=\""+item.columnname+"\">"+dbMsgSource['sc.screendesign.0137']+"2</label>"+element+"</span>";
			}
		}
		if(dataType == 7){
			elementTD += "<span "+style+"><select  name=\""+item.columnname+"\" ";
			if(item.elementtype == 0){
				elementTD += "class=\"form-control qp-input-select\"";
			} else {
				elementTD += "class=\"form-control qp-input-select-detail\"";
			}
			elementTD += ">";
			if(item.msgvalue.length > 0){
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
				elementTD += "<option></option><option>"+dbMsgSource['sc.screendesign.0137']+"1</option><option>"+dbMsgSource['sc.screendesign.0137']+"2</option>";
			}
			elementTD += "</select>"+element+"</span>";
		}
		if(dataType == 10){
			elementTD += "<span "+style+"><textarea ";
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
				+ "\",\"isSubmit\":\""+item.isSubmit
				;
			
				if(item.msgvalue != undefined){
					elementButton += "\",\"msglabel\":\""+msglabel
					+ "\",\"msgvalue\":\""+item.msgvalue;
				} else {
					elementButton += "\",\"msglabel\":\"\",\"msgvalue\":\"";
				}
				
				elementButton += "\",\"require\":\""+require+"\",\"elementtype\":\"0\"'>";
			elementTD += "<span "+style+"><a href=\"javascript:\">"+item.labelText+"</a>"+elementButton+"</span>";
		}
		if(dataType == 12){
			elementTD += "<span "+style+"><input type=\"file\" name=\"" + item.columnname + "1\" ";
						if(item.elementtype == 0){
							elementTD += "class=\"qp-input-file\"";
						} else {
							elementTD += "class=\"com-input-file-detail\"";
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
			
			elementTD += "<span "+style+"><input type=\"button\" class=\"btn qp-button\"  value=\""+item.labelText+"\"/>"+elementButton+"</span>";
		}
		if(dataType == 20){
			elementTD += "<label style=\"cursor: pointer;\">"+item.label+"</label>";
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
			elementTD += "<option></option><option>"+dbMsgSource['sc.screendesign.0137']+"1</option><option>"+dbMsgSource['sc.screendesign.0137']+"2</option>";
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
	
	var element =  "" 
		+ "<input type=\"hidden\" name=\"formElement\" "
		+ "value='\"label\":\""+$(div).attr('label')
		+ "\",\"datatype\":\""+$(div).attr('datatype')
		+ "\",\"physicaldatatype\":\""+physicaldatatype;
	
	if (dataType == 20) {
		element += "\",\"columnname\":\""+"";
		element += "\",\"labelText\":\""+dbMsgSource['sc.screendesign.0050'];
	} else if (dataType == 11) {
		element += "\",\"columnname\":\""+"";
		element += "\",\"labelText\":\""+dbMsgSource['sc.screendesign.0039'];
	} else if (dataType == 13) {
		element += "\",\"columnname\":\""+"";
		element += "\",\"labelText\":\""+dbMsgSource['sc.screendesign.0128'];
	} else {
		element += "\",\"columnname\":\""+$(div).attr('columnname');
	}
	
	element +=  ""
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
		+ "\",\"width\":\""+"25"
		+ "\",\"widthunit\":\""+"%"
		+ "\",\"maxlength\":\""+maxlength
		+ "\",\"physicalmaxlength\":\""+physicalmaxlength;
	if(dataType == 20){
		element += "\",\"isBundle\":\""+"false"
		+ "\",\"isBlank\":\""+"true"
		+ "\",\"labelText\":\""+dbMsgSource['sc.screendesign.0174'] ;
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
			elementTD += "<option></option><option>"+dbMsgSource['sc.screendesign.0137']+"1</option><option>"+dbMsgSource['sc.screendesign.0137']+"2</option>";
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
			+ "\",\"width\":\""+"25"
			+ "\",\"widthunit\":\""+"%"
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
		elementTD += "<input type=\"button\" class=\"btn qp-button\" ondblclick=\"openDialogButtonAreaSettingSection(this)\" value=\""+$(div).attr('labelText')+"\"/>"+elementButton;
	}
	if(dataType == 20){
		elementTD += "<label ondblclick=\"openDialogLableSettingSection(this)\" style=\"cursor: pointer;\">"+dbMsgSource['sc.screendesign.0174']+"</label>"+element;
	}	
	
	var style = "width: 25%; padding: 2px; ";
	var direction = $(obj).closest(".areaContent").find("input[name=formDirection]").val();
	var displayType = $(obj).closest(".areaContent").find("input[name=formDisplayType]").val();
	
	if (displayType == 1) {
		style += "clear: both;";
	} else {
		
	}
	
	if (direction == 0) {
		style += "float: left;";
	} else {		
		style += "float: right;";
	}
	
	if (dataType != 10) {
		style += "height: 30px;";
	}
	
	if (dataType == 5 || dataType == 6) {
		$(obj).append("<span ondblclick=\"this.checked = !this.checked;openDialogComponentListSettingSection(this)\" style=\""+style+"\">" + elementTD + element + "</span>");
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
		+ "\",\"labelText\":\""+dbMsgSource['sc.screendesign.0174'] ;
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
			elementTD += "<option></option><option>"+dbMsgSource['sc.screendesign.0137']+"1</option><option>"+dbMsgSource['sc.screendesign.0137']+"2</option>";
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
		elementTD += "<input type=\"button\" class=\"btn qp-button\" ondblclick=\"openDialogButtonAreaSettingSection(this)\" value=\""+$(div).attr('labelText')+"\"/>"+elementButton;
	}
	if(dataType == 20){
		elementTD += "<label ondblclick=\"openDialogLableSettingSection(this)\" style=\"cursor: pointer;\">"+dbMsgSource['sc.screendesign.0174']+"</label>"+element;
	}

	var style = "height: 30px;padding:2px;";
	
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
		$(obj).before(returnTableSetting(getAreaCode(obj, 'singleEntity')) + "<table class=\"table table-bordered qp-table-form\">" +
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
		if(countSearchCondition == 0){
			$(obj).before(returnTableSetting(getAreaCode(obj, 'singleEntity')) + "<table type=\"search\" class=\"table table-bordered qp-table-form\">" +
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
		
		$(obj).closest(".form-area-content").find("div[class='areaContent']:last").find("input[name=formElementTable]").val("4,4");
//		$("#srcgenScreen").find("div[class='areaContent']:last").find("input[name=formTableColumnSize]").val("25%,25%,25%,25%");
	}
	
	if($(div).attr("elementtype") == 0 || $(div).attr("elementtype") == 1){
		$(obj).closest(".form-area-content").find("table:last").find("tbody td").droppable({
			accept: "#srcgenElements div[id!='divLabel'], #srcgenAction div, #newDragElementTd div",
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
			accept: "#srcgenControlDiv tr[class!='srcgenElementsTable'] div",
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
			accept: "#divHtmlButton, #divLabel, #divHtmlLink",
			activeClass: "state-droppable",
			drop: function(event, ui) {
				insertActionElement(event, ui, $(this));				
			}
		});		
		
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
					accept: "#srcgenElements div[id!='divLabel'], #srcgenAction div, #newDragElementTd div",
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
			var width = '';
			ui.children().each(function() {
				$(this).width($(this).width());
				width = $(this).width();
			});
			ui.width(width);
			return ui;
		},
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
		
		if($("[name=radioEntityType]:checked").val() == "0"){
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
				var content = returnTableSetting() + "<table "+searchTypeAttr+" class=\"table table-bordered qp-table-form\">" +
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
		accept: "#srcgenElements div[id!='divLabel'], #srcgenAction div, #newDragElementTd div",
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
		 alert(dbMsgSource["sc.screendesign.0168"] + " " + TABLE_SETTING.rowLimit);
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
		accept: "#srcgenElements div[id!='divLabel'], #srcgenAction div, #newDragElementTd div",
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
		accept: "#srcgenElements div[id!='divLabel'], #srcgenAction div, #newDragElementTd div",
		activeClass: "state-droppable",
		drop: function(event, ui) {					
			insertComponent(event, ui, $(this));			
		}
	});
	$(table).find("tbody td").droppable( "option", "disabled", false );
	$(table).find("tbody td").find("input[name='enableGroup']").val(true);
	$(table).find("tbody td").find("input[name='groupDisplayType']").val(1);	
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
	var trIndex = parseInt($(tr).attr("index"));
	var columnLength = $(tr).children().length - 2; 
	
	if ($(table).attr("class") == "table table-bordered qp-table-list-none-action") {
		columnLength = $(tr).children().length; 
	}
	
	if(data.elementtype == 1 && data.rowspan != rowspan){
		if(rowspan > 1){
			// validate
			var trLength = $(table).find("tr").length;
			if(rowspan > (trLength - trIndex)){
				alert(dbMsgSource['sc.screendesign.0125'].replace("{0}", (trLength - trIndex)));
				return false;
			}
		}
	}
	
	var rowIndex =trIndex + 1;
	var colIndex = elementIndex;
	if(rowspan > 1){
		var trLength = $(table).find("tr").length;
		if(rowspan > trLength - rowIndex){
			alert(dbMsgSource['sc.screendesign.0125'].replace("{0}", (trLength - rowIndex)));
			return false;
		}
	}
	//validate max colspan
	if(colspan > 1){
		if(colspan > columnLength - elementIndex){
			alert(dbMsgSource['sc.screendesign.0126'].replace("{0}", (columnLength - elementIndex)));
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
			accept: "#srcgenElements div[id!='divLabel'], #srcgenAction div, #newDragElementTd div",
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
		 alert(dbMsgSource['sc.screendesign.0171'] + " " + TABLE_SETTING.columnLimit);
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
		accept: "#srcgenElements div[id!='divLabel'], #srcgenAction div, #newDragElementTd div",
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
		accept: "#srcgenElements div[id!='divLabel'], #srcgenAction div, #newDragElementTd div",
		activeClass: "state-droppable",
		drop: function(event, ui) {					
			insertComponent(event, ui, $(this));			
		}
	});
	$(table).find("tbody td").droppable( "option", "disabled", false );
	$(table).find("tbody td").find("input[name='enableGroup']").val(true);
	$(table).find("tbody td").find("input[name='groupDisplayType']").val(1);
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
		 alert(dbMsgSource['sc.screendesign.0171'] +  " " + TABLE_SETTING.columnLimit);
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

	$(table).find("div[class='dropComponent']").droppable({
		accept: "#srcgenElements div[id!='divLabel'], #srcgenAction div, #newDragElementTd div",
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
		accept: "#srcgenElements div[id!='divLabel'], #srcgenAction div, #newDragElementTd div",
		activeClass: "state-droppable",
		drop: function(event, ui) {					
			insertComponent(event, ui, $(this));			
		}
	});
	$(table).find("tbody td").droppable( "option", "disabled", false );
	$(table).find("tbody td").find("input[name='enableGroup']").val(true);
	$(table).find("tbody td").find("input[name='groupDisplayType']").val(1);
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
		alert("{message} Can not remove area in tag");
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
		baseType += "</select>";
		break;
	case '2':
		baseType = "Integer";
		break;
	case '3':
		baseType = "Decimal";
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
	
	merge($(element), mergeNew, mergeOld);
	
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
			$(element).closest("td").find("input[name=groupDisplayType]").val("");
			$(element).closest("td").find("input[name=enableGroup]").val("");
			$(element).closest("td").find("input[name=groupTotalElement]").val("");
		}
	} else if ($(element).prop("tagName") == "TH"){
		$(element).html(returnHiddenTHEmpty() + "<span style=\"float: left; cursor: move;\" class=\"qp-glyphicon glyphicon glyphicon-screenshot\" title=\"Move\"></span><div class=\"dropLabel\">&nbsp;</div>");
	}
	
	//init drap drop
	$(table).find("div[class='dropComponent']").droppable({
		accept: "#srcgenElements div[id!='divLabel'], #srcgenAction div, #newDragElementTd div",
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
	$(thiz).parents("table").find("tr[class=codelist-screen]").hide();
	$(thiz).parents("table").find("tr[class=codelist-table]").hide();
	$(thiz).parents("table").find("tr[class=codelist-system]").hide();
	if(value == 1){
		$(thiz).parents("table").find("tr[class=codelist-system]").show();
	}else if(value == 3){
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
				$scope.append("<input type='radio' class='qp-input-radio qp-input-radio-margin' value='"+values[i]+"' ></input><label>"+labels[i]+"</label>");
			}
			
			break;
		case "6":
			$scope.find("input[type=checkbox]").each(function(){
				var thiz= this;
				$(thiz).next("label").remove();
				$(thiz).remove();
			});
			for(var i=0;i<labels.length;i++){
				$scope.append("<input type='checkbox' class='qp-input-checkbox qp-input-checkbox-margin' value='"+values[i]+"' ></input><label>"+labels[i]+"</label>");
			}
			
			break;
		case "7":
			$scope = $scope.closest("span");
			var $select = $scope.find("select");
			$select.find("option").remove();
			var html="";
			for(var i=0;i<labels.length;i++){
				html +="<option value='"+values[i]+"'>"+labels[i]+"</option>";
			}
			$select.append(html);
			break;
		} 
		$scope.append($input);
		initBlurEventElement();
	}  else {
		var $input = $scope.find("input[type=hidden]");
		switch(data.datatype){
		case "5":
			$input.before("<input type='radio' class='qp-input-radio qp-input-radio-margin' ></input>"+dbMsgSource['sc.screendesign.0317']+"</label>");
			$input.before("<input type='radio' class='qp-input-radio qp-input-radio-margin'  ></input><label>"+dbMsgSource['sc.screendesign.0318']+"</label>");
			break;
		case "6":
			$input.before("<input type='checkbox' class='qp-input-checkbox qp-input-checkbox-margin'></input><label>"+dbMsgSource['sc.screendesign.0317']+"</label>");
			$input.before("<input type='checkbox' class='qp-input-checkbox qp-input-checkbox-margin'></input><label>"+dbMsgSource['sc.screendesign.0318']+"</label>");
			break;
		}
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
					"</span>";
	$(obj).before(setting + "<div class=\"com-sd-cover ui-sortable form-area-content\">" + formHidden +
			"<div id=\"srcgenAreaTemplate\" style=\"float: left; line-height: 24px; height: 30px; width: 100%; margin: 2px;\" class=\"ui-droppable drap-drop-area\">&nbsp;</div>" +
			"</div>");
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
	
	return data;
}

function openProperties(modal, data) {
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
		
	if (data.labelText != undefined && data.labelText != "undefined"){
		$(modal).find("input[name=labelNameAutocomplete]").val(htmlDecode(data.labelText));
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
		
		if (data.defaultvalue != undefined) {
			data.defaultvalue = $(obj).autoNumeric('get');
		} else {
			data['defaultvalue'] = $(obj).autoNumeric('get');
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
			break;
		case 3:
			$(this).prev().addClass("qp-input-float");
			$.qp.formatFloat($(this).prev());
			break;
		case 2:
			$(this).prev().addClass("qp-input-integer");
			$.qp.formatInteger($(this).prev());
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
	if(localCodelist == undefined || localCodelist.length == 0){
		localCodelist = 1;
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
		
		initOptionAutocomplete($(modal).find("#sql-setting"), data.sqldesignid);
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
								+ "		<td><input type=\"text\" class=\"form-control qp-input-text\" name=\"parameterOptionValue\" value=\""+dataParameter[1]+"\"/></td>"
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
		$(modal).find('input:radio[name="localCodelist"]').prop('checked', false);
		$(modal).find('input:radio[name="localCodelist"]').filter('[value="'+localCodelist+'"]').prop('checked', true);
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
		
		var style=""
		
		for (var k = 0; k < areas.length; k++) {
			
			if (areaName == areas[k]) {
				style = "style='display: none;'";
				break;
			}
		}
		

		if ($(this).closest(".area-tab") != undefined && $(this).closest(".area-tab").length > 0) {
			style = "style='display: none;'";
		}
		
		if (tab != undefined && tab != null && tab["tabCode"] != undefined && tab["tabCode"] != null && $(this).closest(".area-tab").attr("id") == getFormCode(currentForm).replace(/ /g,'') + "-" + tab["tabCode"]) {
			
			if (arrOther != undefined && arrOther != null && arrOther.length > 0) {
				var checkedOther = false;
				for (var areaIndex = 0; areaIndex < arrOther.length; areaIndex++) {
					if (areaName == arrOther[areaIndex]['areas']) {
						checkedOther = true;
					}
				}
				if (!checkedOther) {
					style = "";
				}
				
			} else {
				style = "";
			} 
			
		}
		
		var checked = "";
		var isChecked = false;
		
		for (var k = 0; k < initAreas.length; k++) {
			if (initAreas[k] == areaName) {
				isChecked = true;
				break;
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
					  '</ul></div><div class="contain-tab-content" style="float: left; width: 79%;margin: 0px; padding: 0px;"><div id="'+formCode + '-tab-content" style="border: 1px solid #ddd;" class="tab-content"></div>';
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