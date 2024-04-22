package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class BlogicComponent implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String label ;

	private String remark ;

	private Long sequenceLogicId;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getSequenceLogicId() {
		return sequenceLogicId;
	}

	public void setSequenceLogicId(Long sequenceLogicId) {
		this.sequenceLogicId = sequenceLogicId;
	}
}
