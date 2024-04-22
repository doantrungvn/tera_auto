package org.terasoluna.qp.domain.repository.functiondesign;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.BusinessType;
import org.terasoluna.qp.domain.model.FunctionDesign;

@Repository
public interface FunctionDesignRepository {
	
	long countBySearchCriteria(@Param("criteria") FunctionDesignCriteria criteria);

	List<FunctionDesign> findPageBySearchCriteria(@Param("criteria") FunctionDesignCriteria criteria, @Param("pageable") Pageable pageable);
	
	List<FunctionDesign> findAllFunctionDesignByModuleId(@Param("moduleId") Long moduleId);
	
	List<FunctionDesign> findAllFunctionDesignByProjectId(@Param("projectId") Long projectId);
	
	List<BusinessDesign> findAllBusinessDesignByProjectId(@Param("projectId") Long projectId);
	
	List<BusinessType> findAllBusinessTypeByProjectId(@Param("projectId") Long projectId);
	
	void register(FunctionDesign functionDesign);
	
	Long countNameCodeByFunctionId(FunctionDesign functionDesign);
	
	FunctionDesign findFunctionDesignById(Long functionId);
	
	int modify(FunctionDesign functionDesign);
	
	int delete(FunctionDesign functionDesign);
	
	Long getListUsingFD(Long functionDesignId);
	
	Long getSequencesFunctionDesign(@Param("size") Integer size);
	
	int registerLstFunctionDesign(@Param("lstFunctionDesign") List<FunctionDesign> lstFunctionDesign);
}
