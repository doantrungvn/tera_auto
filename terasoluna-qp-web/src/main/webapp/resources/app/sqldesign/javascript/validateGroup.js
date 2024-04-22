var LOGIC_CODE = {
		AND : 0,
		OR : 1
}

function validateWhere(){
	var errMsg = "";
	
	errMsg += validateRequire();
	errMsg += validateGroup();
	
	return errMsg;
}

function validateRequire(tabName) {
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
}

function validateGroup() {
	var result = true;
	var errMsg = "";
	var arrErrMsg = [];
	
	var arrObj = getDataProcessPrepared();
	
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
						arrGroupElmFirst = sortArrObj(arrGroupElmFirst);
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
			arrErrMsg = getErrMsgSingle(arrGroupElm);
		} else if (arrSingle.length != 0 && arrGroup.length == 0) {
			arrErrMsg = getErrMsgSingle(arrSingle);
		} else {
			arrErrMsg = getErrMsgSingle(arrSingle);
			arrErrMsg.push.apply(arrErrMsg, getErrMsgGroup(arrGroup));
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
}

function getErrMsgSingle(arrObj) {
	
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
}

function getErrMsgGroup(arrParentObj){
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
				/*arrMsgErr.push("[Where] Line "+(arrObj[i].idx+1)+" : Logic condition not meaningful\r");*/
				arrMsgErr.push(
						dbMsgSource['err.autocomplete.0017']
							.replace("{0}", dbMsgSource['sc.autocomplete.0046'])
							.replace("{1}", arrObj[i].idx+1) + "\r");
			}
		}
		
		if(getErrMsgSingle(arrObj).length > 0) {
			arrMsgErr.push.apply(arrMsgErr, getErrMsgSingle(arrObj));
		}
	}
	
	return arrMsgErr;
}


function sortArrObj(arrObj) {
	
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
}

function getDataProcessPrepared() {
	
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
}