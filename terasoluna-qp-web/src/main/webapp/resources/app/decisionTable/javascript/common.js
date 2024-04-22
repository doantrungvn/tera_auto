$(function() {
	
	/* sort */
	$("#"+TABLE_NAME.CONDITION+",#"+TABLE_NAME.ACTION).find("tbody").sortable({
		// placeholder: "ui-sortable-placeholder"
		helper: function(e, ui) {
			ui.children().each(function() {
				$(this).width($(this).width());
			});
			return ui;
		},
		update: function(event, ui) {
			
			$.qp.reCalIndex($('#'+TABLE_NAME.CONDITION));
			$.qp.reArrayIndex($('#'+TABLE_NAME.CONDITION));
			$.qp.reCalIndex($('#'+TABLE_NAME.ACTION));
			$.qp.reArrayIndex($('#'+TABLE_NAME.ACTION));
		},
		items: 'tr',
		cursor: 'move',
		handle: '.sortable'
	});

    // Initial display tabs logic design
    saveData();
    
    // Mark column to check when remove all column in item design tab
	var dataConds = $.qp.decisiontable.convertToJson($('form')
			.find("input[name$='listItemCondition']").val());
	var dataActs = $.qp.decisiontable.convertToJson($('form')
			.find("input[name$='listItemAction']").val());
    COLUMN_MARK = getColumnItemDesign(dataConds, dataActs);
    
    // Set min heigth of logic tab
    $.qp.decisiontable.resizeHeighLogicTab();
    
    $('#tabsDecision a:last').tab('show');
    
    // Process when tab active
    var flagReDrawIDesign  = true;
    var flagReDrawLogic = true;

    $('a[href="#tabsDecision-2"]').on('hide.bs.tab', function(e) {
    	 // Validation when change tab
		  var message = validationInputBean("#"+TABLE_NAME.INPUT);
		  if(message !=  undefined && message.length > 0) {
			  $('#tabsDecision a:eq(0)').tab('show');
			  $.qp.showAlertModal(message);
			  //$.qp.alert(message);
			  return false;
		  } else {
			  saveInputBean();
			  relationCheckItemDesign();
		  }
    });

    $('a[href="#tabsDecision-3"]').on('hide.bs.tab', function(e) {
    	// Validation when change tab
		var message = validationOutputBean("#"+TABLE_NAME.OUTPUT);
		if(message !=  undefined && message.length > 0) {
			$('#tabsDecision a:eq(1)').tab('show');
			$.qp.showAlertModal(message);
			return false;
		} else {
  		saveOutputBean();
  		relationCheckItemDesign();
		}
   });
   
    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
    	  var target = $(e.target).attr("href") // activated tab
    	  if (target == TAB_NAME.ITEMDESIGN && flagReDrawIDesign) {
    		  dataConds = $.qp.decisiontable.convertToJson($('form')
    				  .find("input[name$='listItemCondition']").val());
    		  dataActs = $.qp.decisiontable.convertToJson($('form')
    				  .find("input[name$='listItemAction']").val());
    		  // Repaint table
    		  drawTblCondition(dataConds, TABLE_NAME.CONDITION);
    		  drawTblAction(dataActs, TABLE_NAME.ACTION);
    		  
    		  relationCheckItemDesign();
    		  // initializing autocomplete
    		  initAutocmpl();
    		  // Get column id before edit
    		  COLUMN_MARK = getColumnItemDesign(dataConds, dataActs);
    		  flagReDrawIDesign = false;
    		  
    		  // Disable sortable in the case old column
    		  var dataCondGroup = $.qp.decisiontable.convertToJson($('form')
    				.find("input[name$='listConditionGroup']").val());
    		  
    		  // Set flag for draw item logic desing
    		  flagReDrawLogic = true;
    	  } else if (target == TAB_NAME.ITEMDESIGN && !flagReDrawIDesign) {
    		  // Set flag for draw item logic desing
    		  flagReDrawLogic = true;
    	  } else if (target != TAB_NAME.ITEMDESIGN) {
    		   
    		  // Validation when change tab
    		  var messageCond = validationItemDesign("#"+TABLE_NAME.CONDITION);
    		  var messageAct = validationItemDesign("#"+TABLE_NAME.ACTION);
    		  if(messageCond.length > 0 && messageAct.length > 0) {
    			  var msgCond = dbMsgSource['sc.decisiontable.0018'] + dbMsgSource['sc.sys.0037'] + "\r\n" + messageCond; 

    			  var msgAct  = dbMsgSource['sc.decisiontable.0019'] + dbMsgSource['sc.sys.0037'] + "\r\n" + messageAct;
    			  			  
    			  msgCond += "\r\n" + msgAct;
    			  $('#tabsDecision a:eq(2)').tab('show');
    			  $.qp.showAlertModal(msgCond);
    			  //$.qp.alert(msgCond);
    			  return false;
    		  } else if(messageCond.length > 0) {
    			  $('#tabsDecision a:eq(2)').tab('show');
    			  var msgCond = dbMsgSource['sc.decisiontable.0018'] + dbMsgSource['sc.sys.0037'] +"\r\n" + messageCond;
    			  $.qp.showAlertModal(msgCond);
    			  //$.qp.alert(msgCond);
    			  return false;
    		  } else if(messageAct.length > 0){
    			  var msgAct  = dbMsgSource['sc.decisiontable.0019'] + dbMsgSource['sc.sys.0037'] +"\r\n" + messageAct;
    			  $('#tabsDecision a:eq(2)').tab('show');
    			  $.qp.showAlertModal(msgAct);
    			  // $.qp.alert(msgAct);
    			  return false;
    		  }
    		  
    		  // check had remove row change
    		  var result = checkTabItemDesignChange('#tabsDecision a:eq(2)');
    		  if(!result){
    			  return false;
    		  }
    	  }
    	  
    	  if (target != TAB_NAME.LOGICDESIGN && target == TAB_NAME.ITEMDESIGN) {
    		  // Validation when change tab
    		  saveLogicDesign();
    	  }
    	  
    	  if(target == TAB_NAME.LOGICDESIGN && flagReDrawLogic) {
    		  
    		  $('#'+TABLE_NAME.CONDITION+',#'+TABLE_NAME.ACTION)
    		      .find(">tbody>tr>td[class='sortable']>a").css('display','none');
    		  $('#'+TABLE_NAME.CONDITION+',#'+TABLE_NAME.ACTION)
		      	  .find(">tbody>tr>td[class='sortable']").removeClass('sortable');
    		  
    		   saveItemDesignBean();
    		   flagReDrawIDesign = true;
    		   
    		   $("div"+TAB_NAME.LOGICDESIGN).children().remove();
    		   $.qp.decisiontable.resizeHeighLogicTab();
    		   displayLogicDesign(reloadDataLogic());
    		   flagReDrawLogic = false;
    	  }
    });
    
    // Reload autocomplete client
   /* initAutocmpl();*/
    
    // Display logic design tab
    displayLogicDesign(reloadDataLogic());

});


/**
 * 
 * @param item
 */
