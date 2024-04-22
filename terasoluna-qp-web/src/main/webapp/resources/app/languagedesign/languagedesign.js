
function typeChangeLanguage(obj,type){
	$(obj).closest("table").find("tbody>tr").find("select[name$='.languageCode'] option[value='"+$(obj).closest("td").next().text()+"']").prop("disabled",false);
	$(obj).closest("table").find("tbody>tr").find("select[name$='.languageCode'] option[value='"+$(obj).closest("td").next().text()+"']").show()
	var name = $(obj).find("option[value='"+$(obj).val()+"']").text();
	var code = $(obj).val();
	if (type == 'modify') {
		$(obj).closest("td").next().next().text(code);
		$("input[name=languageName").val(name)
	}
	else {
		$(obj).closest("td").next().text(code);
	}
	
	
	$(obj).next("input[name$='.languageName']").val(name);
	
	$(obj).closest("table").find("tbody>tr").not($(obj).closest("tr")).find("select[name$='.languageCode'] option[value='"+code+"']").prop("disabled",true);
	$(obj).closest("table").find("tbody>tr").not($(obj).closest("tr")).find("select[name$='.languageCode'] option[value='"+code+"']").hide()
};

function typeChangeCountry(obj){
	var name = $(obj).val();
	$("#txtCountryCode").text(name);
	$(obj).next("input[type='hidden']").val($(obj).find("option[value='"+name+"']").text());
};

function submitName(obj)
{
	var name = $(obj).find("option:selected").text();
	$("#languageName").val(name);
}

function removeExistingLanguage(table,direction,row){
	if(direction == "add"){
		$(table).find("tbody>tr").not(row).each(function(){
			$(row).find("select[name$='.languageCode'] option[value='"+$(this).find("[name$='.languageCode']").closest("td").next().text()+"']").prop("disabled",true);
			$(row).find("select[name$='.languageCode'] option[value='"+$(this).find("[name$='.languageCode']").closest("td").next().text()+"']").hide();
		});
	} else {
		if($(row).find("[name$='.languageCode']").val()){
			$(table).find("tbody>tr").find("select[name$='.languageCode'] option[value='"+$(row).find("[name$='.languageCode']").closest("td").next().text()+"']").prop("disabled",false);
			$(table).find("tbody>tr").find("select[name$='.languageCode'] option[value='"+$(row).find("[name$='.languageCode']").closest("td").next().text()+"']").show();
		}
	}
}

