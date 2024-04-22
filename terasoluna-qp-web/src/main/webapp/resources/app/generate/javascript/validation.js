function requireScreenDesign() {
	var anyBoxesChecked = false;
    $('#tblListScreen tbody input[type="checkbox"]').each(function() {
        if ($(this).is(":checked")) {
            anyBoxesChecked = true;
        }
    });

    if (anyBoxesChecked == false) {
     $.qp.alert(fcomMsgSource['err.sys.0116'].replace("{0}",$('#generateDbAndBlogicForm #messageScreen').val()));
      return false;
    }
}

function changeChecked(obj) {
	var selectedModuleCode = $(obj).prop("checked");
	$("#tblListScreen tbody").find("input[type=checkbox]").each(function() {
		$(this).prop("checked" , selectedModuleCode);
	});
}

$( document ).ready(function() {
	var countcheck = $('#tblListScreen tbody input[type="checkbox"]').length;
    var countchecked = $('#tblListScreen tbody input[type="checkbox"]:checked').length;
    
    if (countcheck == countchecked) {
    	$("#tblListScreen thead").find("input[type=checkbox][name='screenCode']").prop("checked" , true);
    }
	
	$("#tblListScreen tbody").find("input[type=checkbox]").click(function() {
		if (!$(this).prop("checked")) {
			$("#tblListScreen thead").find("input[type=checkbox][name='screenCode']").prop("checked" , false);
		} else {
		    var countcheck = $('#tblListScreen tbody input[type="checkbox"]').length;
		    var countchecked = $('#tblListScreen tbody input[type="checkbox"]:checked').length;
		    
		    if (countcheck == countchecked) {
		    	$("#tblListScreen thead").find("input[type=checkbox][name='screenCode']").prop("checked" , true);
		    }
		}
	});
});