package org.terasoluna.qp.app.externalobjectdefinition;

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
import org.terasoluna.qp.app.message.ExternalObjectDefinitionMessageConst;

public class ExternalObjectDefinitionForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long externalObjectDefinitionId;

	@NotEmpty(message = ExternalObjectDefinitionMessageConst.SC_EXTERNALOBJECTDEFINITION_0002)
	@QpNameSize(message = ExternalObjectDefinitionMessageConst.SC_EXTERNALOBJECTDEFINITION_0002)
	@QpNamePattern(message= ExternalObjectDefinitionMessageConst.SC_EXTERNALOBJECTDEFINITION_0002)
	private String externalObjectDefinitionName;

	@NotEmpty(message = ExternalObjectDefinitionMessageConst.SC_EXTERNALOBJECTDEFINITION_0003)
	@QpCodeSize(message = ExternalObjectDefinitionMessageConst.SC_EXTERNALOBJECTDEFINITION_0003)
	@QpCodePattern(message= ExternalObjectDefinitionMessageConst.SC_EXTERNALOBJECTDEFINITION_0003)
	@QpReserved(message= ExternalObjectDefinitionMessageConst.SC_EXTERNALOBJECTDEFINITION_0003)
	private String externalObjectDefinitionCode;
	
	private Long moduleId;
	
	private String moduleIdAutocomplete;
	
	private String className;

//	@NotEmpty(message = ProjectMessageConst.SC_PROJECT_0027)
//	@Pattern(regexp = CommonMessageConst.PATTERN_FOR_PACKAGE_NAME, message= ProjectMessageConst.ERR_PROJECT_0002+";"+ProjectMessageConst.SC_PROJECT_0028 )
	private String packageName;
	
	@QpRemarkSize(message= ExternalObjectDefinitionMessageConst.SC_EXTERNALOBJECTDEFINITION_0014)
	private String remark;

	private Integer itemSeqNo;

	private Long createdBy;

	private Timestamp createdDate;

	private Long updatedBy;

	private Timestamp updatedDate;
	
	private List<ExternalObjectAttributeForm> externalObjectAttributes;
	
	private String mode;
	
	private String externalObjectType;

	private boolean hasErrors = false;
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
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

	public Long getExternalObjectDefinitionId() {
		return externalObjectDefinitionId;
	}

	public void setExternalObjectDefinitionId(Long externalObjectDefinitionId) {
		this.externalObjectDefinitionId = externalObjectDefinitionId;
	}

	public String getExternalObjectDefinitionCode() {
		return externalObjectDefinitionCode;
	}

	public void setExternalObjectDefinitionCode(String externalObjectDefinitionCode) {
		this.externalObjectDefinitionCode = externalObjectDefinitionCode;
	}

	public String getExternalObjectDefinitionName() {
		return externalObjectDefinitionName;
	}

	public void setExternalObjectDefinitionName(String externalObjectDefinitionName) {
		this.externalObjectDefinitionName = externalObjectDefinitionName;
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

	/**
	 * @return the externalObjectAttributes
	 */
	public List<ExternalObjectAttributeForm> getExternalObjectAttributes() {
		return externalObjectAttributes;
	}

	/**
	 * @param externalObjectAttributes the externalObjectAttributes to set
	 */
	public void setExternalObjectAttributes(List<ExternalObjectAttributeForm> externalObjectAttributes) {
		this.externalObjectAttributes = externalObjectAttributes;
	}

	/**
	 * @return the itemSeqNo
	 */
	public Integer getItemSeqNo() {
		return itemSeqNo;
	}

	/**
	 * @param itemSeqNo the itemSeqNo to set
	 */
	public void setItemSeqNo(Integer itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}

	public String getExternalObjectType() {
		return externalObjectType;
	}

	public void setExternalObjectType(String externalObjectType) {
		this.externalObjectType = externalObjectType;
	}



}
