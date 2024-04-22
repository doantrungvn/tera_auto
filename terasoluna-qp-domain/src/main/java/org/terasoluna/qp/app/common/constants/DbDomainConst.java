package org.terasoluna.qp.app.common.constants;

public final class DbDomainConst {

	public static final String REDIRECT_DELETION_SUCCESS = "redirect:/complete";
	public static final String REDIRECT_MODIFY_SUCCESS = "redirect:/modifyComplete";

	public static final String CHARACTER_ENCODING="UTF-8";
	
	public static final String CHARACTER_DOT=".";
	
	public static final int MAX_LENGTH_OF_CODE_SQL = 30;
	public static final int MAX_LENGTH_OF_CODE = 64;
	public static final int MAX_LENGTH_OF_NAME = 64;
	public static final int MIN_VAL_INPUT = 1;
	public static final int MAX_VAL_REMARK = 2000;
	public static final int MIN_VAL_TEXT_INPUT_AREA = 200;
	public static final int MIN_VAL_REMARK = 0;
	public static final int MAX_VAL_MESSAGE = 255;
	public static final int MAX_VAL_OTHER = 150;
	public static final int MAX_VAL_OF_TEXT = 2000;
	
	public static final class AreaPatternType {
		public static final Integer REGISTER = 1;
		public static final Integer MODIFY = 2;
		public static final Integer VIEW = 3;
	}

	public static final class AreaType {
		public static final Integer HEADER_LINK = -1;
		public static final Integer SINGLE_ENTITY = 0;
		public static final Integer LIST_ENTITIES = 1;
		public static final Integer SUBMIT_ACTION = 2;
		public static final Integer FREE_ELEMENT = 3;
	}
	
	public static final class DesignMode {
		public static final Integer PROTOTYPE = 1;
		public static final Integer BUSINESS = 2;
	}

	public static final class GenerateMode {
		public static final Integer BUSINESS_LOGIC = 1;
		public static final Integer DATABASE_AND_BLOGIC = 2;
	}
	
	/**
	 * Fix some logical data type
	 * 
	 * @author dungnn1
	 *
	 */
	public static final class LogicDataType {
		public static final Integer AUTOCOMPLETE = 0;
		public static final Integer NAME = 1;
		public static final Integer INTEGER = 2;
		public static final Integer DECIMAL = 3;
		public static final Integer DATE = 4;
		public static final Integer RADIO = 5;
		public static final Integer CHECKBOX = 6;
		public static final Integer SELECT = 7;
		public static final Integer CURRENCY = 8;
		public static final Integer TIME = 9;
		public static final Integer REMARK = 10;
		public static final Integer LINK = 11;
		public static final Integer FILEUPLOAD = 12;
		public static final Integer BUTTON = 13;
		public static final Integer DATETIME = 14;
		public static final Integer EMAIL = 15;
		public static final Integer PHONE = 16;
		public static final Integer BOOLEAN = 17;
		public static final Integer CODE = 18;
		public static final Integer LABEL = 20;
		public static final Integer LABEL_DYNAMIC = 21;
		public static final Integer LINK_DYNAMIC = 22;
		public static final Integer CUSTOM_ITEM = 23;
		public static final Integer CUSTOM_SECTION = 24;
		
	}

	public static final class PhysicalDataType {
		public static final Integer TEXT = 1;
		public static final Integer INTEGER = 2;
		public static final Integer DECIMAL = 3;
		public static final Integer DATE = 4;
		public static final Integer CHAR = 5;
		public static final Integer CURRENCY = 6;
		public static final Integer BOOLEAN = 7;
		public static final Integer TIME = 8;
		public static final Integer DATETIME = 9;
		public static final Integer BINARY = 10;
	}
	
	public static final class DefaultGenerateSetting {
		public static final Integer NORMAL = 0;
		public static final Integer BLANK = 1;
	}

