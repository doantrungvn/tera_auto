function requireModule() {
	var anyBoxesChecked = false;
    
    $('#tblListModule').find("tbody tr").each(function() {
    	if ($(this).find("input[type=checkbox]").is(":checked")) {
            anyBoxesChecked = true;
            $(this).find("input[name$='.selectedGenerate']").val(1);
        }
    });

    if (anyBoxesChecked == false && $('#scopeGenerate').val() == "MODULE") {
     $.qp.alert(fcomMsgSource['err.sys.0116'].replace("{0}",$('#generateHTMLForm #module').val()));
      return false;
    }
}

function changeChecked(obj) {
	var selectedModuleCode = $(obj).prop("checked");
	
	$('#tblListModule').find("tbody tr").each(function() {
		$(this).find("input[type=checkbox]").prop("checked" , selectedModuleCode);
		$(this).find("input[name$='.selectedGenerate']").val(1);
	});
}

$( document ).ready(function() {
	$("#tblListModule tbody").find("input[type=checkbox]").click(function() {
		if (!$(this).prop("checked")) {
			$("#tblListModule thead").find("input[type=checkbox][name='btnCheckAll']").prop("checked" , false);
		} else {
		    var countcheck = $('#tblListModule tbody input[type="checkbox"]').length;
		    var countchecked = $('#tblListModule tbody input[type="checkbox"]:checked').length;
		    
		    if (countcheck == countchecked) {
		    	$("#tblListModule thead").find("input[type=checkbox][name='btnCheckAll']").prop("checked" , true);
		    }
		}
	});
	
	checkAfterReLoad();
	if ($("#fileName").val() != null && $("#fileName").val() != '') {
		window.location.href = CONTEXT_PATH + "/downloadExport?fileName=" + $("#fileName").val();
	}
	
	$('#project').removeClass("hidden");
	$('#listModule').addClass("hidden");
});

function checkAfterReLoad() {
	var i = 0;
	$("#tblListModule tbody").find("input[type=checkbox]").each(function() {
		if($(this).is(":checked")) {
			i++;
		}
	});
	
	if (i == 0) {
		$("#tblListModule").find("input[type=checkbox][name='btnCheckAll']").prop("checked",false);
		return;
	}
	
	if (i == $("#tblListModule tbody").find("input[type=checkbox]").length) {
		$("#tblListModule").find("input[type=checkbox][name='btnCheckAll']").prop("checked",true);
	}
}

function selectTypeScope(thiz){
	if($(thiz).val() == '1' && $(thiz).is(':checked')) {
		$('#project').removeClass("hidden");
		$('#listModule').addClass("hidden");
		$('#scopeGenerate').val("project");
		$('#btnSubmit').show();
	} else if($(thiz).val() == '2' && $(thiz).is(':checked')) {
		$('#project').addClass("hidden");
		$('#listModule').removeClass("hidden");
		$('#scopeGenerate').val("module");
		if ($("#tblListModule tbody").children().length == 0) {
			$('#btnSubmit').hide();
		} else {
			$('#btnSubmit').show();
		}
	}
}
