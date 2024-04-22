package org.terasoluna.qp.app.common.validation;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

public class MinMaxValidateCharSequence {
	protected BigDecimal defaultValue;

	public void initialize(String defaultValue) {
		this.defaultValue = new BigDecimal( defaultValue);
	}
	
	public boolean validateEqual(CharSequence value){
		if(StringUtils.isBlank(value))
			return true;
		int result = new BigDecimal( value.toString() ).compareTo(defaultValue);
		return result == 0; 
	}
	
	public boolean validateNotEqual(CharSequence value){
		if(StringUtils.isBlank(value))
			return true;
		int result = new BigDecimal( value.toString() ).compareTo(defaultValue);
		return result != 0; 
	}
}
