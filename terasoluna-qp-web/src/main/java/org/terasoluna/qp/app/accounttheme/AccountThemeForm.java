package org.terasoluna.qp.app.accounttheme;

import java.io.Serializable;
import java.util.Map;

public class AccountThemeForm implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private Long accountThemeId;
	private Long accountId;
	private Map<String, String> mapTheme;

	
	
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
	public Map<String, String> getMapTheme() {
		return mapTheme;
	}
	public void setMapTheme(Map<String, String> mapTheme) {
		this.mapTheme = mapTheme;
	}
}
