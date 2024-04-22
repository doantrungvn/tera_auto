package org.terasoluna.qp.domain.repository.functionoutput;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.FunctionMethodOutput;

@Repository
public interface FunctionOutputRepository extends Serializable {
	int register(@Param("lstItems") List<FunctionMethodOutput> lstItems);
	
	List<FunctionMethodOutput> getFunctionOutput(Long functionMasterId);
	
	int modify(FunctionMethodOutput functionMethodOutput);
		
	int delete(@Param("functionOutputItems") List<FunctionMethodOutput> functionOutputItems);
}
