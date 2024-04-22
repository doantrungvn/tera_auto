package org.terasoluna.qp.app.sqldesign.converter;


import org.dozer.DozerConverter;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.sqldesign.OutputForm;
import org.terasoluna.qp.domain.model.SqlDesignOutput;

public class SqlDesignOutputConverter extends DozerConverter<OutputForm, SqlDesignOutput>  {

	public SqlDesignOutputConverter() {
		super(OutputForm.class, SqlDesignOutput.class);
	}

	@Override
	public SqlDesignOutput convertTo(OutputForm outputForm,SqlDesignOutput sqlDesignOutput) {
		if(outputForm!=null){
			if(sqlDesignOutput==null){
				sqlDesignOutput = new SqlDesignOutput();
			}
			sqlDesignOutput.setDataType(DataTypeUtils.convertTo(outputForm.getDataType(),Integer.class));
			sqlDesignOutput.setArrayFlag(!(outputForm.getIsArray()==null || !outputForm.getIsArray())?1:0);
			sqlDesignOutput.setItemSeqNo(DataTypeUtils.convertTo(outputForm.getItemSeqNo(),Integer.class));
			sqlDesignOutput.setGroupId(outputForm.getGroupId());
			sqlDesignOutput.setGroupIndex(outputForm.getGroupIndex());
			sqlDesignOutput.setSqlDesignId(outputForm.getSqlDesignId());
			sqlDesignOutput.setSqlDesignOutputCode(outputForm.getSqlDesignOutputCode());
			sqlDesignOutput.setSqlDesignOutputId(outputForm.getSqlDesignOutputId());
			sqlDesignOutput.setSqlDesignOutputName(outputForm.getSqlDesignOutputName());
			sqlDesignOutput.setMappingColumn(outputForm.getMappingColumn());
			sqlDesignOutput.setTableId(DataTypeUtils.convertTo(outputForm.getTableId(), Long.class));
			sqlDesignOutput.setColumnId(DataTypeUtils.convertTo(outputForm.getColumnId(), Long.class));
			sqlDesignOutput.setSqlDesignOutputParentId(outputForm.getSqlDesignOutputParentId());
			sqlDesignOutput.setDesignType(DataTypeUtils.convertTo(outputForm.getDesignType(),Integer.class));
			sqlDesignOutput.setObjectType(DataTypeUtils.convertTo(outputForm.getObjectType(),Integer.class));
		}
		return sqlDesignOutput;
	}

	@Override
	public OutputForm convertFrom(SqlDesignOutput sqlDesignOutput,OutputForm outputForm) {
		if(sqlDesignOutput!=null){
			if(outputForm==null){
				outputForm = new OutputForm();
			}
			outputForm.setDataType(sqlDesignOutput.getDataType().toString());
//			String itemSeqNo = sqlDesignOutput.getItemSeqNo();
//			if(StringUtils.isEmpty(itemSeqNo)){
//				itemSeqNo = StringUtils.EMPTY;
//			}
//			if(itemSeqNo.indexOf(".0")==itemSeqNo.length()-2){
//				itemSeqNo = StringUtils.left(itemSeqNo,itemSeqNo.length()-2);
//			}
//			Integer lastIndex = itemSeqNo.lastIndexOf(".");
			
			Integer lastIndex = sqlDesignOutput.getGroupIndex().lastIndexOf(".");
			if(lastIndex > 0) {
				outputForm.setGroupId(sqlDesignOutput.getGroupIndex().substring(0, lastIndex));
			}
			outputForm.setGroupIndex(sqlDesignOutput.getGroupIndex());
			outputForm.setIsArray(sqlDesignOutput.getArrayFlag()==null || sqlDesignOutput.getArrayFlag()==0?false:true);
			if(sqlDesignOutput.getItemSeqNo() != null) {
				outputForm.setItemSeqNo(sqlDesignOutput.getItemSeqNo().toString());
			}
			outputForm.setSqlDesignId(sqlDesignOutput.getSqlDesignId());
			outputForm.setSqlDesignOutputCode(sqlDesignOutput.getSqlDesignOutputCode());
			outputForm.setSqlDesignOutputId(sqlDesignOutput.getSqlDesignOutputId());
			outputForm.setSqlDesignOutputName(sqlDesignOutput.getSqlDesignOutputName());
			outputForm.setMappingColumn(sqlDesignOutput.getMappingColumn());
			outputForm.setTableId(DataTypeUtils.toString(sqlDesignOutput.getTableId()));
			outputForm.setColumnId(DataTypeUtils.toString(sqlDesignOutput.getColumnId()));
			outputForm.setSqlDesignOutputParentId(sqlDesignOutput.getSqlDesignOutputParentId());
			outputForm.setDesignType(DataTypeUtils.toString(sqlDesignOutput.getDesignType()));
			outputForm.setObjectType(DataTypeUtils.toString(sqlDesignOutput.getObjectType()));
		}
		return outputForm;
	}
}
