package org.terasoluna.qp.app.sqldesign.converter;

import org.apache.commons.lang3.ArrayUtils;
import org.dozer.DozerConverter;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.sqldesign.WhereForm;
import org.terasoluna.qp.domain.model.SqlDesignInput;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeConst.SqlConditionType;

public class SqlDesignInputWhereConverter extends DozerConverter<WhereForm[], SqlDesignInput[]>  {

	public SqlDesignInputWhereConverter() {
		super(WhereForm[].class, SqlDesignInput[].class);
	}

	@Override
	public SqlDesignInput[] convertTo(WhereForm[] whereForms, SqlDesignInput[] sqlDesignInputs) {
		return sqlDesignInputs;
	}

	@Override
	public WhereForm[] convertFrom(SqlDesignInput[] sqlDesignInputs, WhereForm[] whereForms) {
		if(ArrayUtils.isNotEmpty(whereForms)){
			for(WhereForm whereForm:whereForms){
				if(DataTypeUtils.equals(whereForm.getConditionType(),SqlConditionType.PARAMETER)){
					for(int i=0;i<sqlDesignInputs.length;i++){
						if(DataTypeUtils.equals(whereForm.getArg(),sqlDesignInputs[i].getGroupIndex())){
							whereForm.setDisplayArg(sqlDesignInputs[i].getSqlDesignInputCode());
						}
						if(DataTypeUtils.equals(whereForm.getArg2(),sqlDesignInputs[i].getGroupIndex())){
							whereForm.setDisplayArg2(sqlDesignInputs[i].getSqlDesignInputCode());
						}
					}
				}
			}
		}
		return whereForms;
	}
}
