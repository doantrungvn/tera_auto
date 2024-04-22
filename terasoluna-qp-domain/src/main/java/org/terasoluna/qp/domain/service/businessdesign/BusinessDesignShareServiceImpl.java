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
import org.terasoluna.qp.app.common.constants.DbDomainConst.FromResourceType;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.ProblemListMessageConst;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.ExceptionComponent;
import org.terasoluna.qp.domain.model.ExceptionDetail;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.NavigatorComponent;
import org.terasoluna.qp.domain.model.NavigatorDetail;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.ProblemList;
import org.terasoluna.qp.domain.model.ScreenAction;
import org.terasoluna.qp.domain.model.ScreenActionParam;
import org.terasoluna.qp.domain.model.ScreenItem;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.repository.businessdesign.BusinessDesignRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.BusinessCheckComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.CommonComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.ExceptionComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.ExecutionComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.NavigationComponentRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableRepository;
import org.terasoluna.qp.domain.repository.generatesourcecode.GenerateSourceCodeRepository;
import org.terasoluna.qp.domain.repository.problemlist.ProblemListRepository;
import org.terasoluna.qp.domain.repository.screenactionparam.ScreenActionParamRepository;
import org.terasoluna.qp.domain.repository.screenitem.ScreenItemRepository;

/**
 * @author hunghx
 *
 */
@Service
public class BusinessDesignShareServiceImpl implements BusinessDesignShareService {

	@Inject
	private BusinessDesignRepository businessDesignRepository;

	@Inject
	private ExecutionComponentRepository executionComponentRepository;

	@Inject
	private CommonComponentRepository commonComponentRepository;

	@Inject
	private DecisionTableRepository decisionTableRepository;

	@Inject
	private BusinessCheckComponentRepository businessCheckComponentRepository;
	
	@Inject
	private GenerateSourceCodeRepository generateSourceCodeRepository;
	
	@Inject
	private ProblemListRepository problemListRepository;
	
	@Inject
	private NavigationComponentRepository navigationComponentRepository;
	
	@Inject
	private ExceptionComponentRepository exceptionComponentRepository;
	
	@Inject
	private ScreenItemRepository screenItemRepository;
	
	@Inject
	private BusinessDesignService businessDesignService;
	
	@Inject
	private ScreenActionParamRepository screenActionParamRepository;

	@Override
	public void fix(ProblemList problemList) {
		if (problemList != null) {
			switch (problemList.getFromResourceType()) {
				case FromResourceType.SQL_DESIGN_INPUT:
					executionComponentRepository.deleteExecutionInputBySqlDesignInputId(problemList.getFromResourceId());
					break;
				case FromResourceType.SQL_DESIGN_OUTPUT:
					executionComponentRepository.deleteExecutionOutputBySqlDesignOutputId(problemList.getFromResourceId());
					break;
				case FromResourceType.BLOGIC_COMMON_INPUTBEAN:
					commonComponentRepository.deleteCommonInputById(problemList.getFromResourceId(), problemList.getResourceId());
					break;
				case FromResourceType.BLOGIC_COMMON_OUTPUTBEAN:
					commonComponentRepository.deleteCommonOutputById(problemList.getFromResourceId(), problemList.getResourceId());
					break;
				case FromResourceType.BLOGIC_NAVIGATOR_INPUTBEAN:
					navigationComponentRepository.deleteNavigatorInputById(problemList.getFromResourceId(), problemList.getResourceId());
					break;
				case FromResourceType.DECISION_TABLE_INPUT:
					decisionTableRepository.deleteInputValue(problemList.getFromResourceId());
					break;
				case FromResourceType.DECISION_TABLE_OUTPUT:
					decisionTableRepository.deleteOutputValue(problemList.getFromResourceId());
					break;
			}
		}
	}

	@Override
	public void autoUpdateAffectObjectDefinitionBean(List<TableDesignDetails> lstTableDetails) {
		businessDesignRepository.updateAffectObjectDefinitionBean(lstTableDetails);
	}

	@Override
	public void autoUpdateAffectOutputBean(List<TableDesignDetails> lstTableDetails) {
		businessDesignRepository.updateAffectOutputBean(lstTableDetails);
	}

	@Override
	public void autoDeleteAffectObjectDefinitionBeanByDeleleTableDesign(Long tableDesignId) {
		businessDesignRepository.deleteAffectObjectDefinitionBeanByDeleleTableDesign(tableDesignId);
	}

