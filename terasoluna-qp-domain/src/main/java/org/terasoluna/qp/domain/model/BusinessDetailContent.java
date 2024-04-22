package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BusinessDetailContent implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long businessDetailContentId;

	private Long tblDesignId;

	private String tblDesignIdAutocomplete;

	private String tableCode;

	private Long columnId;

	private String columnIdAutocomplete;

	private String columnCode;

	private Integer parameterScope;

	private String parameterId;

	private String parameterIdAutocomplete;

	private Long businessCheckDetailId;

	private Integer dataType;

	private Integer operatorCode = 0;

	private List<BDParameterIndex> lstParameterIndex = new ArrayList<BDParameterIndex>();

	public Long getBusinessDetailContentId() {
		return businessDetailContentId;
	}

	public void setBusinessDetailContentId(Long businessDetailContentId) {
		this.businessDetailContentId = businessDetailContentId;
	}

	public Long getTblDesignId() {
		return tblDesignId;
	}

	public void setTblDesignId(Long tblDesignId) {
		this.tblDesignId = tblDesignId;
	}

	public Long getColumnId() {
		return columnId;
	}

	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}

	public Integer getParameterScope() {
		return parameterScope;
	}

	public void setParameterScope(Integer parameterScope) {
		this.parameterScope = parameterScope;
	}

	public Long getBusinessCheckDetailId() {
		return businessCheckDetailId;
	}

	public void setBusinessCheckDetailId(Long businessCheckDetailId) {
		this.businessCheckDetailId = businessCheckDetailId;
	}

	public String getTblDesignIdAutocomplete() {
		return tblDesignIdAutocomplete;
	}

	public void setTblDesignIdAutocomplete(String tblDesignIdAutocomplete) {
		this.tblDesignIdAutocomplete = tblDesignIdAutocomplete;
	}

	public String getColumnIdAutocomplete() {
		return columnIdAutocomplete;
	}

	public void setColumnIdAutocomplete(String columnIdAutocomplete) {
		this.columnIdAutocomplete = columnIdAutocomplete;
	}

	public String getParameterIdAutocomplete() {
		return parameterIdAutocomplete;
	}

	public void setParameterIdAutocomplete(String parameterIdAutocomplete) {
		this.parameterIdAutocomplete = parameterIdAutocomplete;
	}

	public String getParameterId() {
		return parameterId;
	}

	public void setParameterId(String parameterId) {
		this.parameterId = parameterId;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public String getTableCode() {
		return tableCode;
	}

	public String getColumnCode() {
		return columnCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}

	/**
	 * @return the operatorCode
	 */
	public Integer getOperatorCode() {
		return operatorCode;
	}

	/**
	 * @param operatorCode the operatorCode to set
	 */
	public void setOperatorCode(Integer operatorCode) {
		this.operatorCode = operatorCode;
	}

	public List<BDParameterIndex> getLstParameterIndex() {
	    return lstParameterIndex;
    }

	public void setLstParameterIndex(List<BDParameterIndex> lstParameterIndex) {
	    this.lstParameterIndex = lstParameterIndex;
    }

}
