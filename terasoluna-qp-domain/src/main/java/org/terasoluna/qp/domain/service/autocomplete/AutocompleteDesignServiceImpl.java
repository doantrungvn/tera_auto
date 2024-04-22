/*
 * 
 */
package org.terasoluna.qp.domain.service.autocomplete;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.DbDomainConst.DesignStatus;
import org.terasoluna.qp.app.common.constants.DbDomainConst.GenerateAppStatus;
import org.terasoluna.qp.app.common.constants.DbDomainConst.ImpactChangeDesign;
import org.terasoluna.qp.app.common.constants.DbDomainConst.SQLDesignType;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.AutocompleteMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.AutocompleteDesign;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.ConsistencyValidationModel;
import org.terasoluna.qp.domain.model.ImpactChangeJobControl;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.model.SqlDesignCondition;
import org.terasoluna.qp.domain.model.SqlDesignGroupBy;
import org.terasoluna.qp.domain.model.SqlDesignHaving;
import org.terasoluna.qp.domain.model.SqlDesignInput;
import org.terasoluna.qp.domain.model.SqlDesignOrder;
import org.terasoluna.qp.domain.model.SqlDesignResult;
import org.terasoluna.qp.domain.model.SqlDesignTable;
import org.terasoluna.qp.domain.model.SqlDesignTableItem;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.model.TableDesignForeignKey;
import org.terasoluna.qp.domain.repository.autocomplete.AutocompleteDesignRepository;
import org.terasoluna.qp.domain.repository.autocomplete.AutocompleteSearchCriteria;
import org.terasoluna.qp.domain.repository.impactchange.ImpactChangeRepository;
import org.terasoluna.qp.domain.repository.project.ProjectRepository;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignConditionRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignGroupByRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignHavingRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignInputRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignOrderRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignResultRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignTableItemsRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignTableRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignDetailRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignForeignKeyRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst.JoinType;
import org.terasoluna.qp.domain.service.module.ModuleService;
import org.terasoluna.qp.domain.service.project.ProjectService;
import org.terasoluna.qp.domain.service.sqldesign.SqlDesignCompound;

/**@author anlt
 * The Class AutocompleteDesignServiceImpl.
 */
@Service
@Transactional
public class AutocompleteDesignServiceImpl  implements AutocompleteDesignService {

	@Inject
	ProjectRepository projectRepository;
	
	@Inject
	AutocompleteDesignRepository autocompleteDesignRepository;
	
	@Inject
	SqlDesignRepository sqlDesignRepository;
	
	@Inject 
	SqlDesignResultRepository sqlDesignResultRepository;

	@Inject 
	SqlDesignTableRepository sqlDesignTableRepository;
	
	@Inject 
	SqlDesignTableItemsRepository sqlDesignTableItemRepository;
	
	@Inject 
	SqlDesignConditionRepository sqlDesignConditionRepository;
	
	@Inject
	SqlDesignOrderRepository sqlDesignOrderRepository;
	
	@Inject
	SqlDesignGroupByRepository sqlDesignGroupByRepository;
	
	@Inject
	SqlDesignHavingRepository sqlDesignHavingRepository;
	
	@Inject
	SqlDesignInputRepository sqlDesignInputRepository;
	
	@Inject
	TableDesignForeignKeyRepository tableDesignForeignKeyRepository;
	
	@Inject 
	TableDesignDetailRepository tableDesignDetailRepository;
	
	@Inject
	SqlBuilderService sqlBuilderService;
	
	@Inject
	ModuleService moduleService;
	
	@Inject
	ProjectService projectService;

	@Inject
	ScreenDesignRepository screenDesignRepository;
	
	@Inject
	ImpactChangeRepository impactChangeRepository;
	
	@Override
	public Page<AutocompleteDesign> findPageByCriteria(
			AutocompleteSearchCriteria criteria, Pageable pageable, CommonModel common) {
		criteria.setProjectId(common.getWorkingProjectId());
		long totalCount = autocompleteDesignRepository.countByCriteria(criteria);

		List<AutocompleteDesign> result;
		if (0 < totalCount) {
			result = autocompleteDesignRepository.findByCriteria(criteria, pageable);
		} else {
			result = Collections.emptyList();
		}

		Page<AutocompleteDesign> page = new PageImpl<AutocompleteDesign>(result, pageable, totalCount);

		return page;
	}

