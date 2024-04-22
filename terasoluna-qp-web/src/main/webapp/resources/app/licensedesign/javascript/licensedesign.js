$(function(){
	if ($("#fileName").val() != null && $("#fileName").val() != '') {
		window.location.href = CONTEXT_PATH + "/downloadExport?fileName=" + $("#fileName").val();
	}
});
function checkBeforeSubmit() {
	$.qp.undoFormatNumericForm($('#licenseDesignSearchForm'));
	$('#licenseDesignSearchForm').submit();
}