function setBeanCode(item){
	var tblName = $(item).closest('table').attr('id');
	var prevVal = $(item).attr('current');
	var currVal = $(item).val();
	var tblDesign;
	
	if(tblName == TABLE_NAME.INPUT){
		tblDesign = TABLE_NAME.CONDITION;
	} else if(tblName == TABLE_NAME.OUTPUT) {
		tblDesign = TABLE_NAME.ACTION;
	}
	
	var datatype = $(item).closest('td').next().find('select').val();
	if(datatype == DATATYPE.OBJECT){
		$('#'+tblDesign).find('>tbody>tr').each(function(){
			var displayValue = $(this).find('td:eq(2) input[name$=".itemValueAutocomplete"]').val().split('.');
			if(displayValue != undefined && displayValue.length > 1 && displayValue[0] == prevVal){
				$(this).find('td:eq(2) input[name$=".itemValueAutocomplete"]').val(currVal+"."+displayValue[1]);
			}
		});
	} else if (datatype in CL_QP_DATATYPE) {
		$('#'+tblDesign).find('>tbody>tr').each(function(){
			if($.trim(prevVal) == $.trim($(this).find('input:hidden[name*="objectCode"]').val())) {
				var displayValue = $(this).find('td:eq(2)').find('input[name$=".itemValueAutocomplete"]').val().split('.');
				if(displayValue != undefined && displayValue.length > 1 && displayValue[1] == prevVal ){
					$(this).find('td:eq(2) input[name$=".itemValueAutocomplete"]').val(displayValue[0]+"."+currVal);
				} else {
					$(this).find('td:eq(2) input[name$=".itemValueAutocomplete"]').val(currVal);
				}
				
				$(this).find('input:hidden[name*="objectCode"]').val(currVal);
				
				return false;
			}
		});
	}
}

/**
 * 
 * @param item
 */
function setBeanName(item) {
	
	var tblName = $(item).closest('table').attr('id');
	var prevVal = $(item).attr('current');
	var currVal = $(item).val();
	var $tr = $(item).closest('tr');
	var inoutBeanId;
	
	var tblDesign;
	if(tblName == TABLE_NAME.INPUT){
		tblDesign = TABLE_NAME.CONDITION;
		inoutBeanId = $tr.find('input:hidden[name$=".decisionInputBeanId"]').val();
	} else if(tblName == TABLE_NAME.OUTPUT) {
		tblDesign = TABLE_NAME.ACTION;
		inoutBeanId = $tr.find('input:hidden[name$=".decisionOutputBeanId"]').val();
	}
	
	$trInOutBean = $('#'+tblDesign).find('>tbody>tr input:hidden[name$=".itemValue"]').closest('tr');
	$.each($trInOutBean, function(){
		if($(this).find('input:hidden[name$=".itemValue"]').val() == inoutBeanId){
			$(this).find('td:eq(3)').text(currVal);
			return false;
		}
	});
}
/**
 * 
 * @returns {Boolean}
 */
function validateSubmit() {
	// check input
	saveInputBean();
	relationCheckItemDesign();
	var message = validationInputBean("#"+TABLE_NAME.INPUT);
	  if(message !=  undefined && message.length > 0) {
		  $('#tabsDecision a:eq(0)').tab('show');
		  $.qp.showAlertModal(message);
		  //$.qp.alert(message);
		  return false;
	  }
	  
	 // Check output
	  saveOutputBean();
	  relationCheckItemDesign();
	  message = validationOutputBean("#"+TABLE_NAME.OUTPUT);
	  if(message !=  undefined && message.length > 0) {
		  $('#tabsDecision a:eq(1)').tab('show');
		  $.qp.showAlertModal(message);
		  //$.qp.alert(message);
		  return false;
	  }
	  
	  // Check item design
	  saveItemDesignBean();
	  
	  if($("#"+TABLE_NAME.CONDITION+" >tbody>tr").length == 0 
			  &&  $("#"+TABLE_NAME.ACTION+" >tbody>tr").length == 0) {
		  var msgCond = dbMsgSource['sc.decisiontable.0048'].replace("{0}",dbMsgSource['sc.decisiontable.0018']);
		  var msgAct  = dbMsgSource['sc.decisiontable.0048'].replace("{0}",dbMsgSource['sc.decisiontable.0019']);
		  $('#tabsDecision a:eq(2)').tab('show');
		  $.qp.showAlertModal(msgCond + "\r\n" + msgAct);
		  //$.qp.alert(msgCond + "\r\n" + msgAct);
		  return false;
	  } else if ($("#"+TABLE_NAME.CONDITION+" >tbody>tr").length == 0) {
		  var msgCond = dbMsgSource['sc.decisiontable.0048'].replace("{0}",dbMsgSource['sc.decisiontable.0018']);
		  $('#tabsDecision a:eq(2)').tab('show');
		  $.qp.showAlertModal(msgCond);
		  //$.qp.alert(msgCond);
		  return false;
	  } else if ($("#"+TABLE_NAME.ACTION+" >tbody>tr").length == 0) {
		  var msgAct  = dbMsgSource['sc.decisiontable.0048'].replace("{0}",dbMsgSource['sc.decisiontable.0019']);
		  $('#tabsDecision a:eq(2)').tab('show');
		  $.qp.showAlertModal(msgAct);
		  //$.qp.alert(msgAct);
		  return false;
	  }
	  
	  var messageCond = validationItemDesign("#"+TABLE_NAME.CONDITION);
	  var messageAct = validationItemDesign("#"+TABLE_NAME.ACTION);
	  if(messageCond.length > 0 && messageAct.length > 0) {
		  var msgCond = dbMsgSource['sc.decisiontable.0018'] + dbMsgSource['sc.sys.0037'] + "\r\n" + messageCond;
		  var msgAct  = dbMsgSource['sc.decisiontable.0019'] + dbMsgSource['sc.sys.0037'] + "\r\n" + messageAct;
		  
		  msgCond += "\r\n" + msgAct;
		  $('#tabsDecision a:eq(2)').tab('show');
		  $.qp.showAlertModal(msgCond);
		  //$.qp.alert(msgCond);
		  return false;
	  } else if(messageCond.length > 0) {
		  var msgCond = dbMsgSource['sc.decisiontable.0018'] + dbMsgSource['sc.sys.0037'] + "\r\n" + messageCond;
		  $('#tabsDecision a:eq(2)').tab('show');
		  $.qp.showAlertModal(msgCond);
		  //$.qp.alert(msgCond);
		  return false;
	  } else if(messageAct.length > 0){
		  var msgAct  = dbMsgSource['sc.decisiontable.0019'] + dbMsgSource['sc.sys.0037'] + "\r\n" + messageAct;
		  $('#tabsDecision a:eq(2)').tab('show');
		  $.qp.showAlertModal(msgAct)
		  //$.qp.alert(msgAct);
		  return false;
	  }
	  
	  var result = checkTabItemDesignChange('#tabsDecision a:eq(2)');
	  if(!result){
		  return false;
	  }
	  
	  // Create column default for new column not draw by not change tab
	  $('div'+TAB_NAME.ITEMDESIGN).find('table>tbody>tr>td[class="sortable"]>a').each(function() {
		  if($.trim($(this).css('display')) == "block") {
			  $('div'+TAB_NAME.LOGICDESIGN).children().remove();
			  displayLogicDesign(reloadDataLogic());
			  return false;
		  } 
	  });
	  
	  // Validation when change tab
	  var message = validationLogicDesign("#"+TABLE_NAME.LOGIC);
	  if(message !=  undefined && message.length > 0) {

		  $('#tabsDecision a:eq(3)').tab('show');
		  $.qp.showAlertModal(message);
		  //$.qp.alert(message);
		  return false;
	  }
	  saveLogicDesign();
	  
	  return true;
}

function createColumnDefaultForNewColumn() {
	var $aList = $(TAB_NAME.ITEMDESIGN).find("table>tbody>tr>td[class='sortable']>a");
	$aList.each(function(){
		if($(this).css('display') == 'none') {
			displayLogicDesign(reloadDataLogic());
			return false;
		}
	});
}

