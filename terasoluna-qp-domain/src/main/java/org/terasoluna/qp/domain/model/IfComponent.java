package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IfComponent extends BlogicComponent implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long ifComponentId;

	private List<IfConditionDetail> ifConditionDetails = new ArrayList<IfConditionDetail>();

	public Long getIfComponentId() {
		return ifComponentId;
	}

	public void setIfComponentId(Long ifComponentId) {
		this.ifComponentId = ifComponentId;
	}

	public List<IfConditionDetail> getIfConditionDetails() {
		return ifConditionDetails;
	}

	public void setIfConditionDetails(List<IfConditionDetail> ifConditionDetails) {
		this.ifConditionDetails = ifConditionDetails;
	}
}
