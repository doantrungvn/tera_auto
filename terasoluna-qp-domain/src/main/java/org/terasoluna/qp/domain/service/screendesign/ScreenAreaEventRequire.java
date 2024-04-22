package org.terasoluna.qp.domain.service.screendesign;

public class ScreenAreaEventRequire {
	private String[] ifRequired;
	private String[] thenMustRequired;
	public String[] getIfRequired() {
		return ifRequired;
	}
	public void setIfRequired(String[] ifRequired) {
		this.ifRequired = ifRequired;
	}
	public String[] getThenMustRequired() {
		return thenMustRequired;
	}
	public void setThenMustRequired(String[] thenMustRequired) {
		this.thenMustRequired = thenMustRequired;
	}
}
