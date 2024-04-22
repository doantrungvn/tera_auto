$(function() {
	/* sort */
	$('#tbl_list_details').find("tbody").sortable({
		// placeholder: "ui-sortable-placeholder"
		helper: function(e, ui) {
			ui.children().each(function() {
				$(this).width($(this).width());
			});
			return ui;
		},
		update: function(event, ui) {
			$("[name=arrIndex]").each(function(i) {
				if (i < $("[name=arrIndex]").length) {
					$('[name=arrIndex]:eq('+i+')').val(i + 1);
					$('span[id=stt]:eq('+i+')').html(i + 1);
					removeIconDeleteFistRow();
				}
			});
		},
		items: 'tr',
		cursor: 'move',
		handle: '.sortable'
	});
	initMapKey();
});
function initMapKey() {
	mapKeyTemp["menuDesignItem"] = 0;
	var tableMenuDesignItem = "#tbl_list_details";
	$(tableMenuDesignItem).find("input[name*='menuItemId']").each(function() {
		var id = $(this).val();
		if (id.search("in") >= 0) {
			id = id.replace("in","");
			id = parseInt(id);
			if (mapKeyTemp["menuDesignItem"] < id) {
				mapKeyTemp["menuDesignItem"] = id;
			}
		}
	});
}
var mapKeyTemp = [];
var CONST = {
		ATTR_PRECHECK_CALLBACK : "data-ar-precheck",
		ATTR_FINISHED_CALLBACK : "data-ar-callback",
		ATTR_INDEXCHANGE_CALLBACK : "data-ar-indexchange",
		ATTR_MAXIMUM_ROWS : "data-ar-mrows",
		ATTR_IGNORE_ROWS : "data-ar-irows",
		ATTR_ROW_GROUPID : "data-ar-rgroup",
		ATTR_ROW_GROUPINDEX : "data-ar-rgroupindex",
		ATTR_ROW_TEMPLATE : "data-ar-templateid",
		ATTR_ROW_INDEX : "data-ar-rindex",
		DIRECTION_ADD : "add",
		DIRECTION_REMOVE : "remove",
		ANCHOR_STRING_BEFORE : "before",
		ANCHOR_STRING_AFTER : "after",
		ANCHOR_STRING_INSIDE_BEGIN : "inside-begin",
		ANCHOR_STRING_INSIDE_END : "inside-end",
		REMOVE_TYPE_ALL :"all",
		REMOVE_TYPE_ONLYDESC: "onlyDescendants",
		AR_CLASS_GROUPINDEX: "ar-groupIndex",
		AR_CLASS_RINDEX: "ar-rIndex",
		AR_CLASS_GROUPID: "ar-groupId",
		ARR_REINDEX_ATTRIBUTES : ["name","id","for"]
};

//Add row
$.qp.addRow = function(param) {
	var $container = param.container;
	var $template = null;
	var $newRow = null;
	var templateData = param.templateData || {};
	if(!$container){
		if(!param.tableId) {
			$container = $(param.link).closest("div").prev("table");
		} else {
			$container = $("#"+param.tableId);
		}
	} else {
		$container = $($container);
	}
	if($container!="undefined") {
		var ignoreRows = parseInt($container.attr(CONST.ATTR_IGNORE_ROWS));
		if(isNaN(ignoreRows)) {
			ignoreRows = 0;
		}
		var maxRows = parseInt($container.attr(CONST.ATTR_MAXIMUM_ROWS));
		if(isNaN(maxRows)) {
			maxRows = 200;
		}
		var precheckFunction = $container.attr(CONST.ATTR_PRECHECK_CALLBACK);
		var passCheck = true;
		if(typeof $.namespace(precheckFunction)=="function"){
			passCheck = $.namespace(precheckFunction)($container,param.link,CONST.DIRECTION_ADD);
		}
		if($container.is("table") & !param.position){
			param.position = {anchor:'>tbody',string:CONST.ANCHOR_STRING_INSIDE_END};
			if($container.find(">tbody>*").length-ignoreRows >= maxRows) {
				passCheck = false;
			}
			if($container.find(">tbody").length==0){
				$container.append($("<tbody>"))
			}
		} 
		if(!param.templateId) {
			$template = $("#" + $container.attr("id") + "-template");
		} else {
			$template = $("#"+param.templateId);
		}
		$newRow = $template.tmpl(templateData);
		if(param.type == "separator"){
			$newRow.find('td:eq(0)').find("#inputmenuName").html("").append("<hr class='sperator_1'/>");
			$newRow.find('td:eq(1)').find(".input-group ").hide();
			$newRow.find('td:eq(0)').find("input[name$='.menuItemType']").val(1);
			$newRow.find('td:eq(0)').find(".input-group-addon").eq(1).hide();
		}
		if(passCheck && $newRow.toString()){
			var callback = $container.attr(CONST.ATTR_FINISHED_CALLBACK);
			var templateId = $template.attr("id");
			if(!templateId){
				templateId = '';
			}
			$newRow.attr(CONST.ATTR_ROW_TEMPLATE, templateId);
			if(!$newRow.attr(CONST.ATTR_ROW_GROUPID)){
				$newRow.attr(CONST.ATTR_ROW_GROUPID,"");
			}
			switch(param.position.string){
			case CONST.ANCHOR_STRING_BEFORE:
				$container.find(param.position.anchor).before($newRow);
				break;
			case CONST.ANCHOR_STRING_AFTER:
				$container.find(param.position.anchor).after($newRow);
				break;
			case CONST.ANCHOR_STRING_INSIDE_END:
				$container.find(param.position.anchor).append($newRow);
				break;
			case CONST.ANCHOR_STRING_INSIDE_BEGIN:
				$container.find(param.position.anchor).prepend($newRow);
				break;
			}
			$.qp.ar.recalculateRowIndex($container,param.indexClass,templateData.groupId); // refresh row index (No.)
			$.qp.ar.renameAttributes($container);
			$.qp.ar.callbackDefault($container,CONST.DIRECTION_ADD,$newRow,param);
			if(typeof $.namespace(callback)=="function"){
				$.namespace(callback)($container,CONST.DIRECTION_ADD,$newRow,param);
			}
		}
	}
	clearActionParentMenu();
	return $newRow;
};

