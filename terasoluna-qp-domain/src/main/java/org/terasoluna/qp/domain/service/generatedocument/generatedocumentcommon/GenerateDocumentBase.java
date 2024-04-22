package org.terasoluna.qp.domain.service.generatedocument.generatedocumentcommon;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.terasoluna.qp.app.common.ultils.FileUtilsQP;

public class GenerateDocumentBase {

	protected static Map<String, XSSFCellStyle> styles = new HashMap<String, XSSFCellStyle>();
	
	public static void generateDatasource(Writer out, List<?> listOfLanguageDesign) throws Exception {

	}
	
	public static void generate(Writer out, List<?> listOfData) throws Exception {

	}

	public static void addSheet(String name, XSSFWorkbook book, Map<String, File> sheets) throws IOException {
		XSSFSheet sheet = book.createSheet(name);
		String ref = sheet.getPackagePart().getPartName().getName().substring(1);
		File tmp = FileUtilsQP.createTempFile("sheet" + (sheets.size() + 1), ".xml");
		sheets.put(ref, tmp);
	}

	/**
	 * Create a library of cell styles.
	 */
	public static Map<String, XSSFCellStyle> createStyles(XSSFWorkbook wb) {
		/*XSSFDataFormat fmt = wb.createDataFormat();

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
		*/
		// Cell style for header row
		XSSFCellStyle style1 = wb.createCellStyle();
		style1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style1.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		// Set Font
		Font f = wb.createFont();
		f.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style1.setFont(f);
		style1.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style1.setWrapText(true);

		// Set border
		style1.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style1.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style1.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style1.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styles.put("headerBold", style1);

		// Cell style for header row normal
		XSSFCellStyle style2 = wb.createCellStyle();
		style2.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// Set border
		style2.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style2.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styles.put("headerNormal", style2);

		// Cell style for Datasource sheet title
		XSSFCellStyle style3 = wb.createCellStyle();
		// Set Font
		Font f3 = wb.createFont();
		f3.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style3.setFont(f3);
		styles.put("dtSourceTitle", style3);

		// Cell style for Datasource sheet header
		XSSFCellStyle style4 = wb.createCellStyle();
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
		XSSFCellStyle style5 = wb.createCellStyle();
		// Set border
		style5.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style5.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style5.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style5.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styles.put("dtSourceContent", style5);

		// Cell style for title of content
		XSSFCellStyle style6 = wb.createCellStyle();
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
		XSSFCellStyle style7 = wb.createCellStyle();
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
		
		// Cell style for header row
		XSSFCellStyle style8 = wb.createCellStyle();
		style8.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style8.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		// Set Font
		Font f8 = wb.createFont();
		f8.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style8.setFont(f8);
		style8.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		style8.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		// Set border
		style8.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style8.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style8.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style8.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styles.put("headerBoldLeft", style8);
		
		// Cell style for header row
		XSSFCellStyle style9 = wb.createCellStyle();
		style9.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style9.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		// Set Font
		Font f9 = wb.createFont();
		f9.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style9.setFont(f9);
		style9.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style9.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		// Set border
		style9.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style9.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style9.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styles.put("headerBoldNoTop", style9);

		// Cell style for normal content left align
		XSSFCellStyle style10 = wb.createCellStyle();
		style10.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
		// Set border
		style10.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style10.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style10.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style10.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styles.put("normalContent", style10);
		
		// Cell style for import status 
		XSSFCellStyle style11 = wb.createCellStyle();
		style11.setAlignment(CellStyle.ALIGN_CENTER);
		style11.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style11.setFillForegroundColor(HSSFColor.YELLOW.index);
		style11.setFillPattern(CellStyle.SOLID_FOREGROUND);
		// Set border
		style11.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style11.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style11.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style11.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styles.put("importStatus", style11);
		
		// Cell style for import result 
		XSSFCellStyle style12 = wb.createCellStyle();
		style12.setAlignment(CellStyle.ALIGN_CENTER);
		style12.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style12.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		style12.setFillPattern(CellStyle.SOLID_FOREGROUND);
		// Set border
		style12.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style12.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style12.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style12.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styles.put("importResult", style12);
		
		return styles;
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
	public static void substitute(File zipfile, Map<String, File> sheets, OutputStream out) throws IOException {
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
			FileUtilsQP.deleteTempFile(sheets);
		}
	}

	public static void copyStream(InputStream in, OutputStream out) throws IOException {
		byte[] chunk = new byte[1024];
		int count;
		while ((count = in.read(chunk)) >= 0) {
			out.write(chunk, 0, count);
		}
	}
}
