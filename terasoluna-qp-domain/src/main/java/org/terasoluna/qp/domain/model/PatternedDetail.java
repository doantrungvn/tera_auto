package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class PatternedDetail implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long patternedDetailId;

	private Long patternedComponentId;

	private Integer componentType;

	private Integer itemSequenceNo;

	public Long getPatternedDetailId() {
		return patternedDetailId;
	}

	public void setPatternedDetailId(Long patternedDetailId) {
		this.patternedDetailId = patternedDetailId;
	}

	public Long getPatternedComponentId() {
		return patternedComponentId;
	}

	public void setPatternedComponentId(Long patternedComponentId) {
		this.patternedComponentId = patternedComponentId;
	}

	public Integer getComponentType() {
		return componentType;
	}

	public void setComponentType(Integer componentType) {
		this.componentType = componentType;
	}

	public Integer getItemSequenceNo() {
		return itemSequenceNo;
	}

	public void setItemSequenceNo(Integer itemSequenceNo) {
		this.itemSequenceNo = itemSequenceNo;
	}

}
