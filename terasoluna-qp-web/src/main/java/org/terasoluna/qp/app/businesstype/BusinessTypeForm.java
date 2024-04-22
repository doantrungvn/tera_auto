package org.terasoluna.qp.app.businesstype;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;
import org.terasoluna.qp.app.common.validation.QpCodePattern;
import org.terasoluna.qp.app.common.validation.QpCodeSize;
import org.terasoluna.qp.app.common.validation.QpNamePattern;
import org.terasoluna.qp.app.common.validation.QpNameSize;
import org.terasoluna.qp.app.common.validation.QpRemarkSize;
import org.terasoluna.qp.app.message.BusinessTypeMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.Module;


public class BusinessTypeForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long businessTypeId;
	
	@NotEmpty(message = BusinessTypeMessageConst.SC_BUSINESSTYPE_0001)
	@QpNameSize(message = BusinessTypeMessageConst.SC_BUSINESSTYPE_0001)
	@QpNamePattern(message= BusinessTypeMessageConst.SC_BUSINESSTYPE_0001)
	private String businessTypeName;
	
	@NotEmpty(message = BusinessTypeMessageConst.SC_BUSINESSTYPE_0002)
	@QpCodeSize(message = BusinessTypeMessageConst.SC_BUSINESSTYPE_0002)
	@QpCodePattern(message= BusinessTypeMessageConst.SC_BUSINESSTYPE_0002)
	private String businessTypeCode;

	private Long parentBusinessTypeId;
	
	private String parentBusinessTypeName;

	@QpRemarkSize(message= CommonMessageConst.SC_SYS_0028)
	private String remark;
	
	List<Module> modules;
	
	private Long createdBy;
	
	private Timestamp createdDate;
	
	private Long updatedBy;
	
	private Timestamp updatedDate;
	
	private boolean hasErrors = false;
	
	private String mode;
	
	public BusinessTypeForm() {
		
	}

	public String getBusinessTypeName() {
		return businessTypeName;
	}
	
	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}
	
	public String getBusinessTypeCode() {
		return businessTypeCode;
	}

	public void setBusinessTypeCode(String businessTypeCode) {
		this.businessTypeCode = businessTypeCode;
	}

	public Long getParentBusinessTypeId() {
		return parentBusinessTypeId;
	}

	public void setParentBusinessTypeId(Long parentBusinessType) {
		this.parentBusinessTypeId = parentBusinessType;
	}

	/**
	 * @return the parentBusinessTypeName
	 */
	public String getParentBusinessTypeName() {
		return parentBusinessTypeName;
	}

	/**
	 * @param parentBusinessTypeName the parentBusinessTypeName to set
	 */
	public void setParentBusinessTypeName(String parentBusinessTypeName) {
		this.parentBusinessTypeName = parentBusinessTypeName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getBusinessTypeId() {
		return businessTypeId;
	}

	public void setBusinessTypeId(Long businessTypeId) {
		this.businessTypeId = businessTypeId;
	}

	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
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

	public boolean isHasErrors() {
		return hasErrors;
	}

	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

}
