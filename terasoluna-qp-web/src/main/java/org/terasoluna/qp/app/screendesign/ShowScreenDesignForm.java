package org.terasoluna.qp.app.screendesign;

import java.io.Serializable;

public class ShowScreenDesignForm implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	private String moduleId;

	private Long screenId;
	
	public Long getScreenId() {
		return screenId;
	}

	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	
}
