package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class FunctionMaster implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long functionMasterId;
	
	private String functionMasterCode;
	
	private String functionMasterName;
	
	private Integer functionMasterType;
	
	private Long projectId;
	
	private String packageName;
	
	private String remark;
	
	private Long uploadFileId;
	
	private byte[] file = new byte[0];
	
	private byte[] content = new byte[0];
	
	private String contentFile;
	
	private String fileName;
	
	private String temporaryFileId;
	
	private Long createdBy;

	private Timestamp createdDate;

	private Long updatedBy;

	private Timestamp updatedDate;

	private Timestamp sysDatetime;
	
	private Timestamp updatedateOfUploadFile;

	private Long selected;
	
	private List<FunctionMethod> functionMethod = new ArrayList<FunctionMethod>();
	
	private List<FunctionMethodInput> functionInputForm = new ArrayList<FunctionMethodInput>();
	
	private List<FunctionMethodOutput> lstFunctionOutput = new ArrayList<FunctionMethodOutput>();
	
	private Boolean flagChangeFile = false;
	
	private List<BusinessDesign> listOfBusinessDesign = new ArrayList<BusinessDesign>();
	
	private List<DecisionTable> listOfDecisionTable = new ArrayList<DecisionTable>();

	private Boolean showImpactFlag = false;
	
	private String author;
	
	/**
	 * @return the functionMasterId
	 */
	public Long getFunctionMasterId() {
		return functionMasterId;
	}

	/**
	 * @param functionMasterId the functionMasterId to set
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
	 * @param functionMasterCode the functionMasterCode to set
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
	 * @param functionMasterName the functionMasterName to set
	 */
	public void setFunctionMasterName(String functionMasterName) {
		this.functionMasterName = functionMasterName;
	}

	/**
	 * @return the functionMasterType
	 */
	public Integer getFunctionMasterType() {
		return functionMasterType;
	}

	/**
	 * @param functionMasterType the functionMasterType to set
	 */
	public void setFunctionMasterType(Integer functionMasterType) {
		this.functionMasterType = functionMasterType;
	}

	/**
	 * @return the projectId
	 */
	public Long getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId the projectId to set
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
	 * @param uploadFileId the uploadFileId to set
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
	 * @param createdBy the createdBy to set
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
	 * @param createdDate the createdDate to set
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
	 * @param updatedBy the updatedBy to set
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
	 * @param updatedDate the updatedDate to set
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
	 * @param sysDatetime the sysDatetime to set
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
	 * @param selected the selected to set
	 */
	public void setSelected(Long selected) {
		this.selected = selected;
	}

	public String getRemark() {
	    return remark;
    }

	public void setRemark(String remark) {
	    this.remark = remark;
    }
	
	public List<FunctionMethodInput> getFunctionInputForm() {
		return functionInputForm;
	}

	public void setFunctionInputForm(List<FunctionMethodInput> functionInputForm) {
		this.functionInputForm = functionInputForm;
	}

	public List<FunctionMethodOutput> getLstFunctionOutput() {
	    return lstFunctionOutput;
    }

	public void setLstFunctionOutput(List<FunctionMethodOutput> lstFunctionOutput) {
	    this.lstFunctionOutput = lstFunctionOutput;
    }

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
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

	public List<FunctionMethod> getFunctionMethod() {
		return functionMethod;
	}

	public void setFunctionMethod(List<FunctionMethod> functionMethod) {
		this.functionMethod = functionMethod;
	}

	public List<BusinessDesign> getListOfBusinessDesign() {
		return listOfBusinessDesign;
	}

	public void setListOfBusinessDesign(List<BusinessDesign> listOfBusinessDesign) {
		this.listOfBusinessDesign = listOfBusinessDesign;
	}

	public List<DecisionTable> getListOfDecisionTable() {
		return listOfDecisionTable;
	}

	public void setListOfDecisionTable(List<DecisionTable> listOfDecisionTable) {
		this.listOfDecisionTable = listOfDecisionTable;
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
	
	public Boolean getShowImpactFlag() {
	    return showImpactFlag;
    }

	public void setShowImpactFlag(Boolean showImpactFlag) {
	    this.showImpactFlag = showImpactFlag;
    }

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}
