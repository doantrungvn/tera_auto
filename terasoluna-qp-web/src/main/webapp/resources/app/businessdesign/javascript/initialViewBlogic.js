/**
 * @author quangvd
 * Javascript for viewing business logic
 */
$(document).ready(function() {
	$.qp.formulabuilder.init();
	var blogicType = $("form").find("input[name='blogicType']").val();
	if((blogicType == "0" && (returnType == "0" || (returnType == "1" && requestMethod == "0")) )|| patternType == "1"){
		$("#tbl_outputbean_list_define").find(".bd-ou-screenitem-hidden").show();
	}else{
		$("#tbl_outputbean_list_define").find(".bd-ou-screenitem-hidden").hide();
	}
	
	if(blogicType == "0" && returnType == "1" && requestMethod == "4"){
		$("#tbl_inputbean_list_define").find(".bd-in-screenitem-hidden").show();
	}else{
		$("#tbl_inputbean_list_define").find(".bd-in-screenitem-hidden").hide();
	}

	
	$("a[href='#tabBusiness-inputbean']").on('shown.bs.tab', function (e) {
		$.qp.businessdesign.initData("tbl_inputbean_list_define");
		$(this).off('shown.bs.tab');
	});
	$("a[href='#tabBusiness-outputbean']").on('shown.bs.tab', function (e) {
		$.qp.businessdesign.initData("tbl_outputbean_list_define");
		$(this).off('shown.bs.tab');
	});
	$("a[href='#tabBusiness-objectdefinition']").on('shown.bs.tab', function (e) {
		$.qp.businessdesign.initData("tbl_objectdefinition_list_define");
		$(this).off('shown.bs.tab');
	});
	loadComponentByFunctiontype(blogicType);
	loadJsPlumb(true);
	setURLOfWS();
});