/**
 *  Draw logic design tab
 * 
 * @param arrData
 */
function displayLogicDesign(arrData) {
	
	var dataConds = arrData[0];
	var dataActs = arrData[1];
	var dataCondGroup = arrData[2];
	var dataCondItem = arrData[3];
	
	var $htmlTable = $('<div>').addClass("panel panel-default");
	var $table = $('<table>').addClass('table table-bordered qp-table-list').attr('id',TABLE_NAME.LOGIC);
	var $divAddRow = $("<div>").addClass('qp-div-action-table');
	var $divContent = $('<div>').addClass('panel-body');
	var $href = $('<a>').addClass('btn btn-default btn-xs glyphicon glyphicon-chevron-down qp-button-action');
	$href.attr('title','Add new row');
	$href.attr('onclick','addRowJS(this);');
	$href.attr('href','javascript:void(0);');
	
	if(dataConds.length > 0 && dataActs.length > 0) {
		
		var htmlColgroup = buildColgroup(dataConds.length, dataActs.length);
		var htmlThead = buildThead(dataConds, dataActs);
		var htmlBody  = buildTbody(dataConds, dataActs, dataCondGroup, dataCondItem);
		
		$table.append("<colgroup>"+htmlColgroup+"</colgroup>");
		$table.append("<thead>"+htmlThead+"</thead>");
		$table.append(htmlBody);
		
		$divAddRow.append($href);
		$divContent.append($('<div>').append($table).append($divAddRow));
		$htmlTable.append($divContent);
		$.qp.initialAutoNumeric($htmlTable);
		$.qp.initialAllPicker($htmlTable);
		renameOfRadio('#'+TABLE_NAME.LOGIC);

		$('div'+TAB_NAME.LOGICDESIGN).append($htmlTable);
	} else {
		$('div'+TAB_NAME.LOGICDESIGN).children().remove();
	}
}

/**
 * 
 * @param dataConds
 * @param dataActs
 * @returns {String}
 */
function buildTemplateJs(dataConds, dataActs, trIndex) {
	
	var allTdOneRow = "";
	var tr = $("<tr>");
	var count = 0;
	
	for(var i = 0; i < (dataConds.length + dataActs.length) ; i++) {
		var tdOneRow = "";
		if(i < dataConds.length) {
			tdOneRow = getTdDefaultForNewRowItemDesign(dataConds[i], 1, trIndex, i);
		} else {
			tdOneRow = getTdDefaultForNewRowItemDesign(dataActs[count], 1, trIndex, i);
			count++;
		}
		allTdOneRow += tdOneRow;
	}

	var tdMinus = $('<td>').addClass('btnRemove');
	var a = $('<a>').addClass('btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action');
	$(a).attr('onclick','removeRowJS(this);');
	$(a).attr('title','Remove');
	$(a).attr('href','javascript:void(0)');
	$(tdMinus).append(a);
	
	$(tr).append(allTdOneRow).append(tdMinus);
	$(tr).find('td').find('input:hidden[name$="rowNumber"]').val(trIndex);

	return tr;
}

/**
 * 
 * @param sizeOfCond
 * @param sizeOfAct
 * @returns {String}
 */
function buildColgroup(sizeOfCond, sizeOfAct) {
	
	var percent = 100;
	var size = percent/(sizeOfCond + sizeOfAct);
	var sizeRemain = 0;
	
	// Check sum size is split equal 100%
	if((size*(sizeOfCond + sizeOfAct)) < percent) {
		sizeRemain = percent - (size*(sizeOfCond + sizeOfAct));
		size += sizeRemain;
	}
	
	var html = "";
	for(var i = 0; i < (sizeOfCond + sizeOfAct) ; i++ ) {
		html += "<col width=\""+parseInt(size, 10)+"%\">";
	}
	
	html += "<col>";
	
	return html;
}

/**
 * 
 * @param dataConds
 * @param dataActs
 * @returns {String}
 */
function buildThead(dataConds, dataActs){
	var attrCond = "align=\"center\" style=\"text-align: center;\"";
	var attrAct = "style=\"background-color: #FAF5EE;text-align: center;\" align=\"left\" valign=\"top\"";
	var headerCond =  dbMsgSource['sc.decisiontable.0023'];
	var headerAct = dbMsgSource['sc.decisiontable.0024'];
	
	var trHeader = "<tr>"
		+ "<th colspan=\""+dataConds.length+"\">"+headerCond+"</th>"
		+ "<th colspan=\""+dataActs.length+"\" style=\"background-color: #FAF5EE; text-align: center;\">"+headerAct+"</th>"
		+ "<th rowspan=\"2\"></th></tr>";
		
	var trSubHeader = "<tr>";
	var th = "";
	var count = 0;
	
	for(var i = 0; i < (dataConds.length + dataActs.length); i++) {
		if(i < dataConds.length) {
			th = "<th "+attrCond+">";
			trSubHeader += th + dataConds[i].itemName + "</th>";
		} else {
			th = "<th "+attrAct+">";
			trSubHeader += th + dataActs[count].itemName + "</th>";
			count++;
		}
	}
	
	trSubHeader += "</tr>";
	
	return trHeader+trSubHeader;
}

/**
 * 
 * @param dataConds
 * @param dataActs
 * @param dataCondGroup
 * @param dataCondItem
 */
function buildTbody(dataConds, dataActs, dataCondGroup, dataCondItem) {
	
	// Number of row for build all <tr> tags
	var tbody = $('<tbody>');
	
	if(dataCondGroup.length > 0) {
		var maxRowNum = getMaxRowNum(dataCondGroup);
		var flag = false;
		
		// In the case of all columns condition is new
		for(var pos = 0; pos < dataConds.length; pos++) {
			// is exist old column
			if(dataConds[pos].decisionItemDesignId in COLUMN_MARK) {
				flag = true;
				break;
			}
		}

		if(flag) {
			// Build row
			for(var rowIndex = 0; rowIndex <= maxRowNum; rowIndex++) {
				// Build one <tr>
				var tr = buildAllTdOneRow(rowIndex, dataConds, dataActs, dataCondGroup, dataCondItem);
				if ($(tr).html() != "") {
					$(tbody).append(tr);
				}
			}
		}
	}
	
	return tbody;
}

/**
 * Build all td one row
 */
function buildAllTdOneRow(rowIndex, dataConds, dataActs, dataCondGroup, dataCondItem) {

	var $tr = $('<tr>');
	var count = 0;
	
	for(var i = 0; i < (dataConds.length + dataActs.length); i++) {
		// True in the case of is new column
		var flagNewClmn = false;		
		// New <td> tag had create
		var tdOneRow;
		// rowspan for new column
		var rowspanNewClmn = 0;
		
		if(i < dataConds.length) {
			
			if(!(dataConds[i].decisionItemDesignId in COLUMN_MARK) 
					&& i == dataConds.length-1) {
				flagNewClmn = true;
				rowspanNewClmn = 1;
			} else if (!(dataConds[i].decisionItemDesignId in COLUMN_MARK)) {
				flagNewClmn = true;
				// Find column id in the case of more new column is sequential
				rowspanNewClmn = getRowSpanNewClmnSeq(rowIndex, i+1, dataConds, dataCondGroup);
			}
			
			tdOneRow = getTdContent(flagNewClmn, rowspanNewClmn, rowIndex, i, dataConds[i], dataCondGroup, dataCondItem);
		} else {
			
			if(!(dataActs[count].decisionItemDesignId in COLUMN_MARK)) {
				flagNewClmn = true;
				rowspanNewClmn = 1;
			}
			
			tdOneRow = getTdContent(flagNewClmn, rowspanNewClmn, rowIndex, i, dataActs[count], dataCondGroup, dataCondItem);
			count++;
		}
		
		$tr.append(tdOneRow);
	}

	var tdMinus = $('<td>').addClass('btnRemove');
	var a = $('<a>').addClass('btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action');
	$(a).attr('onclick','removeRowJS(this);');
	$(a).attr('title','Remove');
	$(a).attr('href','javascript:void(0)');
	$(tdMinus).append(a);
	
	$tr.append(tdMinus);
	
	if($.trim($tr.html()) == ""){
		return $('<tr>');
	}
	
	return $tr;
}


