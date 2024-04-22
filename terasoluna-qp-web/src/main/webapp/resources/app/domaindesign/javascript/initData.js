var DATATYPE = {
	TEXT : 1, 
	INTEGER : 2,
	DECIMAL : 3,
	DATE : 4,
	CHAR : 5,
	CURRENCY : 6,
	BOOLEAN : 7,
	TIME : 8,
	DATETIME : 9,
	BINARY : 10
};

var NUMERIC_TYPE = {
		INTEGER : 5,
		SMALLINT : 6,
		BIGINT : 7,
		SERIAL : 13,
		BIGSERIAL : 14,
		NUMBERIC : 4
};

var CHARACTER_TYPE = {
		CHARACTER_VARYING : 1,
		CHAR : 2,
		TEXT : 3
};

var DATATYPE_FLG = {
		PRIMITIVE: 0,
		DOMAIN_DATA: 1
};


/**
 * fix some max length
 * @param baseType
 * @returns {Number}
 */
function initializeMaxLengthDefault(baseType) {
	var maxLength = '';
	switch(parseInt(baseType)) {
		case 1://character varying
			maxLength = 200;
			break;
		case 2://char
			maxLength = 50;
			break;
		case 3://text
			//maxLength = 2000;
			break;
		case 4://numeric		
			maxLength = 20;
			break;
		case 5://integer
		case 13://Serial
			//maxLength = 10;
			break;
		case 6://smallInt
			//maxLength = 6;
			break;
		case 7://bigInt
		case 14://BigSerial
			//maxLength = 20;
			break;
		case 8://boolean
			//maxLength = 4;
			break;
		case 10://date
			//maxLength = 10;
			break;	
		case 11://Time
			//maxLength = 8;
			break;
		case 12://Datetime
			//maxLength = 19;
			break;
		case 15://currency
			maxLength = 23;
			break;
		default:
			maxLength = '';
	}
	return maxLength;
}
/**
 * fix some precision
 * @param baseType
 * @returns {Number}
 */
function initializePrecisionDefault(baseType) {
	var precision = '';
	switch(parseInt(baseType)) {
		case 4://numeric		
			precision = 2;
			break;
		case 15://currency
			precision = 3;
			break;
		default:
			maxLength = '';
	}
	return precision;
}


/**
 * 
 */
function initializeMaxlength(maxlength, decimalPart) {

	if (maxlength == undefined || maxlength == '') {
		maxlength = 0;
	}

	if (maxlength <= 0) return "";

	var str = "";

	if (decimalPart == undefined || decimalPart == '') {
		decimalPart = 0;
	}

	maxlength = Number(maxlength);
	decimalPart = Number(decimalPart);

	if (decimalPart >= maxlength) {
		decimalPart = 0;
	}

	maxlength = maxlength - decimalPart;

	for (var int = 0; int < maxlength; int++) {
		str = str.concat("9");
		if (int == maxlength - 1) {
			str = str.concat(".");
		}
	}

	for (var i = 0; i < decimalPart; i++) {
		str = str.concat("9");
	}

	return str;
}

