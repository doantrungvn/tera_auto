package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.GenerateUniqueKey;

public class MessageDesign implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	public static final String STR_DOT = ".";
	private static final String PREFIX_DEFAULT = "msg.";

	private Long messageDesignId;
	
	private String messageString;
	
	private String messageCode;

	private Integer messageLevel;
	
	private String messageLevelText;
	
	private String messageType;
	
	private String messageTypeName;
	
	private String languageCode;
	
	private String languageName;
	
	private String countryCode;

	private String remark;
	
	private Long projectId;
	
	private Long moduleId;
	
	private String moduleCode;
	
	private String moduleName;
	
	private Integer moduleStatus;
	
	private Long screenId;
	
	private String screenName;
	
	private Long businessLogicId;
	
	private String businessLogicName;
	
	private Long fromLanguageId;

	private String fromLanguageCode;
	
	private Long fromMessageDesignId;
	
	private String fromMessageString;
	
	private Long toLanguageId;
	
	private String toLanguageCode;
	
	private Long toMessageDesignId;
	
	private String toMessageString;
	
	private List<HashMap<Long, String>> screenDesigns;
	
	private MessageDesign[] messageDesigns;
	
	private Long createdBy;
	
	private Timestamp createdDate;
	
	private Long updatedBy;
	
	private Timestamp updatedDate;
	
	private Timestamp sysDatetime;
	
	private Timestamp fromUpdatedDate;

	private Timestamp toUpdatedDate;
	
	private Long languageId;
	
	private String mode;
	
	private Integer generatedStatus;
	
	private Integer fromGeneratedStatus;
	
	private Integer toGeneratedStatus;

	private Boolean fromSelected = false;
	
	private Boolean toSelected = false;
	
	private Boolean selected = false;
	
	private List<BusinessDesign> listOfBusinessDesign;

	private MenuDesign menuDesign;

	private String classFlg;
	
	private Long accountId;
	
	public Boolean getFromSelected() {
		return fromSelected;
	}

	public void setFromSelected(Boolean fromSelected) {
		this.fromSelected = fromSelected;
	}

	public Boolean getToSelected() {
		return toSelected;
	}

	public void setToSelected(Boolean toSelected) {
		this.toSelected = toSelected;
	}

	public Integer getGeneratedStatus() {
		return generatedStatus;
	}

	public void setGeneratedStatus(Integer generatedStatus) {
		this.generatedStatus = generatedStatus;
	}

	public Integer getFromGeneratedStatus() {
		return fromGeneratedStatus;
	}

	public void setFromGeneratedStatus(Integer fromGeneratedStatus) {
		this.fromGeneratedStatus = fromGeneratedStatus;
	}

	public Integer getToGeneratedStatus() {
		return toGeneratedStatus;
	}

	public void setToGeneratedStatus(Integer toGeneratedStatus) {
		this.toGeneratedStatus = toGeneratedStatus;
	}

	public MessageDesign() {
		
	}
	
	public MessageDesign(Long messageDesignId, String messageString,
			String messageCode, Integer messageLevel,String messageLevelText , String messageType,
			String languageCode, String languageName, String countryCode,
			String remark, Long projectId, Long moduleId, String moduleCode,
			String moduleName, Long screenId, String screenName,
			Long businessLogicId, String businessLogicName,
			Long fromLanguageId, Long fromMessageDesignId,
			String fromMessageString, Long toLanguageId,
			Long toMessageDesignId, String toMessageString,
			List<HashMap<Long, String>> screenDesigns, Long createdBy,
			Timestamp createdDate, Long updatedBy, Timestamp updatedDate,
			Timestamp sysDatetime, Timestamp fromUpdatedDate,
			Timestamp toUpdatedDate) {
		super();
		this.messageDesignId = messageDesignId;
		this.messageString = messageString;
		this.messageCode = messageCode;
		this.messageLevel = messageLevel;
		this.messageType = messageType;
		this.languageCode = languageCode;
		this.languageName = languageName;
		this.countryCode = countryCode;
		this.remark = remark;
		this.projectId = projectId;
		this.moduleId = moduleId;
		this.moduleCode = moduleCode;
		this.moduleName = moduleName;
		this.screenId = screenId;
		this.screenName = screenName;
		this.businessLogicId = businessLogicId;
		this.businessLogicName = businessLogicName;
		this.fromLanguageId = fromLanguageId;
		this.fromMessageDesignId = fromMessageDesignId;
		this.fromMessageString = fromMessageString;
		this.toLanguageId = toLanguageId;
		this.toMessageDesignId = toMessageDesignId;
		this.toMessageString = toMessageString;
		this.screenDesigns = screenDesigns;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
		this.sysDatetime = sysDatetime;
		this.fromUpdatedDate = fromUpdatedDate;
		this.toUpdatedDate = toUpdatedDate;
		this.messageLevelText = messageLevelText;
	}
	
	public String getFromLanguageCode() {
		return fromLanguageCode;
	}

	public void setFromLanguageCode(String fromLanguageCode) {
		this.fromLanguageCode = fromLanguageCode;
	}

	public String getToLanguageCode() {
		return toLanguageCode;
	}

	public void setToLanguageCode(String toLanguageCode) {
		this.toLanguageCode = toLanguageCode;
	}

	/**
	 * @return the messageDesignId
	 */
	public Long getMessageDesignId() {
		return messageDesignId;
	}

	/**
	 * @param messageDesignId the messageDesignId to set
	 */
	public void setMessageDesignId(Long messageDesignId) {
		this.messageDesignId = messageDesignId;
	}

	/**
	 * @return the messageString
	 */
	public String getMessageString() {
		return StringUtils.defaultString(messageString, StringUtils.EMPTY);
	}

	/**
	 * @param messageString the messageString to set
	 */
	public void setMessageString(String messageString) {
		this.messageString = messageString;
	}

	/**
	 * @return the messageCode
	 */
	public String getMessageCode() {
		return messageCode;
	}

	/**
	 * @param messageCode the messageCode to set
	 */
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	/**
	 * @return the messageLevel
	 */
	public Integer getMessageLevel() {
		return messageLevel;
	}

	/**
	 * @param messageLevel the messageLevel to set
	 */
	public void setMessageLevel(Integer messageLevel) {
		this.messageLevel = messageLevel;
	}

	/**
	 * @return the messageType
	 */
	public String getMessageType() {
		return messageType;
	}

	/**
	 * @param messageType the messageType to set
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	/**
	 * @return the languageCode
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * @param languageCode the languageCode to set
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * @return the languageName
	 */
	public String getLanguageName() {
		return languageName;
	}

	/**
	 * @param languageName the languageName to set
	 */
	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
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

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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
	 * @return the screenId
	 */
	public Long getScreenId() {
		return screenId;
	}

	/**
	 * @param screenId the screenId to set
	 */
	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}

	/**
	 * @return the screenName
	 */
	public String getScreenName() {
		return screenName;
	}

	/**
	 * @param screenName the screenName to set
	 */
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	/**
	 * @return the businessLogicId
	 */
	public Long getBusinessLogicId() {
		return businessLogicId;
	}

	/**
	 * @param businessLogicId the businessLogicId to set
	 */
	public void setBusinessLogicId(Long businessLogicId) {
		this.businessLogicId = businessLogicId;
	}

	/**
	 * @return the businessLogicName
	 */
	public String getBusinessLogicName() {
		return businessLogicName;
	}

	/**
	 * @param businessLogicName the businessLogicName to set
	 */
	public void setBusinessLogicName(String businessLogicName) {
		this.businessLogicName = businessLogicName;
	}

	/**
	 * @return the fromMessageDesignId
	 */
	public Long getFromMessageDesignId() {
		return fromMessageDesignId;
	}

	/**
	 * @param fromMessageDesignId the fromMessageDesignId to set
	 */
	public void setFromMessageDesignId(Long fromMessageDesignId) {
		this.fromMessageDesignId = fromMessageDesignId;
	}

	/**
	 * @return the fromMessageString
	 */
	public String getFromMessageString() {
		return fromMessageString;
	}

	/**
	 * @param fromMessageString the fromMessageString to set
	 */
	public void setFromMessageString(String fromMessageString) {
		this.fromMessageString = fromMessageString;
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
	
	public Long getFromLanguageId() {
		return fromLanguageId;
	}

	public void setFromLanguageId(Long fromLanguageId) {
		this.fromLanguageId = fromLanguageId;
	}

	public Long getToLanguageId() {
		return toLanguageId;
	}

	public void setToLanguageId(Long toLanguageId) {
		this.toLanguageId = toLanguageId;
	}

	/**
	 * @return the toMessageDesignId
	 */
	public Long getToMessageDesignId() {
		return toMessageDesignId;
	}

	/**
	 * @param toMessageDesignId the toMessageDesignId to set
	 */
	public void setToMessageDesignId(Long toMessageDesignId) {
		this.toMessageDesignId = toMessageDesignId;
	}

	/**
	 * @return the toMessageString
	 */
	public String getToMessageString() {
		return toMessageString;
	}

	/**
	 * @param toMessageString the toMessageString to set
	 */
	public void setToMessageString(String toMessageString) {
		this.toMessageString = toMessageString;
	}

	/**
	 * @return the screenDesigns
	 */
	public List<HashMap<Long, String>> getScreenDesigns() {
		return screenDesigns;
	}

	/**
	 * @param screenDesigns the screenDesigns to set
	 */
	public void setScreenDesigns(List<HashMap<Long, String>> screenDesigns) {
		this.screenDesigns = screenDesigns;
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
		return updatedDate == null ? FunctionCommon.getCurrentTime() : updatedDate;
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
	 * @return the fromUpdatedDate
	 */
	public Timestamp getFromUpdatedDate() {
		return fromUpdatedDate == null ? FunctionCommon.getCurrentTime() : fromUpdatedDate;
	}

	/**
	 * @param fromUpdatedDate the fromUpdatedDate to set
	 */
	public void setFromUpdatedDate(Timestamp fromUpdatedDate) {
		this.fromUpdatedDate = fromUpdatedDate;
	}

	/**
	 * @return the toUpdatedDate
	 */
	public Timestamp getToUpdatedDate() {
		return toUpdatedDate == null ? FunctionCommon.getCurrentTime() : toUpdatedDate;
	}

	/**
	 * @param toUpdatedDate the toUpdatedDate to set
	 */
	public void setToUpdatedDate(Timestamp toUpdatedDate) {
		this.toUpdatedDate = toUpdatedDate;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public MessageDesign[] getMessageDesigns() {
		return messageDesigns;
	}

	public void setMessageDesigns(MessageDesign[] messageDesigns) {
		this.messageDesigns = messageDesigns;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}
	
	public Object clone()throws CloneNotSupportedException{  
		return super.clone();  
	}
	
	public String getAutoMessageCode() {
		if (StringUtils.isEmpty(this.getModuleCode())) {
			/*if message level is project*/
			if (FunctionCommon.equals(DbDomainConst.MessageLevel.PROJECT, this.getMessageLevel())) {
				return	GenerateUniqueKey.generateAutoCodeStatic(StringUtils.defaultString(this.getMessageType() + STR_DOT,PREFIX_DEFAULT), "sys");
			}
			return StringUtils.defaultString(this.getMessageType() + STR_DOT,PREFIX_DEFAULT) + GenerateUniqueKey.generateUsingSecureRandom();
		}
		return	GenerateUniqueKey.generateAutoCodeStatic(StringUtils.defaultString(this.getMessageType() + STR_DOT, PREFIX_DEFAULT), StringUtils.lowerCase(this.getModuleCode()));
	}

	public List<BusinessDesign> getListOfBusinessDesign() {
		return listOfBusinessDesign;
	}

	public void setListOfBusinessDesign(List<BusinessDesign> listOfBusinessDesign) {
		this.listOfBusinessDesign = listOfBusinessDesign;
	}

	public MenuDesign getMenuDesign() {
		return menuDesign;
	}

	public void setMenuDesign(MenuDesign menuDesign) {
		this.menuDesign = menuDesign;
	}

	public boolean isUsed() {
		if (this.menuDesign != null || FunctionCommon.isNotEmpty(listOfBusinessDesign) || FunctionCommon.isNotEmpty(screenDesigns)) {
			return true;
		}

		return false;
	}

	public String getMessageTypeName() {
		return messageTypeName;
	}

	public void setMessageTypeName(String messageTypeName) {
		this.messageTypeName = messageTypeName;
	}

	public String getClassFlg() {
		return StringUtils.isBlank(classFlg) ? DbDomainConst.QPCommomFlg.NO : classFlg;
	}

	public void setClassFlg(String classFlg) {
		this.classFlg = classFlg;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getMessageLevelText() {
		return messageLevelText;
	}

	public void setMessageLevelText(String messageLevelText) {
		this.messageLevelText = messageLevelText;
	}

	public Integer getModuleStatus() {
		return moduleStatus;
	}

	public void setModuleStatus(Integer moduleStatus) {
		this.moduleStatus = moduleStatus;
	}
}