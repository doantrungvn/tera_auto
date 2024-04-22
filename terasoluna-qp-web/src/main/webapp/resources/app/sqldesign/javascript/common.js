$.qp.common = (function($$module){
	$$module.formSubmitCallback = function(form){
		$(form).find("input#activeTab").val($(form).find(".nav-tabs .active a").attr("href"))
	};
	$$module.initProjectIdParameter = function(){
		if(typeof CURRENT_PROJECT_ID != 'undefined'){
			$("input[selectSqlId^='getAllTableDesignByProjectId']").attr("arg02",CURRENT_PROJECT_ID);
		}
	};
	$$module.setFlag = function() {
		$("#actionDelete").val(true);
	};
	$$module.entityAutocompleteChange = function(event){
		$.qp.ar.removeRow({
			link : event.target,
			removeType : 'onlyDescendants'
		});

		if(event.item){
			var url = CONTEXT_PATH + "/businessdesign/getColumnsOfTableDesign?tableId="+event.item.optionValue+"&r="+Math.random();
			var data = $.qp.getData(url);

			var obj = event.target;
			var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
			$(obj).closest("tr").find("input[name$='Name']").val(event.item.optionLabel);
			$(obj).closest("tr").find("input[name$='Code']").val(event.item.output01);
			$(obj).closest("tr").find("label[name$='Code']").text(event.item.output01);

			if(event.item.optionValue){
				if(data.length>0){
					for(var i=data.length-1;i>=0;i--){
						var newRow = $.qp.ar.addRow({
														container: $(obj).closest("table"),
														link: obj,
														templateId:$(obj).closest("table").attr("data-ar-actionTemplateId").replace("action","child-entity"),
														templateData:{groupId:rgroup},
														position:{anchor:$(obj).closest('tr'),string:'after'}
												});

						$(newRow).find("input[name$='Name']").val(data[i].columnName);
						$(newRow).find("label[name$='Name']").text(data[i].columnName);
						$(newRow).find("input[name$='Code']").val(data[i].columnCode);
						$(newRow).find("label[name$='Code']").text(data[i].columnCode);
						$(newRow).find("input[name$='tableId']").val(data[i].tableDesignId);
						$(newRow).find("input[name$='columnId']").val(data[i].columnId);
						$(newRow).find("input[name$='dataType']").val(data[i].dataType);
						var dataType = CL_SQL_DATATYPE[data[i].dataType];
						if(eval(data[i].arrayFlg)){
							dataType += "[]";
						}
						$(newRow).find("input[name$='isArray']").val(data[i].arrayFlg);
						$(newRow).find("label[name$='dataType']").text(dataType);
						$(newRow).find("input[name$='objectType']").val("5");
					}
				}
			}
		}
	};
	$$module.commonObjectAutocompleteChange = function(event){
		var obj = event.target;
		var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
		var level = 1;
		if(rgroup == undefined || rgroup == ""){
			level = 1;
		}else{
			level = (rgroup.match(/\./g) || []).length + 1;
		}
		if(event.item){
			var url = CONTEXT_PATH + "/commonobjectdefinition/getDetailsCommonObjectDefinition?commonObjectDefinitionId="+event.item.optionValue+"&level="+level+"&r="+Math.random();
			var data = $.qp.getData(url);

			if(typeof isSqlPage != 'undefined' && isSqlPage == true){
				var message = dbMsgSource["sc.sqldesign.0066"].replace("{0}", dbMsgSource["sc.sqldesign.0065"]).replace("{1}", event.item.optionLabel);
				var checkExcute = $$module.notAllowSelectExternalOrCommonContainArrayField(event, data, message);
				if(checkExcute) return
			}
		}
		$.qp.ar.removeRow({
			link : event.target,
			removeType : 'onlyDescendants'
		});
		if(event.item){
			var obj = event.target;
			$(obj).closest("tr").find("input[name$='Name']").val(event.item.optionLabel);
			$(obj).closest("tr").find("input[name$='Code']").val(event.item.output01);
			$(obj).closest("tr").find("label[name$='Code']").text(event.item.output01);
			$(obj).closest("tr").find("input[type=hidden][name$='.moduleId']").val(event.item.output02);
			if(event.item.optionValue){
				$$module.buildCommonObjectAttribute(data,$(obj).closest("tr"));
			}
		}
		$$module.setModuleIdForCommonExternalObjectSql(obj);
	};
	
	$$module.setModuleIdForCommonExternalObjectSql = function(autocomplete){
		var module = $("input[type=hidden][name$='moduleId']:first");
		var checkValidate = $$module.validateAutocompleteBeforeSetModuleId(module, autocomplete);
		if(!checkValidate){
			return;
		}
		$(autocomplete).attr("arg03",$(module).val());
	}

	$$module.validateAutocompleteBeforeSetModuleId = function(module, autocomplete){
		if(module.length == 0 || !$(module).val()){
			return false;
		}
		var isNotGetAutoCommonSql = $(autocomplete).attr("selectsqlid") != "getAutocompleteCommonObjectForSQLDesign" 
									&& $(autocomplete).attr("selectsqlid") != "getAutocompleteExternalObjectForSQLDesign" ;
		if(isNotGetAutoCommonSql){
			return false;
		}
		var isHasModuleId = $(autocomplete).attr("arg03");
		if(isHasModuleId){
			return false;
		}
		return true;
	}
	
	$$module.buildCommonObjectAttribute = function(data,anchor){
		if(data.length == 0){
			return $(anchor);
		}
		var $anchor = $(anchor);
		var defaultGroup = $(anchor).attr("data-ar-rgroupindex");
		var rgroup = defaultGroup;
		var templateId = $(anchor).closest("table").attr("data-ar-actionTemplateId").replace("action","child-entity");
		var $container = $(anchor).closest("table");
		for(var i=0;i<data.length;i++){
			rgroup=defaultGroup;
			if(data[i].groupId){
				for(var j=i-1;j>=0;j--){
					if(data[j].rowAdded && data[j].tableIndex == data[i].groupId){
						rgroup = $(data[j].rowAdded).attr("data-ar-rgroupindex");
						break;
					}
				}
			}
			var newRow = $.qp.ar.addRow({
											container: $container,
											link: $anchor,
											templateId:templateId,
											templateData:{groupId:rgroup},
											position:{anchor:$anchor,string:'after'}
									});

			$(newRow).find("input[name$='Name']").val(data[i].commonObjectAttributeName);
			$(newRow).find("label[name$='Name']").text(data[i].commonObjectAttributeName);
			$(newRow).find("input[name$='Code']").val(data[i].commonObjectAttributeCode);
			$(newRow).find("label[name$='Code']").text(data[i].commonObjectAttributeCode);
			$(newRow).find("input[name$='dataType']").val(data[i].dataType);
			var dataType = CL_SQL_DATATYPE[data[i].dataType];
			if(eval(data[i].arrayFlg)){
				dataType += "[]";
			}
			$(newRow).find("label[name*='dataType']").text(dataType);
			$(newRow).find("input[name*='isArray']").val(data[i].arrayFlg);
			$(newRow).find("input[name$='tableId']").val(data[i].commonObjectDefinitionId);
			$(newRow).find("input[name$='columnId']").val(data[i].commonObjectAttributeId);

			data[i].rowAdded = $(newRow);
			$anchor = $(newRow);
			if(data[i].dataType==16){
				$(newRow).find("input[name$='objectType']").val("0");
				$anchor = $$module.buildCommonObjectAttribute(data[i].commonObjectDefinition.commonObjectAttributes,$anchor);
			} else if(data[i].dataType==17){
				$(newRow).find("input[name$='objectType']").val("1");
				$anchor = $$module.buildExternalObjectAttribute(data[i].externalObjectDefinition.externalObjectAttributes,$anchor);
			} else {
				$(newRow).find("input[name$='objectType']").val("2");
			}
		}
		return $anchor;
	};
	$$module.externalObjectAutocompleteChange = function(event){
		var obj = event.target;
		var rgroup = $(obj).closest("tr").attr("data-ar-rgroupindex");
		var level = 1;
		if(rgroup == undefined || rgroup == ""){
			level = 1;
		}else{
			level = (rgroup.match(/\./g) || []).length + 1;
		}
		if(event.item){
			var url = CONTEXT_PATH + "/externalobjectdefinition/getDetailsExternalObjectDefinition?externalObjectDefinitionId="+event.item.optionValue+"&level="+level+"&r="+Math.random();
			var data = $.qp.getData(url);

			if(typeof isSqlPage != 'undefined' && isSqlPage == true){
				var message = dbMsgSource["sc.sqldesign.0066"].replace("{0}", dbMsgSource["sc.sqldesign.0064"]).replace("{1}", event.item.optionLabel);
				var checkExcute = $$module.notAllowSelectExternalOrCommonContainArrayField(event, data,message);
				if(checkExcute) return
			}
		}
		$.qp.ar.removeRow({
			link : event.target,
			removeType : 'onlyDescendants'
		});
		if(event.item){
			var obj = event.target;
			$(obj).closest("tr").find("input[name$='Name']").val(event.item.optionLabel);
			$(obj).closest("tr").find("input[name$='Code']").val(event.item.output01);
			$(obj).closest("tr").find("label[name$='Code']").text(event.item.output01);
			$(obj).closest("tr").find("input[type=hidden][name$='.moduleId']").val(event.item.output02);
			if(event.item.optionValue){
				$$module.buildExternalObjectAttribute(data,$(obj).closest("tr"));
			}
		}
		$$module.setModuleIdForCommonExternalObjectSql(obj);
	};
	$$module.notAllowSelectExternalOrCommonContainArrayField = function(event,data,message) {
		var checkExcute = false;
		var obj = event.target;
		var arrayFlg =  $$module.checkHasIsArrayFlg(data);
		if(arrayFlg && event.item.output01 != "multipartFile"){
			$.qp.showAlertModal(message);
			$(obj).val('');
			checkExcute = true;
		}
		return checkExcute;
	}
	$$module.checkHasIsArrayFlg = function(data){
		var check = false;
		for(var i = 0; i < data.length; i++){
			if(!!data[i].arrayFlg && data[i].arrayFlg == true && data[i].dataType != 1){
				check = true;
				break;
			}
		}
		return check;
	};

	$$module.buildExternalObjectAttribute = function(data,anchor){
		if(data.length==0){
			return $(anchor) 
		}
		var $anchor = $(anchor);
		var defaultGroup = $(anchor).attr("data-ar-rgroupindex");
		var rgroup = defaultGroup;
		var templateId = $(anchor).closest("table").attr("data-ar-actionTemplateId").replace("action","child-entity");
		var $container = $(anchor).closest("table");
		for(var i=0;i<data.length;i++){
			rgroup=defaultGroup;
			if(data[i].groupId){
				for(var j=i-1;j>=0;j--){
					if(data[j].rowAdded && data[j].tableIndex == data[i].groupId){
						rgroup = $(data[j].rowAdded).attr("data-ar-rgroupindex");
						break;
					}
				}
			}
			var newRow = $.qp.ar.addRow({
											container: $container,
											link: $anchor,
											templateId:templateId,
											templateData:{groupId:rgroup},
											position:{anchor:$anchor,string:'after'}
									});

			$(newRow).find("input[name$='Name']").val(data[i].externalObjectAttributeName);
			$(newRow).find("label[name$='Name']").text(data[i].externalObjectAttributeName);
			$(newRow).find("input[name$='Code']").val(data[i].externalObjectAttributeCode);
			$(newRow).find("label[name$='Code']").text(data[i].externalObjectAttributeCode);
			$(newRow).find("input[name$='dataType']").val(data[i].dataType);
			var dataType = CL_SQL_DATATYPE[data[i].dataType];
			if(eval(data[i].arrayFlg)){
				dataType += "[]";
			}
			$(newRow).find("label[name*='dataType']").text(dataType);
			$(newRow).find("input[name*='isArray']").val(data[i].arrayFlg);
			$(newRow).find("input[name$='tableId']").val(data[i].externalObjectDefinitionId);
			$(newRow).find("input[name$='columnId']").val(data[i].externalObjectAttributeId);
			data[i].rowAdded = $(newRow);
			$anchor = $(newRow);
			if(data[i].dataType==17){
				$anchor = $$module.buildExternalObjectAttribute(data[i].externalObjectDefinition.externalObjectAttributes,$anchor);
			} else {
				$(newRow).find("input[name$='objectType']").val("3");
			}
		}
		return $anchor;
	};
	$$module.objectTypeChange = function(obj,tableId,actionTemplateId) {
		var type = $(obj).val();
		var $Caller = $(obj);
		var $ParentRow = $Caller.closest("tr");
		var rgroupindex = $ParentRow.attr("data-ar-rgroupindex") || "";
		var rgroup = $ParentRow.attr("data-ar-rgroup") || "";
		var newRow;
		var newActionRow;
		var previousValue = $(obj).attr("data-previousValue") || type;
		var code = $(obj).closest("tr").find("input[name$='Code']").val();
		switch(type){
		case '0':
			if(previousValue=="14" || previousValue=="16" || previousValue=="17"){
				$.qp.ar.removeRow({
					link : obj,
					removeType : 'onlyDescendants'
				});
				newRow = $.qp.ar.addRow({
					link : obj,
					tableId : tableId,
					templateData : {
						groupId : rgroup,
						objectCode : code
					},
					position : {
						anchor : $Caller.parents("tr:first"),
						string : "after"
					}
				});
				$(newRow).find("input[name$='mappingColumn']").closest("td").find("*").prop("disabled",true).hide();
				newActionRow = $.qp.ar.addRow({
					link : obj,
					tableId : tableId,
					templateId : $(obj).closest("table").attr("data-ar-actionTemplateId"),
					templateData : {
						groupId : $(newRow).attr("data-ar-rgroupindex"),
					},
					position : {
						anchor : newRow,
						string : "after"
					}
				});

				$.qp.ar.removeRow({
					link : obj,
				});
				obj = $(newRow).find("select[name$='.dataType']");
			} else {
				newRow = $.qp.ar.addRow({
					link : obj,
					tableId : tableId,
					templateId : $(obj).closest("table").attr("data-ar-actionTemplateId"),
					templateData : {
						groupId : rgroupindex,
						objectCode : code
					},
					position : {
						anchor : $Caller.parents("tr:first"),
						string : "after"
					}
				});
				$(obj).closest("td").next("td").find("input[name$='mappingColumn']").closest("td").find("*").prop("disabled",true).hide();
			}

			break;
		case '14':
			if(previousValue=="0"){
				$.qp.ar.removeRow({
					link : obj,
					removeType : 'onlyDescendants'
				});
				newRow = $.qp.ar.addRow({
					link : obj,
					tableId : tableId,
					templateId :  $(obj).closest("table").attr("data-ar-actionTemplateId").replace("action","entity"),
					templateData : {
						groupId : rgroup,
						objectCode : code
					},
					position : {
						anchor : $Caller.parents("tr:first"),
						string : "after"
					}
				});
				$(newRow).find("input[name$='mappingColumn']").closest("td").find("*").prop("disabled",true).hide();
				$.qp.ar.removeRow({
					link : obj,
				});
				obj = $(newRow).find("select[name$='.dataType']");
			} else {
				newRow = $.qp.ar.addRow({
					link : obj,
					tableId : tableId,
					templateId :  $(obj).closest("table").attr("data-ar-actionTemplateId").replace("action","entity"),
					templateData : {
						groupId : rgroup,
						objectCode : code
					},
					position : {
						anchor : $Caller.parents("tr:first"),
						string : "after"
					}
				});
				$(newRow).find("input[name$='mappingColumn']").closest("td").find("*").prop("disabled",true).hide();
				$.qp.ar.removeRow({
					link : obj,
				});
				obj = $(newRow).find("select[name$='.dataType']");
			}

			break;
		case '16':
			if(previousValue=="0"){
				$.qp.ar.removeRow({
					link : obj,
					removeType : 'onlyDescendants'
				});
				newRow = $.qp.ar.addRow({
					link : obj,
					tableId : tableId,
					templateId :  $(obj).closest("table").attr("data-ar-actionTemplateId").replace("action","common"),
					templateData : {
						groupId : rgroup,
						objectCode : code
					},
					position : {
						anchor : $Caller.parents("tr:first"),
						string : "after"
					}
				});
				$(newRow).find("input[name$='mappingColumn']").closest("td").find("*").prop("disabled",true).hide();
				$.qp.ar.removeRow({
					link : obj,
				});
				obj = $(newRow).find("select[name$='.dataType']");
			} else {
				newRow = $.qp.ar.addRow({
					link : obj,
					tableId : tableId,
					templateId :  $(obj).closest("table").attr("data-ar-actionTemplateId").replace("action","common"),
					templateData : {
						groupId : rgroup,
						objectCode : code
					},
					position : {
						anchor : $Caller.parents("tr:first"),
						string : "after"
					}
				});
				$(newRow).find("input[name$='mappingColumn']").closest("td").find("*").prop("disabled",true).hide();
				$.qp.ar.removeRow({
					link : obj,
				});
				obj = $(newRow).find("select[name$='.dataType']");

			}

			break;
		case '17':
			if(previousValue=="0"){
				$.qp.ar.removeRow({
					link : obj,
					removeType : 'onlyDescendants'
				});
				newRow = $.qp.ar.addRow({
					link : obj,
					tableId : tableId,
					templateId :  $(obj).closest("table").attr("data-ar-actionTemplateId").replace("action","external"),
					templateData : {
						groupId : rgroup,
						objectCode : code
					},
					position : {
						anchor : $Caller.parents("tr:first"),
						string : "after"
					}
				});
				$(newRow).find("input[name$='mappingColumn']").closest("td").find("*").prop("disabled",true).hide();
				$.qp.ar.removeRow({
					link : obj,
				});
				obj = $(newRow).find("select[name$='.dataType']");
			} else {
				newRow = $.qp.ar.addRow({
					link : obj,
					tableId : tableId,
					templateId :  $(obj).closest("table").attr("data-ar-actionTemplateId").replace("action","external"),
					templateData : {
						groupId : rgroup,
						objectCode : code
					},
					position : {
						anchor : $Caller.parents("tr:first"),
						string : "after"
					}
				});
				$(newRow).find("input[name$='mappingColumn']").closest("td").find("*").prop("disabled",true).hide();
				$.qp.ar.removeRow({
					link : obj,
				});
				obj = $(newRow).find("select[name$='.dataType']");
			}

			break;
		default:

			if(previousValue=="0"){
				$.qp.ar.removeRow({
					link : obj,
					removeType : 'onlyDescendants'
				});

				$(obj).closest("td").next("td").find("input[name$='mappingColumn']").closest("td").find("*").prop("disabled",false).show();
			} else if(previousValue=="14" || previousValue=="16" || previousValue=="17"){
				$.qp.ar.removeRow({
					link : obj,
					removeType : 'onlyDescendants'
				});
				newRow = $.qp.ar.addRow({
					link : obj,
					tableId : tableId,
					templateData : {
						groupId : rgroup,
						objectCode : code
					},
					position : {
						anchor : $Caller.parents("tr:first"),
						string : "after"
					}
				});
				$.qp.ar.removeRow({
					link : obj,
				});
				obj = $(newRow).find("select[name$='.dataType']");
			}
			break;
		}
		$(obj).val(type);
		$(obj).attr("data-previousValue",type);

		//Validate mapping column with data type
		var autoComplete = $(obj).closest("tr").find("input[type=text][name$='.mappingColumnAutocomplete']");
		if(!!$.qp.sqlbuilder){
			$.qp.sqlbuilder.validateMappingColumn(autoComplete);
		}
		//Enable check is array for data type is byte and opposite with other data type
		$$module.allowCheckIsArrayForByteData(obj);
		if(tableId == "outputForm-advanced-sql" || tableId == "outputForm-sql-builder"){
			$$module.allowIsArrayOutputSqlDesign(obj);
		}
		//Set moduleId for autocomplete
		if(type == 16 || type == 17){
			$(newRow).find("input[name$='tableIdAutocomplete']").attr("arg03", $("input[name = 'sqlDesignForm.moduleId']").val());
		}
	};
	
	$$module.allowIsArrayOutputSqlDesign = function(obj){
		var checkbox = $(obj).parent().find("span.input-group-addon>input[type=checkbox]");
		var dataType = $(obj).val();
		if(eval(dataType) == 1){
			$$module.showAndEnableCheckboxIsArray(checkbox);
		}else{
			$$module.hideAndDisableCheckboxIsArray(checkbox);
		}
	}
	
	$$module.hideAndDisableCheckboxIsArray = function(checkbox){
		var span = $(checkbox).closest("span");
		$(span).hide();
		$(checkbox).prop("disabled",true);
	}

	$$module.showAndEnableCheckboxIsArray = function(checkbox){
		var span = $(checkbox).closest("span");
		$(span).show();
		$(checkbox).prop("disabled",false);
	}
	
	$$module.allowCheckIsArrayForByteData = function(selectbox){
		var checkbox = $(selectbox).closest("tr").find("input[type='checkbox'][name$='.isArray']");
		if($(selectbox).val() == '1'){
			if(!!$.qp.sqlbuilder){
				$.qp.sqlbuilder.allowCheckArrayForByteDataType(checkbox);
			}
		}else{
			if(!!$.qp.sqlbuilder){
				$.qp.sqlbuilder.enableCancelCheckBoxArray(checkbox);
			}
		}
	}

	$$module.buildInputList = function(inputForm,select){
		var $InputForm = $(inputForm);
		var $Dest = $(select);
		var inputSource =[];
		$InputForm.find(">tbody>tr.ar-dataRow").each(function(){
			var groupIndex = $(this).attr("data-ar-rgroupindex");
			var dataType = $(this).find("[name$='dataType']").val();
			var separatedIndexes = groupIndex.split(".");
			var buildIndex='';
			var objectPath='';
			var objectCode = '';
			//objectPath = $(this).find("[name$='.sqlDesignInputCode']").val()
			for(var i=0;i<separatedIndexes.length;i++){
				buildIndex = (!buildIndex?separatedIndexes[i]:buildIndex+"."+separatedIndexes[i]);
				objectCode = $InputForm.find(">tbody>tr.ar-dataRow[data-ar-rgroupindex='"+buildIndex+"'] [name$='.sqlDesignInputCode']").val();
				objectPath = (!objectPath?objectCode:objectPath+"."+objectCode);
			}
			if(dataType != 0 && dataType != 14 && dataType != 16 && dataType != 17){

				if(objectPath){
					inputSource.push([groupIndex,objectPath]);
				}
			}
		});
		if($Dest.is("select")){
			$Dest.each(function(){
				var $SelectedOption =  $(this).find("option:selected");
				var selectedValue = $SelectedOption.val();
				var selectedText = $SelectedOption.text();
				$(this).find("option:gt(0)").remove();
				for(var key in inputSource){
					$(this).append("<option value='"+inputSource[key][0]+"' >"+inputSource[key][1]+"</option>");
				}
				if($Dest.find("option[value='"+selectedValue+"']").text() == selectedText){
					$(this).val(selectedValue);
				}
			});
		}
		return inputSource;
	};

	$$module.buildInputObjectList = function(inputForm){
		var $InputForm = $(inputForm);
		var objectGraph={};
		var inputSource =[];
		$InputForm.find(">tbody>tr.ar-dataRow").each(function(){
			var groupIndex = $(this).attr("data-ar-rgroupindex");
			var separatedIndexes = groupIndex.split(".");
			var buildIndex='';
			var objectPath='';
			var objectCode = '';
			for(var i=0;i<separatedIndexes.length;i++){
				buildIndex = (!buildIndex?separatedIndexes[i]:buildIndex+"."+separatedIndexes[i]);
				objectCode = $InputForm.find(">tbody>tr.ar-dataRow[data-ar-rgroupindex='"+buildIndex+"'] [name$='.sqlDesignInputCode']").val();
				objectPath = (!objectPath?objectCode:objectPath+"."+objectCode);
			}
			if(objectPath){
				inputSource.push([groupIndex,objectPath]);
				$$module.namespace(objectPath,objectGraph)
			}
		});

		return objectGraph;
	};
	$$module.initAddButtonRow = function(tableId,actionTemplateId){
		if(tableId){
			var groups = [];
			var $table = $("#"+tableId);
			$table.find(">tbody>tr").each(function(){
				var rgroup = $(this).attr("data-ar-rgroup");
				var $row = $(this);
				var $Caller;
				if($.inArray(rgroup,groups)==-1 && rgroup){
					var parentDataType = $table.find(">tbody>tr[data-ar-rgroupindex='"+rgroup+"'] [name$='.dataType']").val();
					var parentDesignType = $table.find(">tbody>tr[data-ar-rgroupindex='"+rgroup+"'] [name$='.designType']").val();
					if(parentDataType=='0' && parentDesignType =='0') {
						$Caller = $table.find(">tbody>tr[data-ar-rgroup"+(!rgroup?"='":"^='"+rgroup)+"']:last");
						$.qp.ar.addRow({link:$Caller,tableId:tableId,templateId:actionTemplateId,templateData:{ groupId:rgroup},position:{anchor:$Caller,string:"after"} });
						groups.push(rgroup);
					}
				}
				if($row.find("[name$='.dataType']").val()=="0" && $row.find("[name$='.designType']").val()=="0"){
					rgroup = $row.attr("data-ar-rgroupindex").trim();
					$Caller = $table.find(">tbody>tr[data-ar-rgroup"+(!rgroup?"='":"^='"+rgroup)+"']:last");
					if($Caller.length==0){
						$Caller = $(this);
						$.qp.ar.addRow({link:$Caller,tableId:tableId,templateId:actionTemplateId,templateData:{ groupId:rgroup},position:{anchor:$Caller,string:"after"} });
						groups.push(rgroup);
					}
				}
			});
		}
	};
	$$module.initRestyleRow = function(tableId){
		if(tableId){
			var groups = [];
			var $table = $("#"+tableId);
			$table.find(">tbody>tr.ar-dataRow").each(function(){
				$$module.restyleRow(this);
			});
		}
	};

	$$module.restyleRow = function(row){
		var $row = $(row);
		var paddingWidth =  $row.closest("table").find(">thead th:first").outerWidth();
		var _stylePadding = {
				'width':paddingWidth +"px",
				'height': '100%',
				'border-right': 'solid 1px #ddd',
				'text-align': 'center',
				'padding' : '5px',
				'float': 'left'
		};
		var level = ($row.attr("data-ar-rgroupindex") || "").split(".").length;
		if(level>2){
			$(row).find("select[name$='.dataType'] option[value=0],select[name$='.dataType'] option[value='16'],select[name$='.dataType'] option[value='17']").remove();
		}
		var $paddingDiv = $("<div class=\"ar-paddingDiv\"></div>").css(_stylePadding);

		if($row.hasClass("ar-dataRow")){
			var $inputDiv = $row.find("span.ar-groupIndex").closest("div").next();
			$inputDiv.closest("div").css("padding-top",$inputDiv.closest("td").css("padding-top"));
			$inputDiv.closest("div").css("padding-left",$inputDiv.closest("td").css("padding-left"));
			$inputDiv.closest("div").css("padding-right",$inputDiv.closest("td").css("padding-right"));
			$inputDiv.closest("div").css("padding-bottom",$inputDiv.closest("td").css("padding-bottom"));
			$inputDiv.closest("td").css("padding","0px");
			$inputDiv.prev().css(_stylePadding);
			$inputDiv.prev().outerWidth(paddingWidth)
			for(var i=1;i<level;i++){
				$inputDiv.prev().before($paddingDiv);
				$paddingDiv = $paddingDiv.clone();
			}
			$inputDiv.outerWidth($inputDiv.closest("td").outerWidth()-2-paddingWidth*i);
		} else {
			var $inputDiv = $row.find("a").closest("div");
			$inputDiv.closest("div").css("padding-top",$inputDiv.closest("td").css("padding-top"));
			$inputDiv.closest("div").css("padding-left",$inputDiv.closest("td").css("padding-left"));
			$inputDiv.closest("div").css("padding-right",$inputDiv.closest("td").css("padding-right"));
			$inputDiv.closest("div").css("padding-bottom",$inputDiv.closest("td").css("padding-bottom"));
			$inputDiv.closest("td").css("padding","0px");
			for(var i=1;i<level;i++){
				$inputDiv.before($paddingDiv);
				$paddingDiv = $paddingDiv.clone();
			}
		}
	};
	$$module.namespace = function(objectPath, rootObj) {
		var a = arguments, o = null, i, j, d;
		if(objectPath){
			d = objectPath.split(".");
			o = rootObj;
			for (j = 0; j < d.length; j = j + 1) {
				o[d[j]] = o[d[j]] || {};
				o = o[d[j]];
			}
		}
		return o;
	};
	$$module.initTabs = function(){

		$("a[href='#tab-input']").on('shown.bs.tab', function (e) {
			$.qp.common.initAddButtonRow("inputForm","inputForm-action-l1-template");
			$.qp.common.initRestyleRow("inputForm");

			$(this).off('shown.bs.tab');
		})
		$("a[href='#tab-output']").on('shown.bs.tab', function (e) {
			$.qp.common.initRestyleRow("outputForm");

			$.qp.common.initAddButtonRow("outputForm-sql-builder","outputForm-action-l0-template");
			$.qp.common.initRestyleRow("outputForm-sql-builder");

			$.qp.common.initAddButtonRow("outputForm-advanced-sql","outputForm-action-l1-template");
			$.qp.common.initRestyleRow("outputForm-advanced-sql");
			$(this).off('shown.bs.tab');
		})
		$("a[href='"+$("input#activeTab").val()+"']").tab('show');
	};

	$$module.init = function(){
		$(function(){
			$$module.initProjectIdParameter();
			$(".qp-form-control-label").each(function(){
				$(this).attr("title",$(this).text());
			});
			$("[data-toggle='tooltip']").tooltip();
		});
		$(function(){
			$("#autocompleteForm1").find("input[name='autocompleteForm.moduleIdAutocomplete']").attr("arg01",CURRENT_PROJECT_ID);

			$("#autocompleteForm2").find(">tbody>tr:gt(0)").each(function(){
				$(this).find("input[name$='ColumnIdAutocomplete']").attr("arg01",$(this).find("input[name$='TableId']").val());
			});

			$("#qp-autocomplete #inputForm").find("select[name$='.dataType'] option[value='0'],select[name$='.dataType'] option[value='14'],select[name$='.dataType'] option[value='16'],select[name$='.dataType'] option[value='17']").remove();
			//$("#qp-sqldesign [id^='outputForm'").find("select[name$='.dataType'] option[value='16'],select[name$='.dataType'] option[value='17']").remove();
		});
	};

	$$module.changeModuleEvent = function(){
		$$module.clearExternalCommonObject();
		$$module.setModuleIdForExternalCommonObject();
	}
	
	$$module.clearExternalCommonObject = function(){
		$$module.clearExternalObject();
		$$module.clearCommonObject();
	}
	
	$$module.clearCommonObject = function(){
		var autocompleteCommonObjects = $("input[selectsqlid = 'getAutocompleteCommonObjectForSQLDesign']");
		if(autocompleteCommonObjects.length == 0){
			return;
		}
		$$module.removeAutocompleteExternalCommonObject(autocompleteCommonObjects);
	}
	
	$$module.clearExternalObject = function(){
		var autocompleteExternalObjects = $("input[selectsqlid = 'getAutocompleteExternalObjectForSQLDesign']");
		if(autocompleteExternalObjects.length == 0){
			return;
		}
		$$module.removeAutocompleteExternalCommonObject(autocompleteExternalObjects);
	}
	
	$$module.removeAutocompleteExternalCommonObject = function(externalCommonObjects){
		moduleId = $$module.getModuleIdSql();
		$(externalCommonObjects).each(function(){
			$$module.removeEachAutocompleteExternalCommonObject(this,moduleId);
		});
	};
	
	$$module.removeEachAutocompleteExternalCommonObject = function(obj,moduleId){
		var moduleIdOfObject = $$module.getModuleIdForEachOb(obj);
		if(moduleIdOfObject == ""){
			return;
		}
		if($(moduleIdOfObject) == moduleId){
			return;
		}
		var dropdownToggle = $(obj).closest("div").find("span.dropdown-toggle:first");
		$.qp.removeAutocompleteData(dropdownToggle); 
		$$module.clearObjectCodeInputOutput(obj);
	}
	
	$$module.clearObjectCodeInputOutput = function(obj){
		$(obj).closest("tr").find("input[type=text][name$='Code']").val("");
	}
	
	$$module.getModuleIdForEachOb = function(obj){
		var inputModuleId = $(obj).closest("td").find("input[type=hidden][name$='.moduleId']");
		return inputModuleId.length > 0 ? $(inputModuleId).val() : "";
	}
	
	$$module.getModuleIdSql = function(){
		var moduleId = $("input[name='sqlDesignForm.moduleId']");
		if(moduleId.length == 0){
			return "";
		}
		return moduleId.val();
	}
	
	$$module.setModuleIdForExternalCommonObject = function(){
		var moduleId = $$module.getModuleIdSql();
		$("input[selectsqlid = 'getAutocompleteCommonObjectForSQLDesign']").attr("arg03", moduleId);
		$("input[selectsqlid = 'getAutocompleteExternalObjectForSQLDesign']").attr("arg03",moduleId);
	}

	$$module.initOuputForInsertUpdateDelete = function(){

		var tr = $("#tab-output").find("table[id*='outputForm'] tbody tr");
		if($("#qp-sqldesign [name$='.sqlPattern']").val() == '0'){
			$(tr).remove();
			$("#tab-output a.glyphicon-plus").show();
			return;
		}
		var lengthTrs  = tr.length;
		if(!!lengthTrs && lengthTrs > 0 ){
			$(tr).remove();
		}
		$$module.addRowForInsertOrDelete($("#tab-output a.glyphicon-plus").last());
		$("#tab-output a.glyphicon-plus").hide();
	}

	$$module.addRowForInsertOrDelete = function(element){
		 var table = $(element)
		$("a[href='#tab-output']").tab("show");
		$.qp.ar.addRow({link:element,templateId:'outputForm-l0-insert-template',string:'before'});
		$("a[href='#tab-sql-design']").tab("show");
	}
	
	return $$module;
})($.namespace("$.qp.common"));

$.qp.common.init();
