package org.terasoluna.qp.domain.service.decisiontable;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.codelist.SimpleMapCodeList;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.DbDomainConst.GenerateAppStatus;
import org.terasoluna.qp.app.common.constants.DbDomainConst.ImpactChangeDesign;
import org.terasoluna.qp.app.common.constants.TerasolunaQPValidationConst;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.DecisionTableMessageConst;
import org.terasoluna.qp.domain.model.BDParameterIndex;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.CommonObjectAttribute;
import org.terasoluna.qp.domain.model.CommonObjectDefinition;
import org.terasoluna.qp.domain.model.DecisionTable;
import org.terasoluna.qp.domain.model.DecisionTableConditionGroup;
import org.terasoluna.qp.domain.model.DecisionTableConditionItem;
import org.terasoluna.qp.domain.model.DecisionTableInputBean;
import org.terasoluna.qp.domain.model.DecisionTableItemDesignBean;
import org.terasoluna.qp.domain.model.DecisionTableOutputBean;
import org.terasoluna.qp.domain.model.ExternalObjectAttribute;
import org.terasoluna.qp.domain.model.ExternalObjectDefinition;
import org.terasoluna.qp.domain.model.FormulaDefinition;
import org.terasoluna.qp.domain.model.FormulaDetail;
import org.terasoluna.qp.domain.model.FormulaMethodInput;
import org.terasoluna.qp.domain.model.FormulaMethodOutput;
import org.terasoluna.qp.domain.model.ImpactChangeJobControl;
import org.terasoluna.qp.domain.model.ProblemList;
import org.terasoluna.qp.domain.repository.businessdesign.BusinessDesignRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.DecisionComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.FormulaDefinitionRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableConditionGroupRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableConditionItemRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableInputBeanRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableItemDesignBeanRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableOutputBeanRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableSearchCriteria;
import org.terasoluna.qp.domain.repository.impactchange.ImpactChangeRepository;
import org.terasoluna.qp.domain.repository.problemlist.ProblemListRepository;
import org.terasoluna.qp.domain.repository.project.ProjectRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignService;
import org.terasoluna.qp.domain.service.commonobjectdefinition.CommonObjectDefinitionSharedService;
import org.terasoluna.qp.domain.service.externalobjectdefinition.ExternalObjectDefinitionSharedService;
import org.terasoluna.qp.domain.service.messagedesign.MessageDesignService;
import org.terasoluna.qp.domain.service.module.ModuleService;
import org.terasoluna.qp.domain.service.project.ProjectService;

@Service
@Transactional
public class DecisionTableServiceImpl implements DecisionTableService {

	@Inject
	public DecisionTableRepository decisionTableRepository; 
	
	@Inject
	public DecisionTableInputBeanRepository decisionTableInputBeanRepository; 
	
	@Inject
	public DecisionTableOutputBeanRepository decisionTableOutputBeanRepository; 
	
	@Inject
	public DecisionTableItemDesignBeanRepository decisionTableItemDesignBeanRepository; 
	
	@Inject
	public DecisionTableConditionGroupRepository decisionTableConditionGroupRepository; 
	
	@Inject
	public DecisionTableConditionItemRepository decisionTableConditionItemRepository;
	
	@Inject
	public ProjectRepository projectRepository;
	
	@Inject
	MessageDesignService messageDesignService;
	
	@Inject
	public FormulaDefinitionRepository formulaDefinitionRepository;
	
	@Inject
	ProjectService projectService;
	
	@Inject
	ModuleService moduleService;
	
	@Inject
	ProblemListRepository problemListRepository;
	
	@Inject
	BusinessDesignService businessDesignService;
	
	@Inject
	BusinessDesignRepository businessDesignRepository;
	
	@Inject
	DecisionComponentRepository decisionComponentRepository; 
	 
	@Inject
	@Named(value = "CL_BD_DATATYPE_NOT_ENTITY")
	SimpleMapCodeList simpleMapCodeList;

    @Inject
    CommonObjectDefinitionSharedService commonObjectDefinitionSharedService;

    @Inject
    ExternalObjectDefinitionSharedService externalObjectDefinitionSharedService;
    
    @Inject
    ImpactChangeRepository impactChangeRepository;
	
//	private static final String BUSINESS_REF_COUNT = "business_ref_count";
	
	private Map<String, String> mNameParameter = new HashMap<String, String>();
	private Map<String,Long> mKeyInClient = new HashMap<String, Long>();
	private Map<String,Long> mKeyOuClient = new HashMap<String, Long>();
    private Integer itemSeqNo = 0;
	
	/**
	 * Finds all decision table information with search criteria
	 * 
	 * @param criteria decisionTableSearchCriteria
	 * @return List of all decision table
	 */
	@Override
	public Page<DecisionTable> searchDecisionTable(DecisionTableSearchCriteria decisionTableSearchCriteria, Pageable pageable) {
		List<DecisionTable> lstDecisionTables;
		long totalCount = decisionTableRepository.countBySearchCriteria(decisionTableSearchCriteria);
		
		if (0 < totalCount) {
			lstDecisionTables = decisionTableRepository.findPageBySearchCriteria(decisionTableSearchCriteria, pageable);
		} else {
			lstDecisionTables = Collections.emptyList();
		}
		
		Page<DecisionTable> page = new PageImpl<DecisionTable>(lstDecisionTables, pageable, totalCount);
		
		return page;
	}

	/**
	 * Finds one decision table information
	 * 
	 * @param criteria decisionTableSearchCriteria
	 * @return List of all decision table
	 */
	@Override
	public DecisionTable findOneByDecisionTbId(Long decisionTbId) {

		// Reset value
		mNameParameter.clear();
		// Get value of master table of decision
		DecisionTable decisionTable = decisionTableRepository.findOneByDecisionTbId(decisionTbId);
		if (decisionTable == null) {
			throw new BusinessException(ResultMessages.error()
					.add(CommonMessageConst.ERR_SYS_0037, 
							MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0007)));
		}
		
		// Get list of business logic by decision id
		List<BusinessDesign> listBusinessDesigns = decisionTableRepository.findAllBussinessLogicByDecisionId(decisionTbId);
		decisionTable.setListBD(listBusinessDesigns);
		
		// Get value for input bean
		List<DecisionTableInputBean> inputBean = decisionTableInputBeanRepository.findDecisionInputBeanTypeTree(decisionTbId);
		
		if(inputBean != null && inputBean.size() > 0) {
			// Convert to String Json
			String strInputBean = DecisionTableUtils.convertToJsonFromObjList(inputBean);
			
			// Set value for input bean
			decisionTable.setListInput(strInputBean);
			decisionTable.setInputLst(inputBean);
		}
		
		// Get value for input bean
		List<DecisionTableOutputBean> outputBean = decisionTableOutputBeanRepository.findDecisionOutputBeanTypeTree(decisionTbId);
		
		if(outputBean != null && outputBean.size() > 0) {
			// Convert to String Json
			String strOutputBean = DecisionTableUtils.convertToJsonFromObjList(outputBean);
			
			// Set value for input bean
			decisionTable.setListOutput(strOutputBean);
			decisionTable.setOutputLst(outputBean);
		}
		
		// Set level table index
        itemSeqNo = 0;
        calcTableIndexForInput(decisionTable.getInputLst(), "");
        itemSeqNo = 0;
        calcTableIndexForOutput(decisionTable.getOutputLst(), "");
		
		// Get value for Item design
		List<DecisionTableItemDesignBean> itemDesign = decisionTableItemDesignBeanRepository.findDecisionItemDesignBeanById(decisionTbId);
		List<List<DecisionTableItemDesignBean>> itemDesigns = DecisionTableUtils.getDataItemDesignType(itemDesign);
		
		boolean isExistColCond = false;
		boolean isExistColAct = false;
		
		mNameParameter = initMapCodeCombine(inputBean, outputBean);
		
		String strItemDesignRule = null;
		if(itemDesigns.get(0) != null && itemDesigns.get(0).size() > 0) {
		    for (DecisionTableItemDesignBean input : itemDesigns.get(0)) {
		        input.setObjectCodeCombine(mNameParameter.get(DecisionTableConst.INPUTBEAN_ID + input.getItemValue()));
		    }
			strItemDesignRule =  DecisionTableUtils.convertToJsonFromObjList(itemDesigns.get(0));
			// Set value for item design bean
			decisionTable.setListItemCondition(strItemDesignRule);
			isExistColCond = true;
		}

