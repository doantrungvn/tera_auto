package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class AccountTheme implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long accountThemeId;
	private Long accountId;
	private String code;
	private String value;
	public Long getAccountThemeId() {
		return accountThemeId;
	}
	public void setAccountThemeId(Long accountThemeId) {
		this.accountThemeId = accountThemeId;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	

}
