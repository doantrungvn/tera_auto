package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExecutionOutputValue implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long executionOutputValueId;

	private Long executionComponentId;

	private Long sqlDesignOutputId;

	private String sqlDesignOutputCode;

	private String sqlDesignOutputName;

	private Integer dataType;

	private Boolean arrayFlg;

	private Integer targetScope;

	private String targetId;

	private String targetIdAutocomplete;

	private String itemSequenceNo;

	private String impactStatus;

	private Long sqlDesignIdRefer;

	private Long sqlDesignOutputIdRefer;

	private String sqlDesignOutputCodeRefer;

	private String sqlDesignOutputNameRefer;

	private Integer dataTypeRefer;

	private Boolean arrayFlgRefer;

	private List<BDParameterIndex> lstTargetIndex = new ArrayList<BDParameterIndex>();

	public Long getExecutionOutputValueId() {
		return executionOutputValueId;
	}

	public void setExecutionOutputValueId(Long executionOutputValueId) {
		this.executionOutputValueId = executionOutputValueId;
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

	public Integer getTargetScope() {
		return targetScope;
	}

	public void setTargetScope(Integer targetScope) {
		this.targetScope = targetScope;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getTargetIdAutocomplete() {
		return targetIdAutocomplete;
	}

	public void setTargetIdAutocomplete(String targetIdAutocomplete) {
		this.targetIdAutocomplete = targetIdAutocomplete;
	}

	public Long getSqlDesignOutputId() {
		return sqlDesignOutputId;
	}

	public void setSqlDesignOutputId(Long sqlDesignOutputId) {
		this.sqlDesignOutputId = sqlDesignOutputId;
	}

	public String getSqlDesignOutputCode() {
		return sqlDesignOutputCode;
	}

	public void setSqlDesignOutputCode(String sqlDesignOutputCode) {
		this.sqlDesignOutputCode = sqlDesignOutputCode;
	}

	public String getSqlDesignOutputName() {
		return sqlDesignOutputName;
	}

	public void setSqlDesignOutputName(String sqlDesignOutputName) {
		this.sqlDesignOutputName = sqlDesignOutputName;
	}

	public String getImpactStatus() {
		return impactStatus;
	}

	public void setImpactStatus(String impactStatus) {
		this.impactStatus = impactStatus;
	}

	public Long getSqlDesignIdRefer() {
		return sqlDesignIdRefer;
	}

	public void setSqlDesignIdRefer(Long sqlDesignIdRefer) {
		this.sqlDesignIdRefer = sqlDesignIdRefer;
	}

	public Long getSqlDesignOutputIdRefer() {
		return sqlDesignOutputIdRefer;
	}

	public void setSqlDesignOutputIdRefer(Long sqlDesignOutputIdRefer) {
		this.sqlDesignOutputIdRefer = sqlDesignOutputIdRefer;
	}

	public String getSqlDesignOutputCodeRefer() {
		return sqlDesignOutputCodeRefer;
	}

	public void setSqlDesignOutputCodeRefer(String sqlDesignOutputCodeRefer) {
		this.sqlDesignOutputCodeRefer = sqlDesignOutputCodeRefer;
	}

	public String getSqlDesignOutputNameRefer() {
		return sqlDesignOutputNameRefer;
	}

	public void setSqlDesignOutputNameRefer(String sqlDesignOutputNameRefer) {
		this.sqlDesignOutputNameRefer = sqlDesignOutputNameRefer;
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

	public String getItemSequenceNo() {
		return itemSequenceNo;
	}

	public void setItemSequenceNo(String itemSequenceNo) {
		this.itemSequenceNo = itemSequenceNo;
	}

	public List<BDParameterIndex> getLstTargetIndex() {
	    return lstTargetIndex;
    }

	public void setLstTargetIndex(List<BDParameterIndex> lstTargetIndex) {
	    this.lstTargetIndex = lstTargetIndex;
    }
}
