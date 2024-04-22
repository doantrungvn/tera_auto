package org.terasoluna.qp.domain.service.importmanagement;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.GenerateDocumentMessageConst;
import org.terasoluna.qp.app.message.ImportManagementMessageConst;
import org.terasoluna.qp.domain.model.CodeList;
import org.terasoluna.qp.domain.model.CodeListDetail;
import org.terasoluna.qp.domain.model.ImportManagement;
import org.terasoluna.qp.domain.repository.codelist.CodeListRepository;
import org.terasoluna.qp.domain.repository.importmanagement.ImportManagementRepository;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

@Service
@Transactional(rollbackFor = Exception.class)
public class ImportCodelistDesignServiceImpl implements ImportCodelistDesignService {
	@Inject
	ImportManagementRepository importManagementRepository;
	@Inject
	CodeListRepository codelistRepository;
	
	private int LIMIT_ROW_IMPORT = 0;
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importData(ImportManagement importManagement, Long projectId, Long accountId) {
		String filePath = importManagement.getFilePath();
		boolean rollback = importManagement.isRollback();
		boolean delete = importManagement.isDelete();
		Map<String, List<String[]>> inputData = new HashMap<String, List<String[]>>();
		List<CodeList> listDataImportCodelistDesign = new ArrayList<CodeList>();
		int importCount = 0;
		LIMIT_ROW_IMPORT = ImportUtils.getLimitRowImport();
		List<String[]> errors = new ArrayList<String[]>();
		
		//get input data to file
		inputData = this.getInputData(filePath);
		
		//set table rule for validate
		List<TableField> listCodelistTableField = setTableFieldRuleForCodelistTable();
		List<TableField> listCodelistDetailTableField = setTableFieldRuleForCodelistDetailTable();
		
		// Result Alert
		int totalCountInserted = 0;
		int totalCountDeleted = 0;
		int totalCountUpdated = 0;
		
		int totalCountInsert = 0;
		int totalCountDelete = 0;
		int totalCountUpdate = 0;
		
		// validate data type and validate business of import data 
		int numberOfSheet = inputData.size() / 2;
		for(int i = 2; i < numberOfSheet + 2; i++) {
			List<String[]> tempListSummary = inputData.get("SummarySheet" + i);
			List<String[]> tempListDetail = inputData.get("DetailSheet" + i);
			Map<String, Object> mapSummary = ImportUtils.validate(tempListSummary, listCodelistTableField);
			Map<String, Object> mapDetail = ImportUtils.validate(tempListDetail, listCodelistDetailTableField);
			
			// result after validate
			List<String[]> errorsSummary = (List<String[]>) mapSummary.get(ImportManagementConst.ERR_WHEN_IMPORT);
			List<String[]> rowsDataSummary = new ArrayList<String[]>();
			rowsDataSummary = (List<String[]>) mapSummary.get(ImportManagementConst.IMPORTTING_DATA);
			List<Integer> rowsSummary = (List<Integer>) mapSummary.get(ImportManagementConst.ROWS);
			
			List<String[]> errorsDetail = (List<String[]>) mapDetail.get(ImportManagementConst.ERR_WHEN_IMPORT);
			List<String[]> rowsDataDetail = new ArrayList<String[]>();
			rowsDataDetail = (List<String[]>) mapDetail.get(ImportManagementConst.IMPORTTING_DATA);
			List<Integer> rowsDetail = (List<Integer>) mapDetail.get(ImportManagementConst.ROWS);
			
			//convert string array to Codelist design list
			List<CodeList> listCodelistDesign = new ArrayList<CodeList>();
			List<CodeListDetail> listCodelistDetailDesign = new ArrayList<CodeListDetail>();
			convertArrayToCodelistDesign(listCodelistDesign, listCodelistTableField, rowsDataSummary, errorsSummary, rowsSummary, projectId, accountId);
			listDataImportCodelistDesign.add(listCodelistDesign.get(0));
			
			//set data into insert/update/delete list
			List<CodeList> listDataSummaryInsert = new ArrayList<CodeList>();
			List<CodeList> listDataSummaryUpdate = new ArrayList<CodeList>();
			List<CodeList> listDataSummaryDelete = new ArrayList<CodeList>();
			List<CodeListDetail> listDataDetailInsert = new ArrayList<CodeListDetail>();
			List<CodeListDetail> listDataDetailUpdate = new ArrayList<CodeListDetail>();
			List<CodeListDetail> listDataDetailDelete = new ArrayList<CodeListDetail>();
			setDataIntoInsertUpdateDeleteListSummary(listCodelistDesign, listDataSummaryInsert, listDataSummaryUpdate, listDataSummaryDelete, rowsSummary, errorsSummary, projectId, delete);
			
			// Set totalCount
			totalCountInsert += listDataSummaryInsert.size();
			totalCountDelete += listDataSummaryDelete.size();
			totalCountUpdate += listDataSummaryUpdate.size();
			
			// check transaction rollback or not
			boolean isException = false;
			Long codelistId = null;
			if(rollback && errorsSummary.size() == rowsSummary.size()) {
				//delete data
				if(delete) {
					isException = deleteDataSummary(listDataSummaryDelete);
					if(!isException){
						totalCountDeleted++;
					}
					if(i == numberOfSheet + 1) {
						List<CodeList> listRedundantCodelist = getRedundantCodelist(listDataImportCodelistDesign, projectId);
						isException = deleteDataSummary(listRedundantCodelist);
					}
				}
				//insert data
				isException = insertDataSummary(listDataSummaryInsert);
				if(!isException && listDataSummaryInsert.size() > 0) {
					codelistId = importManagementRepository.getSequencesCodelist();
					totalCountInserted++;
				}
				//update data
				isException = updateDataSummary(listDataSummaryUpdate);
				if(!isException && listDataSummaryUpdate.size() > 0) {
					totalCountUpdated++;
				}
				
				// check exception or not
				if(isException) {
					importCount = 0;
				} else {
					importCount += listCodelistDesign.size();
				}
			}
			
			if(!rollback) {
				//delete data
				if(delete && (listDataSummaryInsert.size() + listDataSummaryUpdate.size() > 0)) {
					isException = deleteDataSummary(listDataSummaryDelete);
					if(i == numberOfSheet + 1) {
						List<CodeList> listRedundantCodelist = getRedundantCodelist(listDataImportCodelistDesign, projectId);
						isException = deleteDataSummary(listRedundantCodelist);
					}
				}
				//insert data
				isException = insertDataSummary(listDataSummaryInsert);
				if(!isException && listDataSummaryInsert.size() > 0) {
					codelistId = importManagementRepository.getSequencesCodelist();
					
				}
				//update data
				isException = updateDataSummary(listDataSummaryUpdate);
				
				// check exception or not
				if(isException) {
					importCount = 0;
				} else {
					importCount += listCodelistDesign.size();
				}
			}
			
			if(codelistId == null) {
				if(listDataSummaryUpdate.size() > 0) {
					codelistId = importManagementRepository.getCodelistIdByCodelistCode(listDataSummaryUpdate.get(0).getCodeListCode(), projectId);
				} else if(listDataSummaryDelete.size() > 0) {
					codelistId = importManagementRepository.getCodelistIdByCodelistCode(listDataSummaryDelete.get(0).getCodeListCode(), projectId);
				}
			}
			
			convertArrayToCodelistDetailDesign(listCodelistDetailDesign, listCodelistDetailTableField, rowsDataDetail, errorsDetail, rowsDetail, projectId, accountId, codelistId);
			setDataIntoInsertUpdateDeleteListDetail(listCodelistDetailDesign, listDataDetailInsert, listDataDetailUpdate, listDataDetailDelete, 
					listDataSummaryInsert, listDataSummaryUpdate, listDataSummaryDelete);
			
			// check transaction rollback or not
			isException = false;
			if(rollback && errorsDetail.size() == rowsDetail.size()) {
				//delete data
				if(delete && listDataDetailDelete.size() > 0) {
					isException = deleteDataDetail(listDataDetailDelete);
				}
				//insert data
				if(listDataDetailInsert.size() > 0) {
					isException = insertDataDetail(listDataDetailInsert);
				}
				//update data
				if(listDataDetailUpdate.size() > 0) {
					isException = updateDataDetail(listDataDetailUpdate, codelistId);
				}
				
				// check exception or not
//				if(isException) {
//					importCount = 0;
//				} else {
//					importCount += listDataDetailInsert.size() + listDataDetailUpdate.size();
//				}
			}
			
			if(!rollback) {
				//delete data
				if(delete && (listDataDetailInsert.size() + listDataDetailUpdate.size() > 0)) {
					isException = deleteDataDetail(listDataDetailDelete);
				}
				//insert data
				if(listDataDetailInsert.size() > 0) {
					isException = insertDataDetail(listDataDetailInsert);
				}
				//update data
				if(listDataDetailUpdate.size() > 0) {
					isException = updateDataDetail(listDataDetailUpdate, codelistId);
				}
				// check exception or not
//				if(isException) {
//					importCount = 0;
//				} else {
//					importCount += listDataDetailInsert.size() + listDataDetailUpdate.size();
//				}
			}
			
			errors.addAll(errorsSummary);
			errors.addAll(errorsDetail);
		}
		
		// set return map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put(ImportManagementConst.ERR_WHEN_IMPORT, errors);
		returnMap.put(ImportManagementConst.ROW_IMPPORTED, importCount);
		returnMap.put(ImportManagementConst.NUMBER_CODELIST, numberOfSheet);
		
		returnMap.put(ImportManagementConst.INSERT, totalCountInsert);
		returnMap.put(ImportManagementConst.UPDATE, totalCountUpdate);
		returnMap.put(ImportManagementConst.DELETE, totalCountDelete);
		
		returnMap.put(ImportManagementConst.INSERTED, totalCountInserted);
		returnMap.put(ImportManagementConst.UPDATED, totalCountUpdated);
		returnMap.put(ImportManagementConst.DELETED, totalCountDeleted);
		
		return returnMap;
	}
	