	@Override
	public void registerAutocomleteDesign(AutocompleteDesignCompound autocompleteDesignCompound, CommonModel common) {
		if(autocompleteDesignCompound!=null) {
			this.validateDesignStatus(autocompleteDesignCompound.getAutocomplete(),true, common);
			this.validateNameCodeUnique(autocompleteDesignCompound);
			this.validateSqlSyntax(autocompleteDesignCompound);
			
			SqlDesign sqlDesign = autocompleteDesignCompound.getSqlDesign();
			sqlDesign.setSqlDesignName(autocompleteDesignCompound.getAutocomplete().getAutocompleteName());
			sqlDesign.setSqlDesignCode(autocompleteDesignCompound.getAutocomplete().getAutocompleteCode());
			sqlDesign.setDesignType(SQLDesignType.AUTOCOMPLETE);
			sqlDesign.setSystemDate(FunctionCommon.getCurrentTime());
			sqlDesign.setDesignStatus(DesignStatus.UNDER_DESIGN);
			sqlDesign.setModuleId(autocompleteDesignCompound.getAutocomplete().getModuleId());
			autocompleteDesignCompound.getAutocomplete().setProjectId(common.getWorkingProjectId());
			sqlDesign.setProjectId(autocompleteDesignCompound.getAutocomplete().getProjectId());
			sqlDesignRepository.register(sqlDesign);
			
			// register sql design table
			List<SqlDesignTableItem> sqlDesignTableItems = new ArrayList<SqlDesignTableItem>();
			for(SqlDesignTable sqlDesignTable:autocompleteDesignCompound.getSqlDesignTables()){
				sqlDesignTable.setSqlDesignId(sqlDesign.getSqlDesignId());
				sqlDesignTable.setTableId(autocompleteDesignCompound.getSqlDesignTables()[0].getTableId());
				sqlDesignTableRepository.register(sqlDesignTable);
				boolean isNotCrossJoin = !StringUtils.equals(sqlDesignTable.getJoinType(), JoinType.CROSS_JOIN.getCode());
				if(sqlDesignTable.getJoinTableId()!=null && isNotCrossJoin){
					for(SqlDesignTableItem sqlDesignTableItem:sqlDesignTable.getSqlDesignTableItems()) {
						sqlDesignTableItem.setSqlDesignTableId(sqlDesignTable.getSqlDesignTableId());
						sqlDesignTableItem.setTableId(sqlDesignTable.getTableId());
						sqlDesignTableItem.setJoinTableId(sqlDesignTable.getJoinTableId());
						sqlDesignTableItems.add(sqlDesignTableItem);
					}
				}
			}
			if(sqlDesignTableItems.size()>0) {
				sqlDesignTableItemRepository.registerAll(sqlDesignTableItems.toArray(new SqlDesignTableItem[sqlDesignTableItems.size()]));
			}
			
			// register sql design conditions
			if(ArrayUtils.isNotEmpty(autocompleteDesignCompound.getSqlDesignConditions())) {
				for(SqlDesignCondition sqlDesignCondition:autocompleteDesignCompound.getSqlDesignConditions()){
					sqlDesignCondition.setSqlDesignId(sqlDesign.getSqlDesignId());
				}
				sqlDesignConditionRepository.registerAll(autocompleteDesignCompound.getSqlDesignConditions());
			}
			
			// register sql design order
			if(ArrayUtils.isNotEmpty(autocompleteDesignCompound.getSqlDesignOrders())) {
				for(SqlDesignOrder sqlDesignOrder:autocompleteDesignCompound.getSqlDesignOrders()){
					sqlDesignOrder.setSqlDesignId(sqlDesign.getSqlDesignId());
				}
				sqlDesignOrderRepository.registerAll(autocompleteDesignCompound.getSqlDesignOrders());
			}
			
			// register sql design group by
			if(ArrayUtils.isNotEmpty(autocompleteDesignCompound.getSqlDesignGroupBys())){
				for(SqlDesignGroupBy sqlDesignGroupBy:autocompleteDesignCompound.getSqlDesignGroupBys()){
					sqlDesignGroupBy.setSqlDesignId(sqlDesign.getSqlDesignId());
				}
				sqlDesignGroupByRepository.registerAll(autocompleteDesignCompound.getSqlDesignGroupBys());
			}
			
			// register sql design having
			if(ArrayUtils.isNotEmpty(autocompleteDesignCompound.getSqlDesignHavings())){
				for(SqlDesignHaving sqlDesignHaving:autocompleteDesignCompound.getSqlDesignHavings()){
					sqlDesignHaving.setSqlDesignId(sqlDesign.getSqlDesignId());
				}
				sqlDesignHavingRepository.registerAll(autocompleteDesignCompound.getSqlDesignHavings());
			}
			
			// register sql design results
			if(ArrayUtils.isNotEmpty(autocompleteDesignCompound.getSqlDesignResults())){
				for(SqlDesignResult sqlDesignResult:autocompleteDesignCompound.getSqlDesignResults()){
					sqlDesignResult.setSqlDesignId(sqlDesign.getSqlDesignId());
				}
				sqlDesignResultRepository.registerAll(autocompleteDesignCompound.getSqlDesignResults());
			}
			
			// register sql design inputs
			if(ArrayUtils.isNotEmpty(autocompleteDesignCompound.getSqlDesignInputs())){
				for(SqlDesignInput sqlDesignInput:autocompleteDesignCompound.getSqlDesignInputs()){
					sqlDesignInput.setSqlDesignId(sqlDesign.getSqlDesignId());
				}
				sqlDesignInputRepository.registerAll(this.fillInputId(autocompleteDesignCompound.getSqlDesignInputs(),autocompleteDesignCompound.getSqlDesignInputs()));
			}
						
			autocompleteDesignCompound.setSqlDesign(sqlDesign);
			autocompleteDesignCompound.getAutocomplete().setSystemDate(FunctionCommon.getCurrentTime());
			autocompleteDesignCompound.getAutocomplete().setCreatedBy(common.getCreatedBy());
			autocompleteDesignCompound.getAutocomplete().setDesignStatus(DesignStatus.UNDER_DESIGN);
			autocompleteDesignRepository.register(autocompleteDesignCompound.getAutocomplete());
		}
		
	}
	private SqlDesignInput[] fillInputId(SqlDesignInput[] newSqlDesignInputs,SqlDesignInput[] sqlDesignInputs) {
		Long start = sqlDesignInputRepository.preserveIds(newSqlDesignInputs.length)-newSqlDesignInputs.length;
		SqlDesignInput sqlDesignInput=null;
		for(int i=0;i<newSqlDesignInputs.length;i++){
			sqlDesignInput = newSqlDesignInputs[i];
			sqlDesignInput.setSqlDesignInputId(start++);
			if(StringUtils.isNotBlank(sqlDesignInput.getGroupId())){
				for(int j=0;j<sqlDesignInputs.length;j++){
					if(DataTypeUtils.equals(sqlDesignInputs[j].getItemSeqNo(),sqlDesignInput.getGroupId())){
						sqlDesignInput.setSqlDesignInputParentId(sqlDesignInputs[j].getSqlDesignInputId());
						break;
					}
				}
			}
		}
		return newSqlDesignInputs;
	}
	@Override
	public void registerAdvancedAutocomleteDesign(AutocompleteDesignCompound autocompleteDesignCompound, CommonModel common) {
		if(autocompleteDesignCompound!=null) {
			this.validateDesignStatus(autocompleteDesignCompound.getAutocomplete(),true, common);
			this.validateNameCodeUnique(autocompleteDesignCompound);
			this.validateSqlSyntax(autocompleteDesignCompound);
			
			SqlDesign sqlDesign = autocompleteDesignCompound.getSqlDesign();
			sqlDesign.setSqlDesignName(autocompleteDesignCompound.getAutocomplete().getAutocompleteName());
			sqlDesign.setSqlDesignCode(autocompleteDesignCompound.getAutocomplete().getAutocompleteCode());
			sqlDesign.setDesignType(SQLDesignType.ADVANCED_AUTOCOMPLETE);
			sqlDesign.setSystemDate(FunctionCommon.getCurrentTime());
			sqlDesign.setModuleId(autocompleteDesignCompound.getAutocomplete().getModuleId());
			autocompleteDesignCompound.getAutocomplete().setProjectId(common.getWorkingProjectId());
			sqlDesign.setDesignStatus(DesignStatus.UNDER_DESIGN);
			sqlDesignRepository.register(sqlDesign);
			
			if(ArrayUtils.isNotEmpty(autocompleteDesignCompound.getSqlDesignInputs())){
				for(SqlDesignInput sqlDesignInput:autocompleteDesignCompound.getSqlDesignInputs()){
					sqlDesignInput.setSqlDesignId(sqlDesign.getSqlDesignId());
				}
				sqlDesignInputRepository.registerAll(this.fillInputId(autocompleteDesignCompound.getSqlDesignInputs(),autocompleteDesignCompound.getSqlDesignInputs()));
			}
			
			autocompleteDesignCompound.setSqlDesign(sqlDesign);
			autocompleteDesignCompound.getAutocomplete().setSystemDate(FunctionCommon.getCurrentTime());
			autocompleteDesignCompound.getAutocomplete().setCreatedBy(common.getUpdatedBy());
			autocompleteDesignCompound.getAutocomplete().setDesignStatus(DesignStatus.UNDER_DESIGN);
			autocompleteDesignRepository.register(autocompleteDesignCompound.getAutocomplete());
		}
	}
	
