package org.terasoluna.qp.domain.repository.sqldesign;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.SqlDesignTableItem;
import org.terasoluna.qp.domain.model.TableDesign;

public interface SqlDesignTableItemsRepository {
	
	SqlDesignTableItem findById(Long sqlDesignTableItemId);

	void register(SqlDesignTableItem sqlDesignTableItem);
	
	void registerAll(@Param("sqlDesignTableItems") SqlDesignTableItem[] sqlDesignTableItems);

	void modify(SqlDesignTableItem sqlDesignTableItem);
	
	void modifyAll(@Param("sqlDesignTableItems") SqlDesignTableItem[] sqlDesignTableItems);
	
	void delete(SqlDesignTableItem sqlDesignTableItem);

	SqlDesignTableItem[] findAllBySqlDesignTableId(Long sqlDesignTableIdId);
	
	List<SqlDesignTableItem> findAllBySqlDesignId(Long sqlDesignId);
	
	Boolean deleteByExceptionalGroup(@Param("exceptionGroup")List<Long> exceptionGroup);

	Boolean deleteGroupBySqlDesignId(@Param("sqlDesignId")Long sqlDesignTableId,@Param("exceptionGroup") List<Long> nestedExceptionGroup);
	
	Boolean deleteBySqlDesignId(@Param("sqlDesignId") Long sqlDesignId);
	
	int updateNameAffect(@Param("tableDesigns") List<TableDesign> tableDesigns);
}
