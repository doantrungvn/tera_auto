package org.terasoluna.qp.app.common.ultils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.terasoluna.gfw.common.codelist.CodeList;



public class FunctionMasterUtils {

	private static final String SEPERATE_CHARACTER = ";";

	public static class QpDouble {
		public static Double toDouble(Object objectToDoubleInput) {
			return Double.parseDouble((String) objectToDoubleInput);
		}

		public static Double toDouble(BigDecimal bigDecimalToDoubleInput) {
			return bigDecimalToDoubleInput.doubleValue();
		}
	}

	public static class QpObject {
		public static Boolean equalValue(Object equalValueInputObject1, Object equalValueInputObject2) {
			return equalValueInputObject1.equals(equalValueInputObject2);
		}

		public static Integer compareTo(Object compareToInputObject1, Object compareToInputObject2) {
			if ((compareToInputObject1 == null) && (compareToInputObject2 == null)) {
				return 0;
			}
			if (compareToInputObject1 == null) {
				return -1;
			}
			if (compareToInputObject2 == null) {
				return 1;
			}

			if (((compareToInputObject1 instanceof String)) && ((compareToInputObject2 instanceof String)))
				return ((String) compareToInputObject1).compareTo((String) compareToInputObject2);
			if (((compareToInputObject1 instanceof Integer)) && ((compareToInputObject2 instanceof Integer)))
				return ((Integer) compareToInputObject1).compareTo((Integer) compareToInputObject2);
			if (((compareToInputObject1 instanceof Long)) && ((compareToInputObject2 instanceof Long)))
				return ((Long) compareToInputObject1).compareTo((Long) compareToInputObject2);
			if (((compareToInputObject1 instanceof Double)) && ((compareToInputObject2 instanceof Double)))
				return ((Double) compareToInputObject1).compareTo((Double) compareToInputObject2);
			if (((compareToInputObject1 instanceof Date)) && ((compareToInputObject2 instanceof Date)))
				return ((Date) compareToInputObject1).compareTo((Date) compareToInputObject2);
			if (((compareToInputObject1 instanceof Time)) && ((compareToInputObject2 instanceof Time)))
				return ((Time) compareToInputObject1).compareTo((Time) compareToInputObject2);
			if (((compareToInputObject1 instanceof Timestamp)) && ((compareToInputObject2 instanceof Timestamp)))
				return ((Timestamp) compareToInputObject1).compareTo((Timestamp) compareToInputObject2);
			if (((compareToInputObject1 instanceof BigDecimal)) && ((compareToInputObject2 instanceof BigDecimal)))
				return ((BigDecimal) compareToInputObject1).compareTo((BigDecimal) compareToInputObject2);
			if (((compareToInputObject1 instanceof Boolean)) && ((compareToInputObject2 instanceof Boolean))) {
				return ((Boolean) compareToInputObject1).compareTo((Boolean) compareToInputObject2);
			}
			return null;
		}

		public static Boolean compareValue(Object compareValueInputObject1, Object compareValueInputObject2) {
			boolean result= false;
			if(compareValueInputObject1==null && compareValueInputObject2==null){
				result = true;
			} else if(compareValueInputObject1==null || compareValueInputObject2==null){
				result = false;
			} else {
				if(compareValueInputObject1.getClass()==compareValueInputObject2.getClass()){
					result = compareValueInputObject1.equals(compareValueInputObject2);
				} else {
					result = compareValueInputObject1.toString().equals(compareValueInputObject2.toString());
				}
			}
			return result;
		}

		public static Object getInstance(String getInstanceInput) {
			Object result = null;
			try {
				result = Class.forName(getInstanceInput).newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				if (result != null) {
					return result;
				}
				if (getInstanceInput.equals("String"))
					return "";
				if (getInstanceInput.equals("Integer"))
					return new Integer(0);
				if (getInstanceInput.equals("Long"))
					return new Long(0L);
				if (getInstanceInput.equals("Double"))
					return new Double(0.0d);
				if (getInstanceInput.equals("Float"))
					return new Float(0.0f);
				if (getInstanceInput.equals("Date"))
					return QpDate.getCurrentDate();
				if (getInstanceInput.equals("Time"))
					return QpDate.getTime();
				if (getInstanceInput.equals("Timestamp"))
					return QpDate.getCurrentDateTime();
				if (getInstanceInput.equals("BigDecimal")) {
					return new BigDecimal(0);
				}
			}
			return null;
		}

