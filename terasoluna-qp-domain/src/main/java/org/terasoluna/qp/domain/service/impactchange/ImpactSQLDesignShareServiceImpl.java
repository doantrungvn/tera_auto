package org.terasoluna.qp.domain.service.impactchange;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.terasoluna.qp.domain.model.DomainDesign;
import org.terasoluna.qp.domain.model.ExecutionComponent;
import org.terasoluna.qp.domain.model.ExecutionInputValue;
import org.terasoluna.qp.domain.model.ExecutionOutputValue;
import org.terasoluna.qp.domain.model.ProblemList;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.model.SqlDesignInput;
import org.terasoluna.qp.domain.model.SqlDesignOutput;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.repository.impactchange.ImpactSQLDesignRepository;
import org.terasoluna.qp.domain.repository.problemlist.ProblemListRepository;
import org.terasoluna.qp.domain.service.sqldesign.SqlDesignCompound;
import org.terasoluna.qp.domain.service.sqldesign.SqlDesignService;

@Service
public class ImpactSQLDesignShareServiceImpl implements ImpactSQLDesignShareService {

	@Inject
	ImpactSQLDesignRepository impactSQLDesignRepository;
	
	@Inject
	ProblemListRepository problemListRepository;
	
	@Inject
	SqlDesignService sqlDesignService;

	@Override
	public void detectListAffectedWhenModifyOfBatch(Long sqlDesignId, CommonModel common) {
		SqlDesignCompound sqlDesignCompound = sqlDesignService.findCompoundById(sqlDesignId);
		detectListAffectedWhenModify(sqlDesignCompound,common,true);
	}

