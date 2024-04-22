package org.terasoluna.qp.domain.repository.businessdesigncomponent;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.AssignComponent;
import org.terasoluna.qp.domain.model.AssignDetail;


public interface AssignComponentRepository {

	Long getSequencesAssignComponent(@Param("size") Integer size);

	Long getSequencesAssignDetail(@Param("size") Integer size);

	int registerAssignComponent(@Param("assignComponentItems") List<AssignComponent> assignComponentItems);

	List<AssignComponent> findAssignComponentByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<AssignComponent> findAssignComponentByModule(@Param("moduleId") Long moduleId);

	int registerAssignDetails(@Param("assignDetailItems") List<AssignDetail> assignDetailItems);

	List<AssignDetail> findAssignDetailsByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<AssignDetail> findAssignDetailsByModule(@Param("moduleId") Long moduleId);

	List<AssignComponent> findAssignComponentByModuleCommon(@Param("projectId") Long projectId);

	List<AssignDetail> findAssignDetailsByModuleCommon(@Param("projectId") Long projectId);
}
