package org.terasoluna.qp.domain.service.importmanagement;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CodelistSheetHandler extends DefaultHandler {
	private SharedStringsTable sst;
	private String lastContents;
	private boolean nextIsString;
	private int row = 0;
	private int column = 0;
	private String[] rowDataSummary = new String[5];
	private String[] rowDataDetail;
	private List<String[]> inputDataSummary = new ArrayList<String[]>();
	private List<String[]> inputDataDetail = new ArrayList<String[]>();
	
	private final int CODELIST_SUMMARY = 13;
	private final int KEY = 2;
	private final int VALUE = 13;
	private final int VALUE1 = 31;
	private final int VALUE2 = 38;
	private final int VALUE3 = 45;
	private final int VALUE4 = 52;
	private final int VALUE5 = 59;
	
	private final String ELEMENT_CELL = "c";
	private final String ELEMENT_ROW = "row";
	private final String ATTRIBUTE_TYPE = "t";
	
	private final int CODELIST_NAME = 2;
	private final int CODELIST_CODE = 3;
	private final int VALUES_ONLY = 4;
	private final int MODULE_NAME = 5;
	private final int DESCRIPTION = 6;
	private final int COLLECTION_START_ROW = 9;
	
	public CodelistSheetHandler(SharedStringsTable sst) {
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
		
		// If element is "row", and row is 12 then initialize new rowDataDetail 
		if(name.equals(ELEMENT_ROW)) {
			rowDataDetail = new String[7];
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
		// If element is "c" and row index is 11, assign string to array
		if(name.equals(ELEMENT_CELL) && row >= COLLECTION_START_ROW) {
			switch (column) {
			case KEY:
			     rowDataDetail[0] = lastContents;
			     break;
			case VALUE:
			     rowDataDetail[1] = lastContents;
			     break;
			case VALUE1:
			     rowDataDetail[2] = lastContents;
			     break;
			case VALUE2:
			     rowDataDetail[3] = lastContents;
			     break;
			case VALUE3:
			     rowDataDetail[4] = lastContents;
			     break;
			case VALUE4:
			     rowDataDetail[5] = lastContents;
			     break;
			case VALUE5:
			     rowDataDetail[6] = lastContents;
			     break;
			}
			column++;
		}
		// If element is "c"
		if(name.equals(ELEMENT_CELL) && row >= CODELIST_NAME && row <= DESCRIPTION) {
			if(row == CODELIST_NAME && column == CODELIST_SUMMARY) {
				rowDataSummary[0] = lastContents;
			} else if(row == CODELIST_CODE && column == CODELIST_SUMMARY) {
				rowDataSummary[1] = lastContents;
			} else if(row == VALUES_ONLY && column == CODELIST_SUMMARY) {
				rowDataSummary[2] = lastContents;
			} else if(row == MODULE_NAME && column == CODELIST_SUMMARY) {
				rowDataSummary[3] = lastContents;
			} else if(row == DESCRIPTION && column == CODELIST_SUMMARY) {
				rowDataSummary[4] = lastContents;
			}
			column++;
		}
		// If element is "row", add string array to string list, increase row index and reset column index
		if(name.equals(ELEMENT_ROW)) {
			if(row == COLLECTION_START_ROW) {
				inputDataSummary.add(rowDataSummary);
			}
			
			if(row >= COLLECTION_START_ROW) {
				inputDataDetail.add(rowDataDetail);
			}
			row++;
			column = 0;
		}
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		lastContents += new String(ch, start, length);
	}

	public List<String[]> getInputDataSummary() {
		return inputDataSummary;
	}

	public void setInputDataSummary(List<String[]> inputDataSummary) {
		this.inputDataSummary = inputDataSummary;
	}

	public List<String[]> getInputDataDetail() {
		return inputDataDetail;
	}

	public void setInputDataDetail(List<String[]> inputDataDetail) {
		this.inputDataDetail = inputDataDetail;
	}
}
