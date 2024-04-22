var ITEM_NAME = {
	ATTRIBUTE_NAME : "Attribute name",
	ATTRIBUTE_CODE : "Attribute code",
	DATATYPE : "Data type"
}
$(document).ready(function() {
	
	$(".out-focus-lower").focusout(function() {
		var lowerVal = $(this).val().toLowerCase();
		$(this).val(lowerVal);
	});
	$('#settingKey').click(function() {
		saveDataColumn();
	});
	initMapKey();
	
});
var CONST = {
		ATTR_PRECHECK_CALLBACK : "data-ar-precheck",
		ATTR_FINISHED_CALLBACK : "data-ar-callback",
		ATTR_INDEXCHANGE_CALLBACK : "data-ar-indexchange",
		ATTR_MAXIMUM_ROWS : "data-ar-mrows",
		ATTR_IGNORE_ROWS : "data-ar-irows",
		ATTR_ROW_GROUPID : "data-ar-rgroup",
		ATTR_ROW_GROUPINDEX : "data-ar-rgroupindex",
		ATTR_ROW_TEMPLATE : "data-ar-templateid",
		ATTR_ROW_INDEX : "data-ar-rindex",
		DIRECTION_ADD : "add",
		DIRECTION_REMOVE : "remove",
		ANCHOR_STRING_BEFORE : "before",
		ANCHOR_STRING_AFTER : "after",
		ANCHOR_STRING_INSIDE_BEGIN : "inside-begin",
		ANCHOR_STRING_INSIDE_END : "inside-end",
		REMOVE_TYPE_ALL :"all",
		REMOVE_TYPE_ONLYDESC: "onlyDescendants",
		AR_CLASS_GROUPINDEX: "ar-groupIndex",
		AR_CLASS_RINDEX: "ar-rIndex",
		AR_CLASS_GROUPID: "ar-groupId",
		ARR_REINDEX_ATTRIBUTES : ["name","id","for"]
};

var mapKeyTemp = [];
function initMapKey() {
	mapKeyTemp["externalobject"] = 0;
	var table = "#tbl_attribute_list_define";
	$(table).find("input[name*='externalObjectAttributeId']").each(function() {
		var id = $(this).val();
		if (id.search("eo") >= 0) {
			id = id.replace("eo","");
			id = parseInt(id);
			if (mapKeyTemp["externalobject"] < id) {
				mapKeyTemp["externalobject"] = id;
			}
		}
	});
}

function reIndexAllRowOfMenu(isView){
	var table = "#tbl_attribute_list_define";
	$(table).find(">tbody>tr").each(function(i){
		$(this).attr("data-ar-rindex",i+1);
	});
	$.qp.businessdesign.initAddButtonRow("tbl_attribute_list_define","tbl_attribute_list_define-action-template",isView);
}

var tempAnchor = null;
function onChangeExternalObject(event){
	var obj = event.target;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	$(obj).closest("tr").nextAll("tr[data-ar-rgroup^='"+rgroup+"']").each(function() {
		$(this).remove();
	});
	$(obj).closest("td").find("input[name*='moduleId']").val("");
	if(event.item != undefined){
		$(obj).closest("tr").find("input[name*='externalObjectAttributeName']").val(event.item.optionLabel);
		$(obj).closest("tr").find("input[name*='externalObjectAttributeCode']").val(event.item.output01);
		$(obj).closest("tr").find("label[name*='externalObjectAttributeCode']").text(event.item.output01);
		var table = $(obj).closest("table").attr("id");
		var actionTemplateId = table+"-external-object-columm-template";
		var level = 1;
		if(rgroup == undefined || rgroup == ""){
			level = 1;
		}else{
			level = (rgroup.match(/\./g) || []).length + 1;
		}
		if(event.item.optionValue != ""){
			var url = CONTEXT_PATH + "/externalobjectdefinition/getDetailsExternalObjectDefinition?externalObjectDefinitionId="+event.item.optionValue+"&level="+level+"&r="+Math.random();
			var data = $.qp.getData(url);
			if(data.length>0){
				data = data.sort(function (a, b){
					return a.externalObjectAttributeId - b.externalObjectAttributeId;
				});
				
				tempAnchor = obj;
				buildExternalObjectAttributes(data, obj);
			}
		}
		$(obj).closest("td").find("input[name*='moduleId']").val(event.item.output02);
	}else{
		$(obj).closest("tr").find("input[name*='externalObjectAttributeName']").val("");
		$(obj).closest("tr").find("input[name*='externalObjectAttributeCode']").val("");
		$(obj).closest("tr").find("label[name*='externalObjectAttributeCode']").text("");
	}
}

