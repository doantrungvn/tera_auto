package org.terasoluna.qp.app.licensedesign;

import java.sql.Timestamp;

import org.hibernate.validator.constraints.NotEmpty;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.validation.QpCodePattern;
import org.terasoluna.qp.app.common.validation.QpCodeSize;
import org.terasoluna.qp.app.common.validation.QpNameSize;
import org.terasoluna.qp.app.common.validation.QpPhone;
import org.terasoluna.qp.app.common.validation.QpSize;
import org.terasoluna.qp.app.message.LicenseDesignMessageConst;
import org.terasoluna.qp.app.message.ProjectMessageConst;

public class LicenseDesignForm {
	/**
	 * Author NhatDN
	 */
	private Long licenseId;

	@NotEmpty(message = LicenseDesignMessageConst.SC_LICENSEDESIGN_0001)
	@QpNameSize(message = LicenseDesignMessageConst.SC_LICENSEDESIGN_0001)
	private String customerName;

	@NotEmpty(message = LicenseDesignMessageConst.SC_LICENSEDESIGN_0000)
	@QpCodeSize(message = LicenseDesignMessageConst.SC_LICENSEDESIGN_0000)
	@QpCodePattern(message = LicenseDesignMessageConst.SC_LICENSEDESIGN_0000)
	private String customerCode;

	@NotEmpty(message = LicenseDesignMessageConst.SC_LICENSEDESIGN_0005)
	@QpSize(max = 50, message = LicenseDesignMessageConst.SC_LICENSEDESIGN_0005+ ";0;50")
	private String email;

	@NotEmpty(message = ProjectMessageConst.SC_PROJECT_0005)
	@QpCodeSize(message = ProjectMessageConst.SC_PROJECT_0005)
	private String projectName;

	private Long projectId;
	private String projectCode;
	private String projectIdAutocomplete;
	private String projectNameAutoCompleteLabel;

	@QpSize(max = 50, message = LicenseDesignMessageConst.SC_LICENSEDESIGN_0004 + ";0;50")
	@QpPhone(message = LicenseDesignMessageConst.SC_LICENSEDESIGN_0004)
	private String tel;

	@QpSize(max = 500, message = LicenseDesignMessageConst.SC_LICENSEDESIGN_0006+ ";0;500")
	private String address;

	@QpSize(max = DbDomainConst.MAX_VAL_OTHER, message = LicenseDesignMessageConst.SC_LICENSEDESIGN_0008 + ";0;" + DbDomainConst.MAX_VAL_OTHER)
	private String version;

	@NotEmpty(message = LicenseDesignMessageConst.SC_LICENSEDESIGN_0007)
	private String num;

	private String mode;

	@NotEmpty(message = LicenseDesignMessageConst.SC_LICENSEDESIGN_0009)
	private String startDate;

	@NotEmpty(message = LicenseDesignMessageConst.SC_LICENSEDESIGN_0010)
	private String expiredDate;

	private Long createdBy;

	private Timestamp createdDate;

	private Long updatedBy;

	private Timestamp updatedDate;

	private Timestamp sysDateTime;

	private Long accountId;

	private Integer status;

	public Long getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(Long licenseId) {
		this.licenseId = licenseId;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
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

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getProjectIdAutocomplete() {
		return projectIdAutocomplete;
	}

	public void setProjectIdAutocomplete(String projectIdAutocomplete) {
		this.projectIdAutocomplete = projectIdAutocomplete;
	}

	public String getProjectNameAutoCompleteLabel() {
		return projectNameAutoCompleteLabel;
	}

	public void setProjectNameAutoCompleteLabel(String projectNameAutoCompleteLabel) {
		this.projectNameAutoCompleteLabel = projectNameAutoCompleteLabel;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
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

	public Timestamp getSysDateTime() {
		return sysDateTime;
	}

	public void setSysDateTime(Timestamp sysDateTime) {
		this.sysDateTime = sysDateTime;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
