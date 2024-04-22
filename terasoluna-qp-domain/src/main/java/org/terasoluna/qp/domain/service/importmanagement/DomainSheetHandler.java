package org.terasoluna.qp.domain.service.importmanagement;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class DomainSheetHandler extends DefaultHandler {
	
	private SharedStringsTable sst;
	private String lastContents;
	private boolean nextIsString;
	private int row = 0;
	private int column = 0;
	private String[] rowData;
	private int totalColumn = 0;
	
	private final int MAJOR_CLASSIFICATION = 2;
	private final int SUB_CLASSIFICATION = 5;
	private final int MINOR_CLASSIFICATION = 8;
	private final int DOMAIN_NAME = 11;
	private final int PHYSICAL_DOMAIN_NAME = 17;
	//private final int LOGICAL_DATA_TYPE = 23;
	//private final int LOGICAL_DATA_LENGHT = 26;
	private final int PHYSICAL_DATA_TYPE = 28;
	private final int PHYSICAL_DATA_LENGHT = 31;
	private final int DESCRIPTION = 33;
	//private final int DESCRIPTION_FORMAT = 42;
	private final int MINIMUM_VALUE = 48;
	private final int MAXIMUM_VALUE = 52;
	private final int DEFAULT_VALUE = 56;
	private final int VALIDATION_RULE = 60;
	//private final int DATASOURCE_ID = 64;
	//private final int DATASOURCE_TYPE = 67;
	
	private final String ELEMENT_CELL = "c";
	private final String ELEMENT_ROW = "row";
	private final String ELEMENT_MERGE_CELL = "mergeCell";
	private final String ATTRIBUTE_TYPE = "t";
	private final String ATTRIBUTE_TYPE_VALUE_STRING = "s";
	private final String ATTRIBUTE_REF = "ref";
	private final int COLLECTION_START_ROW = 3;
	
	private final String MERGE_CELL_MAJOR_CLASSIFICATION = "C4:E5";
	private final String MERGE_CELL_SUB_CLASSIFICATION = "F4:H5";
	private final String MERGE_CELL_MINOR_CLASSIFICATION = "I4:K5";
	private final String MERGE_CELL_DOMAIN_NAME = "L4:Q5";
	private final String MERGE_CELL_PHYSICAL_DOMAIN_NAME = "R4:W5";
	//private final String MERGE_CELL_LOGICAL_DATA_TYPE = "X4:Z4";
	//private final String MERGE_CELL_LOGICAL_DATA_LENGHT = "AA4:AB4";
	private final String MERGE_CELL_PHYSICAL_DATA_TYPE = "AC4:AE5";
	private final String MERGE_CELL_PHYSICAL_DATA_LENGHT = "AF4:AG5";
	private final String MERGE_CELL_DESCRIPTION = "AH4:AP5";
	//private final String MERGE_CELL_DESCRIPTION_FORMAT = "AQ4:AV5";
	private final String MERGE_CELL_MINIMUM_VALUE = "AW4:AZ5";
	private final String MERGE_CELL_MAXIMUM_VALUE = "BA4:BD5";
	private final String MERGE_CELL_DEFAULT_VALUE = "BE4:BH5";
	private final String MERGE_CELL_VALIDATION_RULE = "BI4:BL5";
	//private final String MERGE_CELL_DATASOURCE_ID = "BM5:BO5";
	//private final String MERGE_CELL_DATASOURCE_TYPE = "BP5:BR5";

	
	
	
	private List<String[]> inputData = new ArrayList<String[]>();
	
	DomainSheetHandler(SharedStringsTable sst) {
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
			rowData = new String[12];
		}
		// Figure out input excel file has right format (number of columns)
		if(name.equals(ELEMENT_MERGE_CELL)) {
			String mergeCell = attributes.getValue(ATTRIBUTE_REF);
			if(		mergeCell.equals(MERGE_CELL_MAJOR_CLASSIFICATION) ||
					mergeCell.equals(MERGE_CELL_SUB_CLASSIFICATION) ||
					mergeCell.equals(MERGE_CELL_MINOR_CLASSIFICATION) ||
					mergeCell.equals(MERGE_CELL_DOMAIN_NAME) ||
					mergeCell.equals(MERGE_CELL_PHYSICAL_DOMAIN_NAME) ||
					//mergeCell.equals(MERGE_CELL_LOGICAL_DATA_TYPE) ||
					//mergeCell.equals(MERGE_CELL_LOGICAL_DATA_LENGHT) ||
					mergeCell.equals(MERGE_CELL_PHYSICAL_DATA_TYPE) ||
					mergeCell.equals(MERGE_CELL_PHYSICAL_DATA_LENGHT) ||
					mergeCell.equals(MERGE_CELL_DESCRIPTION) ||
					//mergeCell.equals(MERGE_CELL_DESCRIPTION_FORMAT) ||
					mergeCell.equals(MERGE_CELL_MINIMUM_VALUE) ||
					mergeCell.equals(MERGE_CELL_MAXIMUM_VALUE) ||
					mergeCell.equals(MERGE_CELL_DEFAULT_VALUE) ||
					mergeCell.equals(MERGE_CELL_VALIDATION_RULE) ||
					//mergeCell.equals(MERGE_CELL_DATASOURCE_ID) ||
					//mergeCell.equals(MERGE_CELL_DATASOURCE_TYPE) ||
					mergeCell.equals(MERGE_CELL_DESCRIPTION)) 
			{
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
				case MAJOR_CLASSIFICATION:
				     rowData[0] = lastContents;
				     break;
				case SUB_CLASSIFICATION:
				     rowData[1] = lastContents;
				     break;
				case MINOR_CLASSIFICATION:
				     rowData[2] = lastContents;
				     break;
				case DOMAIN_NAME:
				     rowData[3] = lastContents;
				     break;
				case PHYSICAL_DOMAIN_NAME:
				     rowData[4] = lastContents;
				     break;
				case PHYSICAL_DATA_TYPE:
				     rowData[5] = lastContents;
				     break;
				case PHYSICAL_DATA_LENGHT:
				     rowData[6] = lastContents;
				     break;
				case DESCRIPTION:
				     rowData[7] = lastContents;
				     break;
				case MINIMUM_VALUE:
				     rowData[8] = lastContents;
				     break;
				case MAXIMUM_VALUE:
				     rowData[9] = lastContents;
				     break;
				case DEFAULT_VALUE:
				     rowData[10] = lastContents;
				     break;
				case VALIDATION_RULE:
				     rowData[11] = lastContents;
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