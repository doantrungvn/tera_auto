package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.terasoluna.gfw.common.codelist.SimpleMapCodeList;

public class GenerateDocumentItem implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Id of document */
	private String id;
	/** Name */
	private String documentItemName;
	/** Code */
	private String documentItemCode;
	/** Project or Module type */
	private String documentItemScopeItemType;
	/** RD or ED type */
	private String documentItemParenItemType;
	/** RD or ED  details type */
	private Integer documentItemType;
	/** Item name for collasep */
	private String documentItemCollaseName;	
	/** Template file name for generate */
	private String documentItemTemplateName;
	/** File name for generate */
	private String documentItemFileName;
	/** Item had is checked */
	private Boolean isChecked;
	/** Path of excel folder */
	private StringBuilder excelFolder;
	/** Data of document */
	private String jsonData;
	/** List file */
	private List<GenerateDocumentItemDetails> documentDetailLst;
	/** List Object data */
	private List<?> dataLst;
	/** Object data */
	private Object data;
	/** capture path */
	private String capturePath;
	/** language list */
	private List<LanguageDesign> languageDesignLst;
	
	/**
	 * List of domain design
	 */
	private List<DomainDesign> listDomainDesigns;
	
	/**
	 * List of base type
	 */
	private List<Basetype> listBasetype;
	
	/***
	 * Map of code List
	 * 
	 */
	private Map<String, SimpleMapCodeList> mapCodeList;
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * @return the documentItemType
	 */
	public Integer getDocumentItemType() {
		return documentItemType;
	}

	/**
	 * @param documentItemType the documentItemType to set
	 */
	public void setDocumentItemType(Integer documentItemType) {
		this.documentItemType = documentItemType;
	}

	/**
	 * @return the documentItemScopeItemType
	 */
	public String getDocumentItemScopeItemType() {
		return documentItemScopeItemType;
	}

	/**
	 * @param documentItemScopeItemType the documentItemScopeItemType to set
	 */
	public void setDocumentItemScopeItemType(String documentItemScopeItemType) {
		this.documentItemScopeItemType = documentItemScopeItemType;
	}

	/**
	 * @return the documentItemParenItemType
	 */
	public String getDocumentItemParenItemType() {
		return documentItemParenItemType;
	}

	/**
	 * @param documentItemParenItemType the documentItemParenItemType to set
	 */
	public void setDocumentItemParenItemType(String documentItemParenItemType) {
		this.documentItemParenItemType = documentItemParenItemType;
	}

	/**
	 * @return the isChecked
	 */
	public Boolean getIsChecked() {
		return isChecked;
	}

	/**
	 * @param isChecked the isChecked to set
	 */
	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}

	/**
	 * @return the excelFolder
	 */
	public StringBuilder getExcelFolder() {
		return excelFolder;
	}

	/**
	 * @param excelFolder the excelFolder to set
	 */
	public void setExcelFolder(StringBuilder excelFolder) {
		this.excelFolder = excelFolder;
	}

	/**
	 * @return the documentDetailLst
	 */
	public List<GenerateDocumentItemDetails> getDocumentDetailLst() {
		return documentDetailLst;
	}

	/**
	 * @param documentDetailLst the documentDetailLst to set
	 */
	public void setDocumentDetailLst(List<GenerateDocumentItemDetails> documentDetailLst) {
		this.documentDetailLst = documentDetailLst;
	}

	/**
	 * @return the documentItemTemplateName
	 */
	public String getDocumentItemTemplateName() {
		return documentItemTemplateName;
	}

	/**
	 * @param documentItemTemplateName the documentItemTemplateName to set
	 */
	public void setDocumentItemTemplateName(String documentItemTemplateName) {
		this.documentItemTemplateName = documentItemTemplateName;
	}

	/**
	 * @return the jsonData
	 */
	public String getJsonData() {
		return jsonData;
	}

	/**
	 * @param jsonData the jsonData to set
	 */
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	
	/**
	 * @return the documentItemFileName
	 */
	public String getDocumentItemFileName() {
		return documentItemFileName;
	}

	/**
	 * @param documentItemFileName the documentItemFileName to set
	 */
	public void setDocumentItemFileName(String documentItemFileName) {
		this.documentItemFileName = documentItemFileName;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the documentItemCollaseName
	 */
	public String getDocumentItemCollaseName() {
		return documentItemCollaseName;
	}

	/**
	 * @param documentItemCollaseName the documentItemCollaseName to set
	 */
	public void setDocumentItemCollaseName(String documentItemCollaseName) {
		this.documentItemCollaseName = documentItemCollaseName;
	}

	/**
	 * @return the dataLst
	 */
	public List<?> getDataLst() {
		return dataLst;
	}

	/**
	 * @param dataLst the dataLst to set
	 */
	public void setDataLst(List<?> dataLst) {
		this.dataLst = dataLst;
	}

	/**
	 * @return the documentItemName
	 */
	public String getDocumentItemName() {
		return documentItemName;
	}

	/**
	 * @param documentItemName the documentItemName to set
	 */
	public void setDocumentItemName(String documentItemName) {
		this.documentItemName = documentItemName;
	}

	/**
	 * @return the documentItemCode
	 */
	public String getDocumentItemCode() {
		return documentItemCode;
	}

	/**
	 * @param documentItemCode the documentItemCode to set
	 */
	public void setDocumentItemCode(String documentItemCode) {
		this.documentItemCode = documentItemCode;
	}

	public String getCapturePath() {
		return capturePath;
	}

	public void setCapturePath(String capturePath) {
		this.capturePath = capturePath;
	}

	public List<LanguageDesign> getLanguageDesignLst() {
		return languageDesignLst;
	}

	public void setLanguageDesignLst(List<LanguageDesign> languageDesignLst) {
		this.languageDesignLst = languageDesignLst;
	}
	
	public List<DomainDesign> getListDomainDesigns() {
		return listDomainDesigns;
	}

	public void setListDomainDesigns(List<DomainDesign> listDomainDesigns) {
		this.listDomainDesigns = listDomainDesigns;
	}

	public List<Basetype> getListBasetype() {
		return listBasetype;
	}

	public void setListBasetype(List<Basetype> listBasetype) {
		this.listBasetype = listBasetype;
	}

	public Map<String, SimpleMapCodeList> getMapCodeList() {
		return mapCodeList;
	}

	public void setMapCodeList(Map<String, SimpleMapCodeList> mapCodeList) {
		this.mapCodeList = mapCodeList;
	}
	
}
