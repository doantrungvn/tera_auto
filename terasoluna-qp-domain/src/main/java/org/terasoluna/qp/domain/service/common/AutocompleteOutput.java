package org.terasoluna.qp.domain.service.common;

import org.terasoluna.qp.domain.model.Autocomplete;

public class AutocompleteOutput {

	private Autocomplete[] outputGroup;
	private String status;

	public Autocomplete[] getOutputGroup() {
		return outputGroup;
	}

	public void setOutputGroup(Autocomplete[] outputGroup) {
		this.outputGroup = outputGroup;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
