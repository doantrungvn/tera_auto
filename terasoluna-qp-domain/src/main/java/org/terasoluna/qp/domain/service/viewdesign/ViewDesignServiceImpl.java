package org.terasoluna.qp.domain.service.viewdesign;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

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
import org.terasoluna.qp.app.common.constants.DbDomainConst.DesignStatus;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.ViewDesignMessageConst;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.model.SqlDesignCondition;
import org.terasoluna.qp.domain.model.SqlDesignGroupBy;
import org.terasoluna.qp.domain.model.SqlDesignHaving;
import org.terasoluna.qp.domain.model.SqlDesignInput;
import org.terasoluna.qp.domain.model.SqlDesignOrder;
import org.terasoluna.qp.domain.model.SqlDesignOutput;
import org.terasoluna.qp.domain.model.SqlDesignResult;
import org.terasoluna.qp.domain.model.SqlDesignTable;
import org.terasoluna.qp.domain.model.SqlDesignTableItem;
import org.terasoluna.qp.domain.model.SqlDesignValue;
import org.terasoluna.qp.domain.repository.project.ProjectRepository;
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
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst.JoinType;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeConst.SQLDesignType;
import org.terasoluna.qp.domain.service.sqldesign.SqlDesignCompound;
import org.terasoluna.qp.domain.service.sqldesign.SqlDesignServiceImpl;

@Service
@Transactional
public class ViewDesignServiceImpl extends SqlDesignServiceImpl implements ViewDesignService {

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
	TableDesignRepository tableDesignRepository;
	
	@Inject
	ProjectRepository projectRepository;
	
	@Override
	public Page<SqlDesign> findPageByCriteria(SqlDesignSearchCriteria criteria,Pageable pageable, CommonModel common) {
		criteria.setProjectId(common.getWorkingProjectId());
		long totalCount = sqlDesignRepository.countViewByCriteria(criteria);

		List<SqlDesign> result;
		if (0 < totalCount) {
			result = sqlDesignRepository.findViewByCriteria(criteria, pageable);
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
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(ViewDesignMessageConst.SC_VIEWDESIGN_VIEW_DESIGN)));
		}
		return sqlDesign;
	}
	@Override
	public void registerViewDesign(SqlDesignCompound sqlDesignCompound, CommonModel common) {
		if(sqlDesignCompound!=null) {
			this.validateDesignStatus(sqlDesignCompound.getSqlDesign(), true, common);
			this.validateNameCodeUnique(sqlDesignCompound);
			this.validateSqlSyntax(sqlDesignCompound, common);
			
			SqlDesign sqlDesign = sqlDesignCompound.getSqlDesign();
			sqlDesign.setDesignType(SQLDesignType.VIEW);
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
	public void registerAdvancedViewDesign(SqlDesignCompound sqlDesignCompound, CommonModel common) {
		if(sqlDesignCompound!=null) {
			this.validateDesignStatus(sqlDesignCompound.getSqlDesign(), true, common);
			this.validateNameCodeUnique(sqlDesignCompound);
			this.validateSqlSyntax(sqlDesignCompound, common);
			
			SqlDesign sqlDesign = sqlDesignCompound.getSqlDesign();
			sqlDesign.setDesignType(SQLDesignType.ADVANCED_VIEW);
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

	@Override
	public void modifyViewDesign(SqlDesignCompound sqlDesignCompound, CommonModel common) {
		if(sqlDesignCompound!=null) {
			this.validateExistence(sqlDesignCompound, common);
			this.validateNameCodeUnique(sqlDesignCompound);
			this.validateSqlSyntax(sqlDesignCompound, common);
			
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
			
			sqlDesign.setSystemDate(FunctionCommon.getCurrentTime());
			if(!sqlDesignRepository.modify(sqlDesign)){
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
			}
		}
	}

	@Override
	public void modifyAdvancedViewDesign(SqlDesignCompound sqlDesignCompound, CommonModel common) {
		if(sqlDesignCompound!=null) {
			this.validateExistence(sqlDesignCompound, common);
			this.validateNameCodeUnique(sqlDesignCompound);
			this.validateSqlSyntax(sqlDesignCompound, common);
			
			SqlDesign sqlDesign = sqlDesignCompound.getSqlDesign();
			Long sqlDesignId = sqlDesign.getSqlDesignId();
			List<Long> exceptionGroup = new ArrayList<Long>();
			
			
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
				sqlDesign.setDesignType(SQLDesignType.ADVANCED_VIEW);
				sqlDesignRepository.deleteSqlDesignChildrenExceptIo(sqlDesignId);
				
				if(!sqlDesignRepository.modifyWithDesignType(sqlDesign)){
					throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
				}
			} else {
				if(!sqlDesignRepository.modify(sqlDesign)){
					throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
				}
			}
		}
	}
	
	@Override
	protected SqlDesign validateExistence(SqlDesignCompound sqlDesignCompound,Boolean isNeedCheckStatus, CommonModel common) {
		SqlDesign sqlDesign = sqlDesignRepository.findOneById(sqlDesignCompound.getSqlDesign().getSqlDesignId());
		if(sqlDesign==null){
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037,	MessageUtils.getMessage(ViewDesignMessageConst.SC_VIEWDESIGN_VIEW_DESIGN)));
		}
		if(isNeedCheckStatus){
			this.validateDesignStatus(sqlDesign, common);
		}
		return sqlDesign;
	}
	
	@Override
	protected void validateNameCodeUnique(SqlDesignCompound sqlDesignCompound) {
		Integer flag = sqlDesignRepository.getExistNameCodeViewDesign(sqlDesignCompound.getSqlDesign());
		ResultMessages resultMessages = ResultMessages.error(); 
		switch(flag){
		case 1:
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(ViewDesignMessageConst.SC_VIEWDESIGN_VIEW_DESIGN_NAME));
			break;
		case 2:
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(ViewDesignMessageConst.SC_VIEWDESIGN_VIEW_DESIGN_CODE));
			break;
		case 3:
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(ViewDesignMessageConst.SC_VIEWDESIGN_VIEW_DESIGN_NAME));
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(ViewDesignMessageConst.SC_VIEWDESIGN_VIEW_DESIGN_CODE));
			break;
		}
		if(resultMessages.isNotEmpty()){
			throw new BusinessException(resultMessages);
		}
	}
	
	@Override
	protected void validateConsistency(SqlDesignCompound sqlDesignCompound, CommonModel common) {
		
	}
}
