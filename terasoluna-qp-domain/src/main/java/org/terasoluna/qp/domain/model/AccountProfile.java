package org.terasoluna.qp.domain.model;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;

public class AccountProfile extends CommonModel {

	private static final long serialVersionUID = -1631590204184165216L;

	private static final String defaultUrlTestProxy =  "http://google.com/";
	private static final String defaultBatchJobTimeOut =  "2 hour";
	
	private Long accountId;

	private Long currentProjectId;

	private String integerFormat;

	private String floatFormat;

	private String currencyFormat;

	private String currencyCode;

	private String currencyCodePosition;
	
	private String dateFormat;

	private String dateTimeFormat;

	private String timeFormat;

	private String pagesizeValue;

	private String defaultLanguage;

	private String proxyHost;

	private String proxyPort;

	private String proxyUser;

	private String proxyPass;

	private String datamarketAccessUri;

	private String bingClientId;

	private String bingClientSecret;

	private String serviceUrl;

	private String arrayServiceUrl;

	private String arrayJsonObjectProperty;

	private String maxTranslatedItem;

	private String numberBatchForOneExucute;

	private Integer sessionTimeOut;

	private Integer proxyLevel;

	private Integer intervalReload;

	private Integer connectionFlg;

	private String namePattern = CommonMessageConst.PATTERN_FOR_NAME;

	private String nameMask = CommonMessageConst.NAME_INPUTMASK;

	private String codePattern = CommonMessageConst.PATTERN_FOR_CODE;

	private String codeMask = CommonMessageConst.CODE_INPUTMASK;

	private Integer nameMinLength = DbDomainConst.MIN_VAL_INPUT;

	private Integer nameMaxLength = DbDomainConst.MAX_LENGTH_OF_NAME;

	private Integer codeMinLength = DbDomainConst.MIN_VAL_INPUT;

	private Integer codeMaxLength = DbDomainConst.MAX_LENGTH_OF_CODE;

	private Integer sqlCodeMinLength = DbDomainConst.MIN_VAL_INPUT;

	private Integer sqlCodeMaxLength = DbDomainConst.MAX_LENGTH_OF_CODE;

	private Integer remarkMinLength = DbDomainConst.MIN_VAL_REMARK;

	private Integer remarkMaxLength = DbDomainConst.MAX_VAL_REMARK;

	private Integer maxNestedObject;

	private String urlScreenCapture;

	private String batchJobPath;

	private Integer maxJobNumber;

	private Integer maxLengthOracleDBMS;

	private Integer maxLengthPostgreDBMS;
	
	private String urlTestProxy;
	
	private String batchJobTimeOut;
	
	private Integer maxSizeUpload;
	
	private String licensePassword;
	
	private String licenseKey;
	
	private String urlTestInternet;

	public String getBatchJobTimeOut() {
		return batchJobTimeOut == null ? defaultBatchJobTimeOut : batchJobTimeOut;
	}

	public void setBatchJobTimeOut(String batchJobTimeOut) {
		this.batchJobTimeOut = batchJobTimeOut;
	}

	public String getUrlScreenCapture() {
		return urlScreenCapture;
	}

	public void setUrlScreenCapture(String urlScreenCapture) {
		this.urlScreenCapture = urlScreenCapture;
	}

	public String getBatchJobPath() {
		return batchJobPath;
	}

	public void setBatchJobPath(String batchJobPath) {
		this.batchJobPath = batchJobPath;
	}

	public Integer getMaxJobNumber() {
		return maxJobNumber;
	}

	public void setMaxJobNumber(Integer maxJobNumber) {
		this.maxJobNumber = maxJobNumber;
	}

	public String getProxyPass() {
		return proxyPass;
	}

	public void setProxyPass(String proxyPass) {
		this.proxyPass = proxyPass;
	}

	public String getDatamarketAccessUri() {
		return datamarketAccessUri;
	}

	public void setDatamarketAccessUri(String datamarketAccessUri) {
		this.datamarketAccessUri = datamarketAccessUri;
	}

