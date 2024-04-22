var listReservedWords = [];
$(document).ready(function() {
	
	$.qp.initialCatAutocomplete($("#dialogAdvanceSetting").find("#sqlDesignOutputOptionValue").find("input[name=sqlOutputOptionLabelAutocomplete]"));
	$.qp.initialCatAutocomplete($("#dialogAdvanceSetting").find("#sqlDesignOutputDisplayValue").find("input[name=sqlOutputOptionValueAutocomplete]"));
	$(".out-focus-lower").focusout(function() {
		var lowerVal = $(this).val().toLowerCase();
		$(this).val(lowerVal);
	});
	$('#settingKey').click(function() {
		saveDataColumn();
	});
	
	$('#close-primary-key-button').on('click', function(e){
		$('#storeKeySave').html($('#storeKeyTemp').val());
	});
	
	$('#prepareData').on('click', function(e){
//		var listReservedWords = $("#listReservedWords").val();
//
//		// Validate table code
//		if(isInArray($('#tableCode').val(), listReservedWords)){
//			alert(fcomMsgSource['err.sys.0130'].replace("{0}",dbMsgSource['sc.tabledesign.0002']));
//			return false;
//		}
//		
//		$("flagRegister").val(1);
	});
	
	
	
	$('#keyname').on('focusout', function(e){
		changeNameKey($('#keyname').val());
	});
	
	$('#tableName').on('focusout', function(e){
		var rowCount = $('#tmp_list_table tbody tr').length;
		var tableName = $('#tableName').val();
		var tableCode = $('#tableCode').val();
		if(tableCode != ""){
			var keyNameLowerCase = tableCode.toLowerCase();
			var keyNameReplace = keyNameLowerCase.replace(/\ /g, '_');
			var patt = new RegExp(/^[a-zA-Z][_0-9a-zA-Z]{0,}$/);
			var res = patt.test(keyNameReplace);
			
			if(res){
				if(rowCount == "0"){
					$.qp.addRowJSByLinkEx($("#addRowTable"));
					var $row = $("#tmp_list_table").find(' tbody tr:first');
					$('#tableName').val(tableName.toDashDB().firstTitle());
					$row.find("input[name$='.name']").val(tableName.toDashDB().firstTitle() + " Id");
					$row.find("input[name$='.code']").val(tableName.toDatabaseCode() + "_id");
					$row.find("select[name$='.dataType']").val(14);
					$row.find("input[name$='.groupBaseTypeId']").val(2);
					$row.find("input[name$='.dataTypeFlg']").val(0);
					$row.find("input[name$='.maxlength']").prop('readonly', true);
					$row.find("input[name$='.maxlength']").val("");
					$row.find("input[name$='.autoIncrementFlag']").val(1);
					$row.find("input[name$='.isMandatory']").prop('checked', true);
					$row.find("input[name$='.isMandatory']").prop('disabled', true);
					$row.find("input[name$='.keyType']").val("00001");
					$row.find("input[name$='.binKeyType']").val("1");
					$row.find("input[name$='.itemSeqNo']").val("0");
					$row.find("input[name$='.indexRow']").val("0");
					$("#settingKey").find("#storeKeySave").html("");
					$("#settingKey").find("#storeKeySave").append("<input type=\"hidden\" name=\"listTableDesignKey[0].strKeyItems\" value=\"1�pk_"+tableCode+"�0◘"+tableName+" Id�\">");
					$.qp.initialConventionNameCode()
				}
			}
		}
	});
	
	$('#tableCode').on('focusout', function(e){
		var rowCount = $('#tmp_list_table tbody tr').length;
		var tableCode = $('#tableCode').val();
		
		var tableName = $('#tableName').val();
		
		
		if(tableCode != ""){
			var keyNameLowerCase = tableCode.toLowerCase();
			var keyNameReplace = keyNameLowerCase.replace(/\ /g, '_');
			var patt = new RegExp(/^[a-zA-Z][_0-9a-zA-Z]{0,}$/);
			var res = patt.test(keyNameReplace);
			
			if (tableName == '') {
				tableName = tableCode.toDashDB().firstTitle();
			}
				
			
			if(res){
				if(rowCount == "0"){
					$.qp.addRowJSByLinkEx($("#addRowTable"));
					var $row = $("#tmp_list_table").find(' tbody tr:first');
					$row.find("input[name$='.name']").val(tableCode.toDashDB().firstTitle() + " Id");
					$row.find("input[name$='.code']").val(tableCode.toDatabaseCode() + "_id");
					$row.find("select[name$='.dataType']").val(14);
					$row.find("input[name$='.groupBaseTypeId']").val(2);
					$row.find("input[name$='.dataTypeFlg']").val(0);
					$row.find("input[name$='.maxlength']").prop('readonly', true);
					$row.find("input[name$='.maxlength']").val("");
					$row.find("input[name$='.autoIncrementFlag']").val(1);
					$row.find("input[name$='.isMandatory']").prop('checked', true);
					$row.find("input[name$='.isMandatory']").prop('disabled', true);
					$row.find("input[name$='.keyType']").val("00001");
					$row.find("input[name$='.binKeyType']").val("1");
					$row.find("input[name$='.itemSeqNo']").val("0");
					$row.find("input[name$='.indexRow']").val("0");
					$("#settingKey").find("#storeKeySave").html("");
					$("#settingKey").find("#storeKeySave").append("<input type=\"hidden\" name=\"listTableDesignKey[0].strKeyItems\" value=\"1�pk_"+tableCode+"�0◘"+tableName+" Id�\">");
					$.qp.initialConventionNameCode()
				}
			}
		}
	});
	
	$('a.codelistRegis').click(function(){
		window.open(this.href);
		return false;
	});
	
	$('a.sqlRegis').click(function(){
		window.open(this.href);
		return false;
	});
	
	$('a.colDefaultValue').click(function(){
		//window.open(this.href);
		return false;
	});

	var listFK = {};
	var count = 0;
	$('#tbl_fk_list').find("tbody tr").each(function() {
		var columnName = $(this).find("input[name$='.fromColumnName']").val();
		$('#tmp_list_table').find("tbody tr").each(function() {
			var columnNameTable = $(this).find("input[name$='.name']").val();
			if(columnName == columnNameTable){
				var keyType = $(this).find("input[name$='.keyType']").val();
				$(this).find("input[name$='.keyType']").val(keyType.replaceAt(3, "1"));
				$(this).find("input[name$='.binKeyType']").val(parseInt(keyType.replaceAt(3, "1"), 2));
			}
		});
		
		listFK[count] = {
			foreignKeyCode : $(this).find("input[name$='.foreignKeyCode']").val(),
			fromColumnName : $(this).find("input[name$='.fromColumnName']").val(),
			fromColumnId : $(this).find("input[name$='.fromColumnId']").val(),
			indexRow : $(this).find("input[name$='.indexRow']").val(),
			foreignKeyType : $(this).find("input[name$='.foreignKeyType']").val(),
			toTableCode: $(this).find("input[name$='.toTableCode']").val(),
			toTableName: $(this).find("input[name$='.toTableName']").val(),
			toTableId: $(this).find("input[name$='.toTableId']").val(),
			toColumnCode : $(this).find("input[name$='.toColumnCode']").val(),
			toColumnName : $(this).find("input[name$='.toColumnName']").val(),
			toColumnId : $(this).find("input[name$='.toColumnId']").val()
		};
		count++;
	});
	
	$("#storeForeignKeyTemp").val(JSON.stringify(listFK));

	$('#tmp_list_table').find("tbody tr").each(function() {
		var currentRow = $(this);
		// Set current data type
		var dataTypeRow = $(this).find("select[name$='.dataType']");
		dataTypeRow.on('click', function(e){
			$("#groupBaseTypeTemp").val(dataTypeRow.find(":selected").attr("basetypegroup"));
			$("#datatypeflgTemp").val(dataTypeRow.find(":selected").attr("datatypeflg"));
			
		});
		
		var columnCode = $(this).find("input[name$='.code']");
		columnCode.on('focusout', function(e){
			$.qp.initialConventionNameCode(currentRow);
//			var lowerVal = $(this).val().toDash();
//			$(this).find("input[name$='.code']").val(lowerVal);
		});
	
	var columnName = $(this).find("input[name$='.name']");
	var indexRowTable = $(this).find("input[name$='.indexRow']").val();
	columnName.on('focusout', function(e){
		resetDataStoreKey();
		$('#storeKeyTemp').val($('#storeKeySave').html());
		if($("#storeKeySave").find($('input:first-child')).val() != undefined){
			var hasKey = $("#storeKeySave").find($('input:first-child')).val().split("�")[1];
			changeKeyByName(hasKey);
		}
		saveDataColumn();
		
		// Change name and code Foreign key
		$('#tbl_fk_list').find("tbody tr").each(function() {
			var indexRowFK = $(this).find("input[name$='.indexRow']").val();
			if(indexRowFK == indexRowTable){
				$(this).find("input[name$='.fromColumnName']").val($(columnName).val());
			}
			var listKey = [];
				
			if($("#storeForeignKeyTemp").val() != ""){
				listKey = JSON.parse($("#storeForeignKeyTemp").val());
			}
			
			for (var key in listKey) {
				if (listKey.hasOwnProperty(key)) {
					if(listKey[key].indexRow == indexRowFK){
						listKey[key].fromColumnName = $(columnName).val();
					}
				}
			}
			
			$("#storeForeignKeyTemp").val(JSON.stringify(listKey));
		});
		
		$("select[id$='.fromColumnCode']").find('option').remove().end();
			
			$('#tmp_list_table').find("tbody tr").each(function() {
				var columnName = $(this).find("input[name$='.name']").val();
				var columnCode = $(this).find("input[name$='.code']").val();
				if(columnName != ""){
					var selectValues = { columnCode : columnName};
					$.each(selectValues, function(key, value) { 
					 $("select[id$='.fromColumnCode']")
					 .append($("<option></option>")
					 .attr("value",columnCode)
					 .text(value)); 
					});
				}
			});
	});
});
	
var KEYTYPE = {
		PRIMARY : 1, 
		INDEX : 2,
		UNIQUE : 3,
		FULLTEXT : 4,
		CHAR : 5,
		CURRENCY : 6,
		BOOLEAN : 7,
		TIME : 8,
		DATETIME : 9,
		BINARY : 10
	};
	//var start = new Date().getTime();
	init();
	//processingForChangeDataTypeNon();
//	var end = new Date().getTime();
//	var time = end - start;
//	alert('Execution time: ' + time);
	
	$("#maxTableDetailsRow").val($('#tmp_list_table tr').length - 1);
	
	var index = 0;
	$('#storeKeySave').find("input").each(function() {
		
		$(this).attr("name", "listTableDesignKey["+index+"].strKeyItems");
		index++;
	});
	
	$('#tmp_list_table').find("tbody").sortable({
		helper : function(e, ui) {
			ui.children().each(function() {
				$(this).width($(this).width());
			});
			return ui;
		},
		update : function(event, ui) {

			$.qp.reCalIndex($('#tmp_list_table'));
			$.qp.reArrayIndex($('#tmp_list_table'));
		},
		items : 'tr:not(tr[name="fix"])',
		cursor : 'move',
		handle : '.sortable'
	});
	
	$("#tableDesignForm").find("#plusOfForeignKey").click(function(){
		
		var projectId = $("#tableDesignForm").find("input[name='projectId']").val();
		var tableDesignId = $("#tableDesignForm").find("input[name='tableDesignId']").val();
		if (projectId) {
			$("#tableDesignForm").find("input[name$='.toTableIdAutocomplete']").each(function(){
				$(this).attr("arg02", projectId);
//				$(this).attr("arg03", tableDesignId);
			});
		}
		
		var lastRow = $('#tbl_fk_list').find("tbody tr:last");
		var count = 0;
		
		$('#tmp_list_table').find("tbody tr").each(function() {
			
			var columnName = $(this).find("input[name$='.name']").val();
			var columnCode = $(this).find("input[name$='.code']").val();
			var dataType = $(this).find("select[name$='.dataType'] option:selected").val();
			var commonColumn = $(this).find("input[name$='.commonColumn']").val();
			if(columnName != "" && commonColumn != "1"){
				var selectValues = { dataType : columnName};
				$.each(selectValues, function(key, value) { 
					lastRow.find("select[id$='.fromColumnCode']").append($("<option></option>").attr("value",columnCode).text(value)); 
				});
				count++;
			}
		});
		
		lastRow.find("input[name$='.indexRow']").val($('#tmp_list_table').find("tbody tr:first").find("input[name$='.indexRow']").val());
		lastRow.find("input[name$='.fromColumnName']").val($('#tmp_list_table').find("tbody tr:first").find("input[name$='.name']").val());
		lastRow.find("input[name$='.foreignKeyType']").val($('#tmp_list_table').find("select[name$='.dataType'] option:selected").attr("basetypegroup"));
//		var fkNameTemp = $('#tmp_list_table').find("tbody tr:first").find("input[name$='.code']").val().substring(0, 27);
		lastRow.find("input[name$='.foreignKeyCode']").val("fk_");
	});
	
	saveDataColumn();
	
	 $('#tmp_list_table').find("tbody tr").each(function() {
		var keyType = $(this).find("input[name$='.keyType']").val();
		if(keyType.substr(keyType.length - 1) == "1"){
			$(this).find("input[name$='.isMandatory']").prop('disabled', true);
			$(this).find("input[name$='.isMandatory']").prop('checked', true);
		}
	});
	 
	 setStatusRowWhenErr();
	 
	 
});

