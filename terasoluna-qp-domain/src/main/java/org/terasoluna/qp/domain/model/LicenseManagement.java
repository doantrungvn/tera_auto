package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class LicenseManagement implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long licenseId;
	private String customerCode;
	private String customerName;
	private Long projectId;
	private String projectName;
	private String projectCode;
	private String tel;
	private String email;
	private String address;
	private Timestamp startDate;
	private Timestamp expiredDate;
	private Timestamp appliedDate;
	private Integer status;
	private String version;
	private int num;
	private String filePath;
	private Timestamp systemTime;
	private String licenseFileName;
	
	private Long appliedBy;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Timestamp expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Long getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(Long licenseId) {
		this.licenseId = licenseId;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
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

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Timestamp getAppliedDate() {
		return appliedDate;
	}

	public void setAppliedDate(Timestamp appliedDate) {
		this.appliedDate = appliedDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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

	public Long getAppliedBy() {
		return appliedBy;
	}

	public void setAppliedBy(Long appliedBy) {
		this.appliedBy = appliedBy;
	}
}
