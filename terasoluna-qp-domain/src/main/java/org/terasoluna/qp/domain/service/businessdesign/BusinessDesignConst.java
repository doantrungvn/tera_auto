package org.terasoluna.qp.domain.service.businessdesign;

/**
 * @author quangvd
 */
public final class BusinessDesignConst {
	public static final Integer RETURN_TYPE_INITIALSCREEN = 0;
	public static final Integer RETURN_TYPE_SCREEN = 1;
	public static final Integer RETURN_TYPE_SCREEN_EVENT = 2;
	public static final Integer RETURN_TYPE_DOWNLOAD = 3;
	public static final Integer RETURN_TYPE_JSON = 4;
	public static final Integer RETURN_TYPE_XML = 5;

	public static final Integer DELETED_TYPE_NORMAL = 0;
	public static final Integer DELETED_TYPE_FULLPERMISSION = 1;
	
	public static final String DEFAULT_TIME_MIN = "00:00:00";
	public static final String DEFAULT_DATE_MIN = "01/01/1900";
	public static final String DEFAULT_DATETIME_MIN = "01/01/1900 00:00:00";
	public static final String PATTEN_DEFAULT_TIME = "HH:mm:ss";
	public static final String PATTEN_DEFAULT_DATE = "dd/MM/yyyy";
	public static final String PATTEN_DEFAULT_DATETIME = "dd/MM/yyyy HH:mm:ss";
	
	public static final String DEFAULT_TIME_MAX = "23:59:59";
	public static final String DEFAULT_DATE_MAX = "31/12/2999";
	public static final String DEFAULT_DATETIME_MAX = "31/12/2999 23:59:59";

	public static final int COMPONENT_START = 1;
	public static final int COMPONENT_EXECUTION = 2;
	public static final int COMPONENT_VALIDATIONCHECK = 3;
	public static final int COMPONENT_BUSINESSCHECK = 4;
	public static final int COMPONENT_DECISION = 5;
	public static final int COMPONENT_ADVANCE = 6;
	public static final int COMPONENT_COMMON = 7;
	public static final int COMPONENT_ASSIGN = 8;
	public static final int COMPONENT_IF = 9;
	public static final int COMPONENT_LOOP = 10;
	public static final int COMPONENT_FEEDBACK = 11;
	public static final int COMPONENT_NAVIGATOR = 12;
	public static final int COMPONENT_END = 13;
	public static final int COMPONENT_NESTEDLOGIC = 14;
	public static final int COMPONENT_FILEOPERATION = 15;
	public static final int COMPONENT_IMPORTFILE = 16;
	public static final int COMPONENT_EXPORTFILE = 17;
	public static final int COMPONENT_TRANSACTION = 18;
	public static final int COMPONENT_EMAIL = 20;
	public static final int COMPONENT_LOG = 21;
	public static final int COMPONENT_UTILITY = 22;
	public static final int COMPONENT_DOWNLOAD_FILE = 23;
	public static final int COMPONENT_END_IF = 19;
	public static final int COMPONENT_EXCEPTION = 24;

	public static final String JS_SPLIT_ROW = "�";
	public static final String JS_SPLIT_COL = "π";

	public static final String LOOP_INDEX = "index";
	public static final Integer LOOP_STEP_ASC = 0;
	public static final Integer LOOP_STEP_DESC = 1;

	public static final String REMARK = "Auto generate";

	public static final Integer SCREEN_PATTERN_NORMAL = 0;
	public static final Integer SCREEN_PATTERN_SEARCH = 1;
	public static final Integer SCREEN_PATTERN_REGISTER = 2;
	public static final Integer SCREEN_PATTERN_VIEW = 3;
	public static final Integer SCREEN_PATTERN_MODIFY = 4;
	public static final Integer SCREEN_PATTERN_DELETE = 5;
	public static final Integer SCREEN_PATTERN_CONFIRM_REGISTER = 6;
	public static final Integer SCREEN_PATTERN_CONFIRM_MODIFY = 7;
	public static final Integer SCREEN_PATTERN_COMPLETE_REGISTER = 8;
	public static final Integer SCREEN_PATTERN_COMPLETE_MODIFY = 9;
	public static final Integer SCREEN_PATTERN_REDIRECT_CONFIRM_REGISTER = 10;
	public static final Integer SCREEN_PATTERN_REDIRECT_CONFIRM_MODIFY = 11;

