package org.terasoluna.qp.domain.service.importmanagement;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TableDesignSheetHandler extends DefaultHandler {
	private SharedStringsTable sst;
	private String lastContents;
	private boolean nextIsString;
	private int row = 0;
	private int column = 0;
	private String[] rowTableInfo;
	private String[] rowTableDetail;
	private List<String[]> inputTableInfo = new ArrayList<String[]>();
	private List<String[]> inputTableDetail = new ArrayList<String[]>();
	
	private final String ELEMENT_CELL = "c";
	private final String ELEMENT_ROW = "row";
	private final String ATTRIBUTE_TYPE = "t";
	
	private final int TABLE_INFO_START = 2;
	private final int TABLE_INFO_END = 8;
	private final int TABLE_DETAIL_START = 11;
	
	// Index for table information
	private final int INFO_VALUE = 13;
	
	// Index for table details
	private final int ITEM_NO = 0;
	private final int ATTRIBUTE = 2;
	private final int COLUMN_NAME = 10;
	private final int PRIMARY_KEY = 18;
	private final int ALTERNATE_KEY = 20;
	private final int FOREIGN_KEY = 22;
	private final int PARENT_ENTITY_NAME = 24;
	private final int PARENT_TABLE_NAME = 30;
	private final int PARENT_ATT_NAME = 36;
	private final int PARENT_COL_NAME = 42;
	private final int LOGIC_DATA_TYPE = 48;
	private final int DITGIT_CHARACTER = 51;
	private final int PHYSIC_DATA_TYPE = 54;
	private final int DATA_LENGTH = 57;
	private final int DOMAIN_NAME = 60;
	private final int DEFAULT_VALUE = 65;
	private final int UNIQUE_CONSTRAINT = 67;
	private final int NULL_VALUE = 69;
	private final int CHECK_CONSTRAINT = 71;
	private final int CONSTRAINT_VALUE = 73;
	private final int REMARK = 74;
		
	public TableDesignSheetHandler(SharedStringsTable sst) {
		this.sst = sst;
	}
	
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		// c => cell
		if(name.equals(ELEMENT_CELL)) {
			// Figure out if the value is an index in the SST
			String cellType = attributes.getValue(ATTRIBUTE_TYPE);
			if(cellType != null) {
				nextIsString = true;
			} else {
				nextIsString = false;
			}
		}
		
		// If element is "row", and row is TABLE_INFO_START then initialize new rowTableInfo
		if(name.equals(ELEMENT_ROW) && row == TABLE_INFO_START) {
			rowTableInfo = new String[7];
		}
		
		// If element is "row", and row is >= TABLE_DETAIL_START then initialize new rowTableDetail
		if(name.equals(ELEMENT_ROW) && row >= TABLE_DETAIL_START ) {
			rowTableDetail = new String[21];
		}
		
		// Clear contents cache
		lastContents = "";
	}
	
	public void endElement(String uri, String localName, String name) throws SAXException {
		// Process the last contents as required.
		// Do now, as characters() may be called more than once
		if(nextIsString) {
			int idx = Integer.parseInt(lastContents);
			lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
			nextIsString = false;
		}
		
		// If element is "c" and row index is in table info range, assign string to array
		if(name.equals(ELEMENT_CELL) && row >= TABLE_INFO_START && row <= TABLE_INFO_END) {
			if(column == INFO_VALUE){
				rowTableInfo[row - TABLE_INFO_START] = lastContents;
			}
			column++;
		}
		
		// If element is "c" row index is in table detail range, assign string to array
		if(name.equals(ELEMENT_CELL) && row >= TABLE_DETAIL_START) {
			switch (column) {
				case ITEM_NO:
				     rowTableDetail[0] = lastContents;
				     break;
				case ATTRIBUTE:
				     rowTableDetail[1] = lastContents;
				     break;
				case COLUMN_NAME:
				     rowTableDetail[2] = lastContents;
				     break;
				case PRIMARY_KEY:
				     rowTableDetail[3] = lastContents;
				     break;
				case ALTERNATE_KEY:
				     rowTableDetail[4] = lastContents;
				     break;
				case FOREIGN_KEY:
				     rowTableDetail[5] = lastContents;
				     break;
				case PARENT_ENTITY_NAME:
				     rowTableDetail[6] = lastContents;
				     break;
				case PARENT_TABLE_NAME:
				     rowTableDetail[7] = lastContents;
				     break;
				case PARENT_ATT_NAME:
				     rowTableDetail[8] = lastContents;
				     break;
				case PARENT_COL_NAME:
				     rowTableDetail[9] = lastContents;
				     break;
				case LOGIC_DATA_TYPE:
				     rowTableDetail[10] = lastContents;
				     break;
				case DITGIT_CHARACTER:
				     rowTableDetail[11] = lastContents;
				     break;
				case PHYSIC_DATA_TYPE:
				     rowTableDetail[12] = lastContents;
				     break;
				case DATA_LENGTH:
				     rowTableDetail[13] = lastContents;
				     break;
				case DOMAIN_NAME:
				     rowTableDetail[14] = lastContents;
				     break;
				case DEFAULT_VALUE:
				     rowTableDetail[15] = lastContents;
				     break;
				case UNIQUE_CONSTRAINT:
				     rowTableDetail[16] = lastContents;
				     break;
				case NULL_VALUE:
				     rowTableDetail[17] = lastContents;
				     break;
				case CHECK_CONSTRAINT:
				     rowTableDetail[18] = lastContents;
				     break;
				case CONSTRAINT_VALUE:
				     rowTableDetail[19] = lastContents;
				     break;
				case REMARK:
				     rowTableDetail[20] = lastContents;
				     break;
			}
			column++;
		}
		
		// If element is "row", add string array to string list, increase row index and reset column index
		if(name.equals(ELEMENT_ROW)) {
			if(row == TABLE_INFO_END) {
				inputTableInfo.add(rowTableInfo);
			}
			
			if(row >= TABLE_DETAIL_START) {
				inputTableDetail.add(rowTableDetail);
			}
			row++;
			column = 0;
		}
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		lastContents += new String(ch, start, length);
	}

	public List<String[]> getInputTableInfo() {
		return inputTableInfo;
	}

	public void setInputTableInfo(List<String[]> inputTableInfo) {
		this.inputTableInfo = inputTableInfo;
	}

	public List<String[]> getInputTableDetail() {
		return inputTableDetail;
	}

	public void setInputTableDetail(List<String[]> inputTableDetail) {
		this.inputTableDetail = inputTableDetail;
	}

	
}
