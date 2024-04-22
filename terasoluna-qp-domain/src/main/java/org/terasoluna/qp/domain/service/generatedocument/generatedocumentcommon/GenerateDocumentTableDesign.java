package org.terasoluna.qp.domain.service.generatedocument.generatedocumentcommon;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.terasoluna.gfw.common.codelist.CodeList;
import org.terasoluna.gfw.common.codelist.SimpleMapCodeList;
import org.terasoluna.qp.app.common.ultils.ApplicationContextProvider;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.domain.model.Basetype;
import org.terasoluna.qp.domain.model.DomainDesign;
import org.terasoluna.qp.domain.model.GenerateDocumentItem;
import org.terasoluna.qp.domain.model.SubjectArea;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.model.TableDesignForeignKey;
import org.terasoluna.qp.domain.service.common.XmlUtils;
import org.terasoluna.qp.domain.service.generatedocument.GenerateDocumentConst;
import org.terasoluna.qp.domain.service.generatedocument.GenerateDocumentConst.StringPrefix;
import org.terasoluna.qp.domain.service.generatedocument.spreadsheet.SpreadsheetMessageDesign;
import org.terasoluna.qp.domain.service.generatedocument.spreadsheet.SpreadsheetTableDesign;
import org.terasoluna.qp.domain.service.importmanagement.ImportManagementConst;

@Service
public class GenerateDocumentTableDesign extends GenerateDocumentBase {

	/**
	 * Symbol for maru: ○
	 */
	private static final String CHECK_SYMBOL = "○";
	
	/**
	 * Symbol for dash: -
	 */
	//private static final String EMPTY = "-";
	
	public static void generate(Writer out, TableDesign tableDesign, String[] headerString, int baseTypeSize, int domainSize, Map<String, SimpleMapCodeList> codeListMap, boolean isExportLog) throws Exception {

		SpreadsheetTableDesign sw = new SpreadsheetTableDesign(out);
		sw.beginSheet(100);
		
		// Generate header
		generateHeaderTableDesign(sw, headerString, isExportLog);
	
		// Generate title
		generateTableDesignTableInfo(sw, getTableDesignTableInfoStrings(tableDesign), getTableDesignTableDetailTitleStrings(), isExportLog);
		
		// Generate table design detail 
		SimpleMapCodeList operatorCode = codeListMap.get("operatorCode");
		int detailSize = 0;
		if(tableDesign.getDetails() != null){
			generateTableDesignDetailContent(sw, tableDesign, operatorCode, isExportLog);
			detailSize =  tableDesign.getDetails().size();
		}

		// End sheet
		sw.endSheetWithMoreAction(detailSize, baseTypeSize, domainSize, operatorCode.asMap().keySet().size(), isExportLog);
	}
	
