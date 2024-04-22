package org.terasoluna.qp.domain.service.domaindesign;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.TerasolunaQPValidationConst;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.FunctionMasterUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.DomainDesignMessageConst;
import org.terasoluna.qp.app.message.ProblemListMessageConst;
import org.terasoluna.qp.domain.model.Autocomplete;
import org.terasoluna.qp.domain.model.Basetype;
import org.terasoluna.qp.domain.model.BusinessLogic;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.DomainDesign;
import org.terasoluna.qp.domain.model.ProblemList;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.model.UserDefineCodelist;
import org.terasoluna.qp.domain.model.UserDefineCodelistDetails;
import org.terasoluna.qp.domain.model.ValidationRule;
import org.terasoluna.qp.domain.repository.domaindatatype.DomainDatatypeRepository;
import org.terasoluna.qp.domain.repository.domaindesign.DomainDesignCriteria;
import org.terasoluna.qp.domain.repository.domaindesign.DomainDesignRepository;
import org.terasoluna.qp.domain.repository.problemlist.ProblemListRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignDetailRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignRepository;
import org.terasoluna.qp.domain.repository.tabledesign.UserDefineCodelistRepository;
import org.terasoluna.qp.domain.service.common.AutocompleteInput;
import org.terasoluna.qp.domain.service.graphicdatabasedesign.DataType;
import org.terasoluna.qp.domain.service.graphicdatabasedesign.GroupDataTypeDB;
import org.terasoluna.qp.domain.service.graphicdatabasedesign.TableDesignUtil;
import org.terasoluna.qp.domain.service.project.ProjectService;
import org.terasoluna.qp.domain.service.tabledesign.TableDesignService;

@Service
@Transactional
public class DomainDesignServiceImpl implements DomainDesignService {
	
	private static final String INVERSE_BULLET_SYSBOL = "◘";
	
	private static final String REPLACEMENT_CHARACTER_SYSBOL = "�";

	@Inject
	DomainDesignRepository domainDesignRepository;

	@Inject
	DomainDatatypeRepository domainDatatypeRepository;
	
	@Inject
	UserDefineCodelistRepository userDefineCodelistRepository;
	
	@Inject
	TableDesignRepository tableDesignRepository; 
	
	@Inject
	TableDesignDetailRepository tableDesignDetailRepository; 
	
	@Inject
	ProblemListRepository problemListRepository;
	
	@Inject
	TableDesignService tableDesignService;
	
	@Inject
	ProjectService projectService;

	@Override
	public Page<DomainDesign> findPageByCriteria(DomainDesignCriteria domainCritefia, Pageable pageable) {
		long totalCount = domainDesignRepository.countByCriteria(domainCritefia);

		List<DomainDesign> articles;
		if (0 < totalCount) {
			articles = domainDesignRepository.findPageByCriteria(domainCritefia, pageable);
		} else {
			articles = Collections.emptyList();
		}
		Page<DomainDesign> page = new PageImpl<DomainDesign>(articles, pageable, totalCount);

		return page;
	}
	
