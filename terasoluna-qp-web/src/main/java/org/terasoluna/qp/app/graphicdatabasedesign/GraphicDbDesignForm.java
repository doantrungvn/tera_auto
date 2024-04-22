package org.terasoluna.qp.app.graphicdatabasedesign;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.terasoluna.qp.domain.model.AffectChangedDesign;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.service.common.XmlUtils;

public class GraphicDbDesignForm extends AffectChangedDesign implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String xml;

	private Long projectId;

	private String projectIdAutocomplete;

	private Long subjectAreaId;

	private int mode;

	private Long moduleId;

	private List<TableDesign> tableDesigns;

	private List<BusinessDesign> lstBlogic;

	private List<Long> ListScreenIds;

	private String jSonString;

	private String formJson;

	private Integer generateMode;

	private String nameMask;

	private int numberOfTable;

	private Boolean showImpactFlag = false;

	private Timestamp updatedDate;
	
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectIdAutocomplete() {
		return projectIdAutocomplete;
	}

	public void setProjectIdAutocomplete(String projectIdAutocomplete) {
		this.projectIdAutocomplete = projectIdAutocomplete;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public Long getSubjectAreaId() {
		return subjectAreaId;
	}

	public void setSubjectAreaId(Long subjectAreaId) {
		this.subjectAreaId = subjectAreaId;
	}

	public List<TableDesign> getTableDesigns() {
		return tableDesigns;
	}

	public void setTableDesigns(List<TableDesign> tableDesigns) {
		this.tableDesigns = tableDesigns;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public List<Long> getListScreenIds() {
		return ListScreenIds;
	}

	public void setListScreenIds(List<Long> listScreenIds) {
		ListScreenIds = listScreenIds;
	}

	public String getjSonString() {
		return jSonString;
	}

	public void setjSonString(String jSonString) {
		this.jSonString = jSonString;
	}

	public List<BusinessDesign> getLstBlogic() {
		return lstBlogic;
	}

	public void setLstBlogic(List<BusinessDesign> lstBlogic) {
		this.lstBlogic = lstBlogic;
	}

	public Integer getGenerateMode() {
		return generateMode;
	}

	public void setGenerateMode(Integer generateMode) {
		this.generateMode = generateMode;
	}

	public String getFormJson() {
		return formJson;
	}

	public void setFormJson(String formJson) {
		this.formJson = formJson;
	}

	public String getNameMask() {
		return XmlUtils.xmlEscapeText(nameMask);
	}

	public void setNameMask(String nameMask) {
		this.nameMask = nameMask;
	}

	public int getNumberOfTable() {
		return numberOfTable;
	}

	public void setNumberOfTable(int numberOfTable) {
		this.numberOfTable = numberOfTable;
	}

	public Boolean getShowImpactFlag() {
		return showImpactFlag;
	}

	public void setShowImpactFlag(Boolean showImpactFlag) {
		this.showImpactFlag = showImpactFlag;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

}
