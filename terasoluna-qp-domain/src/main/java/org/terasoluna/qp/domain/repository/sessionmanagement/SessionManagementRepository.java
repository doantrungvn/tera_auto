package org.terasoluna.qp.domain.repository.sessionmanagement;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.SessionManagement;

public interface SessionManagementRepository {

	long countBySearchCriteria(@Param("criteria") SessionManagementSearchCriteria criteria);

	List<SessionManagement> findPageBySearchCriteria(@Param("criteria") SessionManagementSearchCriteria criteria, @Param("pageable") Pageable pageable);
	
	SessionManagement findById(@Param("sessionManagementId")Long sessionManagementId);

	void register(SessionManagement sessionManagement);
	
	boolean modify(SessionManagement sessionManagement);

	int delete(Long sessionManagementId);
    
    Long countNameCodeById(SessionManagement sessionManagement);
    
    void registerListOfSessionManagement(@Param("sessionManagements") List<SessionManagement> listSessionManagement);
	
	List<SessionManagement> findAllOfProject(@Param("projectId")Long projectId);
	
	List<SessionManagement> findSesionManagementByUsingCommonObject(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
}
