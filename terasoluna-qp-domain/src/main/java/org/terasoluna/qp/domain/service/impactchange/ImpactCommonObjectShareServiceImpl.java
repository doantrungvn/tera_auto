package org.terasoluna.qp.domain.service.impactchange;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.DbDomainConst.AutoFix;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.ProblemListMessageConst;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.CommonObjectAttribute;
import org.terasoluna.qp.domain.model.CommonObjectDefinition;
import org.terasoluna.qp.domain.model.DecisionTableInputBean;
import org.terasoluna.qp.domain.model.DecisionTableOutputBean;
import org.terasoluna.qp.domain.model.FunctionMethodInput;
import org.terasoluna.qp.domain.model.FunctionMethodOutput;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.ProblemList;
import org.terasoluna.qp.domain.model.SessionManagement;
import org.terasoluna.qp.domain.model.SqlDesignInput;
import org.terasoluna.qp.domain.model.SqlDesignOutput;
import org.terasoluna.qp.domain.repository.commonobjectdefinition.CommonObjectAttributeRepository;
import org.terasoluna.qp.domain.repository.commonobjectdefinition.CommonObjectDefinitionRepository;
import org.terasoluna.qp.domain.repository.impactchange.ImpactCommonObjectRepository;
import org.terasoluna.qp.domain.repository.problemlist.ProblemListRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;

@Service
public class ImpactCommonObjectShareServiceImpl implements ImpactCommonObjectShareService {

	@Inject
	ImpactCommonObjectRepository impactCommonObjectRepository;
	
	@Inject
	ProblemListRepository problemListRepository;
	
	@Inject
	CommonObjectDefinitionRepository commonObjectDefinitionRepository;
	
	@Inject
	CommonObjectAttributeRepository commonObjectAttributeRepository;


	@Override
	public void detectListAffectedWhenDeleteOfBatch(Long commonObjectDefinitionId, String commonObjectDefinitionCode, CommonModel common) {
		CommonObjectDefinition commonObjectDefinition = new CommonObjectDefinition();
		commonObjectDefinition.setCommonObjectDefinitionId(commonObjectDefinitionId);
		commonObjectDefinition.setCommonObjectDefinitionCode(commonObjectDefinitionCode);
		this.detectListAffectedWhenDelete(commonObjectDefinition, common, true);

	}

