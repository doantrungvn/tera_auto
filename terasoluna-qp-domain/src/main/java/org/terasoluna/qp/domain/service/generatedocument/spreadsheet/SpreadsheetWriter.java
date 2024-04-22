package org.terasoluna.qp.domain.service.generatedocument.spreadsheet;

import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.DateUtil;

public class SpreadsheetWriter {
	protected final Writer _out;
	protected int _rownum;

	public SpreadsheetWriter(Writer out) {
		_out = out;
	}

	public void beginSheet() throws IOException {
		_out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<worksheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">");
		_out.write("<sheetData>\n");
	}
	
	public void beginSheet(int width) throws IOException {
		_out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<worksheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">");
		// Resize column width
		_out.write("<cols><col min=\"1\" max=\""+width +"\" width=\"4\" customWidth=\"1\" /> </cols>");
		_out.write("<sheetData>\n");
	}

	public void endSheetWithMoreAction() throws IOException {
		
	}

	public void endSheet() throws IOException {
		_out.write("</sheetData>");
		_out.write("</worksheet>");
	}

	/**
	 * Insert a new row
	 *
	 * @param rownum
	 *            0-based row number
	 */
	public void insertRow(int rownum) throws IOException {
		_out.write("<row r=\"" + (rownum + 1) + "\">\n");
		this._rownum = rownum;
	}

	/**
	 * Insert row end marker
	 */
	public void endRow() throws IOException {
		_out.write("</row>\n");
	}

	public void createCell(int columnIndex, String value, int styleIndex) throws IOException {
		String ref = new CellReference(_rownum, columnIndex).formatAsString();

		_out.write("<c r=\"" + ref + "\" t=\"inlineStr\"");

		if (styleIndex != -1) {
			_out.write(" s=\"" + styleIndex + "\"");
		}
		_out.write(">");
		_out.write("<is><t>" + ((value == null) ? StringUtils.EMPTY : value) + "</t></is>");

		_out.write("</c>");
	}

	public void createCell(int columnIndex, String value) throws IOException {
		createCell(columnIndex, value, -1);
	}

	public void createCell(int columnIndex, double value, int styleIndex) throws IOException {
		String ref = new CellReference(_rownum, columnIndex).formatAsString();
		_out.write("<c r=\"" + ref + "\" t=\"n\"");
		if (styleIndex != -1)
			_out.write(" s=\"" + styleIndex + "\"");
		_out.write(">");
		_out.write("<v>" + value + "</v>");
		_out.write("</c>");
	}

	public void createCell(int columnIndex, double value) throws IOException {
		createCell(columnIndex, value, -1);
	}

	public void createCell(int columnIndex, Calendar value, int styleIndex) throws IOException {
		createCell(columnIndex, DateUtil.getExcelDate(value, false), styleIndex);
	}
	
	public void createEmptyCells(int startIndex, int endIndex, int styleIndex) throws IOException{
		for(int i = startIndex; i <= endIndex; i++){
			createCell(i, StringUtils.EMPTY, styleIndex);
		}
	}
}