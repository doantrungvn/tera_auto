var KEYTYPE = {
	PRIMARY : 1, 
	INDEX : 8,
	UNIQUE : 4,
	FULLTEXT : 16
};

function showDialogSettingKey() {
	
	var rowCount = $('#tmp_list_table tbody tr').length;
	if(rowCount == 0) return;
	
	$($("#settingKey")).modal(
		{ 
			show: true,
			closable: false,
			keyboard:false,
			backdrop:'static'
		}
	);
	var hasKeyArr = $("#storeKeySave").find($('input:first-child')).val();
	if(hasKeyArr != "" && hasKeyArr != undefined){
		resetDataStoreKey();
		$('#storeKeyTemp').val($('#storeKeySave').html());
		var hasKey = $("#storeKeySave").find($('input:first-child')).val().split("�")[1];
		changeKeyByName(hasKey);
	}else{
		initPKSetting();
		$('#tmp_list_table').find("tbody tr").each(function() {
			var commonColumn = $(this).find("input[name$='.commonColumn']").val();
			var columnName = $(this).find("input[name$='.name']").val();
			
			if(columnName != "" && commonColumn != "1"){
				$("#keyavail").append($('<option>', {value:columnName, text: columnName}));
			}
		});
	}
	
	$("#keyList").attr('disabled', false);
	$("#keyname").attr('disabled', false);
	$("#keytype").attr('disabled', false);
	$("#keyfields").attr('disabled', false);
	$("#keyavail").attr('disabled', false);
	$.qp.initialConventionNameCode();
}

function backupKeyInfor(){
	
	var storeKeySave = $("#storeKeyTemp");
	
	$('#storeKeyTemp').find("input").each(function() {
		$(this).remove();
	});
	
	var htmlStoreKeyBackup;
	
	$('#storeKeySave').find("input").each(function() {
		htmlStoreKeyBackup = "<input type=\"hidden\" value=\'"+ $(this).val() + "' />";
		storeKeySave.append(htmlStoreKeyBackup);
	});
}



function resetDataStoreKey(){
	var listColumn = {};
	$('#tmp_list_table').find("tbody tr").each(function() {
		listColumn[$(this).find("input[name$='.indexRow']").val()] = $(this).find("input[name$='.name']").val();
	});
	
	var setIndexListKey = JSON.parse($("#listValueKeyObjectType").val());
	
	$('#storeKeySave').find("input").each(function() {
		
		var keyInformation = $(this).val().split("�");
		
		var newName = keyInformation[0] + "�" +keyInformation[1] + "�";
		
		for (var int = 2; int < keyInformation.length - 1; int++) {
			var columnName = keyInformation[int].split("◘");
			for (var key in listColumn) {
				if (listColumn.hasOwnProperty(key)) {
					if(key == columnName[0]){
						newName = newName + listColumn[key] + "�";
					}
			    }
			}
		}
		$(this).val(newName);
	});
}

function removeKey(){
	
	var keyNameSelected = $("#keyList option:selected").text();
	var keyNameNextSelected = $("#keyList option:selected").next().text();
	$('#storeKeySave').find("input").each(function() {
		if($(this).val().split("�")[1] == keyNameSelected){
			$(this).remove();
		}
	});
	
	if($('#storeKeySave').find("input").val() == undefined){
		$("#keyList").attr('disabled', true);
		$('#keyList').find('option').remove().end();
		$("#keyname").attr('disabled', true);
		$("#keyname").val("");
		$("#keytype").attr('disabled', true);
		$("#keyfields").attr('disabled', true);
		$('#keyfields').find('option').remove().end();
		$("#keyavail").attr('disabled', true);
		$('#keyavail').find('option').remove().end();
		$("#flgRemovekey").val(1);
	}else{
		resetDataStoreKey();
		changeKeyByName(keyNameNextSelected);
	}
}