function checkRange(operatorCode, maxVal, minVal, defaultVal, tblAdvance){
	if(operatorCode == "8"){
		if(tblAdvance.find("input[name=maxVal]").val() == "" || tblAdvance.find("input[name=minVal]").val() == ""){
			$.qp.showAlertModal(fcomMsgSource['err.sys.0025'].replace("{0}",dbMsgSource['sc.tabledesign.0043']));
			$("input[name=defaultValue]").focus();
			return true;
		}
		/*if(defaultVal != undefined){
			if(minVal > maxVal){
				$.qp.showAlertModal(fcomMsgSource['err.sys.0050'].replace("{0}",dbMsgSource['sc.screendesign.0176']).replace("{1}",dbMsgSource['sc.screendesign.0177']));
				return true;
			}else if(defaultVal > maxVal || defaultVal < minVal){
				$.qp.showAlertModal(dbMsgSource['sc.domaindesign.0053']);
				$("input[name=defaultValue]").focus();
				return true;
			}
		}else{
			if(minVal > maxVal){
				$.qp.showAlertModal(fcomMsgSource['err.sys.0050'].replace("{0}",dbMsgSource['sc.screendesign.0176']).replace("{1}",dbMsgSource['sc.screendesign.0177']));
				return true;
			}
		}*/
	}else if(operatorCode == "1"){
		if(tblAdvance.find("input[name=maxVal]").val() == ""){
			$.qp.showAlertModal(fcomMsgSource['err.sys.0025'].replace("{0}",dbMsgSource['sc.tabledesign.0043']));
			$("input[name=defaultValue]").focus();
			return true;
		}
		/*if(defaultVal != undefined){
			if(defaultVal != maxVal){
				$.qp.showAlertModal(dbMsgSource['sc.domaindesign.0054']);
				$("input[name=defaultValue]").focus();
				return true;
			}
		}*/
	}else if(operatorCode == "2"){
		if(tblAdvance.find("input[name=maxVal]").val() == ""){
			$.qp.showAlertModal(fcomMsgSource['err.sys.0025'].replace("{0}",dbMsgSource['sc.tabledesign.0043']));
			$("input[name=defaultValue]").focus();
			return true;
		}
		/*if(defaultVal != undefined){
			if(defaultVal >= maxVal){
				$.qp.showAlertModal(dbMsgSource['sc.domaindesign.0055']);
				$("input[name=defaultValue]").focus();
				return true;
			}
		}*/
	}else if(operatorCode == "3"){
		if(tblAdvance.find("input[name=maxVal]").val() == ""){
			$.qp.showAlertModal(fcomMsgSource['err.sys.0025'].replace("{0}",dbMsgSource['sc.tabledesign.0043']));
			$("input[name=defaultValue]").focus();
			return true;
		}
		/*if(defaultVal != undefined){
			if(defaultVal > maxVal){
				$.qp.showAlertModal(dbMsgSource['sc.domaindesign.0056']);
				$("input[name=defaultValue]").focus();
				return true;
			}
		}*/
	}else if(operatorCode == "4"){
		if(tblAdvance.find("input[name=minVal]").val() == ""){
			$.qp.showAlertModal(fcomMsgSource['err.sys.0025'].replace("{0}",dbMsgSource['sc.tabledesign.0043']));
			$("input[name=defaultValue]").focus();
			return true;
		}
		/*if(defaultVal != undefined){
			if(defaultVal <= minVal){
				$.qp.showAlertModal(dbMsgSource['sc.domaindesign.0057']);
				$("input[name=defaultValue]").focus();
				return true;
			}
		}*/
	}else if(operatorCode == "5"){
		if(tblAdvance.find("input[name=minVal]").val() == ""){
			$.qp.showAlertModal(fcomMsgSource['err.sys.0025'].replace("{0}",dbMsgSource['sc.tabledesign.0043']));
			$("input[name=defaultValue]").focus();
			return true;
		}
		/*if(defaultVal != undefined){
			if(defaultVal < minVal){
				$.qp.showAlertModal(dbMsgSource['sc.domaindesign.0058']);
				$("input[name=defaultValue]").focus();
				return true;
			}
		}*/
	}else if(operatorCode == "6"){
		if(tblAdvance.find("input[name=minVal]").val() == ""){
			$.qp.showAlertModal(fcomMsgSource['err.sys.0025'].replace("{0}",dbMsgSource['sc.tabledesign.0043']));
			$("input[name=defaultValue]").focus();
			return true;
		}
		/*if(defaultVal != undefined){
			if(defaultVal == minVal){
				$.qp.showAlertModal(dbMsgSource['sc.domaindesign.0059']);
				$("input[name=defaultValue]").focus();
				return true;
			}
		}*/
	}else if(operatorCode == "7"){
		if(tblAdvance.find("input[name=minVal]").val() == ""){
			$.qp.showAlertModal(fcomMsgSource['err.sys.0025'].replace("{0}",dbMsgSource['sc.tabledesign.0043']));
			$("input[name=defaultValue]").focus();
			return true;
		}
		/*if(defaultVal != undefined){
			if(defaultVal != minVal){
				$.qp.showAlertModal(dbMsgSource['sc.domaindesign.0060']);
				$("input[name=defaultValue]").focus();
				return true;
			}
		}*/
	}
	return false;
}

/**
 * Return date folow format: yyyy/MM/DD HH:MM:SS MMM
 * Example: 2016/05/34 12:00:00 000
 * @param date
 * @param type
 * @returns {Date}
 */
function convertDate(date, type){
	if(date != ""){
		if(type == "DATE"){
			// Example: 21/06/2016
			var dateArr = date.split("/");
			return new Date(dateArr[2], dateArr[1], dateArr[0], 0, 0, 0, 000);
		}else if(type == "DATETIME"){
			// Example: 10/06/2016 14:39:21
			var dateTimeArr = date.split(" ");
			var dateArr = dateTimeArr[0].split("/");
			var timeArr = dateTimeArr[1].split(":");
			return new Date(dateArr[2], dateArr[1], dateArr[0], timeArr[0], timeArr[1], timeArr[2], 000);
		}else if(type == "TIME"){
			// Example: 14:56:28
			var timeArr = date.split(":");
			return new Date(2016, 0, 1, timeArr[0], timeArr[1], timeArr[2], 000);
		}
	}
}

function initializeMinlength(maxlength, decimalPart) {
	
	if (maxlength == undefined || maxlength == '') {
		maxlength = 0;
	}
	
	if (maxlength <= 0) return "";

	var str = "-";
	
	if (decimalPart == undefined || decimalPart == '') {
		decimalPart = 0;
	}
	
	maxlength = Number(maxlength);
	decimalPart = Number(decimalPart);
	
	if (decimalPart >= maxlength) {
		decimalPart = 0;
	}

	maxlength = maxlength - decimalPart;

	for (var int = 0; int < maxlength; int++) {
		str = str.concat("9");
		if (int == maxlength - 1) {
			str = str.concat(".");
		}
	}

	for (var i = 0; i < decimalPart; i++) {
		str = str.concat("9");
	}

	return str;
}