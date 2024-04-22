package org.terasoluna.qp.domain.repository.sqldesign;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.SqlDesignOutput;

public interface SqlDesignOutputRepository {
	
	SqlDesignOutput findById(Long sqlDesignOutputId);

	void register(SqlDesignOutput sqlDesignOutput);
	
	void registerAll(@Param("sqlDesignOutputs") SqlDesignOutput[] sqlDesignOutputs);

	void modify(SqlDesignOutput SqlDesignOutput);
	
	void modifyAll(@Param("sqlDesignOutputs") SqlDesignOutput[] sqlDesignOutputs);
	
	void delete(SqlDesignOutput sqlDesignOutput);

	SqlDesignOutput[] findAllBySqlDesignId(Long sqlDesignId);
	
	Boolean deleteByExceptionalGroup(@Param("sqlDesignId") Long sqlDesignId,@Param("exceptionGroup")List<Long> exceptionGroup);
	
	Boolean deleteBySqlDesignId(@Param("sqlDesignId") Long sqlDesignId);
	
	Long preserveIds(Integer preservedNumber);
	
	List<SqlDesignOutput> getReferencedFromBusinessLogic(Long sqlDesignId);

	List<SqlDesignOutput> getReferencedFromScreenDesign(Long sqlDesignId);

	SqlDesignOutput[] findAllForGenerationBySqlDesignId(Long sqlDesignId);
}