function setKeyType(){
	
	if(addKey()) {
		if($("#currentKey").val() == 1){
			$("#settingKey").modal("hide");
		}
		return;
	}
	
	saveDataColumn();
	var listColumn = {};
	
	var index = 0;
	
	$('#storeKeySave').find("input").each(function() {
		
		var keyTypeArr = $(this).val().split("�");
		
		var keyName = keyTypeArr[1];
		
		for (var int = 2; int < keyTypeArr.length - 1; int++) {
			var columnKeyName = keyTypeArr[int].split("◘")[1];
			switch (parseInt(keyTypeArr[0])) {
			case KEYTYPE.PRIMARY:
				if (listColumn[columnKeyName] != undefined){
					listColumn[columnKeyName][4] = appendKeyType(KEYTYPE.PRIMARY, listColumn[columnKeyName]);
					listColumn[columnKeyName] = listColumn[columnKeyName]
				}else{
					listColumn[columnKeyName] = ["0","0","0","0","1"];
				}
				break;
			case KEYTYPE.INDEX:
				
				if (listColumn[columnKeyName] != undefined){
					listColumn[columnKeyName][1] = appendKeyType(KEYTYPE.INDEX, listColumn[columnKeyName]);
					listColumn[columnKeyName] = listColumn[columnKeyName]
				}else{
					listColumn[columnKeyName] = ["0","1","0","0","0"];
				}
				break;
			case KEYTYPE.UNIQUE:
				if (listColumn[columnKeyName] != undefined){
					listColumn[columnKeyName][2] = appendKeyType(KEYTYPE.UNIQUE, listColumn[columnKeyName]);
					listColumn[columnKeyName] = listColumn[columnKeyName]
				}else{
					listColumn[columnKeyName] = ["0","0","1","0","0"];
				}
				break;
			case KEYTYPE.FULLTEXT:
				if (listColumn[columnKeyName] != undefined){
					listColumn[columnKeyName][0] = appendKeyType(KEYTYPE.FULLTEXT, listColumn[columnKeyName]);
					listColumn[columnKeyName] = listColumn[columnKeyName]
				}else{
					listColumn[columnKeyName] = ["1","0","0","0","0"];
				}
				break;
			}
		}
		index++;
	});	
	$('#tmp_list_table').find("tbody tr").each(function() {
		
		var columnName = $(this).find("input[name$='.name']").val();
		var keyType = $(this).find("input[name$='.keyType']").val();
		
		if(listColumn.hasOwnProperty(columnName)){
			if(keyType.substring(3, 4) == '1'){
				listColumn[columnName][3] = "1";
			}
			$(this).find("input[name$='.keyType']").val(converArrToString(listColumn[columnName]));
			$(this).find("input[name$='.binKeyType']").val(parseInt(converArrToString(listColumn[columnName]), 2));
		}
		else{
			$(this).find("input[name$='.keyType']").val("00000");
			$(this).find("input[name$='.binKeyType']").val("0");
			$(this).find("input[name$='.autoIncrementFlag']").val("0");
		}
		
		var keyType = $(this).find("input[name$='.keyType']").val();
		if(keyType.substr(keyType.length - 1) == KEYTYPE.PRIMARY){
			  $(this).find("input[name$='.isMandatory']").prop('disabled', true);
			  $(this).find("input[name$='.isMandatory']").prop('checked', true);
		}else{
			var mandatoryFolowKey = $(this).find("input[name=mandatoryFolowKey]").val();
			$(this).find("input[name$='.isMandatory']").prop('disabled', false);
	//			  if(mandatoryFolowKey == "true"){
	//				  $(this).find("input[name$='.isMandatory']").prop('checked', true);
	//			  }else if(mandatoryFolowKey == "false"){
	//				  $(this).find("input[name$='.isMandatory']").prop('checked', false);
	//			  }
		}
	});
	
	$('#storeKeySave').find("input").each(function() {
		var keyInformation = $(this).val().split("�");
		if(keyInformation[2] == ""){
			$(this).remove();
		}
	});
	
	$('#storeKeyTemp').val($('#storeKeySave').html());
	$("#settingKey").modal("hide");
}

function converArrToString(arrName){
	var strName = "";
	
	for (var int = 0; int < arrName.length; int++) {
		strName = strName + arrName[int];
	}
	
	return strName;
}

function appendKeyType(type, value){
	
	switch (type) {
		case KEYTYPE.PRIMARY:
			return value[4] = "1";
		case KEYTYPE.INDEX:
			return value[1] = "1";
		case KEYTYPE.UNIQUE:
			return value[2] = "1";
		case KEYTYPE.FULLTEXT:
			return value[0] = "1";
	}
}

