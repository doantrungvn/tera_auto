package org.terasoluna.qp.app.accountpermission;

import java.io.Serializable;

public class AccountPermissionForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long accountId;
	private Long permissionId;
	private String permissionName;
	private String permissionCode;
	private Long moduleId;
	private String moduleCode;
	private int sortKey;
	private String remark;
	private String actionPath;
	private boolean selected;
	private String rolesName;
	private boolean hiddenHaveSameRole;
	
	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public int getSortKey() {
		return sortKey;
	}

	public void setSortKey(int sortKey) {
		this.sortKey = sortKey;
	}


	public String getActionPath() {
		return actionPath;
	}

	public void setActionPath(String actionPath) {
		this.actionPath = actionPath;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
		if (this.accountId != null)
			this.selected = true;
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public String getRolesName() {
	    return rolesName;
    }

	public void setRolesName(String rolesName) {
	    this.rolesName = rolesName;
    }

	public boolean isHiddenHaveSameRole() {
	    return hiddenHaveSameRole;
    }

	public void setHiddenHaveSameRole(boolean hiddenHaveSameRole) {
	    this.hiddenHaveSameRole = hiddenHaveSameRole;
    }

	public String getRemark() {
	    return remark;
    }

	public void setRemark(String remark) {
	    this.remark = remark;
    }

}
