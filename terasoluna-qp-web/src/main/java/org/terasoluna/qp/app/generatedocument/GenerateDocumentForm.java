package org.terasoluna.qp.app.generatedocument;

import java.io.Serializable;
import java.util.List;

import org.terasoluna.qp.domain.model.GenerateDocumentItem;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.Project;

public class GenerateDocumentForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private Project project;
	
	private Integer selectType = 1;
	
	private Module module;
	
	private String fileName;
	
	private List<Module> moduleList;
	
	private String jsonBackup;
	
	private GenerateDocumentItem generateDocumentItem;
	
	private List<GenerateDocumentItem> generateDocumentItemLst;
	
	private List<GenerateDocumentItem> generateDocumentProjectTypeRDLst;
	
	private List<GenerateDocumentItem> generateDocumentProjectTypeEDLst;
	
	private List<GenerateDocumentItem> generateDocumentModuleTypeEDLst;

	/**
	 * @return the moduleList
	 */
	public List<Module> getModuleList() {
		return moduleList;
	}

	/**
	 * @param moduleList the moduleList to set
	 */
	public void setModuleList(List<Module> moduleList) {
		this.moduleList = moduleList;
	}

	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * @return the generateDocumentItemLst
	 */
	public List<GenerateDocumentItem> getGenerateDocumentItemLst() {
		return generateDocumentItemLst;
	}

	/**
	 * @param generateDocumentItemLst the generateDocumentItemLst to set
	 */
	public void setGenerateDocumentItemLst(List<GenerateDocumentItem> generateDocumentItemLst) {
		this.generateDocumentItemLst = generateDocumentItemLst;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the selectType
	 */
	public Integer getSelectType() {
		return selectType;
	}

	/**
	 * @param selectType the selectType to set
	 */
	public void setSelectType(Integer selectType) {
		this.selectType = selectType;
	}

	/**
	 * @return the generateDocumentProjectTypeRDLst
	 */
	public List<GenerateDocumentItem> getGenerateDocumentProjectTypeRDLst() {
		return generateDocumentProjectTypeRDLst;
	}

	/**
	 * @param generateDocumentProjectTypeRDLst the generateDocumentProjectTypeRDLst to set
	 */
	public void setGenerateDocumentProjectTypeRDLst(
			List<GenerateDocumentItem> generateDocumentProjectTypeRDLst) {
		this.generateDocumentProjectTypeRDLst = generateDocumentProjectTypeRDLst;
	}

	/**
	 * @return the generateDocumentProjectTypeEDLst
	 */
	public List<GenerateDocumentItem> getGenerateDocumentProjectTypeEDLst() {
		return generateDocumentProjectTypeEDLst;
	}

	/**
	 * @param generateDocumentProjectTypeEDLst the generateDocumentProjectTypeEDLst to set
	 */
	public void setGenerateDocumentProjectTypeEDLst(
			List<GenerateDocumentItem> generateDocumentProjectTypeEDLst) {
		this.generateDocumentProjectTypeEDLst = generateDocumentProjectTypeEDLst;
	}

	/**
	 * @return the generateDocumentModuleTypeEDLst
	 */
	public List<GenerateDocumentItem> getGenerateDocumentModuleTypeEDLst() {
		return generateDocumentModuleTypeEDLst;
	}

	/**
	 * @param generateDocumentModuleTypeEDLst the generateDocumentModuleTypeEDLst to set
	 */
	public void setGenerateDocumentModuleTypeEDLst(
			List<GenerateDocumentItem> generateDocumentModuleTypeEDLst) {
		this.generateDocumentModuleTypeEDLst = generateDocumentModuleTypeEDLst;
	}

	/**
	 * @return the jsonBackup
	 */
	public String getJsonBackup() {
		return jsonBackup;
	}

	/**
	 * @param jsonBackup the jsonBackup to set
	 */
	public void setJsonBackup(String jsonBackup) {
		this.jsonBackup = jsonBackup;
	}

	/**
	 * @return the module
	 */
	public Module getModule() {
		return module;
	}

	/**
	 * @param module the module to set
	 */
	public void setModule(Module module) {
		this.module = module;
	}

	/**
	 * @return the generateDocumentItem
	 */
	public GenerateDocumentItem getGenerateDocumentItem() {
		return generateDocumentItem;
	}

	/**
	 * @param generateDocumentItem the generateDocumentItem to set
	 */
	public void setGenerateDocumentItem(GenerateDocumentItem generateDocumentItem) {
		this.generateDocumentItem = generateDocumentItem;
	}
}