/**
 * 
 * @param dataConds
 * @param tblName
 */
function drawTblCondition(dataConds, tblName) {
	if(dataConds != undefined && dataConds.length > 0) {
		$("#"+tblName).find('>tbody>tr').remove();
		var htmlOutput = returnBeanHTML(dataConds, BEAN_TYPE.CONDITION);
        $('#'+tblName+' tbody').prepend(htmlOutput);
	}
}

/**
 * 
 * @param dataActs
 * @param tblName
 */
function drawTblAction(dataActs, tblName) {
	if(dataActs != undefined && dataActs.length > 0) {
		$("#"+tblName).find('>tbody>tr').remove();
		var htmlOutput = returnBeanHTML(dataActs, BEAN_TYPE.ACTION);
        $('#'+tblName+' tbody').prepend(htmlOutput);
	}
}

/**
 * Save data
 */
function saveData() {
    saveInputBean();
    saveOutputBean();
    saveItemDesignBean();
    saveLogicDesign();
}

/**
 * 
 * @param dataConds
 * @param dataActs
 * @returns {___anonymous4362_4363}
 */
function getColumnItemDesign(dataConds, dataActs) {
	
	var columnMarkTmp = {};
	var count = 0;
	
	if(dataConds.length > 0 && dataActs.length > 0){
		for(var i = 0; i < (dataConds.length + dataActs.length); i++ ){
			if(i < dataConds.length){
				columnMarkTmp[dataConds[i].decisionItemDesignId] = i;
			} else {
				columnMarkTmp[dataActs[count].decisionItemDesignId] = i;
				count++;
			}
		}
	}
	
	return columnMarkTmp;
}

/**
 * 
 * @returns {Array}
 */
function reloadDataLogic() {
	var arrTmp = [];
	var dataConds = $.qp.decisiontable.convertToJson($('form')
			.find("input[name$='listItemCondition']").val());
	var dataActs = $.qp.decisiontable.convertToJson($('form')
			.find("input[name$='listItemAction']").val());
	var dataCondGroup = $.qp.decisiontable.convertToJson($('form')
			.find("input[name$='listConditionGroup']").val());
	var dataCondItem = $.qp.decisiontable.convertToJson($('form')
			.find("input[name$='listConditionItem']").val());
	
	arrTmp[0] = dataConds;
	arrTmp[1] = dataActs;
	arrTmp[2] = dataCondGroup;
	arrTmp[3] = dataCondItem;
	
	return arrTmp;
}

var mapKeyTemp = [];
mapKeyTemp["listInput"] = 1;
mapKeyTemp["listOutput"] = 1;

/**
 * Save input bean of tab Input
 * 
 */
function saveInputBean() {
	var table = "#"+TABLE_NAME.INPUT;
	var arrInputBean = new Array();
	var arrInputAutocomplete = new Array();
	var mapKey = {};
	var decisionInputBeanCode;
	
	$(table).find(">tbody>tr.ar-dataRow").each(function(i) {
		var rgroup = $(this).attr("data-ar-rgroupindex");
		var inputBean = new Object();
		inputBean.parentDecisionInputBeanId = $(this).find("input[name*='parentDecisionInputBeanId']").val();
		inputBean.decisionInputBeanCode = $(this).find("input[name*='decisionInputBeanCode']").val();
		inputBean.decisionInputBeanName = $(this).find("input[name$='.decisionInputBeanName']").val();
		inputBean.itemSequenceNo = i;
		inputBean.decisionTbId = $(this).find("input[name*='decisionTbId']").val();
		inputBean.objectType = $(this).find("input[name$='.objectType']").val();
		inputBean.objectId = $(this).find("input[name$='.objectId']").val();
		inputBean.objectFlg = $(this).find("input[name$='.objectFlg']").val();
		var dataType = $(this).find("select[name*='dataType']").val();
		if (dataType == undefined) {
			dataType = $(this).find("input[name*='dataType']").val()
		}
		inputBean.dataType = parseInt(dataType);
		var id = $(this).find("input[name*='decisionInputBeanId']").val();
		
		if(id=="") {
			id = mapKeyTemp["listInput"];
			mapKeyTemp["listInput"] = parseInt(id)+1;
			id = "in"+id;
		}
		
		inputBean.decisionInputBeanId = id;
		mapKey[rgroup]=id;
		$(this).find("input[name*='decisionInputBeanId']").val(id);
		
		var arrayIndex = rgroup.split(".");
		if(arrayIndex.length >1) {
			var keyParent = "";
			for(var j =0;j <arrayIndex.length-1;j++){
				if(j==0){
					keyParent = arrayIndex[j];
				}else{
					keyParent += "."+arrayIndex[j];
				}
			}
			
			inputBean.parentDecisionInputBeanId = mapKey[keyParent];
			$(this).find("input[name*='parentDecisionInputBeanId']").val(inputBean.parentDecisionInputBeanId);
			
			if(decisionInputBeanCode != "" && (inputBean.dataType == 16 || inputBean.dataType == 17)) {
				inputBean.itemAutocomplete = decisionInputBeanCode+"."+inputBean.decisionInputBeanCode;
			}
		}else {
			inputBean.parentDecisionInputBeanId = "";
			// assign item autocomplete
			inputBean.itemAutocomplete = inputBean.decisionInputBeanCode;
			decisionInputBeanCode = "";
			// If is object
			if(inputBean.dataType == 0 || inputBean.dataType == 16 || inputBean.dataType == 17) {
				inputBean.itemAutocomplete = "";
				// Mark item autocomplete
				decisionInputBeanCode = inputBean.decisionInputBeanCode;
			}
		}
		inputBean.groupId = ($(this).attr("data-ar-rgroup") == undefined || $(this).attr("data-ar-rgroup") == null) ? "": $(this).attr("data-ar-rgroup");
		inputBean.tableIndex = rgroup;
		arrInputBean.push(inputBean);
	});
	
	if(arrInputBean.length > 0){
		var value =  $.qp.decisiontable.convertToString(arrInputBean);	
		$(table).closest("form").find("input[name='listInput']").val(value);
	} else {
		$(table).closest("form").find("input[name='listInput']").val('');
	}
	
	return;
}

/**
 * Save output bean
 */
