package org.terasoluna.qp.domain.service.generatesourcecode;

import java.io.File;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.DecisionTable;
import org.terasoluna.qp.domain.model.DecisionTableConditionGroup;
import org.terasoluna.qp.domain.model.DecisionTableConditionItem;
import org.terasoluna.qp.domain.model.DecisionTableInputBean;
import org.terasoluna.qp.domain.model.DecisionTableItemDesignBean;
import org.terasoluna.qp.domain.model.DecisionTableOutputBean;
import org.terasoluna.qp.domain.model.FormulaDetail;
import org.terasoluna.qp.domain.model.FormulaMethodInput;
import org.terasoluna.qp.domain.model.FormulaMethodOutput;
import org.terasoluna.qp.domain.model.FunctionMaster;
import org.terasoluna.qp.domain.model.FunctionMethod;
import org.terasoluna.qp.domain.model.FunctionMethodInput;
import org.terasoluna.qp.domain.model.FunctionMethodOutput;
import org.terasoluna.qp.domain.model.GenerateSourceCode;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.BDParameterIndexRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.FormulaDefinitionRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableConditionGroupRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableConditionItemRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableInputBeanRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableItemDesignBeanRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableOutputBeanRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableRepository;
import org.terasoluna.qp.domain.repository.functionmaster.FunctionMasterRepository;
import org.terasoluna.qp.domain.repository.generatesourcecode.GenerateSourceCodeRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;
import org.terasoluna.qp.domain.service.decisiontable.DecisionTableUtils;
import org.terasoluna.qp.domain.service.generatesourcecode.CommonComponentGenerateHandler.TypeOfDataType;
import org.terasoluna.qp.domain.service.generatesourcecode.GenerateSourceCodeConst.BusinessLogicGenerate;
import org.terasoluna.qp.domain.service.generatesourcecode.GenerateSourceCodeConst.ModuleScope;

/**
 * Handler for generating Decision table class
 * 
 * @author hunghx
 * @version 1.0
 */
@Component(value="DecisionTableGenerateHandler")
public class DecisionTableGenerateHandler extends GenerationHandler { 
	
	@Inject
	GenerateSourceCodeRepository generateSourceCodeRepository;
	
	@Inject
	DecisionTableRepository decisionTableRepository;
	
	@Inject
	DecisionTableInputBeanRepository decisionTableInputBeanRepository;
	
	@Inject
	DecisionTableOutputBeanRepository decisionTableOutputBeanRepository;
	
	@Inject
	DecisionTableItemDesignBeanRepository decisionTableItemDesignBeanRepository;
	
	@Inject
	DecisionTableConditionGroupRepository decisionTableConditionGroupRepository;
	
	@Inject
	DecisionTableConditionItemRepository decisionTableConditionItemRepository;
	
	@Inject
	FormulaDefinitionRepository formulaDefinitionRepository;
	
	@Inject
	BDParameterIndexRepository bDParameterIndexRepository;
	
	@Inject
	DetailServiceImpHandler detailServiceImpHandler;
	
	@Inject
	FunctionMasterRepository functionMasterRepository;
	
	private static final String TEMPLATE_DECISION_INPUT_BEAN = "decision_input_bean_java.ftl";
	private static final String TEMPLATE_DECISION_OBJ_OF_INPUT_BEAN = "decision_object_input_bean_java.ftl";
	private static final String TEMPLATE_DECISION_OUTPUT_BEAN = "decision_output_bean_java.ftl";
	private static final String TEMPLATE_DECISION_OBJ_OF_OUTPUT_BEAN = "decision_object_output_bean_java.ftl";
	private static final String TEMPLATE_DECISION_BLOGIC_SERVICE = "decision_logic_design_service_java.ftl";
	private static final String TEMPLATE_DECISION_BLOGIC_SERVICE_IMPL = "decision_logic_design_service_impl_java.ftl";

	// Template pattern
	private static final String INIT_OBJECT = "{0} {1} = new {0}();";
	private static final String SETTER_PATTERN = "{0}({1});";
	private static final String IF_PARTTEN  = "if";
	private static final String ELSE_IF_PARTTEN  = " else ";
	private static final String OPEN_BRACKET  = "{";
	private static final String CLOSE_BRACKET  = "}";
	private static final String AND_CONDITION = " && ";
	private static final String OPEN_SINGLE_QUOTE = "(";
	private static final String CLOSE_SINGLE_QUOTE = ")";
	private static final String SPACE = " ";
	private static final String NL = "\n\t\t";
	private static final String ONE_TAB = "\t";
	private static final String BLANK = StringUtils.EMPTY;

	private static final Integer GROUP_ITEM = 2;
	private static final int TYPE_INPUT_SCOPE = 1;
	private static final int TYPE_OUTPUT_SCOPE = 0;
	
	Map<String, DecisionTableInputBean> mAllInOfDecision = new HashMap<String, DecisionTableInputBean>();
	Map<String, DecisionTableOutputBean> mAllOuOfDecision = new HashMap<String, DecisionTableOutputBean>();
	// Not used
	Map<String, DecisionTableInputBean> mInOfDecision = new HashMap<String, DecisionTableInputBean>();
	Map<String, DecisionTableOutputBean> mOuOfDecision = new HashMap<String, DecisionTableOutputBean>();
	Map<Integer, String> mIdxColumnForGetSet = new HashMap<Integer, String>();
	Map<Integer, String> mIdxColumnForRowspan = new HashMap<Integer, String>();
	Map<Integer, Integer> mIdxColumnDataType = new HashMap<Integer, Integer>();
	
	private Map<String, List<?>> mAllParentAndSeflByLevelOfIn = new HashMap<String, List<?>>();
	
	private StringBuilder pathPackage;
	private Project project;
	private DecisionTable decisionCurrent;
	private BLogicHandlerIo bLogicHandlerIo;

	private boolean isBatchCurrent = false;
	
	private void init(GenerateSourceCode generateSourceCode, CommonModel comon) {
		project = generateSourceCode.getProject();
		// Setting pakage folder source
		String[] split = null;
		if (StringUtils.isNotBlank(generateSourceCode.getProject().getPackageName())) {
			split = generateSourceCode.getProject().getPackageName().split("\\.");
		}
		if (split != null && split.length > 0) {
			pathPackage = new StringBuilder();
			for (String str : split) {
				pathPackage.append(str).append(File.separator);
			}
		}
		
		List<FunctionMaster> functionMaster = functionMasterRepository.findFunctionMasterByProjectId(project.getProjectId());
		List<FunctionMethod> functionMethods = functionMasterRepository.findFuntionMethodByProjectId(project.getProjectId());
		List<FunctionMethodInput> functionMethodInputs = functionMasterRepository.findFunctionMethodInputByProjectId(project.getProjectId());
		List<FunctionMethodOutput> functionMethodOutputs = functionMasterRepository.findFunctionMethodOutputByProjectId(project.getProjectId());

		detailServiceImpHandler.settingFuncDesignDataOfProject(functionMaster, functionMethods, functionMethodInputs, functionMethodOutputs);
		
		this.bLogicHandlerIo = new BLogicHandlerIo();
		this.bLogicHandlerIo.setBlogicInSyntax("in");
		this.bLogicHandlerIo.setBlogicOutputSyntax("ou");
		this.bLogicHandlerIo.setFunctionMasters(functionMaster);
		this.bLogicHandlerIo.setFunctionMethods(functionMethods);
		this.bLogicHandlerIo.setModuleScope(ModuleScope.DECISION);
	}
	
