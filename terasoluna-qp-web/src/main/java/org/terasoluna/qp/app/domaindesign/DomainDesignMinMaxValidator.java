package org.terasoluna.qp.app.domaindesign;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.DateUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.DomainDesignMessageConst;
import org.terasoluna.qp.app.message.ScreenDesignMessageConst;
import org.terasoluna.qp.app.message.TableDesignMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeConst;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeUtil;
import org.terasoluna.qp.domain.service.graphicdatabasedesign.TableDesignUtil;

@Component
public class DomainDesignMinMaxValidator implements Validator {

	@Inject 
	SystemService systemService;

	@Override
	public boolean supports(Class<?> clazz) {
		return (DomainDesignForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		DomainDesignForm domainDesignForm = (DomainDesignForm) target;
		AccountProfile accountProfile = systemService.getDefaultProfile();
		
		// Validate Table Name
		if(FunctionCommon.equals(domainDesignForm.getDomainName(), null)){
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0001)}, null);
		}else{
			if(domainDesignForm.getDomainName().length() > accountProfile.getNameMaxLength()){
				errors.reject(CommonMessageConst.ERR_SYS_0064, new Object[] { MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0001), accountProfile.getNameMinLength(), accountProfile.getNameMaxLength()}, null);
			}
			if(!Pattern.matches(accountProfile.getNamePattern(), domainDesignForm.getDomainName())){
				errors.reject(CommonMessageConst.ERR_SYS_0126, new Object[] { MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0001), accountProfile.getNameMask()}, null);
			}
		}
		
		// Validate Table Code
		if(FunctionCommon.equals(domainDesignForm.getDomainCode(), null)){
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0002)}, null);
		}else{
			if(domainDesignForm.getDomainCode().length() > accountProfile.getCodeMaxLength()){
				errors.reject(CommonMessageConst.ERR_SYS_0064, new Object[] { MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0002), accountProfile.getNameMinLength(), accountProfile.getNameMaxLength()}, null);
			}
			if(!Pattern.matches(accountProfile.getCodePattern(), domainDesignForm.getDomainCode())){
				errors.reject(CommonMessageConst.ERR_SYS_0066, new Object[] { MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0002, accountProfile.getNameMask())}, null);
			}
		}

		// Validate Remark
		if(!FunctionCommon.equals(domainDesignForm.getRemark(), null)){
			if(domainDesignForm.getRemark().length() > accountProfile.getRemarkMaxLength()){
				errors.reject(CommonMessageConst.ERR_SYS_0064, new Object[] { MessageUtils.getMessage(CommonMessageConst.SC_SYS_0028), accountProfile.getRemarkMinLength(), accountProfile.getRemarkMaxLength()}, null);
			}
		}
		
		// Validate Base type
		if(FunctionCommon.equals(domainDesignForm.getBaseTypeAutocomplete(), null)){
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0003)}, null);
		}
		//does not validate max length of text
		if (FunctionCommon.equals(DomainDatatypeConst.PhysicalDataTypeDetail.TEXT, domainDesignForm.getBaseType()) || FunctionCommon.equals(DomainDatatypeConst.PhysicalDataTypeDetail.CHAR, domainDesignForm.getBaseType()) || FunctionCommon.equals(DomainDatatypeConst.PhysicalDataTypeDetail.CHARACTER_VARYING, domainDesignForm.getBaseType())) {
			if (FunctionCommon.notEquals(DomainDatatypeConst.PhysicalDataTypeDetail.TEXT, domainDesignForm.getBaseType())) {
				if (FunctionCommon.isEmpty(domainDesignForm.getMaxLength())) {
					errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0006)}, null);
				} else if (domainDesignForm.getMaxLength() > accountProfile.getRemarkMaxLength()) {
					errors.reject(DomainDesignMessageConst.ERR_DOMAINDESIGN_0002, new Object[] { accountProfile.getRemarkMaxLength() }, null);
				}
			}

			if (StringUtils.isNoneBlank(domainDesignForm.getDefaultValue())){
				int maxlength = (FunctionCommon.isEmpty(domainDesignForm.getMaxLength()) || domainDesignForm.getMaxLength() > accountProfile.getRemarkMaxLength()) ? accountProfile.getRemarkMaxLength() : domainDesignForm.getMaxLength();
				if (maxlength < domainDesignForm.getDefaultValue().length()) {
					errors.reject(DomainDesignMessageConst.ERR_DOMAINDESIGN_0002, new Object[] { maxlength }, null);
				}
			}
		} /*else if (DomainDatatypeConst.PhysicalDataTypeDetail.INTEGER.equals(domainDesignForm.getBaseType()) 
				|| DomainDatatypeConst.PhysicalDataTypeDetail.SMALLINT.equals(domainDesignForm.getBaseType()) 
				|| DomainDatatypeConst.PhysicalDataTypeDetail.BIGINT.equals(domainDesignForm.getBaseType()) 
				|| DomainDatatypeConst.PhysicalDataTypeDetail.SERIAL.equals(domainDesignForm.getBaseType()) 
				|| DomainDatatypeConst.PhysicalDataTypeDetail.BIGSERIAL.equals(domainDesignForm.getBaseType())
				|| DomainDatatypeConst.PhysicalDataTypeDetail.BYTE.equals(domainDesignForm.getBaseType())
				|| DomainDatatypeConst.PhysicalDataTypeDetail.FLOAT.equals(domainDesignForm.getBaseType())
				|| DomainDatatypeConst.PhysicalDataTypeDetail.DOUBLE.equals(domainDesignForm.getBaseType())
				) {
			// if is integer then validate default
			List<String> checkOutOfRange = TableDesignUtil.checkValidValueOfBaseType(domainDesignForm.getBaseType(), domainDesignForm.getDefaultValue());
			if (!FunctionCommon.isEmpty(checkOutOfRange)) {
				errors.reject(DomainDesignMessageConst.ERR_DOMAINDESIGN_0003, new Object[] { checkOutOfRange.get(0), checkOutOfRange.get(1) }, null);
			}
		}*/
		
		Integer groupBasetype = domainDesignForm.getGroupBasetypeId();
		// Check Maxlength and precision
		if (DbDomainConst.PhysicalDataType.DECIMAL.equals(groupBasetype) || DbDomainConst.PhysicalDataType.CURRENCY.equals(groupBasetype)){
			if(domainDesignForm.getMaxLength() != null && domainDesignForm.getPrecision() != null){
				if(domainDesignForm.getMaxLength().intValue() <= domainDesignForm.getPrecision().intValue()){
					errors.reject(DomainDesignMessageConst.SC_DOMAINDESIGN_0062, null, null);
				}
			}
		}
		
		String strMsgField = null;

		try {

			// validate minVal and maxVal
			String minVal = "", maxVal = "", defaultVal = "";
			minVal = domainDesignForm.getMinVal();
			maxVal = domainDesignForm.getMaxVal();
			defaultVal = domainDesignForm.getDefaultValue();

			if (groupBasetype == null)
				return;

			if (DbDomainConst.PhysicalDataType.INTEGER.equals(groupBasetype)
					|| DbDomainConst.PhysicalDataType.DECIMAL.equals(groupBasetype)
					|| DbDomainConst.PhysicalDataType.CURRENCY.equals(groupBasetype)) {

				strMsgField = MessageUtils
						.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0008);

				// Check min > max
				if (DomainDesignUtil.compareObject(maxVal, minVal, groupBasetype) < 0) {
					String[] param = {
							MessageUtils
									.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0029),
							MessageUtils
									.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0028) };
					errors.reject(CommonMessageConst.ERR_SYS_0042, param, null);
				}

				strMsgField = MessageUtils
						.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0004);

				// check range for numeric
				if (domainDesignForm.getOperatorCode() != "0" && DbDomainConst.ConstrainsType.RANGE.equals(domainDesignForm.getConstrainsType())) {
					validateRange(minVal, maxVal, defaultVal, groupBasetype, domainDesignForm.getOperatorCode(),errors);
				}

				// Check max < default value
				if (DataTypeUtils.equals(domainDesignForm.getDefaultType(), DbDomainConst.YesNoFlg.NO) && DomainDesignUtil.compareObject(maxVal, defaultVal, groupBasetype) < 0) {
					String[] param = {
							MessageUtils
									.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0004),
							MessageUtils
									.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0029) };
					errors.reject(CommonMessageConst.ERR_SYS_0050, param, null);
				}

				// Check min > default value
				if (DataTypeUtils.equals(domainDesignForm.getDefaultType(), DbDomainConst.YesNoFlg.NO) && DomainDesignUtil.compareObject(defaultVal, minVal, groupBasetype) < 0) {
					String[] param = {
							MessageUtils
									.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0004),
							MessageUtils
									.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0028) };

					errors.reject(CommonMessageConst.ERR_SYS_0042, param, null);
				}

				//If group base type is integer then check value has out of range
				if (DbDomainConst.PhysicalDataType.INTEGER.equals(groupBasetype)) {
					Integer dataType = domainDesignForm.getBaseType();
					//validate value of minval
					if (!DomainDatatypeUtil.isEmpty(minVal)) {
						List<String> listCheck = TableDesignUtil.checkValidValueOfBaseType(dataType, minVal);
						if (!DomainDatatypeUtil.isEmpty(listCheck)) {
							String[] param = { MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0028), listCheck.get(0), listCheck.get(1)};
							errors.reject(CommonMessageConst.ERR_SYS_0103, param, null);
						}
					}
					//validate value of maxval
					if (!DomainDatatypeUtil.isEmpty(maxVal)) {
						List<String> listCheck = TableDesignUtil.checkValidValueOfBaseType(dataType, maxVal);
						if (!DomainDatatypeUtil.isEmpty(listCheck)) {
							String[] param = { MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0029), listCheck.get(0), listCheck.get(1)};
							errors.reject(CommonMessageConst.ERR_SYS_0103, param, null);
						}
					}
					//validate value of default value
					if (!DomainDatatypeUtil.isEmpty(defaultVal)) {
						List<String> listCheck = TableDesignUtil.checkValidValueOfBaseType(dataType, defaultVal);
						if (!DomainDatatypeUtil.isEmpty(listCheck)) {
							String[] param = { MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0004), listCheck.get(0), listCheck.get(1)};
							errors.reject(CommonMessageConst.ERR_SYS_0103, param, null);
						}
					}
				}
				
			// if is char or text then require fmtcode
			} 
