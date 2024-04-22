package org.terasoluna.qp.app.common.ultils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.domain.model.AutocompleteDesign;
import org.terasoluna.qp.domain.model.FormulaDefinition;
import org.terasoluna.qp.domain.model.GenerateDocumentItem;
import org.terasoluna.qp.domain.model.MessageDesign;
import org.terasoluna.qp.domain.model.ScreenAction;
import org.terasoluna.qp.domain.model.ScreenArea;
import org.terasoluna.qp.domain.model.ScreenAreaEvent;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.model.ScreenForm;
import org.terasoluna.qp.domain.model.ScreenItem;
import org.terasoluna.qp.domain.model.ScreenItemStatus;
import org.terasoluna.qp.domain.model.ScreenItemValidation;

public class GenerateScreenDesignDocumentUtilsQP {

	private static final Logger log = LoggerFactory.getLogger(GenerateScreenDesignDocumentUtilsQP.class);

	public static void processGenerateEDDocumentLayoutSheet(GenerateDocumentItem item, Workbook wb, Sheet sheet) {
		try {

			ScreenDesign screenDesign = (ScreenDesign) item.getData();

			// Process multiple language of template sheet
			GenerateDocumentUtilsQP.processMultiLanguageForTemplate(sheet);
			// Generate data for header
			GenerateDocumentUtilsQP.headerGenerateInformation(sheet.getRow(1), Arrays.asList(11, 38, 44, 50, 56));

			Row rowHeader = sheet.getRow(1);
			Cell businessNameCell = rowHeader.getCell(17);
			Cell funcNamecell = rowHeader.getCell(24);
			Cell screenNamecell = rowHeader.getCell(31);

			businessNameCell.setCellValue("");
			funcNamecell.setCellValue(screenDesign.getFunctionDesignName());
			
			log.info("screenDesign code {0}", screenDesign.getScreenCode());
			screenNamecell.setCellValue(getMessageStringFromScreen(screenDesign));

			Row row = sheet.getRow(4);
			Cell cell = row.getCell(0);
			cell.setCellValue(screenDesign.getRemark());

			// call service capture image
			String imagePath = item.getCapturePath();
			if (imagePath != null) {
				File file = new File(imagePath);
				if (file.exists() && file.isFile()) {
					//InputStream inputStream = new FileInputStream(imagePath);
					//byte[] imageBytes = IOUtils.toByteArray(inputStream);
					int pictureureIdx = wb.addPicture(FileUtilsQP.readFileToByteArray(file), Workbook.PICTURE_TYPE_PNG);
					//IOUtils.closeQuietly(inputStream);
					
					CreationHelper helper = wb.getCreationHelper();
					Drawing drawing = sheet.createDrawingPatriarch();
					ClientAnchor anchor = helper.createClientAnchor();

					int imageRow = 9;
					int imageCol = 2;

					anchor.setRow1(imageRow);
					anchor.setCol1(imageCol);

					XSSFPicture pict = (XSSFPicture) drawing.createPicture(anchor, pictureureIdx);
					pict.resize();

					CellRangeAddress cellRangeAddress = new CellRangeAddress(imageRow - 1, anchor.getRow2() + 1, 0, 61);
					sheet.addMergedRegion(cellRangeAddress);
					settingStyleForCellHadMerge(0, cellRangeAddress, sheet, wb);

					// delete file
					FileUtilsQP.deleteQuietly(file);

					
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void processGenerateEDDocumentInputCheckSheet(GenerateDocumentItem item, Workbook wb, Sheet sheet) {

		try {

			ScreenDesign screenDesign = (ScreenDesign) item.getData();

			// Process multiple language of template sheet
			GenerateDocumentUtilsQP.processMultiLanguageForTemplate(sheet);
			// Generate data for header
			GenerateDocumentUtilsQP.headerGenerateInformation(sheet.getRow(1), Arrays.asList(11, 38, 44, 50, 56));

			Row rowHeader = sheet.getRow(1);
			Cell businessNameCell = rowHeader.getCell(17);
			Cell funcNamecell = rowHeader.getCell(24);
			Cell screenNamecell = rowHeader.getCell(31);

			businessNameCell.setCellValue("");
			funcNamecell.setCellValue(screenDesign.getFunctionDesignName());
			screenNamecell.setCellValue(getMessageStringFromScreen(screenDesign));

			// define style
			CellStyle defaultCellStyle = wb.createCellStyle();
			defaultCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			defaultCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			defaultCellStyle.setFillBackgroundColor(HSSFColor.WHITE.index);
			defaultCellStyle.setWrapText(true);

			CellStyle alignRightCellStyle = wb.createCellStyle();
			alignRightCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
			alignRightCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			alignRightCellStyle.setFillBackgroundColor(HSSFColor.WHITE.index);

			CellStyle alignLeftCellStyle = wb.createCellStyle();
			alignLeftCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			alignLeftCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			alignLeftCellStyle.setFillBackgroundColor(HSSFColor.WHITE.index);

			CellStyle headerCellStyle = sheet.getRow(4).getCell(0).getCellStyle();

			int rowIndex = 6;
			List<ScreenItem> btnItems = new ArrayList<ScreenItem>();
			List<List<String>> dataLists = new ArrayList<List<String>>();
			Map<Long, Integer> startIndexFormMap = new HashMap<Long, Integer>();
			Map<Long, Integer> endIndexFormMap = new HashMap<Long, Integer>();
			int startIndexScreenItem = 0;
			int endIndexScreenItem = 0;

			// Independent Item Check
			startIndexScreenItem = rowIndex;
			for (Object obj : screenDesign.getScreenForms()) {
				ScreenForm screenForm = (ScreenForm) obj;
				startIndexFormMap.put(screenForm.getScreenFormId(), rowIndex);
				for (ScreenArea screenArea : screenForm.getListScreenAreas()) {
					for (ScreenItem screenItem : screenArea.getListItems()) {

						ScreenItemValidation itemVal = screenItem.getScreenItemValidation();
						MessageDesign messDesign = screenItem.getMessageDesign();

						if (messDesign != null && itemVal != null && !screenItem.getLogicalDataType().equals(DbDomainConst.LogicDataType.LABEL_DYNAMIC) && !screenItem.getLogicalDataType().equals(DbDomainConst.LogicDataType.LINK) && !screenItem.getLogicalDataType().equals(DbDomainConst.LogicDataType.BUTTON)) {

							String col1 = rowIndex - 5 + "";
							String col2 = messDesign.getMessageString();
							if (StringUtils.isEmpty(col2)) {
								col2 = messDesign.getMessageCode();
							}
							String col3 = itemVal.getFmtCode();
							if (StringUtils.isEmpty(col3)) {
								col3 = "String";
							}
							String col4 = "";
							if (itemVal.getMaxlength() != null) {
								col4 = itemVal.getMaxlength() + "";
							}
							String col5 = "";
							if (itemVal.getMinVal() != null && itemVal.getMaxVal() != null) {
								col5 = itemVal.getMinVal() + " <= x <= " + itemVal.getMaxVal();
							}
							String col6 = "";
							if (DbDomainConst.YesNoFlg.YES.equals(itemVal.getMandatoryFlg())) {
								col6 = "o";
							}
							List<String> dataList = Arrays.asList(col1, col2, col3, col4, col5, col6, "");
							dataLists.add(dataList);
							rowIndex++;
						} else if (screenItem.getLogicalDataType().equals(DbDomainConst.LogicDataType.BUTTON)) {

							ScreenAction screenAction = screenItem.getScreenAction();
							if (screenAction != null && screenAction.getActionType() == 1) {
								btnItems.add(screenItem);
							}
						}
					}
				}
				endIndexFormMap.put(screenForm.getScreenFormId(), rowIndex - 1);
			}
			endIndexScreenItem = rowIndex - 1;

			if (dataLists.size() > 0) {
				sheet.shiftRows(6, 10, dataLists.size());
			}

			// Add Independent Item Check
			rowIndex = startIndexScreenItem;
			for (List<String> dataList : dataLists) {

				List<Integer> positionList = Arrays.asList(1, 15, 20, 25, 30, 33, 38);
				List<CellStyle> styleList = Arrays.asList(alignRightCellStyle, alignLeftCellStyle, alignLeftCellStyle, alignLeftCellStyle, defaultCellStyle, defaultCellStyle, defaultCellStyle);
				writeRowScreenItems(wb, sheet, rowIndex, positionList, dataList, styleList);
				rowIndex++;
			}

			// add button check
			// add check timings and remark header
			rowHeader = sheet.getRow(4);
			Cell cell = rowHeader.createCell(39);
			cell.setCellStyle(headerCellStyle);
			int lastCol = 53;
			if (btnItems.size() > 3) {
				lastCol = 38 + btnItems.size() * 5;
			}
			CellRangeAddress cellRange = new CellRangeAddress(4, 4, 39, lastCol);
			cell.setCellValue(MessageUtils.getMessage("sc.screendesign.0400"));
			sheet.addMergedRegion(cellRange);
			settingStyleForCellHadMerge(0, cellRange, sheet, wb);

			cell = rowHeader.createCell(lastCol + 1);
			cell.setCellStyle(headerCellStyle);
			cellRange = new CellRangeAddress(4, 5, lastCol + 1, lastCol + 8);
			cell.setCellValue(MessageUtils.getMessage("sc.screendesign.0057"));
			sheet.addMergedRegion(cellRange);
			settingStyleForCellHadMerge(0, cellRange, sheet, wb);

			int btnIndex = 0;
			for (ScreenItem btnItem : btnItems) {
				btnIndex++;
				Row row = sheet.getRow(5);
				int colIndex = 34 + btnIndex * 5;
				cell = row.getCell(colIndex);
				if (cell == null) {
					cell = row.createCell(colIndex);
				}
				if (btnItem.getMessageDesign() != null) {
					String btnName = btnItem.getMessageDesign().getMessageString();
					cell.setCellValue(MessageUtils.getMessage("sc.screendesign.0412", btnName));
				} else {
					cell.setCellValue("");
				}
				cell.setCellStyle(defaultCellStyle);
				cellRange = new CellRangeAddress(5, 5, colIndex, colIndex + 4);
				sheet.addMergedRegion(cellRange);
				settingStyleForCellHadMerge(0, cellRange, sheet, wb);
				int startIndex = startIndexFormMap.get(btnItem.getScreenFormId());
				int endIndex = endIndexFormMap.get(btnItem.getScreenFormId());

				for (int formIndex = startIndexScreenItem; formIndex <= endIndexScreenItem; formIndex++) {
					cell = sheet.getRow(formIndex).createCell(colIndex);
					cell.setCellStyle(defaultCellStyle);
					cellRange = new CellRangeAddress(formIndex, formIndex, colIndex, colIndex + 4);
					sheet.addMergedRegion(cellRange);
					settingStyleForCellHadMerge(0, cellRange, sheet, wb);
					if (startIndex <= formIndex && formIndex <= endIndex) {
						cell.setCellValue("o");
					}
				}
			}
			// set check timings column
			while (btnIndex < 3) {
				btnIndex++;
				Row row = sheet.getRow(5);
				int colIndex = 34 + btnIndex * 5;
				cell = row.getCell(colIndex);
				if (cell == null) {
					cell = row.createCell(colIndex);
				}
				cell.setCellValue("");
				cell.setCellStyle(defaultCellStyle);
				cellRange = new CellRangeAddress(5, 5, colIndex, colIndex + 4);
				sheet.addMergedRegion(cellRange);
				settingStyleForCellHadMerge(0, cellRange, sheet, wb);
				for (int formIndex = startIndexScreenItem; formIndex <= endIndexScreenItem; formIndex++) {
					cell = sheet.getRow(formIndex).createCell(colIndex);
					cell.setCellStyle(defaultCellStyle);
					cellRange = new CellRangeAddress(formIndex, formIndex, colIndex, colIndex + 4);
					sheet.addMergedRegion(cellRange);
					settingStyleForCellHadMerge(0, cellRange, sheet, wb);
				}
			}
			btnIndex++;
			int colIndex = 34 + btnIndex * 5;
			// set remark column
			for (int formIndex = startIndexScreenItem; formIndex <= endIndexScreenItem; formIndex++) {
				cell = sheet.getRow(formIndex).createCell(colIndex);
				cell.setCellStyle(defaultCellStyle);
				cellRange = new CellRangeAddress(formIndex, formIndex, colIndex, colIndex + 7);
				sheet.addMergedRegion(cellRange);
				settingStyleForCellHadMerge(0, cellRange, sheet, wb);
			}

			// Correlation items
			// add header Correlation items: Item No., Check contents, Check timings, Remarks

			rowIndex = rowIndex + 2;
			Row row = sheet.getRow(rowIndex);

			cell = row.createCell(39);
			cell.setCellStyle(headerCellStyle);
			lastCol = 53;
			if (btnItems.size() > 3) {
				lastCol = 38 + btnItems.size() * 5;
			}
			cellRange = new CellRangeAddress(rowIndex, rowIndex, 39, lastCol);
			cell.setCellValue(MessageUtils.getMessage("sc.screendesign.0400"));
			sheet.addMergedRegion(cellRange);
			settingStyleForCellHadMerge(0, cellRange, sheet, wb);

			cell = row.createCell(lastCol + 1);
			cell.setCellStyle(headerCellStyle);
			cellRange = new CellRangeAddress(rowIndex, rowIndex + 1, lastCol + 1, lastCol + 8);
			cell.setCellValue(MessageUtils.getMessage("sc.screendesign.0057"));
			sheet.addMergedRegion(cellRange);
			settingStyleForCellHadMerge(0, cellRange, sheet, wb);

			rowIndex = rowIndex + 2;
			int rowCorrelationIndex = rowIndex - 1;

			int correlationIndex = 1;
			startIndexScreenItem = rowIndex;
			for (Object obj : screenDesign.getScreenForms()) {
				ScreenForm screenForm = (ScreenForm) obj;
				startIndexFormMap.put(screenForm.getScreenFormId(), rowIndex);
				for (ScreenArea screenArea : screenForm.getListScreenAreas()) {
					for (ScreenAreaEvent screenAreaEvent : screenArea.getScreenAreaEvents()) {

						List<Integer> positionList = Arrays.asList(1, 38);
						String col1 = correlationIndex + "";
						String col2 = MessageUtils.getMessage("sc.screendesign.0411", screenAreaEvent.getIfRequire(), screenAreaEvent.getThenMustRequire());
						List<String> dataList = Arrays.asList(col1, col2);
						List<CellStyle> styleList = Arrays.asList(alignRightCellStyle, alignLeftCellStyle);
						writeRowScreenItems(wb, sheet, rowIndex, positionList, dataList, styleList);
						rowIndex++;
						correlationIndex++;

					}
				}
				endIndexFormMap.put(screenForm.getScreenFormId(), rowIndex - 1);
			}
			endIndexScreenItem = rowIndex - 1;

			// add button check
			int btnIndexCorrelation = 0;
			for (ScreenItem btnItem : btnItems) {

				btnIndexCorrelation++;
				Row rowCorrelation = sheet.getRow(rowCorrelationIndex);
				colIndex = 34 + btnIndexCorrelation * 5;
				Cell cellCorrelation = rowCorrelation.createCell(colIndex);
				if (cellCorrelation == null) {
					cellCorrelation = rowCorrelation.createCell(colIndex);
				}
				String btnName = btnItem.getMessageDesign().getMessageString();
				cellCorrelation.setCellValue(MessageUtils.getMessage("sc.screendesign.0412", btnName));
				cellCorrelation.setCellStyle(defaultCellStyle);
				cellRange = new CellRangeAddress(rowCorrelationIndex, rowCorrelationIndex, colIndex, colIndex + 4);
				sheet.addMergedRegion(cellRange);
				settingStyleForCellHadMerge(0, cellRange, sheet, wb);

				int startIndex = startIndexFormMap.get(btnItem.getScreenFormId());
				int endIndex = endIndexFormMap.get(btnItem.getScreenFormId());

				for (int formIndex = startIndexScreenItem; formIndex <= endIndexScreenItem; formIndex++) {
					cell = sheet.getRow(formIndex).createCell(colIndex);
					cell.setCellStyle(defaultCellStyle);
					cellRange = new CellRangeAddress(formIndex, formIndex, colIndex, colIndex + 4);
					sheet.addMergedRegion(cellRange);
					settingStyleForCellHadMerge(0, cellRange, sheet, wb);
					if (startIndex <= formIndex && formIndex <= endIndex) {
						cell.setCellValue("o");
					}
				}
			}
			// set check timings column
			while (btnIndexCorrelation < 3) {
				btnIndexCorrelation++;
				Row rowCorrelation = sheet.getRow(rowCorrelationIndex);
				colIndex = 34 + btnIndexCorrelation * 5;
				cell = rowCorrelation.getCell(colIndex);
				if (cell == null) {
					cell = rowCorrelation.createCell(colIndex);
				}
				cell.setCellValue("");
				cell.setCellStyle(defaultCellStyle);
				cellRange = new CellRangeAddress(rowCorrelationIndex, rowCorrelationIndex, colIndex, colIndex + 4);
				sheet.addMergedRegion(cellRange);
				settingStyleForCellHadMerge(0, cellRange, sheet, wb);

				for (int formIndex = startIndexScreenItem; formIndex <= endIndexScreenItem; formIndex++) {
					cell = sheet.getRow(formIndex).createCell(colIndex);
					cell.setCellStyle(defaultCellStyle);
					cellRange = new CellRangeAddress(formIndex, formIndex, colIndex, colIndex + 4);
					sheet.addMergedRegion(cellRange);
					settingStyleForCellHadMerge(0, cellRange, sheet, wb);
				}
			}
			btnIndexCorrelation++;
			colIndex = 34 + btnIndexCorrelation * 5;
			// set remark column
			for (int formIndex = startIndexScreenItem; formIndex <= endIndexScreenItem; formIndex++) {

				cell = sheet.getRow(formIndex).createCell(colIndex);
				cell.setCellStyle(defaultCellStyle);
				cellRange = new CellRangeAddress(formIndex, formIndex, colIndex, colIndex + 7);
				sheet.addMergedRegion(cellRange);
				settingStyleForCellHadMerge(0, cellRange, sheet, wb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void writeRowScreenItems(Workbook wb, Sheet sheet, int rowIndex, List<Integer> positionList, List<String> dataList, List<CellStyle> styleList) {

		Row row = sheet.createRow(rowIndex);
		CellRangeAddress cellRange = null;

		for (int i = 0; i < positionList.size(); i++) {

			int firstCol = 0;
			if (i > 0)
				firstCol = positionList.get(i - 1) + 1;
			int lastCol = positionList.get(i);

			Cell cell = row.createCell(firstCol);
			cell.setCellStyle(styleList.get(i));
			if (i < dataList.size()) {
				cell.setCellValue(dataList.get(i));
			} else {
				cell.setCellValue("");
			}

			cellRange = new CellRangeAddress(rowIndex, rowIndex, firstCol, lastCol);
			sheet.addMergedRegion(cellRange);
			settingStyleForCellHadMerge(0, cellRange, sheet, wb);

		}
	}

	public static void processGenerateEDDocumentScreenItems(GenerateDocumentItem item, Workbook wb, Sheet sheet) {

		try {

			// Process multiple language of template sheet
			GenerateDocumentUtilsQP.processMultiLanguageForTemplate(sheet);

			ScreenDesign screenDesign = (ScreenDesign) item.getData();

			// Generate data for header
			Row rowHeader = sheet.getRow(1);
			Cell businessNameCell = rowHeader.getCell(17);
			Cell funcNamecell = rowHeader.getCell(24);
			Cell screenNamecell = rowHeader.getCell(31);

			businessNameCell.setCellValue("");
			funcNamecell.setCellValue(screenDesign.getFunctionDesignName());
			screenNamecell.setCellValue(getMessageStringFromScreen(screenDesign));
			GenerateDocumentUtilsQP.headerGenerateInformation(rowHeader, Arrays.asList(11, 38, 44, 50, 56));

			CellStyle defaultCellStyle = wb.createCellStyle();
			defaultCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			defaultCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			defaultCellStyle.setFillBackgroundColor(HSSFColor.WHITE.index);

			CellStyle alignRightCellStyle = wb.createCellStyle();
			alignRightCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
			alignRightCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			alignRightCellStyle.setFillBackgroundColor(HSSFColor.WHITE.index);

			CellStyle alignLeftCellStyle = wb.createCellStyle();
			alignLeftCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			alignLeftCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			alignLeftCellStyle.setFillBackgroundColor(HSSFColor.WHITE.index);

			CellStyle headerCellStyle = wb.createCellStyle();
			headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			headerCellStyle.setFillBackgroundColor(HSSFColor.BROWN.index);

			// write data
			int rowIndex = 5;
			// Setting body information
			ArrayList<ScreenAction> screenActions = new ArrayList<ScreenAction>();
			List<List<String>> dataLists = new ArrayList<List<String>>();

			int itemIndex = 1;
			for (Object obj : screenDesign.getScreenForms()) {

				ScreenForm screenForm = (ScreenForm) obj;
				List<String> dataList = Arrays.asList(itemIndex + "", screenForm.getFormCode(), "", "", "", "", "", "", "");
				dataLists.add(dataList);
				itemIndex++;
				for (ScreenArea screenArea : screenForm.getListScreenAreas()) {

					if (screenArea.getListItems() != null) {
						boolean hasAreaTitle = false;
						// add area name if number item > 1
						if (screenArea.getListItems().size() > 1) {
							MessageDesign messDesign = screenArea.getMessageDesign();
							if (messDesign != null && messDesign.getMessageString() != null && !messDesign.getMessageString().isEmpty()) {

								String itemName = "    " + messDesign.getMessageString();
								dataList = Arrays.asList(itemIndex + "", itemName, "", "", "", "", "", "", "");
								dataLists.add(dataList);
								itemIndex++;
								hasAreaTitle = true;
							}
						}
						for (ScreenItem screenItem : screenArea.getListItems()) {

							if (DbDomainConst.LogicDataType.BUTTON.equals(screenItem.getLogicalDataType()) || DbDomainConst.LogicDataType.LINK.equals(screenItem.getLogicalDataType())) {

								if (screenItem.getScreenAction() != null) {
									screenActions.add(screenItem.getScreenAction());
								}

							}
							MessageDesign messDesign = screenItem.getMessageDesign();
							if (messDesign != null && messDesign.getMessageString() != null && !messDesign.getMessageString().isEmpty()) {

								String itemName = "    " + messDesign.getMessageString();
								if (hasAreaTitle)
									itemName = "    " + itemName;
								Integer logicalDataType = screenItem.getLogicalDataType();
								Integer itemType = screenItem.getItemType();
								String logicalItemType = getLogicalItemType(logicalDataType, itemType);
								String ioValue = getIOItemType(logicalDataType, itemType);
								AutocompleteDesign autoDesign = screenItem.getAutocompleteDesign();
								String derivationSource = "";
								String remarks = "";
								if (autoDesign != null) {
									derivationSource = autoDesign.getAutocompleteCode();
									remarks = autoDesign.getAutocompleteName();
								}
								dataList = Arrays.asList(itemIndex + "", itemName, logicalItemType, ioValue, "", "", "", derivationSource, remarks);
								dataLists.add(dataList);
								itemIndex++;
							}
						}
					}
				}
			}

			sheet.shiftRows(5, 8, dataLists.size());

			// Add Screen Items
			for (List<String> dataList : dataLists) {

				List<Integer> positionList = Arrays.asList(1, 15, 22, 24, 28, 32, 38, 45, 61);
				List<CellStyle> styleList = Arrays.asList(alignRightCellStyle, alignLeftCellStyle, defaultCellStyle, defaultCellStyle, defaultCellStyle, defaultCellStyle, defaultCellStyle, defaultCellStyle, defaultCellStyle);
				writeRowScreenItems(wb, sheet, rowIndex, positionList, dataList, styleList);
				rowIndex++;
			}

			// Add Screen Action detail
			rowIndex = rowIndex + 3;
			int actionIndex = 1;
			for (ScreenAction screenAtion : screenActions) {

				String itemNo = actionIndex + "";
				String processingId = screenAtion.getToBlogicCode();
				String processingName = screenAtion.getToBlogicText();
				String processingTimings = screenAtion.getConnectionMsg();
				String overview = "";

				List<Integer> positionList = Arrays.asList(1, 7, 14, 28, 61);
				List<String> dataList = Arrays.asList(itemNo, processingId, processingName, processingTimings, overview);
				List<CellStyle> styleList = Arrays.asList(alignRightCellStyle, alignLeftCellStyle, alignLeftCellStyle, alignLeftCellStyle, alignLeftCellStyle);

				writeRowScreenItems(wb, sheet, rowIndex, positionList, dataList, styleList);
				actionIndex++;
				rowIndex++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void processGenerateEDDocumentScreenStatusSheet(GenerateDocumentItem item, Workbook wb, Sheet sheet) {

		try {

			// Process multiple language of template sheet
			GenerateDocumentUtilsQP.processMultiLanguageForTemplate(sheet);

			ScreenDesign screenDesign = (ScreenDesign) item.getData();

			// Generate data for header
			Row rowHeader = sheet.getRow(1);
			Cell businessNameCell = rowHeader.getCell(17);
			Cell funcNamecell = rowHeader.getCell(24);
			Cell screenNamecell = rowHeader.getCell(31);

			businessNameCell.setCellValue("");
			funcNamecell.setCellValue(screenDesign.getFunctionDesignName());
			screenNamecell.setCellValue(getMessageStringFromScreen(screenDesign));
			GenerateDocumentUtilsQP.headerGenerateInformation(rowHeader, Arrays.asList(11, 38, 44, 50, 56));

			CellStyle defaultCellStyle = wb.createCellStyle();
			defaultCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			defaultCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			defaultCellStyle.setFillBackgroundColor(HSSFColor.WHITE.index);

			CellStyle alignRightCellStyle = wb.createCellStyle();
			alignRightCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
			alignRightCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			alignRightCellStyle.setFillBackgroundColor(HSSFColor.WHITE.index);

			CellStyle alignLeftCellStyle = wb.createCellStyle();
			alignLeftCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			alignLeftCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			alignLeftCellStyle.setFillBackgroundColor(HSSFColor.WHITE.index);

			CellStyle headerCellStyle = wb.createCellStyle();
			headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			headerCellStyle.setFillBackgroundColor(HSSFColor.BROWN.index);

			// write data
			int rowIndex = 9;
			// Setting body information

			int itemIndex = 1;

			for (Object obj : screenDesign.getScreenForms()) {

				ScreenForm screenForm = (ScreenForm) obj;

				List<FormulaDefinition> formulaDefinitions = screenForm.getFormulaDefinition();
				List<List<String>> dataLists = new ArrayList<List<String>>();
				for (int i = 0; i < formulaDefinitions.size(); i++) {
					Row row = sheet.getRow(rowIndex);
					if (row == null) {
						row = sheet.createRow(rowIndex);
					}
					Cell cell = row.getCell(17 + i * 9);
					if (cell == null) {
						cell = row.createCell(17 + i * 9);
						cell.setCellStyle(defaultCellStyle);
						CellRangeAddress cellRange = new CellRangeAddress(rowIndex, rowIndex, 17 + i * 9, 25 + i * 9);
						sheet.addMergedRegion(cellRange);
						settingStyleForCellHadMerge(0, cellRange, sheet, wb);
					}

					if (i == 0) {
						cell.setCellValue("Initial display");
					} else {
						cell.setCellValue(formulaDefinitions.get(i).getFormulaName());
					}
				}
				rowIndex++;
				for (ScreenArea screenArea : screenForm.getListScreenAreas()) {
					if (screenArea.getListItems() != null) {
						boolean hasAreaTitle = false;
						// add area name if number item > 1
						if (screenArea.getListItems().size() > 0) {
							MessageDesign messDesign = screenArea.getMessageDesign();
							if (messDesign != null && messDesign.getMessageString() != null && !messDesign.getMessageString().isEmpty()) {

								String itemName = "    " + messDesign.getMessageString();
								List<String> dataList = new ArrayList<String>();
								dataList.add(itemIndex + "");
								dataList.add(itemName);
								for (int i = 0; i < formulaDefinitions.size(); i++) {
									boolean f = true;
									for (ScreenItemStatus screenItemStatus : screenArea.getScreenItemStatusLst()) {
										if (screenItemStatus.getFormulaDefinitionId().equals(formulaDefinitions.get(i).getFormulaDefinitionId())) {
											if (screenItemStatus.getEnabled() != null && screenItemStatus.getEnabled()) {
												if (screenItemStatus.getStatus() == 0) {
													dataList.add("No display");
													f = false;
												} else {
													dataList.add("Display");
													f = false;
												}
											} else {
												dataList.add("");
												f = false;
											}
											break;
										}
									}
									if (f) {
										dataList.add("");
									}
								}

								dataLists.add(dataList);
								itemIndex++;
								hasAreaTitle = true;
							}
						}
						for (ScreenItem screenItem : screenArea.getListItems()) {

							MessageDesign messDesign = screenItem.getMessageDesign();
							if (messDesign != null && messDesign.getMessageString() != null && !messDesign.getMessageString().isEmpty()) {

								String itemName = "    " + messDesign.getMessageString();
								if (hasAreaTitle)
									itemName = "    " + itemName;

								List<String> dataList = new ArrayList<String>();
								dataList.add(itemIndex + "");
								dataList.add(itemName);
								for (int i = 0; i < formulaDefinitions.size(); i++) {
									if (screenItem.getItemType() == DbDomainConst.ScreenItemType.HIDDEN_ITEM) {
										dataList.add("No display");
									} else {
										boolean f = true;
										for (ScreenItemStatus screenItemStatus : screenItem.getScreenItemStatusLst()) {
											if (screenItemStatus.getFormulaDefinitionId().equals(formulaDefinitions.get(i).getFormulaDefinitionId())) {
												if (screenItemStatus.getEnabled() != null && screenItemStatus.getEnabled()) {
													if (screenItemStatus.getStatus() == 0) {
														dataList.add("No display");
														f = false;
													}
													if (screenItemStatus.getStatus() == 1) {
														dataList.add("Display");
														f = false;
													} else {
														dataList.add("Read only");
														f = false;
													}
												} else {
													dataList.add("");
													f = false;
												}
												break;
											}
										}
										if (f) {
											dataList.add("");
										}
									}
								}

								dataLists.add(dataList);
								itemIndex++;
							}
						}
					}
				}

				int minCols = 5;
				List<Integer> positionList = new ArrayList<Integer>();
				List<CellStyle> styleList = new ArrayList<CellStyle>();
				positionList.add(1);
				positionList.add(16);
				styleList.add(alignRightCellStyle);
				styleList.add(alignLeftCellStyle);
				for (int i = 0; i < formulaDefinitions.size(); i++) {
					positionList.add(16 + (i + 1) * 9);
					styleList.add(defaultCellStyle);
				}
				for (int i = formulaDefinitions.size(); i < minCols; i++) {
					positionList.add(16 + (i + 1) * 9);
					styleList.add(defaultCellStyle);
				}
				// Add Screen Items
				for (List<String> dataList : dataLists) {
					writeRowScreenItems(wb, sheet, rowIndex, positionList, dataList, styleList);
					rowIndex++;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getLogicalItemType(Integer logicalDataType, Integer itemType) {

		String logicalItemType = "";
		if (itemType == DbDomainConst.ScreenItemType.HIDDEN_ITEM) {
			logicalItemType = "Hidden input";
		} else {
			switch (logicalDataType) {
			case DbDomainConst.LogicDataTypePrimitive.AUTOCOMPLETE:
				logicalItemType = MessageUtils.getMessage("sc.screendesign.0041");
				break;
			case DbDomainConst.LogicDataTypePrimitive.NAME:
			case DbDomainConst.LogicDataTypePrimitive.INTEGER:
			case DbDomainConst.LogicDataTypePrimitive.CODE:
			case DbDomainConst.LogicDataTypePrimitive.CURRENCY:
			case DbDomainConst.LogicDataTypePrimitive.DECIMAL:
			case DbDomainConst.LogicDataTypePrimitive.EMAIL:
			case DbDomainConst.LogicDataTypePrimitive.PHONE:
				logicalItemType = MessageUtils.getMessage("sc.screendesign.0182");
				break;
			case DbDomainConst.LogicDataTypePrimitive.BOOLEAN:
				logicalItemType = "";
				break;
			case DbDomainConst.LogicDataTypePrimitive.LINK:
				logicalItemType = MessageUtils.getMessage("sc.screendesign.0039");
				break;
			case DbDomainConst.LogicDataTypePrimitive.BUTTON:
				logicalItemType = MessageUtils.getMessage("sc.screendesign.0128");
				break;
			case DbDomainConst.LogicDataTypePrimitive.CHECKBOX:
				logicalItemType = MessageUtils.getMessage("sc.screendesign.0047");
				break;
			case DbDomainConst.LogicDataTypePrimitive.DATE:
			case DbDomainConst.LogicDataTypePrimitive.DATETIME:
			case DbDomainConst.LogicDataTypePrimitive.TIME:
				logicalItemType = MessageUtils.getMessage("sc.screendesign.0407");
				break;
			case DbDomainConst.LogicDataTypePrimitive.FILEUPLOAD:
				logicalItemType = MessageUtils.getMessage("sc.screendesign.0043");
				break;
			case DbDomainConst.LogicDataTypePrimitive.LABEL:
				logicalItemType = "";
				break;
			case DbDomainConst.LogicDataTypePrimitive.RADIO:
				logicalItemType = MessageUtils.getMessage("sc.screendesign.0049");
				break;
			case DbDomainConst.LogicDataTypePrimitive.REMARK:
				logicalItemType = MessageUtils.getMessage("sc.screendesign.0408");
				break;
			case DbDomainConst.LogicDataTypePrimitive.SELECT:
				logicalItemType = MessageUtils.getMessage("sc.screendesign.0409");
				break;
			case DbDomainConst.LogicDataTypePrimitive.TEXT:
				logicalItemType = MessageUtils.getMessage("sc.screendesign.0410");
				break;
			}
		}
		return logicalItemType;
	}

	public static String getIOItemType(Integer logicalDataType, Integer itemType) {

		String ioItemType = "";
		switch (itemType) {
		case DbDomainConst.ScreenItemType.NORMAL_ITEM:
			switch (logicalDataType) {
			case DbDomainConst.LogicDataTypePrimitive.AUTOCOMPLETE:
			case DbDomainConst.LogicDataTypePrimitive.NAME:
			case DbDomainConst.LogicDataTypePrimitive.INTEGER:
			case DbDomainConst.LogicDataTypePrimitive.CODE:
			case DbDomainConst.LogicDataTypePrimitive.CURRENCY:
			case DbDomainConst.LogicDataTypePrimitive.DECIMAL:
			case DbDomainConst.LogicDataTypePrimitive.EMAIL:
			case DbDomainConst.LogicDataTypePrimitive.PHONE:
			case DbDomainConst.LogicDataTypePrimitive.BOOLEAN:
			case DbDomainConst.LogicDataTypePrimitive.CHECKBOX:
			case DbDomainConst.LogicDataTypePrimitive.DATE:
			case DbDomainConst.LogicDataTypePrimitive.DATETIME:
			case DbDomainConst.LogicDataTypePrimitive.TIME:
			case DbDomainConst.LogicDataTypePrimitive.FILEUPLOAD:
			case DbDomainConst.LogicDataTypePrimitive.RADIO:
			case DbDomainConst.LogicDataTypePrimitive.REMARK:
			case DbDomainConst.LogicDataTypePrimitive.SELECT:
				ioItemType = "I";
				break;
			case DbDomainConst.LogicDataTypePrimitive.LABEL:
			case DbDomainConst.LogicDataTypePrimitive.TEXT:
				ioItemType = "O";
				break;
			case DbDomainConst.LogicDataTypePrimitive.LINK:
			case DbDomainConst.LogicDataTypePrimitive.BUTTON:
				ioItemType = "-";
				break;

			}
			break;
		case DbDomainConst.ScreenItemType.HIDDEN_ITEM:
			ioItemType = "I";
			break;
		}
		return ioItemType;
	}

	private static void settingStyleForCellHadMerge(int style, CellRangeAddress region, Sheet sheet, Workbook wb) {

		switch (style) {
		case 1:
			break;
		default:
			final short borderMediumDashed = CellStyle.BORDER_THIN;
			// Setting style of border
			RegionUtil.setBorderBottom(borderMediumDashed, region, sheet, wb);
			RegionUtil.setBorderTop(borderMediumDashed, region, sheet, wb);
			RegionUtil.setBorderLeft(borderMediumDashed, region, sheet, wb);
			RegionUtil.setBorderRight(borderMediumDashed, region, sheet, wb);
			// Setting color of border
			RegionUtil.setBottomBorderColor(IndexedColors.BLACK.getIndex(), region, sheet, wb);
			RegionUtil.setTopBorderColor(IndexedColors.BLACK.getIndex(), region, sheet, wb);
			RegionUtil.setLeftBorderColor(IndexedColors.BLACK.getIndex(), region, sheet, wb);
			RegionUtil.setRightBorderColor(IndexedColors.BLACK.getIndex(), region, sheet, wb);

			break;
		}
	}
	
	
	private static String getMessageStringFromScreen(ScreenDesign screenDesign) {
		if (screenDesign == null || screenDesign.getMessageDesign() == null)
			return StringUtils.EMPTY;

		return screenDesign.getMessageDesign().getMessageString();
	}
}
