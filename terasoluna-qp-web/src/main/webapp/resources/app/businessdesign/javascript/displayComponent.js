/**
 * display modal
 * @author quangvd
 */
 $(document).on('hidden.bs.modal', '.modal', function () {
    $('.modal:visible').length && $(document.body).addClass('modal-open');
});
function openModalExecution(obj,isOnlyView) {
	var modal = BD_MODAL_NAME.EXECUTION;
	$(modal).data("target", obj);
	$(modal).data("instance", getInstanceOfContainer(obj));
	var value = $(obj).find("input[name='componentElement']").val();
	var data = convertToJson(value);
	
	var tableInputBean = "#tbl-execution-inputbean";
	var tableOutputBean = "#tbl-execution-outputbean";
	$(modal).data("data", value);
	
	//refesh empty data.
	$(modal).find("input[name='label']").val("");
	$(modal).find("textarea[name='remark']").val("");
	$(modal).find("input[name='sqlDesignId']").val("");
	$(modal).find("input[name='sqlDesignIdAutocomplete']").val("");
	$(modal).find("label[name='sqlDesignCode']").text("");
	$(modal).find("input[name='sqlDesignIdAutocomplete']").closest("td").removeClass("qp-bdesign-tr-remove");
	$(modal).find("label[name='sqlPattern']").text("");
	$(modal).find("input[name='concurrencyFlg']").attr('checked', false);
	$(modal).find("input[name='concurrencyFlg']").attr('disabled', true);
	$(modal).find("label[name='returnType']").text("");
	$(modal).find(tableInputBean).find("tbody").empty();
	$(modal).find(tableOutputBean).find("tbody").empty();
	$(modal).find("input[name='moduleId']").val("");
	$(modal).find("input[name='moduleIdAutocomplete']").val("");
	$(modal).find('div[id="tabsExecution-1"]').empty();
	if(data != null){
		var arrIndexLoop = getLabelOfIndexLoop();
		if(data.label != undefined && data.label != null){
			$(modal).find("input[name='label']").val(data.label);
		}else{
			$(modal).find("input[name='label']").val("");
		}
		if(data.moduleId != undefined && data.moduleId != null){
			$(modal).find("input[name='moduleId']").val(data.moduleId);
			$(modal).find("input[name='moduleIdAutocomplete']").val(data.moduleIdAutocomplete);
			$(modal).find("input[name='sqlDesignIdAutocomplete']").attr("arg03",data.moduleId);
		}else{
			$(modal).find("input[name='moduleId']").val("");
			$(modal).find("input[name='moduleIdAutocomplete']").val("");
			$(modal).find("input[name='sqlDesignIdAutocomplete']").attr("arg03","");
		}
		if(data.remark != undefined && data.remark != null){
			$(modal).find("textarea[name='remark']").val(data.remark);
		}else{
			$(modal).find("textarea[name='remark']").val("");
		}
		
		if(data.concurrencyFlg != undefined && data.concurrencyFlg != null){
			$(modal).find("input[type=checkbox][name='concurrencyFlg']").prop("checked",data.concurrencyFlg);
		}else{
			$(modal).find("input[type=checkbox][name='concurrencyFlg']").prop("checked",false);
		}
		// main process initial display
		if(data.sqlDesignId != undefined && data.sqlDesignId != null && data.sqlDesignId != "") {
			var url = CONTEXT_PATH + "/businessdesign/getSQLDesignById?sqlDesignId="+data.sqlDesignId+"&r="+Math.random();
			var arrRequestData = $.qp.getData(url);
			var sqlInfor = arrRequestData.sqlDesign;
			if(arrRequestData != null &&  sqlInfor != null && sqlInfor.sqlDesignId != null){
				$(modal).find("input[name='sqlDesignId']").val(sqlInfor.sqlDesignId);
				
				if(sqlInfor.sqlDesignName != undefined && sqlInfor.sqlDesignName != null && sqlInfor.sqlDesignName != ""){
					$(modal).find("input[name='sqlDesignIdAutocomplete']").val(sqlInfor.sqlDesignName);
				}else{
					$(modal).find("input[name='sqlDesignIdAutocomplete']").val("");
				}
				
				if(sqlInfor.sqlDesignCode != undefined && sqlInfor.sqlDesignCode != null && sqlInfor.sqlDesignCode != ""){
					$(modal).find("label[name='sqlDesignCode']").text(sqlInfor.sqlDesignCode);
					var href = $(modal).find("label[name=sqlDesignCode]").parent().attr('href').split("=");
					$(modal).find("label[name=sqlDesignCode]").parent().attr('href',href[0]+ "=" + sqlInfor.sqlDesignId);
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
				//bind input data
				if(data.parameterInputBeans != undefined && data.parameterInputBeans != null){
					for(var i=0;i<data.parameterInputBeans.length;i++){
						var inputValue = data.parameterInputBeans[i];
						if(inputValue.sqlDesignInputId != undefined){
							var rowInput =$(modal).find(tableInputBean).find("tbody").find("input[name=sqlDesignInputId][value="+inputValue.sqlDesignInputId+"]").closest("tr");
							var inforCurrentParam = new Object();
							if(rowInput.length >0){
								var sqlDesignInputId = $(rowInput).find("input[name='sqlDesignInputId']").val() == undefined ? null : $(rowInput).find("input[name='sqlDesignInputId']").val();
								var sqlDesignInputName = $(rowInput).find("label[name='sqlDesignInputName']").text() == undefined ? null : $(rowInput).find("label[name='sqlDesignInputName']").text() ;
								var sqlDesignInputCode = $(rowInput).find("label[name='sqlDesignInputCode']").text() == undefined ? null : $(rowInput).find("label[name='sqlDesignInputCode']").text();
								var dataType = $(rowInput).find("input[name='dataType']").val() == undefined ? null : $(rowInput).find("input[name='dataType']").val();
								var arrayFlg = $(rowInput).find("input[name='arrayFlg']").val() == undefined ? null : $(rowInput).find("input[name='arrayFlg']").val();
								if(arrayFlg == "0"){
									arrayFlg = false;
								}
								if(inputValue.sqlDesignInputId == sqlDesignInputId 
										&& inputValue.sqlDesignInputName == sqlDesignInputName
										&& inputValue.sqlDesignInputCode == sqlDesignInputCode
										&& compareDataType(inputValue.dataType,inputValue.arrayFlg,dataType,arrayFlg)){
									//no change
								}else{
									//change
									$(rowInput).addClass("qp-bdesign-tr-change");
									$(rowInput).find("input[name*='sqlDesignInputId']").attr("impactStatus","2");
								}
//								inforCurrentParam = getInformationOfParameter(inputValue.parameterId);
//								if(inforCurrentParam != undefined){
//									$(rowInput).find("input[name='parameterId']").val(inforCurrentParam.optionValue);
//									$(rowInput).find("input[name='parameterIdAutocomplete']").val(inforCurrentParam.optionLabel);
//									$(rowInput).find("input[name='parameterId']").attr("parameterScope",inforCurrentParam.output01);
//								}else{
//									$(rowInput).find("input[name='parameterId']").val("");
//									$(rowInput).find("input[name='parameterIdAutocomplete']").val("");
//								}
								bindDataToAssignAutocomplete(inputValue.parameterId,"parameter",inputValue.lstParameterIndex,arrIndexLoop,$(rowInput),modal);
								
								$(rowInput).find("td.btn-remove").children().hide();
								
							}else{
								//remove
								var dataType = CL_BD_DATATYPE[inputValue.dataType] 
								if(inputValue.arrayFlg  == "1"){
									dataType += "[]";
								}
								inputValue.dataTypeStr = dataType;
								var templateInput = $(modal).find("#tbl-execution-inputbean-template").tmpl(inputValue);
								$(templateInput).addClass("qp-bdesign-tr-remove");
//								inforCurrentParam = getInformationOfParameter(inputValue.parameterId);
//								if(inforCurrentParam != undefined){
//									$(templateInput).find("input[name='parameterId']").val(inforCurrentParam.optionValue);
//									$(templateInput).find("input[name='parameterIdAutocomplete']").val(inforCurrentParam.optionLabel);
//									$(templateInput).find("input[name='parameterId']").attr("parameterScope",inforCurrentParam.output01);
//								}else{
//									$(templateInput).find("input[name='parameterId']").val("");
//									$(templateInput).find("input[name='parameterIdAutocomplete']").val("");
//								}
								bindDataToAssignAutocomplete(inputValue.parameterId,"parameter",inputValue.lstParameterIndex,arrIndexLoop,$(templateInput),modal);
								
								$(templateInput).find("input[name*='sqlDesignInputId']").attr("impactStatus","3");
								$(modal).find(tableInputBean).find("tbody").append(templateInput);
							}
						}
					}
				}
				//bind output data
				if(data.parameterOutputBeans != undefined && data.parameterOutputBeans != null){
					for(var i=0;i<data.parameterOutputBeans.length;i++){
						var outputValue = data.parameterOutputBeans[i];
						if(outputValue.sqlDesignOutputId != undefined){
							var inforCurrentParam = new Object();
							var rowInput =$(modal).find(tableOutputBean).find("tbody").find("input[name=sqlDesignOutputId][value="+outputValue.sqlDesignOutputId+"]").closest("tr");
							if(rowInput.length >0){
								var sqlDesignOutputId = $(rowInput).find("input[name='sqlDesignOutputId']").val() == undefined ? null : $(rowInput).find("input[name='sqlDesignOutputId']").val();
								var sqlDesignOutputName = $(rowInput).find("label[name='sqlDesignOutputName']").text() == undefined ? null : $(rowInput).find("label[name='sqlDesignOutputName']").text() ;
								var sqlDesignOutputCode = $(rowInput).find("label[name='sqlDesignOutputCode']").text() == undefined ? null : $(rowInput).find("label[name='sqlDesignOutputCode']").text();
								var dataType = $(rowInput).find("input[name='dataType']").val() == undefined ? null : $(rowInput).find("input[name='dataType']").val();
								var arrayFlg = $(rowInput).find("input[name='arrayFlg']").val() == undefined ? null : $(rowInput).find("input[name='arrayFlg']").val();
								if(arrayFlg == "0"){
									arrayFlg = false;
								}
								if(outputValue.sqlDesignOutputId == sqlDesignOutputId 
										&& outputValue.sqlDesignOutputName == sqlDesignOutputName
										&& outputValue.sqlDesignOutputCode == sqlDesignOutputCode
										&& compareDataType(outputValue.dataType,outputValue.arrayFlg,dataType,arrayFlg)){
									//no change
								}else{
									//change
									$(rowInput).addClass("qp-bdesign-tr-change");
									$(rowInput).find("input[name*='sqlDesignOutputId']").attr("impactStatus","2");
								}
//								inforCurrentParam = getInformationOfParameter(outputValue.targetId);
//								if(inforCurrentParam != undefined){
//									$(rowInput).find("input[name='targetId']").val(inforCurrentParam.optionValue);
//									$(rowInput).find("input[name='targetIdAutocomplete']").val(inforCurrentParam.optionLabel);
//									$(rowInput).find("input[name='targetId']").attr("parameterScope",inforCurrentParam.output01);
//								}else{
//									$(rowInput).find("input[name='targetId']").val("");
//									$(rowInput).find("input[name='targetIdAutocomplete']").val("");
//								}
								bindDataToAssignAutocomplete(outputValue.targetId,"target",outputValue.lstTargetIndex,arrIndexLoop,$(rowInput),modal);
								$(rowInput).find("td.btn-remove").children().hide();
								
							}else{
								//remove
								var dataType = CL_BD_DATATYPE[outputValue.dataType] 
								if(outputValue.arrayFlg  == "1"){
									dataType += "[]";
								}
								outputValue.dataTypeStr = dataType;
								var templateOutput = $(modal).find("#tbl-execution-outputbean-template").tmpl(outputValue);
//								inforCurrentParam = getInformationOfParameter(outputValue.targetId);
//								if(inforCurrentParam != undefined){
//									$(templateOutput).find("input[name='targetId']").val(inforCurrentParam.optionValue);
//									$(templateOutput).find("input[name='targetIdAutocomplete']").val(inforCurrentParam.optionLabel);
//									$(templateOutput).find("input[name='targetId']").attr("parameterScope",inforCurrentParam.output01);
//								}else{
//									$(templateOutput).find("input[name='targetId']").val("");
//									$(templateOutput).find("input[name='targetIdAutocomplete']").val("");
//								}
								bindDataToAssignAutocomplete(outputValue.targetId,"target",outputValue.lstTargetIndex,arrIndexLoop,$(templateOutput),modal);
								
								$(templateOutput).addClass("qp-bdesign-tr-remove");
								$(templateOutput).find("input[name*='sqlDesignOutputId']").attr("impactStatus","3");
								$(modal).find(tableOutputBean).find("tbody").append(templateOutput);
							}
						}
					}
				}
				
				url = CONTEXT_PATH + "/sqldesign/view?sqlDesignForm.sqlDesignId="+data.sqlDesignId+"&r="+Math.random();
				var htmlContent = $.qp.getHtml(url);
				$(modal).find('div[id="tabsExecution-1"]')
					.append($(htmlContent).find('div.tab-content>div#tab-sql-design').children());
				
				setPageableForSqlCode(htmlContent);
				
			}else{
				if(data.sqlDesignId != undefined && data.sqlDesignId != null && data.sqlDesignId != ""){
					$(modal).find("input[name='sqlDesignId']").val(data.sqlDesignId);
				}else{
					$(modal).find("input[name='sqlDesignId']").val("");
				}
				
				if(data.sqlDesignIdAutocomplete != undefined && data.sqlDesignIdAutocomplete != null && data.sqlDesignIdAutocomplete != ""){
					$(modal).find("input[name='sqlDesignIdAutocomplete']").val(data.sqlDesignIdAutocomplete);
				}else{
					$(modal).find("input[name='sqlDesignIdAutocomplete']").val("");
				}
				
				if(data.sqlDesignCode != undefined && data.sqlDesignCode != null && data.sqlDesignCode != ""){
					$(modal).find("label[name='sqlDesignCode']").text(data.sqlDesignCode);
					var href = $(modal).find("label[name=sqlDesignCode]").parent().attr('href').split("=");
					$(modal).find("label[name=sqlDesignCode]").parent().attr('href',href[0]+ "=" + data.sqlDesignId);
				}else{
					$(modal).find("label[name='sqlDesignCode']").text("");
				}
				$(modal).find("input[name='sqlDesignIdAutocomplete']").closest("td").addClass("qp-bdesign-tr-remove");
			}	
		}else{
			
		}
	}
	$.qp.sqlbuilder.initFromForm();
	$.qp.initialAllPicker($(modal));
	$(modal).find("input[name$='IdAutocomplete']").each(function(){
		$(this).data("ui-autocomplete")._trigger("close");
	});
	initialViewModal(modal,isOnlyView);
	$(modal).modal(
			  { 
			   show: true,
			   closable: false,
			   keyboard:false,
			   backdrop:'static'
			  }
			 );
}

function openModalCommon(obj,isOnlyView) {
	var modal = BD_MODAL_NAME.COMMON;
	$(modal).data("target", obj);
	$(modal).data("instance", getInstanceOfContainer(obj));
	var value = $(obj).find("input[name='componentElement']").val();
	var data = convertToJson(value);
	$(modal).data("data", value);
	
	$(modal).find("input[name='label']").val("");
	$(modal).find("textarea[name='remark']").val("");
	$(modal).find("label[name='businessLogicCode']").text("");
	$(modal).find("input[name='businessLogicId']").val("");
	$(modal).find("input[name='businessLogicIdAutocomplete']").val("");
	$(modal).find("#tbl-common-inputbean").find("tbody").empty();
	$(modal).find("#tbl-common-outputbean").find("tbody").empty();
	$(modal).find("input[name='businessLogicIdAutocomplete']").closest("td").removeClass("qp-bdesign-tr-remove");
	if(typeof(moduleIdOfBD) != "undefined" && moduleIdOfBD != null){
		$(modal).find("input[name=businessLogicIdAutocomplete]").attr("arg03",moduleIdOfBD);
	}else{
		$(modal).find("input[name=businessLogicIdAutocomplete]").attr("arg03","");
	}
	if(data != null){
		var arrIndexLoop = getLabelOfIndexLoop();
		if(data.label != undefined && data.label != null){
			$(modal).find("input[name='label']").val(data.label);
		}else{
			$(modal).find("input[name='label']").val("");
		}
		if(data.remark != undefined && data.remark != null){
			$(modal).find("textarea[name='remark']").val(data.remark);
		}else{
			$(modal).find("textarea[name='remark']").val("");
		}
		if(data.businessLogicId != undefined && data.businessLogicId != null && data.businessLogicId != ""){
			//input bean
			var url = CONTEXT_PATH + "/businessdesign/getInformationOfCommonBusinessLogic?businessDesignId="+data.businessLogicId+"&r="+Math.random();
			var responseData = $.qp.getData(url);
			if(responseData.businessLogicId == null){
				//set data by old data
				$(modal).find("input[name='businessLogicId']").val("");
				if(data.businessLogicIdAutocomplete != undefined && data.businessLogicIdAutocomplete != null && data.businessLogicIdAutocomplete != ""){
					$(modal).find("input[name='businessLogicIdAutocomplete']").val(data.businessLogicIdAutocomplete);
				}else{
					$(modal).find("input[name='businessLogicIdAutocomplete']").val("");
				}
//				$(modal).find("input[name='businessLogicIdAutocomplete']").data("ui-autocomplete")._trigger("close");
				
				if(data.businessLogicCode != undefined && data.businessLogicCode != null && data.businessLogicCode != ""){
					$(modal).find("label[name='businessLogicCode']").text(data.businessLogicCode);
					var href = $(modal).find("label[name='businessLogicCode']").parent().attr('href').split("=");
					$(modal).find("label[name='businessLogicCode']").parent().attr('href',href[0]+ "=" + data.businessLogicId+ "&mode=0");
				}else{
					$(modal).find("label[name='businessLogicCode']").text("");
				}
				$(modal).find("input[name='businessLogicIdAutocomplete']").closest("td").addClass("qp-bdesign-tr-remove");
			}else{
				//set data by response data
				if(responseData.businessLogicId != undefined && responseData.businessLogicId != null && responseData.businessLogicId != ""){
					$(modal).find("input[name='businessLogicId']").val(responseData.businessLogicId);
				}else{
					$(modal).find("input[name='businessLogicId']").val("");
				}
				if(responseData.businessLogicName != undefined && responseData.businessLogicName != null && responseData.businessLogicName != ""){
					$(modal).find("input[name='businessLogicIdAutocomplete']").val(responseData.businessLogicName);
				}else{
					$(modal).find("input[name='businessLogicIdAutocomplete']").val("");
				}
//				$(modal).find("input[name='businessLogicIdAutocomplete']").data("ui-autocomplete")._trigger("close");
				if(responseData.businessLogicCode != undefined && responseData.businessLogicCode != null && responseData.businessLogicCode != ""){
					$(modal).find("label[name='businessLogicCode']").text(responseData.businessLogicCode);
				}else{
					$(modal).find("label[name='businessLogicCode']").text("");
				}
				var arrRequestData = responseData.lstInputBean;
				var arrId = new Array();
				for(var i=0;i<arrRequestData.length;i++){
					arrId[arrRequestData[i].inputBeanId] = i;
				}
				var arrInputValues = data.parameterInputBeans;
				if(arrInputValues != undefined && arrInputValues.length >0){
					for(var i =0;i<arrInputValues.length;i++){
						// only compare with value 
						var currentInput = arrInputValues[i];
						if(currentInput.inputBeanId != undefined && currentInput.inputBeanId != null){
							if(arrId.hasOwnProperty(currentInput.inputBeanId)){
								var index = arrId[currentInput.inputBeanId];
								var obj = arrRequestData[index];
								obj.parameterId = currentInput.parameterId;
								obj.parameterIdAutocomplete = currentInput.parameterIdAutocomplete;
								obj.parameterScope = currentInput.parameterScope;
								obj.lstParameterIndex = currentInput.lstParameterIndex;
								
								//check
								if(obj.inputBeanCode ==  currentInput.inputBeanCode 
										&& obj.inputBeanName == currentInput.inputBeanName 
										&& obj.dataType == currentInput.dataType 
										&& obj.arrayFlg.toString() == currentInput.arrayFlg.toString()){
									obj.impactStatus = "0";
			
								}else{
									obj.impactStatus = "2";
								}
								arrRequestData[index] = obj;
							}else{
								currentInput.impactStatus = "3";
								arrRequestData.push(currentInput);
							}
						}
					}
				}
				arrRequestData.sort(sortByItemSequenceNo);
				for(var i=0;i<arrRequestData.length;i++){
					var currentInput = arrRequestData[i];
					var tableIndex = (currentInput.tableIndex == null || currentInput.tableIndex == undefined) ? "" : currentInput.tableIndex;
					currentInput.tableIndex = tableIndex;
					
					var dataTypeStr = CL_BD_DATATYPE[currentInput.dataType];
					if(eval(currentInput.arrayFlg)){
						dataTypeStr += "[]";
					}
					currentInput.dataTypeStr = dataTypeStr;
					var templateInput = $(modal).find("#tbl-common-inputbean-template").tmpl(currentInput);
					
					bindDataToAssignAutocomplete(currentInput.parameterId,"parameter",currentInput.lstParameterIndex,arrIndexLoop,$(templateInput),modal);
					
//					var inforCurrentParam = new Object();
//					inforCurrentParam = getInformationOfParameter(currentInput.parameterId);
//					if(inforCurrentParam != undefined){
//						$(templateInput).find("input[name='parameterId']").val(inforCurrentParam.optionValue);
//						$(templateInput).find("input[name='parameterIdAutocomplete']").val(inforCurrentParam.optionLabel);
//						$(templateInput).find("input[name='parameterId']").attr("parameterScope",inforCurrentParam.output01);
//					}else{
//						$(templateInput).find("input[name='parameterId']").val("");
//						$(templateInput).find("input[name='parameterIdAutocomplete']").val("");
//					}
					$(templateInput).find("td.btn-remove").children().hide();
					if(currentInput.impactStatus == "3"){
						$(templateInput).find("td.btn-remove").children().show();
						$(templateInput).addClass("qp-bdesign-tr-remove");
						$(templateInput).find("input[name*='inputBeanId']").attr("impactStatus","3");
					}else if(currentInput.impactStatus != "0"){
						$(templateInput).addClass("qp-bdesign-tr-change");
					}
					
					$(modal).find("#tbl-common-inputbean").find("tbody").append(templateInput);
				}
				
				//output bean
				arrRequestData = responseData.lstOutputBean;
				arrId = new Array();
				for(var i=0;i<arrRequestData.length;i++){
					arrId[arrRequestData[i].outputBeanId] = i;
				}
				var arrOutputValues = data.parameterOutputBeans;
				if(arrOutputValues != undefined && arrOutputValues.length >0){
					for(var i =0;i<arrOutputValues.length;i++){
						// only compare with value 
						var currentOutput = arrOutputValues[i];
						if(currentOutput.outputBeanId != undefined && currentOutput.outputBeanId != null){
							if(arrId.hasOwnProperty(currentOutput.outputBeanId)){
								var index = arrId[currentOutput.outputBeanId];
								var obj = arrRequestData[index];
			
								obj.targetId = currentOutput.targetId;
								obj.targetIdAutocomplete = currentOutput.targetIdAutocomplete;
								obj.targetScope = currentOutput.targetScope;
								obj.lstTargetIndex = currentOutput.lstTargetIndex;
								
								//check
								if(obj.outputBeanCode ==  currentOutput.outputBeanCode 
										&& obj.outputBeanName == currentOutput.outputBeanName 
										&& obj.dataType == currentOutput.dataType 
										&& obj.arrayFlg.toString() == currentOutput.arrayFlg.toString()){
									obj.impactStatus = "0";
								}else{
									obj.impactStatus = "2";
								}
								arrRequestData[index] = obj;
							}else{
								currentOutput.impactStatus = "3";
								arrRequestData.push(currentOutput);
							}
						}
					}
				}
				arrRequestData.sort(sortByItemSequenceNo);
				for(var i=0;i<arrRequestData.length;i++){
					var currentOutput = arrRequestData[i];
					var tableIndex = (currentOutput.tableIndex == null || currentOutput.tableIndex == undefined) ? "" : currentOutput.tableIndex;
					currentOutput.tableIndex = tableIndex;
					var dataTypeStr = CL_BD_DATATYPE[currentOutput.dataType];
					if(eval(currentOutput.arrayFlg)){
						dataTypeStr += "[]";
					}
					currentOutput.dataTypeStr = dataTypeStr;
					var templateOutput = $(modal).find("#tbl-common-outputbean-template").tmpl(currentOutput);
					
					bindDataToAssignAutocomplete(currentOutput.targetId,"target",currentOutput.lstTargetIndex,arrIndexLoop,$(templateOutput),modal);
//					
//					var inforCurrentParam = new Object();
//					inforCurrentParam = getInformationOfParameter(currentOutput.targetId);
//					if(inforCurrentParam != undefined){
//						$(templateOutput).find("input[name='targetId']").val(inforCurrentParam.optionValue);
//						$(templateOutput).find("input[name='targetIdAutocomplete']").val(inforCurrentParam.optionLabel);
//						$(templateOutput).find("input[name='targetId']").attr("parameterScope",inforCurrentParam.output01);
//					}else{
//						$(templateOutput).find("input[name='targetId']").val("");
//						$(templateOutput).find("input[name='targetIdAutocomplete']").val("");
//					}
					
					$(templateOutput).find("td.btn-remove").children().hide();
					if(currentOutput.impactStatus == "3"){
						$(templateOutput).find("td.btn-remove").children().show();
						$(templateOutput).addClass("qp-bdesign-tr-remove");
						$(templateOutput).find("input[name*='outputBeanId']").attr("impactStatus","3");
					}else if(currentOutput.impactStatus != "0"){
						$(templateOutput).addClass("qp-bdesign-tr-change");
					}
					$(modal).find("#tbl-common-outputbean").find("tbody").append(templateOutput);
				}
			}
		}
	}
//	$(modal).find("input[name='businessLogicIdAutocomplete']").data("ui-autocomplete")._trigger("close");
	$.qp.initialAllPicker(modal);
	initialViewModal(modal,isOnlyView);
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

function setPageableForSqlCode(htmlcode){
	//TODO add page able for sql code
	var isPageable = checkPageableInSql(htmlcode);
	if(!isPageable){
		return;
	}
	displayPanigationByTypeProject(DB_TYPE_PROJECT);
}

/**
 * add code pageable for oracle or postgres
 * @param typeProject
 */
function displayPanigationByTypeProject(typeProject){
	if(typeProject == DATABASE_TYPE.oracle){
		addPaginationForOracle();
		$("#generatePageable>.CodeMirror").css("height","81px");
	}else{
		$("#generatePageable #txtPageableCode").val(CODE_PAGEABLE);
	}
	$("#generatePageable").show();
}

function addPaginationForOracle(){
	$("#lblPageableCodeOracle").text("SELECT * ( \n");
	$("#lblPageableCodeOracle2").text(")");
	$("#txtPageableCode").val(CODE_PAGEABLE_ORACLE);
	$("#txtPageableCode").attr("rows",4)
	$("#lblPageableCodeOracle").show();
	$("#lblPageableCodeOracle2").show();
}

function checkPageableInSql(htmlcode) {
	flagPageable = $(htmlcode).find("input[type=hidden][name='sqlDesignForm.pageable']");
	if(flagPageable.length != 0 && flagPageable.val() == 1){
		return true;
	}
	return false;
}

function bindDataToAssignAutocomplete(id,typeOfId,lstIndexOfId,arrIndexLoop,newRow,modal,removeCallBack){
	var inforCurrentParam = getInformationOfParameter(id);
	var hasArray = false;
	if(inforCurrentParam != undefined){
		var infor = getFullInformationOfParameter(inforCurrentParam);
		if(!isEmptyQP(infor)){
			//case assign
			if(isObjectType( infor[0].output03)){
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
								var inforIndex = getInformationOfParameter(itemIndex.parameterIndexId);
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
				addClassFormInline(newDiv);
			}else{
				$(parentDiv).attr("pattern","0");
				$(newRow).find("input[name='"+typeOfId+"Id']").val(inforCurrentParam.optionValue);
				$(newRow).find("input[name='"+typeOfId+"IdAutocomplete']").val(inforCurrentParam.optionLabel); 
				$(newRow).find("input[name='"+typeOfId+"Id']").attr("parameterScope",inforCurrentParam.output01);
				removeClassFormInline(newRow);
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

function removeClassFormInline(element){
	var td = $(element).closest("td");
	if(td.hasClass("form-inline")){
		td.removeClass("form-inline");
		td.find("div.input-group").css("width", "100%");
	}
}

function addClassFormInline(element){
	var td = $(element).closest("td");
	if(!td.hasClass("form-inline")){
		td.addClass("form-inline");
	}
}

function openModalAssign(obj,isOnlyView) {
	var modal = BD_MODAL_NAME.ASSIGN;
	$(modal).data("target", obj);
	$(modal).data("instance", getInstanceOfContainer(obj));
	var value = $(obj).find("input[name='componentElement']").val();
	var data = convertToJson(value);
	$(modal).data("data", value);
	
	$(modal).find("input[name='label']").val("");
	$(modal).find("textarea[name='remark']").val("");
	$(modal).find(table).find("tbody").empty();
	if(data != null){
		var table = "#tbl-assign-parameter";
		if(data.label != undefined && data.label != null){
			$(modal).find("input[name='label']").val(data.label);
		}else{
			$(modal).find("input[name='label']").val("");
		}
		if(data.remark != undefined && data.remark != null){
			$(modal).find("textarea[name='remark']").val(data.remark);
		}else{
			$(modal).find("textarea[name='remark']").val("");
		}
		$(modal).find(table).find("tbody").empty();
		var arrIndexLoop = getLabelOfIndexLoop();
		if(data.details != undefined && data.details != null){
			var arrData = data.details;
			for(var i=0;i<arrData.length;i++){
				var objAssign = arrData[i];
				var dataGroup = (objAssign.dataGroup == null || objAssign.dataGroup == undefined) ? "" : objAssign.dataGroup;
				//fix old data of database
				if(dataGroup != ""){
					var inforOfCurrent = getInformationOfParameter(objAssign.targetId);
					var inforOfParent = getInformationOfParameter(inforOfCurrent.output01 + inforOfCurrent.output02);
					if(!eval(inforOfParent.output04)){
						dataGroup = "";
					}
				}
				var newRow = $.qp.ar.addRow({link:this,tableId:'tbl-assign-parameter',templateId:'tbl-assign-parameter-template',templateData:{groupId:dataGroup}});
				
				bindDataToAssignAutocomplete(objAssign.targetId,"target",objAssign.lstTargetIndex,arrIndexLoop,$(newRow),modal,onremoveTargetCallbackOfAssignSet);
				
				var assignType = (objAssign.assignType == null || objAssign.assignType == undefined) ? "0" :objAssign.assignType;
				if("0" == assignType){
					var hasArray = bindDataToAssignAutocomplete(objAssign.parameterId,"parameter",objAssign.lstParameterIndex,arrIndexLoop,$(newRow),modal,onremoveParameterCallbackOfAssignSet);
					if(hasArray){
						$(newRow).find("a.glyphicon-cog").hide();
					}else{
						$(newRow).find("a.glyphicon-cog").show();
					}
				}else if("1" == assignType){
					$(newRow).find("input[name='parameterId']").val("");
					objAssign.parameterIdAutocomplete = null;
					objAssign.targetScope = null;
					
					var formulaDefinitionContent =  (objAssign.formulaDefinitionContent == undefined || objAssign.formulaDefinitionContent == null) ? "" : objAssign.formulaDefinitionContent;
					$(newRow).find("input[name='parameterIdAutocomplete']").val(formulaDefinitionContent)
					
					var formulaDefinitionDetails = (objAssign.formulaDefinitionDetails == undefined || objAssign.formulaDefinitionDetails == null) ? [] : objAssign.formulaDefinitionDetails;
					$(newRow).find("input[name='formulaDefinitionDetails']").val(convertToString(formulaDefinitionDetails));
					$(newRow).find("input[name='parameterIdAutocomplete']").addClass("qp-bdesign-tr-change");
					$(newRow).find("[name=assignType]").val("1");
				}
//				$(newRow).find("input[name='targetIdAutocomplete']").data("ui-autocomplete")._trigger("close");
			}
		}
	}
	$.qp.initialAllPicker($(modal));
	initialViewModal(modal,isOnlyView);
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

function openModalDecision(obj,isOnlyView) {
	// prepared initial display
	var modal = BD_MODAL_NAME.DECISION;
	$(modal).data("target", obj);
	$(modal).data("instance", getInstanceOfContainer(obj));
	var value = $(obj).find("input[name='componentElement']").val();
	var data = convertToJson(value);
	
	//refesh data
	$(modal).find("input[name='label']").val("");
	$(modal).find("textarea[name='remark']").val("");
	$(modal).find("#tbl-decision-inputbean").find("tbody").empty();
	$(modal).find("#tbl-decision-outputbean").find("tbody").empty();
	$(modal).find("input[name='decisionTableId']").val("");
	$(modal).find("input[name='decisionTableIdAutocomplete']").val("");
	$(modal).find("label[name='decisionTableCode']").text("");
	$(modal).find("input[name='decisionTableIdAutocomplete']").closest("td").removeClass("qp-bdesign-tr-remove");
	$(modal).find("#tbl_logic_design").empty();
	$(modal).find("input[name='moduleId']").val("");
	$(modal).find("input[name='moduleIdAutocomplete']").val("");
	if(data != null){
		var arrIndexLoop = getLabelOfIndexLoop();
		$(modal).data("data", value);
		if(data.label != undefined && data.label != null){
			$(modal).find("input[name='label']").val(data.label);
		}else{
			$(modal).find("input[name='label']").val("");
		}
		if(data.moduleId != undefined && data.moduleId != null){
			$(modal).find("input[name='moduleId']").val(data.moduleId);
			$(modal).find("input[name='moduleIdAutocomplete']").val(data.moduleIdAutocomplete);
			$(modal).find("input[name='decisionTableIdAutocomplete']").attr("arg03",data.moduleId);
		}else{
			$(modal).find("input[name='moduleId']").val("");
			$(modal).find("input[name='moduleIdAutocomplete']").val("");
		}
		if(data.remark != undefined && data.remark != null){
			$(modal).find("textarea[name='remark']").val(data.remark);
		}else{
			$(modal).find("textarea[name='remark']").val("");
		}
		$(modal).find("#tbl_logic_design").next().hide();
		// main process initial display
		if(data.decisionTableId != undefined && data.decisionTableId != null && data.decisionTableId != "") {
			
			var url = CONTEXT_PATH + "/businessdesign/getDataDecisionComp?decisionTbId="+data.decisionTableId+"&r="+Math.random();
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
					var href = $(modal).find("label[name='decisionTableCode']").parent().attr('href').split("=");
					$(modal).find("label[name='decisionTableCode']").parent().attr('href',href[0]+ "=" + decisionInfor.decisionTbId);
				}else{
					$(modal).find("label[name='decisionTableCode']").text("");
				}
				
				// .1 Process for input
				
				var arrId = new Array();
				for(var i=0;i<arrReqInput.length;i++){
					arrId[arrReqInput[i].decisionInputBeanId] = i;
				}
				
				var arrInputValues = data.parameterInputBeans;
				if(arrInputValues != undefined && arrInputValues.length >0){
					for(var i =0;i<arrInputValues.length;i++){
						// only compare with value 
						var currentInput = arrInputValues[i];
						if(currentInput.decisionInputBeanId != undefined && currentInput.decisionInputBeanId != null){
							if(arrId.hasOwnProperty(currentInput.decisionInputBeanId)){
								var index = arrId[currentInput.decisionInputBeanId];
								var obj = arrReqInput[index];
								obj.parameterId = currentInput.parameterId;
								obj.parameterIdAutocomplete = currentInput.parameterIdAutocomplete;
								obj.parameterScope = currentInput.parameterScope;
								obj.lstParameterIndex = currentInput.lstParameterIndex;
								//check
								if(obj.decisionInputBeanCode ==  currentInput.decisionInputBeanCode 
										&& obj.decisionInputBeanName == currentInput.decisionInputBeanName 
										&& obj.dataType == currentInput.dataType){
									obj.impactStatus = "0";
								}else{
									obj.impactStatus = "2";
								}
								arrReqInput[index] = obj;
							}else{
								currentInput.impactStatus = "3";
								arrReqInput.push(currentInput);
							}
						}
					}
				}
				
				arrReqInput.sort(sortByItemSequenceNo);
				for(var i=0;i<arrReqInput.length;i++){
					var currentInput = arrReqInput[i];
					var tableIndex = (currentInput.tableIndex == null || currentInput.tableIndex == undefined) ? "" : currentInput.tableIndex;
					currentInput.tableIndex = tableIndex;
					currentInput.dataTypeStr = CL_BD_DATATYPE[currentInput.dataType];
					var templateInput = $(modal).find("#tbl-decision-inputbean-template").tmpl(currentInput);
					
//					var inforCurrentParam = new Object();
//					inforCurrentParam = getInformationOfParameter(currentInput.parameterId);
//					if(inforCurrentParam != undefined){
//						$(templateInput).find("input[name='parameterId']").val(inforCurrentParam.optionValue);
//						$(templateInput).find("input[name='parameterIdAutocomplete']").val(inforCurrentParam.optionLabel);
//						$(templateInput).find("input[name='parameterId']").attr("parameterScope",inforCurrentParam.output01);
//					}else{
//						$(templateInput).find("input[name='parameterId']").val("");
//						$(templateInput).find("input[name='parameterIdAutocomplete']").val("");
//					}
					bindDataToAssignAutocomplete(currentInput.parameterId,"parameter",currentInput.lstParameterIndex,arrIndexLoop,$(templateInput),modal);
					
					$(templateInput).find("td.btn-remove").children().hide();
					if(currentInput.impactStatus == "3"){
						$(templateInput).find("td.btn-remove").children().show();
						$(templateInput).addClass("qp-bdesign-tr-remove");
						$(templateInput).find("input[name*='decisionInputBeanId']").attr("impactStatus","3");
					}else if(currentInput.impactStatus != "0"){
						$(templateInput).addClass("qp-bdesign-tr-change");
					}
					
					$(modal).find("#tbl-decision-inputbean").find("tbody").append(templateInput);
				}
				
				// .2 Process for output
				arrId = new Array();
				for(var i=0;i<arrReqOutput.length;i++){
					arrId[arrReqOutput[i].decisionOutputBeanId] = i;
				}
				
				var arrOutputValues = data.parameterOutputBeans;
				if(arrOutputValues != undefined && arrOutputValues.length >0){
					for(var i =0;i<arrOutputValues.length;i++){
						// only compare with value 
						var currentOutput = arrOutputValues[i];
						if(currentOutput.decisionOutputBeanId != undefined && currentOutput.decisionOutputBeanId != null){
							if(arrId.hasOwnProperty(currentOutput.decisionOutputBeanId)){
								var index = arrId[currentOutput.decisionOutputBeanId];
								var obj = arrReqOutput[index];
								obj.targetId = currentOutput.targetId;
								obj.targetIdAutocomplete = currentOutput.targetIdAutocomplete;
								obj.targetScope = currentOutput.targetScope;
								obj.lstTargetIndex = currentOutput.lstTargetIndex;
								//check
								if(obj.decisionOutputBeanCode ==  currentOutput.decisionOutputBeanCode 
										&& obj.decisionOutputBeanName == currentOutput.decisionOutputBeanName 
										&& obj.dataType == currentOutput.dataType){
									obj.impactStatus = "0";
								}else{
									obj.impactStatus = "2";
								}
								arrReqOutput[index] = obj;
							}else{
								currentOutput.impactStatus = "3";
								arrReqOutput.push(currentOutput);
							}
						}
					}
				}
				
				arrReqOutput.sort(sortByItemSequenceNo);
				for(var i=0;i<arrReqOutput.length;i++){
					var currentOutput = arrReqOutput[i];
					var tableIndex = (currentOutput.tableIndex == null || currentOutput.tableIndex == undefined) ? "" : currentOutput.tableIndex;
					currentOutput.tableIndex = tableIndex;
					currentOutput.dataTypeStr = CL_BD_DATATYPE[currentOutput.dataType];
					var templateOutput = $(modal).find("#tbl-decision-outputbean-template").tmpl(currentOutput);
					
//					var inforCurrentParam = new Object();
//					inforCurrentParam = getInformationOfParameter(currentOutput.targetId);
//					if(inforCurrentParam != undefined){
//						$(templateOutput).find("input[name='targetId']").val(inforCurrentParam.optionValue);
//						$(templateOutput).find("input[name='targetIdAutocomplete']").val(inforCurrentParam.optionLabel);
//						$(templateOutput).find("input[name='targetId']").attr("parameterScope",inforCurrentParam.output01);
//					}else{
//						$(templateOutput).find("input[name='targetId']").val("");
//						$(templateOutput).find("input[name='targetIdAutocomplete']").val("");
//					}
					bindDataToAssignAutocomplete(currentOutput.targetId,"target",currentOutput.lstTargetIndex,arrIndexLoop,$(templateOutput),modal);
	
					$(templateOutput).find("td.btn-remove").children().hide();
					if(currentOutput.impactStatus == "3"){
						$(templateOutput).find("td.btn-remove").children().show();
						$(templateOutput).addClass("qp-bdesign-tr-remove");
						$(templateOutput).find("input[name*='decisionOutputBeanId']").attr("impactStatus","3");
					}else if(currentOutput.impactStatus != "0"){
						$(templateOutput).addClass("qp-bdesign-tr-change");
					}
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
			}else{
				if(data.decisionTableId != undefined && data.decisionTableId != null && data.decisionTableId != ""){
					$(modal).find("input[name='decisionTableId']").val(data.decisionTableId);
				}else{
					$(modal).find("input[name='decisionTableId']").val("");
				}
				
				if(data.decisionTableIdAutocomplete != undefined && data.decisionTableIdAutocomplete != null && data.decisionTableIdAutocomplete != ""){
					$(modal).find("input[name='decisionTableIdAutocomplete']").val(data.decisionTableIdAutocomplete);
				}else{
					$(modal).find("input[name='decisionTableIdAutocomplete']").val("");
				}
				
				$(modal).find("input[name='decisionTableIdAutocomplete']").data("ui-autocomplete")._trigger("close");
				
				if(data.decisionTableCode != undefined && data.decisionTableCode != null && data.decisionTableCode != ""){
					$(modal).find("label[name='decisionTableCode']").text(data.decisionTableCode);
				}else{
					$(modal).find("label[name='decisionTableCode']").text("");
				}
				$(modal).find("input[name='decisionTableIdAutocomplete']").closest("td").addClass("qp-bdesign-tr-remove");
			}
		}
	}
	$.qp.initialAllPicker(modal);
	$(modal).find("input[name$='IdAutocomplete']").each(function(){
		$(this).data("ui-autocomplete")._trigger("close");
	});
	initialViewModal(modal,isOnlyView);
	$(modal).modal(
			  { 
			   show: true,
			   closable: false,
			   keyboard:false,
			   backdrop:'static'
			  }
			 );
}

function openModalFeedback(obj,isOnlyView) {
	var modal = $(BD_MODAL_NAME.FEEDBACK);
	$(modal).data("target", obj);
	$(modal).data("instance", getInstanceOfContainer(obj));
	var value = $(obj).find("input[name='componentElement']").val();
	var data = convertToJson(value);
	$(modal).data("data", value);
	
	//refresh data
	$(modal).find("input[name='label']").val("");
	$(modal).find("textarea[name='remark']").val("");
	$(modal).find("select[name='type']").val("");
	$(modal).find("span[name='btnChooseParameter']").hide();
	$(modal).find("input[name='messageCode']").val("");
	$(modal).find("input[name='messageCodeAutocomplete']").val("");
	$(modal).find("input[name='messageParameter']").val("{}");
	if(data != null){
		if(data.label != undefined){
			$(modal).find("input[name='label']").val(data.label);
		}else{
			$(modal).find("input[name='label']").val("");
		}
		
		var messageParameter = [];
		if(data.messageCode != undefined && data.messageCode != ""){
			$(modal).find("input[name='messageCode']").val(data.messageCode);
			$(modal).find("input[name='messageCodeAutocomplete']").val(data.messageCodeAutocomplete);
			messageParameter = convertToString(data.messageParameter);
			$(modal).find("input[name='messageParameter']").val(messageParameter);
			var count = countParameterOfMessage(data.messageCodeAutocomplete);
			if(count == 0){
				$(modal).find("span[name='btnChooseParameter']").hide();
			}else{
				$(modal).find("span[name='btnChooseParameter']").show();
			}
			var selectedValue = isEmptyQP(data.existedMessageFlg) ? false : eval(data.existedMessageFlg);
			$(modal).find("input[name='messageCodeAutocomplete']").attr("selectedValue",selectedValue);
			
		}else{
			$(modal).find("input[name='messageCode']").val("");
			$(modal).find("input[name='messageCodeAutocomplete']").val("");
			messageParameter = convertToString(messageParameter);
			$(modal).find("input[name='messageParameter']").val(messageParameter);
			$(modal).find("span[name='btnChooseParameter']").hide();
		}
		$(modal).find("input[name='messageCodeAutocomplete']").data("ui-autocomplete")._trigger("close");
		if(data.remark != undefined){
			$(modal).find("textarea[name='remark']").val(data.remark);
		}else{
			$(modal).find("textarea[name='remark']").val("");
		}
		if(data.type != undefined){
			$(modal).find("select[name='type']").val(data.type);
		}else{
			$(modal).find("select[name='type']").val("");
		}
	}
	onchangeFeedbackTypeOfFeedbackSet($(modal).find("select[name='type']"));
	$.qp.initialAllPicker($(modal));
	initialViewModal(modal,isOnlyView);
	$(modal).modal(
			  { 
			   show: true,
			   closable: false,
			   keyboard:false,
			   backdrop:'static'
			  }
			 );
}

function openModalNavigator(obj,isOnlyView) {
	var modal = BD_MODAL_NAME.NAVIGATOR;
	var table = "#tbl-navigator-inputbean";
	$(modal).data("target", obj);
	$(modal).data("instance", getInstanceOfContainer(obj));
	var value = $(obj).find("input[name='componentElement']").val();
	var data = convertToJson(value);
	
	//refesh data
	$(modal).find("input[name='label']").val("");
	$(modal).find("textarea[name='remark']").val("");
	$(modal).data("data", value);
	$(modal).find("input:radio[name=transitionType][value=0]").prop('checked', true);
	$(modal).find("input:radio[name=navigatorToType][value=0]").prop('checked', true);
	$(modal).find("input[name='navigatorToIdAutocomplete']").attr("selectsqlid","getAutocompleteScreenDesignForNavigatorNode");
	$(modal).find("input[name='navigatorToIdAutocomplete']").attr("navigatorToName","");
	$(modal).find("input[name='navigatorToIdAutocomplete']").closest("td").removeClass("qp-bdesign-tr-remove");
	$(modal).find("input[name='navigatorToIdAutocomplete']").val("");
	$(modal).find("input[name='navigatorToId']").val("");
	$(modal).find("#tbl-navigator-inputbean").find("tbody").empty();
	$(modal).find("input[name='moduleId']").val("");
	$(modal).find("input[name='moduleIdAutocomplete']").val("");
	
	$(modal).find("input[name=moduleIdAutocomplete]").attr('disabled', false);
	$(modal).find("input[name=navigatorToIdAutocomplete]").attr('disabled', false);
	if(data != null){
		var arrIndexLoop = getLabelOfIndexLoop();
		if(data.label != undefined){
			$(modal).find("input[name='label']").val(data.label);
		}else{
			$(modal).find("input[name='label']").val("");
		}
		if(data.moduleId != undefined && data.moduleId != null){
			$(modal).find("input[name='moduleId']").val(data.moduleId);
			$(modal).find("input[name='moduleIdAutocomplete']").val(data.moduleIdAutocomplete);
			$(modal).find("input[name='navigatorToIdAutocomplete']").attr("arg03",data.moduleId);
		}else{
			$(modal).find("input[name='moduleId']").val("");
			$(modal).find("input[name='moduleIdAutocomplete']").val("");
		}
		if(data.remark != undefined){
			$(modal).find("textarea[name='remark']").val(data.remark);
		}else{
			$(modal).find("textarea[name='remark']").val("");
		}
		$(modal).find("input[name=transitionType][value=1]").parent().show();
		var sqlId = "getAutocompleteBusinessLogicForNavigatorBD";
		$(modal).find("input[name=transitionType][value=0]").parent().hide();
		$(modal).find("input[name=transitionType][value=1]").parent().hide();
		
		$(modal).find("input[name='navigatorToId']").val("");
		$(modal).find("input[name='navigatorToIdAutocomplete']").val("");
		if(!isEmptyQP(data.navigatorToType)){
			$(modal).find("input:radio[name=navigatorToType][value="+data.navigatorToType+"]").prop('checked', true);
			$(modal).find("a[name='registerNavigatorLink']").show();
			switch (data.navigatorToType.toString()) {
			case "0":
				$(modal).find("[name=tranType]").hide();
				$(modal).find("td[name=navigatorType]").attr("colspan", 3);
				$(modal).find("input[name='navigatorToId']").val(data.navigatorToId);
				$(modal).find("input[name='navigatorToIdAutocomplete']").val(data.navigatorToIdAutocomplete);
				$(modal).find("input[name='navigatorToIdAutocomplete']").attr("navigatorToName",data.navigatorToName);
				sqlId = "getAutocompleteScreenDesignForNavigatorNode";
				$(modal).find("a[name='registerNavigatorLink']").attr("onClick","openRegisterNewNavigator(0)");
				break;
			case "1":
				$(modal).find("td[name=navigatorType]").attr("colspan", 1);
				$(modal).find("[name=tranType]").show();
				$(modal).find("a[name='registerNavigatorLink']").attr("onClick","openRegisterNewNavigator(1)");
				$(modal).find("input[name=transitionType][value=0]").parent().show();
				$(modal).find("input[name=transitionType][value=1]").parent().show();
				sqlId = "getAutocompleteBusinessLogicForNavigatorBD";
				//input bean
				var url = CONTEXT_PATH + "/businessdesign/getInputBeanOfBusinessLogic?businessDesignId="+data.navigatorToId+"&r="+Math.random();
				var responseData = $.qp.getData(url);
				if(responseData.businessLogicId == undefined && responseData.businessLogicId == null){
					//set data by old data
					if(data.navigatorToIdAutocomplete != undefined){
						$(modal).find("input[name='navigatorToIdAutocomplete']").val(data.navigatorToIdAutocomplete);
					}else{
						$(modal).find("input[name='navigatorToIdAutocomplete']").val("");
					}
					$(modal).find("input[name='navigatorToIdAutocomplete']").closest("td").addClass("qp-bdesign-tr-remove");
				}else{
					//set data by response data
					$(modal).find("input[name='navigatorToId']").val(responseData.businessLogicId);
					
					if(responseData.businessLogicName != undefined){
						$(modal).find("input[name='navigatorToIdAutocomplete']").val(responseData.businessLogicName);
						$(modal).find("input[name='navigatorToIdAutocomplete']").attr("navigatorToName",responseData.businessLogicName);
					}else{
						$(modal).find("input[name='navigatorToIdAutocomplete']").val("");
						$(modal).find("input[name='navigatorToIdAutocomplete']").attr("navigatorToName","");
					}
					var arrRequestData = responseData.lstInputBean;
					var arrId = new Array();
					for(var i=0;i<arrRequestData.length;i++){
						arrId[arrRequestData[i].inputBeanId] = i;
					}
					var arrInputValues = data.parameterInputBeans;
					if(arrInputValues != undefined && arrInputValues.length >0){
						for(var i =0;i<arrInputValues.length;i++){
							// only compare with value 
							var currentInput = arrInputValues[i];
							if(currentInput.inputBeanId != undefined && currentInput.inputBeanId != null){
								if(arrId.hasOwnProperty(currentInput.inputBeanId)){
									var index = arrId[currentInput.inputBeanId];
									var obj = arrRequestData[index];
									obj.parameterId = currentInput.parameterId;
									obj.parameterIdAutocomplete = currentInput.parameterIdAutocomplete;
									obj.parameterScope = currentInput.parameterScope;
									obj.lstParameterIndex = currentInput.lstParameterIndex;
									
									//check
									if(obj.inputBeanCode ==  currentInput.inputBeanCode 
											&& obj.inputBeanName == currentInput.inputBeanName 
											&& obj.dataType == currentInput.dataType 
											&& obj.arrayFlg.toString() == currentInput.arrayFlg.toString()){
										obj.impactStatus = "0";
				
									}else{
										obj.impactStatus = "2";
									}
									arrRequestData[index] = obj;
								}else{
									currentInput.impactStatus = "3";
									currentInput.messageStringAutocomplete = currentInput.inputBeanName;
									arrRequestData.push(currentInput);
								}
							}
						}
					}
//					arrRequestData.sort(sortByItemSequenceNo);
					for(var i=0;i<arrRequestData.length;i++){
						var currentInput = arrRequestData[i];
						var tableIndex = (currentInput.tableIndex == null || currentInput.tableIndex == undefined) ? "" : currentInput.tableIndex;
						currentInput.tableIndex = tableIndex;
						
						var dataTypeStr = CL_BD_DATATYPE[currentInput.dataType];
						if(eval(currentInput.arrayFlg)){
							dataTypeStr += "[]";
						}
						currentInput.dataTypeStr = dataTypeStr;
						var templateInput = $(modal).find("#tbl-navigator-inputbean-template").tmpl(currentInput);
						
						bindDataToAssignAutocomplete(currentInput.parameterId,"parameter",currentInput.lstParameterIndex,arrIndexLoop,$(templateInput),modal);
		
						$(templateInput).find("td.btn-remove").children().hide();
						if(currentInput.impactStatus == "3"){
							$(templateInput).find("td.btn-remove").children().show();
							$(templateInput).addClass("qp-bdesign-tr-remove");
							$(templateInput).find("input[name*='inputBeanId']").attr("impactStatus","3");
						}else if(currentInput.impactStatus != "0"){
							$(templateInput).addClass("qp-bdesign-tr-change");
						}
						
						$(modal).find("#tbl-navigator-inputbean").find("tbody").append(templateInput);
					}
				}
				break;
			case "2":
				$(modal).find("td[name=navigatorType]").attr("colspan", 1);
				$(modal).find("[name=tranType]").show();
				$(modal).find("input[name=transitionType][value=0]").parent().show();
				sqlId = "getAutocompleteScreenDesignForNavigatorNode";
				$(modal).find("input[name=moduleIdAutocomplete]").attr('disabled', true);
				$(modal).find("input[name=navigatorToIdAutocomplete]").attr('disabled', true);
				$(modal).find("input[name=navigatorToIdAutocomplete]").val("Delete successful");
				$(modal).find("input[name=navigatorToId]").val("-1");
				$(modal).find("a[name='registerNavigatorLink']").hide();
				break;

			default:
				break;
			}
			
		}else{
			$(modal).find("input:radio[name=navigatorToType][value=0]").prop('checked', true);
			sqlId = "getAutocompleteScreenDesignForNavigatorNode";
		}
		$(modal).find("input[name='navigatorToIdAutocomplete']").attr("selectsqlid",sqlId);
	}
	$.qp.initialAllPicker($(modal));
	$(modal).find("input[name$='IdAutocomplete']").each(function(){
		$(this).data("ui-autocomplete")._trigger("close");
	});
	initialViewModal(modal,isOnlyView);
	$(modal).modal(
			  { 
			   show: true,
			   closable: false,
			   keyboard:false,
			   backdrop:'static'
			  }
			 );
}

function openModalLoop(obj,isOnlyView) {
	var modal = BD_MODAL_NAME.LOOP;
	$(modal).data("target", obj);
	$(modal).data("instance", getInstanceOfContainer(obj));
	var value = $(obj).find("input[name='componentElement']").val();
	var data = convertToJson(value);
	var templateInput = $(modal).find("#div-parameter-template").tmpl();
	$(modal).find("tr[name='trContent']").replaceWith(templateInput);

	var arrIndexLoop = getLabelOfIndexLoop();
	
	$(modal).data("data", value);
	//refesh data
	$(modal).find("input[name='label']").val("");
	$(modal).find("textarea[name='remark']").val("");
	$(modal).find('.bd-for-type-while').hide();
	$(modal).find('label[name=formulaDefinitionContent]').text("");
	$(modal).find('input[name=formulaDefinitionDetails]').val("");
	$(modal).find("input[name='parameterId']").val("");
	$(modal).find("input[name='parameterIdAutocomplete']").val("");
//	$(modal).find("input[name='fromValue']").val("");
//	$(modal).find("input[name='toValue']").val("");
	$(modal).find("select[name='loopType']").val("0");
	$(modal).find('.bd-for-type-foreach').show();
	$(modal).find("input[name='index']").val("");
	$(modal).find("select[name='loopStepType']").val(0);
	$(modal).find("input[name='loopStepValue']").val(1);
	$(modal).find("input[name='fromType']").prop("disabled",true);
	$(modal).find("input[name='toType']").prop("disabled",true);
	
	if(data != null){
		if(data.label != undefined){
			$(modal).find("input[name='label']").val(data.label);
		}else{
			$(modal).find("input[name='label']").val("");
		}
		if(data.remark != undefined){
			$(modal).find("textarea[name='remark']").val(data.remark);
		}else{
			$(modal).find("textarea[name='remark']").val("");
		}
		if(data.loopType != undefined){
			$(modal).find("select[name='loopType']").val(data.loopType);
			switch (parseInt(data.loopType)) {
			case 0:
				$(modal).find('.bd-for-type-while').hide();
				$(modal).find('.bd-for-type-foreach').show();
//				if(data.fromScope != undefined && data.fromScope != null){
//					var fromScope = eval(data.fromScope);
//					$(modal).find("input[name='fromScope']").prop("checked",fromScope);
//					$(modal).find("input[name='fromValue']").prop('disabled', fromScope)
//					if(fromScope){
//						$(modal).find("input[name='fromValue']").val("");
//					}else{
//						$(modal).find("input[name='fromValue']").val(data.fromValue);
//					}
//				}else{
//					$(modal).find("input[name='fromScope']").prop("checked",false);
//					$(modal).find("input[name='fromScope']").prop("disabled",true);
//					$(modal).find("input[name='fromValue']").prop('disabled', false)
//					$(modal).find("input[name='fromValue']").val("");
//				}
//				if(data.toScope != undefined && data.toScope != null){
//					var toScope = eval(data.toScope);
//					$(modal).find("input[name='toScope']").prop("checked",toScope);
//					$(modal).find("input[name='toValue']").prop('disabled', toScope)
//					if(toScope){
//						$(modal).find("input[name='toValue']").val("");
//					}else{
//						$(modal).find("input[name='toValue']").val(data.toValue);
//					}
//				}else{
//					$(modal).find("input[name='toScope']").prop("checked",false);
//					$(modal).find("input[name='toScope']").prop("disabled",true);
//					$(modal).find("input[name='toValue']").prop('disabled', false)
//					$(modal).find("input[name='toValue']").val("");
//				}
				if (data.fromScope != undefined && data.fromScope != null) {
					if (data.fromScope.toString() == "-1"){
						$(modal).find("input[name='fromType']").prop("checked",true);
						$(modal).find("div[name='from']").find("input[name*='Autocomplete']").prop('disabled', true);
					} else {
						$(modal).find("input[name='fromType']").prop("checked",false);
						$(modal).find("div[name='from']").find("input[name*='Autocomplete']").prop('disabled', false);
						if (data.fromValue != undefined && data.fromValue != null) {
							$(modal).find("input[name='fromValue']").val(data.fromValue);
							var inforCurrentParam = getInformationOfParameter(data.fromValue);
							if(inforCurrentParam != undefined){
								$(modal).find("input[name='fromValueAutocomplete']").val(inforCurrentParam.optionLabel);
							}
						} else {
							$(modal).find("input[name='fromValue']").val("");
							$(modal).find("input[name='fromValueAutocomplete']").val("");
						}
						if (data.fromValueAutocomplete != undefined && data.fromValueAutocomplete != null) {
							$(modal).find("input[name='fromValueAutocomplete']").val(data.fromValueAutocomplete);
						} else {
							$(modal).find("input[name='fromValueAutocomplete']").val("");
						}
						$(modal).find("input[name='fromValue']").attr("parameterScope", data.fromScope);
						switch (data.fromScope.toString()) {
							case "3":
								$(modal).find("input[name='fromValueAutocomplete']").val(data.fromValue);
								break;
							case "4":
								var actualIndex = data.fromValue.substring(1, data.fromValue.length);
								var labelIndex = arrIndexLoop[actualIndex];
								if(labelIndex != undefined){
									$(modal).find("input[name='fromValueAutocomplete']").val(labelIndex);
								}else{
									$(modal).find("input[name='fromValueAutocomplete']").val("");
								}
								break;
							case "0":
							case "1":
							case "2":
								var inforIndex = getInformationOfParameter(data.fromValue);
								if(inforIndex != undefined){
									$(modal).find("input[name='fromValueAutocomplete']").val(inforIndex.optionLabel);
								}else{
									$(modal).find("input[name='fromValueAutocomplete']").val("");
								}
							default:
								break;
						}
						if (data.lstFromIndex != undefined && data.lstFromIndex != null && data.lstFromIndex.length != 0) {
							bindDataToAssignAutocomplete(data.fromValue,"from",data.lstFromIndex,arrIndexLoop,$(templateInput),modal);
						}
					}
				} else {
					$(modal).find("input[name='fromValue']").attr("parameterScope","");
				}
				
			
				
				
				if (data.toScope != undefined && data.toScope != null) {
					if (data.toScope.toString() == "-1"){
						$(modal).find("input[name='toType']").prop("checked",true);
						$(modal).find("div[name='to']").find("input[name*='Autocomplete']").prop('disabled', true);
					}else {
						$(modal).find("input[name='toType']").prop("checked",false);
						$(modal).find("div[name='to']").find("input[name*='Autocomplete']").prop('disabled', false);
						if (data.toValue != undefined && data.toValue != null) {
							$(modal).find("input[name='toValue']").val(data.toValue);
							var inforCurrentParam = getInformationOfParameter(data.toValue);
							if(inforCurrentParam != undefined){
								$(modal).find("input[name='toValueAutocomplete']").val(inforCurrentParam.optionLabel);
							}
						} else {
							$(modal).find("input[name='toValue']").val("");
							$(modal).find("input[name='toValueAutocomplete']").val("");
						}
						if (data.toValueAutocomplete != undefined && data.toValueAutocomplete != null) {
							$(modal).find("input[name='toValueAutocomplete']").val(data.toValueAutocomplete);
						} else {
							$(modal).find("input[name='toValueAutocomplete']").val("");
						}
						$(modal).find("input[name='toValue']").attr("parameterScope", data.toScope);
						switch (data.toScope.toString()) {
							case "3":
								$(modal).find("input[name='toValueAutocomplete']").val(data.toValue);
								break;
							case "4":
								var actualIndex = data.toValue.substring(1, data.toValue.length);
								var labelIndex = arrIndexLoop[actualIndex];
								if(labelIndex != undefined){
									$(modal).find("input[name='toValueAutocomplete']").val(labelIndex);
								}else{
									$(modal).find("input[name='toValueAutocomplete']").val("");
								}
								break;
							case "0":
							case "1":
							case "2":
								var inforIndex = getInformationOfParameter(data.toValue);
								if(inforIndex != undefined){
									$(modal).find("input[name='toValueAutocomplete']").val(inforIndex.optionLabel);
								}else{
									$(modal).find("input[name='toValueAutocomplete']").val("");
								}
							default:
								break;
						}
						if (data.lstToIndex != undefined && data.lstToIndex != null && data.lstToIndex.length != 0) {
							bindDataToAssignAutocomplete(data.toValue,"to",data.lstToIndex,arrIndexLoop,$(templateInput),modal);
						}
					}
				} else {
					$(modal).find("input[name='toValue']").attr("parameterScope","");
				}
				
				var inforCurrentParam = getInformationOfParameter(data.parameterId);
				if(inforCurrentParam != undefined){
					$(modal).find("input[name='parameterId']").val(inforCurrentParam.optionValue);
					$(modal).find("input[name='parameterIdAutocomplete']").val(inforCurrentParam.optionLabel);
					$(modal).find("input[name='parameterId']").attr("parameterScope",inforCurrentParam.output01);
					$(modal).find("input[name='fromType']").prop("disabled",false);
					$(modal).find("input[name='toType']").prop("disabled",false);
				}else{
					$(modal).find("input[name='parameterId']").val("");
					$(modal).find("input[name='parameterIdAutocomplete']").val("");
				}
				$(modal).find("input[name='parameterIdAutocomplete']").data("ui-autocomplete")._trigger("close");
				
				if(data.index != undefined){
					$(modal).find("input[name='index']").val(data.index);
				}else{
					$(modal).find("input[name='index']").val("");
				}
				
				/* Udate by HungHX */
				if(data.loopStepType != undefined) {
					$(modal).find("select[name='loopStepType']").val(data.loopStepType);
				}
				if(data.loopStepValue != undefined) {
					$(modal).find("input[name='loopStepValue']").val((data.loopStepValue < 1)?1:data.loopStepValue);
				}
				
				break;
			case 1:
				$(modal).find('.bd-for-type-while').show();
				$(modal).find('.bd-for-type-foreach').hide();
				if(data.formulaDefinitionContent != undefined && data.formulaDefinitionContent != null){
					$(modal).find("label[name='formulaDefinitionContent']").text(data.formulaDefinitionContent);
				}else{
					$(modal).find("label[name='formulaDefinitionContent']").text("");
				}
				var formulaDefinitionDetails = (data.formulaDefinitionDetails == undefined || data.formulaDefinitionDetails ==null) ? [] :data.formulaDefinitionDetails ;
				$(modal).find("input[name='formulaDefinitionDetails']").val(convertToString(formulaDefinitionDetails));
				break;
			default:
				break;
			}
		}else{
			$(modal).find("select[name='loopType']").val("0");
			$(modal).find('.bd-for-type-foreach').show();
		}
	}
	
	$.qp.initialAllPicker($(modal));
	initialViewModal(modal,isOnlyView);
	$(modal).modal(
			  { 
			   show: true,
			   closable: false,
			   keyboard:false,
			   backdrop:'static'
			  }
			 );
}

function openModalIf(obj,isOnlyView) {
	var modal = BD_MODAL_NAME.IF;
	var tableCondition = "#tbl-ifcondition";
	$(modal).data("target", obj);
	$(modal).data("instance", getInstanceOfContainer(obj));
	var value = $(obj).find("input[name='componentElement']").val();
	var data = convertToJson(value);
	
	//refresh data
	$(modal).find("input[name='label']").val("");
	$(modal).find("textarea[name='remark']").val("");
	$(modal).find(tableCondition).find("tbody").empty();
	if(data != null){
		$(modal).data("data", value);
		if(data.label != undefined){
			$(modal).find("input[name='label']").val(data.label);
		}else{
			$(modal).find("input[name='label']").val("");
		}
		if(data.remark != undefined){
			$(modal).find("textarea[name='remark']").val(data.remark);
		}else{
			$(modal).find("textarea[name='remark']").val("");
		}

		$(modal).find(tableCondition).find("tbody").empty();
		if(data.ifConditionDetails != undefined){
			var arrConditions = data.ifConditionDetails;
			if(arrConditions.length >= 1){
				for(var i =0;i<arrConditions.length -1;i++){
					$.qp.addRowJSByLinkEx($(modal).find(tableCondition).next().find("a"),'tbl-ifcondition','tbl-ifcondition-template');
					var objConditionDetail =  arrConditions[i];
					
					var caption = (objConditionDetail.caption != undefined && objConditionDetail.caption != null) ? objConditionDetail.caption : "";
					$(modal).find(tableCondition).find("tbody").find("tr:eq("+i+")").find("input[name=caption]").val(caption);
					
					var conditionRemark = (objConditionDetail.conditionRemark != undefined && objConditionDetail.conditionRemark != null) ? objConditionDetail.conditionRemark : "";
					$(modal).find(tableCondition).find("tbody").find("tr:eq("+i+")").find("input[name=conditionRemark]").val(conditionRemark);
	
					var formulaDefinitionContent = (objConditionDetail.formulaDefinitionContent != undefined && objConditionDetail.formulaDefinitionContent != null) ? objConditionDetail.formulaDefinitionContent : "";
					$(modal).find(tableCondition).find("tbody").find("tr:eq("+i+")").find("label[name='formulaDefinitionContent']").text(formulaDefinitionContent);
					
					var formulaDefinitionDetails = (objConditionDetail.formulaDefinitionDetails == undefined || objConditionDetail.formulaDefinitionDetails ==null) ? [] :objConditionDetail.formulaDefinitionDetails ;
					$(modal).find(tableCondition).find("tbody").find("tr:eq("+i+")").find("input[name='formulaDefinitionDetails']").val(convertToString(formulaDefinitionDetails));
				}
				
				var elseCondition = arrConditions[arrConditions.length -1];
				var conditionRemark = (elseCondition.conditionRemark != undefined && elseCondition.conditionRemark != null) ? elseCondition.conditionRemark : "";
				$(modal).find("#tbl-elsecondition").find("td[name='indexOfElse']").text(arrConditions.length);
				$(modal).find("#tbl-elsecondition").find("input[name='conditionRemark']").val(conditionRemark);
				var usedConditionFlg = eval(elseCondition.usedConditionFlg);
				$(modal).find("#tbl-elsecondition").find("input[name='usedConditionFlg']").prop("checked",usedConditionFlg);
				$(modal).find("#tbl-elsecondition").find("input[type='text']").attr("disabled", !usedConditionFlg);
			}
		}
	}
	$.qp.initialAllPicker($(modal));
	initialViewModal(modal,isOnlyView);
	$(modal).modal(
			  { 
			   show: true,
			   closable: false,
			   keyboard:false,
			   backdrop:'static'
			  }
			 );
}

// event when click Validation Check component
function openModalValidationCheck(obj) {
	var modal = BD_MODAL_NAME.VALIDATIONCHECK;
	$(modal).data("target", obj);
	$(modal).data("instance", getInstanceOfContainer(obj));
	var value = $(obj).find("input[name='componentElement']").val();
	var data = convertToJson(value);
	var table = "#tbl-validationcheck";
	$(modal).data("data", value);
	
	//refresh data
	$(modal).find("input[name='label']").val("");
	$(modal).find("textarea[name='remark']").val("");
	$(modal).find(table).find("tbody").empty();
	if(data != null){
		if(data.label != undefined){
			$(modal).find("input[name='label']").val(data.label);
		}else{
			$(modal).find("input[name='label']").val("");
		}
		if(data.remark != undefined){
			$(modal).find("textarea[name='remark']").val(data.remark);
		}else{
			$(modal).find("textarea[name='remark']").val("");
		}
		var validationCheckDetails = [];
		if(data.validationCheckDetails != undefined){
			validationCheckDetails = data.validationCheckDetails;
		}
		var sourceData = convertToJson($("form").find("input[name='jsonInputBean']").val());
		if(sourceData != undefined && sourceData.length > 0) {
			for(var i = 0; i < sourceData.length; i++) {
				var dataGroup = (sourceData[i].groupId == undefined || sourceData[i].groupId == null) ? "" : sourceData[i].groupId;
				var newRow = $.qp.ar.addRow({link:this,tableId:'tbl-validationcheck',templateId:'tbl-validationcheck-template',templateData:{groupId:dataGroup}});
//				$(newRow).find("label[name=inputBeanName]").text(sourceData[i].messageStringAutocomplete);
				$(newRow).find("label[name=inputBeanCode]").text(sourceData[i].inputBeanCode);
				$(newRow).find("input[name=inputBeanId]").val(sourceData[i].inputBeanId);
				$(newRow).find("input[name=dataType]").val(sourceData[i].dataType);
				$(newRow).find("input[name=arrayFlg]").val(sourceData[i].arrayFlg);
				var dataType = sourceData[i].dataType == undefined ? "" :CL_BD_DATATYPE[sourceData[i].dataType];
				if(eval(sourceData[i].arrayFlg)== true){
					dataType += "[]";
				}
				$(newRow).find("label[name=dataType]").text(dataType);
			}
		}
		// According to input bean id
		var validateInputBean = new Array();
		for(var j=0; j < validationCheckDetails.length;j++){
			var objDetailsData = validationCheckDetails[j];
			var checkInput = new Object();
			if(validateInputBean[objDetailsData.inputBeanId] == undefined){
				var parameterItems = [];
				for(var k=j; k < validationCheckDetails.length;k++){
					var objDetailsDataTemp = validationCheckDetails[k];
					if(objDetailsDataTemp.inputBeanId == objDetailsData.inputBeanId){
						parameterItems.push(objDetailsDataTemp);
					}
				}
				checkInput.inputBeanId = objDetailsData.inputBeanId;
//				checkInput.inputBeanName = objDetailsData.inputBeanName;
				checkInput.inputBeanCode = objDetailsData.inputBeanCode;
				var dataType = objDetailsData.dataType == undefined ? "" :CL_BD_DATATYPE[objDetailsData.dataType];
				if(eval(objDetailsData.arrayFlg)== true){
					dataType += "[]";
				}
				checkInput.dataTypeStr = dataType;
				checkInput.dataType = objDetailsData.dataType;
				checkInput.arrayFlg = objDetailsData.arrayFlg;
				checkInput.parameterItems = parameterItems;
				validateInputBean[objDetailsData.inputBeanId] = checkInput;
			}
		}
		for(key in validateInputBean){
			var rowInput =$(modal).find("#tbl-validationcheck").find("input[name=inputBeanId][value="+key+"]").closest("tr");
			if(rowInput.length === 0){
				//deleted row
				var currentInput = validateInputBean[key];
				var templateInput = $(modal).find("#tbl-validationcheck-remove-template").tmpl(currentInput);
				var parameterItems = currentInput.parameterItems ;
				$(templateInput).addClass("qp-bdesign-tr-remove");
				$(modal).find("#tbl-validationcheck").find("tbody").append(templateInput);
				rowInput =$(modal).find("#tbl-validationcheck").find("input[name=inputBeanId][value="+key+"]").closest("tr");
			}else{
				var currentInput = validateInputBean[key];
				var dataType =  $(rowInput).find("input[name=dataType]").val();
				dataType = (dataType == undefined) ? null : dataType;
				var arrayFlg =  $(rowInput).find("input[name=arrayFlg]").val();
				arrayFlg = (arrayFlg == undefined) ? null : arrayFlg;
				if(currentInput.dataType != dataType || currentInput.arrayFlg.toString() != arrayFlg.toString()){
					$(rowInput).addClass("qp-bdesign-tr-remove");
				}
			}
			var parameterItems = validateInputBean[key].parameterItems ;
			$(rowInput).find("input[name=parameters]").val(convertToString(parameterItems));
			var groupData = checkGroupOfValidationCheck(parameterItems);
			if(groupData !=undefined){
				var currentRow = $(newRow);
				if(groupData.isLength){
					$(rowInput).find("td[name=length]").html("O");
				}else{
					$(rowInput).find("td[name=length]").empty();
				}
				if(groupData.isMandatory){
					$(rowInput).find("td[name=mandatory]").html("O");
				}else{
					$(rowInput).find("td[name=mandatory]").empty();
				}
				if(groupData.isOthers){
					$(rowInput).find("td[name=others]").html("O");
				}else{
					$(rowInput).find("td[name=others]").empty();
				}
			}
		}
	}
	$.qp.initialAllPicker($(modal));
	$(modal).modal(
			  { 
			   show: true,
			   closable: false,
			   keyboard:false,
			   backdrop:'static'
			  }
			 );
}

//event when click Validation Check Detail component
function openModalValidationCheckDetail(obj,isOnlyView) {
	var modal = BD_MODAL_NAME.VALIDATIONCHECK_DETAIL;
	$(modal).data("target", obj);
	var value = $(obj).closest("td").find("input[name*='jsonValidationInputs']").val();
	var data = convertToJson(value);
	$(modal).data("data", value);
	var dataType = "";
	var arrayFlg = "";
	
	dataType = $(obj).closest("tr").find("input[name*='dataType'],select[name*='dataType']").val();
	if("checkbox" == $(obj).closest("tr").find("input[name*='arrayFlg']").attr('type')){
		arrayFlg = $(obj).closest("tr").find("input[name*='arrayFlg']").prop("checked");
	}else{
		arrayFlg = $(obj).closest("tr").find("input[name*='arrayFlg']").val();
	}
	
	var table = "#tbl-validationcheck-detail";
	$(modal).find(table).find("tbody").empty();
	//var url = CONTEXT_PATH + "/businessdesign/getStandardCheckFWOfBusinessLogic?"+"r="+Math.random();
//	var arrRequestData = $.qp.getData(url);
	if(fcomMsgSource != null){
		//detect single or multiple by array flag of parent.
//		var inforOfCurrent = getInformationOfParameter(objAssign.targetId);
//		var inforOfParent = getInformationOfParameter(inforOfCurrent.output01 + inforOfCurrent.output02);
//		if(!eval(inforOfParent.output04)){
//			dataGroup = "";
//		}
		var groupIndex = $(obj).closest("tr").attr("data-ar-rgroup") == undefined ? "" : $(obj).closest("tr").attr("data-ar-rgroup");
		var parentRow = $(obj).closest("tr").closest("table").find("tr[data-ar-rgroupindex='"+groupIndex+"']");
		var isMutilple = false;
		if(parentRow.length >0 ){
			var arrayFlgOfParent = false;
			if("checkbox" == $(parentRow).find("input[name*='arrayFlg']").attr('type')){
				arrayFlgOfParent = $(parentRow).find("input[name*='arrayFlg']").prop("checked");
			}else{
				arrayFlgOfParent = $(parentRow).find("input[name*='arrayFlg']").val();
			}
			if(eval(arrayFlgOfParent))
				isMutilple = true;
		}
		
		for(var i=0;i<BD_STANDARD_CHECK.length;i++){
			var objCheck = BD_STANDARD_CHECK[i];
			// check datatype of item has this rule.
			var messageCode = objCheck.messageCode;
			var messageContent = "";
			if(isMutilple){
				messageContent = $.qp.getMessage(objCheck.messageCodeArray);
			}
			else
				messageContent = $.qp.getMessage(objCheck.messageCode);
			
			if(checkValidationRuleOfDataType(dataType,objCheck.type,arrayFlg) && messageContent != undefined){
				$.qp.addRowJSByLink($(modal).find(table).next().find("a"));
				var currentRow = $(modal).find(table).find("tbody").find("tr").last();
				var div = buildParameterBuilderForMessage(messageContent,objCheck.type,isMutilple);
				var validateName = objCheck.name;
				var validateType = objCheck.typeValid == 0 ? $.qp.getModuleMessage('sc.blogiccomponent.0165') : $.qp.getModuleMessage('sc.blogiccomponent.0166');
				$(currentRow).find("div[name=checkContent]").append(div);
				$(currentRow).find(".validateName").append(validateName);
				$(currentRow).find(".validateType").append(validateType);
//				if(2 == objCheck.type 
//						|| 51 == objCheck.type
//						|| 52 == objCheck.type
//						|| 53 == objCheck.type
//						|| 54 == objCheck.type
//						|| 55 == objCheck.type
//						|| 56 == objCheck.type
//						|| 57 == objCheck.type
//						|| 58 == objCheck.type
//						|| 59 == objCheck.type
//						|| 60 == objCheck.type
//						|| 61 == objCheck.type
//						|| 62 == objCheck.type){
//					$(currentRow).find("div[name=checkContent]").find('input:text[name="parameterCode"]').attr('class', 'form-control qp-input-text')
//				}
				var cssClass = getClassCssOfValidationCheck(objCheck.type,dataType);
				$(currentRow).find("div[name=checkContent]").find('input:text[name="parameterCode"]').attr('class', 'form-control').addClass(cssClass);
				$.qp.initialAllPicker(currentRow);
				if($(currentRow).find("input[name='parameterCodeAutocomplete']").length >0){
					$(currentRow).find("input[name='parameterCodeAutocomplete']").data("ui-autocomplete")._trigger("close");
				}
				$(currentRow).attr("validationType",objCheck.type);
			}else{
				//display message not exist
			}
			
		}
		//bind data
		for(var i=0;i<data.length;i++){
			validationType = data[i].validationType;
			var currentRow = $(modal).find(table).find("tbody").find("tr[validationType="+validationType+"]");
			var parameters = data[i].parameters;
			var levelOfMessage = "";
			var level = "";
			if(currentRow.length >0 && parameters!=undefined && parameters != null){
				for(var j=0;j < parameters.length;j++){
					var parameterType = parameters[j].parameterType;
					var divCurrent = $(currentRow).find("div[name=divMessageParameter][itemSequenceNo="+j+"]");
					if(divCurrent.length > 0){
						if("0" == parameterType){
							var dataTemp =parameters[j];
							var parameterCode = dataTemp.parameterCode == null ? "":dataTemp.parameterCode;
							$(divCurrent).find("input[name='parameterCode']").val(dataTemp.parameterCode);
							var parameterCodeAutocomplete = dataTemp.parameterCodeAutocomplete == null ? "":dataTemp.parameterCodeAutocomplete;
							$(divCurrent).find("input[name='parameterCode']").prevAll("input[name='parameterCodeAutocomplete']").val(parameterCodeAutocomplete);
							if(dataTemp.messageLevel != null){}
								levelOfMessage= getlevelOfMessage(dataTemp.messageLevel);
								level = dataTemp.messageLevel;
						}
						if("1" == parameterType){
							var dataTemp =parameters[j];
							var parameterCode = dataTemp.parameterCode == null ? "":dataTemp.parameterCode;
							$(divCurrent).find("input[name='parameterCode']").val(dataTemp.parameterCode);
							checkAndSetformatPatternInputValue(dataTemp,$(divCurrent).find("input[name='parameterCode']"));
						}
						$(divCurrent).find("input[type=text]").attr("disabled",false);
					}
				}
				$(currentRow).find("input[name=validateFlg]").attr("checked",true);
				$(currentRow).find("td.level").text(levelOfMessage);
				$(currentRow).find("td.level").attr("messageLevel",level);
			}
		}
		if($(modal).find("input[name='parameterCodeAutocomplete']").length >0){
			$(modal).find("input[name='parameterCodeAutocomplete']").each(function (){
				$(this).data("ui-autocomplete")._trigger("close");
				if(moduleIdOfBD != undefined && moduleIdOfBD != null){
					$(this).attr("arg03",moduleIdOfBD);
				}
			});
		}
//		$.qp.initialAllPicker($(modal));
		initialViewModal(modal,isOnlyView);
		$(modal).modal(
				  { 
				   show: true,
				   closable: false,
				   keyboard:false,
				   backdrop:'static'
				  }
				 );
	}else{
		alert("Don't have standard check")
	}
	
}
function checkAndSetformatPatternInputValue(messageParam, input){
	var formatPattern = messageParam.patternFormat;
	if(!formatPattern || formatPattern == ""){
		return;
	}
	if($(input).hasClass("qp-input-datetimepicker")){
		checkAndChangeInputDateTime(input,formatPattern);
	}
	if($(input).hasClass("qp-input-datepicker")){
		checkAndChangeInputDate(input,formatPattern);
	}
	if($(input).hasClass("qp-input-timepicker")){
		checkAndChangeInputTime(input,formatPattern);
	}
}

function changeFormatDateTime(formatDatetime){
	var formats = formatDatetime.split(" ");
	var uppercase = formats[0].toUpperCase();
	formats[0] = uppercase;
	return formats.join(" ").replace("aa","a");
}


function checkAndChangeInputDateTime(input, formatPattern){
	if($(input).val() == ""){
		return;
	}
	var formatPattern = changeFormatDateTime(formatPattern);
	var date = moment($(input).val(), formatPattern).toDate();
	if(formatPattern != fcomSysDatetimeFormat){
		var strDatetime = moment(date).format(fcomSysDatetimeFormat);
		$(input).val(strDatetime);
	}
}

function checkAndChangeInputDate(input, formatPattern){
	if($(input).val() == ""){
		return;
	}
	var date = moment($(input).val(), formatPattern.toUpperCase()).toDate();
	if(formatPattern != fcomSysDateFormat){
		var strDatetime = moment(date).format(fcomSysDateFormat);
		$(input).val(strDatetime);
	}
}

function checkAndChangeInputTime(input, formatPattern){
	if($(input).val() == ""){
		return;
	}
	var date = moment($(input).val(), formatPattern.replace("aa","a")).toDate();
	if(formatPattern != fcomSysTimeFormat){
		var strDatetime = moment(date).format(fcomSysTimeFormat);
		$(input).val(strDatetime);
	}
}

// event when click Business Check component
function openModalBusinessCheck(obj,isOnlyView) {
	var modal = BD_MODAL_NAME.BUSINESSCHECK;
	$(modal).data("target", obj);
	$(modal).data("instance", getInstanceOfContainer(obj));
	var value = $(obj).find("input[name='componentElement']").val();
	var data = convertToJson(value);
	var table = "#tbl-validationcheck";
	$(modal).data("data", value);
	
	//refesh data
	$(modal).find("input[name='label']").val("");
	$(modal).find("textarea[name='remark']").val("");
	$(modal).find("select[name=businessCheckType]").val(0);
	$(modal).find("#listBusinessCheck").empty();
	if(data != null){
		var arrIndexLoop = getLabelOfIndexLoop();
		if(data.label != undefined){
			$(modal).find("input[name='label']").val(data.label);
		}else{
			$(modal).find("input[name='label']").val("");
		}
		if(data.remark != undefined){
			$(modal).find("textarea[name='remark']").val(data.remark);
		}else{
			$(modal).find("textarea[name='remark']").val("");
		}
		var businessCheckDetails = data.businessCheckDetails == undefined ? [] : data.businessCheckDetails;
		for(var i= 0 ;i < businessCheckDetails.length; i++){
			var objDetail = businessCheckDetails[i];
			
			var checkType = objDetail.businessCheckType;
			var tableTmp;
			switch (parseInt(checkType)) {
			case 1:
				tableTmp = $("#tbl-bcheck-function-template").tmpl();
				var formulaDefinitionContent = (objDetail.formulaDefinitionContent != undefined && objDetail.formulaDefinitionContent != null) ? objDetail.formulaDefinitionContent : "";
				$(tableTmp).find("label[name='formulaDefinitionContent']").text(formulaDefinitionContent);
				
				var formulaDefinitionDetails = (objDetail.formulaDefinitionDetails == undefined || objDetail.formulaDefinitionDetails ==null) ? [] :objDetail.formulaDefinitionDetails ;
				$(tableTmp).find("input[name='formulaDefinitionDetails']").val(convertToString(formulaDefinitionDetails));
		
				break;
			case 2:
				tableTmp = $("#tbl-bcheck-existence-template").tmpl();
				if(objDetail.contents != undefined && objDetail.contents.length >0){
					for(var j=0;j<objDetail.contents.length;j++){
						var objCondition = objDetail.contents[j];
						var tblDesignIdAutocomplete = objCondition.tblDesignIdAutocomplete == null ? "" : objCondition.tblDesignIdAutocomplete;
						var tblDesignId = objCondition.tblDesignId == null ? "" : objCondition.tblDesignId;
						if(j==0){
							$(tableTmp).find("input[name=tblDesignIdAutocomplete]").val(tblDesignIdAutocomplete);
							$(tableTmp).find("input[name=tblDesignId]").val(tblDesignId);
						}
						var currentRow = $("#tbl-content-conditions-template").tmpl();
						var columnIdAutocomplete = objCondition.columnIdAutocomplete == null ? "" : objCondition.columnIdAutocomplete;
						var columnId = objCondition.columnId == null ? "" : objCondition.columnId;
						$(currentRow).find("input[name=columnIdAutocomplete]").val(columnIdAutocomplete);
						$(currentRow).find("input[name=columnIdAutocomplete]").attr("arg02",tblDesignId);
						$(currentRow).find("input[name=columnId]").val(columnId);
						var dataType = (objCondition.dataType == undefined || objCondition.dataType == null) ? "" : objCondition.dataType;
						$(currentRow).find("input[name=columnId]").attr("dataType",dataType);
						
						var operatorCode = (objCondition.operatorCode == undefined || objCondition.operatorCode == null) ? 0 : objCondition.operatorCode;
						$(currentRow).find("select[name=operatorCode]").val(operatorCode);
						
//						var inforCurrentParam = getInformationOfParameter(objCondition.parameterId);
//						if(inforCurrentParam != undefined){
//							$(currentRow).find("input[name=parameterId]").val(inforCurrentParam.optionValue);
//							$(currentRow).find("input[name=parameterIdAutocomplete]").val(inforCurrentParam.optionLabel);
//							$(currentRow).find("input[name=parameterId]").attr("parameterScope",inforCurrentParam.output01);
//						}else{
//							$(currentRow).find("input[name=parameterId]").val("");
//							$(currentRow).find("input[name=parameterIdAutocomplete]").val("");
//						}
						bindDataToAssignAutocomplete(objCondition.parameterId,"parameter",objCondition.lstParameterIndex,arrIndexLoop,$(currentRow),modal);
						
						$(tableTmp).find("#tbl-content-conditions").find("tbody").append(currentRow);
					}
				}
				break;
			case 3:
				tableTmp = $("#tbl-bcheck-duplicated-template").tmpl();
				if(objDetail.contents != undefined && objDetail.contents.length >0){
					for(var j=0;j<objDetail.contents.length;j++){
						var objCondition = objDetail.contents[j];
						var tblDesignIdAutocomplete = objCondition.tblDesignIdAutocomplete == null ? "" : objCondition.tblDesignIdAutocomplete;
						var tblDesignId = objCondition.tblDesignId == null ? "" : objCondition.tblDesignId;
						if(j==0){
							$(tableTmp).find("input[name=tblDesignIdAutocomplete]").val(tblDesignIdAutocomplete);
							$(tableTmp).find("input[name=tblDesignId]").val(tblDesignId);
						}
						var currentRow = $("#tbl-content-conditions-template").tmpl();
						var columnIdAutocomplete = objCondition.columnIdAutocomplete == null ? "" : objCondition.columnIdAutocomplete;
						var columnId = objCondition.columnId == null ? "" : objCondition.columnId;
						$(currentRow).find("input[name=columnIdAutocomplete]").val(columnIdAutocomplete);
						$(currentRow).find("input[name=columnIdAutocomplete]").attr("arg02",tblDesignId);
						$(currentRow).find("input[name=columnId]").val(columnId);
						var dataType = (objCondition.dataType == undefined || objCondition.dataType == null) ? "" : objCondition.dataType;
						$(currentRow).find("input[name=columnId]").attr("dataType",dataType);

						var operatorCode = (objCondition.operatorCode == undefined || objCondition.operatorCode == null) ? 0 : objCondition.operatorCode;
						$(currentRow).find("select[name=operatorCode]").val(operatorCode);
						
//						var inforCurrentParam = getInformationOfParameter(objCondition.parameterId);
//						if(inforCurrentParam != undefined){
//							$(currentRow).find("input[name=parameterId]").val(inforCurrentParam.optionValue);
//							$(currentRow).find("input[name=parameterIdAutocomplete]").val(inforCurrentParam.optionLabel);
//							$(currentRow).find("input[name=parameterId]").attr("parameterScope",inforCurrentParam.output01);
//						}else{
//							$(currentRow).find("input[name=parameterId]").val("");
//							$(currentRow).find("input[name=parameterIdAutocomplete]").val("");
//						}
						bindDataToAssignAutocomplete(objCondition.parameterId,"parameter",objCondition.lstParameterIndex,arrIndexLoop,$(currentRow),modal);
						
						$(tableTmp).find("#tbl-content-conditions").find("tbody").append(currentRow);
					}
				}
				break;
			default:
				break;
			}
			$(tableTmp).attr("businessCheckType",checkType);
			
			//bind data
			var messageCode = objDetail.messageCode == null ? "" : objDetail.messageCode;
			var messageCodeAutocomplete = objDetail.messageCodeAutocomplete == null ? "" : objDetail.messageCodeAutocomplete;
			var parameters = objDetail.parameters == null ? [] : objDetail.parameters;
			var abortFlg = objDetail.abortFlg == null ? false : objDetail.abortFlg;
			var count = countParameterOfMessage(messageCodeAutocomplete);
			if(count == 0){
				$(tableTmp).find("span[name='btnChooseParameter']").hide();
			}else{
				$(tableTmp).find("span[name='btnChooseParameter']").show();
			}
			
			$(tableTmp).find("input[name=messageCode]").val(messageCode);
			$(tableTmp).find("input[name=messageCodeAutocomplete]").val(messageCodeAutocomplete);
			$(tableTmp).find("input[name=messageParameter]").val(convertToString(parameters));
			$(tableTmp).find("input[name=abortFlg]").prop("checked",abortFlg);

			var selectedValue = isEmptyQP(objDetail.existedMessageFlg) ? false : eval(objDetail.existedMessageFlg);
			$(tableTmp).find("input[name='messageCodeAutocomplete']").attr("selectedValue",selectedValue);
			
			$.qp.initialAllPicker($(tableTmp));
			$(tableTmp).find("input.qp-input-autocomplete").each(function (){
				$(this).data("ui-autocomplete")._trigger("close");
			});
			$(modal).find("#listBusinessCheck").append(tableTmp);
		}
	}
	
	$.qp.initialAllPicker($(modal));
	$(modal).find("input[name$='IdAutocomplete']").each(function(){
		$(this).data("ui-autocomplete")._trigger("close");
	});
	$("#listBusinessCheck").sortable({
		// placeholder: "ui-sortable-placeholder"
		helper: function(e, ui) {
			ui.children().each(function() {
				$(this).width($(this).width());
			});
			return ui;
		},
		update: function(event, ui) {
			alert();
		},
		items: '.qp-bdesign-div-content',
		cursor: 'move',
		handle: '.sortable',
		scroll: false
	});
	initialViewModal(modal,isOnlyView);
	$(modal).modal(
			  { 
			   show: true,
			   closable: false,
			   keyboard:false,
			   backdrop:'static'
			  }
			 );
}
function openModalMessageParameter(thiz,message,messageParameter,isOnlyView){
	var modal = "#dialog-messageparameter-setting";
	$(modal).data("target", thiz);
	var count = countParameterOfMessage(message);
	$(modal).find("#messageParameter").find("tbody").empty();
	var template = $('#messageParameter-template').html();
	
	var messageString;
	if(typeof jQuery.namespace($(thiz).attr("callback")) == "function") {
		messageString = jQuery.namespace($(thiz).attr("callback"))(thiz);
	}
	$(modal).find("label[name='messageString']").text(messageString);
	
	var arrParameters = convertToJson(messageParameter);
	for(var i =0;i<count;i++){
		var newRow = $.qp.addRowJSByLinkEx($(modal).find("#messageParameter").next().find("a"),'messageParameter','messageParameter-template');
		$(newRow).find("label[name=orderIndex]").text("{"+i+"}");
		$(newRow).find("input[name='parameterType']").attr("name","parameterType"+i);
		if(i< arrParameters.length){
			var objData = arrParameters[i];
			var parameterType = objData.parameterType;
			var typeTD = $(newRow).find("td[type='"+parameterType+"']");
			if(!isEmptyQP(parameterType) && typeTD.length > 0){
				$(newRow).find("td[type='"+parameterType+"']").show();
				$(newRow).find("input[name^='parameterType'][value='"+parameterType+"']").prop("checked",true);
				
				switch (parameterType.toString()) {
				case "0":
					var parameterCode = isEmptyQP(objData.parameterCode) ? "" :  objData.parameterCode;
					var parameterCodeAutocomplete = isEmptyQP(objData.parameterCodeAutocomplete) ? "" :  objData.parameterCodeAutocomplete;
					$(typeTD).find("input[name=parameterCode]").val(parameterCode);
					$(typeTD).find("input[name=parameterCodeAutocomplete]").val(parameterCodeAutocomplete);
					
					var selectedValue = isEmptyQP(objData.existedMessageFlg) ? false : eval(objData.existedMessageFlg);
					$(typeTD).find("input[name=parameterCodeAutocomplete]").attr("selectedValue",selectedValue);
					
					var messageLevel = isEmptyQP(objData.messageLevel) ? "" :objData.messageLevel;
					var levelName = "";
					levelName = getlevelOfMessage(messageLevel);
					
					$(newRow).find("input[name=messageLevel]").val(messageLevel);
					$(newRow).find("span[name=messageLevel]").html(levelName);
					break;
				case "1":
					var parameterValue =  isEmptyQP(objData.parameterValue) ? "" :  objData.parameterValue;
					$(typeTD).find("input[name=parameterValue]").val(parameterValue);
					break;
				case "2":
					var parameterCode = isEmptyQP(objData.parameterCode) ? "" :  objData.parameterCode;
					var parameterCodeAutocomplete = isEmptyQP(objData.parameterCodeAutocomplete) ? "" :  objData.parameterCodeAutocomplete;
					$(typeTD).find("input[name=parameterCode]").val(parameterCode);
					$(typeTD).find("input[name=parameterCodeAutocomplete]").val(parameterCodeAutocomplete);
					
					var arrIndexLoop = getLabelOfIndexLoop();
					
					if (objData.lstParameterIndex != undefined && objData.lstParameterIndex != null && objData.lstParameterIndex.length != 0) {
						bindDataToAssignAutocomplete(objData.parameterCode,"parameter",objData.lstParameterIndex,arrIndexLoop,newRow,modal);
					}
				default:
					break;
				}
			}
		}else{
			$(modal).find("#messageParameter").find("tbody").find("tr:eq("+i+")").find("td[type='0']").show();
			$(modal).find("#messageParameter").find("tbody").find("tr:eq("+i+")").find("input[name^='parameterType'][value='0']").prop("checked",true);
		}
	}
	$.qp.initialAllPicker($(modal));
	initialViewModal(modal,isOnlyView);
	if($(modal).find("input.qp-input-autocomplete").length >0){
		$(modal).find("input.qp-input-autocomplete").each(function (){
			$(this).data("ui-autocomplete")._trigger("close");
		});
	}
	$(modal).modal(
			  { 
			   show: true,
			   closable: false,
			   keyboard:false,
			   backdrop:'static'
			  }
			 );
}

//event when click button param in feedback setting
function openParameterOfFeeback(thiz){
	var message = $(thiz).closest("table").find("input[name='messageCodeAutocomplete']").val();
	var messageParameter = $(thiz).closest("table").find("input[name='messageParameter']").val();
	var isView = $(thiz).closest("table").find("input[name='messageCodeAutocomplete']").attr("disabled") == "disabled" ? true : false;
	openModalMessageParameter(thiz,message,messageParameter,isView);
}

/*
 * type = 0 : diplay input
 * type = 1 : display All
 */
function openModalAssignScreenItem(obj, isOnlyView,type){
	// HungHX
	var modal = "#dialog-assign-screenitem-setting";
	var $screenItemId = $(obj).closest("td").find("input[name*=screenItemId]");
	var $mappingType = $(obj).closest("td").find("input[name*=mappingType]");
	if(type == undefined){
		type = 1;
	}
	
	$(modal).data("target", obj);
	if(screenId != undefined && screenId != ""){
		var url = CONTEXT_PATH + "/businessdesign/getScreenInformationBD?screenId="+screenId+"&type="+type+"&r="+Math.random();
		var data = $.qp.getData(url);
		$(modal).find("#tbl-screen-items").find("tbody").empty();
		var index = 1;
		for (var i = 0; i < data.length; i++) {
			var tr = "<tr><td class=\"com-output-fixlength\">"+ index++ +"</td>";
			if(parseInt(data[i].formRowspan)>0){
				tr += "<td rowspan=\""+data[i].formRowspan +"\">";
				tr += "<label class=\"qp-output-text\"> "+data[i].formCode+" </label>";
				tr += "</td>";
			}
			if(parseInt(data[i].areaRowspan)>0) {
				tr += "<td rowspan=\""+data[i].areaRowspan+"\">"+(data[i].areaName == null ?"":data[i].areaName)+" / "+data[i].areaType+"</td>";
			}
			var itemName=data[i].itemName;
			if(data[i].logicalDataType == "0" || data[i].logicalDataType == "5" || data[i].logicalDataType == "6" || data[i].logicalDataType == "7" || (data[i].displayFromTo == "1")) {
//				if(data[i].dataSourceType != undefined && parseInt(data[i].dataSourceType) == 1) {
//					itemName += "("+getLabelOfMappingType(data[i].mappingType)+")";
//				}
//				itemName += "("+data[i].itemName+")";
				itemName += "("+getLabelOfMappingType(data[i].mappingType,data[i].logicalDataType)+")";
			}
			tr += "<td> <label class=\"qp-output-text\" name=\"itemName\">"+itemName+"</label> </td>";
			tr += "<td> <label class=\"qp-output-text\">"+data[i].logicalDataTypeMessageString+"</label> </td>";
			
//			if(data[i].dataType == undefined || data[i].dataType == null) continue;
			
			var dataType = CL_BD_DATATYPE[data[i].dataType.toString()];
			if("1" == data[i].arrayFlg){
				dataType += "[]";
			}
			tr += "<td> "+dataType+"</td>";
			
			if($screenItemId.length > 0 && $mappingType.length > 0 && $screenItemId.length == $mappingType.length){
				var isExsit = false;
				$.each($screenItemId, function(indexRow){
					var mappingType = $($mappingType.get(indexRow)).val();
					if(data[i].screenItemId == $(this).val() && data[i].mappingType == mappingType) {
						tr += "<td> <label><input class=\"checkbox qp-input-checkbox\" type=\"checkbox\" name=\"outputSelect\" value=\""+data[i].screenItemId+"\" mappingType=\""+data[i].mappingType+"\"  checked=\"true\">"
						   +  " <input type=\"hidden\" name=\"dataType\" value=\""+data[i].dataType+"\"></label> </td>";
						isExsit = true;
						return false;
					}
				});
				if(!isExsit) {
					tr += "<td> <label><input class=\"checkbox qp-input-checkbox\" type=\"checkbox\" name=\"outputSelect\" value=\""+data[i].screenItemId+"\"  mappingType=\""+data[i].mappingType+"\"  >"
					   +  " <input type=\"hidden\" name=\"dataType\" value=\""+data[i].dataType+"\"></label> </td>";
				}
			} else {
				tr += "<td> <label><input class=\"checkbox qp-input-checkbox\" type=\"checkbox\" name=\"outputSelect\" value=\""+data[i].screenItemId+"\" mappingType=\""+data[i].mappingType+"\"  >"
				   +  " <input type=\"hidden\" name=\"dataType\" value=\""+data[i].dataType+"\"></label> </td>";
			}
			
			$(modal).find("#tbl-screen-items").find("tbody").append(tr);
		}
		
		var $lstScreenIdSelected = $(obj).closest('tbody').find('input:hidden[name$=".screenItemId"]');
		var $lstCheckbox = $(modal).find('input:checkbox');
		
		$lstCheckbox.each(function() {
			if ($(this).prop("checked") == false) {
				if($(obj).closest("tbody").find("input:hidden[name$='.screenItemId'][value='"+$(this).val()+"'][mappingType='"+$($(this)).attr("mappingType")+"']").length > 0){
					$(this).attr("disabled", true);
					$(this).attr("title", "This item was selected on another item.");
				}
			}else{
				$(this).closest("td").addClass("success");
				$(this).closest("td").prevAll(":eq(0),:eq(1),:eq(2)").addClass("success");
			}
			$(this).change(function (e){
				var checkedFlg = $(this).prop("checked");
				if(checkedFlg){
					$(this).closest("td").addClass("success");
					$(this).closest("td").prevAll(":eq(0),:eq(1),:eq(2)").addClass("success");
				}else{
					$(this).closest("td").removeClass("success");
					$(this).closest("td").prevAll(":eq(0),:eq(1),:eq(2)").removeClass("success");
				}
				
			});
		});
		initialViewModal(modal,isOnlyView);
		$(modal).modal(
				  { 
				   show: true,
				   closable: false,
				   keyboard:false,
				   backdrop:'static'
				  }
				 );
	}else{
		$.qp.showAlertModal("The idenfity of screen does not exist");
	}
	
}
function initialViewModal(modal,isOnlyView){
	if(isOnlyView){
		$(modal).find('input,textarea,select').attr('disabled', true);
		$(modal).find('span.glyphicon-remove').parent().prop('onclick',null).off('click');
		$(modal).find('span.glyphicon-remove').prop('onclick',null).off('click');
		$(modal).find("div.qp-div-action").hide();
		$(modal).find("a.glyphicon-minus").hide();
		$(modal).find("a.glyphicon-plus").hide();
		$(modal).find("a.glyphicon-sort").hide();
		$(modal).find("span.glyphicon-remove-circle").hide();
		$(modal).find("a.glyphicon-cog").hide();
		$(modal).find("a.qp-button-action").hide();
	}else{
		$(modal).find("div.qp-div-action").show();
	}
}

function openModalFileOperation(obj,isOnlyView) {
	var modal = BD_MODAL_NAME.FILE_OPERATION;
	$(modal).data("instance", getInstanceOfContainer(obj));
	$(modal).data("target", obj);
	var value = $(obj).find("input[name='componentElement']").val();
	var data = convertToJson(value);
	$(modal).data("data", value);
	$(modal).find("select[name='type']").val("0");
	$("#tbl-fileoperation").find("tr[type='0']").hide();
	$("#tbl-fileoperation").find("tr[type='1']").hide();
	$("#tbl-fileoperation").find("tr[type='2']").hide();
	$("#tbl-fileoperation").find("tr[type='3']").hide();
	
	$(modal).find("input[name='label']").val("");
//	$(modal).find("input[name='overwriteFlg']").prop("checked", false);
	
	$(modal).find("#tlbMergedFile").find("tr:gt(0)").remove();
	
	$(modal).find("textarea[name='remark']").val("");
	$(modal).find("input[name='content']").val("");
	$(modal).find("input[name='pathType']").val("0");
	$(modal).find("input[name='formulaDefinitionDetails']").val("");
	$(modal).find("input[name=content]").attr("readonly",false);
	
	if (data != null) {
		var sourcePathTR = $(modal).find("tr[type="+data.type+"][name='sourcePath']");
		var destinationPathTR = $(modal).find("tr[type="+data.type+"][name='destinationPath']");
		var newFilenameTR = $(modal).find("tr[type="+data.type+"][name='newFilename']");
		
		if (data.type != undefined && data.type != null) {
			$(modal).find("select[name='type']").val(data.type);
			$("#tbl-fileoperation").find("tr[type="+data.type+"]").show();
		} else {
			$(modal).find("select[name='type']").val("0");
			$("#tbl-fileoperation").find("tr[type='0']").show();
		}
		
		if (data.label != undefined && data.label != null) {
			$(modal).find("input[name='label']").val(data.label);
		} else {
			$(modal).find("input[name='label']").val("");
		}
		
//		if (data.overwriteFlg != undefined && data.overwriteFlg != null) {
//			$(modal).find("tr[type="+data.type+"]").find("input[name='overwriteFlg']").prop("checked", data.overwriteFlg);
//		} else {
//			$(modal).find("tr[type="+data.type+"]").find("input[name='overwriteFlg']").prop("checked", false);
//		}
		
		if (data.remark != undefined && data.remark != null) {
			$(modal).find("textarea[name='remark']").val(data.remark);
		} else {
			$(modal).find("textarea[name='remark']").val("");
		}
		
		if (data.sourcePathContent != undefined && data.sourcePathContent != null) {
			sourcePathTR.find("input[name='content']").val(data.sourcePathContent);
		} else {
			sourcePathTR.find("input[name='content']").val("");
		}
		
		if (data.sourcePathType != undefined && data.sourcePathType != null) {
			if (data.sourcePathType == "1") {
				sourcePathTR.find("[name=content]").attr("readonly",true);
			}
			
			sourcePathTR.find("input[name='pathType']").val(data.sourcePathType);
		} else {
			sourcePathTR.find("input[name='pathType']").val("0");
		}

		if (data.sourcePathFormulaDetails != undefined && data.sourcePathFormulaDetails != null) {
			sourcePathTR.find("input[name='formulaDefinitionDetails']").val(convertToString(data.sourcePathFormulaDetails));
		} else {
			sourcePathTR.find("input[name='formulaDefinitionDetails']").val("");
		}

		if (data.destinationPathContent != undefined && data.destinationPathContent != null) {
			destinationPathTR.find("input[name='content']").val(data.destinationPathContent);
		} else {
			destinationPathTR.find("input[name='content']").val("");
		}
		
		if (data.destinationPathType != undefined && data.destinationPathType != null) {
			if (data.destinationPathType == "1") {
				destinationPathTR.find("[name=content]").attr("readonly",true);
			}
			
			destinationPathTR.find("input[name='pathType']").val(data.destinationPathType);
		} else {
			destinationPathTR.find("input[name='pathType']").val("");
		}

		if (data.destinationPathFormulaDetails != undefined && data.destinationPathFormulaDetails != null) {
			destinationPathTR.find("input[name='formulaDefinitionDetails']").val(convertToString(data.destinationPathFormulaDetails));
		} else {
			destinationPathTR.find("input[name='formulaDefinitionDetails']").val("");
		}
		
		if (data.newFilenameContent != undefined && data.newFilenameContent != null) {
			newFilenameTR.find("input[name='content']").val(data.newFilenameContent);
		} else {
			newFilenameTR.find("input[name='content']").val("");
		}
		
		if (data.newFilenameType != undefined && data.newFilenameType != null) {
			if (data.newFilenameType == "1") {
				newFilenameTR.find("[name=content]").attr("readonly",true);
			}

			newFilenameTR.find("input[name='pathType']").val(data.newFilenameType);
		} else {
			newFilenameTR.find("input[name='pathType']").val("");
		}

		if (data.newFilenameFormulaDetails != undefined && data.newFilenameFormulaDetails != null) {
			newFilenameTR.find("input[name='formulaDefinitionDetails']").val(convertToString(data.newFilenameFormulaDetails));
		} else {
			newFilenameTR.find("input[name='formulaDefinitionDetails']").val("");
		}
		
		if (data.lstMergeFileDetails != undefined && data.lstMergeFileDetails != null) {
			$(modal).find("#tlbMergedFile").find("tbody").empty();
			
			for (var i = 0; i < data.lstMergeFileDetails.length; i++) {
				var mergedFile = data.lstMergeFileDetails[i];
				var templateInput = $(modal).find("#tlbMergedFile-template").tmpl(mergedFile);
				
				if (mergedFile.sourcePathContent != undefined && mergedFile.sourcePathContent != null) {
					$(templateInput).find("input[name='content']").val(mergedFile.sourcePathContent);
				} else {
					$(templateInput).find("input[name='content']").val("");
				}
				
				if (mergedFile.sourcePathType != undefined && mergedFile.sourcePathType != null) {
					if (mergedFile.sourcePathType == "1") {
						$(templateInput).find("[name=content]").attr("readonly",true);
					}
					
					$(templateInput).find("input[name='pathType']").val(mergedFile.sourcePathType);
				} else {
					$(templateInput).find("input[name='pathType']").val("");
				}

				if (mergedFile.sourcePathFormulaDetails != undefined && mergedFile.sourcePathFormulaDetails != null) {
					$(templateInput).find("input[name='formulaDefinitionDetails']").val(convertToString(mergedFile.sourcePathFormulaDetails));
				} else {
					$(templateInput).find("input[name='formulaDefinitionDetails']").val("");
				}
				
				$(modal).find("#tlbMergedFile").find("tbody").append(templateInput);
			}
		}
	}
	$.qp.initialAllPicker($(modal));
	initialViewModal(modal,isOnlyView);
	$(modal).modal({ 
		   show: true,
		   closable: false,
		   keyboard:false,
		   backdrop:'static'
		  });
}
function openModalNestedLogic(obj,isOnlyView) {
	var value = $(obj).find("input[name='componentElement']").val();
	var data = convertToJson(value);
	if(data != null){
		var id = $(obj).attr("id");
		if (data.sequenceLogicId != undefined) {
			data.sequenceLogicId = id;
		} else {		
			data["sequenceLogicId"] = id;
		}
		var levelDesignLogic = $(obj).closest("#allDragDropContent").find("#srcgenElements").attr("level");
		if(levelDesignLogic != undefined && levelDesignLogic.length >0){
			levelDesignLogic = parseInt(levelDesignLogic) + 1;
		}else{
			levelDesignLogic = 2;
		}
		if (data.level != undefined) {
			data.level = levelDesignLogic.toString();
		} else {		
			data["level"] = levelDesignLogic.toString();
		}
		if(data.arrComponent != undefined){
			var arrComponentOfNestedLogic = data.arrComponent;
			for(var i=0;i<arrComponentOfNestedLogic.length;i++){
				if(arrComponentOfNestedLogic[i].sequenceLogicId == undefined || arrComponentOfNestedLogic[i].sequenceLogicId == null || arrComponentOfNestedLogic[i].sequenceLogicId == ""){
					arrComponentOfNestedLogic[i].sequenceLogicId = id + "_"+i;
				}
			}
			data.arrComponent = arrComponentOfNestedLogic;
		}
	}
	value = convertToString(data);
	$(obj).find("input[name='componentElement']").val(value);
	if(isOnlyView == undefined || isOnlyView.length ==0){
		isOnlyView = "false";
	}
	var blogicType = $(obj).closest("form").find("input[name='blogicType']").val();
	var blogicId = $(obj).closest("form").find("input[name='businessLogicId']").val();
	var blogicCode = $(obj).closest("form").find("input[name='businessLogicCode']").val();
	var moduleType = "0";
	if(isEmptyQP(blogicId)){
		moduleType = $(obj).closest("form").find("input[name='moduleType']:checked").val();
	}else{
		moduleType = $(obj).closest("form").find("input[name='moduleType']").val();
	}
	if(!moduleType){
		moduleType = "";
	}
	var url = CONTEXT_PATH  + "/businessdesign/designBlogic?isOnlyView="+isOnlyView+"&sequenceLogicId="+id+"&blogicType="+blogicType+"&moduleType="+moduleType+"&businessLogicCode="+blogicCode+"&r="+Math.random();
	$(obj).find("a").attr("href",url);
//	$(obj).find("a").click();
	$(obj).find("a").trigger( "click" );
}
function initialComponentForNestedLogic(targetBoxId,isOnlyView){
	var parentWindow = window.parent.document;
	var modal = $("body");
	var obj = $(parentWindow).find("#"+targetBoxId);
	var value = $(obj).find("input[name='componentElement']").val();
	var data = convertToJson(value);
	if(data != null){
		if(data.label != undefined && data.label != null){
			$(modal).find("input[name='label']").val(data.label);
		}else{
			$(modal).find("input[name='label']").val("");
		}
		if(data.remark != undefined && data.remark != null){
			$(modal).find("textarea[name='remark']").val(data.remark);
		}else{
			$(modal).find("textarea[name='remark']").val("");
		}
		if(data.arrConnection != undefined && data.arrConnection != null){
			for(var i=0;i<data.arrConnection.length;i++){
				var item = data.arrConnection[i];
				var instanceConnect;
				instanceConnect = {
					source : item.connectorSource,
					target : item.connectorDest,
					label : item.connectorType
				};
				// Adding array
				allInstanceConnect.push(instanceConnect);
			}
		}
		if(data.arrComponent != undefined && data.arrComponent != null){
			for(var i=0;i<data.arrComponent.length;i++){
				var item = data.arrComponent[i];
				var styleItem = assignStyleComponent(item, isOnlyView);
				var templateId = "#tbl-component-node-template";
				if(styleItem.componentType.toString() == "14"){
					templateId="#tbl-component-nestedlogicnode-template";
				}else if(styleItem.componentType.toString() == "9"){
					templateId="#tbl-component-if-node-template";
				}
				var templateComponent = $(templateId).tmpl(styleItem);
				$("#designArea").append(templateComponent);
				initConnectionTargetForComponent(styleItem.sequenceLogicId,styleItem.componentType);
			}
		}
		if(data.level != undefined && data.level != null && data.level.length >0){
			$("#srcgenElements").attr("level",data.level);
		}else{
			$("#srcgenElements").attr("level",2);
		}
	}
	//set hidden value
	$(modal).find("[name='jsonInputBean']").val($(parentWindow).find("[name='jsonInputBean']").val());
	$(modal).find("[name='jsonOutputBean']").val($(parentWindow).find("[name='jsonOutputBean']").val());
	$(modal).find("[name='jsonObjectDefinition']").val($(parentWindow).find("[name='jsonObjectDefinition']").val());
	moduleIdOfBD = parent.moduleIdOfBD;
	moduleIdAutocompleteOfBD = parent.moduleIdAutocompleteOfBD;
	initialViewModal(modal,isOnlyView);
}
//var javaEditor;
function openModalAdvance(obj,isOnlyView) {
	var modal = "#dialog-advance-setting";
	$(modal).data("instance", getInstanceOfContainer(obj));
	$(modal).data("target", obj);
	var value = $(obj).find("input[name='componentElement']").val();
	var data = convertToJson(value);
	$(modal).data("data", value);
	
	$(modal).find("input[name='label']").val("");
	$(modal).find("textarea[name='remark']").val("");
	$(modal).find("#tbl-advance-inputbean").find("tbody").empty();
	$(modal).find("#tbl-advance-outputbean").find("tbody").empty();
	

	$(modal).find("#firstLine").text("");
	$(modal).find("#lastLine").text("");
	var content;
	
	if(data != null){
		var arrIndexLoop = getLabelOfIndexLoop();
		if(data.label != undefined && data.label != null){
			$(modal).find("input[name='label']").val(data.label);
		}else{
			$(modal).find("input[name='label']").val("");
		}
		if(data.methodName != undefined && data.methodName != null){
			var countOutput = data.parameterOutputBeans.length;
			$(modal).find("input[name='methodName']").val(data.methodName);
			if (data.methodName != "") {
				var firstLine = "";
				//get blogic code
				var businessDesignCode = $("input[name='businessLogicCode'").val();
				if(countOutput == 0){
					firstLine = "private "+"void " + buildMethodNameOfAdvanceNode(businessDesignCode,data.methodName) +" ("+ capitalizeFirstLetter(data.methodName) + capitalizeFirstLetter(businessDesignCode) +"Input methodInput) {";
				}else{
					firstLine = "private "+ capitalizeFirstLetter(data.methodName) + capitalizeFirstLetter(businessDesignCode) +"Output " + buildMethodNameOfAdvanceNode(businessDesignCode,data.methodName) +" ("+ capitalizeFirstLetter(data.methodName) + capitalizeFirstLetter(businessDesignCode) +"Input methodInput) {";
				}
				$(modal).find("#firstLine").text(firstLine);
				$(modal).find("#lastLine").text("}");
			}
		}else{
			$(modal).find("input[name='methodName']").val("");
			$(modal).find("#lineOne").text("");
			$(modal).find("#lastLine").text("");
		}
		if(data.remark != undefined && data.remark != null){
			$(modal).find("textarea[name='remark']").val(data.remark);
		}else{
			$(modal).find("textarea[name='remark']").val("");
		}
		if(data.content != undefined && data.content != null){
			content = data.content;
		}else{
			content = "";
		}
		
		if(data.parameterInputBeans != undefined && data.parameterInputBeans != null){
			for(var i=0;i < data.parameterInputBeans.length;i++){
				var inputbean = data.parameterInputBeans[i];
				inputbean.index  = i+1;
				var templateInput = $(modal).find("#tbl-advance-inputbean-template").tmpl(inputbean);
//				var inforCurrentParam = new Object();
//				inforCurrentParam = getInformationOfParameter(inputbean.parameterId);
//				if(inforCurrentParam != undefined){
//					$(templateInput).find("input[name='parameterId']").val(inforCurrentParam.optionValue);
//					$(templateInput).find("input[name='parameterIdAutocomplete']").val(inforCurrentParam.optionLabel);
//					$(templateInput).find("input[name='parameterId']").attr("parameterScope",inforCurrentParam.output01);
//				}else{
//					$(templateInput).find("input[name='parameterId']").val("");
//					$(templateInput).find("input[name='parameterIdAutocomplete']").val("");
//				}
				
				if (inputbean.advanceInputValueId != undefined && inputbean.advanceInputValueId != null) {
					$(templateInput).find("input[name='advanceInputValueId']").val(inputbean.advanceInputValueId);
				} else {
					$(templateInput).find("input[name='advanceInputValueId']").val("");
				}
				
				if (inputbean.inputBeanName != undefined && inputbean.inputBeanName != null) {
					$(templateInput).find("input[name='inputBeanName']").val(inputbean.inputBeanName);
				} else {
					$(templateInput).find("input[name='inputBeanName']").val("");
				}
				
				if (inputbean.inputBeanCode != undefined && inputbean.inputBeanCode != null) {
					$(templateInput).find("input[name='inputBeanCode']").val(inputbean.inputBeanCode);
				} else {
					$(templateInput).find("input[name='inputBeanCode']").val("");
				}
				
				bindDataToAssignAutocomplete(inputbean.parameterId,"parameter",inputbean.lstParameterIndex,arrIndexLoop,$(templateInput),modal);
				$(templateInput).find("select[name='dataType']").val(inputbean.dataType);
				if(inputbean.arrayFlg != undefined &&  eval(inputbean.arrayFlg)){
					$(templateInput).find("input[name='arrayFlg']").prop("checked",true);
				}
				$(modal).find("#tbl-advance-inputbean").find("tbody").append(templateInput);
			}
		}
		if(data.parameterOutputBeans != undefined && data.parameterOutputBeans != null){
			for(var i=0;i < data.parameterOutputBeans.length;i++){
				var outputbean = data.parameterOutputBeans[i];
				outputbean.index  = i+1;
				var templateOutput = $(modal).find("#tbl-advance-outputbean-template").tmpl(outputbean);
				
				if (outputbean.advanceOutputValueId != undefined && outputbean.advanceOutputValueId != null) {
					$(templateOutput).find("input[name='advanceOutputValueId']").val(outputbean.advanceOutputValueId);
				} else {
					$(templateOutput).find("input[name='advanceOutputValueId']").val("");
				}
				
				if (outputbean.outputBeanName != undefined && outputbean.outputBeanName != null) {
					$(templateOutput).find("input[name='outputBeanName']").val(outputbean.outputBeanName);
				} else {
					$(templateOutput).find("input[name='outputBeanName']").val("");
				}
				
				if (outputbean.outputBeanCode != undefined && outputbean.outputBeanCode != null) {
					$(templateOutput).find("input[name='outputBeanCode']").val(outputbean.outputBeanCode);
				} else {
					$(templateOutput).find("input[name='outputBeanCode']").val("");
				}
				
//				var inforCurrentParam = new Object();
//				inforCurrentParam = getInformationOfParameter(outputbean.targetId);
//				if(inforCurrentParam != undefined){
//					$(templateOutput).find("input[name='targetId']").val(inforCurrentParam.optionValue);
//					$(templateOutput).find("input[name='targetIdAutocomplete']").val(inforCurrentParam.optionLabel);
//					$(templateOutput).find("input[name='targetId']").attr("parameterScope",inforCurrentParam.output01);
//				}else{
//					$(templateOutput).find("input[name='targetId']").val("");
//					$(templateOutput).find("input[name='targetIdAutocomplete']").val("");
//				}
				bindDataToAssignAutocomplete(outputbean.targetId,"target",outputbean.lstTargetIndex,arrIndexLoop,$(templateOutput),modal);
				
				$(templateOutput).find("select[name='dataType']").val(outputbean.dataType);
				if(outputbean.arrayFlg != undefined && eval(outputbean.arrayFlg)){
					$(templateOutput).find("input[name='arrayFlg']").prop("checked",true);
				}
				$(modal).find("#tbl-advance-outputbean").find("tbody").append(templateOutput);
			}
		}
	}
	$.qp.initialAllPicker($(modal));
	initialViewModal(modal,isOnlyView);
	$(modal).find("input[name$='IdAutocomplete']").each(function(){
		$(this).data("ui-autocomplete")._trigger("close");
	});
	javaEditor.setValue(content);
	$(modal).on('shown.bs.modal', function() {
		javaEditor.refresh();
	});
	$(modal).modal({
		show : true,
		closable : false,
		keyboard : false,
		backdrop : 'static'
	});
}

function openModalReadFile(obj,isOnlyView) {
	var modal = BD_MODAL_NAME.READ_FILE;
	$(modal).data("instance", getInstanceOfContainer(obj));
	$(modal).data("target", obj);
	var value = $(obj).find("input[name='componentElement']").val();
	var data = convertToJson(value);
	$(modal).data("data", value);
	
	$(modal).find("input[name='isOnlyView']").val(isOnlyView);
	
	$(modal).find("input[name='label']").val("");
	$(modal).find("textarea[name='remark']").val("");
	$(modal).find("input[name='fileFormat']").val("");
	
	$(modal).find("input[name='content']").val("");
	$(modal).find("input[name='pathType']").val("0");
	$(modal).find("input[name='formulaDefinitionDetails']").val("");
	$(modal).find("input[name=content]").attr("readonly",false);
	
	$(modal).find("#tbl-readfile-assign-parameter").find("tr:gt(1)").remove();
	$(modal).find("input[name='targetId']").val("");
	$(modal).find("input[name='targetId']").attr("parameterScope","");
	$(modal).find("input[name='targetIdAutocomplete']").val("");
	
	if (data != null) {
		var sourcePathTR = $(modal).find("tr[name='sourcePath']");
		
		if (data.label != undefined && data.label != null) {
			$(modal).find("input[name='label']").val(data.label);
		} else {
			$(modal).find("input[name='label']").val("");
		}
		
		if (data.sourcePathContent != undefined && data.sourcePathContent != null) {
			sourcePathTR.find("input[name='content']").val(data.sourcePathContent);
		} else {
			sourcePathTR.find("input[name='content']").val("");
		}
		
		if (data.sourcePathType != undefined && data.sourcePathType != null) {
			if (data.sourcePathType == "1") {
				sourcePathTR.find("[name=content]").attr("readonly",true);
			}
			
			sourcePathTR.find("input[name='pathType']").val(data.sourcePathType);
		} else {
			sourcePathTR.find("input[name='pathType']").val("0");
		}

		if (data.sourcePathFormulaDetails != undefined && data.sourcePathFormulaDetails != null) {
			sourcePathTR.find("input[name='formulaDefinitionDetails']").val(convertToString(data.sourcePathFormulaDetails));
		} else {
			sourcePathTR.find("input[name='formulaDefinitionDetails']").val("");
		}
		
		if (data.remark != undefined && data.remark != null) {
			$(modal).find("textarea[name='remark']").val(data.remark);
		} else {
			$(modal).find("textarea[name='remark']").val("");
		}
		
		if (data.targetId != undefined && data.targetId != null) {
			$(modal).find("input[name='targetId']").val(data.targetId);
			
			var inforCurrentParam = getInformationOfParameter(data.targetId);
			
			if(inforCurrentParam != undefined){
				$(modal).find("input[name='targetIdAutocomplete']").val(inforCurrentParam.optionLabel);
			}
		} else {
			$(modal).find("input[name='targetId']").val("");
			$(modal).find("input[name='targetIdAutocomplete']").val("");
		}
		
		if (data.targetScope != undefined && data.targetScope != null) {
			$(modal).find("input[name='targetId']").attr("parameterScope", data.targetScope);
		} else {
			$(modal).find("input[name='targetId']").attr("parameterScope","");
		}
		
		if (data.fileFormat != undefined && data.fileFormat != null) {
			$(modal).find("input[name='fileFormat']").val(convertToString(data.fileFormat));
		} else {
			$(modal).find("input[name='fileFormat']").val("");
		}
		
		if(data.lstImportAssignValues != undefined && data.lstImportAssignValues != null){
			var arrData = data.lstImportAssignValues;
			
			for(var i=0;i<arrData.length;i++){
				var objAssign = arrData[i];
				var newRow = $.qp.ar.addRow({link:this,tableId:'tbl-readfile-assign-parameter',templateId:'tbl-readfile-assign-parameter-template',templateData:{groupId:1}});
				var inforCurrentParam = getInformationOfParameter(objAssign.targetId);
				
				if(inforCurrentParam != undefined){
					$(newRow).find("input[name='targetId']").val(inforCurrentParam.optionValue);
					$(newRow).find("label[name='targetIdAutocomplete']").text(inforCurrentParam.optionLabel); 
					$(newRow).find("input[name='targetId']").attr("parameterScope",inforCurrentParam.output01);
					
					var dataType = (inforCurrentParam.output03 == null || inforCurrentParam.output03 == undefined) ? "" : inforCurrentParam.output03;
					$(newRow).find("input[name='dataType']").val(dataType);
				}else{
					$(newRow).find("input[name='targetId']").val("");
					$(newRow).find("label[name='targetIdAutocomplete']").text("");
				}

				if (objAssign.columnNo != undefined && objAssign.columnNo != null) {
					$(newRow).find("input[name='columnNo']").val(objAssign.columnNo);
					$(newRow).find("input[name='columnNo']").attr("value",objAssign.columnNo);
				} else {
					$(newRow).find("input[name='columnNo']").val("");
				} 				
			}
		}
	}
	$(modal).find("input[name='label']").attr("value","123");
	$.qp.initialAllPicker($(modal));
	initialViewModal(modal,isOnlyView);
	$(modal).find("input[name$='IdAutocomplete']").each(function(){
		$(this).data("ui-autocomplete")._trigger("close");
	});
	$(modal).modal({ 
		   show: true,
		   closable: false,
		   keyboard:false,
		   backdrop:'static'
		  });
	
}

function resetFileFormatReadFile(thiz){
	var modal = BD_MODAL_NAME.READ_FILE_FORMAT;

	$(modal).find("select[name='fileEncoding']").val("0");
	$(modal).find("input[name='lineFeedCharType']").eq(0).prop("checked", true);
	$(modal).find("input[name='lineFeedChar']").val("");
	$(modal).find("input[name='encloseCharType']").eq(0).prop("checked", true)
	$(modal).find("input[name='encloseChar']").val("");
	$(modal).find("input[name='headLineCount']").val("0");
	$(modal).find("input[name='trailerLineCount']").val("0");
	$(modal).find("input[name='lineFeedChar']").attr("readonly",true);
	$(modal).find("input[name='encloseChar']").attr("readonly",true);
}

//event when click button param in file read  - file format row
function openReadFileFormat(thiz){
	var modal = BD_MODAL_NAME.READ_FILE_FORMAT;
	$(modal).data("target", thiz);
	var isOnlyView = $(thiz).closest("td").find("input[name='isOnlyView']").val();
	var fileFormat = $(thiz).closest("td").find("input[name='fileFormat']").val();
	var data = convertToJson(fileFormat);
	$(modal).data("data", fileFormat);
	
	var modalParent = BD_MODAL_NAME.READ_FILE;
	var value = $(thiz).closest(".modal").find("input[name='componentElement']").val();
	$(modalParent).data("data", value);

	$(modal).find("select[name='fileEncoding']").val("0");
	$(modal).find("input[name='lineFeedCharType']").eq(0).prop("checked", true);
	$(modal).find("input[name='lineFeedChar']").val("");
	$(modal).find("input[name='encloseCharType']").eq(0).prop("checked", true)
	$(modal).find("input[name='encloseChar']").val("");
	$(modal).find("input[name='headLineCount']").val("0");
	$(modal).find("input[name='trailerLineCount']").val("0");
	$(modal).find("input[name='overwriteFlg']").prop("checked", false);
	$(modal).find("input[name='lineFeedChar']").attr("readonly",true);
	$(modal).find("input[name='encloseChar']").attr("readonly",true);
	
	if (data != null) {
		if (data.fileEncoding != undefined && data.fileEncoding != null) {
			$(modal).find("select[name='fileEncoding']").val(data.fileEncoding);
		} else {
			$(modal).find("select[name='fileEncoding']").val("0");
		}
		
		if (data.lineFeedCharType != undefined && data.lineFeedCharType != null) {
			var type = data.lineFeedCharType;
			$(modal).find("input[name='lineFeedCharType'][value=" + type + "]").prop('checked', true);
			
			if (type == -1) {
				if (data.lineFeedChar != undefined && data.lineFeedChar != null) {
					var value = data.lineFeedChar;

					$(modal).find("input[name='lineFeedChar']").val(value);
					$(modal).find("input[name='lineFeedChar']").attr("readonly",false);
				} else {
					$(modal).find("input[name='lineFeedChar']").val("");
				}
			}
		} else {
			$(modal).find("input[name='lineFeedCharType'][value='0']").prop('checked', true);
		}
		
//		if (data.delimiter != undefined && data.delimiter != null) {
//			$(modal).find("input[name='delimiter']").val(data.delimiter);
//		} else {
//			$(modal).find("input[name='delimiter']").val("");
//		}
		
		if (data.encloseCharType != undefined && data.encloseCharType != null) {
			var type = data.encloseCharType;
			$(modal).find("input[name='encloseCharType'][value=" + type + "]").prop('checked', true);
			
			if (type == -1) {
				if (data.encloseChar != undefined && data.encloseChar != null) {
					var value = data.encloseChar;

					$(modal).find("input[name='encloseChar']").val(value);
					$(modal).find("input[name='encloseChar']").attr("readonly",false);
				} else {
					$(modal).find("input[name='encloseChar']").val("");
				}
			}
		} else {
			$(modal).find("input[name='encloseChar'][value='0']").prop('checked', true);
		}
		
		if (data.headLineCount != undefined && data.headLineCount != null) {
			$(modal).find("input[name='headLineCount']").val(data.headLineCount);
		} else {
			$(modal).find("input[name='headLineCount']").val("0");
		}
		
		if (data.trailerLineCount != undefined && data.trailerLineCount != null) {
			$(modal).find("input[name='trailerLineCount']").val(data.trailerLineCount);
		} else {
			$(modal).find("input[name='trailerLineCount']").val("0");
		}
	}

	$.qp.initialAllPicker($(modal));
	initialViewModal(modal,isOnlyView);
	$(modal).modal(
			  { 
			   show: true,
			   closable: false,
			   keyboard:false,
			   backdrop:'static'
			  }
			 );
}

//event when click radio lineFeedChar read file formart component
function onChangeLineFeedChar(thiz){
	if ($(thiz).val() == -1) {
		$(thiz).closest("td").find("input[name='lineFeedChar'][type='text']").attr("readonly",false)
	} else {
		$(thiz).closest("td").find("input[name='lineFeedChar'][type='text']").attr("readonly",true)
	}
}

//event when click radio encloseChar read file formart component
function onChangeEncloseChar(thiz){
	if ($(thiz).val() == -1) {
		$(thiz).closest("td").find("input[name='encloseChar'][type='text']").attr("readonly",false)
	} else {
		$(thiz).closest("td").find("input[name='encloseChar'][type='text']").attr("readonly",true)
	}
}

function openModalExportFile(obj,isOnlyView) {
	var modal = BD_MODAL_NAME.EXPORT_FILE;
	$(modal).data("instance", getInstanceOfContainer(obj));
	$(modal).data("target", obj);
	var value = $(obj).find("input[name='componentElement']").val();
	var data = convertToJson(value);
	$(modal).data("data", value);
	
	$(modal).find("input[name='isOnlyView']").val(isOnlyView);
	
	$(modal).find("input[name='label']").val("");
	$(modal).find("textarea[name='remark']").val("");
	$(modal).find("input[name='fileFormat']").val("");
	
	$(modal).find("input[name='content']").val("");
	$(modal).find("input[name='pathType']").val("0");
	$(modal).find("input[name='formulaDefinitionDetails']").val("");
	$(modal).find("input[name=content]").attr("readonly",false);
	
	$(modal).find("#tbl-exportfile-assign-parameter").find("tr:gt(1)").remove();
	$(modal).find("#tbl-exportfile-assign-parameter").find(".btn-getobject a").css('display', 'none');
	$(modal).find("input[name='parameterId']").val("");
	$(modal).find("input[name='parameterId']").attr("parameterScope","");
	$(modal).find("input[name='parameterIdAutocomplete']").val("");
	
	if (data != null) {
		
		if (data.label != undefined && data.label != null) {
			$(modal).find("input[name='label']").val(data.label);
		} else {
			$(modal).find("input[name='label']").val("");
		}

		var destinationPathTR = $(modal).find("tr[name='destinationPath']");
		if (data.destinationPathContent != undefined && data.destinationPathContent != null) {
			destinationPathTR.find("input[name='content']").val(data.destinationPathContent);
		} else {
			destinationPathTR.find("input[name='content']").val("");
		}
		
		if (data.destinationPathType != undefined && data.destinationPathType != null) {
			if (data.destinationPathType == "1") {
				destinationPathTR.find("[name=content]").attr("readonly",true);
			}
			
			destinationPathTR.find("input[name='pathType']").val(data.destinationPathType);
		} else {
			destinationPathTR.find("input[name='pathType']").val("0");
		}

		if (data.destinationPathFormulaDetails != undefined && data.destinationPathFormulaDetails != null) {
			destinationPathTR.find("input[name='formulaDefinitionDetails']").val(convertToString(data.destinationPathFormulaDetails));
		} else {
			destinationPathTR.find("input[name='formulaDefinitionDetails']").val("");
		}
		
		if (data.remark != undefined && data.remark != null) {
			$(modal).find("textarea[name='remark']").val(data.remark);
		} else {
			$(modal).find("textarea[name='remark']").val("");
		}
		
		if (data.parameterId != undefined && data.parameterId != null) {
			$(modal).find("input[name='parameterId']").val(data.parameterId);
			
			var inforCurrentParam = getInformationOfParameter(data.parameterId);
			
			if(inforCurrentParam != undefined){
				$(modal).find("input[name='parameterIdAutocomplete']").val(inforCurrentParam.optionLabel);
			}
		} else {
			$(modal).find("input[name='parameterId']").val("");
			$(modal).find("input[name='parameterIdAutocomplete']").val("");
		}
		
		if (data.parameterScope != undefined && data.parameterScope != null) {
			$(modal).find("input[name='parameterId']").attr("parameterScope", data.parameterScope);
		} else {
			$(modal).find("input[name='parameterId']").attr("parameterScope","");
		}
		
		if (data.fileFormat != undefined && data.fileFormat != null) {
			$(modal).find("input[name='fileFormat']").val(convertToString(data.fileFormat));
		} else {
			$(modal).find("input[name='fileFormat']").val("");
		}
		
		if(data.lstExportAssignValues != undefined && data.lstExportAssignValues != null){
			var arrData = data.lstExportAssignValues;
			
			for(var i=0;i<arrData.length;i++){
				var objAssign = arrData[i];
				var newRow = $.qp.ar.addRow({link:this,tableId:'tbl-exportfile-assign-parameter',templateId:'tbl-exportfile-assign-parameter-template',templateData:{groupId:1}});
				var inforCurrentParam = getInformationOfParameter(objAssign.parameterId);
				
				if(inforCurrentParam != undefined){
					$(newRow).find("input[name='parameterId']").val(inforCurrentParam.optionValue);
					$(newRow).find("label[name='parameterIdAutocomplete']").text(inforCurrentParam.optionLabel); 
					$(newRow).find("input[name='parameterId']").attr("parameterScope",inforCurrentParam.output01);
					
					var dataType = (inforCurrentParam.output03 == null || inforCurrentParam.output03 == undefined) ? "" : inforCurrentParam.output03;
					$(newRow).find("input[name='dataType']").val(dataType);
				}else{
					$(newRow).find("input[name='parameterId']").val("");
					$(newRow).find("label[name='parameterIdAutocomplete']").text("");
				}

				if (objAssign.columnNo != undefined && objAssign.columnNo != null) {
					$(newRow).find("input[name='columnNo']").val(objAssign.columnNo);
					$(newRow).find("input[name='columnNo']").attr("value",objAssign.columnNo);
				} else {
					$(newRow).find("input[name='columnNo']").val("");
				} 

				if (objAssign.columnFileFormat != undefined && objAssign.columnFileFormat != null) {
					$(newRow).find("input[name='columnFileFormat']").attr("value",convertToString(objAssign.columnFileFormat));
				} else {
					$(newRow).find("input[name='columnFileFormat']").val("");
				} 
			}
		}
	}
	$.qp.initialAllPicker($(modal));
	initialViewModal(modal,isOnlyView);
	$(modal).find("input[name$='IdAutocomplete']").each(function(){
		$(this).data("ui-autocomplete")._trigger("close");
	});
	$(modal).modal({ 
		   show: true,
		   closable: false,
		   keyboard:false,
		   backdrop:'static'
		  });
	
}

function resetFileFormatExportFile(thiz){
	var modal = BD_MODAL_NAME.EXPORT_FILE_FORMAT;

	$(modal).find("select[name='fileEncoding']").val("0");
	$(modal).find("input[name='lineFeedCharType']").eq(0).prop("checked", true);
	$(modal).find("input[name='lineFeedChar']").val("");
	$(modal).find("input[name='encloseCharType']").eq(0).prop("checked", true)
	$(modal).find("input[name='encloseChar']").val("");
	$(modal).find("input[name='lineFeedChar']").attr("readonly",true);
	$(modal).find("input[name='encloseChar']").attr("readonly",true);
	$(modal).find("input[name='overwriteFlg']").prop("checked", false);
}

//event when click button param in file export  - file format row
function openExportFileFormat(thiz){
	var modal = BD_MODAL_NAME.EXPORT_FILE_FORMAT;
	$(modal).data("target", thiz);
	var isOnlyView = $(thiz).closest("td").find("input[name='isOnlyView']").val();
	var fileFormat = $(thiz).closest("td").find("input[name='fileFormat']").val();
	var data = convertToJson(fileFormat);
	$(modal).data("data", fileFormat);
	
	var modalParent = BD_MODAL_NAME.EXPORT_FILE;
	var value = $(thiz).closest(".modal").find("input[name='componentElement']").val();
	$(modalParent).data("data", value);

	$(modal).find("select[name='fileEncoding']").val("0");
	$(modal).find("input[name='lineFeedCharType']").eq(0).prop("checked", true);
	$(modal).find("input[name='lineFeedChar']").val("");
	$(modal).find("input[name='encloseCharType']").eq(0).prop("checked", true)
	$(modal).find("input[name='encloseChar']").val("");
	$(modal).find("input[name='overwriteFlg']").prop("checked", false);
	$(modal).find("input[name='lineFeedChar']").attr("readonly",true);
	$(modal).find("input[name='encloseChar']").attr("readonly",true);
	
	if (data != null) {
		if (data.fileEncoding != undefined && data.fileEncoding != null) {
			$(modal).find("select[name='fileEncoding']").val(data.fileEncoding);
		} else {
			$(modal).find("select[name='fileEncoding']").val("0");
		}
		
		if (data.lineFeedCharType != undefined && data.lineFeedCharType != null) {
			var type = data.lineFeedCharType;
			$(modal).find("input[name='lineFeedCharType'][value=" + type + "]").prop('checked', true);
			
			if (type == -1) {
				if (data.lineFeedChar != undefined && data.lineFeedChar != null) {
					var value = data.lineFeedChar;

					$(modal).find("input[name='lineFeedChar']").val(value);
					$(modal).find("input[name='lineFeedChar']").attr("readonly",false);
				} else {
					$(modal).find("input[name='lineFeedChar']").val("");
				}
			}
		} else {
			$(modal).find("input[name='lineFeedCharType'][value='0']").prop('checked', true);
		}
		
		if (data.encloseCharType != undefined && data.encloseCharType != null) {
			var type = data.encloseCharType;
			$(modal).find("input[name='encloseCharType'][value=" + type + "]").prop('checked', true);
			
			if (type == -1) {
				if (data.encloseChar != undefined && data.encloseChar != null) {
					var value = data.encloseChar;

					$(modal).find("input[name='encloseChar']").val(value);
					$(modal).find("input[name='encloseChar']").attr("readonly",false);
				} else {
					$(modal).find("input[name='encloseChar']").val("");
				}
			}
		} else {
			$(modal).find("input[name='encloseChar'][value='0']").prop('checked', true);
		}

		if (data.overwriteFlg != undefined && data.overwriteFlg != null) {
			$(modal).find("input[name='overwriteFlg']").prop("checked", data.overwriteFlg);
		} else {
			$(modal).find("input[name='overwriteFlg']").prop("checked", false);
		}
	}

	$.qp.initialAllPicker($(modal));
	initialViewModal(modal,isOnlyView);
	$(modal).modal(
			  { 
			   show: true,
			   closable: false,
			   keyboard:false,
			   backdrop:'static'
			  }
			 );
}

//event when click button param in file export format
function openExportFileColumnFormat(thiz){
	var modal = BD_MODAL_NAME.EXPORT_FILE_COLUMN_FORMAT;
	$(modal).data("target", thiz);
	var isOnlyView = $(thiz).closest("td").find("input[name='isOnlyView']").val();
	var columnFileFormat = $(thiz).closest("td").find("input[name='columnFileFormat']").val();
	var data = convertToJson(columnFileFormat);
	$(modal).data("data", columnFileFormat);
	
	var modalParent = BD_MODAL_NAME.EXPORT_FILE;
	var value = $(thiz).closest(".modal").find("input[name='componentElement']").val();
	$(modalParent).data("data", value);

	$(modal).find("input[name='paddingType']").eq(0).prop("checked", true);
	$(modal).find("input[name='paddingCharType']").eq(0).prop("checked", true);
	$(modal).find("input[name='paddingChar']").val("");
	$(modal).find("input[name='paddingChar']").attr("readonly",true);
	$(modal).find("input[name='specifyByte']").val("");
	$(modal).find("select[name='columnFormat']").val("0");
	$(modal).find("input[name='trimType']").eq(0).prop("trimType", true);
	$(modal).find("input[name='trimChar']").val("");
	$(modal).find("input[name='converter']").eq(0).prop("checked", true);
	
	if (data != null) {
		if (data.paddingType != undefined && data.paddingType != null) {
			var value = data.paddingType;
			$(modal).find("input[name='lineFeedChar'][value=" + value + "]").prop('checked', true);
		} else {
			$(modal).find("input[name='lineFeedChar'][value='0']").prop('checked', true);
		}
		
		if (data.paddingCharType != undefined && data.paddingCharType != null) {
			var type = data.paddingCharType;
			$(modal).find("input[name='paddingCharType'][value=" + type + "]").prop('checked', true);
			
			if (type == -1) {
				if (data.paddingChar != undefined && data.paddingChar != null) {
					var value = data.paddingChar;

					$(modal).find("input[name='paddingChar']").val(value);
					$(modal).find("input[name='paddingChar']").attr("readonly",false);
				} else {
					$(modal).find("input[name='paddingChar']").val("");
				}
			}
		} else {
			$(modal).find("input[name='paddingChar'][value='0']").prop('checked', true);
		}
		
		if (data.specifyByte != undefined && data.specifyByte != null) {
			$(modal).find("input[name='specifyByte']").val(data.specifyByte);
		} else {
			$(modal).find("input[name='specifyByte']").val("");
		}
		
		if (data.columnFormat != undefined && data.columnFormat != null) {
			$(modal).find("select[name='columnFormat']").val(data.columnFormat);
		} else {
			$(modal).find("select[name='columnFormat']").val("0");
		}
		
		if (data.trimType != undefined && data.trimType != null) {
			var value = data.trimType;
			$(modal).find("input[name='trimType'][value=" + value + "]").prop('checked', true);
		} else {
			$(modal).find("input[name='trimType'][value='0']").prop('checked', true);
		}
		
		if (data.trimChar != undefined && data.trimChar != null) {
			$(modal).find("input[name='trimChar']").val(data.trimChar);
		} else {
			$(modal).find("input[name='trimChar']").val("");
		}

		if (data.converter != undefined && data.converter != null) {
			var value = data.converter;
			$(modal).find("input[name='converter'][value=" + value + "]").prop('checked', true);
		} else {
			$(modal).find("input[name='converter'][value='0']").prop('checked', true);
		}
	}

	$.qp.initialAllPicker($(modal));
	initialViewModal(modal,isOnlyView);
	$(modal).modal(
			  { 
			   show: true,
			   closable: false,
			   keyboard:false,
			   backdrop:'static'
			  }
			 );
}

//event when click radio Padding char export file formart component
function onChangePaddingChar(thiz){
	if ($(thiz).val() == -1) {
		$(thiz).closest("td").find("input[name='paddingChar'][type='text']").attr("readonly",false)
	} else {
		$(thiz).closest("td").find("input[name='paddingChar'][type='text']").attr("readonly",true)
	}
}

function openModalTransaction(obj,isOnlyView) {
	var modal = BD_MODAL_NAME.TRANSACTION;
	$(modal).data("instance", getInstanceOfContainer(obj));
	$(modal).data("target", obj);
	var value = $(obj).find("input[name='componentElement']").val();
	var data = convertToJson(value);
	$(modal).data("data", value);
	
	$(modal).find("input[name='label']").val("");
	$(modal).find("input[name='transactionType']:first").attr('checked', true);
	$(modal).find("textarea[name='remark']").val("");
	
	if (data != null) {
		if (data.label != undefined && data.label != null) {
			$(modal).find("input[name='label']").val(data.label);
		} else {
			$(modal).find("input[name='label']").val("");
		}
		
		if (data.transactionType != undefined && data.transactionType != null) {
			$(modal).find("input[name='transactionType'][value=" + data.transactionType + "]").prop('checked', true);
		} else {
			$(modal).find("input[name='transactionType']:first").prop('checked', true);
		}

		if (data.remark != undefined && data.remark != null) {
			$(modal).find("textarea[name='remark']").val(data.remark);
		} else {
			$(modal).find("textarea[name='remark']").val("");
		}
	}
	$.qp.initialAllPicker($(modal));
	initialViewModal(modal,isOnlyView);
	$(modal).modal({ 
		   show: true,
		   closable: false,
		   keyboard:false,
		   backdrop:'static'
		  });
}

function openModalLog(obj,isOnlyView) {
	var modal = BD_MODAL_NAME.LOG;
	$(modal).data("instance", getInstanceOfContainer(obj));
	$(modal).data("target", obj);
	var value = $(obj).find("input[name='componentElement']").val();
	var data = convertToJson(value);
	$(modal).data("data", value);
	
	$(modal).find("input[name='isOnlyView']").val(isOnlyView);
	
	$(modal).find("input[name='label']").val("");
	$(modal).find("select[name='level']").val("0");
	
	$(modal).find("input[name='content']").val("");
	$(modal).find("input[name='pathType']").val("0");
	$(modal).find("input[name='formulaDefinitionDetails']").val("");
	$(modal).find("input[name=content]").attr("readonly",false);
	
	$(modal).find("textarea[name='remark']").val("");
	
	if(data != null){
		if(data.label != undefined && data.label != null){
			$(modal).find("input[name='label']").val(data.label);
		}else{
			$(modal).find("input[name='label']").val("");
		}

		if (data.level != undefined && data.level != null) {
			$(modal).find("select[name='level']").val(data.level);
		} else {
			$(modal).find("select[name='level']").val("0");
		}
		
		if (data.messageContent != undefined && data.messageContent != null) {
			$(modal).find("input[name='content']").val(data.messageContent);
		} else {
			$(modal).find("input[name='content']").val("");
		}
		
		if (data.messageType != undefined && data.messageType != null) {
			if (data.messageType == "1") {
				$(modal).find("[name=content]").attr("readonly",true);
			}
			
			$(modal).find("input[name='messageType']").val(data.messageType);
		} else {
			$(modal).find("input[name='messageType']").val("0");
		}

		if (data.messageFormulaDetails != undefined && data.messageFormulaDetails != null) {
			$(modal).find("input[name='formulaDefinitionDetails']").val(convertToString(data.messageFormulaDetails));
		} else {
			$(modal).find("input[name='formulaDefinitionDetails']").val("");
		}
		
		if(data.remark != undefined && data.remark != null){
			$(modal).find("textarea[name='remark']").val(data.remark);
		}else{
			$(modal).find("textarea[name='remark']").val("");
		}
		if(data.content != undefined && data.content != null){
			content = data.content;
		}else{
			content = "";
		}
		
	}
	$.qp.initialAllPicker($(modal));
	initialViewModal(modal,isOnlyView);
	
	$(modal).modal({
		show : true,
		closable : false,
		keyboard : false,
		backdrop : 'static'
	});
}

function openModalUtility(obj,isOnlyView) {
	var modal = BD_MODAL_NAME.UTILITY;
	$(modal).data("instance", getInstanceOfContainer(obj));
	$(modal).data("target", obj);
	var value = $(obj).find("input[name='componentElement']").val();
	var data = convertToJson(value);
	$(modal).data("data", value);
	$(modal).find("input[name='isOnlyView']").val(isOnlyView);
	
	$(modal).find("input[name='label']").val("");
	$(modal).find("textarea[name='remark']").val("");
	
	var templateInput = $(modal).find("#div-parameter-template").tmpl();
	
	$(modal).find("input[name='targetId']").val("");
	$(modal).find("input[name='targetId']").attr("parameterScope","");
	$(modal).find("input[name='targetIdAutocomplete']").val("");

	$(modal).find("tr[name='trContent']").replaceWith(templateInput);
	$(modal).find("select[name='type']").val("0");
	$("#tbl-utility").find("[type='0']").hide();
	$("#tbl-utility").find("[type='1']").hide();
	
	var arrIndexLoop = getLabelOfIndexLoop();
	
	if(data != null){
		if(data.label != undefined && data.label != null){
			$(modal).find("input[name='label']").val(data.label);
		}else{
			$(modal).find("input[name='label']").val("");
		}
		
		if (data.type != undefined && data.type != null) {
			$(modal).find("select[name='type']").val(data.type);
			$("#tbl-utility").find("[type="+data.type+"]").show();
			
			switch (data.type+"") {
				case "0":
				case "2":
				case "6":
				case "7":
					$("#tbl-utility").find("[type='0']").show();
					$("#tbl-utility").find("[type='1']").hide();
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
					$("#tbl-utility").find("[type='0']").hide();
					$("#tbl-utility").find("td[type='1']").attr("colspan",3);
					break;
				default:
					$("#tbl-utility").find("[type='0']").hide();
					$("#tbl-utility").find("[type='1']").hide();
			}
		} else {
			$(modal).find("select[name='type']").val("0");
			$("#tbl-utility").find("[type='0']").show();
			$("#tbl-utility").find("td[type='0']").attr("colspan",3);
		}
			
		if(data.remark != undefined && data.remark != null){
			$(modal).find("textarea[name='remark']").val(data.remark);
		}else{
			$(modal).find("textarea[name='remark']").val("");
		}
		if(data.content != undefined && data.content != null){
			content = data.content;
		}else{
			content = "";
		}
		
		if (data.targetId != undefined && data.targetId != null) {
			$(modal).find("input[name='targetId']").val(data.targetId);
			
			var inforCurrentParam = getInformationOfParameter(data.targetId);
			
			if(inforCurrentParam != undefined){
				$(modal).find("input[name='targetIdAutocomplete']").val(inforCurrentParam.optionLabel);
			}
		} else {
			$(modal).find("input[name='targetId']").val("");
			$(modal).find("input[name='targetIdAutocomplete']").val("");
		}
		
		if (data.targetScope != undefined && data.targetScope != null) {
			$(modal).find("input[name='targetId']").attr("parameterScope", data.targetScope);
		} else {
			$(modal).find("input[name='targetId']").attr("parameterScope","");
		}
		
		if (data.parameterId != undefined && data.parameterId != null) {
			$(modal).find("input[name='parameterId']").val(data.parameterId);
			
			var inforCurrentParam = getInformationOfParameter(data.parameterId);
			
			if(inforCurrentParam != undefined){
				$(modal).find("input[name='parameterIdAutocomplete']").val(inforCurrentParam.optionLabel);
			}
		} else {
			$(modal).find("input[name='parameterId']").val("");
			$(modal).find("input[name='tparameterIdAutocomplete']").val("");
		}
		
		if (data.parameterScope != undefined && data.parameterScope != null) {
			$(modal).find("input[name='parameterId']").attr("parameterScope", data.parameterScope);
		} else {
			$(modal).find("input[name='parameterId']").attr("parameterScope","");
		}
		
		if (data.indexId != undefined && data.indexId != null) {
			$(modal).find("input[name='indexId']").val(data.indexId);
			
			var inforCurrentParam = getInformationOfParameter(data.indexId);
			
			if(inforCurrentParam != undefined){
				$(modal).find("input[name='indexIdAutocomplete']").val(inforCurrentParam.optionLabel);
			}
		} else {
			$(modal).find("input[name='indexId']").val("");
			$(modal).find("input[name='indexIdAutocomplete']").val("");
		}
		
		if (data.indexIdAutocomplete != undefined && data.indexIdAutocomplete != null) {
			$(modal).find("input[name='indexIdAutocomplete']").val(data.indexIdAutocomplete);
		} else {
			$(modal).find("input[name='indexIdAutocomplete']").val("");
		}
		
		if (data.indexScope != undefined && data.indexScope != null) {
			$(modal).find("input[name='indexId']").attr("parameterScope", data.indexScope);
			
			switch (data.indexScope.toString()) {
			case "3":
				$(modal).find("input[name='indexIdAutocomplete']").val(data.indexId);
				break;
			case "4":
				var actualIndex = data.indexId.substring(1, data.indexId.length);
				var labelIndex = arrIndexLoop[actualIndex];
				if(labelIndex != undefined){
					$(modal).find("input[name='indexIdAutocomplete']").val(labelIndex);
				}else{
					$(modal).find("input[name='indexIdAutocomplete']").val("");
				}
				break;
			case "0":
			case "1":
			case "2":
				var inforIndex = getInformationOfParameter(data.indexId);
				if(inforIndex != undefined){
					$(modal).find("input[name='indexIdAutocomplete']").val(inforIndex.optionLabel);
				}else{
					$(modal).find("input[name='indexIdAutocomplete']").val("");
				}
			default:
				break;
			}
		} else {
			$(modal).find("input[name='indexId']").attr("parameterScope","");
		}
		
		if (data.lstParameterIndex != undefined && data.lstParameterIndex != null && data.lstParameterIndex.length != 0) {
			bindDataToAssignAutocomplete(data.parameterId,"parameter",data.lstParameterIndex,arrIndexLoop,$(templateInput),modal);
		}
		
		if (data.lstIndex != undefined && data.lstIndex != null && data.lstIndex.length != 0) {
			bindDataToAssignAutocomplete(data.indexId,"index",data.lstIndex,arrIndexLoop,$(templateInput),modal);
		}
	}
	$.qp.initialAllPicker($(modal));
	$(modal).find("input[name$='IdAutocomplete']").each(function(){
		$(this).data("ui-autocomplete")._trigger("close");
	});
	initialViewModal(modal,isOnlyView);
	$(modal).modal({
		show : true,
		closable : false,
		keyboard : false,
		backdrop : 'static'
	});
}

function openModalEmail(obj,isOnlyView) {
	var modal = BD_MODAL_NAME.EMAIL;
	$(modal).data("instance", getInstanceOfContainer(obj));
	$(modal).data("target", obj);
	var value = $(obj).find("input[name='componentElement']").val();
	var data = convertToJson(value);
	$(modal).data("data", value);

	$(modal).find("input[name='label']").val("");
	$(modal).find("textarea[name='remark']").val("");
	$(modal).find("select[name='priorityType']").val("0");
	
	$(modal).find("input[name='content']").val("");
	$(modal).find("input[name='type']").val("0");
	$(modal).find("input[name='formulaDefinitionDetails']").val("");
	$(modal).find("input[name=content]").attr("readonly",false);
	$(modal).find("textarea[id='editor-content']").val("");
	
	if (data != null) {
		if (data.label != undefined && data.label != null) {
			$(modal).find("input[name='label']").val(data.label);
		} else {
			$(modal).find("input[name='label']").val("");
		}
		
		if (data.remark != undefined && data.remark != null) {
			$(modal).find("textarea[name='remark']").val(data.remark);
		} else {
			$(modal).find("textarea[name='remark']").val("");
		}
		
		if (data.priorityType != undefined && data.priorityType != null) {
			$(modal).find("select[name='priorityType']").val(data.priorityType);
		} else {
			$(modal).find("select[name='priorityType']").val("0");
		}
		
		var trSubject = $(modal).find("tr[id='subject']");
		if (data.subjectContent != undefined && data.subjectContent != null) {
			trSubject.find("input[name='content']").val(data.subjectContent);
		} else {
			trSubject.find("input[name='content']").val("");
		}
		
		if (data.subjectType != undefined && data.subjectType != null) {
			if (data.subjectType == "1") {
				trSubject.find("[name=content]").attr("readonly",true);
			}
			
			trSubject.find("input[name='type']").val(data.subjectType);
		} else {
			trSubject.find("input[name='type']").val("0");
		}

		if (data.subjectFormulaDetails != undefined && data.subjectFormulaDetails != null) {
			trSubject.find("input[name='formulaDefinitionDetails']").val(convertToString(data.subjectFormulaDetails));
		} else {
			trSubject.find("input[name='formulaDefinitionDetails']").val("");
		}
		
		if (data.emailContent != undefined && data.emailContent != null) {
			if (data.emailContent.content != undefined && data.emailContent.content != null) {
				$(modal).find("textarea[id='editor-content']").val(data.emailContent.content);
			}
		} else {
			$(modal).find("textarea[id='editor-content']").val("");
		}
		$(modal).find("#tlbTo").find("tbody").empty();
		$(modal).find("#tlbCC").find("tbody").empty();
		$(modal).find("#tlbBCC").find("tbody").empty();
		
		if (data.emailRecipients != undefined && data.emailRecipients != null) {
			var templateInput;
			var table = "";

			for (var i = 0; i < data.emailRecipients.length; i++) {
				var recipient = data.emailRecipients[i];
			
				if (recipient.type == 0) {
					templateInput = $(modal).find("#tlbTo-template").tmpl(recipient);
					table = "#tlbTo";
				} else if (recipient.type == 1) {
					templateInput = $(modal).find("#tlbCC-template").tmpl(recipient);
					table = "#tlbCC";
				} else if (recipient.type == 2) {
					templateInput = $(modal).find("#tlbBCC-template").tmpl(recipient);
					table = "#tlbBCC";
				}
				
				if (recipient.recipientContent != undefined && recipient.recipientContent != null) {
					$(templateInput).find("input[name='content']").val(recipient.recipientContent);
				} else {
					$(templateInput).find("input[name='content']").val("");
				}
				
				if (recipient.recipientType != undefined && recipient.recipientType != null) {
					if (recipient.recipientType == "1") {
						$(templateInput).find("[name=content]").attr("readonly",true);
					}
					
					$(templateInput).find("input[name='type']").val(recipient.recipientType);
				} else {
					$(templateInput).find("input[name='type']").val("");
				}

				if (recipient.recipientFormulaDetails != undefined && recipient.recipientFormulaDetails != null) {
					$(templateInput).find("input[name='formulaDefinitionDetails']").val(convertToString(recipient.recipientFormulaDetails));
				} else {
					$(templateInput).find("input[name='formulaDefinitionDetails']").val("");
				}
				
				$(modal).find(table).find("tbody").append(templateInput);

//				$(modal).find("#tlbTo").find("tr:eq(0) a:eq(1)").hide()
//				$(modal).find("#tlbCC").find("tr:eq(0) a:eq(1)").hide()
//				$(modal).find("#tlbBCC").find("tr:eq(0) a:eq(1)").hide()
			}
		}
	}
	
	$.qp.initialAllPicker($(modal));
	initialViewModal(modal,isOnlyView);
	$(modal).modal({ 
		   show: true,
		   closable: false,
		   keyboard:false,
		   backdrop:'static'
		  });
}

function openModalDownloadFile(obj,isOnlyView) {
	var modal = BD_MODAL_NAME.DOWNLOAD_FILE;
	$(modal).data("instance", getInstanceOfContainer(obj));
	$(modal).data("target", obj);
	var value = $(obj).find("input[name='componentElement']").val();
	var data = convertToJson(value);
	$(modal).data("data", value);

	$(modal).find("input[name='label']").val("");
	$(modal).find("textarea[name='remark']").val("");
	
	$(modal).find("input[name='parameterId']").val("");
	$(modal).find("input[name='parameterIdAutocomplete']").val("");
	
	$(modal).find("input[name='content']").val("");
	$(modal).find("input[name='type']").val("0");
	$(modal).find("input[name='formulaDefinitionDetails']").val("");
	$(modal).find("input[name=content]").attr("readonly",false);
	
	if(data != null){
		if(data.label != undefined){
			$(modal).find("input[name='label']").val(data.label);
		}else{
			$(modal).find("input[name='label']").val("");
		}
		if(data.remark != undefined){
			$(modal).find("textarea[name='remark']").val(data.remark);
		}else{
			$(modal).find("textarea[name='remark']").val("");
		}
		
		var inforCurrentParam = getInformationOfParameter(data.parameterId);
		if(inforCurrentParam != undefined){
			$(modal).find("input[name='parameterId']").val(inforCurrentParam.optionValue);
			$(modal).find("input[name='parameterIdAutocomplete']").val(inforCurrentParam.optionLabel);
			$(modal).find("input[name='parameterId']").attr("parameterScope",inforCurrentParam.output01);
		}else{
			$(modal).find("input[name='parameterId']").val("");
			$(modal).find("input[name='parameterIdAutocomplete']").val("");
		}
		$(modal).find("input[name='parameterIdAutocomplete']").data("ui-autocomplete")._trigger("close");
		
		var trFileName = $(modal).find("tr[id='fileName']");
		if (data.fileNameContent != undefined && data.fileNameContent != null) {
			trFileName.find("input[name='content']").val(data.fileNameContent);
		} else {
			trFileName.find("input[name='content']").val("");
		}
		
		if (data.fileNameType != undefined && data.fileNameType != null) {
			if (data.fileNameType == "1") {
				trFileName.find("[name=content]").attr("readonly",true);
			}
			
			trFileName.find("input[name='type']").val(data.fileNameType);
		} else {
			trFileName.find("input[name='type']").val("0");
		}

		if (data.fileNameFormulaDetails != undefined && data.fileNameFormulaDetails != null) {
			trFileName.find("input[name='formulaDefinitionDetails']").val(convertToString(data.fileNameFormulaDetails));
		} else {
			trFileName.find("input[name='formulaDefinitionDetails']").val("");
		}
	}
	
	$.qp.initialAllPicker($(modal));
	initialViewModal(modal,isOnlyView);
	$(modal).modal(
			  { 
			   show: true,
			   closable: false,
			   keyboard:false,
			   backdrop:'static'
			  }
			 );
}


function openModalException(obj,isOnlyView) {
	var modal = BD_MODAL_NAME.EXCEPTION;
	var table = "#tbl-exception-inputbean";
	$(modal).data("target", obj);
	$(modal).data("instance", getInstanceOfContainer(obj));
	var value = $(obj).find("input[name='componentElement']").val();
	var data = convertToJson(value);
	
	//refesh data
	$(modal).find("input[name='label']").val("");
	$(modal).find("textarea[name='remark']").val("");
	$(modal).data("data", value);
	$(modal).find("input:radio[name=transitionType][value=0]").prop('checked', true);
	$(modal).find("input:radio[name=exceptionToType][value=0]").prop('checked', true);
	$(modal).find("input[name='exceptionToIdAutocomplete']").attr("selectsqlid","getAutocompleteScreenDesignForExceptionNode");
	$(modal).find("input[name='exceptionToIdAutocomplete']").attr("exceptionToName","");
	$(modal).find("input[name='exceptionToIdAutocomplete']").closest("td").removeClass("qp-bdesign-tr-remove");
	$(modal).find("input[name='exceptionToIdAutocomplete']").val("");
	$(modal).find("input[name='exceptionToId']").val("");
	$(modal).find("#tbl-exception-inputbean").find("tbody").empty();
	$(modal).find("input[name='moduleId']").val("");
	$(modal).find("input[name='moduleIdAutocomplete']").val("");
	
	$(modal).find("input[name=moduleIdAutocomplete]").attr('disabled', false);
	$(modal).find("input[name=exceptionToIdAutocomplete]").attr('disabled', false);
	$(modal).find("select[name=exceptionToId]").val(0);
	$(modal).find("select[name='type']").val(0);
	
	if(data != null){
		var arrIndexLoop = getLabelOfIndexLoop();
		if(data.label != undefined){
			$(modal).find("input[name='label']").val(data.label);
		}else{
			$(modal).find("input[name='label']").val("");
		}
		if(data.moduleId != undefined && data.moduleId != null){
			$(modal).find("input[name='moduleId']").val(data.moduleId);
			$(modal).find("input[name='moduleIdAutocomplete']").val(data.moduleIdAutocomplete);
			$(modal).find("input[name='exceptionToIdAutocomplete']").attr("arg03",data.moduleId);
		}else{
			$(modal).find("input[name='moduleId']").val("");
			$(modal).find("input[name='moduleIdAutocomplete']").val("");
		}
		if(data.remark != undefined){
			$(modal).find("textarea[name='remark']").val(data.remark);
		}else{
			$(modal).find("textarea[name='remark']").val("");
		}	
		if(data.type != undefined){
			$(modal).find("select[name='type']").val(data.type);
		}else{
			$(modal).find("select[name='type']").val("");
		}
		$(modal).find("input[name=transitionType][value=1]").parent().show();
		var sqlId = "getAutocompleteBusinessLogicForExceptionBD";
		$(modal).find("input[name=transitionType][value=0]").parent().hide();
		$(modal).find("input[name=transitionType][value=1]").parent().hide();
		
		$(modal).find("input[name='exceptionToId']").val("");
		$(modal).find("input[name='exceptionToIdAutocomplete']").val("");
		
		if(!isEmptyQP(data.exceptionToType)){
			$(modal).find("input:radio[name=exceptionToType][value="+data.exceptionToType+"]").prop('checked', true);
			$(modal).find("input:radio[name=transitionType][value="+data.transitionType+"]").prop('checked', true);
			$(modal).find("a[name='registerNavigatorLink']").show();
			switch (data.exceptionToType.toString()) {
			case "0":
				$(modal).find("[name=tranType]").hide();
				$(modal).find("td[name=exceptionToType]").attr("colspan", 3);
				$(modal).find("#trModule").hide();
				$(modal).find("input[name='exceptionToId']").val(data.exceptionToId);
				$(modal).find("input[name='exceptionToIdAutocomplete']").val(data.exceptionToIdAutocomplete);
				$(modal).find("input[name='exceptionToIdAutocomplete']").attr("exceptionToName",data.exceptionToName);
				sqlId = "getAutocompleteScreenDesignForExceptionNode";
				$(modal).find("a[name='registerNavigatorLink']").attr("onClick","openRegisterNewNavigator(0)");
				break;
			case "1":
				$(modal).find("[name=tranType]").show();
				$(modal).find("td[name=exceptionToType]").attr("colspan", 1);
				$(modal).find("#trModule").show();
				$(modal).find("#tdTypeCommon").hide();
				$(modal).find("#tdTypeBlogic").show();
				$(modal).find("a[name='registerNavigatorLink']").attr("onClick","openRegisterNewNavigator(1)");
				$(modal).find("input[name=transitionType][value=0]").parent().show();
				$(modal).find("input[name=transitionType][value=1]").parent().show();
				sqlId = "getAutocompleteBusinessLogicForExceptionBD";
				//input bean
				var url = CONTEXT_PATH + "/businessdesign/getInputBeanOfBusinessLogic?businessDesignId="+data.exceptionToId+"&r="+Math.random();
				var responseData = $.qp.getData(url);
				if(responseData.businessLogicId == undefined && responseData.businessLogicId == null){
					//set data by old data
					if(data.exceptionToIdAutocomplete != undefined){
						$(modal).find("input[name='exceptionToIdAutocomplete']").val(data.exceptionToIdAutocomplete);
					}else{
						$(modal).find("input[name='exceptionToIdAutocomplete']").val("");
					}
					$(modal).find("input[name='exceptionToIdAutocomplete']").closest("td").addClass("qp-bdesign-tr-remove");
				}else{
					//set data by response data
					$(modal).find("input[name='exceptionToId']").val(responseData.businessLogicId);
					
					if(responseData.businessLogicName != undefined){
						$(modal).find("input[name='exceptionToIdAutocomplete']").val(responseData.businessLogicName);
						$(modal).find("input[name='exceptionToIdAutocomplete']").attr("exceptionToName",responseData.businessLogicName);
					}else{
						$(modal).find("input[name='exceptionToIdAutocomplete']").val("");
						$(modal).find("input[name='exceptionToIdAutocomplete']").attr("exceptionToName","");
					}
					var arrRequestData = responseData.lstInputBean;
					var arrId = new Array();
					for(var i=0;i<arrRequestData.length;i++){
						arrId[arrRequestData[i].inputBeanId] = i;
					}
					var arrInputValues = data.parameterInputBeans;
					if(arrInputValues != undefined && arrInputValues.length >0){
						for(var i =0;i<arrInputValues.length;i++){
							// only compare with value 
							var currentInput = arrInputValues[i];
							if(currentInput.inputBeanId != undefined && currentInput.inputBeanId != null){
								if(arrId.hasOwnProperty(currentInput.inputBeanId)){
									var index = arrId[currentInput.inputBeanId];
									var obj = arrRequestData[index];
									obj.parameterId = currentInput.parameterId;
									obj.parameterIdAutocomplete = currentInput.parameterIdAutocomplete;
									obj.parameterScope = currentInput.parameterScope;
									obj.lstParameterIndex = currentInput.lstParameterIndex;
									
									//check
									if(obj.inputBeanCode ==  currentInput.inputBeanCode 
											&& obj.inputBeanName == currentInput.inputBeanName 
											&& obj.dataType == currentInput.dataType 
											&& obj.arrayFlg.toString() == currentInput.arrayFlg.toString()){
										obj.impactStatus = "0";
				
									}else{
										obj.impactStatus = "2";
									}
									arrRequestData[index] = obj;
								}else{
									currentInput.impactStatus = "3";
									currentInput.messageStringAutocomplete = currentInput.inputBeanName;
									arrRequestData.push(currentInput);
								}
							}
						}
					}
//					arrRequestData.sort(sortByItemSequenceNo);
					for(var i=0;i<arrRequestData.length;i++){
						var currentInput = arrRequestData[i];
						var tableIndex = (currentInput.tableIndex == null || currentInput.tableIndex == undefined) ? "" : currentInput.tableIndex;
						currentInput.tableIndex = tableIndex;
						
						var dataTypeStr = CL_BD_DATATYPE[currentInput.dataType];
						if(eval(currentInput.arrayFlg)){
							dataTypeStr += "[]";
						}
						currentInput.dataTypeStr = dataTypeStr;
						var templateInput = $(modal).find("#tbl-exception-inputbean-template").tmpl(currentInput);
						
						bindDataToAssignAutocomplete(currentInput.parameterId,"parameter",currentInput.lstParameterIndex,arrIndexLoop,$(templateInput),modal);
		
						$(templateInput).find("td.btn-remove").children().hide();
						if(currentInput.impactStatus == "3"){
							$(templateInput).find("td.btn-remove").children().show();
							$(templateInput).addClass("qp-bdesign-tr-remove");
							$(templateInput).find("input[name*='inputBeanId']").attr("impactStatus","3");
						}else if(currentInput.impactStatus != "0"){
							$(templateInput).addClass("qp-bdesign-tr-change");
						}
						
						$(modal).find("#tbl-exception-inputbean").find("tbody").append(templateInput);
					}
				}
				break;
			case "2":
				$(modal).find("[name=tranType]").show();
				$(modal).find("td[name=exceptionToType]").attr("colspan", 1);
				$(modal).find("#trModule").show();
				$(modal).find("#tdTypeCommon").show();
				$(modal).find("#tdTypeBlogic").hide();
				$(modal).find("input[name=transitionType][value=0]").parent().show();
				sqlId = "getAutocompleteScreenDesignForExceptionNode";
				$(modal).find("input[name=moduleIdAutocomplete]").attr('disabled', true);
//				$(modal).find("input[name=exceptionToIdAutocomplete]").attr('disabled', true);
//				$(modal).find("input[name=exceptionToIdAutocomplete]").val("Delete successful");
				$(modal).find("select[name=exceptionToId]").val(data.exceptionToId);
				$(modal).find("a[name='registerNavigatorLink']").hide();
				break;

			default:
				break;
			}
			
		}else{
			$(modal).find("input:radio[name=exceptionToType][value=0]").prop('checked', true);
			sqlId = "getAutocompleteScreenDesignForExceptionNode";
		}
		$(modal).find("input[name='exceptionToIdAutocomplete']").attr("selectsqlid",sqlId);
	}
	$.qp.initialAllPicker($(modal));
	$(modal).find("input[name$='IdAutocomplete']").each(function(){
		$(this).data("ui-autocomplete")._trigger("close");
	});
	initialViewModal(modal,isOnlyView);
	$(modal).modal(
			  { 
			   show: true,
			   closable: false,
			   keyboard:false,
			   backdrop:'static'
			  }
			 );
}