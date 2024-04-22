package org.terasoluna.qp.app.decision;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;
import org.terasoluna.qp.app.common.validation.QpNamePattern;
import org.terasoluna.qp.app.common.validation.QpNameSize;
import org.terasoluna.qp.app.common.validation.QpRemarkSize;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.DecisionTableMessageConst;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.DecisionTableInputBean;
import org.terasoluna.qp.domain.model.DecisionTableOutputBean;
import org.terasoluna.qp.domain.model.ProblemList;

public class DecisionTableForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private String projectId;
	
	private String projectName;
	
	private String projectIdAutocomplete;
	
	private String moduleId;
	
	private String moduleName;
	
	private String moduleIdAutocomplete;
	
	private String decisionTbId;
	
	@NotEmpty(message = DecisionTableMessageConst.SC_DECISIONTABLE_0005)
	@QpNameSize(message = DecisionTableMessageConst.SC_DECISIONTABLE_0005)
	@QpNamePattern(message= DecisionTableMessageConst.SC_DECISIONTABLE_0005)
	private String decisionTbName;
	
	@NotEmpty(message = DecisionTableMessageConst.SC_DECISIONTABLE_0006)
	@QpNameSize(message = DecisionTableMessageConst.SC_DECISIONTABLE_0006)
	@QpNamePattern(message= DecisionTableMessageConst.SC_DECISIONTABLE_0006)
	private String decisionTbCode;
	
	@QpRemarkSize(message = CommonMessageConst.SC_SYS_0028)
	private String remark;
	
	private String mode;
	
	private String designStatus;
	
	private Boolean actionDelete;
	
	private String designStatusParent;
	
	private Long createdBy;
	
	private Timestamp createdDate;
	
	private Long updatedBy;
	
	private Timestamp updatedDate;
	
	private Timestamp sysDatetime;
	
	private List<BusinessDesign> listBD;
	
	private boolean showImpactFlag;
	
	private String listInput;
	
	private String listOutput;
	
	private String listItemCondition;
	
	private String listItemAction;
	
	private String listConditionGroup;
	
	private String listConditionItem;
	
	private List<ProblemList> listOfProblem;
	
    private List<DecisionTableInputBean> inputLst;

    private List<DecisionTableOutputBean> outputLst;

	/**
	 * @return the projectId
	 */
	public String getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
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
	 * @return the moduleId
	 */
	public String getModuleId() {
		return moduleId;
	}

	/**
	 * @param moduleId the moduleId to set
	 */
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
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
	 * @return the decisionTbId
	 */
	public String getDecisionTbId() {
		return decisionTbId;
	}

	/**
	 * @param decisionTbId the decisionTbId to set
	 */
	public void setDecisionTbId(String decisionTbId) {
		this.decisionTbId = decisionTbId;
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
	public String getDesignStatus() {
		return designStatus;
	}

	/**
	 * @param designStatus the designStatus to set
	 */
	public void setDesignStatus(String designStatus) {
		this.designStatus = designStatus;
	}

	/**
	 * @return the actionDelete
	 */
	public Boolean getActionDelete() {
		return actionDelete;
	}

	public List<BusinessDesign> getListBD() {
		return listBD;
	}

	public void setListBD(List<BusinessDesign> listBD) {
		this.listBD = listBD;
	}

	/**
	 * @param actionDelete the actionDelete to set
	 */
	public void setActionDelete(Boolean actionDelete) {
		this.actionDelete = actionDelete;
	}

	public String getDesignStatusParent() {
		return designStatusParent;
	}

	public void setDesignStatusParent(String designStatusParent) {
		this.designStatusParent = designStatusParent;
	}

	public List<ProblemList> getListOfProblem() {
		return listOfProblem;
	}

	public void setListOfProblem(List<ProblemList> listOfProblem) {
		this.listOfProblem = listOfProblem;
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

	
}