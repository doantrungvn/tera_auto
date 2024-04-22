package org.terasoluna.qp.app.accountprofile;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

public class PasswordForm implements Serializable {

	private static final long serialVersionUID = 4412838296075890914L;
	private String userName;
	
	@NotEmpty(message = "sc.accountprofile.0005")
//	@Size(min = 6,max = 200,message = "sc.accountprofile.0005;6;200")
	private String currentPassword;
	
	@NotEmpty(message = "sc.accountprofile.0006")
//	@Size(min = 6,max = 200,message = "sc.accountprofile.0006;6;200")
	private String newPassword;
	
	@NotEmpty(message = "sc.accountprofile.0007")
//	@Size(min = 6,max = 200,message = "sc.accountprofile.0007;6;200")
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
