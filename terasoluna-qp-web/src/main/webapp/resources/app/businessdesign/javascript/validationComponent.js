var OPEN_PARENTHESIS = "(";
var CLOSE_PARENTHESIS = ")";

/**
 * Validate the detail information of node
 * @author quangvd
 */
$.qp.businessdesign.validaionComponent = (function($$module){
	var VALIDATION_COMPONENT_SPLIT = "++++++++++++++++++++++++++++++++++++";
	var VALIDATION_COMPONENT_ROW = "<br/>";
	var BLOGIC_CHECK = "1";
	var DETAIL_CHECK = "0";
	var IMPACT_STATUS = {
			NONE : "0",
			ADDED : "1",
			MODIFIED : "2",
			DELETED : "3"
	}
	/*
	 * validate each component
	 */
	var DATA_TYPE = {
			OBJECT : 0,
			BYTE : 1,
			SHORT : 2,
			INTEGER : 3,
			LONG : 4,
			FLOAT : 5,
			DOUBLE : 6,
			CHAR : 7,
			BOOLEAN : 8,
			STRING : 9,
			BIGDECIMAL : 10,
			TIMESTAMP : 11,
			DATETIME : 12,
			TIME : 13,
			ENTITY : 14,
			DATE : 15,
			COMMON_OBJECT : 16,
			EXTERNAL_OBJECT : 17,
	};
	
	$$module.validationComp = function(data,type) {
		var messages = "";
		switch (type.toString()) {
		case "2":
			messages = $$module.validationExecutionComp(BLOGIC_CHECK,data,false,undefined,type);
			break;
		case "3":
			messages = $$module.validationValidaionCheckComp(BLOGIC_CHECK,data,false,undefined,type);
			break;
		case "4":
			messages = $$module.validationBusinessCheckComp(BLOGIC_CHECK,data,false,undefined,type);
			break;
		case "5":
			messages = $$module.validationDecisionComp(BLOGIC_CHECK,data,false,undefined,type);
			break;
		case "6":
			messages = $$module.validationAdvanceComp(BLOGIC_CHECK,data,false,undefined,type);
			break;
		case "7":
			messages = $$module.validationCommonComp(BLOGIC_CHECK,data,false,undefined,type);
			break;
		case "8":
			messages = $$module.validationAssignComp(BLOGIC_CHECK,data,false,undefined,type);
			break;
		case "9":
			messages = $$module.validationIfComp(BLOGIC_CHECK,data,false,undefined,type);
			break;
		case "10":
			messages = $$module.validationLoopComp(BLOGIC_CHECK,data,false,undefined,type);
			break;
		case "11":
			messages = $$module.validationFeedbackComp(BLOGIC_CHECK,data,false,undefined,type);
			break;
		case "12":
//			messages = $$module.validationNavigatorComp(BLOGIC_CHECK,data,false,undefined,type);
			break;
		case "14":
			messages = $$module.validationNestedLogicComp(BLOGIC_CHECK,data,false,undefined,type);
			break;
		case "15":
			messages = $$module.validationFileOperationComp(BLOGIC_CHECK,data,false,undefined,type);
			break;
		case "16":
			messages = $$module.validationReadFileComp(BLOGIC_CHECK,data,false,undefined,type);
			break;
		case "17":
			messages = $$module.validationExportFileComp(BLOGIC_CHECK,data,false,undefined,type);
			break;
		case "18":
			messages = $$module.validationTransactionComp(BLOGIC_CHECK,data,false,undefined,type);
			break;
		case "20":
			messages = $$module.validationEmailComp(BLOGIC_CHECK,data,false,undefined,type);
			break;
		case "21":
			messages = $$module.validationLogComp(BLOGIC_CHECK,data,false,undefined,type);
			break;
		case "22":
			messages = $$module.validationUtilityComp(BLOGIC_CHECK,data,false,undefined,type);
			break;
		case "23":
			messages = $$module.validationDownloadFileComp(BLOGIC_CHECK,data,false,undefined,type);
			break;
		case "24":
//			messages = $$module.validationExceptionComp(BLOGIC_CHECK,data,false,undefined,type);
			break;
		default:
			break;
		}
		return messages;
	}
	
	/*
	 * validate the file operation component
	 */
	$$module.validationFileOperationComp = function(validationType,data,isWarningInModal,thiz,type) {
		var messages = "";
		if(data != undefined){
			if (!$$module.isNotEmpty(data.sourcePathContent)){
				messages += VALIDATION_COMPONENT_ROW;
				messages += $.qp.getMessage('err.sys.0025').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0060'));
			}
			
			switch (data.type) {
				case "0":
					if (!$$module.isNotEmpty(data.destinationPathContent)){
						messages += VALIDATION_COMPONENT_ROW;
						messages += $.qp.getMessage('err.sys.0025').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0061'));
					}
					break;
				case "2":
					if (!$$module.isNotEmpty(data.newFilenameContent)){
						messages += VALIDATION_COMPONENT_ROW;
						messages += $.qp.getMessage('err.sys.0025').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0062'));
					}
					break;
				case "3":
					if (!$$module.isNotEmpty(data.destinationPathContent)){
						messages += VALIDATION_COMPONENT_ROW;
						messages += $.qp.getMessage('err.sys.0025').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0061'));
					}
					
					if (data.lstMergeFileDetails.length == 0){
						messages += VALIDATION_COMPONENT_ROW;
						messages += $.qp.getMessage('err.sys.0025').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0063'));
					}
					
					for (i = 0; i < data.lstMergeFileDetails.length; i++) {
						if (!$$module.isNotEmpty(data.lstMergeFileDetails[i].sourcePathContent)){
							messages += VALIDATION_COMPONENT_ROW;
							messages += $.qp.getMessage('err.sys.0025').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0063'));
						}
					}
					
					break;
				
			}
		}else{
			messages += $.qp.getModuleMessage('sc.blogiccomponent.0100');
		}
		if(messages.length > 0){
			if(validationType == BLOGIC_CHECK){
				messages = $$module.buildHeaderOfNode(messages,data,type);
			}
		}
		return messages;
	}
	
	/*
	 * validate the read file component
	 */
	$$module.validationReadFileComp = function(validationType,data,isWarningInModal,thiz,type) {
		var messages = "";
		
		if(data != undefined){
			if (!$$module.isNotEmpty(data.sourcePathContent)) {
				messages += VALIDATION_COMPONENT_ROW;
				messages += $.qp.getMessage('err.sys.0025').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0082'));
			
			}
			if (data.targetId == undefined || data.targetId == null || data.targetId.length == 0) {
				messages += VALIDATION_COMPONENT_ROW;
				messages += $.qp.getMessage('err.sys.0025').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0084'));
			
			}
		}else{
			messages += $.qp.getModuleMessage('sc.blogiccomponent.0100');
		}
		if(messages.length > 0){
			if(validationType == BLOGIC_CHECK){
				messages = $$module.buildHeaderOfNode(messages,data,type);
			}
		}
		return messages;
	}
	
	/*
	 * validate the export file component
	 */
	$$module.validationExportFileComp = function(validationType,data,isWarningInModal,thiz,type) {
		var messages = "";
		
		if(data != undefined){
			if (!$$module.isNotEmpty(data.destinationPathContent)) {
				messages += VALIDATION_COMPONENT_ROW;
				messages += $.qp.getMessage('err.sys.0025').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0027'));
			
			}
			if (data.parameterId == undefined || data.parameterId == null || data.parameterId.length == 0) {
				messages += VALIDATION_COMPONENT_ROW;
				messages += $.qp.getMessage('err.sys.0025').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0101'));
			
			}
		}else{
			messages += $.qp.getModuleMessage('sc.blogiccomponent.0100');
		}
		
		data.lstExportAssignValues.sort(function(a, b) { 
		    return a.columnNo - b.columnNo;
		});
		for (i = 0; i < data.lstExportAssignValues.length - 1; i++) {
			if (data.lstExportAssignValues[i+1].columnNo == data.lstExportAssignValues[i].columnNo) {
				messages += VALIDATION_COMPONENT_ROW;
				messages += $.qp.getModuleMessage('err.blogiccomponent.0102').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0103'));
			}
		}
		
		
		if(messages.length > 0){
			if(validationType == BLOGIC_CHECK){
				messages = $$module.buildHeaderOfNode(messages,data,type);
			}
		}
		return messages;
	}
	
	/*
	 * validate the transction component
	 */
	$$module.validationTransactionComp = function(validationType,data,isWarningInModal,thiz,type) {
		var messages = "";
		
		if(messages.length > 0){
			if(validationType == BLOGIC_CHECK){
				messages = $$module.buildHeaderOfNode(messages,data,type);
			}
		}
		return messages;
	}
	
	/*
	 * validate the navigator component
	 */
	$$module.validationNavigatorComp = function(validationType,data,isWarningInModal,thiz,type) {
		var messages = "";
		if(data != undefined){
			if (!$$module.isNotEmpty(data.navigatorToId)){
				messages += VALIDATION_COMPONENT_ROW;
				messages += $.qp.getMessage('err.sys.0025').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0104'));
			}
			// remove because Delete complete case.
//			else if(!$$module.isNotEmpty(data.navigatorToIdRefer)){
//				messages += VALIDATION_COMPONENT_ROW;
//				messages += $.qp.getMessage('err.sys.0037').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0104'));
//			}
			else{
				if (data.parameterInputBeans != undefined && data.parameterInputBeans != null){
					for(var i=0; i < data.parameterInputBeans.length; i++){
						var currentInput = data.parameterInputBeans[i];
						var valid = true;
						//check deleted row
						if(currentInput.impactStatus == IMPACT_STATUS.DELETED){
							messages += VALIDATION_COMPONENT_ROW;
							messages += $.qp.getModuleMessage("err.blogiccomponent.0014").replace("{0}", i + 1);
							valid = false;
						}else if(currentInput.impactStatus == IMPACT_STATUS.ADDED){
							messages += VALIDATION_COMPONENT_ROW;
							messages += $.qp.getModuleMessage("err.blogiccomponent.0022").replace("{0}", i + 1);
							valid = false;
						}else{
							if(!$$module.isNotEmpty(currentInput.parameterId)){
//								messages += VALIDATION_COMPONENT_ROW;
//								messages +=  $.qp.getMessage('err.sys.0077'].replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0106')).replace("{1}", i + 1);
//								valid = false;
							}else{
								//check mapping type
								var infor = getInformationOfParameter(currentInput.parameterId);
								if(infor != undefined){
									var dataType = infor.output03;
									var arrayFlg = infor.output04;
									if(!!currentInput.lstParameterIndex && currentInput.lstParameterIndex.length > 0){
										arrayFlg = false;
									}
									if(!$$module.validateBusinessRule(currentInput.dataTypeRefer,currentInput.arrayFlgRefer,dataType,arrayFlg)){
										messages += VALIDATION_COMPONENT_ROW;
										var dataTypeStr = CL_BD_DATATYPE[currentInput.dataTypeRefer];
										if(eval(currentInput.arrayFlgRefer)){
											dataTypeStr += "[]";
										}
										messages +=  $.qp.getMessage('err.sys.0124').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0105')).replace("{1}", i + 1).replace("{2}", dataTypeStr);
										valid = false;
									}
								}else{
									messages += VALIDATION_COMPONENT_ROW;
									messages +=  $.qp.getMessage('err.sys.0123').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0106')).replace("{1}", i + 1);
									valid = false;
								}
							}
						}
						//show error
						if(!valid && isWarningInModal){
							$(thiz).closest("div.modal-dialog").find("#tbl-navigator-inputbean").find("tbody").find("tr:eq("+i+")").addClass("qp-bdesign-tr-warning");
						}else{
							$(thiz).closest("div.modal-dialog").find("#tbl-navigator-inputbean").find("tbody").find("tr:eq("+i+")").removeClass("qp-bdesign-tr-warning");
						}
					}
				}
			}
		}else{
			messages += $.qp.getModuleMessage('sc.blogiccomponent.0100');
		}
		if(messages.length > 0){
			if(validationType == BLOGIC_CHECK){
				messages = $$module.buildHeaderOfNode(messages,data,type);
			}
		}
		return messages;
	}
	
	$$module.validationUtilityComp = function(validationType,data,isWarningInModal,thiz,type){
		var messages = "";
		var isEmpty = false;
		
		if(data != undefined){
			if (!$$module.isNotEmpty(data.targetId)){
				messages += VALIDATION_COMPONENT_ROW;
				messages += $.qp.getMessage('err.sys.0025').replace("{0}",$.qp.getModuleMessage('sc.sys.0061'));
				isEmpty = true;
			} 
				
			switch(data.type){
				case "1":
				case "3":
				case "5":	
				if(data.indexId == ""){
					messages += VALIDATION_COMPONENT_ROW;
					messages += $.qp.getMessage('err.sys.0025').replace("{0}",$.qp.getModuleMessage("sc.businesslogicdesign.0255"));
					isEmpty = true;
				}
				break;
			}
			
			if(isEmpty){
				return messages;
			}
			
			switch(data.type){
				case "1":
				case "3":
				case "5":
					var scope = data.indexScope;
					if(scope == "0" || scope == "1" || scope == "2"){
						var infor = getInformationOfParameter(data.indexId);
						var dataType = infor.output03;
						
						var isAllowDatatypte = $$module.dataAllowOfIndex(dataType);
						if(!isAllowDatatypte){
							messages += VALIDATION_COMPONENT_ROW;
							messages += $.qp.getModuleMessage("sc.businesslogicdesign.0265").replace("{0}",$.qp.getModuleMessage("sc.businesslogicdesign.0255"));
						}
					}
					if(scope == "3"){
						if(isNaN(Number(data.indexId))){
							messages += VALIDATION_COMPONENT_ROW;
							messages += $.qp.getMessage("err.sys.0171").replace("{0}",$.qp.getModuleMessage("sc.businesslogicdesign.0255"));
						}
					}
			}
			
			switch(data.type){
				case "0":
				case "1":
				case "2":
				case "3":
				case "6":
				case "7":
					if ($$module.isNotEmpty(data.parameterId)){
						var targetScope = data.parameterScope;
						var targetInfor = getInformationOfParameter(data.targetId);
						
						var paramScope = data.parameterScope;
						var paramInfor = getInformationOfParameter(data.parameterId);
						
						if (paramInfor.output03 != targetInfor.output03) {
							messages += VALIDATION_COMPONENT_ROW;
							messages += $.qp.getModuleMessage('sc.blogiccomponent.0188').replace("{0}",$.qp.getModuleMessage('sc.sys.0061')).replace("{1}",$.qp.getModuleMessage('sc.businesslogicdesign.0254'));
						}
					} 
			}
			return messages;
		}
		return messages;
	}
	
	/*
	 * validate the feedback component
	 */
	$$module.validationFeedbackComp = function(validationType,data,isWarningInModal,thiz,type) {
		var messages = "";
		if(data != undefined){
			if (!$$module.isNotEmpty(data.messageCode) || !$$module.isNotEmpty(data.messageCodeAutocomplete)){
				messages += VALIDATION_COMPONENT_ROW;
				messages += $.qp.getMessage('err.sys.0025').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0107'));
			}
			if (!$$module.isNotEmpty(data.type)){
				messages += VALIDATION_COMPONENT_ROW;
				messages += $.qp.getMessage('err.sys.0025').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0057'));
			}
		}else{
			messages += $.qp.getModuleMessage('sc.blogiccomponent.0100');
		}
		if(messages.length > 0){
			if(validationType == BLOGIC_CHECK){
				messages = $$module.buildHeaderOfNode(messages,data,type);
			}
		}
		return messages;
	}
	
	/*
	 * validate the validation check component
	 */
	$$module.validationLoopComp = function(validationType,data,isWarningInModal,thiz,type) {
		var messages = "";
		if(data != undefined){
			if (!$$module.isNotEmpty(data.loopType)){
				messages += VALIDATION_COMPONENT_ROW;
				messages += $.qp.getMessage('err.sys.0025').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0108'));
			}else{
				// case 0 : for
				// case 1 : while
				if("1" == data.loopType){
					if (!$$module.isNotEmpty(data.formulaDefinitionContent)){
						messages += VALIDATION_COMPONENT_ROW;
						messages += $.qp.getMessage('err.sys.0025').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0109'));
					}
				}else if("0" == data.loopType){
					if ($$module.isNotEmpty(data.parameterId)){
						var infor = getInformationOfParameter(data.parameterId);
						if(infor == undefined){
							messages += VALIDATION_COMPONENT_ROW;
							messages +=  $.qp.getMessage('err.sys.0037').replace("{0}", $.qp.getModuleMessage('sc.businesslogicdesign.0125'));
							valid = false;
						}else if(eval(infor.output04) == false){
							messages += VALIDATION_COMPONENT_ROW;
							messages += $.qp.getModuleMessage('err.blogiccomponent.0110');
						}
					}
					
					var fromValid = true;
					var toValid = true;
					if (!$$module.isNotEmpty(data.fromScope)){
						fromValid = false;
					}else{
						if(data.fromScope != "-1" && !$$module.isNotEmpty(data.fromValue)){
							fromValid = false;
						}else{
							//validate data type for from
							var isAllowDatatypteVlueFrom = $$module.checkValidationDataTypeOfValueFrom(data);
							if(!isAllowDatatypteVlueFrom){
								messages += VALIDATION_COMPONENT_ROW;
								messages += $.qp.getModuleMessage("sc.businesslogicdesign.0265").replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0111'));
							 }
						}
					}
					if (!$$module.isNotEmpty(data.toScope)){
						toValid = false;
						
					}else{
						
						if(data.toScope != "-1" && !$$module.isNotEmpty(data.toValue)){
							toValid = false;
						}else{
							//validate data type for to
							var isAllowDatatypeValueTo = $$module.checkValidationDataTypeOfValueTo(data);
							if(!isAllowDatatypeValueTo){
								messages += VALIDATION_COMPONENT_ROW;
								messages += $.qp.getModuleMessage("sc.businesslogicdesign.0265").replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0112'));
							}
						}
					}
					
					if(!fromValid){
						messages += VALIDATION_COMPONENT_ROW;
						messages += $.qp.getMessage('err.sys.0025').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0111'));
					}
					if(!toValid){
						messages += VALIDATION_COMPONENT_ROW;
						messages += $.qp.getMessage('err.sys.0025').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0112'));
					}
				}
			}
			
		}else{
			messages += $.qp.getModuleMessage('sc.blogiccomponent.0100');
		}
		if(messages.length > 0){
			if(validationType == BLOGIC_CHECK){
				messages = $$module.buildHeaderOfNode(messages,data,type);
			}
		}
		return messages;
	}
	
	$$module.checkValidationDataTypeOfValueFrom = function(data){
		var check = true;
		var dataTypeFrom = $$module.getDataTypeOfInput(data.fromValue);
		if(dataTypeFrom == -1){
			return check;
		}
		var check = $$module.validateForFromValue(dataTypeFrom);
		return check;
	}
	$$module.checkValidationDataTypeOfValueTo = function(data){
		var check = true;
		var dataTypeTo = $$module.getDataTypeOfInput(data.toValue);
		if(dataTypeTo == -1){
			return check;
		}
		check = $$module.validateForToValue(dataTypeTo);
		return check;
	}
	
	$$module.getDataTypeOfInput =function(input){
		var inforInput = getInformationOfParameter(input);
		return !!inforInput ? inforInput.output03 : -1;
	}
	
	$$module.dataAllowOfFromAndTo = function(dataType){
		var check = false;
		switch (eval(dataType)) {
		case DATA_TYPE.BYTE:
		case DATA_TYPE.SHORT:
		case DATA_TYPE.INTEGER:
		case DATA_TYPE.CHAR:
			check = true;
			break;
		}
		return check;
	}
	
	$$module.dataAddtionAllowOfFrom = function(dataType){
		var check = false;
		switch (eval(dataType)) {
		case DATA_TYPE.LONG:
		case DATA_TYPE.FLOAT:
		case DATA_TYPE.DOUBLE:
			check = true;
			break;
		}
		return check;
	}
	
	/**
	 * validate for from value
	 */
	$$module.validateForFromValue = function(dataType){
		return $$module.dataAllowOfFromAndTo(dataType)
	}
	
	/**
	 * validate for to value
	 */
	$$module.validateForToValue = function(dataType){
		var check = $$module.dataAllowOfFromAndTo(dataType);
		if(check){
			return check;
		}else{
			return $$module.dataAddtionAllowOfFrom(dataType);
		}
	}
	
	/*
	 * validate the if component
	 */
	$$module.validationIfComp = function(validationType,data,isWarningInModal,thiz,type) {
		var messages = "";
		if(data != undefined){
			if ($$module.isNotEmpty(data.ifConditionDetails) && data.ifConditionDetails.length > 1){
				for(var i=0; i < data.ifConditionDetails.length; i++){
					var currentCondition = data.ifConditionDetails[i];
					var valid = true;
					if(!$$module.isNotEmpty(currentCondition.caption)){
						messages += VALIDATION_COMPONENT_ROW;
						messages +=  $.qp.getMessage('err.sys.0077').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0113')).replace("{1}", i + 1);
						valid = false;
					}
					// in case of else condition : not validate
					if(i < data.ifConditionDetails.length -1){
						if(!$$module.isNotEmpty(currentCondition.formulaDefinitionContent)){
							messages += VALIDATION_COMPONENT_ROW;
							messages +=  $.qp.getMessage('err.sys.0077').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0109')).replace("{1}", i + 1);
							valid = false;
						}
					}
					if(!valid && isWarningInModal){
						$(thiz).closest("div.modal-dialog").find("#tbl-ifcondition").find("tbody").find("tr:eq("+i+")").addClass("qp-bdesign-tr-warning");
					}else{
						$(thiz).closest("div.modal-dialog").find("#tbl-ifcondition").find("tbody").find("tr:eq("+i+")").removeClass("qp-bdesign-tr-warning");
					}
				}
			}else{
				messages += VALIDATION_COMPONENT_ROW;
				messages +=  $.qp.getMessage('err.sys.0037').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0109'));
			}
		}else{
			messages += $.qp.getModuleMessage('sc.blogiccomponent.0100');
		}
		if(messages.length > 0){
			if(validationType == BLOGIC_CHECK){
				messages = $$module.buildHeaderOfNode(messages,data,type);
			}
		}
		return messages;
	}
	
	/*
	 * validate the validation check component
	 */
	$$module.validationAssignComp = function(validationType,data,isWarningInModal,thiz,type) {
		var messages = "";
		if(data != undefined){
			if ($$module.isNotEmpty(data.details)){
				for(var i=0; i < data.details.length; i++){
					var currentTarget = data.details[i];
					var valid = true;
					if(!$$module.isNotEmpty(currentTarget.targetId)){
						messages += VALIDATION_COMPONENT_ROW;
						messages +=  $.qp.getMessage('err.sys.0077').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0114')).replace("{1}", i + 1);
						valid = false;
					}else{
						//check mapping type
						var infor = getInformationOfParameter(currentTarget.targetId);
						if(infor != undefined){
							var dataTypeAssign = infor.output03;
							var flagArrayAssign = infor.output04;
							if(!!currentTarget.lstTargetIndex && currentTarget.lstTargetIndex.length > 0){
								flagArrayAssign = false;
							}
							if(currentTarget.assignType == "0"){
								if(!$$module.isNotEmpty(currentTarget.parameterId)){
									messages += VALIDATION_COMPONENT_ROW;
									messages += $.qp.getMessage('err.sys.0077').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0167')).replace("{1}", i + 1);
									valid = false;
								}else{
									//check mapping type
									var inforSetting = getInformationOfParameter(currentTarget.parameterId);
									if(inforSetting != undefined){
										var dataTypePrameter = inforSetting.output03;
										var flagPrameter = inforSetting.output04;
										if(!!currentTarget.lstParameterIndex && currentTarget.lstParameterIndex.length > 0){
											flagPrameter = false;
										}
										if(!$$module.validateBusinessRule(dataTypeAssign ,flagArrayAssign,dataTypePrameter,flagPrameter)){
											messages += VALIDATION_COMPONENT_ROW;
											var dataTypeStr = CL_BD_DATATYPE[dataTypeAssign];
											messages +=  $.qp.getMessage('err.sys.0124').replace("{0}", $.qp.getModuleMessage('sc.blogiccomponent.0167')).replace("{1}", i + 1).replace("{2}", dataTypeStr);
											valid = false;
										}
									}else{
										messages += VALIDATION_COMPONENT_ROW;
										messages +=  $.qp.getMessage('err.sys.0123').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0167')).replace("{1}", i + 1);
										valid = false;
									}
								}
							}else{
								var formulaDefinitionDetails = data.formulaDefinitionDetails;
								if(!!formulaDefinitionDetails && !!formulaDefinitionDetails.length && formulaDefinitionDetails.length == 0){
									messages += VALIDATION_COMPONENT_ROW;
									messages += $.qp.getMessage('err.sys.0077').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0114')).replace("{1}", i + 1);
								}
							}
							

						}else{
							messages += VALIDATION_COMPONENT_ROW;
							messages +=  $.qp.getMessage('err.sys.0123').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0114')).replace("{1}", i + 1);
							valid = false;
						}
					}
					
					
					
					
					if(!valid && isWarningInModal){
						$(thiz).closest("div.modal-dialog").find("#tbl-assign-parameter").find("tbody").find("tr:eq("+i+")").addClass("qp-bdesign-tr-warning");
					}else{
						$(thiz).closest("div.modal-dialog").find("#tbl-assign-parameter").find("tbody").find("tr:eq("+i+")").removeClass("qp-bdesign-tr-warning");
					}
				}
			}else{
				messages += VALIDATION_COMPONENT_ROW;
				messages +=  $.qp.getMessage('err.sys.0037').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0115'));
			}
		}else{
			messages += $.qp.getModuleMessage('sc.blogiccomponent.0100');
		}
		if(messages.length > 0){
			if(validationType == BLOGIC_CHECK){
				messages = $$module.buildHeaderOfNode(messages,data,type);
			}
		}
		return messages;
	}
	/*
	 * validate the common component
	 */
	$$module.validationCommonComp = function(validationType,data,isWarningInModal,thiz,type) {
		var messages = "";
		if(data != undefined){
			if(!$$module.isNotEmpty(data.businessLogicId)){
				messages += VALIDATION_COMPONENT_ROW;
				messages += $.qp.getMessage('err.sys.0025').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0116'));
			}else if(!$$module.isNotEmpty(data.businessLogicIdRefer)){
				messages += VALIDATION_COMPONENT_ROW;
				messages += $.qp.getMessage('err.sys.0037').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0117'));
			}else{
				if (data.parameterInputBeans != undefined && data.parameterInputBeans != null){
					for(var i=0; i < data.parameterInputBeans.length; i++){
						var currentInput = data.parameterInputBeans[i];
						var valid = true;
						//check deleted row
						if(currentInput.impactStatus == IMPACT_STATUS.DELETED){
							messages += VALIDATION_COMPONENT_ROW;
							messages += $.qp.getModuleMessage("err.blogiccomponent.0014").replace("{0}", i + 1);
							valid = false;
						}else{
							if(!$$module.isNotEmpty(currentInput.parameterId)){
//								messages += VALIDATION_COMPONENT_ROW;
//								messages +=  $.qp.getMessage('err.sys.0077'].replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0106']).replace("{1}", i + 1);
//								valid = false;
							}else{
								//check mapping type
								var infor = getInformationOfParameter(currentInput.parameterId);
								if(infor != undefined){
									var dataType = infor.output03;
									var arrayFlg = infor.output04;
									if(!!currentInput.lstParameterIndex && currentInput.lstParameterIndex.length > 0){
										arrayFlg = false;
									}
//									var indexFlg = false;
//									if(arrayFlg == true){
//										if(currentInput.lstParameterIndex != undefined && currentInput.lstParameterIndex.length > 0){
//											
//										}
//									}
									//currentInput.dataTypeRefer,currentInput.arrayFlgRefer,dataType,arrayFlg
									if(!$$module.validateBusinessRule(currentInput.dataTypeRefer,currentInput.arrayFlgRefer,dataType,arrayFlg)){
										messages += VALIDATION_COMPONENT_ROW;
										var dataTypeStr = CL_BD_DATATYPE[currentInput.dataTypeRefer];
										if(eval(currentInput.arrayFlgRefer)){
											dataTypeStr += "[]";
										}
										messages +=  $.qp.getMessage('err.sys.0124').replace("{0}",$.qp.getModuleMessage("sc.blogiccomponent.0105")).replace("{1}", i + 1).replace("{2}", dataTypeStr);
										valid = false;
									}
								}else{
									messages += VALIDATION_COMPONENT_ROW;
									messages +=  $.qp.getMessage('err.sys.0123').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0106')).replace("{1}", i + 1);
									valid = false;
								}
							}
						}
						//show error
						if(!valid && isWarningInModal){
							$(thiz).closest("div.modal-dialog").find("#tbl-common-inputbean").find("tbody").find("tr:eq("+i+")").addClass("qp-bdesign-tr-warning");
						}else{
							$(thiz).closest("div.modal-dialog").find("#tbl-common-inputbean").find("tbody").find("tr:eq("+i+")").removeClass("qp-bdesign-tr-warning");
						}
					}
				}
				if (data.parameterOutputBeans != undefined && data.parameterOutputBeans != null){
					for(var i=0; i < data.parameterOutputBeans.length; i++){
						var currentOutput = data.parameterOutputBeans[i];
						var valid = true;
						//check deleted row
						if(currentOutput.impactStatus == IMPACT_STATUS.DELETED){
							messages += VALIDATION_COMPONENT_ROW;
							messages += $.qp.getModuleMessage("err.blogiccomponent.0015").replace("{0}", i + 1);
							valid = false;
						}else{
							if(!$$module.isNotEmpty(currentOutput.targetId)){
//								messages += VALIDATION_COMPONENT_ROW;
//								messages +=  $.qp.getMessage('err.sys.0077'].replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0119']).replace("{1}", i + 1);
//								valid = false;
							}else{
								//check mapping type
								var infor = getInformationOfParameter(currentOutput.targetId);
								if(infor != undefined){
									var dataType = infor.output03;
									var arrayFlg = infor.output04;
									if(!!currentOutput.lstTargetIndex && currentOutput.lstTargetIndex.length > 0){
										arrayFlg = false;
									}
									//currentOutput.dataTypeRefer,currentOutput.arrayFlgRefer,dataType,arrayFlg
									if(!$$module.validateBusinessRule(dataType,arrayFlg,currentOutput.dataTypeRefer,currentOutput.arrayFlgRefer)){
										messages += VALIDATION_COMPONENT_ROW;
										var dataTypeStr = CL_BD_DATATYPE[currentOutput.dataTypeRefer];
										if(eval(currentOutput.arrayFlgRefer)){
											dataTypeStr += "[]";
										}
										messages +=  $.qp.getMessage('err.sys.0124').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0118')).replace("{1}", i + 1).replace("{2}", dataTypeStr);
										valid = false;
									}
								}else{
									messages += VALIDATION_COMPONENT_ROW;
									messages +=  $.qp.getMessage('err.sys.0123').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0119')).replace("{1}", i + 1);
									valid = false;
								}
							}
						}
						
						if(!valid && isWarningInModal){
							$(thiz).closest("div.modal-dialog").find("#tbl-common-outputbean").find("tbody").find("tr:eq("+i+")").addClass("qp-bdesign-tr-warning");
						}else{
							$(thiz).closest("div.modal-dialog").find("#tbl-common-outputbean").find("tbody").find("tr:eq("+i+")").removeClass("qp-bdesign-tr-warning");
						}
					}
				}
			}
		}else{
			messages += $.qp.getModuleMessage('sc.blogiccomponent.0100');
		}
		if(messages.length > 0){
			if(validationType == BLOGIC_CHECK){
				messages = $$module.buildHeaderOfNode(messages,data,type);
			}
		}
		return messages;
	}
	
	$$module.validationAdvanceComp = function(validationType,data,isWarningInModal,thiz,type){
		var messages = "";
		if(data != undefined){
			if(!$$module.isNotEmpty(data.methodName)){
				messages += VALIDATION_COMPONENT_ROW;
				messages += $.qp.getMessage('err.sys.0025').replace("{0}",$.qp.getModuleMessage('sc.businesslogicdesign.0165'));
			} else {
				//validate length of method name
				if (data.methodName.length > 50){
					messages += VALIDATION_COMPONENT_ROW;
					messages += $.qp.getMessage('err.sys.0064').replace("{0}",$.qp.getModuleMessage('sc.businesslogicdesign.0165')).replace("{1}", "1").replace("{2}", "50");
				}
				if (!$.qp.validateIsCode(data.methodName)) {
					messages += VALIDATION_COMPONENT_ROW;
					messages += $.qp.getMessage('err.sys.0066').replace("{0}", $.qp.getModuleMessage('sc.businesslogicdesign.0165'));
				}
				
				//validate duplicate method code
			}
			
			if (data.parameterInputBeans != undefined && data.parameterInputBeans != null){
				messages += $$module.validateDuplicate("#tabsAdvance-inputbean","inputBeanCode", $.qp.getModuleMessage('sc.businesslogicdesign.0038'), $.qp.getModuleMessage('sc.businesslogicdesign.0089'));
				
				for(var i=0; i < data.parameterInputBeans.length; i++){
					var currentInput = data.parameterInputBeans[i];
					var valid = true;
					//check deleted row
					if(currentInput.impactStatus == IMPACT_STATUS.DELETED){
						messages += VALIDATION_COMPONENT_ROW;
						messages += $.qp.getModuleMessage("err.blogiccomponent.0014").replace("{0}", i + 1);
						valid = false;
					}else{
						if(!$$module.isNotEmpty(currentInput.parameterId)){
//								messages += VALIDATION_COMPONENT_ROW;
//								messages +=  $.qp.getMessage('err.sys.0077'].replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0106']).replace("{1}", i + 1);
//								valid = false;
						}else{
							//check mapping type
							var infor = getInformationOfParameter(currentInput.parameterId);
							if(infor != undefined){
								var dataType = infor.output03;
								var arrayFlg = infor.output04;
								if(currentInput.lstParameterIndex){
									arrayFlg = false;
								}
//									var indexFlg = false;
//									if(arrayFlg == true){
//										if(currentInput.lstParameterIndex != undefined && currentInput.lstParameterIndex.length > 0){
//											
//										}
//									}
								//currentInput.dataTypeRefer,currentInput.arrayFlgRefer,dataType,arrayFlg
								if(!$$module.validateBusinessRule(currentInput.dataType,"false",dataType,arrayFlg)){
									messages += VALIDATION_COMPONENT_ROW;
									var dataTypeStr = CL_BD_DATATYPE[currentInput.dataType];
									messages +=  $.qp.getMessage('err.sys.0124').replace("{0}",$.qp.getModuleMessage("sc.blogiccomponent.0105")).replace("{1}", i + 1).replace("{2}", dataTypeStr);
									valid = false;
								}
							}else{
								messages += VALIDATION_COMPONENT_ROW;
								messages +=  $.qp.getMessage('err.sys.0123').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0106')).replace("{1}", i + 1);
								valid = false;
							}
						}
					}
					//show error
					if(!valid && isWarningInModal){
						$(thiz).closest("div.modal-dialog").find("#tbl-common-inputbean").find("tbody").find("tr:eq("+i+")").addClass("qp-bdesign-tr-warning");
					}else{
						$(thiz).closest("div.modal-dialog").find("#tbl-common-inputbean").find("tbody").find("tr:eq("+i+")").removeClass("qp-bdesign-tr-warning");
					}
				}
			}
			if (data.parameterOutputBeans != undefined && data.parameterOutputBeans != null){
				messages += $$module.validateDuplicate("#tabsAdvance-outputbean","outputBeanCode", $.qp.getModuleMessage('sc.businesslogicdesign.0038'), $.qp.getModuleMessage('sc.businesslogicdesign.0090'));
				
				for(var i=0; i < data.parameterOutputBeans.length; i++){
					var currentOutput = data.parameterOutputBeans[i];
					var valid = true;
					//check mapping type
					var infor = getInformationOfParameter(currentOutput.targetId);
					if(infor != undefined){
						var dataType = infor.output03;
						var arrayFlg = infor.output04;
						//currentOutput.dataTypeRefer,currentOutput.arrayFlgRefer,dataType,arrayFlg
						if(!!currentOutput.lstTargetIndex && currentOutput.lstTargetIndex.length > 0){
							arrayFlg = false;
						}
						if(!$$module.validateBusinessRule(dataType, arrayFlg, currentOutput.dataType, "false")){
							messages += VALIDATION_COMPONENT_ROW;
							var dataTypeStr = CL_BD_DATATYPE[currentOutput.dataType];
							messages +=  $.qp.getMessage('err.sys.0124').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0118')).replace("{1}", i + 1).replace("{2}", dataTypeStr);
							valid = false;
						}
					}else{
						messages += VALIDATION_COMPONENT_ROW;
						messages +=  $.qp.getMessage('err.sys.0123').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0119')).replace("{1}", i + 1);
						valid = false;
					}
					if(!valid && isWarningInModal){
						$(thiz).closest("div.modal-dialog").find("#tbl-common-outputbean").find("tbody").find("tr:eq("+i+")").addClass("qp-bdesign-tr-warning");
					}else{
						$(thiz).closest("div.modal-dialog").find("#tbl-common-outputbean").find("tbody").find("tr:eq("+i+")").removeClass("qp-bdesign-tr-warning");
					}
				}
			}
		}else{
			messages += $.qp.getModuleMessage('sc.blogiccomponent.0100');
		}
		if(messages.length > 0){
			if(validationType == BLOGIC_CHECK){
				messages = $$module.buildHeaderOfNode(messages,data,type);
			}
		}
		return messages;
	}
	
	
	/*
	 * validate the decision component
	 */
	$$module.validationDecisionComp = function(validationType,data,isWarningInModal,thiz,type) {
		var messages = "";
		if(data != undefined){
			if(!$$module.isNotEmpty(data.decisionTableId)){
				messages += VALIDATION_COMPONENT_ROW;
				messages += $.qp.getMessage('err.sys.0025').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0120'));
			}else if(!$$module.isNotEmpty(data.decisionTableIdRefer)){
				messages += VALIDATION_COMPONENT_ROW;
				messages += $.qp.getMessage('err.sys.0037').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0120'));
			}else{
				if (data.parameterInputBeans != undefined && data.parameterInputBeans != null){
					for(var i=0; i < data.parameterInputBeans.length; i++){
						var currentInput = data.parameterInputBeans[i];
						var valid = true;
						//check deleted row
						if(currentInput.impactStatus == IMPACT_STATUS.DELETED){
							messages += VALIDATION_COMPONENT_ROW;
							messages += $.qp.getModuleMessage("err.blogiccomponent.0014").replace("{0}", i + 1);
							valid = false;
						}else{
							if(!$$module.isNotEmpty(currentInput.parameterId)){
//								messages += VALIDATION_COMPONENT_ROW;
//								messages +=  $.qp.getMessage('err.sys.0077'].replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0106']).replace("{1}", i + 1);
//								valid = false;
							}else{
								//check mapping type
								var infor = getInformationOfParameter(currentInput.parameterId);
								if(infor != undefined){
									var dataType = infor.output03;
									var arraFlg = infor.output04;
									if(!!currentInput.lstParameterIndex && currentInput.lstParameterIndex.length > 0){
										arraFlg = false;
									}
									//compareDataType(currentInput.dataTypeRefer,"true",dataType,"true")
									if(!$$module.validateBusinessRule(currentInput.dataTypeRefer ,false, dataType, arraFlg)){
										messages += VALIDATION_COMPONENT_ROW;
										var dataTypeStr = CL_BD_DATATYPE[currentInput.dataType];
										messages +=  $.qp.getMessage('err.sys.0124').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0105')).replace("{1}", i + 1).replace("{2}", dataTypeStr);
										valid = false;
									}
								}else{
									messages += VALIDATION_COMPONENT_ROW;
									messages +=  $.qp.getMessage('err.sys.0123').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0106')).replace("{1}", i + 1);
									valid = false;
								}
							}
						}
						//show error
						if(!valid && isWarningInModal){
							$(thiz).closest("div.modal-dialog").find("#tbl-decision-inputbean").find("tbody").find("tr:eq("+i+")").addClass("qp-bdesign-tr-warning");
						}else{
							$(thiz).closest("div.modal-dialog").find("#tbl-decision-inputbean").find("tbody").find("tr:eq("+i+")").removeClass("qp-bdesign-tr-warning");
						}
					}
				}
				if (data.parameterOutputBeans != undefined && data.parameterOutputBeans != null){
					for(var i=0; i < data.parameterOutputBeans.length; i++){
						var currentOutput = data.parameterOutputBeans[i];
						var valid = true;
						//check deleted row
						if(currentOutput.impactStatus == IMPACT_STATUS.DELETED){
							messages += VALIDATION_COMPONENT_ROW;
							messages += $.qp.getModuleMessage("err.blogiccomponent.0015").replace("{0}", i + 1);
							valid = false;
						}else{
							if(!$$module.isNotEmpty(currentOutput.targetId)){
//								messages += VALIDATION_COMPONENT_ROW;
//								messages +=  $.qp.getMessage('err.sys.0077'].replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0119']).replace("{1}", i + 1);
//								valid = false;
							}else{
								//check mapping type
								var infor = getInformationOfParameter(currentOutput.targetId);
								if(infor != undefined){
									var dataType = infor.output03;
									var flagArray = infor.output04;
									if(!!currentOutput.lstTargetIndex && currentOutput.lstTargetIndex.length > 0){
										flagArray = false;
									}
									//!compareDataType(currentOutput.dataTypeRefer,"true",dataType,"true")
									if(!$$module.validateBusinessRule(dataType,flagArray,currentOutput.dataTypeRefer,false)){
										messages += VALIDATION_COMPONENT_ROW;
										var dataTypeStr = CL_BD_DATATYPE[currentOutput.dataType];
										messages +=  $.qp.getMessage('err.sys.0124').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0118')).replace("{1}", i + 1).replace("{2}", dataTypeStr);
										valid = false;
									}
								}else{
									messages += VALIDATION_COMPONENT_ROW;
									messages +=  $.qp.getMessage('err.sys.0123').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0119')).replace("{1}", i + 1);
									valid = false;
								}
							}
						}
						if(!valid && isWarningInModal){
							$(thiz).closest("div.modal-dialog").find("#tbl-decision-outputbean").find("tbody").find("tr:eq("+i+")").addClass("qp-bdesign-tr-warning");
						}else{
							$(thiz).closest("div.modal-dialog").find("#tbl-decision-outputbean").find("tbody").find("tr:eq("+i+")").removeClass("qp-bdesign-tr-warning");
						}
					}
				}
			}
		}else{
			messages += $.qp.getModuleMessage('sc.blogiccomponent.0100');
		}
		if(messages.length > 0){
			if(validationType == BLOGIC_CHECK){
				messages = $$module.buildHeaderOfNode(messages,data,type);
			}
		}
		return messages;
	}
	
	/*
	 * validate the business check component
	 */
	$$module.validationBusinessCheckComp = function(validationType,data,isWarningInModal,thiz,type) {
		var messages = "";
		if(data != undefined){
			if ($$module.isNotEmpty(data.businessCheckDetails)){
				for(var i=0; i < data.businessCheckDetails.length; i++){
					var currentCheck = data.businessCheckDetails[i];
					if("1" != currentCheck.businessCheckType){
						if($$module.isNotEmpty(currentCheck.contents)){
							var validChild = true;
							var validTable = true;
							for(var j=0; j < currentCheck.contents.length; j++){
								var currentRow = currentCheck.contents[j];
								if(!$$module.isNotEmpty(currentRow.columnId)){
									messages += VALIDATION_COMPONENT_ROW;
									messages +=  $.qp.getMessage('err.sys.0120').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0121')).replace("{1}", j + 1).replace("{2}", i + 1);
									validChild = false;
								}
								if(!$$module.isNotEmpty(currentRow.parameterId)){
									messages += VALIDATION_COMPONENT_ROW;
									messages +=  $.qp.getMessage('err.sys.0120').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0122')).replace("{1}", j + 1).replace("{2}", i + 1);
									validChild = false;
								}
								if($$module.isNotEmpty(currentRow.columnId) && $$module.isNotEmpty(currentRow.parameterId)){
									var infor = getInformationOfParameter(currentRow.parameterId);
									if(infor != undefined){
										var dataType = infor.output03;
										var arrayFlg = infor.output04;
										if(!!currentRow.lstParameterIndex && currentRow.lstParameterIndex.length > 0){
											arrayFlg = false;
										}
										if(!$$module.validateBusinessRule(currentRow.dataType,false,dataType,arrayFlg)){
											messages += VALIDATION_COMPONENT_ROW;
											var dataTypeStr = CL_BD_DATATYPE[currentRow.dataType];
											messages +=  $.qp.getMessage('err.sys.0124').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0122')).replace("{1}", j + 1).replace("{2}", dataTypeStr);
											validChild = false;
										}
									}else{
										messages += VALIDATION_COMPONENT_ROW;
										messages +=  $.qp.getMessage('err.sys.0123').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0122')).replace("{1}", j + 1);
										validChild = false;
									}
								}
								if(!$$module.isNotEmpty(currentRow.tblDesignId)){
									validTable = false;
								}
								if(!validChild && isWarningInModal){
									$(thiz).closest("div.modal-dialog").find("div.qp-bdesign-div-content:eq("+i+")").find("#tbl-content-conditions").find("tbody").find("tr:eq("+j+")").addClass("qp-bdesign-tr-warning");
								}else{
									$(thiz).closest("div.modal-dialog").find("div.qp-bdesign-div-content:eq("+i+")").find("#tbl-content-conditions").find("tbody").find("tr:eq("+j+")").removeClass("qp-bdesign-tr-warning");
								}
								
//								if(!validTable){
//									messages += VALIDATION_COMPONENT_ROW;
//									messages +=  $.qp.getMessage('err.sys.0119').replace("{0}","Table").replace("{1}", i + 1);
//									if(isWarningInModal){
//										$(thiz).closest("div.modal-dialog").find("div.qp-bdesign-div-content:eq("+i+")").find("#tbl-content").find("tbody").find("tr:eq("+0+")").addClass("qp-bdesign-tr-warning");
//									}else{
//										$(thiz).closest("div.modal-dialog").find("div.qp-bdesign-div-content:eq("+i+")").find("#tbl-content").find("tbody").find("tr:eq("+0+")").removeClass("qp-bdesign-tr-warning");
//									}
//								}else{
////									$(thiz).closest("div.modal-dialog").find("div.qp-bdesign-div-content:eq("+i+")").find("#tbl-content").find("tbody").find("tr:eq("+0+")").removeClass("qp-bdesign-tr-warning");
//								}
							}
						}else{
							messages += VALIDATION_COMPONENT_ROW;
							messages +=  $.qp.getMessage('err.sys.0119').replace("{0}",$.qp.getModuleMessage('sc.businesslogicdesign.0021')).replace("{1}", i + 1);
							if(isWarningInModal){
								$(thiz).closest("div.modal-dialog").find("div.qp-bdesign-div-content:eq("+i+")").find("#tbl-content").find("tbody").find("tr:eq("+0+")").addClass("qp-bdesign-tr-warning");
							}else{
								$(thiz).closest("div.modal-dialog").find("div.qp-bdesign-div-content:eq("+i+")").find("#tbl-content").find("tbody").find("tr:eq("+0+")").removeClass("qp-bdesign-tr-warning");
							}
						}
					}else{
						if(currentCheck.formulaDefinitionDetails.length == 0){
							messages += VALIDATION_COMPONENT_ROW;
							messages +=  $.qp.getMessage('err.sys.0119').replace("{0}",$.qp.getModuleMessage('sc.businesslogicdesign.0021')).replace("{1}", i + 1);
						}
					}
					if(!$$module.isNotEmpty(currentCheck.messageCode)){
						messages += VALIDATION_COMPONENT_ROW;
						messages +=  $.qp.getMessage('err.sys.0119').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0107')).replace("{1}", i + 1);
					}
				}
			}else{
				messages += VALIDATION_COMPONENT_ROW;
				messages +=  $.qp.getMessage('err.sys.0037').replace("{0}",$.qp.getModuleMessage("sc.businesslogicdesign.0253"));
			}
		}else{
			messages += $.qp.getModuleMessage('sc.blogiccomponent.0100');
		}
		if(messages.length > 0){
			if(validationType == BLOGIC_CHECK){
				messages = $$module.buildHeaderOfNode(messages,data,type);
			}
		}
		return messages;
	}
	
	/*
	 * validate the validation check component
	 */
	$$module.validationValidaionCheckComp = function(validationType,data,isWarningInModal,thiz,type) {
		var messages = "";
		if(data != undefined){
			if ($$module.isNotEmpty(data.validationCheckDetails)){
				var validateInputBean = new Array();
				for(var j=0; j < data.validationCheckDetails.length;j++){
					var objDetailsData = data.validationCheckDetails[j];
					var checkInput = new Object();
					if(validateInputBean[objDetailsData.inputBeanId] == undefined){
						var parameterItems = [];
						for(var k=j; k < data.validationCheckDetails.length;k++){
							var objDetailsDataTemp = data.validationCheckDetails[k];
							if(objDetailsDataTemp.inputBeanId == objDetailsData.inputBeanId){
								parameterItems.push(objDetailsDataTemp);
							}
						}
						checkInput.inputBeanId = objDetailsData.inputBeanId;
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
					var currentInput = validateInputBean[key];
					var valid = true;
					//check mapping type
					var infor = getInformationOfParameter("0"+currentInput.inputBeanId);
					if(infor != undefined){
						var dataType = infor.output03;
						var arrayFlg = infor.output04;
						if(!compareDataType(currentInput.dataType,currentInput.arrayFlg,dataType,arrayFlg)){
							messages += VALIDATION_COMPONENT_ROW;
							messages +=  $.qp.getModuleMessage('err.blogiccomponent.0017').replace("{0}",currentInput.inputBeanCode);
							valid = false;
						}
					}else{
						messages += VALIDATION_COMPONENT_ROW;
						messages +=  $.qp.getModuleMessage('err.blogiccomponent.0016').replace("{0}",currentInput.inputBeanCode);
						valid = false;
					}
				}
			}else{
				messages += VALIDATION_COMPONENT_ROW;
				messages +=  $.qp.getMessage('err.sys.0037').replace("{0}",$.qp.getModuleMessage('err.blogiccomponent.0123'));
			}
		}else{
			messages += $.qp.getModuleMessage('sc.blogiccomponent.0100');
		}
		if(messages.length > 0){
			if(validationType == BLOGIC_CHECK){
				messages = $$module.buildHeaderOfNode(messages,data,type);
			}
		}
		return messages;
	}
	
	/*
	 * validate the validation check component
	 */
	$$module.validationExecutionComp = function(validationType,data,isWarningInModal,thiz,type) {
		var messages = "";
		if(data != undefined){
			if(!$$module.isNotEmpty(data.sqlDesignId)){
				messages += VALIDATION_COMPONENT_ROW;
				messages += $.qp.getMessage('err.sys.0025').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0091'));
			}else if(!$$module.isNotEmpty(data.sqlDesignIdRefer)){
				messages += VALIDATION_COMPONENT_ROW;
				messages += $.qp.getMessage('err.sys.0037').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0124'));
			}else{
				if (data.parameterInputBeans != undefined && data.parameterInputBeans != null){
					for(var i=0; i < data.parameterInputBeans.length; i++){
						var currentInput = data.parameterInputBeans[i];
						var valid = true;
						
						//check deleted row
						if(currentInput.impactStatus == IMPACT_STATUS.DELETED){
							messages += VALIDATION_COMPONENT_ROW;
							messages += $.qp.getModuleMessage("err.blogiccomponent.0014").replace("{0}", i + 1);
							valid = false;
						}else{
							if(!$$module.isNotEmpty(currentInput.parameterId)){
//								messages += VALIDATION_COMPONENT_ROW;
//								messages +=  $.qp.getMessage('err.sys.0077').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0106')).replace("{1}", i + 1);
//								valid = false;
							}else{
								//check mapping type
								var infor = getInformationOfParameter(currentInput.parameterId);
								if(infor != undefined){
									var dataType = infor.output03;
									var arrayFlg = infor.output04;
									if(!!currentInput.lstParameterIndex && currentInput.lstParameterIndex.length > 0){
										arrayFlg = false;
									}
									if(!$$module.validateBusinessRule(currentInput.dataTypeRefer,currentInput.arrayFlgRefer,dataType,arrayFlg)){
										messages += VALIDATION_COMPONENT_ROW;
										var dataTypeStr = CL_BD_DATATYPE[currentInput.dataTypeRefer];
										if(eval(currentInput.arrayFlgRefer)){
											dataTypeStr += "[]";
										}
										messages +=  $.qp.getMessage('err.sys.0124').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0105')).replace("{1}", i + 1).replace("{2}", dataTypeStr);
										valid = false;
									}
								}else{
									messages += VALIDATION_COMPONENT_ROW;
									messages +=  $.qp.getMessage('err.sys.0123').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0106')).replace("{1}", i + 1);
									valid = false;
								}
							}
						}
						if(!valid && isWarningInModal){
							$(thiz).closest("div.modal-dialog").find("#tbl-execution-inputbean").find("tbody").find("tr:eq("+i+")").addClass("qp-bdesign-tr-warning");
						}else{
							$(thiz).closest("div.modal-dialog").find("#tbl-execution-inputbean").find("tbody").find("tr:eq("+i+")").removeClass("qp-bdesign-tr-warning");
						}
					}
				}
				if (data.parameterOutputBeans != undefined && data.parameterOutputBeans != null){
					for(var i=0; i < data.parameterOutputBeans.length; i++){
						var currentOutput = data.parameterOutputBeans[i];
						var valid = true;
						//check deleted row
						if(currentOutput.impactStatus == IMPACT_STATUS.DELETED){
							messages += VALIDATION_COMPONENT_ROW;
							messages += $.qp.getModuleMessage("err.blogiccomponent.0015").replace("{0}", i + 1);
							valid = false;
						}else{
							if(!$$module.isNotEmpty(currentOutput.targetId)){
//								messages += VALIDATION_COMPONENT_ROW;
//								messages +=  $.qp.getMessage('err.sys.0077').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0119')).replace("{1}", i + 1);
//								valid = false;
							}else{
								//check mapping type
								var infor = getInformationOfParameter(currentOutput.targetId);
								if(infor != undefined){
									var dataType = infor.output03;
									var arrayFlg = infor.output04;
									if( !!currentOutput.lstTargetIndex && currentOutput.lstTargetIndex.length > 0){
										arrayFlg = false;
									}
									if(!$$module.validateBusinessRule(dataType,arrayFlg,currentOutput.dataTypeRefer,currentOutput.arrayFlgRefer)){
										messages += VALIDATION_COMPONENT_ROW;
										var dataTypeStr = CL_BD_DATATYPE[currentOutput.dataTypeRefer];
										if(eval(currentOutput.arrayFlgRefer)){
											dataTypeStr += "[]";
										}
										messages +=  $.qp.getMessage('err.sys.0124').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0118')).replace("{1}", i + 1).replace("{2}", dataTypeStr);
										valid = false;
									}
								}else{
									messages += VALIDATION_COMPONENT_ROW;
									messages +=  $.qp.getMessage('err.sys.0123').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0119')).replace("{1}", i + 1);
									valid = false;
								}
							}
						}
						if(!valid && isWarningInModal){
							$(thiz).closest("div.modal-dialog").find("#tbl-execution-outputbean").find("tbody").find("tr:eq("+i+")").addClass("qp-bdesign-tr-warning");
						}else{
							$(thiz).closest("div.modal-dialog").find("#tbl-execution-outputbean").find("tbody").find("tr:eq("+i+")").removeClass("qp-bdesign-tr-warning");
						}
					}
				}
			}
		}else{
			messages += $.qp.getModuleMessage('sc.blogiccomponent.0100');
		}
		if(messages.length > 0){
			if(validationType == BLOGIC_CHECK){
				messages = $$module.buildHeaderOfNode(messages,data,type);
			}
		}
		return messages;
	}
	
	/*
	 * validate the log component
	 */
	$$module.validationLogComp = function(validationType,data,isWarningInModal,thiz,type) {
		var messages = "";
		
		if(data != undefined){
//			if (!$$module.isNotEmpty(data.sourcePathContent)) {
//				messages += VALIDATION_COMPONENT_ROW;
//				messages += $.qp.getMessage('err.sys.0025').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0082'));
//			
//			}
//			if (data.targetId == undefined || data.targetId == null || data.targetId.length == 0) {
//				messages += VALIDATION_COMPONENT_ROW;
//				messages += $.qp.getMessage('err.sys.0025').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0084'));
//			
//			}
			var messageContentFlg = true;
			if(data.messageType == "0"){
				if (!$$module.isNotEmpty(data.messageContent)) {
					messageContentFlg = false;
				}
			}else if(data.messageType == "1"){
				if (data.messageFormulaDetails.length == 0) {
					messageContentFlg = false;
				}
			}
			if(!messageContentFlg){
				messages += VALIDATION_COMPONENT_ROW;
				messages += $.qp.getMessage('err.sys.0025').replace("{0}",$.qp.getModuleMessage('sc.businesslogicdesign.0240'));
			}
		}else{
			messages += $.qp.getModuleMessage('sc.blogiccomponent.0100');
		}
		if(messages.length > 0){
			if(validationType == BLOGIC_CHECK){
				messages = $$module.buildHeaderOfNode(messages,data,type);
			}
		}
		return messages;
	}
	
	$$module.validationNestedLogicComp = function(validationType,data,isWarningInModal,thiz,type){
		var messages = "";
		if(data != undefined){
			if(data.arrComponent != undefined){
				for (var i = 0; i < data.arrComponent.length; i++) {
					var componentType = data.arrComponent[i].componentType;
					if(componentType != "1" && componentType != "13"){
						var strData = data.arrComponent[i].strData;
						var detailMessage = $.qp.businessdesign.validaionComponent.validationComp(convertToJson(strData),componentType);
						if(detailMessage.length >0){
							$(thiz).closest("form").find("#designArea").find("div.execution-class[id='"+data.arrComponent[i].sequenceLogicId+"']").addClass("node-error");
						}
						messages += detailMessage;
					}
				}
			}
		}else{
			messages += $.qp.getModuleMessage('sc.blogiccomponent.0100');
		}
		if(messages.length > 0){
			if(validationType == BLOGIC_CHECK){
				messages = $$module.buildHeaderOfNode(messages,data,type);
			}
		}
		return messages;
	}
	
	$$module.validationEmailComp = function(validationType,data,isWarningInModal,thiz,type){
		var messages = "";
		
		if(data != undefined){
			if (!$$module.isNotEmpty(data.emailRecipients) || data.emailRecipients.length == 0) {
				messages += VALIDATION_COMPONENT_ROW;
				messages += $.qp.getModuleMessage('sc.blogiccomponent.0191');
			}
		}else{
			messages += $.qp.getModuleMessage('sc.blogiccomponent.0100');
		}
		if(messages.length > 0){
			if(validationType == BLOGIC_CHECK){
				messages = $$module.buildHeaderOfNode(messages,data,type);
			}
		}
		return messages;
	}
	
	$$module.validationDownloadFileComp = function(validationType,data,isWarningInModal,thiz,type){
		var messages = "";
		
		if(data != undefined){
			if ($$module.isNotEmpty(data.parameterId)){
				var infor = getInformationOfParameter(data.parameterId);
				if(infor == undefined){
					messages += VALIDATION_COMPONENT_ROW;
					messages +=  $.qp.getMessage('err.sys.0037').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0163'));
				}else if(infor.output03 != "1"|| eval(infor.output04) == false){
					messages += VALIDATION_COMPONENT_ROW;
					messages += $.qp.getModuleMessage('err.blogiccomponent.0193');
				}
			}else{
				messages += VALIDATION_COMPONENT_ROW;
				messages +=  $.qp.getMessage('err.sys.0025').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0163'));
			}
		}else{
			messages += VALIDATION_COMPONENT_ROW;
			messages += $.qp.getModuleMessage('sc.blogiccomponent.0100');
		}
		if(messages.length > 0){
			if(validationType == BLOGIC_CHECK){
				messages = $$module.buildHeaderOfNode(messages,data,type);
			}
		}
		return messages;
	}
	
	/*
	 * check value is not undefined, null and size > 0
	 */
	$$module.isNotEmpty = function(data) {
		if(data == undefined || data == null || $.trim(data).length == 0){
			return false;
		}else{
			return true;
		}
	}
	/*
	 * get node label
	 */
	$$module.getNodeLabel = function(type) {
		var message = "";
		switch (type.toString()) {
			case "2":
				message = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0002') + CLOSE_PARENTHESIS;
				break;
			case "3":
				message = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0003') + CLOSE_PARENTHESIS;
				break;
			case "4":
				message = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0004') + CLOSE_PARENTHESIS;
				break;
			case "5":
				message = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0005') + CLOSE_PARENTHESIS;
				break;
			case "6":
				message = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0006') + CLOSE_PARENTHESIS;
				break;
			case "7":
				message = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0007') + CLOSE_PARENTHESIS;
				break;
			case "8":
				message = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0008') + CLOSE_PARENTHESIS;
				break;
			case "9":
				message = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0009') + CLOSE_PARENTHESIS;
				break;
			case "10":
				message = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0010') + CLOSE_PARENTHESIS;
				break;
			case "11":
				message = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0011') + CLOSE_PARENTHESIS;
				break;
			case "12":
				message = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0012') + CLOSE_PARENTHESIS;
				break;
			case "13":
				message = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0013') + CLOSE_PARENTHESIS;
				break;
			case "14":
				message = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0126') + CLOSE_PARENTHESIS;
				break;
			case "15":
				message = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0125') + CLOSE_PARENTHESIS;
				break;
			case "16":
				message = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0081') + CLOSE_PARENTHESIS;
				break;
			case "17":
				message = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0026') + CLOSE_PARENTHESIS;
				break;
			case "18":
				message = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0127') + CLOSE_PARENTHESIS;
				break;
			case "19":
				message = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0128') + CLOSE_PARENTHESIS;
				break;
			case "20":
				message = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0143') + CLOSE_PARENTHESIS;
				break;
			case "21":
				message = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0144') + CLOSE_PARENTHESIS;
				break;
			case "22":
				message = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0148') + CLOSE_PARENTHESIS;
				break;
			case "23":
				message = OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0162') + CLOSE_PARENTHESIS;
				break;
			default:
				break;
		}
		return message;
	}
	
	/**
	 * validate parameter and assign type
	 */
	$$module.validateCastDataTypeRule = function( assignDatatype, assignArrayFlag, parameterDataType, parameterArrayFlag){
		assignDatatype = Number(assignDatatype);
		parameterDataType = Number(parameterDataType);
		assignDatatype = $$module.setObjectTypeForEntityCommonExternal(Number(assignDatatype));
		parameterDataType =  $$module.setObjectTypeForEntityCommonExternal(Number(parameterDataType));
		
		if (assignArrayFlag == false) {
			assignArrayFlag = "false";
		}
		if (assignArrayFlag == true) {
			assignArrayFlag = "true";
		}
		if (parameterArrayFlag == false) {
			parameterArrayFlag = "false";
		}
		if (parameterArrayFlag == true) {
			parameterArrayFlag = "true";
		}
		
		if (assignArrayFlag != parameterArrayFlag) {
			return false;
		}
		
		return $$module.validateAssignAndPameterType(Number(assignDatatype), Number(parameterDataType));
	};
	
	/**
	 * validate parameter and assign type
	 */
	$$module.validateBusinessRule = function( assignDatatype, assignArrayFlag, parameterDataType, parameterArrayFlag){
		assignDatatype = Number(assignDatatype);
		parameterDataType = Number(parameterDataType);
		assignDatatype = $$module.setObjectTypeForEntityCommonExternal(Number(assignDatatype));
		parameterDataType =  $$module.setObjectTypeForEntityCommonExternal(Number(parameterDataType));
		return $$module.validateAssignAndPameterType(Number(assignDatatype), Number(parameterDataType));
	};
	
	$$module.validateAssignAndPameterType = function(assignDatatype, parameterDataType){
		var validate = false;
		switch(assignDatatype){
			case(DATA_TYPE.BYTE):
			case(DATA_TYPE.CHAR):
			case(DATA_TYPE.BOOLEAN):	
			case(DATA_TYPE.OBJECT):		
				validate =  $$module.validateAssignTypeUnique(assignDatatype, parameterDataType);
				break;
			case(DATA_TYPE.SHORT):
				validate =  $$module.validateAssignDataShortType(parameterDataType);
				break;
			case(DATA_TYPE.INTEGER):
				validate =  $$module.validateAssignDataIntType(parameterDataType);
				break;
			case(DATA_TYPE.LONG):
				validate =  $$module.validateAssignDataLongType(parameterDataType);
				break;
			case(DATA_TYPE.FLOAT):
				validate =  $$module.validateAssignDataFloatType(parameterDataType);
				break;
			case(DATA_TYPE.DOUBLE):
				validate =  $$module.validateAssignDataDoubleType(parameterDataType);
				break;
			case(DATA_TYPE.STRING):
				validate =  $$module.validateAssignDataStringType(parameterDataType);
				break;
			case(DATA_TYPE.BIGDECIMAL):
				validate =  $$module.validateAssignDataBigDecimalType(parameterDataType);
				break;
			case(DATA_TYPE.TIMESTAMP):
				validate =  $$module.validateAssignDataTimeStampType(parameterDataType);
				break;
			case(DATA_TYPE.DATETIME):
				validate =  $$module.validateAssignDataDateTimeType(parameterDataType);
				break;
			case(DATA_TYPE.TIME):
				validate =  $$module.validateAssignDataTimeType(parameterDataType);
				break;
			case(DATA_TYPE.DATE):
				validate =  $$module.validateAssignDataDateType(parameterDataType);
				break;
		}
		return validate;
	}
	
	 $$module.validateAssignTypeObject = function(parameterDataType){
		 var validate = false;
		 switch(parameterDataType){
		 	case(DATA_TYPE.OBJECT):	
			case(DATA_TYPE.ENTITY):
			case(DATA_TYPE.COMMON_OBJECT):
			case(DATA_TYPE.EXTERNAL_OBJECT):
				validate = true;
		 }
		 return validate;
	 }
	
	$$module.setObjectTypeForEntityCommonExternal = function(dataType){
		if(dataType == DATA_TYPE.ENTITY || dataType == DATA_TYPE.COMMON_OBJECT || dataType == DATA_TYPE.EXTERNAL_OBJECT){
			return DATA_TYPE.OBJECT;
		}
		return dataType;
	}
	
	$$module.validateAssignDataDateType =  function(parameterDataType){
		var validate = false;
		switch(parameterDataType){
			case(DATA_TYPE.LONG):
			case(DATA_TYPE.DATE):
				validate = true;
				break;
		}
		return validate;
	};
	
	$$module.validateAssignDataTimeType =  function(parameterDataType){
		var validate = false;
		switch(parameterDataType){
			case(DATA_TYPE.LONG):
			case(DATA_TYPE.TIME):
				validate = true;
				break;
		}
		return validate;
	};
	
	$$module.validateAssignDataDateTimeType = function(parameterDataType){
		var validate = false;
		switch(parameterDataType){
			case(DATA_TYPE.LONG):
			case(DATA_TYPE.DATETIME):
			case(DATA_TYPE.TIME):
			case(DATA_TYPE.DATE):	
				validate = true;
				break;
		}
		return validate;
	};
	
	$$module.validateAssignDataTimeStampType = function(parameterDataType){
		var validate = false;
		switch(parameterDataType){
			case(DATA_TYPE.LONG):
			case(DATA_TYPE.TIMESTAMP):
			case(DATA_TYPE.DATETIME):
			case(DATA_TYPE.TIME):
			case(DATA_TYPE.DATE):	
				validate = true;
				break;
		}
		return validate;
	};
	
	$$module.validateAssignDataBigDecimalType = function(parameterDataType){
		var validate = false;
		switch(parameterDataType){
		case(DATA_TYPE.BYTE):
		case(DATA_TYPE.SHORT):
		case(DATA_TYPE.INTEGER):
		case(DATA_TYPE.LONG):
		case(DATA_TYPE.FLOAT):
		case(DATA_TYPE.DOUBLE):
		case(DATA_TYPE.BIGDECIMAL):	
			validate = true;
			break;
		}
		return validate;
	};
	
	$$module.validateAssignDataStringType = function(parameterDataType){
		var validate = false;
		switch(parameterDataType){
			case(DATA_TYPE.BYTE):
			case(DATA_TYPE.SHORT):
			case(DATA_TYPE.INTEGER):
			case(DATA_TYPE.LONG):
			case(DATA_TYPE.FLOAT):
			case(DATA_TYPE.DOUBLE):
			case(DATA_TYPE.CHAR):
			case(DATA_TYPE.BOOLEAN):
			case(DATA_TYPE.STRING):
			case(DATA_TYPE.BIGDECIMAL):
			case(DATA_TYPE.TIMESTAMP):
			case(DATA_TYPE.DATETIME):
			case(DATA_TYPE.TIME):
			case(DATA_TYPE.DATE):
				validate = true;
				break;
		}
		return validate;
	};
	
	$$module.validateAssignDataDoubleType = function(parameterDataType){
		var validate = false;
		switch(parameterDataType){
			case(DATA_TYPE.BYTE):
			case(DATA_TYPE.SHORT):
			case(DATA_TYPE.INTEGER):
			case(DATA_TYPE.LONG):
			case(DATA_TYPE.FLOAT):
			case(DATA_TYPE.DOUBLE):	
				validate = true;
				break;
		}
		return validate;
	};
	
	$$module.validateAssignDataFloatType = function(parameterDataType){
		var validate = false;
		switch(parameterDataType){
			case(DATA_TYPE.BYTE):
			case(DATA_TYPE.SHORT):
			case(DATA_TYPE.INTEGER):
			case(DATA_TYPE.LONG):
			case(DATA_TYPE.FLOAT):
				validate = true;
				break;
		}
		return validate;
	}
	
	$$module.validateAssignDataLongType = function(parameterDataType){
		var validate = false;
		switch(parameterDataType){
			case(DATA_TYPE.BYTE):
			case(DATA_TYPE.SHORT):
			case(DATA_TYPE.INTEGER):
			case(DATA_TYPE.LONG):
			case(DATA_TYPE.TIMESTAMP):
			case(DATA_TYPE.DATETIME):
			case(DATA_TYPE.TIME):
			case(DATA_TYPE.DATE):	
				validate = true;
				break;
		}
		return validate;
	};
	
	$$module.validateAssignDataIntType =function(parameterDataType){
		var validate = false;
		switch(parameterDataType){
			case(DATA_TYPE.BYTE):
			case(DATA_TYPE.SHORT):
			case(DATA_TYPE.INTEGER):
				validate = true;
				break;
		}
		return validate
	};
	
	$$module.validateAssignDataShortType = function(parameterDataType){
		var validate = false;
		switch(parameterDataType){
			case(DATA_TYPE.BYTE):
			case(DATA_TYPE.SHORT):
				validate = true;
				break;
		}
		return validate;
	};
	
	$$module.validateAssignTypeUnique = function(assignDatatype, parameterDataType){
		return assignDatatype ==  parameterDataType ? true : false;
	};
	
	
	/*
	 * build header
	 */
	$$module.buildHeaderOfNode = function(messages,data,type) {
		var tempMessage = messages;
		messages = VALIDATION_COMPONENT_ROW;
		messages += VALIDATION_COMPONENT_SPLIT;
		messages += VALIDATION_COMPONENT_ROW;
		messages += $$module.getNodeLabel(type) + "";
		messages += ($$module.isNotEmpty(data.label) ? data.label : "");
		messages += tempMessage;
		messages += VALIDATION_COMPONENT_ROW;
		messages += VALIDATION_COMPONENT_SPLIT;
		return messages
	}
	
	$$module.validateDuplicate = function(table, inputName, inputLabel, tab) {
		var $Inputs = $(table).find("[name*=" + inputName + "]").not('label');
		var messages="";
		$Inputs.each(function(index) {
			var rgroup = ($(this).closest("tr").attr("data-ar-rgroup") == undefined || $(this).closest("tr").attr("data-ar-rgroup") == null ) ? "" : $(this).closest("tr").attr("data-ar-rgroup");
			for(var j=index+1;j<$Inputs.length;j++) {
				var rgroupCheck = ($Inputs.eq(j).closest("tr").attr("data-ar-rgroup") == undefined || $Inputs.eq(j).closest("tr").attr("data-ar-rgroup") == null ) ? "" : $Inputs.eq(j).closest("tr").attr("data-ar-rgroup");
				if($.trim($(this).val()) != undefined && $.trim($(this).val()) != "" 
						&& $.trim($Inputs.eq(j).val()) != undefined && $.trim($Inputs.eq(j).val()) != ""
						&& $.trim($(this).val()) == $.trim($Inputs.eq(j).val())
						&& rgroup.trim() == rgroupCheck.trim()) {
					messages += "<br/>";
					messages +=$.qp.getMessage('err.sys.0041').replace("{0}","[" + tab +"] " + inputLabel).replace("{1}",(j+1));
					$(this).closest("tr").addClass("qp-bdesign-tr-warning");
					break;
				}
			}
		});
		return messages;
	}
	
	$$module.dataAllowOfIndex = function(dataType){
		var check = false;
		switch (eval(dataType)) {
		case DATA_TYPE.BYTE:
		case DATA_TYPE.SHORT:
		case DATA_TYPE.INTEGER:
		case DATA_TYPE.CHAR:
			check = true;
			break;
		}
		return check;
	}
	
	/*
	 * validate the navigator component
	 */
	$$module.validationExceptionComp = function(validationType,data,isWarningInModal,thiz,type) {
		var messages = "";
		if(data != undefined){
			if ((data.exceptionToType == 1 || data.exceptionToType == 2) && !$$module.isNotEmpty(data.exceptionToId)){
				messages += VALIDATION_COMPONENT_ROW;
				messages += $.qp.getMessage('err.sys.0025').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0198'));
			}
			// remove because Delete complete case.
//			else if(!$$module.isNotEmpty(data.exceptionToIdRefer)){
//				messages += VALIDATION_COMPONENT_ROW;
//				messages += $.qp.getMessage('err.sys.0037').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0104'));
//			}
			else{
				if (data.parameterInputBeans != undefined && data.parameterInputBeans != null){
					for(var i=0; i < data.parameterInputBeans.length; i++){
						var currentInput = data.parameterInputBeans[i];
						var valid = true;
						//check deleted row
						if(currentInput.impactStatus == IMPACT_STATUS.DELETED){
							messages += VALIDATION_COMPONENT_ROW;
							messages += $.qp.getModuleMessage("err.blogiccomponent.0014").replace("{0}", i + 1);
							valid = false;
						}else if(currentInput.impactStatus == IMPACT_STATUS.ADDED){
							messages += VALIDATION_COMPONENT_ROW;
							messages += $.qp.getModuleMessage("err.blogiccomponent.0022").replace("{0}", i + 1);
							valid = false;
						}else{
							if(!$$module.isNotEmpty(currentInput.parameterId)){
//								messages += VALIDATION_COMPONENT_ROW;
//								messages +=  $.qp.getMessage('err.sys.0077'].replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0106')).replace("{1}", i + 1);
//								valid = false;
							}else{
								//check mapping type
								var infor = getInformationOfParameter(currentInput.parameterId);
								if(infor != undefined){
									var dataType = infor.output03;
									var arrayFlg = infor.output04;
									if(!!currentInput.lstParameterIndex && currentInput.lstParameterIndex.length > 0){
										arrayFlg = false;
									}
									if(!$$module.validateBusinessRule(currentInput.dataTypeRefer,currentInput.arrayFlgRefer,dataType,arrayFlg)){
										messages += VALIDATION_COMPONENT_ROW;
										var dataTypeStr = CL_BD_DATATYPE[currentInput.dataTypeRefer];
										if(eval(currentInput.arrayFlgRefer)){
											dataTypeStr += "[]";
										}
										messages +=  $.qp.getMessage('err.sys.0124').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0105')).replace("{1}", i + 1).replace("{2}", dataTypeStr);
										valid = false;
									}
								}else{
									messages += VALIDATION_COMPONENT_ROW;
									messages +=  $.qp.getMessage('err.sys.0123').replace("{0}",$.qp.getModuleMessage('sc.blogiccomponent.0106')).replace("{1}", i + 1);
									valid = false;
								}
							}
						}
						//show error
						if(!valid && isWarningInModal){
							$(thiz).closest("div.modal-dialog").find("#tbl-exception-inputbean").find("tbody").find("tr:eq("+i+")").addClass("qp-bdesign-tr-warning");
						}else{
							$(thiz).closest("div.modal-dialog").find("#tbl-exception-inputbean").find("tbody").find("tr:eq("+i+")").removeClass("qp-bdesign-tr-warning");
						}
					}
				}
			}
		}else{
			messages += $.qp.getModuleMessage('sc.blogiccomponent.0100');
		}
		if(messages.length > 0){
			if(validationType == BLOGIC_CHECK){
				messages = $$module.buildHeaderOfNode(messages,data,type);
			}
		}
		return messages;
	}
	
	return $$module;
})(jQuery.namespace('$.qp.businessdesign.validaionComponent'));