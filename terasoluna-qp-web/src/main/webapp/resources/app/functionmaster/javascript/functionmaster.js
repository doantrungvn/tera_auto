$(document).ready(function() {
	initMapKey();
	
});

var mapKeyTemp = [];
function initMapKey() {
	var table = "#tblLstMethod";
	mapKeyTemp["inputObject"] = 0;
	mapKeyTemp["outputObject"] = 0;
	
	$(table).find(">tbody>tr.ar-dataRow").each(function(i) {
		var tableInputBean = "#tblInput";
		$(tableInputBean).find("input[name*='methodInputId']").each(function() {
			var id = $(this).val();
			if (id.search("in") >= 0) {
				id = id.slice(id.lastIndexOf("in"));
				id = id.replace("in","");
				id = parseInt(id);
				if (mapKeyTemp["inputObject"] < id) {
					mapKeyTemp["inputObject"] = id;
				}
			}
		});
		
		var tableOutputBean = "#tblOutput";
		$(tableOutputBean).find("input[name*='methodOutputId']").each(function() {
			var id = $(this).val();
			if (id.search("ou") >= 0) {
				id = id.slice(id.lastIndexOf("ou"));
				id = id.replace("ou","");
				id = parseInt(id);
				if (mapKeyTemp["outputObject"] < id) {
					mapKeyTemp["outputObject"] = id;
				}
			}
		});
	});
}

function validateRequired(table, inputName, inputLabel) {
	var isList = true;
	var $Inputs = $("table[name*='"+table+"']").find("input[name*=" + inputName + "]");
	var messages = "";
	$Inputs.each(function(index) {
		if ($.trim($(this).val()) == '') {
			if (!isList) {
				messages += $.qp.getMessage('err.sys.0025').replace("{0}",
						inputLabel);
			} else {
				messages += "\r\n";
				messages += $.qp.getMessage('err.sys.0077').replace("{0}",
						inputLabel).replace("{1}", index + 1);
			}
		}
	});
	return messages;
};

function validationForm() {
	var message = "";
//	message += validationInputBean("tblInput");
//	message += validationOutputBean("tblOutput");
	return message;
}

function validationInputBean(table) {
	var messages = "";
	messages += validateRequired(table, "methodInputName", $.qp.getModuleMessage("sc.functionmaster.0072"));
	messages += validateRequired(table, "methodInputCode", $.qp.getModuleMessage("sc.functionmaster.0073"));
	return messages;
}

function validationOutputBean(table) {
	var messages = "";
	messages += validateRequired(table, "methodOutputName", $.qp.getModuleMessage("sc.functionmaster.0074"));
	messages += validateRequired(table, "methodOutputCode", $.qp.getModuleMessage("sc.functionmaster.0075"));
	return messages;
}

function removeEvent(obj){
	if($.qp.confirm(fcomMsgSource['inf.sys.0014'])){
		var currentRow = $(obj).closest('table').closest('tr');
		$.qp.ar.removeRowOfMultiTable({link:currentRow});
	}
}

function addMethodInformation(){
	var actionTemplateId = 'tblMethod-template';
	var newRow = $.qp.ar.addRow({link: this,tableId:'tblLstMethod',templateId:actionTemplateId,templateData:{groupId:''}});
	
	// Setting name attr
	var method = newRow.find("#methodInfor");
	method.css({"border-color":"red","border-style":"dashed","border-width":"2px"});
	method.css({"margin-bottom":"20px"});
	
	reIndexTable();
}

function reIndexTable(){
	$("#wapper").find('input[name=currentMethod]').each(function() {
		var currentMethod = $(this).val() - 1;
		
		var tableFunctionMethod = $(this).closest("table[name=methodInfo]");
		tableFunctionMethod.find("li[name=liInputLogic] a").attr("href", "#tabsDecision-"+currentMethod+"1");
		tableFunctionMethod.find("li[name=liOutputLogic] a").attr("href", "#tabsDecision-"+currentMethod+"2")
		tableFunctionMethod.find("div[name=divInputLogic]").attr("id","tabsDecision-"+currentMethod+"1");
		tableFunctionMethod.find("div[name=divOutputLogic]").attr("id","tabsDecision-"+currentMethod+"2");
		
		tableFunctionMethod.find("table[name=tblInput]").attr("id","tblInput"+currentMethod);
		tableFunctionMethod.find("table[name=tblOutput]").attr("id","tblOutput"+currentMethod);
	});
}

