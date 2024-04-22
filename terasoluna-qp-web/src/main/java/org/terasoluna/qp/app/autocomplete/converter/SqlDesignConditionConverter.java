package org.terasoluna.qp.app.autocomplete.converter;

import org.dozer.DozerConverter;
import org.terasoluna.qp.app.common.constants.DbDomainConst.SqlOperator;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.sqldesign.WhereForm;
import org.terasoluna.qp.domain.model.SqlDesignCondition;

public class SqlDesignConditionConverter extends DozerConverter<WhereForm, SqlDesignCondition>{

	public SqlDesignConditionConverter() {
		super(WhereForm.class, SqlDesignCondition.class);
	}

	@Override
	public SqlDesignCondition convertTo(WhereForm whereForm, SqlDesignCondition sqlDesignCondition) {
		if(sqlDesignCondition==null) {
			sqlDesignCondition = new  SqlDesignCondition();
		}
		sqlDesignCondition.setConditionType(DataTypeUtils.convertTo(whereForm.getConditionType(),Integer.class));
		sqlDesignCondition.setOperatorCode(whereForm.getOperatorCode());
		sqlDesignCondition.setGroupType(whereForm.getGroupType()==null || !whereForm.getGroupType()?0:1);
		sqlDesignCondition.setLeftTableId(DataTypeUtils.convertTo(whereForm.getLeftTableId(),Long.class));
		sqlDesignCondition.setLeftTableName(whereForm.getLeftTableIdAutocomplete());
		sqlDesignCondition.setLeftColumnId(DataTypeUtils.convertTo(whereForm.getLeftColumnId(),Long.class));
		sqlDesignCondition.setLeftColumnName(whereForm.getLeftColumnIdAutocomplete());
		sqlDesignCondition.setItemSeqNo(DataTypeUtils.convertTo(whereForm.getItemSeqNo(),Integer.class));
		sqlDesignCondition.setConditionsId(DataTypeUtils.convertTo(whereForm.getConditionsId(),Long.class));
		sqlDesignCondition.setFunctionCode(whereForm.getFunctionCode());
		sqlDesignCondition.setPatternFormat(whereForm.getPatternFormat());
		if(sqlDesignCondition.getItemSeqNo()>1){
			sqlDesignCondition.setLogicCode(whereForm.getLogicCode());
		} else {
			sqlDesignCondition.setLogicCode("");
		}
		if (sqlDesignCondition.getConditionType()==1) {
				sqlDesignCondition.setRightTableId(DataTypeUtils.convertTo(whereForm.getRightTableId(),Long.class));
				sqlDesignCondition.setRightColumnId(DataTypeUtils.convertTo(whereForm.getRightColumnId(),Long.class));

				sqlDesignCondition.setRightTableName(whereForm.getRightTableIdAutocomplete());
				sqlDesignCondition.setRightColumnName(whereForm.getRightColumnIdAutocomplete());
		} else if (sqlDesignCondition.getConditionType()==0) {
			if(DataTypeUtils.equals(sqlDesignCondition.getOperatorCode(),SqlOperator.BETWEEN)) {
				sqlDesignCondition.setValue(whereForm.getValue()+"::"+whereForm.getValue2());
			}
			else {
				sqlDesignCondition.setValue(whereForm.getValue());
			}
		} else if (sqlDesignCondition.getConditionType()==2) {
			if(DataTypeUtils.equals(sqlDesignCondition.getOperatorCode(),SqlOperator.BETWEEN)) {
				sqlDesignCondition.setValue(whereForm.getArg()+"::"+whereForm.getArg2());
			}
			else {
				sqlDesignCondition.setValue(whereForm.getArg());
			}
		}

		sqlDesignCondition.setDataType(DataTypeUtils.convertTo(whereForm.getDataType(),Integer.class));
		
		return sqlDesignCondition;
	}
	
	@Override
	public WhereForm convertFrom(SqlDesignCondition sqlDesignCondition, WhereForm whereForm) {
		if(whereForm==null) {
			whereForm = new WhereForm();
		}
		whereForm.setConditionType(sqlDesignCondition.getConditionType().toString());
		whereForm.setOperatorCode(sqlDesignCondition.getOperatorCode());
		whereForm.setGroupType(sqlDesignCondition.getGroupType()==1?true:false);
		whereForm.setLeftTableId(sqlDesignCondition.getLeftTableId().toString());
		whereForm.setLeftTableMissingFlag(sqlDesignCondition.getLeftTableMissingFlag());
		whereForm.setLeftColumnId(sqlDesignCondition.getLeftColumnId().toString());
		whereForm.setLeftColumnMissingFlag(sqlDesignCondition.getLeftColumnMissingFlag());
		whereForm.setLeftTableIdAutocomplete(sqlDesignCondition.getLeftTableName());
		whereForm.setLeftColumnIdAutocomplete(sqlDesignCondition.getLeftColumnName());
		whereForm.setItemSeqNo(sqlDesignCondition.getItemSeqNo().toString());
		whereForm.setConditionsId(sqlDesignCondition.getConditionsId().toString());
		whereForm.setFunctionCode(sqlDesignCondition.getFunctionCode());
		whereForm.setPatternFormat(sqlDesignCondition.getPatternFormat());
		if(sqlDesignCondition.getItemSeqNo()>0) {
			whereForm.setLogicCode(sqlDesignCondition.getLogicCode());
		} else {
			whereForm.setLogicCode("");
		}
		
		if (sqlDesignCondition.getConditionType()==1) {
			whereForm.setRightTableId(sqlDesignCondition.getRightTableId().toString());
			whereForm.setRightTableMissingFlag(sqlDesignCondition.getLeftTableMissingFlag());
			whereForm.setRightColumnId(sqlDesignCondition.getRightColumnId().toString());
			whereForm.setRightColumnMissingFlag(sqlDesignCondition.getRightColumnMissingFlag());
			whereForm.setRightTableIdAutocomplete(sqlDesignCondition.getRightTableName());
			whereForm.setRightColumnIdAutocomplete(sqlDesignCondition.getRightColumnName());
		} else if (sqlDesignCondition.getConditionType()==0){
			if(sqlDesignCondition.getOperatorCode().equals(SqlOperator.BETWEEN)) {
				String[] values = sqlDesignCondition.getValue().split("::");
				whereForm.setValue(values[0]);
				whereForm.setValue2(values[1]);
			} else whereForm.setValue(sqlDesignCondition.getValue());
		} else if (sqlDesignCondition.getConditionType()==2){
			if(sqlDesignCondition.getOperatorCode().equals(SqlOperator.BETWEEN)) {
				String[] values = sqlDesignCondition.getValue().split("::");
				whereForm.setArg(values[0]);
				whereForm.setArg2(values[1]);
			} else whereForm.setArg(sqlDesignCondition.getValue());
		}
		
		whereForm.setDataType((sqlDesignCondition.getDataType() == null)?"": sqlDesignCondition.getDataType().toString());
		
		return whereForm;
	}

}
