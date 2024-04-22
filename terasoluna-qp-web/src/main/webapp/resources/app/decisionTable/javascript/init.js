$.qp.decisiontable = (function($$module){
	$$module.objectTypeChange = function(obj){
		var table = $(obj).closest("table").attr("id");
		var type = $(obj).val();
		var oldValue= $(obj).attr("oldvalue");
		var currentRow = $(obj).closest("tr");
		var $Caller = $(obj);
		var $ParentRow = $Caller.closest("tr");
		var rgroupindex = $ParentRow.attr("data-ar-rgroupindex") || "";
		var rgroup= $ParentRow.attr("data-ar-rgroup") || "";
		var actionTemplateId ;
		var newRow ;
		
		if(oldValue == '14' || oldValue == '16' || oldValue == '17'){
			var nextRow = $(currentRow).nextAll().first();
			
			actionTemplateId = table+"-template";
			
			if(type=="14"){
				actionTemplateId = table + "-entity-template";
			} else if(type=="16"){
				actionTemplateId = table + "-common-object-template";
			} else if(type=="17"){
				actionTemplateId = table + "-external-object-template";
			}
			
			if(nextRow.length >0){
				newRow = $.qp.ar.addRow({link:obj,container:$(obj).closest('table'),templateId:actionTemplateId,templateData:{ groupId:rgroup},position:{anchor:nextRow,string:"before"} });
			}else{
				newRow = $.qp.ar.addRow({link:obj,container:$(obj).closest('table'),templateId:actionTemplateId,templateData:{ groupId:rgroup} });
			}
			$(newRow).find("[name*='dataType']").val(type);

			$.qp.ar.removeRow({link:currentRow});
			if(type == "0"){
				var actionTemplateId = table+"-action-template";
				$.qp.ar.addRow({link:obj,container:$(newRow).closest('table'),templateId:actionTemplateId,templateData:{ groupId:rgroupindex},position:{anchor:newRow,string:"after"} });
			}
			$(newRow).find("select").attr("oldvalue",type);
		} else {
			if(type=="0"){
				actionTemplateId = table+"-action-template";
				$.qp.ar.addRow({link:obj,container:$(obj).closest('table'),templateId:actionTemplateId,templateData:{groupId:rgroupindex},position:{anchor:$Caller.parents("tr:first"),string:"after"} });
			} else if(type=="14"){
				actionTemplateId = table + "-entity-template";
				newRow = $.qp.ar.addRow({link: obj,container:$(obj).closest('table'),templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor:currentRow,string:"after"} });
				$.qp.ar.removeRow({link:currentRow});
			} else if(type=="16"){
				actionTemplateId = table + "-common-object-template";
				newRow = $.qp.ar.addRow({link: obj,container:$(obj).closest('table'),templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor:currentRow,string:"after"} });
				$.qp.ar.removeRow({link:currentRow});
			} else if(type=="17"){
				actionTemplateId = table + "-external-object-template";
				newRow = $.qp.ar.addRow({link: obj,container:$(obj).closest('table'),templateId:actionTemplateId,templateData:{groupId:rgroup},position:{anchor:currentRow,string:"after"} });
				$.qp.ar.removeRow({link:currentRow});
			} else{
				$.qp.ar.removeRow({link : obj,removeType : 'onlyDescendants'});
			}
			$(obj).attr("oldvalue",type);
		}
		
		if(newRow != undefined){
			changeArgOfRowObjByType(newRow, type, $('form#decisionTableForm').find("input:hidden[name='moduleId']").val());
			if(table == "tbl_input_list_result"){
				var decisionInputBeanCode = $ParentRow.find("input[name$='decisionInputBeanCode']").val();
				$(newRow).find("input[name$='decisionInputBeanCode']").val(decisionInputBeanCode);
			}else if(table == "tbl_output_list_result"){
				var decisionOutputBeanCode = $ParentRow.find("input[name$='decisionOutputBeanCode']").val();
				$(newRow).find("input[name$='decisionOutputBeanCode']").val(decisionOutputBeanCode);
			}
		}
	};
	
	$$module.reStyleRow = function(table, row) {
		var $row = $(row);
		var $table = $(table);
//		var paddingWidth =  $table.find("thead th:first").prop("clientWidth");
		var paddingWidth =  $table.find("thead th:first").css("width");
		if(paddingWidth != undefined){
			paddingWidth = paddingWidth.replace("px","");
		}
		var _stylePadding = {
				'width':paddingWidth +"px",
				'height': '100%',
				'border-right': 'solid 1px #ddd',
				'text-align': 'center',
				'padding-top' : '5px',
				'float': 'left'
		};
		var level = $row.attr("data-ar-rgroupindex").split(".").length;
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
			$inputDiv.outerWidth($inputDiv.parents("td").width()-paddingWidth*i);
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
	
	$$module.formCallback = function(table,direction,row){
		if(direction=="add"){
			var datatype = $(row).find("select[name*=dataType]");
			var rgroupindex = $(row).attr("data-ar-rgroupindex");
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
			$$module.reStyleRow(table, row);
		}
	};
	$$module.initAddButtonRow = function(tableId,actionTemplateId) {
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
				} else {
					if($row.find("select[name$='.dataType']").val()=="0"){
						rgroup = $row.attr("data-ar-rgroupindex").trim();
						$Caller = $table.find(">tbody>tr[data-ar-rgroup"+(!rgroup?"='":"^='"+rgroup)+"']:last");
						if($Caller.length==0){
							$Caller = $(this);
							$.qp.ar.addRow({link:$Caller,tableId:tableId,templateId:actionTemplateId,templateData:{ groupId:rgroup},position:{anchor:$Caller,string:"after"} });
							groups.push(rgroup);
						}
					}
				}
			});
		}
	};
	
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
	
	$$module.resizeHeighLogicTab = function() {
		if($('#'+TABLE_NAME.CONDITION).find(">tbody>tr").length == 0 
	    		|| $('#'+TABLE_NAME.ACTION).find(">tbody>tr").length == 0) {
	    	$('div'+TAB_NAME.LOGICDESIGN).css('min-height','300px');
	    } else if ($('#'+TABLE_NAME.CONDITION).find(">tbody>tr").length > 0
	    		&& $('#'+TABLE_NAME.ACTION).find(">tbody>tr").length > 0){
	    	$('div'+TAB_NAME.LOGICDESIGN).css('min-height','');
	    }
	};
	
	return $$module;
})(jQuery.namespace('$.qp.decisiontable'));