function fmOnChangeCommonObjectOfIn(event){
	var obj = event.target;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	$(obj).closest("tr").nextAll("tr[data-ar-rgroup^='"+rgroup+"']").each(function() {
		$(this).remove();
	});
	if(event.item != undefined){
		$(obj).closest("tr").find("input[name*='methodInputName']").val(event.item.optionLabel);
		$(obj).closest("tr").find("input[name*='methodInputCode']").val(event.item.output01);
		$(obj).closest("tr").find("input[name*='objectType']").val(0);
		$(obj).closest("tr").find("input[name*='objectId']").val(event.item.optionValue);
		var table = $(obj).closest("table").attr("name");
		var level = 1;
		if(rgroup == undefined || rgroup == ""){
			level = 1;
		}else{
			level = (rgroup.match(/\./g) || []).length + 1;
		}
		if(event.item.optionValue != ""){
			var url = CONTEXT_PATH + "/commonobjectdefinition/getDetailsCommonObjectDefinition?commonObjectDefinitionId="+event.item.optionValue+"&level="+level+"&r="+Math.random();
			var data = $.qp.getData(url);
			if(data.length>0){
				tempAnchor = obj;
				buildCommonObjectAttributesIn(data, obj);
			}
		}
	}else{
		$(obj).closest("tr").find("input[name*='methodInputName']").val("");
		$(obj).closest("tr").find("input[name*='methodInputCode']").val("");
		$(obj).closest("tr").find("input[name*='objectType']").val(-1);
		$(obj).closest("tr").find("input[name*='objectId']").val("");
	}
}
function buildCommonObjectAttributesIn(data, obj) {
	var tempParentId = null;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	var table = $(obj).closest("table").attr("name");
	
    for(var i=0;i<data.length;i++){
		if (data[i].dataType == 16) {
	    	var actionTemplateId = table+"-object-attribute-template";
			var newRow = $.qp.ar.addRow({link: obj,container:$(obj).closest('table'),templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});
			
			var curObj = data[i].commonObjectDefinition;
			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			
			$(newRow).find("input[name*='groupId']").val(rgroup);
			$(newRow).find("input[name*='tableIndex']").text(tableIndex);
			$(newRow).find("input[name*='methodInputName']").val(data[i].commonObjectAttributeName);
			$(newRow).find("label[name*='methodInputName']").text(data[i].commonObjectAttributeName);
			$(newRow).find("input[name*='methodInputCode']").val(data[i].commonObjectAttributeCode);
			$(newRow).find("label[name*='methodInputCode']").text(data[i].commonObjectAttributeCode);
			$(newRow).find("input[name*='dataType']").val(data[i].dataType);
			var dataType = CL_QP_DATATYPE[data[i].dataType];
			if(eval(data[i].arrayFlg)){
				dataType += "[]";
			}
			$(newRow).find("label[name*='dataType']").text(dataType);
			$(newRow).find("input[name*='arrayFlg']").val(data[i].arrayFlg);
			$(newRow).find("input[name*='objectType']").val(2);
			$(newRow).find("input[name*='objectId']").val(data[i].commonObjectAttributeId);
			tempAnchor = $(newRow);
			
			buildCommonObjectAttributesIn(data[i].commonObjectDefinition.commonObjectAttributes, newRow);
		} else if (data[i].dataType == 17) {
	    	var actionTemplateId = table+"-object-attribute-template";
			var newRow = $.qp.ar.addRow({link: obj,container:$(obj).closest('table'),templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});
			
			var curObj = data[i].externalObjectDefinition;
			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			
			$(newRow).find("input[name*='groupId']").val(rgroup);
			$(newRow).find("input[name*='tableIndex']").text(tableIndex);
			$(newRow).find("input[name*='methodInputName']").val(data[i].commonObjectAttributeName);
			$(newRow).find("label[name*='methodInputName']").text(data[i].commonObjectAttributeName);
			$(newRow).find("input[name*='methodInputCode']").val(data[i].commonObjectAttributeCode);
			$(newRow).find("label[name*='methodInputCode']").text(data[i].commonObjectAttributeCode);
			$(newRow).find("input[name*='dataType']").val(data[i].dataType);
			var dataType = CL_QP_DATATYPE[data[i].dataType];
			if(eval(data[i].arrayFlg)){
				dataType += "[]";
			}
			$(newRow).find("label[name*='dataType']").text(dataType);
			$(newRow).find("input[name*='arrayFlg']").val(data[i].arrayFlg);
			$(newRow).find("input[name*='objectType']").val(2);
			$(newRow).find("input[name*='objectId']").val(data[i].commonObjectAttributeId);
			tempAnchor = $(newRow);
			
			buildExternalObjectAttributesIn(data[i].externalObjectDefinition.externalObjectAttributes, newRow);
		} else {
			if (tempParentId < data[i].parentCommonObjectAttributeId) {
				rgroup = $(newRow).closest("tr").attr("data-ar-rgroupindex");
			} else if (tempParentId > data[i].parentCommonObjectAttributeId) {
				rgroup = rgroup.slice(0, rgroup.lastIndexOf("."));
			}
			
			var actionTemplateId = table+"-object-attribute-template";
			var newRow = $.qp.ar.addRow({link: obj,container:$(obj).closest('table'),templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});
			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			
			$(newRow).find("input[name*='groupId']").val(rgroup);
			$(newRow).find("input[name*='tableIndex']").text(tableIndex);
			$(newRow).find("input[name*='methodInputName']").val(data[i].commonObjectAttributeName);
			$(newRow).find("label[name*='methodInputName']").text(data[i].commonObjectAttributeName);
			$(newRow).find("input[name*='methodInputCode']").val(data[i].commonObjectAttributeCode);
			$(newRow).find("label[name*='methodInputCode']").text(data[i].commonObjectAttributeCode);
			$(newRow).find("input[name*='dataType']").val(data[i].dataType);
			var dataType = CL_QP_DATATYPE[data[i].dataType];
			if(eval(data[i].arrayFlg)){
				dataType += "[]";
			}
			$(newRow).find("label[name*='dataType']").text(dataType);
			$(newRow).find("input[name*='arrayFlg']").val(data[i].arrayFlg);
			$(newRow).find("input[name*='objectType']").val(2);
			$(newRow).find("input[name*='objectId']").val(data[i].commonObjectAttributeId);
			tempAnchor = $(newRow);
			
			tempParentId = data[i].parentCommonObjectAttributeId;
		}
	}
}

