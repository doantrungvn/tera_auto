package org.terasoluna.qp.app.loggingmanagement;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.LogManagementConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.Log;
import org.terasoluna.qp.domain.model.LogDetail;
import org.terasoluna.qp.domain.service.common.SystemService;

@Component
public class LoggingManagementValidator implements Validator {

	private static final String XML_LAYOUT = "1";
	private static final int MAX_LENGTH = 400;
	//private static final String ROLLING_FILE_APPENDER = "2";
	
	@Inject 
	SystemService systemService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return (LoggingManagementForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LoggingManagementForm loggingForm = (LoggingManagementForm)target;

		// Validate for console log 
		if(loggingForm.getConsoleLogStatus()){
			validateLog(loggingForm.getConsoleLog(), errors);
		}
		
		// Validate for file log
		if(loggingForm.getFileLogStatus()){
			validateLog(loggingForm.getFileLog(), errors);
		}
		
		// Validate for database log
		if(loggingForm.getDatabaseLogStatus()){
			validateLog(loggingForm.getDatabaseLog(), errors);
		}
	}

	private void validateLog(Log log, Errors errors){
		AccountProfile accountProfile = systemService.getDefaultProfile();
		String logTypeString = (log.getLogType() == DbDomainConst.LogManagementType.LOG_TYPE_CONSOLE)? LogManagementConst.SC_LOGGINGMANAGEMENT_0003:((log.getLogType() == DbDomainConst.LogManagementType.LOG_TYPE_FILE)?LogManagementConst.SC_LOGGINGMANAGEMENT_0004:LogManagementConst.SC_LOGGINGMANAGEMENT_0005);
		int index = 1;
		if(log.getLstLogDetail() != null && log.getLstLogDetail().size() > 0){
			for(LogDetail logDetail : log.getLstLogDetail()){
				// Validate log level
				String logLvlErr = MessageUtils.getMessage(LogManagementConst.ERR_LOGGINGMANAGEMENT_0036, MessageUtils.getMessage(logTypeString), index,MessageUtils.getMessage(LogManagementConst.SC_LOGGINGMANAGEMENT_0007) );
				ValidationUtils.validateRequired(logDetail.getLogLevel()+"", errors, logLvlErr);
				ValidationUtils.validateInteger(logDetail.getLogLevel(), errors, logLvlErr);
				
				// Validate appender
//				String appenderErr = MessageUtils.getMessage(LogManagementConst.ERR_LOGGINGMANAGEMENT_0036, MessageUtils.getMessage(logTypeString), index,MessageUtils.getMessage(LogManagementConst.SC_LOGGINGMANAGEMENT_0008) );
//				ValidationUtils.validateRequired(logDetail.getAppender(), errors,  appenderErr);
//				ValidationUtils.validateMaxLength(logDetail.getAppender(), errors,accountProfile.getNameMaxLength(),  appenderErr);
//				ValidationUtils.validateMask(logDetail.getAppender(), errors, accountProfile.getNamePattern(),  appenderErr);
				
				// Validate pattern layout
				String patternLayoutErr = MessageUtils.getMessage(LogManagementConst.ERR_LOGGINGMANAGEMENT_0036, MessageUtils.getMessage(logTypeString), index,MessageUtils.getMessage(LogManagementConst.SC_LOGGINGMANAGEMENT_0010) );
				ValidationUtils.validateRequired(logDetail.getLayout()+"", errors, patternLayoutErr);
				ValidationUtils.validateInteger(logDetail.getLayout(), errors, patternLayoutErr);
				
				// Case: console log
				if(log.getLogType() == DbDomainConst.LogManagementType.LOG_TYPE_CONSOLE){
					// Validate log target
					String logTargetErr = MessageUtils.getMessage(LogManagementConst.ERR_LOGGINGMANAGEMENT_0036, MessageUtils.getMessage(logTypeString), index,MessageUtils.getMessage(LogManagementConst.SC_LOGGINGMANAGEMENT_0009) );
					ValidationUtils.validateRequired(logDetail.getLogTarget(), errors,logTargetErr);
					//ValidationUtils.validateMaxLength(logDetail.getLogTarget(), errors,accountProfile.getNameMaxLength(), logTargetErr);
					ValidationUtils.validateMaskName(logDetail.getLogTarget(), errors, accountProfile.getNamePattern(), logTargetErr);
					
					// Validate Conversion pattern
					String logConversionPattern= MessageUtils.getMessage(LogManagementConst.ERR_LOGGINGMANAGEMENT_0036, MessageUtils.getMessage(logTypeString), index,MessageUtils.getMessage(LogManagementConst.SC_LOGGINGMANAGEMENT_0011) );
					ValidationUtils.validateRequired(logDetail.getConversionPattern(), errors,logConversionPattern);
					ValidationUtils.validateMaxLength(logDetail.getConversionPattern(), errors,MAX_LENGTH, logConversionPattern);
				}
				
				// Case: file log
				if(log.getLogType() == DbDomainConst.LogManagementType.LOG_TYPE_FILE){
					// Validate log file type
					String logFileTypeErr = MessageUtils.getMessage(LogManagementConst.ERR_LOGGINGMANAGEMENT_0036, MessageUtils.getMessage(logTypeString), index,MessageUtils.getMessage(LogManagementConst.SC_LOGGINGMANAGEMENT_0015) );
					ValidationUtils.validateRequired(logDetail.getLogFileType()+"", errors, logFileTypeErr);
					ValidationUtils.validateInteger(logDetail.getLogFileType(), errors, logFileTypeErr);
					
					// Validate file path
					String filePathErr = MessageUtils.getMessage(LogManagementConst.ERR_LOGGINGMANAGEMENT_0036, MessageUtils.getMessage(logTypeString), index,MessageUtils.getMessage(LogManagementConst.SC_LOGGINGMANAGEMENT_0017) );
					ValidationUtils.validateRequired(logDetail.getFilePath(), errors, filePathErr);
					ValidationUtils.validateMaxLength(logDetail.getFilePath(), errors,MAX_LENGTH, filePathErr);
					
					// Validate charset encoding of file
					String fileCharsetErr = MessageUtils.getMessage(LogManagementConst.ERR_LOGGINGMANAGEMENT_0036, MessageUtils.getMessage(logTypeString), index,MessageUtils.getMessage(LogManagementConst.SC_LOGGINGMANAGEMENT_0042) );
					ValidationUtils.validateRequired(logDetail.getCharset(), errors, fileCharsetErr);
					ValidationUtils.validateMaxLength(logDetail.getCharset(), errors,MAX_LENGTH, fileCharsetErr);
					
					if(logDetail.getLogFileType() == DbDomainConst.LogFileType.LOG_FILE_ROLLING){
						// Validate RollingPolicy
						String rollingPolicyErr = MessageUtils.getMessage(LogManagementConst.ERR_LOGGINGMANAGEMENT_0036, MessageUtils.getMessage(logTypeString), index,MessageUtils.getMessage(LogManagementConst.SC_LOGGINGMANAGEMENT_0043) );
						ValidationUtils.validateRequired(logDetail.getRollingPolicy()+"", errors, rollingPolicyErr);
						
						// Validate pattern file name
						String patternFileNameErr = MessageUtils.getMessage(LogManagementConst.ERR_LOGGINGMANAGEMENT_0036, MessageUtils.getMessage(logTypeString), index,MessageUtils.getMessage(LogManagementConst.SC_LOGGINGMANAGEMENT_0016) );
						ValidationUtils.validateRequired(logDetail.getPatternFileName(), errors, patternFileNameErr);
						ValidationUtils.validateMaxLength(logDetail.getPatternFileName(), errors,MAX_LENGTH, patternFileNameErr);
						ValidationUtils.validateMaskName(logDetail.getPatternFileName(), errors, accountProfile.getNamePattern(), patternFileNameErr);
						
						if (DbDomainConst.RollingPolicy.FIXED_WINDOW_ROLLINGPOLICY.equals(logDetail.getRollingPolicy())){
							// Validate Min Index
							String minIndexErr =  MessageUtils.getMessage(LogManagementConst.ERR_LOGGINGMANAGEMENT_0036, MessageUtils.getMessage(logTypeString), index,MessageUtils.getMessage(LogManagementConst.SC_LOGGINGMANAGEMENT_0047) );
							ValidationUtils.validateRequired(logDetail.getMinIndex() == null ? "" : logDetail.getMinIndex()+"", errors, minIndexErr);
							ValidationUtils.validateInteger(logDetail.getMinIndex(), errors, minIndexErr);
							
							// Validate Max Index
							String maxIndexErr = MessageUtils.getMessage(LogManagementConst.ERR_LOGGINGMANAGEMENT_0036, MessageUtils.getMessage(logTypeString), index,MessageUtils.getMessage(LogManagementConst.SC_LOGGINGMANAGEMENT_0048) );
							ValidationUtils.validateRequired(logDetail.getMaxIndex() == null ? "" : logDetail.getMaxIndex() + "", errors, maxIndexErr);
							ValidationUtils.validateInteger(logDetail.getMaxIndex(), errors, maxIndexErr);
						} else if (DbDomainConst.RollingPolicy.SIZE_AND_TIME_BASED_ROLLINGPOLICY.equals(logDetail.getRollingPolicy()) 
								|| DbDomainConst.RollingPolicy.TIME_BASED_ROLLINGPOLICY.equals(logDetail.getRollingPolicy())){
							// Validate Max History
							String maxHistoryErr =  MessageUtils.getMessage(LogManagementConst.ERR_LOGGINGMANAGEMENT_0036, MessageUtils.getMessage(logTypeString), index,MessageUtils.getMessage(LogManagementConst.SC_LOGGINGMANAGEMENT_0045) );
							ValidationUtils.validateRequired(logDetail.getMaxHistory() == null ? "" : logDetail.getMaxHistory()+"", errors, maxHistoryErr);
							ValidationUtils.validateInteger(logDetail.getMaxHistory(), errors, maxHistoryErr);
							
							//Current logback version not support
							// Validate Total Size Cap
//							String totalSizeCapErr = MessageUtils.getMessage(LogManagementConst.ERR_LOGGINGMANAGEMENT_0036, MessageUtils.getMessage(logTypeString), index,MessageUtils.getMessage(LogManagementConst.SC_LOGGINGMANAGEMENT_0046) );
//							ValidationUtils.validateRequired(logDetail.getTotalSizeCap() == null ? "" : logDetail.getTotalSizeCap()+"", errors, totalSizeCapErr);
//							ValidationUtils.validateInteger(logDetail.getTotalSizeCap(), errors, totalSizeCapErr);
						}
						
						if (DbDomainConst.TriggeringPolicy.SIZE_BASED_TRIGGERINGPOLICY.equals(logDetail.getTriggeringPolicy())){
							// Validate Max file size
							String maxFileSizeErr = MessageUtils.getMessage(LogManagementConst.ERR_LOGGINGMANAGEMENT_0036, MessageUtils.getMessage(logTypeString), index,MessageUtils.getMessage(LogManagementConst.SC_LOGGINGMANAGEMENT_0024) );
							ValidationUtils.validateRequired(logDetail.getMaxFileSize()+"", errors, maxFileSizeErr);
							ValidationUtils.validateMaxLength(logDetail.getMaxFileSize(), errors,100, maxFileSizeErr);
						}
						
					}
					if (!XML_LAYOUT.equals(logDetail.getLayout())){
						// Validate Conversion pattern for file log
						String logConversionPatternFileLog= MessageUtils.getMessage(LogManagementConst.ERR_LOGGINGMANAGEMENT_0036, MessageUtils.getMessage(logTypeString), index,MessageUtils.getMessage(LogManagementConst.SC_LOGGINGMANAGEMENT_0011) );
						ValidationUtils.validateRequired(logDetail.getConversionPattern(), errors,logConversionPatternFileLog);
						ValidationUtils.validateMaxLength(logDetail.getConversionPattern(), errors,MAX_LENGTH, logConversionPatternFileLog);
					}
				}
				
				// Case: database log
				if(log.getLogType() == DbDomainConst.LogManagementType.LOG_TYPE_DB){
					// Validate db driver
					String dbDriverErr = MessageUtils.getMessage(LogManagementConst.ERR_LOGGINGMANAGEMENT_0036, MessageUtils.getMessage(logTypeString), index,MessageUtils.getMessage(LogManagementConst.SC_LOGGINGMANAGEMENT_0029) );
					ValidationUtils.validateRequired(logDetail.getDbDriver(), errors, dbDriverErr);
					ValidationUtils.validateMaxLength(logDetail.getDbDriver(), errors,MAX_LENGTH, dbDriverErr);
					if (StringUtils.isNotBlank(logDetail.getDbDriver())){
						ValidationUtils.validatePackageName(logDetail.getDbDriver(), errors, LogManagementConst.SC_LOGGINGMANAGEMENT_0029,CommonMessageConst.ERR_SYS_0025,LogManagementConst.SC_LOGGINGMANAGEMENT_0029,CommonMessageConst.ERR_SYS_0018);
					}
					
					// Validate url connection
					String urlConnectionErr = MessageUtils.getMessage(LogManagementConst.ERR_LOGGINGMANAGEMENT_0036, MessageUtils.getMessage(logTypeString), index,MessageUtils.getMessage(LogManagementConst.SC_LOGGINGMANAGEMENT_0030) );
					ValidationUtils.validateRequired(logDetail.getDbUrl(), errors, urlConnectionErr);
					ValidationUtils.validateMaxLength(logDetail.getDbUrl(), errors,MAX_LENGTH, urlConnectionErr);
					
					// Validate user name
					String userNameErr = MessageUtils.getMessage(LogManagementConst.ERR_LOGGINGMANAGEMENT_0036, MessageUtils.getMessage(logTypeString), index,MessageUtils.getMessage(LogManagementConst.SC_LOGGINGMANAGEMENT_0031) );
					ValidationUtils.validateRequired(logDetail.getDbUserName(), errors, userNameErr);
					ValidationUtils.validateMaxLength(logDetail.getDbUserName(), errors,MAX_LENGTH, userNameErr);
					ValidationUtils.validateMaskName(logDetail.getDbUserName(), errors, accountProfile.getNamePattern(), userNameErr);
				
					// Validate password
					String passwordErr = MessageUtils.getMessage(LogManagementConst.ERR_LOGGINGMANAGEMENT_0036, MessageUtils.getMessage(logTypeString), index,MessageUtils.getMessage(LogManagementConst.SC_LOGGINGMANAGEMENT_0032) );
					ValidationUtils.validateRequired(logDetail.getDbPassword(), errors, passwordErr);
					ValidationUtils.validateMaxLength(logDetail.getDbPassword(), errors,MAX_LENGTH, passwordErr);
					
					// Validate Database type
					String dbTypeErr = MessageUtils.getMessage(LogManagementConst.ERR_LOGGINGMANAGEMENT_0036, MessageUtils.getMessage(logTypeString), index,MessageUtils.getMessage(LogManagementConst.SC_LOGGINGMANAGEMENT_0049) );
					ValidationUtils.validateRequired(logDetail.getDbType() == null ? "" : logDetail.getDbType()+"", errors, dbTypeErr);
					
					// Validate sql string
//					if (PATTERN_LAYOUT.equals(logDetail.getLayout())){
//						String sqlStringErr = MessageUtils.getMessage(LogManagementConst.ERR_LOGGINGMANAGEMENT_0036, MessageUtils.getMessage(logTypeString), index,MessageUtils.getMessage(LogManagementConst.SC_LOGGINGMANAGEMENT_0033) );
//						ValidationUtils.validateRequired(logDetail.getSqlString(), errors, sqlStringErr);
//					}
				}
				index++;	
			}
		}
		
		
	}
}
