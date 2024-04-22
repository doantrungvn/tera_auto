package org.terasoluna.qp.domain.service.generateddl;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.DbDomainConst.BaseType;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.model.TableDesignDetails;

@Service
@Transactional
public class GenerateOracleDDLServiceImpl extends GenerateDDLServiceImpl implements GenerateOracleDDLService {
	private static final String ORACLE_CREATE_SEQ_TEMPLATE = "CREATE SEQUENCE {0} MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 20;";
	private static final String ORACLE_ALTER_PK_TEMPLATE = "ALTER TABLE {0} ADD CONSTRAINT {1} PRIMARY KEY ({2});";
	private static final String ORACLE_ALTER_INDEX_TEMPLATE = "CREATE INDEX {0} ON {1} ({2});";
	private static final String ORACLE_ALTER_UNIQUE_TEMPLATE = "ALTER TABLE {0} ADD CONSTRAINT {1} UNIQUE ({2});";
	private static final String ORACLE_ALTER_FK_TEMPLATE = "ALTER TABLE {0} ADD CONSTRAINT {1} FOREIGN KEY ({2}) REFERENCES {3}({4});";
	private static final String ORACLE_CREATE_TABLE_TEMPLATE = "CREATE TABLE {0} \n({1}\n);";
	private static final String ORACLE_CREATE_TABLE_COL_PK_DEFAULT_TEMPLATE = "\n\t{0} {1}{2}{3}";
	private static final String ORACLE_CREATE_TRIGGER_FOR_SEQ_TEMPLATE = "CREATE OR REPLACE TRIGGER {0} BEFORE \n\tINSERT ON {1} FOR EACH ROW \n\tBEGIN IF :NEW.{2} IS NULL THEN\n\t\tSELECT {3}.NEXTVAL INTO :NEW.{2} FROM DUAL;\n\tEND IF;\nEND;";

	private static final String ORACLE_DROP_SEQ_TEMPLATE = "qp_common.qp_del_object(''{0}'', ''SEQUENCE'');";
	private static final String ORACLE_DROP_TABLE_TEMPLATE = "qp_common.qp_del_object(''{0}'', ''TABLE'');";

	public void initData() {
		ALTER_FK_TEMPLATE = ORACLE_ALTER_FK_TEMPLATE;
		CREATE_SEQ_TEMPLATE = ORACLE_CREATE_SEQ_TEMPLATE;
		ALTER_PK_TEMPLATE = ORACLE_ALTER_PK_TEMPLATE;
		ALTER_INDEX_TEMPLATE = ORACLE_ALTER_INDEX_TEMPLATE;
		ALTER_UNIQUE_TEMPLATE = ORACLE_ALTER_UNIQUE_TEMPLATE;
		CREATE_TABLE_COL_PK_DEFAULT_TEMPLATE = ORACLE_CREATE_TABLE_COL_PK_DEFAULT_TEMPLATE;
		// ALTER_SEQ_TO_TABLE_TEMPLATE = POSTGRE_ALTER_SEQ_TO_TABLE_TEMPLATE;
		CREATE_TABLE_TEMPLATE = ORACLE_CREATE_TABLE_TEMPLATE;
		DROP_TABLE_TEMPLATE = ORACLE_DROP_TABLE_TEMPLATE;
		DROP_SEQ_TEMPLATE = ORACLE_DROP_SEQ_TEMPLATE;
	}

	/**
	 * Build sql query for creating trigger
	 * 
	 * @param TableDesign
	 *            table information
	 * @return String sql query
	 */
	protected String buildSQLCreateTrigger(TableDesign table) {
		StringBuilder sql = new StringBuilder();
		for (TableDesignDetails column : table.getDetails()) {
			if (StringUtils.isNoneBlank(column.getSeqCode())) {
				/*
				 * sql.append(StringUtils.LF); sql.append(MessageFormat.format(ORACLE_CREATE_SEQ_TEMPLATE, column.getSeqCode()));
				 */
				sql.append(StringUtils.LF);
				sql.append(MessageFormat.format(ORACLE_CREATE_TRIGGER_FOR_SEQ_TEMPLATE, "tri_" + column.getSeqCode(), table.getTableCode(), column.getCode(), column.getSeqCode()));
				sql.append(StringUtils.LF);
				sql.append("/");
				sql.append(StringUtils.LF);
			} /*
				 * else { column.setSeqCode(StringUtils.EMPTY); }
				 */
		}
		return sql.toString();
	}