function fmOnChangeExternalObjectOfIn(event){
	var obj = event.target;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	$(obj).closest("tr").nextAll("tr[data-ar-rgroup^='"+rgroup+"']").each(function() {
		$(this).remove();
	});
	if(event.item != undefined){
		$(obj).closest("tr").find("input[name*='methodInputName']").val(event.item.optionLabel);
		$(obj).closest("tr").find("input[name*='methodInputCode']").val(event.item.output01);
		$(obj).closest("tr").find("input[name*='objectId']").val(event.item.optionValue);
		$(obj).closest("tr").find("input[name*='objectType']").val(1);
		var table = $(obj).closest("table").attr("name");
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
				tempAnchor = obj;
				buildExternalObjectAttributesIn(data, obj);
			}
		}
	}else{
		$(obj).closest("tr").find("input[name*='methodInputName']").val("");
		$(obj).closest("tr").find("input[name*='methodInputCode']").val("");
		$(obj).closest("tr").find("input[name*='objectType']").val(-1);
		$(obj).closest("tr").find("input[name*='objectId']").val("");
	}
}

function buildExternalObjectAttributesIn(data, obj) {
	var tempParentId = null;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	var table = $(obj).closest("table").attr("name");
	
    for(var i=0;i<data.length;i++){
		if (data[i].dataType == 17) {
	    	var actionTemplateId = table+"-object-attribute-template";
			var newRow = $.qp.ar.addRow({link: obj,container:$(obj).closest('table'),templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});

			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			var curObj = data[i].externalObjectDefinition;

			$(newRow).find("input[name*='groupId']").val(rgroup);
			$(newRow).find("input[name*='tableIndex']").text(tableIndex);
			$(newRow).find("input[name*='methodInputName']").val(data[i].externalObjectAttributeName);
			$(newRow).find("label[name*='methodInputName']").text(data[i].externalObjectAttributeName);
			$(newRow).find("input[name*='methodInputCode']").val(data[i].externalObjectAttributeCode);
			$(newRow).find("label[name*='methodInputCode']").text(data[i].externalObjectAttributeCode);
			$(newRow).find("input[name*='dataType']").val(data[i].dataType);
			var dataType = CL_QP_DATATYPE[data[i].dataType];
			if(eval(data[i].arrayFlg)){
				dataType += "[]";
			}
			$(newRow).find("label[name*='dataType']").text(dataType);
			$(newRow).find("input[name*='arrayFlg']").val(data[i].arrayFlg);
			$(newRow).find("input[name*='objectType']").val(3);
			$(newRow).find("input[name*='objectId']").val(data[i].externalObjectAttributeId);
			tempAnchor = $(newRow);
			
			buildExternalObjectAttributesIn(data[i].externalObjectDefinition.externalObjectAttributes, newRow);
		} else {
			if (tempParentId < data[i].parentExternalObjectAttributeId) {
				rgroup = $(newRow).closest("tr").attr("data-ar-rgroupindex");
			} else if (tempParentId > data[i].parentExternalObjectAttributeId) {
				rgroup = rgroup.slice(0, rgroup.lastIndexOf("."));
			}
			
			var actionTemplateId = table+"-object-attribute-template";
			var newRow = $.qp.ar.addRow({link: obj,container:$(obj).closest('table'),templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});
			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			
			$(newRow).find("input[name*='groupId']").val(rgroup);
			$(newRow).find("input[name*='tableIndex']").text(tableIndex);
			$(newRow).find("input[name*='methodInputName']").val(data[i].externalObjectAttributeName);
			$(newRow).find("label[name*='methodInputName']").text(data[i].externalObjectAttributeName);
			$(newRow).find("input[name*='methodInputCode']").val(data[i].externalObjectAttributeCode);
			$(newRow).find("label[name*='methodInputCode']").text(data[i].externalObjectAttributeCode);
			$(newRow).find("input[name*='dataType']").val(data[i].dataType);
			var dataType = CL_QP_DATATYPE[data[i].dataType];
			if(eval(data[i].arrayFlg)){
				dataType += "[]";
			}
			$(newRow).find("label[name*='dataType']").text(dataType);
			$(newRow).find("input[name*='arrayFlg']").val(data[i].arrayFlg);
			$(newRow).find("input[name*='objectType']").val(3);
			$(newRow).find("input[name*='objectId']").val(data[i].externalObjectAttributeId);
			tempAnchor = $(newRow);
			tempParentId = data[i].parentExternalObjectAttributeId;
		}
	}
}