	@Override
	public void autoDeleteAffectOutputBeanDeleleTableDesign(Long tableDesignId) {
		businessDesignRepository.deleteAffectOutputBeanDeleleTableDesign(tableDesignId);
	}

	/* (non-Javadoc)
	 * @see org.terasoluna.qp.domain.service.businessdesign.BusinessDesignShareService#autoDeleteAffectObjectDefinitionBean(java.util.List)
	 */
	@Override
	public void autoDeleteAffectObjectDefinitionBean(List<TableDesignDetails> lstTableDetails) {
		businessDesignRepository.autoDeleteAffectObjectDefinitionBean(lstTableDetails);
	}

	@Override
	public void autoDeleteAffectOutputBean(List<TableDesignDetails> lstTableDetails) {
		businessDesignRepository.autoDeleteAffectOutputBean(lstTableDetails);
	}

	@Override
	public List<ObjectDefinition> findAllInforOfParenObjDefinitionBeanById(Long tableDesignId) {
		return businessDesignRepository.findAllInforOfParenObjDefinitionBeanById(tableDesignId);
	}

	@Override
	public List<OutputBean> findAllInforOfParenOutBeanById(Long tableDesignId) {
		return businessDesignRepository.findAllInforOfParenOutBeanById(tableDesignId);
	}

	@Override
	public void registerListObjectDefinition(List<ObjectDefinition> objectdefinitionItems) {
		businessDesignRepository.registerListObjectDefinition(objectdefinitionItems);
	}

	@Override
	public void registerListOutputBean(List<OutputBean> outputbeanItems) {
		businessDesignRepository.registerListOutputBean(outputbeanItems);
	}

	@Override
	public void autoUpdateAffectBusinessCheckComp(List<TableDesignDetails> lstTableDetail) {
		businessCheckComponentRepository.autoUpdateAffectBusinessCheckComp(lstTableDetail);
	}

	@Override
	public void deleteAffectBusinessCheckComp(List<TableDesignDetails> lstTableDetails) {
		businessCheckComponentRepository.deleteAffectBusinessCheckComp(lstTableDetails);
	}

	@Override
	public void deleteAffectBusinessCheckCompById(Long tableDesignId) {
		businessCheckComponentRepository.deleteAffectBusinessCheckCompById(tableDesignId);
	}

	@Override
	public void updateDesignStatusOfAffectedBlogic(List<Long> lstAffectedBlogic,Long updatedBy,Timestamp updatedDate) {
		businessDesignRepository.updateDesignStatusOfAffectedBlogic(lstAffectedBlogic, updatedBy, updatedDate);
	}

	@Override
	public BusinessDesign findInputBeanOfBusinessLogic(Long businessDesignId,Long workingLanguageId,Long workingProjectId) {
		BusinessDesign businessLogic = businessDesignRepository.findBusinessLogicInformation(businessDesignId, workingLanguageId, workingProjectId);
		Map<String, String> mapTableIndex = new HashMap<String, String>();
		Map<String, Integer> mapSequence = new HashMap<String, Integer>();
		if (businessLogic != null && businessLogic.getBusinessLogicId() != null) {
			List<InputBean> lstInputBeans = businessDesignRepository.findInputBean(businessDesignId, workingLanguageId, workingProjectId);
			for (InputBean in : lstInputBeans) {
				String currentGroup = "";
				if (in.getParentInputBeanId() != null) {
					currentGroup = mapTableIndex.get(in.getParentInputBeanId());
				}
				in.setGroupId(currentGroup);
				int maxIndex = mapSequence.getOrDefault(in.getGroupId(), 0);
				maxIndex++;
				if (in.getParentInputBeanId() == null) {
					in.setTableIndex(String.valueOf(maxIndex));
				} else {
					in.setTableIndex(currentGroup + "." + maxIndex);
				}
				mapTableIndex.put(in.getInputBeanId(), in.getTableIndex());
				mapSequence.put(in.getGroupId(), maxIndex);
			}
			businessLogic.setLstInputBean(lstInputBeans);
		} else {
			businessLogic = new BusinessDesign();
		}

		return businessLogic;
	}