		public static List<Object> getInstance(String getInstanceInputObject, Integer getInstanceInputSize) {
			List<Object> result = null;
			try {
				Object tmp = Class.forName(getInstanceInputObject).newInstance();
				result = new ArrayList<Object>();
				for (int iCnt = 0; iCnt < getInstanceInputSize; iCnt++) {
					result.add(tmp);
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {

				if (result != null) {
					return result;
				}

				if (getInstanceInputObject.equals("String")) {
					List<Object> res = new ArrayList<>();
					for (int iCnt = 0; iCnt < getInstanceInputSize; iCnt++) {
						res.add("");
					}
					return res;
				}
				if (getInstanceInputObject.equals("Integer")) {
					List<Object> res = new ArrayList<>();
					for (int iCnt = 0; iCnt < getInstanceInputSize; iCnt++) {
						res.add(new Integer(0));
					}
					return res;
				}
				if (getInstanceInputObject.equals("Long")) {
					List<Object> res = new ArrayList<>();
					for (int iCnt = 0; iCnt < getInstanceInputSize; iCnt++) {
						res.add(new Long(0L));
					}
					return res;
				}
				if (getInstanceInputObject.equals("Double")) {
					List<Object> res = new ArrayList<>();
					for (int iCnt = 0; iCnt < getInstanceInputSize; iCnt++) {
						res.add(new Double(0.0d));
					}
					return res;
				}
				if (getInstanceInputObject.equals("Float")) {
					List<Object> res = new ArrayList<>();
					for (int iCnt = 0; iCnt < getInstanceInputSize; iCnt++) {
						res.add(new Float(0.0f));
					}
					return res;
				}
				if (getInstanceInputObject.equals("Date")) {
					List<Object> res = new ArrayList<>();
					for (int iCnt = 0; iCnt < getInstanceInputSize; iCnt++) {
						res.add(QpDate.getCurrentDate());
					}
					return res;
				}
				if (getInstanceInputObject.equals("Time")) {
					List<Object> res = new ArrayList<>();
					for (int iCnt = 0; iCnt < getInstanceInputSize; iCnt++) {
						res.add(QpDate.getTime());
					}
					return res;
				}
				if (getInstanceInputObject.equals("Timestamp")) {
					List<Object> res = new ArrayList<>();
					for (int iCnt = 0; iCnt < getInstanceInputSize; iCnt++) {
						res.add(QpDate.getCurrentDateTime());
					}
					return res;
				}
				if (getInstanceInputObject.equals("BigDecimal")) {
					List<Object> res = new ArrayList<>();
					for (int iCnt = 0; iCnt < getInstanceInputSize; iCnt++) {
						res.add(new BigDecimal(0));
					}
					return res;
				}

			}

			return null;
		}
	}

	public static class QpString {
		public static Boolean isBlankOrNull(String isBlankOrNullInput) {
			return GenericValidator.isBlankOrNull(isBlankOrNullInput);
		}

		public static Boolean isEmpty(String isEmptyInput) {
			return isEmptyInput.isEmpty();
		}

		public static Boolean isNotEmpty(String isNotEmptyInput) {
			return isNotEmptyInput != null && !isNotEmptyInput.isEmpty();
		}

		public static Boolean isBlank(String isBlankInput) {
			return StringUtils.isBlank(isBlankInput);
		}

		public static Boolean isNotBlank(String isNotBlankInput) {
			return StringUtils.isNotBlank(isNotBlankInput);
		}

		public static String meminsert(String meminsertInputString1, Integer meminsertInputIndex, Integer meminsertInputLength, String meminsertInputString2) {
			if (meminsertInputString1 == null)
				meminsertInputString1 = "";
			if (meminsertInputString2 == null)
				meminsertInputString2 = "";
			if (meminsertInputIndex < 0)
				return null;
			if ((meminsertInputString2.length() > meminsertInputLength) || (meminsertInputLength < 1)) {
				return null;
			}
			StringBuilder sb = new StringBuilder(meminsertInputString1);

			if (meminsertInputString1.length() <= meminsertInputIndex) {
				StringBuilder sb1 = new StringBuilder(meminsertInputString2);

				for (int i = 0; i < meminsertInputIndex - meminsertInputString1.length(); i++)
					sb1.insert(i, " ");
				sb.append(sb1);
			} else {
				sb.delete(meminsertInputIndex, meminsertInputIndex + meminsertInputLength);
				sb.insert(meminsertInputIndex, meminsertInputString2);
			}
			for (int i = 0; i < meminsertInputLength - meminsertInputString2.length(); i++) {
				sb.insert(meminsertInputIndex + meminsertInputString2.length() + i, " ");
			}
			return sb.toString();
		}

		public static String cat(String strCatInputString1, String strCatInputString2) {
			return strCatInputString1 + strCatInputString2;
		}

		public static String cat(String strCatInputString1, String[] strCatInputArrayString) {
			StringBuilder strBuilder = new StringBuilder();
			for (int i = 0; i < strCatInputArrayString.length; i++) {
				strBuilder.append(strCatInputArrayString[i]);
			}
			return strCatInputString1 + strBuilder.toString();
		}

		public static Integer strCompare(String strcCompareInputString1, String strcCompareInputString2) {
			return strcCompareInputString1.compareTo(strcCompareInputString2);
		}

		public static String toStr(Object toStringInput) {
			return toStringInput.toString();
		}

		public static String toStr(BigDecimal toStringInput) {
			return toStringInput.toString();
		}

		public static Integer strLength(String srtLengthInput) {
			return srtLengthInput == null ? 0 : srtLengthInput.length();
		}

		public static String subStr(String subStringInputString, Integer subStringInputIndex, Integer subStringInputLength) {
			return subStringInputString == null ? null : subStringInputString.substring(subStringInputIndex, subStringInputLength);
		}

		public static String leftSubstring(String leftSubStringInputString, Integer leftSubStringInputLength) {
			return leftSubStringInputString == null ? null : leftSubStringInputString.substring(0, leftSubStringInputLength);
		}

		public static String rightSubstring(String rightSubStringInputString, Integer rightSubStringInputLength) {
			return rightSubStringInputString == null ? null : rightSubStringInputString.substring(rightSubStringInputString.length() - rightSubStringInputLength);
		}

		public static String decimalFormat(Integer decimalFormatIntInputInt1, Integer decimalFormatIntInputInt2) {
			if (decimalFormatIntInputInt2 < 1) {
				return null;
			}
			if (decimalFormatIntInputInt1 < 0) {
				return null;
			}

			if (String.valueOf(decimalFormatIntInputInt1).length() > decimalFormatIntInputInt2) {
				return null;
			}
			char[] decimalFormatIntInputInt1Zero = new char[decimalFormatIntInputInt2];
			for (int i = 0; i < decimalFormatIntInputInt2; i++)
				decimalFormatIntInputInt1Zero[i] = '0';
			char[] decimalFormatIntInputInt1Ary = Integer.toString(decimalFormatIntInputInt1).toCharArray();
			for (int i = 0; i < decimalFormatIntInputInt1Ary.length; i++)
				decimalFormatIntInputInt1Zero[(decimalFormatIntInputInt2 - i - 1)] = decimalFormatIntInputInt1Ary[(decimalFormatIntInputInt1Ary.length - i - 1)];
			return String.valueOf(decimalFormatIntInputInt1Zero);
		}

		public static String decimalFormat(String decimalFormatStringInputString, Integer decimalFormatStringInputInt) {
			if (decimalFormatStringInputString == null) {
				return null;
			}

			if (decimalFormatStringInputInt < 1) {
				return null;
			}
			Pattern valuePattern = Pattern.compile("^-?([0-9]\\d*)$");
			Matcher valueMatcher = valuePattern.matcher(decimalFormatStringInputString);
			if (!valueMatcher.matches()) {
				return null;
			}

			if (decimalFormatStringInputString.charAt(0) == '-') {
				return null;
			}

			if (decimalFormatStringInputString.length() > decimalFormatStringInputInt) {
				return null;
			}
			char[] decimalFormatStringInputStringZero = new char[decimalFormatStringInputInt];
			for (int i = 0; i < decimalFormatStringInputInt; i++)
				decimalFormatStringInputStringZero[i] = '0';
			char[] decimalFormatStringInputStringAry = decimalFormatStringInputString.toCharArray();
			for (int i = 0; i < decimalFormatStringInputStringAry.length; i++)
				decimalFormatStringInputStringZero[(decimalFormatStringInputInt - i - 1)] = decimalFormatStringInputStringAry[(decimalFormatStringInputStringAry.length - i - 1)];
			return String.valueOf(decimalFormatStringInputStringZero);
		}

		public static String[] toArray(String toArrayInputString, String toArrayInputPattern) {

			if (toArrayInputString == null)
				toArrayInputString = "";

			return toArrayInputString.split(toArrayInputPattern);
		}

		public static String trim(String trimString) {
			return StringUtils.trim(trimString);
		}

		public static List<String> toArrayList(String toArrayListInputString, String toArrayListInputPattern) {
			if (toArrayListInputString == null)
				toArrayListInputString = "";

			return Arrays.asList(toArrayListInputString.split(toArrayListInputPattern));
		}
		
		
		public static boolean intersect(String strOne, String strTwo, String regex) {
			if (StringUtils.isBlank(strOne) && StringUtils.isBlank(strTwo)) {
				return false;
			}

			if ((StringUtils.isBlank(strOne) && StringUtils.isNoneBlank(strTwo)) 
					|| (StringUtils.isNoneBlank(strOne) && StringUtils.isBlank(strTwo))) {
				return false;
			}

			List<String> arrOne = FunctionMasterUtils.QpString.toArrayList(strOne, regex);
			List<String> arrTwo = FunctionMasterUtils.QpString.toArrayList(strTwo, regex);

			return CollectionUtils.isNotEmpty(CollectionUtils.intersection(arrOne, arrTwo));
		}
		
	}

	public static class QpInteger {
		public static Integer toInteger(Object objectToIntegerInput) {
			return Integer.parseInt((String) objectToIntegerInput);
		}

		public static Integer toInteger(Double doubleToIntegerInput) {
			return doubleToIntegerInput.intValue();
		}

		public static Integer toInteger(BigDecimal bigDecimalToIntegerInput) {
			return bigDecimalToIntegerInput.intValue();
		}

		public static Integer toInteger(Float floatToIntegerInput) {
			return floatToIntegerInput.intValue();
		}

		public static Integer toInteger(Long longToIntegerInput) {
			return longToIntegerInput.intValue();
		}
	}

	public static class QpLong {
		public static Long toLong(Object objectToLongInput) {
			return Long.parseLong((String) objectToLongInput);
		}

		public static Long toLong(Double doubleToLongInput) {
			return doubleToLongInput.longValue();
		}

		public static Long toLong(BigDecimal bigDecimalToLongInput) {
			return bigDecimalToLongInput.longValue();
		}

		public static Long toLong(Float floatToLongInput) {
			return floatToLongInput.longValue();
		}

		public static Long toLong(Integer integerToLongInput) {
			return integerToLongInput.longValue();
		}
	}

	public static class QpArray {
		public static Integer length(Object[] lengthInput) {
			return lengthInput.length;
		}

		public static Object get(Object[] getInputArr, Integer getInputIndex) {
			return getInputArr[getInputIndex];
		}

		public static Boolean set(Object[] inputSetArr, Integer inputSetIndex, Object inputSetObject) {
			if (inputSetArr.length <= inputSetIndex) {
				return false;
			} else {
				inputSetArr[inputSetIndex] = inputSetObject;
				return true;
			}
		}

		public static String toString(String[] toStringInput) {
			if (toStringInput == null) {
				return "";
			}
			StringBuilder strBuilder = new StringBuilder();
			for (int i = 0; i < toStringInput.length; i++) {
				strBuilder.append(FunctionMasterUtils.SEPERATE_CHARACTER);
				strBuilder.append(toStringInput[i]);
			}
			strBuilder.replace(0, 1, "");
			return strBuilder.toString();
		}

		public static String toString(List<?> toStringInput) {
			if (toStringInput == null) {
				return "";
			}
			StringBuilder strBuilder = new StringBuilder();
			for (Object item : toStringInput) {
				strBuilder.append(FunctionMasterUtils.SEPERATE_CHARACTER);
				strBuilder.append(item.toString());
			}
			strBuilder.replace(0, 1, "");
			return strBuilder.toString();
		}

	}

	public static class QpBoolean {
		public static Boolean toBoolean(Object objectToBooleanInput) {
			return ((Boolean) objectToBooleanInput).booleanValue();
		}
	}

	public static class QpBigDecimal {
		public static BigDecimal toDecimal(Object objectToBigDecimalInput) {
			return ((BigDecimal) objectToBigDecimalInput);
		}

		public static BigDecimal roundHalfDown(Integer roundHalfDownInputNewScale, BigDecimal roundHalfDownInputBigDecimal) {
			return roundHalfDownInputBigDecimal.setScale(roundHalfDownInputNewScale, BigDecimal.ROUND_HALF_DOWN);
		}
	}

	public static class QpFile {
		public static void fileCopy(String fileCopyInputSrc, String fileCopyInputDes) throws IOException {
			FileUtilsQP.copyFile(new File(fileCopyInputSrc), new File(fileCopyInputDes));
		}

		public static void fileRename(String fileRenameInputBefore, String fileRenameInputAfter) throws IOException {
			File from = new File(fileRenameInputBefore);
			File to = new File(fileRenameInputAfter);
			if (to.exists())
				throw new java.io.IOException("file exists");
			from.renameTo(to);
		}

		public static void fileRemove(String fileRemoveInput) {
			new File(fileRemoveInput).delete();
		}

		// incomplete
		public static Long getFileSize(String getFileSizeInput) throws IOException {
			File file = new File(getFileSizeInput);
			if (file.exists()) {
				Long bytes = file.length();
				// Long kilobytes = (bytes / 1024);
				// Long megabytes = (kilobytes / 1024);
				// Long gigabytes = (megabytes / 1024);
				// Long terabytes = (gigabytes / 1024);
				// Long petabytes = (terabytes / 1024);
				// Long exabytes = (petabytes / 1024);
				// Long zettabytes = (exabytes / 1024);
				// Long yottabytes = (zettabytes / 1024);
				return bytes / (1024 * 1024);
			} else {
				throw new java.io.IOException("File doesn't exist error");
			}
		}

		public static Boolean fileExist(String fileExistInput) {
			return (new File(fileExistInput)).exists();
		}

		public static void directoryMake(String directoryMakeInput) throws IOException {
			File files = new File(directoryMakeInput);
			if (files.exists()) {
				if (!files.mkdirs()) {
					throw new java.io.IOException("Failed to create directory!");
				}
			}
		}

		public static void directoryRemove(String directoryRemoveInput) throws IOException {
			FileUtils.deleteDirectory(new File(directoryRemoveInput));
		}
	}

	public static class QpFloat {
		public static Float toFloat(Object objectToFloatInput) {
			return (Float) objectToFloatInput;
		}
	}

	public static class QpList {
		public static <T> Boolean add(List<T> addInputList, T addInputObject) {
			if (addInputList == null) {
				return false;
			} else {
				return addInputList.add(addInputObject);
			}
		}

		public static <T> Boolean add(List<T> addInputList, Integer addInputIndex, T addInputObject) {
			if (addInputList == null) {
				return false;
			} else {
				try {
					addInputList.add(addInputIndex, addInputObject);
					return true;
				} catch (Exception ex) {
					return false;
				}

			}
		}

		public static <T> void clear(List<T> clearInputList) {
			clearInputList.clear();
		}

		public static <T> Boolean contains(List<T> containsInputList, T containsInputObject) {
			return containsInputList == null ? false : containsInputList.contains(containsInputList);
		}

		public static <T> T get(List<T> getInputList, Integer getInputIndex) {
			if (getInputList == null) {
				return null;
			} else {
				try {
					return getInputList.get(getInputIndex);
				} catch (Exception ex) {
					return null;
				}
			}
		}

		public static <T> Integer indexOf(List<T> indexOfInputList, T indexOfInputObject) {
			return indexOfInputList.indexOf(indexOfInputObject);
		}

		public static <T> Boolean isEmpty(List<T> isEmptyInputList) {
			return isEmptyInputList.isEmpty();
		}

		public static <T> Integer lastIndexOf(List<T> lastIndexOfInputList, T lastIndexOfInputObject) {
			return lastIndexOfInputList.lastIndexOf(lastIndexOfInputObject);
		}

		public static <T> Boolean remove(List<T> removeInputList, Integer removeInputIndex) {
			return removeInputList.remove(removeInputIndex);
		}

		public static <T> Boolean set(List<T> setInputList, Integer setInputIndex, T setInputObject) {
			if (setInputList == null) {
				return false;
			} else {
				try {
					setInputList.set(setInputIndex, setInputObject);
					return true;
				} catch (Exception ex) {
					return false;
				}
			}
		}

		public static <T> Integer size(List<T> sizeInputList) {
			return sizeInputList.size();
		}
	}

	public static class QpDate {

		public static java.sql.Date getCurrentDate() {
			return new java.sql.Date(System.currentTimeMillis());
		}

		public static java.sql.Timestamp getCurrentDateTime() {
			// TODO Auto-generated method stub
			return new Timestamp(System.currentTimeMillis());
		}

		public static java.sql.Time getTime() {
			// TODO Auto-generated method stub
			return new java.sql.Time(getCurrentDate().getTime());
		}

		public static Integer getDay(Date inputDay) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(inputDay);
			return calendar.get(Calendar.DAY_OF_WEEK);
		}

		public static Integer getDay(Timestamp inputDay) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(inputDay);
			return calendar.get(Calendar.DAY_OF_WEEK);
		}

