/**
 * 
 * @param table
 * @param inputName
 * @param itemType
 * @returns {String}
 */
function validateRequired(table, inputName, itemType) {

	var $Inputs = $(table).find(itemType+"[name$="+inputName+"]");
	var messages="";
	var flag = false;
	var countInputRadio = 0;
	var countRadioHidden = 0;
	
	for(var index = 0; index < $Inputs.length; index++){
		flag = false;
		
		if("#"+TABLE_NAME.LOGIC == table) {
			
			if($Inputs.eq(index).attr('type') == 'radio' && $Inputs.eq(index).is(':checked') == false &&  $Inputs.eq(index).closest('div.div-decision-row').find('[name="itemType"]').val() == 1) {
				// is the first error
				if (countInputRadio == 0) {
					// mark has error
					/*flagNotChecked = true;*/
					countInputRadio++;
					flag = false;
				} else {
					countInputRadio = 0;
					flag = true;
				}
			} else if ($Inputs.eq(index).attr('type') == 'radio' && $Inputs.eq(index).is(':checked') == true && $Inputs.eq(index).closest('div.div-decision-row').find('[name="itemType"]').val() == 1) {
				// radio had check is the first
				if (countInputRadio == 0) {
					index++;
					flag = false;
				} else {
					// radio had checked but is the second
					flag = false;
					/*flagNotChecked = false;*/
					countInputRadio = 0;
				}
			} else if($.trim($Inputs.eq(index).val())=='' && $Inputs.eq(index).closest('div.div-decision-row').find('[name="itemType"]').val() == 1) {
				flag = true;
			} else if($Inputs.eq(index).attr('type') == 'radio' && $Inputs.eq(index).closest('div.div-decision-row').find('[name="itemType"]').val() != 1) {
				if (countRadioHidden == 0) {
					countRadioHidden++;
				} else {
					var textLabel = $Inputs.eq(index).closest('div.div-decision-row').find('label[name="formulaDefinitionContent"]').text();
					if($.trim(textLabel) == "") {
						flag = true;
					}
					// reset
					countRadioHidden = 0;
				}
			} else if ($Inputs.eq(index).closest('div.div-decision-row').find('[name="itemType"]').val() != 1 && $Inputs.eq(index).attr('type') != 'radio') {
				var textLabel = $Inputs.eq(index).closest('div.div-decision-row').find('label[name="formulaDefinitionContent"]').text();
				if($.trim(textLabel) == "") {
					flag = true;
				}
				countRadioHidden = 0;
			}
			
		} else if ($.trim($Inputs.eq(index).val())=='') {
			flag = true;
		}
		
		if(flag) {
			
			var rowIndex =  $Inputs.eq(index).closest('tr').index();
			var columnIndex = $Inputs.eq(index).closest('td').index();
			var columnName = "";
			
			if($(table).find('tr:eq(1)').find('th') != undefined && $(table).find('tr:eq(1)').find('th').length > columnIndex) {
				// Count number of <td> in the case full
				var maxTdCount = $(table).find('tr:eq(1)').find('th').length;
				if($(table).attr('id') == TABLE_NAME.LOGIC){
					maxTdCount +=1;
				}
				
				// Count number of <td> it current to check position
				var currTdCount = $Inputs.eq(index).closest('tr').find('td').length;
				// In the case of row is checking equal row fix
				if(currTdCount == maxTdCount ){
					columnName = $(table).find('tr:eq(1)').find('th:eq('+columnIndex+')').text();
				} else {
					// It just happend with table logic desing in the case had rowspan
					var columnId = $Inputs.eq(index).closest('td').find('input:hidden[name="decisionItemDesignId"]').val();
					$(table).find('tr:eq(2) td').each(function(){
						if(columnId == $(this).find('input:hidden[name="decisionItemDesignId"]').val()) {
							columnName = $(table).find('tr:eq(1)').find('th:eq('+$(this).index()+')').text();
							return false;
						}
					});
				}
			} else {

				if ($(table).attr('id') == TABLE_NAME.INPUT || $(table).attr('id') == TABLE_NAME.OUTPUT) {
					columnIndex = columnIndex + 1;
				}
				columnName = $(table).find('tr:eq(0)').find('th:eq('+columnIndex+')').text();
			}
			
			messages += fcomMsgSource['err.sys.0077'].replace("{0}",columnName).replace("{1}",rowIndex+1) + "\r";
		}
	}
	
	return messages;
};

