package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.List;

public class CommonComponent extends BlogicComponent implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long commonComponentId;

	private Long businessLogicId;

	private String businessLogicIdAutocomplete;

	private String businessLogicCode;

	private Long businessLogicIdRefer;

	private String businessLogicCodeRefer;

	private String businessLogicNameRefer;

	private List<CommonInputValue> parameterInputBeans;

	private List<CommonOutputValue> parameterOutputBeans;

	public Long getCommonComponentId() {
		return commonComponentId;
	}

	public void setCommonComponentId(Long commonComponentId) {
		this.commonComponentId = commonComponentId;
	}

	public Long getBusinessLogicId() {
		return businessLogicId;
	}

	public void setBusinessLogicId(Long businessLogicId) {
		this.businessLogicId = businessLogicId;
	}

	public String getBusinessLogicIdAutocomplete() {
		return businessLogicIdAutocomplete;
	}

	public void setBusinessLogicIdAutocomplete(String businessLogicIdAutocomplete) {
		this.businessLogicIdAutocomplete = businessLogicIdAutocomplete;
	}

	public String getBusinessLogicCode() {
		return businessLogicCode;
	}

	public void setBusinessLogicCode(String businessLogicCode) {
		this.businessLogicCode = businessLogicCode;
	}

	public List<CommonInputValue> getParameterInputBeans() {
		return parameterInputBeans;
	}

	public void setParameterInputBeans(List<CommonInputValue> parameterInputBeans) {
		this.parameterInputBeans = parameterInputBeans;
	}

	public List<CommonOutputValue> getParameterOutputBeans() {
		return parameterOutputBeans;
	}

	public void setParameterOutputBeans(List<CommonOutputValue> parameterOutputBeans) {
		this.parameterOutputBeans = parameterOutputBeans;
	}

	public Long getBusinessLogicIdRefer() {
		return businessLogicIdRefer;
	}

	public void setBusinessLogicIdRefer(Long businessLogicIdRefer) {
		this.businessLogicIdRefer = businessLogicIdRefer;
	}

	public String getBusinessLogicNameRefer() {
		return businessLogicNameRefer;
	}

	public void setBusinessLogicNameRefer(String businessLogicNameRefer) {
		this.businessLogicNameRefer = businessLogicNameRefer;
	}

	public String getBusinessLogicCodeRefer() {
		return businessLogicCodeRefer;
	}

	public void setBusinessLogicCodeRefer(String businessLogicCodeRefer) {
		this.businessLogicCodeRefer = businessLogicCodeRefer;
	}

}
