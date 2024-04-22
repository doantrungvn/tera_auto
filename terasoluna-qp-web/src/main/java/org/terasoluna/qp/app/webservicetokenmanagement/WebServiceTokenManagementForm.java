package org.terasoluna.qp.app.webservicetokenmanagement;

import java.io.Serializable;
import java.sql.Timestamp;

public class WebServiceTokenManagementForm implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long wsTokenId;
	private Long projectId;
	private String projectCode;
	private String projectName;
	private String clientId;
	private String clientSecret;
	private Timestamp updatedDate;
	private String mode;
	
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
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
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public long getWsTokenId() {
		return wsTokenId;
	}
	public void setWsTokenId(long wsTokenId) {
		this.wsTokenId = wsTokenId;
	}
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	
}
