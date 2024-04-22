package org.terasoluna.qp.app.languagedesign;


public class BingTranslateSettingForm {
	private String bingClientId;

	private String bingClientSecret;
	
	private Boolean testFlag;

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

	public Boolean getTestFlag() {
		return testFlag;
	}

	public void setTestFlag(Boolean testFlag) {
		this.testFlag = testFlag;
	}
}
