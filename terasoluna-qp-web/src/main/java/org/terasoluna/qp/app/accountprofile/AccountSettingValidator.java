package org.terasoluna.qp.app.accountprofile;

import java.io.IOException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.AccountProfileMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.translator.microsoft.ProxyAuthenticator;
import org.terasoluna.qp.app.message.translator.microsoft.ProxyCommon;
import org.terasoluna.qp.domain.service.common.SystemService;

@Component
public class AccountSettingValidator implements Validator {

	@Inject
	Mapper beanMapper;

	@Inject
	SystemService systemService;

	@Override
	public boolean supports(Class<?> clazz) {
		return (SettingForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		
		SettingForm settingForm = (SettingForm) target;
		
		if (settingForm.getSessionTimeOut() != null) {
			if (settingForm.getSessionTimeOut() != null && settingForm.getSessionTimeOut() < Short.MIN_VALUE || settingForm.getSessionTimeOut() > Short.MAX_VALUE) {
				errors.reject(CommonMessageConst.ERR_SYS_0027, new Object[] { MessageUtils.getMessage(AccountProfileMessageConst.SC_ACCOUNTPROFILE_0022) }, null);
			}
		}
		
		if (settingForm.getIntervalReload() != null) {
			if (settingForm.getIntervalReload() < Short.MIN_VALUE || settingForm.getIntervalReload() > Short.MAX_VALUE) {
				errors.reject(CommonMessageConst.ERR_SYS_0027, new Object[] { MessageUtils.getMessage(AccountProfileMessageConst.SC_ACCOUNTPROFILE_0037) }, null);
			}
		}

		if (Boolean.FALSE.equals(settingForm.getTestBingFlag()) && !Boolean.FALSE.equals(settingForm.getValidateProxy())) {
			this.validateProxyHost(settingForm, errors);
		}

		if (Boolean.TRUE.equals(settingForm.getTestBingFlag())) { 
			if (StringUtils.isBlank(settingForm.getBingClientId())) {
				errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(AccountProfileMessageConst.BING_CLIENT_ID) }, null);
			}
	
			if (StringUtils.isEmpty(settingForm.getBingClientSecret())) {
				errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(AccountProfileMessageConst.BING_CLIENT_SECRET) }, null);
			}
		}
		
		// Add validate only when modify data in System setting screen (batch job)
		if (settingForm.getUrlScreenCapture() != null) {
			this.validateBatchSetting(settingForm, errors);
		}
		
		if (settingForm.getMaxSizeUpload() != null) {
			if (settingForm.getMaxSizeUpload() < Short.MIN_VALUE || settingForm.getMaxSizeUpload() > Short.MAX_VALUE) {
				errors.reject(CommonMessageConst.ERR_SYS_0027, new Object[] { MessageUtils.getMessage(AccountProfileMessageConst.SC_ACCOUNTPROFILE_0052) }, null);
			}
		}
		
	}

	private void validateProxyHost(SettingForm settingForm, Errors errors) {
		if (DbDomainConst.ProxySetting.MANUAL_PROXY.equals(settingForm.getProxyLevel())) {
			boolean checkValidProxy = true;

			if (Boolean.FALSE.equals(settingForm.getTestBingFlag()) && StringUtils.isBlank(settingForm.getUrlTestProxy())) {
				errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] {
					"["+MessageUtils.getMessage(AccountProfileMessageConst.SC_ACCOUNTPROFILE_0035) + "] - " + MessageUtils.getMessage(AccountProfileMessageConst.SC_ACCOUNTPROFILE_0044) },
					null);
				checkValidProxy = false;
			}
			
			if (StringUtils.isBlank(settingForm.getProxyHost())) {
				errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(AccountProfileMessageConst.ACCOUNTPROFILE_PROXY_HOST) }, null);
				checkValidProxy = false;
			}

			if (StringUtils.isBlank(settingForm.getProxyPort())) {
				errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(AccountProfileMessageConst.ACCOUNTPROFILE_PROXY_PORT) }, null);
				checkValidProxy = false;
			}

			/*if (Boolean.TRUE.equals(settingForm.getTestBingFlag()) && (StringUtils.isBlank(settingForm.getBingClientId()) || StringUtils.isEmpty(settingForm.getBingClientSecret()))) {
				checkValidProxy = false;
			}*/
			
			if (checkValidProxy) {

				HttpURLConnection httpURLConnection = null;

				URL url;
				try {
					/*if (Boolean.FALSE.equals(settingForm.getTestBingFlag())) {*/
						url = new URL(settingForm.getUrlTestProxy());
					/*} else {
						url = new URL(systemService.getDefaultProfile().getUrlTestProxy());
					}*/

					String proxyUser = settingForm.getProxyUser();
					String proxyPass = settingForm.getProxyPass();
					Proxy proxy = ProxyCommon.initProxySystemProxy(settingForm.getProxyHost(), Integer.parseInt(settingForm.getProxyPort()));

					httpURLConnection = (HttpURLConnection) url.openConnection(proxy);

					httpURLConnection.setInstanceFollowRedirects(true);
					httpURLConnection.setRequestMethod("HEAD");

					if (StringUtils.isNotBlank(proxyUser)) {
						String uname_pwd = proxyUser + ":" + proxyPass;
						String authString = "Basic " + new sun.misc.BASE64Encoder().encode(uname_pwd.getBytes());
						httpURLConnection.setRequestProperty("Proxy-Authorization", authString);
						httpURLConnection.addRequestProperty("Https-Proxy-Authorization", authString);
						Authenticator.setDefault(new ProxyAuthenticator(proxyUser, StringUtils.defaultString(proxyPass, StringUtils.EMPTY)));
					}

					httpURLConnection.connect();

					if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
						errors.reject(AccountProfileMessageConst.ERR_ACCOUNTPROFILE_0003, new Object[] { MessageUtils.getMessage(AccountProfileMessageConst.ACCOUNTPROFILE_PROXY_PORT) }, null);
					}

				} catch (UnknownHostException e) { // unknown host
					errors.reject(AccountProfileMessageConst.ERR_ACCOUNTPROFILE_0001, new Object[] { settingForm.getProxyHost(), settingForm.getProxyPort() }, null);
				} catch (IOException e) { // service probably not running
					errors.reject(AccountProfileMessageConst.ERR_ACCOUNTPROFILE_0002, new Object[] { settingForm.getProxyHost(), settingForm.getProxyPort() }, null);
				} catch (IllegalArgumentException e) {
					errors.reject(CommonMessageConst.ERR_SYS_0018, new Object[] { MessageUtils.getMessage(AccountProfileMessageConst.ACCOUNTPROFILE_PROXY_PORT) }, null);
				}
			}
		}
	}

	/**
	 * Validate batch setting
	 * 
	 * @param settingForm
	 * @param errors
	 */
	private void validateBatchSetting(SettingForm settingForm, Errors errors) {
		// Validate for URL (empty and valid URL)
		if (StringUtils.isEmpty(settingForm.getUrlScreenCapture())) {
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(AccountProfileMessageConst.SC_ACCOUNTPROFILE_0044) }, null);
		} else if (!isValidURL(settingForm.getUrlScreenCapture())) {
			errors.reject(CommonMessageConst.ERR_SYS_0060, new Object[] { MessageUtils.getMessage(AccountProfileMessageConst.SC_ACCOUNTPROFILE_0044) }, null);
		}

		// Validate for Batch job path when user select absolutely directory
		if (settingForm.getBatchDirectoryType() == DbDomainConst.BatchDirectoryType.ABSOLUTE && StringUtils.isBlank(settingForm.getBatchJobPath())) {
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(AccountProfileMessageConst.SC_ACCOUNTPROFILE_0047) }, null);
		}

		// Validate for Max job number
		if (StringUtils.isBlank(settingForm.getMaxJobNumber())) {
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(AccountProfileMessageConst.SC_ACCOUNTPROFILE_0045) }, null);
		}

	}

	/**
	 * Check if input URL is a valid URL
	 * 
	 * @param pUrl
	 * @return
	 */
	private boolean isValidURL(String pUrl) {
		URL u = null;
		try {
			u = new URL(pUrl);
		} catch (MalformedURLException e) {
			return false;
		}
		try {
			u.toURI();
		} catch (URISyntaxException e) {
			return false;
		}
		return true;
	}
	public static void main(String[] args) throws Exception{
		URL url = new URL("https://www.google.com.vn/?gws_rd=cr&ei=uKVjV_jfNsfd0ATl6pjwCg");
		Proxy p = new Proxy(Proxy.Type.HTTP,  new InetSocketAddress("hn-proxy.intra.nttdata.com.vn", 8080));
		URLConnection conn = url.openConnection(p);
		conn.connect();
	}
}