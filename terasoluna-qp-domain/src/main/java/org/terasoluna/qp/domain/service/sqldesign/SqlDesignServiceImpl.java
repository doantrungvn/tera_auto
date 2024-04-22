package org.terasoluna.qp.domain.service.sqldesign;

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
import org.springframework.context.annotation.Primary;
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
import org.terasoluna.qp.app.common.constants.DbDomainConst.SqlPattern;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.LocaleUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.SqlDesignMessageConst;
import org.terasoluna.qp.app.message.TableDesignMessageConst;
import org.terasoluna.qp.app.message.ViewDesignMessageConst;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.ConsistencyValidationModel;
import org.terasoluna.qp.domain.model.ImpactChangeJobControl;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.model.SqlDesignCondition;
import org.terasoluna.qp.domain.model.SqlDesignFunctionGroup;
import org.terasoluna.qp.domain.model.SqlDesignGroupBy;
import org.terasoluna.qp.domain.model.SqlDesignHaving;
import org.terasoluna.qp.domain.model.SqlDesignInput;
import org.terasoluna.qp.domain.model.SqlDesignOrder;
import org.terasoluna.qp.domain.model.SqlDesignOutput;
import org.terasoluna.qp.domain.model.SqlDesignResult;
import org.terasoluna.qp.domain.model.SqlDesignTable;
import org.terasoluna.qp.domain.model.SqlDesignTableItem;
import org.terasoluna.qp.domain.model.SqlDesignValue;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.repository.businessdesign.BusinessDesignRepository;
import org.terasoluna.qp.domain.repository.domaindesign.DomainDesignRepository;
import org.terasoluna.qp.domain.repository.impactchange.ImpactChangeRepository;
import org.terasoluna.qp.domain.repository.project.ProjectRepository;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignRepository;
import org.terasoluna.qp.domain.repository.screenitem.ScreenItemRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignConditionRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignFunctionRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignGroupByRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignHavingRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignInputRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignOrderRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignOutputRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignResultRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignSearchCriteria;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignTableItemsRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignTableRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignValueRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignDetailRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignRepository;
import org.terasoluna.qp.domain.service.autocomplete.SqlBuilderService;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst.JoinType;
import org.terasoluna.qp.domain.service.module.ModuleService;
import org.terasoluna.qp.domain.service.project.ProjectService;

@Service
@Primary
@Transactional
public class SqlDesignServiceImpl implements SqlDesignService {

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
	SqlDesignOutputRepository sqlDesignOutputRepository;
	
	@Inject
	SqlDesignFunctionRepository sqlDesignFunctionRepository;
	
	@Inject
	SqlDesignValueRepository sqlDesignValueRepository;
	
	@Inject
	SqlBuilderService sqlBuilderService;
	
	@Inject
	TableDesignRepository tableDesignRepository;
	
	@Inject
	TableDesignDetailRepository tableDesignDetailRepository;
	
	@Inject
	ProjectRepository projectRepository;
	
	@Inject
	BusinessDesignRepository businessDesignRepository;
	
	@Inject
	ScreenDesignRepository screenDesignRepository;
	
	@Inject
	ScreenItemRepository screenItemRepository;
	
	@Inject
	ModuleService moduleService;
	
	@Inject
	ProjectService projectService;

	@Inject
	DomainDesignRepository domainDesignRepository;
	
	@Inject
	ImpactChangeRepository impactChangeRepository;
	
	@Override
	public Page<SqlDesign> findPageByCriteria(SqlDesignSearchCriteria criteria,
			Pageable pageable, CommonModel common) {
		criteria.setProjectId(common.getWorkingProjectId());
		long totalCount = sqlDesignRepository.countByCriteria(criteria);

		List<SqlDesign> result;
		if (0 < totalCount) {
			result = sqlDesignRepository.findByCriteria(criteria, pageable);
		} else {
			result = Collections.emptyList();
		}

		Page<SqlDesign> page = new PageImpl<SqlDesign>(result, pageable, totalCount);

		return page;
		
	}
	
