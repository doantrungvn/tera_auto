package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class WebServiceToken implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long wsTokenId;
	private Long projectId;
	private String clientId;
	private String clientSecret;
	private Long createdBy;
	private Timestamp createdDate;
	private Long updatedBy;
	private Timestamp updatedDate;
	private String projectName;
	private String projectCode;
	private Timestamp systemTime;
	
	public Long getWsTokenId() {
		return wsTokenId;
	}
	public void setWsTokenId(Long wsTokenId) {
		this.wsTokenId = wsTokenId;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
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
	public Timestamp getSystemTime() {
		return systemTime;
	}
	public void setSystemTime(Timestamp systemTime) {
		this.systemTime = systemTime;
	}
	
}
