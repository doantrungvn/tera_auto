package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


public class DecisionTable implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long decisionTbId;

	private Long projectId;
	
	private String projectName;
	
	private String projectIdAutocomplete;
	
	private Long moduleId;
	
	private String moduleName;
	
	private String moduleCode;
	
	private Integer moduleType;
	
	private String moduleIdAutocomplete;
	
	private String decisionTbName;
	
	private String decisionTbCode;
	
	private String remark;
	
	private Integer designStatus;
	
	private Integer designStatusParent;
	
	private String mode;
	
	private Long createdBy;
	
	private Timestamp createdDate;
	
	private Long updatedBy;
	
	private Timestamp updatedDate;
	
	private Timestamp sysDatetime;
	
	private boolean showImpactFlag;
	
	private List<BusinessDesign> listBD;
	
	private String listInput;
	
	private String listOutput;
	
	private String listItemCondition;
	
	private String listItemAction;
	
	private String listConditionGroup;
	
	private String listConditionItem;
	
	private Boolean actionDelete;
	
	private String methodInputName;
	
	private Long methodInputDatatype;
	
	private Integer methodInputStatus;
	
	private Long methodInputId;
	
	private String methodOutputName;
	
	private Long methodOutputDatatype;

	private Integer methodOutputStatus;

	private Long methodOutputId;

	private List<ProblemList> listOfProblem;

	// For generate source code
	private List<DecisionTableInputBean> inputLst;

	private List<DecisionTableOutputBean> outputLst;
	
	private String blogicDesignContent;
	
	private String author;

	/**
	 * @return the decisionTbId
	 */
	public Long getDecisionTbId() {
		return decisionTbId;
	}

	/**
	 * @param decisionTbId the decisionTbId to set
	 */
	public void setDecisionTbId(Long decisionTbId) {
		this.decisionTbId = decisionTbId;
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
	 * @return the decisionTbName
	 */
	public String getDecisionTbName() {
		return decisionTbName;
	}

	/**
	 * @param decisionTbName the decisionTbName to set
	 */
	public void setDecisionTbName(String decisionTbName) {
		this.decisionTbName = decisionTbName;
	}

	/**
	 * @return the decisionTbCode
	 */
	public String getDecisionTbCode() {
		return decisionTbCode;
	}

	/**
	 * @param decisionTbCode the decisionTbCode to set
	 */
	public void setDecisionTbCode(String decisionTbCode) {
		this.decisionTbCode = decisionTbCode;
	}

	/**
	 * @return the businessId
	 */

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

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
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
	 * @return the listInput
	 */
	public String getListInput() {
		return listInput;
	}

	/**
	 * @param listInput the listInput to set
	 */
	public void setListInput(String listInput) {
		this.listInput = listInput;
	}

	/**
	 * @return the listOutput
	 */
	public String getListOutput() {
		return listOutput;
	}

	/**
	 * @param listOutput the listOutput to set
	 */
	public void setListOutput(String listOutput) {
		this.listOutput = listOutput;
	}

	/**
	 * @return the listItemCondition
	 */
	public String getListItemCondition() {
		return listItemCondition;
	}

	/**
	 * @param listItemCondition the listItemCondition to set
	 */
	public void setListItemCondition(String listItemCondition) {
		this.listItemCondition = listItemCondition;
	}

	/**
	 * @return the listItemAction
	 */
	public String getListItemAction() {
		return listItemAction;
	}

	/**
	 * @param listItemAction the listItemAction to set
	 */
	public void setListItemAction(String listItemAction) {
		this.listItemAction = listItemAction;
	}

	/**
	 * @return the listConditionGroup
	 */
	public String getListConditionGroup() {
		return listConditionGroup;
	}

	/**
	 * @param listConditionGroup the listConditionGroup to set
	 */
	public void setListConditionGroup(String listConditionGroup) {
		this.listConditionGroup = listConditionGroup;
	}

	/**
	 * @return the listConditionItem
	 */
	public String getListConditionItem() {
		return listConditionItem;
	}

	/**
	 * @param listConditionItem the listConditionItem to set
	 */
	public void setListConditionItem(String listConditionItem) {
		this.listConditionItem = listConditionItem;
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
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
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
	 * @return the sysDatetime
	 */
	public Timestamp getSysDatetime() {
		return sysDatetime;
	}

	/**
	 * @param sysDatetime the sysDatetime to set
	 */
	public void setSysDatetime(Timestamp sysDatetime) {
		this.sysDatetime = sysDatetime;
	}

	/**
	 * @return the designStatus
	 */
	public Integer getDesignStatus() {
		return designStatus;
	}

	/**
	 * @param designStatus the designStatus to set
	 */
	public void setDesignStatus(Integer designStatus) {
		this.designStatus = designStatus;
	}

	/**
	 * @return the listBD
	 */
	public List<BusinessDesign> getListBD() {
		return listBD;
	}

	/**
	 * @param listBD the listBD to set
	 */
	public void setListBD(List<BusinessDesign> listBD) {
		this.listBD = listBD;
	}

	public Boolean getActionDelete() {
		return actionDelete;
	}

	public void setActionDelete(Boolean actionDelete) {
		this.actionDelete = actionDelete;
	}

	public Integer getDesignStatusParent() {
		return designStatusParent;
	}

	public void setDesignStatusParent(Integer designStatusParent) {
		this.designStatusParent = designStatusParent;
	}

	public List<ProblemList> getListOfProblem() {
		return listOfProblem;
	}

	public void setListOfProblem(List<ProblemList> listOfProblem) {
		this.listOfProblem = listOfProblem;
	}

	public String getMethodInputName() {
		return methodInputName;
	}

	public void setMethodInputName(String methodInputName) {
		this.methodInputName = methodInputName;
	}

	public Long getMethodInputDatatype() {
		return methodInputDatatype;
	}

	public void setMethodInputDatatype(Long methodInputDatatype) {
		this.methodInputDatatype = methodInputDatatype;
	}

	public Integer getMethodInputStatus() {
		return methodInputStatus;
	}

	public void setMethodInputStatus(Integer methodInputStatus) {
		this.methodInputStatus = methodInputStatus;
	}

	public Long getMethodInputId() {
		return methodInputId;
	}

	public void setMethodInputId(Long methodInputId) {
		this.methodInputId = methodInputId;
	}

	public String getMethodOutputName() {
		return methodOutputName;
	}

	public void setMethodOutputName(String methodOutputName) {
		this.methodOutputName = methodOutputName;
	}

	public Long getMethodOutputDatatype() {
		return methodOutputDatatype;
	}

	public void setMethodOutputDatatype(Long methodOutputDatatype) {
		this.methodOutputDatatype = methodOutputDatatype;
	}

	public Integer getMethodOutputStatus() {
		return methodOutputStatus;
	}

	public void setMethodOutputStatus(Integer methodOutputStatus) {
		this.methodOutputStatus = methodOutputStatus;
	}

	public Long getMethodOutputId() {
		return methodOutputId;
	}

	public void setMethodOutputId(Long methodOutputId) {
		this.methodOutputId = methodOutputId;
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

	/**
	 * @return the inputLst
	 */
	public List<DecisionTableInputBean> getInputLst() {
		return inputLst;
	}

	/**
	 * @param inputLst the inputLst to set
	 */
	public void setInputLst(List<DecisionTableInputBean> inputLst) {
		this.inputLst = inputLst;
	}

	/**
	 * @return the outputLst
	 */
	public List<DecisionTableOutputBean> getOutputLst() {
		return outputLst;
	}

	/**
	 * @param outputLst the outputLst to set
	 */
	public void setOutputLst(List<DecisionTableOutputBean> outputLst) {
		this.outputLst = outputLst;
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
	 * @return the blogicDesignContent
	 */
	public String getBlogicDesignContent() {
		return blogicDesignContent;
	}

	/**
	 * @param blogicDesignContent the blogicDesignContent to set
	 */
	public void setBlogicDesignContent(String blogicDesignContent) {
		this.blogicDesignContent = blogicDesignContent;
	}

	/**
	 * @return the showImpactFlag
	 */
	public boolean isShowImpactFlag() {
		return showImpactFlag;
	}

	/**
	 * @param showImpactFlag the showImpactFlag to set
	 */
	public void setShowImpactFlag(boolean showImpactFlag) {
		this.showImpactFlag = showImpactFlag;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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
}
