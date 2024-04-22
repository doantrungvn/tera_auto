package org.terasoluna.qp.domain.repository.functioninput;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.FunctionMethodInput;

@Repository
public interface FunctionInputRepository extends Serializable {
	int register(@Param("lstItems") List<FunctionMethodInput> lstItems);
	
	List<FunctionMethodInput> getFunctionInput(Long functionMasterId);
	
	int modify(FunctionMethodInput functionInput);
	
	int delete(@Param("functionInputItems") List<FunctionMethodInput> functionInputItems);
	
	List<FunctionMethodInput> findFunctionInputByFormula(@Param("projectId") Long projectId);
	
}
