package org.terasoluna.qp.domain.service.generatedocument;


/**
 * @author hunghx
 *
 */
public class GenerateDocumentConst {

	/** Project scope  */
	public static final Integer PROJECT_SCOPRE = 1;
	/** Module scope  */
	public static final Integer MODULE_SCOPRE = 2;
	/** Project scope  */
	public static final String GENERATE_PROJECT = "project";
	/** Child of project scope  */
	public static final String GENERATE_PROJECT_RD = "rd";
	/** Child of project scope  */
	public static final String GENERATE_PROJECT_ED = "ed";
	/** Child of project scope  */
	public static final String GENERATE_PROJECT_ID = "id";
	/** Module scope  */
	public static final String GENERATE_MODULE = "module";
	/** Child of module scope  */
	public static final String GENERATE_MODULE_RD = "rd";
	/** Child of module scope  */
	public static final String GENERATE_MODULE_ED = "ed";
	/** Child of module scope  */
	public static final String GENERATE_MODULE_ID = "id";
	/** Size ID const  */
	public static final int GENERATE_SIZE_ID = 16;
	/** LABEL  */
	public static final String LABEL = "sc";
	
	/** Document type of RD in scope project */
	public static final class RDDocumentTypeByProject {
		public static final int BUSINESS_TYPE = 0;
		public static final int DIGIT_CODE_DEF_DOC = 1;
		public static final int DOMAIN_DESIGN = 2;
		public static final int ENTITY_LIST = 3;
		public static final int ER_DIAGRAM_DATA_MODEL_DEFINE = 4;
		public static final int ER_DIAGRAM_DATA_MODEL_DESIGN = 5;	
		public static final int FUNCTION_LIST = 6;
		public static final int JOB_LIST = 7;
		public static final int PROCESSING_LIST = 8;
		public static final int REPORT_LIST = 9;
		public static final int SCREEN_LIST = 10;
		public static final int TABLE_DESIGN = 11;
		public static final int ONLINE_PROCESSING = 12;
	}

	/** Document type of ED in scope project */
	public static final class EDDocumentTypeByProject {
		public static final int CRUD_MATRIX_FUNCION_LEVEL = 0;
		public static final int FUNCTION_DEFINE_DOC = 1;
		public static final int JOB_DEFINE_DOC = 2;
		public static final int MESSAGE_DESIGN = 3;
		public static final int REPORT_DEFINE_DOC = 4;
		public static final int SCREEN_TRANSITION_DIAGRAM = 5;
		public static final int VIEW_DESIGN = 6;
		public static final int VIEW_LIST = 7;
		public static final int CODE_LIST = 8;
		
	}
	
	/** Document type of ED in scope module */
	public static final class EDDocumentTypeByModule {
		public static final int CRUD_MATRIX_FUNCTION_LEVEL = 0;
		public static final int CRUD_MATRIX_PROCESSING_LEVEL = 1;
		public static final int DESIGN_DOC_OF_ONLINE_PROCESSING = 2;
		public static final int SCREEN_DEFINITION_DOC = 3;
		public static final int SCREEN_DESIGN_DOC = 4;
		public static final int SCREEN_TRANSITION_DIAGRAM = 5;
		public static final int TABLE_DESIGN_DOC = 6;
		
	}

	/** Scope for generate document */
	public static final class SelectType {
		public static final String PROJECT_TYPE = "0";
		public static final String MODULE_TYPE = "1";
		public static final String SCREEN_TYPE = "2";
		public static final String BUSINESS_TYPE = "3";
	}
	
	/** Define all filed of header document */
	public static class HeaderCommon {
		public static final int SYSTEM_NAME = 0;
		public static final int CREATED_BY = 1;
		public static final int CREATED_ON = 2;
		public static final int UPDATED_BY = 3;
		public static final int UPDATED_ON = 4;
	}
	
	/** Define all string common */
	public static class StringConstant {
		public static final String UNDERLINE = "_";
		public static final String DASH = "－";
		public static final String MARU = "o";
		public static final String CODE_LIST = "CL";
		public static final String SQL_BUILDER = "SQL";
		public static final String SQL_AUTOCOMPLETE = "AC";
		public static final String NG = "NG";
	}
	
