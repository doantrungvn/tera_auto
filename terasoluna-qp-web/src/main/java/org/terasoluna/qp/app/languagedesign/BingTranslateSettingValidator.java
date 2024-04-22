package org.terasoluna.qp.app.languagedesign;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.AccountProfileMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;

@Component
public class BingTranslateSettingValidator implements Validator {

	@Inject
	Mapper beanMapper;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return (BingTranslateSettingForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		BingTranslateSettingForm form = (BingTranslateSettingForm) target;
		if (StringUtils.isBlank(form.getBingClientId())) {
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(AccountProfileMessageConst.BING_CLIENT_ID) }, null);
		}

		if (StringUtils.isEmpty(form.getBingClientSecret())) {
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(AccountProfileMessageConst.BING_CLIENT_SECRET) }, null);
		}
	}
}