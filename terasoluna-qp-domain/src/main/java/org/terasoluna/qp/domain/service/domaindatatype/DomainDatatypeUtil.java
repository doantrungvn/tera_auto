package org.terasoluna.qp.domain.service.domaindatatype;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

public class DomainDatatypeUtil {

	final static private Character CHAR_DELIMITERS = '_';
	final public static String STR_REGEX = ",";

	/**
	 * Convert physical name to model name
	 * 
	 * @param strInput
	 * @return string with upper case first char in the words and replace all '_' by ''
	 * 
	 */
	public static String toCamelCase(String strInput) {
		if (strInput == null || strInput.isEmpty())
			return strInput;

		String strReturn = StringUtils.remove(
				WordUtils.capitalizeFully(strInput, CHAR_DELIMITERS),
				CHAR_DELIMITERS.toString());

		return StringUtils.uncapitalize(strReturn);

	}
	
	/**
	 * if object is null or = 0 then true
	 * @param object
	 * @return
	 */
	public static boolean isEmpty(Long object) {
		if (object == null || object.equals(Long.valueOf(0))) {
			return true;
		}
		return false;
	}
	/**
	 * if object is null or = 0 then true
	 * @param object
	 * @return
	 */
	public static boolean isEmpty(Integer object) {
		if (object == null || object.equals(Integer.parseInt("0"))) {
			return true;
		}
		return false;
	}
	
