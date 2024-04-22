package org.terasoluna.qp.app.generate;

import java.io.Serializable;
import java.util.List;

import org.terasoluna.qp.domain.model.Module;

public class GenerateHTMLForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long projectId;
	
	private String projectName;
	
	private String projectCode;
	
	private String remark;
	
	private Long moduleId;

	private Integer generateMode;
	
	private Boolean selected;
	
	private List<Module> modules;
	
	private List<Long> listModuleId;
	
	private Integer status;
	
	private String scopeGenerate;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Integer getGenerateMode() {
		return generateMode;
	}

	public void setGenerateMode(Integer generateMode) {
		this.generateMode = generateMode;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}

	public List<Long> getListModuleId() {
		return listModuleId;
	}

	public void setListModuleId(List<Long> listModuleId) {
		this.listModuleId = listModuleId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getScopeGenerate() {
		return scopeGenerate;
	}

	public void setScopeGenerate(String scopeGenerate) {
		this.scopeGenerate = scopeGenerate;
	}
}