//			else if (groupBasetype.equals(DbDomainConst.PhysicalDataType.CHAR)
//					|| groupBasetype.equals(DbDomainConst.PhysicalDataType.TEXT)) {
//
//				if (DomainDatatypeUtil.isEmpty(domainDesignForm.getFmtCode())) {
//					errors.reject(
//							CommonMessageConst.ERR_SYS_0025,
//							new String[] { MessageUtils
//									.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0009) },
//							null);
//				}
//
//			}
			
			validateDateTimeAdvance(domainDesignForm, errors, groupBasetype);

			// validate maxlength
			try {
				Integer maxLength = domainDesignForm.getMaxLength();
				int precision = domainDesignForm.getPrecision() == null? 0: domainDesignForm.getPrecision().intValue(); 

				if (DomainDatatypeUtil.isEmpty(maxLength)) {
					if (/*DbDomainConst.PhysicalDataType.TEXT.equals(groupBasetype)
							|| DbDomainConst.PhysicalDataType.CHAR.equals(groupBasetype)
							|| */ DbDomainConst.PhysicalDataType.CURRENCY.equals(groupBasetype)
							|| (DbDomainConst.PhysicalDataType.DECIMAL.equals(groupBasetype)
								&& (domainDesignForm.getBaseType() != null && domainDesignForm.getBaseType().equals(DbDomainConst.BaseType.NUMERIC_BASETYPE)))) {
					errors.reject(
							CommonMessageConst.ERR_SYS_0025,
							new String[] { MessageUtils
									.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0006) },
							null);
					}
				} else {
					if (DbDomainConst.PhysicalDataType.INTEGER.equals(groupBasetype)
							|| DbDomainConst.PhysicalDataType.DECIMAL.equals(groupBasetype)
							|| DbDomainConst.PhysicalDataType.CURRENCY.equals(groupBasetype)){
						minVal = minVal == null ? null : minVal.replace(".", "").replace("-", "");
						minVal = maxVal == null ? null : maxVal.replace(".", "").replace("-", "");
						defaultVal = defaultVal == null ? null : defaultVal.replace(".", "").replace("-", "");
					}
					//validate max length of min value
					if (!DomainDatatypeUtil.isEmpty(minVal) && minVal.length() > (maxLength.intValue() + precision)) {
						String[] param = { MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0028), String.valueOf(maxLength.intValue())};
							errors.reject(CommonMessageConst.ERR_SYS_0020, param, null);
					}
					//validate max length of max value
					if (!DomainDatatypeUtil.isEmpty(maxVal) && maxVal.length() > (maxLength.intValue() + precision)) {
						String[] param = { MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0029), String.valueOf(maxLength.intValue())};
							errors.reject(CommonMessageConst.ERR_SYS_0020, param, null);
					}
					//validate max length of default value
					if (!DomainDatatypeUtil.isEmpty(defaultVal) && defaultVal.length() > (maxLength.intValue() + precision)) {
						String[] param = { MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0004), String.valueOf(maxLength.intValue())};
							errors.reject(CommonMessageConst.ERR_SYS_0020, param, null);
					}
				}
			} catch (Exception e) {
				errors.reject(
						CommonMessageConst.ERR_SYS_0029,
						new String[] { MessageUtils
								.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0006) },
						null);
			}
			
		} catch (Exception e) {
			errors.reject(e.getMessage(), new String[] { strMsgField }, null);
		}

	}

	/**
	 * @param minVal
	 * @param maxVal
	 * @param defaultVal
	 * @param groupBasetype
	 * @param operatorCode
	 * @param errors
	 * @throws Exception
	 */
	private void validateRange(String minVal, String maxVal, String defaultVal, Integer groupBasetype, String operatorCode, Errors errors) throws Exception {
		switch (operatorCode) {
		case "1" : 
			// =
			if(DomainDesignUtil.compareObject(maxVal, defaultVal, groupBasetype) != 0){
				errors.reject(DomainDesignMessageConst.SC_DOMAINDESIGN_0054, null, null);
			}
			break;
		case "2" :
			// <
			if(DomainDesignUtil.compareObject(maxVal, defaultVal, groupBasetype) <= 0){
				errors.reject(DomainDesignMessageConst.SC_DOMAINDESIGN_0055, null, null);
			}
			break;
		case "3" :
			// <=
			if(DomainDesignUtil.compareObject(maxVal, defaultVal, groupBasetype) < 0){
				errors.reject(DomainDesignMessageConst.SC_DOMAINDESIGN_0056, null, null);
			}
			break;
		case "4" :
			// >
			if(DomainDesignUtil.compareObject(minVal, defaultVal, groupBasetype) <= 0){
				errors.reject(DomainDesignMessageConst.SC_DOMAINDESIGN_0057, null, null);
			}
			break;
		case "5" :
			// >=
			if(DomainDesignUtil.compareObject(minVal, defaultVal, groupBasetype) < 0){
				errors.reject(DomainDesignMessageConst.SC_DOMAINDESIGN_0058, null, null);
			}
			break;
		case "6" :
			// <>
			if(DomainDesignUtil.compareObject(minVal, defaultVal, groupBasetype) == 0){
				errors.reject(DomainDesignMessageConst.SC_DOMAINDESIGN_0059, null, null);
			}
			break;
		case "8" :
			//BETWEEN
			if(DomainDesignUtil.compareObject(minVal, defaultVal, groupBasetype) > 0 || DomainDesignUtil.compareObject(defaultVal, maxVal, groupBasetype) > 0){
			errors.reject(DomainDesignMessageConst.SC_DOMAINDESIGN_0053, null, null);
		}
		break;
	};
}

	/**
	 * 
	 * @param target
	 * @param errors
	 */
	private void validateDateTimeAdvance(DomainDesignForm domainDesignForm, Errors errors, Integer groupBasetype) {
		if (DbDomainConst.PhysicalDataType.DATE.equals(groupBasetype)
				|| DbDomainConst.PhysicalDataType.DATETIME.equals(groupBasetype)
				|| DbDomainConst.PhysicalDataType.TIME.equals(groupBasetype)) {
			
			String pattern = "";
			if(DbDomainConst.PhysicalDataType.DATE.equals(groupBasetype)){
				pattern = DateUtils.getPatternDate(SessionUtils.getCurrentAccountProfile().getDateFormat());
			}else if(DbDomainConst.PhysicalDataType.DATETIME.equals(groupBasetype)){
				pattern = DateUtils.getPatternDateTime(SessionUtils.getCurrentAccountProfile().getDateTimeFormat());
			}else if(DbDomainConst.PhysicalDataType.TIME.equals(groupBasetype)){
				pattern = DateUtils.getPatternTime(SessionUtils.getCurrentAccountProfile().getTimeFormat());
			}
			
			Timestamp maxValue = null;
			Timestamp minValue = null;
			Timestamp defaultValue = null;
			
			
			if(DbDomainConst.ConstrainsType.RANGE.equals(domainDesignForm.getConstrainsType())){
				if(DbDomainConst.DefaultType.FUNCTION.equals(domainDesignForm.getDefaultType())){
					try {
						switch (domainDesignForm.getOperatorCode()) {
						case "1":
						case "2":
						case "3":
							if(domainDesignForm.getMaxVal() == null){
								errors.reject(CommonMessageConst.ERR_SYS_0025, new String[] { MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0043) }, null);
							}
							break;
						case "4":
						case "5":
						case "6":
							if(domainDesignForm.getMinVal() == null){
								errors.reject(CommonMessageConst.ERR_SYS_0025, new String[] { MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0043) }, null);
							}
							break;
						case "8":
							if(domainDesignForm.getMaxVal() == null){
								errors.reject(CommonMessageConst.ERR_SYS_0025, new String[] { MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0043) }, null);
							}else if(domainDesignForm.getMinVal() == null){
								errors.reject(CommonMessageConst.ERR_SYS_0025, new String[] { MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0043) }, null);
							}else{
								
									maxValue = DateUtils.parse(domainDesignForm.getMaxVal(), pattern);
									minValue = DateUtils.parse(domainDesignForm.getMinVal(), pattern);
									
									if(DateUtils.compare(minValue, maxValue) == 1){
										errors.reject(CommonMessageConst.ERR_SYS_0050, new String[] { MessageUtils .getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0176), MessageUtils .getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0177) }, null);
									}
							}
							break;
						}
					} catch (ParseException e) {
						
					}
				}else if(domainDesignForm.getDefaultType() == null || DbDomainConst.DefaultType.TEXT.equals(domainDesignForm.getDefaultType())){
					try {
						if(domainDesignForm.getDefaultValue() != null){
							defaultValue = DateUtils.parse(domainDesignForm.getDefaultValue(), pattern);
						}
						switch (domainDesignForm.getOperatorCode()) {
						case "1":
							if(domainDesignForm.getMaxVal() == null){
								errors.reject(CommonMessageConst.ERR_SYS_0025, new String[] { MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0043) }, null);
							}else{
								maxValue = DateUtils.parse( domainDesignForm.getMaxVal(), pattern);
								if(DateUtils.compare(maxValue, defaultValue) != 0){
									errors.reject(DomainDesignMessageConst.SC_DOMAINDESIGN_0054, new String[] {}, null);
								}
							}
							break;
						case "2":
							if(domainDesignForm.getMaxVal() == null){
								errors.reject(CommonMessageConst.ERR_SYS_0025, new String[] { MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0043) }, null);
							}else{
								maxValue = DateUtils.parse(domainDesignForm.getMaxVal(), pattern);
								if(DateUtils.compare(maxValue, defaultValue) == 0 || DateUtils.compare(maxValue, defaultValue) == -1){
									errors.reject(DomainDesignMessageConst.SC_DOMAINDESIGN_0055, new String[] {}, null);
								}
							}
							break;
						case "3":
							if(domainDesignForm.getMaxVal() == null){
								errors.reject(CommonMessageConst.ERR_SYS_0025, new String[] { MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0043) }, null);
							}else{
								maxValue = DateUtils.parse(domainDesignForm.getMaxVal(), pattern);
								if(DateUtils.compare(maxValue, defaultValue) == -1){
									errors.reject(DomainDesignMessageConst.SC_DOMAINDESIGN_0056, new String[] {}, null);
								}
							} 
							break;
						case "4":
							if(domainDesignForm.getMinVal() == null){
								errors.reject(CommonMessageConst.ERR_SYS_0025, new String[] { MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0043) }, null);
							}else{
								minValue = DateUtils.parse(domainDesignForm.getMinVal(), pattern);
								if(DateUtils.compare(minValue, defaultValue) == 1 || DateUtils.compare(minValue, defaultValue) == 0){
									errors.reject(DomainDesignMessageConst.SC_DOMAINDESIGN_0057, new String[] {}, null);
								}
							} 
						case "5":
							if(domainDesignForm.getMinVal() == null){
								errors.reject(CommonMessageConst.ERR_SYS_0025, new String[] { MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0043) }, null);
							}else{
								minValue = DateUtils.parse(domainDesignForm.getMinVal(), pattern);
								if(DateUtils.compare(minValue, defaultValue) == 1){
									errors.reject(DomainDesignMessageConst.SC_DOMAINDESIGN_0058, new String[] {}, null);
								}
							} 
						case "6":
							if(domainDesignForm.getMinVal() == null){
								errors.reject(CommonMessageConst.ERR_SYS_0025, new String[] { MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0043) }, null);
							}else{
								minValue = DateUtils.parse(domainDesignForm.getMinVal(), pattern);
								if(DateUtils.compare(minValue, defaultValue) == 0){
									errors.reject(DomainDesignMessageConst.SC_DOMAINDESIGN_0059, new String[] {}, null);
								}
							} 
							break;
						case "8":
							if(domainDesignForm.getMaxVal() == null){
								errors.reject(CommonMessageConst.ERR_SYS_0025, new String[] { MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0043) }, null);
							}else if(domainDesignForm.getMinVal() == null){
								errors.reject(CommonMessageConst.ERR_SYS_0025, new String[] { MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0043) }, null);
							}else{
								try {
									maxValue = DateUtils.parse(domainDesignForm.getMaxVal(), pattern);
									minValue = DateUtils.parse(domainDesignForm.getMinVal(), pattern);
									defaultValue = DateUtils.parse(domainDesignForm.getDefaultValue(), pattern);

									if(DateUtils.compare(minValue, maxValue) == 1){
										errors.reject(CommonMessageConst.ERR_SYS_0050, new String[] { MessageUtils .getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0177), MessageUtils .getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0176) }, null);
									}

									if(DateUtils.compare(minValue, defaultValue) == 1 || DateUtils.compare(defaultValue, maxValue) == 1){
										errors.reject(DomainDesignMessageConst.SC_DOMAINDESIGN_0053, null);
									}
								} catch (ParseException e) {
									
								}
							}
							break;
						}
					} catch (ParseException e) {
						
					}
				}
			}
		}
	}
}
