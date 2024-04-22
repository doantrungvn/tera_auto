package org.terasoluna.qp.domain.service.loggingmanagement;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.ConversionPattern;
import org.terasoluna.qp.domain.model.Log;
import org.terasoluna.qp.domain.model.LogDetail;
import org.terasoluna.qp.domain.repository.loggingmanagement.LogDetailRepository;
import org.terasoluna.qp.domain.repository.loggingmanagement.LogPatternDetailRepository;
import org.terasoluna.qp.domain.repository.loggingmanagement.LogRepository;
@Service
@Transactional
public class LoggingManagementServiceImpl implements LoggingManagementService {

	@Inject 
	LogRepository logRepository;
	
	@Inject 
	LogDetailRepository logDetailRepository;
	
	@Inject
	LogPatternDetailRepository logPatternDetailRepository;

	@Override
	public List<ConversionPattern> findAllConversionPatternByLogDetailId(Long logDetailId) {
		List<ConversionPattern> lstPattern = logPatternDetailRepository.findAllConversionPatternByLogDetailId(logDetailId);
		return lstPattern;
	}

	@Override
	public List<LogDetail> findAllLogDetailByLogId(Long logId) {
		List<LogDetail> lstLogDetail = logDetailRepository.findLogDetailByLogId(logId);
		return lstLogDetail;
	}

	@Override
	public Log findLogByTypeAndProjectId(Long projectId, int logType) {
		Log log = logRepository.findLogByTypeAndProjectId(projectId, logType);
		if(log != null){
			log.setLstLogDetail(findAllLogDetailByLogId(log.getLogId()));
			if(log.getLstLogDetail() != null && log.getLstLogDetail().size() > 0){
				for(LogDetail item : log.getLstLogDetail()){
					item.setLstConversionPattern(findAllConversionPatternByLogDetailId(item.getLogDetailId()));
				}
			}
		}
		return log;
	}

	@Override
	public boolean modifyLog(Log log) {
		// Set update information for both case new/modify
		//log.setUpdatedBy(this.accountId);
		log.setSystemTime(FunctionCommon.getCurrentTime());
		
		// Check if log is existed or not
		Log oldLog = findLogByTypeAndProjectId(log.getProjectId(), log.getLogType());
		if(oldLog != null){
			// Update log
			if(logRepository.modifyLog(log) <= 0){
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
			}
			
			// Delete old conversion patterns
			if(oldLog.getLstLogDetail() != null && oldLog.getLstLogDetail().size() > 0){
				for(LogDetail logDetail : oldLog.getLstLogDetail()){
					if(logDetail.getLogDetailId() != null){
						logPatternDetailRepository.deleteAllConversionPatternByLogDetailId(logDetail.getLogDetailId());
					}
				}
			}
			
			// Delete all old log details of current log
			logDetailRepository.deleteAllLogDetailByLogId(log.getLogId());
		}else{
			// Set register information 
			//log.setCreatedBy(this.accountId);
			log.setCreatedDate(FunctionCommon.getCurrentTime());
			log.setUpdatedDate(FunctionCommon.getCurrentTime());
			
			logRepository.registerLog(log);
		}
		
		// Insert new log details
		if(log.getLstLogDetail() != null && log.getLstLogDetail().size() > 0){
			for(LogDetail logDetail : log.getLstLogDetail()){
				logDetail.setLogId(log.getLogId());
				logDetailRepository.registerLogDetail(logDetail);
				if(logDetail.getLstConversionPattern() != null && logDetail.getLstConversionPattern().size() > 0){
					// Insert new pattern details
					for(ConversionPattern conversionPattern : logDetail.getLstConversionPattern()){
						conversionPattern.setLogDetailId(logDetail.getLogDetailId());
					}
					logPatternDetailRepository.registerConversionPatternDetail(logDetail.getLstConversionPattern());

				}
			}
		}
		return true;
	}

	@Override
	public List<ConversionPattern> findAllConversionPattern() {
		List<ConversionPattern> lstPattern = logPatternDetailRepository.findAllConversionPattern();
		return lstPattern;
	}

	@Override
	public boolean registerDefaultLog(Log log) {
		// Insert new log
		logRepository.registerLog(log);
		
		// Insert new log details
		if(log.getLstLogDetail() != null && log.getLstLogDetail().size() > 0){
			for(LogDetail logDetail : log.getLstLogDetail()){
				logDetail.setLogId(log.getLogId());
				logDetailRepository.registerLogDetail(logDetail);
				if(logDetail.getLstConversionPattern() != null && logDetail.getLstConversionPattern().size() > 0){
					// Insert new pattern details
					for(ConversionPattern conversionPattern : logDetail.getLstConversionPattern()){
						conversionPattern.setLogDetailId(logDetail.getLogDetailId());
					}
					logPatternDetailRepository.registerConversionPatternDetail(logDetail.getLstConversionPattern());
				}
			}
		}
		
		return true;
	}

	@Override
	public Log findLogByProjectIdAndTypeAndStatus(Long projectId, int logType, int status) {
		return logRepository.findLogByProjectIdAndTypeAndStatus(projectId, logType, status);
	}
	

}