	@Override
	public ImpactChangeDesign detectListAffectedWhenDelete(CommonObjectDefinition commonObjectDefinition, CommonModel common, Boolean isRunBatch) {
		ImpactChangeDesign impact = new ImpactChangeDesign();
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		List<CommonObjectAttribute> lstUsedCommonObject = impactCommonObjectRepository.findCommonObjAttrByUsingCommonObId(commonObjectDefinition.getCommonObjectDefinitionId());
		List<SessionManagement> lstUsedSessionManagement = impactCommonObjectRepository.findSesionManagementByUsingCommonObId(commonObjectDefinition.getCommonObjectDefinitionId());
		List<FunctionMethodInput> lstUsedFunctionInputs = impactCommonObjectRepository.findFunctionMethodInputByUsingCommonObId(commonObjectDefinition.getCommonObjectDefinitionId());
		List<FunctionMethodOutput> lstUsedFunctionOutputs = impactCommonObjectRepository.findFunctionMethodOutputByUsingCommonObId(commonObjectDefinition.getCommonObjectDefinitionId());
		List<SqlDesignInput> lstUsedSqlDesignInputs = impactCommonObjectRepository.findSQLInputByUsingCommonObId(commonObjectDefinition.getCommonObjectDefinitionId());
		List<SqlDesignOutput> lstUsedSqlDesignOutputs = impactCommonObjectRepository.findSQLOutputByUsingCommonObId(commonObjectDefinition.getCommonObjectDefinitionId());
		List<DecisionTableInputBean> lstUsedDecisionTableInputBeans = impactCommonObjectRepository.findDecisionTableInputBeanByUsingCommonObId(commonObjectDefinition.getCommonObjectDefinitionId());
		List<DecisionTableOutputBean> lstUsedDecisionTableOutputBeans = impactCommonObjectRepository.findDecisionTableOutputBeanByUsingCommonObId(commonObjectDefinition.getCommonObjectDefinitionId());
		List<InputBean> lstUsedInputBeans = impactCommonObjectRepository.findInputBeanByUsingCommonObId(commonObjectDefinition.getCommonObjectDefinitionId());
		List<OutputBean> lstUsedOutputBeans = impactCommonObjectRepository.findOutputBeanByUsingCommonObId(commonObjectDefinition.getCommonObjectDefinitionId());
		List<ObjectDefinition> lstUsedObjectDefinitions = impactCommonObjectRepository.findObjectDefinitionByUsingCommonObId(commonObjectDefinition.getCommonObjectDefinitionId());
		ProblemList problemList = new ProblemList();
		Timestamp currentTime = common.getCreatedDate();

		// case : delete common object
		if (CollectionUtils.isNotEmpty(lstUsedCommonObject)) {
			for (CommonObjectAttribute usedAttribute : lstUsedCommonObject) {
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0153, commonObjectDefinition.getCommonObjectDefinitionCode(), usedAttribute.getCommonObjectAttributeCode(), usedAttribute.getCommonObjectDefinitionCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.COMMON_OBJECT);
				problemList.setResourceId(usedAttribute.getCommonObjectDefinitionId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setAutofixFlg(AutoFix.DISABLE);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_COMMON_OBJECT);
				problemList.setModuleId(usedAttribute.getModuleId());
				problemList.setProjectId(common.getWorkingProjectId());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setCreatedDate(currentTime);
				lstProblemLists.add(problemList);
			}
		}
		if (CollectionUtils.isNotEmpty(lstUsedSessionManagement)) {
			for (SessionManagement usedObject : lstUsedSessionManagement) {
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0154, commonObjectDefinition.getCommonObjectDefinitionCode(), usedObject.getSessionManagementCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.SESSION_MANAGEMENT);
				problemList.setResourceId(usedObject.getSessionManagementId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setAutofixFlg(AutoFix.DISABLE);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_SESSION_MANAGEMENT);
				problemList.setModuleId(null);
				problemList.setProjectId(usedObject.getProjectId());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setCreatedDate(currentTime);
				lstProblemLists.add(problemList);
			}
		}
		if (CollectionUtils.isNotEmpty(lstUsedFunctionInputs)) {
			for (FunctionMethodInput input : lstUsedFunctionInputs) {
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0161, commonObjectDefinition.getCommonObjectDefinitionCode(), input.getMethodInputCode(), input.getFunctionMethodCode(), input.getFunctionMasterCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.FUNCTION_MASTER);
				problemList.setResourceId(input.getFunctionMasterId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setAutofixFlg(AutoFix.DISABLE);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_FUNCTION_MASTER);
				problemList.setModuleId(null);
				problemList.setProjectId(common.getWorkingProjectId());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setCreatedDate(currentTime);
				lstProblemLists.add(problemList);
			}
		}
		if (CollectionUtils.isNotEmpty(lstUsedFunctionOutputs)) {
			for (FunctionMethodOutput input : lstUsedFunctionOutputs) {
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0162, commonObjectDefinition.getCommonObjectDefinitionCode(), input.getMethodOutputCode(), input.getFunctionMethodCode(), input.getFunctionMasterCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.FUNCTION_MASTER);
				problemList.setResourceId(input.getFunctionMasterId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setAutofixFlg(AutoFix.DISABLE);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_FUNCTION_MASTER);
				problemList.setModuleId(null);
				problemList.setProjectId(common.getWorkingProjectId());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setCreatedDate(currentTime);
				lstProblemLists.add(problemList);
			}
		}
		if (CollectionUtils.isNotEmpty(lstUsedSqlDesignInputs)) {
			for (SqlDesignInput input : lstUsedSqlDesignInputs) {
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0169, commonObjectDefinition.getCommonObjectDefinitionCode(), input.getSqlDesignInputCode(), input.getSqlDesignCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.SQL_DESIGN);
				problemList.setResourceId(input.getSqlDesignId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setAutofixFlg(AutoFix.DISABLE);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_SQL_DESIGN);
				problemList.setModuleId(input.getModuleId());
				problemList.setProjectId(common.getWorkingProjectId());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setCreatedDate(currentTime);
				lstProblemLists.add(problemList);
			}
		}
		if (CollectionUtils.isNotEmpty(lstUsedSqlDesignOutputs)) {
			for (SqlDesignOutput output : lstUsedSqlDesignOutputs) {
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0170, commonObjectDefinition.getCommonObjectDefinitionCode(), output.getSqlDesignOutputCode(), output.getSqlDesignCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.SQL_DESIGN);
				problemList.setResourceId(output.getSqlDesignId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setAutofixFlg(AutoFix.DISABLE);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_SQL_DESIGN);
				problemList.setModuleId(output.getModuleId());
				problemList.setProjectId(common.getWorkingProjectId());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setCreatedDate(currentTime);
				lstProblemLists.add(problemList);
			}
		}
		if (CollectionUtils.isNotEmpty(lstUsedDecisionTableInputBeans)) {
			for (DecisionTableInputBean input : lstUsedDecisionTableInputBeans) {
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0177, commonObjectDefinition.getCommonObjectDefinitionCode(), input.getDecisionInputBeanCode(), input.getDecisionTableCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
				problemList.setResourceId(Long.valueOf(input.getDecisionTbId()));
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setAutofixFlg(AutoFix.DISABLE);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
				problemList.setModuleId(input.getModuleId());
				problemList.setProjectId(common.getWorkingProjectId());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setCreatedDate(currentTime);
				lstProblemLists.add(problemList);
			}
		}
		if (CollectionUtils.isNotEmpty(lstUsedDecisionTableOutputBeans)) {
			for (DecisionTableOutputBean output : lstUsedDecisionTableOutputBeans) {
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0178, commonObjectDefinition.getCommonObjectDefinitionCode(), output.getDecisionOutputBeanCode(), output.getDecisionTableCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
				problemList.setResourceId(Long.valueOf(output.getDecisionTbId()));
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setAutofixFlg(AutoFix.DISABLE);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
				problemList.setModuleId(output.getModuleId());
				problemList.setProjectId(common.getWorkingProjectId());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setCreatedDate(currentTime);
				lstProblemLists.add(problemList);
			}
		}
		if (CollectionUtils.isNotEmpty(lstUsedInputBeans)) {
			for (InputBean input : lstUsedInputBeans) {
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0188, commonObjectDefinition.getCommonObjectDefinitionCode(), input.getInputBeanCode(), input.getBusinessLogicCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
				problemList.setResourceId(input.getBusinessLogicId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setAutofixFlg(AutoFix.DISABLE);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
				problemList.setModuleId(input.getModuleId());
				problemList.setProjectId(common.getWorkingProjectId());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setCreatedDate(currentTime);
				lstProblemLists.add(problemList);
			}
		}
		if (CollectionUtils.isNotEmpty(lstUsedOutputBeans)) {
			for (OutputBean output : lstUsedOutputBeans) {
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0189, commonObjectDefinition.getCommonObjectDefinitionCode(), output.getOutputBeanCode(), output.getBusinessLogicCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
				problemList.setResourceId(output.getBusinessLogicId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setAutofixFlg(AutoFix.DISABLE);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
				problemList.setModuleId(output.getModuleId());
				problemList.setProjectId(common.getWorkingProjectId());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setCreatedDate(currentTime);
				lstProblemLists.add(problemList);
			}
		}
		if (CollectionUtils.isNotEmpty(lstUsedObjectDefinitions)) {
			for (ObjectDefinition ob : lstUsedObjectDefinitions) {
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0190, commonObjectDefinition.getCommonObjectDefinitionCode(), ob.getObjectDefinitionCode(), ob.getBusinessLogicCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
				problemList.setResourceId(ob.getBusinessLogicId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setAutofixFlg(AutoFix.DISABLE);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
				problemList.setModuleId(ob.getModuleId());
				problemList.setProjectId(common.getWorkingProjectId());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setCreatedDate(currentTime);
				lstProblemLists.add(problemList);
			}
		}

		// insert problem list into database
		if (isRunBatch) {
			List<Long> lstFromResouceIds = new ArrayList<Long>();
			lstFromResouceIds.add(commonObjectDefinition.getCommonObjectDefinitionId());
			problemListRepository.deleteResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT, lstFromResouceIds);
			if (CollectionUtils.isNotEmpty(lstProblemLists))
				problemListRepository.multiRegisterProblem(lstProblemLists);
		}
		return impact;
	}

	@Override
	public void detectListAffectedWhenModifyOfBatch(Long commonObjectDefinitionId, CommonModel common) {
		CommonObjectDefinition commonObjectDefinition = commonObjectDefinitionRepository.findCommonObjectDefinition(commonObjectDefinitionId);
		if (commonObjectDefinition != null) {
			List<CommonObjectAttribute> lstAttributes = commonObjectAttributeRepository.findCommonObjectAttributeFillterDataType(commonObjectDefinitionId);
			commonObjectDefinition.setCommonObjectAttributes(lstAttributes);
			this.detectListAffectedWhenModify(commonObjectDefinition, common, true);
		}
	}

	@Override
	public ImpactChangeDesign detectListAffectedWhenModify(CommonObjectDefinition commonObjectDefinition, CommonModel common, Boolean isRunBatch) {
		ImpactChangeDesign impact = new ImpactChangeDesign();
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		//business logic
		lstProblemLists.addAll(analyseInputBeanOfBLogicWhenModify(commonObjectDefinition, common));
		lstProblemLists.addAll(analyseOutputBeanOfBLogicWhenModify(commonObjectDefinition, common));
		lstProblemLists.addAll(analyseObjectDefinitionOfBLogicWhenModify(commonObjectDefinition, common));
		
		//decision table
		lstProblemLists.addAll(analyseInputBeanOfDecisionTableWhenModify(commonObjectDefinition, common));
		lstProblemLists.addAll(analyseOutputBeanOfDecisionTableWhenModify(commonObjectDefinition, common));
		
		//sql design
		lstProblemLists.addAll(analyseInputBeanOfSQLDesignWhenModify(commonObjectDefinition, common));
		lstProblemLists.addAll(analyseOutputBeanOfSQLDesignWhenModify(commonObjectDefinition, common));
		
		//function master
		lstProblemLists.addAll(analyseInputBeanOfFunctionMethodWhenModify(commonObjectDefinition, common));
		lstProblemLists.addAll(analyseOutputBeanOfFunctionMethodWhenModify(commonObjectDefinition, common));
				
		// insert problem list into database
		if (isRunBatch) {
			List<Long> lstFromResouceIds = new ArrayList<Long>();
			lstFromResouceIds.add(commonObjectDefinition.getCommonObjectDefinitionId());
			problemListRepository.deleteResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT, lstFromResouceIds);
			if (CollectionUtils.isNotEmpty(lstProblemLists))
				problemListRepository.multiRegisterProblem(lstProblemLists);
			//update name/code of attribute.
		}
		return impact;
	}

	private List<ProblemList> analyseInputBeanOfBLogicWhenModify(CommonObjectDefinition commonObjectDefinition, CommonModel common){
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		ProblemList problemList = new ProblemList();
		Timestamp currentTime = common.getCreatedDate();
		List<InputBean> lstUsedInputBeanMapCommonOb = impactCommonObjectRepository.findInputBeanByUsingCommonObjectDefinitionId(commonObjectDefinition.getCommonObjectDefinitionId());
		List<InputBean> lstUsedInputBeanMapAttr = impactCommonObjectRepository.findInputBeanByParentInputBeanUsingCommonObjectDefinitionId(commonObjectDefinition.getCommonObjectDefinitionId());
		for (InputBean parent : lstUsedInputBeanMapCommonOb) {
			List<InputBean> lstChildren = new ArrayList<InputBean>();
			for (InputBean children : lstUsedInputBeanMapAttr) {
				if(FunctionCommon.equals(parent.getInputBeanId(), children.getParentInputBeanId())){
					lstChildren.add(children);
				}
			}
			parent.setLstChildrens(lstChildren);
        }
		
		//compare
		for (InputBean inputBean : lstUsedInputBeanMapCommonOb) {
	        if(CollectionUtils.isNotEmpty(commonObjectDefinition.getCommonObjectAttributes())){
	        	if(CollectionUtils.isNotEmpty(inputBean.getLstChildrens())){
	        		//case add and modify
	        		for (CommonObjectAttribute attribute : commonObjectDefinition.getCommonObjectAttributes()) {
	        			boolean isExist = false;
	        			InputBean temp = new InputBean();
	        			for (InputBean children : inputBean.getLstChildrens()) {
	                        if(BusinessDesignConst.OBJECT_TYPE_COMMON_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getCommonObjectAttributeId(), children.getObjectId())){
	                        	isExist = true;
	                        	temp = children;
	                        	break;
	                        }
                        }
	        			if(isExist){
	        				if(! FunctionCommon.equals(attribute.getArrayFlg(), temp.getArrayFlg()) || !FunctionCommon.equals(attribute.getDataType(),temp.getDataType())){
	        					//case modified attribute
		        				problemList = new ProblemList();
			    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0182, attribute.getCommonObjectAttributeCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), inputBean.getInputBeanCode(),inputBean.getBusinessLogicCode()));
			    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
			    				problemList.setResourceId(inputBean.getBusinessLogicId());
			    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
			    				problemList.setAutofixFlg(AutoFix.DISABLE);
			    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
			    				problemList.setModuleId(inputBean.getModuleId());
			    				problemList.setProjectId(common.getWorkingProjectId());
			    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
			    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
			    				problemList.setCreatedBy(common.getCreatedBy());
			    				problemList.setCreatedDate(currentTime);
			    				lstProblemLists.add(problemList);
	        				}
	        			}else{
	        				//case added attribute
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0179, attribute.getCommonObjectAttributeCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), inputBean.getInputBeanCode(),inputBean.getBusinessLogicCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
		    				problemList.setResourceId(inputBean.getBusinessLogicId());
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
		    				problemList.setModuleId(inputBean.getModuleId());
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
		    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
	        		}
	        		//case deleted attribute
	        		for (InputBean children : inputBean.getLstChildrens()) {
	        			boolean isExist = false;
	        			for (CommonObjectAttribute attribute : commonObjectDefinition.getCommonObjectAttributes()) {
	        				if(BusinessDesignConst.OBJECT_TYPE_COMMON_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getCommonObjectAttributeId(), children.getObjectId())){
	                        	isExist = true;
	                        	break;
	                        }
	        			}
	        			if(!isExist){
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0185, children.getInputBeanCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), inputBean.getInputBeanCode(),inputBean.getBusinessLogicCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
		    				problemList.setResourceId(inputBean.getBusinessLogicId());
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
		    				problemList.setModuleId(inputBean.getModuleId());
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
		    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
                    }
	        	}else{
	        		//case added attribute
	        		for (CommonObjectAttribute attribute : commonObjectDefinition.getCommonObjectAttributes()) {
	        			problemList = new ProblemList();
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0179, attribute.getCommonObjectAttributeCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), inputBean.getInputBeanCode(),inputBean.getBusinessLogicCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
	    				problemList.setResourceId(inputBean.getBusinessLogicId());
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
	    				problemList.setModuleId(inputBean.getModuleId());
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
	    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
	    				problemList.setCreatedBy(common.getCreatedBy());
	    				problemList.setCreatedDate(currentTime);
	    				lstProblemLists.add(problemList);
                    }
	        	}
	        }else{
	        	if(CollectionUtils.isNotEmpty(inputBean.getLstChildrens())){
	        		//case deleted attribute
	        		for (InputBean children : inputBean.getLstChildrens()) {
	        			problemList = new ProblemList();
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0185, children.getInputBeanCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), inputBean.getInputBeanCode(),inputBean.getBusinessLogicCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
	    				problemList.setResourceId(inputBean.getBusinessLogicId());
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
	    				problemList.setModuleId(inputBean.getModuleId());
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
	    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
	    				problemList.setCreatedBy(common.getCreatedBy());
	    				problemList.setCreatedDate(currentTime);
	    				lstProblemLists.add(problemList);
                    }
	        	}
	        }
        }
		return lstProblemLists;
	}
	
	private List<ProblemList> analyseOutputBeanOfBLogicWhenModify(CommonObjectDefinition commonObjectDefinition, CommonModel common){
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		ProblemList problemList = new ProblemList();
		Timestamp currentTime = common.getCreatedDate();
		List<OutputBean> lstUsedOutputBeanMapExOb = impactCommonObjectRepository.findOutputBeanByUsingCommonObjectDefinitionId(commonObjectDefinition.getCommonObjectDefinitionId());
		List<OutputBean> lstUsedOutputBeanMapAttr = impactCommonObjectRepository.findOutputBeanByParentOutputBeanUsingCommonObjectDefinitionId(commonObjectDefinition.getCommonObjectDefinitionId());
		for (OutputBean parent : lstUsedOutputBeanMapExOb) {
			List<OutputBean> lstChildren = new ArrayList<OutputBean>();
			for (OutputBean children : lstUsedOutputBeanMapAttr) {
				if(FunctionCommon.equals(parent.getOutputBeanId(), children.getParentOutputBeanId())){
					lstChildren.add(children);
				}
			}
			parent.setLstChildrens(lstChildren);
        }
		
		//compare
		for (OutputBean outputBean : lstUsedOutputBeanMapExOb) {
	        if(CollectionUtils.isNotEmpty(commonObjectDefinition.getCommonObjectAttributes())){
	        	if(CollectionUtils.isNotEmpty(outputBean.getLstChildrens())){
	        		//case add and modify
	        		for (CommonObjectAttribute attribute : commonObjectDefinition.getCommonObjectAttributes()) {
	        			boolean isExist = false;
	        			OutputBean temp = new OutputBean();
	        			for (OutputBean children : outputBean.getLstChildrens()) {
	                        if(BusinessDesignConst.OBJECT_TYPE_COMMON_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getCommonObjectAttributeId(), children.getObjectId())){
	                        	isExist = true;
	                        	temp = children;
	                        	break;
	                        }
                        }
	        			if(isExist){
	        				if(! FunctionCommon.equals(attribute.getArrayFlg(), temp.getArrayFlg()) || !FunctionCommon.equals(attribute.getDataType(),temp.getDataType())){
	        					//case modified attribute
		        				problemList = new ProblemList();
			    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0183, attribute.getCommonObjectAttributeCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), outputBean.getOutputBeanCode(),outputBean.getBusinessLogicCode()));
			    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
			    				problemList.setResourceId(outputBean.getBusinessLogicId());
			    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
			    				problemList.setAutofixFlg(AutoFix.DISABLE);
			    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
			    				problemList.setModuleId(outputBean.getModuleId());
			    				problemList.setProjectId(common.getWorkingProjectId());
			    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
			    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
			    				problemList.setCreatedBy(common.getCreatedBy());
			    				problemList.setCreatedDate(currentTime);
			    				lstProblemLists.add(problemList);
	        				}
	        			}else{
	        				//case added attribute
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0180, attribute.getCommonObjectAttributeCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), outputBean.getOutputBeanCode(),outputBean.getBusinessLogicCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
		    				problemList.setResourceId(outputBean.getBusinessLogicId());
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
		    				problemList.setModuleId(outputBean.getModuleId());
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
		    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
	        		}
	        		//case deleted attribute
	        		for (OutputBean children : outputBean.getLstChildrens()) {
	        			boolean isExist = false;
	        			for (CommonObjectAttribute attribute : commonObjectDefinition.getCommonObjectAttributes()) {
	        				if(BusinessDesignConst.OBJECT_TYPE_COMMON_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getCommonObjectAttributeId(), children.getObjectId())){
	                        	isExist = true;
	                        	break;
	                        }
	        			}
	        			if(!isExist){
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0186, children.getOutputBeanCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), outputBean.getOutputBeanCode(),outputBean.getBusinessLogicCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
		    				problemList.setResourceId(outputBean.getBusinessLogicId());
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
		    				problemList.setModuleId(outputBean.getModuleId());
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
		    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
                    }
	        	}else{
	        		//case added attribute
	        		for (CommonObjectAttribute attribute : commonObjectDefinition.getCommonObjectAttributes()) {
	        			problemList = new ProblemList();
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0180, attribute.getCommonObjectAttributeCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), outputBean.getOutputBeanCode(),outputBean.getBusinessLogicCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
	    				problemList.setResourceId(outputBean.getBusinessLogicId());
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
	    				problemList.setModuleId(outputBean.getModuleId());
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
	    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
	    				problemList.setCreatedBy(common.getCreatedBy());
	    				problemList.setCreatedDate(currentTime);
	    				lstProblemLists.add(problemList);
                    }
	        	}
	        }else{
	        	if(CollectionUtils.isNotEmpty(outputBean.getLstChildrens())){
	        		//case deleted attribute
	        		for (OutputBean children : outputBean.getLstChildrens()) {
	        			problemList = new ProblemList();
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0186, children.getOutputBeanCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), outputBean.getOutputBeanCode(),outputBean.getBusinessLogicCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
	    				problemList.setResourceId(outputBean.getBusinessLogicId());
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
	    				problemList.setModuleId(outputBean.getModuleId());
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
	    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
	    				problemList.setCreatedBy(common.getCreatedBy());
	    				problemList.setCreatedDate(currentTime);
	    				lstProblemLists.add(problemList);
                    }
	        	}
	        }
        }
		return lstProblemLists;
	}
	
	private List<ProblemList> analyseObjectDefinitionOfBLogicWhenModify(CommonObjectDefinition commonObjectDefinition, CommonModel common){
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		ProblemList problemList = new ProblemList();
		Timestamp currentTime = common.getCreatedDate();
		List<ObjectDefinition> lstUsedOutputBeanMapExOb = impactCommonObjectRepository.findObjectDefinitionByUsingCommonObjectDefinitionId(commonObjectDefinition.getCommonObjectDefinitionId());
		List<ObjectDefinition> lstUsedOutputBeanMapAttr = impactCommonObjectRepository.findObjectDefinitionByParentObjectDefinitionUsingCommonObjectDefinitionId(commonObjectDefinition.getCommonObjectDefinitionId());
		for (ObjectDefinition parent : lstUsedOutputBeanMapExOb) {
			List<ObjectDefinition> lstChildren = new ArrayList<ObjectDefinition>();
			for (ObjectDefinition children : lstUsedOutputBeanMapAttr) {
				if(FunctionCommon.equals(parent.getObjectDefinitionId(), children.getParentObjectDefinitionId())){
					lstChildren.add(children);
				}
			}
			parent.setLstChildrens(lstChildren);
        }
		
		//compare
		for (ObjectDefinition objectDefinition : lstUsedOutputBeanMapExOb) {
	        if(CollectionUtils.isNotEmpty(commonObjectDefinition.getCommonObjectAttributes())){
	        	if(CollectionUtils.isNotEmpty(objectDefinition.getLstChildrens())){
	        		//case add and modify
	        		for (CommonObjectAttribute attribute : commonObjectDefinition.getCommonObjectAttributes()) {
	        			boolean isExist = false;
	        			ObjectDefinition temp = new ObjectDefinition();
	        			for (ObjectDefinition children : objectDefinition.getLstChildrens()) {
	                        if(BusinessDesignConst.OBJECT_TYPE_COMMON_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getCommonObjectAttributeId(), children.getObjectId())){
	                        	isExist = true;
	                        	temp = children;
	                        	break;
	                        }
                        }
	        			if(isExist){
	        				if(! FunctionCommon.equals(attribute.getArrayFlg(), temp.getArrayFlg()) || !FunctionCommon.equals(attribute.getDataType(),temp.getDataType())){
	        					//case modified attribute
		        				problemList = new ProblemList();
			    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0184, attribute.getCommonObjectAttributeCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), objectDefinition.getObjectDefinitionCode(),objectDefinition.getBusinessLogicCode()));
			    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
			    				problemList.setResourceId(objectDefinition.getBusinessLogicId());
			    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
			    				problemList.setAutofixFlg(AutoFix.DISABLE);
			    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
			    				problemList.setModuleId(objectDefinition.getModuleId());
			    				problemList.setProjectId(common.getWorkingProjectId());
			    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
			    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
			    				problemList.setCreatedBy(common.getCreatedBy());
			    				problemList.setCreatedDate(currentTime);
			    				lstProblemLists.add(problemList);
	        				}
	        			}else{
	        				//case added attribute
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0181, attribute.getCommonObjectAttributeCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), objectDefinition.getObjectDefinitionCode(),objectDefinition.getBusinessLogicCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
		    				problemList.setResourceId(objectDefinition.getBusinessLogicId());
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
		    				problemList.setModuleId(objectDefinition.getModuleId());
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
		    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
	        		}
	        		//case deleted attribute
	        		for (ObjectDefinition children : objectDefinition.getLstChildrens()) {
	        			boolean isExist = false;
	        			for (CommonObjectAttribute attribute : commonObjectDefinition.getCommonObjectAttributes()) {
	        				if(BusinessDesignConst.OBJECT_TYPE_COMMON_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getCommonObjectAttributeId(), children.getObjectId())){
	                        	isExist = true;
	                        	break;
	                        }
	        			}
	        			if(!isExist){
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0187, children.getObjectDefinitionCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), objectDefinition.getObjectDefinitionCode(),objectDefinition.getBusinessLogicCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
		    				problemList.setResourceId(objectDefinition.getBusinessLogicId());
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
		    				problemList.setModuleId(objectDefinition.getModuleId());
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
		    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
                    }
	        	}else{
	        		//case added attribute
	        		for (CommonObjectAttribute attribute : commonObjectDefinition.getCommonObjectAttributes()) {
	        			problemList = new ProblemList();
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0181, attribute.getCommonObjectAttributeCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), objectDefinition.getObjectDefinitionCode(),objectDefinition.getBusinessLogicCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
	    				problemList.setResourceId(objectDefinition.getBusinessLogicId());
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
	    				problemList.setModuleId(objectDefinition.getModuleId());
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
	    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
	    				problemList.setCreatedBy(common.getCreatedBy());
	    				problemList.setCreatedDate(currentTime);
	    				lstProblemLists.add(problemList);
                    }
	        	}
	        }else{
	        	if(CollectionUtils.isNotEmpty(objectDefinition.getLstChildrens())){
	        		//case deleted attribute
	        		for (ObjectDefinition children : objectDefinition.getLstChildrens()) {
	        			problemList = new ProblemList();
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0187, children.getObjectDefinitionCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), objectDefinition.getObjectDefinitionCode(),objectDefinition.getBusinessLogicCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
	    				problemList.setResourceId(objectDefinition.getBusinessLogicId());
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
	    				problemList.setModuleId(objectDefinition.getModuleId());
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
	    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
	    				problemList.setCreatedBy(common.getCreatedBy());
	    				problemList.setCreatedDate(currentTime);
	    				lstProblemLists.add(problemList);
                    }
	        	}
	        }
        }
		return lstProblemLists;
	}
	
	private List<ProblemList> analyseInputBeanOfDecisionTableWhenModify(CommonObjectDefinition commonObjectDefinition, CommonModel common){
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		ProblemList problemList = new ProblemList();
		Timestamp currentTime = common.getCreatedDate();
		List<DecisionTableInputBean> lstUsedInputBeanMapCommonOb = impactCommonObjectRepository.findDecisionTableInputBeanByUsingCommonObjectDefinitionId(commonObjectDefinition.getCommonObjectDefinitionId());
		List<DecisionTableInputBean> lstUsedInputBeanMapAttr = impactCommonObjectRepository.findDecisionTableInputBeanByParentInputBeanUsingCommonObjectDefinitionId(commonObjectDefinition.getCommonObjectDefinitionId());
		for (DecisionTableInputBean parent : lstUsedInputBeanMapCommonOb) {
			List<DecisionTableInputBean> lstChildren = new ArrayList<DecisionTableInputBean>();
			for (DecisionTableInputBean children : lstUsedInputBeanMapAttr) {
				if(FunctionCommon.equals(parent.getDecisionInputBeanId(), children.getParentDecisionInputBeanId())){
					lstChildren.add(children);
				}
			}
			parent.setLstChildrens(lstChildren);
        }
		
		//compare
		for (DecisionTableInputBean inputBean : lstUsedInputBeanMapCommonOb) {
	        if(CollectionUtils.isNotEmpty(commonObjectDefinition.getCommonObjectAttributes())){
	        	if(CollectionUtils.isNotEmpty(inputBean.getLstChildrens())){
	        		//case add and modify
	        		for (CommonObjectAttribute attribute : commonObjectDefinition.getCommonObjectAttributes()) {
	        			boolean isExist = false;
	        			DecisionTableInputBean temp = new DecisionTableInputBean();
	        			for (DecisionTableInputBean children : inputBean.getLstChildrens()) {
	                        if(BusinessDesignConst.OBJECT_TYPE_COMMON_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getCommonObjectAttributeId(), children.getObjectId())){
	                        	isExist = true;
	                        	temp = children;
	                        	break;
	                        }
                        }
	        			if(isExist){
	        				if(!FunctionCommon.equals(attribute.getDataType(),temp.getDataType())){
	        					//case modified attribute
		        				problemList = new ProblemList();
			    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0173, attribute.getCommonObjectAttributeCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), inputBean.getDecisionInputBeanCode(),inputBean.getDecisionTableCode()));
			    				problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
			    				problemList.setResourceId(Long.valueOf(inputBean.getDecisionTbId()));
			    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
			    				problemList.setAutofixFlg(AutoFix.DISABLE);
			    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
			    				problemList.setModuleId(inputBean.getModuleId());
			    				problemList.setProjectId(common.getWorkingProjectId());
			    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
			    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
			    				problemList.setCreatedBy(common.getCreatedBy());
			    				problemList.setCreatedDate(currentTime);
			    				lstProblemLists.add(problemList);
	        				}
	        			}else{
	        				//case added attribute
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0171, attribute.getCommonObjectAttributeCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), inputBean.getDecisionInputBeanCode(),inputBean.getDecisionTableCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
		    				problemList.setResourceId(Long.valueOf(inputBean.getDecisionTbId()));
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
		    				problemList.setModuleId(inputBean.getModuleId());
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
		    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
	        		}
	        		//case deleted attribute
	        		for (DecisionTableInputBean children : inputBean.getLstChildrens()) {
	        			boolean isExist = false;
	        			for (CommonObjectAttribute attribute : commonObjectDefinition.getCommonObjectAttributes()) {
	        				if(BusinessDesignConst.OBJECT_TYPE_COMMON_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getCommonObjectAttributeId(), children.getObjectId())){
	                        	isExist = true;
	                        	break;
	                        }
	        			}
	        			if(!isExist){
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0175, children.getDecisionInputBeanCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), inputBean.getDecisionInputBeanCode(),inputBean.getDecisionTableCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
		    				problemList.setResourceId(Long.valueOf(inputBean.getDecisionTbId()));
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
		    				problemList.setModuleId(inputBean.getModuleId());
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
		    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
                    }
	        	}else{
	        		//case added attribute
	        		for (CommonObjectAttribute attribute : commonObjectDefinition.getCommonObjectAttributes()) {
	        			problemList = new ProblemList();
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0171, attribute.getCommonObjectAttributeCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), inputBean.getDecisionInputBeanCode(),inputBean.getDecisionTableCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
	    				problemList.setResourceId(Long.valueOf(inputBean.getDecisionTbId()));
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
	    				problemList.setModuleId(inputBean.getModuleId());
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
	    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
	    				problemList.setCreatedBy(common.getCreatedBy());
	    				problemList.setCreatedDate(currentTime);
	    				lstProblemLists.add(problemList);
                    }
	        	}
	        }else{
	        	if(CollectionUtils.isNotEmpty(inputBean.getLstChildrens())){
	        		//case deleted attribute
	        		for (DecisionTableInputBean children : inputBean.getLstChildrens()) {
	        			problemList = new ProblemList();
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0175, children.getDecisionInputBeanCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), inputBean.getDecisionInputBeanCode(),inputBean.getDecisionTableCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
	    				problemList.setResourceId(Long.valueOf(inputBean.getDecisionTbId()));
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
	    				problemList.setModuleId(inputBean.getModuleId());
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
	    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
	    				problemList.setCreatedBy(common.getCreatedBy());
	    				problemList.setCreatedDate(currentTime);
	    				lstProblemLists.add(problemList);
                    }
	        	}
	        }
        }
		return lstProblemLists;
	}
	
	private List<ProblemList> analyseOutputBeanOfDecisionTableWhenModify(CommonObjectDefinition commonObjectDefinition, CommonModel common){
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		ProblemList problemList = new ProblemList();
		Timestamp currentTime = common.getCreatedDate();
		List<DecisionTableOutputBean> lstUsedOutputBeanMapExOb = impactCommonObjectRepository.findDecisionTableOutputBeanByUsingCommonObjectDefinitionId(commonObjectDefinition.getCommonObjectDefinitionId());
		List<DecisionTableOutputBean> lstUsedOutputBeanMapAttr = impactCommonObjectRepository.findDecisionTableOutputBeanByParentOutputBeanUsingCommonObjectDefinitionId(commonObjectDefinition.getCommonObjectDefinitionId());
		for (DecisionTableOutputBean parent : lstUsedOutputBeanMapExOb) {
			List<DecisionTableOutputBean> lstChildren = new ArrayList<DecisionTableOutputBean>();
			for (DecisionTableOutputBean children : lstUsedOutputBeanMapAttr) {
				if(FunctionCommon.equals(parent.getDecisionOutputBeanId(), children.getParentDecisionOutputBeanId())){
					lstChildren.add(children);
				}
			}
			parent.setLstChildrens(lstChildren);
        }
		
		//compare
		for (DecisionTableOutputBean outputBean : lstUsedOutputBeanMapExOb) {
	        if(CollectionUtils.isNotEmpty(commonObjectDefinition.getCommonObjectAttributes())){
	        	if(CollectionUtils.isNotEmpty(outputBean.getLstChildrens())){
	        		//case add and modify
	        		for (CommonObjectAttribute attribute : commonObjectDefinition.getCommonObjectAttributes()) {
	        			boolean isExist = false;
	        			DecisionTableOutputBean temp = new DecisionTableOutputBean();
	        			for (DecisionTableOutputBean children : outputBean.getLstChildrens()) {
	                        if(BusinessDesignConst.OBJECT_TYPE_COMMON_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getCommonObjectAttributeId(), children.getObjectId())){
	                        	isExist = true;
	                        	temp = children;
	                        	break;
	                        }
                        }
	        			if(isExist){
	        				if(!FunctionCommon.equals(attribute.getDataType(),temp.getDataType())){
	        					//case modified attribute
		        				problemList = new ProblemList();
			    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0174, attribute.getCommonObjectAttributeCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), outputBean.getDecisionOutputBeanCode(),outputBean.getDecisionTableCode()));
			    				problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
			    				problemList.setResourceId(Long.valueOf(outputBean.getDecisionTbId()));
			    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
			    				problemList.setAutofixFlg(AutoFix.DISABLE);
			    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
			    				problemList.setModuleId(outputBean.getModuleId());
			    				problemList.setProjectId(common.getWorkingProjectId());
			    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
			    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
			    				problemList.setCreatedBy(common.getCreatedBy());
			    				problemList.setCreatedDate(currentTime);
			    				lstProblemLists.add(problemList);
	        				}
	        			}else{
	        				//case added attribute
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0172, attribute.getCommonObjectAttributeCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), outputBean.getDecisionOutputBeanCode(),outputBean.getDecisionTableCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
		    				problemList.setResourceId(Long.valueOf(outputBean.getDecisionTbId()));
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
		    				problemList.setModuleId(outputBean.getModuleId());
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
		    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
	        		}
	        		//case deleted attribute
	        		for (DecisionTableOutputBean children : outputBean.getLstChildrens()) {
	        			boolean isExist = false;
	        			for (CommonObjectAttribute attribute : commonObjectDefinition.getCommonObjectAttributes()) {
	        				if(BusinessDesignConst.OBJECT_TYPE_COMMON_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getCommonObjectAttributeId(), children.getObjectId())){
	                        	isExist = true;
	                        	break;
	                        }
	        			}
	        			if(!isExist){
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0176, children.getDecisionOutputBeanCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), outputBean.getDecisionOutputBeanCode(),outputBean.getDecisionTableCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
		    				problemList.setResourceId(Long.valueOf(outputBean.getDecisionTbId()));
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
		    				problemList.setModuleId(outputBean.getModuleId());
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
		    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
                    }
	        	}else{
	        		//case added attribute
	        		for (CommonObjectAttribute attribute : commonObjectDefinition.getCommonObjectAttributes()) {
	        			problemList = new ProblemList();
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0172, attribute.getCommonObjectAttributeCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), outputBean.getDecisionOutputBeanCode(),outputBean.getDecisionTableCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
	    				problemList.setResourceId(Long.valueOf(outputBean.getDecisionTbId()));
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
	    				problemList.setModuleId(outputBean.getModuleId());
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
	    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
	    				problemList.setCreatedBy(common.getCreatedBy());
	    				problemList.setCreatedDate(currentTime);
	    				lstProblemLists.add(problemList);
                    }
	        	}
	        }else{
	        	if(CollectionUtils.isNotEmpty(outputBean.getLstChildrens())){
	        		//case deleted attribute
	        		for (DecisionTableOutputBean children : outputBean.getLstChildrens()) {
	        			problemList = new ProblemList();
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0176, children.getDecisionOutputBeanCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), outputBean.getDecisionOutputBeanCode(),outputBean.getDecisionTableCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
	    				problemList.setResourceId(Long.valueOf(outputBean.getDecisionTbId()));
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
	    				problemList.setModuleId(outputBean.getModuleId());
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
	    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
	    				problemList.setCreatedBy(common.getCreatedBy());
	    				problemList.setCreatedDate(currentTime);
	    				lstProblemLists.add(problemList);
                    }
	        	}
	        }
        }
		return lstProblemLists;
	}
	
	private List<ProblemList> analyseInputBeanOfSQLDesignWhenModify(CommonObjectDefinition commonObjectDefinition, CommonModel common){
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		ProblemList problemList = new ProblemList();
		Timestamp currentTime = common.getCreatedDate();
		List<SqlDesignInput> lstUsedInputBeanMapCommonOb = impactCommonObjectRepository.findSqlDesignInputByUsingCommonObjectDefinitionId(commonObjectDefinition.getCommonObjectDefinitionId());
		List<SqlDesignInput> lstUsedInputBeanMapAttr = impactCommonObjectRepository.findSqlDesignInputByParentSqlDesignInputUsingCommonObjectDefinitionId(commonObjectDefinition.getCommonObjectDefinitionId());
		for (SqlDesignInput parent : lstUsedInputBeanMapCommonOb) {
			List<SqlDesignInput> lstChildren = new ArrayList<SqlDesignInput>();
			for (SqlDesignInput children : lstUsedInputBeanMapAttr) {
				if(FunctionCommon.equals(parent.getSqlDesignInputId(), children.getSqlDesignInputParentId())){
					lstChildren.add(children);
				}
			}
			parent.setLstChildrens(lstChildren);
        }
		
		//compare
		for (SqlDesignInput inputBean : lstUsedInputBeanMapCommonOb) {
	        if(CollectionUtils.isNotEmpty(commonObjectDefinition.getCommonObjectAttributes())){
	        	if(CollectionUtils.isNotEmpty(inputBean.getLstChildrens())){
	        		//case add and modify
	        		for (CommonObjectAttribute attribute : commonObjectDefinition.getCommonObjectAttributes()) {
	        			boolean isExist = false;
	        			SqlDesignInput temp = new SqlDesignInput();
	        			for (SqlDesignInput children : inputBean.getLstChildrens()) {
	                        if(BusinessDesignConst.OBJECT_TYPE_COMMON_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getCommonObjectAttributeId(), children.getColumnId())){
	                        	isExist = true;
	                        	temp = children;
	                        	break;
	                        }
                        }
	        			if(isExist){
	        				if(!FunctionCommon.equals(attribute.getDataType(),temp.getDataType()) || !FunctionCommon.compare(temp.getArrayFlag(), attribute.getArrayFlg())){
	        					//case modified attribute
		        				problemList = new ProblemList();
			    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0165, attribute.getCommonObjectAttributeCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), inputBean.getSqlDesignInputCode(),inputBean.getSqlDesignCode()));
			    				problemList.setResourceType(DbDomainConst.ResourceType.SQL_DESIGN);
			    				problemList.setResourceId(Long.valueOf(inputBean.getSqlDesignId()));
			    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
			    				problemList.setAutofixFlg(AutoFix.DISABLE);
			    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_SQL_DESIGN);
			    				problemList.setModuleId(inputBean.getModuleId());
			    				problemList.setProjectId(common.getWorkingProjectId());
			    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
			    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
			    				problemList.setCreatedBy(common.getCreatedBy());
			    				problemList.setCreatedDate(currentTime);
			    				lstProblemLists.add(problemList);
	        				}
	        			}else{
	        				//case added attribute
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0163, attribute.getCommonObjectAttributeCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), inputBean.getSqlDesignInputCode(),inputBean.getSqlDesignCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.SQL_DESIGN);
		    				problemList.setResourceId(Long.valueOf(inputBean.getSqlDesignId()));
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_SQL_DESIGN);
		    				problemList.setModuleId(inputBean.getModuleId());
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
		    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
	        		}
	        		//case deleted attribute
	        		for (SqlDesignInput children : inputBean.getLstChildrens()) {
	        			boolean isExist = false;
	        			for (CommonObjectAttribute attribute : commonObjectDefinition.getCommonObjectAttributes()) {
	        				if(BusinessDesignConst.OBJECT_TYPE_COMMON_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getCommonObjectAttributeId(), children.getColumnId())){
	                        	isExist = true;
	                        	break;
	                        }
	        			}
	        			if(!isExist){
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0167, children.getSqlDesignInputCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), inputBean.getSqlDesignInputCode(),inputBean.getSqlDesignCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.SQL_DESIGN);
		    				problemList.setResourceId(Long.valueOf(inputBean.getSqlDesignId()));
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_SQL_DESIGN);
		    				problemList.setModuleId(inputBean.getModuleId());
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
		    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
                    }
	        	}else{
	        		//case added attribute
	        		for (CommonObjectAttribute attribute : commonObjectDefinition.getCommonObjectAttributes()) {
	        			problemList = new ProblemList();
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0163, attribute.getCommonObjectAttributeCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), inputBean.getSqlDesignInputCode(),inputBean.getSqlDesignCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.SQL_DESIGN);
	    				problemList.setResourceId(Long.valueOf(inputBean.getSqlDesignId()));
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_SQL_DESIGN);
	    				problemList.setModuleId(inputBean.getModuleId());
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
	    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
	    				problemList.setCreatedBy(common.getCreatedBy());
	    				problemList.setCreatedDate(currentTime);
	    				lstProblemLists.add(problemList);
                    }
	        	}
	        }else{
	        	if(CollectionUtils.isNotEmpty(inputBean.getLstChildrens())){
	        		//case deleted attribute
	        		for (SqlDesignInput children : inputBean.getLstChildrens()) {
	        			problemList = new ProblemList();
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0167, children.getSqlDesignInputCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), inputBean.getSqlDesignInputCode(),inputBean.getSqlDesignCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.SQL_DESIGN);
	    				problemList.setResourceId(Long.valueOf(inputBean.getSqlDesignId()));
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_SQL_DESIGN);
	    				problemList.setModuleId(inputBean.getModuleId());
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
	    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
	    				problemList.setCreatedBy(common.getCreatedBy());
	    				problemList.setCreatedDate(currentTime);
	    				lstProblemLists.add(problemList);
                    }
	        	}
	        }
        }
		return lstProblemLists;
	}
	
	private List<ProblemList> analyseOutputBeanOfSQLDesignWhenModify(CommonObjectDefinition commonObjectDefinition, CommonModel common){
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		ProblemList problemList = new ProblemList();
		Timestamp currentTime = common.getCreatedDate();


		List<SqlDesignOutput> lstUsedOutputBeanMapExOb = impactCommonObjectRepository.findSqlDesignOutputByUsingCommonObjectDefinitionId(commonObjectDefinition.getCommonObjectDefinitionId());
		List<SqlDesignOutput> lstUsedOutputBeanMapAttr = impactCommonObjectRepository.findSqlDesignOutputByParentSqlDesignOutputUsingCommonObjectDefinitionId(commonObjectDefinition.getCommonObjectDefinitionId());
		for (SqlDesignOutput parent : lstUsedOutputBeanMapExOb) {
			List<SqlDesignOutput> lstChildren = new ArrayList<SqlDesignOutput>();
			for (SqlDesignOutput children : lstUsedOutputBeanMapAttr) {
				if(FunctionCommon.equals(parent.getSqlDesignOutputId(), children.getSqlDesignOutputParentId())){
					lstChildren.add(children);
				}
			}
			parent.setLstChildrens(lstChildren);
        }
		
		//compare
		for (SqlDesignOutput outputBean : lstUsedOutputBeanMapExOb) {
	        if(CollectionUtils.isNotEmpty(commonObjectDefinition.getCommonObjectAttributes())){
	        	if(CollectionUtils.isNotEmpty(outputBean.getLstChildrens())){
	        		//case add and modify
	        		for (CommonObjectAttribute attribute : commonObjectDefinition.getCommonObjectAttributes()) {
	        			boolean isExist = false;
	        			SqlDesignOutput temp = new SqlDesignOutput();
	        			for (SqlDesignOutput children : outputBean.getLstChildrens()) {
	                        if(BusinessDesignConst.OBJECT_TYPE_COMMON_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getCommonObjectAttributeId(), children.getColumnId())){
	                        	isExist = true;
	                        	temp = children;
	                        	break;
	                        }
                        }
	        			if(isExist){
	        				if(!FunctionCommon.equals(attribute.getDataType(),temp.getDataType()) || !FunctionCommon.compare(temp.getArrayFlag(), attribute.getArrayFlg())){
	        					//case modified attribute
		        				problemList = new ProblemList();
			    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0166, attribute.getCommonObjectAttributeCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), outputBean.getSqlDesignOutputCode(),outputBean.getSqlDesignCode()));
			    				problemList.setResourceType(DbDomainConst.ResourceType.SQL_DESIGN);
			    				problemList.setResourceId(Long.valueOf(outputBean.getSqlDesignId()));
			    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
			    				problemList.setAutofixFlg(AutoFix.DISABLE);
			    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_SQL_DESIGN);
			    				problemList.setModuleId(outputBean.getModuleId());
			    				problemList.setProjectId(common.getWorkingProjectId());
			    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
			    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
			    				problemList.setCreatedBy(common.getCreatedBy());
			    				problemList.setCreatedDate(currentTime);
			    				lstProblemLists.add(problemList);
	        				}
	        			}else{
	        				//case added attribute
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0164, attribute.getCommonObjectAttributeCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), outputBean.getSqlDesignOutputCode(),outputBean.getSqlDesignCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.SQL_DESIGN);
		    				problemList.setResourceId(Long.valueOf(outputBean.getSqlDesignId()));
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_SQL_DESIGN);
		    				problemList.setModuleId(outputBean.getModuleId());
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
		    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
	        		}
	        		//case deleted attribute
	        		for (SqlDesignOutput children : outputBean.getLstChildrens()) {
	        			boolean isExist = false;
	        			for (CommonObjectAttribute attribute : commonObjectDefinition.getCommonObjectAttributes()) {
	        				if(BusinessDesignConst.OBJECT_TYPE_COMMON_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getCommonObjectAttributeId(), children.getColumnId())){
	                        	isExist = true;
	                        	break;
	                        }
	        			}
	        			if(!isExist){
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0168, children.getSqlDesignOutputCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), outputBean.getSqlDesignOutputCode(),outputBean.getSqlDesignCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.SQL_DESIGN);
		    				problemList.setResourceId(Long.valueOf(outputBean.getSqlDesignId()));
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_SQL_DESIGN);
		    				problemList.setModuleId(outputBean.getModuleId());
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
		    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
                    }
	        	}else{
	        		//case added attribute
	        		for (CommonObjectAttribute attribute : commonObjectDefinition.getCommonObjectAttributes()) {
	        			problemList = new ProblemList();
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0164, attribute.getCommonObjectAttributeCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), outputBean.getSqlDesignOutputCode(),outputBean.getSqlDesignCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.SQL_DESIGN);
	    				problemList.setResourceId(Long.valueOf(outputBean.getSqlDesignId()));
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_SQL_DESIGN);
	    				problemList.setModuleId(outputBean.getModuleId());
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
	    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
	    				problemList.setCreatedBy(common.getCreatedBy());
	    				problemList.setCreatedDate(currentTime);
	    				lstProblemLists.add(problemList);
                    }
	        	}
	        }else{
	        	if(CollectionUtils.isNotEmpty(outputBean.getLstChildrens())){
	        		//case deleted attribute
	        		for (SqlDesignOutput children : outputBean.getLstChildrens()) {
	        			problemList = new ProblemList();
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0168, children.getSqlDesignOutputCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), outputBean.getSqlDesignOutputCode(),outputBean.getSqlDesignCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.SQL_DESIGN);
	    				problemList.setResourceId(Long.valueOf(outputBean.getSqlDesignId()));
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_SQL_DESIGN);
	    				problemList.setModuleId(outputBean.getModuleId());
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
	    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
	    				problemList.setCreatedBy(common.getCreatedBy());
	    				problemList.setCreatedDate(currentTime);
	    				lstProblemLists.add(problemList);
                    }
	        	}
	        }
        }
		return lstProblemLists;
	}
	
	private List<ProblemList> analyseInputBeanOfFunctionMethodWhenModify(CommonObjectDefinition commonObjectDefinition, CommonModel common){
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		ProblemList problemList = new ProblemList();
		Timestamp currentTime = common.getCreatedDate();
		List<FunctionMethodInput> lstUsedInputBeanMapCommonOb = impactCommonObjectRepository.findFunctionMethodInputByUsingCommonObjectDefinitionId(commonObjectDefinition.getCommonObjectDefinitionId());
		List<FunctionMethodInput> lstUsedInputBeanMapAttr = impactCommonObjectRepository.findFunctionMethodInputByParentInputUsingCommonObjectDefinitionId(commonObjectDefinition.getCommonObjectDefinitionId());
		for (FunctionMethodInput parent : lstUsedInputBeanMapCommonOb) {
			List<FunctionMethodInput> lstChildren = new ArrayList<FunctionMethodInput>();
			for (FunctionMethodInput children : lstUsedInputBeanMapAttr) {
				if(FunctionCommon.equals(parent.getMethodInputId(), children.getParentFunctionMethodInputId())){
					lstChildren.add(children);
				}
			}
			parent.setLstChildrens(lstChildren);
        }
		
		//compare
		for (FunctionMethodInput inputBean : lstUsedInputBeanMapCommonOb) {
	        if(CollectionUtils.isNotEmpty(commonObjectDefinition.getCommonObjectAttributes())){
	        	if(CollectionUtils.isNotEmpty(inputBean.getLstChildrens())){
	        		//case add and modify
	        		for (CommonObjectAttribute attribute : commonObjectDefinition.getCommonObjectAttributes()) {
	        			boolean isExist = false;
	        			FunctionMethodInput temp = new FunctionMethodInput();
	        			for (FunctionMethodInput children : inputBean.getLstChildrens()) {
	                        if(BusinessDesignConst.OBJECT_TYPE_COMMON_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getCommonObjectAttributeId(), children.getObjectId())){
	                        	isExist = true;
	                        	temp = children;
	                        	break;
	                        }
                        }
	        			if(isExist){
	        				if(!FunctionCommon.equals(attribute.getDataType(),temp.getDataType()) || !FunctionCommon.equals(temp.isArrayFlgDisplay(), attribute.getArrayFlg())){
	        					//case modified attribute
		        				problemList = new ProblemList();
			    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0157, attribute.getCommonObjectAttributeCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), inputBean.getMethodInputCode(),inputBean.getFunctionMethodCode(),inputBean.getFunctionMasterCode()));
			    				problemList.setResourceType(DbDomainConst.ResourceType.FUNCTION_MASTER);
			    				problemList.setResourceId(Long.valueOf(inputBean.getFunctionMasterId()));
			    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
			    				problemList.setAutofixFlg(AutoFix.DISABLE);
			    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_FUNCTION_MASTER);
			    				problemList.setModuleId(null);
			    				problemList.setProjectId(common.getWorkingProjectId());
			    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
			    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
			    				problemList.setCreatedBy(common.getCreatedBy());
			    				problemList.setCreatedDate(currentTime);
			    				lstProblemLists.add(problemList);
	        				}
	        			}else{
	        				//case added attribute
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0155, attribute.getCommonObjectAttributeCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), inputBean.getMethodInputCode(),inputBean.getFunctionMethodCode(),inputBean.getFunctionMasterCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.FUNCTION_MASTER);
		    				problemList.setResourceId(Long.valueOf(inputBean.getFunctionMasterId()));
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_FUNCTION_MASTER);
		    				problemList.setModuleId(null);
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
		    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
	        		}
	        		//case deleted attribute
	        		for (FunctionMethodInput children : inputBean.getLstChildrens()) {
	        			boolean isExist = false;
	        			for (CommonObjectAttribute attribute : commonObjectDefinition.getCommonObjectAttributes()) {
	        				if(BusinessDesignConst.OBJECT_TYPE_COMMON_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getCommonObjectAttributeId(), children.getObjectId())){
	                        	isExist = true;
	                        	break;
	                        }
	        			}
	        			if(!isExist){
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0159, children.getMethodInputCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), inputBean.getMethodInputCode(),inputBean.getFunctionMethodCode(),inputBean.getFunctionMasterCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.FUNCTION_MASTER);
		    				problemList.setResourceId(Long.valueOf(inputBean.getFunctionMasterId()));
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_FUNCTION_MASTER);
		    				problemList.setModuleId(null);
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
		    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
                    }
	        	}else{
	        		//case added attribute
	        		for (CommonObjectAttribute attribute : commonObjectDefinition.getCommonObjectAttributes()) {
	        			problemList = new ProblemList();
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0155, attribute.getCommonObjectAttributeCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), inputBean.getMethodInputCode(),inputBean.getFunctionMethodCode(),inputBean.getFunctionMasterCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.FUNCTION_MASTER);
	    				problemList.setResourceId(Long.valueOf(inputBean.getFunctionMasterId()));
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_FUNCTION_MASTER);
	    				problemList.setModuleId(null);
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
	    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
	    				problemList.setCreatedBy(common.getCreatedBy());
	    				problemList.setCreatedDate(currentTime);
	    				lstProblemLists.add(problemList);
                    }
	        	}
	        }else{
	        	if(CollectionUtils.isNotEmpty(inputBean.getLstChildrens())){
	        		//case deleted attribute
	        		for (FunctionMethodInput children : inputBean.getLstChildrens()) {
	        			problemList = new ProblemList();
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0159, children.getMethodInputCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), inputBean.getMethodInputCode(),inputBean.getFunctionMethodCode(),inputBean.getFunctionMasterCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.FUNCTION_MASTER);
	    				problemList.setResourceId(Long.valueOf(inputBean.getFunctionMasterId()));
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_FUNCTION_MASTER);
	    				problemList.setModuleId(null);
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
	    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
	    				problemList.setCreatedBy(common.getCreatedBy());
	    				problemList.setCreatedDate(currentTime);
	    				lstProblemLists.add(problemList);
                    }
	        	}
	        }
        }
		return lstProblemLists;
	}
	
	private List<ProblemList> analyseOutputBeanOfFunctionMethodWhenModify(CommonObjectDefinition commonObjectDefinition, CommonModel common){
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		ProblemList problemList = new ProblemList();
		Timestamp currentTime = common.getCreatedDate();
		List<FunctionMethodOutput> lstUsedOutputBeanMapExOb = impactCommonObjectRepository.findFunctionMethodOutputByUsingCommonObjectDefinitionId(commonObjectDefinition.getCommonObjectDefinitionId());
		List<FunctionMethodOutput> lstUsedOutputBeanMapAttr = impactCommonObjectRepository.findFunctionMethodOutputByParentOutputUsingCommonObjectDefinitionId(commonObjectDefinition.getCommonObjectDefinitionId());
		for (FunctionMethodOutput parent : lstUsedOutputBeanMapExOb) {
			List<FunctionMethodOutput> lstChildren = new ArrayList<FunctionMethodOutput>();
			for (FunctionMethodOutput children : lstUsedOutputBeanMapAttr) {
				if(FunctionCommon.equals(parent.getMethodOutputId(), children.getParentFunctionMethodOutputId())){
					lstChildren.add(children);
				}
			}
			parent.setLstChildrens(lstChildren);
        }
		
		//compare
		for (FunctionMethodOutput outputBean : lstUsedOutputBeanMapExOb) {
	        if(CollectionUtils.isNotEmpty(commonObjectDefinition.getCommonObjectAttributes())){
	        	if(CollectionUtils.isNotEmpty(outputBean.getLstChildrens())){
	        		//case add and modify
	        		for (CommonObjectAttribute attribute : commonObjectDefinition.getCommonObjectAttributes()) {
	        			boolean isExist = false;
	        			FunctionMethodOutput temp = new FunctionMethodOutput();
	        			for (FunctionMethodOutput children : outputBean.getLstChildrens()) {
	                        if(BusinessDesignConst.OBJECT_TYPE_COMMON_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getCommonObjectAttributeId(), children.getObjectId())){
	                        	isExist = true;
	                        	temp = children;
	                        	break;
	                        }
                        }
	        			if(isExist){
	        				if(!FunctionCommon.equals(attribute.getDataType(),temp.getDataType()) || !FunctionCommon.equals(temp.isArrayFlgDisplay(), attribute.getArrayFlg())){
	        					//case modified attribute
		        				problemList = new ProblemList();
			    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0158, attribute.getCommonObjectAttributeCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), outputBean.getMethodOutputCode(),outputBean.getFunctionMethodCode(),outputBean.getFunctionMasterCode()));
			    				problemList.setResourceType(DbDomainConst.ResourceType.FUNCTION_MASTER);
			    				problemList.setResourceId(Long.valueOf(outputBean.getFunctionMasterId()));
			    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
			    				problemList.setAutofixFlg(AutoFix.DISABLE);
			    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_FUNCTION_MASTER);
			    				problemList.setModuleId(null);
			    				problemList.setProjectId(common.getWorkingProjectId());
			    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
			    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
			    				problemList.setCreatedBy(common.getCreatedBy());
			    				problemList.setCreatedDate(currentTime);
			    				lstProblemLists.add(problemList);
	        				}
	        			}else{
	        				//case added attribute
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0156, attribute.getCommonObjectAttributeCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), outputBean.getMethodOutputCode(),outputBean.getFunctionMethodCode(),outputBean.getFunctionMasterCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.FUNCTION_MASTER);
		    				problemList.setResourceId(Long.valueOf(outputBean.getFunctionMasterId()));
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_FUNCTION_MASTER);
		    				problemList.setModuleId(null);
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
		    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
	        		}
	        		//case deleted attribute
	        		for (FunctionMethodOutput children : outputBean.getLstChildrens()) {
	        			boolean isExist = false;
	        			for (CommonObjectAttribute attribute : commonObjectDefinition.getCommonObjectAttributes()) {
	        				if(BusinessDesignConst.OBJECT_TYPE_COMMON_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getCommonObjectAttributeId(), children.getObjectId())){
	                        	isExist = true;
	                        	break;
	                        }
	        			}
	        			if(!isExist){
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0160, children.getMethodOutputCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), outputBean.getMethodOutputCode(),outputBean.getFunctionMethodCode(),outputBean.getFunctionMasterCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.FUNCTION_MASTER);
		    				problemList.setResourceId(Long.valueOf(outputBean.getFunctionMasterId()));
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_FUNCTION_MASTER);
		    				problemList.setModuleId(null);
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
		    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
                    }
	        	}else{
	        		//case added attribute
	        		for (CommonObjectAttribute attribute : commonObjectDefinition.getCommonObjectAttributes()) {
	        			problemList = new ProblemList();
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0156, attribute.getCommonObjectAttributeCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), outputBean.getMethodOutputCode(),outputBean.getFunctionMethodCode(),outputBean.getFunctionMasterCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.FUNCTION_MASTER);
	    				problemList.setResourceId(Long.valueOf(outputBean.getFunctionMasterId()));
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_FUNCTION_MASTER);
	    				problemList.setModuleId(null);
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
	    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
	    				problemList.setCreatedBy(common.getCreatedBy());
	    				problemList.setCreatedDate(currentTime);
	    				lstProblemLists.add(problemList);
                    }
	        	}
	        }else{
	        	if(CollectionUtils.isNotEmpty(outputBean.getLstChildrens())){
	        		//case deleted attribute
	        		for (FunctionMethodOutput children : outputBean.getLstChildrens()) {
	        			problemList = new ProblemList();
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0160, children.getMethodOutputCode(), commonObjectDefinition.getCommonObjectDefinitionCode(), outputBean.getMethodOutputCode(),outputBean.getFunctionMethodCode(),outputBean.getFunctionMasterCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.FUNCTION_MASTER);
	    				problemList.setResourceId(Long.valueOf(outputBean.getFunctionMasterId()));
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_FUNCTION_MASTER);
	    				problemList.setModuleId(null);
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_OBJECT);
	    				problemList.setFromResourceId(commonObjectDefinition.getCommonObjectDefinitionId());
	    				problemList.setCreatedBy(common.getCreatedBy());
	    				problemList.setCreatedDate(currentTime);
	    				lstProblemLists.add(problemList);
                    }
	        	}
	        }
        }
		return lstProblemLists;
	}
}