	/**
	 * base type of numeric
	 * 
	 * @author trungdv
	 *
	 */
	public static final class PhysicalDataTypeDetail {
		public static final Integer CHARACTER_VARYING = 1;
		public static final Integer TEXT = 3;
		public static final Integer CHAR = 2;
		public static final Integer INTEGER = 5;
		public static final Integer SMALLINT = 6;
		public static final Integer BIGINT = 7;
		public static final Integer SERIAL = 13;
		public static final Integer BIGSERIAL = 14;
		public static final Integer NUMBERIC = 4;
		public static final Integer FLOAT = 16;
		public static final Integer DOUBLE = 17;
		public static final Integer CURRENTCY = 15;
	}
	
	public static final class CodelistType {
		public static final Integer SYSTEM_CODE_LIST = 1;
		public static final Integer TABLE_CODE_LIST = 2;
		public static final Integer SCREEN_CODE_LIST = 3;
	}

	public static final class TemplateType {
		public static final Integer NORMAL = 1;
		public static final Integer POPUP = 2;
	}
	
	public static final class ScreenPatternType {
		public static final Integer SEARCH = 1;
		public static final Integer REGISTER = 2;
		public static final Integer VIEW = 3;
		public static final Integer MODIFY = 4;
	}
	
	public static final class MessageLevel {
		public static final Integer PROJECT = 0;
		public static final Integer MODULE = 1;
		public static final Integer SCREEN = 2;
//		public static final Integer SCREEN_AREA = 3;
//		public static final Integer SCREEN_ITEM = 4;
		public static final Integer BUSINESS_LOGIC= 5;
		public static final Integer MENU_DESIGN= 6;
		public static final Integer DESIGN_INFORMATION= 7;
	}
	public static final class MessageType {
		public static final String LABEL_MESSAGE = "sc";
		public static final String INFORMATION_MESSAGE = "inf";
		public static final String WARINING_MESSAGE = "wrn";
		public static final String ERROR_MESSAGE = "err";
	}
	public static final class MessageGeneratedStatus {
		public static final Integer Copy = 0;
		public static final Integer AUTO_TRANSLATE = 1;
		public static final Integer MANUAL_TRANSLATE = 2;
	}
	
	public static final class ResourceURL {
		public static final Integer URL_BLOGIC = 1;
		public static final Integer URL_SCREEN_DESIGN = 2;
		public static final Integer URL_SQL_DESIGN = 3;
		public static final Integer URL_DECISION_TABLE = 4;
		public static final Integer URL_DOMAIN_DESIGN = 5;
		public static final Integer URL_TABLE_DESIGN = 6;
		public static final Integer URL_TABLE_DESIGN_DETAILS = 9;
		public static final Integer URL_MENU_DESIGN = 7;
		public static final Integer URL_AUTOCOMPLETE_DESIGN = 8;
		public static final Integer URL_SESSION_MANAGEMENT = 10;
		public static final Integer URL_FUNCTION_MASTER = 11;
		public static final Integer URL_EXTERNAL_OBJECT = 12;
		public static final Integer URL_COMMON_OBJECT = 13;
	}
	
	public static final class ResourceType {
		public static final int OTHER = 0;
		public static final int BLOGIC = 1;
		public static final int SCREEN_DESIGN = 2;
		public static final int SQL_DESIGN = 3;
		public static final int DOMAIN_DESIGN = 4;
		public static final int TABLE_DESIGN = 5;
		public static final int DECISION_TABLE = 6;
		public static final int MENU_DESIGN = 7;
		public static final int AUTOCOMPLETE_DESIGN = 8;
		public static final int SCREEN_ITEM = 10;
		public static final int FUNCTION_MASTER = 11;
		public static final int SESSION_MANAGEMENT = 12;
		public static final int EXTERNAL_OBJECT = 13;
		public static final int COMMON_OBJECT = 14;
		public static final int COMMON_BLOGIC = 15;
	}