//Remove row from List table
$.qp.removeRowJS = function(param) {
	var $container;
	if(!$.contains($(param.container),$(param.link))){
		$container = $(param.link).closest("table");
	} else {
		$container = $(param.container);
	}
	
	var callback = $container.attr(CONST.ATTR_FINISHED_CALLBACK);
	var ignoreRows = parseInt($container.attr(CONST.ATTR_IGNORE_ROWS));
	if (isNaN(ignoreRows)) {
		ignoreRows = 0;
	}
	var $removeRow = $(param.link).closest("tr");
	var precheckFunction = $container.attr(CONST.ATTR_PRECHECK_CALLBACK);
	var passCheck = true;
	if(typeof $.namespace(precheckFunction)=="function"){
		passCheck = $.namespace(precheckFunction)($container,param.link,CONST.DIRECTION_REMOVE,$removeRow);
	}
	if(passCheck){
		if(param.removeType && param.removeType!=CONST.REMOVE_TYPE_ALL){
			if(param.removeType == CONST.REMOVE_TYPE_ONLYDESC){
				$container.find("["+CONST.ATTR_ROW_GROUPID+"^='"+$removeRow.attr(CONST.ATTR_ROW_GROUPINDEX)+"']").remove();
			}
		} else {
			$container.find("["+CONST.ATTR_ROW_GROUPID+"^='"+$removeRow.attr(CONST.ATTR_ROW_GROUPINDEX)+"']").remove();
			$removeRow.remove();
		}
		
		var templateId = $removeRow.attr(CONST.ATTR_ROW_TEMPLATE);
		if(!templateId) {
			$template = $("#" + $container.attr("id") + "-template");
		} else {
			$template = $("#"+templateId);
		}
		
		var $newRow ;
		if (param.isReserved) {
			if ($container.find("tr").size() <= ignoreRows) {
				$newRow = $template.tmpl();
				$newRow.attr(CONST.ATTR_ROW_TEMPLATE,$removeRow.attr(CONST.ATTR_ROW_TEMPLATE));
				$container.append($newRow);
			}
		}
		$.qp.ar.callbackDefault($container,CONST.DIRECTION_REMOVE,$removeRow);
		if(typeof $.namespace(callback)=="function"){
			$.namespace(callback)($container,CONST.DIRECTION_REMOVE,$removeRow,param);
		}
		$.qp.ar.recalculateRowIndex($container,$removeRow.attr(CONST.ATTR_ROW_GROUPID));
		$.qp.ar.renameAttributes($container);
		if (param.isReserved) {
			if ($newRow) {
				$.qp.ar.callbackDefault($container,CONST.DIRECTION_ADD,$newRow,param);
				if(typeof $.namespace(callback)=="function"){
					$.namespace(callback)($container,CONST.DIRECTION_ADD,$newRow,param);
				}
			}
		}
	}
	clearActionParentMenu();
	return $removeRow;
};

