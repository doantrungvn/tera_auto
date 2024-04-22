package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;

import org.terasoluna.qp.app.common.ultils.FunctionCommon;

public class AccountRuleDefinition implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long accountRuleDefinitionId;
	private String accountRuleDefinitionCode;
	private String accountRuleDefinitionName;
	private int rangeOfStringMinimum;
	private int rangeOfStringMaximum;
	private boolean charactersTypeUpper;
	private boolean charactersTypeLower;
	private boolean charactersTypeNumeric;
	private int generationsCount;
	private int lifeTime;
	private int loginContinuousFailureCount;
	private int accountLockTime;
	private boolean initialPassword;
	private boolean initialPasswordForceChange;
	private Long createdBy;
	private Timestamp createdDate;
	private Long updatedBy;
	private Timestamp updatedDate;
	
	public Long getAccountRuleDefinitionId() {
		return accountRuleDefinitionId;
	}
	public void setAccountRuleDefinitionId(Long accountRuleDefinitionId) {
		this.accountRuleDefinitionId = accountRuleDefinitionId;
	}
	public String getAccountRuleDefinitionCode() {
		return accountRuleDefinitionCode;
	}
	public void setAccountRuleDefinitionCode(String accountRuleDefinitionCode) {
		this.accountRuleDefinitionCode = accountRuleDefinitionCode;
	}
	public String getAccountRuleDefinitionName() {
		return accountRuleDefinitionName;
	}
	public void setAccountRuleDefinitionName(String accountRuleDefinitionName) {
		this.accountRuleDefinitionName = accountRuleDefinitionName;
	}
	public int getRangeOfStringMinimum() {
		return rangeOfStringMinimum;
	}
	public void setRangeOfStringMinimum(int rangeOfStringMinimum) {
		this.rangeOfStringMinimum = rangeOfStringMinimum;
	}
	public int getRangeOfStringMaximum() {
		return rangeOfStringMaximum;
	}
	public void setRangeOfStringMaximum(int rangeOfStringMaximum) {
		this.rangeOfStringMaximum = rangeOfStringMaximum;
	}
	public boolean isCharactersTypeUpper() {
		return charactersTypeUpper;
	}
	public void setCharactersTypeUpper(boolean charactersTypeUpper) {
		this.charactersTypeUpper = charactersTypeUpper;
	}
	public boolean isCharactersTypeLower() {
		return charactersTypeLower;
	}
	public void setCharactersTypeLower(boolean charactersTypeLower) {
		this.charactersTypeLower = charactersTypeLower;
	}
	public boolean isCharactersTypeNumeric() {
		return charactersTypeNumeric;
	}
	public void setCharactersTypeNumeric(boolean charactersTypeNumeric) {
		this.charactersTypeNumeric = charactersTypeNumeric;
	}
	public int getGenerationsCount() {
		return generationsCount;
	}
	public void setGenerationsCount(int generationsCount) {
		this.generationsCount = generationsCount;
	}
	public int getLifeTime() {
		return lifeTime;
	}
	public void setLifeTime(int lifeTime) {
		this.lifeTime = lifeTime;
	}
	public int getLoginContinuousFailureCount() {
		return loginContinuousFailureCount;
	}
	public void setLoginContinuousFailureCount(int loginContinuousFailureCount) {
		this.loginContinuousFailureCount = loginContinuousFailureCount;
	}
	public int getAccountLockTime() {
		return accountLockTime;
	}
	public void setAccountLockTime(int accountLockTime) {
		this.accountLockTime = accountLockTime;
	}
	public boolean isInitialPassword() {
		return initialPassword;
	}
	public void setInitialPassword(boolean initialPassword) {
		this.initialPassword = initialPassword;
	}
	public boolean isInitialPasswordForceChange() {
		return initialPasswordForceChange;
	}
	public void setInitialPasswordForceChange(boolean initialPasswordForceChange) {
		this.initialPasswordForceChange = initialPasswordForceChange;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Long getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Timestamp getUpdatedDate() {
		return updatedDate == null ? FunctionCommon.getCurrentTime() : updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
}
