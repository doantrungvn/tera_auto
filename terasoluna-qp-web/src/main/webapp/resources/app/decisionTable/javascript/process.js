/** Start process for display and save data json*/

/**
 * Process return html for input && output bean && item design
 * 
 * @param items
 * @param beanType
 * @returns {String}
 */
function returnBeanHTML(items, beanType) {
	
	var allrow = '';
	
	if(beanType <= 1) {
		// Process row for input && output bean tab		
	} else {
		
		for(var i = 0; i < items.length; i++) {
			allrow += returnRowItem(items[i], beanType, i);
		}
	}
	
	return allrow;
}

/**
 * 
 */
function returnRowItem(item, beanType, arrIndex) {
	
	/**Define properties for item design bean */
	var decisionItemDesignId = "";
	if(item.decisionItemDesignId != undefined){
		decisionItemDesignId = item.decisionItemDesignId;
	}
	
	var itemName = "";
	if(item.itemName != undefined){
		itemName = item.itemName;
	}
	
	var itemType = "";
	if(item.itemType != undefined){
		itemType = item.itemType;
	}
	
	var itemValue = "";
	if(item.itemValue != undefined){
		itemValue = item.itemValue;
	}
	
	var itemSequenceNo = "";
	if(item.itemSequenceNo != undefined){
		itemSequenceNo = item.itemSequenceNo;
	}
	
	var decisionTbId = "";
	if(item.decisionTbId != undefined){
		decisionTbId = item.decisionTbId;
	}
	
	var objectCode = "";
	if(item.objectCode != undefined){
		objectCode = item.objectCode;
	}
	
	var objectCodeCombine = "";
	if(item.objectCodeCombine != undefined){
		objectCodeCombine = item.objectCodeCombine;
	}
	
	var objectName = "";
	if(item.objectName != undefined){
		objectName = item.objectName;
	}
	
	var dataType = "";
	if(item.dataType != undefined){
		dataType = item.dataType;
	}
	
	var dlsDataType = "";
	if (dataType != "") dlsDataType = CL_QP_DATATYPE[dataType];
	
	/** Start process */
	var row = "";
	var index = arrIndex+1;
	
	if(beanType == 2) {
		
		row = "<tr>"
			+ "<td class=\"qp-output-fixlength tableIndex\" id=\"sttIn\">"+index+""
			+ "<input type=\"hidden\" name=\"arrIndexIn\" value=\""+index+"\" /> </td>"
			+ "<td ><input type=\"text\" name=\"condition["+arrIndex+"].itemName\" class=\"form-control qp-input-text\" maxlength=\"100\" value=\""+itemName+"\"></td>"
			+ "<td><div class=\"input-group\" style=\"width:100%\">"
			+ "<input type=\"text\" name=\"condition["+arrIndex+"].itemValueAutocomplete\" id=\"condition["+arrIndex+"].itemValueAutocompleteId\" class=\"form-control qp-input-autocomplete\""
			+ "optionValue=\"optionValue\" optionLabel=\"optionLabel\" minLength = \"0\" value=\""+objectCodeCombine+"\" sourceType=\"local\" sourceCallback=\"getDataSourceCondition\" onfocus=\"markValue(this)\""
			+ "onChangeEvent=\"displayDataRow\" arg01=\"\" mustMatch=\"true\" maxlength=\"200\" placeholder=\""+fcomMsgSource['sc.sys.0034']+"\" previousvalue=\"\" previouslabel=\"\" selectedvalue=\"false\"/>"
			+ "<input type=\"hidden\" name=\"condition["+arrIndex+"].itemValue\" value=\""+itemValue+"\"></td></div>"
			+ "<td>"+objectName+"</td>"
			+ "<td>"+dlsDataType+"</td>"
			+ "<td class=\"\" style=\"text-align: center;\">"
			+ "<a href=\"javascript:\" style=\"margin-top: 3px; cursor: move; display : none;\" class=\"glyphicon glyphicon-sort\" title=\"Move\"></a> </td>"
			+ "<td><a class=\"btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action\" onclick=\"checkRemoveRow(this);\""
			+ "title=\"Remove\" style=\"margin-top: 3px;\" href=\"javascript:void(0)\"></a>"
			+ "<input type=\"hidden\" name=\"condition["+arrIndex+"].objectCode\" value=\""+objectCode+"\">"
			+ "<input type=\"hidden\" name=\"condition["+arrIndex+"].objectName\" value=\""+objectName+"\">"
			+ "<input type=\"hidden\" name=\"condition["+arrIndex+"].dataType\" value=\""+dataType+"\">"
			+ "<input type=\"hidden\" name=\"condition["+arrIndex+"].decisionItemDesignId\" value=\""+decisionItemDesignId+"\">"
			+ "<input type=\"hidden\" name=\"condition["+arrIndex+"].decisionTbId\" value=\""+decisionTbId+"\"></td>"
			+ "</tr>";
		
	} else if (beanType == 3) {
		
		row = "<tr>"
			+ "<td class=\"qp-output-fixlength tableIndex\" id=\"sttOut\">"+index+""
			+ "<input type=\"hidden\" name=\"arrIndexOut\" value=\""+index+"\" /> </td>"
			+ "<td ><input type=\"text\" name=\"action["+arrIndex+"].itemName\" class=\"form-control qp-input-text\" maxlength=\"100\" value=\""+itemName+"\"></td>"
			+ "<td><div class=\"input-group\" style=\"width:100%\">"
			+ "<input type=\"text\" name=\"action["+arrIndex+"].itemValueAutocomplete\" id=\"action["+arrIndex+"].itemValueAutocompleteId\" class=\"form-control qp-input-autocomplete\""
			+ "optionValue=\"optionValue\" optionLabel=\"optionLabel\" minLength = \"0\" value=\""+objectCodeCombine+"\" sourceType=\"local\" sourceCallback=\"getDataSourceAction\" beanType=\"1\""
			+ "onChangeEvent=\"displayDataRow\" arg02=\"\" mustMatch=\"true\" maxlength=\"200\" placeholder=\""+fcomMsgSource['sc.sys.0034']+"\" previousvalue=\"\" previouslabel=\"\" selectedvalue=\"false\"/>"
			+ "<input type=\"hidden\" name=\"action["+arrIndex+"].itemValue\" value=\""+itemValue+"\"></td></div>"
			+ "<td>"+objectName+"</td>"
			+ "<td>"+dlsDataType+"</td>"
			+ "<td class=\"\" style=\"text-align: center;\">"
			+ "<a href=\"javascript:\" style=\"margin-top: 3px; cursor: move; display : none;\" class=\"glyphicon glyphicon-sort\" title=\"Move\"></a> </td>"
			+ "<td><a class=\"btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action\" onclick=\"checkRemoveRow(this);\""
			+ "title=\"Remove\" style=\"margin-top: 3px;\" href=\"javascript:void(0)\"></a>"
			+ "<input type=\"hidden\" name=\"action["+arrIndex+"].objectCode\" value=\""+objectCode+"\">"
			+ "<input type=\"hidden\" name=\"action["+arrIndex+"].objectName\" value=\""+objectName+"\">"
			+ "<input type=\"hidden\" name=\"action["+arrIndex+"].dataType\" value=\""+dataType+"\">"
			+ "<input type=\"hidden\" name=\"action["+arrIndex+"].decisionItemDesignId\" value=\""+decisionItemDesignId+"\">"
			+ "<input type=\"hidden\" name=\"action["+arrIndex+"].decisionTbId\" value=\""+decisionTbId+"\"></td>"
			+ "</tr>";
	}
	
	return row;
}

