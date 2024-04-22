package org.terasoluna.qp.app.commonobjectdefinition;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;
import org.terasoluna.qp.app.common.validation.QpCodePattern;
import org.terasoluna.qp.app.common.validation.QpCodeSize;
import org.terasoluna.qp.app.common.validation.QpNamePattern;
import org.terasoluna.qp.app.common.validation.QpNameSize;
import org.terasoluna.qp.app.common.validation.QpRemarkSize;
import org.terasoluna.qp.app.common.validation.QpReserved;
import org.terasoluna.qp.app.message.CommonObjectDefinitionMessageConst;

public class CommonObjectDefinitionForm implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long commonObjectDefinitionId;

	@NotEmpty(message = CommonObjectDefinitionMessageConst.SC_COMMONOBJECTDEFINITION_0002)
	@QpNameSize(message = CommonObjectDefinitionMessageConst.SC_COMMONOBJECTDEFINITION_0002)
	@QpNamePattern(message = CommonObjectDefinitionMessageConst.SC_COMMONOBJECTDEFINITION_0002)
	private String commonObjectDefinitionName;

	@NotEmpty(message = CommonObjectDefinitionMessageConst.SC_COMMONOBJECTDEFINITION_0003)
	@QpCodeSize(message = CommonObjectDefinitionMessageConst.SC_COMMONOBJECTDEFINITION_0003)
	@QpCodePattern(message = CommonObjectDefinitionMessageConst.SC_COMMONOBJECTDEFINITION_0003)
	@QpReserved(message = CommonObjectDefinitionMessageConst.SC_COMMONOBJECTDEFINITION_0003)
	private String commonObjectDefinitionCode;

	private Long moduleId;

	@QpRemarkSize(message = CommonObjectDefinitionMessageConst.SC_COMMONOBJECTDEFINITION_0012)
	private String remark;

	private int itemSeqNo;

	private Long createdBy;

	private Timestamp createdDate;

	private Long updatedBy;

	private Timestamp updatedDate;

	private List<CommonObjectAttributeForm> commonObjectAttributes;

	private boolean hasErrors = false;

	private String mode;

	private String moduleIdAutocomplete;

	public int getItemSeqNo() {
		return itemSeqNo;
	}

	public void setItemSeqNo(int itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
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

	public Long getCommonObjectDefinitionId() {
		return commonObjectDefinitionId;
	}

	public void setCommonObjectDefinitionId(Long commonObjectDefinitionId) {
		this.commonObjectDefinitionId = commonObjectDefinitionId;
	}

	public String getCommonObjectDefinitionCode() {
		return commonObjectDefinitionCode;
	}

	public void setCommonObjectDefinitionCode(String commonObjectDefinitionCode) {
		this.commonObjectDefinitionCode = commonObjectDefinitionCode;
	}

	public String getCommonObjectDefinitionName() {
		return commonObjectDefinitionName;
	}

	public void setCommonObjectDefinitionName(String commonObjectDefinitionName) {
		this.commonObjectDefinitionName = commonObjectDefinitionName;
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

	public String getModuleIdAutocomplete() {
		return moduleIdAutocomplete;
	}

	public void setModuleIdAutocomplete(String moduleIdAutocomplete) {
		this.moduleIdAutocomplete = moduleIdAutocomplete;
	}

	public List<CommonObjectAttributeForm> getCommonObjectAttributes() {
		return commonObjectAttributes;
	}

	public void setCommonObjectAttributes(List<CommonObjectAttributeForm> commonObjectAttributes) {
		this.commonObjectAttributes = commonObjectAttributes;
	}
}
