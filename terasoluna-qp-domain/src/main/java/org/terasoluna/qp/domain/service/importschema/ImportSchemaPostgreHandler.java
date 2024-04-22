package org.terasoluna.qp.domain.service.importschema;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Component;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.domain.model.ImportSchema;

@Component("ImportSchemaPostgreHandler")
public class ImportSchemaPostgreHandler {
	
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
	 * @param resultSet
	 * @return
	 */
	public int mapDataType(ResultSet resultSet) {
		String dataType;
		int returnDataType = 0;
		try {
			dataType = resultSet.getString("udt_name");
			// Number
			if ("int2".equalsIgnoreCase(dataType)) {
				returnDataType = DbDomainConst.BaseType.SMALLINT_BASETYPE;
			} else if ("int4".equalsIgnoreCase(dataType)) {
				if(resultSet.getString("column_default") != null){
					returnDataType = DbDomainConst.BaseType.SERIAL_BASETYPE;
				}else{
					returnDataType = DbDomainConst.BaseType.INTEGER_BASETYPE;
				}
			} else if ("int8".equalsIgnoreCase(dataType)) {
				if(resultSet.getString("column_default") != null){
					returnDataType = DbDomainConst.BaseType.BIGSERIAL_BASETYPE;
				}else{
					returnDataType = DbDomainConst.BaseType.BIGINT_BASETYPE;
				}
			} else if ("numeric".equalsIgnoreCase(dataType)) {
				returnDataType = DbDomainConst.BaseType.NUMERIC_BASETYPE;
			} else if ("bool".equalsIgnoreCase(dataType)) {
				returnDataType = DbDomainConst.BaseType.BOOLEAN_BASETYPE;
			} else if ("float4".equalsIgnoreCase(dataType)) {
				returnDataType = DbDomainConst.BaseType.FLOAT_BASETYPE;
			} else if ("float8".equalsIgnoreCase(dataType)) {
				returnDataType = DbDomainConst.BaseType.NUMERIC_BASETYPE;
			} else if ("text".equalsIgnoreCase(dataType)) {
				// Character
				returnDataType = DbDomainConst.BaseType.TEXT_BASETYPE;
			} else if ("varchar".equalsIgnoreCase(dataType)) {
				// Character
				returnDataType = DbDomainConst.BaseType.CHARACTER_VARYING_BASETYPE;
			}
			else if ("bpchar".equalsIgnoreCase(dataType) || "char".equalsIgnoreCase(dataType)) {
				// Character
				returnDataType = DbDomainConst.BaseType.CHAR_BASETYPE;
			}
			// Binary data BLOB BFILE
			else if ("bytea".equalsIgnoreCase(dataType)) {
				returnDataType = DbDomainConst.BaseType.BINARY_BASETYPE;
			}
			else if ("money".equalsIgnoreCase(dataType)) {
				returnDataType = DbDomainConst.BaseType.CURRENCY_BASETYPE;
			}
			// Date time data
			else if ("date".equalsIgnoreCase(dataType)) {
				returnDataType = DbDomainConst.BaseType.DATE_BASETYPE;
			}else if ("timetz".equalsIgnoreCase(dataType) || "timet".equalsIgnoreCase(dataType) || "time".equalsIgnoreCase(dataType)) {
				returnDataType = DbDomainConst.BaseType.TIME_BASETYPE;
			} else if ("timestamptz".equalsIgnoreCase(dataType) || "timestamp".equalsIgnoreCase(dataType)) {
				returnDataType = DbDomainConst.BaseType.DATETIME_BASETYPE;
			} else {
				System.out.println("Unable to map column " + resultSet.getString("column_name") + " data type " + dataType);
				returnDataType = DbDomainConst.BaseType.CHARACTER_VARYING_BASETYPE;
			}
			return returnDataType;
		} catch (SQLException e) {
			return returnDataType;
		}
	}
	
	/**
	 * 
	 * @param importSchema
	 * @return
	 * @throws SQLException 
	 */
	public Connection createConnection(ImportSchema importSchema) throws SQLException{
		try {
			Class.forName("org.postgresql.Driver");
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
		stringBuilder.append("jdbc:postgresql://");
		stringBuilder.append(importSchema.getDbHostName());
		stringBuilder.append(":");
		stringBuilder.append(importSchema.getDbPort());
		stringBuilder.append("/");
		stringBuilder.append(importSchema.getDbName());
		return stringBuilder.toString();
	}
}
