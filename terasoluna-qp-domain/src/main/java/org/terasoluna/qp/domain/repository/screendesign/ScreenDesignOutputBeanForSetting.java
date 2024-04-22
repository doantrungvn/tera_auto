package org.terasoluna.qp.domain.repository.screendesign;

public class ScreenDesignOutputBeanForSetting {
	private static final long serialVersionUID = 1L;
	private String label;
	private String shortLabel;
	private Long value;
	private int type;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getShortLabel() {
		return shortLabel;
	}
	public void setShortLabel(String shortLabel) {
		this.shortLabel = shortLabel;
	}
	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
