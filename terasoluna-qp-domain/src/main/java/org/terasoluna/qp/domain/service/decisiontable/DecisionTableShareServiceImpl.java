package org.terasoluna.qp.domain.service.decisiontable;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.ProblemListMessageConst;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.DecisionComponent;
import org.terasoluna.qp.domain.model.DecisionInputValue;
import org.terasoluna.qp.domain.model.DecisionOutputValue;
import org.terasoluna.qp.domain.model.DecisionTable;
import org.terasoluna.qp.domain.model.DecisionTableInputBean;
import org.terasoluna.qp.domain.model.DecisionTableOutputBean;
import org.terasoluna.qp.domain.model.ProblemList;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.DecisionComponentRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableInputBeanRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableOutputBeanRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableRepository;
import org.terasoluna.qp.domain.repository.problemlist.ProblemListRepository;

@Service
public class DecisionTableShareServiceImpl implements DecisionTableShareService {

	@Inject
	DecisionTableRepository decisionTableRepository; 
	
	@Inject
	DecisionTableInputBeanRepository decisionTableInputBeanRepository; 
	
	@Inject
	DecisionTableOutputBeanRepository decisionTableOutputBeanRepository; 
	
	@Inject
	DecisionComponentRepository decisionComponentRepository;
	
	@Inject
	DecisionTableService decisionTableService; 
	
	@Inject
	ProblemListRepository problemListRepository ;
	
