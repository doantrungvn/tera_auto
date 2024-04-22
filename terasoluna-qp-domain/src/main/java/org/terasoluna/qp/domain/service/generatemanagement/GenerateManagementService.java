package org.terasoluna.qp.domain.service.generatemanagement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.GenerateHistory;

@Service
public interface GenerateManagementService {

	Page<GenerateHistory> findPageByCriteria(Pageable pageable, Long projectId);
	
	void registerGenerateHistory(GenerateHistory generateHistory);
	
	void reGenerateHistory(GenerateHistory generateHistory);
	
	boolean updateGenerateHistory(GenerateHistory generateHistory);
	
	String getLastFileName(GenerateHistory generateHistory);
	
	boolean updateDownloadFlag(GenerateHistory generateHistory);
}