function saveOutputBean() {
	var table = "#"+TABLE_NAME.OUTPUT;
	var arrOutputBean = new Array();
	var mapKey = {};
	var decisionOutputBeanCode;
	
	$(table).find(">tbody>tr.ar-dataRow").each(function(i) {
		var rgroup = $(this).attr("data-ar-rgroupindex");
		var outputBean = new Object();
		outputBean.parentDecisionOutputBeanId = $(this).find("input[name*='parentDecisionOutputBeanId']").val();
		outputBean.decisionOutputBeanCode = $(this).find("input[name*='decisionOutputBeanCode']").val();		
		outputBean.decisionOutputBeanName = $(this).find("input[name$='.decisionOutputBeanName']").val();
		outputBean.itemSequenceNo = i;
		outputBean.decisionTbId = $(this).find("input[name*='decisionTbId']").val();
		outputBean.objectType = $(this).find("input[name$='.objectType']").val();
		outputBean.objectId = $(this).find("input[name$='.objectId']").val();
		outputBean.objectFlg = $(this).find("input[name$='.objectFlg']").val();
		var dataType = $(this).find("select[name*='dataType']").val();
		if (dataType == undefined) {
			dataType = $(this).find("input[name*='dataType']").val()
		}
		outputBean.dataType= parseInt(dataType);
		var id = $(this).find("input[name*='decisionOutputBeanId']").val();
		
		if(id=="") {
			id = mapKeyTemp["listOutput"];
			mapKeyTemp["listOutput"] = parseInt(id)+1;
			id = "out"+id;
		}
		
		outputBean.decisionOutputBeanId = id;
		mapKey[rgroup]=id;
		$(this).find("input[name*='decisionOutputBeanId']").val(id);
		
		var arrayIndex = rgroup.split(".");
		if(arrayIndex.length >1) {
			var keyParent = "";
			for(var j =0;j <arrayIndex.length-1;j++){
				if(j==0){
					keyParent = arrayIndex[j];
				}else{
					keyParent += "."+arrayIndex[j];
				}
			}
			
			outputBean.parentDecisionOutputBeanId = mapKey[keyParent];
			$(this).find("input[name*='parentDecisionInputBeanId']").val(outputBean.parentDecisionOutputBeanId);
			
			if(decisionOutputBeanCode != "") {
				outputBean.itemAutocomplete = decisionOutputBeanCode+"."+outputBean.decisionOutputBeanCode;
			}

		}else {
			outputBean.parentDecisionOutputBeanId = "";
			// assign item autocomplete
			outputBean.itemAutocomplete = outputBean.decisionOutputBeanCode;
			decisionOutputBeanCode = "";
			// If is object
			if(outputBean.dataType == 0 || outputBean.dataType == 16 || outputBean.dataType == 17) {
				outputBean.itemAutocomplete = "";
				// Mark item autocomplete
				decisionOutputBeanCode = outputBean.decisionOutputBeanCode;
			}
		}
		arrOutputBean.push(outputBean);
	});
	
	if(arrOutputBean.length > 0){
		var value =  $.qp.decisiontable.convertToString(arrOutputBean);
		$(table).closest("form").find("input[name='listOutput']").val(value);
	} else {
		$(table).closest("form").find("input[name='listOutput']").val('');
	}
	
	return;
}

var autoIncreaIDCond = 0;
var autoIncreaIDAct = 0;

/**
 * Save item design
 */
function saveItemDesignBean() {
	
	// Storing data of Condition
	var table = "#"+TABLE_NAME.CONDITION;
	var arrConditionBean = new Array();
	
	$(table).find(">tbody>tr").each(function(i) {
		var conditionBean = new Object();
		conditionBean.itemType = 1;
		conditionBean.itemName = $(this).find("input[name*='itemName']").val();
		conditionBean.itemValue = $(this).find("input:hidden[name$='.itemValue']").val();
		conditionBean.itemSequenceNo = parseInt($(this).find("td.tableIndex").text());
		conditionBean.decisionTbId = $(this).find("input[name*='decisionTbId']").val();
		conditionBean.objectName = $(this).find("input[name*='objectName']").val();
		conditionBean.objectCode = $(this).find("input[name$='objectCode']").val();
		conditionBean.objectCodeCombine = $(this).find("input[name*='itemValueAutocomplete']").val();
		conditionBean.dataType = parseInt($(this).find("input[name*='dataType']").val());
		conditionBean.decisionItemDesignId = $(this).find("input[name*='decisionItemDesignId']").val();
		
		if(conditionBean.decisionItemDesignId == "") {
			conditionBean.decisionItemDesignId = "cond" + autoIncreaIDCond;
			$(this).find("input[name*='decisionItemDesignId']").val(conditionBean.decisionItemDesignId);
			autoIncreaIDCond++;
		}

		count = i;
		arrConditionBean.push(conditionBean);
	});
	
	var value;
	if(arrConditionBean.length > 0) {
		value =  $.qp.decisiontable.convertToString(arrConditionBean);
		$(table).closest("form").find("input[name='listItemCondition']").val(value);
	} else {
		$(table).closest("form").find("input[name='listItemCondition']").val('');
	}
		
	// Storing data of Action
	table = "#"+TABLE_NAME.ACTION;
	var arrActionBean = new Array();
	$(table).find(">tbody>tr").each(function(i) {
		var actionBean = new Object();
		actionBean.itemType = 0;
		actionBean.itemName = $(this).find("input[name*='itemName']").val();
		actionBean.itemValue = $(this).find("input:hidden[name$='.itemValue']").val();
		actionBean.itemSequenceNo = parseInt($(this).find("td.tableIndex").text());
		actionBean.decisionTbId = $(this).find("input[name*='decisionTbId']").val();		
		actionBean.objectName = $(this).find("input[name*='objectName']").val();
		actionBean.objectCode = $(this).find("input[name$='objectCode']").val();
		actionBean.objectCodeCombine = $(this).find("input[name*='itemValueAutocomplete']").val();
		
		actionBean.dataType = parseInt($(this).find("input[name*='dataType']").val());
		actionBean.decisionItemDesignId = $(this).find("input[name*='decisionItemDesignId']").val();
		
		if(actionBean.decisionItemDesignId == "") {
			actionBean.decisionItemDesignId = "act" + autoIncreaIDAct;
			$(this).find("input[name*='decisionItemDesignId']").val(actionBean.decisionItemDesignId);
			autoIncreaIDAct++;
		}
		
		arrActionBean.push(actionBean);
	});
	
	if(arrActionBean.length > 0){
		value =  $.qp.decisiontable.convertToString(arrActionBean);
		$(table).closest("form").find("input[name='listItemAction']").val(value);
	} else {
		$(table).closest("form").find("input[name='listItemAction']").val('');
	}
	
	return;
}

var autoIncreaLD = 0;

/**
 * Save logic design
 */
function saveLogicDesign() {
		
	// Save table decison
	var table = "#"+TABLE_NAME.LOGIC;
	
	var arrGroupCond = new Array();
	var arrGroupItem = new Array();
	
	$.qp.undoFormatNumericForm($(table));

	$(table).find(">tbody>tr").each(function(i) {
		var rowNum = i;
		// find all group condition in row
		$(this).find("td[class!='btnRemove']").each(function(j) {

			if($(this).html() != '') {
				// Create new object storing data
				var groupConditionBean = new Object();
				groupConditionBean.decisionItemDesignId = $(this).find("input[name*='decisionItemDesignId']").val();
				groupConditionBean.rowSpan = parseInt($(this).attr('rowspan'));
				groupConditionBean.groupType = 1;
				if($(this).find("div.div-decision-row").length > 1 ) {
					groupConditionBean.groupType = 2;
				}

				groupConditionBean.rowNumber = rowNum;
				
				if($(this).find("input[name*='conditionGroupId']").val().length > 0){
					groupConditionBean.conditionGroupId = $(this).find("input[name*='conditionGroupId']").val();
				} else {
					groupConditionBean.conditionGroupId = autoIncreaLD+"_"+j+"condGr";
					autoIncreaLD++;
				}

				arrGroupCond.push(groupConditionBean);
				
				// find all condition item in condition group
				$(this).find("div.div-decision-row").each(function(k) {
					
					if($(this).find('select').length > 0 || $(this).find('input').length > 0) {
						// Create new object storing data
						var groupItemBean = new Object();
						groupItemBean.conditionItemId = "";
						if($(this).find("input[name*='conditionItemId']").val().length > 0){
							groupItemBean.conditionItemId = $(this).find("input[name*='conditionItemId']").val();
						}

						groupItemBean.conditionGroupId = groupConditionBean.conditionGroupId;
						groupItemBean.itemSequenceNo = k+1;
						groupItemBean.itemType = parseInt($(this).find("input[name*='itemType']").val());
						// In the case of not defined
						groupItemBean.opertatorType = 1;
						
						// If data type using input text
						if(groupItemBean.itemType == 1) {
							// Input type radio
							var dataType = $(this).closest('td').find('input[name="dataType"]').val();
							if (dataType == 8) {
								groupItemBean.itemValue = $(this).find("input[name$='itemValue']:checked").val();
							} else {
								groupItemBean.itemValue = $(this).find("input[name$='itemValue']").val();
							}
						} else {
							// If using formular
							groupItemBean.formulaDefinitionContent = $(this).find('[name="formulaDefinitionContent"]').text();
							groupItemBean.formulaDefinitionDetails = $.qp.decisiontable.convertToJson($(this).find('[name="formulaDefinitionDetails"]').val());
						}

						if(parseInt($(this).find("select[name*='opertatorType']").val()) > 1 && $(this).find("select[name*='opertatorType']").is(':visible')){
							groupItemBean.opertatorType = parseInt($(this).find("select[name*='opertatorType']").val());
						}
						
						arrGroupItem.push(groupItemBean);
					}
				});
			} else {
				j--;
			}
		});
	});

	if(arrGroupCond.length > 0) {
		// Backup data Condition group
		var value =  $.qp.decisiontable.convertToString(arrGroupCond);
		$(table).closest("form").find("input[name='listConditionGroup']").val(value);
		
		if(arrGroupItem.length > 0){
			// Backup data Condition item
			value =  $.qp.decisiontable.convertToString(arrGroupItem);
			$(table).closest("form").find("input[name='listConditionItem']").val(value);
		} else {
			$(table).closest("form").find("input[name='listConditionItem']").val('');
		}
	} else {
		$(table).closest("form").find("input[name='listConditionGroup']").val('');
	}

	return;
}

