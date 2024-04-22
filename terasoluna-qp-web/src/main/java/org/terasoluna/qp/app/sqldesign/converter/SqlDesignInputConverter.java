package org.terasoluna.qp.app.sqldesign.converter;


import org.dozer.DozerConverter;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.sqldesign.InputForm;
import org.terasoluna.qp.domain.model.SqlDesignInput;

public class SqlDesignInputConverter extends DozerConverter<InputForm, SqlDesignInput>  {

	public SqlDesignInputConverter() {
		super(InputForm.class, SqlDesignInput.class);
	}

	@Override
	public SqlDesignInput convertTo(InputForm inputForm,SqlDesignInput sqlDesignInput) {
		if(inputForm!=null){
			if(sqlDesignInput==null){
				sqlDesignInput = new SqlDesignInput();
			}
			sqlDesignInput.setDataType(DataTypeUtils.convertTo(inputForm.getDataType(),Integer.class));
			sqlDesignInput.setArrayFlag(!(inputForm.getIsArray()==null || !inputForm.getIsArray())?1:0);
			sqlDesignInput.setItemSeqNo(DataTypeUtils.convertTo(inputForm.getItemSeqNo(),Integer.class));
			sqlDesignInput.setGroupId(inputForm.getGroupId());
			sqlDesignInput.setGroupIndex(inputForm.getGroupIndex());
			sqlDesignInput.setSqlDesignId(inputForm.getSqlDesignId());
			sqlDesignInput.setSqlDesignInputCode(inputForm.getSqlDesignInputCode());
			sqlDesignInput.setSqlDesignInputId(inputForm.getSqlDesignInputId());
			sqlDesignInput.setSqlDesignInputParentId(inputForm.getSqlDesignInputParentId());
			sqlDesignInput.setSqlDesignInputName(inputForm.getSqlDesignInputName());
			sqlDesignInput.setTableId(DataTypeUtils.convertTo(inputForm.getTableId(),Long.class));
			sqlDesignInput.setColumnId(DataTypeUtils.convertTo(inputForm.getColumnId(),Long.class));
			sqlDesignInput.setDesignType(DataTypeUtils.convertTo(inputForm.getDesignType(),Integer.class));
			sqlDesignInput.setObjectType(DataTypeUtils.convertTo(inputForm.getObjectType(),Integer.class));
		}
		return sqlDesignInput;
	}

	@Override
	public InputForm convertFrom(SqlDesignInput sqlDesignInput,InputForm inputForm) {
		if(sqlDesignInput!=null){
			if(inputForm==null){
				inputForm = new InputForm();
			}
			inputForm.setDataType(sqlDesignInput.getDataType().toString());
//			String itemSeqNo = sqlDesignInput.getItemSeqNo();
//			if(StringUtils.isEmpty(itemSeqNo)){
//				itemSeqNo = StringUtils.EMPTY;
//			}
//			if(itemSeqNo.indexOf(".0")==itemSeqNo.length()-2){
//				itemSeqNo = StringUtils.left(itemSeqNo,itemSeqNo.length()-2);
//			}
//			Integer lastIndex = itemSeqNo.lastIndexOf(".");
			Integer lastIndex = sqlDesignInput.getGroupIndex().lastIndexOf(".");
			if(lastIndex > 0) {
				inputForm.setGroupId(sqlDesignInput.getGroupIndex().substring(0, lastIndex));
			}
			inputForm.setGroupIndex(sqlDesignInput.getGroupIndex());
			inputForm.setIsArray(sqlDesignInput.getArrayFlag()==null || sqlDesignInput.getArrayFlag()==0?false:true);
			inputForm.setItemSeqNo(sqlDesignInput.getItemSeqNo().toString());
			inputForm.setSqlDesignId(sqlDesignInput.getSqlDesignId());
			inputForm.setSqlDesignInputCode(sqlDesignInput.getSqlDesignInputCode());
			inputForm.setSqlDesignInputId(sqlDesignInput.getSqlDesignInputId());
			inputForm.setSqlDesignInputParentId(sqlDesignInput.getSqlDesignInputParentId());
			inputForm.setSqlDesignInputName(sqlDesignInput.getSqlDesignInputName());
			inputForm.setTableId(DataTypeUtils.toString(sqlDesignInput.getTableId()));
			inputForm.setColumnId(DataTypeUtils.toString(sqlDesignInput.getColumnId()));
			inputForm.setDesignType(DataTypeUtils.toString(sqlDesignInput.getDesignType()));
			inputForm.setObjectType(DataTypeUtils.toString(sqlDesignInput.getObjectType()));
			
		}
		return inputForm;
	}
}
