package org.terasoluna.qp.domain.service.domaindatatype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.DomainDatatypeMessage;
import org.terasoluna.qp.domain.model.DomainDatatype;
import org.terasoluna.qp.domain.model.DomainDatatypeCodelist;
import org.terasoluna.qp.domain.model.DomainDatatypeItem;
import org.terasoluna.qp.domain.model.DomainDesign;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.repository.businesstype.BusinessTypeRepository;
import org.terasoluna.qp.domain.repository.domaindatatype.DomainDatatypeCodelistRepository;
import org.terasoluna.qp.domain.repository.domaindatatype.DomainDatatypeCriteria;
import org.terasoluna.qp.domain.repository.domaindatatype.DomainDatatypeItemRepository;
import org.terasoluna.qp.domain.repository.domaindatatype.DomainDatatypeRepository;
import org.terasoluna.qp.domain.repository.domaindesign.DomainDesignRepository;
import org.terasoluna.qp.domain.repository.module.ModuleRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignDetailRepository;
import org.terasoluna.qp.domain.service.module.ModuleService;

@Service
@Transactional
public class DomainDatatypeServiceImpl implements DomainDatatypeService {

	@Inject
	DomainDatatypeRepository domainDatatypeRepository;

	@Inject
	ModuleRepository moduleRepository;

	@Inject
	DomainDesignRepository domainDesignRepository;

	@Inject
	DomainDatatypeItemRepository domainDatatypeItemRepository;

	@Inject
	DomainDatatypeCodelistRepository domainDatatypeCodelistRepository;
	
	@Inject
	TableDesignDetailRepository tableDesignDetailRepository; 
	
	@Inject
	BusinessTypeRepository businessTypeRepository; 
	
	@Inject
	ModuleService moduleService;
	
	@Override
	public Page<DomainDatatype> findPageByCriteria(
			DomainDatatypeCriteria searchCriteria, Pageable pageable) {
		// TODO Auto-generated method stub
		long totalCount = domainDatatypeRepository.countByCriteria(searchCriteria);

		List<DomainDatatype> listObj;
		if (0 < totalCount) {
			listObj = domainDatatypeRepository.findPageByCriteria(searchCriteria, pageable);
		} else {
			listObj = Collections.emptyList();
		}

		Page<DomainDatatype> page = new PageImpl<DomainDatatype>(listObj, pageable, totalCount);

		return page;
	}