/**
 * Initializing
 */
function initAutocmpl() {
	$('form').find('.qp-input-autocomplete').each(function() {
        $.qp.initialAutocomplete($(this));
    });
}

/**
 * 
 * @returns {Array}
 */
function getDataSourceCondition(event) {
	var sourceData = $.qp.decisiontable.convertToJson($("form").find("input[name='listInput']").val());
	var dataResult = [];
	var mKeyCode = new Array();
	var searchKey = event.searchKey;
	
	if(sourceData != undefined && sourceData.length > 0) {
		for(var i = 0; i < sourceData.length; i++) {
			var objTmp = new Object();
			// Input bean name for display
			objTmp.optionValue = sourceData[i].decisionInputBeanId;
			// Input bean value for hidden
			objTmp.optionLabel = sourceData[i].itemAutocomplete;
			// Object name
			objTmp.output01 = sourceData[i].decisionInputBeanName;
			// Object code
			objTmp.output02 = sourceData[i].decisionInputBeanCode;
			// Data type
			objTmp.output03 = sourceData[i].dataType;
		
			if(sourceData[i].parentDecisionInputBeanId != undefined && sourceData[i].parentDecisionInputBeanId != ""){
				objTmp.optionLabel = mKeyCode[sourceData[i].parentDecisionInputBeanId]+"."+sourceData[i].decisionInputBeanCode; 
			}else{
				objTmp.optionLabel = sourceData[i].decisionInputBeanCode;
			}
			mKeyCode[objTmp.optionValue] = objTmp.optionLabel;
			
			var index = objTmp.optionLabel.indexOf(searchKey);
			// is exist substring
			if(index > -1){
				if (sourceData[i].dataType != 0 && sourceData[i].dataType != 16 && sourceData[i].dataType != 17) {
					dataResult.push(objTmp);
				}
			}
		}
	}
	
	return dataResult;
}

/**
 * 
 * @returns {Array}
 */
function getDataSourceAction(event) {
	var sourceData = $.qp.decisiontable.convertToJson($("form").find("input[name='listOutput']").val());
	var dataResult = [];
	var mKeyCode = new Array();
	var searchKey = event.searchKey;
	
	if(sourceData != undefined && sourceData.length > 0){
		for(var i = 0; i < sourceData.length; i++){
			var objTmp = new Object();
			// Input bean name for display
			objTmp.optionValue = sourceData[i].decisionOutputBeanId;
			// Input bean value for hidden
			objTmp.optionLabel = sourceData[i].itemAutocomplete;
			// Object name
			objTmp.output01 = sourceData[i].decisionOutputBeanName;
			// Object code
			objTmp.output02 = sourceData[i].decisionOutputBeanCode;
			// Data type
			objTmp.output03 = sourceData[i].dataType;
			
			if(sourceData[i].parentDecisionOutputBeanId != undefined && sourceData[i].parentDecisionOutputBeanId != ""){
				objTmp.optionLabel = mKeyCode[sourceData[i].parentDecisionOutputBeanId]+"."+sourceData[i].decisionOutputBeanCode; 
			}else{
				objTmp.optionLabel = sourceData[i].decisionOutputBeanCode;
			}
			mKeyCode[objTmp.optionValue] = objTmp.optionLabel;
			
			var index = objTmp.optionLabel.indexOf(searchKey);
			// is exist substring
			if (sourceData[i].dataType != 0 && sourceData[i].dataType != 16 && sourceData[i].dataType != 17) {
				dataResult.push(objTmp);
			}
		}
	}
	
	return dataResult;
}

/**
 * 
 * @param event
 */
function bdOnChangeModule(event){
	if(event.item != undefined){
		var moduleId = event.item.optionValue;
		var tableInput = "#"+TABLE_NAME.INPUT;
		var tableOutput = "#"+TABLE_NAME.OUTPUT;
		
		$(tableInput).find("input[name*='messageStringAutocomplete']").each(function(){
			$(this).attr("arg01",moduleId);
		});
		$(tableOutput).find("input[name*='messageStringAutocomplete']").each(function(){
			$(this).attr("arg01",moduleId);
		});
	}
}

/**
 * 
 * @param obj
 */
function checkRemoveRow(obj) {
	var tableName = $(obj).closest('table').attr('id');
	var isConfirm;
	var columnID = $(obj).closest('tr').find('input:hidden[name$=".decisionItemDesignId"]').val();
	
	var $tds = $('#'+TABLE_NAME.LOGIC).find('>tbody>tr>td[class!="btnRemove"]');
	
	if($tds != undefined && $tds.length > 0 && columnID != undefined && columnID.length > 0) {
		
		if(!$(obj).closest('tr').hasClass('removeRow')) {
			$tds.each(function() {
				if(columnID == $(this).find('input:hidden[name="decisionItemDesignId"]').val()){
					isConfirm = $.qp.confirm(dbMsgSource['sc.decisiontable.0030']);
					return false;
				}
			});
			
			if(isConfirm) {
				$.qp.removeRowJS(tableName, obj);
			// Catch in the case not exist
			} else if(isConfirm == undefined) {
				$.qp.removeRowJS(tableName, obj);
			}
		} else {
			$.qp.removeRowJS(tableName, obj);
		}
		
		if(isConfirm || isConfirm == undefined) {
			$tds.each(function() {
				if(columnID == $(this).find('input:hidden[name="decisionItemDesignId"]').val()){
					$(this).html('');
				}
			});
		}
	} else {
		$.qp.removeRowJS(tableName, obj);
	}
}

/**
 * 
 * @param columnId
 * @param datatype
 * @param itemType
 */