/**
 * 
 * @param rowIndex
 * @param tdIndex
 * @param dataConds
 * @param dataCondGroup
 * @returns {Number}
 */
function getRowSpanNewClmnSeq(rowIndex, tdIndex, dataConds, dataCondGroup){
	var rowspan = 1;
	var decisionItemDesignId;
	
	if(tdIndex == dataConds.length-1) {
		rowspan = 1;
	} else {
		var flagStop = false;
		for(var i = tdIndex; i < dataConds.length; i++) {
			if(!(dataConds[i].decisionItemDesignId in COLUMN_MARK) 
					&& i < dataConds.length-1) {
				continue;
			} else if(!(dataConds[i].decisionItemDesignId in COLUMN_MARK)
					&& i == dataConds.length-1) {
				rowspan = 1;
				break;
			} else if(dataConds[i].decisionItemDesignId in COLUMN_MARK) {
				rowspan = getRowSpanNewColumn(rowIndex, dataConds[i].decisionItemDesignId, dataCondGroup);
				break;
			}
		}
	}

	return rowspan;
}

/**
 * 
 * @param rowIndex
 * @param decisionItemDesignId
 * @param dataCondGroup
 * @returns {Number}
 */
function getRowSpanNewColumn(rowIndex, decisionItemDesignId, dataCondGroup) {
	// Initializing in the case of not have td at this column
	var rowspan = 0;
	
	for(var i = 0; i < dataCondGroup.length; i++) {
		if(decisionItemDesignId == dataCondGroup[i].decisionItemDesignId 
				&& rowIndex == dataCondGroup[i].rowNumber) {
			rowspan = dataCondGroup[i].rowSpan;
			break; 
		}
	}
	
	return rowspan;
}

/**
 * Get contend of <td> tag
 * 
 * @param flagNewClmn
 * @param rowspanNewClmn
 * @param rowIndex
 * @param itemDataCond
 * @param dataCondGroup
 * @param dataCondItem
 * @returns {String}
 */
function getTdContent(flagNewClmn, rowspanNewClmn, rowIndex, tdIndex, itemDataDesign, dataCondGroup, dataCondItem) {

	var tdContent;
	var isRowSpan = false;
	
	if (flagNewClmn && rowspanNewClmn == 0) {
		// In the case of had rowspan > 1
		return "";

	} else if(flagNewClmn && rowspanNewClmn != 0) {
		// In the case new column
		tdContent = getTdDefaultForNewRowItemDesign(itemDataDesign, rowspanNewClmn,rowIndex, tdIndex);
		return tdContent;
	} else {
		for(var i = 0; i < dataCondGroup.length; i++) {
			if(itemDataDesign.decisionItemDesignId == dataCondGroup[i].decisionItemDesignId 
					&& rowIndex == dataCondGroup[i].rowNumber) {
				tdContent = getTdContentDetail(itemDataDesign, dataCondGroup[i], dataCondItem, rowIndex, tdIndex);
				return tdContent;
			} else {
				// In the case of had rowspan > 1
				isRowSpan = true;
			}
		}
		
		// had row span
		if(isRowSpan){
			return "";
		}
	}
}

/**
 * 
 * @param itemDataDesign
 * @param rowIndex
 * @param tdIndex
 * @param rowspanNewClmn
 * @returns {String}
 */
function getTdForNewRowItemDesign(itemDataDesign, rowIndex, tdIndex, rowspanNewClmn) {
	var tdDetails = $('<td>');
	var divJoin;
	var count = 0;
	var select = "<select style=\"float: left; width: 40%;\" class=\"form-control qp-input-select\" name=\"opertatorType\">";
	var styleInputCond = "float: left;width: 45%; margin-left: 5px";
	// Input hidden for formula
	var itemValueOfFormula = "<input type=\"hidden\" name=\"formulaDefinitionDetails\" value=\"\" />";
	
	// In the case of item design is type condition
	if(itemDataDesign.itemType == 1) {
		
		var $divSpanIn = $("<div>").addClass("dropdown").css("float","right");
		var $a = $('<a>').addClass('dropdown-toggle');
		$a.attr('href',"#").attr('data-toggle','dropdown').attr('role','button').attr('aria-expanded','false');
		$a.append(' <span class=\"caret\"></span>');
		
		var $ul = $('<ul>').addClass('dropdown-menu');
		// add formula
		$ul.append($('<li>').append($('<a>').attr('href','javascript:void(0)').attr('onclick','openFomulaSetting(this);').attr('targetlabel','findTargetLabelOfFormula')
				.attr('targetvalue','findTargetValueOfFormula').attr('sourcecallback','$.qp.formulabuilder.buildDataSourceForDecisionTableDesign').text(dbMsgSource['sc.decisiontable.0026'])));
		$ul.append($('<li>').addClass('divider'));
		$ul.append($('<li>').append('<a href=\"javascript:void(0)\" onclick=\"userValue(this);\">'+dbMsgSource['sc.decisiontable.0046']+'</a>'));
		$ul.append($('<li>').addClass('divider'));
		$ul.append($('<li>').append('<a href=\"javascript:void(0)\" onclick=\"removeCondition(this);\">'+dbMsgSource['sc.decisiontable.0027']+'</a>'));
		$ul.append($('<li>').addClass('divider'));
		$ul.append($('<li>').append('<a href=\"javascript:void(0)\" onclick=\"addAndCondition(this);\">'+dbMsgSource['sc.decisiontable.0028']+'</a>'));
		$ul.append($('<li>').addClass('divider'));
		$ul.append($('<li>').append('<a href=\"javascript:void(0)\" onclick=\"addOrCondition(this);\">'+dbMsgSource['sc.decisiontable.0029']+'</a>'));
		$divSpanIn.append($a).append($ul);

		$divSpanIn.find('ul li:eq(1)').next().hide();
		$divSpanIn.find('ul li:eq(1)').hide();
		// Item type 0 : input form keyboard, 1: input from formula setting
		var itemType = 0;
		
		// Option group for select item
		var trOptionOprator = buildOptionTag("", itemDataDesign.dataType);

		var selectApp = "";
		if (trOptionOprator != undefined && trOptionOprator != "") {
			selectApp = select + trOptionOprator + "</select>"
		}

		var hiddenHtml = getInputHidden(null);
		
		var styleInputCond = "float: left;width: 45%; margin-left: 5px";
		var itemInputValueType = getInputHtml(itemDataDesign.dataType, styleInputCond , "", rowIndex, tdIndex);
		
		// Item type 1 : input form keyboard, 2: input from formula setting
		var itemLabel = '<label style="float: left; margin-left: 5px; margin-top : 4px; display:none" '
					+'class=\"qp-output-text\" name=\"formulaDefinitionContent\"></label>';
		
		if(itemDataDesign.dataType == 8) {
			$divSpanIn.find('ul li:eq(5)').next().remove();
			$divSpanIn.find('ul li:eq(5)').remove();
		}
		
		var $div = $('<div>').addClass('div-decision-row');
		$div.append(selectApp);
		$div.append(itemInputValueType);
		$div.append(itemLabel);
		$div.append(itemValueOfFormula);
		$div.append($divSpanIn);
		$div.append(hiddenHtml);
		
		if (DATATYPE.BOOLEAN == itemDataDesign.dataType) {
			$div.find('select[name="opertatorType"]').hide();
		}
			
		divJoin = $div;
	// In the case of item design type action
	} else if (itemDataDesign.itemType == 0 ) {
		
		var $divSpanIn = $("<div>").addClass("dropdown").css("float","right");
		var $a = $('<a>').addClass('dropdown-toggle');
		$a.attr('href',"#").attr('data-toggle','dropdown').attr('role','button').attr('aria-expanded','false');
		$a.append(' <span class=\"caret\"></span>');
		
		var $ul = $('<ul>').addClass('dropdown-menu');
		$ul.append($('<li>').append('<a href=\"javascript:void(0)\" targetLabel=\"findTargetLabelOfFormula\" targetValue=\"findTargetValueOfFormula\" '
				+' sourceCallback=\"$.qp.formulabuilder.buildDataSourceForDecisionTableDesign\" onclick=\"$.qp.formulabuilder.initialDataForFormulaSetting(this);\">'+dbMsgSource['sc.decisiontable.0026']+'</a>'));
		$ul.append($('<li>').addClass('divider'));
		$ul.append($('<li>').append('<a href=\"javascript:void(0)\" onclick=\"userValue(this);\">'+dbMsgSource['sc.decisiontable.0046']+'</a>'));
		$divSpanIn.append($a).append($ul);

		var hiddenHtml = getInputHidden(null);
		
		var styleInputAct = "float: left; width: 87%";
		var itemInputValueType = getInputHtml(itemDataDesign.dataType, styleInputAct , "", rowIndex, tdIndex);
		
		// Item type 1 : input form keyboard, 2: input from formula setting
		var itemLabel = '<label  style="float: left; margin-left: 5px; margin-top : 4px; display : none" '
					+'class=\"qp-output-text\" name=\"formulaDefinitionContent\"></label>';
		
		var $div = $('<div>').addClass('div-decision-row');
		$div.append(itemInputValueType);
		$div.append(itemLabel);
		$div.append(itemValueOfFormula);
		$div.append($divSpanIn);
		$div.append(hiddenHtml);
			
		divJoin = $div;
	}
	
	// Create td success
	
	$(tdDetails).css('vertical-align','top');
	$(tdDetails).attr('rowspan', rowspanNewClmn);
	$(tdDetails).append(divJoin);
	$(tdDetails).append("<input type=\"hidden\" name=\"conditionGroupId\">");
	$(tdDetails).append("<input type=\"hidden\" name=\"rowNumber\">");
	$(tdDetails).append("<input type=\"hidden\" name=\"groupType\">");
	$(tdDetails).append("<input type=\"hidden\" name=\"rowSpan\">");
	$(tdDetails).append("<input type=\"hidden\" name=\"dataType\" value=\""+itemDataDesign.dataType+"\">");
	$(tdDetails).append("<input type=\"hidden\" name=\"decisionItemDesignId\" value=\""+itemDataDesign.decisionItemDesignId+"\">");
	
	return $('<div/>').append($(tdDetails).clone()).html();

}

