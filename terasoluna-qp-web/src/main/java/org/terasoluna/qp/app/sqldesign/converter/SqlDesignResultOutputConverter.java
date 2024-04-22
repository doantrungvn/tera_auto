package org.terasoluna.qp.app.sqldesign.converter;


import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerConverter;
import org.terasoluna.gfw.common.codelist.CodeList;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.sqldesign.OutputForm;
import org.terasoluna.qp.domain.model.SqlDesignResult;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeConst.SQLDataType;

public class SqlDesignResultOutputConverter extends DozerConverter<OutputForm[], SqlDesignResult[]>  {

	@Inject
    @Named("CL_SQL_AGGREGATE_FUNCTIONS")
	private CodeList CL_AggregateFunctions;
	
	public SqlDesignResultOutputConverter() {
		super(OutputForm[].class, SqlDesignResult[].class);
	}

	@Override
	public SqlDesignResult[] convertTo(OutputForm[] paramA,SqlDesignResult[] paramB) {
		return paramB;
	}

	@Override
	public OutputForm[] convertFrom(SqlDesignResult[] sqlDesignResults, OutputForm[] outputForms) {
		if(ArrayUtils.isNotEmpty(sqlDesignResults) && ArrayUtils.isNotEmpty(outputForms)){
			for(OutputForm outputForm : outputForms){
				if(DataTypeUtils.equals(outputForm.getDataType(),SQLDataType.Object)){
					continue;
				}
				SqlDesignResult result = null;
				for(SqlDesignResult sqlDesignResult:sqlDesignResults){
					if(DataTypeUtils.equals(outputForm.getMappingColumn(), sqlDesignResult.getItemSeqNo())){
						result = sqlDesignResult;
						break;
					}
				}
				if(result!=null){
					if(StringUtils.isNoneEmpty(result.getFunctionCode())){
						outputForm.setMappingColumnAutocomplete(ObjectUtils.firstNonNull(ObjectUtils.firstNonNull(CL_AggregateFunctions.asMap().get(result.getFunctionCode()),StringUtils.EMPTY),StringUtils.EMPTY)+"(["
								+result.getTableName()+"].["
								+result.getColumnName()+"])");
					} else {
						outputForm.setMappingColumnAutocomplete("["+result.getTableName()+"].["+result.getColumnName()+"]");
					}
				}
			}
			
		}
		return outputForms;
	}
}
