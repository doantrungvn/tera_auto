package org.terasoluna.qp.app.accountruledefinition;

import java.io.Serializable;
import java.sql.Timestamp;

import org.hibernate.validator.constraints.NotEmpty;
import org.terasoluna.qp.app.common.validation.QpCodePattern;
import org.terasoluna.qp.app.common.validation.QpCodeSize;
import org.terasoluna.qp.app.common.validation.QpNamePattern;
import org.terasoluna.qp.app.common.validation.QpNameSize;
import org.terasoluna.qp.app.message.AccountRuleDefinitionMessageConst;

public class AccountRuleDefinitionForm implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long accountRuleDefinitionId;
	@NotEmpty(message = AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0005)
	@QpNameSize(message = AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0005)
	@QpNamePattern(message = AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0005)
	private String accountRuleDefinitionName;
	@NotEmpty(message = AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0004)
	@QpCodeSize(message = AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0004)
	@QpCodePattern(message = AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0004)
	private String accountRuleDefinitionCode;
	private boolean rangeOfStringCheckbox;
	private String rangeOfStringMinimum;
	private String rangeOfStringMaximum;
	private boolean charactersTypeCheckbox;
	private boolean charactersTypeUpper;
	private boolean charactersTypeLower;
	private boolean charactersTypeNumeric;
	private boolean generationsCountCheckbox;
	private String generationsCount;
	private boolean lifeTimeCheckbox;
	private String lifeTime;
	private boolean loginContinuousFailureCountCheckbox;
	private String loginContinuousFailureCount;
	private boolean accountLockTimeCheckbox;
	private String accountLockTime;
	private boolean initialPassword;
	private boolean initialPasswordForceChange;
	private Long createdBy;
	private Timestamp createdDate;
	private Long updatedBy;
	private Timestamp updatedDate;
	private String mode;
	
	public Long getAccountRuleDefinitionId() {
		return accountRuleDefinitionId;
	}
	public void setAccountRuleDefinitionId(Long accountRuleDefinitionId) {
		this.accountRuleDefinitionId = accountRuleDefinitionId;
	}
	public String getAccountRuleDefinitionName() {
		return accountRuleDefinitionName;
	}
	public void setAccountRuleDefinitionName(String accountRuleDefinitionName) {
		this.accountRuleDefinitionName = accountRuleDefinitionName;
	}
	public String getAccountRuleDefinitionCode() {
		return accountRuleDefinitionCode;
	}
	public void setAccountRuleDefinitionCode(String accountRuleDefinitionCode) {
		this.accountRuleDefinitionCode = accountRuleDefinitionCode;
	}
	public boolean isRangeOfStringCheckbox() {
		return rangeOfStringCheckbox;
	}
	public void setRangeOfStringCheckbox(boolean rangeOfStringCheckbox) {
		this.rangeOfStringCheckbox = rangeOfStringCheckbox;
	}
	public String getRangeOfStringMinimum() {
		return rangeOfStringMinimum;
	}
	public void setRangeOfStringMinimum(String rangeOfStringMinimum) {
		this.rangeOfStringMinimum = rangeOfStringMinimum;
	}
	public String getRangeOfStringMaximum() {
		return rangeOfStringMaximum;
	}
	public void setRangeOfStringMaximum(String rangeOfStringMaximum) {
		this.rangeOfStringMaximum = rangeOfStringMaximum;
	}
	public boolean isCharactersTypeCheckbox() {
		return charactersTypeCheckbox;
	}
	public void setCharactersTypeCheckbox(boolean charactersTypeCheckbox) {
		this.charactersTypeCheckbox = charactersTypeCheckbox;
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
	public boolean isGenerationsCountCheckbox() {
		return generationsCountCheckbox;
	}
	public void setGenerationsCountCheckbox(boolean generationsCountCheckbox) {
		this.generationsCountCheckbox = generationsCountCheckbox;
	}
	public String getGenerationsCount() {
		return generationsCount;
	}
	public void setGenerationsCount(String generationsCount) {
		this.generationsCount = generationsCount;
	}
	public boolean isLifeTimeCheckbox() {
		return lifeTimeCheckbox;
	}
	public void setLifeTimeCheckbox(boolean lifeTimeCheckbox) {
		this.lifeTimeCheckbox = lifeTimeCheckbox;
	}
	public String getLifeTime() {
		return lifeTime;
	}
	public void setLifeTime(String lifeTime) {
		this.lifeTime = lifeTime;
	}
	public boolean isLoginContinuousFailureCountCheckbox() {
		return loginContinuousFailureCountCheckbox;
	}
	public void setLoginContinuousFailureCountCheckbox(
			boolean loginContinuousFailureCountCheckbox) {
		this.loginContinuousFailureCountCheckbox = loginContinuousFailureCountCheckbox;
	}
	public String getLoginContinuousFailureCount() {
		return loginContinuousFailureCount;
	}
	public void setLoginContinuousFailureCount(String loginContinuousFailureCount) {
		this.loginContinuousFailureCount = loginContinuousFailureCount;
	}
	public boolean isAccountLockTimeCheckbox() {
		return accountLockTimeCheckbox;
	}
	public void setAccountLockTimeCheckbox(boolean accountLockTimeCheckbox) {
		this.accountLockTimeCheckbox = accountLockTimeCheckbox;
	}
	public String getAccountLockTime() {
		return accountLockTime;
	}
	public void setAccountLockTime(String accountLockTime) {
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
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
}
