package org.terasoluna.qp.app.autocomplete;

import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.constants.DbDomainConst.SqlOperator;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.AutocompleteMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.sqldesign.FromForm;
import org.terasoluna.qp.app.sqldesign.GroupByForm;
import org.terasoluna.qp.app.sqldesign.HavingForm;
import org.terasoluna.qp.app.sqldesign.InputForm;
import org.terasoluna.qp.app.sqldesign.JoinColumnsForm;
import org.terasoluna.qp.app.sqldesign.OrderByForm;
import org.terasoluna.qp.app.sqldesign.SelectForm;
import org.terasoluna.qp.app.sqldesign.WhereForm;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst.JoinType;
import org.terasoluna.qp.domain.service.common.SystemService;

/**
 * @author anlt
 *
 */
@Component
public class AutocompleteDesignFormValidator implements Validator {

	@Inject
	SystemService systemService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return (AutocompleteDesignForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AutocompleteDesignForm autocompleteDesignForm = (AutocompleteDesignForm) target;
		//this.validateAutocomplete(autocompleteDesignForm.getAutocompleteForm(),errors);
		this.validateFromForm(autocompleteDesignForm.getFromForm(), errors);
		this.validateWhereForm(autocompleteDesignForm.getWhereForm(), errors);
		this.validateGroupByForm(autocompleteDesignForm.getGroupByForm(),errors);
		this.validateHavingForm(autocompleteDesignForm.getHavingForm(), errors);
		this.validateOrderByForm(autocompleteDesignForm.getOrderByForm(),errors);
		this.validateSelectForm(autocompleteDesignForm.getSelectForm(), errors);
		this.validateInputForm(autocompleteDesignForm.getInputForm(),errors);
		this.validateOutputForm(autocompleteDesignForm.getOutputForm(),errors);
	}