function dtOnChangeCommonObjectOfIn(event){
	
	var obj = event.target;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	$(obj).closest("tr").nextAll("tr[data-ar-rgroup^='"+rgroup+"']").each(function() {
		$(this).remove();
	});
	if(event.item != undefined){
		$(obj).closest("tr").find("input[name*='decisionInputBeanName']").val(event.item.optionLabel);
		$(obj).closest("tr").find("input[name*='decisionInputBeanCode']").val(event.item.output01);
		$(obj).closest("tr").find("input[name*='objectType']").val(0);
		$(obj).closest("tr").find("input[name*='objectId']").val(event.item.optionValue);
		var table = $(obj).closest("table").attr("id");
		
		if(event.item.optionValue != ""){
			var level = 1;
			if(rgroup == undefined || rgroup == ""){
				level = 1;
			}else{
				level = (rgroup.match(/\./g) || []).length + 1;
			}
			var url = CONTEXT_PATH + "/commonobjectdefinition/getDetailsCommonObjectDefinition?commonObjectDefinitionId="+event.item.optionValue+"&level="+level+"&r="+Math.random();
			
			var data = $.qp.getData(url);
			if(data.length>0) {
				
				var message = dbMsgSource["sc.decisiontable.0054"].replace("{0}", dbMsgSource["sc.sqldesign.0064"]).replace("{1}", event.item.optionLabel);
				var checkExcute = notAllowSelectExternalOrCommonContainArrayField(event, data, message);
				if(checkExcute) {
					$(obj).closest("tr").find("input[name*='decisionInputBeanCode']").val("");
					//$.qp.searchAutocompleteData($(obj).closest("tr").find("input[name*='objectId']").nextAll('.dropdown-toggle:first'));
					return;
				}
				
				tempAnchor = obj;
				buildCommonObjectAttributesIn(data, obj);
			}
		}
	}else{
		$(obj).closest("tr").find("input[name*='decisionInputBeanName']").val("");
		$(obj).closest("tr").find("input[name*='decisionInputBeanCode']").val("");
		$(obj).closest("tr").find("input[name*='objectType']").val(-1);
		$(obj).closest("tr").find("input[name*='objectId']").val("");
	}
}

