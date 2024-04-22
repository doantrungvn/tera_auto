package org.terasoluna.qp.app.common.validation;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

public abstract class MinValidateForCharSequenceBase extends MinMaxValidateCharSequence{

	public void initialize(String minValue) {
		this.defaultValue = new BigDecimal( minValue);
	}

	public boolean isValid(CharSequence value) {
		//null values are valid
		if (StringUtils.isBlank(value)) {
			return true;
		}
		try {
			return validate(value);
		}
		catch ( NumberFormatException nfe ) {
			return false;
		}
	}
	
	public abstract boolean validate(CharSequence value);
	
	public boolean handleValidate(CharSequence value){
		int result = new BigDecimal( value.toString() ).compareTo( defaultValue );
		return result != -1 && result != 0; 
	}
}