function buildExternalObjectAttributes(data, obj) {
	var tempParentId = null;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	var table = $(obj).closest("table").attr("id");
	
    for(var i=0;i<data.length;i++){
		if (data[i].dataType == 17) {
	    	var actionTemplateId = table+"-external-object-column-template";
			var newRow = $.qp.ar.addRow({link: obj,tableId:table,templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});

			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			var curObj = data[i].externalObjectDefinition;

			$(newRow).find("input[name*='groupId']").val(rgroup);
			$(newRow).find("input[name*='tableIndex']").text(tableIndex);
			$(newRow).find("input[name*='externalObjectAttributeName']").val(data[i].externalObjectAttributeName);
			$(newRow).find("label[name*='externalObjectAttributeName']").text(data[i].externalObjectAttributeName);
			$(newRow).find("input[name*='externalObjectAttributeCode']").val(data[i].externalObjectAttributeCode);
			$(newRow).find("label[name*='externalObjectAttributeCode']").text(data[i].externalObjectAttributeCode);
			$(newRow).find("input[name*='externalObjectDefinitionId']").val(curObj.externalObjectDefinitionId);
			$(newRow).find("input[name*='dataType']").val(data[i].dataType);
			$(newRow).find("input[name*='arrayFlg']").val(data[i].arrayFlg);
			$(newRow).find("label[name*='dataType']").text(CL_EX_DATATYPE_NOT_COMMON_OBJECT[data[i].dataType] + (data[i].arrayFlg ? "[]" : ""));
			tempAnchor = $(newRow);
			
			buildExternalObjectAttributes(data[i].externalObjectDefinition.externalObjectAttributes, newRow);
		} else {
			if (tempParentId < data[i].parentExternalObjectAttributeId) {
				rgroup = $(newRow).closest("tr").attr("data-ar-rgroupindex");
			} else if (tempParentId > data[i].parentExternalObjectAttributeId) {
				rgroup = rgroup.slice(0, rgroup.lastIndexOf("."));
			}
			
			var actionTemplateId = table+"-external-object-column-template";
			var newRow = $.qp.ar.addRow({link: obj,tableId:table,templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});
			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			
			$(newRow).find("input[name*='groupId']").val(rgroup);
			$(newRow).find("input[name*='tableIndex']").text(tableIndex);
			$(newRow).find("input[name*='externalObjectAttributeName']").val(data[i].externalObjectAttributeName);
			$(newRow).find("label[name*='externalObjectAttributeName']").text(data[i].externalObjectAttributeName);
			$(newRow).find("input[name*='externalObjectAttributeCode']").val(data[i].externalObjectAttributeCode);
			$(newRow).find("label[name*='externalObjectAttributeCode']").text(data[i].externalObjectAttributeCode);
			$(newRow).find("input[name*='externalObjectDefinitionId']").val(data[i].externalObjectAttributeId);
			$(newRow).find("input[name*='dataType']").val(data[i].dataType);
			$(newRow).find("input[name*='arrayFlg']").val(data[i].arrayFlg);
			$(newRow).find("label[name*='dataType']").text(CL_EX_DATATYPE_NOT_COMMON_OBJECT[data[i].dataType] + (data[i].arrayFlg ? "[]" : ""));
			tempAnchor = $(newRow);
			tempParentId = data[i].parentExternalObjectAttributeId;
		}
	}
}
function objectTypeChange(obj){
	var table = $(obj).closest("table").prop("id");
	var type = $(obj).val();
	var oldValue= $(obj).attr("oldvalue");
	var currentRow = $(obj).closest("tr");
	var $Caller = $(obj);
	var $ParentRow = $Caller.closest("tr");
	var rgroupindex = $ParentRow.attr("data-ar-rgroupindex") || "";
	var rgroup= $ParentRow.attr("data-ar-rgroup") || "";
	var actionTemplateId ; 
	var externalObjectAttributeCode = $ParentRow.find("input[name$='externalObjectAttributeCode']").val();

	var newRow ;
	if(oldValue == '17'){
		var nextRow = $(currentRow).nextAll().first();
		
		actionTemplateId = table+"-template";
		
		if (type=="17"){
			actionTemplateId = table + "-external-object-template";
		}
		
		if(nextRow.length >0){
			newRow = $.qp.ar.addRow({link:obj,tableId:table,templateId:actionTemplateId,templateData:{ groupId:rgroup},position:{anchor:nextRow,string:"before"} });
		}else{
			newRow = $.qp.ar.addRow({link:obj,tableId:table,templateId:actionTemplateId,templateData:{ groupId:rgroup} });
		}
		$(newRow).find("[name*='dataType']").val(type);

		$.qp.ar.removeRow({link:currentRow});
		if(type == "0"){
			var actionTemplateId = table+"-action-template";
			$.qp.ar.addRow({link:obj,tableId:table,templateId:actionTemplateId,templateData:{ groupId:rgroupindex},position:{anchor:newRow,string:"after"} });
		}
	} else {
		if(type=="0"){
			actionTemplateId = table+"-action-template";
			$.qp.ar.addRow({link:obj,tableId:table,templateId:actionTemplateId,templateData:{ groupId:rgroupindex},position:{anchor:$Caller.parents("tr:first"),string:"after"} });
		} else if(type=="17"){
			actionTemplateId = table + "-external-object-template";
			newRow = $.qp.ar.addRow({link: obj,tableId:table,templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor:currentRow,string:"after"} });
			$.qp.ar.removeRow({link:currentRow});
		} else{
			$.qp.ar.removeRow({link : obj,removeType : 'onlyDescendants'});
		}
		$(obj).attr("oldvalue",type);
	}
	if(newRow != undefined){
		$(newRow).find("input[name$='externalObjectAttributeCode']").val(externalObjectAttributeCode);
	}
}

