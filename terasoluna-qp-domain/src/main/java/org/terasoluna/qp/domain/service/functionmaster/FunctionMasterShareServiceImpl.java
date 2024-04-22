package org.terasoluna.qp.domain.service.functionmaster;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.BusinessDesignMessageConst;
import org.terasoluna.qp.app.message.ProblemListMessageConst;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.DecisionTable;
import org.terasoluna.qp.domain.model.DecisionTableConditionItem;
import org.terasoluna.qp.domain.model.FormulaDetail;
import org.terasoluna.qp.domain.model.FormulaMethodInput;
import org.terasoluna.qp.domain.model.FormulaMethodOutput;
import org.terasoluna.qp.domain.model.FunctionMaster;
import org.terasoluna.qp.domain.model.FunctionMethod;
import org.terasoluna.qp.domain.model.FunctionMethodInput;
import org.terasoluna.qp.domain.model.FunctionMethodOutput;
import org.terasoluna.qp.domain.model.ProblemList;
import org.terasoluna.qp.domain.model.SequenceLogic;
import org.terasoluna.qp.domain.repository.functionmaster.FunctionMasterRepository;
import org.terasoluna.qp.domain.repository.impactchange.ImpactFunctionMasterRepository;
import org.terasoluna.qp.domain.repository.problemlist.ProblemListRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;

@Service
public class FunctionMasterShareServiceImpl implements FunctionMasterShareService {

	@Inject
	ProblemListRepository problemListRepository;
	
	@Inject
	ImpactFunctionMasterRepository impactFunctionMasterRepository;
	
	@Inject
	FunctionMasterRepository functionMasterRepository;
	
	@Inject
	FunctionMasterService functionMasterService;
	
	@Override
	public void detectListAffectedWhenModifyMethodOfBatch(Long functionMethodId, CommonModel common) {
		FunctionMethod method = impactFunctionMasterRepository.findOneFuntionMethodById(functionMethodId);
		method.setFunctionMethodInput(functionMasterRepository.findFunctionMethodInputByFunctionMethodId(functionMethodId));
		method.setFunctionMethodOutput(functionMasterRepository.findFunctionMethodOutputByFunctionMethodId(functionMethodId));
		List<FunctionMethod> lstMethods = new ArrayList<FunctionMethod>();
		lstMethods.add(method);
		detectListAffectedWhenModifyMethod(lstMethods,method.getFunctionMasterCode(),common,true);
	}