	public static final class ParameterType {
		public static final int UPDATE = 0;
		public static final int INSERT = 1;
		public static final int DELETE = 2;
	}
	
	public static final class ProblemType {
		public static final int UNMATCHED = 1;
		public static final int CHANGE_DATABASE = 1;
		public static final int MISS_SETTING = 2;
	}
	
	public static final class AutoFix {
		public static final Integer DISABLE = 0;
		public static final Integer ENABLE = 1;
	}
	
	/*public static final class FromResourceType_ {
		public static final int TABLE_DESIGN = 1;
		public static final int TABLE_DESIGN_DETAIL = 2;
		public static final int DOMAIN_DESIGN = 3;
		public static final int SQL_DESIGN = 4;
		public static final int BLOGIC = 5;
		public static final int DECISION_TABLE = 6;
		public static final int SCREEN_DESIGN = 7;
		public static final int GRAPHIC_DATABASE_DESIGN = 10;
	}*/
	
	public static final class FromResourceType {
		public static final int TABLE_DESIGN = 1;
		public static final int TABLE_DESIGN_DETAIL = 2;
		public static final int DOMAIN_DESIGN = 3;
		public static final int SQL_DESIGN = 4;
		public static final int BLOGIC = 5;
		public static final int DECISION_TABLE = 6;
		public static final int SCREEN_DESIGN = 7;
		public static final int DECISION_TABLE_INPUT = 8;
		public static final int DECISION_TABLE_OUTPUT = 9;
		public static final int SQL_DESIGN_OUTPUT = 10;
		public static final int SQL_DESIGN_INPUT = 11;
		public static final int BLOGIC_COMMON_INPUTBEAN = 12;
		public static final int BLOGIC_COMMON_OUTPUTBEAN = 13;
		public static final int BLOGIC_NAVIGATOR_INPUTBEAN = 14;
		public static final int TABLE_DESIGN_DETAIL_CHANGE_DATATYPE = 15;
		public static final int TABLE_DESIGN_DETAIL_CHANGE_CODE = 16;
		public static final int TABLE_DESIGN_DETAIL_CHANGE_DELETE_COLUMN = 17;
		public static final int TABLE_DESIGN_DETAIL_CHANGE_ADD_COLUMN = 18;
		public static final int TABLE_DESIGN_DELETE = 19;
		public static final int BLOGIC_RELATED_SCREEN_INPUTBEAN = 20;
		public static final int DECISION_TABLE_CHANGED_DATA_TYPE_INPUT_OUTPUT = 21;
		public static final int DECISION_TABLE_DELETE_INPUT_OUTPUT = 22;
		public static final int DECISION_TABLE_ADDNEW_INPUT_OUTPUT = 23;
		public static final int FUNCTION_MASTER_CHANGE_DATE_TYPE_INPUT = 24;
		public static final int FUNCTION_MASTER_DELETED_INPUT = 25;
		public static final int FUNCTION_MASTER_INSERT_INPUT = 26;
		public static final int FUNCTION_MASTER_CHANGE_DATE_TYPE_OUTPUT = 27;
		public static final int FUNCTION_MASTER_DELETED_OUTPUT = 28;
		public static final int FUNCTION_MASTER_DELETED_FUNCTION_METHOD = 29;
		public static final int FUNCTION_MASTER_CHANGE_ARRAY_FLAG_INPUT = 30;
		public static final int FUNCTION_MASTER_CHANGE_ARRAY_FLAG_OUTPUT = 31;
		public static final int GRAPHIC_DATABASE_DESIGN = 32;
		public static final int MESSAGE_DESIGN_DELETE = 33;
		public static final int PROJECT_CHANGE_DB_TYPE = 34;
		public static final int TABLE_DESIGN_DETAIL_CHANGE_DATATYPE_FOREIGN_KEY = 35;
		public static final int MODULE_DELETE = 36;
		public static final int PROJECT_CHANGE_DB_TYPE_SQL = 37;
		public static final int SESSION_MANAGEMENT= 38;
		public static final int EXTERNAL_OBJECT= 39;
		public static final int COMMON_OBJECT= 40;
		public static final int FUNCTION_METHOD_MODIFY = 41;
		public static final int COMMON_BLOGIC = 42;
		public static final int NAVIGATOR_BLOGIC = 43;
		public static final int EXTERNAL_OBJECT_ADD_ATTRIBUTE= 44;
		public static final int EXTERNAL_OBJECT_MODIFY_ATTRIBUTE= 45;
		public static final int EXTERNAL_OBJECT_DELETE_ATTRIBUTE= 46;
		public static final int AUTOCOMPLETE_DESIGN = 47;
		public static final int FUNCTION_METHOD = 48;
	}
	
