package org.terasoluna.qp.domain.service.generatesourcecode;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.DbDomainConst.SqlPattern;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.ExecutionComponent;
import org.terasoluna.qp.domain.model.ExecutionInputValue;
import org.terasoluna.qp.domain.model.ExecutionOutputValue;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.ModuleTableMapping;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.SqlDesignInput;
import org.terasoluna.qp.domain.model.SqlDesignOutput;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.repository.moduletablemapping.ModuleTableMappingRepository;
import org.terasoluna.qp.domain.repository.screenarea.ScreenAreaRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignInputRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignOutputRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignDetailRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignConst;

@Component("executionGenerationHandler")
public class ExecutionGenerationHandler extends SequenceLogicGenerationHandler {
	@Inject
	DetailServiceImpHandler detailServiceImpHandler;
	@Inject
	SqlDesignInputRepository sqlDesignInputRepository;
	@Inject
	SqlDesignOutputRepository sqlDesignOutputRepository;
	@Inject
	TableDesignDetailRepository tableDesignDetailRepository;
	@Inject
	ModuleTableMappingRepository moduleTableMappingRepository;
	@Inject
	ScreenAreaRepository screenAreaRepository;

	private static final String NL = "\n\t\t";
	private static final String NL_TAB = "\n\t\t\t";
	private static final String NL_DOUBLE_TAB = "\n\t\t\t\t";
	private static final String INIT_SQL_INPUT = "{0}InputBean {1} = new {2}InputBean() ;";
	private static final String INIT_SQL_OUTPUT = "{0}OutputBean {1} = {2}Repository.{3}({4}) ;";
	private static final String INIT_SQL_OUTPUT_NOT_SELECT = "Integer {0} = {1}Repository.{2}({3}) ;";
	private static final String INIT_SQL_LIST_OUTPUT = "List<{0}OutputBean> {1} = {2}Repository.{3}({4}) ;";
	private static final String INIT_SQL_LIST_SEARCH_RECORDS_OUTPUT = "List<{0}OutputBean> {1} = {2}Repository.{3}({4}, pageable) ;";
	private static final String INPUT_SETTER = "{0}({1}) ;";
	private static final String BLOGIC_SETTER = "{0}({1}) ;";
	private static final String SPACE = " ";
	private static final Integer RETURN_TYPE_LIST = 1;
	private static final boolean IS_GETTER = true;
	private static final boolean IS_SETTER = false;
	private static final String INIT_OBJECT = "{0} {1} = new {0}();";
	private static final Integer PAGEABLE = 1;
		
	private StringBuilder sbCheckNullInput = new StringBuilder(StringUtils.EMPTY);
	
	private ExecutionComponent currentComponent = new ExecutionComponent();

	public ExecutionComponent getCurrentComponent() {
		return currentComponent;
	}

	public void setCurrentComponent(ExecutionComponent currentComponent) {
		this.currentComponent = currentComponent;
	}

	public void handle(StringBuilder builder, BLogicHandlerIo param){
		Module module = param.getModule();
		BusinessDesign blogic = param.getBusinessDesign();
		Map<String, ObjectDefinition> mapObjDef = param.getMapObjDef();
		Map<String, OutputBean> mapOutputBean = param.getMapOutputBean();
		String blogicObSyntax = param.getBlogicObSyntax();
		String blogicOutputSyntax = param.getBlogicOutputSyntax();

		List<ObjectDefinition> lstObjectDefinition  = blogic.getLstObjectDefinition();
		List<OutputBean> lstOutputBean  = blogic.getLstOutputBean();
		StringBuilder strGenExecDetails = new StringBuilder(NL);

		if (currentComponent != null) {
			preGencode(builder, param);
			String sqlDesignCode = currentComponent.getSqlDesignCodeRefer();
			String inputSyntax = "input" + GenerateSourceCodeUtil.normalizedClassName(sqlDesignCode) + currentComponent.getExecutionComponentId();
			strGenExecDetails.append(MessageFormat.format(INIT_SQL_INPUT, GenerateSourceCodeUtil.normalizedClassName(sqlDesignCode), inputSyntax, GenerateSourceCodeUtil.normalizedClassName(sqlDesignCode))).append(NL);

			List<SqlDesignInput> lstSqlDesignInputs = new ArrayList<SqlDesignInput>(Arrays.asList(sqlDesignInputRepository.findAllBySqlDesignId(currentComponent.getSqlDesignId())));
			List<SqlDesignOutput> lstSqlDesignOutputs = new ArrayList<SqlDesignOutput>(Arrays.asList(sqlDesignOutputRepository.findAllBySqlDesignId(currentComponent.getSqlDesignId())));
			Map<Long, SqlDesignInput> mapSqlInput = new HashMap<Long, SqlDesignInput>();
			Map<Long, SqlDesignOutput> mapSqlOutput = new HashMap<Long, SqlDesignOutput>();
			for (SqlDesignInput sqlInput : lstSqlDesignInputs) {
				mapSqlInput.put(sqlInput.getSqlDesignInputId(), sqlInput);
			}
			for (SqlDesignOutput sqlOut : lstSqlDesignOutputs) {
				mapSqlOutput.put(sqlOut.getSqlDesignOutputId(), sqlOut);
			}
			
			// Modify by HungHX
			Map<String, List<?>> mAllParentAndSeflByLevelOfInOutObjCustom = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(1, lstSqlDesignInputs, null, lstSqlDesignOutputs);

			List<ExecutionInputValue> lstInputValue = currentComponent.getParameterInputBeans();
			// gen input
			if (FunctionCommon.isNotEmpty(lstInputValue)) {
				strGenExecDetails = buildPassParameterInput(param, strGenExecDetails);
				strGenExecDetails.append(NL).append(NL);
			}
			
			// gen output
			List<ExecutionOutputValue> lstOutputValue = currentComponent.getParameterOutputBeans();
			if (lstOutputValue != null && lstOutputValue.size() > 0) {
				
				String outputSyntax = "output" + GenerateSourceCodeUtil.normalizedClassName(sqlDesignCode) + currentComponent.getExecutionComponentId();
				String objSqlOutput = GenerateSourceCodeUtil.normalizedClassName(sqlDesignCode) + "OutputBean";
				// Fix gen get list from data souce
				String repositoryCode = currentComponent.getModuleId() != null? currentComponent.getModuleCode() : "common";
				
				// Build call get sql from repository
				switch (Integer.valueOf(currentComponent.getSqlPattern())) {
				
					case DbDomainConst.SqlPattern.SELECT:
						
						if (RETURN_TYPE_LIST.equals(currentComponent.getReturnType())) {
							// In the case using execution get value from sql common
							if (currentComponent.getModuleId() != null) {
								if (currentComponent.getPageable() != null && currentComponent.getPageable().intValue() == PAGEABLE.intValue()) {
									strGenExecDetails.append(MessageFormat.format(INIT_SQL_LIST_SEARCH_RECORDS_OUTPUT, GenerateSourceCodeUtil.normalizedClassName(sqlDesignCode), outputSyntax, GenerateSourceCodeUtil.normalizedMethodName(repositoryCode), GenerateSourceCodeUtil.normalizedMethodName(sqlDesignCode), inputSyntax)).append(NL);
								} else {
									strGenExecDetails.append(MessageFormat.format(INIT_SQL_LIST_OUTPUT, GenerateSourceCodeUtil.normalizedClassName(sqlDesignCode), outputSyntax, GenerateSourceCodeUtil.normalizedMethodName(repositoryCode), GenerateSourceCodeUtil.normalizedMethodName(sqlDesignCode), inputSyntax)).append(NL);
								}
							} else {
								strGenExecDetails.append(MessageFormat.format(INIT_SQL_LIST_OUTPUT, GenerateSourceCodeUtil.normalizedClassName(sqlDesignCode), outputSyntax, GenerateSourceCodeUtil.normalizedMethodName(repositoryCode), GenerateSourceCodeUtil.normalizedMethodName(sqlDesignCode), inputSyntax)).append(NL);
							}
						} else {
							strGenExecDetails.append(MessageFormat.format(INIT_SQL_OUTPUT, GenerateSourceCodeUtil.normalizedClassName(sqlDesignCode), outputSyntax, GenerateSourceCodeUtil.normalizedMethodName(repositoryCode), GenerateSourceCodeUtil.normalizedMethodName(sqlDesignCode), inputSyntax)).append(NL);
						}

						break;
	
					case DbDomainConst.SqlPattern.INSERT:
					case DbDomainConst.SqlPattern.UPDATE:
					case DbDomainConst.SqlPattern.DELETE:
						
						if (RETURN_TYPE_LIST.equals(currentComponent.getReturnType())) {
							strGenExecDetails.append(MessageFormat.format(INIT_SQL_LIST_OUTPUT, GenerateSourceCodeUtil.normalizedClassName(sqlDesignCode), outputSyntax, GenerateSourceCodeUtil.normalizedMethodName(repositoryCode), GenerateSourceCodeUtil.normalizedMethodName(sqlDesignCode), inputSyntax)).append(NL);
						} else {
							strGenExecDetails.append(MessageFormat.format(INIT_SQL_OUTPUT_NOT_SELECT , outputSyntax, GenerateSourceCodeUtil.normalizedMethodName(repositoryCode), GenerateSourceCodeUtil.normalizedMethodName(sqlDesignCode), inputSyntax)).append(NL);
						}
						
						break;
					}
				
				// Build output in the case sql is update - delete- insert
				if(!currentComponent.getSqlPattern().equals(String.valueOf(SqlPattern.SELECT)) && lstOutputValue.size() == 1) {
					
					if(currentComponent.getSqlPattern().equals(String.valueOf(SqlPattern.UPDATE)) && BusinessDesignConst.MODULE_TYPE_ONLINE.equals(param.getModule().getModuleType())) {
						strGenExecDetails.append(String.format("if(%s < 0) {", outputSyntax)).append(NL_TAB);
						strGenExecDetails.append("throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));").append("\n\t\t");
						strGenExecDetails.append("}");
					}
					
					// Assign for input bean
					String setterOfParameter = StringUtils.EMPTY;
					if (BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(lstOutputValue.get(0).getTargetScope())) {
						setterOfParameter = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), false, lstOutputValue.get(0).getTargetId(), BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID, blogicObSyntax, lstOutputValue.get(0).getLstTargetIndex());
					
					} else if (BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(lstOutputValue.get(0).getTargetScope())) { 
						setterOfParameter = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), false, lstOutputValue.get(0).getTargetId(), BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, blogicObSyntax, lstOutputValue.get(0).getLstTargetIndex());
					}
					