	/**
	 * find by id and action
	 */
	@Override
	public DomainDatatype findOne(long id, int flag) {
		DomainDatatype modelObj = domainDatatypeRepository.findOne(id);

		if (modelObj == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(CommonMessageConst.TQP_DOMAINDATATYPE)));
		}
		List<TableDesignDetails> listOfTableDesignDetails = new ArrayList<TableDesignDetails>();
		//if table design was deleted
		if (DomainDatatypeUtil.isEmpty(modelObj.getTableDesignId())) {
			modelObj.setChangeDesignFlg(1);
		} else {
			//list table design details form db
			listOfTableDesignDetails = listOfTableDesignDetails(modelObj.getTableDesignId()) ;	
		}

		modelObj.setDomainDatatypeItems(domainDatatypeItemRepository.findAllByDomainDatatype(id));
		
		StringBuilder strMsgLabel = new StringBuilder();
		StringBuilder strMsgValue = new StringBuilder();
		StringBuilder strDatasource = new StringBuilder();
		
		List<DomainDatatypeCodelist> listOfCodelist = domainDatatypeCodelistRepository.findAllByDomainDatatype(id);
		
		int size = modelObj.getDomainDatatypeItems().size();
		
		boolean checkExistInTableDesignDetail = false;
		
		for (int i = 0; i < size; i++) {
			DomainDatatypeItem itemObj = modelObj.getDomainDatatypeItems().get(i);

			//if domain data type is autocomplete
			if (DomainDatatypeConst.LogicDataType.AUTOCOMPLETE.equals(itemObj.getDomainDataType())) {
				strDatasource.append(itemObj.getAutocompleteId());

			} else if (itemObj.getCodelistId() != null && itemObj.getCodelistId() != 0) {//if domain data type is systen codelist
				strDatasource.append(itemObj.getCodelistId());
				itemObj.setAutocompleteName(itemObj.getCodelistName());

			} else if (itemObj.getCodelistType() != null && DomainDatatypeConst.CodelistType.SCREEN_CODE_LIST.equals(itemObj.getCodelistType())) {//if domain data type is custom codelist
				for (DomainDatatypeCodelist codelistTemp : listOfCodelist) {
					if (itemObj.getDomainDatatypeItemId() == codelistTemp.getDomainDatatypeItemId()) {
						//if null then not add
						if (!DomainDatatypeUtil.isEmpty(codelistTemp.getCodelistName())) {
							strMsgLabel.append(codelistTemp.getCodelistName());
							strMsgLabel.append(DomainDatatypeConst.CHAR_SPLIT);
						}
						//if null then not add
						if (!DomainDatatypeUtil.isEmpty(codelistTemp.getCodelistValue())) {
							strMsgValue.append(codelistTemp.getCodelistValue());
							strMsgValue.append(DomainDatatypeConst.CHAR_SPLIT);
						}
					}
				}
			}

			itemObj.setMsgLabel(strMsgLabel.toString());
			itemObj.setMsgValue(strMsgValue.toString());
			itemObj.setDataSource(strDatasource.toString());

			strMsgLabel.delete(0, strMsgLabel.length());
			strMsgValue.delete(0, strMsgValue.length());
			strDatasource.delete(0, strDatasource.length());

			checkExistInTableDesignDetail = false;
			for (TableDesignDetails designItem : listOfTableDesignDetails) {
				if (designItem.getColumnId().equals(itemObj.getTblDesignDetailsId())) {
					itemObj.setRequiredFlg(Integer.valueOf(designItem.getIsMandatory()));
					itemObj.setTblDesignDetailsCode(designItem.getCode());
					itemObj.setMaxlengthPhysical(designItem.getMaxlength());

					itemObj.setStatus(DomainDatatypeConst.DomainMappingItemStatus.NO_CHANGE_STATUS);

					if (!itemObj.getGroupBasetypeId().equals(designItem.getGroupBaseTypeId()) || itemObj.isPrimaryKey() != designItem.isPrimaryKey()) {
						modelObj.setChangeDesignFlg(1);
						itemObj.setGroupBasetypeId(designItem.getGroupBaseTypeId());
						itemObj.setStatus(DomainDatatypeConst.DomainMappingItemStatus.CHANGE_STATUS);
					}

					listOfTableDesignDetails.remove(designItem);
					checkExistInTableDesignDetail = true;
					break;
				}
			}

			if (!checkExistInTableDesignDetail) {
				itemObj.setStatus(DomainDatatypeConst.DomainMappingItemStatus.DELETED_STATUS);
				modelObj.setChangeDesignFlg(1);
			}

		}
		//if find for edit then get in
		if (flag == DomainDatatypeConst.MODIFY_ACTION) {
			//process for new column
			int numOfNewColumn = 0;
			for (TableDesignDetails designItem : listOfTableDesignDetails) {
				DomainDatatypeItem itemObj = new DomainDatatypeItem();
				itemObj.setDomainColumnName(designItem.getName());
				itemObj.setDomainColumnCode(DomainDatatypeUtil.toCamelCase(designItem.getCode()));
				//itemObj.setPhysicalDataType(Integer.valueOf(designItem.getDataType()));
				itemObj.setKeyType(designItem.getKeyType());
				itemObj.setMaxlengthPhysical(designItem.getMaxlength());
				itemObj.setMaxlength(designItem.getMaxlength());
				itemObj.setStatus(DomainDatatypeConst.DomainMappingItemStatus.NEW_STATUS);
				itemObj.setTblDesignDetailsCode(designItem.getCode());
				itemObj.setTblDesignDetailsId(designItem.getColumnId());
				itemObj.setGroupBasetypeId(designItem.getGroupBaseTypeId());
				itemObj.setItemSeqNo(numOfNewColumn);
				itemObj.setRequiredFlg(Integer.valueOf(designItem.getIsMandatory()));
				
				if (designItem.getIsMandatory() == 0 && designItem.isPrimaryKey() == 0) {
					itemObj.setDisplayType(DomainDatatypeConst.DisplayType.DISPLAY);
				}

				//itemObj.setDefaultValue(designItem.getDefault());
				//itemObj.setFmtCode(DomainDesignUtil.getValidatetionRule(designItem.getGroupOfDataType()));

				numOfNewColumn++;
				modelObj.getDomainDatatypeItems().add(itemObj);

			}
		}

		return modelObj;
	}

	@Override
	public void deleteDomainDatatype(long id) {
		domainDatatypeRepository.deleteDomainDatatype(id);
	}

	/**
	 * 
	 * @param modelObj
	 */
	private void validatorBeforceUpdate(DomainDatatype modelObj) {
		//check exist name code
		DomainDatatypeCriteria criteria = new DomainDatatypeCriteria();
		criteria.setDomainTableName(modelObj.getDomainDatatypeName());
		criteria.setDomainTableCode(modelObj.getDomainDatatypeCode());
		criteria.setProjectId(modelObj.getProjectId());
		criteria.setDomainTableId(modelObj.getDomainDatatypeId());
		DomainDatatype domainDataType = checkExistNameOrCodeOnProject(criteria);
		
		ResultMessages resultMessages = ResultMessages.error(); 
		Boolean hasErrors = false;

		if (domainDataType != null) {
			//exist name
			if (domainDataType.getDomainDatatypeName().equalsIgnoreCase(modelObj.getDomainDatatypeName())) {
				hasErrors = true;
				resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(DomainDatatypeMessage.SC_DOMAINDATATYPE_0002));
			}
			//exist code
			if (domainDataType.getDomainDatatypeCode().equalsIgnoreCase(modelObj.getDomainDatatypeCode())) {
				hasErrors = true;
				resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(DomainDatatypeMessage.SC_DOMAINDATATYPE_0003));
			}
		}

		if(hasErrors){
			throw new BusinessException(resultMessages);
		}
	}
	
	@Override
	public void updateDomainDatatype(DomainDatatype modelObj) {
//		//Check Name exist
//		validatorBeforceUpdate(modelObj);
//
//		modelObj.setSystemTime(FunctionCommon.getCurrentTime());
//		modelObj.setUpdatedBy(SessionUtils.getAccountId());
//		
//		if (modelObj.getDomainDatatypeId() != 0) {
//			if (!domainDatatypeRepository.updateDomainDatatype(modelObj)) {
//				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(DomainDatatypeMessage.SC_DOMAINDATATYPE_0000)));
//			}
//
//			domainDatatypeCodelistRepository.deleteAllByDomainDatatype(modelObj.getDomainDatatypeId());
//		} else {
//			domainDatatypeRepository.insertDomainDatatype(modelObj);
//		}
//
//		int size = modelObj.getDomainDatatypeItems().size();
//
//		String strMsgLabel = null;
//		String strMsgValue = null;
//		String strMsgDatasource = null;
//		
//		for (int i = 0; i < size; i++) {
//			DomainDatatypeItem itemObj = modelObj.getDomainDatatypeItems().get(i);
//
//			strMsgLabel = itemObj.getMsgLabel();
//			strMsgValue = itemObj.getMsgValue();
//			strMsgDatasource = itemObj.getDataSource();
//			itemObj.setCodelistType(null);
//			//if autocomplete
//			if (DomainDatatypeConst.LogicDataType.AUTOCOMPLETE.equals(itemObj.getDomainDataType())) {
//				itemObj.setAutocompleteId( Long.parseLong(strMsgDatasource));
//			} else {
//				if (!DomainDatatypeUtil.isEmpty (strMsgDatasource)) {
//					itemObj.setCodelistId(Long.parseLong(strMsgDatasource));
//					itemObj.setCodelistType(DomainDatatypeConst.CodelistType.SYSTEM_CODE_LIST);
//				} else if (!DomainDatatypeUtil.isEmpty(strMsgValue)) {
//					itemObj.setCodelistType(DomainDatatypeConst.CodelistType.SCREEN_CODE_LIST);
//				}
//			}
//			if (!DomainDatatypeUtil.isEmpty(itemObj.getDomainDatatypeItemId())) {
//				// update domain datatype items
//				domainDatatypeItemRepository.updateDomainDatatypeItem(itemObj);
//			} else {
//				// insert domain datatype items
//				itemObj.setDomainDatatypeId(modelObj.getDomainDatatypeId());
//				domainDatatypeItemRepository.insertDomainDatatypeItem(itemObj);
//			}
//
//			// process domain datatype codelist
//			if (!DomainDatatypeUtil.isEmpty(strMsgValue)) {
//				DomainDatatypeCodelist codelist = new DomainDatatypeCodelist();
//				codelist.setDomainDatatypeItemId(itemObj.getDomainDatatypeItemId());
//
//				if (!DomainDatatypeUtil.isEmpty(strMsgValue)) {
//					String[] arrMsgValue = strMsgValue.split(DomainDatatypeConst.CHAR_SPLIT);
//					String[] arrMsgLabel = null;
//					
//					boolean isSupportOption = false;
//					
//					if (strMsgLabel != null && !strMsgLabel.isEmpty()) {
//						arrMsgLabel = strMsgLabel.split(DomainDatatypeConst.CHAR_SPLIT);
//						codelist.setSupportOptionFlag(1);
//						isSupportOption = true;
//					} else {
//						codelist.setSupportOptionFlag(0);
//					}
//	
//					for (int j = 0; j < arrMsgValue.length; j++) {
//						String strOptionValue = arrMsgValue[j];
//						if(DomainDatatypeUtil.isEmpty(strOptionValue)) {
//							continue;
//						}
//						codelist.setCodelistValue(strOptionValue);
//						if (isSupportOption) {
//							codelist.setCodelistName(arrMsgLabel[j]);
//						}
//						domainDatatypeCodelistRepository.insertDomainDatatypeCodelist(codelist);
//					}
//				}
//			}
//		}
//		if(null != modelObj.getIsGenerate() && modelObj.getIsGenerate()) {
//			BusinessType businessType = businessTypeRepository.selectFirstBusinessType(); 
//			if(null == businessType) {
//				throw new BusinessException(ResultMessages.error().add(DomainDatatypeMessage.SC_DOMAINDATATYPE_0014));
//			} else {
//				Long maxModuleId = moduleRepository.selectMaxId();
//				ModuleTableMapping []moduleTableMappings = new ModuleTableMapping[1];
//				moduleTableMappings[0] = new ModuleTableMapping();
//				moduleTableMappings[0].setTblDesignId(modelObj.getDomainDatatypeId());
//				moduleTableMappings[0].setTblDesignName(modelObj.getDomainDatatypeName());
//				moduleTableMappings[0].setTableMappingType(0);
//				Integer []screenPatternTypes = new Integer[4];
//				screenPatternTypes[0] = DbDomainConst.ScreenPatternType.SEARCH;
//				screenPatternTypes[1] = DbDomainConst.ScreenPatternType.REGISTER;
//				screenPatternTypes[2] = DbDomainConst.ScreenPatternType.VIEW;
//				screenPatternTypes[3] = DbDomainConst.ScreenPatternType.MODIFY;
//				Module module = new Module();
//				module.setModuleCode(modelObj.getDomainDatatypeCode() + maxModuleId);
//				module.setModuleName(modelObj.getDomainDatatypeName() + maxModuleId);
//				module.setProjectId(modelObj.getProjectId());
//				module.setConfirmationType(1);
//				module.setCompletionType(1);
//				module.setStatus(DesignStatus.UNDER_DESIGN);
//				module.setScreenPatternTypes(screenPatternTypes);
//				module.setModuleTableMappings(moduleTableMappings);
//				module.setBusinessTypeId(businessType.getBusinessTypeId());
//				module.setBusinessTypeName(businessType.getBusinessTypeName());
//				moduleService.registerModule(module);
//			}
//		}
	}

	@Override
	public List<Module> findAllModuleByTableMappingId(long id) {
		return moduleRepository.findAllModuleByTableMappingId(id);
	}

	@Override
	public Map<Integer, List<DomainDesign>> listOfDomainDesign(Long projectId) {
		
		Map<Integer, List<DomainDesign>> mapReturn = new HashMap<Integer, List<DomainDesign>>();
		
		List<DomainDesign> listOfDomainDesign = domainDesignRepository.findAllByProjectId(projectId);
		
		for (DomainDesign domainObj: listOfDomainDesign) {
			if (mapReturn.containsKey(domainObj.getGroupBasetypeId())) {
				mapReturn.get(domainObj.getGroupBasetypeId()).add(domainObj);
			} else {
				List<DomainDesign> listTempDomain = new ArrayList<DomainDesign>();
				listTempDomain.add(domainObj);
				mapReturn.put(domainObj.getGroupBasetypeId(), listTempDomain);
			}
		}

		return mapReturn;
	}

	@Override
	public Map<String, String> listOfDomainDesignExt(Long projectId) {
		// get all domain
		List<DomainDesign> listOfDomain = domainDesignRepository.findAllByProjectId(projectId);

		// and add radio, select, check, Autocomplete
		DomainDesign domainObj = new DomainDesign();
		domainObj.setDomainId(Long.valueOf(DomainDatatypeConst.LogicDataType.RADIO));
		domainObj.setDomainName("Radio");
		listOfDomain.add(domainObj);

		domainObj = new DomainDesign();
		domainObj.setDomainId(Long.valueOf(DomainDatatypeConst.LogicDataType.CHECKBOX));
		domainObj.setDomainName("Checkbox");
		listOfDomain.add(domainObj);

		domainObj = new DomainDesign();
		domainObj.setDomainId(Long.valueOf(DomainDatatypeConst.LogicDataType.SELECT));
		domainObj.setDomainName("Select");
		listOfDomain.add(domainObj);

		domainObj = new DomainDesign();
		domainObj.setDomainId(Long.valueOf(DomainDatatypeConst.LogicDataType.AUTOCOMPLETE));
		domainObj.setDomainName("Autocomplete");
		listOfDomain.add(domainObj);

		Map<String, String> mapDomain = new HashMap<String, String>();
		for (DomainDesign temp : listOfDomain) {
			mapDomain.put(String.valueOf(temp.getDomainId()), temp.getDomainName());
		}
		
		return mapDomain;
	}

	@Override
	public List<TableDesignDetails> listOfTableDesignDetails(long tableDesignId) {
		// TODO Auto-generated method stub
		return tableDesignDetailRepository.findAllByTableDesign(tableDesignId);
	}

	@Override
	public DomainDatatype checkExistNameOrCodeOnProject(DomainDatatypeCriteria searchCriteria) {
		return domainDatatypeRepository.checkExistNameOrCodeOnProject(searchCriteria);
	}

}