/**
 * 
 * @returns
 */
function getTdDefault() {
	var $newDiv = $("<div>").addClass('div-decision-row');
	
	var $divSpanIn = $("<div>").addClass('dropdown').css('float','right');
	$divSpanIn.append("<a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-expanded=\"false\"> <span class=\"caret\"></span></a>");
	
	var $ul = $('<ul>').addClass('dropdown-menu');
	$ul.append('<li><a href=\"javascript:void(0)\" onclick=\"addCondition(this);\">'+dbMsgSource['sc.decisiontable.0045']+'</a></li>');
	$ul.append('<li class=\"divider\"></li>');
	$ul.append('<li><a href=\"javascript:void(0)\" onclick=\"addOrCondition(this);\">'+dbMsgSource['sc.decisiontable.0029']+'</a></li>');
	
	$divSpanIn.append($ul);
	$newDiv.append($divSpanIn);
	
	return $newDiv;
}

/**
 * 
 * @param itemDataDesign
 * @param rowspanNewClmn
 * @param rowIndex
 * @param tdIndex
 * @returns
 */
function getTdDefaultForNewRowItemDesign(itemDataDesign, rowspanNewClmn, rowIndex, tdIndex) {
	var tdDetails = $('<td  class="word-wrap">');
	var divJoin;
	
	divJoin = getDivDefaul(itemDataDesign);
	
	// Create td success
	$(tdDetails).css('vertical-align','top');
	$(tdDetails).attr('rowspan', rowspanNewClmn);
	$(tdDetails).append(divJoin);
	$(tdDetails).append("<input type=\"hidden\" name=\"conditionGroupId\">");
	$(tdDetails).append("<input type=\"hidden\" name=\"rowNumber\">");
	$(tdDetails).append("<input type=\"hidden\" name=\"groupType\">");
	$(tdDetails).append("<input type=\"hidden\" name=\"rowSpan\">");
	$(tdDetails).append("<input type=\"hidden\" name=\"dataType\" value=\""+itemDataDesign.dataType+"\">");
	$(tdDetails).append("<input type=\"hidden\" name=\"decisionItemDesignId\" value=\""+itemDataDesign.decisionItemDesignId+"\">");
	
	return $('<div/>').append(tdDetails.clone()).html();
}

/**
 * 
 * @param itemDataDesign
 * @returns
 */
function getDivDefaul(itemDataDesign) {
	var divJoin;
	// In the case of item design is type condition
	if(itemDataDesign.itemType == 1) {
		// Process append new menu context
		var $newDiv = getTdDefault();
		divJoin =  $("<div />").append($newDiv.clone()).html();
	
	// In the case of item design type action
	} else if (itemDataDesign.itemType == 0 ) {

		var $divSpanIn =  $('<div>').addClass('dropdown').css('float','right');
		var $ul = $('<ul>').addClass("dropdown-menu");
		$ul.append($('<li>').append($('<a>').attr('href','javascript:void(0)').attr('onclick','openFomulaSetting(this);').attr('targetlabel','findTargetLabelOfFormula')
				.attr('targetvalue','findTargetValueOfFormula').attr('sourcecallback','$.qp.formulabuilder.buildDataSourceForDecisionTableDesign').text(dbMsgSource['sc.decisiontable.0026'])));
		$ul.append($('<li>').addClass('divider'));
		$ul.append($('<li>').append('<a href=\"javascript:void(0)\" onclick=\"userValue(this);\">'+dbMsgSource['sc.decisiontable.0046']+'</a>'));
		$divSpanIn.append("<a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-expanded=\"false\"> <span class=\"caret\"></span></a>");
		$divSpanIn.append($ul);
		
		$divSpanIn.find('ul li:eq(1)').next().hide();
		$divSpanIn.find('ul li:eq(1)').hide();
		
		var hiddenHtml = getInputHidden(null);
		var styleInputAct = "float: left; width: 87%";
		var itemInputValueType = getInputHtml(itemDataDesign.dataType, styleInputAct , "");

		// Item type 1 : input form keyboard, 2: input from formula setting
		var itemLabel = '<label style="float: left;margin-left : 5px; display:none" '
					+'class=\"qp-output-text\" name=\"formulaDefinitionContent\"></label>';
		// Input hidden for formula
		var itemValueOfFormula = "<input type=\"hidden\" name=\"formulaDefinitionDetails\" value=\"\" />";
		
		divJoin = $('<div>').addClass('div-decision-row');

		$(divJoin).append(itemInputValueType);
		$(divJoin).append(itemLabel);
		$(divJoin).append(itemValueOfFormula);
		$(divJoin).append($divSpanIn);
		$(divJoin).append(hiddenHtml);
	}

	return divJoin;
}

