package org.terasoluna.qp.domain.service.autocomplete.function;

import java.text.MessageFormat;

public class ToNumberFunction {
	private String text;
	private String variable;
	public static final String TO_NUMBER = "to_number";
	public static final String TO_NUMBER_TEXT = "to_number(''{0}'')";
	public static final String TO_NUMBER_VARIABLE = "to_number({0})";
	
	private ToNumberFunction(){}
	
	public static ToNumberFunction get(){
		return new ToNumberFunction();
	}
	
	public ToNumberFunction text(String text){
		this.text = text;
		return this;
	}
	
	public ToNumberFunction variable(String variable){
		this.variable = variable;
		return this;
	}
	
	public String  toNumberText(){
		return MessageFormat.format(TO_NUMBER_TEXT, this.text);
	}
	
	public String toNumberVariable(){
		return MessageFormat.format(TO_NUMBER_VARIABLE, this.variable);
	}
}