	public static final String PATTERN_SEARCH = "Search";
	public static final String PATTERN_VIEW = "View";
	public static final String PATTERN_REGISTER = "Register";
	public static final String PATTERN_MODIFY = "Modify";

	public static final String DISPLAY_REGISTER = "displayRegister";
	public static final String PROCESS_REGISTER = "processRegister";
	public static final String PROCESS_CONFIRM_REGISTER = "processConfirmRegister";
	public static final String DISPLAY_MODIFY = "displayModify";
	public static final String PROCESS_MODIFY = "processModify";
	public static final String PROCESS_CONFIRM_MODIFY = "processConfirmModify";
	public static final String DISPLAY_SEARCH = "displaySearch";
	public static final String PROCESS_SEARCH = "processSearch";
	public static final String DISPLAY_VIEW = "displayView";
	public static final String PROCESS_DELETE = "processDelete";
	public static final String DISPLAY_COMPLETE_REGISTER = "displayCompleteRegister";
	public static final String DISPLAY_COMPLETE_MODIFY = "displayCompleteModify";
	public static final String DISPLAY_CONFIRM_REGISTER = "displayConfirmRegister";
	public static final String DISPLAY_CONFIRM_MODIFY = "displayConfirmModify";
	public static final String UNDO_REGISTER = "undoRegister";
	public static final String UNDO_MODIFY = "undoModify";
	public static final String REDIRECT_CONFIRM_REGISTER = "redirectConfirmRegister";
	public static final String REDIRECT_CONFIRM_MODIFY = "redirectConfirmModify";
	public static final String DOWNLOAD_FILE = "downloadFile";

	/** insert */
	public static final String SC_BUSINESSLOGIC_DESIGN_0215="sc.businesslogicdesign.0215";
	/** update */
	public static final String SC_BUSINESSLOGIC_DESIGN_0216="sc.businesslogicdesign.0216";
	/** delete */
	public static final String SC_BUSINESSLOGIC_DESIGN_0217="sc.businesslogicdesign.0217";

	public static final String SC_GENERATESOURCECODE_0035="sc.generatesourcecode.0035";

	public static final String SC_GENERATESOURCECODE_0040="sc.generatesourcecode.0040";

	public static final Integer BLOGIC_TYPE_STANDARD = 0;
	public static final Integer BLOGIC_TYPE_COMMON = 1;
	public static final Integer BLOGIC_TYPE_WEBSERVICE = 2;
	public static final Integer BLOGIC_TYPE_BATCH = 3;

	public static final Integer MODULE_TYPE_ONLINE = 0;
	public static final Integer MODULE_TYPE_BATCH = 1;

	public static final String MULTI_COMMENT_START = "/**";
	public static final String MULTI_COMMENT_END = "*/";
	public static final String SINGLE_COMMENT_START = "//";
	public static final String SPACE = " ";
	public static final String NL = "\n\t\t";

	public static final Integer REQUEST_METHOD_INITIAL = 0;
	public static final Integer REQUEST_METHOD_PROCESSING = 4;

	public static final Integer DESIGN_MODE_AUTO = 0;
	public static final Integer DESIGN_MODE_MANUAL = 1;
	
	public static final class PREFIX_MAPPING {
		public static final String INPUTBEAN_CODE = "in";
		public static final String OBJECTDEFINITION_CODE = "ob";
		public static final String OUTPUTBEAN_CODE = "ou";

		public static final String SQL_INPUT_CODE = "sqlin";
		public static final String SQL_OUTPUT_CODE = "sqlou";

		public static final Integer INPUTBEAN_ID = 0;
		public static final Integer OBJECTDEFINITION_ID = 1;
		public static final Integer OUTPUTBEAN_ID = 2;
	}

