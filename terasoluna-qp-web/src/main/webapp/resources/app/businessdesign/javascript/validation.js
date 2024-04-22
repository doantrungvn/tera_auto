/**
 * Validation of business design
 * @author quangvd
 */
$.qp.businessdesign.validaion = (function($$module){
	
	$$module.validationForm = function() {
		var message = "";
		message += $$module.validationInputBean("#tbl_inputbean_list_define");
		message += $$module.validationOutputBean("#tbl_outputbean_list_define");
		message += $$module.validationObjectDefinition("#tbl_objectdefinition_list_define");
		return message;
	}
	
	/**
	 * Validation input bean of business logic
	 * @param table
	 * @returns {String}
	 */
	$$module.validationInputBean = function(table) {
		$$module.changeStyleNormal(table);
		var messages="";
		messages += $$module.validateRequired(table,"inputBeanName",BD_TABLE_ITEM_NAME.OBJECT_NAME);
		messages += $$module.validateRequired(table,"inputBeanCode", BD_TABLE_ITEM_NAME.OBJECT_CODE);
		messages += $$module.validateRequired(table,"dataType", BD_TABLE_ITEM_NAME.DATATYPE);
		messages += $$module.validateSpecialChar(table,"inputBeanCode",BD_TABLE_ITEM_NAME.OBJECT_CODE);
//		messages += $$module.validateDuplicate(table,"messageStringAutocomplete", BD_TABLE_ITEM_NAME.OBJECT_NAME);
		messages += $$module.validateDuplicate(table,"inputBeanCode", BD_TABLE_ITEM_NAME.OBJECT_CODE);
		messages += $$module.validateDatatypeInputBean(table,"inputBeanCode", BD_TABLE_ITEM_NAME.OBJECT_CODE);
		return messages;
	}
	
	/**
	 * Validation input bean of business logic
	 * @param table
	 * @returns {String}
	 */
	$$module.validationOutputBean = function(table) {
		$$module.changeStyleNormal(table);
		var messages="";
		messages += $$module.validateRequired(table,"outputBeanName",BD_TABLE_ITEM_NAME.OBJECT_NAME);
		messages += $$module.validateRequired(table,"outputBeanCode", BD_TABLE_ITEM_NAME.OBJECT_CODE);
		messages += $$module.validateRequired(table,"dataType", BD_TABLE_ITEM_NAME.DATATYPE);
		messages += $$module.validateSpecialChar(table,"outputBeanCode",BD_TABLE_ITEM_NAME.OBJECT_CODE);
//		messages += $$module.validateDuplicate(table,"outputBeanName", BD_TABLE_ITEM_NAME.OBJECT_NAME);
		messages += $$module.validateDuplicate(table,"outputBeanCode", BD_TABLE_ITEM_NAME.OBJECT_CODE);
		messages += $$module.validateDatatypeOutputBean(table,"inputBeanCode", BD_TABLE_ITEM_NAME.OBJECT_CODE);
		return messages;
	}
	
	/**
	 * Validation input bean of business logic
	 * @param table
	 * @returns {String}
	 */
	$$module.validationObjectDefinition = function(table) {
		$$module.changeStyleNormal(table);
		var messages="";
		messages += $$module.validateRequired(table,"objectDefinitionName",BD_TABLE_ITEM_NAME.OBJECT_NAME);
		messages += $$module.validateRequired(table,"objectDefinitionCode", BD_TABLE_ITEM_NAME.OBJECT_CODE);
		messages += $$module.validateRequired(table,"dataType", BD_TABLE_ITEM_NAME.DATATYPE);
		messages += $$module.validateSpecialChar(table,"objectDefinitionCode",BD_TABLE_ITEM_NAME.OBJECT_CODE);
//		messages += $$module.validateDuplicate(table,"objectDefinitionName", BD_TABLE_ITEM_NAME.OBJECT_NAME);
		messages += $$module.validateDuplicate(table,"objectDefinitionCode", BD_TABLE_ITEM_NAME.OBJECT_CODE);
		return messages;
	}
	
	/**
	 * Validate required
	 * @param table
	 * @param inputName
	 * @param inputLabel
	 * @returns {String}
	 */
	$$module.validateRequired = function(table, inputName, inputLabel) {
		var isList = true;
		var $Inputs;
		$Inputs = $(table).find("[name$=" + inputName + "]").not("label");
		var messages = "";
		$Inputs.each(function(index) {
			if ($.trim($(this).val()) == '') {
				if (!isList) {
					messages += $.qp.getMessage('err.sys.0025').replace("{0}",inputLabel);
				} else {
					messages += "<br/>";
					messages += $.qp.getMessage('err.sys.0077').replace("{0}",inputLabel).replace("{1}", index + 1);
					$(this).closest("tr").addClass("qp-bdesign-tr-warning");
				}
			}
		});
		return messages;
	};
	
	/**
	 * Validate special char
	 * @param table
	 * @param inputName
	 * @param inputLabel
	 * @returns {String}
	 */
	$$module.validateSpecialChar = function(table, inputName, inputLabel) {
		var messages = "";
		var $Inputs = $(table).find("[name$=" + inputName + "]").not("label,[type='hidden']");
		var flag = false;
		$Inputs.each(function(index) {
			flag = false;
			if ($.trim($(this).val()) !='' && !($.qp.validateIsCode($(this).val()))) {
				flag = true;
			}
			if(flag){
				messages += "<br/>";
//				messages += $.qp.getMessage('err.sys.0094').replace("{0}", inputLabel).replace("{1}", "\\@/:_*?<>|white-space").replace("{2}", index+1);
				messages += $.qp.getMessage('err.sys.0094').replace("{0}", inputLabel).replace("{1}", index+1);
				$(this).closest("tr").addClass("qp-bdesign-tr-warning");
			}
		});
		messages = messages.replace('&quot;', '"').replace('&quot;', '"');
		return messages;
	}
	
	/**
	 * Validate duplicate value
	 * @param table
	 * @param inputName
	 * @param itemType
	 * @returns {String}
	 */
	$$module.validateDuplicate = function(table, inputName, inputLabel) {
		var $Inputs = $(table).find("[name$=" + inputName + "]").not('label');
		var messages="";
		$Inputs.each(function(index) {
			var rgroup = ($(this).closest("tr").attr("data-ar-rgroup") == undefined || $(this).closest("tr").attr("data-ar-rgroup") == null ) ? "" : $(this).closest("tr").attr("data-ar-rgroup");
			for(var j=index + 1;j<$Inputs.length;j++) {
				var rgroupCheck = ($Inputs.eq(j).closest("tr").attr("data-ar-rgroup") == undefined || $Inputs.eq(j).closest("tr").attr("data-ar-rgroup") == null ) ? "" : $Inputs.eq(j).closest("tr").attr("data-ar-rgroup");
				if($.trim($(this).val()) != undefined && $.trim($(this).val()) != "" 
						&& $.trim($Inputs.eq(j).val()) != undefined && $.trim($Inputs.eq(j).val()) != ""
						&& $.trim($(this).val()) == $.trim($Inputs.eq(j).val())
						&& rgroup.trim() == rgroupCheck.trim()) {
					messages += "<br/>";
					messages +=$.qp.getMessage('err.sys.0041').replace("{0}",inputLabel).replace("{1}",(j+1));
					$(this).closest("tr").addClass("qp-bdesign-tr-warning");
					break;
				}
			}
		});
		return messages;
	}
	/*
	 * change normal style  
	 */
	$$module.changeStyleNormal = function(table) {
		$(table).find("tbody").find("tr").each(function (){
			$(this).removeClass("qp-bdesign-tr-warning");
		});
	}
	/*
	 * check format  
	 */
//	$.qp.validateIsCode = function(val) {
//		var REGULAR_EXP_CODE = /^[a-zA-Z]+[_0-9a-zA-Z]{0,49}$/i;
//	    return val.match(REGULAR_EXP_CODE) ? false : true;
//	}
	/**
	 * Validate required
	 * @param table
	 * @param inputName
	 * @param inputLabel
	 * @returns {String}
	 */
	$$module.validateDatatypeInputBean = function(table, inputName, inputLabel) {
		var messages = "";
		$(table).find("tr.ar-dataRow").each(function(index) {
			var dataTypeAssign = $(this).find("select[name$='dataType']").val();
			var flagArrayAssign = $(this).find("input[name$='arrayFlg']").is(":checked");
			var dataTypeParameter = $(this).find("input[name$='dataTypeSession']").val();
			var flagArrayParameter = $(this).find("input[name$='arrayFlagSession']").val();
			
			if (dataTypeAssign == undefined) {
				return;
			}
			
			if (!isEmptyQP(dataTypeParameter) && !isEmptyQP(flagArrayParameter)) {
				if(!$.qp.businessdesign.validaionComponent.validateCastDataTypeRule(dataTypeAssign, flagArrayAssign, dataTypeParameter, flagArrayParameter)){
					messages += "<br/>";
					var dataTypeStr = CL_BD_DATATYPE[dataTypeParameter];
					if(eval(flagArrayParameter)){
						dataTypeStr += "[]";
					}
					messages +=  $.qp.getMessage('err.sys.0124').replace("{0}",$.qp.getModuleMessage("sc.businesslogicdesign.0039")).replace("{1}", index + 1).replace("{2}", dataTypeStr);
	
					$(this).closest("tr").addClass("qp-bdesign-tr-warning");
				}
			}
		});
		return messages;
	};
	/**
	 * Validate required
	 * @param table
	 * @param inputName
	 * @param inputLabel
	 * @returns {String}
	 */
	$$module.validateDatatypeOutputBean = function(table, inputName, inputLabel) {
		var messages = "";
		$(table).find("tr.ar-dataRow").each(function(index) {
			var dataTypeAssign = $(this).find("select[name$='dataType']").val();
			var flagArrayAssign = $(this).find("input[name$='arrayFlg']").is(":checked");
			var dataTypeParameter = $(this).find("input[name$='dataTypeSession']").val();
			var flagArrayParameter = $(this).find("input[name$='arrayFlagSession']").val();
			
			if (dataTypeAssign == undefined) {
				return;
			}
			
			if (!isEmptyQP(dataTypeParameter) && !isEmptyQP(flagArrayParameter)) {
				if(!$.qp.businessdesign.validaionComponent.validateCastDataTypeRule(dataTypeParameter, flagArrayParameter, dataTypeAssign, flagArrayAssign)){
					messages += "<br/>";
					var dataTypeStr = CL_BD_DATATYPE[dataTypeParameter];
					if(eval(flagArrayParameter)){
						dataTypeStr += "[]";
					}
					messages +=  $.qp.getMessage('err.sys.0124').replace("{0}",$.qp.getModuleMessage("sc.businesslogicdesign.0039")).replace("{1}", index + 1).replace("{2}", dataTypeStr);
	
					$(this).closest("tr").addClass("qp-bdesign-tr-warning");
				}
			}
		});
		return messages;
	};
	
	
	return $$module;
})(jQuery.namespace('$.qp.businessdesign.validaion'));