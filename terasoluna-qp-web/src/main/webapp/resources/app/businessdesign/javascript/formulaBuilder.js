/**
 * formula setting
 * @author quangvd
 */
$.qp.formulabuilder = (function($$module){

	var mapFullCodeOfFormula = [];
	var mapFullParameterOfFormula = [];
	var BD_MODAL_NAME_FORMULA = "#dialog-formula-setting";
	$$module.mapFullCodeOfFormula = mapFullCodeOfFormula;
	$$module.mapFullParameterOfFormula = mapFullParameterOfFormula;
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
		var divContent = "";
		if(obj.type != undefined){
			var template = "#tbl-formula-content-parameter-template";
			var label=obj.label;
			if(obj.type != "18" && obj.type != "19" && obj.type != "20"){
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
//				case "18":
//					var parameter = $$module.getParameter("18"+obj.parameterId);
//					if(parameter != undefined && parameter.arrayFlg){
//						template = "#tbl-formula-content-parameter-index-template";
//					}
//					if(status == "1"){
//						label = ($$module.mapFullCodeOfFormula!= undefined && $$module.mapFullCodeOfFormula["18"+obj.parameterId] != undefined) ? $$module.mapFullCodeOfFormula["18"+obj.parameterId] : obj.parameterFullCode;
//					}else{
//						label = obj.parameterFullCode;
//					}
//					obj.type = 18;
//					buildIndexOfParameter
//					break;
//				case "19":
//					var parameter = $$module.getParameter("19"+obj.parameterId);
//					if(parameter != undefined && parameter.arrayFlg){
//						template = "#tbl-formula-content-parameter-index-template";
//					}
//					if(status == "1"){
//						label = ($$module.mapFullCodeOfFormula!= undefined && $$module.mapFullCodeOfFormula["19"+obj.parameterId] != undefined) ? $$module.mapFullCodeOfFormula["19"+obj.parameterId] : obj.parameterFullCode;
//					}else{
//						label = obj.parameterFullCode;
//					}
//					obj.type = 19;
//					break;
//				case "20":
//					var parameter = $$module.getParameter("20"+obj.parameterId);
//					if(parameter != undefined && parameter.arrayFlg){
//						template = "#tbl-formula-content-parameter-index-template";
//					}
//					if(status == "1"){
//						label = ($$module.mapFullCodeOfFormula!= undefined && $$module.mapFullCodeOfFormula["20"+obj.parameterId] != undefined) ? $$module.mapFullCodeOfFormula["20"+obj.parameterId] : obj.parameterFullCode;
//					}else{
//						label = obj.parameterFullCode;
//					}
//					obj.type = 20;
//					break;
				case "22":
					label = "Null";
					obj.type = 22;
					break;
				case "23":
					obj.type = 23;
					obj.value = (obj.value == undefined || obj.value==null) ? "" : obj.value;
					template = "#tbl-formula-content-value-template";
					break;	
				case "24":
					label = "ResultMessages.info.size()";
					obj.type = 24;
					break;
				case "25":
					label = "ResultMessages.warn.size()";
					obj.type = 25;
					break;
				case "26":
					label = "ResultMessages.error.size()";
					obj.type = 26;
					break;
				case "27":
					label = "new Object()";
					obj.type = 27;
					break;
				default:
					break;
				}
				var hiddenValue = $$module.convertToString(obj);
				divContent = $(template).tmpl({label:label,hiddenValue:hiddenValue,type:obj.type,value:obj.value});
			}else{
				var hiddenValue = $$module.convertToString(obj);
				divContent =  $$module.buildIndexOfParameter(obj.parameterId,obj.type);
				$(divContent).find("input[name='formulaitem']").val(hiddenValue);
				//bind index
				if(status == "1"){
					//bind data
					for (var int2 = 0; int2 < obj.lstParameterIndex.length; int2++) {						
						var itemIndex = obj.lstParameterIndex[int2];
						
						if (itemIndex != null) {
							var selectedIndex = $(divContent).find("input[name='parameterIndexId'][parameterId='"+itemIndex.parameterId+"']");
							if(selectedIndex.length > 0){
	//							$(selectedIndex).prev().val(itemIndex.parameterIndexIdAutocomplete);
								$(selectedIndex).val(itemIndex.parameterIndexId);
								$(selectedIndex).attr("indextype",itemIndex.parameterIndexType);
								
								switch (itemIndex.parameterIndexType.toString()) {
								case "3":
									$(selectedIndex).prev().val(itemIndex.parameterIndexId);
									break;
								case "4":
									var arrIndexLoop = getLabelOfIndexLoop();
									var actualIndex = itemIndex.parameterIndexId.substring(1,itemIndex.parameterIndexId.length);
									var labelIndex = arrIndexLoop[actualIndex];
									if(labelIndex != undefined){
										$(selectedIndex).prev().val(labelIndex);
									}else{
										$(selectedIndex).prev().val("");
									}
									break;
								case "0":
								case "1":
								case "2":
	//								var inforIndex = getInformationOfParameter(itemIndex.parameterIndexId);
	//								if(inforIndex != undefined){
	//									$(selectedIndex).prev().val(inforIndex.optionLabel);
	//								}else{
	//									$(selectedIndex).prev().val("");
	//								}
									
									var prefix;
									switch (itemIndex.parameterIndexType.toString()) {
									case "0":
										prefix = "18";
										break;
									case "1":
										prefix = "19";
										break;
									case "2":
										prefix = "20";
										break;
	
									default:
										break;
									}
									
									var label = "";
									if(itemIndex.parameterIndexId.length > 0){
										var parameterIndexId = itemIndex.parameterIndexId.substring(1,itemIndex.parameterIndexId.length);
										label = ($$module.mapFullCodeOfFormula!= undefined && $$module.mapFullCodeOfFormula[prefix+parameterIndexId] != undefined) ? $$module.mapFullCodeOfFormula[prefix+parameterIndexId] : "";
									}
									$(selectedIndex).prev().val(label);
								default:
									break;
								}
							}
						}
					}
				}
			}
			$.qp.initialAllPicker($(divContent));
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
		
		// Add resultmessage
		
		var objResult = new Object();
		objResult.id = "resultMessage";
		objResult.label = "Result Message";
		objResult.datas = [
		             {
						arrayFlg : false,
						dataType : 0,
						paddingLeft : "5px",
						parameterCode : "infor",
						parameterFullCode : "ResultMessage.infor.size()",
//						parameterId : "0",
//						parentParameterId : null,
						type : "24"
	             	},
	             	{
						arrayFlg : false,
						dataType : 0,
						paddingLeft : "5px",
						parameterCode : "warn",
						parameterFullCode : "ResultMessage.warn.size()",
//						parameterId : "0",
//						parentParameterId : null,
						type : "25"
	             	},
	             	{
						arrayFlg : false,
						dataType : 0,
						paddingLeft : "5px",
						parameterCode : "error",
						parameterFullCode : "ResultMessage.error.size()",
//						parameterId : "0",
//						parentParameterId : null,
						type : "26"
	             	}
		             ];
		dataSource.push(objResult);
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
		if(objFunction != undefined){
			var template = "#tbl-formula-content-function-template";
			var divContent = $(template).tmpl();
			var labelContent = $(divContent).find("div.qp-formula-component-content");
			$(labelContent).append(objFunction.functionMasterCode +"."+objFunction.functionMethodCode);
			objData.functionMethodCode = objFunction.functionMethodCode;
			objData.functionMasterCode = objFunction.functionMasterCode;
			$(labelContent).append("(");
			
			for(var i=0; i < objFunction.functionMethodInput.length;i++){
				var input = objFunction.functionMethodInput[i];
				if(objData.formulaMethodInputs != undefined){
					for(var j=0;j< objData.formulaMethodInputs.length;j++){
						var tempInput = objData.formulaMethodInputs[j];
						
						if(tempInput.methodInputId == input.methodInputId){
							if(tempInput.parameterType == "1"){
								$(labelContent).append((tempInput.parameterValue == undefined || tempInput.parameterValue == null)? "":tempInput.parameterValue);
							}else{
								var arrIndexLoop = getLabelOfIndexLoop();
								
								var inforCurrentParam = $$module.getInformationOfParameterFormula(tempInput.parameterId);
								var hasArray = false;
								var infor;
								if (inforCurrentParam != undefined) {
									infor = $$module.getFullInformationOfParameterFormula(inforCurrentParam);
									if (!isEmptyQP(infor)) {
										for (var k = 0; k < infor.length; k++) {
											var currentParam = infor[k];
											if (eval(currentParam.output04)) {
												hasArray = true;
												break;
											}
										}
									}
								}
								if(hasArray && inforCurrentParam != undefined){
									bindDataToFormulaSetting(tempInput.lstParameterIndex,arrIndexLoop, hasArray, infor, labelContent);
								} else {
									$(labelContent).append(tempInput.parameterIdAutocomplete);
								}
								
							}
							break;
						}
					}
					if(i+1 < objFunction.functionMethodInput.length)
						$(labelContent).append(",");
				}
			}
			
			$(labelContent).append(")");
			
			if (objData.formulaMethodOutputs != undefined && objData.formulaMethodOutputs.length > 0) {
				var temp = objData.formulaMethodOutputs[0];
				
				$(labelContent).append("." + temp.methodOutputCode);				
			}
	
			//set output
	//		if(objData.formulaMethodOutputs != undefined && objData.formulaMethodOutputs.length >0){
	//			for(var i=0; i < objFunction.functionMethodOutput.length;i++){
	//				var output = objFunction.functionMethodOutput[i];
	//				var tempOutput = objData.formulaMethodOutputs[0];
	//				if(tempOutput.methodOutputId == output.methodOutputId){
	//					divDetail+="." + output.methodOutputCode;
	//				}
	//			}
	//		}
			
			$(divContent).find("input[name=formulaitem]").val($$module.convertToString(objData));
			$(divContent).find("div.qp-formula-component-content").attr("hiddenvalue",$$module.convertToString(objFunction));
			return divContent;
		}
	}
	$$module.onSelectComponentBean = function(thiz){
		var contentDiv = $(thiz).closest("div.modal-content").find("#bdFormulaValueFormula");
//		var value = $(thiz).closest("li").find("input[name=detailparameter]").val() == undefined ? null: $(thiz).closest("li").find("input[name=detailparameter]").val();
		var value = $(thiz).closest("li").data("data");
//		var value = $(thiz).closest("li").find("input[name=detailparameter]").val() == undefined ? null: $(thiz).closest("li").find("input[name=detailparameter]").val();
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
	$$module.initialComponentStatus = function(obj){
		var formulaType = $(obj).attr("formulaType");
		//case assign node : show new Object();
		if(formulaType == "0"){
			$("button.qp-formula-expression-component[component='27']").show();
		}else{
			$("button.qp-formula-expression-component[component='27']").hide();
		}
	}
	$$module.openModalFormulaSetting = function(obj,objValue,objLabel,parameters){
		var modal = BD_MODAL_NAME_FORMULA;
		$(modal).data("target", obj);
		$(modal).data("data", $$module.convertToString(parameters));
		var onbeforeopen = $(obj).attr("onbeforeopen");
		if(typeof jQuery.namespace(onbeforeopen) == "function") {
			jQuery.namespace(onbeforeopen)(obj);
		}
		var targetLabel = objValue;
		var targetValue = objLabel;
		$(modal).data("targetLabel", targetLabel);
		$(modal).data("targetValue", targetValue);
		$(modal).data("target", obj);
		
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
					var code=objData.parameterCode;
					if(eval(objData.arrayFlg)){
						code += "[]";
					}
					var templateChildren = $("#tbl-scopeparameter-children-template").tmpl({id:objData.parameterId,code:code,paddingLeft:objData.paddingLeft});
					$(templateChildren).find("input[name=detailparameter]").val($$module.convertToString(objData));
					$(templateChildren).data("data", $$module.convertToString(objData));
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
				var value = undefined;
				if(dataOfFunction != undefined){
					value = $$module.convertToJson(dataOfFunction);
				}
				templateComponent = $$module.loadDataForFunctionOperators(value,objData);
			}else{
				templateComponent = $$module.initialDataForComponentOperators(objData,"1");
			}
			$(contentDiv).append(templateComponent);
		}
		$(modal).find("input[name$='IdAutocomplete']").each(function(){
			$(this).data("ui-autocomplete")._trigger("close");
		});
		 $(modal).find('label.tree-toggler').click(function() {
		        $(this).parent().children('ul.tree').toggle(300);
		    });
		 
		 // show hide component
		$$module.initialComponentStatus(obj);
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
				}else if(type == "17"){
					content += $(this).find("div.qp-formula-component-content").text() + " ";
				}else if(type == "18" || type == "19" || type == "20"){
					$(this).find("div.qp-formula-component-content").find("label,:text").each(function(){
						if($(this).is(':input')){
							content += $(this).val();
						}else
							content += $(this).text();
					});
					// get index
					var lstParameterIndex =[];
					$(this).find("div.qp-formula-component-content").find("input[name='parameterIndexId']").each(function (){
						var item = new Object();
						var parameterIndexId = $(this).val() == undefined ? null : $(this).val();
						var parameterIndexIdAutocomplete = $(this).prev().val() == undefined ? null : $(this).prev().val();
						var parameterIndexType = ($(this).attr("indextype") == undefined || $(this).attr("indextype") == "") ? "3" : $(this).attr("indextype");
						var parameterId = ($(this).attr("parameterId") == undefined || $(this).attr("parameterId") == "") ? null : $(this).attr("parameterId");
						item.parameterIndexId = parameterIndexId;
						item.parameterIndexIdAutocomplete = parameterIndexIdAutocomplete;
						item.parameterIndexType = parameterIndexType;
						item.parameterId = parameterId;
						if(!isEmptyQP(parameterIndexId)) {
							lstParameterIndex.push(item);
						}else {
							lstParameterIndex.push(null);
						}
					});
					item.lstParameterIndex = lstParameterIndex;
				}else if(type == "23"){
					var input = $(this).find("div.qp-formula-component-content").find("input[type=text]").val() == undefined ? "" : $(this).find("div.qp-formula-component-content").find("input[type=text]").val();
					content +=  '"'+ input + '"';
					item.value = input;
				}else{
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
		
		$("span#lblFunctionMethodName").text(data.functionMethodName);
		$("span#lblFunctionMethodCode").text(data.functionMethodCode);
		$("span#lblFunctionMethodRemark").text(!data.remark || data.remark == 'null' ? '' : data.remark);
		
		var urlMethodInput = CONTEXT_PATH + "/businessdesign/findFunctionMethodInputByFunctionMethodId?functionMethodId="+data.functionMethodId+"&r="+Math.random();
		var urlMethodOutput = CONTEXT_PATH + "/businessdesign/findFunctionMethodOutputByFunctionMethodId?functionMethodId="+data.functionMethodId+"&r="+Math.random();
		var dataFunctionMethodInputs = $.qp.getData(urlMethodInput);
		var dataFunctionMethodOutputs =  $.qp.getData(urlMethodOutput);
		data.functionMethodInput = dataFunctionMethodInputs;
		data.functionMethodOutput = dataFunctionMethodOutputs;
		var tableInput = "#tbl-function-detail-input";
		var tableOutput = "#tbl-function-detail-output";
		$(modal).find(tableInput).find("tbody").empty();
		$(modal).find(tableOutput).find("tbody").empty();
		var templateInput;
		if(data.functionMethodInput != undefined){
			for(var i=0; i< data.functionMethodInput.length;i++){
				var objInput = data.functionMethodInput[i];
				objInput.index = i+1;
				var dataTypeStr="";
				if(CL_FORMULA_BUILDER_DATATYPE != undefined){
					dataTypeStr = CL_FORMULA_BUILDER_DATATYPE[objInput.dataType];
				}
				if(eval(objInput.arrayFlg)){
					dataTypeStr = dataTypeStr + "[]";
				}
				objInput.dataTypeStr = dataTypeStr;
				templateInput = $(modal).find("#tbl-function-detail-input-template").tmpl(objInput);
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
				if(eval(objOutput.arrayFlg)){
					dataTypeStr = dataTypeStr + "[]";
				}
				objOutput.dataTypeStr = dataTypeStr;
				var templateOuput = $(modal).find("#tbl-function-detail-output-template").tmpl(objOutput);
				$(modal).find(tableOutput).find("tbody").append(templateOuput);
			}
			if(data.functionMethodOutput.length == 1){
				var checkbox = $("#tbl-function-detail-output tr td input[type='radio'][name='outputSelect']");
				checkbox.prop('checked',true);
			}
		}
		
		//bind data
		var item = $(obj).parent().find("input[name=formulaitem]").val()==undefined ? new Object() : $$module.convertToJson($(obj).parent().find("input[name=formulaitem]").val());
		if(item.formulaMethodOutputs !=undefined && item.formulaMethodOutputs.length >0){
			var output= item.formulaMethodOutputs[0];
			var outputRow = $(modal).find(tableOutput).find("input[name=methodOutputId][value="+output.methodOutputId+"]").closest("tr");
			$(outputRow).find("input[type=radio][name=outputSelect]").prop("checked",true);
		}
		if(item.formulaMethodInputs != undefined && item.formulaMethodInputs.length > 0){
			$(modal).find(tableInput).find("tbody>tr").each(function(index){
				var input= item.formulaMethodInputs[index];
				
				if (input != undefined) {
					var inputRow =  $(modal).find(tableInput).find("input[name=methodInputId][value="+input.methodInputId+"]").closest("tr");
					if(inputRow != undefined){
						$(inputRow).find("td[type=value]").hide();
						$(inputRow).find("td[type=parameter]").hide();
						var parameterType = (input.parameterType == undefined || input.parameterType == null) ? "" :  input.parameterType;
						if(parameterType== "1"){
							var parameterValue = (input.parameterValue == undefined || input.parameterValue == null) ? "" :  input.parameterValue;
							$(inputRow).find("input[type=radio][name*=parameterScope][value=1]").prop("checked",true);
							$(inputRow).find("input[name=parameterValue]").val(parameterValue);
							$(inputRow).find("td[type=value]").show();
						}else{
							$(inputRow).find("input[type=radio][name*=parameterScope][value=2]").prop("checked",true);
							var parameterId = (input.parameterId == undefined || input.parameterId == null) ? "" :  input.parameterId;
							$(inputRow).find("input[name=parameterId]").val(parameterId);
	//						var parameterIdAutocomplete = ($$module.mapFullCodeOfFormula!= undefined && $$module.mapFullCodeOfFormula[parameterScope+parameterId] != undefined) ? $$module.mapFullCodeOfFormula[parameterScope+parameterId] : "";
							var parameterIdAutocomplete = (input.parameterIdAutocomplete == undefined || input.parameterIdAutocomplete == null) ? "" :  input.parameterIdAutocomplete;
							$(inputRow).find("input[name=parameterIdAutocomplete]").val(parameterIdAutocomplete);
							var parameterScope = (input.parameterScope == undefined || input.parameterScope == null) ? "" :  input.parameterScope;
							$(inputRow).find("input[name=parameterId]").attr("parameterScope", parameterScope);
							$(inputRow).find("td[type=parameter]").show();
	
							var arrIndexLoop = getLabelOfIndexLoop();
							
							$$module.bindDataToAssignAutocomplete(input.parameterId,"parameter",input.lstParameterIndex,arrIndexLoop,$(this),modal);
						}
					}
				}
			});
		}
		$.qp.initialAllPicker(modal);
		$(modal).find("input[name$='IdAutocomplete']").each(function(){
			$(this).data("ui-autocomplete")._trigger("close");
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
				var parameterType = null;
				parameterType =  $(this).find("input[type=radio][name*=parameterScope]:checked").val();
				if(parameterType == "1"){
					objDetailInput.parameterScope = 0;
					parameterValue = $(this).find("input[name=parameterValue]").val() == undefined ? null : $(this).find("input[name=parameterValue]").val();
					content+=parameterValue;
				}else if(parameterType == "2"){
					var divIndex = $(this).find("div.bd-content[id='parameter']");
					var parameterPattern = $(divIndex).attr("pattern");
					
					if(parameterPattern=="0"){
						parameterId = $(this).find("input[name='parameterId']").val() == undefined ? null : $(this).find("input[name='parameterId']").val();
						parameterIdAutocomplete = $(this).find("input[name='parameterIdAutocomplete']").val() == undefined ? null : $(this).find("input[name='parameterIdAutocomplete']").val();
						parameterScope = ($(this).find("input[name='parameterId']").attr("parameterScope") == undefined || $(this).find("input[name='parameterId']").attr("parameterScope") == "") ? null : $(this).find("input[name='parameterId']").attr("parameterScope");
						objDetailInput.lstParameterIndex = [];
						content+=(parameterIdAutocomplete != null ? parameterIdAutocomplete : "");
					} else if(parameterPattern=="1"){
						parameterId = isEmptyQP($(divIndex).attr("parameterId")) ? null : $(divIndex).attr("parameterId");
						parameterIdAutocomplete = $(this).find("input[name='parameterIndexIdAutocomplete']").val() == undefined ? null : $(this).find("input[name='parameterIndexIdAutocomplete']").val();
						parameterScope = (isEmptyQP($(divIndex).attr("parameterScope"))) ? null : $(divIndex).attr("parameterScope");
						
						for (var i = 0; i < $(this).find("div.bd-content").children().children().length - 1; i++) {
							if ($(this).find("div.bd-content").children().children()[i]) {
								if ($(this).find("div.bd-content").children().children()[i].tagName == "LABEL") {
									content+= $(this).find("div.bd-content").children().children()[i].textContent
								} else if ($(this).find("div.bd-content").children().children()[i].tagName == "DIV") {
									content+= $($(this).find("div.bd-content").children().children()[i]).find("input[name='parameterIndexIdAutocomplete']").val();
								}
							}
						}
						
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
							if(!isEmptyQP(parameterIndexId)) {
								lstParameterIndex.push(item);
							}else {
								lstParameterIndex.push(null);
							}
						});
						objDetailInput.lstParameterIndex = lstParameterIndex;
					}
				}
				
				objDetailInput.parameterValue = parameterValue;
				objDetailInput.parameterIdAutocomplete = parameterIdAutocomplete;
				objDetailInput.parameterScope = parameterScope;
				objDetailInput.parameterId = parameterId;
				objDetailInput.parameterType = parameterType;
				
				var methodInputId = $(this).find("input[name=methodInputId]").val() == undefined ? null : $(this).find("input[name=methodInputId]").val();
				objDetailInput.methodInputId = methodInputId;
				objDetailInput.dataType = $(this).find("input[name=dataType]").val() == undefined ? null : $(this).find("input[name=dataType]").val();
				objDetailInput.arrayFlg = $(this).find("input[name=arrayFlg]").val() == undefined ? null : Boolean(Number($(this).find("input[name=arrayFlg]").val()));
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
				var methodOutputCode = $(outputRow).find("label[name=methodOutputCode]").text() == undefined ? "" : $(outputRow).find("label[name=methodOutputCode]").text();
				output.dataType = $(outputRow).find("input[name=dataType]").val() == undefined ? null : $(outputRow).find("input[name=dataType]").val();
				output.arrayFlg = $(outputRow).find("input[name=arrayFlg]").val() == undefined ? null : Boolean(Number($(outputRow).find("input[name=arrayFlg]").val()));
				output.methodOutputCode = methodOutputCode;
				var countOuputSelect = $("#tbl-function-detail-output input[type=radio][name=outputSelect]").length;
				if(countOuputSelect > 1){
					content+="."+methodOutputCode;
				}
				formulaMethodOutputs.push(output);
				item.formulaMethodOutputs = formulaMethodOutputs;
			}else{
				var countOuputSelect = $("#tbl-function-detail-output input[type=radio][name=outputSelect]").length;
				if(countOuputSelect > 1){
					alert("Please choose output for function");
					return;
				}
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
		var sourceData = $$module.convertToJson($("form").find("input[name='jsonInputBean']").val());
		var mKeyCode = new Array();
		if(sourceData.length >0){
			var data = new Object();
			data.id = "inOfBD";
			data.label = "Input Bean";
			data.datas = [];
			for(var i=0;i<sourceData.length;i++){
				var objContent = new Object();
				var objData = sourceData[i];
				objContent.type = "18";
				objContent.parameterId = "0"+objData.inputBeanId;
				objContent.parameterCode = objData.inputBeanCode;
				objContent.dataType = objData.dataType;
				objContent.arrayFlg = objData.arrayFlg;
				objContent.parentParameterId = isEmptyQP(objData.parentInputBeanId) ? null : "0"+objData.parentInputBeanId;
				var fullCode="";
				if(objData.parentInputBeanId != undefined && objData.parentInputBeanId != ""){
					fullCode = mKeyCode["0"+objData.parentInputBeanId]+"."+objData.inputBeanCode; 
				}else{
					fullCode = "in."+objData.inputBeanCode;
				}
				mKeyCode["0"+objData.inputBeanId] = fullCode;
				objContent.parameterFullCode = fullCode;
				var paddingLeft = (objData.tableIndex.split(".").length * 10)+"px";
				objContent.paddingLeft = paddingLeft;
				data.datas.push(objContent);
				$$module.mapFullCodeOfFormula["18"+objData.inputBeanId] = fullCode;
				$$module.mapFullParameterOfFormula["18"+objData.inputBeanId] = objContent;
			}
			parameters.push(data);
		}
		
		sourceData = $$module.convertToJson($("form").find("input[name='jsonObjectDefinition']").val());
		if(sourceData.length >0){
			var data = new Object();
			data.id = "obOfBD";
			data.label = "Object Definition";
			data.datas = [];
			for(var i=0;i<sourceData.length;i++){
				var objContent = new Object();
				var objData = sourceData[i];
				objContent.type = "19";
				objContent.parameterId = "1"+objData.objectDefinitionId;
				objContent.parameterCode = objData.objectDefinitionCode;
				objContent.dataType = objData.dataType;
				objContent.arrayFlg = objData.arrayFlg;
				objContent.parentParameterId =  isEmptyQP(objData.parentObjectDefinitionId) ? null : "1"+objData.parentObjectDefinitionId;
				var fullCode="";
				if(objData.parentObjectDefinitionId != undefined && objData.parentObjectDefinitionId != ""){
					fullCode = mKeyCode["1"+objData.parentObjectDefinitionId]+"."+objData.objectDefinitionCode; 
				}else{
					fullCode = "ob."+objData.objectDefinitionCode;
				}
				mKeyCode["1"+objData.objectDefinitionId] = fullCode;
				objContent.parameterFullCode = fullCode;
				var paddingLeft = (objData.tableIndex.split(".").length * 10)+"px";
				objContent.paddingLeft = paddingLeft;
				data.datas.push(objContent);
				$$module.mapFullCodeOfFormula["19"+objData.objectDefinitionId] = fullCode;
				$$module.mapFullParameterOfFormula["19"+objData.objectDefinitionId] = objContent;
			}
			parameters.push(data);
		}
		
		var sourceData = $$module.convertToJson($("form").find("input[name='jsonOutputBean']").val());
		if(sourceData.length >0){
			var data = new Object();
			data.id = "ouOfBD";
			data.label = "Output Bean";
			data.datas = [];
			for(var i=0;i<sourceData.length;i++){
				var objContent = new Object();
				var objData = sourceData[i];
				objContent.type = "20";
				objContent.parameterId = "2"+objData.outputBeanId;
				objContent.parameterCode = objData.outputBeanCode;
				objContent.dataType = objData.dataType;
				objContent.arrayFlg = objData.arrayFlg;
				objContent.parentParameterId = isEmptyQP(objData.parentOutputBeanId) ? null : "2"+objData.parentOutputBeanId;
				var fullCode="";
				if(objData.parentOutputBeanId != undefined && objData.parentOutputBeanId != ""){
					fullCode = mKeyCode["2"+objData.parentOutputBeanId]+"."+objData.outputBeanCode; 
				}else{
					fullCode = "ou."+objData.outputBeanCode;
				}
				mKeyCode["2"+objData.outputBeanId] = fullCode;
				objContent.parameterFullCode = fullCode;
				var paddingLeft = (objData.tableIndex.split(".").length * 10)+"px";
				objContent.paddingLeft = paddingLeft;
				data.datas.push(objContent);
				$$module.mapFullCodeOfFormula["20"+objData.outputBeanId] = fullCode;
				$$module.mapFullParameterOfFormula["20"+objData.outputBeanId] = objContent;
			}
			parameters.push(data);
		}
		return parameters;
	}
	$$module.onChangeParameterScopeOfFunction = function(thiz){
		var selectedType = $(thiz).val();
		$(thiz).closest("tr").find("td[type=value]").hide();
		$(thiz).closest("tr").find("td[type=parameter]").hide();
		if(selectedType == "1"){
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
				objContent.type = "18";
				objContent.parameterId = "0"+objData.decisionInputBeanId;
				objContent.parameterCode = objData.decisionInputBeanCode;
				objContent.dataType = objData.dataType;
				objContent.arrayFlg = objData.arrayFlg;
				objContent.parentParameterId = isEmptyQP(objData.parentDecisionInputBeanId) ? null : "0"+objData.parentDecisionInputBeanId;
				var fullCode="";
				
				if(objData.parentDecisionInputBeanId != undefined && objData.parentDecisionInputBeanId != ""){
					fullCode = mKeyCode["0"+objData.parentDecisionInputBeanId]+"."+objData.decisionInputBeanCode;
				}else{
					fullCode = "in."+objData.decisionInputBeanCode;
				}
				mKeyCode["0"+objData.decisionInputBeanId] = fullCode;
				objContent.parameterFullCode = fullCode;
				var paddingLeft = (objData.tableIndex.split(".").length * 10)+"px";
				objContent.paddingLeft = paddingLeft;
				data.datas.push(objContent);
				$$module.mapFullCodeOfFormula["18"+objData.decisionInputBeanId] = fullCode;
				$$module.mapFullParameterOfFormula["18"+objData.decisionInputBeanId] = objContent;
			}
			parameters.push(data);
		}
		
		return parameters;
	}

	$$module.convertToJson = function(string) {
		var json = {};
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
	
	$$module.getParameter = function(id) {
		if($$module.mapFullParameterOfFormula != undefined){
			return $$module.mapFullParameterOfFormula[id];
		}else
			return undefined;
	};
	$$module.getFullInformationOfParameter = function(id){
		var lstInformation = [];
		var infor = $$module.getInformationOfParameter(id);
		if(!isEmptyQP(infor)){
			lstInformation.push(infor);
			while(!isEmptyQP(infor.parentParameterId)){
				var idTemp = toStringQP(infor.parentParameterId);
				var infor = $$module.getInformationOfParameter(idTemp);
				lstInformation.push(infor);
			}
		}
		return lstInformation;
	}
	$$module.getInformationOfParameter = function(id){
		var modal = $("#dialog-formula-setting");
		var listData = $$module.convertToJson($(modal).data("data"));
		for(var i=0;i<listData.length;i++){
			for(var j=0;j<listData[i].datas.length;j++){
				var item = listData[i].datas[j];
				if(item.parameterId == id){
					return item;
				}
			}
		}
		return null;
	}
	$$module.buildIndexOfParameter = function(id,type){
		var infor = $$module.getFullInformationOfParameter(id);
		var newDiv = $("#tbl-formula-content-parameter-template").tmpl({label:""});
		var newContent = $(newDiv).find("div.qp-formula-component-content");
		if(!isEmptyQP(infor)){
			var hasArray = false;
			for(var i=0; i < infor.length; i++){
				var currentParam = infor[i];
				if(eval(currentParam.arrayFlg)){
					hasArray = true;
					break;
				}
			}
			for (var int = infor.length; int > 0; int--) {
				var currentParam = infor[int -1];
				var code="";
				if(int == infor.length){
					switch (currentParam.type.toString()) {
					case "18":
						code = "in.";
						break;
					case "19":
						code = "ob.";
						break;
					// Modify by HungHX
					case "20":
						code = "ou.";
						break;
					default:
						break;
					}
				}
				code += currentParam.parameterCode;
				var arrayFlg = eval(currentParam.arrayFlg);
				if(arrayFlg){
					code+="[";
					$(newContent).append("<label class='control-label'>"+code+"</label>");
					var templateLabel = $("#dialog-formula-setting").find("#div-parameter-index-template").tmpl();
					$(templateLabel).find("input[name='parameterIndexId']").attr("parameterId",currentParam.parameterId);
					$(newContent).append(templateLabel);
					if(int > 1){
						$(newContent).append("<label class='control-label'>].</label>");
					}else{
						$(newContent).append("<label class='control-label'>]</label>");
					}	
					
				}else{
					if(int > 1)
						code +=".";
					$(newContent).append("<label class='control-label'>"+code+"</label>");
				}
			}
			$(newDiv).attr("type",type);
			$.qp.initialAllPicker($(newDiv));
//				$(parentDiv).append(newDiv);
//				$(parentDiv).attr("pattern","1");
//				$(parentDiv).attr("parameterScope",selectedParam.output01);
//				$(parentDiv).attr("parameterId",selectedParam.optionValue);
		
		}
		return newDiv;
	}
	function bindDataToFormulaSetting(lstIndexOfId,arrIndexLoop, hasArray, infor, labelContent){
		var code = "";
				
		if(hasArray){
			for (var int = infor.length; int > 0; int--) {
				var currentParam = infor[int -1];
				if(int == infor.length){
					switch (currentParam.output01.toString()) {
					case "0":
						 $(labelContent).append("in.");
						break;
					case "1":
						 $(labelContent).append("ob.");
						break;
					case "2":
						 $(labelContent).append("ou.");
						break;

					default:
						break;
					}
				}
				$(labelContent).append(currentParam.output06);
				var arrayFlg = eval(currentParam.output04);
				if(arrayFlg){
					$(labelContent).append("[");

					$(labelContent).append("<label parameterId='"+toStringQP(currentParam.output01) + toStringQP(currentParam.output05)+"'></label>");
					
					if(int > 1){
						$(labelContent).append("].");
					}else{
						$(labelContent).append("]");
					}	
				}else{
					if(int > 1)
						$(labelContent).append(".");
				}
			}
			
			//bind data
			for (var int2 = 0; int2 < lstIndexOfId.length; int2++) {
				var itemIndex = lstIndexOfId[int2];
				if (!isEmptyQP(itemIndex)) {
					if(!isEmptyQP(itemIndex.parameterIndexId)){
						switch (itemIndex.parameterIndexType.toString()) {
						case "3":
							$(labelContent).find("label[parameterId='"+itemIndex.parameterId+"']").text(itemIndex.parameterIndexId);
							break;
						case "4":
							var actualIndex = itemIndex.parameterIndexId.substring(1,itemIndex.parameterIndexId.length);
							var labelIndex = arrIndexLoop[actualIndex];
							if(labelIndex != undefined){
								$(labelContent).find("label[parameterId='"+itemIndex.parameterId+"']").text(labelIndex);
							}
							break;
						case "0":
						case "1":
						case "2":
							var inforIndex = $$module.getInformationOfParameter(itemIndex.parameterIndexId);
							if(inforIndex != undefined){
								$(labelContent).find("label[parameterId='"+itemIndex.parameterId+"']").text(inforIndex.parameterFullCode);
							}
						default:
							break;
						}
					}
				}
			}
		}

		return hasArray;
	}

	$$module.bindDataToAssignAutocomplete = function(id,typeOfId,lstIndexOfId,arrIndexLoop,newRow,modal,removeCallBack){
		var inforCurrentParam = $$module.getInformationOfParameterFormula(id);
		var hasArray = false;
		if(inforCurrentParam != undefined){
			var infor = $$module.getFullInformationOfParameterFormula(inforCurrentParam);
			if(!isEmptyQP(infor)){
				//case assign
				if($$module.isObjectType( infor[0].output03)){
					$(newRow).find("td.btn-getobject").children().show();
				}else{
					$(newRow).closest("tr").find("td.btn-getobject").children().hide();
				}
				for(var i=0; i < infor.length; i++){
					var currentParam = infor[i];
					if(eval(currentParam.output04)){
						hasArray = true;
						break;
					}
				}
				var parentDiv = $(newRow).find("div.bd-content[name='"+typeOfId+"']");
				var oldContent = $(parentDiv).children();
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
							$$module.addClassFormInline(newDiv);
						}else{
							if(int > 1)
								code +=".";
							$(newDiv).append("<label class='control-label'>"+code+"</label>");
							$$module.removeClassFormInline(newDiv);
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
					
					//bind data
					for (var int2 = 0; int2 < lstIndexOfId.length; int2++) {
						var itemIndex = lstIndexOfId[int2];
						if (!isEmptyQP(itemIndex)) {
							var selectedIndex = $(newDiv).find("input[name='parameterIndexId'][parameterId='"+itemIndex.parameterId+"']");
							if(selectedIndex.length > 0 && !isEmptyQP(itemIndex.parameterIndexId)){
								$(selectedIndex).val(itemIndex.parameterIndexId);
								$(selectedIndex).attr("indextype",itemIndex.parameterIndexType);
								switch (itemIndex.parameterIndexType.toString()) {
								case "3":
									$(selectedIndex).prev().val(itemIndex.parameterIndexId);
									break;
								case "4":
									var actualIndex = itemIndex.parameterIndexId.substring(1,itemIndex.parameterIndexId.length);
									var labelIndex = arrIndexLoop[actualIndex];
									if(labelIndex != undefined){
										$(selectedIndex).prev().val(labelIndex);
									}else{
										$(selectedIndex).prev().val("");
									}
									break;
								case "0":
								case "1":
								case "2":
									var inforIndex = $$module.getInformationOfParameterFormula(itemIndex.parameterIndexId);
									if(inforIndex != undefined){
										$(selectedIndex).prev().val(inforIndex.optionLabel);
									}else{
										$(selectedIndex).prev().val("");
									}
								default:
									break;
								}
							}
						}
					}
					$(parentDiv).append(newDiv);
					$(parentDiv).attr("pattern","1");
					$(parentDiv).attr("parameterScope",inforCurrentParam.output01);
					$(parentDiv).attr("parameterId",inforCurrentParam.optionValue);
					$$module.addClassFormInline(newDiv);
				}else{
					$(parentDiv).attr("pattern","0");
					$(newRow).find("input[name='"+typeOfId+"Id']").val(inforCurrentParam.optionValue);
					$(newRow).find("input[name='"+typeOfId+"IdAutocomplete']").val(inforCurrentParam.optionLabel); 
					$(newRow).find("input[name='"+typeOfId+"Id']").attr("parameterScope",inforCurrentParam.output01);
					$$module.removeClassFormInline(newRow);
				}
			}
			
		}else{
			$(newRow).find("input[name='"+typeOfId+"Id']").val("");
			$(newRow).find("input[name='"+typeOfId+"IdAutocomplete']").val("");
		}
		$.qp.initialAllPicker($(newRow));
		$(newRow).find("input[name$='IdAutocomplete']").each(function(){
			$(this).data("ui-autocomplete")._trigger("close");
		});
		return hasArray;
	}
	
	$$module.getInformationOfParameterFormula = function(id,isPrefix){
		var data = $$module.getDataSourceMap().idResults;
		return data[id];
	}

	$$module.getFullInformationOfParameterFormula = function(infor){
		var lstInformation = [];
		var id;
		if(!isEmptyQP(infor)){
			lstInformation.push(infor);
			while(!isEmptyQP(infor.output02)){
				id = toStringQP(infor.output01) + toStringQP(infor.output02);
				var infor = $$module.getInformationOfParameterFormula(id);
				lstInformation.push(infor);
			}
		}
		return lstInformation;
	}
	
	$$module.isObjectType = function(dataType){
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

	$$module.removeClassFormInline = function(element){
		var td = $(element).closest("td");
		if(td.hasClass("form-inline")){
			td.removeClass("form-inline");
			td.find("div.input-group").css("width", "100%");
		}
	}

	$$module.addClassFormInline = function(element){
		var td = $(element).closest("td");
		if(!td.hasClass("form-inline")){
			td.addClass("form-inline");
		}
	}

	$$module.getDataSourceMap = function(){
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

		var sourceData = convertToJson($("form").find("input[name='listInput']").val());
		if(sourceData != undefined && sourceData.length > 0) {
			for(var i = 0; i < sourceData.length; i++) {
				var objTmp = new Object();
				objTmp.optionValue = "0"+sourceData[i].decisionInputBeanId;
				objTmp.output01 = 0;
				objTmp.output02 = sourceData[i].parentDecisionInputBeanId;
				objTmp.output03 = sourceData[i].dataType;
				objTmp.output04 = sourceData[i].arrayFlg;
				objTmp.output05 = sourceData[i].decisionInputBeanId;
				objTmp.output06 = sourceData[i].decisionInputBeanCode;
				
				if(sourceData[i].parentDecisionInputBeanId != undefined && sourceData[i].parentDecisionInputBeanId != ""){
					objTmp.optionLabel = mKeyCode["0"+sourceData[i].parentDecisionInputBeanId]+"."+sourceData[i].decisionInputBeanCode; 
				}else{
					objTmp.optionLabel = "in."+sourceData[i].decisionInputBeanCode;
				}
				mKeyCode[objTmp.optionValue] = objTmp.optionLabel;
				codeResults[objTmp.optionLabel] = objTmp;
				idResults[objTmp.optionValue] = objTmp;
			}
		}

		var sourceData = convertToJson($("form").find("input[name='listOutput']").val());
		if(sourceData != undefined && sourceData.length > 0) {
			for(var i = 0; i < sourceData.length; i++) {
				var objTmp = new Object();
				objTmp.optionValue = "0"+sourceData[i].decisionOutputBeanId;
				objTmp.output01 = 0;
				objTmp.output02 = sourceData[i].parentDecisionOutputBeanId;
				objTmp.output03 = sourceData[i].dataType;
				objTmp.output04 = sourceData[i].arrayFlg;
				objTmp.output05 = sourceData[i].decisionOutputBeanId;
				objTmp.output06 = sourceData[i].decisionOutputBeanCode;
				
				if(sourceData[i].parentDecisionOutputBeanId != undefined && sourceData[i].parentDecisionOutputBeanId != ""){
					objTmp.optionLabel = mKeyCode["0"+sourceData[i].parentDecisionOutputBeanId]+"."+sourceData[i].decisionOutputBeanCode; 
				}else{
					objTmp.optionLabel = "in."+sourceData[i].decisionOutputBeanCode;
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
return $$module;
})(jQuery.namespace('$.qp.formulabuilder'));