	@Override
	public boolean delete(AutocompleteDesignCompound autocompleteDesignCompound, CommonModel common) {
		Boolean result = true;
		if(autocompleteDesignCompound!=null) {
			AutocompleteDesign autocompleteDesign = this.validateExistence(autocompleteDesignCompound, common);
			//this.validateConsistency(autocompleteDesign, common);
			autocompleteDesignRepository.deleteSqlDesignChildren(autocompleteDesign.getSqlDesign().getSqlDesignId());
			autocompleteDesignCompound.getAutocomplete().setSystemDate(FunctionCommon.getCurrentTime());
			autocompleteDesign.getSqlDesign().setSystemDate(FunctionCommon.getCurrentTime());
			this.deleteAffected(autocompleteDesign, common);
			if(!autocompleteDesignRepository.delete(autocompleteDesignCompound.getAutocomplete())){
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
			}
			
			if(!sqlDesignRepository.delete(autocompleteDesign.getSqlDesign())){
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
			}
		} else {
			result = false;
		}
		return result;
	}
	
	private void ModifyAffected(AutocompleteDesign autocomplete,CommonModel common) {
		ImpactChangeJobControl jobControl = new ImpactChangeJobControl();
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		jobControl.setJobAppCd(DbDomainConst.ImpactChangeDesign.JOB_APP_CD);
		jobControl.setProjectId(String.valueOf(autocomplete.getProjectId()));
		jobControl.setModuleId(String.valueOf(autocomplete.getModuleId()));
		jobControl.setCreatedBy(String.valueOf(common.getCreatedBy()));
		jobControl.setImpactType(String.valueOf(DbDomainConst.FromResourceType.AUTOCOMPLETE_DESIGN));
		jobControl.setImpactId(String.valueOf(autocomplete.getAutocompleteId()));
		jobControl.setCurAppStatus(GenerateAppStatus.INIT);
		jobControl.setAddDateTime(currentTime);
		jobControl.setUpdDateTime(currentTime);
		jobControl.setJobArgNm6(ImpactChangeDesign.CASE_MODIFY);
		jobControl.setJobArgNm7(autocomplete.getAutocompleteCode());
		
		//check the same business type job.
		Long count = impactChangeRepository.countImpactChangeByType(jobControl);
		if(count == 0)
			impactChangeRepository.registerImpactChange(jobControl);
		else
			impactChangeRepository.modifyImpactChange(jobControl);
    }
	
	 private void deleteAffected(AutocompleteDesign autocomplete,CommonModel common) {
			ImpactChangeJobControl jobControl = new ImpactChangeJobControl();
			Timestamp currentTime = FunctionCommon.getCurrentTime();
			jobControl.setJobAppCd(DbDomainConst.ImpactChangeDesign.JOB_APP_CD);
			jobControl.setProjectId(String.valueOf(autocomplete.getProjectId()));
			jobControl.setModuleId(String.valueOf(autocomplete.getModuleId()));
			jobControl.setCreatedBy(String.valueOf(common.getCreatedBy()));
			jobControl.setImpactType(String.valueOf(DbDomainConst.FromResourceType.AUTOCOMPLETE_DESIGN));
			jobControl.setImpactId(String.valueOf(autocomplete.getAutocompleteId()));
			jobControl.setCurAppStatus(GenerateAppStatus.INIT);
			jobControl.setAddDateTime(currentTime);
			jobControl.setUpdDateTime(currentTime);
			jobControl.setJobArgNm6(ImpactChangeDesign.CASE_DELETE);
			jobControl.setJobArgNm7(autocomplete.getAutocompleteCode());
			
			//check the same business type job.
			Long count = impactChangeRepository.countImpactChangeByType(jobControl);
			if(count == 0)
				impactChangeRepository.registerImpactChange(jobControl);
			else
				impactChangeRepository.modifyImpactChange(jobControl);
	    }

