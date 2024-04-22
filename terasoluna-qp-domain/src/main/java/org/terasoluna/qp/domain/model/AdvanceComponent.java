package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdvanceComponent extends BlogicComponent implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long advanceComponentId;

	private String content;

	private String methodName;

	private List<AdvanceInputValue> parameterInputBeans = new ArrayList<AdvanceInputValue>();

	private List<AdvanceOutputValue> parameterOutputBeans = new ArrayList<AdvanceOutputValue>();

	private String author;

	public Long getAdvanceComponentId() {
		return advanceComponentId;
	}

	public void setAdvanceComponentId(Long advanceComponentId) {
		this.advanceComponentId = advanceComponentId;
	}

	public List<AdvanceInputValue> getParameterInputBeans() {
		return parameterInputBeans;
	}

	public void setParameterInputBeans(List<AdvanceInputValue> parameterInputBeans) {
		this.parameterInputBeans = parameterInputBeans;
	}

	public List<AdvanceOutputValue> getParameterOutputBeans() {
		return parameterOutputBeans;
	}

	public void setParameterOutputBeans(List<AdvanceOutputValue> parameterOutputBeans) {
		this.parameterOutputBeans = parameterOutputBeans;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}
