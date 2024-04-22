
package org.terasoluna.qp.domain.service.domaindatatype;
import java.text.SimpleDateFormat;

import org.terasoluna.qp.app.common.ultils.FunctionCommon;


public final class DomainDatatypeConst {

	public static final String CHAR_SPLIT = "�";
	
	/**
	 * Fix some logical data type
	 * 
	 * @author dungnn1
	 *
	 */
	public static final class LogicDataType {
		public static final Long AUTOCOMPLETE = 0L;
		public static final Long LABEL = 20L;
		public static final Long CURRENCY = 8L;
		public static final Long TEXT = 21L;
		public static final Long FILEUPLOAD = 12L;
		public static final Long NAME = 1L;
		public static final Long DECIMAL = 3L;
		public static final Long INTEGER = 2L;
		public static final Long DATETIME = 14L;
		public static final Long DATE = 4L;
		public static final Long TIME = 9L;
		public static final Long CODE = 18L;
		public static final Long CHECKBOX = 6L;
		public static final Long EMAIL = 15L;
		public static final Long SELECT = 7L;
		public static final Long PHONE = 16L;
		public static final Long RADIO = 5L;
		public static final Long REMARK = 10L;
		public static final Long LINK = 11L;
		public static final Long BUTTON = 13L;
		public static final Long BOOLEAN = 17L;
		public static final Long ENTITY = 22L;
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

	public static final class CodelistType {
		public static final Integer SYSTEM_CODE_LIST = 1;
		public static final Integer TABLE_CODE_LIST = 2;
		public static final Integer SCREEN_CODE_LIST = 3;
	}

	/**
	 * Define status of domain mapping table
	 * 
	 * @author dungnn1
	 *
	 */
	public static final class DomainMappingStatus {
		public static final int UNDER_DESIGN = 1;
		public static final int FIXED = 2;
		public static final int DESIGN_CHANGE = 3;
	}

	/**
	 * define syn status between domain data type with physical data
	 * 
	 * @author dungnn1
	 *
	 */
	public static final class DomainMappingItemStatus {
		public static final int NO_CHANGE_STATUS = 0;
		public static final int NEW_STATUS = 1;
		public static final int DELETED_STATUS = 2;
		public static final int CHANGE_STATUS = 3;
	}

	/**
	 * define type key of column
	 * 
	 * @author dungnn1
	 *
	 */
	public static final class DatabaseKeyType {
		public static final int NONE = 0;
		public static final int PK = 1;
		public static final int FK = 2;
		public static final int UNIQUE = 4;
		public static final int INDEX = 8;
		public static final int FULLTEXT = 16;

		public static final int numOfKey = 5;
	}

	public static final class SQLDesignType {
		public static final int VIEW = 0;
		public static final int ADVANCED_VIEW = 1;
		public static final int AUTOCOMPLETE = 2;
		public static final int ADVANCED_AUTOCOMPLETE = 3;
		public static final int SQL_BUILDER = 4;
		public static final int ADVANCED_SQL = 5;
	}
	
	public static final class SQLDataType {
		public static final String Object = "0";
		public static final String Byte = "1";
		public static final String Short = "2";
		public static final String Integer = "3";
		public static final String Long = "4";
		public static final String Float = "5";
		public static final String Double = "6";
		public static final String Character = "7";
		public static final String Boolean = "8";
		public static final String String = "9";
		public static final String BigDecimal = "10";
		public static final String Timestamp = "11";
		public static final String Datetime = "12";
		public static final String Time = "13";
	}
	
	public static final class SqlConditionType{
		public static final String VALUE="0";
		public static final String ENTITY = "1";
		public static final String PARAMETER = "2";
	}
	public static final int VIEW_ACTION = 0;
	public static final int MODIFY_ACTION = 1;

	// Search all table in project
	public static final Long SEARCH_ALL_TABLE_DESIGN = -1L;
	// Search all table in project and not in subject area
	public static final Long SEARCH_TABLE_DESIGN_NOT_IN_SUBJECT_AREA = -2L;

	/**
	 * define type key of column
	 * 
	 * @author dungnn1
	 *
	 */
	public static final class DisplayType {
		public static final int MANDATORY = 1;
		public static final int DISPLAY = 2;
		public static final int HIDDEN = 4;
	}

	public static final String REGULAR_EXP_CODE = "^[a-zA-Z]+[0-9a-zA-Z_.]{1,29}$";
	public static final String REGULAR_EXP_NAME = "^[^\\\\:*?\"<>|.]{0,63}$";

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
		public static final Integer FLOAT= 16;
		public static final Integer DOUBLE= 17;
		public static final Integer BYTE= 19;

		public static final Integer DATE= 10;
		public static final Integer TIME= 11;
		public static final Integer DATETIME= 12;
	}
	
	/**
	 * size of numeric
	 * 
	 * @author trungdv
	 *
	 */
	public static final class NumericSize {
		public static final int SERIAL_MIN = 1;
		public static final long BIGSERIAL_MIN = 1;
	}
	
	public static void main(String[] args) {
		SimpleDateFormat format = new SimpleDateFormat("yyyMMdd_HHmmss");
		System.out.println(format.format(FunctionCommon.getCurrentTime()));
	}
	
}