function fmOnChangeCommonObjectOfOu(event){
	var obj = event.target;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	$(obj).closest("tr").nextAll("tr[data-ar-rgroup^='"+rgroup+"']").each(function() {
		$(this).remove();
	});
	if(event.item != undefined){
		$(obj).closest("tr").find("input[name*='methodOutputName']").val(event.item.optionLabel);
		$(obj).closest("tr").find("input[name*='methodOutputCode']").val(event.item.output01);
		$(obj).closest("tr").find("input[name*='objectType']").val(0);
		$(obj).closest("tr").find("input[name*='objectId']").val(event.item.optionValue);
		var table = $(obj).closest("table").attr("name");
		
		if(rgroup == undefined || rgroup == ""){
			level = 1;
		}else{
			level = (rgroup.match(/\./g) || []).length + 1;
		}
		if(event.item.optionValue != ""){
			var url = CONTEXT_PATH + "/commonobjectdefinition/getDetailsCommonObjectDefinition?commonObjectDefinitionId="+event.item.optionValue+"&level="+level+"&r="+Math.random();
			var data = $.qp.getData(url);
			if(data.length>0){
				tempAnchor = obj;
				buildCommonObjectAttributesOu(data, obj);
			}
		}
	}else{
		$(obj).closest("tr").find("input[name*='methodOutputName']").val("");
		$(obj).closest("tr").find("input[name*='methodOutputCode']").val("");
		$(obj).closest("tr").find("input[name*='objectType']").val(-1);
		$(obj).closest("tr").find("input[name*='objectId']").val("");
	}
}
function buildCommonObjectAttributesOu(data, obj) {
	var tempParentId = null;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	var table = $(obj).closest("table").attr("name");
	
    for(var i=0;i<data.length;i++){
		if (data[i].dataType == 16) {
	    	var actionTemplateId = table+"-object-attribute-template";
			var newRow = $.qp.ar.addRow({link: obj,container:$(obj).closest('table'),templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});
			
			var curObj = data[i].commonObjectDefinition;
			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			
			$(newRow).find("input[name*='groupId']").val(rgroup);
			$(newRow).find("input[name*='tableIndex']").text(tableIndex);
			$(newRow).find("input[name*='methodOutputName']").val(data[i].commonObjectAttributeName);
			$(newRow).find("label[name*='methodOutputName']").text(data[i].commonObjectAttributeName);
			$(newRow).find("input[name*='methodOutputCode']").val(data[i].commonObjectAttributeCode);
			$(newRow).find("label[name*='methodOutputCode']").text(data[i].commonObjectAttributeCode);
			$(newRow).find("input[name*='dataType']").val(data[i].dataType);
			var dataType = CL_QP_DATATYPE[data[i].dataType];
			if(eval(data[i].arrayFlg)){
				dataType += "[]";
			}
			$(newRow).find("label[name*='dataType']").text(dataType);
			$(newRow).find("input[name*='arrayFlg']").val(data[i].arrayFlg);
			$(newRow).find("input[name*='objectType']").val(2);
			$(newRow).find("input[name*='objectId']").val(data[i].commonObjectAttributeId);
			tempAnchor = $(newRow);
			
			buildCommonObjectAttributesOu(data[i].commonObjectDefinition.commonObjectAttributes, newRow);
		} else if (data[i].dataType == 17) {
	    	var actionTemplateId = table+"-object-attribute-template";
			var newRow = $.qp.ar.addRow({link: obj,container:$(obj).closest('table'),templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});
			
			var curObj = data[i].externalObjectDefinition;
			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			
			$(newRow).find("input[name*='groupId']").val(rgroup);
			$(newRow).find("input[name*='tableIndex']").text(tableIndex);
			$(newRow).find("input[name*='methodOutputName']").val(data[i].commonObjectAttributeName);
			$(newRow).find("label[name*='methodOutputName']").text(data[i].commonObjectAttributeName);
			$(newRow).find("input[name*='methodOutputCode']").val(data[i].commonObjectAttributeCode);
			$(newRow).find("label[name*='methodOutputCode']").text(data[i].commonObjectAttributeCode);
			$(newRow).find("input[name*='dataType']").val(data[i].dataType);
			var dataType = CL_QP_DATATYPE[data[i].dataType];
			if(eval(data[i].arrayFlg)){
				dataType += "[]";
			}
			$(newRow).find("label[name*='dataType']").text(dataType);
			$(newRow).find("input[name*='arrayFlg']").val(data[i].arrayFlg);
			$(newRow).find("input[name*='objectType']").val(1);
			$(newRow).find("input[name*='objectId']").val(data[i].commonObjectAttributeId);
			tempAnchor = $(newRow);
			
			buildExternalObjectAttributesOu(data[i].externalObjectDefinition.externalObjectAttributes, newRow);
		} else {
			if (tempParentId < data[i].parentCommonObjectAttributeId) {
				rgroup = $(newRow).closest("tr").attr("data-ar-rgroupindex");
			} else if (tempParentId > data[i].parentCommonObjectAttributeId) {
				rgroup = rgroup.slice(0, rgroup.lastIndexOf("."));
			}
			
			var actionTemplateId = table+"-object-attribute-template";
			var newRow = $.qp.ar.addRow({link: obj,container:$(obj).closest('table'),templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});
			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			
			$(newRow).find("input[name*='groupId']").val(rgroup);
			$(newRow).find("input[name*='tableIndex']").text(tableIndex);
			$(newRow).find("input[name*='methodOutputName']").val(data[i].commonObjectAttributeName);
			$(newRow).find("label[name*='methodOutputName']").text(data[i].commonObjectAttributeName);
			$(newRow).find("input[name*='methodOutputCode']").val(data[i].commonObjectAttributeCode);
			$(newRow).find("label[name*='methodOutputCode']").text(data[i].commonObjectAttributeCode);
			$(newRow).find("input[name*='dataType']").val(data[i].dataType);
			var dataType = CL_QP_DATATYPE[data[i].dataType];
			if(eval(data[i].arrayFlg)){
				dataType += "[]";
			}
			$(newRow).find("label[name*='dataType']").text(dataType);
			$(newRow).find("input[name*='arrayFlg']").val(data[i].arrayFlg);
			$(newRow).find("input[name*='objectType']").val(2);
			$(newRow).find("input[name*='objectId']").val(data[i].commonObjectAttributeId);
			tempAnchor = $(newRow);
			tempParentId = data[i].parentCommonObjectAttributeId;
		}
	}
}

