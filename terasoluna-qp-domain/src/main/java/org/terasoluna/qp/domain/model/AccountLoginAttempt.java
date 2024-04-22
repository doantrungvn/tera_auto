package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class AccountLoginAttempt implements Serializable {
	private static final long serialVersionUID = 1L;
	private long accountLoginAttemptId;
	private long accountId;
	private int generationsCount;
	private int loginContinuousFailureCount;
	private long createdBy;
	private Timestamp createdDate;
	private long updatedBy;
	private Timestamp updatedDate;
	
	public long getAccountLoginAttemptId() {
		return accountLoginAttemptId;
	}
	public void setAccountLoginAttemptId(long accountLoginAttemptId) {
		this.accountLoginAttemptId = accountLoginAttemptId;
	}
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public int getGenerationsCount() {
		return generationsCount;
	}
	public void setGenerationsCount(int generationsCount) {
		this.generationsCount = generationsCount;
	}
	public int getLoginContinuousFailureCount() {
		return loginContinuousFailureCount;
	}
	public void setLoginContinuousFailureCount(int loginContinuousFailureCount) {
		this.loginContinuousFailureCount = loginContinuousFailureCount;
	}
	public long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public long getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(long updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
}
