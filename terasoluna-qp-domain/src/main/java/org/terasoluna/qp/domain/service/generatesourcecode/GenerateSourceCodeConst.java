package org.terasoluna.qp.domain.service.generatesourcecode;

import java.util.HashMap;
import java.util.Map;


/**
 * @author hunghx
 *
 */
public class GenerateSourceCodeConst {

	/** Scope project  */
	public static final Integer PROJECT_SCOPRE = 1;
	/** Scope module  */
	public static final Integer MODULE_SCOPRE = 2;
	/** Java file extend  */
	public static final String JAVA_EXTEND = ".java";
	/** Jsp file extend  */
	public static final String JSP_EXTEND = ".jsp";
	/** Zip file extend  */
	public static final String ZIP_EXTEND = ".zip";
	/** xml file extend  */
	public static final String XML_EXTEND = ".xml";
	/** Template folder name  */
	public static final String TEMPPLATE_FOLDER_NAME = "template";
	
	public static final String LOG_FOLDER = "log";
	
	public static final String MAVEN_SH_SCRIPT = "install_cus_lib.sh";
	public static final String MAVEN_BAT_SCRIPT = "install_cus_lib.bat";
	public static final String LIB_FOLDER = "lib";
	public static final String PREPARE_SH_FILE = "prepare_file.sh";
	public static final String GENERATE_BAT = "generate_async.bat";
	public static final String GENERATE_SH = "generate_async.sh";
	public static final String BUILD_WAR_FILE_BAT = "build_war.bat";
	public static final String BUILD_WAR_FILE_SH = "build_war.sh";
	
	public static final String MAVEN_COMMAND = "mvn install:install-file -DgroupId=%s -DartifactId=%s -Dversion=%s -Dpackaging=%s -Dfile=%s";
	
	public static final String SYSTEM_DATETIME_TYPE = "5";
	
	/** Endcoding */
	public static final String ENCODE_UTF8 = "UTF-8";
	/** Generate source type manual */
	public static final Integer SOURCE_CODE = 1;
	/** Generate source type war file */
	public static final Integer WAR_FILE = 2;
	/** Generate source of business */
	public static final int BUSINESS_LOGIC = 0;
	/** Generate source of sql scrip*/
	public static final int SQL = 1;
	/** Generate source of screen*/
	public static final int SCREEN = 2;
	
	public static final int MODULE_SELECTED_GENERATE = 1;
	
	public static class MappingType {
		
		public static final Integer SUBMIT = 0;
		
		public static final Integer DISPLAY = 1;
		
		public static final Integer DATA_SOURCE = 2;

		public static final Integer SELECT = 3;
	}
	
	public static class BusinessLogicGenerate {
		
		public static final int CONTROLLER = 0;
		
		public static final int SERVICE = 1;
		
		public static final int MODEL = 2;
		
		public static final int MODEL_WEB = 3;
		
		public static final int BATCH = 4;
		
		public static final int BATCH_XML = 5;
		
		public static final int DOZER_XML = 6;
		
		public static final int PROPERTIES = 7;

		public static final String SUFFIX_CONTROLLER = "Controller";
		
		public static final String SUFFIX_WS_CONTROLLER = "RestController";
		
		public static final String SUFFIX_FORM = "Form";

		public static final String SUFFIX_SERVICE = "Service";
		
		public static final String SUFFIX_SERVICE_IMPL = "ServiceImpl";
		
		public static final String SUFFIX_INPUT_BEAN = "InputBean";
		
		public static final String SUFFIX_OUTPUT_BEAN = "OutputBean";
		
		public static final String SUFFIX_DATA_SOURCE = "DataSource";
        
        public static final String SUFFIX_INPUT = "Input";
        
        public static final String SUFFIX_OUTPUT = "Output";
		
		public static final String SUFFIX_OBJ_DEFINITION = "ObjectDefinition";
		
		public static final String SUFFIX_INPUT_FORM = "InputForm";
		
		public static final String SUFFIX_OUTPUT_FORM = "OutputForm";
		
		public static final String SUFFIX_INPUT_RESOURCE = "InputResource";
		
		public static final String SUFFIX_OUTPUT_RESOURCE = "OutputResource";
		
		public static final String SUFFIX_BLOGIC = "BLogic";
		
