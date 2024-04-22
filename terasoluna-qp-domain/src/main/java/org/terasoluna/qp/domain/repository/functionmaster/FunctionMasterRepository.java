package org.terasoluna.qp.domain.repository.functionmaster;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.AffectedBusinessDesign;
import org.terasoluna.qp.domain.model.AffectedDecisionTable;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.DecisionTable;
import org.terasoluna.qp.domain.model.ExternalObjectAttribute;
import org.terasoluna.qp.domain.model.FunctionMaster;
import org.terasoluna.qp.domain.model.FunctionMethod;
import org.terasoluna.qp.domain.model.FunctionMethodInput;
import org.terasoluna.qp.domain.model.FunctionMethodOutput;

@Repository
public interface FunctionMasterRepository {

	long countBySearchCriteria(@Param("criteria") FunctionMasterCriteria criteria);

	List<FunctionMaster> findPageBySearchCriteria(@Param("criteria") FunctionMasterCriteria criteria, @Param("pageable") Pageable pageable);

	Long countNameCodeExisted(FunctionMaster functionMaster);

	Long register(FunctionMaster functionMaster);

	void multiCreateFunctionMethod(@Param("functionMethods") List<FunctionMethod> functionMethods);

	void multiCreateFunctionInput(@Param("methodInputs") List<FunctionMethodInput> methodInputs);

	void multiCreateFunctionOutput(@Param("methodOutputs") List<FunctionMethodOutput> methodOutputs);

	FunctionMaster findOneFuntionMasterById(Long functionMasterId);

	List<FunctionMethod> findFuntionMethodByFunctionMasterId(Long functionMasterId);

	List<FunctionMethodInput> findFunctionMethodInputByFunctionMasterId(Long functionMasterId);

	List<FunctionMethodOutput> findFunctionMethodOutputByFunctionMasterId(Long functionMasterId);

	int modify(FunctionMaster functionMaster);

	int deleteOneFunctionMaster(Long functionMasterId);

	int delete(Long functionMasterId);

	int multiDeleteFunctionMethod(@Param("multiDeleteFunctionMethod") List<FunctionMethod> multiDeleteFunctionMethod);

	int multiUpdateFunctionMethod(@Param("functionMethodsUpdate") List<FunctionMethod> functionMethodsUpdate);

	int multiDeleteMethodInput(@Param("functionMethodInputDelete") List<FunctionMethodInput> functionMethodInputDelete);

	int multiDeleteMethodOutput(@Param("functionMethodOutputDelete") List<FunctionMethodOutput> functionMethodOutputDelete);

	int multiUpdateMethodInput(@Param("functionMethodInputUpdate") List<FunctionMethodInput> functionMethodInputUpdate);

	int multiUpdateMethodOutput(@Param("functionMethodOutputUpdate") List<FunctionMethodOutput> functionMethodOutputUpdate);

	List<FunctionMaster> findFunctionMasterByProjectId(@Param("projectId") Long projectId);

	List<FunctionMaster> findAllFunctionMasterByProjectId(@Param("projectId") Long projectId);

	List<FunctionMethodInput> findFunctionMethodInputByFunctionMethodId(@Param("functionMethodId") Long functionMethodId);

	List<FunctionMethodOutput> findFunctionMethodOutputByFunctionMethodId(@Param("functionMethodId") Long functionMethodId);

	List<FunctionMethod> findFuntionMethodByProjectId(@Param("projectId") Long projectId);

	List<FunctionMethodInput> findFunctionMethodInputByProjectId(@Param("projectId") Long projectId);

	List<FunctionMethodOutput> findFunctionMethodOutputByProjectId(@Param("projectId") Long projectId);

	List<DecisionTable> getDTableEffectedDeleteFMaster(@Param("functionMethods") List<FunctionMethod> functionMethods);

	List<FunctionMaster> findFunctionMasterDefault();

	List<FunctionMethod> findFuntionMethodDefault();

	List<FunctionMethodInput> findFunctionMethodInputDefault();

	List<FunctionMethodOutput> findFunctionMethodOutputDefault();

	List<AffectedBusinessDesign> getEffectedBDesignByInput(@Param("methodInputs") List<FunctionMethodInput> methodInputs);

	List<AffectedBusinessDesign> getEffectedBDesignByOutput(@Param("methodOutputs") List<FunctionMethodOutput> methodOutputs);

	List<AffectedBusinessDesign> getEffectedBDesignByAddedInput(@Param("functionMethods") List<Long> functionMethods);

	List<AffectedBusinessDesign> getEffectedBDesignByDeletedFunctionMethod(@Param("functionMethods") List<Long> functionMethods);

	List<AffectedDecisionTable> getEffectedDecisionTableByInput(@Param("methodInputs") List<FunctionMethodInput> methodInputs);

	List<AffectedDecisionTable> getEffectedDecisionTableByOutput(@Param("methodOutputs") List<FunctionMethodOutput> methodOutputs);

	List<AffectedDecisionTable> getEffectedDecisionTableByAddedInput(@Param("functionMethods") List<Long> functionMethods);

	List<AffectedDecisionTable> getEffectedDecisionTableByDeletedFunctionMethod(@Param("functionMethods") List<Long> functionMethods);

	FunctionMethod getFunctionMethodByFMId(@Param("functionMethodId") Long functionMethodId);

	List<BusinessDesign> getBDesignEffectedDeleteFMaster(@Param("functionMethods") List<FunctionMethod> functionMethods);

	Long getSequencesFunctionMethod(@Param("size") Integer size);

	void insertFunctionMethods(@Param("functionMethods") List<FunctionMethod> functionMethods);

	Long getSequencesFunctionMethodInput(@Param("size") Integer size);

	Long getSequencesFunctionMethodOutput(@Param("size") Integer size);

	List<FunctionMethod> findFuntionMethodNotCommonByProjectId(@Param("projectId") Long projectId);

	List<FunctionMethodInput> findFunctionMethodInputNotCommonByProjectId(@Param("projectId") Long projectId);

	List<FunctionMethodOutput> findFunctionMethodOutputNotCommonByProjectId(@Param("projectId") Long projectId);

	List<FunctionMaster> findFunctionMasterByUsingExternalObject(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);

	List<FunctionMaster> findFunctionMasterByUsingCommonObject(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);

	List<FunctionMaster> findFunctionMasterByMofidingExternalAttributes(@Param("lstExternalObjectAttributes") List<ExternalObjectAttribute> lstExternalObjectAttributes);

}
