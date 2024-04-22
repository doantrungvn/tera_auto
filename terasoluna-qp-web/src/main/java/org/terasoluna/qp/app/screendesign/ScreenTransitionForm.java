package org.terasoluna.qp.app.screendesign;

import org.hibernate.validator.constraints.NotEmpty;

public class ScreenTransitionForm {
	public static interface IShowTransition {
	};
	
	private Long projectId;
	
	private Long moduleId;
	
	private String mode;
	
	private String projectIdAutocomplete;
	
	private String jSonScreenTransition;
	
	private Long target;
	
	private String blogicAutocomplete;
	
	private String fromScreenId;
	
	private boolean clicked = false;
	
	public Long getTarget() {
		return target;
	}

	public void setTarget(Long target) {
		this.target = target;
	}

	public Long getSource() {
		return source;
	}

	public void setSource(Long source) {
		this.source = source;
	}

	public int getSubmitMethodType() {
		return submitMethodType;
	}

	public void setSubmitMethodType(int submitMethodType) {
		this.submitMethodType = submitMethodType;
	}

	public Long getBusinessLogicId() {
		return businessLogicId;
	}

	public void setBusinessLogicId(Long businessLogicId) {
		this.businessLogicId = businessLogicId;
	}

	public String getConnectionMsg() {
		return connectionMsg;
	}

	public void setConnectionMsg(String connectionMsg) {
		this.connectionMsg = connectionMsg;
	}

	private Long source;
	
	private int submitMethodType;
	
	private Long businessLogicId;
	
	private String connectionMsg;
	
	@NotEmpty (message="sc.module.0007")
	private String moduleIdAutocomplete;
	
	//truongld:add for transfer information
	private String jsonInfo;
	
	/**
	 * @return the projectId
	 */
	public Long getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	/**
	 * @return the moduleId
	 */
	public Long getModuleId() {
		return moduleId;
	}
	
	/**
	 * @param moduleId the moduleId to set
	 */
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	
	/**
	 * @return the projectIdAutocomplete
	 */
	public String getProjectIdAutocomplete() {
		return projectIdAutocomplete;
	}

	/**
	 * @param projectIdAutocomplete the projectIdAutocomplete to set
	 */
	public void setProjectIdAutocomplete(String projectIdAutocomplete) {
		this.projectIdAutocomplete = projectIdAutocomplete;
	}
	
	/**
	 * @return the moduleIdAutocomplete
	 */
	public String getModuleIdAutocomplete() {
		return moduleIdAutocomplete;
	}

	/**
	 * @param moduleIdAutocomplete the moduleIdAutocomplete to set
	 */
	public void setModuleIdAutocomplete(String moduleIdAutocomplete) {
		this.moduleIdAutocomplete = moduleIdAutocomplete;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getJsonInfo() {
		return jsonInfo;
	}

	public void setJsonInfo(String jsonInfo) {
		this.jsonInfo = jsonInfo;
	}

	public String getjSonScreenTransition() {
		return jSonScreenTransition;
	}

	public void setjSonScreenTransition(String jSonScreenTransition) {
		this.jSonScreenTransition = jSonScreenTransition;
	}

	public String getBlogicAutocomplete() {
		return blogicAutocomplete;
	}

	public void setBlogicAutocomplete(String blogicAutocomplete) {
		this.blogicAutocomplete = blogicAutocomplete;
	}

	public String getFromScreenId() {
		return fromScreenId;
	}

	public void setFromScreenId(String fromScreenId) {
		this.fromScreenId = fromScreenId;
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

}
