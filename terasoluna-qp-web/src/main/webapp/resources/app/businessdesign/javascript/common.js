/**
 * common function
 */
function isEmptyQP(value){
	if(value == undefined || value == null){
		return true;
	}else{
		if(typeof(value) == "string" &&  value.trim().length == 0)
			return true;
		if(typeof(value) == "object" &&  $.isEmptyObject(value))
			return true;
		return false;
	}
}
function toStringQP(value){
	if(value == undefined || value == null){
		return "";
	}else{
		return value.toString();
	}
}
function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}
function deCapitalizeFirstLetter(string) {
    return string.charAt(0).toLowerCase() + string.slice(1);
}
function convertToString(json) {
	return  JSON.stringify(json);
}
function convertToJson(string) {
	if (string != undefined) {
		var json = {};
		try {
			json = JSON.parse(string);
	    } catch (e) {
	    	json = JSON.parse("{" + string + "}");
	    }
	    return json;
	}
}