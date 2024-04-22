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
import org.terasoluna.qp.domain.model.DecisionTableInputBean;
import org.terasoluna.qp.domain.model.DecisionTableOutputBean;
import org.terasoluna.qp.domain.model.ExternalObjectAttribute;
import org.terasoluna.qp.domain.model.ExternalObjectDefinition;
import org.terasoluna.qp.domain.model.FunctionMethodInput;
import org.terasoluna.qp.domain.model.FunctionMethodOutput;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.ProblemList;
import org.terasoluna.qp.domain.model.SessionManagement;
import org.terasoluna.qp.domain.model.SqlDesignInput;
import org.terasoluna.qp.domain.model.SqlDesignOutput;
import org.terasoluna.qp.domain.repository.externalobjectdefinition.ExternalObjectAttributeRepository;
import org.terasoluna.qp.domain.repository.externalobjectdefinition.ExternalObjectDefinitionRepository;
import org.terasoluna.qp.domain.repository.impactchange.ImpactExternalObjectRepository;
import org.terasoluna.qp.domain.repository.problemlist.ProblemListRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;
import org.terasoluna.qp.domain.service.externalobjectdefinition.ExternalObjectDefinitionService;

@Service
public class ImpactExternalObjectShareServiceImpl implements ImpactExternalObjectShareService {

	@Inject
	ExternalObjectDefinitionRepository externalObjectDefinitionRepository;

	@Inject
	ExternalObjectAttributeRepository externalObjectAttributeRepository;
	
	@Inject
	ExternalObjectDefinitionService externalObjectDefinitionService;

	@Inject
	ProblemListRepository problemListRepository;
	
	@Inject
	ImpactExternalObjectRepository impactExternalObjectRepository;	

	@Override
	public void detectListAffectedWhenDeleteOfBatch(Long externalObjectDefinitionId, String externalObjectDefinitionCode, CommonModel common) {
		ExternalObjectDefinition externalObjectDefinition = new ExternalObjectDefinition();
		externalObjectDefinition.setExternalObjectDefinitionId(externalObjectDefinitionId);
		externalObjectDefinition.setExternalObjectDefinitionCode(externalObjectDefinitionCode);
		this.detectListAffectedWhenDelete(externalObjectDefinition, common, true);
	}

	@Override
	public void detectListAffectedWhenModifyOfBatch(Long externalObjectDefinitionId, CommonModel common) {
		ExternalObjectDefinition externalObjectDefinition = externalObjectDefinitionRepository.findExternalObjectDefinition(externalObjectDefinitionId);
		if (externalObjectDefinition != null) {
			externalObjectDefinition.setExternalObjectAttributes(externalObjectAttributeRepository.findOnlyExternalObjectAttributeByExternalObjectDefinitionId(externalObjectDefinitionId));
			this.detectListAffectedWhenModify(externalObjectDefinition, common, true);
		}
	}

