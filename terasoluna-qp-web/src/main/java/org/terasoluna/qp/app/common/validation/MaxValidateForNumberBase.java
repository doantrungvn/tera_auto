package org.terasoluna.qp.app.common.validation;

public abstract class MaxValidateForNumberBase extends MinMaxValidateNumber{

	public boolean isValid(Number value) {
		// null values are valid
		if ( value == null ) {
			return true;
		}
		// handling of NaN, positive infinity and negative infinity
		else if ( value instanceof Double ) {
			if ( (Double) value == Double.NEGATIVE_INFINITY ) {
				return true;
			}
			else if ( Double.isNaN( (Double) value ) || (Double) value == Double.POSITIVE_INFINITY ) {
				return false;
			}
		}
		else if ( value instanceof Float ) {
			if ( (Float) value == Float.NEGATIVE_INFINITY ) {
				return true;
			}
			else if ( Float.isNaN( (Float) value ) || (Float) value == Float.POSITIVE_INFINITY ) {
				return false;
			}
		}
		try{
			return validate(value);
		}catch(NumberFormatException e){
			return false;
		}
	}
	
	abstract boolean validate(Number value);
	
	public boolean handlingValidate(Number value){
		if(value instanceof Byte)
			return ValidateMaxCustom.BYTE.validate(value, defautValue);
		if(value instanceof Short)
			return ValidateMaxCustom.SHORT.validate(value, defautValue);
		if(value instanceof Integer)
			return ValidateMaxCustom.INTEGER.validate(value, defautValue);
		if(value instanceof Float)
			return ValidateMaxCustom.FLOAT.validate(value, defautValue);
		if(value instanceof Long)
			return ValidateMaxCustom.LONG.validate(value, defautValue);
		return ValidateMaxCustom.DOUBLE.validate(value, defautValue);
	}
	
}
