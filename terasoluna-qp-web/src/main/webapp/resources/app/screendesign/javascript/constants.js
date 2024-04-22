/* Define bean type */
var BEAN_TYPE = {
	INPUT : 0, 
	OUTPUT : 1,
	CONDITION : 2,
	ACTION : 3
};

/* Define tab name */
var TAB_NAME = {
	LOGICDESIGN : "#tabsDecision-1",
	INPUT : "#tabsDecision-2",
	OUTPUT : "#tabsDecision-3",
	ITEMDESIGN : "#tabsDecision-4"
};

/* Define table name */
var TABLE_NAME= {
		INPUT : "tbl_input_list_result",
		OUTPUT : "tbl_output_list_result",
		CONDITION : "tbl_item_condition",
		ACTION : "tbl_item_action",
		LOGIC : "tbl_logic_design"
	};

/* Processing for tab [Input - Output - ItemDesign] */
var REFERENCE_TYPE = {
	PARENT : 0, 
	CHILDREN : 1
};

/* For validation */
var ITEM_TYPE = {
		INPUT : 'input', 
		SELECT : 'select',
		CHECKBOX : 'checkbox',
	};

/* Datatype */
var DATATYPE = {
		OBJECT : 0,
		BYTE : 1, 
		SHORT : 2,
		INT : 3,
		LONG : 4,
		FLOAT : 5,
		DOUBLE : 6,
		CHAR : 7,
		BOOLEAN : 8,
		STRING : 9,
		BIGDECIMAL : 10,
		TIMESTAMP : 11,
		DATETIME : 12,
		TIME : 13,
		DATE : 15
	};

/* For mapping column old */
var COLUMN_MARK = {};
