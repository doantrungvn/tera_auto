package org.terasoluna.qp.domain.repository.screenactionparam;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.ScreenActionParam;
import org.terasoluna.qp.domain.model.ScreenItem;

@Repository
public interface ScreenActionParamRepository {

	Long insertScreenActionParam(ScreenActionParam screenActionParam);
	
	Integer countScreenActionParamByScreenActionId(Long screenActionId);
	
	List<ScreenActionParam> findAllActionParamByScreenActionId(@Param("screenActionId") Long screenActionId,@Param("projectId") Long projectId);
	
	List<ScreenActionParam> findAllActionParamByBusinessLogicId(Long businessLogicId);
	
	boolean deleteScreenActionParamByAutofixProblem(@Param("inputBeanId") Long inputBeanId, @Param("screenId") Long screenId);
	
	Long getSequencesScreenActionParam(@Param("size") Integer size);
	
	int registerListScreenActionParam(@Param("lstScreenActionParam") List<ScreenActionParam> lstScreenActionParam);
	
	int updateScreenActionCode(ScreenActionParam screenActionParam);
	
	List<ScreenActionParam> findAllActionParamByLstScreenItem(@Param("lstScreenItems") List<ScreenItem> lstScreenItems);
	
	boolean deleteScreenActionParamByNavigatorBlogicId(@Param("businessLogicId") Long businessLogicId);
	
	boolean deleteScreenActionParamByLstScreenItems(@Param("lstScreenItems") List<ScreenItem> lstScreenItems);
	
	List<ScreenActionParam> getOutputByItemId(@Param("lstScreenActionParam") List<ScreenActionParam> lstScreenActionParam);
	
}