function checkHasIsArrayFlg(data){
	var check = false;
	for(var i = 0; i < data.length; i++){
		if(!!data[i].arrayFlg && data[i].arrayFlg == true){
			check = true;
			break;
		}
	}
	return check;
};

function notAllowSelectExternalOrCommonContainArrayField(event,data,message) {
	var checkExcute = false;
	var obj = event.target;
	var arrayFlg =  checkHasIsArrayFlg(data);
	if(arrayFlg){
		$.qp.showAlertModal(message);
		$(obj).val('');
		checkExcute = true;
	}
	return checkExcute;
}

function buildCommonObjectAttributesIn(data, obj) {
	var tempParentId = null;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	var table = $(obj).closest("table").attr("id");
	
    for(var i=0;i<data.length;i++){
		if (data[i].dataType == 16) {
	    	var actionTemplateId = table+"-object-attribute-template";
			var newRow = $.qp.ar.addRow({link: obj,container:$(obj).closest('table'),templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});
			
			var curObj = data[i].commonObjectDefinition;
			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			
			$(newRow).find("input[name*='groupId']").val(rgroup);
			$(newRow).find("input[name*='tableIndex']").text(tableIndex);
			$(newRow).find("input[name*='decisionInputBeanName']").val(curObj.commonObjectDefinitionName);
			$(newRow).find("label[name*='decisionInputBeanName']").text(curObj.commonObjectDefinitionName);
			$(newRow).find("input[name*='decisionInputBeanCode']").val(curObj.commonObjectDefinitionCode);
			$(newRow).find("label[name*='decisionInputBeanCode']").text(curObj.commonObjectDefinitionCode);
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
			$(newRow).find("input[name*='decisionInputBeanName']").val(curObj.externalObjectDefinitionName);
			$(newRow).find("label[name*='decisionInputBeanName']").text(curObj.externalObjectDefinitionName);
			$(newRow).find("input[name*='decisionInputBeanCode']").val(curObj.externalObjectDefinitionCode);
			$(newRow).find("label[name*='decisionInputBeanCode']").text(curObj.externalObjectDefinitionCode);
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
			$(newRow).find("input[name*='decisionInputBeanName']").val(data[i].commonObjectAttributeName);
			$(newRow).find("label[name*='decisionInputBeanName']").text(data[i].commonObjectAttributeName);
			$(newRow).find("input[name*='decisionInputBeanCode']").val(data[i].commonObjectAttributeCode);
			$(newRow).find("label[name*='decisionInputBeanCode']").text(data[i].commonObjectAttributeCode);
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

function dtOnChangeExternalObjectOfIn(event){
	var obj = event.target;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	$(obj).closest("tr").nextAll("tr[data-ar-rgroup^='"+rgroup+"']").each(function() {
		$(this).remove();
	});
	if(event.item != undefined){
		$(obj).closest("tr").find("input[name*='decisionInputBeanName']").val(event.item.optionLabel);
		$(obj).closest("tr").find("input[name*='decisionInputBeanCode']").val(event.item.output01);
		$(obj).closest("tr").find("input[name*='objectId']").val(event.item.optionValue);
		$(obj).closest("tr").find("input[name*='objectType']").val(1);
		var table = $(obj).closest("table").attr("id");
		
		if(event.item.optionValue != ""){
			if(rgroup == undefined || rgroup == ""){
				level = 1;
			}else{
				level = (rgroup.match(/\./g) || []).length + 1;
			}
			var url = CONTEXT_PATH + "/externalobjectdefinition/getDetailsExternalObjectDefinition?externalObjectDefinitionId="+event.item.optionValue+"&level="+level+"&r="+Math.random();
			var data = $.qp.getData(url);
			if(data.length>0){
				
				var message = dbMsgSource["sc.decisiontable.0054"].replace("{0}", dbMsgSource["sc.sqldesign.0064"]).replace("{1}", event.item.optionLabel);
				var checkExcute = notAllowSelectExternalOrCommonContainArrayField(event, data,message);
				if(checkExcute) {
					$(obj).closest("tr").find("input[name*='decisionInputBeanCode']").val("");
					return;
				} 
				
				tempAnchor = obj;
				buildExternalObjectAttributesIn(data, obj);
			}
		}
	}else{
		$(obj).closest("tr").find("input[name*='decisionInputBeanName']").val("");
		$(obj).closest("tr").find("input[name*='decisionInputBeanCode']").val("");
		$(obj).closest("tr").find("input[name*='objectType']").val(-1);
		$(obj).closest("tr").find("input[name*='objectId']").val("");
	}
}
function buildExternalObjectAttributesIn(data, obj) {
	var tempParentId = null;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	var table = $(obj).closest("table").attr("id");
	
    for(var i=0;i<data.length;i++){
		if (data[i].dataType == 17) {
	    	var actionTemplateId = table+"-object-attribute-template";
			var newRow = $.qp.ar.addRow({link: obj,container:$(obj).closest('table'),templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});

			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			var curObj = data[i].externalObjectDefinition;

			$(newRow).find("input[name*='groupId']").val(rgroup);
			$(newRow).find("input[name*='tableIndex']").text(tableIndex);
			$(newRow).find("input[name*='decisionInputBeanName']").val(curObj.externalObjectDefinitionName);
			$(newRow).find("label[name*='decisionInputBeanName']").text(curObj.externalObjectDefinitionName);
			$(newRow).find("input[name*='decisionInputBeanCode']").val(curObj.externalObjectDefinitionCode);
			$(newRow).find("label[name*='decisionInputBeanCode']").text(curObj.externalObjectDefinitionCode);
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
			$(newRow).find("input[name*='decisionInputBeanName']").val(data[i].externalObjectAttributeName);
			$(newRow).find("label[name*='decisionInputBeanName']").text(data[i].externalObjectAttributeName);
			$(newRow).find("input[name*='decisionInputBeanCode']").val(data[i].externalObjectAttributeCode);
			$(newRow).find("label[name*='decisionInputBeanCode']").text(data[i].externalObjectAttributeCode);
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

function dtOnChangeCommonObjectOfOu(event){
	var obj = event.target;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	$(obj).closest("tr").nextAll("tr[data-ar-rgroup^='"+rgroup+"']").each(function() {
		$(this).remove();
	});
	if(event.item != undefined){
		$(obj).closest("tr").find("input[name*='decisionOutputBeanName']").val(event.item.optionLabel);
		$(obj).closest("tr").find("input[name*='decisionOutputBeanCode']").val(event.item.output01);
		$(obj).closest("tr").find("input[name*='objectType']").val(0);
		$(obj).closest("tr").find("input[name*='objectId']").val(event.item.optionValue);
		var table = $(obj).closest("table").attr("id");
		
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
				
				var message = dbMsgSource["sc.decisiontable.0054"].replace("{0}", dbMsgSource["sc.sqldesign.0064"]).replace("{1}", event.item.optionLabel);
				var checkExcute = notAllowSelectExternalOrCommonContainArrayField(event, data, message);
				if(checkExcute) {
					$(obj).closest("tr").find("input[name*='decisionOutputBeanCode']").val("");
					return;
				}
				
				tempAnchor = obj;
				buildCommonObjectAttributesOu(data, obj);
			}
		}
	}else{
		$(obj).closest("tr").find("input[name*='decisionOutputBeanName']").val("");
		$(obj).closest("tr").find("input[name*='decisionOutputBeanCode']").val("");
		$(obj).closest("tr").find("input[name*='objectType']").val(-1);
		$(obj).closest("tr").find("input[name*='objectId']").val("");
	}
}
function buildCommonObjectAttributesOu(data, obj) {
	var tempParentId = null;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	var table = $(obj).closest("table").attr("id");
	
    for(var i=0;i<data.length;i++){
		if (data[i].dataType == 16) {
	    	var actionTemplateId = table+"-object-attribute-template";
			var newRow = $.qp.ar.addRow({link: obj,container:$(obj).closest('table'),templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});
			
			var curObj = data[i].commonObjectDefinition;
			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			
			$(newRow).find("input[name*='groupId']").val(rgroup);
			$(newRow).find("input[name*='tableIndex']").text(tableIndex);
			$(newRow).find("input[name*='decisionOutputBeanName']").val(curObj.commonObjectDefinitionName);
			$(newRow).find("label[name*='decisionOutputBeanName']").text(curObj.commonObjectDefinitionName);
			$(newRow).find("input[name*='decisionOutputBeanCode']").val(curObj.commonObjectDefinitionCode);
			$(newRow).find("label[name*='decisionOutputBeanCode']").text(curObj.commonObjectDefinitionCode);
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
			$(newRow).find("input[name*='decisionOutputBeanName']").val(curObj.externalObjectDefinitionName);
			$(newRow).find("label[name*='decisionOutputBeanName']").text(curObj.externalObjectDefinitionName);
			$(newRow).find("input[name*='decisionOutputBeanCode']").val(curObj.externalObjectDefinitionCode);
			$(newRow).find("label[name*='decisionOutputBeanCode']").text(curObj.externalObjectDefinitionCode);
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
			$(newRow).find("input[name*='decisionOutputBeanName']").val(data[i].commonObjectAttributeName);
			$(newRow).find("label[name*='decisionOutputBeanName']").text(data[i].commonObjectAttributeName);
			$(newRow).find("input[name*='decisionOutputBeanCode']").val(data[i].commonObjectAttributeCode);
			$(newRow).find("label[name*='decisionOutputBeanCode']").text(data[i].commonObjectAttributeCode);
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

function dtOnChangeExternalObjectOfOu(event){
	var obj = event.target;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	$(obj).closest("tr").nextAll("tr[data-ar-rgroup^='"+rgroup+"']").each(function() {
		$(this).remove();
	});
	if(event.item != undefined){
		$(obj).closest("tr").find("input[name*='decisionOutputBeanName']").val(event.item.optionLabel);
		$(obj).closest("tr").find("input[name*='decisionOutputBeanCode']").val(event.item.output01);
		$(obj).closest("tr").find("input[name*='objectId']").val(event.item.optionValue);
		$(obj).closest("tr").find("input[name*='objectType']").val(1);
		var table = $(obj).closest("table").attr("id");
		
		if(event.item.optionValue != ""){
			if(rgroup == undefined || rgroup == ""){
				level = 1;
			}else{
				level = (rgroup.match(/\./g) || []).length + 1;
			}
			var url = CONTEXT_PATH + "/externalobjectdefinition/getDetailsExternalObjectDefinition?externalObjectDefinitionId="+event.item.optionValue+"&level="+level+"&r="+Math.random();
			var data = $.qp.getData(url);
			if(data.length>0){
				
				var message = dbMsgSource["sc.decisiontable.0054"].replace("{0}", dbMsgSource["sc.sqldesign.0065"]).replace("{1}", event.item.optionLabel);
				var checkExcute = notAllowSelectExternalOrCommonContainArrayField(event, data, message);
				if(checkExcute) {
					$(obj).closest("tr").find("input[name*='decisionOutputBeanCode']").val("");
					 return;
				}
				
				tempAnchor = obj;
				buildExternalObjectAttributesOu(data, obj);
			}
		}
	}else{
		$(obj).closest("tr").find("input[name*='decisionOutputBeanName']").val("");
		$(obj).closest("tr").find("input[name*='decisionOutputBeanCode']").val("");
		$(obj).closest("tr").find("input[name*='objectType']").val(-1);
		$(obj).closest("tr").find("input[name*='objectId']").val("");
	}
}
function buildExternalObjectAttributesOu(data, obj) {
	var tempParentId = null;
	var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
	var table = $(obj).closest("table").attr("id");
	
    for(var i=0;i<data.length;i++){
		if (data[i].dataType == 17) {
	    	var actionTemplateId = table+"-object-attribute-template";
			var newRow = $.qp.ar.addRow({link: obj,container:$(obj).closest('table'),templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor: $(tempAnchor).closest('tr'),string:'after'}});

			var tableIndex = $(newRow).closest("tr").attr("data-ar-rindex");
			var curObj = data[i].externalObjectDefinition;

			$(newRow).find("input[name*='groupId']").val(rgroup);
			$(newRow).find("input[name*='tableIndex']").text(tableIndex);
			$(newRow).find("input[name*='decisionOutputBeanName']").val(curObj.externalObjectDefinitionName);
			$(newRow).find("label[name*='decisionOutputBeanName']").text(curObj.externalObjectDefinitionName);
			$(newRow).find("input[name*='decisionOutputBeanCode']").val(curObj.externalObjectDefinitionCode);
			$(newRow).find("label[name*='decisionOutputBeanCode']").text(curObj.externalObjectDefinitionCode);
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
			$(newRow).find("input[name*='decisionOutputBeanName']").val(data[i].externalObjectAttributeName);
			$(newRow).find("label[name*='decisionOutputBeanName']").text(data[i].externalObjectAttributeName);
			$(newRow).find("input[name*='decisionOutputBeanCode']").val(data[i].externalObjectAttributeCode);
			$(newRow).find("label[name*='decisionOutputBeanCode']").text(data[i].externalObjectAttributeCode);
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

/** End processing */