package org.terasoluna.qp.app.codelist;

import java.io.Serializable;

public class CodeListSearchForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer[] OptionValude = new Integer[0];

	private String codeListName;
	private String codeListCode;
	private Integer moduleId;
	private String moduleIdAutocomplete;
	private Long projectId;
	private String projectIdAutocomplete;
	private Integer multivalueFlg;
	
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

	public String getModuleIdAutocomplete() {
		return moduleIdAutocomplete;
	}

	public void setModuleIdAutocomplete(String moduleIdAutocomplete) {
		this.moduleIdAutocomplete = moduleIdAutocomplete;
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

	public Integer getMultivalueFlg() {
		return multivalueFlg;
	}

	public void setMultivalueFlg(Integer multivalueFlg) {
		this.multivalueFlg = multivalueFlg;
	}

}
