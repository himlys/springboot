package com.rain.common.datasource.config;

import com.alibaba.druid.filter.logging.LogFilter;
import com.asiainfo.spring.common.logging.logback.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
public class LocalDruidLogFilter extends LogFilter {
    private String url;
    protected final Marker marker = MarkerFactory.getMarker("SPRING_CLOUD");
    private Logger dataSourceLogger = new Logger(marker, dataSourceLoggerName);
    private Logger connectionLogger = new Logger(marker, connectionLoggerName);
    private Logger statementLogger = new Logger(marker, statementLoggerName);
    private Logger resultSetLogger = new Logger(marker, resultSetLoggerName);

    public void setUrl(String url) {
        this.url = url;
    }

    private Logger getLocalLogger(Logger logger) {
        Logger l = null;
        Thread.currentThread().getThreadGroup();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null && ClassUtils.isPresent("javax.servlet.http.HttpServletRequest",this.getClass().getClassLoader())) {
            Object a = attributes.getAttribute("current_logger",0);
            l = a != null ? (Logger) a : null;
        }

        return l != null ? l : logger;
    }

    @Override
    public String getDataSourceLoggerName() {
        return dataSourceLoggerName;
    }

    @Override
    public void setDataSourceLoggerName(String dataSourceLoggerName) {
        this.dataSourceLoggerName = dataSourceLoggerName;
        dataSourceLogger = new Logger(dataSourceLoggerName);
    }

    public void setDataSourceLogger(Logger dataSourceLogger) {
        this.dataSourceLogger = dataSourceLogger;
        this.dataSourceLoggerName = dataSourceLogger.getName();
    }

    @Override
    public String getConnectionLoggerName() {
        return connectionLoggerName;
    }

    @Override
    public void setConnectionLoggerName(String connectionLoggerName) {
        this.connectionLoggerName = connectionLoggerName;
        connectionLogger = new Logger(connectionLoggerName);
    }

    public void setConnectionLogger(Logger connectionLogger) {
        this.connectionLogger = connectionLogger;
        this.connectionLoggerName = connectionLogger.getName();
    }

    @Override
    public String getStatementLoggerName() {
        return statementLoggerName;
    }

    @Override
    public void setStatementLoggerName(String statementLoggerName) {
        this.statementLoggerName = statementLoggerName;
        statementLogger = new Logger(statementLoggerName);
    }

    public void setStatementLogger(Logger statementLogger) {
        this.statementLogger = statementLogger;
        this.statementLoggerName = statementLogger.getName();
    }

    @Override
    public String getResultSetLoggerName() {
        return resultSetLoggerName;
    }

    @Override
    public void setResultSetLoggerName(String resultSetLoggerName) {
        this.resultSetLoggerName = resultSetLoggerName;
        resultSetLogger = new Logger(resultSetLoggerName);
    }

    public void setResultSetLogger(Logger resultSetLogger) {
        this.resultSetLogger = resultSetLogger;
        this.resultSetLoggerName = resultSetLogger.getName();
    }

    @Override
    public boolean isConnectionLogErrorEnabled() {
        return getLocalLogger(connectionLogger).isErrorEnabled() && super.isConnectionLogErrorEnabled();
    }

    @Override
    public boolean isDataSourceLogEnabled() {
        return getLocalLogger(dataSourceLogger).isDebugEnabled() && super.isDataSourceLogEnabled();
    }

    @Override
    public boolean isConnectionLogEnabled() {
        return getLocalLogger(connectionLogger).isDebugEnabled() && super.isConnectionLogEnabled();
    }

    @Override
    public boolean isStatementLogEnabled() {
        return getLocalLogger(statementLogger).isDebugEnabled() && super.isStatementLogEnabled();
    }

    @Override
    public boolean isResultSetLogEnabled() {
        return getLocalLogger(resultSetLogger).isDebugEnabled() && super.isResultSetLogEnabled();
    }

    @Override
    public boolean isResultSetLogErrorEnabled() {
        return getLocalLogger(resultSetLogger).isErrorEnabled() && super.isResultSetLogErrorEnabled();
    }

    @Override
    public boolean isStatementLogErrorEnabled() {
        return getLocalLogger(statementLogger).isErrorEnabled() && super.isStatementLogErrorEnabled();
    }

    private String addURL(String message) {
        return url != null ? url + "\t" + message : message;
    }

    @Override
    protected void connectionLog(String message) {
        message = addURL(message);
        getLocalLogger(connectionLogger).debug(message);
    }

    @Override
    protected void statementLog(String message) {
        message = addURL(message);
        getLocalLogger(statementLogger).debug(message);
    }

    @Override
    protected void resultSetLog(String message) {
        message = addURL(message);
        getLocalLogger(resultSetLogger).debug(message);
    }

    @Override
    protected void resultSetLogError(String message, Throwable error) {
        message = addURL(message);
        getLocalLogger(resultSetLogger).error(message, error);
    }

    @Override
    protected void statementLogError(String message, Throwable error) {
        message = addURL(message);
        getLocalLogger(statementLogger).error(message, error);
    }
}