function validateSpecialChar(table , inputName) {
	var messages = "";
	var $Inputs = $(table).find("input[type='text'][name$="+inputName+"]");
	var flag = false;

	for(var index = 0; index < $Inputs.length; index++) {

		flag = false;
		if ($.trim($Inputs.eq(index).val())!='' && !($.qp.validateIsCode($Inputs.eq(index).val()))) {
			flag = true;
		}

		if(flag) {
			var rowIndex =  $Inputs.eq(index).closest('tr').index();
			var columnIndex = $Inputs.eq(index).closest('td').index();
			var columnName = "";

			if($(table).find('tr:eq(1)').find('th') != undefined && $(table).find('tr:eq(1)').find('th').length > columnIndex) {
				columnName = $(table).find('tr:eq(1)').find('th:eq('+columnIndex+')').text();
			} else {
				if ($(table).attr('id') == TABLE_NAME.INPUT || $(table).attr('id') == TABLE_NAME.OUTPUT) {
					columnIndex = columnIndex + 1;
				}
				columnName = $(table).find('tr:eq(0)').find('th:eq('+columnIndex+')').text();
			}

			messages += fcomMsgSource['err.sys.0094'].replace("{0}", columnName).replace("{1}", "\\@/:*?<>|white-space").replace("{2}", rowIndex+1) + "\r";
			messages = messages.replace('&quot;', '"').replace('&quot;', '"');
		}
	}

	return messages;
}

/**
 * 
 * @param table
 * @param inputName
 * @param itemType
 * @returns {String}
 */
function validateDuplicate(table, inputName, itemType) {
	
	var $Inputs = $(table).find(itemType+"[name$="+inputName+"]");
	var messages="";
	for(var i=$Inputs.length-1;i>0;i--) {
		
		var rowIndex =  $Inputs.eq(i).closest('tr').index();
		var columnIndex = $Inputs.eq(i).closest('td').index();
		var columnName = "";
		
		if($(table).find('tr:eq(1)').find('th') != undefined && $(table).find('tr:eq(1)').find('th').length > columnIndex) {
			columnName = $(table).find('tr:eq(1)').find('th:eq('+columnIndex+')').text();
		} else {
			if ($(table).attr('id') == TABLE_NAME.INPUT || $(table).attr('id') == TABLE_NAME.OUTPUT) {
				columnIndex = columnIndex + 1;
			}
			columnName = $(table).find('tr:eq(0)').find('th:eq('+columnIndex+')').text();
		}
		var rgroup = ($($Inputs.eq(i)).closest("tr").attr("data-ar-rgroup") == undefined || $($Inputs.eq(i)).closest("tr").attr("data-ar-rgroup") == null ) ? "" : $($Inputs.eq(i)).closest("tr").attr("data-ar-rgroup");
		
		for(var j=0;j<i;j++) {
			var rgroupCheck = ($Inputs.eq(j).closest("tr").attr("data-ar-rgroup") == undefined || $Inputs.eq(j).closest("tr").attr("data-ar-rgroup") == null ) ? "" : $Inputs.eq(j).closest("tr").attr("data-ar-rgroup");
			
			if($.trim($Inputs.eq(i).val()) != undefined && $.trim($Inputs.eq(i).val()) != "" 
					&& $.trim($Inputs.eq(j).val()) != undefined && $.trim($Inputs.eq(j).val()) != ""
					&& $.trim($Inputs.eq(i).val()) == $.trim($Inputs.eq(j).val())
					&& rgroup.trim() == rgroupCheck.trim()) {
				
				messages +=fcomMsgSource['err.sys.0041'].replace("{0}",columnName).replace("{1}",i+1) + "\r";
				break;
			}
		}
	}
	
	return messages;
}

/**
 * 
 * @param table
 * @returns {String}
 */
function validationInputBean(table) {
	var messages="";
	messages += validateRequired(table,"decisionInputBeanName",ITEM_TYPE.INPUT);
	messages += validateRequired(table,"decisionInputBeanCode", ITEM_TYPE.INPUT);
	messages += validateRequired(table,"dataType", ITEM_TYPE.SELECT);
	messages += validateSpecialChar(table,"decisionInputBeanCode");
//	messages += validateDuplicate(table,"decisionInputBeanName", ITEM_TYPE.INPUT);
	messages += validateDuplicate(table,"decisionInputBeanCode", ITEM_TYPE.INPUT);
	return messages;
}

/**
 * 
 * @param table
 * @returns {String}
 */
function validationOutputBean(table) {
	var messages="";
	messages += validateRequired(table,"decisionOutputBeanName", ITEM_TYPE.INPUT);
	messages += validateRequired(table,"decisionOutputBeanCode", ITEM_TYPE.INPUT);
	messages += validateRequired(table,"dataType", ITEM_TYPE.SELECT);
	messages += validateSpecialChar(table,"decisionOutputBeanCode");
//	messages += validateDuplicate(table,"decisionOutputBeanName", ITEM_TYPE.INPUT);
	messages += validateDuplicate(table,"decisionOutputBeanCode",ITEM_TYPE.INPUT);
	return messages;
}

/**
 * 
 * @param table
 * @returns {String}
 */