	/**
	 * Build sql query for creating tables
	 * 
	 * @param TableDesign
	 *            table information
	 * @return String sql query
	 */
	protected String buildSQLCreateTable(TableDesign table) {
		StringBuilder sql = new StringBuilder();
		StringBuilder sqlCols = new StringBuilder();
		int index = 0;
		for (TableDesignDetails col : table.getDetails()) {
			String notNull = StringUtils.EMPTY;
			String defaultValue = StringUtils.EMPTY;
			String baseType = StringUtils.EMPTY;
			if (1 == col.getIsMandatory()) {
				notNull = " NOT NULL";
			}
			baseType = convertBaseTypeToDb(col);
			index++;
			if (null != baseType && baseType.length() != 0) {
				sqlCols.append(MessageFormat.format(ORACLE_CREATE_TABLE_COL_PK_DEFAULT_TEMPLATE, col.getCode(), baseType, defaultValue, notNull));
				if (index != table.getDetails().size()) {
					sqlCols.append(",");
				}
			}
		}
		sql.append(StringUtils.LF);
		sql.append(MessageFormat.format(ORACLE_CREATE_TABLE_TEMPLATE, table.getTableCode(), sqlCols.toString()));

		// Create trigger after table created
		String sqlSeq = buildSQLCreateTrigger(table);
		if (null != sqlSeq && sqlSeq.length() != 0) {
			sql.append(sqlSeq);
			sql.append(StringUtils.LF);
		}
		return sql.toString();
	}