		/*public static Integer getDayOfMonth(Date inputDay) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(inputDay);
			return calendar.get(Calendar.DAY_OF_MONTH);
		}*/

		public static Integer getDate(Date getDateInput) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(getDateInput);
			return calendar.get(Calendar.DAY_OF_MONTH);
		}

		public static Integer getDate(Timestamp getDateInput) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(getDateInput);
			return calendar.get(Calendar.DAY_OF_MONTH);
		}

		public static Integer getMonth(Date getMonthIntput) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(getMonthIntput);
			return calendar.get(Calendar.MONTH);
		}

		public static Integer getMonth(Timestamp getMonthIntput) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(getMonthIntput);
			return calendar.get(Calendar.MONTH);
		}

		public static Integer getYear(Date getYearInput) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(getYearInput);
			return calendar.get(Calendar.YEAR);
		}

		public static Integer getYear(Timestamp getYearInput) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(getYearInput);
			return calendar.get(Calendar.YEAR);
		}

		public static Boolean after(Date afterInputDate1, Date afterInputDate2) {
			return afterInputDate1.after(afterInputDate2);
		}

		public static Boolean after(Timestamp afterInputDate1, Timestamp afterInputDate2) {
			return afterInputDate1.after(afterInputDate2);
		}

		public static Boolean before(Date beforeInputDate1, Date beforeInputDate2) {
			return beforeInputDate1.before(beforeInputDate2);
		}

		public static Boolean before(Timestamp beforeInputDate1, Timestamp beforeInputDate2) {
			return beforeInputDate1.before(beforeInputDate2);
		}

		public static Boolean equal(Date equalInputDate1, Date equalInputDate2) {
			return equalInputDate1.equals(equalInputDate2);
		}

		public static Boolean equal(Timestamp equalInputDate1, Timestamp equalInputDate2) {
			return equalInputDate1.equals(equalInputDate2);
		}

		public static Integer compare(Date compareInputDate1, Date compareInputDate2) {
			return compareInputDate1.compareTo(compareInputDate2);
		}

		public static Integer compare(Timestamp compareInputDate1, Timestamp compareInputDate2) {
			return compareInputDate1.compareTo(compareInputDate2);
		}

		public static java.util.Date toDate(Object toDateInputObject) {
			// TODO Auto-generated method stub
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			if (toDateInputObject == null)
				return null;
			if (toDateInputObject instanceof java.util.Date)
				return (java.util.Date) toDateInputObject;

			if (toDateInputObject instanceof String) {
				if ("".equals((String) toDateInputObject))
					return null;
				try {

					return new java.util.Date(formatter.parse((String) toDateInputObject).getTime());
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
			}
			try {
				return new java.util.Date(formatter.parse(toDateInputObject.toString()).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}

		public static java.util.Date toDateTime(Object toDateTimeInputObject) {
			// TODO Auto-generated method stub
			SimpleDateFormat formatter = new SimpleDateFormat("d/M/yy H:mm:ss");
			if (toDateTimeInputObject == null)
				return null;
			if (toDateTimeInputObject instanceof java.util.Date)
				return (java.util.Date) toDateTimeInputObject;
			if (toDateTimeInputObject instanceof String) {
				if ("".equals((String) toDateTimeInputObject))
					return null;
				try {
					return formatter.parse((String) toDateTimeInputObject);
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
			}

			try {
				return formatter.parse(toDateTimeInputObject.toString());
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}

		public static java.sql.Timestamp toTimestamp(Object toTimestampInputObject) {
			// TODO Auto-generated method stub
			SimpleDateFormat formatter = new SimpleDateFormat("d/M/yy H:mm:ss.SSS");
			if (toTimestampInputObject == null)
				return null;
			if (toTimestampInputObject instanceof java.sql.Timestamp)
				return (java.sql.Timestamp) toTimestampInputObject;
			if (toTimestampInputObject instanceof String) {
				if ("".equals((String) toTimestampInputObject))
					return null;
				try {
					return new java.sql.Timestamp(formatter.parse((String) toTimestampInputObject).getTime());
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
			}

			try {
				return new java.sql.Timestamp(formatter.parse(toTimestampInputObject.toString()).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}

		public static java.sql.Timestamp parse(String parseInputString) {
			// TODO Auto-generated method stub
			SimpleDateFormat formatter = new SimpleDateFormat("d/M/yy H:mm:ss.SSS");
			try {
				return new java.sql.Timestamp(formatter.parse(parseInputString).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}

		public static java.sql.Timestamp parse(String inputString, String dateFormat) {
			try {
				return DateUtils.parse(inputString, dateFormat);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

		}
	}

	public static class QpCodelist {

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

		private static Object getKeyFromValueOfMap(Map<?, ?> map, Object value) {
			for (Object o : map.keySet()) {
				if (DataTypeUtils.equalsIgnoreCase(map.get(o), value)) {
					return o;
				}
			}
			return null;
		}

		private static Object getValueFromKeyOfMap(Map<?, ?> map, Object key) {
			for (Object o : map.keySet()) {
				if (DataTypeUtils.equalsIgnoreCase(o, key)) {
					return map.get(o);
				}
			}
			return null;
		}
	}

}
