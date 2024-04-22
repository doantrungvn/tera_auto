/**
 *  Display all tab on view screen
 * 
 * @param arrData
 */
function displayView(arrTmp) {
     var listConds = arrTmp[2];
     var listActs = arrTmp[3];
     displayTabItemDesignBean(listConds, listActs);
     var listCondGroups = arrTmp[4]; 
     var listCondItems = arrTmp[5];
	 displayTabLogicDesign(listConds, listActs, listCondGroups, listCondItems);
}

/**
 * 
 * @param listInputs
 */
function displayTabInputBean(listInputs) {
	var table = $('div'+TAB_NAME.INPUT).find('table#'+TABLE_NAME.INPUT);
	var index = 0;
	
	// Process row for input && output bean tab
	var arrElm = [];
	var markObj = {
			row : "",
			index : 0,
			indexrow : 0,
			subindex : 0 ,
			count : 0,
			countall : 0,
			size : 0  // size of array
		};
	
	// Get all parent of list type tree
	var arrParent  = getParents(listInputs, BEAN_TYPE.INPUT);
	// Get all list divide by parent id into family 
	var arrParentAndChild = getParentAndChild(listInputs, arrParent, BEAN_TYPE.INPUT);
	
	for(var i = 0; i < arrParentAndChild.length; i++){
		arrElm = arrParentAndChild[i];
		markObj.size = arrElm.length - 1;
		
		for(var j = 0; j < arrElm.length; j++) {
			markObj.count = j;
			markObj = returnRowIn(arrElm[j], markObj);
			$(table).find('tbody').append(markObj.row);
			// Increate index row
			markObj.indexrow++;
			markObj.countall++;
		}
	}
	
	if ($(table).find('>tbody>tr').length > 0 ){
		$('div'+TAB_NAME.INPUT).children().css('display','block');
	}
}

/**
 * 
 * @param obj
 * @param markObj
 * @returns {___anonymous2276_2282}
 */
function returnRowIn(obj, markObj) {
	
	var parentDecisionInputBeanId = "";
	if(obj.parentDecisionInputBeanId != undefined) {
		parentDecisionInputBeanId = obj.parentDecisionInputBeanId;
	}
	
	var decisionInputBeanCode = "";
	if(obj.decisionInputBeanCode != undefined) {
		decisionInputBeanCode = obj.decisionInputBeanCode;
	}
	
	var decisionInputBeanName = "";
	if(obj.decisionInputBeanName != undefined){
		decisionInputBeanName = obj.decisionInputBeanName;
	}
	
	var dataType = "";
	if(obj.dataType != undefined){
		dataType = obj.dataType;
	}
	
	if(parentDecisionInputBeanId == "") {
		markObj.index = markObj.index+1;
		// reset subindex in the case of parent
		markObj.subindex = 0;
		markObj.row = $('<tr>')
			.append('<td>'+markObj.index+'</td>')
			.append('<td colspan=\"2\">'+decisionInputBeanName+'</td>')
			.append('<td>'+decisionInputBeanCode+'</td>')
			.append('<td>'+CL_QP_DATATYPE[dataType]+'</td>');
		
	} else {
		// Increase subindex in the case of is childrent 
		markObj.subindex = markObj.subindex+1;
		markObj.row = $('<tr>')
			.append('<td></td><td>'+markObj.index+"."+markObj.subindex+'</td>')
			.append('<td>'+decisionInputBeanName+'</td>')
			.append('<td>'+decisionInputBeanCode+'</td>')
			.append('<td>'+CL_QP_DATATYPE[dataType]+'</td>');
	}

	return markObj;
}

/**
 * 
 */
function displayTabOutputBean(listOutputs) {
	var table = $('div'+TAB_NAME.OUTPUT).find('table#'+TABLE_NAME.OUTPUT);
	var index = 0;
	
	// Process row for input && output bean tab
	var arrElm = [];
	var markObj = {
			row : "",
			index : 0,
			indexrow : 0,
			subindex : 0 ,
			count : 0,
			countall : 0,
			size : 0  // size of array
		};
	
	// Get all parent of list type tree
	var arrParent  = getParents(listOutputs, BEAN_TYPE.OUTPUT);
	// Get all list divide by parent id into family 
	var arrParentAndChild = getParentAndChild(listOutputs, arrParent, BEAN_TYPE.OUTPUT);
	
	for(var i = 0; i < arrParentAndChild.length; i++){
		arrElm = arrParentAndChild[i];
		markObj.size = arrElm.length - 1;
		
		for(var j = 0; j < arrElm.length; j++) {
			markObj.count = j;
			markObj = returnRowOut(arrElm[j], markObj);
			$(table).find('tbody').append(markObj.row);
			// Increate index row
			markObj.indexrow++;
			markObj.countall++;
		}
	}
	
	if ($(table).find('>tbody>tr').length > 0 ){
		$('div'+TAB_NAME.OUTPUT).children().css('display','block');
	}
}

