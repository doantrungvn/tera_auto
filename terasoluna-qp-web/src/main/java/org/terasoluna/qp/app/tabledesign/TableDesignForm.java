package org.terasoluna.qp.app.tabledesign;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.validation.Valid;

import org.terasoluna.qp.app.common.validation.QpRemarkSize;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.AffectChangedDesign;
import org.terasoluna.qp.domain.model.TableDesignKey;
import org.terasoluna.qp.domain.model.UserDefineCodelist;
import org.terasoluna.qp.domain.service.graphicdatabasedesign.GroupDataTypeDB;

public class TableDesignForm extends AffectChangedDesign implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long tableDesignId;

	private String tableName;

	private String tableCode;

	@QpRemarkSize(message = CommonMessageConst.SC_SYS_0028)
	private String remark;

	private int mode;

	private String projectId;

	private Timestamp updatedDate;

	private String projectName;
	private String projectIdAutocomplete;
	List<TableDesignKey> listTableDesignKey;

	List<UserDefineCodelist> listTableCodelist;

	@Valid
	List<SubjectAreaForm> subjectAreas;

	@Valid
	List<TableDesignDetailsForm> listTableDesignDetails;

	private List<GroupDataTypeDB> listGroupOfDatatype;

	@Valid
	List<TableDesignForeignKeyForm> tableDesignForeignKeys;

	private String designStatus;

	private Integer type;

	private Boolean actionDelete;

	private int hasCompositeKey;

	private String storeForeignKeyTemp;

	private String flagRegister;

	private int commonColumn;

	private Boolean showImpactFlag = false;
	
	private String toForeignKeyType;

	public List<UserDefineCodelist> getListTableCodelist() {
		return listTableCodelist;
	}

	public void setListTableCodelist(List<UserDefineCodelist> listTableCodelist) {
		this.listTableCodelist = listTableCodelist;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<SubjectAreaForm> getSubjectAreas() {
		return subjectAreas;
	}

	public void setSubjectAreas(List<SubjectAreaForm> subjectAreas) {
		this.subjectAreas = subjectAreas;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectIdAutocomplete() {
		return projectIdAutocomplete;
	}

	public void setProjectIdAutocomplete(String projectIdAutocomplete) {
		this.projectIdAutocomplete = projectIdAutocomplete;
	}

	public List<TableDesignDetailsForm> getListTableDesignDetails() {
		return listTableDesignDetails;
	}

	public void setListTableDesignDetails(List<TableDesignDetailsForm> listTableDesignDetails) {
		this.listTableDesignDetails = listTableDesignDetails;
	}

	public List<TableDesignKey> getListTableDesignKey() {
		return listTableDesignKey;
	}

	public void setListTableDesignKey(List<TableDesignKey> listTableDesignKey) {
		this.listTableDesignKey = listTableDesignKey;
	}

	public List<TableDesignForeignKeyForm> getTableDesignForeignKeys() {
		return tableDesignForeignKeys;
	}

	public void setTableDesignForeignKeys(List<TableDesignForeignKeyForm> tableDesignForeignKeys) {
		this.tableDesignForeignKeys = tableDesignForeignKeys;
	}

	public String getDesignStatus() {
		return designStatus;
	}

	public void setDesignStatus(String designStatus) {
		this.designStatus = designStatus;
	}

	public Long getTableDesignId() {
		return tableDesignId;
	}

	public void setTableDesignId(Long tableDesignId) {
		this.tableDesignId = tableDesignId;
	}

	public List<GroupDataTypeDB> getListGroupOfDatatype() {
		return listGroupOfDatatype;
	}

	public void setListGroupOfDatatype(List<GroupDataTypeDB> listGroupOfDatatype) {
		this.listGroupOfDatatype = listGroupOfDatatype;
	}

	public Boolean getActionDelete() {
		return actionDelete;
	}

	public void setActionDelete(Boolean actionDelete) {
		this.actionDelete = actionDelete;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public int getHasCompositeKey() {
		return hasCompositeKey;
	}

	public void setHasCompositeKey(int hasCompositeKey) {
		this.hasCompositeKey = hasCompositeKey;
	}

	public String getStoreForeignKeyTemp() {
		return storeForeignKeyTemp;
	}

	public void setStoreForeignKeyTemp(String storeForeignKeyTemp) {
		this.storeForeignKeyTemp = storeForeignKeyTemp;
	}

	public String getFlagRegister() {
		return flagRegister;
	}

	public void setFlagRegister(String flagRegister) {
		this.flagRegister = flagRegister;
	}

	public int getCommonColumn() {
		return commonColumn;
	}

	public void setCommonColumn(int commonColumn) {
		this.commonColumn = commonColumn;
	}

	public Boolean getShowImpactFlag() {
		return showImpactFlag;
	}

	public void setShowImpactFlag(Boolean showImpactFlag) {
		this.showImpactFlag = showImpactFlag;
	}

	public String getToForeignKeyType() {
		return toForeignKeyType;
	}

	public void setToForeignKeyType(String toForeignKeyType) {
		this.toForeignKeyType = toForeignKeyType;
	}

}
