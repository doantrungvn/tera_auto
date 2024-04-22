package org.terasoluna.qp.domain.service.tabledesign;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
import org.terasoluna.qp.app.common.constants.DbDomainConst.DisplayType;
import org.terasoluna.qp.app.common.constants.DbDomainConst.YesNoFlg;
import org.terasoluna.qp.app.common.constants.TerasolunaQPValidationConst;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.DateUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.GenerateUniqueKey;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.ProblemListMessageConst;
import org.terasoluna.qp.app.message.TableDesignMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.Autocomplete;
import org.terasoluna.qp.domain.model.Basetype;
import org.terasoluna.qp.domain.model.BusinessLogic;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.ProblemList;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.model.SqlDesignOutput;
import org.terasoluna.qp.domain.model.SubjectArea;
import org.terasoluna.qp.domain.model.SubjectAreaTableDesign;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.model.TableDesignForeignKey;
import org.terasoluna.qp.domain.model.TableDesignKey;
import org.terasoluna.qp.domain.model.TableDesignKeyItem;
import org.terasoluna.qp.domain.model.UserDefineCodelist;
import org.terasoluna.qp.domain.model.UserDefineCodelistDetails;
import org.terasoluna.qp.domain.model.ValidationRule;
import org.terasoluna.qp.domain.repository.businessdesign.BusinessDesignRepository;
import org.terasoluna.qp.domain.repository.domaindatatype.DomainDatatypeRepository;
import org.terasoluna.qp.domain.repository.domaindesign.DomainDesignRepository;
import org.terasoluna.qp.domain.repository.domaindesign.ResourceRepository;
import org.terasoluna.qp.domain.repository.module.ModuleRepository;
import org.terasoluna.qp.domain.repository.problemlist.ProblemListRepository;
import org.terasoluna.qp.domain.repository.project.ProjectRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignConditionRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignOutputRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignResultRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignTableItemsRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignTableRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignValueRepository;
import org.terasoluna.qp.domain.repository.subjectarea.SubjectAreaRepository;
import org.terasoluna.qp.domain.repository.subjectarea.SubjectAreaTableRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignCriteria;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignDetailRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignForeignKeyRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignKeyRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignRepository;
import org.terasoluna.qp.domain.repository.tabledesign.UserDefineCodelistRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignHelper;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignShareService;
import org.terasoluna.qp.domain.service.common.AutocompleteInput;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeConst;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeUtil;
import org.terasoluna.qp.domain.service.domaindesign.DomainDesignService;
import org.terasoluna.qp.domain.service.graphicdatabasedesign.TableDesignUtil;
import org.terasoluna.qp.domain.service.project.ProjectService;

@Service
@Transactional
public class TableDesignServiceImpl implements TableDesignService {
	
	private static final String CHAR_SPLIT = ";";
	
	private static final String INVERSE_BULLET_SYSBOL = "◘";
	
	private static final String REPLACEMENT_CHARACTER_SYSBOL = "�";
	
	private Map<Integer, String> mapItemtype;
	
	@Inject
	TableDesignRepository tableDesignRepository;

	@Inject
	TableDesignDetailRepository tableDesignDetailRepository;

	@Inject
	SubjectAreaRepository subjectAreaRepository;

	@Inject
	SubjectAreaTableRepository subjectAreaTableRepository;

	@Inject
	TableDesignKeyRepository tableDesignKeyRepository;

	@Inject
	SqlDesignRepository sqlDesignRepository;
	
	@Inject
	TableDesignForeignKeyRepository tableDesignForeignKeyRepository;
	
	@Inject
	DomainDesignRepository domainDesignRepository;
	
	@Inject
	DomainDesignService domainDesignService;
	
	@Inject
	UserDefineCodelistRepository userDefineCodelistRepository;
	
	@Inject
	DomainDatatypeRepository domainDatatypeRepository;
	
	@Inject
	ProjectRepository projectRepository;
	
	@Inject
	ModuleRepository moduleRepository;
	
	@Inject
	BusinessDesignRepository businessDesignRepository;
	
	@Inject
	ProblemListRepository problemListRepository;
	
	@Inject
	SqlDesignConditionRepository sqlDesignConditionRepository;
	
	@Inject
	SqlDesignResultRepository sqlDesignResultRepository;
	
	@Inject
	SqlDesignTableRepository sqlDesignTableRepository;
	
	@Inject
	SqlDesignTableItemsRepository sqlDesignTableItemsRepository;
	
	@Inject
	SqlDesignValueRepository sqlDesignValueRepository;
	
	@Inject
	ProjectService projectService;
	
	@Inject
	ResourceRepository resourceRepository; 
	
	@Inject
	DomainDesignRepository domainRepository;
	
	@Inject
	BusinessDesignShareService businessDesignShareService;
	
	@Inject
	SystemService systemService;
	
	@Inject 
	SqlDesignOutputRepository sqlDesignOutputRepository;
	
	@Inject
	@Named("CL_QP_ITEMTYPE")
	SimpleMapCodeList itemTypeCodeList;
	
	private Integer maxLengOfCode;

	@Override
	public TableDesign findOneById(Long tableDesignId) {
		return tableDesignRepository.findOneById(tableDesignId);
	}

	@Override
	public Page<TableDesign> findPageByCriteria(TableDesignCriteria criteria,
			Pageable pageable) {

		// Get count number Table form TABLE_DESIGN
		Long totalCount = tableDesignRepository.countByCriteria(criteria);

		// Declare list contain TABLE_DESIGN
		List<TableDesign> articles;

		// If number table more than zero, fill data to list
		if (totalCount > 0) {
			articles = tableDesignRepository.findPageByCriteria(criteria,pageable);
		}

		// Else number table equal zero, return list empty.
		else {

			articles = Collections.emptyList();
		}

		Page<TableDesign> page = new PageImpl<TableDesign>(articles, pageable,
				totalCount);

		return page;
	}

