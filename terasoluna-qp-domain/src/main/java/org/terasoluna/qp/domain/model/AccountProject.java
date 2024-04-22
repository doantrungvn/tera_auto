package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class AccountProject implements Serializable {

	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long projectId;
	private Long accountId;
	private boolean selected;
}