		String strItemDesignAction = null;
		if(itemDesigns.get(1) != null && itemDesigns.get(1).size() > 0) {
		    for (DecisionTableItemDesignBean output : itemDesigns.get(1)) {
		        output.setObjectCodeCombine(mNameParameter.get(DecisionTableConst.OUTPUTBEAN_ID + output.getItemValue()));
            }
			// Convert to String Json
			strItemDesignAction = DecisionTableUtils.convertToJsonFromObjList(itemDesigns.get(1));
			decisionTable.setListItemAction(strItemDesignAction);
			isExistColAct = true;
		}
		
		// If existence condition and action column
		if(isExistColCond && isExistColAct){
			// Get value for decision condition group by list decision item design
			if(itemDesign != null && itemDesign.size() > 0) {
				// Get all id item design
				List<DecisionTableConditionGroup> conditionGroup = decisionTableConditionGroupRepository.findConditionGroupById(itemDesign);
				if(conditionGroup != null && conditionGroup.size() > 0){
					String convertConditionGroup = DecisionTableUtils.convertToJsonFromObjList(conditionGroup);
					
					// Get value for decision item group by list decision condition group
					if(conditionGroup != null && conditionGroup.size() > 0) {
						List<DecisionTableConditionItem> conditionItem = decisionTableConditionItemRepository.findConditionItemById(conditionGroup);
						if(conditionItem != null && conditionItem.size() > 0) {
							
							// Get FormulaDefinitionDetails
							List<FormulaDetail> lstFormulaDetails = getListFormulaDetails(conditionItem, null);
							
							conditionItem = getListConditionItemUpdate(lstFormulaDetails, conditionItem);
							String convertConditionItem = DecisionTableUtils.convertToJsonFromObjList(conditionItem);
							decisionTable.setListConditionItem(convertConditionItem);
						}
					}

					decisionTable.setListConditionGroup(convertConditionGroup);
				}
			}
		}
		
		return decisionTable;
	}

    private Map<String, String> initMapCodeCombine(List<DecisionTableInputBean> lstInputBean, List<DecisionTableOutputBean> lstOutputBean) {
        Map<String, String> mNameParameter = new HashMap<String, String>();
        
        Map<String, String> mapTableIndex = new HashMap<String, String>();
        // set level of input bean
        Map<String, Integer> mapSequence = new HashMap<String, Integer>();
        for (DecisionTableInputBean in : lstInputBean) {
            String code = "";
            String currentGroup = "";
            if (in.getParentDecisionInputBeanId() != null) {
                currentGroup = mapTableIndex.get(in.getParentDecisionInputBeanId());
            }
            in.setGroupId(currentGroup);
            int maxIndex = mapSequence.getOrDefault(in.getGroupId(), 0);
            maxIndex++;
            if (in.getParentDecisionInputBeanId() == null) {
                in.setTableIndex(String.valueOf(maxIndex));
                code = in.getDecisionInputBeanCode();
            } else {
                in.setTableIndex(currentGroup + "." + maxIndex);
                code = mNameParameter.getOrDefault(DecisionTableConst.INPUTBEAN_ID + in.getParentDecisionInputBeanId(), "");
                code += "." + in.getDecisionInputBeanCode();
            }
            mapTableIndex.put(in.getDecisionInputBeanId(), in.getTableIndex());
            mapSequence.put(in.getGroupId(), maxIndex);
            mNameParameter.put(DecisionTableConst.INPUTBEAN_ID + in.getDecisionInputBeanId(), code);
        }

        mapTableIndex.clear();
        mapSequence.clear();
        // process output bean
        for (DecisionTableOutputBean ou : lstOutputBean) {
            String code = "";
            String currentGroup = "";
            if (ou.getParentDecisionOutputBeanId() != null) {
                currentGroup = mapTableIndex.get(ou.getParentDecisionOutputBeanId());
            }
            ou.setGroupId(currentGroup);
            int maxIndex = mapSequence.getOrDefault(ou.getGroupId(), 0);
            maxIndex++;
            if (ou.getParentDecisionOutputBeanId() == null) {
                ou.setTableIndex(String.valueOf(maxIndex));
                code = ou.getDecisionOutputBeanCode();
            } else {
                ou.setTableIndex(currentGroup + "." + maxIndex);
                code = mNameParameter.getOrDefault(DecisionTableConst.OUTPUTBEAN_ID + ou.getParentDecisionOutputBeanId(), "");
                code += "." + ou.getDecisionOutputBeanCode();
            }
            mapTableIndex.put(ou.getDecisionOutputBeanId(), ou.getTableIndex());
            mapSequence.put(ou.getGroupId(), maxIndex);
            mNameParameter.put(DecisionTableConst.OUTPUTBEAN_ID + ou.getDecisionOutputBeanId(), code);
        }
        return mNameParameter;
    }

    private void calcTableIndexForInput(List<DecisionTableInputBean> outputLst, String groupPref) {
        Map<String, String> mapTableIndex = new HashMap<String, String>();
        // set level
        Map<String, Integer> mapSequence = new HashMap<String, Integer>();
        if (!FunctionCommon.isEmpty(outputLst)) {
            for (DecisionTableInputBean output : outputLst) {
                String currentGroup = groupPref;
                if (!FunctionCommon.isEmpty(output.getParentDecisionInputBeanId())) {
                    currentGroup = mapTableIndex.get(output.getParentDecisionInputBeanId());
                } else {
                    currentGroup = groupPref;
                }

                output.setGroupId(currentGroup);
                output.setItemSequenceNo(itemSeqNo);
                itemSeqNo++;
                
                int maxIndex = mapSequence.getOrDefault(output.getGroupId(), 0);
                maxIndex++;
                String tableIndex;
                if (FunctionCommon.isEmpty(currentGroup)) {
                    tableIndex = String.valueOf(maxIndex);
                } else {
                    tableIndex = currentGroup + "." + maxIndex;
                }
                output.setTableIndex(tableIndex);

                mapTableIndex.put(output.getDecisionInputBeanId(), output.getTableIndex());
                mapSequence.put(output.getGroupId(), maxIndex);
            }
        }
    }
    
    private void calcTableIndexForOutput(List<DecisionTableOutputBean> outputLst, String groupPref) {
        Map<String, String> mapTableIndex = new HashMap<String, String>();
        // set level
        Map<String, Integer> mapSequence = new HashMap<String, Integer>();
        if (!FunctionCommon.isEmpty(outputLst)) {
            for (DecisionTableOutputBean output : outputLst) {
                String currentGroup = groupPref;
                if (!FunctionCommon.isEmpty(output.getParentDecisionOutputBeanId())) {
                    currentGroup = mapTableIndex.get(output.getParentDecisionOutputBeanId());
                } else {
                    currentGroup = groupPref;
                }

                output.setGroupId(currentGroup);
                output.setItemSequenceNo(itemSeqNo);
                itemSeqNo++;
                
                int maxIndex = mapSequence.getOrDefault(output.getGroupId(), 0);
                maxIndex++;
                String tableIndex;
                if (FunctionCommon.isEmpty(currentGroup)) {
                    tableIndex = String.valueOf(maxIndex);
                } else {
                    tableIndex = currentGroup + "." + maxIndex;
                }
                output.setTableIndex(tableIndex);

                mapTableIndex.put(output.getDecisionOutputBeanId(), output.getTableIndex());
                mapSequence.put(output.getGroupId(), maxIndex);
            }
        }
    }

	private List<DecisionTableConditionItem> getListConditionItemUpdate(List<FormulaDetail> lstFormulaDetails, List<DecisionTableConditionItem> conditionItem) {
		
		for(DecisionTableConditionItem itemi : conditionItem) {
			if(itemi.getFormulaDefinitionId() != null) {
				List<FormulaDetail> tmp = new ArrayList<FormulaDetail>();
				for(FormulaDetail itemj : lstFormulaDetails) {
					if(itemj.getFormulaDefinitionId().equals(itemi.getFormulaDefinitionId())) {
						tmp.add(itemj);
					}
				}
				
				// Convert to String Json
				itemi.setFormulaDefinitionDetails(tmp);
			}
		}
		
		return conditionItem;
	}

	private List<FormulaDetail> getListFormulaDetails(List<DecisionTableConditionItem> conditionItem, List<BDParameterIndex> lstBdParameterIndexs) {

		List<FormulaDetail> lstFormulaDetails = formulaDefinitionRepository.findFormulaDetailsByListDecisionItemId(conditionItem);
		List<FormulaMethodInput> lstFormulaMethodInputs = formulaDefinitionRepository.findFormulaMethodInputsByListDecisionItemId(conditionItem);
		List<FormulaMethodOutput> lstFormulaMethodOutputs = formulaDefinitionRepository.findFormulaMethodOutputsByListDecisionItemId(conditionItem);

		//map detail function for formula setting
		if (CollectionUtils.isNotEmpty(lstFormulaMethodInputs)) {
			for (FormulaMethodInput objInput : lstFormulaMethodInputs) {
				if (BusinessDesignConst.FormulaBuilder.PARAMETER_TYPE_PARAMETER.equals(objInput.getParameterType())) {
					// set parameter
					if (StringUtils.isNotBlank(objInput.getParameterId())) {
						if (mNameParameter != null) {
							objInput.setParameterIdAutocomplete(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_CODE + "." +  mNameParameter.getOrDefault(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.toString() + objInput.getParameterId(), ""));
						} else {
							objInput.setParameterIdAutocomplete("");
						}

						List<BDParameterIndex> lstParameterIndex = new ArrayList<BDParameterIndex>();
						if (CollectionUtils.isNotEmpty(lstBdParameterIndexs)) {
							for (BDParameterIndex index : lstBdParameterIndexs) {
								if (BusinessDesignConst.ParameterIndex.TABLE_FORMULA_DETAIL.equals(index.getTableType()) && objInput.getFormulaMethodInputId().equals(index.getTableId())) {
									index.setParameterId(objInput.getParameterScope() + objInput.getParameterScope() + index.getParameterId());
									lstParameterIndex.add(index);
								}
							}
						}
						objInput.setLstParameterIndex(lstParameterIndex);
					}
				}
			}
		}
		
		if (CollectionUtils.isNotEmpty(lstFormulaDetails)) {
			for (FormulaDetail objDetail : lstFormulaDetails) {
				List<FormulaMethodInput> lstInputTemps = new ArrayList<FormulaMethodInput>();
				if (CollectionUtils.isNotEmpty(lstFormulaMethodInputs)) {
					for (FormulaMethodInput objInput : lstFormulaMethodInputs) {
						if (objInput.getFormulaDetailId().equals(objDetail.getFormulaDetailId())) {
							lstInputTemps.add(objInput);
						}
					}
				}
				objDetail.setFormulaMethodInputs(lstInputTemps);

				List<FormulaMethodOutput> lstOutputTemps = new ArrayList<FormulaMethodOutput>();
				if (CollectionUtils.isNotEmpty(lstFormulaMethodOutputs)) {
					for (FormulaMethodOutput objOutput : lstFormulaMethodOutputs) {
						if (objOutput.getFormulaDetailId().equals(objDetail.getFormulaDetailId())) {
							lstOutputTemps.add(objOutput);
						}
					}
				}
				objDetail.setFormulaMethodOutputs(lstOutputTemps);
				// mapping index
				List<BDParameterIndex> lstParameterIndex = new ArrayList<BDParameterIndex>();
				objDetail.setLstParameterIndex(lstParameterIndex);
				
				// mapping id of parameter
				if (objDetail.getType() != null) {
					Integer scope;
					switch (objDetail.getType().intValue()) {
						case BusinessDesignConst.FormulaBuilder.TYPE_IN_BUSINESSLOGIC:
							scope = BusinessDesignConst.FormulaBuilder.PARAMETER_SCOPE_INPUT_BEAN;
							objDetail.setParameterId(scope.toString() + objDetail.getParameterId());
							break;
						case BusinessDesignConst.FormulaBuilder.TYPE_OB_BUSINESSLOGIC:
							scope = BusinessDesignConst.FormulaBuilder.PARAMETER_SCOPE_OBJECT_DEFINITION;
							objDetail.setParameterId(scope.toString() + objDetail.getParameterId());
							break;
						case BusinessDesignConst.FormulaBuilder.TYPE_OU_BUSINESSLOGIC:
							scope = BusinessDesignConst.FormulaBuilder.PARAMETER_SCOPE_OUTPUT_BEAN;
							objDetail.setParameterId(scope.toString() + objDetail.getParameterId());
							break;
						default:
							break;
					}
				}
			}
		}

		return lstFormulaDetails;
	}
	
	/**
	 * Register decision table information
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void registerDecisionTable(DecisionTable decisionTable, CommonModel common) {
		
		/*if(decisionTable.getModuleId() == null) {
			projectService.validateProject(decisionTable.getProjectId(),true);
		} else {
			moduleService.validateModule(decisionTable.getModuleId(), true);
		}*/