function saveDataColumn(){
	
	var listColumn = {};
	var countRow = 0;
	$('#tmp_list_table').find("tbody tr").each(function() {
		listColumn[$(this).find("input[name$='.indexRow']").val()] = $(this).find("input[name$='.name']").val();
	});
	
	var setIndexListKey = {};
	
	$('#storeKeySave').find("input").each(function() {
		
		var keyInformation = $(this).val().split("�");
		
		for (var int = 2; int < keyInformation.length - 1; int++) {
			for (var key in listColumn) {
				if (listColumn.hasOwnProperty(key)) {
					if(listColumn[key] == keyInformation[int]){
						keyInformation[int] = key + "◘" + keyInformation[int];
					}
			}
			}
		}
		
		$(this).val(keyInformation.join("�"));
	});
	$("#listValueKeyObjectType").val(JSON.stringify(setIndexListKey));
}

function changeDataType(event) {
	
	var currentRow = $(event.target).parents("tr");
	processingForChangeDataType(currentRow, false);
	var dataType = "";
	if(currentRow.find("select[name$='.dataType'] option:selected").attr("datatypeflg") == "0"){
		dataType = currentRow.find("select[name$='.dataType'] option:selected").attr("basetypegroup");
	}else if(currentRow.find("select[name$='.dataType'] option:selected").attr("datatypeflg") == "1"){
		dataType = currentRow.find("select[name$='.dataType'] option:selected").attr("basetypegroup");
	}
	var indexRowTable = currentRow.find("input[name$='.indexRow']").val();
	
	 $('#tbl_fk_list').find("tbody tr").each(function() {
		var indexRowFK = $(this).find("input[name$='.indexRow']").val();
		if(indexRowFK == indexRowTable){
			$(this).find("input[name$='.foreignKeyType']").val(dataType);
		}
	});
	
	var listKey = [];
	
	if($("#storeForeignKeyTemp").val() != ""){
		listKey = JSON.parse($("#storeForeignKeyTemp").val());
	}
	var indexRowFK = currentRow.find("input[name$='.indexRow']").val();
	for (var key in listKey) {
		if (listKey.hasOwnProperty(key)) {
			if(listKey[key].indexRow == indexRowFK){
				listKey[key].foreignKeyType = dataType;
			}
		}
	}
	$("#storeForeignKeyTemp").val(JSON.stringify(listKey));
}

