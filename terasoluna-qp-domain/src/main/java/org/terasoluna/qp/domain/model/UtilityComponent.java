package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UtilityComponent extends BlogicComponent implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long utilityComponentId;

	private Integer targetScope;

	private String targetId = "";

	private String targetIdAutocomplete = "";

	private Integer type;

	private Integer parameterScope;

	private String parameterId = "";

	private String parameterIdAutocomplete = "";

	private Integer indexScope;

	private String indexId = "";

	private String indexIdAutocomplete = "";

	private List<BDParameterIndex> lstParameterIndex = new ArrayList<BDParameterIndex>();

	private List<BDParameterIndex> lstIndex = new ArrayList<BDParameterIndex>();

	public Long getUtilityComponentId() {
		return utilityComponentId;
	}

	public void setUtilityComponentId(Long utilityComponentId) {
		this.utilityComponentId = utilityComponentId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public List<BDParameterIndex> getLstParameterIndex() {
		return lstParameterIndex;
	}

	public void setLstParameterIndex(List<BDParameterIndex> lstParameterIndex) {
		this.lstParameterIndex = lstParameterIndex;
	}

	public String getIndexId() {
		return indexId;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

	public List<BDParameterIndex> getLstIndex() {
		return lstIndex;
	}

	public void setLstIndex(List<BDParameterIndex> lstIndex) {
		this.lstIndex = lstIndex;
	}

	public String getIndexIdAutocomplete() {
		return indexIdAutocomplete;
	}

	public void setIndexIdAutocomplete(String indexIdAutocomplete) {
		this.indexIdAutocomplete = indexIdAutocomplete;
	}

	public Integer getIndexScope() {
		return indexScope;
	}

	public void setIndexScope(Integer indexScope) {
		this.indexScope = indexScope;
	}

}
