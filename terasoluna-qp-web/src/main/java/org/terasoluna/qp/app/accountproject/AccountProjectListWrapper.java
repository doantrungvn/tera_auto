package org.terasoluna.qp.app.accountproject;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AccountProjectListWrapper implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long accountId;
	private Long projectId;
	private String projectName;
	private String projectCode;
	private List<AccountProjectForm> accProjectList = new ArrayList<AccountProjectForm>();
	private String status;
	private String remark;
	private Timestamp updatedDate;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
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

	public List<AccountProjectForm> getAccProjectList() {
		return accProjectList;
	}

	public void setAccProjectList(List<AccountProjectForm> accProjectList) {
		this.accProjectList = accProjectList;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
}