		public static final String SUFFIX_DOZER_MAPPER = "-mapping";
		
		public static final String WEB_SERVICE_FOLDER = "webservice";
	}
	
	public static class TemplatePath {
		public static final String SOURCE_TEMPLATE = "/META-INF/template/source/";
	}

	public static class GenerateScope {
		public static final Integer BLOGIC = 0;
		public static final Integer SQLDESIGN = 1;
		public static final Integer DECISION = 2;
	}
	
    public static final class ObjectType {
        public static final int OBJECT = 0;
        public static final int COMMON_OBJECT = 16;
        public static final int EXTERNAL_OBJECT = 17;
    }
    
    public static final class OperatorType {
        public static final int OPERATOR_NOT_EQUAL = 5;
        public static final int LIKE = 6;
        public static final int OPERATOR_EQUAL = 0;
    }
    
    public static final class DataType {
        public static final int OBJECT = 0;
        public static final int BYTE = 1;
        public static final int SHORT = 2;
        public static final int INTEGER = 3;
        public static final int LONG = 4;
        public static final int FLOAT = 5;
        public static final int DOUBLE = 6;
        public static final int CHARACTER = 7;
        public static final int BOOLEAN = 8;
        public static final int STRING = 9;
        public static final int BIGDECIMAL = 10;
        public static final int TIMESTAMP = 11;
        public static final int DATETIME = 12;
        public static final int TIME = 13;
        public static final int ENTITY = 14;
        public static final int DATE = 15;
        public static final int COMMON_OBJECT = 16;
        public static final int EXTERNAL_OBJECT = 17;
    }

