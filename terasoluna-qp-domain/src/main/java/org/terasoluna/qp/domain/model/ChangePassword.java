package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class ChangePassword implements Serializable{

	private static final long serialVersionUID = -5166829390485925483L;
	private String userName;
	private String currentPassword;
	private String newPassword;
	private String confirmNewPassword;
	private long accountId;
	private long accountRuleDefinitionId;
	private boolean redirectFromLogin;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String password) {
		this.currentPassword = password;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public boolean isInvalidPassword(){
		return newPassword.equals(confirmNewPassword);
	}
	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}
	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public long getAccountRuleDefinitionId() {
		return accountRuleDefinitionId;
	}
	public void setAccountRuleDefinitionId(long accountRuleDefinitionId) {
		this.accountRuleDefinitionId = accountRuleDefinitionId;
	}
	public boolean isRedirectFromLogin() {
		return redirectFromLogin;
	}
	public void setRedirectFromLogin(boolean redirectFromLogin) {
		this.redirectFromLogin = redirectFromLogin;
	}
}
