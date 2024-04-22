package org.terasoluna.qp.domain.repository.screenaction;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.NavigatorComponent;
import org.terasoluna.qp.domain.model.ScreenAction;

@Repository
public interface ScreenActionRepository {

	Long insertScreenAction(ScreenAction screenAction);
	
	List<ScreenAction> findAllActionByScreenId(Long screenId);
	
	Long screenActionParamGetSequences(@Param("size") Integer size);
	
	Long insertScreenActionWithParam(@Param("screenAction") ScreenAction screenAction);
	
	ScreenAction findById(@Param("screenActionId") Long screenActionId, @Param("languageId") Long languageId);
	
	ScreenAction findByIdOfNavigateBlogic(@Param("screenActionId") Long screenActionId, @Param("languageId") Long languageId);
	
	List<ScreenAction> getScreenActionByLstToScreenId(@Param("lstToScreenId") List<Long> lstToScreenId);
	
	int updateBLoicNavigator(ScreenAction screenAction);
	
	NavigatorComponent getNavigateByActionId(@Param("screenActionId") Long screenActionId);

}
