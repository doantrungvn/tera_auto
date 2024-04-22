/**
 * 
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
		messageParameter = ($(modal).find("input[name='messageParameter']").val() == null || $(modal).find("input[name='messageParameter']").val().length == 0) ? "[]" : convertToJson($(modal).find("input[name='messageParameter']").val());
	}
	if (data.messageParameter != undefined) {
		data.messageParameter = convertToJson(messageParameter);
	} else {		
		data["messageParameter"] = convertToJson(messageParameter);
	}
	var validationMessage = $.qp.businessdesign.validaionComponent.validationFeedbackComp($.qp.businessdesign.validaionComponent.DETAIL_CHECK,data,true,thiz);
	if(validationMessage != undefined && validationMessage != ""){
		alert(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("title",remark);
		$(obj).find("span.component-name").text(label);
		$(modal).modal("hide");
		instance.repaintEverything();
	}
}
function saveModalMessageParameterSetting(thiz){
	var modal =  $(thiz).closest(BD_MODAL_NAME.MESSAGEPARAMETER);
	var obj = $(modal).data("target");
	var parameters = [];
	$(modal).find("#messageParameter").find("tbody>tr").each(function(index){
		var objData = new Object();
		var parameterCode = $(this).find("input[name='parameterCode']").val() == undefined ? null : $(this).find("input[name='parameterCode']").val();
		var parameterCodeAutocomplete = $(this).find("input[name='parameterCodeAutocomplete']").val() == undefined ? null : $(this).find("input[name='parameterCodeAutocomplete']").val() ;
		var messageLevel = $(this).find("input[name='messageLevel']").val();
		
		objData.parameterCode = parameterCode;
		objData.parameterCodeAutocomplete = parameterCodeAutocomplete;
		objData.parameterType = 0;
		objData.itemSequenceNo = index;
		objData.messageLevel = messageLevel;
		parameters.push(objData);
	});
	var value =  convertToString(parameters);
	$(obj).closest(".modal").find("input[name='messageParameter']").val(value);
	$(modal).modal("hide");
}
function saveModalIfSetting(thiz){
	var modal = BD_MODAL_NAME.IF;
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
	
	if (data.ifConditionDetails != undefined) {
		data.ifConditionDetails = ifConditionDetails;
	} else {		
		data["ifConditionDetails"] = ifConditionDetails;
	}
	
	var validationMessage = $.qp.businessdesign.validaionComponent.validationIfComp($.qp.businessdesign.validaionComponent.DETAIL_CHECK,data,true,thiz);
	if(validationMessage != undefined && validationMessage != ""){
		alert(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("title",remark);
		$(obj).attr("connectionTarget",count);
		$(obj).find("span.component-name").text(label);
		if(amountCondition <=1){
			arrStatus = new Array();
			arrStatus = [{type:"TRUE", status:false, connectionId:""},{type:"FALSE", status:false, connectionId:""}];
		}
		arrStatus.push({type:"", status:false, connectionId:""});
		arrConnectionComponent[$(obj).attr("id")] = arrStatus;
		$(modal).modal("hide");
		instance.repaintEverything();
	}
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
		
		objInputBean.parameterId = ($(this).find("input[name='parameterId']").val() == undefined || $(this).find("input[name='parameterId']").val() == "" ) ? null : $(this).find("input[name='parameterId']").val();
		objInputBean.parameterIdAutocomplete = ($(this).find("input[name='parameterIdAutocomplete']").val() == undefined  || $(this).find("input[name='parameterIdAutocomplete']").val() == "" )? null : $(this).find("input[name='parameterIdAutocomplete']").val();
		var parameterScope = ($(this).find("input[name='parameterId']").attr("parameterScope") == undefined || $(this).find("input[name='parameterId']").attr("parameterScope") == "") ? null : $(this).find("input[name='parameterId']").attr("parameterScope");
		objInputBean.parameterScope = parameterScope;
		
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
		alert(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("title",remark);
		$(obj).find("span.component-name").text(label);
		$(modal).modal("hide");
		instance.repaintEverything();
	}
}
function saveModalAssignScreenItem(obj){
	var modal = "#dialog-assign-screenitem-setting";
	var obj = $(modal).data("target");
	var selectedRow = $(modal).find("input[name=outputSelect]:checked");
	if(selectedRow!=undefined && selectedRow.length > 0 ){
		
		// Adding HungHX
		var dataTypeInRow = $(obj).closest("tr").find('input:hidden[name$=".dataType"]').val();
		var dataTypeInRowModal = $(selectedRow).closest("tr").find('input:hidden[name="dataType"]').val();
		
		if(dataTypeInRow != dataTypeInRowModal) {
			alert("Data type is not mapping!");
			return false;
		}

		var screenItemId = $(selectedRow).val();
		var screenItemName = $(selectedRow).closest("tr").find("label[name=itemName]").text();
		$(obj).closest("td").find("input[name*=screenItemId]").val(screenItemId);
		$(obj).closest("td").find("label[name*=screenItemIdAutocomplete]").text(screenItemName);
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
		objAssign.targetId = $(this).find("input[name='targetId']").val() == undefined ? null : $(this).find("input[name='targetId']").val();
		objAssign.targetIdAutocomplete = $(this).find("input[name='targetIdAutocomplete']").val() == undefined ? null : $(this).find("input[name='targetIdAutocomplete']").val();
		objAssign.targetScope = ($(this).find("input[name='targetId']").attr("parameterScope") == undefined || $(this).find("input[name='targetId']").attr("parameterScope") == "") ? null : $(this).find("input[name='targetId']").attr("parameterScope");
		if("0" == objAssign.assignType){
			objAssign.parameterId = $(this).find("input[name='parameterId']").val() == undefined ? null : $(this).find("input[name='parameterId']").val();
			objAssign.parameterIdAutocomplete = $(this).find("input[name='parameterIdAutocomplete']").val() == undefined ? null : $(this).find("input[name='parameterIdAutocomplete']").val();
			objAssign.parameterScope = ($(this).find("input[name='parameterId']").attr("parameterScope") == undefined || $(this).find("input[name='parameterId']").attr("parameterScope") == "") ? null : $(this).find("input[name='parameterId']").attr("parameterScope");
		
			objAssign.formulaDefinitionContent = null;
			objAssign.formulaDefinitionDetails = [];
		}else if("1" == objAssign.assignType){
			objAssign.parameterId = null;
			objAssign.parameterIdAutocomplete = null;
			objAssign.parameterScope = null;
			
			objAssign.formulaDefinitionContent = ($(this).find("input[name='parameterIdAutocomplete']").val() == undefined || $(this).find("input[name='parameterIdAutocomplete']").val() == "" ) ? null : $(this).find("input[name='parameterIdAutocomplete']").val();
			objAssign.formulaDefinitionDetails = ($(this).find("input[name='formulaDefinitionDetails']").val() == undefined || $(this).find("input[name='formulaDefinitionDetails']").val() == "" ) ? [] : convertToJson($(this).find("input[name='formulaDefinitionDetails']").val());
		}
		objAssign.dataGroup = $(this).attr("data-ar-rgroup") == undefined ? "" : $(this).attr("data-ar-rgroup");
		objAssign.dataType = $(this).find("input[name='dataType']").val() == undefined ? null : $(this).find("input[name='dataType']").val();
		details.push(objAssign);
	});
	if (data.details != undefined) {
		data.details = details;
	} else {		
		data["details"] = details;
	}
	var validationMessage = $.qp.businessdesign.validaionComponent.validationAssignComp($.qp.businessdesign.validaionComponent.DETAIL_CHECK,data,true,thiz);
	if(validationMessage != undefined && validationMessage != ""){
		alert(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("title",remark);
		$(obj).find("span.component-name").text(label);
		$(modal).modal("hide");
		instance.repaintEverything();
	}
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
						var parameterIdAutocomplete = ($(this).find("input[name=parameterIdAutocomplete]").val() == undefined || $(this).find("input[name=parameterIdAutocomplete]").val().length ==0) ? null : $(this).find("input[name=parameterIdAutocomplete]").val();
						objCondition.parameterIdAutocomplete = parameterIdAutocomplete;					
						var parameterId = ($(this).find("input[name=parameterId]").val() == undefined || $(this).find("input[name=parameterId]").val().length ==0) ? null : $(this).find("input[name=parameterId]").val();
						objCondition.parameterId = parameterId;				
						var parameterScope = ($(this).find("input[name='parameterId']").attr("parameterScope") == undefined || $(this).find("input[name='parameterId']").attr("parameterScope").length ==0) ? null : $(this).find("input[name='parameterId']").attr("parameterScope");
						objCondition.parameterScope = parameterScope;
						objCondition.tblDesignIdAutocomplete = tblDesignIdAutocomplete;
						objCondition.tblDesignId = tblDesignId;
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
	if(validationMessage != undefined && validationMessage != ""){
		alert(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("title",remark);
		$(obj).find("span.component-name").text(label);
		$(modal).modal("hide");
		instance.repaintEverything();
	}
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
		
		objInputBean.parameterId = ($(this).find("input[name='parameterId']").val() == undefined || $(this).find("input[name='parameterId']").val() == "" ) ? null : $(this).find("input[name='parameterId']").val();
		objInputBean.parameterIdAutocomplete = ($(this).find("input[name='parameterIdAutocomplete']").val() == undefined  || $(this).find("input[name='parameterIdAutocomplete']").val() == "" )? null : $(this).find("input[name='parameterIdAutocomplete']").val();
		var parameterScope = ($(this).find("input[name='parameterId']").attr("parameterScope") == undefined || $(this).find("input[name='parameterId']").attr("parameterScope") == "") ? null : $(this).find("input[name='parameterId']").attr("parameterScope");
		objInputBean.parameterScope = parameterScope;
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
		
		objOutputbean.targetId = ($(this).find("input[name='targetId']").val() == undefined || $(this).find("input[name='targetId']").val() == "")? null : $(this).find("input[name='targetId']").val();
		objOutputbean.targetIdAutocomplete = ($(this).find("input[name='targetIdAutocomplete']").val() == undefined || $(this).find("input[name='targetIdAutocomplete']").val() == "")? null : $(this).find("input[name='targetIdAutocomplete']").val();
		objOutputbean.targetScope = ($(this).find("input[name='targetId']").attr("parameterScope") == undefined || $(this).find("input[name='targetId']").attr("parameterScope") == "")? null : $(this).find("input[name='targetId']").attr("parameterScope");
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
	if(validationMessage != undefined && validationMessage != ""){
		alert(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("title",remark);
		$(obj).find("span.component-name").text(label);
		$(modal).modal("hide");
		instance.repaintEverything();
	}
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
		objInputBean.parameterId = ($(this).find("input[name='parameterId']").val() == undefined || $(this).find("input[name='parameterId']").val() == "" ) ? null : $(this).find("input[name='parameterId']").val();
		objInputBean.parameterIdAutocomplete = ($(this).find("input[name='parameterIdAutocomplete']").val() == undefined  || $(this).find("input[name='parameterIdAutocomplete']").val() == "" )? null : $(this).find("input[name='parameterIdAutocomplete']").val();
		var parameterScope = ($(this).find("input[name='parameterId']").attr("parameterScope") == undefined || $(this).find("input[name='parameterId']").attr("parameterScope") == "") ? null : $(this).find("input[name='parameterId']").attr("parameterScope");
		objInputBean.parameterScope = parameterScope;
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
		objOutputbean.targetId = ($(this).find("input[name='targetId']").val() == undefined || $(this).find("input[name='targetId']").val() == "")? null : $(this).find("input[name='targetId']").val();
		objOutputbean.targetIdAutocomplete = ($(this).find("input[name='targetIdAutocomplete']").val() == undefined || $(this).find("input[name='targetIdAutocomplete']").val() == "")? null : $(this).find("input[name='targetIdAutocomplete']").val();
		objOutputbean.targetScope = ($(this).find("input[name='targetId']").attr("parameterScope") == undefined || $(this).find("input[name='targetId']").attr("parameterScope") == "")? null : $(this).find("input[name='targetId']").attr("parameterScope");
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
	if(validationMessage != undefined && validationMessage != ""){
		alert(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("title",remark);
		$(obj).find("span.component-name").text(label);
		$(modal).modal("hide");
		instance.repaintEverything();
	}
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
		objInputBean.parameterId = ($(this).find("input[name='parameterId']").val() == undefined || $(this).find("input[name='parameterId']").val() == "" ) ? null : $(this).find("input[name='parameterId']").val();
		objInputBean.parameterIdAutocomplete = ($(this).find("input[name='parameterIdAutocomplete']").val() == undefined  || $(this).find("input[name='parameterIdAutocomplete']").val() == "" )? null : $(this).find("input[name='parameterIdAutocomplete']").val();
		var parameterScope = ($(this).find("input[name='parameterId']").attr("parameterScope") == undefined || $(this).find("input[name='parameterId']").attr("parameterScope") == "") ? null : $(this).find("input[name='parameterId']").attr("parameterScope");
		
		objInputBean.parameterScope = parameterScope;
		
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
		objOutputbean.targetId = ($(this).find("input[name='targetId']").val() == undefined || $(this).find("input[name='targetId']").val() == "")? null : $(this).find("input[name='targetId']").val();
		objOutputbean.targetIdAutocomplete = ($(this).find("input[name='targetIdAutocomplete']").val() == undefined || $(this).find("input[name='targetIdAutocomplete']").val() == "")? null : $(this).find("input[name='targetIdAutocomplete']").val();
		objOutputbean.targetScope = ($(this).find("input[name='targetId']").attr("parameterScope") == undefined || $(this).find("input[name='targetId']").attr("parameterScope") == "")? null : $(this).find("input[name='targetId']").attr("parameterScope");
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
	if(validationMessage != undefined && validationMessage != ""){
		alert(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("title",remark);
		$(obj).find("span.component-name").text(label);
		$(modal).modal("hide");
		instance.repaintEverything();
	}
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
	var fromType = null;
	var toValue = null;
	var toType =  null;
	
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
			
			if($(modal).find("input[name='fromType']").prop("checked")){
				fromType = 1;
			}else{
				fromType = 0;
				fromValue = ($(modal).find("input[name='fromValue']").val() == undefined || $(modal).find("input[name='fromValue']").val() == "" ) ? null : $(modal).find("input[name='fromValue']").val();
			}
			
			if($(modal).find("input[name='toType']").prop("checked")){
				toType = 1;
			}else{
				toType = 0;
				toValue = ($(modal).find("input[name='toValue']").val() == undefined || $(modal).find("input[name='toValue']").val() == "" ) ? null : $(modal).find("input[name='toValue']").val();
			}
			
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
	
	if (data.fromValue != undefined) {
		data.fromValue = fromValue;
	} else {		
		data["fromValue"] = fromValue;
	}
	
	if (data.fromType != undefined) {
		data.fromType = fromType;
	} else {		
		data["fromType"] = fromType;
	}
	
	if (data.toValue != undefined) {
		data.toValue = toValue;
	} else {		
		data["toValue"] = toValue;
	}
	
	if (data.toType != undefined) {
		data.toType = toType;
	} else {		
		data["toType"] = toType;
	}
	
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
	var validationMessage = $.qp.businessdesign.validaionComponent.validationLoopComp($.qp.businessdesign.validaionComponent.DETAIL_CHECK,data,true,thiz);
	if(validationMessage != undefined && validationMessage != ""){
		alert(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("title",remark);
		$(obj).find("span.component-name").text(label);
		$(modal).modal("hide");
		instance.repaintEverything();
	}
	
}
function saveModalValidationCheckSetting(thiz){
	var modal = $(thiz).closest(BD_MODAL_NAME.VALIDATIONCHECK);
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
	var validationCheckDetails = [];
	var table = "#tbl-validationcheck";
	$(modal).find(table).find("tbody>tr").each(function(index){
		var parameters =  $(this).find("input[name=parameters]").val();
		parameters = (parameters == undefined || parameters == null ) ? [] : convertToJson(parameters);
		for(var i = 0;i <parameters.length;i++){
			var objItem  = parameters[i];
			var inputBeanId =  $(this).find("input[name=inputBeanId]").val();
			objItem.inputBeanId = (inputBeanId == undefined || inputBeanId == null) ? null : inputBeanId;
			var inputBeanCode =  $(this).find("label[name=inputBeanCode]").text();
			objItem.inputBeanCode = (inputBeanCode == undefined || inputBeanCode == null) ? null : inputBeanCode;
			var dataType =  $(this).find("input[name=dataType]").val();
			objItem.dataType = (dataType == undefined || dataType == null) ? null : dataType;
			var arrayFlg =  $(this).find("input[name=arrayFlg]").val();
			objItem.arrayFlg = (arrayFlg == undefined || arrayFlg == null) ? null : arrayFlg;
			validationCheckDetails.push(objItem);
		}
	});
	if (data.validationCheckDetails != undefined) {
		data.validationCheckDetails = validationCheckDetails;
	} else {		
		data["validationCheckDetails"] = validationCheckDetails;
	}
	
	var validationMessage = $.qp.businessdesign.validaionComponent.validationValidaionCheckComp($.qp.businessdesign.validaionComponent.DETAIL_CHECK,data,true,thiz);
	if(validationMessage != undefined && validationMessage != ""){
		alert(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("title",remark);
		$(obj).find("span.component-name").text(label);
		$(modal).modal("hide");
		instance.repaintEverything();
	}
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
					parameters.push(objData);
				}
				if(parameterType == "1"){
					objData.parameterCode = ($(this).val() == undefined || $(this).val() == "")? null :$(this).val();
					objData.parameterType = parameterType;
					var itemSequenceNo = $(this).parent().attr("itemSequenceNo");
					objData.itemSequenceNo = (itemSequenceNo == undefined || itemSequenceNo == "") ? null :itemSequenceNo;
					parameters.push(objData);
				}
			});
			var validationType = $(this).attr("validationType");
			parameters = parameters.sort(sortByItemSequenceNo);
			validationCheckDetails.push({validationType:validationType,parameters:parameters});
		}
	});
	var groupData = checkGroupOfValidationCheck(validationCheckDetails);
	if(groupData !=undefined){
		var currentRow = $(obj).closest("tr");
		if(groupData.isLength){
			$(currentRow).find("td[name=length]").html("O");
		}else{
			$(currentRow).find("td[name=length]").empty();
		}
		if(groupData.isMandatory){
			$(currentRow).find("td[name=mandatory]").html("O");
		}else{
			$(currentRow).find("td[name=mandatory]").empty();
		}
		if(groupData.isOthers){
			$(currentRow).find("td[name=others]").html("O");
		}else{
			$(currentRow).find("td[name=others]").empty();
		}
	}
	var value =  convertToString(validationCheckDetails);
	$(obj).closest("td").find("input[name='parameters']").val(value);
	$(obj).closest("tr").removeClass("qp-bdesign-tr-remove");
	$(modal).modal("hide");
}
function saveModalNestedLogicSetting(thiz){
	var modal = $(thiz).closest("body");
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
	var validationMessage = "";
	if(validationMessage != undefined && validationMessage != ""){
		alert(validationMessage);
	}else{
		var value =  convertToString(data);
		$(obj).find("input[name='componentElement']").val(value);
		$(obj).attr("title",remark);
		$(obj).find("span.component-name").text(label);
		parent.jQuery.fancybox.close();
		instanceParent.repaintEverything();
	}
}
function deleteModalValidationCheckDetailSetting(thiz){
	var modal = $(thiz).closest(BD_MODAL_NAME.VALIDATIONCHECK_DETAIL);
	var obj = $(modal).data("target");
	var parameters = "";
	var table = "#tbl-validationcheck-detail";
	var validationCheckDetails = [];
	var groupData = checkGroupOfValidationCheck(validationCheckDetails);
	if(groupData !=undefined){
		var currentRow = $(obj).closest("tr");
		if(groupData.isLength){
			$(currentRow).find("td[name=length]").html("O");
		}else{
			$(currentRow).find("td[name=length]").empty();
		}
		if(groupData.isMandatory){
			$(currentRow).find("td[name=mandatory]").html("O");
		}else{
			$(currentRow).find("td[name=mandatory]").empty();
		}
		if(groupData.isOthers){
			$(currentRow).find("td[name=others]").html("O");
		}else{
			$(currentRow).find("td[name=others]").empty();
		}
	}
	var value =  convertToString(validationCheckDetails);
	$(obj).closest("td").find("input[name='parameters']").val(value);
	$(obj).closest("tr").removeClass("qp-bdesign-tr-remove");
	$(modal).modal("hide");
}
/*
 * delete nested logic node
 */
