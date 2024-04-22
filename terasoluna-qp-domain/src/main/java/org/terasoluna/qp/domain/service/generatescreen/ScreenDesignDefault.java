package org.terasoluna.qp.domain.service.generatescreen;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.constants.DbDomainConst.MessageLevel;
import org.terasoluna.qp.domain.model.AutocompleteDesign;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.LanguageDesign;
import org.terasoluna.qp.domain.model.MessageDesign;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.ModuleTableMapping;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.ScreenAction;
import org.terasoluna.qp.domain.model.ScreenArea;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.model.ScreenForm;
import org.terasoluna.qp.domain.model.ScreenItem;
import org.terasoluna.qp.domain.model.ScreenTransition;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignRegister;

public class ScreenDesignDefault {
	private Long moduleId;
	private Integer[] screenPatternTypes;
	private ModuleTableMapping[] moduleTableMappings;
	private Integer confirmationType;
	private Integer completionType;
	private String moduleName;
	private String moduleCode;
	private String screenCode;
	private String screenName;
	private Boolean isCopy;
	private Long tempScreenId;
	private Integer templateType;
	private Long projectId;
	private String remark;
	private Integer flagMessageType;
	private List<ScreenDesign> lstScreenDesign;
	private List<ScreenItem> lstScreenItem;
	private List<ScreenItem> lstScreenItemSearchResults;
	private LanguageDesign languageDesign;
	private Map<String, BusinessDesign> mapBusinessLogicId = new HashMap<String, BusinessDesign>();
	private Map<String, ScreenDesign> mapScreenDesign = new HashMap<String, ScreenDesign>();
	private Map<Long, List<InputBean>> mapInputBeanOfBLogic = new HashMap<Long, List<InputBean>>();
	private Map<Long, List<OutputBean>> mapOutputBeanOfBLogic = new HashMap<Long, List<OutputBean>>();
	private Map<Long, SqlDesign> mapSqlDesign = new HashMap<Long, SqlDesign>();
	private Map<Long, AutocompleteDesign> mapAutocomplete = new HashMap<Long, AutocompleteDesign>();
	private ScreenAction screenActionDelete;
	private ScreenItem screenItemButtonDelete;
	private List<ScreenItem> lstScreenItemActionDelete;
	private ScreenForm formActionDelete;
	private ScreenArea areaActionDelete;
//	private Map<String, List<ScreenItem>> mapScreenItemHidden;
	private ScreenAction screenActionofActionDelete;
	private ScreenAction screenActionNavigator;
	private Long functionDesignId;
	private Map<Long, ScreenForm> mapFormOfScreen =  new HashMap<Long, ScreenForm>();
	private Integer xCoordinate;
	private Integer yCoordinate;
	private List<ScreenTransition> lstScreenTransitions = new ArrayList<ScreenTransition>();
	private Map<ScreenArea, List<ScreenItem>> mapAreaResultAndItemHeader = new HashMap<ScreenArea, List<ScreenItem>>();
	private ScreenForm formOfSearchScreen;
	private SqlDesign sqlSearchCount;
	private SqlDesign sqlSearchRecord;
	private CommonModel commonModel;
	private List<MessageDesign> listMessRegisted = new ArrayList<MessageDesign>();

	private String suffix;

	private Long createdBy;

	private Timestamp createdDate;
    
    private Project project;
    
    private Boolean isGenDefault;
	
	public Integer getFlagMessageType() {
		return flagMessageType;
	}