	/**Display register*/
	public static final String SC_GENERATESOURCECODE_0015="sc.generatesourcecode.0015";
	/**Process register*/
	public static final String SC_GENERATESOURCECODE_0016="sc.generatesourcecode.0016";
	/**Process confirm register*/
	public static final String SC_GENERATESOURCECODE_0017="sc.generatesourcecode.0017";
	/**Display modify*/
	public static final String SC_GENERATESOURCECODE_0018="sc.generatesourcecode.0018";
	/**Process modify*/
	public static final String SC_GENERATESOURCECODE_0019="sc.generatesourcecode.0019";
	/**Process confirm modify*/
	public static final String SC_GENERATESOURCECODE_0020="sc.generatesourcecode.0020";
	/**Display search*/
	public static final String SC_GENERATESOURCECODE_0021="sc.generatesourcecode.0021";
	/**Process search*/
	public static final String SC_GENERATESOURCECODE_0022="sc.generatesourcecode.0022";
	/**Display view*/
	public static final String SC_GENERATESOURCECODE_0023="sc.generatesourcecode.0023";
	/**Process delete*/
	public static final String SC_GENERATESOURCECODE_0024="sc.generatesourcecode.0024";
	/**Display complete register*/
	public static final String SC_GENERATESOURCECODE_0025="sc.generatesourcecode.0025";
	/**Display complete modify*/
	public static final String SC_GENERATESOURCECODE_0026="sc.generatesourcecode.0026";
	/**Display confirm register*/
	public static final String SC_GENERATESOURCECODE_0027="sc.generatesourcecode.0027";
	/**Display confirm modify*/
	public static final String SC_GENERATESOURCECODE_0028="sc.generatesourcecode.0028";
	/**Undo register*/
	public static final String SC_GENERATESOURCECODE_0029="sc.generatesourcecode.0029";
	/**Undo modify*/
	public static final String SC_GENERATESOURCECODE_0030="sc.generatesourcecode.0030";
	/**Redirect confirm register*/
	public static final String SC_GENERATESOURCECODE_0031="sc.generatesourcecode.0031";
	/**Redirect confirm modify*/
	public static final String SC_GENERATESOURCECODE_0032="sc.generatesourcecode.0032";
	/**Initial search count*/
	public static final String SC_GENERATESOURCECODE_0033="sc.generatesourcecode.0033";
	/**Initial search records*/
	public static final String SC_GENERATESOURCECODE_0034="sc.generatesourcecode.0034";
	/**Delete all*/
	public static final String SC_GENERATESOURCECODE_0035="sc.generatesourcecode.0035";
	/**Process search count*/
	public static final String SC_GENERATESOURCECODE_0036="sc.generatesourcecode.0036";
	/**Process search records*/
	public static final String SC_GENERATESOURCECODE_0037="sc.generatesourcecode.0037";
	/**Get {0} to view*/
	public static final String SC_GENERATESOURCECODE_0038="sc.generatesourcecode.0038";
	/**Get {0} to modify*/
	public static final String SC_GENERATESOURCECODE_0039="sc.generatesourcecode.0039";
	/**Insert {0} after delete*/
	public static final String SC_GENERATESOURCECODE_0040="sc.generatesourcecode.0040";
	/** Count records to display*/
	public static final String SC_GENERATESOURCECODE_0041="sc.generatesourcecode.0041";
	/** Search records to display*/
	public static final String SC_GENERATESOURCECODE_0042="sc.generatesourcecode.0042";
	/** Check duplicated value of {0} */
	public static final String SC_GENERATESOURCECODE_0043="sc.generatesourcecode.0043";
	/** Mapping {0} to Entity */
	public static final String SC_GENERATESOURCECODE_0044="sc.generatesourcecode.0044";
	/** Set {0} to output */
	public static final String SC_GENERATESOURCECODE_0045="sc.generatesourcecode.0045";
	/** Set records to output */
	public static final String SC_GENERATESOURCECODE_0046="sc.generatesourcecode.0046";
	/** Get datasource value of {0} */
	public static final String SC_GENERATESOURCECODE_0047="sc.generatesourcecode.0047";
	/** Set datasource of {0} to output */
	public static final String SC_GENERATESOURCECODE_0048="sc.generatesourcecode.0048";
	/** Count records */
	public static final String SC_GENERATESOURCECODE_0049 = "sc.generatesourcecode.0049";
	/** Search records */
	public static final String SC_GENERATESOURCECODE_0050 = "sc.generatesourcecode.0050";
	/** Set to output */
	public static final String SC_GENERATESOURCECODE_0051 = "sc.generatesourcecode.0051";
	/** Check existence {0} */
	public static final String SC_GENERATESOURCECODE_0052 = "sc.generatesourcecode.0052";
	/** Get {0} */
	public static final String SC_GENERATESOURCECODE_0053 = "sc.generatesourcecode.0053";
	/** Mapping {0} to output */
	public static final String SC_GENERATESOURCECODE_0054 = "sc.generatesourcecode.0054";
	/** Modify successful */
	public static final String SC_GENERATESOURCECODE_0055 = "sc.generatesourcecode.0055";
	/** Display complete modify screen */
	public static final String SC_GENERATESOURCECODE_0056 = "sc.generatesourcecode.0056";
	/** Display search screen */
	public static final String SC_GENERATESOURCECODE_0057 = "sc.generatesourcecode.0057";
	/** Display modify screen */
	public static final String SC_GENERATESOURCECODE_0058 = "sc.generatesourcecode.0058";
	/** click on button Save */
	public static final String SC_GENERATESOURCECODE_0059 = "sc.generatesourcecode.0059";
	/** Display confirm register screen */
	public static final String SC_GENERATESOURCECODE_0060 = "sc.generatesourcecode.0060";
	/** Display confirm modify screen */
	public static final String SC_GENERATESOURCECODE_0061 = "sc.generatesourcecode.0061";
	/** Delete {0} */
	public static final String SC_GENERATESOURCECODE_0062 = "sc.generatesourcecode.0062";
	/** Delete successful */
	public static final String SC_GENERATESOURCECODE_0063 = "sc.generatesourcecode.0063";
	/** Display delete successful screen */
	public static final String SC_GENERATESOURCECODE_0064 = "sc.generatesourcecode.0064";
	/** Register successful */
	public static final String SC_GENERATESOURCECODE_0065 = "sc.generatesourcecode.0065";
	/** Display complete register screen */
	public static final String SC_GENERATESOURCECODE_0066 = "sc.generatesourcecode.0066";
	/** Display register screen */
	public static final String SC_GENERATESOURCECODE_0067 = "sc.generatesourcecode.0067";
	/** Delete all {0} */
	public static final String SC_GENERATESOURCECODE_0068 = "sc.generatesourcecode.0068";
	/** Loop {0} */
	public static final String SC_GENERATESOURCECODE_0069 = "sc.generatesourcecode.0069";
	/** Check {0} */
	public static final String SC_GENERATESOURCECODE_0070 = "sc.generatesourcecode.0070";
	/** click on button modify */
	public static final String SC_GENERATESOURCECODE_0071 = "sc.generatesourcecode.0071";
	/** click on button Delete */
	public static final String SC_GENERATESOURCECODE_0072 = "sc.generatesourcecode.0072";
	/** click on button Confirm */
	public static final String SC_GENERATESOURCECODE_0073 = "sc.generatesourcecode.0073";
	/** click on button Back */
	public static final String SC_GENERATESOURCECODE_0074 = "sc.generatesourcecode.0074";
	/** click on link Register */
	public static final String SC_GENERATESOURCECODE_0075 = "sc.generatesourcecode.0075";
	/** click on link Search */
	public static final String SC_GENERATESOURCECODE_0076 = "sc.generatesourcecode.0076";
	/** Total count */
	public static final String SC_GENERATESOURCECODE_0077 = "sc.generatesourcecode.0077";
	/** register */
	public static final String SC_GENERATESOURCECODE_0078 = "sc.generatesourcecode.0078";
	/** modify */
	public static final String SC_GENERATESOURCECODE_0079 = "sc.generatesourcecode.0079";
	/** Modify successful */
	public static final String SC_GENERATESOURCECODE_0080 = "sc.generatesourcecode.0080";
	/** Insert {0} after delete */
	public static final String SC_GENERATESOURCECODE_0081 = "sc.generatesourcecode.0081";
	/** go to View Screen */
	public static final String SC_GENERATESOURCECODE_0082 = "sc.generatesourcecode.0082";
	/** go to Modify Screen */
	public static final String SC_GENERATESOURCECODE_0083 = "sc.generatesourcecode.0083";
	/** Fail register navigator components */
	public static final String SC_GENERATESOURCECODE_0084 = "sc.generatesourcecode.0084";
	/**Download file {0}*/
	public static final String SC_GENERATESOURCECODE_0085="sc.generatesourcecode.0085";
	/**Get {0} to download*/
	public static final String SC_GENERATESOURCECODE_0086="sc.generatesourcecode.0086";
	/**In case type of first table is list, currently only support create search screen*/
	public static final String SC_GENERATESOURCECODE_0087="sc.generatesourcecode.0087";
	/** Register {0} after delete */
	public static final String SC_GENERATESOURCECODE_0088 = "sc.generatesourcecode.0088";


