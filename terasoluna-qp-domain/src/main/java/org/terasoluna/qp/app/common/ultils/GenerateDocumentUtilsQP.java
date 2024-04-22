package org.terasoluna.qp.app.common.ultils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.domain.model.Account;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.Basetype;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.BusinessType;
import org.terasoluna.qp.domain.model.CodeList;
import org.terasoluna.qp.domain.model.CodeListDetail;
import org.terasoluna.qp.domain.model.FunctionDesign;
import org.terasoluna.qp.domain.model.GenerateDocumentDomainDesign;
import org.terasoluna.qp.domain.model.GenerateDocumentItem;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.model.SqlDesignResult;
import org.terasoluna.qp.domain.model.SqlDesignTable;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.service.generatedocument.GenerateDocumentConst;
import org.terasoluna.qp.domain.service.generatedocument.GenerateDocumentConst.DataSourceName;
import org.terasoluna.qp.domain.service.generatedocument.GenerateDocumentConst.EDDocumentTypeByModule;
import org.terasoluna.qp.domain.service.generatedocument.GenerateDocumentConst.EDDocumentTypeByProject;
import org.terasoluna.qp.domain.service.generatedocument.GenerateDocumentConst.HeaderCommon;
import org.terasoluna.qp.domain.service.generatedocument.GenerateDocumentConst.RDDocumentTypeByProject;
import org.terasoluna.qp.domain.service.generatedocument.GenerateDocumentConst.StringConstant;
import org.terasoluna.qp.domain.service.generatedocument.GenerateDocumentConst.StringPrefix;
import org.terasoluna.qp.domain.service.generatedocument.generatedocumentcommon.GenerateDocumentMessageDesign;
import org.terasoluna.qp.domain.service.generatedocument.generatedocumentcommon.GenerateDocumentTableDesign;

public class GenerateDocumentUtilsQP {
	
	/* Session information */
	public static Account currentAccount = new Account();
	public static Project currentProject = new Project();
	public static AccountProfile currentAccounProfile = new AccountProfile();
	
	public static StringBuilder excelFolderName;
	
	public static String templateFolderName = "/META-INF/template/";
	