	/**
	 * Convert qp system base type to oracle data type
	 * 
	 * @param TableDesignDetails
	 *            column information
	 * @return String data type
	 */
	public String convertBaseTypeToDb(TableDesignDetails column) {
		StringBuilder result = new StringBuilder(StringUtils.EMPTY);
		switch (column.getBaseType()) {
		case BaseType.CHARACTER_VARYING_BASETYPE:
			if (null != column.getMaxlength() && 0 != column.getMaxlength()) {
				result.append("VARCHAR2(" + column.getMaxlength() + " char)");
			}
			this.getDefaultValueText(column, result);
			break;
		case BaseType.CHAR_BASETYPE:
			if (null != column.getMaxlength() && 0 != column.getMaxlength()) {
				result.append("CHAR(" + column.getMaxlength() + ")");
			}
			this.getDefaultValueText(column, result);
			break;
		case BaseType.TEXT_BASETYPE:
			if ((null == column.getMaxlength() || 0 == column.getMaxlength()) || (null != column.getMaxlength() && DbDomainConst.MAX_VAL_OF_TEXT < column.getMaxlength())) {
				result.append("NCLOB");
			} else {
				result.append("VARCHAR2(" + column.getMaxlength() + " char)");
			}
			this.getDefaultValueText(column, result);
			break;
		case BaseType.NUMERIC_BASETYPE:
			if (null != column.getDecimalPart() && 0 != column.getDecimalPart()) {
				result.append("NUMBER(" + column.getPrecision() + "," + column.getDecimalPart() + ")");
			} else if (column.getMaxlength() != null && column.getMaxlength() != 0 ) {
				result.append("NUMBER(" + column.getMaxlength() + ")");
			} else {
				result.append("NUMBER");
			}

			this.getDefaultNumeric(column, result);
			break;
		case BaseType.INTEGER_BASETYPE:
		case BaseType.SERIAL_BASETYPE:
			result.append("NUMBER(10)");
			this.getDefaultNumeric(column, result);
			break;
		case BaseType.SMALLINT_BASETYPE:
			result.append("NUMBER(5)");
			this.getDefaultNumeric(column, result);
			break;
		case BaseType.BIGINT_BASETYPE:
		case BaseType.BIGSERIAL_BASETYPE:
			result.append("NUMBER(19)");
			this.getDefaultNumeric(column, result);
			break;
		case BaseType.BOOLEAN_BASETYPE:
			/* if (null != column.getMaxlength() && 0 != column.getMaxlength()) { */
			result.append("NUMBER(1)");
			/* } */
			// Default value
			if (null != column.getDefaultValue() && column.getDefaultValue().length() != 0) {
				result.append(" DEFAULT ");
				result.append("'");
				if (Integer.valueOf(column.getDefaultValue()) == 0) {
					result.append("0");
				} else {
					result.append("1");
				}
				result.append("'");
				result.append(StringUtils.SPACE);
				column.setSeqCode(StringUtils.EMPTY);
			}
			break;
		case BaseType.BINARY_BASETYPE:
			result.append("BLOB");
			this.getDefaultValueText(column, result);
			break;
		case BaseType.DATE_BASETYPE:

			result.append("DATE");

			if (column.getDefaultType() != null) {
				if (Integer.valueOf(column.getDefaultType()) == 1) {
					result.append(" DEFAULT ");
					result.append("sysdate");
				} /*else if (StringUtils.isNoneBlank(column.getDefaultValue())) {
					result.append(" DEFAULT TO_DATE(");
					result.append(" '");
					result.append(column.getDefaultValue());
					result.append(" ','");
					result.append(dateFormat);
					result.append("')");
				}*/
				column.setSeqCode(StringUtils.EMPTY);
			}

			break;

		case BaseType.TIME_BASETYPE:
			result.append("DATE");

			if (column.getDefaultType() != null) {
				if (Integer.valueOf(column.getDefaultType()) == 1) {
					result.append(" DEFAULT ");
					result.append("sysdate");
				} /*else if (StringUtils.isNoneBlank(column.getDefaultValue())) {
					result.append(" DEFAULT TO_DATE(");
					result.append(" '");
					result.append(column.getDefaultValue());
					result.append(" ','");
					result.append(timeFormat);
					result.append("')");
				}*/
				column.setSeqCode(StringUtils.EMPTY);
			}

			break;
		case BaseType.DATETIME_BASETYPE:
			result.append("DATE");

			if (column.getDefaultType() != null) {
				if (Integer.valueOf(column.getDefaultType()) == 1) {
					result.append(" DEFAULT ");
					result.append("sysdate");
				} /*else if (StringUtils.isNoneBlank(column.getDefaultValue())) {
					result.append(" DEFAULT TO_DATE(");
					result.append(" '");
					result.append(column.getDefaultValue());
					result.append(" ','");
					result.append(dateTimeFormat);
					result.append("')");
				}*/
				column.setSeqCode(StringUtils.EMPTY);
			}

			break;
		case BaseType.CURRENCY_BASETYPE:
			// result.append("NUMBER(19,4)");
			if (null != column.getDecimalPart() && 0 != column.getDecimalPart()) {
				result.append("NUMBER(" + column.getPrecision() + "," + column.getDecimalPart() + ")");
			} else {
				result.append("NUMBER(" + column.getMaxlength() + ")");
			}
			this.getDefaultNumeric(column, result);
			break;
		case BaseType.FLOAT_BASETYPE:
			result.append("BINARY_FLOAT");
			break;
		case BaseType.DOUBLE_BASETYPE:
			result.append("BINARY_DOUBLE");
			break;
		case BaseType.BYTE_BASETYPE:
			result.append("NUMBER(3)");
			this.getDefaultNumeric(column, result);
			break;
		default:
			result.append(StringUtils.EMPTY);
			break;
		}
		return result.toString();
	}

}
