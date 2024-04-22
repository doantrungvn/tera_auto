package org.terasoluna.qp.domain.service.sessionmanagement;

import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.CommonModel;

@Service
public interface SessionManagementShareService {
	
	public void detectListAffectedWhenDeleteOfBatch(Long sessionManagementId,String sessionManagementCode,CommonModel common);
	
	public void detectListAffectedWhenModifyOfBatch(Long sessionManagementId,CommonModel common);
}
