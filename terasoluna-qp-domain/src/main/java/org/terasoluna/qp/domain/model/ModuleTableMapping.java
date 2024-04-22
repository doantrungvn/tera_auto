package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class ModuleTableMapping implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long moduleId;
	
	private Long moduleTableMappingId;
	
	private Long tblDesignId;
	
	private String tblDesignName;
	
	private Integer tableMappingType;
	
	private Integer areaPatternType;
	
	private List<TableDesignDetails> listTableDesignDetail;
	
	private List<TableDesignDetails> listAllColumns;
	
	private MessageDesign messageDesign;
	
	private Long createdBy;
	
	private Timestamp createdDate;
	
	private Long updatedBy;
	
	private Timestamp updatedDate;
	
	private Timestamp sysDatetime;	
	
	private String tblDesignCode;
	
	public MessageDesign getMessageDesign() {
		return messageDesign;
	}
	
	public void setMessageDesign(MessageDesign messageDesign) {
		this.messageDesign = messageDesign;
	}
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
	 * @return the moduleTableMappingId
	 */
	public Long getModuleTableMappingId() {
		return moduleTableMappingId;
	}
	/**
	 * @param moduleTableMappingId the moduleTableMappingId to set
	 */
	public void setModuleTableMappingId(Long moduleTableMappingId) {
		this.moduleTableMappingId = moduleTableMappingId;
	}
	/**
	 * @return the tblDesignId
	 */
	public Long getTblDesignId() {
		return tblDesignId;
	}
	/**
	 * @param tblDesignId the tblDesignId to set
	 */
	public void setTblDesignId(Long tblDesignId) {
		this.tblDesignId = tblDesignId;
	}
	/**
	 * @return the tblDesignName
	 */
	public String getTblDesignName() {
		return tblDesignName;
	}
	/**
	 * @param tblDesignName the tblDesignName to set
	 */
	public void setTblDesignName(String tblDesignName) {
		this.tblDesignName = tblDesignName;
	}
	/**
	 * @return the tableMappingType
	 */
	public Integer getTableMappingType() {
		return tableMappingType;
	}
	/**
	 * @param tableMappingType the tableMappingType to set
	 */
	public void setTableMappingType(Integer tableMappingType) {
		this.tableMappingType = tableMappingType;
	}
	public Integer getAreaPatternType() {
		return areaPatternType;
	}
	public void setAreaPatternType(Integer areaPatternType) {
		this.areaPatternType = areaPatternType;
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
	public Timestamp getSysDatetime() {
		return sysDatetime;
	}
	public void setSysDatetime(Timestamp sysDatetime) {
		this.sysDatetime = sysDatetime;
	}
	/*public List<DomainDatatypeItem> getListDatatypeItems() {
		return listDatatypeItems;
	}
	public void setListDatatypeItems(List<DomainDatatypeItem> listDatatypeItems) {
		this.listDatatypeItems = listDatatypeItems;
	}*/

	public List<TableDesignDetails> getListTableDesignDetail() {
		return listTableDesignDetail;
	}

	public void setListTableDesignDetail(List<TableDesignDetails> listOfTableDesignDetail) {
		this.listTableDesignDetail = listOfTableDesignDetail;
	}

	public String getTblDesignCode() {
	    return tblDesignCode;
    }

	public void setTblDesignCode(String tblDesignCode) {
	    this.tblDesignCode = tblDesignCode;
    }

	public List<TableDesignDetails> getListAllColumns() {
	    return listAllColumns;
    }

	public void setListAllColumns(List<TableDesignDetails> listAllColumns) {
	    this.listAllColumns = listAllColumns;
    }
}