//		validate(decisionTable.getProjectId(), decisionTable.getModuleId(), common);
		
		mKeyInClient.clear();
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		
		// check duplicated name code
		Long totalCount = decisionTableRepository.countNameCodeExist(decisionTable);
		// Return message error when error
		ResultMessages resultMessages = ResultMessages.error();
		if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0005));
		} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0006));
		} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0005));
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0006));
		}

		// confirm information of error message
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {
			// Processing for register decision table
			decisionTable.setCreatedDate(currentTime);
			decisionTable.setUpdatedDate(currentTime);
			
			// insert business design
			decisionTableRepository.register(decisionTable);
			
			// Processing for insert information all tab
			// Parse string to json object
			List<Object> arrObj = DecisionTableUtils.getObjectParseFromJsons(decisionTable);
			
			// Processing for setting message
			/*arrObj = processMultiLanguage(arrObj, createdBy, currentTime, languageId);*/
			
			// Process register In/Out
			// Processing register In/Out
			List<DecisionTableInputBean> lstInputBeanRegister = (List<DecisionTableInputBean>) arrObj.get(0);
			List<DecisionTableOutputBean> lstOutputBeanRegister = (List<DecisionTableOutputBean>) arrObj.get(1);
			List<DecisionTableItemDesignBean> itemDesigns = (List<DecisionTableItemDesignBean>) arrObj.get(2);
			
			Long startSequence;
			int resultInputBean, resultOutputBean;
			resultInputBean = resultOutputBean = 0;
			
			if(lstInputBeanRegister != null && !lstInputBeanRegister.isEmpty()) {
				// get sequence of input bean
				// get list sequence
				Long sequenceInputBean = decisionTableInputBeanRepository.getSequencesInputBean(lstInputBeanRegister.size() - 1);
				startSequence = sequenceInputBean - (lstInputBeanRegister.size() - 1);

				Map<String, Long> mapKeyInput = new HashMap<String, Long>();
				for (DecisionTableInputBean objInputBean : lstInputBeanRegister) {
					mapKeyInput.put(objInputBean.getDecisionInputBeanId(), startSequence);
					mKeyInClient.put(objInputBean.getDecisionInputBeanId(), startSequence);
					objInputBean.setDecisionInputBeanId(startSequence.toString());
					startSequence++;

					// If parent
					if ("".equals(objInputBean.getParentDecisionInputBeanId())) {
						objInputBean.setParentDecisionInputBeanId(null);
					}

					objInputBean.setDecisionTbId(decisionTable.getDecisionTbId().toString());

					// map key of parent
					if (mapKeyInput.containsKey(objInputBean.getParentDecisionInputBeanId())) {
						objInputBean.setParentDecisionInputBeanId(
								mapKeyInput.get(objInputBean.getParentDecisionInputBeanId()).toString());
					}
				}
				
				// Set item value for item design
				if(itemDesigns !=  null && !itemDesigns.isEmpty()) {
					itemDesigns = getChangeItemValue(mapKeyInput, itemDesigns);
				}
				
				resultInputBean = decisionTableInputBeanRepository.registerInputBean(lstInputBeanRegister);
			}
			
			if(lstOutputBeanRegister != null && !lstOutputBeanRegister.isEmpty()) {
				// get sequence of input bean
				// get list sequence
				Long sequenceOutputBean = decisionTableOutputBeanRepository.getSequencesOutputBean(lstOutputBeanRegister.size() - 1);
				startSequence = sequenceOutputBean - (lstOutputBeanRegister.size() - 1);

				Map<String, Long> mapKeyOutput = new HashMap<String, Long>();
				for (DecisionTableOutputBean objOutputBean : lstOutputBeanRegister) {
					mapKeyOutput.put(objOutputBean.getDecisionOutputBeanId(), startSequence);
					objOutputBean.setDecisionOutputBeanId(startSequence.toString());
					startSequence++;

					// If parent
					if ("".equals(objOutputBean.getParentDecisionOutputBeanId())) {
						objOutputBean.setParentDecisionOutputBeanId(null);
					}

					objOutputBean.setDecisionTbId(decisionTable.getDecisionTbId().toString());

					// map key of parent
					if (mapKeyOutput.containsKey(objOutputBean.getParentDecisionOutputBeanId())) {
						objOutputBean.setParentDecisionOutputBeanId(
								mapKeyOutput.get(objOutputBean.getParentDecisionOutputBeanId()).toString());
					}
				}
				
				// Change item value of item design
				if(itemDesigns != null && !itemDesigns.isEmpty()) {
					itemDesigns = getChangeItemValue(mapKeyOutput, itemDesigns);
				}
				
				resultOutputBean = decisionTableOutputBeanRepository.registerOutputBean(lstOutputBeanRegister);
			}
			
			System.out.println(resultInputBean);
			System.out.println(resultOutputBean);
			
			List<Object> arObjTmp = new ArrayList<Object>();
			arObjTmp.add(itemDesigns);
			arObjTmp.add(arrObj.get(3));
			arObjTmp.add(arrObj.get(4));
			arObjTmp.add(arrObj.get(5));
			
			// Processing for register
			processRegister(arObjTmp, common);
			
		}
	}
	
	/**
	 * Update date decision table information
	 * 
	 */
	@Override
	public void modifyDecisionTable(DecisionTable decisionTable, CommonModel common) {
		mKeyInClient.clear();
		// Get value of master table of decision
		DecisionTable dc = decisionTableRepository.findOneByDecisionTbId(decisionTable.getDecisionTbId());
		if (dc == null) {
			throw new BusinessException(ResultMessages.error()
					.add(CommonMessageConst.ERR_SYS_0037, 
							MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0007)));
		}
		
		// Adding check concurrence
		if (DbDomainConst.DesignStatus.FIXED.equals(dc.getDesignStatus())) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0109, decisionTable.getDecisionTbName()));
		}
				
		// Check project
		/*if(dc.getModuleId() == null) {
			projectService.validateProject(dc.getProjectId(),true);
		} else {
			moduleService.validateModule(dc.getModuleId(), true);
		}*/

		common.setProjectId(dc.getProjectId());
		validate(dc.getProjectId(), dc.getModuleId(), common);
		
		// check duplicated name code
		Long totalCount = decisionTableRepository.countNameCodeExist(decisionTable);
		// Return message error when occuring error
		ResultMessages resultMessages = ResultMessages.error();
		if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0005));
		} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0006));
		} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0005));
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0006));
		}
		
		// Confirm information of error message
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {
			// Time for update
			Timestamp currentTime = FunctionCommon.getCurrentTime();
			// Setting time to check concurrence
			decisionTable.setSysDatetime(currentTime);
			decisionTable.setUpdatedBy(common.getUpdatedBy());
			
			int resultModify = decisionTableRepository.modify(decisionTable);
			
			if (resultModify == 0) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, 
						MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0007)));
			}
			//insert problem list
			modifyAffected(decisionTable,common);
			//update name/code of decision component
			if(!(FunctionCommon.equals(dc.getDecisionTbName(), decisionTable.getDecisionTbName()) && FunctionCommon.equals(dc.getDecisionTbCode(), decisionTable.getDecisionTbCode()))){
				decisionComponentRepository.modifyDecisionComponent(decisionTable);
			}

			// Process delete formula setting
			processDeleteFormula(decisionTable.getDecisionTbId());
			lstFormulaDetails = new ArrayList<FormulaDetail>();
			
			// Get object parsing form json string
			List<Object> arrObj = DecisionTableUtils.getObjectParseFromJsons(decisionTable);
			
			// Processing for setting message
			/*arrObj = processMultiLanguage(arrObj, updateBy, currentTime, languageId);*/
			
			@SuppressWarnings("unchecked")
			List<DecisionTableInputBean> inputBeans = (List<DecisionTableInputBean>) arrObj.get(0);
			@SuppressWarnings("unchecked")
			List<DecisionTableOutputBean> outputBeans = (List<DecisionTableOutputBean>) arrObj.get(1);
			@SuppressWarnings("unchecked")
			List<DecisionTableItemDesignBean> itemDesigns = (List<DecisionTableItemDesignBean>) arrObj.get(2);
			@SuppressWarnings("unchecked")
			List<DecisionTableConditionGroup> conditionGroups = (List<DecisionTableConditionGroup>) arrObj.get(3);
			@SuppressWarnings("unchecked")
			List<DecisionTableConditionItem> conditionItems = (List<DecisionTableConditionItem>) arrObj.get(4);
			
			// List for divide register and modify
			List<DecisionTableInputBean> lstInputBeanRegister = new ArrayList<DecisionTableInputBean>();
			List<DecisionTableInputBean> lstInputBeanModify = new ArrayList<DecisionTableInputBean>();
			List<DecisionTableOutputBean> lstOutputBeanRegister = new ArrayList<DecisionTableOutputBean>();
			List<DecisionTableOutputBean> lstOutputBeanModify = new ArrayList<DecisionTableOutputBean>();
			List<DecisionTableItemDesignBean> lstItemDesignRegister = new ArrayList<DecisionTableItemDesignBean>();
			List<DecisionTableItemDesignBean> lstItemDesignModify = new ArrayList<DecisionTableItemDesignBean>();
			List<DecisionTableConditionGroup> lstCondGroupRegister = new ArrayList<DecisionTableConditionGroup>();
			List<DecisionTableConditionGroup> lstCondGroupModify = new ArrayList<DecisionTableConditionGroup>();
			List<DecisionTableConditionItem> lstCondItemRegister = new ArrayList<DecisionTableConditionItem>();
			List<DecisionTableConditionItem> lstCondItemModify = new ArrayList<DecisionTableConditionItem>();
			
			// Divide list input
			if(inputBeans != null && !inputBeans.isEmpty()) {
				for (DecisionTableInputBean obj : inputBeans) {
					if (obj.getDecisionInputBeanId() != null 
							&& obj.getDecisionInputBeanId().contains(DecisionTableConst.PREFIX_INPUTBEAN_ID)) {
						if("".equals(obj.getParentDecisionInputBeanId())) {
							obj.setParentDecisionInputBeanId(null);
						}
						lstInputBeanRegister.add(obj);
						
					} else if (obj.getDecisionInputBeanId() != null) {
						if("".equals(obj.getParentDecisionInputBeanId())) {
							obj.setParentDecisionInputBeanId(null);
						}
						mKeyInClient.put(obj.getDecisionInputBeanId().toString(), Long.valueOf(obj.getDecisionInputBeanId()));
						lstInputBeanModify.add(obj);
					}
				}
			}

			// Divide list output
			if(outputBeans != null && !outputBeans.isEmpty()) {
				for (DecisionTableOutputBean obj : outputBeans) {
					if (obj.getDecisionOutputBeanId() != null 
							&& obj.getDecisionOutputBeanId().contains(DecisionTableConst.PREFIX_OUTPUTBEAN_ID)) {
						if("".equals(obj.getParentDecisionOutputBeanId())) {
							obj.setParentDecisionOutputBeanId(null);
						}
						lstOutputBeanRegister.add(obj);
					} else if (obj.getDecisionOutputBeanId() != null) {
						if("".equals(obj.getParentDecisionOutputBeanId())) {
							obj.setParentDecisionOutputBeanId(null);
						}
						lstOutputBeanModify.add(obj);
					}
				}
			}
			
			// Divide list item design
			if(itemDesigns != null && !itemDesigns.isEmpty()) {
				for (DecisionTableItemDesignBean obj : itemDesigns) {
					if (obj.getDecisionItemDesignId() != null 
							&& (obj.getDecisionItemDesignId().contains(DecisionTableConst.PREFIX_ITEMDESIGN_COND_ID)
									|| (obj.getDecisionItemDesignId().contains(DecisionTableConst.PREFIX_ITEMDESIGN_ACT_ID)))) {
						lstItemDesignRegister.add(obj);
					} else if (obj.getDecisionItemDesignId() != null) {
						lstItemDesignModify.add(obj);
					}
				}
			}
			// Divide list condition group
			if(conditionGroups != null && !conditionGroups.isEmpty()) {
				for (DecisionTableConditionGroup obj : conditionGroups) {
					if (obj.getConditionGroupId() != null 
							&& obj.getConditionGroupId().contains(DecisionTableConst.PREFIX_COND_GROUP_ID)) {
						lstCondGroupRegister.add(obj);
					} else if (obj.getConditionGroupId() != null) {
						lstCondGroupModify.add(obj);
					}
				}
			}

			// Divide list condition item
			if(conditionItems != null && !conditionItems.isEmpty()) {
				for (DecisionTableConditionItem obj : conditionItems) {
					if (obj.getConditionItemId() != null && !obj.getConditionItemId().isEmpty()) {
						lstCondItemModify.add(obj);
					} else {
						lstCondItemRegister.add(obj);
					}
				}
			}

			// modify input, output, item design, logic design
			// Processing for modify first
			if (!lstInputBeanModify.isEmpty()) {
				decisionTableInputBeanRepository.deleteBeforModifyInputBean(lstInputBeanModify, decisionTable.getDecisionTbId());
				resultModify = decisionTableInputBeanRepository.modifyInputBean(lstInputBeanModify);
				if (resultModify == 0) {
					throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0072, 
							MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0011)));
				}
			} else {
				decisionTableInputBeanRepository.deleteBeforModifyInputBean(null, decisionTable.getDecisionTbId());
			}
			
			if (!lstOutputBeanModify.isEmpty()) {
				decisionTableOutputBeanRepository.deleteBeforModifyOutputBean(lstOutputBeanModify, decisionTable.getDecisionTbId());
				resultModify = decisionTableOutputBeanRepository.modifyOutputBean(lstOutputBeanModify);
				if (resultModify == 0) {
					throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0072, 
							MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0012)));
				}
			} else {
				decisionTableOutputBeanRepository.deleteBeforModifyOutputBean(null, decisionTable.getDecisionTbId());
			}
			
			// Processing register In/Out
			Long startSequence;
			int resultInputBean, resultOutputBean;
			resultInputBean = resultOutputBean = 0;
			
			if(lstInputBeanRegister != null && !lstInputBeanRegister.isEmpty()) {
				// get sequence of input bean
				// get list sequence
				Long sequenceInputBean = decisionTableInputBeanRepository.getSequencesInputBean(lstInputBeanRegister.size() - 1);
				startSequence = sequenceInputBean - (lstInputBeanRegister.size() - 1);

				Map<String, Long> mapKeyInput = new HashMap<String, Long>();
				for (DecisionTableInputBean objInputBean : lstInputBeanRegister) {
					mapKeyInput.put(objInputBean.getDecisionInputBeanId(), startSequence);
					mKeyInClient.put(objInputBean.getDecisionInputBeanId(), startSequence);
					objInputBean.setDecisionInputBeanId(startSequence.toString());
					startSequence++;

					// If parent
					if ("".equals(objInputBean.getParentDecisionInputBeanId())) {
						objInputBean.setParentDecisionInputBeanId(null);
					}

					objInputBean.setDecisionTbId(decisionTable.getDecisionTbId().toString());

					// map key of parent
					if (mapKeyInput.containsKey(objInputBean.getParentDecisionInputBeanId())) {
						objInputBean.setParentDecisionInputBeanId(
								mapKeyInput.get(objInputBean.getParentDecisionInputBeanId()).toString());
					}
				}
				
				// Set item value for item design
				if(lstItemDesignRegister !=  null && !lstItemDesignRegister.isEmpty()) {
					lstItemDesignRegister = getChangeItemValue(mapKeyInput, lstItemDesignRegister);
				}
				
				resultInputBean = decisionTableInputBeanRepository.registerInputBean(lstInputBeanRegister);
			}
			
			if(lstOutputBeanRegister != null && !lstOutputBeanRegister.isEmpty()) {
				// get sequence of input bean
				// get list sequence
				Long sequenceOutputBean = decisionTableOutputBeanRepository.getSequencesOutputBean(lstOutputBeanRegister.size() - 1);
				startSequence = sequenceOutputBean - (lstOutputBeanRegister.size() - 1);

				Map<String, Long> mapKeyOutput = new HashMap<String, Long>();
				for (DecisionTableOutputBean objOutputBean : lstOutputBeanRegister) {
					mapKeyOutput.put(objOutputBean.getDecisionOutputBeanId(), startSequence);
					objOutputBean.setDecisionOutputBeanId(startSequence.toString());
					startSequence++;

					// If parent
					if ("".equals(objOutputBean.getParentDecisionOutputBeanId())) {
						objOutputBean.setParentDecisionOutputBeanId(null);
					}

					objOutputBean.setDecisionTbId(decisionTable.getDecisionTbId().toString());

					// map key of parent
					if (mapKeyOutput.containsKey(objOutputBean.getParentDecisionOutputBeanId())) {
						objOutputBean.setParentDecisionOutputBeanId(
								mapKeyOutput.get(objOutputBean.getParentDecisionOutputBeanId()).toString());
					}
				}
				
				// Change item value of item design
				if(lstItemDesignRegister != null && !lstItemDesignRegister.isEmpty()) {
					lstItemDesignRegister = getChangeItemValue(mapKeyOutput, lstItemDesignRegister);
				}
				
				resultOutputBean = decisionTableOutputBeanRepository.registerOutputBean(lstOutputBeanRegister);
			}
			
			System.out.println(resultInputBean);
			System.out.println(resultOutputBean);
			
			// Processing for item design && logic design
			if (!lstItemDesignModify.isEmpty() && !lstCondGroupModify.isEmpty() && !lstCondItemModify.isEmpty()) {
				// Call process register modify
				processRegisterFormula(lstCondItemModify, common);
				
				// Delete all group item belong group condition that not using
				decisionTableConditionItemRepository.deleteBeforModifyCondItem(
						lstCondItemModify, null, null, decisionTable.getDecisionTbId());
				// Delete all group condition is not using
				decisionTableConditionGroupRepository.deleteBeforModifyCondGroup(
						lstCondGroupModify, null, decisionTable.getDecisionTbId());

				resultModify = decisionTableConditionItemRepository.modifyCondItem(lstCondItemModify);
				if (resultModify == 0) {
					throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0072, 
							MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0033)));
				}
				
				// Delete group not using
				decisionTableConditionGroupRepository.deleteBeforModifyCondGroup(
						lstCondGroupModify, null, decisionTable.getDecisionTbId());
				resultModify = decisionTableConditionGroupRepository.modifyCondGroup(lstCondGroupModify);
				if (resultModify == 0) {
					throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0072, 
							MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0032)));
				}
				// Delete all item not using
				decisionTableItemDesignBeanRepository.deleteBeforModifyItemDesign(
						lstItemDesignModify, decisionTable.getDecisionTbId());
				resultModify = decisionTableItemDesignBeanRepository.modifyItemDesign(lstItemDesignModify);
				if (resultModify == 0) {
					throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0072, 
							MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0013)));
				}
			} else if (!lstItemDesignModify.isEmpty() && !lstCondGroupModify.isEmpty()) {
				// Delete group item not using
				decisionTableConditionItemRepository.deleteBeforModifyCondItem(
						null, null, null, decisionTable.getDecisionTbId());
				// Delete group condition not using
				decisionTableConditionGroupRepository.deleteBeforModifyCondGroup(
						lstCondGroupModify, null, decisionTable.getDecisionTbId());
				resultModify = decisionTableConditionGroupRepository.modifyCondGroup(lstCondGroupModify);
				if (resultModify == 0) {
					throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0072, 
							MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0032)));
				}
				// Delete all item not using
				decisionTableItemDesignBeanRepository.deleteBeforModifyItemDesign(
						lstItemDesignModify, decisionTable.getDecisionTbId());
				resultModify = decisionTableItemDesignBeanRepository.modifyItemDesign(lstItemDesignModify);
				if (resultModify == 0) {
					throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0072, 
							MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0013)));
				}
			} else if (!lstItemDesignModify.isEmpty()) {
				// Delete group item not using
				decisionTableConditionItemRepository.deleteBeforModifyCondItem(
							null, null, null, decisionTable.getDecisionTbId());
				// Delete group condition not using
				decisionTableConditionGroupRepository.deleteBeforModifyCondGroup(
							null, null, decisionTable.getDecisionTbId());
				// Delete all item not using
				decisionTableItemDesignBeanRepository.deleteBeforModifyItemDesign(
						lstItemDesignModify, decisionTable.getDecisionTbId());
				resultModify = decisionTableItemDesignBeanRepository.modifyItemDesign(lstItemDesignModify);
				if (resultModify == 0) {
					throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0072, 
							MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0013)));
				}
			} else {
				// Delete all
				decisionTableConditionItemRepository.deleteBeforModifyCondItem(null, null, null, decisionTable.getDecisionTbId());
				decisionTableConditionGroupRepository.deleteBeforModifyCondGroup(null, null, decisionTable.getDecisionTbId());
				decisionTableItemDesignBeanRepository.deleteBeforModifyItemDesign(null, decisionTable.getDecisionTbId());
			}
			
			
			// Processing for register
			arrObj = new ArrayList<Object>();
			arrObj.add(lstItemDesignRegister);
			arrObj.add(lstCondGroupRegister);
			arrObj.add(lstCondItemRegister);
			arrObj.add(decisionTable);
			
			// Call processing for register all
			processRegister(arrObj, common);
			
		}
	}

	private void processDeleteFormula(Long decisionTbId) {
		
		// Get value for Item design
		List<DecisionTableItemDesignBean> itemDesign = decisionTableItemDesignBeanRepository.findDecisionItemDesignBeanById(decisionTbId);
		List<List<DecisionTableItemDesignBean>> itemDesigns = DecisionTableUtils.getDataItemDesignType(itemDesign);
		
		boolean isExistColCond = false;
		
		if(itemDesigns.get(0) != null && itemDesigns.get(0).size() > 0 
				&& itemDesigns.get(1) != null && itemDesigns.get(1).size() > 0) {
			isExistColCond = true;
		}
		
		// If existence condition and action column
		if(isExistColCond){
			// Get value for decision condition group by list decision item design
			if(itemDesign != null && itemDesign.size() > 0) {
				// Get all id item design
				List<DecisionTableConditionGroup> conditionGroup = decisionTableConditionGroupRepository.findConditionGroupById(itemDesign);
				if(conditionGroup != null && conditionGroup.size() > 0) {
					List<DecisionTableConditionItem> conditionItem = decisionTableConditionItemRepository.findConditionItemById(conditionGroup);
					if(conditionItem != null && conditionItem.size() > 0) {
						// Processing for delete formula setting
						formulaDefinitionRepository.deleteFormulaDefinitionByDecisionTableId(conditionItem);
					}
				}
			}
		}
	}

	/**
	 * Processing for register
	 * 
	 * @param arrObj
	 */
	private void processRegister(List<Object> arrObj, CommonModel common) {

		@SuppressWarnings("unchecked")
		List<DecisionTableItemDesignBean> itemDesigns = (List<DecisionTableItemDesignBean>) arrObj.get(0);
		@SuppressWarnings("unchecked")
		List<DecisionTableConditionGroup> conditionGroups = (List<DecisionTableConditionGroup>) arrObj.get(1);
		@SuppressWarnings("unchecked")
		List<DecisionTableConditionItem> conditionItems = (List<DecisionTableConditionItem>) arrObj.get(2);
		
		DecisionTable decisionTable = (DecisionTable) arrObj.get(3);
		
		Long startSequence;
		int resultItemDesign, resultCondGroup, resultCondItem;
		resultItemDesign = resultCondGroup = resultCondItem = 0;

		if(itemDesigns != null && !itemDesigns.isEmpty()) {
			// get sequence of item design
			// get list sequence
			Long sequenceItemDesign = decisionTableItemDesignBeanRepository.getSequencesItemDesign(itemDesigns.size() - 1);
			startSequence = sequenceItemDesign - (itemDesigns.size() - 1);
			
			Map<String, Long> mapKeyItemDesign = new HashMap<String, Long>();
			for (DecisionTableItemDesignBean objItemDesign : itemDesigns) {
				mapKeyItemDesign.put(objItemDesign.getDecisionItemDesignId(), startSequence);
				objItemDesign.setDecisionItemDesignId(startSequence.toString());
				objItemDesign.setDecisionTbId(decisionTable.getDecisionTbId().toString());
				startSequence++;
			}
			
			// Set item design id for condition group
			if(conditionGroups != null && !conditionGroups.isEmpty()) {
				conditionGroups = getChangeItemDesignId(mapKeyItemDesign, conditionGroups);
			}
			
			resultItemDesign = decisionTableItemDesignBeanRepository.registerItemDesign(itemDesigns);
		}

		if(conditionGroups != null && !conditionGroups.isEmpty()) {
			// Get sequence of group condition
			// Get list sequence
			Long sequenceCondGroup = decisionTableConditionGroupRepository.getSequencesCondGroup(conditionGroups.size() - 1);
			startSequence = sequenceCondGroup - (conditionGroups.size() - 1);
			
			Map<String, Long> mapKeyCondGroup = new HashMap<String, Long>();
			for (DecisionTableConditionGroup objCondGroup : conditionGroups) {
				mapKeyCondGroup.put(objCondGroup.getConditionGroupId(), startSequence);
				objCondGroup.setConditionGroupId(startSequence.toString());
				startSequence++;
			}
			
			// Set condition group id for item condition
			if(conditionItems != null && !conditionItems.isEmpty()) {
				conditionItems = getChangeCondGroupId(mapKeyCondGroup, conditionItems);
			}
			
			resultCondGroup = decisionTableConditionGroupRepository.registerCondGroup(conditionGroups);
			
		}

		lstFormulaDetails = new ArrayList<FormulaDetail>();
		if(conditionItems != null && !conditionItems.isEmpty()) {
			// Call process register formula
			processRegisterFormula(conditionItems, common);	
			// Register condition
			resultCondItem = decisionTableConditionItemRepository.registerCondItem(conditionItems);
		}

		// Output all result register of tab
		System.out.println(resultItemDesign);
		System.out.println(resultCondGroup);
		System.out.println(resultCondItem);
		
	}
	
	private void processRegisterFormula(List<DecisionTableConditionItem> conditionItems, CommonModel common) {
		lstFormulaDetails = new ArrayList<FormulaDetail>();
		for(DecisionTableConditionItem dtci : conditionItems) {
			if(dtci.getFormulaDefinitionContent() != null && !dtci.getFormulaDefinitionContent().isEmpty()) {
				
				FormulaDefinition objFormula = new FormulaDefinition();
				objFormula.setProjectId(common.getWorkingProjectId());
				List<FormulaDetail> formulaDefinitionDetails = dtci.getFormulaDefinitionDetails();
				objFormula.setFormulaDefinitionDetails(formulaDefinitionDetails);
				objFormula.setFormulaDefinitionContent(dtci.getFormulaDefinitionContent());
				Long formulaId = registerFormulaSetting(objFormula);
				dtci.setFormulaDefinitionId(formulaId);
			}
		}

		registerFormulaDetail(lstFormulaDetails);
		
	}

	/**
	 * Processing for delete decision table information
	 * 
	 */
	@Override
	public void deleteDecisionTable(DecisionTable decisionTable,Boolean deleteObjectHasFk, CommonModel common) {
		// Check exist
		DecisionTable dc = decisionTableRepository.findOneByDecisionTbId(decisionTable.getDecisionTbId());
		if (dc == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0007)));
		}
		
		// Adding check concurrence
		if (DbDomainConst.DesignStatus.FIXED.equals(dc.getDesignStatus())) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0109, decisionTable.getDecisionTbName()));
		}
		
		// Check change status parent
		/*if(dc.getModuleId() == null) {
			projectService.validateProject(dc.getProjectId(),true);
		} else {
			moduleService.validateModule(dc.getModuleId(), true);
		}*/
		common.setProjectId(dc.getProjectId());
		validate(dc.getProjectId(), dc.getModuleId(), common);
		