	public static final class MessageParameter {
		public static final Integer TARGET_TYPE_VALIDATION = 0;
		public static final Integer TARGET_TYPE_BUSINESSCHECK = 1;
		public static final Integer TARGET_TYPE_FEEDBACK = 2;

		public static final Integer PARAMETER_TYPE_MESSAGECODE = 0;
		public static final Integer PARAMETER_TYPE_VALUE = 1;
		public static final Integer PARAMETER_TYPE_VARIABLE = 2;
	}
	
	public static final Integer OBJECT_TYPE_COMMON_OBJECT = 0;
	public static final Integer OBJECT_TYPE_EXTERNAL_OBJECT = 1;
	public static final Integer OBJECT_TYPE_COMMON_ATTRIBUTE = 2;
	public static final Integer OBJECT_TYPE_EXTERNAL_ATTRIBUTE = 3;

	// public static final Integer PARAMETER_SCOPE_INPUTBEAN = 0;
	// public static final Integer PARAMETER_SCOPE_OBJECTDEFINITION = 1;
	// public static final Integer PARAMETER_SCOPE_OUTPUTBEAN = 2;

	public static final class InputBeanType {
		public static final Integer DEFAULT = 0;
		public static final Integer CUSTOMIZE = 1;
		public static final Integer DELETED_DEFAULT = 2;
		public static final Integer ADDED_DEFAULT = 3;
	}

	public static final class ConnectorType {
		public static final String OLD_VERSION = "Old version";
		public static final String NORMAL = "";
		public static final String TRUE = "TRUE";
		public static final String FALSE = "FALSE";
		public static final String CYCLE = "CYCLE";
		public static final String BACK = "";
	}

	public static final class NavigatorComponent {
		public static final Integer NAVIGATOR_TO_TYPE_SCREEN = 0;
		public static final Integer NAVIGATOR_TO_TYPE_BLOGIC = 1;
		public static final Integer NAVIGATOR_TO_TYPE_COMMON = 2;

		public static final Integer TRANSITION_TYPE_REDIRECT = 0;
		public static final Integer TRANSITION_TYPE_FORWARD = 1;
	}

	public static final class AssignDetailComponent {
		public static final Integer ASSIGN_TYPE_PARAMETER = 0;
		public static final Integer ASSIGN_TYPE_FORMULA = 1;

		public static final Integer PARAMETER_SCOPE_INPUT_BEAN = 0;
		public static final Integer PARAMETER_SCOPE_OBJECT_DEFINITION = 1;
		public static final Integer PARAMETER_SCOPE_OUTPUT_BEAN = 2;

		public static final Integer TARGET_SCOPE_INPUT_BEAN = 0;
		public static final Integer TARGET_SCOPE_OBJECT_DEFINITION = 1;
		public static final Integer TARGET_SCOPE_OUTPUT_BEAN = 2;
	}

	public static final class LoopComponent {
		public static final Integer LOOP_TYPE_FOREACH = 0;
		public static final Integer LOOP_TYPE_WHILE = 1;

		public static final Integer PARAMETER_SCOPE_INPUT_BEAN = 0;
		public static final Integer PARAMETER_SCOPE_OBJECT_DEFINITION = 1;

		public static final Integer LOOP_SCOPE_CUSTOMIZE = 3;
		public static final Integer LOOP_SCOPE_LENGTH = -1;
	}

	public static final class BusinessCheckComponent {
		public static final Integer BCHECK_TYPE_FORMULA = 1;
		public static final Integer BCHECK_TYPE_EXISTENCE = 2;
		public static final Integer BCHECK_TYPE_DUPLICATED = 3;
		public static final Integer AUTO_INCREMENT = 1;
	}

	public static final class FormulaBuilder {
		public static final int TYPE_IN_BUSINESSLOGIC = 18;
		public static final int TYPE_OB_BUSINESSLOGIC = 19;
		public static final int TYPE_OU_BUSINESSLOGIC = 20;
		public static final int TYPE_IN_DECISIONTABLE = 18;
		public static final int TYPE_OU_DECISIONTABLE = 20;

