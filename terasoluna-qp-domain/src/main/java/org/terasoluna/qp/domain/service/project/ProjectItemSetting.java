package org.terasoluna.qp.domain.service.project;

import java.io.Serializable;

public class ProjectItemSetting implements Serializable {
	private static final long serialVersionUID = 1L;
	private String type;
	private String position;
	private String hoverStyle;
	private String style;
	private String labelNameAutocomplete;
	private String labelName;
	private String moduleIdAutocomplete;
	private String moduleId;
	private String navigateToAutocomplete;
	private String navigateTo;
	private String convertTo;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getHoverStyle() {
		return hoverStyle;
	}
	public void setHoverStyle(String hoverStyle) {
		this.hoverStyle = hoverStyle;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getLabelNameAutocomplete() {
		return labelNameAutocomplete;
	}
	public void setLabelNameAutocomplete(String labelNameAutocomplete) {
		this.labelNameAutocomplete = labelNameAutocomplete;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public String getModuleIdAutocomplete() {
		return moduleIdAutocomplete;
	}
	public void setModuleIdAutocomplete(String moduleIdAutocomplete) {
		this.moduleIdAutocomplete = moduleIdAutocomplete;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getNavigateToAutocomplete() {
		return navigateToAutocomplete;
	}
	public void setNavigateToAutocomplete(String navigateToAutocomplete) {
		this.navigateToAutocomplete = navigateToAutocomplete;
	}
	public String getNavigateTo() {
		return navigateTo;
	}
	public void setNavigateTo(String navigateTo) {
		this.navigateTo = navigateTo;
	}
	public String getConvertTo() {
		return convertTo;
	}
	public void setConvertTo(String convertTo) {
		this.convertTo = convertTo;
	}
}