	public static void generateHeaderTableDesign(SpreadsheetTableDesign sw, String[] headerString, boolean isExportLog) throws IOException{
		// Get style
		int headerBold = styles.get("headerBold").getIndex();
		int headerNormal = styles.get("headerNormal").getIndex();
		int importStatus = styles.get("importStatus").getIndex();
		int importResult = styles.get("importResult").getIndex();
		//1st line
		sw.insertRow(0);
		sw.createCell(0, XmlUtils.xmlEscapeText(headerString[0]), headerBold);
		sw.createEmptyCells(1, 10, headerBold);
		
		sw.createCell(11, XmlUtils.xmlEscapeText(headerString[1]), headerBold);
		sw.createEmptyCells(12, 19, headerBold);

		sw.createCell(20, "", headerBold);
		sw.createEmptyCells(21, 57, headerBold);

		sw.createCell(58, XmlUtils.xmlEscapeText(headerString[2]), headerBold);
		sw.createEmptyCells(59, 63, headerBold);

		sw.createCell(64, XmlUtils.xmlEscapeText(headerString[3]), headerBold);
		sw.createEmptyCells(65, 69, headerBold);

		sw.createCell(70, XmlUtils.xmlEscapeText(headerString[4]), headerBold);
		sw.createEmptyCells(71, 75, headerBold);

		sw.createCell(76, XmlUtils.xmlEscapeText(headerString[5]), headerBold);
		sw.createEmptyCells(77, 81, headerBold);
		
		if(isExportLog){
			sw.createCell(82, XmlUtils.xmlEscapeText(MessageUtils.getMessage(ImportManagementConst.SC_IMPORTMANAGEMENT_0018)), importStatus);
			sw.createEmptyCells(83, 86, importStatus);
			sw.createCell(87, XmlUtils.xmlEscapeText(MessageUtils.getMessage(ImportManagementConst.SC_IMPORTMANAGEMENT_0009)), importResult);
			sw.createEmptyCells(88, 91, importResult);
		}
		
		sw.endRow();
		// 2nd line
		sw.insertRow(1);
		
		sw.createEmptyCells(0, 10, headerBold);

		sw.createCell(11, XmlUtils.xmlEscapeText(headerString[6]), headerNormal);
		sw.createEmptyCells(12, 19, headerNormal);

		sw.createCell(20, StringUtils.EMPTY, headerNormal);
		sw.createEmptyCells(21, 57, headerNormal);

		sw.createCell(58, XmlUtils.xmlEscapeText(headerString[7]), headerNormal);
		sw.createEmptyCells(59, 63, headerNormal);

		sw.createCell(64, XmlUtils.xmlEscapeText(headerString[8]), headerNormal);
		sw.createEmptyCells(65, 69, headerNormal);

		sw.createCell(70, XmlUtils.xmlEscapeText(headerString[9]), headerNormal);
		sw.createEmptyCells(71, 75, headerNormal);

		sw.createCell(76, XmlUtils.xmlEscapeText(headerString[10]), headerNormal);
		sw.createEmptyCells(77, 81, headerNormal);
		
		if(isExportLog){
			sw.createEmptyCells(82, 86, importStatus);
			
			sw.createEmptyCells(87, 91, importResult);
		}
		
		sw.endRow();
	}
	
