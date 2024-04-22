package org.terasoluna.qp.domain.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class ScreenTransition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String connectorSource;
	
	private String connectorDest;
	
	private String screenActionId;
	
	private Long screenActionIdSecond;
	
	private String jSonScreenTransition;
	
	private String target;
	
	private String source;
	
	private String navigateToBlogicText;
	
	private int submitMethodType;
	
	private Long navigateToBlogicId;
	
	private String connectionMsg;
	
	private String fromScreenId;
	private String toScreenId;
	private String moduleName;
	
	/// DatLD
	private String screenTransitionId;
	private String transitionName;
	private String transitionCode;
	private String fromScreen;
	private String toScreen;
	private Long moduleId;
	private int status;
	private int type;
	private String buttonOrLinkName;
	private String bLogicName;
	private Boolean flgViewDeleteTrans;
	
	public ScreenTransition() {
		
	}
	
	public ScreenTransition(String transitionName, String transitionCode,
			String fromScreen, String toScreen, Long moduleId, int type) {
		this.transitionName = transitionName;
		this.transitionCode = transitionCode;
		this.fromScreen = fromScreen;
		this.toScreen = toScreen;
		this.type = type;
		this.moduleId = moduleId;
	}
	
	public String getConnectorSource() {
		return connectorSource;
	}

	public void setConnectorSource(String connectorSource) {
		this.connectorSource = connectorSource;
	}

	public String getConnectorDest() {
		return connectorDest;
	}

	public void setConnectorDest(String connectorDest) {
		this.connectorDest = connectorDest;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	
	public int getSubmitMethodType() {
		return submitMethodType;
	}
	
	public void setSubmitMethodType(int submitMethodType) {
		this.submitMethodType = submitMethodType;
	}
	
	public String getConnectionMsg() {
		return connectionMsg;
	}
	
	public void setConnectionMsg(String connectionMsg) {
		this.connectionMsg = connectionMsg;
	}

	public Long getScreenActionIdSecond() {
		return screenActionIdSecond;
	}

	public void setScreenActionIdSecond(Long screenActionIdSecond) {
		this.screenActionIdSecond = screenActionIdSecond;
	}

	public String getScreenActionId() {
		return screenActionId;
	}

	public void setScreenActionId(String screenActionId) {
		this.screenActionId = screenActionId;
		if(StringUtils.isNumeric(screenActionId)) {
			this.screenActionIdSecond = Long.valueOf(screenActionId);
		}
	}

	public String getjSonScreenTransition() {
		return jSonScreenTransition;
	}

	public void setjSonScreenTransition(String jSonScreenTransition) {
		this.jSonScreenTransition = jSonScreenTransition;
	}

	public Long getNavigateToBlogicId() {
		return navigateToBlogicId;
	}

	public void setNavigateToBlogicId(Long navigateToBlogicId) {
		this.navigateToBlogicId = navigateToBlogicId;
	}

	public String getNavigateToBlogicText() {
		return navigateToBlogicText;
	}

	public void setNavigateToBlogicText(String navigateToBlogicText) {
		this.navigateToBlogicText = navigateToBlogicText;
	}

	public String getScreenTransitionId() {
		return screenTransitionId;
	}

	public void setScreenTransitionId(String screenTransitionId) {
		this.screenTransitionId = screenTransitionId;
	}

	public String getTransitionName() {
		return transitionName;
	}

	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}

	public String getTransitionCode() {
		return transitionCode;
	}

	public void setTransitionCode(String transitionCode) {
		this.transitionCode = transitionCode;
	}

	public String getFromScreen() {
		return fromScreen;
	}

	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	public String getToScreen() {
		return toScreen;
	}

	public void setToScreen(String toScreen) {
		this.toScreen = toScreen;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getFromScreenId() {
		return fromScreenId;
	}

	public void setFromScreenId(String fromScreenId) {
		this.fromScreenId = fromScreenId;
	}

	public String getToScreenId() {
		return toScreenId;
	}

	public void setToScreenId(String toScreenId) {
		this.toScreenId = toScreenId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getButtonOrLinkName() {
		return buttonOrLinkName;
	}

	public void setButtonOrLinkName(String buttonOrLinkName) {
		this.buttonOrLinkName = buttonOrLinkName;
	}

	public String getbLogicName() {
		return bLogicName;
	}

	public void setbLogicName(String bLogicName) {
		this.bLogicName = bLogicName;
	}

	public Boolean getFlgViewDeleteTrans() {
		return flgViewDeleteTrans;
	}

	public void setFlgViewDeleteTrans(Boolean flgViewDeleteTrans) {
		this.flgViewDeleteTrans = flgViewDeleteTrans;
	}
}
