package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class SqlDesignParameters implements Serializable {
	
	private static final long serialVersionUID = 2025517860132670268L;
	private Long sqlDesignParameterId;
	private String parameterCode;
	private String parameterValue;
	public Long getSqlDesignParameterId() {
		return sqlDesignParameterId;
	}
	public void setSqlDesignParameterId(Long sqlDesignParameterId) {
		this.sqlDesignParameterId = sqlDesignParameterId;
	}
	public String getParameterCode() {
		return parameterCode;
	}
	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
}
