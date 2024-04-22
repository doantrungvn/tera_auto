package org.terasoluna.qp.domain.service.importschema;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Component;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.domain.model.ImportSchema;

@Component("ImportSchemaOracleHandler")
public class ImportSchemaOracleHandler {
	
	public static final String JDBC_DATA_TYPE = "jdbc_type";
	public static final String DATA_TYPE = "data_type";
	
	public static final String JDBC_BIT = "BIT";
	public static final String JDBC_VARCHAR = "VARCHAR";
	public static final String JDBC_LONGVARCHAR = "LONGVARCHAR";
	
	public static final String JDBC_NUMERIC = "NUMERIC";
	public static final String JDBC_DECIMAL = "DECIMAL";
	public static final String JDBC_TINYINT = "TINYINT";
	public static final String JDBC_SMALLINT = "SMALLINT";
	public static final String JDBC_INTEGER = "INTEGER";
	public static final String JDBC_BIGINT = "BIGINT";
	public static final String JDBC_REAL = "REAL";
	public static final String JDBC_FLOAT = "FLOAT";
	public static final String JDBC_DOUBLE = "DOUBLE";
	
	public static final String JDBC_BINARY = "BINARY";
	public static final String JDBC_VARBINARY = "VARBINARY";
	public static final String JDBC_LONGVARBINARY = "LONGVARBINARY";
	public static final String JDBC_DATE = "DATE";
	public static final String JDBC_TIME = "TIME";
	public static final String JDBC_TIMESTAMP = "TIMESTAMP";
	
	public static final String JDBC_money = "money";
	public static final String JDBC_char = "\"char\"";
	public static final String JDBC_boolean = "boolean";
	public static final String JDBC_time = "time with time zone";
	
	/**
	 * 
	 * @param column
	 */
	public int mapDataType(ResultSet resultSet) {
		String dataType;
		int returnDataType = 0;
		try {
			dataType = resultSet.getString("data_type");
			// Number
			if ("number".equalsIgnoreCase(dataType)) {
				returnDataType = DbDomainConst.BaseType.NUMERIC_BASETYPE;
	
				/*Integer precision = resultSet.getString("data_precision") == null ? null : Integer.parseInt(resultSet.getString("data_precision"));
				Integer scale = resultSet.getString("data_scale") == null ? null : Integer.parseInt(resultSet.getString("data_scale"));
	
				if(precision == null && scale == null){
					returnDataType = DbDomainConst.BaseType.NUMERIC_BASETYPE;
				}else if (scale != null && scale.equals(scale)) {
					if (precision != null) {
						if (precision.equals(5)) {
							returnDataType = DbDomainConst.BaseType.SMALLINT_BASETYPE;
						} else if (precision.equals(10)) {
							returnDataType = DbDomainConst.BaseType.INTEGER_BASETYPE;
						} else if (precision.equals(19)) {
							returnDataType = DbDomainConst.BaseType.BIGINT_BASETYPE;
						} else if (precision.equals(1)) {
							returnDataType = DbDomainConst.BaseType.BOOLEAN_BASETYPE;
						}
					} else {
						returnDataType = DbDomainConst.BaseType.NUMERIC_BASETYPE;
					}
				} else if (precision.equals(19) && scale.equals(4)) {
					returnDataType = DbDomainConst.BaseType.CURRENCY_BASETYPE;
				} else {
					returnDataType = DbDomainConst.BaseType.NUMERIC_BASETYPE;
				}*/
			} else if (JDBC_FLOAT.equalsIgnoreCase(dataType)) {
				returnDataType = DbDomainConst.BaseType.FLOAT_BASETYPE;
			} else if (dataType.contains(JDBC_DOUBLE)) {
				returnDataType = DbDomainConst.BaseType.DOUBLE_BASETYPE;
			} else if ("NCLOB".equalsIgnoreCase(dataType)) {
				// Character
				returnDataType = DbDomainConst.BaseType.TEXT_BASETYPE;
			} else if (dataType.contains("NVARCHAR")) {
				// Character
				returnDataType = DbDomainConst.BaseType.CHARACTER_VARYING_BASETYPE;
			}
			else if ("bpchar".equalsIgnoreCase(dataType) || "char".equalsIgnoreCase(dataType)) {
				// Character
				returnDataType = DbDomainConst.BaseType.CHAR_BASETYPE;
			}
			// Binary data BLOB BFILE
			else if ("BLOB".equalsIgnoreCase(dataType)) {
				returnDataType = DbDomainConst.BaseType.BINARY_BASETYPE;
			}
			
			// Date time data
			else if (dataType.contains("TIMESTAMP") || dataType.contains("DATE")) {
				if (dataType.contains("WITH TIME ZONE")) {
					returnDataType = DbDomainConst.BaseType.DATETIME_BASETYPE;
				}else{
					returnDataType = DbDomainConst.BaseType.DATE_BASETYPE;
				}
			}else {
				System.out.println("Unable to map column " + resultSet.getString("column_name") + " data type " + dataType);
				returnDataType = DbDomainConst.BaseType.CHARACTER_VARYING_BASETYPE;
			}
			return returnDataType;
		} catch (SQLException e) {
			e.printStackTrace();
			return returnDataType;
		}
	}
	
	/**
	 * 
	 * @param importSchema
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public ResultSet createResultSet(ImportSchema importSchema, Connection connection, Statement statement, String query){
		ResultSet rs = null;
		try {
			// execute select SQL stetement
			rs = statement.executeQuery(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return rs;
	}
	
	/**
	 * 
	 * @param importSchema
	 * @return
	 * @throws SQLException 
	 */
	public Connection createConnection(ImportSchema importSchema) throws SQLException{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return DriverManager.getConnection(this.createNameConnection(importSchema), importSchema.getDbUser(), importSchema.getDbPassword());
	}
	
	/**
	 * 
	 * @param importSchema
	 * @return
	 */
	private String createNameConnection(ImportSchema importSchema){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("jdbc:oracle:thin:@");
		stringBuilder.append(importSchema.getDbHostName());
		stringBuilder.append(":");
		stringBuilder.append(importSchema.getDbPort());
		stringBuilder.append(":");
		stringBuilder.append(importSchema.getSchemaName());
		return stringBuilder.toString();
	}
}