	/**
	 * check null or "" return true
	 * @param object
	 * @return
	 */
	public static boolean isEmpty(Object object) {
		if (object == null) {
			return true;
		} else {
			try {
				Method isEmpty = object.getClass().getDeclaredMethod("isEmpty", (Class<?>[]) null);
				if (isEmpty != null) {
					Object result = isEmpty.invoke(object, (Object[]) null);
					if (result instanceof Boolean) {
						return (Boolean) result;
					}
				}
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
	}

	/**
	 * convert String to List<String> by regex
	 * @author dungnn1
	 */
	public static List<String> convertStringToArrayList(String strInput, String regex) {
		List<String> returnList = new ArrayList<String>();

		if (isEmpty(strInput)) {
			return returnList;
		}

		String[] arrStr = strInput.split(regex);
		for (String s : arrStr) {
			returnList.add(s.trim());
		}

		return returnList;

	}
	
	/**
	 * validate duplicated in the list
	 * @param strCheck
	 * @param listData
	 * @return
	 */
	public static boolean checkExistInList(String strCheck, List<String> listData) {
		if ( DomainDatatypeUtil.isEmpty(listData ) ||  DomainDatatypeUtil.isEmpty(strCheck)) {
			return false;
		}

		for (String strTemp : listData) {
			if (strCheck.equalsIgnoreCase(strTemp)) {
				return true;
			}
		}
		return false;
	}
	

	/**
	 * validate range of numeric value : Integer, BigInteger, SmallInt , Serial, BigSerial
	 * @param basetypeId
	 * @param inputValue
	 * @return List with element at 0 : min value - 1: max value
	 * 
	 */
//	public static List<String> checkValidValueOfBaseType(Integer basetypeId, String strInput) {
//		if (isEmpty(basetypeId) || isEmpty(strInput)) {
//			return null;
//		}
//		
//		/*Integer baseTypeValue = Integer.parseInt(basetypeId);*/
//		// if base type is : Integer
//		if (DomainDatatypeConst.PhysicalDataTypeDetail.INTEGER.equals(basetypeId)) {
//			List<String> listReturn = new ArrayList<String>();
//			listReturn.add(String.valueOf(Integer.MIN_VALUE));
//			listReturn.add(String.valueOf(Integer.MAX_VALUE));
//
//			Integer inputValue;
//			try {
//				inputValue = Integer.parseInt(strInput);
//			} catch (NumberFormatException ne) {
//				return listReturn;
//			}
//			if( inputValue.intValue() < Integer.MIN_VALUE || Integer.MAX_VALUE < inputValue.intValue()) {
//				return listReturn;
//			}
//		// if base type is : Smallint
//		} else if (DomainDatatypeConst.PhysicalDataTypeDetail.SMALLINT.equals(basetypeId)) {
//			List<String> listReturn = new ArrayList<String>();
//			listReturn.add(String.valueOf(Short.MIN_VALUE));
//			listReturn.add(String.valueOf(Short.MAX_VALUE));
//			
//			Short inputValue;
//			try {
//				inputValue = Short.parseShort(strInput);
//			} catch (NumberFormatException ne) {
//				return listReturn;
//			}
//			if (inputValue.shortValue() < Short.MIN_VALUE || Short.MAX_VALUE < inputValue.shortValue()) {
//				return listReturn;
//			}
//		}
//		// if base type is : Bigint
//		else if (DomainDatatypeConst.PhysicalDataTypeDetail.BIGINT.equals(basetypeId)) {
//			List<String> listReturn = new ArrayList<String>();
//			listReturn.add(String.valueOf(Long.MIN_VALUE));
//			listReturn.add(String.valueOf(Long.MAX_VALUE));
//			Long inputValue;
//			try {
//				inputValue = Long.parseLong(strInput);
//			} catch (NumberFormatException ne) {
//				return listReturn;
//			}
//			if(inputValue.longValue() < Long.MIN_VALUE || Long.MAX_VALUE < inputValue.longValue()) {
//				return listReturn;
//			}
//		}
//		// if base type is : Serial
//		else if (DomainDatatypeConst.PhysicalDataTypeDetail.SERIAL.equals(basetypeId)) {
//			List<String> listReturn = new ArrayList<String>();
//			listReturn.add(String.valueOf(DomainDatatypeConst.NumericSize.SERIAL_MIN));
//			listReturn.add(String.valueOf(Integer.MAX_VALUE));
//			
//			Integer inputValue;
//			try {
//				inputValue = Integer.parseInt(strInput);
//			} catch (NumberFormatException ne) {
//				return listReturn;
//			}
//			if(inputValue.intValue() < DomainDatatypeConst.NumericSize.SERIAL_MIN || Integer.MAX_VALUE < inputValue.intValue() ) {
//				return listReturn;
//			}
//		}
//		// if base type is : BigSerial
//		else if (DomainDatatypeConst.PhysicalDataTypeDetail.BIGSERIAL.equals(basetypeId)) {
//			List<String> listReturn = new ArrayList<String>();
//			listReturn.add(String.valueOf(DomainDatatypeConst.NumericSize.BIGSERIAL_MIN));
//			listReturn.add(String.valueOf(Long.MAX_VALUE));
//			Long inputValue;
//			try {
//				inputValue = Long.parseLong(strInput);
//			} catch (NumberFormatException ne) {
//				return listReturn;
//			}
//			if(inputValue.longValue() < DomainDatatypeConst.NumericSize.BIGSERIAL_MIN || Long.MAX_VALUE < inputValue.longValue()) {
//				return listReturn;
//			}
//		}
//		//if base type is Byte
//		else if (DomainDatatypeConst.PhysicalDataTypeDetail.BYTE.equals(basetypeId)) {
//			List<String> listReturn = new ArrayList<String>();
//			listReturn.add(String.valueOf(Byte.MIN_VALUE));
//			listReturn.add(String.valueOf(Byte.MAX_VALUE));
//			Byte inputValue;
//			try {
//				inputValue = Byte.parseByte(strInput);
//			} catch (NumberFormatException ne) {
//				return listReturn;
//			}
//			if(inputValue.byteValue() < Byte.MIN_VALUE || Byte.MAX_VALUE < inputValue.byteValue()) {
//				return listReturn;
//			}
//		}
//		
//		//if base type is Float
//		else if (DomainDatatypeConst.PhysicalDataTypeDetail.FLOAT.equals(basetypeId)) {
//			List<String> listReturn = new ArrayList<String>();
//			listReturn.add(String.valueOf(Float.MIN_VALUE));
//			listReturn.add(String.valueOf(Float.MAX_VALUE));
//			Float inputValue;
//			try {
//				inputValue = Float.parseFloat(strInput);
//			} catch (NumberFormatException ne) {
//				return listReturn;
//			}
//			if(Float.compare(Float.MIN_VALUE, inputValue.floatValue()) > 0 || Float.compare(inputValue.floatValue(), Float.MAX_VALUE) > 0) {
//				return listReturn;
//			}
//		}
//		
//		//if base type is Float
//		else if (DomainDatatypeConst.PhysicalDataTypeDetail.DOUBLE.equals(basetypeId)) {
//			List<String> listReturn = new ArrayList<String>();
//			listReturn.add(String.valueOf(Double.MIN_VALUE));
//			listReturn.add(String.valueOf(Double.MAX_VALUE));
//			Double inputValue;
//			try {
//				inputValue = Double.parseDouble(strInput);
//			} catch (NumberFormatException ne) {
//				return listReturn;
//			}
//			if(Double.compare(Double.MIN_VALUE, inputValue.floatValue()) > 0 || Double.compare(inputValue.floatValue(), Double.MAX_VALUE) > 0) {
//				return listReturn;
//			}
//		}
//		
//		return null;
//	}

}