/**
 * 
 * @param curentRow
 * @param idFistRow
 */
function removeRow(curentRow, idFistRow){
	if(idFistRow == ""){
		idFistRow = curentRow.attr("id");
	}
	var idCurentRow = curentRow.attr("id");
	var idNextRow = curentRow.next().attr("id");
	var sdfdf = curentRow.next();
	curentRow.remove();
	if(idFistRow == sdfdf.attr("id")){
		return;
	}
	if((idCurentRow == idNextRow) && (idCurentRow == idFistRow)){
		return;
	}else if(sdfdf.attr("id") == findNextRank(idFistRow)){
		return;
	}else{
		removeRow(sdfdf, idFistRow);
	}
}

/**
 * Find name parent rank.
 * @param curentRank
 * @returns {String}
 */
function findNextRank(curentRank){
	if(curentRank == "rankFour"){
		return "rankThree";
	}else if(curentRank == "rankThree"){
		return "rankTwo";
	}else if(curentRank == "rankTwo"){
		return "rankOne";
	}
}

function addRowDeparator(row, type){
	for (var i = 0; i < $(row).length; i++) {
		if(type == "1"){
			$(row[i]).find('td:eq(0)').find("#inputmenuName").html("").append("<hr class='sperator_1'/>");
			$(row[i]).find('td:eq(0)').find("a.btn").prop( "disabled", true );
			$(row[i]).find('td:eq(1)').find(".input-group ").hide();
		}
	}
}

function rankThree(curentRow){
	var idCurentRow = curentRow.attr("id");
	var idNextRow = curentRow.next().attr("id");
	if(idNextRow == "rankOne" || idNextRow == "rankTwo" || idNextRow == "rankThree"){
		return curentRow;
	}else if(idNextRow == "rankFour"){
		return rankThree(curentRow.next());
	}
}

function rankTwo(curentRow){
	var idCurentRow = curentRow.attr("id");
	var idNextRow = curentRow.next().attr("id");
	if(idNextRow == "rankOne" || idNextRow == "rankTwo"){
		return curentRow;
	}else if(idNextRow == "rankThree" || idNextRow == "rankFour"){
		return rankTwo(curentRow.next());
	}
}

function findParentRow(curentRow){
	var idCurentRow = curentRow.attr("id");
	var idNextRow = curentRow.next().attr("id");
	if(idNextRow == undefined){
		return curentRow;
	}else if(idNextRow == "rankOne"){
		return curentRow;
	}else if(idNextRow != "rankOne"){
		return findParentRow(curentRow.next());
	}
}

function menuTableCallback(table,direction,row){
	if(direction=="add"){
		
		swapRow(row, index);
		restyleRow(row);
		
		//initial sortable
		initialSortableOfMenu();
		
		var index = row.attr("data-ar-rgroupindex").split(".").length;
		
		if (index > MAX_NESTED_OBJECT) {
			row.find("a.glyphicon-circle-arrow-right.qp-button-action").hide();
		}
	}
	
}

function swapRow(curRow, fistIndex){
	var curGroupIndex = curRow.attr("data-ar-rgroupindex").split(".").length;
	if(curRow.next().attr("data-ar-rgroupindex") != undefined){
		var curNextGroupIndex = curRow.next().attr("data-ar-rgroupindex").split(".").length;
		if(curGroupIndex <= curNextGroupIndex){
			if(curGroupIndex != curNextGroupIndex){
				curRow.insertAfter(curRow.next());
				swapRow(curRow, fistIndex);
			}
		}
	}
	removeIconDeleteFistRow();
}

