package org.terasoluna.qp.domain.service.screenitemstatus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.ModuleMessageConst;
import org.terasoluna.qp.domain.model.FormulaDefinition;
import org.terasoluna.qp.domain.model.FormulaDetail;
import org.terasoluna.qp.domain.model.ScreenForm;
import org.terasoluna.qp.domain.model.ScreenItemStatus;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.FormulaDefinitionRepository;
import org.terasoluna.qp.domain.repository.screenform.ScreenFormRepository;
import org.terasoluna.qp.domain.repository.screenitemstatus.ScreenItemStatusRepository;
import org.terasoluna.qp.domain.service.decisiontable.DecisionTableService;
import org.terasoluna.qp.domain.service.module.ModuleService;
import org.terasoluna.qp.domain.service.project.ProjectService;

@Service
@Transactional(rollbackFor = Exception.class)
public class ScreenItemStatusServiceImpl implements ScreenItemStatusService {

	@Inject
	ModuleService moduleService;
	
	@Inject
	ProjectService projectService;
	
	@Inject
	FormulaDefinitionRepository formulaDefinitionRepository;
	
	@Inject
	ScreenItemStatusRepository screenItemStatusRepository;
	
	@Inject
	ScreenFormRepository screenFormRepository;
	
	@Inject
	DecisionTableService decisionTableService;
	
	@Override
	public void modifyScreenItemStatus(List<FormulaDefinition> formulaDefinitions, Long projectId, Long screenId) {
		//Get last sequence formula definition
		/*List<FormulaDefinition> lstFormulaDefinition = formulaDefinitionRepository.getAllFormulaDefinition(projectId);*/
		Long sequencesFormulaDefinition = formulaDefinitionRepository.getSequencesFormulaDefinition(formulaDefinitions.size() - 1);
		Long startSequence = sequencesFormulaDefinition - (formulaDefinitions.size() - 1);
		
		for(FormulaDefinition formulaDefinition : formulaDefinitions) {
			formulaDefinition.setFormulaDefinitionId(startSequence);
			
			if(FunctionCommon.isNotEmpty(formulaDefinition.getFormulaDefinitionDetails())) {
				for(FormulaDetail formulaDetail : formulaDefinition.getFormulaDefinitionDetails()) {
					formulaDetail.setFormulaDefinitionId(startSequence);
				}
			}
			if(FunctionCommon.isNotEmpty(formulaDefinition.getScreenItemStatuses())) {
				for(ScreenItemStatus screenItemStatus : formulaDefinition.getScreenItemStatuses()) {
					screenItemStatus.setFormulaDefinitionId(startSequence);
				}
			}
			startSequence = startSequence + 1;
		}
		
		List<FormulaDetail> formulaDetails = new ArrayList<FormulaDetail>();
		List<ScreenItemStatus> screenItemStatuses = new ArrayList<ScreenItemStatus>();
		/*List<FormulaMethodInput> lstFormulaMethodInput = new ArrayList<FormulaMethodInput>();
		List<FormulaMethodOutput> lstFormulaMethodOutput = new ArrayList<FormulaMethodOutput>();*/
		
		for(FormulaDefinition formulaDefinition : formulaDefinitions) {
			if(FunctionCommon.isNotEmpty(formulaDefinition.getFormulaDefinitionDetails())) {
				for(FormulaDetail formulaDetail : formulaDefinition.getFormulaDefinitionDetails()) {
					formulaDetails.add(formulaDetail);
					/*if(formulaDetail.getFormulaMethodInputs() != null) {
						for(FormulaMethodInput fmi : formulaDetail.getFormulaMethodInputs()) {
							lstFormulaMethodInput.add(fmi);
						}
					}
					if(formulaDetail.getFormulaMethodOutputs() != null) {
						for(FormulaMethodOutput fmo : formulaDetail.getFormulaMethodOutputs()) {
							lstFormulaMethodOutput.add(fmo);
						}
					}*/
				}
			}
			if(FunctionCommon.isNotEmpty(formulaDefinition.getScreenItemStatuses())) {
				for(ScreenItemStatus screenItemStatus : formulaDefinition.getScreenItemStatuses()) {
					screenItemStatuses.add(screenItemStatus);
				}
			}
		}
		
		if (formulaDetails.size() > 0) {
			Long sequenceFormulaDetail = formulaDefinitionRepository.getSequencesFormulaDetail(formulaDetails.size() - 1);
			startSequence = sequenceFormulaDetail - (formulaDetails.size() - 1);
			for (FormulaDetail objDetail : formulaDetails) {
				objDetail.setFormulaDetailId(startSequence);
				startSequence = startSequence++;
			}
		}
		
		//Prepare to delete
		List<FormulaDetail> lstFormulaDetail = new ArrayList<FormulaDetail>();
		List<FormulaDefinition> lstFormulaDefinition = new ArrayList<FormulaDefinition>();
		List<ScreenForm> lstScreenForm = screenFormRepository.getScreenFormByScreenId(screenId);
		if(lstScreenForm != null) {
			lstFormulaDefinition = formulaDefinitionRepository.getFormulaDefinitionByScreenFormId(lstScreenForm);
		}
		if(lstFormulaDefinition != null) {
			lstFormulaDetail = formulaDefinitionRepository.getFormulaDetailByFormulaDefinition(lstFormulaDefinition);
		}
		
		formulaDefinitionRepository.deleleFormulaMethodInputByFormulaDetailId(lstFormulaDetail);
		formulaDefinitionRepository.deleleFormulaMethodOutputByFormulaDetailId(lstFormulaDetail);
		screenItemStatusRepository.deleteAllScreenItemStatusByFormulaDefinitionId(lstFormulaDefinition);
		formulaDefinitionRepository.deleteFormulaDetailsByFormulaDefinitionId(lstFormulaDefinition);
		formulaDefinitionRepository.deleteFormulaDefinitionByScreenFormId(lstScreenForm);
		
		//Insert
		formulaDefinitionRepository.registerFormulaDefinition(formulaDefinitions);
		if(registerFormulaDetail(formulaDetails) < 0) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0005)));
		}
		screenItemStatusRepository.registerScreenItemStatus(screenItemStatuses);
	}

	private int registerFormulaDetail(List<FormulaDetail> lstFormulaDetails){
		Long startSequence = 0L;
		if(lstFormulaDetails.size() >0){
			Long sequenceFormulaDetail = formulaDefinitionRepository.getSequencesFormulaDetail(lstFormulaDetails.size() - 1);
			startSequence = sequenceFormulaDetail - (lstFormulaDetails.size() - 1);
			for(FormulaDetail objDetail : lstFormulaDetails){
				objDetail.setFormulaDetailId(startSequence);
				startSequence ++;
			}
		}
		
		int result = formulaDefinitionRepository.registerFormulaDetails(lstFormulaDetails);
		return result;
	}

}
