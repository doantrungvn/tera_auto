package org.terasoluna.qp.domain.service.graphicdatabasedesign;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.terasoluna.qp.domain.model.AffectChangedDesign;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.TableDesign;

public class GraphicDbDesign extends AffectChangedDesign implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String xml;

/*	private Long projectId;*/

	private String projectIdAutocomplete;

	private Long subjectAreaId;
	
	private Long moduleId;
	
	private List<TableDesign> tableDesigns;
	
	private List<BusinessDesign> lstBusinessDesigns;

	private Project project;
	
	private String nameMask;
	
	private int numberOfTable;
	
	private Timestamp updatedDate;
	
	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	/*public Long getProjectId() {
		return projectId;
	}*/

	/*public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}*/

	public String getProjectIdAutocomplete() {
		return projectIdAutocomplete;
	}

	public void setProjectIdAutocomplete(String projectIdAutocomplete) {
		this.projectIdAutocomplete = projectIdAutocomplete;
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

	public List<BusinessDesign> getLstBusinessDesigns() {
	    return lstBusinessDesigns;
    }

	public void setLstBusinessDesigns(List<BusinessDesign> lstBusinessDesigns) {
	    this.lstBusinessDesigns = lstBusinessDesigns;
    }

	public Long getModuleId() {
	    return moduleId;
    }

	public void setModuleId(Long moduleId) {
	    this.moduleId = moduleId;
    }

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getNameMask() {
		return nameMask;
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

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
}
