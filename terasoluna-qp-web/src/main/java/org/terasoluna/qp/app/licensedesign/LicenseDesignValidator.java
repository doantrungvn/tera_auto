package org.terasoluna.qp.app.licensedesign;

import java.text.ParseException;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.ultils.DateUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.LicenseDesignMessageConst;
import org.terasoluna.qp.app.message.LicenseManagementMessageConst;
import org.terasoluna.qp.domain.service.common.SystemService;

@Component
public class LicenseDesignValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return (LicenseDesignForm.class).isAssignableFrom(clazz);
	}
	
	@Inject
	SystemService systemService;

	@Override
	public void validate(Object target, Errors errors) {
		LicenseDesignForm licenseDesignForm = (LicenseDesignForm) target;
		try {
			if (FunctionCommon.isNotEmpty(licenseDesignForm.getStartDate()) && FunctionCommon.isNotEmpty(licenseDesignForm.getExpiredDate()) && DateUtils.parse(licenseDesignForm.getStartDate()).compareTo(DateUtils.parse(licenseDesignForm.getExpiredDate())) >= 0) {
				String[] args = { MessageUtils.getMessage(LicenseDesignMessageConst.SC_LICENSEDESIGN_0009), MessageUtils.getMessage(LicenseDesignMessageConst.SC_LICENSEDESIGN_0010) };
				errors.reject(CommonMessageConst.ERR_SYS_0050, args, null);
			}

			if (FunctionCommon.isNotEmpty(licenseDesignForm.getNum()) && (Integer.parseInt(licenseDesignForm.getNum()) <= 0 || Integer.parseInt(licenseDesignForm.getNum()) > 1000)) {
				String[] args = { MessageUtils.getMessage(LicenseDesignMessageConst.SC_LICENSEDESIGN_0007), LicenseDesignMessageConst.VALIDATION_MIN_VALUE, LicenseDesignMessageConst.VALIDATION_MAX_VALUE };
				errors.reject(CommonMessageConst.ERR_SYS_0024, args, null);
			}

			if (FunctionCommon.isNotEmpty(licenseDesignForm.getExpiredDate()) && DateUtils.parse(licenseDesignForm.getExpiredDate()).compareTo(FunctionCommon.getCurrentTime()) <= 0) {
				String[] args = { MessageUtils.getMessage(LicenseDesignMessageConst.SC_LICENSEDESIGN_0010) };
				errors.reject(CommonMessageConst.ERR_SYS_0049, args, null);
			}

			String email = ((LicenseDesignForm) target).getEmail();
			ValidationUtils.validateEmail(email, errors, LicenseManagementMessageConst.SC_LICENSEMANAGEMENT_0005);

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}