$( document ).ready(function() {
				
		for(var j = 0; j< parentId.length;j++){
			var i = 0 ;
			$("#tblPermission tbody").find("input[type=checkbox][tag='"+parentId[j]+"']").each(function() {
			if(!$(this).prop("checked")){
				i++;
			}
		});
		if(i == 1){
			$("[id='"+parentId[j]+"']").prop("checked" , true);
			}
		}
});
			
function changeModule(obj) {
	if($(obj).val() == -1) {
		$("#tblPermission tbody").find("tr").each(function() {
			$(this).show();
		});
	} else {
		$("#tblPermission tbody").find("tr").each(function() {
			if($(this).attr("tag") != undefined) {
				var tabOfTr = $(this).attr("tag");
				if(tabOfTr == $(obj).val()) {
					$(this).show();
				} else {
					$(this).hide();
				}
			}
		});
	}
}

function onChangePermission(obj){
	var selectedAccountPermission = $(obj).prop("checked");
	var parentId = $(obj).closest("tr").attr("tag");

	var html = " <span class=\"glyphicon glyphicon-ok\"></span>";
	if (selectedAccountPermission) {
		$(obj).parents("tr").children("td[name=summary]").html(html);
	} else {
		var rolePermission = $(obj).parents("tr").find("input[type=hidden][name$=hiddenHaveSameRole]").val();
		if (rolePermission == undefined){
			rolePermission = false;
		} else {
			rolePermission = eval(rolePermission);
		}
		if (rolePermission) {
			$(obj).parents("tr").children("td[name=summary]").html(html);
		} else {
			$(obj).parents("tr").children("td[name=summary]").empty();
		}	
	}
	
	if(!selectedAccountPermission){
		$("[id='"+parentId+"']").prop("checked" , false);
	} else {
		var i = 0 ;
		$("#tblPermission tbody").find("input[type=checkbox][tag='"+parentId+"']").each(function() {
			if(!$(this).prop("checked")){
				i++;
			}
		});
		if(i == 1){
			$("[id='"+parentId+"']").prop("checked" , true);
		}
	}
}

function prepareForSumary(obj) {
	var selectedAccountPermission = $(obj).prop("checked");
	
	var html = " <span class=\"glyphicon glyphicon-ok\"></span>";
	if (selectedAccountPermission) {
		$(obj).parents("tr").children("td[name=summary]").html(html);
	} else {
		var rolePermission = $(obj).parents("tr").find("input[type=hidden][name$=hiddenHaveSameRole]").val();
		if (rolePermission == undefined){
			rolePermission = false;
		} else {
			rolePermission = eval(rolePermission);
		}
		
		if (rolePermission) {
			$(obj).parents("tr").children("td[name=summary]").html(html);
		} else {
			$(obj).parents("tr").children("td[name=summary]").empty();
		}	
	}
}


function changeChecked(obj) {
	var selectedModuleCode = $(obj).prop("checked");
	var moduleCodeValue = $(obj).attr("tag");

	$("#tblPermission tbody").find("input[type=checkbox][tag='"+moduleCodeValue+"']").each(function() {
		$(this).prop("checked" , selectedModuleCode);
		prepareForSumary(this);
	});
}