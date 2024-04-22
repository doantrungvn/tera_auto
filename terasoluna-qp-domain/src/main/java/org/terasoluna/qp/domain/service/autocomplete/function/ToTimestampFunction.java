package org.terasoluna.qp.domain.service.autocomplete.function;

import java.text.MessageFormat;

public class ToTimestampFunction {
	
	public static final String TO_TIMESTAMP_VALUE = "to_timestamp(''{0}'',''{1}'')";
	
	private String value;
	private String format;
	
	private ToTimestampFunction(String format){
		this.format = format;
	}
	
	public static ToTimestampFunction get(String format){
		return new ToTimestampFunction(format);
	}
	
	public ToTimestampFunction value(String value){
		this.value = value;
		return this;
	}
	
	public String toTimestampValue(){
		return MessageFormat.format(TO_TIMESTAMP_VALUE, this.value, this.format);
	}
	
}