		public static final Integer PARAMETER_SCOPE_INPUT_BEAN = 0;
		public static final Integer PARAMETER_SCOPE_OBJECT_DEFINITION = 1;
		public static final Integer PARAMETER_SCOPE_OUTPUT_BEAN = 2;

		public static final Integer PARAMETER_TYPE_VALUE = 1;
		public static final Integer PARAMETER_TYPE_PARAMETER = 2;
	}

	public static final class ImpactStatus {
		public static final String NONE = "0";
		public static final String ADDED = "1";
		public static final String MODIFIED = "2";
		public static final String DELETED = "3";
	}

	public static final class ErrorMessage {
		public static final String NONE = "0";
	}

	public static final class DataType {
		public static final Integer OBJECT = 0;
		public static final Integer BYTE = 1;
		public static final Integer SHORT = 2;
		public static final Integer INTEGER = 3;
		public static final Integer LONG = 4;
		public static final Integer FLOAT = 5;
		public static final Integer DOUBLE = 6;
		public static final Integer CHAR = 7;
		public static final Integer BOOLEAN = 8;
		public static final Integer STRING = 9;
		public static final Integer BIGDECIMAL = 10;
		public static final Integer TIMESTAMP = 11;
		public static final Integer DATETIME = 12;
		public static final Integer TIME = 13;
		public static final Integer ENTITY = 14;
		public static final Integer DATE = 15;
		public static final Integer COMMON_OBJECT = 16;
		public static final Integer EXTERNAL_OBJECT = 17;
	}

	public static final class FileOperationComponent {
		public static final Integer TYPE_MERGE = 3;
		public static final Integer PATH_TYPE_FORMULASETTING = 1;
	}

	public static final class ImportFileComponent {
		public static final Integer TYPE_IMPORT = 0;
		public static final Integer PATH_TYPE_FORMULASETTING = 1;
	}

	public static final class ExportFileComponent {
		public static final Integer TYPE_EXPORT = 1;
		public static final Integer PATH_TYPE_FORMULASETTING = 1;
	}

	public static final class OutputbeanScreenitemMapping {
		public static final Integer TYPE_OPTION_SUBMIT = 0;
		public static final Integer TYPE_OPTION_DISPLAY = 1;
		public static final Integer TYPE_DATASOURCE = 2;
		public static final Integer TYPE_OPTION_SELECT = 3;
		public static final Integer TYPE_FROM = 4;
		public static final Integer TYPE_TO = 5;
		public static final Integer TYPE_TOTAL_COUNT = 6;
	}

	public static final class ValidationCheckDetail {
		public static final Integer VALIDATIONTYPE_NOTNULL = 0;
	}

	public static final class ParameterIndex {
		public static final Integer TABLE_ASSIGN_DETAIL_TARGET = 0;
		public static final Integer TABLE_ASSIGN_DETAIL_PARAMETER = 1;
		public static final Integer TABLE_FORMULA_DETAIL_PARAMETER = 2;
		public static final Integer TABLE_COMMON_INPUT_VALUE = 3;
		public static final Integer TABLE_COMMON_OUTPUT_VALUE = 4;
		public static final Integer TABLE_NAVIGATOR_DETAIL_PARAMETER = 5;
		public static final Integer TABLE_ADVANCE_INPUT_VALUE = 6;
		public static final Integer TABLE_ADVANCE_OUTPUT_VALUE = 7;
		public static final Integer TABLE_UTILITY_CONTENT = 8;
		public static final Integer TABLE_UTILITY_INDEX = 9;
		public static final Integer TABLE_DECISION_INPUT_VALUE = 10;
		public static final Integer TABLE_DECISION_OUTPUT_VALUE = 11;
		public static final Integer TABLE_EXECUTION_INPUT_VALUE = 12;
		public static final Integer TABLE_EXECUTION_OUTPUT_VALUE = 13;
		public static final Integer TABLE_BUSINESS_CHECK_CONTENT = 14;
		public static final Integer TABLE_MESSAGE_PARAMETER = 15;
		public static final Integer TABLE_FORMULA_DETAIL = 16;
		public static final Integer TABLE_LOOP_FROM = 17;
		public static final Integer TABLE_LOOP_TO = 18;
		public static final Integer TABLE_EXCEPTION_DETAIL_PARAMETER = 19;

