package org.terasoluna.qp.app.generate;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;
import org.terasoluna.qp.app.message.ScreenDesignMessageConst;

public class GenerateDbAndBlogicForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long projectId;

	private Long moduleId;

	private Integer generateMode;

	@Valid
	@NotEmpty(message = ScreenDesignMessageConst.SC_SCREENDESIGN_0004)
	private List<Long> listScreenIds;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public List<Long> getListScreenIds() {
		return listScreenIds;
	}

	public void setListScreenIds(List<Long> listScreenIds) {
		this.listScreenIds = listScreenIds;
	}

	public Integer getGenerateMode() {
		return generateMode;
	}

	public void setGenerateMode(Integer generateMode) {
		this.generateMode = generateMode;
	}

}