	/**
	 * Define status of design
	 * @author dungnn1
	 *
	 */
	public static final class DesignStatus{
		public static final Integer UNDER_DESIGN = 1;
		public static final Integer FIXED = 2;
		/*public static final Integer DESIGN_CHANGE = 3;*/
	}
	
	/**
	 * define syn status between domain data type with physical data
	 * @author dungnn1
	 *
	 */
	public static final class DomainMappingItemStatus{
		public static final Integer NO_CHANGE_STATUS = 0;
		public static final Integer NEW_STATUS = 1;
		public static final Integer DELETED_STATUS = 2;
		public static final Integer CHANGE_STATUS = 3;
	}
	
	/**
	 * Define SQL type
	 * 
	 * @author tiennd
	 *
	 */
	public static final class SQLDesignType {
		public static final int VIEW = 0;
		public static final int ADVANCED_VIEW = 1;
		public static final int AUTOCOMPLETE = 2;
		public static final int ADVANCED_AUTOCOMPLETE = 3;
		public static final int SQL_BUILDER = 4;
		public static final int ADVANCED_SQL = 5;
	}
	
	/**
	 * Define status of domain table mapping items 
	 * @author quangvd
	 *
	 */
	public static final class DomainTableMappingItems{
		public static final Integer CODELIST_TYPE_SYSTEM = 1;
		public static final Integer CODELIST_TYPE_CUSTOM = 3;
	}
	
	public static final class SqlOperator{
		public static final String EQUAL = "0";
		public static final String LESS = "1";
		public static final String LESS_EQUAL = "2";
		public static final String GREATER = "3";
		public static final String GREATER_EQUAL = "4";
		public static final String NOT_EQUAL = "5";
		public static final String LIKE = "6";
		public static final String BETWEEN = "7";
		public static final String IS_NULL = "8";
		public static final String IS_NOT_NULL = "9";
	}
	
	public static final class SqlPattern {
		public static final int SELECT = 0;
		public static final int INSERT = 1;
		public static final int UPDATE = 2;
		public static final int DELETE = 3;
	}

	public static final class FunctionMasterType {
		public static final Integer QP_COMMON = 0;
		public static final Integer CUSTOMIZE = 1;
	}

	public static final class FunctionMasterArrayFlag {
		public static final Integer NON_ARRAY = 0;
		public static final Integer IS_ARRAY = 1;
	}
	
	/**
	 * Define data source type of table_design_details table 
	 * @author dungnn1
	 *
	 */
	public static final class DatasourceType {
		public static final Integer USER_DEFINE = 0;
		public static final Integer CODELIST = 1;
		public static final Integer SQL_BUILDER_IS_AUTOCOMPLETE = 2;
		public static final Integer SQL_BUILDER = 3;
	}

	/**
	 * Define display type of table_design_details table 
	 * @author trungdv
	 *
	 */
	public static final class DisplayType {
		public static final Integer UNUSED = 0;
		public static final Integer USED = 1;
	}
	
	public static final class BlogicReturnType {
		public static final Integer INITIAL = 0;
		public static final Integer SCREEN = 1;
		public static final Integer SCREENEVENT = 2;
		public static final Integer DOWNLOAD = 3;
		public static final Integer COMMON = 4;
	}
	