	@Override
	public TableDesign getTableDesignForScreenItemSetting(Long tableId) {
		// Get Inform Table
		TableDesign tableDesign = tableDesignRepository.findOneById(tableId);

		if(tableDesign == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(CommonMessageConst.TQP_TABLEDESIGN)));
		}
		
		// Check Unsign Table
		if(DbDomainConst.DesignStatus.FIXED.equals(tableDesign.getDesignStatus())){
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0109, tableDesign.getTableName()));
		}
				
		// Get Information Table Design Details
		List<ValidationRule> listValidationRule = domainDesignService.findAllValidationRule();
		List<TableDesignDetails> tableDesignDetailsDomain = tableDesignDetailRepository.getAllInformationByTableDesign(tableId);
		int numOfFieldPk = 0;
		for (TableDesignDetails td : tableDesignDetailsDomain) {

			if(YesNoFlg.YES.equals(td.isPrimaryKey())) {
				if (YesNoFlg.YES.equals(td.getAutoIncrementFlag())) {
					td.setIsMandatory(YesNoFlg.NO);
				}
				numOfFieldPk++;
			}

			td.setBinKeyType(Integer.parseInt(td.getKeyType(), 2));
			td.setListItemtype(getOptionItemType(td,listValidationRule));
		}
		
		for (TableDesignDetails td : tableDesignDetailsDomain) {

			if(YesNoFlg.YES.equals(td.isPrimaryKey())) {
				if (YesNoFlg.YES.equals(td.getAutoIncrementFlag())) {
					td.setIsMandatory(YesNoFlg.NO);
				} else if (numOfFieldPk == 1){
					td.setDisplayType(DisplayType.USED);
				}
			}

			if (DisplayType.USED.equals(td.getDisplayType())) {
				td.setUsed(true);
			} else {
				td.setUsed(false);
			}

//			td.setBinKeyType(Integer.parseInt(td.getKeyType(), 2));
//			td.setListItemtype(getOptionItemType(td,listValidationRule));
		}

		Collections.sort(tableDesignDetailsDomain);
		tableDesign.setListTableDesignDetails(tableDesignDetailsDomain);
		tableDesign.setHasCompositeKey(numOfFieldPk);
		return tableDesign;
	}

	@Override
	public TableDesign loadTableDesign(Long tableId, CommonModel commonModel) {
		

		TableDesign table = this.getTableDesignForView(tableId, commonModel);
		
		AutocompleteInput autocompleteInput = null;
		// Get User Define Codelist
		List<UserDefineCodelistDetails> userDefineCodelistDetails = userDefineCodelistRepository.getByTableDesign(tableId);
		StringBuilder userDefineValue = null;
		
		for(TableDesignDetails td : table.getListTableDesignDetails()) {
			td.setBinKeyType(Integer.parseInt(td.getKeyType(), 2));
			this.setValueDataSourceId(td, autocompleteInput, commonModel.getWorkingProjectId());
			userDefineValue = new StringBuilder();
			for (UserDefineCodelistDetails codelistDetails : userDefineCodelistDetails) {
				if(DbDomainConst.DatasourceType.USER_DEFINE.equals(td.getDatasourceType()) && td.getDatasourceId() != null){
					if (td.getDatasourceId().equals(codelistDetails.getCodelistId())){
						td.setSupportOptionFlg(codelistDetails.getSupportOptionFlg());
						userDefineValue.append(codelistDetails.getDefaultFlg());
						userDefineValue.append(REPLACEMENT_CHARACTER_SYSBOL);
						userDefineValue.append(codelistDetails.getCodelistValue());
						userDefineValue.append(REPLACEMENT_CHARACTER_SYSBOL);
						if(codelistDetails.getCodelistName() == null){
							userDefineValue.append(org.apache.commons.lang3.StringUtils.EMPTY);
						}else{
							userDefineValue.append(codelistDetails.getCodelistName());
						}
						userDefineValue.append(INVERSE_BULLET_SYSBOL);
					}
				}
			}
			td.setUserDefineValue(userDefineValue.toString());
		}
		
		// Get information Key
		List<TableDesignKey> tableDesignKeys = tableDesignKeyRepository.findAllByTableDesign(tableId);
		List<TableDesignKeyItem> tableDesignKeyItems = tableDesignKeyRepository.findAllKeyItemByKeyId(tableId);
		
		StringBuilder stringBuilder = null;
		
		for (TableDesignKey tableDesignKey : tableDesignKeys) {
			
			stringBuilder = new StringBuilder();
			
			stringBuilder.append(tableDesignKey.getType());
			stringBuilder.append(REPLACEMENT_CHARACTER_SYSBOL);
			stringBuilder.append(tableDesignKey.getCode());
			stringBuilder.append(REPLACEMENT_CHARACTER_SYSBOL);
			
			for (TableDesignKeyItem keyItem : tableDesignKeyItems) {
				if(keyItem.getTableDesignKeyId().equals(tableDesignKey.getKeyId())){
					for (TableDesignDetails tableDetails : table.getListTableDesignDetails()) {
						if(tableDetails.getColumnId().equals(keyItem.getColumnId())){
							stringBuilder.append(tableDetails.getName());
							stringBuilder.append(REPLACEMENT_CHARACTER_SYSBOL);
						}
					}
				}
			}
			
			tableDesignKey.setStrKeyItems(stringBuilder.toString());
		}
		
		table.setListTableDesignKey(tableDesignKeys);
		
		// Get information Foreign Key
		List<TableDesignForeignKey> tableDesignForeignKeys = tableDesignForeignKeyRepository.findAllByTableDesign(tableId);
		
		for (TableDesignForeignKey tableDesignForeignKey : tableDesignForeignKeys) {
			
			// Set information from table
			TableDesign td = tableDesignRepository.findOneById(tableDesignForeignKey.getToTableId());
			
			tableDesignForeignKey.setToTableName(td.getTableName());
			tableDesignForeignKey.setToTableCode(td.getTableCode());
			
			TableDesignDetails tddFrom = tableDesignDetailRepository.findOne(tableDesignForeignKey.getFromColumnId());
			tableDesignForeignKey.setFromColumnName(tddFrom.getName());
			if(tddFrom.getName().equals(tableDesignForeignKey.getFromColumnName())){
				tableDesignForeignKey.setIndexRow(tddFrom.getItemSeqNo());
			}
			
			TableDesignDetails tdd = tableDesignDetailRepository.findOne(tableDesignForeignKey.getToColumnId());
			tableDesignForeignKey.setToColumnName(tdd.getName());
			
			tableDesignForeignKey.setForeignKeyType(tdd.getGroupBaseTypeId());
			
			for (TableDesignDetails designDetails : table.getListTableDesignDetails()) {
				if(designDetails.getName().equals(tableDesignForeignKey.getFromColumnName())){
					tableDesignForeignKey.setIndexRow(designDetails.getIndexRow());
				}
			}
		}
		table.setTableDesignForeignKeys(tableDesignForeignKeys);
		
		return table;
	}
	
	@Override
	public void modifyTableCommon(TableDesign tableDesign, CommonModel common){
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		
		// Get Inform Table
		this.modifyTableAndSubjectArea(tableDesign, common, currentTime);
	}

	@Override
	public void modifyTable(TableDesign tableDesign, CommonModel common, Project project, boolean isExport) {

		Timestamp currentTime = FunctionCommon.getCurrentTime();
		Long projectId = project.getProjectId();
		Long tableDesignId = tableDesign.getTableDesignId();
		TableDesign tableDesignForModify = modifyTableAndSubjectArea(tableDesign, common, currentTime);

		List<ValidationRule> validationRules = domainDatatypeRepository.findAllValidationRule();
		List<Basetype> listOfBasetype = domainRepository.getAllBasetype(projectId);
		List<TableDesignDetails> tableDesignDetailsBefore = tableDesignDetailRepository.findAllByTableDesign(tableDesign.getTableDesignId());
		List<Long> listIdOfUserdefineCodelist = new ArrayList<Long>();
		
		/*for (Basetype basetype : listOfBasetype) {
			for (TableDesignDetails designDetails : tableDesignDetailsBefore) {
				if(basetype.getBasetypeValue() == designDetails.getDataType()){
					designDetails.setDatasourceType(basetype.getDatasourceType());
					designDetails.setConstrainsType(basetype.getConstrainsType());
				}
			}
		}*/
		
		// Get Information Table Design Details
		List<TableDesignDetails> tableDesignDetailsAfter = tableDesign.getListTableDesignDetails();
		List<TableDesignDetails> insertTableDetails = new ArrayList<TableDesignDetails>();
		List<TableDesignDetails> deleteTableDetails = new ArrayList<TableDesignDetails>();
		List<TableDesignDetails> updateTableDetails = new ArrayList<TableDesignDetails>();
		List<TableDesignDetails> updateDataTypeOfTableDetails = new ArrayList<TableDesignDetails>();
		
		this.setBaseTypeForDomainType(tableDesignDetailsAfter, listOfBasetype);
		this.setBaseTypeForDomainType(tableDesignDetailsBefore, listOfBasetype);
		/*this.setBaseTypeForDomainType(tableDesignDetailsBefore, listOfBasetype);*/
		
		// Re-index SEQ_ITEM_NO
		int seqItemNo = 0;
		
		boolean check = false;
		if(isExport){
			for (TableDesignDetails after : tableDesignDetailsAfter) {
				after.setItemSeqNo(seqItemNo);
				check = false;
				for (TableDesignDetails before : tableDesignDetailsBefore) {
					if(after.getCode().equals(before.getCode())){
						check = true;
						break;
					}
				}
				if(!check){
//					after.setColumnId(null);
					insertTableDetails.add(after);
				}
				seqItemNo++;
			}
		}else{
			// Use iterator to reset
			for (TableDesignDetails after : tableDesignDetailsAfter) {
				after.setItemSeqNo(seqItemNo);
				if(after.getColumnId() == null){
					after.setTableDesignId(tableDesignId);
					insertTableDetails.add(after);
				}
				seqItemNo++;
			}
		}
		
		check = false;
		
		for (TableDesignDetails before : tableDesignDetailsBefore) {
			check = false;
			for (TableDesignDetails after : tableDesignDetailsAfter) {
				if(before.getColumnId().equals(after.getColumnId())){
					
					// Set item type
					if (after.hasBeenChangedBasetype(before) || DataTypeUtils.notEquals(after.getDatasourceType(), before.getDatasourceType())
							|| DataTypeUtils.notEquals(after.getConstrainsType(), before.getConstrainsType())) {
						TableDesignUtil.initDefaultForItemType(after, validationRules);
					} else {
						/*if(DbDomainConst.ConstrainsType.DATASOURCE.equals(after.getConstrainsType())){
							if(DbDomainConst.DatasourceType.SQL_BUILDER_IS_AUTOCOMPLETE.equals(after.getDatasourceType())){
								after.setItemType(DbDomainConst.LogicDataType.AUTOCOMPLETE);
							}else{
								after.setItemType(DbDomainConst.LogicDataType.SELECT);
							}
						}else{*/
							after.setItemType(before.getItemType());
						/*}*/
					}
					
					//display type
					after.setDisplayType(before.getDisplayType());
					if (DbDomainConst.YesNoFlg.YES.equals(after.getIsMandatory())) {
						if(after.getAutoIncrementFlag() == 0 && StringUtils.isBlank(after.getDefaultValue())){
							after.setDisplayType(DbDomainConst.DisplayType.USED);
						}
					}

					if (DbDomainConst.DatasourceType.USER_DEFINE.equals(before.getDatasourceType()) 
							&& DbDomainConst.DataTypeFlag.PRIMITIVE.equals(before.getDataTypeFlg())){
						listIdOfUserdefineCodelist.add(before.getDatasourceId());
					}
					
					//DungNN 20150927 - recalculation for data type and group base type
					for (Basetype basetype: listOfBasetype) {
						if (basetype.getBasetyeId().equals(after.getDataType())) {
							after.setDataTypeFlg(basetype.getDataTypeFlg());
							after.setGroupBaseTypeId(basetype.getGroupBaseTypeId());
							break;
						}
					}
					
					// Set java type
					after.setJavaType(BusinessDesignHelper.convertJavaTypeFromBaseType(after.getBaseType()));

					if(after.getKeyType().substring(after.getKeyType().length() - 1).equals("1")){
						after.setIsMandatory(1);
					}
					after.setTableDesignId(tableDesign.getTableDesignId());
					
						
					// Check pattern format
					checkConditionPatternFormat(before, after, common);	
					after.setPatternFormat(before.getPatternFormat());
					
					updateTableDetails.add(after);
					if(!before.getBaseType().equals(after.getBaseType())){
						after.setOldBaseType(before.getBaseType());
						updateDataTypeOfTableDetails.add(after);
					}
					check = true;
					break;
				}
			}
			if(!check){
				deleteTableDetails.add(before);
			}
		}
		
		//delete user define code list
		if (!listIdOfUserdefineCodelist.isEmpty())
			userDefineCodelistRepository.multiDeleteUserDefineCodelist(listIdOfUserdefineCodelist);
		
		List<UserDefineCodelistDetails> listUserDefineCodelistDetails = new ArrayList<UserDefineCodelistDetails>();
		
		// Delete Row
		if(deleteTableDetails.size() > 0){
			List<Long> listOfId = new ArrayList<Long>();
			for (TableDesignDetails tddelete : deleteTableDetails) {
				listOfId.add(tddelete.getColumnId());
			}
			try{
				this.updateForeignKeyWhenDeleteColumns(listOfId);
				tableDesignForeignKeyRepository.deleteByTableDesignDetail(listOfId);
				tableDesignDetailRepository.multiDelete(listOfId);
			} catch (Exception ex) {
				//if missing check fk then put out message
				if (ex.getMessage().contains("violates foreign key constraint")) {
					//ResultMessages resultMessages = ResultMessages.error();
					throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0097));
				}
				throw ex;
			}
			
			//impact change design of business logic
			List<TableDesign> tblContentColumnDelete = new ArrayList<TableDesign>();
			tableDesignForModify.setListTableDesignDetails(deleteTableDetails);
			tblContentColumnDelete.add(tableDesignForModify);
			this.processAutoSynchDataReferBusinessLogicByDeleteColumn(tblContentColumnDelete, common.getCreatedBy(), projectId);
		}
		
		// Mofify Row
		if(updateTableDetails.size() > 0){
			if(!isExport){
				for (TableDesignDetails update : updateTableDetails) {
					update.setStatusImport("Modify");
					if (DbDomainConst.DataTypeFlag.PRIMITIVE.equals(update.getDataTypeFlg()) 
							&& DbDomainConst.ConstrainsType.DATASOURCE.equals(update.getConstrainsType()) 
							&& DbDomainConst.DatasourceType.USER_DEFINE.equals(update.getDatasourceType())){
						this.insertUserDefineCodelist(listUserDefineCodelistDetails, update);
					}
				}
				if(listUserDefineCodelistDetails.size() > 0){
					userDefineCodelistRepository.createUserDefineCodelistDetails(listUserDefineCodelistDetails);
				}
			}
			
			this.processSeqCode(tableDesign.getTableCode(), updateTableDetails, project);
			this.processAffectChangeDataTypeForeignKey(updateDataTypeOfTableDetails, listOfBasetype, common);
			tableDesignDetailRepository.updateTableDesignDetails(updateTableDetails);
			
			
			//auto synchronize output bean/object definition of business logic
			List<TableDesign> tblContentColumnUpdate = new ArrayList<TableDesign>();
			tableDesignForModify.setListTableDesignDetails(updateTableDetails);
			tableDesignForModify.setListTableDesignDetailsChangeType(updateDataTypeOfTableDetails);
			tblContentColumnUpdate.add(tableDesignForModify);
			this.processAutoSynchDataReferBusinessLogicByModifyTableDesign(tblContentColumnUpdate);
		}
		listUserDefineCodelistDetails.clear();
		
		// Insert New Row
		if(insertTableDetails.size() > 0){
			this.processSeqCode(tableDesign.getTableCode(), insertTableDetails, project);
			settingRowBeforeInsert(tableDesignId, insertTableDetails, listUserDefineCodelistDetails, validationRules, false, isExport, projectId);
			//auto synchronize output bean/object definition of business logic
			List<TableDesign> tblContentColumnAddnew = new ArrayList<TableDesign>();
			tableDesignForModify.setListTableDesignDetails(insertTableDetails);
			tblContentColumnAddnew.add(tableDesignForModify);
			this.processAutoSynchDataReferBusinessLogicByAddingColumn(tblContentColumnAddnew);
		}
		
		// Update TABLE_DESIGN_KEY
		tableDesignKeyRepository.deleteKeyByTableDesignId(tableDesignId);
		
		// Update TABLE_DESIGN_FOREIGN_KEY
		tableDesignForeignKeyRepository.deleteByTableDesign(tableDesignId);
		this.insertKey(tableDesign, projectId);
		
		// Insert Problem
		tableDesign.setActionDelete(false);
		
		// Change name table SQL_DESIGN_CONDITIONS
		List<TableDesign> listTableDesigns = new ArrayList<TableDesign>();
		listTableDesigns.add(tableDesign);
		sqlDesignConditionRepository.updateNameAffect(listTableDesigns);
		sqlDesignResultRepository.updateNameAffect(listTableDesigns);
		sqlDesignTableRepository.updateNameAffect(listTableDesigns);
		sqlDesignTableItemsRepository.updateNameAffect(listTableDesigns);
		sqlDesignValueRepository.updateNameAffect(listTableDesigns);
		
		// Delete problem when change Oracle tp Postgres
		problemListRepository.deleteFromResourceTypeOfProject(DbDomainConst.FromResourceType.PROJECT_CHANGE_DB_TYPE, projectId);
	}

	/**
	 * @param tableDesign
	 * @param accountId
	 * @param currentTime
	 * @param tableDesignId
	 * @return
	 * @throws BusinessException
	 */
	private TableDesign modifyTableAndSubjectArea(TableDesign tableDesign, CommonModel common, Timestamp currentTime) throws BusinessException {
		
		Long tableDesignId = tableDesign.getTableDesignId();
		
		// Get Inform Table
		TableDesign tableDesignForModify = tableDesignRepository.findOneById(tableDesignId);
		
		projectService.validateProject(tableDesign.getProjectId(), common.getCreatedBy(), common.getWorkingProjectId(), true);
		
		// Check exits Table
		if(tableDesignForModify == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(CommonMessageConst.TQP_TABLEDESIGN)));
		}
		//DungNN - 20160216 - if not QP table then validate duplicated 
		if(!DbDomainConst.TableDesignType.QP_TABLE.equals(tableDesignForModify.getType())){
			//KhangTM 20151216 : check duplicate table
			if(tableDesign.getProjectId() == null){
				tableDesign.setProjectId(common.getWorkingProjectId());
			}
			Long totalTableDuplicate = tableDesignRepository.countNameCodeByProjectId(tableDesign);
			ResultMessages resultMessages = ResultMessages.error();
			
			if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalTableDuplicate)) {
				resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0019));
			} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalTableDuplicate)) {
				resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0020));
			} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalTableDuplicate)) {
				resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0019));
				resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0020));
			}
			if (resultMessages.isNotEmpty()) {
				throw new BusinessException(resultMessages);
			}
		}
		// Check common table
