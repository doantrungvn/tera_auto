package org.terasoluna.qp.domain.repository.domaindesign;

import java.util.List;

import org.terasoluna.qp.domain.model.Resource;

public interface ResourceRepository {
	
	List<Resource> getAllResourceByCategoryCd(String categoryCd);
}
