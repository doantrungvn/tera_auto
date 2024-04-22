package org.terasoluna.qp.domain.repository.screendesign;

import java.io.Serializable;

import org.terasoluna.qp.domain.model.ModuleTableMapping;
import org.terasoluna.qp.domain.model.Project;

public class ScreenDesignRegister implements Serializable {

	private static final long serialVersionUID = 1L;

	private String screenName;
	private Long moduleId;
	private Long projectId;
	private String screenCode;
	private Integer templateType;
	private Integer screenPatternType;
	private Integer confirmationType;
	private Integer completionType;
	private ModuleTableMapping[] moduleTableMappings;
	private Boolean isCopy;
	private Long copyScreenId;
	private String remark;
	private Long functionDesignId;
	private Integer xCoordinate;
	private Integer yCoordinate;
	private Integer designMode;
    
    private Project project;
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCopyScreenId() {
		return copyScreenId;
	}

	public void setCopyScreenId(Long copyScreenId) {
		this.copyScreenId = copyScreenId;
	}

	public ModuleTableMapping[] getModuleTableMappings() {
		return moduleTableMappings;
	}

	public void setModuleTableMappings(ModuleTableMapping[] moduleTableMappings) {
		this.moduleTableMappings = moduleTableMappings;
	}

	public Integer getConfirmationType() {
		return confirmationType;
	}

	public void setConfirmationType(Integer confirmationType) {
		this.confirmationType = confirmationType;
	}

	public Integer getCompletionType() {
		return completionType;
	}

	public void setCompletionType(Integer completionType) {
		this.completionType = completionType;
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

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public ScreenDesignRegister() {
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public Boolean getIsCopy() {
		return isCopy;
	}

	public void setIsCopy(Boolean isCopy) {
		this.isCopy = isCopy;
	}

	public String getScreenCode() {
		return screenCode;
	}

	public void setScreenCode(String screenCode) {
		this.screenCode = screenCode;
	}

	public Long getFunctionDesignId() {
	    return functionDesignId;
    }

	public void setFunctionDesignId(Long functionDesignId) {
	    this.functionDesignId = functionDesignId;
    }

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Integer getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(Integer xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public Integer getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(Integer yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public Integer getDesignMode() {
		return designMode;
	}

	public void setDesignMode(Integer designMode) {
		this.designMode = designMode;
	}
	
}