function restyleRow(row){
	var $row = $(row);
	var paddingWidth =  40;;
	var _stylePadding = {
			'width':paddingWidth +"px",
			'height': '100%',
			'text-align': 'center',
			'padding' : '5px',
			'float': 'left'
	};
	var level = ($row.attr("data-ar-rgroupindex") || "").split(".").length;
	if(level>2){
		$(row).find("select[name$='.dataType'] option[value=0]").remove();
	}
	var $paddingDiv = $("<div></div>").css(_stylePadding);
	
	if($row.hasClass("ar-dataRow")){
		var $inputDiv = $row.find("#fistDiv");
		$inputDiv.closest("div").css("padding-top",$inputDiv.closest("td").css("padding-top"));
		$inputDiv.closest("div").css("padding-left",$inputDiv.closest("td").css("padding-left"));
		$inputDiv.closest("div").css("padding-right",$inputDiv.closest("td").css("padding-right"));
		$inputDiv.closest("div").css("padding-bottom",$inputDiv.closest("td").css("padding-bottom"));
		$inputDiv.closest("td").css("padding","0px");
		for(var i=1;i<level;i++){
			$inputDiv.before($paddingDiv);
			$paddingDiv = $paddingDiv.clone();
		}
		$inputDiv.outerWidth($inputDiv.closest("td").outerWidth()-2-paddingWidth*(i-1));
//		var marginTop = ($inputDiv.outerHeight()-$inputDiv.find("input").outerHeight())/2;
//		var originHeight = $inputDiv.find("input").height();
//		$inputDiv.find("input").css("margin-top",marginTop+"px");
//		$inputDiv.find("input").height(originHeight)
	
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

function saveMenuSetting() {
	var table = "#tbl_list_details";
	arrMenuDesignItem = new Array();
	
	var mapKey = {};
	$(table).find(">tbody>tr.ar-dataRow").each(function(i) {
		var rgroup = $(this).attr("data-ar-rgroupindex");
		var menuDesignItem = new Object();
		menuDesignItem.menuName = $(this).find("input[name*='menuName']").val();
		menuDesignItem.actionUrlCode = $(this).find("input[name*='actionUrlCode']").val();

		
		menuDesignItem.itemSeqNo = i;
		$(this).find("input[name*='itemSeqNo']").val(menuDesignItem.itemSeqNo);
		var id = $(this).find("input[name*='menuItemId']").val();
		if (id == "") {
			id = mapKeyTemp["menuDesignItem"];
			mapKeyTemp["menuDesignItem"] = parseInt(id) + 1;
			id = "in" + mapKeyTemp["menuDesignItem"];
		}
		menuDesignItem.menuItemId = id;
		mapKey[rgroup] = id;
		$(this).find("input[name*='menuItemId']").val(id);

		var arrayIndex = rgroup.split(".");
		if (arrayIndex.length > 1) {
			var keyParent = "";
			for (var j = 0; j < arrayIndex.length - 1; j++) {
				if (j == 0) {
					keyParent = arrayIndex[j];
				} else {
					keyParent += "." + arrayIndex[j];
				}
			}
			menuDesignItem.parentMenuItemId = mapKey[keyParent];
			$(this).find("input[name*='parentMenuItemId']").val(menuDesignItem.parentMenuItemId);
		} else {
			menuDesignItem.parentDesignItemId = "";
		}
		menuDesignItem.groupId = ($(this).attr("data-ar-rgroup") == undefined || $(this).attr("data-ar-rgroup") == null) ? "": $(this).attr("data-ar-rgroup");
		menuDesignItem.tableIndex = rgroup;
		arrMenuDesignItem.push(menuDesignItem);
	});
	var value = JSON.stringify(arrMenuDesignItem);
	$(table).closest("form").find("input[name='jsonMenuDesignItem']").val(value);
	
}

/*
 * QuangVD
 * initial sortable for menu.
 */
function initialSortableOfMenu(){
	removeIconDeleteFistRow();
	var table = "#tbl_list_details";
	var listOfItems = $(table).find(">tbody>tr");
	$(table).sortable({
		// placeholder: "ui-sortable-placeholder"
		helper: function(e, ui) {
			ui.children().each(function() {
				$(this).width($(this).width());
			});
			return ui;
		},
		update: function(event, ui) {
		},
		items: listOfItems,
		cursor: 'move',
		handle: '.glyphicon-sort',
		scroll: false,
		start: function( event, ui ) {
			
		},
		stop: function( event, ui ) {
			var selectedItem = ui.item;
			var selectedGroup = $(selectedItem).attr("data-ar-rgroup");
			var selectedGroupIndex = $(selectedItem).attr("data-ar-rgroupindex");
			var isValid = true;
			// get previous item.
			var prevItem = $(selectedItem).prev();
			var prevGroup = $(prevItem).attr("data-ar-rgroup");
			var prevGroupIndex = $(prevItem).attr("data-ar-rgroupindex");
			// get next item.
			var nextItem = $(selectedItem).next();
			var nextGroup = $(nextItem).attr("data-ar-rgroup");
			var nextGroupIndex = $(nextItem).attr("data-ar-rgroupindex");
			
			// validate sortable with previous item and next item.
			var selectedLevel = selectedGroupIndex.split('.').length;
			if(nextGroup != undefined){
				var nextLevel = nextGroupIndex.split('.').length;
				if(selectedLevel >= nextLevel){
					if(prevGroup == undefined){
						if(selectedLevel > 1){
							isValid = false;
						}
					}else{
						var prevLevel = prevGroupIndex.split('.').length;
						if(prevLevel < selectedLevel -1){
							isValid = false;
						}
					}
				}else{
					isValid = false;
				}
			}else{
				if(prevGroup == undefined){
					if(selectedLevel > 1){
						isValid = false;
					}
				}else{
					var prevLevel = prevGroupIndex.split('.').length;
					if(prevLevel < selectedLevel -1){
						isValid = false;
					}
				}
			}
			// cancel this event.
			if(!isValid){
				$(this).sortable("cancel");
				alert("Cannot sort this item.");
				
				return;
			}
			
			//move all children.
			$(table).find("tr[data-ar-rgroup^='"+selectedGroupIndex+"']").each(function() {
				if(nextItem.length > 0 ){
					$(nextItem).before($(this));
				}else{
					$(table).find('tbody:last-child').append($(this));
				}
			});
			$.qp.ar.recalculateRowIndex(table,selectedGroup);
			removeIconDeleteFistRow();
		}
	});
	
}
/*
 * initial the CSS style of item
 */
function reIndexAllRowOfMenu(){
	var table = "#tbl_list_details";
	$(table).find(">tbody>tr.ar-dataRow").each(function() {
		if($(this).find("input[name$='.menuItemType']").val() == '1'){
			$(this).find('td:eq(0)').find("#inputmenuName").html("").append("<hr class='sperator_1'/>");
			$(this).find('td:eq(1)').find(".input-group ").hide();
		}
		restyleRow($(this));
	});
}

function clearActionParentMenu(){
	var table = "#tbl_list_details";
	$(table).find(">tbody>tr").each(function() {
		var idxCurrent = $(this).attr(CONST.ATTR_ROW_GROUPINDEX);
		var idxNext = $(this).next().attr(CONST.ATTR_ROW_GROUPINDEX);
		if(idxNext == (idxCurrent + ".1")){
			$(this).find('td:eq(1)').find(".input-group ").hide();
			$(this).find('td:eq(1)').find("input[name$='.screenIdAutocomplete']").val("");
			$(this).find('td:eq(1)').find("input[name$='.screenId']").val("");
			$(this).find('td:eq(1)').find("input[name=checkParent]").val(0);
		}else{
			var checkParent = $(this).find('td:eq(1)').find("input[name=checkParent]").val();
			if(checkParent == '0'){
				$(this).find('td:eq(1)').find(".input-group ").show();
			}
		}
	});
}

function setActionUrlCode(obj){
	if(obj.item != undefined){
		$(obj.target).closest("tr").find("input[name$='.actionUrlCode']").val(obj.item.optionLabel);
	}
}

function openDialogPreview(obj){
	saveMenuSetting();
	var postData = $('form[id=menuDesignForm]').serializeArray();
    var formURL = CONTEXT_PATH + "/menudesign/previewTemp";
    $.ajax(
    	    {
    	        url : formURL,
    	        type: "POST",
    	        data : postData,
    	        success:function(data, textStatus, jqXHR) 
    	        {
    	        	var href = CONTEXT_PATH + "/menudesign/preview";
    				$.fancybox.open([
    				                 {
    				                     'type': 'iframe',
    				                     'href' : href,
    				             		'width' : 1100,
    				            		'height' : '100%',
    				            		'autoSize' : false,
    				            		'autoScale' : true,
    				            		'autoDimensions' : true,
    				            		'hideOnOverlayClick' : false,
    				            		'transitionIn' : 'none',
    				            		'transitionOut' : 'none',
    				            		'type' : 'iframe',
    				            		'centerOnScroll' : true,
    				            		'enableEscapeButton' : false,
    				            		'onStart' : function() {
    				            			$("body").css("overflow", "hidden");
    				            		},
    				            		'onClosed' : function() {
    				            			$("body").css("overflow", "auto");
    				            		},
    				                 }
    				             ], {
    				                 padding : 0
    				});
    	        },
    	        error: function(jqXHR, textStatus, errorThrown) 
    	        {
    	            //if fails      
    	        }
    	    });
}

function removeIconDeleteFistRow(){
	var count = 0;
	$('#tbl_list_details').find("tbody tr").each(function() {
		if(count == 0){
			$(this).find('td:eq(2)').find(".btn-default").hide();
		}else{
			$(this).find('td:eq(2)').find(".btn-default").show();
		}
		count++;
	});
}
