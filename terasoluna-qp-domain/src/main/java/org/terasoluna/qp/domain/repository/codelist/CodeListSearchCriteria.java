package org.terasoluna.qp.domain.repository.codelist;

import java.io.Serializable;

public class CodeListSearchCriteria implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codeListName;
	private String codeListCode;
	private Integer moduleId;
	private Long projectId;
	private Integer[] OptionValude;
	public String getCodeListName() {
		return codeListName;
	}
	public void setCodeListName(String codeListName) {
		this.codeListName = codeListName;
	}
	public String getCodeListCode() {
		return codeListCode;
	}
	public void setCodeListCode(String codeListCode) {
		this.codeListCode = codeListCode;
	}
	public Integer getModuleId() {
		return moduleId;
	}
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}
	public Integer[] getOptionValude() {
		return OptionValude;
	}
	public void setOptionValude(Integer[] optionValude) {
		OptionValude = optionValude;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	
}
