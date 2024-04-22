package org.terasoluna.qp.domain.model;

public class ConditionModel {
	private String columnName;
	private String operator;
	private Object value;
	private String logicCode;
	private String functionCode;
	private String pattern;
	public static final String FORMAT_TIME = "HH24:MI:SS";

	private final String defaultLogicCode = " AND ";

	public enum FunctionCode {
		TRUNC("trunc"), TOCHAR("to_char"), UPPER("upper");

		private FunctionCode(String code) {
			this.code = code;
		}

		private String code;

		public String getCode() {
			return this.code;
		}
	}

	public enum YesNoFlg {
		NO(0), YES(1);
		YesNoFlg(Integer num) {
			this.num = num;
		}

		private Integer num;

		public Integer getNum() {
			return this.num;
		}

	}

	public enum LogicCode {
		AND("AND"), OR("OR");
		private LogicCode(String text) {
			this.text = text;
		}

		private String text;

		public String getText() {
			return this.text;
		}
	}

	public enum Operator {
		EQUAL("="), NOT_EQUAL("<>"), GREATER_THAN(">"), GREATER_THAN_OR_EQUAL(">="), LESS_THAN("<"), LESS_THAN_OR_EQUAL("<=");

		private Operator(String text) {
			this.text = text;
		}

		private String text;

		public String getText() {
			return this.text;
		}
	}

	public ConditionModel(String pcolumnName, String poperator, Object pvalue) {
		this.columnName = pcolumnName;
		this.operator = poperator;
		this.value = pvalue;
		this.logicCode = defaultLogicCode;
	}

	public ConditionModel(String pcolumnName, String poperator, Object pvalue, String plogicCode) {
		this.columnName = pcolumnName;
		this.operator = poperator;
		this.value = pvalue;
		this.logicCode = plogicCode;
	}

	public ConditionModel(String pcolumnName, String poperator, Object pvalue, String plogicCode, String functionCode) {
		this.columnName = pcolumnName;
		this.operator = poperator;
		this.value = pvalue;
		this.logicCode = plogicCode;
		this.functionCode = functionCode;
	}

	public ConditionModel(String pcolumnName, String poperator, Object pvalue, String plogicCode, String functionCode, String pattern) {
		this.columnName = pcolumnName;
		this.operator = poperator;
		this.value = pvalue;
		this.logicCode = plogicCode;
		this.functionCode = functionCode;
		this.pattern = pattern;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getLogicCode() {
		return logicCode;
	}

	public void setLogicCode(String logicCode) {
		this.logicCode = logicCode;
	}

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