	private Map<String, List<String[]>> getInputData(String filePath) {
		Map<String, List<String[]>> inputData = new HashMap<String, List<String[]>>();
		int sheetCounter = 2;
		try {
			// Open file, call library to read file, get shared string table
			// contains all string of excel file, and get XML parser
			OPCPackage pkg = OPCPackage.open(filePath);
			XSSFReader r = new XSSFReader(pkg);
			// Read excel file from input stream, call next() to pass first sheet (sheet data source)
			Iterator<InputStream> sheets = r.getSheetsData();
			sheets.next();
			while(sheets.hasNext()) {
				SharedStringsTable sst = r.getSharedStringsTable();
				XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
				CodelistSheetHandler handler = new CodelistSheetHandler(sst);
				parser.setContentHandler(handler);
				
				List<String[]> inputDataSummary = new ArrayList<String[]>();
				List<String[]> inputDataDetail = new ArrayList<String[]>();
				InputStream sheet = sheets.next();
				InputSource inputSource = new InputSource(sheet);
				parser.parse(inputSource);
				sheet.close();
				inputDataSummary = handler.getInputDataSummary();
				
				if(inputDataSummary.size() == 0) {
					throw new BusinessException(ResultMessages.error().add(ImportManagementMessageConst.ERR_IMPORTMANAGEMENT_0005));	
				}
				
				inputDataDetail = handler.getInputDataDetail();
				
				if(inputDataDetail.size() == 0) {
					throw new BusinessException(ResultMessages.error().add(ImportManagementMessageConst.ERR_IMPORTMANAGEMENT_0005));	
				}
				
				inputData.put("SummarySheet" + sheetCounter, inputDataSummary);
				inputData.put("DetailSheet" + sheetCounter, inputDataDetail);
				sheetCounter++;
			}

		} catch (IOException | OpenXML4JException | SAXException e) {
			e.printStackTrace();
			throw new BusinessException(ResultMessages.error().add(ImportManagementMessageConst.ERR_IMPORTMANAGEMENT_0004, MessageUtils.getMessage(ImportManagementMessageConst.SC_IMPORTMANAGEMENT_0003)));
		}
		
		return inputData;
	}
	
