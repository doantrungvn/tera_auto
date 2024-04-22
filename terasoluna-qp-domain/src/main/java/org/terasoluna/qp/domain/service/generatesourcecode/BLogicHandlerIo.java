package org.terasoluna.qp.domain.service.generatesourcecode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.DecisionTable;
import org.terasoluna.qp.domain.model.FormulaDefinition;
import org.terasoluna.qp.domain.model.FunctionMaster;
import org.terasoluna.qp.domain.model.FunctionMethod;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.model.SequenceLogic;
import org.terasoluna.qp.domain.service.generatesourcecode.GenerateSourceCodeConst.ModuleScope;

public class BLogicHandlerIo {
	private SequenceLogic sequenceLogic;
	private Module module;
	private Project project;
	private Long languageId;

	/** Defualt for business logic */
	private int moduleScope = ModuleScope.BLOGIC;
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	private BusinessDesign businessDesign;
	private DecisionTable decisionTable;
	private List<FormulaDefinition> formulaDefinition;
	private List<FunctionMaster> functionMasters;
	private List<FunctionMethod> functionMethods;
	private Map<String,Object> ioMap;
	private ScreenDesign screenDesign;
	private Map<String, ObjectDefinition> mapObjDef;
	private Map<String, OutputBean> mapOutputBean;
	private String blogicInSyntax;
	private String blogicObSyntax;
	private String blogicOutputSyntax;
	private Map<String, String> mNameParameter;
	private String content;
	private Map<String, List<?>> mAllParentAndSeflByLevelOfInOutObj;
	
	// Method for Advance Node
	private String advanceComponentMethod;

	private Boolean isGenConfig = true;
	private String assignObjectForFormula;
	
	public Map<String, ObjectDefinition> getMapObjDef() {
		return mapObjDef;
	}

	public void setMapObjDef(Map<String, ObjectDefinition> mapObjDef) {
		this.mapObjDef = mapObjDef;
	}

	public Map<String, OutputBean> getMapOutputBean() {
		return mapOutputBean;
	}

	public void setMapOutputBean(Map<String, OutputBean> mapOutputBean) {
		this.mapOutputBean = mapOutputBean;
	}

	public String getBlogicObSyntax() {
		return blogicObSyntax;
	}

	public void setBlogicObSyntax(String blogicObSyntax) {
		this.blogicObSyntax = blogicObSyntax;
	}

	public String getBlogicOutputSyntax() {
		return blogicOutputSyntax;
	}

	public void setBlogicOutputSyntax(String blogicOutputSyntax) {
		this.blogicOutputSyntax = blogicOutputSyntax;
	}

	public Map<String, String> getmNameParameter() {
		return mNameParameter;
	}

	public void setmNameParameter(Map<String, String> mNameParameter) {
		this.mNameParameter = mNameParameter;
	}

	public BLogicHandlerIo(){
		ioMap = new HashMap<String, Object>();
	}

	public SequenceLogic getSequenceLogic() {
		return sequenceLogic;
	}
	public Module getModule() {
		return module;
	}
	public BusinessDesign getBusinessDesign() {
		return businessDesign;
	}
	public void setBusinessDesign(BusinessDesign businessDesign) {
		this.businessDesign = businessDesign;
	}
	public void setSequenceLogic(SequenceLogic sequenceLogic) {
		this.sequenceLogic = sequenceLogic;
	}
	public void setModule(Module module) {
		this.module = module;
	}
	public Map<String, Object> getIoMap() {
		return ioMap;
	}
	public void setIoMap(Map<String, Object> ioMap) {
		this.ioMap = ioMap;
	}

	/**
	 * @return the formulaDefinition
	 */
	public List<FormulaDefinition> getFormulaDefinition() {
		return formulaDefinition;
	}

	/**
	 * @param formulaDefinition the formulaDefinition to set
	 */
	public void setFormulaDefinition(List<FormulaDefinition> formulaDefinition) {
		this.formulaDefinition = formulaDefinition;
	}

	/**
	 * @return the functionMasters
	 */
	public List<FunctionMaster> getFunctionMasters() {
		return functionMasters;
	}

	/**
	 * @param functionMasters the functionMasters to set
	 */
	public void setFunctionMasters(List<FunctionMaster> functionMasters) {
		this.functionMasters = functionMasters;
	}

	/**
	 * @return the functionMethods
	 */
	public List<FunctionMethod> getFunctionMethods() {
		return functionMethods;
	}

	/**
	 * @param functionMethods the functionMethods to set
	 */
	public void setFunctionMethods(List<FunctionMethod> functionMethods) {
		this.functionMethods = functionMethods;
	}

	public ScreenDesign getScreenDesign() {
		return screenDesign;
	}

	public void setScreenDesign(ScreenDesign screenDesign) {
		this.screenDesign = screenDesign;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the mAllParentAndSeflByLevelOfInOutObj
	 */
	public Map<String, List<?>> getmAllParentAndSeflByLevelOfInOutObj() {
		return mAllParentAndSeflByLevelOfInOutObj;
	}

	/**
	 * @param mAllParentAndSeflByLevelOfInOutObj the mAllParentAndSeflByLevelOfInOutObj to set
	 */
	public void setmAllParentAndSeflByLevelOfInOutObj(
			Map<String, List<?>> mAllParentAndSeflByLevelOfInOutObj) {
		this.mAllParentAndSeflByLevelOfInOutObj = mAllParentAndSeflByLevelOfInOutObj;
	}

	public String getAdvanceComponentMethod() {
		return advanceComponentMethod;
	}

	public void setAdvanceComponentMethod(String advanceComponentMethod) {
		this.advanceComponentMethod = advanceComponentMethod;
	}

	/**
	 * @return the blogicInSyntax
	 */
	public String getBlogicInSyntax() {
		return blogicInSyntax;
	}

	/**
	 * @param blogicInSyntax the blogicInSyntax to set
	 */
	public void setBlogicInSyntax(String blogicInSyntax) {
		this.blogicInSyntax = blogicInSyntax;
	}

	public Boolean getIsGenConfig() {
		return isGenConfig;
	}

	public void setIsGenConfig(Boolean isGenConfig) {
		this.isGenConfig = isGenConfig;
	}

	public String getAssignObjectForFormula() {
		return assignObjectForFormula;
	}

	public void setAssignObjectForFormula(String assignObjectForFormula) {
		this.assignObjectForFormula = assignObjectForFormula;
	}

	/**
	 * @return the moduleScope
	 */
	public int getModuleScope() {
		return moduleScope;
	}

	/**
	 * @param moduleScope the moduleScope to set
	 */
	public void setModuleScope(int moduleScope) {
		this.moduleScope = moduleScope;
	}

	/**
	 * @return the decisionTable
	 */
	public DecisionTable getDecisionTable() {
		return decisionTable;
	}

	/**
	 * @param decisionTable the decisionTable to set
	 */
	public void setDecisionTable(DecisionTable decisionTable) {
		this.decisionTable = decisionTable;
	}

}
