package org.terasoluna.qp.domain.repository.sqldesign;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.SqlDesignResult;
import org.terasoluna.qp.domain.model.TableDesign;

public interface SqlDesignResultRepository {
	
	SqlDesignResult findById(Long sqlDesignResultsId);

	void register(SqlDesignResult sqlDesignResults);
	
	void registerAll(@Param("sqlDesignResults") SqlDesignResult[] sqlDesignResults);

	void modify(SqlDesignResult sqlDesignResults);
	
	void modifyAll(@Param("sqlDesignResults") SqlDesignResult[] sqlDesignResults); 
	
	void delete(SqlDesignResult sqlDesignResults);

	SqlDesignResult[] findAllBySqlDesignId(Long sqlDesignId);
	
	Boolean deleteByExceptionalGroup(@Param("sqlDesignId") Long sqlDesignId,@Param("exceptionGroup")List<Long> exceptionGroup);
	
	Boolean deleteBySqlDesignId(@Param("sqlDesignId") Long sqlDesignId);
	
	int updateNameAffect(@Param("tableDesigns") List<TableDesign> tableDesigns);

	SqlDesignResult[] findAllByIds(@Param("sqlDesignResults")List<SqlDesignResult> sqlDesignResults);

}