	@Override
	public BusinessDesign findInputBeanOfBusinessLogicForGensource(Long businessDesignId,Long workingLanguageId,Long workingProjectId) {
		BusinessDesign businessLogic = businessDesignRepository.findBusinessLogicInformation(businessDesignId, workingLanguageId, workingProjectId);
		if (businessLogic != null && businessLogic.getBusinessLogicId() != null) {
			List<InputBean> lstInputBeans = generateSourceCodeRepository.findAllInputBeanByBusinessId(businessDesignId);
			businessLogic.setLstInputBean(lstInputBeans);
		} else {
			businessLogic = new BusinessDesign();
		}

		return businessLogic;
	}

	@Override
    public ImpactChangeOfStandardBlogic detectListAffectedWhenModify(BusinessDesign blogic, CommonModel common, Boolean isRunBatch) {
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		ImpactChangeOfStandardBlogic impact = new ImpactChangeOfStandardBlogic();
		List<InputBean> inputBeans = blogic.getLstInputBean();

		List<BusinessDesign> lstUsedBusinessDesign = new ArrayList<BusinessDesign>();
		List<BusinessDesign> lstTemps = new ArrayList<BusinessDesign>();
		Map<Long, BusinessDesign> mapComponentOfBlogic = new HashMap<Long, BusinessDesign>();

		// detect impact change design
		List<NavigatorComponent> lstNavigatorCommonComponents = new ArrayList<NavigatorComponent>();
		List<NavigatorDetail> lstNavigatorInputValues = new ArrayList<NavigatorDetail>();
		List<ExceptionComponent> lstExceptionComponents = new ArrayList<ExceptionComponent>();
		List<ExceptionDetail> lstExceptionInputValues = new ArrayList<ExceptionDetail>();
		
		lstNavigatorCommonComponents = navigationComponentRepository.findNavigatorComponentByNavigatorBlogicId(blogic.getBusinessLogicId());
		lstExceptionComponents = exceptionComponentRepository.findExceptionComponentByNavigatorBlogicId(blogic.getBusinessLogicId());
		ProblemList problemList = new ProblemList();
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		lstUsedBusinessDesign = businessDesignRepository.findAllBussinessLogicByNavigatorBlogicId(blogic.getBusinessLogicId());
		List<ScreenItem> lstUsedScreenItems = screenItemRepository.getAllScreenItemByBusinessLogicId(blogic.getBusinessLogicId(), common.getWorkingLanguageId());
		
		Boolean existFlag = false;
		Boolean isImpact = false;
		List<ScreenItem> lstImpactScreenItems = new ArrayList<ScreenItem>();
		
		if (CollectionUtils.isNotEmpty(lstNavigatorCommonComponents) && CollectionUtils.isNotEmpty(inputBeans)) {
			lstNavigatorInputValues = navigationComponentRepository.findNavigatorDetailByNavigatorBlogicId(blogic.getBusinessLogicId());
			// mapping
			List<NavigatorDetail> lstCurrentInputValues = new ArrayList<NavigatorDetail>();
			for (NavigatorComponent component : lstNavigatorCommonComponents) {
				lstCurrentInputValues = new ArrayList<NavigatorDetail>();
				if (CollectionUtils.isNotEmpty(lstNavigatorInputValues)) {
					for (NavigatorDetail inputValues : lstNavigatorInputValues) {
						if (FunctionCommon.equals(component.getNavigatorComponentId(), inputValues.getNavigatorComponentId())) {
							lstCurrentInputValues.add(inputValues);
						}
					}
				}
				component.setParameterInputBeans(lstCurrentInputValues);
				if (CollectionUtils.isNotEmpty(lstUsedBusinessDesign)) {
					for (BusinessDesign temp : lstUsedBusinessDesign) {
						if (FunctionCommon.equals(component.getBusinessLogicId(), temp.getBusinessLogicId())) {
							mapComponentOfBlogic.put(component.getSequenceLogicId(), temp);
							break;
						}
					}
				}
			}
			// compare with current design
			BusinessDesign businessDesign = new BusinessDesign();
			for (NavigatorComponent component : lstNavigatorCommonComponents) {
				isImpact = false;
				businessDesign = mapComponentOfBlogic.getOrDefault(component.getSequenceLogicId(), new BusinessDesign());
				// compare input bean of standard blogic
				for (InputBean inputBean : inputBeans) {
					existFlag = false;
					NavigatorDetail mappingValue = null;
					if(CollectionUtils.isNotEmpty(component.getParameterInputBeans())){
						for (NavigatorDetail inputValue : component.getParameterInputBeans()) {
							if (FunctionCommon.equals(inputValue.getInputBeanId(), inputBean.getInputBeanId())) {
								existFlag = true;
								mappingValue = inputValue;
								break;
							}
						}
					}
					if (existFlag) {
						if (mappingValue != null && (FunctionCommon.notEquals(mappingValue.getDataType(), inputBean.getDataType()) || FunctionCommon.notEquals(mappingValue.getArrayFlg(), inputBean.getArrayFlg()))) {
							// modify input bean
							if (isRunBatch) {
								problemList = new ProblemList();
								problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0084, inputBean.getInputBeanCode(), blogic.getBusinessLogicCode(), component.getLabel(), businessDesign.getBusinessLogicCode()));
								problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
								problemList.setResourceId(businessDesign.getBusinessLogicId());
								problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
								problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
								problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
								problemList.setModuleId(businessDesign.getModuleId());
								problemList.setProjectId(businessDesign.getProjectId());
								problemList.setCreatedBy(common.getCreatedBy());
								problemList.setFromResourceType(DbDomainConst.FromResourceType.NAVIGATOR_BLOGIC);
								problemList.setFromResourceId(blogic.getBusinessLogicId());
								problemList.setCreatedDate(currentTime);
								lstProblemLists.add(problemList);
							}
							isImpact = true;
						}
					} else {
						// new input bean
						if (isRunBatch) {
							problemList = new ProblemList();
							problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0082, inputBean.getInputBeanCode(), blogic.getBusinessLogicCode(), component.getLabel(), businessDesign.getBusinessLogicCode()));
							problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
							problemList.setResourceId(businessDesign.getBusinessLogicId());
							problemList.setProblemType(DbDomainConst.ProblemType.MISS_SETTING);
							problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
							problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
							problemList.setModuleId(businessDesign.getModuleId());
							problemList.setProjectId(businessDesign.getProjectId());
							problemList.setCreatedBy(common.getCreatedBy());
							problemList.setFromResourceType(DbDomainConst.FromResourceType.NAVIGATOR_BLOGIC);
							problemList.setFromResourceId(blogic.getBusinessLogicId());
							problemList.setCreatedDate(currentTime);
							lstProblemLists.add(problemList);
						}
						isImpact = true;
					}
				}
				if (isImpact && !lstTemps.contains(businessDesign)) {
					lstTemps.add(businessDesign);
				}
			}
		}
		if (CollectionUtils.isNotEmpty(lstExceptionComponents) && CollectionUtils.isNotEmpty(inputBeans)) {
			lstExceptionInputValues = exceptionComponentRepository.findExceptionDetailByNavigatorBlogicId(blogic.getBusinessLogicId());
			// mapping
			List<ExceptionDetail> lstCurrentInputValues = new ArrayList<ExceptionDetail>();
			for (ExceptionComponent component : lstExceptionComponents) {
				lstCurrentInputValues = new ArrayList<ExceptionDetail>();
				if (CollectionUtils.isNotEmpty(lstExceptionInputValues)) {
					for (ExceptionDetail inputValues : lstExceptionInputValues) {
						if (FunctionCommon.equals(component.getExceptionComponentId(), inputValues.getExceptionComponentId())) {
							lstCurrentInputValues.add(inputValues);
						}
					}
				}
				component.setParameterInputBeans(lstCurrentInputValues);
				if (CollectionUtils.isNotEmpty(lstUsedBusinessDesign)) {
					for (BusinessDesign temp : lstUsedBusinessDesign) {
						if (FunctionCommon.equals(component.getBusinessLogicId(), temp.getBusinessLogicId())) {
							mapComponentOfBlogic.put(component.getSequenceLogicId(), temp);
							break;
						}
					}
				}
			}
			// compare with current design
			BusinessDesign businessDesign = new BusinessDesign();
			for (ExceptionComponent component : lstExceptionComponents) {
				isImpact = false;
				businessDesign = mapComponentOfBlogic.getOrDefault(component.getSequenceLogicId(), new BusinessDesign());
				// compare input bean of standard blogic
				for (InputBean inputBean : inputBeans) {
					existFlag = false;
					ExceptionDetail mappingValue = null;
					if(CollectionUtils.isNotEmpty(component.getParameterInputBeans())){
						for (ExceptionDetail inputValue : component.getParameterInputBeans()) {
							if (FunctionCommon.equals(inputValue.getInputBeanId(), inputBean.getInputBeanId())) {
								existFlag = true;
								mappingValue = inputValue;
								break;
							}
						}
					}
					if (existFlag) {
						if (mappingValue != null && (FunctionCommon.notEquals(mappingValue.getDataType(), inputBean.getDataType()) || FunctionCommon.notEquals(mappingValue.getArrayFlg(), inputBean.getArrayFlg()))) {
							// modify input bean
							if (isRunBatch) {
								problemList = new ProblemList();
								problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0085, inputBean.getInputBeanCode(), blogic.getBusinessLogicCode(), component.getLabel(), businessDesign.getBusinessLogicCode()));
								problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
								problemList.setResourceId(businessDesign.getBusinessLogicId());
								problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
								problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
								problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
								problemList.setModuleId(businessDesign.getModuleId());
								problemList.setProjectId(businessDesign.getProjectId());
								problemList.setCreatedBy(common.getCreatedBy());
								problemList.setFromResourceType(DbDomainConst.FromResourceType.NAVIGATOR_BLOGIC);
								problemList.setFromResourceId(blogic.getBusinessLogicId());
								problemList.setCreatedDate(currentTime);
								lstProblemLists.add(problemList);
							}
							isImpact = true;
						}
					} else {
						// new input bean
						if (isRunBatch) {
							problemList = new ProblemList();
							problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0083, inputBean.getInputBeanCode(), blogic.getBusinessLogicCode(), component.getLabel(), businessDesign.getBusinessLogicCode()));
							problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
							problemList.setResourceId(businessDesign.getBusinessLogicId());
							problemList.setProblemType(DbDomainConst.ProblemType.MISS_SETTING);
							problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
							problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
							problemList.setModuleId(businessDesign.getModuleId());
							problemList.setProjectId(businessDesign.getProjectId());
							problemList.setCreatedBy(common.getCreatedBy());
							problemList.setFromResourceType(DbDomainConst.FromResourceType.NAVIGATOR_BLOGIC);
							problemList.setFromResourceId(blogic.getBusinessLogicId());
							problemList.setCreatedDate(currentTime);
							lstProblemLists.add(problemList);
						}
						isImpact = true;
					}
				}
				if (isImpact && !lstTemps.contains(businessDesign)) {
					lstTemps.add(businessDesign);
				}
			}
		}
		
		//detect with screen item
		if(CollectionUtils.isNotEmpty(lstUsedScreenItems)){
			List<ScreenActionParam> lstScreenActionParams = screenActionParamRepository.findAllActionParamByLstScreenItem(lstUsedScreenItems);
			// compare input bean of standard blogic
			for (ScreenItem item : lstUsedScreenItems) {
				ScreenAction action = new ScreenAction();
				List<ScreenActionParam> lstCurrentParams = new ArrayList<ScreenActionParam>();
				if(CollectionUtils.isNotEmpty(lstScreenActionParams)){
					for (ScreenActionParam params : lstScreenActionParams){
						if(FunctionCommon.equals(params.getArrayFlg(), 1)){
							params.setArrayFlgOfJava(true);
						}else{
							params.setArrayFlgOfJava(false);
						}
						if(FunctionCommon.equals(params.getScreenActionId(), item.getScreenActionId())){
							lstCurrentParams.add(params);
						}
					}
				}
				action.setListScreenParameters(lstCurrentParams);
				item.setScreenAction(action);
            }
			
			for (ScreenItem item : lstUsedScreenItems) {
				isImpact = false;
				if(CollectionUtils.isNotEmpty(inputBeans)){
					ScreenActionParam currenValue = new ScreenActionParam();
					for (InputBean inputBean : inputBeans) {
						currenValue = new ScreenActionParam();
						existFlag = false;
						if(item.getScreenAction() != null && CollectionUtils.isNotEmpty(item.getScreenAction().getListScreenParameters())){
							for (ScreenActionParam param : item.getScreenAction().getListScreenParameters()) {
		                        if(FunctionCommon.equals(param.getActionParamCode(), inputBean.getInputBeanId())){
		                        	existFlag = true;
		                        	currenValue = param;
		                        	break;
		                        }
	                        }
						}
						if(existFlag){
							if(FunctionCommon.notEquals(inputBean.getArrayFlg(), currenValue.getArrayFlgOfJava()) || FunctionCommon.notEquals(inputBean.getDataType(), currenValue.getDataType())){
								if (isRunBatch) {
									problemList = new ProblemList();
									problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0089, inputBean.getInputBeanCode(), blogic.getBusinessLogicCode(), item.getItemCode(), item.getScreenCode()));
									problemList.setResourceType(DbDomainConst.ResourceType.SCREEN_DESIGN);
									problemList.setResourceId(item.getScreenId());
									problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
									problemList.setUrlId(DbDomainConst.ResourceURL.URL_SCREEN_DESIGN);
									problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
									problemList.setModuleId(item.getModuleId());
									problemList.setProjectId(blogic.getProjectId());
									problemList.setCreatedBy(common.getCreatedBy());
									problemList.setFromResourceType(DbDomainConst.FromResourceType.NAVIGATOR_BLOGIC);
									problemList.setFromResourceId(blogic.getBusinessLogicId());
									problemList.setCreatedDate(currentTime);
									lstProblemLists.add(problemList);
								}
								isImpact = true;
							}
						}else{
							if (isRunBatch) {
								problemList = new ProblemList();
								problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0088, inputBean.getInputBeanCode(), blogic.getBusinessLogicCode(), item.getItemCode(), item.getScreenCode()));
								problemList.setResourceType(DbDomainConst.ResourceType.SCREEN_DESIGN);
								problemList.setResourceId(item.getScreenId());
								problemList.setProblemType(DbDomainConst.ProblemType.MISS_SETTING);
								problemList.setUrlId(DbDomainConst.ResourceURL.URL_SCREEN_DESIGN);
								problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
								problemList.setModuleId(item.getModuleId());
								problemList.setProjectId(blogic.getProjectId());
								problemList.setCreatedBy(common.getCreatedBy());
								problemList.setFromResourceType(DbDomainConst.FromResourceType.NAVIGATOR_BLOGIC);
								problemList.setFromResourceId(blogic.getBusinessLogicId());
								problemList.setCreatedDate(currentTime);
								lstProblemLists.add(problemList);
							}
							isImpact = true;
						}
						if(isImpact){
							lstImpactScreenItems.add(item);
						}
					}
				}
			}
		}
		
		if (CollectionUtils.isNotEmpty(lstTemps) || CollectionUtils.isNotEmpty(lstImpactScreenItems)) {
			impact.setImpactFlag(true);
		}
		impact.setLstUsedBlogics(lstTemps);
		impact.setLstUsedNavigatorScreenItems(lstImpactScreenItems);
		if (Boolean.TRUE.equals(isRunBatch)) {
			// delete mapping data
			navigationComponentRepository.deleteNavigatorDetailByNavigatorBlogicId(blogic.getBusinessLogicId());
			exceptionComponentRepository.deleteExceptionDetailByNavigatorBlogicId(blogic.getBusinessLogicId());
			screenActionParamRepository.deleteScreenActionParamByNavigatorBlogicId(blogic.getBusinessLogicId());
			// delete old problem of this business
			List<Long> lstFroms = new ArrayList<Long>();
			lstFroms.add(blogic.getBusinessLogicId());
			problemListRepository.deleteResourceType(DbDomainConst.FromResourceType.NAVIGATOR_BLOGIC, lstFroms);
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
    public ImpactChangeOfStandardBlogic detectListAffectedWhenDelete(BusinessDesign blogic, CommonModel common, Boolean isRunBatch) {
		ImpactChangeOfStandardBlogic impact = new ImpactChangeOfStandardBlogic();
		List<BusinessDesign> lstUsedBusinessDesign = new ArrayList<BusinessDesign>();
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		List<NavigatorComponent> lstNavigatorComponents = new ArrayList<NavigatorComponent>();
		List<ExceptionComponent> lstExceptionComponents = new ArrayList<ExceptionComponent>();
		List<ScreenItem> lstUsedScreenItems = new ArrayList<ScreenItem>();
		Timestamp systemDate = FunctionCommon.getCurrentTime();
		//detect impact change design
		
		lstNavigatorComponents = navigationComponentRepository.findNavigatorComponentByNavigatorBlogicId(blogic.getBusinessLogicId());
		lstExceptionComponents = exceptionComponentRepository.findExceptionComponentByNavigatorBlogicId(blogic.getBusinessLogicId());
		lstUsedBusinessDesign = businessDesignRepository.findAllBussinessLogicByNavigatorBlogicId(blogic.getBusinessLogicId());
		lstUsedScreenItems = screenItemRepository.getAllScreenItemByBusinessLogicId(blogic.getBusinessLogicId(), common.getWorkingLanguageId());
		if(CollectionUtils.isNotEmpty(lstNavigatorComponents)){
			//mapping
			BusinessDesign usedBusinessDesign = new BusinessDesign();
			ProblemList problemList = new ProblemList();
			for (NavigatorComponent component : lstNavigatorComponents) {
				for (BusinessDesign temp : lstUsedBusinessDesign){
            		if(FunctionCommon.equals(component.getBusinessLogicId(),temp.getBusinessLogicId())){
            			usedBusinessDesign = temp;
            			break;
            		}
            	}
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0086,blogic.getBusinessLogicCode(),component.getLabel(), usedBusinessDesign.getBusinessLogicCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
				problemList.setResourceId(usedBusinessDesign.getBusinessLogicId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
				problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
				problemList.setModuleId(usedBusinessDesign.getModuleId());
				problemList.setProjectId(usedBusinessDesign.getProjectId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.NAVIGATOR_BLOGIC);
				problemList.setFromResourceId(blogic.getBusinessLogicId());
				problemList.setCreatedDate(systemDate);
				lstProblemLists.add(problemList);
            }
			
			//delete mapping data
			navigationComponentRepository.deleteNavigatorDetailByNavigatorComponent(lstNavigatorComponents);
		}
		if(CollectionUtils.isNotEmpty(lstExceptionComponents)){
			//mapping
			BusinessDesign usedBusinessDesign = new BusinessDesign();
			ProblemList problemList = new ProblemList();
			for (ExceptionComponent component : lstExceptionComponents) {
				for (BusinessDesign temp : lstUsedBusinessDesign){
            		if(FunctionCommon.equals(component.getBusinessLogicId(),temp.getBusinessLogicId())){
            			usedBusinessDesign = temp;
            			break;
            		}
            	}
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0087,blogic.getBusinessLogicCode(),component.getLabel(), usedBusinessDesign.getBusinessLogicCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
				problemList.setResourceId(usedBusinessDesign.getBusinessLogicId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
				problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
				problemList.setModuleId(usedBusinessDesign.getModuleId());
				problemList.setProjectId(usedBusinessDesign.getProjectId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.NAVIGATOR_BLOGIC);
				problemList.setFromResourceId(blogic.getBusinessLogicId());
				problemList.setCreatedDate(systemDate);
				lstProblemLists.add(problemList);
            }
			
			//delete mapping data
			exceptionComponentRepository.deleteExceptionDetailByNavigatorComponent(lstExceptionComponents);
		}
		if(CollectionUtils.isNotEmpty(lstUsedScreenItems)){
			ProblemList problemList = new ProblemList();
			for (ScreenItem item : lstUsedScreenItems) {
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0090,blogic.getBusinessLogicCode(),item.getItemCode(), item.getScreenCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.SCREEN_DESIGN);
				problemList.setResourceId(item.getScreenId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
				problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
				problemList.setModuleId(item.getModuleId());
				problemList.setProjectId(common.getWorkingProjectId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.NAVIGATOR_BLOGIC);
				problemList.setFromResourceId(blogic.getBusinessLogicId());
				problemList.setCreatedDate(systemDate);
				lstProblemLists.add(problemList);
            }
			//delete mapping data
			screenActionParamRepository.deleteScreenActionParamByLstScreenItems(lstUsedScreenItems);
		}
		if(isRunBatch){
			//delete old problem of this business
			List<Long> lstFroms = new ArrayList<Long>();
			lstFroms.add(blogic.getBusinessLogicId());
			problemListRepository.deleteResourceType(DbDomainConst.FromResourceType.NAVIGATOR_BLOGIC, lstFroms);
			if(CollectionUtils.isNotEmpty(lstProblemLists))
				problemListRepository.multiRegisterProblem(lstProblemLists);
		}
		if (CollectionUtils.isNotEmpty(lstUsedBusinessDesign) || CollectionUtils.isNotEmpty(lstUsedScreenItems)) {
			impact.setImpactFlag(true);
		}
		impact.setLstUsedBlogics(lstUsedBusinessDesign);
		impact.setLstUsedNavigatorScreenItems(lstUsedScreenItems);
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
