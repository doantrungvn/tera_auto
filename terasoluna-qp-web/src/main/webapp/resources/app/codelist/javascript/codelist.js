$(document).ready(function() {
	supportOnyValue();
	supportMultiVal();
});

function supportMultiVal() {
	if ($("#multivalueFlg").val()==1) {
		$("#tbl_list_result").find(".multiVal").show();
		$("#multiValue").removeClass("glyphicon-eye-close").addClass("glyphicon-eye-open");
	} else {
		$("#multiValue").removeClass("glyphicon-eye-open").addClass("glyphicon-eye-close");
		$("#tbl_list_result").find(".multiVal").hide();
	}
}

function processSupportMultiVal(Obj) {
	if ($(Obj).hasClass("glyphicon-eye-open")) {
		$("#multivalueFlg").val("0");
	} else {
		$("#multivalueFlg").val("1");
	}
	supportMultiVal();
}

function supportOnyValue() {
	if ($("input[name=isOptionValude]").is(':checked')){
		$("#tbl_list_result").find(".colName").hide();
		$("#isOptionValude").val("1");
	} else {
		$("#tbl_list_result").find(".colName").show();
		$("#isOptionValude").val("0");
	}
}