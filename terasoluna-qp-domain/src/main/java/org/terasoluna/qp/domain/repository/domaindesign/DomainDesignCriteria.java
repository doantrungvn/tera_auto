package org.terasoluna.qp.domain.repository.domaindesign;

import java.io.Serializable;

public class DomainDesignCriteria implements Serializable {

	private static final long serialVersionUID = 1L;
	private String domainName;
	private String domainCode;
	private Integer domainBaseType;

	private Long projectId;
	
	private Long domainId;

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getDomainCode() {
		return domainCode;
	}

	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}

	public Integer getDomainBaseType() {
		return domainBaseType;
	}

	public void setDomainBaseType(Integer domainBaseType) {
		this.domainBaseType = domainBaseType;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getDomainId() {
		return domainId;
	}

	public void setDomainId(Long domainId) {
		this.domainId = domainId;
	}


}
