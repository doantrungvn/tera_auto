package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.List;

public class ExceptionComponent extends BlogicComponent implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long exceptionComponentId ;

	private Integer exceptionToType;

	private Long exceptionToId;
	
	private Integer exceptionToScreenPatternType;

	private String exceptionToIdAutocomplete = "";

	private String exceptionToName = "";

	private Long exceptionToIdRefer;

	private Integer transitionType;

	private List<ExceptionDetail> parameterInputBeans;

	private Long moduleId;

	private String moduleIdAutocomplete;

	// For generate exception details
	private String exceptionId;

	private String exceptionAssignContent;

	private String exceptionDirect;
	
	private Long exceptionFromId;
	
	private Long businessLogicId;

	public Long getExceptionComponentId() {
		return exceptionComponentId;
	}

	public void setExceptionComponentId(Long exceptionComponentId) {
		this.exceptionComponentId = exceptionComponentId;
	}

	public Integer getExceptionToType() {
		return exceptionToType;
	}

	public void setExceptionToType(Integer exceptionToType) {
		this.exceptionToType = exceptionToType;
	}

	public Integer getTransitionType() {
		return transitionType;
	}

	public void setTransitionType(Integer transitionType) {
		this.transitionType = transitionType;
	}

	public Long getExceptionToId() {
		return exceptionToId;
	}

	public void setExceptionToId(Long exceptionToId) {
		this.exceptionToId = exceptionToId;
	}

	public String getExceptionToIdAutocomplete() {
		return exceptionToIdAutocomplete;
	}

	public void setExceptionToIdAutocomplete(String exceptionToIdAutocomplete) {
		this.exceptionToIdAutocomplete = exceptionToIdAutocomplete;
	}

	public List<ExceptionDetail> getParameterInputBeans() {
		return parameterInputBeans;
	}

	public void setParameterInputBeans(List<ExceptionDetail> parameterInputBeans) {
		this.parameterInputBeans = parameterInputBeans;
	}

	public String getExceptionToName() {
		return exceptionToName;
	}

	public void setExceptionToName(String exceptionToName) {
		this.exceptionToName = exceptionToName;
	}

	public Long getExceptionToIdRefer() {
		return exceptionToIdRefer;
	}

	public void setExceptionToIdRefer(Long exceptionToIdRefer) {
		this.exceptionToIdRefer = exceptionToIdRefer;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleIdAutocomplete() {
		return moduleIdAutocomplete;
	}

	public void setModuleIdAutocomplete(String moduleIdAutocomplete) {
		this.moduleIdAutocomplete = moduleIdAutocomplete;
	}

	/**
	 * @return the exceptionAssignContent
	 */
	public String getExceptionAssignContent() {
		return exceptionAssignContent;
	}

	/**
	 * @param exceptionAssignContent the exceptionAssignContent to set
	 */
	public void setExceptionAssignContent(String exceptionAssignContent) {
		this.exceptionAssignContent = exceptionAssignContent;
	}

	/**
	 * @return the exceptionDirect
	 */
	public String getExceptionDirect() {
		return exceptionDirect;
	}

	/**
	 * @param exceptionDirect the exceptionDirect to set
	 */
	public void setExceptionDirect(String exceptionDirect) {
		this.exceptionDirect = exceptionDirect;
	}

	/**
	 * @return the exceptionId
	 */
	public String getExceptionId() {
		return exceptionId;
	}

	/**
	 * @param exceptionId the exceptionId to set
	 */
	public void setExceptionId(String exceptionId) {
		this.exceptionId = exceptionId;
	}

	public Integer getExceptionToScreenPatternType() {
		return exceptionToScreenPatternType;
	}

	public void setExceptionToScreenPatternType(Integer exceptionToScreenPatternType) {
		this.exceptionToScreenPatternType = exceptionToScreenPatternType;
	}

	public Long getExceptionFromId() {
		return exceptionFromId;
	}

	public void setExceptionFromId(Long exceptionFromId) {
		this.exceptionFromId = exceptionFromId;
	}

	public Long getBusinessLogicId() {
	    return businessLogicId;
    }

	public void setBusinessLogicId(Long businessLogicId) {
	    this.businessLogicId = businessLogicId;
    }

}
