package org.terasoluna.qp.app.licensemanagement;

import java.text.ParseException;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.ultils.DateUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.LicenseManagementMessageConst;

@Component
public class LicenseManagementValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return (LicenseManagementForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LicenseManagementForm licenseManagementForm = (LicenseManagementForm) target;
		try {
			if (FunctionCommon.isEmpty(licenseManagementForm.getLicenseFileName())) {
				String[] args =  {MessageUtils.getMessage(LicenseManagementMessageConst.SC_LICENSEMANAGEMENT_0015)};
				errors.reject(CommonMessageConst.ERR_SYS_0025, args , null);
			}
			
			if (FunctionCommon.isNotEmpty(licenseManagementForm.getStartDate()) && FunctionCommon.isNotEmpty(licenseManagementForm.getExpiredDate()) && DateUtils.parse(licenseManagementForm.getStartDate()).compareTo(DateUtils.parse(licenseManagementForm.getExpiredDate())) >= 0) {
				String[] args = { MessageUtils.getMessage(LicenseManagementMessageConst.SC_LICENSEMANAGEMENT_0009), MessageUtils.getMessage(LicenseManagementMessageConst.SC_LICENSEMANAGEMENT_0010) };
				errors.reject(CommonMessageConst.ERR_SYS_0050, args, null);
			}

			if (FunctionCommon.isNotEmpty(licenseManagementForm.getNum()) && (Integer.parseInt(licenseManagementForm.getNum()) < 0 || Integer.parseInt(licenseManagementForm.getNum()) > 1000)) {
				String[] args = { MessageUtils.getMessage(LicenseManagementMessageConst.SC_LICENSEMANAGEMENT_0010), LicenseManagementMessageConst.VALIDATION_MIN_VALUE, LicenseManagementMessageConst.VALIDATION_MAX_VALUE };
				errors.reject(CommonMessageConst.ERR_SYS_0024, args, null);
			}
			if (FunctionCommon.isNotEmpty(licenseManagementForm.getExpiredDate()) && DateUtils.parse(licenseManagementForm.getExpiredDate()).compareTo(FunctionCommon.getCurrentTime()) >= 0) {

			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}