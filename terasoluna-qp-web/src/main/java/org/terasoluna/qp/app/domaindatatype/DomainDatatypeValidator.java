package org.terasoluna.qp.app.domaindatatype;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.domaindesign.DomainDesignUtil;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.DomainDatatypeMessage;
import org.terasoluna.qp.app.message.DomainDesignMessageConst;
import org.terasoluna.qp.app.message.GraphicDatabaseDesignMessageConst;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeConst;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeUtil;
import org.terasoluna.qp.domain.service.graphicdatabasedesign.TableDesignUtil;

@Component
public class DomainDatatypeValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return (DomainDatatypeForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		DomainDatatypeForm datatypeForm = (DomainDatatypeForm) target;

		// validate form details
		List<String> listOfColumnName = new ArrayList<String>();
		List<String> listOfColumnCode = new ArrayList<String>();

		String strMsgField = null;
		String strCode = null;
		int line = 0;
		try {
			int size = datatypeForm.getDomainDatatypeItems().size();
			
			for (int i = 0; i < size; i++) {
				line = i + 1;
				/* strMsgContent = null; */
				DomainDatatypeItemForm itemForm = datatypeForm.getDomainDatatypeItems().get(i);

				// check domain column name duplicated
				if (DomainDatatypeUtil.checkExistInList(itemForm.getDomainColumnName(), listOfColumnName)) {
					String[] param = { MessageUtils.getMessage(DomainDatatypeMessage.SC_DOMAINDATATYPE_0010), String.valueOf(line) };
					errors.reject(CommonMessageConst.ERR_SYS_0041, param, null);
				} else {
					listOfColumnName.add(itemForm.getDomainColumnName());
				}
				// check domain column code duplicated
				if (DomainDatatypeUtil.checkExistInList(itemForm.getDomainColumnCode(), listOfColumnCode)) {
					String[] param = { MessageUtils.getMessage(DomainDatatypeMessage.SC_DOMAINDATATYPE_0011), String.valueOf(line) };
					errors.reject(CommonMessageConst.ERR_SYS_0041, param, null);
				} else {
					listOfColumnCode.add(itemForm.getDomainColumnCode());
				}

				/**
				 * validate for min max value
				 */
				String minVal = itemForm.getMinVal();
				String maxVal = itemForm.getMaxVal();
				Integer groupBasetype = itemForm.getGroupBasetypeId();
				String defaultVal = itemForm.getDefaultValue();

				if (DbDomainConst.PhysicalDataType.INTEGER.equals(groupBasetype) || DbDomainConst.PhysicalDataType.DECIMAL.equals(groupBasetype)
						|| DbDomainConst.PhysicalDataType.CURRENCY.equals(groupBasetype) || DbDomainConst.PhysicalDataType.DATE.equals(groupBasetype)
						|| DbDomainConst.PhysicalDataType.DATETIME.equals(groupBasetype) || DbDomainConst.PhysicalDataType.TIME.equals(groupBasetype)) {

					strMsgField = MessageUtils.getMessage(DomainDatatypeMessage.SC_DOMAINDATATYPE_0022);

					if (DomainDesignUtil.compareObject(maxVal, minVal, groupBasetype) < 0) {
						String[] param = { MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0029), MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0028),
								String.valueOf(line) };

						errors.reject(CommonMessageConst.ERR_SYS_0085, param, null);
					}

					strMsgField = MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0004);

					if (DomainDesignUtil.compareObject(maxVal, defaultVal, groupBasetype) < 0) {
						String[] param = { MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0004), MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0029),
								String.valueOf(line) };

						errors.reject(CommonMessageConst.ERR_SYS_0084, param, null);
					}

					if (DomainDesignUtil.compareObject(defaultVal, minVal, groupBasetype) < 0) {
						String[] param = { MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0004), MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0028),
								String.valueOf(line) };

						errors.reject(CommonMessageConst.ERR_SYS_0085, param, null);
					}

					//If  group base type is integer then check value has out of range
					if (DbDomainConst.PhysicalDataType.INTEGER.equals(groupBasetype)) {
						Integer dataType = itemForm.getPhysicalDataType();
						//validate value of minval
						if (!DomainDatatypeUtil.isEmpty(minVal)) {
							List<String> listCheck = TableDesignUtil.checkValidValueOfBaseType(dataType, minVal);
							if (!DomainDatatypeUtil.isEmpty(listCheck)) {
								String[] param = { String.valueOf(line), MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0028), listCheck.get(0), listCheck.get(1)};
								errors.reject(CommonMessageConst.ERR_SYS_0003, param, null);
							}
						}
						//validate value of maxval
						if (!DomainDatatypeUtil.isEmpty(maxVal)) {
							List<String> listCheck = TableDesignUtil.checkValidValueOfBaseType(dataType, maxVal);
							if (!DomainDatatypeUtil.isEmpty(listCheck)) {
								String[] param = { String.valueOf(line), MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0029), listCheck.get(0), listCheck.get(1)};
								errors.reject(CommonMessageConst.ERR_SYS_0003, param, null);
							}
						}
						//validate value of default value
						if (!DomainDatatypeUtil.isEmpty(defaultVal)) {
							List<String> listCheck = TableDesignUtil.checkValidValueOfBaseType(dataType, defaultVal);
							if (!DomainDatatypeUtil.isEmpty(listCheck)) {
								String[] param = { String.valueOf(line), MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0004), listCheck.get(0), listCheck.get(1)};
								errors.reject(CommonMessageConst.ERR_SYS_0003, param, null);
							}
						}
					}
				}

				/**
				 * validate for maxlength
				 */

				Integer maxlengthOfDomain = itemForm.getMaxlength();

				if (DomainDatatypeUtil.isEmpty(maxlengthOfDomain)) {
					if (!DomainDatatypeUtil.isEmpty(itemForm.getMaxlengthPhysical())) {
						String[] param = { MessageUtils.getMessage(DomainDatatypeMessage.SC_DOMAINDATATYPE_0014), String.valueOf(line) };
						errors.reject(CommonMessageConst.ERR_SYS_0077, param, null);
						/* continue; */
					}
				} else {
					if (maxlengthOfDomain.compareTo(itemForm.getMaxlengthPhysical()) > 0) {
						String[] param = { MessageUtils.getMessage(DomainDatatypeMessage.SC_DOMAINDATATYPE_0014),
								MessageUtils.getMessage(DomainDatatypeMessage.SC_DOMAINDATATYPE_0055), String.valueOf(line) };
						errors.reject(CommonMessageConst.ERR_SYS_0084, param, null);

					/* continue; */
					}
					//validate max length of min value
					if (!DomainDatatypeUtil.isEmpty(minVal) && minVal.length() > maxlengthOfDomain.intValue()) {
						String[] param = { MessageUtils.getMessage(DomainDatatypeMessage.SC_DOMAINDATATYPE_0015),
								String.valueOf(line), String.valueOf(maxlengthOfDomain.intValue())};
							errors.reject(CommonMessageConst.ERR_SYS_0021, param, null);
					}
					//validate max length of min value
					if (!DomainDatatypeUtil.isEmpty(maxVal) && maxVal.length() > maxlengthOfDomain.intValue()) {
						String[] param = { MessageUtils.getMessage(DomainDatatypeMessage.SC_DOMAINDATATYPE_0016),
								String.valueOf(line), String.valueOf(maxlengthOfDomain.intValue())};
							errors.reject(CommonMessageConst.ERR_SYS_0021, param, null);
					}
					//validate max length of min value
					if (!DomainDatatypeUtil.isEmpty(defaultVal) && defaultVal.length() > maxlengthOfDomain.intValue()) {
						String[] param = { MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0004),
								String.valueOf(line), String.valueOf(maxlengthOfDomain.intValue())};
							errors.reject(CommonMessageConst.ERR_SYS_0021, param, null);
					}
				}
				/**
				 * validate for require
				 */
				Long domainDatatype = itemForm.getDomainDataType();
				// if is autocomplete
				if (DomainDatatypeConst.LogicDataType.AUTOCOMPLETE.equals(domainDatatype)) {
					if (DomainDatatypeUtil.isEmpty(itemForm.getDataSource())) {
						String[] param = { MessageUtils.getMessage(DomainDatatypeMessage.SC_DOMAINDATATYPE_0038), String.valueOf(line) };
						errors.reject(CommonMessageConst.ERR_SYS_0077, param, null);
						/* continue; */
					}
					// if is radio or checkbox or select
				} else if (DomainDatatypeConst.LogicDataType.RADIO.equals(domainDatatype) || DomainDatatypeConst.LogicDataType.CHECKBOX.equals(domainDatatype)
						|| DomainDatatypeConst.LogicDataType.SELECT.equals(domainDatatype)) {
					String strDataSource = itemForm.getDataSource();
					String strMsgValue = itemForm.getMsgValue();
					String strMsgLabel = itemForm.getMsgLabel();
					//if using system codelisst
					if (DomainDatatypeConst.CodelistType.SYSTEM_CODE_LIST.equals(itemForm.getCodelistType())) {
						if (DomainDatatypeUtil.isEmpty(strDataSource)) {
							String[] param = { MessageUtils.getMessage(DomainDatatypeMessage.SC_DOMAINDATATYPE_0046), String.valueOf(line) };
							errors.reject(CommonMessageConst.ERR_SYS_0077, param, null);
						}
					//if using screen codelist
					} else if (DomainDatatypeConst.CodelistType.SCREEN_CODE_LIST.equals(itemForm.getCodelistType())){
						if (DomainDatatypeUtil.isEmpty(strMsgValue)) {
							String[] param = { MessageUtils.getMessage(DomainDatatypeMessage.SC_DOMAINDATATYPE_0051), String.valueOf(line) };
							errors.reject(CommonMessageConst.ERR_SYS_0077, param, null);
						} else if (DbDomainConst.PhysicalDataType.INTEGER.equals(groupBasetype)) {
							//validator value out of range
							Integer dataType = itemForm.getPhysicalDataType();
							String[] arrOptionValue = strMsgValue.split(DomainDatatypeConst.CHAR_SPLIT);
							for (int j = 0; j < arrOptionValue.length; j++) {
								String strOptionValue = arrOptionValue[j];
								if(DomainDatatypeUtil.isEmpty(strOptionValue)) {
									continue;
								}
								//if min value and max value is null, check out of range by data type
								if (DomainDatatypeUtil.isEmpty(minVal) && DomainDatatypeUtil.isEmpty(maxVal)) {
									List<String> listCheck = TableDesignUtil.checkValidValueOfBaseType(dataType, strOptionValue);
									if (!DomainDatatypeUtil.isEmpty(listCheck)) {
										String[] param = { String.valueOf(line), MessageUtils.getMessage(DomainDatatypeMessage.SC_DOMAINDATATYPE_0051), listCheck.get(0), listCheck.get(1)};
										errors.reject(CommonMessageConst.ERR_SYS_0003, param, null);
										break;
									}
								} else {
									//compare with max value
									if (!DomainDatatypeUtil.isEmpty(maxVal)) {//compare with max value
										if (DomainDesignUtil.compareObject(maxVal, strOptionValue, groupBasetype) < 0) {
											String[] param = { MessageUtils.getMessage(DomainDatatypeMessage.SC_DOMAINDATATYPE_0051), MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0029),
													String.valueOf(line) };
											errors.reject(CommonMessageConst.ERR_SYS_0084, param, null);
											break;
										}
									} 
									//compare with min value
									if (!DomainDatatypeUtil.isEmpty(minVal) && DomainDesignUtil.compareObject(strOptionValue, minVal, groupBasetype) < 0) {
										String[] param = { MessageUtils.getMessage(DomainDatatypeMessage.SC_DOMAINDATATYPE_0051), MessageUtils.getMessage(DomainDesignMessageConst.SC_DOMAINDESIGN_0028),
												String.valueOf(line) };
										errors.reject(CommonMessageConst.ERR_SYS_0085, param, null);
										break;
									}
								}
							}
						}
						// if is custom codelist then require codelist name or boolean
						if (itemForm.getSupportOptionFlg() == 0 && DomainDatatypeUtil.isEmpty(strMsgLabel)) {
							String[] param = { MessageUtils.getMessage(DomainDatatypeMessage.SC_DOMAINDATATYPE_0050), String.valueOf(line) };
							errors.reject(CommonMessageConst.ERR_SYS_0077, param, null);
						}
					}
					
					// if is custom codelist then require codelist name or boolean
					if (DbDomainConst.PhysicalDataType.BOOLEAN.equals(groupBasetype) && DomainDatatypeUtil.isEmpty(strMsgLabel)) {
						String[] param = { MessageUtils.getMessage(DomainDatatypeMessage.SC_DOMAINDATATYPE_0050), String.valueOf(line) };
						errors.reject(CommonMessageConst.ERR_SYS_0077, param, null);
					}

				}
				/**
				 * validate for on change/select method
				 */
				String changeMethod = itemForm.getOnChangeMethod();
				if (!DomainDatatypeUtil.isEmpty(changeMethod) && !changeMethod.matches(DomainDatatypeConst.REGULAR_EXP_CODE)) {
					String[] param = { MessageUtils.getMessage(DomainDatatypeMessage.SC_DOMAINDATATYPE_0043), String.valueOf(line),
							String.valueOf(DbDomainConst.MIN_VAL_INPUT), String.valueOf(DbDomainConst.MAX_LENGTH_OF_CODE)
					};
					errors.reject(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0001, param, null);
				}

				String selectMethod = itemForm.getOnSelectMethod();
				if (!DomainDatatypeUtil.isEmpty(selectMethod) && !selectMethod.matches(DomainDatatypeConst.REGULAR_EXP_CODE)) {
					String[] param = { MessageUtils.getMessage(DomainDatatypeMessage.SC_DOMAINDATATYPE_0044), String.valueOf(line), 
							String.valueOf(DbDomainConst.MIN_VAL_INPUT), String.valueOf(DbDomainConst.MAX_LENGTH_OF_CODE)
					};
					errors.reject(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0001, param, null);
				}
			}
		} catch (Exception e) {
			/* e.printStackTrace(); */
			if (e.getMessage().equalsIgnoreCase(CommonMessageConst.ERR_SYS_0100)) {
				strCode = CommonMessageConst.ERR_SYS_0010;
			} else if (e.getMessage().equalsIgnoreCase(CommonMessageConst.ERR_SYS_0005)) {
				strCode = CommonMessageConst.ERR_SYS_0006;
			}
			errors.reject(strCode, new String[] { String.valueOf(line), strMsgField }, null);
		}
	}
}
