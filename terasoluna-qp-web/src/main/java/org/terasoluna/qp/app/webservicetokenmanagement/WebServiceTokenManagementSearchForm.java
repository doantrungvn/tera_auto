package org.terasoluna.qp.app.webservicetokenmanagement;

import java.io.Serializable;

public class WebServiceTokenManagementSearchForm implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String projectCode;
	private String projectName;
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
	

}