	public void setFlagMessageType(Integer flagMessageType) {
		this.flagMessageType = flagMessageType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Integer getTemplateType() {
		return templateType;
	}

	public void setTemplateType(Integer templateType) {
		this.templateType = templateType;
	}

	public Long getTempScreenId() {
		return tempScreenId;
	}

	public void setTempScreenId(Long tempScreenId) {
		this.tempScreenId = tempScreenId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Integer[] getScreenPatternTypes() {
		return screenPatternTypes;
	}

	public void setScreenPatternTypes(Integer[] screenPatternTypes) {
		this.screenPatternTypes = screenPatternTypes;
	}

	public ModuleTableMapping[] getModuleTableMappings() {
		return moduleTableMappings;
	}

	public void setModuleTableMappings(ModuleTableMapping[] moduleTableMappings) {
		this.moduleTableMappings = moduleTableMappings;
	}

	public Integer getConfirmationType() {
		return confirmationType;
	}

	public void setConfirmationType(Integer confirmationType) {
		this.confirmationType = confirmationType;
	}

	public Integer getCompletionType() {
		return completionType;
	}

	public void setCompletionType(Integer completionType) {
		this.completionType = completionType;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	
	public String getScreenCode() {
		return screenCode;
	}

	public void setScreenCode(String screenCode) {
		this.screenCode = screenCode;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public Boolean getIsCopy() {
		return isCopy;
	}

	public void setIsCopy(Boolean isCopy) {
		this.isCopy = isCopy;
	}

	public void toModule(Module module) {
		this.moduleId = module.getModuleId();
		this.screenPatternTypes = module.getScreenPatternTypes();
		this.moduleTableMappings = module.getModuleTableMappings();
		this.confirmationType = module.getConfirmationType();
		this.completionType = module.getCompletionType();
		this.moduleName = module.getModuleName();
		this.moduleCode = module.getModuleCode();
		this.projectId = module.getProjectId();
		this.flagMessageType = MessageLevel.MODULE;
		this.languageDesign = module.getLanguageDesign();
	}
	
	public void toScreenRegister(ScreenDesignRegister screenDesignRegister) {
		this.moduleId = screenDesignRegister.getModuleId();
		this.screenPatternTypes = new Integer[1]; 
		this.templateType = screenDesignRegister.getTemplateType();
		this.screenPatternTypes[0] = screenDesignRegister.getScreenPatternType();
		this.moduleTableMappings = screenDesignRegister.getModuleTableMappings();
		this.confirmationType = screenDesignRegister.getConfirmationType();
		this.completionType = screenDesignRegister.getCompletionType();
		this.screenCode = screenDesignRegister.getScreenCode();
		this.screenName = screenDesignRegister.getScreenName();
		this.isCopy = screenDesignRegister.getIsCopy();
		this.tempScreenId = screenDesignRegister.getCopyScreenId();
		this.projectId = screenDesignRegister.getProjectId();
		this.remark = screenDesignRegister.getRemark();
		this.flagMessageType = MessageLevel.SCREEN;
	}

	public List<ScreenDesign> getLstScreenDesign() {
	    return lstScreenDesign;
    }

	public void setLstScreenDesign(List<ScreenDesign> lstScreenDesign) {
	    this.lstScreenDesign = lstScreenDesign;
    }

	public List<ScreenItem> getLstScreenItem() {
	    return lstScreenItem;
    }

	public void setLstScreenItem(List<ScreenItem> lstScreenItem) {
	    this.lstScreenItem = lstScreenItem;
    }

	public LanguageDesign getLanguageDesign() {
	    return languageDesign;
    }

	public void setLanguageDesign(LanguageDesign languageDesign) {
	    this.languageDesign = languageDesign;
    }

	public Map<String, BusinessDesign> getMapBusinessLogicId() {
	    return mapBusinessLogicId;
    }

	public void setMapBusinessLogicId(Map<String, BusinessDesign> mapBusinessLogicId) {
	    this.mapBusinessLogicId = mapBusinessLogicId;
    }

	public List<ScreenItem> getLstScreenItemSearchResults() {
	    return lstScreenItemSearchResults;
    }

	public void setLstScreenItemSearchResults(List<ScreenItem> lstScreenItemSearchResults) {
	    this.lstScreenItemSearchResults = lstScreenItemSearchResults;
    }

	public Map<Long, List<InputBean>> getMapInputBeanOfBLogic() {
	    return mapInputBeanOfBLogic;
    }

	public void setMapInputBeanOfBLogic(Map<Long, List<InputBean>> mapInputBeanOfBLogic) {
	    this.mapInputBeanOfBLogic = mapInputBeanOfBLogic;
    }

	public String getSuffix() {
		return StringUtils.defaultString(suffix, "");
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
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

	public ScreenAction getScreenActionDelete() {
	    return screenActionDelete;
    }

	public void setScreenActionDelete(ScreenAction screenActionDelete) {
	    this.screenActionDelete = screenActionDelete;
    }

	public ScreenItem getScreenItemButtonDelete() {
	    return screenItemButtonDelete;
    }

	public void setScreenItemButtonDelete(ScreenItem screenItemButtonDelete) {
	    this.screenItemButtonDelete = screenItemButtonDelete;
    }

	public List<ScreenItem> getLstScreenItemActionDelete() {
		return lstScreenItemActionDelete;
	}

	public void setLstScreenItemActionDelete(List<ScreenItem> lstScreenItemActionDelete) {
		this.lstScreenItemActionDelete = lstScreenItemActionDelete;
	}

	public ScreenForm getFormActionDelete() {
		return formActionDelete;
	}

	public void setFormActionDelete(ScreenForm formActionDelete) {
		this.formActionDelete = formActionDelete;
	}

	public ScreenArea getAreaActionDelete() {
		return areaActionDelete;
	}

	public void setAreaActionDelete(ScreenArea areaActionDelete) {
		this.areaActionDelete = areaActionDelete;
	}

//	public Map<String, List<ScreenItem>> getMapScreenItemHidden() {
//		return mapScreenItemHidden;
//	}
//
//	public void setMapScreenItemHidden(Map<String, List<ScreenItem>> mapScreenItemHidden) {
//		this.mapScreenItemHidden = mapScreenItemHidden;
//	}

	public ScreenAction getScreenActionofActionDelete() {
		return screenActionofActionDelete;
	}

	public void setScreenActionofActionDelete(ScreenAction screenActionofActionDelete) {
		this.screenActionofActionDelete = screenActionofActionDelete;
	}

	public Long getFunctionDesignId() {
	    return functionDesignId;
    }

	public void setFunctionDesignId(Long functionDesignId) {
	    this.functionDesignId = functionDesignId;
    }

	public Map<Long, SqlDesign> getMapSqlDesign() {
		return mapSqlDesign;
	}

	public void setMapSqlDesign(Map<Long, SqlDesign> mapSqlDesign) {
		this.mapSqlDesign = mapSqlDesign;
	}

	public Map<Long, AutocompleteDesign> getMapAutocomplete() {
	    return mapAutocomplete;
    }

	public void setMapAutocomplete(Map<Long, AutocompleteDesign> mapAutocomplete) {
	    this.mapAutocomplete = mapAutocomplete;
    }

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Map<Long, List<OutputBean>> getMapOutputBeanOfBLogic() {
	    return mapOutputBeanOfBLogic;
    }

	public void setMapOutputBeanOfBLogic(Map<Long, List<OutputBean>> mapOutputBeanOfBLogic) {
	    this.mapOutputBeanOfBLogic = mapOutputBeanOfBLogic;
    }

	public ScreenAction getScreenActionNavigator() {
		return screenActionNavigator;
	}

	public void setScreenActionNavigator(ScreenAction screenActionNavigator) {
		this.screenActionNavigator = screenActionNavigator;
	}

	public Map<String, ScreenDesign> getMapScreenDesign() {
		return mapScreenDesign;
	}

	public void setMapScreenDesign(Map<String, ScreenDesign> mapScreenDesign) {
		this.mapScreenDesign = mapScreenDesign;
	}

	public Map<Long, ScreenForm> getMapFormOfScreen() {
		return mapFormOfScreen;
	}

	public void setMapFormOfScreen(Map<Long, ScreenForm> mapFormOfScreen) {
		this.mapFormOfScreen = mapFormOfScreen;
	}

	public Integer getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(Integer xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public Integer getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(Integer yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public List<ScreenTransition> getLstScreenTransitions() {
		return lstScreenTransitions;
	}

	public void setLstScreenTransitions(List<ScreenTransition> lstScreenTransitions) {
		this.lstScreenTransitions = lstScreenTransitions;
	}

	public Map<ScreenArea, List<ScreenItem>> getMapAreaResultAndItemHeader() {
		return mapAreaResultAndItemHeader;
	}

	public void setMapAreaResultAndItemHeader(Map<ScreenArea, List<ScreenItem>> mapAreaResultAndItemHeader) {
		this.mapAreaResultAndItemHeader = mapAreaResultAndItemHeader;
	}

	public ScreenForm getFormOfSearchScreen() {
		return formOfSearchScreen;
	}

	public void setFormOfSearchScreen(ScreenForm formOfSearchScreen) {
		this.formOfSearchScreen = formOfSearchScreen;
	}

	public SqlDesign getSqlSearchCount() {
		return sqlSearchCount;
	}

	public void setSqlSearchCount(SqlDesign sqlSearchCount) {
		this.sqlSearchCount = sqlSearchCount;
	}

	public SqlDesign getSqlSearchRecord() {
		return sqlSearchRecord;
	}

	public void setSqlSearchRecord(SqlDesign sqlSearchRecord) {
		this.sqlSearchRecord = sqlSearchRecord;
	}

	public CommonModel getCommonModel() {
		return commonModel;
	}

	public void setCommonModel(CommonModel commonModel) {
		this.commonModel = commonModel;
	}

	public Boolean getIsGenDefault() {
		return isGenDefault;
	}

	public void setIsGenDefault(Boolean isGenDefault) {
		this.isGenDefault = isGenDefault;
	}

	public List<MessageDesign> getListMessRegisted() {
		return listMessRegisted;
	}

	public void setListMessRegisted(List<MessageDesign> listMessRegisted) {
		this.listMessRegisted = listMessRegisted;
	}
}
