package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class ScreenAction implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long screenActionId;
	private Long fromScreenId;
	private Long toScreenId;
	private MessageDesign messageDesign;
	private Integer actionType;
	private String connectionMsg;
	private Integer submitMethodType;
	private Long businessLogicId;
	private Long createdBy;
	private Timestamp createdDate;
	private Long updatedBy;
	private Timestamp updatedDate;
	private List<ScreenActionParam> listScreenParameters;
	private String toScreenText;
	private String toScreenCode;
	private String toBlogicText;
	private String toBlogicCode;
	private String toModuleCode;
	private Long toModuleId;
	private Integer fromScreenPatternType;
	private Integer toScreenPatternType;
	private Integer toScreenTemplateType;
	private Integer toBlogicReturnType;
	private Long toBlogicInScreenId;
	private Integer toBlogicScreenPatternType;
	private Integer toBlogicScreenTemplateType;
	private String navigateToBlogicCode;
	private String blogicAutocomplete;
	
	private Long toScreenByBlogicId;
	
	private Long navigateToBlogicId;
	
	private String navigateToBlogicText;
	
	private String toScreenCodeByNavigateBlogic;
	
	private Long toScreenIdByNavigateBlogic;
	
	private String toModuleCodeByNavigateBlogic;
	
	private Integer toTemplateTypeByNavigateBlogic;
	
	private Long toModuleIdByNavigateBlogic;
	
	private String moduleName;
	
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

	public Long getScreenActionId() {
		return screenActionId;
	}

	public void setScreenActionId(Long screenActionId) {
		this.screenActionId = screenActionId;
	}

	public Long getFromScreenId() {
		return fromScreenId;
	}

	public void setFromScreenId(Long fromScreenId) {
		this.fromScreenId = fromScreenId;
	}

	public Long getToScreenId() {
		return toScreenId;
	}

	public void setToScreenId(Long toScreenId) {
		this.toScreenId = toScreenId;
	}

	public Integer getActionType() {
		return actionType;
	}

	public void setActionType(Integer actionType) {
		this.actionType = actionType;
	}

	public String getConnectionMsg() {
		return connectionMsg;
	}

	public void setConnectionMsg(String connectionMsg) {
		this.connectionMsg = connectionMsg;
	}

	public Integer getSubmitMethodType() {
		return submitMethodType;
	}

	public void setSubmitMethodType(Integer submitMethodType) {
		this.submitMethodType = submitMethodType;
	}

	public Long getBusinessLogicId() {
		return businessLogicId;
	}

	public void setBusinessLogicId(Long businessLogicId) {
		this.businessLogicId = businessLogicId;
	}

	/**
	 * @return the messageDesign
	 */
	public MessageDesign getMessageDesign() {
		return messageDesign;
	}

	/**
	 * @param messageDesign the messageDesign to set
	 */
	public void setMessageDesign(MessageDesign messageDesign) {
		this.messageDesign = messageDesign;
	}

	public List<ScreenActionParam> getListScreenParameters() {
		return listScreenParameters;
	}

	public void setListScreenParameters(List<ScreenActionParam> listScreenParameters) {
		this.listScreenParameters = listScreenParameters;
	}

	public String getToScreenText() {
		return toScreenText;
	}

	public void setToScreenText(String toScreenText) {
		this.toScreenText = toScreenText;
	}

	public String getToScreenCode() {
		return toScreenCode;
	}

	public void setToScreenCode(String toScreenCode) {
		this.toScreenCode = toScreenCode;
	}

	public String getToBlogicText() {
		return toBlogicText;
	}

	public void setToBlogicText(String toBlogicText) {
		this.toBlogicText = toBlogicText;
	}
	
	public String getToBlogicCode() {
		return toBlogicCode;
	}

	public void setToBlogicCode(String toBlogicCode) {
		this.toBlogicCode = toBlogicCode;
	}

	public Integer getFromScreenPatternType() {
		return fromScreenPatternType;
	}

	public void setFromScreenPatternType(Integer fromScreenPatternType) {
		this.fromScreenPatternType = fromScreenPatternType;
	}

	public Integer getToScreenPatternType() {
		return toScreenPatternType;
	}

	public void setToScreenPatternType(Integer toScreenPatternType) {
		this.toScreenPatternType = toScreenPatternType;
	}

	public Integer getToScreenTemplateType() {
		return toScreenTemplateType;
	}

	public void setToScreenTemplateType(Integer toScreenTemplateType) {
		this.toScreenTemplateType = toScreenTemplateType;
	}

	public Integer getToBlogicReturnType() {
		return toBlogicReturnType;
	}

	public void setToBlogicReturnType(Integer toBlogicReturnType) {
		this.toBlogicReturnType = toBlogicReturnType;
	}

	public Long getToBlogicInScreenId() {
		return toBlogicInScreenId;
	}

	public void setToBlogicInScreenId(Long toBlogicInScreenId) {
		this.toBlogicInScreenId = toBlogicInScreenId;
	}

	public Long getNavigateToBlogicId() {
		return navigateToBlogicId;
	}

	public void setNavigateToBlogicId(Long navigateToBlogicId) {
		this.navigateToBlogicId = navigateToBlogicId;
	}

	public String getNavigateToBlogicText() {
		return navigateToBlogicText;
	}

	public void setNavigateToBlogicText(String navigateToBlogicText) {
		this.navigateToBlogicText = navigateToBlogicText;
	}

	public Integer getToBlogicScreenPatternType() {
		return toBlogicScreenPatternType;
	}

	public void setToBlogicScreenPatternType(Integer toBlogicScreenPatternType) {
		this.toBlogicScreenPatternType = toBlogicScreenPatternType;
	}

	public Integer getToBlogicScreenTemplateType() {
		return toBlogicScreenTemplateType;
	}

	public void setToBlogicScreenTemplateType(Integer toBlogicScreenTemplateType) {
		this.toBlogicScreenTemplateType = toBlogicScreenTemplateType;
	}

	public String getNavigateToBlogicCode() {
		return navigateToBlogicCode;
	}

	public void setNavigateToBlogicCode(String navigateToBlogicCode) {
		this.navigateToBlogicCode = navigateToBlogicCode;
	}

	public Long getToScreenByBlogicId() {
		return toScreenByBlogicId;
	}

	public void setToScreenByBlogicId(Long toScreenByBlogicId) {
		this.toScreenByBlogicId = toScreenByBlogicId;
	}

	public String getToScreenCodeByNavigateBlogic() {
		return toScreenCodeByNavigateBlogic;
	}

	public void setToScreenCodeByNavigateBlogic(String toScreenCodeByNavigateBlogic) {
		this.toScreenCodeByNavigateBlogic = toScreenCodeByNavigateBlogic;
	}

	public String getToModuleCodeByNavigateBlogic() {
		return toModuleCodeByNavigateBlogic;
	}

	public void setToModuleCodeByNavigateBlogic(String toModuleCodeByNavigateBlogic) {
		this.toModuleCodeByNavigateBlogic = toModuleCodeByNavigateBlogic;
	}

	public Integer getToTemplateTypeByNavigateBlogic() {
		return toTemplateTypeByNavigateBlogic;
	}

	public void setToTemplateTypeByNavigateBlogic(
			Integer toTemplateTypeByNavigateBlogic) {
		this.toTemplateTypeByNavigateBlogic = toTemplateTypeByNavigateBlogic;
	}

	public Long getToModuleIdByNavigateBlogic() {
		return toModuleIdByNavigateBlogic;
	}

	public void setToModuleIdByNavigateBlogic(Long toModuleIdByNavigateBlogic) {
		this.toModuleIdByNavigateBlogic = toModuleIdByNavigateBlogic;
	}

	public Long getToScreenIdByNavigateBlogic() {
		return toScreenIdByNavigateBlogic;
	}

	public void setToScreenIdByNavigateBlogic(Long toScreenIdByNavigateBlogic) {
		this.toScreenIdByNavigateBlogic = toScreenIdByNavigateBlogic;
	}

	public String getToModuleCode() {
		return toModuleCode;
	}

	public void setToModuleCode(String toModuleCode) {
		this.toModuleCode = toModuleCode;
	}

	public Long getToModuleId() {
		return toModuleId;
	}

	public void setToModuleId(Long toModuleId) {
		this.toModuleId = toModuleId;
	}

	public String getBlogicAutocomplete() {
		return blogicAutocomplete;
	}

	public void setBlogicAutocomplete(String blogicAutocomplete) {
		this.blogicAutocomplete = blogicAutocomplete;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	
}
