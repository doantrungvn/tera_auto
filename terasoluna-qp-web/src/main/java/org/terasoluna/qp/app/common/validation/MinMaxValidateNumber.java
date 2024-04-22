package org.terasoluna.qp.app.common.validation;

import org.apache.commons.lang3.StringUtils;

public class MinMaxValidateNumber {
	
	protected String defautValue;
	protected Class<?> dataType;
	
	public void initialize(String value) {
		this.defautValue = value;
	}
	
	public boolean validateEqual(Number value){
		if(value == null)
			return true;
		if(value instanceof Byte)
			return ValidateEqualCustom.BYTE.validate(value, defautValue);
		if(value instanceof Short)
			return ValidateEqualCustom.SHORT.validate(value, defautValue);
		if(value instanceof Integer)
			return ValidateEqualCustom.INTEGER.validate(value, defautValue);
		if(value instanceof Float)
			return ValidateEqualCustom.FLOAT.validate(value, defautValue);
		if(value instanceof Long)
			return ValidateEqualCustom.LONG.validate(value, defautValue);
		return ValidateEqualCustom.DOUBLE.validate(value, defautValue);
	}
	
	public boolean validateNotEqual(Number value) {
		if(value == null)
			return true;
		if(value instanceof Byte)
			return ValidateNotEqualCustom.BYTE.validate(value, defautValue);
		if(value instanceof Short)
			return ValidateNotEqualCustom.SHORT.validate(value, defautValue);
		if(value instanceof Integer)
			return ValidateNotEqualCustom.INTEGER.validate(value, defautValue);
		if(value instanceof Float)
			return ValidateNotEqualCustom.FLOAT.validate(value, defautValue);
		if(value instanceof Long)
			return ValidateNotEqualCustom.LONG.validate(value, defautValue);
		return ValidateNotEqualCustom.DOUBLE.validate(value, defautValue);
	}
	
	public enum ValidateMaxCustom {
		DOUBLE {
			public boolean validate(Number value,String maxValue) {
				Double maxValueD = StringUtils.isBlank(maxValue) ? Double.MAX_VALUE : Double.valueOf(maxValue);
				Double valueD = (Double) value;
				return valueD.compareTo(maxValueD) != 1;
			}
		}, LONG {
			public boolean validate(Number value,String maxValue) {
				Long minValueL = StringUtils.isBlank(maxValue) ? Long.MAX_VALUE : Long.valueOf(maxValue);
				Long valueL = (Long) value;
				return valueL.longValue() < minValueL.longValue();
			}
		}, FLOAT {
			public boolean validate(Number value,String maxValue) {
				Float minValueF = StringUtils.isBlank(maxValue) ? Float.MAX_VALUE : Float.valueOf(maxValue);
				Float valueF = (Float) value;
				return valueF.compareTo(minValueF) != 1;
			}
		}, INTEGER {
			public boolean validate(Number value,String maxValue) {
				Integer minValueI = StringUtils.isBlank(maxValue) ? Integer.MAX_VALUE : Integer.valueOf(maxValue);
				Integer valueI = (Integer) value;
				return valueI.intValue() < minValueI.intValue();
			}
		}, SHORT {
			public boolean validate(Number value, String maxValue) {
				Short minValueS = StringUtils.isBlank(maxValue) ? Short.MAX_VALUE : Short.valueOf(maxValue);
				Short valueS = (Short) value;
				return valueS.shortValue() < minValueS.shortValue();
			}
		},BYTE {
			public boolean validate(Number value, String maxValue) {
				Byte minValueB = StringUtils.isBlank(maxValue) ? Byte.MAX_VALUE : Byte.valueOf(maxValue);
				Byte valueB = (Byte) value;
				return valueB.byteValue() < minValueB.byteValue();
			}
		};
		public abstract boolean validate(Number value, String maxValue);
	}
	
	public enum ValidateMinCustom {
		DOUBLE {
			public boolean validate(Number value,String minValue) {
				Double minValueD = StringUtils.isBlank(minValue) ? Double.MIN_VALUE : Double.valueOf(minValue);
				Double valueD = (Double) value;
				return valueD.compareTo(minValueD) != -1;
			}
		}, LONG {
			public boolean validate(Number value,String minValue) {
				Long minValueL = StringUtils.isBlank(minValue) ? Long.MIN_VALUE : Long.valueOf(minValue);
				Long valueL = (Long) value;
				return valueL.longValue() > minValueL.longValue();
			}
		}, FLOAT {
			public boolean validate(Number value,String minValue) {
				Float minValueF = StringUtils.isBlank(minValue) ? Float.MIN_VALUE : Float.valueOf(minValue);
				Float valueF = (Float) value;
				return valueF.compareTo(minValueF) != -1;
			}
		}, INTEGER {
			public boolean validate(Number value,String minValue) {
				Integer minValueI = StringUtils.isBlank(minValue) ? Integer.MIN_VALUE : Integer.valueOf(minValue);
				Integer valueI = (Integer) value;
				return valueI.intValue() > minValueI.intValue();
			}
		}, SHORT {
			public boolean validate(Number value, String minValue) {
				Short minValueS = StringUtils.isBlank(minValue) ? Short.MIN_VALUE : Short.valueOf(minValue);
				Short valueS = (Short) value;
				return valueS.shortValue() > minValueS.shortValue();
			}
		}, BYTE {
			public boolean validate(Number value, String minValue) {
				Byte minValueB = StringUtils.isBlank(minValue) ? Byte.MIN_VALUE : Byte.valueOf(minValue);
				Byte valueB = (Byte) value;
				return valueB.byteValue() > minValueB.byteValue();
			}
		};
		public abstract boolean validate(Number value, String minValue);
	}
	
