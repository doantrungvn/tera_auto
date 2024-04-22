package org.terasoluna.qp.domain.repository.sqldesign;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.SqlDesignHaving;

public interface SqlDesignHavingRepository {
	
	SqlDesignHaving findById(Long sqlDesignHavingId);

	void register(SqlDesignHaving sqlDesignHaving);
	
	void registerAll(@Param("sqlDesignHavings") SqlDesignHaving[] sqlDesignHavings);

	void modify(SqlDesignHaving sqlDesignHaving);
	
	void modifyAll(@Param("sqlDesignHavings") SqlDesignHaving[] sqlDesignHavings);
	
	void delete(SqlDesignHaving sqlDesignHaving);

	SqlDesignHaving[] findAllBySqlDesignId(Long sqlDesignId);
	
	Boolean deleteByExceptionalGroup(@Param("sqlDesignId") Long sqlDesignId,@Param("exceptionGroup")List<Long> exceptionGroup);
	
	Boolean deleteBySqlDesignId(@Param("sqlDesignId") Long sqlDesignId);
}
