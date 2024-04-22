package org.terasoluna.qp.domain.repository.loggingmanagement;

import java.util.List;

import org.terasoluna.qp.domain.model.LogDetail;

public interface LogDetailRepository {
	// Get List Log Detail by LogID
	List<LogDetail> findLogDetailByLogId(Long logId);
	
	// Delete all log detail of a log
	int deleteAllLogDetailByLogId(Long logId);
	
	// Register log detail
	void registerLogDetail(LogDetail logDetail);
}