	public static void createFolder(String folderPath) {
		try {
			FileUtilsQP.forceMkdir(new File(folderPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteFile(String folderPath) {
		for (File file : new File(folderPath).listFiles()) {
			if(!file.isDirectory()){
				file.delete();
				file.exists();
			}
		}
	}
	
	public static void deleteFolder(String folderPath) {
		try {
			File f = new File(folderPath);
			FileUtils.deleteDirectory(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void forceDeleteFolder(String folderPath) {
		File f = new File(folderPath);
		FileDeleteStrategy.FORCE.deleteQuietly(f);
	}
	
	public static void processGenerateRDDocumentByProject(GenerateDocumentItem item){

		switch (item.getDocumentItemType()) {
		
		case RDDocumentTypeByProject.BUSINESS_TYPE:
			processGenerateRDDocumentBusinessListByProject(item);
			break;
		case RDDocumentTypeByProject.FUNCTION_LIST:
			processGenerateRDDocumentFunctionListByProject(item);
			break;
		case RDDocumentTypeByProject.DOMAIN_DESIGN:
			processGenerateRDDocumentDomainDesignByProject(item);
			break;
		case RDDocumentTypeByProject.PROCESSING_LIST:
			processGenerateRDDocumentProcessingListByProject(item);
			break;	
		case RDDocumentTypeByProject.TABLE_DESIGN:
			processGenerateRDDocumentTableDesignByProjectNew(item, false);
			break;
		case RDDocumentTypeByProject.ONLINE_PROCESSING:
			processGenerateRDDocumentOnlineProcessingByProject(item);
			break;	
		}
	}

	private static void processGenerateRDDocumentBusinessListByProject(GenerateDocumentItem item) {

		try {
			// Template file had created by manual
			String outputFilePath = item.getExcelFolder() + item.getDocumentItemTemplateName();
			// Read template file
			InputStream is = GenerateDocumentConst.class.getResourceAsStream(templateFolderName+item.getDocumentItemTemplateName());
			Workbook wb = WorkbookFactory.create(is);
			Sheet sheet = wb.getSheetAt(0);
			
			// Process multiple language of template sheet
			processMultiLanguageForTemplate(sheet);

			// Setting header data information
			headerGenerateInformation(sheet.getRow(1), Arrays.asList(11,20,26,32,38));
			
			int firstRow = 4;
			int tempRow = 4;
			List<CellRangeAddress> cellRangeAddressList = new ArrayList<CellRangeAddress>();
			for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
				CellRangeAddress cellRangeAddress = sheet.getMergedRegion(i);
				if (cellRangeAddress.getFirstRow() == firstRow) {
					cellRangeAddressList.add(cellRangeAddress);
				}
			}
			if(CollectionUtils.isNotEmpty(item.getDataLst())) {
				// Setting body information
				List<?> businessTypeLst = item.getDataLst();
				
				for (Object obj : businessTypeLst) {
				
					BusinessType bt = (BusinessType) obj;
					Row row = sheet.getRow(firstRow);
					
					if( row == null){
						row = cloneRow(wb, sheet, tempRow, firstRow);
					}
					
					// Item no
					Cell cell = row.getCell(0);
					cell.setCellValue(firstRow - tempRow + 1);

					// business id
					cell = row.getCell(2);
					cell.setCellValue(StringPrefix.BUSINESS + StringUtils.leftPad(bt.getBusinessTypeId().toString(), GenerateDocumentConst.GENERATE_SIZE_ID, "0"));
					
					// business name
					cell = row.getCell(7);
					cell.setCellValue(bt.getBusinessTypeName());
					
					// business over view
					cell = row.getCell(13);
					cell.setCellValue(bt.getRemark());
					
					// Parent column
					cell = row.getCell(44);
					cell.setCellValue(bt.getParentBusinessTypeName());
					
					if(firstRow > tempRow) {
						for(CellRangeAddress cellRangeAddress : cellRangeAddressList ){
							CellRangeAddress newCellRangeAddress = new CellRangeAddress(firstRow,
									(firstRow + (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow())),
									cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastColumn());
							sheet.addMergedRegion(newCellRangeAddress);
						}
					}
					
					firstRow++;
				}
			}
			
			FileOutputStream out = new FileOutputStream(outputFilePath);
			wb.write(out);
			out.close();
			wb.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			item.setDataLst(null);
		}
	}
	
	private static void processGenerateRDDocumentFunctionListByProject(GenerateDocumentItem item) {

		try {
			String outputFilePath = item.getExcelFolder() + item.getDocumentItemTemplateName();
			// Read template file
			InputStream is = GenerateDocumentConst.class.getResourceAsStream(templateFolderName+item.getDocumentItemTemplateName());

			Workbook wb = WorkbookFactory.create(is);
			Sheet sheet = wb.getSheetAt(0);
			
			// Process multiple language of template sheet		
			processMultiLanguageForTemplate(sheet);
			
			int no = 1;
			int firstRowBusiness, firstRowModule, startRowIndex;
			firstRowBusiness = firstRowModule = startRowIndex = 4;

			// Set the border and border colors.
			CellRangeAddress cellRangeAddress;
			CellStyle style = wb.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_LEFT);
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			style.setFillBackgroundColor(HSSFColor.WHITE.index);
			
			CellStyle remarkStyle = wb.createCellStyle();
			remarkStyle.setAlignment(CellStyle.ALIGN_LEFT);
			remarkStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			remarkStyle.setWrapText(true);
			remarkStyle.setFillBackgroundColor(HSSFColor.WHITE.index);

			// Setting header information
			headerGenerateInformation(sheet.getRow(1), Arrays.asList(22,39,45,51,57));
			
			if(CollectionUtils.isNotEmpty(item.getDataLst())) {
				// Setting body information
				List<?> businessTypeLst = item.getDataLst();
				for (Object obj : businessTypeLst) {
					BusinessType bt = (BusinessType) obj;
					Row row = null;
					for (Module md : bt.getModules()) {
						for (FunctionDesign fd : md.getListFunctionDesign()) {

							row = sheet.createRow(startRowIndex);
							// function code
							Cell cell = row.createCell(24);
							cell.setCellValue(fd.getFunctionCode());
							cell.setCellStyle(style);
							cellRangeAddress = new CellRangeAddress(startRowIndex,startRowIndex, 24, 28);
							sheet.addMergedRegion(cellRangeAddress);
							settingStyleForCellHadMerge(0, cellRangeAddress, sheet,wb);
							// function name
							cell = row.createCell(29);
							cell.setCellValue(fd.getFunctionName());
							cell.setCellStyle(style);
							cellRangeAddress = new CellRangeAddress(startRowIndex,startRowIndex, 29, 36);
							sheet.addMergedRegion(cellRangeAddress);
							settingStyleForCellHadMerge(0, cellRangeAddress, sheet, wb);
							// function actor
							cell = row.createCell(37);
							cell.setCellValue(fd.getActor());
							cell.setCellStyle(style);
							cellRangeAddress = new CellRangeAddress(startRowIndex,startRowIndex, 37, 42);
							sheet.addMergedRegion(cellRangeAddress);
							settingStyleForCellHadMerge(0, cellRangeAddress, sheet,wb);

							CellStyle styleRunStyle = wb.createCellStyle();
							styleRunStyle.setAlignment(CellStyle.ALIGN_CENTER);
							styleRunStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
							if (fd.getFunctionType() != null && fd.getFunctionType() == 0) {
								// function online
								cell = row.createCell(43);
								cell.setCellValue(StringConstant.MARU);
								cell.setCellStyle(styleRunStyle);
								cellRangeAddress = new CellRangeAddress(startRowIndex, startRowIndex, 43, 46);
								sheet.addMergedRegion(cellRangeAddress);
								settingStyleForCellHadMerge(0, cellRangeAddress,sheet, wb);
								cell = row.createCell(47);
								cell.setCellStyle(styleRunStyle);
								cellRangeAddress = new CellRangeAddress(startRowIndex, startRowIndex, 47, 50);
								sheet.addMergedRegion(cellRangeAddress);
								settingStyleForCellHadMerge(0, cellRangeAddress,sheet, wb);
							} else if (fd.getFunctionType() != null && fd.getFunctionType() == 1) {
								// function batch
								cell = row.createCell(43);
								cell.setCellStyle(styleRunStyle);
								cellRangeAddress = new CellRangeAddress(startRowIndex, startRowIndex, 43, 46);
								sheet.addMergedRegion(cellRangeAddress);
								settingStyleForCellHadMerge(0, cellRangeAddress,sheet, wb);
								cell = row.createCell(47);
								cell.setCellValue(StringConstant.MARU);
								cell.setCellStyle(styleRunStyle);
								cellRangeAddress = new CellRangeAddress(startRowIndex, startRowIndex, 47, 50);
								sheet.addMergedRegion(cellRangeAddress);
								settingStyleForCellHadMerge(0, cellRangeAddress,sheet, wb);
							} else {
								// function batch
								cell = row.createCell(43);
								cell.setCellStyle(styleRunStyle);
								cellRangeAddress = new CellRangeAddress(startRowIndex, startRowIndex, 43, 46);
								sheet.addMergedRegion(cellRangeAddress);
								settingStyleForCellHadMerge(0, cellRangeAddress,sheet, wb);
								cell = row.createCell(47);
								cell.setCellStyle(styleRunStyle);
								cellRangeAddress = new CellRangeAddress(startRowIndex, startRowIndex, 47, 50);
								sheet.addMergedRegion(cellRangeAddress);
								settingStyleForCellHadMerge(0, cellRangeAddress,sheet, wb);
							}

							// function over view
							cell = row.createCell(51);
							cell.setCellValue(fd.getRemark());
							cell.setCellStyle(remarkStyle);
							cellRangeAddress = new CellRangeAddress(startRowIndex,startRowIndex, 51, 62);
							sheet.addMergedRegion(cellRangeAddress);
							settingStyleForCellHadMerge(0, cellRangeAddress, sheet, wb);

							// Item no
							cell = row.createCell(0);
							cell.setCellValue(no);
							/*CellStyle tmpCT = style;
							tmpCT.setAlignment(CellStyle.ALIGN_RIGHT);
							cell.setCellStyle(tmpCT);*/
							cellRangeAddress = new CellRangeAddress(startRowIndex,startRowIndex, 0, 1);
							sheet.addMergedRegion(cellRangeAddress);
							settingStyleForCellHadMerge(0, cellRangeAddress, sheet, wb);

							// module id
							cell = row.createCell(13);
							cell.setCellValue(StringPrefix.MODULE + StringUtils.leftPad(md.getModuleId().toString(), GenerateDocumentConst.GENERATE_SIZE_ID, "0"));
							cell.setCellStyle(style);
							// module name
							cell = row.createCell(18);
							cell.setCellValue(md.getModuleName());
							cell.setCellStyle(style);

							// business id
							cell = row.createCell(2);
							if(!bt.getBusinessTypeId().equals(0L)) cell.setCellValue(StringPrefix.BUSINESS + StringUtils.leftPad(bt.getBusinessTypeId().toString(), GenerateDocumentConst.GENERATE_SIZE_ID, "0"));
							cell.setCellStyle(style);
							// business name
							cell = row.createCell(7);
							cell.setCellValue(bt.getBusinessTypeName());
							cell.setCellStyle(style);

							startRowIndex++;
							no++;
						}

						// merge for module group
						// merge module id
						cellRangeAddress = new CellRangeAddress(firstRowModule,startRowIndex - 1, 13, 17);
						sheet.addMergedRegion(cellRangeAddress);
						settingStyleForCellHadMerge(0, cellRangeAddress, sheet, wb);
						// merge module name
						cellRangeAddress = new CellRangeAddress(firstRowModule,startRowIndex - 1, 18, 23);
						sheet.addMergedRegion(cellRangeAddress);
						settingStyleForCellHadMerge(0, cellRangeAddress, sheet, wb);
						
						
						if(bt.getBusinessTypeId().equals(0L)){
							// merge business id
							cellRangeAddress = new CellRangeAddress(firstRowModule,startRowIndex - 1, 2, 6);
							sheet.addMergedRegion(cellRangeAddress);
							settingStyleForCellHadMerge(0, cellRangeAddress, sheet, wb);
							// merge business name
							cellRangeAddress = new CellRangeAddress(firstRowModule,startRowIndex - 1, 7, 12);
							sheet.addMergedRegion(cellRangeAddress);
							settingStyleForCellHadMerge(0, cellRangeAddress, sheet, wb);
							firstRowBusiness = startRowIndex;
						}

						firstRowModule = startRowIndex;
					}

					if(!bt.getBusinessTypeId().equals(0L)){
						// merge for business group
						// merge business id
						cellRangeAddress = new CellRangeAddress(firstRowBusiness,startRowIndex - 1, 2, 6);
						sheet.addMergedRegion(cellRangeAddress);
						settingStyleForCellHadMerge(0, cellRangeAddress, sheet, wb);
						// merge business name
						cellRangeAddress = new CellRangeAddress(firstRowBusiness,startRowIndex - 1, 7, 12);
						sheet.addMergedRegion(cellRangeAddress);
						settingStyleForCellHadMerge(0, cellRangeAddress, sheet, wb);
						firstRowBusiness = startRowIndex;
					}
				}
			}

			FileOutputStream out = new FileOutputStream(outputFilePath);
			wb.write(out);
			out.close();
			wb.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			item.setDataLst(null);
		}
	}
	
	/**
	 * 
	 * @param item
	 */
	private static void processGenerateRDDocumentDomainDesignByProject(GenerateDocumentItem item) {
		String outputFilePath = item.getExcelFolder() + item.getDocumentItemTemplateName();
		// Keep 100 rows in memory, exceeding rows will be flushed to disk
		SXSSFWorkbook workbook = new SXSSFWorkbook(100);
		
		// Create cell style
		Font font = workbook.createFont();
		font.setBold(true);
		CellStyle headerCellStyleRow1 = workbook.createCellStyle();
		headerCellStyleRow1.setBorderLeft(CellStyle.BORDER_THIN);
		headerCellStyleRow1.setBorderRight(CellStyle.BORDER_THIN);
		headerCellStyleRow1.setBorderTop(CellStyle.BORDER_THIN);
		headerCellStyleRow1.setBorderBottom(CellStyle.BORDER_THIN);
		headerCellStyleRow1.setAlignment(CellStyle.ALIGN_CENTER);
		headerCellStyleRow1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headerCellStyleRow1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		headerCellStyleRow1.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerCellStyleRow1.setFont(font);
		headerCellStyleRow1.setWrapText(true);
		
		CellStyle headerCellStyleRow2 = workbook.createCellStyle();
		headerCellStyleRow2.setBorderLeft(CellStyle.BORDER_THIN);
		headerCellStyleRow2.setBorderRight(CellStyle.BORDER_THIN);
		headerCellStyleRow2.setBorderTop(CellStyle.BORDER_THIN);
		headerCellStyleRow2.setBorderBottom(CellStyle.BORDER_THIN);
		headerCellStyleRow2.setAlignment(CellStyle.ALIGN_CENTER);
		headerCellStyleRow2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
		CellStyle summaryCellStyle1 = workbook.createCellStyle();
		summaryCellStyle1.setBorderLeft(CellStyle.BORDER_THIN);
		summaryCellStyle1.setBorderRight(CellStyle.BORDER_THIN);
		summaryCellStyle1.setBorderTop(CellStyle.BORDER_THIN);
		summaryCellStyle1.setBorderBottom(CellStyle.BORDER_THIN);
		summaryCellStyle1.setAlignment(CellStyle.ALIGN_LEFT);
		summaryCellStyle1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		summaryCellStyle1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		summaryCellStyle1.setFillPattern(CellStyle.SOLID_FOREGROUND);
		summaryCellStyle1.setFont(font);
		
		CellStyle summaryCellStyle2 = workbook.createCellStyle();
		summaryCellStyle2.setBorderLeft(CellStyle.BORDER_THIN);
		summaryCellStyle2.setBorderRight(CellStyle.BORDER_THIN);
		summaryCellStyle2.setBorderTop(CellStyle.BORDER_THIN);
		summaryCellStyle2.setBorderBottom(CellStyle.BORDER_THIN);
		summaryCellStyle2.setAlignment(CellStyle.ALIGN_LEFT);
		summaryCellStyle2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
		// Create sheet Data Source
		Sheet sheetDataSource = workbook.createSheet("Data Source");
		
		Row rowDataSourceTitle = sheetDataSource.createRow(2);
		Cell cellDataType = rowDataSourceTitle.createCell(0);
		cellDataType.setCellValue("Data type");
		Cell cellValidationRule = rowDataSourceTitle.createCell(4);
		cellValidationRule.setCellValue("Validation rule");
		Cell cellDataSourceType = rowDataSourceTitle.createCell(8);
		cellDataSourceType.setCellValue("Data source type");
		
		Row rowDataSourceTableHeader = sheetDataSource.createRow(3);
		Cell cellDataTypeCode = rowDataSourceTableHeader.createCell(1);
		cellDataTypeCode.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.CODE));
		cellDataTypeCode.setCellStyle(summaryCellStyle1);
		Cell cellDataTypeName = rowDataSourceTableHeader.createCell(2);
		cellDataTypeName.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.NAME));
		cellDataTypeName.setCellStyle(summaryCellStyle1);
		Cell cellValidationRuleCode = rowDataSourceTableHeader.createCell(5);
		cellValidationRuleCode.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.CODE));
		cellValidationRuleCode.setCellStyle(summaryCellStyle1);
		Cell cellValidationRuleName = rowDataSourceTableHeader.createCell(6);
		cellValidationRuleName.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.NAME));
		cellValidationRuleName.setCellStyle(summaryCellStyle1);
		Cell cellDataSourceTypeCode = rowDataSourceTableHeader.createCell(9);
		cellDataSourceTypeCode.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.CODE));
		cellDataSourceTypeCode.setCellStyle(summaryCellStyle1);
		Cell cellDataSourceTypeName = rowDataSourceTableHeader.createCell(10);
		cellDataSourceTypeName.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.NAME));
		cellDataSourceTypeName.setCellStyle(summaryCellStyle1);
		
		List<String> dataTypeCollection = new ArrayList<String>();
		for(Basetype type : item.getListBasetype()){
			dataTypeCollection.add(type.getBasetypeName());
		}
		
		List<String> dataSourceTypeCollection = new ArrayList<String>();
		dataSourceTypeCollection.add(MessageUtils.getMessage(GenerateDocumentConst.DomainDesign.DTS_USER_DEFINE));
		dataSourceTypeCollection.add(MessageUtils.getMessage(GenerateDocumentConst.DomainDesign.DTS_CODELIST));
		dataSourceTypeCollection.add(MessageUtils.getMessage(GenerateDocumentConst.DomainDesign.DTS_SQL_BUILDER));
		
		@SuppressWarnings("unchecked")
		List<String> validationRuleCollection = (List<String>) item.getData();
		
		int maxLoop = 0;
		
		if(dataTypeCollection.size() <= validationRuleCollection.size()) {
			maxLoop = validationRuleCollection.size() + 4;
		} else {
			maxLoop = dataTypeCollection.size() + 4;
		}
		
		for(int i = 4; i < maxLoop; i++) {
			Row tempRow = sheetDataSource.createRow(i);
			// Data type
			if(i < dataTypeCollection.size() + 4) {
				Cell tempCellCode = tempRow.createCell(1);
				Cell tempCellName = tempRow.createCell(2);
				tempCellCode.setCellValue(i - 3);
				tempCellName.setCellValue(dataTypeCollection.get(i - 4));
				tempCellCode.setCellStyle(summaryCellStyle2);
				tempCellName.setCellStyle(summaryCellStyle2);
			}
			
			// Data source type
			if(i < dataSourceTypeCollection.size() + 4) {
				Cell tempCellCode = tempRow.createCell(9);
				Cell tempCellName = tempRow.createCell(10);
				tempCellCode.setCellValue(i - 4);
				tempCellName.setCellValue(dataSourceTypeCollection.get(i - 4));
				tempCellCode.setCellStyle(summaryCellStyle2);
				tempCellName.setCellStyle(summaryCellStyle2);
			}
			
			// Validation rule
			if(i < validationRuleCollection.size() + 4) {
				Cell tempCellCode = tempRow.createCell(5);
				Cell tempCellName = tempRow.createCell(6);
				tempCellCode.setCellValue(validationRuleCollection.get(i - 4));
				tempCellName.setCellValue(validationRuleCollection.get(i - 4));
				tempCellCode.setCellStyle(summaryCellStyle2);
				tempCellName.setCellStyle(summaryCellStyle2);
			}
		}
		
		sheetDataSource.autoSizeColumn(2);
		sheetDataSource.autoSizeColumn(5);
		sheetDataSource.autoSizeColumn(6);
		sheetDataSource.autoSizeColumn(10);
		
		// Create sheet domain definition document
		Sheet sheetDomain = workbook.createSheet("DomainDefinitionDocument");
		workbook.setActiveSheet(1);
		
		// Create merged cell
		CellRangeAddress documentName = new CellRangeAddress(0, 1, 0, 10);
		CellRangeAddress systemNameLabel = new CellRangeAddress(0, 0, 11, 19);
		CellRangeAddress systemNameValue = new CellRangeAddress(1, 1, 11, 19);
		CellRangeAddress emptyLabel1 = new CellRangeAddress(0, 0, 20, 28);
		CellRangeAddress emptyValue1 = new CellRangeAddress(1, 1, 20, 28);
		CellRangeAddress emptyLabel2 = new CellRangeAddress(0, 0, 29, 35);
		CellRangeAddress emptyValue2 = new CellRangeAddress(1, 1, 29, 35);
		CellRangeAddress createdByLabel = new CellRangeAddress(0, 0, 36, 41);
		CellRangeAddress createdByValue = new CellRangeAddress(1, 1, 36, 41);
		CellRangeAddress createdDateLabel = new CellRangeAddress(0, 0, 42, 47);
		CellRangeAddress createdDateValue = new CellRangeAddress(1, 1, 42, 47);
		CellRangeAddress updatedByLabel = new CellRangeAddress(0, 0, 48, 53);
		CellRangeAddress updatedByValue = new CellRangeAddress(1, 1, 48, 53);
		CellRangeAddress updatedDateLabel = new CellRangeAddress(0, 0, 54, 69);
		CellRangeAddress updatedDateValue = new CellRangeAddress(1, 1, 54, 69);
		
		CellRangeAddress id = new CellRangeAddress(3, 4, 0, 1);
		CellRangeAddress majorClassification = new CellRangeAddress(3, 4, 2, 4);
		CellRangeAddress subClassification = new CellRangeAddress(3, 4, 5, 7);
		CellRangeAddress minorClassification = new CellRangeAddress(3, 4, 8, 10);
		CellRangeAddress domainName = new CellRangeAddress(3, 4, 11, 16);
		CellRangeAddress physicalDomainName = new CellRangeAddress(3, 4, 17, 22);
		CellRangeAddress logicalDataType = new CellRangeAddress(3, 4, 23, 27);
		CellRangeAddress physicalDataType = new CellRangeAddress(3, 4, 28, 32);
		CellRangeAddress description = new CellRangeAddress(3, 4, 33, 41);
		CellRangeAddress descriptionFormat = new CellRangeAddress(3, 4, 42, 47);
		CellRangeAddress minimumValue = new CellRangeAddress(3, 4, 48, 51);
		CellRangeAddress maximumValue = new CellRangeAddress(3, 4, 52, 55);
		CellRangeAddress defaultValue = new CellRangeAddress(3, 4, 56, 59);
		CellRangeAddress validationRule = new CellRangeAddress(3, 4, 60, 63);
		CellRangeAddress datasource = new CellRangeAddress(3, 3, 64, 69);
		CellRangeAddress dataSourceName = new CellRangeAddress(4, 4, 64, 66);
		CellRangeAddress dataSourceCode = new CellRangeAddress(4, 4, 67, 69);
		
		sheetDomain.addMergedRegion(documentName);
		sheetDomain.addMergedRegion(systemNameLabel);
		sheetDomain.addMergedRegion(systemNameValue);
		sheetDomain.addMergedRegion(emptyLabel1);
		sheetDomain.addMergedRegion(emptyValue1);
		sheetDomain.addMergedRegion(emptyLabel2);
		sheetDomain.addMergedRegion(emptyValue2);
		sheetDomain.addMergedRegion(createdByLabel);
		sheetDomain.addMergedRegion(createdByValue);
		sheetDomain.addMergedRegion(createdDateLabel);
		sheetDomain.addMergedRegion(createdDateValue);
		sheetDomain.addMergedRegion(updatedByLabel);
		sheetDomain.addMergedRegion(updatedByValue);
		sheetDomain.addMergedRegion(updatedDateLabel);
		sheetDomain.addMergedRegion(updatedDateValue);
		
		sheetDomain.addMergedRegion(id);
		sheetDomain.addMergedRegion(majorClassification);
		sheetDomain.addMergedRegion(subClassification);
		sheetDomain.addMergedRegion(minorClassification);
		sheetDomain.addMergedRegion(domainName);
		sheetDomain.addMergedRegion(physicalDomainName);
		sheetDomain.addMergedRegion(logicalDataType);
		sheetDomain.addMergedRegion(physicalDataType);
		sheetDomain.addMergedRegion(description);
		sheetDomain.addMergedRegion(descriptionFormat);
		sheetDomain.addMergedRegion(minimumValue);
		sheetDomain.addMergedRegion(maximumValue);
		sheetDomain.addMergedRegion(defaultValue);
		sheetDomain.addMergedRegion(validationRule);
		sheetDomain.addMergedRegion(datasource);
		sheetDomain.addMergedRegion(dataSourceName);
		sheetDomain.addMergedRegion(dataSourceCode);
		
		Row row1 = sheetDomain.createRow(0);
		Row row2 = sheetDomain.createRow(1);
		Row row4 = sheetDomain.createRow(3);
		Row row5 = sheetDomain.createRow(4);
		
		row4.setHeight((short) (20*20));
		row5.setHeight((short) (30*20));
		
		for(int i = 0; i < 70; i++) {
			sheetDomain.setColumnWidth(i, 5*256);
			
			Cell tempCellRow1 = row1.createCell(i);
			tempCellRow1.setCellStyle(headerCellStyleRow1);
			
			Cell tempCellRow2 = row2.createCell(i);
			tempCellRow2.setCellStyle(headerCellStyleRow2);
			
			Cell tempCellRow4 = row4.createCell(i);
			tempCellRow4.setCellStyle(headerCellStyleRow1);

			Cell tempCellRow5 = row5.createCell(i);
			tempCellRow5.setCellStyle(headerCellStyleRow1);
			
			// Insert value to cell
			switch(i) {
			case 0:
				tempCellRow1.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.DomainDesign.TITLE));
				tempCellRow4.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.DomainDesign.ID));
				break;
			case 2:
				tempCellRow4.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.DomainDesign.MAJOR_CLASS));
				break;
			case 5:
				tempCellRow4.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.DomainDesign.SUB_CLASS));
				break;
			case 8:
				tempCellRow4.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.DomainDesign.MINOR_CLASS));
				break;
			case 11:
				tempCellRow1.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.Header.SYSTEM_NAME));
				tempCellRow2.setCellValue(currentProject.getProjectName());
				tempCellRow4.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.DomainDesign.DOMAIN_NAME));
				break;
			case 17:
				tempCellRow4.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.DomainDesign.PHYSICAL_DOMAIN_NAME));
				break;
			case 23:
				tempCellRow4.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.DomainDesign.LOGICAL_DATA_TYPE));
				break;
			case 28:
				tempCellRow4.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.DomainDesign.PHYSICAL_DATA_TYPE));
				break;
			case 33:
				tempCellRow4.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.DomainDesign.DESCRIPTION));
				break;
			case 36:
				tempCellRow1.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.Header.CREATE_BY));
				tempCellRow2.setCellValue(currentAccount.getUsername());
				break;
			case 42:
				tempCellRow4.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.DomainDesign.DESCRIPTION_FORMAT));
				tempCellRow1.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.Header.CREATE_DATE));
				DateFormat df = new SimpleDateFormat(currentAccounProfile.getDateFormat());
				tempCellRow2.setCellValue(df.format(new Date()).toString());
				break;
			case 48:
				tempCellRow4.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.DomainDesign.MINIMUM_VALUE));
				tempCellRow1.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.Header.UPDATE_BY));
				break;
			case 52:
				tempCellRow4.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.DomainDesign.MAXIMUM_VALUE));
				break;
			case 54:
				tempCellRow1.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.Header.UPDATE_DATE));
				//DateFormat dateFormat = new SimpleDateFormat(currentAccounProfile.getDateFormat());
				break;
			case 56:
				tempCellRow4.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.DomainDesign.DEFAULT_VALUE));
				break;
			case 60:
				tempCellRow4.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.DomainDesign.VALIDATION_RULE));
				break;
			case 64:
				tempCellRow4.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.DomainDesign.DATA_SOURCE));
				tempCellRow5.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.DomainDesign.DATA_SOURCE_NAME));
				break;
			case 67:
				tempCellRow5.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.DomainDesign.DATA_SOURCE_CODE));
				break;
			}
		}
		
		@SuppressWarnings("unchecked")
		List<GenerateDocumentDomainDesign> listDomainDesign = (List<GenerateDocumentDomainDesign>) item.getDataLst();
		for(int i = 0; i < listDomainDesign.size(); i++) {
			CellRangeAddress tempId = new CellRangeAddress(i + 5, i + 5, 0, 1);
			CellRangeAddress tempMajorClassification = new CellRangeAddress(i + 5, i + 5, 2, 4);
			CellRangeAddress tempSubClassification = new CellRangeAddress(i + 5, i + 5, 5, 7);
			CellRangeAddress tempMinorClassification = new CellRangeAddress(i + 5, i + 5, 8, 10);
			CellRangeAddress tempDomainName = new CellRangeAddress(i + 5, i + 5, 11, 16);
			CellRangeAddress tempPhysicalDomainName = new CellRangeAddress(i + 5, i + 5, 17, 22);
			CellRangeAddress tempLogicalDataTypeName = new CellRangeAddress(i + 5, i + 5, 23, 25);
			CellRangeAddress tempLogicalDataTypeValue = new CellRangeAddress(i + 5, i + 5, 26, 27);
			CellRangeAddress tempPhysicalDataTypeName = new CellRangeAddress(i + 5, i + 5, 28, 30);
			CellRangeAddress tempPhysicalDataTypeValue = new CellRangeAddress(i + 5, i + 5, 31, 32);
			CellRangeAddress tempDescription = new CellRangeAddress(i + 5, i + 5, 33, 41);
			CellRangeAddress tempDescriptionFormat = new CellRangeAddress(i + 5, i + 5, 42, 47);
			CellRangeAddress tempMinimumValue = new CellRangeAddress(i + 5, i + 5, 48, 51);
			CellRangeAddress tempMaximumValue = new CellRangeAddress(i + 5, i + 5, 52, 55);
			CellRangeAddress tempDefaultValue = new CellRangeAddress(i + 5, i + 5, 56, 59);
			CellRangeAddress tempValidationRule = new CellRangeAddress(i + 5, i + 5, 60, 63);
			CellRangeAddress tempDatasourceName = new CellRangeAddress(i + 5, i + 5, 64, 66);
			CellRangeAddress tempDataSourceCode = new CellRangeAddress(i + 5, i + 5, 67, 69);
			
			sheetDomain.addMergedRegion(tempId);
			sheetDomain.addMergedRegion(tempMajorClassification);
			sheetDomain.addMergedRegion(tempSubClassification);
			sheetDomain.addMergedRegion(tempMinorClassification);
			sheetDomain.addMergedRegion(tempDomainName);
			sheetDomain.addMergedRegion(tempPhysicalDomainName);
			sheetDomain.addMergedRegion(tempLogicalDataTypeName);
			sheetDomain.addMergedRegion(tempLogicalDataTypeValue);
			sheetDomain.addMergedRegion(tempPhysicalDataTypeName);
			sheetDomain.addMergedRegion(tempPhysicalDataTypeValue);
			sheetDomain.addMergedRegion(tempDescription);
			sheetDomain.addMergedRegion(tempDescriptionFormat);
			sheetDomain.addMergedRegion(tempMinimumValue);
			sheetDomain.addMergedRegion(tempMaximumValue);
			sheetDomain.addMergedRegion(tempDefaultValue);
			sheetDomain.addMergedRegion(tempValidationRule);
			sheetDomain.addMergedRegion(tempDatasourceName);
			sheetDomain.addMergedRegion(tempDataSourceCode);
			
			Row tempRow = sheetDomain.createRow(i + 5);
			for(int j = 0; j < 70; j++) {
				Cell tempCell = tempRow.createCell(j);
				tempCell.setCellStyle(summaryCellStyle2);
				
				// Data source and data source type
				String datasoureType = null;
				String datasourceId = null;
				String datasourceName =  null;
				
				if(listDomainDesign.get(i).getDatasourceType() == null) {
					datasoureType = datasourceId = StringUtils.EMPTY;
				} else if(DbDomainConst.DatasourceType.USER_DEFINE == listDomainDesign.get(i).getDatasourceType()) {
					datasoureType = DataSourceName.USER_DEFINE;
					datasourceName = StringUtils.EMPTY;
				} else if(DbDomainConst.DatasourceType.CODELIST == listDomainDesign.get(i).getDatasourceType()) {
					datasoureType = DataSourceName.CODE_LIST;
					datasourceId = StringConstant.CODE_LIST;
					datasourceName = listDomainDesign.get(i).getDatasourceId().toString();
				} else if(DbDomainConst.DatasourceType.SQL_BUILDER == listDomainDesign.get(i).getDatasourceType()) {
					datasoureType = DataSourceName.SQL_BUILDER;
					datasourceId = StringConstant.SQL_BUILDER;
					datasourceName = listDomainDesign.get(i).getDatasourceId().toString();
				} else if (DbDomainConst.DatasourceType.SQL_BUILDER_IS_AUTOCOMPLETE == listDomainDesign.get(i).getDatasourceType()) {
					datasoureType = DataSourceName.SQL_AUTOCOMPLETE;
					datasourceId = StringConstant.SQL_AUTOCOMPLETE;
					datasourceName = listDomainDesign.get(i).getDatasourceId().toString();
				}
				
				switch(j) {
				case 0:
					tempCell.setCellValue(StringPrefix.DOMAIN + StringUtils.leftPad(listDomainDesign.get(i).getDomainId().toString(), GenerateDocumentConst.GENERATE_SIZE_ID, "0"));
					break;
				case 2:
					tempCell.setCellValue(listDomainDesign.get(i).getMajorClassification() != null ? listDomainDesign.get(i).getMajorClassification() : "");
					break;
				case 5:
					tempCell.setCellValue(listDomainDesign.get(i).getSubClassification() != null ? listDomainDesign.get(i).getSubClassification() : "");
					break;
				case 8:
					tempCell.setCellValue(listDomainDesign.get(i).getMinorClassification() != null ? listDomainDesign.get(i).getMinorClassification() : "");
					break;
				case 11:
					tempCell.setCellValue(listDomainDesign.get(i).getDomainName() != null ? listDomainDesign.get(i).getDomainName() : "");
					break;
				case 17:
					tempCell.setCellValue(listDomainDesign.get(i).getDomainCode() != null ? listDomainDesign.get(i).getDomainCode() : "");
					break;
				case 23:
					tempCell.setCellValue(listDomainDesign.get(i).getBaseTypeName() != null ? listDomainDesign.get(i).getBaseTypeName() : "");
					
					break;
				case 26:
					tempCell.setCellValue(listDomainDesign.get(i).getMaxLength());
					break;
				case 28:
					tempCell.setCellValue(listDomainDesign.get(i).getBaseTypeName() != null ? listDomainDesign.get(i).getBaseTypeName() : "");
					break;
				case 31:
					tempCell.setCellValue(listDomainDesign.get(i).getMaxLength());
					break;
				case 33:
					tempCell.setCellValue(listDomainDesign.get(i).getRemark() != null ? listDomainDesign.get(i).getRemark() : "");
					break;
				case 42:
					tempCell.setCellValue(listDomainDesign.get(i).getDescriptionFormat() != null ? listDomainDesign.get(i).getDescriptionFormat() : "");
					break;
				case 48:
					tempCell.setCellValue(listDomainDesign.get(i).getMinVal() != null ? listDomainDesign.get(i).getMinVal() : StringConstant.DASH);
					break;
				case 52:
					tempCell.setCellValue(listDomainDesign.get(i).getMaxVal() != null ? listDomainDesign.get(i).getMaxVal() : StringConstant.DASH);
					break;
				case 56:
					tempCell.setCellValue(listDomainDesign.get(i).getDefaultValue() != null ? listDomainDesign.get(i).getDefaultValue() : StringConstant.DASH);
					break;
				case 60:
					tempCell.setCellValue(listDomainDesign.get(i).getFmtCode() != null ? listDomainDesign.get(i).getFmtCode() : "");
					break;
				case 64:
					tempCell.setCellValue(datasoureType);
					break;
				case 67:
					tempCell.setCellValue(listDomainDesign.get(i).getDatasourceId() == null ? 
							StringUtils.EMPTY:listDomainDesign.get(i).getDatasourceType() == listDomainDesign.get(i).getDatasourceType() ? datasourceName:datasourceId + datasourceName);
					break;
				}
			}
		}
		
		// Create data validation (drop down list)
		int firstRow = 4;
		String dataSourceDataType = "=\''Data Source\''!$C${0}:$C${1}";
		String dataSourceValidationRule = "=\''Data Source\''!$G${0}:$G${1}";
		String dataSourceDataSourceType = "=\''Data Source\''!$K${0}:$K${1}";
		dataSourceDataType = MessageFormat.format(dataSourceDataType, String.valueOf(firstRow + 1), String.valueOf(firstRow + dataTypeCollection.size()));
		dataSourceValidationRule = MessageFormat.format(dataSourceValidationRule, String.valueOf(firstRow + 1), String.valueOf(firstRow + validationRuleCollection.size()));
		dataSourceDataSourceType = MessageFormat.format(dataSourceDataSourceType, String.valueOf(firstRow + 1), String.valueOf(firstRow + dataSourceTypeCollection.size()));
			
		DataValidationHelper dvHelper = sheetDomain.getDataValidationHelper();
		DataValidationConstraint dvConstraintDataType = dvHelper.createFormulaListConstraint(dataSourceDataType);
		DataValidationConstraint dvConstraintValidationRule = dvHelper.createFormulaListConstraint(dataSourceValidationRule);
		DataValidationConstraint dvContrainstDataSourceType = dvHelper.createFormulaListConstraint(dataSourceDataSourceType);
		CellRangeAddressList addressListLogicalDataType = new CellRangeAddressList(5, listDomainDesign.size() + 4, 23, 25);
		CellRangeAddressList addressListPhysicalDataType = new CellRangeAddressList(5, listDomainDesign.size() + 4, 28, 30);
		CellRangeAddressList addressListValidationRule = new CellRangeAddressList(5, listDomainDesign.size() + 4, 60, 63);
		CellRangeAddressList addressListDatasourceName = new CellRangeAddressList(5, listDomainDesign.size() + 4, 64, 66);
		DataValidation validationLogicalDataType = dvHelper.createValidation(dvConstraintDataType, addressListLogicalDataType);
		DataValidation validationPhysicalDataType = dvHelper.createValidation(dvConstraintDataType, addressListPhysicalDataType);
		DataValidation validationValidationRule = dvHelper.createValidation(dvConstraintValidationRule, addressListValidationRule);
		DataValidation validationDatasourceName = dvHelper.createValidation(dvContrainstDataSourceType, addressListDatasourceName);
		
