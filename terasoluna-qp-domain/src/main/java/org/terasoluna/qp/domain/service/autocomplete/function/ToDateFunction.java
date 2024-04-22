package org.terasoluna.qp.domain.service.autocomplete.function;

import java.text.MessageFormat;

import org.terasoluna.qp.domain.service.autocomplete.SqlBuilderServiceImpl.Const;

public class ToDateFunction {
	
	private String date;
	private String format;
	private String tableName;
	private String columnName;
	
	public static final String TO_DATE_FUNCTION_TEXT = "to_date(''{0}'',''{1}'')";
	public static final String TO_DATE_FUNCTION_ENTITY = "to_date({0},''{1}'')";
	
	private ToDateFunction(String format) {
		this.format = format;
	}
	
	public static ToDateFunction get(String format){
		return new ToDateFunction(format);
	}
	
	public ToDateFunction date(String date){
		this.date = date;
		return this;
	}
	
	public ToDateFunction tableName(String tableName){
		this.tableName = tableName;
		return this;
	}
	
	public ToDateFunction columnName(String columnName){
		this.columnName = columnName;
		return this;
	}
	
	public String toDateText(){
		return MessageFormat.format(TO_DATE_FUNCTION_TEXT,this.date, this.format);
	}
	
	public String toDateEntity(){
		String tableColumnName = this.getTableColumnName();
		return MessageFormat.format(TO_DATE_FUNCTION_ENTITY, tableColumnName, this.format);
	}
	
	private String getTableColumnName(){
		return new StringBuffer(this.tableName).append(Const.DOT).append(this.columnName).toString();
	}
}
