package org.terasoluna.qp.app.autocomplete.converter;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerConverter;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.sqldesign.HavingForm;
import org.terasoluna.qp.domain.model.SqlDesignHaving;

public class SqlDesignHavingConverter extends DozerConverter<HavingForm, SqlDesignHaving>{

	public SqlDesignHavingConverter() {
		super(HavingForm.class, SqlDesignHaving.class);
	}

	@Override
	public SqlDesignHaving convertTo(HavingForm havingForm, SqlDesignHaving sqlDesignHaving) {
		if(sqlDesignHaving==null) {
			sqlDesignHaving = new  SqlDesignHaving();
		}
		sqlDesignHaving.setLogicCode(havingForm.getLogicCode());
		sqlDesignHaving.setTableId(DataTypeUtils.convertTo(havingForm.getTableId(),Long.class));
		sqlDesignHaving.setTableName(havingForm.getTableIdAutocomplete());
		sqlDesignHaving.setColumnId(DataTypeUtils.convertTo(havingForm.getColumnId(),Long.class));
		sqlDesignHaving.setColumnName(havingForm.getColumnIdAutocomplete());
		sqlDesignHaving.setOperatorCode(havingForm.getOperatorCode());
		sqlDesignHaving.setItemSeqNo(DataTypeUtils.convertTo(havingForm.getItemSeqNo(),Integer.class));
		sqlDesignHaving.setHavingId(DataTypeUtils.convertTo(havingForm.getHavingId(),Long.class));
		sqlDesignHaving.setFunctionCode(havingForm.getFunctionCode());
		if(sqlDesignHaving.getOperatorCode().equals("7")){
			sqlDesignHaving.setValue(havingForm.getValue()+":"+havingForm.getValue2());
		} else {
			sqlDesignHaving.setValue(havingForm.getValue());
		}
		
		return sqlDesignHaving;
	}
	
	@Override
	public HavingForm convertFrom(SqlDesignHaving sqlDesignHaving, HavingForm havingForm) {
		if(havingForm==null) {
			havingForm = new HavingForm();
		}
		havingForm.setLogicCode(sqlDesignHaving.getLogicCode());
		havingForm.setTableId(sqlDesignHaving.getTableId().toString());
		havingForm.setTableIdAutocomplete(sqlDesignHaving.getTableName());
		havingForm.setColumnId(sqlDesignHaving.getColumnId().toString());
		havingForm.setColumnIdAutocomplete(sqlDesignHaving.getColumnName());
		havingForm.setOperatorCode(sqlDesignHaving.getOperatorCode());
		havingForm.setItemSeqNo(sqlDesignHaving.getItemSeqNo().toString());
		havingForm.setHavingId(sqlDesignHaving.getHavingId().toString());
		havingForm.setFunctionCode(sqlDesignHaving.getFunctionCode());
		if(havingForm.getOperatorCode().equals("7")){
			String[] values = StringUtils.split(sqlDesignHaving.getValue(),":");
			havingForm.setValue(values[0]);
			havingForm.setValue2(values[1]);
		} else {
			havingForm.setValue(sqlDesignHaving.getValue());
		}
		
		return havingForm;
	}
}
