/**
 * @author QuangVD
 */
var mapKeyTemp = [];
$(document).ready(function() {
	initMapKey();
});
function initMapKey() {
	mapKeyTemp["inputbean"] = 0;
	mapKeyTemp["outputbean"] = 0;
	mapKeyTemp["objectdefinition"] = 0;
	var tableInputBean = "#tbl_inputbean_list_define";
	$(tableInputBean).find("input[name*='inputBeanId']").each(function() {
		var id = $(this).val();
		if (id.search("in") >= 0) {
			id = id.replace("in","");
			id = parseInt(id);
			if (mapKeyTemp["inputbean"] < id) {
				mapKeyTemp["inputbean"] = id;
			}
		}
	});
	var tableOutputBean = "#tbl_outputbean_list_define";
	$(tableOutputBean).find("input[name*='outputBeanId']").each(function() {
		var id = $(this).val();
		if (id.search("ou") >= 0) {
			id = id.replace("ou","");
			id = parseInt(id);
			if (mapKeyTemp["outputbean"] < id) {
				mapKeyTemp["outputbean"] = id;
			}
		}
	});
	var tableObjectDefinition = "#tbl_objectdefinition_list_define";
	$(tableObjectDefinition).find("input[name*='objectDefinitionId']").each(function() {
		var id = $(this).val();
		if (id.search("ob") >= 0) {
			id = id.replace("ob","");
			id = parseInt(id);
			if (mapKeyTemp["objectdefinition"] < id) {
				mapKeyTemp["objectdefinition"] = id;
			}
		}
	});
}

/*
 * Save inputbean into json field
 */
function saveInputBean() {
	var table = "#tbl_inputbean_list_define";
	arrInputBean = new Array();
	var mapKey = {};
	$(table).find(">tbody>tr.ar-dataRow").each(function(i) {
		var rgroup = $(this).attr("data-ar-rgroupindex");
		var inputbean = new Object();
		inputbean.inputBeanCode = $(this).find("input[name*='inputBeanCode']").val();
		inputbean.inputBeanName = $(this).find("input[name*='inputBeanName']").val();
//		inputbean.messageStringAutocomplete = $(this).find("input[name*='messageStringAutocomplete']").val();
//		inputbean.messageString = $(this).find("input[name*='messageString']").val();
//		if("false"==$(this).find("input[name*='messageStringAutocomplete']").attr("selectedvalue")){
//			inputbean.createdMessageFlg = true;
//			$(this).find("input[name*='createdMessageFlg']").val(true);
//		}else{
//			$(this).find("input[name*='createdMessageFlg']").val(false);
//		}
		inputbean.dataType = $(this).find("input[name*='dataType'],select[name*='dataType']").val();
		if("checkbox" == $(this).find("input[name*='arrayFlg']").attr('type')){
			inputbean.arrayFlg = $(this).find("input[name*='arrayFlg']").prop("checked");
		}else{
			inputbean.arrayFlg = $(this).find("input[name*='arrayFlg']").val();
		}
		inputbean.itemSequenceNo = i;
		$(this).find("input[name*='itemSequenceNo']").val(inputbean.itemSequenceNo);
		var id = $(this).find("input[name*='inputBeanId']").val();
		if (id == "") {
			id = mapKeyTemp["inputbean"];
			mapKeyTemp["inputbean"] = parseInt(id) + 1;
			id = "in" + mapKeyTemp["inputbean"];
		}
		inputbean.inputBeanId = id;
		mapKey[rgroup] = id;
		$(this).find("input[name*='inputBeanId']").val(id);

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
			inputbean.parentInputBeanId = mapKey[keyParent];
			$(this).find("input[name*='parentInputBeanId']").val(inputbean.parentInputBeanId);
		} else {
			inputbean.parentInputBeanId = "";
		}
		inputbean.groupId = ($(this).attr("data-ar-rgroup") == undefined || $(this).attr("data-ar-rgroup") == null) ? "": $(this).attr("data-ar-rgroup");
		inputbean.tableIndex = rgroup;
		arrInputBean.push(inputbean);
	});
	var value = convertToString(arrInputBean);
	$(table).closest("form").find("input[name='jsonInputBean']").val(value);
}

/*
 * Save outputbean into json field
 */
function saveOutputBean() {
	var table = "#tbl_outputbean_list_define";
	arrOutputBean = new Array();
	var mapKey = {};
	$(table).find(">tbody>tr.ar-dataRow").each(function(i) {
		var rgroup = $(this).attr("data-ar-rgroupindex");
		var outputbean = new Object();
		outputbean.outputBeanCode = $(this).find("input[name*='outputBeanCode']").val();
		outputbean.outputBeanName = $(this).find("input[name*='outputBeanName']").val();
		outputbean.dataType = $(this).find("input[name*='dataType'],select[name*='dataType']").val();
		if("checkbox" == $(this).find("input[name*='arrayFlg']").attr('type')){
			outputbean.arrayFlg = $(this).find("input[name*='arrayFlg']").prop("checked");
		}else{
			outputbean.arrayFlg = $(this).find("input[name*='arrayFlg']").val();
		}
		outputbean.itemSequenceNo = i;
		$(this).find("input[name*='itemSequenceNo']").val(outputbean.itemSequenceNo);
		var id = $(this).find("input[name*='outputBeanId']").val();
		if (id == "") {
			id = mapKeyTemp["outputbean"];
			mapKeyTemp["outputbean"] = parseInt(id) + 1;
			id = "ou" + mapKeyTemp["outputbean"];
		}
		outputbean.outputBeanId = id;
		mapKey[rgroup] = id;
		$(this).find("input[name*='outputBeanId']").val(id);
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
			outputbean.parentOutputBeanId = mapKey[keyParent];
			$(this).find("input[name*='parentOutputBeanId']").val(outputbean.parentOutputBeanId);
		} else {
			outputbean.parentOutputBeanId = "";
		}
		outputbean.tableIndex = rgroup;
		arrOutputBean.push(outputbean);
	});
	var value = convertToString(arrOutputBean);
	$(table).closest("form").find("input[name='jsonOutputBean']").val(value);
}

/*
 * Save objectdefinition into json field
 */
function saveObjectDefinition() {
	var table = "#tbl_objectdefinition_list_define";
	arrObjectDefinition = new Array();
	var mapKey = {};
	$(table).find(">tbody>tr.ar-dataRow").each(function(i) {
		var rgroup = $(this).attr("data-ar-rgroupindex");
		var objectdefinition = new Object();
		objectdefinition.objectDefinitionCode = $(this).find("input[name*='objectDefinitionCode']").val();
		objectdefinition.objectDefinitionName = $(this).find("input[name*='objectDefinitionName']").val();
		objectdefinition.dataType = $(this).find("input[name*='dataType'],select[name*='dataType']").val();
		if("checkbox" == $(this).find("input[name*='arrayFlg']").attr('type')){
			objectdefinition.arrayFlg = $(this).find("input[name*='arrayFlg']").prop("checked");
		}else{
			objectdefinition.arrayFlg = $(this).find("input[name*='arrayFlg']").val();
		}
		objectdefinition.sessionFlg = $(this).find("input[name*='sessionFlg']").prop("checked");
		objectdefinition.itemSequenceNo = i;
		$(this).find("input[name*='itemSequenceNo']").val(objectdefinition.itemSequenceNo);
		var id = $(this).find("input[name*='objectDefinitionId']").val();
		if (id == "") {
			id = mapKeyTemp["objectdefinition"];
			mapKeyTemp["objectdefinition"] = parseInt(id) + 1;
			id = "ob" + mapKeyTemp["objectdefinition"];
		}
		objectdefinition.objectDefinitionId = id;
		mapKey[rgroup] = id;
		$(this).find("input[name*='objectDefinitionId']").val(id);
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
			objectdefinition.parentObjectDefinitionId = mapKey[keyParent];
			$(this).find("input[name*='parentObjectDefinitionId']").val(objectdefinition.parentObjectDefinitionId);
		} else {
			objectdefinition.parentObjectDefinitionId = "";
		}
		objectdefinition.tableIndex = rgroup;
		arrObjectDefinition.push(objectdefinition);
	});
	var value = convertToString(arrObjectDefinition);
	$(table).closest("form").find("input[name='jsonObjectDefinition']").val(value);
}

/*
 * Save data when User clicks save button
 */
function saveData() {
	saveInputBean();
	saveOutputBean();
	saveObjectDefinition();

	var message = $.qp.businessdesign.validaion.validationForm();
	message += validationBlogicSetting();
	if(message != ""){
		$.qp.showAlertModal(message);
		return false;
	}else{
		saveBlogicSetting();
		return true;
	}
}

/*
 * Save data when User show business design
 */
function saveParameterData() {
	saveInputBean();
	saveOutputBean();
	saveObjectDefinition();
	var message = $.qp.businessdesign.validaion.validationForm();
	if(message != ""){
		$.qp.showAlertModal(message);
		return false;
	}else{
		return true;
	}
}
var arrComponent = new Array();
var arrConnection = new Array();
function saveBlogicSetting() {
	arrComponent = new Array();
	arrConnection = new Array();
	$("#designArea").find(".execution-class").each(function(i) {
		var object = new Object();
		object.sequenceLogicId = $(this).attr("id");
		var xCoordinates = $(this).css("left");
		object.xCoordinates = xCoordinates.replace("px", "");
		var yCoordinates = $(this).css("top");
		object.yCoordinates = yCoordinates.replace("px", "");
		object.sequenceNo = i;
		object.sequenceLogicName = $(this).find("span.component-name").text();
		object.componentType = $(this).attr("componenttype");
		object.remark = $(this).attr("data-original-title");
		object.strData = $(this).find("input[name='componentElement']").val();
		if("9" == object.componentType){
			object.relatedSequenceLogicId = $(this).attr("id-endif");
		}
		
		arrComponent.push(object);
	});
	var value = convertToString(arrComponent);
	$("#designArea").closest("form").find("input[name='jsonComponent']").val(value);
	var arrAllConnection = instanceOfBlogic.getAllConnections();
	for (var i = 0; i < arrAllConnection.length; i++) {
		var connection = new Object();
		connection.connectorSource = arrAllConnection[i].sourceId;
		connection.connectorDest = arrAllConnection[i].targetId;
		connection.connectorType =  arrAllConnection[i].getOverlay("label").getLabel();
		arrConnection.push(connection);
	}
	var value = convertToString(arrConnection);
	$("#designArea").closest("form").find("input[name='jsonConnector']").val(value);
}

