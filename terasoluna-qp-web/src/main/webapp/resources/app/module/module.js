function changeProjectAC(obj){
	var value = $(obj.target).next("input[type=hidden]").val();
	$("#moduleForm").find("input[name$='.tblDesignIdAutocomplete']").each(function(){
		$(this).attr("arg01",value);
		$(this).data("ui-autocomplete")._trigger("change");
	});
};

function changeDefaultGeneration(obj) {
	var value = $(obj).val();
	var defaultSettings = $("#div_default_settings");
	if(value == "0"){
		defaultSettings.show();
	} else if(value == "1"){
		defaultSettings.hide();
	}
}

function changeModuleType() {
	var online = $("#tbl_module_information").find("#moduleType1");
	var batch = $("#tbl_module_information").find("#moduleType2");
	var defaultSettings = $("#div_default_settings");
	
	if(online.prop('checked') == true) {
		
		$("#divDefaultGenerationSetting").show();
		$("#defaultGenerationSettingTH").show();
		var normal = $("#tbl_module_information").find("#defaultGenerationSetting1");
		var blank = $("#tbl_module_information").find("#defaultGenerationSetting2");
		
		if(normal.prop('checked') == true) {
			defaultSettings.show();
			
		}else{
			defaultSettings.hide();
			
		}
		
	}
	
	if(batch.prop('checked') == true) {
		defaultSettings.hide();
		$("#divDefaultGenerationSetting").hide();
		$("#defaultGenerationSettingTH").html("");
	}else{
		$("#defaultGenerationSettingTH").html(dbMsgSource['sc.module.0036']);
	}
}

$(document).ready(function(){
	$("#moduleForm").find("a.glyphicon-plus").click(function(){
		var projectId = $("#moduleForm").find("input[name='projectId']").val();
		if (projectId) {
			$("#moduleForm").find("input[name$='.tblDesignIdAutocomplete']").each(function(){
				$(this).attr("arg01", projectId);
			});
		}
	});
	
	var online = $("#tbl_module_information").find("#moduleType1");
	var batch = $("#tbl_module_information").find("#moduleType2");
	var defaultSettings = $("#div_default_settings");
	if(batch.prop('checked') == true) {
		batch.prop('checked',true);
		defaultSettings.hide();
		$("#divDefaultGenerationSetting").hide();
	} else {
		online.prop('checked',true);
		var normal = $("#tbl_module_information").find("#defaultGenerationSetting1");
		var blank = $("#tbl_module_information").find("#defaultGenerationSetting2");
		var defaultGenerationSettingInit = $("#defaultGenerationSettingInit").val();
		if(defaultGenerationSettingInit != ""){
			if(normal.prop('checked') == true) {
				$("#div_default_settings").show();
			}else{
				$("#div_default_settings").hide();
			}
		}else{
			normal.prop('checked', true);
		}
	}
	
	changeEntityType();
});