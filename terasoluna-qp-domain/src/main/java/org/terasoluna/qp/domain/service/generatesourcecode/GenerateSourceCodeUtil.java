package org.terasoluna.qp.domain.service.generatesourcecode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.FileUtilsQP;
import org.terasoluna.qp.app.common.ultils.GenerateUniqueKey;
import org.terasoluna.qp.app.common.ultils.ZipUtils;
import org.terasoluna.qp.domain.model.GenerateSourceCode;

/**
 * @author hunghx
 *
 */
public class GenerateSourceCodeUtil {

	private static final Logger log = LoggerFactory.getLogger(GenerateSourceCodeUtil.class);
			
	public static String createSaveFileDirectory(String branchFolderPath, String rootFolderPath) {
		StringBuilder fullPath = new StringBuilder(rootFolderPath);
		FileUtilsQP.createDirectory(fullPath.append(branchFolderPath).toString());
		fullPath.append(File.separator);
		return fullPath.toString();
	}

	public static void generateZipSourceFile(GenerateSourceCode generateSourceCode) {
		//String exportFileName = generateSourceCode.getProject().getProjectCode() + GenerateUniqueKey.generateWithDatePrefix() + GenerateSourceCodeConst.ZIP_EXTEND;
		FileUtilsQP.createDirectory(FileUtilsQP.getExportFolder());
		try {
			log.debug("source path {}", generateSourceCode.getSourcePath());
			log.debug("source export {}", FileUtilsQP.getExportFolder());
			ZipUtils.Zip(generateSourceCode.getSourcePath(), FileUtilsQP.getExportFolder() + generateSourceCode.getFileName());
			//generateSourceCode.setFileName(exportFileName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void zipSourceFileBatch(GenerateSourceCode generateSourceCode, String pathExport) {
		//String exportFileName = generateSourceCode.getProject().getProjectCode() + GenerateUniqueKey.generateWithDatePrefix() + GenerateSourceCodeConst.ZIP_EXTEND;
		try {
			ZipUtils.Zip(generateSourceCode.getSourcePath(), StringUtils.appendIfMissing(pathExport, File.separator, File.separator) + generateSourceCode.getFileName());
			//generateSourceCode.setFileName(exportFileName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void createSourceFolder(GenerateSourceCode generateSourceCode) {
		if (generateSourceCode != null) {
			String exportFileName = generateSourceCode.getProject().getProjectCode() + GenerateUniqueKey.generateWithDatePrefix();
			generateSourceCode.setSourcePath(FileUtilsQP.getSourceGenerationFolderPath() + exportFileName + File.separator);
			generateSourceCode.setFileName(exportFileName + GenerateSourceCodeConst.ZIP_EXTEND);
			FileUtilsQP.createDirectory(generateSourceCode.getSourcePath());
		}
	}
	
	public static void writeLog(File logFile, String strContent) {
		try {
			FileUtilsQP.writeStringToFile(logFile, strContent, DbDomainConst.CHARACTER_ENCODING ,true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeLog(File logFile, Exception ex) {
		BufferedWriter out = null;
		Writer writer = null;
		PrintWriter pWriter = null;
		try {
			logFile.getParentFile().mkdirs();
			writer = new FileWriterWithEncoding(logFile, DbDomainConst.CHARACTER_ENCODING, true);

			out = new BufferedWriter(writer);
			pWriter = new PrintWriter(out, true);
			ex.printStackTrace(pWriter);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(writer);
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(pWriter);
		}
	}
	
	/**
	 * 
	 * @param packageName
	 * @return
	 * @author dungnn1
	 */
	public static String normalizedPackageName(String packageName) {
		return StringUtils.lowerCase(StringUtils.deleteWhitespace(packageName));
	}

	/**
	 * 
	 * @param className
	 * @return
	 * @author dungnn1
	 */
	public static String normalizedClassName(String className) {
		return StringUtils.capitalize(StringUtils.deleteWhitespace(className));
	}

	/**
	 * 
	 * @param methodName
	 * @return
	 * @author dungnn1
	 */
	public static String normalizedMethodName(String methodName) {
		return StringUtils.uncapitalize(StringUtils.deleteWhitespace(methodName));
	}

	/**
	 * 
	 * @param variantName
	 * @return
	 * @author dungnn1
	 */
	public static String normalizedVariantName(String variantName) {
		return StringUtils.uncapitalize(StringUtils.deleteWhitespace(variantName));
	}

	/**
	 * 
	 * @param propertiesName
	 * @return
	 * @author dungnn1
	 */
	public static String normalizedPropertiesJSP(String propertiesName) {

		StringBuilder str = new StringBuilder(StringUtils.deleteWhitespace(propertiesName));

		if (StringUtils.isBlank(str)) {
			return propertiesName;
		}
		if (str.length() > 1 && Character.isUpperCase(str.charAt(1)) && Character.isUpperCase(str.charAt(0))) {
			return str.toString();
		}

		return StringUtils.uncapitalize(str.toString());
	}

	/**
	 * 
	 * @param url
	 * @return
	 * @author dungnn1
	 */
	public static String normalizedURL(String url) {
		return StringUtils.lowerCase(StringUtils.deleteWhitespace(url));
	}
	
	public static void setSuffixObjDataType(int type, int scope, List<?> lstArgObj){
		
		switch (scope) {

		}
	}
	
	/**
	 * Get name of primitive type of java
	 * 
	 * @param data type
	 * @return String name of primitive
	 */
	public static String getPrimitiveTypeName(int dataType){
		String nameOfPrimitive = "";
		switch (dataType) {
		case GenerateSourceCodeConst.DataType.BYTE:nameOfPrimitive = "byte";
			break;
		case GenerateSourceCodeConst.DataType.SHORT:nameOfPrimitive = "Short";
			break;
		case GenerateSourceCodeConst.DataType.INTEGER:nameOfPrimitive = "Integer";
			break;
		case GenerateSourceCodeConst.DataType.LONG:nameOfPrimitive = "Long";
			break;
		case GenerateSourceCodeConst.DataType.FLOAT:nameOfPrimitive = "Float";
			break;
		case GenerateSourceCodeConst.DataType.DOUBLE:nameOfPrimitive = "Double";
			break;
		case GenerateSourceCodeConst.DataType.CHARACTER:nameOfPrimitive = "Character";
			break;
		case GenerateSourceCodeConst.DataType.BOOLEAN:nameOfPrimitive = "Boolean";
			break;
		case GenerateSourceCodeConst.DataType.STRING:nameOfPrimitive = "String";
			break;
		case GenerateSourceCodeConst.DataType.BIGDECIMAL:nameOfPrimitive = "java.math.BigDecimal";
			break;
		case GenerateSourceCodeConst.DataType.DATETIME:
		case GenerateSourceCodeConst.DataType.TIMESTAMP:nameOfPrimitive = "java.sql.Timestamp";
			break;
		case GenerateSourceCodeConst.DataType.TIME:nameOfPrimitive = "java.sql.Time";
			break;
		case GenerateSourceCodeConst.DataType.DATE:nameOfPrimitive = "java.sql.Date";
			break;
		default:nameOfPrimitive = "";
			break;
		}

		return nameOfPrimitive;
	}
	
	/**
	 * convert physical type to java data type name
	 *
	 * @param physical
	 * @return Integer java type
	 */
	public static String convertToJavaDataTypeNameFromBaseType(int baseType, boolean isDomain) {
		String result = null;
		switch (baseType) {
			case DbDomainConst.BaseType.CHARACTER_VARYING_BASETYPE:
			case DbDomainConst.BaseType.TEXT_BASETYPE:
			case DbDomainConst.BaseType.CHAR_BASETYPE:
				//STRING_DATATYPE
				result = "String";
				break;
			case DbDomainConst.BaseType.INTEGER_BASETYPE:
			case DbDomainConst.BaseType.SERIAL_BASETYPE:
				//INT_DATATYPE
				result = "Integer";
				break;
			case DbDomainConst.BaseType.SMALLINT_BASETYPE:
				//SHORT_DATATYPE
				result = "Short";
				break;
			case DbDomainConst.BaseType.BIGINT_BASETYPE:
			case DbDomainConst.BaseType.BIGSERIAL_BASETYPE:
				//LONG_DATATYPE;
				result = "Long";
				break;
			case DbDomainConst.BaseType.FLOAT_BASETYPE:
				//FLOAT_DATATYPE;
				result = "Float";
				break;
			case DbDomainConst.BaseType.NUMERIC_BASETYPE:
			case DbDomainConst.BaseType.CURRENCY_BASETYPE:
				//BIGDECIMAL_DATATYPE;
				result = "java.math.BigDecimal";
				break;
			case DbDomainConst.BaseType.BOOLEAN_BASETYPE:
				//BOOLEAN_DATATYPE;
				result = "Boolean";
				break;
			case DbDomainConst.BaseType.DATE_BASETYPE:
				result = (isDomain?"java.sql.Date":"String");
				break;
			case DbDomainConst.BaseType.TIME_BASETYPE:
				result = (isDomain?"java.sql.Time":"String");
				break;
			case DbDomainConst.BaseType.DATETIME_BASETYPE:
			case DbDomainConst.BaseType.TIMESTAMP_BASETYPE:
				//TIMESTAMP_DATATYPE;
				result = (isDomain?"java.sql.Timestamp":"String");
				break;
			case DbDomainConst.BaseType.BYTE_BASETYPE:
				result = "Byte";
				break;
			case DbDomainConst.BaseType.BINARY_BASETYPE:
				//BYTE_DATATYPE;
				result = "byte []";
				break;
			case DbDomainConst.BaseType.DOUBLE_BASETYPE:
				//DOUBLE_BASETYPE_DATATYPE;
				result = "Double";
				break;
		}

		return result;
	}
	
}