	public static final class InputBeanType {
		public static final Integer DEFAULT = 0;
		public static final Integer CUSTOMIZE = 1;
		public static final Integer DELETED_DEFAULT = 2;
		public static final Integer ADDED_DEFAULT = 3;
	}
	
	public static final class JavaDataTypeOfBlogic {
		public static final Integer OBJECT_DATATYPE = 0;
		public static final Integer BYTE_DATATYPE = 1;
		public static final Integer SHORT_DATATYPE = 2;
		public static final Integer INT_DATATYPE = 3;
		public static final Integer LONG_DATATYPE = 4;
		public static final Integer FLOAT_DATATYPE = 5;
		public static final Integer DOUBLE_DATATYPE = 6;
		public static final Integer CHAR_DATATYPE = 7;
		public static final Integer BOOLEAN_DATATYPE = 8;
		public static final Integer STRING_DATATYPE = 9;
		public static final Integer BIGDECIMAL_DATATYPE = 10;
		public static final Integer TIMESTAMP_DATATYPE = 11;
		public static final Integer DATETIME_DATATYPE = 12;
		public static final Integer TIME_DATATYPE = 13;
		public static final Integer ENTITY_DATATYPE = 14;
		public static final Integer DATE_DATATYPE = 15;
	}
	
	public static final class PhysicalDataTypePrimitive {
		public static final int TEXT = 1;
		public static final int INTEGER = 2;
		public static final int DECIMAL = 3;
		public static final int DATE = 4;
		public static final int CHAR = 5;
		public static final int CURRENCY = 6;
		public static final int BOOLEAN = 7;
		public static final int TIME = 8;
		public static final int DATETIME = 9;
		public static final int BINARY = 10;
	}

	public static final class BaseType {
		public static final int CHARACTER_VARYING_BASETYPE = 1;
		public static final int TEXT_BASETYPE = 3;
		public static final int INTEGER_BASETYPE = 5;
		public static final int BYTE_BASETYPE = 19;
		public static final int SMALLINT_BASETYPE = 6;
		public static final int BIGINT_BASETYPE = 7;
		public static final int SERIAL_BASETYPE = 13;
		public static final int BIGSERIAL_BASETYPE = 14;
		public static final int NUMERIC_BASETYPE = 4;
		public static final int DOUBLE_BASETYPE = 17;
		public static final int FLOAT_BASETYPE = 16;
		public static final int DATE_BASETYPE = 10;
		public static final int CHAR_BASETYPE = 2;
		public static final int CURRENCY_BASETYPE = 15;
		public static final int BOOLEAN_BASETYPE = 8;
		public static final int TIME_BASETYPE = 11;
		public static final int DATETIME_BASETYPE = 12;
		public static final int TIMESTAMP_BASETYPE = 18;
		public static final int BINARY_BASETYPE = 9;
		
	}
	
	public static final class LogicDataTypePrimitive {
		public static final int AUTOCOMPLETE = 0;
		public static final int NAME = 1;
		public static final int INTEGER = 2;
		public static final int DECIMAL = 3;
		public static final int DATE = 4;
		public static final int RADIO = 5;
		public static final int CHECKBOX = 6;
		public static final int SELECT = 7;
		public static final int CURRENCY = 8;
		public static final int TIME = 9;
		public static final int REMARK = 10;
		public static final int LINK = 11;
		public static final int FILEUPLOAD = 12;
		public static final int BUTTON = 13;
		public static final int DATETIME = 14;
		public static final int EMAIL = 15;
		public static final int PHONE = 16;
		public static final int BOOLEAN = 17;
		public static final int CODE = 18;
		public static final int LABEL = 20;
		public static final int TEXT = 21;
	}
	
