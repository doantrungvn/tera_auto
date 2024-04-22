package org.terasoluna.qp.app.sqldesign;

import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.constants.DbDomainConst.SqlOperator;
import org.terasoluna.qp.app.common.constants.DbDomainConst.SqlPattern;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.AutocompleteMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.SqlDesignMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst.JoinType;
import org.terasoluna.qp.domain.service.common.SystemService;

/**
 * @author anlt
 *
 */
@Component
public class SqlDesignDesignFormValidator implements Validator {

	@Inject
	SystemService systemService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return (SqlDesignDesignForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SqlDesignDesignForm sqlDesignDesignForm = (SqlDesignDesignForm) target;
		String sqlPattern = sqlDesignDesignForm.getSqlDesignForm().getSqlPattern();
		if(DataTypeUtils.equals(SqlPattern.SELECT,sqlPattern)){
			this.validateFromForm(sqlDesignDesignForm.getFromForm(), errors);
			this.validateWhereForm(sqlDesignDesignForm.getWhereForm(), errors);
			this.validateGroupByForm(sqlDesignDesignForm.getGroupByForm(),errors);
			this.validateHavingForm(sqlDesignDesignForm.getHavingForm(), errors);
			this.validateOrderByForm(sqlDesignDesignForm.getOrderByForm(),errors);
			this.validateSelectForm(sqlDesignDesignForm.getSelectForm(), errors);
		}
		if(DataTypeUtils.equals(SqlPattern.INSERT,sqlPattern)){
			this.validateIntoForm(sqlDesignDesignForm.getIntoForm(),errors);
			this.validateValueForm(sqlDesignDesignForm.getValueForm(),errors);
		}
		if(DataTypeUtils.equals(SqlPattern.UPDATE,sqlPattern)){
			this.validateIntoForm(sqlDesignDesignForm.getIntoForm(),errors);
			this.validateValueForm(sqlDesignDesignForm.getValueForm(),errors);
			this.validateWhereForm(sqlDesignDesignForm.getWhereForm(), errors);
		}
		if(DataTypeUtils.equals(SqlPattern.DELETE,sqlPattern)){
			this.validateIntoForm(sqlDesignDesignForm.getIntoForm(),errors);
			this.validateWhereForm(sqlDesignDesignForm.getWhereForm(), errors);
		}
		this.validateInputForm(sqlDesignDesignForm.getInputForm(),errors);
		this.validateOutputForm(sqlDesignDesignForm.getOutputForm(),errors);
	}

