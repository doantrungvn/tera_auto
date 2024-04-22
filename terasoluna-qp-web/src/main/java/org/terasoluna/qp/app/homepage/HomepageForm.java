package org.terasoluna.qp.app.homepage;

import java.io.Serializable;

public class HomepageForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long projectId;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

}
