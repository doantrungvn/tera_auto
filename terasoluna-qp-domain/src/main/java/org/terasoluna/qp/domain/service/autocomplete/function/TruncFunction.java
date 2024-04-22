package org.terasoluna.qp.domain.service.autocomplete.function;

import java.text.MessageFormat;

import org.terasoluna.qp.domain.service.autocomplete.SqlBuilderServiceImpl.Const;

public class TruncFunction {
	private String text;
	private String tableName;
	private String columnName;
	
	public static final String TRUNC = "trunc";
	public static final String TRUNC_FUNCTION_TEXT = "trunc(''{0}'')";
	public static final String TRUNC_FUNCTION_ENTITY = "trunc({0})"; 
	
	private TruncFunction(){
	}
	
	public static TruncFunction get(){
		return new TruncFunction();
	}
	
	public TruncFunction text(String text){
		this.text = text;
		return this;
	}
	
	public TruncFunction tableName(String tableName){
		this.tableName = tableName;
		return this;
	}
	
	public TruncFunction columnName(String columnName){
		this.columnName = columnName;
		return this;
	}
	
	public  String truncText(){
		return MessageFormat.format(TRUNC_FUNCTION_TEXT, this.text);
	}
	
	public String trunc(){
		return MessageFormat.format(TRUNC_FUNCTION_ENTITY, this.text);
	}
	
	public  String truncDate(String formatDate){
		String todatate = ToDateFunction.get(formatDate).date(this.text).toDateText();
		return MessageFormat.format(TRUNC_FUNCTION_ENTITY, todatate);
	}
	
	public String truncEntity(){
		String tableColumnName = this.getTableColumnName();
		return MessageFormat.format(TRUNC_FUNCTION_ENTITY, tableColumnName);
	}
	
	public String truncEntityDate(String format){
		String toDateEntity = ToDateFunction.get(format).tableName(this.tableName).columnName(this.columnName).toDateEntity();
		return MessageFormat.format(TRUNC_FUNCTION_ENTITY, toDateEntity);
	}
	
	private String getTableColumnName(){
		return new StringBuffer(this.tableName).append(Const.DOT).append(this.columnName).toString();
	}
}
