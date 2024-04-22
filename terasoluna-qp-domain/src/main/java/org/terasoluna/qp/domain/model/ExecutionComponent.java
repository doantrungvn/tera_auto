package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExecutionComponent extends BlogicComponent implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long executionComponentId;

	private Long sqlDesignId;

	private String sqlDesignIdAutocomplete;

	private String sqlDesignCode;

	private Long sqlDesignIdRefer;

	private String sqlDesignNameRefer;

	private String sqlDesignCodeRefer;
	
	private String sqlPattern;
	
	private Integer pageable;

	private Boolean concurrencyFlg;

	private List<ExecutionInputValue> parameterInputBeans = new ArrayList<ExecutionInputValue>();

	private List<ExecutionOutputValue> parameterOutputBeans = new ArrayList<ExecutionOutputValue>();

	private Long moduleId;

	private String moduleIdAutocomplete;

	private String moduleCode;
	
	private Integer returnType;

	private String strGenExecDetails;

	private Long businessLogicId;

	public Long getExecutionComponentId() {
		return executionComponentId;
	}

	public void setExecutionComponentId(Long executionComponentId) {
		this.executionComponentId = executionComponentId;
	}

	public Long getSqlDesignId() {
		return sqlDesignId;
	}

	public void setSqlDesignId(Long sqlDesignId) {
		this.sqlDesignId = sqlDesignId;
	}

	public String getSqlDesignIdAutocomplete() {
		return sqlDesignIdAutocomplete;
	}

	public void setSqlDesignIdAutocomplete(String sqlDesignIdAutocomplete) {
		this.sqlDesignIdAutocomplete = sqlDesignIdAutocomplete;
	}

	public String getSqlDesignCode() {
		return sqlDesignCode;
	}

	public void setSqlDesignCode(String sqlDesignCode) {
		this.sqlDesignCode = sqlDesignCode;
	}

	public Boolean getConcurrencyFlg() {
		return concurrencyFlg;
	}

	public void setConcurrencyFlg(Boolean concurrencyFlg) {
		this.concurrencyFlg = concurrencyFlg;
	}

	public List<ExecutionInputValue> getParameterInputBeans() {
		return parameterInputBeans;
	}

	public void setParameterInputBeans(List<ExecutionInputValue> parameterInputBeans) {
		this.parameterInputBeans = parameterInputBeans;
	}

	public List<ExecutionOutputValue> getParameterOutputBeans() {
		return parameterOutputBeans;
	}

	public void setParameterOutputBeans(
			List<ExecutionOutputValue> parameterOutputBeans) {
		this.parameterOutputBeans = parameterOutputBeans;
	}

	public Long getSqlDesignIdRefer() {
		return sqlDesignIdRefer;
	}

	public void setSqlDesignIdRefer(Long sqlDesignIdRefer) {
		this.sqlDesignIdRefer = sqlDesignIdRefer;
	}

	public String getSqlDesignNameRefer() {
		return sqlDesignNameRefer;
	}

	public void setSqlDesignNameRefer(String sqlDesignNameRefer) {
		this.sqlDesignNameRefer = sqlDesignNameRefer;
	}

	public String getSqlDesignCodeRefer() {
		return sqlDesignCodeRefer;
	}

	public void setSqlDesignCodeRefer(String sqlDesignCodeRefer) {
		this.sqlDesignCodeRefer = sqlDesignCodeRefer;
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

	public Integer getReturnType() {
		return returnType;
	}

	public void setReturnType(Integer returnType) {
		this.returnType = returnType;
	}

	public String getStrGenExecDetails() {
		return strGenExecDetails;
	}

	public void setStrGenExecDetails(String strGenExecDetails) {
		this.strGenExecDetails = strGenExecDetails;
	}

	/**
	 * @return the moduleCode
	 */
	public String getModuleCode() {
		return moduleCode;
	}

	/**
	 * @param moduleCode the moduleCode to set
	 */
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public Integer getPageable() {
		return pageable;
	}

	public void setPageable(Integer pageable) {
		this.pageable = pageable;
	}

	/**
	 * @return the sqlPattern
	 */
	public String getSqlPattern() {
		return sqlPattern;
	}

	/**
	 * @param sqlPattern the sqlPattern to set
	 */
	public void setSqlPattern(String sqlPattern) {
		this.sqlPattern = sqlPattern;
	}

	public Long getBusinessLogicId() {
	    return businessLogicId;
    }

	public void setBusinessLogicId(Long businessLogicId) {
	    this.businessLogicId = businessLogicId;
    }

}
