function changeProjectAC(obj){
	var value = $(obj.target).next("input[type=hidden]").val();
	var nextInput = $(obj.target).closest("tr").find("input[id='moduleIdAutocompleteId']");
	var nextHidden = nextInput.next("input[type=hidden]");
	nextInput.val("");
	nextInput.attr("arg01",value);
	nextHidden.val("");
	nextInput.data("ui-autocomplete")._trigger("change");
};		

function changeModuleAC(obj){
	var moduleId = $("input[name=moduleId]").val();
	$("#screenIdAutocompleteId").val("");
	$("#screenIdAutocompleteId").attr("arg01", moduleId);
	$("#screenIdAutocompleteId").data("ui-autocomplete")._trigger("change");
};

function updateModuleAC(event){
	var columnAutocomplete = $("input[name='moduleId']");
	var select = $(columnAutocomplete).next().find("select");
	var optionSelected = $("option[value=" + columnAutocomplete.val() + "]");
	$("#moduleCode").val(event.item.output01);
};

function updateCountryAC(event){
	$("#countryCode").val(event.item.output01);
	$("#languageCode").val(event.item.optionValue);
};

function changeFromLanguageAC(obj) {
	if(obj.item != undefined){
		$("input[name=fromLanguageCode]").val("");
		$("input[name=fromLanguageCode]").val(obj.item.optionValue);
		var fromLanguageId = $("input[name=fromLanguageId]").val();
		$("#toLanguageIdAutocompleteId").attr("arg01", fromLanguageId);
	}else{
		$("#toLanguageIdAutocompleteId").attr("arg01", "");
	}
};

function changeToLanguageAC(obj) {
	if(obj.item != undefined){
		$("input[name=toLanguageCode]").val("");
		$("input[name=toLanguageCode]").val(obj.item.optionValue);
		var toLanguageId = $("input[name=toLanguageId]").val();
		$("#fromLanguageIdAutocompleteId").attr("arg01", toLanguageId);
		$("input[name=toCountryCode]").val(obj.item.output01);
	}else{
		$("#fromLanguageIdAutocompleteId").attr("arg01", "");
	}
};

function changeToLanguageOfSystemAC(obj) {
	$("input[name=toLanguageCode]").val("");
	var toLanguageCode = $("input[name=toLanguageCode]").val(obj.item.optionValue);
	$("#fromLanguageCodeAutocompleteId").attr("arg01", toLanguageCode);
};

//check rule {} when modify
function getRule(str){
	var regex = /\{\d*\}/g;
	var index = 0;
	var t = "";
	if(str != "" && str != undefined) {
		t = str.match(regex);
	}
	if(t == null){
		return;
	}
	var result = "";
	for(i = 0; i < t.length; i++){
		result += t[i];
	}
	return result;
}

// validate on client when input string == null
function validate() {
	var result = true;
	var mess = "";
	$("#tableResult").children('tbody').find("input[name$='fromMessageString']").each(function (index){
		if($(this).val().trim() == ""){
			mess += fcomMsgSource['err.sys.0026'].replace("{0}",(index+1)).replace("{1}",dbMsgSource["sc.messagedesign.0017"]);
			mess += "\n";
		}
	});
	 if(mess.length > 0)
	{
		result = false;
	}
	if(result){
		return true;
	}else{
		alert(mess);
		return false;
	}
}

//when modify input, set value to input hidden
function hasModifyValue(obj){
	$(this).change(function() {
		$("div .table-responsive").find("input[id='checkChanged']").val("hasChangeValueInput");
		var checkbox = $(obj).nextAll('[type="checkbox"]');
		if(!checkModifyInput()){
			$(checkbox).attr("checked",true);
		}
	});
}
//check input hidden has value='hasChangeValueInput'. if it has value return false
function checkModifyInput(){
	var flag=true;
	var valueInput = $("div .table-responsive").find("input[id='checkChanged']").val();
	if("hasChangeValueInput" == valueInput){
		flag=false;
	}
	return flag;
}
//confirm to move page
$(document).ready(function(){
	$(".pagination").find("li").each(function (){
		$(this).click(function(){
			if($(this).hasClass("active") == false && $(this).hasClass("disabled") == false) {
				if(!checkModifyInput()){
					return $.qp.confirm(fcomMsgSource["inf.sys.0028"]);					
				}
			}
		}); 
	});
	
	$(".qp-table-list").find(".qp-link-action").each(function (){
	 	$(this).click(function(){
	 		if(!checkModifyInput()){
				return $.qp.confirm(fcomMsgSource["inf.sys.0028"]);
			}
		});   
	});
});

// when modify input, checkbox is checked