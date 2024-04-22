$(document).ready(function() {
	var obj = $("#tbl-database-setting").find("select[name=dbType]");
	
	var tableDbSetting = $(obj).closest("table");
	var dbType = $(obj).val();
	
	var dbPort = $(tableDbSetting).find("input[name=dbPort]");
	
	if(dbType == "1"){
		$(tableDbSetting).find("input[name=dbDriver]").val("org.postgresql.Driver");
		if (dbPort.val() != undefined &&  dbPort.val() == '') {
			dbPort.val("5432");
		}
		$(tableDbSetting).find("#dbNameLabel").html(dbMsgSource['sc.project.0017']);
		$(tableDbSetting).find("#dbNameLabelR").html(dbMsgSource['sc.sys.0029']);
		$(tableDbSetting).find("#schemaName").html(dbMsgSource['sc.importschema.0003']);
		
		$(tableDbSetting).find("#dbNamePostges").show();
		$(tableDbSetting).find("#dbNameOracle").hide();
		
	}else if(dbType == "2"){
		$(tableDbSetting).find("input[name=dbDriver]").val("oracle.jdbc.driver.OracleDriver");
		
		if (dbPort.val() != undefined &&  dbPort.val() == '') {
			dbPort.val("1521");
		}

		$(tableDbSetting).find("#dbNameLabel").html("");
		$(tableDbSetting).find("#dbNameLabelR").html("");
		$(tableDbSetting).find("#dbNamePostges").hide();
		$(tableDbSetting).find("#dbNameOracle").show();
		var connectionType = $("#tbl-database-setting").find("#connectionType").val();
		if(connectionType == "0"){
			$(tableDbSetting).find("#schemaName").html(dbMsgSource['sc.importschema.0006']);
			$("#tbl-database-setting").find("#sid").prop('checked', true);
		}else if(connectionType == "1"){
			$("#tbl-database-setting").find("#service").prop('checked', true);
			$(tableDbSetting).find("#schemaName").html(dbMsgSource['sc.importschema.0007']);
		}else if(connectionType == ""){
			$(tableDbSetting).find("#schemaName").html(dbMsgSource['sc.importschema.0006']);
			$("#tbl-database-setting").find("#sid").prop('checked', true);
			$("#tbl-database-setting").find("#connectionType").val(0)
		}
	}
	
	
	$.qp.undoFormatNumericForm($("#tbl-database-setting"));
	$("#tbl-database-setting").find("#dbPort").on('focusout', function(e){
		$.qp.undoFormatNumericForm($("#tbl-database-setting"));	
	});
	$("#tbl-database-setting").find("#dbPort").on('change', function(e){
		$.qp.undoFormatNumericForm($("#tbl-database-setting"));	
	});
});

function changeDataType(obj){
	var tableDbSetting = $(obj).closest("table");
	var dbType = $(obj).val();
	var sid = $("#tbl-database-setting").find("#sid");
	
	
	if(dbType == "1"){
		$(tableDbSetting).find("input[name=dbDriver]").val("org.postgresql.Driver");
		$(tableDbSetting).find("#dbNameLabel").html(dbMsgSource['sc.project.0017']);
		$(tableDbSetting).find("#dbNameLabelR").html(dbMsgSource['sc.sys.0029']);
		$(tableDbSetting).find("#schemaName").html(dbMsgSource['sc.importschema.0003']);
		
		$(tableDbSetting).find("#dbNamePostges").show();
		$(tableDbSetting).find("#dbNameOracle").hide();
		
		$(tableDbSetting).find("input[name=dbName]").val("");
		$(tableDbSetting).find("input[name=schemaName]").val("");
		$(tableDbSetting).find("input[name=dbHostName]").val("");
		$(tableDbSetting).find("input[name=dbPort]").val("5432");
		$(tableDbSetting).find("input[name=dbUser]").val("");
		$(tableDbSetting).find("input[name=dbPassword]").val("");
	}else if(dbType == "2"){
		if(sid.prop('checked') == true) {
			$(tableDbSetting).find("#schemaName").html(dbMsgSource['sc.importschema.0006']);
		}else{
			$(tableDbSetting).find("#schemaName").html(dbMsgSource['sc.importschema.0007']);
		}
		$(tableDbSetting).find("input[name=dbDriver]").val("oracle.jdbc.driver.OracleDriver");
		$(tableDbSetting).find("#dbNameLabel").html("");
		$(tableDbSetting).find("#dbNameLabelR").html("");
		$(tableDbSetting).find("#dbNamePostges").hide();
		$(tableDbSetting).find("#dbNameOracle").show();
		
		$(tableDbSetting).find("input[name=schemaName]").val("");
		$(tableDbSetting).find("input[name=dbHostName]").val("");
		$(tableDbSetting).find("input[name=dbPort]").val("1521");
		$(tableDbSetting).find("input[name=dbUser]").val("");
		$(tableDbSetting).find("input[name=dbPassword]").val("");
		
		var connectionType = $("#tbl-database-setting").find("#connectionType").val();
		if(connectionType == "0"){
			$("#tbl-database-setting").find("#sid").prop('checked', true);
		}else if(connectionType == "1"){
			$("#tbl-database-setting").find("#service").prop('checked', true);
		}else if(connectionType == ""){
			$("#tbl-database-setting").find("#sid").prop('checked', true);
			$("#tbl-database-setting").find("#connectionType").val(0)
		}
	}
}

function changeConnectionType(){
	
	var sid = $("#tbl-database-setting").find("#sid");
	var service = $("#tbl-database-setting").find("#service");
	$("#tbl-database-setting").find("input[name=schemaName]").val("");
	if(sid.prop('checked') == true) {
		$("#tbl-database-setting").find("#schemaName").html(dbMsgSource['sc.importschema.0006']);
		$("#tbl-database-setting").find("#connectionType").val(0);
	}else{
		$("#tbl-database-setting").find("#schemaName").html(dbMsgSource['sc.importschema.0007']);
		$("#tbl-database-setting").find("#connectionType").val(1);
	}
}