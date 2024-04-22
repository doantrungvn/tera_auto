package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class BingInfor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
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

	/*public Integer getIntegerMaxTranslatedItem() {
		return Integer.parseInt(StringUtils.defaultString(maxTranslatedItem, "1"));
	}

	public Integer getIntegerNumberBatchForOneExucute() {
		return Integer.parseInt(StringUtils.defaultString(numberBatchForOneExucute, "1"));
	}

	public Integer getIntegerProxyPort() {
		return Integer.parseInt(StringUtils.defaultString(proxyPort, ""));
	}*/
}
