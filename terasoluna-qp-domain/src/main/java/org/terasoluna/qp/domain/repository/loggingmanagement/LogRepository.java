package org.terasoluna.qp.domain.repository.loggingmanagement;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.Log;

/**
 * Logging Management Repository Interface
 * @author quynd1
 *
 */
public interface LogRepository {

	// Get Log by projectId and log Type
	Log findLogByTypeAndProjectId(@Param("projectId")Long projectId, @Param("logType")int logType);
	
	// Register new log
	void registerLog(Log log);
	
	// Update log
	int modifyLog(Log log);
	
	// Get log by projectId, logtype, and status
	Log findLogByProjectIdAndTypeAndStatus(@Param("projectId")Long projectId, @Param("logType")int logType, @Param("status")int status);
}
