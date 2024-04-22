function isProhibitChar(val){
    if(val.indexOf("'") != -1 || val.indexOf('"') != -1){
    	return true;
    } else {
    	return false;
    }
}

function isNotAlphanumeric(val){
    return val.match(/^[a-zA-Z0-9]+$/) ? false : true;
} 