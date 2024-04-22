$(document).ready(function() {	
	$("#wapper>table>tbody").sortable({
        
        update: function(e, ui) {
        	$(this).find(".methodInfor").css("width","");
        	$.qp.ar.recalculateRowIndex($(this).closest("table"),"");
        	$.qp.ar.renameAttributes($(this).closest("table"));
		},
		helper: function(e, ui) {
			var width = '';
			ui.children().each(function() {
				$(this).width($(this).width());
				width = $(this).width();
			});
			ui.width(width);
			return ui;
		},
		items: '>tr',
		handle: '.sort-method',
	});
});

$.qp.functionmaster = (function($$module){
	$$module.reStyleRow = function(table, row){
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
			$inputDiv.closest("div").css("text-align","left");
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
			$inputDiv.closest("div").css("text-align","left");
			$inputDiv.closest("td").css("padding","0px");
			for(var i=1;i<level;i++){
				$inputDiv.before($paddingDiv);
				$paddingDiv = $paddingDiv.clone();
			}
		}
	};
	
	$$module.initAddButtonRow = function(tableId,actionTemplateId,isView){
		if(tableId){
			var groups = [];
			var $table = $("table[id='"+tableId+"']");
			$table.find(">tbody>tr").each(function(){
				$$module.reStyleRow($table,$(this));
				var rgroup = $(this).attr("data-ar-rgroup");
				var $row = $(this);
				var $Caller;
				if($.inArray(rgroup,groups)==-1 && rgroup){
					var parentDataType = $table.find(">tbody>tr[data-ar-rgroupindex='"+rgroup+"'] [name$='.dataType']").val();
					var addChildFlg = $table.find(">tbody>tr[data-ar-rgroupindex='"+rgroup+"'] [name$='.dataType']").attr("addChildFlg");
					if(parentDataType=='0' && addChildFlg != "false") {
						$Caller = $table.find(">tbody>tr[data-ar-rgroup"+(!rgroup?"='":"^='"+rgroup)+"']:last");
						$.qp.ar.addRow({link:$Caller,container:$table,templateId:actionTemplateId,templateData:{ groupId:rgroup},position:{anchor:$Caller,string:"after"} });
						groups.push(rgroup);
					}
				}
				if($row.find("[name$='.dataType']").val()=="0"){
					rgroup = $row.attr("data-ar-rgroupindex").trim();
					$Caller = $table.find(">tbody>tr[data-ar-rgroup"+(!rgroup?"='":"^='"+rgroup)+"']:last");
					if($Caller.length==0){
						$Caller = $(this);
						$.qp.ar.addRow({link:$Caller,container:$table,templateId:actionTemplateId,templateData:{ groupId:rgroup},position:{anchor:$Caller,string:"after"} });
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
			$table.find(">tbody>tr").each(function(){
				$$module.reStyleRow($table,$(this));
			});
		}
	};
	$$module.inputParameterOfAssignCallback = function(table,direction,row){
		if(direction=="add"){
			var rgroupindex = $(row).attr("data-ar-rgroupindex");
			var regex = /\./g;
			var t = rgroupindex.match(regex);
			$(row).find("td.btn-getobject").children().hide();
			$$module.reStyleRow(table, row);
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
	return $$module;
})(jQuery.namespace('$.qp.functionmaster'));