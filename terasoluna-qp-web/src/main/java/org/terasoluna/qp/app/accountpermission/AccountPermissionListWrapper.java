package org.terasoluna.qp.app.accountpermission;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.terasoluna.qp.app.accountrole.AccountRoleForm;

public class AccountPermissionListWrapper implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<AccountPermissionForm> accPermissionList = new ArrayList<AccountPermissionForm>();
	private List<AccountRoleForm> accRoleList = new ArrayList<AccountRoleForm>();
	private Long accountId;
	private String username;
	private String moduleCd;
	private Timestamp updatedDate;

	public String getModuleCd() {
		return moduleCd;
	}

	public void setModuleCd(String moduleCd) {
		this.moduleCd = moduleCd;
	}

	public List<AccountPermissionForm> getAccPermissionList() {
		return accPermissionList;
	}

	public void setAccPermissionList(
			List<AccountPermissionForm> accPermissionList) {
		this.accPermissionList = accPermissionList;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public List<AccountRoleForm> getAccRoleList() {
		return accRoleList;
	}

	public void setAccRoleList(List<AccountRoleForm> accRoleList) {
		this.accRoleList = accRoleList;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

}
