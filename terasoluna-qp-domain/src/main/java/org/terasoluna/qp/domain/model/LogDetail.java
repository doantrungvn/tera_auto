package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model for Log Detail
 * @author quynd1
 *
 */
@XmlRootElement(name = "logDetail")
public class LogDetail implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long logDetailId;
	private Long logId;
	private int logLevel;
	private String layout;
	private String logTarget;
	private int logFileType;
	private String patternFileName;
	private String filePath;
	private int immediateFlush;
	private int appendType;
	private String maxFileSize;
	private Integer maxBackupIndex;
	private String datePattern;
	private String conversionPattern;
	private String dbDriver;
	private String dbUrl;
	private String dbUserName;
	private String dbPassword;
	private String sqlString;
	private String charset;
	private String rollingPolicy;
	private String triggeringPolicy;
	private Integer maxHistory;
	private Integer totalSizeCap;
	private Integer minIndex;
	private Integer maxIndex;
	private Integer dbType;
	private Integer insertHeaders;
	private int configMode;
	private List<ConversionPattern> lstConversionPattern;
	@XmlElement(name = "conversionPatternObj", type = ConversionPattern.class)
	public List<ConversionPattern> getLstConversionPattern() {
		return lstConversionPattern;
	}
	public void setLstConversionPattern(List<ConversionPattern> lstConversionPattern) {
		this.lstConversionPattern = lstConversionPattern;
	}
	public Long getLogDetailId() {
		return logDetailId;
	}
	public void setLogDetailId(Long logDetailId) {
		this.logDetailId = logDetailId;
	}
	public Long getLogId() {
		return logId;
	}
	public void setLogId(Long logId) {
		this.logId = logId;
	}
	@XmlElement(name = "logLevel")
	public int getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(int logLevel) {
		this.logLevel = logLevel;
	}
	@XmlElement(name = "layout")
	public String getLayout() {
		return layout;
	}
	public void setLayout(String layout) {
		this.layout = layout;
	}
	@XmlElement(name = "logTarget")
	public String getLogTarget() {
		return logTarget;
	}
	public void setLogTarget(String logTarget) {
		this.logTarget = logTarget;
	}
	@XmlElement(name = "logFileType")
	public int getLogFileType() {
		return logFileType;
	}
	public void setLogFileType(int logFileType) {
		this.logFileType = logFileType;
	}
	@XmlElement(name = "patternFileName")
	public String getPatternFileName() {
		return patternFileName;
	}
	public void setPatternFileName(String patternFileName) {
		this.patternFileName = patternFileName;
	}
	@XmlElement(name = "filePath")
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	@XmlElement(name = "immediateFlush")
	public int getImmediateFlush() {
		return immediateFlush;
	}
	public void setImmediateFlush(int immediateFlush) {
		this.immediateFlush = immediateFlush;
	}
	@XmlElement(name = "appendType")
	public int getAppendType() {
		return appendType;
	}
	public void setAppendType(int appendType) {
		this.appendType = appendType;
	}
	@XmlElement(name = "maxFileSize")
	public String getMaxFileSize() {
		return maxFileSize;
	}
	public void setMaxFileSize(String maxFileSize) {
		this.maxFileSize = maxFileSize;
	}
	@XmlElement(name = "maxBackupIndex")
	public Integer getMaxBackupIndex() {
		return maxBackupIndex;
	}
	public void setMaxBackupIndex(Integer maxBackupIndex) {
		this.maxBackupIndex = maxBackupIndex;
	}
	@XmlElement(name = "datePattern")
	public String getDatePattern() {
		return datePattern;
	}
	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}
	@XmlElement(name = "conversionPatterStr")
	public String getConversionPattern() {
		return conversionPattern;
	}
	public void setConversionPattern(String conversionPattern) {
		this.conversionPattern = conversionPattern;
	}
	public String getDbDriver() {
		return dbDriver;
	}
	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}
	public String getDbUrl() {
		return dbUrl;
	}
	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}
	public String getDbUserName() {
		return dbUserName;
	}
	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}
	public String getDbPassword() {
		return dbPassword;
	}
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	public String getSqlString() {
		return sqlString;
	}
	public void setSqlString(String sqlString) {
		this.sqlString = sqlString;
	}
	@XmlElement(name = "charset")
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getRollingPolicy() {
		return rollingPolicy;
	}
	public void setRollingPolicy(String rollingPolicy) {
		this.rollingPolicy = rollingPolicy;
	}
	public String getTriggeringPolicy() {
		return triggeringPolicy;
	}
	public void setTriggeringPolicy(String triggeringPolicy) {
		this.triggeringPolicy = triggeringPolicy;
	}
	public Integer getMaxHistory() {
		return maxHistory;
	}
	public void setMaxHistory(Integer maxHistory) {
		this.maxHistory = maxHistory;
	}
	public Integer getTotalSizeCap() {
		return totalSizeCap;
	}
	public void setTotalSizeCap(Integer totalSizeCap) {
		this.totalSizeCap = totalSizeCap;
	}
	public Integer getMinIndex() {
		return minIndex;
	}
	public void setMinIndex(Integer minIndex) {
		this.minIndex = minIndex;
	}
	public Integer getMaxIndex() {
		return maxIndex;
	}
	public void setMaxIndex(Integer maxIndex) {
		this.maxIndex = maxIndex;
	}
	public Integer getDbType() {
		return dbType;
	}
	public void setDbType(Integer dbType) {
		this.dbType = dbType;
	}
	public Integer getInsertHeaders() {
		return insertHeaders;
	}
	public void setInsertHeaders(Integer insertHeaders) {
		this.insertHeaders = insertHeaders;
	}
	@XmlElement(name = "configMode")
	public int getConfigMode() {
		return configMode;
	}
	public void setConfigMode(int configMode) {
		this.configMode = configMode;
	}
	
}
