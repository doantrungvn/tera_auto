package org.terasoluna.qp.app.common.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;

public class ValidationDateTimeRange {
	
	protected SimpleDateFormat simpleFormat;
	protected Calendar caMin;
	protected Calendar caMax;
	protected Calendar dateOfUser;

	
	public ValidationDateTimeRange(String valueMin, String valueMax, String patternFomrmat) {
		if(StringUtils.isBlank(patternFomrmat)){
			throw new IllegalArgumentException("pattern format must be not null");
		}
		this.simpleFormat = new SimpleDateFormat(patternFomrmat);
		this.dateOfUser = Calendar.getInstance();
		this.caMin = Calendar.getInstance();
		this.caMax = Calendar.getInstance();
		Date dateMin = ValidationUtils.parseStringToDate(simpleFormat, valueMin);
		Date dateMax = ValidationUtils.parseStringToDate(simpleFormat, valueMax);
		this.caMin.setTime(dateMin);
		this.caMax.setTime(dateMax);
	}
	
	/**
	 * use in isValid method of Validation customs 
	 * @param value
	 * @param type
	 * @return
	 */
	public boolean isValid(String value) {
		if(StringUtils.isBlank(value)){
			return true;
		}
		try {
			Date dateUser = simpleFormat.parse(value);
			this.dateOfUser.setTime(dateUser);
			return this.validateDateTime();
		} catch (ParseException e) {
			return false;
		}
		
	}
	
	/**
	 * validate date of user have in between two dates defined.
	 * Should call this method in children class
	 * @param dateOfUser
	 * @param caMin
	 * @param caMax
	 * @return
	 */
	public boolean validateRangeTwoCalendar(Calendar dateOfUser, Calendar caMin, Calendar caMax){
		if(dateOfUser.equals(caMin) || dateOfUser.equals(caMax)){
			return true;
		}
		return dateOfUser.after(caMin) && dateOfUser.before(caMax);
	}
	
	/**
	 * 
	 * @param type
	 * @return
	 */
	public  boolean validateDateTime(){
		return validateRangeTwoCalendar(this.dateOfUser, this.caMin, this.caMax);
	}
}
