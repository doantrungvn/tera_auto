package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BusinessCheckComponent extends BlogicComponent implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long businessCheckComponentId;

	private List<BusinessCheckDetail> businessCheckDetails = new ArrayList<BusinessCheckDetail>();
	public Long getBusinessCheckComponentId() {
		return businessCheckComponentId;
	}

	public void setBusinessCheckComponentId(Long businessCheckComponentId) {
		this.businessCheckComponentId = businessCheckComponentId;
	}

	public List<BusinessCheckDetail> getBusinessCheckDetails() {
		return businessCheckDetails;
	}

	public void setBusinessCheckDetails(List<BusinessCheckDetail> businessCheckDetails) {
		this.businessCheckDetails = businessCheckDetails;
	}

}
