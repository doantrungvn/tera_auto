package org.terasoluna.qp.domain.repository.sqldesign;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.SqlDesignFunctionGroup;

public interface SqlDesignFunctionRepository {
	
	List<SqlDesignFunctionGroup> findAllByGroup(@Param("dialect")String dialect,@Param("language")String language);
}