//		if(DbDomainConst.TableDesignType.QP_TABLE.equals(tableDesignForModify.getType())){
//			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0134, tableDesign.getTableName()));
//		}
		
		// Check Unsign Table
		if(DbDomainConst.DesignStatus.FIXED.equals(tableDesignForModify.getDesignStatus()) && !DbDomainConst.TableDesignType.QP_TABLE.equals(tableDesignForModify.getType())){
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0109, tableDesign.getTableName()));
		}
		tableDesignForModify.setSystemTime(currentTime);
		tableDesignForModify.setUpdatedDate(tableDesign.getUpdatedDate());
		tableDesignForModify.setUpdatedBy(tableDesign.getCreatedBy());
		
		// Check change status parent
		if(!DbDomainConst.TableDesignType.QP_TABLE.equals(tableDesignForModify.getType())){
			projectService.validateProject(tableDesignForModify.getProjectId(), common.getCreatedBy(), common.getWorkingProjectId(), true);
			tableDesignForModify.setTableName(tableDesign.getTableName());
			tableDesignForModify.setTableCode(tableDesign.getTableCode());
			tableDesignForModify.setRemark(tableDesign.getRemark());
			tableDesignForModify.setType(tableDesign.getType());
			tableDesignForModify.setCommonColumn(tableDesign.getCommonColumn());
			
			// Check Concurrence when delete
			if(!tableDesignRepository.modifyTableDesign(tableDesignForModify)){
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
			}
		}else{
			// Check Concurrence when delete
			if(!tableDesignRepository.modifyTableDesignCommon(tableDesignForModify)){
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
			}
		}
		
		// Update Table SUBJECT_AREA_DESIGN_TABLE
		// Get Information
		List<SubjectArea> subjectAreasBefore = subjectAreaRepository.getAllSubAreaByTableId(tableDesignId);

		// List Area after modify
		List<SubjectArea> subjectAreasAfter = tableDesign.getSubjectAreas();
		List<SubjectAreaTableDesign> deleteSubjectArea = new ArrayList<SubjectAreaTableDesign>();
		List<SubjectAreaTableDesign> insertSubjectArea = new ArrayList<SubjectAreaTableDesign>();
		SubjectAreaTableDesign subAreaTableDesign = null;
		
		if(FunctionCommon.isNotEmpty(subjectAreasAfter)) {
			if (subjectAreasBefore.size() <= subjectAreasAfter.size()) {
				for (int i = 0; i < subjectAreasBefore.size(); i++) {
					if (!subjectAreasBefore.get(i).getAreaId().equals(subjectAreasAfter.get(i).getAreaId())) {
						subAreaTableDesign = new SubjectAreaTableDesign();
						subAreaTableDesign.setSubAreaId(subjectAreasBefore.get(i).getAreaId());
						subAreaTableDesign.setTableId(tableDesignId);
						deleteSubjectArea.add(subAreaTableDesign);
	
						subAreaTableDesign = new SubjectAreaTableDesign();
						subAreaTableDesign.setSubAreaId(subjectAreasAfter.get(i).getAreaId());
						subAreaTableDesign.setTableId(tableDesignId);
						insertSubjectArea.add(subAreaTableDesign);
					}
				}
	
				for (int j = subjectAreasBefore.size(); j < subjectAreasAfter.size(); j++) {
	
					subAreaTableDesign = new SubjectAreaTableDesign();
					subAreaTableDesign.setSubAreaId(subjectAreasAfter.get(j).getAreaId());
					subAreaTableDesign.setTableId(tableDesignId);
					insertSubjectArea.add(subAreaTableDesign);
				}
	
				if(insertSubjectArea.size() > 0){
					subjectAreaTableRepository.insertArray(insertSubjectArea);
				}
				subjectAreaTableRepository.delete(deleteSubjectArea);
			}
	
			deleteSubjectArea = new ArrayList<SubjectAreaTableDesign>();
			insertSubjectArea = new ArrayList<SubjectAreaTableDesign>();
	
			if (subjectAreasBefore.size() > subjectAreasAfter.size()) {
				for (int i = 0; i < subjectAreasAfter.size(); i++) {
					if (!subjectAreasBefore.get(i).getAreaId().equals(subjectAreasAfter.get(i).getAreaId())) {
	
						subAreaTableDesign = new SubjectAreaTableDesign();
						subAreaTableDesign.setSubAreaId(subjectAreasBefore.get(i).getAreaId());
						subAreaTableDesign.setTableId(tableDesignId);
						deleteSubjectArea.add(subAreaTableDesign);
	
						subAreaTableDesign = new SubjectAreaTableDesign();
						subAreaTableDesign.setSubAreaId(subjectAreasAfter.get(i).getAreaId());
						subAreaTableDesign.setTableId(tableDesignId);
						insertSubjectArea.add(subAreaTableDesign);
					}
				}
	
				for (int j = subjectAreasAfter.size(); j < subjectAreasBefore.size(); j++) {
					subAreaTableDesign = new SubjectAreaTableDesign();
					subAreaTableDesign.setSubAreaId(subjectAreasBefore.get(j).getAreaId());
					subAreaTableDesign.setTableId(tableDesignId);
					deleteSubjectArea.add(subAreaTableDesign);
				}
	
				if(insertSubjectArea.size() > 0){
					subjectAreaTableRepository.insertArray(insertSubjectArea);
				}
				subjectAreaTableRepository.delete(deleteSubjectArea);
			}
		}else{
			subjectAreaTableRepository.deleteAllByTableId(tableDesignId);
		}
		return tableDesignForModify;
	}

	/**
	 * Auto synchronize outputbean/object definition of business logic when adding new table design details (column)
	 * 
	 * @param tableDesignForModify
	 * @param insertTableDetails
	 */
	public void processAutoSynchDataReferBusinessLogicByAddingColumn (List<TableDesign> tableDesignForModify) {
		for (TableDesign tableDesign : tableDesignForModify) {
			// Process for insert affect
			List<Long> lstAffectedBlogic = new ArrayList<Long>();
			if(tableDesign.getListTableDesignDetails().size() > 0) {
				// Select all parent of object definition by id
				List<ObjectDefinition> objDefinitions = businessDesignShareService.findAllInforOfParenObjDefinitionBeanById(tableDesign.getTableDesignId());
				if(objDefinitions != null && objDefinitions.size() > 0) {
					List<ObjectDefinition> listForInsert = new ArrayList<ObjectDefinition>();
					ObjectDefinition obj = null;
					for (ObjectDefinition itemParent : objDefinitions) {
						List<ObjectDefinition> listObj = new ArrayList<ObjectDefinition>();
						int itemSequenceNo = itemParent.getItemSequenceNo() == null ? 0 : itemParent.getItemSequenceNo() + 1;
						for (TableDesignDetails itemChild : tableDesign.getListTableDesignDetails()) {
							obj = new ObjectDefinition();
							obj.setObjectDefinitionCode(itemChild.getCode());
							obj.setObjectDefinitionName(itemChild.getName());
							obj.setDataType(BusinessDesignHelper.convertJavaTypeFromBaseType(itemChild.getBaseType().intValue()));
							obj.setArrayFlg(false);
							obj.setBusinessLogicId(itemParent.getBusinessLogicId());
							obj.setParentObjectDefinitionId(itemParent.getObjectDefinitionId());
							obj.setItemSequenceNo(itemSequenceNo++);
							obj.setTblDesignId(itemChild.getTableDesignId());
							obj.setGroupBaseTypeId(itemChild.getBaseType());
							obj.setImpactStatus(itemParent.getImpactStatus());
							obj.setColumnId(itemChild.getColumnId());
							// Add list insert after processing
							listObj.add(obj);
						}
						// Add all list for insert
						listForInsert.addAll(listObj);
						lstAffectedBlogic.add(itemParent.getBusinessLogicId());
					}
					//businessDesignShareService.registerListObjectDefinition(listForInsert);
				}
				// Select all parent of object output bean by id
				List<OutputBean> outputBeans = businessDesignShareService.findAllInforOfParenOutBeanById(tableDesign.getTableDesignId());
				if(outputBeans != null && outputBeans.size() > 0) {
					List<OutputBean> listForInsert = new ArrayList<OutputBean>();
					OutputBean obj = null;
					for (OutputBean itemParent : outputBeans) {
						List<OutputBean> listOut = new ArrayList<OutputBean>();
						int itemSequenceNo = itemParent.getItemSequenceNo() + 1;
						for (TableDesignDetails itemChild : tableDesign.getListTableDesignDetails()) {
							obj = new OutputBean();
							obj.setOutputBeanCode(itemChild.getCode());
							obj.setOutputBeanName(itemChild.getName());
							obj.setDataType(BusinessDesignHelper.convertJavaTypeFromBaseType(itemChild.getBaseType().intValue()));
							obj.setArrayFlg(false);
							obj.setBusinessLogicId(itemParent.getBusinessLogicId());
							obj.setParentOutputBeanId(itemParent.getOutputBeanId());
							obj.setItemSequenceNo(itemSequenceNo++);
							obj.setTblDesignId(itemChild.getTableDesignId());
							obj.setGroupBaseTypeId(itemChild.getBaseType());
							obj.setImpactStatus(itemParent.getImpactStatus());
							obj.setColumnId(itemChild.getColumnId());
							// Add list insert after processing
							listOut.add(obj);
						}
						// Add all list for insert
						listForInsert.addAll(listOut);
						lstAffectedBlogic.add(itemParent.getBusinessLogicId());
					}
					//businessDesignShareService.registerListOutputBean(listForInsert);
				}
				//update design status of affected blogic
				businessDesignShareService.updateDesignStatusOfAffectedBlogic(lstAffectedBlogic,tableDesign.getUpdatedBy(),tableDesign.getSystemTime());
			}
		}
	}
	
	/**
	 * Auto fix data of business logic when change table design
	 * 
	 * @param tableDesignForModify
	 * @param updateTableDetails
	 * @param updateDataTypeOfTableDetails
	 */
	public void processAutoSynchDataReferBusinessLogicByModifyTableDesign (List<TableDesign> tableDesign) {
		// Process for update affect
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		for (TableDesign design : tableDesign) {
			List<ProblemList> problemLists = new ArrayList<ProblemList>();
			List<BusinessLogic> logics = new ArrayList<BusinessLogic>();
			List<Long> lstAffectedBlogic = new ArrayList<Long>();
			List<Integer> lstFromResourceType = new ArrayList<Integer>();
			List<Long> lstFromResouceId = new ArrayList<Long>();
			if(design.getListTableDesignDetails().size() >0){
				List<TableDesignDetails> tableDesignDetails  = design.getListTableDesignDetails();
				for(TableDesignDetails item : tableDesignDetails) {
					// Convert data type type DB to Java
					item.setDataType(new Long(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getBaseType().intValue())));
				}
				
				TableDesignDetails itemMaster = new TableDesignDetails();
				itemMaster.setTableDesignId(design.getTableDesignId());
				itemMaster.setName(design.getTableName());
				itemMaster.setCode(design.getTableCode());
				tableDesignDetails.add(itemMaster);
				
				if(design.getListTableDesignDetailsChangeType().size() >0){
					logics = tableDesignRepository.getAllBLogicAffectedByColumnIds(design.getListTableDesignDetailsChangeType());
					Long workingProjectId = design.getProjectId();
					Long accountId = design.getUpdatedBy();
					for (TableDesignDetails designDetails : design.getListTableDesignDetailsChangeType()) {
						String oldDataType  = BusinessDesignHelper.getDataTypeStr(BusinessDesignHelper.convertJavaTypeFromBaseType( designDetails.getOldBaseType()), false);
						String newDataType  = BusinessDesignHelper.getDataTypeStr(BusinessDesignHelper.convertJavaTypeFromBaseType( designDetails.getBaseType()), false);
						for (BusinessLogic businessLogic : logics) {
							if(businessLogic.getColumnId().equals(designDetails.getColumnId())){
								if(!DataTypeUtils.equals(businessLogic.getDataType(), BusinessDesignHelper.convertJavaTypeFromBaseType( designDetails.getBaseType()).toString())){
									ProblemList problem = new ProblemList();
									problem.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0009, designDetails.getName(), design.getTableName(),oldDataType,newDataType));
									problem.setResourceType(DbDomainConst.ResourceType.BLOGIC);
									problem.setResourceId(businessLogic.getBusinessLogicId());
									problem.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
									problem.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
									problem.setModuleId(businessLogic.getModuleId());
									problem.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
									problem.setFromResourceType(DbDomainConst.FromResourceType.TABLE_DESIGN_DETAIL_CHANGE_DATATYPE);
									problem.setFromResourceId(designDetails.getColumnId());
									problem.setCreatedBy(accountId);
									problem.setCreatedDate(currentTime);
									problem.setProjectId(workingProjectId);
									problemLists.add(problem);
								}
								lstFromResourceType.add(DbDomainConst.FromResourceType.TABLE_DESIGN_DETAIL_CHANGE_ADD_COLUMN);
								lstFromResourceType.add(DbDomainConst.FromResourceType.TABLE_DESIGN_DETAIL_CHANGE_DATATYPE);
								lstFromResouceId.add(designDetails.getColumnId());
							}
						}
					}
					problemListRepository.deleteFromResourceTypeOfTblDesign(lstFromResourceType, lstFromResouceId);
					if(problemLists.size() > 0){
						problemListRepository.multiRegisterProblem(problemLists);
					}
					for (BusinessLogic businessLogic : logics) {
						lstAffectedBlogic.add(businessLogic.getBusinessLogicId());
					}
					businessDesignShareService.updateDesignStatusOfAffectedBlogic(lstAffectedBlogic,design.getUpdatedBy(),design.getSystemTime());
				}
				
				// Sync name ,code
				businessDesignShareService.autoUpdateAffectObjectDefinitionBean(tableDesignDetails);
				businessDesignShareService.autoUpdateAffectOutputBean(tableDesignDetails);
				businessDesignShareService.autoUpdateAffectBusinessCheckComp(tableDesignDetails);
			}
		}
	}
	
	/**
	 * @param TableDesign tableDesign
	 * @param List<TableDesignDetails> deletedTableDesignDetails
	 */
	public void processAutoSynchDataReferBusinessLogicByDeleteColumn(List<TableDesign> tableDesign, Long accountId, Long projectId){
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		for (TableDesign design : tableDesign) {
			List<ProblemList> problemLists = new ArrayList<ProblemList>();
			List<BusinessLogic> logics = new ArrayList<BusinessLogic>();
			List<Long> lstAffectedBlogic = new ArrayList<Long>();
			List<Integer> lstFromResourceType = new ArrayList<Integer>();
			List<Long> lstFromResouceId = new ArrayList<Long>();
			if(design.getListTableDesignDetails().size() >0){
				logics = tableDesignRepository.getAllBLogicAffectedByTableDesignId(design.getTableDesignId());
				for (TableDesignDetails designDetails : design.getListTableDesignDetails()) {
					for (BusinessLogic businessLogic : logics) {
						ProblemList problem = new ProblemList();
						problem.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0010, designDetails.getName(), design.getTableName()));
						problem.setResourceType(DbDomainConst.ResourceType.BLOGIC);
						problem.setResourceId(businessLogic.getBusinessLogicId());
						problem.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
						problem.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
						problem.setModuleId(businessLogic.getModuleId());
						problem.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
						problem.setFromResourceType(DbDomainConst.FromResourceType.TABLE_DESIGN_DETAIL_CHANGE_DELETE_COLUMN);
						problem.setFromResourceId(designDetails.getColumnId());
						problem.setCreatedBy(accountId);
						problem.setCreatedDate(currentTime);
						problem.setProjectId(projectId);
						problemLists.add(problem);
					}
					lstFromResourceType.add(DbDomainConst.FromResourceType.TABLE_DESIGN_DETAIL_CHANGE_ADD_COLUMN);
					lstFromResouceId.add(designDetails.getColumnId());
				}
				
				if (CollectionUtils.isNotEmpty(lstFromResouceId)) {
					problemListRepository.deleteFromResourceTypeOfTblDesign(lstFromResourceType, lstFromResouceId);
				}

				if(problemLists.size() > 0){
					problemListRepository.multiRegisterProblem(problemLists);
				}
				// Process for delete affect
				businessDesignShareService.autoDeleteAffectObjectDefinitionBean(design.getListTableDesignDetails());
				businessDesignShareService.autoDeleteAffectOutputBean(design.getListTableDesignDetails());
				for (BusinessLogic businessLogic : logics) {
					lstAffectedBlogic.add(businessLogic.getBusinessLogicId());
				}
				businessDesignShareService.updateDesignStatusOfAffectedBlogic(lstAffectedBlogic,design.getUpdatedBy(),design.getSystemTime());
			}
		}
	}
	/**
	 * @param TableDesign tableDesign
	 */
	public void impactChangeDesignBusinessLogicByDeleteTable(List<TableDesign> tableDesign, Long projectId){
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		for (TableDesign design : tableDesign) {
			List<ProblemList> problemLists = new ArrayList<ProblemList>();
			List<Integer> lstFromResourceType = new ArrayList<Integer>();
			List<Long> lstFromResouceId = new ArrayList<Long>();
			List<Long> lstAffectedBlogic = new ArrayList<Long>();
			List<BusinessLogic> logics = new ArrayList<BusinessLogic>();
			logics = tableDesignRepository.getAllBLogicAffectedByTableDesignId(design.getTableDesignId());
			lstFromResouceId.add(design.getTableDesignId());
			for (BusinessLogic businessLogic : logics) {
				ProblemList problem = new ProblemList();
				problem.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0014, design.getTableName()));
				problem.setResourceType(DbDomainConst.ResourceType.BLOGIC);
				problem.setResourceId(businessLogic.getBusinessLogicId());
				problem.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problem.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
				problem.setModuleId(businessLogic.getModuleId());
				problem.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
				problem.setFromResourceType(DbDomainConst.FromResourceType.TABLE_DESIGN_DELETE);
				problem.setFromResourceId(design.getTableDesignId());
				problem.setCreatedBy(design.getUpdatedBy());
				problem.setCreatedDate(currentTime);
				problem.setProjectId(projectId);
				problemLists.add(problem);
				lstAffectedBlogic.add(businessLogic.getBusinessLogicId());
			}

			lstFromResourceType.add(DbDomainConst.FromResourceType.TABLE_DESIGN_DELETE);
			problemListRepository.deleteFromResourceTypeOfTblDesign(lstFromResourceType, lstFromResouceId);

			if(problemLists.size() > 0){
				problemListRepository.multiRegisterProblem(problemLists);
			}
			businessDesignShareService.updateDesignStatusOfAffectedBlogic(lstAffectedBlogic,design.getUpdatedBy(),design.getSystemTime());
			businessDesignShareService.autoDeleteAffectObjectDefinitionBeanByDeleleTableDesign(design.getTableDesignId());
			businessDesignShareService.autoDeleteAffectOutputBeanDeleleTableDesign(design.getTableDesignId());
			businessDesignShareService.deleteAffectBusinessCheckCompById(design.getTableDesignId());
		}
	}
	@Override
	public void deleteTable(TableDesign tableDesign, Long accountId, Long projectId, boolean hasProblem) {
		// Get tableId
		Long tableId = tableDesign.getTableDesignId();
		TableDesign tableFromDb = tableDesignRepository.findOneById(tableId);
		
		if (tableFromDb == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(CommonMessageConst.TQP_TABLEDESIGN)));
		}

		if (DbDomainConst.DesignStatus.FIXED.equals(tableFromDb.getDesignStatus())) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0109, tableFromDb.getTableName()));
		}

		//check Concurrence
		if (DateUtils.compare(tableFromDb.getUpdatedDate(), tableDesign.getUpdatedDate()) == 1) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
		}
		
		projectService.validateProject(tableFromDb.getProjectId(), accountId, projectId, true );
		
		
		// Delete Table Design
		List<TableDesign> tableDesigns = new ArrayList<TableDesign>();
		tableDesigns.add(tableFromDb);
		this.deleteTableDesign(tableDesigns);
		
		if(hasProblem){
			tableDesign.setIsDeleted(true);
			List<TableDesign> tblAffect = new ArrayList<TableDesign>();
			tblAffect.add(tableDesign);
			this.impactChangeDesignSqlDesignByDeleteTable(tblAffect, accountId, projectId);
			
			//impact change design to business logic
			tableDesign.setUpdatedBy(accountId);
			tableDesign.setSystemTime(FunctionCommon.getCurrentTime());
			this.impactChangeDesignBusinessLogicByDeleteTable(tblAffect, projectId);
		}
	}

	@Override
	public void createTable(TableDesign tableDesign, CommonModel commonModel, Project project, AccountProfile account) {
		List<Basetype> listOfBasetype = domainRepository.getAllBasetype(project.getProjectId());
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		Long projectId = project.getProjectId();
		
		// Check change status parent
		//projectService.validateProject(projectId);
		projectService.validateProject(tableDesign.getProjectId(), commonModel.getCreatedBy(), commonModel.getWorkingProjectId(), true);
		
		Long totalTableDuplicate = tableDesignRepository.countNameCodeByProjectId(tableDesign);
		ResultMessages resultMessages = ResultMessages.error();
		
		if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalTableDuplicate)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0019));
		} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalTableDuplicate)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0020));
		} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalTableDuplicate)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0019));
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0020));
		}
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {

			// Insert to TABLE_DESIGN
			tableDesign.setX(0);
			tableDesign.setY(0);
			tableDesign.setCreatedDate(currentTime);
			tableDesign.setUpdatedDate(currentTime);
			tableDesignRepository.create(tableDesign);
	
			// Get TABLE_DESIGN_ID
			Long tableDesignId = tableDesign.getTableDesignId();
	
			// Insert to SUBJECT_AREA_DESIGN_TABLE
			List<SubjectAreaTableDesign> subAreaTableDesigns = new ArrayList<SubjectAreaTableDesign>();
	
			SubjectAreaTableDesign areaTableDesign = null;
			
			if (FunctionCommon.isNotEmpty(tableDesign.getSubjectAreas())) {
	
				for (SubjectArea subjectArea : tableDesign.getSubjectAreas()) {
	
					areaTableDesign = new SubjectAreaTableDesign();
	
					areaTableDesign.setSubAreaId(subjectArea.getAreaId());
					areaTableDesign.setTableId(tableDesignId);
	
					subAreaTableDesigns.add(areaTableDesign);
				}
				subjectAreaTableRepository.insertArray(subAreaTableDesigns);
			}
			
			// Insert to TABLE_DESIGN_DETAILS
			List<TableDesignDetails> tableDesignDetails = tableDesign.getListTableDesignDetails();
			
			// Set item SEQ_NO
			for (int i = 0; i < tableDesignDetails.size(); i++) {
				tableDesignDetails.get(i).setItemSeqNo(i);
			}
			this.setBaseTypeForDomainType(tableDesignDetails, listOfBasetype);
			this.setPatternFormatForDomainType(tableDesignDetails, account);
			List<UserDefineCodelistDetails> listUserDefineCodelistDetails = new ArrayList<UserDefineCodelistDetails>();
			List<ValidationRule> validationRules = domainDatatypeRepository.findAllValidationRule();
	
			this.processSeqCode(tableDesign.getTableCode(), tableDesignDetails, project);
			this.settingRowBeforeInsert(tableDesignId, tableDesignDetails, listUserDefineCodelistDetails, validationRules, true, false, projectId);
	
			tableDesign.setTableDesignId(tableDesign.getTableDesignId());
	
			this.insertKey(tableDesign, projectId);
		}
	}
	
	/**
	 * DungNN modify 2015-08-08
	 */
	public Map<Integer, String> getOptionItemType(TableDesignDetails tableDesignDetails, List<ValidationRule> listValidationRule) {
		
		Map<Integer, String> mapReturn = new ConcurrentHashMap<Integer, String>();
		//if mapItemtype is null then initial for this
		if(mapItemtype == null) {
			initItemtypeCodelist();
		}
		//if configuration datasource
		if(DbDomainConst.ConstrainsType.DATASOURCE.equals(tableDesignDetails.getConstrainsType())) {
			// if datasourcetype is User Define or Code List
			if(DbDomainConst.DatasourceType.USER_DEFINE.equals(tableDesignDetails.getDatasourceType())
					|| DbDomainConst.DatasourceType.CODELIST.equals(tableDesignDetails.getDatasourceType())) {
				mapReturn.put(DbDomainConst.LogicDataType.SELECT, mapItemtype.get(DbDomainConst.LogicDataType.SELECT));
				mapReturn.put(DbDomainConst.LogicDataType.RADIO, mapItemtype.get(DbDomainConst.LogicDataType.RADIO));
				mapReturn.put(DbDomainConst.LogicDataType.CHECKBOX, mapItemtype.get(DbDomainConst.LogicDataType.CHECKBOX));
				
			} else if (DbDomainConst.DatasourceType.SQL_BUILDER.equals(tableDesignDetails.getDatasourceType())) {// if datasourcetype is SQL Builder
				mapReturn.put(DbDomainConst.LogicDataType.SELECT, mapItemtype.get(DbDomainConst.LogicDataType.SELECT));
				mapReturn.put(DbDomainConst.LogicDataType.RADIO, mapItemtype.get(DbDomainConst.LogicDataType.RADIO));
				mapReturn.put(DbDomainConst.LogicDataType.CHECKBOX, mapItemtype.get(DbDomainConst.LogicDataType.CHECKBOX));
				
			} else if(DbDomainConst.DatasourceType.SQL_BUILDER_IS_AUTOCOMPLETE.equals(tableDesignDetails.getDatasourceType())){// if datasourcetype is SQL Builder-Autocomplete
				mapReturn.put(DbDomainConst.LogicDataType.AUTOCOMPLETE, mapItemtype.get(DbDomainConst.LogicDataType.AUTOCOMPLETE));
			}
		} else {
			if (FunctionCommon.equals(DomainDatatypeConst.PhysicalDataTypeDetail.TEXT, tableDesignDetails.getBaseType()) 
					|| FunctionCommon.equals(DomainDatatypeConst.PhysicalDataTypeDetail.CHAR, tableDesignDetails.getBaseType()) 
					|| FunctionCommon.equals(DomainDatatypeConst.PhysicalDataTypeDetail.CHARACTER_VARYING, tableDesignDetails.getBaseType())) {

				mapReturn.put(DbDomainConst.LogicDataType.NAME, mapItemtype.get(DbDomainConst.LogicDataType.NAME));

				if (tableDesignDetails.getMaxlength() == null || (tableDesignDetails.getMaxlength() != null 
						&& tableDesignDetails.getMaxlength().intValue() > DbDomainConst.MIN_VAL_TEXT_INPUT_AREA)) {
					mapReturn.put(DbDomainConst.LogicDataType.REMARK, mapItemtype.get(DbDomainConst.LogicDataType.REMARK));
				}
			}
			
			if(StringUtils.isNotBlank(tableDesignDetails.getFmtCode())) {
				for(String fmtCode : tableDesignDetails.getFmtCode().split(",")){
					for(ValidationRule validateObj: listValidationRule){
						if(fmtCode.equalsIgnoreCase(validateObj.getValidationRuleCode())){
							mapReturn.put(validateObj.getItemType(), mapItemtype.get(validateObj.getItemType()));
							break;
						}
					}
				}
			} else {
				for(ValidationRule validateObj: listValidationRule){
					for(String baseType : validateObj.getBaseTypeGroup().split(CHAR_SPLIT)){
						if(tableDesignDetails.getGroupBaseTypeId().equals(Integer.parseInt(baseType))){
							mapReturn.put(validateObj.getItemType(), mapItemtype.get(validateObj.getItemType()));
						}
					}
				}
			}
		}
		//if don't matched default text input
		if (DomainDatatypeUtil.isEmpty(mapReturn)) {
			mapReturn.put(DbDomainConst.LogicDataType.NAME, mapItemtype.get(DbDomainConst.LogicDataType.NAME));
		}

		return mapReturn;
	}
	
	@Override
	public void updateItem(TableDesign tableDesign, CommonModel commonModel) {
		// Get Inform Table
		TableDesign table = tableDesignRepository.findOneById(tableDesign.getTableDesignId());
		// Check exits Table
		if(table == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, 
					MessageUtils.getMessage(CommonMessageConst.TQP_TABLEDESIGN)));
		}

		// Check Unsign Table
		if (DbDomainConst.DesignStatus.FIXED.equals(table.getDesignStatus())) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0109, tableDesign.getTableName()));
		}
		// Check change status parent
		projectService.validateProject(tableDesign.getProjectId(), commonModel.getCreatedBy(), commonModel.getWorkingProjectId(), true);

		Timestamp currentTime = FunctionCommon.getCurrentTime();

		// Check Concurrence.
		tableDesign.setSystemTime(currentTime);
		tableDesign.setUpdatedDate(tableDesign.getUpdatedDate());
		int count = tableDesignRepository.updateItem(tableDesign);
		if(count <= 0) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0005)));
		}
	}
	
	@Override
	public void modifyDesignStatus(TableDesign tableDesign, Long accountId, Long projectId) {
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		tableDesign.setUpdatedBy(accountId);
		tableDesign.setUpdatedDate(tableDesign.getUpdatedDate());
		tableDesign.setSystemTime(currentTime);
		// check consistency
		TableDesign tableFromDb = tableDesignRepository.findOneById(tableDesign.getTableDesignId());
		if(tableFromDb == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, 
					MessageUtils.getMessage(CommonMessageConst.TQP_TABLEDESIGN)));
		}
		
		
		/*if (DbDomainConst.DesignStatus.FIXED.equals(tableFromDb.getDesignStatus())) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0109, tableFromDb.getTableName()));
		}*/

		//check Concurrence
		if (DateUtils.compare(tableFromDb.getUpdatedDate(), tableDesign.getUpdatedDate()) == 1) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
		}
		
		projectService.validateProject(tableFromDb.getProjectId(), accountId, projectId, true );
		
		
		
		if(DbDomainConst.DesignStatus.UNDER_DESIGN.equals(tableDesign.getDesignStatus())) {
			tableDesign.setDesignStatus(DbDomainConst.DesignStatus.FIXED);
			int count = tableDesignRepository.modifyDesignStatus(tableDesign);
			// check Concurrence
			if(count <= 0) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0005)));
			}
		} else {
			tableDesign.setDesignStatus( DbDomainConst.DesignStatus.UNDER_DESIGN);
			int count = tableDesignRepository.modifyDesignStatus(tableDesign);
			// check Concurrence
			if(count <= 0) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0005)));
			}
			/*// get project
			Project project = projectRepository.findById(SessionUtils.getCurrentProjectId());
			project.setUpdatedBy(SessionUtils.getAccountId());
			project.setUpdatedDate(currentTime);
			project.setStatus(DbDomainConst.DesignStatus.UNDER_DESIGN);
			projectRepository.modifyDesignStatus(project);*/
		}
		
	}

	@Override
	public void loadDataWhenHasErr(TableDesign tableDesign, Long projectId) {
		
		if(tableDesign.getListTableDesignDetails() != null){
			for (TableDesignDetails tableDesignDetails : tableDesign.getListTableDesignDetails()) {
				tableDesignDetails.setItemSeqNo(tableDesignDetails.getIndexRow());
			}
		}
		
		tableDesign.setProjectId(projectId);
		
		List<SubjectArea> subjectAreas = new ArrayList<SubjectArea>();
		
		List<SubjectArea> subjectAreasByProjId = null;
		if(DbDomainConst.TableDesignType.QP_TABLE.equals(tableDesign.getType())){
			subjectAreasByProjId = subjectAreaRepository.getAll();
		}else{
			subjectAreasByProjId = subjectAreaRepository.getAllByProjectId(projectId);
		}
		
		SubjectArea subjectArea = null;
		
		if (tableDesign.getSubjectAreas() != null) {
			
			for (SubjectArea form : tableDesign.getSubjectAreas()) {

				if (form.getAreaId() != null) {
					for (SubjectArea subArea : subjectAreasByProjId) {
						if(subArea.getAreaId().equals(form.getAreaId())){
							subjectArea = subArea;
							subjectArea.setAreaIdAutocomplete(form.getAreaIdAutocomplete());
							break;
						}
					}
				} else {
					subjectArea = new SubjectArea();
				}
				subjectAreas.add(subjectArea);
			}
			tableDesign.setSubjectAreas(subjectAreas);
		}
	}
	
	
	@Override
	public TableDesign getTableDesignForView(Long tableId, CommonModel commonModel) {
		// Get Inform Table
		TableDesign tableDesign = tableDesignRepository.findOneById(tableId);

		if(tableDesign == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, 
					MessageUtils.getMessage(CommonMessageConst.TQP_TABLEDESIGN)));
		}
		
		projectService.validateProject(tableDesign.getProjectId(), commonModel.getCreatedBy(), commonModel.getWorkingProjectId(), false);
		
		// Get Information Table Design Details
		List<TableDesignDetails> tableDesignDetailsDb = tableDesignDetailRepository.getAllInformationByTableDesign(tableId);
		int countIndex = 0;
		for (TableDesignDetails td : tableDesignDetailsDb) {
			if(td.getCommonColumn() == 1 &&  td.getDataType().intValue()== DbDomainConst.BaseType.DATETIME_BASETYPE){
				td.setDefaultValue("now()");
			}
			td.setBinKeyType(Integer.parseInt(td.getKeyType(), 2));
			td.setIndexRow(countIndex);
			countIndex++;
		}
		Collections.sort(tableDesignDetailsDb);
		tableDesign.setListTableDesignDetails(tableDesignDetailsDb);

		// Get Information Subject Area Design
		List<SubjectArea> subjectAreas = subjectAreaRepository.getAllSubAreaByTableId(tableDesign.getTableDesignId());
		tableDesign.setSubjectAreas(subjectAreas);
		
		return tableDesign;
	}
	
	@Override
	public void loadListAffected(List<TableDesign> tableDesign, int fromResourceType, Long projectId){
		List<Basetype> listOfBasetype = domainRepository.getAllBasetype(projectId);
		for (TableDesign design : tableDesign) {
			List<TableDesignDetails> tableDesignDetailsBefore = tableDesignDetailRepository.findAllByTableDesign(design.getTableDesignId());
			List<TableDesignDetails> tableDesignDetailsAfter = new ArrayList<TableDesignDetails>();
			switch (fromResourceType) {
			case DbDomainConst.FromResourceType.TABLE_DESIGN:
				tableDesignDetailsAfter = design.getListTableDesignDetails();
				break;
			case DbDomainConst.FromResourceType.GRAPHIC_DATABASE_DESIGN:
				tableDesignDetailsAfter = design.getDetails();
				break;
			}
			this.setBaseTypeForDomainType(tableDesignDetailsAfter, listOfBasetype);
			this.setBaseTypeForDomainType(tableDesignDetailsBefore, listOfBasetype);
			List<Long> listColumnIdSqlDesign = new ArrayList<Long>();
			List<TableDesignDetails> listColumnChangeType = new ArrayList<TableDesignDetails>();
			List<TableDesignDetails> listColumnDelete = new ArrayList<TableDesignDetails>();
			List<TableDesignDetails> forKey = new ArrayList<TableDesignDetails>();
			
			boolean check = false;
			
			for (TableDesignDetails before : tableDesignDetailsBefore) {
				if(!design.getIsDeleted()){
					check = false;
					if(tableDesignDetailsAfter != null){
						for (TableDesignDetails after : tableDesignDetailsAfter) {
							if(before.getColumnId().equals(after.getColumnId())){
								if(before.hasBeenChangedBasetype(after)){
									listColumnIdSqlDesign.add(before.getColumnId());
									listColumnChangeType.add(before);
									forKey.add(after);
								}
								check = true;
								break;
							}
						}
					}
					if(!check){
						listColumnIdSqlDesign.add(before.getColumnId());
						listColumnDelete.add(before);
					}
				}else{
					listColumnIdSqlDesign.add(before.getColumnId());
					listColumnDelete.add(before);
				}
			}
			
			if(listColumnIdSqlDesign.size() > 0){
				design.setListSqlDesigns(tableDesignRepository.getAllSqlAffected(listColumnIdSqlDesign, projectId));
			}
			List<BusinessLogic> logics = new ArrayList<BusinessLogic>();
			
			if(listColumnChangeType.size() > 0){
				logics.addAll(tableDesignRepository.getAllBLogicAffectedByColumnIds(listColumnChangeType));
				List<Long> listColumnId = new ArrayList<Long>();
				for (TableDesignDetails designDetails : listColumnChangeType) {
					listColumnId.add(designDetails.getColumnId());
				}
				List<TableDesignForeignKey> tableDesignForeignKey = tableDesignForeignKeyRepository.findForeignKeyByListColumn(listColumnId);
				boolean flagHasAffect = false;
				for (TableDesignDetails designDetails : forKey) {
					for (TableDesignForeignKey key : tableDesignForeignKey) {
						TableDesignDetails details = tableDesignDetailRepository.findOne(key.getFromColumnId());
						if(!DataTypeUtils.equals(details.getGroupBaseTypeId(), designDetails.getGroupBaseTypeId())){
							flagHasAffect = true;
						}
					}
				}
				if(flagHasAffect){
					design.setListTableDesignForeignKeyAffect(tableDesignForeignKey);
				}
			}
			
			if(listColumnDelete.size() > 0){
				logics.addAll(tableDesignRepository.getAllBLogicAffectedByTableDesignId(design.getTableDesignId()));
//				List<Long> listColumnId = new ArrayList<Long>();
//				for (TableDesignDetails designDetails : listColumnDelete) {
//					listColumnId.add(designDetails.getColumnId());
//				}
//				tableDesignForeignKeys.addAll(tableDesignForeignKeyRepository.findForeignKeyByListColumn(listColumnId));
			}
			design.setListBusinessLogics(logics);
		}
	}
	
	/**
	 * 
	 */
	@Override
	public void impactChangeDesignSqlDesignByDeleteTable(List<TableDesign> tableDesign, Long accountId, Long projectId){
		for (TableDesign design : tableDesign) {
			List<SqlDesign> sqlDesigns = design.getListSqlDesigns();
			List<ProblemList> problemLists = new ArrayList<ProblemList>();
			List<Long> listFromResouceId = new ArrayList<Long>();
			List<Integer> listFromResourceType = new ArrayList<Integer>();
			List<Long> moduleId = new ArrayList<Long>();
			ProblemList problemList = null;
			
			if(!design.getIsDeleted()){
				if(FunctionCommon.isNotEmpty(sqlDesigns)){
					// Change Status SQL Design, BLogic Design, Module, Project
					sqlDesignRepository.modifyAffectChangeDesign(sqlDesigns);
					moduleId.addAll(this.prepareModuleId(null, sqlDesigns));
				}
				moduleRepository.switchDesignStatusToUnderDesign(moduleId);
				projectRepository.modifyAffectChangeDesign(projectId);
				if(problemLists.size() > 0){
					problemListRepository.deleteFromResourceTypeOfTblDesign(listFromResourceType, listFromResouceId);
					problemListRepository.multiRegisterProblem(problemLists);
				}
			}else {
				Timestamp currentTime = FunctionCommon.getCurrentTime();
				
				// Insert proplem Sql Design 
				if(sqlDesigns != null && sqlDesigns.size() > 0){
					for (SqlDesign sql : sqlDesigns) {				
						problemList = new ProblemList();
						problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0014, design.getTableName()));
						problemList.setResourceType(DbDomainConst.ResourceType.SQL_DESIGN);
						problemList.setResourceId(sql.getSqlDesignId());
						problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
						problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
						problemList.setModuleId(sql.getModuleId());
						problemList.setUrlId(DbDomainConst.ResourceURL.URL_SQL_DESIGN);
						problemList.setFromResourceType(DbDomainConst.FromResourceType.TABLE_DESIGN_DELETE);
						problemList.setProjectId(projectId);
						problemList.setCreatedBy(accountId);
						problemList.setCreatedDate(currentTime);
						problemLists.add(problemList);
					}
				}
				if(problemLists.size() > 0){
					problemListRepository.multiRegisterProblem(problemLists);
				}
			}
		}
	}
	
	/**
	 * return Map contain item type with label multi language
	 * @return
	 * @author dungnn1
	 */
	private void initItemtypeCodelist() {
		mapItemtype = new HashMap<Integer, String>();
		
		for(String key : itemTypeCodeList.asMap().keySet()){
			/*try {
				mapItemtype.put(Integer.parseInt(key), MessageUtils.getMessage(itemTypeCodeList.asMap().get(key)));
			} catch (Exception ex ) {
				//if don't configuration then show message code
				mapItemtype.put(Integer.parseInt(key), itemTypeCodeList.asMap().get(key));
			}*/
			mapItemtype.put(Integer.parseInt(key), itemTypeCodeList.asMap().get(key));
		}
	}
	
	/**
	 * 
	 * @param tableDesignDetails
	 */
	private void clearValueDomainType(TableDesignDetails tableDesignDetails){
		tableDesignDetails.setConstrainsType(null);
		tableDesignDetails.setDatasourceId(null);
		tableDesignDetails.setDatasourceType(null);
		tableDesignDetails.setFmtCode(null);
		tableDesignDetails.setDefaultValue(null);
		tableDesignDetails.setOperatorCode(null);
		tableDesignDetails.setMaxVal(null);
		tableDesignDetails.setMaxVal(null);
		tableDesignDetails.setMaxlength(null);
		tableDesignDetails.setDecimalPart(null);
	}

	/**
	 * 
	 * @param tableDesignId
	 * @param tableDesignDetails
	 * @param listUserDefineCodelistDetails
	 * @param validationRules
	 * @param status
	 */
	private void settingRowBeforeInsert(Long tableDesignId,List<TableDesignDetails> tableDesignDetails,List<UserDefineCodelistDetails> listUserDefineCodelistDetails,List<ValidationRule> validationRules, boolean status, boolean isExport, Long projectId) {
		//get all datatype
		List<Basetype> listOfBasetype = domainRepository.getAllBasetype(projectId);
		this.setBaseTypeForDomainType(tableDesignDetails, listOfBasetype);
		Long sequenceTableDesignDetail = tableDesignDetailRepository.getSequencesTableDesignDetails(tableDesignDetails.size() - 1);
		Long startSequence;
		startSequence = sequenceTableDesignDetail - (tableDesignDetails.size() - 1);
		for (TableDesignDetails tableDetail : tableDesignDetails) {
			tableDetail.setStatusImport("Add New");
			if(tableDetail.getCommonColumn() == 1){
				tableDetail.setIsMandatory(1);
			}
			tableDetail.setTableDesignId(tableDesignId);
			tableDetail.setDisplayType(DbDomainConst.DisplayType.USED);
			// Set java type
			tableDetail.setJavaType(BusinessDesignHelper.convertJavaTypeFromBaseType(tableDetail.getBaseType()));
			
			if(tableDetail.getKeyType().substring(tableDetail.getKeyType().length() - 1).equals("1")){
				tableDetail.setIsMandatory(1);
			}
			
			

			if(YesNoFlg.YES.equals(tableDetail.isPrimaryKey()) && tableDetail.getAutoIncrementFlag() == 1){
				tableDetail.setIsMandatory(YesNoFlg.YES);

				//DungNN - 20151026 - if has auto increment then don't set display type
				tableDetail.setDisplayType(DbDomainConst.DisplayType.UNUSED);
			}
			
			// Set display type where column isMadatory
			if(YesNoFlg.YES.equals(tableDetail.getIsMandatory())){
				if(tableDetail.getAutoIncrementFlag() == 0 || StringUtils.isNotBlank(tableDetail.getDefaultValue())){
					tableDetail.setDisplayType(DbDomainConst.DisplayType.USED);
				}
			}

			//DungNN 20150927 - recalculation for data type and group base type
			for (Basetype basetype: listOfBasetype) {
				if (basetype.getBasetyeId().equals(tableDetail.getDataType())) {
					tableDetail.setDataTypeFlg(basetype.getDataTypeFlg());
					tableDetail.setGroupBaseTypeId(basetype.getGroupBaseTypeId());
					break;
				}
			}
			
			TableDesignUtil.initDefaultForItemType(tableDetail, validationRules);
			
			if(DbDomainConst.DataTypeFlag.DOMAIN_DATA.equals(tableDetail.getDataTypeFlg())){
				this.clearValueDomainType(tableDetail);
			}
			insertUserDefineCodelist(listUserDefineCodelistDetails, tableDetail);
			if(!isExport){
				tableDetail.setColumnId(startSequence);
			}
			startSequence++;
		}
		
		tableDesignDetailRepository.registerMultiTableDesignDetailsWithSequence(tableDesignDetails);
		
		if(listUserDefineCodelistDetails.size() > 0){
			userDefineCodelistRepository.createUserDefineCodelistDetails(listUserDefineCodelistDetails);
		}
	}

	/**
	 * 
	 * @param listUserDefineCodelistDetails
	 * @param td
	 */
	private void insertUserDefineCodelist(List<UserDefineCodelistDetails> listUserDefineCodelistDetails, TableDesignDetails td) {
		UserDefineCodelist userDefineCodelist;
		UserDefineCodelistDetails userDefineCodelistDetails;
		Integer supportOptionFlg = td.getSupportOptionFlg();
		
		if(supportOptionFlg !=null){
			userDefineCodelist = new UserDefineCodelist();
			userDefineCodelist.setSupportOptionFlg(supportOptionFlg);
			userDefineCodelistRepository.createUserDefineCodelist(userDefineCodelist);
			
			Long codelistId = userDefineCodelist.getCodelistId();
			// Set UserDefineId for Column of Table 
			td.setDatasourceId(codelistId);
			
			String [] userDefineValue = td.getUserDefineValue().split(INVERSE_BULLET_SYSBOL);
			Integer itemSeqNo = 0;
			for (int i = 0; i < userDefineValue.length; i++) {
				String [] userDefineValueRow = userDefineValue[i].split(REPLACEMENT_CHARACTER_SYSBOL);
				userDefineCodelistDetails = new UserDefineCodelistDetails();
				userDefineCodelistDetails.setCodelistId(codelistId);
				userDefineCodelistDetails.setItemSeqNo(itemSeqNo++);
				if(supportOptionFlg.equals(0)){
					userDefineCodelistDetails.setCodelistName(null);
				} else {
					userDefineCodelistDetails.setCodelistName(userDefineValueRow[2]);
				}
				userDefineCodelistDetails.setCodelistValue(userDefineValueRow[1]);
				userDefineCodelistDetails.setDefaultFlg(Integer.parseInt(userDefineValueRow[0]));
				listUserDefineCodelistDetails.add(userDefineCodelistDetails);

				if(userDefineValueRow[0] == "1"){
					td.setDefaultValue(userDefineValueRow[1]);
				}
			}
		}
	}

	/**
	 * @param tableDesign
	 * @param tableDesignId
	 */
	@Override
	public void insertKey(TableDesign tableDesign, Long projectId) {
		
		List<TableDesignDetails> table = tableDesignDetailRepository.findAllByTableDesign(tableDesign.getTableDesignId());
		
		// get key from db
		List<TableDesignKey> listTableDesignKeyFromDB = tableDesignKeyRepository.getAllByProjectAndSubArea(projectId, DomainDatatypeConst.SEARCH_ALL_TABLE_DESIGN);
		
		// key insert
		List<TableDesignKey> listTableDesignKeyInsert = tableDesign.getListTableDesignKey();
		
		ResultMessages resultMessages = ResultMessages.error();
		
		// Insert to TABLE_DESIGN_KEY
		TableDesignKey tableDesignKey = null;
		
		if(FunctionCommon.isNotEmpty(listTableDesignKeyInsert)){
			
			//prepare for all key
			for (TableDesignKey key : listTableDesignKeyFromDB) {
				//check exists key code
				if (isDuplicateKeyName(key.getCode(), key.getKeyId(), listTableDesignKeyInsert)) {
					throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0036, key.getCode()));
				} 
			}
			
			List<TableDesignKey> tableDesignKeys = new ArrayList<TableDesignKey>();
			
			for (TableDesignKey key : listTableDesignKeyInsert) {
				
				String keyItems[] = key.getStrKeyItems().split(REPLACEMENT_CHARACTER_SYSBOL);
				if(keyItems.length > 2){
					tableDesignKey = new TableDesignKey();
					tableDesignKey.setCode(keyItems[1]);
					tableDesignKey.setType(Integer.parseInt(keyItems[0]));
					tableDesignKey.setTableDesignId(tableDesign.getTableDesignId());
					tableDesignKeys.add(tableDesignKey);
				}
			}
	
			if(FunctionCommon.isNotEmpty(tableDesignKeys)){
				if(FunctionCommon.isNotEmpty(tableDesignKeys)){
					tableDesignKeyRepository.createArray(tableDesignKeys);
				}
				Long getMinValueTblDesignKeyByTblId = tableDesignKeyRepository.selectMinTblDesignKeyValue(tableDesign.getTableDesignId());
				List<TableDesignKeyItem> tableDesignKeyItems = new ArrayList<TableDesignKeyItem>();
				
		
				// Insert to TABLE_DESIGN_KEY_ITEMS
				TableDesignKeyItem tableDesignKeyItem = null;
				
				for (TableDesignKey key : listTableDesignKeyInsert) {
					String keyItems[] = key.getStrKeyItems().split(REPLACEMENT_CHARACTER_SYSBOL);
					for (int i = 2; i < keyItems.length; i++) {
						for (TableDesignDetails td : table) {
							if(td.getName().equals(keyItems[i].split(INVERSE_BULLET_SYSBOL)[1])){
								tableDesignKeyItem = new TableDesignKeyItem();
								tableDesignKeyItem.setColumnId(td.getColumnId());
								tableDesignKeyItem.setTableDesignKeyId(getMinValueTblDesignKeyByTblId);
								tableDesignKeyItems.add(tableDesignKeyItem);
							}
						}
					}
					getMinValueTblDesignKeyByTblId++;
				}
				if(FunctionCommon.isNotEmpty(tableDesignKeyItems)){
					tableDesignKeyRepository.insertArray(tableDesignKeyItems);
				}
			}
		}
		
		
		// Insert to TABLE_DESIGN_FOREIGN_KEY
		if(FunctionCommon.isNotEmpty(tableDesign.getTableDesignForeignKeys())){
			List<TableDesignForeignKey> tableDesignForeignKeys = new ArrayList<TableDesignForeignKey>();
			TableDesignForeignKey designForeignKey = null;
			int countForeignKey = 1;
			List<Long> toColumnId = new ArrayList<Long>();
			
			// Get collection column id
			for (TableDesignForeignKey foreignKey : tableDesign.getTableDesignForeignKeys()) {
				toColumnId.add(foreignKey.getToColumnId());
			}
			List<TableDesignDetails> getAllInformationByTableDesign = tableDesignDetailRepository.findDetailsByToColumnForeignKey(toColumnId);
			
			for (TableDesignForeignKey foreignKey : tableDesign.getTableDesignForeignKeys()) {
				designForeignKey = new TableDesignForeignKey();	
				designForeignKey.setFromTableId(tableDesign.getTableDesignId());
				designForeignKey.setToTableId(foreignKey.getToTableId());
				designForeignKey.setToColumnId(foreignKey.getToColumnId());
				designForeignKey.setForeignKeyCode(foreignKey.getForeignKeyCode());
				
				for (TableDesignDetails td : table) {
					if(td.getName().equals(foreignKey.getFromColumnName())){
						designForeignKey.setFromColumnId(td.getColumnId());
					}
				}
				
				tableDesignForeignKeys.add(designForeignKey);
				
				if(tableDesignForeignKeyRepository.checkDuplicateKeyCode(projectId, designForeignKey.getForeignKeyCode(), designForeignKey.getFromTableId()) > 0 ){
					resultMessages.add(CommonMessageConst.ERR_SYS_0041, MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0027), countForeignKey, null);
//					resultMessages.add(CommonMessageConst.ERR_SYS_0041,new Object[] { MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0027), countForeignKey }, null);
				}
				
				if(!this.isDataTypeEqual(getAllInformationByTableDesign, foreignKey)){
					resultMessages.add(TableDesignMessageConst.ERR_TABLEDESIGN_0002, countForeignKey, null);
				}
				countForeignKey++;
			}
			
			if (resultMessages.isNotEmpty()) {
				throw new BusinessException(resultMessages);
			}else{
				if(FunctionCommon.isNotEmpty(tableDesignForeignKeys)){
					tableDesignForeignKeyRepository.createArray(tableDesignForeignKeys);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param allTable
	 * @param listForeignKey
	 * @return
	 */
	private boolean isDataTypeEqual(List<TableDesignDetails> getAllInformationByTableDesign, TableDesignForeignKey foreignKey){
		
		for (TableDesignDetails tableDesignDetails : getAllInformationByTableDesign) {
			if(foreignKey.getToColumnId().equals(tableDesignDetails.getColumnId()) && DataTypeUtils.equals(tableDesignDetails.getGroupBaseTypeId(), foreignKey.getForeignKeyType())){
				return true;
			}
		}
		return false;
	}

	/**
	 * DungNN modify - change delete table and every one reference to
	 * TableDesignRepository.xml
	 * 
	 * @param TablesToDelete
	 * @author dungnn1
	 */
	private void deleteTableDesign(List<TableDesign> listOfTablesToDelete) {
		try {
			List<TableDesignDetails> designDetails = tableDesignDetailRepository.findAllByTableDesign(listOfTablesToDelete.get(0).getTableDesignId());
			
			List<Long> listOfId = new ArrayList<Long>();
			for (TableDesignDetails tddelete : designDetails) {
				listOfId.add(tddelete.getColumnId());
			}
			this.updateForeignKeyWhenDeleteColumns(listOfId);
			
			// Delete all table and element of this
			tableDesignRepository.delete(listOfTablesToDelete.get(0).getTableDesignId());
			//this.processAffectChangeDesign(listOfTablesToDelete.get(0));
		} catch (Exception ex) {
			//if missing check fk then put out message
			if (ex.getMessage().contains("violates foreign key constraint")) {
				ResultMessages resultMessages = ResultMessages.error();
				throw new BusinessException(resultMessages.add(CommonMessageConst.ERR_SYS_0097));
			}
			throw ex;
		}
	}
	
	/**
	 * 
	 * @param tableDesingDetails
	 * @param autocompleteInput
	 */
	private void setValueDataSourceId(TableDesignDetails tableDesingDetails, AutocompleteInput autocompleteInput, Long projectId){
		autocompleteInput = new AutocompleteInput();
		Autocomplete autocomplete = null;
		if(DbDomainConst.DatasourceType.CODELIST.equals(tableDesingDetails.getDatasourceType())){
			autocompleteInput.setArg01(projectId.toString());
			autocompleteInput.setArg02(tableDesingDetails.getDatasourceId().toString());
			autocompleteInput.setArg03(tableDesingDetails.getDefaultValue());
			if(!FunctionCommon.isEmpty(tableDesingDetails.getDatasourceId())){				
				autocomplete = tableDesignRepository.getAllCodeList(autocompleteInput);
				if(autocomplete != null){
					tableDesingDetails.setCodelistCodeAutocomplete(autocomplete.getOptionLabel());
				}
				autocomplete = null;
				if(!StringUtils.isEmpty(tableDesingDetails.getDefaultValue())){
					autocomplete = tableDesignRepository.getAllCodeListDetail(autocompleteInput);
				}
				if(autocomplete != null){
					tableDesingDetails.setCodelistDefaultAutocomplete(autocomplete.getOptionLabel());		
				}
			}
		} else if (DbDomainConst.DatasourceType.SQL_BUILDER.equals(tableDesingDetails.getDatasourceType())) {
			autocompleteInput.setArg01(projectId.toString());
			autocompleteInput.setArg02(tableDesingDetails.getDatasourceId().toString());
			autocompleteInput.setArg03(tableDesingDetails.getDatasourceType().toString());
			autocomplete = tableDesignRepository.getAllSqlBuilderACLoadModify(autocompleteInput);
			if(autocomplete != null){
				tableDesingDetails.setSqlCodeAutocomplete(autocomplete.getOptionLabel());
			}
		}else if(DbDomainConst.DatasourceType.SQL_BUILDER_IS_AUTOCOMPLETE.equals(tableDesingDetails.getDatasourceType())){
			autocompleteInput.setArg01(projectId.toString());
			autocompleteInput.setArg02(tableDesingDetails.getDatasourceId().toString());
			autocompleteInput.setArg03(tableDesingDetails.getDatasourceType().toString());
			autocomplete = tableDesignRepository.getAllAutocompleteACLoadModify(autocompleteInput);
			if(autocomplete != null){
				tableDesingDetails.setSqlCodeAutocomplete(autocomplete.getOptionLabel());
			}
		}
	}
	
	/**
	 * 
	 * @param businessLogics
	 * @param sqlDesigns
	 * @return
	 */
	private List<Long> prepareModuleId(List<BusinessLogic> businessLogics, List<SqlDesign> sqlDesigns){
		
		/*@SuppressWarnings("unused")*/
		/*List<Long> module = null;*/
		HashSet<Long> set = null;
		List<Long> listModuleId = new ArrayList<Long>();
		
		if(businessLogics != null){
			for (BusinessLogic elm : businessLogics) {
				listModuleId.add(elm.getModuleId());
			}
		}
		if(sqlDesigns != null){
			for (SqlDesign elm : sqlDesigns) {
				listModuleId.add(elm.getModuleId());
			}
		}
		set = new HashSet<>(listModuleId);
		//module = new ArrayList<>(set);
		
		return new ArrayList<>(set);
	}
	
	/**
	 * 
	 * @param tableDesign
	 */
	private void setBaseTypeForDomainType(List<TableDesignDetails> tableDesignDetails, List<Basetype> listOfBasetype){
		if(FunctionCommon.isNotEmpty(tableDesignDetails)){
			for (TableDesignDetails details : tableDesignDetails) {
				for (Basetype basetype : listOfBasetype) {
					if(details.getDataType().equals(basetype.getBasetyeId())){
						details.setBaseType((int) (long)basetype.getPrimitiveId());
						break;
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param table_design_details
	 */
	private void setPatternFormatForDomainType(List<TableDesignDetails> tableDesignDetails, AccountProfile account){
		if(FunctionCommon.isNotEmpty(tableDesignDetails)){
			for (TableDesignDetails details : tableDesignDetails) {
				if (StringUtils.isNotBlank(details.getMinVal()) || StringUtils.isNotBlank(details.getMaxVal()) || StringUtils.isNotBlank(details.getDefaultValue())) {
					// Check date, time, date time
					if (DataTypeUtils.equals(DbDomainConst.PhysicalDataTypePrimitive.DATE, details.getGroupBaseTypeId())) {
						details.setPatternFormat(DateUtils.getPatternDate(account.getDateFormat()));
					} else if (DataTypeUtils.equals(DbDomainConst.PhysicalDataTypePrimitive.TIME, details.getGroupBaseTypeId())) {
						details.setPatternFormat(DateUtils.getPatternTime(account.getTimeFormat()));
					} else if (DataTypeUtils.equals(DbDomainConst.PhysicalDataTypePrimitive.DATETIME, details.getGroupBaseTypeId())) {
						details.setPatternFormat(DateUtils.getPatternDateTime(account.getDateTimeFormat()));
					}
				}
			}
		}
	}
	
	/**
	 * check name or code of table existed
	 * 
	 * @param key
	 * @param value
	 * @param map
	 * @return
	 */
	private boolean isDuplicateKeyName(String code, Long id, List<TableDesignKey> listTableDesignKeyInsert) {
		
		for (TableDesignKey tableDesignKey : listTableDesignKeyInsert) {
			String keyItems[] = tableDesignKey.getStrKeyItems().split(REPLACEMENT_CHARACTER_SYSBOL);
			
			if(keyItems[1].equals(code)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param tableDesignDetails
	 */
	private void processAffectChangeDataTypeForeignKey(List<TableDesignDetails> tableDesignDetails, List<Basetype> listOfBasetype, CommonModel common){
		
		if(tableDesignDetails.size() == 0) return;
		
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		List<ProblemList> problemLists = new ArrayList<ProblemList>();
		List<Integer> lstFromResourceType = new ArrayList<Integer>();
		List<Long> lstFromResouceId = new ArrayList<Long>();
		List<Long> listColumnId = new ArrayList<Long>();
		for (TableDesignDetails designDetails : tableDesignDetails) {
			listColumnId.add(designDetails.getColumnId());
		}
		List<TableDesignForeignKey> tableDesignForeignKeys = tableDesignForeignKeyRepository.findForeignKeyByListColumn(listColumnId);
		
		for (TableDesignDetails designDetails : tableDesignDetails) {
			for (TableDesignForeignKey tableDesignForeignKey : tableDesignForeignKeys) {
				if(tableDesignForeignKey.getToColumnId().equals(designDetails.getColumnId())){
					TableDesignDetails details = tableDesignDetailRepository.findOne(tableDesignForeignKey.getFromColumnId());
					List<TableDesignDetails> designDetailsTemp = new ArrayList<TableDesignDetails>();
					designDetailsTemp.add(details);
					this.setBaseTypeForDomainType(designDetailsTemp, listOfBasetype);
					String oldDataType  = BusinessDesignHelper.getDataTypeStr(BusinessDesignHelper.convertJavaTypeFromBaseType( designDetailsTemp.get(0).getBaseType()), false);
					String newDataType  = BusinessDesignHelper.getDataTypeStr(BusinessDesignHelper.convertJavaTypeFromBaseType( designDetails.getBaseType()), false);
					if(!DataTypeUtils.equals(details.getGroupBaseTypeId(), designDetails.getGroupBaseTypeId())){
						ProblemList problem = new ProblemList();
						problem.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0020, tableDesignForeignKey.getForeignKeyCode(),oldDataType,newDataType));
						problem.setResourceType(DbDomainConst.ResourceType.TABLE_DESIGN);
						problem.setResourceId(details.getColumnId());
						problem.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
						problem.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
						problem.setUrlId(DbDomainConst.ResourceURL.URL_TABLE_DESIGN_DETAILS);
						problem.setFromResourceType(DbDomainConst.FromResourceType.TABLE_DESIGN_DETAIL_CHANGE_DATATYPE_FOREIGN_KEY);
						problem.setFromResourceId(designDetails.getColumnId());
						problem.setCreatedBy(common.getCreatedBy());
						problem.setCreatedDate(currentTime);
						problem.setProjectId(common.getWorkingProjectId());
						problemLists.add(problem);
					}
					lstFromResourceType.add(DbDomainConst.FromResourceType.TABLE_DESIGN_DETAIL_CHANGE_DATATYPE_FOREIGN_KEY);
					lstFromResouceId.add(designDetails.getColumnId());
				}
			}
		}
		problemListRepository.deleteFromResourceTypeOfTblDesign(lstFromResourceType, lstFromResouceId);
		if(problemLists.size() > 0){
			problemListRepository.multiRegisterProblem(problemLists);
		}
	}
	
	/**
	 * 
	 * @param tableDesignDetails
	 */
	private void processSeqCode(String tableCode, List<TableDesignDetails> tableDesignDetails, Project project){
		
		// Set seq when column has auto increment is true
		for (TableDesignDetails tableDesignDetail : tableDesignDetails) {
			tableDesignDetail.setSeqCode(org.apache.commons.lang3.StringUtils.EMPTY);
			
			if(tableDesignDetail.getAutoIncrementFlag() == 1){
				tableDesignDetail.setSeqCode(this.genSeqColumn(tableCode, tableDesignDetail, project));
			}else{
				String keyType = tableDesignDetail.getKeyType();
				if(keyType.charAt(4) == '1'){
					if(DbDomainConst.BaseType.BIGSERIAL_BASETYPE == tableDesignDetail.getDataType().intValue() ||
							DbDomainConst.BaseType.SERIAL_BASETYPE == tableDesignDetail.getDataType().intValue()){
						tableDesignDetail.setSeqCode(this.genSeqColumn(tableCode, tableDesignDetail, project));
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param tableCode
	 * @param tableDetail
	 * @param project
	 * @return
	 */
	private String genSeqColumn(String tableCode, TableDesignDetails tableDetail, Project project){
		AccountProfile accountProfile = systemService.getDefaultProfile();
		String dataType = String.valueOf(project.getDbType());
		setMaxLengOfCode(accountProfile.getSqlCodeMaxLengthByDbType(dataType));
		GenerateUniqueKey generateUniqueKey = new GenerateUniqueKey(DbDomainConst.MAX_LENGTH_OF_NAME, maxLengOfCode);
		return generateUniqueKey.calculateCodeForManual(MessageFormat.format(TableDesignUtil.SEQ_TEMPLATE, tableCode, tableDetail.getCode(), GenerateUniqueKey.generateRandomInteger(), true));
	}
	
	/**
	 * 
	 * @param listColumnId
	 */
	@Override
	public void updateForeignKeyWhenDeleteColumns(List<Long> listColumnId){
		if(FunctionCommon.isNotEmpty(listColumnId)){
			List<TableDesignForeignKey> tableDesignForeignKeys = tableDesignForeignKeyRepository.findForeignKeyByListColumn(listColumnId);
			List<Long> fromTableId = new ArrayList<Long>();
			List<Long> fromColumnId = new ArrayList<Long>();
			for (TableDesignForeignKey designForeignKey : tableDesignForeignKeys) {
				fromTableId.add(designForeignKey.getFromTableId());
				fromColumnId.add(designForeignKey.getFromColumnId());
			}
			
			List<TableDesignForeignKey> toColumn = tableDesignForeignKeyRepository.findForeignKeyByListToColumn(listColumnId);
			
			if(FunctionCommon.isEmpty(toColumn)){
				List<TableDesignDetails> tableDesignDetails = tableDesignDetailRepository.findAllByTableDesignAndColumn(fromTableId, fromColumnId);
				if(FunctionCommon.isNotEmpty(tableDesignDetails)){
					for (TableDesignDetails designDetails : tableDesignDetails) {
						StringBuilder builder = new StringBuilder(designDetails.getKeyType());
						builder.setCharAt(3, '0');
						designDetails.setKeyType(builder.toString());
					}
					tableDesignDetailRepository.updateForeignTableDesignDetails(tableDesignDetails);
					tableDesignForeignKeyRepository.deleteByToTableAndColumn(fromTableId, fromColumnId);
				}
			}
		}
	}

	public Integer getMaxLengOfCode() {
		return maxLengOfCode;
	}

	public void setMaxLengOfCode(Integer maxLengOfCode) {
		this.maxLengOfCode = maxLengOfCode;
	}

	@Override
	public SqlDesignOutput[] getSqlDesignOutputsForAdvanceSetting(Long sqlDesignId) {
		SqlDesignOutput[] sqlDesignOutputs = sqlDesignOutputRepository.findAllBySqlDesignId(sqlDesignId);
		
		// Set level
		if (sqlDesignOutputs.length == 0) {
			return null;
		}
		// set level of input bean
		Map<Long, String> mapTableIndex = new HashMap<Long, String>();
		Map<String, Integer> mapSequence = new HashMap<String, Integer>();
		for (int i = 0; i < sqlDesignOutputs.length; i++) {
			String currentGroup = "";
			if (sqlDesignOutputs[i].getSqlDesignOutputParentId() != null) {
				currentGroup = mapTableIndex.get(sqlDesignOutputs[i].getSqlDesignOutputParentId());
			}
			
			sqlDesignOutputs[i].setGroupId(currentGroup);
			int maxIndex = mapSequence.getOrDefault(sqlDesignOutputs[i].getGroupId(), 0);
			maxIndex++;
			if (sqlDesignOutputs[i].getSqlDesignOutputParentId() == null) {
				sqlDesignOutputs[i].setGroupIndex(String.valueOf(maxIndex));
			} else {
				sqlDesignOutputs[i].setGroupIndex(currentGroup + "." + maxIndex);
			}
			
			mapTableIndex.put(sqlDesignOutputs[i].getSqlDesignOutputId(), sqlDesignOutputs[i].getGroupIndex());
			mapSequence.put(sqlDesignOutputs[i].getGroupId(), maxIndex);
		}
		
		return sqlDesignOutputs;
	}
	
	/**
	 * Check condition for insert, modify into field[pattern_format] of table design details
	 * 
	 * @return void
	 */
	private void checkConditionPatternFormat(TableDesignDetails tableDesignDetails, TableDesignDetails tableDesignDetailsAfter, CommonModel common) {
		// Check blank
		if (StringUtils.isNotBlank(tableDesignDetails.getMinVal()) || StringUtils.isNotBlank(tableDesignDetails.getMaxVal()) || StringUtils.isNotBlank(tableDesignDetails.getDefaultValue())
				|| StringUtils.isNotBlank(tableDesignDetailsAfter.getMinVal()) || StringUtils.isNotBlank(tableDesignDetailsAfter.getMaxVal()) || StringUtils.isNotBlank(tableDesignDetailsAfter.getDefaultValue())) {
			// Check date, time, date time
			if (DataTypeUtils.equals(DbDomainConst.BaseType.DATE_BASETYPE, tableDesignDetails.getBaseType())) {
				tableDesignDetails.setPatternFormat(DateUtils.getPatternDate(common.getAccountProfile().getDateFormat()));
			} else if (DataTypeUtils.equals(DbDomainConst.BaseType.TIME_BASETYPE, tableDesignDetails.getBaseType())) {
				tableDesignDetails.setPatternFormat(DateUtils.getPatternTime(common.getAccountProfile().getTimeFormat()));
			} else if (DataTypeUtils.equals(DbDomainConst.BaseType.DATETIME_BASETYPE, tableDesignDetails.getBaseType())) {
				tableDesignDetails.setPatternFormat(DateUtils.getPatternDateTime(common.getAccountProfile().getDateTimeFormat()));
			}
		} 
	}

	@Override
	public TableDesignDetails findOneTableDesignDetail(Long columnId) {
		return this.tableDesignDetailRepository.findOne(columnId);
	}
	
}
