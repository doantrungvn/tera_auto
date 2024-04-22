$(document).ready(function(){
	changeScope({target:$("input[name=scope]")});
	systemFlagChange($("input[type=radio][name$='systemFlag']:checked"));
})
function systemFlagChange(obj){
	if($(obj).val() == "1"){
		$(obj).closest("tr").next().find("th *:not(.path),td *:not(.path)").hide();
		$(obj).closest("tr").next().find(".path").show();
	} else if($(obj).val() == "2") {
		$(obj).closest("tr").next().find("th *:not(.path),td *:not(.path)").show();
		$(obj).closest("tr").next().find(".path").hide();
	}
	$(".qp-required-field").show();
};
function changeScope(event, ui){
	var scope = $(event.target).val();
	if( scope != 'system'){
		$(event.target).closest("tr").next().hide();
		$(event.target).closest("td").next().find("*").hide();
	}
	else {
		$(event.target).closest("tr").next().show();
		$(event.target).closest("td").next().find("*").show();
	}
	systemFlagChange($("input[type=radio][name$='systemFlag']:checked"));
}
function loadLibraryScopeDefault(obj) {
	
	var results = [];
	jQuery.each(CL_LIBRARY_SCOPE, function(key, value) {
		results.push({
			"optionLabel" : value,
			"optionValue" :value
		});
	});
	return results;
}

function loadLibraryTypeDefault(obj) {
	
	var results = [];
	jQuery.each(CL_LIBRARY_TYPE, function(key, value) {
		results.push({
			"optionLabel" : value,
			"optionValue" :value
		});
	});
	return results;
}
function checkBeforeSubmit(){
    var fileUpload = $("span.file-input-name").text();
    
    if (fileUpload != undefined || fileUpload != "") {
        $("#uploadFileName").val(fileUpload);
    }
    
	var uploadFile = $("[name='uploadFileContent']")[0].files[0];
	var url = CONTEXT_PATH + "/account/getMaxSizeUpload?r="+Math.random();
	var maxSize = parseInt($.qp.getTextResult(url)) || 0;
	
	if(uploadFile.size > maxSize*1024*1024){
		//alert(fcomMsgSource['err.sys.0201']);
		$.qp.alert(fcomMsgSource['err.sys.0208'].replace("{0}",maxSize));
		return false;
	}
	
	return true;
}
