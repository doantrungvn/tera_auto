package org.terasoluna.qp.app.common.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.ultils.DateUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.domain.model.AccountProfile;

public class ValidationCompareDate {
	
	protected SimpleDateFormat simpleFormat;
	protected Calendar origin;
	protected Calendar dateOfUser;
	
	public enum TypesValidateDateTime{
		BEFORE {
			@Override
			boolean validate(Calendar dateOfUser, Calendar origin) {
				return dateOfUser.before(origin) || (dateOfUser.compareTo(origin) == 0); 
			}
		}, AFTER {
			@Override
			boolean validate(Calendar dateOfUser, Calendar origin) {
				return dateOfUser.after(origin) || (dateOfUser.compareTo(origin) == 0 );
			}
		}, EQUAL {
			@Override
			boolean validate(Calendar dateOfUser, Calendar origin) {
				return dateOfUser.compareTo(origin) == 0;
			}
		}, NOT_EQUAL {
			@Override
			boolean validate(Calendar dateOfUser, Calendar origin) {
				return !(dateOfUser.compareTo(origin) == 0);
			}
		}, PAST {
			@Override
			boolean validate(Calendar dateOfUser, Calendar origin) {
				return dateOfUser.before(origin);
			}
		},FUTURE {
			@Override
			boolean validate(Calendar dateOfUser, Calendar origin) {
				return dateOfUser.after(origin);
			}
		};
		abstract boolean validate(Calendar dateOfUser, Calendar origin);
	}
	
	public enum DateTimeType{
		DATE {
			@Override
			SimpleDateFormat getPatterFormat() {
				AccountProfile profile = this.getAccountProfile();
				String pattern = DateUtils.getPatternDate(profile.getDateFormat());
				return new SimpleDateFormat(pattern);
			}
		},DATETIME {
			@Override
			SimpleDateFormat getPatterFormat() {
				AccountProfile profile = this.getAccountProfile();
				String pattern = DateUtils.getPatternDateTime(profile.getDateTimeFormat());
				return new SimpleDateFormat(pattern);
			}
		},TIME {
			@Override
			SimpleDateFormat getPatterFormat() {
				AccountProfile profile = this.getAccountProfile();
				String pattern = DateUtils.getPatternTime(profile.getDateTimeFormat());
				return new SimpleDateFormat(pattern);
			}
		},TIMESTAMP {
			@Override
			SimpleDateFormat getPatterFormat() {
				AccountProfile profile = this.getAccountProfile();
				String pattern = DateUtils.getPatternDateTime(profile.getDateTimeFormat());
				return new SimpleDateFormat(pattern);
			}
		};
		 AccountProfile getAccountProfile(){
			return SessionUtils.getCurrentAccountProfile();
		}
		abstract SimpleDateFormat getPatterFormat();
	}
	
	public ValidationCompareDate(String valueOrgin, String patternFormat) {
		if(StringUtils.isBlank(patternFormat)){
			throw new IllegalArgumentException("pattern format must be not null");
		}
		this.simpleFormat = new SimpleDateFormat(patternFormat);
		this.simpleFormat.setLenient(false);
		dateOfUser = Calendar.getInstance();
		this.origin = Calendar.getInstance();
		Date dateOrgin = ValidationUtils.parseStringToDate(simpleFormat, valueOrgin);
		origin.setTime(dateOrgin);
	}
	
	public ValidationCompareDate(String patternFormat) {
		if(StringUtils.isBlank(patternFormat)){
			throw new IllegalArgumentException("pattern format must be not null");
		}
		this.simpleFormat = new SimpleDateFormat(patternFormat);
		this.simpleFormat.setLenient(false);
		dateOfUser = Calendar.getInstance();
		origin = Calendar.getInstance();
		String strDateCurrent = simpleFormat.format(new Date());
		try {
			origin.setTime(simpleFormat.parse(strDateCurrent));
		} catch (ParseException e) {
			origin.setTime(new Date());
		}
		
	}
	
	
	public boolean isValid(String value,DateTimeType datimeType,TypesValidateDateTime type) {
		if(StringUtils.isBlank(value)){
			return true;
		}
		try {
			SimpleDateFormat simpleFormat = datimeType.getPatterFormat();
			simpleFormat.setLenient(false);
			Date dateUser = simpleFormat.parse(value);
			this.dateOfUser.setTime(dateUser);
			return this.validateDateTimeByType(type);
		} catch (ParseException e) {
			return false;
		}
		
	}
	
	
	/**
	 * validate the calendar first before or after second calendar
	 * @param dateOfUser
	 * @param origin
	 * @param type
	 * @return
	 */
	public boolean validateTwoCalendar(Calendar dateOfUser, Calendar origin, TypesValidateDateTime type){
		if(type == null)
			return false;
		return type.validate(dateOfUser, origin);		
	}
	
	public boolean validateDateTimeByType(TypesValidateDateTime type){
		return validateTwoCalendar(this.dateOfUser, this.origin, type);
	}
}
