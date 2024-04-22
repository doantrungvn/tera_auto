package org.terasoluna.qp.app.businessdesign;

import java.io.Serializable;

public class BusinessDesignSearchForm implements Serializable{

    private static final long serialVersionUID = 1L;

	private String businessLogicName;

	private String businessLogicCode;

	private Long moduleId;
	
	private String moduleIdAutocomplete;
	
	private Long screenId;
	
	private String screenIdAutocomplete;
	
	private Integer[] returnType = new Integer[0];
	
	private Integer[] designStatus; 
	
	private Integer[] blogicType;
	
	private Integer[] designMode; 

	public String getBusinessLogicName() {
		return businessLogicName;
	}

	public void setBusinessLogicName(String businessLogicName) {
		this.businessLogicName = businessLogicName;
	}

	public String getBusinessLogicCode() {
		return businessLogicCode;
	}

	public void setBusinessLogicCode(String businessLogicCode) {
		this.businessLogicCode = businessLogicCode;
	}

	/**
	 * @return the moduleId
	 */
	public Long getModuleId() {
		return moduleId;
	}

	/**
	 * @param moduleId the moduleId to set
	 */
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	/**
	 * @return the moduleIdAutocomplete
	 */
	public String getModuleIdAutocomplete() {
		return moduleIdAutocomplete;
	}

	/**
	 * @param moduleIdAutocomplete the moduleIdAutocomplete to set
	 */
	public void setModuleIdAutocomplete(String moduleIdAutocomplete) {
		this.moduleIdAutocomplete = moduleIdAutocomplete;
	}

	public Long getScreenId() {
		return screenId;
	}

	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}

	public String getScreenIdAutocomplete() {
		return screenIdAutocomplete;
	}

	public void setScreenIdAutocomplete(String screenIdAutocomplete) {
		this.screenIdAutocomplete = screenIdAutocomplete;
	}

	public Integer[] getBlogicType() {
		return blogicType;
	}

	public void setBlogicType(Integer[] blogicType) {
		this.blogicType = blogicType;
	}

	public Integer[] getReturnType() {
		return returnType;
	}

	public void setReturnType(Integer[] returnType) {
		this.returnType = returnType;
	}

	public Integer[] getDesignStatus() {
		return designStatus;
	}

	public void setDesignStatus(Integer[] designStatus) {
		this.designStatus = designStatus;
	}

	public Integer[] getDesignMode() {
	    return designMode;
    }

	public void setDesignMode(Integer[] designMode) {
	    this.designMode = designMode;
    }
}
