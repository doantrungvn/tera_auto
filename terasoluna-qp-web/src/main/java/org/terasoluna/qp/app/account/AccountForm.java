package org.terasoluna.qp.app.account;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;
import org.terasoluna.qp.app.accountproject.AccountProjectForm;
import org.terasoluna.qp.app.accountrole.AccountRoleForm;
import org.terasoluna.qp.app.common.validation.QpCodeSize;
public class AccountForm implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long accountId;
	
	@NotEmpty(message = "sc.account.0002")
	@QpCodeSize(message = "sc.account.0002")
	//@QpCodePattern(message= "sc.account.0002")
	private String username;

	private boolean accountNonLocked = true;
	
//	@NotEmpty(message = "sc.account.0003")
//	@Size(min = 6,max = 200,message = "sc.account.0003;6;200")
	private String password;

//	@NotEmpty(message= "sc.account.0004")
//	@Size(min = 6,max = 200,message = "sc.account.0004;6;200")
	private String confirmPassword;
	
	private boolean accountNonExpired = true;
	
	private boolean credentialsNonExpired = true;
	
	private boolean initialPassword = false;
	
	private String moduleCd;
	
	private Long createdBy;
	
	private Timestamp createdDate;
	
	private Long updatedBy;
	
	private Timestamp updatedDate;
	
	private boolean selectedChangePass;
	
	private List<AccountProjectForm> accProjectList = new ArrayList<AccountProjectForm>();
	
	private List<AccountRoleForm> accRoleList = new ArrayList<AccountRoleForm>();
	
	private String mode;
	
	private Long accountRuleDefinitionId;
	
	private String accountRuleDefinitionIdAutocomplete;
	
	public Long getAccountId() {
	return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getModuleCd() {
	    return moduleCd;
    }

	public void setModuleCd(String moduleCd) {
	    this.moduleCd = moduleCd;
    }

	public boolean isSelectedChangePass() {
	    return selectedChangePass;
    }

	public void setSelectedChangePass(boolean selectedChangePass) {
	    this.selectedChangePass = selectedChangePass;
    }

	public List<AccountProjectForm> getAccProjectList() {
		return accProjectList;
	}

	public void setAccProjectList(List<AccountProjectForm> accProjectList) {
		this.accProjectList = accProjectList;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public List<AccountRoleForm> getAccRoleList() {
		return accRoleList;
	}
	
	public void setAccRoleList(List<AccountRoleForm> accRoleList) {
		this.accRoleList = accRoleList;
	}

	public Long getAccountRuleDefinitionId() {
		return accountRuleDefinitionId;
	}

	public void setAccountRuleDefinitionId(Long accountRuleDefinitionId) {
		this.accountRuleDefinitionId = accountRuleDefinitionId;
	}

	public String getAccountRuleDefinitionIdAutocomplete() {
		return accountRuleDefinitionIdAutocomplete;
	}

	public void setAccountRuleDefinitionIdAutocomplete(
			String accountRuleDefinitionIdAutocomplete) {
		this.accountRuleDefinitionIdAutocomplete = accountRuleDefinitionIdAutocomplete;
	}

	public boolean isInitialPassword() {
		return initialPassword;
	}

	public void setInitialPassword(boolean initialPassword) {
		this.initialPassword = initialPassword;
	}
	
	
}