/**
 * 
 * @param obj
 * @param markObj
 * @returns {___anonymous4832_4838}
 */
function returnRowOut(obj, markObj) {
	
	var parentDecisionOutputBeanId = "";
	if(obj.parentDecisionOutputBeanId != undefined){
		parentDecisionOutputBeanId = obj.parentDecisionOutputBeanId;
	}
	
	var decisionOutputBeanCode = "";
	if(obj.decisionOutputBeanCode != undefined){
		decisionOutputBeanCode = obj.decisionOutputBeanCode;
	}
	
	var decisionOutputBeanName = "";
	if(obj.decisionOutputBeanName != undefined){
		decisionOutputBeanName = obj.decisionOutputBeanName;
	}
	
	var dataType = "";
	if(obj.dataType != undefined){
		dataType = obj.dataType;
	}	
	
	if(parentDecisionOutputBeanId == "") {
		markObj.index = markObj.index+1;
		// reset subindex in the case of parent
		markObj.subindex = 0;
		markObj.row = $('<tr>')
			.append('<td>'+markObj.index+'</td>')
			.append('<td colspan=\"2\">'+decisionOutputBeanName+'</td>')
			.append('<td>'+decisionOutputBeanCode+'</td>')
			.append('<td>'+CL_QP_DATATYPE[dataType]+'</td>');
		
	} else {
		// Increase subindex in the case of is childrent 
		markObj.subindex = markObj.subindex+1;
		markObj.row = $('<tr>')
			.append('<td></td><td>'+markObj.index+"."+markObj.subindex+'</td>')
			.append('<td>'+decisionOutputBeanName+'</td>')
			.append('<td>'+decisionOutputBeanCode+'</td>')
			.append('<td>'+CL_QP_DATATYPE[dataType]+'</td>');
	}
	
	return markObj;
}

/**
 * 
 */
function displayTabItemDesignBean(listConds, listActs) {
	var tableCond = $('div'+TAB_NAME.ITEMDESIGN).find('table#'+TABLE_NAME.CONDITION);
	var tableAct = $('div'+TAB_NAME.ITEMDESIGN).find('table#'+TABLE_NAME.ACTION);
	var appendRow = "";
	var index = 0;
	
	for(var i = 0; i < (listConds.length + listActs.length) ; i++) {
		if(i < listConds.length) {
			
			appendRow = returnRowItem(i, listConds[i]);
			$(tableCond).find('tbody').append(appendRow);
			
		} else {
			appendRow = returnRowItem(index, listActs[index]);
			$(tableAct).find('tbody').append(appendRow);
			index++;
		}
	}
	
	if ($(tableCond).find('>tbody>tr').length > 0 
			&& $(tableAct).find('>tbody>tr').length > 0 ){
		$('div'+TAB_NAME.ITEMDESIGN).children().css('display','block');
	}
	
}

/**
 * 
 * @param index
 * @param obj
 * @returns
 */
function returnRowItem(index, obj){
	
	var objectName = "";
	if(obj.objectName != undefined){
		objectName = obj.objectName;
	}
	
	var itemName = "";
	if(obj.itemName != undefined){
		itemName = obj.itemName;
	}
	
	var objectCodeCombine = "";
	if(obj.objectCodeCombine != undefined){
		objectCodeCombine = obj.objectCodeCombine;
	}
	
	var dataType = "";
	if(obj.dataType != undefined){
		dataType = obj.dataType;
	}
	
	
	var row = $("<tr>");
	$(row).append("<td>"+(index+1)+"</td>")
		  .append("<td>"+itemName+"</td>")
		  .append("<td>"+objectCodeCombine+"</td>")
		  .append("<td>"+objectName+"</td>")
		  .append("<td>"+CL_QP_DATATYPE[dataType]+"</td>");
	
	return row;
}

/**
 * 
 * @param listConds
 * @param listActs
 * @param listCondGroups
 * @param listCondItems
 */