function init() {
	var indexColumn = 0;
	$('#tmp_list_table').find("tbody tr").each(function() {
		processingForChangeDataType($(this), true);
	});
}

function processingForChangeDataType(currentRow, isInit){
	if(!isInit){
		valueOfTableDesignDetails(currentRow);
	}
	var groupId = currentRow.find("select[name$='.dataType'] option:selected").attr("basetypegroup");
	currentRow.find("input[name$='.groupBaseTypeId']").val(groupId);
	var datatypeflg = currentRow.find("select[name$='.dataType'] option:selected").attr("datatypeflg");
	currentRow.find("input[name$='.isMandatory']").prop("disabled", false);
	
	if(parseInt(datatypeflg) != 0){
		if(!isInit){
			
		}else{
			if(currentRow.find("input[name=mandatoryOfTable]").val() == "true"){
				currentRow.find("input[name$='.isMandatory']").prop('checked', true);
			}else{
				currentRow.find("input[name$='.isMandatory']").prop('checked', false);
			}
		}
		currentRow.find("input[name$='.maxlength']").prop("readonly", true);
		currentRow.find("input[name$='.decimalPart']").prop("readonly", true);
		currentRow.find("input[name$='.maxlength']").val(currentRow.find("select[name$='.dataType'] option:selected").attr("length"));
		currentRow.find("input[name$='.decimalPart']").val(currentRow.find("select[name$='.dataType'] option:selected").attr("precision"));
		currentRow.find("input[name$='.fmtCode']").val(currentRow.find("select[name$='.dataType'] option:selected").attr("validationrule"));
		currentRow.find("input[name$='.constrainsType']").val(currentRow.find("select[name$='.dataType'] option:selected").attr("constrainstype"));
		currentRow.find("input[name$='.datasourceId']").val(currentRow.find("select[name$='.dataType'] option:selected").attr("datasourceid"));
		currentRow.find("input[name$='.datasourceType']").val(currentRow.find("select[name$='.dataType'] option:selected").attr("datasourcetype"));
		currentRow.find("input[name$='.operatorCode']").val(currentRow.find("select[name$='.dataType'] option:selected").attr("operatorcode"));
		currentRow.find("input[name$='.userDefineValue']").val(currentRow.find("select[name$='.dataType'] option:selected").attr("userdefinevalue"));
		currentRow.find("input[name$='.supportOptionFlg']").val(currentRow.find("select[name$='.dataType'] option:selected").attr("supportoptionflg"));
		currentRow.find("input[name$='.maxVal']").val(currentRow.find("select[name$='.dataType'] option:selected").attr("maxvalue"));
		currentRow.find("input[name$='.minVal']").val(currentRow.find("select[name$='.dataType'] option:selected").attr("minvalue"));
		currentRow.find("input[name$='.defaultValue']").val(currentRow.find("select[name$='.dataType'] option:selected").attr("defaultvalue"));
		currentRow.find("input[name$='.codelistDefaultAutocomplete']").val(currentRow.find("select[name$='.dataType'] option:selected").attr("codelistdefaultautocomplete"));
		currentRow.find("input[name$='.codelistCodeAutocomplete']").val(currentRow.find("select[name$='.dataType'] option:selected").attr("codelistcodeautocomplete"));
		currentRow.find("input[name$='.sqlCodeAutocomplete']").val(currentRow.find("select[name$='.dataType'] option:selected").attr("sqlcodeautocomplete"));
		currentRow.find("input[name$='.dataTypeFlg']").val("1");
		
		switch(parseInt(groupId)) {
			case DATATYPE.INTEGER:
			case DATATYPE.DATE:
			case DATATYPE.DATETIME:
			case DATATYPE.TIME:
			case DATATYPE.BOOLEAN:
			case DATATYPE.BINARY:
			case DATATYPE.DECIMAL:
//				currentRow.find("input[name$='.maxlength']").val("");
//				currentRow.find("input[name$='.decimalPart']").val("");
				break;
			case DATATYPE.TEXT:
				if(currentRow.find("select[name$='.dataType'] option:selected").attr("primitiveid") == "3"){
					currentRow.find("input[name$='.decimalPart']").prop("readonly", true);
					currentRow.find("input[name$='.maxlength']").prop("readonly", true);
					currentRow.find("input[name$='.maxlength']").val("");
					break;
				}
			case DATATYPE.CHAR:
//				currentRow.find("input[name$='.decimalPart']").val("");
				break;
		}
		
	}else{
		currentRow.find("input[name$='.maxlength']").prop("readonly", false);
		currentRow.find("input[name$='.decimalPart']").prop("readonly", false);
		currentRow.find("input[name$='.dataTypeFlg']").val("0");
		
		switch(parseInt(groupId)) {
			case DATATYPE.TEXT:
				if(currentRow.find("select[name$='.dataType'] option:selected").val() == "3"){
					currentRow.find("input[name$='.decimalPart']").prop("readonly", true);
					currentRow.find("input[name$='.maxlength']").prop("readonly", true);
					currentRow.find("input[name$='.maxlength']").val("");
					break;
				}
			case DATATYPE.CHAR:
				console.log ("char or text");
				currentRow.find("input[name$='.decimalPart']").prop("readonly", true);
				currentRow.find("input[name$='.maxlength']").prop("readonly", false);
				if(!isInit){
					currentRow.find("input[name$='.maxlength']").val(initializeMaxLengthDefault(currentRow.find("select[name$='.dataType'] option:selected").val()));
				}else{
					if(currentRow.find("input[name$='.maxlength']").val() == ""){
						currentRow.find("input[name$='.maxlength']").val(initializeMaxLengthDefault(currentRow.find("select[name$='.dataType'] option:selected").val()));
					}
				}
				currentRow.find("input[name$='.decimalPart']").val(initializePrecisionDefault(currentRow.find("select[name$='.dataType'] option:selected").val()));
				break;
			case DATATYPE.INTEGER:
				console.log ("Integer");
				currentRow.find("input[name$='.decimalPart']").prop("readonly", true);
				currentRow.find("input[name$='.maxlength']").prop("readonly", true);
				
				//fix leng for some integer 
//				currentRow.find("input[name$='.maxlength']").val(initializeMaxLengthDefault(currentRow.find("select[name$='.dataType'] option:selected").val()));
//				currentRow.find("input[name$='.decimalPart']").val(initializePrecisionDefault(currentRow.find("select[name$='.dataType'] option:selected").val()));
				currentRow.find("input[name$='.maxlength']").val("");
				currentRow.find("input[name$='.decimalPart']").val("");
				break;
			case DATATYPE.DECIMAL:
				console.log ("DECIMAL");
				var dataType = currentRow.find("select[name$='.dataType'] option:selected").val();
				if(dataType == "16" || dataType == "17"){
					currentRow.find("input[name$='.decimalPart']").prop("readonly", true);
					currentRow.find("input[name$='.maxlength']").prop("readonly", true);
					currentRow.find("input[name$='.maxlength']").val("");
					currentRow.find("input[name$='.decimalPart']").val("");
				}else{
					currentRow.find("input[name$='.decimalPart']").prop("readonly", false);
					currentRow.find("input[name$='.maxlength']").prop("readonly", false);
					if(!isInit){
						currentRow.find("input[name$='.maxlength']").val(initializeMaxLengthDefault(currentRow.find("select[name$='.dataType'] option:selected").val()));
						currentRow.find("input[name$='.decimalPart']").val(initializePrecisionDefault(currentRow.find("select[name$='.dataType'] option:selected").val()));
					}
				}
				break;
			case DATATYPE.DATE:
				console.log ("Date");
				currentRow.find("input[name$='.decimalPart']").prop("readonly", true);
				currentRow.find("input[name$='.maxlength']").prop("readonly", true);
//				currentRow.find("input[name$='.maxlength']").val(initializeMaxLengthDefault(currentRow.find("select[name$='.dataType'] option:selected").val()));
//				currentRow.find("input[name$='.decimalPart']").val(initializePrecisionDefault(currentRow.find("select[name$='.dataType'] option:selected").val()));
				currentRow.find("input[name$='.maxlength']").val("");
				currentRow.find("input[name$='.decimalPart']").val("");
				currentRow.find("input[name$='.autoIncrementFlag']").val(0);
				break;
			case DATATYPE.DATETIME:
				console.log ("datetime");
				currentRow.find("input[name$='.decimalPart']").prop("readonly", true);
				currentRow.find("input[name$='.maxlength']").prop("readonly", true);
//				currentRow.find("input[name$='.maxlength']").val(initializeMaxLengthDefault(currentRow.find("select[name$='.dataType'] option:selected").val()));
//				currentRow.find("input[name$='.decimalPart']").val(initializePrecisionDefault(currentRow.find("select[name$='.dataType'] option:selected").val()));
				currentRow.find("input[name$='.maxlength']").val("");
				currentRow.find("input[name$='.decimalPart']").val("");
				currentRow.find("input[name$='.autoIncrementFlag']").val(0);
				break;
			case DATATYPE.TIME:
				console.log ("Time");
				currentRow.find("input[name$='.decimalPart']").prop("readonly", true);
				currentRow.find("input[name$='.maxlength']").prop("readonly", true);
//				currentRow.find("input[name$='.maxlength']").val(initializeMaxLengthDefault(currentRow.find("select[name$='.dataType'] option:selected").val()));
//				currentRow.find("input[name$='.decimalPart']").val(initializePrecisionDefault(currentRow.find("select[name$='.dataType'] option:selected").val()));
				currentRow.find("input[name$='.maxlength']").val("");
				currentRow.find("input[name$='.decimalPart']").val("");
				currentRow.find("input[name$='.autoIncrementFlag']").val(0);
				break;
			case DATATYPE.CURRENCY:
				console.log ("CURRENCY");
				if(!isInit){
					currentRow.find("input[name$='.decimalPart']").prop("readonly", false);
					currentRow.find("input[name$='.maxlength']").prop("readonly", false);
					currentRow.find("input[name$='.maxlength']").val(initializeMaxLengthDefault(currentRow.find("select[name$='.dataType'] option:selected").val()));
					currentRow.find("input[name$='.decimalPart']").val(initializePrecisionDefault(currentRow.find("select[name$='.dataType'] option:selected").val()));
				}
				break;
			case DATATYPE.BOOLEAN:
			case DATATYPE.BINARY:
				console.log ("BINARY");
				currentRow.find("input[name$='.decimalPart']").prop("readonly", true);
				currentRow.find("input[name$='.maxlength']").prop("readonly", true);
//				currentRow.find("input[name$='.maxlength']").val(initializeMaxLengthDefault(currentRow.find("select[name$='.dataType'] option:selected").val()));
//				currentRow.find("input[name$='.decimalPart']").val(initializePrecisionDefault(currentRow.find("select[name$='.dataType'] option:selected").val()));
				currentRow.find("input[name$='.maxlength']").val("");
				currentRow.find("input[name$='.decimalPart']").val("");
				currentRow.find("input[name$='.autoIncrementFlag']").val(0);
				break;
			default:
				currentRow.find("input[name$='.decimalPart']").prop("readonly", true);
				currentRow.find("input[name$='.maxlength']").prop("readonly", true);
				currentRow.find("input[name$='.autoIncrementFlag']").val(0);
				break;
		}
	}
	
	if(currentRow.find("input[name$='.binKeyType']").val() == "1"){
		currentRow.find("input[name$='.isMandatory']").prop("disabled", true);
	}
	
	
}

