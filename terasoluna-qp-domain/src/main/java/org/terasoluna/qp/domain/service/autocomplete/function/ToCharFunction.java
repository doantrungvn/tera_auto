package org.terasoluna.qp.domain.service.autocomplete.function;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.domain.service.autocomplete.SqlBuilderServiceImpl.Const;

public class ToCharFunction {
	private String value;
	private String tableName;
	private String columnName;
	private String format;
	public static final String TO_CHAR = "to_char";
	public static final String FORMAT_TIME = "HH24:MI:SS";
	public static final String TO_CHAR_FUNCTION_TEXT = "to_char(''{0}'',''{1}'')";
	public static final String TO_CHAR_FUNCTION_ENTITY = "to_char({0},''{1}'')";
	public static final String TO_CHAR_NON_FORMAT = "to_char({0})";
	
	private ToCharFunction(){
	}
	
	public static ToCharFunction get(){
		return new ToCharFunction();
	}
	
	
	public ToCharFunction format(String format){
		this.format = format;
		return this;
	}
	
	public ToCharFunction date(String date){
		this.value = date;
		return this;
	}
	
	public ToCharFunction tableName(String tableName){
		this.tableName = tableName;
		return this;
	}
	
	public ToCharFunction columnName(String columnName){
		this.columnName = columnName;
		return this;
	}
	
	public String toChar(){
		return MessageFormat.format(TO_CHAR_FUNCTION_ENTITY, this.value, this.getFormat());
	}
	
	public String toCharDate(String format){
		String toDate = ToDateFunction.get(format).date(this.value).toDateText();
		return MessageFormat.format(TO_CHAR_FUNCTION_ENTITY,toDate,FORMAT_TIME);
	}
	
	public String toCharEntityWithoutFormat(){
		String tableColumnName = this.getTableColumnName();
		return MessageFormat.format(TO_CHAR_NON_FORMAT, tableColumnName);
	}
	
	public String toCharEntity(){
		String tableColumnName = this.getTableColumnName();
		return MessageFormat.format(TO_CHAR_FUNCTION_ENTITY, tableColumnName, this.getFormat());
	}
	
	private String getTableColumnName(){
		return new StringBuffer(this.tableName).append(Const.DOT).append(this.columnName).toString();
	}
	
	private String getFormat(){
		return StringUtils.isEmpty(this.format) ? FORMAT_TIME : this.format;
	}
}
