package org.terasoluna.qp.app.accountprofile;

import java.io.Serializable;

import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.validation.QpSize;

public class SettingForm implements Serializable {

	private static final long serialVersionUID = -6340938855084312325L;

	private String accountId = null;

	private String dateFormat = null;

	private String dateTimeFormat = null;

	private String timeFormat = null;

	private String integerFormat = null;

	private String floatFormat = null;

	private String currencyFormat = null;

	private String currencyCode = null;

	private String currencyCodePosition = null;

	private String defaultLanguage = null;

	private Integer sessionTimeOut;

	private Integer proxyLevel;

	@QpSize(max = DbDomainConst.MAX_VAL_OTHER, message = "sc.accountprofile.0024" + ";0;" + DbDomainConst.MAX_VAL_OTHER)
	private String proxyHost;

	/*@QpMin(value = "0", message = "sc.accountprofile.0025" + ";0")
	@QpMax(value = "65536", message = "sc.accountprofile.0025" + ";65536")*/
	private String proxyPort;

	@QpSize(max = DbDomainConst.MAX_VAL_OTHER, message = "sc.accountprofile.0026" + ";0;" + DbDomainConst.MAX_VAL_OTHER)
	private String proxyUser;

	@QpSize(max = DbDomainConst.MAX_VAL_OTHER, message = "sc.accountprofile.0027" + ";0;" + DbDomainConst.MAX_VAL_OTHER)
	private String proxyPass;

	@QpSize(max = DbDomainConst.MAX_VAL_OTHER, message = "sc.accountprofile.0029" + ";0;" + DbDomainConst.MAX_VAL_OTHER)
	private String bingClientId;

	@QpSize(max = DbDomainConst.MAX_VAL_OTHER, message = "sc.accountprofile.0030" + ";0;" + DbDomainConst.MAX_VAL_OTHER)
	private String bingClientSecret;

	private Integer intervalReload;

	private Integer connectionFlg;

	private Integer batchDirectoryType;

	@QpSize(max = DbDomainConst.MAX_VAL_OTHER, message = "sc.accountprofile.0044" + ";0;" + DbDomainConst.MAX_VAL_OTHER)
	private String urlScreenCapture;

	@QpSize(max = DbDomainConst.MAX_VAL_OTHER, message = "sc.accountprofile.0047" + ";0;" + DbDomainConst.MAX_VAL_OTHER)
	private String batchJobPath;

	private String maxJobNumber;

	private Integer maxSizeUpload;

	@QpSize(max = DbDomainConst.MAX_VAL_OTHER, message = "sc.accountprofile.0044" + ";0;" + DbDomainConst.MAX_VAL_OTHER)
	private String urlTestProxy;

	private Boolean testBingFlag;

	private Boolean testInternetFlag;
	private Boolean validateProxy;

	public String getCurrencyCodePosition() {
		return currencyCodePosition;
	}

	public void setCurrencyCodePosition(String currencyCodePosition) {
		this.currencyCodePosition = currencyCodePosition;
	}

	public String getDefaultLanguage() {
		return defaultLanguage;
	}

	public void setDefaultLanguage(String defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
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

	public Integer getProxyLevel() {
		return proxyLevel;
	}

	public void setProxyLevel(Integer proxyLevel) {
		this.proxyLevel = proxyLevel;
	}

	public Integer getIntervalReload() {
		return intervalReload;
	}

	public void setIntervalReload(Integer intervalReload) {
		this.intervalReload = intervalReload;
	}

	public String getProxyPass() {
		return proxyPass;
	}

	public void setProxyPass(String proxyPass) {
		this.proxyPass = proxyPass;
	}

	public Integer getConnectionFlg() {
		return connectionFlg;
	}

	public void setConnectionFlg(Integer connectionFlg) {
		this.connectionFlg = connectionFlg;
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

	public String getMaxJobNumber() {
		return maxJobNumber;
	}

	public void setMaxJobNumber(String maxJobNumber) {
		this.maxJobNumber = maxJobNumber;
	}

	public int getBatchDirectoryType() {
		return batchDirectoryType;
	}

	public void setBatchDirectoryType(int batchDirectoryType) {
		this.batchDirectoryType = batchDirectoryType;
	}

	public Integer getMaxSizeUpload() {
		return maxSizeUpload;
	}

	public void setMaxSizeUpload(Integer maxSizeUpload) {
		this.maxSizeUpload = maxSizeUpload;
	}

	public String getUrlTestProxy() {
		return urlTestProxy;
	}

	public void setUrlTestProxy(String urlTestProxy) {
		this.urlTestProxy = urlTestProxy;
	}

	public Boolean getTestBingFlag() {
		return testBingFlag;
	}

	public void setTestBingFlag(Boolean testBingFlag) {
		this.testBingFlag = testBingFlag;
	}

	public Boolean getTestInternetFlag() {
		return testInternetFlag;
	}

	public void setTestInternetFlag(Boolean testInternetFlag) {
		this.testInternetFlag = testInternetFlag;
	}

	public Boolean getValidateProxy() {
		return validateProxy;
	}

	public void setValidateProxy(Boolean validateProxy) {
		this.validateProxy = validateProxy;
	}
}
