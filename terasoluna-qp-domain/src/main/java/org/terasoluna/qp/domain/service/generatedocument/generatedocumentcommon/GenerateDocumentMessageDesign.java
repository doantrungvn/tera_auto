package org.terasoluna.qp.domain.service.generatedocument.generatedocumentcommon;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.domain.model.LanguageDesign;
import org.terasoluna.qp.domain.model.MessageDesign;
import org.terasoluna.qp.domain.service.common.XmlUtils;
import org.terasoluna.qp.domain.service.generatedocument.GenerateDocumentConst;
import org.terasoluna.qp.domain.service.generatedocument.spreadsheet.SpreadsheetMessageDesign;


public class GenerateDocumentMessageDesign extends GenerateDocumentBase {
	public static void generateDatasource(Writer out, List<?> listOfLanguageDesign) throws Exception {
		// Get style
		int dtSourceTitle = styles.get("dtSourceTitle").getIndex();
		int dtSourceHeader = styles.get("dtSourceHeader").getIndex();
		int dtSourceContent = styles.get("dtSourceContent").getIndex();
		
		SpreadsheetMessageDesign sw = new SpreadsheetMessageDesign(out);
		sw.beginSheet();

		// Insert title
		sw.insertRow(2);
		sw.createCell( 0,  XmlUtils.xmlEscapeText(MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.SOURCE)), dtSourceTitle);
		sw.createCell( 4, XmlUtils.xmlEscapeText(MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.OUTPUT_LOCATION)), dtSourceTitle);
		sw.createCell( 8, XmlUtils.xmlEscapeText(MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.ERROR_LEVEL)), dtSourceTitle);
		sw.createCell( 12, XmlUtils.xmlEscapeText(MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.LANGUAGE)), dtSourceTitle);
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

		Map<String, List<String[]>> mapData = prepareData(listOfLanguageDesign);
		List<String[]> listSource = mapData.get("listSource");
		List<String[]> listOutputLocation = mapData.get("listOutputLocation");
		List<String[]> listErrorLevel = mapData.get("listErrorLevel");
		List<String[]> listLanguage = mapData.get("listLanguage");

		// Get max row
		int maxRow = (listOutputLocation.size() > listLanguage.size()) ? listOutputLocation.size()
				: listLanguage.size();

		for (int rownum = 4; rownum < (maxRow + 4); rownum++) {
			sw.insertRow(rownum);

			if (listSource.size() >= rownum - 3) {
				if (listSource.get(rownum - 4) != null) {
					sw.createCell(1, Double.parseDouble(listSource.get(rownum - 4)[0]), dtSourceContent);
					sw.createCell(2, XmlUtils.xmlEscapeText(listSource.get(rownum - 4)[1]), dtSourceContent);
				}
			}
			if (listOutputLocation.size() >= rownum - 3) {
				if (listOutputLocation.get(rownum - 4) != null) {
					sw.createCell(5, Double.parseDouble(listOutputLocation.get(rownum - 4)[0]), dtSourceContent);
					sw.createCell(6, XmlUtils.xmlEscapeText(listOutputLocation.get(rownum - 4)[1]), dtSourceContent);
				}
			}
			if (listErrorLevel.size() >= rownum - 3) {
				if (listErrorLevel.get(rownum - 4) != null) {
					sw.createCell(9, listErrorLevel.get(rownum - 4)[0], dtSourceContent);
					sw.createCell(10, XmlUtils.xmlEscapeText(listErrorLevel.get(rownum - 4)[1]), dtSourceContent);
				}
			}
			if (listLanguage.size() >= rownum - 3) {
				if (listLanguage.get(rownum - 4) != null) {
					sw.createCell(13, Double.parseDouble(listLanguage.get(rownum - 4)[0]), dtSourceContent);
					sw.createCell(14, XmlUtils.xmlEscapeText(listLanguage.get(rownum - 4)[1]), dtSourceContent);
				}
			}
			sw.endRow();
		}
		
		sw.endSheet();
	}
	
	public static void generate(Writer out, List<?> listOfData, String[] headerString, int numOfLanguage) throws Exception {
		SpreadsheetMessageDesign sw = new SpreadsheetMessageDesign(out);
		sw.beginSheet(100);
		
		// Generate header and title
		generateMessageDesignHeader(sw, headerString);
		generateMessageTitle(sw, getMessageDesignTitleString());
		
		// Generate content
		generateMessageDesignContent(sw, listOfData);
		
		// end sheet with merge information
		sw.endSheetWithMoreAction(listOfData.size(),numOfLanguage);
	}
	
	/**
	 * prepare data for data source sheet
	 * 
	 * @param listOfLanguageDesign
	 * @return
	 */
	private static Map<String, List<String[]>> prepareData(List<?> listOfLanguageDesign) {
		Map<String, List<String[]>> mapData = new HashMap<String, List<String[]>>();

		List<String[]> listSource = new ArrayList<String[]>();
		List<String[]> listOutputLocation = new ArrayList<String[]>();
		List<String[]> listErrorLevel = new ArrayList<String[]>();
		List<String[]> listLanguage = new ArrayList<String[]>();

		// Add source
		String[] source1 = new String[2];
		source1[0] = "0";
		source1[1] = MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.CLIENT);
		listSource.add(source1);
		String[] source2 = new String[2];
		source2[0] = "1";
		source2[1] = MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.SERVER);
		listSource.add(source2);

		// Output location
		String[] outputLocation1 = new String[2];
		outputLocation1[0] = "0";
		outputLocation1[1] = MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.PROJECT);
		listOutputLocation.add(outputLocation1);
		String[] outputLocation2 = new String[2];
		outputLocation2[0] = "1";
		outputLocation2[1] = MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.MODULE);
		listOutputLocation.add(outputLocation2);
		String[] outputLocation3 = new String[2];
		outputLocation3[0] = "2";
		outputLocation3[1] = MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.SCREEN);
		listOutputLocation.add(outputLocation3);
		String[] outputLocation4 = new String[2];
		outputLocation4[0] = "3";
		outputLocation4[1] = MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.SCREEN_AREA);;
		listOutputLocation.add(outputLocation4);
		String[] outputLocation5 = new String[2];
		outputLocation5[0] = "4";
		outputLocation5[1] = MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.SCREEN_ITEM);
		listOutputLocation.add(outputLocation5);
		String[] outputLocation6 = new String[2];
		outputLocation6[0] = "5";
		outputLocation6[1] = MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.BLOGIC);
		listOutputLocation.add(outputLocation6);
		String[] outputLocation7 = new String[2];
		outputLocation7[0] = "6";
		outputLocation7[1] = MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.MENU_DESIGN);
		listOutputLocation.add(outputLocation7);
		String[] outputLocation8 = new String[2];
		outputLocation8[0] = "7";
		outputLocation8[1] = MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.DESIGN_INFORMATION);
		listOutputLocation.add(outputLocation8);

		// Error level
		String[] errorLevel1 = new String[2];
		errorLevel1[0] = "sc";
		errorLevel1[1] =  MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.LABEL);
		listErrorLevel.add(errorLevel1);
		String[] errorLevel2 = new String[2];
		errorLevel2[0] = "inf";
		errorLevel2[1] =  MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.INFORMATION);
		listErrorLevel.add(errorLevel2);
		String[] errorLevel3 = new String[2];
		errorLevel3[0] = "wrn";
		errorLevel3[1] =  MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.WARNING);
		listErrorLevel.add(errorLevel3);
		String[] errorLevel4 = new String[2];
		errorLevel4[0] = "err";
		errorLevel4[1] =  MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.ERROR);
		listErrorLevel.add(errorLevel4);

		for (Object languageDesign : listOfLanguageDesign) {
			String[] language = new String[2];
			language[0] = ((LanguageDesign) languageDesign).getLanguageId().toString();
			language[1] = ((LanguageDesign) languageDesign).getLanguageName();
			listLanguage.add(language);
		}

		mapData.put("listSource", listSource);
		mapData.put("listOutputLocation", listOutputLocation);
		mapData.put("listErrorLevel", listErrorLevel);
		mapData.put("listLanguage", listLanguage);

		return mapData;
	}
	
	/**
	 * Generate common header
	 * 
	 * @param sheet
	 * @param titleString
	 * @throws IOException 
	 */
	public static void generateMessageDesignHeader(SpreadsheetMessageDesign sw, String[] titleString) throws IOException {
		// Get style
		int headerBold = styles.get("headerBold").getIndex();
		int headerNormal = styles.get("headerNormal").getIndex();
		// 1st line
		sw.insertRow(0);
		sw.createCell( 0, XmlUtils.xmlEscapeText(titleString[0]), headerBold);
		sw.createEmptyCells( 1, 10, headerBold);

		sw.createCell( 11, XmlUtils.xmlEscapeText(titleString[1]), headerBold);
		sw.createEmptyCells( 12, 19, headerBold);

		sw.createCell( 20, "", headerBold);
		sw.createEmptyCells( 21, 28, headerBold);

		sw.createCell( 29, "", headerBold);
		sw.createEmptyCells( 30, 37, headerBold);

		sw.createCell( 38, XmlUtils.xmlEscapeText(titleString[2]), headerBold);
		sw.createEmptyCells( 39, 43, headerBold);

		sw.createCell( 44, XmlUtils.xmlEscapeText(titleString[3]), headerBold);
		sw.createEmptyCells( 45, 49, headerBold);

		sw.createCell( 50, XmlUtils.xmlEscapeText(titleString[4]), headerBold);
		sw.createEmptyCells( 51, 56, headerBold);

		sw.createCell( 57, XmlUtils.xmlEscapeText(titleString[5]), headerBold);
		sw.createEmptyCells( 58, 63, headerBold);

		sw.endRow();
		// 2nd line
		sw.insertRow(1);
		sw.createEmptyCells( 0, 10, headerBold);

		sw.createCell( 11, XmlUtils.xmlEscapeText(titleString[6]), headerNormal);
		sw.createEmptyCells( 12, 19, headerNormal);

		sw.createCell( 20, "", headerNormal);
		sw.createEmptyCells( 21, 28, headerNormal);

		sw.createCell( 29, "", headerNormal);
		sw.createEmptyCells( 30, 37, headerNormal);

		sw.createCell( 38, XmlUtils.xmlEscapeText(titleString[7]), headerNormal);
		sw.createEmptyCells( 39, 43, headerNormal);

		sw.createCell( 44, XmlUtils.xmlEscapeText(titleString[8]), headerNormal);
		sw.createEmptyCells( 45, 49, headerNormal);

		sw.createCell( 50, XmlUtils.xmlEscapeText(titleString[9]), headerNormal);
		sw.createEmptyCells( 51, 56, headerNormal);

		sw.createCell( 57, XmlUtils.xmlEscapeText(titleString[10]), headerNormal);
		sw.createEmptyCells( 58, 63, headerNormal);
		
		sw.endRow();

	}
	
	/**
	 * Generate message title for message design information sheet
	 * 
	 * @param sheet
	 * @param titleString
	 * @throws IOException 
	 */
	public static void generateMessageTitle(SpreadsheetMessageDesign sw, String[] titleString) throws IOException {
		int messageTitle = styles.get("messageTitle").getIndex();
		sw.insertRow(3);

		// Create cells
		sw.createCell( 0, XmlUtils.xmlEscapeText(titleString[0]), messageTitle);
		sw.createEmptyCells( 1, 1, messageTitle);

		sw.createCell( 2, XmlUtils.xmlEscapeText(titleString[1]), messageTitle);
		sw.createEmptyCells( 3, 13, messageTitle);

		sw.createCell( 14, XmlUtils.xmlEscapeText(titleString[2]), messageTitle);
		sw.createEmptyCells( 15, 18, messageTitle);

		sw.createCell( 19, XmlUtils.xmlEscapeText(titleString[3]), messageTitle);
		sw.createEmptyCells( 20, 23, messageTitle);

		sw.createCell( 24, XmlUtils.xmlEscapeText(titleString[4]), messageTitle);
		sw.createEmptyCells( 25, 28, messageTitle);

		sw.createCell( 29, XmlUtils.xmlEscapeText(titleString[5]), messageTitle);
		sw.createEmptyCells( 30, 49, messageTitle);

		sw.createCell( 50, XmlUtils.xmlEscapeText(titleString[6]), messageTitle);
		sw.createEmptyCells( 51, 61, messageTitle);

		sw.createCell( 62, XmlUtils.xmlEscapeText(titleString[7]), messageTitle);
		sw.createEmptyCells( 63, 63, messageTitle);
		
		sw.endRow();
	}
	
	/**
	 * Generate message design title strings
	 * @return
	 */
	public static String[] getMessageDesignTitleString(){
		String[] titleString = new String[8];
		titleString[0] =  MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.ITEM_NO);
		titleString[1] =  MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.ID);
		titleString[2] =  MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.SOURCE);
		titleString[3] =  MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.OUTPUT_LOCATION);
		titleString[4] =  MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.ERROR_LEVEL);
		titleString[5] =  MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.MESSAGE_STRING);
		titleString[6] =  MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.DESCRIPTION);
		titleString[7] =  MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.LANGUAGE);
		return titleString;
	}
	
	public static void generateMessageDesignContent(SpreadsheetMessageDesign sw, List<?> listOfData) throws IOException{
		if(listOfData != null){
			int indexRow = 4;
			for (Object obj : listOfData) {
 				MessageDesign messageDesign = (MessageDesign) obj;

 				int messageContent = styles.get("messageContent").getIndex();
 				
 				sw.insertRow(indexRow);
 				sw.createCell( 0, (indexRow - 4 + 1), messageContent);
 				sw.createEmptyCells( 1, 1, messageContent);

 				sw.createCell( 2, messageDesign.getMessageCode(), messageContent);
 				sw.createEmptyCells( 3, 13, messageContent);

 				String source = StringUtils.EMPTY;
 				if (GenerateDocumentConst.LABEL.equals(messageDesign.getMessageType())) {
 					source = MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.CLIENT);
 				} else {
 					source = MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.SERVER);
 				}
 				sw.createCell( 14, XmlUtils.xmlEscapeText(source), messageContent);
 				sw.createEmptyCells( 15, 18, messageContent);

 				sw.createCell( 19, XmlUtils.xmlEscapeText(MessageUtils.getMessage(GenerateDocumentConst.MessageDesign.SCREEN)), messageContent);
 				sw.createEmptyCells( 20, 23, messageContent);

 				sw.createCell( 24, XmlUtils.xmlEscapeText(messageDesign.getMessageTypeName()), messageContent);
 				sw.createEmptyCells( 25, 28, messageContent);
 				
 				sw.createCell( 29, XmlUtils.xmlEscapeText(StringUtils.defaultString(messageDesign.getMessageString(), StringUtils.EMPTY)), messageContent);
 				sw.createEmptyCells( 30, 49, messageContent);

 				sw.createCell( 50, XmlUtils.xmlEscapeText(StringUtils.defaultString(messageDesign.getRemark(), StringUtils.EMPTY)), messageContent);
 				sw.createEmptyCells( 51, 61, messageContent);
 				
 				sw.createCell( 62, messageDesign.getLanguageName(), messageContent);
 				sw.createEmptyCells( 63, 63, messageContent);
 				
 				sw.endRow();
 				indexRow++;
            }
		}
	}
}
