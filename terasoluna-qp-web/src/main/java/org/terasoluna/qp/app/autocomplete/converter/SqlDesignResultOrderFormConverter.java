package org.terasoluna.qp.app.autocomplete.converter;


import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerConverter;
import org.terasoluna.gfw.common.codelist.CodeList;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.sqldesign.OrderByForm;
import org.terasoluna.qp.domain.model.SqlDesignResult;

public class SqlDesignResultOrderFormConverter extends DozerConverter<OrderByForm[], SqlDesignResult[]>  {

	@Inject
    @Named("CL_SQL_AGGREGATE_FUNCTIONS")
	private CodeList CL_AggregateFunctions;
	
	public SqlDesignResultOrderFormConverter() {
		super(OrderByForm[].class,SqlDesignResult[].class);
	}

	@Override
	public OrderByForm[] convertFrom(SqlDesignResult[] sqlDesignResults,OrderByForm[] orderByForms) {
		if(ArrayUtils.isNotEmpty(orderByForms) & ArrayUtils.isNotEmpty(sqlDesignResults)){
			for(OrderByForm orderByForm:orderByForms){
				SqlDesignResult result = null;
				for(SqlDesignResult sqlDesignResult : sqlDesignResults ){
					if(DataTypeUtils.equals(sqlDesignResult.getItemSeqNo().toString(),orderByForm.getTableColumn())){
						result = sqlDesignResult;
					}
				}
				if(result!=null){
					if(StringUtils.isNoneEmpty(result.getFunctionCode())){
						orderByForm.setTableColumnAutocomplete(ObjectUtils.firstNonNull(
								CL_AggregateFunctions.asMap().get(result.getFunctionCode()),
								StringUtils.EMPTY
								)
								+"(["+result.getTableName()+"].["
								+result.getColumnName()+"])");
					} else {
						orderByForm.setTableColumnAutocomplete("["+result.getTableName()+"].["+result.getColumnName()+"]");
					}
				}
			}
		}
		return orderByForms;
	}
	
	@Override
	public SqlDesignResult[] convertTo(OrderByForm[] orderByForm,SqlDesignResult[] sqlDesignResults) {
		// TODO Auto-generated method stub
		return sqlDesignResults;
	}

	
}
