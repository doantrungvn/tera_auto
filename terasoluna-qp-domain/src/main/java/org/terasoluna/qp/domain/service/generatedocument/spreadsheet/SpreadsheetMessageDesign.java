package org.terasoluna.qp.domain.service.generatedocument.spreadsheet;

import java.io.IOException;
import java.io.Writer;

public class SpreadsheetMessageDesign extends SpreadsheetWriter {

	public SpreadsheetMessageDesign(Writer out) {
		super(out);
	}
	
	public void endSheetWithMoreAction(int numOfRow, int numOfLanguage) throws IOException {
		_out.write("</sheetData>");
		_out.write("<mergeCells count=\"1\">");
		// Merge header
		_out.write("<mergeCell ref=\"A1:K2\"/>");
		_out.write("<mergeCell ref=\"L1:T1\"/>");
		_out.write("<mergeCell ref=\"L2:T2\"/>");
		_out.write("<mergeCell ref=\"U1:AC1\"/>");
		_out.write("<mergeCell ref=\"U2:AC2\"/>");
		_out.write("<mergeCell ref=\"AD1:AL1\"/>");
		_out.write("<mergeCell ref=\"AD2:AL2\"/>");
		_out.write("<mergeCell ref=\"AM1:AR1\"/>");
		_out.write("<mergeCell ref=\"AM2:AR2\"/>");
		_out.write("<mergeCell ref=\"AS1:AX1\"/>");
		_out.write("<mergeCell ref=\"AS2:AX2\"/>");
		_out.write("<mergeCell ref=\"AY1:BE1\"/>");
		_out.write("<mergeCell ref=\"AY2:BE2\"/>");
		_out.write("<mergeCell ref=\"BF1:BL1\"/>");
		_out.write("<mergeCell ref=\"BF2:BL2\"/>");

		
		// Merge title
		_out.write("<mergeCell ref=\"A4:B4\"/>");
		_out.write("<mergeCell ref=\"C4:N4\"/>");
		_out.write("<mergeCell ref=\"O4:S4\"/>");
		_out.write("<mergeCell ref=\"T4:X4\"/>");
		_out.write("<mergeCell ref=\"Y4:AC4\"/>");
		_out.write("<mergeCell ref=\"AD4:AX4\"/>");
		_out.write("<mergeCell ref=\"AY4:BJ4\"/>");
		_out.write("<mergeCell ref=\"BK4:BL4\"/>");
		
		// Merge content
		for (int i = 5; i < numOfRow + 5; i++) {
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

		_out.write("<dataValidations count=\"4\">");
		// Validation for language
		_out.write("  <dataValidation type=\"list\" errorStyle=\"warning\" operator=\"equal\"");
		_out.write(" sqref=\"BK5:BK" + (numOfRow + 5) + "\">");
		_out.write("<formula1>DataSource!$O$5:$O$" + (numOfLanguage + 4) + "</formula1>");
		_out.write("  </dataValidation>");
		
		// Validation for source
		_out.write("  <dataValidation type=\"list\" errorStyle=\"warning\" operator=\"equal\"");
		_out.write(" sqref=\"O5:O" + (numOfRow + 5) + "\">");
		_out.write("<formula1>DataSource!$C$5:$C$6</formula1>");
		_out.write("  </dataValidation>");
		
		// Validation for output
		_out.write("  <dataValidation type=\"list\" errorStyle=\"warning\" operator=\"equal\"");
		_out.write(" sqref=\"T5:T" + (numOfRow + 5) + "\">");
		_out.write("<formula1>DataSource!$G$5:$G$12</formula1>");
		_out.write("  </dataValidation>");
		
		// Validation for error level
		_out.write("  <dataValidation type=\"list\" errorStyle=\"warning\" operator=\"equal\"");
		_out.write(" sqref=\"Y5:Y" + (numOfRow + 5) + "\">");
		_out.write("<formula1>DataSource!$K$5:$K$8</formula1>");
		_out.write("  </dataValidation>");
		_out.write("</dataValidations>");

		_out.write("</worksheet>");
	}

}
