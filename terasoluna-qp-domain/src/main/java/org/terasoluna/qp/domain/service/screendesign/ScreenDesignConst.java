package org.terasoluna.qp.domain.service.screendesign;

public class ScreenDesignConst {

	public static final String ITEM_SPLIT = "π";
	public static final String ROW_SPLIT = "�";
	/** Item code {0} at line {1} is duplicated in {2} Area*/
	public static final String SC_SCREENDESIGN_0195 = "sc.screendesign.0195";
	/**screen*/
	public static final String SC_SCREENDESIGN_0001 = "sc.screendesign.0001";
	
	public static final String SEARCH_SCREEN = "SearchScreen";
	public static final String REGISTER_SCREEN = "RegisterScreen";
	public static final String CONFIRM_REGISTER_SCREEN = "ConfirmRegisterScreen";
	public static final String COMPLETE_REGISTER_SCREEN = "CompleteRegisterScreen";
	public static final String VIEW_SCREEN = "ViewScreen";
	public static final String MODIFY_SCREEN = "ModifyScreen";
	public static final String CONFIRM_MODIFY_SCREEN = "ConfirmModifyScreen";
	public static final String COMPLETE_MODIFY_SCREEN = "CompleteModifyScreen";
	public static final Integer MANDATORY = 1;
	/** Search */
	public static final String SC_SCREEN_DESIGN_0310 = "sc.screendesign.0310";
	/** Register */
	public static final String SC_SCREEN_DESIGN_0309 = "sc.screendesign.0309";
	/** View */
	public static final String SC_SCREEN_DESIGN_0311 = "sc.screendesign.0311";
	/** Modify */
	public static final String SC_SCREEN_DESIGN_0312 = "sc.screendesign.0312";
	/**MultipartFile*/
	public static final String MULTIPART_FILE="MultipartFile";
	/**Datasource configuration for column [{0}] of table [{1}] has been deleted.*/
	public static final String ERR_SCREENDESIGN_0433="err.screendesign.0433";
	
	public static final class FieldStyle {
		
		public static final String NUMERIC = "result-numeric";
		
		public static final String DATETIME = "result-date-time";
		
		public static final String DATE = "result-date";
		
		public static final String TEXT = "result-text";
	}
	
	public static final class ParameterStatus {
		/**Parameter {0} has already updated in screen {1}. Please change in screen {2}*/
		public static final String UPDATE_PARAMETER = "sc.screendesign.0333";
		/**Parameter {0} is new paramter insert in screen {1}. Please change in screen {2}*/
		public static final String INSERT_PARAMETER = "sc.screendesign.0334";
		/**Parameter {0} has already deleted in screen {1}. Please change in screen {2}*/
		public static final String DELETE_PARAMETER = "sc.screendesign.0335";
	}
	/**
	 * Fix some type
	 * 
	 * @author quangvd
	 *
	 */
	public static final class ScreenActionConst {
		public static final Integer SUBMIT_METHOD_TYPE_GET = 1;
		public static final Integer SUBMIT_METHOD_TYPE_POST = 2;
		public static final Integer ACTION_TYPE_SCREEN = 0;
		public static final Integer ACTION_TYPE_BLOGIC = 1;
		public static final String IS_SUBMIT_TRUE = "true";
		public static final String IS_SUBMIT_FALSE = "false";
	}
	public static final class ScreenItemConst {
		public static final Integer ITEM_TYPE_HIDDEN = 2;
		public static final Integer CODELIST_TYPE_SYSTEM = 1;
		public static final Integer CODELIST_TYPE_TABLE = 2;
		public static final Integer CODELIST_TYPE_CUSTOM = 3; 
		public static final String VALUE_TRUE = "true"; 
		public static final String VALUE_FALSE = "false"; 
		public static final Integer DATA_SOURCE_TYPE_BLOGIC_DEFINE = 1;
		public static final Integer DATA_SOURCE_TYPE_CODELIST = 2;
	}
	
	public static final class ScreenItemBeanTypeConst {
		public static final Integer INPUT = 1;
		public static final Integer OUTPUT = 0;
	}
	
	public static final class ScreenElementConst {
		public static final Integer SCREEN = 0;
		public static final Integer FORM = 1;
		public static final Integer AREA = 2;
		public static final Integer GROUP_ITEM = 3;
		public static final Integer ITEM = 4;
	}
	
	public static final class DesignMode {
		public static final Integer PROTOTYPE = 1;
		public static final Integer DESIGN = 2;
	}
	
	public static final class ConfirmType {
		public static final Integer NONE = 0;
		public static final Integer MESSAGE = 1;
		public static final Integer SCREEN = 2;
	}
	
	public static final class CompleteType {
		public static final Integer MESSAGE = 1;
		public static final Integer SCREEN = 2;
	}
	
	public static final class ScreenPatternType {
		public static final Integer SEARCH = 1;
		public static final Integer REGISTER = 2;
		public static final Integer VIEW = 3;
		public static final Integer MODIFY = 4;
	}
	
	public static final class ItemType {
		public static final Integer FORM = 1;
		public static final Integer AREA = 2;
		public static final Integer ITEM = 3;
	}
	
	public static final class ShowLabel {
		public static final Integer NON_SHOW_LABEL = 0;
		public static final Integer SHOW_LABEL = 1;
		
	}
	
	public static final class EnableConfirm {
		public static final Integer DISABLE_CONFIRM = 0;
		public static final Integer ENABLE_CONFIRM = 1;
	}
	
	public static final class ScreenTransitionType {
		public static final Integer SCREEN_TO_SCREEN = 0;
		public static final Integer SCREEN_TO_BRANCH = 1;
		public static final Integer BRANCH_TO_SCREEN = 2;
	}
	
	public static final class ButtonType {
		public static final Integer BUTTON_TYPE_DEFAULT = 0;
		public static final Integer BUTTON_TYPE_SAVE = 1;
		public static final Integer BUTTON_TYPE_DELETE = 2;
		public static final Integer BUTTON_TYPE_CLIENT = 3;
	}
	
	public static final class TableMappingType {
		public static final Integer SINGLE = 0;
		public static final Integer LIST = 1;
	}
	
	public static final class AreaCustomType {
		public static final Integer NORMAL = 0;
		public static final Integer ADD_REMOVE_TABLE = 1;
	}
	
	public static final class AreaTypeAction {
		public static final Integer VIEW = 0;
		public static final Integer ADD_REMOVE = 1;
		public static final Integer PAGEABLE = 2;
	}
	
	public static final class FixedRow {
		public static final Integer NO = 0;
		public static final Integer YES = 1;
	}
	
	public static final class OBJECT_MAPPING_TYPE {
		public static final Integer MAPPING_TABLE_DESIGN = 0;
		public static final Integer MAPPING_INPUT_BEAN = 1;
	}
	
	public static final class AreaType {
		public static final Integer SINGLE = 0;
		public static final Integer LIST = 1;
	}
	
	public static final class ReturnType {
		public static final Integer SINGLE = 0;
		public static final Integer MULTIPLE = 1;
	}
	
	public static final class FromTo {
		public static final Integer NORMAL = 0;
		public static final Integer FROM_TO = 1;
	}
}
