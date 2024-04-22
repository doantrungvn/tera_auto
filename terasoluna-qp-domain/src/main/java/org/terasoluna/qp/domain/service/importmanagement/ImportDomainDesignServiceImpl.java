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
import org.terasoluna.qp.domain.model.DomainDesign;
import org.terasoluna.qp.domain.model.ImportManagement;
import org.terasoluna.qp.domain.repository.importmanagement.ImportManagementRepository;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;


@Service
@Transactional(rollbackFor = Exception.class)
public class ImportDomainDesignServiceImpl  implements ImportDomainDesignService {
	@Inject
	ImportManagementRepository importManagementRepository;

	@Inject
	PlatformTransactionManager platformTransactionManager;
	
	private final String SHEET_ID = "rId2";
	private int LIMIT_ROW_IMPORT = 0;
	
	@Override
	public Map<String, Object> importData(ImportManagement importManagement, Long projectId, Long accountId) {
		
		String filePath = importManagement.getFilePath();
		boolean rollback = importManagement.isRollback();
		boolean delete = importManagement.isDelete();
		List<String[]> inputData = new ArrayList<String[]>();
		int importCount = 0;
		LIMIT_ROW_IMPORT = ImportUtils.getLimitRowImport();
		
		//get input data to file
		inputData = this.getInputData(filePath);
		
		//set table rule for validate
		List<TableField> listTableField  = setTableFieldRuleForDomainTable();
		
		// validate data type and validate business of import data 
		Map<String, Object> map = ImportUtils.validate(inputData,listTableField);
	
		// result after validate
		List<String[]> errors = (List<String[]>) map.get(ImportManagementConst.ERR_WHEN_IMPORT);
		List<String[]> rowsData = new ArrayList<String[]>();
		rowsData = (List<String[]>) map.get(ImportManagementConst.IMPORTTING_DATA);
		List<Integer> rows = (List<Integer>) map.get(ImportManagementConst.ROWS);
		
		//convert string array to Domain design list
		List<DomainDesign> listDomainDesign = new ArrayList<DomainDesign>();
		convertArrayToDomainDesign(listDomainDesign,listTableField, rowsData, errors, rows, projectId, accountId);
		
		//set data into insert/update/delete list
		List<DomainDesign> listDataInsert = new ArrayList<DomainDesign>();
		List<DomainDesign> listDataUpdate = new ArrayList<DomainDesign>();
		List<DomainDesign> listDataDelete = new ArrayList<DomainDesign>();
		setDataIntoInsertUpdateDeleteList(listDomainDesign,listDataInsert,listDataUpdate,listDataDelete,rows, errors,projectId,delete);
		
		
		/*TransactionDefinition transaction = null;
		TransactionStatus status = null;
		transaction = new DefaultTransactionDefinition();
		status = platformTransactionManager.getTransaction(transaction);
		platformTransactionManager.rollback(status);
		platformTransactionManager.commit(status);*/
		
		// check transaction rollback or not
		boolean isException = false;
		if(rollback && errors.size() == rows.size()){				
			//delete data
			if(delete){
				isException = deleteData(listDataDelete);				
			}		
			//insert data
			isException = insertData(listDataInsert);
			//update data
			isException = updateData(listDataUpdate);
			
			// check exception or not
			if(isException){				
				importCount = 0;
			}else{
				importCount = listDomainDesign.size();
			}
		}
		if(!rollback){
			//delete data
			if(delete && (listDataInsert.size()+listDataUpdate.size() > 0)){
				isException = deleteData(listDataDelete);				
			}
			//insert data
			isException = insertData(listDataInsert);	
			//update data
			isException = updateData(listDataUpdate);	
			
			// check exception or not
			if(isException){				
				importCount = 0;
			}else{
				importCount = listDomainDesign.size();
			}
		}
		
		
		// set return map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put(ImportManagementConst.ERR_WHEN_IMPORT, errors);
		returnMap.put(ImportManagementConst.ROW_IMPPORTED, importCount);
		return returnMap;
	}
	
