package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExecutionInputValue implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long executionInputValueId;

	private Long executionComponentId;

	private Long sqlDesignInputId;

	private String sqlDesignInputCode;

	private String sqlDesignInputName;

	private Integer dataType;

	private Boolean arrayFlg;

	private Integer parameterScope;

	private String parameterId;

	private String parameterIdAutocomplete;

	private String itemSequenceNo;

	private String impactStatus;

	private Long sqlDesignInputIdRefer;

	private String sqlDesignInputCodeRefer;

	private String sqlDesignInputNameRefer;

	private Integer dataTypeRefer;

	private Boolean arrayFlgRefer;

	private Long sqlDesignIdRefer;

	private List<BDParameterIndex> lstParameterIndex = new ArrayList<BDParameterIndex>();

	public Long getExecutionInputValueId() {
		return executionInputValueId;
	}

	public void setExecutionInputValueId(Long executionInputValueId) {
		this.executionInputValueId = executionInputValueId;
	}

	public Long getExecutionComponentId() {
		return executionComponentId;
	}

	public void setExecutionComponentId(Long executionComponentId) {
		this.executionComponentId = executionComponentId;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Boolean getArrayFlg() {
		return arrayFlg;
	}

	public void setArrayFlg(Boolean arrayFlg) {
		this.arrayFlg = arrayFlg;
	}

	public Integer getParameterScope() {
		return parameterScope;
	}

	public void setParameterScope(Integer parameterScope) {
		this.parameterScope = parameterScope;
	}

	public String getParameterId() {
		return parameterId;
	}

	public void setParameterId(String parameterId) {
		this.parameterId = parameterId;
	}

	public String getParameterIdAutocomplete() {
		return parameterIdAutocomplete;
	}

	public void setParameterIdAutocomplete(String parameterIdAutocomplete) {
		this.parameterIdAutocomplete = parameterIdAutocomplete;
	}

	public Long getSqlDesignInputId() {
		return sqlDesignInputId;
	}

	public void setSqlDesignInputId(Long sqlDesignInputId) {
		this.sqlDesignInputId = sqlDesignInputId;
	}

	public String getSqlDesignInputCode() {
		return sqlDesignInputCode;
	}

	public void setSqlDesignInputCode(String sqlDesignInputCode) {
		this.sqlDesignInputCode = sqlDesignInputCode;
	}

	public String getSqlDesignInputName() {
		return sqlDesignInputName;
	}

	public void setSqlDesignInputName(String sqlDesignInputName) {
		this.sqlDesignInputName = sqlDesignInputName;
	}

	public String getImpactStatus() {
		return impactStatus;
	}

	public void setImpactStatus(String impactStatus) {
		this.impactStatus = impactStatus;
	}

	public Long getSqlDesignInputIdRefer() {
		return sqlDesignInputIdRefer;
	}

	public void setSqlDesignInputIdRefer(Long sqlDesignInputIdRefer) {
		this.sqlDesignInputIdRefer = sqlDesignInputIdRefer;
	}

	public String getSqlDesignInputCodeRefer() {
		return sqlDesignInputCodeRefer;
	}

	public void setSqlDesignInputCodeRefer(String sqlDesignInputCodeRefer) {
		this.sqlDesignInputCodeRefer = sqlDesignInputCodeRefer;
	}

	public String getSqlDesignInputNameRefer() {
		return sqlDesignInputNameRefer;
	}

	public void setSqlDesignInputNameRefer(String sqlDesignInputNameRefer) {
		this.sqlDesignInputNameRefer = sqlDesignInputNameRefer;
	}

	public Integer getDataTypeRefer() {
		return dataTypeRefer;
	}

	public void setDataTypeRefer(Integer dataTypeRefer) {
		this.dataTypeRefer = dataTypeRefer;
	}

	public Boolean getArrayFlgRefer() {
		return arrayFlgRefer;
	}

	public void setArrayFlgRefer(Boolean arrayFlgRefer) {
		this.arrayFlgRefer = arrayFlgRefer;
	}

	public Long getSqlDesignIdRefer() {
		return sqlDesignIdRefer;
	}

	public void setSqlDesignIdRefer(Long sqlDesignIdRefer) {
		this.sqlDesignIdRefer = sqlDesignIdRefer;
	}

	public String getItemSequenceNo() {
		return itemSequenceNo;
	}

	public void setItemSequenceNo(String itemSequenceNo) {
		this.itemSequenceNo = itemSequenceNo;
	}

	public List<BDParameterIndex> getLstParameterIndex() {
		return lstParameterIndex;
	}

	public void setLstParameterIndex(List<BDParameterIndex> lstParameterIndex) {
		this.lstParameterIndex = lstParameterIndex;
	}
}
