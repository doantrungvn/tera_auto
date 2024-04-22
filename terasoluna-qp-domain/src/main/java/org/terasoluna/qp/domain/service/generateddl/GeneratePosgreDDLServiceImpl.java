package org.terasoluna.qp.domain.service.generateddl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.qp.app.common.constants.DbDomainConst.BaseType;
import org.terasoluna.qp.domain.model.TableDesignDetails;

@Service
@Transactional
public class GeneratePosgreDDLServiceImpl extends GenerateDDLServiceImpl implements GeneratePosgreDDLService {
	private static final String POSTGRE_ALTER_FK_TEMPLATE = "ALTER TABLE {0} ADD CONSTRAINT {1} FOREIGN KEY ({2}) REFERENCES {3}({4});";

	private static final String POSTGRE_DROP_SEQ_TEMPLATE = "DROP SEQUENCE IF EXISTS {0} CASCADE;";
	private static final String POSTGRE_CREATE_SEQ_TEMPLATE = "CREATE SEQUENCE {0} START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;";

	private static final String POSTGRE_ALTER_PK_TEMPLATE = "ALTER TABLE {0} ADD CONSTRAINT {1} PRIMARY KEY ({2});";

	private static final String POSTGRE_CREATE_INDEX_TEMPLATE = "CREATE INDEX {0} ON {1} ({2});";

	private static final String POSTGRE_ALTER_UNIQUE_TEMPLATE = "ALTER TABLE {0} ADD CONSTRAINT {1} UNIQUE ({2});";

	private static final String POSTGRE_DROP_TABLE_TEMPLATE = "DROP TABLE IF EXISTS {0} CASCADE;";
	private static final String POSTGRE_CREATE_TABLE_TEMPLATE = "CREATE TABLE {0} \n({1}\n);";

	private static final String POSTGRE_ALTER_SEQ_TO_TABLE_TEMPLATE = " DEFAULT nextval(''{0}''::regclass)";

	private static final String POSTGRE_CREATE_TABLE_COL_PK_DEFAULT_TEMPLATE = "\n\t{0} {1}{2}{3}";
	
	public void initData() {
		ALTER_FK_TEMPLATE = POSTGRE_ALTER_FK_TEMPLATE;
		CREATE_SEQ_TEMPLATE = POSTGRE_CREATE_SEQ_TEMPLATE;
		ALTER_PK_TEMPLATE = POSTGRE_ALTER_PK_TEMPLATE;
		ALTER_INDEX_TEMPLATE = POSTGRE_CREATE_INDEX_TEMPLATE;
		ALTER_UNIQUE_TEMPLATE = POSTGRE_ALTER_UNIQUE_TEMPLATE;
		CREATE_TABLE_COL_PK_DEFAULT_TEMPLATE = POSTGRE_CREATE_TABLE_COL_PK_DEFAULT_TEMPLATE;
		ALTER_SEQ_TO_TABLE_TEMPLATE = POSTGRE_ALTER_SEQ_TO_TABLE_TEMPLATE;
		CREATE_TABLE_TEMPLATE = POSTGRE_CREATE_TABLE_TEMPLATE;
		DROP_SEQ_TEMPLATE = POSTGRE_DROP_SEQ_TEMPLATE;
		DROP_TABLE_TEMPLATE = POSTGRE_DROP_TABLE_TEMPLATE;
	}

