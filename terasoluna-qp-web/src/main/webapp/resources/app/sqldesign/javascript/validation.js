$.namespace("$.qp.common.validation")
$.qp.common.validation = (function($$module){
	
	// Constant definition
	var LOGIC_CODE = {
			AND : 0,
			OR : 1
	}
	
	$$module.LOGIC_CODE = LOGIC_CODE;
	
	$$module.validateInputForm = function(table) {
		var messagesErr = "";
		
		messagesErr += $$module.validateRequired(table,"sqlDesignInputName", dbMsgSource['sc.sqldesign.0023'], 1);
		messagesErr += $$module.validateRequired(table,"sqlDesignInputCode", dbMsgSource['sc.sqldesign.0024'], 1);
		messagesErr += $$module.validateSpecialChar(table,"sqlDesignInputCode",dbMsgSource['sc.sqldesign.0024'], 1);
		//bangnl allow same input name
		//messagesErr += $$module.validateDuplicate(table,"sqlDesignInputName", dbMsgSource['sc.sqldesign.0023'], 1);
		messagesErr += $$module.validateDuplicate(table,"sqlDesignInputCode", dbMsgSource['sc.sqldesign.0024'], 1);
		
		return messagesErr;
	};
	
	$$module.validateOutputForm = function(table) {
		var messagesErr = "";
		
		messagesErr += $$module.validateRequired(table,"sqlDesignOutputName", dbMsgSource['sc.sqldesign.0023'], 0);
		messagesErr += $$module.validateRequired(table,"sqlDesignOutputCode", dbMsgSource['sc.sqldesign.0024'], 0);
		messagesErr += $$module.validateSpecialChar(table,"sqlDesignOutputCode",dbMsgSource['sc.sqldesign.0024'], 0);
		//bangnl allow same output name
		//messagesErr += $$module.validateDuplicate(table,"sqlDesignOutputName", dbMsgSource['sc.sqldesign.0023'], 0);
		messagesErr += $$module.validateDuplicate(table,"sqlDesignOutputCode", dbMsgSource['sc.sqldesign.0024'], 0);
		
		return messagesErr;
	};
	
	$$module.validateRequired = function(table, inputName, inputLabel, type) {
		
		var isList = true;
		var tabName = (type == 1)?dbMsgSource['sc.sqldesign.0003']:dbMsgSource['sc.sqldesign.0004'];
		
		var $Inputs
		$Inputs = $(table).find("input[name$=" + inputName + "]");
		var messages = "";
		$Inputs.each(function(index) {
			if ($.trim($(this).val()) == '') {
				if (!isList) {
					messages += dbMsgSource['err.autocomplete.0016'].replace("{0}",tabName).replace("{1}",inputLabel) + "\r";
				} else {
					messages += dbMsgSource['err.autocomplete.0022'].replace("{0}", tabName)
								.replace("{1}", $(this).closest('tr').attr('data-ar-rgroupindex')).replace("{2}",inputLabel) + "\r";
				}
			}
		});
		return messages;
	};
	
	$$module.validateDuplicate = function(table, inputName, inputLabel, type) {
		var tabName = (type == 1)?dbMsgSource['sc.sqldesign.0003']:dbMsgSource['sc.sqldesign.0004'];
		var $Inputs = $(table).find("input[name$=" + inputName + "]");
		var messages="";
		$Inputs.each(function(index) {
			var rgroup = ($(this).closest("tr").attr("data-ar-rgroup") == undefined || $(this).closest("tr").attr("data-ar-rgroup") == null ) ? "" : $(this).closest("tr").attr("data-ar-rgroup");
			for(var j=index + 1;j<$Inputs.length;j++) {
				var rgroupCheck = ($Inputs.eq(j).closest("tr").attr("data-ar-rgroup") == undefined || $Inputs.eq(j).closest("tr").attr("data-ar-rgroup") == null ) ? "" : $Inputs.eq(j).closest("tr").attr("data-ar-rgroup");
				if($.trim($(this).val()) != undefined && $.trim($(this).val()) != "" 
						&& $.trim($Inputs.eq(j).val()) != undefined && $.trim($Inputs.eq(j).val()) != ""
						&& $.trim($(this).val()) == $.trim($Inputs.eq(j).val())
						&& rgroup.trim() == rgroupCheck.trim()) {
					messages +=dbMsgSource['err.autocomplete.0023'].replace("{0}",tabName)
								.replace("{1}",$Inputs.eq(j).closest('tr').attr('data-ar-rgroupindex')).replace("{2}",inputLabel) + "\r";;
					break;
				}
			}
		});
		return messages;
	}
	
	$$module.validateSpecialChar = function(table, inputName, inputLabel, type) {
		var tabName = (type == 1)?dbMsgSource['sc.sqldesign.0003']:dbMsgSource['sc.sqldesign.0004'];
		var messages = "";
		var $Inputs = $(table).find("input[name$=" + inputName + "]");
		var flag = false;
		$Inputs.each(function(index) {
			flag = false;
			if ($.trim($(this).val()) !='' && !($.qp.validateIsCode($(this).val()))) {
				flag = true;
			}
			if(flag){
				messages += dbMsgSource['err.autocomplete.0024'].replace("{0}", tabName).replace("{1}", $(this).closest('tr').attr('data-ar-rgroupindex')).replace("{3}", inputLabel).replace("{3}", "\\@/:_*?<>|white-space") + "\r";;
			}
		});
		messages = messages.replace('&quot;', '"').replace('&quot;', '"');
		return messages;
	}
	
	$$module.validateMappingDataTypeOutput = function(table) {
		return;
	}
	
	$$module.validateSqlText = function(sqlText,sqlPattern){
		var messages = [];
		if(sqlText.indexOf('?')>-1){
			messages.push(dbMsgSource['err.sqldesign.0001']);
		}
		var matches = [];
		switch(sqlPattern){
		case '0':
			matches = sqlText.match(/^select/i);
			if(!matches || matches.length==0){
				messages.push(dbMsgSource['err.sqldesign.0002'].replace(/&quot;/g,'"'));
			}
			break;
		case '1':
			matches = sqlText.match(/^insert/i);
			if(!matches || matches.length==0){
				messages.push(dbMsgSource['err.sqldesign.0003'].replace(/&quot;/g,'"'));
			}
			break;
		case '2':
			matches = sqlText.match(/^update/i);
			if(!matches || matches.length==0){
				messages.push(dbMsgSource['err.sqldesign.0004'].replace(/&quot;/g,'"'));
			}
			break;
		case '3':
			matches = sqlText.match(/^delete/i);
			if(!matches || matches.length==0){
				messages.push(dbMsgSource['err.sqldesign.0005'].replace(/&quot;/g,'"'));
			}
			break;
		}
		
		return messages;
	};
	
	$$module.validateWhereRequire = function(tabName) {
		var errMsg = "";
		var $Tr;
		
		if (tabName != undefined) {
			$Tr = $(tabName +' #whereForm').find('>tbody>tr');
		} else {
			$Tr = $('#whereForm').find('>tbody>tr');
		}
		
		$Tr.each(function(idx) {
			
			var logicCode = "";
			var rowIdx = idx + 1;
			var cssDspl = $(this).find('table>tbody>tr:eq(0) select[name$=".logicCode"]').css('display');
			
			if(cssDspl!= undefined && cssDspl != "none") {
				logicCode = $(this).find('table>tbody>tr:eq(0) select[name$=".logicCode"]').val();
				
				if(logicCode == "" ) {
					errMsg += dbMsgSource['err.autocomplete.0005'].replace("{0}", dbMsgSource['sc.autocomplete.0046'])
					             .replace("{1}", rowIdx).replace("{2}", dbMsgSource['sc.autocomplete.0053']) + "\r";
				}
			}
			
			var leftTableId = $(this).find('table>tbody>tr:eq(0) input:hidden[name$=".leftTableId"]').val();
			var leftColumnId = $(this).find('table>tbody>tr:eq(1) input:hidden[name$=".leftColumnId"]').val();
			
			if(leftTableId == "") {
				errMsg += dbMsgSource['err.autocomplete.0005'].replace("{0}", dbMsgSource['sc.autocomplete.0046'])
							.replace("{1}", rowIdx).replace("{2}", dbMsgSource['sc.autocomplete.0054']) + "\r";
				errMsg += dbMsgSource['err.autocomplete.0005'].replace("{0}", dbMsgSource['sc.autocomplete.0046'])
							.replace("{1}", rowIdx).replace("{2}", dbMsgSource['sc.autocomplete.0055']) + "\r";

			} else if (leftColumnId == "") {
				errMsg += dbMsgSource['err.autocomplete.0005'].replace("{0}", dbMsgSource['sc.autocomplete.0046'])
							.replace("{1}", rowIdx).replace("{2}", dbMsgSource['sc.autocomplete.0055']) + "\r";
			}
			
			var operatorCode = $(this).find('table>tbody>tr:eq(0) select[name$=".operatorCode"]').val();
			
			if(operatorCode == undefined || operatorCode == "") {
				errMsg += dbMsgSource['err.autocomplete.0005'].replace("{0}", dbMsgSource['sc.autocomplete.0046'])
							.replace("{1}", rowIdx).replace("{2}", dbMsgSource['sc.autocomplete.0057']) + "\r";
			}
			
			var conditionType = $(this).find('table>tbody>tr:eq(1) select[name$=".conditionType"]').val();
			if (conditionType == undefined || conditionType == "") {
				errMsg += dbMsgSource['err.autocomplete.0005'].replace("{0}", dbMsgSource['sc.autocomplete.0046'])
							.replace("{1}", rowIdx).replace("{2}", dbMsgSource['sc.autocomplete.0056']) + "\r";
			}
			
			if (operatorCode != "" && conditionType != "") {
				
				switch (conditionType) {

				case "0":
					
					switch (operatorCode) {
					
					// Between
					case "7":
						var value1 = $(this).find('table>tbody>tr:eq(1) input[name$=".value"]').val();
						if (value1 == undefined || $.trim(value1) == "") {
							errMsg += dbMsgSource['err.autocomplete.0005'].replace("{0}", dbMsgSource['sc.autocomplete.0046'])
										.replace("{1}", rowIdx).replace("{2}", dbMsgSource['sc.autocomplete.0060'] + "1") + "\r";
						}
						var value2 = $(this).find('table>tbody>tr:eq(1) input[name$=".value2"]').val();
						if (value2 == undefined || $.trim(value2) == "") {
							errMsg += dbMsgSource['err.autocomplete.0005'].replace("{0}", dbMsgSource['sc.autocomplete.0046'])
										.replace("{1}", rowIdx).replace("{2}", dbMsgSource['sc.autocomplete.0060'] + "2") + "\r";
						}

						break;

					case "8": break;
					case "9" : break;
						
					default:
						
						var value = $(this).find('table>tbody>tr:eq(1) input[name$=".value"]').val();
						if (value == undefined || $.trim(value) == "") {
							errMsg += dbMsgSource['err.autocomplete.0005'].replace("{0}", dbMsgSource['sc.autocomplete.0046'])
										.replace("{1}", rowIdx).replace("{2}", dbMsgSource['sc.autocomplete.0060']) + "\r";
						}
						
						break;
					}
					
					break;
				case "1":
					
					var rightTableId = $(this).find('table>tbody>tr:eq(0) input:hidden[name$=".rightTableId"]').val();
					var rightColumnId = $(this).find('table>tbody>tr:eq(1) input:hidden[name$=".rightColumnId"]').val();
					
					if(rightTableId == undefined || rightTableId == ""){
						errMsg += dbMsgSource['err.autocomplete.0005'].replace("{0}", dbMsgSource['sc.autocomplete.0046'])
									.replace("{1}", rowIdx).replace("{2}", dbMsgSource['sc.autocomplete.0058']) + "\r";
						errMsg += dbMsgSource['err.autocomplete.0005'].replace("{0}", dbMsgSource['sc.autocomplete.0046'])
									.replace("{1}", rowIdx).replace("{2}", dbMsgSource['sc.autocomplete.0059']) + "\r";
						
					} else if (rightColumnId == undefined || rightColumnId == "") {
						errMsg += dbMsgSource['err.autocomplete.0005'].replace("{0}", dbMsgSource['sc.autocomplete.0046'])
									.replace("{1}", rowIdx).replace("{2}", dbMsgSource['sc.autocomplete.0059']) + "\r";
					}
					
					break;
				case "2":
					
					var value = $(this).find('table>tbody>tr:eq(1) select[name$=".arg"]').val();
					if (value == undefined || $.trim(value) == "") {
						errMsg += dbMsgSource['err.autocomplete.0005'].replace("{0}", dbMsgSource['sc.autocomplete.0046'])
									.replace("{1}", rowIdx).replace("{2}", dbMsgSource['sc.autocomplete.0060']) + "\r";
					}
					
					break;
				}
			} else if (operatorCode == "" && conditionType == "1") {
				var rightTableId = $(this).find('table>tbody>tr:eq(0) input:hidden[name$=".rightTableId"]').val();
				var rightColumnId = $(this).find('table>tbody>tr:eq(1) input:hidden[name$=".rightColumnId"]').val();
				
				if(rightTableId == undefined || rightTableId == "") {
					errMsg += dbMsgSource['err.autocomplete.0005'].replace("{0}", dbMsgSource['sc.autocomplete.0046'])
								.replace("{1}", rowIdx).replace("{2}", dbMsgSource['sc.autocomplete.0058']) + "\r";
					errMsg += dbMsgSource['err.autocomplete.0005'].replace("{0}", dbMsgSource['sc.autocomplete.0046'])
								.replace("{1}", rowIdx).replace("{2}", dbMsgSource['sc.autocomplete.0059']) + "\r";
				} else if (rightColumnId == undefined || rightColumnId == "") {
					errMsg += dbMsgSource['err.autocomplete.0005'].replace("{0}", dbMsgSource['sc.autocomplete.0046'])
								.replace("{1}", rowIdx).replace("{2}", dbMsgSource['sc.autocomplete.0059']) + "\r";
				}
			}
		});

		return errMsg;
	};

	$$module.validateMappingDataTypeWhere = function(tabName) {
		var errMsg = "";
		var $Tr;
		var mapInput = [];
		var mapClmId = $.qp.sqlbuilder.reloadMapColumnId();
		
		if (tabName != undefined) {
			$Tr = $(tabName +' #whereForm').find('>tbody>tr');
		} else {
			$Tr = $('#whereForm').find('>tbody>tr');
		}
		
		mapInput = $.qp.sqlbuilder.buildMapInputByCode('#inputForm', 'sqlDesignInputCode');

		$Tr.each(function(idx) {
			var rowIdx = idx + 1;
			var leftColumnId = $(this).find('table>tbody>tr:eq(1) input:hidden[name$=".leftColumnId"]').val();
			
			if(leftColumnId != undefined && leftColumnId != "") {
				
				var operatorCode = $(this).find('table>tbody>tr:eq(0) select[name$=".operatorCode"]').val();
				var conditionType = $(this).find('table>tbody>tr:eq(1) select[name$=".conditionType"]').val();
				
				if (operatorCode != "" && conditionType != "") {
					switch (conditionType) {

					case "0":

						switch (operatorCode) {

						// Between
						case "7":
							var flgcheck = false;
							var value1 = $(this).find('table>tbody>tr:eq(1) input[name$=".value"]').val();
							if (value1 != undefined && $.trim(value1) != "") {
								
							}
							var value2 = $(this).find('table>tbody>tr:eq(1) input[name$=".value2"]').val();
							if (value2 != undefined && $.trim(value2) != "") {
								
							}
							
							break;

						case "8": break;
						case "9" : break;
							
						default:
							
							var value = $(this).find('table>tbody>tr:eq(1) input[name$=".value"]').val();
							if (value != undefined && $.trim(value) != "") {
								
							}
							
							break;
						}
						
						break;
					case "1":

						var rightColumnId = $(this).find('table>tbody>tr:eq(1) input:hidden[name$=".rightColumnId"]').val();
						if (rightColumnId != undefined && rightColumnId != "" && (mapClmId[leftColumnId] != mapClmId[rightColumnId])) {
							errMsg += dbMsgSource['err.autocomplete.0020'].replace("{0}", dbMsgSource['sc.autocomplete.0046']).replace("{1}", rowIdx) + "\r";
						}
						
						break;
						/**
						 * bangnl
						 * allow user choose column not match data type with input beand
						 */
					/*case "2":
						
						var value = $.trim($(this).find('table>tbody>tr:eq(1) select[name$=".arg"] option:selected').val());
						if (value != undefined && $.trim(value) != ""  && !(mapClmId[leftColumnId] in mapInput[value]) ) {
							errMsg += dbMsgSource['err.autocomplete.0020'].replace("{0}", dbMsgSource['sc.autocomplete.0046']).replace("{1}", rowIdx) + "\r";
						}
						
						break;*/
					}

				} else if (operatorCode == "" && conditionType == "1") {
					
					var rightColumnId = $(this).find('table>tbody>tr:eq(1) input:hidden[name$=".rightColumnId"]').val();
					
					if (rightColumnId != undefined && rightColumnId != "" && (mapClmId[leftColumnId] != mapClmId[rightColumnId])) {
						errMsg += dbMsgSource['err.autocomplete.0020'].replace("{0}", dbMsgSource['sc.autocomplete.0046']).replace("{1}", rowIdx) + "\r";
					}
				}
			}
		});

		return errMsg;
	};
	
	/**
	 * bangnl
	 * clone from $$module.validateMappingDataTypeWhere to validate for input bean
	 * 
	 */
	$$module.validateInputBeanInWhereCondition = function(tabName){
		var errMsg = "";
		var $Tr;
		var mapInput = [];
		var mapClmId = $.qp.sqlbuilder.reloadMapColumnId();
		
		if (tabName != undefined) {
			$Tr = $(tabName +' #whereForm').find('>tbody>tr');
		} else {
			$Tr = $('#whereForm').find('>tbody>tr');
		}
		
		mapInput = $.qp.sqlbuilder.buildMapInputByCode('#inputForm', 'sqlDesignInputCode');
		
		$Tr.each(function(idx) {
			var rowIdx = idx + 1;
			var leftColumnId = $(this).find('table>tbody>tr:eq(1) input:hidden[name$=".leftColumnId"]').val();
			
			if(leftColumnId != undefined && leftColumnId != "") {
				var value = $.trim($(this).find('table>tbody>tr:eq(1) select[name$=".arg"] option:selected').val());
				if (value != undefined && $.trim(value) != ""  && !(mapClmId[leftColumnId] in mapInput[value]) ) {
					errMsg += dbMsgSource['err.autocomplete.0020'].replace("{0}", dbMsgSource['sc.autocomplete.0046']).replace("{1}", rowIdx) + "</br>";
				}
			}
			
		});
		return errMsg;
		
	};
	
	$$module.validateGroup = function() {
		var result = true;
		var errMsg = "";
		var arrErrMsg = [];

		var arrObj = $$module.getDataProcessPrepared();

		if (arrObj.length != 0) {
			
			var arrSingle = [];
			var arrGroup = [];
			var arrGroupElm = [];
			
			for(var i = 0; i < arrObj.length; i++) {
				
				if(arrObj[i].group) {
					arrGroupElm.push(arrObj[i]);
					// is last
					if (i == arrObj.length - 1 && arrGroupElm.length > 0){
						arrGroup.push(arrGroupElm);
						break;
					}
				} else {
					
					if (arrGroupElm.length != 0) {

						var arrGroupElmFirst = arrGroupElm;
						// In the case single + group
						var idx = arrGroupElm[0].idx-1;
						if (idx > 0) {
							arrGroupElmFirst.push(arrObj[idx]);
							arrGroupElmFirst = $$module.sortArrObj(arrGroupElmFirst);
							arrGroup.push(arrGroupElmFirst);
						}
						
						// In the case group + single
						arrGroupElm.push(arrObj[i]);
						arrGroup.push(arrGroupElm);
						arrGroupElm = [];
					}

					arrSingle.push(arrObj[i]);
				}
			}
			

			if (arrSingle.length == 0 && arrGroup.length > 0) {
				arrErrMsg = $$module.getErrMsgSingle(arrGroupElm);
			} else if (arrSingle.length != 0 && arrGroup.length == 0) {
				arrErrMsg = $$module.getErrMsgSingle(arrSingle);
			} else {
				arrErrMsg = $$module.getErrMsgSingle(arrSingle);
				arrErrMsg.push.apply(arrErrMsg, $$module.getErrMsgGroup(arrGroup));
			}
			
		}

		if (arrErrMsg.length > 0) {
			var result = [];
			var item0;

			arrErrMsg.forEach(function(item0) {
			     if(result.indexOf(item0) < 0) {
			         result.push(item0);
			     }
			});
			
			if (result.length > 0) {
				for(var i = 0; i < result.length; i++) {
					errMsg += result[i];
				}
			}
		}
		
		return errMsg;
	};
	
	$$module.getDataProcessPrepared = function(){
		var obj;
		var arrObj = [];
		var $Tr = $('#whereForm').find('tbody>tr[data-ar-templateid="whereForm-template"]');
		var isGrpCount = 0;
		
		$Tr.each(function(i) {
			obj = new Object();
			// Default item is not in group
			var isGrpFlg = false;
			
			// And is 0 && Or is 1
			var logicCode = $(this).find('table>tbody>tr:eq(0) select[name$=".logicCode"]').val();
			if(i == 0) logicCode = "";
			
			if($(this).find('>td:eq(1)>input:checkbox').is(":checked")) {
				
				if($(this).next() != undefined && $(this).next().find('>td:eq(1)>input:checkbox').is(":checked")){
					// Is group
					isGrpFlg = true;
					isGrpCount++;
					
				} else {
					// Is group but one item in group should setting not group
					if(isGrpCount >= 1) {
						isGrpFlg = true;
					} else {
						isGrpFlg = false;
					}
					
					// Reset value default
					isGrpCount = 0;
				}
			}
			
			obj.group = isGrpFlg;
			
			var functionCode = $Tr.eq(i).find('table>tbody>tr:eq(2) select[name$=".functionCode"]').val();
			if (functionCode != undefined && functionCode.length > 0) {
				// Is item having
				obj.type = 0;
			} else {
				// Is item where
				obj.type = 1;
			}
			
			obj.item = $(this);
			obj.idx = i;
			obj.logic = logicCode;
			
			arrObj.push(obj);
		});
		
		return arrObj;
	};
	
	$$module.getErrMsgSingle = function(arrObj){
		var arrMsgErr = [];
		
		for(var i = 1; i < arrObj.length; i++) {
			
			if ((arrObj[i].idx - arrObj[i-1].idx) == 1){
				if(arrObj[i].logic == LOGIC_CODE.OR && arrObj[i].type != arrObj[i-1].type) {
					arrMsgErr.push(
							dbMsgSource['err.autocomplete.0017']
								.replace("{0}", dbMsgSource['sc.autocomplete.0046'])
								.replace("{1}", arrObj[i].idx+1) + "\r");
				}
			}
		}
		
		return arrMsgErr;
	};
	
	$$module.getErrMsgGroup = function(arrParentObj) {
		var arrMsgErr = [];
		
		for(var i = 0; i < arrParentObj.length; i++) {

			var arrObj = arrParentObj[i];
			// Marking object is item single
			var objMark;
			
			// In the case item first is item single
			if(arrObj[0].type == 1) {
				arrObj[0].logic = arrObj[1].logic;
				objMark = arrObj[0];
				// remove item is single
				arrObj.splice(0, 1);
			} else {
				// In the case item last is item single
				objMark = arrObj[arrObj.length-1];
				// Remove item is single
				arrObj.splice(arrObj.length-1, 1);
			}

			for (var j = 0 ; j < arrObj.length; j++) {
				if(objMark.logic == LOGIC_CODE.OR && objMark.type != arrObj[i].type ) {
					arrMsgErr.push(
							dbMsgSource['err.autocomplete.0017']
								.replace("{0}", dbMsgSource['sc.autocomplete.0046'])
								.replace("{1}", arrObj[i].idx+1) + "\r");
				}
			}
			
			if($$module.getErrMsgSingle(arrObj).length > 0) {
				arrMsgErr.push.apply(arrMsgErr, $$module.getErrMsgSingle(arrObj));
			}
		}
		
		return arrMsgErr;
	};

	$$module.sortArrObj = function(arrObj) {
		for(var i = 0; i < arrObj.length; i++){
			for (var j = 1; j < arrObj.length; j++){
				if(arrObj[i].idx > arrObj[j].idx){
					var tmp = arrObj[i];
					arrObj[i] = arrObj[j];
					arrObj[j] = tmp;
				}
			}
		}
		
		return arrObj;
	};

	$$module.validateRequiredFromForm = function() {
		var errMsg = "";
		var rowIdx = 1;
		var isErrFlg = false;
		
		var tableIdFirst = $.trim($('#fromForm').find('input[name$=".tableIdAutocomplete"]:first').val());
		
		if(tableIdFirst == undefined || tableIdFirst.length == 0) {
			errMsg += dbMsgSource['err.autocomplete.0007'].replace("{0}", dbMsgSource['sc.autocomplete.0041'])
				.replace("{1}", rowIdx) + "\r";
		}
		
		$('#fromForm').find('>tbody>tr:gt(0)').each(function(idx) {
			var $pos = $(this).find('table:first>tbody>tr:first');
			rowIdx++;
			
			if($pos.find('td:first>label').is(':visible')){
				if($pos.find('>td:first>div:first>input:hidden[name$=".joinTableId"]').val() != "" 
					&& $pos.find('>td:first>div:first>input:hidden[name$=".joinTableId"]').val() != undefined) {
					
					var $posJoinCond = $pos.find('>td:eq(2)>div');
					if($posJoinCond.find('div:first input:radio[name$=".joinType"]').is(":checked")) {
						
						$posJoinCond.find('table>tbody>tr').each(function(i){
							if($.trim($(this).find('input:hidden[name$=".tableId"]').val()) == "") isErrFlg = true;
							if($.trim($(this).find('input:hidden[name$=".columnId"]').val()) == "") isErrFlg = true;
							if($(this).find('select[name$=".operatorCode"]').val() == "") isErrFlg = true;
							if($.trim($(this).find('input:hidden[name$=".joinColumnId"]').val()) == "") isErrFlg = true;
							if(isErrFlg) return false;
						});
					} else {
						isErrFlg = true;
					}
					
				} else {
					isErrFlg = true;
				}
			}
			
			if (isErrFlg) {
				errMsg += dbMsgSource['err.autocomplete.0007'].replace("{0}", dbMsgSource['sc.autocomplete.0041'])
					.replace("{1}", rowIdx) + "\r";
			}
		});
		
		return errMsg;
	};
	
	$$module.validateMappingDataTypeFromForm = function() {
		var rowIdx = 1;
		var errMsg = "";
		var mapClmId = $.qp.sqlbuilder.reloadMapColumnId();
		
		$('#fromForm').find('>tbody>tr:gt(0)').each(function(idx) {
			var $pos = $(this).find('table:first>tbody>tr:first');
			rowIdx++;
			if($pos.find('td:first>label').is(':visible')){
				if($pos.find('>td:first>div:first>input:hidden[name$=".joinTableId"]').val() != "" 
					&& $pos.find('>td:first>div:first>input:hidden[name$=".joinTableId"]').val() != undefined) {
					var $posJoinCond = $pos.find('>td:eq(2)>div');
					var tableName = $pos.find('>td:first>div:first>input[name$=".joinTableIdAutocomplete"]').val();
					var idxChild = 0;
					$posJoinCond.find('table>tbody>tr').each(function(i){
						idxChild++;
						var leftClmId = $(this).find('input:hidden[name$=".columnId"]').val();
						var rightClmId = $(this).find('input:hidden[name$=".joinColumnId"]').val();
						if(leftClmId != "" && rightClmId != "" && (mapClmId[leftClmId] != mapClmId[rightClmId])){
							errMsg += dbMsgSource['err.autocomplete.0019'].replace("{0}", dbMsgSource['sc.autocomplete.0041'])
								.replace("{1}", rowIdx).replace("{2}", tableName).replace("{3}", idxChild) + "\r";
						}
					});
				}
			}
		});
		
		return errMsg;
	};

	$$module.validateSelectForm = function() {
		var errMsg = "";
		var $Select = $('#selectForm').find('>tbody>tr').find('td:first>input:checkbox:checked').length;
		if($Select == 0) {
			errMsg = dbMsgSource['err.autocomplete.0021'].replace("{0}", dbMsgSource['sc.autocomplete.0045']) + "\r";
		}

		return errMsg;
	}

	$$module.validateRequireTableForm = function(tabNameId) {
		var errMsg = "";
		if($(tabNameId+' #intoForm').find('input[name="intoForm.tableId"]').val() == "") {
			errMsg = dbMsgSource['err.autocomplete.0006'];
		}
		return errMsg;
	};
	
	$$module.validateRequireValueForm = function() {
		var errMsg = "";
		var trValues = $('#valueForm').find('>tbody>tr');
		if(trValues.length == 0){
			errMsg += "\r"+dbMsgSource["sc.sqldesign.0067"].replace("{0}",dbMsgSource["sc.sqldesign.0034"])+ "\r";
		}
		$(trValues).each(function(i){
			if($(this).find('input[name$=".columnId"]').val() == "") {
				errMsg += dbMsgSource['err.autocomplete.0005'].replace("{0}", dbMsgSource['sc.sqldesign.0034'])
	        		.replace("{1}", i+1).replace("{2}", dbMsgSource['sc.autocomplete.0017']) + "\r";
			}
			if($(this).find('select[name$=".parameter"]').val() == "") {
				errMsg += dbMsgSource['err.autocomplete.0005'].replace("{0}", dbMsgSource['sc.sqldesign.0034'])
	        		.replace("{1}", i+1).replace("{2}", dbMsgSource['sc.sqldesign.0033']) + "\r";
			}
		});
		
		return errMsg;
	};
	
	$$module.validateMappingValueForm = function() {
		var errMsg = "";
		var mapInput = $.qp.sqlbuilder.buildMapInputByCode('#inputForm', 'sqlDesignInputCode');
		var mapClmId = $.qp.sqlbuilder.reloadMapColumnId();
		var columns = $.qp.sqlbuilder.getColumsByTableIdIntoForm();
		$('#valueForm').find('>tbody>tr').each(function(rowIdx){
			var columnId = $(this).find('input[name$=".columnId"]').val();
			//var parameter = $(this).find('select[name$=".parameter"] option:checked').text();
			var parameter = $(this).find('select[name$=".parameter"] option:checked').val();
			
			if(!!parameter && columnId != "" && parameter != "" && mapInput.length > 0 && !(mapClmId[columnId] in mapInput[parameter])) {
				errMsg += dbMsgSource['err.autocomplete.0020'].replace("{0}", dbMsgSource['sc.sqldesign.0034']).replace("{1}", rowIdx+1) + "\r";
			}
			var valueType = $(this).find("select[name$='.valueType']").val();
			if(!!valueType && valueType == 2){
				errMsg += $$module.validateAutocompleteEntityTypeValue(columns, rowIdx,this);
			}
		});
		return errMsg;
	};
	
	$$module.validateAutocompleteEntityTypeValue = function(columns, rowIdx,row){
		var errMsg = "";
		var dataType = $(row).find("input[type=hidden][name$='.dataType']").val();
		var parameter = $(row).find("input[type=hidden][name$='.parameter']").val();
		var column = $.qp.sqlbuilder.getColumnInColumnsById(columns,parameter);
		if(dataType != column.output03){
			errMsg = dbMsgSource['err.autocomplete.0020'].replace("{0}", dbMsgSource['sc.sqldesign.0034']).replace("{1}", rowIdx+1) + "\r";
		}
		return errMsg;
	}
	
	$$module.validateRequireOrderForm = function() {
		var errMsg = "";
		$('#orderByForm').find('tbody>tr').each(function(i){
			if($(this).find('input[name$=".tableColumn"]').val() == "") {
				errMsg += dbMsgSource['err.autocomplete.0005'].replace("{0}", dbMsgSource['sc.autocomplete.0044'])
	    			.replace("{1}", i+1).replace("{2}", dbMsgSource['sc.sqldesign.0048']) + "\r";
			}
		});

		return errMsg;
	};
	
	$$module.validateRequireCheckedOutputForm = function(table) {
		var errMsg = "";
		if($(table).length > 0  && $(table).find('[name$="submitColumn"]:checked').length == 0)
			errMsg += dbMsgSource['err.autocomplete.0016'].replace("{0}", dbMsgSource['sc.sqldesign.0004'])
	    			.replace("{1}", dbMsgSource['sc.autocomplete.0020']) + "\r";
		
		return errMsg;
	};
	
	$$module.validateRequireCheckedDisplayOutputForm = function(table) {
		var errMsg = "";
		if($(table).length > 0  && $(table).find('[name^="outputForm.output"]:checked').length == 0)
			errMsg += dbMsgSource['err.autocomplete.0015'].replace("{0}", dbMsgSource['sc.sqldesign.0004'])
	    			.replace("{1}", dbMsgSource['sc.autocomplete.0019']) + "\r";
		
		return errMsg;
	};
	
	$$module.validateMappingDataTypeOutputForm = function(tabName, mappingColumn) {
		var errMsg = "";
		var $Select = $('#selectForm').find('>tbody>tr').find('td:first>input:checkbox:checked').length;
		var MAP_SEQUENCE_FROM_SELECT = $.qp.sqlbuilder.MAP_SEQUENCE_FROM_SELECT;
		var MAP_PHYSICAL_TO_JAVA_TYPES = $.qp.sqlbuilder.PHYSICAL_TO_JAVA_TYPES;
		
		if($Select != 0) {
//			var mapOutput = [];
//			// create map sequence of select
//			$(tabName).find('>tbody>tr').each(function() {
//				var sequence = $(this).find("[name$="+mappingColumn+"]").val();
//				if(sequence != "") {
//					mapOutput = $.qp.sqlbuilder.buildMapByKey(mapOutput, sequence, $(this).find('select[name$=".dataType"]').val());
//				}
//			});
//			
//			if(mapOutput.length > 0){
//				$.each($(tabName).find('>tbody>tr'), function() {
//					var sequence = $(this).find("[name$="+mappingColumn+"]").val();
//					if(sequence != "" && !(MAP_SEQUENCE_FROM_SELECT[sequence] in mapOutput[sequence])) {
//						errMsg += dbMsgSource['err.autocomplete.0020'].replace("{0}", dbMsgSource['sc.sqldesign.0004'])
//		    						.replace("{1}", $(this).attr('data-ar-rgroupindex')) + "\r";
//					}
//				});
//			}
			
			$.each($(tabName).find('>tbody>tr'), function() {
				var sequence = $(this).find("[name$="+mappingColumn+"]").val();
				if(sequence != undefined && $.trim(sequence) != "" && MAP_PHYSICAL_TO_JAVA_TYPES[MAP_SEQUENCE_FROM_SELECT[sequence]] != $(this).find('[name$=".dataType"]').val()) {
					errMsg += dbMsgSource['err.autocomplete.0020'].replace("{0}", dbMsgSource['sc.sqldesign.0004'])
	    						.replace("{1}", $(this).attr('data-ar-rgroupindex')) + "</br>";
				}
			});
		}
		
		return errMsg;
	}
	
	$$module.validateTypeReturnOfOutput = function(){
		var message = "";
		var sqlPattern = $("#sqlDesignAdvancedDesignForm select[name='sqlDesignForm.sqlPattern']").val();
		if(sqlPattern != 0){
			return "";
		}
		var pageable = $("#sqlDesignAdvancedDesignForm input[type=hidden][name='sqlDesignForm.pageable']").val();
		if(!pageable){
			return message;
		}
		var returnTypeOutput = $("#sqlDesignAdvancedDesignForm select[name='sqlDesignForm.returnType']").val();
		if(!returnTypeOutput){
			return message;
		}
		if(pageable == 1 && returnTypeOutput == 0){
			message = dbMsgSource["sc.sqldesign.0068"];
		}
		return message;
	}
	
	return $$module;
})($.namespace("$.qp.common.validation"));