function removeItemLogicByColumnId(columnId, datatype, itemType) {
	var obj = new Object();
	obj.itemType = itemType;
	obj.dataType = datatype;
	
	$("#"+TABLE_NAME.LOGIC).find('>tbody>tr>td[class!="btnRemove"]').each(function(rowIdx){
		
		if($(this).find('input:hidden[name*="decisionItemDesignId"]').val() == columnId && datatype != DATATYPE.OBJECT) {
			$(this).find('input:hidden[name*="dataType"]').val(datatype);
			$(this).find('div').remove();
			var divJoin = getDivDefaul(obj);
			$.qp.initialAutoNumeric($(divJoin));
			$.qp.initialAllPicker($(divJoin));
			$(this).prepend(divJoin);
		} else if($(this).find('input:hidden[name*="decisionItemDesignId"]').val() == columnId && datatype == DATATYPE.OBJECT) {
			$(this).html('');
		}
	});
	// Save logic design after edit
	saveLogicDesign();
}

/**
 * 
 * @param obj
 */
function displayDataRow(obj) {

	var $scope = $(obj.target).closest('td');
	var beanNameIO = $(obj.target).val();
	
	if(beanNameIO == ''){
		
		var isConfirm;
		var flgexist = false;
		var valtmp;
		
		if($scope.parent().find("input:hidden[name$='decisionItemDesignId']").val() != undefined 
				&& $scope.parent().find("input:hidden[name$='decisionItemDesignId']").val().length > 0) {
			valtmp = $scope.parent().find("input:hidden[name$='decisionItemDesignId']").val();
			$('#'+TABLE_NAME.LOGIC).find('>tbody>tr>td[class!="btnRemove"]').each(function(){
				if(valtmp == $(this).find('input[name="decisionItemDesignId"]').val()) {
					flgexist = true;
					return false;
				}
			});
		}
		
		if(flgexist && !$scope.closest('tr').hasClass('removeRow')) {
			isConfirm = $.qp.confirm(dbMsgSource['sc.decisiontable.0044']);
			
			if(isConfirm){
				$('#'+TABLE_NAME.LOGIC).find('>tbody>tr>td[class!="btnRemove"]').each(function() {
					if(valtmp == $(this).find('input[name="decisionItemDesignId"]').val()) {
						$(this).html('');
					}
				});
			} else {
				
				// store value hidden
				$(obj.target).val($(obj.target).attr('previouslabel'));
				// store value for display
				$(obj.target).next().val($(obj.target).attr('previousvalue'));
				
				$(obj.target).nextAll('span').attr('onclick','$.qp.removeAutocompleteData(this)');
				$(obj.target).nextAll('span').children().attr('class','glyphicon glyphicon-remove');
				return false;
			}
		}
		
		//Set for diplay
		$scope.next().text('')
				.next().text('');

		// Set hidden field
		$scope.parent().find("input:hidden[name$='objectCode']").val("");
		$scope.parent().find("input:hidden[name$='objectName']").val("");
		$scope.parent().find("input:hidden[name$='dataType']").val("");
		$scope.parent().find("input:hidden[name$='decisionItemDesignId']").val("");
		$scope.closest('tr').find('td:eq(5)').addClass('sortable');
		$scope.closest('tr').find('td:eq(5)>a').show();
	} else {
		
		//Remove for diplay
		$scope.next().text(obj.item.output01)
				.next().text(CL_QP_DATATYPE[obj.item.output03]);

		// Set object name
		if($.trim($scope.prev().find('input[name$="itemName"]').val()) == '') {
			$scope.prev().find('input[name$="itemName"]').val(obj.item.output01);
		} 

		//Remove row mark
		if($scope.closest('tr').hasClass('removeRow')){
			$scope.closest('tr').removeClass('removeRow');
		}

		// Set hidden field
		$scope.parent().find("input:hidden[name$='objectCode']").val(obj.item.output02);
		$scope.parent().find("input:hidden[name$='objectName']").val(obj.item.output01);
		$scope.parent().find("input:hidden[name$='dataType']").val(obj.item.output03);
		$scope.closest('tr').find('td:eq(5)').addClass('sortable');
		$scope.closest('tr').find('td:eq(5)>a').show();
	}
}

/**
 * Makking value for process onchange
 */
function markValue(obj) {
	
	$(obj).attr('current', obj.value);
	
}

/**
 * 
 * @param item
 * @returns {Boolean}
 */