function validationBlogicSetting() {
	var messageBlogic = "";
	$("#designArea").find(".execution-class").each(function(i) {
		var componentType = $(this).attr("componenttype");
		if(componentType != "1" && componentType != "13"){
			var strData = $(this).find("input[name='componentElement']").val();
			var detailMessage = $.qp.businessdesign.validaionComponent.validationComp(convertToJson(strData),componentType);
			if(detailMessage.length >0){
				$(this).addClass("node-error");
			}
			messageBlogic += detailMessage;
		}
	});
	return messageBlogic;
}
function toggleBDScope(thiz) {
	$("#tabBusiness-objectdefinition").find(".bd-in-scope").toggleClass("bd-in-scope-hidden");
	var status = $(thiz).attr("status");
	if (status == "0") {
		// 1 : show
		$(thiz).attr("status", "1");
	} else if (status == "1") {
		// 0 : hide
		$(thiz).attr("status", "0");
	}
	var table = $(thiz).parents("table").first();
	$(table).find(">tbody>tr.ar-dataRow").each(function(i) {
		var element = $(this).find("div.pull-right").first();
		// if(element != undefined && element.length >0){
		// var width = $(element).css("width");
		// if(status == "0"){
		// $(element).css("width",(parseInt(width.replace("px", ""))-100)+"px");
		// }else if(status == "1"){
		// $(element).css("width",(parseInt(width.replace("px", ""))+100)+"px");
		// }
		// }
		// $.qp.businessdesign.reStyleRow(table, $(this));

	});
};
function showHideFileContent(thiz) {
	var value = $(thiz).prop("checked");
	if (value) {
		$(thiz).closest("table").find("tr.tr-customize-content").show();
		$('[href="#tabBusiness-objectdefinition"]').closest('li').hide();
		$('[href="#tabBusiness-blogicsetting"]').closest('li').hide();
		
		
		$('#com-menu-sidebar').find("a#tabOutputBean").tab("show");
		
		//remove data of object definition
		$("#tbl_objectdefinition_list_define").find("tbody").find("tr").remove();
		//remove blogic
		$("#designArea").find(".execution-class").filter("[componenttype!='1']").remove();
		
		//restyle table of ouputbean
//		$.qp.businessdesign.initAddButtonRow("tbl_outputbean_list_define","tbl_outputbean_list_define-action-template");
		$.qp.businessdesign.initData("tbl_outputbean_list_define");
	} else {
		$(thiz).closest("table").find("tr.tr-customize-content").hide();
		$('[href="#tabBusiness-objectdefinition"]').closest('li').show();
		$('[href="#tabBusiness-blogicsetting"]').closest('li').show();
		
		$('#com-menu-sidebar').find("a#tabBlogicSetting").tab("show");
		
		//resize design area
		var width = $("#designArea").closest("td").outerWidth() -12;
		$("#designArea").width(width);
	}
};
//check rule {} when modify
function countParameterOfMessage(str){
	if(str != undefined){
		var regex = /\{\d*\}/g;
		var index = 0;
		var t = str.match(regex);
		if(t == null || t == undefined){
			return 0;
		}else
			return t.length;
	}else{
		return 0;
	}
	
}
function onchangeModule(event){
	onchangeModuleAffectObjExt(event);
	if(event.item != undefined){
		var moduleId = event.item.optionValue;
		moduleIdOfBD = moduleId;
		moduleIdAutocompleteOfBD = event.item.optionLabel;
	}else{
		moduleIdOfBD = "";
		moduleIdAutocompleteOfBD = "";
	}
	clearFunctionDesignAutocomplete(moduleIdOfBD);
	clearScreenDesignAutocomplete(moduleIdOfBD);
}
function clearModuleAutocomplete(){
	clearFunctionDesignAutocomplete("");
	clearScreenDesignAutocomplete("");
}
function bdOnChangeTableDesignOfIn(event){
	var obj = event.target;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	$(obj).closest("tr").nextAll("tr[data-ar-rgroup="+rgroup+"]").each(function() {
		$(this).remove();
	});
	if(event.item != undefined){
		$(obj).closest("tr").find("input[name*='inputBeanName']").val(event.item.optionLabel);
		$(obj).closest("tr").find("input[name*='inputBeanCode']").val(event.item.output01);
		$(obj).closest("tr").find("input[name*='objectFlg']").val(true);
		var table = $(obj).closest("table").attr("id");
		var actionTemplateId = table+"-column-template";
		if(event.item.optionValue != ""){
			var url = CONTEXT_PATH + "/businessdesign/getColumnsOfTableDesign?tableId="+event.item.optionValue+"&r="+Math.random();
			var data = $.qp.getData(url);
			if(data.length>0){
				for(var i=data.length -1;i >= 0;i--){
					var newRow = $.qp.ar.addRow({link: obj,tableId:table,templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor:$(obj).closest('tr'),string:'after'}});
					$(newRow).find("input[name*='inputBeanName']").val(data[i].columnName);
					$(newRow).find("label[name*='inputBeanName']").text(data[i].columnName);
					$(newRow).find("input[name*='inputBeanCode']").val(data[i].columnCode);
					$(newRow).find("label[name*='inputBeanCode']").text(data[i].columnCode);
					$(newRow).find("input[name*='tblDesignId']").val(data[i].tableDesignId);
					$(newRow).find("input[name*='columnId']").val(data[i].columnId);
					$(newRow).find("input[name*='dataType']").val(data[i].dataType);
					$(newRow).find("input[name*='arrayFlg']").val(data[i].arrayFlg);
					$(newRow).find("label[name*='dataType']").text(CL_BD_DATATYPE[data[i].dataType] + (data[i].arrayFlg == true ? "[]" : ""));
					$(newRow).find("input[name*='groupBaseTypeId']").val(data[i].baseType);
					$(newRow).find("input[name*='objectFlg']").val(false);
				}
			}
		}
	}else{
		$(obj).closest("tr").find("input[name*='inputBeanName']").val("");
		$(obj).closest("tr").find("input[name*='inputBeanCode']").val("");
	}
}
function bdOnChangeTableDesignOfOb(event){
	var obj = event.target;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	$(obj).closest("tr").nextAll("tr[data-ar-rgroup="+rgroup+"]").each(function() {
		$(this).remove();
	});
	if(event.item != undefined){
		$(obj).closest("tr").find("input[name*='objectDefinitionName']").val(event.item.optionLabel);
		$(obj).closest("tr").find("input[name*='objectDefinitionCode']").val(event.item.output01);
		$(obj).closest("tr").find("input[name*='objectFlg']").val(true);
		var table = $(obj).closest("table").attr("id");
		var actionTemplateId = table+"-column-template";
		if(event.item.optionValue != ""){
			var url = CONTEXT_PATH + "/businessdesign/getColumnsOfTableDesign?tableId="+event.item.optionValue+"&r="+Math.random();
			var data = $.qp.getData(url);
			if(data.length>0){
				for(var i=data.length -1;i >= 0;i--){
					var newRow = $.qp.ar.addRow({link: obj,tableId:table,templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor:$(obj).closest('tr'),string:'after'}});
					$(newRow).find("input[name*='objectDefinitionName']").val(data[i].columnName);
					$(newRow).find("label[name*='objectDefinitionName']").text(data[i].columnName);
					$(newRow).find("input[name*='objectDefinitionCode']").val(data[i].columnCode);
					$(newRow).find("label[name*='objectDefinitionCode']").text(data[i].columnCode);
					$(newRow).find("input[name*='tblDesignId']").val(data[i].tableDesignId);
					$(newRow).find("input[name*='columnId']").val(data[i].columnId);
					$(newRow).find("input[name*='dataType']").val(data[i].dataType);
					$(newRow).find("input[name*='arrayFlg']").val(data[i].arrayFlg);
					$(newRow).find("label[name*='dataType']").text(CL_BD_DATATYPE[data[i].dataType] + (data[i].arrayFlg == true ? "[]" : ""));
					$(newRow).find("input[name*='groupBaseTypeId']").val(data[i].baseType);
					$(newRow).find("input[name*='objectFlg']").val(false);
				}
			}
		}
	}else{
		$(obj).closest("tr").find("input[name*='objectDefinitionName']").val("");
		$(obj).closest("tr").find("input[name*='objectDefinitionCode']").val("");
	}
}
function bdOnChangeTableDesignOfOu(event){
	var obj = event.target;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	$(obj).closest("tr").nextAll("tr[data-ar-rgroup="+rgroup+"]").each(function() {
		$(this).remove();
	});
	if(event.item != undefined){
		$(obj).closest("tr").find("input[name*='outputBeanName']").val(event.item.optionLabel);
		$(obj).closest("tr").find("input[name*='outputBeanCode']").val(event.item.output01);
		$(obj).closest("tr").find("input[name*='objectFlg']").val(true);
		var table = $(obj).closest("table").attr("id");
		var actionTemplateId = table+"-column-template";
		if(event.item.optionValue != ""){
			var url = CONTEXT_PATH + "/businessdesign/getColumnsOfTableDesign?tableId="+event.item.optionValue+"&r="+Math.random();
			var data = $.qp.getData(url);
			if(data.length>0){
				for(var i=data.length -1;i >= 0;i--){
					var newRow = $.qp.ar.addRow({link: obj,tableId:table,templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor:$(obj).closest('tr'),string:'after'}});
					$(newRow).find("input[name*='outputBeanName']").val(data[i].columnName);
					$(newRow).find("label[name*='outputBeanName']").text(data[i].columnName);
					$(newRow).find("input[name*='outputBeanCode']").val(data[i].columnCode);
					$(newRow).find("label[name*='outputBeanCode']").text(data[i].columnCode);
					$(newRow).find("input[name*='tblDesignId']").val(data[i].tableDesignId);
					$(newRow).find("input[name*='columnId']").val(data[i].columnId);
					$(newRow).find("input[name*='dataType']").val(data[i].dataType);
					$(newRow).find("input[name*='arrayFlg']").val(data[i].arrayFlg);
					$(newRow).find("label[name*='dataType']").text(CL_BD_DATATYPE[data[i].dataType] + (data[i].arrayFlg == true ? "[]" : ""));
					$(newRow).find("input[name*='groupBaseTypeId']").val(data[i].baseType);
					$(newRow).find("input[name*='objectFlg']").val(false);
				}
			}
		}
	}else{
		$(obj).closest("tr").find("input[name*='outputBeanName']").val("");
		$(obj).closest("tr").find("input[name*='outputBeanCode']").val("");
	}
}

