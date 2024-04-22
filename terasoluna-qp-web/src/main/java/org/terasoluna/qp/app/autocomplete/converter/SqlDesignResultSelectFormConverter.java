package org.terasoluna.qp.app.autocomplete.converter;

import org.apache.commons.lang3.ObjectUtils;
import org.dozer.DozerConverter;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.sqldesign.SelectForm;
import org.terasoluna.qp.domain.model.SqlDesignResult;

public class SqlDesignResultSelectFormConverter extends DozerConverter<SelectForm, SqlDesignResult>{
	public SqlDesignResultSelectFormConverter() {
		super(SelectForm.class, SqlDesignResult.class);
	}

	@Override
	public SqlDesignResult convertTo(SelectForm selectForm, SqlDesignResult sqlDesignResult) {
		if(sqlDesignResult==null) {
			sqlDesignResult = new  SqlDesignResult();
		}
		sqlDesignResult.setColumnId(DataTypeUtils.convertTo(selectForm.getColumnId(),Long.class));
		sqlDesignResult.setColumnName(selectForm.getColumnIdAutocomplete());
		sqlDesignResult.setColumnCode(selectForm.getColumnCode());
		sqlDesignResult.setFunctionCode(selectForm.getFunctionCode());
		sqlDesignResult.setItemSeqNo(DataTypeUtils.convertTo(selectForm.getItemSeqNo(),Integer.class));
		sqlDesignResult.setResultId(DataTypeUtils.convertTo(selectForm.getResultId(),Long.class));
		sqlDesignResult.setSqlDesignId(DataTypeUtils.convertTo(selectForm.getSqlDesignId(),Long.class));
		sqlDesignResult.setTableId(DataTypeUtils.convertTo(selectForm.getTableId(), Long.class));
		sqlDesignResult.setTableName(selectForm.getTableIdAutocomplete());
		sqlDesignResult.setEnabledFlag(ObjectUtils.firstNonNull(selectForm.getIsSelected(),false)?1:0);
		sqlDesignResult.setDataType(DataTypeUtils.convertTo(selectForm.getDataType(),Integer.class));
		sqlDesignResult.setTableType(DataTypeUtils.convertTo(selectForm.getTableType(),Integer.class));
		return sqlDesignResult;
	}
	
	@Override
	public SelectForm convertFrom(SqlDesignResult sqlDesignResult, SelectForm selectForm) {
		if(selectForm==null) {
			selectForm = new SelectForm();
		}
		selectForm.setColumnId(sqlDesignResult.getColumnId().toString());
		selectForm.setColumnIdAutocomplete(sqlDesignResult.getColumnName());
		selectForm.setColumnCode(sqlDesignResult.getColumnCode());
		selectForm.setColumnMissingFlag(sqlDesignResult.getColumnMissingFlag());
		selectForm.setFunctionCode(sqlDesignResult.getFunctionCode());
		selectForm.setItemSeqNo(sqlDesignResult.getItemSeqNo().toString());
		selectForm.setResultId(sqlDesignResult.getResultId().toString());
		selectForm.setSqlDesignId(sqlDesignResult.getSqlDesignId().toString());
		selectForm.setTableId(sqlDesignResult.getTableId().toString());
		selectForm.setTableIdAutocomplete(sqlDesignResult.getTableName());
		selectForm.setTableMissingFlag(sqlDesignResult.getTableMissingFlag());
		selectForm.setIsSelected(sqlDesignResult.getEnabledFlag()==1?true:false);
		selectForm.setDataType((sqlDesignResult.getDataType() == null)?"": sqlDesignResult.getDataType().toString());
		selectForm.setTableType((sqlDesignResult.getTableType() == null)?"": sqlDesignResult.getTableType().toString());
		selectForm.setDataTypeBackup((sqlDesignResult.getDataTypeBackup() == null)?"": sqlDesignResult.getDataTypeBackup().toString());
		
		return selectForm;
	}

}