	@Override
	public ImpactChangeOfDecisionTable detectListAffectedWhenModify(DecisionTable decisionTable,CommonModel common,Boolean isRunBatch) {
		// Get object parsing form json string
		List<Object> arrObj = DecisionTableUtils.getObjectParseFromJsons(decisionTable);
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		
		ImpactChangeOfDecisionTable impact = new ImpactChangeOfDecisionTable();
		
		@SuppressWarnings("unchecked")
		List<DecisionTableInputBean> inputBeans = (List<DecisionTableInputBean>) arrObj.get(0);
		@SuppressWarnings("unchecked")
		List<DecisionTableOutputBean> outputBeans = (List<DecisionTableOutputBean>) arrObj.get(1);
		
		List<BusinessDesign> lstUsedBusinessDesign = new ArrayList<BusinessDesign>();
		List<BusinessDesign> lstTemps = new ArrayList<BusinessDesign>();
		Map<Long,BusinessDesign> mapComponentOfBlogic = new HashMap<Long,BusinessDesign>();
		
		//detect impact change design
		List<DecisionComponent> lstDecisionComponents = new ArrayList<DecisionComponent>();
		List<DecisionInputValue> lstDecisionInputValues = new ArrayList<DecisionInputValue>();
		List<DecisionOutputValue> lstDecisionOutputValues = new ArrayList<DecisionOutputValue>();
		lstDecisionComponents = decisionComponentRepository.findDecisionComponentByDecisionTbId(decisionTable.getDecisionTbId());
		ProblemList problemList = new ProblemList();
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		if(CollectionUtils.isNotEmpty(lstDecisionComponents)){
			lstUsedBusinessDesign = decisionTableRepository.findAllBussinessLogicByDecisionId(decisionTable.getDecisionTbId());
			lstDecisionInputValues = decisionComponentRepository.findDecisionInputValueByDecisionTbId(decisionTable.getDecisionTbId());
			lstDecisionOutputValues = decisionComponentRepository.findDecisionOutputValueByDecisionTbId(decisionTable.getDecisionTbId());
			//mapping
			List<DecisionInputValue> lstCurrentInputValues = new ArrayList<DecisionInputValue>();
			List<DecisionOutputValue> lstCurrentOutputValues = new ArrayList<DecisionOutputValue>();
			for (DecisionComponent component : lstDecisionComponents) {
            	lstCurrentInputValues = new ArrayList<DecisionInputValue>();
	            if(CollectionUtils.isNotEmpty(lstDecisionInputValues)){
	            	for (DecisionInputValue inputValues : lstDecisionInputValues){
	            		if(inputValues.getDecisionComponentId().equals(component.getDecisionComponentId())){
	            			lstCurrentInputValues.add(inputValues);
	            		}
	            	}
	            }
	            component.setParameterInputBeans(lstCurrentInputValues);
	            lstCurrentOutputValues = new ArrayList<DecisionOutputValue>();
	            if(CollectionUtils.isNotEmpty(lstDecisionOutputValues)){
	            	for (DecisionOutputValue outputValues : lstDecisionOutputValues){
	            		if(outputValues.getDecisionComponentId().equals(component.getDecisionComponentId())){
	            			lstCurrentOutputValues.add(outputValues);
	            		}
	            	}
	            }
	            component.setParameterOutputBeans(lstCurrentOutputValues);
	            if(CollectionUtils.isNotEmpty(lstUsedBusinessDesign)){
	            	for (BusinessDesign businessDesign : lstUsedBusinessDesign){
	            		if(FunctionCommon.equals(component.getBusinessLogicId(),businessDesign.getBusinessLogicId())){
	            			mapComponentOfBlogic.put(component.getDecisionComponentId(), businessDesign);
	            			break;
	            		}
	            	}
	            }
            }
			//compare with current design
			Boolean existFlag = false;
			BusinessDesign businessDesign = new BusinessDesign();
			Boolean isImpact = false;
			for (DecisionComponent component : lstDecisionComponents) {
				businessDesign = mapComponentOfBlogic.getOrDefault(component.getDecisionComponentId(),new BusinessDesign());
	            //compare input bean of decision table
				if(CollectionUtils.isNotEmpty(inputBeans)){
					for (DecisionTableInputBean inputBean : inputBeans) {
						existFlag = false;
						DecisionInputValue mappingValue = null;
						for (DecisionInputValue inputValue : component.getParameterInputBeans()){
							if(FunctionCommon.equals(inputValue.getDecisionInputBeanId(), inputBean.getDecisionInputBeanId())){
								existFlag = true;
								mappingValue = inputValue;
								break;
							}
						}
						if(existFlag){
							if(mappingValue != null && FunctionCommon.notEquals(mappingValue.getDataType(), inputBean.getDataType())){
								//modify input bean
								if(isRunBatch){
									problemList = new ProblemList();
									problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0071,inputBean.getDecisionInputBeanCode(),decisionTable.getDecisionTbCode(),component.getLabel(),businessDesign.getBusinessLogicCode()));
									problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
									problemList.setResourceId(businessDesign.getBusinessLogicId());
									problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
									problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
									problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
									problemList.setModuleId(businessDesign.getModuleId());
									problemList.setProjectId(businessDesign.getProjectId());
									problemList.setCreatedBy(common.getCreatedBy());
									problemList.setFromResourceType(DbDomainConst.FromResourceType.DECISION_TABLE);
									problemList.setFromResourceId(decisionTable.getDecisionTbId());
									problemList.setCreatedDate(currentTime);
									lstProblemLists.add(problemList);
								}
								isImpact = true;
							}
						}else{
							//new input bean
							if(isRunBatch){
								problemList = new ProblemList();
								problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0070,inputBean.getDecisionInputBeanCode(),decisionTable.getDecisionTbCode(),component.getLabel(),businessDesign.getBusinessLogicCode()));
								problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
								problemList.setResourceId(businessDesign.getBusinessLogicId());
								problemList.setProblemType(DbDomainConst.ProblemType.MISS_SETTING);
								problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
								problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
								problemList.setModuleId(businessDesign.getModuleId());
								problemList.setProjectId(businessDesign.getProjectId());
								problemList.setCreatedBy(common.getCreatedBy());
								problemList.setFromResourceType(DbDomainConst.FromResourceType.DECISION_TABLE);
								problemList.setFromResourceId(decisionTable.getDecisionTbId());
								problemList.setCreatedDate(currentTime);
								lstProblemLists.add(problemList);
							}
							isImpact = true;
						}
	                }
				}
				//compare output bean of decision table
				if(CollectionUtils.isNotEmpty(outputBeans)){
					for (DecisionTableOutputBean outputBean : outputBeans) {
						existFlag = false;
						DecisionOutputValue mappingValue = null;
						for (DecisionOutputValue outputValue : component.getParameterOutputBeans()){
							if(FunctionCommon.equals(outputValue.getDecisionOutputBeanId(), outputBean.getDecisionOutputBeanId())){
								existFlag = true;
								mappingValue = outputValue;
								break;
							}
						}
						if(existFlag){
							if(mappingValue != null && FunctionCommon.notEquals(mappingValue.getDataType(), outputBean.getDataType())){
								//modify output bean
								if(isRunBatch){
									problemList = new ProblemList();
									problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0072,outputBean.getDecisionOutputBeanCode(),decisionTable.getDecisionTbCode(),component.getLabel(),businessDesign.getBusinessLogicCode()));
									problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
									problemList.setResourceId(businessDesign.getBusinessLogicId());
									problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
									problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
									problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
									problemList.setModuleId(businessDesign.getModuleId());
									problemList.setProjectId(businessDesign.getProjectId());
									problemList.setCreatedBy(common.getCreatedBy());
									problemList.setFromResourceType(DbDomainConst.FromResourceType.DECISION_TABLE);
									problemList.setFromResourceId(decisionTable.getDecisionTbId());
									problemList.setCreatedDate(currentTime);
									lstProblemLists.add(problemList);
								}
								isImpact = true;
							}
						}
	                }
				}
				if(isImpact && !lstTemps.contains(businessDesign)){
					lstTemps.add(businessDesign);
				}
            }
		}
		if(CollectionUtils.isNotEmpty(lstTemps)){
			impact.setImpactFlag(true);
			impact.setLstUsedBusinessDesign(lstTemps);
		}
		if(Boolean.TRUE.equals(isRunBatch)){
			//delete mapping data
			decisionComponentRepository.deleteDecisionInputValueByDecisionTblId(decisionTable.getDecisionTbId());
			decisionComponentRepository.deleteDecisionOutputValueByDecisionTblId(decisionTable.getDecisionTbId());
			//delete old problem of this business
			List<Long> lstFroms = new ArrayList<Long>();
			lstFroms.add(decisionTable.getDecisionTbId());
			problemListRepository.deleteResourceType(DbDomainConst.FromResourceType.DECISION_TABLE, lstFroms);
			//update name code
			
			if(CollectionUtils.isNotEmpty(lstProblemLists)){
				problemListRepository.multiRegisterProblem(lstProblemLists);
			}
		}
		
		return impact;
	}


	@Override
    public void detectListAffectedWhenModifyOfBatch(Long decisionTableId,CommonModel common) {
	    DecisionTable decisionTable = decisionTableService.findOneByDecisionTbId(decisionTableId);
	    if (decisionTable != null) {
	    	this.detectListAffectedWhenModify(decisionTable, common, true);
	    }
    }


	@Override
    public void detectListAffectedWhenDeleteOfBatch(Long decisionTableId,String decisionTableCode,CommonModel common) {
		List<BusinessDesign> lstUsedBusinessDesign = new ArrayList<BusinessDesign>();
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		List<DecisionComponent> lstDecisionComponents = new ArrayList<DecisionComponent>();
		Timestamp systemDate = common.getCreatedDate();
		//detect impact change design
		
		lstDecisionComponents = decisionComponentRepository.findDecisionComponentByDecisionTbId(decisionTableId);
		if(CollectionUtils.isNotEmpty(lstDecisionComponents)){
			lstUsedBusinessDesign = decisionTableRepository.findAllBussinessLogicByDecisionId(decisionTableId);
			//mapping
			BusinessDesign businessDesign = new BusinessDesign();
			ProblemList problemList = new ProblemList();
			for (DecisionComponent component : lstDecisionComponents) {
				for (BusinessDesign temp : lstUsedBusinessDesign){
            		if(FunctionCommon.equals(component.getBusinessLogicId(),temp.getBusinessLogicId())){
            			businessDesign = temp;
            			break;
            		}
            	}
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0073,decisionTableCode,component.getLabel(), businessDesign.getBusinessLogicCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
				problemList.setResourceId(businessDesign.getBusinessLogicId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
				problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
				problemList.setModuleId(businessDesign.getModuleId());
				problemList.setProjectId(businessDesign.getProjectId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.DECISION_TABLE);
				problemList.setFromResourceId(decisionTableId);
				problemList.setCreatedDate(systemDate);
				lstProblemLists.add(problemList);
            }
			
			//delete mapping data
			decisionComponentRepository.deleteDecisionInputValueByDecisionComponent(lstDecisionComponents);
			decisionComponentRepository.deleteDecisionOutputValueByDecisionComponent(lstDecisionComponents);
		}

		//delete old problem of this business
		List<Long> lstFroms = new ArrayList<Long>();
		lstFroms.add(decisionTableId);
		problemListRepository.deleteResourceType(DbDomainConst.FromResourceType.DECISION_TABLE, lstFroms);
		if(CollectionUtils.isNotEmpty(lstProblemLists)){
			problemListRepository.multiRegisterProblem(lstProblemLists);
		}
    }
}
