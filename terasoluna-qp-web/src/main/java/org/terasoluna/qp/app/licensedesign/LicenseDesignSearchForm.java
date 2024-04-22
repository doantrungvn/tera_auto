package org.terasoluna.qp.app.licensedesign;

import java.io.Serializable;

public class LicenseDesignSearchForm implements Serializable{
	/**
	 * Author NhatDN
	 */
	private static final long serialVersionUID = 1L;
    private String customerName;
    private String customerCode;
    private String projectIdAutocomplete;
    private String projectCode;
    private String tel;
    private String num;
    private String email;
    private String fromStartDate;
    private String toStartDate;
    private String fromExpiredDate;
    private String toExpiredDate;
    private Integer status;
    private String fileName;
    private String projectId;
    
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFromStartDate() {
		return fromStartDate;
	}
	public void setFromStartDate(String fromStartDate) {
		this.fromStartDate = fromStartDate;
	}
	public String getToStartDate() {
		return toStartDate;
	}
	public void setToStartDate(String toStartDate) {
		this.toStartDate = toStartDate;
	}
	public String getFromExpiredDate() {
		return fromExpiredDate;
	}
	public void setFromExpiredDate(String fromExpiredDate) {
		this.fromExpiredDate = fromExpiredDate;
	}
	public String getToExpiredDate() {
		return toExpiredDate;
	}
	public void setToExpiredDate(String toExpiredDate) {
		this.toExpiredDate = toExpiredDate;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectIdAutocomplete() {
		return projectIdAutocomplete;
	}
	public void setProjectIdAutocomplete(String projectIdAutocomplete) {
		this.projectIdAutocomplete = projectIdAutocomplete;
	}
}
