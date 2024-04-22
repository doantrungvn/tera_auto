package org.terasoluna.qp.batch.generate;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class GenerateScriptMessage {

	private final static String SQL_MESSAGE_TEMPLATE = "UPDATE message SET message_string='%s', module_resource=%s, reuse_flg=%s WHERE message_code = '%s' and (language_code || '_' ||  country_code ) = '%s';";

	private final static String SQL_DELETE_TEMPLATE = "DELETE FROM message WHERE message_code = '%s';";

	private static final String APOSTROPHE = "'";

	private final static int indexMessageCode = 0;
	private final static int indexResource = 4;
	private final static int indexReuseflg = 5;
	private final static int indexStringEn = 6;
	private final static int indexSQLEn = 7;
	private final static int indexStringJp = 8;
	private final static int indexSQLJp = 9;
	private final static int indexStatus = 12;
	private final static int startRow = 3;

	private static final String STATUS_NEW = "New";
	private static final String STATUS_REVISED = "Revised";
	private static final String STATUS_FINISHED = "Finished";
	private static final String STATUS_DELETE = "Deleted";
	private static final String STR_NULL = "NULL";

	private static final String LOCAL_EN = "en_US";
	private static final String LOCAL_JP = "ja_JP";

	public static void main(String[] args) throws IOException {

		String root = "D:\\DungNN\\Projects\\J15041\\trunk\\Document\\";

		String excelFilePath = root + "2.ED\\D-ED001_MessageDesignDocument_For_HQ_latest.xls";
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

		String scriptPath = root + "1.RD\\04_ERD\\Alter script\\message.sql";

		String scriptPathFull = root + "1.RD\\04_ERD\\Alter script\\message_full.sql";

		StringBuilder strOutPut = new StringBuilder("------------new message -------------");
		strOutPut.append("\n");
		HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

		StringBuilder strOutUpdate = new StringBuilder(StringUtils.EMPTY);

		StringBuilder strUpdateEvidence = new StringBuilder(StringUtils.EMPTY);
		boolean updateMessage = false;

		StringBuilder strInsertEvidence = new StringBuilder(StringUtils.EMPTY);
		boolean newMessage = false;

		StringBuilder strOutPutFull = new StringBuilder("delete from message; \n SELECT setval('public.message_seq', 1, true);");
		strOutPutFull.append("\n");

		StringBuilder strDelete = new StringBuilder(StringUtils.EMPTY);
		StringBuilder strDeleteEvidence = new StringBuilder(StringUtils.EMPTY);
		boolean deleteMessage = false;

		int numOfSheet = workbook.getNumberOfSheets();

		for (int i = 1; i < numOfSheet; i++) {

			HSSFSheet firstSheet = workbook.getSheetAt(i);

			Iterator<Row> iterator = firstSheet.iterator();

			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				/* Iterator<Cell> cellIterator = nextRow.cellIterator(); */

				// if is header row -> continue
				if (nextRow.getRowNum() <= startRow)
					continue;

				Cell cell = nextRow.getCell(indexStatus);

				if (cell == null)
					continue;

				String value = StringUtils.trimToEmpty(cell.getStringCellValue());
				/* System.out.println(value); */

				processforFull(nextRow, strOutPutFull);

				// if finished -> continue
				if (StringUtils.equalsIgnoreCase(STATUS_FINISHED, value))
					continue;

				cell = nextRow.getCell(indexMessageCode);

				if (cell == null)
					continue;
				String strMessCode = StringUtils.trimToEmpty(cell.getStringCellValue());

				// if new -> make insert
				if (StringUtils.equalsIgnoreCase(STATUS_NEW, value)) {

					cell = nextRow.getCell(indexSQLEn);

					value = StringUtils.trimToEmpty(cell.getStringCellValue());

					/* System.out.println(value); */

					if (StringUtils.isNotBlank(value)) {
						newMessage = true;

						strOutPut.append(value);
						strOutPut.append("\n");

						strInsertEvidence.append(",");
						strInsertEvidence.append(APOSTROPHE);
						strInsertEvidence.append(strMessCode);
						strInsertEvidence.append(APOSTROPHE);
					}

					cell = nextRow.getCell(indexSQLJp);
					value = StringUtils.trimToEmpty(cell.getStringCellValue());

					/* System.out.println(value); */

					if (StringUtils.isNotBlank(value)) {
						strOutPut.append(value);
						strOutPut.append("\n");
						newMessage = true;
					}

				} else if (StringUtils.equalsIgnoreCase(STATUS_REVISED, value)) {

					strUpdateEvidence.append(",");
					strUpdateEvidence.append(APOSTROPHE);
					strUpdateEvidence.append(strMessCode);
					strUpdateEvidence.append(APOSTROPHE);

					/* System.out.println(strMessCode); */
					cell = nextRow.getCell(indexResource);
					String strResource = StringUtils.trimToNull(cell.getStringCellValue());
					if (!StringUtils.equalsIgnoreCase(STR_NULL, strResource)) {
						strResource = APOSTROPHE + strResource + APOSTROPHE;
					}

					cell = nextRow.getCell(indexReuseflg);
					int reuseFlg = (int) cell.getNumericCellValue();
					/* String strReuse = StringUtils.trimToEmpty(cell.getStringCellValue()); */

					cell = nextRow.getCell(indexStringEn);
					String strEn = StringUtils.trimToEmpty(cell.getStringCellValue());

					cell = nextRow.getCell(indexStringJp);
					String strJp = StringUtils.trimToEmpty(cell.getStringCellValue());

					if (StringUtils.isNotBlank(strEn) && StringUtils.isNotBlank(strJp)) {

						strOutUpdate.append(String.format(SQL_MESSAGE_TEMPLATE, strEn, strResource, reuseFlg, strMessCode, LOCAL_EN));
						strOutUpdate.append("\n");

						strOutUpdate.append(String.format(SQL_MESSAGE_TEMPLATE, strJp, strResource, reuseFlg, strMessCode, LOCAL_JP));
						strOutUpdate.append("\n");

						updateMessage = true;
					}
					// if delete
				} else if (StringUtils.equalsIgnoreCase(STATUS_DELETE, value)) {

					strDelete.append(String.format(SQL_DELETE_TEMPLATE, strMessCode));
					strDelete.append("\n");

					strDeleteEvidence.append(",");
					strDeleteEvidence.append(APOSTROPHE);
					strDeleteEvidence.append(strMessCode);
					strDeleteEvidence.append(APOSTROPHE);

					deleteMessage = true;
				}
			}
		}
		workbook.close();
		inputStream.close();

		// script 
		if (updateMessage) {
			strUpdateEvidence.deleteCharAt(0);
			strUpdateEvidence.insert(0, "select * from message  where message_code  in (");
			strUpdateEvidence.append(");");

			System.out.println("-------------Evidence for update message--------------");
			System.out.println(strUpdateEvidence);

			strOutPut.append("------------update message -------------");
			strOutPut.append("\n");
			strOutPut.append(strOutUpdate);

		}

		if (newMessage) {
			strInsertEvidence.deleteCharAt(0);
			strInsertEvidence.insert(0, "select * from message  where message_code  in (");
			strInsertEvidence.append(");");
			System.out.println("-------------Evidence for new message--------------");
			System.out.println(strInsertEvidence);
		}

		if (deleteMessage) {
			strDeleteEvidence.deleteCharAt(0);
			strDeleteEvidence.insert(0, "select * from message  where message_code  in (");
			strDeleteEvidence.append(");");
			System.out.println("-------------Evidence for new message--------------");
			System.out.println(strDeleteEvidence);

			strOutPut.append("------------delete message -------------");
			strOutPut.append("\n");
			strOutPut.append(strDelete);
		}
		
		if (newMessage || updateMessage || deleteMessage) {
			File fsave = new File(scriptPath);
			FileUtils.writeStringToFile(fsave, strOutPut.toString());
			Desktop.getDesktop().open(fsave);
		}
		
		File fFull = new File(scriptPathFull);
		FileUtils.writeStringToFile(fFull, strOutPutFull.toString());
		Desktop.getDesktop().open(fFull);
		
	}

	private static void processforFull(Row nextRow, StringBuilder strOutPutFull) {

		Cell cell = nextRow.getCell(indexMessageCode);

		if (cell == null)
			return;

		cell = nextRow.getCell(indexSQLEn);
		String value = StringUtils.trimToEmpty(cell.getStringCellValue());
		if (StringUtils.isNotBlank(value)) {
			strOutPutFull.append(value);
			strOutPutFull.append("\n");
		}
		cell = nextRow.getCell(indexSQLJp);
		value = StringUtils.trimToEmpty(cell.getStringCellValue());
		if (StringUtils.isNotBlank(value)) {
			strOutPutFull.append(value);
			strOutPutFull.append("\n");
		}

	}
}