function displayTabLogicDesign(listConds, listActs, listCondGroups, listCondItems) {
	var table = $('div'+TAB_NAME.LOGICDESIGN).find('table#'+TABLE_NAME.LOGIC);
	var colgroup = $('<colgroup>');
	var thead = $('<thead>');
	var tbody = $('<tbody>');
	if(listConds != undefined && listConds.length > 0
			&& listActs != undefined && listActs.length > 0 
			&& listCondGroups != undefined && listActs != undefined ) {
		// create html colgroup
		var htmlColgroup = buildColgroup(listConds.length, listActs.length);
		$(colgroup).append(htmlColgroup);
		
		// create html thead
		var htmlThead = buildThead(listConds, listActs);
		$(thead).append(htmlThead);
		
		// create html tbody
		var htmlTBody = buildTbody(listConds, listActs, listCondGroups, listCondItems);
		$(tbody).append(htmlTBody);
		
		// Append all element of table
		$(table).append(colgroup).append(thead).append(tbody);
		
		if ($(table).find('>tbody>tr').length > 0 ){
			$('div'+TAB_NAME.LOGICDESIGN).children().css('display','block');
		}
	}
	
    $.qp.decisiontable.resizeHeighLogicTab();
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
function buildThead(dataConds, dataActs) {
	var attrCond = "style=\"text-align: center;\"";
	var attrAct = "style=\"background-color: #FAF5EE;text-align: center;\"";
	var headerCond = dbMsgSource['sc.decisiontable.0023'];
	var headerAct = dbMsgSource['sc.decisiontable.0024'];
	
	var trHeader = "<tr>"
		+ "<th colspan=\""+dataConds.length+"\">"+headerCond+"</th>"
		+ "<th colspan=\""+dataActs.length+"\" style=\"background-color: #FAF5EE; text-align: center;\">"+headerAct+"</th></tr>";
		
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

function buildTbody(dataConds, dataActs, dataCondGroup, dataCondItem) {

	var html = "";
	if(dataCondGroup.length > 0) {
		html += buildAllGroupCondition(dataConds, dataActs, dataCondGroup, dataCondItem);
	}
	
	return html;
}

/**
 * 
 * @param dataConds
 * @param dataActs
 * @param dataCondGroup
 * @param dataCondItem
 * @returns {String}
 */
function buildAllGroupCondition(dataConds, dataActs, dataCondGroup, dataCondItem) {
	
	// Number of row for build all <tr> tags
	var maxRowNum = getMaxRowNum(dataCondGroup);
	var allTr = "";

	// Build row
	for(var rowIndex = 0; rowIndex <= maxRowNum; rowIndex++) {
		// Build one <tr>
		var tr = "<tr>";
		tr += buildAllTdOneRow(rowIndex, dataConds, dataActs, dataCondGroup, dataCondItem);
		tr += "</tr>";
		// Append all <tr> tags
		allTr += tr;
	}
	
	return allTr;
}

/**
 * 
 */
function buildAllTdOneRow(rowIndex, dataConds, dataActs, dataCondGroup, dataCondItem) {
	
	var allTdOneRow = "";
	var count = 0;
	
	for(var i = 0; i < (dataConds.length + dataActs.length); i++) {
		var tdOneRow = "";
		if(i < dataConds.length) {
			tdOneRow = getTdContent(rowIndex, i, dataConds[i], dataCondGroup, dataCondItem);
		} else {
			tdOneRow = getTdContent(rowIndex, i, dataActs[count], dataCondGroup, dataCondItem);
			count++;
		}
		
		allTdOneRow += tdOneRow;
	}
	
	if(allTdOneRow == ""){
		return "";
	}
	
	return allTdOneRow;
}

/**
 * 
 * @param rowIndex
 * @param tdIndex
 * @param itemDataDesign
 * @param dataCondGroup
 * @param dataCondItem
 * @returns {String}
 */
function getTdContent(rowIndex, tdIndex, itemDataDesign, dataCondGroup, dataCondItem) {
	var tdContent;
	var isRowSpan = false;
	
	for(var i = 0; i < dataCondGroup.length; i++) {
		if(itemDataDesign.decisionItemDesignId == dataCondGroup[i].decisionItemDesignId 
				&& rowIndex == dataCondGroup[i].rowNumber) {
			tdContent = getTdContentDetail(itemDataDesign, dataCondGroup[i], dataCondItem);
			return tdContent;
		} else {
			isRowSpan = true;
		}
	}
	
	if(isRowSpan) {
		return "";
	}
}

/**
 * 
 * @param itemDataDesign
 * @param itemdataCondGroup
 * @param dataCondItem
 * @returns {String}
 */
function getTdContentDetail(itemDataDesign, itemdataCondGroup, dataCondItem) {
	var tdDetails = "";
	var textJoin = "";
	var count = 0;
	var valueDisplay = "";
	
	for(var i = 0; i < dataCondItem.length; i++) {
		
		// In the case of item design is type condition
		if(itemDataDesign.itemType == 1 
				&& itemdataCondGroup.conditionGroupId == dataCondItem[i].conditionGroupId) {
			
			// Is item single
			if(itemdataCondGroup.groupType == 1 ) {
				
				// In the case value get from fomula setting
				if(dataCondItem[i].itemType == 0) {
					textJoin =  CL_QP_OPERATORTYPE[dataCondItem[i].opertatorType]+"&nbsp;"+dataCondItem[i].formulaDefinitionContent;
				} else {
					// Check datatype for processing radio button
					if(itemDataDesign.dataType == 8) {
						textJoin = CL_SUPPORT_OPTION_VALUE_FLAG[dataCondItem[i].itemValue];
					} else {
						textJoin =  CL_QP_OPERATORTYPE[dataCondItem[i].opertatorType]+"&nbsp;"+dataCondItem[i].itemValue;
					}
				}
				
				break;
			} else {
				// Note : in the case of no have item type radio
				var divAnd = "&nbsp;AND&nbsp;";
				count++;
				
				if(count > 1) {
					// Div have [AND] conditon
					textJoin+= divAnd;
				}
				
				var valueDisplay = dataCondItem[i].itemType == 0 ? dataCondItem[i].formulaDefinitionContent : dataCondItem[i].itemValue; 
				
				textJoin+= CL_QP_OPERATORTYPE[dataCondItem[i].opertatorType]+"&nbsp;"+valueDisplay;
			}
		// In the case of item design type action
		} else if (itemDataDesign.itemType == 0 
				&& itemdataCondGroup.conditionGroupId == dataCondItem[i].conditionGroupId) {
			// Set class for process menu context
			if(itemdataCondGroup.groupType == 1 ) {
				var valueDisplay = dataCondItem[i].itemType == 0 ? dataCondItem[i].formulaDefinitionContent : ((itemDataDesign.dataType == 8)?CL_SUPPORT_OPTION_VALUE_FLAG[dataCondItem[i].itemValue]:dataCondItem[i].itemValue);
				textJoin = valueDisplay;
				break;
			}
		}
	}
	
	// Create td success
	tdDetails += "<td style=\"vertical-align: middle; text-align : center;\" rowspan=\""+itemdataCondGroup.rowSpan+"\" >"+textJoin+"</td>";

	return tdDetails;
}

/**
 * 
 * @param items
 * @param arrParent
 * @param beanType
 * @returns {Array}
 */
function getParentAndChild(items, arrParent, beanType) {

	var arrAllElm = [];
	var arrElm = [];
	
	if(beanType == 0) {
		
		for(var i = 0; i < arrParent.length; i++){
			var markItem = arrParent[i].decisionInputBeanId;
			arrElm.push(arrParent[i]);
			for(var j = 0; j < items.length; j++){
				if(markItem == items[j].parentDecisionInputBeanId){
					arrElm.push(items[j]);
				}
			}
			arrAllElm.push(arrElm);
			arrElm = [];
		}
		
	} else {
		for(var i = 0; i < arrParent.length; i++){
			var markItem = arrParent[i].decisionOutputBeanId;
			arrElm.push(arrParent[i]);
			for(var j = 0; j < items.length; j++){
				if(markItem == items[j].parentDecisionOutputBeanId){
					arrElm.push(items[j]);
				}
			}
			arrAllElm.push(arrElm);
			arrElm = [];
		}
	}
	
	return arrAllElm;
}

/**
 * 
 * @param items
 * @param beanType
 * @returns {Array}
 */
function getParents(items, beanType) {
	var arrParent = [];
	if(beanType == 0){
		for(var i = 0; i < items.length; i++){
			if(items[i].parentDecisionInputBeanId == null){
				arrParent.push(items[i]);
			}
		}
	} else {
		for(var i = 0; i < items.length; i++){
			if(items[i].parentDecisionOutputBeanId == null){
				arrParent.push(items[i]);
			}
		}
	}
	
	return arrParent;
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