function updatePK(){
	checkUpdateFlg = false;
	$('#storeKeySave').find("input").each(function() {
		if(keyType == KEYTYPE.PRIMARY){
		
			checkUpdateFlg = false;
			var keyType = $("#keytype").val();
			var keyName = $("#keyname").val();
			var listColumn = "";
			
			$("#keyfields option").each(function(){
				listColumn = listColumn + $(this).val() + "�";
			});
		
			$(this).val(keyType + "�" +keyName +  "�" + listColumn );
			checkUpdateFlg =  true;
		}
	});
	return checkUpdateFlg;
}

function addKey() {
	var checkFlg = false;
	if ($("#flgRemovekey").val() == 1) {
		$("#keyList").attr('disabled', false);
		$("#keyname").attr('disabled', false);
		$("#keytype").attr('disabled', false);
		$("#keyfields").attr('disabled', false);
		$("#keyavail").attr('disabled', false);
		$('#keyavail').find('option').remove().end();
		$('#tmp_list_table').find("tbody tr").each(
			function() {
				var commonColumn = $(this).find("input[name$='.commonColumn']").val();
				var columnName = $(this).find("input[name$='.name']").val();

				if (columnName != "" && commonColumn != "1") {
					$("#keyavail").append($('<option>', {
						value : columnName,
						text : columnName
					}));
				}
			});
		
		$("#keytype").val(KEYTYPE.PRIMARY);
		$("#flgRemovekey").val(0);
	} else if (!checkNamePK()) {

		var keyType = $("#keytype").val();
		var keyName = $("#keyname").val();

		if ($('#keyfields').has('option').length == 0) {
			$.qp.showAlertModal(fcomMsgSource["err.sys.0104"].replace("{0}", $("#keytype option:selected").text()));
			return true;
		}

		if (checkDuplicateKeyName()) {
			return true;
		}

		if (keyName == "") {
			$.qp.showAlertModal(dbMsgSource['sc.tabledesign.0026']);
			return true;
		}

		if (hasPrimaryKey()
				&& $('#storeKeySave').find("input").val() != undefined
				&& keyType == KEYTYPE.PRIMARY) {

			var listNameKey = "";

			$('#storeKeySave').find("input").each(
							function() {
								var k = $(this).val().split("�")[0];
								var listColumn = "";

								$("#keyfields option").each(
										function() {
											listColumn = listColumn + $(this).val() + "�";
										});

								if (listColumn.length == 0) {
									$.qp.showAlertModal(fcomMsgSource["err.sys.0104"]
													.replace("{0}", $("#keytype option:selected").text()));
									return true;
								}

								if ($(this).val().split("�")[0] == 1) {
									if ($("#keyList option:selected").text() == $(this).val().split("�")[1]) {
										$(this).val(keyType + "�" + keyName + "�" + listColumn);
									} else {
										checkFlg = true;
										$.qp.showAlertModal(dbMsgSource["sc.tabledesign.0083"]);
										return true;
									}
								}
								
								listNameKey = listNameKey + $(this).val().split("�")[1];
							});

			var keyNameSelected = $("#keyList option:selected").text();
			$('#keyList option:contains(' + keyNameSelected + ')').text(keyName);
		} else {

			var listColumn = "";
			$("#keyfields option").each(function() {
				listColumn = listColumn + $(this).val() + "�";
			});

			if (listColumn.length == 0) {
				$.qp.showAlertModal(fcomMsgSource["err.sys.0104"].replace("{0}", $("#keytype option:selected").text()));
				return true;
			}

			var selectValues = {
				keyType : keyName
			};

			$.each(selectValues, function(key, value) {
				$('#keyList').append($("<option></option>").attr("value", keyType).text(value));
			});

			$("#keyList").val(keyType);

			var storeKeySave = $("#storeKeySave");

			var countKey = 0;
			$('#storeKeySave').find("input").each(function() {
				countKey++;
			});

			var htmlStoreKeySave = "";
			if (countKey == 0) {
				htmlStoreKeySave = "<input type=\"hidden\" name=\"listTableDesignKey["
						+ countKey
						+ "].strKeyItems\" value=\'"
						+ keyType
						+ "�"
						+ keyName + "�" + listColumn + "' />";
			} else {
				htmlStoreKeySave = "<input type=\"hidden\" name=\"listTableDesignKey["
						+ countKey
						+ "].strKeyItems\" value=\'"
						+ keyType
						+ "�"
						+ keyName + "�" + listColumn + "' />";
			}
			storeKeySave.append(htmlStoreKeySave);
		}
	} else {
		return true;
	}
}