	public String getArrayServiceUrl() {
		return arrayServiceUrl;
	}

	public void setArrayServiceUrl(String arrayServiceUrl) {
		this.arrayServiceUrl = arrayServiceUrl;
	}

	public String getArrayJsonObjectProperty() {
		return arrayJsonObjectProperty;
	}

	public void setArrayJsonObjectProperty(String arrayJsonObjectProperty) {
		this.arrayJsonObjectProperty = arrayJsonObjectProperty;
	}

	public String getMaxTranslatedItem() {
		return maxTranslatedItem;
	}

	public void setMaxTranslatedItem(String maxTranslatedItem) {
		this.maxTranslatedItem = maxTranslatedItem;
	}

	public String getNumberBatchForOneExucute() {
		return numberBatchForOneExucute;
	}

	public void setNumberBatchForOneExucute(String numberBatchForOneExucute) {
		this.numberBatchForOneExucute = numberBatchForOneExucute;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getSessionTimeOut() {
		return sessionTimeOut;
	}

	public void setSessionTimeOut(Integer sessionTimeOut) {
		this.sessionTimeOut = sessionTimeOut;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public String getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getCurrencyFormat() {
		return currencyFormat;
	}

	public void setCurrencyFormat(String currencyFormat) {
		this.currencyFormat = currencyFormat;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getIntegerFormat() {
		return integerFormat;
	}

	public void setIntegerFormat(String integerFormat) {
		this.integerFormat = integerFormat;
	}

	public String getFloatFormat() {
		return floatFormat;
	}

	public void setFloatFormat(String floatFormat) {
		this.floatFormat = floatFormat;
	}

	public String getDateFormat() {

		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getDateTimeFormat() {
		return dateTimeFormat;
	}

	public void setDateTimeFormat(String dateTimeFormat) {
		this.dateTimeFormat = dateTimeFormat;
	}

	public String getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}

	public String getPagesizeValue() {
		return pagesizeValue;
	}

	public void setPagesizeValue(String pagesizeValue) {
		this.pagesizeValue = pagesizeValue;
	}

	public String getDefaultLanguage() {
		return defaultLanguage;
	}

	public void setDefaultLanguage(String defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

	public Long getCurrentProjectId() {
		return currentProjectId;
	}

	public void setCurrentProjectId(Long currentProjectId) {
		this.currentProjectId = currentProjectId;
	}

	public String getProxyUser() {
		return proxyUser;
	}

	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}

	public String getBingClientId() {
		return bingClientId;
	}

	public void setBingClientId(String bingClientId) {
		this.bingClientId = bingClientId;
	}

	public String getBingClientSecret() {
		return bingClientSecret;
	}

	public void setBingClientSecret(String bingClientSecret) {
		this.bingClientSecret = bingClientSecret;
	}

	public Integer getIntervalReload() {
		return intervalReload;
	}

	public void setIntervalReload(Integer intervalReload) {
		this.intervalReload = intervalReload;
	}

	public String getNamePattern() {
		return namePattern;
	}

	public void setNamePattern(String namePattern) {
		this.namePattern = namePattern;
	}

	public String getCodePattern() {
		return codePattern;
	}

	public void setCodePattern(String codePattern) {
		this.codePattern = codePattern;
	}

	public Integer getNameMinLength() {
		return nameMinLength;
	}

	public void setNameMinLength(Integer nameMinLength) {
		this.nameMinLength = nameMinLength;
	}

	public Integer getNameMaxLength() {
		return nameMaxLength;
	}

	public void setNameMaxLength(Integer nameMaxLength) {
		this.nameMaxLength = nameMaxLength;
	}

	public Integer getCodeMinLength() {
		return codeMinLength;
	}

	public void setCodeMinLength(Integer codeMinLength) {
		this.codeMinLength = codeMinLength;
	}

	public Integer getCodeMaxLength() {
		return codeMaxLength;
	}

	public void setCodeMaxLength(Integer codeMaxLength) {
		this.codeMaxLength = codeMaxLength;
	}

	public Integer getSqlCodeMinLength() {
		return sqlCodeMinLength;
	}

	public void setSqlCodeMinLength(Integer codeMinLength) {
		this.sqlCodeMinLength = codeMinLength;
	}

	public Integer getSqlCodeMaxLength() {
		return sqlCodeMaxLength;
	}

	public Integer getSqlCodeMaxLengthByDbType(String dataType) {
		
		if (DataTypeUtils.equals(DbDomainConst.DatabaseType.ORACLE, dataType)) {
			return this.getMaxLengthOracleDBMS();
		} else if (DataTypeUtils.equals(DbDomainConst.DatabaseType.PostgreSQL, dataType)) {
			return this.getMaxLengthPostgreDBMS();
		}
		return new Integer(0);
	}
	
	public void setSqlCodeMaxLength(Integer codeMaxLength) {
		this.sqlCodeMaxLength = codeMaxLength;
	}

	public Integer getRemarkMinLength() {
		return remarkMinLength;
	}

	public void setRemarkMinLength(Integer remarkMinLength) {
		this.remarkMinLength = remarkMinLength;
	}

	public Integer getRemarkMaxLength() {
		return remarkMaxLength;
	}

	public void setRemarkMaxLength(Integer remarkMaxLength) {
		this.remarkMaxLength = remarkMaxLength;
	}

	public String getNameMask() {
		return nameMask;
	}

	public void setNameMask(String nameMask) {
		this.nameMask = nameMask;
	}

	public String getCodeMask() {
		return codeMask;
	}

	public void setCodeMask(String codeMask) {
		this.codeMask = codeMask;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public Integer getProxyLevel() {
		return proxyLevel;
	}

	public void setProxyLevel(Integer proxyLevel) {
		this.proxyLevel = proxyLevel;
	}

	public Integer getConnectionFlg() {
		return connectionFlg == null ? DbDomainConst.ConnectionSetting.MANUAL_SETTING : connectionFlg;
	}

	public void setConnectionFlg(Integer connectionFlg) {
		this.connectionFlg = connectionFlg;
	}

	public Integer getMaxNestedObject() {
		return maxNestedObject;
	}

	public void setMaxNestedObject(Integer maxNestedObject) {
		this.maxNestedObject = maxNestedObject;
	}

	public Integer getMaxLengthOracleDBMS() {
		return maxLengthOracleDBMS;
	}

	public void setMaxLengthOracleDBMS(Integer maxLengthOracleDBMS) {
		this.maxLengthOracleDBMS = maxLengthOracleDBMS;
	}

	public Integer getMaxLengthPostgreDBMS() {
		return maxLengthPostgreDBMS;
	}

	public void setMaxLengthPostgreDBMS(Integer maxLengthPostgreDBMS) {
		this.maxLengthPostgreDBMS = maxLengthPostgreDBMS;
	}

	public String getCurrencyCodePosition() {
		return currencyCodePosition;
	}

	public void setCurrencyCodePosition(String currencyCodePosition) {
		this.currencyCodePosition = currencyCodePosition;
	}

	public String getUrlTestProxy() {
		return StringUtils.isBlank(urlTestProxy) ? defaultUrlTestProxy : urlTestProxy;
	}

	public void setUrlTestProxy(String urlTestProxy) {
		this.urlTestProxy = urlTestProxy;
	}

	public Integer getMaxSizeUpload() {
		return maxSizeUpload;
	}

	public void setMaxSizeUpload(Integer maxSizeUpload) {
		this.maxSizeUpload = maxSizeUpload;
	}

	public String getLicensePassword() {
		return licensePassword;
	}

	public void setLicensePassword(String licensePassword) {
		this.licensePassword = licensePassword;
	}

	public String getLicenseKey() {
		return licenseKey;
	}

	public void setLicenseKey(String licenseKey) {
		this.licenseKey = licenseKey;
	}

	public String getUrlTestInternet() {
		return urlTestInternet;
	}

	public void setUrlTestInternet(String urlTestInternet) {
		this.urlTestInternet = urlTestInternet;
	}
	
}
