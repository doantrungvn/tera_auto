package org.terasoluna.qp.app.domaindesign;

import java.io.Serializable;

public class DomainDesignSearchForm implements Serializable {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private String domainName;
	private String domainCode;
	
	private Integer domainBaseType;
	private String domainBaseTypeAutocomplete;
	
	private Long projectId;
	private String projectIdAutocomplete;
	
	public String getDomainCode() {
		return domainCode;
	}

	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
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

	public String getProjectIdAutocomplete() {
		return projectIdAutocomplete;
	}

	public void setProjectIdAutocomplete(String projectIdAutocomplete) {
		this.projectIdAutocomplete = projectIdAutocomplete;
	}

	public String getDomainBaseTypeAutocomplete() {
		return domainBaseTypeAutocomplete;
	}

	public void setDomainBaseTypeAutocomplete(String domainBaseTypeAutocomplete) {
		this.domainBaseTypeAutocomplete = domainBaseTypeAutocomplete;
	}

}
