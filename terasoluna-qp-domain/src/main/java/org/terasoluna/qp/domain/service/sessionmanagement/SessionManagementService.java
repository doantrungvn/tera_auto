package org.terasoluna.qp.domain.service.sessionmanagement;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.SessionManagement;
import org.terasoluna.qp.domain.repository.sessionmanagement.SessionManagementSearchCriteria;

@Service
public interface SessionManagementService {

	SessionManagement findSessionManagementById(Long sessionManagementId);

	public List<BusinessDesign> getBusinessDesign(Long sessionManagementId);

	Page<SessionManagement> findSessionManagementBySearchCriteria(
			SessionManagementSearchCriteria criteria, Pageable pageable,
			CommonModel common);

	SessionManagement registerSessionManagement(
			SessionManagement sessionManagement, CommonModel common);

	void modifySessionManagement(SessionManagement sessionManagement,
			CommonModel common);

	void deleteSessionManagement(SessionManagement sessionManagement,
			CommonModel common);
}