function fmOnChangeExternalObjectOfOu(event){
	var obj = event.target;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	$(obj).closest("tr").nextAll("tr[data-ar-rgroup^='"+rgroup+"']").each(function() {
		$(this).remove();
	});
	if(event.item != undefined){
		$(obj).closest("tr").find("input[name*='methodOutputName']").val(event.item.optionLabel);
		$(obj).closest("tr").find("input[name*='methodOutputCode']").val(event.item.output01);
		$(obj).closest("tr").find("input[name*='objectId']").val(event.item.optionValue);
		$(obj).closest("tr").find("input[name*='objectType']").val(1);
		var table = $(obj).closest("table").attr("name");
		if(rgroup == undefined || rgroup == ""){
			level = 1;
		}else{
			level = (rgroup.match(/\./g) || []).length + 1;
		}
		if(event.item.optionValue != ""){
			var url = CONTEXT_PATH + "/externalobjectdefinition/getDetailsExternalObjectDefinition?externalObjectDefinitionId="+event.item.optionValue+"&level="+level+"&r="+Math.random();
			var data = $.qp.getData(url);
			if(data.length>0){
				tempAnchor = obj;
				buildExternalObjectAttributesOu(data, obj);
			}
		}
	}else{
		$(obj).closest("tr").find("input[name*='methodOutputName']").val("");
		$(obj).closest("tr").find("input[name*='methodOutputCode']").val("");
		$(obj).closest("tr").find("input[name*='objectType']").val(-1);
		$(obj).closest("tr").find("input[name*='objectId']").val("");
	}
}
function buildExternalObjectAttributesOu(data, obj) {
	var tempParentId = null;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	var table = $(obj).closest("table").attr("name");
	
    for(var i=0;i<data.length;i++){
		if (data[i].dataType == 17) {
	    	var actionTemplateId = table+"-object-attribute-template";
			var newRow = $.qp.ar.addRow({link: obj,container:$(obj).closest('table'),templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});

			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			var curObj = data[i].externalObjectDefinition;

			$(newRow).find("input[name*='groupId']").val(rgroup);
			$(newRow).find("input[name*='tableIndex']").text(tableIndex);
			$(newRow).find("input[name*='methodOutputName']").val(data[i].externalObjectAttributeName);
			$(newRow).find("label[name*='methodOutputName']").text(data[i].externalObjectAttributeName);
			$(newRow).find("input[name*='methodOutputCode']").val(data[i].externalObjectAttributeCode);
			$(newRow).find("label[name*='methodOutputCode']").text(data[i].externalObjectAttributeCode);
			$(newRow).find("input[name*='dataType']").val(data[i].dataType);
			var dataType = CL_QP_DATATYPE[data[i].dataType];
			if(eval(data[i].arrayFlg)){
				dataType += "[]";
			}
			$(newRow).find("label[name*='dataType']").text(dataType);
			$(newRow).find("input[name*='arrayFlg']").val(data[i].arrayFlg);
			$(newRow).find("input[name*='objectType']").val(1);
			$(newRow).find("input[name*='objectId']").val(data[i].externalObjectAttributeId);
			tempAnchor = $(newRow);
			
			buildExternalObjectAttributesOu(data[i].externalObjectDefinition.externalObjectAttributes, newRow);
		} else {
			if (tempParentId < data[i].parentExternalObjectAttributeId) {
				rgroup = $(newRow).closest("tr").attr("data-ar-rgroupindex");
			} else if (tempParentId > data[i].parentExternalObjectAttributeId) {
				rgroup = rgroup.slice(0, rgroup.lastIndexOf("."));
			}
			
			var actionTemplateId = table+"-object-attribute-template";
			var newRow = $.qp.ar.addRow({link: obj,container:$(obj).closest('table'),templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});
			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			
			$(newRow).find("input[name*='groupId']").val(rgroup);
			$(newRow).find("input[name*='tableIndex']").text(tableIndex);
			$(newRow).find("input[name*='methodOutputName']").val(data[i].externalObjectAttributeName);
			$(newRow).find("label[name*='methodOutputName']").text(data[i].externalObjectAttributeName);
			$(newRow).find("input[name*='methodOutputCode']").val(data[i].externalObjectAttributeCode);
			$(newRow).find("label[name*='methodOutputCode']").text(data[i].externalObjectAttributeCode);
			$(newRow).find("input[name*='dataType']").val(data[i].dataType);
			var dataType = CL_QP_DATATYPE[data[i].dataType];
			if(eval(data[i].arrayFlg)){
				dataType += "[]";
			}
			$(newRow).find("label[name*='dataType']").text(dataType);
			$(newRow).find("input[name*='arrayFlg']").val(data[i].arrayFlg);
			$(newRow).find("input[name*='objectType']").val(3);
			$(newRow).find("input[name*='objectId']").val(data[i].externalObjectAttributeId);
			tempAnchor = $(newRow);
			tempParentId = data[i].parentExternalObjectAttributeId;
		}
	}
}

