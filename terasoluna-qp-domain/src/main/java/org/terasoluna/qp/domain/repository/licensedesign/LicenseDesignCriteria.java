package org.terasoluna.qp.domain.repository.licensedesign;

import java.io.Serializable;
import java.sql.Timestamp;

public class LicenseDesignCriteria implements Serializable {
	
	private static final long serialVersionUID = 1L;
    
    private String customerName;
	private String customerCode;
	private Long projectId;
    private String projectName;
    private String projectCode;
    private String tel;
    private Integer num;
    private String email;
	private Timestamp fromStartDate;
	private Timestamp toStartDate;
	private Timestamp fromExpiredDate;
	private Timestamp toExpiredDate;
	private Integer status;
	private Timestamp updatedDate;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

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

	public Timestamp getFromStartDate() {
		return fromStartDate;
	}

	public void setFromStartDate(Timestamp fromStartDate) {
		this.fromStartDate = fromStartDate;
	}

	public Timestamp getToStartDate() {
		return toStartDate;
	}

	public void setToStartDate(Timestamp toStartDate) {
		this.toStartDate = toStartDate;
	}

	public Timestamp getFromExpiredDate() {
		return fromExpiredDate;
	}

	public void setFromExpiredDate(Timestamp fromExpiredDate) {
		this.fromExpiredDate = fromExpiredDate;
	}

	public Timestamp getToExpiredDate() {
		return toExpiredDate;
	}

	public void setToExpiredDate(Timestamp toExpiredDate) {
		this.toExpiredDate = toExpiredDate;
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

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
}
