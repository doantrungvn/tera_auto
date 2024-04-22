/**
 * 
 */
/* Define modal*/
var BD_MODAL_NAME = {
		DECISION : "#dialog-decision-setting",
		ASSIGN : "#dialog-assign-setting",
		FEEDBACK : "#dialog-feedback-setting",
		VALIDATIONCHECK : "#dialog-validationcheck-setting",
		VALIDATIONCHECK_DETAIL : "#dialog-validationcheck-detail-setting",
		BUSINESSCHECK : "#dialog-businesscheck-setting",
		MESSAGEPARAMETER : "#dialog-messageparameter-setting",
		FORMULA : "#dialog-formula-setting",
		LOOP : "#dialog-loop-setting",
		IF : "#dialog-if-setting",
		EXECUTION : "#dialog-execution-setting",
		NAVIGATOR : "#dialog-navigator-setting",
		COMMON : "#dialog-common-setting",
		NESTEDLOGIC : "#dialog-nestedlogic-setting",
		ADVANCE : "#dialog-advance-setting",
		FILE_OPERATION : "#dialog-fileoperation-setting",
		READ_FILE : "#dialog-readfile-setting",
		READ_FILE_FORMAT : "#dialog-readfileformat-setting",
		EXPORT_FILE : "#dialog-exportfile-setting",
		EXPORT_FILE_FORMAT : "#dialog-exportfileformat-setting",
		EXPORT_FILE_COLUMN_FORMAT : "#dialog-exportfilecolumnformat-setting",
		TRANSACTION : "#dialog-transaction-setting",
		EMAIL : "#dialog-email-setting",
		LOG : "#dialog-log-setting",
		UTILITY : "#dialog-utility-setting",
		DOWNLOAD_FILE : "#dialog-downloadfile-setting",
		EXCEPTION : "#dialog-exception-setting"
};
var BD_PREFIX_SCOPE = {
		INPUTBEAN : "0",
		OBJECTDEFINITION : "1",
		OUTPUTBEAN : "2"
};
var BD_STANDARD_CHECK = [
        {type : 17,name :"NotEmpty",messageCode :"err.sys.0025",messageCodeArray :"err.sys.0077",groupType :2, typeValid : 0},
        {type : 16,name :"NotBlank",messageCode :"err.sys.0061",messageCodeArray :"err.sys.0079",groupType :2, typeValid : 0},
        {type : 0,name :"NotNull",messageCode :"err.sys.0062",messageCodeArray :"err.sys.0091",groupType :2, typeValid: 0},
        {type : 1,name :"Null",	messageCode :"err.sys.0063",messageCodeArray :"err.sys.0092",groupType :3, typeValid: 0},
        {type : 2,name :"Pattern",messageCode :"err.sys.0129",messageCodeArray :"err.sys.0094",groupType :3, typeValid: 0},
        {type : 3,name :"Min",messageCode :"err.sys.0042",messageCodeArray :"err.sys.0085",groupType :1, typeValid: 0},
        {type : 4,name :"Max",messageCode :"err.sys.0050",messageCodeArray :"err.sys.0084",groupType :1, typeValid: 0},
        {type : 64,name :"GreaterThan",messageCode :"err.sys.0238",messageCodeArray :"err.sys.0240",groupType :2, typeValid : 1},
        {type : 65,name :"LessThan",messageCode :"err.sys.0239",messageCodeArray :"err.sys.0241",groupType :2, typeValid : 1},
        {type : 66,name :"Equal",messageCode :"err.sys.0242",messageCodeArray :"err.sys.0243",groupType :2, typeValid : 1},
        {type : 67,name :"NotEqual",messageCode :"err.sys.0244",messageCodeArray :"err.sys.0245",groupType :2, typeValid : 1},
        {type : 22,name :"Decimal",messageCode :"err.sys.0165",messageCodeArray :"err.sys.0166",groupType :2, typeValid : 1},
        {type : 5,name :"DecimalMin",messageCode :"err.sys.0112",messageCodeArray :"err.sys.0114",groupType :1, typeValid: 0},
        {type : 6,name :"DecimalMax",messageCode :"err.sys.0113",messageCodeArray :"err.sys.0115",groupType :1,typeValid: 0},
        {type : 7,name :"Size",messageCode :"err.sys.0064",messageCodeArray :"err.sys.0095",groupType :0, typeValid: 0},
        {type : 50,name :"QpSize",messageCode :"err.sys.0064",messageCodeArray :"err.sys.0095",groupType :0, typeValid: 0},
        {type : 8,name :"Digits",messageCode :"err.sys.0069",messageCodeArray :"err.sys.0086",groupType :1, typeValid : 0},
        {type : 9,name :"AssertTrue",messageCode :"err.sys.0071",messageCodeArray :"err.sys.0083",groupType :3, typeValid : 0},
        {type : 10,name :"AssertFalse",messageCode :"err.sys.0070",messageCodeArray :"err.sys.0082",groupType :3, typeValid: 0},
        {type : 11,name :"Future",messageCode :"err.sys.0068",messageCodeArray :"err.sys.0087",groupType :3, typeValid : 0},
        {type : 12,name :"Past",messageCode :"err.sys.0065",messageCodeArray :"err.sys.0093",groupType :3, typeValid : 0},
        
        {type : 13,name :"CreditCardNumber",messageCode :"err.sys.0004",messageCodeArray :"err.sys.0081",groupType :3, typeValid : 0},
        {type : 14,name :"Email",messageCode :"err.sys.0012",messageCodeArray :"err.sys.0076",groupType :3, typeValid : 0},
        {type : 15,name :"URL",messageCode :"err.sys.0060",messageCodeArray :"err.sys.0080",groupType :3, typeValid : 0},
        {type : 18,name :"Alphabet",messageCode :"err.sys.0157",messageCodeArray :"err.sys.0158",groupType :2, typeValid : 1},
        {type : 19,name :"Alphanumeric",messageCode :"err.sys.0159",messageCodeArray :"err.sys.0160",groupType :2, typeValid : 1},
        {type : 20,name :"Binary",messageCode :"err.sys.0161",messageCodeArray :"err.sys.0162",groupType :2, typeValid : 1},
        {type : 21,name :"Currency",messageCode :"err.sys.0163",messageCodeArray :"err.sys.0164",groupType :2, typeValid : 1},
        
        {type : 42,name :"Day",messageCode :"err.sys.0197",messageCodeArray :"err.sys.0198",groupType :2, typeValid : 1},
        {type : 23,name :"Double",messageCode :"err.sys.0167",messageCodeArray :"err.sys.0168",groupType :2, typeValid : 1},
        {type : 24,name :"EmCharacter",messageCode :"err.sys.0135",messageCodeArray :"err.sys.0136",groupType :2, typeValid : 1},
        {type : 25,name :"EnCharacter",messageCode :"err.sys.0137",messageCodeArray :"err.sys.0138",groupType :2, typeValid : 1},
        {type : 26,name :"Float",messageCode :"err.sys.0169",messageCodeArray :"err.sys.0170",groupType :2, typeValid : 1},
        {type : 27,name :"Hiragana",messageCode :"err.sys.0139",messageCodeArray :"err.sys.0140",groupType :2, typeValid : 1},
        {type : 43,name :"Hour",messageCode :"err.sys.0195",messageCodeArray :"err.sys.0196",groupType :2, typeValid : 1},
        {type : 28,name :"Integer",messageCode :"err.sys.0171",messageCodeArray :"err.sys.0172",groupType :2, typeValid : 1},
        {type : 29,name :"Kanji",messageCode :"err.sys.0143",messageCodeArray :"err.sys.0144",groupType :2, typeValid : 1},
        {type : 30,name :"Katakana",messageCode :"err.sys.0141",messageCodeArray :"err.sys.0142",groupType :2, typeValid : 1},
        {type : 31,name :"Long",messageCode :"err.sys.0173",messageCodeArray :"err.sys.0174",groupType :2, typeValid : 1},
        {type : 44,name :"Minute",messageCode :"err.sys.0187",messageCodeArray :"err.sys.0188",groupType :2, typeValid : 1},
        {type : 45,name :"Month",messageCode :"err.sys.0189",messageCodeArray :"err.sys.0190",groupType :2, typeValid : 1},
        {type : 32,name :"Phone",messageCode :"err.sys.0145",messageCodeArray :"err.sys.0146",groupType :2, typeValid : 1},
        {type : 33,name :"Postcode",messageCode :"err.sys.0147",messageCodeArray :"err.sys.0148",groupType :2, typeValid : 1},
        {type : 46,name :"Second",messageCode :"err.sys.0191",messageCodeArray :"err.sys.0192",groupType :2, typeValid : 1},
        {type : 34,name :"Space",messageCode :"err.sys.0175",messageCodeArray :"err.sys.0176",groupType :2, typeValid : 1},
        {type : 35,name :"Symbol",messageCode :"err.sys.0210",messageCodeArray :"err.sys.0211",groupType :2, typeValid : 1},
        {type : 47,name :"Week",messageCode :"err.sys.0193",messageCodeArray :"err.sys.0194",groupType :2, typeValid : 1},
        {type : 37,name :"Year",messageCode :"err.sys.0179",messageCodeArray :"err.sys.0180",groupType :2, typeValid : 1},
        {type : 38,name :"ZenkakuAlphabet",messageCode :"err.sys.0151",messageCodeArray :"err.sys.0152",groupType :2, typeValid : 1},
        {type : 39,name :"ZenkakuKatakana",messageCode :"err.sys.0153",messageCodeArray :"err.sys.0154",groupType :2, typeValid : 1},
        {type : 40,name :"ZenkakuNumeric",messageCode :"err.sys.0155",messageCodeArray :"err.sys.0156",groupType :2, typeValid : 1},
        {type : 41,name :"ZenkakuSymbol",messageCode :"err.sys.0181",messageCodeArray :"err.sys.0182",groupType :2, typeValid : 1},
        
        {type : 49,name :"Date",messageCode :"err.sys.0005",messageCodeArray :"err.sys.0006",groupType :2, typeValid : 1},
        {type : 76,name :"FutureDate",messageCode :"err.sys.0262",messageCodeArray :"err.sys.0263",groupType :2, typeValid : 1},
        {type : 79,name :"PastDate",messageCode :"err.sys.0264",messageCodeArray :"err.sys.0265",groupType :2, typeValid : 1},
        {type : 68,name :"DateEqual",messageCode :"err.sys.0246",messageCodeArray :"err.sys.0250",groupType :2, typeValid : 1},
        {type : 69,name :"DateNotEqual",messageCode :"err.sys.0254",messageCodeArray :"err.sys.0258",groupType :2, typeValid : 1},
        {type : 51,name :"DateMin",messageCode :"err.sys.0216",messageCodeArray :"err.sys.0228",groupType :2, typeValid : 1},
        {type : 52,name :"DateMax",messageCode :"err.sys.0212",messageCodeArray :"err.sys.0224",groupType :2, typeValid : 1},
        {type : 53,name :"DateRange",messageCode :"err.sys.0220",messageCodeArray :"err.sys.0232",groupType :2, typeValid : 1},
        {type : 36,name :"Time",messageCode :"err.sys.0177",messageCodeArray :"err.sys.0178",groupType :2, typeValid : 1},
        {type : 72,name :"TimeEqual",messageCode :"err.sys.0248",messageCodeArray :"err.sys.0252",groupType :2, typeValid : 1},
        {type : 73,name :"TimeNotEqual",messageCode :"err.sys.0244",messageCodeArray :"err.sys.0245",groupType :2, typeValid : 1},
        {type : 54,name :"TimeMin",messageCode :"err.sys.0217",messageCodeArray :"err.sys.0229",groupType :2, typeValid : 1},
        {type : 55,name :"TimeMax",messageCode :"err.sys.0213",messageCodeArray :"err.sys.0225",groupType :2, typeValid : 1},
        {type : 56,name :"TimeRange",messageCode :"err.sys.0221",messageCodeArray :"err.sys.0233",groupType :2, typeValid : 1},
        {type : 48,name :"DateTime",messageCode :"err.sys.0183",messageCodeArray :"err.sys.0184",groupType :2, typeValid : 1},
        {type : 77,name :"FutureDatetime",messageCode :"err.sys.0266",messageCodeArray :"err.sys.0267",groupType :2, typeValid : 1},
        {type : 80,name :"PastDatetime",messageCode :"err.sys.0268",messageCodeArray :"err.sys.0269",groupType :2, typeValid : 1},
        {type : 70,name :"DateTimeEqual",messageCode :"err.sys.0247",messageCodeArray :"err.sys.0251",groupType :2, typeValid : 1},
        {type : 71,name :"DateTimeNotEqual",messageCode :"err.sys.0255",messageCodeArray :"err.sys.0259",groupType :2, typeValid : 1},
        {type : 57,name :"DateTimeMin",messageCode :"err.sys.0218",messageCodeArray :"err.sys.0230",groupType :2, typeValid : 1},
        {type : 58,name :"DateTimeMax",messageCode :"err.sys.0214",messageCodeArray :"err.sys.0226",groupType :2, typeValid : 1},
        {type : 59,name :"DateTimeRange",messageCode :"err.sys.0222",messageCodeArray :"err.sys.0234",groupType :2, typeValid : 1},
        {type : 63,name :"Timestamp",messageCode :"err.sys.0236",messageCodeArray :"err.sys.0237",groupType :2, typeValid : 1},
        {type : 78,name :"FutureTimestamp",messageCode :"err.sys.0270",messageCodeArray :"err.sys.0271",groupType :2, typeValid : 1},
        {type : 81,name :"PastTimestamp",messageCode :"err.sys.0272",messageCodeArray :"err.sys.0273",groupType :2, typeValid : 1},
        {type : 74,name :"TimeStampEqual",messageCode :"err.sys.0249",messageCodeArray :"err.sys.0253",groupType :2, typeValid : 1},
        {type : 75,name :"TimeStampNotEqual",messageCode :"err.sys.0257",messageCodeArray :"err.sys.0261",groupType :2, typeValid : 1},
        {type : 60,name :"TimestampMin",messageCode :"err.sys.0219",messageCodeArray :"err.sys.0231",groupType :2, typeValid : 1},
        {type : 61,name :"TimestampMax",messageCode :"err.sys.0215",messageCodeArray :"err.sys.0227",groupType :2, typeValid : 1},
        {type : 62,name :"TimestampRange",messageCode :"err.sys.0223",messageCodeArray :"err.sys.0235",groupType :2, typeValid : 1},
];
var BD_STANDARD_DATATYPE= {
	OBJECT : "0",
	BYTE : "1",
	SHORT : "2",
	INTEGER : "3",
	LONG : "4",
	FLOAT : "5",
	DOUBLE : "6",
	CHAR : "7",
	BOOLEAN : "8",
	STRING : "9",
	BIGDECIMAL : "10",
	TIMESTAMP : "11",
	DATETIME : "12",
	TIME : "13",
	ENTITY : "14",
	DATE : "15",
};
var BD_TABS_NAME = {
	INPUTBEAN : "#tabInputBean",
	OUTPUTBEAN : "#tabOutputBean",
	OBJECTDEFINITION : "#tabObjectDefinition",
	BLOGICSETTING : "#tabBlogicSetting"
}
/* For validation */
var ITEM_TYPE = {
	INPUT : "input", 
	SELECT : "select",
};
var BD_TABLE_NAME = {
	INPUTBEAN : "#tbl_inputbean_list_define",
	OUTPUTBEAN : "#tbl_outputbean_list_define",
	OBJECTDEFINITION : "#tbl_objectdefinition_list_define"
}