	public static final class TblDesignKeyType {
		public static final Integer NONE = 0;
		public static final Integer PK = 1;
		public static final Integer FK = 2;
		public static final Integer UNIQUE = 4;
		public static final Integer INDEX = 8;
		public static final Integer FULLTEXT = 16;
	}
	
	/**
	 * 
	 * @author dungnn1
	 *
	 */
	public static final class DataTypeFlag {
		public static final Integer PRIMITIVE = 0;
		public static final Integer DOMAIN_DATA = 1;
	}
	
	public static final class ImpactStatus {
		public static final Integer NO_CONFLICT = 1;
	}

	/**
	 * 
	 * @author dungnn1
	 *
	 */
	public static final class YesNoFlg {
		public static final Integer NO = 0;
		public static final Integer YES = 1;
	}
	
	/**
	 * 
	 * @author dungnn1
	 *
	 */
	public static final class ConstrainsType {
		public static final Integer NONE = 0;
		public static final Integer RANGE = 1;
		public static final Integer DATASOURCE = 2;
	}
	
	public static final class ComponentType {
		public static final int START = 1;
		public static final int EXECUTION = 2;
		public static final int VALIDATION_CHECK = 3;
		public static final int BUSINESS_CHECK = 4;
		public static final int DECISION = 5;
		public static final int ADVANCE = 6;
		public static final int COMMON = 7;
		public static final int ASSIGN = 8;
		public static final int IF = 9;
		public static final int FOREACH = 10;
		public static final int FEEDBACK = 11;
		public static final int NAVIGATOR = 12;
		public static final int END = 13;
	}
	/**
	 * 
	 * @author dungnn1
	 *
	 */
	public static final class DatabaseType {
		public static final Integer PostgreSQL = 1;
		public static final Integer ORACLE = 2;
	}
	
	public static final class ROLE {
		public static final Integer STATUS_INACTIVE = 0;
		public static final Integer STATUS_ACTIVE = 1;
	}
	
	public static final class ProxySetting {
		public static final Integer NONE_PROXY = 0;
		public static final Integer SYSTEM_PROXY = 1;
		public static final Integer MANUAL_PROXY = 2;
	}
	
	public static final String SEPARATE_LANGUAGE_COUNTRY = "_";

	

	public static final class FunctionType {
		public static final Integer ONLINE = 0;
		public static final Integer BATCH = 1;
	}

	public static final class MenuItemType {
		public static final Integer MENU_ITEM = 0;
		public static final Integer SEPARATOR = 1;
	}

	public static final class MenuDirection {
		public static final int VERTICAL = 0;
		public static final int HORIZONTAL = 1;
	}
	
	/**
	 * define build menu for preview (menu design) or generate prototype
	 * @author dungnn1
	 *
	 */
	public static final class MenuType {
		public static final int PREVIEW = 0;
		public static final int PROTOTYPE = 1;
		public static final int JSP = 2;
	}
	
	public static final class NavigatorFrom {
		public static final int VIEW = 0;
		public static final int MODIFY = 1;
	}
	
	public static final class TableDesignType {
		public static final Integer TRANSACTION = 0;
		public static final Integer MASTER = 1;
		public static final Integer QP_TABLE = 2;
	}
	
	/**
	 * 
	 * @author KhangTM
	 *
	 */
	public static final class GenerateHistoryStatus {
		public static final int GENERATING = 0;
		public static final int GENERATED = 1;
		public static final int GENERATE_ERROR = 2;
		public static final int GENERATE_TIMEOUT = 3;
	}
	public static final class GenerateAppStatus {
		public static final String INIT = "0";
		public static final String GENERATING = "1";
		public static final String GENERATED = "2";
	}
	public static final class GenerateBLogicAppStatus {
		public static final String SUCCESS = "0";
		public static final String ERROR = "-1";
	}
	public static final class GenerateHistoryMode {
		public static final String DOCUMENT = "0";
		public static final String SOURCE_CODE = "1";
		public static final String WAR_FILE = "2";
	}
	