	@Override
	public ImpactSQLDesign detectListAffectedWhenModify(SqlDesignCompound sqlDesignCompound, CommonModel common, Boolean isRunBatch) {
		ImpactSQLDesign impact = new ImpactSQLDesign();
		if(sqlDesignCompound != null){
			List<SqlDesignInput> lstSqlDesignInputs = new ArrayList<SqlDesignInput>();
			if(sqlDesignCompound.getSqlDesignInputs() != null)
				lstSqlDesignInputs = Arrays.asList(sqlDesignCompound.getSqlDesignInputs());
			List<SqlDesignOutput> lstSqlDesignOutputs = new ArrayList<SqlDesignOutput>();
			if(sqlDesignCompound.getSqlDesignOutputs() != null)
				lstSqlDesignOutputs = Arrays.asList(sqlDesignCompound.getSqlDesignOutputs());
			SqlDesign sqlDesign = sqlDesignCompound.getSqlDesign();
			
			//detect impact change design to business logic
			List<ExecutionComponent> lstExecutionComponents = new ArrayList<ExecutionComponent>();
			List<ExecutionInputValue> lstExecutionInputValues = new ArrayList<ExecutionInputValue>();
			List<ExecutionOutputValue> lstExecutionOutputValues = new ArrayList<ExecutionOutputValue>();
			List<BusinessDesign> lstUsedBusinessDesign = new ArrayList<BusinessDesign>();
			List<BusinessDesign> lstTemps = new ArrayList<BusinessDesign>();
			Map<Long,BusinessDesign> mapComponentOfBlogic = new HashMap<Long,BusinessDesign>();
			
			lstExecutionComponents = impactSQLDesignRepository.findExecutionComponentBySqlDesignId(sqlDesign.getSqlDesignId());
			ProblemList problemList = new ProblemList();
			List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
			if(CollectionUtils.isNotEmpty(lstExecutionComponents)){
				lstUsedBusinessDesign = impactSQLDesignRepository.findAllBussinessLogicBySqlDesignId(sqlDesign.getSqlDesignId());
				lstExecutionInputValues = impactSQLDesignRepository.findExecutionInputValueBySqlDesignId(sqlDesign.getSqlDesignId());
				lstExecutionOutputValues = impactSQLDesignRepository.findExecutionOutputValueBySqlDesignId(sqlDesign.getSqlDesignId());
				
				//mapping
				List<ExecutionInputValue> lstCurrentInputValues = new ArrayList<ExecutionInputValue>();
				List<ExecutionOutputValue> lstCurrentOutputValues = new ArrayList<ExecutionOutputValue>();
				for (ExecutionComponent component : lstExecutionComponents) {
	            	lstCurrentInputValues = new ArrayList<ExecutionInputValue>();
		            if(CollectionUtils.isNotEmpty(lstExecutionInputValues)){
		            	for (ExecutionInputValue inputValues : lstExecutionInputValues){
		            		if(inputValues.getExecutionComponentId().equals(component.getExecutionComponentId())){
		            			lstCurrentInputValues.add(inputValues);
		            		}
		            	}
		            }
		            component.setParameterInputBeans(lstCurrentInputValues);
		            lstCurrentOutputValues = new ArrayList<ExecutionOutputValue>();
		            if(CollectionUtils.isNotEmpty(lstExecutionOutputValues)){
		            	for (ExecutionOutputValue outputValues : lstExecutionOutputValues){
		            		if(outputValues.getExecutionComponentId().equals(component.getExecutionComponentId())){
		            			lstCurrentOutputValues.add(outputValues);
		            		}
		            	}
		            }
		            component.setParameterOutputBeans(lstCurrentOutputValues);
		            if(CollectionUtils.isNotEmpty(lstUsedBusinessDesign)){
		            	for (BusinessDesign businessDesign : lstUsedBusinessDesign){
		            		if(FunctionCommon.equals(component.getBusinessLogicId(),businessDesign.getBusinessLogicId())){
		            			mapComponentOfBlogic.put(component.getExecutionComponentId(), businessDesign);
		            			break;
		            		}
		            	}
		            }
	            }
				//compare with current design
				Boolean existFlag = false;
				BusinessDesign businessDesign = new BusinessDesign();
				Boolean isImpact = false;
				for (ExecutionComponent component : lstExecutionComponents) {
					businessDesign = mapComponentOfBlogic.getOrDefault(component.getExecutionComponentId(),new BusinessDesign());
		            //compare input bean of decision table
					if(CollectionUtils.isNotEmpty(lstSqlDesignInputs)){
						for (SqlDesignInput inputBean : lstSqlDesignInputs) {
							existFlag = false;
							ExecutionInputValue mappingValue = null;
							for (ExecutionInputValue inputValue : component.getParameterInputBeans()){
								if(FunctionCommon.equals(inputValue.getSqlDesignInputId(), inputBean.getSqlDesignInputId())){
									existFlag = true;
									mappingValue = inputValue;
									break;
								}
							}
							if(existFlag){
								if(mappingValue != null && (FunctionCommon.notEquals(mappingValue.getDataType(), inputBean.getDataType()) || !FunctionCommon.compare(inputBean.getArrayFlag(), mappingValue.getArrayFlg()))){
									//modify input bean
									if(isRunBatch){
										problemList = new ProblemList();
										problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0146,inputBean.getSqlDesignInputCode(),sqlDesign.getSqlDesignCode(),component.getLabel(),businessDesign.getBusinessLogicCode()));
										problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
										problemList.setResourceId(businessDesign.getBusinessLogicId());
										problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
										problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
										problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
										problemList.setModuleId(businessDesign.getModuleId());
										problemList.setProjectId(common.getWorkingProjectId());
										problemList.setCreatedBy(common.getCreatedBy());
										problemList.setFromResourceType(DbDomainConst.FromResourceType.SQL_DESIGN);
										problemList.setFromResourceId(sqlDesign.getSqlDesignId());
										problemList.setCreatedDate(common.getCreatedDate());
										lstProblemLists.add(problemList);
									}
									isImpact = true;
								}
							}else{
								//new input bean
								if(isRunBatch){
									problemList = new ProblemList();
									problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0145,inputBean.getSqlDesignInputCode(),sqlDesign.getSqlDesignCode(),component.getLabel(),businessDesign.getBusinessLogicCode()));
									problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
									problemList.setResourceId(businessDesign.getBusinessLogicId());
									problemList.setProblemType(DbDomainConst.ProblemType.MISS_SETTING);
									problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
									problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
									problemList.setModuleId(businessDesign.getModuleId());
									problemList.setProjectId(common.getWorkingProjectId());
									problemList.setCreatedBy(common.getCreatedBy());
									problemList.setFromResourceType(DbDomainConst.FromResourceType.SQL_DESIGN);
									problemList.setFromResourceId(sqlDesign.getSqlDesignId());
									problemList.setCreatedDate(common.getCreatedDate());
									lstProblemLists.add(problemList);
								}
								isImpact = true;
							}
		                }
					}
					//compare output bean of decision table
					if(CollectionUtils.isNotEmpty(lstSqlDesignOutputs)){
						for (SqlDesignOutput outputBean : lstSqlDesignOutputs) {
							existFlag = false;
							ExecutionOutputValue mappingValue = null;
							for (ExecutionOutputValue outputValue : component.getParameterOutputBeans()){
								if(FunctionCommon.equals(outputValue.getSqlDesignOutputId(), outputBean.getSqlDesignOutputId())){
									existFlag = true;
									mappingValue = outputValue;
									break;
								}
							}
							if(existFlag){
								if(mappingValue != null && (FunctionCommon.notEquals(mappingValue.getDataType(), outputBean.getDataType()) || !FunctionCommon.compare(outputBean.getArrayFlag(), mappingValue.getArrayFlg()))){
									//modify output bean
									if(isRunBatch){
										problemList = new ProblemList();
										problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0147,outputBean.getSqlDesignOutputCode(),sqlDesign.getSqlDesignCode(),component.getLabel(),businessDesign.getBusinessLogicCode()));
										problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
										problemList.setResourceId(businessDesign.getBusinessLogicId());
										problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
										problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
										problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
										problemList.setModuleId(businessDesign.getModuleId());
										problemList.setProjectId(common.getWorkingProjectId());
										problemList.setCreatedBy(common.getCreatedBy());
										problemList.setFromResourceType(DbDomainConst.FromResourceType.SQL_DESIGN);
										problemList.setFromResourceId(sqlDesign.getSqlDesignId());
										problemList.setCreatedDate(common.getCreatedDate());
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
			List<DomainDesign> lstUsedDomainDesign = impactSQLDesignRepository.findAllDomainDesignsBySqlDesignIdAndNotReferOutput(sqlDesign.getSqlDesignId(),lstSqlDesignOutputs);
			if(isRunBatch && CollectionUtils.isNotEmpty(lstUsedDomainDesign)){
				for (DomainDesign domainDesign : lstUsedDomainDesign) {
					problemList = new ProblemList();
					problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0149,sqlDesign.getSqlDesignCode(),domainDesign.getDomainCode()));
					problemList.setResourceType(DbDomainConst.ResourceType.DOMAIN_DESIGN);
					problemList.setResourceId(domainDesign.getDomainId());
					problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
					problemList.setUrlId(DbDomainConst.ResourceURL.URL_DOMAIN_DESIGN);
					problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
					problemList.setModuleId(null);
					problemList.setProjectId(common.getWorkingProjectId());
					problemList.setCreatedBy(common.getCreatedBy());
					problemList.setFromResourceType(DbDomainConst.FromResourceType.SQL_DESIGN);
					problemList.setFromResourceId(sqlDesign.getSqlDesignId());
					problemList.setCreatedDate(common.getCreatedDate());
					lstProblemLists.add(problemList);
                }
			}
			
			List<TableDesign> lstUsedTableDesigns = impactSQLDesignRepository.findAllTableDesignsBySqlDesignId(sqlDesign.getSqlDesignId());
			List<TableDesign> lstTempTableDesigns = new ArrayList<TableDesign>();
			List<TableDesignDetails> lstUsedTableDesignDetails = impactSQLDesignRepository.findAllTableDesignDetailsBySqlDesignIdAndNotReferOutput(sqlDesign.getSqlDesignId(),lstSqlDesignOutputs);
			if(CollectionUtils.isNotEmpty(lstUsedTableDesignDetails)){
				TableDesign currentTable = new TableDesign();
				for (TableDesignDetails column : lstUsedTableDesignDetails) {
					for (TableDesign temp : lstUsedTableDesigns){
	            		if(FunctionCommon.equals(column.getTableDesignId(),temp.getTableDesignId())){
	            			currentTable = temp;
	            			break;
	            		}
	            	}
					if(!lstTempTableDesigns.contains(currentTable)){
						lstTempTableDesigns.add(currentTable);
					}
	                if(isRunBatch){
	                	problemList = new ProblemList();
						problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0151,sqlDesign.getSqlDesignCode(),currentTable.getTableCode(), column.getCode()));
						problemList.setResourceType(DbDomainConst.ResourceType.TABLE_DESIGN);
						problemList.setResourceId(column.getTableDesignId());
						problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
						problemList.setUrlId(DbDomainConst.ResourceURL.URL_TABLE_DESIGN);
						problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
						problemList.setModuleId(null);
						problemList.setProjectId(common.getWorkingProjectId());
						problemList.setCreatedBy(common.getCreatedBy());
						problemList.setFromResourceType(DbDomainConst.FromResourceType.SQL_DESIGN);
						problemList.setFromResourceId(sqlDesign.getSqlDesignId());
						problemList.setCreatedDate(common.getCreatedDate());
						lstProblemLists.add(problemList);
	                }
                }
			}
			if(CollectionUtils.isNotEmpty(lstTemps) || CollectionUtils.isNotEmpty(lstUsedDomainDesign) || CollectionUtils.isNotEmpty(lstTempTableDesigns)){
				impact.setImpactFlag(true);
			}
			impact.setLstUsedBusinessDesign(lstTemps);
			impact.setLstUsedDomainDesign(lstUsedDomainDesign);
			impact.setLstUsedTableDesign(lstTempTableDesigns);
			if(Boolean.TRUE.equals(isRunBatch)){
				//delete mapping data
				//delete old problem of this business
				List<Long> lstFroms = new ArrayList<Long>();
				lstFroms.add(sqlDesign.getSqlDesignId());
				problemListRepository.deleteResourceType(DbDomainConst.FromResourceType.SQL_DESIGN, lstFroms);
				//update name code
				
				if(CollectionUtils.isNotEmpty(lstProblemLists)){
					lstFroms.add(sqlDesign.getSqlDesignId());
					problemListRepository.multiRegisterProblem(lstProblemLists);
				}
			}
		}
		return impact;
	}

	@Override
	public void detectListAffectedWhenDeleteOfBatch(Long sqlDesignId, String sqlDesignCode, CommonModel common) {
		// TODO Auto-generated method stub
		SqlDesign sqlDesign = new SqlDesign();
		sqlDesign.setSqlDesignId(sqlDesignId);
		sqlDesign.setSqlDesignCode(sqlDesignCode);
		detectListAffectedWhenDelete(sqlDesign, common, true);

	}

	@Override
	public ImpactSQLDesign detectListAffectedWhenDelete(SqlDesign sqlDesign, CommonModel common, Boolean isRunBatch) {
		ImpactSQLDesign impact = new ImpactSQLDesign();
		List<BusinessDesign> lstUsedBusinessDesign = new ArrayList<BusinessDesign>();
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		List<ExecutionComponent> lstExecutionComponents = new ArrayList<ExecutionComponent>();
		Timestamp systemDate = common.getCreatedDate();
		// detect impact change design to blogic
		lstExecutionComponents = impactSQLDesignRepository.findExecutionComponentBySqlDesignId(sqlDesign.getSqlDesignId());
		if(CollectionUtils.isNotEmpty(lstExecutionComponents)){
			lstUsedBusinessDesign = impactSQLDesignRepository.findAllBussinessLogicBySqlDesignId(sqlDesign.getSqlDesignId());
			//mapping
			BusinessDesign businessDesign = new BusinessDesign();
			ProblemList problemList = new ProblemList();
			for (ExecutionComponent component : lstExecutionComponents) {
				for (BusinessDesign temp : lstUsedBusinessDesign){
            		if(FunctionCommon.equals(component.getBusinessLogicId(),temp.getBusinessLogicId())){
            			businessDesign = temp;
            			break;
            		}
            	}
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0148,sqlDesign.getSqlDesignCode(),component.getLabel(), businessDesign.getBusinessLogicCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
				problemList.setResourceId(businessDesign.getBusinessLogicId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
				problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
				problemList.setModuleId(businessDesign.getModuleId());
				problemList.setProjectId(common.getWorkingProjectId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.SQL_DESIGN);
				problemList.setFromResourceId(sqlDesign.getSqlDesignId());
				problemList.setCreatedDate(systemDate);
				lstProblemLists.add(problemList);
            }
			
			//delete mapping data
			impactSQLDesignRepository.deleteExecutionInputValueByExecutionComponent(lstExecutionComponents);
			impactSQLDesignRepository.deleteExecutionOutputValueByExecutionComponent(lstExecutionComponents);
		}
		
		// detect impact change design to domain design
		List<DomainDesign> lstUsedDomainDesign = new ArrayList<DomainDesign>();
		lstUsedDomainDesign = impactSQLDesignRepository.findAllDomainDesignsBySqlDesignId(sqlDesign.getSqlDesignId());
		if(CollectionUtils.isNotEmpty(lstUsedDomainDesign)){
			ProblemList problemList = new ProblemList();
			for (DomainDesign domain : lstUsedDomainDesign) {
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0150,sqlDesign.getSqlDesignCode(),domain.getDomainCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.DOMAIN_DESIGN);
				problemList.setResourceId(domain.getDomainId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_DOMAIN_DESIGN);
				problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
				problemList.setModuleId(null);
				problemList.setProjectId(common.getWorkingProjectId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.SQL_DESIGN);
				problemList.setFromResourceId(sqlDesign.getSqlDesignId());
				problemList.setCreatedDate(systemDate);
				lstProblemLists.add(problemList);
            }
			
			//delete mapping data
		}
		
		// detect impact change design to table design
		List<TableDesign> lstUsedTableDesign = new ArrayList<TableDesign>();
		List<TableDesignDetails> lstDesignDetails = impactSQLDesignRepository.findAllTableDesignDetailsBySqlDesignId(sqlDesign.getSqlDesignId());
		if(CollectionUtils.isNotEmpty(lstDesignDetails)){
			lstUsedTableDesign = impactSQLDesignRepository.findAllTableDesignsBySqlDesignId(sqlDesign.getSqlDesignId());
			//mapping
			TableDesign currentTable = new TableDesign();
			ProblemList problemList = new ProblemList();
			for (TableDesignDetails column : lstDesignDetails) {
				for (TableDesign temp : lstUsedTableDesign){
            		if(FunctionCommon.equals(column.getTableDesignId(),temp.getTableDesignId())){
            			currentTable = temp;
            			break;
            		}
            	}
				problemList = new ProblemList();
				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0152,sqlDesign.getSqlDesignCode(),column.getCode(), currentTable.getTableCode()));
				problemList.setResourceType(DbDomainConst.ResourceType.TABLE_DESIGN);
				problemList.setResourceId(currentTable.getTableDesignId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_TABLE_DESIGN);
				problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
				problemList.setModuleId(null);
				problemList.setProjectId(common.getWorkingProjectId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.SQL_DESIGN);
				problemList.setFromResourceId(sqlDesign.getSqlDesignId());
				problemList.setCreatedDate(systemDate);
				lstProblemLists.add(problemList);
            }
			
			//delete mapping data
		}
				
		//delete old problem of this business
		if(isRunBatch){
			List<Long> lstFroms = new ArrayList<Long>();
			lstFroms.add(sqlDesign.getSqlDesignId());
			problemListRepository.deleteResourceType(DbDomainConst.FromResourceType.SQL_DESIGN, lstFroms);
			if(CollectionUtils.isNotEmpty(lstProblemLists)){
				problemListRepository.multiRegisterProblem(lstProblemLists);
			}
		}
		if(CollectionUtils.isNotEmpty(lstUsedBusinessDesign) || CollectionUtils.isNotEmpty(lstUsedDomainDesign) || CollectionUtils.isNotEmpty(lstUsedTableDesign)){
			impact.setImpactFlag(true);
		}
		impact.setLstUsedBusinessDesign(lstUsedBusinessDesign);
		impact.setLstUsedDomainDesign(lstUsedDomainDesign);
		impact.setLstUsedTableDesign(lstUsedTableDesign);
		return impact;
	}

}
