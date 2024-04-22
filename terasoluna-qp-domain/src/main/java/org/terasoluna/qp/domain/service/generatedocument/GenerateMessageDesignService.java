package org.terasoluna.qp.domain.service.generatedocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.util.HtmlUtils;
import org.terasoluna.qp.app.common.ultils.FileUtilsQP;
import org.terasoluna.qp.domain.model.GenerateDocumentItem;
import org.terasoluna.qp.domain.model.LanguageDesign;
import org.terasoluna.qp.domain.model.MessageDesign;

/**
 * Demo best performance export document
 * @author DungNN
 *
 */
public class GenerateMessageDesignService {
	static int numOfRow = 100;
	static int numOfLanguage = 0;

	public static void generate(GenerateDocumentItem item) {

		try {
			// Step 1. Create a template file. Setup sheets and workbook-level
			// objects such as
			// cell styles, number formats, etc.
			long start = System.currentTimeMillis();
			System.out.println("Starting..." + start);

			XSSFWorkbook wb = new XSSFWorkbook();
			Map<String, File> sheets = new HashMap<String, File>();

			addSheet("DataSource", wb, sheets);
			addSheet("MessageDefinitionDocument", wb, sheets);

			Map<String, XSSFCellStyle> styles = createStyles(wb);

			// save the template
			FileOutputStream os = new FileOutputStream(item.getExcelFolder() + "template.xlsx");
			wb.write(os);
			os.close();

			numOfLanguage = CollectionUtils.isEmpty(item.getLanguageDesignLst()) ? 0 : item.getLanguageDesignLst().size();

			// generate data for each sheet
			for (Map.Entry<String, File> entry : sheets.entrySet()) {
				Writer fw = new FileWriter(entry.getValue());

				if (StringUtils.contains(entry.getKey(), "sheet1.xml")) {
					generateDatasource(fw, styles, item.getLanguageDesignLst());
				} else {
					generate(fw, styles, item.getDataLst());
				}

				fw.close();
			}

			// Step 3. Substitute the template entry with the generated data
			String outputFilePath = item.getExcelFolder() + item.getDocumentItemTemplateName();
			FileOutputStream out = new FileOutputStream(outputFilePath);

			substitute(new File(item.getExcelFolder() + "template.xlsx"), sheets, out);
			out.close();
			long end = (System.currentTimeMillis() - start) / 1000;
			System.out.println("end..." + end);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void generateDatasource(Writer out, Map<String, XSSFCellStyle> styles, List<LanguageDesign> listOfLanguageDesign) throws Exception {

		SpreadsheetWriter sw = new SpreadsheetWriter(out);
		sw.beginSheet();

		// insert header row
		sw.insertRow(3);
		int styleIndex = styles.get("header").getIndex();
		sw.createCell(0, "Source", styleIndex);
		sw.createCell(4, "Output location", styleIndex);
		sw.createCell(8, "Error level", styleIndex);
		sw.createCell(12, "Language", styleIndex);
		sw.endRow();

		sw.insertRow(4);
		sw.createCell(1, "Code", styleIndex);
		sw.createCell(2, "Name", styleIndex);
		sw.createCell(5, "Code", styleIndex);
		sw.createCell(6, "Name", styleIndex);
		sw.createCell(9, "Code", styleIndex);
		sw.createCell(10, "Name", styleIndex);
		sw.createCell(13, "Code", styleIndex);
		sw.createCell(14, "Name", styleIndex);
		sw.endRow();

		for (int rownum = 0; rownum < numOfLanguage; rownum++) {
			sw.insertRow(rownum + 5);
			LanguageDesign row = listOfLanguageDesign.get(rownum);

			sw.createCell(13, row.getLanguageId());
			sw.createCell(14, HtmlUtils.htmlEscape(row.getLanguageName()));
			sw.endRow();
		}

		// write data rows
		sw.endSheet();
	}

	private static void addSheet(String name, XSSFWorkbook book, Map<String, File> sheets) throws IOException {
		XSSFSheet sheet = book.createSheet(name);
		String ref = sheet.getPackagePart().getPartName().getName().substring(1);
		File tmp = FileUtilsQP.createTempFile("sheet" + (sheets.size() + 1), ".xml");
		sheets.put(ref, tmp);
	}

	/**
	 * Create a library of cell styles.
	 */
	private static Map<String, XSSFCellStyle> createStyles(XSSFWorkbook wb) {
		Map<String, XSSFCellStyle> styles = new HashMap<String, XSSFCellStyle>();
		XSSFDataFormat fmt = wb.createDataFormat();

		XSSFCellStyle style1 = wb.createCellStyle();
		style1.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
		style1.setDataFormat(fmt.getFormat("0.0%"));
		styles.put("percent", style1);

		XSSFCellStyle style2 = wb.createCellStyle();
		style2.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style2.setDataFormat(fmt.getFormat("0.0X"));
		styles.put("coeff", style2);

		XSSFCellStyle style3 = wb.createCellStyle();
		style3.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
		style3.setDataFormat(fmt.getFormat("$#,##0.00"));
		styles.put("currency", style3);

		XSSFCellStyle style4 = wb.createCellStyle();
		style4.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
		style4.setDataFormat(fmt.getFormat("mmm dd"));
		styles.put("date", style4);

		XSSFCellStyle style5 = wb.createCellStyle();
		XSSFFont headerFont = wb.createFont();
		headerFont.setBold(true);
		style5.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style5.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style5.setFont(headerFont);
		styles.put("header", style5);

		return styles;
	}

	private static void generate(Writer out, Map<String, XSSFCellStyle> styles, List<?> listOfData) throws Exception {

		SpreadsheetWriter sw = new SpreadsheetWriter(out);
		sw.beginSheet();

		// insert header row
		sw.insertRow(4);
		int styleIndex = styles.get("header").getIndex();
		sw.createCell(0, "Item no.", styleIndex);
		sw.createCell(2, "ID", styleIndex);
		sw.createCell(14, "Source", styleIndex);
		sw.createCell(19, "Output location", styleIndex);
		sw.createCell(24, "Error level", styleIndex);
		sw.createCell(29, "Message string", styleIndex);
		sw.createCell(50, "Description", styleIndex);
		sw.createCell(62, "Language", styleIndex);

		sw.endRow();

		numOfRow = CollectionUtils.isEmpty(listOfData) ? 0 : listOfData.size();

		// write data rows
		for (int rownum = 0; rownum < numOfRow; rownum++) {
			sw.insertRow(rownum + 5);
			MessageDesign msg = (MessageDesign) listOfData.get(rownum);

			sw.createCell(0, rownum + 1);
			sw.createCell(2, msg.getMessageCode());

			sw.createCell(14, "Client");
			sw.createCell(19, "Screen");
			sw.createCell(24, "Label");
			sw.createCell(29, HtmlUtils.htmlEscape(msg.getMessageString()));
			sw.createCell(50, HtmlUtils.htmlEscape(StringUtils.defaultString(msg.getRemark(), StringUtils.EMPTY)));
			sw.createCell(62, msg.getLanguageCode());
			sw.endRow();

		}
		sw.endSheetWithMoreAction();
	}

	/**
	 *
	 * @param zipfile
	 *            the template file
	 * @param sheets
	 *            the Map with key "name of the sheet entry to substitute (e.g. xl/worksheets/sheet1.xml, xl/worksheets/sheet2.xml etc)" and value "XML file with the sheet data"
	 * @param out
	 *            the stream to write the result to
	 */
	private static void substitute(File zipfile, Map<String, File> sheets, OutputStream out) throws IOException {
		ZipFile zip = null;
		ZipOutputStream zos = null;

		try {
			zip = new ZipFile(zipfile);
			zos = new ZipOutputStream(out);

			@SuppressWarnings("unchecked")
			Enumeration<ZipEntry> en = (Enumeration<ZipEntry>) zip.entries();
			while (en.hasMoreElements()) {
				ZipEntry ze = en.nextElement();
				System.out.println(ze.getName());
				if (!sheets.containsKey(ze.getName())) {
					zos.putNextEntry(new ZipEntry(ze.getName()));
					InputStream is = zip.getInputStream(ze);
					copyStream(is, zos);
					is.close();
				}
			}

			for (Map.Entry<String, File> entry : sheets.entrySet()) {
				System.out.println(entry.getKey());
				zos.putNextEntry(new ZipEntry(entry.getKey()));
				InputStream is = new FileInputStream(entry.getValue());
				copyStream(is, zos);
				is.close();
			}
		} finally {
			try {
				if (zos != null)
					zos.close();
			} finally {
				if (zip != null)
					zip.close();
			}
		}
	}

	private static void copyStream(InputStream in, OutputStream out) throws IOException {
		byte[] chunk = new byte[1024];
		int count;
		while ((count = in.read(chunk)) >= 0) {
			out.write(chunk, 0, count);
		}
	}

	/**
	 * Writes spreadsheet data in a Writer. (YK: in future it may evolve in a full-featured API for streaming data in Excel)
	 */
	public static class SpreadsheetWriter {
		private final Writer _out;
		private int _rownum;

		public SpreadsheetWriter(Writer out) {
			_out = out;
		}

		public void beginSheet() throws IOException {
			_out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<worksheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">");

			_out.write("<sheetData>\n");
		}

		public void endSheetWithMoreAction() throws IOException {
			_out.write("</sheetData>");
			_out.write("<mergeCells count=\"1\">");
			for (int i = 5; i < numOfRow; i++) {
				_out.write("<mergeCell ref=\"A" + i + ":B" + i + "\"/>");
				_out.write("<mergeCell ref=\"C" + i + ":N" + i + "\"/>");
				_out.write("<mergeCell ref=\"O" + i + ":S" + i + "\"/>");
				_out.write("<mergeCell ref=\"T" + i + ":X" + i + "\"/>");
				_out.write("<mergeCell ref=\"Y" + i + ":AC" + i + "\"/>");
				_out.write("<mergeCell ref=\"AD" + i + ":AX" + i + "\"/>");
				_out.write("<mergeCell ref=\"AY" + i + ":BJ" + i + "\"/>");
				_out.write("<mergeCell ref=\"BK" + i + ":BL" + i + "\"/>");
			}

			_out.write("</mergeCells>");

			_out.write("<dataValidations count=\"1\">");
			_out.write("  <dataValidation type=\"list\" errorStyle=\"warning\" operator=\"equal\"");

			_out.write(" sqref=\"BK5:BK" + numOfRow + "\">");
			int maxIndex = numOfLanguage + 5;
			_out.write("<formula1>DataSource!$O$6:$O$" + maxIndex + "</formula1>");
			_out.write("  </dataValidation>");
			_out.write("</dataValidations>");

			_out.write("</worksheet>");
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
			_out.write("<is><t>" + value + "</t></is>");

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
	}

}