function hasPrimaryKey(){
	var isPrimaryKey = false;
	$('#storeKeySave').find("input").each(function() {
		var keyInformation = $(this).val().split("�");
		if(KEYTYPE.PRIMARY == keyInformation[0]){
			isPrimaryKey = true;
		}
	});
	return isPrimaryKey;
}

function changeStoreKey(event){
	var keyNameSelected = $( "#keyList option:selected" ).text();
	resetDataStoreKey();
	changeKeyByName(keyNameSelected);
}

function checkDuplicateKeyName(){
	
	// create current key on screen
	$("#currentKey").val(0);
	
	var checkFlg = false;
	$('#storeKeySave').find("input").each(function() {
		var keyName = $(this).val().split("�")[1];
		var keyType = $(this).val().split("�")[0];
		if(keyName == $("#keyname").val() && $("#keytype").val() != KEYTYPE.PRIMARY){
			if($("#keytype").val() == keyType){
				$("#currentKey").val(1);
				currentKey = 1;
			}
			checkFlg = true;
		}
		
	});
	if(checkFlg){
		if(currentKey != 1){
			$.qp.showAlertModal(fcomMsgSource['err.sys.0036'].replace("{0}", dbMsgSource["sc.databasedesign.0117"]));
		}
		return true;
	}
}

function changeKeyByName(name){
	
	$("#keyList").find('option').remove().end();
	processChangeFiels();
	var index = 0;
	
	$('#storeKeySave').find("input").each(function() {
		$(this).attr("name", "listTableDesignKey["+index+"].strKeyItems");
		index++;
		var keyNameArr = $(this).val().split("�");
		var keyType = keyNameArr[0];
		var keyName = keyNameArr[1];
		var selectValues = { keyType : keyName};
		
		$.each(selectValues, function(key, value) {   
		     $('#keyList')
		         .append($("<option></option>")
		         .attr("value",keyType)
		         .text(value)); 
		});
		
		processRemoveFiels(keyNameArr);
		
		var newKeyName = "";
		for (var int = 0; int < keyNameArr.length - 1; int++) {
			newKeyName = newKeyName + keyNameArr[int] + "�";
		}
		
		$(this).val(newKeyName);
		if(name == keyName){
			$("#keyfields").find('option').remove().end();
			for (var int = 2; int < keyNameArr.length - 1; int++) {
				$("#keyfields").append($('<option>', {value:keyNameArr[int], text: keyNameArr[int]}));
			}
			$("#keyname").val(keyName);
			$("#keytype").val(keyNameArr[0]);
		}
		
	});
	
	$("#keyList").find("option").filter(function(){
	      return ($(this).text() == name)
	}).prop('selected', true);
	
	var columnName  = "";
	var listKeyfields = "";
	$("#keyavail").find('option').remove().end();
	
	$('#tmp_list_table').find("tbody tr").each(function() {
		var commonColumn = $(this).find("input[name$='.commonColumn']").val();
		var name = $(this).find("input[name$='.name']").val();
		if(name != "" && commonColumn != "1"){
			columnName = columnName + $(this).find("input[name$='.name']").val() + "�";
		}
	});
	
	$("#keyfields option").each(function(){
		listKeyfields = listKeyfields + $(this).val() + "�";
	});
	
	var columnNameArr = columnName.split("�");
	var listKeyfields = listKeyfields.split("�");
	var checkFlg = true;
	
	for (var int = 0; int < columnNameArr.length - 1; int++) {
		checkFlg = true;
		for (var int2 = 0; int2 < listKeyfields.length - 1; int2++) {
			col = listKeyfields[int2];
			if(col == columnNameArr[int]){
				checkFlg = false;
			}
		}
		if(checkFlg){
			$("#keyavail").append($('<option>', {value:columnNameArr[int], text: columnNameArr[int]}));
		}
	}
}