	@Override
	public void handle(GenerateSourceCode generateSourceCode,  CommonModel comon) {
		// Initializing
		init(generateSourceCode, comon);
		
		List<Module> listOfModules = new ArrayList<Module>();
		if(generateSourceCode.getScopeGenerateSource().equals(GenerateSourceCodeConst.PROJECT_SCOPRE)) {
			listOfModules = generateSourceCode.getModules();
		} else if(generateSourceCode.getScopeGenerateSource().equals(GenerateSourceCodeConst.MODULE_SCOPRE)){
			for(Module module : generateSourceCode.getModules()) {
				if(module.getSelectedGenerate() == GenerateSourceCodeConst.MODULE_SELECTED_GENERATE) {
					listOfModules.add(module);
				}
			}
		}

		// Preparing data for generate controller
		if (CollectionUtils.isEmpty(listOfModules)) return;
		List<DecisionTable> lstDecision = decisionTableRepository.findAllDecisionByProjectId(project.getProjectId());
		if(lstDecision.isEmpty()) return;
		preparedDataForGenerateSource(lstDecision);
		
		// Main process for generate decision
		generateSourceBean(lstDecision, generateSourceCode);
		generateLogicDesign(lstDecision, generateSourceCode);
	}

	private void generateLogicDesign(List<DecisionTable> lstDecision, GenerateSourceCode generateSourceCode) {
		OutputStreamWriter out = null;
		
		if(CollectionUtils.isNotEmpty(lstDecision)) {
			List<DecisionTable> lstDecisionBatch = new ArrayList<DecisionTable>();
			List<DecisionTable> lstDecisionOnline = new ArrayList<DecisionTable>();
			
			for (DecisionTable decisionTable : lstDecision) {
				if (decisionTable.getModuleId() == null) {
					lstDecisionOnline.add(decisionTable);
					lstDecisionBatch.add(decisionTable);
				} else if(BusinessDesignConst.MODULE_TYPE_ONLINE.equals(decisionTable.getModuleType())) {
					lstDecisionOnline.add(decisionTable);
				} if(BusinessDesignConst.MODULE_TYPE_BATCH.equals(decisionTable.getModuleType()) && Boolean.TRUE.equals(generateSourceCode.getBatchModuleFlg())) {
					lstDecisionBatch.add(decisionTable);
				}
			}

			try {
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("package", GenerateSourceCodeUtil.normalizedPackageName(project.getPackageName()));
				
				String outputDir = createFileOutputFolder(StringUtils.EMPTY, BusinessLogicGenerate.SERVICE, generateSourceCode.getSourcePathDomain());
				data.put("lstDecision", lstDecisionOnline);
				data.put("place", "domain.decision");
				this.isBatchCurrent = false;
				settingGenerateBlogicDesignContent(lstDecisionOnline);
				
				this.process(data, TEMPLATE_DECISION_BLOGIC_SERVICE, outputDir
						+ "DecisionLogicDesignCommonService" 
						+ GenerateSourceCodeConst.JAVA_EXTEND);
				
				this.process(data, TEMPLATE_DECISION_BLOGIC_SERVICE_IMPL, outputDir
						+ "DecisionLogicDesignCommonServiceImpl" 
						+ GenerateSourceCodeConst.JAVA_EXTEND);
				
				if(Boolean.TRUE.equals(generateSourceCode.getBatchModuleFlg())) {
					String outputDirBatch = createFileOutputFolder(StringUtils.EMPTY, BusinessLogicGenerate.BATCH, generateSourceCode.getSourcePathBatch());
					data.put("lstDecision", lstDecisionBatch);
					data.put("place", "batch.decision");
					this.isBatchCurrent = true;
					settingGenerateBlogicDesignContent(lstDecisionBatch);
					
					this.process(data, TEMPLATE_DECISION_BLOGIC_SERVICE, outputDirBatch
							+ "DecisionLogicDesignCommonService" 
							+ GenerateSourceCodeConst.JAVA_EXTEND);
					
					this.process(data, TEMPLATE_DECISION_BLOGIC_SERVICE_IMPL, outputDirBatch
							+ "DecisionLogicDesignCommonServiceImpl" 
							+ GenerateSourceCodeConst.JAVA_EXTEND);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(out);
			}
		}
	}
	
	/**
	 * Build content of Blogic design
	 * 
	 * @param lstDecision
	 * @return
	 */
	private void settingGenerateBlogicDesignContent(List<DecisionTable> lstDecision) {
		List<DecisionTableItemDesignBean> lstItemDesign = decisionTableItemDesignBeanRepository.findAllDecisionItemDesignByLstDecisionInfor(lstDecision);

		// Get all id item design
		List<DecisionTableConditionGroup> lstConditionGroup = decisionTableConditionGroupRepository.findConditionGroupById(lstItemDesign);
		List<DecisionTableConditionItem> lstConditionItem = new ArrayList<DecisionTableConditionItem>();
		if (CollectionUtils.isNotEmpty(lstConditionGroup)) {
			lstConditionItem = decisionTableConditionItemRepository.findConditionItemById(lstConditionGroup);
			if (CollectionUtils.isNotEmpty(lstConditionItem)) {
				// Get FormulaDefinitionDetails
				List<FormulaDetail> lstFormulaDetails = getListFormulaDetails(lstConditionItem);
				lstConditionItem = getListConditionItemUpdate(lstFormulaDetails, lstConditionItem);
			}
		}

		for (DecisionTable dt : lstDecision) {
			Map<String, DecisionTableInputBean> mIn = new HashMap<String, DecisionTableInputBean>();
			Map<String, DecisionTableOutputBean> mOu = new HashMap<String, DecisionTableOutputBean>();
			List<DecisionTableItemDesignBean> condLst = new ArrayList<DecisionTableItemDesignBean>();
			List<DecisionTableItemDesignBean> actLst = new ArrayList<DecisionTableItemDesignBean>();
			List<DecisionTableConditionGroup> condGrLst = new ArrayList<DecisionTableConditionGroup>();
			List<DecisionTableConditionItem> condItemLst = new ArrayList<DecisionTableConditionItem>();

			for (DecisionTableItemDesignBean dtid : lstItemDesign) {
				for (DecisionTableInputBean in : dt.getInputLst()) {
					mAllInOfDecision.put(in.getDecisionInputBeanId(), in);
					if (dtid.getItemValue().equals(in.getDecisionInputBeanId())) {
						mIn.put(dtid.getItemValue(), in);
						condLst.add(dtid);
						for (DecisionTableConditionGroup dtcg : lstConditionGroup) {
							if (dtid.getDecisionItemDesignId().equals(dtcg.getDecisionItemDesignId())) {
								condGrLst.add(dtcg);
								for (DecisionTableConditionItem dtci : lstConditionItem) {
									if (dtcg.getConditionGroupId().equals(dtci.getConditionGroupId())) {
										condItemLst.add(dtci);
									}
								}
							}
						}
					}
				}
				
				for (DecisionTableOutputBean ou : dt.getOutputLst()) {
					mAllOuOfDecision.put(ou.getDecisionOutputBeanId(), ou);
					if (dtid.getItemValue().equals(ou.getDecisionOutputBeanId())) {
						mOu.put(dtid.getItemValue(), ou);
						actLst.add(dtid);
						for (DecisionTableConditionGroup dtcg : lstConditionGroup) {
							if (dtid.getDecisionItemDesignId().equals(dtcg.getDecisionItemDesignId())) {
								condGrLst.add(dtcg);
								for (DecisionTableConditionItem dtci : lstConditionItem) {
									if (dtcg.getConditionGroupId().equals(dtci.getConditionGroupId())) {
										condItemLst.add(dtci);
									}
								}
							}
						}
					}
				}
			}
			
			if (CollectionUtils.isEmpty(condLst) || CollectionUtils.isEmpty(actLst) 
					|| CollectionUtils.isEmpty(condGrLst) || CollectionUtils.isEmpty(condItemLst)) {
				dt.setBlogicDesignContent(StringUtils.EMPTY);
				break;
			}
			
			if(dt.getDecisionTbId() == 201){
				System.out.println("");
			}
			
			Map<String, List<?>> mAllDecisionRefer = detailServiceImpHandler
					.getAllParentAndSeflByLevelOfInOutObj(ModuleScope.DECISION, dt.getInputLst(), null, dt.getOutputLst());
			this.bLogicHandlerIo.setmAllParentAndSeflByLevelOfInOutObj(mAllDecisionRefer);
			this.bLogicHandlerIo.setDecisionTable(dt);
			
			dt.setBlogicDesignContent(generateBlogicDesignContentItem(dt, condLst, actLst, condGrLst, condItemLst));
		}
	}
	
	private String getStardEndSettingObj(Map<String, DecisionTableOutputBean> mapNotDuplicate, boolean isStart){
		StringBuilder declareObj = new StringBuilder(StringUtils.EMPTY);

		if (isStart) {
			Iterator<Map.Entry<String, DecisionTableOutputBean>> entries = mapNotDuplicate.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry<String, DecisionTableOutputBean> entry = entries.next();
				DecisionTableOutputBean value = entry.getValue();
				String dataTypeObjByOut
					= getPackageName(this.decisionCurrent, value) + GenerateSourceCodeUtil.normalizedClassName(detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.DECISION, BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, value));
				declareObj.append(NL).append(
						MessageFormat.format(INIT_OBJECT, dataTypeObjByOut, detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.DECISION, BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, value)));
			}
		} else {
			Iterator<Map.Entry<String, DecisionTableOutputBean>> entries = mapNotDuplicate.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry<String, DecisionTableOutputBean> entry = entries.next();
				DecisionTableOutputBean value = entry.getValue();
				declareObj.append(NL).append(
						MessageFormat.format(SETTER_PATTERN, "ou.set"+GenerateSourceCodeUtil.normalizedClassName(value.getDecisionOutputBeanCode()), value.getDecisionOutputBeanCode()));
			}
		}

		return declareObj.toString()!=null?declareObj.toString():StringUtils.EMPTY;
	}
	
	private List<DecisionTableOutputBean> getMapObjectParentGrandOfOutputBean(DecisionTableOutputBean dtob, List<DecisionTableOutputBean> lstDtob, List<DecisionTableOutputBean> lstParentObj) {
		lstParentObj = (CollectionUtils.isNotEmpty(lstParentObj)==true)?lstParentObj : new ArrayList<DecisionTableOutputBean>();
		for (DecisionTableOutputBean item : lstDtob) {
			if(dtob.getParentDecisionOutputBeanId().equals(item.getDecisionOutputBeanId()) 
					&&  item.getParentDecisionOutputBeanId() == null) {
				lstParentObj.add(item);
				break;
			} else if(dtob.getParentDecisionOutputBeanId().equals(item.getDecisionOutputBeanId()) 
					&& item.getParentDecisionOutputBeanId() != null) {
				lstParentObj.add(item);
				getMapObjectParentGrandOfOutputBean(item, lstDtob, lstParentObj);
			}
		}

		return lstParentObj;
	}

	private String generateBlogicDesignContentItem(DecisionTable decisionCurrent, List<DecisionTableItemDesignBean> condLst,
			List<DecisionTableItemDesignBean> actLst, List<DecisionTableConditionGroup> condGrLst, List<DecisionTableConditionItem> condItemLst) {
		
		// Assign decision current in process
		this.decisionCurrent = decisionCurrent;
		// Build mapping for formula
		this.mAllParentAndSeflByLevelOfIn = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(2, decisionCurrent.getInputLst(), null, decisionCurrent.getOutputLst());
		
		Map<String, DecisionTableOutputBean> mapNotDuplicate = new HashMap<String, DecisionTableOutputBean>();
		int count = 0;
		for (int idx = 0;  idx < (condLst.size() + actLst.size()) ; idx++) {
			if (idx < condLst.size()) {
				DecisionTableInputBean in = mAllInOfDecision.get(condLst.get(idx).getItemValue());
				String getterInput = detailServiceImpHandler.getterAndSetterOfParameter(2, mAllParentAndSeflByLevelOfIn, true, in.getDecisionInputBeanId(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, "in", null);
				
				mIdxColumnForGetSet.put(idx, getterInput);
				mIdxColumnDataType.put(idx, in.getDataType());
			} else {
				List<DecisionTableOutputBean> lstOutput = new ArrayList<DecisionTableOutputBean>();
				DecisionTableOutputBean ou = mAllOuOfDecision.get(actLst.get(count).getItemValue());
				if (ou.getParentDecisionOutputBeanId() == null) {
					// In the case output at level 0
					String setterOutput = detailServiceImpHandler.getterAndSetterOfParameter(2, mAllParentAndSeflByLevelOfIn, false, ou.getDecisionOutputBeanId(), BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, "ou", null);
					
					mIdxColumnForGetSet.put(idx, setterOutput);
				} else {
					List<DecisionTableOutputBean> lstParentObj = getMapObjectParentGrandOfOutputBean(ou, decisionCurrent.getOutputLst(), new ArrayList<DecisionTableOutputBean>());
					Collections.reverse(lstParentObj);
					// Get object lowest of output field
					DecisionTableOutputBean ouParent = lstParentObj.get(lstParentObj.size()-1);
					
					if (!mapNotDuplicate.containsKey(ouParent.getDecisionOutputBeanId())) {
						mapNotDuplicate.put(ouParent.getDecisionOutputBeanId(), ouParent);
					}

					lstOutput.add(ou);
					Map<String, List<?>> mAllParentAndSeflByLevelOfInOutChild = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(2, null, null, lstOutput);
					String setterOutput = detailServiceImpHandler.getterAndSetterOfParameter(2, mAllParentAndSeflByLevelOfInOutChild, false, ou.getDecisionOutputBeanId(), BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, ouParent.getDecisionOutputBeanCode(), null);

					mIdxColumnForGetSet.put(idx, setterOutput);
				}

				mIdxColumnDataType.put(idx, ou.getDataType());
				count++;
			}
		}
		
		String startDeclareObj = getStardEndSettingObj(mapNotDuplicate, true);
		String endSettingObjIsDeclare = getStardEndSettingObj(mapNotDuplicate, false);
		
		// Content of decision blogic design
		StringBuilder blogicDesignContent = new StringBuilder(StringUtils.EMPTY);
		// Storing logic id key and content blogic design
		Map<String, DecisionTableConditionGroup> mItemDesignId_GroupItem = new HashMap<String, DecisionTableConditionGroup>();
		// Storing logic id key and content blogic design
		Map<String, DecisionTableConditionItem> mGroupItemId_ConditionItem = new HashMap<String, DecisionTableConditionItem>();
		
		// Merge all decision item design
		List<DecisionTableItemDesignBean> itemDesign = new ArrayList<DecisionTableItemDesignBean>(condLst);
		itemDesign.addAll(actLst);
		
		// Preparing data for generate
		for (DecisionTableItemDesignBean dtid : itemDesign) {
			for (DecisionTableConditionGroup dtcg : condGrLst) {
				if (dtid.getDecisionItemDesignId().equals(dtcg.getDecisionItemDesignId())) {
					mItemDesignId_GroupItem.put(dtid.getDecisionItemDesignId(), dtcg);
					for (DecisionTableConditionItem dtci : condItemLst) {
						if (dtcg.getConditionGroupId().equals(dtci.getConditionGroupId())) {
							mGroupItemId_ConditionItem.put(dtcg.getConditionGroupId(), dtci);
						}
					}
				}
			}
		}

		blogicDesignContent.append(startDeclareObj).append(NL);
		int maxRowNum = getMaxRowNum(condGrLst);
		for(int rowIndex = 0; rowIndex <= maxRowNum; rowIndex++) {
			// Build one <tr>
			String tr = buildAllTdOneRow(rowIndex, condLst, actLst, condGrLst, condItemLst);
			if (!StringUtils.isEmpty(tr)) {
				blogicDesignContent.append(tr);
			}
		}
		blogicDesignContent.append(";");
		blogicDesignContent.append(endSettingObjIsDeclare);

		// Reset
		mapNotDuplicate = new HashMap<String, DecisionTableOutputBean>();
		mIdxColumnForGetSet = new HashMap<Integer, String>();
		mIdxColumnForRowspan = new HashMap<Integer, String>();

		return blogicDesignContent.toString();
	}

	private String buildAllTdOneRow(int rowIdx, List<DecisionTableItemDesignBean> condLst,
			List<DecisionTableItemDesignBean> actLst, List<DecisionTableConditionGroup> condGrLst, List<DecisionTableConditionItem> condItemLst) {
		
		StringBuilder allTdContent = new StringBuilder(StringUtils.EMPTY);

		int countColumnIdx = 0;
		for(int columnIdx = 0; columnIdx < (condLst.size() + actLst.size()); columnIdx++) {
			// Process start and if is here
			if (columnIdx == 0 && rowIdx == 0) {
				// => if(
				allTdContent.append(IF_PARTTEN).append(OPEN_SINGLE_QUOTE)
						.append(getTdContent(rowIdx, columnIdx, condLst.get(columnIdx), condGrLst, condItemLst));
			} else if (columnIdx == 0 && rowIdx > 0) {
				// } else if (
				allTdContent.append(ELSE_IF_PARTTEN).append(IF_PARTTEN).append(OPEN_SINGLE_QUOTE)
						.append(getTdContent(rowIdx, columnIdx, condLst.get(columnIdx), condGrLst, condItemLst));
			} else if (columnIdx < condLst.size()) {
				// in.getField() == 3
				if (!StringUtils.isEmpty(getTdContent(rowIdx, columnIdx, condLst.get(columnIdx), condGrLst, condItemLst))) {
					allTdContent.append(NL).append(ONE_TAB).append(AND_CONDITION)
							.append(getTdContent(rowIdx, columnIdx, condLst.get(columnIdx), condGrLst, condItemLst));
				}
			} else if (columnIdx == condLst.size()) {
				// ) { ou.setField(5)
				allTdContent.append(CLOSE_SINGLE_QUOTE).append(SPACE).append(OPEN_BRACKET)
						.append(getTdContent(rowIdx, columnIdx, actLst.get(countColumnIdx), condGrLst, condItemLst)).append(";");
				countColumnIdx++;
			} else {
				allTdContent.append(getTdContent(rowIdx, columnIdx, actLst.get(countColumnIdx), condGrLst, condItemLst)).append(";");
				countColumnIdx++;
			}
			
			if(countColumnIdx == actLst.size()) allTdContent.append(NL).append(CLOSE_BRACKET);
		}

		return allTdContent.toString();
	}

	private String getTdContent(int rowIndex, int tdIndex,
			DecisionTableItemDesignBean itemDataDesign,
			List<DecisionTableConditionGroup> condGrLst,
			List<DecisionTableConditionItem> condItemLst) {

		boolean isExistGroup = false;
		String tdContentDetail = null;
		for (DecisionTableConditionGroup dtcgItem : condGrLst) {
			// In the case same column && same row number
			if (itemDataDesign.getDecisionItemDesignId().equals(dtcgItem.getDecisionItemDesignId()) 
					&& dtcgItem.getRowNumber().equals(rowIndex)) {
				isExistGroup = true;
				tdContentDetail = getTdContentDetail(itemDataDesign, dtcgItem, condItemLst, rowIndex, tdIndex);
				break;
			}
		}

		if (isExistGroup && StringUtils.isEmpty(tdContentDetail)) {
			return BLANK;
		} else if (!isExistGroup){
			// rowspan > 1 just happen in condition input
			// And in the case rowpan > 1 will condition combine
			return mIdxColumnForRowspan.getOrDefault(tdIndex, StringUtils.EMPTY);
		}

		return tdContentDetail==null?StringUtils.EMPTY:tdContentDetail;
	}

	private String getTdContentDetail(
			DecisionTableItemDesignBean itemDataDesign,
			DecisionTableConditionGroup dtcGroupItem,
			List<DecisionTableConditionItem> condItemLst, 
			int rowIndex,
			int tdIndex) {

		List<String> valueLst = null;
		String strGetSet = mIdxColumnForGetSet.get(tdIndex);
		StringBuilder allItemInTd = new StringBuilder(StringUtils.EMPTY);
		int count = 0;
		
		for(DecisionTableConditionItem dtciItem : condItemLst) {
			// Item type 1 : input form keyboard, 2: input from formula setting
			int itemType = dtciItem.getItemType();
			String value = StringUtils.EMPTY;
			// In the case of item design is type condition
			if(TYPE_INPUT_SCOPE == itemDataDesign.getItemType()
					&& dtcGroupItem.getConditionGroupId().equals(dtciItem.getConditionGroupId())) {
				// Get Operator setting
				String trOperator = DecisionTableUtils.getOperatorByDataType(mIdxColumnDataType.get(tdIndex) , dtciItem.getOpertatorType());
				// Check item have more setting value
				if(GROUP_ITEM.equals(dtcGroupItem.getGroupType())) {
					// Append open quote if first else append and condition
					allItemInTd.append(count == 0?OPEN_SINGLE_QUOTE:AND_CONDITION);
					count++;
				}
				// If value item setting from formula
				if (itemType != 1) {
//					value = generateConditionByFormula(dtciItem.getFormulaDefinitionDetails());
					valueLst = detailServiceImpHandler.generateConditionByFormula(this.bLogicHandlerIo, dtciItem.getFormulaDefinitionDetails());
					value = valueLst.get(0);
				} else {
					// Value input from keyboard
					// ex : in.getAge() + trOperator + item value
					value = DecisionTableUtils.settingValueByDataType(mIdxColumnDataType.get(tdIndex), dtciItem.getItemValue());
				}

				// In the case
				if (dtcGroupItem.getRowSpan() > 1 && !mIdxColumnForRowspan.containsKey(tdIndex)) {
					mIdxColumnForRowspan.put(tdIndex, MessageFormat.format(trOperator, strGetSet, value));
				}

				// In the case item column is here not set condition value
				allItemInTd.append(MessageFormat.format(trOperator, strGetSet, value));
			// In the case of item design type action	
			} else if (TYPE_OUTPUT_SCOPE == itemDataDesign.getItemType()
					&& dtcGroupItem.getConditionGroupId().equals(dtciItem.getConditionGroupId())) {
				if (itemType != 1) {
//					value = generateConditionByFormula(dtciItem.getFormulaDefinitionDetails());
					valueLst = detailServiceImpHandler.generateConditionByFormula(this.bLogicHandlerIo, dtciItem.getFormulaDefinitionDetails());
					value = valueLst.get(0);
				} else {
					value = DecisionTableUtils.settingValueByDataType(mIdxColumnDataType.get(tdIndex), dtciItem.getItemValue());
				}
				allItemInTd.append(NL).append(ONE_TAB).append(strGetSet).append("(").append(value).append(")");
			}
		}

		// End close single quote
		if(GROUP_ITEM.equals(dtcGroupItem.getGroupType())) allItemInTd.append(CLOSE_SINGLE_QUOTE);

		return allItemInTd.toString();
	}

	private int getMaxRowNum(List<DecisionTableConditionGroup> condGrLst) {
		int maxNum = condGrLst.get(0).getRowNumber();
		for (DecisionTableConditionGroup item : condGrLst) 
			if (maxNum < item.getRowNumber()) maxNum = item.getRowNumber();
		
		return maxNum;
	}

	private void preparedDataForGenerateSource(List<DecisionTable> lstDecision) {
		// Get all input bean of decision
		List<DecisionTableInputBean> inputLst = decisionTableInputBeanRepository.findAllDecisionInputBeanByListDecisionId(lstDecision);
		// Get all output bean of decision
		List<DecisionTableOutputBean> outputLst = decisionTableOutputBeanRepository.findAllDecisionOutputBeanByListDecisionId(lstDecision);

		// Rearrange data from input & output for each decision master
		for (DecisionTable decision : lstDecision) {
			decision.setDecisionTbName(WordUtils.capitalize(decision.getDecisionTbCode()));
			if (decision.getModuleCode() == null) {
				decision.setModuleCode(StringUtils.EMPTY);
			}
			List<DecisionTableInputBean> inLst = new ArrayList<DecisionTableInputBean>();
			for (DecisionTableInputBean decisionTableInputBean : inputLst) {
				if (decision.getDecisionTbId().toString().equals(decisionTableInputBean.getDecisionTbId())) {
					inLst.add(decisionTableInputBean);
				}
			}
			decision.setInputLst(inLst);

			List<DecisionTableOutputBean> outLst = new ArrayList<DecisionTableOutputBean>();
			for (DecisionTableOutputBean decisionTableOutputBean : outputLst) {
				if (decision.getDecisionTbId().toString().equals(decisionTableOutputBean.getDecisionTbId())) {
					outLst.add(decisionTableOutputBean);
				}
			}
			decision.setOutputLst(outLst);
		}
	}

	private void generateSourceBean(List<DecisionTable> lstDecision, GenerateSourceCode generateSourceCode) {
		OutputStreamWriter out = null;
		try {
			
			for (DecisionTable decisionTable : lstDecision) {
				
				String javaFileName = GenerateSourceCodeUtil.normalizedClassName(decisionTable.getDecisionTbCode());

				Map<String, Object> data = new HashMap<String, Object>();
				data.put("package", GenerateSourceCodeUtil.normalizedPackageName(project.getPackageName()));
				data.put("decision", decisionTable);
				data.put("moduleCode", (StringUtils.isNotEmpty(decisionTable.getModuleCode())?"." + decisionTable.getModuleCode():StringUtils.EMPTY));
				data.put("singleInputList", getSingleListInputBean(decisionTable.getInputLst()));
				generateObjectInputBeanLst(decisionTable, generateSourceCode, getObjectListInputBean(decisionTable.getInputLst()));
				data.put("singleOutputList", getSingleListOutputBean(decisionTable.getOutputLst()));
				generateObjectOutputBeanLst(decisionTable, generateSourceCode, getObjectListOutputBean(decisionTable.getOutputLst()));
				
				if(decisionTable.getModuleId() == null){
					data.put("place", "domain.decision");
					data.put("isdomain", true);
					String outputDir = createFileOutputFolder(WordUtils.uncapitalize(decisionTable.getModuleCode()), BusinessLogicGenerate.SERVICE, generateSourceCode.getSourcePathDomain());
					this.process(data, TEMPLATE_DECISION_INPUT_BEAN, outputDir
							+ javaFileName 
							+ BusinessLogicGenerate.SUFFIX_INPUT_BEAN 
							+ GenerateSourceCodeConst.JAVA_EXTEND);
					this.process(data, TEMPLATE_DECISION_OUTPUT_BEAN, outputDir
							+ javaFileName
							+ BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN
							+ GenerateSourceCodeConst.JAVA_EXTEND);
					
					if(Boolean.TRUE.equals(generateSourceCode.getBatchModuleFlg())) {
						String outputDirBatch = createFileOutputFolder(WordUtils.uncapitalize(decisionTable.getModuleCode()), BusinessLogicGenerate.BATCH, generateSourceCode.getSourcePathBatch());
						data.put("isdomain", false);
						data.put("place", "batch.decision");
						this.process(data, TEMPLATE_DECISION_INPUT_BEAN, outputDirBatch
								+ javaFileName 
								+ BusinessLogicGenerate.SUFFIX_INPUT_BEAN 
								+ GenerateSourceCodeConst.JAVA_EXTEND);
						this.process(data, TEMPLATE_DECISION_OUTPUT_BEAN, outputDirBatch
								+ javaFileName
								+ BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN
								+ GenerateSourceCodeConst.JAVA_EXTEND);	
					}
				} else if(BusinessDesignConst.MODULE_TYPE_ONLINE.equals(decisionTable.getModuleType())) {
					data.put("isdomain", true);
					data.put("place", "domain.decision");
					String outputDir = createFileOutputFolder(WordUtils.uncapitalize(decisionTable.getModuleCode()), BusinessLogicGenerate.SERVICE, generateSourceCode.getSourcePathDomain());
					this.process(data, TEMPLATE_DECISION_INPUT_BEAN, outputDir
							+ javaFileName 
							+ BusinessLogicGenerate.SUFFIX_INPUT_BEAN 
							+ GenerateSourceCodeConst.JAVA_EXTEND);
					this.process(data, TEMPLATE_DECISION_OUTPUT_BEAN, outputDir
							+ javaFileName
							+ BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN
							+ GenerateSourceCodeConst.JAVA_EXTEND);
				} if(BusinessDesignConst.MODULE_TYPE_BATCH.equals(decisionTable.getModuleType()) && Boolean.TRUE.equals(generateSourceCode.getBatchModuleFlg())) {
					String outputDirBatch = createFileOutputFolder(WordUtils.uncapitalize(decisionTable.getModuleCode()), BusinessLogicGenerate.BATCH, generateSourceCode.getSourcePathBatch());
					data.put("isdomain", true);
					data.put("place", "batch.decision");
					this.process(data, TEMPLATE_DECISION_INPUT_BEAN, outputDirBatch
							+ javaFileName 
							+ BusinessLogicGenerate.SUFFIX_INPUT_BEAN 
							+ GenerateSourceCodeConst.JAVA_EXTEND);
					this.process(data, TEMPLATE_DECISION_OUTPUT_BEAN, outputDirBatch
							+ javaFileName
							+ BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN
							+ GenerateSourceCodeConst.JAVA_EXTEND);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(out);
		}
	}
	
	private void generateObjectInputBeanLst(DecisionTable decisionTable, GenerateSourceCode generateSourceCode, List<DecisionTableInputBean> objInputBeanLst) {

		for (DecisionTableInputBean element : objInputBeanLst) {
			if (CollectionUtils.isNotEmpty(element.getObjectList()) && element.getObjectList().size() > 0) {
				generateObjectInputBeanLst(decisionTable, generateSourceCode, element.getObjectList());
			}
			if (CollectionUtils.isNotEmpty(element.getSingleList()) && element.getSingleList().size() > 0) {
				generateObjectInputBean(decisionTable, generateSourceCode, element);
			}
		}
	}
	
	private void generateObjectInputBean(DecisionTable decisionTable, GenerateSourceCode generateSourceCode, DecisionTableInputBean objInputBean) {

		OutputStreamWriter out = null;

		try {

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("package", GenerateSourceCodeUtil.normalizedPackageName(project.getPackageName()));
			data.put("decision", decisionTable);
			data.put("moduleCode", (StringUtils.isNotEmpty(decisionTable.getModuleCode())?"." + decisionTable.getModuleCode():StringUtils.EMPTY));
			data.put("inputBean", objInputBean);
			
			if(decisionTable.getModuleId() == null) {
				data.put("place", "domain.decision");
				data.put("isdomain", true);
				String outputDir = createFileOutputFolder(
						WordUtils.uncapitalize(decisionTable.getModuleCode())+File.separator+decisionTable.getDecisionTbCode()+BusinessLogicGenerate.SUFFIX_INPUT_BEAN,
						BusinessLogicGenerate.SERVICE,
						generateSourceCode.getSourcePathDomain());
				this.process(data, TEMPLATE_DECISION_OBJ_OF_INPUT_BEAN, outputDir
								+ GenerateSourceCodeUtil.normalizedClassName(objInputBean.getDecisionInputBeanCode())
								+ GenerateSourceCodeConst.JAVA_EXTEND);
				if(Boolean.TRUE.equals(generateSourceCode.getBatchModuleFlg())) {
					data.put("place", "batch.decision");
					data.put("isdomain", false);
					String outputDirBatch = createFileOutputFolder(
							WordUtils.uncapitalize(decisionTable.getModuleCode())+File.separator+decisionTable.getDecisionTbCode()+BusinessLogicGenerate.SUFFIX_INPUT_BEAN,
							BusinessLogicGenerate.BATCH,
							generateSourceCode.getSourcePathBatch());
					this.process(data, TEMPLATE_DECISION_OBJ_OF_INPUT_BEAN, outputDirBatch
							+ GenerateSourceCodeUtil.normalizedClassName(objInputBean.getDecisionInputBeanCode())
							+ GenerateSourceCodeConst.JAVA_EXTEND);
				}
			} else if(BusinessDesignConst.MODULE_TYPE_ONLINE.equals(decisionTable.getModuleType())) {
				data.put("place", "domain.decision");
				data.put("isdomain", true);
				String outputDir = createFileOutputFolder(
						WordUtils.uncapitalize(decisionTable.getModuleCode())+File.separator+decisionTable.getDecisionTbCode()+BusinessLogicGenerate.SUFFIX_INPUT_BEAN,
						BusinessLogicGenerate.SERVICE,
						generateSourceCode.getSourcePathDomain());
				this.process(data, TEMPLATE_DECISION_OBJ_OF_INPUT_BEAN, outputDir
								+ GenerateSourceCodeUtil.normalizedClassName(objInputBean.getDecisionInputBeanCode())
								+ GenerateSourceCodeConst.JAVA_EXTEND);
			} else if(BusinessDesignConst.MODULE_TYPE_BATCH.equals(decisionTable.getModuleType()) && Boolean.TRUE.equals(generateSourceCode.getBatchModuleFlg())) {
				data.put("place", "batch.decision");
				data.put("isdomain", false);
				String outputDirBatch = createFileOutputFolder(
						WordUtils.uncapitalize(decisionTable.getModuleCode())+File.separator+decisionTable.getDecisionTbCode()+BusinessLogicGenerate.SUFFIX_INPUT_BEAN,
						BusinessLogicGenerate.BATCH,
						generateSourceCode.getSourcePathBatch());
				this.process(data, TEMPLATE_DECISION_OBJ_OF_INPUT_BEAN, outputDirBatch
						+ GenerateSourceCodeUtil.normalizedClassName(objInputBean.getDecisionInputBeanCode())
						+ GenerateSourceCodeConst.JAVA_EXTEND);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(out);
		}
	}
	
	private void generateObjectOutputBeanLst(DecisionTable decisionTable, GenerateSourceCode generateSourceCode, List<DecisionTableOutputBean> objOutputBeanLst) {

		for (DecisionTableOutputBean element : objOutputBeanLst) {
			if (CollectionUtils.isNotEmpty(element.getObjectList()) && element.getObjectList().size() > 0) {
				generateObjectOutputBeanLst(decisionTable, generateSourceCode, element.getObjectList());
			}
			if (CollectionUtils.isNotEmpty(element.getSingleList()) && element.getSingleList().size() > 0) {
				generateObjectOutputBean(decisionTable, generateSourceCode, element);
			}
		}
	}
	
	private void generateObjectOutputBean(DecisionTable decisionTable, GenerateSourceCode generateSourceCode, DecisionTableOutputBean objOutputBean) {

		OutputStreamWriter out = null;
		try {

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("package", GenerateSourceCodeUtil.normalizedPackageName(project.getPackageName()));
			data.put("decision", decisionTable);
			data.put("moduleCode", (StringUtils.isNotEmpty(decisionTable.getModuleCode())?"." + decisionTable.getModuleCode():StringUtils.EMPTY));
			data.put("outputBean", objOutputBean);
			
			if(decisionTable.getModuleId() == null) {
				data.put("place", "domain.decision");
				data.put("isdomain", true);
				String outputDir = createFileOutputFolder(
						WordUtils.uncapitalize(decisionTable.getModuleCode())+File.separator+decisionTable.getDecisionTbCode()+BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN,
						BusinessLogicGenerate.SERVICE,
						generateSourceCode.getSourcePathDomain());
				this.process(data, TEMPLATE_DECISION_OBJ_OF_OUTPUT_BEAN, outputDir
						+ GenerateSourceCodeUtil.normalizedClassName(objOutputBean.getDecisionOutputBeanCode())
						+ GenerateSourceCodeConst.JAVA_EXTEND);
				if(Boolean.TRUE.equals(generateSourceCode.getBatchModuleFlg())) {
					data.put("isdomain", false);
					data.put("place", "batch.decision");
					String outputDirBatch = createFileOutputFolder(
							WordUtils.uncapitalize(decisionTable.getModuleCode())+File.separator+decisionTable.getDecisionTbCode()+BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN,
							BusinessLogicGenerate.BATCH,
							generateSourceCode.getSourcePathBatch());
					this.process(data, TEMPLATE_DECISION_OBJ_OF_OUTPUT_BEAN, outputDirBatch
							+ GenerateSourceCodeUtil.normalizedClassName(objOutputBean.getDecisionOutputBeanCode())
							+ GenerateSourceCodeConst.JAVA_EXTEND);
				}
			} else if(BusinessDesignConst.MODULE_TYPE_ONLINE.equals(decisionTable.getModuleType())) {
				data.put("isdomain", true);
				data.put("place", "domain.decision");
				String outputDir = createFileOutputFolder(
						WordUtils.uncapitalize(decisionTable.getModuleCode())+File.separator+decisionTable.getDecisionTbCode()+BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN,
						BusinessLogicGenerate.SERVICE,
						generateSourceCode.getSourcePathDomain());
				this.process(data, TEMPLATE_DECISION_OBJ_OF_OUTPUT_BEAN, outputDir
						+ GenerateSourceCodeUtil.normalizedClassName(objOutputBean.getDecisionOutputBeanCode())
						+ GenerateSourceCodeConst.JAVA_EXTEND);
			} else if(BusinessDesignConst.MODULE_TYPE_BATCH.equals(decisionTable.getModuleType()) && Boolean.TRUE.equals(generateSourceCode.getBatchModuleFlg())) {
				data.put("isdomain", false);
				data.put("place", "batch.decision");
				String outputDirBatch = createFileOutputFolder(
						WordUtils.uncapitalize(decisionTable.getModuleCode())+File.separator+decisionTable.getDecisionTbCode()+BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN,
						BusinessLogicGenerate.BATCH,
						generateSourceCode.getSourcePathBatch());
				this.process(data, TEMPLATE_DECISION_OBJ_OF_OUTPUT_BEAN, outputDirBatch
						+ GenerateSourceCodeUtil.normalizedClassName(objOutputBean.getDecisionOutputBeanCode())
						+ GenerateSourceCodeConst.JAVA_EXTEND);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	private String createFileOutputFolder(String moduleName, int sourceCodeType, String pathRoot) {
		StringBuilder pathOutput = new StringBuilder();
		switch (sourceCodeType) {
			case GenerateSourceCodeConst.BusinessLogicGenerate.SERVICE:
				pathOutput.append("src").append(File.separator).append("main").append(File.separator)
						  .append("java").append(File.separator).append(pathPackage.toString())
						  .append(File.separator).append("domain").append(File.separator).append("decision").append(File.separator).append(moduleName);
				
				break;
			case GenerateSourceCodeConst.BusinessLogicGenerate.BATCH:
				pathOutput.append("src").append(File.separator).append("main").append(File.separator)
						  .append("java").append(File.separator).append(pathPackage.toString())
						  .append(File.separator).append("batch").append(File.separator).append("decision").append(File.separator).append(moduleName);
				break;
		}

		return GenerateSourceCodeUtil.createSaveFileDirectory(GenerateSourceCodeUtil.normalizedPackageName(pathOutput.toString()), pathRoot);
	}
	
	public static List<DecisionTableInputBean> getSingleListInputBean(List<DecisionTableInputBean> inputBeanList) {
		List<DecisionTableInputBean> listInputSingle = new ArrayList<DecisionTableInputBean>();
		for (DecisionTableInputBean inputBean : inputBeanList) {
			if (inputBean.getParentDecisionInputBeanId() == null) {
				listInputSingle.add(inputBean);
			}
		}
		
		return listInputSingle;
	}
	
	public static List<DecisionTableInputBean> getObjectListInputBean(List<DecisionTableInputBean> inputBeanList) {
		List<DecisionTableInputBean> listInputObject = new ArrayList<DecisionTableInputBean>();
		for (DecisionTableInputBean inputBean : inputBeanList) {
			if (inputBean.getParentDecisionInputBeanId() == null && inputBean.getDataType().equals(0)) {
				settingFieldObjectInputBean(inputBean, inputBeanList);
				listInputObject.add(inputBean);
			}
		}

		return listInputObject;
	}
	
	public static void settingFieldObjectInputBean(DecisionTableInputBean item, List<DecisionTableInputBean> inputBeanList) {
		List<DecisionTableInputBean> listInputSingle = new ArrayList<DecisionTableInputBean>();
		List<DecisionTableInputBean> listInputObject = new ArrayList<DecisionTableInputBean>();
		for (DecisionTableInputBean inputBean : inputBeanList) {
			if (item.getDecisionInputBeanId().equals(inputBean.getParentDecisionInputBeanId())){
				listInputSingle.add(inputBean);
				if (inputBean.getDataType().equals(0)) {
					settingFieldObjectInputBean(inputBean, inputBeanList);
					listInputObject.add(inputBean);
				}
			}
		}

		item.setSingleList(listInputSingle);
		item.setObjectList(listInputObject);
	}
	
	public static List<DecisionTableOutputBean> getSingleListOutputBean(List<DecisionTableOutputBean> inputBeanList) {
		List<DecisionTableOutputBean> listOutputSingle = new ArrayList<DecisionTableOutputBean>();
		for (DecisionTableOutputBean outputBean : inputBeanList) {
			if (outputBean.getParentDecisionOutputBeanId() == null) {
				listOutputSingle.add(outputBean);
			}
		}
		
		return listOutputSingle;
	}

	public static List<DecisionTableOutputBean> getObjectListOutputBean(List<DecisionTableOutputBean> outputBeanList) {
		List<DecisionTableOutputBean> listInputObject = new ArrayList<DecisionTableOutputBean>();
		for (DecisionTableOutputBean outputBean : outputBeanList) {
			if (outputBean.getParentDecisionOutputBeanId() == null && outputBean.getDataType().equals(0)) {
				settingFieldObjectOutputBean(outputBean, outputBeanList);
				listInputObject.add(outputBean);
			}
		}

		return listInputObject;
	}

	public static void settingFieldObjectOutputBean(DecisionTableOutputBean item, List<DecisionTableOutputBean> outputBeanList) {
		List<DecisionTableOutputBean> listOutputSingle = new ArrayList<DecisionTableOutputBean>();
		List<DecisionTableOutputBean> listOutputObject = new ArrayList<DecisionTableOutputBean>();
		for (DecisionTableOutputBean outputBean : outputBeanList) {
			if (item.getDecisionOutputBeanId().equals(outputBean.getParentDecisionOutputBeanId())) {
				listOutputSingle.add(outputBean);
				if(outputBean.getDataType().equals(0)){
					settingFieldObjectOutputBean(outputBean, outputBeanList);
					listOutputObject.add(outputBean);
				}
			}
		}

		item.setSingleList(listOutputSingle);
		item.setObjectList(listOutputObject);
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

	private List<FormulaDetail> getListFormulaDetails(List<DecisionTableConditionItem> conditionItem) {

		List<FormulaDetail> lstFormulaDetails = formulaDefinitionRepository.findFormulaDetailsByListDecisionItemId(conditionItem);
		List<FormulaMethodInput> lstFormulaMethodInputs = formulaDefinitionRepository.findFormulaMethodInputsByListDecisionItemId(conditionItem);
		List<FormulaMethodOutput> lstFormulaMethodOutputs = formulaDefinitionRepository.findFormulaMethodOutputsByListDecisionItemId(conditionItem);

		// It current not using
		//List<BDParameterIndex> allBdParameterIndexs = bDParameterIndexRepository.findBDParameterIndexByModuleCommon(project.getProjectId());
		
		//map detail function for formula setting
		for (FormulaDetail objDetail : lstFormulaDetails){
			List<FormulaMethodInput> lstInputTemps = new ArrayList<FormulaMethodInput>();
			for(FormulaMethodInput objInput : lstFormulaMethodInputs){
				if(objInput.getFormulaDetailId().equals(objDetail.getFormulaDetailId())){
					lstInputTemps.add(objInput);
				}
			}
			objDetail.setFormulaMethodInputs(lstInputTemps);
			
			List<FormulaMethodOutput> lstOutputTemps = new ArrayList<FormulaMethodOutput>();
			for(FormulaMethodOutput objOutput : lstFormulaMethodOutputs){
				if(objOutput.getFormulaDetailId().equals(objDetail.getFormulaDetailId())){
					lstOutputTemps.add(objOutput);
				}
			}
			objDetail.setFormulaMethodOutputs(lstOutputTemps);
		}
		
		return lstFormulaDetails;
	}

	private String getPackageName(DecisionTable decisionTable , Object obj) {
		StringBuilder pakageName = new StringBuilder(project.getPackageName());
		int dataType = -1;
		String code = StringUtils.EMPTY;
		String pakageExternal = StringUtils.EMPTY;
		String moduleCode = StringUtils.EMPTY;
		String place = this.isBatchCurrent?".batch":".domain";
		
		if (obj instanceof DecisionTableInputBean) {
			DecisionTableInputBean in = (DecisionTableInputBean) obj;
			dataType = in.getDataType();
			code = GenerateSourceCodeConst.BusinessLogicGenerate.SUFFIX_INPUT_BEAN;
			pakageExternal = in.getPackageNameObjExt();
			moduleCode = in.getModuleCode();
		} else if(obj instanceof DecisionTableOutputBean) {
			DecisionTableOutputBean ou = (DecisionTableOutputBean) obj;
			dataType = ou.getDataType();
			code = GenerateSourceCodeConst.BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN;
			pakageExternal = ou.getPackageNameObjExt();
			moduleCode = ou.getModuleCode();
		}

		switch (dataType) {
			case TypeOfDataType.OBJECT : 
				pakageName.append(place).append(".decision")
						.append(".").append(decisionTable.getDecisionTbCode()).append(code).append(".");
				break;
			case TypeOfDataType.ENTITY :
				pakageName.append(".domain.model.");
				break;
			case TypeOfDataType.COMMON_OBJECT :
				pakageName.append(place).append(".commonobject.");
				
				if(StringUtils.isNotEmpty(moduleCode)) {
					pakageName.append(moduleCode).append(".");
				}
				
				break;
			case TypeOfDataType.EXTERNAL_OBJECT :
				pakageName.setLength(0);
				pakageName.append(pakageExternal).append(".");
				break;
		}

		return GenerateSourceCodeUtil.normalizedPackageName(pakageName.toString());
	}
}
