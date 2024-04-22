function showSettingForeignKeys() {
	var rowCount = $('#tmp_list_table tbody tr').length;
	if(rowCount == 0) return;
	
	$($("#settingForeignKeys")).modal(
		{ 
			show: true,
			closable: false,
			keyboard:false,
			backdrop:'static'
		}
	);
	
	var listColumnName = []; // Get all Column Name at time when click "Setting Foreign Key"
	var columnId = []; // Get All Column Id selected as Foreign Key
	
//	if($("#flgCheckLoadForeignkey").val() == ""){
//		var listFK = {};
//		var count = 0;
//		$('#tbl_fk_list').find("tbody tr").each(function() {
//			listFK[count] = {
//				foreignKeyCode : $(this).find("input[name$='.foreignKeyCode']").val(),
//				fromColumnName : $(this).find("input[name$='.fromColumnName']").val(),
//				fromColumnId   : $(this).find("input[name$='.fromColumnId']").val(),
//				indexRow       : $(this).find("input[name$='.indexRow']").val(),
//				foreignKeyType : $(this).find("input[name$='.foreignKeyType']").val(),
//				toTableCode    : $(this).find("input[name$='.toTableCode']").val(),
//				toTableName    : $(this).find("input[name$='.toTableName']").val(),
//				toTableId      : $(this).find("input[name$='.toTableId']").val(),
//				toColumnCode   : $(this).find("input[name$='.toColumnCode']").val(),
//				toColumnName   : $(this).find("input[name$='.toColumnName']").val(),
//				toColumnId     : $(this).find("input[name$='.toColumnId']").val()
//			};
//			count++;
//		});
//		
//		$("#storeForeignKeyTemp").val(JSON.stringify(listFK));
//	}
	
	// Clear Foreign key
	$('#tbl_fk_list').find("tbody").html("");
	var template = $('#tbl_fk_list-template').html();
	
	var keyTempHtml = "";
	
	var listKey = [];
	
	if($("#storeForeignKeyTemp").val() != ""){
		listKey = JSON.parse($("#storeForeignKeyTemp").val());
	}
	
	$('#tmp_list_table').find("tbody tr").each(function() {
		var commonColumn = $(this).find("input[name$='.commonColumn']").val();
		var columnName = $(this).find("input[name$='.name']").val();
		if(columnName != "" && commonColumn != "1"){
			listColumnName.push(columnName);
		}
	});
	
	for (var key in listKey) {
		if (listKey.hasOwnProperty(key)) {
			var table = $('#tbl_fk_list');
			var obj = $("#tbl_fk_list-template").tmpl();
			
			$(table).append(obj);
			$(obj).find(".input-datepicker").each(function() {// initial
				$.qp.initialDatePicker(this);
			});
			$(obj).find(".qp-input-datetimepicker-detail").each(function() {// initial
				$.qp.initialDateTimePickerDetail($(this));
			});
			$(obj).find(".qp-input-autocomplete").each(function() {// initial
				$.qp.initialAutocomplete($(this));
			});
			$.qp.initialAutoNumeric(obj);// initial Auto numeric
			$.qp.reCalIndex(table); // refresh row index (No.)
			$.qp.reArrayIndex(table);
			$.qp.alternateRowColorInTable($(table).attr("id")); // alternate color
			$.qp.initialConventionNameCode(obj);
			
			$(obj).find("input[name$='.foreignKeyCode']").val(listKey[key].foreignKeyCode);
			
			$(obj).find("input[name$='.fromColumnName']").val(listKey[key].fromColumnName);
			$(obj).find("input[name$='.fromColumnId']").val(listKey[key].fromColumnId);
			$(obj).find("input[name$='.indexRow']").val(listKey[key].indexRow);
			$(obj).find("input[name$='.foreignKeyType']").val(listKey[key].foreignKeyType);
			
			$(obj).find("input[name$='.toTableId']").val(listKey[key].toTableId);
			$(obj).find("input[name$='.toTableCode']").val(listKey[key].toTableCode);
			$(obj).find("input[name$='.toTableName']").val(listKey[key].toTableName);
			$(obj).find("input[name$='.toTableIdAutocomplete']").val(listKey[key].toTableName);
			$(obj).find("input[name$='.toTableIdAutocomplete']").attr("arg02", $("#currentProjectId").val());
			
			$(obj).find("input[name$='.toColumnCode']").val(listKey[key].toColumnCode);
			$(obj).find("input[name$='.toColumnId']").val(listKey[key].toColumnId);
			$(obj).find("input[name$='.toColumnName']").val(listKey[key].toColumnName);
			$(obj).find("input[name$='.toColumnIdAutocomplete']").val(listKey[key].toColumnName);
			$(obj).find("input[name$='.toColumnIdAutocomplete']").attr("arg01", listKey[key].toTableId);
		}
	}
	
	
	
	if($("#flgCheckLoadForeignkey").val() != ""){ // If not the fist click "Setting Foreign Key"
	
		$('#tbl_fk_list').find("tbody tr").each(function() {
			
			var flg = true;
			
			columnId.push($(this).find("select[name$='.fromColumnCode'] option:selected" ).text()); // Push column Name as Foreign Key
			
			for (var int = 0; int < listColumnName.length; int++) {
				
				// If Foreign Key equals one of list Column: BREAK
				if(listColumnName[int] == $(this).find("select[name$='.fromColumnCode'] option:selected" ).text()){
					flg = false;
				}
			}
			
			// If foreign Key not equals one of list Column. This mean, this column deleted
			if(flg){
				$(this).remove(); // Remove at client Foreign Key
			}
		});
	}
	
	var count = 0;
	
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
			count++;
		}
	});
		
	var flgColumn = 0; 
	
	$('#tbl_fk_list').find("tbody tr").each(function() {
		var columnName = $(this).find("input[name$='.fromColumnName']").val();
		$(this).find("select[name$='.fromColumnCode']").find("option").filter(function(){
			if($(this).text() == columnName){
				return $(this).text() == columnName;
			}
		}).prop('selected', true);
		flgColumn++;
	});
	$("#flgCheckLoadForeignkey").val("STATED");
	$("#fistClick").val("1");
}

