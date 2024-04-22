<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!-- Application Loggers -->
    <logger name="org.terasoluna.qp">
        <level value="debug" />
    </logger>

    <!-- TERASOLUNA -->
    <logger name="org.terasoluna.gfw">
        <level value="info" />
    </logger>
    <logger name="org.terasoluna.gfw.web.logging.TraceLoggingInterceptor">
        <level value="trace" />
    </logger>
    <logger name="org.terasoluna.gfw.common.exception.ExceptionLogger">
        <level value="info" />
    </logger>
     <!-- 3rdparty Loggers -->
    <logger name="org.springframework">
        <level value="warn" />
    </logger>

    <logger name="org.springframework.web.servlet">
        <level value="info" />
    </logger>

    <logger name="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <level value="debug" />
    </logger>

    <logger name="jdbc.sqltiming">
        <level value="debug" />
    </logger>
    
    <!-- only for development -->
    <logger name="jdbc.resultsettable">
        <level value="debug" />
    </logger>

</configuration>
