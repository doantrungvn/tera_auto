package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class UserRoles implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String roleCd;
	private String roleName;
	private String status;
	private String remark;
	
	private String companyCode;
	
	private Long userRoleId;
	
	private int roleId;
	
	private Long accountId;
	
	private String deleteFlag;
	
	private Module module;
	private String moduleName;
	private String username;
	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}
	
	public String getCompanyCode() {
		return companyCode;
	}
	
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	public Long getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}
	
	public int getRoleId() {
		return roleId;
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	public Long getAcccountId() {
		return accountId;
	}
	
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	public String getDeleteFlag() {
		return deleteFlag;
	}
	
	public void setDeleteFLag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getRoleCd() {
		return roleCd;
	}

	public void setRoleCd(String roleCd) {
		this.roleCd = roleCd;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


}