/**
 * 
 * @param dataType
 * @param styleInput
 * @param itemValue
 * @param rowIndex
 * @param tdIndex
 * @returns {String}
 */
function getInputHtml(dataType, styleInput, itemValue, rowIndex, tdIndex) {
	var itemInputValueType = "";
	
	switch (parseInt(dataType)) {
	
	case DATATYPE.BYTE:
		// temporary
		itemInputValueType = "<input style=\""+styleInput+"\" name=\"itemValue\" type=\"text\" class=\"form-control qp-input-smallint\" value=\""+itemValue+"\">";
		break;

	case DATATYPE.SHORT:
		itemInputValueType = "<input style=\""+styleInput+"\" name=\"itemValue\" type=\"text\" class=\"form-control qp-input-smallint\" value=\""+itemValue+"\">";
		break;
	
	case DATATYPE.INT:
		
		itemInputValueType = "<input style=\""+styleInput+"\" name=\"itemValue\" type=\"text\" class=\"form-control qp-input-integer\" value=\""+itemValue+"\">";
		break;
	
	case DATATYPE.LONG:
		itemInputValueType = "<input style=\""+styleInput+"\" name=\"itemValue\" type=\"text\" class=\"form-control qp-input-bigint\" value=\""+itemValue+"\">";
		
		break;
	
	case DATATYPE.FLOAT:
		itemInputValueType = "<input style=\""+styleInput+"\" name=\"itemValue\" type=\"text\" class=\"form-control qp-input-float\" value=\""+itemValue+"\">";
		break;
	
	case DATATYPE.DOUBLE:
		// temporay
		itemInputValueType = "<input style=\""+styleInput+"\" name=\"itemValue\" type=\"text\" class=\"form-control qp-input-float\" value=\""+itemValue+"\">";
		break;
		
	case DATATYPE.CHAR:
		itemInputValueType = "<input style=\""+styleInput+"\" name=\"itemValue\" type=\"text\" class=\"form-control qp-input-text\" value=\""+itemValue+"\">";
		break;
	
	case DATATYPE.BOOLEAN:
		className = "qp-input-radio qp-input-radio-margin";
		
		// buil button radio
		var inputRadio = "";
		for(var j in CL_SUPPORT_OPTION_VALUE_FLAG)
		{
			if(j == itemValue) {
				inputRadio += "<input id=\"radio."+rowIndex+"."+tdIndex+"."+j+"\" type=\"radio\" name=\""+rowIndex+"."+tdIndex+".itemValue\" class=\""+className+"\" value=\""+j+"\" checked=\"checked\">"
							+ "<label for=\"radio."+rowIndex+"."+tdIndex+"."+j+"\">"+CL_SUPPORT_OPTION_VALUE_FLAG[j]+"</label>";
				continue;
			}
			
			inputRadio += "<input id=\"radio."+rowIndex+"."+tdIndex+"."+j+"\" type=\"radio\" name=\""+rowIndex+"."+tdIndex+".itemValue\" class=\""+className+"\" value=\""+j+"\">"
						+ "<label for=\"radio."+rowIndex+"."+tdIndex+"."+j+"\">"+CL_SUPPORT_OPTION_VALUE_FLAG[j]+"</label>";
		}
		
		itemInputValueType = "<div style=\"float:left; width:50%; margin-left:0px; text-align:left;\">"+inputRadio+"</div>";
		
		break;
	
	case DATATYPE.STRING:
		itemInputValueType = "<input style=\""+styleInput+"\" name=\"itemValue\" type=\"text\" class=\"form-control qp-input-text\" value=\""+itemValue+"\">";
		break;
	
	case DATATYPE.BIGDECIMAL:
		itemInputValueType = "<input style=\""+styleInput+"\" name=\"itemValue\" type=\"text\" class=\"form-control qp-input-float\" value=\""+itemValue+"\">";
		break;
	
	case DATATYPE.TIMESTAMP:
		// temporary
		itemInputValueType = "<div style=\""+styleInput+"\" class=\"input-group date qp-input-datetimepicker-detail\" >"
		+ "<input type=\"text\" name=\"itemValue\" class=\"form-control\" value=\""+itemValue+"\">"
		+ "<span class=\"input-group-addon\">"
		+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
		+ "</span></div>";
	
	break;
	
	case DATATYPE.DATETIME:
		itemInputValueType = "<div style=\""+styleInput+"\" class=\"input-group date qp-input-datetimepicker-detail\" >"
			+ "<input type=\"text\" name=\"itemValue\" class=\"form-control\" value=\""+itemValue+"\">"
			+ "<span class=\"input-group-addon\">"
			+ "<span class=\"glyphicon glyphicon-calendar\"></span>"
			+ "</span></div>";
		
		break;
		
	case DATATYPE.TIME:
		
		itemInputValueType = "<div style=\""+styleInput+"\" class=\"input-group date qp-input-timepicker\">"
			+ "<input type=\"text\" name=\"itemValue\" class=\"form-control\" value=\""+itemValue+"\">"
			+ "<span class=\"input-group-addon\">"
			+ "<span class=\"glyphicon glyphicon-time\"></span>"
			+ "</span></div>";
		
		break;

	case DATATYPE.DATE:
		
		itemInputValueType = "<div style=\""+styleInput+"\" class=\"input-group date qp-input-datepicker\">"
			+ "<input type=\"text\" name=\"itemValue\" class=\"form-control\" value=\""+itemValue+"\">"
			+ "<span class=\"input-group-addon\">"
			+ "<span class=\"glyphicon glyphicon-time\"></span>"
			+ "</span></div>";
		
		break;
		
	default: // when Object
		break;
	}
	
	return itemInputValueType;
}

/**
 * 
 * @param itemDataDesign
 * @param itemdataCondGroup
 * @param dataCondItem
 * @returns {String}
 */
