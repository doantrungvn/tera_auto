package org.terasoluna.qp.domain.repository.businessdesign;

import java.io.Serializable;

public class BusinessDesignCriteria implements Serializable {

    private static final long serialVersionUID = 1L;
	
	private String businessLogicName;
	
	private String businessLogicCode;
	
	private Integer[] returnType = new Integer[0];
	
	private Long projectId;
	
	private Long moduleId;
	
	private Long screenId;
	
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

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Integer[] getReturnType() {
		return returnType;
	}

	public void setReturnType(Integer[] returnType) {
		this.returnType = returnType;
	}

	public Long getScreenId() {
		return screenId;
	}

	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}

	public Integer[] getDesignStatus() {
		return designStatus;
	}

	public void setDesignStatus(Integer[] designStatus) {
		this.designStatus = designStatus;
	}

	public Integer[] getBlogicType() {
		return blogicType;
	}

	public void setBlogicType(Integer[] blogicType) {
		this.blogicType = blogicType;
	}

	public Integer[] getDesignMode() {
	    return designMode;
    }

	public void setDesignMode(Integer[] designMode) {
	    this.designMode = designMode;
    }

}
