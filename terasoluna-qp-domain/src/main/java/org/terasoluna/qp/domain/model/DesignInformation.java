package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.terasoluna.gfw.common.message.ResultMessages;

public class DesignInformation implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long designInformationId;
	
	private String moduleCode;
	
	private String moduleName;
	
	private String userName;
	
	private String designName;
	
	private String remark;
	
	private String comment;
	
	private Long createdBy;
	
	private String createdByName;
	
	private Timestamp createdDate;
	
	private Long updatedBy;
	
	private String updatedByName;
	
	private Timestamp updatedDate;
	
	private List<DesignInformationDetail> designInformationDetail;
	
	private List<DesignRelationSetting> designRelationSetting;
	
	private Timestamp sysDatetime;
	
	private ResultMessages resultMessages;
	
	public Long getDesignInformationId() {
		return designInformationId;
	}

	public void setDesignInformationId(Long designInformationId) {
		this.designInformationId = designInformationId;
	}

	public String getDesignName() {
		return designName;
	}

	public void setDesignName(String designName) {
		this.designName = designName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public List<DesignRelationSetting> getDesignRelationSetting() {
		return designRelationSetting;
	}

	public void setDesignRelationSetting(
			List<DesignRelationSetting> designRelationSetting) {
		this.designRelationSetting = designRelationSetting;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getUpdatedByName() {
		return updatedByName;
	}

	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
	}

	public Timestamp getSysDatetime() {
		return sysDatetime;
	}

	public void setSysDatetime(Timestamp sysDatetime) {
		this.sysDatetime = sysDatetime;
	}

	public List<DesignInformationDetail> getDesignInformationDetail() {
		return designInformationDetail;
	}

	public void setDesignInformationDetail(
			List<DesignInformationDetail> designInformationDetail) {
		this.designInformationDetail = designInformationDetail;
	}

	public ResultMessages getResultMessages() {
		return resultMessages;
	}

	public void setResultMessages(ResultMessages resultMessages) {
		this.resultMessages = resultMessages;
	}
	
	
}
