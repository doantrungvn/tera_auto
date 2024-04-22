package org.terasoluna.qp.app.sqldesign.converter;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerConverter;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.sqldesign.SqlDesignForm;
import org.terasoluna.qp.domain.model.SqlDesign;

public class SqlDesignConverter extends DozerConverter<SqlDesignForm, SqlDesign>  {

	public SqlDesignConverter() {
		super(SqlDesignForm.class, SqlDesign.class);
	}

	@Override
	public SqlDesignForm convertFrom(SqlDesign sqlDesign, SqlDesignForm sqlDesignForm) {
		if(sqlDesign!=null) {
			if(sqlDesignForm==null) {
				sqlDesignForm = new SqlDesignForm();
			}
			sqlDesignForm.setSqlDesignId(String.valueOf(sqlDesign.getSqlDesignId()));
			sqlDesignForm.setSqlDesignName(sqlDesign.getSqlDesignName());
			sqlDesignForm.setSqlDesignCode(sqlDesign.getSqlDesignCode());
			sqlDesignForm.setCreatedBy(String.valueOf(sqlDesign.getCreatedBy()));
			sqlDesignForm.setCreatedDate(sqlDesign.getCreatedDate());
			sqlDesignForm.setDesignStatus(String.valueOf(sqlDesign.getDesignStatus()));
			sqlDesignForm.setDesignType(String.valueOf(sqlDesign.getDesignType()));
			sqlDesignForm.setModuleId(sqlDesign.getModuleId()==null?StringUtils.EMPTY:sqlDesign.getModuleId().toString());
			sqlDesignForm.setModuleIdAutocomplete(sqlDesign.getModuleName());
			sqlDesignForm.setOmitOverlap(sqlDesign.getOmitOverlap()==1?true:false);
			sqlDesignForm.setProjectId(String.valueOf(sqlDesign.getProjectId()));
			sqlDesignForm.setProjectIdAutocomplete(sqlDesign.getProjectName());
			sqlDesignForm.setRemark(sqlDesign.getRemark());
			sqlDesignForm.setReturnType(String.valueOf(sqlDesign.getReturnType()));
			sqlDesignForm.setSqlPattern(String.valueOf(sqlDesign.getSqlPattern()));
			sqlDesignForm.setSqlText(sqlDesign.getSqlText());
			sqlDesignForm.setUpdatedBy(String.valueOf(sqlDesign.getUpdatedBy()));
			sqlDesignForm.setUpdatedDate(sqlDesign.getUpdatedDate());
			sqlDesignForm.setVerifiedDate(sqlDesign.getVerifiedDate());
			sqlDesignForm.setIsConversion(sqlDesign.getIsConversion());
			sqlDesignForm.setPageable(sqlDesign.getPageable());
		}
		return sqlDesignForm;
	}

	@Override
	public SqlDesign convertTo(SqlDesignForm sqlDesignForm, SqlDesign sqlDesign) {
		if(sqlDesignForm!=null) {
			if(sqlDesign==null) {
				sqlDesign = new SqlDesign();
			}
			sqlDesign.setSqlDesignId(DataTypeUtils.convertTo(sqlDesignForm.getSqlDesignId(),Long.class));
			sqlDesign.setSqlDesignName(sqlDesignForm.getSqlDesignName());
			sqlDesign.setSqlDesignCode(sqlDesignForm.getSqlDesignCode());
			sqlDesign.setCreatedBy(DataTypeUtils.convertTo(sqlDesignForm.getCreatedBy(),Long.class));
			sqlDesign.setCreatedDate(sqlDesignForm.getCreatedDate());
			sqlDesign.setDesignStatus(DataTypeUtils.convertTo(sqlDesignForm.getDesignStatus(),Integer.class));
			sqlDesign.setDesignType(DataTypeUtils.convertTo(sqlDesignForm.getDesignType(),Integer.class));
			sqlDesign.setModuleId(StringUtils.isEmpty(sqlDesignForm.getModuleId())?null:DataTypeUtils.convertTo(sqlDesignForm.getModuleId(),Long.class));
			sqlDesign.setModuleName(sqlDesignForm.getModuleIdAutocomplete());
			sqlDesign.setOmitOverlap(sqlDesignForm.getOmitOverlap()==null || !sqlDesignForm.getOmitOverlap() ?0:1);
			sqlDesign.setProjectId(SessionUtils.getCurrentProjectId());
			sqlDesign.setProjectName(sqlDesignForm.getProjectIdAutocomplete());
			sqlDesign.setRemark(sqlDesignForm.getRemark());
			sqlDesign.setReturnType(DataTypeUtils.convertTo(sqlDesignForm.getReturnType(),Integer.class));
			sqlDesign.setSqlPattern(DataTypeUtils.convertTo(sqlDesignForm.getSqlPattern(),Integer.class));
			sqlDesign.setSqlText(sqlDesignForm.getSqlText());
			sqlDesign.setUpdatedBy(DataTypeUtils.convertTo(sqlDesignForm.getUpdatedBy(),Long.class));
			sqlDesign.setUpdatedDate(sqlDesignForm.getUpdatedDate());
			sqlDesign.setVerifiedDate(sqlDesignForm.getVerifiedDate());
			sqlDesign.setIsConversion(sqlDesignForm.getIsConversion());
			sqlDesign.setPageable(sqlDesignForm.getPageable());
		}
		return sqlDesign;
	}
}
