package org.terasoluna.qp.domain.service.importmanagement;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.ImportManagement;

@Service
public interface ImportCodelistDesignService {
	Map<String, Object> importData(ImportManagement importManagement, Long projectId , Long accountId); 
}
