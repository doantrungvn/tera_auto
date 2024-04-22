package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class ScreenArea implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long messageDesignId;
	
	private MessageDesign messageDesign;

	private String areaCode;

	private Long screenAreaId;

	private Integer areaSeqNo;

	private String areaLocalName;

	private String colWidthUnit;

	private Integer totalCol;
	
	private Integer totalElement;
	
	private Integer areaType;
	
	private String tblWidthUnit;
	
	private Integer tblHeaderRow;
	
	private Integer tblComponentRow;
	
	private Integer alignPositionType;
	
	private Integer areaPatternType;
	
	private Integer areaCustomType;
	
	/**
	 * DungNN1 - 20160121 add for improve performance 
	 */
	private Integer screenPatternType;
	
	private Long screenFormId;
	
	private Long screenId;
	
	private List<ScreenItem> listItems;
	
	private List<ScreenItemSequence> listItemSequences;
	
	private List<ScreenGroupItem> listGroupItems;
	
	private Long createdBy;
	
	private Timestamp createdDate;
	
	private Long updatedBy;
	
	private Timestamp updatedDate; 

	private String messageString;
	
	private Integer totalGroup;
	
    private List<ScreenGroupItem> groups;
    private List<ScreenItem> items;
    
    private Integer elementDipslayType;
    private Integer elementPositionType;
    
    private List<ScreenItem> listHiddenItems;
    
    private String formElementHidden;
    
    private List<ScreenAreaEvent> screenAreaEvents;
    
    private List<ScreenAreaSortMapping> screenAreaSorts;
    
    private String areaEventValue;
    
    private String startHtml;
    
    private String endHtml;
    
	private String panelStyle;
	
	private String headerStyle;
	
	private String rowStyle;
	
	private String inputStyle;
	
	private String alternateRowStyle;
	
	private ScreenForm screenForm;
	
	private ScreenArea screenArea;
	
	private Integer fixedRow;
	
	private Integer areaTypeAction;
	
	private String areaIcon;
	
	private Long screenAreaIdStore;
	
	private List<ScreenItemStatus> screenItemStatusLst;

	List<String> screenStatusConditions;
	
	private Long objectMappingId;
	
	private Integer objectMappingType;
	
	private String customSectionContent;
	
	private Integer displayPageable;
	
	private String screenAreaSortValue;

	private Boolean enableSort;
	
	private Long sqlColumnId;
	
	private String sqlColumnCode;
	
	private List<ScreenAreaSortMapping> screenAreaSortMappings;
	
	private String formCode;
	
	public String getPanelStyle() {
		return panelStyle;
	}

	public void setPanelStyle(String panelStyle) {
		this.panelStyle = panelStyle;
	}

	public String getHeaderStyle() {
		return headerStyle;
	}

	public void setHeaderStyle(String headerStyle) {
		this.headerStyle = headerStyle;
	}

	public String getRowStyle() {
		return rowStyle;
	}

	public void setRowStyle(String rowStyle) {
		this.rowStyle = rowStyle;
	}

	public String getInputStyle() {
		return inputStyle;
	}

	public void setInputStyle(String inputStyle) {
		this.inputStyle = inputStyle;
	}

	public String getAlternateRowStyle() {
		return alternateRowStyle;
	}

	public void setAlternateRowStyle(String alternateRowStyle) {
		this.alternateRowStyle = alternateRowStyle;
	}

	public String getStartHtml() {
		return startHtml;
	}

	public void setStartHtml(String startHtml) {
		this.startHtml = startHtml;
	}

	public String getEndHtml() {
		return endHtml;
	}

	public void setEndHtml(String endHtml) {
		this.endHtml = endHtml;
	}

	public String getMessageString() {
		return messageString;
	}

	public void setMessageString(String messageString) {
		this.messageString = messageString;
	}

	public List<ScreenGroupItem> getGroups() {
		return groups;
	}

	public void setGroups(List<ScreenGroupItem> groups) {
		this.groups = groups;
	}

	public List<ScreenItem> getItems() {
		return items;
	}

	public void setItems(List<ScreenItem> items) {
		this.items = items;
	}

	public List<ScreenGroupItem> getListGroupItems() {
		return listGroupItems;
	}

	public void setListGroupItems(List<ScreenGroupItem> listGroupItems) {
		this.listGroupItems = listGroupItems;
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

	public List<ScreenItem> getListItems() {
		return listItems;
	}

	public void setListItems(List<ScreenItem> listItems) {
		this.listItems = listItems;
	}

	public List<ScreenItemSequence> getListItemSequences() {
		return listItemSequences;
	}

	public void setListItemSequences(List<ScreenItemSequence> listItemSequences) {
		this.listItemSequences = listItemSequences;
	}

	public Long getScreenAreaId() {
		return screenAreaId;
	}

	public void setScreenAreaId(Long screenAreaId) {
		this.screenAreaId = screenAreaId;
	}

	public Integer getAreaSeqNo() {
		return areaSeqNo;
	}

	public void setAreaSeqNo(Integer areaSeqNo) {
		this.areaSeqNo = areaSeqNo;
	}

	public String getAreaLocalName() {
		return areaLocalName;
	}

	public void setAreaLocalName(String areaLocalName) {
		this.areaLocalName = areaLocalName;
	}

	public String getColWidthUnit() {
		return colWidthUnit;
	}

	public void setColWidthUnit(String colWidthUnit) {
		this.colWidthUnit = colWidthUnit;
	}

	public Integer getTotalCol() {
		return totalCol;
	}

	public void setTotalCol(Integer totalCol) {
		this.totalCol = totalCol;
	}

	public Integer getTotalElement() {
		return totalElement;
	}

	public void setTotalElement(Integer totalElement) {
		this.totalElement = totalElement;
	}

	public Integer getAreaType() {
		return areaType;
	}

	public void setAreaType(Integer areaType) {
		this.areaType = areaType;
	}

	public Integer getTblHeaderRow() {
		return tblHeaderRow;
	}

	public void setTblHeaderRow(Integer tblHeaderRow) {
		this.tblHeaderRow = tblHeaderRow;
	}

	public Integer getAlignPositionType() {
		return alignPositionType;
	}

	public void setAlignPositionType(Integer alignPositionType) {
		this.alignPositionType = alignPositionType;
	}

	public Integer getAreaPatternType() {
		return areaPatternType;
	}

	public void setAreaPatternType(Integer areaPatternType) {
		this.areaPatternType = areaPatternType;
	}

	public Long getScreenFormId() {
		return screenFormId;
	}

	public void setScreenFormId(Long screenFormId) {
		this.screenFormId = screenFormId;
	}

	public Long getMessageDesignId() {
		return messageDesignId;
	}

	public void setMessageDesignId(Long messageDesignId) {
		this.messageDesignId = messageDesignId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getTblWidthUnit() {
		return tblWidthUnit;
	}

	public void setTblWidthUnit(String tblWidthUnit) {
		this.tblWidthUnit = tblWidthUnit;
	}

	public Integer getTotalGroup() {
		return totalGroup;
	}

	public void setTotalGroup(Integer totalGroup) {
		this.totalGroup = totalGroup;
	}

	public Long getScreenId() {
		return screenId;
	}

	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}

	public MessageDesign getMessageDesign() {
		return messageDesign;
	}

	public void setMessageDesign(MessageDesign messageDesign) {
		this.messageDesign = messageDesign;
	}

	public Integer getElementDipslayType() {
		return elementDipslayType;
	}

	public void setElementDipslayType(Integer elementDipslayType) {
		this.elementDipslayType = elementDipslayType;
	}

	public Integer getElementPositionType() {
		return elementPositionType;
	}

	public void setElementPositionType(Integer elementPositionType) {
		this.elementPositionType = elementPositionType;
	}

	public List<ScreenItem> getListHiddenItems() {
		return listHiddenItems;
	}

	public void setListHiddenItems(List<ScreenItem> listHiddenItems) {
		this.listHiddenItems = listHiddenItems;
	}

	public String getFormElementHidden() {
		return formElementHidden;
	}

	public void setFormElementHidden(String formElementHidden) {
		this.formElementHidden = formElementHidden;
	}

	public Integer getTblComponentRow() {
		return tblComponentRow;
	}

	public void setTblComponentRow(Integer tblComponentRow) {
		this.tblComponentRow = tblComponentRow;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof ScreenArea) {
			return ((ScreenArea) obj).getScreenAreaId().equals(this.getScreenAreaId());
		}
		return false;
	}

	public List<ScreenAreaEvent> getScreenAreaEvents() {
		return screenAreaEvents;
	}

	public void setScreenAreaEvents(List<ScreenAreaEvent> screenAreaEvents) {
		this.screenAreaEvents = screenAreaEvents;
	}

	public String getAreaEventValue() {
		return areaEventValue;
	}

	public void setAreaEventValue(String areaEventValue) {
		this.areaEventValue = areaEventValue;
	}

	public ScreenForm getScreenForm() {
		return screenForm;
	}

	public void setScreenForm(ScreenForm screenForm) {
		this.screenForm = screenForm;
	}

	public Integer getScreenPatternType() {
		return screenPatternType;
	}

	public void setScreenPatternType(Integer screenPatternType) {
		this.screenPatternType = screenPatternType;
	}

	public Integer getAreaCustomType() {
		return areaCustomType;
	}

	public void setAreaCustomType(Integer areaCustomType) {
		this.areaCustomType = areaCustomType;
	}

	public ScreenArea getScreenArea() {
		return screenArea;
	}

	public void setScreenArea(ScreenArea screenArea) {
		this.screenArea = screenArea;
	}

	public Integer getFixedRow() {
		return fixedRow;
	}

	public void setFixedRow(Integer fixedRow) {
		this.fixedRow = fixedRow;
	}

	public String getAreaIcon() {
		return areaIcon;
	}

	public void setAreaIcon(String areaIcon) {
		this.areaIcon = areaIcon;
	}

	public Integer getAreaTypeAction() {
		return areaTypeAction;
	}

	public void setAreaTypeAction(Integer areaTypeAction) {
		this.areaTypeAction = areaTypeAction;
	}

	public List<ScreenItemStatus> getScreenItemStatusLst() {
		return screenItemStatusLst;
	}

	public void setScreenItemStatusLst(List<ScreenItemStatus> screenItemStatusLst) {
		this.screenItemStatusLst = screenItemStatusLst;
	}

	public Long getScreenAreaIdStore() {
		return screenAreaIdStore;
	}

	public void setScreenAreaIdStore(Long screenAreaIdStore) {
		this.screenAreaIdStore = screenAreaIdStore;
	}


	public List<String> getScreenStatusConditions() {
		return screenStatusConditions;
	}

	public void setScreenStatusConditions(List<String> screenStatusConditions) {
		this.screenStatusConditions = screenStatusConditions;
	}

	public Long getObjectMappingId() {
		return objectMappingId;
	}

	public void setObjectMappingId(Long objectMappingId) {
		this.objectMappingId = objectMappingId;
	}

	public Integer getObjectMappingType() {
		return objectMappingType;
	}

	public void setObjectMappingType(Integer objectMappingType) {
		this.objectMappingType = objectMappingType;
	}

	public String getCustomSectionContent() {
		return customSectionContent;
	}

	public void setCustomSectionContent(String customSectionContent) {
		this.customSectionContent = customSectionContent;
	}

	public Integer getDisplayPageable() {
		return displayPageable;
	}

	public void setDisplayPageable(Integer displayPageable) {
		this.displayPageable = displayPageable;
	}
	
	public List<ScreenAreaSortMapping> getScreenAreaSorts() {
		return screenAreaSorts;
	}

	public void setScreenAreaSorts(List<ScreenAreaSortMapping> screenAreaSorts) {
		this.screenAreaSorts = screenAreaSorts;
	}
	
	public Boolean getEnableSort() {
		return enableSort;
	}

	public void setEnableSort(Boolean enableSort) {
		this.enableSort = enableSort;
	}

	public Long getSqlColumnId() {
		return sqlColumnId;
	}

	public void setSqlColumnId(Long sqlColumnId) {
		this.sqlColumnId = sqlColumnId;
	}

	public String getSqlColumnCode() {
		return sqlColumnCode;
	}

	public void setSqlColumnCode(String sqlColumnCode) {
		this.sqlColumnCode = sqlColumnCode;
	}

	public String getScreenAreaSortValue() {
		return screenAreaSortValue;
	}

	public void setScreenAreaSortValue(String screenAreaSortValue) {
		this.screenAreaSortValue = screenAreaSortValue;
	}

	public List<ScreenAreaSortMapping> getScreenAreaSortMappings() {
		return screenAreaSortMappings;
	}

	public void setScreenAreaSortMappings(
			List<ScreenAreaSortMapping> screenAreaSortMappings) {
		this.screenAreaSortMappings = screenAreaSortMappings;
	}

	public String getFormCode() {
	    return formCode;
    }

	public void setFormCode(String formCode) {
	    this.formCode = formCode;
    }

	
}