	public static class StringPrefix {
		public static final String BUSINESS = "B";
		public static final String DOMAIN = "D";
		public static final String FUNCTION = "F";
		public static final String MODULE = "M";
		public static final String VIEW = "V";
		public static final String TABLE = "T";
		public static final String SCREEN = "S";
	}
	
	public static class DataSourceName {
		public static final String USER_DEFINE = "User Define";
		public static final String CODE_LIST = "Code List";
		public static final String SQL_BUILDER = "SQL Builder";
		public static final String SQL_AUTOCOMPLETE = "SQL Autocomplete";
	}
	
	public static class NullValue {
		public static final String NULL = "Null";
		public static final String NOT_NULL = "Not null";
	}
	
	public static class MessageDesign {
		public static final String TITLE = "sc.generatedocument.0042";
		public static final String ITEM_NO = "sc.sys.0083";
		public static final String ID = "sc.generatedocument.0022";
		public static final String SOURCE = "sc.generatedocument.0039";
		public static final String OUTPUT_LOCATION = "sc.generatedocument.0040";
		public static final String ERROR_LEVEL = "sc.generatedocument.0041";
		public static final String MESSAGE_STRING = "sc.generatedocument.0038";
		public static final String DESCRIPTION = "sc.generatedocument.0031";
		public static final String LANGUAGE = "sc.generatedocument.0092";
		public static final String CODE = "sc.databasedesign.0012";
		public static final String NAME = "sc.databasedesign.0011";
		public static final String CLIENT = "sc.databasedesign.0065";
		public static final String SERVER = "sc.databasedesign.0066";
		
		public static final String PROJECT = "sc.generatesourcecode.0003";
		public static final String MODULE = "sc.generatedocument.0006";
		public static final String SCREEN = "sc.screendesign.0157";
		public static final String SCREEN_AREA = "sc.messagedesign.0010";
		public static final String SCREEN_ITEM = "sc.messagedesign.0015";
		public static final String BLOGIC = "sc.businesslogicdesign.0135";
		public static final String MENU_DESIGN = "sc.menudesign.0008";
		public static final String DESIGN_INFORMATION = "sc.designinformation.0004";
		
		public static final String LABEL = "cl.sys.0020";
		public static final String INFORMATION = "cl.sys.0017";
		public static final String WARNING = "cl.sys.0018";
		public static final String ERROR = "cl.sys.0019";
	}
	
	public static class Header {
		public static final String SYSTEM_NAME = "sc.sys.0077";
		public static final String CREATE_BY = "sc.sys.0078";
		public static final String CREATE_DATE = "sc.sys.0079";
		public static final String UPDATE_BY = "sc.sys.0080";
		public static final String UPDATE_DATE = "sc.sys.0081";
	}
	
	public static class ViewList {
		public static final String TITLE = "sc.generatedocument.0043";
	}
	
	public static class ViewDesign {
		public static final String TITLE = "sc.generatedocument.0082";
	}
	
	public static class ScreenTransitionDiagram {
		public static final String TITLE = "sc.generatedocument.0072";
	}
	
	public static class BusinessList {
		public static final String TITLE = "sc.generatedocument.0012";
	}
	
	public static class FunctionList {
		public static final String TITLE = "sc.generatedocument.0013";
	}
	
	public static class DomainDesign {
		public static final String TITLE = "sc.generatedocument.0021";
		public static final String DTS_USER_DEFINE = "sc.tabledesign.0038";
		public static final String DTS_CODELIST = "sc.tabledesign.0039";
		public static final String DTS_SQL_BUILDER = "sc.tabledesign.0040";
		public static final String ID = "sc.generatedocument.0022";
		public static final String MAJOR_CLASS = "sc.generatedocument.0023";
		public static final String SUB_CLASS = "sc.generatedocument.0024";
		public static final String MINOR_CLASS = "sc.generatedocument.0025";
		public static final String DOMAIN_NAME = "sc.generatedocument.0026";
		public static final String PHYSICAL_DOMAIN_NAME = "sc.generatedocument.0027";
		public static final String LOGICAL_DATA_TYPE = "sc.generatedocument.0028";
		public static final String PHYSICAL_DATA_TYPE = "sc.generatedocument.0029";
		public static final String DESCRIPTION = "sc.generatedocument.0031";
		public static final String DESCRIPTION_FORMAT = "sc.generatedocument.0032";
		public static final String MINIMUM_VALUE = "sc.generatedocument.0033";
		public static final String MAXIMUM_VALUE = "sc.generatedocument.0034";
		public static final String DEFAULT_VALUE = "sc.generatedocument.0035";
		public static final String VALIDATION_RULE = "sc.generatedocument.0036";
		public static final String DATA_SOURCE = "sc.generatedocument.0037";
		public static final String DATA_SOURCE_NAME = "sc.generatedocument.0116";
		public static final String DATA_SOURCE_CODE = "sc.generatedocument.0117";
	}
	
