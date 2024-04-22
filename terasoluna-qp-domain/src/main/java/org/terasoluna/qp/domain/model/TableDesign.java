package org.terasoluna.qp.domain.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "org.terasoluna.qp.domain.service.tabledesign.SqlDesign")
public class TableDesign extends AffectChangedDesign{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long tableDesignId;
	
	private Long projectId;
	
	private String tableName;
	
	private String tableDesignIdAutocomplete;

	private String tableCode;
	
	private String projectName;
	
	private String columnsText;

	private float x;
	
	private float y;
	
	private String remark;
	
	private Long createdBy;
	
	private Timestamp createdDate;
	
	private Long updatedBy;
	
	private Timestamp updatedDate;
	
	private Timestamp systemTime;
	
	private Integer groupBasetypeId = 0;
	
	private String mode;
	
	private String projectIdAutocomplete;
	
	private Integer designStatus;
	
	private Integer type;
	
	private Boolean showImpactFlag = false;
	
	/**
	 * Name of type
	 */
	private String tableType;
	
	private Boolean actionDelete;
	
	private Boolean isDeleted;
	
	private int hasCompositeKey;
	
	private String storeForeignKeyTemp;
	
	private String flagRegister;
	
	private int commonColumn;
	
	private boolean hasAffect;
	
	private String statusImport;
	
	private String contentStatusImport;
	
	List<TableDesignKey> listTableDesignKey;

	List<SubjectArea> subjectAreas;

	List<TableDesignDetails> listTableDesignDetails;
	
	List<TableDesignDetails> listTableDesignDetailsChangeType;
	
	List<UserDefineCodelist> listTableCodelist;
	
	private String toForeignKeyType;
	
	private String author;
	
	public List<UserDefineCodelist> getListTableCodelist() {
		return listTableCodelist;
	}

	public void setListTableCodelist(List<UserDefineCodelist> listTableCodelist) {
		this.listTableCodelist = listTableCodelist;
	}

	private List<String> lstColumnCodeOfTable = new ArrayList<String>();
	
	public List<TableDesignDetails> getListTableDesignDetailsChangeType() {
		return listTableDesignDetailsChangeType;
	}

	public void setListTableDesignDetailsChangeType(
			List<TableDesignDetails> listTableDesignDetailsChangeType) {
		this.listTableDesignDetailsChangeType = listTableDesignDetailsChangeType;
	}

	List<TableDesignForeignKey> tableDesignForeignKeys;
	
	List<ValidationRule> validationRule;
	
	public String getProjectIdAutocomplete() {
		return projectIdAutocomplete;
	}

	public void setProjectIdAutocomplete(String projectIdAutocomplete) {
		this.projectIdAutocomplete = projectIdAutocomplete;
	}

	public List<TableDesignKey> getListTableDesignKey() {
		return listTableDesignKey;
	}

	public void setListTableDesignKey(List<TableDesignKey> listTableDesignKey) {
		this.listTableDesignKey = listTableDesignKey;
	}

	public List<SubjectArea> getSubjectAreas() {
		return subjectAreas;
	}

	public void setSubjectAreas(List<SubjectArea> subjectAreas) {
		this.subjectAreas = subjectAreas;
	}

	public List<TableDesignDetails> getListTableDesignDetails() {
		return listTableDesignDetails;
	}

	public List<ValidationRule> getValidationRule() {
		return validationRule;
	}

	public void setValidationRule(List<ValidationRule> validationRule) {
		this.validationRule = validationRule;
	}

	public void setListTableDesignDetails(
			List<TableDesignDetails> listTableDesignDetails) {
		this.listTableDesignDetails = listTableDesignDetails;
	}

	public List<TableDesignForeignKey> getTableDesignForeignKeys() {
		return tableDesignForeignKeys;
	}

	public void setTableDesignForeignKeys(
			List<TableDesignForeignKey> tableDesignForeignKeys) {
		this.tableDesignForeignKeys = tableDesignForeignKeys;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	private List<TableDesignDetails> listOfRow = new ArrayList<TableDesignDetails>();
	
	private List<TableDesignKey> listOfKey = new ArrayList<TableDesignKey>();

	public TableDesign() {

	}

	public TableDesign(String name, String code, int x, int y, String comment) {
		setTableName(name);
		setTableCode(code);
		setX(x);
		setY(y);
		setRemark(comment);
	}

	public Integer getGroupBasetypeId() {
		return groupBasetypeId;
	}

	public void setGroupBasetypeId(Integer groupBasetypeId) {
		this.groupBasetypeId = groupBasetypeId;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	@XmlAttribute(name = "name")
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String name) {
		this.tableName = name;
	}

	@XmlAttribute
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	@XmlAttribute
	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * @return the tableCode
	 */
	@XmlAttribute(name = "code")
	public String getTableCode() {
		return tableCode;
	}

	/**
	 * @param tableCode the tableCode to set
	 */
	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	@XmlElement(name = "comment")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String comment) {
		this.remark = comment;
	}

	@XmlElement(name = "row")
	public List<TableDesignDetails> getDetails() {
		return listOfRow;
	}

	public void setDetails(List<TableDesignDetails> lRow) {
		this.listOfRow = lRow;
	}

