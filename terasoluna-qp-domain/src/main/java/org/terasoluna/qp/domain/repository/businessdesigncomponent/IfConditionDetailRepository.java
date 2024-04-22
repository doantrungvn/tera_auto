package org.terasoluna.qp.domain.repository.businessdesigncomponent;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.IfConditionDetail;


public interface IfConditionDetailRepository {

	int registerIfConditionDetail(@Param("ifConditionItems") List<IfConditionDetail> ifConditionItems);

	List<IfConditionDetail> findIfConditionByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<IfConditionDetail> findIfConditionByModule(@Param("moduleId") Long moduleId);

	List<IfConditionDetail> findIfConditionByProject(@Param("projectId") Long projectId);

	List<IfConditionDetail> findIfConditionByModuleCommon(@Param("languageId") Long languageId, @Param("projectId") Long projectId);
}
