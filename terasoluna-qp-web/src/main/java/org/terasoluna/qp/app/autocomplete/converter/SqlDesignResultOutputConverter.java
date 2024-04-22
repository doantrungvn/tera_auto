package org.terasoluna.qp.app.autocomplete.converter;


import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerConverter;
import org.terasoluna.gfw.common.codelist.CodeList;
import org.terasoluna.qp.app.autocomplete.OutputForm;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.domain.model.SqlDesignResult;

public class SqlDesignResultOutputConverter extends DozerConverter<OutputForm, SqlDesignResult[]>  {

	@Inject
    @Named("CL_SQL_AGGREGATE_FUNCTIONS")
	private CodeList CL_AggregateFunctions;
	
	public SqlDesignResultOutputConverter() {
		super(OutputForm.class, SqlDesignResult[].class);
	}

	@Override
	public SqlDesignResult[] convertTo(OutputForm paramA,
			SqlDesignResult[] paramB) {
		return paramB;
	}

	@Override
	public OutputForm convertFrom(SqlDesignResult[] sqlDesignResults, OutputForm outputForm) {
		if(ArrayUtils.isNotEmpty(sqlDesignResults) && outputForm!=null){
			Integer index = DataTypeUtils.convertTo(outputForm.getOutput1Column(), Integer.class)-1;
			if(index>-1 && index < sqlDesignResults.length){
				if(StringUtils.isNoneEmpty(sqlDesignResults[index].getFunctionCode())){
					outputForm.setOutput1ColumnAutocomplete(ObjectUtils.firstNonNull(ObjectUtils.firstNonNull(CL_AggregateFunctions.asMap().get(sqlDesignResults[index].getFunctionCode()),StringUtils.EMPTY),StringUtils.EMPTY)+"(["
							+sqlDesignResults[index].getTableName()+"].["
							+sqlDesignResults[index].getColumnName()+"])");
				} else {
					outputForm.setOutput1ColumnAutocomplete("["+sqlDesignResults[index].getTableName()+"].["+sqlDesignResults[index].getColumnName()+"]");
				}
			}
			
			index = DataTypeUtils.convertTo(outputForm.getOutput2Column(), Integer.class)-1;
			if(index>-1 && index < sqlDesignResults.length){
				if(StringUtils.isNoneEmpty(sqlDesignResults[index].getFunctionCode())){
					outputForm.setOutput2ColumnAutocomplete(ObjectUtils.firstNonNull(ObjectUtils.firstNonNull(CL_AggregateFunctions.asMap().get(sqlDesignResults[index].getFunctionCode()),StringUtils.EMPTY),StringUtils.EMPTY)+"(["
							+sqlDesignResults[index].getTableName()+"].["
							+sqlDesignResults[index].getColumnName()+"])");
				} else {
					outputForm.setOutput2ColumnAutocomplete("["+sqlDesignResults[index].getTableName()+"].["+sqlDesignResults[index].getColumnName()+"]");
				}
			}
			
			index = DataTypeUtils.convertTo(outputForm.getOutput3Column(), Integer.class)-1;
			if(index>-1 && index < sqlDesignResults.length){
				if(StringUtils.isNoneEmpty(sqlDesignResults[index].getFunctionCode())){
					outputForm.setOutput3ColumnAutocomplete(ObjectUtils.firstNonNull(ObjectUtils.firstNonNull(CL_AggregateFunctions.asMap().get(sqlDesignResults[index].getFunctionCode()),StringUtils.EMPTY),StringUtils.EMPTY)+"(["
							+sqlDesignResults[index].getTableName()+"].["
							+sqlDesignResults[index].getColumnName()+"])");
				} else {
					outputForm.setOutput3ColumnAutocomplete("["+sqlDesignResults[index].getTableName()+"].["+sqlDesignResults[index].getColumnName()+"]");
				}
			}
			
			index = DataTypeUtils.convertTo(outputForm.getOutput4Column(), Integer.class)-1;
			if(index>-1 && index < sqlDesignResults.length){
				if(StringUtils.isNoneEmpty(sqlDesignResults[index].getFunctionCode())){
					outputForm.setOutput4ColumnAutocomplete(ObjectUtils.firstNonNull(ObjectUtils.firstNonNull(CL_AggregateFunctions.asMap().get(sqlDesignResults[index].getFunctionCode()),StringUtils.EMPTY),StringUtils.EMPTY)+"(["
							+sqlDesignResults[index].getTableName()+"].["
							+sqlDesignResults[index].getColumnName()+"])");
				} else {
					outputForm.setOutput4ColumnAutocomplete("["+sqlDesignResults[index].getTableName()+"].["+sqlDesignResults[index].getColumnName()+"]");
				}
			}
			
			index = DataTypeUtils.convertTo(outputForm.getOutput5Column(), Integer.class)-1;
			if(index>-1 && index < sqlDesignResults.length){
				if(StringUtils.isNoneEmpty(sqlDesignResults[index].getFunctionCode())){
					outputForm.setOutput5ColumnAutocomplete(ObjectUtils.firstNonNull(ObjectUtils.firstNonNull(CL_AggregateFunctions.asMap().get(sqlDesignResults[index].getFunctionCode()),StringUtils.EMPTY),StringUtils.EMPTY)+"(["
							+sqlDesignResults[index].getTableName()+"].["
							+sqlDesignResults[index].getColumnName()+"])");
				} else {
					outputForm.setOutput5ColumnAutocomplete("["+sqlDesignResults[index].getTableName()+"].["+sqlDesignResults[index].getColumnName()+"]");
				}
			}
			index = DataTypeUtils.convertTo(outputForm.getOutput6Column(), Integer.class)-1;
			if(index>-1 && index < sqlDesignResults.length){
				if(StringUtils.isNoneEmpty(sqlDesignResults[index].getFunctionCode())){
					outputForm.setOutput6ColumnAutocomplete(ObjectUtils.firstNonNull(ObjectUtils.firstNonNull(CL_AggregateFunctions.asMap().get(sqlDesignResults[index].getFunctionCode()),StringUtils.EMPTY),StringUtils.EMPTY)+"(["
							+sqlDesignResults[index].getTableName()+"].["
							+sqlDesignResults[index].getColumnName()+"])");
				} else {
					outputForm.setOutput6ColumnAutocomplete("["+sqlDesignResults[index].getTableName()+"].["+sqlDesignResults[index].getColumnName()+"]");
				}
			}
			
			index = DataTypeUtils.convertTo(outputForm.getOutput7Column(), Integer.class)-1;
			if(index>-1 && index < sqlDesignResults.length){
				if(StringUtils.isNoneEmpty(sqlDesignResults[index].getFunctionCode())){
					outputForm.setOutput7ColumnAutocomplete(ObjectUtils.firstNonNull(ObjectUtils.firstNonNull(CL_AggregateFunctions.asMap().get(sqlDesignResults[index].getFunctionCode()),StringUtils.EMPTY),StringUtils.EMPTY)+"(["
							+sqlDesignResults[index].getTableName()+"].["
							+sqlDesignResults[index].getColumnName()+"])");
				} else {
					outputForm.setOutput7ColumnAutocomplete("["+sqlDesignResults[index].getTableName()+"].["+sqlDesignResults[index].getColumnName()+"]");
				}
			}
			
			index = DataTypeUtils.convertTo(outputForm.getOutput8Column(), Integer.class)-1;
			if(index>-1 && index < sqlDesignResults.length){
				if(StringUtils.isNoneEmpty(sqlDesignResults[index].getFunctionCode())){
					outputForm.setOutput8ColumnAutocomplete(ObjectUtils.firstNonNull(ObjectUtils.firstNonNull(CL_AggregateFunctions.asMap().get(sqlDesignResults[index].getFunctionCode()),StringUtils.EMPTY),StringUtils.EMPTY)+"(["
							+sqlDesignResults[index].getTableName()+"].["
							+sqlDesignResults[index].getColumnName()+"])");
				} else {
					outputForm.setOutput8ColumnAutocomplete("["+sqlDesignResults[index].getTableName()+"].["+sqlDesignResults[index].getColumnName()+"]");
				}
			}
			index = DataTypeUtils.convertTo(outputForm.getOutput9Column(), Integer.class)-1;
			if(index>-1 && index < sqlDesignResults.length){
				if(StringUtils.isNoneEmpty(sqlDesignResults[index].getFunctionCode())){
					outputForm.setOutput9ColumnAutocomplete(ObjectUtils.firstNonNull(ObjectUtils.firstNonNull(CL_AggregateFunctions.asMap().get(sqlDesignResults[index].getFunctionCode()),StringUtils.EMPTY),StringUtils.EMPTY)+"(["
							+sqlDesignResults[index].getTableName()+"].["
							+sqlDesignResults[index].getColumnName()+"])");
				} else {
					outputForm.setOutput9ColumnAutocomplete("["+sqlDesignResults[index].getTableName()+"].["+sqlDesignResults[index].getColumnName()+"]");
				}
			}
			
			index = DataTypeUtils.convertTo(outputForm.getOutput10Column(), Integer.class)-1;
			if(index>-1 && index < sqlDesignResults.length){
				if(StringUtils.isNoneEmpty(sqlDesignResults[index].getFunctionCode())){
					outputForm.setOutput10ColumnAutocomplete(ObjectUtils.firstNonNull(ObjectUtils.firstNonNull(CL_AggregateFunctions.asMap().get(sqlDesignResults[index].getFunctionCode()),StringUtils.EMPTY),StringUtils.EMPTY)+"(["
							+sqlDesignResults[index].getTableName()+"].["
							+sqlDesignResults[index].getColumnName()+"])");
				} else {
					outputForm.setOutput10ColumnAutocomplete("["+sqlDesignResults[index].getTableName()+"].["+sqlDesignResults[index].getColumnName()+"]");
				}
			}
			
			index = DataTypeUtils.convertTo(outputForm.getOutput11Column(), Integer.class)-1;
			if(index>-1 && index < sqlDesignResults.length){
				if(StringUtils.isNoneEmpty(sqlDesignResults[index].getFunctionCode())){
					outputForm.setOutput11ColumnAutocomplete(ObjectUtils.firstNonNull(ObjectUtils.firstNonNull(CL_AggregateFunctions.asMap().get(sqlDesignResults[index].getFunctionCode()),StringUtils.EMPTY),StringUtils.EMPTY)+"(["
							+sqlDesignResults[index].getTableName()+"].["
							+sqlDesignResults[index].getColumnName()+"])");
				} else {
					outputForm.setOutput11ColumnAutocomplete("["+sqlDesignResults[index].getTableName()+"].["+sqlDesignResults[index].getColumnName()+"]");
				}
			}
			
			index = DataTypeUtils.convertTo(outputForm.getOutput12Column(), Integer.class)-1;
			if(index>-1 && index < sqlDesignResults.length){
				if(StringUtils.isNoneEmpty(sqlDesignResults[index].getFunctionCode())){
					outputForm.setOutput12ColumnAutocomplete(ObjectUtils.firstNonNull(ObjectUtils.firstNonNull(CL_AggregateFunctions.asMap().get(sqlDesignResults[index].getFunctionCode()),StringUtils.EMPTY),StringUtils.EMPTY)+"(["
							+sqlDesignResults[index].getTableName()+"].["
							+sqlDesignResults[index].getColumnName()+"])");
				} else {
					outputForm.setOutput12ColumnAutocomplete("["+sqlDesignResults[index].getTableName()+"].["+sqlDesignResults[index].getColumnName()+"]");
				}
			}
			
			index = DataTypeUtils.convertTo(outputForm.getOutput13Column(), Integer.class)-1;
			if(index>-1 && index < sqlDesignResults.length){
				if(StringUtils.isNoneEmpty(sqlDesignResults[index].getFunctionCode())){
					outputForm.setOutput13ColumnAutocomplete(ObjectUtils.firstNonNull(ObjectUtils.firstNonNull(CL_AggregateFunctions.asMap().get(sqlDesignResults[index].getFunctionCode()),StringUtils.EMPTY),StringUtils.EMPTY)+"(["
							+sqlDesignResults[index].getTableName()+"].["
							+sqlDesignResults[index].getColumnName()+"])");
				} else {
					outputForm.setOutput13ColumnAutocomplete("["+sqlDesignResults[index].getTableName()+"].["+sqlDesignResults[index].getColumnName()+"]");
				}
			}
			
			index = DataTypeUtils.convertTo(outputForm.getOutput14Column(), Integer.class)-1;
			if(index>-1 && index < sqlDesignResults.length){
				if(StringUtils.isNoneEmpty(sqlDesignResults[index].getFunctionCode())){
					outputForm.setOutput14ColumnAutocomplete(ObjectUtils.firstNonNull(ObjectUtils.firstNonNull(CL_AggregateFunctions.asMap().get(sqlDesignResults[index].getFunctionCode()),StringUtils.EMPTY),StringUtils.EMPTY)+"(["
							+sqlDesignResults[index].getTableName()+"].["
							+sqlDesignResults[index].getColumnName()+"])");
				} else {
					outputForm.setOutput14ColumnAutocomplete("["+sqlDesignResults[index].getTableName()+"].["+sqlDesignResults[index].getColumnName()+"]");
				}
			}
			
			index = DataTypeUtils.convertTo(outputForm.getOutput15Column(), Integer.class)-1;
			if(index>-1 && index < sqlDesignResults.length){
				if(StringUtils.isNoneEmpty(sqlDesignResults[index].getFunctionCode())){
					outputForm.setOutput15ColumnAutocomplete(ObjectUtils.firstNonNull(ObjectUtils.firstNonNull(CL_AggregateFunctions.asMap().get(sqlDesignResults[index].getFunctionCode()),StringUtils.EMPTY),StringUtils.EMPTY)+"(["
							+sqlDesignResults[index].getTableName()+"].["
							+sqlDesignResults[index].getColumnName()+"])");
				} else {
					outputForm.setOutput15ColumnAutocomplete("["+sqlDesignResults[index].getTableName()+"].["+sqlDesignResults[index].getColumnName()+"]");
				}
			}
			
			index = DataTypeUtils.convertTo(outputForm.getOutput16Column(), Integer.class)-1;
			if(index>-1 && index < sqlDesignResults.length){
				if(StringUtils.isNoneEmpty(sqlDesignResults[index].getFunctionCode())){
					outputForm.setOutput16ColumnAutocomplete(ObjectUtils.firstNonNull(ObjectUtils.firstNonNull(CL_AggregateFunctions.asMap().get(sqlDesignResults[index].getFunctionCode()),StringUtils.EMPTY),StringUtils.EMPTY)+"(["
							+sqlDesignResults[index].getTableName()+"].["
							+sqlDesignResults[index].getColumnName()+"])");
				} else {
					outputForm.setOutput16ColumnAutocomplete("["+sqlDesignResults[index].getTableName()+"].["+sqlDesignResults[index].getColumnName()+"]");
				}
			}
			
			index = DataTypeUtils.convertTo(outputForm.getOutput17Column(), Integer.class)-1;
			if(index>-1 && index < sqlDesignResults.length){
				if(StringUtils.isNoneEmpty(sqlDesignResults[index].getFunctionCode())){
					outputForm.setOutput17ColumnAutocomplete(ObjectUtils.firstNonNull(ObjectUtils.firstNonNull(CL_AggregateFunctions.asMap().get(sqlDesignResults[index].getFunctionCode()),StringUtils.EMPTY),StringUtils.EMPTY)+"(["
							+sqlDesignResults[index].getTableName()+"].["
							+sqlDesignResults[index].getColumnName()+"])");
				} else {
					outputForm.setOutput17ColumnAutocomplete("["+sqlDesignResults[index].getTableName()+"].["+sqlDesignResults[index].getColumnName()+"]");
				}
			}
			
			index = DataTypeUtils.convertTo(outputForm.getOutput18Column(), Integer.class)-1;
			if(index>-1 && index < sqlDesignResults.length){
				if(StringUtils.isNoneEmpty(sqlDesignResults[index].getFunctionCode())){
					outputForm.setOutput18ColumnAutocomplete(ObjectUtils.firstNonNull(ObjectUtils.firstNonNull(CL_AggregateFunctions.asMap().get(sqlDesignResults[index].getFunctionCode()),StringUtils.EMPTY),StringUtils.EMPTY)+"(["
							+sqlDesignResults[index].getTableName()+"].["
							+sqlDesignResults[index].getColumnName()+"])");
				} else {
					outputForm.setOutput18ColumnAutocomplete("["+sqlDesignResults[index].getTableName()+"].["+sqlDesignResults[index].getColumnName()+"]");
				}
			}
			
			index = DataTypeUtils.convertTo(outputForm.getOutput19Column(), Integer.class)-1;
			if(index>-1 && index < sqlDesignResults.length){
				if(StringUtils.isNoneEmpty(sqlDesignResults[index].getFunctionCode())){
					outputForm.setOutput19ColumnAutocomplete(ObjectUtils.firstNonNull(ObjectUtils.firstNonNull(CL_AggregateFunctions.asMap().get(sqlDesignResults[index].getFunctionCode()),StringUtils.EMPTY),StringUtils.EMPTY)+"(["
							+sqlDesignResults[index].getTableName()+"].["
							+sqlDesignResults[index].getColumnName()+"])");
				} else {
					outputForm.setOutput19ColumnAutocomplete("["+sqlDesignResults[index].getTableName()+"].["+sqlDesignResults[index].getColumnName()+"]");
				}
			}
			
			index = DataTypeUtils.convertTo(outputForm.getOutput20Column(), Integer.class)-1;
			if(index>-1 && index < sqlDesignResults.length){
				if(StringUtils.isNoneEmpty(sqlDesignResults[index].getFunctionCode())){
					outputForm.setOutput20ColumnAutocomplete(ObjectUtils.firstNonNull(ObjectUtils.firstNonNull(CL_AggregateFunctions.asMap().get(sqlDesignResults[index].getFunctionCode()),StringUtils.EMPTY),StringUtils.EMPTY)+"(["
							+sqlDesignResults[index].getTableName()+"].["
							+sqlDesignResults[index].getColumnName()+"])");
				} else {
					outputForm.setOutput20ColumnAutocomplete("["+sqlDesignResults[index].getTableName()+"].["+sqlDesignResults[index].getColumnName()+"]");
				}
			}
		}
		return outputForm;
	}
}