	public static final Map<Integer, String> formulaType = new HashMap<Integer, String>();

	static {
		formulaType.put(0, "+");
		formulaType.put(1, "-");
		formulaType.put(2, "*");
		formulaType.put(3, "/");
		formulaType.put(4, "&&");
		formulaType.put(5, "||");
		formulaType.put(6, "!");
		formulaType.put(7, "==");
		formulaType.put(8, "<");
		formulaType.put(9, "<=");
		formulaType.put(10, ">");
		formulaType.put(11, ">=");
		formulaType.put(12, "!=");
		formulaType.put(13, "(");
		formulaType.put(14, ")");
		formulaType.put(15, "\"\"");
		formulaType.put(22, "null");
		formulaType.put(24, "lstInfoMessages.getList().size()");
		formulaType.put(25, "lstWarningMessages.getList().size()");
		formulaType.put(26, "lstErrorMessages.getList().size()");
	}
	
	public static final Integer FUNCTION_TYPE_COMMON = 0;
	public static final Integer FUNCTION_TYPE_CUSTOMIZE = 1;
	public static final Integer FORMULA_TYPE_NEW_OBJECT = 27;
	
	public static final class Types {
		
		public static final int BLOGIC = 0;
		
		public static final int SQL = 1;
		
		public static final int DECISION = 2;
		
	}
	
	public static final class TypeScope {
		
		public static final int INPUTBEAN = 0;
		
		public static final int OBJECTDEFINITION = 1;
		
		public static final int OUTPUTBEAN = 2;
	}
	
	public static final class ModuleScope {
		
		public static final int BLOGIC = 0;
		
		public static final int SQL = 1;
		
		public static final int DECISION = 2;
		
		public static final int FUNCTION_MASTER = 3;
	}
}
