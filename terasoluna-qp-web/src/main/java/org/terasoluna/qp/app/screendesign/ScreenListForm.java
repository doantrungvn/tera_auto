package org.terasoluna.qp.app.screendesign;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class ScreenListForm implements Serializable{

	/**
	 * 
	 */
    private static final long serialVersionUID = 1L;
	@NotEmpty
	@Size(max = 200)
	private String moduleName;
	
	public ScreenListForm() {
	}
	
	public String getModuleName() {
		return moduleName;
	}
	
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
}
