package org.terasoluna.qp.domain.repository.impactchange;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.CommonObjectAttribute;
import org.terasoluna.qp.domain.model.DecisionTableInputBean;
import org.terasoluna.qp.domain.model.DecisionTableOutputBean;
import org.terasoluna.qp.domain.model.FunctionMethodInput;
import org.terasoluna.qp.domain.model.FunctionMethodOutput;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.SessionManagement;
import org.terasoluna.qp.domain.model.SqlDesignInput;
import org.terasoluna.qp.domain.model.SqlDesignOutput;

public interface ImpactCommonObjectRepository {

	List<CommonObjectAttribute> findCommonObjAttrByUsingCommonObId(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
	
	List<SessionManagement> findSesionManagementByUsingCommonObId(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
	
	List<FunctionMethodInput> findFunctionMethodInputByUsingCommonObId(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);

	List<FunctionMethodOutput> findFunctionMethodOutputByUsingCommonObId(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
	
	List<SqlDesignInput> findSQLInputByUsingCommonObId(@Param("commonObjectDefinitionId")Long commonObjectDefinitionId);
	
	List<SqlDesignOutput> findSQLOutputByUsingCommonObId(@Param("commonObjectDefinitionId")Long commonObjectDefinitionId);
	
	List<DecisionTableInputBean> findDecisionTableInputBeanByUsingCommonObId(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
	
	List<DecisionTableOutputBean> findDecisionTableOutputBeanByUsingCommonObId(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
	
	List<InputBean> findInputBeanByUsingCommonObId(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
	
	List<OutputBean> findOutputBeanByUsingCommonObId(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
	
	List<ObjectDefinition> findObjectDefinitionByUsingCommonObId(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
	
	List<InputBean> findInputBeanByUsingCommonObjectDefinitionId(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
	
	List<InputBean> findInputBeanByParentInputBeanUsingCommonObjectDefinitionId(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
	
	List<OutputBean> findOutputBeanByUsingCommonObjectDefinitionId(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
	
	List<OutputBean> findOutputBeanByParentOutputBeanUsingCommonObjectDefinitionId(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
	
	List<ObjectDefinition> findObjectDefinitionByUsingCommonObjectDefinitionId(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
	
	List<ObjectDefinition> findObjectDefinitionByParentObjectDefinitionUsingCommonObjectDefinitionId(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
	
	List<DecisionTableInputBean> findDecisionTableInputBeanByUsingCommonObjectDefinitionId(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
	
	List<DecisionTableInputBean> findDecisionTableInputBeanByParentInputBeanUsingCommonObjectDefinitionId(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
	
	List<DecisionTableOutputBean> findDecisionTableOutputBeanByUsingCommonObjectDefinitionId(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
	
	List<DecisionTableOutputBean> findDecisionTableOutputBeanByParentOutputBeanUsingCommonObjectDefinitionId(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
	
	List<SqlDesignInput> findSqlDesignInputByUsingCommonObjectDefinitionId(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
	
	List<SqlDesignInput> findSqlDesignInputByParentSqlDesignInputUsingCommonObjectDefinitionId(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
	
	List<SqlDesignOutput> findSqlDesignOutputByUsingCommonObjectDefinitionId(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
	
	List<SqlDesignOutput> findSqlDesignOutputByParentSqlDesignOutputUsingCommonObjectDefinitionId(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
	
	List<FunctionMethodInput> findFunctionMethodInputByUsingCommonObjectDefinitionId(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
	
	List<FunctionMethodInput> findFunctionMethodInputByParentInputUsingCommonObjectDefinitionId(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
	
	List<FunctionMethodOutput> findFunctionMethodOutputByUsingCommonObjectDefinitionId(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
	
	List<FunctionMethodOutput> findFunctionMethodOutputByParentOutputUsingCommonObjectDefinitionId(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
}