function getTdContentDetail(itemDataDesign, itemdataCondGroup, dataCondItem, rowIndex, tdIndex) {
	var tdDetails = $('<td  class="word-wrap">');
	var $divJoin = $('<div>');
	var count = 0;

	var select = "<select style=\"float: left; width: 40%;\" class=\"form-control qp-input-select\" name=\"opertatorType\">";
	var styleInputCond = "float: left;width: 45%; margin-left: 5px";
	var styleInputAct = "float: left; width: 87%";
	var classTd = "";

	var itemValueOfFormula = "<input type=\"hidden\" name=\"formulaDefinitionDetails\" value=\"\" />";
	
	for(var i = 0; i < dataCondItem.length; i++) {
		// In the case of item design is type condition
		if(itemDataDesign.itemType == 1 
				&& itemdataCondGroup.conditionGroupId == dataCondItem[i].conditionGroupId) {
			var $divSpanIn = $("<div>").addClass("dropdown").css("float","right");
			
			var $a = $('<a>').addClass('dropdown-toggle');
			$a.attr('href',"#").attr('data-toggle','dropdown').attr('role','button').attr('aria-expanded','false');
			$a.append(' <span class=\"caret\"></span>');

			var $ul = $('<ul>').addClass('dropdown-menu');
			// adding for formula
			$ul.append($('<li>').append($('<a>').attr('href','javascript:void(0)').attr('onclick','openFomulaSetting(this);').attr('targetlabel','findTargetLabelOfFormula')
					.attr('targetvalue','findTargetValueOfFormula').attr('sourcecallback','$.qp.formulabuilder.buildDataSourceForDecisionTableDesign').text(dbMsgSource['sc.decisiontable.0026'])));
			$ul.append($('<li>').addClass('divider'));
			$ul.append($('<li>').append('<a href=\"javascript:void(0)\" onclick=\"userValue(this);\">'+dbMsgSource['sc.decisiontable.0046']+'</a>'));
			$ul.append($('<li>').addClass('divider'));
			$ul.append($('<li>').append('<a href=\"javascript:void(0)\" onclick=\"removeCondition(this);\">'+dbMsgSource['sc.decisiontable.0027']+'</a>'));
			$ul.append($('<li>').addClass('divider'));
			$ul.append($('<li>').append('<a href=\"javascript:void(0)\" onclick=\"addAndCondition(this);\">'+dbMsgSource['sc.decisiontable.0028']+'</a>'));
			$ul.append($('<li>').addClass('divider'));
			$ul.append($('<li>').append('<a href=\"javascript:void(0)\" onclick=\"addOrCondition(this);\">'+dbMsgSource['sc.decisiontable.0029']+'</a>'));
			$divSpanIn.append($a).append($ul);

			// Item type 1 : input form keyboard, 2: input from formula setting
			var itemType = dataCondItem[i].itemType;
			// Set class for process menu context
			var itemInputValueType;
			// Input hidden for formula
			var itemLabel;

			if (itemType != 1) {
				itemInputValueType = getInputHtml(itemDataDesign.dataType, styleInputCond , "", rowIndex, tdIndex);
				itemLabel = '<label  style="float: left;margin-left: 5px; margin-top : 4px; display : block" '
						+'class=\"qp-output-text\" name=\"formulaDefinitionContent\">'+dataCondItem[i].formulaDefinitionContent+'</label>';
				
			} else {
				itemInputValueType = getInputHtml(itemDataDesign.dataType, styleInputCond , dataCondItem[i].itemValue, rowIndex, tdIndex);
				itemLabel = '<label  style="float: left; margin-left: 5px; margin-top : 4px; display : none" '
						+'class=\"qp-output-text\" name=\"formulaDefinitionContent\"></label>';
			}
			
			// In the case of radio
			if(DATATYPE.BOOLEAN == itemDataDesign.dataType) {
				$divSpanIn.find('ul li:eq(5)').next().remove();
				$divSpanIn.find('ul li:eq(5)').remove();
			}
			
			// Is item single
			if(itemdataCondGroup.groupType == 1 ) {
				
				// Option group for select item
				var selectApp = "";
				var trOptionOprator = buildOptionTag(dataCondItem[i].opertatorType, itemDataDesign.dataType);
				if (trOptionOprator != undefined && trOptionOprator.length > 0) {
					selectApp += select +  trOptionOprator + "</select>";
				}

				var hiddenHtml = getInputHidden(dataCondItem[i]);
				
				var $div = $('<div>').addClass('div-decision-row');
				$div.append(selectApp);
				$div.append(itemInputValueType);
				$div.append(itemLabel);
				$div.append(itemValueOfFormula);
				$div.append($divSpanIn);
				$div.append(hiddenHtml);
				
				// formular
				if (itemType != 1) {
					var convertJson;
					if(dataCondItem[i].formulaDefinitionDetails != undefined && dataCondItem[i].formulaDefinitionDetails.length > 0 ) {
						convertJson = $.qp.decisiontable.convertToString(dataCondItem[i].formulaDefinitionDetails);
						$div.find('input:hidden[name="formulaDefinitionDetails"]').val(convertJson);
					}
				}
				
				$divJoin.append($div);
				
				break;
			} else {
				
				// Option group for select item
				var selectApp = "";
				var trOptionOprator = buildOptionTag(dataCondItem[i].opertatorType, itemDataDesign.dataType);
				if (trOptionOprator != undefined && trOptionOprator.length > 0) {
					selectApp += select +  trOptionOprator + "</select>";
				}
				
				var divAnd = "<div style=\"float: left; width: 100%;\"><span style=\"text-align: left; float:left; margin-left: 5px; margin-top: -7px;\">"+dbMsgSource['sc.decisiontable.0047']+"</span></div>";
				count++;
				var hiddenHtml = getInputHidden(dataCondItem[i]);
				
				if(count > 1) {
					// Div have [AND] conditon

					var $divNext = $('<div>').addClass('div-decision-row');
					$divNext.append(divAnd);
					$divNext.append(selectApp);
					$divNext.append(itemInputValueType);
					$divNext.append(itemLabel);
					$divNext.append(itemValueOfFormula);
					$divNext.append($divSpanIn);
					$divNext.append(hiddenHtml);
					
					if (itemType != 1) {
						var convertJson;
						if(dataCondItem[i].formulaDefinitionDetails != undefined && dataCondItem[i].formulaDefinitionDetails.length > 0 ) {
							convertJson = $.qp.decisiontable.convertToString(dataCondItem[i].formulaDefinitionDetails);
							$divNext.find('input:hidden[name="formulaDefinitionDetails"]').val(convertJson);
						}
					}
					
					$divJoin.append($divNext);

				} else {
					
					var $divFisrt = $('<div>').addClass('div-decision-row');
					$divFisrt.append(selectApp);
					$divFisrt.append(itemInputValueType);
					$divFisrt.append(itemLabel);
					$divFisrt.append(itemValueOfFormula);
					$divFisrt.append($divSpanIn);
					$divFisrt.append(hiddenHtml);
					
					if (itemType != 1) {
						var convertJson;
						if(dataCondItem[i].formulaDefinitionDetails != undefined && dataCondItem[i].formulaDefinitionDetails.length > 0 ) {
							convertJson = $.qp.decisiontable.convertToString(dataCondItem[i].formulaDefinitionDetails);
							$divFisrt.find('input:hidden[name="formulaDefinitionDetails"]').val(convertJson);
						}
					}
					
					$divJoin.append($divFisrt);
				}
			}
		// In the case of item design type action
		} else if (itemDataDesign.itemType == 0 
				&& itemdataCondGroup.conditionGroupId == dataCondItem[i].conditionGroupId) {
			var $divSpanIn = $("<div>").addClass("dropdown").css("float","right");
			var $a = $('<a>').addClass('dropdown-toggle');
			$a.attr('href',"#").attr('data-toggle','dropdown').attr('role','button').attr('aria-expanded','false');
			$a.append(' <span class=\"caret\"></span>');
			
			var $ul = $('<ul>').addClass('dropdown-menu');
			$ul.append($('<li>').append($('<a>').attr('href','javascript:void(0)').attr('onclick','openFomulaSetting(this);').attr('targetlabel','findTargetLabelOfFormula')
					.attr('targetvalue','findTargetValueOfFormula').attr('sourcecallback','$.qp.formulabuilder.buildDataSourceForDecisionTableDesign').text(dbMsgSource['sc.decisiontable.0026'])));
			$ul.append($('<li>').addClass('divider'));
			$ul.append($('<li>').append('<a href=\"javascript:void(0)\" onclick=\"userValue(this);\">'+dbMsgSource['sc.decisiontable.0046']+'</a>'));
			$divSpanIn.append($a).append($ul);
			
			// Item type 1 : input form keyboard, 2: input from formula setting
			var itemType = dataCondItem[i].itemType;
			var itemLabel;

			if (itemType != 1) {
				itemLabel = '<label  style="float: left;display : block" '
						+'class=\"qp-output-text\" name=\"formulaDefinitionContent\">'+dataCondItem[i].formulaDefinitionContent+'</label>';
			} else {
				itemLabel = '<label  style="float: left; display : none" '
						+'class=\"qp-output-text\" name=\"formulaDefinitionContent\"></label>';

			}
			
			// Set class for process menu context
			if(itemdataCondGroup.groupType == 1 ) {
				var hiddenHtml = getInputHidden(dataCondItem[i]);
				var itemInputValueType = getInputHtml(itemDataDesign.dataType, styleInputAct , dataCondItem[i].itemValue, rowIndex, tdIndex);
					
				var $div = $('<div>').addClass('div-decision-row');
				$div.append(itemInputValueType);
				$div.append(itemLabel);
				$div.append(itemValueOfFormula);
				$div.append($divSpanIn);
				$div.append(hiddenHtml);
				
				if (itemType != 1) {
					var convertJson;
					if(dataCondItem[i].formulaDefinitionDetails != undefined && dataCondItem[i].formulaDefinitionDetails.length > 0 ) {
						convertJson = $.qp.decisiontable.convertToString(dataCondItem[i].formulaDefinitionDetails);
						$div.find('input:hidden[name="formulaDefinitionDetails"]').val(convertJson);
					}
				}
				
				$divJoin.append($div);

				break;
			}
		}
	}
	
	if($divJoin.children().length == 0) {
		// Process append new menu context
		var $newDiv = getTdDefault();
		$divJoin.append($newDiv);
	}
	
	// Create td success
	$(tdDetails).css('vertical-align','top');
	$(tdDetails).attr('rowspan', itemdataCondGroup.rowSpan);
	$(tdDetails).append($divJoin.children());
	$(tdDetails).append("<input type=\"hidden\" name=\"conditionGroupId\" value=\""+itemdataCondGroup.conditionGroupId+"\">");
	$(tdDetails).append("<input type=\"hidden\" name=\"rowNumber\" value=\""+itemdataCondGroup.rowNumber+"\">");
	$(tdDetails).append("<input type=\"hidden\" name=\"groupType\" value=\""+itemdataCondGroup.groupType+"\">");
	$(tdDetails).append("<input type=\"hidden\" name=\"rowSpan\" value=\""+itemdataCondGroup.rowSpan+"\">");
	$(tdDetails).append("<input type=\"hidden\" name=\"dataType\" value=\""+itemDataDesign.dataType+"\">");
	$(tdDetails).append("<input type=\"hidden\" name=\"decisionItemDesignId\" value=\""+itemDataDesign.decisionItemDesignId+"\">");

	tdDetails = getChangeDisplayType(tdDetails);
	
	return tdDetails;
}

