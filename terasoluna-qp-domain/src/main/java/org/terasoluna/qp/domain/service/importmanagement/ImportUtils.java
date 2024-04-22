package org.terasoluna.qp.domain.service.importmanagement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.terasoluna.gfw.common.codelist.ReloadableCodeList;
import org.terasoluna.gfw.common.codelist.SimpleMapCodeList;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.ImportManagement;
import org.terasoluna.qp.domain.service.generatedocument.GenerateDocumentConst.StringConstant;

public class ImportUtils {

	private final static String CODE_LIST_IMPORT_SETTING = "CL_IMPORT_SETTING";
	
	public static void writeImportResult(ImportManagement importManagement, List<String[]> errors) {
		switch (importManagement.getDocumentType()) {
		case 1:
			writeImportResultCodelistDesign(importManagement.getFilePath(), errors);
			break;
		case 2:
			writeImportResultDomainDesign(importManagement.getFilePath(), errors);
			break;
		case 3:
			writeImportResultMessageDesign(importManagement.getFilePath(), errors);
			break;
		case 4:
			
			break;
		}
	}
	
	public static void writeImportResultMessageDesign(String filePath, List<String[]> errors) {
		// Template file had created by manual
		try {
			InputStream is = new FileInputStream(filePath);
			Workbook wb = WorkbookFactory.create(is);
			Sheet sheet = wb.getSheetAt(1);
			
			// Column 64-65: Import status 
			// Column 66-70: Import result
			CellStyle statusCellStyle = wb.createCellStyle();
			statusCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			statusCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			statusCellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
			statusCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			
			CellStyle resultCellStyle = wb.createCellStyle();
			resultCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			resultCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			resultCellStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
			resultCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			
			// create header
			Row row = sheet.getRow(3);
			
			Cell cell = row.createCell(64);
			cell.setCellValue("Import status");
			cell.setCellStyle(statusCellStyle);
			CellRangeAddress cellRange = new CellRangeAddress(3, 3, 64, 65);
			sheet.addMergedRegion(cellRange);
			settingStyleForCellHadMerge(0, cellRange, sheet,wb);
					
			cell = row.createCell(66);
			cell.setCellValue("Import result");
			cell.setCellStyle(resultCellStyle);
			cellRange = new CellRangeAddress(3, 3, 66, 70);
			sheet.addMergedRegion(cellRange);
			settingStyleForCellHadMerge(0, cellRange, sheet,wb);
			
			// create detail
			int indexRow = 4;
			for(String[] error : errors) {
				if(error.length >= 2) {
					Cell statusCell = sheet.getRow(indexRow).createCell(64);
					statusCell.setCellValue(MessageUtils.getMessage(error[1]));
					error[0] = null;
					error[1] = null;
					String importResult = joinString(error, ";");
					Cell resultCell = sheet.getRow(indexRow).createCell(66);
					resultCell.setCellValue(importResult);
				}
				// merge cell and add border
				cellRange = new CellRangeAddress(indexRow, indexRow, 64, 65);
				sheet.addMergedRegion(cellRange);
				settingStyleForCellHadMerge(0, cellRange, sheet,wb);
				cellRange = new CellRangeAddress(indexRow, indexRow, 66, 70);
				sheet.addMergedRegion(cellRange);
				settingStyleForCellHadMerge(0, cellRange, sheet,wb);
				
				indexRow++;
			}
			FileOutputStream out = new FileOutputStream(filePath);
	    	wb.write(out);
			out.close();
			wb.close();
		}
		catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeImportResultDomainDesign(String filePath, List<String[]> errors) {
		// Template file had created by manual
		try {
			InputStream is = new FileInputStream(filePath);
			Workbook wb = WorkbookFactory.create(is);
			Sheet sheet = wb.getSheetAt(1);
			
			// Column 64-65: Import status 
			// Column 66-70: Import result
			CellStyle statusCellStyle = wb.createCellStyle();
			statusCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			statusCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			statusCellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
			statusCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			
			CellStyle resultCellStyle = wb.createCellStyle();
			resultCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			resultCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			resultCellStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
			resultCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			
			// create header
			Row row = sheet.getRow(3);
			
			Cell cell = row.createCell(70);
			cell.setCellValue("Import status");
			cell.setCellStyle(statusCellStyle);
			CellRangeAddress cellRange = new CellRangeAddress(3, 4, 70, 71);
			sheet.addMergedRegion(cellRange);
			settingStyleForCellHadMerge(0, cellRange, sheet,wb);
					
			cell = row.createCell(72);
			cell.setCellValue("Import result");
			cell.setCellStyle(resultCellStyle);
			cellRange = new CellRangeAddress(3, 4, 72, 76);
			sheet.addMergedRegion(cellRange);
			settingStyleForCellHadMerge(0, cellRange, sheet,wb);
			
			// create detail
			int indexRow = 5;
			for(String[] error : errors) {
				if(error.length >= 2) {
					Cell statusCell = sheet.getRow(indexRow).createCell(70);
					statusCell.setCellValue(MessageUtils.getMessage(error[1]));
					error[0] = null;
					error[1] = null;
					String importResult = joinString(error, ";");
					Cell resultCell = sheet.getRow(indexRow).createCell(72);
					resultCell.setCellValue(importResult);
				}
				// merge cell and add border
				cellRange = new CellRangeAddress(indexRow, indexRow, 70, 71);
				sheet.addMergedRegion(cellRange);
				settingStyleForCellHadMerge(0, cellRange, sheet,wb);
				cellRange = new CellRangeAddress(indexRow, indexRow, 72, 76);
				sheet.addMergedRegion(cellRange);
				settingStyleForCellHadMerge(0, cellRange, sheet,wb);
				
				indexRow++;
			}
			FileOutputStream out = new FileOutputStream(filePath);
	    	wb.write(out);
			out.close();
			wb.close();
		}
		catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeImportResultCodelistDesign(String filePath, List<String[]> errors) {
		// Template file had created by manual
		try {
			InputStream is = new FileInputStream(filePath);
			Workbook wb = WorkbookFactory.create(is);
			int summaryPointer = 0;
			
			for(int x = 1; x < wb.getNumberOfSheets(); x++) {
				Sheet sheet = wb.getSheetAt(x);
				
				int indexRow = 11;
				int numberOfRows = sheet.getLastRowNum() + 1;
				int numberOfLoop = numberOfRows - indexRow;
				
				// Column 64-65: Import status 
				// Column 66-70: Import result
				CellStyle statusCellStyle = wb.createCellStyle();
				statusCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
				statusCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				statusCellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
				statusCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
				
				CellStyle resultCellStyle = wb.createCellStyle();
				resultCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
				resultCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				resultCellStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
				resultCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
				
				// create header
				Row row = sheet.createRow(2);
				
				Cell cell = row.createCell(66);
				cell.setCellValue(MessageUtils.getMessage(ImportManagementConst.SC_IMPORTMANAGEMENT_0018));
				cell.setCellStyle(statusCellStyle);
				CellRangeAddress cellRange = new CellRangeAddress(2, 2, 66, 67);
				sheet.addMergedRegion(cellRange);
				settingStyleForCellHadMerge(0, cellRange, sheet,wb);
						
				cell = row.createCell(68);
				cell.setCellValue(MessageUtils.getMessage(ImportManagementConst.SC_IMPORTMANAGEMENT_0009));
				cell.setCellStyle(resultCellStyle);
				cellRange = new CellRangeAddress(2, 2, 68, 72);
				sheet.addMergedRegion(cellRange);
				settingStyleForCellHadMerge(0, cellRange, sheet,wb);
				
				String[] errorSummary = errors.get(summaryPointer);
				summaryPointer++;
				String errorSummaryImportStatus = errorSummary[1];
				//String errorSummaryImportResult = joinString(errorSummary, ";");
				
				cell = sheet.getRow(3).createCell(66);
				cell.setCellValue(MessageUtils.getMessage(errorSummaryImportStatus));
				cellRange = new CellRangeAddress(3, 7, 66, 67);
				sheet.addMergedRegion(cellRange);
				settingStyleForCellHadMerge(0, cellRange, sheet,wb);
				
				cell = sheet.getRow(3).createCell(68);
//				cell.setCellValue(MessageUtils.getMessage(errorSummaryImportResult));
				cellRange = new CellRangeAddress(3, 7, 68, 72);
				sheet.addMergedRegion(cellRange);
				settingStyleForCellHadMerge(0, cellRange, sheet,wb);
				
				// create detail
//				int indexRow = 11;
//				int numberOfRows = sheet.getPhysicalNumberOfRows();
//				int numberOfLoop = numberOfRows - indexRow;
				for(int i = 1; i <= numberOfLoop; i++) {
					String[] error = errors.get(summaryPointer);
					if(error.length >= 2) {
						Cell statusCell = sheet.getRow(indexRow).createCell(66);
						statusCell.setCellValue(MessageUtils.getMessage(error[1]));
						error[0] = null;
						error[1] = null;
						String importResult = joinString(error, ";");
						Cell resultCell = sheet.getRow(indexRow).createCell(68);
						resultCell.setCellValue(importResult);
					}
					// merge cell and add border
					cellRange = new CellRangeAddress(indexRow, indexRow, 66, 67);
					sheet.addMergedRegion(cellRange);
					settingStyleForCellHadMerge(0, cellRange, sheet,wb);
					cellRange = new CellRangeAddress(indexRow, indexRow, 68, 72);
					sheet.addMergedRegion(cellRange);
					settingStyleForCellHadMerge(0, cellRange, sheet,wb);
					
					indexRow++;
					summaryPointer++;
				}
			}
				
			FileOutputStream out = new FileOutputStream(filePath);
	    	wb.write(out);
			out.close();
			wb.close();
		}
		catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Map<String, Object> validate(List<String[]> importData, List<TableField> tableField) {
		List<String[]> errors = new ArrayList<String[]>();
		List<String[]> rowsData = new ArrayList<String[]>();
		List<Integer> rows = new ArrayList<Integer>();
	
		for (int line = 0; line < importData.size(); line++) {
			List<String> errorInLine = new ArrayList<String>();
			// clone data
			String[] dataArray = importData.get(line).clone(); 
			if (dataArray != null) {
				boolean lineError = false;

				for (int i = 0; i < dataArray.length; i++) {
					String data = dataArray[i];
					TableField fields = tableField.get(i);
					String messageCode = fields.getColumnMessage();
					
					if(data.equals(StringConstant.DASH)){
						data = null;
						dataArray[i]=null;
					}
					if (data != null) {
						boolean errorBussiness = false;
						
						// validate total column
						/*if (dataArray.length != tableField.size()) {
							errorInLine.add(MessageUtils.getMessage(ImportManagementConst.ERR_COLUMN_NOT_MATCH, tableField.size()));
							errorBussiness = true;
							lineError = true;
							break;
						}
						else {
							//fields = tableField.get(i);
							messageCode = fields.getColumnMessage();
						}*/
						
						// validate Required
						if (fields.getIsMandatory() != null) {
							boolean error = ValidationUtils.validateRequired(data, null, messageCode);
							if (!error) {
								errorInLine.add(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0025, MessageUtils.getMessage(messageCode)));
								errorBussiness = true;
								lineError = true;
								continue;
							}
						}
						
						// validate match
						if (fields.getPatternCode() != null) {
							boolean error = ValidationUtils.validateMaskCode(data, null, CommonMessageConst.PATTERN_FOR_CODE, messageCode);
							if (!error) {
								errorInLine.add(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0066, MessageUtils.getMessage(messageCode)));								
								errorBussiness = true;
							}
						}
						
						// validate match
						if (fields.getPatternName() != null) {
							boolean error = ValidationUtils.validateMaskName(data, null, CommonMessageConst.PATTERN_FOR_NAME, messageCode);
							if (!error) {
								errorInLine.add(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0126, MessageUtils.getMessage(messageCode)));								
								errorBussiness = true;
							}
						}
						
						// validate MaxLength
						if (fields.getMaxLength() != null) {
							boolean error = ValidationUtils.validateMaxLength(data, null, Integer.parseInt(fields.getMaxLength()), messageCode);
							if (!error) {
								errorInLine.add(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0067, MessageUtils.getMessage(messageCode), Integer.parseInt(fields.getMaxLength())));								
								errorBussiness = true;
							}
						}
						// validate type=text
						//if (fields.getDataType().toString().equals(ImportManagementConst.DATA_TYPE.STRING)) {
						//	rowData.add(data);
						//}
						// validate type=integer
						if (fields.getDataType().toString().equals(ImportManagementConst.DATA_TYPE.INTEGER)) {
							boolean error = (boolean) ValidationUtils.validateInteger(data,null, messageCode);
							if (!error) {
								errorInLine.add(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0015,MessageUtils.getMessage(messageCode)));
								errorBussiness = true;
							}
							if (fields.getMaxValue() != null && fields.getMinValue() != null) {
								error = ValidationUtils.validateIntRange(data,null,Integer.parseInt(fields.getMinValue()),Integer.parseInt(fields.getMaxValue()), messageCode);
								if (!error) {
									errorInLine.add(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0024,MessageUtils.getMessage(messageCode)));
									errorBussiness = true;
								}
							}							
						}
						// validate type=Float
						if (fields.getDataType().toString().equals(ImportManagementConst.DATA_TYPE.FLOAT)) {
							boolean error = (boolean) ValidationUtils.validateFloat(data,null, messageCode);
							if (!error) {
								errorInLine.add(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0013,MessageUtils.getMessage(messageCode)));
								errorBussiness = true;
							}
							if (fields.getMaxValue() != null && fields.getMinValue() != null) {
								error = ValidationUtils.validateFloatRange(data,null,Integer.parseInt(fields.getMinValue()),Integer.parseInt(fields.getMaxValue()), messageCode);
								if (!error) {
									errorInLine.add(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0024,MessageUtils.getMessage(messageCode)));
									errorBussiness = true;
								}
							}							
						}
						// validate type=Double
						if (fields.getDataType().toString().equals(ImportManagementConst.DATA_TYPE.DOUBLE)) {
							boolean error = (boolean) ValidationUtils.validateDouble(data,null, messageCode);
							if (!error) {
								errorInLine.add(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0009,MessageUtils.getMessage(messageCode)));
								errorBussiness = true;
							}
							if (fields.getMaxValue() != null && fields.getMinValue() != null) {
								error = ValidationUtils.validateDoubleRange(data,null,Integer.parseInt(fields.getMinValue()),Integer.parseInt(fields.getMaxValue()), messageCode);
								if (!error) {
									errorInLine.add(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0024,MessageUtils.getMessage(messageCode)));
									errorBussiness = true;
								}
							}
						}
						// validate type=Long
						if (fields.getDataType().toString().equals(ImportManagementConst.DATA_TYPE.LONG)) {
							boolean error = (boolean) ValidationUtils.validateLong(data,null, messageCode);
							if (!error) {
								errorInLine.add(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0019,MessageUtils.getMessage(messageCode)));
								errorBussiness = true;
							}
						}
						// validate type=TIMESTAMP
						if (fields.getDataType().toString().equals(ImportManagementConst.DATA_TYPE.TIMESTAMP)) {
							String dataPattern = "";
							/*if (SessionUtils.get(SessionUtils.FORMATDATE) == null) {
								dataPattern = SystemUtils.getSysFormatDatePartern();
							} else {
								dataPattern = SessionUtils.get(SessionUtils.FORMATDATE).toString();
							}*/
							boolean error = (boolean) ValidationUtils.validateDate(data,null, dataPattern,messageCode);
							if (!error ) {
								errorInLine.add(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0005,MessageUtils.getMessage(messageCode)));
								errorBussiness = true;
							} 
						}
						
						// validate Simple Map CodeList
						if (fields.getCodeList() != null) {
							if(data.trim().length() > 0 ){
								SimpleMapCodeList loader = FunctionCommon.getSimpleMapCodeList(fields.getCodeList());							
								//boolean idValue = loader.asMap().containsValue(data);
								String idValue = (String) FunctionCommon.getKeyFromValueOfMap(loader.asMap(), data);
								if (idValue == null) {
									errorInLine.add(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(fields.getColumnMessage())));								
									errorBussiness = true;								
								}
								else{
									dataArray[i]=idValue;
								}
							}
						}
						
						// validate data source
						if (fields.getDataSource() != null) {
							if(data.trim().length() > 0 ){
								Map<String, String> dataSourceMap = getDataSource(fields.getDataSource());
										//FunctionCommon.getCodeList(fields.getCodeList());							
								//boolean idValue = loader.asMap().containsValue(data);
								String idValue = (String) FunctionCommon.getKeyFromValueOfMap(dataSourceMap, data);
								if (idValue == null) {
									errorInLine.add(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(fields.getColumnMessage())));								
									errorBussiness = true;								
								}
								else{
									dataArray[i]=idValue;
								}
							}
						}
										
						//check line error
						if(errorBussiness){
							lineError = errorBussiness;
						}
						
					} // close check data is not empty
					
				} // close each line
				// add data into list of rowData, after that insert into database
				if(!lineError){
					rowsData.add(dataArray);
					rows.add(line);
					errorInLine.add(0, String.valueOf(line));
					errorInLine.add(1, String.valueOf(ImportManagementConst.IMPORT_RESULT_STATUS.DATA_CORRECT));
					errors.add((String[]) errorInLine.toArray(new String[errorInLine.size()]));
					
				} else {
					errorInLine.add(0, String.valueOf(line));
					errorInLine.add(1, String.valueOf(ImportManagementConst.IMPORT_RESULT_STATUS.ERROR));
					errors.add((String[]) errorInLine.toArray(new String[errorInLine.size()]));
				}
			}
		}
						
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put(ImportManagementConst.ERR_WHEN_IMPORT, errors);
		returnMap.put(ImportManagementConst.IMPORTTING_DATA, rowsData);
		returnMap.put(ImportManagementConst.ROWS, rows);		
		return returnMap;
	}

	private static void settingStyleForCellHadMerge(int style,CellRangeAddress region, Sheet sheet, Workbook wb) {
		
		switch (style) {
		case 1:
			
			break;

		default:
			
			final short borderMediumDashed = CellStyle.BORDER_THIN;
			//Setting style of border
			RegionUtil.setBorderBottom( borderMediumDashed, region, sheet, wb);
			RegionUtil.setBorderTop( borderMediumDashed, region, sheet, wb);
			RegionUtil.setBorderLeft( borderMediumDashed, region, sheet, wb);
			RegionUtil.setBorderRight( borderMediumDashed, region, sheet, wb);
			// Setting color of border
			RegionUtil.setBottomBorderColor(IndexedColors.BLACK.getIndex(), region, sheet, wb);
			RegionUtil.setTopBorderColor(IndexedColors.BLACK.getIndex(), region, sheet, wb);
			RegionUtil.setLeftBorderColor(IndexedColors.BLACK.getIndex(), region, sheet, wb);
			RegionUtil.setRightBorderColor(IndexedColors.BLACK.getIndex(), region, sheet, wb);

			break;
		}
	}
	public static Map<String,String> getDataSource(String sourceType){
		
		Map<String,String> returnMap = new HashMap<String, String>();
		switch (sourceType) {
			case ImportManagementConst.IMPORT_DATASOURCE.MESSAGE_DESIGN_OUTPUT_LOCATION:
				returnMap.put("0", "Project");
				returnMap.put("1", "Module");
				returnMap.put("2", "Screen");
				returnMap.put("3", "Screen area");
				returnMap.put("4", "Screen item");
				returnMap.put("5", "Blogic");
				returnMap.put("6", "Menu design");
				returnMap.put("7", "Design information");
				break;
			case ImportManagementConst.IMPORT_DATASOURCE.MESSAGE_DESIGN_ERROR_LEVEL:
				returnMap.put("sc", "Label");
				returnMap.put("inf", "Information");
				returnMap.put("wrn", "Warning");
				returnMap.put("err", "Error");
				break;
			case ImportManagementConst.IMPORT_DATASOURCE.DOMAIN_DESIGN_NULL_VALUE:
				returnMap.put("0", "Null");
				returnMap.put("1", "Not null");
				break;
			case ImportManagementConst.IMPORT_DATASOURCE.CODELIST_DESIGN_VALUES_ONLY:
				returnMap.put("0", "No");
				returnMap.put("1", "Yes");
				break;
			default:
				break;
		}
		return  returnMap;
	}
	
	public static int getLimitRowImport(){
		
		int limitRow =ImportManagementConst.LIMIT_ROW_IMPORT;
		ReloadableCodeList loaderBlogic = FunctionCommon.getReloadableCodeList(CODE_LIST_IMPORT_SETTING);
		if(loaderBlogic !=null){
			try{
				limitRow = Integer.parseInt(loaderBlogic.asMap().get(ImportManagementConst.LIMIT_ROW_IMPORT_NAME));
			}
			catch(Exception ex){
				return limitRow;
			}
		}
		return limitRow;
	}
	
	public static int getLimitFileSize(){
		
		int limitSize =ImportManagementConst.LIMIT_FILE_SIZE;		
		ReloadableCodeList loaderBlogic = FunctionCommon.getReloadableCodeList(CODE_LIST_IMPORT_SETTING);
		if(loaderBlogic !=null){
			try{
				limitSize = Integer.parseInt(loaderBlogic.asMap().get(ImportManagementConst.LIMIT_FILE_SIZE_NAME));
			}
			catch(Exception ex){
				return limitSize;
			}
		}	
		return limitSize;
	}
	
	private static String joinString(final Object[] array, String separator){
		if (array == null) {
            return null;
        }
		else {
	        if (separator == null) {
	            separator = "";
	        }
	        final StringBuilder buf = new StringBuilder(array.length * 16);

	        for (int i = 0; i < array.length; i++) {
	        	if (array[i] != null) {
	        		if (i > 0) {
		                buf.append(separator);
		            }
		            buf.append(array[i]);
	        	}	        	
	        }
	        return buf.toString();
		}
	}
	
}
