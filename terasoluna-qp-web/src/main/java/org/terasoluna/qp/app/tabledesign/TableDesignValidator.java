package org.terasoluna.qp.app.tabledesign;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.DateUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.domaindesign.DomainDesignUtil;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.DomainDesignMessageConst;
import org.terasoluna.qp.app.message.GraphicDatabaseDesignMessageConst;
import org.terasoluna.qp.app.message.ScreenDesignMessageConst;
import org.terasoluna.qp.app.message.SubjectAreaMessageConst;
import org.terasoluna.qp.app.message.TableDesignMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.TableDesignKey;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeConst;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeUtil;
import org.terasoluna.qp.domain.service.graphicdatabasedesign.TableDesignUtil;

@Component
public class TableDesignValidator implements Validator {
	
	private static final String REPLACEMENT_CHARACTER_SYSBOL = "�";
	
	private String [] reservedWords;
	private int maxlengthDBMS;
	
	@Inject 
	SystemService systemService;
	
	@Inject
	Mapper beanMapper;

	@Override
	public boolean supports(Class<?> clazz) {
		return (TableDesignForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		String dbType = SessionUtils.getCurrentDatabaseType();

		reservedWords = systemService.databaseReservedWords(dbType);
		if (DataTypeUtils.equals(dbType, DbDomainConst.DatabaseType.ORACLE)) {
			maxlengthDBMS = systemService.getDefaultProfile().getMaxLengthOracleDBMS();
		} else {
			maxlengthDBMS = systemService.getDefaultProfile().getMaxLengthPostgreDBMS();
		}

		TableDesignForm tableDesignForm = (TableDesignForm) target;
		this.validateSubjectArea(tableDesignForm, errors);
		if(!DbDomainConst.TableDesignType.QP_TABLE.equals(tableDesignForm.getType())){
			this.validateTableDetails(tableDesignForm, errors);
			this.validateMaxlength(tableDesignForm, errors);
			this.validateKeyName(tableDesignForm, errors);
		}
	}
	
	
	/**
	 * 
	 * @param tableDesignForm
	 * @param errors
	 */
	private void validateTableDetails(TableDesignForm tableDesignForm, Errors errors){
		List<TableDesignDetailsForm> tableDesignDetails = tableDesignForm.getListTableDesignDetails();
		AccountProfile accountProfile = systemService.getDefaultProfile();
		String dataType = SessionUtils.getCurrentDatabaseType();
		
		Integer maxLengOfCode = accountProfile.getSqlCodeMaxLengthByDbType(dataType);
		
		// Validate Table Name
		if(FunctionCommon.equals(tableDesignForm.getTableName(), null)){
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0019)}, null);
		}else{
			if(tableDesignForm.getTableName().length() > accountProfile.getNameMaxLength()){
				errors.reject(CommonMessageConst.ERR_SYS_0064, new Object[] { MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0019), accountProfile.getNameMinLength(), accountProfile.getNameMaxLength()}, null);
			}
			if(!Pattern.matches(accountProfile.getNamePattern(), tableDesignForm.getTableName())){
				errors.reject(CommonMessageConst.ERR_SYS_0126, new Object[] { MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0019), accountProfile.getNameMask()}, null);
			}
		}
		
		// Validate Table Code
		if(FunctionCommon.equals(tableDesignForm.getTableCode(), null)){
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0020)}, null);
		} else {
			if(tableDesignForm.getTableCode().length() > maxLengOfCode){
				errors.reject(CommonMessageConst.ERR_SYS_0064, new Object[] { MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0020), accountProfile.getCodeMinLength(), maxLengOfCode}, null);
			}
			if(!Pattern.matches(accountProfile.getCodePattern(), tableDesignForm.getTableCode())){
				errors.reject(CommonMessageConst.ERR_SYS_0066, new Object[] { MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0020, accountProfile.getCodeMask())}, null);
			}
			if (FunctionCommon.checkExists(reservedWords, tableDesignForm.getTableCode())) {
				errors.reject(CommonMessageConst.ERR_SYS_0130, new Object[] { MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0020)}, null);
			}
			
			ValidationUtils.validateReservedJava(tableDesignForm.getTableCode(), errors, CommonMessageConst.ERR_SYS_0018, new Object[] { MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0020)});
		}
		
		// Check codelist detail is duplicated or not
		Set<String> valueSet = new HashSet<String>();
		Set<String> nameSet = new HashSet<String>();
		
		if(FunctionCommon.isNotEmpty(tableDesignDetails)) {
			for (int i = 1; i <= tableDesignDetails.size(); i++) {

				String columnName = tableDesignDetails.get(i-1).getName();
				if(FunctionCommon.equals(columnName, null)){
					errors.reject(CommonMessageConst.ERR_SYS_0120, new Object[] { MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0021), i, MessageUtils.getMessage("sc.tabledesign.0014") }, null);
				}else{
					if(columnName.length() > accountProfile.getNameMaxLength()){
						errors.reject(CommonMessageConst.ERR_SYS_0095, new Object[] { MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0021), accountProfile.getNameMinLength(), accountProfile.getNameMaxLength() , i}, null);
					}
					if(!Pattern.matches(accountProfile.getNamePattern(), columnName)){
						errors.reject(CommonMessageConst.ERR_SYS_0128, new Object[] { MessageUtils.getMessage("sc.tabledesign.0014"),MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0021), i, accountProfile.getNameMask() }, null);
					}
				}
				
				String columnCode = tableDesignDetails.get(i-1).getCode();
				if (FunctionCommon.equals(columnCode, null)) {
					errors.reject(CommonMessageConst.ERR_SYS_0120, new Object[] { MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0022), i, MessageUtils.getMessage("sc.tabledesign.0014") }, null);
				} else {
					if(columnCode.length() > maxLengOfCode){
						errors.reject(CommonMessageConst.ERR_SYS_0095, new Object[] { MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0022), accountProfile.getCodeMinLength(), maxLengOfCode , i}, null);
					}
					if(!Pattern.matches(accountProfile.getCodePattern(), columnCode)){
						errors.reject(CommonMessageConst.ERR_SYS_0107, new Object[] { MessageUtils.getMessage("sc.tabledesign.0014"), MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0022), i}, null);
					}
					if (FunctionCommon.checkExists(reservedWords, columnCode)) {
						errors.reject(CommonMessageConst.ERR_SYS_0131, new Object[] { MessageUtils.getMessage("sc.tabledesign.0014"), MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0022), i}, null);
					}

					ValidationUtils.validateReservedJava(columnCode, errors, CommonMessageConst.ERR_SYS_0096, new Object[] {MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0022), i});
				}

				String[] argsName =  { MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0021), String.valueOf(i) };
				ValidationUtils.validateExistInListIgnoreCase(columnName, nameSet, errors, CommonMessageConst.ERR_SYS_0041, argsName);
				
				String[] argsCode =  { MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0022), String.valueOf(i) };
				ValidationUtils.validateExistInListIgnoreCase(columnCode, valueSet, errors, CommonMessageConst.ERR_SYS_0041, argsCode);
			}
		}else{
			errors.reject(CommonMessageConst.ERR_SYS_0104, new Object[] { MessageUtils.getMessage("sc.tabledesign.0014") }, null);
		}
	}

	/**
	 * 
	 * @param tableDesignForm
	 * @param errors
	 */
	private void validateMaxlength(TableDesignForm tableDesignForm,Errors errors) {
		AccountProfile accountProfile = systemService.getDefaultProfile();
		if (tableDesignForm.getListTableDesignDetails() != null && tableDesignForm.getListTableDesignDetails().size() > 0) {
			
			int index = 1;
			
			for (TableDesignDetailsForm tableDesignDetails : tableDesignForm.listTableDesignDetails) {
				// Check maxlength Primitive Type
				if(DbDomainConst.DataTypeFlag.PRIMITIVE.equals(tableDesignDetails.getDataTypeFlg())){
					
					if (FunctionCommon.equals(DomainDatatypeConst.PhysicalDataTypeDetail.TEXT, tableDesignDetails.getDataType()) || FunctionCommon.equals(DomainDatatypeConst.PhysicalDataTypeDetail.CHAR, tableDesignDetails.getDataType()) || FunctionCommon.equals(DomainDatatypeConst.PhysicalDataTypeDetail.CHARACTER_VARYING, tableDesignDetails.getDataType())) {
						if (FunctionCommon.notEquals(DomainDatatypeConst.PhysicalDataTypeDetail.TEXT, tableDesignDetails.getDataType())) {
							if (FunctionCommon.isEmpty(tableDesignDetails.getMaxlength())) {
								errors.reject(CommonMessageConst.ERR_SYS_0077, new Object[] { MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0006), index }, null);
							} else if (tableDesignDetails.getMaxlength() > accountProfile.getRemarkMaxLength()) {
								errors.reject(TableDesignMessageConst.ERR_TABLEDESIGN_0004, new Object[] { index, accountProfile.getRemarkMaxLength() }, null);
							}
						}
						
						if (StringUtils.isNoneBlank(tableDesignDetails.getDefaultValue())){
							int maxlengthCompare = (FunctionCommon.isEmpty(tableDesignDetails.getMaxlength()) || tableDesignDetails.getMaxlength().intValue() > accountProfile.getRemarkMaxLength()) ? accountProfile.getRemarkMaxLength() : tableDesignDetails.getMaxlength().intValue();
							if (maxlengthCompare < tableDesignDetails.getDefaultValue().length()) {
								errors.reject(TableDesignMessageConst.ERR_TABLEDESIGN_0004, new Object[] { index, maxlengthCompare }, null);
							}
						}

					} 
//					else if (DomainDatatypeConst.PhysicalDataTypeDetail.INTEGER.equals(tableDesignDetails.getDataType()) 
//							|| DomainDatatypeConst.PhysicalDataTypeDetail.SMALLINT.equals(tableDesignDetails.getDataType()) 
//							|| DomainDatatypeConst.PhysicalDataTypeDetail.BIGINT.equals(tableDesignDetails.getDataType()) 
//							|| DomainDatatypeConst.PhysicalDataTypeDetail.SERIAL.equals(tableDesignDetails.getDataType()) 
//							|| DomainDatatypeConst.PhysicalDataTypeDetail.BIGSERIAL.equals(tableDesignDetails.getDataType())
//							|| DomainDatatypeConst.PhysicalDataTypeDetail.BYTE.equals(tableDesignDetails.getDataType())
//							|| DomainDatatypeConst.PhysicalDataTypeDetail.FLOAT.equals(tableDesignDetails.getDataType())
//							|| DomainDatatypeConst.PhysicalDataTypeDetail.DOUBLE.equals(tableDesignDetails.getDataType())
//							) {
//						// if is integer then validate default
//						List<String> checkOutOfRange = TableDesignUtil.checkValidValueOfBaseType(Integer.parseInt(tableDesignDetails.getDataType()), tableDesignDetails.getDefaultValue());
//						if (!FunctionCommon.isEmpty(checkOutOfRange)) {
//							errors.reject(TableDesignMessageConst.ERR_TABLEDESIGN_0005, new Object[] { tableDesignDetails.getName(), index , checkOutOfRange.get(0), checkOutOfRange.get(1) }, null);
//						}
//					}
					
					// Check Maxlength and precision
					if (DbDomainConst.PhysicalDataType.DECIMAL.equals(tableDesignDetails.getGroupBaseTypeId()) || DbDomainConst.PhysicalDataType.CURRENCY.equals(tableDesignDetails.getGroupBaseTypeId())){
						if(tableDesignDetails.getMaxlength() != null && tableDesignDetails.getDecimalPart() != null){
							if(tableDesignDetails.getMaxlength().intValue() <= tableDesignDetails.getDecimalPart().intValue()){
								errors.reject(DomainDesignMessageConst.SC_DOMAINDESIGN_0062, null, null);
							}
						}
					}
					
					
					
//					switch (tableDesignDetails.getGroupBaseTypeId()) {
//						case 1:
//							//don't validate of max length text
//							if (FunctionCommon.equals(DomainDatatypeConst.PhysicalDataTypeDetail.TEXT, tableDesignDetails.getDataType())) {
//								break;
//							}
//						case 5:
//						case 6:
//							if (tableDesignDetails.getMaxlength() == null) {
//								errors.reject(CommonMessageConst.ERR_SYS_0077, new Object[] { MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0006), index }, null);
//							} else if (defaultValue != null) {
//								if (maxlength < defaultValue.length()) {
//									errors.reject(TableDesignMessageConst.ERR_TABLEDESIGN_0001, new Object[] { index }, null);
//								}
//							}
//						break;
//						case 3:
//							if (DbDomainConst.PhysicalDataTypeDetail.NUMBERIC.equals(tableDesignDetails.getBaseType())) {
//								if (tableDesignDetails.getMaxlength() == null) {
//									errors.reject(CommonMessageConst.ERR_SYS_0077, new Object[] { MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0006), index }, null);
//								} else if (defaultValue != null) {
//									if (maxlength < defaultValue.length()) {
//										errors.reject(TableDesignMessageConst.ERR_TABLEDESIGN_0001, new Object[] { index }, null);
//									}
//								}
//							}
//							break;
//					}
					
					///////
					Integer groupBasetype = tableDesignDetails.getGroupBaseTypeId();
					
					// validate minVal and maxVal
					String minVal = "", maxVal = "", defaultVal = "";
					minVal = tableDesignDetails.getMinVal();
					maxVal = tableDesignDetails.getMaxVal();
					defaultVal = tableDesignDetails.getDefaultValue();

					if (groupBasetype == null)
						return;
					try {

						if (DbDomainConst.PhysicalDataType.INTEGER.equals(groupBasetype)
								|| DbDomainConst.PhysicalDataType.DECIMAL.equals(groupBasetype)
								|| DbDomainConst.PhysicalDataType.CURRENCY.equals(groupBasetype)) {
							
							if (DomainDesignUtil.compareObject(maxVal, minVal, groupBasetype) < 0) {
								String[] param = {
										MessageUtils
										.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0029), String.valueOf(index),
										MessageUtils
										.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0028) };
								errors.reject(CommonMessageConst.ERR_SYS_0085, param, null);
							}

							// check range for numeric
							if (tableDesignDetails.getOperatorCode() != "0" && DbDomainConst.ConstrainsType.RANGE.equals(tableDesignDetails.getConstrainsType())) {
								validateRange(minVal, maxVal, defaultVal, groupBasetype, tableDesignDetails.getOperatorCode(),errors, index);
							}

							/*if (DataTypeUtils.equals(tableDesignDetails.getDefaultType(), DbDomainConst.YesNoFlg.NO) && DomainDesignUtil.compareObject(maxVal, defaultVal, groupBasetype) < 0) {
								String[] param = {
										MessageUtils
										.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0004),String.valueOf(index),
										MessageUtils
										.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0029) };
								errors.reject(CommonMessageConst.ERR_SYS_0084, param, null);
							}
							
							if (DataTypeUtils.equals(tableDesignDetails.getDefaultType(), DbDomainConst.YesNoFlg.NO) && DomainDesignUtil.compareObject(defaultVal, minVal, groupBasetype) < 0) {
								String[] param = {
										MessageUtils
										.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0004),String.valueOf(index),
										MessageUtils
										.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0028) };
								
								errors.reject(CommonMessageConst.ERR_SYS_0085, param, null);
							}*/

							//If group base type is integer then check value has out of range
							if (DbDomainConst.PhysicalDataType.INTEGER.equals(groupBasetype)) {
								Integer dataType = tableDesignDetails.getBaseType();
								//validate value of minval
								if (!DomainDatatypeUtil.isEmpty(minVal)) {
									List<String> listCheck = TableDesignUtil.checkValidValueOfBaseType(dataType, minVal);
									if (!DomainDatatypeUtil.isEmpty(listCheck)) {
										String[] param = { MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0028), listCheck.get(0), listCheck.get(1)};
										errors.reject(CommonMessageConst.ERR_SYS_0103, param, null);
									}
								}
								//validate value of maxval
								if (!DomainDatatypeUtil.isEmpty(maxVal)) {
									List<String> listCheck = TableDesignUtil.checkValidValueOfBaseType(dataType, maxVal);
									if (!DomainDatatypeUtil.isEmpty(listCheck)) {
										String[] param = { MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0029), listCheck.get(0), listCheck.get(1)};
										errors.reject(CommonMessageConst.ERR_SYS_0103, param, null);
									}
								}
								//validate value of default value
								if (!DomainDatatypeUtil.isEmpty(defaultVal)) {
									List<String> listCheck = TableDesignUtil.checkValidValueOfBaseType(dataType, defaultVal);
									if (!DomainDatatypeUtil.isEmpty(listCheck)) {
										String[] param = { MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0004), listCheck.get(0), listCheck.get(1)};
										errors.reject(CommonMessageConst.ERR_SYS_0103, param, null);
									}
								}
							}
						}

						validateDateTimeAdvance(tableDesignDetails, errors, groupBasetype, index);

						// validate maxlength
						try {
							Integer maxLength = tableDesignDetails.getMaxlength();
							int precision = tableDesignDetails.getDecimalPart()==null? 0: tableDesignDetails.getDecimalPart().intValue();

							if (DomainDatatypeUtil.isEmpty(maxLength)) {
								if (/*DbDomainConst.PhysicalDataType.TEXT.equals(groupBasetype)
										|| DbDomainConst.PhysicalDataType.CHAR.equals(groupBasetype)
										|| */ DbDomainConst.PhysicalDataType.CURRENCY.equals(groupBasetype)
										|| (DbDomainConst.PhysicalDataType.DECIMAL.equals(groupBasetype)
											&& (tableDesignDetails.getBaseType() != null && tableDesignDetails.getBaseType().equals(DbDomainConst.BaseType.NUMERIC_BASETYPE)))) {
								errors.reject(
										CommonMessageConst.ERR_SYS_0026,
										new String[] { MessageUtils
												.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0006), String.valueOf(index) },
										null);
								}
							} else {
								if (DbDomainConst.PhysicalDataType.INTEGER.equals(groupBasetype)
										|| DbDomainConst.PhysicalDataType.DECIMAL.equals(groupBasetype)
										|| DbDomainConst.PhysicalDataType.CURRENCY.equals(groupBasetype)) {
									minVal = minVal == null ? null : minVal.replace(".", "").replace("-", "");
									minVal = maxVal == null ? null : maxVal.replace(".", "").replace("-", "");
									defaultVal = defaultVal == null ? null : defaultVal.replace(".", "").replace("-", "");
								}
								//validate max length of min value
								if (!DomainDatatypeUtil.isEmpty(minVal) && minVal.length() > (maxLength.intValue() + precision)) {
									String[] param = { MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0028), String.valueOf(index), String.valueOf(maxLength.intValue())};
										errors.reject(CommonMessageConst.ERR_SYS_0021, param, null);
								}
								//validate max length of max value
								if (!DomainDatatypeUtil.isEmpty(maxVal) && maxVal.length() > (maxLength.intValue() + precision)) {
									String[] param = { MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0029), String.valueOf(index), String.valueOf(maxLength.intValue())};
										errors.reject(CommonMessageConst.ERR_SYS_0021, param, null);
								}
								//validate max length of default value
								if (!DomainDatatypeUtil.isEmpty(defaultVal) && defaultVal.length() > (maxLength.intValue() + precision)) {
									String[] param = { MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0004), String.valueOf(index), String.valueOf(maxLength.intValue())};
										errors.reject(CommonMessageConst.ERR_SYS_0021, param, null);
								}
							}
						} catch (Exception e) {
							errors.reject(
									CommonMessageConst.ERR_SYS_0028,
									new String[] { String.valueOf(index), MessageUtils
											.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0006) },
									null);
						}
					} catch (Exception e) {
					}
				}
				index++;
			}
		}
	}

	/**
	 * 
	 * @param tableDesignForm
	 * @param errors
	 */
	private void validateSubjectArea(TableDesignForm tableDesignForm,Errors errors) {
		
		if (FunctionCommon.isNotEmpty(tableDesignForm.getSubjectAreas())) {
			Set<String> nameSet = new HashSet<String>();
			int i = 1;
			for (SubjectAreaForm subjectAreaForm : tableDesignForm.getSubjectAreas()) {
				if(FunctionCommon.equals(subjectAreaForm.getAreaId(), null)){
					errors.reject(CommonMessageConst.ERR_SYS_0077, new Object[] { MessageUtils.getMessage(SubjectAreaMessageConst.SC_SUBAREADESIGN_0004), i }, null);
				}
				if (nameSet.contains(subjectAreaForm.getAreaId())) {
					String[] args =  { MessageUtils.getMessage(SubjectAreaMessageConst.SC_SUBAREADESIGN_0004), String.valueOf(i) };
					errors.reject(CommonMessageConst.ERR_SYS_0041, args , null);
				} else {
					if(StringUtils.isNotBlank(subjectAreaForm.getAreaId())) {
						nameSet.add(subjectAreaForm.getAreaId());
					}
				}
				i++;
			}
		}
	}
	
	/**
	 * 
	 * @param tableDesignForm
	 * @param errors
	 */
	private void validateKeyName(TableDesignForm tableDesignForm, Errors errors) {
		List<TableDesignKey> tableDesignKeys =  tableDesignForm.getListTableDesignKey();
		List<TableDesignForeignKeyForm> tableDesignForeignKeys = tableDesignForm.getTableDesignForeignKeys();
		
		if(CollectionUtils.isNotEmpty(tableDesignKeys)){
			for (TableDesignKey tableDesignKey : tableDesignKeys) {

				if (StringUtils.isBlank(tableDesignKey.getStrKeyItems())) {
					continue;
				}

				String keyItems[] = tableDesignKey.getStrKeyItems().split(REPLACEMENT_CHARACTER_SYSBOL);

				//validate key code
				validateKeyCode (keyItems[1], errors,GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0117);

				//validate item
				if (keyItems.length > 2 && StringUtils.isBlank(keyItems[2])) {
					errors.reject(CommonMessageConst.ERR_SYS_0104, new Object[] { keyItems[1]}, null);
				}

			}
		}
		
		if(CollectionUtils.isNotEmpty(tableDesignForeignKeys)){
			int index = 1;
			for (TableDesignForeignKeyForm tableDesignForeignKeyForm : tableDesignForeignKeys) {
				validateKeyCode(tableDesignForeignKeyForm.getForeignKeyCode(), errors, TableDesignMessageConst.SC_TABLEDESIGN_0027);

				if (StringUtils.isBlank(tableDesignForeignKeyForm.getFromColumnCode()) && tableDesignForeignKeyForm.getFromColumnId() == null ) {
					errors.reject(CommonMessageConst.ERR_SYS_0077, new Object[] { MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0028), index}, null);
				}

				if (StringUtils.isBlank(tableDesignForeignKeyForm.getToTableCode()) && tableDesignForeignKeyForm.getToTableId() == null) {
					errors.reject(CommonMessageConst.ERR_SYS_0077, new Object[] { MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0029), index}, null);
				}

				if (StringUtils.isBlank(tableDesignForeignKeyForm.getToColumnCode()) && tableDesignForeignKeyForm.getToColumnId() == null) {
					errors.reject(CommonMessageConst.ERR_SYS_0077, new Object[] { MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0030), index}, null);
				}

				//
				if ((DataTypeUtils.equals(tableDesignForm.getTableDesignId(), tableDesignForeignKeyForm.getToTableId())
						|| DataTypeUtils.equals(tableDesignForm.getTableCode(), tableDesignForeignKeyForm.getToTableCode()))
					&& (DataTypeUtils.equals(tableDesignForeignKeyForm.getFromColumnId(), tableDesignForeignKeyForm.getToColumnId())
							|| DataTypeUtils.equals(tableDesignForeignKeyForm.getFromColumnCode(), tableDesignForeignKeyForm.getToColumnCode()))) {
					errors.reject(CommonMessageConst.ERR_SYS_0096, new Object[] { MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0056), index}, null);
				}

				index++;
			}
		}
	}


	private void validateDateTimeAdvance(TableDesignDetailsForm tableDesignDetailsForm, Errors errors, Integer groupBasetype, int index) {
		
		if (DbDomainConst.PhysicalDataType.DATE.equals(groupBasetype)
				|| DbDomainConst.PhysicalDataType.DATETIME.equals(groupBasetype)
				|| DbDomainConst.PhysicalDataType.TIME.equals(groupBasetype)) {
			
			String pattern = "";
			if(DbDomainConst.PhysicalDataType.DATE.equals(groupBasetype)){
				pattern = DateUtils.getPatternDate(SessionUtils.getCurrentAccountProfile().getDateFormat());
			}else if(DbDomainConst.PhysicalDataType.DATETIME.equals(groupBasetype)){
				pattern = DateUtils.getPatternDateTime(SessionUtils.getCurrentAccountProfile().getDateTimeFormat());
			}else if(DbDomainConst.PhysicalDataType.TIME.equals(groupBasetype)){
				pattern = DateUtils.getPatternTime(SessionUtils.getCurrentAccountProfile().getTimeFormat());
			}
			
			Timestamp maxValue = null;
			Timestamp minValue = null;
			Timestamp defaultValue = null;
			
			
			if(DbDomainConst.ConstrainsType.RANGE.equals(tableDesignDetailsForm.getConstrainsType())){
				if(DbDomainConst.DefaultType.FUNCTION.equals(tableDesignDetailsForm.getDefaultType())){
					try {
						switch (tableDesignDetailsForm.getOperatorCode()) {
						case "1":
						case "2":
						case "3":
							if(tableDesignDetailsForm.getMaxVal() == null){
								errors.reject(CommonMessageConst.ERR_SYS_0025, new String[] { MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0043) }, null);
							}
							break;
						case "4":
						case "5":
						case "6":
							if(tableDesignDetailsForm.getMinVal() == null){
								errors.reject(CommonMessageConst.ERR_SYS_0025, new String[] { MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0043) }, null);
							}
							break;
						case "8":
							if(tableDesignDetailsForm.getMaxVal() == null){
								errors.reject(CommonMessageConst.ERR_SYS_0025, new String[] { MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0043) }, null);
							}else if(tableDesignDetailsForm.getMinVal() == null){
								errors.reject(CommonMessageConst.ERR_SYS_0025, new String[] { MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0043) }, null);
							}else{
								
									maxValue = DateUtils.parse(tableDesignDetailsForm.getMaxVal(), pattern);
									minValue = DateUtils.parse(tableDesignDetailsForm.getMinVal(), pattern);
									
									if(DateUtils.compare(minValue, maxValue) == 1){
										errors.reject(CommonMessageConst.ERR_SYS_0050, new String[] { MessageUtils .getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0176), MessageUtils .getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0177) }, null);
									}
							}
							break;
						}
					} catch (ParseException e) {
						
					}
				}else if(tableDesignDetailsForm.getDefaultType() == null || DbDomainConst.DefaultType.TEXT.equals(tableDesignDetailsForm.getDefaultType())){
					try {
						if(tableDesignDetailsForm.getDefaultValue() != null){
							defaultValue = DateUtils.parse(tableDesignDetailsForm.getDefaultValue(), pattern);
						}
						switch (tableDesignDetailsForm.getOperatorCode()) {
						case "1":
							if(tableDesignDetailsForm.getMaxVal() == null){
								errors.reject(CommonMessageConst.ERR_SYS_0025, new String[] { MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0043) }, null);
							}else{
								maxValue = DateUtils.parse(tableDesignDetailsForm.getMaxVal(), pattern);
								if(DateUtils.compare(maxValue, defaultValue) != 0){
									errors.reject(DomainDesignMessageConst.SC_DOMAINDESIGN_0054, new String[] {}, null);
								}
							}
							break;
						case "2":
							if(tableDesignDetailsForm.getMaxVal() == null){
								errors.reject(CommonMessageConst.ERR_SYS_0025, new String[] { MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0043) }, null);
							}else{
								maxValue = DateUtils.parse(tableDesignDetailsForm.getMaxVal(), pattern);
								if(DateUtils.compare(maxValue, defaultValue) == 0 || DateUtils.compare(maxValue, defaultValue) == -1){
									errors.reject(DomainDesignMessageConst.SC_DOMAINDESIGN_0055, new String[] {}, null);
								}
							}
							break;
						case "3":
							if(tableDesignDetailsForm.getMaxVal() == null){
								errors.reject(CommonMessageConst.ERR_SYS_0025, new String[] { MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0043) }, null);
							}else{
								maxValue = DateUtils.parse(tableDesignDetailsForm.getMaxVal(), pattern);
								if(DateUtils.compare(maxValue, defaultValue) == -1){
									errors.reject(DomainDesignMessageConst.SC_DOMAINDESIGN_0056, new String[] {}, null);
								}
							} 
							break;
						case "4":
							if(tableDesignDetailsForm.getMinVal() == null){
								errors.reject(CommonMessageConst.ERR_SYS_0025, new String[] { MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0043) }, null);
							}else{
								minValue = DateUtils.parse(tableDesignDetailsForm.getMinVal(), pattern);
								if(DateUtils.compare(minValue, defaultValue) == 1 || DateUtils.compare(minValue, defaultValue) == 0){
									errors.reject(DomainDesignMessageConst.SC_DOMAINDESIGN_0057, new String[] {}, null);
								}
							} 
						case "5":
							if(tableDesignDetailsForm.getMinVal() == null){
								errors.reject(CommonMessageConst.ERR_SYS_0025, new String[] { MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0043) }, null);
							}else{
								minValue = DateUtils.parse(tableDesignDetailsForm.getMinVal(), pattern);
								if(DateUtils.compare(minValue, defaultValue) == 1){
									errors.reject(DomainDesignMessageConst.SC_DOMAINDESIGN_0058, new String[] {}, null);
								}
							} 
						case "6":
							if(tableDesignDetailsForm.getMinVal() == null){
								errors.reject(CommonMessageConst.ERR_SYS_0025, new String[] { MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0043) }, null);
							}else{
								minValue = DateUtils.parse(tableDesignDetailsForm.getMinVal(), pattern);
								if(DateUtils.compare(minValue, defaultValue) == 0){
									errors.reject(DomainDesignMessageConst.SC_DOMAINDESIGN_0059, new String[] {}, null);
								}
							} 
							break;
						case "8":
							if(tableDesignDetailsForm.getMaxVal() == null){
								errors.reject(CommonMessageConst.ERR_SYS_0025, new String[] { MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0043) }, null);
							}else if(tableDesignDetailsForm.getMinVal() == null){
								errors.reject(CommonMessageConst.ERR_SYS_0025, new String[] { MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0043) }, null);
							}else{
								try {
									maxValue = DateUtils.parse(tableDesignDetailsForm.getMaxVal(), pattern);
									minValue = DateUtils.parse(tableDesignDetailsForm.getMinVal(), pattern);
									defaultValue = DateUtils.parse(tableDesignDetailsForm.getDefaultValue(), pattern);
									
									if(DateUtils.compare(minValue, maxValue) == 1){
										errors.reject(CommonMessageConst.ERR_SYS_0050, new String[] { MessageUtils .getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0177), MessageUtils .getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0176) }, null);
									}
									if(DateUtils.compare(minValue, defaultValue) == 1 || DateUtils.compare(defaultValue, maxValue) == 1){
										errors.reject(DomainDesignMessageConst.SC_DOMAINDESIGN_0053, null);
									}
								} catch (ParseException e) {
									
								}
							}
							break;
						}
					} catch (ParseException e) {
						
					}
				}
			}
		}
	}
	
	private void validateKeyCode(String code, Errors errors, String messageCode) {
		//Check empty
		if (StringUtils.isBlank(code)) {
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(messageCode) }, null);
		} else {

			ValidationUtils.validateMaskCode(code, errors, systemService.getDefaultProfile().getCodePattern(), messageCode);

			if (maxlengthDBMS < code.length()) {
				errors.reject(CommonMessageConst.ERR_SYS_0020, new Object[] { MessageUtils.getMessage(messageCode), maxlengthDBMS }, null);
			}

			if (FunctionCommon.checkExists(reservedWords, code)) {
				errors.reject(CommonMessageConst.ERR_SYS_0130, new Object[] { MessageUtils.getMessage(messageCode)}, null);
			}
		}
	}

	/**
	 * @param minVal
	 * @param maxVal
	 * @param defaultVal
	 * @param groupBasetype
	 * @param operatorCode
	 * @param errors
	 * @throws Exception
	 */
	private void validateRange(String minVal, String maxVal, String defaultVal, Integer groupBasetype, String operatorCode, Errors errors, int index) throws Exception {
		switch (operatorCode) {
		case "1" : 
			// =
			if(DomainDesignUtil.compareObject(maxVal, defaultVal, groupBasetype) != 0){
				String[] args = new String [] { MessageUtils .getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0004), MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0089), String.valueOf(index)};
				errors.reject(TableDesignMessageConst.SC_TABLEDESIGN_0086
						, args
						, null);
			}
			break;
		case "2" :
			// <
			if(DomainDesignUtil.compareObject(maxVal, defaultVal, groupBasetype) <= 0){
				String[] args = new String [] { MessageUtils .getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0004), MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0089), String.valueOf(index)};
				errors.reject(TableDesignMessageConst.SC_TABLEDESIGN_0084
						, args
						, null);
			}
			break;
		case "3" :
			// <=
			if(DomainDesignUtil.compareObject(maxVal, defaultVal, groupBasetype) < 0){
				String[] args = new String [] { MessageUtils .getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0004), MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0089), String.valueOf(index)};
				errors.reject(CommonMessageConst.ERR_SYS_0084
						, args
						, null);
			}
			break;
		case "4" :
			// >
			if(DomainDesignUtil.compareObject(minVal, defaultVal, groupBasetype) <= 0){
				String[] args = new String [] { MessageUtils .getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0004), MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0089), String.valueOf(index)};
				errors.reject(TableDesignMessageConst.SC_TABLEDESIGN_0088
						, args
						, null);
			}
			break;
		case "5" :
			// >=
			if(DomainDesignUtil.compareObject(minVal, defaultVal, groupBasetype) < 0){
				String[] args = new String [] { MessageUtils .getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0004), MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0089), String.valueOf(index)};
				errors.reject(CommonMessageConst.ERR_SYS_0085
						, args
						, null);
			}
			break;
		case "6" :
			// <>
			if(DomainDesignUtil.compareObject(minVal, defaultVal, groupBasetype) == 0){
				String[] args = new String [] { MessageUtils .getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0004), MessageUtils .getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0089), String.valueOf(index)};
				errors.reject(TableDesignMessageConst.SC_TABLEDESIGN_0087
						, args
						, null);
			}
			break;
		case "8" :
			//BETWEEN
			if(DomainDesignUtil.compareObject(minVal, defaultVal, groupBasetype) > 0 || DomainDesignUtil.compareObject(defaultVal, maxVal, groupBasetype) > 0){
				String[] args = new String [] { MessageUtils .getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0004), null, String.valueOf(index)};
				errors.reject(TableDesignMessageConst.SC_TABLEDESIGN_0088
						, args
						, null);
			}
			break;
	};
}

}