function deleteNestedLogicModal(thiz){
	if ($.qp.confirm(dbMsgSource['err.blogiccomponent.0130']) == true) {
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
//		if (confirm(dbMsgSource['err.blogiccomponent.0130']) == true) {
//			$(modal).modal("hide");
//			var targetBoxId = $(obj).attr("id");
//			
//			var connectionSources = instance.getConnections({ source:targetBoxId });
//			var connectionTargets = instance.getConnections({ target:targetBoxId });
//			for(var i = 0;i<connectionSources.length;i++){
//				processDeleteConnectionOfDeletedNode(connectionSources[i],instance);
//			}
//			for(var i = 0;i<connectionTargets.length;i++){
//				processDeleteConnectionOfDeletedNode(connectionTargets[i],instance);
//			}
//			instance.removeAllEndpoints(targetBoxId);
//			instance.detach(targetBoxId);
//			$("#" + targetBoxId).remove();
//			instance.repaintEverything();
//		}
		deleteNode($(obj),instance);
		$(modal).modal("hide");
	}	
}
function deleteNode(thiz,instance){
	if(instance == undefined)
		instance = instanceOfBlogic;
	if ($.qp.confirm(dbMsgSource['err.blogiccomponent.0130']) == true) {
		var targetBoxId = $(thiz).attr("id");
		
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
		$("#" + targetBoxId).remove();
		instance.repaintEverything();
	}
}
function onChangeNavigationType(thiz){
	var type = $(thiz).val();
	var sqlId = "getAutocompleteBusinessLogicForNavigatorBD";
	if(type == "0"){
		sqlId = "getAutocompleteScreenDesignForNavigatorBD";
		$(thiz).closest("table").find("input[name=transitionType][value=1]").parent().hide();
		$(thiz).closest("table").find("input[name=transitionType][value=0]").prop("checked",true);
	}else{
		$(thiz).closest("table").find("input[name=transitionType][value=1]").parent().show();
	}
	$(thiz).closest("table").find("input[name=navigatorToIdAutocomplete]").val("");
	$(thiz).closest("table").find("input[name=navigatorToIdAutocomplete]").attr("selectsqlid",sqlId);
	$(thiz).closest("table").find("input[name=navigatorToId]").val("");
	$(thiz).closest("table").find("input[name=navigatorToIdAutocomplete]").data("ui-autocomplete")._trigger("close");
	$(thiz).closest(".modal").find("#tbl-navigator-inputbean").find("tbody").empty();
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
	if(event.item != undefined){
		if(event.item.optionValue != ""){
			var url = CONTEXT_PATH + "/businessdesign/getInformationOfCommonBusinessLogic?businessDesignId="+event.item.optionValue+"&r="+Math.random();
			var responseData = $.qp.getData(url);
			if(responseData.businessLogicId == null){
				$(modal).find("label[name=businessLogicCode]").text("");
			}else{
				//input bean
				$(modal).find("label[name=businessLogicCode]").text(responseData.businessLogicCode);
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
			displayTabLogicDesign(arrReqItemCond, arrReqItemAct, arrReqCondGroup, arrReqCondItem);
		}
	}
	$.qp.initialAllPicker(modal);
}
function ongetPropetyOfObjectAssignSet(thiz){
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
					var newRow = $.qp.ar.addRow({link:thiz,tableId:'tbl-assign-parameter',templateId:'tbl-assign-parameter-template',templateData:{groupId:groupIndex},position:{anchor:tempRow,string:"after"}});
					$(newRow).find("input[name=targetId]").val(BD_PREFIX_SCOPE.INPUTBEAN + sourceData[i].inputBeanId);
					$(newRow).find("input[name=targetIdAutocomplete]").val(parentCode+"."+sourceData[i].inputBeanCode);
					$(newRow).find("input[name=targetIdAutocomplete]").data("ui-autocomplete")._trigger("close");
					$(newRow).find("input[name=targetId]").attr("parameterScope","0");
					$(newRow).find("input[name=dataType]").val(sourceData[i].dataType);
					if("0" == sourceData[i].dataType || "14" == sourceData[i].dataType){
						$(newRow).find("td.btn-getobject").children().show();
					}else{
						$(newRow).find("td.btn-getobject").children().hide();
					}
					tempRow = newRow;
				}
			}
			break;
		case "1":
			var sourceDataOb = convertToJson($("form").find("input[name='jsonObjectDefinition']").val());
			for(var i=0;i< sourceDataOb.length; i++){
				if(sourceDataOb[i].parentObjectDefinitionId == targetId){
					var newRow = $.qp.ar.addRow({link:thiz,tableId:'tbl-assign-parameter',templateId:'tbl-assign-parameter-template',templateData:{groupId:groupIndex},position:{anchor:tempRow,string:"after"}});
					$(newRow).find("input[name=targetId]").val(BD_PREFIX_SCOPE.OBJECTDEFINITION + sourceDataOb[i].objectDefinitionId);
					$(newRow).find("input[name=targetIdAutocomplete]").val(parentCode+"."+sourceDataOb[i].objectDefinitionCode);
					$(newRow).find("input[name=targetIdAutocomplete]").data("ui-autocomplete")._trigger("close");
					$(newRow).find("input[name=targetId]").attr("parameterScope","1");
					$(newRow).find("input[name=dataType]").val(sourceDataOb[i].dataType);
					if("0" == sourceDataOb[i].dataType || "14" == sourceDataOb[i].dataType){
						$(newRow).find("td.btn-getobject").children().show();
					}else{
						$(newRow).find("td.btn-getobject").children().hide();
					}
					tempRow = newRow;
				}
			}
			break;
		case "2":
			var sourceData = convertToJson($("form").find("input[name='jsonOutputBean']").val());
			for(var i=0;i< sourceData.length; i++){
				if(sourceData[i].parentOutputBeanId == targetId){
					var newRow = $.qp.ar.addRow({link:thiz,tableId:'tbl-assign-parameter',templateId:'tbl-assign-parameter-template',templateData:{groupId:groupIndex},position:{anchor:tempRow,string:"after"}});
					$(newRow).find("input[name=targetId]").val(BD_PREFIX_SCOPE.OUTPUTBEAN + sourceData[i].outputBeanId);
					$(newRow).find("input[name=targetIdAutocomplete]").val(parentCode+"."+sourceData[i].outputBeanCode);
					$(newRow).find("input[name=targetIdAutocomplete]").data("ui-autocomplete")._trigger("close");
					$(newRow).find("input[name=targetId]").attr("parameterScope","2");
					$(newRow).find("input[name=dataType]").val(sourceData[i].dataType);
					if("0" == sourceData[i].dataType || "14" == sourceData[i].dataType){
						$(newRow).find("td.btn-getobject").children().show();
					}else{
						$(newRow).find("td.btn-getobject").children().hide();
					}
					tempRow = newRow;
				}
			}
			break;
		default:
			break;
		}
	}
}
function onchangeMessageOfFeebackSet(event){
	var model = $(event.target).closest("#dialog-feedback-setting");
	if(event.item != undefined){
//		$(model).find("span[name='messageCode']").text(event.item.output01);
		var message = event.item.optionLabel;
		var count = countParameterOfMessage(message);
		if(count == 0){
			$(model).find("span[name='btnChooseParameter']").hide();
		}else{
			$(model).find("span[name='btnChooseParameter']").show();
		}
	}	else{
		$(model).find("span[name='btnChooseParameter']").hide();
	}
	$(model).find("input[name='messageParameter']").val("");
}
function onchangeParameterOfAssignSet (event){
	var obj = event.target;
	if(event.item != undefined){
		var type = event.item.output01;
		$(obj).next().attr("parameterScope",type);
		var dataType = event.item.output03;
		if(dataType ==0 || dataType==14){
			$(obj).closest("tr").find("td.btn-getobject").children().show();
		}
		$.qp.ar.removeRow({link:obj,removeType:'onlyDescendants'});
		$(obj).closest("tr").find("input[name=dataType]").val(dataType);
	}
	else{
		$(obj).next().attr("parameterScope","");
		$(obj).closest("tr").find("td.btn-getobject").children().hide();
		$.qp.ar.removeRow({link:obj,removeType:'onlyDescendants'});
		$(obj).closest("tr").find("input[name=dataType]").val("");
	}
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
			switch (value) {
			case "0":
				levelName = "Message of project";
				break;
			case "1":
				levelName = "Message of module";
				break;
			case "2":
				levelName = "Message of screen";
				break;
			case "3":
				levelName = "Message of screen area";
				break;
			case "4":
				levelName = "Message of screen item";
				break;
			case "5":
				levelName = "Message of design information";
				break;
			default:
				levelName = "";
				break;
			}
			
		}else{
			levelName = "";
			value = "";
		}
	}
	$(obj).closest("tr").find("span[name=messageLevel]").html(levelName);
	$(obj).closest("tr").find("input[name=messageLevel]").val(value);
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
	switch (validationType) {
	case 0:
	case 1:
		isValidated = true;
		break;
	case 2:
	case 13:
	case 14:
	case 15:
	case 16:
		if(dataType == BD_STANDARD_DATATYPE.STRING){
			isValidated = true;
		}else{
			isValidated = false;
		}
		break;
	case 3:
	case 4:
	case 5:
	case 6:
	case 8:
		if(dataType == BD_STANDARD_DATATYPE.BIGDECIMAL || dataType == BD_STANDARD_DATATYPE.DOUBLE || dataType == BD_STANDARD_DATATYPE.FLOAT || dataType == BD_STANDARD_DATATYPE.INTEGER
				|| dataType == BD_STANDARD_DATATYPE.LONG || dataType == BD_STANDARD_DATATYPE.SHORT){
			isValidated = true;
		}else{
			isValidated = false;
		}
		break;
	case 9:
	case 10:
		if(dataType == BD_STANDARD_DATATYPE.BOOLEAN){
			isValidated = true;
		}else{
			isValidated = false;
		}
	break;
	case 11:
	case 12:
		if(dataType == BD_STANDARD_DATATYPE.DATE||dataType == BD_STANDARD_DATATYPE.DATETIME||dataType == BD_STANDARD_DATATYPE.TIME||dataType == BD_STANDARD_DATATYPE.TIMESTAMP){
			isValidated = true;
		}else{
			isValidated = false;
		}
		break;
	case 17:
		if(dataType == BD_STANDARD_DATATYPE.STRING){
			isValidated = true;
		}else if(arrayFlg){
			isValidated = true;
		}else{
			isValidated = false;
		}
		break;
	case 7:
		if(eval(arrayFlg) == true || dataType == BD_STANDARD_DATATYPE.STRING) {
			isValidated = true;
		}else{
			isValidated = false;
		}
		break
	default:
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
		if(sqlInfor != null && sqlInfor.sqlDesignId != null){
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
		alert(dbMsgSource['err.blogiccomponent.0140']);
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
	if($.qp.confirm(fcomMsgSource['inf.sys.0014'])){
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
	return $(thiz).closest("td").find("input[name$='formulaDefinitionContent']");
	
}

function findTargetValueOfFormula(thiz){
	return $(thiz).closest("td").find("input[name$='formularDefinitionDetails']");
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
	}
}

function onAfterDeleteFormulaForAssign(thiz){
	$(thiz).closest("tr").find("[name=parameterIdAutocomplete]").removeClass("qp-bdesign-tr-change");
	$(thiz).closest("tr").find("[name=assignType]").val("0");
}
/*
 * handle event of Loop setting
 */
function onChangeAutoObjectOfLoop (event){
	setParameterTypeOfAutocompleteBD(event);
	var obj = event.target;
	if(event.item != undefined){
		$(obj).closest("tr").find("input[name='toType']").prop("disabled",false);
		$(obj).closest("tr").find("input[name='fromType']").prop("disabled",false);
	}
	else{
		$(obj).closest("tr").find("input[name='toType']").prop("disabled",true);
		$(obj).closest("tr").find("input[name='toType']").prop("checked",false);
		$(obj).closest("tr").find("input[name='fromType']").prop("disabled",true);
		$(obj).closest("tr").find("input[name='fromType']").prop("checked",false);
		$(obj).closest("tr").find("input[name='toValue']").prop('disabled', false);
		$(obj).closest("tr").find("input[name='fromValue']").prop('disabled', false)
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
		$(modal).find("input[name='fromType']").prop("checked",false);
		$(modal).find("input[name='fromType']").prop("disabled",true);
		$(modal).find("input[name='fromValue']").prop('disabled', false)
		$(modal).find("input[name='fromValue']").val("");
		$(modal).find("input[name='toType']").prop("checked",false);
		$(modal).find("input[name='toType']").prop("disabled",true);
		$(modal).find("input[name='toValue']").prop('disabled', false)
		$(modal).find("input[name='toValue']").val("");
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
	$(thiz).closest(".input-group").find("input.qp-input-integer").prop('disabled', status);
	$(thiz).closest(".input-group").find("input.qp-input-integer").val("");
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
		alert("SYSTEM ERROR");
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
		objLogic.prefixLabel = dbMsgSource['sc.blogiccomponent.0001'];
		break;
	case "2":
		objLogic.imagePath = path + "excution.png";
		if(isView){
			objLogic.actionPath = "openModalExecution(this,true)";
		}else{
			objLogic.actionPath = "openModalExecution(this)";
		}
		objLogic.cssClass = "bdesign-node-default";
		objLogic.prefixLabel = OPEN_PARENTHESIS + dbMsgSource['sc.blogiccomponent.0002'] + CLOSE_PARENTHESIS;
		break;
	case "3":
		objLogic.imagePath = path + "validationcheck.png";
		if(isView){
			objLogic.actionPath = "openModalValidationCheck(this,true)";
		}else{
			objLogic.actionPath = "openModalValidationCheck(this)";
		}
		objLogic.cssClass = "bdesign-node-default";
		objLogic.prefixLabel = OPEN_PARENTHESIS + dbMsgSource['sc.blogiccomponent.0003'] + CLOSE_PARENTHESIS;
		break;
	case "4":
		objLogic.imagePath = path + "businesscheck.png";
		if(isView){
			objLogic.actionPath = "openModalBusinessCheck(this,true)";
		}else{
			objLogic.actionPath = "openModalBusinessCheck(this)";
		}
		objLogic.cssClass = "bdesign-node-default";
		objLogic.prefixLabel = OPEN_PARENTHESIS + dbMsgSource['sc.blogiccomponent.0004'] + CLOSE_PARENTHESIS;
		break;
	case "5":
		objLogic.imagePath = path + "decision.png";
		if(isView){
			objLogic.actionPath = "openModalDecision(this,true)";
		}else{
			objLogic.actionPath = "openModalDecision(this)";
		}
		objLogic.cssClass = "bdesign-node-default";
		objLogic.prefixLabel = OPEN_PARENTHESIS + dbMsgSource['sc.blogiccomponent.0005'] + CLOSE_PARENTHESIS;
		break;
	case "6":
		objLogic.imagePath = path + "advanced.png";
		objLogic.cssClass = "bdesign-node-two";
		objLogic.prefixLabel = OPEN_PARENTHESIS + dbMsgSource['sc.blogiccomponent.0006'] + CLOSE_PARENTHESIS;
		break;
	case "7":
		objLogic.imagePath = path + "common.png";
		if(isView){
			objLogic.actionPath = "openModalCommon(this,true)";
		}else{
			objLogic.actionPath = "openModalCommon(this)";
		}
		objLogic.cssClass = "bdesign-node-two";
		objLogic.prefixLabel = OPEN_PARENTHESIS + dbMsgSource['sc.blogiccomponent.0007'] + CLOSE_PARENTHESIS;
		break;
	case "8":
		objLogic.imagePath = path + "assign.png";
		if(isView){
			objLogic.actionPath = "openModalAssign(this,true)";
		}else{
			objLogic.actionPath = "openModalAssign(this)";
		}
		objLogic.cssClass = "bdesign-node-one";
		objLogic.prefixLabel = OPEN_PARENTHESIS +dbMsgSource['sc.blogiccomponent.0008'] + CLOSE_PARENTHESIS;
		break;
	case "9":
		objLogic.imagePath = path + "if.png";
		if(isView){
			objLogic.actionPath = "openModalIf(this,true)";
		}else{
			objLogic.actionPath = "openModalIf(this)";
		}
		objLogic.cssClass = "bdesign-node-one";
		objLogic.prefixLabel = OPEN_PARENTHESIS + dbMsgSource['sc.blogiccomponent.0009'] + CLOSE_PARENTHESIS;
		break;
	case "10":
		objLogic.imagePath = path + "foreach.png";
		if(isView){
			objLogic.actionPath = "openModalLoop(this,true)";
		}else{
			objLogic.actionPath = "openModalLoop(this)";
		}
		objLogic.cssClass = "bdesign-node-one";
		objLogic.prefixLabel = OPEN_PARENTHESIS + dbMsgSource['sc.blogiccomponent.0010'] + CLOSE_PARENTHESIS;
		break;
	case "11":
		objLogic.imagePath = path + "feedback.png";
		if(isView){
			objLogic.actionPath = "openModalFeedback(this,true)";
		}else{
			objLogic.actionPath = "openModalFeedback(this)";
		}
		objLogic.cssClass = "bdesign-node-one";
		objLogic.prefixLabel = OPEN_PARENTHESIS + dbMsgSource['sc.blogiccomponent.0011'] + CLOSE_PARENTHESIS;
		break;
	case "12":
		objLogic.imagePath = path + "navigator.png";
		if(isView){
			objLogic.actionPath = "openModalNavigator(this,true)";
		}else{
			objLogic.actionPath = "openModalNavigator(this)";
		}
		objLogic.cssClass = "bdesign-node-one";
		objLogic.prefixLabel = OPEN_PARENTHESIS + dbMsgSource['sc.blogiccomponent.0012'] + CLOSE_PARENTHESIS;
		break;
	case "13":
		objLogic.imagePath = path + "end.png";
		objLogic.cssClass = "bdesign-node-three";
		objLogic.prefixLabel = OPEN_PARENTHESIS + dbMsgSource['sc.blogiccomponent.0013'] + CLOSE_PARENTHESIS;
		break;
	case "14":
		objLogic.imagePath = path + "nestedlogic.png";
		if(isView){
			objLogic.actionPath = "openModalNestedLogic(this,true)";
		}else{
			objLogic.actionPath = "openModalNestedLogic(this)";
		}
		objLogic.prefixLabel = OPEN_PARENTHESIS + dbMsgSource['sc.blogiccomponent.0126'] + CLOSE_PARENTHESIS;
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
		case "start":
		case "validationcheck":
		case "navigator":
		case "end":
			$(this).hide();
			break;
		case "nestedlogic":
			if(levelOfNestedLogic != "2")
				$(this).hide();
			break;
		default:
			break;
		}
	});
}
function detachNestedLogicModal(thiz){
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
	$(obj).remove();
	$(modal).find(".design-area").find(".execution-class[componenttype!='1'][componenttype!='13']").each(function(i) {
		var currentLeft = parseInt($(this).css("left"));
		var currentTop = parseInt($(this).css("top"));
		$(this).css("left",currentLeft - positionLeft.minLeft+currentLeftOfParentNestedLogic);
		$(this).css("top",currentTop - positionTop.minTop + currentTopOfParentNestedLogic);
//		instance.removeAllEndpoints($(this).attr("id"));
//		instance.detach($(this).attr("id"));
		var clone = $(this).clone().removeClass("jsplumb-draggable jsplumb-droppable");
		$(parentWindow).find(".design-area").append(clone);
		$(this).remove();
		instanceParent.draggable($(clone));
		
		initEndPointForComponent($(clone),instanceParent);
		initConnectionTargetForComponent($(clone).attr("id"),$(clone).attr("componenttype"));
		parent.initEventForNewNode($(clone).attr("id"));
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
        	var countNode = $("#designArea").find("div.execution-class[add='on']").length;
        	if(countNode > 0){
                if($.qp.confirm("Do you want to group the selected nodes")){
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
        			
                	$("#designArea").find("div.execution-class[add='on']").each(function(i){
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
        				connection.connectorSource = connectionSources[i].sourceId;
        				connection.connectorDest = connectionSources[i].targetId;
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
    });
}
function test1(){
	var els = $("#designArea").find(".execution-class");
	instanceOfBlogic.draggable(els
			, {
			containment : "designArea",
	        stop:function(e){
	        },
			}
		);
}