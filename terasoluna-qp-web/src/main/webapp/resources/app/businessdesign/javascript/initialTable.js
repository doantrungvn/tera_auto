$.qp.businessdesign = (function($$module){
	$$module.objectTypeChange = function(obj){
		var table = $(obj).closest("table").prop("id");
		var type = $(obj).val();
		var oldValue= $(obj).attr("oldvalue");
		var currentRow = $(obj).closest("tr");
		var $Caller = $(obj);
		var $ParentRow = $Caller.closest("tr");
		var rgroupindex = $ParentRow.attr("data-ar-rgroupindex") || "";
		var rgroup= $ParentRow.attr("data-ar-rgroup") || "";
		var actionTemplateId ;
		var newRow ;
		
		if (oldValue == "14" && type == "0") {
			if (!isEmptyQP($(currentRow).find("input[name*='tblDesignIdAutocomplete']").val())) { 
				var parentGroup = currentRow.attr("data-ar-rgroupindex");
				var allNextRow = currentRow.nextAll();
	
				var lstEntityItem = [];
				lstEntityItem.push(currentRow);
				
				$(allNextRow).each(function () {
					var childIndex = $(this).attr("data-ar-rgroup");
	
					if (parentGroup == childIndex) {
						lstEntityItem.push($(this));
						$(this).remove();
					} else {
						return;
					}
				});
				
				convertEntityToObject(lstEntityItem, currentRow);
				return;
			}
		}
		
		if(oldValue == "14" || oldValue == "16" || oldValue == "17"){
			var nextRow = $(currentRow).next();
			
			actionTemplateId = table+"-template";
			
			if(type=="14"){
				actionTemplateId = table + "-entity-template";
			} else if(type=="16"){
				actionTemplateId = table + "-common-object-template";
			} else if(type=="17"){
				actionTemplateId = table + "-external-object-template";
			}
			
			if(nextRow.length >0){
				newRow = $.qp.ar.addRow({link:obj,tableId:table,templateId:actionTemplateId,templateData:{ groupId:rgroup},position:{anchor:nextRow,string:"before"} });
			}else{
				newRow = $.qp.ar.addRow({link:obj,tableId:table,templateId:actionTemplateId,templateData:{ groupId:rgroup} });
			}
			$(newRow).find("[name*='dataType']").val(type);

			$.qp.ar.removeRow({link:currentRow});
			if(type == "0"){
				var actionTemplateId = table+"-action-template";
				newRow = $.qp.ar.addRow({link:obj,tableId:table,templateId:actionTemplateId,templateData:{ groupId:rgroupindex},position:{anchor:newRow,string:"after"} });
			}
		} else {
			if(type=="0"){
				actionTemplateId = table+"-action-template";
				newRow = $.qp.ar.addRow({link:obj,tableId:table,templateId:actionTemplateId,templateData:{ groupId:rgroupindex},position:{anchor:$Caller.parents("tr:first"),string:"after"} });
			} else if(type=="14"){
				actionTemplateId = table + "-entity-template";
				newRow = $.qp.ar.addRow({link: obj,tableId:table,templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor:currentRow,string:"after"} });
				$.qp.ar.removeRow({link:currentRow});
			} else if(type=="16"){
				actionTemplateId = table + "-common-object-template";
				newRow = $.qp.ar.addRow({link: obj,tableId:table,templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor:currentRow,string:"after"} });
				$.qp.ar.removeRow({link:currentRow});
			} else if(type=="17"){
				actionTemplateId = table + "-external-object-template";
				newRow = $.qp.ar.addRow({link: obj,tableId:table,templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor:currentRow,string:"after"} });
				$.qp.ar.removeRow({link:currentRow});
			} else{
				$.qp.ar.removeRow({link : obj,removeType : 'onlyDescendants'});
			}
			$(obj).attr("oldvalue",type);
		}
		if(newRow != undefined){
			changeArgOfRowObjByType(newRow, type, $('form#businessDesignForm').find("input:hidden[name='moduleId']").val());
			if(table == "tbl_inputbean_list_define"){
				var inputBeanCode = $ParentRow.find("input[name$='inputBeanCode']").val();
				$(newRow).find("input[name$='inputBeanCode']").val(inputBeanCode);
			}else if(table == "tbl_outputbean_list_define"){
				var outputBeanCode = $ParentRow.find("input[name$='outputBeanCode']").val();
				$(newRow).find("input[name$='outputBeanCode']").val(outputBeanCode);
			}else if(table == "tbl_objectdefinition_list_define"){
				var objectDefinitionCode = $ParentRow.find("input[name$='objectDefinitionCode']").val();
				$(newRow).find("input[name$='objectDefinitionCode']").val(objectDefinitionCode);
			}
		}
	};
	
	$$module.reStyleRow = function(table, row){
		var $row = $(row);
		var restyleFlag = $row.attr("restyleFlag");
		if(restyleFlag != "true"){
			row.attr("restyleFlag",true)
			var $table = $(table);
	//		var paddingWidth =  $table.find("thead th:first").prop("clientWidth");
	//		var paddingWidth =  $table.find("thead th:first").css("width");
			var paddingWidth =  $table.find(">thead th:first").outerWidth();
	//		if(paddingWidth != undefined){
	//			paddingWidth = paddingWidth.replace("px","");
	//		}
			var _stylePadding = {
					'width':paddingWidth +"px",
					'height': '100%',
					'border-right': 'solid 1px #ddd',
					'text-align': 'center',
					'padding' : '5px',
					'float': 'left'
			};
			var level = ($row.attr("data-ar-rgroupindex") || "").split(".").length;
			var $paddingDiv = $("<div class=\"ar-paddingDiv\"></div>").css(_stylePadding);
			
			if($row.hasClass("ar-dataRow")){
				var $inputDiv = $row.find("span.ar-groupIndex").closest("div").next();
				$inputDiv.closest("div").css("padding-top",$inputDiv.closest("td").css("padding-top"));
				$inputDiv.closest("div").css("padding-left",$inputDiv.closest("td").css("padding-left"));
				$inputDiv.closest("div").css("padding-right",$inputDiv.closest("td").css("padding-right"));
				$inputDiv.closest("div").css("padding-bottom",$inputDiv.closest("td").css("padding-bottom"));
				$inputDiv.closest("td").css("padding","0px");
				$inputDiv.closest("div").css("text-align","left");
				$inputDiv.prev().css(_stylePadding);
				$inputDiv.prev().outerWidth(paddingWidth)
				for(var i=1;i<level;i++){
					$inputDiv.prev().before($paddingDiv);
					$paddingDiv = $paddingDiv.clone();
				}
				
				var outerWidth;
				outerWidth = $inputDiv.closest("td").outerWidth();
				$inputDiv.outerWidth(outerWidth-2-paddingWidth*i);
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
		}
	};
	
	$$module.inputFormCallback = function(table,direction,row){
		if(direction=="add"){
			var moduleId = $(table).closest("form").find("input[name='moduleId']").val();
			$(row).find("input[name*='messageStringAutocomplete']").attr("arg01",moduleId);
			
			var datatype = $(row).find("select[name*=dataType]");
			var rgroupindex = $(row).attr("data-ar-rgroupindex") == undefined ? "" : $(row).attr("data-ar-rgroupindex");
			var regex = /\./g;
			var t = rgroupindex.match(regex);
			if(datatype!=undefined){
				if(t!=null){
					if(t.length >0){
						$(datatype).find('option[value="14"]').remove();
					}
					if(t.length == MAX_NESTED_OBJECT){
						$(datatype).find('option[value="0"]').remove();
						$(datatype).find('option[value="16"]').remove();
						$(datatype).find('option[value="17"]').remove();
						$(datatype).find('option[value="14"]').remove();
					}
				}
			}
			if(typeof blogicType != 'undefined'){
				if(blogicType == "0" && moduleType == "0" && returnType == "1" && requestMethod == "4"){
					$(row).find(".bd-in-screenitem-hidden").show();
				}else{
					$(row).find(".bd-in-screenitem-hidden").hide();
				}
				
				var currentModuleType = $("form").find("input[name='moduleType']").val();
				if(blogicType == "1"){
					$(row).find(".bd-in-getscope").hide();
				}else{
					if(blogicType == "0" && currentModuleType == "1"){
						$(row).find(".bd-in-getscope").hide();
					}
					else{
						$(row).find(".bd-in-getscope").show();
					}
				}
			}
			
			$(row).find("input[name$='.objectIdAutocomplete']").attr("arg03", moduleId);
			
			//case batch
			var countOfRow = $(table).find("tbody > tr").length;
			var maxRow = $(table).attr("max-row");
			if(maxRow != undefined && maxRow != ""){
				if(countOfRow >= maxRow)
					$(table).next("div.qp-div-action-table").hide();
			}
			$$module.reStyleRow(table, row);
		}
		if(direction=="remove"){
			var countOfRow = $(table).find("tbody > tr").length;
			var maxRow = $(table).attr("max-row");
			if(maxRow != undefined && maxRow != ""){
				if(countOfRow < maxRow)
					$(table).next("div.qp-div-action-table").show();
			}
		}
	};
	$$module.outputFormCallback = function(table,direction,row){
		if(direction=="add"){
			var moduleId = $(table).closest("form").find("input[name='moduleId']").val();
			$(row).find("input[name*='messageStringAutocomplete']").attr("arg01",moduleId);
			
			var datatype = $(row).find("select[name*=dataType]");
			var rgroupindex = $(row).attr("data-ar-rgroupindex") == undefined ? "" : $(row).attr("data-ar-rgroupindex");
			var regex = /\./g;
			var t = rgroupindex.match(regex);
			if(datatype!=undefined){
				if(t!=null){
					if(t.length >0){
//						$(datatype).find('option[value="14"]').remove();
					}
					if(t.length == MAX_NESTED_OBJECT){
						$(datatype).find('option[value="0"]').remove();
						$(datatype).find('option[value="16"]').remove();
						$(datatype).find('option[value="17"]').remove();
						$(datatype).find('option[value="14"]').remove();
					}
				}
			}
			
			//hide scope for WS mode
			if(typeof blogicType != 'undefined'){
				if((blogicType == "0" && (returnType == "0" || (returnType == "1" && requestMethod == "0")) )|| patternType == "1"){
					$(row).find(".bd-ou-screenitem-hidden").show();
				}else{
					$(row).find(".bd-ou-screenitem-hidden").hide();
				}
				
				var currentModuleType = $("form").find("input[name='moduleType']").val();
				if(blogicType == "1"){
					$(row).find(".bd-ou-setscope").hide();
				}else{
					if(blogicType == "0" && currentModuleType == "1"){
						$(row).find(".bd-ou-setscope").hide();
					}
					else{
						$(row).find(".bd-ou-setscope").show();
					}
				}
			}
			$(row).find("input[name$='.objectIdAutocomplete']").attr("arg03", moduleId);
			$$module.reStyleRow(table, row);
		}
	};
	$$module.obFormCallback = function(table,direction,row){
		if(direction=="add"){
//			var status = $("#objectDefinitionScope").attr("status");
//			//0 : hide
//			//1 : show
//
//			if(status == "0"){
//				$(row).find("td.bd-in-scope").addClass("bd-in-scope-hidden");
//			}else if(status == "1"){
//				$(row).find("td.bd-in-scope").removeClass("bd-in-scope-hidden");
//			}
			var datatype = $(row).find("select[name*=dataType]");
			var rgroupindex = $(row).attr("data-ar-rgroupindex") == undefined ? "" : $(row).attr("data-ar-rgroupindex");
			var regex = /\./g;
			var t = rgroupindex.match(regex);
			if(datatype!=undefined){
				if(t!=null){
					if(t.length >0){
//						$(datatype).find('option[value="14"]').remove();
					}
					if(t.length == MAX_NESTED_OBJECT){
						$(datatype).find('option[value="0"]').remove();
						$(datatype).find('option[value="16"]').remove();
						$(datatype).find('option[value="17"]').remove();
						$(datatype).find('option[value="14"]').remove();
					}
				}
			}
//			var actionType= $(row).attr("action-type");
//			if("action-type" == actionType){
//				var colspan = 5;
//				$(row).children("td").attr("colspan",colspan);
//			}
			var moduleId = $(table).closest("form").find("input[name='moduleId']").val();
			$(row).find("input[name$='.objectIdAutocomplete']").attr("arg03", moduleId);
			$$module.reStyleRow(table, row);
		}
	};
	$$module.initAddButtonRow = function(tableId,actionTemplateId,isView){
		if(tableId){
			var groups = [];
			var $table = $("#"+tableId);
			$table.find(">tbody>tr").each(function(i){
//				$$module.reStyleRow($table,$(this));
				var rgroup = $(this).attr("data-ar-rgroup");
				var $row = $(this);
				var $Caller;
				if($.inArray(rgroup,groups)==-1 && rgroup){
					var parentDataType = $table.find(">tbody>tr[data-ar-rgroupindex='"+rgroup+"'] [name$='.dataType']").val();
					var addChildFlg = $table.find(">tbody>tr[data-ar-rgroupindex='"+rgroup+"'] [name$='.dataType']").attr("addChildFlg");
					if(parentDataType=='0' && addChildFlg != "false") {
						$Caller = $table.find(">tbody>tr[data-ar-rgroup"+(!rgroup?"='":"^='"+rgroup)+"']:last");
						$.qp.ar.addRow({link:$Caller,tableId:tableId,templateId:actionTemplateId,templateData:{ groupId:rgroup},position:{anchor:$Caller,string:"after"} });
						groups.push(rgroup);
					}
				}
				if($row.find("select[name$='.dataType']").val()=="0"){
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
	$$module.initAddButtonRowForRangeRow = function(tableId,actionTemplateId,isView,startRow,endRow){
		if(tableId){
			var groups = [];
			var $table = $("#"+tableId);
			$(startRow).nextUntil(endRow).each(function(i){
				var rgroup = $(this).attr("data-ar-rgroup");
				var $row = $(this);
				var $Caller;
				if($.inArray(rgroup,groups)==-1 && rgroup){
					var parentDataType = $table.find(">tbody>tr[data-ar-rgroupindex='"+rgroup+"'] [name$='.dataType']").val();
					var addChildFlg = $table.find(">tbody>tr[data-ar-rgroupindex='"+rgroup+"'] [name$='.dataType']").attr("addChildFlg");
					if(parentDataType=='0' && addChildFlg != "false") {
						$Caller = $table.find(">tbody>tr[data-ar-rgroup"+(!rgroup?"='":"^='"+rgroup)+"']:last");
						$.qp.ar.addRow({link:$Caller,tableId:tableId,templateId:actionTemplateId,templateData:{ groupId:rgroup},position:{anchor:$Caller,string:"after"} });
						groups.push(rgroup);
					}
				}
				if($row.find("select[name$='.dataType']").val()=="0"){
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
	$$module.initData = function(tableId){
		if(tableId){
			var groups = [];
			var $table = $("#"+tableId);
			$table.find(">tbody>tr.ar-dataRow").each(function(){
				$$module.reStyleRow($table,$(this));
			});
		}
	};
	$$module.inputParameterOfAssignCallback = function(table,direction,row){
		if(direction=="add"){
//			var rgroupindex = $(row).attr("data-ar-rgroupindex");
//			var regex = /\./g;
//			var t = rgroupindex.match(regex);
			$(row).find("td.btn-getobject").children().hide();
			$$module.reStyleRow(table, row);
		}
	};
	
	function convertEntityToObject(lstEntityItem, currentRow){
		//get active tab:
		var currentTab = $('#com-menu-sidebar').find('li.active').attr('id');
//		var isRollbackTab = false;
//		if(currentTab != 'tabInput'){
//			isRollbackTab = true;
//			$('#com-menu-sidebar').find('a#tabInputBean').tab('show');
//		}
		
		var table;

		if (currentTab == 'tabInput'){
			table = $("#businessDesignForm").find("table[id='tbl_inputbean_list_define']");
		} else if (currentTab == 'tabOutput'){
			table = $("#businessDesignForm").find("table[id='tbl_outputbean_list_define']");
		} else {
			table = $("#businessDesignForm").find("table[id='tbl_objectdefinition_list_define']");
		}

		var tableId = $(table).attr("id");
		var rowTemplateId = tableId+"-template";
		var actionTemplateId = tableId+"-action-template";
		
		var anchor = currentRow;

		var groupId= "";
		
//		var lstObject = [];
//		
//		lstEntityItem.each(function() {
//			var object;
//			object.beanName = $(this).find("input[name*='BeanName']").val();
//			object.beanCode = $(this).find("input[name*='BeanCode']").val();
//			object.obName = $(this).find("input[name*='objectDefinitionName']").val();
//			object.obCode = $(this).find("input[name*='objectDefinitionCode']").val();
//			
//			object.messageStringAutocomplete = $(this).find("input[name*='messageStringAutocomplete']").val();
//			object.dataType = $(this).find("input[name*='dataType']").val();
//			object.arrayFlg = $(this).find("input[name*='arrayFlg']").val();
//			object.createdMessageFlg = $(this).find("input[name*='createdMessageFlg']").val();
//			object.BeanType = $(this).find("input[name*='BeanType']").val();
//			object.screenItemId = $(this).find("input[name*='screenItemId']").val();
//			object.objectType = $(this).find("input[name*='objectType']").val();
//			object.objectId = $(this).find("input[name*='objectId']").val();
//			object.objectFlg = $(this).find("input[name*='objectFlg']").val();
//			object.scopeType = $(this).find("input[name*='scopeType']").val();
//			object.scopeValue = $(this).find("input[name*='scopeValue']").val();
//			object.scopeValueAutocomplete = $(this).find("input[name*='scopeValueAutocomplete']").val();
//			object.dataTypeSession = $(this).find("input[name*='dataTypeSession']").val();
//			object.arrayFlagSession = $(this).find("input[name*='arrayFlagSession']").val();
//			object.scopeValueAutocomplete = $(this).find("input[name*='scopeValueAutocomplete']").val();
//			object.scopeValueAutocomplete = $(this).find("input[name*='scopeValueAutocomplete']").val();
//			object.beanName = $(this).find("input[name*='objectDefinitionName']").val();
//		});
		
		for(var i =0 ;i <lstEntityItem.length;i++){
			var objBean = lstEntityItem[i];
			
			if (i == 1) {
				groupId = $(newRow).attr("data-ar-rgroupindex");
			}
			var newRow = $.qp.ar.addRow({link:anchor,tableId:tableId,templateId:rowTemplateId,templateData:{groupId:groupId}, position:{anchor: $(anchor).closest('tr'),string:'after'}});
			anchor = newRow;
			
			// Ob case
			$(newRow).find("input[name*='objectDefinitionId']").val(objBean.find("input[name*='objectDefinitionId']").val());
			$(newRow).find("input[name*='objectDefinitionName']").val(objBean.find("input[name*='objectDefinitionName']").val());
			$(newRow).find("label[name*='objectDefinitionName']").text(objBean.find("label[name*='objectDefinitionName']").text());
			$(newRow).find("input[name*='objectDefinitionCode']").val(objBean.find("input[name*='objectDefinitionCode']").val());
			$(newRow).find("label[name*='objectDefinitionCode']").text(objBean.find("label[name*='objectDefinitionCode']").text());
			$(newRow).find("input[name*='parentObjectDefinitionId']").val(objBean.find("input[name*='parentObjectDefinitionId']").val());

			$(newRow).find("input[name*='inputBeanId']").val(objBean.find("input[name*='inputBeanId']").val());
			$(newRow).find("input[name*='inputBeanName']").val(objBean.find("input[name*='inputBeanName']").val());
			$(newRow).find("input[name*='inputBeanType']").val(objBean.find("input[name*='inputBeanType']").val());
			$(newRow).find("label[name*='inputBeanName']").text(objBean.find("label[name*='inputBeanName']").text());
			$(newRow).find("input[name*='parentInputBeanId']").val(objBean.find("input[name*='parentInputBeanId']").val());
			$(newRow).find("input[name*='itemSequenceNo']").val(objBean.find("input[name*='itemSequenceNo']").val());
			$(newRow).find("input[name*='messageStringAutocomplete']").val(objBean.find("input[name*='messageStringAutocomplete']").val());
			$(newRow).find("input[name*='inputBeanCode']").val(objBean.find("input[name*='inputBeanCode']").val());
			$(newRow).find("label[name*='inputBeanCode']").text(objBean.find("label[name*='inputBeanCode']").text());

			$(newRow).find("input[name*='outputBeanId']").val(objBean.find("input[name*='outputBeanId']").val());
			$(newRow).find("input[name*='outputBeanName']").val(objBean.find("input[name*='outputBeanName']").val());
			$(newRow).find("input[name*='outputBeanType']").val(objBean.find("input[name*='outputBeanType']").val());
			$(newRow).find("label[name*='outputBeanName']").text(objBean.find("label[name*='outputBeanName']").text());
			$(newRow).find("input[name*='parentOutputBeanId']").val(objBean.find("input[name*='parentOutputBeanId']").val());
			$(newRow).find("input[name*='itemSequenceNo']").val(objBean.find("input[name*='itemSequenceNo']").val());
			$(newRow).find("input[name*='messageStringAutocomplete']").val(objBean.find("input[name*='messageStringAutocomplete']").val());
			$(newRow).find("input[name*='outputBeanCode']").val(objBean.find("input[name*='outputBeanCode']").val());
			$(newRow).find("label[name*='outputBeanCode']").text(objBean.find("label[name*='outputBeanCode']").text());
			
			if (i == 0) {
				$(newRow).find("input[name*='dataType']").val(0);
				$(newRow).find("select[name*='dataType']").val(0);
			} else {
				$(newRow).find("input[name*='dataType']").val(objBean.find("input[name*='dataType']").val());
				$(newRow).find("select[name*='dataType']").val(objBean.find("input[name*='dataType']").val());
			}
			$(newRow).find("input[name*='arrayFlg']").val(objBean.find("input[name*='arrayFlg']").val());
			
			$(newRow).find("input[name*='createdMessageFlg']").val(false);
			$(newRow).find("input[name*='screenItemId']").val(objBean.find("input[name*='screenItemId']").val());
			$(newRow).find("input[name*='tblDesignId']").val(objBean.find("input[name*='tblDesignId']").val());
			$(newRow).find("input[name*='columnId']").val(objBean.find("input[name*='columnId']").val());
			$(newRow).find("input[name*='objectFlg']").val(objBean.find("input[name*='objectFlg']").val());
			$(newRow).find("input[name*='mappingScreenItemFlag']").val(true);
			$(newRow).find("input[name*='objectType']").val(objBean.find("input[name*='objectType']").val());
			$(newRow).find("input[name*='objectId']").val(objBean.find("input[name*='objectId']").val());
			$(newRow).find("input[name*='objectFlg']").val(true);
			$(newRow).find("input[name*='scopeType']").val(objBean.find("input[name*='scopeType']").val());
			if($(newRow).find("input[name*='scopeType']").val() == 1 && !isEmptyQP($(newRow).find("input[name*='scopeType']").val())){
				$(newRow).find(":button.glyphicon-menu-hamburger").removeClass("btn-info");
				$(newRow).find(":button.glyphicon-menu-hamburger").addClass("btn-warning");
			}else{
				$(newRow).closest("div.dropdown").find(":button.glyphicon-menu-hamburger").addClass("btn-info");
				$(newRow).closest("div.dropdown").find(":button.glyphicon-menu-hamburger").removeClass("btn-warning");
			}
			$(newRow).find("input[name*='scopeValue']").val(objBean.find("input[name*='scopeValue']").val());
			$(newRow).find("input[name*='scopeValueAutocomplete']").val(objBean.find("input[name*='scopeValueAutocomplete']").val());
			$(newRow).find("input[name*='dataTypeSession']").val(objBean.find("input[name*='dataTypeSession']").val());
			$(newRow).find("input[name*='arrayFlagSession']").val(objBean.find("input[name*='arrayFlagSession']").val());
			
			if (objBean.find("input[name*='screenItemId']").length > 0) {
				$(newRow).find("label[name*='screenItemIdAutocomplete']").text(objBean.find("label[name*='screenItemIdAutocomplete']").text());
				$(newRow).find("input[name*='screenItemId']").val(objBean.find("input[name*='screenItemId']").val());
				$(newRow).find("input[name*='mappingType']").val(objBean.find("input[name*='mappingType']").val());
				$(newRow).find("input[name*='itemName']").val(objBean.find("input[name*='itemName']").val());
				
				$(newRow).find("label[name*='screenItemId']").attr("name", objBean.find("label[name*='screenItemId']").attr("name"));
				$(newRow).find("input[name*='screenItemId']").attr("name", objBean.find("input[name*='screenItemId']").attr("name"));
				$(newRow).find("input[name*='mappingType']").attr("name", objBean.find("input[name*='mappingType']").attr("name"));
				$(newRow).find("input[name*='itemName']").attr("name", objBean.find("input[name*='itemName']").attr("name"));
			}
		}

		var startRow,endRow;
		//get start/end row to init add button
		if(i==0){
			startRow = $(newRow);
		}
		if(i==lstEntityItem.length-1){
			endRow = $(newRow);
		}
		//init add button
		$.qp.ar.addRow({link:anchor,tableId:tableId,templateId:tableId+"-action-template",templateData:{ groupId:groupId},position:{anchor:$(anchor).closest('tr'),string:"after"} });

		$.qp.ar.removeRow({link:currentRow});

        $.qp.ar.recalculateRowIndex($(tableId),"");
        $.qp.ar.renameAttributes($(tableId));
	}
	
	return $$module;
})(jQuery.namespace('$.qp.businessdesign'));