	/**
	 * Generate table information strings
	 * @return
	 */
	public static String[] getTableDesignTableInfoStrings(TableDesign tableDesign){
		String[] infoString = new String[16];
		//Entity ID
		infoString[0] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.ENTITY_ID);
		infoString[1] =  StringPrefix.TABLE + StringUtils.leftPad(tableDesign.getTableDesignId().toString(), GenerateDocumentConst.GENERATE_SIZE_ID, "0");
		//Subject area id
		infoString[2] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.SUBJECT_AREA_ID);
		infoString[3] =  settingSubjectAreaID(tableDesign.getSubjectAreas());
		//Subject area name
		infoString[4] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.SUBJECT_AREA_NAME);
		infoString[5] =  settingSubjectAreaName(tableDesign.getSubjectAreas());
		//Entity type
		infoString[6] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.ENTITY_TYPE);
		infoString[7] =  MessageUtils.getMessage(getTableTypeName(tableDesign.getType().toString()));
		//Entity name
		infoString[8] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.ENTITY_NAME);
		infoString[9] =  tableDesign.getTableName();
		//Table name
		infoString[10] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.TABLE_NAME);
		infoString[11] =  tableDesign.getTableCode();
		//Description
		infoString[12] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.DESCRIPTION);
		infoString[13] =  tableDesign.getRemark();
		
		// Import status
		infoString[14] = tableDesign.getStatusImport();
		infoString[15] = tableDesign.getContentStatusImport();

		return infoString;
	}
	
	/**
	 * Generate table information strings
	 * @return
	 */
	public static String[] getTableDesignTableDetailTitleStrings(){
		String[] titleString = new String[25];
		titleString[0] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.ITEM_NO);
		titleString[1] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.ATTRIBUTE_NAME);
		titleString[2] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.COLUMN_NAME);
		titleString[3] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.PRIMARY_KEY);
		titleString[4] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.ALTERNATE_KEY);
		titleString[5] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.FOREIGN_KEY);
		titleString[6] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.LOGICAL_DATA_TYPE);
		titleString[7] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.PHYSICAL_DATA_TYPE);
		titleString[8] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.DOMAIN_NAME);
		titleString[9] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.DEFAULT_VALUE);
		titleString[10] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.UNIQUE_CONSTRAINT);
		titleString[11] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.NULL_VALUE_CONSTRAINT);
		titleString[12] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.CHECK_CONSTRAINT);
		titleString[13] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.DESCRIPTION);
		titleString[14] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.PARENT_ENTITY_NAME);
		titleString[15] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.PARENT_TABLE_NAME);
		titleString[16] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.PARENT_ATTRIBUTE_NAME);
		titleString[17] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.PARENT_COLUMN_NAME);
		titleString[18] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.DATA_TYPE);
		titleString[19] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.DIGIT_CHARACTER);
		titleString[20] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.DATA_TYPE);
		titleString[21] =  MessageUtils.getMessage(GenerateDocumentConst.TableDesign.DATA_LENGTH);
		return titleString;
	}
	
	public static void generateTableDesignTableInfo(SpreadsheetTableDesign sw, String[] inforString, String contentTitleString[], boolean isExportLog) throws IOException{
		// Get style	
		int headerBold = styles.get("headerBold").getIndex();
		int headerBoldLeft =  styles.get("headerBoldLeft").getIndex();
		int normalContent = styles.get("normalContent").getIndex();
		int headerBoldNoTop = styles.get("headerBoldNoTop").getIndex();
		
		// Generate table information
		for (int i = 3 ; i < 10 ; i++){
			sw.insertRow(i);
			int arrIndx = i - 3;
			sw.createCell(0, XmlUtils.xmlEscapeText(inforString[arrIndx*2]), headerBoldLeft);
			sw.createEmptyCells(1, 12, headerBoldLeft);
			
			sw.createCell(13, XmlUtils.xmlEscapeText(inforString[arrIndx*2 +1]), normalContent);
			sw.createEmptyCells(14, 81, normalContent);
			
			// Generate import result
			if(isExportLog){
				if(i==7){
					sw.createCell(82, XmlUtils.xmlEscapeText(inforString[14]), normalContent);
					sw.createEmptyCells(83, 86, normalContent);
					sw.createCell(87, XmlUtils.xmlEscapeText(inforString[15]), normalContent);
					sw.createEmptyCells(88, 91, normalContent);
				}
				if(i==8){
					sw.createEmptyCells(82, 91, normalContent);
				}
			}
			sw.endRow();
		}
		
		//Generate content title string
		// Row 1
		sw.insertRow(11);
				
		sw.createCell( 0, XmlUtils.xmlEscapeText(contentTitleString[0]), headerBold);
		sw.createEmptyCells( 1, 1, headerBold);
		
		sw.createCell( 2, XmlUtils.xmlEscapeText(contentTitleString[1]), headerBold);
		sw.createEmptyCells( 3, 9, headerBold);
		
		sw.createCell( 10, XmlUtils.xmlEscapeText(contentTitleString[2]), headerBold);
		sw.createEmptyCells( 11, 17, headerBold);
		
		sw.createCell( 18, XmlUtils.xmlEscapeText(contentTitleString[3]), headerBold);
		sw.createEmptyCells( 19, 19, headerBold);
		
		sw.createCell( 20, XmlUtils.xmlEscapeText(contentTitleString[4]), headerBold);
		sw.createEmptyCells( 21, 21, headerBold);
		
		sw.createCell( 22, XmlUtils.xmlEscapeText(contentTitleString[5]), headerBold);
		sw.createEmptyCells( 23, 47, headerBold);
		
		sw.createCell( 48, XmlUtils.xmlEscapeText(contentTitleString[6]), headerBold);
		sw.createEmptyCells( 49, 53, headerBold);
				
		sw.createCell( 54, XmlUtils.xmlEscapeText(contentTitleString[7]), headerBold);
		sw.createEmptyCells( 55, 59, headerBold);
				
		sw.createCell( 60, XmlUtils.xmlEscapeText(contentTitleString[8]), headerBold);
		sw.createEmptyCells( 61, 64, headerBold);
		
		sw.createCell( 65, XmlUtils.xmlEscapeText(contentTitleString[9]), headerBold);
		sw.createEmptyCells( 66, 66, headerBold);
		
		sw.createCell( 67, XmlUtils.xmlEscapeText(contentTitleString[10]), headerBold);
		sw.createEmptyCells( 68, 68, headerBold);
		
		sw.createCell( 69, XmlUtils.xmlEscapeText(contentTitleString[11]), headerBold);
		sw.createEmptyCells( 70, 70, headerBold);
		
		sw.createCell( 71, XmlUtils.xmlEscapeText(contentTitleString[12]), headerBold);
		sw.createEmptyCells( 72, 72, headerBold);
		
		sw.createEmptyCells( 73, 73, headerBold);
		sw.createCell( 74, XmlUtils.xmlEscapeText(contentTitleString[13]), headerBold);
		sw.createEmptyCells( 75, 81, headerBold);
				
		sw.endRow();
		// Row 2
		sw.insertRow(12);
				
		sw.createEmptyCells( 0, 1, headerBold);
		
		sw.createEmptyCells( 2, 9, headerBold);
		
		sw.createEmptyCells( 10, 17, headerBold);
		
		sw.createEmptyCells( 18, 19, headerBold);
		
		sw.createEmptyCells( 20, 21, headerBold);
		
		sw.createEmptyCells( 22, 23, headerBoldNoTop);

		sw.createCell( 24, XmlUtils.xmlEscapeText(contentTitleString[14]), headerBold);
		sw.createEmptyCells( 25, 29, headerBold);
		
		sw.createCell( 30, XmlUtils.xmlEscapeText(contentTitleString[15]), headerBold);
		sw.createEmptyCells( 31, 35, headerBold);
		
		sw.createCell( 36, XmlUtils.xmlEscapeText(contentTitleString[16]), headerBold);
		sw.createEmptyCells( 37, 41, headerBold);
		
		sw.createCell( 42, XmlUtils.xmlEscapeText(contentTitleString[17]), headerBold);
		sw.createEmptyCells( 43, 47, headerBold);
		
		sw.createCell( 48, XmlUtils.xmlEscapeText(contentTitleString[18]), headerBold);
		sw.createEmptyCells( 49, 50, headerBold);
		
		sw.createCell( 51, XmlUtils.xmlEscapeText(contentTitleString[19]), headerBold);
		sw.createEmptyCells( 52, 53, headerBold);
		
		sw.createCell( 54, XmlUtils.xmlEscapeText(contentTitleString[20]), headerBold);
		sw.createEmptyCells( 55, 56, headerBold);
		
		sw.createCell( 57, XmlUtils.xmlEscapeText(contentTitleString[21]), headerBold);
		sw.createEmptyCells( 58, 59, headerBold);
		
		sw.createEmptyCells( 60, 64, headerBold);
		
		sw.createEmptyCells( 65, 66, headerBold);
		
		sw.createEmptyCells( 67, 68, headerBold);

		sw.createEmptyCells( 69, 70, headerBold);
		
		sw.createEmptyCells( 71, 72, headerBold);
		
		sw.createEmptyCells( 73, 81, headerBold);
		
		sw.endRow();
	}
	
	/**
	 * SettingSubjectAreaName
	 * @param subjectAreas
	 * @return 
	 */
	private static String settingSubjectAreaName(List<SubjectArea> subjectAreas){
		StringBuilder stringBuilder = new StringBuilder();
		if(FunctionCommon.isNotEmpty(subjectAreas)){
			int count = 1;
			for (SubjectArea subjectArea : subjectAreas) {
				stringBuilder.append(subjectArea.getAreaName());
				if(count != subjectAreas.size()){
					stringBuilder.append("; ");
				}
				count++;
			}
			return stringBuilder.toString();
		}else{
			return StringUtils.EMPTY;
		}
	}
	
	/**
	 * SettingSubjectAreaName
	 * @param subjectAreas
	 * @return 
	 */
	private static String settingSubjectAreaID(List<SubjectArea> subjectAreas){
		StringBuilder stringBuilder = new StringBuilder();
		if(FunctionCommon.isNotEmpty(subjectAreas)){
			int count = 1;
			for (SubjectArea subjectArea : subjectAreas) {
				stringBuilder.append(subjectArea.getAreaId());
				if(count != subjectAreas.size()){
					stringBuilder.append("; ");
				}
				count++;
			}
			return stringBuilder.toString();
		}else{
			return StringUtils.EMPTY;
		}
	}
	
	private static String[] settingForeignKeyInfo(List<TableDesignForeignKey> listForeignKey){
		String[] data = new String[5];
		// Only 1 item in list
		for(TableDesignForeignKey fKey : listForeignKey){
			data[0] = fKey.getToTableName();
			data[1] =fKey.getToTableCode();
			data[2] =fKey.getToColumnName();
			data[3] =fKey.getToColumnCode();
		}
		return data;
	}
	
	public static void generateTableDesignDetailContent(SpreadsheetTableDesign sw, TableDesign tableDesign, SimpleMapCodeList operatorCode, boolean isExportLog) throws IOException{
		// Get style	
		int normalContent = styles.get("normalContent").getIndex();
		
		int count = 1;
		int firstRow = 13;
		tableDesign.getTableDesignForeignKeys();
		for (TableDesignDetails tableDetails : tableDesign.getDetails()) {
			sw.insertRow(firstRow++);
			
			// Item no
			sw.createCell( 0, count++, normalContent);
			sw.createEmptyCells( 1, 1, normalContent);
			
			// Attribute name
			sw.createCell( 2, XmlUtils.xmlEscapeText(tableDetails.getName()), normalContent);
			sw.createEmptyCells( 3, 9, normalContent);
			
			// Column name
			sw.createCell( 10, XmlUtils.xmlEscapeText(tableDetails.getCode()), normalContent);
			sw.createEmptyCells( 11, 17, normalContent);
			
			// Primary key
			String pkValue = StringUtils.EMPTY;
			if( tableDetails.getKeyType().length() == 5 && tableDetails.getKeyType().charAt(4) == '1'){
				pkValue =  CHECK_SYMBOL;
			}
			sw.createCell( 18, XmlUtils.xmlEscapeText(pkValue), normalContent);
			sw.createEmptyCells( 19, 19, normalContent);
			
			// Alternate key
			String alterKey =  StringUtils.EMPTY;
			if(new Integer(1).equals(tableDetails.getAutoIncrementFlag())){
				alterKey = CHECK_SYMBOL;
			}
			sw.createCell( 20, XmlUtils.xmlEscapeText(alterKey), normalContent);
			sw.createEmptyCells( 21, 21, normalContent);
			
			// Foreign key
			String frKey =  StringUtils.EMPTY;
			if(tableDetails.getKeyType().length() == 5 && tableDetails.getKeyType().charAt(3) == '1'){
				frKey = CHECK_SYMBOL;
			}
			sw.createCell( 22, XmlUtils.xmlEscapeText(frKey), normalContent);
			sw.createEmptyCells( 23, 23, normalContent);
			
			if(tableDetails.getForeignKeys() != null && tableDetails.getForeignKeys().size() > 0){
				String[] data = settingForeignKeyInfo(tableDetails.getForeignKeys());
				// Parent entity name
				sw.createCell( 24, XmlUtils.xmlEscapeText(data[0]), normalContent);
				sw.createEmptyCells( 25, 29, normalContent);
				// Parent table name
				sw.createCell( 30, XmlUtils.xmlEscapeText(data[1]), normalContent);
				sw.createEmptyCells( 31, 35, normalContent);
				// Parent attribute name
				sw.createCell( 36, XmlUtils.xmlEscapeText(data[2]), normalContent);
				sw.createEmptyCells( 37, 41, normalContent);
				// Parent column name
				sw.createCell( 42, XmlUtils.xmlEscapeText(data[3]), normalContent);
				sw.createEmptyCells( 43, 47, normalContent);
			}else{
				// Parent entity name
				sw.createCell( 24, "", normalContent);
				sw.createEmptyCells( 25, 29, normalContent);
				// Parent table name
				sw.createCell( 30, "", normalContent);
				sw.createEmptyCells( 31, 35, normalContent);
				// Parent attribute name
				sw.createCell( 36, "", normalContent);
				sw.createEmptyCells( 37, 41, normalContent);
				// Parent column name
				sw.createCell( 42, "", normalContent);
				sw.createEmptyCells( 43, 47, normalContent);
			}
						
			// Logic data type
			sw.createCell( 48, XmlUtils.xmlEscapeText(tableDetails.getDataTypeName()), normalContent);
			sw.createEmptyCells( 49, 50, normalContent);
			
			// Digit character
			sw.createCell( 51, XmlUtils.xmlEscapeText((tableDetails.getMaxlength() == null)? StringUtils.EMPTY : tableDetails.getMaxlength().toString()), normalContent);
			sw.createEmptyCells( 52, 53, normalContent);
			
			// Physical data type
			sw.createCell( 54, XmlUtils.xmlEscapeText(tableDetails.getDataTypeName()), normalContent);
			sw.createEmptyCells( 55, 56, normalContent);
			
			// Data length
			sw.createCell( 57, XmlUtils.xmlEscapeText((tableDetails.getMaxlength() == null)? StringUtils.EMPTY : tableDetails.getMaxlength().toString()), normalContent);
			sw.createEmptyCells( 58, 59, normalContent);
			
			// Domain name
			sw.createCell( 60, XmlUtils.xmlEscapeText(tableDetails.getDomainName()), normalContent);
			sw.createEmptyCells( 61, 64, normalContent);
			
			// Default value
			sw.createCell( 65, XmlUtils.xmlEscapeText(tableDetails.getDefaultValue()), normalContent);
			sw.createEmptyCells( 66, 66, normalContent);
			
			// Unique constraint
			String unique = StringUtils.EMPTY;
			if(tableDetails.getKeyType().length() == 5 && tableDetails.getKeyType().charAt(2) == '1'){
				unique = CHECK_SYMBOL;
			}
			sw.createCell( 67, XmlUtils.xmlEscapeText(unique), normalContent);
			sw.createEmptyCells( 68, 68, normalContent);
			
			// Null value constraint 
			String nullValue = StringUtils.EMPTY;
			if(new Integer(1).equals(tableDetails.getIsMandatory())){
				nullValue =  CHECK_SYMBOL;
			}
			sw.createCell( 69, XmlUtils.xmlEscapeText(nullValue), normalContent);
			sw.createEmptyCells( 70, 70, normalContent);
			
			// Check constraint
			generateConstrainString(sw, tableDetails, operatorCode);
			
			// Description (remark)
			sw.createCell( 74, XmlUtils.xmlEscapeText(tableDetails.getRemark()), normalContent);
			sw.createEmptyCells( 75, 81, normalContent);
			
			// Generate import status
			if(isExportLog){
				// Import status
				sw.createCell( 82, XmlUtils.xmlEscapeText(tableDetails.getStatusImport()), normalContent);
				sw.createEmptyCells( 83, 86, normalContent);
				
				// Import result
				sw.createCell( 87, XmlUtils.xmlEscapeText(tableDetails.getContentStatusImport()), normalContent);
				sw.createEmptyCells( 88, 91, normalContent);
			}
			
			sw.endRow();
		}
	}
	
	private static void generateConstrainString(SpreadsheetTableDesign sw, TableDesignDetails tableDetails, SimpleMapCodeList operatorCode) throws IOException{
		int normalContent = styles.get("normalContent").getIndex();
		
		String operator = tableDetails.getOperatorCode();
		if(new Integer(1).equals(tableDetails.getConstrainsType())){
			switch (operator) {
				case "1":
					
				case "2":
					
				case "3":
					sw.createCell( 71, XmlUtils.xmlEscapeText( MessageUtils.getMessage(operatorCode.asMap().get(operator))), normalContent);
					sw.createEmptyCells( 72, 72, normalContent);
					sw.createCell( 73, XmlUtils.xmlEscapeText(tableDetails.getMaxVal()), normalContent);
					break;
				case "4":

				case "5":
			
				case "6":
					
				case "7":
					sw.createCell( 71, XmlUtils.xmlEscapeText( MessageUtils.getMessage(operatorCode.asMap().get(operator))), normalContent);
					sw.createEmptyCells( 72, 72, normalContent);
					sw.createCell( 73, XmlUtils.xmlEscapeText(tableDetails.getMinVal()), normalContent);
					break;
				case "8":
					sw.createCell( 71, XmlUtils.xmlEscapeText( MessageUtils.getMessage(operatorCode.asMap().get(operator))), normalContent);
					sw.createEmptyCells( 72, 72, normalContent);
					sw.createCell( 73, XmlUtils.xmlEscapeText(tableDetails.getMinVal() + "~" + tableDetails.getMaxVal() ), normalContent);
					break;
				default:
					sw.createEmptyCells( 71, 73, normalContent);
					break;
			}
		}else{
			sw.createEmptyCells( 71, 73, normalContent);
		}
		
	}
	
	public static void generateDatasource(Writer out, GenerateDocumentItem item) throws IOException{
		// Get style
		int dtSourceTitle = styles.get("dtSourceTitle").getIndex();
		int dtSourceHeader = styles.get("dtSourceHeader").getIndex();
		int dtSourceContent = styles.get("dtSourceContent").getIndex();
		
		SpreadsheetMessageDesign sw = new SpreadsheetMessageDesign(out);
		sw.beginSheet();
		
		// Insert title
		sw.insertRow(2);
		sw.createCell( 0,  XmlUtils.xmlEscapeText(MessageUtils.getMessage(GenerateDocumentConst.TableDesign.ENTITY_TYPE)), dtSourceTitle);
		sw.createCell( 4, XmlUtils.xmlEscapeText(MessageUtils.getMessage(GenerateDocumentConst.TableDesign.DATA_TYPE)), dtSourceTitle);
		sw.createCell( 8, XmlUtils.xmlEscapeText(MessageUtils.getMessage(GenerateDocumentConst.TableDesign.DOMAIN_NAME)), dtSourceTitle);
		sw.createCell( 12, XmlUtils.xmlEscapeText(MessageUtils.getMessage(GenerateDocumentConst.TableDesign.OPERATOR_CODE)), dtSourceTitle);
		sw.endRow();
		
		// Insert data source header
		sw.insertRow(3);
		sw.createCell( 1, XmlUtils.xmlEscapeText(MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.CODE)), dtSourceHeader);
		sw.createCell( 2, XmlUtils.xmlEscapeText(MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.NAME)), dtSourceHeader);
		sw.createCell( 5, XmlUtils.xmlEscapeText(MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.CODE)), dtSourceHeader);
		sw.createCell( 6, XmlUtils.xmlEscapeText(MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.NAME)), dtSourceHeader);
		sw.createCell( 9, XmlUtils.xmlEscapeText(MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.CODE)), dtSourceHeader);
		sw.createCell( 10, XmlUtils.xmlEscapeText(MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.NAME)), dtSourceHeader);
		sw.createCell( 13, XmlUtils.xmlEscapeText(MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.CODE)), dtSourceHeader);
		sw.createCell( 14, XmlUtils.xmlEscapeText(MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.NAME)), dtSourceHeader);

		sw.endRow();
		
		Map<String, List<String[]>> mapData = prepareData(item);
		List<String[]> listTableType = mapData.get("listTableType");
		List<String[]> listDataType = mapData.get("listDataType");
		List<String[]> listDomain = mapData.get("listDomain");
		List<String[]> listOperator = mapData.get("listOperator");
		
		// Get max row
		int maxRow = (listDataType.size() > listDomain.size()) ? listDataType.size(): listDomain.size();
		
		for (int rownum = 4; rownum < (maxRow + 4); rownum++) {
			sw.insertRow(rownum);

			if (listTableType.size() >= rownum - 3) {
				if (listTableType.get(rownum - 4) != null) {
					sw.createCell(1, Double.parseDouble(listTableType.get(rownum - 4)[0]), dtSourceContent);
					sw.createCell(2, XmlUtils.xmlEscapeText(listTableType.get(rownum - 4)[1]), dtSourceContent);
				}
			}
			if (listDataType.size() >= rownum - 3) {
				if (listDataType.get(rownum - 4) != null) {
					sw.createCell(5, Double.parseDouble(listDataType.get(rownum - 4)[0]), dtSourceContent);
					sw.createCell(6, XmlUtils.xmlEscapeText(listDataType.get(rownum - 4)[1]), dtSourceContent);
				}
			}
			if (listDomain.size() >= rownum - 3) {
				if (listDomain.get(rownum - 4) != null) {
					sw.createCell(9, Double.parseDouble(listDomain.get(rownum - 4)[0]), dtSourceContent);
					sw.createCell(10, XmlUtils.xmlEscapeText(listDomain.get(rownum - 4)[1]), dtSourceContent);
				}
			}
			if (listOperator.size() >= rownum - 3) {
				if (listOperator.get(rownum - 4) != null) {
					sw.createCell(13, Double.parseDouble(listOperator.get(rownum - 4)[0]), dtSourceContent);
					sw.createCell(14, XmlUtils.xmlEscapeText(listOperator.get(rownum - 4)[1]), dtSourceContent);
				}
			}
			sw.endRow();
		}
		sw.endSheet();
	}
	
	private static Map<String, List<String[]>> prepareData(GenerateDocumentItem item) {
		Map<String, List<String[]>> mapData = new HashMap<String, List<String[]>>();

		// Get code list
		Map<String, SimpleMapCodeList> codeListMap = item.getMapCodeList();
		SimpleMapCodeList tableType =  codeListMap.get("tableType");
		SimpleMapCodeList operatorCode = codeListMap.get("operatorCode");
		
		List<String[]> listTableType = new ArrayList<String[]>();
		List<String[]> listDataType = new ArrayList<String[]>();
		List<String[]> listDomain = new ArrayList<String[]>();
		List<String[]> listOperator = new ArrayList<String[]>();
		
		// Add table type
		for(String key : tableType.asMap().keySet()){
			String[] type = new String[2];
			type[0] = key;
			type[1] = MessageUtils.getMessage(tableType.asMap().get(key));
			listTableType.add(type);
		}
				
		// Add data type
		for(Basetype baseType : item.getListBasetype()){
			String[] basetype = new String[2];
			basetype[0] = baseType.getBasetyeId().toString();
			basetype[1] = baseType.getBasetypeName();
			listDataType.add(basetype);
		}
		
		// Add date domain
		for(DomainDesign domain : item.getListDomainDesigns()){
			String[] dmdesign = new String[2];
			dmdesign[0] = domain.getDomainId().toString();
			dmdesign[1] = domain.getDomainName();
			listDomain.add(dmdesign);
		}
		
		// Add table type
		for(String key : operatorCode.asMap().keySet()){
			String[] code = new String[2];
			code[0] = key;
			code[1] = MessageUtils.getMessage(operatorCode.asMap().get(key));
			listOperator.add(code);
		}
		
		mapData.put("listTableType", listTableType);
		mapData.put("listDataType", listDataType);
		mapData.put("listDomain", listDomain);
		mapData.put("listOperator", listOperator);
		
		return mapData;
	}
	
	private static String getTableTypeName(String tableType) {
		CodeList codeList = (CodeList) ApplicationContextProvider.getApplicationContext().getBean("CL_TABLE_TYPE_ALL");
		return String.valueOf(getValueFromKeyOfMap(codeList.asMap(),tableType));
	}
	
	private static Object getValueFromKeyOfMap(Map<?, ?> map, Object key) {
		for (Object o : map.keySet()) {
			if (DataTypeUtils.equalsIgnoreCase(o, key)) {
				return map.get(o);
			}
		}
		return null;
	}
}
