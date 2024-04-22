package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdvanceOutputValue implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long advanceOutputValueId;

	private String outputBeanCode;

	private String outputBeanName;

	private Integer dataType;

	private Boolean arrayFlg = false;

	private String targetId;

	private String targetIdAutocomplete;

	private Integer targetScope;

	private Long parentAdvanceOutputValueId;

	private Long advanceComponentId;

	private List<BDParameterIndex> lstTargetIndex = new ArrayList<BDParameterIndex>();

	public Long getAdvanceOutputValueId() {
		return advanceOutputValueId;
	}

	public void setAdvanceOutputValueId(Long advanceOutputValueId) {
		this.advanceOutputValueId = advanceOutputValueId;
	}

	public String getOutputBeanCode() {
		return outputBeanCode;
	}

	public void setOutputBeanCode(String outputBeanCode) {
		this.outputBeanCode = outputBeanCode;
	}

	public String getOutputBeanName() {
		return outputBeanName;
	}

	public void setOutputBeanName(String outputBeanName) {
		this.outputBeanName = outputBeanName;
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

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public Integer getTargetScope() {
		return targetScope;
	}

	public void setTargetScope(Integer targetScope) {
		this.targetScope = targetScope;
	}

	public Long getParentAdvanceOutputValueId() {
		return parentAdvanceOutputValueId;
	}

	public void setParentAdvanceOutputValueId(Long parentAdvanceOutputValueId) {
		this.parentAdvanceOutputValueId = parentAdvanceOutputValueId;
	}

	public Long getAdvanceComponentId() {
		return advanceComponentId;
	}

	public void setAdvanceComponentId(Long advanceComponentId) {
		this.advanceComponentId = advanceComponentId;
	}

	public String getTargetIdAutocomplete() {
		return targetIdAutocomplete;
	}

	public void setTargetIdAutocomplete(String targetIdAutocomplete) {
		this.targetIdAutocomplete = targetIdAutocomplete;
	}

	public List<BDParameterIndex> getLstTargetIndex() {
		return lstTargetIndex;
	}

	public void setLstTargetIndex(List<BDParameterIndex> lstTargetIndex) {
		this.lstTargetIndex = lstTargetIndex;
	}

}