function changeDataType(item) {
	
	var prevValue = $(item).attr('current');
	var currValue = $(item).val();
	var valId;
	
	// Do something with the previous value after the change
	var $obj = $(item).closest('tr');
	var tbName = $obj.closest('table').attr('id');
	var isConfirm = false;
	var itemType;
	var tbItemDesignName;
	var hiddenName;
	
	if(tbName!= undefined && tbName == TABLE_NAME.INPUT){
		tbItemDesignName = TABLE_NAME.CONDITION;
		itemType = 1; // item for condition
		valId = $obj.find('input:hidden[name$="decisionInputBeanId"]').val();
		hiddenName = 'decisionInputBeanId';
	} else if (tbName!= undefined && tbName == TABLE_NAME.OUTPUT) {
		tbItemDesignName = TABLE_NAME.ACTION;
		itemType = 0; // item for action
		valId = $obj.find('input:hidden[name$="decisionOutputBeanId"]').val();
		hiddenName = 'decisionOutputBeanId';
	}
	
	
	// in the case preview value is default
	if(prevValue != undefined && prevValue.length == 0){
		$.qp.decisiontable.objectTypeChange(item);
		return;
	}
	
	// In the case had old row
	if (valId != undefined && valId.length > 0 ) {
		// Get value item tab Design for check
		var $inputDesign = $("#"+tbItemDesignName).find(">tbody>tr");
		// Get value item tab Logic for check
		var $inputsLogic = $("#"+TABLE_NAME.LOGIC).find(">tbody>tr");
		// In the case of exist data at item design
		if($inputDesign.length > 0 && $inputsLogic.length > 0) {
			
			// In the case of change from single to single field another
			if((parseInt(prevValue) != DATATYPE.OBJECT 
					&& parseInt(prevValue) != DATATYPE.COMMON_OBJECT
					&& parseInt(prevValue) != DATATYPE.EXTERNAL_OBJECT)
					&& (parseInt(currValue) != DATATYPE.OBJECT
							&& parseInt(currValue) != DATATYPE.COMMON_OBJECT
							&& parseInt(currValue) != DATATYPE.EXTERNAL_OBJECT)) {
				
				for(var i = 0; i < $inputDesign.length; i++) {
					// in the case of exist
	    			if (valId == $inputDesign.eq(i).find("input:hidden[name$='.itemValue']").val()) {
	    				
	    				var columnId = $inputDesign.eq(i).find('input:hidden[name*="decisionItemDesignId"]').val();
	    				isConfirm = false;
	    				if(checkExistInLogicDesign(columnId)) {
	    					isConfirm = $.qp.confirm(dbMsgSource['sc.decisiontable.0035']);
	    					
	    					if(isConfirm) {
			    				// Remove condition item logic design
			    				removeItemLogicByColumnId(columnId, currValue, itemType);
			    				// Change item design
		    					$inputDesign.eq(i).find("input:hidden[name$='.dataType']").val(currValue);
			    				$inputDesign.eq(i).find('td:eq(4)').text(CL_QP_DATATYPE[currValue]);
			    				// Check if table condition dont'n have at least one row
			    				$inputDesign = $("#"+tbItemDesignName).find(">tbody>tr");
			    				if($inputDesign == undefined || $inputDesign.length == 0) {
			    					// remove all setting at logic tab
			    					$('div'+TAB_NAME.LOGICDESIGN).children().remove();
			    				}
			    				// Change datatype success
			    				$.qp.decisiontable.objectTypeChange(item);
			    				
			    				return;
		    				} else {
		    					$(item).val(prevValue);
		    					return;
		    				}
	    					
	    				} else {
	    					// Change item design
	    					$inputDesign.eq(i).find("input:hidden[name$='.dataType']").val(currValue);
		    				$inputDesign.eq(i).find('td:eq(4)').text(CL_QP_DATATYPE[currValue]);

		    				// Check if table condition dont'n have at least one row
		    				$inputDesign = $("#"+tbItemDesignName).find(">tbody>tr");
		    				if($inputDesign == undefined || $inputDesign.length == 0) {
		    					// remove all setting at logic tab
		    					$('div'+TAB_NAME.LOGICDESIGN).children().remove();
		    				}
		    				// Change datatype success
		    				$.qp.decisiontable.objectTypeChange(item);
		    				
		    				return;
	    				}
	    			}
	    		}
			// In the case of change from single to object
			} else if ((parseInt(prevValue) != DATATYPE.OBJECT 
						&& parseInt(prevValue) != DATATYPE.COMMON_OBJECT
						&& parseInt(prevValue) != DATATYPE.EXTERNAL_OBJECT) 
						&& (parseInt(currValue) == DATATYPE.OBJECT
								|| parseInt(currValue) == DATATYPE.COMMON_OBJECT
								|| parseInt(currValue) == DATATYPE.EXTERNAL_OBJECT)) {
				for(var i = 0; i < $inputDesign.length; i++) {
					// Check exist
	    			if (valId == $inputDesign.eq(i).find("input:hidden[name$='.itemValue']").val()) {
	    				var columnId = $inputDesign.eq(i).find('input:hidden[name*="decisionItemDesignId"]').val();
	    				
	    				if(checkExistInLogicDesign(columnId)) {
	    					isConfirm = $.qp.confirm(dbMsgSource['sc.decisiontable.0035']);
	    					
	    					if(isConfirm) {
	    	    				// remove condition item logic design
	    	    				removeItemLogicByColumnId(columnId, currValue, itemType);
	    	    				// Remove row not using
	    	    				$inputDesign.eq(i).remove();
	    	    				// Check if table condition dont'n have at least one row
			    				$inputDesign = $("#"+tbItemDesignName).find(">tbody>tr");
			    				if($inputDesign == undefined || $inputDesign.length == 0) {
			    					// remove all setting at logic tab
			    					$('div'+TAB_NAME.LOGICDESIGN).children().remove();
			    				}
			    				// Change datatype success
			    				$.qp.decisiontable.objectTypeChange(item);
			    				
			    				return;
	    					}  else {
		    					$(item).val(prevValue);
		    					return;
		    				}
	    				} else {
	    					// Remove row not using
    	    				$inputDesign.eq(i).remove();
    	    				// Check if table condition dont'n have at least one row
		    				$inputDesign = $("#"+tbItemDesignName).find(">tbody>tr");
		    				if($inputDesign == undefined || $inputDesign.length == 0) {
		    					// remove all setting at logic tab
		    					$('div'+TAB_NAME.LOGICDESIGN).children().remove();
		    				}
		    				// Change datatype success
		    				$.qp.decisiontable.objectTypeChange(item);
		    				return;
	    				}
	    				break;
	    			}
	    		}
			// In the case of change from Object to single field
			} else if(((parseInt(prevValue) == DATATYPE.OBJECT
						|| parseInt(prevValue) == DATATYPE.COMMON_OBJECT
						|| parseInt(prevValue) == DATATYPE.EXTERNAL_OBJECT) 
						&& (parseInt(currValue) != DATATYPE.OBJECT
								&& parseInt(currValue) != DATATYPE.COMMON_OBJECT
								&& parseInt(currValue) != DATATYPE.EXTERNAL_OBJECT))
								|| ((parseInt(prevValue) == DATATYPE.OBJECT
										|| parseInt(prevValue) == DATATYPE.COMMON_OBJECT
										|| parseInt(prevValue) == DATATYPE.EXTERNAL_OBJECT)
										&& (parseInt(currValue) == DATATYPE.OBJECT
												|| parseInt(currValue) == DATATYPE.COMMON_OBJECT
												|| parseInt(currValue) == DATATYPE.EXTERNAL_OBJECT)
												&& parseInt(prevValue) != parseInt(currValue))) {
				// Search all children
				var flagExist = false;
				$obj.nextAll().each(function(i) {
					// Get data item design it current time for check
					$inputDesign = $("#"+tbItemDesignName).find(">tbody>tr");
					// Check exist
					if($(this).find('td:first').attr('colspan') != undefined && $(this).find('td:first').attr('colspan') != '5') {
						// Get value of IN-OUT bean id
						var tmpval = $(this).find('input:hidden[name$='+hiddenName+']').val();
						// Check exist in item design
						for(var i = 0; i < $inputDesign.length; i++) {
							// Check exist
			    			if (tmpval == $inputDesign.eq(i).find("input:hidden[name$='.itemValue']").val()){
			    				var columnId = $inputDesign.eq(i).find('input:hidden[name*="decisionItemDesignId"]').val();
			    				// Check exist in Logic design
			    				if(checkExistInLogicDesign(columnId)) {
			    					
			    					flagExist = true;
				    				break;
			    				}
			    			}
			    		}
					} else if($(this).find('td:first').attr('colspan') != undefined && $(this).find('td:first').attr('colspan') == '5') {
						// eixst loop
						return false;
					}
				});
				
				// If exist in Logic design
				if (flagExist) {
					isConfirm = $.qp.confirm(dbMsgSource['sc.decisiontable.0035']);
					if (isConfirm) {
						//Search and remove had exist
						$obj.nextAll().each(function(i) {
							// Get data item design it current time for check
							$inputDesign = $("#"+tbItemDesignName).find(">tbody>tr");
							// Check exist
							if($(this).find('td:first').attr('colspan') != undefined && $(this).find('td:first').attr('colspan') != '5') {
								// Get value of IN-OUT bean id
								var tmpval = $(this).find('input:hidden[name$='+hiddenName+']').val();
								// Check exist in item design
								for(var i = 0; i < $inputDesign.length; i++) {
									// Check exist
					    			if (tmpval == $inputDesign.eq(i).find("input:hidden[name$='.itemValue']").val()){
					    				var columnId = $inputDesign.eq(i).find('input:hidden[name*="decisionItemDesignId"]').val();
					    				// remove condition item logic design
					    				removeItemLogicByColumnId(columnId, DATATYPE.OBJECT, itemType);
					    				$inputDesign.eq(i).remove();
					    				
					    				break;
					    			}
					    		}
							}
						});
						
						// Check if table condition dont'n have at least one row
						$inputDesign = $("#"+tbItemDesignName).find(">tbody>tr");
						if($inputDesign == undefined || $inputDesign.length == 0) {
							// remove all setting at logic tab
							$('div'+TAB_NAME.LOGICDESIGN).children().remove();
						}
						
						$.qp.decisiontable.objectTypeChange(item);
						return;
					} else {
						$(item).val(prevValue);
    					return;
					}
				} else {
					
					$.qp.decisiontable.objectTypeChange(item);
					return;
				}
			}
		// In the case of data loigc design is empty
		} else if($inputDesign.length > 0) {	
			// All in the case not check for exist in Logic design
			// In the case of change from single to single field another
			if((parseInt(prevValue) != DATATYPE.OBJECT 
					&& parseInt(prevValue) != DATATYPE.COMMON_OBJECT
					&& parseInt(prevValue) != DATATYPE.EXTERNAL_OBJECT)
					&& (parseInt(currValue) != DATATYPE.OBJECT
							&& parseInt(currValue) != DATATYPE.COMMON_OBJECT
							&& parseInt(currValue) != DATATYPE.EXTERNAL_OBJECT)) {
				
				for(var i = 0; i < $inputDesign.length; i++) {
					// in the case of exist
	    			if (valId == $inputDesign.eq(i).find("input:hidden[name$='.itemValue']").val()) {
	    				// Change item design
    					$inputDesign.eq(i).find("input:hidden[name$='.dataType']").val(currValue);
	    				$inputDesign.eq(i).find('td:eq(4)').text(CL_QP_DATATYPE[currValue]);
	    				// Check if table condition dont'n have at least one row
	    				$inputDesign = $("#"+tbItemDesignName).find(">tbody>tr");
	    				if($inputDesign == undefined || $inputDesign.length == 0) {
	    					// remove all setting at logic tab
	    					$('div'+TAB_NAME.LOGICDESIGN).children().remove();
	    				}
	    				// Change datatype success
	    				$.qp.decisiontable.objectTypeChange(item);
	    				return;
	    			}
	    		}
				
				$.qp.decisiontable.objectTypeChange(item);
				return;
			// In the case of change from single to object
			} else if ((parseInt(prevValue) != DATATYPE.OBJECT 
						&& parseInt(prevValue) != DATATYPE.COMMON_OBJECT
						&& parseInt(prevValue) != DATATYPE.EXTERNAL_OBJECT) 
						&& (parseInt(currValue) == DATATYPE.OBJECT
								|| parseInt(currValue) == DATATYPE.COMMON_OBJECT
								|| parseInt(currValue) == DATATYPE.EXTERNAL_OBJECT)) {
				for(var i = 0; i < $inputDesign.length; i++) {
					// Check exist
	    			if (valId == $inputDesign.eq(i).find("input:hidden[name$='.itemValue']").val()) {
	    				// Remove row not using
	    				$inputDesign.eq(i).remove();
	    				// Check if table condition dont'n have at least one row
	    				$inputDesign = $("#"+tbItemDesignName).find(">tbody>tr");
	    				if($inputDesign == undefined || $inputDesign.length == 0) {
	    					// remove all setting at logic tab
	    					$('div'+TAB_NAME.LOGICDESIGN).children().remove();
	    				}
	    				// Change datatype success
	    				$.qp.decisiontable.objectTypeChange(item);
	    				
	    				return;
	    			}
	    		}
				
				$.qp.decisiontable.objectTypeChange(item);

				return;
			// In the case of change from Object to single field
			} else if(((parseInt(prevValue) == DATATYPE.OBJECT
					|| parseInt(prevValue) == DATATYPE.COMMON_OBJECT
					|| parseInt(prevValue) == DATATYPE.EXTERNAL_OBJECT) 
					&& (parseInt(currValue) != DATATYPE.OBJECT
							&& parseInt(currValue) != DATATYPE.COMMON_OBJECT
							&& parseInt(currValue) != DATATYPE.EXTERNAL_OBJECT))
							|| ((parseInt(prevValue) == DATATYPE.OBJECT
									|| parseInt(prevValue) == DATATYPE.COMMON_OBJECT
									|| parseInt(prevValue) == DATATYPE.EXTERNAL_OBJECT)
									&& (parseInt(currValue) == DATATYPE.OBJECT
											|| parseInt(currValue) == DATATYPE.COMMON_OBJECT
											|| parseInt(currValue) == DATATYPE.EXTERNAL_OBJECT)
											&& parseInt(prevValue) != parseInt(currValue))) {
				
				$obj.nextAll().each(function(i) {
					// Get data item design it current time for check
					$inputDesign = $("#"+tbItemDesignName).find(">tbody>tr");
					// Check exist
					if($(this).find('td:first').attr('colspan') != undefined && $(this).find('td:first').attr('colspan') != '5') {
						// Get value of IN-OUT bean id
						var tmpval = $(this).find('input:hidden[name$='+hiddenName+']').val();
						// Check exist in item design
						for(var i = 0; i < $inputDesign.length; i++) {
							// Check exist
			    			if (tmpval == $inputDesign.eq(i).find("input:hidden[name$='.itemValue']").val()){
			    				$inputDesign.eq(i).remove();
			    				break;
			    			}
			    		}
					} else if($(this).find('td:first').attr('colspan') != undefined && $(this).find('td:first').attr('colspan') == '5') {
						// eixst loop
						return false;
					}
				});
				
				$.qp.decisiontable.objectTypeChange(item);
				return;
			}
		} else {
			$.qp.decisiontable.objectTypeChange(item);
			return;
		}
	} else {
		// in the case of had new row
		$.qp.decisiontable.objectTypeChange(item);
		return;
	}
}

