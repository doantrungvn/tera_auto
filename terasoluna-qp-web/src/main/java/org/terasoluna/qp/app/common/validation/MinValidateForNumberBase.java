package org.terasoluna.qp.app.common.validation;

public abstract class MinValidateForNumberBase extends MinMaxValidateNumber{
	
	public boolean isValid(Number value) {
		// null values are valid
		if ( value == null ) {
			return true;
		}

		//handling of NaN, positive infinity and negative infinity
		else if ( value instanceof Double ) {
			if ( (Double) value == Double.POSITIVE_INFINITY ) {
				return true;
			}
			else if ( Double.isNaN( (Double) value ) || (Double) value == Double.NEGATIVE_INFINITY ) {
				return false;
			}
		}
		else if ( value instanceof Float ) {
			if ( (Float) value == Float.POSITIVE_INFINITY ) {
				return true;
			}
			else if ( Float.isNaN( (Float) value ) || (Float) value == Float.NEGATIVE_INFINITY ) {
				return false;
			}
		}
		try{
			return validate(value);
		}catch (NumberFormatException e){
			return false;
		}
	}
	
	public abstract boolean validate(Number value);
	
	public boolean handlingValidate(Number value){
		if(value instanceof Byte)
			return ValidateMinCustom.BYTE.validate(value, defautValue);
		if(value instanceof Short)
			return ValidateMinCustom.SHORT.validate(value, defautValue);
		if(value instanceof Integer)
			return ValidateMinCustom.INTEGER.validate(value, defautValue);
		if(value instanceof Float)
			return ValidateMinCustom.FLOAT.validate(value, defautValue);
		if(value instanceof Long)
			return ValidateMinCustom.LONG.validate(value, defautValue);
		
		return ValidateMinCustom.DOUBLE.validate(value, defautValue);
	}
}
