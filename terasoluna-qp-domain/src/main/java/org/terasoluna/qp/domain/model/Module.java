package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


public class Module implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long moduleId;
	
	private Long projectId;
	
	private String projectName;
	
	private String moduleCode;
	
	private String moduleName;
	
	private Integer moduleType;
	
	private Long businessTypeId;
	
	private String businessTypeName;
	
	private Integer status;
	
	private Integer confirmationType;
	
	private Integer completionType;
	
	private String remark;
	
	private Long createdBy;
	
	private Timestamp createdDate;
	
	private Long updatedBy;

	private String updatedByName;
	
	private Timestamp updatedDate;
	
	private Timestamp sysDatetime;
	
	private int selectedGenerate;

	private ModuleTableMapping[] moduleTableMappings;
	
	private Integer[] screenPatternTypes;
	
	private List<BusinessDesign> listBusinessDesign;
	
	private List<ScreenDesign> listScreenDesign;
	
	private List<FunctionDesign> listFunctionDesign;
	
	private GenerateDocumentItem generateDocumentItem;
	
	private Boolean defaultGeneration;
	
	private Integer defaultGenerationSetting;
	
    private String pathSourceDomain;
	
    private String pathSourceBatch;
    
    private Project project;
    
    private LanguageDesign languageDesign;
    
    private Long accountId;

    private List<BusinessDesign> affectedBusinessDesigns;
	
	private List<ScreenDesign> affectedScreenDesigns;
	 
	private List<DomainDesign> affectedDomainDesigns;
	
	private List<TableDesign> affectedTableDesigns;
	
	private List<SqlDesign> affectedSqlDesigns;
	
    private String author;
    
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
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the moduleCode
	 */
	public String getModuleCode() {
		return moduleCode;
	}

	/**
	 * @param moduleCode the moduleCode to set
	 */
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * @param moduleName the moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * @return the moduleType
	 */
	public Integer getModuleType() {
		return moduleType;
	}

	/**
	 * @param moduleType the moduleType to set
	 */
	public void setModuleType(Integer moduleType) {
		this.moduleType = moduleType;
	}

	/**
	 * @return the businessTypeId
	 */
	public Long getBusinessTypeId() {
		return businessTypeId;
	}

	/**
	 * @param businessTypeId the businessTypeId to set
	 */
	public void setBusinessTypeId(Long businessTypeId) {
		this.businessTypeId = businessTypeId;
	}

	/**
	 * @return the businessTypeName
	 */
	public String getBusinessTypeName() {
		return businessTypeName;
	}

	/**
	 * @param businessTypeName the businessTypeName to set
	 */
	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}
	
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the confirmationType
	 */
	public Integer getConfirmationType() {
		return confirmationType;
	}

	/**
	 * @param confirmationType the confirmationType to set
	 */
	public void setConfirmationType(Integer confirmationType) {
		this.confirmationType = confirmationType;
	}

	/**
	 * @return the completionType
	 */
	public Integer getCompletionType() {
		return completionType;
	}

	/**
	 * @param completionType the completionType to set
	 */
	public void setCompletionType(Integer completionType) {
		this.completionType = completionType;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public Timestamp getSysDatetime() {
		return sysDatetime;
	}

	public void setSysDatetime(Timestamp sysDatetime) {
		this.sysDatetime = sysDatetime;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the updatedBy
	 */
	public Long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the updatedDate
	 */
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the moduleTableMappings
	 */
	public ModuleTableMapping[] getModuleTableMappings() {
		return moduleTableMappings;
	}

	/**
	 * @param moduleTableMappings the moduleTableMappings to set
	 */
	public void setModuleTableMappings(ModuleTableMapping[] moduleTableMappings) {
		this.moduleTableMappings = moduleTableMappings;
	}

	/**
	 * @return the screenPatternTypes
	 */
	public Integer[] getScreenPatternTypes() {
		return screenPatternTypes;
	}

	/**
	 * @param screenPatternTypes the screenPatternTypes to set
	 */
	public void setScreenPatternTypes(Integer[] screenPatternTypes) {
		this.screenPatternTypes = screenPatternTypes;
	}

	public List<BusinessDesign> getListBusinessDesign() {
		return listBusinessDesign;
	}

	public void setListBusinessDesign(List<BusinessDesign> listBusinessDesign) {
		this.listBusinessDesign = listBusinessDesign;
	}

	public List<ScreenDesign> getListScreenDesign() {
		return listScreenDesign;
	}

	public void setListScreenDesign(List<ScreenDesign> listScreenDesign) {
		this.listScreenDesign = listScreenDesign;
	}

	public List<FunctionDesign> getListFunctionDesign() {
		return listFunctionDesign;
	}

	public void setListFunctionDesign(List<FunctionDesign> listFunctionDesign) {
		this.listFunctionDesign = listFunctionDesign;
	}

	public int getSelectedGenerate() {
		return selectedGenerate;
	}

	public void setSelectedGenerate(int selectedGenerate) {
		this.selectedGenerate = selectedGenerate;
	}

	/**
	 * @return the updatedByName
	 */
	public String getUpdatedByName() {
		return updatedByName;
	}

	/**
	 * @param updatedByName the updatedByName to set
	 */
	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
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

	public Boolean getDefaultGeneration() {
		return defaultGeneration;
	}

	public void setDefaultGeneration(Boolean defaultGeneration) {
		this.defaultGeneration = defaultGeneration;
	}

	public Integer getDefaultGenerationSetting() {
		return defaultGenerationSetting;
	}

	public void setDefaultGenerationSetting(Integer defaultGenerationSetting) {
		this.defaultGenerationSetting = defaultGenerationSetting;
	}

    /**
     * @return the pathSourceDomain
     */
    public String getPathSourceDomain() {
        return pathSourceDomain;
    }

    /**
     * @param pathSourceDomain the pathSourceDomain to set
     */
    public void setPathSourceDomain(String pathSourceDomain) {
        this.pathSourceDomain = pathSourceDomain;
    }

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public LanguageDesign getLanguageDesign() {
	    return languageDesign;
    }

	public void setLanguageDesign(LanguageDesign languageDesign) {
	    this.languageDesign = languageDesign;
    }

	public Long getAccountId() {
	    return accountId;
    }

	public void setAccountId(Long accountId) {
	    this.accountId = accountId;
    }
	public List<ScreenDesign> getAffectedScreenDesigns() {
		return affectedScreenDesigns;
	}

	public void setAffectedScreenDesigns(List<ScreenDesign> affectedScreenDesigns) {
		this.affectedScreenDesigns = affectedScreenDesigns;
	}

	public List<TableDesign> getAffectedTableDesigns() {
		return affectedTableDesigns;
	}

	public void setAffectedTableDesigns(List<TableDesign> affectedTableDesigns) {
		this.affectedTableDesigns = affectedTableDesigns;
	}

	public List<BusinessDesign> getAffectedBusinessDesigns() {
		return affectedBusinessDesigns;
	}

	public List<DomainDesign> getAffectedDomainDesigns() {
		return affectedDomainDesigns;
	}

	public void setAffectedBusinessDesigns(
			List<BusinessDesign> affectedBusinessDesigns) {
		this.affectedBusinessDesigns = affectedBusinessDesigns;
	}

	public void setAffectedDomainDesigns(List<DomainDesign> affectedDomainDesigns) {
		this.affectedDomainDesigns = affectedDomainDesigns;
	}

	public List<SqlDesign> getAffectedSqlDesigns() {
		return affectedSqlDesigns;
	}

	public void setAffectedSqlDesigns(List<SqlDesign> affectedSqlDesigns) {
		this.affectedSqlDesigns = affectedSqlDesigns;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPathSourceBatch() {
		return pathSourceBatch;
	}

	public void setPathSourceBatch(String pathSourceBatch) {
		this.pathSourceBatch = pathSourceBatch;
	}
}
