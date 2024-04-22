/**
 * formula setting
 * @author quangvd
 */
$.qp.formulabuilder = (function($$module){

	var mapFullCodeOfFormula = [];
	var BD_MODAL_NAME_FORMULA = "#dialog-formula-setting";
	$$module.mapFullCodeOfFormula = mapFullCodeOfFormula;
	$$module.init = function(isView){
		$(function(){
			var modal = BD_MODAL_NAME_FORMULA;
			$(modal).find("#bdFormulaValueFormula").sortable();
			$(modal).find("#bdFormulaValueFormula").disableSelection();
		});
	};
	$$module.initialDataForComponentOperators = function(obj,status){
		/*
		 * status
		 * 0 : new
		 * 1 : load from db
		 */
		if(obj.type != undefined){
			var template = "#tbl-formula-content-parameter-template";
			var label=obj.label;
			switch (obj.type.toString()) {
			case "0":
				label = "+";
				obj.type = 0;
				break;
			case "1":
				label = "-";
				obj.type = 1;
				break;
			case "2":
				label = "*";
				obj.type = 2;
				break;
			case "3":
				label = "/";
				obj.type = 3;
				break;
			case "4":
				label = "and";
				obj.type = 4;
				break;
			case "5":
				label = "or";
				obj.type = 5;
				break;
			case "6":
				label = "not";
				obj.type = 6;
				break;
			case "7":
				label = "=";
				obj.type = 7;
				break;
			case "8":
				label = "<";
				obj.type = 8;
				break;
			case "9":
				label = "<=";
				obj.type = 9;
				break;
			case "10":
				label = ">";
				obj.type = 10;
				break;
			case "11":
				label = ">=";
				obj.type = 11;
				break;
			case "12":
				label = "<>";
				obj.type = 12;
				break;
			case "13":
				label = "(";
				obj.type = 13;
				break;
			case "14":
				label = ")";
				obj.type = 14;
				break;
			case "15":
				label = "Empty";
				obj.type = 15;
				break;
			case "16":
				obj.type = 16;
				obj.value = (obj.value == undefined || obj.value==null) ? "" : obj.value;
				template = "#tbl-formula-content-value-template";
				break;
			case "18":
				if(status == "1"){
					label = ($$module.mapFullCodeOfFormula!= undefined && $$module.mapFullCodeOfFormula["18"+obj.parameterId] != undefined) ? $$module.mapFullCodeOfFormula["18"+obj.parameterId] : obj.parameterFullCode;
				}else{
					label = obj.parameterFullCode;
				}
				obj.type = 18;
				break;
			case "19":
				if(status == "1"){
					label = ($$module.mapFullCodeOfFormula!= undefined && $$module.mapFullCodeOfFormula["19"+obj.parameterId] != undefined) ? $$module.mapFullCodeOfFormula["19"+obj.parameterId] : obj.parameterFullCode;
				}else{
					label = obj.parameterFullCode;
				}
				obj.type = 19;
				break;
			case "20":
				if(status == "1"){
					label = ($$module.mapFullCodeOfFormula!= undefined && $$module.mapFullCodeOfFormula["20"+obj.parameterId] != undefined) ? $$module.mapFullCodeOfFormula["20"+obj.parameterId] : obj.parameterFullCode;
				}else{
					label = obj.parameterFullCode;
				}
				obj.type = 20;
				break;
			case "22":
				label = "Null";
				obj.type = 22;
				break;
			case "23":
				if(status == "1"){
					label = ($$module.mapFullCodeOfFormula!= undefined && $$module.mapFullCodeOfFormula["23"+obj.parameterId] != undefined) ? $$module.mapFullCodeOfFormula["23"+obj.parameterId] : obj.parameterFullCode;
				}else{
					label = obj.parameterFullCode;
				}
				obj.type = 23;
				break;
			default:
				break;
			}
			var hiddenValue = $$module.convertToString(obj);
			var divContent = $(template).tmpl({label:label,hiddenValue:hiddenValue,type:obj.type,value:obj.value});
			return divContent;
		}else{
			return "";
		}
		
	}
	
	$$module.initialDataForFormulaSetting = function(obj,arg1,arg2){
		var dataSource = [];
		if(typeof jQuery.namespace($(obj).attr("sourceCallback")) == "function") {
			dataSource = jQuery.namespace($(obj).attr("sourceCallback"))();
		}
		var targetLabel;
		if(typeof jQuery.namespace($(obj).attr("targetLabel")) == "function") {
			targetLabel = jQuery.namespace($(obj).attr("targetLabel"))(obj);
		}
		var targetValue;
		if(typeof jQuery.namespace($(obj).attr("targetValue")) == "function") {
			targetValue = jQuery.namespace($(obj).attr("targetValue"))(obj);
		}
		$$module.openModalFormulaSetting(obj,targetLabel,targetValue,dataSource);
	}
	$$module.initialDataForFunctionOperators = function(objFunction){
		var template = "#tbl-formula-content-function-template";
		var divContent = $(template).tmpl();
		var divDetail = objFunction.functionMasterCode +"."+objFunction.functionMethodCode;
		var obj = new Object();
		obj.type = 17;
		obj.functionMethodId = objFunction.functionMethodId;
		obj.functionMethodCode = objFunction.functionMethodCode;
		obj.functionMasterCode = objFunction.functionMasterCode;
		divDetail+="(";
		for(var i=0; i < objFunction.functionMethodInput.length;i++){
			divDetail+=",";
		}
		divDetail+=")";
		$(divContent).find("div.qp-formula-component-content").append(divDetail);
		$(divContent).find("input[name=formulaitem]").val($$module.convertToString(obj));
		$(divContent).find("div.qp-formula-component-content").attr("hiddenvalue",$$module.convertToString(objFunction));
		return divContent;
		
	}
	$$module.loadDataForFunctionOperators = function(objFunction,objData){
		var template = "#tbl-formula-content-function-template";
		var divContent = $(template).tmpl();
		var divDetail = objFunction.functionMasterCode +"."+objFunction.functionMethodCode;
		objData.functionMethodCode = objFunction.functionMethodCode;
		objData.functionMasterCode = objFunction.functionMasterCode;
		divDetail+="(";
		for(var i=0; i < objFunction.functionMethodInput.length;i++){
			var input = objFunction.functionMethodInput[i];
			if(objData.formulaMethodInputs != undefined){
				for(var j=0;j< objData.formulaMethodInputs.length;j++){
					var tempInput = objData.formulaMethodInputs[j];
					if(tempInput.methodInputId == input.methodInputId){
						if(tempInput.parameterScope == "0"){
							divDetail+= (tempInput.parameterValue == undefined || tempInput.parameterValue == null)? "":tempInput.parameterValue;
						}else{
							var parameterIdAutocomplete = ($$module.mapFullCodeOfFormula!= undefined && $$module.mapFullCodeOfFormula[tempInput.parameterScope+tempInput.parameterId] != undefined) ? $$module.mapFullCodeOfFormula[tempInput.parameterScope+tempInput.parameterId] : "";
							divDetail+= parameterIdAutocomplete;
						}
						break;
					}
				}
				if(i+1 < objFunction.functionMethodInput.length)
					divDetail+=",";
			}
		}
		
		divDetail+=")";

		//set output
		if(objData.formulaMethodOutputs != undefined && objData.formulaMethodOutputs.length >0){
			for(var i=0; i < objFunction.functionMethodOutput.length;i++){
				var output = objFunction.functionMethodOutput[i];
				var tempOutput = objData.formulaMethodOutputs[0];
				if(tempOutput.methodOutputId == output.methodOutputId){
					divDetail+="." + output.methodOutputCode;
				}
			}
		}
		
		$(divContent).find("div.qp-formula-component-content").append(divDetail);
		$(divContent).find("input[name=formulaitem]").val($$module.convertToString(objData));
		$(divContent).find("div.qp-formula-component-content").attr("hiddenvalue",$$module.convertToString(objFunction));
		return divContent;
		
	}
	$$module.onSelectComponentBean = function(thiz){
		var contentDiv = $(thiz).closest("div.modal-content").find("#bdFormulaValueFormula");
		var value = $(thiz).closest("li").find("input[name=detailparameter]").val() == undefined ? null: $(thiz).closest("li").find("input[name=detailparameter]").val();
		var data = $$module.convertToJson(value);
		var divContent =  $$module.initialDataForComponentOperators(data,"0");
		$(contentDiv).append(divContent);
	}
	$$module.onSelectComponent = function(thiz,type){
		var contentDiv = $(thiz).closest("div.modal-content").find("#bdFormulaValueFormula");
		var obj = new Object();
		obj.type = type;
		var divContent =  $$module.initialDataForComponentOperators(obj,"0");
		$(contentDiv).append(divContent);
	}
	$$module.onSelectFunctionOperators = function(event){
		var selectedBox = event.srcElement;
		var contentDiv = $(selectedBox).closest("div.modal-content").find("#bdFormulaValueFormula");
		var data = $(selectedBox).find("option[value="+event.srcElement.value+"]").attr("method");
		var value = $$module.convertToJson(data);
		var divContent =  $$module.initialDataForFunctionOperators(value);
		$(contentDiv).append(divContent);
		$(selectedBox).val(0);
	}
	$$module.openModalFormulaSetting = function(obj,objValue,objLabel,parameters){
		var modal = BD_MODAL_NAME_FORMULA;
		$(modal).data("target", obj);
		var onbeforeopen = $(obj).attr("onbeforeopen");
		if(typeof jQuery.namespace(onbeforeopen) == "function") {
			jQuery.namespace(onbeforeopen)(obj);
		}
		var targetLabel = objValue;
		var targetValue = objLabel;
		$(modal).data("targetLabel", targetLabel);
		$(modal).data("targetValue", targetValue);
		$(modal).data("target", obj);
		$(modal).find("#formulaName").val($(obj).closest("th").find('input[name$="formulaName"]').val());
		//get function
		var url = CONTEXT_PATH + "/businessdesign/getFuntionOfProject?&r="+Math.random();
		var arrRequestData = $.qp.getData(url);

		var cbFunction = "#cbFunctionMaster";
		$(modal).find(cbFunction).empty();
		$(modal).find(cbFunction).append("<option value='0' selected='selected'>--Select function--</option>");
		for(var i =0; i < arrRequestData.length;i++){
			var functionMaster = arrRequestData[i];
			if(functionMaster != undefined && functionMaster.functionMethod != undefined && functionMaster.functionMethod.length >0){
				var functionMasterCode = functionMaster.functionMasterCode == undefined ? "" : functionMaster.functionMasterCode;
				$(modal).find(cbFunction).append("<optgroup label='"+functionMasterCode+"'>");
				for(var j =0; j < functionMaster.functionMethod.length;j++){
					var functionMethod = functionMaster.functionMethod[j];
					functionMethod.functionMasterCode = functionMasterCode;
					var templateFunctionMethod = $("#tbl-formula-select-function-template").tmpl({functionMethod:$$module.convertToString(functionMethod),functionMethodId:functionMethod.functionMethodId,functionMethodCode:functionMethod.functionMethodCode});
					$(modal).find(cbFunction).append(templateFunctionMethod);
				}
				$(modal).find(cbFunction).append("</optgroup>");
			}
		}
		
		$(modal).find("#scopeParameter").empty();
		if(parameters != undefined){
			$(modal).find("input[name=parameterItems]").val($$module.convertToString(parameters));
			$(modal).find("#scopeParameter").parent().show();
			for(var i=0;i< parameters.length;i++){
				var data = parameters[i];
				var templateParent = $("#tbl-scopeparameter-parent-template").tmpl({id:data.id,label:data.label});
				$(modal).find("#scopeParameter").append(templateParent);
				for(var j=0;j<data.datas.length;j++){
					var objData = data.datas[j];
					var templateChildren = $("#tbl-scopeparameter-children-template").tmpl({id:objData.parameterId,code:objData.parameterCode,paddingLeft:objData.paddingLeft});
					$(templateChildren).find("input[name=detailparameter]").val($$module.convertToString(objData));
					$(modal).find("#scopeParameter").find("ul[id="+data.id+"]").append(templateChildren);
				}
			}
		}else{
			$(modal).find("#scopeParameter").parent().hide();
		}
		$(modal).find("#bdFormulaValueFormula").empty();
		var data = $(targetValue).val();
		data = $$module.convertToJson(data);
		var contentDiv = $(modal).find("#bdFormulaValueFormula");
		for(var j=0;j<data.length;j++){
			var objData = data[j];
			var templateComponent;
			if(objData.type == "17"){
				var dataOfFunction = $(modal).find("#cbFunctionMaster").find("option[value="+objData.functionMethodId+"]").attr("method");
				var value = $$module.convertToJson(dataOfFunction);
				templateComponent = $$module.loadDataForFunctionOperators(value,objData);
			}else{
				templateComponent = $$module.initialDataForComponentOperators(objData,"1");
			}
			$(contentDiv).append(templateComponent);
		}
		
		 $(modal).find('label.tree-toggler').click(function() {
		        $(this).parent().children('ul.tree').toggle(300);
		    });
		 $(modal).modal(
				  { 
				   show: true,
				   closable: false,
				   keyboard:false,
				   backdrop:'static'
				  }
				 );
	}
	$$module.deleteModalFormulaSetting = function(thiz){
		var modal = BD_MODAL_NAME_FORMULA;
		var targetLabel = $(modal).data("targetLabel");
		var targetValue = $(modal).data("targetValue");
		var target = $(modal).data("target");
		if($(targetLabel).prop('tagName') == "INPUT"){
			$(targetLabel).val("");
		}else{
			$(targetLabel).text("");
		}
		$(targetValue).val($$module.convertToString([]));
		$(modal).modal("hide");
		
		var onafterdelete = $(target).attr("onafterdelete");
		if(typeof jQuery.namespace(onafterdelete) == "function") {
			jQuery.namespace(onafterdelete)(target);
		}
	}
	$$module.saveModalFormulaSetting = function(thiz){
		var modal = BD_MODAL_NAME_FORMULA;
		var targetLabel = $(modal).data("targetLabel");
		var targetValue = $(modal).data("targetValue");
		var target = $(modal).data("target");
		var content = "";
		var formulaDefinitionDetails = [];
		if($(modal).find("#bdFormulaValueFormula").find("div.qp-formula-component").length > 0){
			$(modal).find("#bdFormulaValueFormula").find("div.qp-formula-component").each(function (index){
				var type = $(this).attr("type");
				var value = $(this).find("input[name=formulaitem]").val() == undefined ? null : $(this).find("input[name=formulaitem]").val();
				var item = $$module.convertToJson(value);
				item.itemSequenceNo = index;
				
				if(type == "16"){
					var input = $(this).find("div.qp-formula-component-content").find("input[type=text]").val() == undefined ? "" : $(this).find("div.qp-formula-component-content").find("input[type=text]").val();
					content +=  input + " ";
					item.value = input;
				} else if(type == "17") {
					content += $(this).find("div.qp-formula-component-content").text() + " ";
				} else {
					content += $(this).find("div.qp-formula-component-content").text() + " ";
				}
				formulaDefinitionDetails.push(item);
			});
			if($(targetLabel).prop('tagName') == "INPUT"){
				$(targetLabel).val(content.trim());
			}else{
				$(targetLabel).text(content.trim());
			}
			$(targetValue).val($$module.convertToString(formulaDefinitionDetails));
			$(modal).modal("hide");
			
			var onaftersave = $(target).attr("onaftersave");
			if(typeof jQuery.namespace(onaftersave) == "function") {
				jQuery.namespace(onaftersave)(target,formulaDefinitionDetails);
			}
		}else{
			alert("The data is invalid");
		}
	}
	
	
	//remove component
	$$module.onRemoveComponent = function(thiz){
		$(thiz).parent().remove();
	}
	$$module.onRemoveAll = function(){
		var modal = BD_MODAL_NAME_FORMULA;
		$(modal).find("#bdFormulaValueFormula").empty();
	}
	$$module.onValidationFormula = function(){
		alert("validation formula");
	}
	$$module.onOpenFunctionFormulaSetting = function(obj){
		var modal = "#dialog-function-detail-setting";
		$(modal).data("target", obj);
		var value = $(obj).attr("hiddenvalue") == undefined ? null : $(obj).attr("hiddenvalue");
		var data = $$module.convertToJson(value);
		var tableInput = "#tbl-function-detail-input";
		var tableOutput = "#tbl-function-detail-output";
		$(modal).find(tableInput).find("tbody").empty();
		$(modal).find(tableOutput).find("tbody").empty();
		if(data.functionMethodInput != undefined){
			for(var i=0; i< data.functionMethodInput.length;i++){
				var objInput = data.functionMethodInput[i];
				objInput.index = i+1;
				var dataTypeStr="";
				if(CL_FORMULA_BUILDER_DATATYPE != undefined){
					dataTypeStr = CL_FORMULA_BUILDER_DATATYPE[objInput.dataType];
				}
				if(eval(objInput.arrayFlgDisplay)){
					dataTypeStr = dataTypeStr + "[]";
				}
				objInput.dataTypeStr = dataTypeStr;
				var templateInput = $(modal).find("#tbl-function-detail-input-template").tmpl(objInput);
				$(modal).find(tableInput).find("tbody").append(templateInput);
			}
		}
		if(data.functionMethodOutput != undefined){
			for(var i=0; i< data.functionMethodOutput.length;i++){
				var objOutput = data.functionMethodOutput[i];
				objOutput.index = i+1;
				var dataTypeStr="";
				if(CL_FORMULA_BUILDER_DATATYPE != undefined){
					dataTypeStr = CL_FORMULA_BUILDER_DATATYPE[objOutput.dataType];
				}
				objOutput.dataTypeStr = dataTypeStr;
				if(eval(objOutput.arrayFlgDisplay)){
					dataTypeStr = dataTypeStr + "[]";
				}
				objOutput.dataTypeStr = dataTypeStr;
				var templateOuput = $(modal).find("#tbl-function-detail-output-template").tmpl(objOutput);
				$(modal).find(tableOutput).find("tbody").append(templateOuput);
			}
		}
		
		//bind data
		var item = $(obj).parent().find("input[name=formulaitem]").val()==undefined ? new Object() : $$module.convertToJson($(obj).parent().find("input[name=formulaitem]").val());
		if(item.formulaMethodOutputs !=undefined && item.formulaMethodOutputs.length >0){
			var output= item.formulaMethodOutputs[0];
			var outputRow = $(modal).find(tableOutput).find("input[name=methodOutputId][value="+output.methodOutputId+"]").closest("tr");
			$(outputRow).find("input[type=radio][name=outputSelect]").prop("checked",true);
		}
		if(item.formulaMethodInputs !=undefined){
			for(var i=0;i<item.formulaMethodInputs.length;i++){
				var input= item.formulaMethodInputs[i];
				var inputRow =  $(modal).find(tableInput).find("input[name=methodInputId][value="+input.methodInputId+"]").closest("tr");
				if(inputRow != undefined){
					$(inputRow).find("td[type=value]").hide();
					$(inputRow).find("td[type=parameter]").hide();
					var parameterScope = (input.parameterScope == undefined || input.parameterScope == null) ? "" :  input.parameterScope;
					if(parameterScope == "0"){
						var parameterValue = (input.parameterValue == undefined || input.parameterValue == null) ? "" :  input.parameterValue;
						$(inputRow).find("input[type=radio][name*=parameterScope][value=0]").prop("checked",true);
						$(inputRow).find("input[name=parameterValue]").val(parameterValue);
						$(inputRow).find("td[type=value]").show();
					}else{
						$(inputRow).find("input[type=radio][name*=parameterScope][value=1]").prop("checked",true);
						var parameterId = (input.parameterId == undefined || input.parameterId == null) ? "" :  input.parameterId;
						$(inputRow).find("input[name=parameterId]").val(parameterId);
						var parameterIdAutocomplete = ($$module.mapFullCodeOfFormula!= undefined && $$module.mapFullCodeOfFormula[parameterScope+parameterId] != undefined) ? $$module.mapFullCodeOfFormula[parameterScope+parameterId] : "";
						$(inputRow).find("input[name=parameterIdAutocomplete]").val(parameterIdAutocomplete);
						$(inputRow).find("input[name=parameterId]").attr("parameterScope",parameterScope);
						$(inputRow).find("td[type=parameter]").show();
					}
				}
			}
		}
		$.qp.initialAllPicker(modal);
		$(modal).modal(
				  { 
				   show: true,
				   closable: false,
				   keyboard:false,
				   backdrop:'static'
				  }
				 );
	}
	$$module.onSaveFunctionFormulaSetting = function(thiz){
		var modal = "#dialog-function-detail-setting";
		var obj = $(modal).data("target");
		var tableInput = "#tbl-function-detail-input";
		var tableOutput = "#tbl-function-detail-output";
		var item = $(obj).parent().find("input[name=formulaitem]").val()==undefined ? new Object() : $$module.convertToJson($(obj).parent().find("input[name=formulaitem]").val());
		var count =$(modal).find(tableInput).find("tbody").find("tr").length;
		
		if(item.functionMethodCode != undefined){
			var formulaMethodInputs = [];
			var formulaMethodOutputs = [];
			var content = item.functionMasterCode +"."+item.functionMethodCode;
			content+="(";
			$(modal).find(tableInput).find("tbody").find("tr").each(function(index){
				var objDetailInput = new Object();
				var parameterIdAutocomplete = null;
				var parameterId = null;
				var parameterScope = null;
				var parameterValue = null;
				
				parameterScope =  $(this).find("input[type=radio][name*=parameterScope]:checked").val();
				if(parameterScope == "0"){
					objDetailInput.parameterScope = 0;
					parameterValue = $(this).find("input[name=parameterValue]").val() == undefined ? null : $(this).find("input[name=parameterValue]").val();
					content+=parameterValue;
				}else if(parameterScope == "1"){
					parameterId = ($(this).find("input[name='parameterId']").val() == undefined || $(this).find("input[name='parameterId']").val() == "" ) ? null : $(this).find("input[name='parameterId']").val();
					if(parameterId != null){
						parameterIdAutocomplete = ($(this).find("input[name='parameterIdAutocomplete']").val() == undefined  || $(this).find("input[name='parameterIdAutocomplete']").val() == "" )? null : $(this).find("input[name='parameterIdAutocomplete']").val();
						parameterScope = ($(this).find("input[name='parameterId']").attr("parameterScope") == undefined || $(this).find("input[name='parameterId']").attr("parameterScope") == "") ? null : $(this).find("input[name='parameterId']").attr("parameterScope");
						content+=(parameterIdAutocomplete != null ? parameterIdAutocomplete : "");
					}
				}
				objDetailInput.parameterScope = parameterScope;
				objDetailInput.parameterValue = parameterValue;
				objDetailInput.parameterId = parameterId;
				
				var methodInputId = $(this).find("input[name=methodInputId]").val() == undefined ? null : $(this).find("input[name=methodInputId]").val();
				objDetailInput.methodInputId = methodInputId;
				objDetailInput.functionMethodId = item.functionMethodId;
				
				formulaMethodInputs.push(objDetailInput);
				
				if(index+1 < count){
					content+=",";
				}
			});
			content+=")";
			item.formulaMethodInputs = formulaMethodInputs;
			
			var outputRow = $(modal).find(tableOutput).find("input[type=radio][name=outputSelect]:checked").closest("tr");
			if(outputRow.length > 0){
				var output = new Object();
				var methodOutputId = $(outputRow).find("input[name=methodOutputId]").val() == undefined ? null : $(outputRow).find("input[name=methodOutputId]").val();
				output.methodOutputId = methodOutputId;
				formulaMethodOutputs.push(output);
				item.formulaMethodOutputs = formulaMethodOutputs;
				
				var methodOutputCode = $(outputRow).find("label[name=methodOutputCode]").text() == undefined ? "" : $(outputRow).find("label[name=methodOutputCode]").text();
				content+="."+methodOutputCode;
			}else{
				alert("Please choose output for function");
				return;
			}
			
			$(obj).text(content);
			$(obj).parent().find("input[name=formulaitem]").val($$module.convertToString(item));
		}else{
			alert("This function is not exist");
			return;
		}
		
		$(modal).modal('hide');
	}
	$$module.deleteFunctionFormulaSetting = function(thiz){
		var modal=$(thiz).closest(".modal");
		var obj = $(modal).data("target");
		$(obj).parent().remove();
		$(modal).modal('hide');
	}
	$$module.buildDataSourceForBusinessLogic = function(){
		var parameters = [];
		var sourceData = $$module.convertToJson($("form").find("input[name$='jsonOutputBean']").val());
		var mKeyCode = new Array();
		if(sourceData.length >0){
			var data = new Object();
			data.id = "ouOfBD";
			data.label = "Output Bean";
			data.datas = [];
			for(var i=0;i<sourceData.length;i++){
				var objContent = new Object();
				var objData = sourceData[i];
				objContent.type = "23";
				objContent.parameterId = objData.outputBeanId;
				objContent.parameterCode = objData.outputBeanCode;
				var fullCode="";
				if(objData.parentOutputBeanId != undefined && objData.parentOutputBeanId != ""){
					fullCode = mKeyCode["0"+objData.parentOutputBeanId]+"."+objData.outputBeanCode; 
				}else{
					fullCode = "ou."+ objData.outputBeanCode;
				}
				mKeyCode["0"+objData.outputBeanId] = fullCode;
				objContent.parameterFullCode = fullCode;
				var paddingLeft = (objData.tableIndex.split(".").length * 10)+"px";
				objContent.paddingLeft = paddingLeft;
				data.datas.push(objContent);
				$$module.mapFullCodeOfFormula["23"+objData.outputBeanId] = fullCode;
			}
			parameters.push(data);
		}
		
		return parameters;
	}
	$$module.onChangeParameterScopeOfFunction = function(thiz){
		var selectedType = $(thiz).val();
		$(thiz).closest("tr").find("td[type=value]").hide();
		$(thiz).closest("tr").find("td[type=parameter]").hide();
		if(selectedType == "0"){
			$(thiz).closest("tr").find("td[type=value]").show();
		}else{
			$(thiz).closest("tr").find("td[type=parameter]").show();
		}
	}
	$$module.getDataSourceAutocompleteFormula = function(event){
		var searchKey = event.searchKey;
		var dataResult = [];
		var data = $(BD_MODAL_NAME_FORMULA).find("input[name=parameterItems]").val();
		if(data!=undefined){
			var sourceData = $$module.convertToJson(data);
			if(sourceData != undefined && sourceData.length > 0) {
				for(var i = 0; i < sourceData.length; i++) {
					if(sourceData[i].datas != undefined){
						for(var j = 0; j < sourceData[i].datas.length; j++) {
							var objTmp = new Object();
							var objItemData = sourceData[i].datas[j];
							objTmp.optionValue = objItemData.parameterId;
							objTmp.optionLabel = objItemData.parameterFullCode;
							objTmp.output01 = objItemData.type;
							var index = objTmp.optionLabel.indexOf(searchKey);
							if(index >-1){
								dataResult.push(objTmp);
							}
						}
					}
				}
			}
		}
		return dataResult;
	}
	$$module.setParameterTypeOfAutocompleteFormula = function(event){
		var obj = event.target;
		if(event.item != undefined){
			var type = event.item.output01;
			$(obj).next().attr("parameterScope",type);
		}
		else{
			$(obj).next().attr("parameterScope","");
		}
	}

	$$module.buildDataSourceForDecisionTableDesign = function() {
		var parameters = [];
		var sourceData = $$module.convertToJson($("form").find("input[name='listInput']").val());
		var mKeyCode = new Array();
		if(sourceData.length >0){
			var data = new Object();
			data.id = "inOfBD";
			data.label = "Input Bean";
			data.datas = [];
			for(var i=0;i <sourceData.length; i++){
				var objContent = new Object();
				var objData = sourceData[i];
				objContent.type = "20";
				objContent.parameterId = objData.decisionInputBeanId;
				objContent.parameterCode = objData.decisionInputBeanCode;
				var fullCode="";
				var paddingLeft;
				
				if(objData.parentDecisionInputBeanId != undefined && objData.parentDecisionInputBeanId != ""){
					fullCode = mKeyCode["0"+objData.parentDecisionInputBeanId]+"."+objData.decisionInputBeanCode;
					paddingLeft = "20px";
				}else{
					fullCode = "in."+objData.decisionInputBeanCode;
					paddingLeft = "10px";
				}
				mKeyCode["0"+objData.decisionInputBeanId] = fullCode;
				objContent.parameterFullCode = fullCode;
				objContent.paddingLeft = paddingLeft;
				data.datas.push(objContent);
				$$module.mapFullCodeOfFormula["20"+objData.decisionInputBeanId] = fullCode;
			}
			parameters.push(data);
		}
		
		return parameters;
	}

	
	$$module.convertToJson = function(string) {
		var json = {};
		if(!string){string="";}
		try {
			json = JSON.parse(string);
		} catch (e) {
			json = JSON.parse("{" + string + "}");
		}
		return json;
	};

	$$module.convertToString = function(json) {
		return  JSON.stringify(json);
	};
return $$module;
})(jQuery.namespace('$.qp.formulabuilder'));