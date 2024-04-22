package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class DesignInformationDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long designInformationDetailId;
	
	private Long designInformationId;
	
	private String subtitle;
	
	private String remark;

	public Long getDesignInformationDetailId() {
		return designInformationDetailId;
	}

	public void setDesignInformationDetailId(Long designInformationDetailId) {
		this.designInformationDetailId = designInformationDetailId;
	}

	public Long getDesignInformationId() {
		return designInformationId;
	}

	public void setDesignInformationId(Long designInformationId) {
		this.designInformationId = designInformationId;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