function processChangeFiels(){
	
	// Get new fiels name
	var columnName = [];
	$('#tmp_list_table').find("tbody tr").each(function() {
		columnName.push($(this).find("input[name$='.name']").val());
	});
	
	// Change Field key Setting
	$('#storeKeySave').find("input").each(function() {
		var keyInformation = $(this).val().split("�");
		for (var int = 0; int < columnName.length -1 ; int++) {
			var flg = true;
			for (var int2 = 2; int2 < keyInformation.length; int2++) {
				if(keyInformation[int2] == columnName[int]){
					flg = false;
					break;
				}
			}
			if(flg){
				
			}
		}
	});
}

function processRemoveFiels(columnKeyArr){
	var listKeyAvai = "";
	
	$('#tmp_list_table').find("tbody tr").each(function() {
		listKeyAvai = listKeyAvai + $(this).find("input[name$='.name']").val() + "�";
	});
	
	var listKeyAvaiArr = listKeyAvai.split("�");
	var checkFlg = false;
	
	for (var int = 2; int < columnKeyArr.length - 1; int++) {
		checkFlg = true;
		for (var int2 = 0; int2 < listKeyAvaiArr.length - 1; int2++) {
			if(columnKeyArr[int] == listKeyAvaiArr[int2]){
				checkFlg = false;
				break;
			}
		}
		
		if(checkFlg){
			columnKeyArr.splice(int,1);
			int = 2;
		}
	}
}

function initPKSetting(){
	
	$("#keyavail").find('option').remove().end();
	$("#keyList").find('option').remove().end();
	$("#keyfields").find('option').remove().end();
	$("#keyname").val("");
	$("#keytype").val(KEYTYPE.PRIMARY);
}

function toLeftClick(){
	
	var selectedField = $("#keyavail option:selected" );
	$("#keyfields").append(selectedField );
	var keyName = $("#keyname").val();
	var keyType = $("#keytype").val();
	
	$('#storeKeySave').find("input").each(function() {
		var listColumn = "";
		$("#keyfields option").each(function(){
			listColumn = listColumn + $(this).val() + "�";
		});
		var keyInform = $(this).val().split("�");
		if(keyType == keyInform[0] && keyName == keyInform[1]){
			$(this).val(keyType + "�" +keyName +  "�" + listColumn );
		}
	});
}

function changeKeyList(event) {
	var key = event.keyCode || event.charCode;
	if (key == 38 || key == 39 || key == 37 || key == 40) {
		var keyNameSelected = $("#keyList option:selected").text();
		resetDataStoreKey();
		changeKeyByName(keyNameSelected);
	}
}

function checkNamePK(){
	var name = $('#keyname').val();
	
	if ($.qp.isNullOrEmpty(name)) {
		return true;
	}
	
	var keyNameLowerCase = name.toLowerCase();
	var keyNameReplace = keyNameLowerCase.replace(/\ /g, '_');
	
	/*var patt = new RegExp(/^[a-zA-Z][_0-9a-zA-Z]{0,}$/);
	var res = patt.test(keyNameReplace);*/
	
	if(!$.qp.validateIsCode(keyNameReplace)){
		$.qp.showAlertModal(fcomMsgSource['err.sys.0066'].replace("{0}", dbMsgSource["sc.databasedesign.0117"]));
		return true;
	}
	$('#keyname').val(keyNameReplace);
	return false;
}

function toRightClick(){
	
	var selectedField = $("#keyfields option:selected" );
	$("#keyavail").append(selectedField );
	var keyName = $("#keyname").val();
	var keyType = $("#keytype").val();
	$('#storeKeySave').find("input").each(function() {
		var listColumn = "";
		$("#keyfields option").each(function(){
			listColumn = listColumn + $(this).val() + "�";
		});
		var keyInform = $(this).val().split("�");
		if(keyType == keyInform[0] && keyName == keyInform[1]){
			$(this).val(keyType + "�" +keyName +  "�" + listColumn );
		}
	});
}