function changeChecked(obj) {
	var selectedModuleCode = $(obj).prop("checked");
	
	$('#tblModule').find("tbody tr").each(function() {
		$(this).find("input[type=checkbox]").prop("checked" , selectedModuleCode);
	});
}

$(function(){
//	$('input[name="scopeGenerateSource"]').click(function(){
		
//		if($(this).val() == '1' && $(this).is(':checked')) {
//			$('table[id="tblProject"]').show();
//			$('table[id="tblModule"]').hide();
//			$('#scopeGenerate').val("project");
//			$('table[id="tblGenerateSetting"]').find('>tbody>tr').show();
//			
//		} else if($(this).val() == '2' && $(this).is(':checked')) {
//			$('table[id="tblProject"]').hide();
//			$('table[id="tblModule"]').show();
			$('#scopeGenerate').val("module");
			var $trFirst = $('table[id="tblGenerateSetting"]').find('>tbody>tr[id="warfile"]');
			$trFirst.hide();
			$trFirst.nextAll().each(function(){
				if($(this).attr('id') != undefined){
					return false;
				}
				
				$(this).hide();
			});
//		}
//	});
	
	$('table[id="tblModule"]').find('tbody>tr').each(function(){
		
		if($(this).find(':hidden[name$=".selectedGenerate"]').val() == 1){
			$(this).find(':radio').prop('checked', true);
		} else {
			$(this).find(':radio').prop('checked', false);
		}
	});
	
	if ($("#fileName").val() != null && $("#fileName").val() != '') {
		window.location.href = CONTEXT_PATH + "/downloadExport?fileName=" + $("#fileName").val();
	}
	
	if ('${generateSourceCodeForm.selectType}' == '2') {
		$('[name="selectType"][value="2"]').attr('checked', 'checked');
		$('#tblModule').show();
		$('#tblProject').hide();
		$('[name="generateType"]').show();
		$('[name="generateTypeItem"]').show();
		$('[name="genAll"]').val('false');
		$('#generateTypeInfor').show();
	} else {
		$('[name="selectType"][value="1"]').attr('checked', 'checked');
		$('#tblModule').hide();
		$('#tblProject').show();
		$('[name="generateType"]').hide();
		$('[name="generateTypeItem"]').hide();
		$('[name="genAll"]').val('true');
		$('#generateTypeInfor').hide();
	}
});
	
function selectGenerateJSP(obj) {
	var boo = $("#tblGenerateSetting").find("input[name='checkToGenerateJSP']");
	if(!boo.is(":checked")) {
		boo.prop("value", "false");
	}
}
function changeSelectType() {
	var selectType = $('[name="selectType"]:checked').val();
	if (selectType == 1) {
		$('#tblModule').hide();
		$('#tblProject').show();
		$('[name="generateType"]').hide();
		$('[name="generateTypeItem"]').hide();
		$('[name="genAll"]').val('true');
		$('#generateTypeInfor').hide();
	} else {
		$('#tblModule').show();
		$('#tblProject').hide();
		$('[name="generateType"]').show();
		$('[name="generateTypeItem"]').show();
		$('[name="genAll"]').val('false');
		$('#generateTypeInfor').show();
	}
}