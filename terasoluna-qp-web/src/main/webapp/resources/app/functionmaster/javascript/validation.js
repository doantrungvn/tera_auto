$.namespace("$.qp.common.validation")
$.qp.common.validation = (function($$module){
	
	// Constant definition
	var METHOD_INFOR = {
			METHOD_NM : $.qp.getModuleMessage('sc.functionmaster.0032'),
			IN_NM : $.qp.getModuleMessage('sc.functionmaster.0033'),
			OUT_NM : $.qp.getModuleMessage('sc.functionmaster.0034')
	}
	
	var SCOPE = {
		METHOD_INFO : 0,
		INPUT_TAB : 1,
		OUTPUT_TAB : 2
	};
	
	$$module.validationForm = function(table) {
		var messagesErr = "";
		$$module.changeStyleNormal(table);
		
		$(table).find('table[name="methodInfo"]').each(function(idx) {
			
			var isErr = false;

			// Validate required
			if ($.trim($(this).find('input[name$=".functionMethodName"]').val()) == '') {
				messagesErr += $.qp.getMessage('err.sys.0077').replace("{0}", $.qp.getModuleMessage('sc.functionmaster.0027')).replace("{1}", idx + 1);
				messagesErr += "\r";
				isErr = true;
			}
			
			if ($.trim($(this).find('input[name$=".functionMethodCode"]').val()) == '') {
				messagesErr += $.qp.getMessage('err.sys.0077').replace("{0}", $.qp.getModuleMessage('sc.functionmaster.0028')).replace("{1}", idx + 1);
				messagesErr += "\r";
				if(!isErr) isErr = true;
			}
			
			// Validate special character
			var flag = false;
			if ($.trim($(this).find('input[name$=".functionMethodCode"]').val()) !='' && !($.qp.validateIsCode($(this).find('input[name$=".functionMethodCode"]').val()))) {
				flag = true;
			}
			if(flag) {
				messagesErr += $.qp.getMessage('err.sys.0094').replace("{0}", $.qp.getModuleMessage('sc.functionmaster.0028')).replace("{1}", idx+1);
				messagesErr += "\r";
				messagesErr = messagesErr.replace('&quot;', '"').replace('&quot;', '"');
				if(!isErr) isErr = true;
			}
			
			// Validate duplicate name
			if($.trim($(this).find('input[name$=".functionMethodName"]').val()) != undefined) {
				var methodNameVal = $(this).find('input[name$=".functionMethodName"]').val();
				$(table).find('table[name="methodInfo"]').find('input[name$=".functionMethodName"]').each(function(innerIdx) {
					if(innerIdx !=0 && idx != innerIdx &&  $.trim($(this).val()) != '' &&  $.trim($(this).val()) == $.trim(methodNameVal)) {
						messagesErr += $.qp.getMessage('err.sys.0041').replace("{0}", $.qp.getModuleMessage('sc.functionmaster.0027')).replace("{1}", idx+1);
						messagesErr += "\r";
						if(!isErr) isErr = true;
						return false;
					}
				});
			}	
			// Validate duplicate code
			if($.trim($(this).find('input[name$=".functionMethodCode"]').val()) != undefined) {
				var methodCodeVal = $(this).find('input[name$=".functionMethodCode"]').val();
				$(table).find('table[name="methodInfo"]').find('input[name$=".functionMethodCode"]').each(function(innerIdx) {
					if(innerIdx != 0 && idx != innerIdx && $.trim($(this).val()) != '' && $.trim($(this).val()) == $.trim(methodCodeVal)) {
						messagesErr += $.qp.getMessage('err.sys.0041').replace("{0}", $.qp.getModuleMessage('sc.functionmaster.0028')).replace("{1}", idx+1);
						messagesErr += "\r";
						if(!isErr) isErr = true;
						return false;
					}
				});
			}
			
			if(isErr) $(this).find('input[name$=".functionMethodName"]').closest("tr").addClass("qp-bdesign-tr-warning");
			
			// Validate tab in method information
			messagesErr += $$module.validateInputTab(idx, $(this).find('table[name="tblInput"]'));
			messagesErr += $$module.validateOutputTab(idx, $(this).find('table[name="tblOutput"]'));
			
			isErr = false;
		});

		return messagesErr;
	}
	

	$$module.validateInputTab = function(idxMethod, table) {
		var messagesErr = "";
		// remove waring of tr
		$$module.changeStyleNormal(table);
		messagesErr += $$module.validateRequired(idxMethod, table,"methodInputName", $.qp.getModuleMessage('sc.functionmaster.0035'), SCOPE.INPUT_TAB);
		messagesErr += $$module.validateRequired(idxMethod, table,"methodInputCode", $.qp.getModuleMessage('sc.functionmaster.0036'), SCOPE.INPUT_TAB);
		messagesErr += $$module.validateRequiredDataType(idxMethod, table,"dataType", $.qp.getModuleMessage('sc.functionmaster.0037'), SCOPE.INPUT_TAB);
		messagesErr += $$module.validateSpecialChar(idxMethod, table,"methodInputCode", $.qp.getModuleMessage('sc.functionmaster.0036'), SCOPE.INPUT_TAB);
//		messagesErr += $$module.validateDuplicate(idxMethod, table,"methodInputName", $.qp.getModuleMessage('sc.functionmaster.0035'), SCOPE.INPUT_TAB);
		messagesErr += $$module.validateDuplicate(idxMethod, table,"methodInputCode", $.qp.getModuleMessage('sc.functionmaster.0036'), SCOPE.INPUT_TAB);
		
		return messagesErr;
	};
	
	$$module.validateOutputTab = function(idxMethod, table) {
		var messagesErr = "";
		// remove waring of tr
		$$module.changeStyleNormal(table);
		messagesErr += $$module.validateRequired(idxMethod, table,"methodOutputName", $.qp.getModuleMessage('sc.functionmaster.0035'), SCOPE.OUTPUT_TAB);
		messagesErr += $$module.validateRequired(idxMethod, table,"methodOutputCode", $.qp.getModuleMessage('sc.functionmaster.0036'), SCOPE.OUTPUT_TAB);
		messagesErr += $$module.validateRequiredDataType(idxMethod, table,"dataType", $.qp.getModuleMessage('sc.functionmaster.0037'), SCOPE.OUTPUT_TAB);
		messagesErr += $$module.validateSpecialChar(idxMethod, table,"methodOutputCode", $.qp.getModuleMessage('sc.functionmaster.0036'), SCOPE.OUTPUT_TAB);
//		messagesErr += $$module.validateDuplicate(idxMethod, table,"methodOutputName", $.qp.getModuleMessage('sc.functionmaster.0035'), SCOPE.OUTPUT_TAB);
		messagesErr += $$module.validateDuplicate(idxMethod, table,"methodOutputCode", $.qp.getModuleMessage('sc.functionmaster.0036'), SCOPE.OUTPUT_TAB);
		
		return messagesErr;
	};

	$$module.validateRequired = function(idxMethod, table, inputName, inputLabel, scope) {
		var messages = "";
		var $Inputs;
		
		var ParentName = (SCOPE.INPUT_TAB == scope)?METHOD_INFOR.IN_NM : (SCOPE.OUTPUT_TAB == scope)?METHOD_INFOR.OUT_NM: METHOD_INFOR.METHOD_NM;
		
		$Inputs = $(table).find("[name*=" + inputName + "]").not("label");
		$Inputs.each(function(index) {
			if ($.trim($(this).val()) == '') {
				messages += $.qp.getModuleMessage('err.functionmaster.0066').replace("{0}", METHOD_INFOR.METHOD_NM).replace("{1}", idxMethod+1).replace("{2}", ParentName).replace("{3}", inputLabel).replace("{4}", index + 1);
				messages += "\r";
				$(this).closest("tr").addClass("qp-bdesign-tr-warning");
			}
		});

		return messages;
	};
	
	$$module.validateRequiredDataType = function(idxMethod, table, inputName, inputLabel, scope) {
		var messages = "";
		var $Inputs;
		
		var ParentName = (SCOPE.INPUT_TAB == scope)?METHOD_INFOR.IN_NM : (SCOPE.OUTPUT_TAB == scope)?METHOD_INFOR.OUT_NM: METHOD_INFOR.METHOD_NM;
		
		$Inputs = $(table).find("select[name*=" + inputName + "]").not("label");
		$Inputs.each(function(index) {
			if ($.trim($(this).val()) == '') {
				messages += $.qp.getModuleMessage('err.functionmaster.0066').replace("{0}", METHOD_INFOR.METHOD_NM).replace("{1}", idxMethod+1).replace("{2}", ParentName).replace("{3}", inputLabel).replace("{4}", index + 1);
				messages += "\r";
				$(this).closest("tr").addClass("qp-bdesign-tr-warning");
			}
		});

		return messages;
	};
	
	$$module.validateSpecialChar = function(idxMethod, table, inputName, inputLabel, scope) {
		var messages = "";
		var $Inputs;
		
		var ParentName = (SCOPE.INPUT_TAB == scope)?METHOD_INFOR.IN_NM : (SCOPE.OUTPUT_TAB == scope)?METHOD_INFOR.OUT_NM: METHOD_INFOR.METHOD_NM;
		
		$Inputs = $(table).find("[name*=" + inputName + "]").not("label, [type='hidden']");
		var flag = false;
		$Inputs.each(function(index) {
			flag = false;
			if ($.trim($(this).val()) !='' && !($.qp.validateIsCode($(this).val()))) {
				flag = true;
			}
			if(flag) {
				messages += $.qp.getModuleMessage('err.functionmaster.0067').replace("{0}", METHOD_INFOR.METHOD_NM).replace("{1}", idxMethod+1).replace("{2}", ParentName).replace("{3}", inputLabel).replace("{4}", index+1);
				messages += "\r";
				$(this).closest("tr").addClass("qp-bdesign-tr-warning");
			}
		});
		messages = messages.replace('&quot;', '"').replace('&quot;', '"');

		return messages;
	}

	$$module.validateDuplicate = function(idxMethod, table, inputName, inputLabel, scope) {
		var messages="";
		var $Inputs; 
		
		var ParentName = (SCOPE.INPUT_TAB == scope)?METHOD_INFOR.IN_NM : (SCOPE.OUTPUT_TAB == scope)?METHOD_INFOR.OUT_NM: METHOD_INFOR.METHOD_NM;
		
		$Inputs = $(table).find("[name*=" + inputName + "]").not('label');
		$Inputs.each(function(index) {
			var rgroup = ($(this).closest("tr").attr("data-ar-rgroup") == undefined || $(this).closest("tr").attr("data-ar-rgroup") == null ) ? "" : $(this).closest("tr").attr("data-ar-rgroup");
			for(var j=index + 1;j<$Inputs.length;j++) {
				var rgroupCheck = ($Inputs.eq(j).closest("tr").attr("data-ar-rgroup") == undefined || $Inputs.eq(j).closest("tr").attr("data-ar-rgroup") == null ) ? "" : $Inputs.eq(j).closest("tr").attr("data-ar-rgroup");
				if($.trim($(this).val()) != undefined && $.trim($(this).val()) != "" 
						&& $.trim($Inputs.eq(j).val()) != undefined && $.trim($Inputs.eq(j).val()) != ""
						&& $.trim($(this).val()) == $.trim($Inputs.eq(j).val())
						&& rgroup.trim() == rgroupCheck.trim()) {
					messages +=$.qp.getModuleMessage('err.functionmaster.0068').replace("{0}", METHOD_INFOR.METHOD_NM).replace("{1}", idxMethod+1).replace("{2}", ParentName).replace("{3}", inputLabel).replace("{4}",(j+1));
					messages += "\r";
					$(this).closest("tr").addClass("qp-bdesign-tr-warning");
					break;
				}
			}
		});
		return messages;
	}
	
	$$module.changeStyleNormal = function(table) {
		$(table).find("tbody").find("tr").each(function (){
			$(this).removeClass("qp-bdesign-tr-warning");
		});
	}
	
	return $$module;
})($.namespace("$.qp.common.validation"));