	@Override
	public ImpactChangeOfFunctionMaster detectListAffectedWhenModifyMethod(List<FunctionMethod> lstFunctionMethods, String functionMasterCode, CommonModel common, Boolean isRunBatch) {
		ImpactChangeOfFunctionMaster impact = new ImpactChangeOfFunctionMaster();
		List<BusinessDesign> lstUsedBusinessDesigns = new ArrayList<BusinessDesign>();
		List<DecisionTable> lstUsedDecisionTables = new ArrayList<DecisionTable>();
		ProblemList problemList = new ProblemList();
		List<ProblemList> lsProblemLists = new ArrayList<ProblemList>();
		Timestamp systemDate = common.getCreatedDate();
		List<Long> lstFroms = new ArrayList<Long>();
		
		if(CollectionUtils.isNotEmpty(lstFunctionMethods)){
			List<FormulaDetail> lstUsedFormulaDetails = impactFunctionMasterRepository.findFormulaDetailsByLstFunctionMethods(lstFunctionMethods);
			List<FormulaMethodInput> lstUsedMethodInputs = impactFunctionMasterRepository.findMethodInputsByLstFunctionMethods(lstFunctionMethods);
			List<FormulaMethodOutput> lstUsedMethodOutputs = impactFunctionMasterRepository.findMethodOutputsByLstFunctionMethods(lstFunctionMethods);
			//mapping data
			if(CollectionUtils.isNotEmpty(lstUsedFormulaDetails)){
				Map<FormulaDetail,FunctionMethod> mapFormulaDetails = new HashMap<FormulaDetail, FunctionMethod>();
				for (FormulaDetail detail : lstUsedFormulaDetails) {
					List<FormulaMethodInput> lstCurrentInput = new ArrayList<FormulaMethodInput>();
					if(CollectionUtils.isNotEmpty(lstUsedMethodInputs)){
						for (FormulaMethodInput input : lstUsedMethodInputs){
							if(FunctionCommon.equals(detail.getFormulaDetailId(), input.getFormulaDetailId())){
								lstCurrentInput.add(input);
							}
						}
					}
					detail.setFormulaMethodInputs(lstCurrentInput);
					
					List<FormulaMethodOutput> lstCurrentOutput = new ArrayList<FormulaMethodOutput>();
					if(CollectionUtils.isNotEmpty(lstUsedMethodOutputs)){
						for (FormulaMethodOutput output : lstUsedMethodOutputs){
							if(FunctionCommon.equals(detail.getFormulaDetailId(), output.getFormulaDetailId())){
								lstCurrentOutput.add(output);
							}
						}
					}
					detail.setFormulaMethodOutputs(lstCurrentOutput);
					
					for (FunctionMethod newMethod : lstFunctionMethods) {
						if(FunctionCommon.equals(detail.getFunctionMethodId(), newMethod.getFunctionMethodId())){
							mapFormulaDetails.put(detail, newMethod);
							break;
						}
					}
					
                }
				
				//impact of business logic
				List<SequenceLogic> lstUsedSequenceLogics = impactFunctionMasterRepository.findSequenceLogicAndFormulaDetailByUsingLstFunctionMethod(lstFunctionMethods);
				if(CollectionUtils.isNotEmpty(lstUsedSequenceLogics)){
					for (SequenceLogic currentSequence : lstUsedSequenceLogics) {
						FormulaDetail currentFormula = null;
						for (FormulaDetail detail : lstUsedFormulaDetails) {
	                        if(FunctionCommon.equals(detail.getFormulaDetailId(), currentSequence.getFormulaDetailId())){
	                        	currentFormula = detail;
	                        	break;
	                        }
                        }
						if(currentFormula != null){
							FunctionMethod currentFMethod = mapFormulaDetails.getOrDefault(currentFormula, null);
							if(currentFMethod != null){
								//compare formula detail with current method
								//compare input
								if(CollectionUtils.isNotEmpty(currentFMethod.getFunctionMethodInput())){
									for (FunctionMethodInput in : currentFMethod.getFunctionMethodInput()) {
				                        boolean isExist = false;
				                        FormulaMethodInput currentInput = new FormulaMethodInput();
				                        for (FormulaMethodInput current : currentFormula.getFormulaMethodInputs()) {
				                        	if(FunctionCommon.equals(in.getMethodInputId(), current.getMethodInputId())){
				                        		isExist = true;
				                        		currentInput = current;
				                        		break;
				                        	}
				                        }
				                        if(isExist){
				                        	if(!FunctionCommon.compare(in.getArrayFlg(), currentInput.getArrayFlg())||FunctionCommon.notEquals(in.getDataType(), currentInput.getDataType())){
				                        		problemList = new ProblemList();
					                        	String problemName = MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0135,in.getMethodInputCode(),currentFMethod.getFunctionMethodCode(),functionMasterCode,getComponentName(currentSequence.getComponentType()),StringUtils.trimToEmpty(currentSequence.getSequenceLogicName()),currentSequence.getBusinessLogicCode());
					    						problemList.setProblemName(problemName);
					    						problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
					    						problemList.setResourceId(currentSequence.getBusinessLogicId());
					    						problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
					    						problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
					    						problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
					    						problemList.setProjectId(common.getWorkingProjectId());
					    						problemList.setModuleId(currentSequence.getModuleId());
					    						problemList.setCreatedBy(common.getCreatedBy());	
					    						problemList.setCreatedDate(systemDate);
					    						problemList.setFromResourceType(DbDomainConst.FromResourceType.FUNCTION_METHOD);
					    						problemList.setFromResourceId(currentFMethod.getFunctionMethodId());
					    						addProblemList(lsProblemLists,problemList);
				                        	}
				                        }else{
				                        	problemList = new ProblemList();
				                        	String problemName = MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0134,in.getMethodInputCode(),currentFMethod.getFunctionMethodCode(),functionMasterCode,getComponentName(currentSequence.getComponentType()),StringUtils.trimToEmpty(currentSequence.getSequenceLogicName()),currentSequence.getBusinessLogicCode());
				    						problemList.setProblemName(problemName);
				    						problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
				    						problemList.setResourceId(currentSequence.getBusinessLogicId());
				    						problemList.setProblemType(DbDomainConst.ProblemType.MISS_SETTING);
				    						problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
				    						problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
				    						problemList.setProjectId(common.getWorkingProjectId());
				    						problemList.setModuleId(currentSequence.getModuleId());
				    						problemList.setCreatedBy(common.getCreatedBy());	
				    						problemList.setCreatedDate(systemDate);
				    						problemList.setFromResourceType(DbDomainConst.FromResourceType.FUNCTION_METHOD);
				    						problemList.setFromResourceId(currentFMethod.getFunctionMethodId());
				    						addProblemList(lsProblemLists,problemList);
				                        }
			                        }
								}
								//compare output
								if(CollectionUtils.isNotEmpty(currentFormula.getFormulaMethodOutputs())){
									for (FormulaMethodOutput out : currentFormula.getFormulaMethodOutputs()) {
				                        boolean isExist = false;
				                        FunctionMethodOutput currentOutput = new FunctionMethodOutput();
				                        for (FunctionMethodOutput current : currentFMethod.getFunctionMethodOutput()) {
				                        	if(FunctionCommon.equals(out.getMethodOutputId(), current.getMethodOutputId())){
				                        		isExist = true;
				                        		currentOutput = current;
				                        		break;
				                        	}
				                        }
				                        if(isExist){
				                        	if(!FunctionCommon.compare(currentOutput.getArrayFlg(),out.getArrayFlg())||FunctionCommon.notEquals(out.getDataType(), currentOutput.getDataType())){
				                        		problemList = new ProblemList();
					                        	String problemName = MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0136,currentOutput.getMethodOutputCode(), currentFMethod.getFunctionMethodCode(),functionMasterCode,getComponentName(currentSequence.getComponentType()),StringUtils.trimToEmpty(currentSequence.getSequenceLogicName()),currentSequence.getBusinessLogicCode());
					    						problemList.setProblemName(problemName);
					    						problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
					    						problemList.setResourceId(currentSequence.getBusinessLogicId());
					    						problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
					    						problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
					    						problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
					    						problemList.setProjectId(common.getWorkingProjectId());
					    						problemList.setModuleId(currentSequence.getModuleId());
					    						problemList.setCreatedBy(common.getCreatedBy());	
					    						problemList.setCreatedDate(systemDate);
					    						problemList.setFromResourceType(DbDomainConst.FromResourceType.FUNCTION_METHOD);
					    						problemList.setFromResourceId(currentFMethod.getFunctionMethodId());
					    						addProblemList(lsProblemLists,problemList);
				                        	}
				                        }else{
				                        	problemList = new ProblemList();
				                        	String problemName = MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0137,out.getMethodOutputCode(), currentFMethod.getFunctionMethodCode(),functionMasterCode,getComponentName(currentSequence.getComponentType()),StringUtils.trimToEmpty(currentSequence.getSequenceLogicName()),currentSequence.getBusinessLogicCode());
				    						problemList.setProblemName(problemName);
				    						problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
				    						problemList.setResourceId(currentSequence.getBusinessLogicId());
				    						problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				    						problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
				    						problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
				    						problemList.setProjectId(common.getWorkingProjectId());
				    						problemList.setModuleId(currentSequence.getModuleId());
				    						problemList.setCreatedBy(common.getCreatedBy());	
				    						problemList.setCreatedDate(systemDate);
				    						problemList.setFromResourceType(DbDomainConst.FromResourceType.FUNCTION_METHOD);
				    						problemList.setFromResourceId(currentFMethod.getFunctionMethodId());
				    						addProblemList(lsProblemLists,problemList);
				                        }
			                        }
								}
							}
						}
					}
                }
				
				//impact of decision table
				List<DecisionTableConditionItem> lstUsedConditionItems = impactFunctionMasterRepository.findConditionItemByByUsingLstFunctionMethod(lstFunctionMethods);
				for (DecisionTableConditionItem item : lstUsedConditionItems) {
					FormulaDetail currentFormula = null;
					for (FormulaDetail detail : lstUsedFormulaDetails) {
                        if(FunctionCommon.equals(detail.getFormulaDefinitionId(), item.getFormulaDefinitionId())){
                        	currentFormula = detail;
                        	break;
                        }
                    }
					if(currentFormula != null){
						FunctionMethod currentFMethod = mapFormulaDetails.getOrDefault(currentFormula, null);
						if(currentFMethod != null){
							//compare formula detail with current method
							//compare input
							if(CollectionUtils.isNotEmpty(currentFMethod.getFunctionMethodInput())){
								for (FunctionMethodInput in : currentFMethod.getFunctionMethodInput()) {
			                        boolean isExist = false;
			                        FormulaMethodInput currentInput = new FormulaMethodInput();
			                        for (FormulaMethodInput current : currentFormula.getFormulaMethodInputs()) {
			                        	if(FunctionCommon.equals(in.getMethodInputId(), current.getMethodInputId())){
			                        		isExist = true;
			                        		currentInput = current;
			                        		break;
			                        	}
			                        }
			                        if(isExist){
			                        	if(!FunctionCommon.compare(in.getArrayFlg(), currentInput.getArrayFlg())||FunctionCommon.notEquals(in.getDataType(), currentInput.getDataType())){
			                        		problemList = new ProblemList();
				                        	String problemName = MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0141,in.getMethodInputCode(),currentFMethod.getFunctionMethodCode(),functionMasterCode,item.getDecisionTbCode());
				    						problemList.setProblemName(problemName);
				    						problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
				    						problemList.setResourceId(item.getDecisionTbId());
				    						problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				    						problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
				    						problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
				    						problemList.setProjectId(common.getWorkingProjectId());
				    						problemList.setModuleId(item.getModuleId());
				    						problemList.setCreatedBy(common.getCreatedBy());	
				    						problemList.setCreatedDate(systemDate);
				    						problemList.setFromResourceType(DbDomainConst.FromResourceType.FUNCTION_METHOD);
				    						problemList.setFromResourceId(currentFMethod.getFunctionMethodId());
				    						addProblemList(lsProblemLists,problemList);
			                        	}
			                        }else{
			                        	problemList = new ProblemList();
			                        	String problemName = MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0140,in.getMethodInputCode(),currentFMethod.getFunctionMethodCode(),functionMasterCode,item.getDecisionTbCode());
			    						problemList.setProblemName(problemName);
			    						problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
			    						problemList.setResourceId(item.getDecisionTbId());
			    						problemList.setProblemType(DbDomainConst.ProblemType.MISS_SETTING);
			    						problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
			    						problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
			    						problemList.setProjectId(common.getWorkingProjectId());
			    						problemList.setModuleId(item.getModuleId());
			    						problemList.setCreatedBy(common.getCreatedBy());	
			    						problemList.setCreatedDate(systemDate);
			    						problemList.setFromResourceType(DbDomainConst.FromResourceType.FUNCTION_METHOD);
			    						problemList.setFromResourceId(currentFMethod.getFunctionMethodId());
			    						addProblemList(lsProblemLists,problemList);
			                        }
		                        }
							}
							//compare output
							if(CollectionUtils.isNotEmpty(currentFormula.getFormulaMethodOutputs())){
								for (FormulaMethodOutput out : currentFormula.getFormulaMethodOutputs()) {
			                        boolean isExist = false;
			                        FunctionMethodOutput currentOutput = new FunctionMethodOutput();
			                        for (FunctionMethodOutput current : currentFMethod.getFunctionMethodOutput()) {
			                        	if(FunctionCommon.equals(out.getMethodOutputId(), current.getMethodOutputId())){
			                        		isExist = true;
			                        		currentOutput = current;
			                        		break;
			                        	}
			                        }
			                        if(isExist){
			                        	if(!FunctionCommon.compare(currentOutput.getArrayFlg(),out.getArrayFlg())||FunctionCommon.notEquals(out.getDataType(), currentOutput.getDataType())){
			                        		problemList = new ProblemList();
				                        	String problemName = MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0142,currentOutput.getMethodOutputCode(), currentFMethod.getFunctionMethodCode(),functionMasterCode,item.getDecisionTbCode());
				    						problemList.setProblemName(problemName);
				    						problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
				    						problemList.setResourceId(item.getDecisionTbId());
				    						problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				    						problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
				    						problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
				    						problemList.setProjectId(common.getWorkingProjectId());
				    						problemList.setModuleId(item.getModuleId());
				    						problemList.setCreatedBy(common.getCreatedBy());	
				    						problemList.setCreatedDate(systemDate);
				    						problemList.setFromResourceType(DbDomainConst.FromResourceType.FUNCTION_METHOD);
				    						problemList.setFromResourceId(currentFMethod.getFunctionMethodId());
				    						addProblemList(lsProblemLists,problemList);
			                        	}
			                        }else{
			                        	problemList = new ProblemList();
			                        	String problemName = MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0143,out.getMethodOutputCode(), currentFMethod.getFunctionMethodCode(),functionMasterCode,item.getDecisionTbCode());
			    						problemList.setProblemName(problemName);
			    						problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
			    						problemList.setResourceId(item.getDecisionTbId());
			    						problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
			    						problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
			    						problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
			    						problemList.setProjectId(common.getWorkingProjectId());
			    						problemList.setModuleId(item.getModuleId());
			    						problemList.setCreatedBy(common.getCreatedBy());	
			    						problemList.setCreatedDate(systemDate);
			    						problemList.setFromResourceType(DbDomainConst.FromResourceType.FUNCTION_METHOD);
			    						problemList.setFromResourceId(currentFMethod.getFunctionMethodId());
			    						addProblemList(lsProblemLists,problemList);
			                        }
		                        }
							}
						}
					}
                }
				
			}
			List<ProblemList> lstProblemOfBD = new ArrayList<ProblemList>();
			List<ProblemList> lstProblemOfDecisionTable = new ArrayList<ProblemList>();
			for (ProblemList problem : lsProblemLists) {
				if(problem.equals(DbDomainConst.ResourceType.BLOGIC)){
					lstProblemOfBD.add(problem);
				}else if(problem.equals(DbDomainConst.ResourceType.DECISION_TABLE)){
					lstProblemOfDecisionTable.add(problem);
				}
            }
			if(CollectionUtils.isNotEmpty(lstProblemOfBD))
				lstUsedBusinessDesigns = impactFunctionMasterRepository.findBusinessDesignByLstProblemLists(lstProblemOfBD);
			if(CollectionUtils.isNotEmpty(lstProblemOfDecisionTable))
				lstUsedDecisionTables = impactFunctionMasterRepository.findDecisionTableByLstProblemLists(lstProblemOfDecisionTable);
			if(CollectionUtils.isNotEmpty(lstUsedBusinessDesigns) || CollectionUtils.isNotEmpty(lstUsedDecisionTables)){
				impact.setImpactFlag(true);
			}
			impact.setLstUsedBusinessDesign(lstUsedBusinessDesigns);
			impact.setLstUsedDecisionTable(lstUsedDecisionTables);
			if(isRunBatch){
				//delete old problem of this business
				for (FunctionMethod method : lstFunctionMethods) {
	                lstFroms.add(method.getFunctionMethodId());
                }
				problemListRepository.deleteResourceType(DbDomainConst.FromResourceType.FUNCTION_METHOD, lstFroms);
				if(CollectionUtils.isNotEmpty(lsProblemLists)){
					problemListRepository.multiRegisterProblem(lsProblemLists);
				}
			}
		}
		
		return impact;
	}
	
	private void addProblemList(List<ProblemList> lsProblemLists,ProblemList problemList){
		if(lsProblemLists != null){
			boolean duplicateFlg = false;
			for (ProblemList current : lsProblemLists) {
	            if(FunctionCommon.equals(current.getProblemName(), problemList.getProblemName()) && FunctionCommon.equals(current.getResourceType(), problemList.getResourceType()) && FunctionCommon.equals(current.getResourceId(), problemList.getResourceId())){
	            	duplicateFlg = true;
	            }
            }
			if(!duplicateFlg){
				lsProblemLists.add(problemList);
			}
		}
	}

	@Override
	public void detectListAffectedWhenDeleteMethodOfBatch(Long functionMethodId, String functionMethodCode,String functionMasterCode, CommonModel common) {
		FunctionMethod method = new FunctionMethod();
		method.setFunctionMethodId(functionMethodId);
		method.setFunctionMethodCode(functionMethodCode);
		List<FunctionMethod> lstMethods = new ArrayList<FunctionMethod>();
		lstMethods.add(method);
		this.detectListAffectedWhenDeleteMethod(lstMethods,functionMasterCode, common, true);

	}

	@Override
	public ImpactChangeOfFunctionMaster detectListAffectedWhenDeleteMethod(List<FunctionMethod> lstFunctionMethods,String functionMasterCode, CommonModel common, Boolean isRunBatch) {
		ImpactChangeOfFunctionMaster impact = new ImpactChangeOfFunctionMaster();
		List<SequenceLogic> lstUsedSequenceLogics = new ArrayList<SequenceLogic>();
		List<BusinessDesign> lstUsedBusinessDesigns = new ArrayList<BusinessDesign>();
		Map<Long,Boolean> mapUsedBusinessDesigns = new HashMap<Long,Boolean>();
		List<DecisionTable> lstUsedDecisionTables = new ArrayList<DecisionTable>();
		ProblemList problemList = new ProblemList();
		List<ProblemList> lsProblemLists = new ArrayList<ProblemList>();
		Timestamp systemDate = common.getCreatedDate();
		
		List<Long> lstFroms = new ArrayList<Long>();
		if(CollectionUtils.isNotEmpty(lstFunctionMethods)){
			for (FunctionMethod functionMethod : lstFunctionMethods) {
				lstFroms.add(functionMethod.getFunctionMethodId());
				lstUsedSequenceLogics = impactFunctionMasterRepository.findSequenceLogicByUsingFunctionMethod(functionMethod.getFunctionMethodId());
				if(CollectionUtils.isNotEmpty(lstUsedSequenceLogics)){
					for (SequenceLogic sequenceLogic : lstUsedSequenceLogics) {
						problemList = new ProblemList();
		            	String problemName = MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0138,functionMethod.getFunctionMethodCode(),functionMasterCode,getComponentName(sequenceLogic.getComponentType()),StringUtils.trimToEmpty(sequenceLogic.getSequenceLogicName()),sequenceLogic.getBusinessLogicCode());
						problemList.setProblemName(problemName);
						problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
						problemList.setResourceId(sequenceLogic.getBusinessLogicId());
						problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
						problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
						problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
						problemList.setProjectId(common.getWorkingProjectId());
						problemList.setModuleId(sequenceLogic.getModuleId());
						problemList.setCreatedBy(common.getCreatedBy());	
						problemList.setCreatedDate(systemDate);
						problemList.setFromResourceType(DbDomainConst.FromResourceType.FUNCTION_METHOD);
						problemList.setFromResourceId(functionMethod.getFunctionMethodId());
						lsProblemLists.add(problemList);
						
						if(!mapUsedBusinessDesigns.containsKey(sequenceLogic.getBusinessLogicId())){
							mapUsedBusinessDesigns.put(sequenceLogic.getBusinessLogicId(), true);
							BusinessDesign blogic = new BusinessDesign();
							blogic.setBusinessLogicId(sequenceLogic.getBusinessLogicId());
							blogic.setBusinessLogicCode(sequenceLogic.getBusinessLogicCode());
							blogic.setBusinessLogicName(sequenceLogic.getBusinessLogicName());
							blogic.setModuleName(sequenceLogic.getModuleName());
							lstUsedBusinessDesigns.add(blogic);
						}
		            }
				}
				lstUsedDecisionTables =  impactFunctionMasterRepository.findDecisionTableByUsingFunctionMethod(lstFroms);
				if(CollectionUtils.isNotEmpty(lstUsedDecisionTables)){
		            for (DecisionTable decisionTable : lstUsedDecisionTables) {
		            	problemList = new ProblemList();
		            	String problemName = MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0144,functionMethod.getFunctionMethodCode(),functionMasterCode, decisionTable.getDecisionTbCode());
						problemList.setProblemName(problemName);
						problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
						problemList.setResourceId(decisionTable.getDecisionTbId());
						problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
						problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
						problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
						problemList.setProjectId(decisionTable.getProjectId());
						problemList.setModuleId(decisionTable.getModuleId());
						problemList.setCreatedBy(common.getCreatedBy());	
						problemList.setCreatedDate(systemDate);
						problemList.setFromResourceType(DbDomainConst.FromResourceType.FUNCTION_METHOD);
						problemList.setFromResourceId(functionMethod.getFunctionMethodId());
						lsProblemLists.add(problemList);
		            }
				}
            }
			if(CollectionUtils.isNotEmpty(lstUsedBusinessDesigns) || CollectionUtils.isNotEmpty(lstUsedDecisionTables)){
				impact.setImpactFlag(true);
			}
			impact.setLstUsedBusinessDesign(lstUsedBusinessDesigns);
			impact.setLstUsedDecisionTable(lstUsedDecisionTables);
			if(isRunBatch){
				//delete old problem of this business
				
				problemListRepository.deleteResourceType(DbDomainConst.FromResourceType.FUNCTION_METHOD, lstFroms);
				if(CollectionUtils.isNotEmpty(lsProblemLists)){
					problemListRepository.multiRegisterProblem(lsProblemLists);
				}
			}
		}
		return impact;
	}

	private String getComponentName(Integer componentType) {
		String name = "";
		switch (componentType) {
			case BusinessDesignConst.COMPONENT_ASSIGN:
				name = MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0008);
				break;
			case BusinessDesignConst.COMPONENT_BUSINESSCHECK:
				name = MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0004);
				break;
			case BusinessDesignConst.COMPONENT_LOOP:
				name = MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0010);
				break;
			case BusinessDesignConst.COMPONENT_IF:
				name = MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0009);
				break;
			case BusinessDesignConst.COMPONENT_EMAIL:
				name = MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0143);
				break;
			case BusinessDesignConst.COMPONENT_LOG:
				name = MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0144);
				break;
			case BusinessDesignConst.COMPONENT_DOWNLOAD_FILE:
				name = MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0162);
				break;
			case BusinessDesignConst.COMPONENT_FILEOPERATION:
				name = MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0125);
				break;
			case BusinessDesignConst.COMPONENT_EXPORTFILE:
				name = MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0026);
				break;
			case BusinessDesignConst.COMPONENT_IMPORTFILE:
				name = MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0081);
				break;
			default:
				break;
		}
		return name;
	}

	@Override
    public ImpactChangeOfFunctionMaster detectListAffectedWhenDeleteMaster(FunctionMaster functionMaster, CommonModel common, Boolean isRunBatch) {
		functionMaster = functionMasterRepository.findOneFuntionMasterById(functionMaster.getFunctionMasterId());
		List<FunctionMethod> lstMethods = functionMasterRepository.findFuntionMethodByFunctionMasterId(functionMaster.getFunctionMasterId());
		ImpactChangeOfFunctionMaster impact =  this.detectListAffectedWhenDeleteMethod(lstMethods,functionMaster.getFunctionMasterCode(), common, isRunBatch);
		return impact;
    }

	@Override
    public ImpactChangeOfFunctionMaster detectListAffectedWhenModifyMaster(FunctionMaster newMaster, CommonModel common, Boolean isRunBatch) {
		FunctionMaster oldMaster =  functionMasterService.loadFunctionMaster(newMaster.getFunctionMasterId());
		List<FunctionMethod> lstDeletedFunctionMethods = new ArrayList<FunctionMethod>();
		List<FunctionMethod> lstModifiedFunctionMethods = new ArrayList<FunctionMethod>();
		if(CollectionUtils.isNotEmpty(oldMaster.getFunctionMethod())){
			FunctionMethod mapMethod = new FunctionMethod();
			boolean isDelete = true;
			for (FunctionMethod method : oldMaster.getFunctionMethod()) {
	            isDelete = true;
	            mapMethod = new FunctionMethod();
	            if(CollectionUtils.isNotEmpty(newMaster.getFunctionMethod())){
	            	for (FunctionMethod newMethod : newMaster.getFunctionMethod()) {
	            		if(FunctionCommon.equals(method.getFunctionMethodId(), newMethod.getFunctionMethodId())){
	            			isDelete = false;
	            			mapMethod = newMethod;
	            			break;
	            		}
	            	}
	            }
	            if(isDelete){
	            	lstDeletedFunctionMethods.add(method);
	            }else{
	            	lstModifiedFunctionMethods.add(mapMethod);
	            }
            }
		}
		ImpactChangeOfFunctionMaster impactOfDeleteMethod = this.detectListAffectedWhenDeleteMethod(lstDeletedFunctionMethods, newMaster.getFunctionMasterCode(), common, isRunBatch);
		ImpactChangeOfFunctionMaster impactOfModifyMethod = this.detectListAffectedWhenModifyMethod(lstModifiedFunctionMethods, newMaster.getFunctionMasterCode(), common, isRunBatch);
		ImpactChangeOfFunctionMaster impact = new ImpactChangeOfFunctionMaster();
		//merge
		List<BusinessDesign> lstUsedBusinessDesigns = new ArrayList<BusinessDesign>();
		Map<Long,Boolean> mapBLogics = new HashMap<Long, Boolean>();
		List<DecisionTable> lstUsedDecisionTables = new ArrayList<DecisionTable>();
		Map<Long,Boolean> mapDecisions = new HashMap<Long, Boolean>();
		if(CollectionUtils.isNotEmpty(impactOfModifyMethod.getLstUsedBusinessDesign())){
			for (BusinessDesign blogic : impactOfModifyMethod.getLstUsedBusinessDesign()) {
				if(!mapBLogics.containsKey(blogic.getBusinessLogicId())){
	            	lstUsedBusinessDesigns.add(blogic);
	            	mapBLogics.put(blogic.getBusinessLogicId(), true);
	            }
            }
		}
		if(CollectionUtils.isNotEmpty(impactOfDeleteMethod.getLstUsedBusinessDesign())){
			for (BusinessDesign blogic : impactOfDeleteMethod.getLstUsedBusinessDesign()) {
	            if(!mapBLogics.containsKey(blogic.getBusinessLogicId())){
	            	lstUsedBusinessDesigns.add(blogic);
	            	mapBLogics.put(blogic.getBusinessLogicId(), true);
	            }
            }
		}
		
		if(CollectionUtils.isNotEmpty(impactOfModifyMethod.getLstUsedDecisionTable())){
			for (DecisionTable table : impactOfModifyMethod.getLstUsedDecisionTable()) {
				if(!mapBLogics.containsKey(table.getDecisionTbId())){
					lstUsedDecisionTables.add(table);
					mapDecisions.put(table.getDecisionTbId(), true);
	            }
            }
		}
		if(CollectionUtils.isNotEmpty(impactOfDeleteMethod.getLstUsedDecisionTable())){
			for (DecisionTable table : impactOfDeleteMethod.getLstUsedDecisionTable()) {
				if(!mapBLogics.containsKey(table.getDecisionTbId())){
					lstUsedDecisionTables.add(table);
					mapDecisions.put(table.getDecisionTbId(), true);
	            }
            }
		}
		
		if(CollectionUtils.isNotEmpty(lstUsedBusinessDesigns) || CollectionUtils.isNotEmpty(lstUsedDecisionTables)){
			impact.setImpactFlag(true);
		}
		impact.setLstUsedBusinessDesign(lstUsedBusinessDesigns);
		impact.setLstUsedDecisionTable(lstUsedDecisionTables);
		return impact;
    }
}
