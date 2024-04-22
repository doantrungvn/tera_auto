package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.terasoluna.gfw.common.message.ResultMessages;

public class CodeList implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codeListId;
	private String codeListName;
	private String codeListCode;
	private String remark;
	private Integer isOptionValude;
	private Integer multivalueFlg;

	private Integer moduleStatus;

	private Long createdBy;

	private Timestamp createdDate;

	private Long updatedBy;

	private Timestamp updatedDate;

	private Timestamp systemTime;

	private Long moduleId;
	private String moduleIdAutocomplete;
	
	private Long projectId;
	private String projectIdAutocomplete;

	private CodeListDetail[] codelistDetails;
	
	private List<Autocomplete> listTableDesignItems;
	
	private List<Autocomplete> listScreenItem;
	
	private List<Autocomplete> listDomainDesign;
	
	private ResultMessages resultMessages;
	
	public Long getCodeListId() {
		return codeListId;
	}

	public void setCodeListId(Long codeListId) {
		this.codeListId = codeListId;
	}

	public String getCodeListName() {
		return codeListName;
	}

	public void setCodeListName(String codeListName) {
		this.codeListName = codeListName;
	}

	public String getCodeListCode() {
		return codeListCode;
	}

	public void setCodeListCode(String codeListCode) {
		this.codeListCode = codeListCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * support value only
	 * @return
	 */
	public Integer getIsOptionValude() {
		return isOptionValude;
	}

	public void setIsOptionValude(Integer isOptionValude) {
		this.isOptionValude = isOptionValude;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}


	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}


	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}


	public CodeList() {
		super();
	}

	public CodeList(String codeListName, String codeListCode, Long moduleId,
			Long codelistId) {
		super();
		this.codeListId = codelistId;
		this.codeListName = codeListName;
		this.codeListCode = codeListCode;
		this.moduleId = moduleId;
	}

	public CodeListDetail[] getCodelistDetails() {
		return codelistDetails;
	}

	public void setCodelistDetails(CodeListDetail[] codelistDetails) {
		this.codelistDetails = codelistDetails;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}


	public String getModuleIdAutocomplete() {
		return moduleIdAutocomplete;
	}

	public void setModuleIdAutocomplete(String moduleIdAutocomplete) {
		this.moduleIdAutocomplete = moduleIdAutocomplete;
	}

	public String getProjectIdAutocomplete() {
		return projectIdAutocomplete;
	}

	public void setProjectIdAutocomplete(String projectIdAutocomplete) {
		this.projectIdAutocomplete = projectIdAutocomplete;
	}

	public Timestamp getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(Timestamp systemTime) {
		this.systemTime = systemTime;
	}

	public Integer getMultivalueFlg() {
		return multivalueFlg;
	}

	public void setMultivalueFlg(Integer multivalueFlg) {
		this.multivalueFlg = multivalueFlg;
	}

	public List<Autocomplete> getListTableDesignItems() {
		return listTableDesignItems;
	}

	public void setListTableDesignItems(List<Autocomplete> listTableDesignItems) {
		this.listTableDesignItems = listTableDesignItems;
	}

	public List<Autocomplete> getListScreenItem() {
		return listScreenItem;
	}

	public void setListScreenItem(List<Autocomplete> listScreenItem) {
		this.listScreenItem = listScreenItem;
	}

	public List<Autocomplete> getListDomainDesign() {
		return listDomainDesign;
	}

	public void setListDomainDesign(List<Autocomplete> listDomainDesign) {
		this.listDomainDesign = listDomainDesign;
	}

	public ResultMessages getResultMessages() {
		return resultMessages;
	}

	public void setResultMessages(ResultMessages resultMessages) {
		this.resultMessages = resultMessages;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Integer getModuleStatus() {
		return moduleStatus;
	}

	public void setModuleStatus(Integer moduleStatus) {
		this.moduleStatus = moduleStatus;
	}
}