function getChangeDisplayType(obj){
	var dataType = $(obj).closest('td').find('input:hidden[name="dataType"]').val();
	
	$(obj).find('.div-decision-row input:hidden[name="itemType"]').each(function(){
		// in the case item formular
		if($(this).val() != 1 ) {
			
			switch (parseInt(dataType)) {

			case DATATYPE.BOOLEAN:
			case DATATYPE.TIMESTAMP:
			case DATATYPE.DATETIME:
			case DATATYPE.TIME:
			case DATATYPE.DATE:
				$(this).closest('div.div-decision-row').find('div:first').hide();
				break;

			default:
				$(this).closest('div.div-decision-row').find('input[name="itemValue"]').hide().val('');
				break;
			}

		} else {

			if (DATATYPE.BOOLEAN == dataType) {
				$(this).closest('div.div-decision-row').find('select[name="opertatorType"]').hide();
			}
			
			$(this).closest('div.div-decision-row').find('ul li:eq(1)').next().hide();
			$(this).closest('div.div-decision-row').find('ul li:eq(1)').hide();
		}
	});
	
	return obj;
}

/**
 * 
 * @param dataCondItem
 * @returns {String}
 */
function getInputHidden(dataCondItem) {
	var hiddenHtml = "";
	if(dataCondItem != undefined && dataCondItem != null ){
		hiddenHtml = "<input type=\"hidden\" name=\"conditionItemId\" value=\""+dataCondItem.conditionItemId+"\">"
		+ "<input type=\"hidden\" name=\"conditionGroupId\" value=\""+dataCondItem.conditionGroupId+"\">"
		+ "<input type=\"hidden\" name=\"itemSequenceNo\" value=\""+dataCondItem.itemSequenceNo+"\">"
		+ "<input type=\"hidden\" name=\"itemType\" value=\""+dataCondItem.itemType+"\">";
	} else {
		hiddenHtml = "<input type=\"hidden\" name=\"conditionItemId\" value=\"\">"
		+ "<input type=\"hidden\" name=\"conditionGroupId\" value=\"\">"
		+ "<input type=\"hidden\" name=\"itemSequenceNo\" value=\"1\">"
		+ "<input type=\"hidden\" name=\"itemType\" value=\"1\">";
	}

	return hiddenHtml;
}

/**
 * 
 * @param opertatorType
 * @param dataType
 * @returns {String}
 */
function buildOptionTag(opertatorType, dataType) {
	
	var trOptionOprator = "";
	
	if (dataType == 1 || dataType == 2 
			|| dataType == 3 || dataType == 4 
			|| dataType == 5 || dataType == 6
			|| dataType == 8 || dataType == 10
			|| dataType == 11 || dataType == 12 
			|| dataType == 13 || dataType == 15) {
		
		for(var i in CL_QP_OPERATORTYPE)
		{
			if(opertatorType != "" && i == opertatorType) {
				trOptionOprator += "<option value=\""+i+"\" selected=\"selected\">"+CL_QP_OPERATORTYPE[i]+"</option>";
				continue;
			}
			
			trOptionOprator += "<option value=\""+i+"\">"+CL_QP_OPERATORTYPE[i]+"</option>";
		}
	} else if (dataType == 7 || dataType == 9 || dataType == 8) {
		for(var i in CL_QP_OPERATORTYPE)
		{
			if(opertatorType != "" && i == opertatorType) {
				trOptionOprator += "<option value=\""+i+"\" selected=\"selected\">"+CL_QP_OPERATORTYPE[i]+"</option>";
				continue;
			}
			
			if (i == 1 || i == 6) {
				trOptionOprator += "<option value=\""+i+"\">"+CL_QP_OPERATORTYPE[i]+"</option>";
			}
		}
	}

	return trOptionOprator
}

/**
 * 
 * @param dataCondGroup
 * @returns maxNum
 */
function getMaxRowNum(dataCondGroup) {
	var maxNum = dataCondGroup[0].rowNumber;
	for(var i = 1; i < dataCondGroup.length; i++)
		if(maxNum < dataCondGroup[i].rowNumber) maxNum = dataCondGroup[i].rowNumber;
	
	return maxNum;
}

/**
 * 
 * @param obj
 */
