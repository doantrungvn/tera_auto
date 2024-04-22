package org.terasoluna.qp.app.common.ultils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.gfw.common.codelist.CodeList;
import org.terasoluna.qp.app.common.constants.DbDomainConst.BaseType;
import org.terasoluna.qp.domain.model.AccountProfile;

public class DateUtils {

	private static Map<String, String> DATE_FORMAT_REGEXPS;
	private static List<String> DATE_FORMAT;

	private static Map<String, String> DATETIME_FORMAT_SETTING;
	private static Map<String, String> TIME_FORMAT_SETTING;
	private static Map<String, String> DATE_FORMAT_SETTING;

	private static Map<String, String> DATETIME_FORMAT_SETTING_SQL;
	private static Map<String, String> TIME_FORMAT_SETTING_SQL;
	private static Map<String, String> DATE_FORMAT_SETTING_SQL;

	public static final Locale defaultLocal = new Locale("en", "US");

	static {
		init();
	}

	private static void init() {
		DATE_FORMAT_REGEXPS = new ConcurrentHashMap<String, String>(16, 0.9f, 1);

		DATE_FORMAT_REGEXPS.put("^\\d{8}$", "yyyyMMdd");
		DATE_FORMAT_REGEXPS.put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
		DATE_FORMAT_REGEXPS.put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
		DATE_FORMAT_REGEXPS.put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
		DATE_FORMAT_REGEXPS.put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
		DATE_FORMAT_REGEXPS.put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");

		DATE_FORMAT_REGEXPS.put("^\\d{12}$", "yyyyMMddHHmm");
		DATE_FORMAT_REGEXPS.put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
		DATE_FORMAT_REGEXPS.put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
		DATE_FORMAT_REGEXPS.put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm");

		DATE_FORMAT_REGEXPS.put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm");
		DATE_FORMAT_REGEXPS.put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
		DATE_FORMAT_REGEXPS.put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");

		DATE_FORMAT_REGEXPS.put("^\\d{14}$", "yyyyMMddHHmmss");
		DATE_FORMAT_REGEXPS.put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
		DATE_FORMAT_REGEXPS.put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
		DATE_FORMAT_REGEXPS.put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd/MM/yyyy HH:mm:ss");

		DATE_FORMAT_REGEXPS.put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
		DATE_FORMAT_REGEXPS.put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");

		DATE_FORMAT_REGEXPS.put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
		DATE_FORMAT_REGEXPS.put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss");

		DATE_FORMAT_REGEXPS.put("^\\d{1,2}:\\d{2}:\\d{2}$", "HH:mm:ss");
		DATE_FORMAT_REGEXPS.put("^\\d{1,2}:\\d{2}$", "HH:mm");
		DATE_FORMAT_REGEXPS.put("^\\w{3}\\s\\w{3}\\s\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}\\s\\w{3}\\s\\d{4}$", "EEE MMM dd HH:mm:ss zzz yyyy");

		// Mon Apr 20 17:25:15 ICT 2015
		DATE_FORMAT_REGEXPS.put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "dd/MM/yyyy");
		DATE_FORMAT_REGEXPS.put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}.\\d{1,3}$", "yyyy-MM-dd HH:mm:ss.SSS");

		DATE_FORMAT = new ArrayList<String>();

		DATE_FORMAT.add("yyyyMMdd");
		DATE_FORMAT.add("dd-MM-yyyy");
		DATE_FORMAT.add("dd/MM/yyyy");
		DATE_FORMAT.add("yyyy-MM-dd");
		DATE_FORMAT.add("MM/dd/yyyy");
		DATE_FORMAT.add("yyyy/MM/dd");
		DATE_FORMAT.add("dd MMM yyyy");
		DATE_FORMAT.add("dd MMMM yyyy");
		DATE_FORMAT.add("yyyyMMddHHmm");
		DATE_FORMAT.add("yyyyMMdd HHmm");
		DATE_FORMAT.add("dd-MM-yyyy HH:mm");
		DATE_FORMAT.add("yyyy-MM-dd HH:mm");
		DATE_FORMAT.add("MM/dd/yyyy HH:mm");
		DATE_FORMAT.add("yyyy/MM/dd HH:mm");
		DATE_FORMAT.add("dd MMM yyyy HH:mm");
		DATE_FORMAT.add("dd MMMM yyyy HH:mm");
		DATE_FORMAT.add("yyyyMMddHHmmss");
		DATE_FORMAT.add("yyyyMMdd HHmmss");
		DATE_FORMAT.add("dd-MM-yyyy HH:mm:ss");
		DATE_FORMAT.add("dd-MM-yyyy hh:mm:ss");
		DATE_FORMAT.add("dd/MM/yyyy HH:mm:ss");
		DATE_FORMAT.add("dd/MM/yyyy hh:mm:ss");
		DATE_FORMAT.add("yyyy-MM-dd HH:mm:ss");
		DATE_FORMAT.add("yyyy-MM-dd hh:mm:ss");
		DATE_FORMAT.add("yyyy/MM/dd HH:mm:ss");
		DATE_FORMAT.add("yyyy/MM/dd hh:mm:ss");
		DATE_FORMAT.add("MM/dd/yyyy HH:mm:ss");
		DATE_FORMAT.add("MM/dd/yyyy hh:mm:ss");
		DATE_FORMAT.add("MM-dd-yyyy HH:mm:ss");
		DATE_FORMAT.add("MM-dd-yyyy hh:mm:ss");
		DATE_FORMAT.add("dd MMM yyyy HH:mm:ss");
		DATE_FORMAT.add("dd MMMM yyyy HH:mm:ss");
		DATE_FORMAT.add("HH:mm:ss");
		DATE_FORMAT.add("HH:mm");
		DATE_FORMAT.add("EEE MMM dd HH:mm:ss zzz yyyy");
		DATE_FORMAT.add("yyyy-MM-dd HH:mm:ss.SSS");

		DATETIME_FORMAT_SETTING = new ConcurrentHashMap<String, String>(16, 0.9f, 1);
		DATETIME_FORMAT_SETTING.put("1", "yyyy/MM/dd HH:mm:ss");
		DATETIME_FORMAT_SETTING.put("2", "yyyy/MM/dd hh:mm:ss aa");
		DATETIME_FORMAT_SETTING.put("3", "dd/MM/yyyy HH:mm:ss");
		DATETIME_FORMAT_SETTING.put("4", "dd/MM/yyyy hh:mm:ss aa");

		DATE_FORMAT_SETTING = new ConcurrentHashMap<String, String>(16, 0.9f, 1);
		DATE_FORMAT_SETTING.put("1", "yyyy/MM/dd");
		DATE_FORMAT_SETTING.put("2", "dd/MM/yyyy");

		TIME_FORMAT_SETTING = new ConcurrentHashMap<String, String>(16, 0.9f, 1);
		TIME_FORMAT_SETTING.put("1", "HH:mm:ss");
		TIME_FORMAT_SETTING.put("2", "hh:mm:ss aa");

		DATETIME_FORMAT_SETTING_SQL = new ConcurrentHashMap<String, String>(16, 0.9f, 1);
		DATETIME_FORMAT_SETTING_SQL.put("1", "YYYY/MM/DD HH24:MI:SS");
		DATETIME_FORMAT_SETTING_SQL.put("2", "YYYY/MMM/DD HH:MI:SS AM");
		DATETIME_FORMAT_SETTING_SQL.put("3", "DD/MM/YYYY HH24:MI:SS");
		DATETIME_FORMAT_SETTING_SQL.put("4", "DD/MM/YYYY HH:MI:SS AM");

		TIME_FORMAT_SETTING_SQL = new ConcurrentHashMap<String, String>(16, 0.9f, 1);
		TIME_FORMAT_SETTING_SQL.put("1", "HH24:MI:SS");
		TIME_FORMAT_SETTING_SQL.put("2", "HH:MI:SS AM");

		DATE_FORMAT_SETTING_SQL = new ConcurrentHashMap<String, String>(16, 0.9f, 1);
		DATE_FORMAT_SETTING_SQL.put("1", "YYYY/MM/DD");
		DATE_FORMAT_SETTING_SQL.put("2", "DD/MM/YYYY");

	}

	public static Timestamp parse(String dateString) throws ParseException {
		String dateFormat = determineDateFormat(dateString);
		if (dateFormat == null) {
			throw new ParseException("Unknown date format.", 0);
		}
		return parse(dateString, dateFormat);
	}

	public static Timestamp parse(String dateString, String dateFormat) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, defaultLocal);
		return convertJavaDateToSqlDate(simpleDateFormat.parse(dateString));
	}

	public static Timestamp convertJavaDateToSqlDate(java.util.Date date) {
		return new Timestamp(date.getTime());
	}

	public static java.util.Date convertTime2Date(java.sql.Time time) {
		return new java.util.Date(time.getTime());
	}

	public static java.util.Date convertTimestamp2Date(java.sql.Timestamp timestamp) {
		return new java.util.Date(timestamp.getTime());
	}

	public static String determineDateFormat(String dateString) {
		for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
			if (dateString.toLowerCase().matches(regexp)) {
				return DATE_FORMAT_REGEXPS.get(regexp);
			}
		}
		return null; // Unknown format.
	}

	public static boolean isValidPattern(String pattern) {
		if (DATE_FORMAT.contains(pattern)) {
			return true;
		}

		return false; // Unknown pattern.
	}

	public static boolean isValidDate(String dateString) {
		for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
			if (dateString.toLowerCase().matches(regexp)) {
				return true;
			}
		}
		return false;
	}

	public static String getPatternDate(String format) {

		if (StringUtils.isBlank(format)) {
			// get default system setting attributes from database.resource
			CodeList systemSettings = (CodeList) ApplicationContextProvider.getApplicationContext().getBean("CL_SYSTEM_SETTING");
			if (systemSettings != null && systemSettings.asMap().get("dateFormat") != null) {
				format = systemSettings.asMap().get("dateFormat");
			} else {
				format = "1";
			}
		}
		format = DATE_FORMAT_SETTING.get(format);
		return StringUtils.isBlank(format) ? DATE_FORMAT_SETTING.get("1") : format;
	}

	public static String getPatternDateSql(String format) {
		if (StringUtils.isBlank(format)) {
			return DATE_FORMAT_SETTING_SQL.get("1");
		}
		format = DATE_FORMAT_SETTING_SQL.get(format);
		return StringUtils.isBlank(format) ? DATE_FORMAT_SETTING_SQL.get("1") : format;
	}

	public static String getPatternDateTime(String format) {

		if (StringUtils.isBlank(format)) {
			CodeList systemSettings = (CodeList) ApplicationContextProvider.getApplicationContext().getBean("CL_SYSTEM_SETTING");
			if (systemSettings != null && systemSettings.asMap().get("dateTimeFormat") != null) {
				format = systemSettings.asMap().get("dateTimeFormat");
			} else {
				format = "1";
			}
		}

		format = DATETIME_FORMAT_SETTING.get(format);
		return StringUtils.isBlank(format) ? DATETIME_FORMAT_SETTING.get("1") : format;
	}

	public static String getPatternDateTimeSql(String format) {

		if (StringUtils.isBlank(format)) {
			return DATETIME_FORMAT_SETTING_SQL.get("1");
		}
		format = DATETIME_FORMAT_SETTING_SQL.get(format);
		return StringUtils.isBlank(format) ? DATETIME_FORMAT_SETTING_SQL.get("1") : format;
	}

	public static String getPatternTime(String format) {

		if (StringUtils.isBlank(format)) {
			CodeList systemSettings = (CodeList) ApplicationContextProvider.getApplicationContext().getBean("CL_SYSTEM_SETTING");
			if (systemSettings != null && systemSettings.asMap().get("timeFormat") != null) {
				format = systemSettings.asMap().get("timeFormat");
			} else {
				format = "1";
			}
		}

		format = TIME_FORMAT_SETTING.get(format);

		return StringUtils.isBlank(format) ? TIME_FORMAT_SETTING.get("1") : format;
	}

	public static String getPatternTimeSql(String format) {
		if (StringUtils.isBlank(format)) {
			return TIME_FORMAT_SETTING_SQL.get("1");
		}
		format = TIME_FORMAT_SETTING_SQL.get(format);
		return StringUtils.isBlank(format) ? TIME_FORMAT_SETTING_SQL.get("1") : format;
	}

	public static String formatDate(String dateStr, String pattern) throws ParseException {
		return format(dateStr, getPatternDate(pattern));
	}

	public static String formatDateTime(String dateStr, String pattern) throws ParseException {
		return format(dateStr, getPatternDateTime(pattern));
	}

	public static String formatTime(String dateStr, String pattern) throws ParseException {
		return format(dateStr, getPatternTime(pattern));
	}

	private static String format(String dateStr, String pattern) throws ParseException {
		try {
			SimpleDateFormat frmPattern = new SimpleDateFormat(pattern, defaultLocal);
			Timestamp date = parse(dateStr.trim());
			return frmPattern.format(date);
		} catch (ParseException e) {
			throw e;
		}
	}

	public static String formatDateTime(Timestamp dateTime, String pattern) {
		SimpleDateFormat frmPattern = new SimpleDateFormat(pattern, defaultLocal);
		return frmPattern.format(dateTime);
	}

	/**
	 * 
	 * @param dateOne
	 * @param dateTwo
	 * @return -1: dateOne smaller than dateTwo 0: equal 1: dateOne greater than
	 *         dateTwo
	 */
	public static int compare(Timestamp dateOne, Timestamp dateTwo) {
		if (dateOne.before(dateTwo))
			return -1;
		if (dateOne.after(dateTwo))
			return 1;

		return 0;
	}
	
	public static String getPatternFormatDateTimeByDataTypeFromProfile(Integer dataType, AccountProfile profile){
		if(dataType == null){
			return "";
		}
		String pattern = DateUtils.getPatternDateSql(profile.getDateFormat()); 
		switch (dataType) {
			case BaseType.DATETIME_BASETYPE:
			case BaseType.TIMESTAMP_BASETYPE:
				pattern = DateUtils.getPatternDateTimeSql(profile.getDateTimeFormat());
				break;
			case BaseType.TIME_BASETYPE:
				pattern = DateUtils.getPatternTimeSql(profile.getTimeFormat());
				break;
		}
		return pattern;
	}

}
