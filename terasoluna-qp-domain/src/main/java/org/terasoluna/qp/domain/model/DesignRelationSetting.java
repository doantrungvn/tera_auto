package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class DesignRelationSetting implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long designInformationId;
	
	private String moduleCode;

	public Long getDesignInformationId() {
		return designInformationId;
	}

	public void setDesignInformationId(Long designInformationId) {
		this.designInformationId = designInformationId;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
}
