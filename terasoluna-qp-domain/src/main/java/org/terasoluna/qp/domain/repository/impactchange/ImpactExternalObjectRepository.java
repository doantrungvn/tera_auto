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

public interface ImpactExternalObjectRepository {
	
	List<InputBean> findInputBeanByUsingExternalObId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
	List<InputBean> findInputBeanByParentInputBeanUsingExternalObId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
	List<DecisionTableInputBean> findDecisionTableInputBeanByUsingExtObId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
	List<DecisionTableOutputBean> findDecisionTableOutputBeanByUsingExtObId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);

	List<SqlDesignInput> findSQLInputByUsingExtObId(@Param("externalObjectDefinitionId")Long externalObjectDefinitionId);

	List<SqlDesignOutput> findSQLOutputByUsingExtObId(@Param("externalObjectDefinitionId")Long externalObjectDefinitionId);

	List<FunctionMethodInput> findFunctionMethodInputByUsingExtObId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);

	List<FunctionMethodOutput> findFunctionMethodOutputByUsingExtObId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
	List<SessionManagement> findSesionManagementByUsingExternalObject(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
	List<CommonObjectAttribute> findCommonObjAttrByUsingExtObId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
	List<InputBean>  findInputBeanByUsingExtObId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
	List<OutputBean>  findOutputBeanByUsingExtObId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
	List<ObjectDefinition>  findObjectDefinitionByUsingExtObId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
	List<OutputBean> findOutputBeanByUsingExternalObId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
	List<OutputBean> findOutputBeanByParentOutputBeanUsingExternalObId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
	List<ObjectDefinition> findObjectDefinitionByUsingExternalObId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
	List<ObjectDefinition> findObjectDefinitionByParentObjectDefinitionUsingExternalObId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
	List<DecisionTableInputBean> findDecisionTableInputBeanByUsingExternalObId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
	List<DecisionTableInputBean> findDecisionTableInputBeanByParentInputBeanUsingExternalObId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
	List<DecisionTableOutputBean> findDecisionTableOutputBeanByUsingExternalObId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
	List<DecisionTableOutputBean> findDecisionTableOutputBeanByParentOutputBeanUsingExternalObId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
	List<SqlDesignInput> findSqlDesignInputByUsingExternalObId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
	List<SqlDesignInput> findSqlDesignInputByParentSqlDesignInputUsingExternalObId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
	List<SqlDesignOutput> findSqlDesignOutputByUsingExternalObId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
	List<SqlDesignOutput> findSqlDesignOutputByParentSqlDesignOutputUsingExternalObId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
	List<FunctionMethodInput> findFunctionMethodInputByUsingExternalObId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
	List<FunctionMethodInput> findFunctionMethodInputByParentInputUsingExternalObId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
	List<FunctionMethodOutput> findFunctionMethodOutputByUsingExternalObId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
	List<FunctionMethodOutput> findFunctionMethodOutputByParentOutputUsingExternalObId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
}
