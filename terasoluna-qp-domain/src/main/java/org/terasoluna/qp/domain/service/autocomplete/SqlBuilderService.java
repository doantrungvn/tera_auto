package org.terasoluna.qp.domain.service.autocomplete;

import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.service.sqldesign.SqlDesignCompound;

public interface SqlBuilderService{
	String buildSql(SqlDesignCompound autocompleteDesignCompound,String dialect, CommonModel common);
	boolean validate(SqlDesignCompound autocomplete);
	void analyzeAdvanced(SqlDesign sqlDesign);
}