//		if(validationLogicalDataType instanceof XSSFDataValidation) {
//			validationLogicalDataType.setSuppressDropDownArrow(true);
			validationLogicalDataType.setShowErrorBox(true);
//		} else {
//			validationLogicalDataType.setSuppressDropDownArrow(false);
//		}
//		
//		if(validationPhysicalDataType instanceof XSSFDataValidation) {
//			validationPhysicalDataType.setSuppressDropDownArrow(true);
			validationPhysicalDataType.setShowErrorBox(true);
//		} else {
//			validationPhysicalDataType.setSuppressDropDownArrow(false);
//		}
//		
//		if(validationValidationRule instanceof XSSFDataValidation) {
//			validationValidationRule.setSuppressDropDownArrow(true);
			validationValidationRule.setShowErrorBox(true);
//		} else {
//			validationValidationRule.setSuppressDropDownArrow(false);
//		}
//		
//		if(validationDatasourceName instanceof XSSFDataValidation) {
//			validationDatasourceName.setSuppressDropDownArrow(true);
			validationDatasourceName.setShowErrorBox(true);
//		} else {
//			validationDatasourceName.setSuppressDropDownArrow(false);
//		}
		
		sheetDomain.addValidationData(validationLogicalDataType);
		sheetDomain.addValidationData(validationPhysicalDataType);
		sheetDomain.addValidationData(validationValidationRule);
		sheetDomain.addValidationData(validationDatasourceName);
		
		try {
			FileOutputStream out = new FileOutputStream(outputFilePath);
			workbook.write(out);
			out.close();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			workbook.dispose();
			item.setDataLst(null);
		}
	}
	
	private static void processGenerateRDDocumentProcessingListByProject(GenerateDocumentItem item) {
		try {
			String outputFilePath = item.getExcelFolder() + item.getDocumentItemTemplateName();
			// Read template file
			InputStream is = GenerateDocumentConst.class.getResourceAsStream(templateFolderName+item.getDocumentItemTemplateName());

			Workbook wb = WorkbookFactory.create(is);
			Sheet tempSheet = wb.getSheetAt(0);
			
			// Process multiple language of template sheet		
			processMultiLanguageForTemplate(tempSheet);

			// Set the border and border colors.
			CellStyle style = wb.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_LEFT);
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			style.setBorderBottom(CellStyle.BORDER_THIN);

			// Setting header information
			headerGenerateInformation(tempSheet.getRow(1), Arrays.asList(11, 38, 44, 50, 56));

			if (CollectionUtils.isNotEmpty(item.getDataLst())) {
				// Setting body information
				int itemNo = 1;
				int firstRowFunction = 4;
				int startRowIndex = 4;
				int countLstItem = 0;
				
				List<?> businessDesignLst = item.getDataLst();
				Row row = null;
				BusinessDesign businessDesign = (BusinessDesign) businessDesignLst.get(0);
				Cell cell = null;
				Sheet sheet = wb.cloneSheet(0);
				wb.setSheetName(wb.getSheetIndex(sheet), businessDesign.getBusinessTypeName());
				CellRangeAddress cellRangeAddress = null;
				Long tempFunctionId = businessDesign.getFunctionDesignId();
				String tempBusinessTypeId = businessDesign.getBusinessTypeId();
				
				for (Object obj : businessDesignLst) {
					businessDesign = (BusinessDesign) obj;
					
					if (!tempBusinessTypeId.equals(businessDesign.getBusinessTypeId())) {
						// Merged last function
						cellRangeAddress = new CellRangeAddress(firstRowFunction, startRowIndex - 1, 2, 6);
						sheet.addMergedRegion(cellRangeAddress);
						settingStyleForCellHadMerge(0, cellRangeAddress, sheet, wb);
						
						cellRangeAddress = new CellRangeAddress(firstRowFunction, startRowIndex - 1, 7, 14);
						sheet.addMergedRegion(cellRangeAddress);
						settingStyleForCellHadMerge(0, cellRangeAddress, sheet, wb);
						
						cellRangeAddress = new CellRangeAddress(firstRowFunction, startRowIndex - 1, 0, 61);
						RegionUtil.setBorderBottom(CellStyle.BORDER_MEDIUM, cellRangeAddress, sheet, wb);
						
						// Reset
						sheet = wb.cloneSheet(0);
						wb.setSheetName(wb.getSheetIndex(sheet), businessDesign.getBusinessTypeName());	
						itemNo = 1;
						firstRowFunction = 4;
						startRowIndex = 4;
						tempBusinessTypeId = businessDesign.getBusinessTypeId();
						tempFunctionId = businessDesign.getFunctionDesignId();
						
						// Business name
						cell = sheet.getRow(1).createCell(20);
						cell.setCellValue(businessDesign.getBusinessTypeName());
						cell.setCellStyle(style);
					}
						
					row = sheet.createRow(startRowIndex);
					
					// Business name
					cell = sheet.getRow(1).createCell(20);
					cell.setCellValue(businessDesign.getBusinessTypeName());
					cell.setCellStyle(style);
					
					// function id
					cell = row.createCell(2);
					cell.setCellValue(businessDesign.getFunctionDesignCode());
					cell.setCellStyle(style);
					
					// function name
					cell = row.createCell(7);
					cell.setCellValue(businessDesign.getFunctionDesignName());
					cell.setCellStyle(style);
					
					// Item no
					cell = row.createCell(0);
					cell.setCellValue(itemNo);
					cellRangeAddress = new CellRangeAddress(startRowIndex, startRowIndex, 0, 1);
					sheet.addMergedRegion(cellRangeAddress);
					settingStyleForCellHadMerge(0, cellRangeAddress, sheet, wb);
					
					// Processing ID
					cell = row.createCell(15);
					cell.setCellValue(businessDesign.getBusinessLogicCode());
					cell.setCellStyle(style);
					cellRangeAddress = new CellRangeAddress(startRowIndex, startRowIndex, 15, 20);
					sheet.addMergedRegion(cellRangeAddress);
					settingStyleForCellHadMerge(0, cellRangeAddress, sheet, wb);
					
					// Processing Name
					cell = row.createCell(21);
					cell.setCellValue(businessDesign.getBusinessLogicName());
					cell.setCellStyle(style);
					cellRangeAddress = new CellRangeAddress(startRowIndex, startRowIndex, 21, 32);
					sheet.addMergedRegion(cellRangeAddress);
					settingStyleForCellHadMerge(0, cellRangeAddress, sheet, wb);
					
					// Overview
					cell = row.createCell(33);
					cell.setCellValue(businessDesign.getRemark());
					cell.setCellStyle(style);
					cellRangeAddress = new CellRangeAddress(startRowIndex, startRowIndex, 33, 61);
					sheet.addMergedRegion(cellRangeAddress);
					settingStyleForCellHadMerge(0, cellRangeAddress, sheet, wb);
					RegionUtil.setBorderRight(CellStyle.BORDER_MEDIUM, cellRangeAddress, sheet, wb);
						
					// Merged body function
					if (!businessDesign.getFunctionDesignId().toString().equals(tempFunctionId.toString())) {
						cellRangeAddress = new CellRangeAddress(firstRowFunction, startRowIndex - 1, 2, 6);
						sheet.addMergedRegion(cellRangeAddress);
						settingStyleForCellHadMerge(0, cellRangeAddress, sheet, wb);
						
						cellRangeAddress = new CellRangeAddress(firstRowFunction, startRowIndex - 1, 7, 14);
						sheet.addMergedRegion(cellRangeAddress);
						settingStyleForCellHadMerge(0, cellRangeAddress, sheet, wb);
						
						tempFunctionId = businessDesign.getFunctionDesignId();
						firstRowFunction = startRowIndex;
					}

					itemNo++;
					startRowIndex++;
					countLstItem++;
						
					// Merged last function of last sheet
					if (countLstItem == businessDesignLst.size()) {
						cellRangeAddress = new CellRangeAddress(firstRowFunction, startRowIndex - 1, 2, 6);
						sheet.addMergedRegion(cellRangeAddress);
						settingStyleForCellHadMerge(0, cellRangeAddress, sheet, wb);
						
						cellRangeAddress = new CellRangeAddress(firstRowFunction, startRowIndex - 1, 7, 14);
						sheet.addMergedRegion(cellRangeAddress);
						settingStyleForCellHadMerge(0, cellRangeAddress, sheet, wb);
						
						cellRangeAddress = new CellRangeAddress(firstRowFunction, startRowIndex - 1, 0, 61);
						RegionUtil.setBorderBottom(CellStyle.BORDER_MEDIUM, cellRangeAddress, sheet, wb);
					}
				}
				wb.removeSheetAt(0);
			}

			FileOutputStream out = new FileOutputStream(outputFilePath);
			wb.write(out);
			out.close();
			wb.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			item.setDataLst(null);
		}
	}
	
	private static void processGenerateRDDocumentOnlineProcessingByProject(GenerateDocumentItem item) {
		try {
			for( Object obj : item.getDataLst()){
				
				String outputFilePath = item.getExcelFolder() + item.getDocumentItemTemplateName();
				// Read template file
				InputStream is = GenerateDocumentConst.class.getResourceAsStream(templateFolderName+item.getDocumentItemTemplateName());

				Workbook wb = WorkbookFactory.create(is);
				Sheet tempSheet = wb.getSheetAt(0);
				
				// Process multiple language of template sheet		
				processMultiLanguageForTemplate(tempSheet);

				// Set the border and border colors.
				CellStyle style = wb.createCellStyle();
				style.setAlignment(CellStyle.ALIGN_LEFT);
				style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				style.setBorderBottom(CellStyle.BORDER_THIN);

				// Setting header information
				headerGenerateInformation(tempSheet.getRow(1), Arrays.asList(11, 38, 44, 50, 56));
				
				GenerateDocumentItem generateDocumentItem = (GenerateDocumentItem) obj;
				BusinessDesign businessDesign = (BusinessDesign) generateDocumentItem.getData();
				
				Row headerRow = tempSheet.getRow(1);
				headerRow.getCell(17).setCellValue(businessDesign.getBusinessTypeName());
				headerRow.getCell(24).setCellValue(businessDesign.getFunctionDesignName());
				headerRow.getCell(31).setCellValue(businessDesign.getBusinessLogicName());
				
				
				int imageSize = 0;
				// add processing flow			
				String imagePath = generateDocumentItem.getCapturePath();
				if( imagePath != null){
					File file = new File(imagePath);
					if( file.exists() ) {
						InputStream inputStream = new FileInputStream(imagePath);
						byte[] imageBytes = IOUtils.toByteArray(inputStream);
						int pictureureIdx = wb.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
		
						inputStream.close();
						CreationHelper helper = wb.getCreationHelper();
						Drawing drawing = tempSheet.createDrawingPatriarch();
						ClientAnchor anchor = helper.createClientAnchor();
		
						int imageRow = 9;
						int imageCol = 1;
						
						anchor.setRow1(imageRow);
						anchor.setCol1(imageCol);
		
						XSSFPicture pict = (XSSFPicture) drawing.createPicture(anchor, pictureureIdx);
						pict.resize();
						
						imageSize = anchor.getRow2() + 1 - imageRow;
						tempSheet.shiftRows(11, 28, imageSize);
						
						CellRangeAddress cellRangeAddress = new CellRangeAddress(imageRow - 1, anchor.getRow2() + 1, 0, anchor.getCol2() + 1);
						tempSheet.addMergedRegion(cellRangeAddress);
						settingStyleForCellHadMerge(0, cellRangeAddress, tempSheet, wb);
						
						// delete file
						FileUtilsQP.deleteQuietly(file);
						
					}
				}
				
				// add input bean
				List<InputBean> inputBeans = businessDesign.getLstInputBean();
				if (CollectionUtils.isNotEmpty(inputBeans)) {
					int indexRow = 13 + imageSize;
					int tempRow = indexRow;
					List<CellRangeAddress> cellRangeAddressList = new ArrayList<CellRangeAddress>();
					for (int i = 0; i < tempSheet.getNumMergedRegions(); i++) {
						CellRangeAddress cellRangeAddress = tempSheet.getMergedRegion(i);
						if (cellRangeAddress.getFirstRow() == tempRow) {
							cellRangeAddressList.add(cellRangeAddress);
						}
					}
					if(inputBeans.size() > 1){
						tempSheet.shiftRows(14, 28, inputBeans.size() - 1);
					}

					for(InputBean inputBean : inputBeans){
						
						Row row = tempSheet.getRow(indexRow);
						if( row == null){
							row = cloneRow(wb, tempSheet, tempRow, indexRow);
						}

						row.getCell(0).setCellValue(indexRow-tempRow+1);
						row.getCell(2).setCellValue(inputBean.getMessageString());
						row.getCell(19).setCellValue(inputBean.getLogicalDataType());
						row.getCell(36).setCellValue("");

						if(indexRow > tempRow) {
							for(CellRangeAddress cellRangeAddress : cellRangeAddressList ){
								CellRangeAddress newCellRangeAddress = new CellRangeAddress(indexRow,
										(indexRow + (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow())),
										cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastColumn());
								tempSheet.addMergedRegion(newCellRangeAddress);
							}
						}
						indexRow++;
					}
				}			
				
				// add output bean
				List<OutputBean> outputBeans = businessDesign.getLstOutputBean();
				if(outputBeans.size() > 0){
					int tempRow = 22 + Math.max(inputBeans.size() - 1, 0)  + imageSize;
					int indexRow = tempRow;
					List<CellRangeAddress> cellRangeAddressList = new ArrayList<CellRangeAddress>();
					for (int i = 0; i < tempSheet.getNumMergedRegions(); i++) {
						CellRangeAddress cellRangeAddress = tempSheet.getMergedRegion(i);
						if (cellRangeAddress.getFirstRow() == tempRow) {
							cellRangeAddressList.add(cellRangeAddress);
						}
					}
					if(outputBeans.size() > 1){
						tempSheet.shiftRows(tempRow + 1, tempRow + 6, outputBeans.size() - 1);
					}
					
					for(OutputBean outputBean : outputBeans){

						Row row = tempSheet.getRow(indexRow);
						if( row == null){
							row = cloneRow(wb, tempSheet, tempRow, indexRow);
						}

						row.getCell(0).setCellValue(indexRow-tempRow+1);
						row.getCell(2).setCellValue(outputBean.getOutputBeanName());
						row.getCell(19).setCellValue(outputBean.getLogicalDataType());
						row.getCell(36).setCellValue("");
						
						if(indexRow > tempRow) {
							for(CellRangeAddress cellRangeAddress : cellRangeAddressList ){
								CellRangeAddress newCellRangeAddress = new CellRangeAddress(indexRow,
										(indexRow + (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow())),
										cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastColumn());
								tempSheet.addMergedRegion(newCellRangeAddress);
							}
						}
						indexRow++;
					}
				}
				
				outputFilePath = item.getExcelFolder() + businessDesign.getBusinessLogicCode() + item.getDocumentItemTemplateName();
				FileOutputStream out = new FileOutputStream(outputFilePath);
				wb.write(out);

				IOUtils.closeQuietly(out);
				IOUtils.closeQuietly(wb);
			}			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			item.setDataLst(null);
		}
	}
	
	/**
	 * Process multiple language for title excel file
	 * 
	 * @param sheetTmp
	 */
	public static void processMultiLanguageForTemplate(Sheet sheetTmp) {

		for (int i = sheetTmp.getFirstRowNum(); i <= sheetTmp.getLastRowNum(); i++) {	 
			Row srcRow = sheetTmp.getRow(i);
			if (srcRow != null) {	 
				for (int j = srcRow.getFirstCellNum(); j <= srcRow.getLastCellNum() && j > -1; j++) {
					try {
						Cell cell = srcRow.getCell(j);
						if (cell != null && cell.getStringCellValue().length()>0) {
							String[] cellValue = cell.getStringCellValue().split(StringConstant.UNDERLINE);
							
							if(cellValue != null && cellValue.length == 1) {
								cell.setCellValue(MessageUtils.getMessage(StringUtils.trim(cellValue[0])));
							} else if (cellValue != null && cellValue.length > 1) {
								StringBuilder appStr = new StringBuilder();
								for (int k = 0; k < cellValue.length; k++) {
									appStr.append(MessageUtils.getMessage(cellValue[k]));
								}
								cell.setCellValue(appStr.length() > 0?appStr.toString():StringUtils.EMPTY);
							}
						}
					} catch (Exception e) {
						System.out.print("Error at cell : "+j);
					}
				}
			}
		}
	}

	/**
	 * Setting information of header
	 * 
	 * @param rowHeader
	 * @param positionColumnList -> List of position column header
	 * @param account 
	 * @param project 
	 */
	public static void headerGenerateInformation(Row rowHeader, List<Integer> positionColumnList) {

		int count = 0;
		DateFormat df = new SimpleDateFormat(currentAccounProfile.getDateFormat());
		for (Integer position : positionColumnList) {
			Cell cell = rowHeader.getCell(position);
			
			switch (count) {

			case HeaderCommon.SYSTEM_NAME:
				cell.setCellValue(currentProject.getProjectName());
				break;
			case HeaderCommon.CREATED_BY:
				cell.setCellValue(currentAccount.getUsername());
				break;
			case HeaderCommon.CREATED_ON:
				String createdOn = df.format(new Date()).toString();
				cell.setCellValue(createdOn);
				cell.setCellStyle(cell.getCellStyle());
				break;
			case HeaderCommon.UPDATED_BY:
				cell.setCellValue(StringUtils.EMPTY);
				cell.setCellStyle(cell.getCellStyle());
			case HeaderCommon.UPDATED_ON:
				cell.setCellValue(StringUtils.EMPTY);
				cell.setCellStyle(cell.getCellStyle());
				break;
			}
			count++;
		}
	}
	
	public static void processGenerateEDDocumentByModule(GenerateDocumentItem item) {
		
		switch (item.getDocumentItemType()) {
			case EDDocumentTypeByModule.SCREEN_DESIGN_DOC:
				processGenerateEDDocumentScreenDesignByModule(item);
				break;
			case EDDocumentTypeByModule.SCREEN_TRANSITION_DIAGRAM:
				processGenerateEDDocumentScreenTransitionByModule(item);
				break;
		}
	}
	
	private static void processGenerateEDDocumentScreenDesignByModule(GenerateDocumentItem item) {
		
		try {
			
			Workbook wb = WorkbookFactory.create(GenerateDocumentConst.class.getResourceAsStream(templateFolderName+item.getDocumentItemTemplateName()));
			
			Sheet layoutSheet = wb.getSheetAt(0);
			GenerateScreenDesignDocumentUtilsQP.processGenerateEDDocumentLayoutSheet(item, wb, layoutSheet);
			Sheet screenItemsSheet = wb.getSheetAt(1);
			GenerateScreenDesignDocumentUtilsQP.processGenerateEDDocumentScreenItems(item, wb, screenItemsSheet);
			Sheet inputCheckSheet = wb.getSheetAt(2);
			GenerateScreenDesignDocumentUtilsQP.processGenerateEDDocumentInputCheckSheet(item, wb, inputCheckSheet);
			Sheet screenStatusSheet = wb.getSheetAt(3);
			GenerateScreenDesignDocumentUtilsQP.processGenerateEDDocumentScreenStatusSheet(item, wb, screenStatusSheet);
			
			String outputFilePath = item.getExcelFolder() + item.getDocumentItemFileName();
			FileOutputStream out = new FileOutputStream(outputFilePath);
			wb.write(out);
			out.close();
			wb.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void processGenerateEDDocumentScreenTransitionByModule(GenerateDocumentItem item) {
		
		try {
			
			Workbook wb = WorkbookFactory.create(GenerateDocumentConst.class.getResourceAsStream(templateFolderName+item.getDocumentItemTemplateName()));
			
			Sheet sheet = wb.getSheetAt(0);
			// Process multiple language of template sheet		
			processMultiLanguageForTemplate(sheet);
			
			Module module = (Module) item.getData();
			// Generate data for header
			Row rowHeader = sheet.getRow(1);
			Cell businessNameCell = rowHeader.getCell(20);
			
			businessNameCell.setCellValue(module.getBusinessTypeName());
			GenerateDocumentUtilsQP.headerGenerateInformation (rowHeader, Arrays.asList(11,38,44,50,56));
			
			int remarkRow = 9;
			// call service capture image
			String imagePath = item.getCapturePath();
			if( imagePath != null){
				File file = new File(imagePath);
				if( file.exists() ) {
					InputStream inputStream = new FileInputStream(imagePath);
					byte[] imageBytes = IOUtils.toByteArray(inputStream);
					int pictureureIdx = wb.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
	
					inputStream.close();
					CreationHelper helper = wb.getCreationHelper();
					Drawing drawing = sheet.createDrawingPatriarch();
					ClientAnchor anchor = helper.createClientAnchor();
	
					int imageRow = 5;
					int imageCol = 1;
					
					anchor.setRow1(imageRow);
					anchor.setCol1(imageCol);
	
					XSSFPicture pict = (XSSFPicture) drawing.createPicture(anchor, pictureureIdx);
					pict.resize();
					
					sheet.shiftRows(7, 10, anchor.getRow2() + 1 - 5);
					remarkRow += anchor.getRow2() + 1 - 5;
					
					CellRangeAddress cellRangeAddress = new CellRangeAddress(imageRow - 1, anchor.getRow2() + 1, 0, anchor.getCol2() + 1);
					sheet.addMergedRegion(cellRangeAddress);
					settingStyleForCellHadMerge(0, cellRangeAddress, sheet, wb);
					
					// delete file
					FileUtilsQP.deleteQuietly(file);
					
				}
			}
			// set remark
			
			Cell cell = sheet.getRow(remarkRow).getCell(1);
			cell.setCellValue(module.getRemark());
			
			String outputFilePath = item.getExcelFolder() + module.getModuleCode() + item.getDocumentItemTemplateName();
			FileOutputStream out = new FileOutputStream(outputFilePath);
			wb.write(out);
			out.close();
			wb.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void processGenerateRDDocumentByModule(GenerateDocumentItem item) {
		
	}

	public static void processGenerateEDDocumentByProject(GenerateDocumentItem item) throws Exception {
		switch (item.getDocumentItemType()) {
		
		case EDDocumentTypeByProject.VIEW_LIST:
			processGenerateEDDocumentViewListByProject(item);
			break;
		case EDDocumentTypeByProject.VIEW_DESIGN:
			processGenerateEDDocumentViewDesignByProject(item);
			break;
		case EDDocumentTypeByProject.MESSAGE_DESIGN:
			processGenerateEDDocumentMessageDesignByProjectNew(item);
			break;
		case EDDocumentTypeByProject.SCREEN_TRANSITION_DIAGRAM:
			processGenerateEDDocumentScreenTransitionByProject(item);
			break;	
		case EDDocumentTypeByProject.CODE_LIST:
			//KhanhTH
			processGenerateEDDocumentCodelistByProject(item);
			break;
		}
	}

	private static void processGenerateEDDocumentViewListByProject(GenerateDocumentItem item) {
		try {
			String outputFilePath = item.getExcelFolder() + item.getDocumentItemTemplateName();
			Workbook wb = WorkbookFactory.create(GenerateDocumentConst.class.getResourceAsStream(templateFolderName + item.getDocumentItemTemplateName()));
			Sheet sheet = wb.getSheetAt(0);

			// Process multiple language of template sheet		
			processMultiLanguageForTemplate(sheet);
			
			// Generate data for header
			headerGenerateInformation(sheet.getRow(1), Arrays.asList(11,42,48,54,60));
			int rowIndex = 4;
			CellStyle defaultCellStyle = wb.createCellStyle();
			defaultCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			defaultCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			
			if (CollectionUtils.isNotEmpty(item.getDataLst())) {
				List<?> sqlDesignLst = item.getDataLst();
			
				for (Object obj : sqlDesignLst) {
					SqlDesign viewDesign = (SqlDesign) obj;
					Row row = sheet.createRow(rowIndex);
					CellRangeAddress cellRange = null;
					
					Cell idCell = row.createCell(0);
					idCell.setCellStyle(defaultCellStyle);
					cellRange = new CellRangeAddress(rowIndex,rowIndex, 0, 10);
					idCell.setCellValue("V"+StringUtils.leftPad(String.valueOf(viewDesign.getSqlDesignId()),16,'0'));
					sheet.addMergedRegion(cellRange);
					settingStyleForCellHadMerge(0, cellRange, sheet, wb);
					
					Cell viewNameCell = row.createCell(11);
					viewNameCell.setCellStyle(defaultCellStyle);
					cellRange = new CellRangeAddress(rowIndex,rowIndex, 11, 19);
					viewNameCell.setCellValue(viewDesign.getSqlDesignCode());
					sheet.addMergedRegion(cellRange);
					settingStyleForCellHadMerge(0, cellRange, sheet, wb);
					 
					Cell logicalViewNameCell = row.createCell(20);
					logicalViewNameCell.setCellStyle(defaultCellStyle);
					cellRange = new CellRangeAddress(rowIndex,rowIndex, 20, 28);
					logicalViewNameCell.setCellValue(viewDesign.getSqlDesignName());
					sheet.addMergedRegion(cellRange);
					settingStyleForCellHadMerge(0, cellRange, sheet, wb);
					
					Cell viewTypeCell = row.createCell(29);
					viewTypeCell.setCellStyle(defaultCellStyle);
					cellRange = new CellRangeAddress(rowIndex,rowIndex, 29, 32);
					viewTypeCell.setCellValue("Read-only");
					sheet.addMergedRegion(cellRange);
					settingStyleForCellHadMerge(0, cellRange, sheet, wb);
					
					Cell viewDescriptionCell = row.createCell(33);
					viewDescriptionCell.setCellStyle(defaultCellStyle);
					cellRange = new CellRangeAddress(rowIndex,rowIndex, 33, 65);
					viewDescriptionCell.setCellValue(viewDesign.getRemark());
					sheet.addMergedRegion(cellRange);
					settingStyleForCellHadMerge(0, cellRange, sheet, wb);
					
					rowIndex++;
				}
			}

			FileOutputStream out = new FileOutputStream(outputFilePath);
			wb.write(out);
			out.close();
			wb.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			item.setDataLst(null);
		}
	}

	private static void processGenerateEDDocumentViewDesignByProject(GenerateDocumentItem item) {
		try {
			String outputFilePath = item.getExcelFolder() + item.getDocumentItemTemplateName();
			Workbook wb = WorkbookFactory.create(GenerateDocumentConst.class.getResourceAsStream(templateFolderName + item.getDocumentItemTemplateName()));
			Sheet templateSheet = wb.getSheetAt(0);
			// Process multiple language of template sheet		
			processMultiLanguageForTemplate(templateSheet);
			headerGenerateInformation(templateSheet.getRow(1), Arrays.asList(11,38,44,50,56));
			
			CellStyle defaultCellStyle = wb.createCellStyle();
			defaultCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			defaultCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			defaultCellStyle.setFillBackgroundColor(IndexedColors.WHITE.index);
			
			CellStyle indexCellStyle = wb.createCellStyle();
			indexCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			indexCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			indexCellStyle.setFillBackgroundColor(IndexedColors.WHITE.index);
			
			if (CollectionUtils.isNotEmpty(item.getDataLst())) {
				List<?> sqlDesignLst = item.getDataLst();
				
				for (Object obj : sqlDesignLst) {
					SqlDesign viewDesign = (SqlDesign) obj;
					Sheet sheet = wb.cloneSheet(0);
					wb.setSheetName(wb.getSheetIndex(sheet), viewDesign.getSqlDesignCode());
					CellRangeAddress cellRange = null;
					Row row = null;
					row = sheet.getRow(3);
					Cell idCell = row.getCell(11);
					idCell.setCellValue("V"+StringUtils.leftPad(String.valueOf(viewDesign.getSqlDesignId()),16,'0'));
					
					row = sheet.getRow(4);
					Cell viewNameCell = row.getCell(11);
					viewNameCell.setCellValue(viewDesign.getSqlDesignName());

					row = sheet.getRow(5);
					Cell logicalViewNameCell = row.getCell(11);
					logicalViewNameCell.setCellValue(viewDesign.getSqlDesignCode());

					row = sheet.getRow(6);
					Cell logicalTableNameCell = row.getCell(11);
					logicalTableNameCell.setCellValue(getTableName(viewDesign,true));
					
					row = sheet.getRow(7);
					Cell tableNameCell = row.getCell(11);
					tableNameCell.setCellValue(getTableName(viewDesign,false));
					
					row = sheet.getRow(8);
					Cell viewDescriptionCell = row.getCell(11);
					viewDescriptionCell.setCellValue(viewDesign.getRemark());
					
					row = sheet.getRow(14);
					Cell sqlTextCell = row.getCell(0);
					sqlTextCell.setCellValue(viewDesign.getSqlText());
					
					if(viewDesign.getSqlDesignResults().size()>0){
						sheet.shiftRows(13, 22, viewDesign.getSqlDesignResults().size());
					}
					
					int startIndex = 13;
					for(SqlDesignResult sqlDesignResult:viewDesign.getSqlDesignResults()){
						row = sheet.createRow(startIndex);
						Cell itemNoCell = row.createCell(0);
						itemNoCell.setCellValue(sqlDesignResult.getItemSeqNo());
						itemNoCell.setCellStyle(indexCellStyle);
						cellRange = new CellRangeAddress(startIndex,startIndex, 0, 2);
						sheet.addMergedRegion(cellRange);
						settingStyleForCellHadMerge(0, cellRange, sheet, wb);
						
						Cell columnCodeCell = row.createCell(3);
						columnCodeCell.setCellValue(sqlDesignResult.getColumnCode());
						columnCodeCell.setCellStyle(defaultCellStyle);
						cellRange = new CellRangeAddress(startIndex,startIndex, 3, 19);
						sheet.addMergedRegion(cellRange);
						settingStyleForCellHadMerge(0, cellRange, sheet, wb);
						
						Cell columnNameCell = row.createCell(20);
						columnNameCell.setCellValue(sqlDesignResult.getColumnName());
						columnNameCell.setCellStyle(defaultCellStyle);
						cellRange = new CellRangeAddress(startIndex,startIndex, 20, 37);
						sheet.addMergedRegion(cellRange);
						settingStyleForCellHadMerge(0, cellRange, sheet, wb);
						
						Cell tableColumnCodeCell = row.createCell(38);
						tableColumnCodeCell.setCellValue(sqlDesignResult.getTableCode()+"."+sqlDesignResult.getColumnCode());
						tableColumnCodeCell.setCellStyle(defaultCellStyle);
						cellRange = new CellRangeAddress(startIndex,startIndex, 38, 49);
						sheet.addMergedRegion(cellRange);
						settingStyleForCellHadMerge(0, cellRange, sheet, wb);
						
						Cell tableColumnNameCell = row.createCell(50);
						tableColumnNameCell.setCellValue(sqlDesignResult.getTableName()+"."+sqlDesignResult.getColumnName());
						tableColumnNameCell.setCellStyle(defaultCellStyle);
						cellRange = new CellRangeAddress(startIndex,startIndex, 50, 61);
						sheet.addMergedRegion(cellRange);
						settingStyleForCellHadMerge(0, cellRange, sheet, wb);
						
						startIndex++;
					}
				}
				wb.removeSheetAt(0);
			}

			FileOutputStream out = new FileOutputStream(outputFilePath);
			wb.write(out);
			out.close();
			wb.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			item.setDataLst(null);
		}
	}
	
	/*private static void processGenerateEDDocumentMessageDesignByProject(GenerateDocumentItem item) {
		
		try {						
			String outputFilePath = item.getExcelFolder() + item.getDocumentItemTemplateName();
			// Read template file
			InputStream is = GenerateDocumentConst.class.getResourceAsStream(templateFolderName + item.getDocumentItemTemplateName());
			Workbook wb = WorkbookFactory.create(is);
			
			// generate data source
			Sheet sheet0 = wb.getSheetAt(0);
			int firstRow = 4;
			String langFormula = "=\''Data Source\''!$O${0}:$O${1}";
			langFormula = MessageFormat.format(langFormula, String.valueOf(firstRow + 1), String.valueOf(firstRow + item.getLanguageDesignLst().size()));
			for(LanguageDesign languageDesign: item.getLanguageDesignLst()){
				Row row = sheet0.getRow(firstRow);
				if( row == null || row.getCell(13) == null || row.getCell(14) == null) {
					row = cloneRowByColumn(wb, sheet0, firstRow - 1, firstRow, 13, 14);	
				}
				row.getCell(13).setCellValue(languageDesign.getLanguageId());
				row.getCell(14).setCellValue(languageDesign.getLanguageName());
				firstRow++;
			}
			
			
			// generate content
			Sheet sheet = wb.getSheetAt(1);
			
			// Process multiple language of template sheet
			processMultiLanguageForTemplate(sheet);
			
			// Setting header information
			headerGenerateInformation(sheet.getRow(1), Arrays.asList(11,38,44,50,57));
			int indexRow = 4;
			int tempRow = 4;
			
			List<?> dataValidations = sheet.getDataValidations();
			DataValidationHelper dvHelper = sheet.getDataValidationHelper();			
			if(CollectionUtils.isNotEmpty(item.getDataLst())) {
				List<CellRangeAddress> cellRangeAddressList = new ArrayList<CellRangeAddress>();
				for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
					CellRangeAddress cellRangeAddress = sheet.getMergedRegion(i);
					if (cellRangeAddress.getFirstRow() == indexRow) {
						cellRangeAddressList.add(cellRangeAddress);
					}
				}
				for(Object object : dataValidations){
					DataValidation dataValidation = (DataValidation) object;
					int firstCol = dataValidation.getRegions().getCellRangeAddress(0).getFirstColumn();
					int lastCol = dataValidation.getRegions().getCellRangeAddress(0).getLastColumn();
					CellRangeAddressList addressList = new CellRangeAddressList(indexRow, indexRow + item.getDataLst().size() - 1, firstCol, lastCol);
					DataValidationConstraint constraint = dataValidation.getValidationConstraint();
					if( firstCol == 62){
						constraint.setFormula1(langFormula);
					}
					DataValidation newDataValidation = dvHelper.createValidation(constraint, addressList );
					sheet.addValidationData(newDataValidation);
				}
				for (Object obj : item.getDataLst()) {
					MessageDesign messageDesign = (MessageDesign) obj;
					
					Row row = sheet.getRow(indexRow);
					if( row == null){
						row =  cloneRow(wb, sheet, tempRow, indexRow);
					}
					
					// Item No.
					Cell cell = row.getCell(0);
					cell.setCellValue(indexRow - tempRow + 1);
					
					// ID
					cell = row.getCell(2);
					cell.setCellValue(messageDesign.getMessageCode());
					
					// Source
					cell = row.getCell(14);
					if(GenerateDocumentConst.LABEL.equals(messageDesign.getMessageType())){
						cell.setCellValue(MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0065));
					}
					else {
						cell.setCellValue(MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0066));
					}
					
					// Output location
					cell = row.getCell(19);
					cell.setCellValue(MessageUtils.getMessage("sc.businesslogicdesign.0134"));
					
					// Error level
					cell = row.getCell(24);
					cell.setCellValue(messageDesign.getMessageTypeName());
					
					// Message string
					cell = row.getCell(29);
					cell.setCellValue(messageDesign.getFromMessageString());
					
					// Description
					cell = row.getCell(50);
					cell.setCellValue(messageDesign.getRemark());
					
					// Language
					cell = row.getCell(62);
					cell.setCellValue(messageDesign.getLanguageName());
					
					// add merge cell and data validation
					if(indexRow > tempRow ) {
						for(CellRangeAddress cellRangeAddress : cellRangeAddressList ){
							CellRangeAddress newCellRangeAddress = new CellRangeAddress(indexRow,
									(indexRow + (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow())),
									cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastColumn());
							sheet.addMergedRegion(newCellRangeAddress);
						}
					} 
					
					indexRow++;
				}
			}
			FileOutputStream out = new FileOutputStream(outputFilePath);
			wb.write(out);
			out.close();
			wb.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			item.setDataLst(null);
		}
	}
	*/
	private static void processGenerateEDDocumentScreenTransitionByProject(GenerateDocumentItem item) {

		try {
			String outputFilePath = item.getExcelFolder() + item.getDocumentItemTemplateName();
			// Read template file
			InputStream is = GenerateDocumentConst.class.getResourceAsStream(templateFolderName + item.getDocumentItemTemplateName());
			Workbook wb = (Workbook) WorkbookFactory.create(is);
			Sheet sheet = wb.getSheetAt(0);
			
			// Process multiple language of template sheet
			processMultiLanguageForTemplate(sheet);
			
			headerGenerateInformation(sheet.getRow(1), Arrays.asList(11,38,44,50,56));
			
			
			if(CollectionUtils.isNotEmpty(item.getDataLst())) {
				for (Object obj : item.getDataLst()) {
					GenerateDocumentItem moduleItem = (GenerateDocumentItem) obj;
					if(moduleItem.getId() != null){   					
						wb.cloneSheet(0);
					}
				}
				int sheetIndex = 0;
				for (Object obj : item.getDataLst()) {
					sheet = wb.getSheetAt(sheetIndex);
					GenerateDocumentItem moduleItem = (GenerateDocumentItem) obj;
					Module module = (Module) moduleItem.getData();
					int remarkRow = 9;
					String imagePath = moduleItem.getCapturePath();
					if( imagePath != null){
						File file = new File(imagePath);
						if( file.exists() ) {
							InputStream inputStream = new FileInputStream(imagePath);
							byte[] imageBytes = IOUtils.toByteArray(inputStream);
							int pictureureIdx = wb.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
			
							inputStream.close();
							CreationHelper helper = wb.getCreationHelper();
							Drawing drawing = sheet.createDrawingPatriarch();
							ClientAnchor anchor = helper.createClientAnchor();
			
							int imageRow = 5;
							int imageCol = 1;
							
							anchor.setRow1(imageRow);
							anchor.setCol1(imageCol);
			
							XSSFPicture pict = (XSSFPicture) drawing.createPicture(anchor, pictureureIdx);
							pict.resize();
							
							sheet.shiftRows(7, 10, anchor.getRow2() + 1 - 5);
							remarkRow += anchor.getRow2() + 1 - 5;
							
							CellRangeAddress cellRangeAddress = new CellRangeAddress(imageRow - 1, anchor.getRow2() + 1, 0, anchor.getCol2() + 1);
							sheet.addMergedRegion(cellRangeAddress);
							settingStyleForCellHadMerge(0, cellRangeAddress, sheet, wb);
							
							// delete file
							FileUtilsQP.deleteQuietly(file);
							
						}
					}
					// set remark					
					Cell cell = sheet.getRow(remarkRow).getCell(1);
					if( moduleItem.getId() != null ){
						cell.setCellValue(module.getRemark());
						wb.setSheetName(sheetIndex, module.getModuleName());
					}
					sheetIndex++;
				}
			}
			
			FileOutputStream out = new FileOutputStream(outputFilePath);
			wb.write(out);
			out.close();
			wb.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			item.setDataLst(null);
		}
	}
	
	private static String getTableName(SqlDesign sqlDesign,Boolean isLogical) {
		String  name= "";
		int i=0;
		for (SqlDesignTable sqlDesignTable:sqlDesign.getSqlDesignTables()) {
			if(StringUtils.isNotBlank(name)){
				name += ", ";
			}
			if(i==0){
				name += isLogical?sqlDesignTable.getTableName():sqlDesignTable.getTableCode();
				if(StringUtils.isNotBlank(sqlDesignTable.getJoinTableName())){
					name += ", ";
					name += isLogical?sqlDesignTable.getJoinTableName():sqlDesignTable.getJoinTableCode();
				}
			} else {
				name += isLogical?sqlDesignTable.getJoinTableName():sqlDesignTable.getJoinTableCode();
			}
			i++;
		}
		
		return name;
	}
	private static void settingStyleForCellHadMerge(int style,
			CellRangeAddress region, Sheet sheet, Workbook wb) {
		
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
	
	public static String generateDatetimeSystem() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();

		return dateFormat.format(date);
	}
	
	public static boolean isMultipleDocumentType(GenerateDocumentItem item) {
		boolean flg = false;

		
		if(GenerateDocumentConst.GENERATE_PROJECT.equals(item.getDocumentItemScopeItemType())
				&& GenerateDocumentConst.GENERATE_PROJECT_RD.equals(item.getDocumentItemParenItemType())) { 


		} else if(GenerateDocumentConst.GENERATE_PROJECT.equals(item.getDocumentItemScopeItemType())
				&& GenerateDocumentConst.GENERATE_PROJECT_ED.equals(item.getDocumentItemParenItemType())) { 


		} else if(GenerateDocumentConst.GENERATE_PROJECT.equals(item.getDocumentItemScopeItemType())
				&& GenerateDocumentConst.GENERATE_PROJECT_ID.equals(item.getDocumentItemParenItemType())) { 


		} else if(GenerateDocumentConst.GENERATE_MODULE.equals(item.getDocumentItemScopeItemType())
				&& GenerateDocumentConst.GENERATE_MODULE_RD.equals(item.getDocumentItemParenItemType())) { 


		}  else if(GenerateDocumentConst.GENERATE_MODULE.equals(item.getDocumentItemScopeItemType())
				&& GenerateDocumentConst.GENERATE_MODULE_ED.equals(item.getDocumentItemParenItemType())) { 
			
			switch (item.getDocumentItemType()) {
			case GenerateDocumentConst.EDDocumentTypeByModule.SCREEN_DESIGN_DOC:
				flg = true;
				break;

			}
		} else if(GenerateDocumentConst.GENERATE_MODULE.equals(item.getDocumentItemScopeItemType())
			&& GenerateDocumentConst.GENERATE_MODULE_ID.equals(item.getDocumentItemParenItemType())) { 

		}
		
		return flg;
	}
	
	private static void processGenerateEDDocumentCodelistByProject(GenerateDocumentItem item) {
		String outputFilePath = item.getExcelFolder() + item.getDocumentItemTemplateName();
		// Keep 100 rows in memory, exceeding rows will be flushed to disk
		SXSSFWorkbook workbook = new SXSSFWorkbook(100);
		// Create sheet Data Source
		workbook.createSheet("Data Source");
		
		int numberOfCodelist = item.getDataLst().size();
		List<Sheet> listCodelistSheet = new ArrayList<Sheet>();
		// Create detail
		@SuppressWarnings("unchecked")
		Map<String, List<CodeListDetail>> mapData = (Map<String, List<CodeListDetail>>) item.getData();
		
		// Create cell style
		Font font = workbook.createFont();
		font.setBold(true);
		
		CellStyle headerCellStyleRow1 = workbook.createCellStyle();
		headerCellStyleRow1.setBorderLeft(CellStyle.BORDER_THIN);
		headerCellStyleRow1.setBorderRight(CellStyle.BORDER_THIN);
		headerCellStyleRow1.setBorderTop(CellStyle.BORDER_THIN);
		headerCellStyleRow1.setBorderBottom(CellStyle.BORDER_THIN);
		headerCellStyleRow1.setAlignment(CellStyle.ALIGN_CENTER);
		headerCellStyleRow1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headerCellStyleRow1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		headerCellStyleRow1.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerCellStyleRow1.setFont(font);
		headerCellStyleRow1.setWrapText(true);
		
		CellStyle headerCellStyleRow2 = workbook.createCellStyle();
		headerCellStyleRow2.setBorderLeft(CellStyle.BORDER_THIN);
		headerCellStyleRow2.setBorderRight(CellStyle.BORDER_THIN);
		headerCellStyleRow2.setBorderTop(CellStyle.BORDER_THIN);
		headerCellStyleRow2.setBorderBottom(CellStyle.BORDER_THIN);
		headerCellStyleRow2.setAlignment(CellStyle.ALIGN_CENTER);
		headerCellStyleRow2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
		CellStyle summaryCellStyle1 = workbook.createCellStyle();
		summaryCellStyle1.setBorderLeft(CellStyle.BORDER_THIN);
		summaryCellStyle1.setBorderRight(CellStyle.BORDER_THIN);
		summaryCellStyle1.setBorderTop(CellStyle.BORDER_THIN);
		summaryCellStyle1.setBorderBottom(CellStyle.BORDER_THIN);
		summaryCellStyle1.setAlignment(CellStyle.ALIGN_LEFT);
		summaryCellStyle1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		summaryCellStyle1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		summaryCellStyle1.setFillPattern(CellStyle.SOLID_FOREGROUND);
		summaryCellStyle1.setFont(font);
		
		CellStyle summaryCellStyle2 = workbook.createCellStyle();
		summaryCellStyle2.setBorderLeft(CellStyle.BORDER_THIN);
		summaryCellStyle2.setBorderRight(CellStyle.BORDER_THIN);
		summaryCellStyle2.setBorderTop(CellStyle.BORDER_THIN);
		summaryCellStyle2.setBorderBottom(CellStyle.BORDER_THIN);
		summaryCellStyle2.setAlignment(CellStyle.ALIGN_LEFT);
		summaryCellStyle2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
		for(int i = 0; i < numberOfCodelist; i++) {
			CodeList currentCodelist = (CodeList) item.getDataLst().get(i);
			Sheet tempSheet = workbook.createSheet(currentCodelist.getCodeListName());
			listCodelistSheet.add(tempSheet);
		}
		
		workbook.setActiveSheet(1);
		
		// Create merged cell
		CellRangeAddress documentName = new CellRangeAddress(0, 1, 0, 10);
		CellRangeAddress systemNameLabel = new CellRangeAddress(0, 0, 11, 19);
		CellRangeAddress systemNameValue = new CellRangeAddress(1, 1, 11, 19);
		CellRangeAddress emptyLabel1 = new CellRangeAddress(0, 0, 20, 28);
		CellRangeAddress emptyValue1 = new CellRangeAddress(1, 1, 20, 28);
		CellRangeAddress emptyLabel2 = new CellRangeAddress(0, 0, 29, 41);
		CellRangeAddress emptyValue2 = new CellRangeAddress(1, 1, 29, 41);
		CellRangeAddress createdByLabel = new CellRangeAddress(0, 0, 42, 47);
		CellRangeAddress createdByValue = new CellRangeAddress(1, 1, 42, 47);
		CellRangeAddress createdDateLabel = new CellRangeAddress(0, 0, 48, 53);
		CellRangeAddress createdDateValue = new CellRangeAddress(1, 1, 48, 53);
		CellRangeAddress updatedByLabel = new CellRangeAddress(0, 0, 54, 59);
		CellRangeAddress updatedByValue = new CellRangeAddress(1, 1, 54, 59);
		CellRangeAddress updatedDateLabel = new CellRangeAddress(0, 0, 60, 65);
		CellRangeAddress updatedDateValue = new CellRangeAddress(1, 1, 60, 65);
		
		CellRangeAddress codelistNameLabel = new CellRangeAddress(3, 3, 0, 12);
		CellRangeAddress codelistNameValue = new CellRangeAddress(3, 3, 13, 65);
		CellRangeAddress codelistCodeLabel = new CellRangeAddress(4, 4, 0, 12);
		CellRangeAddress codelistCodeValue = new CellRangeAddress(4, 4, 13, 65);
		CellRangeAddress valuesOnlyLabel = new CellRangeAddress(5, 5, 0, 12);
		CellRangeAddress valuesOnlyValue = new CellRangeAddress(5, 5, 13, 65);
		CellRangeAddress moduleNameLabel = new CellRangeAddress(6, 6, 0, 12);
		CellRangeAddress moduleNameValue = new CellRangeAddress(6, 6, 13, 65);
		CellRangeAddress descriptionLabel = new CellRangeAddress(7, 7, 0, 12);
		CellRangeAddress descriptionValue = new CellRangeAddress(7, 7, 13, 65);
		
		CellRangeAddress itemNo = new CellRangeAddress(9, 10, 0, 1);
		CellRangeAddress key = new CellRangeAddress(9, 10, 2, 12);
		CellRangeAddress value = new CellRangeAddress(9, 10, 13, 30);
		CellRangeAddress value1 = new CellRangeAddress(9, 10, 31, 37);
		CellRangeAddress value2 = new CellRangeAddress(9, 10, 38, 44);
		CellRangeAddress value3 = new CellRangeAddress(9, 10, 45, 51);
		CellRangeAddress value4 = new CellRangeAddress(9, 10, 52, 58);
		CellRangeAddress value5 = new CellRangeAddress(9, 10, 59, 65);
		
		for(int i = 0; i < listCodelistSheet.size(); i++) {
			CodeList currentCodelist = (CodeList) item.getDataLst().get(i);
			Sheet tempSheet = listCodelistSheet.get(i);
			tempSheet.addMergedRegion(documentName);
			tempSheet.addMergedRegion(systemNameLabel);
			tempSheet.addMergedRegion(systemNameValue);
			tempSheet.addMergedRegion(emptyLabel1);
			tempSheet.addMergedRegion(emptyValue1);
			tempSheet.addMergedRegion(emptyLabel2);
			tempSheet.addMergedRegion(emptyValue2);
			tempSheet.addMergedRegion(createdByLabel);
			tempSheet.addMergedRegion(createdByValue);
			tempSheet.addMergedRegion(createdDateLabel);
			tempSheet.addMergedRegion(createdDateValue);
			tempSheet.addMergedRegion(updatedByLabel);
			tempSheet.addMergedRegion(updatedByValue);
			tempSheet.addMergedRegion(updatedDateLabel);
			tempSheet.addMergedRegion(updatedDateValue);
			
			tempSheet.addMergedRegion(codelistNameLabel);
			tempSheet.addMergedRegion(codelistNameValue);
			tempSheet.addMergedRegion(codelistCodeLabel);
			tempSheet.addMergedRegion(codelistCodeValue);
			tempSheet.addMergedRegion(valuesOnlyLabel);
			tempSheet.addMergedRegion(valuesOnlyValue);
			tempSheet.addMergedRegion(moduleNameLabel);
			tempSheet.addMergedRegion(moduleNameValue);
			tempSheet.addMergedRegion(descriptionLabel);
			tempSheet.addMergedRegion(descriptionValue);
			
			tempSheet.addMergedRegion(itemNo);
			tempSheet.addMergedRegion(key);
			tempSheet.addMergedRegion(value);
			tempSheet.addMergedRegion(value1);
			tempSheet.addMergedRegion(value2);
			tempSheet.addMergedRegion(value3);
			tempSheet.addMergedRegion(value4);
			tempSheet.addMergedRegion(value5);
			
			Row row1 = tempSheet.createRow(0);
			Row row2 = tempSheet.createRow(1);
			Row row4 = tempSheet.createRow(3);
			Row row5 = tempSheet.createRow(4);
			Row row6 = tempSheet.createRow(5);
			Row row7 = tempSheet.createRow(6);
			Row row8 = tempSheet.createRow(7);
			Row row10 = tempSheet.createRow(9);
			Row row11 = tempSheet.createRow(10);
			
			for(int j = 0; j < 66; j++) {
				tempSheet.setColumnWidth(j, 3*256);
				
				Cell tempCellRow1 = row1.createCell(j);
				tempCellRow1.setCellStyle(headerCellStyleRow1);
				
				Cell tempCellRow2 = row2.createCell(j);
				tempCellRow2.setCellStyle(headerCellStyleRow2);
				
				Cell tempCellRow10 = row10.createCell(j);
				tempCellRow10.setCellStyle(headerCellStyleRow1);

				Cell tempCellRow11 = row11.createCell(j);
				tempCellRow11.setCellStyle(headerCellStyleRow1);
				
				Cell tempCellRow4 = row4.createCell(j);
				Cell tempCellRow5 = row5.createCell(j);
				Cell tempCellRow6 = row6.createCell(j);
				Cell tempCellRow7 = row7.createCell(j);
				Cell tempCellRow8 = row8.createCell(j);
				
				if(j < 13) {
					tempCellRow4.setCellStyle(summaryCellStyle1);
					tempCellRow5.setCellStyle(summaryCellStyle1);
					tempCellRow6.setCellStyle(summaryCellStyle1);
					tempCellRow7.setCellStyle(summaryCellStyle1);
					tempCellRow8.setCellStyle(summaryCellStyle1);
				} else {
					tempCellRow4.setCellStyle(summaryCellStyle2);
					tempCellRow5.setCellStyle(summaryCellStyle2);
					tempCellRow6.setCellStyle(summaryCellStyle2);
					tempCellRow7.setCellStyle(summaryCellStyle2);
					tempCellRow8.setCellStyle(summaryCellStyle2);
				}
				
				// Insert value to cell
				switch(j) {
				case 0:
					tempCellRow1.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.CodeList.TITLE));
					tempCellRow4.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.CodeList.CODELIST_NAME));
					tempCellRow5.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.CodeList.CODELIST_CODE));
					tempCellRow6.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.CodeList.VALUE_ONLY));
					tempCellRow7.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.CodeList.MODULE_NAME));
					tempCellRow8.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.CodeList.DESCRIPTION));
					tempCellRow10.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.TableDesign.ITEM_NO));
					break;
				case 2:
					tempCellRow10.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.CodeList.KEY));
					break;
				case 11:
					tempCellRow1.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.Header.SYSTEM_NAME));
					tempCellRow2.setCellValue(currentProject.getProjectName());
					break;
				case 13:
					tempCellRow4.setCellValue(currentCodelist.getCodeListName() != null ? currentCodelist.getCodeListName() : "");
					tempCellRow5.setCellValue(currentCodelist.getCodeListCode() != null ? currentCodelist.getCodeListCode() : "");
					tempCellRow6.setCellValue(currentCodelist.getIsOptionValude() == 1 ? "Yes" : "No");
					tempCellRow7.setCellValue(currentCodelist.getModuleIdAutocomplete() != null ? currentCodelist.getModuleIdAutocomplete() : "");
					tempCellRow8.setCellValue(currentCodelist.getRemark() != null ? currentCodelist.getRemark() : "");
					tempCellRow10.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.CodeList.VALUE));
					break;
				case 31:
					tempCellRow10.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.CodeList.VALUE1));
					break;
				case 38:
					tempCellRow10.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.CodeList.VALUE2));
					break;
				case 42:
					tempCellRow1.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.Header.CREATE_BY));
					tempCellRow2.setCellValue(currentAccount.getUsername());
					break;
				case 45:
					tempCellRow10.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.CodeList.VALUE3));
					break;
				case 48:
					tempCellRow1.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.Header.CREATE_DATE));
					DateFormat df = new SimpleDateFormat(currentAccounProfile.getDateFormat());
					tempCellRow2.setCellValue(df.format(new Date()).toString());
					break;
				case 52:
					tempCellRow10.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.CodeList.VALUE4));
					break;
				case 54:
					tempCellRow1.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.Header.UPDATE_BY));
					break;
				case 59:
					tempCellRow10.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.CodeList.VALUE5));
					break;
				case 60:
					tempCellRow1.setCellValue(MessageUtils.getMessage(GenerateDocumentConst.Header.UPDATE_DATE));
					break;
				}
			}
			
			for(String currentSheet : mapData.keySet()) {
				if(currentSheet.equals(tempSheet.getSheetName())) {
					List<CodeListDetail> currentCodelistDetail = mapData.get(currentSheet);
					for(int j = 0; j < currentCodelistDetail.size(); j++) {
						CellRangeAddress tempItemNo = new CellRangeAddress(j + 11, j + 11, 0, 1);
						CellRangeAddress tempKey = new CellRangeAddress(j + 11, j + 11, 2, 12);
						CellRangeAddress tempValue = new CellRangeAddress(j + 11, j + 11, 13, 30);
						CellRangeAddress tempValue1 = new CellRangeAddress(j + 11, j + 11, 31, 37);
						CellRangeAddress tempValue2 = new CellRangeAddress(j + 11, j + 11, 38, 44);
						CellRangeAddress tempValue3 = new CellRangeAddress(j + 11, j + 11, 45, 51);
						CellRangeAddress tempValue4 = new CellRangeAddress(j + 11, j + 11, 52, 58);
						CellRangeAddress tempValue5 = new CellRangeAddress(j + 11, j + 11, 59, 65);
						
						tempSheet.addMergedRegion(tempItemNo);
						tempSheet.addMergedRegion(tempKey);
						tempSheet.addMergedRegion(tempValue);
						tempSheet.addMergedRegion(tempValue1);
						tempSheet.addMergedRegion(tempValue2);
						tempSheet.addMergedRegion(tempValue3);
						tempSheet.addMergedRegion(tempValue4);
						tempSheet.addMergedRegion(tempValue5);
						
						Row tempRow = tempSheet.createRow(j + 11);
						for(int k = 0; k < 66; k++) {
							Cell tempCell = tempRow.createCell(k);
							tempCell.setCellStyle(summaryCellStyle2);
							
							switch(k) {
							case 0:
								tempCell.setCellValue(j + 1);
								break;
							case 2:
								tempCell.setCellValue(currentCodelistDetail.get(j).getName() != null ? currentCodelistDetail.get(j).getName() : "");
								break;
							case 13:
								tempCell.setCellValue(currentCodelistDetail.get(j).getValue() != null ? currentCodelistDetail.get(j).getValue() : "");
								break;
							case 31:
								tempCell.setCellValue(currentCodelistDetail.get(j).getValue1() != null ? currentCodelistDetail.get(j).getValue1() : "");
								break;
							case 38:
								tempCell.setCellValue(currentCodelistDetail.get(j).getValue2() != null ? currentCodelistDetail.get(j).getValue2() : "");
								break;
							case 45:
								tempCell.setCellValue(currentCodelistDetail.get(j).getValue3() != null ? currentCodelistDetail.get(j).getValue3() : "");
								break;
							case 52:
								tempCell.setCellValue(currentCodelistDetail.get(j).getValue4() != null ? currentCodelistDetail.get(j).getValue4() : "");
								break;
							case 59:
								tempCell.setCellValue(currentCodelistDetail.get(j).getValue5() != null ? currentCodelistDetail.get(j).getValue5() : "");
								break;
							}
						}
					}
				}
			}
		}
		
		try {
			FileOutputStream out = new FileOutputStream(outputFilePath);
			workbook.write(out);
			out.close();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			item.setData(null);
			item.setDataLst(null);
			workbook.dispose();
		}
	}
	
	/**
	 * Generate header string (common)
	 * @param item
	 * @return
	 */
	public static String[] getHeaderString(GenerateDocumentItem item){
		String[] titleString = new String[11];
		if(item.getDocumentItemParenItemType().equals(GenerateDocumentConst.GENERATE_PROJECT_ED))
		{
			switch (item.getDocumentItemType()) {
			case EDDocumentTypeByProject.VIEW_LIST:
				titleString[0] = MessageUtils.getMessage(GenerateDocumentConst.ViewList.TITLE);
				break;
			case EDDocumentTypeByProject.VIEW_DESIGN:
				titleString[0] = MessageUtils.getMessage(GenerateDocumentConst.ViewDesign.TITLE);
				break;
			case EDDocumentTypeByProject.MESSAGE_DESIGN:
				titleString[0] = MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.TITLE);
				break;
			case EDDocumentTypeByProject.SCREEN_TRANSITION_DIAGRAM:
				titleString[0] = MessageUtils.getMessage(GenerateDocumentConst.ScreenTransitionDiagram.TITLE);
				break;	
			case EDDocumentTypeByProject.CODE_LIST:
				titleString[0] = MessageUtils.getMessage(GenerateDocumentConst.CodeList.TITLE);
				break;
			}
		}
		
		if(item.getDocumentItemParenItemType().equals(GenerateDocumentConst.GENERATE_PROJECT_RD))
		{
			switch (item.getDocumentItemType()) {
			
			case RDDocumentTypeByProject.BUSINESS_TYPE:
				titleString[0] = MessageUtils.getMessage(GenerateDocumentConst.BusinessList.TITLE);
				break;
			case RDDocumentTypeByProject.FUNCTION_LIST:
				titleString[0] = MessageUtils.getMessage(GenerateDocumentConst.FunctionList.TITLE);
				break;
			case RDDocumentTypeByProject.DOMAIN_DESIGN:
				titleString[0] = MessageUtils.getMessage(GenerateDocumentConst.DomainDesign.TITLE);
				break;
			case RDDocumentTypeByProject.PROCESSING_LIST:
				titleString[0] = MessageUtils.getMessage(GenerateDocumentConst.ProcessingList.TITLE);
				break;	
			case RDDocumentTypeByProject.TABLE_DESIGN:
				titleString[0] = MessageUtils.getMessage(GenerateDocumentConst.TableDesign.TITLE);
				break;
			}
		}
		
		titleString[1] = MessageUtils.getMessage(GenerateDocumentConst.Header.SYSTEM_NAME);
		titleString[2] = MessageUtils.getMessage(GenerateDocumentConst.Header.CREATE_BY);
		titleString[3] = MessageUtils.getMessage(GenerateDocumentConst.Header.CREATE_DATE);
		titleString[4] = MessageUtils.getMessage(GenerateDocumentConst.Header.UPDATE_BY);
		titleString[5] = MessageUtils.getMessage(GenerateDocumentConst.Header.UPDATE_DATE);
		
		DateFormat df = new SimpleDateFormat(currentAccounProfile.getDateFormat());
		titleString[6] = currentProject.getProjectName();
		titleString[7] = currentAccount.getUsername();
		titleString[8] = df.format(new Date()).toString();
		titleString[9] = StringUtils.EMPTY;
		titleString[10] = StringUtils.EMPTY;
		return titleString;
	 }
		
	/**
	 * Generate message design 
	 * @param item
	 */
	private static void processGenerateEDDocumentMessageDesignByProjectNew(GenerateDocumentItem item) {
			
			try {						
				String outputFilePath = item.getExcelFolder() + item.getDocumentItemTemplateName();
				XSSFWorkbook wb = new XSSFWorkbook();
				Map<String, File> sheets = new HashMap<String, File>();

				GenerateDocumentMessageDesign.addSheet("DataSource", wb, sheets);
				GenerateDocumentMessageDesign.addSheet("MessageDefinitionDocument", wb, sheets);

				wb.setActiveSheet(1);
				
				GenerateDocumentMessageDesign.createStyles(wb);

				// save the template
				FileOutputStream os = new FileOutputStream(item.getExcelFolder() + "template.xlsx");
				wb.write(os);
				os.close();

				int numOfLanguage = CollectionUtils.isEmpty(item.getLanguageDesignLst()) ? 0 : item.getLanguageDesignLst().size();

				// generate data for each sheet
				for (Map.Entry<String, File> entry : sheets.entrySet()) {
					Writer fw = new FileWriter(entry.getValue());

					if (StringUtils.contains(entry.getKey(), "sheet1.xml")) {
						GenerateDocumentMessageDesign.generateDatasource(fw, item.getLanguageDesignLst());
					} else {
						GenerateDocumentMessageDesign.generate(fw, item.getDataLst(),getHeaderString(item), numOfLanguage);
					}
					fw.close();
				}

				//Substitute the template entry with the generated data
				FileOutputStream out = new FileOutputStream(outputFilePath);

				GenerateDocumentMessageDesign.substitute(new File(item.getExcelFolder() + "template.xlsx"), sheets, out);
				out.close();
				
				// Delete temp file
				File fileTemp = new File(item.getExcelFolder() + "template.xlsx");
				if (fileTemp.exists()){
					fileTemp.delete();
				}   
				wb.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				item.setDataLst(null);
			}
		}
	
	/**
	 * Generate table design 
	 * @param item
	 */
	public static void processGenerateRDDocumentTableDesignByProjectNew(GenerateDocumentItem item, boolean isExportLog) {
			
		try {						
			String outputFilePath = item.getExcelFolder() + item.getDocumentItemTemplateName();

			XSSFWorkbook wb = new XSSFWorkbook();
			Map<String, File> sheets = new HashMap<String, File>();
			
			// Generate sheet with styles
			
			// Generate data source sheet
			GenerateDocumentTableDesign.addSheet("DataSource", wb, sheets);
			
			// Map store created sheets
			Map<String, TableDesign> mapTableDesign =  new HashMap<String, TableDesign>();
			int sheetNum = 2;
			
			if(CollectionUtils.isNotEmpty(item.getDataLst())){
				for(Object obj: item.getDataLst()){
					TableDesign tableDesign = (TableDesign) obj;
					GenerateDocumentTableDesign.addSheet(tableDesign.getTableName(), wb, sheets);
					mapTableDesign.put("sheet" + sheetNum + ".xml", tableDesign);
					sheetNum++;
				}
			}
			wb.setActiveSheet(1);
			GenerateDocumentTableDesign.createStyles(wb);	
			
			// save the template
			FileOutputStream os = new FileOutputStream(item.getExcelFolder() + "template.xlsx");
			wb.write(os);
			os.close();
			
			// generate data for each sheet
			for (Map.Entry<String, File> entry : sheets.entrySet()) {
				Writer fw = new FileWriter(entry.getValue());
				// Gen data source
				if(StringUtils.contains(entry.getKey(), "sheet1.xml")){
					GenerateDocumentTableDesign.generateDatasource(fw, item);
					fw.close();
					continue;
				}
				
				// Gen table
				for(String sheetName : mapTableDesign.keySet()){
					if (StringUtils.contains(entry.getKey(), sheetName)) {
						// Call generate method
						GenerateDocumentTableDesign.generate(fw, mapTableDesign.get(sheetName), getHeaderString(item), item.getListBasetype().size(), item.getListDomainDesigns().size(), item.getMapCodeList(), isExportLog);
						break;
					}
				}
				fw.close();
			}
		
			// Substitute the template entry with the generated data
			FileOutputStream out = new FileOutputStream(outputFilePath);
			GenerateDocumentTableDesign.substitute(new File(item.getExcelFolder() + "template.xlsx"), sheets, out);
			out.close();

			// Delete temp file
			File fileTemp = new File(item.getExcelFolder() + "template.xlsx");
			if (fileTemp.exists()){
				fileTemp.delete();
			}
			wb.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			item.setDataLst(null);
		}
	}
	
	private static Row cloneRow(Workbook wb, Sheet sheet, int sourceRowNum, int destinationRowNum){
		
		Row newRow = sheet.createRow(destinationRowNum);
		Row sourceRow = sheet.getRow(sourceRowNum);
		
		for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
			
			Cell oldCell = sourceRow.getCell(i);
			Cell newCell = newRow.createCell(i);
			if (oldCell == null) {
				newCell = null;
				continue;
			}
			CellStyle newCellStyle = wb.createCellStyle();
			newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
			newCell.setCellStyle(newCellStyle);
			// If there is a cell comment, copy
			if (oldCell.getCellComment() != null) {
				newCell.setCellComment(oldCell.getCellComment());
			}
			// If there is a cell hyperlink, copy
			if (oldCell.getHyperlink() != null) {
				newCell.setHyperlink(oldCell.getHyperlink());
			}
			// Set the cell data type
			newCell.setCellType(oldCell.getCellType());
		}
		return newRow;
		
	}
	
	/*private static Row cloneRowByColumn(Workbook wb, Sheet sheet, int sourceRowNum, int destinationRowNum, int startColumn, int endColumn){
		
		Row newRow = sheet.getRow(destinationRowNum);
		if( newRow == null) {
			newRow = sheet.createRow(destinationRowNum);
		}
		Row sourceRow = sheet.getRow(sourceRowNum);
		
		for (int i = startColumn; i <= endColumn; i++) {
			
			Cell oldCell = sourceRow.getCell(i);
			Cell newCell = newRow.createCell(i);
			if (oldCell == null) {
				newCell = null;
				continue;
			}
			CellStyle newCellStyle = wb.createCellStyle();
			newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
			newCell.setCellStyle(newCellStyle);
			// If there is a cell comment, copy
			if (oldCell.getCellComment() != null) {
				newCell.setCellComment(oldCell.getCellComment());
			}
			// If there is a cell hyperlink, copy
			if (oldCell.getHyperlink() != null) {
				newCell.setHyperlink(oldCell.getHyperlink());
			}
			// Set the cell data type
			newCell.setCellType(oldCell.getCellType());		 
		}
		return newRow;
		
	}*/
			
}


