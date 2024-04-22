package org.terasoluna.qp.domain.repository.sqldesign;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.ConsistencyValidationModel;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.model.TableDesign;

public interface SqlDesignRepository {
	
	SqlDesign findOneById(Long sqlDesignId);

	void register(SqlDesign sqlDesign);

	Boolean modify(SqlDesign sqlDesign);
	
	Boolean delete(SqlDesign sqlDesignId);

	void modifyFromAutocomplete(SqlDesign sqlDesign);
	
	List<SqlDesign> findByTableDesignId(@Param("tableDesigns") List<TableDesign> listOfTables);

	void modifySqlText(SqlDesign sqlDesign);
	
	Long countByCriteria(@Param("criteria")SqlDesignSearchCriteria searchCriteria);
	
	List<SqlDesign> findByCriteria(@Param("criteria")SqlDesignSearchCriteria searchCriteria,@Param("pageable")Pageable pageable);
	
	Long countViewByCriteria(@Param("criteria")SqlDesignSearchCriteria searchCriteria);
	
	List<SqlDesign> findViewByCriteria(@Param("criteria")SqlDesignSearchCriteria searchCriteria,@Param("pageable")Pageable pageable);
	
	Integer getExistNameCode(SqlDesign sqlDesign);

	List<ConsistencyValidationModel> getReferenceById(@Param("sqlDesignId")Long sqlDesignId,@Param("languageId")Long languageId);
	
	void deleteSqlDesignChildren(Long sqlDesignId);

	boolean modifyDesignStatus(SqlDesign sqlDesign);

	void convertToAdvanced(Long sqlDesignId);

	void deleteSqlDesignChildrenExceptIo(Long sqlDesignId);
	
	Long getSequencesSqlDesign(@Param("size") Integer size);

	Boolean modifyWithDesignType(SqlDesign sqlDesign);
	
	int modifyAffectChangeDesign(@Param("sqlDesign")List<SqlDesign> sqlDesign);
	
	List<Long> getAllSqlDesignId(@Param("projectId") Long projectId);
	
	Long[] checkSqlDesignExists(@Param("listOfId") List<Long> listOfId);
	
	List<SqlDesign> getAllViewDesignByProjectId(Long projectId);
	
	List<SqlDesign> getAllSqlDesignByProjectId(@Param("projectId") Long projectId, @Param("moduleType") Integer moduleType);
	
	List<SqlDesign> getAllSqlDesignByModuleId(@Param("moduleId") Long moduleId,@Param("projectId") Long projectId);
	
	List<SqlDesign> getSQLDesignByProjectId(Long projectId);
	
	long getSQLDesignAdvanceByProjectId(@Param("projectId") Long projectId);

	List<SqlDesign> findAllAffactedSqlDesignsByModuleId(@Param("moduleId") Long moduleId);
	
	List<SqlDesign> findSQLDesignByUsingExternalOb (@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
	List<SqlDesign> findSQLDesignByUsingCommonOb (@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
	
	List<SqlDesign> getAllSqlDesignByProjectIdWithCommon(@Param("projectId") Long projectId, @Param("moduleType") Integer moduleType);

	Integer getExistNameCodeViewDesign(SqlDesign sqlDesign);
	
}