var BD_TABLE_ITEM_NAME = {
	OBJECT_NAME : $.qp.getModuleMessage('sc.businesslogicdesign.0037'),
	OBJECT_CODE : $.qp.getModuleMessage('sc.businesslogicdesign.0038'),
	DATATYPE : $.qp.getModuleMessage('sc.businesslogicdesign.0039')
}

var CODE_PAGEABLE = '<if test="pageable.sort != null">'+"\n"+
'ORDER BY'+"\n"+
	'<foreach collection="pageable.sort" item="sort" separator=",">'+"\n"+
		'${sort.property} ${sort.direction}'+ "\n"+
	'</foreach>'+ "\n"+
'</if>'+ "\n"+
'<![CDATA['+ "\n"+		    	
   'LIMIT'+ "\n"+
	   '#{pageable.pageSize}'+ "\n"+
   'OFFSET'+ "\n"+
	   '#{pageable.offset}' + "\n"+
']]>';
var CODE_PAGEABLE_ORACLE =    "WHERE" + "\n" +
			  "<![CDATA["  + "\n" +
			   "rn BETWEEN  (#{pageable.offset} +1) AND (#{pageable.offset} + #{pageable.pageSize})" + "\n" +     
			  "]]>";
var DATABASE_TYPE = {
		postgre_sql : 1,
		oracle : 2	
	};