	/**
	 * Convert qp system base type to postgreSQL data type
	 * 
	 * @param TableDesignDetails
	 *            column information
	 * @return String data type
	 */
	public String convertBaseTypeToDb(TableDesignDetails column) {
		StringBuilder result = new StringBuilder(StringUtils.EMPTY);

		switch (column.getBaseType()) {
		case BaseType.CHARACTER_VARYING_BASETYPE:
			// Add Data Type
			if (null != column.getMaxlength() && 0 != column.getMaxlength()) {
				result.append("character varying(" + column.getMaxlength() + ")");
			} else {
				result.append("character varying");
			}

			getDefaultValueText(column, result);
			break;
		case BaseType.CHAR_BASETYPE:
			if (null != column.getMaxlength() && 0 != column.getMaxlength()) {
				result.append("char(" + column.getMaxlength() + ")");
			} else {
				result.append("char");
			}

			getDefaultValueText(column, result);
			break;
		case BaseType.TEXT_BASETYPE:

			result.append("text");

			getDefaultValueText(column, result);
			break;
		case BaseType.NUMERIC_BASETYPE:
			if (null != column.getDecimalPart() && 0 != column.getDecimalPart()) {
				result.append("numeric(" + column.getPrecision() + "," + column.getDecimalPart() + ")");
			} else if (column.getMaxlength() != null && column.getMaxlength() != 0 ) {
				result.append("numeric(" + column.getMaxlength() + ")");
			} else {
				result.append("numeric");
			}

			getDefaultNumeric(column, result);
			break;
		case BaseType.INTEGER_BASETYPE:
			result.append("integer");
			this.getDefaultNumeric(column, result);
			break;
		case BaseType.SMALLINT_BASETYPE:
		case BaseType.BYTE_BASETYPE:
			result.append("smallint");
			this.getDefaultNumeric(column, result);
			break;
		case BaseType.BIGINT_BASETYPE:
			result.append("bigint");
			this.getDefaultNumeric(column, result);
			break;
		case BaseType.BOOLEAN_BASETYPE:
			result.append("boolean");
			// Default value
			if (null != column.getDefaultValue() && column.getDefaultValue().length() != 0) {
				result.append(" DEFAULT ");
				result.append("'");
				if (Integer.valueOf(column.getDefaultValue()) == 0) {
					result.append("NO");
				} else {
					result.append("YES");
				}
				result.append("'");
				result.append(StringUtils.SPACE);
				column.setSeqCode(StringUtils.EMPTY);
			}
			break;
		case BaseType.BINARY_BASETYPE:
			result.append("bytea");
			// this.getDefaultValueText(column, result);
			break;
		case BaseType.DATE_BASETYPE:
			result.append("date");
			if (column.getDefaultType() != null) {
				if (Integer.valueOf(column.getDefaultType()) == 1) {
					result.append(" DEFAULT ");
					result.append("timezone('utc'::text, now())");
				} else if (StringUtils.isNoneBlank(column.getDefaultValue())){
					result.append(" DEFAULT ");
					result.append(" '");
					result.append(column.getDefaultValue());
					result.append("' ");
				}
				column.setSeqCode(StringUtils.EMPTY);
			}
			break;
		case BaseType.TIME_BASETYPE:
			result.append("time with time zone");
			if (column.getDefaultType() != null) {
				if (Integer.valueOf(column.getDefaultType()) == 1) {
					result.append(" DEFAULT ");
					result.append(" now() ");
				} else if (StringUtils.isNoneBlank(column.getDefaultValue())){
					result.append(" DEFAULT cast(");
					result.append(" '");
					result.append(column.getDefaultValue());
					result.append("' AS TIME)");
				}
				column.setSeqCode(StringUtils.EMPTY);
			}
			break;
		case BaseType.DATETIME_BASETYPE:
			result.append("timestamp with time zone");
			if (column.getDefaultType() != null) {
				if (Integer.valueOf(column.getDefaultType()) == 1) {
					result.append(" DEFAULT ");
					result.append("timezone('utc'::text, now())");
				} else if (StringUtils.isNoneBlank(column.getDefaultValue())){
					result.append(" DEFAULT cast(");
					result.append(" '");
					result.append(column.getDefaultValue());
					result.append("' AS TIMESTAMP)");
				}
				column.setSeqCode(StringUtils.EMPTY);
			}
			break;
		case BaseType.SERIAL_BASETYPE:
			// result.append("serial");.
			result.append("int");
			this.getDefaultNumeric(column, result);
			break;
		case BaseType.BIGSERIAL_BASETYPE:
			// result.append("bigserial");
			result.append("bigint");
			this.getDefaultNumeric(column, result);
			break;
		case BaseType.CURRENCY_BASETYPE:
			// result.append("money");
			if (null != column.getDecimalPart() && 0 != column.getDecimalPart()) {
				result.append("numeric(" + column.getPrecision() + "," + column.getDecimalPart() + ")");
			} else {
				result.append("numeric(" + column.getMaxlength() + ")");
			}
			this.getDefaultNumeric(column, result);
			break;
		case BaseType.FLOAT_BASETYPE:
			result.append("real");
			this.getDefaultNumeric(column, result);
			break;
		case BaseType.DOUBLE_BASETYPE:
			result.append("double precision");
			this.getDefaultNumeric(column, result);
			break;
		}

		return result.toString().toLowerCase();
	}
}
