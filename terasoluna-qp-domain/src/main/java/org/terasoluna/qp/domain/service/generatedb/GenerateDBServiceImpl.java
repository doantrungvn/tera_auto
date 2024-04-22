package org.terasoluna.qp.domain.service.generatedb;


import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.domain.model.ScreenArea;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.repository.domaindesign.DomainDesignRepository;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignRepository;
import org.terasoluna.qp.domain.repository.screenitem.ScreenItemRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignDetailRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignRepository;

@Service
@Transactional
public class GenerateDBServiceImpl implements GenerateDBService{
	
	private HashMap<Long, Long> mScreenAreaWithTable = new HashMap<Long, Long>();
	
	@Inject
	ScreenItemRepository screenItemRepository;
	
	@Inject
	ScreenDesignRepository screenDesignRepository;
	
	@Inject
	TableDesignRepository tableDesignRepository;
	
	@Inject
	TableDesignDetailRepository tableDesignDetailRepository;
	
	@Inject
	DomainDesignRepository domainRepository;
	
	/**
	 * generate table & table design from screen of module
	 * 
	 * if overwriteFlg = true -> overwrite else ignore
	 */
	@Override
	public void generateDbFromScreen(Long moduleId, boolean overwriteFlg) {
//		Long languageId = SessionUtils.getCurrentLanguageId();
//		//get all screen area of module
//		List<ScreenArea> listOfScreenDb = screenDesignRepository.getAllRegistScreenInfoByModuleIdForGenerateDb(moduleId, languageId);
//		int numOfScreen = FunctionCommon.isEmpty(listOfScreenDb) ? 0: listOfScreenDb.size();
//
//		//get all screen item of module
//		List<ScreenItem> listOfScreenItem = screenItemRepository.getAllScreenItemInfoByModuleIdForGenerateDb(moduleId, languageId);
//		int numOfScreenItem = FunctionCommon.isEmpty(listOfScreenItem) ? 0: listOfScreenItem.size();
//		
//		//get all table of project
//		List<TableDesign> listOfTableFromDB = tableDesignRepository.getTableDesignByProjectAndSubArea(SessionUtils.getCurrentProjectId(), DomainDatatypeConst.SEARCH_ALL_TABLE_DESIGN);
//		if (overwriteFlg) {
//			//Empty all table design detail
//			List<Long> listOfColumnId = tableDesignDetailRepository.getToEmptyFromScreen(SessionUtils.getCurrentProjectId(), moduleId);
//			tableDesignDetailRepository.multiDelete(listOfColumnId);
//		}
//
//		//prepare data, if same message code then choice area has total element greater. If name or code exist in table design -> ignore
//		List<ScreenArea> listNewScreenArea = new ArrayList<ScreenArea>();
//		
//		for (int i=0;i<numOfScreen;i++) {
//			ScreenArea currentSA = listOfScreenDb.get(i);
//			
//			if (!checkExistsScreenArea(listNewScreenArea, currentSA)) {
//				int checkSame = checkExistsNameOrCode(listOfTableFromDB, currentSA);
//				//if not exist on table design
//				if (checkSame == GenerateDBConst.SameType.NOT_SAME) {
//					listNewScreenArea.add(currentSA);
//				} else {
//					//if exist and don't overwrite -> ignore
//					if (!overwriteFlg) {
//						continue;
//					} else {
//						if (checkSame == GenerateDBConst.SameType.SAME_NAME) {
//							currentSA.setAreaLocalName(UUID.randomUUID().toString().replace("-","_"));
//						}
//						listNewScreenArea.add(currentSA);
//					}
//				}
//			}
//		}
//		//Empty listOfScreen From DB
//		listOfScreenDb = null;
//
//		//insert table and table detail design
//		//get all datatype
//		List<Basetype> listOfBasetype = domainRepository.getAllBasetype(SessionUtils.getCurrentProjectId());
//		
//		List<TableDesign> listOfTable = new ArrayList<TableDesign>();
//		List<TableDesignDetails> listOfColumn = new ArrayList<TableDesignDetails>();
//
//		Long accountId = SessionUtils.getAccountId();
//		Timestamp currentTime = FunctionCommon.getCurrentTime();
//		for (ScreenArea currentSA : listNewScreenArea) {
//			TableDesign table = new TableDesign();
//			table.setDesignStatus(DbDomainConst.DesignStatus.UNDER_DESIGN);
//			table.setTableName(currentSA.getAreaLocalName());
//			table.setTableCode(currentSA.getAreaCode());
//			table.setProjectId(SessionUtils.getCurrentProjectId());
//
//			table.setCreatedBy(accountId);
//			table.setUpdatedBy(accountId);
//			table.setCreatedDate(currentTime);
//			table.setUpdatedDate(currentTime);
//			
//			if (mScreenAreaWithTable.containsKey(currentSA.getScreenAreaId())) {
//				table.setTableDesignId(mScreenAreaWithTable.get(currentSA.getScreenAreaId()));
//				listOfTable.add(table);
//			} else {
//				tableDesignRepository.create(table);
//			}
//
//			for (int i = 0; i < numOfScreenItem; i++) {
//				ScreenItem screenItem = listOfScreenItem.get(i);
//
//				if (screenItem.getScreenAreaId().equals(currentSA.getScreenAreaId())) {
//					TableDesignDetails tableDetail = new TableDesignDetails();
//					tableDetail.setCode(screenItem.getItemCode());
//					tableDetail.setName(screenItem.getItemName());
//					tableDetail.setTableDesignId(table.getTableDesignId());
//					tableDetail.setItemSeqNo(screenItem.getItemSeqNo());
//					tableDetail.setDefaultValue(screenItem.getDefaultValue());
//					tableDetail.setIsMandatory(screenItem.getMandatoryFlg());
//					tableDetail.setItemType(screenItem.getLogicalDataType());
//					tableDetail.setDataType(Long.valueOf(screenItem.getPhysicalDataType()));
//
//					if (null != screenItem.getScreenItemValidation()) {
//						tableDetail.setMinVal(screenItem.getScreenItemValidation().getMinVal());
//						tableDetail.setMaxVal(screenItem.getScreenItemValidation().getMaxVal());
//						tableDetail.setMaxlength(screenItem.getScreenItemValidation().getMaxlength());
//						tableDetail.setFmtCode(screenItem.getScreenItemValidation().getFmtCode());
//					}
//
//					tableDetail.setDataTypeFlg(DbDomainConst.DataTypeFlag.PRIMITIVE);
//					tableDetail.setDisplayType(DbDomainConst.DisplayType.USED);
//					tableDetail.setBinKeyType(DbDomainConst.TblDesignKeyType.NONE);
//					
//					//prepare something from datatype
//					for (Basetype basetype: listOfBasetype) {
//						if (basetype.getBasetyeId().equals(tableDetail.getDataType())) {
//							tableDetail.setDataTypeFlg(basetype.getDataTypeFlg());
//							tableDetail.setFmtCode(basetype.getValidationRule());
//							tableDetail.setGroupBaseTypeId(basetype.getGroupBaseTypeId());
//							tableDetail.setDatasourceType(basetype.getDatasourceType());
//							break;
//						}
//					}
//					
//					listOfColumn.add(tableDetail);
//					listOfScreenItem.remove(i);
//					i--;
//					numOfScreenItem--;
//				}
//			}
//		}
//		
//		if (!FunctionCommon.isEmpty(listOfTable)) {
//			tableDesignRepository.multiModify(listOfTable);
//		}
//		
//		if(!FunctionCommon.isEmpty(listOfColumn)) {
//			tableDesignDetailRepository.multiCreate(listOfColumn);
//		}
	}
	
