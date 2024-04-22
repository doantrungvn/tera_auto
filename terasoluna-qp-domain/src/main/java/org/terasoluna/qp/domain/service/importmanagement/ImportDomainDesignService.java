package org.terasoluna.qp.domain.service.importmanagement;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.ImportManagement;

@Service
public interface ImportDomainDesignService {
	//Map<String, Object> getInputData(String filePath);
	//Map<String, Object> importBusinessDetail(Map<String, Object> mapData);
	
	Map<String, Object> importData(ImportManagement importManagement, Long projectId , Long accountId); 
}
