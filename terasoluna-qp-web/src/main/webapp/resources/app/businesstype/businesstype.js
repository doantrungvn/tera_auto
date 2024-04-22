function changeProjectAC(obj){
	var value = $(obj.target).next("input[type=hidden]").val();
	var nextInput = $(obj.target).closest("tr").find("input[id='moduleIdAutocompleteId']");
	var nextHidden = nextInput.next("input[type=hidden]");
	nextInput.val("");
	nextInput.attr("arg01",value);
	nextHidden.val("");
	nextInput.data("ui-autocomplete")._trigger("change");
};