package org.terasoluna.qp.domain.service.licensemanagement;

public final class LicenseManagementConst {

	
	/** temporary */
	public static final String TMP_PATH = "/resources/temporary/";

	/** File size */
	public static final String UNIT_OF_FILE_SIZE = "MB";

	public static final int LIMIT_FILE_SIZE = 6;

	public static final int LIMIT_ROW_IMPORT = 1000;

	public static final String LIMIT_ROW_IMPORT_NAME = "limitRowImport";

	public static final String LIMIT_FILE_SIZE_NAME = "limitFileSize";

	public static final String FILE_NAME_DATE_PATTERN = "yyyyMMddHHmmss";

	public static final String IMP_FILE_NAME_PREFIX = "license-decoded";
	
	public static final String FILE_NAME = "license.lic";

	public static final String TXT_TYPE = ".txt";

	public static final String ERR_WHEN_IMPORT = "objectError";

	public static final String ROW_IMPPORTED = "updateRows";

	public static final String IMPORTTING_DATA = "importData";

	public static final String LIST_DATA_INSERT = "listDataInsert";

	public static final String LIST_DATA_UPDATE = "listDataUpdate";

	public static final String LIST_DATA_DELETE = "listDataDelete";

	public static final String ROWS = "rows";

	public static final String ERR_COLUMN_NOT_MATCH = "err.imp.0004";
	
	public static final String INSERTED = "inserted";
	
	public static final String DELETED = "deleted";
	
	public static final String UPDATED = "updated";
	
	public static final String INSERT = "insert";
	
	public static final String DELETE = "delete";
	
	public static final String UPDATE= "update";
	
	public static final String NUMBER_CODELIST= "numberCodelist";

	public static final class TABLE_TYPE {
		public static final Integer MASTER = 0;
		public static final Integer DETAIL = 1;
	}

	public static final class DATA_TYPE {
		public static final String STRING = "0";
		public static final String INTEGER = "1";
		public static final String FLOAT = "2";
		public static final String DOUBLE = "3";
		public static final String LONG = "4";
		public static final String TIMESTAMP = "5";

	}

	public static final class IMPORT_RESULT_STATUS {
		public static final String REGISTER = "sc.importmanagement.0012";
		public static final String MODIFY = "sc.importmanagement.0013";
		public static final String ERROR = "sc.importmanagement.0014";
		public static final String DATA_CORRECT = "sc.importmanagement.0015";
	}

	public static final class ISMANDATORY {
		public static final String YES = "0";
	}

	public static final class PATTERN {
		public static final String YES = "0";
	}

	public static final class IMPORT_DATASOURCE {
		public static final String MESSAGE_DESIGN_OUTPUT_LOCATION = "messageDesignOutputLocation";
		public static final String MESSAGE_DESIGN_ERROR_LEVEL = "messageDesignErrorLevel";
		public static final String DOMAIN_DESIGN_NULL_VALUE = "domainDesignNullValue";
		public static final String CODELIST_DESIGN_VALUES_ONLY = "codelistDesignValuesOnly";
	}

	/**
	 * Import status
	 */
	public static final String SC_IMPORTMANAGEMENT_0018 = "sc.importmanagement.0018";

	/**
	 * Data type is incorect.
	 */
	public static final String SC_IMPORTMANAGEMENT_0019 = "sc.importmanagement.0019";

	/**
	 * Import result
	 */
	public static final String SC_IMPORTMANAGEMENT_0009 = "sc.importmanagement.0009";

	/** Data isn't correct. */
	public static final String ERR_IMPORTMANAGEMENT_0006 = "err.importmanagement.0006";

}
