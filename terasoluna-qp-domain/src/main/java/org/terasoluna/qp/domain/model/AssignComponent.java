package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AssignComponent extends BlogicComponent implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long assignComponentId;

	private List<AssignDetail> details = new ArrayList<AssignDetail>();

	public Long getAssignComponentId() {
		return assignComponentId;
	}

	public void setAssignComponentId(Long assignComponentId) {
		this.assignComponentId = assignComponentId;
	}

	public List<AssignDetail> getDetails() {
		return details;
	}

	public void setDetails(List<AssignDetail> details) {
		this.details = details;
	}
}
