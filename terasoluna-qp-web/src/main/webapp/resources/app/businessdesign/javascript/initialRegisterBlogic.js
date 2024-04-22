/**
 * initial register business design screen
 * @author quangvd
 */
var instanceOfBlogic;
$(document).ready(function() {
	$.qp.formulabuilder.init();
	if((blogicType == "0" && (returnType == "0" || (returnType == "1" && requestMethod == "0")) )|| patternType == "1"){
		$("#tbl_outputbean_list_define").find(".bd-ou-screenitem-hidden").show();
	}else{
		$("#tbl_outputbean_list_define").find(".bd-ou-screenitem-hidden").hide();
	}
	if(blogicType == "0"){
		$("#tbl_outputbean_list_define").find(".bd-ou-setscope").show();
		
	}else {
		$("#tbl_outputbean_list_define").find(".bd-ou-setscope").hide();
	}
	
	if(blogicType == "0" && returnType == "1" && requestMethod == "4"){
		$("#tbl_inputbean_list_define").find(".bd-in-screenitem-hidden").show();
	}else{
		$("#tbl_inputbean_list_define").find(".bd-in-screenitem-hidden").hide();
	}
	if(blogicType == "0" || blogicType == "2"){
		$("#tbl_inputbean_list_define").find(".bd-in-getscope").show();
	}else{
		$("#tbl_inputbean_list_define").find(".bd-in-getscope").hide();
	}
	
	


	arrConnectionComponent = new Array();
	for (var i = 0; i < arrComponent.length; i++) {
		initConnectionTargetForComponent(arrComponent[i].id, arrComponent[i].componentType);
	}
	// validation data
	$(BD_TABS_NAME.BLOGICSETTING).on('shown.bs.tab', function(e) {
		saveParameterData();
	});
	$(BD_TABS_NAME.INPUTBEAN).on('hide.bs.tab', function(e) {
		// Validation when change tab
		var message = $.qp.businessdesign.validaion.validationInputBean(BD_TABLE_NAME.INPUTBEAN);
		if (message != undefined && message.length > 0) {
			$.qp.showAlertModal(message);
			e.preventDefault();
			return false;
		} else {
			saveInputBean();
		}
	});
	$(BD_TABS_NAME.OUTPUTBEAN).on('hide.bs.tab', function(e) {
		// Validation when change tab
		var message = $.qp.businessdesign.validaion.validationOutputBean(BD_TABLE_NAME.OUTPUTBEAN);
		if (message != undefined && message.length > 0) {
			$.qp.showAlertModal(message);
			e.preventDefault();
			return false;
		} else {
			saveOutputBean();
		}
	});
	$(BD_TABS_NAME.OBJECTDEFINITION).on('hide.bs.tab', function(e) {
		// Validation when change tab
		var message = $.qp.businessdesign.validaion.validationObjectDefinition(BD_TABLE_NAME.OBJECTDEFINITION);
		if (message != undefined && message.length > 0) {
			$.qp.showAlertModal(message);
			e.preventDefault();
			return false;
		} else {
			saveObjectDefinition();
		}
	});
	
	loadJsPlumb(false);
	initGroupNode();
	//Change Enhancement Blogic
	changeModuleTypeOfBlogic($("#tbl-blogicinformation").find("input[name='moduleType']"),true, $('#advanceReturnTypeFlag').val(), $('#advanceRequestMethodFlag').val(),blogicType);
	
	//load show/hide condition
	switch (blogicType.toString()) {
		case "1":
			var designTypeElement = $("#tbl-blogicinformation").find(":checkbox[name='customizeFlg']");
			if(designTypeElement.length >0)
				showHideFileContent(designTypeElement);
			break;
		case "2":
			setURLOfWS();
			break;
		case "3":
			//check max-row of input bean.
			var tableInput = $("table#tbl_inputbean_list_define");
			var countOfRow = $(tableInput).find("tbody > tr").length;
			var maxRow = $(tableInput).attr("max-row");
			if(maxRow != undefined && maxRow != ""){
				if(countOfRow >= maxRow)
					$(tableInput).next("div.qp-div-action-table").hide();
			}
			break;
		default:
			break;
	};

	$("a[href='#tabBusiness-inputbean']").on('shown.bs.tab', function (e) {
		$.qp.businessdesign.initAddButtonRow("tbl_inputbean_list_define","tbl_inputbean_list_define-action-template");
		$.qp.businessdesign.initData("tbl_inputbean_list_define");
		$(this).off('shown.bs.tab');
	});
	
	$("a[href='#tabBusiness-outputbean']").on('shown.bs.tab', function (e) {
		$.qp.businessdesign.initAddButtonRow("tbl_outputbean_list_define","tbl_outputbean_list_define-action-template");
		$.qp.businessdesign.initData("tbl_outputbean_list_define");
		$(this).off('shown.bs.tab');
	});
	
	$("a[href='#tabBusiness-objectdefinition']").on('shown.bs.tab', function (e) {
		$.qp.businessdesign.initAddButtonRow("tbl_objectdefinition_list_define","tbl_objectdefinition_list_define-action-template");
		$.qp.businessdesign.initData("tbl_objectdefinition_list_define");
		$(this).off('shown.bs.tab');
	});
	

	
});
//0 : register screen
var pageType = "0";
