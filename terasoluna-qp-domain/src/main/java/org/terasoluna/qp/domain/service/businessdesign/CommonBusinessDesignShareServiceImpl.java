package org.terasoluna.qp.domain.service.businessdesign;

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
import org.terasoluna.qp.domain.model.CommonComponent;
import org.terasoluna.qp.domain.model.CommonInputValue;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.CommonOutputValue;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.ProblemList;
import org.terasoluna.qp.domain.repository.businessdesign.BusinessDesignRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.CommonComponentRepository;
import org.terasoluna.qp.domain.repository.problemlist.ProblemListRepository;

@Service
public class CommonBusinessDesignShareServiceImpl implements CommonBusinessDesignShareService {

	@Inject
	CommonComponentRepository commonComponentRepository;
	
	@Inject
	BusinessDesignRepository businessDesignRepository;
	
	@Inject
	ProblemListRepository problemListRepository;
	
	@Inject
	BusinessDesignService businessDesignService;
	
	@Override
	public ImpactChangeOfCommonBlogic detectListAffectedWhenModify(BusinessDesign commonBlogic, CommonModel common, Boolean isRunBatch) {
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		ImpactChangeOfCommonBlogic impact = new ImpactChangeOfCommonBlogic();
		List<InputBean> inputBeans = commonBlogic.getLstInputBean();
		List<OutputBean> outputBeans = commonBlogic.getLstOutputBean();

		List<BusinessDesign> lstUsedBusinessDesign = new ArrayList<BusinessDesign>();
		List<BusinessDesign> lstTemps = new ArrayList<BusinessDesign>();
		Map<Long, BusinessDesign> mapComponentOfBlogic = new HashMap<Long, BusinessDesign>();

		// detect impact change design
		List<CommonComponent> lstCommonComponents = new ArrayList<CommonComponent>();
		List<CommonInputValue> lstCommonInputValues = new ArrayList<CommonInputValue>();
		List<CommonOutputValue> lstCommonOutputValues = new ArrayList<CommonOutputValue>();
		
		lstCommonComponents = commonComponentRepository.findCommonComponentByCommonBlogicId(commonBlogic.getBusinessLogicId());
		ProblemList problemList = new ProblemList();
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		if (CollectionUtils.isNotEmpty(lstCommonComponents)) {
			lstUsedBusinessDesign = businessDesignRepository.findAllBussinessLogicByCommonBlogicId(commonBlogic.getBusinessLogicId());
			lstCommonInputValues = commonComponentRepository.findCommonInputValueByCommonBlogicId(commonBlogic.getBusinessLogicId());
			lstCommonOutputValues = commonComponentRepository.findCommonOutputValueByCommonBlogicId(commonBlogic.getBusinessLogicId());
			// mapping
			List<CommonInputValue> lstCurrentInputValues = new ArrayList<CommonInputValue>();
			List<CommonOutputValue> lstCurrentOutputValues = new ArrayList<CommonOutputValue>();
			for (CommonComponent component : lstCommonComponents) {
				lstCurrentInputValues = new ArrayList<CommonInputValue>();
				if (CollectionUtils.isNotEmpty(lstCommonInputValues)) {
					for (CommonInputValue inputValues : lstCommonInputValues) {
						if (inputValues.getCommonComponentId().equals(component.getCommonComponentId())) {
							lstCurrentInputValues.add(inputValues);
						}
					}
				}
				component.setParameterInputBeans(lstCurrentInputValues);
				lstCurrentOutputValues = new ArrayList<CommonOutputValue>();
				if (CollectionUtils.isNotEmpty(lstCommonOutputValues)) {
					for (CommonOutputValue outputValues : lstCommonOutputValues) {
						if (outputValues.getCommonComponentId().equals(component.getCommonComponentId())) {
							lstCurrentOutputValues.add(outputValues);
						}
					}
				}
				component.setParameterOutputBeans(lstCurrentOutputValues);
				if (CollectionUtils.isNotEmpty(lstUsedBusinessDesign)) {
					for (BusinessDesign temp : lstUsedBusinessDesign) {
						if (FunctionCommon.equals(component.getBusinessLogicIdRefer(), temp.getBusinessLogicId())) {
							mapComponentOfBlogic.put(component.getCommonComponentId(), temp);
							break;
						}
					}
				}
			}
			// compare with current design
			Boolean existFlag = false;
			BusinessDesign businessDesign = new BusinessDesign();
			Boolean isImpact = false;
			for (CommonComponent component : lstCommonComponents) {
				businessDesign = mapComponentOfBlogic.getOrDefault(component.getCommonComponentId(), new BusinessDesign());
				// compare input bean of common blogic
				for (InputBean inputBean : inputBeans) {
					existFlag = false;
					CommonInputValue mappingValue = null;
					for (CommonInputValue inputValue : component.getParameterInputBeans()) {
						if (FunctionCommon.equals(inputValue.getInputBeanId(), inputBean.getInputBeanId())) {
							existFlag = true;
							mappingValue = inputValue;
							break;
						}
					}
					if (existFlag) {
						if (mappingValue != null && (FunctionCommon.notEquals(mappingValue.getDataType(), inputBean.getDataType()) || FunctionCommon.notEquals(mappingValue.getArrayFlg(), inputBean.getArrayFlg()))) {
							// modify input bean
							if (isRunBatch) {
								problemList = new ProblemList();
								problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0079, inputBean.getInputBeanCode(), commonBlogic.getBusinessLogicCode(), component.getLabel(), businessDesign.getBusinessLogicCode()));
								problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
								problemList.setResourceId(businessDesign.getBusinessLogicId());
								problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
								problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
								problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
								problemList.setModuleId(businessDesign.getModuleId());
								problemList.setProjectId(businessDesign.getProjectId());
								problemList.setCreatedBy(common.getCreatedBy());
								problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_BLOGIC);
								problemList.setFromResourceId(commonBlogic.getBusinessLogicId());
								problemList.setCreatedDate(currentTime);
								lstProblemLists.add(problemList);
							}
							isImpact = true;
						}
					} else {
						// new input bean
						if (isRunBatch) {
							problemList = new ProblemList();
							problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0078, inputBean.getInputBeanCode(), commonBlogic.getBusinessLogicCode(), component.getLabel(), businessDesign.getBusinessLogicCode()));
							problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
							problemList.setResourceId(businessDesign.getBusinessLogicId());
							problemList.setProblemType(DbDomainConst.ProblemType.MISS_SETTING);
							problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
							problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
							problemList.setModuleId(businessDesign.getModuleId());
							problemList.setProjectId(businessDesign.getProjectId());
							problemList.setCreatedBy(common.getCreatedBy());
							problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_BLOGIC);
							problemList.setFromResourceId(commonBlogic.getBusinessLogicId());
							problemList.setCreatedDate(currentTime);
							lstProblemLists.add(problemList);
						}
						isImpact = true;
					}
				}
				// compare output bean of common blogic
				for (OutputBean outputBean : outputBeans) {
					existFlag = false;
					CommonOutputValue mappingValue = null;
					for (CommonOutputValue outputValue : component.getParameterOutputBeans()) {
						if (FunctionCommon.equals(outputValue.getOutputBeanId(), outputBean.getOutputBeanId())) {
							existFlag = true;
							mappingValue = outputValue;
							break;
						}
					}
					if (existFlag) {
						if (mappingValue != null && (FunctionCommon.notEquals(mappingValue.getDataType(), outputBean.getDataType()) || FunctionCommon.notEquals(mappingValue.getArrayFlg(), outputBean.getArrayFlg()))) {
							// modify output bean
							if (isRunBatch) {
								problemList = new ProblemList();
								problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0080, outputBean.getOutputBeanCode(), commonBlogic.getBusinessLogicCode(), component.getLabel(), businessDesign.getBusinessLogicCode()));
								problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
								problemList.setResourceId(businessDesign.getBusinessLogicId());
								problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
								problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
								problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
								problemList.setModuleId(businessDesign.getModuleId());
								problemList.setProjectId(businessDesign.getProjectId());
								problemList.setCreatedBy(common.getCreatedBy());
								problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_BLOGIC);
								problemList.setFromResourceId(commonBlogic.getBusinessLogicId());
								problemList.setCreatedDate(currentTime);
								lstProblemLists.add(problemList);
							}
							isImpact = true;
						}
					}
				}
				if (isImpact && !lstTemps.contains(businessDesign)) {
					lstTemps.add(businessDesign);
				}
			}
		}
		if (CollectionUtils.isNotEmpty(lstTemps)) {
			impact.setImpactFlag(true);
		}
		impact.setLstUsedBusinessDesign(lstTemps);
		if (Boolean.TRUE.equals(isRunBatch)) {
			// delete mapping data
			commonComponentRepository.deleteCommonInputValueByCommonBlogicId(commonBlogic.getBusinessLogicId());
			commonComponentRepository.deleteCommonOutputValueByCommonBlogicId(commonBlogic.getBusinessLogicId());

			// delete old problem of this business
			List<Long> lstFroms = new ArrayList<Long>();
			lstFroms.add(commonBlogic.getBusinessLogicId());
			problemListRepository.deleteResourceType(DbDomainConst.FromResourceType.COMMON_BLOGIC, lstFroms);
			if (CollectionUtils.isNotEmpty(lstProblemLists)) {
				problemListRepository.multiRegisterProblem(lstProblemLists);
			}
		}

		return impact;
	}

	@Override
	public void detectListAffectedWhenModifyOfBatch(Long businessLogicId, CommonModel common) {
		BusinessDesign businessDesign = businessDesignService.findBusinessLogicInformation(businessLogicId, true, common,false);
	    if (businessDesign != null) {
	    	this.detectListAffectedWhenModify(businessDesign, common, true);
	    }
	}

	@Override
	public ImpactChangeOfCommonBlogic detectListAffectedWhenDelete(BusinessDesign commonBlogic, CommonModel common, Boolean isRunBatch) {
		ImpactChangeOfCommonBlogic impact = new ImpactChangeOfCommonBlogic();
		List<BusinessDesign> lstUsedBusinessDesign = new ArrayList<BusinessDesign>();
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		List<CommonComponent> lstCommonComponents = new ArrayList<CommonComponent>();
		Timestamp systemDate = FunctionCommon.getCurrentTime();
		//detect impact change design
		
		lstCommonComponents = commonComponentRepository.findCommonComponentByCommonBlogicId(commonBlogic.getBusinessLogicId());
		if(CollectionUtils.isNotEmpty(lstCommonComponents)){
			lstUsedBusinessDesign = businessDesignRepository.findAllBussinessLogicByCommonBlogicId(commonBlogic.getBusinessLogicId());
			//mapping
			BusinessDesign businessDesign = new BusinessDesign();
			ProblemList problemList = new ProblemList();
			for (CommonComponent component : lstCommonComponents) {
				for (BusinessDesign temp : lstUsedBusinessDesign){
            		if(FunctionCommon.equals(component.getBusinessLogicIdRefer(),temp.getBusinessLogicId())){
            			businessDesign = temp;
            			break;
            		}
            	}
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0081,commonBlogic.getBusinessLogicCode(),component.getLabel(), businessDesign.getBusinessLogicCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
				problemList.setResourceId(businessDesign.getBusinessLogicId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
				problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
				problemList.setModuleId(businessDesign.getModuleId());
				problemList.setProjectId(businessDesign.getProjectId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.COMMON_BLOGIC);
				problemList.setFromResourceId(commonBlogic.getBusinessLogicId());
				problemList.setCreatedDate(systemDate);
				lstProblemLists.add(problemList);
            }
			
			//delete mapping data
			commonComponentRepository.deleteCommonInputValueByCommonComponent(lstCommonComponents);
			commonComponentRepository.deleteCommonOutputValueByCommonComponent(lstCommonComponents);
		}
		if(isRunBatch){
			//delete old problem of this business
			List<Long> lstFroms = new ArrayList<Long>();
			lstFroms.add(commonBlogic.getBusinessLogicId());
			problemListRepository.deleteResourceType(DbDomainConst.FromResourceType.COMMON_BLOGIC, lstFroms);
			if(CollectionUtils.isNotEmpty(lstProblemLists))
				problemListRepository.multiRegisterProblem(lstProblemLists);
		}
		if(CollectionUtils.isNotEmpty(lstUsedBusinessDesign)){
			impact.setImpactFlag(true);
		}
		impact.setLstUsedBusinessDesign(lstUsedBusinessDesign);
		return impact;
	}

	@Override
	public void detectListAffectedWhenDeleteOfBatch(Long businessLogicId, String businessLogicCode, CommonModel common) {
		BusinessDesign businessDesign = new BusinessDesign();
		businessDesign.setBusinessLogicId(businessLogicId);
		businessDesign.setBusinessLogicCode(businessLogicCode);
	    if (businessDesign != null) {
	    	this.detectListAffectedWhenDelete(businessDesign, common, true);
	    }
	}

}
