package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class AccountPermission implements Serializable {

	public Long getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long permissionId;
	private Long accountId;
}