	@Override
	public DomainDesign findOne(Long domainId, CommonModel common, boolean checkDesignStatus) {
		DomainDesign domaindesign = domainDesignRepository.findOne(domainId);
		if (domaindesign == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(CommonMessageConst.TQP_DOMAINDESIGN)));
		}
		projectService.validateProject(domaindesign.getProjectId(), common.getCreatedBy(), common.getWorkingProjectId(), checkDesignStatus);
		return domaindesign;
	}
	
	@Override
	public void loadUserDefineCodelist(DomainDesign domainDesign, CommonModel common){
		
		// Get User Define Codelist
		List<UserDefineCodelistDetails> userDefineCodelistDetails = userDefineCodelistRepository.getAllByDomainDesign(domainDesign);
		StringBuilder userDefineValue = null;
		
		userDefineValue = new StringBuilder();
		for (UserDefineCodelistDetails codelistDetails : userDefineCodelistDetails) {
			domainDesign.setSupportOptionFlg(codelistDetails.getSupportOptionFlg());
			userDefineValue.append(codelistDetails.getDefaultFlg());
			userDefineValue.append(REPLACEMENT_CHARACTER_SYSBOL);
			userDefineValue.append(codelistDetails.getCodelistValue());
			userDefineValue.append(REPLACEMENT_CHARACTER_SYSBOL);
			userDefineValue.append(codelistDetails.getCodelistName());
			userDefineValue.append(INVERSE_BULLET_SYSBOL);
		}
		domainDesign.setUserDefineValue(userDefineValue.toString());
		
		
		AutocompleteInput autocompleteInput = new AutocompleteInput();
		Autocomplete autocomplete = null;
		
		if(DbDomainConst.DatasourceType.CODELIST.equals(domainDesign.getDatasourceType())){
			if(domainDesign.getDatasourceId() != null){
				
				autocompleteInput.setArg01(common.getWorkingProjectId().toString());
				autocompleteInput.setArg02(domainDesign.getDatasourceId().toString());
				if(domainDesign.getDefaultValue() != null){
					autocompleteInput.setArg03(domainDesign.getDefaultValue());
				}
				autocomplete = tableDesignRepository.getAllCodeList(autocompleteInput);
				
				if(autocomplete != null){
					domainDesign.setCodelistCodeAutocomplete(autocomplete.getOptionLabel());
				}
				
				if(domainDesign.getDefaultValue() != null){
					autocomplete = tableDesignRepository.getAllCodeListDetail(autocompleteInput);
					if(autocomplete != null){
						domainDesign.setCodelistDefaultAutocomplete(autocomplete.getOptionLabel());		
					}
				}
			}
		} else if(DbDomainConst.DatasourceType.SQL_BUILDER.equals(domainDesign.getDatasourceType()) || DbDomainConst.DatasourceType.SQL_BUILDER_IS_AUTOCOMPLETE.equals(domainDesign.getDatasourceType())){
			if(domainDesign.getDatasourceId() != null){
				if (DbDomainConst.DatasourceType.SQL_BUILDER.equals(domainDesign.getDatasourceType())) {
					autocompleteInput.setArg01(common.getWorkingProjectId().toString());
					autocompleteInput.setArg02(domainDesign.getDatasourceId().toString());
					autocompleteInput.setArg03(domainDesign.getDatasourceType().toString());
					autocomplete = tableDesignRepository.getAllSqlBuilderACLoadModify(autocompleteInput);
					if(autocomplete != null){
						domainDesign.setSqlCodeAutocomplete(autocomplete.getOptionLabel());
					}
				}else if(DbDomainConst.DatasourceType.SQL_BUILDER_IS_AUTOCOMPLETE.equals(domainDesign.getDatasourceType())){
					autocompleteInput.setArg01(common.getWorkingProjectId().toString());
					autocompleteInput.setArg02(domainDesign.getDatasourceId().toString());
					autocompleteInput.setArg03(domainDesign.getDatasourceType().toString());
					autocomplete = tableDesignRepository.getAllAutocompleteACLoadModify(autocompleteInput);
					if(autocomplete != null){
						domainDesign.setSqlCodeAutocomplete(autocomplete.getOptionLabel());
					}
				}
			}
		}
	}
	
	@Override
	public void registerDomain(DomainDesign domain, CommonModel common) {
		
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		
		projectService.validateProject(domain.getProjectId(), common.getCreatedBy(), common.getWorkingProjectId(), true);
		
		Long totalCount = domainDesignRepository.countNameCodeByDomainId(domain);
		ResultMessages resultMessages = ResultMessages.error();

		if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0001));
		} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0002));
		} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0001));
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0002));
		} 
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {
			
			this.insertUserDefineCodelist(domain);
			
			if(domain.getDefaultValue() == null){
				
				switch (domain.getGroupBasetypeId()) {
					case DbDomainConst.PhysicalDataTypePrimitive.DATE:
					case DbDomainConst.PhysicalDataTypePrimitive.DATETIME:
					case DbDomainConst.PhysicalDataTypePrimitive.TIME:
						domain.setDefaultValue(FunctionCommon.getCurrentTime().toString());
						break;
				}
			}
			domain.setCreatedDate(currentTime);
			domain.setUpdatedDate(currentTime);
			domainDesignRepository.registerDomain(domain);
		}
	}

	@Override
	public void modifyDomain(DomainDesign domainDesign, List<Autocomplete> listOfTableDesign, CommonModel common) {
		
		DomainDesign domainDesignBeforeEdit = domainDesignRepository.findOne(domainDesign.getDomainId());
		DomainDesign fromDB = findOne(domainDesign.getDomainId(), common, true); 
		/*//check if change base type
		if (fromDB.getBaseType() != domainDesign.getBaseType() && checkForeignKey(domainDesign.getDomainId()) > 0) {
			throw new BusinessException(ResultMessages.error().add(DomainDesignMessageConst.ERR_DOMAINDESIGN_0003,
					MessageUtils.getMessage(DomainDatatypeMessage.SC_DOMAINDATATYPE_0000)));
		}*/
		
		//Check Name or code exist
		Long totalCount = domainDesignRepository.countNameCodeByDomainId(domainDesign);
		ResultMessages resultMessages = ResultMessages.error();
		
		if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0001));
		} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0002));
		} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0001));
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0002));
		} 
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {
			if(domainDesign.getSupportOptionFlg() != null){
				// Delete UserDefineCodelIst
				userDefineCodelistRepository.deleteUserDefineCodelist(domainDesign.getDatasourceId());
				Long userDefineCodeListId = this.insertUserDefineCodelist(domainDesign);
				// Set new DataSourceId
				domainDesign.setDatasourceId(userDefineCodeListId);
			}

			// Check concurence
			if (!domainDesignRepository.modifyDomain(domainDesign)) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
			}
			this.insertProblem(domainDesignBeforeEdit, domainDesign, common);
		}
		
		// Change Item type table design if use.
		List<ValidationRule> validationRules = domainDatatypeRepository.findAllValidationRule();
		List<Long> tddIds = new ArrayList<Long>();
		for (Autocomplete autocomplete : listOfTableDesign) {
			tddIds.add(Long.parseLong(autocomplete.getOutput03()));
		}

		//DungNN - 20160730 - fix bug don't update item type when change base type
		// Get all Column use
		List<TableDesignDetails> tableDesignDetails = tableDesignDetailRepository.findByListId(tddIds);
		if (CollectionUtils.isNotEmpty(tableDesignDetails)) {
			for (TableDesignDetails designDetails : tableDesignDetails) {
				if (fromDB.getBaseType() != domainDesign.getBaseType()) {
					TableDesignUtil.initDefaultForItemType(designDetails, validationRules);
				} else {
					if (DbDomainConst.ConstrainsType.DATASOURCE.equals(domainDesign.getConstrainsType())) {
						if (DbDomainConst.DatasourceType.SQL_BUILDER_IS_AUTOCOMPLETE.equals(domainDesign.getDatasourceType())) {
							designDetails.setItemType(DbDomainConst.LogicDataType.AUTOCOMPLETE);
						} else {
							designDetails.setItemType(DbDomainConst.LogicDataType.SELECT);
						}
					} else if (!FunctionMasterUtils.QpString.intersect(fromDB.getFmtCode(), domainDesign.getFmtCode(), ",")) {
						TableDesignUtil.initDefaultForItemType(designDetails, validationRules);
					}
				}
			}

			tableDesignDetailRepository.initDefaultForItemType(tableDesignDetails);

		}
	}

	@Override
	public void deleteDomain(DomainDesign design) {

		List<Autocomplete> listOfTableDesign = this.listOfTableDeignUsed(design.getDomainId());
		
		ResultMessages resultMessages = ResultMessages.error();
		if(FunctionCommon.isNotEmpty(listOfTableDesign)){
			for (Autocomplete autocomplete : listOfTableDesign) {
				resultMessages.add(DomainDesignMessageConst.ERR_DOMAINDESIGN_0001, design.getDomainName(), autocomplete.getOutput01());
			}
		}
		
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		}else{
			boolean flagDel = domainDesignRepository.deleteDomain(design.getDomainId());
			if (!flagDel) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0073, MessageUtils.getMessage(CommonMessageConst.TQP_DOMAINDESIGN)));
			}
		}
	}
	
	/**
	 * 
	 * @param listUserDefineCodelistDetails
	 * @param td
	 */
	private Long insertUserDefineCodelist(DomainDesign domainDesign) {
		Long userDefineCodeListId = null;
		UserDefineCodelist userDefineCodelist;
		UserDefineCodelistDetails userDefineCodelistDetails;
		Integer supportOptionFlg = domainDesign.getSupportOptionFlg();
		
		if(supportOptionFlg !=null){
			userDefineCodelist = new UserDefineCodelist();
			userDefineCodelist.setSupportOptionFlg(supportOptionFlg);
			userDefineCodelistRepository.createUserDefineCodelist(userDefineCodelist);
			
			List<UserDefineCodelistDetails> listUserDefineCodelistDetails = new ArrayList<UserDefineCodelistDetails>();
			
			userDefineCodeListId = userDefineCodelist.getCodelistId();
			// Set UserDefineId for Column of Table 
			domainDesign.setDatasourceId(userDefineCodeListId);
			
			String [] userDefineValue = domainDesign.getUserDefineValue().split(INVERSE_BULLET_SYSBOL);
			Integer itemSeqNo = 0;
			for (int i = 0; i < userDefineValue.length; i++) {
				String [] userDefineValueRow = userDefineValue[i].split(REPLACEMENT_CHARACTER_SYSBOL);
				userDefineCodelistDetails = new UserDefineCodelistDetails();
				userDefineCodelistDetails.setCodelistId(userDefineCodeListId);
				userDefineCodelistDetails.setItemSeqNo(itemSeqNo++);

				if(supportOptionFlg.equals(0)){
					userDefineCodelistDetails.setCodelistName(null);
				}else{					
					userDefineCodelistDetails.setCodelistName(userDefineValueRow[2]);
				}
				userDefineCodelistDetails.setCodelistValue(userDefineValueRow[1]);
				userDefineCodelistDetails.setDefaultFlg(Integer.parseInt(userDefineValueRow[0]));
				listUserDefineCodelistDetails.add(userDefineCodelistDetails);
				
				if(userDefineValueRow[0] == "1"){
					domainDesign.setDefaultValue(userDefineValueRow[1]);
				}
			}
		
			if(listUserDefineCodelistDetails.size() > 0){
				userDefineCodelistRepository.createUserDefineCodelistDetails(listUserDefineCodelistDetails);
			}
		}
		
		return userDefineCodeListId;
	}

	/**
	 * get list base type and group them
	 * @author dungnn1
	 */
	@Override
	@Singleton
	public List<GroupDataTypeDB> getAllBasetype(Long projectId, boolean isFromGraphicDesign) {

		List<Basetype> listOfBasetype = domainDesignRepository.getAllBasetype(projectId);
		List<GroupDataTypeDB> listOfGroupDBType = new ArrayList<GroupDataTypeDB>();

		Integer currentDataTypeFlg = null;
		GroupDataTypeDB groupDataTypeDb = null;
		AutocompleteInput autocompleteInput = null;

		DataType dataType = null;
		List<DataType> listOfDataType = null;

		for (Basetype basetypeObj : listOfBasetype) {
			//if first init groupDataType
			if (currentDataTypeFlg == null) {
				groupDataTypeDb = new GroupDataTypeDB();
				listOfDataType = new ArrayList<DataType>();
				currentDataTypeFlg = basetypeObj.getDataTypeFlg();
			}
			//if next group then add and init new element
			if (!currentDataTypeFlg.equals(basetypeObj.getDataTypeFlg())) {
				groupDataTypeDb.setListOfDataType(listOfDataType);
				listOfGroupDBType.add(groupDataTypeDb);

				groupDataTypeDb = new GroupDataTypeDB();
				listOfDataType = new ArrayList<DataType>();
				currentDataTypeFlg = basetypeObj.getDataTypeFlg();
			}

			//groupDataTypeDb.setColor(basetypeObj.getGroupColor());
			groupDataTypeDb.setLabel(basetypeObj.getBasetypeGroupName());
			groupDataTypeDb.setDatatypeFlg(currentDataTypeFlg);
			groupDataTypeDb.setItemSeqNo(basetypeObj.getItemSeqNo());

			dataType = new DataType();
			dataType.setLabel(basetypeObj.getBasetypeName());
			dataType.setBasetypeName(basetypeObj.getBasetypeName());
			dataType.setBasetypeId(basetypeObj.getBasetyeId());
			dataType.setGroupBaseTypeId(basetypeObj.getGroupBaseTypeId());
			dataType.setLength(basetypeObj.getLength());
			dataType.setMandatory(basetypeObj.getMandatory());
			dataType.setPrecision(basetypeObj.getPrecision());
			dataType.setColor(basetypeObj.getGroupColor());
			dataType.setValidationRule(basetypeObj.getValidationRule());
			dataType.setMaxValue(basetypeObj.getMaxValue());
			dataType.setMinValue(basetypeObj.getMinValue());
			dataType.setConstrainsType(basetypeObj.getConstrainsType());
			dataType.setDatasourceId(basetypeObj.getDatasourceId());
			dataType.setDatasourceType(basetypeObj.getDatasourceType());
			dataType.setOperatorCode(basetypeObj.getOperatorCode());
			dataType.setDefaultValue(basetypeObj.getDefaultValue());
			dataType.setRemark(basetypeObj.getRemark());
			dataType.setDatatypeFlg(currentDataTypeFlg);
			dataType.setPrimitiveId(basetypeObj.getPrimitiveId());
			if(basetypeObj.getDatasourceId() != null && !isFromGraphicDesign){
				this.setUserDefineValue(dataType, basetypeObj.getDatasourceId());
				this.setValueDataSourceId(dataType, autocompleteInput, projectId);
			}
			listOfDataType.add(dataType);
		}

		//if still had element
		if (listOfBasetype != null) {
			groupDataTypeDb.setListOfDataType(listOfDataType);
			listOfGroupDBType.add(groupDataTypeDb);
		}
		return listOfGroupDBType;
	}

	/**
	 *  list Validation Rule type is using domain design
	 */
	@Override
	public List<ValidationRule> findAllValidationRule() {
		return domainDatatypeRepository.findAllValidationRule();
	}
	
	@Override
	/**
	 * check domain design being used other module
	 */
	public Long checkForeignKey(long domainId) {
		return domainDesignRepository.checkForeignKey(domainId);
	}

	@Override
	public List<DomainDesign> getReferenceById(long id) {
		return domainDesignRepository.getReferenceById(id);
	}
	
	private void setValueDataSourceId(DataType dataType, AutocompleteInput autocompleteInput, Long projectId){
		autocompleteInput = new AutocompleteInput();
		Autocomplete autocomplete = null;
		if(DbDomainConst.DatasourceType.CODELIST.equals(dataType.getDatasourceType())){
			autocompleteInput.setArg01(projectId.toString());
			autocompleteInput.setArg02(dataType.getDatasourceId().toString());
			autocompleteInput.setArg03(dataType.getDefaultValue());
			if (dataType.getDatasourceId() != null) {
				autocomplete = tableDesignRepository.getAllCodeList(autocompleteInput);
				if(autocomplete != null){
					dataType.setCodelistCodeAutocomplete(autocomplete.getOptionLabel());
				}
				autocomplete = null;
				if(!StringUtils.isEmpty(dataType.getDefaultValue())){
					autocomplete = tableDesignRepository.getAllCodeListDetail(autocompleteInput);
				}
				if(autocomplete != null){
					dataType.setCodelistDefaultAutocomplete(autocomplete.getOptionLabel());		
				}
			}
		} else if (DbDomainConst.DatasourceType.SQL_BUILDER.equals(dataType.getDatasourceType())) {
			autocompleteInput.setArg01(projectId.toString());
			autocompleteInput.setArg02(dataType.getDatasourceId().toString());
			autocompleteInput.setArg03(dataType.getDatasourceType().toString());
			autocomplete = tableDesignRepository.getAllSqlBuilderACLoadModify(autocompleteInput);
			if(autocomplete != null){
				dataType.setSqlCodeAutocomplete(autocomplete.getOptionLabel());
			}
		}else if(DbDomainConst.DatasourceType.SQL_BUILDER_IS_AUTOCOMPLETE.equals(dataType.getDatasourceType())){
			autocompleteInput.setArg01(projectId.toString());
			autocompleteInput.setArg02(dataType.getDatasourceId().toString());
			autocompleteInput.setArg03(dataType.getDatasourceType().toString());
			autocomplete = tableDesignRepository.getAllAutocompleteACLoadModify(autocompleteInput);
			if(autocomplete != null){
				dataType.setSqlCodeAutocomplete(autocomplete.getOptionLabel());
			}
		}
	}

	private void setUserDefineValue(DataType dataType, Long dataSourceId){
		
		List<UserDefineCodelistDetails> userDefineCodelistDetails = userDefineCodelistRepository.getAllByCodeList(dataSourceId);
		StringBuilder userDefineValue = new StringBuilder();
		
		if(userDefineCodelistDetails.size() > 0){
			for (UserDefineCodelistDetails codelistDetails : userDefineCodelistDetails) {
				userDefineValue.append(codelistDetails.getDefaultFlg());
				userDefineValue.append(REPLACEMENT_CHARACTER_SYSBOL);
				userDefineValue.append(codelistDetails.getCodelistValue());
				userDefineValue.append(REPLACEMENT_CHARACTER_SYSBOL);
				userDefineValue.append(codelistDetails.getCodelistName());
				userDefineValue.append(INVERSE_BULLET_SYSBOL);
			}
			
			dataType.setUserDefineValue(userDefineValue.toString());
			dataType.setSupportOptionFlg(userDefineCodelistDetails.get(0).getSupportOptionFlg());
		}
	}

	@Override
	public List<ValidationRule> findAllValidationRuleByStatys(int status) {
		return domainDatatypeRepository.findAllValidationRuleByStatus(status);
	}

	@Override
	public List<Autocomplete> listOfTableDeignUsed(Long domainDesignId) {
		return domainDesignRepository.listOfTableDeignUsed(domainDesignId);
	}
	
	/**
	 * 
	 */
	@Override
	public void insertProblem(DomainDesign domainDesignBeforeEdit, DomainDesign domainDesignAfterEdit, CommonModel common){
		
		List<BusinessLogic> businessLogics = domainDesignAfterEdit.getListBusinessLogics();
		List<SqlDesign> sqlDesigns = domainDesignAfterEdit.getListSqlDesigns();
		List<ProblemList> problemLists = new ArrayList<ProblemList>();
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		ProblemList problemList = null;
		List<Long> listDomainId = new ArrayList<Long>();
		List<Long> listFromResouceId = new ArrayList<Long>();
		List<Integer> listFromResourceType = new ArrayList<Integer>();
		
		// Insert proplem BLoogic 
		if(businessLogics != null && businessLogics.size() > 0){
			listFromResourceType.add(DbDomainConst.FromResourceType.DOMAIN_DESIGN);

			for (BusinessLogic logic : businessLogics) {
				problemList = new ProblemList();
				problemList.setProblemName(this.getNameProblem(domainDesignBeforeEdit, domainDesignAfterEdit));
				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
				problemList.setResourceId(logic.getBusinessLogicId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
				problemList.setModuleId(logic.getModuleId());
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
				problemList.setProjectId(common.getWorkingProjectId());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.DOMAIN_DESIGN);
				problemList.setFromResourceId(domainDesignBeforeEdit.getDomainId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setCreatedDate(currentTime);
				problemLists.add(problemList);

				listFromResouceId.add(domainDesignBeforeEdit.getDomainId());
			}
		}

		// Insert proplem Sql Design 
		if(sqlDesigns != null && sqlDesigns.size() > 0){
			listFromResourceType.add(DbDomainConst.FromResourceType.DOMAIN_DESIGN);

			for (SqlDesign sql : sqlDesigns) {
				problemList = new ProblemList();
				problemList.setProblemName(this.getNameProblem(domainDesignBeforeEdit, domainDesignAfterEdit));
				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
				problemList.setResourceId(sql.getSqlDesignId());
				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
				problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
				problemList.setModuleId(sql.getModuleId());
				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
				problemList.setProjectId(common.getWorkingProjectId());
				problemList.setFromResourceType(DbDomainConst.FromResourceType.DOMAIN_DESIGN);
				problemList.setFromResourceId(domainDesignBeforeEdit.getDomainId());
				problemList.setCreatedBy(common.getCreatedBy());
				problemList.setCreatedDate(currentTime);
				problemLists.add(problemList);
				
				listFromResouceId.add(domainDesignBeforeEdit.getDomainId());
			}
		}

		listDomainId.add(domainDesignBeforeEdit.getDomainId());
		if (problemLists.size() > 0) {
			problemListRepository.deleteFromResourceTypeOfTblDesign(listFromResourceType, listFromResouceId);
			problemListRepository.multiRegisterProblem(problemLists);
		}
	}

	/**
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	private String getNameProblem(DomainDesign before, DomainDesign after){
		return MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0001, after.getDomainName(), before.getBaseTypeAutocomplete(), after.getBaseTypeAutocomplete());
	}
	
	@Override
	public void loadListAffected(DomainDesign domainDesign, boolean isDeleted, CommonModel common){
		
		DomainDesign domain = findOne(domainDesign.getDomainId(), common, true);
		
		List<Autocomplete> listOfTableDesign = domainDesignRepository.listOfTableDeignUsed(domainDesign.getDomainId());
		domainDesign.setListOfTableDesign(listOfTableDesign);
		List<Long> listColumnId = new ArrayList<Long>();
		if(isDeleted){
			for (Autocomplete autocomplete : listOfTableDesign) {
				listColumnId.add(Long.parseLong(autocomplete.getOutput03()));
			}
		}else{
			// If change Data Type 
			if(domainDesign.getGroupBasetypeId() != domain.getGroupBasetypeId()){
				if(listOfTableDesign != null){
					for (Autocomplete autocomplete : listOfTableDesign) {
						listColumnId.add(Long.parseLong(autocomplete.getOutput03()));
					}
				}
			}
		}
		if(listColumnId.size() > 0){
			domainDesign.setListSqlDesigns(tableDesignRepository.getAllSqlAffected(listColumnId, common.getWorkingProjectId()));
			domainDesign.setListBusinessLogics(tableDesignRepository.getAllBLogicAffected(listColumnId, null, null));
		}
	}

	@Override
	public DomainDesign setFmtCodelist(DomainDesign domainDesign) {
		domainDesign.setFmtCodelist(domainDatatypeRepository.findAllValidationRuleByStatus(DbDomainConst.DisplayType.USED));
		
		if(FunctionCommon.isNotEmpty(domainDesign.getFmtCode())){
			List<String> listFtm = new ArrayList<String>();
			String [] arrFtmCode = domainDesign.getFmtCode().split(",");
			for (int i = 0; i < arrFtmCode.length; i++) {
				listFtm.add(arrFtmCode[i]);
			}
			domainDesign.setFmtCodeByString(listFtm);
		}
		return domainDesign;
	}
}
