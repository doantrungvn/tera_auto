package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class RolePermission implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long permissionId;
	private Long roleId;
	
	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