function saveDialogSetting(){
	
	var dialogForm = $("#settingForeignKeys");
	var icon = dialogForm.data('icon');
	var value = "";
	
	//required for foreignKeyCode
	if(!validateRequiredFK(dialogForm,"foreignKeyCode",dbMsgSource['sc.tabledesign.0027'],true)) {
		return;
	}
	
	//required for foreignKeyCode
	if(!validateRequiredFK(dialogForm,"fromColumnName",dbMsgSource['sc.tabledesign.0028'],true)) {
		return;
	}
	
	//required for toTableIdAutocomplete
	if(!validateRequiredFK(dialogForm,"toTableIdAutocomplete",dbMsgSource['sc.tabledesign.0029'],true)) {
		return;
	}
	
	//required for toColumnIdAutocomplete
	if(!validateRequiredFK(dialogForm,"toColumnIdAutocomplete",dbMsgSource['sc.tabledesign.0030'],true)) {
		return;
	}
	
	// duplication for foreignKeyCode
	if(!validateDuplicationFK(dialogForm,"foreignKeyCode",dbMsgSource['sc.tabledesign.0027'],true)) {
		return;
	}
	
	/*if(checkDuplicateFK(dialogForm)){
		return;
	}*/
	
	/*var strAlert = "";
	var count = 1;
	$('#tbl_fk_list').find("tbody tr").each(function() {
		var foreignKeyType = $(this).find("input[name$='.foreignKeyType']").val();
		var toForeignKeyType = $(this).find("input[name$='.toForeignKeyType']").val();
		
		if(foreignKeyType != toForeignKeyType){
			strAlert += dbMsgSource["err.tabledesign.0002"].replace("{0}",count);
			strAlert += "\n";
		}
		count++;
	});
	
	if(strAlert != ""){
		alert(strAlert);
		return;
	}*/
	
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
			fromColumnId   : $(this).find("input[name$='.fromColumnId']").val(),
			indexRow       : $(this).find("input[name$='.indexRow']").val(),
			foreignKeyType : $(this).find("input[name$='.foreignKeyType']").val(),
			toTableCode    : $(this).find("input[name$='.toTableCode']").val(),
			toTableName    : $(this).find("input[name$='.toTableName']").val(),
			toTableId      : $(this).find("input[name$='.toTableId']").val(),
			toColumnCode   : $(this).find("input[name$='.toColumnCode']").val(),
			toColumnName   : $(this).find("input[name$='.toColumnName']").val(),
			toColumnId     : $(this).find("input[name$='.toColumnId']").val()
		};
		count++;
	});
	
	if(count == 0){
		$('#tmp_list_table').find("tbody tr").each(function() {
			var keyType = $(this).find("input[name$='.keyType']").val();
			$(this).find("input[name$='.keyType']").val(keyType.replaceAt(3, "0"));
			$(this).find("input[name$='.binKeyType']").val(parseInt(keyType.replaceAt(3, "0"), 2));
		});
	}
	
	$("#storeForeignKeyTemp").val(JSON.stringify(listFK));
	
	$("#settingForeignKeys").modal("hide");
}

