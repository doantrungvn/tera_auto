package org.terasoluna.qp.domain.repository.sqldesign;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.SqlDesignGroupBy;

public interface SqlDesignGroupByRepository {
	
	SqlDesignGroupBy findById(Long sqlDesignGroupById);

	void register(SqlDesignGroupBy sqlDesignGroupBy);
	
	void registerAll(@Param("sqlDesignGroupBys") SqlDesignGroupBy[] sqlDesignGroupBys);

	void modify(SqlDesignGroupBy sqlDesignGroupBy);
	
	void modifyAll(@Param("sqlDesignGroupBys") SqlDesignGroupBy[] sqlDesignGroupBys);
	
	void delete(SqlDesignGroupBy sqlDesignGroupBy);

	SqlDesignGroupBy[] findAllBySqlDesignId(Long sqlDesignId);
	
	Boolean deleteByExceptionalGroup(@Param("sqlDesignId") Long sqlDesignId,@Param("exceptionGroup")List<Long> exceptionGroup);
	
	Boolean deleteBySqlDesignId(@Param("sqlDesignId") Long sqlDesignId);
}