function getDataSourceAutocompleteBD(event){
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
			var sourceData = convertToJson($("form").find("input[name='jsonInputBean']").val());
			if(sourceData != undefined && sourceData.length > 0) {
				for(var i = 0; i < sourceData.length; i++) {
					var objTmp = new Object();
					objTmp.optionValue = "0"+sourceData[i].inputBeanId;
					objTmp.output01 = 0;
					objTmp.output02 = sourceData[i].parentInputBeanId;
					objTmp.output03 = sourceData[i].dataType;
					objTmp.output04 = sourceData[i].arrayFlg;
					objTmp.output05 = sourceData[i].inputBeanId;
					objTmp.output06 = sourceData[i].inputBeanCode;
					
					if(sourceData[i].parentInputBeanId != undefined && sourceData[i].parentInputBeanId != ""){
						objTmp.optionLabel = mKeyCode["0"+sourceData[i].parentInputBeanId]+"."+sourceData[i].inputBeanCode + (eval(sourceData[i].arrayFlg) ? "[]": "");
					}else{
						objTmp.optionLabel = "in."+sourceData[i].inputBeanCode + (eval(sourceData[i].arrayFlg) ? "[]": "");
					}
					mKeyCode[objTmp.optionValue] = objTmp.optionLabel;
					
					var index = objTmp.optionLabel.indexOf(searchKey);
					if(index >-1){
						dataResult.push(objTmp);
					}
				}
				
			}
			break;
		case "ob":
			var sourceDataOb = convertToJson($("form").find("input[name='jsonObjectDefinition']").val());
			if(sourceDataOb != undefined && sourceDataOb.length > 0) {
				for(var i = 0; i < sourceDataOb.length; i++) {
					var objTmp = new Object();
					objTmp.optionValue = "1"+sourceDataOb[i].objectDefinitionId;
					objTmp.output01 = 1;
					objTmp.output02 = sourceDataOb[i].parentObjectDefinitionId;
					objTmp.output03 = sourceDataOb[i].dataType;
					objTmp.output04 = sourceDataOb[i].arrayFlg;
					objTmp.output05 = sourceDataOb[i].objectDefinitionId;
					objTmp.output06 = sourceDataOb[i].objectDefinitionCode;
					if(sourceDataOb[i].parentObjectDefinitionId != undefined && sourceDataOb[i].parentObjectDefinitionId != ""){
						objTmp.optionLabel = mKeyCode["1"+sourceDataOb[i].parentObjectDefinitionId]+"."+sourceDataOb[i].objectDefinitionCode + (eval(sourceDataOb[i].arrayFlg) ? "[]": "");
					}else{
						objTmp.optionLabel = "ob."+sourceDataOb[i].objectDefinitionCode + (eval(sourceDataOb[i].arrayFlg) ? "[]": "");
					}
					mKeyCode[objTmp.optionValue] = objTmp.optionLabel;
					
					var index = objTmp.optionLabel.indexOf(searchKey);
					if(index >-1){
						dataResult.push(objTmp);
					}
				}
			}
			break;
		case "ou":
			var sourceData = convertToJson($("form").find("input[name='jsonOutputBean']").val());
			if(sourceData != undefined && sourceData.length > 0) {
				for(var i = 0; i < sourceData.length; i++) {
					var objTmp = new Object();
					objTmp.optionValue = "2"+sourceData[i].outputBeanId;
					objTmp.output01 = 2;
					objTmp.output02 = sourceData[i].parentOutputBeanId;
					objTmp.output03 = sourceData[i].dataType;
					objTmp.output04 = sourceData[i].arrayFlg;
					objTmp.output05 = sourceData[i].outputBeanId;
					objTmp.output06 = sourceData[i].outputBeanCode;
					
					if(sourceData[i].parentOutputBeanId != undefined && sourceData[i].parentOutputBeanId != ""){
						objTmp.optionLabel = mKeyCode["2"+sourceData[i].parentOutputBeanId]+"."+sourceData[i].outputBeanCode + (eval(sourceData[i].arrayFlg) ? "[]": "");
					}else{
						objTmp.optionLabel = "ou."+sourceData[i].outputBeanCode + (eval(sourceData[i].arrayFlg) ? "[]": "");
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

function getDataSourceAutocompleteBDFilterArray(event){
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
			var sourceData = convertToJson($("form").find("input[name='jsonInputBean']").val());
			if(sourceData != undefined && sourceData.length > 0) {
				for(var i = 0; i < sourceData.length; i++) {
					var objTmp = new Object();
					objTmp.optionValue = "0"+sourceData[i].inputBeanId;
					objTmp.output01 = 0;
					objTmp.output02 = sourceData[i].parentInputBeanId;
					objTmp.output03 = sourceData[i].dataType;
					objTmp.output04 = sourceData[i].arrayFlg;
					objTmp.output05 = sourceData[i].inputBeanId;
					objTmp.output06 = sourceData[i].inputBeanCode;
					
					if(sourceData[i].parentInputBeanId != undefined && sourceData[i].parentInputBeanId != ""){
						objTmp.optionLabel = mKeyCode["0"+sourceData[i].parentInputBeanId]+"."+sourceData[i].inputBeanCode + (eval(sourceData[i].arrayFlg) ? "[]": "");
					}else{
						objTmp.optionLabel = "in."+sourceData[i].inputBeanCode + (eval(sourceData[i].arrayFlg) ? "[]": "");
					}
					mKeyCode[objTmp.optionValue] = objTmp.optionLabel;
					
					var index = objTmp.optionLabel.indexOf(searchKey);
					if(index >-1){
						if (eval(objTmp.output04)) {
							dataResult.push(objTmp);
						}
					}
				}
				
			}
			break;
		case "ob":
			var sourceDataOb = convertToJson($("form").find("input[name='jsonObjectDefinition']").val());
			if(sourceDataOb != undefined && sourceDataOb.length > 0) {
				for(var i = 0; i < sourceDataOb.length; i++) {
					var objTmp = new Object();
					objTmp.optionValue = "1"+sourceDataOb[i].objectDefinitionId;
					objTmp.output01 = 1;
					objTmp.output02 = sourceDataOb[i].parentObjectDefinitionId;
					objTmp.output03 = sourceDataOb[i].dataType;
					objTmp.output04 = sourceDataOb[i].arrayFlg;
					objTmp.output05 = sourceDataOb[i].objectDefinitionId;
					objTmp.output06 = sourceDataOb[i].objectDefinitionCode;
					if(sourceDataOb[i].parentObjectDefinitionId != undefined && sourceDataOb[i].parentObjectDefinitionId != ""){
						objTmp.optionLabel = mKeyCode["1"+sourceDataOb[i].parentObjectDefinitionId]+"."+sourceDataOb[i].objectDefinitionCode + (eval(sourceDataOb[i].arrayFlg) ? "[]": "");
					}else{
						objTmp.optionLabel = "ob."+sourceDataOb[i].objectDefinitionCode + (eval(sourceDataOb[i].arrayFlg) ? "[]": "");
					}
					mKeyCode[objTmp.optionValue] = objTmp.optionLabel;
					
					var index = objTmp.optionLabel.indexOf(searchKey);
					if(index >-1){
						if (eval(objTmp.output04)) {
							dataResult.push(objTmp);
						}
					}
				}
			}
			break;
		case "ou":
			var sourceData = convertToJson($("form").find("input[name='jsonOutputBean']").val());
			if(sourceData != undefined && sourceData.length > 0) {
				for(var i = 0; i < sourceData.length; i++) {
					var objTmp = new Object();
					objTmp.optionValue = "2"+sourceData[i].outputBeanId;
					objTmp.output01 = 2;
					objTmp.output02 = sourceData[i].parentOutputBeanId;
					objTmp.output03 = sourceData[i].dataType;
					objTmp.output04 = sourceData[i].arrayFlg;
					objTmp.output05 = sourceData[i].outputBeanId;
					objTmp.output06 = sourceData[i].outputBeanCode;
					
					if(sourceData[i].parentOutputBeanId != undefined && sourceData[i].parentOutputBeanId != ""){
						objTmp.optionLabel = mKeyCode["2"+sourceData[i].parentOutputBeanId]+"."+sourceData[i].outputBeanCode + (eval(sourceData[i].arrayFlg) ? "[]": "");
					}else{
						objTmp.optionLabel = "ou."+sourceData[i].outputBeanCode + (eval(sourceData[i].arrayFlg) ? "[]": "");
					}
					mKeyCode[objTmp.optionValue] = objTmp.optionLabel;
					
					var index = objTmp.optionLabel.indexOf(searchKey);
					if(index >-1){
						if (eval(objTmp.output04)) {
							dataResult.push(objTmp);
						}
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

function getDataSourceAutocompleteBDFilterByteArray(event){
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
			var sourceData = convertToJson($("form").find("input[name='jsonInputBean']").val());
			if(sourceData != undefined && sourceData.length > 0) {
				for(var i = 0; i < sourceData.length; i++) {
					var objTmp = new Object();
					objTmp.optionValue = "0"+sourceData[i].inputBeanId;
					objTmp.output01 = 0;
					objTmp.output02 = sourceData[i].parentInputBeanId;
					objTmp.output03 = sourceData[i].dataType;
					objTmp.output04 = sourceData[i].arrayFlg;
					objTmp.output05 = sourceData[i].inputBeanId;
					objTmp.output06 = sourceData[i].inputBeanCode;
					
					if(sourceData[i].parentInputBeanId != undefined && sourceData[i].parentInputBeanId != ""){
						objTmp.optionLabel = mKeyCode["0"+sourceData[i].parentInputBeanId]+"."+sourceData[i].inputBeanCode + (eval(sourceData[i].arrayFlg) ? "[]": "");
					}else{
						objTmp.optionLabel = "in."+sourceData[i].inputBeanCode + (eval(sourceData[i].arrayFlg) ? "[]": "");
					}
					mKeyCode[objTmp.optionValue] = objTmp.optionLabel;
					
					var index = objTmp.optionLabel.indexOf(searchKey);
					if(index >-1){
						if (objTmp.output04 == true && objTmp.output03 == "1") {
							dataResult.push(objTmp);
						}
					}
				}
				
			}
			break;
		case "ob":
			var sourceDataOb = convertToJson($("form").find("input[name='jsonObjectDefinition']").val());
			if(sourceDataOb != undefined && sourceDataOb.length > 0) {
				for(var i = 0; i < sourceDataOb.length; i++) {
					var objTmp = new Object();
					objTmp.optionValue = "1"+sourceDataOb[i].objectDefinitionId;
					objTmp.output01 = 1;
					objTmp.output02 = sourceDataOb[i].parentObjectDefinitionId;
					objTmp.output03 = sourceDataOb[i].dataType;
					objTmp.output04 = sourceDataOb[i].arrayFlg;
					objTmp.output05 = sourceDataOb[i].objectDefinitionId;
					objTmp.output06 = sourceDataOb[i].objectDefinitionCode;
					if(sourceDataOb[i].parentObjectDefinitionId != undefined && sourceDataOb[i].parentObjectDefinitionId != ""){
						objTmp.optionLabel = mKeyCode["1"+sourceDataOb[i].parentObjectDefinitionId]+"."+sourceDataOb[i].objectDefinitionCode + (eval(sourceDataOb[i].arrayFlg) ? "[]": "");
					}else{
						objTmp.optionLabel = "ob."+sourceDataOb[i].objectDefinitionCode + (eval(sourceDataOb[i].arrayFlg) ? "[]": "");
					}
					mKeyCode[objTmp.optionValue] = objTmp.optionLabel;
					
					var index = objTmp.optionLabel.indexOf(searchKey);
					if(index >-1){
						if (objTmp.output04 == true && objTmp.output03 == "1") {
							dataResult.push(objTmp);
						}
					}
				}
			}
			break;
		case "ou":
			var sourceData = convertToJson($("form").find("input[name='jsonOutputBean']").val());
			if(sourceData != undefined && sourceData.length > 0) {
				for(var i = 0; i < sourceData.length; i++) {
					var objTmp = new Object();
					objTmp.optionValue = "2"+sourceData[i].outputBeanId;
					objTmp.output01 = 2;
					objTmp.output02 = sourceData[i].parentOutputBeanId;
					objTmp.output03 = sourceData[i].dataType;
					objTmp.output04 = sourceData[i].arrayFlg;
					objTmp.output05 = sourceData[i].outputBeanId;
					objTmp.output06 = sourceData[i].outputBeanCode;
					
					if(sourceData[i].parentOutputBeanId != undefined && sourceData[i].parentOutputBeanId != ""){
						objTmp.optionLabel = mKeyCode["2"+sourceData[i].parentOutputBeanId]+"."+sourceData[i].outputBeanCode + (eval(sourceData[i].arrayFlg) ? "[]": "");
					}else{
						objTmp.optionLabel = "ou."+sourceData[i].outputBeanCode + (eval(sourceData[i].arrayFlg) ? "[]": "");
					}
					mKeyCode[objTmp.optionValue] = objTmp.optionLabel;
					
					var index = objTmp.optionLabel.indexOf(searchKey);
					if(index >-1){
						if (objTmp.output04 == true && objTmp.output03 == "1") {
							dataResult.push(objTmp);
						}
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

function getDataSourceMap(){
	var dataResult = new Array();
	var idResults = new Array();
	var codeResults = new Array();
	var mKeyCode = new Array();
	var sourceData = convertToJson($("form").find("input[name='jsonInputBean']").val());
	if(sourceData != undefined && sourceData.length > 0) {
		for(var i = 0; i < sourceData.length; i++) {
			var objTmp = new Object();
			objTmp.optionValue = "0"+sourceData[i].inputBeanId;
			objTmp.output01 = 0;
			objTmp.output02 = sourceData[i].parentInputBeanId;
			objTmp.output03 = sourceData[i].dataType;
			objTmp.output04 = sourceData[i].arrayFlg;
			objTmp.output05 = sourceData[i].inputBeanId;
			objTmp.output06 = sourceData[i].inputBeanCode;
			
			if(sourceData[i].parentInputBeanId != undefined && sourceData[i].parentInputBeanId != ""){
				objTmp.optionLabel = mKeyCode["0"+sourceData[i].parentInputBeanId]+"."+sourceData[i].inputBeanCode; 
			}else{
				objTmp.optionLabel = "in."+sourceData[i].inputBeanCode;
			}
			mKeyCode[objTmp.optionValue] = objTmp.optionLabel;
			codeResults[objTmp.optionLabel] = objTmp;
			idResults[objTmp.optionValue] = objTmp;
		}
	}
	
	var sourceDataOb = convertToJson($("form").find("input[name='jsonObjectDefinition']").val());
	if(sourceDataOb != undefined && sourceDataOb.length > 0) {
		for(var i = 0; i < sourceDataOb.length; i++) {
			var objTmp = new Object();
			objTmp.optionValue = "1"+sourceDataOb[i].objectDefinitionId;
			objTmp.output01 = 1;
			objTmp.output02 = sourceDataOb[i].parentObjectDefinitionId;
			objTmp.output03 = sourceDataOb[i].dataType;
			objTmp.output04 = sourceDataOb[i].arrayFlg;
			objTmp.output05 = sourceDataOb[i].objectDefinitionId;
			objTmp.output06 = sourceDataOb[i].objectDefinitionCode;
			if(sourceDataOb[i].parentObjectDefinitionId != undefined && sourceDataOb[i].parentObjectDefinitionId != ""){
				objTmp.optionLabel = mKeyCode["1"+sourceDataOb[i].parentObjectDefinitionId]+"."+sourceDataOb[i].objectDefinitionCode; 
			}else{
				objTmp.optionLabel = "ob."+sourceDataOb[i].objectDefinitionCode;
			}
			mKeyCode[objTmp.optionValue] = objTmp.optionLabel;
			codeResults[objTmp.optionLabel] = objTmp;
			idResults[objTmp.optionValue] = objTmp;
		}
	}
	var sourceData = convertToJson($("form").find("input[name='jsonOutputBean']").val());
	if(sourceData != undefined && sourceData.length > 0) {
		for(var i = 0; i < sourceData.length; i++) {
			var objTmp = new Object();
			objTmp.optionValue = "2"+sourceData[i].outputBeanId;
			objTmp.output01 = 2;
			objTmp.output02 = sourceData[i].parentOutputBeanId;
			objTmp.output03 = sourceData[i].dataType;
			objTmp.output04 = sourceData[i].arrayFlg;
			objTmp.output05 = sourceData[i].outputBeanId;
			objTmp.output06 = sourceData[i].outputBeanCode;
			
			if(sourceData[i].parentOutputBeanId != undefined && sourceData[i].parentOutputBeanId != ""){
				objTmp.optionLabel = mKeyCode["2"+sourceData[i].parentOutputBeanId]+"."+sourceData[i].outputBeanCode; 
			}else{
				objTmp.optionLabel = "ou."+sourceData[i].outputBeanCode;
			}
			mKeyCode[objTmp.optionValue] = objTmp.optionLabel;
			codeResults[objTmp.optionLabel] = objTmp;
			idResults[objTmp.optionValue] = objTmp;
		}
	}
	dataResult.codeResults = codeResults;
	dataResult.idResults = idResults;
	return dataResult;
}
function setParameterTypeOfAutocompleteBD (event){
	var obj = event.target;
	if(event.item != undefined){
		var type = event.item.output01;
		var dataType = event.item.output03;
		var arrayFlg = event.item.output04;
		$(obj).next().attr("parameterScope",type);
		$(obj).next().attr("dataType",dataType);
		$(obj).next().attr("arrayFlg",arrayFlg);
	}
	else{
		$(obj).next().attr("parameterScope","");
		$(obj).next().attr("dataType","");
		$(obj).next().attr("arrayFlg","");
	}
}
function mergeByProperty(arr1, arr2, prop) {
    _.each(arr2, function(arr2obj) {
        var arr1obj = _.find(arr1, function(arr1obj) {
            return arr1obj[prop] === arr2obj[prop];
        });
         
        //If the object already exist extend it with the new values from arr2, otherwise just add the new object to arr1
        arr1obj ? _.extend(arr1obj, arr2obj) : arr1.push(arr2obj);
    });
}
//This will sort your array
function bdOnChangeExternalObjectOfOu(event){
	var obj = event.target;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	if(event.item != undefined){
		$(obj).closest("tr").find("input[name$='outputBeanName']").val(event.item.optionLabel);
		$(obj).closest("tr").find("input[name$='outputBeanCode']").val(event.item.output01);
//		$(obj).closest("tr").find("label[name*='outputBeanCode']").text(event.item.output01);
		var table = $(obj).closest("table").attr("id");
		var actionTemplateId = table+"-columm-template";
		
		var tempAnchor = $(obj).closest('tr');
		var tempParentId = "";
		
		if(event.item.optionValue != ""){
			var level = 1;
			if(rgroup == undefined || rgroup == ""){
				level = 1;
			}else{
				level = (rgroup.match(/\./g) || []).length + 1;
			}
			var url = CONTEXT_PATH + "/externalobjectdefinition/getDetailsExternalObjectDefinition?externalObjectDefinitionId="+event.item.optionValue+"&level="+level+"&r="+Math.random();
			var data = $.qp.getData(url);
			if(data.length>0){
				for(var i=0;i<data.length;i++){
					if (tempParentId < data[i].parentId) {
						rgroup = $(newRow).closest("tr").attr("data-ar-rgroupindex");
					} else if (tempParentId > data[i].parentId) {
						rgroup = rgroup.slice(0, rgroup.lastIndexOf("."));
					}
					
					var newRow = $.qp.ar.addRow({link: obj,tableId:table,templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor),string:'after'}});
					
					$(newRow).find("input[name$='outputBeanName']").val(data[i].externalObjectAttributeName);
					$(newRow).find("label[name$='outputBeanName']").text(data[i].externalObjectAttributeName);
					$(newRow).find("input[name$='outputBeanCode']").val(data[i].externalObjectAttributeCode);
					$(newRow).find("label[name$='outputBeanCode']").text(data[i].externalObjectAttributeCode);
					$(newRow).find("input[name$='externalObjectDefinitionId']").val(data[i].externalObjectDefinitionId);
					$(newRow).find("input[name$='externalObjectAttributeId']").val(data[i].externalObjectAttributeId);
					$(newRow).find("input[name$='dataType']").val(data[i].dataType);
					$(newRow).find("label[name$='dataType']").text(CL_BD_DATATYPE[data[i].dataType]);
					$(newRow).find("input[name$='groupBaseTypeId']").val(data[i].groupBaseTypeId);
					$(newRow).find("input[name$='objectType']").val(3);
					$(newRow).find("input[name$='objectId']").val(data[i].externalObjectAttributeId);

					tempParentId = data[i].parentId;
					tempAnchor = $(newRow).closest('tr');
				}
			}
		}
	}else{
		$(obj).closest("tr").find("input[name$='outputBeanName']").val("");
		$(obj).closest("tr").find("input[name$='outputBeanCode']").val("");
//		$(obj).closest("tr").find("label[name*='outputBeanCode']").text("");
		$(obj).closest("tr").nextAll("tr[data-ar-rgroup^='"+rgroup+"']").each(function() {
			$(this).remove();
		});
	}
}
function sortByItemSequenceNo(a, b){
  var seqA = parseInt(a.itemSequenceNo);
  var seqB = parseInt(b.itemSequenceNo); 
  return ((seqA < seqB) ? -1 : ((seqA > seqB) ? 1 : 0));
}
function buildParameterBuilderForMessage(messageContent,type,isMultiple){
	var count = countParameterOfMessage(messageContent);
	if(isMultiple){
		if(count >0){
			count = count -1;
			messageContent = messageContent.replace(" {"+count+"} "," {INDEX OF ROW} ");
		}
		
	}
	for(var i= 0;i<count;i++){
//		var levelFlag = false;
		var content = "{"+i+"}";
		var template;
		if(i==0){
			var dataTemplate = {itemSequenceNo : i,parameterType:0};
			switch (type) {
			case 36:
				template = $("#item-validationcheck-parameter-timepicker-template").tmpl(dataTemplate);
				break;
			case 49:
			case 76:
			case 79:
				template = $("#item-validationcheck-parameter-datepicker-template").tmpl(dataTemplate);
				break;
			case 48:
			case 77:
			case 80:
				template = $("#item-validationcheck-parameter-datetimepicker-template").tmpl(dataTemplate);
				break;
			case 63:
			case 78:
			case 81:
				template = $("#item-validationcheck-parameter-datetimepicker-template").tmpl(dataTemplate);
				break;
			default:
				template = $("#item-validationcheck-parameter-template").tmpl(dataTemplate);
			}
			levelFlag = true;
		}else{
			var dataTemplate = {itemSequenceNo : i,parameterType:1};
			switch (type) {
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
			case 68:
			case 69:
			case 70:
			case 71:
			case 72:
			case 73:
			case 74:
			case 75:	
				template = $("#item-validationcheck-parameter-datetime-template").tmpl(dataTemplate);
				break;
			default:
				template = $("#item-validationcheck-parameter-textbox-template").tmpl(dataTemplate);
			}
			
		}
		$(template).attr("index",i);
		
//		if(levelFlag){
//			divReplace = "</span>"+$(template).prop('outerHTML')+"<span class=\"qp-bdesign-div-text pull-left text-primary\">";
//			divReplace += "</span><span class=\"qp-bdesign-div-text pull-left\">";
//		}else{
//			divReplace = "</span>"+$(template).prop('outerHTML')+"<span class=\"qp-bdesign-div-text pull-left\">";
//		}
		divReplace = "</span>"+$(template).prop('outerHTML')+"<span class=\"qp-bdesign-div-text pull-left\">";
		messageContent = messageContent.replace(content,divReplace);
	}
	return "<span class=\"qp-bdesign-div-text pull-left\">"+messageContent +"</span>";
}
function getInformationOfParameter(id,isPrefix){
	var data = getDataSourceMap().idResults;
	return data[id];
}
function compareDataType(currentDatatype,currentArrayFlg,oldDatatype,oldArrayFlg){
	if(currentDatatype == '14' || currentDatatype == '16' || currentDatatype == '17'){
		currentDatatype = '0';
	}
	if(oldDatatype == '14' || oldDatatype == '16' || oldDatatype == '17'){
		oldDatatype = '0';
	}
//	if(currentDatatype.toString() == oldDatatype.toString() && currentArrayFlg.toString() == oldArrayFlg.toString()){
	if(currentDatatype.toString() == oldDatatype.toString()){
		return true;
	}else
		return false;
}

function loadComponentByFunctiontype(functionType){
	switch (functionType.toString()) {
	case "1":
		$("[elementtype='4']").hide();
		$("[elementtype='11']").hide();
		$("[elementtype='12']").hide();
		$("[elementtype='23']").hide();
		$("[elementtype='24']").hide();
		break;
	case "2":
		$("[elementtype='4']").hide();
		$("[elementtype='12']").hide();
		$("[elementtype='23']").hide();
		$("[elementtype='24']").hide();
		break;
	case "3":
		$("[elementtype='4']").hide();
		$("[elementtype='11']").hide();
		$("[elementtype='12']").hide();
		$("[elementtype='23']").hide();
		$("[elementtype='24']").hide();
		break;
	default:
		break;
	}
}
function changeModuleTypeOfBlogic(thiz,isNotResetData, advanceReturnTypeFlag, advanceRequestMethodFlag,blogicType){
	var functionType = "0";
	functionType = $(thiz).val();
//	$('.panel-collapse').collapse('hide');
	loadComponentByFunctiontype(blogicType);
	// change arg02 of function design autocomplete
	var nextInput = $(thiz).closest("#tbl-blogicinformation").find("input[name$='moduleIdAutocomplete']");
	if(nextInput.length >0){
		var nextHidden = nextInput.next("input[type=hidden]");
		nextInput.attr("arg04",functionType);
		if(!isNotResetData){
			nextInput.val("");
			nextHidden.val("");
			clearModuleAutocomplete("");
		}
		nextInput.data("ui-autocomplete")._trigger("close");		
	}
	if(blogicType == "0"){
		$(thiz).closest("table").find("td[advanceReturnType='0'],td[advanceReturnType='1']").show();
		
		if (advanceReturnTypeFlag == "true") {
			$(thiz).closest("table").find("td[advanceReturnType='1']").hide();
			onchangeReturnType($(thiz).closest("#tbl-blogicinformation").find("input[name='returnType']:checked"));
		}else {
			$(thiz).closest("table").find("td[advanceReturnType='0']").hide();
		}
		
		$(thiz).closest("table").find("td[advanceRequestMethod='0'],td[advanceRequestMethod='1']").show();
		if (advanceRequestMethodFlag == "true") {
			$(thiz).closest("table").find("td[advanceRequestMethod='1']").hide();
		}else {
			$(thiz).closest("table").find("td[advanceRequestMethod='0']").hide();
		}
		
		var requestMethodItem = $(thiz).closest("#tbl-blogicinformation").find("input[name='requestMethod']:checked");
		if(!isNotResetData){
			onchangeRequestMethod(requestMethodItem);
		}else{
			if(requestMethod == "4"){
				$(thiz).closest("#tbl-blogicinformation").find("th[requestMethod='4'],td[requestMethod='4']").show();
				$(thiz).closest("#tbl-blogicinformation").find("label[name='lblScreenName']").text($.qp.getModuleMessage("sc.businesslogicdesign.0257"));
			}else{
				$(thiz).closest("#tbl-blogicinformation").find("th[requestMethod='4'],td[requestMethod='4']").hide();
				$(thiz).closest("#tbl-blogicinformation").find("label[name='lblScreenName']").text($.qp.getModuleMessage("sc.businesslogicdesign.0256"));
			}
		}
	}
}
function onchangeModuleTypeOfBlogic(thiz,isNotResetData){
	var result = $.qp.confirm($.qp.getModuleMessage('err.blogiccomponent.0142'));
	if (result == true) {
		var arrAffectNode = new Array();
		// affect node of blogic
		$("#designArea").find(".execution-class").each(function(i) {
			var componentType = $(this).attr("componenttype");
			if(componentType == "15" || componentType == "16" || componentType == "17"	|| componentType == "18"
				|| componentType == "4" || componentType == "11" || componentType == "12" || componentType == "23" || componentType == "24"){
				var targetBoxId = $(this).attr("id");
				arrAffectNode.push(targetBoxId);
			}
		});
		if(arrAffectNode.length > 0){
			for(var k =0;k < arrAffectNode.length; k++){
				var targetBoxId = arrAffectNode[k];
				var connectionSources = instanceOfBlogic.getConnections({ source:targetBoxId });
				var connectionTargets = instanceOfBlogic.getConnections({ target:targetBoxId });
				for(var i = 0;i<connectionSources.length;i++){
					processDeleteConnectionOfDeletedNode(connectionSources[i],instanceOfBlogic);
				}
				for(var i = 0;i<connectionTargets.length;i++){
					processDeleteConnectionOfDeletedNode(connectionTargets[i],instanceOfBlogic);
				}
				instanceOfBlogic.removeAllEndpoints(targetBoxId);
				instanceOfBlogic.detach(targetBoxId);
				$("#" + targetBoxId).remove();
			}
			instanceOfBlogic.repaintEverything();
		}
		
		//clear data of outputbean
		$('#tbl_outputbean_list_define').find('tbody > tr').remove();

		changeModuleTypeOfBlogic(thiz,isNotResetData, $('#advanceReturnTypeFlag').val(), $('#advanceRequestMethodFlag').val());
		
	} else {
		//revert old value
		var functionType = $(thiz).val();
		var oldFunctionType = functionType == "0" ? "1" : "0";
		$(thiz).closest("td").find(":radio[name='moduleType'][value='"+oldFunctionType+"']").prop("checked",true);
	}
}

function getInstanceOfContainer(obj){
	var container = $(obj).closest(".design-area").attr("id");
	if(container ==  "designAreaOfNestedLogic"){
		return instanceOfNestedNode;
	}else{
		return instanceOfBlogic;
	}
}

// Input bean
var tempAnchor = null;
function bdOnChangeCommonObjectOfIn(event){
	var obj = event.target;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	$(obj).closest("tr").nextAll("tr[data-ar-rgroup^='"+rgroup+"']").each(function() {
		$(this).remove();
	});
	$(obj).closest("td").find("input[name$='moduleId']").val("");
	if(event.item != undefined){
		$(obj).closest("tr").find("input[name*='inputBeanName']").val(event.item.optionLabel);
		$(obj).closest("tr").find("input[name*='inputBeanCode']").val(event.item.output01);
		$(obj).closest("tr").find("input[name*='objectType']").val(0);
//		$(obj).closest("tr").find("input[name*='objectId']").val(event.item.optionValue);
		$(obj).closest("tr").find("input[name*='objectFlg']").val(true);
//		$(obj).closest("tr").find("label[name*='inputBeanCode']").text(event.item.output01);
		var table = $(obj).closest("table").attr("id");
		var actionTemplateId = table+"-common-object-columm-template";
		if(rgroup == undefined || rgroup == ""){
			level = 1;
		}else{
			level = (rgroup.match(/\./g) || []).length + 1;
		}
		if(event.item.optionValue != ""){
			var url = CONTEXT_PATH + "/commonobjectdefinition/getDetailsCommonObjectDefinition?commonObjectDefinitionId="+event.item.optionValue+"&level="+level+"&r="+Math.random();
			var data = $.qp.getData(url);
			if(data.length>0){
				data = data.sort(function (a, b){
					return a.commonObjectAttributeId - b.commonObjectAttributeId;
				});
				tempAnchor = obj;
				buildInputCommonObjectAttributes(data, obj);
			}
		}
		$(obj).closest("td").find("input[name$='moduleId']").val(event.item.output02);
	}else{
		$(obj).closest("tr").find("input[name*='inputBeanName']").val("");
		$(obj).closest("tr").find("input[name*='inputBeanCode']").val("");
		$(obj).closest("tr").find("input[name*='objectType']").val(-1);
		$(obj).closest("tr").find("input[name*='objectId']").val("");
	}
}
function buildInputCommonObjectAttributes(data, obj) {
	var tempParentId = null;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	var table = $(obj).closest("table").attr("id");
	
    for(var i=0;i<data.length;i++){
		if (data[i].dataType == 16) {
	    	var actionTemplateId = table+"-column-template";
			var newRow = $.qp.ar.addRow({link: obj,tableId:table,templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});
			
			var curObj = data[i].commonObjectDefinition;
			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			
			$(newRow).find("input[name$='groupId']").val(rgroup);
			$(newRow).find("input[name$='tableIndex']").text(tableIndex);
			$(newRow).find("input[name$='inputBeanName']").val(data[i].commonObjectAttributeName);
			$(newRow).find("label[name$='inputBeanName']").text(data[i].commonObjectAttributeName);
			$(newRow).find("input[name$='inputBeanCode']").val(data[i].commonObjectAttributeCode);
			$(newRow).find("label[name$='inputBeanCode']").text(data[i].commonObjectAttributeCode);
			$(newRow).find("input[name$='commonObjectDefinitionId']").val(curObj.commonObjectDefinitionId);
			$(newRow).find("input[name$='dataType']").val(data[i].dataType);
			$(newRow).find("input[name$='objectType']").val(2);
			$(newRow).find("input[name$='objectId']").val(data[i].commonObjectAttributeId);
			var dataType = CL_BD_DATATYPE[data[i].dataType];
			if(eval(data[i].arrayFlg)){
				dataType += "[]";
			}
			$(newRow).find("label[name$='dataType']").text(dataType);
			$(newRow).find("input[name$='arrayFlg']").val(data[i].arrayFlg);
			$(newRow).find("input[name$='objectFlg']").val(false);
			tempAnchor = $(newRow);
			
			buildInputCommonObjectAttributes(data[i].commonObjectDefinition.commonObjectAttributes, newRow);
		} else if (data[i].dataType == 17) {
	    	var actionTemplateId = table+"-column-template";
			var newRow = $.qp.ar.addRow({link: obj,tableId:table,templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});
			
			var curObj = data[i].externalObjectDefinition;
			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			
			$(newRow).find("input[name$='groupId']").val(rgroup);
			$(newRow).find("input[name$='tableIndex']").text(tableIndex);
			$(newRow).find("input[name$='inputBeanName']").val(data[i].commonObjectAttributeName);
			$(newRow).find("label[name$='inputBeanName']").text(data[i].commonObjectAttributeName);
			$(newRow).find("input[name$='inputBeanCode']").val(data[i].commonObjectAttributeCode);
			$(newRow).find("label[name$='inputBeanCode']").text(data[i].commonObjectAttributeCode);
			$(newRow).find("input[name$='commonObjectDefinitionId']").val(curObj.externalObjectDefinitionId);
			$(newRow).find("input[name$='dataType']").val(data[i].dataType);
			$(newRow).find("input[name$='objectType']").val(2);
			$(newRow).find("input[name$='objectId']").val(data[i].commonObjectAttributeId);
			var dataType = CL_BD_DATATYPE[data[i].dataType];
			if(eval(data[i].arrayFlg)){
				dataType += "[]";
			}
			$(newRow).find("label[name$='dataType']").text(dataType);
			$(newRow).find("input[name$='arrayFlg']").val(data[i].arrayFlg);
			$(newRow).find("input[name$='objectFlg']").val(false);
			tempAnchor = $(newRow);
			
			buildInputExternalObjectAttributes(data[i].externalObjectDefinition.externalObjectAttributes, newRow);
		} else {
			if (tempParentId < data[i].parentCommonObjectAttributeId) {
				rgroup = $(newRow).closest("tr").attr("data-ar-rgroupindex");
			} else if (tempParentId > data[i].parentCommonObjectAttributeId) {
				rgroup = rgroup.slice(0, rgroup.lastIndexOf("."));
			}
			
			var actionTemplateId = table+"-column-template";
			var newRow = $.qp.ar.addRow({link: obj,tableId:table,templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});
			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			
			$(newRow).find("input[name$='groupId']").val(rgroup);
			$(newRow).find("input[name$='tableIndex']").text(tableIndex);
			$(newRow).find("input[name$='inputBeanName']").val(data[i].commonObjectAttributeName);
			$(newRow).find("label[name$='inputBeanName']").text(data[i].commonObjectAttributeName);
			$(newRow).find("input[name$='inputBeanCode']").val(data[i].commonObjectAttributeCode);
			$(newRow).find("label[name$='inputBeanCode']").text(data[i].commonObjectAttributeCode);
			$(newRow).find("input[name$='commonObjectDefinitionId']").val(data[i].commonObjectDefinitionId);
			$(newRow).find("input[name$='dataType']").val(data[i].dataType);
			$(newRow).find("input[name$='objectType']").val(2);
			$(newRow).find("input[name$='objectId']").val(data[i].commonObjectAttributeId);
			var dataType = CL_BD_DATATYPE[data[i].dataType];
			if(eval(data[i].arrayFlg)){
				dataType += "[]";
			}
			$(newRow).find("label[name$='dataType']").text(dataType);
			$(newRow).find("input[name$='arrayFlg']").val(data[i].arrayFlg);
			$(newRow).find("input[name$='objectFlg']").val(false);
			tempAnchor = $(newRow);
			
			tempParentId = data[i].parentCommonObjectAttributeId;
		}
	}
}
function bdOnChangeExternalObjectOfIn(event){
	var obj = event.target;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	$(obj).closest("tr").nextAll("tr[data-ar-rgroup^='"+rgroup+"']").each(function() {
		$(this).remove();
	});
	$(obj).closest("td").find("input[name$='moduleId']").val("");
	if(event.item != undefined){
		$(obj).closest("tr").find("input[name$='inputBeanName']").val(event.item.optionLabel);
		$(obj).closest("tr").find("input[name$='inputBeanCode']").val(event.item.output01);
		$(obj).closest("tr").find("input[name$='objectType']").val(1);
		$(obj).closest("tr").find("input[name$='objectId']").val(event.item.optionValue);
		$(obj).closest("tr").find("input[name$='objectFlg']").val(true);
		var table = $(obj).closest("table").attr("id");
		var actionTemplateId = table+"-external-object-columm-template";
		
		if(event.item.optionValue != ""){
			var level = 1;
			if(rgroup == undefined || rgroup == ""){
				level = 1;
			}else{
				level = (rgroup.match(/\./g) || []).length + 1;
			}
			var url = CONTEXT_PATH + "/externalobjectdefinition/getDetailsExternalObjectDefinition?externalObjectDefinitionId="+event.item.optionValue+"&level="+level+"&r="+Math.random();
			var data = $.qp.getData(url);
			if(data.length>0){
				data = data.sort(function (a, b){
					return a.commonObjectAttributeId - b.commonObjectAttributeId;
				});

				tempAnchor = obj;
				buildInputExternalObjectAttributes(data, obj);
			}
		}
		$(obj).closest("td").find("input[name$='moduleId']").val(event.item.output02);
	}else{
		$(obj).closest("tr").find("input[name$='inputBeanName']").val("");
		$(obj).closest("tr").find("input[name$='inputBeanCode']").val("");
		$(obj).closest("tr").find("input[name$='objectType']").val(-1);
		$(obj).closest("tr").find("input[name$='objectId']").val("");
	}
}
function buildInputExternalObjectAttributes(data, obj) {
	var tempParentId = null;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	var table = $(obj).closest("table").attr("id");
	
    for(var i=0;i<data.length;i++){
		if (data[i].dataType == 17) {
	    	var actionTemplateId = table+"-column-template";
			var newRow = $.qp.ar.addRow({link: obj,tableId:table,templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});

			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			var curObj = data[i].externalObjectDefinition;

			$(newRow).find("input[name$='groupId']").val(rgroup);
			$(newRow).find("input[name$='tableIndex']").text(tableIndex);
			$(newRow).find("input[name$='inputBeanName']").val(data[i].externalObjectAttributeName);
			$(newRow).find("label[name$='inputBeanName']").text(data[i].externalObjectAttributeName);
			$(newRow).find("input[name$='inputBeanCode']").val(data[i].externalObjectAttributeCode);
			$(newRow).find("label[name$='inputBeanCode']").text(data[i].externalObjectAttributeCode);
			$(newRow).find("input[name$='commonObjectDefinitionId']").val(curObj.externalObjectDefinitionId);
			$(newRow).find("input[name$='dataType']").val(data[i].dataType);
			$(newRow).find("input[name$='objectType']").val(3);
			$(newRow).find("input[name$='objectId']").val(data[i].externalObjectAttributeId);
			var dataType = CL_BD_DATATYPE[data[i].dataType];
			if(eval(data[i].arrayFlg)){
				dataType += "[]";
			}
			$(newRow).find("label[name$='dataType']").text(dataType);
			$(newRow).find("input[name$='arrayFlg']").val(data[i].arrayFlg);
			$(newRow).find("label[name$='objectFlg']").text(false);
			tempAnchor = $(newRow);
			
			buildInputExternalObjectAttributes(data[i].externalObjectDefinition.externalObjectAttributes, newRow);
		} else {
			if (tempParentId < data[i].parentExternalObjectAttributeId) {
				rgroup = $(newRow).closest("tr").attr("data-ar-rgroupindex");
			} else if (tempParentId > data[i].parentExternalObjectAttributeId) {
				rgroup = rgroup.slice(0, rgroup.lastIndexOf("."));
			}
			
			var actionTemplateId = table+"-column-template";
			var newRow = $.qp.ar.addRow({link: obj,tableId:table,templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});
			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			
			$(newRow).find("input[name$='groupId']").val(rgroup);
			$(newRow).find("input[name$='tableIndex']").text(tableIndex);
			$(newRow).find("input[name$='inputBeanName']").val(data[i].externalObjectAttributeName);
			$(newRow).find("label[name$='inputBeanName']").text(data[i].externalObjectAttributeName);
			$(newRow).find("input[name$='inputBeanCode']").val(data[i].externalObjectAttributeCode);
			$(newRow).find("label[name$='inputBeanCode']").text(data[i].externalObjectAttributeCode);
			$(newRow).find("input[name$='commonObjectDefinitionId']").val(data[i].externalObjectAttributeId);
			$(newRow).find("input[name$='dataType']").val(data[i].dataType);
			$(newRow).find("input[name$='objectType']").val(3);
			$(newRow).find("input[name$='objectId']").val(data[i].externalObjectAttributeId);
			var dataType = CL_BD_DATATYPE[data[i].dataType];
			if(eval(data[i].arrayFlg)){
				dataType += "[]";
			}
			$(newRow).find("label[name$='dataType']").text(dataType);
			$(newRow).find("input[name$='arrayFlg']").val(data[i].arrayFlg);
			$(newRow).find("label[name$='objectFlg']").text(false);
			tempAnchor = $(newRow);
			tempParentId = data[i].parentExternalObjectAttributeId;
		}
	}
}

// Output bean
function bdOnChangeCommonObjectOfOu(event){
	var obj = event.target;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	$(obj).closest("tr").nextAll("tr[data-ar-rgroup^='"+rgroup+"']").each(function() {
		$(this).remove();
	});
	$(obj).closest("td").find("input[name$='moduleId']").val("");
	if(event.item != undefined){
		$(obj).closest("tr").find("input[name$='outputBeanName']").val(event.item.optionLabel);
		$(obj).closest("tr").find("input[name$='outputBeanCode']").val(event.item.output01);
		$(obj).closest("tr").find("input[name$='objectType']").val(0);
//		$(obj).closest("tr").find("input[name$='objectId']").val(event.item.optionValue);
		$(obj).closest("tr").find("input[name$='objectFlg']").val(true);
//		$(obj).closest("tr").find("label[name$='outputBeanCode']").text(event.item.output01);
		var table = $(obj).closest("table").attr("id");
		var actionTemplateId = table+"-common-object-columm-template";
		
		if(event.item.optionValue != ""){
			var level = 1;
			if(rgroup == undefined || rgroup == ""){
				level = 1;
			}else{
				level = (rgroup.match(/\./g) || []).length + 1;
			}
			var url = CONTEXT_PATH + "/commonobjectdefinition/getDetailsCommonObjectDefinition?commonObjectDefinitionId="+event.item.optionValue+"&level="+level+"&r="+Math.random();
			var data = $.qp.getData(url);
			if(data.length>0){
				data = data.sort(function (a, b){
					return a.commonObjectAttributeId - b.commonObjectAttributeId;
				});

				tempAnchor = obj;
				buildOutputCommonObjectAttributes(data, obj);
			}
		}
		$(obj).closest("td").find("input[name$='moduleId']").val(event.item.output02);
	}else{
		$(obj).closest("tr").find("input[name$='outputBeanName']").val("");
		$(obj).closest("tr").find("input[name$='outputBeanCode']").val("");
		$(obj).closest("tr").find("input[name$='objectType']").val(-1);
		$(obj).closest("tr").find("input[name$='objectId']").val("");
//		$(obj).closest("tr").find("label[name$='outputBeanCode']").text("");
	}
}
function buildOutputCommonObjectAttributes(data, obj) {
	var tempParentId = null;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	var table = $(obj).closest("table").attr("id");
	
    for(var i=0;i<data.length;i++){
		if (data[i].dataType == 16) {
	    	var actionTemplateId = table+"-column-template";
			var newRow = $.qp.ar.addRow({link: obj,tableId:table,templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});
			
			var curObj = data[i].commonObjectDefinition;
			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			
			$(newRow).find("input[name$='groupId']").val(rgroup);
			$(newRow).find("input[name$='tableIndex']").text(tableIndex);
			$(newRow).find("input[name$='outputBeanName']").val(data[i].commonObjectAttributeName);
			$(newRow).find("label[name$='outputBeanName']").text(data[i].commonObjectAttributeName);
			$(newRow).find("input[name$='outputBeanCode']").val(data[i].commonObjectAttributeCode);
			$(newRow).find("label[name$='outputBeanCode']").text(data[i].commonObjectAttributeCode);
			$(newRow).find("input[name$='commonObjectDefinitionId']").val(curObj.commonObjectDefinitionId);
			$(newRow).find("input[name$='dataType']").val(data[i].dataType);
			$(newRow).find("input[name$='objectType']").val(2);
			$(newRow).find("input[name$='objectId']").val(data[i].commonObjectAttributeId);
			var dataType = CL_BD_DATATYPE[data[i].dataType];
			if(eval(data[i].arrayFlg)){
				dataType += "[]";
			}
			$(newRow).find("label[name$='dataType']").text(dataType);
			$(newRow).find("input[name$='arrayFlg']").val(data[i].arrayFlg);
			$(newRow).find("input[name$='objectFlg']").val(false);
			tempAnchor = $(newRow);
			
			buildOutputCommonObjectAttributes(data[i].commonObjectDefinition.commonObjectAttributes, newRow);
		} else if (data[i].dataType == 17) {
	    	var actionTemplateId = table+"-column-template";
			var newRow = $.qp.ar.addRow({link: obj,tableId:table,templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});
			
			var curObj = data[i].externalObjectDefinition;
			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			
			$(newRow).find("input[name$='groupId']").val(rgroup);
			$(newRow).find("input[name$='tableIndex']").text(tableIndex);
			$(newRow).find("input[name$='outputBeanName']").val(data[i].commonObjectAttributeName);
			$(newRow).find("label[name$='outputBeanName']").text(data[i].commonObjectAttributeName);
			$(newRow).find("input[name$='outputBeanCode']").val(data[i].commonObjectAttributeCode);
			$(newRow).find("label[name$='outputBeanCode']").text(data[i].commonObjectAttributeCode);
			$(newRow).find("input[name$='commonObjectDefinitionId']").val(curObj.externalObjectDefinitionId);
			$(newRow).find("input[name$='dataType']").val(data[i].dataType);
			$(newRow).find("input[name$='objectType']").val(2);
			$(newRow).find("input[name$='objectId']").val(data[i].commonObjectAttributeId);
			var dataType = CL_BD_DATATYPE[data[i].dataType];
			if(eval(data[i].arrayFlg)){
				dataType += "[]";
			}
			$(newRow).find("label[name$='dataType']").text(dataType);
			$(newRow).find("input[name$='arrayFlg']").val(data[i].arrayFlg);
			$(newRow).find("input[name$='objectFlg']").val(false);
			tempAnchor = $(newRow);
			
			buildOutputExternalObjectAttributes(data[i].externalObjectDefinition.externalObjectAttributes, newRow);
		} else {
			if (tempParentId < data[i].parentCommonObjectAttributeId) {
				rgroup = $(newRow).closest("tr").attr("data-ar-rgroupindex");
			} else if (tempParentId > data[i].parentCommonObjectAttributeId) {
				rgroup = rgroup.slice(0, rgroup.lastIndexOf("."));
			}
			
			var actionTemplateId = table+"-column-template";
			var newRow = $.qp.ar.addRow({link: obj,tableId:table,templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});
			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			
			$(newRow).find("input[name$='groupId']").val(rgroup);
			$(newRow).find("input[name$='tableIndex']").text(tableIndex);
			$(newRow).find("input[name$='outputBeanName']").val(data[i].commonObjectAttributeName);
			$(newRow).find("label[name$='outputBeanName']").text(data[i].commonObjectAttributeName);
			$(newRow).find("input[name$='outputBeanCode']").val(data[i].commonObjectAttributeCode);
			$(newRow).find("label[name$='outputBeanCode']").text(data[i].commonObjectAttributeCode);
			$(newRow).find("input[name$='commonObjectDefinitionId']").val(data[i].commonObjectDefinitionId);
			$(newRow).find("input[name$='dataType']").val(data[i].dataType);
			$(newRow).find("input[name$='objectType']").val(2);
			$(newRow).find("input[name$='objectId']").val(data[i].commonObjectAttributeId);
			var dataType = CL_BD_DATATYPE[data[i].dataType];
			if(eval(data[i].arrayFlg)){
				dataType += "[]";
			}
			$(newRow).find("label[name$='dataType']").text(dataType);
			$(newRow).find("input[name$='arrayFlg']").val(data[i].arrayFlg);
			$(newRow).find("input[name$='objectFlg']").val(false);
			tempAnchor = $(newRow);
			
			tempParentId = data[i].parentCommonObjectAttributeId;
		}
	}
}
function bdOnChangeExternalObjectOfOu(event){
	var obj = event.target;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	$(obj).closest("tr").nextAll("tr[data-ar-rgroup^='"+rgroup+"']").each(function() {
		$(this).remove();
	});
	$(obj).closest("td").find("input[name$='moduleId']").val("");
	if(event.item != undefined){
		$(obj).closest("tr").find("input[name$='outputBeanName']").val(event.item.optionLabel);
		$(obj).closest("tr").find("input[name$='outputBeanCode']").val(event.item.output01);
		$(obj).closest("tr").find("input[name$='objectType']").val(1);
		$(obj).closest("tr").find("input[name$='objectId']").val(event.item.optionValue);
		$(obj).closest("tr").find("input[name$='objectFlg']").val(true);
		var table = $(obj).closest("table").attr("id");
		var actionTemplateId = table+"-external-object-columm-template";
		
		if(event.item.optionValue != ""){
			var level = 1;
			if(rgroup == undefined || rgroup == ""){
				level = 1;
			}else{
				level = (rgroup.match(/\./g) || []).length + 1;
			}
			var url = CONTEXT_PATH + "/externalobjectdefinition/getDetailsExternalObjectDefinition?externalObjectDefinitionId="+event.item.optionValue+"&level="+level+"&r="+Math.random();
			var data = $.qp.getData(url);
			if(data.length>0){
				data = data.sort(function (a, b){
					return a.commonObjectAttributeId - b.commonObjectAttributeId;
				});

				tempAnchor = obj;
				buildOutputExternalObjectAttributes(data, obj);
			}
		}
		$(obj).closest("td").find("input[name$='moduleId']").val(event.item.output02);
	}else{
		$(obj).closest("tr").find("input[name$='outputBeanName']").val("");
		$(obj).closest("tr").find("input[name$='outputBeanCode']").val("");
		$(obj).closest("tr").find("input[name$='objectType']").val(-1);
		$(obj).closest("tr").find("input[name$='objectId']").val("");
	}
}
function buildOutputExternalObjectAttributes(data, obj) {
	var tempParentId = null;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	var table = $(obj).closest("table").attr("id");
	
    for(var i=0;i<data.length;i++){
		if (data[i].dataType == 17) {
	    	var actionTemplateId = table+"-column-template";
			var newRow = $.qp.ar.addRow({link: obj,tableId:table,templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});

			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			var curObj = data[i].externalObjectDefinition;

			$(newRow).find("input[name$='groupId']").val(rgroup);
			$(newRow).find("input[name$='tableIndex']").text(tableIndex);
			$(newRow).find("input[name$='outputBeanName']").val(data[i].externalObjectAttributeName);
			$(newRow).find("label[name$='outputBeanName']").text(data[i].externalObjectAttributeName);
			$(newRow).find("input[name$='outputBeanCode']").val(data[i].externalObjectAttributeCode);
			$(newRow).find("label[name$='outputBeanCode']").text(data[i].externalObjectAttributeCode);
			$(newRow).find("input[name$='commonObjectDefinitionId']").val(curObj.externalObjectDefinitionId);
			$(newRow).find("input[name$='dataType']").val(data[i].dataType);
			$(newRow).find("input[name$='objectType']").val(3);
			$(newRow).find("input[name$='objectId']").val(data[i].externalObjectAttributeId);
			var dataType = CL_BD_DATATYPE[data[i].dataType];
			if(eval(data[i].arrayFlg)){
				dataType += "[]";
			}
			$(newRow).find("label[name$='dataType']").text(dataType);
			$(newRow).find("input[name$='arrayFlg']").val(data[i].arrayFlg);
			$(newRow).find("label[name$='objectFlg']").text(false);
			tempAnchor = $(newRow);
			
			buildOutputExternalObjectAttributes(data[i].externalObjectDefinition.externalObjectAttributes, newRow);
		} else {
			if (tempParentId < data[i].parentExternalObjectAttributeId) {
				rgroup = $(newRow).closest("tr").attr("data-ar-rgroupindex");
			} else if (tempParentId > data[i].parentExternalObjectAttributeId) {
				rgroup = rgroup.slice(0, rgroup.lastIndexOf("."));
			}
			
			var actionTemplateId = table+"-column-template";
			var newRow = $.qp.ar.addRow({link: obj,tableId:table,templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});
			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			
			$(newRow).find("input[name$='groupId']").val(rgroup);
			$(newRow).find("input[name$='tableIndex']").text(tableIndex);
			$(newRow).find("input[name$='outputBeanName']").val(data[i].externalObjectAttributeName);
			$(newRow).find("label[name$='outputBeanName']").text(data[i].externalObjectAttributeName);
			$(newRow).find("input[name$='outputBeanCode']").val(data[i].externalObjectAttributeCode);
			$(newRow).find("label[name$='outputBeanCode']").text(data[i].externalObjectAttributeCode);
			$(newRow).find("input[name$='commonObjectDefinitionId']").val(data[i].externalObjectAttributeId);
			$(newRow).find("input[name$='dataType']").val(data[i].dataType);
			$(newRow).find("input[name$='objectType']").val(3);
			$(newRow).find("input[name$='objectId']").val(data[i].externalObjectAttributeId);
			var dataType = CL_BD_DATATYPE[data[i].dataType];
			if(eval(data[i].arrayFlg)){
				dataType += "[]";
			}
			$(newRow).find("label[name$='dataType']").text(dataType);
			$(newRow).find("input[name$='arrayFlg']").val(data[i].arrayFlg);
			$(newRow).find("label[name$='objectFlg']").text(false);
			tempAnchor = $(newRow);
			tempParentId = data[i].parentExternalObjectAttributeId;
		}
	}
}

// Object Definition
function bdOnChangeCommonObjectOfOb(event){
	var obj = event.target;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	$(obj).closest("tr").nextAll("tr[data-ar-rgroup^='"+rgroup+"']").each(function() {
		$(this).remove();
	});
	$(obj).closest("td").find("input[name$='moduleId']").val("");
	if(event.item != undefined){
		$(obj).closest("tr").find("input[name$='objectDefinitionName']").val(event.item.optionLabel);
		$(obj).closest("tr").find("input[name$='objectDefinitionCode']").val(event.item.output01);
		$(obj).closest("tr").find("input[name$='objectType']").val(0);
//		$(obj).closest("tr").find("input[name$='objectId']").val(event.item.optionValue);
		$(obj).closest("tr").find("input[name$='objectFlg']").val(true);
		var table = $(obj).closest("table").attr("id");
		var actionTemplateId = table+"-common-object-columm-template";
		
		if(event.item.optionValue != ""){
			var level = 1;
			if(rgroup == undefined || rgroup == ""){
				level = 1;
			}else{
				level = (rgroup.match(/\./g) || []).length + 1;
			}
			var url = CONTEXT_PATH + "/commonobjectdefinition/getDetailsCommonObjectDefinition?commonObjectDefinitionId="+event.item.optionValue+"&level="+level+"&r="+Math.random();
			var data = $.qp.getData(url);
			if(data.length>0){
				data = data.sort(function (a, b){
					return a.commonObjectAttributeId - b.commonObjectAttributeId;
				});

				tempAnchor = obj;
				buildODCommonObjectAttributes(data, obj);
			}
		}
		$(obj).closest("td").find("input[name$='moduleId']").val(event.item.output02);
	}else{
		$(obj).closest("tr").find("input[name$='objectDefinitionName']").val("");
		$(obj).closest("tr").find("input[name$='objectDefinitionCode']").val("");
		$(obj).closest("tr").find("input[name$='objectType']").val(-1);
		$(obj).closest("tr").find("input[name$='objectId']").val("");
	}
}
function buildODCommonObjectAttributes(data, obj) {
	var tempParentId = null;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	var table = $(obj).closest("table").attr("id");
	
    for(var i=0;i<data.length;i++){
		if (data[i].dataType == 16) {
	    	var actionTemplateId = table+"-column-template";
			var newRow = $.qp.ar.addRow({link: obj,tableId:table,templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});
			
			var curObj = data[i].commonObjectDefinition;
			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			
			$(newRow).find("input[name$='groupId']").val(rgroup);
			$(newRow).find("input[name$='tableIndex']").text(tableIndex);
			$(newRow).find("input[name$='objectDefinitionName']").val(data[i].commonObjectAttributeName);
			$(newRow).find("label[name$='objectDefinitionName']").text(data[i].commonObjectAttributeName);
			$(newRow).find("input[name$='objectDefinitionCode']").val(data[i].commonObjectAttributeCode);
			$(newRow).find("label[name$='objectDefinitionCode']").text(data[i].commonObjectAttributeCode);
			$(newRow).find("input[name$='commonObjectDefinitionId']").val(curObj.commonObjectDefinitionId);
			$(newRow).find("input[name$='dataType']").val(data[i].dataType);
			$(newRow).find("input[name$='objectType']").val(2);
			$(newRow).find("input[name$='objectId']").val(data[i].commonObjectAttributeId);
			var dataType = CL_BD_DATATYPE[data[i].dataType];
			if(eval(data[i].arrayFlg)){
				dataType += "[]";
			}
			$(newRow).find("label[name$='dataType']").text(dataType);
			$(newRow).find("input[name$='arrayFlg']").val(data[i].arrayFlg);
			$(newRow).find("input[name$='objectFlg']").val(false);
			tempAnchor = $(newRow);
			
			buildODCommonObjectAttributes(data[i].commonObjectDefinition.commonObjectAttributes, newRow);
		} else if (data[i].dataType == 17) {
	    	var actionTemplateId = table+"-column-template";
			var newRow = $.qp.ar.addRow({link: obj,tableId:table,templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});
			
			var curObj = data[i].externalObjectDefinition;
			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			
			$(newRow).find("input[name$='groupId']").val(rgroup);
			$(newRow).find("input[name$='tableIndex']").text(tableIndex);
			$(newRow).find("input[name$='objectDefinitionName']").val(curObj.externalObjectDefinitionName);
			$(newRow).find("label[name$='objectDefinitionName']").text(curObj.externalObjectDefinitionName);
			$(newRow).find("input[name$='objectDefinitionCode']").val(data[i].commonObjectAttributeCode);
			$(newRow).find("label[name$='objectDefinitionCode']").text(data[i].commonObjectAttributeCode);
			$(newRow).find("input[name$='commonObjectDefinitionId']").val(curObj.externalObjectDefinitionId);
			$(newRow).find("input[name$='dataType']").val(data[i].dataType);
			$(newRow).find("input[name$='objectType']").val(2);
			$(newRow).find("input[name$='objectId']").val(data[i].commonObjectAttributeId);
			var dataType = CL_BD_DATATYPE[data[i].dataType];
			if(eval(data[i].arrayFlg)){
				dataType += "[]";
			}
			$(newRow).find("label[name$='dataType']").text(dataType);
			$(newRow).find("input[name$='arrayFlg']").val(data[i].arrayFlg);
			$(newRow).find("input[name$='objectFlg']").val(false);
			tempAnchor = $(newRow);
			
			buildODExternalObjectAttributes(data[i].externalObjectDefinition.externalObjectAttributes, newRow);
		} else {
			if (tempParentId < data[i].parentCommonObjectAttributeId) {
				rgroup = $(newRow).closest("tr").attr("data-ar-rgroupindex");
			} else if (tempParentId > data[i].parentCommonObjectAttributeId) {
				rgroup = rgroup.slice(0, rgroup.lastIndexOf("."));
			}
			
			var actionTemplateId = table+"-column-template";
			var newRow = $.qp.ar.addRow({link: obj,tableId:table,templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});
			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			
			$(newRow).find("input[name$='groupId']").val(rgroup);
			$(newRow).find("input[name$='tableIndex']").text(tableIndex);
			$(newRow).find("input[name$='objectDefinitionName']").val(data[i].commonObjectAttributeName);
			$(newRow).find("label[name$='objectDefinitionName']").text(data[i].commonObjectAttributeName);
			$(newRow).find("input[name$='objectDefinitionCode']").val(data[i].commonObjectAttributeCode);
			$(newRow).find("label[name$='objectDefinitionCode']").text(data[i].commonObjectAttributeCode);
			$(newRow).find("input[name$='commonObjectDefinitionId']").val(data[i].commonObjectDefinitionId);
			$(newRow).find("input[name$='dataType']").val(data[i].dataType);
			$(newRow).find("input[name$='objectType']").val(2);
			$(newRow).find("input[name$='objectId']").val(data[i].commonObjectAttributeId);
			var dataType = CL_BD_DATATYPE[data[i].dataType];
			if(eval(data[i].arrayFlg)){
				dataType += "[]";
			}
			$(newRow).find("label[name$='dataType']").text(dataType);
			$(newRow).find("input[name$='arrayFlg']").val(data[i].arrayFlg);
			$(newRow).find("input[name$='objectFlg']").val(false);
			tempAnchor = $(newRow);
			
			tempParentId = data[i].parentCommonObjectAttributeId;
		}
	}
}
function bdOnChangeExternalObjectOfOb(event){
	var obj = event.target;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	$(obj).closest("tr").nextAll("tr[data-ar-rgroup^='"+rgroup+"']").each(function() {
		$(this).remove();
	});
	$(obj).closest("td").find("input[name$='moduleId']").val("");
	if(event.item != undefined){
		$(obj).closest("tr").find("input[name$='objectDefinitionName']").val(event.item.optionLabel);
		$(obj).closest("tr").find("input[name$='objectDefinitionCode']").val(event.item.output01);
		$(obj).closest("tr").find("input[name$='objectType']").val(1);
		$(obj).closest("tr").find("input[name$='objectId']").val(event.item.optionValue);
		$(obj).closest("tr").find("input[name$='objectFlg']").val(true);
		var table = $(obj).closest("table").attr("id");
		var actionTemplateId = table+"-external-object-columm-template";
		
		if(event.item.optionValue != ""){
			var level = 1;
			if(rgroup == undefined || rgroup == ""){
				level = 1;
			}else{
				level = (rgroup.match(/\./g) || []).length + 1;
			}
			var url = CONTEXT_PATH + "/externalobjectdefinition/getDetailsExternalObjectDefinition?externalObjectDefinitionId="+event.item.optionValue+"&level="+level+"&r="+Math.random();
			var data = $.qp.getData(url);
			if(data.length>0){
				data = data.sort(function (a, b){
					return a.commonObjectAttributeId - b.commonObjectAttributeId;
				});

				tempAnchor = obj;
				buildODExternalObjectAttributes(data, obj);
			}
		}
		$(obj).closest("td").find("input[name$='moduleId']").val(event.item.output02);
	}else{
		$(obj).closest("tr").find("input[name$='objectDefinitionName']").val("");
		$(obj).closest("tr").find("input[name$='objectDefinitionCode']").val("");
		$(obj).closest("tr").find("input[name$='objectType']").val(-1);
		$(obj).closest("tr").find("input[name$='objectId']").val("");
	}
}
function buildODExternalObjectAttributes(data, obj) {
	var tempParentId = null;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	var table = $(obj).closest("table").attr("id");
	
    for(var i=0;i<data.length;i++){
		if (data[i].dataType == 17) {
	    	var actionTemplateId = table+"-column-template";
			var newRow = $.qp.ar.addRow({link: obj,tableId:table,templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});

			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			var curObj = data[i].externalObjectDefinition;

			$(newRow).find("input[name$='groupId']").val(rgroup);
			$(newRow).find("input[name$='tableIndex']").text(tableIndex);
			$(newRow).find("input[name$='objectDefinitionName']").val(data[i].externalObjectAttributeName);
			$(newRow).find("label[name$='objectDefinitionName']").text(data[i].externalObjectAttributeName);
			$(newRow).find("input[name$='objectDefinitionCode']").val(data[i].externalObjectAttributeCode);
			$(newRow).find("label[name$='objectDefinitionCode']").text(data[i].externalObjectAttributeCode);
			$(newRow).find("input[name$='commonObjectDefinitionId']").val(curObj.externalObjectDefinitionId);
			$(newRow).find("input[name$='dataType']").val(data[i].dataType);
			$(newRow).find("input[name$='objectType']").val(3);
			$(newRow).find("input[name$='objectId']").val(data[i].externalObjectAttributeId);
			var dataType = CL_BD_DATATYPE[data[i].dataType];
			if(eval(data[i].arrayFlg)){
				dataType += "[]";
			}
			$(newRow).find("label[name$='dataType']").text(dataType);
			$(newRow).find("input[name$='arrayFlg']").val(data[i].arrayFlg);
			$(newRow).find("label[name$='objectFlg']").text(false);
			tempAnchor = $(newRow);
			
			buildODExternalObjectAttributes(data[i].externalObjectDefinition.externalObjectAttributes, newRow);
		} else {
			if (tempParentId < data[i].parentExternalObjectAttributeId) {
				rgroup = $(newRow).closest("tr").attr("data-ar-rgroupindex");
			} else if (tempParentId > data[i].parentExternalObjectAttributeId) {
				rgroup = rgroup.slice(0, rgroup.lastIndexOf("."));
			}
			
			var actionTemplateId = table+"-column-template";
			var newRow = $.qp.ar.addRow({link: obj,tableId:table,templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});
			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			
			$(newRow).find("input[name$='groupId']").val(rgroup);
			$(newRow).find("input[name$='tableIndex']").text(tableIndex);
			$(newRow).find("input[name$='objectDefinitionName']").val(data[i].externalObjectAttributeName);
			$(newRow).find("label[name$='objectDefinitionName']").text(data[i].externalObjectAttributeName);
			$(newRow).find("input[name$='objectDefinitionCode']").val(data[i].externalObjectAttributeCode);
			$(newRow).find("label[name$='objectDefinitionCode']").text(data[i].externalObjectAttributeCode);
			$(newRow).find("input[name$='commonObjectDefinitionId']").val(data[i].externalObjectAttributeId);
			$(newRow).find("input[name$='dataType']").val(data[i].dataType);
			$(newRow).find("input[name$='objectType']").val(3);
			$(newRow).find("input[name$='objectId']").val(data[i].externalObjectAttributeId);
			var dataType = CL_BD_DATATYPE[data[i].dataType];
			if(eval(data[i].arrayFlg)){
				dataType += "[]";
			}
			$(newRow).find("label[name$='dataType']").text(dataType);
			$(newRow).find("input[name$='arrayFlg']").val(data[i].arrayFlg);
			$(newRow).find("label[name$='objectFlg']").text(false);
			tempAnchor = $(newRow);
			tempParentId = data[i].parentExternalObjectAttributeId;
		}
	}
}
function getDataSourceOfIndexBD(event){
	var searchKey = "";
	if(event != undefined && event != null){
		searchKey = event.searchKey; 
	}
	var dataResult = [];
	$("#designArea").find("div.execution-class[componenttype='10']").each(function(i){
		var value = $(this).find("input[name='componentElement']").val();
		var data = convertToJson(value);
		if(data.loopType == "0" && data.index != undefined && data.index != null){
			var objTmp = new Object();
			objTmp.optionValue = 4+$(this).attr("id");
			objTmp.optionLabel = data.index;
			objTmp.output01 = 4;
			var index = objTmp.optionLabel.indexOf(searchKey);
			if(index >-1){
				dataResult.push(objTmp);
			}
		}
	});
	var defaulResult = getDataSourceAutocompleteBD(event);
	dataResult = $.merge(dataResult, defaulResult);
	return dataResult;
}

function setTypeOfAssignIndexBD (event){
	var obj = event.target;
	$(obj).closest("td").removeClass("qp-bdesign-tr-remove");
	if(event.item != undefined){
		var indexType = event.item.output01;
		$(obj).next().attr("indextype",indexType);
	}
	else{
		$(obj).next().attr("indextype","");
	}
}

function getLabelOfIndexLoop(paramId){
	var result = [];
	$("#designArea").find("div.execution-class[componenttype='10']").each(function(i){
		var value = $(this).find("input[name='componentElement']").val();
		var data = convertToJson(value);
		if(data.loopType == "0" && data.index != undefined && data.index != null){
			result[$(this).attr("id")] = data.index;
		}
	});
	return result;
}
function onChangeModuleOfForm(obj){
	onchangeModule(obj)
	if(blogicType == 2)
		setURLOfWS(obj);
	//filter data
	var scope = $(obj.target).closest("td");
    var value = $(scope).find("input:hidden[name='moduleId']").val();

    $('table[id="tbl_inputbean_list_define"]').find('>tbody>tr').each(function(){
    	//clear by module.
    	removeRowByModule(this,".objectId",value);
    });
    $('table[id="tbl_outputbean_list_define"]').find('>tbody>tr').each(function(){
    	//clear by module.
    	removeRowByModule(this,".objectId",value);
    });
    $('table[id="tbl_objectdefinition_list_define"]').find('>tbody>tr').each(function(){
    	//clear by module.
    	removeRowByModule(this,".objectId",value);
    });
}

function removeRowByModule(row,inputName,moduleId){
	var moduleIdOfRow = $(row).find("input[name$='moduleId']").val();
	moduleIdOfRow = (moduleIdOfRow != undefined ? moduleIdOfRow : "");
	moduleId = (moduleId != undefined ? moduleId : "");
	$(row).find("input:text[name$='"+inputName+"Autocomplete']").attr("arg03", moduleId);
	if(moduleIdOfRow != "" && moduleId != moduleIdOfRow){
		$.qp.removeAutocompleteData($(row).find("input:hidden[name$='"+inputName+"']").nextAll('.dropdown-toggle:first'));
	}
}

function setURLOfWS(obj) {
	
	var businessLogicCode = $("form[id='businessDesignForm']").find("input[name='businessLogicCode']").val();
//	var moduleCode = $("form[id='businessDesignForm']").find("input[name$='moduleCode']").val();

	if (obj != undefined ) {
		if(obj.item != undefined){
			moduleCode = obj.item.output01;
		}
		else{
			moduleCode = "";
		}
	}

	$("form[id='businessDesignForm']").find("input[name$='moduleCode']").val(moduleCode);
	
	var url = "";
	if(!isEmptyQP(businessLogicCode) && !isEmptyQP(moduleCode)){
		if ($("#authenticatedFlg1").is(":checked")) {
			url = "/" + webservicePattern + "/authen/"+moduleCode+"/" + businessLogicCode;
		} else {
			url = "/" + webservicePattern + "/notauthen/"+moduleCode+"/" + businessLogicCode;
		}
	}
	$("form[id='businessDesignForm']").find("label[name='url']").text(url.toLowerCase());
}

/* Adding by HungHX follow ticket 577, 583, 587 */
/*
 * When click module id then affect change dislay list common object definition & external object definition.
 * 
 */
function onchangeModuleAffectObjExt(obj){
	var scope = $(obj.target).closest("tr");
    var value = $(scope).find("input:hidden[name='moduleId']").val();

    $('table[id="tbl_outputbean_list_define"]').find('>tbody>tr').each(function(){
    	changeArgOfRowObjByType(this, "16", value);
    	changeArgOfRowObjByType(this, "17", value);
    });
    
    $('table[id="tbl_objectdefinition_list_define"]').find('>tbody>tr').each(function(){
    	changeArgOfRowObjByType(this, "16", value);
    	changeArgOfRowObjByType(this, "17", value);
    });
}

function changeArgOfRowObjByType(newRow, type, argValue) {
	switch (type) {
	case "16":
		if($(newRow) != undefined && $(newRow).find("input:text[name$='.commonObjectDefinitionIdAutocomplete']").val() != undefined && type == "16"){
			$(newRow).find("input:text[name$='.commonObjectDefinitionIdAutocomplete']").val('');
			$(newRow).find("input:hidden[name$='.commonObjectDefinitionId']").val('');
			$(newRow).find("input:text[name$='.commonObjectDefinitionIdAutocomplete']").attr("arg03", argValue);
			$.qp.removeAutocompleteData($($(newRow).find("input:hidden[name$='.commonObjectDefinitionId']")).nextAll('.dropdown-toggle:first'));
		}
		break;
		
	case "17":
		if($(newRow) != undefined && $(newRow).find("input:text[name$='.externalObjectDefinitionIdAutocomplete']").val() != undefined && type == "17"){
			$(newRow).find("input:text[name$='.externalObjectDefinitionIdAutocomplete']").val('');
			$(newRow).find("input:hidden[name$='.externalObjectDefinitionId']").val('');
			$(newRow).find("input:text[name$='.externalObjectDefinitionIdAutocomplete']").attr("arg03", argValue);
			$.qp.removeAutocompleteData($($(newRow).find("input:hidden[name$='.externalObjectDefinitionId']")).nextAll('.dropdown-toggle:first'));
		}
		break;

	default:
		break;
	}
}
function onchangeFunctionDesign(event){
	var functionDesignId = "";
	if(event.item != undefined){
		functionDesignId = event.item.optionValue;
	}
}
function clearFunctionDesignAutocomplete(moduleIdOfBD){
	// change arg02 of function design autocomplete
	var nextInput = $("#tbl-blogicinformation").find("input[name$='functionDesignIdAutocomplete']");
	if(nextInput.length >0){
		var nextHidden = nextInput.next("input[type=hidden]");
		nextInput.val("");
		nextInput.attr("arg02",moduleIdOfBD);
		nextHidden.val("");
		nextInput.data("ui-autocomplete")._trigger("close");
	}
}
function onchangeScreenDesign(event){
	screenId = "";
	var requestMethodItem = $("#tbl-blogicinformation").find("input[type=radio][name='requestMethod']:checked, input[type=hidden][name='requestMethod']").val();
	if(event.item != undefined){
		screenId = event.item.optionValue;
	}
	clearScreenFormAutocomplete(screenId);
	if(screenId != "" && requestMethodItem == "0"){
		//load screen param
		loadInputBeanFromScreenId(screenId,requestMethodItem,"");
		if(pageType != "1"){
			loadOutputBeanFromScreenId(screenId);
		}
	}
	
}
function clearScreenDesignAutocomplete(moduleId){
	clearLoadInputBeanFromScreenId();
	if(pageType != "1"){
		clearLoadOutputBeanFromScreenId();
	}
	// change arg03 of screen id autocomplete
	var nextInput = $("#tbl-blogicinformation").find("input[name$='screenIdAutocomplete']");
	if(nextInput.length >0){
		var nextHidden = nextInput.next("input[type=hidden]");
		nextInput.val("");
		nextInput.attr("arg03",moduleId);
		nextHidden.val("");
		nextInput.data("ui-autocomplete")._trigger("close");
	}
	clearScreenFormAutocomplete("");
	
}
function clearScreenFormAutocomplete(screenId){
	clearLoadInputBeanFromScreenId();
	if(pageType != "1"){
		clearLoadOutputBeanFromScreenId();
	}
	// change arg02 of screen form autocomplete
	var nextInput = $("#tbl-blogicinformation").find("input[name$='screenFormIdAutocomplete']");
	if(nextInput.length >0){
		var nextHidden = nextInput.next("input[type=hidden]");
		nextInput.val("");
		nextInput.attr("arg02",screenId);
		nextHidden.val("");
		nextInput.data("ui-autocomplete")._trigger("close");
	}
}
function onchangeRequestMethod(thiz){
	if($(thiz).length == 0){
		$("#tbl-blogicinformation").find("th[requestMethod='4'],td[requestMethod='4']").hide();
	}else{
		requestMethod = $(thiz).val();
		clearScreenDesignAutocomplete(moduleIdOfBD);

		if($(thiz).val() == "4"){
			$(thiz).closest("#tbl-blogicinformation").find("th[requestMethod='4'],td[requestMethod='4']").show();
			$(thiz).closest("#tbl-blogicinformation").find("label[name='lblScreenName']").text($.qp.getModuleMessage("sc.businesslogicdesign.0257"));
		}else{
			$(thiz).closest("#tbl-blogicinformation").find("th[requestMethod='4'],td[requestMethod='4']").hide();
			$(thiz).closest("#tbl-blogicinformation").find("label[name='lblScreenName']").text($.qp.getModuleMessage("sc.businesslogicdesign.0256"));
		}
		
		if(blogicType == "0" && moduleType == "0" && returnType == "1" && requestMethod == "4"){
			$("#tbl_inputbean_list_define").find(".bd-in-screenitem-hidden").show();
		}else{
			$("#tbl_inputbean_list_define").find(".bd-in-screenitem-hidden").hide();
		}
		
		if((blogicType == "0" && moduleType == "0" && (returnType == "0" || (returnType == "1" && requestMethod == "0")) )|| patternType == "1"){
			$("#tbl_outputbean_list_define").find(".bd-ou-screenitem-hidden").show();
		}else{
			$("#tbl_outputbean_list_define").find(".bd-ou-screenitem-hidden").hide();
		}
	}
	
	//style row
	$("a[href='#tabBusiness-outputbean']").tab("show");
	changeWithDivDisplayObjectName($("#tbl_outputbean_list_define tr"));
	$("a[href='#tabBusiness-inputbean']").tab("show");
	changeWithDivDisplayObjectName($("#tbl_inputbean_list_define tr"));
}
function changeWithDivDisplayObjectName(trs){
	var widthThSecond = $(trs).closest("table").find("thead th:nth-child(2)").outerWidth();
	var widthThFirst = $(trs).closest("table").find("thead th:nth-child(1)").outerWidth();
	var tds = $(trs).find("td:first");
	$(tds).each(function(){
		var numberOfdiv = $(this).find("div:first>div").not("div.pull-right").length;
		var withReduce = 0;
		if(numberOfdiv > 1){
			withReduce = (numberOfdiv - 1) * widthThFirst;
		}
		$(this).find("div.pull-right").outerWidth(widthThSecond - 2 - withReduce);
	});
}

function onchangeReturnType(thiz){
	if($(thiz).val() == "2"){
		$(thiz).closest("#tbl-blogicinformation").find("input[type='radio'][name='requestMethod'][value='0']").prop("checked",true);
		onchangeRequestMethod($(thiz).closest("#tbl-blogicinformation").find("input[type='radio'][name='requestMethod'][value='0']"));
		$(thiz).closest("#tbl-blogicinformation").find("input[type='radio'][name='requestMethod'][value='4']").prop("disabled",true);
	}else{
		var checkedRadio = $(thiz).closest("#tbl-blogicinformation").find("input[type='radio'][name='requestMethod']:checked");
		if(checkedRadio.length == 0 ){
			(thiz).closest("#tbl-blogicinformation").find("input[type='radio'][name='requestMethod'][value='0']").prop("checked",true);
			onchangeRequestMethod($(thiz).closest("#tbl-blogicinformation").find("input[type='radio'][name='requestMethod'][value='0']"));
		}
		$(thiz).closest("#tbl-blogicinformation").find("input[type='radio'][name='requestMethod'][value='4']").prop("disabled",false);
	}
}
function onchangeScreenForm(event){
	var screenFormId = "";
	clearLoadInputBeanFromScreenId();
	//clear inputbean of form
	if(event.item != undefined){
		screenFormId = event.item.optionValue;
		var requestMethodItem = 4;
		var screenId = $("form#businessDesignForm").find("input[name='screenId']").val();
		loadInputBeanFromScreenId(screenId,requestMethodItem,screenFormId);
	}
	
}
function loadInputBeanFromScreenId(screenId,requestMethodIn,screenFormId){
	//get active tab:
	var currentTab = $('#com-menu-sidebar').find('li.active').attr('id');
	var isRollbackTab = false;
	if(currentTab != 'tabInput'){
		isRollbackTab = true;
		$('#com-menu-sidebar').find('a#tabInputBean').tab('show');
	}

	var projectId = CURRENT_PROJECT_ID;
	var languageId = CURRENT_LANGUAGE_DESIGN_ID;
	var url = CONTEXT_PATH + "/businessdesign/getInputBeanFromScreenId?screenId="+screenId+"&requestMethod="+requestMethodIn+"&screenFormId="+screenFormId+"&projectId="+projectId+"&languageId="+languageId+"&r="+Math.random();
	var data = $.qp.getData(url);
	var table = $("#businessDesignForm").find("table[id='tbl_inputbean_list_define']");
	var tableId = $(table).attr("id");
	var anchor = $("#businessDesignForm").find("table[id='tbl_inputbean_list_define']").next().find("a");
	var rowTemplateId = tableId+"-column-template";
	var actionTemplateId = tableId+"-action-template";
	var lastRow = $("#businessDesignForm").find("table[id='tbl_inputbean_list_define']").find("tr").last();
	var mapRGroupIndex = [];
	var startRow,endRow;
	var lstCurrentInputBean = convertToJson($("#businessDesignForm").find("input[name='jsonInputBean']").val());
	for(var i =0 ;i <data.length;i++){
		var inputbean = data[i];
		var groupId= "";
		var newRow = null;
		if(!isEmptyQP(inputbean.parentInputBeanId)){
			if(mapRGroupIndex[inputbean.parentInputBeanId] != undefined){
				groupId = mapRGroupIndex[inputbean.parentInputBeanId];
			}
		}
		var newRow = $.qp.ar.addRow({link:anchor,tableId:tableId,templateId:rowTemplateId,templateData:{groupId:groupId}});
		var rGroupIndex = $(newRow).attr("data-ar-rgroupindex");
		mapRGroupIndex[inputbean.inputBeanId] = rGroupIndex;
		//re-mapping :
		if(lstCurrentInputBean.length >0){
			for(var j=0;j < lstCurrentInputBean.length; j++){
				if(lstCurrentInputBean[j].inputBeanCode == inputbean.inputBeanCode && lstCurrentInputBean[j].dataType == inputbean.dataType && lstCurrentInputBean[j].arrayFlg == inputbean.arrayFlg){
					$(newRow).find("input[name*='inputBeanId']").val(lstCurrentInputBean[j].inputBeanId);
				}
			}
		}
		//
		$(newRow).find("input[name*='inputBeanName']").val(inputbean.inputBeanName);
		$(newRow).find("input[name*='messageStringAutocomplete']").val(inputbean.messageStringAutocomplete);
		$(newRow).find("label[name*='inputBeanName']").text(inputbean.messageStringAutocomplete);
		$(newRow).find("input[name*='inputBeanCode']").val(inputbean.inputBeanCode);
		$(newRow).find("label[name*='inputBeanCode']").text(inputbean.inputBeanCode);
		$(newRow).find("input[name*='dataType']").val(inputbean.dataType);
		$(newRow).find("input[name*='arrayFlg']").val(inputbean.arrayFlg);
		var dataType = CL_BD_DATATYPE[inputbean.dataType];
		if(inputbean.arrayFlg)
			dataType += "[]";
		$(newRow).find("label[name*='dataType']").text(dataType);
		$(newRow).find("input[name*='groupBaseTypeId']").val(inputbean.baseType);
		
		$(newRow).find("input[name*='createdMessageFlg']").val(false);
		$(newRow).find("input[name*='inputBeanType']").val(inputbean.inputBeanType);
		$(newRow).find("input[name*='screenItemId']").val(inputbean.screenItemId);
		$(newRow).find("input[name*='tblDesignId']").val(inputbean.tblDesignId);
		$(newRow).find("input[name*='columnId']").val(inputbean.columnId);
		$(newRow).find("input[name*='objectFlg']").val(inputbean.objectFlg);
		$(newRow).find("input[name*='mappingScreenItemFlag']").val(true);
		$(newRow).find("input[name*='inputBeanType']").val(0);
		$(newRow).find("input[name*='objectType']").val(inputbean.objectType);
		$(newRow).find("input[name*='objectId']").val(inputbean.objectId);
		$(newRow).find("input[name*='objectFlg']").val(inputbean.objectFlg);
		if(requestMethodIn == "4" && inputbean.screenItemMapping != null ){
			//show screen item name
			$(newRow).find("label[name*='screenItemIdAutocomplete']").text(inputbean.screenItemMapping.itemName);
			$(newRow).find("input[name*='screenItemMapping.screenItemId']").val(inputbean.screenItemId);
			$(newRow).find("input[name*='screenItemMapping.mappingType']").val(inputbean.screenItemMapping.mappingType);
			$(newRow).find("input[name*='screenItemMapping.itemName']").val(inputbean.screenItemMapping.itemName);
			$(newRow).find("label[name*='screenItemIdAutocomplete']").closest("td").find("span.bd-in-screenitem-hidden").show();
		}else{
			$(newRow).find("label[name*='screenItemIdAutocomplete']").closest("td").find("span.bd-in-screenitem-hidden").hide();
		}
		$(newRow).find("td:last").children().show();
		
		//get start/end row to init add button
		if(i==0){
			startRow = $(newRow);
		}
		if(i==data.length-1){
			endRow = $(newRow);
		}
		if(!inputbean.objectFlg){
			$(newRow).find("a[name='btnOpenValidationCheck']").show();
		}else{
			$(newRow).find("a[name='btnOpenValidationCheck']").hide();
		}
	}
	
	//init add button
	$.qp.businessdesign.initAddButtonRowForRangeRow("tbl_inputbean_list_define","tbl_inputbean_list_define-action-template",false,startRow,endRow);
	//rollback active tab
	if(isRollbackTab){
		$('#com-menu-sidebar').find('li#'+currentTab).children('a').tab('show');
	}
}
function clearLoadInputBeanFromScreenId(){
	$("#tbl_inputbean_list_define tbody").find("tr").each(function(i){
		if($(this).find("input[name*='inputBeanType']").val() == "0"){
			$(this).children("td:last").children("a").click();
		};
	});
}
function clearLoadOutputBeanFromScreenId(){
	$("#tbl_outputbean_list_define tbody").find("tr").each(function(i){
		if($(this).find("input[name*='outputBeanType']").val() == "0"){
			$(this).children("td:last").children("a").click();
		};
	});
}

/* Enhancement Blogic*/
function advanceSettingReturnType(thiz) {
	$(thiz).closest("td").prev().show();
	$(thiz).closest("td").hide();
	if ($(thiz).attr('id') == 'advanceRequestMethodSetting') {
		$("input[name='advanceRequestMethodFlag']").val('true');
	} else if ($(thiz).attr('id') == 'advanceReturnTypeSetting') {
		$("input[name='advanceReturnTypeFlag']").val('true');
	}
}

/* Load output bean from screen item of screenId*/
function loadOutputBeanFromScreenId(screenId){
	//get active tab:
	var currentTab = $('#com-menu-sidebar').find('li.active').attr('id');
	var isRollbackTab = false;
	if(currentTab != 'tabOutput'){
		isRollbackTab = true;
		$('#com-menu-sidebar').find('a#tabOutputBean').tab('show');
	}
	
	var projectId = CURRENT_PROJECT_ID;
	var languageId = CURRENT_LANGUAGE_DESIGN_ID;
	var url = CONTEXT_PATH + "/businessdesign/getOutputBeanFromScreenId?screenId="+screenId+"&projectId="+projectId+"&languageId="+languageId+"&r="+Math.random();
	var data = $.qp.getData(url);
	var table = $("#businessDesignForm").find("table[id='tbl_outputbean_list_define']");
	var tableId = $(table).attr("id");
	var anchor = $("#businessDesignForm").find("table[id='tbl_outputbean_list_define']").next().find("a");
	var rowTemplateId = tableId+"-template";
	var rowExternalObjectTemplateId = tableId+"-external-object-template";
	var rowExternalAttributeTemplateId = tableId+"-column-template";
	var actionTemplateId = tableId+"-action-template";
	var lastRow = $("#businessDesignForm").find("table[id='tbl_outputbean_list_define']").find("tbody tr").last();
	var mapRGroupIndex = [];
	var lastIndex = $(lastRow).length > 0 ? $(lastRow).attr("data-ar-rindex") : 0;
	var startRow,endRow;
	for(var i =0 ;i <data.length;i++){
		var outputBean = data[i];
		var groupId= "";
		var newRow = null;
		if(!isEmptyQP(outputBean.parentOutputBeanId)){
			if(mapRGroupIndex[outputBean.parentOutputBeanId] != undefined){
				groupId = mapRGroupIndex[outputBean.parentOutputBeanId];
			}
		}
		if(outputBean.dataType.toString() == "17"){
			newRow= $.qp.ar.addRow({link:anchor,tableId:tableId,templateId:rowExternalObjectTemplateId,templateData:{groupId:groupId}});
			$(newRow).find("input[name*='outputBeanName']").val(outputBean.outputBeanName);
			$(newRow).find("input[name*='externalObjectDefinitionId']").val(outputBean.objectId);
			$(newRow).find("input[name*='externalObjectDefinitionIdAutocomplete']").val(outputBean.outputBeanName);
			$(newRow).find("input[name*='outputBeanCode']").val(outputBean.outputBeanCode);
			$(newRow).find("select[name*='dataType']").find("option[value="+outputBean.dataType+"]").prop('selected', true)
			if(outputBean.arrayFlg)
				$(newRow).find("input[name*='arrayFlg']").prop("checked",true);
		}else{
			if(outputBean.objectType == "2" || outputBean.objectType == "3" ){
				newRow= $.qp.ar.addRow({link:anchor,tableId:tableId,templateId:rowExternalAttributeTemplateId,templateData:{groupId:groupId}});
				$(newRow).find("input[name*='outputBeanName']").val(outputBean.outputBeanName);
				$(newRow).find("label[name*='outputBeanName']").text(outputBean.outputBeanName);
				$(newRow).find("input[name*='outputBeanCode']").val(outputBean.outputBeanCode);
				$(newRow).find("label[name*='outputBeanCode']").text(outputBean.outputBeanCode);
				$(newRow).find("input[name*='dataType']").val(outputBean.dataType);
				$(newRow).find("input[name*='arrayFlg']").val(outputBean.arrayFlg);
				var dataType = CL_BD_DATATYPE[outputBean.dataType];
				if(outputBean.arrayFlg)
					dataType += "[]";
				$(newRow).find("label[name*='dataType']").text(dataType);
			}else{
				newRow = $.qp.ar.addRow({link:anchor,tableId:tableId,templateId:rowTemplateId,templateData:{groupId:groupId}});
				$(newRow).find("input[name*='outputBeanName']").val(outputBean.outputBeanName);
				$(newRow).find("input[name*='outputBeanCode']").val(outputBean.outputBeanCode);
				$(newRow).find("select[name*='dataType']").find("option[value="+outputBean.dataType+"]").prop('selected', true)
				if(outputBean.arrayFlg)
					$(newRow).find("input[name*='arrayFlg']").prop("checked",true);
			}
			
		}
		var rGroupIndex = $(newRow).attr("data-ar-rgroupindex");
		mapRGroupIndex[outputBean.outputBeanId] = rGroupIndex;

		$(newRow).find("input[name*='objectFlg']").val(outputBean.objectFlg);
		$(newRow).find("input[name*='objectType']").val(outputBean.objectType);
		$(newRow).find("input[name*='objectId']").val(outputBean.objectId);
		$(newRow).find("input[name*='objectFlg']").val(outputBean.objectFlg);
		$(newRow).find("input[name*='outputBeanType']").val(0);
		
		var obj = $(newRow).find("span.bd-ou-screenitem-hidden");
		var screenItemName = "";
		if(outputBean.lstScreenItemMapping.length > 0){
			// setting hidden item on 
			$(obj).closest("td").find("input[name*=screenItemId]").remove();
			$(obj).closest("td").find("input[name*=mappingType]").remove();
			$(obj).closest("td").find("input[name*=itemName]").remove();
		}
		for(var j = 0; j < outputBean.lstScreenItemMapping.length; j++){
			var screenItemOutput = outputBean.lstScreenItemMapping[j];
			var $screenItemClone = $('<input>').attr('type','hidden');
			var $mappingTypeClone = $('<input>').attr('type','hidden');
			var $itemNameClone = $('<input>').attr('type','hidden');
			var mappingType = screenItemOutput.mappingType;
			
			var itemName = screenItemOutput.itemName;
			var index = lastIndex + j + 1;
			$screenItemClone.attr('name', 'lstOutputBean['+index+'].lstScreenItemMapping['+j+'].screenItemId').val(screenItemOutput.screenItemId);
			$screenItemClone.attr('mappingType', mappingType);
			$mappingTypeClone.attr('name', 'lstOutputBean['+index+'].lstScreenItemMapping['+j+'].mappingType').val(mappingType);
			$itemNameClone.attr('name', 'lstOutputBean['+index+'].lstScreenItemMapping['+j+'].itemName').val(itemName);
			
			if(j == outputBean.lstScreenItemMapping.length-1){
				screenItemName += itemName;
				$(obj).closest("td").find("label[name*=screenItemIdAutocomplete]").text(screenItemName);
			}else{
				screenItemName += itemName + ";";
			}
			$(obj).closest("td").append($screenItemClone);
			$(obj).closest("td").append($mappingTypeClone);
			$(obj).closest("td").append($itemNameClone);
		}
		
		//get start/end row to init add button
		if(i==0){
			startRow = $(newRow);
		}
		if(i==data.length-1){
			endRow = $(newRow);
		}
		
	}
	//init add button
	$.qp.businessdesign.initAddButtonRowForRangeRow("tbl_outputbean_list_define","tbl_outputbean_list_define-action-template",false,startRow,endRow);
	//rollback active tab
	if(isRollbackTab){
		$('#com-menu-sidebar').find('li#'+currentTab).children('a').tab('show');
	}
}

function buildMethodNameOfAdvanceNode(businessDesignCode,methodName){
	if(isEmptyQP(businessDesignCode)){
		return deCapitalizeFirstLetter(methodName);
	}else{
		return deCapitalizeFirstLetter(methodName) + capitalizeFirstLetter(businessDesignCode);
	}
}
function getClassCssOfValidationCheck(type,dataType){
	var classOfDataType = "";
	
	switch (type) {
	case 3:
	case 4:
	case 64:
	case 65:
	case 66:
	case 67:	
		switch (dataType.toString()) {
		case "2":
			classOfDataType = "qp-input-smallint";
			break;
		case "3":
			classOfDataType = "qp-input-integer";
			break;
		case "4":	
			classOfDataType = "qp-input-bigint";
			break;
		case "5":
			classOfDataType = "qp-input-float";
			break;
		case "6":
			//temp
			classOfDataType = "qp-input-text";
			break;
		default:
			break;
		}
		break;
	case 8:	
		classOfDataType = "qp-input-integer";
		break;
	case 5:
	case 6:
		classOfDataType = "qp-input-text";
		break;
	case 51:
	case 52:
	case 53:
	case 68:
	case 69:	
		classOfDataType = "qp-input-datepicker";
		break;
	case 54:
	case 55:
	case 56:
	case 72:
	case 73:
		classOfDataType = "qp-input-timepicker";
		break;
	case 57:
	case 58:
	case 59:
	case 70:
	case 71:	
	case 74:
	case 75:		
		classOfDataType = "qp-input-datetimepicker";
		break;
	case 60:
	case 61:
	case 62:
		classOfDataType = "qp-input-datetimepicker";
		break;
	case 2:
		classOfDataType = "qp-input-text";
		break;
	default:
		break;
	}
	return classOfDataType;
}
function getlevelOfMessage(value){
	
	var levelName = "";
	if(value == undefined || value == null)
		return levelName;
	switch (value.toString()) {
	case "0":
		levelName = $.qp.getModuleMessage('sc.blogiccomponent.0131');
		break;
	case "1":
		levelName = $.qp.getModuleMessage('sc.blogiccomponent.0132');
		break;
	case "2":
		levelName = $.qp.getModuleMessage('sc.blogiccomponent.0133');
		break;
	case "5":
		levelName = $.qp.getModuleMessage('sc.blogiccomponent.0197');
		break;
	default:
		levelName = "";
		break;
	}
	return levelName;
}