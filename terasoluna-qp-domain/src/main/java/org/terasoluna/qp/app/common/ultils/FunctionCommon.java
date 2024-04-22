package org.terasoluna.qp.app.common.ultils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.terasoluna.gfw.common.codelist.CodeList;
import org.terasoluna.gfw.common.codelist.ReloadableCodeList;
import org.terasoluna.gfw.common.codelist.SimpleMapCodeList;

public class FunctionCommon {

	final static String REGEX_REMOVE_MULTI_SPACE = "\\s{2,}";
	final static private Character CHAR_DELIMITERS = '_';
	final static private String STR_DELIMITERS = "_";
	final static private Character CHAR_SPACE = ' ';
	
	
	public static Timestamp getCurrentTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static String getStringJson(String string) {

		if (string.indexOf("{") != 0) {
			string = "{" + string;
		}

		if (string.lastIndexOf("}") != string.length() - 1) {
			string = string + "}";
		}

		return string;
	}

	/**
	 * check null or "" return true
	 * 
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
	 * if object is null or = 0 then true
	 * 
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
	 * 
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
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isNotEmpty(Object object) {
		return !isEmpty(object);
	}

	/**
	 * 
	 * @param objOne
	 * @param objTwo
	 * @return
	 */
	public static boolean equals(Object objOne, Object objTwo) {
		/*if (objOne == objTwo)
			return true;
		if (objOne == null || objTwo == null)
			return false;

		if (objOne.getClass() != objTwo.getClass())
			return false;

		return objOne.equals(objTwo);*/
		
		return DataTypeUtils.equals(objOne, objTwo);
	}

	/**
	 * 
	 * @param objOne
	 * @param objTwo
	 * @return
	 */
	public static boolean notEquals(Object objOne, Object objTwo) {
		return !equals(objOne, objTwo);
	}

	/**
	 * 
	 * @param name
	 * @return String with Code's format
	 */
	public static String convertNameToCode(String name) {
		if (StringUtils.isBlank(name))
			return name;

		return StringUtils.uncapitalize(StringUtils.remove(WordUtils.capitalizeFully(name, CHAR_SPACE), StringUtils.SPACE));
	}

	/**
	 * 
	 * @param name
	 * @return String with database Code's format
	 */
	public static String convertNameToCodeDb(String name) {
		if (StringUtils.isBlank(name))
			return name;

		return StringUtils.lowerCase(StringUtils.replace(WordUtils.capitalizeFully(name, CHAR_SPACE), StringUtils.SPACE, STR_DELIMITERS));
	}
	
	public static String convertCodeToNameDb(String code) {
		if (StringUtils.isBlank(code))
			return code;

		return StringUtils.capitalize(StringUtils.replace(code, STR_DELIMITERS, StringUtils.SPACE));
	}

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
	
	
	public static boolean checkExists(Object[] arr, Object targetValue) {
		if (null == arr || arr.length == 0)
			return false;
		int a = Arrays.binarySearch(arr, targetValue);
		if (a >= 0)
			return true;
		else
			return false;
	}

	public static String removeMultiSpaces(String input) {
		if (StringUtils.isNoneEmpty(input)) {
			return StringUtils.replacePattern(input, REGEX_REMOVE_MULTI_SPACE, " ").trim();
		}

		return StringUtils.trim(input);
	}

	
	public static SimpleMapCodeList getSimpleMapCodeList(String codelist) {
		SimpleMapCodeList loaderBlogic = null;
		if (ApplicationContextProvider.getApplicationContext().getBean(codelist) != null) {
			loaderBlogic = (SimpleMapCodeList) ApplicationContextProvider.getApplicationContext().getBean(codelist);
		}
		return loaderBlogic;
	}
	
	public static ReloadableCodeList getReloadableCodeList(String codelist) {
		ReloadableCodeList loaderBlogic = null;
		if (ApplicationContextProvider.getApplicationContext().getBean(codelist) != null) {
			loaderBlogic = (ReloadableCodeList) ApplicationContextProvider.getApplicationContext().getBean(codelist);
		}
		return loaderBlogic;
	}
	
	public static Object getKeyFromValueOfMap(Map<?, ?> map, Object value) {
		for (Object o : map.keySet()) {
			if (DataTypeUtils.equalsIgnoreCase(map.get(o), value)) {
				return o;
			}
		}
		return null;
	}
	
	public static Object getValueFromKeyOfMap(Map<?, ?> map, Object key) {
		for (Object o : map.keySet()) {
			if (DataTypeUtils.equalsIgnoreCase(o, key)) {
				return map.get(o);
			}
		}
		return null;
	}
	
	public static String getOauth2Username(){
		String username = "username";	
		return username;
	}
	
	public static String getOauth2Password(){
		String username = "password";
		return username;
	}
	
	
	public static String getCodeListLabelFromValue(String codelistName, String value) {
		CodeList codeList = null;
	
		if (ApplicationContextProvider.getApplicationContext().getBean(codelistName) != null) {
			codeList = (CodeList) ApplicationContextProvider.getApplicationContext().getBean(codelistName);
			return String.valueOf(getValueFromKeyOfMap(codeList.asMap(), value));
		}
		return null;
	}
	
	public static String getCodeListValueFromLabel(String codelistName, String label) {
		CodeList codeList = null;

		if (ApplicationContextProvider.getApplicationContext().getBean(codelistName) != null) {
			codeList = (CodeList) ApplicationContextProvider.getApplicationContext().getBean(codelistName);
			return String.valueOf(getKeyFromValueOfMap(codeList.asMap(), label));
		}
		return null;
	}
	
	/*public static String toCamelCaseKeepUpperCaseForOutputBean (String strInput) {
		String[] arrStr = strInput.split("");
		for(int i = 0; i < arrStr.length; i++) {
			if(i == 0) {
				String[] arrCha = arrStr[i].split("");
				for(int j = 0; j < arrCha.length; j++) {
					String newStr = "";
					if(j == 0) {
						String lowerCase = arrCha[j].toLowerCase();
						arrCha[j] = lowerCase;
						newStr = arrCha.toString();
						arrStr[i] = newStr;
					}
				}
			} else{
				String[] arrCha = arrStr[i].split("");
				for(int j = 0; j < arrCha.length; j++) {
					String newStr = "";
					if(j == 0) {
						String upperCase = arrCha[j].toUpperCase();
						arrCha[j] = upperCase;
						newStr = arrCha.toString();
						arrStr[i] = newStr;
					}
				}
			}
		}
		return StringUtils.uncapitalize(arrStr.toString());
	}*/
	/*
	 * return true if a = b
	 */
	public static boolean compare(Integer a,Boolean b) {
		Boolean c = false;
		if(1 == a){
			c = true;
		}
		return FunctionCommon.equals(b, c);
    }
}
