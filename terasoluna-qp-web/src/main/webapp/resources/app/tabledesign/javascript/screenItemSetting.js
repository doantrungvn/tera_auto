$(document).ready(function() {
	$("#tblScreenItemSetting tbody").find("input[type=checkbox]").click(function() {
		if ($(this).prop("checked")) {
			$(this).closest("tr").removeClass("trDisable");
		} else {
			$(this).closest("tr").addClass("trDisable");
		}
	});
});