package org.terasoluna.qp.domain.service.generatedocument;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.GraphicDatabaseDesignMessageConst;
import org.terasoluna.qp.domain.model.LanguageDesign;
import org.terasoluna.qp.domain.model.MessageDesign;

public class GenerateDocumentCommon {
	private static Map<String, CellStyle> styles = new HashMap<String, CellStyle>();

	/**
	 * Set style list for workbook
	 * 
	 * @param wb
	 */
	public static void SetStyleList(Workbook wb) {
		// Cell style for header row
		CellStyle style1 = wb.createCellStyle();
		style1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style1.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		// Set Font
		Font f = wb.createFont();
		f.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style1.setFont(f);
		style1.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		// Set border
		style1.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style1.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style1.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style1.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styles.put("headerBold", style1);

		// Cell style for header row normal
		CellStyle style2 = wb.createCellStyle();
		style2.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// Set border
		style2.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style2.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styles.put("headerNormal", style2);

		// Cell style for Datasource sheet title
		CellStyle style3 = wb.createCellStyle();
		// Set Font
		Font f3 = wb.createFont();
		f3.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style3.setFont(f3);
		styles.put("dtSourceTitle", style3);

		// Cell style for Datasource sheet header
		CellStyle style4 = wb.createCellStyle();
		style4.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style4.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		// Set Font
		Font f4 = wb.createFont();
		f4.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style4.setFont(f4);

		// Set border
		style4.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style4.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style4.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style4.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styles.put("dtSourceHeader", style4);

		// Cell style for Datasource sheet content (normal)
		CellStyle style5 = wb.createCellStyle();
		// Set border
		style5.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style5.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style5.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style5.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styles.put("dtSourceContent", style5);

		// Cell style for title of content
		CellStyle style6 = wb.createCellStyle();
		style6.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style6.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		// Set Font
		Font f6 = wb.createFont();
		f6.setBoldweight(Font.BOLDWEIGHT_BOLD);
		f6.setFontHeightInPoints((short) 8);
		style6.setFont(f6);
		style6.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style6.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		// Set border
		style6.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style6.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style6.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style6.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styles.put("messageTitle", style6);

		// Cell style for content
		CellStyle style7 = wb.createCellStyle();
		// Set Font
		Font f7 = wb.createFont();
		f7.setFontHeightInPoints((short) 8);
		style7.setFont(f7);
		style7.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		// Set border
		style7.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style7.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style7.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style7.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styles.put("messageContent", style7);
	}

	/**
	 * Create empty style cells by range
	 * 
	 * @param row
	 * @param start
	 * @param end
	 * @param style
	 */
	public static void createEmptyCells(Row row, int start, int end, CellStyle style) {
		for (int i = start; i <= end; i++) {
			Cell cell = row.createCell(i);
			cell.setCellStyle(style);
		}
	}

	/**
	 * Create cell with string value
	 * 
	 * @param row
	 * @param index
	 * @param value
	 * @param style
	 * @return
	 */
	public static Cell createCell(Row row, int index, String value, CellStyle style) {
		Cell cell = row.createCell(index);
		cell.setCellStyle(style);
		cell.setCellValue((value == null) ? StringUtils.EMPTY : value);
		return cell;
	}

	/**
	 * Create cell with string value and cell type
	 * 
	 * @param row
	 * @param index
	 * @param value
	 * @param style
	 * @param type
	 * @return
	 */
	public static Cell createCell(Row row, int index, String value, CellStyle style, int type) {
		Cell cell = row.createCell(index, type);
		cell.setCellStyle(style);
		cell.setCellValue(value);
		return cell;
	}

	/**
	 * Set cell width for sheet
	 * 
	 * @param sheet
	 */
	public static void setCellWidthForRow(SXSSFSheet sheet) {
		for (int i = 0; i < 64; i++) {
			sheet.setColumnWidth(i, 4 * 256);
		}
	}

