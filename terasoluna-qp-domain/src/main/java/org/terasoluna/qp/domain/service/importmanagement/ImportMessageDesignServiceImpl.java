package org.terasoluna.qp.domain.service.importmanagement;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.GenerateDocumentMessageConst;
import org.terasoluna.qp.app.message.ImportManagementMessageConst;
import org.terasoluna.qp.domain.model.ImportManagement;
import org.terasoluna.qp.domain.model.LanguageDesign;
import org.terasoluna.qp.domain.model.MessageDesign;
import org.terasoluna.qp.domain.repository.importmanagement.ImportManagementRepository;
import org.terasoluna.qp.domain.repository.languagedesign.LanguageDesignRepository;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

@Service
@Transactional(rollbackFor = Exception.class)
public class ImportMessageDesignServiceImpl  implements ImportMessageDesignService {
	
	@Inject
	ImportManagementRepository importManagementRepository;
	
	@Inject
	LanguageDesignRepository languageDesignRepository;
	
	@Inject
	PlatformTransactionManager platformTransactionManager;
	
	private final String SHEET_ID = "rId2";
	
	private int LIMIT_ROW_IMPORT = 0;
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> importData(ImportManagement importManagement, Long projectId , Long accountId ) {
					
		String filePath = importManagement.getFilePath();
		boolean rollback = importManagement.isRollback();
		boolean delete = importManagement.isDelete();
		List<String[]> inputData = new ArrayList<String[]>();
		int  importCount = 0;
		LIMIT_ROW_IMPORT = ImportUtils.getLimitRowImport();
		//get input data to file
		inputData = this.getInputData(filePath);
		
		//set table rule for validate
		List<TableField> listTableField  = setTableFieldRuleForMessageTable();
		
		// validate data type and validate business of import data 
		Map<String, Object> map = ImportUtils.validate(inputData, listTableField);
		
		// result after validate
		List<String[]> errors = (List<String[]>) map.get(ImportManagementConst.ERR_WHEN_IMPORT);
		List<String[]> rowsData = new ArrayList<String[]>();
		rowsData = (List<String[]>) map.get(ImportManagementConst.IMPORTTING_DATA);
		List<Integer> rows = (List<Integer>) map.get(ImportManagementConst.ROWS);
		
		//convert string array to Message design list
		List<MessageDesign> listMessageDesign = new ArrayList<MessageDesign>();
		convertArrayToMessageDesign(listMessageDesign, listTableField, rowsData, errors, rows, projectId, accountId);
		
		//set data into insert/update list
		List<MessageDesign> listDataInsert = new ArrayList<MessageDesign>();
		List<MessageDesign> listDataUpdate = new ArrayList<MessageDesign>();
		setDataIntoInsertUpdateList(listMessageDesign,listDataInsert,listDataUpdate, rows, errors, projectId);
		
		// check transaction rollback or not
		boolean isException = false;
		if(rollback && errors.size() == rows.size()){			
			//delete data
			if(delete){
				isException = deleteData(projectId);
				isException = insertData(listMessageDesign);
			}else{
				//insert data
				isException = insertData(listDataInsert);
				//update data
				isException = updateData(listDataUpdate);
			}
			// check exception or not
			if(isException){				
				importCount = 0;		
			}else{
				importCount = listMessageDesign.size();
			}
		}
		if(!rollback){			
			//delete data
			if(delete && (listDataInsert.size()+listDataUpdate.size() > 0)){
				isException = deleteData(projectId);
				isException = insertData(listMessageDesign);
			}else{
				//insert data
				isException = insertData(listDataInsert);
				//update data
				isException = updateData(listDataUpdate);	
			}
			// check exception or not
			if(isException){				
				importCount = 0;		
			}else{
				importCount = listMessageDesign.size();
			}
		}
		
			
		// set return map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put(ImportManagementConst.ERR_WHEN_IMPORT, errors);
		returnMap.put(ImportManagementConst.ROW_IMPPORTED, importCount);
		return returnMap;
	}
	
	
	//insert data
	private boolean insertData(List<MessageDesign> listMessageDesign) {
		
		boolean isException = false;
		if(listMessageDesign.size()>0){					
			int rowsDataSize = listMessageDesign.size();
			int startRowIndex = 0;
			int endRowIndex = 0;
						
			while (rowsDataSize > 0 && endRowIndex < rowsDataSize) {
				List<MessageDesign> subListMessageDesign = new ArrayList<MessageDesign>();
				endRowIndex = startRowIndex + LIMIT_ROW_IMPORT;
				if(endRowIndex >= rowsDataSize) {
					endRowIndex = rowsDataSize;
				}
				subListMessageDesign = listMessageDesign.subList(startRowIndex, endRowIndex);			
				// insert data into database							
				try {
					importManagementRepository.insertMessageDesign(subListMessageDesign);					
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
	
	//update data
	private boolean updateData(List<MessageDesign> listMessageDesign) {
		
		boolean isException = false;
		if(listMessageDesign.size()>0){
			int rowsDataSize = listMessageDesign.size();
			int startRowIndex = 0;
			int endRowIndex = 0;	
			
			while (rowsDataSize > 0 && endRowIndex < rowsDataSize) {
				List<MessageDesign> subListMessageDesign = new ArrayList<MessageDesign>();
				endRowIndex = startRowIndex + LIMIT_ROW_IMPORT;
				if(endRowIndex >= rowsDataSize) {
					endRowIndex = rowsDataSize;
				}
				subListMessageDesign = listMessageDesign.subList(startRowIndex, endRowIndex);	
				// update data into database							
				try {
					importManagementRepository.updateMessageDesign(subListMessageDesign);					
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
		
	// delete data
	private boolean deleteData(Long projectId) {
		boolean isException = false;
		try {
			importManagementRepository.deleteMessageDesign(projectId);
		} catch (Exception ex) {
			ex.printStackTrace();
			isException = true;
		}
		return isException;
	}
			
	public List<String[]> getInputData(String filePath) {
		
		List<String[]> inputData = new ArrayList<String[]>();	
		try {
			// Open file, call library to read file, get shared string table contains all string of excel file, and get XML parser
			OPCPackage pkg = OPCPackage.open(filePath);
			XSSFReader r = new XSSFReader(pkg);
			SharedStringsTable sst = r.getSharedStringsTable();
			XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
			SheetHandler handler = new SheetHandler(sst);
			parser.setContentHandler(handler);
			// Read excel file from input stream
			InputStream sheet1 = r.getSheet(SHEET_ID);
			InputSource sheetSource = new InputSource(sheet1);
			parser.parse(sheetSource);
			sheet1.close();
			inputData = handler.getInputData();
			
		} 
		catch (IOException | OpenXML4JException | SAXException e) {
			e.printStackTrace();
			throw new BusinessException(ResultMessages.error().add(ImportManagementMessageConst.ERR_IMPORTMANAGEMENT_0004, MessageUtils.getMessage(ImportManagementMessageConst.SC_IMPORTMANAGEMENT_0003)));
		}
		if(inputData.size()==0){
			throw new BusinessException(ResultMessages.error().add(ImportManagementMessageConst.ERR_IMPORTMANAGEMENT_0005));
		}
		return inputData;
	}
	
	private void convertArrayToMessageDesign(List<MessageDesign> listMessageDesign,List<TableField> listTableField, List<String[]> inputData, List<String[]> errors,List<Integer> rows, Long projectId, Long accountId){
			
		// check input data not null
		if(inputData.size() > 0 ){
			List<Integer> rowsClone = new ArrayList<Integer>();
			rowsClone.addAll(rows);
			List<LanguageDesign> listLanguageDesign = languageDesignRepository.findLanguageByProjectId(projectId);
			Map<String, Long> mapLanguage = new HashMap<String, Long>();
			
			for(LanguageDesign item : listLanguageDesign) {
				mapLanguage.put(item.getLanguageName(), item.getLanguageId());
			}
			
			List<Map<String, Object>> listModuleId= importManagementRepository.getModuleIdByProjectId(projectId);
			Map<String, String> moduleIdMap = new HashMap<String, String>();
			for(Map<String, Object> item : listModuleId) {
				moduleIdMap.put(String.valueOf(item.get("name")),item.get("id").toString());
			}
			Timestamp systemDate =  FunctionCommon.getCurrentTime();	
			for (int line = 0; line < inputData.size(); line++) {
				
				String[] item = inputData.get(line);
				List<String> errorInLine = new ArrayList<String>();
				int rowsIndex = rowsClone.get(line);
				boolean lineError = false;
				
				MessageDesign messageDesign = new MessageDesign();
				
				messageDesign.setMessageCode(item[0]);
				messageDesign.setMessageLevel(Integer.parseInt(item[1]));
				messageDesign.setMessageType(item[2]);
				messageDesign.setMessageString(item[3]);
				messageDesign.setRemark(item[4]);
				
				//validate business
				Long languageId = mapLanguage.get(item[5]);
				if(languageId == null){
					errorInLine.add(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(listTableField.get(5).getColumnMessage())));	
					lineError = true;
				}else{
					messageDesign.setLanguageId(mapLanguage.get(item[5]));
				}
				if(moduleIdMap.get(getModuleCode(item[0])) != null){
					messageDesign.setModuleId(Long.parseLong(moduleIdMap.get(getModuleCode(item[0]))));
				}
				
				messageDesign.setProjectId(projectId);
				messageDesign.setGeneratedStatus(2);
				messageDesign.setCreatedBy(accountId);
				messageDesign.setUpdatedBy(accountId);
				messageDesign.setCreatedDate(systemDate);
				messageDesign.setUpdatedDate(systemDate);
				
				if(lineError){
					Object rowValue = rowsIndex ;
					rows.remove(rowValue);
					errorInLine.add(0, String.valueOf(rowsIndex));
					errorInLine.add(1, String.valueOf(ImportManagementConst.IMPORT_RESULT_STATUS.ERROR));				
					errors.set(rowsIndex,(String[]) errorInLine.toArray(new String[errorInLine.size()]));	
				} else {
					listMessageDesign.add(messageDesign);
				}// end check error			
			}//end loop inputData
		}//end check data not null
	}

	private String getModuleCode(String id) {
		String moduleCode = null;
		if (id.contains("_")) {
			String[] parts = id.split("_");
			moduleCode = parts[1];
		}	
		return moduleCode;
	}
	
	private void setDataIntoInsertUpdateList(List<MessageDesign> listData, List<MessageDesign> listDataInsert, List<MessageDesign> listDataUpdate, List<Integer> rows, List<String[]> errors,Long projectId) {
		
		
		//check input data not null
		if(listData.size() > 0){
			List<Map<String, Object>> listDataMap = importManagementRepository.getCodeKeyOfMessageDesign(projectId);
			Map<String, String> itemsMap = new HashMap<String, String>();
			
			for (Map<String, Object> item : listDataMap) {
				itemsMap.put(String.valueOf(item.get("name")), item.get("id").toString());
			}
					
			for (int line = 0; line < listData.size(); line++) {
				MessageDesign item = listData.get(line);
				String codeKey = item.getMessageCode() +"_"+ item.getLanguageId();
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
				}
				errors.set(rowsIndex,msgError);
			}//end loop input data
		}//end check input data not null
	}
	
	private List<TableField> setTableFieldRuleForMessageTable(){
		List<TableField> columnInfo = new ArrayList<TableField>();
												
		TableField column = new TableField();
		
		//set message_code
		column.setColumnName("message_code");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setIsMandatory(ImportManagementConst.ISMANDATORY.YES);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0022);
		column.setIndex(0);
		columnInfo.add(0,column);

		//set Output location
		column = new TableField();
		column.setColumnName("message_level");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setIsMandatory(ImportManagementConst.ISMANDATORY.YES);
		column.setDataSource(ImportManagementConst.IMPORT_DATASOURCE.MESSAGE_DESIGN_OUTPUT_LOCATION);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0040);
		column.setIndex(1);
		columnInfo.add(1,column);
		
		//set Error level
		column = new TableField();
		column.setColumnName("message_type");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setIsMandatory(ImportManagementConst.ISMANDATORY.YES);
		column.setDataSource(ImportManagementConst.IMPORT_DATASOURCE.MESSAGE_DESIGN_ERROR_LEVEL);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0041);
		column.setIndex(2);
		columnInfo.add(2,column);
		
		//set Message string
		column = new TableField();
		column.setColumnName("message_string");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setIsMandatory(ImportManagementConst.ISMANDATORY.YES);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0038);
		column.setIndex(3);
		columnInfo.add(3,column);
		
		//set Description
		column = new TableField();
		column.setColumnName("remark");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0031);
		column.setIndex(4);
		columnInfo.add(4,column);
		
		
		//set Language
		column = new TableField();
		column.setColumnName("language_id");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setIsMandatory(ImportManagementConst.ISMANDATORY.YES);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0092);
		column.setIndex(5);
		columnInfo.add(5,column);
					
		return columnInfo;
	}
}
