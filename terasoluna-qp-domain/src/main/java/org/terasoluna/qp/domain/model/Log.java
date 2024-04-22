package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "loggingManagement")
public class Log implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long logId;
	private Long projectId;
	private int status;
	private int logType;
	private Long createdBy;
	private Timestamp createdDate;
	private Long updatedBy;
	private Timestamp updatedDate = new Timestamp(1);
	private Timestamp systemTime;
	List<LogDetail> lstLogDetail;
	public Long getLogId() {
		return logId;
	}
	public void setLogId(Long logId) {
		this.logId = logId;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	@XmlElement(name = "status")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@XmlElement(name = "logType")
	public int getLogType() {
		return logType;
	}
	public void setLogType(int logType) {
		this.logType = logType;
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
	@XmlElement(name = "logDetail", type = LogDetail.class)
	public List<LogDetail> getLstLogDetail() {
		return lstLogDetail;
	}
	public void setLstLogDetail(List<LogDetail> lstLogDetail) {
		this.lstLogDetail = lstLogDetail;
	}
	public Timestamp getSystemTime() {
		return systemTime;
	}
	public void setSystemTime(Timestamp systemTime) {
		this.systemTime = systemTime;
	}
}
