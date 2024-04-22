package org.terasoluna.qp.domain.repository.businessdesigncomponent;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.NestedLogicComponent;


public interface NestedLogicComponentRepository {

	int registerNestedLogicComponent(@Param("nestedLogicComponentItems") List<NestedLogicComponent> nestedLogicComponentItems);

	List<NestedLogicComponent> findNestedLogicComponentByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<NestedLogicComponent> findNestedLogicComponentByModuleId(@Param("moduleId") Long moduleId);

	List<NestedLogicComponent> findNestedLogicComponentByModuleCommon(@Param("projectId") Long projectId);
}