function removeCondition(obj) {
	var clsName = 'div-decision-row';
	var $div = $(obj).closest("div[class="+clsName+"]");
	
	// alone
	if($div.prev('.'+clsName).length == 0 
			&& $div.next('.'+clsName).length == 0) {
		// Call back up table after remove
		var $td = $div.parent();
		// Remove conditon current
		$div.remove();
		
		// Process append new menu context
		var $newDiv = getTdDefault();
		$td.prepend($newDiv);
	// first and friend behide
	} else if($div.prev('.'+clsName).length == 0 
			&& $div.next('.'+clsName).length > 0) {
		$div.next().find('div').first().remove();
		$div.remove();
	// between and last.
	} else {
		$div.remove();
	}
	
	return;
}

/**
 * 
 * @param obj
 */
function addCondition(obj) {
	
	var $td = $(obj).closest('td');
	var itemDesign = new Object();
	itemDesign.itemType = 1;
	itemDesign.dataType = parseInt($td.find("input[name$='dataType']").val());
	
	var td = getTdForNewRowItemDesign(itemDesign, -1, -1, 1);
	var $divJoin = $(td).find('div[class="div-decision-row"]');
	
	$td.find('div').remove();
	$td.prepend($divJoin);
	
	$.qp.initialAutoNumeric($td);
	$.qp.initialAllPicker($td);
	
	renameOfRadio($td.closest('table'));
	
	return;
}

function userValue(obj) {
	var $div = $(obj).closest('div[class="div-decision-row"]');
	var dataType = $div.closest('td').find('input:hidden[name="dataType"]').val();
	
	switch (parseInt(dataType)) {
	
	case DATATYPE.BOOLEAN:
		$div.find('select[name="opertatorType"]').hide();
		$div.find('div:first').show();
		$div.find('div:first').next('label').hide();
		break;
	case DATATYPE.TIMESTAMP:
	case DATATYPE.DATETIME:
	case DATATYPE.TIME:
	case DATATYPE.DATE:
		$div.find('div.input-group.date').show();
		$div.find('div.input-group.date').next('label').hide();
		break;

	default:
		
		if ($div.find('input[name$="itemValue"]').is(':hidden')) {
			$div.find('input[name$="itemValue"]').show();
			$div.find('input[name$="itemValue"]').next('label').hide();
		}
		break;
	}
	
	$div.find('input:hidden[name="itemType"]').val(1);
	$div.find('ul li:eq(1)').next().hide();
	$div.find('ul li:eq(1)').hide();
}

/*
 * 
 */
function openFomulaSetting(obj) {

	var $div = $(obj).closest('div[class="div-decision-row"]');
	var dataType = $div.closest('td').find('input:hidden[name="dataType"]').val();

	switch (parseInt(dataType)) {
	
	case DATATYPE.BOOLEAN:
		$div.find('select[name="opertatorType"]').show();
		$div.find('div:first').hide();
		$div.find('div:first').next('label').show();
		break;
	case DATATYPE.TIMESTAMP:
	case DATATYPE.DATETIME:
	case DATATYPE.TIME:
	case DATATYPE.DATE:
		$div.find('div.input-group.date').hide();
		$div.find('div.input-group.date').next('label').show();
		break;

	default:
		if ($div.find('input[name$="itemValue"]').is(':visible')) {
			$div.find('input[name$="itemValue"]').hide();
			$div.find('input[name$="itemValue"]').next('label').show();
		}
		break;
	}

	$div.find('ul li:eq(1)').next().show();
	$div.find('ul li:eq(1)').show();
	$div.find('input:hidden[name="itemType"]').val(0);
	
	$.qp.formulabuilder.initialDataForFormulaSetting(obj);
}

/**
 * 
 * @param obj
 */
function addAndCondition(obj) {
	var clsName = 'div-decision-row';
	var $div = $(obj).closest("div[class="+clsName+"]");
	var itemDesign = new Object();
	itemDesign.itemType = 1;
	itemDesign.dataType = parseInt($div.parent().find("input[name$='dataType']").val());
	
	// If radio button then not add and condition
	if(itemDesign.dataType != 8) {
		var td = getTdForNewRowItemDesign(itemDesign, -1, -1, 1);
		
		var $divAnd = $("<div>", {style: "float: left; width: 100%;"});
		$divAnd.append('<span style=\"text-align: left; float:left; margin-left: 5px; margin-top: -7px;\">AND</span>');
		
		var $divAfter = $(td).find('div[class="div-decision-row"]');
		
		$.qp.initialAutoNumeric($divAfter);
		$.qp.initialAllPicker($divAfter);
		
		$divAfter.find('select').before($divAnd);

		$div.after($divAfter);
		
		renameOfRadio($div.closest('table'));
	}
	
	return;
}

/**
 * 
 * @param obj
 */
function addOrCondition(obj) {
	
	var trNew = $("<tr></tr>");
	var tdCurr = $(obj).closest("td");
	var tdIndex = $(tdCurr).index();
	var rowspanCurr = parseInt($(tdCurr).attr('rowspan'));
	var trCurr = $(tdCurr).parent();
	var trIndex = $(trCurr).index();
	var sizeCurr = $(trCurr).find('td').size();
	
	// find tr first to count zise of td
	var $table = $(tdCurr).parent('tr').closest('table');
	var sizeFix = $table.find(">tbody>tr:first>td").size();
	var arrTr = new Array();
	
	// Process in the case of td select has rowspan=1	
	// In the case td is full
	if(sizeCurr == sizeFix) {
		$(trCurr).find('td').each(function(i) {
			if(i < tdIndex) {
				$(this).attr('rowspan', parseInt($(this).attr('rowspan'))+rowspanCurr);
			} else if(i == tdIndex) {
				$(trNew).append($(this).clone());
			} else {
				$(trNew).append($(this).clone());
			}
		});
		
		if(rowspanCurr > 1) {
			arrTr.push(trNew);
			// clone tr remain
			// Remove current row had create
			var index = (rowspanCurr-1);
			$(trCurr).nextAll().each(function(i) {
				if(i < index) {
					arrTr.push($(this).clone());
				}
			});
		}
		
	} else {
		// sizeCurr < sizeFix
		$(trNew).append($(tdCurr).clone());
		// Append all td after
		$(tdCurr).nextAll().each(function(i) {
			$(trNew).append($(this).clone());
		});
		
		if(rowspanCurr > 1) {
			arrTr.push(trNew);
			// clone tr remain
			// Remove current row had create
			var index = (rowspanCurr-1);
			$(trCurr).nextAll().each(function(i) {
				if(i < index) {
					arrTr.push($(this).clone());
				}
			});
		}
		
		$(tdCurr).prevAll().each(function(i) {
			
			var rowspan = parseInt($(this).attr('rowspan'));
			$(this).attr('rowspan', rowspan+rowspanCurr);
			tdIndex--;
		});
		
		// Get index real by size fix
		tdIndex = (sizeFix - sizeCurr + tdIndex);
		
		while(sizeCurr < sizeFix) {
			
			tdIndex--;
			var isTrue = isSetRowSpan(tdIndex, rowspanCurr, sizeFix, trCurr);
			if(isTrue) {
				sizeCurr++;
			}
		}
	}

	if(rowspanCurr == 1){
		arrTr.push(trNew);
	}
	
	var arrTmp = resetDataGroup(arrTr);
	
	// Append in the case of td had rowspan > 1
	if(rowspanCurr > 1) {
		var rowAppendIdx = trIndex + rowspanCurr - 1;
		
		for(var i = 0; i < arrTmp.length; i++) {
			var trAppend = $(trCurr).closest('table').find('>tbody>tr:eq('+rowAppendIdx+')');
			$(trAppend).after(arrTmp[i]);
			rowAppendIdx++;
		}
	} else {
		// Append in the case of td had row span = 1
		var trCurrHadChange = $(obj).closest("tr");
		$(trCurrHadChange).after(arrTmp[0]);
	}
	
	// Displat format
	$.qp.initialAutoNumeric($table);
	$.qp.initialAllPicker($table);
	// Rename all radio all table
	renameOfRadio($table);
	
	return;
}

