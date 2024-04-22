$(document).ready(function() {
	//Allow table to sorting
	sortTable($("#patternTable"));
	
	//Event to process check/uncheck all checkboxes
	$("#checkAll").click(function(){
		var checkState = $(this).is(':checked');
		$("input:checkbox[name^='checkbox']").each(function(){
			$(this).prop('checked',checkState);
		});
		//Call update current pattern
		updateCurrentPattern();
	})
	
	
	// Clear dialog table content when hide dialog
	$('#conversionPatternSetting').on('hidden.bs.modal', function () {
		$("#conversionPatternTableContent").empty();
	})
	
});	

/**
 * Function to update current conversion pattern based on value of checkbox list
 */
function updateCurrentPattern(){
	 $("#currentPatternText").text('[%d{yyyy-MM-dd HH:mm:ss}] [%-5level] [%-48logger{48}]');
	 $("input:checkbox[name^='checkbox']").each(function(){
		 if($(this).is(':checked')){
			 $("#currentPatternText").text($("#currentPatternText").text() + $(this).val());
		 }
	 });
}

/**
 * Allow sort table by rows
 * @param table
 * 	input table
 */
function sortTable(table){
	if(table!= undefined){
		table.find("tbody").sortable({
			helper : function(e, ui) {
				ui.children().each(function() {
					$(this).width($(this).width());
				});
				return ui;
			},
			update : function(event, ui) {
			$.qp.reArrayIndex(table);
			$.qp.reCalIndex(table);
			updateCurrentPattern();
			},
			items : 'tr',
			cursor : 'move',
			handle : '.sortable'
		});
	}
}

/**
 * Open dialog to select conversion pattern
 * @param obj
 * @param logType
 * @param index
 */
function openDialogConversionPattern(selectedId, logType, index){
	$($("#conversionPatternSetting")).modal(
			{ 
				show: true,
				closable: false,
				keyboard:false,
				backdrop:'static'
			}
		);
	// Set log type and index for hidden fields
	$("#dialogSelectedId").val(selectedId);
	$("#dialogLogType").val(logType);
	$("#dialogLogIndex").val(index);
	$("#conversionPatternSetting").find("input[id='checkAll']").removeAttr("checked");
	
	var sortedArr = getArrayOfSortedDetailConversionPattern(logType, index);
	
	buildConversionPatternTableContent(sortedArr);
};

function buildConversionPatternTableContent(sortedArray){
	var sortCell = "<td class='sortable' style='text-align: center;'><a href='javascript:' style='margin-top: 3px; cursor: move;' class='glyphicon glyphicon-sort' title='Move'></a></td>"; 
	var tableContent = $("#conversionPatternTableContent");
	for (i = 0; i < sortedArray.length; i++) {
	    var rowHTML = "";
	    var rowPatternId = "<input type='hidden' name='patternId" + i + "' value ='" + sortedArray[i].patternId +"' />";
	    var indexCell = "<td class='qp-output-fixlength tableIndex' id='spanArrIndexNo'>" + (i+1) + "</td>";
	    var patternNameCell = "<td><label>" + sortedArray[i].patternName + "</label></td>";
	    var patternRemarkCell ="<td><label >" + sortedArray[i].remark + "</label></td>";
	    var checkboxStatus = "" ;
//	    if(sortedArray[i].itemSequence < Number.MAX_VALUE){
//	    	checkboxStatus = "checked= 'true'";
//	    }
	    var checkboxCell = "<td><input id='checkbox" + i +"' name='checkbox "+ i +"' class='qp-input-checkbox-margin qp-input-checkbox' type='checkbox' value='" + sortedArray[i].patternCode + "'" + checkboxStatus + "></td>";
	    rowHTML = "<tr>" + rowPatternId+ indexCell + patternNameCell + patternRemarkCell + checkboxCell + sortCell + "</tr>";
	    tableContent.append(rowHTML);
	}
	
	//Add event for each checkbox
	$("input:checkbox[name^='checkbox']").each(function(){
		$(this).change(function(){
			updateCurrentPattern();
		})
	})
	
	//Update current pattern
	$("#currentPatternText").text($("textarea[name='" +$("#dialogSelectedId").val() + "conversionPattern" + "']").val());
};

function getArrayOfSortedDetailConversionPattern(logType, index){
	var patternArr = [];
	// Clone array
	for (i = 0; i < LIST_PATTERN.length; i++) {
		patternArr.push({
	        patternId: LIST_PATTERN[i].patternId,
	        patternCode: LIST_PATTERN[i].patternCode ,
	        patternName: LIST_PATTERN[i].patternName,
	        remark: LIST_PATTERN[i].remark,
	        itemSequence: LIST_PATTERN[i].itemSequence
	    });
	}
	// Get sequence value from DB
	var valueArea = $("#patternHidden_"+ logType + "_" + index);
	valueArea.find("div[id^='pattern']").each(function(){
		var patternId = $(this).find("input[name$='.patternId']").val();
		var itemSequence =  $(this).find("input[name$='.itemSequence']").val();
		for (i = 0; i < patternArr.length; i++) {
			if(patternArr[i].patternId == patternId){
				patternArr[i].itemSequence = itemSequence;
			}
		}
	});
	
	// Sort array 
	patternArr.sort(compare);
	return patternArr;
};

/**
 * 
 * @param a
 * @param b
 * @returns {Number}
 */
function compare(a,b) {
	  if (a.itemSequence < b.itemSequence)
	    return -1;
	  if (a.itemSequence > b.itemSequence)
	    return 1;
	  return 0;
};

function saveConversionPattern(){
	var logType = $("#dialogLogType").val();
	var index = $("#dialogLogIndex").val();
	var selectedId = $("#dialogSelectedId").val() + "lstConversionPattern";
	// Get checked conversion pattern
	var patternArr = [];
	var total = 1;
	var tableContent = $("#conversionPatternTableContent");
	tableContent.find('> tr').each(function(){
		var patternId =  $(this).find("input[name^='patternId']").val();
		$(this).find("input:checkbox[name^='checkbox']").each(function(){
			if($(this).is(':checked')){
				patternArr.push({
			        patternId: patternId,
			        itemSequence: total++
				})
			 }
		});
		 
	 });
	
	var appendHTML = "";
	for (i = 0; i < patternArr.length; i++) {
		appendHTML += "<div id='pattern" + i + "'>"
		appendHTML += "<input type='hidden' id='" +selectedId + "[" + i + "].patternId" + "' name='" +selectedId + "[" + i + "].patternId" +"' value='" + patternArr[i].patternId +"' />";
		appendHTML += "<input type='hidden' id='" +selectedId + "[" + i + "].itemSequence" + "' name='" +selectedId + "[" + i + "].itemSequence" +"' value='" + patternArr[i].itemSequence +"' />";
		appendHTML += "</div>";
	}
	
	var valueArea = $("#patternHidden_"+ logType + "_" + index);
	valueArea.empty();
	valueArea.append(appendHTML);
	tableContent.empty();
	
	// Set conversion pattern to log detail
	$("textarea[name='" +$("#dialogSelectedId").val() + "conversionPattern" + "']").val($("#currentPatternText").text());
	$("#conversionPatternSetting").modal("hide");
};