		public static final Integer INDEX_TYPE_INPUT_BEAN = 0;
		public static final Integer INDEX_TYPE_OBJECT_DEFINITION = 1;
		public static final Integer INDEX_TYPE_OUTPUT_BEAN = 2;
		public static final Integer INDEX_TYPE_CUSTOMIZE = 3;
		public static final Integer INDEX_TYPE_LOOP = 4;
		public static final Integer INDEX_TYPE_LENGTH = -1;
	}

	public static final class ValidateType {
		public static final int NOT_NULL = 0;
		public static final int NULL = 1;
		public static final int PATTERN = 2;
		public static final int MIN = 3;
		public static final int MAX = 4;
		public static final int DECIMAL_MIN = 5;
		public static final int DECIMAL_MAX = 6;
		public static final int SIZE = 7;
		public static final int DIGITS = 8;
		public static final int ASSRT_TRUE = 9;
		public static final int ASSRT_FALSE = 10;
		public static final int FUTURE = 11;
		public static final int PAST = 12;
		public static final int CREDITCARD_NUMBER = 13;
		public static final int EMAIL = 14;
		public static final int URL = 15;
		public static final int NOT_BLANK = 16;
		public static final int NOT_EMPTY = 17;
		public static final int ALPHABET = 18;
		public static final int ALPHA_NUMERIC = 19;
		public static final int BINARY = 20;
		public static final int CURRENCY = 21;
		public static final int DECIMAL = 22;
		public static final int DOUBLE = 23;
		public static final int EM_CHARACTER = 24;
		public static final int EN_CHARACTER = 25;
		public static final int FLOAT = 26;
		public static final int HIRAGANA = 27;
		public static final int INTEGER = 28;
		public static final int KANJI = 29;
		public static final int KATAKANA = 30;
		public static final int LONG = 31;
		public static final int PHONE = 32;
		public static final int POSTCODE = 33;
		public static final int SPACE = 34;
		public static final int SYMBOL = 35;
		public static final int TIME = 36;
		public static final int YEAR = 37;
		public static final int ZENKAKU_ALPHABET = 38;
		public static final int ZENKAKU_KATAKANA = 39;
		public static final int ZENKAKU_NUMERIC = 40;
		public static final int ZENKAKU_SYMBOL = 41;
		public static final int DAY = 42;
		public static final int HOUR = 43;
		public static final int MINUTE = 44;
		public static final int MONTH = 45;
		public static final int SECOND = 46;
		public static final int WEEK = 47;
		public static final int DATE_TIME = 48;
		public static final int DATE = 49;
		public static final int QP_SIZE = 50;
		
		public static final int DATE_MIN = 51;
		public static final int DATE_MAX = 52;
		public static final int DATE_RANGE = 53;
		
		public static final int TIME_MIN = 54;
		public static final int TIME_MAX = 55;
		public static final int TIME_RANGE = 56;
		
		public static final int DATE_TIME_MIN = 57;
		public static final int DATE_TIME_MAX = 58;
		public static final int DATE_TIME_RANGE = 59;
		
		public static final int TIMESTAMP_MIN = 60;
		public static final int TIMESTAMP_MAX = 61;
		public static final int TIMESTAMP_RANGE = 62;
		public static final int TIMESTAMP = 63;
		
		public static final int QP_GREATER_THAN = 64;
		public static final int QP_LESS_THAN = 65;
		public static final int QP_EQUAL = 66;
		public static final int QP_NOT_EQUAL = 67;
		
		public static final int DATE_EQUAL = 68;
		public static final int DATE_NOT_EQUAL = 69;
		
		public static final int DATE_TIME_EQUAL = 70;
		public static final int DATE_TIME_NOT_EQUAL = 71;
		
		public static final int TIME_EQUAL = 72;
		public static final int TIME_NOT_EQUAL = 73;
		
