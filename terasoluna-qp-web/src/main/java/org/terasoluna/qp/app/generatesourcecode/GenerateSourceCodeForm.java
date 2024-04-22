package org.terasoluna.qp.app.generatesourcecode;

import java.io.Serializable;
import java.util.List;

import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.DecisionTable;
import org.terasoluna.qp.domain.model.GenerateSourceCodeItem;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.ScreenDesign;


/**
 * @author hunghx
 *
 */
public class GenerateSourceCodeForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private Project project;
	
	private Module module;
	
	private List<Module> modules;
	
	private String fileName;
	
	private byte[] fileContent;
	
	private Integer scopeGenerateSource = 1;
	
	private Integer generateType;

	private List<GenerateSourceCodeItem> generateSourceCodeItemLst;	
	
	private List<ScreenDesign> listOfScreenDesign;
	
	private List<BusinessDesign> listOfBusinessDesign;
	
	private List<DecisionTable> listOfDecisionTable;
    
    private String jsonBackup;
    
    private Boolean genAll;
    
    private Integer selectType;

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
	 * @return the modules
	 */
	public List<Module> getModules() {
		return modules;
	}

	/**
	 * @param modules the modules to set
	 */
	public void setModules(List<Module> modules) {
		this.modules = modules;
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
	 * @return the fileContent
	 */
	public byte[] getFileContent() {
		return fileContent;
	}

	/**
	 * @param fileContent the fileContent to set
	 */
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	/**
	 * @return the scopeGenerateSource
	 */
	public Integer getScopeGenerateSource() {
		return scopeGenerateSource;
	}

	/**
	 * @param scopeGenerateSource the scopeGenerateSource to set
	 */
	public void setScopeGenerateSource(Integer scopeGenerateSource) {
		this.scopeGenerateSource = scopeGenerateSource;
	}

	/**
	 * @return the generateType
	 */
	public Integer getGenerateType() {
		return generateType;
	}

	/**
	 * @param generateType the generateType to set
	 */
	public void setGenerateType(Integer generateType) {
		this.generateType = generateType;
	}

	/**
	 * @return the generateSourceCodeItemLst
	 */
	public List<GenerateSourceCodeItem> getGenerateSourceCodeItemLst() {
		return generateSourceCodeItemLst;
	}

	/**
	 * @param generateSourceCodeItemLst the generateSourceCodeItemLst to set
	 */
	public void setGenerateSourceCodeItemLst(
			List<GenerateSourceCodeItem> generateSourceCodeItemLst) {
		this.generateSourceCodeItemLst = generateSourceCodeItemLst;
	}

	/**
	 * @return the listOfScreenDesign
	 */
	public List<ScreenDesign> getListOfScreenDesign() {
		return listOfScreenDesign;
	}

	/**
	 * @param listOfScreenDesign the listOfScreenDesign to set
	 */
	public void setListOfScreenDesign(List<ScreenDesign> listOfScreenDesign) {
		this.listOfScreenDesign = listOfScreenDesign;
	}

	/**
	 * @return the listOfBusinessDesign
	 */
	public List<BusinessDesign> getListOfBusinessDesign() {
		return listOfBusinessDesign;
	}

	/**
	 * @param listOfBusinessDesign the listOfBusinessDesign to set
	 */
	public void setListOfBusinessDesign(List<BusinessDesign> listOfBusinessDesign) {
		this.listOfBusinessDesign = listOfBusinessDesign;
	}

	/**
	 * @return the listOfDecisionTable
	 */
	public List<DecisionTable> getListOfDecisionTable() {
		return listOfDecisionTable;
	}

	/**
	 * @param listOfDecisionTable the listOfDecisionTable to set
	 */
	public void setListOfDecisionTable(List<DecisionTable> listOfDecisionTable) {
		this.listOfDecisionTable = listOfDecisionTable;
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
     * @return the genAll
     */
    public Boolean getGenAll() {
        return genAll;
    }

    /**
     * @param genAll the genAll to set
     */
    public void setGenAll(Boolean genAll) {
        this.genAll = genAll;
    }

	public Integer getSelectType() {
		return selectType;
	}

	public void setSelectType(Integer selectType) {
		this.selectType = selectType;
	}
	
}
