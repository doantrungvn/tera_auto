package org.terasoluna.qp.app.codelist;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.terasoluna.qp.domain.model.Autocomplete;

public class CodeListForm implements Serializable {

	public static interface CodeListCreate {
	};

	public static interface CodeListEdit {
	};

	private static final long serialVersionUID = 1L;

	private long codeListId;
	
	private String mode;
	
	private String codeListName;

	private String codeListCode;

	private String remark;

	private Integer isOptionValude;
	
	private Long moduleId;
	
	/*@NotEmpty(message = ModuleMessageConst.SC_MODULE_0007)*/
	private String moduleIdAutocomplete;
	
	private Long projectId;
	
	/*@NotEmpty(message = ProjectMessageConst.SC_PROJECT_0005)*/
	private String projectIdAutocomplete;

	private Timestamp updatedDate;
	
	private Integer multivalueFlg;
	
	private Integer openOwner;

	/*	@Valid*/
	private CodeListDetailForm[] codeListDetails;
	
	private List<Autocomplete> listTableDesignItems;
	
	private List<Autocomplete> listScreenItem;
	
	private List<Autocomplete> listDomainDesign;

	public long getCodeListId() {
		return codeListId;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public void setCodeListId(long codeListId) {
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

	public Integer getIsOptionValude() {
		return isOptionValude;
	}

	public void setIsOptionValude(Integer isOptionValude) {
		this.isOptionValude = isOptionValude;
	}

	public String getModuleIdAutocomplete() {
		return moduleIdAutocomplete;
	}

	public void setModuleIdAutocomplete(String moduleIdAutocomplete) {
		this.moduleIdAutocomplete = moduleIdAutocomplete;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public CodeListDetailForm[] getCodeListDetails() {
		return codeListDetails;
	}

	public void setCodeListDetails(CodeListDetailForm[] codeListDetails) {
		this.codeListDetails = codeListDetails;
	}


	public String getProjectIdAutocomplete() {
		return projectIdAutocomplete;
	}

	public void setProjectIdAutocomplete(String projectIdAutocomplete) {
		this.projectIdAutocomplete = projectIdAutocomplete;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Integer getMultivalueFlg() {
		return multivalueFlg;
	}

	public void setMultivalueFlg(Integer multivalueFlg) {
		this.multivalueFlg = multivalueFlg;
	}

	public Integer getOpenOwner() {
		return openOwner;
	}

	public void setOpenOwner(Integer openOwner) {
		this.openOwner = openOwner;
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
}