	/**
	 * Generate common header
	 * 
	 * @param sheet
	 * @param titleString
	 */
	public static void generateHeader(SXSSFSheet sheet, String[] titleString) {
		// Get style
		CellStyle headerBold = styles.get("headerBold");
		CellStyle headerNormal = styles.get("headerNormal");
		// 1st line
		Row row = sheet.createRow(0);
		createCell(row, 0, titleString[0], headerBold);
		createEmptyCells(row, 1, 10, headerBold);

		createCell(row, 11, titleString[1], headerBold);
		createEmptyCells(row, 12, 19, headerBold);

		createCell(row, 20, "", headerBold);
		createEmptyCells(row, 21, 28, headerBold);

		createCell(row, 29, "", headerBold);
		createEmptyCells(row, 30, 37, headerBold);

		createCell(row, 38, titleString[2], headerBold);
		createEmptyCells(row, 39, 43, headerBold);

		createCell(row, 44, titleString[3], headerBold);
		createEmptyCells(row, 45, 49, headerBold);

		createCell(row, 50, titleString[4], headerBold);
		createEmptyCells(row, 51, 56, headerBold);

		createCell(row, 57, titleString[5], headerBold);
		createEmptyCells(row, 58, 63, headerBold);

		// 2nd line
		row = sheet.createRow(1);
		createEmptyCells(row, 0, 10, headerBold);

		createCell(row, 11, titleString[6], headerNormal);
		createEmptyCells(row, 12, 19, headerNormal);

		createCell(row, 20, "", headerNormal);
		createEmptyCells(row, 21, 28, headerNormal);

		createCell(row, 29, "", headerNormal);
		createEmptyCells(row, 30, 37, headerNormal);

		createCell(row, 38, titleString[7], headerNormal);
		createEmptyCells(row, 39, 43, headerNormal);

		createCell(row, 44, titleString[8], headerNormal);
		createEmptyCells(row, 45, 49, headerNormal);

		createCell(row, 50, titleString[9], headerNormal);
		createEmptyCells(row, 51, 56, headerNormal);

		createCell(row, 57, titleString[10], headerNormal);
		createEmptyCells(row, 58, 63, headerNormal);

		// Merge cell
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 10));
		for (int i = 0; i < 2; i++) {
			sheet.addMergedRegion(new CellRangeAddress(i, i, 11, 19));
			sheet.addMergedRegion(new CellRangeAddress(i, i, 20, 28));
			sheet.addMergedRegion(new CellRangeAddress(i, i, 29, 37));
			sheet.addMergedRegion(new CellRangeAddress(i, i, 38, 43));
			sheet.addMergedRegion(new CellRangeAddress(i, i, 44, 49));
			sheet.addMergedRegion(new CellRangeAddress(i, i, 50, 56));
			sheet.addMergedRegion(new CellRangeAddress(i, i, 57, 63));
		}
	}

	/**
	 * Generate message data source sheet
	 * 
	 * @param sheet
	 * @param languageDesignList
	 */
	public static void generateMessageDataSource(SXSSFSheet sheet,
			List<LanguageDesign> languageDesignList) {
		// Get style
		CellStyle dtSourceTitle = styles.get("dtSourceTitle");
		CellStyle dtSourceHeader = styles.get("dtSourceHeader");
		CellStyle dtSourceContent = styles.get("dtSourceContent");

		// Insert title
		Row row1 = sheet.createRow(2);
		createCell(row1, 0, "Source", dtSourceTitle);
		createCell(row1, 4, "Output location", dtSourceTitle);
		createCell(row1, 8, "Error level", dtSourceTitle);
		createCell(row1, 12, "Language", dtSourceTitle);

		// Insert data source header
		Row row2 = sheet.createRow(3);
		createCell(row2, 1, "Code", dtSourceHeader);
		createCell(row2, 2, "Name", dtSourceHeader);
		createCell(row2, 5, "Code", dtSourceHeader);
		createCell(row2, 6, "Name", dtSourceHeader);
		createCell(row2, 9, "Code", dtSourceHeader);
		createCell(row2, 10, "Name", dtSourceHeader);
		createCell(row2, 13, "Code", dtSourceHeader);
		createCell(row2, 14, "Name", dtSourceHeader);

		Map<String, List<String[]>> mapData = prepareData(languageDesignList);
		List<String[]> listSource = mapData.get("listSource");
		List<String[]> listOutputLocation = mapData.get("listOutputLocation");
		List<String[]> listErrorLevel = mapData.get("listErrorLevel");
		List<String[]> listLanguage = mapData.get("listLanguage");

		// Get max row
		int maxRow = (listOutputLocation.size() > listLanguage.size()) ? listOutputLocation.size()
				: listLanguage.size();

		for (int rownum = 4; rownum < (maxRow + 4); rownum++) {
			Row row = sheet.createRow(rownum);

			if (listSource.size() >= rownum - 3) {
				if (listSource.get(rownum - 4) != null) {
					createCell(row, 1, listSource.get(rownum - 4)[0], dtSourceContent, 0);
					createCell(row, 2, listSource.get(rownum - 4)[1], dtSourceContent);
				}
			}
			if (listOutputLocation.size() >= rownum - 3) {
				if (listOutputLocation.get(rownum - 4) != null) {
					createCell(row, 5, listOutputLocation.get(rownum - 4)[0], dtSourceContent, 0);
					createCell(row, 6, listOutputLocation.get(rownum - 4)[1], dtSourceContent);
				}
			}
			if (listErrorLevel.size() >= rownum - 3) {
				if (listErrorLevel.get(rownum - 4) != null) {
					createCell(row, 9, listErrorLevel.get(rownum - 4)[0], dtSourceContent);
					createCell(row, 10, listErrorLevel.get(rownum - 4)[1], dtSourceContent);
				}
			}
			if (listLanguage.size() >= rownum - 3) {
				if (listLanguage.get(rownum - 4) != null) {
					createCell(row, 13, listLanguage.get(rownum - 4)[0], dtSourceContent, 0);
					createCell(row, 14, listLanguage.get(rownum - 4)[1], dtSourceContent);
				}
			}
		}
	}

	/**
	 * prepare data for data source sheet
	 * 
	 * @param languageDesignList
	 * @return
	 */
	private static Map<String, List<String[]>> prepareData(List<LanguageDesign> languageDesignList) {
		Map<String, List<String[]>> mapData = new HashMap<String, List<String[]>>();

		List<String[]> listSource = new ArrayList<String[]>();
		List<String[]> listOutputLocation = new ArrayList<String[]>();
		List<String[]> listErrorLevel = new ArrayList<String[]>();
		List<String[]> listLanguage = new ArrayList<String[]>();

		// Add source
		String[] source1 = new String[2];
		source1[0] = "0";
		source1[1] = "Client";
		listSource.add(source1);
		String[] source2 = new String[2];
		source2[0] = "1";
		source2[1] = "Server";
		listSource.add(source2);

		// Output location
		String[] outputLocation1 = new String[2];
		outputLocation1[0] = "0";
		outputLocation1[1] = "Project";
		listOutputLocation.add(outputLocation1);
		String[] outputLocation2 = new String[2];
		outputLocation2[0] = "1";
		outputLocation2[1] = "Module";
		listOutputLocation.add(outputLocation2);
		String[] outputLocation3 = new String[2];
		outputLocation3[0] = "2";
		outputLocation3[1] = "Screen";
		listOutputLocation.add(outputLocation3);
		String[] outputLocation4 = new String[2];
		outputLocation4[0] = "3";
		outputLocation4[1] = "Screen area";
		listOutputLocation.add(outputLocation4);
		String[] outputLocation5 = new String[2];
		outputLocation5[0] = "4";
		outputLocation5[1] = "Screen item";
		listOutputLocation.add(outputLocation5);
		String[] outputLocation6 = new String[2];
		outputLocation6[0] = "5";
		outputLocation6[1] = "Blogic";
		listOutputLocation.add(outputLocation6);
		String[] outputLocation7 = new String[2];
		outputLocation7[0] = "6";
		outputLocation7[1] = "Menu design";
		listOutputLocation.add(outputLocation7);
		String[] outputLocation8 = new String[2];
		outputLocation8[0] = "7";
		outputLocation8[1] = "Design information";
		listOutputLocation.add(outputLocation8);

		// Error level
		String[] errorLevel1 = new String[2];
		errorLevel1[0] = "sc";
		errorLevel1[1] = "Label";
		listErrorLevel.add(errorLevel1);
		String[] errorLevel2 = new String[2];
		errorLevel2[0] = "inf";
		errorLevel2[1] = "Information";
		listErrorLevel.add(errorLevel2);
		String[] errorLevel3 = new String[2];
		errorLevel3[0] = "wrn";
		errorLevel3[1] = "Warning";
		listErrorLevel.add(errorLevel3);
		String[] errorLevel4 = new String[2];
		errorLevel4[0] = "err";
		errorLevel4[1] = "Error";
		listErrorLevel.add(errorLevel4);

		for (LanguageDesign languageDesign : languageDesignList) {
			String[] language = new String[2];
			language[0] = languageDesign.getLanguageId().toString();
			language[1] = languageDesign.getLanguageName();
			listLanguage.add(language);
		}

		mapData.put("listSource", listSource);
		mapData.put("listOutputLocation", listOutputLocation);
		mapData.put("listErrorLevel", listErrorLevel);
		mapData.put("listLanguage", listLanguage);

		return mapData;
	}

	/**
	 * Generate message title for message design information sheet
	 * 
	 * @param sheet
	 * @param titleString
	 */
	public static void generateMessageTitle(SXSSFSheet sheet, String[] titleString) {
		CellStyle messageTitle = styles.get("messageTitle");
		Row row = sheet.createRow(3);
		// Set height
		row.setHeightInPoints(37);

		// Create cells
		createCell(row, 0, titleString[0], messageTitle);
		createEmptyCells(row, 1, 1, messageTitle);

		createCell(row, 2, titleString[1], messageTitle);
		createEmptyCells(row, 3, 13, messageTitle);

		createCell(row, 14, titleString[2], messageTitle);
		createEmptyCells(row, 15, 18, messageTitle);

		createCell(row, 19, titleString[3], messageTitle);
		createEmptyCells(row, 20, 23, messageTitle);

		createCell(row, 24, titleString[4], messageTitle);
		createEmptyCells(row, 25, 28, messageTitle);

		createCell(row, 29, titleString[5], messageTitle);
		createEmptyCells(row, 30, 49, messageTitle);

		createCell(row, 50, titleString[6], messageTitle);
		createEmptyCells(row, 51, 61, messageTitle);

		createCell(row, 62, titleString[7], messageTitle);
		createEmptyCells(row, 63, 63, messageTitle);

		// Merge cell
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 13));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 14, 18));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 19, 23));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 24, 28));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 29, 49));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 50, 61));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 62, 63));

	}

	/**
	 * Generate message design content
	 * 
	 * @param sheet
	 * @param messageDesign
	 * @param rowNum
	 */
	public static void generateMessageDesignContent(SXSSFSheet sheet, MessageDesign messageDesign,
			int rowNum) {
		CellStyle messageContent = styles.get("messageContent");
		Row row = sheet.createRow(rowNum);
		createCell(row, 0, String.valueOf(rowNum - 4 + 1), messageContent);
		createEmptyCells(row, 1, 1, messageContent);

		createCell(row, 2, messageDesign.getMessageCode(), messageContent);
		createEmptyCells(row, 3, 13, messageContent);

		String source = StringUtils.EMPTY;
		if (GenerateDocumentConst.LABEL.equals(messageDesign.getMessageType())) {
			source = MessageUtils
					.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0065);
		} else {
			source = MessageUtils
					.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0066);
		}
		createCell(row, 14, source, messageContent);
		createEmptyCells(row, 15, 18, messageContent);

		createCell(row, 19, MessageUtils.getMessage("sc.businesslogicdesign.0134"), messageContent);
		createEmptyCells(row, 20, 23, messageContent);

		createCell(row, 24, messageDesign.getMessageTypeName(), messageContent);
		createEmptyCells(row, 25, 28, messageContent);

		createCell(row, 29, messageDesign.getMessageString(), messageContent);
		createEmptyCells(row, 30, 49, messageContent);

		createCell(row, 50, messageDesign.getRemark(), messageContent);
		createEmptyCells(row, 51, 61, messageContent);

		createCell(row, 62, messageDesign.getLanguageName(), messageContent);
		createEmptyCells(row, 63, 63, messageContent);

		// Merge cell
		sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 2, 13));
		sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 14, 18));
		sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 19, 23));
		sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 24, 28));
		sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 29, 49));
		sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 50, 61));
		sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 62, 63));

	}

	public static void generateMessageDataSourceReference(SXSSFSheet sheet, int languageListSize, int totalDataRow) {
		int firstRow = 4;

		// Language
		String langFormula = "=\''Data Source\''!$O${0}:$O${1}";
		langFormula = MessageFormat.format(langFormula, String.valueOf(firstRow + 1), String.valueOf(firstRow + languageListSize));
		CellRangeAddressList langAddressList = new CellRangeAddressList(firstRow, firstRow + totalDataRow - 1, 62, 63);
		DataValidationHelper langHelper = sheet.getDataValidationHelper();
		DataValidationConstraint langConstraint = langHelper.createFormulaListConstraint(langFormula);
		DataValidation langValidation = langHelper.createValidation(langConstraint, langAddressList);
		sheet.addValidationData(langValidation);
		
		// Source
		String sourceFormula = "=\''Data Source\''!$C${0}:$C${1}";
		sourceFormula = MessageFormat.format(sourceFormula, 5, 6);
		CellRangeAddressList sourceAddressList = new CellRangeAddressList(firstRow, firstRow + totalDataRow - 1, 14, 18);
		DataValidationHelper sourceHelper = sheet.getDataValidationHelper();
		DataValidationConstraint sourceConstraint = sourceHelper.createFormulaListConstraint(sourceFormula);
		DataValidation sourceValidation = sourceHelper.createValidation(sourceConstraint, sourceAddressList);
		sheet.addValidationData(sourceValidation);
		
		// Output
		String outputFormula = "=\''Data Source\''!$G${0}:$G${1}";
		outputFormula = MessageFormat.format(outputFormula, 5, 12);
		CellRangeAddressList outputAddressList = new CellRangeAddressList(firstRow, firstRow + totalDataRow - 1, 19, 23);
		DataValidationHelper outputHelper = sheet.getDataValidationHelper();
		DataValidationConstraint outputConstraint = outputHelper.createFormulaListConstraint(outputFormula);
		DataValidation outputValidation = outputHelper.createValidation(outputConstraint, outputAddressList);
		sheet.addValidationData(outputValidation);

		// Error level
		String levelFormula = "=\''Data Source\''!$K${0}:$K${1}";
		levelFormula = MessageFormat.format(levelFormula, 5, 8);
		CellRangeAddressList levelAddressList = new CellRangeAddressList(firstRow, firstRow + totalDataRow - 1, 24, 28);
		DataValidationHelper levelHelper = sheet.getDataValidationHelper();
		DataValidationConstraint levelConstraint = levelHelper.createFormulaListConstraint(levelFormula);
		DataValidation levelValidation = levelHelper.createValidation(levelConstraint, levelAddressList);
		sheet.addValidationData(levelValidation);
	}
}