/**
 * fix some max length for integer
 * @param baseType
 * @returns {Number}
 */
function initializeMaxLengthForInteger(groupBaseTypeId) {
	//fix leng for some integer
	var maxLength = 10;
	switch(parseInt(groupBaseTypeId)) {
		case 6://smallInt
			maxLength = 6;
			break;
		case 7://bigInt
		case 14://BigSerial
			maxLength = 20;
			break;
		default:
			maxLength = 10;//integer and serial
	}

	return maxLength;
}

function resetValueAdvanceSetting(curentTR){
	curentTR.find("input[name$='.constrainsType']").val("");
	curentTR.find("input[name$='.datasourceId']").val("");
	curentTR.find("input[name$='.datasourceType']").val("");
	curentTR.find("input[name$='.remark']").val("");
	curentTR.find("input[name$='.defaultValue']").val("");
	curentTR.find("input[name$='.operatorCode']").val("");
	curentTR.find("input[name$='.minVal']").val("");
	curentTR.find("input[name$='.maxVal']").val("");
	curentTR.find("input[name$='.codelistCodeAutocomplete']").val("");
	curentTR.find("input[name$='.codelistDefaultAutocomplete']").val("");
	curentTR.find("input[name$='.sqlCodeAutocomplete']").val("");
	curentTR.find("input[name$='.userDefineValue']").val("");
	curentTR.find("input[name$='.supportOptionFlg']").val("");
}

