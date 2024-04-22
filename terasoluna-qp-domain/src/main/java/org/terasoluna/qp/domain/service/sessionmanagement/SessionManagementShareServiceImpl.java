package org.terasoluna.qp.domain.service.sessionmanagement;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.ProblemListMessageConst;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.ProblemList;
import org.terasoluna.qp.domain.model.SessionManagement;
import org.terasoluna.qp.domain.repository.businessdesign.BusinessDesignRepository;
import org.terasoluna.qp.domain.repository.impactchange.ImpactSessionManagementRepository;
import org.terasoluna.qp.domain.repository.problemlist.ProblemListRepository;
import org.terasoluna.qp.domain.repository.sessionmanagement.SessionManagementRepository;

@Service
public class SessionManagementShareServiceImpl implements SessionManagementShareService {

	@Inject
	BusinessDesignRepository businessDesignRepository;
	
	@Inject
	ProblemListRepository problemListRepository;
	
	@Inject
	SessionManagementRepository sessionManagementRepository;
	
	@Inject
	ImpactSessionManagementRepository impactSessionManagementRepository;
	
	@Override
    public void detectListAffectedWhenDeleteOfBatch(Long sessionManagementId, String sessionManagementCode, CommonModel common) {
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		List<InputBean> lstInputBeans = new ArrayList<InputBean>();
		List<OutputBean> lstOutputBeans = new ArrayList<OutputBean>();
		Timestamp systemDate = FunctionCommon.getCurrentTime();
		//detect impact change design
		lstInputBeans = impactSessionManagementRepository.findInputbeanBySessionId(sessionManagementId);
		lstOutputBeans = impactSessionManagementRepository.findOutputbeanBySessionId(sessionManagementId);
		ProblemList problemList = new ProblemList();
		if(CollectionUtils.isNotEmpty(lstInputBeans)){
			for (InputBean bean : lstInputBeans) {
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0076,sessionManagementCode,bean.getInputBeanCode(),bean.getBusinessLogicCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
				problemList.setResourceId(bean.getBusinessLogicId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
				problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
				problemList.setModuleId(bean.getModuleId());
				problemList.setProjectId(bean.getProjectId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.SESSION_MANAGEMENT);
				problemList.setFromResourceId(sessionManagementId);
				problemList.setCreatedDate(systemDate);
				lstProblemLists.add(problemList);
            }
		}
		if(CollectionUtils.isNotEmpty(lstOutputBeans)){
			for (OutputBean bean : lstOutputBeans) {
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0077,sessionManagementCode,bean.getOutputBeanCode(),bean.getBusinessLogicCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
				problemList.setResourceId(bean.getBusinessLogicId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
				problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
				problemList.setModuleId(bean.getModuleId());
				problemList.setProjectId(bean.getProjectId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.SESSION_MANAGEMENT);
				problemList.setFromResourceId(sessionManagementId);
				problemList.setCreatedDate(systemDate);
				lstProblemLists.add(problemList);
            }
		}
		//delete old problem of this business
		List<Long> lstFroms = new ArrayList<Long>();
		lstFroms.add(sessionManagementId);
		problemListRepository.deleteResourceType(DbDomainConst.FromResourceType.SESSION_MANAGEMENT, lstFroms);
		if(CollectionUtils.isNotEmpty(lstProblemLists)){
			problemListRepository.multiRegisterProblem(lstProblemLists);
		}
	    
    }

	@Override
    public void detectListAffectedWhenModifyOfBatch(Long sessionManagementId, CommonModel common) {
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		List<InputBean> lstInputBeans = new ArrayList<InputBean>();
		List<OutputBean> lstOutputBeans = new ArrayList<OutputBean>();
		Timestamp systemDate = FunctionCommon.getCurrentTime();
		//detect impact change design
		lstInputBeans = impactSessionManagementRepository.findInputbeanBySessionId(sessionManagementId);
		lstOutputBeans = impactSessionManagementRepository.findOutputbeanBySessionId(sessionManagementId);
		SessionManagement session = sessionManagementRepository.findById(sessionManagementId);
		ProblemList problemList = new ProblemList();
		if(CollectionUtils.isNotEmpty(lstInputBeans)){
			for (InputBean bean : lstInputBeans) {
				if(FunctionCommon.notEquals(bean.getDataType(), session.getDataType()) || FunctionCommon.notEquals(bean.getArrayFlg(), session.getArrayFlg())){
					problemList = new ProblemList();
					problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0074,session.getSessionManagementCode(),bean.getInputBeanCode(),bean.getBusinessLogicCode()));
					problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
					problemList.setResourceId(bean.getBusinessLogicId());
					problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
					problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
					problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
					problemList.setModuleId(bean.getModuleId());
					problemList.setProjectId(bean.getProjectId());
					problemList.setCreatedBy(common.getCreatedBy());
					problemList.setFromResourceType(DbDomainConst.FromResourceType.SESSION_MANAGEMENT);
					problemList.setFromResourceId(sessionManagementId);
					problemList.setCreatedDate(systemDate);
					lstProblemLists.add(problemList);
				}
            }
		}
		if(CollectionUtils.isNotEmpty(lstOutputBeans)){
			for (OutputBean bean : lstOutputBeans) {
				if(FunctionCommon.notEquals(bean.getDataType(), session.getDataType()) || FunctionCommon.notEquals(bean.getArrayFlg(), session.getArrayFlg())){
					problemList = new ProblemList();
					problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0075,session.getSessionManagementCode(),bean.getOutputBeanCode(),bean.getBusinessLogicCode()));
					problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
					problemList.setResourceId(bean.getBusinessLogicId());
					problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
					problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
					problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
					problemList.setModuleId(bean.getModuleId());
					problemList.setProjectId(bean.getProjectId());
					problemList.setCreatedBy(common.getCreatedBy());
					problemList.setFromResourceType(DbDomainConst.FromResourceType.SESSION_MANAGEMENT);
					problemList.setFromResourceId(sessionManagementId);
					problemList.setCreatedDate(systemDate);
					lstProblemLists.add(problemList);
				}
            }
		}
		//delete old problem of this business
		List<Long> lstFroms = new ArrayList<Long>();
		lstFroms.add(sessionManagementId);
		problemListRepository.deleteResourceType(DbDomainConst.FromResourceType.SESSION_MANAGEMENT, lstFroms);
		if(CollectionUtils.isNotEmpty(lstProblemLists)){
			problemListRepository.multiRegisterProblem(lstProblemLists);
		}
    }

}
