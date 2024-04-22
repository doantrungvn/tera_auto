$(document).ready(function() {
	$('#tblAllTable').find("tbody tr").each(function() {
		var tableDesignId = $(this).find("input[name=tableDesignId]").val();
		$(this).find("input[name=checkboxSingle]").on('click', function(e){
			if($(this).prop('checked')){
				$(this).closest("tr").find("[id^=listTableId]").val(tableDesignId);
			}else{
				$(this).closest("tr").find("[id^=listTableId]").val("");
			}
			changeStatusOfParent();
		});
	});
	
	$('#tblAllTable').find("input[name=checkboxAll]").on('click', function(e){
		if($(this).prop('checked')){
			$('#tblAllTable').find("tbody tr").each(function() {
				var tableDesignId = $(this).find("input[name=tableDesignId]").val();
				$(this).find("[id^=listTableId]").val(tableDesignId);
			});
		}else{
			$('#tblAllTable').find("tbody tr").each(function() {
				$(this).find("[id^=listTableId]").val("");
			});
		}
	});

});

function changeStatusOfParent() {
	var countcheck = $('#tblAllTable tbody input[type="checkbox"]').length;
	var countchecked = $('#tblAllTable tbody input[type="checkbox"]:checked').length;

	if (countcheck == countchecked) {
		$("#tblAllTable thead").find("input[type=checkbox][name='checkboxAll']").prop("checked" , true);
	} else {
		$("#tblAllTable thead").find("input[type=checkbox][name='checkboxAll']").prop("checked" , false);
	}
}

function selectedTableGen(){
	
	var genDropCheckbox = $("#tblSearch").find("input[name=genDrop]");
	
	if(genDropCheckbox.prop("checked") == true){
		$("#generateForm").find("input[name=genDrop]").val(1);
	}else{
		$("#generateForm").find("input[name=genDrop]").val(0);
	}
	
	var listTable = "";
	$("#tblAllTable").find("tbody tr").each(function() {
		if($(this).find("input[name=checkboxSingle]").prop('checked') && listTable != ""){
			listTable = listTable + "," + $(this).find("input[name=tableDesignId]").val();
		}
	});
	
	$("#listTable").val(listTable);
	
	return requireTable();
}

function changeMode(obj){
	if(obj.value == "all"){
		$("#generateMode").val("0");
	} else if(obj.value == "customTable"){
		$("#generateMode").val("1");
		var sqlScripts =  $("#sqlScripts").val();
		if (sqlScripts == null || sqlScripts == ""){
			$("#generateForm").submit();
		} else {
			var btnSubmit = $('.qp-div-action').find('button.qp-dialog-confirm');
			btnSubmit.attr("messageCustom", dbMsgSource["inf.sys.0032"]);
			btnSubmit.trigger("click");
			btnSubmit.removeAttr("messageCustom");
		}
	}  else if(obj.value == "allForLog"){
		$("#generateMode").val("2");
	}
}

function selectAll(obj){
	var table = $("#tblAllTable");
	var checkboxALl = $(table).find("input[name=checkboxAll]");
	
	if(obj.checked){
		$(table).find("tbody tr").each(function() {
			$(this).find("input[name=checkboxSingle]").prop('checked', true);
		});
	}else{
		$(table).find("tbody tr").each(function() {
			$(this).find("input[name=checkboxSingle]").prop('checked', false);
		});
	}
}

function requireTable() {
	var anyBoxesChecked = false;
    $('#tblAllTable tbody input[type="checkbox"]').each(function() {
        if ($(this).is(":checked")) {
            anyBoxesChecked = true;
        }
    });

    if (anyBoxesChecked == false) {
     $.qp.alert(fcomMsgSource['err.sys.0116'].replace("{0}",$('#generateForm #message_table').val()));
      return false;
    }
}
