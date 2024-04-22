var CONFIG = {
	AVAILABLE_DBS: ["mysql", "sqlite", "web2py", "mssql", "postgresql", "oracle", "sqlalchemy", "vfp9", "cubrid"],
	DEFAULT_DB: "postgresql",

	AVAILABLE_LOCALES: ["ar", "cs", "de", "el", "en", "eo", "es", "fr", "hu", "it", "ja", "pl", "pt_BR", "ro", "ru", "sv", "zh"],
	DEFAULT_LOCALE: "en",
	
	AVAILABLE_BACKENDS: ["warehouse", "tracking", "transporting", "starterkit", CONTEXT_PATH],
	DEFAULT_BACKEND: [CONTEXT_PATH],

	RELATION_THICKNESS: 2,
	RELATION_SPACING: 15,
	RELATION_COLORS: ["#000", "#800", "#080", "#008", "#088", "#808", "#088"],

	STATIC_PATH: CONTEXT_PATH + "/resources/app/graphicdatabasedesign/javascript/",
	XHR_PATH: ""
}

var TABLE_TYPE = {
	TRANSACTION:0,
	MASTER:1, 
	COMMON:2
}

var DESIGN_STATUS = {
	UNDER_DESIGN:1, 
	FIXED:2
}

/** 2 */
var REQUIRED_MIN_INPUT_VAL = 1;
/** 200 */
var NAME_MAX_VAL = "64";
/**Regular exp for code*/
/*var REGULAR_EXP_CODE = /^[a-zA-Z][_0-9a-zA-Z]{0,}$/i;*/
/**Regular exp for name*/
var REGULAR_EXP_NAME = /^[^/\\/:*?\"<>|.]{0,}$/i;
