package org.terasoluna.qp.domain.repository.sqldesign;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.SqlDesignValue;
import org.terasoluna.qp.domain.model.TableDesign;

public interface SqlDesignValueRepository {
	
	SqlDesignValue findById(Long sqlDesignValueId);

	void register(SqlDesignValue sqlDesignValue);
	
	void registerAll(@Param("sqlDesignValues") SqlDesignValue[] sqlDesignValues);

	void modify(SqlDesignValue sqlDesignValue);
	
	void modifyAll(@Param("sqlDesignValues") SqlDesignValue[] sqlDesignValues);
	
	void delete(SqlDesignValue sqlDesignValue);

	SqlDesignValue[] findAllBySqlDesignId(Long sqlDesignId);
	
	Boolean deleteByExceptionalGroup(@Param("sqlDesignId") Long sqlDesignId,@Param("exceptionGroup")List<Long> exceptionGroup);
	
	Boolean deleteBySqlDesignId(@Param("sqlDesignId") Long sqlDesignId);
	
	int updateNameAffect(@Param("tableDesigns") List<TableDesign> tableDesigns);
}