	private List<TableField> setTableFieldRuleForCodelistTable() {
		List<TableField> columnInfo = new ArrayList<TableField>();
		TableField column = new TableField();
		
		//set codelist_name
		column.setColumnName("codelist_name");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setIsMandatory(ImportManagementConst.ISMANDATORY.YES);
		column.setPatternName(ImportManagementConst.PATTERN.YES);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0104);
		column.setIndex(0);
		columnInfo.add(0,column);

		//set codelist_code
		column = new TableField();
		column.setColumnName("codelist_code");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setIsMandatory(ImportManagementConst.ISMANDATORY.YES);
		column.setPatternCode(ImportManagementConst.PATTERN.YES);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0105);
		column.setIndex(1);
		columnInfo.add(1,column);
		
		//set values_only
		column = new TableField();
		column.setColumnName("values_only");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setIsMandatory(ImportManagementConst.ISMANDATORY.YES);
		column.setDataSource(ImportManagementConst.IMPORT_DATASOURCE.CODELIST_DESIGN_VALUES_ONLY);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0106);
		column.setIndex(2);
		columnInfo.add(2,column);
		
		//set module name
		column = new TableField();
		column.setColumnName("module_name");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0107);
		column.setIndex(3);
		columnInfo.add(3,column);
		
		//set description
		column = new TableField();
		column.setColumnName("description");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0108);
		column.setIndex(4);
		columnInfo.add(4,column);
		
		return columnInfo;
	}
	
	private List<TableField> setTableFieldRuleForCodelistDetailTable() {
		List<TableField> columnInfo = new ArrayList<TableField>();
		TableField column = new TableField();
		
		//set key
		column.setColumnName("key");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0109);
		column.setIndex(0);
		columnInfo.add(0,column);

		//set value
		column = new TableField();
		column.setColumnName("value");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setIsMandatory(ImportManagementConst.ISMANDATORY.YES);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0110);
		column.setIndex(1);
		columnInfo.add(1,column);
		
		//set value 1
		column = new TableField();
		column.setColumnName("value1");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0111);
		column.setIndex(2);
		columnInfo.add(2,column);
		
		//set value 2
		column = new TableField();
		column.setColumnName("value2");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0112);
		column.setIndex(3);
		columnInfo.add(3,column);
		
		//set value 3
		column = new TableField();
		column.setColumnName("value3");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0113);
		column.setIndex(4);
		columnInfo.add(4,column);
		
		//set value 4
		column = new TableField();
		column.setColumnName("value4");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0114);
		column.setIndex(5);
		columnInfo.add(5,column);
		
		//set value 5
		column = new TableField();
		column.setColumnName("value5");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0115);
		column.setIndex(6);
		columnInfo.add(6,column);
		
		return columnInfo;
	}
	
	private void convertArrayToCodelistDesign(List<CodeList> listCodelistDesign, List<TableField> listTableField, List<String[]> inputData, 
			List<String[]> errors, List<Integer> rows, Long projectId, Long accountId) {
		// check input data not null
		if(inputData.size() > 0) {
			List<Integer> rowsClone = new ArrayList<Integer>();
			rowsClone.addAll(rows);
			
			for(int line = 0; line < inputData.size(); line++) {
				String[] item = inputData.get(line);
				Map<String, String> moduleIdMap = new HashMap<String, String>();
				String moduleId = null;
				
				if(item[3].length() > 0) {
					List<Map<String, Object>> listModuleId = importManagementRepository.getModuleId(projectId, item[3]);
					moduleIdMap = new HashMap<String, String>();
					for(Map<String, Object> temp : listModuleId) {
						moduleIdMap.put(String.valueOf(temp.get("name")), temp.get("id").toString());
					}
				}
				
				if(moduleIdMap.size() > 0) {
					moduleId = moduleIdMap.get(item[3]);
				}
				
				CodeList codelist = new CodeList();
				
				if(moduleId != null) {
					codelist.setModuleId(Long.parseLong(moduleId));
				}
				
				codelist.setCodeListCode(item[1]);
				codelist.setCodeListName(item[0]);
				codelist.setRemark(item[4]);
				codelist.setIsOptionValude(Integer.parseInt(item[2]));
				codelist.setCreatedBy(accountId);
				codelist.setUpdatedBy(accountId);
				codelist.setProjectId(projectId);
				
				listCodelistDesign.add(codelist);
				// end check error
			}
			// end loop input data
		}
		//end check data not null
	}
	
	private void convertArrayToCodelistDetailDesign(List<CodeListDetail> listCodelistDetailDesign, List<TableField> listTableField, List<String[]> inputData, 
			List<String[]> errors, List<Integer> rows, Long projectId, Long accountId, Long codeListId) {
		// check input data not null
		if(inputData.size() > 0) {
			List<Integer> rowsClone = new ArrayList<Integer>();
			rowsClone.addAll(rows);
			Timestamp systemDate =  FunctionCommon.getCurrentTime();
			
			for(int line = 0; line < inputData.size(); line++) {
				String[] item = inputData.get(line);
				
				CodeListDetail codelistDetail = new CodeListDetail();
				if(codeListId != null) {
					Long codelistDetailId = importManagementRepository.getCodelistDetailId(codeListId, item[0], item[1]);
					codelistDetail.setClDeatailId(codelistDetailId);
					codelistDetail.setCodeListId(codeListId);
				}
				codelistDetail.setName(item[0]);
				codelistDetail.setValue(item[1]);
				codelistDetail.setValue1(item[2]);
				codelistDetail.setValue2(item[3]);
				codelistDetail.setValue3(item[4]);
				codelistDetail.setValue4(item[5]);
				codelistDetail.setValue5(item[6]);
				codelistDetail.setIsDefault(0);
				codelistDetail.setCreatedBy(accountId);
				codelistDetail.setCreatedDate(systemDate);
				codelistDetail.setUpdatedBy(accountId);
				codelistDetail.setUpdatedDate(systemDate);
				
				listCodelistDetailDesign.add(codelistDetail);
				// end check error
			}
			// end loop input data
		}
		//end check data not null
	}
	
	private void setDataIntoInsertUpdateDeleteListSummary(List<CodeList> listData, List<CodeList> listDataInsert, List<CodeList> listDataUpdate, List<CodeList> listDataDelete, 
			List<Integer> rows, List<String[]> errors, Long projectId, boolean delete) {
		//check input data not null
		if(listData.size() > 0) {
			List<Map<String, Object>> listReference = new ArrayList<Map<String,Object>>();
			Map<String, String> referenceMap = new HashMap<String, String>();
			Map<String, String> dataUpdateMap = new HashMap<String, String>();	
			if(delete) {
				listReference = importManagementRepository.getReferenceCodelistDesign(projectId);
				for (Map<String, Object> item : listReference) {
					referenceMap.put(String.valueOf(item.get("name")), item.get("id").toString());
				}
			}
			
			List<Map<String, Object>> listDataMap = importManagementRepository.getCodeKeyOfCodelistDesign(listData.get(0).getCodeListCode(), projectId);
			Map<String, String> itemsMap = new HashMap<String, String>();
			
			for (Map<String, Object> item : listDataMap) {
				itemsMap.put(String.valueOf(item.get("name")), item.get("id").toString());
				
				//add item not reference into deleteList
				if(delete && referenceMap.get(item.get("name")) == null) {
					CodeList codelistDesign = new CodeList();
					codelistDesign.setCodeListId(Long.parseLong(item.get("id").toString()));
					codelistDesign.setCodeListName(String.valueOf(item.get("name")));
					listDataDelete.add(codelistDesign);				
				}
			}
			
			// Add item into insert/update list	
			for (int line = 0; line < listData.size(); line++) {
				CodeList item = listData.get(line);
				String codeKey = item.getCodeListCode();
				String idValue = null;
				idValue = itemsMap.get(codeKey);
				int rowsIndex = rows.get(line);
				String[] msgError = new String[3];
				msgError[0] = String.valueOf(rowsIndex);
				msgError[2] = null;
				if (idValue == null) {
					listDataInsert.add(item);
					msgError[1] = ImportManagementConst.IMPORT_RESULT_STATUS.REGISTER;
					
				} else {
					listDataUpdate.add(item);
					msgError[1] = ImportManagementConst.IMPORT_RESULT_STATUS.MODIFY;
					if(delete){
						dataUpdateMap.put(idValue, codeKey);
					}
				}
				errors.set(rowsIndex,msgError);
			}
						
			//remove updateItem in DeleteList  
			if(delete && listDataUpdate.size() > 0) {
				List<CodeList> listDataDeleteRemove = new ArrayList<CodeList>();
				for(int i=0; i<listDataDelete.size();i++) {
					CodeList item = listDataDelete.get(i);
					if(dataUpdateMap.get(item.getCodeListId().toString()) != null) {
						listDataDeleteRemove.add(item);
					}
				}
				listDataDelete.removeAll(listDataDeleteRemove);
			}	
			
		}//end check input data not null
	}
	
	private void setDataIntoInsertUpdateDeleteListDetail(List<CodeListDetail> listData, List<CodeListDetail> listDataInsertDetail, List<CodeListDetail> listDataUpdateDetail, 
			List<CodeListDetail> listDataDeleteDetail, List<CodeList> listDataInsertSummary, List<CodeList> listDataUpdateSummary, List<CodeList> listDataDeleteSummary) {
		if(listData.size() > 0) {
			for(CodeListDetail itemDetail : listData) {
				
				if(listDataInsertSummary.size() > 0) {
					listDataInsertDetail.add(itemDetail);
				}
				
				if(listDataUpdateSummary.size() > 0) {
					listDataUpdateDetail.add(itemDetail);
				}
				
				if(listDataDeleteSummary.size() > 0) {
					listDataDeleteDetail.add(itemDetail);
				}
			}
		}
	}
	
	//delete data summary
	private boolean deleteDataSummary(List<CodeList> listCodelistDesign) {
		boolean isException = false;
		
		if(listCodelistDesign.size() > 0) {
			for(int i = 0; i < listCodelistDesign.size(); i++) {
				CodeList codelistDesign = listCodelistDesign.get(i);		
				try {
					importManagementRepository.deleteCodelistDetailDesignBeforeUpdate(codelistDesign.getCodeListId());
					importManagementRepository.deleteCodelistDesign(codelistDesign.getCodeListId());
				}
				catch(Exception ex) {
					ex.printStackTrace();
					isException = true;
				}
			}			
		}		
		return isException;
	}
	
	// insert data summary
	private boolean insertDataSummary(List<CodeList> listCodelistDesign) {		
		boolean isException = false;
		if(listCodelistDesign.size() > 0) {					
			int rowsDataSize = listCodelistDesign.size();
			int startRowIndex = 0;
			int endRowIndex = 0;
						
			while (rowsDataSize > 0 && endRowIndex < rowsDataSize) {
				List<CodeList> subListCodelistDesign = new ArrayList<CodeList>();
				endRowIndex = startRowIndex + LIMIT_ROW_IMPORT;
				if(endRowIndex >= rowsDataSize) {
					endRowIndex = rowsDataSize;
				}
				subListCodelistDesign = listCodelistDesign.subList(startRowIndex, endRowIndex);			
				// insert data into database							
				try {
					importManagementRepository.insertCodelistDesign(subListCodelistDesign);					
				}
				catch(Exception ex) {
					ex.printStackTrace();
					isException = true;
				}
				// set index
				startRowIndex = endRowIndex;
			}					
		}
		return isException;
	}
	
	//update data summary
	private boolean updateDataSummary(List<CodeList> listCodelistDesign) {
		boolean isException = false;
		if(listCodelistDesign.size() > 0) {
			int rowsDataSize = listCodelistDesign.size();
			int startRowIndex = 0;
			int endRowIndex = 0;	
			
			while (rowsDataSize > 0 && endRowIndex < rowsDataSize) {
				List<CodeList> subListCodelistDesign = new ArrayList<CodeList>();
				endRowIndex = startRowIndex + LIMIT_ROW_IMPORT;
				if(endRowIndex >= rowsDataSize) {
					endRowIndex = rowsDataSize;
				}
				subListCodelistDesign = listCodelistDesign.subList(startRowIndex, endRowIndex);	
				// update data into database							
				try {
					importManagementRepository.updateCodelistDesign(subListCodelistDesign);					
				}
				catch(Exception ex) {
					ex.printStackTrace();
					isException = true;
				}
				// set index
				startRowIndex = endRowIndex;
			}					
		}
		return isException;
	}
	
	//delete data detail
	private boolean deleteDataDetail(List<CodeListDetail> listCodelistDetailDesign) {
		boolean isException = false;
		
		if(listCodelistDetailDesign.size() > 0) {
			for(int i = 0; i < listCodelistDetailDesign.size(); i++) {
				CodeListDetail codelistDetailDesign = listCodelistDetailDesign.get(i);		
				try {
					importManagementRepository.deleteCodelistDetailDesign(codelistDetailDesign.getCodeListId());
				}
				catch(Exception ex) {
					ex.printStackTrace();
					isException = true;
				}
			}			
		}		
		return isException;
	}
	
	// insert data detail
	private boolean insertDataDetail(List<CodeListDetail> listCodelistDetailDesign) {		
		boolean isException = false;
		if(listCodelistDetailDesign.size() > 0) {					
			int rowsDataSize = listCodelistDetailDesign.size();
			int startRowIndex = 0;
			int endRowIndex = 0;
						
			while (rowsDataSize > 0 && endRowIndex < rowsDataSize) {
				List<CodeListDetail> subListCodelistDesign = new ArrayList<CodeListDetail>();
				endRowIndex = startRowIndex + LIMIT_ROW_IMPORT;
				if(endRowIndex >= rowsDataSize) {
					endRowIndex = rowsDataSize;
				}
				subListCodelistDesign = listCodelistDetailDesign.subList(startRowIndex, endRowIndex);			
				// insert data into database							
				try {
					importManagementRepository.insertCodelistDetailDesign(subListCodelistDesign);					
				}
				catch(Exception ex) {
					ex.printStackTrace();
					isException = true;
				}
				// set index
				startRowIndex = endRowIndex;
			}					
		}
		return isException;
	}
	
	//update data detail
	private boolean updateDataDetail(List<CodeListDetail> listCodelistDetailDesign, Long codelistId) {
		boolean isException = false;
		if(listCodelistDetailDesign.size() > 0) {
			int rowsDataSize = listCodelistDetailDesign.size();
			int startRowIndex = 0;
			int endRowIndex = 0;	
			
			while (rowsDataSize > 0 && endRowIndex < rowsDataSize) {
				List<CodeListDetail> subListCodelistDesign = new ArrayList<CodeListDetail>();
				endRowIndex = startRowIndex + LIMIT_ROW_IMPORT;
				if(endRowIndex >= rowsDataSize) {
					endRowIndex = rowsDataSize;
				}
				subListCodelistDesign = listCodelistDetailDesign.subList(startRowIndex, endRowIndex);
				// delete all codelist detail and then
				// insert data into database
				try {
					importManagementRepository.deleteCodelistDetailDesignBeforeUpdate(codelistId);
					importManagementRepository.insertCodelistDetailDesign(subListCodelistDesign);					
				}
				catch(Exception ex) {
					ex.printStackTrace();
					isException = true;
				}
				// set index
				startRowIndex = endRowIndex;
			}					
		}
		return isException;
	}
	
	//add codelist in db but not in document to delete list
	private List<CodeList> getRedundantCodelist(List<CodeList> listData, Long projectId) {
		List<CodeList> listCodelistInDocumentAndDatabase = new ArrayList<CodeList>();
		List<CodeList> listAllProjectCodelist = codelistRepository.getCodeListByProject(projectId);
		
		if(listData.size() > 0) {
			for(CodeList codelistInDatabase : listAllProjectCodelist) {
				for(CodeList codelistInDocument : listData) {
					if(codelistInDocument.getCodeListCode().equals(codelistInDatabase.getCodeListCode())) {
						listCodelistInDocumentAndDatabase.add(codelistInDatabase);
					}
				}
			}
			
			if(listCodelistInDocumentAndDatabase.size() > 0) {
				listAllProjectCodelist.removeAll(listCodelistInDocumentAndDatabase);
				return listAllProjectCodelist;
			}
		}
		
		return new ArrayList<CodeList>();
	}
}