String.prototype.replaceAt=function(index, character) {
    return this.substr(0, index) + character + this.substr(index+character.length);
}

function checkDuplicateFK(dialogForm){
//	var flgDuplicateFromColumn  = false;
	var flgDuplicateToTable  = false;
	var flgDuplicateToColumn  = false;
//	if(!validateDuplicationFK(dialogForm,"fromColumnName",dbMsgSource['sc.tabledesign.0027'],true)) {
//		flgDuplicateFromColumn = true;
//	}
	if(!validateDuplicationFK(dialogForm,"toTableName",dbMsgSource['sc.tabledesign.0027'],false)) {
		flgDuplicateToTable  = true;
	}
	if(!validateDuplicationFK(dialogForm,"toColumnId",dbMsgSource['sc.tabledesign.0027'],false)) {
		flgDuplicateToColumn  = true;
	}
	
	if(flgDuplicateToTable && flgDuplicateToColumn){
		$.qp.showAlertModal(fcomMsgSource['err.sys.0036'].replace("{0}",dbMsgSource['sc.tabledesign.0056']))
		return true;
	}
}

function validateDuplicationFK(modal,inputName,inputLabel, flagCheck) {
	var $Inputs = $(modal).find("input[name$='."+inputName+"']");
	var messages="";
	for(var i=$Inputs.length-1;i>0;i--){
		for(var j=0;j<i;j++){
			if($.trim($Inputs.eq(i).val()) == $.trim($Inputs.eq(j).val())){
				messages +="\r\n" + fcomMsgSource['err.sys.0041'].replace("{0}",inputLabel).replace("{1}",i+1);
				break;
			}
		}
	}

	if(messages!="") {
		$($Inputs).closest(".modal-body").find("a[href='#"+$Inputs.closest(".tab-pane").attr("id")+"']").tab("show");
		setFocus(messages);
		if(flagCheck){
			$.qp.showAlertModal(setDisplayMessage(messages));
		}
		return false;
	}
	return true;
}

function setFocus(messages){
	var indexRow = parseInt(messages.match(/\d+/)[0]);
	var numberRow = 1;
	$('#tbl_fk_list').find("tbody tr").each(function() {
		if(numberRow == indexRow){
			$(this).focus();
		}
		numberRow++;
	});
}

function setDisplayMessage(messages){
	var indexRow = parseInt(messages.match(/\d+/)[0]);
	var numberRow = 1;
	var keyName = "";
	$('#tbl_fk_list').find("tbody tr").each(function() {
		if(numberRow == indexRow){
			keyName = $(this).find("input[name$='.foreignKeyCode']").val();
		}
		numberRow++;
	});
	
	return messages.replace('Name', '');
}

