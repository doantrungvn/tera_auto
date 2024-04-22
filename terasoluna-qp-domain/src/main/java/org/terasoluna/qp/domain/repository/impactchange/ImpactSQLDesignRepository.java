package org.terasoluna.qp.domain.repository.impactchange;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.DomainDesign;
import org.terasoluna.qp.domain.model.ExecutionComponent;
import org.terasoluna.qp.domain.model.ExecutionInputValue;
import org.terasoluna.qp.domain.model.ExecutionOutputValue;
import org.terasoluna.qp.domain.model.SqlDesignOutput;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.model.TableDesignDetails;

public interface ImpactSQLDesignRepository {

	List<ExecutionComponent> findExecutionComponentBySqlDesignId(@Param("sqlDesignId") Long sqlDesignId);
	
	void deleteExecutionInputValueByExecutionComponent(@Param("lstComponents") List<ExecutionComponent> lstComponents);
	
	void deleteExecutionOutputValueByExecutionComponent(@Param("lstComponents") List<ExecutionComponent> lstComponents);
	
	List<BusinessDesign> findAllBussinessLogicBySqlDesignId(@Param("sqlDesignId") Long sqlDesignId);
	
	List<DomainDesign> findAllDomainDesignsBySqlDesignId(@Param("sqlDesignId") Long sqlDesignId);
	
	List<TableDesign> findAllTableDesignsBySqlDesignId(@Param("sqlDesignId") Long sqlDesignId);
	
	List<TableDesignDetails> findAllTableDesignDetailsBySqlDesignId(@Param("sqlDesignId") Long sqlDesignId);
	
	List<ExecutionInputValue> findExecutionInputValueBySqlDesignId(@Param("sqlDesignId") Long sqlDesignId);
	
	List<ExecutionOutputValue> findExecutionOutputValueBySqlDesignId(@Param("sqlDesignId") Long sqlDesignId);
	
	List<DomainDesign> findAllDomainDesignsBySqlDesignIdAndNotReferOutput(@Param("sqlDesignId") Long sqlDesignId,@Param("lstSqlDesignOutputs")List<SqlDesignOutput> lstSqlDesignOutputs);
	
	List<TableDesignDetails> findAllTableDesignDetailsBySqlDesignIdAndNotReferOutput(@Param("sqlDesignId") Long sqlDesignId,@Param("lstSqlDesignOutputs")List<SqlDesignOutput> lstSqlDesignOutputs);
}