		public static final int TIMESTAMP_EQUAL = 74;
		public static final int TIMESTAMP_NOT_EQUAL = 75;
		
		public static final int FUTURE_DATE = 76;
		public static final int FUTURE_DATETIME = 77;
		public static final int FUTURE_TIMESTAMP = 78;
		
		public static final int PAST_DATE = 79;
		public static final int PAST_DATETIME = 80;
		public static final int PAST_TIMESTAMP = 81;
	}

	public static final class ValidationRuleCode {
		public static final String Alphabet = "Alphabet";
		public static final String Alphanumeric = "Alphanumeric";
		public static final String Binary = "Binary";
		public static final String Boolean = "Boolean";
		public static final String CreaditCard = "CreaditCard";
		public static final String Currency = "Currency";
		public static final String Date = "Date";
		public static final String DateTime = "DateTime";
		public static final String Day = "Day";
		public static final String Decimal = "Decimal";
		public static final String Double = "Double";
		public static final String Email = "Email";
		public static final String EmCharacter = "EmCharacter";
		public static final String EnCharacter = "EnCharacter";
		public static final String Float = "Float";
		public static final String Hiragana = "Hiragana";
		public static final String Hour = "Hour";
		public static final String Integer = "Integer";
		public static final String Kanji = "Kanji";
		public static final String Katakana = "Katakana";
		public static final String Long = "Long";
		public static final String Minute = "Minute";
		public static final String Month = "Month";
		public static final String Name = "Name";
		public static final String Numeric = "Numeric";
		public static final String Phone = "Phone";
		public static final String Postcode = "Postcode";
		public static final String Remark = "Remark";
		public static final String Second = "Second";
		public static final String Space = "Space";
		public static final String Symbol = "Symbol";
		public static final String Time = "Time";
		public static final String Timestamp = "Timestamp";
		public static final String Week = "Week";
		public static final String Year = "Year";
		public static final String ZenkakuAlphabet = "ZenkakuAlphabet";
		public static final String ZenkakuKatakana = "ZenkakuKatakana";
		public static final String ZenkakuNumeric = "ZenkakuNumeric";
		public static final String ZenkakuSpaces = "ZenkakuSpaces";
		public static final String ZenkakuSymbol = "ZenkakuSymbol";
	}

	public static final class LogComponent {
		public static final Integer PATH_TYPE_FORMULASETTING = 1;
	}

	public static final class EmailComponent {
		public static final Integer PATH_TYPE_FORMULASETTING = 1;
	}

	public static final class DownloadFileComponent {
		public static final Integer PATH_TYPE_FORMULASETTING = 1;
		public static final Integer PATH_TYPE_CUSTOMIZE = 0;
	}

	/**
	 * Data source type for screen_item table
	 * @author bangnl
	 *
	 */
	public static final class DataSourceType{
		public static final Integer BLOGIC_DEFINE = 1;
		public static final Integer CODELIST = 2;
	}

	public static final class MappingScreenItem{
		public static final Integer INPUTBEAN = 0;
		public static final Integer OUTPUTBEAN = 1;
	}

	public static final class ExceptionComponent {
		public static final Integer EXCEPTION_TO_TYPE_SCREEN = 0;
		public static final Integer EXCEPTION_TO_TYPE_BLOGIC = 1;
		public static final Integer EXCEPTION_TO_TYPE_COMMON = 2;

		public static final Integer TRANSITION_TYPE_REDIRECT = 0;
		public static final Integer TRANSITION_TYPE_FORWARD = 1;
	}
	
	public enum JoinType{
		INNER_JOIN("1"), LEFT_JOIN("2"), RIGHT_JOIN("3"), FULL_OUTER_JOIN("4"), CROSS_JOIN("5");
		JoinType(String code){
			this.code = code;
		}
		private String code;
		public String getCode(){
			return  code;
		}
	}
	
	public enum WhereConditionType{
		VALUE(0),ENTITY(1),PARAMETERS(2);
		WhereConditionType(Integer number){
			this.number = number;
		}
		Integer number;
		public Integer getNumber() {
			return number;
		}
	}
}
