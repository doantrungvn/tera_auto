package org.terasoluna.qp.domain.repository.sqldesign;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.SqlDesignTable;
import org.terasoluna.qp.domain.model.TableDesign;

public interface SqlDesignTableRepository {
	
	SqlDesignTable findById(Long sqlDesignTableId);

	void register(SqlDesignTable sqlDesignTable);
	
	void registerAll(@Param("sqlDesignTables") SqlDesignTable[] sqlDesignTables);

	void modify(SqlDesignTable sqlDesignTable);
	
	void modifyAll(@Param("sqlDesignTables") SqlDesignTable[] sqlDesignTables);
	
	void delete(SqlDesignTable sqlDesignTable);
	
	SqlDesignTable[] findAllBySqlDesignId(Long sqlDesignId);
	
	Boolean deleteByExceptionalGroup(@Param("sqlDesignId") Long sqlDesignId,@Param("exceptionGroup")List<Long> exceptionGroup);
	
	Boolean deleteBySqlDesignId(@Param("sqlDesignId") Long sqlDesignId);
	
	Long getSequencesSqlDesignTable(@Param("size") Integer size);
	
	void registerAllHaveId(@Param("sqlDesignTables") SqlDesignTable[] sqlDesignTables);
	
	int updateNameAffect(@Param("tableDesigns") List<TableDesign> tableDesigns);
}
