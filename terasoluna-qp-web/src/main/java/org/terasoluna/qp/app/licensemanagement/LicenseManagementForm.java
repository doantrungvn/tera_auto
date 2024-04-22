package org.terasoluna.qp.app.licensemanagement;

import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;

public class LicenseManagementForm {
	/**
	 * Author NhatDN
	 */
	private Long licenseId;

	private String customerCode;
	private String customerName;
	private Long projectId;
	private String projectIdAutocomplete;
	private String projectNameAutoCompleteLabel;
	private String projectName;
	private String projectCode;
	private String tel;
	private String email;
	private String address;
	private String version;
	private String num;
	private String mode;
	private String startDate;
	private String expiredDate;
	private String appliedDate;
	private Integer status;
	private String resultFileName;
	private MultipartFile fileName;
	private Timestamp systemTime;
	private String licenseFileName;
	private int maxSize;

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

	public String getAppliedDate() {
		return appliedDate;
	}

	public void setAppliedDate(String appliedDate) {
		this.appliedDate = appliedDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getResultFileName() {
		return resultFileName;
	}

	public void setResultFileName(String resultFileName) {
		this.resultFileName = resultFileName;
	}

	public MultipartFile getFileName() {
		return fileName;
	}

	public void setFileName(MultipartFile fileName) {
		this.fileName = fileName;
	}

	public Timestamp getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(Timestamp systemTime) {
		this.systemTime = systemTime;
	}

	public String getLicenseFileName() {
		return licenseFileName;
	}

	public void setLicenseFileName(String licenseFileName) {
		this.licenseFileName = licenseFileName;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
}
