package org.terasoluna.qp.domain.service.generatedocument.spreadsheet;

import java.io.IOException;
import java.io.Writer;

public class SpreadsheetTableDesign extends SpreadsheetWriter {

	public SpreadsheetTableDesign(Writer out) {
		super(out);
	}

	public void endSheetWithMoreAction(int numOfRow, int baseTypeSize, int domainSize, int operatorSize, boolean isExportLog) throws IOException {
		_out.write("</sheetData>");
		_out.write("<mergeCells count=\"1\">");

		// Merge cell for header
		_out.write("<mergeCell ref=\"A1:K2\"/>");
		_out.write("<mergeCell ref=\"L1:T1\"/>");
		_out.write("<mergeCell ref=\"L2:T2\"/>");
		_out.write("<mergeCell ref=\"U1:BF1\"/>");
		_out.write("<mergeCell ref=\"U2:BF2\"/>");
		_out.write("<mergeCell ref=\"BG1:BL1\"/>");
		_out.write("<mergeCell ref=\"BG2:BL2\"/>");
		_out.write("<mergeCell ref=\"BM1:BR1\"/>");
		_out.write("<mergeCell ref=\"BM2:BR2\"/>");
		_out.write("<mergeCell ref=\"BS1:BX1\"/>");
		_out.write("<mergeCell ref=\"BS2:BX2\"/>");
		_out.write("<mergeCell ref=\"BY1:CD1\"/>");
		_out.write("<mergeCell ref=\"BY2:CD2\"/>");
		
		if(isExportLog){
			_out.write("<mergeCell ref=\"CE1:CI2\"/>");
			_out.write("<mergeCell ref=\"CJ1:CN2\"/>");
		}
		
		// Merge cell for title information
		for (int i = 4; i <= 10; i++) {
			_out.write("<mergeCell ref=\"A" + i + ":M" + i + "\"/>");
			_out.write("<mergeCell ref=\"N" + i + ":CD" + i + "\"/>");
		}
		
		if(isExportLog){
			_out.write("<mergeCell ref=\"CE8:CI9\"/>");
			_out.write("<mergeCell ref=\"CJ8:CN9\"/>");
		}
		
		// Merge cell for content title
		_out.write("<mergeCell ref=\"A12:B13\"/>");
		_out.write("<mergeCell ref=\"C12:J13\"/>");
		_out.write("<mergeCell ref=\"K12:R13\"/>");
		_out.write("<mergeCell ref=\"S12:T13\"/>");
		_out.write("<mergeCell ref=\"U12:V13\"/>");
		_out.write("<mergeCell ref=\"W12:AV12\"/>");
		_out.write("<mergeCell ref=\"W13:X13\"/>");
		_out.write("<mergeCell ref=\"Y13:AD13\"/>");
		_out.write("<mergeCell ref=\"AE13:AJ13\"/>");
		_out.write("<mergeCell ref=\"AK13:AP13\"/>");
		_out.write("<mergeCell ref=\"AQ13:AV13\"/>");
		_out.write("<mergeCell ref=\"AW12:BB12\"/>");
		_out.write("<mergeCell ref=\"BC12:BH12\"/>");
		_out.write("<mergeCell ref=\"AW13:AY13\"/>");
		_out.write("<mergeCell ref=\"AZ13:BB13\"/>");
		_out.write("<mergeCell ref=\"BC13:BE13\"/>");
		_out.write("<mergeCell ref=\"BF13:BH13\"/>");
		_out.write("<mergeCell ref=\"BI12:BM13\"/>");
		_out.write("<mergeCell ref=\"BN12:BO13\"/>");
		_out.write("<mergeCell ref=\"BP12:BQ13\"/>");
		_out.write("<mergeCell ref=\"BR12:BS13\"/>");
		_out.write("<mergeCell ref=\"BT12:BV13\"/>");
		_out.write("<mergeCell ref=\"BW12:CD13\"/>");
		
		// Merge cell for content detail
		for (int i = 14; i < (14 + numOfRow) ; i++) {
			_out.write("<mergeCell ref=\"A" + i + ":B" + i + "\"/>");
			_out.write("<mergeCell ref=\"C" + i + ":J" + i + "\"/>");
			_out.write("<mergeCell ref=\"K" + i + ":R" + i + "\"/>");
			_out.write("<mergeCell ref=\"S" + i + ":T" + i + "\"/>");
			_out.write("<mergeCell ref=\"U" + i + ":V" + i + "\"/>");
			_out.write("<mergeCell ref=\"W" + i + ":X" + i + "\"/>");
			_out.write("<mergeCell ref=\"Y" + i + ":AD" + i + "\"/>");
			_out.write("<mergeCell ref=\"AE" + i + ":AJ" + i + "\"/>");
			_out.write("<mergeCell ref=\"AK" + i + ":AP" + i + "\"/>");
			_out.write("<mergeCell ref=\"AQ" + i + ":AV" + i + "\"/>");
			_out.write("<mergeCell ref=\"AW" + i + ":AY" + i + "\"/>");
			_out.write("<mergeCell ref=\"AZ" + i + ":BB" + i + "\"/>");
			_out.write("<mergeCell ref=\"BC" + i + ":BE" + i + "\"/>");
			_out.write("<mergeCell ref=\"BF" + i + ":BH" + i + "\"/>");
			_out.write("<mergeCell ref=\"BI" + i + ":BM" + i + "\"/>");
			_out.write("<mergeCell ref=\"BN" + i + ":BO" + i + "\"/>");
			_out.write("<mergeCell ref=\"BP" + i + ":BQ" + i + "\"/>");
			_out.write("<mergeCell ref=\"BR" + i + ":BS" + i + "\"/>");
			_out.write("<mergeCell ref=\"BT" + i + ":BU" + i + "\"/>");
			_out.write("<mergeCell ref=\"BW" + i + ":CD" + i + "\"/>");

			if(isExportLog){
				_out.write("<mergeCell ref=\"CE" + i + ":CI" + i + "\"/>");
				_out.write("<mergeCell ref=\"CJ" + i + ":CN" + i + "\"/>");
			}
		}
		_out.write("</mergeCells>");
		
		// Add data validation 
		_out.write("<dataValidations count=\"5\">");
		
		// Validation for table type
		_out.write("  <dataValidation type=\"list\" errorStyle=\"warning\" operator=\"equal\"");
		_out.write(" sqref=\"N7\">");
		_out.write("<formula1>DataSource!$C$5:$C$6</formula1>");
		_out.write("  </dataValidation>");
		
		// Validation for base type
		_out.write("  <dataValidation type=\"list\" errorStyle=\"warning\" operator=\"equal\"");
		_out.write(" sqref=\"AW14:AW" + (numOfRow + 14) + "\">");
		_out.write("<formula1>DataSource!$G$5:$G$" + (baseTypeSize + 4) + "</formula1>");
		_out.write("  </dataValidation>");
		
		_out.write("  <dataValidation type=\"list\" errorStyle=\"warning\" operator=\"equal\"");
		_out.write(" sqref=\"BC14:BC" + (numOfRow + 14) + "\">");
		_out.write("<formula1>DataSource!$G$5:$G$" + (baseTypeSize + 4) + "</formula1>");
		_out.write("  </dataValidation>");
		
		// Validation for domain type
		_out.write("  <dataValidation type=\"list\" errorStyle=\"warning\" operator=\"equal\"");
		_out.write(" sqref=\"BI14:BI" + (numOfRow + 14) + "\">");
		_out.write("<formula1>DataSource!$K$5:$K$" + (domainSize + 4) + "</formula1>");
		_out.write("  </dataValidation>");
		
		// Validation for operator
		_out.write("  <dataValidation type=\"list\" errorStyle=\"warning\" operator=\"equal\"");
		_out.write(" sqref=\"BT14:BT" + (numOfRow + 14) + "\">");
		_out.write("<formula1>DataSource!$O$5:$O$" + (operatorSize + 4) + "</formula1>");
		_out.write("  </dataValidation>");
		
		_out.write("</dataValidations>");

		_out.write("</worksheet>");
	}

}
