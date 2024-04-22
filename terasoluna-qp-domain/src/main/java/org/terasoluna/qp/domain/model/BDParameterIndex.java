package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class BDParameterIndex implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long bdParameterIndexId;

	private Integer tableType;

	private Long tableId;

	private String parameterId;

	private Integer parameterIndexType;

	private String parameterIndexId;

	private String parameterIndexIdAutocomplete;

	private Long businessLogicId;

	public Long getBdParameterIndexId() {
		return bdParameterIndexId;
	}

	public void setBdParameterIndexId(Long bdParameterIndexId) {
		this.bdParameterIndexId = bdParameterIndexId;
	}

	public Integer getTableType() {
		return tableType;
	}

	public void setTableType(Integer tableType) {
		this.tableType = tableType;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public String getParameterId() {
		return parameterId;
	}

	public void setParameterId(String parameterId) {
		this.parameterId = parameterId;
	}

	public Integer getParameterIndexType() {
		return parameterIndexType;
	}

	public void setParameterIndexType(Integer parameterIndexType) {
		this.parameterIndexType = parameterIndexType;
	}

	public String getParameterIndexId() {
		return parameterIndexId;
	}

	public void setParameterIndexId(String parameterIndexId) {
		this.parameterIndexId = parameterIndexId;
	}

	public String getParameterIndexIdAutocomplete() {
		return parameterIndexIdAutocomplete;
	}

	public void setParameterIndexIdAutocomplete(String parameterIndexIdAutocomplete) {
		this.parameterIndexIdAutocomplete = parameterIndexIdAutocomplete;
	}

	public Long getBusinessLogicId() {
	    return businessLogicId;
    }

	public void setBusinessLogicId(Long businessLogicId) {
	    this.businessLogicId = businessLogicId;
    }

}