	public static class ProcessingList {
		public static final String TITLE = "sc.generatedocument.0066";
	}
	
	public static class TableDesign {
		public static final String TITLE = "sc.generatedocument.0047";
		public static final String ENTITY_ID = "sc.generatedocument.0095";
		public static final String SUBJECT_AREA_ID = "sc.generatedocument.0096";
		public static final String SUBJECT_AREA_NAME = "sc.generatedocument.0048";
		public static final String ENTITY_TYPE = "sc.generatedocument.0049";
		public static final String ENTITY_NAME = "sc.generatedocument.0050";
		public static final String TABLE_NAME = "sc.generatedocument.0051";
		public static final String DESCRIPTION = "sc.generatedocument.0031";
		
		public static final String ITEM_NO = "sc.generatedocument.0079";
		public static final String ATTRIBUTE_NAME = "sc.generatedocument.0052";
		public static final String COLUMN_NAME = "sc.generatedocument.0053";
		public static final String PRIMARY_KEY = "sc.generatedocument.0054";
		public static final String ALTERNATE_KEY = "sc.generatedocument.0058";
		public static final String FOREIGN_KEY = "sc.generatedocument.0055";
		public static final String PARENT_ENTITY_NAME = "sc.generatedocument.0097";
		public static final String PARENT_TABLE_NAME = "sc.generatedocument.0098";
		public static final String PARENT_ATTRIBUTE_NAME = "sc.generatedocument.0099";
		public static final String PARENT_COLUMN_NAME = "sc.generatedocument.0100";
		public static final String LOGICAL_DATA_TYPE = "sc.generatedocument.0028";
		public static final String DATA_TYPE = "sc.generatedocument.0059";
		public static final String DIGIT_CHARACTER = "sc.generatedocument.0060";
		public static final String PHYSICAL_DATA_TYPE = "sc.generatedocument.0029";
		public static final String DATA_LENGTH = "sc.generatedocument.0061";
		public static final String DOMAIN_NAME = "sc.generatedocument.0026";
		public static final String DEFAULT_VALUE = "sc.generatedocument.0035";
		public static final String UNIQUE_CONSTRAINT = "sc.generatedocument.0101";
		public static final String NULL_VALUE_CONSTRAINT = "sc.generatedocument.0102";
		public static final String CHECK_CONSTRAINT = "sc.generatedocument.0103";
		public static final String OPERATOR_CODE = "sc.autocomplete.0057";
		
		/**
		 * Operators 
		 */
		public static final String EQUAL = "= {0}";
		public static final String LESS_THAN = "< {0}";
		public static final String LESS_EQUAL_THAN = "<= {0}";
		public static final String GREATER_THAN = "> {0}";
		public static final String GREATER_EQUAL_THAN = ">= {0}";
		public static final String DIFFERENT = "<> {0}";
		public static final String LIKE = "LIKE {0}";
		public static final String BETWEEN = "BETWEEN {0} AND {1}";
		
	}
	
	public static class CodeList {
		public static final String TITLE = "sc.generatedocument.0094";
		public static final String CODELIST_NAME = "sc.generatedocument.0104";
		public static final String CODELIST_CODE = "sc.generatedocument.0105";
		public static final String VALUE_ONLY = "sc.generatedocument.0106";
		public static final String MODULE_NAME = "sc.generatedocument.0107";
		public static final String DESCRIPTION = "sc.generatedocument.0108";
		public static final String KEY = "sc.generatedocument.0109";
		public static final String VALUE = "sc.generatedocument.0110";
		public static final String VALUE1 = "sc.generatedocument.0111";
		public static final String VALUE2 = "sc.generatedocument.0112";
		public static final String VALUE3 = "sc.generatedocument.0113";
		public static final String VALUE4 = "sc.generatedocument.0114";
		public static final String VALUE5 = "sc.generatedocument.0115";
		
	}
			
}