/* Processing dialog */
function findTargetValueOfFormula(thiz){
	return $(thiz).closest('div[class="div-decision-row"]').find("[name='formulaDefinitionDetails']");
}

function findTargetLabelOfFormula(thiz){
	return $(thiz).closest('div[class="div-decision-row"]').find("[name='formulaDefinitionContent']");
}

/**
 * 
 * @param arrTr
 * @returns
 */
function resetDataGroup(arrTr) {
	for(var i = 0; i < arrTr.length; i++) {
		$(arrTr[i]).find("td[class!='btnRemove']").each(function() {
			//reset data
			$(this).find('input[name!="dataType"][name!="decisionItemDesignId"][type!="radio"]').val("");
			$(this).find('div[class="div-decision-row"]').each(function() {
				
				$(this).find('input').each(function(k){
					if($(this).attr('type') == 'radio') {
						$(this).attr('name', k+"."+$(this).attr('name'))
							   .removeAttr("checked");
					} else if($(this).attr('name') == 'itemType') {
						$(this).val(1);
					} else {
						$(this).val('');
					}
				});
				$(this).find('select').val('1');
				$(this).find('label[name="formulaDefinitionContent"]').text('');
			});
		});
	}
	
	return arrTr;
}

/**
 * 
 * @param tdIndex
 * @param rowspanCurr
 * @param sizeFix
 * @param trCurr
 * @returns {Boolean}
 */
function isSetRowSpan(tdIndex, rowspanCurr, sizeFix, trCurr) {
	var flag = false;
	$(trCurr).prevAll().each(function(i) {
		
		if(!flag) {
			
			var numTd = $(this).find('td').size();
			var pos = tdIndex - (sizeFix - numTd);

			var rowspan = parseInt($(this).find('td:eq('+pos+')').attr('rowspan'));
			if(rowspan > 1){
				$(this).find('td:eq('+pos+')').attr('rowspan', rowspan+rowspanCurr);
				flag = true;
			}
		}
	});
	
	return flag;
}

/**
 * 
 * @param obj
 */
function addRowJS(obj) {
	var table = $(obj).closest("div").prev("table");
	var indexTrLast = $(table).find('tr:last').index();
	
	var dataConds = $.qp.decisiontable.convertToJson($('form').find("input[name$='listItemCondition']").val());
	var dataActs = $.qp.decisiontable.convertToJson($('form').find("input[name$='listItemAction']").val());
	
	if(dataConds.length > 0 && dataActs.length > 0) {
		var $template = buildTemplateJs(dataConds, dataActs, indexTrLast+1);
		// append template
		$(table).find('tbody').append($template);

		$template.find(".qp-input-timepicker").each(function() {
			$.qp.initialTimePicker($(".qp-input-timepicker"));
		});
		$template.find(".qp-input-datetimepicker-detail").each(function() {// initial
			$.qp.initialDateTimePickerDetail($(this));
		});
		$template.find(".qp-input-autocomplete").each(function() {// initial
			$.qp.initialAutocomplete($(this));
		});

		$.qp.initialAutoNumeric($template);

	} else {
		console.log(dataConds);
		console.log(dataActs);
	}
	
	renameOfRadio('#'+TABLE_NAME.LOGIC);
}

/**
 * Remove one row
 */
function removeRowJS(obj){
	
	// Innitializing variable
	var trCurr = $(obj).closest("td").parent();
	var trIndex = $(trCurr).index();
	var table = $(trCurr).closest('table');
	var sizeFix = $(table).find(">tbody>tr:first>td").size();
	var sizeCurr = $(trCurr).find('td').size();
	
	// Check rowspan is exist on one row	
	$(trCurr).find('td:last').prevAll().each(function() {
		var rowspan  = parseInt($(this).attr('rowspan'));
		if(rowspan > 1) {
			// Subjact rowpsan by 1 
			$(this).attr('rowspan', rowspan-1);
			// Append current td subjact rowspan in the firsr td of next row
			$(this).parent().next('tr').prepend($(this).clone());
		} 
	});
	
	// In the case not had rowspan && full td of row standard
	if(sizeCurr == sizeFix) {
		// Remove row current
		$(trCurr).remove();
	// In the case had rowspan && not full td of row standard
	} else {
		// Get the index of row preview
		var tdIndex = (sizeFix - sizeCurr);
		
		while(sizeCurr < sizeFix) {
		
			tdIndex--;
			var isTrue = isSetRowSpan(tdIndex, -1, sizeFix, trCurr);
			if(isTrue) {
				sizeCurr++;
			}
		}

		// Remove row current
		$(trCurr).remove();
	}
}

/**
 * 
 * @param table
 */
function renameOfRadio(table) {
	$(table).find('>tbody>tr').each(function(rowIdx){
		$(this).find('td[class!="btnRemove"]').each(function(tdIdx){
			$(this).find('input:radio').each(function(seqIdx) {
				var idRadioNm = $(this).attr('id').split('.')[0];
				var itemValue = $(this).attr('name').split('.');
				
				$(this).attr('id', idRadioNm+"."+rowIdx+"."+tdIdx+"."+seqIdx );
				$(this).attr('name', rowIdx+"."+tdIdx+"."+itemValue[itemValue.length-1]);
				// Set label for
				$(this).next('label').attr('for', $(this).attr('id'));
			});
		});
	});
}


function getDataSourceAutocompleteBD(event) {
	var searchKey = event.searchKey;
	var dataResult = [];
	var mKeyCode = new Array();
	var arg01 =  event.arg01;
	var isOnlyGetParent =  (event.arg02 != undefined && event.arg02!= null && event.arg02.length >0) ? eval(event.arg02) : false;
	var dataArg01 = (arg01 != undefined && arg01!= null && arg01.length >0 ) ? arg01.split(",") : "";
	for(var j=0;j< dataArg01.length;j++){
		var temp = dataArg01[j];
		switch (temp) {
		case "in":
			var sourceData = convertToJson($("form").find("input[name='listInput']").val());
			if(sourceData != undefined && sourceData.length > 0) {
				for(var i = 0; i < sourceData.length; i++) {
					var objTmp = new Object();
					objTmp.optionValue = "0"+sourceData[i].decisionInputBeanId;
					objTmp.output01 = 0;
					objTmp.output02 = sourceData[i].parentDecisionInputBeanId;
					objTmp.output03 = sourceData[i].dataType;
					objTmp.output04 = false;
					objTmp.output05 = sourceData[i].decisionInputBeanId;
					objTmp.output06 = sourceData[i].decisionInputBeanCode;
					
					if(sourceData[i].parentDecisionInputBeanId != undefined && sourceData[i].parentDecisionInputBeanId != "") {
						objTmp.optionLabel = mKeyCode["0"+sourceData[i].parentDecisionInputBeanId]+"."+sourceData[i].decisionInputBeanCode;
					}else{
						objTmp.optionLabel = "in."+sourceData[i].decisionInputBeanCode;
					}
					mKeyCode[objTmp.optionValue] = objTmp.optionLabel;
					
					var index = objTmp.optionLabel.indexOf(searchKey);
					if(index >-1){
						dataResult.push(objTmp);
					}
				}
			}
			break;
		default:
			break;
		}
	}
	return dataResult;
}

