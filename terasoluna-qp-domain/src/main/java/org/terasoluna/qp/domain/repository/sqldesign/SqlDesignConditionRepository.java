package org.terasoluna.qp.domain.repository.sqldesign;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.SqlDesignCondition;
import org.terasoluna.qp.domain.model.TableDesign;

public interface SqlDesignConditionRepository {
	
	SqlDesignCondition findById(SqlDesignCondition sqlDesignConditionsId);

	void register(SqlDesignCondition sqlDesignCondition);
	
	void registerAll(@Param("sqlDesignConditions") SqlDesignCondition[] sqlDesignConditions);

	void modify(SqlDesignCondition sqlDesignConditions);
	
	void modifyAll(@Param("sqlDesignConditions") SqlDesignCondition[] sqlDesignConditions);
	
	void delete(SqlDesignCondition sqlDesignConditions);

	SqlDesignCondition[] findAllBySqlDesignId(Long sqlDesignId);
	
	Boolean deleteByExceptionalGroup(@Param("sqlDesignId") Long sqlDesignId,@Param("exceptionGroup")List<Long> exceptionGroup);
	
	Boolean deleteBySqlDesignId(@Param("sqlDesignId") Long sqlDesignId);
	
	int updateNameAffect(@Param("tableDesigns") List<TableDesign> tableDesigns);
}
