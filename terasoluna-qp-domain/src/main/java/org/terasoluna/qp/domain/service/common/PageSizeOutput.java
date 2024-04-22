package org.terasoluna.qp.domain.service.common;

import java.io.Serializable;

public class PageSizeOutput implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String value;
	private String status;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
