package org.terasoluna.qp.domain.service.autocomplete.function;

import java.text.MessageFormat;

public class ToTimeFunction {
	public static final String TO_TIME_VALUE = "to_time(''{0}'',''{1}'')";
	
	
	private String value;
	private String format;
	
	private ToTimeFunction(String format){
		this.format = format;
	}
	
	public static ToTimeFunction get(String format){
		return new ToTimeFunction(format);
	}
	
	public ToTimeFunction value(String value){
		this.value = value;
		return this;
	}
	
	public String toTimeValue(){
		return MessageFormat.format(TO_TIME_VALUE, this.value, this.format);
	}
}