function saveExternalOject() {
	var table = "#tbl_attribute_list_define";
	var msg = validateBeforeSubmit(table);
	
	if (msg != "") {
		$.qp.showAlertModal(msg);
		return false;
	} else {
		var mapKey = {};
		$(table).find(">tbody>tr.ar-dataRow").each(function(i) {
			var rgroup = $(this).attr("data-ar-rgroupindex");
			var rgroupOf = $(this).attr("data-ar-rgroup");
			
			var id = $(this).find("input[name*='externalObjectAttributeId']").val();
			if (id == "") {
				id = mapKeyTemp["externalobject"];
				mapKeyTemp["externalobject"] = parseInt(id) + 1;
				id = "eo" + mapKeyTemp["externalobject"];
			}
			mapKey[rgroup] = id;
			$(this).find("input[name*='externalObjectAttributeId']").val(id);
			var arrayIndex = rgroup.split(".");
			if (arrayIndex.length > 1) {
				var keyParent = "";
				for (var j = 0; j < arrayIndex.length - 1; j++) {
					if (j == 0) {
						keyParent = arrayIndex[j];
					} else {
						keyParent += "." + arrayIndex[j];
					}
				}
				$(this).find("input[name*='parentExternalObjectAttributeId']").val(mapKey[keyParent]);
			} else {
			}
	
			var dataType = $(this).find("select[name*='dataType']").val();
			if (dataType != undefined) {
				$(this).find("input[name*='dataType']").val($(this).find("select[name*='dataType']").val());
			} else {
				$(this).find("input[name*='dataType']").val($(this).find("input[name*='dataType']").val());
			}
			$(this).find("input[name*='itemSeqNo']").val(i);
			$(this).find("input[name*='.groupId']").val(rgroupOf);
			$(this).find("input[name*='.tableIndex']").val(rgroup);
		});
		
		return true;
	}
}