//		Timestamp systemTime = FunctionCommon.getCurrentTime();
//		
//		ResultMessages resultMessages = ResultMessages.error();
//		HashMap<String, Long> referenceHashMap = decisionTableRepository.countReferByDecisionTableId(decisionTable.getDecisionTbId());
		
		processDeleteFormula(decisionTable.getDecisionTbId());
		// Delete all table reference
		decisionTableConditionItemRepository.deleteBeforModifyCondItem(null, null, null, decisionTable.getDecisionTbId());
		decisionTableConditionGroupRepository.deleteBeforModifyCondGroup(null, null, decisionTable.getDecisionTbId());
		decisionTableItemDesignBeanRepository.deleteBeforModifyItemDesign(null, decisionTable.getDecisionTbId());
		decisionTableInputBeanRepository.deleteBeforModifyInputBean(null, decisionTable.getDecisionTbId());
		decisionTableOutputBeanRepository.deleteBeforModifyOutputBean(null, decisionTable.getDecisionTbId());
		
		this.deleteAffected(dc,common);
		// Delete decision table master include (item design, input bean, output bean)
		decisionTableRepository.deleteDecisionTable(dc.getDecisionTbId());
		
//		if (0 < referenceHashMap.get(BUSINESS_REF_COUNT)) {
//			resultMessages.add(CommonMessageConst.ERR_SYS_0097, MessageUtils.getMessage(CommonMessageConst.TQP_BUSINESSLOGICDESIGN));
//		}
//		if (resultMessages.isNotEmpty()) {
//			throw new BusinessException(resultMessages);
//		}
	}
	
	/**
	 * 
	 * @param mapKeyCondGroup
	 * @param conditionItems
	 * @return
	 */
	private List<DecisionTableConditionItem> getChangeCondGroupId(Map<String, Long> mapKeyCondGroup,
			List<DecisionTableConditionItem> conditionItems) {
		
		for(DecisionTableConditionItem item : conditionItems) {
			if (mapKeyCondGroup.containsKey(item.getConditionGroupId())){
				item.setConditionGroupId(mapKeyCondGroup.get(item.getConditionGroupId()).toString());
			}
		}
		
		return conditionItems;
	}

	/**
	 * 
	 * @param mapKeyItemDesign
	 * @param conditionGroups
	 * @return
	 */
	private List<DecisionTableConditionGroup> getChangeItemDesignId(Map<String, Long> mapKeyItemDesign,
			List<DecisionTableConditionGroup> conditionGroups) {

		for(DecisionTableConditionGroup item : conditionGroups) {
			if (mapKeyItemDesign.containsKey(item.getDecisionItemDesignId())){
				item.setDecisionItemDesignId(mapKeyItemDesign.get(item.getDecisionItemDesignId()).toString());
			}
		}
		
		return conditionGroups;
	}

	/**
	 * 
	 * @param mapKeyInput
	 * @param itemDesigns
	 * @return
	 */
	private List<DecisionTableItemDesignBean> getChangeItemValue(Map<String, Long> mapKeyInput,
			List<DecisionTableItemDesignBean> itemDesigns) {

		for(DecisionTableItemDesignBean item : itemDesigns) {
			if (mapKeyInput.containsKey(item.getItemValue())){
				item.setItemValue(mapKeyInput.get(item.getItemValue()).toString());
			}
		}
		
		return itemDesigns;
	}

	@Override
	public void modifyDesignStatus(DecisionTable decisionTable, CommonModel common) {

		// Check exist
		// Get value of master table of decision
		DecisionTable dc = decisionTableRepository.findOneByDecisionTbId(decisionTable.getDecisionTbId());
		if (dc == null) {
			throw new BusinessException(ResultMessages.error()
					.add(CommonMessageConst.ERR_SYS_0037, 
							MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0007)));
		}
		
		/*if(dc.getModuleId() == null) {
			projectService.validateProject(dc.getProjectId(),true);
		} else {
			moduleService.validateModule(dc.getModuleId(), true);
		}*/

		common.setProjectId(dc.getProjectId());
		validate(dc.getProjectId(), dc.getModuleId(), common);
		
		// Time for update
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		// Setting time to check concurrence
		decisionTable.setSysDatetime(currentTime);
		
		if(DbDomainConst.DesignStatus.UNDER_DESIGN.equals(decisionTable.getDesignStatus())) {
			decisionTable.setDesignStatus(DbDomainConst.DesignStatus.FIXED);
			int resultModify = decisionTableRepository.modifyDesignStatus(decisionTable);
			
			if (resultModify == 0) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, 
						MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0007)));
			}
		} else {
			decisionTable.setDesignStatus( DbDomainConst.DesignStatus.UNDER_DESIGN);
			int resultModify = decisionTableRepository.modifyDesignStatus(decisionTable);
			
			if (resultModify == 0) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, 
						MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0007)));
			}
			
			// Get project
			/*DungNN -20151027 - remove because not unnecessary
			Project project = projectRepository.findById(projectId, updateBy);
			project.setUpdatedBy(updateBy);
			project.setUpdatedDate(currentTime);
			project.setStatus(DbDomainConst.DesignStatus.UNDER_DESIGN);
			projectRepository.modifyDesignStatus(project);*/
		}
	}

	private List<FormulaDetail> lstFormulaDetails = new ArrayList<FormulaDetail>();
	private Long registerFormulaSetting(FormulaDefinition formulaDefinition) {
		Long formulaDefinitionId  = -1L;
		int result = formulaDefinitionRepository.registerOneFormulaDefinition(formulaDefinition);
		if(result > 0 ){
			formulaDefinitionId = formulaDefinition.getFormulaDefinitionId();
			for (FormulaDetail formulaDetail : formulaDefinition.getFormulaDefinitionDetails()) {
				if(formulaDetail.getType() != null){
					switch (formulaDetail.getType().intValue()) {
					case BusinessDesignConst.FormulaBuilder.TYPE_IN_DECISIONTABLE:
						String indexId = formulaDetail.getParameterId().substring(1, formulaDetail.getParameterId().length());
						Long id = getKeyOfParameter(indexId, DecisionTableConst.PARAMETER_SCOPE_INPUTBEAN);
						if(id.compareTo(0L)<=0){
							throw new BusinessException(ResultMessages.error()
									.add(DecisionTableMessageConst.SC_DECISIONTABLE_0050, 
											MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0052)));
						}
						formulaDetail.setParameterId(id.toString());
						break;
					default:
						break;
					}
				}
				
				if(formulaDetail.getFormulaMethodInputs() != null) {
					for (FormulaMethodInput objMethodInput : formulaDetail.getFormulaMethodInputs()) {
						if (objMethodInput.getParameterId() != null && objMethodInput.getParameterId().length() > 0) {
							// Param
							String parameterId = objMethodInput.getParameterId().substring(1, objMethodInput.getParameterId().length());
							String paramScope = objMethodInput.getParameterId().substring(0, 1);
							
							//parameterId = parameterId.substring(1, parameterId.length());
							Long id = getKeyOfParameter(parameterId, Integer.valueOf(paramScope));
							if (id.compareTo(0L) <= 0) {
								throw new BusinessException(ResultMessages.error()
										.add(DecisionTableMessageConst.SC_DECISIONTABLE_0051, 
												MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0052)));
							}
							objMethodInput.setParameterId(id.toString());
						} else {
							objMethodInput.setParameterId(null);
							objMethodInput.setParameterScope(null);
						}
					}
				}
				formulaDetail.setFormulaDefinitionId(formulaDefinitionId);
				lstFormulaDetails.add(formulaDetail);
			}
		}
		return formulaDefinitionId;
	}
	
	private int registerFormulaDetail(List<FormulaDetail> lstFormulaDetails){
		Long startSequence = 0L;
		if(lstFormulaDetails.size() >0){
//			Long sequenceFormulaDetail = formulaDefinitionRepository.getSequencesFormulaDetail(lstFormulaDetails.size() - 1);
//			startSequence = sequenceFormulaDetail - (lstFormulaDetails.size() - 1);
//			for(FormulaDetail objDetail : lstFormulaDetails){
//				objDetail.setFormulaDetailId(startSequence);
//				startSequence ++;
//			}
			
			Long sequenceFormulaDetail = formulaDefinitionRepository.getSequencesFormulaDetail(lstFormulaDetails.size() - 1);
			startSequence = sequenceFormulaDetail - (lstFormulaDetails.size() - 1);

			int totalMessage = 0;
			for (FormulaDetail objDetail : lstFormulaDetails) {
				if (CollectionUtils.isNotEmpty(objDetail.getFormulaMethodInputs())) {
					totalMessage += objDetail.getFormulaMethodInputs().size();
				}
			}

			int size = totalMessage - 1;
			Long sequenceParameter = formulaDefinitionRepository.getSequencesFormulaMethodInput(size);
			Long startFormulaMethodInputSequence = sequenceParameter - (size);
			
			for (FormulaDetail objDetail : lstFormulaDetails) {
				objDetail.setFormulaDetailId(startSequence);
				startSequence++;

				if (CollectionUtils.isNotEmpty(objDetail.getLstParameterIndex())) {
					for (BDParameterIndex index : objDetail.getLstParameterIndex()) {
						index.setTableType(BusinessDesignConst.ParameterIndex.TABLE_FORMULA_DETAIL_PARAMETER);
						index.setTableId(objDetail.getFormulaDetailId());
						parseParameterIndex(index);
					}
				}

				if (CollectionUtils.isNotEmpty(objDetail.getFormulaMethodInputs())) {
					for (FormulaMethodInput obj : objDetail.getFormulaMethodInputs()) {
						obj.setFormulaMethodInputId(startFormulaMethodInputSequence);
						startFormulaMethodInputSequence++;

						// index of pass parameter
						if (CollectionUtils.isNotEmpty(obj.getLstParameterIndex())) {
							for (BDParameterIndex index : obj.getLstParameterIndex()) {
								if (index != null) {
									index.setTableId(obj.getFormulaMethodInputId());
								}
							}
						}
					}
				}
			}
		}
		
		int result = formulaDefinitionRepository.registerFormulaDetails(lstFormulaDetails);
		return result;
	}
	
	private void parseParameterIndex(BDParameterIndex index) {
		if (StringUtils.isNotBlank(index.getParameterId())) {
			// get id of parameter id
			String indexId = index.getParameterId().substring(1, index.getParameterId().length());
			String scope = index.getParameterId().substring(0, 1);
			Long id = getKeyOfParameter(indexId, Integer.valueOf(scope));
			index.setParameterId(id.toString());

			if (StringUtils.isNotBlank(index.getParameterIndexId())) {
				// get id of parameter index id
				indexId = index.getParameterIndexId();
				if (!BusinessDesignConst.ParameterIndex.INDEX_TYPE_CUSTOMIZE.equals(index.getParameterIndexType())) {
					indexId = indexId.substring(1, index.getParameterIndexId().length());
						indexId = getKeyOfParameter(indexId, index.getParameterIndexType()).toString();
				}
				index.setParameterIndexId(indexId);
			} else {

			}
		}
	}
	
	private Long getKeyOfParameter(String parameterId, Integer scope){
		Long id = -1L;
		if(DecisionTableConst.PARAMETER_SCOPE_INPUTBEAN.equals(scope)){
			id = mKeyInClient.getOrDefault(parameterId, -1L);
		}else if(DecisionTableConst.PARAMETER_SCOPE_OUTPUTBEAN.equals(scope)){
			id = mKeyOuClient.getOrDefault(parameterId, -1L);
		}
		return id;
	}

	@Override
	public List<BusinessDesign> getListBusinessDesign(DecisionTable decisionTable) {
		List<BusinessDesign> listBusinessDesigns = decisionTableRepository.findAllBussinessLogicByDecisionId(decisionTable.getDecisionTbId());
		return listBusinessDesigns;
	}

	@Override
	public void insertProblemList(DecisionTable decisionTable,List<ProblemList> listOfProblem, CommonModel common) {
		
		if( listOfProblem == null || !listOfProblem.isEmpty()) problemListRepository.multiRegisterProblem(listOfProblem);
		
		//Update status of blogic
		List<BusinessDesign> listBusinessDesigns = decisionTableRepository.findAllBussinessLogicByDecisionId(decisionTable.getDecisionTbId());
		for(BusinessDesign obj : listBusinessDesigns) {
			if(!listOfProblem.isEmpty()) {
				BusinessDesign businessDesign = businessDesignRepository.findBusinessLogicInformation(obj.getBusinessLogicId(), common.getWorkingLanguageId(), common.getWorkingProjectId());
				businessDesign.setDesignStatus(DbDomainConst.DesignStatus.UNDER_DESIGN);
			}
		}
	}

    @Override
    public List<CommonObjectAttribute> findCommonObjectAttributeByCommonObject(Long commonObjectDefinitionId, Long languageId, Long projectId) {
        List<CommonObjectAttribute> lstCommonObjectAttributes = new ArrayList<CommonObjectAttribute>();

        CommonObjectDefinition commonObjectDefinition = commonObjectDefinitionSharedService.getCommonObjectDefinition(commonObjectDefinitionId, null, projectId, languageId,null);

        for (CommonObjectAttribute objDetail : commonObjectDefinition.getCommonObjectAttributes()) {
            lstCommonObjectAttributes.add(objDetail);
        }
        return lstCommonObjectAttributes;
    }

    @Override
    public List<ExternalObjectAttribute> findExternalObjectAttributeByCommonObject(Long externalObjectDefinitionId, Long languageId, Long projectId) {
        List<ExternalObjectAttribute> lstExternalObjectAttributes = new ArrayList<ExternalObjectAttribute>();

        ExternalObjectDefinition externalObjectDefinition = externalObjectDefinitionSharedService.getExternalObjectDefinition(externalObjectDefinitionId, null, projectId, languageId,1);

        for (ExternalObjectAttribute objDetail : externalObjectDefinition.getExternalObjectAttributes()) {
            lstExternalObjectAttributes.add(objDetail);
        }

        return lstExternalObjectAttributes;
    }
    
    /**
     * 
     * @param projectId
     * @param moduleId
     */
    private void validate(Long projectId, Long moduleId, CommonModel common) {
    	if(moduleId == null) {
			projectService.validateProject(common);
		} else {
			moduleService.validateModule(moduleId, common.getCreatedBy(), common.getWorkingProjectId(), true);
		}
    }
    
    private void modifyAffected(DecisionTable decisionTable,CommonModel common) {
		ImpactChangeJobControl jobControl = new ImpactChangeJobControl();
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		jobControl.setJobAppCd(DbDomainConst.ImpactChangeDesign.JOB_APP_CD);
		jobControl.setProjectId(String.valueOf(decisionTable.getProjectId()));
		jobControl.setModuleId(String.valueOf(decisionTable.getModuleId()));
		jobControl.setCreatedBy(String.valueOf(common.getUpdatedBy()));
		jobControl.setImpactType(String.valueOf(DbDomainConst.FromResourceType.DECISION_TABLE));
		jobControl.setImpactId(String.valueOf(decisionTable.getDecisionTbId()));
		jobControl.setCurAppStatus(GenerateAppStatus.INIT);
		jobControl.setAddDateTime(currentTime);
		jobControl.setUpdDateTime(currentTime);
		jobControl.setJobArgNm6(ImpactChangeDesign.CASE_MODIFY);
		
		//check the same business type job.
		Long count = impactChangeRepository.countImpactChangeByType(jobControl);
		if(count == 0)
			impactChangeRepository.registerImpactChange(jobControl);
		else
			impactChangeRepository.modifyImpactChange(jobControl);
    }
    
    private void deleteAffected(DecisionTable decisionTable,CommonModel common) {
		ImpactChangeJobControl jobControl = new ImpactChangeJobControl();
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		jobControl.setJobAppCd(DbDomainConst.ImpactChangeDesign.JOB_APP_CD);
		jobControl.setProjectId(String.valueOf(decisionTable.getProjectId()));
		jobControl.setModuleId(String.valueOf(decisionTable.getModuleId()));
		jobControl.setCreatedBy(String.valueOf(common.getCreatedBy()));
		jobControl.setImpactType(String.valueOf(DbDomainConst.FromResourceType.DECISION_TABLE));
		jobControl.setImpactId(String.valueOf(decisionTable.getDecisionTbId()));
		jobControl.setCurAppStatus(GenerateAppStatus.INIT);
		jobControl.setAddDateTime(currentTime);
		jobControl.setUpdDateTime(currentTime);
		jobControl.setJobArgNm6(ImpactChangeDesign.CASE_DELETE);
		jobControl.setJobArgNm7(decisionTable.getDecisionTbCode());
		
		//check the same business type job.
		Long count = impactChangeRepository.countImpactChangeByType(jobControl);
		if(count == 0)
			impactChangeRepository.registerImpactChange(jobControl);
		else
			impactChangeRepository.modifyImpactChange(jobControl);
    }
}
