package org.terasoluna.qp.app.sqldesign.converter;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerConverter;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.sqldesign.ValueForm;
import org.terasoluna.qp.domain.model.SqlDesignInput;
import org.terasoluna.qp.domain.service.autocomplete.SqlBuilderServiceImpl.Const;

public class SqlDesignInputValuesConverter extends DozerConverter<ValueForm[], SqlDesignInput[]>  {

	public SqlDesignInputValuesConverter() {
		super(ValueForm[].class, SqlDesignInput[].class);
	}

	@Override
	public SqlDesignInput[] convertTo(ValueForm[] valueForms, SqlDesignInput[] sqlDesignInputs) {
		return sqlDesignInputs;
	}

	@Override
	public ValueForm[] convertFrom(SqlDesignInput[] sqlDesignInputs, ValueForm[] valueForms) {
		if(ArrayUtils.isNotEmpty(valueForms)){
			for(ValueForm valueForm:valueForms){
				valueForm.setDisplayParameter(getInputParam(sqlDesignInputs,valueForm.getParameter()));
			}
		}
		return valueForms;
	}
	private String getInputParam(SqlDesignInput[] sqlDesignInputs,String itemSeqNo){
		SqlDesignInput sqlDesignInput = null;
		if(ArrayUtils.isNotEmpty(sqlDesignInputs)){
			for(int i=0;i<sqlDesignInputs.length;i++){
				if(DataTypeUtils.equals(sqlDesignInputs[i].getGroupIndex(),itemSeqNo)){
					sqlDesignInput = sqlDesignInputs[i];
					break;
				}
			}
			if(sqlDesignInput != null){
				return getInputObjectPath(sqlDesignInputs,sqlDesignInput);
			}
		}	
		return StringUtils.EMPTY;
	}
	
	private String getInputObjectPath(SqlDesignInput[] sqlDesignInputs,SqlDesignInput sqlDesignInput){
		String path=sqlDesignInput.getSqlDesignInputCode();
		if(sqlDesignInput.getSqlDesignInputParentId()!=null){
			SqlDesignInput sqlDesignInputParent = null;
			for(int i=0;i<sqlDesignInputs.length;i++){
				if(DataTypeUtils.equals(sqlDesignInputs[i].getSqlDesignInputId(),sqlDesignInput.getSqlDesignInputParentId())){
					sqlDesignInputParent = sqlDesignInputs[i];
					break;
				}
			}
			path = getInputObjectPath(sqlDesignInputs,sqlDesignInputParent) +Const.DOT+ path;
		}
		return path;
	}
}