	private void validateValueForm(ValueForm[] valueForm, Errors errors) {
		if(ArrayUtils.isNotEmpty(valueForm)){
			for(int i=0;i<valueForm.length;i++){
				if (StringUtils.isEmpty(valueForm[i].getColumnId())) {
					errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005, new Object[] {
						MessageUtils.getMessage(SqlDesignMessageConst.SC_SQLDESIGN_VALUE_FORM),
						String.valueOf(i + 1),
						MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0017),
					}, null);
				}
				if (StringUtils.isEmpty(valueForm[i].getParameter())) {
					errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005, new Object[] {
						MessageUtils.getMessage(SqlDesignMessageConst.SC_SQLDESIGN_VALUE_FORM),
						String.valueOf(i + 1),
						MessageUtils.getMessage(SqlDesignMessageConst.SC_SQLDESIGN_PARAMETER),
					}, null);
				}
			}
		}
	}

	private void validateIntoForm(IntoForm intoForm, Errors errors) {
		if (StringUtils.isEmpty(intoForm.getTableId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0006);
		} 
	}

	private void validateFromForm(FromForm[] fromForm, Errors errors) {
		if (ArrayUtils.isNotEmpty(fromForm)) {
			if (fromForm.length > 1) {
				for (int i = 0; i < fromForm.length; i++) {
					for(int j=0;j<i;j++) {
						if(DataTypeUtils.equals(fromForm[i].getJoinTableId(),fromForm[j].getJoinTableId())) {
							errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0011,
										new Object[] { 
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0041),
											i+1,
											j+1 
										},
										"");
						}
					} 
					validateFromFormItem(fromForm[i], i, errors);
				}
			} else if (fromForm.length == 1){
				if(StringUtils.isEmpty(fromForm[0].getJoinTableId())){
					if (StringUtils.isEmpty(fromForm[0].getTableId())) {
						errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0006);
					} 
				} else {
					validateFromFormItem(fromForm[0], 0, errors);
					if(DataTypeUtils.equals(fromForm[0].getTableIdAutocomplete(),fromForm[0].getJoinTableIdAutocomplete())){
						errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0003, new Object[] { MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0041),1 },"");
					}
				}
			}
		} else {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0002,
						new Object[] { 
							MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0041) 
						},
						"");
		}
	}

	private void validateWhereForm(WhereForm[] whereForm, Errors errors) {
		if(ArrayUtils.isNotEmpty(whereForm)) {
			for(int i=0;i<whereForm.length;i++) {
				if((StringUtils.isEmpty(whereForm[i].getLogicCode()) & i>0)){
					errors.reject(
							AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005,
							new Object[] {
								MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0046),
								String.valueOf(i + 1),
								MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0053) 
							},
							null);
				}
				if(StringUtils.isEmpty(whereForm[i].getLeftTableId())){
					errors.reject(
							AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005,
							new Object[] {
								MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0046),
								String.valueOf(i + 1),
								MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0054) 
							},
							null);
				}
				if(StringUtils.isEmpty(whereForm[i].getLeftColumnId())){
					errors.reject(
							AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005,
							new Object[] {
								MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0046),
								String.valueOf(i + 1),
								MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0055)
							},
							null);
				}
				if(StringUtils.isEmpty(whereForm[i].getConditionType())){
					errors.reject(
							AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005,
							new Object[] {
								MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0046),
								String.valueOf(i + 1),
								MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0056)
							},
							null);
				}
				if(StringUtils.isEmpty(whereForm[i].getOperatorCode())){
					errors.reject(
							AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005,
							new Object[] {
								MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0046),
								String.valueOf(i + 1),
								MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0057)
							},
							null);
				}
				if(StringUtils.isNotEmpty(whereForm[i].getConditionType())){
					if(whereForm[i].getConditionType().equals("1")){
						if(StringUtils.isEmpty(whereForm[i].getRightTableId())){
							errors.reject(
									AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005,
									new Object[] {
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0046),
										String.valueOf(i + 1),
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0058)
									},
									null);
						}
						if(StringUtils.isEmpty(whereForm[i].getRightColumnId())){
							errors.reject(
									AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005,
									new Object[] {
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0046),
										String.valueOf(i + 1),
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0059)
									},
									null);
						}
					} else if(whereForm[i].getConditionType().equals("0")) {
						if(StringUtils.isNotEmpty(whereForm[i].getOperatorCode())){
							switch(whereForm[i].getOperatorCode()){
							case SqlOperator.EQUAL:
							case SqlOperator.LESS:
							case SqlOperator.LESS_EQUAL:
							case SqlOperator.GREATER:
							case SqlOperator.GREATER_EQUAL:
							case SqlOperator.NOT_EQUAL:
							case SqlOperator.LIKE:
								if(StringUtils.isEmpty(whereForm[i].getValue())){
									errors.reject(
											AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005,
											new Object[] {
												MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0046),
												String.valueOf(i + 1),
												MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0060)
											},
											null);
								}
								break;
							case SqlOperator.BETWEEN:
								if(StringUtils.isEmpty(whereForm[i].getValue())||StringUtils.isEmpty(whereForm[i].getValue2())){
									errors.reject(
											AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005,
											new Object[] {
												MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0046),
												String.valueOf(i + 1),
												MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0060)
											},
											null);
								}
								break;
							default:
								break;
							}
						}
					} else if(whereForm[i].getConditionType().equals("2")) {
						if(StringUtils.isNotEmpty(whereForm[i].getOperatorCode())){
							switch(whereForm[i].getOperatorCode()){
							case SqlOperator.EQUAL:
							case SqlOperator.LESS:
							case SqlOperator.LESS_EQUAL:
							case SqlOperator.GREATER:
							case SqlOperator.GREATER_EQUAL:
							case SqlOperator.NOT_EQUAL:
							case SqlOperator.LIKE:
								if(StringUtils.isEmpty(whereForm[i].getArg())){
									errors.reject(
											AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005,
											new Object[] {
												MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0046),
												String.valueOf(i + 1),
												MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0060)
											},
											null);
								}
								break;
							case SqlOperator.BETWEEN:
								if(StringUtils.isEmpty(whereForm[i].getArg())||StringUtils.isEmpty(whereForm[i].getArg2())){
									errors.reject(
											AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005,
											new Object[] {
												MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0046),
												String.valueOf(i + 1),
												MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0060)
											},
											null);
								}
								break;
							default:
								break;
							}
						}
					}
				}
			}
		}
	}

	private void validateGroupByForm(GroupByForm[] groupByForm, Errors errors) {
		if (ArrayUtils.isNotEmpty(groupByForm)) {
			for (int i = 0; i < groupByForm.length; i++) {
				if (StringUtils.isEmpty(groupByForm[i].getTableId())) {
					errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005, new Object[] {
							MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0047),
							String.valueOf(i + 1),
							MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0016)},null);
				}
				if (StringUtils.isEmpty(groupByForm[i].getColumnId())) {
					errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005, new Object[] {
							MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0047),
							String.valueOf(i + 1),
							MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0017),}, null);
				}
				for(int j=0;j<i;j++) {
					if(DataTypeUtils.equals(groupByForm[i].getTableId(),groupByForm[j].getTableId()) & 
							DataTypeUtils.equals(groupByForm[i].getColumnId(),groupByForm[j].getColumnId())) {
						errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0012, new Object[] {
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0047),
									String.valueOf(i + 1),
									String.valueOf(j + 1)
								}, 
								null);
						break;
					}
				}
			}
		}
	}

	private void validateHavingForm(HavingForm[] havingForm, Errors errors) {
		if(ArrayUtils.isNotEmpty(havingForm)) {
			for(int i=0;i<havingForm.length;i++) {
				if((StringUtils.isEmpty(havingForm[i].getLogicCode()) & i>0)){
					errors.reject(
							AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005,
							new Object[] {
								MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0048),
								String.valueOf(i + 1),
								MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0053)
							},
							null);
				}
				if(StringUtils.isEmpty(havingForm[i].getTableId())){
					errors.reject(
							AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005,
							new Object[] {
								MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0048),
								String.valueOf(i + 1),
								MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0054) 
							},
							null);
				}
				if(StringUtils.isEmpty(havingForm[i].getColumnId())){
					errors.reject(
							AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005,
							new Object[] {
								MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0048),
								String.valueOf(i + 1),
								MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0055) 
							},
							null);
				}
				
				if(StringUtils.isEmpty(havingForm[i].getOperatorCode())){
					errors.reject(
							AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005,
							new Object[] {
								MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0048),
								String.valueOf(i + 1),
								MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0057)
							},
							null);
				} else {
//					if(StringUtils.isEmpty(havingForm[i].getFunctionCode()) & 
//							!SqlOperator.IS_NULL.equals(havingForm[i].getOperatorCode()) & 
//							!SqlOperator.IS_NOT_NULL.equals(havingForm[i].getOperatorCode())){
//						errors.reject(
//								AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005,
//								new Object[] {
//									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0048),
//									String.valueOf(i + 1),
//									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0061) 
//								},
//								null);
//					}
					switch(havingForm[i].getOperatorCode()){
					case SqlOperator.EQUAL:
					case SqlOperator.LESS:
					case SqlOperator.LESS_EQUAL:
					case SqlOperator.GREATER:
					case SqlOperator.GREATER_EQUAL:
					case SqlOperator.NOT_EQUAL:
					case SqlOperator.LIKE:
						if(StringUtils.isEmpty(havingForm[i].getValue())){
							errors.reject(
									AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005,
									new Object[] {
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0048),
										String.valueOf(i + 1),
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0060)
									},
									null);
						}
						break;
					case SqlOperator.BETWEEN:
						if(StringUtils.isEmpty(havingForm[i].getValue())||
								StringUtils.isEmpty(havingForm[i].getValue2())){
							errors.reject(
									AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005,
									new Object[] {
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0048),
										String.valueOf(i + 1),
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0060)
									},
									null);
						}
						break;
					default:
						break;
					}
				}
				
			}
		}
	}

	private void validateOrderByForm(OrderByForm[] orderByForm, Errors errors) {
		if (ArrayUtils.isNotEmpty(orderByForm)) {
			for (int i = 0; i < orderByForm.length; i++) {
				if (StringUtils.isEmpty(orderByForm[i].getTableColumn())) {
					errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005, 
							new Object[] {
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0044),
										String.valueOf(i + 1),
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0017)
							},
							null);
				}
				if (StringUtils.isEmpty(orderByForm[i].getOrderType())) {
					errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005, 
							new Object[] {
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0044),
										String.valueOf(i + 1),
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0052)
							},
							null);
				}
				for(int j=0;j<i;j++) {
					if(DataTypeUtils.equals(orderByForm[i].getTableColumn(),orderByForm[j].getTableColumn()) & 
							DataTypeUtils.equals(orderByForm[i].getOrderType(),orderByForm[j].getOrderType())
							) {
						errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0012, new Object[] {
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0044),
									String.valueOf(i + 1),
									String.valueOf(j + 1)
								}, 
								null);
						break;
					}
				}
			}
		}
	}
	
	private void validateSelectForm(SelectForm[] selectForm, Errors errors) {
		if (ArrayUtils.isNotEmpty(selectForm)) {
			for (int i = 0; i < selectForm.length; i++) {
				if (StringUtils.isEmpty(selectForm[i].getTableId())) {
					errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005, 
							new Object[] {
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0045),
										String.valueOf(i + 1),
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0016)
							},
							null);
				}
				if (StringUtils.isEmpty(selectForm[i].getColumnId())) {
					errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005, 
							new Object[] {
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0045),
										String.valueOf(i + 1),
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0017)
							},
							null);
				}
				for(int j=0;j<i;j++) {
					if(DataTypeUtils.equals(selectForm[i].getTableId(),selectForm[j].getTableId()) & 
							DataTypeUtils.equals(selectForm[i].getColumnId(),selectForm[j].getColumnId()) & 
							selectForm[i].getFunctionCode().equals(selectForm[j].getFunctionCode())) {
						errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0012, new Object[] {
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0045),
									String.valueOf(i + 1),
									String.valueOf(j + 1)
								}, 
								null);
						break;
					}
				}
			}
		}
	}

	private void validateOutputForm(OutputForm[] outputForm, Errors errors) {
		AccountProfile accountProfile = systemService.getDefaultProfile();
		if (ArrayUtils.isNotEmpty(outputForm) && accountProfile != null) {
			Pattern codePattern = Pattern.compile(accountProfile.getCodePattern());
			String codeMask = accountProfile.getCodeMask();
			Pattern namePattern = Pattern.compile(accountProfile.getNamePattern());
			String nameMask = accountProfile.getNameMask();
			int nameMinSize = accountProfile.getNameMinLength();
			int nameMaxSize = accountProfile.getNameMaxLength();
			int codeMinSize = accountProfile.getCodeMinLength();
			int codeMaxSize = accountProfile.getCodeMaxLength();
			
			for (int i = 0; i < outputForm.length; i++) {
				if (StringUtils.isEmpty(outputForm[i].getSqlDesignOutputName())) {
					errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005, 
							new Object[] {
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OUTPUT),
										String.valueOf(i + 1),
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_NAME)
							},
							null);
				} else {
					if(outputForm[i].getSqlDesignOutputName().length()<nameMinSize || outputForm[i].getSqlDesignOutputName().length()>nameMaxSize){
						errors.reject(CommonMessageConst.ERR_SYS_0108, 
								new Object[] {
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OUTPUT),
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_NAME),
											outputForm[i].getItemSeqNo(),
											nameMinSize,nameMaxSize
								},
								null);
					}
					if(!namePattern.matcher(outputForm[i].getSqlDesignOutputName()).matches()){
						errors.reject(CommonMessageConst.ERR_SYS_0107, 
								new Object[] {
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OUTPUT),
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_NAME),
											outputForm[i].getItemSeqNo(),
											nameMask
								},
								null);
					}
				}
				
				if (StringUtils.isEmpty(outputForm[i].getSqlDesignOutputCode())) {
					errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005, 
							new Object[] {
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OUTPUT),
										String.valueOf(i + 1),
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE)
							},
							null);
				} else {
					if(outputForm[i].getSqlDesignOutputCode().length()<codeMinSize || outputForm[i].getSqlDesignOutputCode().length()>codeMaxSize){
						errors.reject(CommonMessageConst.ERR_SYS_0108, 
								new Object[] {
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OUTPUT),
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE),
											outputForm[i].getItemSeqNo(),
											codeMinSize,codeMaxSize
								},
								null);
					}
					if(!codePattern.matcher(outputForm[i].getSqlDesignOutputCode()).matches()){
						errors.reject(CommonMessageConst.ERR_SYS_0107, 
								new Object[] {
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OUTPUT),
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE),
											outputForm[i].getItemSeqNo(),
											codeMask
								},
								null);
					}

					ValidationUtils.validateReservedJava(outputForm[i].getSqlDesignOutputCode(), errors, CommonMessageConst.ERR_SYS_0096, new Object[] {MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE), outputForm[i].getItemSeqNo()});
				}
				
				//Bangnl allow two output have same name
				/*for(int j=0;j<i;j++) {
					if(!DataTypeUtils.equal(outputForm[i].getDataType(),14) && 
							DataTypeUtils.equal(outputForm[i].getGroupId(), outputForm[j].getGroupId()) && 
							DataTypeUtils.equal(outputForm[i].getSqlDesignOutputName(),outputForm[j].getSqlDesignOutputName())) {
						errors.reject(CommonMessageConst.ERR_SYS_0106, new Object[] {
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OUTPUT),
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_NAME),
									outputForm[i].getGroupIndex()
								}, 
								null);
						break;
					}
				}*/
				for(int j=0;j<i;j++) {
					if(DataTypeUtils.equals(outputForm[i].getGroupId(), outputForm[j].getGroupId()) && DataTypeUtils.equals(outputForm[i].getSqlDesignOutputCode(),outputForm[j].getSqlDesignOutputCode())) {
						errors.reject(CommonMessageConst.ERR_SYS_0106, new Object[] {
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OUTPUT),
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE),
									outputForm[i].getGroupIndex()
								}, 
								null);
						break;
					}
				}
			}
		}
	}

	private void validateInputForm(InputForm[] inputForm, Errors errors) {
		AccountProfile accountProfile = systemService.getDefaultProfile();
		if (ArrayUtils.isNotEmpty(inputForm) && accountProfile != null) {
			Pattern codePattern = Pattern.compile(accountProfile.getCodePattern());
			String codeMask = accountProfile.getCodeMask();
			Pattern namePattern = Pattern.compile(accountProfile.getNamePattern());
			String nameMask = accountProfile.getNameMask();
			int nameMinSize = accountProfile.getNameMinLength();
			int nameMaxSize = accountProfile.getNameMaxLength();
			int codeMinSize = accountProfile.getCodeMinLength();
			int codeMaxSize = accountProfile.getCodeMaxLength();
			
			for (int i = 0; i < inputForm.length; i++) {
				if (StringUtils.isEmpty(inputForm[i].getSqlDesignInputName())) {
					errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005, 
							new Object[] {
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_INPUT),
										String.valueOf(i + 1),
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_NAME)
							},
							null);
				} else {
					if(inputForm[i].getSqlDesignInputName().length()<nameMinSize || inputForm[i].getSqlDesignInputName().length()>nameMaxSize){
						errors.reject(CommonMessageConst.ERR_SYS_0108, 
								new Object[] {
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_INPUT),
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_NAME),
											inputForm[i].getItemSeqNo(),
											nameMinSize,nameMaxSize
								},
								null);
					}
					if(!namePattern.matcher(inputForm[i].getSqlDesignInputName()).matches()){
						errors.reject(CommonMessageConst.ERR_SYS_0107, 
								new Object[] {
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_INPUT),
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_NAME),
											inputForm[i].getItemSeqNo(),
											nameMask
								},
								null);
					}
				}
				
				if (StringUtils.isEmpty(inputForm[i].getSqlDesignInputCode())) {
					errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005, 
							new Object[] {
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_INPUT),
										String.valueOf(i + 1),
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE)
							},
							null);
				} else {
					if(inputForm[i].getSqlDesignInputCode().length()<codeMinSize || inputForm[i].getSqlDesignInputCode().length()>codeMaxSize){
						errors.reject(CommonMessageConst.ERR_SYS_0108, 
								new Object[] {
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_INPUT),
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE),
											inputForm[i].getItemSeqNo(),
											codeMinSize,codeMaxSize
								},
								null);
					}
					if(!codePattern.matcher(inputForm[i].getSqlDesignInputCode()).matches()){
						errors.reject(CommonMessageConst.ERR_SYS_0107, 
								new Object[] {
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_INPUT),
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE),
											inputForm[i].getItemSeqNo(),
											codeMask
								},
								null);
					}

					ValidationUtils.validateReservedJava(inputForm[i].getSqlDesignInputCode(), errors, CommonMessageConst.ERR_SYS_0096, new Object[] {MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE), inputForm[i].getItemSeqNo()});
				}
				//Bangnl allow two input have same name
				/*if(StringUtils.isNotEmpty(inputForm[i].getSqlDesignInputName())){
					for(int j=0;j<i;j++) {
						if(DataTypeUtils.equal(inputForm[i].getGroupId(), inputForm[j].getGroupId()) && DataTypeUtils.equal(inputForm[i].getSqlDesignInputName(),inputForm[j].getSqlDesignInputName())) {
							errors.reject(CommonMessageConst.ERR_SYS_0106, new Object[] {
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_INPUT),
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_NAME),
										inputForm[i].getGroupIndex()
									}, 
									null);
							break;
						}
					}
				}*/
				if(StringUtils.isNotEmpty(inputForm[i].getSqlDesignInputCode())){
					for(int j=0;j<i;j++) {
						if(DataTypeUtils.equals(inputForm[i].getGroupId(), inputForm[j].getGroupId()) && DataTypeUtils.equals(inputForm[i].getSqlDesignInputCode(),inputForm[j].getSqlDesignInputCode())) {
							errors.reject(CommonMessageConst.ERR_SYS_0106, new Object[] {
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_INPUT),
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE),
										inputForm[i].getGroupIndex()
									}, 
									null);
							break;
						}
					}
				}
			}
		}
	}
	
	private void validateFromFormItem(FromForm fromFormItem, int index,Errors errors) {
		Boolean isJoinColumnsInvalid = false;
		
		if (ArrayUtils.isNotEmpty(fromFormItem.getJoinColumnsForm())) {
			for (JoinColumnsForm joinColumnsForm : fromFormItem.getJoinColumnsForm()) {
				if (StringUtils.isEmpty(joinColumnsForm.getTableId()) ||
						StringUtils.isEmpty(joinColumnsForm.getColumnId()) || 
						StringUtils.isEmpty(joinColumnsForm.getJoinColumnId()) || 
						StringUtils.isEmpty(joinColumnsForm.getOperatorCode())) {
					isJoinColumnsInvalid = true;
					break;
				}
			}
		}
		
		if (StringUtils.isEmpty(fromFormItem.getJoinType()) || 
							//StringUtils.isEmpty(fromFormItem.getTableId()) || 
							StringUtils.isEmpty(fromFormItem.getJoinTableId()) || 
							ArrayUtils.isEmpty(fromFormItem.getJoinColumnsForm()) || 
							isJoinColumnsInvalid) {
			if(StringUtils.isNotEmpty(fromFormItem.getJoinType()) && StringUtils.equals(fromFormItem.getJoinType(),JoinType.CROSS_JOIN.getCode())){
				return;
			}
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0007,
						new Object[] {
							MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0041),
							String.valueOf(index + 1) 
						}, 
						null);
		}
		
	}
}