	public enum ValidateEqualCustom{
		DOUBLE {
			public boolean validate(Number value,String equalValue) {
				if(StringUtils.isBlank(equalValue))
					return false;
				Double equalValueD = Double.valueOf(equalValue);
				Double valueD = (Double) value;
				return valueD.compareTo(equalValueD) == 0;
			}
		}, LONG {
			public boolean validate(Number value,String equalValue) {
				if(StringUtils.isBlank(equalValue))
					return false;
				Long equalValueL = Long.valueOf(equalValue);
				Long valueL = (Long) value;
				return valueL.compareTo(equalValueL) == 0;
			}
		}, FLOAT {
			public boolean validate(Number value,String equalValue) {
				if(StringUtils.isBlank(equalValue))
					return false;
				Float equalValueF = Float.valueOf(equalValue);
				Float valueF = (Float) value;
				return valueF.compareTo(equalValueF) == 0;
			}
		}, INTEGER {
			public boolean validate(Number value,String equalValue) {
				if(StringUtils.isBlank(equalValue))
					return false;
				Integer equalValueI =  Integer.valueOf(equalValue);
				Integer valueI = (Integer) value;
				return valueI.compareTo(equalValueI) == 0;
			}
		}, SHORT {
			public boolean validate(Number value, String equalValue) {
				if(StringUtils.isBlank(equalValue))
					return false;
				Short equalValueS = Short.valueOf(equalValue);
				Short valueS = (Short) value;
				return valueS.compareTo(equalValueS) == 0;
			}
		}, BYTE {
			public boolean validate(Number value, String equalValue) {
				if(StringUtils.isBlank(equalValue))
					return false;
				Byte equalValueB = Byte.valueOf(equalValue);
				Byte valueB = (Byte) value;
				return valueB.compareTo(equalValueB) == 0;
			}
		};
		public abstract boolean validate(Number value, String equalValue);
	}
	
	public enum ValidateNotEqualCustom{
		DOUBLE {
			public boolean validate(Number value,String notEqualValue) {
				if(StringUtils.isBlank(notEqualValue))
					return false;
				Double notEqualValueD = Double.valueOf(notEqualValue);
				Double valueD = (Double) value;
				return valueD.compareTo(notEqualValueD) != 0;
			}
		}, LONG {
			public boolean validate(Number value,String notEqualValue) {
				if(StringUtils.isBlank(notEqualValue))
					return false;
				Long notEqualValueL = Long.valueOf(notEqualValue);
				Long valueL = (Long) value;
				return valueL.compareTo(notEqualValueL) != 0;
			}
		}, FLOAT {
			public boolean validate(Number value,String notEqualValue) {
				if(StringUtils.isBlank(notEqualValue))
					return false;
				Float notEqualValueF = Float.valueOf(notEqualValue);
				Float valueF = (Float) value;
				return valueF.compareTo(notEqualValueF) != 0;
			}
		}, INTEGER {
			public boolean validate(Number value,String notEqualValue) {
				if(StringUtils.isBlank(notEqualValue))
					return false;
				Integer notEqualValueI =  Integer.valueOf(notEqualValue);
				Integer valueI = (Integer) value;
				return valueI.compareTo(notEqualValueI) != 0;
			}
		}, SHORT {
			public boolean validate(Number value, String notEqualValue) {
				if(StringUtils.isBlank(notEqualValue))
					return false;
				Short notEqualValueS = Short.valueOf(notEqualValue);
				Short valueS = (Short) value;
				return valueS.compareTo(notEqualValueS) != 0;
			}
		}, BYTE {
			public boolean validate(Number value, String notEqualValue) {
				if(StringUtils.isBlank(notEqualValue))
					return false;
				Byte notEqualValueB = Byte.valueOf(notEqualValue);
				Byte valueB = (Byte) value;
				return valueB.compareTo(notEqualValueB) != 0;
			}
		};
		public abstract boolean validate(Number value, String notEqualValue);
	}
}
