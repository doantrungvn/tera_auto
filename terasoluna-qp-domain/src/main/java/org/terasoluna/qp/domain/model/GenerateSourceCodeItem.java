package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class GenerateSourceCodeItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Item key */
	private Integer sourceCodeItemKey;
	/** File name for generate */
	private String sourceCodeItemTemplateName;
	/** Item had is checked */
	private Boolean isChecked;

	/**
	 * @return the sourceCodeItemTemplateName
	 */
	public String getSourceCodeItemTemplateName() {
		return sourceCodeItemTemplateName;
	}
	/**
	 * @param sourceCodeItemTemplateName the sourceCodeItemTemplateName to set
	 */
	public void setSourceCodeItemTemplateName(String sourceCodeItemTemplateName) {
		this.sourceCodeItemTemplateName = sourceCodeItemTemplateName;
	}
	/**
	 * @return the isChecked
	 */
	public Boolean getIsChecked() {
		return isChecked;
	}
	/**
	 * @param isChecked the isChecked to set
	 */
	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}
	/**
	 * @return the sourceCodeItemKey
	 */
	public Integer getSourceCodeItemKey() {
		return sourceCodeItemKey;
	}
	/**
	 * @param sourceCodeItemKey the sourceCodeItemKey to set
	 */
	public void setSourceCodeItemKey(Integer sourceCodeItemKey) {
		this.sourceCodeItemKey = sourceCodeItemKey;
	}

}