	@Override
	public ImpactChangeDesign detectListAffectedWhenDelete(ExternalObjectDefinition externalObject, CommonModel common, Boolean isRunBatch) {
		ImpactChangeDesign impact = new ImpactChangeDesign();
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		List<ExternalObjectAttribute> lstUsedExternalAttributes = externalObjectAttributeRepository.findOnlyExternalObjectAttributeByExternalOBId(externalObject.getExternalObjectDefinitionId());
		List<CommonObjectAttribute> lstUsedCommonObject = impactExternalObjectRepository.findCommonObjAttrByUsingExtObId(externalObject.getExternalObjectDefinitionId());
		List<SessionManagement> lstUsedSessionManagement = impactExternalObjectRepository.findSesionManagementByUsingExternalObject(externalObject.getExternalObjectDefinitionId());
		List<FunctionMethodInput> lstUsedFunctionInputs = impactExternalObjectRepository.findFunctionMethodInputByUsingExtObId(externalObject.getExternalObjectDefinitionId());
		List<FunctionMethodOutput> lstUsedFunctionOutputs = impactExternalObjectRepository.findFunctionMethodOutputByUsingExtObId(externalObject.getExternalObjectDefinitionId());
		List<SqlDesignInput> lstUsedSqlDesignInputs = impactExternalObjectRepository.findSQLInputByUsingExtObId(externalObject.getExternalObjectDefinitionId());
		List<SqlDesignOutput> lstUsedSqlDesignOutputs = impactExternalObjectRepository.findSQLOutputByUsingExtObId(externalObject.getExternalObjectDefinitionId());
		List<DecisionTableInputBean> lstUsedDecisionTableInputBeans = impactExternalObjectRepository.findDecisionTableInputBeanByUsingExtObId(externalObject.getExternalObjectDefinitionId());
		List<DecisionTableOutputBean> lstUsedDecisionTableOutputBeans = impactExternalObjectRepository.findDecisionTableOutputBeanByUsingExtObId(externalObject.getExternalObjectDefinitionId());
		List<InputBean> lstUsedInputBeans = impactExternalObjectRepository.findInputBeanByUsingExtObId(externalObject.getExternalObjectDefinitionId());
		List<OutputBean> lstUsedOutputBeans = impactExternalObjectRepository.findOutputBeanByUsingExtObId(externalObject.getExternalObjectDefinitionId());
		List<ObjectDefinition> lstUsedObjectDefinitions = impactExternalObjectRepository.findObjectDefinitionByUsingExtObId(externalObject.getExternalObjectDefinitionId());
		ProblemList problemList = new ProblemList();
		Timestamp currentTime = common.getCreatedDate();

		// case : delete external object
		if (CollectionUtils.isNotEmpty(lstUsedExternalAttributes)) {
			for (ExternalObjectAttribute usedAttribute : lstUsedExternalAttributes) {
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0091, externalObject.getExternalObjectDefinitionCode(), usedAttribute.getExternalObjectAttributeCode(), usedAttribute.getExternalObjectDefinitionCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.EXTERNAL_OBJECT);
				problemList.setResourceId(usedAttribute.getExternalObjectDefinitionId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setAutofixFlg(AutoFix.DISABLE);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_EXTERNAL_OBJECT);
				problemList.setModuleId(usedAttribute.getModuleId());
				problemList.setProjectId(common.getWorkingProjectId());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
				problemList.setFromResourceId(externalObject.getExternalObjectDefinitionId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setCreatedDate(currentTime);
				lstProblemLists.add(problemList);
			}
		}
		if (CollectionUtils.isNotEmpty(lstUsedCommonObject)) {
			for (CommonObjectAttribute usedAttribute : lstUsedCommonObject) {
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0092, externalObject.getExternalObjectDefinitionCode(), usedAttribute.getCommonObjectAttributeCode(), usedAttribute.getCommonObjectDefinitionCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.COMMON_OBJECT);
				problemList.setResourceId(usedAttribute.getCommonObjectDefinitionId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setAutofixFlg(AutoFix.DISABLE);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_COMMON_OBJECT);
				problemList.setModuleId(usedAttribute.getModuleId());
				problemList.setProjectId(common.getWorkingProjectId());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
				problemList.setFromResourceId(externalObject.getExternalObjectDefinitionId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setCreatedDate(currentTime);
				lstProblemLists.add(problemList);
			}
		}
		if (CollectionUtils.isNotEmpty(lstUsedSessionManagement)) {
			for (SessionManagement usedObject : lstUsedSessionManagement) {
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0093, externalObject.getExternalObjectDefinitionCode(), usedObject.getSessionManagementCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.SESSION_MANAGEMENT);
				problemList.setResourceId(usedObject.getSessionManagementId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setAutofixFlg(AutoFix.DISABLE);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_SESSION_MANAGEMENT);
				problemList.setModuleId(null);
				problemList.setProjectId(usedObject.getProjectId());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
				problemList.setFromResourceId(externalObject.getExternalObjectDefinitionId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setCreatedDate(currentTime);
				lstProblemLists.add(problemList);
			}
		}
		if (CollectionUtils.isNotEmpty(lstUsedFunctionInputs)) {
			for (FunctionMethodInput input : lstUsedFunctionInputs) {
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0098, externalObject.getExternalObjectDefinitionCode(), input.getMethodInputCode(), input.getFunctionMethodCode(), input.getFunctionMasterCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.FUNCTION_MASTER);
				problemList.setResourceId(input.getFunctionMasterId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setAutofixFlg(AutoFix.DISABLE);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_FUNCTION_MASTER);
				problemList.setModuleId(null);
				problemList.setProjectId(common.getWorkingProjectId());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
				problemList.setFromResourceId(externalObject.getExternalObjectDefinitionId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setCreatedDate(currentTime);
				lstProblemLists.add(problemList);
			}
		}
		if (CollectionUtils.isNotEmpty(lstUsedFunctionOutputs)) {
			for (FunctionMethodOutput input : lstUsedFunctionOutputs) {
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0099, externalObject.getExternalObjectDefinitionCode(), input.getMethodOutputCode(), input.getFunctionMethodCode(), input.getFunctionMasterCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.FUNCTION_MASTER);
				problemList.setResourceId(input.getFunctionMasterId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setAutofixFlg(AutoFix.DISABLE);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_FUNCTION_MASTER);
				problemList.setModuleId(null);
				problemList.setProjectId(common.getWorkingProjectId());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
				problemList.setFromResourceId(externalObject.getExternalObjectDefinitionId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setCreatedDate(currentTime);
				lstProblemLists.add(problemList);
			}
		}
		if (CollectionUtils.isNotEmpty(lstUsedSqlDesignInputs)) {
			for (SqlDesignInput input : lstUsedSqlDesignInputs) {
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0108, externalObject.getExternalObjectDefinitionCode(), input.getSqlDesignInputCode(), input.getSqlDesignCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.SQL_DESIGN);
				problemList.setResourceId(input.getSqlDesignId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setAutofixFlg(AutoFix.DISABLE);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_SQL_DESIGN);
				problemList.setModuleId(input.getModuleId());
				problemList.setProjectId(common.getWorkingProjectId());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
				problemList.setFromResourceId(externalObject.getExternalObjectDefinitionId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setCreatedDate(currentTime);
				lstProblemLists.add(problemList);
			}
		}
		if (CollectionUtils.isNotEmpty(lstUsedSqlDesignOutputs)) {
			for (SqlDesignOutput output : lstUsedSqlDesignOutputs) {
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0109, externalObject.getExternalObjectDefinitionCode(), output.getSqlDesignOutputCode(), output.getSqlDesignCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.SQL_DESIGN);
				problemList.setResourceId(output.getSqlDesignId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setAutofixFlg(AutoFix.DISABLE);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_SQL_DESIGN);
				problemList.setModuleId(output.getModuleId());
				problemList.setProjectId(common.getWorkingProjectId());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
				problemList.setFromResourceId(externalObject.getExternalObjectDefinitionId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setCreatedDate(currentTime);
				lstProblemLists.add(problemList);
			}
		}
		if (CollectionUtils.isNotEmpty(lstUsedDecisionTableInputBeans)) {
			for (DecisionTableInputBean input : lstUsedDecisionTableInputBeans) {
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0116, externalObject.getExternalObjectDefinitionCode(), input.getDecisionInputBeanCode(), input.getDecisionTableCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
				problemList.setResourceId(Long.valueOf(input.getDecisionTbId()));
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setAutofixFlg(AutoFix.DISABLE);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
				problemList.setModuleId(input.getModuleId());
				problemList.setProjectId(common.getWorkingProjectId());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
				problemList.setFromResourceId(externalObject.getExternalObjectDefinitionId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setCreatedDate(currentTime);
				lstProblemLists.add(problemList);
			}
		}
		if (CollectionUtils.isNotEmpty(lstUsedDecisionTableOutputBeans)) {
			for (DecisionTableOutputBean output : lstUsedDecisionTableOutputBeans) {
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0117, externalObject.getExternalObjectDefinitionCode(), output.getDecisionOutputBeanCode(), output.getDecisionTableCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
				problemList.setResourceId(Long.valueOf(output.getDecisionTbId()));
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setAutofixFlg(AutoFix.DISABLE);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
				problemList.setModuleId(output.getModuleId());
				problemList.setProjectId(common.getWorkingProjectId());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
				problemList.setFromResourceId(externalObject.getExternalObjectDefinitionId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setCreatedDate(currentTime);
				lstProblemLists.add(problemList);
			}
		}
		if (CollectionUtils.isNotEmpty(lstUsedInputBeans)) {
			for (InputBean input : lstUsedInputBeans) {
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0124, externalObject.getExternalObjectDefinitionCode(), input.getInputBeanCode(), input.getBusinessLogicCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
				problemList.setResourceId(input.getBusinessLogicId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setAutofixFlg(AutoFix.DISABLE);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
				problemList.setModuleId(input.getModuleId());
				problemList.setProjectId(common.getWorkingProjectId());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
				problemList.setFromResourceId(externalObject.getExternalObjectDefinitionId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setCreatedDate(currentTime);
				lstProblemLists.add(problemList);
			}
		}
		if (CollectionUtils.isNotEmpty(lstUsedOutputBeans)) {
			for (OutputBean output : lstUsedOutputBeans) {
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0125, externalObject.getExternalObjectDefinitionCode(), output.getOutputBeanCode(), output.getBusinessLogicCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
				problemList.setResourceId(output.getBusinessLogicId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setAutofixFlg(AutoFix.DISABLE);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
				problemList.setModuleId(output.getModuleId());
				problemList.setProjectId(common.getWorkingProjectId());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
				problemList.setFromResourceId(externalObject.getExternalObjectDefinitionId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setCreatedDate(currentTime);
				lstProblemLists.add(problemList);
			}
		}
		if (CollectionUtils.isNotEmpty(lstUsedObjectDefinitions)) {
			for (ObjectDefinition ob : lstUsedObjectDefinitions) {
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0133, externalObject.getExternalObjectDefinitionCode(), ob.getObjectDefinitionCode(), ob.getBusinessLogicCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
				problemList.setResourceId(ob.getBusinessLogicId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setAutofixFlg(AutoFix.DISABLE);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
				problemList.setModuleId(ob.getModuleId());
				problemList.setProjectId(common.getWorkingProjectId());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
				problemList.setFromResourceId(externalObject.getExternalObjectDefinitionId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setCreatedDate(currentTime);
				lstProblemLists.add(problemList);
			}
		}

		// insert problem list into database
		if (isRunBatch) {
			List<Long> lstFromResouceIds = new ArrayList<Long>();
			lstFromResouceIds.add(externalObject.getExternalObjectDefinitionId());
			problemListRepository.deleteResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT, lstFromResouceIds);
			if (CollectionUtils.isNotEmpty(lstProblemLists))
				problemListRepository.multiRegisterProblem(lstProblemLists);
		}
		return impact;
	}
	
	@Override
	public ImpactChangeDesign detectListAffectedWhenModify(ExternalObjectDefinition externalObjectDefinition, CommonModel common, Boolean isRunBatch) {
		// TODO Auto-generated method stub
		ImpactChangeDesign impact = new ImpactChangeDesign();
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		//business logic
		lstProblemLists.addAll(analyseInputBeanOfBLogicWhenModify(externalObjectDefinition, common));
		lstProblemLists.addAll(analyseOutputBeanOfBLogicWhenModify(externalObjectDefinition, common));
		lstProblemLists.addAll(analyseObjectDefinitionOfBLogicWhenModify(externalObjectDefinition, common));
		
		//decision table
		lstProblemLists.addAll(analyseInputBeanOfDecisionTableWhenModify(externalObjectDefinition, common));
		lstProblemLists.addAll(analyseOutputBeanOfDecisionTableWhenModify(externalObjectDefinition, common));
		
		//sql design
		lstProblemLists.addAll(analyseInputBeanOfSQLDesignWhenModify(externalObjectDefinition, common));
		lstProblemLists.addAll(analyseOutputBeanOfSQLDesignWhenModify(externalObjectDefinition, common));
		
		//function master
		lstProblemLists.addAll(analyseInputBeanOfFunctionMethodWhenModify(externalObjectDefinition, common));
		lstProblemLists.addAll(analyseOutputBeanOfFunctionMethodWhenModify(externalObjectDefinition, common));
				
		// insert problem list into database
		if (isRunBatch) {
			List<Long> lstFromResouceIds = new ArrayList<Long>();
			lstFromResouceIds.add(externalObjectDefinition.getExternalObjectDefinitionId());
			problemListRepository.deleteResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT, lstFromResouceIds);
			if (CollectionUtils.isNotEmpty(lstProblemLists))
				problemListRepository.multiRegisterProblem(lstProblemLists);
			//update name/code of attribute.
		}
		return impact;
	}
	
	private List<ProblemList> analyseInputBeanOfBLogicWhenModify(ExternalObjectDefinition externalObjectDefinition, CommonModel common){
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		ProblemList problemList = new ProblemList();
		Timestamp currentTime = common.getCreatedDate();
		List<InputBean> lstUsedInputBeanMapExOb = impactExternalObjectRepository.findInputBeanByUsingExternalObId(externalObjectDefinition.getExternalObjectDefinitionId());
		List<InputBean> lstUsedInputBeanMapAttr = impactExternalObjectRepository.findInputBeanByParentInputBeanUsingExternalObId(externalObjectDefinition.getExternalObjectDefinitionId());
		for (InputBean parent : lstUsedInputBeanMapExOb) {
			List<InputBean> lstChildren = new ArrayList<InputBean>();
			for (InputBean children : lstUsedInputBeanMapAttr) {
				if(FunctionCommon.equals(parent.getInputBeanId(), children.getParentInputBeanId())){
					lstChildren.add(children);
				}
			}
			parent.setLstChildrens(lstChildren);
        }
		
		//compare
		for (InputBean inputBean : lstUsedInputBeanMapExOb) {
	        if(CollectionUtils.isNotEmpty(externalObjectDefinition.getExternalObjectAttributes())){
	        	if(CollectionUtils.isNotEmpty(inputBean.getLstChildrens())){
	        		//case add and modify
	        		for (ExternalObjectAttribute attribute : externalObjectDefinition.getExternalObjectAttributes()) {
	        			boolean isExist = false;
	        			InputBean temp = new InputBean();
	        			for (InputBean children : inputBean.getLstChildrens()) {
	                        if(BusinessDesignConst.OBJECT_TYPE_EXTERNAL_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getExternalObjectAttributeId(), children.getObjectId())){
	                        	isExist = true;
	                        	temp = children;
	                        	break;
	                        }
                        }
	        			if(isExist){
	        				if(! FunctionCommon.equals(attribute.getArrayFlg(), temp.getArrayFlg()) || !FunctionCommon.equals(attribute.getDataType(),temp.getDataType())){
	        					//case modified attribute
		        				problemList = new ProblemList();
			    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0120, attribute.getExternalObjectAttributeCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), inputBean.getInputBeanCode(),inputBean.getBusinessLogicCode()));
			    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
			    				problemList.setResourceId(inputBean.getBusinessLogicId());
			    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
			    				problemList.setAutofixFlg(AutoFix.DISABLE);
			    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
			    				problemList.setModuleId(inputBean.getModuleId());
			    				problemList.setProjectId(common.getWorkingProjectId());
			    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
			    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
			    				problemList.setCreatedBy(common.getCreatedBy());
			    				problemList.setCreatedDate(currentTime);
			    				lstProblemLists.add(problemList);
	        				}
	        			}else{
	        				//case added attribute
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0118, attribute.getExternalObjectAttributeCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), inputBean.getInputBeanCode(),inputBean.getBusinessLogicCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
		    				problemList.setResourceId(inputBean.getBusinessLogicId());
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
		    				problemList.setModuleId(inputBean.getModuleId());
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
		    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
	        		}
	        		//case deleted attribute
	        		for (InputBean children : inputBean.getLstChildrens()) {
	        			boolean isExist = false;
	        			for (ExternalObjectAttribute attribute : externalObjectDefinition.getExternalObjectAttributes()) {
	        				if(BusinessDesignConst.OBJECT_TYPE_EXTERNAL_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getExternalObjectAttributeId(), children.getObjectId())){
	                        	isExist = true;
	                        	break;
	                        }
	        			}
	        			if(!isExist){
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0122, children.getInputBeanCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), inputBean.getInputBeanCode(),inputBean.getBusinessLogicCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
		    				problemList.setResourceId(inputBean.getBusinessLogicId());
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
		    				problemList.setModuleId(inputBean.getModuleId());
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
		    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
                    }
	        	}else{
	        		//case added attribute
	        		for (ExternalObjectAttribute attribute : externalObjectDefinition.getExternalObjectAttributes()) {
	        			problemList = new ProblemList();
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0118, attribute.getExternalObjectAttributeCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), inputBean.getInputBeanCode(),inputBean.getBusinessLogicCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
	    				problemList.setResourceId(inputBean.getBusinessLogicId());
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
	    				problemList.setModuleId(inputBean.getModuleId());
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
	    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
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
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0122, children.getInputBeanCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), inputBean.getInputBeanCode(),inputBean.getBusinessLogicCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
	    				problemList.setResourceId(inputBean.getBusinessLogicId());
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
	    				problemList.setModuleId(inputBean.getModuleId());
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
	    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
	    				problemList.setCreatedBy(common.getCreatedBy());
	    				problemList.setCreatedDate(currentTime);
	    				lstProblemLists.add(problemList);
                    }
	        	}
	        }
        }
		return lstProblemLists;
	}
	
	private List<ProblemList> analyseOutputBeanOfBLogicWhenModify(ExternalObjectDefinition externalObjectDefinition, CommonModel common){
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		ProblemList problemList = new ProblemList();
		Timestamp currentTime = common.getCreatedDate();
		List<OutputBean> lstUsedOutputBeanMapExOb = impactExternalObjectRepository.findOutputBeanByUsingExternalObId(externalObjectDefinition.getExternalObjectDefinitionId());
		List<OutputBean> lstUsedOutputBeanMapAttr = impactExternalObjectRepository.findOutputBeanByParentOutputBeanUsingExternalObId(externalObjectDefinition.getExternalObjectDefinitionId());
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
	        if(CollectionUtils.isNotEmpty(externalObjectDefinition.getExternalObjectAttributes())){
	        	if(CollectionUtils.isNotEmpty(outputBean.getLstChildrens())){
	        		//case add and modify
	        		for (ExternalObjectAttribute attribute : externalObjectDefinition.getExternalObjectAttributes()) {
	        			boolean isExist = false;
	        			OutputBean temp = new OutputBean();
	        			for (OutputBean children : outputBean.getLstChildrens()) {
	                        if(BusinessDesignConst.OBJECT_TYPE_EXTERNAL_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getExternalObjectAttributeId(), children.getObjectId())){
	                        	isExist = true;
	                        	temp = children;
	                        	break;
	                        }
                        }
	        			if(isExist){
	        				if(! FunctionCommon.equals(attribute.getArrayFlg(), temp.getArrayFlg()) || !FunctionCommon.equals(attribute.getDataType(),temp.getDataType())){
	        					//case modified attribute
		        				problemList = new ProblemList();
			    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0121, attribute.getExternalObjectAttributeCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), outputBean.getOutputBeanCode(),outputBean.getBusinessLogicCode()));
			    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
			    				problemList.setResourceId(outputBean.getBusinessLogicId());
			    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
			    				problemList.setAutofixFlg(AutoFix.DISABLE);
			    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
			    				problemList.setModuleId(outputBean.getModuleId());
			    				problemList.setProjectId(common.getWorkingProjectId());
			    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
			    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
			    				problemList.setCreatedBy(common.getCreatedBy());
			    				problemList.setCreatedDate(currentTime);
			    				lstProblemLists.add(problemList);
	        				}
	        			}else{
	        				//case added attribute
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0119, attribute.getExternalObjectAttributeCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), outputBean.getOutputBeanCode(),outputBean.getBusinessLogicCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
		    				problemList.setResourceId(outputBean.getBusinessLogicId());
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
		    				problemList.setModuleId(outputBean.getModuleId());
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
		    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
	        		}
	        		//case deleted attribute
	        		for (OutputBean children : outputBean.getLstChildrens()) {
	        			boolean isExist = false;
	        			for (ExternalObjectAttribute attribute : externalObjectDefinition.getExternalObjectAttributes()) {
	        				if(BusinessDesignConst.OBJECT_TYPE_EXTERNAL_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getExternalObjectAttributeId(), children.getObjectId())){
	                        	isExist = true;
	                        	break;
	                        }
	        			}
	        			if(!isExist){
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0123, children.getOutputBeanCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), outputBean.getOutputBeanCode(),outputBean.getBusinessLogicCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
		    				problemList.setResourceId(outputBean.getBusinessLogicId());
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
		    				problemList.setModuleId(outputBean.getModuleId());
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
		    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
                    }
	        	}else{
	        		//case added attribute
	        		for (ExternalObjectAttribute attribute : externalObjectDefinition.getExternalObjectAttributes()) {
	        			problemList = new ProblemList();
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0119, attribute.getExternalObjectAttributeCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), outputBean.getOutputBeanCode(),outputBean.getBusinessLogicCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
	    				problemList.setResourceId(outputBean.getBusinessLogicId());
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
	    				problemList.setModuleId(outputBean.getModuleId());
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
	    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
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
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0123, children.getOutputBeanCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), outputBean.getOutputBeanCode(),outputBean.getBusinessLogicCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
	    				problemList.setResourceId(outputBean.getBusinessLogicId());
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
	    				problemList.setModuleId(outputBean.getModuleId());
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
	    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
	    				problemList.setCreatedBy(common.getCreatedBy());
	    				problemList.setCreatedDate(currentTime);
	    				lstProblemLists.add(problemList);
                    }
	        	}
	        }
        }
		return lstProblemLists;
	}
	
	private List<ProblemList> analyseObjectDefinitionOfBLogicWhenModify(ExternalObjectDefinition externalObjectDefinition, CommonModel common){
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		ProblemList problemList = new ProblemList();
		Timestamp currentTime = common.getCreatedDate();
		List<ObjectDefinition> lstUsedOutputBeanMapExOb = impactExternalObjectRepository.findObjectDefinitionByUsingExternalObId(externalObjectDefinition.getExternalObjectDefinitionId());
		List<ObjectDefinition> lstUsedOutputBeanMapAttr = impactExternalObjectRepository.findObjectDefinitionByParentObjectDefinitionUsingExternalObId(externalObjectDefinition.getExternalObjectDefinitionId());
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
	        if(CollectionUtils.isNotEmpty(externalObjectDefinition.getExternalObjectAttributes())){
	        	if(CollectionUtils.isNotEmpty(objectDefinition.getLstChildrens())){
	        		//case add and modify
	        		for (ExternalObjectAttribute attribute : externalObjectDefinition.getExternalObjectAttributes()) {
	        			boolean isExist = false;
	        			ObjectDefinition temp = new ObjectDefinition();
	        			for (ObjectDefinition children : objectDefinition.getLstChildrens()) {
	                        if(BusinessDesignConst.OBJECT_TYPE_EXTERNAL_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getExternalObjectAttributeId(), children.getObjectId())){
	                        	isExist = true;
	                        	temp = children;
	                        	break;
	                        }
                        }
	        			if(isExist){
	        				if(! FunctionCommon.equals(attribute.getArrayFlg(), temp.getArrayFlg()) || !FunctionCommon.equals(attribute.getDataType(),temp.getDataType())){
	        					//case modified attribute
		        				problemList = new ProblemList();
			    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0131, attribute.getExternalObjectAttributeCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), objectDefinition.getObjectDefinitionCode(),objectDefinition.getBusinessLogicCode()));
			    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
			    				problemList.setResourceId(objectDefinition.getBusinessLogicId());
			    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
			    				problemList.setAutofixFlg(AutoFix.DISABLE);
			    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
			    				problemList.setModuleId(objectDefinition.getModuleId());
			    				problemList.setProjectId(common.getWorkingProjectId());
			    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
			    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
			    				problemList.setCreatedBy(common.getCreatedBy());
			    				problemList.setCreatedDate(currentTime);
			    				lstProblemLists.add(problemList);
	        				}
	        			}else{
	        				//case added attribute
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0130, attribute.getExternalObjectAttributeCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), objectDefinition.getObjectDefinitionCode(),objectDefinition.getBusinessLogicCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
		    				problemList.setResourceId(objectDefinition.getBusinessLogicId());
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
		    				problemList.setModuleId(objectDefinition.getModuleId());
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
		    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
	        		}
	        		//case deleted attribute
	        		for (ObjectDefinition children : objectDefinition.getLstChildrens()) {
	        			boolean isExist = false;
	        			for (ExternalObjectAttribute attribute : externalObjectDefinition.getExternalObjectAttributes()) {
	        				if(BusinessDesignConst.OBJECT_TYPE_EXTERNAL_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getExternalObjectAttributeId(), children.getObjectId())){
	                        	isExist = true;
	                        	break;
	                        }
	        			}
	        			if(!isExist){
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0132, children.getObjectDefinitionCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), objectDefinition.getObjectDefinitionCode(),objectDefinition.getBusinessLogicCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
		    				problemList.setResourceId(objectDefinition.getBusinessLogicId());
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
		    				problemList.setModuleId(objectDefinition.getModuleId());
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
		    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
                    }
	        	}else{
	        		//case added attribute
	        		for (ExternalObjectAttribute attribute : externalObjectDefinition.getExternalObjectAttributes()) {
	        			problemList = new ProblemList();
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0130, attribute.getExternalObjectAttributeCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), objectDefinition.getObjectDefinitionCode(),objectDefinition.getBusinessLogicCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
	    				problemList.setResourceId(objectDefinition.getBusinessLogicId());
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
	    				problemList.setModuleId(objectDefinition.getModuleId());
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
	    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
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
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0132, children.getObjectDefinitionCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), objectDefinition.getObjectDefinitionCode(),objectDefinition.getBusinessLogicCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
	    				problemList.setResourceId(objectDefinition.getBusinessLogicId());
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
	    				problemList.setModuleId(objectDefinition.getModuleId());
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
	    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
	    				problemList.setCreatedBy(common.getCreatedBy());
	    				problemList.setCreatedDate(currentTime);
	    				lstProblemLists.add(problemList);
                    }
	        	}
	        }
        }
		return lstProblemLists;
	}
	
	private List<ProblemList> analyseInputBeanOfDecisionTableWhenModify(ExternalObjectDefinition externalObjectDefinition, CommonModel common){
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		ProblemList problemList = new ProblemList();
		Timestamp currentTime = common.getCreatedDate();
		List<DecisionTableInputBean> lstUsedInputBeanMapExOb = impactExternalObjectRepository.findDecisionTableInputBeanByUsingExternalObId(externalObjectDefinition.getExternalObjectDefinitionId());
		List<DecisionTableInputBean> lstUsedInputBeanMapAttr = impactExternalObjectRepository.findDecisionTableInputBeanByParentInputBeanUsingExternalObId(externalObjectDefinition.getExternalObjectDefinitionId());
		for (DecisionTableInputBean parent : lstUsedInputBeanMapExOb) {
			List<DecisionTableInputBean> lstChildren = new ArrayList<DecisionTableInputBean>();
			for (DecisionTableInputBean children : lstUsedInputBeanMapAttr) {
				if(FunctionCommon.equals(parent.getDecisionInputBeanId(), children.getParentDecisionInputBeanId())){
					lstChildren.add(children);
				}
			}
			parent.setLstChildrens(lstChildren);
        }
		
		//compare
		for (DecisionTableInputBean inputBean : lstUsedInputBeanMapExOb) {
	        if(CollectionUtils.isNotEmpty(externalObjectDefinition.getExternalObjectAttributes())){
	        	if(CollectionUtils.isNotEmpty(inputBean.getLstChildrens())){
	        		//case add and modify
	        		for (ExternalObjectAttribute attribute : externalObjectDefinition.getExternalObjectAttributes()) {
	        			boolean isExist = false;
	        			DecisionTableInputBean temp = new DecisionTableInputBean();
	        			for (DecisionTableInputBean children : inputBean.getLstChildrens()) {
	                        if(BusinessDesignConst.OBJECT_TYPE_EXTERNAL_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getExternalObjectAttributeId(), children.getObjectId())){
	                        	isExist = true;
	                        	temp = children;
	                        	break;
	                        }
                        }
	        			if(isExist){
	        				if(!FunctionCommon.equals(attribute.getDataType(),temp.getDataType())){
	        					//case modified attribute
		        				problemList = new ProblemList();
			    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0112, attribute.getExternalObjectAttributeCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), inputBean.getDecisionInputBeanCode(),inputBean.getDecisionTableCode()));
			    				problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
			    				problemList.setResourceId(Long.valueOf(inputBean.getDecisionTbId()));
			    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
			    				problemList.setAutofixFlg(AutoFix.DISABLE);
			    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
			    				problemList.setModuleId(inputBean.getModuleId());
			    				problemList.setProjectId(common.getWorkingProjectId());
			    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
			    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
			    				problemList.setCreatedBy(common.getCreatedBy());
			    				problemList.setCreatedDate(currentTime);
			    				lstProblemLists.add(problemList);
	        				}
	        			}else{
	        				//case added attribute
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0110, attribute.getExternalObjectAttributeCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), inputBean.getDecisionInputBeanCode(),inputBean.getDecisionTableCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
		    				problemList.setResourceId(Long.valueOf(inputBean.getDecisionTbId()));
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
		    				problemList.setModuleId(inputBean.getModuleId());
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
		    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
	        		}
	        		//case deleted attribute
	        		for (DecisionTableInputBean children : inputBean.getLstChildrens()) {
	        			boolean isExist = false;
	        			for (ExternalObjectAttribute attribute : externalObjectDefinition.getExternalObjectAttributes()) {
	        				if(BusinessDesignConst.OBJECT_TYPE_EXTERNAL_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getExternalObjectAttributeId(), children.getObjectId())){
	                        	isExist = true;
	                        	break;
	                        }
	        			}
	        			if(!isExist){
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0114, children.getDecisionInputBeanCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), inputBean.getDecisionInputBeanCode(),inputBean.getDecisionTableCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
		    				problemList.setResourceId(Long.valueOf(inputBean.getDecisionTbId()));
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
		    				problemList.setModuleId(inputBean.getModuleId());
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
		    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
                    }
	        	}else{
	        		//case added attribute
	        		for (ExternalObjectAttribute attribute : externalObjectDefinition.getExternalObjectAttributes()) {
	        			problemList = new ProblemList();
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0110, attribute.getExternalObjectAttributeCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), inputBean.getDecisionInputBeanCode(),inputBean.getDecisionTableCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
	    				problemList.setResourceId(Long.valueOf(inputBean.getDecisionTbId()));
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
	    				problemList.setModuleId(inputBean.getModuleId());
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
	    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
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
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0114, children.getDecisionInputBeanCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), inputBean.getDecisionInputBeanCode(),inputBean.getDecisionTableCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
	    				problemList.setResourceId(Long.valueOf(inputBean.getDecisionTbId()));
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
	    				problemList.setModuleId(inputBean.getModuleId());
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
	    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
	    				problemList.setCreatedBy(common.getCreatedBy());
	    				problemList.setCreatedDate(currentTime);
	    				lstProblemLists.add(problemList);
                    }
	        	}
	        }
        }
		return lstProblemLists;
	}
	
	private List<ProblemList> analyseOutputBeanOfDecisionTableWhenModify(ExternalObjectDefinition externalObjectDefinition, CommonModel common){
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		ProblemList problemList = new ProblemList();
		Timestamp currentTime = common.getCreatedDate();
		List<DecisionTableOutputBean> lstUsedOutputBeanMapExOb = impactExternalObjectRepository.findDecisionTableOutputBeanByUsingExternalObId(externalObjectDefinition.getExternalObjectDefinitionId());
		List<DecisionTableOutputBean> lstUsedOutputBeanMapAttr = impactExternalObjectRepository.findDecisionTableOutputBeanByParentOutputBeanUsingExternalObId(externalObjectDefinition.getExternalObjectDefinitionId());
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
	        if(CollectionUtils.isNotEmpty(externalObjectDefinition.getExternalObjectAttributes())){
	        	if(CollectionUtils.isNotEmpty(outputBean.getLstChildrens())){
	        		//case add and modify
	        		for (ExternalObjectAttribute attribute : externalObjectDefinition.getExternalObjectAttributes()) {
	        			boolean isExist = false;
	        			DecisionTableOutputBean temp = new DecisionTableOutputBean();
	        			for (DecisionTableOutputBean children : outputBean.getLstChildrens()) {
	                        if(BusinessDesignConst.OBJECT_TYPE_EXTERNAL_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getExternalObjectAttributeId(), children.getObjectId())){
	                        	isExist = true;
	                        	temp = children;
	                        	break;
	                        }
                        }
	        			if(isExist){
	        				if(!FunctionCommon.equals(attribute.getDataType(),temp.getDataType())){
	        					//case modified attribute
		        				problemList = new ProblemList();
			    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0113, attribute.getExternalObjectAttributeCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), outputBean.getDecisionOutputBeanCode(),outputBean.getDecisionTableCode()));
			    				problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
			    				problemList.setResourceId(Long.valueOf(outputBean.getDecisionTbId()));
			    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
			    				problemList.setAutofixFlg(AutoFix.DISABLE);
			    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
			    				problemList.setModuleId(outputBean.getModuleId());
			    				problemList.setProjectId(common.getWorkingProjectId());
			    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
			    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
			    				problemList.setCreatedBy(common.getCreatedBy());
			    				problemList.setCreatedDate(currentTime);
			    				lstProblemLists.add(problemList);
	        				}
	        			}else{
	        				//case added attribute
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0111, attribute.getExternalObjectAttributeCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), outputBean.getDecisionOutputBeanCode(),outputBean.getDecisionTableCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
		    				problemList.setResourceId(Long.valueOf(outputBean.getDecisionTbId()));
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
		    				problemList.setModuleId(outputBean.getModuleId());
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
		    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
	        		}
	        		//case deleted attribute
	        		for (DecisionTableOutputBean children : outputBean.getLstChildrens()) {
	        			boolean isExist = false;
	        			for (ExternalObjectAttribute attribute : externalObjectDefinition.getExternalObjectAttributes()) {
	        				if(BusinessDesignConst.OBJECT_TYPE_EXTERNAL_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getExternalObjectAttributeId(), children.getObjectId())){
	                        	isExist = true;
	                        	break;
	                        }
	        			}
	        			if(!isExist){
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0115, children.getDecisionOutputBeanCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), outputBean.getDecisionOutputBeanCode(),outputBean.getDecisionTableCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
		    				problemList.setResourceId(Long.valueOf(outputBean.getDecisionTbId()));
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
		    				problemList.setModuleId(outputBean.getModuleId());
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
		    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
                    }
	        	}else{
	        		//case added attribute
	        		for (ExternalObjectAttribute attribute : externalObjectDefinition.getExternalObjectAttributes()) {
	        			problemList = new ProblemList();
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0111, attribute.getExternalObjectAttributeCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), outputBean.getDecisionOutputBeanCode(),outputBean.getDecisionTableCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
	    				problemList.setResourceId(Long.valueOf(outputBean.getDecisionTbId()));
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
	    				problemList.setModuleId(outputBean.getModuleId());
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
	    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
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
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0115, children.getDecisionOutputBeanCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), outputBean.getDecisionOutputBeanCode(),outputBean.getDecisionTableCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.DECISION_TABLE);
	    				problemList.setResourceId(Long.valueOf(outputBean.getDecisionTbId()));
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_DECISION_TABLE);
	    				problemList.setModuleId(outputBean.getModuleId());
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
	    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
	    				problemList.setCreatedBy(common.getCreatedBy());
	    				problemList.setCreatedDate(currentTime);
	    				lstProblemLists.add(problemList);
                    }
	        	}
	        }
        }
		return lstProblemLists;
	}
	
	private List<ProblemList> analyseInputBeanOfSQLDesignWhenModify(ExternalObjectDefinition externalObjectDefinition, CommonModel common){
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		ProblemList problemList = new ProblemList();
		Timestamp currentTime = common.getCreatedDate();
		List<SqlDesignInput> lstUsedInputBeanMapExOb = impactExternalObjectRepository.findSqlDesignInputByUsingExternalObId(externalObjectDefinition.getExternalObjectDefinitionId());
		List<SqlDesignInput> lstUsedInputBeanMapAttr = impactExternalObjectRepository.findSqlDesignInputByParentSqlDesignInputUsingExternalObId(externalObjectDefinition.getExternalObjectDefinitionId());
		for (SqlDesignInput parent : lstUsedInputBeanMapExOb) {
			List<SqlDesignInput> lstChildren = new ArrayList<SqlDesignInput>();
			for (SqlDesignInput children : lstUsedInputBeanMapAttr) {
				if(FunctionCommon.equals(parent.getSqlDesignInputId(), children.getSqlDesignInputParentId())){
					lstChildren.add(children);
				}
			}
			parent.setLstChildrens(lstChildren);
        }
		
		//compare
		for (SqlDesignInput inputBean : lstUsedInputBeanMapExOb) {
	        if(CollectionUtils.isNotEmpty(externalObjectDefinition.getExternalObjectAttributes())){
	        	if(CollectionUtils.isNotEmpty(inputBean.getLstChildrens())){
	        		//case add and modify
	        		for (ExternalObjectAttribute attribute : externalObjectDefinition.getExternalObjectAttributes()) {
	        			boolean isExist = false;
	        			SqlDesignInput temp = new SqlDesignInput();
	        			for (SqlDesignInput children : inputBean.getLstChildrens()) {
	                        if(BusinessDesignConst.OBJECT_TYPE_EXTERNAL_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getExternalObjectAttributeId(), children.getColumnId())){
	                        	isExist = true;
	                        	temp = children;
	                        	break;
	                        }
                        }
	        			if(isExist){
	        				if(!FunctionCommon.equals(attribute.getDataType(),temp.getDataType()) || !FunctionCommon.compare(temp.getArrayFlag(), attribute.getArrayFlg())){
	        					//case modified attribute
		        				problemList = new ProblemList();
			    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0104, attribute.getExternalObjectAttributeCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), inputBean.getSqlDesignInputCode(),inputBean.getSqlDesignCode()));
			    				problemList.setResourceType(DbDomainConst.ResourceType.SQL_DESIGN);
			    				problemList.setResourceId(Long.valueOf(inputBean.getSqlDesignId()));
			    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
			    				problemList.setAutofixFlg(AutoFix.DISABLE);
			    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_SQL_DESIGN);
			    				problemList.setModuleId(inputBean.getModuleId());
			    				problemList.setProjectId(common.getWorkingProjectId());
			    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
			    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
			    				problemList.setCreatedBy(common.getCreatedBy());
			    				problemList.setCreatedDate(currentTime);
			    				lstProblemLists.add(problemList);
	        				}
	        			}else{
	        				//case added attribute
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0102, attribute.getExternalObjectAttributeCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), inputBean.getSqlDesignInputCode(),inputBean.getSqlDesignCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.SQL_DESIGN);
		    				problemList.setResourceId(Long.valueOf(inputBean.getSqlDesignId()));
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_SQL_DESIGN);
		    				problemList.setModuleId(inputBean.getModuleId());
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
		    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
	        		}
	        		//case deleted attribute
	        		for (SqlDesignInput children : inputBean.getLstChildrens()) {
	        			boolean isExist = false;
	        			for (ExternalObjectAttribute attribute : externalObjectDefinition.getExternalObjectAttributes()) {
	        				if(BusinessDesignConst.OBJECT_TYPE_EXTERNAL_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getExternalObjectAttributeId(), children.getColumnId())){
	                        	isExist = true;
	                        	break;
	                        }
	        			}
	        			if(!isExist){
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0106, children.getSqlDesignInputCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), inputBean.getSqlDesignInputCode(),inputBean.getSqlDesignCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.SQL_DESIGN);
		    				problemList.setResourceId(Long.valueOf(inputBean.getSqlDesignId()));
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_SQL_DESIGN);
		    				problemList.setModuleId(inputBean.getModuleId());
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
		    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
                    }
	        	}else{
	        		//case added attribute
	        		for (ExternalObjectAttribute attribute : externalObjectDefinition.getExternalObjectAttributes()) {
	        			problemList = new ProblemList();
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0102, attribute.getExternalObjectAttributeCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), inputBean.getSqlDesignInputCode(),inputBean.getSqlDesignCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.SQL_DESIGN);
	    				problemList.setResourceId(Long.valueOf(inputBean.getSqlDesignId()));
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_SQL_DESIGN);
	    				problemList.setModuleId(inputBean.getModuleId());
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
	    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
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
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0106, children.getSqlDesignInputCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), inputBean.getSqlDesignInputCode(),inputBean.getSqlDesignCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.SQL_DESIGN);
	    				problemList.setResourceId(Long.valueOf(inputBean.getSqlDesignId()));
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_SQL_DESIGN);
	    				problemList.setModuleId(inputBean.getModuleId());
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
	    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
	    				problemList.setCreatedBy(common.getCreatedBy());
	    				problemList.setCreatedDate(currentTime);
	    				lstProblemLists.add(problemList);
                    }
	        	}
	        }
        }
		return lstProblemLists;
	}
	
	private List<ProblemList> analyseOutputBeanOfSQLDesignWhenModify(ExternalObjectDefinition externalObjectDefinition, CommonModel common){
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		ProblemList problemList = new ProblemList();
		Timestamp currentTime = common.getCreatedDate();


		List<SqlDesignOutput> lstUsedOutputBeanMapExOb = impactExternalObjectRepository.findSqlDesignOutputByUsingExternalObId(externalObjectDefinition.getExternalObjectDefinitionId());
		List<SqlDesignOutput> lstUsedOutputBeanMapAttr = impactExternalObjectRepository.findSqlDesignOutputByParentSqlDesignOutputUsingExternalObId(externalObjectDefinition.getExternalObjectDefinitionId());
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
	        if(CollectionUtils.isNotEmpty(externalObjectDefinition.getExternalObjectAttributes())){
	        	if(CollectionUtils.isNotEmpty(outputBean.getLstChildrens())){
	        		//case add and modify
	        		for (ExternalObjectAttribute attribute : externalObjectDefinition.getExternalObjectAttributes()) {
	        			boolean isExist = false;
	        			SqlDesignOutput temp = new SqlDesignOutput();
	        			for (SqlDesignOutput children : outputBean.getLstChildrens()) {
	                        if(BusinessDesignConst.OBJECT_TYPE_EXTERNAL_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getExternalObjectAttributeId(), children.getColumnId())){
	                        	isExist = true;
	                        	temp = children;
	                        	break;
	                        }
                        }
	        			if(isExist){
	        				if(!FunctionCommon.equals(attribute.getDataType(),temp.getDataType()) || !FunctionCommon.compare(temp.getArrayFlag(), attribute.getArrayFlg())){
	        					//case modified attribute
		        				problemList = new ProblemList();
			    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0105, attribute.getExternalObjectAttributeCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), outputBean.getSqlDesignOutputCode(),outputBean.getSqlDesignCode()));
			    				problemList.setResourceType(DbDomainConst.ResourceType.SQL_DESIGN);
			    				problemList.setResourceId(Long.valueOf(outputBean.getSqlDesignId()));
			    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
			    				problemList.setAutofixFlg(AutoFix.DISABLE);
			    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_SQL_DESIGN);
			    				problemList.setModuleId(outputBean.getModuleId());
			    				problemList.setProjectId(common.getWorkingProjectId());
			    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
			    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
			    				problemList.setCreatedBy(common.getCreatedBy());
			    				problemList.setCreatedDate(currentTime);
			    				lstProblemLists.add(problemList);
	        				}
	        			}else{
	        				//case added attribute
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0103, attribute.getExternalObjectAttributeCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), outputBean.getSqlDesignOutputCode(),outputBean.getSqlDesignCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.SQL_DESIGN);
		    				problemList.setResourceId(Long.valueOf(outputBean.getSqlDesignId()));
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_SQL_DESIGN);
		    				problemList.setModuleId(outputBean.getModuleId());
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
		    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
	        		}
	        		//case deleted attribute
	        		for (SqlDesignOutput children : outputBean.getLstChildrens()) {
	        			boolean isExist = false;
	        			for (ExternalObjectAttribute attribute : externalObjectDefinition.getExternalObjectAttributes()) {
	        				if(BusinessDesignConst.OBJECT_TYPE_EXTERNAL_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getExternalObjectAttributeId(), children.getColumnId())){
	                        	isExist = true;
	                        	break;
	                        }
	        			}
	        			if(!isExist){
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0107, children.getSqlDesignOutputCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), outputBean.getSqlDesignOutputCode(),outputBean.getSqlDesignCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.SQL_DESIGN);
		    				problemList.setResourceId(Long.valueOf(outputBean.getSqlDesignId()));
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_SQL_DESIGN);
		    				problemList.setModuleId(outputBean.getModuleId());
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
		    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
                    }
	        	}else{
	        		//case added attribute
	        		for (ExternalObjectAttribute attribute : externalObjectDefinition.getExternalObjectAttributes()) {
	        			problemList = new ProblemList();
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0103, attribute.getExternalObjectAttributeCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), outputBean.getSqlDesignOutputCode(),outputBean.getSqlDesignCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.SQL_DESIGN);
	    				problemList.setResourceId(Long.valueOf(outputBean.getSqlDesignId()));
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_SQL_DESIGN);
	    				problemList.setModuleId(outputBean.getModuleId());
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
	    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
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
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0107, children.getSqlDesignOutputCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), outputBean.getSqlDesignOutputCode(),outputBean.getSqlDesignCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.SQL_DESIGN);
	    				problemList.setResourceId(Long.valueOf(outputBean.getSqlDesignId()));
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_SQL_DESIGN);
	    				problemList.setModuleId(outputBean.getModuleId());
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
	    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
	    				problemList.setCreatedBy(common.getCreatedBy());
	    				problemList.setCreatedDate(currentTime);
	    				lstProblemLists.add(problemList);
                    }
	        	}
	        }
        }
		return lstProblemLists;
	}
	
	private List<ProblemList> analyseInputBeanOfFunctionMethodWhenModify(ExternalObjectDefinition externalObjectDefinition, CommonModel common){
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		ProblemList problemList = new ProblemList();
		Timestamp currentTime = common.getCreatedDate();
		List<FunctionMethodInput> lstUsedInputBeanMapExOb = impactExternalObjectRepository.findFunctionMethodInputByUsingExternalObId(externalObjectDefinition.getExternalObjectDefinitionId());
		List<FunctionMethodInput> lstUsedInputBeanMapAttr = impactExternalObjectRepository.findFunctionMethodInputByParentInputUsingExternalObId(externalObjectDefinition.getExternalObjectDefinitionId());
		for (FunctionMethodInput parent : lstUsedInputBeanMapExOb) {
			List<FunctionMethodInput> lstChildren = new ArrayList<FunctionMethodInput>();
			for (FunctionMethodInput children : lstUsedInputBeanMapAttr) {
				if(FunctionCommon.equals(parent.getMethodInputId(), children.getParentFunctionMethodInputId())){
					lstChildren.add(children);
				}
			}
			parent.setLstChildrens(lstChildren);
        }
		
		//compare
		for (FunctionMethodInput inputBean : lstUsedInputBeanMapExOb) {
	        if(CollectionUtils.isNotEmpty(externalObjectDefinition.getExternalObjectAttributes())){
	        	if(CollectionUtils.isNotEmpty(inputBean.getLstChildrens())){
	        		//case add and modify
	        		for (ExternalObjectAttribute attribute : externalObjectDefinition.getExternalObjectAttributes()) {
	        			boolean isExist = false;
	        			FunctionMethodInput temp = new FunctionMethodInput();
	        			for (FunctionMethodInput children : inputBean.getLstChildrens()) {
	                        if(BusinessDesignConst.OBJECT_TYPE_EXTERNAL_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getExternalObjectAttributeId(), children.getObjectId())){
	                        	isExist = true;
	                        	temp = children;
	                        	break;
	                        }
                        }
	        			if(isExist){
	        				if(!FunctionCommon.equals(attribute.getDataType(),temp.getDataType()) || !FunctionCommon.equals(temp.isArrayFlgDisplay(), attribute.getArrayFlg())){
	        					//case modified attribute
		        				problemList = new ProblemList();
			    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0096, attribute.getExternalObjectAttributeCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), inputBean.getMethodInputCode(),inputBean.getFunctionMethodCode(),inputBean.getFunctionMasterCode()));
			    				problemList.setResourceType(DbDomainConst.ResourceType.FUNCTION_MASTER);
			    				problemList.setResourceId(Long.valueOf(inputBean.getFunctionMasterId()));
			    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
			    				problemList.setAutofixFlg(AutoFix.DISABLE);
			    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_FUNCTION_MASTER);
			    				problemList.setModuleId(null);
			    				problemList.setProjectId(common.getWorkingProjectId());
			    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
			    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
			    				problemList.setCreatedBy(common.getCreatedBy());
			    				problemList.setCreatedDate(currentTime);
			    				lstProblemLists.add(problemList);
	        				}
	        			}else{
	        				//case added attribute
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0094, attribute.getExternalObjectAttributeCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), inputBean.getMethodInputCode(),inputBean.getFunctionMethodCode(),inputBean.getFunctionMasterCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.FUNCTION_MASTER);
		    				problemList.setResourceId(Long.valueOf(inputBean.getFunctionMasterId()));
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_FUNCTION_MASTER);
		    				problemList.setModuleId(null);
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
		    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
	        		}
	        		//case deleted attribute
	        		for (FunctionMethodInput children : inputBean.getLstChildrens()) {
	        			boolean isExist = false;
	        			for (ExternalObjectAttribute attribute : externalObjectDefinition.getExternalObjectAttributes()) {
	        				if(BusinessDesignConst.OBJECT_TYPE_EXTERNAL_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getExternalObjectAttributeId(), children.getObjectId())){
	                        	isExist = true;
	                        	break;
	                        }
	        			}
	        			if(!isExist){
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0098, children.getMethodInputCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), inputBean.getMethodInputCode(),inputBean.getFunctionMethodCode(),inputBean.getFunctionMasterCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.FUNCTION_MASTER);
		    				problemList.setResourceId(Long.valueOf(inputBean.getFunctionMasterId()));
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_FUNCTION_MASTER);
		    				problemList.setModuleId(null);
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
		    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
                    }
	        	}else{
	        		//case added attribute
	        		for (ExternalObjectAttribute attribute : externalObjectDefinition.getExternalObjectAttributes()) {
	        			problemList = new ProblemList();
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0094, attribute.getExternalObjectAttributeCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), inputBean.getMethodInputCode(),inputBean.getFunctionMethodCode(),inputBean.getFunctionMasterCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.FUNCTION_MASTER);
	    				problemList.setResourceId(Long.valueOf(inputBean.getFunctionMasterId()));
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_FUNCTION_MASTER);
	    				problemList.setModuleId(null);
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
	    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
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
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0098, children.getMethodInputCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), inputBean.getMethodInputCode(),inputBean.getFunctionMethodCode(),inputBean.getFunctionMasterCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.FUNCTION_MASTER);
	    				problemList.setResourceId(Long.valueOf(inputBean.getFunctionMasterId()));
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_FUNCTION_MASTER);
	    				problemList.setModuleId(null);
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
	    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
	    				problemList.setCreatedBy(common.getCreatedBy());
	    				problemList.setCreatedDate(currentTime);
	    				lstProblemLists.add(problemList);
                    }
	        	}
	        }
        }
		return lstProblemLists;
	}
	
	private List<ProblemList> analyseOutputBeanOfFunctionMethodWhenModify(ExternalObjectDefinition externalObjectDefinition, CommonModel common){
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		ProblemList problemList = new ProblemList();
		Timestamp currentTime = common.getCreatedDate();
		List<FunctionMethodOutput> lstUsedOutputBeanMapExOb = impactExternalObjectRepository.findFunctionMethodOutputByUsingExternalObId(externalObjectDefinition.getExternalObjectDefinitionId());
		List<FunctionMethodOutput> lstUsedOutputBeanMapAttr = impactExternalObjectRepository.findFunctionMethodOutputByParentOutputUsingExternalObId(externalObjectDefinition.getExternalObjectDefinitionId());
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
	        if(CollectionUtils.isNotEmpty(externalObjectDefinition.getExternalObjectAttributes())){
	        	if(CollectionUtils.isNotEmpty(outputBean.getLstChildrens())){
	        		//case add and modify
	        		for (ExternalObjectAttribute attribute : externalObjectDefinition.getExternalObjectAttributes()) {
	        			boolean isExist = false;
	        			FunctionMethodOutput temp = new FunctionMethodOutput();
	        			for (FunctionMethodOutput children : outputBean.getLstChildrens()) {
	                        if(BusinessDesignConst.OBJECT_TYPE_EXTERNAL_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getExternalObjectAttributeId(), children.getObjectId())){
	                        	isExist = true;
	                        	temp = children;
	                        	break;
	                        }
                        }
	        			if(isExist){
	        				if(!FunctionCommon.equals(attribute.getDataType(),temp.getDataType()) || !FunctionCommon.equals(temp.isArrayFlgDisplay(), attribute.getArrayFlg())){
	        					//case modified attribute
		        				problemList = new ProblemList();
			    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0097, attribute.getExternalObjectAttributeCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), outputBean.getMethodOutputCode(),outputBean.getFunctionMethodCode(),outputBean.getFunctionMasterCode()));
			    				problemList.setResourceType(DbDomainConst.ResourceType.FUNCTION_MASTER);
			    				problemList.setResourceId(Long.valueOf(outputBean.getFunctionMasterId()));
			    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
			    				problemList.setAutofixFlg(AutoFix.DISABLE);
			    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_FUNCTION_MASTER);
			    				problemList.setModuleId(null);
			    				problemList.setProjectId(common.getWorkingProjectId());
			    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
			    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
			    				problemList.setCreatedBy(common.getCreatedBy());
			    				problemList.setCreatedDate(currentTime);
			    				lstProblemLists.add(problemList);
	        				}
	        			}else{
	        				//case added attribute
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0095, attribute.getExternalObjectAttributeCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), outputBean.getMethodOutputCode(),outputBean.getFunctionMethodCode(),outputBean.getFunctionMasterCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.FUNCTION_MASTER);
		    				problemList.setResourceId(Long.valueOf(outputBean.getFunctionMasterId()));
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_FUNCTION_MASTER);
		    				problemList.setModuleId(null);
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
		    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
	        		}
	        		//case deleted attribute
	        		for (FunctionMethodOutput children : outputBean.getLstChildrens()) {
	        			boolean isExist = false;
	        			for (ExternalObjectAttribute attribute : externalObjectDefinition.getExternalObjectAttributes()) {
	        				if(BusinessDesignConst.OBJECT_TYPE_EXTERNAL_ATTRIBUTE.equals(children.getObjectType()) && FunctionCommon.equals(attribute.getExternalObjectAttributeId(), children.getObjectId())){
	                        	isExist = true;
	                        	break;
	                        }
	        			}
	        			if(!isExist){
	        				problemList = new ProblemList();
		    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0099, children.getMethodOutputCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), outputBean.getMethodOutputCode(),outputBean.getFunctionMethodCode(),outputBean.getFunctionMasterCode()));
		    				problemList.setResourceType(DbDomainConst.ResourceType.FUNCTION_MASTER);
		    				problemList.setResourceId(Long.valueOf(outputBean.getFunctionMasterId()));
		    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		    				problemList.setAutofixFlg(AutoFix.DISABLE);
		    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_FUNCTION_MASTER);
		    				problemList.setModuleId(null);
		    				problemList.setProjectId(common.getWorkingProjectId());
		    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
		    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
		    				problemList.setCreatedBy(common.getCreatedBy());
		    				problemList.setCreatedDate(currentTime);
		    				lstProblemLists.add(problemList);
	        			}
                    }
	        	}else{
	        		//case added attribute
	        		for (ExternalObjectAttribute attribute : externalObjectDefinition.getExternalObjectAttributes()) {
	        			problemList = new ProblemList();
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0095, attribute.getExternalObjectAttributeCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), outputBean.getMethodOutputCode(),outputBean.getFunctionMethodCode(),outputBean.getFunctionMasterCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.FUNCTION_MASTER);
	    				problemList.setResourceId(Long.valueOf(outputBean.getFunctionMasterId()));
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_FUNCTION_MASTER);
	    				problemList.setModuleId(null);
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
	    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
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
	    				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0099, children.getMethodOutputCode(), externalObjectDefinition.getExternalObjectDefinitionCode(), outputBean.getMethodOutputCode(),outputBean.getFunctionMethodCode(),outputBean.getFunctionMasterCode()));
	    				problemList.setResourceType(DbDomainConst.ResourceType.FUNCTION_MASTER);
	    				problemList.setResourceId(Long.valueOf(outputBean.getFunctionMasterId()));
	    				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
	    				problemList.setAutofixFlg(AutoFix.DISABLE);
	    				problemList.setUrlId(DbDomainConst.ResourceURL.URL_FUNCTION_MASTER);
	    				problemList.setModuleId(null);
	    				problemList.setProjectId(common.getWorkingProjectId());
	    				problemList.setFromResourceType(DbDomainConst.FromResourceType.EXTERNAL_OBJECT);
	    				problemList.setFromResourceId(externalObjectDefinition.getExternalObjectDefinitionId());
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