function validationItemDesign(table){
	var messages="";
	
	messages += validateRequired(table,"itemName", ITEM_TYPE.INPUT);
	
	if(table == "#"+TABLE_NAME.CONDITION) {
		messages += validateRequired(table,"itemValue", ITEM_TYPE.INPUT);
		messages += validateDuplicate(table,"itemName", ITEM_TYPE.INPUT);
		messages += validateDuplicate(table,"itemValue", ITEM_TYPE.INPUT);
	} else if (table == "#"+TABLE_NAME.ACTION) {
		messages += validateRequired(table,"itemValue", ITEM_TYPE.INPUT);
		messages += validateDuplicate(table,"itemName", ITEM_TYPE.INPUT);
		messages += validateDuplicate(table,"itemValue", ITEM_TYPE.INPUT);
	}

	
	return messages;
}

function validateAtLeastConditionCombine(table) {
	var message="";
	$(table).find('tbody>tr').each(function(idx) {
		var flgErr = true;
		$.each($(this).find('td[class!="btnRemove"] .div-decision-row'), function() {
			if($(this).find('label').length > 0 && $(this).find('select').length > 0) {
				flgErr = false;
				return false;
			}
		});
		
		if(flgErr) {
			message += dbMsgSource['sc.decisiontable.0053'].replace("{0}",idx+1) + "\r";
		}
	});
	
	return message;
}

/**
 * 
 * @param table
 * @returns {String}
 */
function validationLogicDesign(table) {
	var messages="";
	
	messages += validateRequired(table,"itemValue", ITEM_TYPE.INPUT);
	messages += validateAtLeastConditionCombine(table);
	
	return messages;
}

/**
 * 
 */
function relationCheckItemDesign() {

	
	var $InputConds = $("#"+TABLE_NAME.CONDITION).find(">tbody>tr>td").find('input:hidden[name$="itemValue"]');
	var $InputActs = $("#"+TABLE_NAME.ACTION).find(">tbody>tr>td").find('input:hidden[name$="itemValue"]');
	var classCss = "removeRow";
	
	for(var i = 0;  i < $InputConds.length; i++) {
		var flag = isExistInDataSrc($InputConds.eq(i));
		if (flag == 0) {
			// mark all color
			$InputConds.eq(i).closest('table').find('tr').addClass(classCss);
			break;
		} else if (flag == 1) {
			// mark color one row
			$InputConds.eq(i).closest('tr').addClass(classCss);
		}
		
	}
	
	for(var j = 0;  j < $InputActs.length; j++) {
		var flag = isExistInDataSrc($InputActs.eq(j));
		if (flag == 0) {
			// mark all color
			$InputActs.eq(j).closest('table').find('tr').addClass(classCss);
			break;
		} else if (flag == 1) {
			// mark color one row
			$InputActs.eq(j).closest('tr').addClass(classCss);
		}
	}
}

/**
 * 
 * @param tab
 * @returns {Boolean}
 */
function checkTabItemDesignChange(tab) {
	
	var $InputConds = $("#"+TABLE_NAME.CONDITION).find(">tbody>tr");
	var flag = false;
	
	for(var i = 0;  i < $InputConds.length; i++) {
		if($InputConds.eq(i).hasClass('removeRow')) {
			$.qp.showAlertModal(dbMsgSource['sc.decisiontable.0031']);
			$(tab).tab('show');
			return false;
		} 
	}
	
	var $InputActs = $("#"+TABLE_NAME.ACTION).find(">tbody>tr");
	for(var j = 0;  j < $InputActs.length; j++) {
		if($InputActs.eq(j).hasClass('removeRow')) {
			$.qp.showAlertModal(dbMsgSource['sc.decisiontable.0031']);
			$(tab).tab('show');
			return false;
		} 
	}
	
	return true;
}

/**
 * 
 * @param item
 * @returns {Number}
 */
function isExistInDataSrc(item){
	
	var $obj = $(item);
	var flag = 1;
	
	var sourceData;
	
	if ($obj.closest('table').attr('id') == TABLE_NAME.CONDITION) {
		sourceData = $.qp.decisiontable.convertToJson($("form").find("input[name='listInput']").val());
		
		for(var i = 0; i < sourceData.length; i++) {
			if($.trim($obj.val()) == '' || $obj.val() == sourceData[i].decisionInputBeanId) {
				flag = -1;
				break;
			}
		}
	} else if ($obj.closest('table').attr('id') == TABLE_NAME.ACTION) {
		sourceData = $.qp.decisiontable.convertToJson($("form").find("input[name='listOutput']").val());
		
		for(var i = 0; i < sourceData.length; i++) {
			if($.trim($obj.val()) == '' || $obj.val() == sourceData[i].decisionOutputBeanId) {
				flag = -1;
				break;
			}
		}
	}
	
	if (sourceData == undefined || sourceData.lenght == 0) {
		return 0;
	}
	
	return flag;
}

/*
 * change normal style  
 */
changeStyleNormalOfDecisionTable = function(table) {
	$(table).find("tbody").find("tr").each(function (){
		$(this).removeClass("qp-bdesign-tr-warning");
	});
}