function saveFunctionMaster() {
	var allowedFiles = [".java"];
    var fileUpload = $("span.file-input-name").text();
    
    if (fileUpload != undefined || fileUpload != "") {
        $("#fileName").val(fileUpload);
    }
    
    var regex = new RegExp("([a-zA-Z0-9\s_\\.\-:])+(" + allowedFiles.join('|') + ")$");
    if (fileUpload != undefined && fileUpload != "") {
        if (!regex.test(fileUpload.toLowerCase())) {
    		$.qp.showAlertModal($.qp.getModuleMessage('err.functionmaster.0069'));
    		return false;
        }
    }
	
	var table = "#tblLstMethod";

	var flgIsErr = false;
	
	$(table).find(">tbody>tr.ar-dataRow").each(function(i) {
		var msg = $.qp.common.validation.validationForm(table);
		
		if (msg != "") {
			$.qp.showAlertModal(msg);
			flgIsErr = true;
			return false;
		} else {
			var mapKey = {};
			var currentMethod = $(this).attr('data-ar-rindex');
			
			$(this).find('[name="tblInput"]').find(">tbody>tr.ar-dataRow").each(function(i) {
				var rgroup = $(this).attr("data-ar-rgroupindex");
				var rgroupOf = $(this).attr("data-ar-rgroup");
				
				var id = $(this).find("input[name*='methodInputId']").val();
				if (id == "") {
					id = mapKeyTemp["inputObject"];
					mapKeyTemp["inputObject"] = parseInt(id) + 1;
					id = "in" + mapKeyTemp["inputObject"];
				}
				mapKey[rgroup] = id;
				$(this).find("input[name*='methodInputId']").val(id);
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
					$(this).find("input[name*='parentFunctionMethodInputId']").val(mapKey[keyParent]);
				} else {
				}
		
				$(this).find("input[name*='itemSeqNo']").val(i);
				$(this).find("input[name*='.groupId']").val(rgroupOf);
				$(this).find("input[name*='.tableIndex']").val(rgroup);
			});
			
			$(this).find('[name="tblOutput"]').find(">tbody>tr.ar-dataRow").each(function(i) {
				var rgroup = $(this).attr("data-ar-rgroupindex");
				var rgroupOf = $(this).attr("data-ar-rgroup");
				
				var id = $(this).find("input[name*='methodOutputId']").val();
				if (id == "") {
					id = mapKeyTemp["outputObject"];
					mapKeyTemp["outputObject"] = parseInt(id) + 1;
					id = "ou" + mapKeyTemp["outputObject"];
				}
				
				mapKey[rgroup] = id;
				$(this).find("input[name*='methodOutputId']").val(id);
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
					$(this).find("input[name*='parentFunctionMethodOutputId']").val(mapKey[keyParent]);
				} else {
				}
						
				$(this).find("input[name*='itemSeqNo']").val(i);
				$(this).find("input[name*='.groupId']").val(rgroupOf);
				$(this).find("input[name*='.tableIndex']").val(rgroup);
			});
			
			return true;
		}
	});
	
	if(flgIsErr) return false;
	
	}