	@XmlElement(name = "key")
	public List<TableDesignKey> getTableKey() {
		return listOfKey;
	}

	public void setTableKey(List<TableDesignKey> ltableKey) {
		this.listOfKey = ltableKey;
	}

	@XmlAttribute(name = "tableid")
	public Long getTableDesignId() {
		return tableDesignId;
	}

	public void setTableDesignId(Long tableId) {
		this.tableDesignId = tableId;
	}
	
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	
	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	
	@XmlAttribute(name = "createdby")
	public String getCreatedByXml() {
		return createdBy != null?createdBy.toString():"";
	}

	public void setCreatedByXml(String createdby) {
		try
		{		
			this.createdBy = Long.parseLong(createdby);
		}
		catch (NumberFormatException ex)
		{
			this.createdBy = null;
		}
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	
	@XmlAttribute(name = "createddate")
	public String getCreatedDateXml() {
		return String.valueOf((createdDate != null)?createdDate.getTime():"");
	}

	public void setCreatedDateXml(String createddate) {
		try
		{		
			this.createdDate = new Timestamp(Long.parseLong(createddate));
		}
		catch (NumberFormatException ex)
		{
			this.createdDate = null;
		}
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	@XmlAttribute(name = "updatedby")
	public String getUpdatedByXml() {
		return updatedBy != null?updatedBy.toString():"";
	}

	public void setUpdatedByXml(String updatedby) {
		try
		{		
			this.updatedBy = Long.parseLong(updatedby);
		}
		catch (NumberFormatException ex)
		{
			this.updatedBy = null;
		}
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	@XmlAttribute(name = "updateddate")
	public String getUpdatedDateXml() {
		return String.valueOf((updatedDate != null)?updatedDate.getTime():"");
	}

	public void setUpdatedDateXml(String updateddate) {
		try
		{
			this.updatedDate = new Timestamp(Long.parseLong(updateddate));
		}
		catch (NumberFormatException ex)
		{
			this.updatedDate = null;
		}
	}
	
	public Timestamp getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(Timestamp systemTime) {
		this.systemTime = systemTime;
	}

	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();

		str.append("Table Infor:name=");
		str.append(getTableName());
		str.append("; Code=");
		str.append(getTableCode());

		return str.toString();
	}

	/**
	 * @return the tableDesignIdAutocomplete
	 */
	public String getTableDesignIdAutocomplete() {
		return tableDesignIdAutocomplete;
	}

	/**
	 * @param tableDesignIdAutocomplete the tableDesignIdAutocomplete to set
	 */
	public void setTableDesignIdAutocomplete(String tableDesignIdAutocomplete) {
		this.tableDesignIdAutocomplete = tableDesignIdAutocomplete;
	}

	@XmlAttribute(name = "designStatus")
	public Integer getDesignStatus() {
		return designStatus;
	}

	public void setDesignStatus(Integer designStatus) {
		this.designStatus = designStatus;
	}
	
	@Override
	public boolean equals(Object object) {
		boolean result = false;
		if (object == null || object.getClass() != getClass()) {
			result = false;
		} else {
			TableDesign tableDesign = (TableDesign) object;
			if (this.tableDesignId.equals(tableDesign.getTableDesignId())) {
				result = true;
			}
		}
		return result;
	}

	public Boolean getActionDelete() {
		return actionDelete;
	}

	public void setActionDelete(Boolean actionDelete) {
		this.actionDelete = actionDelete;
	}

	/**
	 * @return the columnsText
	 */
	public String getColumnsText() {
		return columnsText;
	}

	/**
	 * @param columnsText the columnsText to set
	 */
	public void setColumnsText(String columnsText) {
		this.columnsText = columnsText;
	}

	@XmlAttribute(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getHasCompositeKey() {
		return hasCompositeKey;
	}

	public void setHasCompositeKey(int hasCompositeKey) {
		this.hasCompositeKey = hasCompositeKey;
	}

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public List<String> getLstColumnCodeOfTable() {
		return lstColumnCodeOfTable;
	}

	public void setLstColumnCodeOfTable(List<String> lstColumnCodeOfTable) {
		this.lstColumnCodeOfTable = lstColumnCodeOfTable;
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
	
	@XmlAttribute(name = "usedCommonColumn")
	public int getCommonColumn() {
		return commonColumn;
	}

	public void setCommonColumn(int commonColumn) {
		this.commonColumn = commonColumn;
	}

	public boolean isHasAffect() {
		return hasAffect;
	}

	public void setHasAffect(boolean hasAffect) {
		this.hasAffect = hasAffect;
	}

	public String getContentStatusImport() {
		return contentStatusImport;
	}

	public void setContentStatusImport(String contentStatusImport) {
		this.contentStatusImport = contentStatusImport;
	}

	public String getStatusImport() {
		return statusImport;
	}

	public void setStatusImport(String statusImport) {
		this.statusImport = statusImport;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getToForeignKeyType() {
		return toForeignKeyType;
	}

	public void setToForeignKeyType(String toForeignKeyType) {
		this.toForeignKeyType = toForeignKeyType;
	}

	public Boolean getShowImpactFlag() {
		return showImpactFlag;
	}

	public void setShowImpactFlag(Boolean showImpactFlag) {
		this.showImpactFlag = showImpactFlag;
	}
	
}
