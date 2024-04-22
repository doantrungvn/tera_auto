package org.terasoluna.qp.app.accountrole;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AccountRoleListWrapper implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private List<AccountRoleForm> accRoleList = new ArrayList<AccountRoleForm>();
	private Long accountId;
	private String username;
	private String moduleCd;
	private Timestamp updatedDate;
	public List<AccountRoleForm> getAccRoleList() {
		return accRoleList;
	}
	public void setAccRoleList(List<AccountRoleForm> accRoleList) {
		this.accRoleList = accRoleList;
	}
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
	public String getModuleCd() {
		return moduleCd;
	}
	public void setModuleCd(String moduleCd) {
		this.moduleCd = moduleCd;
	}
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	

}