function validateRequiredFK(modal,inputName,inputLabel,isList) {
	var $Inputs = isList?$(modal).find("input[name$='."+inputName+"']"):$(modal).find("input[name$='."+inputName+"']").first();
	var messages="";
	var focusInput = null;
	$Inputs.each(function(index){
		if($.trim($(this).val())==''){
			if(!isList){
				if(focusInput==null) {
					focusInput = this;
				}
				messages += fcomMsgSource['err.sys.0025'].replace("{0}",inputLabel);
			} else {
				if(index>0) {
					messages+="\r\n";
				}
				if(focusInput==null) {
					focusInput = this;
				}
				messages += fcomMsgSource['err.sys.0077'].replace("{0}",inputLabel).replace("{1}",index+1);
			}
		}
	});
	if(messages!="") {
		$Inputs.closest(".modal-body").find("a[href='#"+$Inputs.closest(".tab-pane").attr("id")+"']").tab("show");
		setFocus(messages);
		$.qp.showAlertModal(messages);
		return false;
	}
	return true;
}

function addRow(table,direction,row){
	var projectId = $("input[name=projectId]").val();
	
	$(row).find("input[name$='areaIdAutocomplete']").attr("arg01",projectId);
}

function selectColumnName(event){
	var fromColumnName = $(event.target).parents("tr").find("select[name$='.fromColumnCode']").find("option:selected").text();
	$(event.target).parents("tr").find("input[name$='.fromColumnName']").val(fromColumnName);
	$('#tmp_list_table').find("tbody tr").each(function() {
		var dataTypeTbl = $(this).find("select[name$='.dataType'] option:selected").attr("basetypegroup");
		var columnNameTbl = $(this).find("input[name$='.name']").val();
		var indexRowTbl = $(this).find("input[name$='.indexRow']").val();
		
		if(fromColumnName == columnNameTbl){
			$(event.target).parents("tr").find("input[name$='.foreignKeyType']").val(dataTypeTbl);
			$(event.target).parents("tr").find("input[name$='.indexRow']").val(indexRowTbl);
		}
	});
	$(event.target).parents("tr").find("input[name$='.foreignKeyCode']").val(getForeignKeyName(event).substring(0, 31));
}

function getForeignKeyName(event){
//	var fromColumnName = $(event.target).parents("tr").find("select[name$='.fromColumnCode']").find("option:selected");
	return "fk_" + $(event.target).parents("tr").find("input[name$='.toTableCode']").val() + "_" + $(event.target).parents("tr").find("input[name$='.toColumnCode']").val();
}

function changeTableAC(obj){
	
	var value = $(obj.target).next("input[type=hidden]").val();
	var nextInput = $(obj.target).closest("tr").find("input[name$='.toColumnIdAutocomplete']");
	
	if(obj.item != undefined){
		$(obj.target).closest("tr").find("input[name$='.toTableName']").val(obj.item.optionLabel);
		$(obj.target).closest("tr").find("input[name$='.toTableCode']").val(obj.item.output01);
		if($(obj.target).parents("tr").find("input[name$='.toTableCode']").val() != undefined){
			$(obj.target).parents("tr").find("input[name$='.foreignKeyCode']").val("fk_" + $(obj.target).parents("tr").find("input[name$='.toTableCode']").val().substring(0, 31));
		}
	}
	
	var nextHidden = nextInput.next("input[type=hidden]");
	nextInput.val("");
	nextInput.attr("arg01",value);
	nextHidden.val("");
	if(nextInput.data("ui-autocomplete") != undefined){
		nextInput.data("ui-autocomplete")._trigger("change");	
	}
};

function changeColumnAC(obj){
	if(obj.item != undefined){
		$(obj.target).closest("tr").find("input[name$='.toColumnCode']").val(obj.item.output02);
		$(obj.target).closest("tr").find("input[name$='.toColumnName']").val(obj.item.optionLabel);
		$(obj.target).parents("tr").find("input[name$='.foreignKeyCode']").val(getForeignKeyName(obj).substring(0, 31));
		$(obj.target).parents("tr").find("input[name$='.foreignKeyCode']").val(getForeignKeyName(obj).substring(0, 31));
		$(obj.target).parents("tr").find("input[name$='.toForeignKeyType']").val(obj.item.output04);
	}
}