	@Override
	public AutocompleteDesign findOneById(Long autocompleteId) {
		return autocompleteDesignRepository.findOneById(autocompleteId);
	}

	@Override
	public AutocompleteDesignCompound findCompoundById(Long autocompleteId) {
		AutocompleteDesignCompound autocompleteDesignCompound = null;
		AutocompleteDesign autocompleteDesign = this.findOneById(autocompleteId);
		if(autocompleteDesign != null) {
			autocompleteDesignCompound = new AutocompleteDesignCompound();
			autocompleteDesignCompound.setAutocomplete(autocompleteDesign);
			autocompleteDesignCompound.setSqlDesign(sqlDesignRepository.findOneById(autocompleteDesign.getSqlDesign().getSqlDesignId()));
			autocompleteDesignCompound.setSqlDesignTables(sqlDesignTableRepository.findAllBySqlDesignId(autocompleteDesign.getSqlDesign().getSqlDesignId()));
			List<SqlDesignTableItem> sqlDesignTableItems = sqlDesignTableItemRepository.findAllBySqlDesignId(autocompleteDesign.getSqlDesign().getSqlDesignId());

			Map<Long,List<SqlDesignTableItem>> sqlDesignTableItemsMap = new HashMap<Long,List<SqlDesignTableItem>>();
			for(SqlDesignTableItem item:sqlDesignTableItems){
				if(sqlDesignTableItemsMap.get(item.getSqlDesignTableId())==null){
					sqlDesignTableItemsMap.put(item.getSqlDesignTableId(),new ArrayList<SqlDesignTableItem>());
				}
				sqlDesignTableItemsMap.get(item.getSqlDesignTableId()).add(item);
			}
			
			for(SqlDesignTable sqldesignTable : autocompleteDesignCompound.getSqlDesignTables()){
				List<SqlDesignTableItem> temporaryList = sqlDesignTableItemsMap.get(sqldesignTable.getSqlDesignTableId());
				if(CollectionUtils.isNotEmpty(temporaryList)) {
					sqldesignTable.setSqlDesignTableItems(temporaryList.toArray(new SqlDesignTableItem[temporaryList.size()]));
				}
			}
			
			autocompleteDesignCompound.setSqlDesignConditions(sqlDesignConditionRepository.findAllBySqlDesignId(autocompleteDesign.getSqlDesign().getSqlDesignId()));
			autocompleteDesignCompound.setSqlDesignOrders(sqlDesignOrderRepository.findAllBySqlDesignId(autocompleteDesign.getSqlDesign().getSqlDesignId()));
			autocompleteDesignCompound.setSqlDesignGroupBys(sqlDesignGroupByRepository.findAllBySqlDesignId(autocompleteDesign.getSqlDesign().getSqlDesignId()));
			autocompleteDesignCompound.setSqlDesignHavings(sqlDesignHavingRepository.findAllBySqlDesignId(autocompleteDesign.getSqlDesign().getSqlDesignId()));
			autocompleteDesignCompound.setSqlDesignResults(sqlDesignResultRepository.findAllBySqlDesignId(autocompleteDesign.getSqlDesign().getSqlDesignId()));
			autocompleteDesignCompound.setSqlDesignInputs(sqlDesignInputRepository.findAllBySqlDesignId(autocompleteDesign.getSqlDesign().getSqlDesignId()));
			return autocompleteDesignCompound;
		}
		else {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0001)));
		}
	}

	@Override
	public void modifyAutocomleteDesign(AutocompleteDesignCompound autocompleteDesignCompound, CommonModel common) {
		if(autocompleteDesignCompound!=null) {
			this.validateExistence(autocompleteDesignCompound, common);
			this.validateNameCodeUnique(autocompleteDesignCompound);
			this.validateSqlSyntax(autocompleteDesignCompound);
			
			Long sqlDesignId = autocompleteDesignCompound.getSqlDesign().getSqlDesignId();
			SqlDesign sqlDesign = autocompleteDesignCompound.getSqlDesign();
			sqlDesign.setSqlDesignId(sqlDesignId);
			List<Long> exceptionGroup = new ArrayList<Long>();
			List<Long> nestedExceptionGroup = new ArrayList<Long>();
			
			if(ArrayUtils.isNotEmpty(autocompleteDesignCompound.getSqlDesignTables())){
				List<SqlDesignTable> modificationListTable = new ArrayList<SqlDesignTable>();
				List<SqlDesignTableItem> registrationListTableItem = new ArrayList<SqlDesignTableItem>();
				List<SqlDesignTableItem> modificationListTableItem = new ArrayList<SqlDesignTableItem>();
				for(SqlDesignTable sqlDesignTable:autocompleteDesignCompound.getSqlDesignTables()){
					sqlDesignTable.setSqlDesignId(sqlDesignId);
					boolean isNotCrossJoin = !StringUtils.equals(sqlDesignTable.getJoinType(), JoinType.CROSS_JOIN.getCode());
					if(!(sqlDesignTable.getSqlDesignTableId()==null || sqlDesignTable.getSqlDesignTableId() <= 0)){
						exceptionGroup.add(sqlDesignTable.getSqlDesignTableId());
						modificationListTable.add(sqlDesignTable);
						if(sqlDesignTable.getJoinTableId()!=null && isNotCrossJoin){
							for(SqlDesignTableItem sqlDesignTableItem:sqlDesignTable.getSqlDesignTableItems()) {
								sqlDesignTableItem.setSqlDesignTableId(sqlDesignTable.getSqlDesignTableId());
								sqlDesignTableItem.setJoinTableId(sqlDesignTable.getJoinTableId());
								if(!(sqlDesignTableItem.getSqlDesignTableItemId()==null || sqlDesignTableItem.getSqlDesignTableItemId()<=0)) {
									nestedExceptionGroup.add(sqlDesignTableItem.getSqlDesignTableItemId());
									modificationListTableItem.add(sqlDesignTableItem);
								} else {
									registrationListTableItem.add(sqlDesignTableItem);
								}
							}
						} 
					} else {
						sqlDesignTableRepository.register(sqlDesignTable);
						exceptionGroup.add(sqlDesignTable.getSqlDesignTableId());
						if(sqlDesignTable.getJoinTableId()!=null && isNotCrossJoin){
							for(SqlDesignTableItem sqlDesignTableItem:sqlDesignTable.getSqlDesignTableItems()) {
								sqlDesignTableItem.setSqlDesignTableId(sqlDesignTable.getSqlDesignTableId());
								sqlDesignTableItem.setJoinTableId(sqlDesignTable.getJoinTableId());
								registrationListTableItem.add(sqlDesignTableItem);
							}
						}
					}
				}
				sqlDesignTableItemRepository.deleteGroupBySqlDesignId(sqlDesignId, nestedExceptionGroup);
				sqlDesignTableRepository.deleteByExceptionalGroup(sqlDesignId, exceptionGroup);
				
				if(modificationListTable.size()>0) {
					sqlDesignTableRepository.modifyAll(modificationListTable.toArray(new SqlDesignTable[modificationListTable.size()]));
				}
				
				if(modificationListTableItem.size()>0){
					sqlDesignTableItemRepository.modifyAll(modificationListTableItem.toArray(new SqlDesignTableItem[modificationListTableItem.size()]));
				}
				if(registrationListTableItem.size()>0){
					sqlDesignTableItemRepository.registerAll(registrationListTableItem.toArray(new SqlDesignTableItem[registrationListTableItem.size()]));
				}
			}
			exceptionGroup.clear();
			
			if(ArrayUtils.isNotEmpty(autocompleteDesignCompound.getSqlDesignConditions())) {
				List<SqlDesignCondition> registrationList = new ArrayList<SqlDesignCondition>();
				List<SqlDesignCondition> modificationList = new ArrayList<SqlDesignCondition>();
				for(SqlDesignCondition sqlDesignCondition:autocompleteDesignCompound.getSqlDesignConditions()){
					sqlDesignCondition.setSqlDesignId(sqlDesignId);
					if(!(sqlDesignCondition.getConditionsId()==null || sqlDesignCondition.getConditionsId()<=0)) {
						modificationList.add(sqlDesignCondition);
						exceptionGroup.add(sqlDesignCondition.getConditionsId());
					} else {
						registrationList.add(sqlDesignCondition);
					}
				}
				sqlDesignConditionRepository.deleteByExceptionalGroup(sqlDesignId, exceptionGroup);
				if(modificationList.size()>0){
					sqlDesignConditionRepository.modifyAll(modificationList.toArray(new SqlDesignCondition[modificationList.size()]));
				} 
				if(registrationList.size()>0) {
					sqlDesignConditionRepository.registerAll(registrationList.toArray(new SqlDesignCondition[registrationList.size()]));
				}
			} else {
				sqlDesignConditionRepository.deleteByExceptionalGroup(sqlDesignId, exceptionGroup);
			}
			exceptionGroup.clear();
			
			if(ArrayUtils.isNotEmpty(autocompleteDesignCompound.getSqlDesignGroupBys())){
				List<SqlDesignGroupBy> registrationList = new ArrayList<SqlDesignGroupBy>();
				List<SqlDesignGroupBy> modificationList = new ArrayList<SqlDesignGroupBy>();
				for(SqlDesignGroupBy sqlDesignGroupBy:autocompleteDesignCompound.getSqlDesignGroupBys()){
					sqlDesignGroupBy.setSqlDesignId(sqlDesignId);
					if(!(sqlDesignGroupBy.getGroupById() == null || sqlDesignGroupBy.getGroupById()<=0)) {
						modificationList.add(sqlDesignGroupBy);
						exceptionGroup.add(sqlDesignGroupBy.getGroupById());
					} else {
						registrationList.add(sqlDesignGroupBy);
					}
				}
				sqlDesignGroupByRepository.deleteByExceptionalGroup(sqlDesignId, exceptionGroup);
				if(modificationList.size()>0){
					sqlDesignGroupByRepository.modifyAll(modificationList.toArray(new SqlDesignGroupBy[modificationList.size()]));
				}
				if(registrationList.size()>0){
					sqlDesignGroupByRepository.registerAll(registrationList.toArray(new SqlDesignGroupBy[registrationList.size()]));
				}
			} else {
				sqlDesignGroupByRepository.deleteByExceptionalGroup(sqlDesignId, exceptionGroup);
			}
			exceptionGroup.clear();
			
			if(ArrayUtils.isNotEmpty(autocompleteDesignCompound.getSqlDesignHavings())){
				List<SqlDesignHaving> registrationList = new ArrayList<SqlDesignHaving>();
				List<SqlDesignHaving> modificationList = new ArrayList<SqlDesignHaving>();
				for(SqlDesignHaving sqlDesignHaving:autocompleteDesignCompound.getSqlDesignHavings()){
					sqlDesignHaving.setSqlDesignId(sqlDesignId);
					if(!(sqlDesignHaving.getHavingId()==null || sqlDesignHaving.getHavingId()<=0)) {
						exceptionGroup.add(sqlDesignHaving.getHavingId());
						modificationList.add(sqlDesignHaving);
					} else {
						registrationList.add(sqlDesignHaving);
					}
				}
				sqlDesignHavingRepository.deleteByExceptionalGroup(sqlDesignId, exceptionGroup);
				if(modificationList.size()>0){
					sqlDesignHavingRepository.modifyAll(modificationList.toArray(new SqlDesignHaving[modificationList.size()]));
				}
				if(registrationList.size()>0){
					sqlDesignHavingRepository.registerAll(registrationList.toArray(new SqlDesignHaving[registrationList.size()]));
				}
			} else {
				sqlDesignHavingRepository.deleteByExceptionalGroup(sqlDesignId, exceptionGroup);
			}
			exceptionGroup.clear();
			
			
			if(ArrayUtils.isNotEmpty(autocompleteDesignCompound.getSqlDesignResults())){
				List<SqlDesignResult> registrationList = new ArrayList<SqlDesignResult>();
				List<SqlDesignResult> modificationList = new ArrayList<SqlDesignResult>();
				for(SqlDesignResult sqlDesignResult:autocompleteDesignCompound.getSqlDesignResults()){
					sqlDesignResult.setSqlDesignId(sqlDesignId);
					if(!(sqlDesignResult.getResultId()==null || sqlDesignResult.getResultId()<=0)){
						modificationList.add(sqlDesignResult);
						exceptionGroup.add(sqlDesignResult.getResultId());
					} else {
						registrationList.add(sqlDesignResult);
					}
				}
				sqlDesignResultRepository.deleteByExceptionalGroup(sqlDesignId, exceptionGroup);
				if(modificationList.size()>0){
					sqlDesignResultRepository.modifyAll(modificationList.toArray(new SqlDesignResult[modificationList.size()]));
				}
				if(registrationList.size()>0){
					sqlDesignResultRepository.registerAll(registrationList.toArray(new SqlDesignResult[registrationList.size()]));
				}
			} else {
				sqlDesignResultRepository.deleteByExceptionalGroup(sqlDesignId, exceptionGroup);
			}
			exceptionGroup.clear();
			
			if(ArrayUtils.isNotEmpty(autocompleteDesignCompound.getSqlDesignOrders())) {
				List<SqlDesignOrder> registrationList = new ArrayList<SqlDesignOrder>();
				List<SqlDesignOrder> modificationList = new ArrayList<SqlDesignOrder>();
				for(SqlDesignOrder sqlDesignOrder:autocompleteDesignCompound.getSqlDesignOrders()){
					sqlDesignOrder.setSqlDesignId(sqlDesignId);
					if(!(sqlDesignOrder.getOrderId()==null || sqlDesignOrder.getOrderId()<=0)) {
						modificationList.add(sqlDesignOrder);
						exceptionGroup.add(sqlDesignOrder.getOrderId());
					} else {
						registrationList.add(sqlDesignOrder);
					}
				}
				sqlDesignOrderRepository.deleteByExceptionalGroup(sqlDesignId, exceptionGroup);
				if(modificationList.size()>0){
					sqlDesignOrderRepository.modifyAll(modificationList.toArray(new SqlDesignOrder[modificationList.size()]));
				}
				if(registrationList.size()>0){
					sqlDesignOrderRepository.registerAll(registrationList.toArray(new SqlDesignOrder[registrationList.size()]));
				}
			} else {
				sqlDesignOrderRepository.deleteByExceptionalGroup(sqlDesignId, exceptionGroup);
			}
			exceptionGroup.clear();
			
			if(ArrayUtils.isNotEmpty(autocompleteDesignCompound.getSqlDesignInputs())){
				List<SqlDesignInput> registrationList = new ArrayList<SqlDesignInput>();
				List<SqlDesignInput> modificationList = new ArrayList<SqlDesignInput>();
				for(SqlDesignInput sqlDesignInput:autocompleteDesignCompound.getSqlDesignInputs()){
					sqlDesignInput.setSqlDesignId(sqlDesignId);
					if(!(sqlDesignInput.getSqlDesignInputId()==null || sqlDesignInput.getSqlDesignInputId()<=0)){
						modificationList.add(sqlDesignInput);
						exceptionGroup.add(sqlDesignInput.getSqlDesignInputId());
					} else {
						registrationList.add(sqlDesignInput);
					}
				}
				sqlDesignInputRepository.deleteByExceptionalGroup(sqlDesignId, exceptionGroup);
				if(modificationList.size()>0){
					sqlDesignInputRepository.modifyAll(modificationList.toArray(new SqlDesignInput[modificationList.size()]));
				}
				if(registrationList.size()>0){
					sqlDesignInputRepository.registerAll(this.fillInputId(registrationList.toArray(new SqlDesignInput[registrationList.size()]),autocompleteDesignCompound.getSqlDesignInputs()));
				}
			} else {
				sqlDesignInputRepository.deleteByExceptionalGroup(sqlDesignId, exceptionGroup);
			}
			exceptionGroup.clear();
			
			sqlDesign.setSystemDate(FunctionCommon.getCurrentTime());
			sqlDesign.setSqlDesignName(autocompleteDesignCompound.getAutocomplete().getAutocompleteName());
			sqlDesign.setSqlDesignCode(autocompleteDesignCompound.getAutocomplete().getAutocompleteCode());
			sqlDesign.setModuleId(autocompleteDesignCompound.getAutocomplete().getModuleId());
			sqlDesignRepository.modifyFromAutocomplete(sqlDesign);
			
			autocompleteDesignCompound.getAutocomplete().setSystemDate(FunctionCommon.getCurrentTime());
			autocompleteDesignCompound.getAutocomplete().setUpdatedBy(common.getUpdatedBy());
			this.validateProjectModification(autocompleteDesignCompound);
			
			if(!autocompleteDesignRepository.modify(autocompleteDesignCompound.getAutocomplete())){
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
			}
		}
		this.ModifyAffected(autocompleteDesignCompound.getAutocomplete(), common);
	}

	@Override
	public void modifyAdvancedAutocomleteDesign(AutocompleteDesignCompound autocompleteDesignCompound, CommonModel common) {
		if(autocompleteDesignCompound!=null) {
			this.validateExistence(autocompleteDesignCompound, common);
			this.validateNameCodeUnique(autocompleteDesignCompound);
			this.validateSqlSyntax(autocompleteDesignCompound);
			
			SqlDesign sqlDesign = autocompleteDesignCompound.getSqlDesign();
			List<Long> exceptionGroup = new ArrayList<Long>();
			
			if(BooleanUtils.isTrue(sqlDesign.getIsConversion())){
				sqlDesign.setDesignType(SQLDesignType.ADVANCED_AUTOCOMPLETE);
				sqlDesignRepository.deleteSqlDesignChildrenExceptIo(sqlDesign.getSqlDesignId());
			}
			
			if(ArrayUtils.isNotEmpty(autocompleteDesignCompound.getSqlDesignInputs())){
				List<SqlDesignInput> registrationList = new ArrayList<SqlDesignInput>();
				List<SqlDesignInput> modificationList = new ArrayList<SqlDesignInput>();
				for(SqlDesignInput sqlDesignInput:autocompleteDesignCompound.getSqlDesignInputs()){
					sqlDesignInput.setSqlDesignId(sqlDesign.getSqlDesignId());
					if(!(sqlDesignInput.getSqlDesignInputId()==null || sqlDesignInput.getSqlDesignInputId()<=0)){
						modificationList.add(sqlDesignInput);
						exceptionGroup.add(sqlDesignInput.getSqlDesignInputId());
					} else {
						registrationList.add(sqlDesignInput);
					}
				}
				sqlDesignInputRepository.deleteByExceptionalGroup(sqlDesign.getSqlDesignId(), exceptionGroup);
				if(modificationList.size()>0){
					sqlDesignInputRepository.modifyAll(modificationList.toArray(new SqlDesignInput[modificationList.size()]));
				}
				if(registrationList.size()>0){
					sqlDesignInputRepository.registerAll(this.fillInputId(registrationList.toArray(new SqlDesignInput[registrationList.size()]),autocompleteDesignCompound.getSqlDesignInputs()));
				}
			} else {
				sqlDesignInputRepository.deleteByExceptionalGroup(sqlDesign.getSqlDesignId(), exceptionGroup);
			}
			exceptionGroup.clear();
			
			sqlDesign.setSystemDate(FunctionCommon.getCurrentTime());
			sqlDesign.setDesignType(SQLDesignType.ADVANCED_AUTOCOMPLETE);
			sqlDesignRepository.modifySqlText(sqlDesign);
			
			autocompleteDesignCompound.getAutocomplete().setSystemDate(FunctionCommon.getCurrentTime());
			autocompleteDesignCompound.getAutocomplete().setUpdatedBy(common.getUpdatedBy());
			this.validateProjectModification(autocompleteDesignCompound);
			if(!autocompleteDesignRepository.modify(autocompleteDesignCompound.getAutocomplete())){
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
			}
		}
		this.ModifyAffected(autocompleteDesignCompound.getAutocomplete(), common);
	}
	/**
	 * Validate existence.
	 *
	 * @param autocompleteDesignCompound the autocomplete design compound
	 * @return the autocomplete design
	 */
	private AutocompleteDesign validateExistence(AutocompleteDesignCompound autocompleteDesignCompound,Boolean isNeedCheckStatus, CommonModel common) {
		AutocompleteDesign autocompleteDesign = autocompleteDesignRepository.findOneById(autocompleteDesignCompound.getAutocomplete().getAutocompleteId());
		if(autocompleteDesign==null){
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037,
																MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0001)));
		}
		if(isNeedCheckStatus){
			this.validateDesignStatus(autocompleteDesign, common);
		}
		return autocompleteDesign;
	}
	private AutocompleteDesign validateExistence(AutocompleteDesignCompound autocompleteDesignCompound, CommonModel common) {
		return this.validateExistence(autocompleteDesignCompound, true, common);
	}
	/**
	 * Validate autocomplete to determine project name could be changed or not.
	 *
	 * @param autocompleteDesignCompound the autocomplete design compound
	 */
	private void validateProjectModification(
			AutocompleteDesignCompound autocompleteDesignCompound) {
		if(!autocompleteDesignRepository.isProjectChangeable(autocompleteDesignCompound.getAutocomplete())){
			throw new BusinessException(ResultMessages.error()
														.add(CommonMessageConst.ERR_SYS_0101,
															MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0050),
															MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0001)));
		}
	}
	
	/**
	 * Validate generated sql syntax
	 *
	 * @param autocompleteDesignCompound the autocomplete design compound
	 */
	private void validateSqlSyntax(SqlDesignCompound sqlDesignCompound) {
		Boolean isSqlValid = sqlBuilderService.validate(sqlDesignCompound);
		if(!isSqlValid){
			throw new BusinessException(ResultMessages.error().add(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0001));
		}
		
	}

	/**
	 * Validate autocomplete to determine autocomplete's name and code are unique or not.
	 *
	 * @param autocompleteDesignCompound the autocomplete design compound
	 */
	private void validateNameCodeUnique(AutocompleteDesignCompound autocompleteDesignCompound) {
		Integer flag = autocompleteDesignRepository.getExistNameCode(autocompleteDesignCompound.getAutocomplete());
		ResultMessages resultMessages = ResultMessages.error(); 
		switch(flag){
		case 1:
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0005));
			break;
		case 2:
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0006));
			break;
		case 3:
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0005));
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0006));
			break;
		}
		if(resultMessages.isNotEmpty()){
			throw new BusinessException(resultMessages);
		}
	}
	
	/**
	 * Validate consistency.
	 *
	 * @param autocompleteDesign the autocomplete design
	 */
	private void validateConsistency(AutocompleteDesign autocompleteDesign, CommonModel common) {
		List<ConsistencyValidationModel> consistencyValidationModels = autocompleteDesignRepository.getReferenceById(autocompleteDesign.getAutocompleteId(),common.getWorkingLanguageId());
		ResultMessages resultMessages = ResultMessages.error(); 
		
		for(ConsistencyValidationModel consistencyValidationModel:consistencyValidationModels) {
			if(consistencyValidationModel.getGroupType()==1){
				resultMessages.add(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0013,
						consistencyValidationModel.getItemName(),
						consistencyValidationModel.getGroupName());
			}
			if(consistencyValidationModel.getGroupType()==2){
				resultMessages.add(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0014,
						consistencyValidationModel.getItemName(),
						consistencyValidationModel.getGroupName());
			}
		}
		if(resultMessages.isNotEmpty()){
			throw new BusinessException(resultMessages);
		}
	}
	
	protected void validateDesignStatus(AutocompleteDesign autocompleteDesign,Boolean isRegistration, CommonModel common) {
		if(autocompleteDesign!=null){
			ResultMessages resultMessages = ResultMessages.error();
			common.setProjectId(autocompleteDesign.getProjectId());
			if(autocompleteDesign.getModuleId()!=null){
			/*	moduleService.setWorkingProjectId(getWorkingProjectId());
				moduleService.setAccountId(getAccountId());*/
				moduleService.validateModule(autocompleteDesign.getModuleId(), common);
			} else {
				/*projectService.setWorkingProjectId(getWorkingProjectId());
				projectService.setAccountId(getAccountId());*/
				projectService.validateProject(common);
			}
			if(!isRegistration){
				if(autocompleteDesign.getDesignStatus()==DesignStatus.FIXED){
					resultMessages.add(
							CommonMessageConst.ERR_SYS_0111,
							StringUtils.lowerCase(MessageUtils.getMessage(AutocompleteMessageConst.TQP_AUTOCOMPLETE_DESIGN)),
							autocompleteDesign.getAutocompleteName());
				}
			}
			
			if(resultMessages.isNotEmpty()){
				throw new BusinessException(resultMessages);
			}
		}
	}
	protected void validateDesignStatus(AutocompleteDesign autocompleteDesign, CommonModel common) {
		this.validateDesignStatus(autocompleteDesign, false, common);
	}
	@Override
	public TableDesignForeignKey findForeignKeyBetweenTwoTables(Long tableId,Long joinTableId) {
		TableDesignForeignKey tableForeignKey =  tableDesignForeignKeyRepository.findForeignKeyBetweenTwoTables(tableId, joinTableId);
		return tableForeignKey;
	}

	@Override
	public void modifyDesignStatus(AutocompleteDesignCompound autocompleteDesignCompound, CommonModel common) {
		if(autocompleteDesignCompound!=null){
			Timestamp currentTime = FunctionCommon.getCurrentTime();
			AutocompleteDesign autocompleteDesign = this.validateExistence(autocompleteDesignCompound,false, common);
			autocompleteDesign.setUpdatedDate(autocompleteDesignCompound.getAutocomplete().getUpdatedDate());
			autocompleteDesign.setUpdatedBy(common.getUpdatedBy());
			autocompleteDesign.setSystemDate(currentTime);
			
			SqlDesign sqlDesign = this.sqlDesignRepository.findOneById(autocompleteDesign.getSqlDesign().getSqlDesignId());
			sqlDesign.setSystemDate(currentTime);
			sqlDesign.setUpdatedBy(common.getUpdatedBy());
			if(DbDomainConst.DesignStatus.UNDER_DESIGN.equals(autocompleteDesign.getDesignStatus())) {
				autocompleteDesign.setDesignStatus(DbDomainConst.DesignStatus.FIXED);
				sqlDesign.setDesignStatus(DbDomainConst.DesignStatus.FIXED);
				if(!autocompleteDesignRepository.modifyDesignStatus(autocompleteDesign)) {
					throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0003)));
				}
				if(!this.sqlDesignRepository.modifyDesignStatus(sqlDesign)){
					throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0003)));
				}
			} else {
				autocompleteDesign.setDesignStatus(DbDomainConst.DesignStatus.UNDER_DESIGN);
				sqlDesign.setDesignStatus(DbDomainConst.DesignStatus.UNDER_DESIGN);
				if(!autocompleteDesignRepository.modifyDesignStatus(autocompleteDesign)) {
					throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0003)));
				}
				if(!this.sqlDesignRepository.modifyDesignStatus(sqlDesign)){
					throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0003)));
				}
				/*
				DungNN -20151027 - remove because not unnecessary
				Project project = projectRepository.findById(this.getProjectId(), accountId);
				project.setUpdatedBy(accountId);
				project.setUpdatedDate(currentTime);
				project.setStatus(DbDomainConst.DesignStatus.UNDER_DESIGN);
				projectRepository.modifyDesignStatus(project);*/
			}
		}
	}

	@Override
	public List<TableDesignDetails> getAllTableColumnByProject(Long projectId) {
		return tableDesignDetailRepository.getAllTableColumnByProject(projectId);
	}

	@Override
	public void findAllDeletionAffection(AutocompleteDesignCompound sqlDesignCompound, CommonModel common) {
		if(sqlDesignCompound != null){
			sqlDesignCompound.setAffectedScreenDesigns(screenDesignRepository.findAllScreenDesignsByAutocompleteId(sqlDesignCompound.getAutocomplete().getAutocompleteId(),common.getWorkingLanguageId()));
		}
		
	}
}