function validateBeforeSubmit(table) {
	changeStyleNormal(table);
	var messages="";
	messages += validateRequired(table,"externalObjectAttributeName", ITEM_NAME.ATTRIBUTE_NAME);
	messages += validateRequired(table,"externalObjectAttributeCode", ITEM_NAME.ATTRIBUTE_CODE);
	messages += validateRequired(table,"dataType", ITEM_NAME.DATATYPE);
	messages += validateSpecialChar(table,"externalObjectAttributeCode", ITEM_NAME.ATTRIBUTE_CODE);
//	messages += validateDuplicate(table,"externalObjectAttributeName", ITEM_NAME.ATTRIBUTE_NAME);
	messages += validateDuplicate(table,"externalObjectAttributeCode", ITEM_NAME.ATTRIBUTE_CODE);
	return messages;
}
function validateRequired(table, inputName, inputLabel) {
	var isList = true;
	var $Inputs
	$Inputs = $(table).find("[name*=" + inputName + "]").not('label,:hidden');
	var messages = "";
	$Inputs.each(function(index) {
		if ($.trim($(this).val()) == '') {
			if (!isList) {
				messages += fcomMsgSource['err.sys.0025'].replace("{0}",inputLabel);
			} else {
				messages += "\r\n";
				messages += fcomMsgSource['err.sys.0077'].replace("{0}",inputLabel).replace("{1}", index + 1);
				$(this).closest("tr").addClass("qp-bdesign-tr-warning");
			}
		}
	});
	return messages;
};
function validateSpecialChar(table, inputName, inputLabel) {
	var messages = "";
	var $Inputs = $(table).find("[name*=" + inputName + "]").not('label,:hidden');
	var flag = false;
	$Inputs.each(function(index) {
		flag = false;
		if ($.trim($(this).val()) !='' && !($.qp.validateIsCode($(this).val()))) {
			flag = true;
		}
		if(flag){
			messages += "\r\n";
			messages += fcomMsgSource['err.sys.0094'].replace("{0}", inputLabel).replace("{1}", index+1);
			$(this).closest("tr").addClass("qp-bdesign-tr-warning");
		}
	});
	messages = messages.replace('&quot;', '"').replace('&quot;', '"');
	return messages;
};
function validateDuplicate(table, inputName, inputLabel) {
	var $Inputs = $(table).find("[name*=" + inputName + "]").not('label,:hidden');
	var messages="";
	$Inputs.each(function(index) {
		var rgroup = ($(this).closest("tr").attr("data-ar-rgroup") == undefined || $(this).closest("tr").attr("data-ar-rgroup") == null ) ? "" : $(this).closest("tr").attr("data-ar-rgroup");
		for(var j=index + 1;j<$Inputs.length;j++) {
			var rgroupCheck = ($Inputs.eq(j).closest("tr").attr("data-ar-rgroup") == undefined || $Inputs.eq(j).closest("tr").attr("data-ar-rgroup") == null ) ? "" : $Inputs.eq(j).closest("tr").attr("data-ar-rgroup");
			if($.trim($(this).val()) != undefined && $.trim($(this).val()) != "" 
					&& $.trim($Inputs.eq(j).val()) != undefined && $.trim($Inputs.eq(j).val()) != ""
					&& $.trim($(this).val()) == $.trim($Inputs.eq(j).val())
					&& rgroup.trim() == rgroupCheck.trim()) {
				messages += "\r\n";
				messages +=fcomMsgSource['err.sys.0041'].replace("{0}",inputLabel).replace("{1}",(j+1));
				$(this).closest("tr").addClass("qp-bdesign-tr-warning");
				break;
			}
		}
	});
	return messages;
};
function changeStyleNormal(table) {
	$(table).find("tbody").find("tr").each(function (){
		$(this).removeClass("qp-bdesign-tr-warning");
	});
};

function changeModule(obj) {
	var scope = $(obj.target).closest("tr");
    var value = $(scope).find("input:hidden[name='moduleId']").val();

    $('table[id="tbl_attribute_list_define"]').find('>tbody>tr').each(function(){
//    	changeArgOfRowObjByType(this, "17", value);
    	//clear by module.
    	removeRowByModule(this,".objectDefinitionId",value);
    });
}

function removeRowByModule(row,inputName,moduleId){
	var moduleIdOfRow = $(row).find("input[name$='moduleId']").val();
	moduleIdOfRow = (moduleIdOfRow != undefined ? moduleIdOfRow : "");
	moduleId = (moduleId != undefined ? moduleId : "");
	$(row).find("input:text[name$='"+inputName+"Autocomplete']").attr("arg04", moduleId);
	if(moduleIdOfRow != "" && moduleId != moduleIdOfRow){
		$.qp.removeAutocompleteData($(row).find("input:hidden[name$='"+inputName+"']").nextAll('.dropdown-toggle:first'));
	}
}

function changeArgOfRowObjByType(newRow, type, argValue) {
	if($(newRow) != undefined && $(newRow).find("input:text[name$='.objectDefinitionIdAutocomplete']").val() != undefined && type == "17"){
		$(newRow).find("input:text[name$='.objectDefinitionIdAutocomplete']").val('');
		$(newRow).find("input:hidden[name$='.objectDefinitionId']").val('');
		$(newRow).find("input:text[name$='.objectDefinitionIdAutocomplete']").attr("arg04", argValue);
		$.qp.removeAutocompleteData($($(newRow).find("input:hidden[name$='.objectDefinitionId']")).nextAll('.dropdown-toggle:first'));
	}
}

function changeRowOfAttributeCallBackfunction(table,direction,row){
	if(direction=="add"){
		var type = $(row).find("select[name$='.dataType']").val();
		if(type == 17){
			var moduleId = $(table).closest("form").find("input[name='moduleId']").val();
			 $(row).find("input[name$='.objectDefinitionIdAutocomplete']").attr("arg04", moduleId);
		}
		$.qp.businessdesign.reStyleRow(table, row);
	}
}