package org.terasoluna.qp.domain.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

public class Account extends CommonModel {
	private static final long serialVersionUID = 1L;

	private Long accountId;

	private String username;

	private String password;

	private boolean accountNonExpired;

	private boolean accountNonLocked;

	private boolean credentialsNonExpired;
	
	/**
	 * QuyND add force change password
	 */
	private boolean forceChangePassword;

	private List<UserRoles> roles;

	private List<? extends GrantedAuthority> authorities;

	private List<Role> lstRole = new ArrayList<Role>();

	private List<Permission> lstPermission = new ArrayList<Permission>();

	private List<String> lstModuleCode = new ArrayList<String>();

	private List<Project> lstProject = new ArrayList<Project>();

	private boolean selectedChangePass;

	private Long accountRuleDefinitionId;

	private String accountRuleDefinitionIdAutocomplete;

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Account() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public List<UserRoles> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRoles> roles) {
		this.roles = roles;
	}

	public List<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public List<Role> getLstRole() {
		return lstRole;
	}

	public void setLstRole(List<Role> lstRole) {
		this.lstRole = lstRole;
	}

	public List<Permission> getLstPermission() {
		return lstPermission;
	}

	public void setLstPermission(List<Permission> lstPermission) {
		this.lstPermission = lstPermission;
	}

	public List<String> getLstModuleCode() {
		return lstModuleCode;
	}

	public void setLstModuleCode(List<String> lstModuleCode) {
		this.lstModuleCode = lstModuleCode;
	}

	public boolean isSelectedChangePass() {
		return selectedChangePass;
	}

	public void setSelectedChangePass(boolean selectedChangePass) {
		this.selectedChangePass = selectedChangePass;
	}

	public List<Project> getLstProject() {
		return lstProject;
	}

	public void setLstProject(List<Project> lstProject) {
		this.lstProject = lstProject;
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

	public void setAccountRuleDefinitionIdAutocomplete(String accountRuleDefinitionIdAutocomplete) {
		this.accountRuleDefinitionIdAutocomplete = accountRuleDefinitionIdAutocomplete;
	}

	public boolean isForceChangePassword() {
		return forceChangePassword;
	}

	public void setForceChangePassword(boolean forceChangePassword) {
		this.forceChangePassword = forceChangePassword;
	}
	
}