function changeProjectAC(obj){
	var value = $(obj.target).next("input[type=hidden]").val();
	var nextInput = $(obj.target).closest("tr").find("input[id='subjectAreaIdAutocompleteId']");
	
	var nextHidden = nextInput.next("input[type=hidden]");
	nextInput.val("");
	nextInput.attr("arg01",value);
	nextHidden.val("");
	nextInput.data("ui-autocomplete")._trigger("change");	
};

function changeProjectACRegis(obj){
	var value = $(obj.target).next("input[type=hidden]").val();
	
	var nextInput = $('#tbl_list_Subject').find("input[id$='.areaIdAutocompleteId']");
	var nextHidden = nextInput.next("input[type=hidden]");
	
	nextInput.val("");
	nextInput.attr("arg01",value);
	nextHidden.val("");
	nextInput.data("ui-autocomplete")._trigger("change");
	
	var nextInputFK = $('#tbl_fk_list').find("input[name$='.toTableIdAutocomplete']");
	var nextHiddenFK = nextInputFK.next("input[type=hidden]");
	nextInputFK.val("");
	nextInputFK.attr("arg02",value);
};

function changeTableCodeLabel(obj) {

	var scope = $(obj.target).closest('td');
	var tableDesignId = $(obj.target).val();
	
	if(tableDesignId == ''){
		$(scope).next().find('span').text('');
	} else {
		var tableCd = obj.item.output01;
		$(scope).next().find('span').text(tableCd);
	}
}

function removeOption(table,direction,row){
	row.find("select[name$='.dataType'] option").each(function() {
		if($(this).val() == ""){
			$(this).remove();
		}
	});
	
	row.find("select[name$='.groupBaseTypeId']").val(2);
}

function callback(table,direction,row){
	var projectId = $("input[name=projectId]").val();
	
	$(row).find("input[name$='areaIdAutocomplete']").attr("arg01",projectId);
}

//Remove row from List table
$.qp.removeRowJSTblDesign = function(fcomTableId, obj) {
	var table = $(obj).closest("table#" + fcomTableId);
	if(table!="undefined") {
		$(obj).closest("tr").remove();
		if (table.find("tr").size() == 0) {
			var tmpl = $("#" + $(table).attr("id") + "-template").tmpl();

			$(table).append(tmpl);
			$(tmpl).find(".input-datepicker").each(function() {// initial
				$.qp.initialDatePicker(this);
			});
			$(tmpl).find(".qp-input-datetimepicker-detail").each(function() {// initial
				$.qp.initialDateTimePickerDetail($(this));
			});
			$(tmpl).find(".qp-input-autocomplete").each(function() {// initial
				$.qp.initialAutocomplete($(this));
			});
			$(obj).find(".qp-input-autocomplete-test").each(function() {// initial
				$.qp.initialAutocompleteTest($(this));
			});
		}
		$.qp.initialAutoNumeric(obj);// initial Auto numeric
		$.qp.reCalIndex(table); // refresh row index (No.)
		$.qp.alternateRowColorInTable(fcomTableId); // alternate color
		$.qp.reArrayIndex(table);
		resetDataStoreKey();
		$('#storeKeyTemp').val($('#storeKeySave').html());
			if($("#storeKeySave").find($('input:first-child')).val() != undefined){
				var hasKey = $("#storeKeySave").find($('input:first-child')).val().split("�")[1];
				changeKeyByName(hasKey);
		}
		saveDataColumn();
	}
};

function setFlag() {
	$("#actionDelete").val(true);
	var path = CONTEXT_PATH + "/tabledesign/viewListAffectedChangeDesignDelete";
	$("#tableDesignForm").attr("method", "GET");
	$("#tableDesignForm").attr("action", path);
	$("#tableDesignForm").submit();
}

function setDefaultDatatype(curentRow){
	curentRow.find("select[name$='.dataType']").val(1);
	curentRow.find("input[name$='.groupBaseTypeId']").val(1);
	curentRow.find("input[name$='.dataTypeFlg']").val(0);
	curentRow.find("input[name$='.maxlength']").prop('readonly', false);
	curentRow.find("input[name$='.maxlength']").val(200);
}

