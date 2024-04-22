package org.terasoluna.qp.domain.service.graphicdatabasedesign;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.DbDomainConst.ConstrainsType;
import org.terasoluna.qp.app.common.constants.DbDomainConst.DatasourceType;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.GenerateUniqueKey;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.model.ValidationRule;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeConst;

public class TableDesignUtil {
	private static final String CHAR_SPLIT = ";";
	
	public static final String SEQ_TEMPLATE = "seq_{0}_{1}";
	
	// Search all table in project
	public static final Long SEARCH_ALL_TABLE_DESIGN = -1L;
	// Search all table in project and not in subject area
	public static final Long SEARCH_TABLE_DESIGN_NOT_IN_SUBJECT_AREA = -2L;
	
	public static final int SERIAL_MIN = 1;
	public static final long BIGSERIAL_MIN = 1;
	
	/**
	 * validate duplicated in the list
	 * @param strCheck
	 * @param listData
	 * @return
	 */
	public static boolean checkExistInList(Long tableId, List<TableDesign> listData) {
		if ( FunctionCommon.isEmpty(listData ) || FunctionCommon.isEmpty(tableId)) {
			return false;
		}

		for (TableDesign strTemp : listData) {
			if (strTemp.getTableDesignId().equals(tableId)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean checkColumnBelong(Long columnId, List<TableDesignDetails> listData) {
		if ( FunctionCommon.isEmpty(listData)) {
			return false;
		}

		for (TableDesignDetails strTemp : listData) {
			if (strTemp.getColumnId().equals(columnId)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * validate duplicated in the list
	 * @param strCheck
	 * @param listData
	 * @return
	 */
	public static boolean checkExistInList(String strCheck, List<String> listData) {
		if ( FunctionCommon.isEmpty(listData ) || FunctionCommon.isEmpty(strCheck)) {
			return false;
		}

		for (String strTemp : listData) {
			if (strCheck.equalsIgnoreCase(strTemp)) {
				return true;
			}
		}
		return false;
	}
	
	public static void initDefaultForItemType(TableDesignDetails designDetail,
			List<ValidationRule> validationRules) {
		//if has configuration datasource then
		if (ConstrainsType.DATASOURCE.equals(designDetail.getConstrainsType())) {
			//if auto complete default is autocomplete
			if (DatasourceType.SQL_BUILDER_IS_AUTOCOMPLETE.equals(designDetail.getDatasourceType())) {
				designDetail.setItemType(DbDomainConst.LogicDataType.AUTOCOMPLETE);
			} else {
				//default is select
				designDetail.setItemType(DbDomainConst.LogicDataType.SELECT);
			}
		} else {
			
			//default is text input
			designDetail.setItemType(DbDomainConst.LogicDataType.NAME);

			//if it is text/char/char varying and has max length greater than default
			if ((FunctionCommon.equals(DomainDatatypeConst.PhysicalDataTypeDetail.TEXT, designDetail.getBaseType()) 
					|| FunctionCommon.equals(DomainDatatypeConst.PhysicalDataTypeDetail.CHAR, designDetail.getBaseType()) 
					|| FunctionCommon.equals(DomainDatatypeConst.PhysicalDataTypeDetail.CHARACTER_VARYING, designDetail.getBaseType()))
				&& (designDetail.getMaxlength() == null || (designDetail.getMaxlength() != null && designDetail.getMaxlength().intValue() >= DbDomainConst.MAX_VAL_REMARK))) {
				// default is remark
				designDetail.setItemType(DbDomainConst.LogicDataType.REMARK);
			
			//if don't has fmt then default by base type group
			}

			if (StringUtils.isBlank(designDetail.getFmtCode())) {
				if (DataTypeUtils.notEquals(DbDomainConst.LogicDataType.REMARK, designDetail.getItemType())) {
					for (ValidationRule validateObj : validationRules) {
						if (checkBaseType(validateObj.getBaseTypeGroup(), designDetail.getGroupBaseTypeId())) {
							designDetail.setItemType(validateObj.getItemType());
							break;
						}
					}
				}
			} else {
				// default by fmt
				String[] arrFmtCode = designDetail.getFmtCode().split(",");
				String fmtCodeFinal = arrFmtCode[arrFmtCode.length - 1];
				for (ValidationRule validateObj : validationRules) {
					if (StringUtils.equalsIgnoreCase(fmtCodeFinal, validateObj.getValidationRuleCode())) {
						designDetail.setItemType(validateObj.getItemType());
						break;
					}
				}
			}
		}
	}

	/**
	 * check same latest base type
	 * @param baseTypeList
	 * @param baseType
	 * @return
	 */
	private static boolean checkBaseType(String baseTypeList, Integer baseType) {
		if (StringUtils.isBlank(baseTypeList))
			return false;

		String[] baseTypeArr = baseTypeList.split(CHAR_SPLIT);

		if (baseType.equals(Integer.valueOf(baseTypeArr[baseTypeArr.length - 1]))) {
			return true;
		}
		return false;
	}
	
	/*private static boolean hasBaseType(String baseTypeList, Integer baseType) {
		String[] baseTypeArr = baseTypeList.split(CHAR_SPLIT);

		for (int i = 0; i < baseTypeArr.length; i++) {
			if (baseType.equals(Integer.valueOf(baseTypeArr[i]))) {
				return true;
			}
		}
		return false;
	}*/
	
	/**
	 * validate range of numeric value : Integer, BigInteger, SmallInt , Serial, BigSerial
	 * @param basetypeId
	 * @param inputValue
	 * @return List with element at 0 : min value - 1: max value
	 * 
	 */
	public static List<String> checkValidValueOfBaseType(Integer basetypeId, String strInput) {
		if (FunctionCommon.isEmpty(basetypeId) || FunctionCommon.isEmpty(strInput)) {
			return null;
		}
		
		/*Integer baseTypeValue = Integer.parseInt(basetypeId);*/
		// if base type is : Integer
		if (DbDomainConst.PhysicalDataTypeDetail.INTEGER.equals(basetypeId)) {
			List<String> listReturn = new ArrayList<String>();
			listReturn.add(String.valueOf(Integer.MIN_VALUE));
			listReturn.add(String.valueOf(Integer.MAX_VALUE));

			Integer inputValue;
			try {
				inputValue = Integer.parseInt(strInput);
			} catch (NumberFormatException ne) {
				return listReturn;
			}
			if( inputValue.intValue() < Integer.MIN_VALUE || Integer.MAX_VALUE < inputValue.intValue()) {
				return listReturn;
			}
		// if base type is : Smallint
		} else if (DbDomainConst.PhysicalDataTypeDetail.SMALLINT.equals(basetypeId)) {
			List<String> listReturn = new ArrayList<String>();
			listReturn.add(String.valueOf(Short.MIN_VALUE));
			listReturn.add(String.valueOf(Short.MAX_VALUE));
			
			Short inputValue;
			try {
				inputValue = Short.parseShort(strInput);
			} catch (NumberFormatException ne) {
				return listReturn;
			}
			if (inputValue.shortValue() < Short.MIN_VALUE || Short.MAX_VALUE < inputValue.shortValue()) {
				return listReturn;
			}
		}
		// if base type is : Bigint
		else if (DbDomainConst.PhysicalDataTypeDetail.BIGINT.equals(basetypeId)) {
			List<String> listReturn = new ArrayList<String>();
			listReturn.add(String.valueOf(Long.MIN_VALUE));
			listReturn.add(String.valueOf(Long.MAX_VALUE));
			Long inputValue;
			try {
				inputValue = Long.parseLong(strInput);
			} catch (NumberFormatException ne) {
				return listReturn;
			}
			if(inputValue.longValue() < Long.MIN_VALUE || Long.MAX_VALUE < inputValue.longValue()) {
				return listReturn;
			}
		}
		// if base type is : Serial
		else if (DbDomainConst.PhysicalDataTypeDetail.SERIAL.equals(basetypeId)) {
			List<String> listReturn = new ArrayList<String>();
			listReturn.add(String.valueOf(SERIAL_MIN));
			listReturn.add(String.valueOf(Integer.MAX_VALUE));
			
			Integer inputValue;
			try {
				inputValue = Integer.parseInt(strInput);
			} catch (NumberFormatException ne) {
				return listReturn;
			}
			if(inputValue.intValue() < SERIAL_MIN || Integer.MAX_VALUE < inputValue.intValue() ) {
				return listReturn;
			}
		}
		// if base type is : BigSerial
		else if (DbDomainConst.PhysicalDataTypeDetail.BIGSERIAL.equals(basetypeId)) {
			List<String> listReturn = new ArrayList<String>();
			listReturn.add(String.valueOf(BIGSERIAL_MIN));
			listReturn.add(String.valueOf(Long.MAX_VALUE));
			Long inputValue;
			try {
				inputValue = Long.parseLong(strInput);
			} catch (NumberFormatException ne) {
				return listReturn;
			}
			if(inputValue.longValue() < BIGSERIAL_MIN || Long.MAX_VALUE < inputValue.longValue()) {
				return listReturn;
			}
		}
		
		// if base type is : Float
		else if (DbDomainConst.PhysicalDataTypeDetail.FLOAT.equals(basetypeId)) {
			List<String> listReturn = new ArrayList<String>();
			listReturn.add(String.valueOf(Float.MIN_VALUE));
			listReturn.add(String.valueOf(Float.MAX_VALUE));
			Float inputValue;
			try {
				inputValue = Float.parseFloat(strInput);
			} catch (NumberFormatException ne) {
				return listReturn;
			}
			if(inputValue.floatValue() < Float.MIN_VALUE || Float.MAX_VALUE < inputValue.floatValue()) {
				return listReturn;
			}
		}
		
		// if base type is : Double
		else if (DbDomainConst.PhysicalDataTypeDetail.DOUBLE.equals(basetypeId)) {
			List<String> listReturn = new ArrayList<String>();
			listReturn.add(String.valueOf(Double.MIN_VALUE));
			listReturn.add(String.valueOf(Double.MAX_VALUE));
			Double inputValue;
			try {
				inputValue = Double.parseDouble(strInput);
			} catch (NumberFormatException ne) {
				return listReturn;
			}
			if(inputValue.doubleValue() < Double.MIN_VALUE || Double.MAX_VALUE < inputValue.doubleValue()) {
				return listReturn;
			}
		}
		return null;
	}
	
	public static Integer initializeMaxLengthDefault(Integer baseType) {
		Integer maxLength = 0;
		switch (baseType) {
		case DbDomainConst.BaseType.CHARACTER_VARYING_BASETYPE:
			maxLength = 200;
			break;
		case DbDomainConst.BaseType.CHAR_BASETYPE:
			maxLength = 50;
			break;
		case DbDomainConst.BaseType.TEXT_BASETYPE:
			maxLength = 2000;
			break;
		case DbDomainConst.BaseType.NUMERIC_BASETYPE:
			maxLength = 16;
			break;
		case DbDomainConst.BaseType.INTEGER_BASETYPE:
		case DbDomainConst.BaseType.SERIAL_BASETYPE:
			maxLength = 10;
			break;
		case DbDomainConst.BaseType.SMALLINT_BASETYPE:
			maxLength = 6;
			break;
		case DbDomainConst.BaseType.BIGINT_BASETYPE:
		case DbDomainConst.BaseType.BIGSERIAL_BASETYPE:
			maxLength = 20;
			break;
		case DbDomainConst.BaseType.BOOLEAN_BASETYPE:
			maxLength = 4;
			break;
		case DbDomainConst.BaseType.DATE_BASETYPE:
			maxLength = 10;
			break;
		case DbDomainConst.BaseType.TIME_BASETYPE:
			maxLength = 8;
			break;
		case DbDomainConst.BaseType.DATETIME_BASETYPE:
			maxLength = 19;
			break;
		case DbDomainConst.BaseType.CURRENCY_BASETYPE:
			maxLength = 23;
			break;
		case DbDomainConst.BaseType.TIMESTAMP_BASETYPE:
			maxLength = 23;
			break;
		case DbDomainConst.BaseType.FLOAT_BASETYPE:
			maxLength = 20;
			break;
		default:
			break;
		}
		return maxLength;
	}
	
	public static boolean checkExistFk(Long fromColumn, Long toColumn, Map<Long, Long> mapFk) {
		if (mapFk != null & mapFk.size() > 0 && (DataTypeUtils.equals(mapFk.get(fromColumn), toColumn) || DataTypeUtils.equals(mapFk.get(toColumn), fromColumn))) {
			return true;
		}
		return false;
	}
	
	
	public static String genSeqName(String tableCode, TableDesignDetails column, GenerateUniqueKey generateUniqueKey){
		if (StringUtils.isBlank(column.getDefaultValue()) && ( 1 == column.getAutoIncrementFlag() || (1 == column.isPrimaryKey() && checkSpecialBasetype(column.getBaseType())))) {
			return generateUniqueKey.calculateCodeForManual(MessageFormat.format(SEQ_TEMPLATE, tableCode, column.getCode(), GenerateUniqueKey.generateRandomInteger(), true));	
		}
		return null;
	}
	
	
	public static boolean checkSpecialBasetype(Integer baseType) {
		if (DbDomainConst.PhysicalDataTypeDetail.BIGSERIAL.equals(baseType) || DbDomainConst.PhysicalDataTypeDetail.SERIAL.equals(baseType)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param baseType
	 * @return
	 */
	/*public static boolean checkBaseTypeNoInitMaxlength(Integer baseType) {
		if (DbDomainConst.PhysicalDataTypeDetail.CHAR.equals(baseType) || DbDomainConst.PhysicalDataTypeDetail.CHARACTER_VARYING.equals(baseType) || DbDomainConst.PhysicalDataTypeDetail.NUMBERIC.equals(baseType) || DbDomainConst.PhysicalDataTypeDetail.CURRENTCY.equals(baseType)) {
			return false;
		}
		return true;
	}*/
}
