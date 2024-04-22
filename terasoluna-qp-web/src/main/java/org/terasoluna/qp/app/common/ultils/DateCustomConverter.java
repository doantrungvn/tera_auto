package org.terasoluna.qp.app.common.ultils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.sql.Date;

import org.dozer.CustomConverter;

public class DateCustomConverter implements CustomConverter {

	public static final Locale defaultLocal = new Locale("en", "US");
	
	@Override
	public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass,
			Class<?> sourceClass) {
		// TODO Auto-generated method stub
		if (sourceFieldValue == null || sourceFieldValue.toString().isEmpty()) {
			return null;
		}

		if (sourceFieldValue instanceof String) {
			String source = sourceFieldValue.toString();
			try {
				if (destinationClass.getName().equals("java.sql.Time")) {
					Timestamp result = DateUtils.parse(source, DateUtils.getPatternTime(SessionUtils.getCurrentAccountProfile().getTimeFormat()));
					return new java.sql.Time(result.getTime());
				} else if (destinationClass.getName().equals("java.sql.Timestamp")) {
					Timestamp result = DateUtils.parse(source, DateUtils.getPatternDateTime(SessionUtils.getCurrentAccountProfile().getDateTimeFormat()));
					return result;
				} else {
					Timestamp result = DateUtils.parse(source, DateUtils.getPatternDate(SessionUtils.getCurrentAccountProfile().getDateFormat()));
					return new java.sql.Date(result.getTime());
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		String pattern = null;
		String strReturn = null;

		if (sourceFieldValue instanceof Date) {
			Date source = (Date) sourceFieldValue;
			pattern = DateUtils.getPatternDate(SessionUtils.getCurrentAccountProfile().getDateFormat());
			SimpleDateFormat format = new SimpleDateFormat(pattern, defaultLocal);

			return format.format(source);
		} else if (sourceFieldValue instanceof Timestamp) {
			Timestamp source = (Timestamp) sourceFieldValue;
			if (sourceClass.getName().equals("java.sql.Time")) {
				pattern = DateUtils.getPatternTime(SessionUtils.getCurrentAccountProfile().getTimeFormat());
			} else if (sourceClass.getName().equals("java.sql.Timestamp")) {
				pattern = DateUtils.getPatternDateTime(SessionUtils.getCurrentAccountProfile().getDateTimeFormat());
			} else {
				pattern = DateUtils.getPatternDate(SessionUtils.getCurrentAccountProfile().getDateFormat());
			}

			SimpleDateFormat format = new SimpleDateFormat(pattern, defaultLocal);

			return format.format(DateUtils.convertTimestamp2Date(source));
		} else if (sourceFieldValue instanceof Time) {
			Time source = (Time) sourceFieldValue;
			pattern = DateUtils.getPatternTime(SessionUtils.getCurrentAccountProfile().getTimeFormat());
			SimpleDateFormat format = new SimpleDateFormat(pattern, defaultLocal);
			return format.format(DateUtils.convertTime2Date(source));
		}

		return strReturn;
	}

}