	private void validateAutocomplete(AutocompleteForm autocompleteForm,Errors errors) {
//		if(StringUtils.isEmpty(autocompleteForm.getDisplayTableId())) {
//			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0008,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0019)},"");
//		}
//		if(StringUtils.isEmpty(autocompleteForm.getDisplayColumnId())) {
//			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0009,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0019)},"");
//		}
//		if(StringUtils.isNotEmpty(autocompleteForm.getSubmitColumnId()) & StringUtils.isEmpty(autocompleteForm.getSubmitTableId())) {
//			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0008,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
//		}
//		if(StringUtils.isNotEmpty(autocompleteForm.getSubmitTableId()) & StringUtils.isEmpty(autocompleteForm.getSubmitColumnId())) {
//			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0009,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
//		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput01ColumnId()) & StringUtils.isEmpty(autocompleteForm.getOutput01TableId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0008,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput01TableId()) & StringUtils.isEmpty(autocompleteForm.getOutput01ColumnId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0009,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput02ColumnId()) & StringUtils.isEmpty(autocompleteForm.getOutput02TableId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0008,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput02TableId()) & StringUtils.isEmpty(autocompleteForm.getOutput02ColumnId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0009,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput03ColumnId()) & StringUtils.isEmpty(autocompleteForm.getOutput03TableId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0008,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput03TableId()) & StringUtils.isEmpty(autocompleteForm.getOutput03ColumnId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0009,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput04ColumnId()) & StringUtils.isEmpty(autocompleteForm.getOutput04TableId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0008,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput04TableId()) & StringUtils.isEmpty(autocompleteForm.getOutput04ColumnId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0009,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput05ColumnId()) & StringUtils.isEmpty(autocompleteForm.getOutput05TableId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0008,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput05TableId()) & StringUtils.isEmpty(autocompleteForm.getOutput05ColumnId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0009,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput06ColumnId()) & StringUtils.isEmpty(autocompleteForm.getOutput06TableId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0008,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput06TableId()) & StringUtils.isEmpty(autocompleteForm.getOutput06ColumnId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0009,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput07ColumnId()) & StringUtils.isEmpty(autocompleteForm.getOutput07TableId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0008,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput07TableId()) & StringUtils.isEmpty(autocompleteForm.getOutput07ColumnId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0009,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput08ColumnId()) & StringUtils.isEmpty(autocompleteForm.getOutput08TableId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0008,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput08TableId()) & StringUtils.isEmpty(autocompleteForm.getOutput08ColumnId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0009,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput09ColumnId()) & StringUtils.isEmpty(autocompleteForm.getOutput09TableId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0008,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput09TableId()) & StringUtils.isEmpty(autocompleteForm.getOutput09ColumnId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0009,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput10ColumnId()) & StringUtils.isEmpty(autocompleteForm.getOutput10TableId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0008,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput10TableId()) & StringUtils.isEmpty(autocompleteForm.getOutput10ColumnId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0009,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput11ColumnId()) & StringUtils.isEmpty(autocompleteForm.getOutput11TableId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0008,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput11TableId()) & StringUtils.isEmpty(autocompleteForm.getOutput11ColumnId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0009,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput12ColumnId()) & StringUtils.isEmpty(autocompleteForm.getOutput12TableId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0008,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput12TableId()) & StringUtils.isEmpty(autocompleteForm.getOutput12ColumnId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0009,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput13ColumnId()) & StringUtils.isEmpty(autocompleteForm.getOutput13TableId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0008,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput13TableId()) & StringUtils.isEmpty(autocompleteForm.getOutput13ColumnId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0009,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput14ColumnId()) & StringUtils.isEmpty(autocompleteForm.getOutput14TableId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0008,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput14TableId()) & StringUtils.isEmpty(autocompleteForm.getOutput14ColumnId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0009,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput15ColumnId()) & StringUtils.isEmpty(autocompleteForm.getOutput15TableId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0008,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput15TableId()) & StringUtils.isEmpty(autocompleteForm.getOutput15ColumnId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0009,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput16ColumnId()) & StringUtils.isEmpty(autocompleteForm.getOutput16TableId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0008,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput16TableId()) & StringUtils.isEmpty(autocompleteForm.getOutput16ColumnId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0009,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput17ColumnId()) & StringUtils.isEmpty(autocompleteForm.getOutput17TableId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0008,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput17TableId()) & StringUtils.isEmpty(autocompleteForm.getOutput17ColumnId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0009,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput18ColumnId()) & StringUtils.isEmpty(autocompleteForm.getOutput18TableId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0008,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput18TableId()) & StringUtils.isEmpty(autocompleteForm.getOutput18ColumnId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0009,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput19ColumnId()) & StringUtils.isEmpty(autocompleteForm.getOutput19TableId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0008,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput19TableId()) & StringUtils.isEmpty(autocompleteForm.getOutput19ColumnId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0009,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput20ColumnId()) & StringUtils.isEmpty(autocompleteForm.getOutput20TableId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0008,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
		}
		if(StringUtils.isNotEmpty(autocompleteForm.getOutput20TableId()) & StringUtils.isEmpty(autocompleteForm.getOutput20ColumnId())) {
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0009,new String[]{MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)},"");
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

	private void validateOutputForm(OutputForm outputForm, Errors errors) {
		if(StringUtils.isEmpty(outputForm.getSubmitColumn())){
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0016,
					new String[]{
								MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OUTPUT),
								MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)
								},
					"");
		}
		if(!outputForm.getOutput1Display() &&
				!outputForm.getOutput2Display() &&
				!outputForm.getOutput3Display() &&
				!outputForm.getOutput4Display() &&
				!outputForm.getOutput5Display() &&
				!outputForm.getOutput6Display() &&
				!outputForm.getOutput7Display() &&
				!outputForm.getOutput8Display() &&
				!outputForm.getOutput9Display() &&
				!outputForm.getOutput10Display() &&
				!outputForm.getOutput11Display() &&
				!outputForm.getOutput12Display() &&
				!outputForm.getOutput13Display() &&
				!outputForm.getOutput14Display() &&
				!outputForm.getOutput15Display() &&
				!outputForm.getOutput16Display() &&
				!outputForm.getOutput17Display() &&
				!outputForm.getOutput18Display() &&
				!outputForm.getOutput19Display() &&
				!outputForm.getOutput20Display()){
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0015,
					new String[]{
								MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OUTPUT),
								MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0019)
								},
					"");
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
				}
				
				for(int j=0;j<i;j++) {
					if(DataTypeUtils.equals(inputForm[i].getSqlDesignInputName(),inputForm[j].getSqlDesignInputName())) {
						errors.reject(CommonMessageConst.ERR_SYS_0106, new Object[] {
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_INPUT),
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_NAME),
									inputForm[i].getItemSeqNo()
								}, 
								null);
						break;
					}
					if(DataTypeUtils.equals(inputForm[i].getSqlDesignInputCode(),inputForm[j].getSqlDesignInputCode())) {
						errors.reject(CommonMessageConst.ERR_SYS_0106, new Object[] {
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_INPUT),
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE),
									inputForm[i].getItemSeqNo()
								}, 
								null);
						break;
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