function changeNameKey(name){
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

function submmitBack() {
	$("#actionDelete").val(true);
	var tableDesignId = $("#tableDesignForm").find("#tableDesignId").val();
	var path = CONTEXT_PATH + "/modify?tableDesignId=" + tableDesignId + "&mode=0";
	$("#tableDesignForm").attr("method", "GET");
	$("#tableDesignForm").attr("action", path);
	$("#tableDesignForm").submit();
}

function setIndexRow(table,direction,row){
	$("#tbl_list_Suport").html("");
	$(row).find("input[name$='.indexRow']").val($("#maxTableDetailsRow").val());
	$(row).find("input[name$='.itemSeqNo']").val($("#maxTableDetailsRow").val());
	$(row).find("input[name$='.dataTypeFlg']").val(0);
	$(row).find("input[name$='.isMandatory']").removeClass();
	$(row).find("input[name$='.isMandatory']").addClass("qp-input-checkbox-margin qp-input-checkbox");
	
	$("#maxTableDetailsRow").val(parseInt($("#maxTableDetailsRow").val()) + 1);
	
	row.find("select[name$='.dataType'] option").each(function() {
		if($(this).val() == ""){
			$(this).remove();
		}
	});
	
	row.find("input[name$='.code']").focusout(function() {
		var lowerVal = $(this).val().toLowerCase();
		$(this).val(lowerVal);
	});
	setDefaultDatatype(row);
	
	$.qp.initialConventionNameCode(row);
//	$.qp.initialConventionLowerCaseCode($(row));
//	$(row).find('.qp-convention-db-code-row').on('change', function() {
//		var code = $(this).closest("tr").find(".qp-convention-db-code-row");
//		$(code).val(($(this).val().toLowerCase().replace(/\ /g, '_')));
//	});
	
	$('#tmp_list_table').find("tbody tr").each(function() {
		
		// Set current data type
		var dataTypeRow = $(this).find("select[name$='.dataType']");
		var columnCode = $(this).find("input[name$='.code']");
		
		columnCode.on('focusout', function(e){
			$.qp.initialConventionNameCode(row);
//			$.qp.initialConventionLowerCaseCode($(row));
		});
		
		dataTypeRow.on('click', function(e){
			$("#groupBaseTypeTemp").val(dataTypeRow.find(":selected").attr("basetypegroup"));
			$("#datatypeflgTemp").val(dataTypeRow.find(":selected").attr("datatypeflg"));
		});
	});
	
	$(row).find('.qp-convention-db-name-row').on('change', function() {
//		var code = $(this).closest("tr").find(".qp-convention-db-code-row");
//		$(code).val(($(this).val().toLowerCase().replace(/\ /g, '_')));
		
		// Set list column code
		var listColumnCode = [];
		$('#tmp_list_table').find("tbody tr").each(function() {
			var columnCode = $(this).find("input[name$='.name']").val();
			listColumnCode.push(columnCode);
		});
		
		var indexRowTable = $(this).closest("tr").find("input[name$='.indexRow']").val();
		var columnNameTable = $(this).closest("tr").find("input[name$='.code']").val();
		// Change name and code Foreign key
		$('#tbl_fk_list').find("tbody tr").each(function() {
			var selectValues = { dataType : listColumnCode};
			var fromColumnCodeCbo = $(this).find("select[id$='.fromColumnCode']")
			fromColumnCodeCbo.find('option').remove().end();
			$.each(selectValues, function(key, value) {
				fromColumnCodeCbo.append($("<option></option>").attr("value",listColumnCode).text(value)); 
			});
			
			var indexRowFK = $(this).find("input[name$='.indexRow']").val();
			if(indexRowFK == indexRowTable){
				fromColumnCodeCbo.val($(columnNameTable).val());
			}
		});
	});
}

function isInArray(value, array) {
	return jQuery.inArray(value, array) > -1;
}

function processCommonColumn(obj){
	var rowCount = $('#tmp_list_table tbody tr').length;
	if(obj.checked){
		$("#tbl-list-Table").find("#commonColumn").val("1");
		for (var i = 0; i < 4; i++) {
			var newRow = addRowCommon($('#addRowTable'));
//			newRow.attr("name", "fix");
			newRow.find("input[name$='.commonColumn']").val(1);
			//newRow.find("#deleteRow").hide();
			
			newRow.find("input[name$='.isMandatory']").prop('checked', true);
//			newRow.find(".icon-sort").hide();
			if(i == 0){
				newRow.find("input[name$='.name']").val("Created By");
				newRow.find("input[name$='.code']").val("created_by");
				newRow.find("select[name$='.dataType']").val(7);
				newRow.find("input[name$='.defaultValue']").val(-1);
				processingForChangeDataType(newRow, false);
//				var dataType;
//				newRow.find("select[name$='.dataType'] option").each(function() {
//					var datatypeflg = $(this).attr("datatypeflg");
//					var primitiveid = $(this).attr("primitiveid");
//					if(datatypeflg == "0" && primitiveid == "7" ){
//						dataType = $(this);
//					}
//				});
//				newRow.find("select[name$='.dataType'] option").remove().end();
//				newRow.find("select[name$='.dataType'] optgroup").remove().end();
//				newRow.find("select[name$='.dataType']").append(dataType);
			}
			if(i == 1){
//				newRow.find(".icon-advance-setting").hide();
				newRow.find("input[name$='.name']").val("Created Date");
				newRow.find("input[name$='.code']").val("created_date");
				newRow.find("select[name$='.dataType']").val(12);
				newRow.find("input[name$='.displayType']").val("1");
				newRow.find("input[name$='.defaultType']").val("1");
				newRow.find("input[name$='.constrainsType']").val("0");
				
				processingForChangeDataType(newRow, false);
//				var dataType;
//				newRow.find("select[name$='.dataType'] option").each(function() {
//					var datatypeflg = $(this).attr("datatypeflg");
//					var primitiveid = $(this).attr("primitiveid");
//					if(datatypeflg == "0" && primitiveid == "12" ){
//						dataType = $(this);
//					}
//				});
//				newRow.find("select[name$='.dataType'] option").remove().end();
//				newRow.find("select[name$='.dataType'] optgroup").remove().end();
//				newRow.find("select[name$='.dataType']").append(dataType);
			}
			if(i == 2){
				newRow.find("input[name$='.name']").val("Updated By");
				newRow.find("input[name$='.code']").val("updated_by");
				newRow.find("select[name$='.dataType']").val(7);
				newRow.find("select[name$='.defaultValue']").val(-1);
				processingForChangeDataType(newRow, false);
//				var dataType;
//				newRow.find("select[name$='.dataType'] option").each(function() {
//					var datatypeflg = $(this).attr("datatypeflg");
//					var primitiveid = $(this).attr("primitiveid");
//					if(datatypeflg == "0" && primitiveid == "7" ){
//						dataType = $(this);
//					}
//				});
//				newRow.find("select[name$='.dataType'] option").remove().end();
//				newRow.find("select[name$='.dataType'] optgroup").remove().end();
//				newRow.find("select[name$='.dataType']").append(dataType);
			}
			if(i == 3){
//				newRow.find(".icon-advance-setting").hide();
				newRow.find("input[name$='.name']").val("Updated Date");
				newRow.find("input[name$='.code']").val("updated_date");
				newRow.find("select[name$='.dataType']").val(12);
				newRow.find("input[name$='.displayType']").val("1");
				newRow.find("input[name$='.defaultType']").val("1");
				newRow.find("input[name$='.constrainsType']").val("0");
				processingForChangeDataType(newRow, false);
//				var dataType;
//				newRow.find("select[name$='.dataType'] option").each(function() {
//					var datatypeflg = $(this).attr("datatypeflg");
//					var primitiveid = $(this).attr("primitiveid");
//					if(datatypeflg == "0" && primitiveid == "12" ){
//						dataType = $(this);
//					}
//				});
//				newRow.find("select[name$='.dataType'] option").remove().end();
//				newRow.find("select[name$='.dataType'] optgroup").remove().end();
//				newRow.find("select[name$='.dataType']").append(dataType);
			}
//			newRow.find("input,button,textarea,select").attr("readonly", true);
//			newRow.find("input[name$='.isMandatory']").prop('disabled', true);
			newRow.find("input[name$='.dataType']").prop('disabled', true);
		}
		
		$('#tmp_list_table').find("tbody tr").each(function() {
			var commonColumn = $(this).find("input[name$='.commonColumn']").val();
			var dataType = $(this).find("select[name$='.dataType']").val();
			if(commonColumn == '1'){
				$(this).find("input[name$='.constrainsType']").val(0);
				if(dataType == '7'){
					$(this).find("input[name$='.defaultValue']").val(-1);
					
				}else if(dataType == '12'){
					$(this).find("input[name$='.defaultValue']").val("now()");
				}
			}
		});
	}else{
		$("#tbl-list-Table").find("#commonColumn").val("0");
		$('#tmp_list_table').find("tbody tr").each(function() {
			var commonColumn = $(this).find("input[name$='.commonColumn']").val();
			if(commonColumn == '1'){
				$.qp.removeRowJS('tmp_list_table', $(this).find("#deleteRow"));
			}
		});
	}
}

function setStatusRowWhenErr(){
	if($("#tbl-list-Table").find("#commonColumn").val() == "1"){
		$("#tbl-list-Table").find("#commonColumnTable").prop('checked', true);
	}else if($("#tbl-list-Table").find("#commonColumn").val() == "0"){
		$("#tbl-list-Table").find("#commonColumnTable").prop('checked', false);
	}
	$('#tmp_list_table').find("tbody tr").each(function() {
		var commonColumn = $(this).find("input[name$='.commonColumn']").val();
		if(commonColumn == '1'){
//			$(this).attr("name", "fix");
//			$(this).find("input,button,textarea,select").attr("readonly", true);
//			$(this).find("input[name$='.isMandatory']").prop('disabled', true);
//			$(this).find("input[name$='.isMandatory']").prop('checked', true);
			$(this).find("input[name$='.commonColumn']").val(1);
			//$(this).find("#deleteRow").hide();
			
//			$(this).find(".icon-sort").hide();
//			$(this).find("td:eq(8)").removeClass("sortable ui-sortable-handle");
//			var dataType = $(this).find("select[name$='.dataType']").val();
//			if(dataType == "7"){
//				var dataType;
//				$(this).find("select[name$='.dataType'] option").each(function() {
//					var datatypeflg = $(this).attr("datatypeflg");
//					var primitiveid = $(this).attr("primitiveid");
//					if(datatypeflg == "0" && primitiveid == "7" ){
//						dataType = $(this);
//					}
//				});
//				$(this).find("select[name$='.dataType'] option").remove().end();
//				$(this).find("select[name$='.dataType'] optgroup").remove().end();
//				$(this).find("select[name$='.dataType']").append(dataType);
//			}else if(dataType == "12"){
//				var dataType;
//				$(this).find("select[name$='.dataType'] option").each(function() {
//					var datatypeflg = $(this).attr("datatypeflg");
//					var primitiveid = $(this).attr("primitiveid");
//					if(datatypeflg == "0" && primitiveid == "12" ){
//						dataType = $(this);
//					}
//				});
//				$(this).find("select[name$='.dataType'] option").remove().end();
//				$(this).find("select[name$='.dataType'] optgroup").remove().end();
//				$(this).find("select[name$='.dataType']").append(dataType);
//				$(this).find(".icon-advance-setting").hide();
//			}
		}
	});
}

function addRowCommon(link,tableId,templateId) {
	var $table = null;
	var $template = null;
	if(!tableId) {
		$table = $(link).closest("div").prev("table");
	} else {
		$table = $("#"+tableId);
	}
	if($table!="undefined") {
		var precheckFunction = $table.attr("data-ar-precheck");
		var passCheck = true;
		if(typeof window[precheckFunction]=="function"){
			passCheck = window[precheckFunction]($table,link,"add");
		}
		if(passCheck){
			if(!templateId) {
				$template = $("#" + $table.attr("id") + "-template");
			} else {
				$template = $("#"+templateId);
			}
			var ignoreRows = parseInt($table.attr("data-ar-irows"));
			if(isNaN(ignoreRows)) {
				ignoreRows = 0;
			}
			
			var maxRows = parseInt($table.attr("data-ar-mrows"));
			if(isNaN(maxRows)) {
				maxRows = 200;
			}

			if($table.find(">tbody>tr").length-ignoreRows < maxRows) {
				var callback = $table.attr("data-ar-callback");
				var newRow = $template.tmpl();
				$(newRow).attr("data-ar-templateid",$template.attr("id"));
				$table.append(newRow);
				$(newRow).find(".input-datepicker").each(function() {// initial
					$.qp.initialDatePicker(this);
				});
				$(newRow).find(".qp-input-datetimepicker-detail").each(function() {// initial
					$.qp.initialDateTimePickerDetail($(this));
				});
				$(newRow).find(".qp-input-autocomplete").each(function() {// initial
					$.qp.initialAutocomplete($(this));
				});
				$.qp.initialAutoNumeric(newRow);// initial Auto numeric
				$.qp.reCalIndex($table); // refresh row index (No.)
				$.qp.reArrayIndex($table);
				$.qp.initialConventionNameCode($(this));
				if(typeof window[callback]=="function"){
					window[callback]($table,'add',newRow);
				}
			}
		}
	}
	
	return newRow;
};

 function addRowJSByLinkEx(link,tableId,templateId) {
	var $table = null;
	var $template = null;
	if(!tableId) {
		$table = $(link).closest("div").prev("table");
	} else {
		$table = $("#"+tableId);
	}
	if($table!="undefined") {
		var precheckFunction = $table.attr("data-ar-precheck");
		var passCheck = true;
		if(typeof window[precheckFunction]=="function"){
			passCheck = window[precheckFunction]($table,link,"add");
		}
		if(passCheck){
			if(!templateId) {
				$template = $("#" + $table.attr("id") + "-template");
			} else {
				$template = $("#"+templateId);
			}
			var ignoreRows = parseInt($table.attr("data-ar-irows"));
			if(isNaN(ignoreRows)) {
				ignoreRows = 0;
			}
			
			var maxRows = parseInt($table.attr("data-ar-mrows"));
			if(isNaN(maxRows)) {
				maxRows = 200;
			}

			if($table.find(">tbody>tr").length-ignoreRows < maxRows) {
				var callback = $table.attr("data-ar-callback");
				var newRow = $template.tmpl();
				var tblInform = $("#tbl-list-Table");
				$(newRow).attr("data-ar-templateid",$template.attr("id"));
				
				if(tblInform.find("input[name=commonColumn]").val() == "1"){
					if($table.find(">tbody>tr").length >= 4){
						newRow.insertBefore($table.find(">tbody>tr").eq($table.find(">tbody>tr").length - 4));
					}else{
						$table.append(newRow);
					}
				}else{
					$table.append(newRow);
				}
				$(newRow).find(".input-datepicker").each(function() {// initial
					$.qp.initialDatePicker(this);
				});
				$(newRow).find(".qp-input-datetimepicker-detail").each(function() {// initial
					$.qp.initialDateTimePickerDetail($(this));
				});
				$(newRow).find(".qp-input-autocomplete").each(function() {// initial
					$.qp.initialAutocomplete($(this));
				});
				$.qp.initialAutoNumeric(newRow);// initial Auto numeric
				$.qp.reCalIndex($table); // refresh row index (No.)
				$.qp.reArrayIndex($table);
				$.qp.initialConventionNameCode($(this));
				if(typeof window[callback]=="function"){
					window[callback]($table,'add',newRow);
				}
			}
		}
	}
};

function clickDataType(obj){
	$("#autoIncrementFlagTemp").val($(obj).closest("tr").find("input[name$='.autoIncrementFlag']").val());
}

function submitForm() {
	/*var listReservedWords = $("#listReservedWords").val();*/
	var strAlert = "";
	
	// Validate table code
	if($('#tableCode').val() != ""){
		if(isInArray($('#tableCode').val(), listReservedWords)){
			strAlert = strAlert + fcomMsgSource['err.sys.0130'].replace("{0}",dbMsgSource['sc.tabledesign.0002']);
			strAlert = strAlert + "\n";
		}
	}
	
	// Validate column code
	var countRow = 1;
	$('#tmp_list_table').find("tbody tr").each(function() {
		var columnCode = $(this).find("input[name$='.code']").val();
		if(isInArray(columnCode, listReservedWords)){
			strAlert = strAlert + fcomMsgSource['err.sys.0131'].replace("{0}",dbMsgSource['sc.tabledesign.0014']).replace("{1}",dbMsgSource['sc.tabledesign.0022']).replace("{2}",countRow);
			strAlert = strAlert + "\n"
		}
		countRow++;
	});
	
	// Validate name primary key
	countRow = 1;
	$('#storeKeySave').find("input").each(function() {
		var keyInformation = $(this).val().split("�");
		if(isInArray(keyInformation[1], listReservedWords)){
			strAlert = strAlert + fcomMsgSource['err.sys.0134'].replace("{0}",dbMsgSource['sc.tabledesign.0077']).replace("{1}",countRow);
			strAlert = strAlert + "\n";
		}
		countRow++;
	});
	
	// Validate name foreign key
	countRow = 1;
	$('#tbl_fk_list').find("tbody tr").each(function() {
		var keyName = $(this).find("input[name$='.foreignKeyCode']").val();
		if(isInArray(keyName, listReservedWords)){
			strAlert = strAlert + fcomMsgSource['err.sys.0134'].replace("{0}",dbMsgSource['sc.tabledesign.0027']).replace("{1}",countRow);
			strAlert = strAlert + "\n";
		}
		countRow++;
	});

	if(strAlert.length > 0){
		$.qp.showAlertModal(strAlert);
		return false;
	}
}

function removeRowEx(thiz){
	var currentTR = $(thiz).closest("tr");
	var columnNamme = $(currentTR).find("input[name$='.name']").val();
	
	$('#tbl_fk_list').find("tbody tr").each(function() {
		var columnNameFK = $(this).find("input[name$='.fromColumnName']").val();
		if(columnNamme == columnNameFK){
			$(this).remove();
		}
	});
	
	var listKey = [];
	
	if($("#storeForeignKeyTemp").val() != ""){
		listKey = JSON.parse($("#storeForeignKeyTemp").val());
	}
	
	for (var key in listKey) {
		if (listKey.hasOwnProperty(key)) {
			if(listKey[key].fromColumnName == columnNamme){
				delete listKey[key];
			}
		}
	}
	$("#storeForeignKeyTemp").val(JSON.stringify(listKey));
}

function processingForChangeDataTypeNon(){
	$('#tmp_list_table').find("tbody tr").each(function() {
		var currentRow = $(this);
		var groupId = currentRow.find("select[name$='.dataType'] option:selected").attr("basetypegroup");
		currentRow.find("input[name$='.groupBaseTypeId']").val(groupId);
		var datatypeflg = currentRow.find("select[name$='.dataType'] option:selected").attr("datatypeflg");
		currentRow.find("input[name$='.isMandatory']").prop("disabled", false);	
		switch(parseInt(groupId)) {
			case DATATYPE.TEXT:
			case DATATYPE.CHAR:
				console.log ("char or text");
				currentRow.find("input[name$='.decimalPart']").prop("readonly", true);
				currentRow.find("input[name$='.maxlength']").prop("readonly", false);
				break;
			case DATATYPE.INTEGER:
				console.log ("Integer");
				currentRow.find("input[name$='.decimalPart']").prop("readonly", true);
				currentRow.find("input[name$='.maxlength']").prop("readonly", true);
				break;
			case DATATYPE.DECIMAL:
				console.log ("DECIMAL");
				var dataType = currentRow.find("select[name$='.dataType'] option:selected").val();
				if(dataType == "16" || dataType == "17"){
					currentRow.find("input[name$='.decimalPart']").prop("readonly", true);
					currentRow.find("input[name$='.maxlength']").prop("readonly", true);
				}else{
					currentRow.find("input[name$='.decimalPart']").prop("readonly", false);
					currentRow.find("input[name$='.maxlength']").prop("readonly", false);
				}
				break;
			case DATATYPE.DATE:
				console.log ("Date");
				currentRow.find("input[name$='.decimalPart']").prop("readonly", true);
				currentRow.find("input[name$='.maxlength']").prop("readonly", true);
				break;
			case DATATYPE.DATETIME:
				console.log ("datetime");
				currentRow.find("input[name$='.decimalPart']").prop("readonly", true);
				currentRow.find("input[name$='.maxlength']").prop("readonly", true);
				break;
			case DATATYPE.TIME:
				console.log ("Time");
				currentRow.find("input[name$='.decimalPart']").prop("readonly", true);
				currentRow.find("input[name$='.maxlength']").prop("readonly", true);
				break;
			case DATATYPE.CURRENCY:
				console.log ("CURRENCY");
				if(!isInit){
					currentRow.find("input[name$='.decimalPart']").prop("readonly", false);
					currentRow.find("input[name$='.maxlength']").prop("readonly", false);
				}
				break;
			case DATATYPE.BOOLEAN:
			case DATATYPE.BINARY:
				console.log ("BINARY");
				currentRow.find("input[name$='.decimalPart']").prop("readonly", true);
				currentRow.find("input[name$='.maxlength']").prop("readonly", true);
				break;
			default:
				currentRow.find("input[name$='.decimalPart']").prop("readonly", true);
				currentRow.find("input[name$='.maxlength']").prop("readonly", true);
				break;
		}
	});
	
	if(currentRow.find("input[name$='.binKeyType']").val() == "1"){
		currentRow.find("input[name$='.isMandatory']").prop("disabled", true);
	}
}