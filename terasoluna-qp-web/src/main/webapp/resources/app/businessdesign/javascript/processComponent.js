/**
 * process event in modal
 * @author quangvd
 */
function saveModalFeedbackSetting(thiz) {
	var modal = BD_MODAL_NAME.FEEDBACK;
	var obj = $(modal).data("target");
	var instance = $(modal).data("instance");
	var data = convertToJson($(modal).data("data"));
	var label  = "";
	if($(modal).find("input[name='label']").val() != undefined){
		label = $(modal).find("input[name='label']").val();
	}	
	if (data.label != undefined) {
		data.label = label;
	} else {		
		data["label"] = label;
	}
	
	var remark  = "";
	if($(modal).find("textarea[name='remark']").val() != undefined){
		remark = $(modal).find("textarea[name='remark']").val();
	}
	if (data.remark != undefined) {
		data.remark = remark;
	} else {		
		data["remark"] = remark;
	}
	
	var messageCode  = "";
	if($(modal).find("input[name='messageCode']").val() != undefined){
		messageCode = $(modal).find("input[name='messageCode']").val();
	}
	if (data.messageCode != undefined) {
		data.messageCode = messageCode;
	} else {		
		data["messageCode"] = messageCode;
	}
	
	var existedMessageFlg  = "";
	if($(modal).find("input[name='messageCodeAutocomplete']").attr("selectedvalue") != undefined){
		existedMessageFlg = $(modal).find("input[name='messageCodeAutocomplete']").attr("selectedvalue");
	}
	if (data.existedMessageFlg != undefined) {
		data.existedMessageFlg = existedMessageFlg;
	} else {		
		data["existedMessageFlg"] = existedMessageFlg;
	}
	
	var messageCodeAutocomplete  = "";
	if($(modal).find("input[name='messageCodeAutocomplete']").val() != undefined){
		messageCodeAutocomplete = $(modal).find("input[name='messageCodeAutocomplete']").val();
	}
	if (data.messageCodeAutocomplete != undefined) {
		data.messageCodeAutocomplete = messageCodeAutocomplete;
	} else {		
		data["messageCodeAutocomplete"] = messageCodeAutocomplete;
	}
	
	var type  = "";
	if($(modal).find("select[name='type']").val() != undefined){
		type = $(modal).find("select[name='type']").val();
	}
	if (data.type != undefined) {
		data.type = type;
	} else {		
		data["type"] = type;
	}
	
	var messageParameter  = "";
	if($(modal).find("input[name='messageParameter']").val() != undefined){
		messageParameter = ($(modal).find("input[name='messageParameter']").val() == null || $(modal).find("input[name='messageParameter']").val().length == 0) ? "[]" : $(modal).find("input[name='messageParameter']").val();
	}
	if (data.messageParameter != undefined) {
		data.messageParameter = convertToJson(messageParameter);
	} else {		
		data["messageParameter"] = convertToJson(messageParameter);
	}
	var validationMessage = $.qp.businessdesign.validaionComponent.validationFeedbackComp($.qp.businessdesign.validaionComponent.DETAIL_CHECK,data,true,thiz);
	if(validationMessage != undefined && validationMessage != ""){
		$.qp.showAlertModal(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("data-original-title",remark);
		$(obj).find("span.component-name").text(label);
		$(modal).modal("hide");
		instance.repaintEverything();
	}
	saveCommonSetting($(obj));
}
function saveModalMessageParameterSetting(thiz){
	var modal =  $(thiz).closest(BD_MODAL_NAME.MESSAGEPARAMETER);
	var obj = $(modal).data("target");
	var parameters = [];
	$(modal).find("#messageParameter").find("tbody>tr").each(function(index){
		var objData = new Object();
		var parameterType = $(this).find("input[name^='parameterType']:checked").val();
		var typeTD = $(this).closest("tr").find("td[type='"+parameterType+"']");
		var parameterCode = null;
		var parameterValue = null;
		var parameterCodeAutocomplete = null;
		var messageLevel = null;
		var lstParameterIndex = null;
		if(typeTD.length > 0){
			switch (parameterType) {
			case "0":
				parameterCode = $(typeTD).find("input[name='parameterCode']").val() == undefined ? null : $(typeTD).find("input[name='parameterCode']").val();
				parameterCodeAutocomplete = $(typeTD).find("input[name='parameterCodeAutocomplete']").val() == undefined ? null : $(typeTD).find("input[name='parameterCodeAutocomplete']").val() ;
				messageLevel = $(this).find("input[name='messageLevel']").val();
				
				var existedMessageFlg  = "";
				if($(typeTD).find("input[name='parameterCodeAutocomplete']").attr("selectedvalue") != undefined){
					existedMessageFlg = $(typeTD).find("input[name='parameterCodeAutocomplete']").attr("selectedvalue");
				}
				if (objData.existedMessageFlg != undefined) {
					objData.existedMessageFlg = existedMessageFlg;
				}else{
					objData["existedMessageFlg"] = existedMessageFlg;
				}
				break;
			case "1":
				parameterValue = $(typeTD).find("input[name='parameterValue']").val() == undefined ? null : $(typeTD).find("input[name='parameterValue']").val();
				break;
			case "2":
				var divIndex = $(typeTD).find("div.bd-content[id='parameter']")
				var parameterPattern = $(divIndex).attr("pattern");
				if(parameterPattern=="0"){
					parameterCode = $(typeTD).find("input[name='parameterCode']").val() == undefined ? null : $(typeTD).find("input[name='parameterCode']").val();
					parameterCodeAutocomplete = $(typeTD).find("input[name='parameterCodeAutocomplete']").val() == undefined ? null : $(typeTD).find("input[name='parameterCodeAutocomplete']").val();
					parameterScope = ($(typeTD).find("input[name='parameterCode']").attr("parameterScope") == undefined || $(typeTD).find("input[name='parameterCode']").attr("parameterScope") == "") ? null : $(typeTD).find("input[name='parameterCode']").attr("parameterScope");
					lstParameterIndex = [];
				}else if(parameterPattern=="1"){
					parameterCode = isEmptyQP($(divIndex).attr("parameterId")) ? null : $(divIndex).attr("parameterId");
					parameterScope = (isEmptyQP($(divIndex).attr("parameterId"))) ? null : $(divIndex).attr("parameterId");
					var lstParameterIndex =[];
					$(divIndex).find("input[name='parameterIndexId']").each(function (){
						var item = new Object();
						var parameterIndexId = $(this).val() == undefined ? null : $(this).val();
						var parameterIndexIdAutocomplete = $(this).prev().val() == undefined ? null : $(this).prev().val();
						var parameterIndexType = ($(this).attr("indextype") == undefined || $(this).attr("indextype") == "") ? "3" : $(this).attr("indextype");
						var parameterId = ($(this).attr("parameterId") == undefined || $(this).attr("parameterId") == "") ? null : $(this).attr("parameterId");
						item.parameterIndexId = parameterIndexId;
						item.parameterIndexIdAutocomplete = parameterIndexIdAutocomplete;
						item.parameterIndexType = parameterIndexType;
						item.parameterId = parameterId;
						if(!isEmptyQP(parameterIndexId))
							lstParameterIndex.push(item);
					});
					lstParameterIndex = lstParameterIndex;
				}
				break;
			default:
				break;
			}
		}

		objData.lstParameterIndex = lstParameterIndex;
		objData.parameterCode = parameterCode;
		objData.parameterCodeAutocomplete = parameterCodeAutocomplete;
		objData.parameterType = parameterType;
		objData.parameterValue = parameterValue;
		objData.itemSequenceNo = index;
		objData.messageLevel = messageLevel;
		parameters.push(objData);
	});
	var value =  convertToString(parameters);
	$(obj).closest("table").find("input[name='messageParameter']").val(value);
	$(modal).modal("hide");
}
function saveModalIfSetting(thiz){
	var modal = BD_MODAL_NAME.IF;
	var obj = $(modal).data("target");
	var instance = $(modal).data("instance");
	var data = convertToJson($(modal).data("data"));
	var oldIfConditionDetails = data.ifConditionDetails;
	var label  = "";
	if($(modal).find("input[name='label']").val() != undefined){
		label = $(modal).find("input[name='label']").val();
	}	
	if (data.label != undefined) {
		data.label = label;
	} else {		
		data["label"] = label;
	}
	
	var remark  = "";
	if($(modal).find("textarea[name='remark']").val() != undefined){
		remark = $(modal).find("textarea[name='remark']").val();
	}
	if (data.remark != undefined) {
		data.remark = remark;
	} else {		
		data["remark"] = remark;
	}
	var conditions = "";
	var count = 0;
	var arrStatus = new Array();
	var amountCondition = 1;
	var ifConditionDetails = [];
	amountCondition = $(modal).find("#tbl-ifcondition").find("tbody>tr").length;
	$(modal).find("#tbl-ifcondition").find("tbody>tr").each(function(){
		var objConditionDetais = new Object();
		objConditionDetais.caption = $(this).find("input[name='caption']").val() == undefined ? "" : $(this).find("input[name='caption']").val();
		objConditionDetais.conditionRemark = $(this).find("input[name='conditionRemark']").val() == undefined ? "" : $(this).find("input[name='conditionRemark']").val();
		objConditionDetais.formulaDefinitionContent = ($(this).find("label[name='formulaDefinitionContent']").text() == undefined || $(this).find("label[name='formulaDefinitionContent']").text() == "" ) ? null : $(this).find("label[name='formulaDefinitionContent']").text();
		objConditionDetais.formulaDefinitionDetails = ($(this).find("input[name='formulaDefinitionDetails']").val() == undefined || $(this).find("input[name='formulaDefinitionDetails']").val() == "" ) ? [] : convertToJson($(this).find("input[name='formulaDefinitionDetails']").val());
		ifConditionDetails.push(objConditionDetais);
		
		count ++;
		if(amountCondition>1){
			var connection = new Object();
			connection.type = count + "."+objConditionDetais.caption;
			connection.status = false;
			arrStatus.push(connection);
		}
	});
	
	// Initial else condition
	var objConditionDetais = new Object();
	objConditionDetais.caption = $(modal).find("#tbl-elsecondition").find("label[name='caption']").text();
	objConditionDetais.conditionRemark =  $(modal).find("#tbl-elsecondition").find("input[name='conditionRemark']").val() == undefined ? "" :  $(modal).find("#tbl-elsecondition").find("input[name='conditionRemark']").val();
	objConditionDetais.formulaDefinitionContent = "";
	objConditionDetais.formulaDefinitionDetails = [];
	objConditionDetais.usedConditionFlg = $(modal).find("#tbl-elsecondition").find("input[name='usedConditionFlg']").prop("checked");
	ifConditionDetails.push(objConditionDetais);
	var connection = new Object();
	var count = $(modal).find("#tbl-elsecondition").find("td[name='indexOfElse']").text();
	connection.type = count + "."+objConditionDetais.caption;
	connection.status = false;
	arrStatus.push(connection);
	//
	
	if (data.ifConditionDetails != undefined) {
		data.ifConditionDetails = ifConditionDetails;
	} else {		
		data["ifConditionDetails"] = ifConditionDetails;
	}
	
	var validationMessage = $.qp.businessdesign.validaionComponent.validationIfComp($.qp.businessdesign.validaionComponent.DETAIL_CHECK,data,true,thiz);
	if(validationMessage != undefined && validationMessage != ""){
		$.qp.showAlertModal(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("data-original-title",remark);
		$(obj).attr("connectionTarget",count);
		$(obj).find("span.component-name").text(label);
//		if(amountCondition <=1){
//			arrStatus = new Array();
//			arrStatus = [{type:"TRUE", status:false, connectionId:""},{type:"FALSE", status:false, connectionId:""}];
//		}
//		arrStatus.push({type:"", status:false, connectionId:""});
//		arrConnectionComponent[$(obj).attr("id")] = arrStatus;
		if(oldIfConditionDetails != undefined && ifConditionDetails != undefined){
			if(oldIfConditionDetails.length != ifConditionDetails.length){
				var connectionSources = instance.getConnections({ source:$(obj).attr("id") });
				for(var i = 0;i<connectionSources.length;i++){
					processDeleteConnectionOfDeletedNode(connectionSources[i],instance);
				}
			}else{
				var connectionSources = instance.getConnections({ source:$(obj).attr("id") });
				for(var i = 0;i<connectionSources.length;i++){
					var label = connectionSources[i].getOverlay("label").getLabel();
					var indexTemp = -1;
					for(var j = 0;j<oldIfConditionDetails.length;j++){
						var labelTemp = (j+1) + "."+oldIfConditionDetails[j].caption;
						if(label == labelTemp){
							indexTemp = j;
							break;
						}
					}
					if(indexTemp > -1){
						var labelTemp2 = (indexTemp+1) + "."+ifConditionDetails[indexTemp].caption; 
						connectionSources[i].getOverlay("label").setLabel(labelTemp2);
					}
				}
			}
		}
		$(modal).modal("hide");
		instance.repaintEverything();
	}
	saveCommonSetting($(obj));
}
function saveModalNavigationSetting(thiz) {
	var modal = BD_MODAL_NAME.NAVIGATOR;
	var table = "#tbl-navigator-inputbean";
	
	var obj = $(modal).data("target");
	var instance = $(modal).data("instance");
	var data = convertToJson($(modal).data("data"));
	var label  = "";
	if($(modal).find("input[name='label']").val() != undefined){
		label = $(modal).find("input[name='label']").val();
	}	
	if (data.label != undefined) {
		data.label = label;
	} else {		
		data["label"] = label;
	}
	
	var moduleId = null;
	var moduleIdAutocomplete = null;
	if($(modal).find("input[name='moduleId']").val() != undefined && $(modal).find("input[name='moduleId']").val() != null && $(modal).find("input[name='moduleId']").val().length >0){
		moduleId = $(modal).find("input[name='moduleId']").val();
		moduleIdAutocomplete = $(modal).find("input[name='moduleIdAutocomplete']").val();
	}
	if (data.moduleId != undefined) {
		data.moduleId = moduleId;
	} else {		
		data["moduleId"] = moduleId;
	}
	
	if (data.moduleIdAutocomplete != undefined) {
		data.moduleIdAutocomplete = moduleIdAutocomplete;
	} else {		
		data["moduleIdAutocomplete"] = moduleIdAutocomplete;
	}
	
	var remark  = "";
	if($(modal).find("textarea[name='remark']").val() != undefined){
		remark = $(modal).find("textarea[name='remark']").val();
	}
	if (data.remark != undefined) {
		data.remark = remark;
	} else {		
		data["remark"] = remark;
	}
	
	var navigatorToId  = "";
	if($(modal).find("input[name='navigatorToId']").val() != undefined){
		navigatorToId = $(modal).find("input[name='navigatorToId']").val();
	}
	if (data.navigatorToId != undefined) {
		data.navigatorToId = navigatorToId;
	} else {		
		data["navigatorToId"] = navigatorToId;
	}
	
	if (data.navigatorToIdRefer != undefined) {
		data.navigatorToIdRefer = navigatorToId;
	} else {		
		data["navigatorToIdRefer"] = navigatorToId;
	}
	
	var navigatorToIdAutocomplete  = "";
	if($(modal).find("input[name='navigatorToIdAutocomplete']").val() != undefined){
		navigatorToIdAutocomplete = $(modal).find("input[name='navigatorToIdAutocomplete']").val();
	}
	if (data.navigatorToIdAutocomplete != undefined) {
		data.navigatorToIdAutocomplete = navigatorToIdAutocomplete;
	} else {		
		data["navigatorToIdAutocomplete"] = navigatorToIdAutocomplete;
	}

	var navigatorToName  = "";
	if($(modal).find("input[name='navigatorToIdAutocomplete']").attr("navigatorToName") != undefined){
		navigatorToName = $(modal).find("input[name='navigatorToIdAutocomplete']").attr("navigatorToName");
	}
	if (data.navigatorToName != undefined) {
		data.navigatorToName = navigatorToName;
	} else {		
		data["navigatorToName"] = navigatorToName;
	}
	
	var navigatorToType  = "";
	if($(modal).find("input[name='navigatorToType']").val() != undefined){
		navigatorToType = $(modal).find("input[name='navigatorToType']:checked").val();
	}	
	if (data.navigatorToType != undefined) {
		data.navigatorToType = navigatorToType;
	} else {		
		data["navigatorToType"] = navigatorToType;
	}
	
	var transitionType  = "";
	if($(modal).find("input[name='transitionType']:checked").val() != undefined){
		transitionType = $(modal).find("input[name='transitionType']:checked").val();
	}	
	if (data.transitionType != undefined) {
		data.transitionType = transitionType;
	} else {		
		data["transitionType"] = transitionType;
	}
	
	var parameterInputBeans = new Array();
	$(modal).find("#tbl-navigator-inputbean").find("tbody>tr").each(function(){
		var objInputBean = new Object();
		objInputBean.inputBeanId = $(this).find("input[name='inputBeanId']").val() == undefined ? null : $(this).find("input[name='inputBeanId']").val();
		objInputBean.inputBeanName = $(this).find("input[name='inputBeanName']").val() == undefined ? null : $(this).find("input[name='inputBeanName']").val() ;
		objInputBean.inputBeanCode = $(this).find("label[name='inputBeanCode']").text() == undefined ? null : $(this).find("label[name='inputBeanCode']").text();
		objInputBean.dataType = $(this).find("input[name='dataType']").val() == undefined ? null : $(this).find("input[name='dataType']").val();
		objInputBean.arrayFlg = $(this).find("input[name='arrayFlg']").val() == undefined ? null : eval($(this).find("input[name='arrayFlg']").val());
		
//		objInputBean.parameterId = ($(this).find("input[name='parameterId']").val() == undefined || $(this).find("input[name='parameterId']").val() == "" ) ? null : $(this).find("input[name='parameterId']").val();
//		objInputBean.parameterIdAutocomplete = ($(this).find("input[name='parameterIdAutocomplete']").val() == undefined  || $(this).find("input[name='parameterIdAutocomplete']").val() == "" )? null : $(this).find("input[name='parameterIdAutocomplete']").val();
//		var parameterScope = ($(this).find("input[name='parameterId']").attr("parameterScope") == undefined || $(this).find("input[name='parameterId']").attr("parameterScope") == "") ? null : $(this).find("input[name='parameterId']").attr("parameterScope");
//		objInputBean.parameterScope = parameterScope;
		
		var parameterPattern = $(this).find("div.bd-content[name='parameter']").attr("pattern");
		if(parameterPattern=="0"){
			objInputBean.parameterId = $(this).find("input[name='parameterId']").val() == undefined ? null : $(this).find("input[name='parameterId']").val();
			objInputBean.parameterIdAutocomplete = $(this).find("input[name='parameterIdAutocomplete']").val() == undefined ? null : $(this).find("input[name='parameterIdAutocomplete']").val();
			objInputBean.parameterScope = ($(this).find("input[name='parameterId']").attr("parameterScope") == undefined || $(this).find("input[name='parameterId']").attr("parameterScope") == "") ? null : $(this).find("input[name='parameterId']").attr("parameterScope");
			objInputBean.lstParameterIndex = [];
		}else if(parameterPattern=="1"){
			objInputBean.parameterId = isEmptyQP($(this).find("div.bd-content[name='parameter']").attr("parameterId")) ? null : $(this).find("div.bd-content[name='parameter']").attr("parameterId");
			objInputBean.parameterScope = (isEmptyQP($(this).find("div.bd-content[name='parameter']").attr("parameterScope"))) ? null : $(this).find("div.bd-content[name='parameter']").attr("parameterScope");
			var lstParameterIndex =[];
			$(this).find("div.bd-content[name='parameter']").find("input[name='parameterIndexId']").each(function (){
				var item = new Object();
				var parameterIndexId = $(this).val() == undefined ? null : $(this).val();
				var parameterIndexIdAutocomplete = $(this).prev().val() == undefined ? null : $(this).prev().val();
				var parameterIndexType = ($(this).attr("indextype") == undefined || $(this).attr("indextype") == "") ? "3" : $(this).attr("indextype");
				var parameterId = ($(this).attr("parameterId") == undefined || $(this).attr("parameterId") == "") ? null : $(this).attr("parameterId");
				item.parameterIndexId = parameterIndexId;
				item.parameterIndexIdAutocomplete = parameterIndexIdAutocomplete;
				item.parameterIndexType = parameterIndexType;
				item.parameterId = parameterId;
				if(!isEmptyQP(parameterIndexId))
					lstParameterIndex.push(item);
			});
			objInputBean.lstParameterIndex = lstParameterIndex;
		}
		
		//for impact change
		objInputBean.impactStatus = $(this).find("input[name='inputBeanId']").attr("impactStatus") == undefined ? "" : $(this).find("input[name='inputBeanId']").attr("impactStatus");
		objInputBean.dataTypeRefer = objInputBean.dataType;
		objInputBean.arrayFlgRefer = objInputBean.arrayFlg;
		parameterInputBeans.push(objInputBean);
	});
	if (data.parameterInputBeans != undefined) {
		data.parameterInputBeans = parameterInputBeans;
	} else {		
		data["parameterInputBeans"] = parameterInputBeans;
	}
	
	var validationMessage = $.qp.businessdesign.validaionComponent.validationNavigatorComp($.qp.businessdesign.validaionComponent.DETAIL_CHECK,data,true,thiz);
	if(validationMessage != undefined && validationMessage != ""){
		$.qp.showAlertModal(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("data-original-title",remark);
		$(obj).find("span.component-name").text(label);
		$(modal).modal("hide");
		instance.repaintEverything();
	}
	saveCommonSetting($(obj));
}
function saveModalAssignScreenItem(obj){
	// Add new HungHX
	var modal = "#dialog-assign-screenitem-setting";
	var obj = $(modal).data("target");
	
	// remove
	$(obj).closest('tr').find('input[name$="screenItemId"]').remove();
	$(obj).closest('tr').find('label[name$="screenItemIdAutocomplete"]').text("");
	// Get all checkbox is select
	var $allRowSelect = $(modal).find("input[name=outputSelect]:checked");
	
	// setting hidden item on 
	$(obj).closest("td").find("input[name*=screenItemId]").remove();
	$(obj).closest("td").find("input[name*=mappingType]").remove();
	$(obj).closest("td").find("input[name*=itemName]").remove();
	if($allRowSelect!=undefined && $allRowSelect.length > 0 ){
		var name = $(obj).closest('tr').find('label[name$="screenItemIdAutocomplete"]').attr("name");
		var arrIdxLstOutputBean = name.split("[").pop().split("]").shift();
		var screenItemName = "";
		$.each($allRowSelect, function(idx) {
			var $screenItemClone = $('<input>').attr('type','hidden');
			var $mappingTypeClone = $('<input>').attr('type','hidden');
			var $itemNameClone = $('<input>').attr('type','hidden');
			var mappingType = $(this).attr("mappingType");
			var itemName = $(this).closest("tr").find("label[name=itemName]").text();
			$screenItemClone.attr('name', 'lstOutputBean['+arrIdxLstOutputBean+'].lstScreenItemMapping['+idx+'].screenItemId').val($(this).val());
			$screenItemClone.attr('mappingType', mappingType);
			$mappingTypeClone.attr('name', 'lstOutputBean['+arrIdxLstOutputBean+'].lstScreenItemMapping['+idx+'].mappingType').val(mappingType);
			$itemNameClone.attr('name', 'lstOutputBean['+arrIdxLstOutputBean+'].lstScreenItemMapping['+idx+'].itemName').val(itemName);
			
			if(idx == $allRowSelect.length-1){
				screenItemName += itemName;
				$(obj).closest("td").find("label[name*=screenItemIdAutocomplete]").text(screenItemName);
			}else{
				screenItemName += itemName + ";";
			}
			$(obj).closest("td").append($screenItemClone);
			$(obj).closest("td").append($mappingTypeClone);
			$(obj).closest("td").append($itemNameClone);
		});
	}

	$(modal).modal("hide");
}

function saveModalAssignSetting(thiz){
	var modal = BD_MODAL_NAME.ASSIGN;
	var table = "#tbl-assign-parameter";
	var instance = $(modal).data("instance");
	var obj = $(modal).data("target");
	var data = convertToJson($(modal).data("data"));
	
	var label  = "";
	if($(modal).find("input[name='label']").val() != undefined){
		label = $(modal).find("input[name='label']").val();
	}	
	if (data.label != undefined) {
		data.label = label;
	} else {		
		data["label"] = label;
	}
	
	var remark  = "";
	if($(modal).find("textarea[name='remark']").val() != undefined){
		remark = $(modal).find("textarea[name='remark']").val();
	}
	if (data.remark != undefined) {
		data.remark = remark;
	} else {		
		data["remark"] = remark;
	}
	
	var details =[];
	$(modal).find(table).find("tbody>tr").each(function(){
		var objAssign = new Object();
		objAssign.assignType = $(this).find("input[name='assignType']").val() == undefined ? "0" : $(this).find("input[name='assignType']").val();
		objAssign.dataGroup = $(this).attr("data-ar-rgroup") == undefined ? "" : $(this).attr("data-ar-rgroup");
		objAssign.dataType = $(this).find("input[name='dataType']").val() == undefined ? null : $(this).find("input[name='dataType']").val();
		
		var targetPattern = $(this).find("div.bd-content[name='target']").attr("pattern");
		if(targetPattern=="0"){
			objAssign.targetId = $(this).find("input[name='targetId']").val() == undefined ? null : $(this).find("input[name='targetId']").val();
			objAssign.targetIdAutocomplete = $(this).find("input[name='targetIdAutocomplete']").val() == undefined ? null : $(this).find("input[name='targetIdAutocomplete']").val();
			objAssign.targetScope = ($(this).find("input[name='targetId']").attr("parameterScope") == undefined || $(this).find("input[name='targetId']").attr("parameterScope") == "") ? null : $(this).find("input[name='targetId']").attr("parameterScope");
			objAssign.lstTargetIndex = [];
		}else if(targetPattern=="1"){
			objAssign.targetId = isEmptyQP($(this).find("div.bd-content[name='target']").attr("parameterId")) ? null : $(this).find("div.bd-content[name='target']").attr("parameterId");
			objAssign.targetScope = (isEmptyQP($(this).find("div.bd-content[name='target']").attr("parameterScope"))) ? null : $(this).find("div.bd-content[name='target']").attr("parameterScope");
			var lstTargetIndex =[];
			$(this).find("div.bd-content[name='target']").find("input[name='parameterIndexId']").each(function (){
				var item = new Object();
				var parameterIndexId = $(this).val() == undefined ? null : $(this).val();
				var parameterIndexIdAutocomplete = $(this).prev().val() == undefined ? null : $(this).prev().val();
				var parameterIndexType = ($(this).attr("indextype") == undefined || $(this).attr("indextype") == "") ? "3" : $(this).attr("indextype");
				var parameterId = ($(this).attr("parameterId") == undefined || $(this).attr("parameterId") == "") ? null : $(this).attr("parameterId");
				item.parameterIndexId = parameterIndexId;
				item.parameterIndexIdAutocomplete = parameterIndexIdAutocomplete;
				item.parameterIndexType = parameterIndexType;
				item.parameterId = parameterId;
				if(!isEmptyQP(parameterIndexId))
					lstTargetIndex.push(item);
			});
			objAssign.lstTargetIndex = lstTargetIndex;
		}
		
		if("0" == objAssign.assignType){
			objAssign.formulaDefinitionContent = null;
			objAssign.formulaDefinitionDetails = [];
			
			var parameterPattern = $(this).find("div.bd-content[name='parameter']").attr("pattern");
			if(parameterPattern=="0"){
				objAssign.parameterId = $(this).find("input[name='parameterId']").val() == undefined ? null : $(this).find("input[name='parameterId']").val();
				objAssign.parameterIdAutocomplete = $(this).find("input[name='parameterIdAutocomplete']").val() == undefined ? null : $(this).find("input[name='parameterIdAutocomplete']").val();
				objAssign.parameterScope = ($(this).find("input[name='parameterId']").attr("parameterScope") == undefined || $(this).find("input[name='parameterId']").attr("parameterScope") == "") ? null : $(this).find("input[name='parameterId']").attr("parameterScope");
				objAssign.lstParameterIndex = [];
			}else if(parameterPattern=="1"){
				objAssign.parameterId = isEmptyQP($(this).find("div.bd-content[name='parameter']").attr("parameterId")) ? null : $(this).find("div.bd-content[name='parameter']").attr("parameterId");
				objAssign.parameterScope = (isEmptyQP($(this).find("div.bd-content[name='parameter']").attr("parameterScope"))) ? null : $(this).find("div.bd-content[name='parameter']").attr("parameterScope");
				var lstParameterIndex =[];
				$(this).find("div.bd-content[name='parameter']").find("input[name='parameterIndexId']").each(function (){
					var item = new Object();
					var parameterIndexId = $(this).val() == undefined ? null : $(this).val();
					var parameterIndexIdAutocomplete = $(this).prev().val() == undefined ? null : $(this).prev().val();
					var parameterIndexType = ($(this).attr("indextype") == undefined || $(this).attr("indextype") == "") ? "3" : $(this).attr("indextype");
					var parameterId = ($(this).attr("parameterId") == undefined || $(this).attr("parameterId") == "") ? null : $(this).attr("parameterId");
					item.parameterIndexId = parameterIndexId;
					item.parameterIndexIdAutocomplete = parameterIndexIdAutocomplete;
					item.parameterIndexType = parameterIndexType;
					item.parameterId = parameterId;
					if(!isEmptyQP(parameterIndexId))
						lstParameterIndex.push(item);
				});
				objAssign.lstParameterIndex = lstParameterIndex;
			}
			
		}else if("1" == objAssign.assignType){
			objAssign.parameterId = null;
			objAssign.parameterIdAutocomplete = null;
			objAssign.parameterScope = null;
			
			objAssign.formulaDefinitionContent = ($(this).find("input[name='parameterIdAutocomplete']").val() == undefined || $(this).find("input[name='parameterIdAutocomplete']").val() == "" ) ? null : $(this).find("input[name='parameterIdAutocomplete']").val();
			objAssign.formulaDefinitionDetails = ($(this).find("input[name='formulaDefinitionDetails']").val() == undefined || $(this).find("input[name='formulaDefinitionDetails']").val() == "" ) ? [] : convertToJson($(this).find("input[name='formulaDefinitionDetails']").val());
		}
			
			
		details.push(objAssign);
	});
	if (data.details != undefined) {
		data.details = details;
	} else {		
		data["details"] = details;
	}
//	var validationMessage = "";
	var validationMessage =	$.qp.businessdesign.validaionComponent.validationAssignComp($.qp.businessdesign.validaionComponent.DETAIL_CHECK,data,true,thiz);
	if(validationMessage != undefined && validationMessage != ""){
		$.qp.showAlertModal(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("data-original-title",remark);
		$(obj).find("span.component-name").text(label);
		$(modal).modal("hide");
		instance.repaintEverything();
	}
	saveCommonSetting($(obj));
}
function saveModalBusinessCheckSetting(thiz){
	var modal = $(thiz).closest(BD_MODAL_NAME.BUSINESSCHECK);
	var instance = $(modal).data("instance");
	var obj = $(modal).data("target");
	var data = convertToJson($(modal).data("data"));
	var label  = "";
	if($(modal).find("input[name='label']").val() != undefined){
		label = $(modal).find("input[name='label']").val();
	}	
	if (data.label != undefined) {
		data.label = label;
	} else {		
		data["label"] = label;
	}
	
	var remark  = "";
	if($(modal).find("textarea[name='remark']").val() != undefined){
		remark = $(modal).find("textarea[name='remark']").val();
	}
	if (data.remark != undefined) {
		data.remark = remark;
	} else {		
		data["remark"] = remark;
	}
	
	var div = "#listBusinessCheck";
	var businessCheckDetails = [];
	$(div).find("div.qp-bdesign-div-content").each(function (index){
		var businessCheckType = $(this).attr("businessCheckType");
		var objDetail = new Object();
		objDetail.businessCheckType = businessCheckType;
		switch (businessCheckType) {
			case "1":
				objDetail.formulaDefinitionId = null;
				objDetail.formulaDefinitionContent = ($(this).find("label[name='formulaDefinitionContent']").text() == undefined || $(this).find("label[name='formulaDefinitionContent']").text() == "" ) ? null : $(this).find("label[name='formulaDefinitionContent']").text();
				objDetail.formulaDefinitionDetails = ($(this).find("input[name='formulaDefinitionDetails']").val() == undefined || $(this).find("input[name='formulaDefinitionDetails']").val() == "" ) ? [] : convertToJson($(this).find("input[name='formulaDefinitionDetails']").val());
				break;
			case "2":
			case "3":
				objDetail.formulaDefinitionId = null;
				objDetail.formulaDefinitionContent = null;
				var contents = [];
				
				var tblDesignIdAutocomplete = ($(this).find("input[name=tblDesignIdAutocomplete]").val() == undefined || $(this).find("input[name=tblDesignIdAutocomplete]").val().length ==0) ? null : $(this).find("input[name=tblDesignIdAutocomplete]").val();
				var tblDesignId = ($(this).find("input[name=tblDesignId]").val() == undefined || $(this).find("input[name=tblDesignId]").val().length ==0) ? null : $(this).find("input[name=tblDesignId]").val();
				if(tblDesignIdAutocomplete !=null){
					$(this).find("#tbl-content-conditions").find("tbody").find("tr").each(function (){
						var objCondition = new Object();
						var columnIdAutocomplete = ($(this).find("input[name=columnIdAutocomplete]").val() == undefined || $(this).find("input[name=columnIdAutocomplete]").val().length ==0) ? null : $(this).find("input[name=columnIdAutocomplete]").val();
						objCondition.columnIdAutocomplete = columnIdAutocomplete;
						var columnId = ($(this).find("input[name=columnId]").val() == undefined || $(this).find("input[name=columnId]").val().length ==0) ? null : $(this).find("input[name=columnId]").val();
						objCondition.columnId = columnId;					
						
//						var parameterIdAutocomplete = ($(this).find("input[name=parameterIdAutocomplete]").val() == undefined || $(this).find("input[name=parameterIdAutocomplete]").val().length ==0) ? null : $(this).find("input[name=parameterIdAutocomplete]").val();
//						objCondition.parameterIdAutocomplete = parameterIdAutocomplete;					
//						var parameterId = ($(this).find("input[name=parameterId]").val() == undefined || $(this).find("input[name=parameterId]").val().length ==0) ? null : $(this).find("input[name=parameterId]").val();
//						objCondition.parameterId = parameterId;				
//						var parameterScope = ($(this).find("input[name='parameterId']").attr("parameterScope") == undefined || $(this).find("input[name='parameterId']").attr("parameterScope").length ==0) ? null : $(this).find("input[name='parameterId']").attr("parameterScope");
//						objCondition.parameterScope = parameterScope;
						var parameterPattern = $(this).find("div.bd-content[name='parameter']").attr("pattern");
						if(parameterPattern=="0"){
							objCondition.parameterId = $(this).find("input[name='parameterId']").val() == undefined ? null : $(this).find("input[name='parameterId']").val();
							objCondition.parameterIdAutocomplete = $(this).find("input[name='parameterIdAutocomplete']").val() == undefined ? null : $(this).find("input[name='parameterIdAutocomplete']").val();
							objCondition.parameterScope = ($(this).find("input[name='parameterId']").attr("parameterScope") == undefined || $(this).find("input[name='parameterId']").attr("parameterScope") == "") ? null : $(this).find("input[name='parameterId']").attr("parameterScope");
							objCondition.lstParameterIndex = [];
						}else if(parameterPattern=="1"){
							objCondition.parameterId = isEmptyQP($(this).find("div.bd-content[name='parameter']").attr("parameterId")) ? null : $(this).find("div.bd-content[name='parameter']").attr("parameterId");
							objCondition.parameterScope = (isEmptyQP($(this).find("div.bd-content[name='parameter']").attr("parameterScope"))) ? null : $(this).find("div.bd-content[name='parameter']").attr("parameterScope");
							var lstParameterIndex =[];
							$(this).find("div.bd-content[name='parameter']").find("input[name='parameterIndexId']").each(function (){
								var item = new Object();
								var parameterIndexId = $(this).val() == undefined ? null : $(this).val();
								var parameterIndexIdAutocomplete = $(this).prev().val() == undefined ? null : $(this).prev().val();
								var parameterIndexType = ($(this).attr("indextype") == undefined || $(this).attr("indextype") == "") ? "3" : $(this).attr("indextype");
								var parameterId = ($(this).attr("parameterId") == undefined || $(this).attr("parameterId") == "") ? null : $(this).attr("parameterId");
								item.parameterIndexId = parameterIndexId;
								item.parameterIndexIdAutocomplete = parameterIndexIdAutocomplete;
								item.parameterIndexType = parameterIndexType;
								item.parameterId = parameterId;
								if(!isEmptyQP(parameterIndexId))
									lstParameterIndex.push(item);
							});
							objCondition.lstParameterIndex = lstParameterIndex;
						}
						
						objCondition.tblDesignIdAutocomplete = tblDesignIdAutocomplete;
						objCondition.tblDesignId = tblDesignId;
						
						var operatorCode = ($(this).find("select[name=operatorCode]").val() == undefined || $(this).find("select[name=operatorCode]").val().length ==0) ? null : $(this).find("select[name=operatorCode]").val();
						objCondition.operatorCode = (operatorCode == undefined) ? "" : operatorCode;
						
						var dataType = $(this).find("input[name=columnId]").attr("dataType");
						objCondition.dataType = (dataType == undefined) ? "" : dataType;
						contents.push(objCondition);
					});
					if(contents.length ==0){
						var objCondition = new Object();
						objCondition.columnIdAutocomplete = null;
						objCondition.columnId = null;
						objCondition.parameterIdAutocomplete = null;
						objCondition.parameterId = null;
						objCondition.parameterScope = null;
						objCondition.tblDesignIdAutocomplete = tblDesignIdAutocomplete;
						objCondition.tblDesignId = tblDesignId;
						objCondition.operatorCode = null;
						contents.push(objCondition);
					}
				}
				break;
			default:
				break;
		}
		
		//get message code
		var parameters = [];
		var messageCodeAutocomplete = ($(this).find("input[name=messageCodeAutocomplete]").val() == undefined) ? null : $(this).find("input[name=messageCodeAutocomplete]").val();
		var messageCode = ($(this).find("input[name=messageCode]").val() == undefined) ? null : $(this).find("input[name=messageCode]").val();
		var dataParameters = ($(this).find("input[name=messageParameter]").val() == undefined || $(this).find("input[name=messageParameter]").val().length == 0) ? "[]" : $(this).find("input[name=messageParameter]").val();
		parameters = convertToJson(dataParameters);
		objDetail.messageCodeAutocomplete = messageCodeAutocomplete;
		objDetail.messageCode = messageCode;
		objDetail.itemSequenceNo = index;
		objDetail.parameters = parameters;
		
		var existedMessageFlg  = "";
		if($(this).find("input[name='messageCodeAutocomplete']").attr("selectedvalue") != undefined){
			existedMessageFlg = $(this).find("input[name='messageCodeAutocomplete']").attr("selectedvalue");
		}
		if (objDetail.existedMessageFlg != undefined) {
			objDetail.existedMessageFlg = existedMessageFlg;
		}else{
			objDetail["existedMessageFlg"] = existedMessageFlg;
		}
		
		var abortFlg = ($(this).find("input[name=abortFlg]").prop("checked") == undefined) ? null : $(this).find("input[name=abortFlg]").prop("checked");
		objDetail.abortFlg = abortFlg;
		objDetail.contents = contents;
		businessCheckDetails.push(objDetail);
	});
	if (data.businessCheckDetails != undefined) {
		data.businessCheckDetails = businessCheckDetails;
	} else {		
		data["businessCheckDetails"] = businessCheckDetails;
	}
	
	var validationMessage = $.qp.businessdesign.validaionComponent.validationBusinessCheckComp($.qp.businessdesign.validaionComponent.DETAIL_CHECK,data,true,thiz);
//	var validationMessage = "";
	if(validationMessage != undefined && validationMessage != ""){
		$.qp.showAlertModal(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("data-original-title",remark);
		$(obj).find("span.component-name").text(label);
		$(modal).modal("hide");
		instance.repaintEverything();
	}
	saveCommonSetting($(obj));
}
function saveModalCommonSetting(thiz){
	var modal = BD_MODAL_NAME.COMMON;
	var instance = $(modal).data("instance");
	var obj = $(modal).data("target");
	var data = convertToJson($(modal).data("data"));
	var label  = "";
	if($(modal).find("input[name='label']").val() != undefined){
		label = $(modal).find("input[name='label']").val();
	}	
	if (data.label != undefined) {
		data.label = label;
	} else {		
		data["label"] = label;
	}
	
	var remark  = "";
	if($(modal).find("textarea[name='remark']").val() != undefined){
		remark = $(modal).find("textarea[name='remark']").val();
	}
	if (data.remark != undefined) {
		data.remark = remark;
	} else {		
		data["remark"] = remark;
	}
	
	var businessLogicId  = "";
	if($(modal).find("input[name='businessLogicId']").val() != undefined){
		businessLogicId = $(modal).find("input[name='businessLogicId']").val();
	}
	if (data.businessLogicId != undefined) {
		data.businessLogicId = businessLogicId;
	} else {		
		data["businessLogicId"] = businessLogicId;
	}
	
	if (data.businessLogicIdRefer != undefined) {
		data.businessLogicIdRefer = businessLogicId;
	} else {		
		data["businessLogicIdRefer"] = businessLogicId;
	}
	
	var businessLogicIdAutocomplete  = "";
	if($(modal).find("input[name='businessLogicIdAutocomplete']").val() != undefined){
		businessLogicIdAutocomplete = $(modal).find("input[name='businessLogicIdAutocomplete']").val();
	}
	if (data.businessLogicIdAutocomplete != undefined) {
		data.businessLogicIdAutocomplete = businessLogicIdAutocomplete;
	} else {		
		data["businessLogicIdAutocomplete"] = businessLogicIdAutocomplete;
	}
	
	var businessLogicCode  = "";
	if($(modal).find("label[name='businessLogicCode']").text() != undefined){
		businessLogicCode = $(modal).find("label[name='businessLogicCode']").text();
	}
	if (data.businessLogicCode != undefined) {
		data.businessLogicCode = businessLogicCode;
	} else {		
		data["businessLogicCode"] = businessLogicCode;
	}

	var parameterInputBeans = new Array();
	$(modal).find("#tbl-common-inputbean").find("tbody>tr").each(function(){
		var objInputBean = new Object();
		objInputBean.inputBeanId = $(this).find("input[name='inputBeanId']").val() == undefined ? null : $(this).find("input[name='inputBeanId']").val();
		objInputBean.inputBeanName = $(this).find("input[name='inputBeanName']").val() == undefined ? null : $(this).find("input[name='inputBeanName']").val() ;
		objInputBean.inputBeanCode = $(this).find("label[name='inputBeanCode']").text() == undefined ? null : $(this).find("label[name='inputBeanCode']").text();
		objInputBean.dataType = $(this).find("input[name='dataType']").val() == undefined ? null : $(this).find("input[name='dataType']").val();
		objInputBean.arrayFlg = $(this).find("input[name='arrayFlg']").val() == undefined ? null : eval($(this).find("input[name='arrayFlg']").val());
		
		var parameterPattern = $(this).find("div.bd-content[name='parameter']").attr("pattern");
		if(parameterPattern=="0"){
			objInputBean.parameterId = $(this).find("input[name='parameterId']").val() == undefined ? null : $(this).find("input[name='parameterId']").val();
			objInputBean.parameterIdAutocomplete = $(this).find("input[name='parameterIdAutocomplete']").val() == undefined ? null : $(this).find("input[name='parameterIdAutocomplete']").val();
			objInputBean.parameterScope = ($(this).find("input[name='parameterId']").attr("parameterScope") == undefined || $(this).find("input[name='parameterId']").attr("parameterScope") == "") ? null : $(this).find("input[name='parameterId']").attr("parameterScope");
			objInputBean.lstParameterIndex = [];
		}else if(parameterPattern=="1"){
			objInputBean.parameterId = isEmptyQP($(this).find("div.bd-content[name='parameter']").attr("parameterId")) ? null : $(this).find("div.bd-content[name='parameter']").attr("parameterId");
			objInputBean.parameterScope = (isEmptyQP($(this).find("div.bd-content[name='parameter']").attr("parameterScope"))) ? null : $(this).find("div.bd-content[name='parameter']").attr("parameterScope");
			var lstParameterIndex =[];
			$(this).find("div.bd-content[name='parameter']").find("input[name='parameterIndexId']").each(function (){
				var item = new Object();
				var parameterIndexId = $(this).val() == undefined ? null : $(this).val();
				var parameterIndexIdAutocomplete = $(this).prev().val() == undefined ? null : $(this).prev().val();
				var parameterIndexType = ($(this).attr("indextype") == undefined || $(this).attr("indextype") == "") ? "3" : $(this).attr("indextype");
				var parameterId = ($(this).attr("parameterId") == undefined || $(this).attr("parameterId") == "") ? null : $(this).attr("parameterId");
				item.parameterIndexId = parameterIndexId;
				item.parameterIndexIdAutocomplete = parameterIndexIdAutocomplete;
				item.parameterIndexType = parameterIndexType;
				item.parameterId = parameterId;
				if(!isEmptyQP(parameterIndexId))
					lstParameterIndex.push(item);
			});
			objInputBean.lstParameterIndex = lstParameterIndex;
		}
		//for impact change
		objInputBean.impactStatus = $(this).find("input[name='inputBeanId']").attr("impactStatus") == undefined ? "" : $(this).find("input[name='inputBeanId']").attr("impactStatus");
		objInputBean.dataTypeRefer = objInputBean.dataType;
		objInputBean.arrayFlgRefer = objInputBean.arrayFlg;
		parameterInputBeans.push(objInputBean);
	});
	if (data.parameterInputBeans != undefined) {
		data.parameterInputBeans = parameterInputBeans;
	} else {		
		data["parameterInputBeans"] = parameterInputBeans;
	}
	
	var parameterOutputBeans = new Array();
	$(modal).find("#tbl-common-outputbean").find("tbody>tr").each(function(){
		var objOutputbean= new Object();
		objOutputbean.outputBeanId = $(this).find("input[name='outputBeanId']").val() == undefined ? null : $(this).find("input[name='outputBeanId']").val();
		objOutputbean.outputBeanName = $(this).find("label[name='outputBeanName']").text() == undefined ? null :  $(this).find("label[name='outputBeanName']").text();
		objOutputbean.outputBeanCode = $(this).find("label[name='outputBeanCode']").text() == undefined ? null : $(this).find("label[name='outputBeanCode']").text();
		objOutputbean.dataType = $(this).find("input[name='dataType']").val() == undefined ? null : $(this).find("input[name='dataType']").val();
		objOutputbean.arrayFlg = $(this).find("input[name='arrayFlg']").val() == undefined ? null : $(this).find("input[name='arrayFlg']").val();
		
		var targetPattern = $(this).find("div.bd-content[name='target']").attr("pattern");
		if(targetPattern=="0"){
			objOutputbean.targetId = $(this).find("input[name='targetId']").val() == undefined ? null : $(this).find("input[name='targetId']").val();
			objOutputbean.targetIdAutocomplete = $(this).find("input[name='targetIdAutocomplete']").val() == undefined ? null : $(this).find("input[name='targetIdAutocomplete']").val();
			objOutputbean.targetScope = ($(this).find("input[name='targetId']").attr("parameterScope") == undefined || $(this).find("input[name='targetId']").attr("parameterScope") == "") ? null : $(this).find("input[name='targetId']").attr("parameterScope");
			objOutputbean.lstTargetIndex = [];
		}else if(targetPattern=="1"){
			objOutputbean.targetId = isEmptyQP($(this).find("div.bd-content[name='target']").attr("parameterId")) ? null : $(this).find("div.bd-content[name='target']").attr("parameterId");
			objOutputbean.targetScope = (isEmptyQP($(this).find("div.bd-content[name='target']").attr("parameterScope"))) ? null : $(this).find("div.bd-content[name='target']").attr("parameterScope");
			var lstTargetIndex =[];
			$(this).find("div.bd-content[name='target']").find("input[name='parameterIndexId']").each(function (){
				var item = new Object();
				var parameterIndexId = $(this).val() == undefined ? null : $(this).val();
				var parameterIndexIdAutocomplete = $(this).prev().val() == undefined ? null : $(this).prev().val();
				var parameterIndexType = ($(this).attr("indextype") == undefined || $(this).attr("indextype") == "") ? "3" : $(this).attr("indextype");
				var parameterId = ($(this).attr("parameterId") == undefined || $(this).attr("parameterId") == "") ? null : $(this).attr("parameterId");
				item.parameterIndexId = parameterIndexId;
				item.parameterIndexIdAutocomplete = parameterIndexIdAutocomplete;
				item.parameterIndexType = parameterIndexType;
				item.parameterId = parameterId;
				if(!isEmptyQP(parameterIndexId))
					lstTargetIndex.push(item);
			});
			objOutputbean.lstTargetIndex = lstTargetIndex;
		}
		//for impact change
		objOutputbean.impactStatus = $(this).find("input[name='outputBeanId']").attr("impactStatus") == undefined ? "" : $(this).find("input[name='outputBeanId']").attr("impactStatus");
		objOutputbean.dataTypeRefer = objOutputbean.dataType;
		objOutputbean.arrayFlgRefer = objOutputbean.arrayFlg;
		parameterOutputBeans.push(objOutputbean);
	});
	if (data.parameterOutputBeans != undefined) {
		data.parameterOutputBeans = parameterOutputBeans;
	} else {		
		data["parameterOutputBeans"] = parameterOutputBeans;
	}
	
	var validationMessage = $.qp.businessdesign.validaionComponent.validationCommonComp($.qp.businessdesign.validaionComponent.DETAIL_CHECK,data,true,thiz);
//	var validationMessage = "";
	if(validationMessage != undefined && validationMessage != ""){
		$.qp.showAlertModal(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("data-original-title",remark);
		$(obj).find("span.component-name").text(label);
		$(modal).modal("hide");
		instance.repaintEverything();
	}
	saveCommonSetting($(obj));
}
function saveModalDecisionSetting(thiz){
	var modal = BD_MODAL_NAME.DECISION;
	var instance = $(modal).data("instance");
	var obj = $(modal).data("target");
	var data = convertToJson($(modal).data("data"));
	var label  = "";
	if($(modal).find("input[name='label']").val() != undefined){
		label = $(modal).find("input[name='label']").val();
	}	
	if (data.label != undefined) {
		data.label = label;
	} else {		
		data["label"] = label;
	}
	
	var remark  = "";
	if($(modal).find("textarea[name='remark']").val() != undefined){
		remark = $(modal).find("textarea[name='remark']").val();
	}
	if (data.remark != undefined) {
		data.remark = remark;
	} else {		
		data["remark"] = remark;
	}
	
	var moduleId = null;
	var moduleIdAutocomplete = null;
	if($(modal).find("input[name='moduleId']").val() != undefined && $(modal).find("input[name='moduleId']").val() != null && $(modal).find("input[name='moduleId']").val().length >0){
		moduleId = $(modal).find("input[name='moduleId']").val();
		moduleIdAutocomplete = $(modal).find("input[name='moduleIdAutocomplete']").val();
	}
	if (data.moduleId != undefined) {
		data.moduleId = moduleId;
	} else {		
		data["moduleId"] = moduleId;
	}
	
	if (data.moduleIdAutocomplete != undefined) {
		data.moduleIdAutocomplete = moduleIdAutocomplete;
	} else {		
		data["moduleIdAutocomplete"] = moduleIdAutocomplete;
	}
	
	var decisionTableId = null;
	var decisionTableIdAutocomplete = null;
	var decisionTableCode = null;
	
	if($(modal).find("input[name='decisionTableId']").val() != undefined && $(modal).find("input[name='decisionTableId']").val() != null && $(modal).find("input[name='decisionTableId']").val().length >0){
		decisionTableId = $(modal).find("input[name='decisionTableId']").val();
		decisionTableIdAutocomplete = $(modal).find("input[name='decisionTableIdAutocomplete']").val();
		decisionTableCode = $(modal).find("label[name='decisionTableCode']").text();
	}
	
	if (data.decisionTableId != undefined) {
		data.decisionTableId = decisionTableId;
	} else {		
		data["decisionTableId"] = decisionTableId;
	}
	
	if (data.decisionTableIdRefer != undefined) {
		data.decisionTableIdRefer = decisionTableId;
	} else {		
		data["decisionTableIdRefer"] = decisionTableId;
	}
	
	if (data.decisionTableIdAutocomplete != undefined) {
		data.decisionTableIdAutocomplete = decisionTableIdAutocomplete;
	} else {		
		data["decisionTableIdAutocomplete"] = decisionTableIdAutocomplete;
	}
	if (data.decisionTableCode != undefined) {
		data.decisionTableCode = decisionTableCode;
	} else {		
		data["decisionTableCode"] = decisionTableCode;
	}
	
	var parameterInputBeans = new Array();
	$(modal).find("#tbl-decision-inputbean").find("tbody>tr").each(function(){
		var objInputBean = new Object();
		objInputBean.decisionInputBeanId = $(this).find("input[name='decisionInputBeanId']").val() == undefined ? null : $(this).find("input[name='decisionInputBeanId']").val();
		objInputBean.decisionInputBeanName = $(this).find("input[name='decisionInputBeanName']").val() == undefined ? null : $(this).find("input[name='decisionInputBeanName']").val() ;
		objInputBean.decisionInputBeanCode = $(this).find("label[name='decisionInputBeanCode']").text() == undefined ? null : $(this).find("label[name='decisionInputBeanCode']").text();
		objInputBean.dataType = $(this).find("input[name='dataType']").val() == undefined ? null : $(this).find("input[name='dataType']").val();
		
		var parameterPattern = $(this).find("div.bd-content[name='parameter']").attr("pattern");
		if(parameterPattern=="0"){
			objInputBean.parameterId = $(this).find("input[name='parameterId']").val() == undefined ? null : $(this).find("input[name='parameterId']").val();
			objInputBean.parameterIdAutocomplete = $(this).find("input[name='parameterIdAutocomplete']").val() == undefined ? null : $(this).find("input[name='parameterIdAutocomplete']").val();
			objInputBean.parameterScope = ($(this).find("input[name='parameterId']").attr("parameterScope") == undefined || $(this).find("input[name='parameterId']").attr("parameterScope") == "") ? null : $(this).find("input[name='parameterId']").attr("parameterScope");
			objInputBean.lstParameterIndex = [];
		}else if(parameterPattern=="1"){
			objInputBean.parameterId = isEmptyQP($(this).find("div.bd-content[name='parameter']").attr("parameterId")) ? null : $(this).find("div.bd-content[name='parameter']").attr("parameterId");
			objInputBean.parameterScope = (isEmptyQP($(this).find("div.bd-content[name='parameter']").attr("parameterScope"))) ? null : $(this).find("div.bd-content[name='parameter']").attr("parameterScope");
			var lstParameterIndex =[];
			$(this).find("div.bd-content[name='parameter']").find("input[name='parameterIndexId']").each(function (){
				var item = new Object();
				var parameterIndexId = $(this).val() == undefined ? null : $(this).val();
				var parameterIndexIdAutocomplete = $(this).prev().val() == undefined ? null : $(this).prev().val();
				var parameterIndexType = ($(this).attr("indextype") == undefined || $(this).attr("indextype") == "") ? "3" : $(this).attr("indextype");
				var parameterId = ($(this).attr("parameterId") == undefined || $(this).attr("parameterId") == "") ? null : $(this).attr("parameterId");
				item.parameterIndexId = parameterIndexId;
				item.parameterIndexIdAutocomplete = parameterIndexIdAutocomplete;
				item.parameterIndexType = parameterIndexType;
				item.parameterId = parameterId;
				if(!isEmptyQP(parameterIndexId))
					lstParameterIndex.push(item);
			});
			objInputBean.lstParameterIndex = lstParameterIndex;
		}
		
		//for impact change
		objInputBean.impactStatus = $(this).find("input[name='decisionInputBeanId']").attr("impactStatus") == undefined ? "" : $(this).find("input[name='decisionInputBeanId']").attr("impactStatus");
		objInputBean.dataTypeRefer = objInputBean.dataType;
		parameterInputBeans.push(objInputBean);
	});
	if (data.parameterInputBeans != undefined) {
		data.parameterInputBeans = parameterInputBeans;
	} else {		
		data["parameterInputBeans"] = parameterInputBeans;
	}
	
	var parameterOutputBeans = new Array();
	$(modal).find("#tbl-decision-outputbean").find("tbody>tr").each(function(){
		var objOutputbean= new Object();
		objOutputbean.decisionOutputBeanId = $(this).find("input[name='decisionOutputBeanId']").val() == undefined ? null : $(this).find("input[name='decisionOutputBeanId']").val();
		objOutputbean.decisionOutputBeanName = $(this).find("input[name='decisionOutputBeanName']").val() == undefined ? null :  $(this).find("input[name='decisionOutputBeanName']").val();
		objOutputbean.decisionOutputBeanCode = $(this).find("label[name='decisionOutputBeanCode']").text() == undefined ? null : $(this).find("label[name='decisionOutputBeanCode']").text();
		objOutputbean.dataType = $(this).find("input[name='dataType']").val() == undefined ? null : $(this).find("input[name='dataType']").val();
		
		var targetPattern = $(this).find("div.bd-content[name='target']").attr("pattern");
		if(targetPattern=="0"){
			objOutputbean.targetId = $(this).find("input[name='targetId']").val() == undefined ? null : $(this).find("input[name='targetId']").val();
			objOutputbean.targetIdAutocomplete = $(this).find("input[name='targetIdAutocomplete']").val() == undefined ? null : $(this).find("input[name='targetIdAutocomplete']").val();
			objOutputbean.targetScope = ($(this).find("input[name='targetId']").attr("parameterScope") == undefined || $(this).find("input[name='targetId']").attr("parameterScope") == "") ? null : $(this).find("input[name='targetId']").attr("parameterScope");
			objOutputbean.lstTargetIndex = [];
		}else if(targetPattern=="1"){
			objOutputbean.targetId = isEmptyQP($(this).find("div.bd-content[name='target']").attr("parameterId")) ? null : $(this).find("div.bd-content[name='target']").attr("parameterId");
			objOutputbean.targetScope = (isEmptyQP($(this).find("div.bd-content[name='target']").attr("parameterScope"))) ? null : $(this).find("div.bd-content[name='target']").attr("parameterScope");
			var lstTargetIndex =[];
			$(this).find("div.bd-content[name='target']").find("input[name='parameterIndexId']").each(function (){
				var item = new Object();
				var parameterIndexId = $(this).val() == undefined ? null : $(this).val();
				var parameterIndexIdAutocomplete = $(this).prev().val() == undefined ? null : $(this).prev().val();
				var parameterIndexType = ($(this).attr("indextype") == undefined || $(this).attr("indextype") == "") ? "3" : $(this).attr("indextype");
				var parameterId = ($(this).attr("parameterId") == undefined || $(this).attr("parameterId") == "") ? null : $(this).attr("parameterId");
				item.parameterIndexId = parameterIndexId;
				item.parameterIndexIdAutocomplete = parameterIndexIdAutocomplete;
				item.parameterIndexType = parameterIndexType;
				item.parameterId = parameterId;
				if(!isEmptyQP(parameterIndexId))
					lstTargetIndex.push(item);
			});
			objOutputbean.lstTargetIndex = lstTargetIndex;
		}
		
		//for impact change
		objOutputbean.impactStatus = $(this).find("input[name='decisionOutputBeanId']").attr("impactStatus") == undefined ? "" : $(this).find("input[name='decisionOutputBeanId']").attr("impactStatus");
		objOutputbean.dataTypeRefer = objOutputbean.dataType;
		parameterOutputBeans.push(objOutputbean);
	});
	if (data.parameterOutputBeans != undefined) {
		data.parameterOutputBeans = parameterOutputBeans;
	} else {		
		data["parameterOutputBeans"] = parameterOutputBeans;
	}
	
	var validationMessage = $.qp.businessdesign.validaionComponent.validationDecisionComp($.qp.businessdesign.validaionComponent.DETAIL_CHECK,data,true,thiz);
//	var validationMessage = "";
	if(validationMessage != undefined && validationMessage != ""){
		$.qp.showAlertModal(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("data-original-title",remark);
		$(obj).find("span.component-name").text(label);
		$(modal).modal("hide");
		instance.repaintEverything();
	}
	saveCommonSetting($(obj));
}
function saveModalExecutionSetting(thiz){
	var modal = BD_MODAL_NAME.EXECUTION;
	var instance = $(modal).data("instance");
	var obj = $(modal).data("target");
	var data = convertToJson($(modal).data("data"));
	var tableInputBean = "#tbl-execution-inputbean";
	var tableOutputBean = "#tbl-execution-outputbean";
	var label  = "";
	if($(modal).find("input[name='label']").val() != undefined){
		label = $(modal).find("input[name='label']").val();
	}	
	if (data.label != undefined) {
		data.label = label;
	} else {		
		data["label"] = label;
	}
	
	var remark  = "";
	if($(modal).find("textarea[name='remark']").val() != undefined){
		remark = $(modal).find("textarea[name='remark']").val();
	}
	if (data.remark != undefined) {
		data.remark = remark;
	} else {		
		data["remark"] = remark;
	}
	
	var moduleId = null;
	var moduleIdAutocomplete = null;
	if($(modal).find("input[name='moduleId']").val() != undefined && $(modal).find("input[name='moduleId']").val() != null && $(modal).find("input[name='moduleId']").val().length >0){
		moduleId = $(modal).find("input[name='moduleId']").val();
		moduleIdAutocomplete = $(modal).find("input[name='moduleIdAutocomplete']").val();
	}
	if (data.moduleId != undefined) {
		data.moduleId = moduleId;
	} else {		
		data["moduleId"] = moduleId;
	}
	
	if (data.moduleIdAutocomplete != undefined) {
		data.moduleIdAutocomplete = moduleIdAutocomplete;
	} else {		
		data["moduleIdAutocomplete"] = moduleIdAutocomplete;
	}
	
	var sqlDesignId = null;
	var sqlDesignIdAutocomplete = null;
	var sqlDesignCode = null;
	
	if($(modal).find("input[name='sqlDesignId']").val() != undefined && $(modal).find("input[name='sqlDesignId']").val() != null && $(modal).find("input[name='sqlDesignId']").val().length >0){
		sqlDesignId = $(modal).find("input[name='sqlDesignId']").val();
		sqlDesignIdAutocomplete = $(modal).find("input[name='sqlDesignIdAutocomplete']").val();
		sqlDesignCode = $(modal).find("label[name='sqlDesignCode']").text();
	}
	if (data.sqlDesignId != undefined) {
		data.sqlDesignId = sqlDesignId;
	} else {		
		data["sqlDesignId"] = sqlDesignId;
	}
	
	if (data.sqlDesignIdRefer != undefined) {
		data.sqlDesignIdRefer = sqlDesignId;
	} else {		
		data["sqlDesignIdRefer"] = sqlDesignId;
	}
	
	if (data.sqlDesignIdAutocomplete != undefined) {
		data.sqlDesignIdAutocomplete = sqlDesignIdAutocomplete;
	} else {		
		data["sqlDesignIdAutocomplete"] = sqlDesignIdAutocomplete;
	}
	if (data.sqlDesignCode != undefined) {
		data.sqlDesignCode = sqlDesignCode;
	} else {		
		data["sqlDesignCode"] = sqlDesignCode;
	}
	
	var concurrencyFlg = ($(modal).find("input[type=checkbox][name='concurrencyFlg']").prop("checked") == undefined) ? false : $(modal).find("input[type=checkbox][name='concurrencyFlg']").prop("checked");
	if (data.concurrencyFlg != undefined) {
		data.concurrencyFlg = concurrencyFlg;
	} else {		
		data["concurrencyFlg"] = concurrencyFlg;
	}
	var parameterInputBeans = new Array();
	$(modal).find(tableInputBean).find("tbody>tr").each(function(){
		var objInputBean = new Object();
		objInputBean.itemSequenceNo =  $(this).find("td.tableIndexNotAuto").text();
		objInputBean.sqlDesignInputId = $(this).find("input[name='sqlDesignInputId']").val() == undefined ? null : $(this).find("input[name='sqlDesignInputId']").val();
		objInputBean.sqlDesignInputName = $(this).find("label[name='sqlDesignInputName']").text() == undefined ? null : $(this).find("label[name='sqlDesignInputName']").text() ;
		objInputBean.sqlDesignInputCode = $(this).find("label[name='sqlDesignInputCode']").text() == undefined ? null : $(this).find("label[name='sqlDesignInputCode']").text();
		objInputBean.dataType = $(this).find("input[name='dataType']").val() == undefined ? null : $(this).find("input[name='dataType']").val();
		var arrayFlg = $(this).find("input[name='arrayFlg']").val() == undefined ? null : $(this).find("input[name='arrayFlg']").val();
		objInputBean.arrayFlg = arrayFlg == "1" ? true : false ;
		
		var parameterPattern = $(this).find("div.bd-content[name='parameter']").attr("pattern");
		if(parameterPattern=="0"){
			objInputBean.parameterId = $(this).find("input[name='parameterId']").val() == undefined ? null : $(this).find("input[name='parameterId']").val();
			objInputBean.parameterIdAutocomplete = $(this).find("input[name='parameterIdAutocomplete']").val() == undefined ? null : $(this).find("input[name='parameterIdAutocomplete']").val();
			objInputBean.parameterScope = ($(this).find("input[name='parameterId']").attr("parameterScope") == undefined || $(this).find("input[name='parameterId']").attr("parameterScope") == "") ? null : $(this).find("input[name='parameterId']").attr("parameterScope");
			objInputBean.lstParameterIndex = [];
		}else if(parameterPattern=="1"){
			objInputBean.parameterId = isEmptyQP($(this).find("div.bd-content[name='parameter']").attr("parameterId")) ? null : $(this).find("div.bd-content[name='parameter']").attr("parameterId");
			objInputBean.parameterScope = (isEmptyQP($(this).find("div.bd-content[name='parameter']").attr("parameterScope"))) ? null : $(this).find("div.bd-content[name='parameter']").attr("parameterScope");
			var lstParameterIndex =[];
			$(this).find("div.bd-content[name='parameter']").find("input[name='parameterIndexId']").each(function (){
				var item = new Object();
				var parameterIndexId = $(this).val() == undefined ? null : $(this).val();
				var parameterIndexIdAutocomplete = $(this).prev().val() == undefined ? null : $(this).prev().val();
				var parameterIndexType = ($(this).attr("indextype") == undefined || $(this).attr("indextype") == "") ? "3" : $(this).attr("indextype");
				var parameterId = ($(this).attr("parameterId") == undefined || $(this).attr("parameterId") == "") ? null : $(this).attr("parameterId");
				item.parameterIndexId = parameterIndexId;
				item.parameterIndexIdAutocomplete = parameterIndexIdAutocomplete;
				item.parameterIndexType = parameterIndexType;
				item.parameterId = parameterId;
				if(!isEmptyQP(parameterIndexId))
					lstParameterIndex.push(item);
			});
			objInputBean.lstParameterIndex = lstParameterIndex;
		}
		
		objInputBean.impactStatus = $(this).find("input[name='sqlDesignInputId']").attr("impactStatus") == undefined ? "" : $(this).find("input[name='sqlDesignInputId']").attr("impactStatus");
		objInputBean.dataTypeRefer = objInputBean.dataType;
		objInputBean.arrayFlgRefer = objInputBean.arrayFlg;
		parameterInputBeans.push(objInputBean);
	});
	if (data.parameterInputBeans != undefined) {
		data.parameterInputBeans = parameterInputBeans;
	} else {		
		data["parameterInputBeans"] = parameterInputBeans;
	}
	
	var parameterOutputBeans = new Array();
	$(modal).find(tableOutputBean).find("tbody>tr").each(function(){
		var objOutputbean= new Object();
		objOutputbean.itemSequenceNo =  $(this).find("td.tableIndexNotAuto").text();
		objOutputbean.sqlDesignOutputId = $(this).find("input[name='sqlDesignOutputId']").val() == undefined ? null : $(this).find("input[name='sqlDesignOutputId']").val();
		objOutputbean.sqlDesignOutputName = $(this).find("label[name='sqlDesignOutputName']").text() == undefined ? null :  $(this).find("label[name='sqlDesignOutputName']").text();
		objOutputbean.sqlDesignOutputCode = $(this).find("label[name='sqlDesignOutputCode']").text() == undefined ? null : $(this).find("label[name='sqlDesignOutputCode']").text();
		objOutputbean.dataType = $(this).find("input[name='dataType']").val() == undefined ? null : $(this).find("input[name='dataType']").val();
		var arrayFlg = $(this).find("input[name='arrayFlg']").val() == undefined ? null : $(this).find("input[name='arrayFlg']").val();
		objOutputbean.arrayFlg = arrayFlg == "1" ? true : false ;
		
		var targetPattern = $(this).find("div.bd-content[name='target']").attr("pattern");
		if(targetPattern=="0"){
			objOutputbean.targetId = $(this).find("input[name='targetId']").val() == undefined ? null : $(this).find("input[name='targetId']").val();
			objOutputbean.targetIdAutocomplete = $(this).find("input[name='targetIdAutocomplete']").val() == undefined ? null : $(this).find("input[name='targetIdAutocomplete']").val();
			objOutputbean.targetScope = ($(this).find("input[name='targetId']").attr("parameterScope") == undefined || $(this).find("input[name='targetId']").attr("parameterScope") == "") ? null : $(this).find("input[name='targetId']").attr("parameterScope");
			objOutputbean.lstTargetIndex = [];
		}else if(targetPattern=="1"){
			objOutputbean.targetId = isEmptyQP($(this).find("div.bd-content[name='target']").attr("parameterId")) ? null : $(this).find("div.bd-content[name='target']").attr("parameterId");
			objOutputbean.targetScope = (isEmptyQP($(this).find("div.bd-content[name='target']").attr("parameterScope"))) ? null : $(this).find("div.bd-content[name='target']").attr("parameterScope");
			var lstTargetIndex =[];
			$(this).find("div.bd-content[name='target']").find("input[name='parameterIndexId']").each(function (){
				var item = new Object();
				var parameterIndexId = $(this).val() == undefined ? null : $(this).val();
				var parameterIndexIdAutocomplete = $(this).prev().val() == undefined ? null : $(this).prev().val();
				var parameterIndexType = ($(this).attr("indextype") == undefined || $(this).attr("indextype") == "") ? "3" : $(this).attr("indextype");
				var parameterId = ($(this).attr("parameterId") == undefined || $(this).attr("parameterId") == "") ? null : $(this).attr("parameterId");
				item.parameterIndexId = parameterIndexId;
				item.parameterIndexIdAutocomplete = parameterIndexIdAutocomplete;
				item.parameterIndexType = parameterIndexType;
				item.parameterId = parameterId;
				if(!isEmptyQP(parameterIndexId))
					lstTargetIndex.push(item);
			});
			objOutputbean.lstTargetIndex = lstTargetIndex;
		}
		
		objOutputbean.impactStatus = $(this).find("input[name='sqlDesignOutputId']").attr("impactStatus") == undefined ? "" : $(this).find("input[name='sqlDesignOutputId']").attr("impactStatus");
		objOutputbean.dataTypeRefer = objOutputbean.dataType;
		objOutputbean.arrayFlgRefer = objOutputbean.arrayFlg;
		parameterOutputBeans.push(objOutputbean);
	});
	if (data.parameterOutputBeans != undefined) {
		data.parameterOutputBeans = parameterOutputBeans;
	} else {		
		data["parameterOutputBeans"] = parameterOutputBeans;
	}
	
	var validationMessage = $.qp.businessdesign.validaionComponent.validationExecutionComp($.qp.businessdesign.validaionComponent.DETAIL_CHECK,data,true,thiz);
//	var validationMessage = "";
	if(validationMessage != undefined && validationMessage != ""){
		$.qp.showAlertModal(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("data-original-title",remark);
		$(obj).find("span.component-name").text(label);
		$(modal).modal("hide");
		instance.repaintEverything();
	}
	saveCommonSetting($(obj));
}
function saveModalLoop(thiz){
	var modal = BD_MODAL_NAME.LOOP;
	var instance = $(modal).data("instance");
	var obj = $(modal).data("target");
	var data = convertToJson($(modal).data("data"));
	
	if($(modal).find("input[name='label']").val() != undefined){
		label = $(modal).find("input[name='label']").val();
	}	
	if (data.label != undefined) {
		data.label = label;
	} else {		
		data["label"] = label;
	}
	
	var remark  = "";
	if($(modal).find("textarea[name='remark']").val() != undefined){
		remark = $(modal).find("textarea[name='remark']").val();
	}
	if (data.remark != undefined) {
		data.remark = remark;
	} else {		
		data["remark"] = remark;
	}
	
	var loopType = null;
	var parameterId = null;
	var parameterIdAutocomplete = null;
	var parameterScope = null;
	var fromValue = null;
//	var fromType = null;
	var toValue = null;
//	var toType =  null;
	var index = null;
	/* Update by HungHX */
	var loopStepType = null;	
	var loopStepValue = null;
	
	var formulaDefinitionId =  null;
	var formulaDefinitionContent =  null;
	var formulaDefinitionDetails = [];
	
	if($(modal).find("select[name='loopType']").val() != undefined){
		loopType = parseInt($(modal).find("select[name='loopType']").val());
		if(loopType == 0){
			// Case : foreach
			parameterId = ($(modal).find("input[name='parameterId']").val() == undefined || $(modal).find("input[name='parameterId']").val() == "" ) ? null : $(modal).find("input[name='parameterId']").val();
			if(parameterId != null){
				parameterIdAutocomplete = ($(modal).find("input[name='parameterIdAutocomplete']").val() == undefined  || $(modal).find("input[name='parameterIdAutocomplete']").val() == "" )? null : $(modal).find("input[name='parameterIdAutocomplete']").val();
				parameterScope = ($(modal).find("input[name='parameterId']").attr("parameterScope") == undefined || $(modal).find("input[name='parameterId']").attr("parameterScope") == "") ? null : $(modal).find("input[name='parameterId']").attr("parameterScope");
			}
			
//			if($(modal).find("input[name='fromScope']").prop("checked")){
//				fromScope = 1;
//			}else{
//				fromScope = 0;
//				fromValue = ($(modal).find("input[name='fromValue']").val() == undefined || $(modal).find("input[name='fromValue']").val() == "" ) ? null : $(modal).find("input[name='fromValue']").val();
//			}
//			
//			if($(modal).find("input[name='toScope']").prop("checked")){
//				toScope = 1;
//			}else{
//				toScope = 0;
//				toValue = ($(modal).find("input[name='toValue']").val() == undefined || $(modal).find("input[name='toValue']").val() == "" ) ? null : $(modal).find("input[name='toValue']").val();
//			}
			if($(modal).find("input[name='fromType']").prop("checked")){
				data.fromScope = -1;
			} else {
				var trBDContentIndex = $(modal).find("div.bd-content[id='from']");
				if (trBDContentIndex.closest("td").is(":visible")) {
					var fromPattern = $(trBDContentIndex).attr("pattern");
					if (fromPattern=="0"){
						data.fromValue = $(modal).find("input[name='fromValue']").val() == undefined ? null : $(modal).find("input[name='fromValue']").val();
						data.fromValueAutocomplete = $(modal).find("input[name='fromValueAutocomplete']").val() == undefined ? null : $(modal).find("input[name='fromValueAutocomplete']").val();
						data.fromScope = ($(modal).find("input[name='fromValue']").attr("parameterScope") == undefined || $(modal).find("input[name='fromValue']").attr("parameterScope") == "") ? null : $(modal).find("input[name='fromValue']").attr("parameterScope");
						data.lstFromIndex = [];
					} else if(fromPattern=="1"){
						data.fromValue = isEmptyQP($(trBDContentIndex).attr("parameterId")) ? null : $(trBDContentIndex).attr("parameterId");
						data.fromScope = (isEmptyQP($(trBDContentIndex).attr("parameterScope"))) ? null : $(trBDContentIndex).attr("parameterScope");
						var lstIndex =[];
						$(trBDContentIndex).find("input[name='parameterIndexId']").each(function (){
							var item = new Object();
							var parameterIndexId = $(this).val() == undefined ? null : $(this).val();
							var parameterIndexIdAutocomplete = $(this).prev().val() == undefined ? null : $(this).prev().val();
							var parameterIndexType = ($(this).attr("indextype") == undefined || $(this).attr("indextype") == "") ? "3" : $(this).attr("indextype");
							var parameterId = ($(this).attr("parameterId") == undefined || $(this).attr("parameterId") == "") ? null : $(this).attr("parameterId");
							item.parameterIndexId = parameterIndexId;
							item.parameterIndexIdAutocomplete = parameterIndexIdAutocomplete;
							item.parameterIndexType = parameterIndexType;
							item.parameterId = parameterId;
							if(!isEmptyQP(parameterIndexId))
								lstIndex.push(item);
						});
						data.lstFromIndex = lstIndex;
					}
				} else {
					data.fromValue = null;
					data.fromValueAutocomplete = null;
					data.fromScope = null;
					data.lstFromIndex = [];
				}
			}
		
			if($(modal).find("input[name='toType']").prop("checked")){
				data.toScope = -1;
			} else {
				var trBDContentIndex = $(modal).find("div.bd-content[id='to']");
				if (trBDContentIndex.closest("td").is(":visible")) {
					var toPattern = $(trBDContentIndex).attr("pattern");
					if (toPattern=="0"){
						data.toValue = $(modal).find("input[name='toValue']").val() == undefined ? null : $(modal).find("input[name='toValue']").val();
						data.toValueAutocomplete = $(modal).find("input[name='toValueAutocomplete']").val() == undefined ? null : $(modal).find("input[name='toValueAutocomplete']").val();
						data.toScope = ($(modal).find("input[name='toValue']").attr("parameterScope") == undefined || $(modal).find("input[name='toValue']").attr("parameterScope") == "") ? null : $(modal).find("input[name='toValue']").attr("parameterScope");
						data.lstToIndex = [];
					} else if(toPattern=="1"){
						data.toValue = isEmptyQP($(trBDContentIndex).attr("parameterId")) ? null : $(trBDContentIndex).attr("parameterId");
						data.toScope = (isEmptyQP($(trBDContentIndex).attr("parameterScope"))) ? null : $(trBDContentIndex).attr("parameterScope");
						var lstIndex =[];
						$(trBDContentIndex).find("input[name='parameterIndexId']").each(function (){
							var item = new Object();
							var parameterIndexId = $(this).val() == undefined ? null : $(this).val();
							var parameterIndexIdAutocomplete = $(this).prev().val() == undefined ? null : $(this).prev().val();
							var parameterIndexType = ($(this).attr("indextype") == undefined || $(this).attr("indextype") == "") ? "3" : $(this).attr("indextype");
							var parameterId = ($(this).attr("parameterId") == undefined || $(this).attr("parameterId") == "") ? null : $(this).attr("parameterId");
							item.parameterIndexId = parameterIndexId;
							item.parameterIndexIdAutocomplete = parameterIndexIdAutocomplete;
							item.parameterIndexType = parameterIndexType;
							item.parameterId = parameterId;
							if(!isEmptyQP(parameterIndexId))
								lstIndex.push(item);
						});
						data.lstToIndex = lstIndex;
					}
				} else {
					data.toValue = null;
					data.toValueAutocomplete = null;
					data.toScope = null;
					data.lstToIndex = [];
				}
			}
			
			index = $(modal).find("input[name='index']").val();
			
			/* Update by HungHX */
			loopStepType = ($(modal).find("select[name='loopStepType']").val() != undefined)?parseInt($(modal).find("select[name='loopStepType']").val()):null;
			loopStepValue = ($(modal).find("input[name='loopStepValue']").val() != undefined)?parseInt($(modal).find("input[name='loopStepValue']").val()):null;
			
		}
		if(loopType == 1){
			// Case : while
			formulaDefinitionContent = ($(modal).find("label[name='formulaDefinitionContent']").text() == undefined || $(modal).find("label[name='formulaDefinitionContent']").text() == "" ) ? null : $(modal).find("label[name='formulaDefinitionContent']").text();
			formulaDefinitionDetails = ($(modal).find("input[name='formulaDefinitionDetails']").val() == undefined || $(modal).find("input[name='formulaDefinitionDetails']").val() == "" ) ? [] : convertToJson($(modal).find("input[name='formulaDefinitionDetails']").val());
		}
		
	}
	
	if (data.loopType != undefined) {
		data.loopType = loopType;
	} else {		
		data["loopType"] = loopType;
	}
	if (data.parameterId != undefined) {
		data.parameterId = parameterId;
	} else {		
		data["parameterId"] = parameterId;
	}
	
	if (data.parameterIdAutocomplete != undefined) {
		data.parameterIdAutocomplete = parameterIdAutocomplete;
	} else {		
		data["parameterIdAutocomplete"] = parameterIdAutocomplete;
	}
	
	if (data.parameterScope != undefined) {
		data.parameterScope = parameterScope;
	} else {		
		data["parameterScope"] = parameterScope;
	}
	
//	if (data.fromValue != undefined) {
//		data.fromValue = fromValue;
//	} else {		
//		data["fromValue"] = fromValue;
//	}
//	
//	if (data.fromScope != undefined) {
//		data.fromScope = fromScope;
//	} else {		
//		data["fromScope"] = fromScope;
//	}
//	
//	if (data.toValue != undefined) {
//		data.toValue = toValue;
//	} else {		
//		data["toValue"] = toValue;
//	}
//	
//	if (data.toScope != undefined) {
//		data.toScope = toScope;
//	} else {		
//		data["toScope"] = toScope;
//	}
	
	if (data.formulaDefinitionId != undefined) {
		data.formulaDefinitionId = formulaDefinitionId;
	} else {		
		data["formulaDefinitionId"] = formulaDefinitionId;
	}
	
	if (data.formulaDefinitionContent != undefined) {
		data.formulaDefinitionContent = formulaDefinitionContent;
	} else {		
		data["formulaDefinitionContent"] = formulaDefinitionContent;
	}
	
	if (data.formulaDefinitionDetails != undefined) {
		data.formulaDefinitionDetails = formulaDefinitionDetails;
	} else {		
		data["formulaDefinitionDetails"] = formulaDefinitionDetails;
	}
	
	if (data.index != undefined) {
		data.index = index;
	} else {		
		data["index"] = index;
	}
	
	if (data.loopStepType != undefined) {
		data.loopStepType = loopStepType;
	} else {		
		data["loopStepType"] = loopStepType;
	}
	
	if (data.loopStepValue != undefined) {
		data.loopStepValue = loopStepValue;
	} else {
		data["loopStepValue"] = loopStepValue;
	}

	var validationMessage = $.qp.businessdesign.validaionComponent.validationLoopComp($.qp.businessdesign.validaionComponent.DETAIL_CHECK,data,true,thiz);
	//var validationMessage = "";
	if(validationMessage != undefined && validationMessage != ""){
		$.qp.showAlertModal(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("data-original-title",remark);
		$(obj).find("span.component-name").text(label);
		$(modal).modal("hide");
		instance.repaintEverything();
	}
	saveCommonSetting($(obj));
}

function saveModalValidationCheckDetailSetting(thiz){
	var modal = $(thiz).closest(BD_MODAL_NAME.VALIDATIONCHECK_DETAIL);
	var obj = $(modal).data("target");
	var parameters = "";
	var table = "#tbl-validationcheck-detail";
	var validationCheckDetails = [];
	$.qp.undoFormatNumericForm(modal);
	
	$(modal).find(table).find("tbody>tr").each(function(index){
		var validateFlg = $(this).find("input[type=checkbox][name=validateFlg]").prop("checked");
		if(validateFlg){
			var parameters = []
			$(this).find("input[name='parameterCode']").each(function(index){
				var objData = new Object();
				var parameterType = $(this).parent().attr("parameterType");
				if(parameterType == "0"){
					objData.parameterCode = ($(this).val() == undefined || $(this).val() == "")? null :$(this).val();
					objData.parameterCodeAutocomplete = ($(this).prevAll("input[name='parameterCodeAutocomplete']").val() == undefined || $(this).prevAll("input[name='parameterCodeAutocomplete']").val() == "")? null :$(this).prevAll("input[name='parameterCodeAutocomplete']").val();
					objData.parameterType = parameterType;
					var itemSequenceNo = $(this).parent().attr("itemSequenceNo");
					objData.itemSequenceNo = (itemSequenceNo == undefined || itemSequenceNo == "") ? null :itemSequenceNo;
					objData.patternFormat = getDateFormatOfInput($(this).parent().find("input[type=hidden][name='patternFormat']"));
					var level = $(this).closest("tr").find("td.level").attr("messageLevel");
					objData.messageLevel = (level == undefined || level == null || level == "") ? null :level;
					parameters.push(objData);
				}
				if(parameterType == "1"){
					objData.parameterCode = ($(this).val() == undefined || $(this).val() == "")? null :$(this).val();
					objData.parameterType = parameterType;
					var itemSequenceNo = $(this).parent().attr("itemSequenceNo");
					objData.itemSequenceNo = (itemSequenceNo == undefined || itemSequenceNo == "") ? null :itemSequenceNo;
					objData.patternFormat = getDateFormatOfInput(this);
					parameters.push(objData);
				}
			});
			var validationType = $(this).attr("validationType");
			parameters = parameters.sort(sortByItemSequenceNo);
			validationCheckDetails.push({validationType:validationType,parameters:parameters});
			
		}
	});	
//	var groupData = checkGroupOfValidationCheck(validationCheckDetails);
//	if(groupData !=undefined){
//		var currentRow = $(obj).closest("tr");
//		if(groupData.isLength){
//			$(currentRow).find("td[name=length]").html("O");
//		}else{
//			$(currentRow).find("td[name=length]").empty();
//		}
//		if(groupData.isMandatory){
//			$(currentRow).find("td[name=mandatory]").html("O");
//		}else{
//			$(currentRow).find("td[name=mandatory]").empty();
//		}
//		if(groupData.isOthers){
//			$(currentRow).find("td[name=others]").html("O");
//		}else{
//			$(currentRow).find("td[name=others]").empty();
//		}
//	}
	var value =  convertToString(validationCheckDetails);
	$(obj).closest("td").find("input[name*='jsonValidationInputs']").val(value);
	$(obj).closest("tr").removeClass("qp-bdesign-tr-remove");
	$(modal).modal("hide");
}



/**
 * get datetime format by datime picker
 * @param input
 */
function getDateFormatOfInput(input){
	var format = "";
	if($(input).hasClass("qp-input-datetimepicker")){
		format = javaDateTimeFormat;
	}
	if($(input).hasClass("qp-input-datepicker")){
		format = javaDateFormat;
	}
	if($(input).hasClass("qp-input-timepicker")){
		format = javaTimeFormat;
	}
	return format;
}

function saveModalNestedLogicSetting(thiz){
	var modal = $(thiz).closest("body");
	var table = $(modal).find("#tbl-nestedlocic-infor");
	var parentWindow = window.parent.document;
	var instanceParent = parent.instanceOfBlogic;
	var instance = instanceOfBlogic;
	var targetBoxId = $(modal).find("input[name='sequenceLogicId']").val();
	var obj = $(parentWindow).find("#"+targetBoxId);
	var data = new Object();
	var label  = "";
	if (data.sequenceLogicId != undefined) {
		data.sequenceLogicId = targetBoxId;
	} else {		
		data["sequenceLogicId"] = targetBoxId;
	}
	if($(table).find("input[name='label']").val() != undefined){
		label = $(table).find("input[name='label']").val();
	}	
	if (data.label != undefined) {
		data.label = label;
	} else {		
		data["label"] = label;
	}
	
	var remark  = "";
	if($(table).find("textarea[name='remark']").val() != undefined){
		remark = $(table).find("textarea[name='remark']").val();
	}
	if (data.remark != undefined) {
		data.remark = remark;
	} else {		
		data["remark"] = remark;
	}
	
	var arrComponent = new Array();
	var arrConnection = new Array();
	$(modal).find(".design-area").find(".execution-class").each(function(i) {
		var object = new Object();
		object.sequenceLogicId = $(this).attr("id");
		var xCoordinates = $(this).css("left");
		object.xCoordinates = xCoordinates.replace("px", "");
		var yCoordinates = $(this).css("top");
		object.yCoordinates = yCoordinates.replace("px", "");
		object.sequenceNo = i;
		object.sequenceLogicName = $(this).find("span.component-name").text();
		object.componentType = $(this).attr("componenttype");
		object.remark = $(this).attr("title");
		object.strData = $(this).find("input[name='componentElement']").val();
		if("9" == object.componentType){
			object.relatedSequenceLogicId = $(this).attr("id-endif");
		}
		arrComponent.push(object);
	});
	var arrAllConnection = instance.getAllConnections();
	for (var i = 0; i < arrAllConnection.length; i++) {
		var connection = new Object();
		connection.connectorSource = arrAllConnection[i].sourceId;
		connection.connectorDest = arrAllConnection[i].targetId;
		connection.connectorType =  arrAllConnection[i].getOverlay("label").getLabel();
		arrConnection.push(connection);
	}
	if (data.arrComponent != undefined) {
		data.arrComponent = arrComponent;
	} else {		
		data["arrComponent"] = arrComponent;
	}
	if (data.arrConnection != undefined) {
		data.arrConnection = arrConnection;
	} else {		
		data["arrConnection"] = arrConnection;
	}
	var validationMessage = $.qp.businessdesign.validaionComponent.validationNestedLogicComp($.qp.businessdesign.validaionComponent.DETAIL_CHECK,data,true,thiz);
	if(validationMessage != undefined && validationMessage != ""){
		$.qp.showAlertModal(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("data-original-title",remark);
		$(obj).find("span.component-name").text(label);
		parent.jQuery.fancybox.close();
		instanceParent.repaintEverything();
	}
}
function saveModalAdvanceSetting(thiz){
	var modal = BD_MODAL_NAME.ADVANCE;
	var instance = $(modal).data("instance");
	var obj = $(modal).data("target");
	var data = convertToJson($(modal).data("data"));
	var label  = "";
	var methodName  = "";
	if($(modal).find("input[name='label']").val() != undefined){
		label = $(modal).find("input[name='label']").val();
	}	
	if (data.label != undefined) {
		data.label = label;
	} else {		
		data["label"] = label;
	}
	
	if($(modal).find("input[name='methodName']").val() != undefined){
		methodName = $(modal).find("input[name='methodName']").val();
	}	
	if (data.methodName != undefined) {
		data.methodName = methodName;
	} else {		
		data["methodName"] = methodName;
	}
	
	var content  = "";
	if($(modal).find("textarea[id='editor-java-code']").val() != undefined){
		content = javaEditor.getValue();
	}
	if (data.content != undefined) {
		data.content = content;
	} else {		
		data["content"] = content;
	}
	
	var remark  = "";
	if($(modal).find("textarea[name='remark']").val() != undefined){
		remark = $(modal).find("textarea[name='remark']").val();
	}
	if (data.remark != undefined) {
		data.remark = remark;
	} else {		
		data["remark"] = remark;
	}

	var parameterInputBeans = new Array();
	$(modal).find("#tbl-advance-inputbean").find("tbody>tr").each(function(){
		var objInputBean = new Object();
		objInputBean.advanceInputValueId = $(this).find("input[name='advanceInputValueId']").val() == undefined ? null : $(this).find("input[name='advanceInputValueId']").val();
		objInputBean.inputBeanName = $(this).find("input[name='inputBeanName']").val() == undefined ? null : $(this).find("input[name='inputBeanName']").val() ;
		objInputBean.inputBeanCode = $(this).find("input[name='inputBeanCode']").val() == undefined ? null : $(this).find("input[name='inputBeanCode']").val();
		objInputBean.dataType = $(this).find("select[name='dataType']").val() == undefined ? null : $(this).find("select[name='dataType']").val();
		objInputBean.arrayFlg = $(this).find("input[name='arrayFlg']").length == 0 ? false : $(this).find("input[name*='arrayFlg']").prop("checked");
		
//		objInputBean.parameterId = ($(this).find("input[name='parameterId']").val() == undefined || $(this).find("input[name='parameterId']").val() == "" ) ? null : $(this).find("input[name='parameterId']").val();
//		objInputBean.parameterIdAutocomplete = ($(this).find("input[name='parameterIdAutocomplete']").val() == undefined  || $(this).find("input[name='parameterIdAutocomplete']").val() == "" )? null : $(this).find("input[name='parameterIdAutocomplete']").val();
//		var parameterScope = ($(this).find("input[name='parameterId']").attr("parameterScope") == undefined || $(this).find("input[name='parameterId']").attr("parameterScope") == "") ? null : $(this).find("input[name='parameterId']").attr("parameterScope");
//		objInputBean.parameterScope = parameterScope;
		var parameterPattern = $(this).find("div.bd-content[name='parameter']").attr("pattern");
		if(parameterPattern=="0"){
			objInputBean.parameterId = $(this).find("input[name='parameterId']").val() == undefined ? null : $(this).find("input[name='parameterId']").val();
			objInputBean.parameterIdAutocomplete = $(this).find("input[name='parameterIdAutocomplete']").val() == undefined ? null : $(this).find("input[name='parameterIdAutocomplete']").val();
			objInputBean.parameterScope = ($(this).find("input[name='parameterId']").attr("parameterScope") == undefined || $(this).find("input[name='parameterId']").attr("parameterScope") == "") ? null : $(this).find("input[name='parameterId']").attr("parameterScope");
			objInputBean.lstParameterIndex = [];
		}else if(parameterPattern=="1"){
			objInputBean.parameterId = isEmptyQP($(this).find("div.bd-content[name='parameter']").attr("parameterId")) ? null : $(this).find("div.bd-content[name='parameter']").attr("parameterId");
			objInputBean.parameterScope = (isEmptyQP($(this).find("div.bd-content[name='parameter']").attr("parameterScope"))) ? null : $(this).find("div.bd-content[name='parameter']").attr("parameterScope");
			var lstParameterIndex =[];
			$(this).find("div.bd-content[name='parameter']").find("input[name='parameterIndexId']").each(function (){
				var item = new Object();
				var parameterIndexId = $(this).val() == undefined ? null : $(this).val();
				var parameterIndexIdAutocomplete = $(this).prev().val() == undefined ? null : $(this).prev().val();
				var parameterIndexType = ($(this).attr("indextype") == undefined || $(this).attr("indextype") == "") ? "3" : $(this).attr("indextype");
				var parameterId = ($(this).attr("parameterId") == undefined || $(this).attr("parameterId") == "") ? null : $(this).attr("parameterId");
				item.parameterIndexId = parameterIndexId;
				item.parameterIndexIdAutocomplete = parameterIndexIdAutocomplete;
				item.parameterIndexType = parameterIndexType;
				item.parameterId = parameterId;
				if(!isEmptyQP(parameterIndexId))
					lstParameterIndex.push(item);
			});
			objInputBean.lstParameterIndex = lstParameterIndex;
		}
		
		parameterInputBeans.push(objInputBean);
	});
	if (data.parameterInputBeans != undefined) {
		data.parameterInputBeans = parameterInputBeans;
	} else {		
		data["parameterInputBeans"] = parameterInputBeans;
	}
	
	var parameterOutputBeans = new Array();
	$(modal).find("#tbl-advance-outputbean").find("tbody>tr").each(function(){
		var objOutputbean= new Object();
		objOutputbean.advanceOutputValueId = $(this).find("input[name='advanceOutputValueId']").val() == undefined ? null : $(this).find("input[name='advanceOutputValueId']").val();
		objOutputbean.outputBeanName = $(this).find("input[name='outputBeanName']").val() == undefined ? null :  $(this).find("input[name='outputBeanName']").val();
		objOutputbean.outputBeanCode = $(this).find("input[name='outputBeanCode']").val() == undefined ? null : $(this).find("input[name='outputBeanCode']").val();
		objOutputbean.dataType = $(this).find("select[name='dataType']").val() == undefined ? null : $(this).find("select[name='dataType']").val();
		objOutputbean.arrayFlg = $(this).find("input[name='arrayFlg']").length == 0 ? false : $(this).find("input[name*='arrayFlg']").prop("checked");
		
//		objOutputbean.targetId = ($(this).find("input[name='targetId']").val() == undefined || $(this).find("input[name='targetId']").val() == "")? null : $(this).find("input[name='targetId']").val();
//		objOutputbean.targetIdAutocomplete = ($(this).find("input[name='targetIdAutocomplete']").val() == undefined || $(this).find("input[name='targetIdAutocomplete']").val() == "")? null : $(this).find("input[name='targetIdAutocomplete']").val();
//		objOutputbean.targetScope = ($(this).find("input[name='targetId']").attr("parameterScope") == undefined || $(this).find("input[name='targetId']").attr("parameterScope") == "")? null : $(this).find("input[name='targetId']").attr("parameterScope");
		var targetPattern = $(this).find("div.bd-content[name='target']").attr("pattern");
		if(targetPattern=="0"){
			objOutputbean.targetId = $(this).find("input[name='targetId']").val() == undefined ? null : $(this).find("input[name='targetId']").val();
			objOutputbean.targetIdAutocomplete = $(this).find("input[name='targetIdAutocomplete']").val() == undefined ? null : $(this).find("input[name='targetIdAutocomplete']").val();
			objOutputbean.targetScope = ($(this).find("input[name='targetId']").attr("parameterScope") == undefined || $(this).find("input[name='targetId']").attr("parameterScope") == "") ? null : $(this).find("input[name='targetId']").attr("parameterScope");
			objOutputbean.lstTargetIndex = [];
		}else if(targetPattern=="1"){
			objOutputbean.targetId = isEmptyQP($(this).find("div.bd-content[name='target']").attr("parameterId")) ? null : $(this).find("div.bd-content[name='target']").attr("parameterId");
			objOutputbean.targetScope = (isEmptyQP($(this).find("div.bd-content[name='target']").attr("parameterScope"))) ? null : $(this).find("div.bd-content[name='target']").attr("parameterScope");
			var lstTargetIndex =[];
			$(this).find("div.bd-content[name='target']").find("input[name='parameterIndexId']").each(function (){
				var item = new Object();
				var parameterIndexId = $(this).val() == undefined ? null : $(this).val();
				var parameterIndexIdAutocomplete = $(this).prev().val() == undefined ? null : $(this).prev().val();
				var parameterIndexType = ($(this).attr("indextype") == undefined || $(this).attr("indextype") == "") ? "3" : $(this).attr("indextype");
				var parameterId = ($(this).attr("parameterId") == undefined || $(this).attr("parameterId") == "") ? null : $(this).attr("parameterId");
				item.parameterIndexId = parameterIndexId;
				item.parameterIndexIdAutocomplete = parameterIndexIdAutocomplete;
				item.parameterIndexType = parameterIndexType;
				item.parameterId = parameterId;
				if(!isEmptyQP(parameterIndexId))
					lstTargetIndex.push(item);
			});
			objOutputbean.lstTargetIndex = lstTargetIndex;
		}
		parameterOutputBeans.push(objOutputbean);
	});
	if (data.parameterOutputBeans != undefined) {
		data.parameterOutputBeans = parameterOutputBeans;
	} else {		
		data["parameterOutputBeans"] = parameterOutputBeans;
	}
	
	var validationMessage = $.qp.businessdesign.validaionComponent.validationAdvanceComp($.qp.businessdesign.validaionComponent.DETAIL_CHECK,data,true,thiz);
	//var validationMessage = "";
	
	if(validationMessage != undefined && validationMessage != ""){
		$.qp.showAlertModal(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("data-original-title",remark);
		$(obj).find("span.component-name").text(label);
		$(modal).modal("hide");
		instance.repaintEverything();
	}
	saveCommonSetting($(obj));
}
function deleteModalValidationCheckDetailSetting(thiz){
	var modal = $(thiz).closest(BD_MODAL_NAME.VALIDATIONCHECK_DETAIL);
	var obj = $(modal).data("target");
	var parameters = "";
	var table = "#tbl-validationcheck-detail";
	var validationCheckDetails = [];
//	var groupData = checkGroupOfValidationCheck(validationCheckDetails);
//	if(groupData !=undefined){
//		var currentRow = $(obj).closest("tr");
//		if(groupData.isLength){
//			$(currentRow).find("td[name=length]").html("O");
//		}else{
//			$(currentRow).find("td[name=length]").empty();
//		}
//		if(groupData.isMandatory){
//			$(currentRow).find("td[name=mandatory]").html("O");
//		}else{
//			$(currentRow).find("td[name=mandatory]").empty();
//		}
//		if(groupData.isOthers){
//			$(currentRow).find("td[name=others]").html("O");
//		}else{
//			$(currentRow).find("td[name=others]").empty();
//		}
//	}
	var value =  convertToString(validationCheckDetails);
	$(obj).closest("td").find("input[name*='jsonValidationInputs']").val(value);
	$(obj).closest("tr").removeClass("qp-bdesign-tr-remove");
	$(modal).modal("hide");
}
/*
 * delete nested logic node
 */
function deleteNestedLogicModal(thiz){
	if ($.qp.confirm($.qp.getModuleMessage('err.blogiccomponent.0130')) == true) {
		var parentWindow = window.parent.document;
		var body = $(thiz).closest("body");
		var targetBoxId = $(body).find("input[name='sequenceLogicId']").val();
		if(targetBoxId != undefined){
			var instance = parent.instanceOfBlogic;
			var connectionSources = instance.getConnections({ source:targetBoxId });
			var connectionTargets = instance.getConnections({ target:targetBoxId });
			for(var i = 0;i<connectionSources.length;i++){
				processDeleteConnectionOfDeletedNode(connectionSources[i],instance);
			}
			for(var i = 0;i<connectionTargets.length;i++){
				processDeleteConnectionOfDeletedNode(connectionTargets[i],instance);
			}
			instance.removeAllEndpoints(targetBoxId);
			instance.detach(targetBoxId);
			$(parentWindow).find("#"+targetBoxId).remove();
//			$(parentWindow).find('.fancybox-overlay').hide();
			parent.jQuery.fancybox.close();
			instance.repaintEverything();
		}
	}
}
/*
 * delete this node
 */
function deleteDialog(thiz){
	var modal=$(thiz).closest(".modal");
	var instance = $(modal).data("instance");
	var obj = $(modal).data("target");
	var type = $(thiz).attr("componenttype");
	if(type != "1"){
		deleteNode($(obj),instance);
		$(modal).modal("hide");
	}	
}
function deleteNode(thiz,instance){
	if(instance == undefined)
		instance = instanceOfBlogic;
	if ($.qp.confirm($.qp.getModuleMessage('err.blogiccomponent.0130')) == true) {
		deleteNodeNotConfirm(thiz,instance);
		instance.repaintEverything();
		//clear tooltip of end node
		$("div.tooltip").remove();
	}
}
function deleteNodeNotConfirm(thiz,instance){
	var targetBoxId = $(thiz).attr("id");
	var componentType =  $(thiz).attr("componenttype");
	if(componentType == "9"){
		//remove end-if
		var idEndIf =  $(thiz).attr("id-endif");
		if(idEndIf != undefined && idEndIf != null){
			var endIfNode = $(thiz).closest("div.design-area").find("div[id='"+idEndIf+"']");
			if(endIfNode.length > 0){
				deleteNodeNotConfirm(endIfNode,instance);
			}
		}
	}
	
	var connectionSources = instance.getConnections({ source:targetBoxId });
	var connectionTargets = instance.getConnections({ target:targetBoxId });
	for(var i = 0;i<connectionSources.length;i++){
		processDeleteConnectionOfDeletedNode(connectionSources[i],instance);
	}
	for(var i = 0;i<connectionTargets.length;i++){
		processDeleteConnectionOfDeletedNode(connectionTargets[i],instance);
	}
	instance.removeAllEndpoints(targetBoxId);
	instance.detach(targetBoxId);
	$(instance.getContainer()).find("#" + targetBoxId).remove();
}
function onChangeNavigationType(thiz){
	var type = $(thiz).val();
	var sqlId = "getAutocompleteBusinessLogicForNavigatorBD";
	
	$(thiz).closest("table").find("input[name=moduleIdAutocomplete]").attr('disabled', false);
	$(thiz).closest("table").find("input[name=navigatorToIdAutocomplete]").attr('disabled', false);
	$(thiz).closest("table").find("input[name=navigatorToIdAutocomplete]").val("");
	$(thiz).closest("table").find("input[name=navigatorToId]").val("");
	
	$(thiz).closest(".modal").find("#tbl-navigator-inputbean").find("tbody").empty();
	
	$(thiz).closest("table").find("input[name=transitionType][value=0]").parent().hide();
	$(thiz).closest("table").find("input[name=transitionType][value=1]").parent().hide();
	$(thiz).closest("table").find("a[name='registerNavigatorLink']").show();
	switch (type) {
	case "0":
		sqlId = "getAutocompleteScreenDesignForNavigatorNode";
		$(thiz).closest("table").find("[name=tranType]").hide();
		$(thiz).closest("table").find("td[name=navigatorType]").attr("colspan", 3);
		$(thiz).closest("table").find("input[name=transitionType][value=0]").prop("checked",true);
		$(thiz).closest("table").find("a[name='registerNavigatorLink']").attr("onClick","openRegisterNewNavigator(0)");
		break;
	case "1":
		$(thiz).closest("table").find("td[name=navigatorType]").attr("colspan", 1);
		$(thiz).closest("table").find("[name=tranType]").show();
		$(thiz).closest("table").find("input[name=transitionType][value=0]").parent().show();
		$(thiz).closest("table").find("input[name=transitionType][value=1]").parent().show();
		$(thiz).closest("table").find("a[name='registerNavigatorLink']").attr("onClick","openRegisterNewNavigator(1)");
		break;
	case "2":
		sqlId = "getAutocompleteScreenDesignForNavigatorNode";
		$(thiz).closest("table").find("td[name=navigatorType]").attr("colspan", 1);
		$(thiz).closest("table").find("[name=tranType]").show();
		$(thiz).closest("table").find("input[name=transitionType][value=0]").parent().show();
		$(thiz).closest("table").find("input[name=transitionType][value=0]").prop("checked",true);
		
		//disable
		$(thiz).closest("table").find("input[name=moduleIdAutocomplete]").val("");
		$(thiz).closest("table").find("input[name=moduleIdAutocomplete]").attr('disabled', true);
		$(thiz).closest("table").find("input[name=moduleId]").val("");
		
		$(thiz).closest("table").find("input[name=navigatorToIdAutocomplete]").attr('disabled', true);
		$(thiz).closest("table").find("input[name=navigatorToIdAutocomplete]").val("Delete successful");
		$(thiz).closest("table").find("input[name=navigatorToId]").val("-1");
		$(thiz).closest("table").find("a[name='registerNavigatorLink']").hide();
		
		break;
	default:
		break;
	}
	$(thiz).closest("table").find("input[name=navigatorToIdAutocomplete]").attr("selectsqlid",sqlId);
	$(thiz).closest("table").find("input[name=navigatorToIdAutocomplete]").data("ui-autocomplete")._trigger("close");
	$(thiz).closest("table").find("input[name=moduleIdAutocomplete]").data("ui-autocomplete")._trigger("close");
}
function onchangeBusinessLogicOfCommonSetting(event){
	var obj = event.target;
	var modal = $(obj).closest(".modal");
	var tableInput = "#tbl-common-inputbean";
	$(modal).find(tableInput).find("tbody").empty();
	var tableOutput = "#tbl-common-outputbean";
	$(modal).find(tableOutput).find("tbody").empty();
	$(obj).closest("td").removeClass("qp-bdesign-tr-remove");
	$(obj).closest("tr").find("label[name=businessLogicCode]").text("");
	var href = $(modal).find("label[name=businessLogicCode]").parent().attr('href').split("=");
	if(event.item != undefined){
		if(event.item.optionValue != ""){
			var url = CONTEXT_PATH + "/businessdesign/getInformationOfCommonBusinessLogic?businessDesignId="+event.item.optionValue+"&r="+Math.random();
			var responseData = $.qp.getData(url);
			if(responseData.businessLogicId == null){
				$(modal).find("label[name=businessLogicCode]").text("");
			}else{
				//input bean
				$(modal).find("label[name=businessLogicCode]").text(responseData.businessLogicCode);
				$(modal).find("label[name=businessLogicCode]").parent().attr('href',href[0]+ "=" + event.item.optionValue + "&mode=0");
				var data = responseData.lstInputBean;
				for(var i=0;i<data.length;i++){
					var currentInput = data[i];
					var tableIndex = (currentInput.tableIndex == null || currentInput.tableIndex == undefined) ? "" : currentInput.tableIndex;
					currentInput.tableIndex = tableIndex;
					var dataTypeStr = CL_BD_DATATYPE[currentInput.dataType];
					if(eval(currentInput.arrayFlg)){
						dataTypeStr += "[]";
					}
					currentInput.dataTypeStr = dataTypeStr;
					var templateInput = $(modal).find("#tbl-common-inputbean-template").tmpl(currentInput);
					$(templateInput).find("td.btn-remove").children().hide();
					$(modal).find(tableInput).find("tbody").append(templateInput);
				}
				//output bean
				data = responseData.lstOutputBean;
				for(var i=0;i<data.length;i++){
					var currentOutput = data[i];
					var tableIndex = (currentOutput.tableIndex == null || currentOutput.tableIndex == undefined) ? "" : currentOutput.tableIndex;
					currentOutput.tableIndex = tableIndex;
					var dataTypeStr = CL_BD_DATATYPE[currentOutput.dataType];
					if(eval(currentOutput.arrayFlg)){
						dataTypeStr += "[]";
					}
					currentOutput.dataTypeStr = dataTypeStr;
					var templateOutput = $(modal).find("#tbl-common-outputbean-template").tmpl(currentOutput);
					$(templateOutput).find("td.btn-remove").children().hide();
					$(modal).find(tableOutput).find("tbody").append(templateOutput);
				}
			}
		}
	}
	$.qp.initialAllPicker(modal);
}
function onchangeModuleOfNavigator(event) {
	var obj = event.target;
	var modal = $(obj).closest(".modal");
	$(obj).attr("navigatorToName","");
	$(obj).closest("td").removeClass("qp-bdesign-tr-remove");
	var table = "#tbl-navigator-inputbean";
	$(modal).find(table).find("tbody").empty();
	$(modal).find("input[name='navigatorToId']").val("");
	$(modal).find("input[name='navigatorToIdAutocomplete']").val("");
	var selectedModule = "";
	if(event.item != undefined && event.item.optionValue != "") {
		selectedModule = event.item.optionValue;
	}
	$(modal).find("input[name='navigatorToIdAutocomplete']").attr("arg03",selectedModule);
	$.qp.initialAllPicker($(modal));
	$(modal).find("input[name$='IdAutocomplete']").each(function(){
		$(this).data("ui-autocomplete")._trigger("close");
	});
}

function onchangeNavigatorOfNavigator(event){
	var obj = event.target;
	var modal = $(obj).closest(".modal");
	var table = "#tbl-navigator-inputbean";
	$(obj).attr("navigatorToName","");
	$(obj).closest("td").removeClass("qp-bdesign-tr-remove");
	$(modal).find(table).find("tbody").empty();
	if(event.item != undefined){
		if(event.item.optionValue != ""){
			$(obj).attr("navigatorToName",event.item.output01);
			
			//check navigator type
			var navigatorToType = $(modal).find(":radio[name='navigatorToType']:checked").val();
			if(navigatorToType == "1"){
				var url = CONTEXT_PATH + "/businessdesign/getInputBeanOfBusinessLogic?businessDesignId="+event.item.optionValue+"&r="+Math.random();
				var data = $.qp.getData(url);
				//input bean
				for(var i=0;i<data.lstInputBean.length;i++){
					var currentInput = data.lstInputBean[i];
					var tableIndex = (currentInput.tableIndex == null || currentInput.tableIndex == undefined) ? "" : currentInput.tableIndex;
					currentInput.tableIndex = tableIndex;
					var dataTypeStr = CL_BD_DATATYPE[currentInput.dataType];
					if(eval(currentInput.arrayFlg)){
						dataTypeStr += "[]";
					}
					currentInput.dataTypeStr = dataTypeStr;
					var templateInput = $(modal).find("#tbl-navigator-inputbean-template").tmpl(currentInput);
					$(templateInput).find("td.btn-remove").children().hide();
					$(modal).find(table).find("tbody").append(templateInput);
				}
			}
		}
	}
	$.qp.initialAllPicker(modal);
}

function onchangeModuleOfDecision(event) {
	var obj = event.target;
	var modal = $(obj).closest(".modal");
	var tableInput = "#tbl-decision-inputbean";
	var tableOutput = "#tbl-decision-outputbean";
	var tableLogic = "#tbl_logic_design";
	$(modal).find(tableInput).find("tbody").empty();
	$(modal).find(tableOutput).find("tbody").empty();
	$(modal).find(tableLogic).children().remove();
	$(modal).find("label[name=decisionTableCode]").text("");
	$(modal).find("input[name=decisionTableId]").val("");
	$(modal).find("input[name=decisionTableIdAutocomplete]").val("");
	$(modal).find("input[name=decisionTableIdAutocomplete]").closest("td").removeClass("qp-bdesign-tr-remove");
	var selectedModule = "";
	if(event.item != undefined && event.item.optionValue != "") {
		selectedModule = event.item.optionValue;
	}
	$(modal).find("input[name='decisionTableIdAutocomplete']").attr("arg03",selectedModule);
	$.qp.initialAllPicker($(modal));
	$(modal).find("input[name$='IdAutocomplete']").each(function(){
		$(this).data("ui-autocomplete")._trigger("close");
	});
}

function onchangeDecisionOfDecision(event){
	var obj = event.target;
	var modal = $(obj).closest(".modal");	
	var tableInput = "#tbl-decision-inputbean";
	var tableOutput = "#tbl-decision-outputbean";
	var tableLogic = "#tbl_logic_design";
	$(modal).find(tableInput).find("tbody").empty();
	$(modal).find(tableOutput).find("tbody").empty();
	$(modal).find(tableLogic).children().remove();
	$(modal).find("label[name=decisionTableCode]").text("");
	$(modal).find("input[name=decisionTableId]").val("");
	$(modal).find("input[name=decisionTableIdAutocomplete]").val("");
	$(modal).find("input[name=decisionTableIdAutocomplete]").closest("td").removeClass("qp-bdesign-tr-remove");
	var href = $(modal).find("label[name=decisionTableCode]").parent().attr('href').split("=");
	
	if(event.item != undefined && event.item.optionValue != "") {
			
		$(modal).find("label[name=decisionTableCode]").text(event.item.output01);
		//All data decision
		var url = CONTEXT_PATH + "/businessdesign/getDataDecisionComp?decisionTbId="+event.item.optionValue+"&r="+Math.random();
		var arrRequestData = $.qp.getData(url);
		
		// Split all data by tab of decision
		var decisionInfor = arrRequestData[0];
		var arrReqInput = arrRequestData[1];
		var arrReqOutput = arrRequestData[2];
		var arrReqItemCond = arrRequestData[3];
		var arrReqItemAct = arrRequestData[4];
		var arrReqCondGroup = arrRequestData[5];
		var arrReqCondItem = arrRequestData[6];
		
		if(decisionInfor != null && decisionInfor.decisionTbId != null){
			$(modal).find("input[name='decisionTableId']").val(decisionInfor.decisionTbId);
			$(modal).find("label[name='decisionTableCode']").parent().attr('href',href[0]+ "=" + decisionInfor.decisionTbId);
			
			if(decisionInfor.decisionTbName != undefined && decisionInfor.decisionTbName != null && decisionInfor.decisionTbName != ""){
				$(modal).find("input[name='decisionTableIdAutocomplete']").val(decisionInfor.decisionTbName);
			}else{
				$(modal).find("input[name='decisionTableIdAutocomplete']").val("");
			}
			if(decisionInfor.decisionTbCode != undefined && decisionInfor.decisionTbCode != null && decisionInfor.decisionTbCode != ""){
				$(modal).find("label[name='decisionTableCode']").text(decisionInfor.decisionTbCode);
			}else{
				$(modal).find("label[name='decisionTableCode']").text("");
			}
			//input bean
			for(var i=0;i<arrReqInput.length;i++){
				var currentInput = arrReqInput[i];
				var tableIndex = (currentInput.tableIndex == null || currentInput.tableIndex == undefined) ? "" : currentInput.tableIndex;
				currentInput.tableIndex = tableIndex;
				currentInput.dataTypeStr = CL_BD_DATATYPE[currentInput.dataType];
				var templateInput = $(modal).find("#tbl-decision-inputbean-template").tmpl(currentInput);
				$(templateInput).find("td.btn-remove").children().hide();
				$(modal).find("#tbl-decision-inputbean").find("tbody").append(templateInput);
			}
	
			//output bean
			for(var i=0;i<arrReqOutput.length;i++){
				var currentOutput = arrReqOutput[i];
				var tableIndex = (currentOutput.tableIndex == null || currentOutput.tableIndex == undefined) ? "" : currentOutput.tableIndex;
				currentOutput.tableIndex = tableIndex;
				currentOutput.dataTypeStr = CL_BD_DATATYPE[currentOutput.dataType];
				var templateOutput = $(modal).find("#tbl-decision-outputbean-template").tmpl(currentOutput);
				$(templateOutput).find("td.btn-remove").children().hide();
				$(modal).find("#tbl-decision-outputbean").find("tbody").append(templateOutput);
			}
		
			// Display tab logic design
			if(arrReqItemCond != undefined && arrReqItemCond.length > 0 && arrReqItemAct != undefined && arrReqItemAct.length > 0 
							&& arrReqCondGroup != undefined && arrReqCondItem != undefined ){
				$(modal).find("#tbl_logic_design").next().hide();
				displayTabLogicDesign(arrReqItemCond, arrReqItemAct, arrReqCondGroup, arrReqCondItem);
			}
			else{
				$(modal).find("#tbl_logic_design").next().show();
			}
		}
	}
	$.qp.initialAllPicker(modal);
}
function ongetPropetyOfObjectAssignSet(thiz){
	var currentRow = $(thiz).closest("tr");
	var data_ar_rgroup = $(thiz).closest("tr").attr("data-ar-rgroup");
	var data_ar_rindex = $(thiz).closest("tr").attr("data-ar-rindex");
	var groupIndex = $(thiz).closest("tr").attr("data-ar-rgroupindex");
	var modal = $(thiz).closest(".modal");
	var arrIndexLoop = getLabelOfIndexLoop();
	//remove old rows
	$(currentRow).nextUntil($(currentRow).nextAll("tr[data-ar-rgroup='"+data_ar_rgroup+"']")).each(function (){
		$(this).remove();
	});
	var objAssign = new Object();
	var targetPattern = $(currentRow).find("div.bd-content[name='target']").attr("pattern");
	if(targetPattern=="0"){
		objAssign.targetId = $(currentRow).find("input[name='targetId']").val() == undefined ? null : $(currentRow).find("input[name='targetId']").val();
		objAssign.lstTargetIndex = [];
	}else if(targetPattern=="1"){
		objAssign.targetId = isEmptyQP($(currentRow).find("div.bd-content[name='target']").attr("parameterId")) ? null : $(currentRow).find("div.bd-content[name='target']").attr("parameterId");
		var lstTargetIndex =[];
		$(currentRow).find("div.bd-content[name='target']").find("input[name='parameterIndexId']").each(function (){
			var item = new Object();
			var parameterIndexId = $(this).val() == undefined ? null : $(this).val();
			var parameterIndexIdAutocomplete = $(this).prev().val() == undefined ? null : $(this).prev().val();
			var parameterIndexType = ($(this).attr("indextype") == undefined || $(this).attr("indextype") == "") ? "3" : $(this).attr("indextype");
			var parameterId = ($(this).attr("parameterId") == undefined || $(this).attr("parameterId") == "") ? null : $(this).attr("parameterId");
			item.parameterIndexId = parameterIndexId;
			item.parameterIndexIdAutocomplete = parameterIndexIdAutocomplete;
			item.parameterIndexType = parameterIndexType;
			item.parameterId = parameterId;
			if(!isEmptyQP(parameterIndexId))
				lstTargetIndex.push(item);
		});
		objAssign.lstTargetIndex = lstTargetIndex;
	}
	if(objAssign.targetId != undefined && objAssign.targetId != null){
		//get children of target
		var listChildren = [];
		var fullData = getDataSourceMap().idResults;
		var currenObject = null;
		for(var id in fullData) { 
		    var param = fullData[id];
		    if(objAssign.targetId == (param.output01 + param.output02)){
		    	listChildren.push(param);
		    }
		    if(objAssign.targetId == param.optionValue){
		    	currenObject = param;
		    }
		}
		//in case of single object : not set group index
		if(currenObject!= null && currenObject.output04 == false){
			groupIndex = '';
		}
		for (var i = listChildren.length - 1; i >= 0; i--) {
			var newRow = $.qp.ar.addRow({link:this,tableId:'tbl-assign-parameter',templateId:'tbl-assign-parameter-template',templateData:{groupId:groupIndex},position:{anchor:currentRow,string:"after"}});
			bindDataToAssignAutocomplete(listChildren[i].optionValue,"target",objAssign.lstTargetIndex,arrIndexLoop,$(newRow),$(modal),onremoveTargetCallbackOfAssignSet);	
			if(groupIndex != ''){
				$(newRow).find("td.btn-getobject").children().hide();
			}
		}
	}
}
function bindIndexOfObjectAssignSet(parentRow,newRow){
	if($(parentRow).find("div[name='indexOfTarget']").css('display') != 'none'){
		var targetIndexId = $(parentRow).find("input[name='targetIndexId']").val() == undefined ? null : $(parentRow).find("input[name='targetIndexId']").val();
		var targetIndexIdAutocomplete = $(parentRow).find("input[name='targetIndexIdAutocomplete']").val() == undefined ? null : $(parentRow).find("input[name='targetIndexIdAutocomplete']").val();
		var targetIndexType = ($(parentRow).find("input[name='targetIndexId']").attr("indextype") == undefined || $(parentRow).find("input[name='targetIndexId']").attr("indextype") == "") ? 3 : $(parentRow).find("input[name='targetIndexId']").attr("indextype");
		
		$(newRow).find("div[name='indexOfTarget']").show();
		$(newRow).find("input[name='targetIndexId']").val(targetIndexId);
		switch (targetIndexType.toString()) {
		case "3":
			$(newRow).find("input[name='targetIndexIdAutocomplete']").val(targetIndexId);
			break;
		case "4":
			var actualIndex = targetIndexId.substring(1, targetIndexId.length);
			var labelIndex = arrIndexLoop[actualIndex];
			if(labelIndex != undefined){
				$(newRow).find("input[name='targetIndexIdAutocomplete']").val(labelIndex);
			}else{
				$(newRow).find("input[name='targetIndexIdAutocomplete']").val("");
				$(newRow).find("div[name='indexOfTarget']").closest("td").addClass("qp-bdesign-tr-remove");
			}
			break;
		case "0":
		case "1":
		case "2":
			var inforIndex = getInformationOfParameter(targetIndexId);
			if(inforIndex != undefined){
				$(newRow).find("input[name='targetIndexIdAutocomplete']").val(inforIndex.optionLabel);
			}else{
				$(newRow).find("input[name='targetIndexIdAutocomplete']").val("");
				$(newRow).find("div[name='indexOfTarget']").closest("td").addClass("qp-bdesign-tr-remove");
			}
		default:
			break;
		}
		$(newRow).find("input[name='targetIndexIdAutocomplete']").data("ui-autocomplete")._trigger("close");
		$(newRow).find("input[name='targetIndexId']").attr("indexType",targetIndexType);
	}
}
function onchangeMessageOfFeebackSet(event){
	var object = $(event.target).parents("tr").eq(0);
	if(event.item != undefined){
//		$(model).find("span[name='messageCode']").text(event.item.output01);
		var message = event.item.optionLabel;
		var count = countParameterOfMessage(message);
		if(count == 0){
			$(object).find("span[name='btnChooseParameter']").hide();
		}else{
			$(object).find("span[name='btnChooseParameter']").show();
		}
	}	else{
		$(object).find("span[name='btnChooseParameter']").hide();
	}
	$(object).find("input[name='messageParameter']").val("");
}

function onchangeParameterOfAssignSet(event){
	var obj = event.target;
	var currentTd = $(obj).closest("td");
	setParameterTypeOfAutocompleteBD(event);
	var arrayFlg = onchangeParameterOfBD(event,onremoveParameterCallbackOfAssignSet);
	if(arrayFlg){
		var obj = event.target;
		$(currentTd).next("td").find("a.glyphicon-cog").hide();
	}else{
		$(currentTd).next("td").find("a.glyphicon-cog").show();
	}
}
function onchangeTargetOfAssignSet(event){
	var obj = event.target;
	setParameterTypeOfAutocompleteBD(event);
	onremoveTargetCallbackOfAssignSet(obj);
	onchangeParameterOfBD(event,onremoveTargetCallbackOfAssignSet);
}
function onremoveParameterCallbackOfAssignSet(thiz){
	var currentTd = $(thiz).closest("td");
	$(currentTd).next("td").find("a.glyphicon-cog").show();
}
function onchangeFeedbackTypeOfFeedbackSet(thiz){
	var table = $(thiz).closest("table");
	var autocomplete = $(table).find("input[name=messageCodeAutocomplete]");
	var value = $(thiz).val();
	if(value != undefined &&  value != null  && value.length >0){
		 $(table).find("input[name=messageCodeAutocomplete]").attr("arg03",value);
	}else{
		 $(table).find("input[name=messageCodeAutocomplete]").attr("arg03","");
	}
	
}
function onchangeParameterOfMessageParSet(event){
	var obj = event.target;
	var levelName = "";
	var value ="";
	if(event.item != undefined){
		value = event.item.output01;
		if(value != undefined && value != null && value != ""){
			levelName = getlevelOfMessage(value);
		}else{
			levelName = $.qp.getModuleMessage("sc.blogiccomponent.0046");
			value = "";
		}
	}
	$(obj).closest("tr").find("span[name=messageLevel]").html(levelName);
	$(obj).closest("tr").find("input[name=messageLevel]").val(value);
}
function onchangeMessageOfValidationCheck(event){
	var obj = event.target;
	var levelName = "";
	var value ="";
	if(event.item != undefined){
		value = event.item.output01;
		if(value != undefined && value != null && value != ""){
			levelName = getlevelOfMessage(value);
		}else{
			levelName = $.qp.getModuleMessage("sc.blogiccomponent.0046");
			value = "";
		}
	}
	$(obj).closest("tr").find("td.level").text(levelName);
	$(obj).closest("tr").find("td.level").attr("messageLevel",value);	
}
function checkGroupOfValidationCheck(data){
	var obj = new Object();
	obj.isMandatory = false;
	obj.isOthers = false;
	obj.range="";
	obj.isLength= false;
	for(var i =0; i< data.length; i++){
		var objTemp = data[i];
		var standardCheck = BD_STANDARD_CHECK[objTemp.validationType];
		if(standardCheck != undefined){
			//check length
			if(standardCheck.groupType==0){
				obj.isLength = true;
			}
			// check mandatory
			if(standardCheck.groupType==2)
				obj.isMandatory = true;
			// check others
			if(standardCheck.groupType==3)
				obj.isOthers = true;
		}
	}
	return obj;
}
function checkValidationRuleOfDataType(dataType,validationType,arrayFlg){
	var isValidated = false;
	/* Update by HungHX base on ticket 504 */
	switch (validationType) {
	case 0:
	case 1:
		isValidated = (eval(arrayFlg) == false && dataType != BD_STANDARD_DATATYPE.STRING && dataType != BD_STANDARD_DATATYPE.CHAR &&
				dataType != BD_STANDARD_DATATYPE.BIGDECIMAL && dataType != BD_STANDARD_DATATYPE.DATE
				&& dataType != BD_STANDARD_DATATYPE.DATETIME && dataType != BD_STANDARD_DATATYPE.TIMESTAMP && dataType != BD_STANDARD_DATATYPE.TIME)?true:false;
		break;
	case 2:
	case 13:
	case 14:
	case 15:
	case 18:
	case 19:
	case 20:
	case 21:
	case 22:
	case 23:
	case 24:
	case 25:
	case 26:
	case 27:
	case 28:
	case 29:
	case 30:	
	case 31:	
	case 32:	
	case 33:
	case 34:
	case 35:		
	case 37:	
	case 38:	
	case 39:	
	case 40:	
	case 41:
	case 42:	
	case 43:
	case 44:
	case 45:
	case 46:
	case 47:
	case 50:	
		isValidated = (eval(arrayFlg) == false && dataType == BD_STANDARD_DATATYPE.STRING)?true:false;
		break;
	case 76:
	case 79:
		isValidated = (eval(arrayFlg) == false && dataType == BD_STANDARD_DATATYPE.DATE)?true:false;
		break;	
	case 77:
	case 80:
		isValidated = (eval(arrayFlg) == false && dataType == BD_STANDARD_DATATYPE.DATETIME)?true:false;
		break;
	case 78:
	case 81:
		isValidated = (eval(arrayFlg) == false && dataType == BD_STANDARD_DATATYPE.TIMESTAMP)?true:false;
		break;
	case 48:	
		isValidated = (eval(arrayFlg) == false && (dataType == BD_STANDARD_DATATYPE.STRING || dataType == BD_STANDARD_DATATYPE.DATETIME))?true:false;
		break;	
	case 49:	
		isValidated = (eval(arrayFlg) == false && (dataType == BD_STANDARD_DATATYPE.STRING || dataType == BD_STANDARD_DATATYPE.DATE))?true:false;
		break;
	case 36:
		isValidated = (eval(arrayFlg) == false && (dataType == BD_STANDARD_DATATYPE.STRING || dataType == BD_STANDARD_DATATYPE.TIME))?true:false;
		break
	case 3:
	case 4:
		isValidated = (eval(arrayFlg) == false && (dataType == BD_STANDARD_DATATYPE.STRING || dataType == BD_STANDARD_DATATYPE.SHORT || dataType == BD_STANDARD_DATATYPE.INTEGER
				|| dataType == BD_STANDARD_DATATYPE.LONG || dataType == BD_STANDARD_DATATYPE.FLOAT  || dataType == BD_STANDARD_DATATYPE.DOUBLE))?true:false;
		break;
	case 5:
	case 6:
		isValidated = (eval(arrayFlg) == false && (dataType == BD_STANDARD_DATATYPE.BIGDECIMAL))?true:false;
		break;
	case 8:
		isValidated = (eval(arrayFlg) == false && (dataType == BD_STANDARD_DATATYPE.STRING || dataType == BD_STANDARD_DATATYPE.BIGDECIMAL ||
				dataType == BD_STANDARD_DATATYPE.SHORT ||dataType == BD_STANDARD_DATATYPE.INTEGER  || dataType == BD_STANDARD_DATATYPE.LONG
				|| dataType == BD_STANDARD_DATATYPE.FLOAT || dataType == BD_STANDARD_DATATYPE.DOUBLE))?true:false;
		break;
	case 7:
		isValidated = (eval(arrayFlg) == true)?true:false;
		break;
	case 16:	
	case 17:
		isValidated = (eval(arrayFlg) == false && (dataType == BD_STANDARD_DATATYPE.STRING || dataType == BD_STANDARD_DATATYPE.CHAR ||
				dataType == BD_STANDARD_DATATYPE.BIGDECIMAL || dataType == BD_STANDARD_DATATYPE.DATE
				|| dataType == BD_STANDARD_DATATYPE.DATETIME || dataType == BD_STANDARD_DATATYPE.TIMESTAMP || dataType == BD_STANDARD_DATATYPE.TIME))?true:false;
		break;
	case 9:
	case 10:
		isValidated = (eval(arrayFlg) == false && dataType == BD_STANDARD_DATATYPE.BOOLEAN)?true:false;
		break;
	case 51:
	case 52:
	//case 53:
	case 68:
	case 69:	
		isValidated = (eval(arrayFlg) == false && dataType == BD_STANDARD_DATATYPE.DATE)?true:false;
		break;
	case 54:
	case 55:
	//case 56:
	case 72:
	case 73:	
		isValidated = (eval(arrayFlg) == false && dataType == BD_STANDARD_DATATYPE.TIME)?true:false;
		break;
	case 57:
	case 58:
	//case 59:
	case 70:
	case 71:	
		isValidated = (eval(arrayFlg) == false && dataType == BD_STANDARD_DATATYPE.DATETIME)?true:false;
		break;
	case 60:
	case 61:
	//case 62:
	case 74:
	case 75:	
		isValidated = (eval(arrayFlg) == false && dataType == BD_STANDARD_DATATYPE.TIMESTAMP)?true:false;
		break;	
	case 63:
		isValidated = (eval(arrayFlg) == false && (dataType == BD_STANDARD_DATATYPE.STRING || dataType == BD_STANDARD_DATATYPE.TIMESTAMP))?true:false;
		break;
	case 64:
	case 65:
	case 66:
	case 67:	
		isValidated = (eval(arrayFlg) == false && (dataType == BD_STANDARD_DATATYPE.STRING || dataType == BD_STANDARD_DATATYPE.SHORT || dataType == BD_STANDARD_DATATYPE.BIGDECIMAL || dataType == BD_STANDARD_DATATYPE.DOUBLE || dataType == BD_STANDARD_DATATYPE.FLOAT || dataType == BD_STANDARD_DATATYPE.LONG || dataType == BD_STANDARD_DATATYPE.INTEGER ))?true:false;
		break;
	}
	
	return isValidated;
}
function onchangeValidFlagOfValidationDetailSet(thiz){
	var status = $(thiz).prop("checked");
	$(thiz).closest("tr").find("div[name=divMessageParameter]").each(function (){
		$(this).find("input[type=text]").attr("disabled",!status);
		if(!status){
			$(this).find("input[type=text]").val("");
		}
	});
	$(thiz).closest("tr").find("input[name*=Autocomplete]").data("ui-autocomplete")._trigger("close");
}
function onchangeModuleOfExecution(event) {
	var obj = event.target;
	var modal = $(obj).closest(".modal");
	var tableInputBean = "#tbl-execution-inputbean";
	var tableOutputBean = "#tbl-execution-outputbean";
	$(modal).find(tableInputBean).find("tbody").empty();
	$(modal).find(tableOutputBean).find("tbody").empty();
	$(modal).find('div[id="tabsExecution-1"]').empty();
	$(modal).find("label[name=sqlDesignCode]").text("");
	$(modal).find("input[name=sqlDesignId]").val("");
	$(modal).find("input[name=sqlDesignIdAutocomplete]").val("");
	$(modal).find("input[name=sqlDesignIdAutocomplete]").closest("td").removeClass("qp-bdesign-tr-remove");
	$(modal).find("label[name='sqlPattern']").text("");
	$(modal).find("input[name='concurrencyFlg']").attr('checked', false);
	$(modal).find("input[name='concurrencyFlg']").attr('disabled', true);
	$(modal).find("label[name='returnType']").text("");
	var selectedModule = "";
	if(event.item != undefined && event.item.optionValue != "") {
		selectedModule = event.item.optionValue;
	}
	$(modal).find("input[name='sqlDesignIdAutocomplete']").attr("arg03",selectedModule);
	$.qp.initialAllPicker($(modal));
	$(modal).find("input[name$='IdAutocomplete']").each(function(){
		$(this).data("ui-autocomplete")._trigger("close");
	});
}
function onchangeSQLOfExecution(event) {
	var obj = event.target;
	var modal = $(obj).closest(".modal");	
	$(modal).find("label[name=sqlDesignCode]").text("");
	var href = $(modal).find("label[name=sqlDesignCode]").parent().attr('href').split("=");

	var tableInputBean = "#tbl-execution-inputbean";
	var tableOutputBean = "#tbl-execution-outputbean";
	$(modal).find(tableInputBean).find("tbody").empty();
	$(modal).find(tableOutputBean).find("tbody").empty();
	$(modal).find('div[id="tabsExecution-1"]').empty();
	$(modal).find("label[name=sqlDesignCode]").text("");
	$(modal).find("input[name=sqlDesignId]").val("");
	$(modal).find("input[name=sqlDesignIdAutocomplete]").val("");
	$(modal).find("input[name=sqlDesignIdAutocomplete]").closest("td").removeClass("qp-bdesign-tr-remove");
	$(modal).find("label[name='sqlPattern']").text("");
	$(modal).find("input[name='concurrencyFlg']").attr('checked', false);
	$(modal).find("input[name='concurrencyFlg']").attr('disabled', true);
	$(modal).find("label[name='returnType']").text("");
	
	if(event.item != undefined && event.item.optionValue != "") {
		$(modal).find("label[name=sqlDesignCode]").text(event.item.output01);
		$(modal).find("label[name=sqlDesignCode]").parent().attr('href',href[0]+ "=" + event.item.optionValue);
		
		var url = CONTEXT_PATH + "/businessdesign/getSQLDesignById?sqlDesignId="+event.item.optionValue+"&r="+Math.random();
		var arrRequestData = $.qp.getData(url);
		var sqlInfor = arrRequestData.sqlDesign;
		if(arrRequestData != null && sqlInfor != null && sqlInfor.sqlDesignId != null){
			$(modal).find("input[name='sqlDesignId']").val(sqlInfor.sqlDesignId);
			
			if(sqlInfor.sqlDesignName != undefined && sqlInfor.sqlDesignName != null && sqlInfor.sqlDesignName != ""){
				$(modal).find("input[name='sqlDesignIdAutocomplete']").val(sqlInfor.sqlDesignName);
			}else{
				$(modal).find("input[name='sqlDesignIdAutocomplete']").val("");
			}
			if(sqlInfor.sqlDesignCode != undefined && sqlInfor.sqlDesignCode != null && sqlInfor.sqlDesignCode != ""){
				$(modal).find("label[name='sqlDesignCode']").text(sqlInfor.sqlDesignCode);
			}else{
				$(modal).find("label[name='sqlDesignCode']").text("");
			}
			
			if(sqlInfor.sqlPattern != undefined && sqlInfor.sqlPattern != null){
				var sqlPattern = CL_SQL_SQLPATTERN[sqlInfor.sqlPattern];
				$(modal).find("label[name='sqlPattern']").text(sqlPattern);
				if(sqlInfor.sqlPattern == "2" || sqlInfor.sqlPattern == "3"){
					$(modal).find("input[name='concurrencyFlg']").attr('disabled', false);
				}
			}
			
			var returnType = CL_SQL_RETURNTYPE[sqlInfor.returnType];
			$(modal).find("label[name='returnType']").text(returnType);
			
			var arrsqlDesignInputs = arrRequestData.sqlDesignInputs;
			if(arrsqlDesignInputs != undefined && arrsqlDesignInputs.length >0){
				for(var i=0;i<arrsqlDesignInputs.length;i++){
					var inputValue = arrsqlDesignInputs[i];
					var dataType = CL_BD_DATATYPE[inputValue.dataType] 
					if(inputValue.arrayFlag  == "1"){
						dataType += "[]";
					}
					inputValue.dataTypeStr = dataType;
					var templateInput = $(modal).find("#tbl-execution-inputbean-template").tmpl(inputValue);
					$(templateInput).find("td.btn-remove").children().hide();
					$(modal).find(tableInputBean).find("tbody").append(templateInput);
				}
			}
			var arrsqlDesignOutputs = arrRequestData.sqlDesignOutputs;
			if(arrsqlDesignOutputs != undefined && arrsqlDesignOutputs.length >0){
				for(var i=0;i<arrsqlDesignOutputs.length;i++){
					var outputValue = arrsqlDesignOutputs[i];
					var dataType = CL_BD_DATATYPE[outputValue.dataType] 
					if(outputValue.arrayFlag  == "1"){
						dataType += "[]";
					}
					outputValue.dataTypeStr = dataType;
					var templateOutput = $(modal).find("#tbl-execution-outputbean-template").tmpl(outputValue);
					$(templateOutput).find("td.btn-remove").children().hide();
					$(modal).find(tableOutputBean).find("tbody").append(templateOutput);
				}
			}
			
			url = CONTEXT_PATH + "/sqldesign/view?sqlDesignForm.sqlDesignId="+event.item.optionValue+"&r="+Math.random();
			var htmlContent = $.qp.getHtml(url);
			$(modal).find('div[id="tabsExecution-1"]')
				.append($(htmlContent).find('div.tab-content>div#tab-sql-design').children());
		}
	}
	$.qp.sqlbuilder.initFromForm();
	$.qp.initialAllPicker($(modal));
}
function addBusinessCheckOfBCheckSet(thiz){
	var parent = $(thiz).closest("#dialog-businesscheck-setting");
	var checkType = $(parent).find("#tbl-bcheck-infor").find("select[name=businessCheckType]").val();
	var tableTmp;
	if(checkType == "0"){
		$.qp.showAlertModal($.qp.getModuleMessage('err.blogiccomponent.0137'));
	}else{
		switch (parseInt(checkType)) {
		case 1:
			tableTmp = $("#tbl-bcheck-function-template").tmpl();
			break;
		case 2:
			tableTmp = $("#tbl-bcheck-existence-template").tmpl();
			break;
		case 3:
			tableTmp = $("#tbl-bcheck-duplicated-template").tmpl();
			break;

		default:
			break;
		}
		$(tableTmp).attr("businessCheckType",checkType);
		$(parent).find("#listBusinessCheck").append(tableTmp);
		$.qp.initialAllPicker($(parent).find("#listBusinessCheck"));

		$("#listBusinessCheck").sortable({
			// placeholder: "ui-sortable-placeholder"
			helper: function(e, ui) {
				ui.children().each(function() {
					$(this).width($(this).width());
				});
				return ui;
			},
			update: function(event, ui) {
			},
			items: '.qp-bdesign-div-content',
			cursor: 'move',
			handle: '.sortable',
			scroll: false
		});
//		$('#listBusinessCheck').scrollTop($('#listBusinessCheck').prop("scrollHeight"));
		var body = $('#dialog-businesscheck-setting .modal-body');
		var position = $('#listBusinessCheck').find(".qp-bdesign-div-content").last().position();
		$(body).scrollTop(position.top);
	}
}
function removeBusinessCheckOfBCheckSet(thiz){
	if($.qp.confirm($.qp.getMessage('inf.sys.0014'))){
		$(thiz).closest('div.qp-bdesign-div-content').remove();		
	}
}
function onAddNewConditionOfBCheckSet(table,direction,row){
	if(direction=="add"){
		var tblDesignId = $(table).closest("tr").find("input[name=tblDesignId]").val();
		if(tblDesignId != undefined){
			$(row).find("input[name=columnIdAutocomplete]").attr("arg02",tblDesignId);
		}
	}
}
function onChangeTableConditionOfBCheckSet(event){
	var obj = event.target;
	$(obj).closest("tr").find("table[id=tbl-content-conditions]").find("tbody").empty();
	if(event.item != undefined){
		var optionLabel = event.item.optionLabel;
		if(optionLabel != undefined && optionLabel.length >0 ){
			var aTag = $(obj).closest("tr").find("table[id=tbl-content-conditions]").next().find("a");
			$.qp.addRowJSByLinkEx(aTag,'','tbl-content-conditions-template');
		}
	}
}
function onAddNewMessageOfMessageParameterSet(table,direction,row){
	if(direction=="add"){
		if(moduleIdOfBD !=undefined && moduleIdOfBD != null){
			$(row).find("input[name=parameterCodeAutocomplete]").attr("arg03",moduleIdOfBD);
		}
	}
}

function findTargetLabelOfFormulaForAssign(thiz){
	return $(thiz).closest("tr").find("[name=parameterIdAutocomplete]");
}

function findTargetValueOfFormulaForAssign(thiz){
	return $(thiz).closest("tr").find("[name=formulaDefinitionDetails]");
}

function findTargetLabelOfFormula(thiz){
	return $(thiz).closest("td").find("[name=formulaDefinitionContent]");
}

function findTargetValueOfFormula(thiz){
	return $(thiz).closest("td").find("[name=formulaDefinitionDetails]");
}

function onBeforeOpenFormulaForAssign(thiz){
//	$(thiz).closest("tr").find("input[name='parameterIdAutocomplete']").val("");
//	$(thiz).closest("tr").find("input[name='parameterId']").val("");
//	$(thiz).closest("tr").find("input[name='parameterIdAutocomplete']").data("ui-autocomplete")._trigger("close");
}

function onAfterSaveFormulaForAssign(thiz,formulaDefinitionDetails){
	if(formulaDefinitionDetails.length >0){
		$(thiz).closest("tr").find("input[name='parameterId']").val("");
		$(thiz).closest("tr").find("[name=parameterIdAutocomplete]").addClass("qp-bdesign-tr-change");
		$(thiz).closest("tr").find("input[name='parameterIdAutocomplete']").data("ui-autocomplete")._trigger("close");
		$(thiz).closest("tr").find("[name=assignType]").val("1");
		
		$(thiz).closest("tr").find("div[name='indexOfParameter']").hide();
		$(thiz).closest("tr").find("input[name='parameterIndexId']").val();
		$(thiz).closest("tr").find("input[name='parameterIndexIdAutocomplete']").val();
		$(thiz).closest("tr").find("input[name='parameterIndexId']").attr("indextype","");
	}
}

function onAfterDeleteFormulaForAssign(thiz){
	$(thiz).closest("tr").find("[name=parameterIdAutocomplete]").removeClass("qp-bdesign-tr-change");
	$(thiz).closest("tr").find("[name=assignType]").val("0");
	
	$(thiz).closest("tr").find("div[name='indexOfParameter']").hide();
	$(thiz).closest("tr").find("input[name='parameterIndexId']").val();
	$(thiz).closest("tr").find("input[name='parameterIndexIdAutocomplete']").val();
	$(thiz).closest("tr").find("input[name='parameterIndexId']").attr("indextype","");
}
/*
 * handle event of Loop setting
 */
function onChangeAutoObjectOfLoop (event){
	setParameterTypeOfAutocompleteBD(event);
	var obj = event.target;
	if(event.item != undefined){
//		$(obj).closest("tr").find("input[name='toType']").prop("disabled",false);
//		$(obj).closest("tr").find("input[name='fromType']").prop("disabled",false);
		$(obj).closest("tr").next().next().find("input[name='toType']").prop("disabled",false);
		$(obj).closest("tr").next().next().find("input[name='fromType']").prop("disabled",false);
	}
	else{
		$(obj).closest("tr").next().next().find("input[name='toType']").prop("disabled",true);
		$(obj).closest("tr").next().next().find("input[name='toType']").prop("checked",false);
		$(obj).closest("tr").next().next().find("input[name='fromType']").prop("disabled",true);
		$(obj).closest("tr").next().next().find("input[name='fromType']").prop("checked",false);
		$(obj).closest("tr").next().next().find("input[name='toValueAutocomplete']").prop('disabled', false);
		$(obj).closest("tr").next().next().find("input[name='fromValueAutocomplete']").prop('disabled', false)
	}
	
}
function onchangeTypeOfLoop(thiz)
{
	var value = $(thiz).val();
	var modal = $(thiz).closest(".modal");
	$(modal).find('.bd-for-type-foreach').hide();
	$(modal).find('.bd-for-type-while').hide();
	switch (value) {
	case '0':
		$(modal).find('.bd-for-type-foreach').show();
		//reset value
		$(modal).find("input[name='parameterId']").val("");
		$(modal).find("input[name='parameterIdAutocomplete']").val("");
		$(modal).find("input[name='fromScope']").prop("checked",false);
		$(modal).find("input[name='fromScope']").prop("disabled",true);
		$(modal).find("input[name='fromValue']").prop('disabled', false)
		$(modal).find("input[name='fromValue']").val("");
		$(modal).find("input[name='toScope']").prop("checked",false);
		$(modal).find("input[name='toScope']").prop("disabled",true);
		$(modal).find("input[name='toValue']").prop('disabled', false)
		$(modal).find("input[name='toValue']").val("");
		$(modal).find("select[name='loopStepType']").val(0);
		$(modal).find("input[name='loopStepValue']").val(1);
		break;
	case '1':
		$(modal).find('.bd-for-type-while').show();
		$(modal).find("label[name='formulaDefinitionContent']").text("");
		$(modal).find("input[name='formulaDefinitionDetails']").val(convertToString([]));
		break;
	default:
		break;
	}
	$.qp.initialAllPicker($(modal));
}

function onchooseLengthOfLoop(thiz){
	var status = $(thiz).prop("checked");
	if (status){
		var templateInput;
		if(thiz.name == "fromType"){
			templateInput = $(thiz).closest("div.modal").find("#div-from-template").tmpl();
			$(thiz).closest(".input-group").find("div[id='from']").replaceWith(templateInput);
		} else {
			templateInput = $(thiz).closest("div.modal").find("#div-to-template").tmpl();
			$(thiz).closest(".input-group").find("div[id='to']").replaceWith(templateInput);
		}
		$.qp.initialAllPicker($(templateInput));
		$(thiz).closest(".input-group").find("input[name*='Autocomplete']").val("");
	}
	$(thiz).closest(".input-group").find("input[name*='Autocomplete']").prop('disabled', status);
}
function processDeleteConnectionOfDeletedNode(connection,instance){
	if(connection.sourceId == undefined){
		connection = connection.component;
	}
	var sourceId = connection.sourceId;
	if(arrConnectionComponent != undefined && arrConnectionComponent[sourceId] !=undefined){
		var arrStatus = arrConnectionComponent[sourceId];
		var label = connection.getOverlay("label").label
		if(label == null){
			label = "";
		}
		for(var i=0;i<arrStatus.length;i++){
			if(label.trim() == arrStatus[i].type.trim()){
				arrStatus[i].status =  false;
				break;
			}
		}
		arrConnectionComponent[sourceId] = arrStatus;
		instance.detach(connection);
	}else{
//		alert("SYSTEM ERROR");
	}
}
function onchangeTypeOfFileSetting(thiz){
	var type = $(thiz).val();
	$("#tbl-filesetting").find("tr[type='0']").hide();
	$("#tbl-filesetting").find("tr[type='1']").hide();
	$("#tbl-filesetting").find("tr[type='2']").hide();
	$("#tbl-filesetting").find("tr[type='3']").hide();
	$("#tbl-filesetting").find("tr[type='4']").hide();
	$("#tbl-filesetting").find("tr[type='5']").hide();
	if(type != undefined){
		$("#tbl-filesetting").find("tr[type='"+type+"']").show();
	}else{
		$("#tbl-filesetting").find("tr[type='"+type+"']").hide();
	}
}
function onchangeTypeOfFileOperation(thiz){
	var type = $(thiz).val();
	$("#tbl-fileoperation").find("tr[type='0']").hide();
	$("#tbl-fileoperation").find("tr[type='1']").hide();
	$("#tbl-fileoperation").find("tr[type='2']").hide();
	$("#tbl-fileoperation").find("tr[type='3']").hide();
	$("#tbl-fileoperation").find("tr[type='4']").hide();
	if(type != undefined){
		$("#tbl-fileoperation").find("tr[type='"+type+"']").show();
	}else{
		$("#tbl-fileoperation").find("tr[type='"+type+"']").hide();
	}
}
function assignStyleComponent(objLogic, isView){
	var OPEN_PARENTHESIS = "(";
	var CLOSE_PARENTHESIS = ")";
	var path = CONTEXT_PATH + "/resources/media/images/businessdesign/";
	switch (objLogic.componentType.toString()) {
	case "1":
		objLogic.imagePath = path + "start.png";
		objLogic.cssClass = "bdesign-node-one";
		objLogic.prefixLabel = $.qp.getModuleMessage('sc.blogiccomponent.0001');
		break;
	case "2":
		objLogic.imagePath = path + "excution.png";
		if(isView){
			objLogic.actionPath = "openModalExecution(this,true)";
		}else{
			objLogic.actionPath = "openModalExecution(this)";
		}
		objLogic.cssClass = "bdesign-node-default";
		objLogic.prefixLabel = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0002') + CLOSE_PARENTHESIS;
		break;
	case "3":
		objLogic.imagePath = path + "validationcheck.png";
		if(isView){
			objLogic.actionPath = "openModalValidationCheck(this,true)";
		}else{
			objLogic.actionPath = "openModalValidationCheck(this)";
		}
		objLogic.cssClass = "bdesign-node-default";
		objLogic.prefixLabel = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0003') + CLOSE_PARENTHESIS;
		break;
	case "4":
		objLogic.imagePath = path + "businesscheck.png";
		if(isView){
			objLogic.actionPath = "openModalBusinessCheck(this,true)";
		}else{
			objLogic.actionPath = "openModalBusinessCheck(this)";
		}
		objLogic.cssClass = "bdesign-node-default";
		objLogic.prefixLabel = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0004') + CLOSE_PARENTHESIS;
		break;
	case "5":
		objLogic.imagePath = path + "decision.png";
		if(isView){
			objLogic.actionPath = "openModalDecision(this,true)";
		}else{
			objLogic.actionPath = "openModalDecision(this)";
		}
		objLogic.cssClass = "bdesign-node-default";
		objLogic.prefixLabel = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0005') + CLOSE_PARENTHESIS;
		break;
	case "6":
		objLogic.imagePath = path + "advanced.png";
		objLogic.cssClass = "bdesign-node-two";
		objLogic.prefixLabel = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0006') + CLOSE_PARENTHESIS;
		break;
	case "7":
		objLogic.imagePath = path + "common.png";
		if(isView){
			objLogic.actionPath = "openModalCommon(this,true)";
		}else{
			objLogic.actionPath = "openModalCommon(this)";
		}
		objLogic.cssClass = "bdesign-node-two";
		objLogic.prefixLabel = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0007') + CLOSE_PARENTHESIS;
		break;
	case "8":
		objLogic.imagePath = path + "assign.png";
		if(isView){
			objLogic.actionPath = "openModalAssign(this,true)";
		}else{
			objLogic.actionPath = "openModalAssign(this)";
		}
		objLogic.cssClass = "bdesign-node-one";
		objLogic.prefixLabel = OPEN_PARENTHESIS +$.qp.getModuleMessage('sc.blogiccomponent.0008') + CLOSE_PARENTHESIS;
		break;
	case "9":
		objLogic.imagePath = path + "if.png";
		if(isView){
			objLogic.actionPath = "openModalIf(this,true)";
		}else{
			objLogic.actionPath = "openModalIf(this)";
		}
		objLogic.cssClass = "bdesign-node-one";
		objLogic.prefixLabel = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0009') + CLOSE_PARENTHESIS;
		break;
	case "10":
		objLogic.imagePath = path + "foreach.png";
		if(isView){
			objLogic.actionPath = "openModalLoop(this,true)";
		}else{
			objLogic.actionPath = "openModalLoop(this)";
		}
		objLogic.cssClass = "bdesign-node-one";
		objLogic.prefixLabel = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0010') + CLOSE_PARENTHESIS;
		break;
	case "11":
		objLogic.imagePath = path + "feedback.png";
		if(isView){
			objLogic.actionPath = "openModalFeedback(this,true)";
		}else{
			objLogic.actionPath = "openModalFeedback(this)";
		}
		objLogic.cssClass = "bdesign-node-one";
		objLogic.prefixLabel = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0011') + CLOSE_PARENTHESIS;
		break;
	case "12":
		objLogic.imagePath = path + "navigator.png";
		if(isView){
			objLogic.actionPath = "openModalNavigator(this,true)";
		}else{
			objLogic.actionPath = "openModalNavigator(this)";
		}
		objLogic.cssClass = "bdesign-node-one";
		objLogic.prefixLabel = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0012') + CLOSE_PARENTHESIS;
		break;
	case "13":
		objLogic.imagePath = path + "end.png";
		objLogic.cssClass = "bdesign-node-three";
		objLogic.prefixLabel = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0013') + CLOSE_PARENTHESIS;
		break;
	case "14":
		objLogic.imagePath = path + "nestedlogic.png";
		if(isView){
			objLogic.actionPath = "openModalNestedLogic(this,true)";
		}else{
			objLogic.actionPath = "openModalNestedLogic(this)";
		}
		objLogic.prefixLabel = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0126') + CLOSE_PARENTHESIS;
		break;
	case "15":
		objLogic.imagePath = path + "fileoperation.png";
		if(isView){
			objLogic.actionPath = "openModalFileOperation(this,true)";
		}else{
			objLogic.actionPath = "openModalFileOperation(this)";
		}
		objLogic.prefixLabel = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0125') + CLOSE_PARENTHESIS;
		break;
	case "16":
		objLogic.imagePath = path + "readfile.png";
		if(isView){
			objLogic.actionPath = "openModalReadFile(this,true)";
		}else{
			objLogic.actionPath = "openModalReadFile(this)";
		}
		objLogic.prefixLabel = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0081') + CLOSE_PARENTHESIS;
		break;
	case "17":
		objLogic.imagePath = path + "exportfile.png";
		if(isView){
			objLogic.actionPath = "openModalExportFile(this,true)";
		}else{
			objLogic.actionPath = "openModalExportFile(this)";
		}
		objLogic.prefixLabel = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0026') + CLOSE_PARENTHESIS;
		break;
	case "18":
		objLogic.imagePath = path + "transaction.png";
		if(isView){
			objLogic.actionPath = "openModalTransaction(this,true)";
		}else{
			objLogic.actionPath = "openModalTransaction(this)";
		}
		objLogic.prefixLabel = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0127') + CLOSE_PARENTHESIS;
		break;
	case "19":
		objLogic.imagePath = path + "end.png";
		objLogic.cssClass = "bdesign-node-three";
		objLogic.prefixLabel = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0128') + CLOSE_PARENTHESIS;
		break;
	case "20":
		objLogic.imagePath = path + "email.png";
		if(isView){
			objLogic.actionPath = "openModalEmail(this,true)";
		}else{
			objLogic.actionPath = "openModalEmail(this)";
		}
		objLogic.prefixLabel = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0143') + CLOSE_PARENTHESIS;
		break;
	case "21":
		objLogic.imagePath = path + "log.png";
		if(isView){
			objLogic.actionPath = "openModalLog(this,true)";
		}else{
			objLogic.actionPath = "openModalLog(this)";
		}
		objLogic.prefixLabel = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0144') + CLOSE_PARENTHESIS;
		break;
	case "22":
		objLogic.imagePath = path + "utility.png";
		if(isView){
			objLogic.actionPath = "openModalUtility(this,true)";
		}else{
			objLogic.actionPath = "openModalUtility(this)";
		}
		objLogic.prefixLabel = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0148') + CLOSE_PARENTHESIS;
		break;
	case "23":
		objLogic.imagePath = path + "download.png";
		if(isView){
			objLogic.actionPath = "openModalDownloadFile(this,true)";
		}else{
			objLogic.actionPath = "openModalDownloadFile(this)";
		}
		objLogic.prefixLabel = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0162') + CLOSE_PARENTHESIS;
		break;
	case "24":
		objLogic.imagePath = path + "exception.png";
		if(isView){
			objLogic.actionPath = "openModalException(this,true)";
		}else{
			objLogic.actionPath = "openModalException(this)";
		}
		objLogic.cssClass = "bdesign-node-one";
		objLogic.prefixLabel = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0196') + CLOSE_PARENTHESIS;
		break;
	default:
		break;
	}
	return objLogic;
}
function hideUnusedComponentOfNestedLogic(modal){
	var levelOfNestedLogic = $(modal).attr("level");
	$(modal).find("div.bdesign-node").each(function (){
		var componentType = $(this).attr("elementtype");
		switch (componentType) {
		case "1":
		case "3":
//		case "12":
		case "13":
			$(this).hide();
			break;
		case "14":
			if(levelOfNestedLogic != "2")
				$(this).hide();
			break;
		default:
			break;
		}
	});
}
function detachNestedLogicModal(thiz){
	if ($.qp.confirm($.qp.getModuleMessage('err.blogiccomponent.0138')) == true) {
		var modal = $(thiz).closest("body");
		var parentWindow = window.parent.document;
		var instanceParent = parent.instanceOfBlogic;
		var instance = instanceOfBlogic;
		var targetBoxId = $(modal).find("input[name='sequenceLogicId']").val();
		var obj = $(parentWindow).find("#"+targetBoxId);
		var arrComponent = new Array();
		var arrConnection = new Array();
		var positionLeft = new Object();
		positionLeft.minLeft = 0;
		positionLeft.maxLeft = 0;
		var positionTop = new Object();
		positionTop.minTop = 0;
		positionTop.maxTop = 0;
		//get connection
		var queryNodeID = [];
		$(modal).find(".design-area").find(".execution-class[componenttype!='1'][componenttype!='13']").each(function(i) {
			var currentLeft = parseInt($(this).css("left"));
			var currentTop = parseInt($(this).css("top"));
			if(i==0){
				positionLeft.minLeft = currentLeft;
				positionLeft.maxLeft = currentLeft;
				positionTop.minTop = currentTop;
				positionTop.maxTop = currentTop;
			}
			
			if(currentLeft > positionLeft.maxLeft){
				positionLeft.maxLeft = currentLeft;
			}
			if(currentLeft < positionLeft.minLeft){
				positionLeft.minLeft = currentLeft;
			}
			if(currentTop > positionTop.maxTop){
				positionTop.maxTop = currentTop;
			}
			if(currentTop < positionTop.minTop){
				positionTop.minTop = currentTop;
			}
			queryNodeID.push($(this).attr("id"));
		});
	
		var connectionSources = instance.getConnections({ source : queryNodeID , target : queryNodeID});
		
		var width = positionLeft.maxLeft - positionLeft.minLeft;
		var length = positionTop.maxTop - positionTop.minTop;
		var currentLeftOfParentNestedLogic = parseInt($(obj).css("left"));
		var currentTopOfParentNestedLogic = parseInt($(obj).css("top"));
		//move node to parent window
//		$(obj).remove();
		deleteNodeNotConfirm($(obj),instanceParent);
		$(modal).find(".design-area").find(".execution-class[componenttype!='1'][componenttype!='13']").each(function(i) {
			var currentLeft = parseInt($(this).css("left"));
			var currentTop = parseInt($(this).css("top"));
			$(this).css("left",currentLeft - positionLeft.minLeft+currentLeftOfParentNestedLogic);
			$(this).css("top",currentTop - positionTop.minTop + currentTopOfParentNestedLogic);
	//		instance.removeAllEndpoints($(this).attr("id"));
	//		instance.detach($(this).attr("id"));
			var clone = $(this).clone().removeClass("jsplumb-draggable jsplumb-droppable");
			$(parentWindow).find(".design-area").append(clone);
			
			instanceParent.draggable($(clone));
			
			initEndPointForComponent($(clone),instanceParent);
			initConnectionTargetForComponent($(clone).attr("id"),$(clone).attr("componenttype"));
			parent.initEventForNewNode($(clone).attr("id"));
			$(this).remove();
		});
		for(var i = 0;i<connectionSources.length;i++){
			instanceParent.connect({
				source : connectionSources[i].sourceId,
				target :connectionSources[i].targetId,
				overlays : [ [ "Arrow", {
					location : 1,
					id : "arrow",
					length : 14,
					width : 10,
					foldback : 0.8
				} ], [ "Label", {
					location : 45,
					cssClass : "aLabel"
				} ] ],
				parameters : {
					"type" : "modify",
					"label" : connectionSources[i].getOverlay("label").getLabel()
				}
			});
		}
		
		$(parentWindow).find(".design-area").find(".execution-class").each(function(i) {
			var currentLeft = parseInt($(this).css("left"));
			var currentTop = parseInt($(this).css("top"));
			if(currentTop >= currentLeftOfParentNestedLogic){
				$(this).css("top",currentTop+length);
			}
		});
		instanceParent.repaintEverything();
		parent.jQuery.fancybox.close();
	}
}
function initEventForNewNode(nodeId){
	var node = $("#"+nodeId);
	$(node).attr("add", "off");
	$(node).click(function() {
		compDragClick($(node),instanceOfBlogic);
//		instanceOfBlogic.draggable($(node));
	});
}
function initGroupNode(){
	$(document).keydown(function(e) {
        if (e.keyCode == 71 && e.ctrlKey) {
        	var level = $("#srcgenElements").attr("level");
        	if(level > 2){
        		$.qp.showAlertModal($.qp.getModuleMessage('err.blogiccomponent.0194'));
        	}else{
	        	var countNode = $("#designArea").find("div.execution-class[add='on'][componenttype!='1'][componenttype!='13']").length;
	        	if(countNode > 0){
	                if($.qp.confirm($.qp.getModuleMessage('err.blogiccomponent.0139'))){
	                	var prefixId = $("#designArea").attr("prefixId");
	                	if(prefixId == undefined || prefixId == ""){
	                		prefixId = "jsPlumb_";
	                	}
	                	var arrComponent = [];
	                	var nestedLogicNode = new Object();
	        			nestedLogicNode.label = "";
	        			nestedLogicNode.remark = "";
	        			var arrComponent = [];
	        			var arrConnection = [];
	        			arrComponent.push({"sequenceLogicId":"jsPlumb_3_2",
	        				"xCoordinates":"379",
	        				"yCoordinates":"55",
	        				"sequenceNo":0,
	        				"sequenceLogicName":"",
	        				"sequenceLogicId":"",
	        				"componentType":"1",});
	        			arrComponent.push({"sequenceLogicId":"jsPlumb_3_2",
	        				"xCoordinates":"383",
	        				"yCoordinates":"422",
	        				"sequenceNo":1,
	        				"sequenceLogicName":"",
	        				"sequenceLogicId":"",
	        				"componentType":"13",});
	        			var topPosition = 0;
	        			var leftPosition = 0;
	        			
	        			//get connection
	        			var queryNodeID = [];
	        			
	                	$("#designArea").find("div.execution-class[add='on'][componenttype!='1'][componenttype!='13']").each(function(i){
	                		var object = new Object();
	                		object.sequenceLogicId = prefixId + $(this).attr("id");
	                		object.relatedSequenceLogicId = prefixId + $(this).attr("id-endif");
	                		var xCoordinates = $(this).css("left");
	                		object.xCoordinates = xCoordinates.replace("px", "");
	                		var yCoordinates = $(this).css("top");
	                		object.yCoordinates = yCoordinates.replace("px", "");
	                		object.sequenceNo = i;
	                		object.sequenceLogicName = $(this).find("span.component-name").text();
	                		object.componentType = $(this).attr("componenttype");
	                		object.remark = $(this).attr("title");
	                		object.strData = $(this).find("input[name='componentElement']").val();
	                		arrComponent.push(object);
	                		queryNodeID.push($(this).attr("id"));
	            			
	                		if(i==0){
	                			leftPosition = object.yCoordinates;
	                			topPosition = object.xCoordinates;
	                		}
	                	});
	                	//remove connection + endpoint + node
	                	var connectionSources = instanceOfBlogic.getConnections({ source : queryNodeID , target : queryNodeID});
	        			for(var i = 0;i<connectionSources.length;i++){
	        				var connection = new Object();
	        				connection.connectorSource = prefixId + connectionSources[i].sourceId;
	        				connection.connectorDest = prefixId + connectionSources[i].targetId;
	        				connection.connectorType =  connectionSources[i].getOverlay("label").getLabel();
	        				arrConnection.push(connection);
	        				processDeleteConnectionOfDeletedNode(connectionSources[i],instanceOfBlogic);
	        			}
	        			for(var i = 0;i<queryNodeID.length;i++){
	        				instanceOfBlogic.removeAllEndpoints(queryNodeID[i]);
	            			instanceOfBlogic.detach(queryNodeID[i]);
	            			$("#" + queryNodeID[i]).remove();
	        			}
	        			nestedLogicNode.arrComponent = arrComponent;
	        			nestedLogicNode.arrConnection = arrConnection;
	        			var node = new Object();
	        			node.sequenceLogicName = "";
	        			node.remark = "";
	        			node.level = 2;
	        			node.componentType = "14";
	        			node.sequenceNo = 0;
	        			node.strData = convertToString(nestedLogicNode);
	        			node.xCoordinates = topPosition;
	        			node.yCoordinates = leftPosition;
	        			var styleItem = assignStyleComponent(node, false);
	    				var templateId = "#tbl-component-nestedlogicnode-template";
	    				var templateComponent = $(templateId).tmpl(styleItem);
	    				$("#designArea").append(templateComponent);
	    				
	    				//init jsplumb for this node
	    				$(templateComponent).click(function() {
	    					compDragClick($(templateComponent),instanceOfBlogic);
	    				});
	    				instanceOfBlogic.draggable(templateComponent, {
	    					containment : "designArea",
	    			        stop:function(e){
	    			            var position = getMaxPositionOfItemsBD();
	    			            $("#designArea").height(position.top);
	    			            $("#designArea").width(position.left);
	    			        },
	    				});
	    				initEndPointForComponent(templateComponent,instanceOfBlogic);
	    				initConnectionTargetForComponent($(templateComponent).attr("id"),14);
	    				instanceOfBlogic.repaintEverything();
	    				$(templateComponent).dblclick();
	                }
	        	}
        	}
        }
    });
}

function saveModalFileOperationSetting(thiz) {
	var modal = BD_MODAL_NAME.FILE_OPERATION;
	var instance = $(modal).data("instance");
	var obj = $(modal).data("target");
	var data = convertToJson($(modal).data("data"));
	
	var label  = "";
	if($(modal).find("input[name='label']").val() != undefined){
		label = $(modal).find("input[name='label']").val();
	}	
	
	if (data.label != undefined) {
		data.label = label;
	} else {		
		data["label"] = label;
	}
	
	var type = "";
	if($(modal).find("select[name='type']").val() != undefined){
		type = $(modal).find("select[name='type']").val();
	}
	if (data.type != undefined) {
		data.type = type;
	} else {		
		data["type"] = type;
	}
	
	//use formula setting
	var pathType ="";
	var content = "";
	var formulaDefinitionDetails= [];
	var sourceTR = $(modal).find("tr[type='"+type+"'][name='sourcePath']");
	pathType = $(sourceTR).find("input[name='pathType']").val() != undefined ? $(sourceTR).find("input[name='pathType']").val() : "";
	if (data.sourcePathType != undefined) {
		data.sourcePathType = pathType;
	} else {		
		data["sourcePathType"] = pathType;
	}
	
	content = $(sourceTR).find("input[name='content']").val() != undefined ? $(sourceTR).find("input[name='content']").val() : "";
	if (data.sourcePathContent != undefined) {
		data.sourcePathContent = content;
	} else {		
		data["sourcePathContent"] = content;
	}
	
	if(pathType == "1"){
		formulaDefinitionDetails = ($(sourceTR).find("input[name='formulaDefinitionDetails']").val() == undefined || $(sourceTR).find("input[name='formulaDefinitionDetails']").val() == "" ) 
										? [] : convertToJson($(sourceTR).find("input[name='formulaDefinitionDetails']").val());
	}
	if (data.sourcePathFormulaDetails != undefined) {
		data.sourcePathFormulaDetails = formulaDefinitionDetails;
	} else {		
		data["sourcePathFormulaDetails"] = formulaDefinitionDetails;
	}
	
	var overwriteFlg = false;
//	if($(modal).find("tr[type='"+type+"']").find("input[name='overwriteFlg']").prop("checked") != undefined){
//		overwriteFlg = $(modal).find("tr[type='"+type+"']").find("input[name='overwriteFlg']").prop("checked");
//	}
	if (data.overwriteFlg != undefined) {
		data.overwriteFlg = overwriteFlg;
	} else {		
		data["overwriteFlg"] = overwriteFlg;
	}
	
	var remark = "";
	if($(modal).find("textarea[name='remark']").val() != undefined){
		remark = $(modal).find("textarea[name='remark']").val();
	}
	if (data.remark != undefined) {
		data.remark = remark;
	} else {		
		data["remark"] = remark;
	}

	var lstMergeFileDetails = new Array();
	switch (type) {
	case "0":
		var destinationTR = $(modal).find("tr[type='"+type+"'][name='destinationPath']");
		pathType = $(destinationTR).find("input[name='pathType']").val() != undefined ? $(destinationTR).find("input[name='pathType']").val() : "";
		if (data.destinationPathType != undefined) {
			data.destinationPathType = pathType;
		} else {		
			data["destinationPathType"] = pathType;
		}
		
		content = $(destinationTR).find("input[name='content']").val() != undefined ? $(destinationTR).find("input[name='content']").val() : "";
		if (data.destinationPathContent != undefined) {
			data.destinationPathContent = content;
		} else {		
			data["destinationPathContent"] = content;
		}
		
		if(pathType == "1"){
			formulaDefinitionDetails = ($(destinationTR).find("input[name='formulaDefinitionDetails']").val() == undefined || $(destinationTR).find("input[name='formulaDefinitionDetails']").val() == "" ) 
											? [] : convertToJson($(destinationTR).find("input[name='formulaDefinitionDetails']").val());
		}
		if (data.destinationPathFormulaDetails != undefined) {
			data.destinationPathFormulaDetails = formulaDefinitionDetails;
		} else {		
			data["destinationPathFormulaDetails"] = formulaDefinitionDetails;
		}
		break;
	case "2":
		var newFileTR = $(modal).find("tr[type='"+type+"'][name='newFilename']");
		pathType = $(newFileTR).find("input[name='pathType']").val() != undefined ? $(newFileTR).find("input[name='pathType']").val() : "";
		if (data.newFilenameType != undefined) {
			data.newFilenameType = pathType;
		} else {		
			data["newFilenameType"] = pathType;
		}
		
		content = $(newFileTR).find("input[name='content']").val() != undefined ? $(newFileTR).find("input[name='content']").val() : "";
		if (data.newFilenameContent != undefined) {
			data.newFilenameContent = content;
		} else {		
			data["newFilenameContent"] = content;
		}
		
		if(pathType == "1"){
			formulaDefinitionDetails = ($(newFileTR).find("input[name='formulaDefinitionDetails']").val() == undefined || $(newFileTR).find("input[name='formulaDefinitionDetails']").val() == "" ) 
											? [] : convertToJson($(newFileTR).find("input[name='formulaDefinitionDetails']").val());
		}
		if (data.newFilenameFormulaDetails != undefined) {
			data.newFilenameFormulaDetails = formulaDefinitionDetails;
		} else {		
			data["newFilenameFormulaDetails"] = formulaDefinitionDetails;
		}
		break;
	case "3":
		var destinationTR = $(modal).find("tr[type='"+type+"'][name='destinationPath']");
		pathType = $(destinationTR).find("input[name='pathType']").val() != undefined ? $(destinationTR).find("input[name='pathType']").val() : "";
		if (data.destinationPathType != undefined) {
			data.destinationPathType = pathType;
		} else {		
			data["destinationPathType"] = pathType;
		}
		
		content = $(destinationTR).find("input[name='content']").val() != undefined ? $(destinationTR).find("input[name='content']").val() : "";
		if (data.destinationPathContent != undefined) {
			data.destinationPathContent = content;
		} else {		
			data["destinationPathContent"] = content;
		}
		
		if(pathType == "1"){
			formulaDefinitionDetails = ($(destinationTR).find("input[name='formulaDefinitionDetails']").val() == undefined || $(destinationTR).find("input[name='formulaDefinitionDetails']").val() == "" ) 
											? [] : convertToJson($(destinationTR).find("input[name='formulaDefinitionDetails']").val());
		}
		if (data.destinationPathFormulaDetails != undefined) {
			data.destinationPathFormulaDetails = formulaDefinitionDetails;
		} else {		
			data["destinationPathFormulaDetails"] = formulaDefinitionDetails;
		}
		
		$(modal).find("#tlbMergedFile").find("tbody>tr").each(function(){
			var objMergedFile = new Object();
			objMergedFile.sourcePathContent = $(this).find("input[name='content']").val() == undefined ? null : $(this).find("input[name='content']").val() ;
			objMergedFile.sourcePathType = $(this).find("input[name='pathType']").val() == undefined ? null : $(this).find("input[name='pathType']").val() ;
			
			if (objMergedFile.sourcePathType == "1") {
				objMergedFile.sourcePathFormulaDetails = $(this).find("input[name='formulaDefinitionDetails']").val() == undefined ? null : convertToJson($(this).find("input[name='formulaDefinitionDetails']").val()) ;
			} else {
				objMergedFile.sourcePathFormulaDetails = [];
			}
			
			lstMergeFileDetails.push(objMergedFile);
		});
		
		break;
	default:
		break;
	}
	if (data.lstMergeFileDetails != undefined) {
		data.lstMergeFileDetails = lstMergeFileDetails;
	} else {		
		data["lstMergeFileDetails"] = lstMergeFileDetails;
	}
	
	var validationMessage = $.qp.businessdesign.validaionComponent.validationFileOperationComp($.qp.businessdesign.validaionComponent.DETAIL_CHECK,data,true,thiz);
	if(validationMessage != undefined && validationMessage != ""){
		$.qp.showAlertModal(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("data-original-title",remark);
		$(obj).find("span.component-name").text(label);
		$(modal).modal("hide");
		instance.repaintEverything();
	}
	saveCommonSetting($(obj));
}

function saveModalReadFileSetting(thiz) {
	var modal = BD_MODAL_NAME.READ_FILE;
	$.qp.undoFormatNumericForm( $(modal));
	var table = "#tbl-readfile-assign-parameter";
	var instance = $(modal).data("instance");
	var obj = $(modal).data("target");
	var data = convertToJson($(modal).data("data"));
	
	var label  = "";
	if($(modal).find("input[name='label']").val() != undefined){
		label = $(modal).find("input[name='label']").val();
	}	
	
	if (data.label != undefined) {
		data.label = label;
	} else {		
		data["label"] = label;
	}
	
	//use formula setting
	var pathType ="";
	var content = "";
	var formulaDefinitionDetails= [];
	var sourceTR = $(modal).find("tr[name='sourcePath']");
	
	pathType = $(sourceTR).find("input[name='pathType']").val() != undefined ? $(sourceTR).find("input[name='pathType']").val() : "";
	if (data.sourcePathType != undefined) {
		data.sourcePathType = pathType;
	} else {		
		data["sourcePathType"] = pathType;
	}
	
	content = $(sourceTR).find("input[name='content']").val() != undefined ? $(sourceTR).find("input[name='content']").val() : "";
	if (data.sourcePathContent != undefined) {
		data.sourcePathContent = content;
	} else {		
		data["sourcePathContent"] = content;
	}
	
	if(pathType == "1"){
		formulaDefinitionDetails = ($(sourceTR).find("input[name='formulaDefinitionDetails']").val() == undefined || $(sourceTR).find("input[name='formulaDefinitionDetails']").val() == "" ) 
										? [] : convertToJson($(sourceTR).find("input[name='formulaDefinitionDetails']").val());
	}
	if (data.sourcePathFormulaDetails != undefined) {
		data.sourcePathFormulaDetails = formulaDefinitionDetails;
	} else {		
		data["sourcePathFormulaDetails"] = formulaDefinitionDetails;
	}

	var remark = "";
	if($(modal).find("textarea[name='remark']").val() != undefined){
		remark = $(modal).find("textarea[name='remark']").val();
	}
	if (data.remark != undefined) {
		data.remark = remark;
	} else {		
		data["remark"] = remark;
	}
	
	var fileFormat  = "";
	if($(modal).find("input[name='fileFormat']").val() != undefined){
		fileFormat = $(modal).find("input[name='fileFormat']").val();
	}
	if (data.fileFormat != undefined) {
		data.fileFormat = convertToJson(fileFormat);
	} else {		
		data["fileFormat"] = convertToJson(fileFormat);
	}
	
	var count = 0; 
	var lstImportAssignValues =[];
	$(modal).find(table).find("tbody>tr").each(function(){
		if (count == 0) {
			var targetId = $(this).find("input[name='targetId']").val() == undefined ? null : $(this).find("input[name='targetId']").val();
			if (data.targetId != undefined) {
				data.targetId = targetId;
			} else {		
				data["targetId"] = targetId;
			}
			
			var targetIdAutocomplete = $(this).find("input[name='targetIdAutocomplete']").val() == undefined ? null : $(this).find("input[name='targetIdAutocomplete']").val();
			if (data.targetIdAutocomplete != undefined) {
				data.targetIdAutocomplete = targetIdAutocomplete;
			} else {		
				data["targetIdAutocomplete"] = targetIdAutocomplete;
			}
			
			var targetScope = ($(this).find("input[name='targetId']").attr("parameterScope") == undefined || $(this).find("input[name='targetId']").attr("parameterScope") == "") ? null : $(this).find("input[name='targetId']").attr("parameterScope");
			if (data.targetScope != undefined) {
				data.targetScope = targetScope;
			} else {		
				data["targetScope"] = targetScope;
			}
		} else {
			var objAssign = new Object();
			objAssign.targetId = $(this).find("input[name='targetId']").val() == undefined ? null : $(this).find("input[name='targetId']").val();
			objAssign.targetIdAutocomplete = $(this).find("label[name='targetIdAutocomplete']").val() == undefined ? null : $(this).find("label[name='targetIdAutocomplete']").val();
			objAssign.targetScope = ($(this).find("input[name='targetId']").attr("parameterScope") == undefined || $(this).find("input[name='targetId']").attr("parameterScope") == "") ? null : $(this).find("input[name='targetId']").attr("parameterScope");
			objAssign.columnNo = $(this).find("input[name='columnNo']").val() == undefined ? null : $(this).find("input[name='columnNo']").val();
			objAssign.dataGroup = $(this).attr("data-ar-rgroup") == undefined ? "" : $(this).attr("data-ar-rgroup");
			objAssign.dataType = $(this).find("input[name='dataType']").val() == undefined ? null : $(this).find("input[name='dataType']").val();
			lstImportAssignValues.push(objAssign);
		}
		count++;
	});
	if (data.lstImportAssignValues != undefined) {
		data.lstImportAssignValues = lstImportAssignValues;
	} else {		
		data["lstImportAssignValues"] = lstImportAssignValues;
	}
	
	var validationMessage = $.qp.businessdesign.validaionComponent.validationReadFileComp($.qp.businessdesign.validaionComponent.DETAIL_CHECK,data,true,thiz);
	if(validationMessage != undefined && validationMessage != ""){
		$.qp.showAlertModal(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("data-original-title",remark);
		$(obj).find("span.component-name").text(label);
		$(modal).modal("hide");
		instance.repaintEverything();
	}
	saveCommonSetting($(obj));
}

function saveModalReadFileFormatSetting(thiz){
	var modal = BD_MODAL_NAME.READ_FILE_FORMAT;
	var obj = $(modal).data("target");
	var data = convertToJson($(modal).data("data"));
	
	var fileEncoding = "";
	if($(modal).find("select[name='fileEncoding']").val() != undefined){
		fileEncoding = $(modal).find("select[name='fileEncoding']").val();
	}
	if (data.fileEncoding != undefined) {
		data.fileEncoding = fileEncoding;
	} else {		
		data["fileEncoding"] = fileEncoding;
	}
	
	var lineFeedCharType = 0;
	if($(modal).find("input[name='lineFeedCharType']:checked").val() != undefined){
		lineFeedCharType = $(modal).find("input[name='lineFeedCharType']:checked").val();
	}
	if (data.lineFeedCharType != undefined) {
		data.lineFeedCharType = lineFeedCharType;
	} else {		
		data["lineFeedCharType"] = lineFeedCharType;
	}

	var lineFeedChar = "";
	if($(modal).find("input[name='lineFeedChar']").val() != undefined){
		lineFeedChar = $(modal).find("input[name='lineFeedChar']").val();
	}
	if (data.lineFeedChar != undefined) {
		data.lineFeedChar = lineFeedChar;
	} else {		
		data["lineFeedChar"] = lineFeedChar;
	}
	
	var delimiter = ",";
//	if($(modal).find("input[name='delimiter']").val() != undefined){
//		delimiter = $(modal).find("input[name='delimiter']").val();
//	}
	if (data.delimiter != undefined) {
		data.delimiter = delimiter;
	} else {		
		data["delimiter"] = delimiter;
	}
	
	var encloseCharType = 0;
	if($(modal).find("input[name='encloseCharType']:checked").val() != undefined){
		encloseCharType = $(modal).find("input[name='encloseCharType']:checked").val();
	}
	if (data.encloseCharType != undefined) {
		data.encloseCharType = encloseCharType;
	} else {		
		data["encloseCharType"] = encloseCharType;
	}

	var encloseChar = "";
	if($(modal).find("input[name='encloseChar']").val() != undefined){
		encloseChar = $(modal).find("input[name='encloseChar']").val();
	}
	if (data.encloseChar != undefined) {
		data.encloseChar = encloseChar;
	} else {		
		data["encloseChar"] = encloseChar;
	}
	
	var headLineCount = "";
	if($(modal).find("input[name='headLineCount']").val() != undefined){
		headLineCount = $(modal).find("input[name='headLineCount']").val();
	}
	if (data.headLineCount != undefined) {
		data.headLineCount = headLineCount;
	} else {		
		data["headLineCount"] = headLineCount;
	}
	
	var trailerLineCount = "";
	if($(modal).find("input[name='trailerLineCount']").val() != undefined){
		trailerLineCount = $(modal).find("input[name='trailerLineCount']").val();
	}
	if (data.trailerLineCount != undefined) {
		data.trailerLineCount = trailerLineCount;
	} else {		
		data["trailerLineCount"] = trailerLineCount;
	}

	var overwriteFlg = false;
	if($(modal).find("input[name='overwriteFlg']").prop("checked") != undefined){
		overwriteFlg = $(modal).find("input[name='overwriteFlg']").prop("checked");
	}
	if (data.overwriteFlg != undefined) {
		data.overwriteFlg = overwriteFlg;
	} else {		
		data["overwriteFlg"] = overwriteFlg;
	}

	var value = convertToString(data);

	$(obj).closest(".modal").find("input[name='fileFormat']").val(value);
	$(modal).modal("hide");
	saveCommonSetting($(obj));
}

function onchangeParameterOfAssignObject(event){
	var obj = event.target;
	if(event.item != undefined){
		var type = event.item.output01;
		$(obj).next().attr("parameterScope",type);
		var dataType = event.item.output03;
		$.qp.ar.removeRow({link:obj,removeType:'onlyDescendants'});
		$(obj).closest("tr").find("input[name=dataType]").val(dataType);
	}
	else{
		$(obj).next().attr("parameterScope","");
		$.qp.ar.removeRow({link:obj,removeType:'onlyDescendants'});
		$(obj).closest("tr").find("input[name=dataType]").val("");
	}
	ongetPropetyOfObjectReadFileSet(obj);
}

function saveModalExportFileSetting(thiz) {
	var modal = BD_MODAL_NAME.EXPORT_FILE;
	$.qp.undoFormatNumericForm( $(modal));
	var table = "#tbl-exportfile-assign-parameter";
	var instance = $(modal).data("instance");
	var obj = $(modal).data("target");
	var data = convertToJson($(modal).data("data"));
	
	var label  = "";
	if($(modal).find("input[name='label']").val() != undefined){
		label = $(modal).find("input[name='label']").val();
	}	
	
	if (data.label != undefined) {
		data.label = label;
	} else {		
		data["label"] = label;
	}
	
	//use formula setting
	var pathType ="";
	var content = "";
	var formulaDefinitionDetails= [];
	
	var destinationTR = $(modal).find("tr[name='destinationPath']");
	pathType = $(destinationTR).find("input[name='pathType']").val() != undefined ? $(destinationTR).find("input[name='pathType']").val() : "";
	if (data.destinationPathType != undefined) {
		data.destinationPathType = pathType;
	} else {		
		data["destinationPathType"] = pathType;
	}
	
	content = $(destinationTR).find("input[name='content']").val() != undefined ? $(destinationTR).find("input[name='content']").val() : "";
	if (data.destinationPathContent != undefined) {
		data.destinationPathContent = content;
	} else {		
		data["destinationPathContent"] = content;
	}
	
	if(pathType == "1"){
		formulaDefinitionDetails = ($(destinationTR).find("input[name='formulaDefinitionDetails']").val() == undefined || $(destinationTR).find("input[name='formulaDefinitionDetails']").val() == "" ) 
										? [] : convertToJson($(destinationTR).find("input[name='formulaDefinitionDetails']").val());
	}
	if (data.destinationPathFormulaDetails != undefined) {
		data.destinationPathFormulaDetails = formulaDefinitionDetails;
	} else {		
		data["destinationPathFormulaDetails"] = formulaDefinitionDetails;
	}

	var remark = "";
	if($(modal).find("textarea[name='remark']").val() != undefined){
		remark = $(modal).find("textarea[name='remark']").val();
	}
	if (data.remark != undefined) {
		data.remark = remark;
	} else {		
		data["remark"] = remark;
	}
	
	var fileFormat  = "";
	if($(modal).find("input[name='fileFormat']").val() != undefined){
		fileFormat = $(modal).find("input[name='fileFormat']").val();
	}
	if (data.fileFormat != undefined) {
		data.fileFormat = convertToJson(fileFormat);
	} else {		
		data["fileFormat"] = convertToJson(fileFormat);
	}
	
	var count = 0; 
	var lstExportAssignValues =[];
	$(modal).find(table).find("tbody>tr").each(function(){
		if (count == 0) {
			var parameterId = $(this).find("input[name='parameterId']").val() == undefined ? null : $(this).find("input[name='parameterId']").val();
			if (data.parameterId != undefined) {
				data.parameterId = parameterId;
			} else {		
				data["parameterId"] = parameterId;
			}
			
			var parameterIdAutocomplete = $(this).find("input[name='parameterIdAutocomplete']").val() == undefined ? null : $(this).find("input[name='parameterIdAutocomplete']").val();
			if (data.parameterIdAutocomplete != undefined) {
				data.parameterIdAutocomplete = parameterIdAutocomplete;
			} else {		
				data["parameterIdAutocomplete"] = parameterIdAutocomplete;
			}
			
			var parameterScope = ($(this).find("input[name='parameterId']").attr("parameterScope") == undefined || $(this).find("input[name='parameterId']").attr("parameterScope") == "") ? null : $(this).find("input[name='parameterId']").attr("parameterScope");
			if (data.parameterScope != undefined) {
				data.parameterScope = parameterScope;
			} else {		
				data["parameterScope"] = parameterScope;
			}
		} else {
			var objAssign = new Object();
			
			objAssign.parameterId = $(this).find("input[name='parameterId']").val() == undefined ? null : $(this).find("input[name='parameterId']").val();
			objAssign.parameterIdAutocomplete = $(this).find("label[name='parameterIdAutocomplete']").val() == undefined ? null : $(this).find("label[name='parameterIdAutocomplete']").val();
			objAssign.parameterScope = ($(this).find("input[name='parameterId']").attr("parameterScope") == undefined || $(this).find("input[name='parameterId']").attr("parameterScope") == "") ? null : $(this).find("input[name='parameterId']").attr("parameterScope");
			objAssign.columnNo = $(this).find("input[name='columnNo']").val() == undefined ? null : $(this).find("input[name='columnNo']").val();
			objAssign.dataGroup = $(this).attr("data-ar-rgroup") == undefined ? "" : $(this).attr("data-ar-rgroup");
			objAssign.dataType = $(this).find("input[name='dataType']").val() == undefined ? null : $(this).find("input[name='dataType']").val();
			
			var columnFileFormat  = "";
			if($(this).find("input[name='columnFileFormat']").val() != undefined){
				columnFileFormat = $(this).find("input[name='columnFileFormat']").val();
			}
			if (objAssign.columnFileFormat != undefined) {
				objAssign.columnFileFormat = convertToJson(columnFileFormat);
			} else {		
				objAssign["columnFileFormat"] = convertToJson(columnFileFormat);
			}
			
			lstExportAssignValues.push(objAssign);
		}
		count++;
	});
	if (data.lstExportAssignValues != undefined) {
		data.lstExportAssignValues = lstExportAssignValues;
	} else {		
		data["lstExportAssignValues"] = lstExportAssignValues;
	}
	
	var validationMessage = $.qp.businessdesign.validaionComponent.validationExportFileComp($.qp.businessdesign.validaionComponent.DETAIL_CHECK,data,true,thiz);
	if(validationMessage != undefined && validationMessage != ""){
		$.qp.showAlertModal(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("data-original-title",remark);
		$(obj).find("span.component-name").text(label);
		$(modal).modal("hide");
		instance.repaintEverything();
	}
	saveCommonSetting($(obj));
}

function saveModalExportFileFormatSetting(thiz){
	var modal = BD_MODAL_NAME.EXPORT_FILE_FORMAT;
	var obj = $(modal).data("target");
	var data = convertToJson($(modal).data("data"));
	
	var fileEncoding = "";
	if($(modal).find("select[name='fileEncoding']").val() != undefined){
		fileEncoding = $(modal).find("select[name='fileEncoding']").val();
	}
	if (data.fileEncoding != undefined) {
		data.fileEncoding = fileEncoding;
	} else {		
		data["fileEncoding"] = fileEncoding;
	}
	
	var lineFeedCharType = 0;
	if($(modal).find("input[name='lineFeedCharType']:checked").val() != undefined){
		lineFeedCharType = $(modal).find("input[name='lineFeedCharType']:checked").val();
	}
	if (data.lineFeedCharType != undefined) {
		data.lineFeedCharType = lineFeedCharType;
	} else {		
		data["lineFeedCharType"] = lineFeedCharType;
	}

	var lineFeedChar = "";
	if($(modal).find("input[name='lineFeedChar']").val() != undefined){
		lineFeedChar = $(modal).find("input[name='lineFeedChar']").val();
	}
	if (data.lineFeedChar != undefined) {
		data.lineFeedChar = lineFeedChar;
	} else {		
		data["lineFeedChar"] = lineFeedChar;
	}
	
	var delimiter = ",";
//	if($(modal).find("input[name='delimiter']").val() != undefined){
//		delimiter = $(modal).find("input[name='delimiter']").val();
//	}
	if (data.delimiter != undefined) {
		data.delimiter = delimiter;
	} else {		
		data["delimiter"] = delimiter;
	}
	
	var encloseCharType = 0;
	if($(modal).find("input[name='encloseCharType']:checked").val() != undefined){
		encloseCharType = $(modal).find("input[name='encloseCharType']:checked").val();
	}
	if (data.encloseCharType != undefined) {
		data.encloseCharType = encloseCharType;
	} else {		
		data["encloseCharType"] = encloseCharType;
	}

	var encloseChar = "";
	if($(modal).find("input[name='encloseChar']").val() != undefined){
		encloseChar = $(modal).find("input[name='encloseChar']").val();
	}
	if (data.encloseChar != undefined) {
		data.encloseChar = encloseChar;
	} else {		
		data["encloseChar"] = encloseChar;
	}
	
	var headLineCount = "";
	if($(modal).find("input[name='headLineCount']").val() != undefined){
		headLineCount = $(modal).find("input[name='headLineCount']").val();
	}
	if (data.headLineCount != undefined) {
		data.headLineCount = headLineCount;
	} else {		
		data["headLineCount"] = headLineCount;
	}
	
	var trailerLineCount = "";
	if($(modal).find("input[name='trailerLineCount']").val() != undefined){
		trailerLineCount = $(modal).find("input[name='trailerLineCount']").val();
	}
	if (data.trailerLineCount != undefined) {
		data.trailerLineCount = trailerLineCount;
	} else {		
		data["trailerLineCount"] = trailerLineCount;
	}

	var overwriteFlg = false;
	if($(modal).find("input[name='overwriteFlg']").prop("checked") != undefined){
		overwriteFlg = $(modal).find("input[name='overwriteFlg']").prop("checked");
	}
	if (data.overwriteFlg != undefined) {
		data.overwriteFlg = overwriteFlg;
	} else {		
		data["overwriteFlg"] = overwriteFlg;
	}

	var value = convertToString(data);

	$(obj).closest(".modal").find("input[name='fileFormat']").val(value);
	$(modal).modal("hide");
	saveCommonSetting($(obj));
}

function saveModalExportFileColumnFormatSetting(thiz){
	var modal = BD_MODAL_NAME.EXPORT_FILE_COLUMN_FORMAT;
	var obj = $(modal).data("target");
	var data = convertToJson($(modal).data("data"));
	
	var paddingType = 0;
	if($(modal).find("input[name='paddingType']:checked").val() != undefined){
		paddingType = $(modal).find("input[name='paddingType']:checked").val();
	}
	if (data.paddingType != undefined) {
		data.paddingType = paddingType;
	} else {		
		data["paddingType"] = paddingType;
	}
	
	var paddingCharType = 0;
	if($(modal).find("input[name='paddingCharType']:checked").val() != undefined){
		paddingCharType = $(modal).find("input[name='paddingCharType']:checked").val();
	}
	if (data.paddingCharType != undefined) {
		data.paddingCharType = paddingCharType;
	} else {		
		data["paddingCharType"] = paddingCharType;
	}

	var paddingChar = "";
	if($(modal).find("input[name='paddingChar']").val() != undefined){
		paddingChar = $(modal).find("input[name='paddingChar']").val();
	}
	if (data.paddingChar != undefined) {
		data.paddingChar = paddingChar;
	} else {		
		data["paddingChar"] = paddingChar;
	}

	var specifyByte = "";
	if($(modal).find("input[name='specifyByte']").val() != undefined){
		specifyByte = $(modal).find("input[name='specifyByte']").val();
	}
	if (data.specifyByte != undefined) {
		data.specifyByte = specifyByte;
	} else {		
		data["specifyByte"] = specifyByte;
	}
	
	var columnFormat = "";
	if($(modal).find("select[name='columnFormat']").val() != undefined){
		columnFormat = $(modal).find("select[name='columnFormat']").val();
	}
	if (data.columnFormat != undefined) {
		data.columnFormat = columnFormat;
	} else {		
		data["columnFormat"] = columnFormat;
	}
	
	var trimType = 0;
	if($(modal).find("input[name='trimType']:checked").val() != undefined){
		trimType = $(modal).find("input[name='trimType']:checked").val();
	}
	if (data.trimType != undefined) {
		data.trimType = trimType;
	} else {		
		data["trimType"] = trimType;
	}

	var trimChar = "";
	if($(modal).find("input[name='trimChar']").val() != undefined){
		trimChar = $(modal).find("input[name='trimChar']").val();
	}
	if (data.trimChar != undefined) {
		data.trimChar = trimChar;
	} else {		
		data["trimChar"] = trimChar;
	}
	
	var converter = 0;
	if($(modal).find("input[name='converter']:checked").val() != undefined){
		converter = $(modal).find("input[name='converter']:checked").val();
	}
	if (data.converter != undefined) {
		data.converter = converter;
	} else {		
		data["converter"] = converter;
	}

	var value = convertToString(data);

	$(obj).closest("td").find("input[name='columnFileFormat']").val(value);
	$(modal).modal("hide");
}

function onchangeParameterOfPassObject(event){
	var obj = event.target;
	if(event.item != undefined){
		var type = event.item.output01;
		$(obj).next().attr("parameterScope",type);
		var dataType = event.item.output03;
		$.qp.ar.removeRow({link:obj,removeType:'onlyDescendants'});
		$(obj).closest("tr").find("input[name=dataType]").val(dataType);
	}
	else{
		$(obj).next().attr("parameterScope","");
		$.qp.ar.removeRow({link:obj,removeType:'onlyDescendants'});
		$(obj).closest("tr").find("input[name=dataType]").val("");
	}
	ongetPropetyOfObjectExportFileSet(obj);
}

function findTargetInputOfPathForFileOperation(thiz){
	return $(thiz).closest("tr").find("[name=content]");
}
function findTargetValueOfPathForFileOperation(thiz){
	return $(thiz).closest("tr").find("[name=formulaDefinitionDetails]");
}
function onAfterSaveFormulaOfPathForFileOperation(thiz,formulaDefinitionDetails){
	if(formulaDefinitionDetails.length >0){
		$(thiz).closest("tr").find("[name=content]").attr("readonly",true);
//		$(thiz).closest("tr").find("[name=content]").addClass("qp-bdesign-tr-change");
		$(thiz).closest("tr").find("[name=pathType]").val("1");
	}
}

function onAfterDeleteFormulaOfPathForFileOperation(thiz){
//	$(thiz).closest("tr").find("[name=content]").removeClass("qp-bdesign-tr-change");
	$(thiz).closest("tr").find("[name=content]").attr("readonly",false);
	$(thiz).closest("tr").find("[name=pathType]").val("0");
}

function findTargetInputOfPathForReadFile(thiz){
	return $(thiz).closest("tr").find("[name=content]");
}
function findTargetValueOfPathForReadFile(thiz){
	return $(thiz).closest("tr").find("[name=formulaDefinitionDetails]");
}
function onAfterSaveFormulaOfPathForReadFile(thiz,formulaDefinitionDetails){
	if(formulaDefinitionDetails.length >0){
		$(thiz).closest("tr").find("[name=content]").attr("readonly",true);
//		$(thiz).closest("tr").find("[name=content]").addClass("qp-bdesign-tr-change");
		$(thiz).closest("tr").find("[name=pathType]").val("1");
	}
}

function onAfterDeleteFormulaOfPathForReadFile(thiz){
//	$(thiz).closest("tr").find("[name=content]").removeClass("qp-bdesign-tr-change");
	$(thiz).closest("tr").find("[name=content]").attr("readonly",false);
	$(thiz).closest("tr").find("[name=pathType]").val("0");
}

function ongetPropetyOfObjectReadFileSet(thiz){
	var currentRow = $(thiz).closest("tr");
	var groupIndex = $(thiz).closest("tr").attr("data-ar-rgroupindex");
	var targetId = $(thiz).closest("tr").find("input[name=targetId]").val();
	var type = $(thiz).closest("tr").find("input[name=targetId]").attr("parameterScope");
	var parentCode = $(thiz).closest("tr").find("input[name=targetIdAutocomplete]").val() == undefined ? "" : $(thiz).closest("tr").find("input[name=targetIdAutocomplete]").val();
	
	if(targetId != undefined && targetId != null && targetId.length > 0 && type != undefined && type != ""){
		targetId = targetId.substring(1, targetId.length);
		var tempRow = currentRow;
		switch (type) {
		case "0":
			var sourceData = convertToJson($("form").find("input[name='jsonInputBean']").val());
			for(var i=0;i< sourceData.length; i++){
				if(sourceData[i].parentInputBeanId == targetId){
					var newRow = $.qp.ar.addRow({link:thiz,tableId:'tbl-readfile-assign-parameter',templateId:'tbl-readfile-assign-parameter-template',templateData:{groupId:groupIndex},position:{anchor:tempRow,string:"after"}});
					$(newRow).find("input[name=targetId]").val(BD_PREFIX_SCOPE.INPUTBEAN + sourceData[i].inputBeanId);
					$(newRow).find("label[name=targetIdAutocomplete]").text(parentCode+"."+sourceData[i].inputBeanCode);
					$(newRow).find("input[name=targetId]").attr("parameterScope","0");
					$(newRow).find("input[name=dataType]").val(sourceData[i].dataType);
					tempRow = newRow;
				}
			}
			break;
		case "1":
			var sourceDataOb = convertToJson($("form").find("input[name='jsonObjectDefinition']").val());
			for(var i=0;i< sourceDataOb.length; i++){
				if(sourceDataOb[i].parentObjectDefinitionId == targetId){
					var newRow = $.qp.ar.addRow({link:thiz,tableId:'tbl-readfile-assign-parameter',templateId:'tbl-readfile-assign-parameter-template',templateData:{groupId:groupIndex},position:{anchor:tempRow,string:"after"}});
					$(newRow).find("input[name=targetId]").val(BD_PREFIX_SCOPE.OBJECTDEFINITION + sourceDataOb[i].objectDefinitionId);
					$(newRow).find("label[name=targetIdAutocomplete]").text(parentCode+"."+sourceDataOb[i].objectDefinitionCode);
					$(newRow).find("input[name=targetId]").attr("parameterScope","1");
					$(newRow).find("input[name=dataType]").val(sourceDataOb[i].dataType);
					tempRow = newRow;
				}
			}
			break;
		case "2":
			var sourceData = convertToJson($("form").find("input[name='jsonOutputBean']").val());
			for(var i=0;i< sourceData.length; i++){
				if(sourceData[i].parentOutputBeanId == targetId){
					var newRow = $.qp.ar.addRow({link:thiz,tableId:'tbl-readfile-assign-parameter',templateId:'tbl-readfile-assign-parameter-template',templateData:{groupId:groupIndex},position:{anchor:tempRow,string:"after"}});
					$(newRow).find("input[name=targetId]").val(BD_PREFIX_SCOPE.OUTPUTBEAN + sourceData[i].outputBeanId);
					$(newRow).find("label[name=targetIdAutocomplete]").text(parentCode+"."+sourceData[i].outputBeanCode);
					$(newRow).find("input[name=targetId]").attr("parameterScope","2");
					$(newRow).find("input[name=dataType]").val(sourceData[i].dataType);
					tempRow = newRow;
				}
			}
			break;
		default:
			break;
		}
	}
}

function findTargetInputOfPathForExportFile(thiz){
	return $(thiz).closest("tr").find("[name=content]");
}
function findTargetValueOfPathForExportFile(thiz){
	return $(thiz).closest("tr").find("[name=formulaDefinitionDetails]");
}
function onAfterSaveFormulaOfPathForExportFile(thiz,formulaDefinitionDetails){
	if(formulaDefinitionDetails.length >0){
		$(thiz).closest("tr").find("[name=content]").attr("readonly",true);
//		$(thiz).closest("tr").find("[name=content]").addClass("qp-bdesign-tr-change");
		$(thiz).closest("tr").find("[name=pathType]").val("1");
	}
}

function onAfterDeleteFormulaOfPathForExportFile(thiz){
//	$(thiz).closest("tr").find("[name=content]").removeClass("qp-bdesign-tr-change");
	$(thiz).closest("tr").find("[name=content]").attr("readonly",false);
	$(thiz).closest("tr").find("[name=pathType]").val("0");
}

function ongetPropetyOfObjectExportFileSet(thiz){
	var currentRow = $(thiz).closest("tr");
	var groupIndex = $(thiz).closest("tr").attr("data-ar-rgroupindex");
	var parameterId = $(thiz).closest("tr").find("input[name=parameterId]").val();
	var type = $(thiz).closest("tr").find("input[name=parameterId]").attr("parameterScope");
	var parentCode = $(thiz).closest("tr").find("input[name=parameterIdAutocomplete]").val() == undefined ? "" : $(thiz).closest("tr").find("input[name=parameterIdAutocomplete]").val();
	
	if(parameterId != undefined && parameterId != null && parameterId.length > 0 && type != undefined && type != ""){
		parameterId = parameterId.substring(1, parameterId.length);
		var tempRow = currentRow;
		switch (type) {
		case "0":
			var sourceData = convertToJson($("form").find("input[name='jsonInputBean']").val());
			for(var i=0;i< sourceData.length; i++){
				if(sourceData[i].parentInputBeanId == parameterId){
					var newRow = $.qp.ar.addRow({link:thiz,tableId:'tbl-exportfile-assign-parameter',templateId:'tbl-exportfile-assign-parameter-template',templateData:{groupId:groupIndex},position:{anchor:tempRow,string:"after"}});
					$(newRow).find("input[name=parameterId]").val(BD_PREFIX_SCOPE.INPUTBEAN + sourceData[i].inputBeanId);
					$(newRow).find("label[name=parameterIdAutocomplete]").text(parentCode+"."+sourceData[i].inputBeanCode);
					$(newRow).find("input[name=parameterId]").attr("parameterScope","0");
					$(newRow).find("input[name=dataType]").val(sourceData[i].dataType);
					tempRow = newRow;
				}
			}
			break;
		case "1":
			var sourceDataOb = convertToJson($("form").find("input[name='jsonObjectDefinition']").val());
			for(var i=0;i< sourceDataOb.length; i++){
				if(sourceDataOb[i].parentObjectDefinitionId == parameterId){
					var newRow = $.qp.ar.addRow({link:thiz,tableId:'tbl-exportfile-assign-parameter',templateId:'tbl-exportfile-assign-parameter-template',templateData:{groupId:groupIndex},position:{anchor:tempRow,string:"after"}});
					$(newRow).find("input[name=parameterId]").val(BD_PREFIX_SCOPE.OBJECTDEFINITION + sourceDataOb[i].objectDefinitionId);
					$(newRow).find("label[name=parameterIdAutocomplete]").text(parentCode+"."+sourceDataOb[i].objectDefinitionCode);
					$(newRow).find("input[name=parameterId]").attr("parameterScope","1");
					$(newRow).find("input[name=dataType]").val(sourceDataOb[i].dataType);
					tempRow = newRow;
				}
			}
			break;
		case "2":
			var sourceData = convertToJson($("form").find("input[name='jsonOutputBean']").val());
			for(var i=0;i< sourceData.length; i++){
				if(sourceData[i].parentOutputBeanId == parameterId){
					var newRow = $.qp.ar.addRow({link:thiz,tableId:'tbl-exportfile-assign-parameter',templateId:'tbl-exportfile-assign-parameter-template',templateData:{groupId:groupIndex},position:{anchor:tempRow,string:"after"}});
					$(newRow).find("input[name=parameterId]").val(BD_PREFIX_SCOPE.OUTPUTBEAN + sourceData[i].outputBeanId);
					$(newRow).find("label[name=parameterIdAutocomplete]").text(parentCode+"."+sourceData[i].outputBeanCode);
					$(newRow).find("input[name=parameterId]").attr("parameterScope","2");
					$(newRow).find("input[name=dataType]").val(sourceData[i].dataType);
					tempRow = newRow;
				}
			}
			break;
		default:
			break;
		}
	}
}
function setDataTypeOfColumnBD (event){
	var obj = event.target;
	if(event.item != undefined){
		var dataType = event.item.output02;
		$(obj).next().attr("dataType",dataType);
	}
	else{
		$(obj).next().attr("dataType","");
	}
}
function onchangeTypeOfTransactionType(thiz){
	var type = $(thiz).val();
	$("#tbl-transaction").find("tr[type='0']").hide();
	$("#tbl-transaction").find("tr[type='1']").hide();
	$("#tbl-transaction").find("tr[type='2']").hide();
	$("#tbl-transaction").find("tr[type='3']").hide();
	if(type != undefined){
		$("#tbl-transaction").find("tr[type='"+type+"']").show();
	}else{
		$("#tbl-transaction").find("tr[type='"+type+"']").hide();
	}
}
function saveModalTransactionSetting(thiz) {
	var modal = BD_MODAL_NAME.TRANSACTION;
	var instance = $(modal).data("instance");
	var obj = $(modal).data("target");
	var data = convertToJson($(modal).data("data"));
	
	var label  = "";
	if($(modal).find("input[name='label']").val() != undefined){
		label = $(modal).find("input[name='label']").val();
	}	
	
	if (data.label != undefined) {
		data.label = label;
	} else {		
		data["label"] = label;
	}
	
	var transactionType = "";
	if($(modal).find("input[name='transactionType']:checked").val() != undefined){
		transactionType = $(modal).find("input[name='transactionType']:checked").val();
	}
	if (data.transactionType != undefined) {
		data.transactionType = transactionType;
	} else {		
		data["transactionType"] = transactionType;
	}
	
	var remark = "";
	if($(modal).find("textarea[name='remark']").val() != undefined){
		remark = $(modal).find("textarea[name='remark']").val();
	}
	if (data.remark != undefined) {
		data.remark = remark;
	} else {		
		data["remark"] = remark;
	}
	
	var validationMessage = $.qp.businessdesign.validaionComponent.validationTransactionComp($.qp.businessdesign.validaionComponent.DETAIL_CHECK,data,true,thiz);
	if(validationMessage != undefined && validationMessage != ""){
		$.qp.showAlertModal(validationMessage);
	}else{
		var value = convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("data-original-title",remark);
		$(obj).find("span.component-name").text(label);
		$(modal).modal("hide");
		instance.repaintEverything();
	}
	saveCommonSetting($(obj));
}
function saveCommonSetting(obj){
	$(obj).removeClass("node-error");
}
function updateIndexOfCondtionDetail(table,direction,row){
	if(direction=="add" || direction=="remove"){
		var count = $(table).find("tbody>tr").length;
		$(table).next("table#tbl-elsecondition").find("td[name='indexOfElse']").text(count + 1);
	}
}
function onchangeStatusOfElseCondition(thiz){
	var status = $(thiz).prop("checked");
	$(thiz).closest("tr").find("input[type='text'][name='conditionRemark']").val("");
	$(thiz).closest("tr").find("input[type='text']").attr("disabled", !status);
}
function onChangeParameterTypeOfMessageParameter(thiz){
	var value = $(thiz).val();
	$(thiz).closest("tr").find("td[type]").hide();
	$(thiz).closest("tr").find("span[name=messageLevel]").html("");
	$(thiz).closest("tr").find("input[name=messageLevel]").val("");

	if (value == 0) {
		var templateInput = $('script[id="parameter-content-message-design"]').tmpl();
		$.qp.initialAllPicker($(templateInput));
		if(moduleIdOfBD !=undefined && moduleIdOfBD != null){
			$(templateInput).find("input[name='parameterCodeAutocomplete']").attr("arg03",moduleIdOfBD);
		}
		$(thiz).closest('tr').find("td[type='0']").replaceWith(templateInput);
	}
	
	if (value == 1) {
		var templateInput = $('script[id="parameter-content-value"]').tmpl();
		$.qp.initialAllPicker($(templateInput));
		$(thiz).closest('tr').find("td[type='1']").replaceWith(templateInput);
	}
	
	if (value == 2) {
		var templateInput = $('script[id="parameter-content-variable"]').tmpl();
		$.qp.initialAllPicker($(templateInput));
		$(thiz).closest('tr').find("td[type='2']").replaceWith(templateInput);
	}
	$(thiz).closest("tr").find("td[type='"+value+"']").show();
}
function getLabelOfMappingType(mappingType,logicalDataType){
	var type = "";
	if(!isEmptyQP(mappingType)){
		switch (mappingType.toString()) {
		case "0":
			if(logicalDataType == "0")
				type =  "Autocomplete Submit";
			else
				type =  "Option Submit";
			break;
		case "1":
			if(logicalDataType == "0")
				type =  "Autocomplete Display";
			else
				type =  "Option Display";
			break;
		case "2":
			type = "DataSource";
			break;
		case "3":
			if(logicalDataType == "0")
				type =  "Autocomplete Select";
			else
				type =  "Option Select";
			break;
		case "4":
			type = "From";
			break;
		case "5":
			type = "To";
			break;
		default:
			break;
		}
	}
	return type;
}
function buildSampleContentOfAdvanceComp(thiz){
	var modal = $(thiz).closest(".modal");
	var methodName = $(thiz).val();
	var firstLine = $(modal).find("#firstLine");
	var lastLine = $(modal).find("#lastLine");
	
	var countOutput = $(modal).find("#tbl-advance-outputbean tbody tr").length;
	if(!isEmptyQP(methodName)){
		//get blogic code
		var businessDesignCode = $("input[name='businessLogicCode'").val();
		if(countOutput == 0){
			$(firstLine).text("private "+ "void " + buildMethodNameOfAdvanceNode(businessDesignCode, methodName) +" ("+ capitalizeFirstLetter(methodName) + capitalizeFirstLetter(businessDesignCode) +"Input methodInput) {");
		}else{
			$(firstLine).text("private "+ capitalizeFirstLetter(methodName) + capitalizeFirstLetter(businessDesignCode) +"Output " + buildMethodNameOfAdvanceNode(businessDesignCode, methodName) +" ("+ capitalizeFirstLetter(methodName) + capitalizeFirstLetter(businessDesignCode) +"Input methodInput) {");
		}
		$(lastLine).text("}");
	} else {
		$(firstLine).text("");
		$(lastLine).text("");
	}
}
function onchangeParameterOfBD(event,removeCallBack){
	var obj = event.target;
	var modal = $(obj).closest(".modal");
	var selectedParam = event.item;
	var parentDiv = $(obj).closest(".bd-content");
	var oldContent = $(parentDiv).children();
	var hasArray = false;
	
	if ($(obj).val() != "" && $(obj).attr("selectedValue")) {
		var hidden = $(obj).next();
		$(hidden).attr("parameterscope", "3");
	}
	
	if(selectedParam!= undefined){
		var infor = getFullInformationOfParameter(selectedParam);
		if(!isEmptyQP(infor)){
			for(var i=0; i < infor.length; i++){
				var currentParam = infor[i];
				if(eval(currentParam.output04)){
					hasArray = true;
					break;
				}
			}
			//show-hide get object function
			if(isObjectType( infor[0].output03)){
				$(obj).closest("td").next("td.btn-getobject").children().show();
			}else{
				$(obj).closest("td").next("td.btn-getobject").children().hide();
			}
			
			if(hasArray){
				$(parentDiv).empty();
				var newDiv = $("<div>").addClass("form-group").css({"width": "100%"});
				for (var int = infor.length; int > 0; int--) {
					var currentParam = infor[int -1];
					var code="";
					if(int == infor.length){
						switch (currentParam.output01.toString()) {
						case "0":
							code = "in.";
							break;
						case "1":
							code = "ob.";
							break;
						case "2":
							code = "ou.";
							break;

						default:
							break;
						}
					}
					code += currentParam.output06;
					var arrayFlg = eval(currentParam.output04);
					if(arrayFlg){
						code+="[";
						$(newDiv).append("<label class='control-label'>"+code+"</label>");
						var templateLabel = $(modal).find("#div-parameter-index-template").tmpl();
						$(templateLabel).find("input[name='parameterIndexId']").attr("parameterId",toStringQP(currentParam.output01) + toStringQP(currentParam.output05));
						$(newDiv).append(templateLabel);
						if(int > 1){
							$(newDiv).append("<label class='control-label'>].</label>");
						}else{
							$(newDiv).append("<label class='control-label'>]</label>");
						}	
						addClassFormInline(newDiv);
					}else{
						if(int > 1)
							code +=".";
						$(newDiv).append("<label class='control-label'>"+code+"</label>");
						removeClassFormInline(newDiv);
					}
				}
				var removeButton = $("<span>").attr("title",$.qp.getModuleMessage("sc.businesslogicdesign.0260")).addClass("glyphicon glyphicon-remove pull-right qp-cursor").css({"padding-top": "3px"}).bind( "click", function() {
					if(removeCallBack !=undefined &&  removeCallBack instanceof Function) {
						jQuery.namespace(removeCallBack(this));
					}
					onrevertParameterOfBD(this);
				});
				$(removeButton).data("oldContent", oldContent);
				
				$(newDiv).append(removeButton);
				$.qp.initialAllPicker($(newDiv));
				$(parentDiv).append(newDiv);
				$(parentDiv).attr("pattern","1");
				$(parentDiv).attr("parameterScope",selectedParam.output01);
				$(parentDiv).attr("parameterId",selectedParam.optionValue);
				addClassFormInline(parentDiv);
			}else{
				$(parentDiv).attr("pattern","0");
				$(obj).next().attr("parameterScope",selectedParam.output01);
				removeClassFormInline(parentDiv);
			}
		}
	}
	return hasArray;
}
function getFullInformationOfParameter(infor){
	var lstInformation = [];
	var id;
	if(!isEmptyQP(infor)){
		lstInformation.push(infor);
		while(!isEmptyQP(infor.output02)){
			id = toStringQP(infor.output01) + toStringQP(infor.output02);
			var infor = getInformationOfParameter(id);
			lstInformation.push(infor);
		}
	}
	return lstInformation;
}
function onrevertParameterOfBD(thiz){
	var parentDiv = $(thiz).closest(".bd-content");
	var oldContent = $(thiz).data("oldContent");
	$(parentDiv).empty();
	$(oldContent).find("input").each(function(){
		$(this).val("");
	});
	$.qp.initialAllPicker($(oldContent));
	$(oldContent).find("input[name$='IdAutocomplete']").each(function(){
		$(this).data("ui-autocomplete")._trigger("close");
	});
	$(parentDiv).append($(oldContent));
	$(parentDiv).attr("pattern","0");
	removeClassFormInline(parentDiv);
}

function saveModalLogSetting(thiz) {
	var modal = BD_MODAL_NAME.LOG;
	var instance = $(modal).data("instance");
	var obj = $(modal).data("target");
	var data = convertToJson($(modal).data("data"));
	
	var label  = "";
	if($(modal).find("input[name='label']").val() != undefined){
		label = $(modal).find("input[name='label']").val();
	}	
	
	if (data.label != undefined) {
		data.label = label;
	} else {		
		data["label"] = label;
	}
	
	var level = "";
	if($(modal).find("select[name='level']").val() != undefined){
		level = $(modal).find("select[name='level']").val();
	}
	if (data.level != undefined) {
		data.level = level;
	} else {		
		data["level"] = level;
	}
	
	//use formula setting
	var messageType ="";
	var content = "";
	var formulaDefinitionDetails= [];
	
	messageType = $(modal).find("input[name='messageType']").val() != undefined ? $(modal).find("input[name='messageType']").val() : "";
	if (data.messageType != undefined) {
		data.messageType = messageType;
	} else {		
		data["messageType"] = messageType;
	}
	
	content = $(modal).find("input[name='content']").val() != undefined ? $(modal).find("input[name='content']").val() : "";
	if (data.messageContent != undefined) {
		data.messageContent = content;
	} else {		
		data["messageContent"] = content;
	}
	
	if(messageType == "1"){
		formulaDefinitionDetails = ($(modal).find("input[name='formulaDefinitionDetails']").val() == undefined || $(modal).find("input[name='formulaDefinitionDetails']").val() == "" ) 
										? [] : convertToJson($(modal).find("input[name='formulaDefinitionDetails']").val());
	}
	if (data.messageFormulaDetails != undefined) {
		data.messageFormulaDetails = formulaDefinitionDetails;
	} else {		
		data["messageFormulaDetails"] = formulaDefinitionDetails;
	}

	var remark = "";
	if($(modal).find("textarea[name='remark']").val() != undefined){
		remark = $(modal).find("textarea[name='remark']").val();
	}
	if (data.remark != undefined) {
		data.remark = remark;
	} else {		
		data["remark"] = remark;
	}
	
	var validationMessage = $.qp.businessdesign.validaionComponent.validationLogComp($.qp.businessdesign.validaionComponent.DETAIL_CHECK,data,true,thiz);
	if(validationMessage != undefined && validationMessage != ""){
		$.qp.showAlertModal(validationMessage);
	}else{
		var value = convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("data-original-title",remark);
		$(obj).find("span.component-name").text(label);
		$(modal).modal("hide");
		instance.repaintEverything();
	}
	saveCommonSetting($(obj));
}

function findTargetInputOfPathForLog(thiz){
	return $(thiz).closest("tr").find("[name=content]");
}
function findTargetValueOfPathForLog(thiz){
	return $(thiz).closest("tr").find("[name=formulaDefinitionDetails]");
}
function onAfterSaveFormulaOfPathForLog(thiz,formulaDefinitionDetails){
	if(formulaDefinitionDetails.length >0){
		$(thiz).closest("tr").find("[name=content]").attr("readonly",true);
		$(thiz).closest("tr").find("[name=messageType]").val("1");
	}
}

function onAfterDeleteFormulaOfPathForLog(thiz){
	$(thiz).closest("tr").find("[name=content]").attr("readonly",false);
	$(thiz).closest("tr").find("[name=messageType]").val("0");
}

// Utility comp
function onchangeTypeOfUtility(thiz){
	var type = $(thiz).val();
	// 0: Content
	$("#tbl-utility").find("[type='0']").hide();
	// 1: Index
	$("#tbl-utility").find("[type='1']").hide();
	
	if(type != undefined){
		switch (type) {
			case "0":
			case "2":
			case "6":
			case "7":
				$("#tbl-utility").find("[type='0']").show();
				$("#tbl-utility").find("td[type='0']").attr("colspan",3);
				break;
			case "1":
			case "3":
				$("#tbl-utility").find("[type='0']").show();
				$("#tbl-utility").find("[type='1']").show();
				$("#tbl-utility").find("td[type='0']").attr("colspan",1);
				$("#tbl-utility").find("td[type='1']").attr("colspan",1);
				break;
			case "5":
				$("#tbl-utility").find("[type='1']").show();
				$("#tbl-utility").find("td[type='1']").attr("colspan",3);
				break;
			default:
				$("#tbl-utility").find("[type='0']").hide();
				$("#tbl-utility").find("[type='1']").hide();
		}
	}
}

function onchangeTargetOfUtilitySet(event){
	setParameterTypeOfAutocompleteBD(event);
}

function saveModalUtilitySetting(thiz){
	var modal = BD_MODAL_NAME.UTILITY;
	var instance = $(modal).data("instance");
	var obj = $(modal).data("target");
	var data = convertToJson($(modal).data("data"));
	
	var label  = "";
	if($(modal).find("input[name='label']").val() != undefined){
		label = $(modal).find("input[name='label']").val();
	}	
	if (data.label != undefined) {
		data.label = label;
	} else {		
		data["label"] = label;
	}
	
	var remark  = "";
	if($(modal).find("textarea[name='remark']").val() != undefined){
		remark = $(modal).find("textarea[name='remark']").val();
	}
	if (data.remark != undefined) {
		data.remark = remark;
	} else {		
		data["remark"] = remark;
	}
	
	var type = "";
	if($(modal).find("select[name='type']").val() != undefined){
		type = $(modal).find("select[name='type']").val();
	}
	if (data.type != undefined) {
		data.type = type;
	} else {		
		data["type"] = type;
	}
	
	data.targetId = $(modal).find("input[name='targetId']").val() == undefined ? null : $(modal).find("input[name='targetId']").val();
	data.targetIdAutocomplete = $(modal).find("input[name='targetIdAutocomplete']").val() == undefined ? null : $(modal).find("input[name='targetIdAutocomplete']").val();
	data.targetScope = ($(modal).find("input[name='targetId']").attr("parameterScope") == undefined || $(modal).find("input[name='targetId']").attr("parameterScope") == "") ? null : $(modal).find("input[name='targetId']").attr("parameterScope");
	
	var trBDContentParameter = $(modal).find("div.bd-content[id='parameter']");
	if (trBDContentParameter.closest("td").is(":visible")) {
		var parameterPattern = $(trBDContentParameter).attr("pattern");
		if(parameterPattern=="0"){
			data.parameterId = $(modal).find("input[name='parameterId']").val() == undefined ? null : $(modal).find("input[name='parameterId']").val();
			data.parameterIdAutocomplete = $(modal).find("input[name='parameterIdAutocomplete']").val() == undefined ? null : $(modal).find("input[name='parameterIdAutocomplete']").val();
			data.parameterScope = ($(modal).find("input[name='parameterId']").attr("parameterScope") == undefined || $(modal).find("input[name='parameterId']").attr("parameterScope") == "") ? null : $(modal).find("input[name='parameterId']").attr("parameterScope");
			data.lstParameterIndex = [];
		}else if(parameterPattern=="1"){
			data.parameterId = isEmptyQP($(trBDContentParameter).attr("parameterId")) ? null : $(trBDContentParameter).attr("parameterId");
			data.parameterScope = (isEmptyQP($(trBDContentParameter).attr("parameterScope"))) ? null : $(trBDContentParameter).attr("parameterScope");
			var lstParameterIndex =[];
			$(trBDContentParameter).find("input[name='parameterIndexId']").each(function (){
				var item = new Object();
				var parameterIndexId = $(this).val() == undefined ? null : $(this).val();
				var parameterIndexIdAutocomplete = $(this).prev().val() == undefined ? null : $(this).prev().val();
				var parameterIndexType = ($(this).attr("indextype") == undefined || $(this).attr("indextype") == "") ? "3" : $(this).attr("indextype");
				var parameterId = ($(this).attr("parameterId") == undefined || $(this).attr("parameterId") == "") ? null : $(this).attr("parameterId");
				item.parameterIndexId = parameterIndexId;
				item.parameterIndexIdAutocomplete = parameterIndexIdAutocomplete;
				item.parameterIndexType = parameterIndexType;
				item.parameterId = parameterId;
				if(!isEmptyQP(parameterIndexId))
					lstParameterIndex.push(item);
			});
			data.lstParameterIndex = lstParameterIndex;
		}
	} else {
		data.parameterId = null;
		data.parameterIdAutocomplete = null;
		data.parameterScope = null;
		data.lstParameterIndex = [];
	}

	var trBDContentIndex = $(modal).find("div.bd-content[id='index']");
	if (trBDContentIndex.closest("td").is(":visible")) {
		var indexPattern = $(trBDContentIndex).attr("pattern");
		if (indexPattern=="0"){
			data.indexId = $(modal).find("input[name='indexId']").val() == undefined ? null : $(modal).find("input[name='indexId']").val();
			data.indexIdAutocomplete = $(modal).find("input[name='indexIdAutocomplete']").val() == undefined ? null : $(modal).find("input[name='indexIdAutocomplete']").val();
			data.indexScope = ($(modal).find("input[name='indexId']").attr("parameterScope") == undefined || $(modal).find("input[name='indexId']").attr("parameterScope") == "") ? null : $(modal).find("input[name='indexId']").attr("parameterScope");
			data.lstIndex = [];
		} else if(indexPattern=="1"){
			data.indexId = isEmptyQP($(trBDContentIndex).attr("parameterId")) ? null : $(trBDContentIndex).attr("parameterId");
			data.indexScope = (isEmptyQP($(trBDContentIndex).attr("parameterScope"))) ? null : $(trBDContentIndex).attr("parameterScope");
			var lstIndex =[];
			$(trBDContentIndex).find("input[name='parameterIndexId']").each(function (){
				var item = new Object();
				var parameterIndexId = $(this).val() == undefined ? null : $(this).val();
				var parameterIndexIdAutocomplete = $(this).prev().val() == undefined ? null : $(this).prev().val();
				var parameterIndexType = ($(this).attr("indextype") == undefined || $(this).attr("indextype") == "") ? "3" : $(this).attr("indextype");
				var parameterId = ($(this).attr("parameterId") == undefined || $(this).attr("parameterId") == "") ? null : $(this).attr("parameterId");
				item.parameterIndexId = parameterIndexId;
				item.parameterIndexIdAutocomplete = parameterIndexIdAutocomplete;
				item.parameterIndexType = parameterIndexType;
				item.parameterId = parameterId;
				if(!isEmptyQP(parameterIndexId))
					lstIndex.push(item);
			});
			data.lstIndex = lstIndex;
		}
	} else {
		data.indexId = null;
		data.indexIdAutocomplete = null;
		data.indexScope = null;
		data.lstIndex = [];
	}
	
	var validationMessage = "";
	validationMessage = $.qp.businessdesign.validaionComponent.validationUtilityComp($.qp.businessdesign.validaionComponent.DETAIL_CHECK,data,true,thiz);
	if(validationMessage != undefined && validationMessage != ""){
		$.qp.showAlertModal(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("data-original-title",remark);
		$(obj).find("span.component-name").text(label);
		$(modal).modal("hide");
		instance.repaintEverything();
	}
	saveCommonSetting($(obj));
}

function findTargetInputOfPathForEmail(thiz){
	return $(thiz).closest("tr").find("[name=content]");
}
function findTargetValueOfPathForEmail(thiz){
	return $(thiz).closest("tr").find("[name=formulaDefinitionDetails]");
}
function onAfterSaveFormulaOfPathForEmail(thiz,formulaDefinitionDetails){
	if(formulaDefinitionDetails.length >0){
		$(thiz).closest("tr").find("[name=content]").attr("readonly",true);
		$(thiz).closest("tr").find("[name=type]").val("1");
	}
}
function onAfterDeleteFormulaOfPathForEmail(thiz){
	$(thiz).closest("tr").find("[name=content]").attr("readonly",false);
	$(thiz).closest("tr").find("[name=type]").val("0");
}

function saveModalEmailSetting(thiz){
	var modal = BD_MODAL_NAME.EMAIL;
	var instance = $(modal).data("instance");
	var obj = $(modal).data("target");
	var data = convertToJson($(modal).data("data"));
	
	var label  = "";
	if($(modal).find("input[name='label']").val() != undefined){
		label = $(modal).find("input[name='label']").val();
	}	
	
	if (data.label != undefined) {
		data.label = label;
	} else {		
		data["label"] = label;
	}
	
	var remark = "";
	if($(modal).find("textarea[name='remark']").val() != undefined){
		remark = $(modal).find("textarea[name='remark']").val();
	}
	if (data.remark != undefined) {
		data.remark = remark;
	} else {		
		data["remark"] = remark;
	}
	
	var priorityType = "";
	if($(modal).find("select[name='priorityType']").val() != undefined){
		priorityType = $(modal).find("select[name='priorityType']").val();
	}
	if (data.priorityType != undefined) {
		data.priorityType = priorityType;
	} else {		
		data["priorityType"] = priorityType;
	}
	
	var emailRecipients = new Array();
	$(modal).find("#tlbTo").find("tbody>tr").each(function(){
		var recipient = new Object();
		recipient.recipientContent = $(this).find("input[name='content']").val() == undefined ? null : $(this).find("input[name='content']").val() ;
		recipient.recipientType = $(this).find("input[name='type']").val() == undefined ? null : $(this).find("input[name='type']").val() ;
		recipient.type = 0;
		
		if (recipient.recipientType == "1") {
			recipient.recipientFormulaDetails = $(this).find("input[name='formulaDefinitionDetails']").val() == undefined ? null : convertToJson($(this).find("input[name='formulaDefinitionDetails']").val()) ;
		} else {
			recipient.recipientFormulaDetails = [];
		}
		
		if (recipient.recipientContent != null && recipient.recipientContent.trim() != "") {
			emailRecipients.push(recipient);	
		}
	});
	
	$(modal).find("#tlbCC").find("tbody>tr").each(function(){
		var recipient = new Object();
		recipient.recipientContent = $(this).find("input[name='content']").val() == undefined ? null : $(this).find("input[name='content']").val() ;
		recipient.recipientType = $(this).find("input[name='type']").val() == undefined ? null : $(this).find("input[name='type']").val() ;
		recipient.type = 1;
		
		if (recipient.recipientType == "1") {
			recipient.recipientFormulaDetails = $(this).find("input[name='formulaDefinitionDetails']").val() == undefined ? null : convertToJson($(this).find("input[name='formulaDefinitionDetails']").val()) ;
		} else {
			recipient.recipientFormulaDetails = [];
		}

		if (recipient.recipientContent != null && recipient.recipientContent.trim() != "") {
			emailRecipients.push(recipient);	
		}
	});

	$(modal).find("#tlbBCC").find("tbody>tr").each(function(){
		var recipient = new Object();
		recipient.recipientContent = $(this).find("input[name='content']").val() == undefined ? null : $(this).find("input[name='content']").val() ;
		recipient.recipientType = $(this).find("input[name='type']").val() == undefined ? null : $(this).find("input[name='type']").val() ;
		recipient.type = 2;
		
		if (recipient.recipientType == "1") {
			recipient.recipientFormulaDetails = $(this).find("input[name='formulaDefinitionDetails']").val() == undefined ? null : convertToJson($(this).find("input[name='formulaDefinitionDetails']").val()) ;
		} else {
			recipient.recipientFormulaDetails = [];
		}
		
		if (recipient.recipientContent != null && recipient.recipientContent.trim() != "") {
			emailRecipients.push(recipient);	
		}
	});
	
	if (data.emailRecipients != undefined) {
		data.emailRecipients = emailRecipients;
	} else {		
		data["emailRecipients"] = emailRecipients;
	}

	//use formula setting
	var pathType ="";
	var content = "";
	var formulaDefinitionDetails= [];
	var trSubject = $(modal).find("tr[id='subject']");
	subjectType = $(trSubject).find("input[name='type']").val() != undefined ? $(trSubject).find("input[name='type']").val() : "";
	if (data.subjectType != undefined) {
		data.subjectType = subjectType;
	} else {		
		data["subjectType"] = subjectType;
	}
	
	subjectContent = $(trSubject).find("input[name='content']").val() != undefined ? $(trSubject).find("input[name='content']").val() : "";
	if (data.subjectContent != undefined) {
		data.subjectContent = subjectContent;
	} else {		
		data["subjectContent"] = subjectContent;
	}
	
	if(subjectType == "1"){
		formulaDefinitionDetails = ($(trSubject).find("input[name='formulaDefinitionDetails']").val() == undefined || $(trSubject).find("input[name='formulaDefinitionDetails']").val() == "" ) 
										? [] : convertToJson($(trSubject).find("input[name='formulaDefinitionDetails']").val());
	}
	if (data.subjectFormulaDetails != undefined) {
		data.subjectFormulaDetails = formulaDefinitionDetails;
	} else {		
		data["subjectFormulaDetails"] = formulaDefinitionDetails;
	}

	var emailContent = new Object;
	if($(modal).find("textarea[id='editor-content']").val() != undefined){
		emailContent.content = $(modal).find("textarea[id='editor-content']").val();
	}
	
	if (data.emailContent != undefined) {
		data.emailContent = emailContent;
	} else {		
		data["emailContent"] = emailContent;
	}
	
	var validationMessage = $.qp.businessdesign.validaionComponent.validationEmailComp($.qp.businessdesign.validaionComponent.DETAIL_CHECK,data,true,thiz);
	if(validationMessage != undefined && validationMessage != ""){
		$.qp.showAlertModal(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("data-original-title",remark);
		$(obj).find("span.component-name").text(label);
		$(modal).modal("hide");
		instance.repaintEverything();
	}
	saveCommonSetting($(obj));
}

function onchangeTargetOfDownloadSet(event){
	setParameterTypeOfAutocompleteBD(event);
}

function findTargetInputOfPathForDownload(thiz){
	return $(thiz).closest("tr").find("[name=content]");
}
function findTargetValueOfPathForDownload(thiz){
	return $(thiz).closest("tr").find("[name=formulaDefinitionDetails]");
}
function onAfterSaveFormulaOfPathForDownload(thiz,formulaDefinitionDetails){
	if(formulaDefinitionDetails.length >0){
		$(thiz).closest("tr").find("[name=content]").attr("readonly",true);
		$(thiz).closest("tr").find("[name=type]").val("1");
	}
}
function onAfterDeleteFormulaOfPathForDownload(thiz){
	$(thiz).closest("tr").find("[name=content]").attr("readonly",false);
	$(thiz).closest("tr").find("[name=type]").val("0");
}

function saveModalDownloadFile(thiz){
	var modal = BD_MODAL_NAME.DOWNLOAD_FILE;
	var instance = $(modal).data("instance");
	var obj = $(modal).data("target");
	var data = convertToJson($(modal).data("data"));
	
	var label  = "";
	if($(modal).find("input[name='label']").val() != undefined){
		label = $(modal).find("input[name='label']").val();
	}	
	
	if (data.label != undefined) {
		data.label = label;
	} else {		
		data["label"] = label;
	}
	
	var remark = "";
	if($(modal).find("textarea[name='remark']").val() != undefined){
		remark = $(modal).find("textarea[name='remark']").val();
	}
	if (data.remark != undefined) {
		data.remark = remark;
	} else {		
		data["remark"] = remark;
	}
	
	data.parameterId = $(modal).find("input[name='parameterId']").val() == undefined ? null : $(modal).find("input[name='parameterId']").val();
	data.parameterIdAutocomplete = $(modal).find("input[name='parameterIdAutocomplete']").val() == undefined ? null : $(modal).find("input[name='parameterIdAutocomplete']").val();
	data.parameterScope = ($(modal).find("input[name='parameterId']").attr("parameterScope") == undefined || $(modal).find("input[name='parameterId']").attr("parameterScope") == "") ? null : $(modal).find("input[name='parameterId']").attr("parameterScope");
	
	//use formula setting
	var pathType ="";
	var content = "";
	var formulaDefinitionDetails= [];
	var trFileName = $(modal).find("tr[id='fileName']");
	fileNameType = $(trFileName).find("input[name='type']").val() != undefined ? $(trFileName).find("input[name='type']").val() : "";
	if (data.fileNameType != undefined) {
		data.fileNameType = fileNameType;
	} else {		
		data["fileNameType"] = fileNameType;
	}
	
	fileNameContent = $(trFileName).find("input[name='content']").val() != undefined ? $(trFileName).find("input[name='content']").val() : "";
	if (data.fileNameContent != undefined) {
		data.fileNameContent = fileNameContent;
	} else {		
		data["fileNameContent"] = fileNameContent;
	}
	
	if(fileNameType == "1"){
		formulaDefinitionDetails = ($(trFileName).find("input[name='formulaDefinitionDetails']").val() == undefined || $(trFileName).find("input[name='formulaDefinitionDetails']").val() == "" ) 
										? [] : convertToJson($(trFileName).find("input[name='formulaDefinitionDetails']").val());
	}
	if (data.fileNameFormulaDetails != undefined) {
		data.fileNameFormulaDetails = formulaDefinitionDetails;
	} else {		
		data["fileNameFormulaDetails"] = formulaDefinitionDetails;
	}
	
	var validationMessage = $.qp.businessdesign.validaionComponent.validationDownloadFileComp($.qp.businessdesign.validaionComponent.DETAIL_CHECK,data,true,thiz);
	if(validationMessage != undefined && validationMessage != ""){
		$.qp.showAlertModal(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("data-original-title",remark);
		$(obj).find("span.component-name").text(label);
		$(modal).modal("hide");
		instance.repaintEverything();
	}
	saveCommonSetting($(obj));
}
function isObjectType(dataType){
	var objectFlg = false;
	if(dataType != undefined && dataType != null){
		switch (dataType.toString()) {
		case "0":
		case "14":
		case "16":
		case "17":
			objectFlg = true;
			break;
		default:
			objectFlg = false;
			break;
		}
	}
	return objectFlg;
}
function onremoveTargetCallbackOfAssignSet(thiz){
	var currentRow = $(thiz).closest("tr");
	var data_ar_rgroup = $(currentRow).attr("data-ar-rgroup");
	$(currentRow).nextUntil($(currentRow).nextAll("tr[data-ar-rgroup='"+data_ar_rgroup+"']")).each(function (){
		 $(this).remove();
	 });
	$(currentRow).find("td.btn-getobject").children().hide();
}
function onClickChooseParamMsg(thiz){
	var messageString;
	messageString = $(thiz).closest("tr").find("input[name='messageCodeAutocomplete']").val();
	return messageString;
}
function openRegisterNewNavigator(type){
	var url;
	if(type == 0){
		url = CONTEXT_PATH+"/screendesign/register";
	} else {
		url = CONTEXT_PATH+"/businessdesign/register?blogicType=0";
	}
	var win = window.open(url, '_blank');
}

function saveModalExceptionSetting(thiz) {
	var modal = BD_MODAL_NAME.EXCEPTION;
	var table = "#tbl-exception-inputbean";
	
	var obj = $(modal).data("target");
	var instance = $(modal).data("instance");
	var data = convertToJson($(modal).data("data"));
	var label  = "";
	if($(modal).find("input[name='label']").val() != undefined){
		label = $(modal).find("input[name='label']").val();
	}	
	if (data.label != undefined) {
		data.label = label;
	} else {		
		data["label"] = label;
	}
	
	var moduleId = null;
	var moduleIdAutocomplete = null;
	if($(modal).find("input[name='moduleId']").val() != undefined && $(modal).find("input[name='moduleId']").val() != null && $(modal).find("input[name='moduleId']").val().length >0){
		moduleId = $(modal).find("input[name='moduleId']").val();
		moduleIdAutocomplete = $(modal).find("input[name='moduleIdAutocomplete']").val();
	}
	if (data.moduleId != undefined) {
		data.moduleId = moduleId;
	} else {		
		data["moduleId"] = moduleId;
	}
	
	if (data.moduleIdAutocomplete != undefined) {
		data.moduleIdAutocomplete = moduleIdAutocomplete;
	} else {		
		data["moduleIdAutocomplete"] = moduleIdAutocomplete;
	}
	
	var remark  = "";
	if($(modal).find("textarea[name='remark']").val() != undefined){
		remark = $(modal).find("textarea[name='remark']").val();
	}
	if (data.remark != undefined) {
		data.remark = remark;
	} else {		
		data["remark"] = remark;
	}
	
	var exceptionToIdAutocomplete  = "";
	if($(modal).find("input[name='exceptionToIdAutocomplete']").val() != undefined){
		exceptionToIdAutocomplete = $(modal).find("input[name='exceptionToIdAutocomplete']").val();
	}
	if (data.exceptionToIdAutocomplete != undefined) {
		data.exceptionToIdAutocomplete = exceptionToIdAutocomplete;
	} else {		
		data["exceptionToIdAutocomplete"] = exceptionToIdAutocomplete;
	}

	var exceptionToName  = "";
	if($(modal).find("input[name='exceptionToIdAutocomplete']").attr("exceptionToName") != undefined){
		exceptionToName = $(modal).find("input[name='exceptionToIdAutocomplete']").attr("exceptionToName");
	}
	if (data.exceptionToName != undefined) {
		data.exceptionToName = exceptionToName;
	} else {		
		data["exceptionToName"] = exceptionToName;
	}
	
	var exceptionToType  = "";
	if($(modal).find("input[name='exceptionToType']").val() != undefined){
		exceptionToType = $(modal).find("input[name='exceptionToType']:checked").val();
	}	
	if (data.exceptionToType != undefined) {
		data.exceptionToType = exceptionToType;
	} else {		
		data["exceptionToType"] = exceptionToType;
	}
	
	var exceptionToId  = "";
	if (exceptionToType == 2) {
		exceptionToId = $(modal).find("select[name='exceptionToId']").val();
	} else {
		exceptionToId = $(modal).find("input[name='exceptionToId']").val();
	}
	
	if (data.exceptionToId != undefined) {
		data.exceptionToId = exceptionToId;
	} else {		
		data["exceptionToId"] = exceptionToId;
	}
	
	if (data.exceptionToIdRefer != undefined) {
		data.exceptionToIdRefer = exceptionToId;
	} else {		
		data["exceptionToIdRefer"] = exceptionToId;
	}
	
	var transitionType  = "";
	if($(modal).find("input[name='transitionType']:checked").val() != undefined){
		transitionType = $(modal).find("input[name='transitionType']:checked").val();
	}	
	if (data.transitionType != undefined) {
		data.transitionType = transitionType;
	} else {		
		data["transitionType"] = transitionType;
	}

	var type  = "";
	if($(modal).find("select[name='type']").val() != undefined){
		type = $(modal).find("select[name='type']").val();
	}
	if (data.type != undefined) {
		data.type = "err";
	} else {		
		data["type"] = "err";
	}
	
	var parameterInputBeans = new Array();
	$(modal).find("#tbl-exception-inputbean").find("tbody>tr").each(function(){
		var objInputBean = new Object();
		objInputBean.inputBeanId = $(this).find("input[name='inputBeanId']").val() == undefined ? null : $(this).find("input[name='inputBeanId']").val();
		objInputBean.inputBeanName = $(this).find("input[name='inputBeanName']").val() == undefined ? null : $(this).find("input[name='inputBeanName']").val() ;
		objInputBean.inputBeanCode = $(this).find("label[name='inputBeanCode']").text() == undefined ? null : $(this).find("label[name='inputBeanCode']").text();
		objInputBean.dataType = $(this).find("input[name='dataType']").val() == undefined ? null : $(this).find("input[name='dataType']").val();
		objInputBean.arrayFlg = $(this).find("input[name='arrayFlg']").val() == undefined ? null : eval($(this).find("input[name='arrayFlg']").val());
		
//		objInputBean.parameterId = ($(this).find("input[name='parameterId']").val() == undefined || $(this).find("input[name='parameterId']").val() == "" ) ? null : $(this).find("input[name='parameterId']").val();
//		objInputBean.parameterIdAutocomplete = ($(this).find("input[name='parameterIdAutocomplete']").val() == undefined  || $(this).find("input[name='parameterIdAutocomplete']").val() == "" )? null : $(this).find("input[name='parameterIdAutocomplete']").val();
//		var parameterScope = ($(this).find("input[name='parameterId']").attr("parameterScope") == undefined || $(this).find("input[name='parameterId']").attr("parameterScope") == "") ? null : $(this).find("input[name='parameterId']").attr("parameterScope");
//		objInputBean.parameterScope = parameterScope;
		
		var parameterPattern = $(this).find("div.bd-content[name='parameter']").attr("pattern");
		if(parameterPattern=="0"){
			objInputBean.parameterId = $(this).find("input[name='parameterId']").val() == undefined ? null : $(this).find("input[name='parameterId']").val();
			objInputBean.parameterIdAutocomplete = $(this).find("input[name='parameterIdAutocomplete']").val() == undefined ? null : $(this).find("input[name='parameterIdAutocomplete']").val();
			objInputBean.parameterScope = ($(this).find("input[name='parameterId']").attr("parameterScope") == undefined || $(this).find("input[name='parameterId']").attr("parameterScope") == "") ? null : $(this).find("input[name='parameterId']").attr("parameterScope");
			objInputBean.lstParameterIndex = [];
		}else if(parameterPattern=="1"){
			objInputBean.parameterId = isEmptyQP($(this).find("div.bd-content[name='parameter']").attr("parameterId")) ? null : $(this).find("div.bd-content[name='parameter']").attr("parameterId");
			objInputBean.parameterScope = (isEmptyQP($(this).find("div.bd-content[name='parameter']").attr("parameterScope"))) ? null : $(this).find("div.bd-content[name='parameter']").attr("parameterScope");
			var lstParameterIndex =[];
			$(this).find("div.bd-content[name='parameter']").find("input[name='parameterIndexId']").each(function (){
				var item = new Object();
				var parameterIndexId = $(this).val() == undefined ? null : $(this).val();
				var parameterIndexIdAutocomplete = $(this).prev().val() == undefined ? null : $(this).prev().val();
				var parameterIndexType = ($(this).attr("indextype") == undefined || $(this).attr("indextype") == "") ? "3" : $(this).attr("indextype");
				var parameterId = ($(this).attr("parameterId") == undefined || $(this).attr("parameterId") == "") ? null : $(this).attr("parameterId");
				item.parameterIndexId = parameterIndexId;
				item.parameterIndexIdAutocomplete = parameterIndexIdAutocomplete;
				item.parameterIndexType = parameterIndexType;
				item.parameterId = parameterId;
				if(!isEmptyQP(parameterIndexId))
					lstParameterIndex.push(item);
			});
			objInputBean.lstParameterIndex = lstParameterIndex;
		}
		
		//for impact change
		objInputBean.impactStatus = $(this).find("input[name='inputBeanId']").attr("impactStatus") == undefined ? "" : $(this).find("input[name='inputBeanId']").attr("impactStatus");
		objInputBean.dataTypeRefer = objInputBean.dataType;
		objInputBean.arrayFlgRefer = objInputBean.arrayFlg;
		parameterInputBeans.push(objInputBean);
	});
	if (data.parameterInputBeans != undefined) {
		data.parameterInputBeans = parameterInputBeans;
	} else {		
		data["parameterInputBeans"] = parameterInputBeans;
	}
	
	var validationMessage = $.qp.businessdesign.validaionComponent.validationExceptionComp($.qp.businessdesign.validaionComponent.DETAIL_CHECK,data,true,thiz);
	if(validationMessage != undefined && validationMessage != ""){
		$.qp.showAlertModal(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("data-original-title",remark);
		$(obj).find("span.component-name").text(label);
		$(modal).modal("hide");
		instance.repaintEverything();
	}
	saveCommonSetting($(obj));
}

function onChangeExceptionType(thiz){
	var type = $(thiz).val();
	var sqlId = "getAutocompleteBusinessLogicForExceptionBD";
	
	$(thiz).closest("table").find("input[name=moduleIdAutocomplete]").attr('disabled', false);
	$(thiz).closest("table").find("input[name=exceptionToIdAutocomplete]").attr('disabled', false);
	$(thiz).closest("table").find("input[name=exceptionToIdAutocomplete]").val("");
	$(thiz).closest("table").find("input[name=exceptionToId]").val("");
	
	$(thiz).closest(".modal").find("#tbl-exception-inputbean").find("tbody").empty();
	

	$(thiz).closest("table").find("input[name=transitionType][value=0]").parent().hide();
	$(thiz).closest("table").find("input[name=transitionType][value=1]").parent().hide();
	$(thiz).closest("table").find("a[name='registerNavigatorLink']").show();
	switch (type) {
	case "0":
		$(thiz).closest("table").find("[name=tranType]").hide();
		$(thiz).closest("table").find("td[name=exceptionToType]").attr("colspan", 3);
		$(thiz).closest("table").find("#trModule").hide();
//		sqlId = "getAutocompleteScreenDesignForExceptionNode";
//		$(thiz).closest("table").find("input[name=transitionType][value=0]").prop("checked",true);
//		$(thiz).closest("table").find("a[name='registerNavigatorLink']").attr("onClick","openRegisterNewNavigator(0)");
		break;
	case "1":
		$(thiz).closest("table").find("td[name=exceptionToType]").attr("colspan", 1);
		$(thiz).closest("table").find("[name=tranType]").show();
		$(thiz).closest("table").find("#trModule").show();
		$(thiz).closest("table").find("#tdTypeCommon").hide();
		$(thiz).closest("table").find("#tdTypeBlogic").show();
		$(thiz).closest("table").find("input[name=transitionType][value=0]").parent().show();
		$(thiz).closest("table").find("input[name=transitionType][value=1]").parent().show();
		$(thiz).closest("table").find("a[name='registerNavigatorLink']").attr("onClick","openRegisterNewNavigator(1)");
		break;
	case "2":
		$(thiz).closest("table").find("td[name=exceptionToType]").attr("colspan", 1);
		$(thiz).closest("table").find("[name=tranType]").show();
		$(thiz).closest("table").find("#trModule").show();
		$(thiz).closest("table").find("#tdTypeCommon").show();
		$(thiz).closest("table").find("#tdTypeBlogic").hide();
		sqlId = "getAutocompleteScreenDesignForExceptionNode";
		$(thiz).closest("table").find("input[name=transitionType][value=0]").parent().show();
		$(thiz).closest("table").find("input[name=transitionType][value=0]").prop("checked",true);
		
		//disable
		$(thiz).closest("table").find("input[name=moduleIdAutocomplete]").val("");
		$(thiz).closest("table").find("input[name=moduleIdAutocomplete]").attr('disabled', true);
		$(thiz).closest("table").find("input[name=moduleId]").val("");
		
		$(thiz).closest("table").find("input[name=exceptionToIdAutocomplete]").attr('disabled', true);
		$(thiz).closest("table").find("input[name=exceptionToIdAutocomplete]").val("Delete successful");
		$(thiz).closest("table").find("input[name=exceptionToId]").val("-1");
		$(thiz).closest("table").find("a[name='registerNavigatorLink']").hide();
		
		break;
	default:
		break;
	}
	$(thiz).closest("table").find("input[name=exceptionToIdAutocomplete]").attr("selectsqlid",sqlId);
	$(thiz).closest("table").find("input[name=exceptionToIdAutocomplete]").data("ui-autocomplete")._trigger("close");
	$(thiz).closest("table").find("input[name=moduleIdAutocomplete]").data("ui-autocomplete")._trigger("close");
}
function onchangeModuleOfException(event) {
	var obj = event.target;
	var modal = $(obj).closest(".modal");
	$(obj).attr("exceptionToName","");
	$(obj).closest("td").removeClass("qp-bdesign-tr-remove");
	var table = "#tbl-exception-inputbean";
	$(modal).find(table).find("tbody").empty();
	$(modal).find("input[name='exceptionToId']").val("");
	$(modal).find("input[name='exceptionToIdAutocomplete']").val("");
	var selectedModule = "";
	if(event.item != undefined && event.item.optionValue != "") {
		selectedModule = event.item.optionValue;
	}
	$(modal).find("input[name='exceptionToIdAutocomplete']").attr("arg03",selectedModule);
	$.qp.initialAllPicker($(modal));
	$(modal).find("input[name$='IdAutocomplete']").each(function(){
		$(this).data("ui-autocomplete")._trigger("close");
	});
}
function onchangeNavigatorOfException(event){
	var obj = event.target;
	var modal = $(obj).closest(".modal");
	var table = "#tbl-exception-inputbean";
	$(obj).attr("exceptionToName","");
	$(obj).closest("td").removeClass("qp-bdesign-tr-remove");
	$(modal).find(table).find("tbody").empty();
	if(event.item != undefined){
		if(event.item.optionValue != ""){
			$(obj).attr("exceptionToName",event.item.output01);
			
			//check exception type
			var exceptionToType = $(modal).find(":radio[name='exceptionToType']:checked").val();
			if(exceptionToType == "1"){
				var url = CONTEXT_PATH + "/businessdesign/getInputBeanOfBusinessLogic?businessDesignId="+event.item.optionValue+"&r="+Math.random();
				var data = $.qp.getData(url);
				//input bean
				for(var i=0;i<data.lstInputBean.length;i++){
					var currentInput = data.lstInputBean[i];
					var tableIndex = (currentInput.tableIndex == null || currentInput.tableIndex == undefined) ? "" : currentInput.tableIndex;
					currentInput.tableIndex = tableIndex;
					var dataTypeStr = CL_BD_DATATYPE[currentInput.dataType];
					if(eval(currentInput.arrayFlg)){
						dataTypeStr += "[]";
					}
					currentInput.dataTypeStr = dataTypeStr;
					var templateInput = $(modal).find("#tbl-exception-inputbean-template").tmpl(currentInput);
					$(templateInput).find("td.btn-remove").children().hide();
					$(modal).find(table).find("tbody").append(templateInput);
				}
			}
		}
	}
	$.qp.initialAllPicker(modal);
}