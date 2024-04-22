package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class FunctionDesign implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long functionId;
	
	private String functionName;
	
	private String functionCode;
	
	private Integer functionType;
	
	private String actor;
	
	private String moduleName;
	
	private Long moduleId;
	
	private String moduleIdAutocomplete;
	
	private String remark;
	
	private Long createdBy;
	
	private Timestamp createdDate;
	
	private Long updatedBy;
	
	private Timestamp updatedDate;
	
	private Timestamp sysDateTime;
	
	private Long projectId;
	
	private Integer status;
	
	private Long accountId;

	public Long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Integer getFunctionType() {
		return functionType;
	}

	public void setFunctionType(Integer functionType) {
		this.functionType = functionType;
	}

	public Timestamp getSysDateTime() {
		return sysDateTime;
	}

	public void setSysDateTime(Timestamp sysDateTime) {
		this.sysDateTime = sysDateTime;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getModuleIdAutocomplete() {
		return moduleIdAutocomplete;
	}

	public void setModuleIdAutocomplete(String moduleIdAutocomplete) {
		this.moduleIdAutocomplete = moduleIdAutocomplete;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
}
