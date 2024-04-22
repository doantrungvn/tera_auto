package org.terasoluna.qp.domain.repository.sqldesign;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.SqlDesignInput;

public interface SqlDesignInputRepository {
	
	SqlDesignInput findById(Long sqlDesignInputId);

	void register(SqlDesignInput sqlDesignInput);
	
	void registerAll(@Param("sqlDesignInputs") SqlDesignInput[] sqlDesignInputs);

	void modify(SqlDesignInput SqlDesignInput);
	
	void modifyAll(@Param("sqlDesignInputs") SqlDesignInput[] sqlDesignInputs);
	
	void delete(SqlDesignInput sqlDesignInput);

	SqlDesignInput[] findAllBySqlDesignId(Long sqlDesignId);
	
	Boolean deleteByExceptionalGroup(@Param("sqlDesignId") Long sqlDesignId,@Param("exceptionGroup")List<Long> exceptionGroup);
	
	Boolean deleteBySqlDesignId(@Param("sqlDesignId") Long sqlDesignId);
	
	Long preserveIds(Integer preservedNumber);

	List<SqlDesignInput> getReferencedFromBusinessLogic(Long sqlDesignId);

	SqlDesignInput[] findAllForGenerationBySqlDesignId(Long sqlDesignId);
}
