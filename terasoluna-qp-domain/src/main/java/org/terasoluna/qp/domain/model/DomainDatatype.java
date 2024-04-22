package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class DomainDatatype implements Serializable {

	/**
	 * table domain_table_mapping
	 */
	private static final long serialVersionUID = 1L;
	private long domainDatatypeId;
	private String domainDatatypeName;
	private String domainDatatypeCode;
	private int status;
	private String remark;
	private Boolean isGenerate;
	private long tableDesignId;
	private String tableDesignName;

	private Long projectId;
	
	private String projectName;
	
	private int changeDesignFlg;
	
	private Long createdBy;
	
	private Timestamp createdDate;
	
	private Long updatedBy;
	
	private Timestamp updatedDate;
	
	private Timestamp systemTime;
	
	public Boolean getIsGenerate() {
		return isGenerate;
	}

	public void setIsGenerate(Boolean isGenerate) {
		this.isGenerate = isGenerate;
	}

	private List<DomainDatatypeItem> domainDatatypeItems;

	public long getDomainDatatypeId() {
		return domainDatatypeId;
	}

	public void setDomainDatatypeId(long domainDatatypeId) {
		this.domainDatatypeId = domainDatatypeId;
	}

	public String getDomainDatatypeName() {
		return domainDatatypeName;
	}

	public void setDomainDatatypeName(String domainDatatypeName) {
		this.domainDatatypeName = domainDatatypeName;
	}

	public String getDomainDatatypeCode() {
		return domainDatatypeCode;
	}

	public void setDomainDatatypeCode(String domainDatatypeCode) {
		this.domainDatatypeCode = domainDatatypeCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getTableDesignId() {
		return tableDesignId;
	}

	public void setTableDesignId(long tableDesignId) {
		this.tableDesignId = tableDesignId;
	}

	public String getTableDesignName() {
		return tableDesignName;
	}

	public void setTableDesignName(String tableDesignName) {
		this.tableDesignName = tableDesignName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<DomainDatatypeItem>  getDomainDatatypeItems() {
		return domainDatatypeItems;
	}

	public void setDomainDatatypeItems(List<DomainDatatypeItem> domainDatatypeItems) {
		this.domainDatatypeItems = domainDatatypeItems;
	}

	public int getChangeDesignFlg() {
		return changeDesignFlg;
	}

	public void setChangeDesignFlg(int changeDesignFlg) {
		this.changeDesignFlg = changeDesignFlg;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Timestamp getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(Timestamp systemTime) {
		this.systemTime = systemTime;
	}

}