/**
 * 
 * @param columnId
 * @returns {Boolean}
 */
function checkExistInLogicDesign(columnId) {
	var isExist = false;
	
	$("#"+TABLE_NAME.LOGIC).find('>tbody>tr>td[class!="btnRemove"]').each(function(rowIdx){
		if(columnId == $(this).find('input[name="decisionItemDesignId"]').val()) {
			isExist = true;
			return false;
		}
	});

	return isExist;
}

/* Adding by HungHX follow ticket 577, 583, 587 */
/*
 * When click module id then affect change dislay list common object definition & external object definition.
 * 
 */
function onchangeModuleAffectObjExt(obj){
	var scope = $(obj.target).closest("tr");
    var value = $(scope).find("input:hidden[name='moduleId']").val();

    $('table[id="tbl_input_list_result"]').find('>tbody>tr').each(function(){
    	changeArgOfRowObjByType(this, "16", value);
    	changeArgOfRowObjByType(this, "17", value);
    });
    
    $('table[id="tbl_output_list_result"]').find('>tbody>tr').each(function(){
    	changeArgOfRowObjByType(this, "16", value);
    	changeArgOfRowObjByType(this, "17", value);
    });
}

function changeArgOfRowObjByType(newRow, type, argValue) {
	switch (type) {
	case "16":
		if($(newRow) != undefined && $(newRow).find("input:text[name$='.commonObjectIdAutocomplete']").val() != undefined && type == "16"){
			$(newRow).find("input:text[name$='.commonObjectIdAutocomplete']").val('');
			$(newRow).find("input:hidden[name$='.commonObjectId']").val('');
			$(newRow).find("input:text[name$='.commonObjectIdAutocomplete']").attr("arg03", argValue);
			$.qp.removeAutocompleteData($($(newRow).find("input:hidden[name$='.commonObjectId']")).nextAll('.dropdown-toggle:first'));
		}
		break;
		
	case "17":
		if($(newRow) != undefined && $(newRow).find("input:text[name$='.externalObjectIdAutocomplete']").val() != undefined && type == "17"){
			$(newRow).find("input:text[name$='.externalObjectIdAutocomplete']").val('');
			$(newRow).find("input:hidden[name$='.externalObjectId']").val('');
			$(newRow).find("input:text[name$='.externalObjectIdAutocomplete']").attr("arg03", argValue);
			$.qp.removeAutocompleteData($($(newRow).find("input:hidden[name$='.externalObjectId']")).nextAll('.dropdown-toggle:first'));
		}
		break;

	default:
		break;
	}
}

function getLabelOfIndexLoop(paramId){
	var result = [];
	
	return result;
}
/* End adding */