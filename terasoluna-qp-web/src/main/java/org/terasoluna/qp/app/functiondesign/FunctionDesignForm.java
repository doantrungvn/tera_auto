package org.terasoluna.qp.app.functiondesign;

import java.io.Serializable;
import java.sql.Timestamp;

import org.hibernate.validator.constraints.NotEmpty;
import org.terasoluna.qp.app.common.validation.QpNamePattern;
import org.terasoluna.qp.app.common.validation.QpNameSize;
import org.terasoluna.qp.app.common.validation.QpRemarkSize;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.FunctionDesignMessageConst;
import org.terasoluna.qp.app.message.ModuleMessageConst;

public class FunctionDesignForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long functionId;
	
	@NotEmpty(message = FunctionDesignMessageConst.SC_FUNCTIONDESIGN_0002)
	@QpNameSize(message = FunctionDesignMessageConst.SC_FUNCTIONDESIGN_0002)
	@QpNamePattern(message= FunctionDesignMessageConst.SC_FUNCTIONDESIGN_0002)
	private String functionName;
	
	@NotEmpty(message = FunctionDesignMessageConst.SC_FUNCTIONDESIGN_0003)
	@QpNameSize(message = FunctionDesignMessageConst.SC_FUNCTIONDESIGN_0003)
	@QpNamePattern(message= FunctionDesignMessageConst.SC_FUNCTIONDESIGN_0003)
	private String functionCode;
	
	private Integer functionType;
	
	private String actor;
	
	@NotEmpty(message = ModuleMessageConst.SC_MODULE_0007)
	private String moduleIdAutocomplete;
	
	private String moduleName;
	
	private Long moduleId;
	
	@QpRemarkSize(message= CommonMessageConst.SC_SYS_0028)
	private String remark;
	
	private Long createdBy;
	
	private Timestamp createdDate;
	
	private Long updatedBy;
	
	private Timestamp updatedDate;
	
	private Timestamp sysDateTime;
	
	private Long projectId;
	
	private String mode;
	
	private Integer status;

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

	public String getModuleIdAutocomplete() {
		return moduleIdAutocomplete;
	}

	public void setModuleIdAutocomplete(String moduleIdAutocomplete) {
		this.moduleIdAutocomplete = moduleIdAutocomplete;
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

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
