package org.terasoluna.qp.domain.service.importmanagement;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SheetHandler extends DefaultHandler {
	
	private SharedStringsTable sst;
	private String lastContents;
	private boolean nextIsString;
	private int row = 0;
	private int column = 0;
	private String[] rowData;
	private int totalColumn = 0;
	private final int COLUMN_ID = 2;
	private final int COLUMN_OUTPUT_LOCATION = 19;
	private final int COLUMN_ERROR_LEVEL = 24;
	private final int COLUMN_MESSAGE_STRING = 29;
	private final int COLUMN_DESCRIPTION = 50;
	private final int COLUMN_LANGUAGE = 62;
	private final String ELEMENT_CELL = "c";
	private final String ELEMENT_ROW = "row";
	private final String ELEMENT_MERGE_CELL = "mergeCell";
	private final String ATTRIBUTE_TYPE = "t";
	private final String ATTRIBUTE_TYPE_VALUE_STRING = "s";
	private final String ATTRIBUTE_REF = "ref";
	private final int COLLECTION_START_ROW = 2;
	private final String MERGE_CELL_ITEM_NO = "A4:B4";
	private final String MERGE_CELL_ID = "C4:N4";
	private final String MERGE_CELL_SOURCE = "O4:S4";
	private final String MERGE_CELL_OUTPUT_LOCATION = "T4:X4";
	private final String MERGE_CELL_ERROR_LEVEL = "Y4:AC4";
	private final String MERGE_CELL_MESSAGE_STRING = "AD4:AX4";
	private final String MERGE_CELL_DESCRIPTION = "AY4:BJ4";
	
	private List<String[]> inputData = new ArrayList<String[]>();
	
	SheetHandler(SharedStringsTable sst) {
		this.sst = sst;
	}
	
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		// c => cell
		if(name.equals(ELEMENT_CELL)) {
			// Figure out if the value is an index in the SST
			String cellType = attributes.getValue(ATTRIBUTE_TYPE);
			if(cellType != null && cellType.equals(ATTRIBUTE_TYPE_VALUE_STRING)) {
				nextIsString = true;
			} else {
				nextIsString = false;
			}
		}
		
		if(name.equals(ELEMENT_ROW)) {
			rowData = new String[6];
		}
		// Figure out input excel file has right format (number of columns)
		if(name.equals(ELEMENT_MERGE_CELL)) {
			String mergeCell = attributes.getValue(ATTRIBUTE_REF);
			if(mergeCell.equals(MERGE_CELL_ITEM_NO) || mergeCell.equals(MERGE_CELL_ID) || mergeCell.equals(MERGE_CELL_SOURCE) || mergeCell.equals(MERGE_CELL_OUTPUT_LOCATION)
					|| mergeCell.equals(MERGE_CELL_ERROR_LEVEL) || mergeCell.equals(MERGE_CELL_MESSAGE_STRING) || mergeCell.equals(MERGE_CELL_DESCRIPTION)) {
				totalColumn++;
			}
		}
		// Clear contents cache
		lastContents = "";
	}
	
	public void endElement(String uri, String localName, String name) throws SAXException {
//		if(totalColumn < TOTAL_COLUMN_MESSAGE_DESIGN_TEMPLATE) {
//			
//		} else {
			// Process the last contents as required.
			// Do now, as characters() may be called more than once
			if(nextIsString) {
				int idx = Integer.parseInt(lastContents);
				lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
				nextIsString = false;
			}
			// If element is "c" and row index is 4, assign string to array
			if(name.equals(ELEMENT_CELL) && row > COLLECTION_START_ROW) {
				switch (column) {
				case COLUMN_ID:
					rowData[0] = lastContents;
					break;
				case COLUMN_OUTPUT_LOCATION:
					rowData[1] = lastContents;
					break;
				case COLUMN_ERROR_LEVEL:
					rowData[2] = lastContents;
					break;
				case COLUMN_MESSAGE_STRING:
					rowData[3] = lastContents;
					break;
				case COLUMN_DESCRIPTION:
					rowData[4] = lastContents;
					break;
				case COLUMN_LANGUAGE:
					rowData[5] = lastContents;
					break;
				}
				column++;
			}
			
			// If element is "row", add string array to string list, increase row index and reset column index
			if(name.equals(ELEMENT_ROW)) {
				if(row > COLLECTION_START_ROW) {
					inputData.add(rowData);
				}
				row++;
				column = 0;
			}
//		}
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		lastContents += new String(ch, start, length);
	}

	public List<String[]> getInputData() {
		return inputData;
	}

	public void setInputData(List<String[]> inputData) {
		this.inputData = inputData;
	}
	
	
}