					if(StringUtils.isNotEmpty(setterOfParameter)) {
						if(Boolean.TRUE.equals(lstOutputValue.get(0).getArrayFlg()) && CollectionUtils.isNotEmpty(lstOutputValue.get(0).getLstTargetIndex())) {
							strGenExecDetails.append(String.format(setterOfParameter, outputSyntax)+(";")).append(NL_TAB);
						} else {
							strGenExecDetails.append(MessageFormat.format(BLOGIC_SETTER, setterOfParameter, outputSyntax)).append(NL_TAB);
						}
					}
				} else {
					// check output null
					strGenExecDetails.append("if (" + outputSyntax + SPACE + "!= null) {").append(NL_TAB);

					Map<String, List<ObjectDefinition>> hashmapChirldrenObjDef = new HashMap<String, List<ObjectDefinition>>();
					Set<ObjectDefinition> objDefsToSetValue = new HashSet<ObjectDefinition>();
					Map<String, List<OutputBean>> hashmapChirldrenOutputBean = new HashMap<String, List<OutputBean>>();
					Set<OutputBean> outputBeanToSetValue = new HashSet<OutputBean>();

					Map<String, ExecutionOutputValue> mAllAssignByTargetId = new HashMap<String, ExecutionOutputValue>();
					
					for (ExecutionOutputValue ou : lstOutputValue) {
						if (ou.getTargetScope() != null && BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(ou.getTargetScope())) {
							ObjectDefinition ob = mapObjDef.get(ou.getTargetId());
							if (ob.getParentObjectDefinitionId() != null) {
								objDefsToSetValue.add(mapObjDef.getOrDefault(ob.getParentObjectDefinitionId(), new ObjectDefinition()));
							} else {
								objDefsToSetValue.add(mapObjDef.getOrDefault(ob.getObjectDefinitionId(), new ObjectDefinition()));
							}
							mAllAssignByTargetId.put(BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID + ou.getTargetId(), ou);
						} else if (ou.getTargetScope() != null && BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(ou.getTargetScope())) {
							OutputBean ob = mapOutputBean.get(ou.getTargetId());
							if (ob.getParentOutputBeanId() != null) {
								outputBeanToSetValue.add(mapOutputBean.getOrDefault(ob.getParentOutputBeanId(), new OutputBean()));
							} else {
								outputBeanToSetValue.add(mapOutputBean.getOrDefault(ob.getOutputBeanId(), new OutputBean()));
							}
							mAllAssignByTargetId.put(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID + ou.getTargetId(), ou);
						}
					}

					for (ObjectDefinition obParent : objDefsToSetValue) {
						List<ObjectDefinition> lstChirlden = new ArrayList<ObjectDefinition>();
						for (ObjectDefinition child : lstObjectDefinition) {
							if (child.getParentObjectDefinitionId() != null && child.getParentObjectDefinitionId().equals(obParent.getObjectDefinitionId())) {
								lstChirlden.add(child);
							}
						}
						hashmapChirldrenObjDef.put(obParent.getObjectDefinitionId(), lstChirlden);
					}
					for (OutputBean outputParent : outputBeanToSetValue) {
						List<OutputBean> lstChirlden = new ArrayList<OutputBean>();
						for (OutputBean child : lstOutputBean) {
							if (child.getParentOutputBeanId() != null && child.getParentOutputBeanId().equals(outputParent.getOutputBeanId())) {
								lstChirlden.add(child);
							}
						}
						hashmapChirldrenOutputBean.put(outputParent.getOutputBeanId(), lstChirlden);
					}
					
					Set<ExecutionOutputValue> hashSetParentExecOutput = new HashSet<ExecutionOutputValue>();
					Map<Long, List<ExecutionOutputValue>> mapParentAndChildExecOutput = new HashMap<Long, List<ExecutionOutputValue>>();
					for (ExecutionOutputValue executionOutputValue : lstOutputValue) {
						SqlDesignOutput sqlOutput = mapSqlOutput.get(executionOutputValue.getSqlDesignOutputId());
						if (BusinessDesignConst.DataType.ENTITY.equals(sqlOutput.getDataType()) 
								|| BusinessDesignConst.DataType.OBJECT.equals(sqlOutput.getDataType())
								|| BusinessDesignConst.DataType.COMMON_OBJECT.equals(sqlOutput.getDataType())
								|| BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(sqlOutput.getDataType())) {
							List<ExecutionOutputValue> listExecOutChild = new ArrayList<ExecutionOutputValue>();
							for (ExecutionOutputValue obj : lstOutputValue) {
								SqlDesignOutput out = mapSqlOutput.get(obj.getSqlDesignOutputId());
								if (out.getSqlDesignOutputParentId() != null && out.getSqlDesignOutputParentId().equals(sqlOutput.getSqlDesignOutputId())) {
									listExecOutChild.add(obj);
								}
							}
							mapParentAndChildExecOutput.put(executionOutputValue.getExecutionOutputValueId(), listExecOutChild);
						}
					}
					
					// Preparing variable
					int isFirst = 0;
					
					String parentForGetter = StringUtils.EMPTY;
					String parentForSetter = StringUtils.EMPTY;
					String parentForAddToLst = StringUtils.EMPTY;
					
					if(currentComponent.getExecutionComponentId() == 31771){
						System.out.println();
					}
					
					// Main for processing
					for (int idx = 0; idx < lstOutputValue.size(); idx++) {
						
						ExecutionOutputValue executionOutputValue = lstOutputValue.get(idx);
						
						
						String syntaxExecOp = executionOutputValue.getSqlDesignOutputCode() + "Execute";
						String syntaxListExecOp = "lst" + GenerateSourceCodeUtil.normalizedClassName(executionOutputValue.getSqlDesignOutputCode()) + "Execute";
						
						// In the case using sql is multiple exist item last had not set
						if(idx == lstOutputValue.size() - 1 && RETURN_TYPE_LIST.equals(currentComponent.getReturnType())
								&& !BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(executionOutputValue.getTargetScope())
								&& !BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(executionOutputValue.getTargetScope())
								&& StringUtils.isNotEmpty(parentForAddToLst)
								&& StringUtils.isNotEmpty(parentForSetter)){

							strGenExecDetails.append(parentForAddToLst+".add("+parentForSetter+");").append(NL_DOUBLE_TAB);
							strGenExecDetails.append("}").append("\n\t\t");
						}

						// In the case of assign to object definition
						if(BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(executionOutputValue.getTargetScope())) {
							ObjectDefinition objectDefinition = mapObjDef.get(executionOutputValue.getTargetId());
							String codeExecOp = GenerateSourceCodeUtil.normalizedClassName(detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC, executionOutputValue.getTargetScope(), objectDefinition));
							
							if (BusinessDesignConst.DataType.ENTITY.equals(executionOutputValue.getDataType()) 
									|| BusinessDesignConst.DataType.OBJECT.equals(executionOutputValue.getDataType())
									|| BusinessDesignConst.DataType.COMMON_OBJECT.equals(executionOutputValue.getDataType())
									|| BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(executionOutputValue.getDataType())) {
								
								String pakage = this.getPackageName(param, objectDefinition);
								
								if (!hashSetParentExecOutput.contains(executionOutputValue)) {
									List<ExecutionOutputValue> lstExcOpChild = mapParentAndChildExecOutput.get(executionOutputValue.getExecutionOutputValueId());
									if (executionOutputValue.getArrayFlg()) {
										if (FunctionCommon.isNotEmpty(lstExcOpChild)) {
											// Append : List<SqlDesignOuput> lstSqlOutput = new ArrayList<SqlDesignOuput>();
											strGenExecDetails.append("List<" + pakage + codeExecOp + ">" + SPACE + syntaxListExecOp + SPACE + "=" + SPACE + "new ArrayList<" + pakage + codeExecOp + ">() ;").append(NL_TAB);
											
											// Append : ob.setParentObj(lstSqlOutput);
											String setterOfParameter = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), false, objectDefinition.getObjectDefinitionId(), BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID, blogicObSyntax, executionOutputValue.getLstTargetIndex());
											
											// Append clear list had assign
											strGenExecDetails.append(String.format("%s(new java.util.ArrayList<%s>()) ;", setterOfParameter, pakage + codeExecOp)).append(NL_TAB);
											
											// Append : for(SqlDesignOuputValue obj : lstSqlDesignOuputValue){
											strGenExecDetails.append("for(" + objSqlOutput + SPACE + "obj" + SPACE + ":" + SPACE + outputSyntax + ") {").append(NL_DOUBLE_TAB);
											// Append: SqlDesignOuput sqlDesignOuput = new SqlDesignOuput();
											strGenExecDetails.append(pakage + codeExecOp + SPACE + syntaxExecOp + SPACE + "=" + SPACE + "new" + SPACE + pakage + codeExecOp + "();").append(NL_DOUBLE_TAB);
											for (ExecutionOutputValue child : lstExcOpChild) {
												SqlDesignOutput sqlOutChild = mapSqlOutput.get(child.getSqlDesignOutputId());
												List<SqlDesignOutput> lstTmp = new ArrayList<SqlDesignOutput>();
												lstTmp.add(sqlOutChild);
												// Build map for setter of list child
												Map<String, List<?>> mAllChild = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(1, null, null, lstTmp);
												String getterOfSqlOut = detailServiceImpHandler.getterAndSetterOfParameter(1, mAllParentAndSeflByLevelOfInOutObjCustom, true, sqlOutChild.getSqlDesignOutputId().toString(), BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, "obj", null);
												String setterOfChild = detailServiceImpHandler.getterAndSetterOfParameter(1, mAllChild, false, sqlOutChild.getSqlDesignOutputId().toString(), BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, syntaxExecOp, child.getLstTargetIndex());
												if(sqlOutChild.getArrayFlag()!= null && sqlOutChild.getArrayFlag() != 0 && CollectionUtils.isNotEmpty(child.getLstTargetIndex())) {
													strGenExecDetails.append(String.format(setterOfChild, getterOfSqlOut)+";").append(NL_DOUBLE_TAB);
												} else {
													strGenExecDetails.append(MessageFormat.format(BLOGIC_SETTER, setterOfChild, getterOfSqlOut)).append(NL_DOUBLE_TAB);
												}
											}
											
											// Adding to list exist
											strGenExecDetails.append(syntaxListExecOp + ".add(" + syntaxExecOp + ");").append(NL_TAB);
											strGenExecDetails.append("}").append(NL_TAB);
											
											if(CollectionUtils.isNotEmpty(executionOutputValue.getLstTargetIndex())){
												strGenExecDetails.append(String.format(setterOfParameter, syntaxListExecOp)+";").append(NL_TAB);
											} else {
												strGenExecDetails.append(MessageFormat.format(BLOGIC_SETTER, setterOfParameter, syntaxListExecOp)).append(NL_TAB);
											}
										}
									} else {
										if (FunctionCommon.isNotEmpty(lstExcOpChild)) {
											// Append: SqlDesignOuput sqlDesignOuput = new SqlDesignOuput();
											if(objectDefinition != null && StringUtils.isNotEmpty(objectDefinition.getTblDesignCode())) {
												strGenExecDetails.append(pakage + GenerateSourceCodeUtil.normalizedClassName(objectDefinition.getTblDesignCode()) + SPACE + syntaxExecOp + SPACE + "=" + SPACE + "new" + SPACE + pakage + GenerateSourceCodeUtil.normalizedClassName(objectDefinition.getTblDesignCode()) + "();").append(NL_TAB);
											} else {
												strGenExecDetails.append(pakage + codeExecOp + SPACE + syntaxExecOp + SPACE + "=" + SPACE + "new" + SPACE + pakage + codeExecOp + "();").append(NL_TAB);
											}
											
											for (ExecutionOutputValue child : lstExcOpChild) {
												SqlDesignOutput sqlOutChild = mapSqlOutput.get(child.getSqlDesignOutputId());
												List<SqlDesignOutput> lstTmp = new ArrayList<SqlDesignOutput>();lstTmp.add(sqlOutChild);
												// Build map for setter of list child
												Map<String, List<?>> mAllChild = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(1, null, null, lstTmp);
												String getterOfSqlOut = detailServiceImpHandler.getterAndSetterOfParameter(1, mAllParentAndSeflByLevelOfInOutObjCustom, true, sqlOutChild.getSqlDesignOutputId().toString(), BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, outputSyntax, null);
												String setterOfChild = detailServiceImpHandler.getterAndSetterOfParameter(1, mAllChild, false, sqlOutChild.getSqlDesignOutputId().toString(), BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, syntaxExecOp, child.getLstTargetIndex());
												if(sqlOutChild.getArrayFlag()!= null && sqlOutChild.getArrayFlag() != 0 && CollectionUtils.isNotEmpty(child.getLstTargetIndex())) {
													strGenExecDetails.append(String.format(setterOfChild, getterOfSqlOut)+";").append(NL_DOUBLE_TAB);
												} else {
													strGenExecDetails.append(MessageFormat.format(BLOGIC_SETTER, setterOfChild, getterOfSqlOut)).append(NL_DOUBLE_TAB);
												}
											}
											
											String setterOfParameter = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), false, objectDefinition.getObjectDefinitionId(), BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID, blogicObSyntax, executionOutputValue.getLstTargetIndex());
											if(Boolean.TRUE.equals(objectDefinition.getArrayFlg()) && CollectionUtils.isNotEmpty(executionOutputValue.getLstTargetIndex())) {
												strGenExecDetails.append(String.format(setterOfParameter,  syntaxExecOp)+";").append(NL);
											} else {
												strGenExecDetails.append(MessageFormat.format(BLOGIC_SETTER, setterOfParameter, syntaxExecOp)).append(NL);
											}
										}
									}
									hashSetParentExecOutput.add(executionOutputValue);
								}
							} else {
								// Adding by HungHX 06/04/2016
								
								SqlDesignOutput sqlOutput = mapSqlOutput.get(executionOutputValue.getSqlDesignOutputId());
								if (sqlOutput.getSqlDesignOutputParentId() == null && RETURN_TYPE_LIST.equals(currentComponent.getReturnType()) && objectDefinition.getParentObjectDefinitionId() != null) {

									if(isFirst == 0) {
										ObjectDefinition parentObj = mapObjDef.get(objectDefinition.getParentObjectDefinitionId());
										// Build assign for list
										String codeObjDeclare = GenerateSourceCodeUtil.normalizedClassName(detailServiceImpHandler.getNameDeclareObjByScope(0, BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID, parentObj));
										String fullPakage = getPackageName(param, parentObj) + codeObjDeclare;
										
										parentForGetter = "obj";
										parentForSetter = syntaxExecOp;
										parentForAddToLst = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), true, parentObj.getObjectDefinitionId(), BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID, param.getBlogicObSyntax(), null);
										
										String setterParentForAddToLst = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), false, parentObj.getObjectDefinitionId(), BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID, param.getBlogicObSyntax(), null);
										strGenExecDetails.append(String.format("%s(new java.util.ArrayList<%s>()) ;", setterParentForAddToLst, fullPakage)).append(NL_TAB);
										//Fix bug assign output Execution node
										
										strGenExecDetails.append("for(" + GenerateSourceCodeUtil.normalizedClassName(sqlDesignCode)+"OutputBean" + SPACE + parentForGetter + SPACE + ":" + SPACE + outputSyntax + ") {").append(NL_DOUBLE_TAB);
										strGenExecDetails.append(fullPakage + SPACE + parentForSetter + SPACE + "=" + SPACE + "new" + SPACE + fullPakage + "();").append(NL_DOUBLE_TAB);
									}
									
									String getter = parentForGetter+".get"+ GenerateSourceCodeUtil.normalizedClassName(sqlOutput.getSqlDesignOutputCode())+"()";
									getter = BusinessLogicGenerateHelper.getContentByCastDataType(objectDefinition.getDataType(), sqlOutput.getDataType(), getter);
									
									strGenExecDetails.append(parentForSetter + ".set"+GenerateSourceCodeUtil.normalizedClassName(objectDefinition.getObjectDefinitionCode())+"("+getter+");").append(NL_DOUBLE_TAB);

									if(idx == lstOutputValue.size() - 1) {
										strGenExecDetails.append(parentForAddToLst+".add("+parentForSetter+");").append(NL_DOUBLE_TAB);
										strGenExecDetails.append("}").append("\n\t\t");
									}

									isFirst++;
								} else if(sqlOutput.getSqlDesignOutputParentId() == null && !RETURN_TYPE_LIST.equals(currentComponent.getReturnType())) {
									String setterOfParameter = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), false, objectDefinition.getObjectDefinitionId(), BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID, blogicObSyntax, executionOutputValue.getLstTargetIndex());
									String getterOfSqlOut = detailServiceImpHandler.getterAndSetterOfParameter(1, mAllParentAndSeflByLevelOfInOutObjCustom, true, sqlOutput.getSqlDesignOutputId().toString(), BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, outputSyntax, null);
									getterOfSqlOut = BusinessLogicGenerateHelper.getContentByCastDataType(objectDefinition.getDataType(), sqlOutput.getDataType(), getterOfSqlOut);
									if(Boolean.TRUE.equals(objectDefinition.getArrayFlg()) && CollectionUtils.isNotEmpty(executionOutputValue.getLstTargetIndex())){
										strGenExecDetails.append(String.format(setterOfParameter, getterOfSqlOut)+";").append(NL);
									} else {
										strGenExecDetails.append(MessageFormat.format(BLOGIC_SETTER, setterOfParameter, getterOfSqlOut)).append(NL);
									}
								}
							}
						// In the case assign to output bean
						} else if(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(executionOutputValue.getTargetScope())) {
							OutputBean outputBean = mapOutputBean.get(executionOutputValue.getTargetId());
							String codeExecOp = GenerateSourceCodeUtil.normalizedClassName(detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC, executionOutputValue.getTargetScope(), outputBean));
							
							if (BusinessDesignConst.DataType.ENTITY.equals(executionOutputValue.getDataType()) 
									|| BusinessDesignConst.DataType.OBJECT.equals(executionOutputValue.getDataType())
									|| BusinessDesignConst.DataType.COMMON_OBJECT.equals(executionOutputValue.getDataType())
									|| BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(executionOutputValue.getDataType())) {
								
								String pakage = this.getPackageName(param, outputBean);
								
								if (!hashSetParentExecOutput.contains(executionOutputValue)) {
									List<ExecutionOutputValue> lstExcOpChild = mapParentAndChildExecOutput.get(executionOutputValue.getExecutionOutputValueId());
									if (executionOutputValue.getArrayFlg()) {
										if (FunctionCommon.isNotEmpty(lstExcOpChild)) {
											// Append : List<SqlDesignOuput> lstSqlOutput = new ArrayList<SqlDesignOuput>();
											strGenExecDetails.append("List<" + pakage + codeExecOp + ">" + SPACE + syntaxListExecOp + SPACE + "=" + SPACE + "new ArrayList<" + pakage + codeExecOp + ">() ;").append(NL_TAB);
											
											String setterOfParameter = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), false, outputBean.getOutputBeanId(), BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, blogicOutputSyntax, null);
											
											strGenExecDetails.append(String.format("%s(new java.util.ArrayList<%s>()) ;", setterOfParameter, pakage + codeExecOp)).append(NL_TAB);
											
											// Append : for(SqlDesignOuputValue obj : lstSqlDesignOuputValue){
											strGenExecDetails.append("for(" + objSqlOutput + SPACE + "obj" + SPACE + ":" + SPACE + outputSyntax + ") {").append(NL_DOUBLE_TAB);
											// Append: SqlDesignOuput sqlDesignOuput = new SqlDesignOuput();
											strGenExecDetails.append(pakage + codeExecOp + SPACE + syntaxExecOp + SPACE + "=" + SPACE + "new" + SPACE + pakage + codeExecOp + "();").append(NL_DOUBLE_TAB);
											for (ExecutionOutputValue child : lstExcOpChild) {
												SqlDesignOutput sqlOutChild = mapSqlOutput.get(child.getSqlDesignOutputId());
												List<SqlDesignOutput> lstTmp = new ArrayList<SqlDesignOutput>();lstTmp.add(sqlOutChild);
												// Build map for setter of list child
												Map<String, List<?>> mAllChild = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(1, null, null, lstTmp);
												
												String getterOfSqlOut = detailServiceImpHandler.getterAndSetterOfParameter(1, mAllParentAndSeflByLevelOfInOutObjCustom, true, sqlOutChild.getSqlDesignOutputId().toString(), BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, "obj", null);
												
												String setterOfChild = detailServiceImpHandler.getterAndSetterOfParameter(1, mAllChild, false, sqlOutChild.getSqlDesignOutputId().toString(), BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, syntaxExecOp, child.getLstTargetIndex());
												if(sqlOutChild.getArrayFlag()!= null && sqlOutChild.getArrayFlag() != 0 && CollectionUtils.isNotEmpty(child.getLstTargetIndex())) {
													strGenExecDetails.append(String.format(setterOfChild, getterOfSqlOut)+";").append(NL_DOUBLE_TAB);
												} else {
													strGenExecDetails.append(MessageFormat.format(BLOGIC_SETTER, setterOfChild, getterOfSqlOut)).append(NL_DOUBLE_TAB);
												}
											}
											strGenExecDetails.append(syntaxListExecOp + ".add(" + syntaxExecOp + ");").append(NL_TAB);
											strGenExecDetails.append("}").append(NL_TAB);
											// Append : ob.setParentObj(lstSqlOutput);
											strGenExecDetails.append(MessageFormat.format(BLOGIC_SETTER, setterOfParameter, syntaxListExecOp)).append(NL_TAB);
										}
									} else {
										if (FunctionCommon.isNotEmpty(lstExcOpChild)) {
											// Append: SqlDesignOuput sqlDesignOuput = new SqlDesignOuput();
											if(outputBean != null && StringUtils.isNotEmpty(outputBean.getTblDesignCode())) {
												strGenExecDetails.append(pakage + GenerateSourceCodeUtil.normalizedClassName(outputBean.getTblDesignCode()) + SPACE + syntaxExecOp + SPACE + "=" + SPACE + "new" + SPACE + pakage + GenerateSourceCodeUtil.normalizedClassName(outputBean.getTblDesignCode()) + "();").append(NL_TAB);
											} else {
												strGenExecDetails.append(pakage + codeExecOp + SPACE + syntaxExecOp + SPACE + "=" + SPACE + "new" + SPACE + pakage + codeExecOp + "();").append(NL_TAB);
											}
											
											for (ExecutionOutputValue child : lstExcOpChild) {
												SqlDesignOutput sqlOutChild = mapSqlOutput.get(child.getSqlDesignOutputId());
												List<SqlDesignOutput> lstTmp = new ArrayList<SqlDesignOutput>();lstTmp.add(sqlOutChild);
												// Build map for setter of list child
												Map<String, List<?>> mAllChild = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(1, null, null, lstTmp);
												
												String getterOfSqlOut = detailServiceImpHandler.getterAndSetterOfParameter(1, mAllParentAndSeflByLevelOfInOutObjCustom, true, sqlOutChild.getSqlDesignOutputId().toString(), BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, outputSyntax, null);
												
												String setterOfChild = detailServiceImpHandler.getterAndSetterOfParameter(1, mAllChild, false, sqlOutChild.getSqlDesignOutputId().toString(), BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, syntaxExecOp, child.getLstTargetIndex());
												if(sqlOutChild.getArrayFlag()!= null && sqlOutChild.getArrayFlag() != 0 && CollectionUtils.isNotEmpty(child.getLstTargetIndex())) {
													strGenExecDetails.append(String.format(setterOfChild, getterOfSqlOut)+";").append(NL_DOUBLE_TAB);
												} else {
													strGenExecDetails.append(MessageFormat.format(BLOGIC_SETTER, setterOfChild, getterOfSqlOut)).append(NL_DOUBLE_TAB);
												}
											}
											
											String setterOfParameter = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), false, outputBean.getOutputBeanId(), BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, blogicOutputSyntax, executionOutputValue.getLstTargetIndex());
											
											if(Boolean.TRUE.equals(outputBean.getArrayFlg()) && CollectionUtils.isNotEmpty(executionOutputValue.getLstTargetIndex())) {
												strGenExecDetails.append(String.format(setterOfParameter,  syntaxExecOp)+";").append(NL);
											} else {
												strGenExecDetails.append(MessageFormat.format(BLOGIC_SETTER, setterOfParameter, syntaxExecOp)).append(NL);
											}
											
											strGenExecDetails.append(MessageFormat.format(BLOGIC_SETTER, setterOfParameter, syntaxExecOp)).append(NL);
										}
									}
									hashSetParentExecOutput.add(executionOutputValue);
								}
							} else {
								// Adding by HungHX 06/04/2016
								SqlDesignOutput sqlOutput = mapSqlOutput.get(executionOutputValue.getSqlDesignOutputId());
								if (sqlOutput.getSqlDesignOutputParentId() == null && RETURN_TYPE_LIST.equals(currentComponent.getReturnType()) && outputBean.getParentOutputBeanId() != null) {
									if(isFirst == 0) {
										OutputBean parentOut = mapOutputBean.get(outputBean.getParentOutputBeanId());
										// Build assign for list
										String codeObjDeclare = GenerateSourceCodeUtil.normalizedClassName(detailServiceImpHandler.getNameDeclareObjByScope(0, BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, parentOut));
										String fullPakage = getPackageName(param, parentOut) + codeObjDeclare;
										
										parentForGetter = "obj";
										parentForSetter = syntaxExecOp;
										parentForAddToLst = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), true, parentOut.getOutputBeanId(), BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, param.getBlogicOutputSyntax(), null);
										String setterParentForAddToLst = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), false, parentOut.getOutputBeanId(), BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, param.getBlogicOutputSyntax(), null);
										strGenExecDetails.append(String.format("%s(new java.util.ArrayList<%s>()) ;", setterParentForAddToLst, fullPakage)).append(NL_TAB);
										
										strGenExecDetails.append("for(" + GenerateSourceCodeUtil.normalizedClassName(sqlDesignCode)+"OutputBean" + SPACE + parentForGetter + SPACE + ":" + SPACE + outputSyntax + ") {").append(NL_DOUBLE_TAB);
										strGenExecDetails.append(fullPakage + SPACE + parentForSetter + SPACE + "=" + SPACE + "new" + SPACE + fullPakage + "();").append(NL_DOUBLE_TAB);
									}
									
									String getter = parentForGetter+".get"+ GenerateSourceCodeUtil.normalizedClassName(sqlOutput.getSqlDesignOutputCode())+"()";
									getter = BusinessLogicGenerateHelper.getContentByCastDataType(outputBean.getDataType(), sqlOutput.getDataType(), getter);
									strGenExecDetails.append(parentForSetter + ".set"+GenerateSourceCodeUtil.normalizedClassName(outputBean.getOutputBeanCode())+"("+getter+");").append(NL_DOUBLE_TAB);

									if(idx == lstOutputValue.size() - 1) {
										strGenExecDetails.append(parentForAddToLst+".add("+parentForSetter+");").append(NL_DOUBLE_TAB);
										strGenExecDetails.append("}").append("\n\t\t");
									}

									isFirst++;
								} else if(sqlOutput.getSqlDesignOutputParentId() == null && !RETURN_TYPE_LIST.equals(currentComponent.getReturnType())) {
									String setterOfParameter = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), false, outputBean.getOutputBeanId(), BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, blogicOutputSyntax, executionOutputValue.getLstTargetIndex());
									String getterOfSqlOut = detailServiceImpHandler.getterAndSetterOfParameter(1, mAllParentAndSeflByLevelOfInOutObjCustom, true, sqlOutput.getSqlDesignOutputId().toString(), BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, outputSyntax, null);
									getterOfSqlOut = BusinessLogicGenerateHelper.getContentByCastDataType(outputBean.getDataType(), sqlOutput.getDataType(), getterOfSqlOut);
									if(Boolean.TRUE.equals(outputBean.getArrayFlg()) && CollectionUtils.isNotEmpty(executionOutputValue.getLstTargetIndex())){
										strGenExecDetails.append(String.format(setterOfParameter, getterOfSqlOut)+";").append(NL);
									} else {
										strGenExecDetails.append(MessageFormat.format(BLOGIC_SETTER, setterOfParameter, getterOfSqlOut)).append(NL);
									}
								}
							}
						}
					}
					strGenExecDetails.append("}").append(NL);
				// Build sql output in the case list output for assign is not exist
				}
			} else {
				
				if (currentComponent.getSqlPattern().equals(String.valueOf(SqlPattern.DELETE)) || StringUtils.EMPTY.equals(sbCheckNullInput.toString())) {
					strGenExecDetails.append((currentComponent.getModuleId() != null? GenerateSourceCodeUtil.normalizedMethodName(currentComponent.getModuleCode()) : "common") + "Repository." + GenerateSourceCodeUtil.normalizedMethodName(sqlDesignCode) + "(" + inputSyntax + ") ;").append(NL);
				} else {
					String outputSyntax = "output" + GenerateSourceCodeUtil.normalizedClassName(sqlDesignCode) + currentComponent.getExecutionComponentId();
					strGenExecDetails.append(sbCheckNullInput.toString());
					strGenExecDetails.append(String.format("Integer %s = ", outputSyntax));
					strGenExecDetails.append((currentComponent.getModuleId() != null? GenerateSourceCodeUtil.normalizedMethodName(currentComponent.getModuleCode()) : "common") + "Repository." + GenerateSourceCodeUtil.normalizedMethodName(sqlDesignCode) + "(" + inputSyntax + ") ;").append(NL);
					// Adding check currency
					if(currentComponent.getSqlPattern().equals(String.valueOf(SqlPattern.UPDATE)) && BusinessDesignConst.MODULE_TYPE_ONLINE.equals(param.getModule().getModuleType())) {
						strGenExecDetails.append("\n\t\t\t");
						strGenExecDetails.append(String.format("if(%s < 0) {", outputSyntax)).append("\n\t\t\t\t");
						strGenExecDetails.append("throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));").append("\n\t\t\t");
						strGenExecDetails.append("}").append("\n\t\t");
					}
					strGenExecDetails.append("}").append(NL);
				}

				SqlDesignInput entitySqlInput = null;
				Map<Long, SqlDesignInput> mapColumnIdAndSqlDesignInput = new HashMap<Long, SqlDesignInput>();
				for (SqlDesignInput sqlInput : lstSqlDesignInputs) {
					if (BusinessDesignConst.DataType.ENTITY.equals(sqlInput.getDataType())) {
						entitySqlInput = sqlInput;
					}
					if (sqlInput.getColumnId() != null) {
						mapColumnIdAndSqlDesignInput.put(sqlInput.getColumnId(), sqlInput);
					}
				}
				if (entitySqlInput != null) {
					// TrungDV :  set parent_id to ob parent
					ModuleTableMapping[] allTableOfModule = moduleTableMappingRepository.findModuleTableMappingByModuleId(module.getModuleId());
					boolean genFlag = false;
					if (allTableOfModule != null && allTableOfModule.length > 0) {
						for (ModuleTableMapping table : allTableOfModule) {
							if (table.getTblDesignId() != null && DataTypeUtils.equals(table.getTblDesignId(), entitySqlInput.getTableId()) && ScreenDesignConst.TableMappingType.SINGLE.equals(table.getTableMappingType())) {
								genFlag = true;
							}
						}
					}
					if (genFlag) {
						List<TableDesignDetails> lstColumns = tableDesignDetailRepository.getAllInformationByTableDesign(entitySqlInput.getTableId()) != null ? tableDesignDetailRepository.getAllInformationByTableDesign(entitySqlInput.getTableId()) : new ArrayList<TableDesignDetails>();
						List<TableDesignDetails> lstPK = new ArrayList<TableDesignDetails>();
						for (TableDesignDetails col : lstColumns) {
							if (DbDomainConst.YesNoFlg.YES.equals(col.isKey(DbDomainConst.TblDesignKeyType.PK)) && DbDomainConst.DisplayType.UNUSED.equals(col.getDisplayType())) {
								lstPK.add(col);
							}
						}
						for (TableDesignDetails col : lstPK) {
							SqlDesignInput sqlInput = mapColumnIdAndSqlDesignInput.get(col.getColumnId());
							for (ObjectDefinition ob : blogic.getLstObjectDefinition()) {
								if (col.getColumnId().equals(ob.getColumnId())) {
									// String setterOfParameter = detailServiceImpHandler.getterAndSetterOfParameter(ob.getObjectDefinitionId(), BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID, blogicObSyntax, null, false, false);
									String setterOfParameter = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), false, ob.getObjectDefinitionId(), BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID, blogicObSyntax, null);
									// String getterOfSqlInput = detailServiceImpHandler.getterAndSetterOfParameter(sqlInput.getSqlDesignInputId().toString(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputSyntax, sqlNameParameter, true, true);
									String getterOfSqlInput = detailServiceImpHandler.getterAndSetterOfParameter(1, mAllParentAndSeflByLevelOfInOutObjCustom, true, sqlInput.getSqlDesignInputId().toString(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputSyntax, null);
									// Cas data typr
									getterOfSqlInput = BusinessLogicGenerateHelper.getContentByCastDataType(ob.getDataType(), sqlInput.getDataType(), getterOfSqlInput);
									strGenExecDetails.append(MessageFormat.format(BLOGIC_SETTER, setterOfParameter, getterOfSqlInput)).append(NL);
									break;
								}
							}
						}
					}
					// End by TrungDV :  set parent_id to ob parent
				}
			}
			currentComponent.setStrGenExecDetails(strGenExecDetails.toString());
			sbCheckNullInput.setLength(0);
		}
		builder.append(strGenExecDetails);
		postGencode(builder, param);
	}

	private StringBuilder buildPassParameterInput(BLogicHandlerIo param, StringBuilder strGenExecDetails) {
		
		// All Data desing for generate
		// Input syntax of sql input
		String inputSyntax = "input" + GenerateSourceCodeUtil.normalizedClassName(currentComponent.getSqlDesignCodeRefer()) + currentComponent.getExecutionComponentId();
		// Get all sql input
		List<SqlDesignInput> lstSqlDesignInputs = new ArrayList<SqlDesignInput>(Arrays.asList(sqlDesignInputRepository.findAllBySqlDesignId(currentComponent.getSqlDesignId())));
		// All input parameter of sql input
		List<ExecutionInputValue> lstInputValue = currentComponent.getParameterInputBeans();
		// Buil map parent of  sql input for setter getter
		Map<String, List<?>> mAllParentAndSeflByLevelOfInOutObjCustom = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(1, lstSqlDesignInputs, null, null);
		// Storing id of sql input
		Map<Long, SqlDesignInput> mapSqlInput = new HashMap<Long, SqlDesignInput>();
		if (CollectionUtils.isNotEmpty(lstSqlDesignInputs)) {
			for (SqlDesignInput sqlInput : lstSqlDesignInputs) {
				mapSqlInput.put(sqlInput.getSqlDesignInputId(), sqlInput);
			}
		}

		// Build mapid of output and object definition
		Map<String, ObjectDefinition> mIdObjDef = new HashMap<String, ObjectDefinition>();
		if (CollectionUtils.isNotEmpty(lstSqlDesignInputs)) {
			for (ObjectDefinition obj : param.getBusinessDesign().getLstObjectDefinition()) {
				mIdObjDef.put(obj.getObjectDefinitionId(), obj);
			}
		}

		Map<String, InputBean> mIdInput = new HashMap<String, InputBean>();
		if (CollectionUtils.isNotEmpty(lstSqlDesignInputs)) {
			for (InputBean ou : param.getBusinessDesign().getLstInputBean()) {
				mIdInput.put(ou.getInputBeanId(), ou);
			}
		}	
		
		// Custom variable for main processing
		int countExeInputDetail = 0;
		// Storing parent id of object
		String parentIdOfInput = StringUtils.EMPTY;
		String instanceNmObj = StringUtils.EMPTY;
		// Start is false
		Boolean isParentArray = false;
		// storing parent input setter
		String parentInputForSet = StringUtils.EMPTY;
		// Parent output for getter
		String parentobjDefForGet = StringUtils.EMPTY;
		// Getter from parent input is list
		String getterParentInputList = StringUtils.EMPTY;
		
		// Initial variable
		Object objMark = new Object();
		int dataType = -1;
		String id = StringUtils.EMPTY;
		String parentId = StringUtils.EMPTY;
		String codeScope = StringUtils.EMPTY;
		boolean arrayFlg = false;
		
		// Main process
		for (ExecutionInputValue exeInput : lstInputValue) {
			countExeInputDetail++;
			boolean isTwooArrayPrimitive = false;
				
			SqlDesignInput sqlInput = mapSqlInput.get(exeInput.getSqlDesignInputId());

			if(sqlInput == null) continue;
			
			if (BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.equals(exeInput.getParameterScope())) {
				InputBean ou = mIdInput.get(exeInput.getParameterId());
				if(ou == null) continue;
				objMark = ou;
				dataType = ou.getDataType();
				id = ou.getInputBeanId();
				parentId = ou.getParentInputBeanId();
				codeScope = param.getBlogicInSyntax();
				arrayFlg = ou.getArrayFlg();
			} else if (BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(exeInput.getParameterScope())) {
				ObjectDefinition objDef =  mIdObjDef.get(exeInput.getParameterId());
				if(objDef == null) continue;
				objMark = objDef;
				dataType = objDef.getDataType();
				id = objDef.getObjectDefinitionId();
				parentId = objDef.getParentObjectDefinitionId();
				codeScope = param.getBlogicObSyntax();
				arrayFlg = objDef.getArrayFlg();
			}

			if(sqlInput != null && sqlInput.getDataType() != null 
					&& (BusinessDesignConst.DataType.OBJECT.equals(sqlInput.getDataType()) || BusinessDesignConst.DataType.ENTITY.equals(sqlInput.getDataType()) 
							|| BusinessDesignConst.DataType.COMMON_OBJECT.equals(sqlInput.getDataType()) || BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(sqlInput.getDataType()))) {
				
				// Marking value
				parentIdOfInput = sqlInput.getSqlDesignInputId().toString();
				
				// In the case is object
				if(sqlInput.getArrayFlag().equals(0)) {
					
					if(Boolean.TRUE.equals(isParentArray)) {
						strGenExecDetails.append(getterParentInputList + ".add("+parentInputForSet+");").append(String.format("\n\t\t}\n\t\t"));
						isParentArray = false;
					}
					
					// Getter of parent
					String getterParent = detailServiceImpHandler.getterAndSetterOfParameter(1, mAllParentAndSeflByLevelOfInOutObjCustom, IS_GETTER, sqlInput.getSqlDesignInputId().toString(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputSyntax, null);
					//
					String setterOfSqlIn = detailServiceImpHandler.getterAndSetterOfParameter(1, mAllParentAndSeflByLevelOfInOutObjCustom, IS_SETTER, sqlInput.getSqlDesignInputId().toString(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputSyntax, null);
					// Get name of instance object					
					instanceNmObj = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.SQLDESIGN, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, sqlInput);
					
					String pakage =  getPackageName(param, sqlInput);
					
					// Mapping check null input for sql
					sbCheckNullInput.append("if(" + getterParent + SPACE + "!= null) {").append(NL_TAB);
					
					strGenExecDetails.append("if(" + getterParent + SPACE + "== null) {").append(NL_TAB);
					strGenExecDetails.append(setterOfSqlIn + "(new" + SPACE + pakage + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj) + "()" + ");").append(NL);
					strGenExecDetails.append("}").append(NL);
					
				} else {
					
					if(Boolean.TRUE.equals(isParentArray)) {
						strGenExecDetails.append(getterParentInputList + ".add("+parentInputForSet+");").append(String.format("\n\t\t}\n\t\t"));
						isParentArray = false;
					}
					
					// In the case array object
					strGenExecDetails.append("\n\t");
					// Marking value
					isParentArray = true;
					// Marking parent syntax for set
					parentInputForSet = "temp";
					
					// Get name of instance object
					instanceNmObj = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.SQLDESIGN, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, sqlInput);
					
					String getterParent = detailServiceImpHandler.getterAndSetterOfParameter(1, mAllParentAndSeflByLevelOfInOutObjCustom, IS_GETTER, sqlInput.getSqlDesignInputId().toString(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputSyntax, null);
					
					String setterOfSqlIn = detailServiceImpHandler.getterAndSetterOfParameter(1, mAllParentAndSeflByLevelOfInOutObjCustom, false, sqlInput.getSqlDesignInputId().toString(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputSyntax, null);

					String pakage =  getPackageName(param, sqlInput);

					// Mapping check null input for sql
					sbCheckNullInput.append("if(" + getterParent + SPACE + "!= null && "+ getterParent+".size()" + SPACE + "> 0 ) {").append(NL_TAB);
					
					strGenExecDetails.append("if(" + getterParent + SPACE + "== null) {").append(NL_TAB);
					strGenExecDetails.append(setterOfSqlIn + "(new ArrayList< " + pakage + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj) + ">()" + ");").append(NL);
					strGenExecDetails.append("}").append(NL);
					
					strGenExecDetails.append(String.format("%s.clear();", getterParent)).append(NL_TAB);
					// Building for each
					
					parentobjDefForGet = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC, exeInput.getParameterScope(), objMark);
					
					String declareObj = getPackageName(param, objMark) + GenerateSourceCodeUtil.normalizedClassName(parentobjDefForGet);
					
					// Get list to for
					String getterListToForEach = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, exeInput.getParameterId(), exeInput.getParameterScope(), codeScope, null);
					
					strGenExecDetails.append("\n\t\t").append(String.format("for (%s "+parentobjDefForGet+" : %s){", declareObj, getterListToForEach)).append("\n\t\t\t");
					// Build new temporary object from input refer
					strGenExecDetails.append(MessageFormat.format(INIT_OBJECT, pakage + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj), parentInputForSet)).append("\n\t\t\t");
					
					// Using for add one item into the list object
					getterParentInputList = detailServiceImpHandler.getterAndSetterOfParameter(1, mAllParentAndSeflByLevelOfInOutObjCustom, IS_GETTER, sqlInput.getSqlDesignInputId().toString(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputSyntax, null);
				
					// In the case item is end of list
					if(countExeInputDetail == lstInputValue.size()){
						strGenExecDetails.append(getterParentInputList + ".add("+parentInputForSet+");").append(String.format("\n\t\t}\n\t\t"));
					}
				}
					
				continue;
			}
			
			String getter = StringUtils.EMPTY;
			
			if (exeInput.getParameterId() != null) {
				// Marking two list primitive
				if(sqlInput.getArrayFlag() == 1 && arrayFlg && DataTypeUtils.notEquals(sqlInput.getDataType(), dataType)) isTwooArrayPrimitive = true;

				// In the case item is top level
				if (sqlInput.getSqlDesignInputParentId() == null) {
					
					if(Boolean.TRUE.equals(isParentArray)) {
						strGenExecDetails.append(String.format("\n\t\t\t")).append(getterParentInputList + ".add(temp);").append(String.format("\n\t\t}")).append(String.format("\n\t\t"));
						isParentArray = false;
					}
					
					if(isTwooArrayPrimitive) {
						strGenExecDetails.append("\n\t\t");
						String targetNull = detailServiceImpHandler.getterAndSetterOfParameter(1, mAllParentAndSeflByLevelOfInOutObjCustom, IS_GETTER, sqlInput.getSqlDesignInputId().toString(), 0, inputSyntax, exeInput.getLstParameterIndex());
						strGenExecDetails.append(String.format("if (%s == null) {", targetNull));
						strGenExecDetails.append("\n\t\t\t");
						String dataTypeSrc = GenerateSourceCodeUtil.getPrimitiveTypeName(sqlInput.getDataType());
						String targetSet = detailServiceImpHandler.getterAndSetterOfParameter(1, mAllParentAndSeflByLevelOfInOutObjCustom, IS_SETTER, sqlInput.getSqlDesignInputId().toString(), 0, inputSyntax, exeInput.getLstParameterIndex())+"(new ArrayList<"+dataTypeSrc+">());";
						strGenExecDetails.append(targetSet);
						strGenExecDetails.append("\n\t\t}");
						strGenExecDetails.append("\n\t\t");
						strGenExecDetails.append(String.format("%s.clear();", targetNull));
						strGenExecDetails.append("\n\t\t");
						String getterParam = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, id, exeInput.getParameterScope(), codeScope, exeInput.getLstParameterIndex());
						
						if(GenerateSourceCodeConst.DataType.BYTE == dataType){
							strGenExecDetails.append(String.format("if (%s != null && %s.length > 0) {", getterParam, getterParam));
						} else {
							strGenExecDetails.append(String.format("if (%s != null && %s.size() > 0) {", getterParam, getterParam));
						}
						
						strGenExecDetails.append("\n\t\t\t");
						strGenExecDetails.append(String.format("for (%s %s : %s) {", GenerateSourceCodeUtil.getPrimitiveTypeName(dataType), "iter", getterParam));
						String paramInput = BusinessLogicGenerateHelper.getContentByCastDataType(sqlInput.getDataType(), dataType, "iter");
						strGenExecDetails.append("\n\t\t\t\t");
						String setterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(1, mAllParentAndSeflByLevelOfInOutObjCustom, IS_GETTER, sqlInput.getSqlDesignInputId().toString(), 0, inputSyntax , exeInput.getLstParameterIndex())+".add("+paramInput+");";
						strGenExecDetails.append(setterInReferParent);
						strGenExecDetails.append("\n\t\t\t");
						strGenExecDetails.append("}");
						strGenExecDetails.append("\n\t\t");
						strGenExecDetails.append("}");
						strGenExecDetails.append("\n\t\t");
					} else {
						getter = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, id, exeInput.getParameterScope(), codeScope , exeInput.getLstParameterIndex());
						getter = BusinessLogicGenerateHelper.getContentByCastDataType(sqlInput.getDataType(), dataType, getter);
						
						if(sqlInput.getArrayFlag().equals(0) && BusinessDesignConst.DataType.BOOLEAN.equals(sqlInput.getDataType())) {
							getter = String.format("(%s == null)? false : %s",  getter, getter);
						}
						
						// setter of child
						String setterChild = detailServiceImpHandler.getterAndSetterOfParameter(1, mAllParentAndSeflByLevelOfInOutObjCustom, IS_SETTER, sqlInput.getSqlDesignInputId().toString(), 0, inputSyntax, null);
						
						strGenExecDetails.append(MessageFormat.format(INPUT_SETTER, setterChild, getter)).append("\n\t\t");
					}
				// In the case item is top level	
				} else if(!isParentArray && parentIdOfInput != null && parentIdOfInput.equals(sqlInput.getSqlDesignInputParentId().toString())) {
					if(isTwooArrayPrimitive) {
						strGenExecDetails.append("\n\t\t");
						String targetNull = detailServiceImpHandler.getterAndSetterOfParameter(1, mAllParentAndSeflByLevelOfInOutObjCustom, IS_GETTER, sqlInput.getSqlDesignInputId().toString(), 0, inputSyntax, exeInput.getLstParameterIndex());
						strGenExecDetails.append(String.format("if (%s == null) {", targetNull));
						strGenExecDetails.append("\n\t\t\t");
						String dataTypeSrc = GenerateSourceCodeUtil.getPrimitiveTypeName(sqlInput.getDataType());
						String targetSet = detailServiceImpHandler.getterAndSetterOfParameter(1, mAllParentAndSeflByLevelOfInOutObjCustom, IS_SETTER, sqlInput.getSqlDesignInputId().toString(), 0, inputSyntax, exeInput.getLstParameterIndex())+"(new ArrayList<"+ dataTypeSrc + ">());";
						strGenExecDetails.append(targetSet);
						strGenExecDetails.append("\n\t\t}");
						strGenExecDetails.append("\n\t\t");
						strGenExecDetails.append(String.format("%s.clear();", targetNull));
						strGenExecDetails.append("\n\t\t");
						String getterParam = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, id, exeInput.getParameterScope(), codeScope, exeInput.getLstParameterIndex());
						
						if(GenerateSourceCodeConst.DataType.BYTE == dataType){
							strGenExecDetails.append(String.format("if (%s != null && %s.length > 0) {", getterParam, getterParam));
						} else {
							strGenExecDetails.append(String.format("if (%s != null && %s.size() > 0) {", getterParam, getterParam));
						}
						
						strGenExecDetails.append("\n\t\t\t");
						strGenExecDetails.append(String.format("for (%s %s : %s) {", GenerateSourceCodeUtil.getPrimitiveTypeName(dataType), "iter", getterParam));
						String paramInput = BusinessLogicGenerateHelper.getContentByCastDataType(sqlInput.getDataType(), dataType, "iter");
						strGenExecDetails.append("\n\t\t\t\t");
						String setterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(1, mAllParentAndSeflByLevelOfInOutObjCustom, IS_GETTER, sqlInput.getSqlDesignInputId().toString(), 0, inputSyntax , exeInput.getLstParameterIndex())+".add("+paramInput+");";
						strGenExecDetails.append(setterInReferParent);
						strGenExecDetails.append("\n\t\t\t");
						strGenExecDetails.append("}");
						strGenExecDetails.append("\n\t\t");
						strGenExecDetails.append("}");
						strGenExecDetails.append("\n\t\t");
					} else {
						getter = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, id, exeInput.getParameterScope(), codeScope , exeInput.getLstParameterIndex());
						getter = BusinessLogicGenerateHelper.getContentByCastDataType(sqlInput.getDataType(), dataType, getter);
						
						if(sqlInput.getArrayFlag().equals(0) && BusinessDesignConst.DataType.BOOLEAN.equals(sqlInput.getDataType())) {
							getter = String.format("(%s == null)? false : %s", getter, getter);
						}
						// setter of child
						String setterChild = detailServiceImpHandler.getterAndSetterOfParameter(1, mAllParentAndSeflByLevelOfInOutObjCustom, IS_SETTER, sqlInput.getSqlDesignInputId().toString(), 0, inputSyntax, null);
						
						strGenExecDetails.append(MessageFormat.format(INPUT_SETTER, setterChild, getter)).append("\n\t\t\t");
					}
				} else if (isParentArray && parentIdOfInput != null && parentIdOfInput.equals(sqlInput.getSqlDesignInputParentId().toString())) {
					List<?> listObjParamOfParent = null;
					Map<String, List<?>> mAllBlogicCurrentTmp = null;
					
					if (BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.equals(exeInput.getParameterScope())) {
						listObjParamOfParent = detailServiceImpHandler.getAllChildByParent(0, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, parentId, param.getBusinessDesign().getLstInputBean());
						mAllBlogicCurrentTmp = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(0, listObjParamOfParent, null, null);
						if(mAllBlogicCurrentTmp == null) continue;
						InputBean inParentParam = mIdInput.get(parentId);
						if (inParentParam == null) continue;
						
						getter = (Boolean.TRUE.equals(inParentParam.getArrayFlg())) 
								? detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrentTmp, IS_GETTER, id, exeInput.getParameterScope(), parentobjDefForGet, null) 
								: detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, id, exeInput.getParameterScope(), codeScope, exeInput.getLstParameterIndex()) ;

					} else if (BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(exeInput.getParameterScope())) {
						listObjParamOfParent = detailServiceImpHandler.getAllChildByParent(0, BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID, parentId, param.getBusinessDesign().getLstObjectDefinition());
						mAllBlogicCurrentTmp = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(0, null, listObjParamOfParent, null);
						if(mAllBlogicCurrentTmp == null) continue;
						ObjectDefinition objParentParam = mIdObjDef.get(parentId);
						if (objParentParam == null) continue;
						
						getter = (Boolean.TRUE.equals(objParentParam.getArrayFlg())) 
								? detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrentTmp, IS_GETTER, id, exeInput.getParameterScope(), parentobjDefForGet, null) 
								: detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, id, exeInput.getParameterScope(), codeScope, exeInput.getLstParameterIndex()) ;
					}

					if (StringUtils.isEmpty(getter)) continue;
					
					// setter of child of parentInputForSet
					List<?> listInputReferOfParent = detailServiceImpHandler.getAllChildByParent(1, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, parentIdOfInput, lstSqlDesignInputs);
					Map<String, List<?>> mAllSqlInputTmp = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(1, listInputReferOfParent, null, null);

					if(isTwooArrayPrimitive) {
						strGenExecDetails.append("\n\t\t\t");
						String targetNull = detailServiceImpHandler.getterAndSetterOfParameter(1, mAllSqlInputTmp, IS_GETTER, sqlInput.getSqlDesignInputId().toString(), 0, parentInputForSet, null);
						strGenExecDetails.append(String.format("if (%s == null) {", targetNull));
						strGenExecDetails.append("\n\t\t\t\t");
						String dataTypeSrc = GenerateSourceCodeUtil.getPrimitiveTypeName(sqlInput.getDataType());
						String targetSet = detailServiceImpHandler.getterAndSetterOfParameter(1, mAllSqlInputTmp, IS_SETTER, sqlInput.getSqlDesignInputId().toString(), 0, parentInputForSet, null)+"(new ArrayList<"+ dataTypeSrc + ">());";
						strGenExecDetails.append(targetSet);
						strGenExecDetails.append("\n\t\t\t}");
						strGenExecDetails.append("\n\t\t\t");
						strGenExecDetails.append(String.format("%s.clear();", targetNull));
						strGenExecDetails.append("\n\t\t\t");
						String getterParam = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrentTmp, IS_GETTER, id, exeInput.getParameterScope(), parentobjDefForGet, null);
						
						if(GenerateSourceCodeConst.DataType.BYTE == dataType){
							strGenExecDetails.append(String.format("if (%s != null && %s.length > 0) {", getterParam, getterParam));
						} else {
							strGenExecDetails.append(String.format("if (%s != null && %s.size() > 0) {", getterParam, getterParam));
						}
						
						strGenExecDetails.append("\n\t\t\t\t");
						strGenExecDetails.append(String.format("for (%s %s : %s) {", GenerateSourceCodeUtil.getPrimitiveTypeName(dataType), "iter", getterParam));
						String paramInput = BusinessLogicGenerateHelper.getContentByCastDataType(sqlInput.getDataType(), dataType, "iter");
						strGenExecDetails.append("\n\t\t\t\t\t");
						String setterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(1, mAllSqlInputTmp, IS_GETTER, sqlInput.getSqlDesignInputId().toString(), 0, parentInputForSet , null)+".add("+paramInput+");";
						strGenExecDetails.append(setterInReferParent);
						strGenExecDetails.append("\n\t\t\t\t");
						strGenExecDetails.append("}");
						strGenExecDetails.append("\n\t\t\t");
						strGenExecDetails.append("}");
						strGenExecDetails.append("\n\t\t\t");
						
						if(countExeInputDetail == lstInputValue.size()){
							strGenExecDetails.append(getterParentInputList + ".add("+parentInputForSet+");").append(String.format("\n\t\t}\n\t\t"));
						}
					} else {
						getter = BusinessLogicGenerateHelper.getContentByCastDataType(sqlInput.getDataType(), dataType, getter);
						
						if(sqlInput.getArrayFlag().equals(0) && BusinessDesignConst.DataType.BOOLEAN.equals(sqlInput.getDataType())) {
							getter = String.format("(%s == null)? false : %s",  getter, getter);
						}
						
						String setterChild = detailServiceImpHandler.getterAndSetterOfParameter(1, mAllSqlInputTmp, IS_SETTER, sqlInput.getSqlDesignInputId().toString(), 0, parentInputForSet, null);
	
						strGenExecDetails.append(MessageFormat.format(INPUT_SETTER, setterChild, getter)).append("\n\t\t\t");
	
						if(countExeInputDetail == lstInputValue.size()){
							strGenExecDetails.append(getterParentInputList + ".add("+parentInputForSet+");").append(String.format("\n\t\t}\n\t\t"));
						}
					}
				}
			}
		}
		
		return strGenExecDetails;
	}

	private String getPackageName(BLogicHandlerIo  paramIO, Object obj) {
		Integer dataType = -1;
		String endPrefix = StringUtils.EMPTY;
		String pakageExternal = StringUtils.EMPTY;
		String moduleCode = StringUtils.EMPTY;
		
		String servicePath = (BusinessDesignConst.BLOGIC_TYPE_STANDARD.equals(paramIO.getBusinessDesign().getBlogicType())?"service.":StringUtils.EMPTY);
		String place = ".domain.";
		if(BusinessDesignConst.MODULE_TYPE_BATCH.equals(paramIO.getModule().getModuleType())){
			servicePath = StringUtils.EMPTY;
			place = ".batch.";
		}
		
		StringBuilder pakage = new StringBuilder(paramIO.getProject().getPackageName());
		
		if (obj instanceof InputBean) {
			InputBean input = (InputBean) obj;
			dataType = input.getDataType();
			pakageExternal = input.getPackageNameObjExt();
			endPrefix = "InputBean.";
			moduleCode = input.getModuleCode();
		} else if(obj instanceof ObjectDefinition) {
			ObjectDefinition od = (ObjectDefinition) obj;
			dataType = od.getDataType();
			pakageExternal = od.getPackageNameObjExt();
			endPrefix = "ObjectDefinition.";
			moduleCode = od.getModuleCode();
		} else if(obj instanceof OutputBean) {
			OutputBean output = (OutputBean) obj;
			dataType = output.getDataType();
			pakageExternal = output.getPackageNameObjExt();
			endPrefix = "OutputBean.";
			moduleCode = output.getModuleCode();
		} else if(obj instanceof SqlDesignInput) {
			SqlDesignInput sqlDesignInput = (SqlDesignInput) obj;
			dataType = sqlDesignInput.getDataType();
			pakageExternal = sqlDesignInput.getPakageExternal();
			endPrefix = "InputBean.";
			moduleCode = sqlDesignInput.getModuleCode();
		} else if(obj instanceof SqlDesignOutput) {
			SqlDesignOutput sqlDesignOutput = (SqlDesignOutput) obj;
			dataType = sqlDesignOutput.getDataType();
			pakageExternal = sqlDesignOutput.getPakageExternal();
			endPrefix = "OutputBean.";
			moduleCode = sqlDesignOutput.getModuleCode();
		}
		
		switch (dataType) {
		// is Object
		case GenerateSourceCodeConst.DataType.OBJECT:
			if(obj instanceof SqlDesignInput || obj instanceof SqlDesignOutput) {
				if(currentComponent.getModuleId() != null) {
					pakage.append(place).append("model.").append(GenerateSourceCodeUtil.normalizedMethodName(paramIO.getModule().getModuleCode())).append(".").append(currentComponent.getSqlDesignCode()).append(endPrefix);
				} else {
					pakage.append(place).append("model.common.").append(currentComponent.getSqlDesignCode()).append(endPrefix);
				}
			} else {
				pakage.append(place).append(servicePath).append(GenerateSourceCodeUtil.normalizedMethodName(paramIO.getModule().getModuleCode()))
					.append(".").append(GenerateSourceCodeUtil.normalizedMethodName(paramIO.getBusinessDesign().getBusinessLogicCode())).append(endPrefix);
			}
			break;
		// is Entity
		case GenerateSourceCodeConst.DataType.ENTITY:
			pakage.append(place).append("model.");
			break;
		// is Common Object
		case GenerateSourceCodeConst.DataType.COMMON_OBJECT:
			pakage.append(place).append("commonobject.");
			if(StringUtils.isNotEmpty(moduleCode)){
				pakage.append(moduleCode).append(".");
			}
			break;
		// is External Object
		case GenerateSourceCodeConst.DataType.EXTERNAL_OBJECT:
			pakage = new StringBuilder();
			pakage.append(pakageExternal).append(".");
			break;
		}

		return GenerateSourceCodeUtil.normalizedPackageName(pakage.toString());

	}

	@Override
	public void preGencode(StringBuilder builder, BLogicHandlerIo param) {
		if (param.getIsGenConfig()) {
			builder.append(KEY_NL);
			builder.append("// Start execution node");
			builder.append(KEY_NL);
			
			if(this.currentComponent !=null) {
				builder.append("// Label:" + currentComponent.getLabel());
				builder.append(KEY_NL);
				
				String remark = currentComponent.getRemark();
				if (StringUtils.isNotEmpty(remark)) {
					if (org.springframework.util.StringUtils.countOccurrencesOf(remark, "\n") > 0) {
						remark = remark.replace("\n", KEY_NL);
						builder.append(BusinessDesignConst.MULTI_COMMENT_START).append(KEY_NL).append(remark).append(KEY_NL).append(BusinessDesignConst.MULTI_COMMENT_END).append(KEY_NL);
					} else {
						builder.append(BusinessDesignConst.SINGLE_COMMENT_START).append(SPACE).append(remark).append(KEY_NL);
					}
				}
			}
		}
	}

	@Override
	public void postGencode(StringBuilder builder, BLogicHandlerIo param) {
		if (param.getIsGenConfig()) {
			builder.append(KEY_NL);
			builder.append("// End execution node");
			builder.append(KEY_NL);
		}
	}
}
