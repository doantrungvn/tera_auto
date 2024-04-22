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
				$.qp.ar.addRow({link:obj,tableId:table,templateId:actionTemplateId,templateData:{ groupId:rgroupindex},position:{anchor:newRow,string:"after"} });
			}
		} else {
			if(type=="0"){
				actionTemplateId = table+"-action-template";
				$.qp.ar.addRow({link:obj,tableId:table,templateId:actionTemplateId,templateData:{ groupId:rgroupindex},position:{anchor:$Caller.parents("tr:first"),string:"after"} });
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
					}
				}
			}
//			if(returnType != 0){
//				$(row).find(".bd-ou-screenitem-hidden").hide();
//			}else{
//				$(row).find(".bd-ou-screenitem-hidden").show();
//			}
			
			//hide scope for WS mode
//			var actionType= $(row).attr("action-type");
//			if("action-type" == actionType){
//				var colspan = 5;
//				$(row).children("td").attr("colspan",colspan);
//			}
			if(typeof blogicType != 'undefined'){
				if(isEmptyQP(businessLogicId)){
					$(row).find(".bd-in-screenitem-hidden").hide();
				}else{
					if(blogicType == "0" && returnType == "1"){
						$(row).find(".bd-in-screenitem-hidden").show();
					}else{
						$(row).find(".bd-in-screenitem-hidden").hide();
					}
				}
				
				var currentModuleType = $("form").find("input[name='moduleType']").val();
				if(blogicType == "2" || blogicType == "1"){
					$(row).find(".bd-in-getscope").hide();
				}else{
					if(currentModuleType == "1"){
						$(row).find(".bd-in-getscope").hide();
					}
					else{
						$(row).find(".bd-in-getscope").show();
					}
				}
			}
			
			$$module.reStyleRow(table, row);
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
					}
				}
			}
			
			//hide scope for WS mode
			if(typeof blogicType != 'undefined'){
//				if(blogicType == "2" || blogicType == "1"){
//					$(row).find("input[name*='scopeType']").closest("td").hide();
//					$(row).find(".bd-ou-screenitem-hidden").hide();
//					$(table).find("th.bd-ou-screenitem-hidden").hide();
//					$(table).find("col.bd-ou-screenitem-hidden").hide();
//				}else 
				if(returnType == "0" && blogicType == "0" || patternType == "1"){
					$(row).find(".bd-ou-screenitem-hidden").show();
				}else{
					$(row).find(".bd-ou-screenitem-hidden").hide();
				}
				
				if(blogicType == "2" || blogicType == "1"){
					$(row).find(".bd-in-getscope").hide();
				}else{
					$(row).find(".bd-in-getscope").show();
				}
			}
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
					}
				}
			}
//			var actionType= $(row).attr("action-type");
//			if("action-type" == actionType){
//				var colspan = 5;
//				$(row).children("td").attr("colspan",colspan);
//			}
			$$module.reStyleRow(table, row);
		}
	};
	$$module.initAddButtonRow = function(tableId,actionTemplateId,isView){
		if(tableId){
			var groups = [];
			var $table = $("#"+tableId);
			$table.find(">tbody>tr").each(function(i){
				$$module.reStyleRow($table,$(this));
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
	return $$module;
})(jQuery.namespace('$.qp.businessdesign'));