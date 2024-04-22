function openDialogSettingRemarkColumn(obj) {
	$($("#settingRemarkColumn")).modal(
		{ 
			show: true,
			closable: false,
			keyboard:false,
			backdrop:'static'
		}
	);
	loadRemark(obj);
	$("#currentRow").val($(obj).closest("tr").find("input[name$='.name']").attr("name").match(/\d+/)[0]);
}

function saveRemarkSetting(obj){
	var curentTR = $('tr', tmp_list_table).eq(parseInt($("#currentRow").val()) +1 );
	curentTR.find("input[name$='.remark']").val($("#tableRemark").find("textarea[name=remarkColumn]").val());
	$("#settingRemarkColumn").modal("hide");
}

function loadRemark(obj){
	$("#tableRemark").find("textarea[name=remarkColumn]").val($(obj).closest("tr").find("input[name$='.remark']").val());	
}