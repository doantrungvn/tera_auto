package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.List;

public class NavigatorComponent extends BlogicComponent implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long navigatorComponentId ;

	private Integer navigatorToType;

	private Long navigatorToId;
	
	private Integer navigatorToScreenPatternType;

	private String navigatorToIdAutocomplete = "";

	private String navigatorToName = "";

	private Long navigatorToIdRefer;

	private Integer transitionType;

	private List<NavigatorDetail> parameterInputBeans;

	private Long moduleId;

	private String moduleIdAutocomplete;

	// For generate navigator details
	private String navigatorId;

	private String navigatorAssignContent;

	private String navigatorDirect;
	
	private Long navigatorFromId;
	
	private Long businessLogicId;

	public Long getNavigatorComponentId() {
		return navigatorComponentId;
	}

	public void setNavigatorComponentId(Long navigatorComponentId) {
		this.navigatorComponentId = navigatorComponentId;
	}

	public Integer getNavigatorToType() {
		return navigatorToType;
	}

	public void setNavigatorToType(Integer navigatorToType) {
		this.navigatorToType = navigatorToType;
	}

	public Integer getTransitionType() {
		return transitionType;
	}

	public void setTransitionType(Integer transitionType) {
		this.transitionType = transitionType;
	}

	public Long getNavigatorToId() {
		return navigatorToId;
	}

	public void setNavigatorToId(Long navigatorToId) {
		this.navigatorToId = navigatorToId;
	}

	public String getNavigatorToIdAutocomplete() {
		return navigatorToIdAutocomplete;
	}

	public void setNavigatorToIdAutocomplete(String navigatorToIdAutocomplete) {
		this.navigatorToIdAutocomplete = navigatorToIdAutocomplete;
	}

	public List<NavigatorDetail> getParameterInputBeans() {
		return parameterInputBeans;
	}

	public void setParameterInputBeans(List<NavigatorDetail> parameterInputBeans) {
		this.parameterInputBeans = parameterInputBeans;
	}

	public String getNavigatorToName() {
		return navigatorToName;
	}

	public void setNavigatorToName(String navigatorToName) {
		this.navigatorToName = navigatorToName;
	}

	public Long getNavigatorToIdRefer() {
		return navigatorToIdRefer;
	}

	public void setNavigatorToIdRefer(Long navigatorToIdRefer) {
		this.navigatorToIdRefer = navigatorToIdRefer;
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
	 * @return the navigatorAssignContent
	 */
	public String getNavigatorAssignContent() {
		return navigatorAssignContent;
	}

	/**
	 * @param navigatorAssignContent the navigatorAssignContent to set
	 */
	public void setNavigatorAssignContent(String navigatorAssignContent) {
		this.navigatorAssignContent = navigatorAssignContent;
	}

	/**
	 * @return the navigatorDirect
	 */
	public String getNavigatorDirect() {
		return navigatorDirect;
	}

	/**
	 * @param navigatorDirect the navigatorDirect to set
	 */
	public void setNavigatorDirect(String navigatorDirect) {
		this.navigatorDirect = navigatorDirect;
	}

	/**
	 * @return the navigatorId
	 */
	public String getNavigatorId() {
		return navigatorId;
	}

	/**
	 * @param navigatorId the navigatorId to set
	 */
	public void setNavigatorId(String navigatorId) {
		this.navigatorId = navigatorId;
	}

	public Integer getNavigatorToScreenPatternType() {
		return navigatorToScreenPatternType;
	}

	public void setNavigatorToScreenPatternType(Integer navigatorToScreenPatternType) {
		this.navigatorToScreenPatternType = navigatorToScreenPatternType;
	}

	public Long getNavigatorFromId() {
		return navigatorFromId;
	}

	public void setNavigatorFromId(Long navigatorFromId) {
		this.navigatorFromId = navigatorFromId;
	}

	public Long getBusinessLogicId() {
	    return businessLogicId;
    }

	public void setBusinessLogicId(Long businessLogicId) {
	    this.businessLogicId = businessLogicId;
    }

}
