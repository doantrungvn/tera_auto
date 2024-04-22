$(document).ready(function() {
	$('[name=autocomplete]').addClass('hidden');
	
	//QuyND: change logic to initiate auto complete on load
	var dataType = $('[name=dataType]');
	dataTypeChange(dataType,true);
	
});
function dataTypeChange(obj,isNotReset){
	var objectId = $('[name=objectIdAutocomplete]');
	var dataType = $(obj).val();
	
	if(!isNotReset){
		$(objectId).val("");
		$(objectId).next().val("");
		$(objectId).data("ui-autocomplete")._trigger("close");
	}
	
	switch (dataType) {
	case "14":
		$('[name=autocomplete]').removeClass('hidden');
		$(objectId).attr('selectSqlId', 'getAutocompleteEntity');
		break;
	case "16":
		$('[name=autocomplete]').removeClass('hidden');
		$(objectId).attr('selectSqlId', 'getAutocompleteCommonObject');
		break;
	case "17":
		$('[name=autocomplete]').removeClass('hidden');
		$(objectId).attr('selectSqlId', 'getAutocompleteExternalObject');
		break;
	default:
		$('[name=autocomplete]').addClass('hidden');
	}
}

function validateBeforeSubmit(table) {
	var messages="";
	return messages;
}