	// insert data
	private boolean insertData(List<DomainDesign> listDomainDesign) {		
		boolean isException = false;
		if(listDomainDesign.size()>0){					
			int rowsDataSize = listDomainDesign.size();
			int startRowIndex = 0;
			int endRowIndex = 0;
						
			while (rowsDataSize > 0 && endRowIndex < rowsDataSize) {
				List<DomainDesign> subListDomainDesign = new ArrayList<DomainDesign>();
				endRowIndex = startRowIndex + LIMIT_ROW_IMPORT;
				if(endRowIndex >= rowsDataSize) {
					endRowIndex = rowsDataSize;
				}
				subListDomainDesign = listDomainDesign.subList(startRowIndex, endRowIndex);			
				// insert data into database							
				try {
					importManagementRepository.insertDomainDesign(subListDomainDesign);					
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
	private boolean updateData(List<DomainDesign> listDomainDesign) {
		boolean isException = false;
		if(listDomainDesign.size()>0){
			int rowsDataSize = listDomainDesign.size();
			int startRowIndex = 0;
			int endRowIndex = 0;	
			
			while (rowsDataSize > 0 && endRowIndex < rowsDataSize) {
				List<DomainDesign> subListDomainDesign = new ArrayList<DomainDesign>();
				endRowIndex = startRowIndex + LIMIT_ROW_IMPORT;
				if(endRowIndex >= rowsDataSize) {
					endRowIndex = rowsDataSize;
				}
				subListDomainDesign = listDomainDesign.subList(startRowIndex, endRowIndex);	
				// update data into database							
				try {
					importManagementRepository.updateDomainDesign(subListDomainDesign);					
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
	
	//delete data
	private boolean deleteData(List<DomainDesign> listDomainDesign){
		boolean isException = false;
		
		if(listDomainDesign.size()>0){
			for(int i=0; i<listDomainDesign.size();i++){
				DomainDesign domainDesign = listDomainDesign.get(i);		
				try {
					importManagementRepository.deleteDomainDesign(domainDesign.getDomainId());				
				}
				catch(Exception ex) {
					ex.printStackTrace();
					isException = true;
				}
			}			
		}		
		return isException;
	}
		
	private List<String[]> getInputData(String filePath) {

		List<String[]> inputData = new ArrayList<String[]>();
		try {
			// Open file, call library to read file, get shared string table
			// contains all string of excel file, and get XML parser
			OPCPackage pkg = OPCPackage.open(filePath);
			XSSFReader r = new XSSFReader(pkg);
			SharedStringsTable sst = r.getSharedStringsTable();
			XMLReader parser = XMLReaderFactory
					.createXMLReader("org.apache.xerces.parsers.SAXParser");
			DomainSheetHandler handler = new DomainSheetHandler(sst);
			parser.setContentHandler(handler);
			// Read excel file from input stream
			InputStream sheet1 = r.getSheet(SHEET_ID);
			InputSource sheetSource = new InputSource(sheet1);
			parser.parse(sheetSource);
			sheet1.close();
			inputData = handler.getInputData();

			}catch (IOException | OpenXML4JException | SAXException e) {
				e.printStackTrace();
				throw new BusinessException(ResultMessages.error().add(ImportManagementMessageConst.ERR_IMPORTMANAGEMENT_0004, MessageUtils.getMessage(ImportManagementMessageConst.SC_IMPORTMANAGEMENT_0003)));
			}
			if(inputData.size()==0){
				throw new BusinessException(ResultMessages.error().add(ImportManagementMessageConst.ERR_IMPORTMANAGEMENT_0005));
				
			}
		return inputData;
	}
	
	private void convertArrayToDomainDesign(List<DomainDesign> listDomainDesign,List<TableField> listTableField, List<String[]> inputData, List<String[]> errors,List<Integer> rows, Long projectId, Long accountId ){
		
		
		// check input data not null
		if(inputData.size() > 0 ){
			List<Integer> rowsClone = new ArrayList<Integer>();
			rowsClone.addAll(rows);
			List<Map<String, Object>> listBaseType = importManagementRepository.getBaseType();
			Map<String, String> BaseTypeMap = new HashMap<String, String>();
			for (Map<String, Object> item : listBaseType) {
				BaseTypeMap.put(String.valueOf(item.get("name")), item.get("id").toString());
				
			}
			
			List<Map<String, Object>> listValidationRule = importManagementRepository.getValidationRule();
			Map<String, String> ValidationRuleMap = new HashMap<String, String>();
			for (Map<String, Object> item : listValidationRule) {
				ValidationRuleMap.put(String.valueOf(item.get("name")), item.get("id").toString());
			}
			
			Map<String, String> ValidationRuleByGroupBasetypeMap = initValidationRuleByGroupBasetype();
			
			Timestamp systemDate =  FunctionCommon.getCurrentTime();
			for (int line = 0; line < inputData.size(); line++) {
				
				String[] item = inputData.get(line);
				List<String> errorInLine = new ArrayList<String>();
				int rowsIndex = rowsClone.get(line);
				boolean lineError = false;
				
				DomainDesign domainDesign = new DomainDesign();
	
				
				domainDesign.setMajorClassification(item[0]);
				domainDesign.setSubClassification(item[1]);
				domainDesign.setMinorClassification(item[2]);
				domainDesign.setDomainName(item[3]);
				domainDesign.setDomainCode(item[4]);	
				
				//set BaseType + GroupBasetypeId 
				String baseType = null;
				String[] baseTypeStr = new String[2]; 
				baseType = BaseTypeMap.get(item[5]);
				if(baseType == null){
					errorInLine.add(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(listTableField.get(5).getColumnMessage())));	
					lineError = true;
				}else{
					baseTypeStr = baseType.split("_");  			
					domainDesign.setBaseType(Integer.parseInt(baseTypeStr[0]));
					domainDesign.setGroupBasetypeId(Integer.parseInt(baseTypeStr[1]));	
				}
				
				domainDesign.setMaxLength(Integer.parseInt(item[6]));
				domainDesign.setMandatoryflg(0);
				domainDesign.setRemark(item[7]);
				domainDesign.setMinVal(item[8]);
				domainDesign.setMaxVal(item[9]);
				domainDesign.setDefaultValue(item[10]);
				
				//set validation rule
				if(item[11].trim().length() > 0){
					String FmtCode = ValidationRuleMap.get(item[11]);
					if(FmtCode == null){
						errorInLine.add(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(listTableField.get(11).getColumnMessage())));	
						lineError = true;
					}else{
						if(baseType !=null){
							if(baseTypeStr[1].equals("1")){
								domainDesign.setFmtCode(ValidationRuleMap.get(item[11]));
							}
							else{
								domainDesign.setFmtCode(ValidationRuleByGroupBasetypeMap.get(domainDesign.getGroupBasetypeId().toString()));
							}
						}
					}
				}
				//set default data
				//domainDesign.setPrecision(0);		
				domainDesign.setProjectId(projectId);
				domainDesign.setConstrainsType(null);
				domainDesign.setDatasourceId(null);
				domainDesign.setDatasourceType(null);
				domainDesign.setOperatorCode(null);
				domainDesign.setDefaultType(null);
				domainDesign.setCreatedBy(accountId);
				domainDesign.setCreatedDate(systemDate);
				domainDesign.setUpdatedBy(accountId);
				domainDesign.setUpdatedDate(systemDate);
				
				if(lineError){
					Object rowValue = rowsIndex ;
					rows.remove(rowValue);
					errorInLine.add(0, String.valueOf(rowsIndex));
					errorInLine.add(1, String.valueOf(ImportManagementConst.IMPORT_RESULT_STATUS.ERROR));				
					errors.set(rowsIndex,(String[]) errorInLine.toArray(new String[errorInLine.size()]));	
				} else {
					listDomainDesign.add(domainDesign);
				}// end check error
			}//end loop inputData
		}//end check data not null
	}
	
	private void setDataIntoInsertUpdateDeleteList(List<DomainDesign> listData, List<DomainDesign> listDataInsert, List<DomainDesign> listDataUpdate, List<DomainDesign> listDataDelete, 
			List<Integer> rows,List<String[]> errors, Long projectId, boolean delete) {
			
		//check input data not null
		if(listData.size() > 0){
			List<Map<String, Object>> listReference = new ArrayList<Map<String,Object>>();
			Map<String, String> referenceMap = new HashMap<String, String>();
			Map<String, String> dataUpdateMap = new HashMap<String, String>();	
			if(delete){
				listReference = importManagementRepository.getReferenceDomainDesign(projectId);
				for (Map<String, Object> item : listReference) {
					referenceMap.put(String.valueOf(item.get("name")), item.get("id").toString());
				}
			}
			
			List<Map<String, Object>> listDataMap = importManagementRepository.getCodeKeyOfDomainDesign(projectId);
			Map<String, String> itemsMap = new HashMap<String, String>();
			
			for (Map<String, Object> item : listDataMap) {
				itemsMap.put(String.valueOf(item.get("name")), item.get("id").toString());
				
				//add item not reference into deleteList
				if(delete && referenceMap.get(item.get("name")) == null){
					DomainDesign domainDesign = new DomainDesign();
					domainDesign.setDomainId(Long.parseLong(item.get("id").toString()));
					domainDesign.setDomainCode(String.valueOf(item.get("name")));
					listDataDelete.add(domainDesign);				
				}
			}
			
			// Add item into insert/update list	
			for (int line = 0; line < listData.size(); line++) {
				DomainDesign item = listData.get(line);
				String codeKey = item.getDomainCode();
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
			if(delete && listDataUpdate.size() > 0){
				List<DomainDesign> listDataDeleteRemove = new ArrayList<DomainDesign>();
				for(int i=0; i<listDataDelete.size();i++){
					DomainDesign item = listDataDelete.get(i);
					if(dataUpdateMap.get(item.getDomainId().toString()) != null){
						listDataDeleteRemove.add(item);
					}
				}
				listDataDelete.removeAll(listDataDeleteRemove);
			}	
			
		}//end check input data not null
	}
	
	private List<TableField> setTableFieldRuleForDomainTable(){
		List<TableField> columnInfo = new ArrayList<TableField>();
			
		TableField column = new TableField();
		
		//set major_classification
		column.setColumnName("major_classification");
		column.setIndex(0);
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0023);
		columnInfo.add(0,column);

		//set sub_classification
		column = new TableField();
		column.setColumnName("sub_classification");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0024);
		column.setIndex(1);
		columnInfo.add(1,column);
		
		//set minor_classification
		column = new TableField();
		column.setColumnName("minor_classification");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0025);
		column.setIndex(2);
		columnInfo.add(2,column);
		
		//set domain_name
		column = new TableField();
		column.setColumnName("domain_name");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setIsMandatory(ImportManagementConst.ISMANDATORY.YES);
		column.setPatternName(ImportManagementConst.PATTERN.YES);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0026);
		column.setIndex(3);
		columnInfo.add(3,column);
		
		//set physical_domain_name(domain code)
		column = new TableField();
		column.setColumnName("domain_code");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setIsMandatory(ImportManagementConst.ISMANDATORY.YES);
		column.setPatternCode(ImportManagementConst.PATTERN.YES);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0027);
		column.setIndex(4);
		columnInfo.add(4,column);
		
		//set physical_data_type
		column = new TableField();
		column.setColumnName("base_type");
		column.setIndex(5);
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setIsMandatory(ImportManagementConst.ISMANDATORY.YES);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0029);
		columnInfo.add(5,column);

		//set physical_data_lenght
		column = new TableField();
		column.setColumnName("maxLength");
		column.setDataType(ImportManagementConst.DATA_TYPE.INTEGER);
		column.setIsMandatory(ImportManagementConst.ISMANDATORY.YES);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0093);
		column.setIndex(6);
		columnInfo.add(6,column);
		
		//set description
		column = new TableField();
		column.setColumnName("remark");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0031);
		column.setIndex(7);
		columnInfo.add(7,column);
		
		//set minimum_value
		column = new TableField();
		column.setColumnName("min_val");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0033);
		column.setIndex(8);
		columnInfo.add(8,column);
		
		//set maximum_value
		column = new TableField();
		column.setColumnName("max_val");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0034);
		column.setIndex(9);
		columnInfo.add(9,column);
		
		//set default_value
		column = new TableField();
		column.setColumnName("default_value");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0035);
		column.setIndex(10);
		columnInfo.add(10,column);
						
		//set validation_rule
		column = new TableField();
		column.setColumnName("fmt_code");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0036);
		column.setIndex(11);
		columnInfo.add(11,column);
		return columnInfo;
	}
	
	private Map<String, String> initValidationRuleByGroupBasetype(){
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("1", "Alphanumeric");
		returnMap.put("2", "Integer");
		returnMap.put("3", "Decimal");
		returnMap.put("4", "Date");
		returnMap.put("5", "Name");
		returnMap.put("6", "Currency");
		returnMap.put("7", "Boolean");
		returnMap.put("8", "Time");
		returnMap.put("9", "DateTime");
		returnMap.put("10", "Binary");	
		return returnMap;	
	}
	
}