	/**
	 * 
	 * @author VinhHV
	 *
	 */
	public static final class ConnectionSetting {
		public static final Integer SYSTEM_SETTING = 0;
		public static final Integer MANUAL_SETTING = 1;
	}
	
	public static final class ObjectDefinitionType {
		public static final Integer COMMOM = 16;
		public static final Integer EXTERNAL = 17;
	}
	
	public static final class ScreenItemType {
		public static final int NORMAL_ITEM = 1;
		public static final int HIDDEN_ITEM = 2;
		public static final int LINK_ITEM = 3;
		public static final int BUTTON_ITEM = 4;
	}
	
	/**
	 * 
	 * @author quynd1
	 *
	 */
	public static final class BatchDirectoryType{
		public static final Integer RELATIVE = 0;
		public static final Integer ABSOLUTE = 1;
	}
	
	/**
	 * 
	 * @author trungdv
	 *
	 */
	public static final class DesignType {
		public static final Integer DESIGN_TYPE = 0;
		public static final Integer REFERENCE_TYPE = 1;
	}
	
	/**
	 * 
	 * @author trungdv
	 *
	 */
	public static final class ObjectType {
		public static final Integer COMMON_OBJECT = 0;
		public static final Integer EXTERNAL_OBJECT = 1;
		public static final Integer COMMON_ATTRIBUTE = 2;
		public static final Integer EXTERNAL_ATTRIBUTE = 3;
		public static final Integer ENTITY_OBJECT = 4;
		public static final Integer ENTITY_ATTRIBUTE = 5;
		public static final Integer OBJECT_OBJECT = 0;
		public static final Integer OBJECT_ATTRIBUTE = 0;
	}
	
	/**
	 * 
	 * @author quynd1
	 *
	 */
	public static final class LogManagementType{
		public static final int LOG_TYPE_CONSOLE = 1;
		public static final int LOG_TYPE_FILE = 2;
		public static final int LOG_TYPE_DB = 3;
	}
	
	/**
	 * 
	 * @author quynd1
	 *
	 */
	public static final class LogFileType{
		public static final int LOG_FILE_NORMAL = 1;
		public static final int LOG_FILE_ROLLING = 2;
	} 
	/**
	 * 
	 * @author dungnn1
	 * 0 - is qp common
	 * 1 - user define
	 */
	public static final class QPCommomFlg {
		public static final String YES = "0";
		public static final String NO = "1";
	}

	public static final class initTranslateFlg{
		public static final int FOR_TEST_CONNECTION = 1;
		public static final int FOR_TRANSLATE = 2;
		
	}
	
	public static class FileNameType {
		public static final Integer USER_INPUT = 0;
		public static final Integer FORMULA_SETTING = 1;
	}
	
	public static final class DefaultType {
		public static final Integer TEXT = 0;
		public static final Integer FUNCTION = 1;
	}
	
	public static final class RollingPolicy {
		public static final String TIME_BASED_ROLLINGPOLICY = "1";
		public static final String SIZE_AND_TIME_BASED_ROLLINGPOLICY = "2";
		public static final String FIXED_WINDOW_ROLLINGPOLICY = "3";
	}
	
	public static final class TriggeringPolicy {
		public static final String SIZE_BASED_TRIGGERINGPOLICY = "1";
	}
	
	public static final class MappingType {
		public static final Integer SUBMIT  = 0;
		public static final Integer DISPLAY  = 1;
		public static final Integer DATASOURCE  = 2;
		public static final Integer SELECT  = 3;
	}
	
	public static final class ImpactChangeDesign {
		//only 10 character
		public static final String JOB_APP_CD = "DetectImpt";
		
		public static final String BLOGIC_APP_STATUS_SUCCESS = "0";
		public static final String BLOGIC_APP_STATUS_ERROR = "-1";
		
		public static final String CASE_MODIFY = "0";
		public static final String CASE_DELETE = "1";
	}
}
