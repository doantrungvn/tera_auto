package org.terasoluna.qp.domain.service.loggingmanagement;

import java.util.List;

import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.ConversionPattern;
import org.terasoluna.qp.domain.model.Log;
import org.terasoluna.qp.domain.model.LogDetail;

@Service
public interface LoggingManagementService{

	// Find all conversion pattern 
	List<ConversionPattern> findAllConversionPattern();
		
	// Find all conversion pattern by log detail id
	List<ConversionPattern> findAllConversionPatternByLogDetailId(Long logDetailId);
	
	// Find all log detail by log Id
	List<LogDetail> findAllLogDetailByLogId(Long logId);
	
	// Find Log by type and project id
	Log findLogByTypeAndProjectId(Long projectId, int logType);
	
	// Update log
	boolean modifyLog(Log log);
	
	// Register default log
	boolean registerDefaultLog(Log log);
	
	// Finds log by projectId, logtype, and status
	Log findLogByProjectIdAndTypeAndStatus(Long projectId, int logType, int status);
	
}