	@Override
	public SqlDesign findOneById(Long sqlDesignId) {
		SqlDesign sqlDesign = sqlDesignRepository.findOneById(sqlDesignId);
		if(sqlDesign == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(SqlDesignMessageConst.SC_SQLDESIGN_SQL_DESIGN)));
		}
		return sqlDesign;
	}

	@Override
	public SqlDesignCompound findCompoundById(Long sqlDesignId) {
		SqlDesignCompound sqlDesignCompound = null;
		SqlDesign sqlDesign = this.findOneById(sqlDesignId);
		sqlDesignCompound = new SqlDesignCompound();
		sqlDesignCompound.setSqlDesign(sqlDesign);
		sqlDesignCompound.setSqlDesignTables(sqlDesignTableRepository.findAllBySqlDesignId(sqlDesignId));
		List<SqlDesignTableItem> sqlDesignTableItems = sqlDesignTableItemRepository.findAllBySqlDesignId(sqlDesignId);

		Map<Long,List<SqlDesignTableItem>> sqlDesignTableItemsMap = new HashMap<Long,List<SqlDesignTableItem>>();
		for(SqlDesignTableItem item:sqlDesignTableItems){
			if(sqlDesignTableItemsMap.get(item.getSqlDesignTableId())==null){
				sqlDesignTableItemsMap.put(item.getSqlDesignTableId(),new ArrayList<SqlDesignTableItem>());
			}
			sqlDesignTableItemsMap.get(item.getSqlDesignTableId()).add(item);
		}
		
		for(SqlDesignTable sqldesignTable : sqlDesignCompound.getSqlDesignTables()){
			List<SqlDesignTableItem> temporaryList = sqlDesignTableItemsMap.get(sqldesignTable.getSqlDesignTableId());
			if(CollectionUtils.isNotEmpty(temporaryList)) {
				sqldesignTable.setSqlDesignTableItems(temporaryList.toArray(new SqlDesignTableItem[temporaryList.size()]));
			}
		}
		if(SqlPattern.SELECT != sqlDesign.getSqlPattern()){
			sqlDesignCompound.setSqlDesignTable(ArrayUtils.isEmpty(sqlDesignCompound.getSqlDesignTables())?null:sqlDesignCompound.getSqlDesignTables()[0]);
		}
		sqlDesignCompound.setSqlDesignConditions(sqlDesignConditionRepository.findAllBySqlDesignId(sqlDesignId));
		sqlDesignCompound.setSqlDesignOrders(sqlDesignOrderRepository.findAllBySqlDesignId(sqlDesignId));
		sqlDesignCompound.setSqlDesignGroupBys(sqlDesignGroupByRepository.findAllBySqlDesignId(sqlDesignId));
		sqlDesignCompound.setSqlDesignHavings(sqlDesignHavingRepository.findAllBySqlDesignId(sqlDesignId));
		sqlDesignCompound.setSqlDesignResults(sqlDesignResultRepository.findAllBySqlDesignId(sqlDesignId));
		sqlDesignCompound.setSqlDesignInputs(sqlDesignInputRepository.findAllBySqlDesignId(sqlDesignId));
		sqlDesignCompound.setSqlDesignOutputs(sqlDesignOutputRepository.findAllBySqlDesignId(sqlDesignId));
		sqlDesignCompound.setSqlDesignValues(sqlDesignValueRepository.findAllBySqlDesignId(sqlDesignId));
		return sqlDesignCompound;
		
	}
	
	@Override
	public SqlDesignCompound findCompoundForGenerationById(Long sqlDesignId) {
		SqlDesignCompound sqlDesignCompound = null;
		SqlDesign sqlDesign = this.findOneById(sqlDesignId);
		sqlDesignCompound = new SqlDesignCompound();
		sqlDesignCompound.setSqlDesign(sqlDesign);
		sqlDesignCompound.setSqlDesignTables(sqlDesignTableRepository.findAllBySqlDesignId(sqlDesignId));
		List<SqlDesignTableItem> sqlDesignTableItems = sqlDesignTableItemRepository.findAllBySqlDesignId(sqlDesignId);

		Map<Long,List<SqlDesignTableItem>> sqlDesignTableItemsMap = new HashMap<Long,List<SqlDesignTableItem>>();
		for(SqlDesignTableItem item:sqlDesignTableItems){
			if(sqlDesignTableItemsMap.get(item.getSqlDesignTableId())==null){
				sqlDesignTableItemsMap.put(item.getSqlDesignTableId(),new ArrayList<SqlDesignTableItem>());
			}
			sqlDesignTableItemsMap.get(item.getSqlDesignTableId()).add(item);
		}
		
		for(SqlDesignTable sqldesignTable : sqlDesignCompound.getSqlDesignTables()){
			List<SqlDesignTableItem> temporaryList = sqlDesignTableItemsMap.get(sqldesignTable.getSqlDesignTableId());
			if(CollectionUtils.isNotEmpty(temporaryList)) {
				sqldesignTable.setSqlDesignTableItems(temporaryList.toArray(new SqlDesignTableItem[temporaryList.size()]));
			}
		}
		if(SqlPattern.SELECT != sqlDesign.getSqlPattern()){
			sqlDesignCompound.setSqlDesignTable(ArrayUtils.isEmpty(sqlDesignCompound.getSqlDesignTables())?null:sqlDesignCompound.getSqlDesignTables()[0]);
		}
		sqlDesignCompound.setSqlDesignConditions(sqlDesignConditionRepository.findAllBySqlDesignId(sqlDesignId));
		sqlDesignCompound.setSqlDesignOrders(sqlDesignOrderRepository.findAllBySqlDesignId(sqlDesignId));
		sqlDesignCompound.setSqlDesignGroupBys(sqlDesignGroupByRepository.findAllBySqlDesignId(sqlDesignId));
		sqlDesignCompound.setSqlDesignHavings(sqlDesignHavingRepository.findAllBySqlDesignId(sqlDesignId));
		sqlDesignCompound.setSqlDesignResults(sqlDesignResultRepository.findAllBySqlDesignId(sqlDesignId));
		sqlDesignCompound.setSqlDesignInputs(sqlDesignInputRepository.findAllForGenerationBySqlDesignId(sqlDesignId));
		sqlDesignCompound.setSqlDesignOutputs(sqlDesignOutputRepository.findAllForGenerationBySqlDesignId(sqlDesignId));
		sqlDesignCompound.setSqlDesignValues(sqlDesignValueRepository.findAllBySqlDesignId(sqlDesignId));
		return sqlDesignCompound;
		
	}

	@Override
	public void registerSqlDesign(SqlDesignCompound sqlDesignCompound, CommonModel common) {
		if(sqlDesignCompound!=null) {
			this.validateDesignStatus(sqlDesignCompound.getSqlDesign(),true, common);
			this.validateNameCodeUnique(sqlDesignCompound);
			this.validateSqlSyntax(sqlDesignCompound, common);
			
			SqlDesign sqlDesign = sqlDesignCompound.getSqlDesign();
			sqlDesign.setDesignType(SQLDesignType.SQL_BUILDER);
			sqlDesign.setSystemDate(FunctionCommon.getCurrentTime());
			sqlDesign.setDesignStatus(DesignStatus.UNDER_DESIGN);
			sqlDesign.setProjectId(common.getWorkingProjectId());
			sqlDesignRepository.register(sqlDesign);
			
			// register sql design table
			if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignTables())){
				List<SqlDesignTableItem> sqlDesignTableItems = new ArrayList<SqlDesignTableItem>();
				for(SqlDesignTable sqlDesignTable:sqlDesignCompound.getSqlDesignTables()){
					sqlDesignTable.setSqlDesignId(sqlDesign.getSqlDesignId());
					sqlDesignTable.setTableId(sqlDesignCompound.getSqlDesignTables()[0].getTableId());
					sqlDesignTableRepository.register(sqlDesignTable);
					if(sqlDesignTable.getJoinTableId()!=null && !StringUtils.equals(sqlDesignTable.getJoinType(), JoinType.CROSS_JOIN.getCode())){
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
			}
			// register sql design conditions
			if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignConditions())) {
				for(SqlDesignCondition sqlDesignCondition:sqlDesignCompound.getSqlDesignConditions()){
					sqlDesignCondition.setSqlDesignId(sqlDesign.getSqlDesignId());
				}
				sqlDesignConditionRepository.registerAll(sqlDesignCompound.getSqlDesignConditions());
			}
			
			// register sql design order
			if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignOrders())) {
				for(SqlDesignOrder sqlDesignOrder:sqlDesignCompound.getSqlDesignOrders()){
					sqlDesignOrder.setSqlDesignId(sqlDesign.getSqlDesignId());
				}
				sqlDesignOrderRepository.registerAll(sqlDesignCompound.getSqlDesignOrders());
			}
			
			// register sql design group by
			if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignGroupBys())){
				for(SqlDesignGroupBy sqlDesignGroupBy:sqlDesignCompound.getSqlDesignGroupBys()){
					sqlDesignGroupBy.setSqlDesignId(sqlDesign.getSqlDesignId());
				}
				sqlDesignGroupByRepository.registerAll(sqlDesignCompound.getSqlDesignGroupBys());
			}
			
			// register sql design having
			if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignHavings())){
				for(SqlDesignHaving sqlDesignHaving:sqlDesignCompound.getSqlDesignHavings()){
					sqlDesignHaving.setSqlDesignId(sqlDesign.getSqlDesignId());
				}
				sqlDesignHavingRepository.registerAll(sqlDesignCompound.getSqlDesignHavings());
			}
			
			// register sql design results
			if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignResults())){
				for(SqlDesignResult sqlDesignResult:sqlDesignCompound.getSqlDesignResults()){
					sqlDesignResult.setSqlDesignId(sqlDesign.getSqlDesignId());
				}
				sqlDesignResultRepository.registerAll(sqlDesignCompound.getSqlDesignResults());
			}
			
			if(sqlDesignCompound.getSqlDesignTable()!=null){
				sqlDesignCompound.getSqlDesignTable().setSqlDesignId(sqlDesignCompound.getSqlDesign().getSqlDesignId());
				sqlDesignTableRepository.register(sqlDesignCompound.getSqlDesignTable());
			}
			
			if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignValues())){
				for(SqlDesignValue sqlDesignValue:sqlDesignCompound.getSqlDesignValues()){
					sqlDesignValue.setSqlDesignId(sqlDesign.getSqlDesignId());
				}
				sqlDesignValueRepository.registerAll(sqlDesignCompound.getSqlDesignValues());
			}
			
			// register sql design inputs
			if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignInputs())){
				for(SqlDesignInput sqlDesignInput:sqlDesignCompound.getSqlDesignInputs()){
					sqlDesignInput.setSqlDesignId(sqlDesign.getSqlDesignId());
				}
				sqlDesignInputRepository.registerAll(this.fillInputId(sqlDesignCompound.getSqlDesignInputs(),sqlDesignCompound.getSqlDesignInputs()));
			}
			
			// register sql design outputs
			if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignOutputs())){
				for(SqlDesignOutput sqlDesignOutput:sqlDesignCompound.getSqlDesignOutputs()){
					sqlDesignOutput.setSqlDesignId(sqlDesign.getSqlDesignId());
				}
				sqlDesignOutputRepository.registerAll(this.fillOutputId(sqlDesignCompound.getSqlDesignOutputs(),sqlDesignCompound.getSqlDesignOutputs()));
			}
		}
	}
	
	@Override
	public void registerAdvancedSqlDesign(SqlDesignCompound sqlDesignCompound, CommonModel common) {
		if(sqlDesignCompound!=null) {
			this.validateDesignStatus(sqlDesignCompound.getSqlDesign(),true, common);
			this.validateNameCodeUnique(sqlDesignCompound);
			this.validateSqlSyntax(sqlDesignCompound, common);
			
			SqlDesign sqlDesign = sqlDesignCompound.getSqlDesign();
			sqlDesign.setDesignType(SQLDesignType.ADVANCED_SQL);
			sqlDesign.setSystemDate(FunctionCommon.getCurrentTime());
			sqlDesign.setProjectId(common.getWorkingProjectId());
			sqlDesign.setDesignStatus(DesignStatus.UNDER_DESIGN);
			sqlDesign.setCreatedBy(common.getCreatedBy());
			sqlDesignRepository.register(sqlDesign);
			
			if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignInputs())){
				for(SqlDesignInput sqlDesignInput:sqlDesignCompound.getSqlDesignInputs()){
					sqlDesignInput.setSqlDesignId(sqlDesign.getSqlDesignId());
				}
				sqlDesignInputRepository.registerAll(this.fillInputId(sqlDesignCompound.getSqlDesignInputs(),sqlDesignCompound.getSqlDesignInputs()));
			}
			
			if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignOutputs())){
				for(SqlDesignOutput sqlDesignOutput:sqlDesignCompound.getSqlDesignOutputs()){
					sqlDesignOutput.setSqlDesignId(sqlDesign.getSqlDesignId());
				}
				sqlDesignOutputRepository.registerAll(this.fillOutputId(sqlDesignCompound.getSqlDesignOutputs(),sqlDesignCompound.getSqlDesignOutputs()));
			}
		}
		
	}

	protected SqlDesignInput[] fillInputId(SqlDesignInput[] newSqlDesignInputs,SqlDesignInput[] sqlDesignInputs) {
		Long start = sqlDesignInputRepository.preserveIds(newSqlDesignInputs.length)-newSqlDesignInputs.length;
		SqlDesignInput sqlDesignInput=null;
		for(int i=0;i<newSqlDesignInputs.length;i++){
			sqlDesignInput = newSqlDesignInputs[i];
			sqlDesignInput.setSqlDesignInputId(start++);
			if(StringUtils.isNotBlank(sqlDesignInput.getGroupId())){
				for(int j=0;j<sqlDesignInputs.length;j++){
					if(DataTypeUtils.equals(sqlDesignInputs[j].getGroupIndex(),sqlDesignInput.getGroupId())){
						sqlDesignInput.setSqlDesignInputParentId(sqlDesignInputs[j].getSqlDesignInputId());
						break;
					}
				}
			}
		}
		return newSqlDesignInputs;
	}
	
	protected SqlDesignOutput[] fillOutputId(SqlDesignOutput[] newSqlDesignOutputs,SqlDesignOutput[] sqlDesignOutputs) {
		Long start = sqlDesignOutputRepository.preserveIds(newSqlDesignOutputs.length)-newSqlDesignOutputs.length;
		SqlDesignOutput sqlDesignOutput=null;
		for(int i=0;i<newSqlDesignOutputs.length;i++){
			sqlDesignOutput = newSqlDesignOutputs[i];
			sqlDesignOutput.setSqlDesignOutputId(start++);
			if(StringUtils.isNotBlank(sqlDesignOutput.getGroupId())){
				for(int j=0;j<sqlDesignOutputs.length;j++){
					if(DataTypeUtils.equals(sqlDesignOutputs[j].getGroupIndex(),sqlDesignOutput.getGroupId())){
						sqlDesignOutput.setSqlDesignOutputParentId(sqlDesignOutputs[j].getSqlDesignOutputId());
						break;
					}
				}
			}
		}
		return newSqlDesignOutputs;
	}

	@Override
	public boolean delete(SqlDesignCompound sqlDesignCompound, CommonModel common) {
		Boolean result = true;
		if(sqlDesignCompound!=null) {
			SqlDesign sqlDesign = this.validateExistence(sqlDesignCompound, common);
			this.validateConsistency(sqlDesignCompound, common);
			sqlDesignRepository.deleteSqlDesignChildren(sqlDesign.getSqlDesignId());
			sqlDesignCompound.getSqlDesign().setSystemDate(FunctionCommon.getCurrentTime());
			if(!sqlDesignRepository.delete(sqlDesignCompound.getSqlDesign())){
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
			}
			deleteAffected(sqlDesign,common);
		} else {
			result = false;
		}
		return result;
	}

	@Override
	public void modifySqlDesign(SqlDesignCompound sqlDesignCompound, CommonModel common) {
		if(sqlDesignCompound!=null) {
			this.validateExistence(sqlDesignCompound, common);
			this.validateNameCodeUnique(sqlDesignCompound);
			this.validateSqlSyntax(sqlDesignCompound, common);
			//ProblemList problemList = null;
			this.findAllDeletionAffection(sqlDesignCompound, common);
//			SqlDesignCompound oldSqlDesignCompound = this.findCompoundById(sqlDesignCompound.getSqlDesign().getSqlDesignId());
			
			Long sqlDesignId = sqlDesignCompound.getSqlDesign().getSqlDesignId();
			SqlDesign sqlDesign = sqlDesignCompound.getSqlDesign();
			List<Long> exceptionGroup = new ArrayList<Long>();
			List<Long> nestedExceptionGroup = new ArrayList<Long>();
			
			if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignTables())){
				List<SqlDesignTable> modificationListTable = new ArrayList<SqlDesignTable>();
				List<SqlDesignTableItem> registrationListTableItem = new ArrayList<SqlDesignTableItem>();
				List<SqlDesignTableItem> modificationListTableItem = new ArrayList<SqlDesignTableItem>();
				for(SqlDesignTable sqlDesignTable:sqlDesignCompound.getSqlDesignTables()){
					sqlDesignTable.setSqlDesignId(sqlDesignId);
					if(!(sqlDesignTable.getSqlDesignTableId()==null || sqlDesignTable.getSqlDesignTableId() <= 0)){
						exceptionGroup.add(sqlDesignTable.getSqlDesignTableId());
						modificationListTable.add(sqlDesignTable);
						if(sqlDesignTable.getJoinTableId()!=null && !StringUtils.equals(sqlDesignTable.getJoinType(), JoinType.CROSS_JOIN.getCode()) ){
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
						if(sqlDesignTable.getJoinTableId()!=null && !StringUtils.equals(sqlDesignTable.getJoinType(), JoinType.CROSS_JOIN.getCode())){
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
			
			if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignConditions())) {
				List<SqlDesignCondition> registrationList = new ArrayList<SqlDesignCondition>();
				List<SqlDesignCondition> modificationList = new ArrayList<SqlDesignCondition>();
				for(SqlDesignCondition sqlDesignCondition:sqlDesignCompound.getSqlDesignConditions()){
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
			
			if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignGroupBys())){
				List<SqlDesignGroupBy> registrationList = new ArrayList<SqlDesignGroupBy>();
				List<SqlDesignGroupBy> modificationList = new ArrayList<SqlDesignGroupBy>();
				for(SqlDesignGroupBy sqlDesignGroupBy:sqlDesignCompound.getSqlDesignGroupBys()){
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
			
			if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignHavings())){
				List<SqlDesignHaving> registrationList = new ArrayList<SqlDesignHaving>();
				List<SqlDesignHaving> modificationList = new ArrayList<SqlDesignHaving>();
				for(SqlDesignHaving sqlDesignHaving:sqlDesignCompound.getSqlDesignHavings()){
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
			
			
			if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignResults())){
				List<SqlDesignResult> registrationList = new ArrayList<SqlDesignResult>();
				List<SqlDesignResult> modificationList = new ArrayList<SqlDesignResult>();
				for(SqlDesignResult sqlDesignResult:sqlDesignCompound.getSqlDesignResults()){
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
			
			if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignOrders())) {
				List<SqlDesignOrder> registrationList = new ArrayList<SqlDesignOrder>();
				List<SqlDesignOrder> modificationList = new ArrayList<SqlDesignOrder>();
				for(SqlDesignOrder sqlDesignOrder:sqlDesignCompound.getSqlDesignOrders()){
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
			
			if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignValues())){
				List<SqlDesignValue> registrationList = new ArrayList<SqlDesignValue>();
				List<SqlDesignValue> modificationList = new ArrayList<SqlDesignValue>();
				for(SqlDesignValue sqlDesignValue:sqlDesignCompound.getSqlDesignValues()){
					sqlDesignValue.setSqlDesignId(sqlDesignId);
					if(!(sqlDesignValue.getSqlDesignValueId()==null || sqlDesignValue.getSqlDesignValueId()<=0)){
						modificationList.add(sqlDesignValue);
						exceptionGroup.add(sqlDesignValue.getSqlDesignValueId());
					} else {
						registrationList.add(sqlDesignValue);
					}
				}
				sqlDesignValueRepository.deleteByExceptionalGroup(sqlDesignId, exceptionGroup);
				if(modificationList.size()>0){
					sqlDesignValueRepository.modifyAll(modificationList.toArray(new SqlDesignValue[modificationList.size()]));
				}
				if(registrationList.size()>0){
					sqlDesignValueRepository.registerAll(registrationList.toArray(new SqlDesignValue[registrationList.size()]));
				}
			} else {
				sqlDesignValueRepository.deleteByExceptionalGroup(sqlDesignId, exceptionGroup);
			}
			exceptionGroup.clear();
			
			if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignInputs())){
				List<SqlDesignInput> registrationList = new ArrayList<SqlDesignInput>();
				List<SqlDesignInput> modificationList = new ArrayList<SqlDesignInput>();
				for(SqlDesignInput sqlDesignInput:sqlDesignCompound.getSqlDesignInputs()){
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
					sqlDesignInputRepository.registerAll(this.fillInputId(registrationList.toArray(new SqlDesignInput[registrationList.size()]),sqlDesignCompound.getSqlDesignInputs()));
				}
			} else {
				sqlDesignInputRepository.deleteByExceptionalGroup(sqlDesignId, exceptionGroup);
			}
			exceptionGroup.clear();
			
			if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignOutputs())){
				List<SqlDesignOutput> registrationList = new ArrayList<SqlDesignOutput>();
				List<SqlDesignOutput> modificationList = new ArrayList<SqlDesignOutput>();
				for(SqlDesignOutput sqlDesignOutput:sqlDesignCompound.getSqlDesignOutputs()){
					sqlDesignOutput.setSqlDesignId(sqlDesignId);
					if(!(sqlDesignOutput.getSqlDesignOutputId()==null || sqlDesignOutput.getSqlDesignOutputId()<=0)){
						modificationList.add(sqlDesignOutput);
						exceptionGroup.add(sqlDesignOutput.getSqlDesignOutputId());
					} else {
						registrationList.add(sqlDesignOutput);
					}
				}
				sqlDesignOutputRepository.deleteByExceptionalGroup(sqlDesignId, exceptionGroup);
				if(modificationList.size()>0){
					sqlDesignOutputRepository.modifyAll(modificationList.toArray(new SqlDesignOutput[modificationList.size()]));
				}
				if(registrationList.size()>0){
					sqlDesignOutputRepository.registerAll(this.fillOutputId(registrationList.toArray(new SqlDesignOutput[registrationList.size()]),sqlDesignCompound.getSqlDesignOutputs()));
				}
			} else {
				sqlDesignOutputRepository.deleteByExceptionalGroup(sqlDesignId, exceptionGroup);
			}
			
			exceptionGroup.clear();
			if(sqlDesignCompound.getSqlDesignTable()!=null){
				SqlDesignTable sqlDesignTable = sqlDesignCompound.getSqlDesignTable();
				sqlDesignTable.setSqlDesignId(sqlDesign.getSqlDesignId());
				sqlDesignTableRepository.modify(sqlDesignTable);
			}
			sqlDesign.setSystemDate(FunctionCommon.getCurrentTime());
			if(!sqlDesignRepository.modify(sqlDesign)){
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
			}
			modifyAffected(sqlDesign,common);
		}
	}

	@Override
	public void modifyAdvancedSqlDesign(SqlDesignCompound sqlDesignCompound, CommonModel common) {
		if(sqlDesignCompound!=null) {
			SqlDesign sqlDesign = sqlDesignCompound.getSqlDesign();
			this.validateExistence(sqlDesignCompound, common);
			this.validateNameCodeUnique(sqlDesignCompound);
			this.validateSqlSyntax(sqlDesignCompound, common);
			
			Long sqlDesignId = sqlDesign.getSqlDesignId();
			List<Long> exceptionGroup = new ArrayList<Long>();
			//ProblemList problemList = null;
			this.findAllDeletionAffection(sqlDesignCompound, common);
//			SqlDesignCompound oldSqlDesignCompound = this.findCompoundById(sqlDesignCompound.getSqlDesign().getSqlDesignId());
			
			if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignInputs())){
				List<SqlDesignInput> registrationList = new ArrayList<SqlDesignInput>();
				List<SqlDesignInput> modificationList = new ArrayList<SqlDesignInput>();
				for(SqlDesignInput sqlDesignInput:sqlDesignCompound.getSqlDesignInputs()){
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
					sqlDesignInputRepository.registerAll(this.fillInputId(registrationList.toArray(new SqlDesignInput[registrationList.size()]),sqlDesignCompound.getSqlDesignInputs()));
				}
			} else {
				sqlDesignInputRepository.deleteByExceptionalGroup(sqlDesignId, exceptionGroup);
			}
			exceptionGroup.clear();
			
			if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignOutputs())){
				List<SqlDesignOutput> registrationList = new ArrayList<SqlDesignOutput>();
				List<SqlDesignOutput> modificationList = new ArrayList<SqlDesignOutput>();
				for(SqlDesignOutput sqlDesignOutput:sqlDesignCompound.getSqlDesignOutputs()){
					sqlDesignOutput.setSqlDesignId(sqlDesignId);
					if(!(sqlDesignOutput.getSqlDesignOutputId()==null || sqlDesignOutput.getSqlDesignOutputId()<=0)){
						modificationList.add(sqlDesignOutput);
						exceptionGroup.add(sqlDesignOutput.getSqlDesignOutputId());
					} else {
						registrationList.add(sqlDesignOutput);
					}
				}
				sqlDesignOutputRepository.deleteByExceptionalGroup(sqlDesignId, exceptionGroup);
				if(modificationList.size()>0){
					sqlDesignOutputRepository.modifyAll(modificationList.toArray(new SqlDesignOutput[modificationList.size()]));
				}
				if(registrationList.size()>0){
					sqlDesignOutputRepository.registerAll(this.fillOutputId(registrationList.toArray(new SqlDesignOutput[registrationList.size()]),sqlDesignCompound.getSqlDesignOutputs()));
				}
			} else {
				sqlDesignOutputRepository.deleteByExceptionalGroup(sqlDesignId, exceptionGroup);
			}
			exceptionGroup.clear();
			
			sqlDesign.setSystemDate(FunctionCommon.getCurrentTime());
			sqlDesign.setUpdatedBy(common.getUpdatedBy());
			if(BooleanUtils.isTrue(sqlDesign.getIsConversion())){
				sqlDesign.setDesignType(SQLDesignType.ADVANCED_SQL);
				sqlDesignRepository.deleteSqlDesignChildrenExceptIo(sqlDesignId);
				
				if(!sqlDesignRepository.modifyWithDesignType(sqlDesign)){
					throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
				}
			} else {
				if(!sqlDesignRepository.modify(sqlDesign)){
					throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
				}
				modifyAffected(sqlDesign, common);
			}
		}
	}

	@Override
	public List<SqlDesignFunctionGroup> findAllFunctionCode(String dialect) {
		
		return sqlDesignFunctionRepository.findAllByGroup(dialect,LocaleUtils.getLanguage(LocaleUtils.getRequestLocale()));
	}

	protected SqlDesign validateExistence(SqlDesignCompound sqlDesignCompound, CommonModel common) {
		return this.validateExistence(sqlDesignCompound,true, common);
	}
	protected SqlDesign validateExistence(SqlDesignCompound sqlDesignCompound,Boolean isNeedCheckStatus, CommonModel common) {
		SqlDesign sqlDesign = sqlDesignRepository.findOneById(sqlDesignCompound.getSqlDesign().getSqlDesignId());
		SqlDesign sqlDesignOld = sqlDesignCompound.getSqlDesign();
		if(sqlDesign==null){
			if(SQLDesignType.VIEW == sqlDesignOld.getDesignType()){
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037,	MessageUtils.getMessage(ViewDesignMessageConst.SC_VIEWDESIGN_VIEW_DESIGN)));
			}else{
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037,	MessageUtils.getMessage(SqlDesignMessageConst.SC_SQLDESIGN_SQL_DESIGN)));
			}
		}
		if(isNeedCheckStatus){
			this.validateDesignStatus(sqlDesign, common);
		}
		return sqlDesign;
	}
	protected void validateSqlSyntax(SqlDesignCompound sqlDesignCompound, CommonModel common) {
		//sqlBuilderService.initData(common.getWorkingProjectId(), common.getWorkingLanguageId(), common.getCreatedBy());
		Boolean isSqlValid = sqlBuilderService.validate(sqlDesignCompound);
		if(!isSqlValid){
			throw new BusinessException(ResultMessages.error().add(SqlDesignMessageConst.SC_SQLDESIGN_SQL_DESIGN));
		}
	}

	protected void validateNameCodeUnique(SqlDesignCompound sqlDesignCompound) {
		Integer flag = sqlDesignRepository.getExistNameCode(sqlDesignCompound.getSqlDesign());
		ResultMessages resultMessages = ResultMessages.error(); 
		switch(flag){
		case 1:
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(SqlDesignMessageConst.SC_SQLDESIGN_SQL_DESIGN_NAME));
			break;
		case 2:
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(SqlDesignMessageConst.SC_SQLDESIGN_SQL_DESIGN_CODE));
			break;
		case 3:
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(SqlDesignMessageConst.SC_SQLDESIGN_SQL_DESIGN_NAME));
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(SqlDesignMessageConst.SC_SQLDESIGN_SQL_DESIGN_CODE));
			break;
		}
		if(resultMessages.isNotEmpty()){
			throw new BusinessException(resultMessages);
		}
	}
	
	protected void validateConsistency(SqlDesignCompound sqlDesignCompound, CommonModel common) {
		List<ConsistencyValidationModel> consistencyValidationModels = sqlDesignRepository.getReferenceById(sqlDesignCompound.getSqlDesign().getSqlDesignId(),common.getWorkingProjectId());
		ResultMessages resultMessages = ResultMessages.error(); 
		
		for(ConsistencyValidationModel consistencyValidationModel:consistencyValidationModels) {
			if(consistencyValidationModel.getGroupType()==1){
				resultMessages.add(SqlDesignMessageConst.ERR_SQLDESIGN_0006,
						consistencyValidationModel.getItemName(),
						consistencyValidationModel.getGroupName());
			}
			if(consistencyValidationModel.getGroupType()==2){
				resultMessages.add(SqlDesignMessageConst.ERR_SQLDESIGN_0007,
						consistencyValidationModel.getItemName(),
						consistencyValidationModel.getGroupName());
			}
		}
		if(resultMessages.isNotEmpty()){
			throw new BusinessException(resultMessages);
		}
	}
	
	protected void validateDesignStatus(SqlDesign sqlDesign, CommonModel common) {
		this.validateDesignStatus(sqlDesign,false, common);
	}
	
	protected void validateDesignStatus(SqlDesign sqlDesign,Boolean isRegistration, CommonModel common) {
		if(sqlDesign!=null){
			common.setProjectId(sqlDesign.getProjectId());
			ResultMessages resultMessages = ResultMessages.error(); 
			
			if(sqlDesign.getModuleId()!=null){
				//moduleService.initData(getWorkingProjectId(), getAccountId());
				moduleService.validateModule(sqlDesign.getModuleId(), common);
			} else {
				//projectService.initData(getWorkingProjectId(), getAccountId());
				projectService.validateProject(common);
			}
			if(!isRegistration){
				if(sqlDesign.getDesignStatus()==DesignStatus.FIXED){
					String messageCode = "";
					if(sqlDesign.getDesignType()==SQLDesignType.VIEW||sqlDesign.getDesignType()==SQLDesignType.ADVANCED_VIEW){
						messageCode = ViewDesignMessageConst.SC_VIEWDESIGN_VIEW_DESIGN;
					} else {
						messageCode = SqlDesignMessageConst.SC_SQLDESIGN_SQL_DESIGN;
					}

					resultMessages.add(
							CommonMessageConst.ERR_SYS_0111,
							StringUtils.lowerCase(MessageUtils.getMessage(messageCode)),
							sqlDesign.getSqlDesignName());
				}
			}
			if(resultMessages.isNotEmpty()){
				throw new BusinessException(resultMessages);
			}
		}
	}
	
	@Override
	public List<Map<String , String>> getLstTableDesignDetails(String[] tblDesignIds, Long projectId) {
		List<Map<String , String>> listMap = new ArrayList<Map<String , String>>();
		List<TableDesign> lstTableDesign = tableDesignRepository.getLstTableDesignDetails(tblDesignIds, projectId);

		Map<String , String> mapTbl = new HashMap<String , String>();
		Map<String , String> mapTblAlias = new HashMap<String , String>();
		Map<String , String> mapClm = new HashMap<String , String>();
		Map<String , String> mapClmDataType = new HashMap<String , String>();
		
		int count = 0;
		
		if(lstTableDesign != null && !lstTableDesign.isEmpty()) {
			for(TableDesign item : lstTableDesign) {
				// Processing convert to Map table_id - tablecode
				mapTbl.put(item.getTableDesignId().toString(), item.getTableCode());
				
				//put table alias
				count++;
				mapTblAlias.put(item.getTableDesignId().toString(), "t"+Integer.valueOf(count));
				// Processing convert to Map Column Id - Column Code
				String[] columnText = item.getColumnsText().split(",");
				for(String s : columnText) {
					String [] itemClm = s.split(":");
					mapClm.put(itemClm[0], itemClm[2]);
					mapClmDataType.put(itemClm[0], itemClm[3]);
				}
			}

			listMap.add(mapTbl);
			listMap.add(mapClm);
			listMap.add(mapTblAlias);
			listMap.add(mapClmDataType);
		}

		return listMap;
	}

	@Override
	public void modifyDesignStatus(SqlDesignCompound sqlDesignCompound, CommonModel common) {
		if(sqlDesignCompound!=null){
			Timestamp currentTime = FunctionCommon.getCurrentTime();
			SqlDesign sqlDesign = this.validateExistence(sqlDesignCompound,false, common);
			Timestamp updateDateOld = sqlDesignCompound.getSqlDesign().getUpdatedDate();
			sqlDesign.setUpdatedDate(updateDateOld);
			sqlDesign.setSystemDate(currentTime);
			if(DbDomainConst.DesignStatus.UNDER_DESIGN.equals(sqlDesign.getDesignStatus())) {
				sqlDesign.setDesignStatus(DbDomainConst.DesignStatus.FIXED);
			} else {
				sqlDesign.setDesignStatus(DbDomainConst.DesignStatus.UNDER_DESIGN);
			}
			if(!sqlDesignRepository.modifyDesignStatus(sqlDesign)) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0005)));
			}
		}
	}

	@Override
	public void findAllDeletionAffection(SqlDesignCompound sqlDesignCompound, CommonModel common) {
		if(sqlDesignCompound != null){
			sqlDesignCompound.setAffectedBusinessDesigns(businessDesignRepository.findAllBusinessLogicsBySqlDesignId(sqlDesignCompound.getSqlDesign().getSqlDesignId()));
			sqlDesignCompound.setAffectedScreenDesigns(screenDesignRepository.findAllScreenDesignsBySqlDesignId(sqlDesignCompound.getSqlDesign().getSqlDesignId(),common.getWorkingLanguageId()));
			sqlDesignCompound.setAffectedDomainDesigns(domainDesignRepository.findAllDomainDesignsBySqlDesignId(sqlDesignCompound.getSqlDesign().getSqlDesignId(),common.getWorkingProjectId()));
			sqlDesignCompound.setAffectedTableDesigns(tableDesignRepository.findAllTableDesignsBySqlDesignId(sqlDesignCompound.getSqlDesign().getSqlDesignId()));
		}
	}

	@Override
	public SqlDesignResult[] findAllBySqlDesignId(Long sqlDesignId) {
		// TODO Auto-generated method stub
		return sqlDesignResultRepository.findAllBySqlDesignId(sqlDesignId);
	}

	@Override
	public SqlDesign findById(Long sqlDesignId) {
		// TODO Auto-generated method stub
		SqlDesign sqlDesign = sqlDesignRepository.findOneById(sqlDesignId);
		
		return sqlDesign;
	}

	@Override
	public SqlDesignResult[] findAllSqlDesignResults(SqlDesignCompound sqlDesignCompound) {
		SqlDesignResult[] sqlDesignResults = null;
		if(!(sqlDesignCompound==null || sqlDesignCompound.getSqlDesign()==null)){
			List<SqlDesignResult> newSqlDesignResults = new ArrayList<SqlDesignResult>();
			List<SqlDesignResult> oldSqlDesignResults = new ArrayList<SqlDesignResult>();
			List<Long> ids = new ArrayList<Long>();
			if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignResults())){
				for(SqlDesignResult sqlDesignResult : sqlDesignCompound.getSqlDesignResults()){
					if(!ids.contains(sqlDesignResult.getTableId()) && sqlDesignResult.getTableType() == 0) {
						ids.add(sqlDesignResult.getTableId());
					}
					if(sqlDesignResult.getResultId()==0){
						newSqlDesignResults.add(sqlDesignResult);
						
					} else {
						oldSqlDesignResults.add(sqlDesignResult);
					}
				}
				List<TableDesignDetails> tableDesignDetails = tableDesignDetailRepository.findByListIdWithBaseType(ids);
				if(CollectionUtils.isNotEmpty(tableDesignDetails)){
					for(SqlDesignResult sqlDesignResult : newSqlDesignResults){
						Boolean flag = false;
						for(TableDesignDetails tableDesignDetail:tableDesignDetails){
							if(DataTypeUtils.equals(sqlDesignResult.getColumnId(), tableDesignDetail.getColumnId())){
								sqlDesignResult.setDataType(tableDesignDetail.getBaseType());
								flag = true;
								break;
							} 
						}
						if(!flag){
							sqlDesignResult.setDataType(null);
						}
					}
					for(TableDesignDetails tableDesignDetail:tableDesignDetails){
						Boolean flag = false;
						SqlDesignResult newSqlDesignResult = null;
						for(SqlDesignResult sqlDesignResult : sqlDesignCompound.getSqlDesignResults()){
							if(DataTypeUtils.equals(sqlDesignResult.getColumnId(), tableDesignDetail.getColumnId())){
								flag = true;
								break;
							}
						}
						if(!flag){
							newSqlDesignResult = new SqlDesignResult();
							newSqlDesignResult.setTableId(tableDesignDetail.getTableDesignId());
							newSqlDesignResult.setTableCode(tableDesignDetail.getTableDesignCode());
							newSqlDesignResult.setTableName(tableDesignDetail.getTableDesignName());
							newSqlDesignResult.setColumnId(tableDesignDetail.getColumnId());
							newSqlDesignResult.setColumnName(tableDesignDetail.getName());
							newSqlDesignResult.setColumnCode(tableDesignDetail.getCode());
							newSqlDesignResult.setDataType(tableDesignDetail.getBaseType());
							newSqlDesignResult.setTableType(tableDesignDetail.getTableDesignType());
							newSqlDesignResults.add(newSqlDesignResult);
						}
					}
				}
			}
			if(CollectionUtils.isNotEmpty(oldSqlDesignResults)){
				sqlDesignResults = sqlDesignResultRepository.findAllByIds(oldSqlDesignResults);
			}
			
			if(CollectionUtils.isNotEmpty(newSqlDesignResults)){
				sqlDesignResults = ArrayUtils.addAll(sqlDesignResults, newSqlDesignResults.toArray(new SqlDesignResult[newSqlDesignResults.size()]));
			}
		} 
		return sqlDesignResults;
	}
	
	private void modifyAffected(SqlDesign sqlDesign,CommonModel common) {
		ImpactChangeJobControl jobControl = new ImpactChangeJobControl();
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		jobControl.setJobAppCd(DbDomainConst.ImpactChangeDesign.JOB_APP_CD);
		jobControl.setProjectId(String.valueOf(common.getWorkingProjectId()));
		jobControl.setModuleId(String.valueOf(sqlDesign.getModuleId()));
		jobControl.setCreatedBy(String.valueOf(common.getUpdatedBy()));
		jobControl.setImpactType(String.valueOf(DbDomainConst.FromResourceType.SQL_DESIGN));
		jobControl.setImpactId(String.valueOf(sqlDesign.getSqlDesignId()));
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
    
    private void deleteAffected(SqlDesign sqlDesign,CommonModel common) {
		ImpactChangeJobControl jobControl = new ImpactChangeJobControl();
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		jobControl.setJobAppCd(DbDomainConst.ImpactChangeDesign.JOB_APP_CD);
		jobControl.setProjectId(String.valueOf(sqlDesign.getProjectId()));
		jobControl.setModuleId(null);
		jobControl.setCreatedBy(String.valueOf(common.getCreatedBy()));
		jobControl.setImpactType(String.valueOf(DbDomainConst.FromResourceType.SQL_DESIGN));
		jobControl.setImpactId(String.valueOf(sqlDesign.getSqlDesignId()));
		jobControl.setCurAppStatus(GenerateAppStatus.INIT);
		jobControl.setAddDateTime(currentTime);
		jobControl.setUpdDateTime(currentTime);
		jobControl.setJobArgNm6(ImpactChangeDesign.CASE_DELETE);
		jobControl.setJobArgNm7(sqlDesign.getSqlDesignCode());
		
		//check the same business type job.
		Long count = impactChangeRepository.countImpactChangeByType(jobControl);
		if(count == 0)
			impactChangeRepository.registerImpactChange(jobControl);
		else
			impactChangeRepository.modifyImpactChange(jobControl);
    }

}
