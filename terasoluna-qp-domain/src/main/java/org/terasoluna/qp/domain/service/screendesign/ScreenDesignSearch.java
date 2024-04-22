package org.terasoluna.qp.domain.service.screendesign;

import java.io.Serializable;

import org.terasoluna.qp.domain.model.MessageDesign;

public class ScreenDesignSearch implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long screenId;

	private String screenCode;

	private MessageDesign messageDesign;
	
	private Long moduleId;
	
	private Long functionDesignId;

	private String screenUrlCode;

	private Integer screenPatternType;

	private Integer templateType;

	private String moduleName;
	
	private Integer messageDesignId;
	
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public ScreenDesignSearch() {

	}

	public Long getScreenId() {
		return screenId;
	}

	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}

	public String getScreenCode() {
		return screenCode;
	}

	public void setScreenCode(String screenCode) {
		this.screenCode = screenCode;
	}

	public MessageDesign getMessageDesign() {
		return messageDesign;
	}

	public void setMessageDesign(MessageDesign messageDesign) {
		this.messageDesign = messageDesign;
	}

	public String getScreenUrlCode() {
		return screenUrlCode;
	}

	public void setScreenUrlCode(String screenUrlCode) {
		this.screenUrlCode = screenUrlCode;
	}

	public Integer getScreenPatternType() {
		return screenPatternType;
	}

	public void setScreenPatternType(Integer screenPatternType) {
		this.screenPatternType = screenPatternType;
	}

	public Integer getTemplateType() {
		return templateType;
	}

	public void setTemplateType(Integer templateType) {
		this.templateType = templateType;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Integer getMessageDesignId() {
		return messageDesignId;
	}

	public void setMessageDesignId(Integer messageDesignId) {
		this.messageDesignId = messageDesignId;
	}

	public Long getFunctionDesignId() {
		return functionDesignId;
	}

	public void setFunctionDesignId(Long functionDesignId) {
		this.functionDesignId = functionDesignId;
	}

}
