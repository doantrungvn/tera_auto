package org.terasoluna.qp.domain.repository.domaindatatype;

import java.io.Serializable;

public class DomainDatatypeCriteria implements Serializable {
	private static final long serialVersionUID = 1L;
	private String domainTableName;
	private String domainTableCode;
	private int status;
	private Long projectId;

	private Integer[] statuses = new Integer[0];
	
	private Long domainTableId;
	
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getDomainTableName() {
		return domainTableName;
	}

	public void setDomainTableName(String domainTableName) {
		this.domainTableName = domainTableName;
	}

	public String getDomainTableCode() {
		return domainTableCode;
	}

	public void setDomainTableCode(String domainTableCode) {
		this.domainTableCode = domainTableCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getDomainTableId() {
		return domainTableId;
	}

	public void setDomainTableId(Long domainTableId) {
		this.domainTableId = domainTableId;
	}

	public Integer[] getStatuses() {
		return statuses;
	}

	public void setStatuses(Integer[] statuses) {
		this.statuses = statuses;
	}

}