function onChangeDataType(obj){
	var table = $(obj).closest("table").attr("name");
	var type = $(obj).val();
	var oldValue= $(obj).attr("oldvalue");
	var currentRow = $(obj).closest("tr");
	var $Caller = $(obj);
	var $ParentRow = $Caller.closest("tr");
	var rgroupindex = $ParentRow.attr("data-ar-rgroupindex") || "";
	var rgroup= $ParentRow.attr("data-ar-rgroup") || "";
	var actionTemplateId ; 
	
	
	var newRow ;
	if(oldValue == '14' || oldValue == '16' || oldValue == '17'){
		var nextRow = $(currentRow).nextAll().first();
		
		actionTemplateId = table+"-attribute-template";
		
		if(type=="14"){
			actionTemplateId = table + "-entity-template";
		} else if(type=="16"){
			actionTemplateId = table + "-common-object-template";
		} else if(type=="17"){
			actionTemplateId = table + "-external-object-template";
		}
		
		if(nextRow.length >0){
			newRow = $.qp.ar.addRow({link:obj,container:$(obj).closest('table'),templateId:actionTemplateId,templateData:{ groupId:rgroup},position:{anchor:nextRow,string:"before"} });
		}else{
			newRow = $.qp.ar.addRow({link:obj,container:$(obj).closest('table'),templateId:actionTemplateId,templateData:{ groupId:rgroup} });
		}
		$(newRow).find("[name*='dataType']").val(type);

		$.qp.ar.removeRow({link:currentRow});
		if(type == "0"){
			var actionTemplateId = table+"-action-template";
			$.qp.ar.addRow({link:obj,container:$(newRow).closest('table'),templateId:actionTemplateId,templateData:{ groupId:rgroupindex},position:{anchor:newRow,string:"after"} });
		}
		$(newRow).find("select").attr("oldvalue",type);
	} else {
		if(type=="0"){
			actionTemplateId = table+"-action-template";
			$.qp.ar.addRow({link:obj,container:$(obj).closest('table'),templateId:actionTemplateId,templateData:{groupId:rgroupindex},position:{anchor:$Caller.parents("tr:first"),string:"after"} });
		} else if(type=="14"){
			actionTemplateId = table + "-entity-template";
			newRow = $.qp.ar.addRow({link: obj,container:$(obj).closest('table'),templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor:currentRow,string:"after"} });
			$.qp.ar.removeRow({link:currentRow});
		} else if(type=="16"){
			actionTemplateId = table + "-common-object-template";
			newRow = $.qp.ar.addRow({link: obj,container:$(obj).closest('table'),templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor:currentRow,string:"after"} });
			$.qp.ar.removeRow({link:currentRow});
		} else if(type=="17"){
			actionTemplateId = table + "-external-object-template";
			newRow = $.qp.ar.addRow({link: obj,container:$(obj).closest('table'),templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor:currentRow,string:"after"} });
			$.qp.ar.removeRow({link:currentRow});
		} else{
			$.qp.ar.removeRow({link : obj,removeType : 'onlyDescendants'});
		}
		$(obj).attr("oldvalue",type);
	}
	if(newRow != undefined){
		if(table == "tblOutput"){
			var methodOutputCode = $ParentRow.find("input[name$='methodOutputCode']").val();
			$(newRow).find("input[name$='methodOutputCode']").val(methodOutputCode);
		}else if(table == "tblInput"){
			var methodInputCode = $ParentRow.find("input[name$='methodInputCode']").val();
			$(newRow).find("input[name$='methodInputCode']").val(methodInputCode);
		}
	}
};

function initAddButton(isView){
	$("table[name*='tblInput']").each(function(i) {
		$.qp.functionmaster.initAddButtonRow('tblInput'+i,"tblInput-action-template",isView);
	});
	
	$("table[name*='tblOutput']").each(function(i) {
		$.qp.functionmaster.initAddButtonRow('tblOutput'+i,"tblOutput-action-template",isView);
	});
}

function checkFileSize(){
	var uploadFile = $("[name='file']")[0].files[0];
	var maxSize = $("[name='maxSize']").val();
	
	if(uploadFile.size > maxSize*1024*1024){
		//alert(fcomMsgSource['err.sys.0201']);
		$.qp.alert(fcomMsgSource['err.sys.0208'].replace("{0}",maxSize));
		return false;
	}
	
	return true;
}
