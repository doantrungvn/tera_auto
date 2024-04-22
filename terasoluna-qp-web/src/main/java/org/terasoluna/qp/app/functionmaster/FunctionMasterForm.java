package org.terasoluna.qp.app.functionmaster;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;
import org.terasoluna.qp.app.common.validation.QpCodePattern;
import org.terasoluna.qp.app.common.validation.QpCodeSize;
import org.terasoluna.qp.app.common.validation.QpNamePattern;
import org.terasoluna.qp.app.common.validation.QpNameSize;
import org.terasoluna.qp.app.message.FunctionMasterMessageConst;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.DecisionTable;

public class FunctionMasterForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long functionMasterId;

	@NotEmpty(message = FunctionMasterMessageConst.SC_FUNCTIONMASTER_0005)
	@QpNameSize(message = FunctionMasterMessageConst.SC_FUNCTIONMASTER_0005)
	@QpNamePattern(message = FunctionMasterMessageConst.SC_FUNCTIONMASTER_0005)
	private String functionMasterName;

	@NotEmpty(message = FunctionMasterMessageConst.SC_FUNCTIONMASTER_0006)
	@QpCodeSize(message = FunctionMasterMessageConst.SC_FUNCTIONMASTER_0006)
	@QpCodePattern(message = FunctionMasterMessageConst.SC_FUNCTIONMASTER_0006)
	private String functionMasterCode;

	private String functionMasterType;

	private Long projectId;

	private Long uploadFileId;

	private MultipartFile file;

	private String remark;

	private Long createdBy;

	private Timestamp createdDate;

	private Long updatedBy;

	private Timestamp updatedDate;

	private Timestamp sysDatetime;

	private Long selected;

	@NotEmpty(message = FunctionMasterMessageConst.SC_FUNCTIONMASTER_0030)
	private String packageName;

	private boolean hasErrors = false;

	private String mode;

	private String fileName;

	private String temporaryFileId;

	private byte[] content = new byte[0];

	private String contentFile;

	private Boolean flagChangeFile = false;

	private Timestamp updatedateOfUploadFile;

	private List<FunctionMethodForm> functionMethod = new ArrayList<FunctionMethodForm>();

	private FunctionMethodInputForm[] functionMethodInputForm = new FunctionMethodInputForm[0];

	private FunctionMethodOutputForm[] lstFunctionOutput = new FunctionMethodOutputForm[0];

	private List<DecisionTable> listOfDecisionTable = new ArrayList<DecisionTable>();

	private List<BusinessDesign> listOfBusinessDesign = new ArrayList<BusinessDesign>();

	private Boolean isShowWarningFile = false;
	
	private int maxSize;
	
	private Boolean showImpactFlag = false;

	/**
	 * @return the functionMasterId
	 */
	public Long getFunctionMasterId() {
		return functionMasterId;
	}

	/**
	 * @param functionMasterId
	 *            the functionMasterId to set
	 */
	public void setFunctionMasterId(Long functionMasterId) {
		this.functionMasterId = functionMasterId;
	}

	/**
	 * @return the functionMasterCode
	 */
	public String getFunctionMasterCode() {
		return functionMasterCode;
	}

	/**
	 * @param functionMasterCode
	 *            the functionMasterCode to set
	 */
	public void setFunctionMasterCode(String functionMasterCode) {
		this.functionMasterCode = functionMasterCode;
	}

	/**
	 * @return the functionMasterName
	 */
	public String getFunctionMasterName() {
		return functionMasterName;
	}

	/**
	 * @param functionMasterName
	 *            the functionMasterName to set
	 */
	public void setFunctionMasterName(String functionMasterName) {
		this.functionMasterName = functionMasterName;
	}

	/**
	 * @return the functionMasterType
	 */
	public String getFunctionMasterType() {
		return functionMasterType;
	}

	/**
	 * @param functionMasterType
	 *            the functionMasterType to set
	 */
	public void setFunctionMasterType(String functionMasterType) {
		this.functionMasterType = functionMasterType;
	}

	/**
	 * @return the projectId
	 */
	public Long getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId
	 *            the projectId to set
	 */
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	/**
	 * @return the uploadFileId
	 */
	public Long getUploadFileId() {
		return uploadFileId;
	}

	/**
	 * @param uploadFileId
	 *            the uploadFileId to set
	 */
	public void setUploadFileId(Long uploadFileId) {
		this.uploadFileId = uploadFileId;
	}

	/**
	 * @return the createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the updatedBy
	 */
	public Long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            the updatedBy to set
	 */
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the updatedDate
	 */
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate
	 *            the updatedDate to set
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the sysDatetime
	 */
	public Timestamp getSysDatetime() {
		return sysDatetime;
	}

	/**
	 * @param sysDatetime
	 *            the sysDatetime to set
	 */
	public void setSysDatetime(Timestamp sysDatetime) {
		this.sysDatetime = sysDatetime;
	}

	/**
	 * @return the selected
	 */
	public Long getSelected() {
		return selected;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(Long selected) {
		this.selected = selected;
	}

	/**
	 * @return the hasErrors
	 */
	public boolean isHasErrors() {
		return hasErrors;
	}

	/**
	 * @param hasErrors
	 *            the hasErrors to set
	 */
	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode
	 *            the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public FunctionMethodInputForm[] getFunctionInputForm() {
		return functionMethodInputForm;
	}

	public void setFunctionInputForm(FunctionMethodInputForm[] functionInputForm) {
		this.functionMethodInputForm = functionInputForm;
	}

	public FunctionMethodOutputForm[] getLstFunctionOutput() {
		return lstFunctionOutput;
	}

	public void setLstFunctionOutput(FunctionMethodOutputForm[] lstFunctionOutput) {
		this.lstFunctionOutput = lstFunctionOutput;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public Timestamp getUpdatedateOfUploadFile() {
		return updatedateOfUploadFile;
	}

	public void setUpdatedateOfUploadFile(Timestamp updatedateOfUploadFile) {
		this.updatedateOfUploadFile = updatedateOfUploadFile;
	}

	public Boolean getFlagChangeFile() {
		return flagChangeFile;
	}

	public void setFlagChangeFile(Boolean flagChangeFile) {
		this.flagChangeFile = flagChangeFile;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public List<FunctionMethodForm> getFunctionMethod() {
		return functionMethod;
	}

	public void setFunctionMethod(List<FunctionMethodForm> functionMethod) {
		this.functionMethod = functionMethod;
	}

	public FunctionMethodInputForm[] getFunctionMethodInputForm() {
		return functionMethodInputForm;
	}

	public void setFunctionMethodInputForm(FunctionMethodInputForm[] functionMethodInputForm) {
		this.functionMethodInputForm = functionMethodInputForm;
	}

	public List<DecisionTable> getListOfDecisionTable() {
		return listOfDecisionTable;
	}

	public void setListOfDecisionTable(List<DecisionTable> listOfDecisionTable) {
		this.listOfDecisionTable = listOfDecisionTable;
	}

	public List<BusinessDesign> getListOfBusinessDesign() {
		return listOfBusinessDesign;
	}

	public void setListOfBusinessDesign(List<BusinessDesign> listOfBusinessDesign) {
		this.listOfBusinessDesign = listOfBusinessDesign;
	}

	public String getTemporaryFileId() {
		return temporaryFileId;
	}

	public void setTemporaryFileId(String temporaryFileId) {
		this.temporaryFileId = temporaryFileId;
	}

	public String getContentFile() {
		return contentFile;
	}

	public void setContentFile(String contentFile) {
		this.contentFile = contentFile;
	}

	public Boolean getIsShowWarningFile() {
		return isShowWarningFile;
	}

	public void setIsShowWarningFile(Boolean isShowWarningFile) {
		this.isShowWarningFile = isShowWarningFile;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public Boolean getShowImpactFlag() {
	    return showImpactFlag;
    }

	public void setShowImpactFlag(Boolean showImpactFlag) {
	    this.showImpactFlag = showImpactFlag;
    }
}