	/**
	 * 
	 * @param listScreenArea
	 * @param screenArea
	 * @return
	 */
	private boolean checkExistsScreenArea(List<ScreenArea> listScreenArea, ScreenArea screenArea) {
		if(FunctionCommon.isEmpty(listScreenArea)) {
			return false;
		}
		
		for (ScreenArea temp: listScreenArea) {
			if (StringUtils.equalsIgnoreCase(temp.getAreaCode(), screenArea.getAreaCode())) {
				return true;
			}
		}
		return false;
	}
	
	
	private int checkExistsNameOrCode(List<TableDesign> listOfTable, ScreenArea screenArea) {
		if(FunctionCommon.isEmpty(listOfTable)) {
			return GenerateDBConst.SameType.NOT_SAME;
		}

		int iReturn = GenerateDBConst.SameType.NOT_SAME;
		
		for (TableDesign table : listOfTable) {
			if (StringUtils.equalsIgnoreCase(table.getTableCode(), screenArea.getAreaCode())) {
				iReturn = GenerateDBConst.SameType.SAME_CODE;
				mScreenAreaWithTable.put(screenArea.getScreenAreaId(), table.getTableDesignId());
				break;
			}
		}

		for (TableDesign table : listOfTable) {
			if (StringUtils.equalsIgnoreCase(table.getTableName(), screenArea.getAreaLocalName())) {
				if (!StringUtils.equalsIgnoreCase(table.getTableCode(), screenArea.getAreaCode())) {
					iReturn = GenerateDBConst.SameType.SAME_NAME;
				}
				break;
			}
		}

		return iReturn;
	}
	
}
