package com.asiainfo.spring.common.logging.logback;

import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

public class Logger {
    private org.slf4j.Logger logger;
    private Marker marker;

    public Logger(Class classz) {
        logger = LoggerFactory.getLogger(classz);
    }

    public Logger(String classz) {
        logger = LoggerFactory.getLogger(classz);
    }

    public Logger(Marker marker, Class classz) {
        logger = LoggerFactory.getLogger(classz);
        this.marker = marker;
    }

    public Logger(Marker marker, String classz) {
        logger = LoggerFactory.getLogger(classz);
        this.marker = marker;
    }
    public String getName(){
        return logger.getName();
    }
    public void trace(String msg) {
        logger.trace(marker, msg);
    }

    public void trace(String format, Object arg) {
        logger.trace(marker, format, arg);
    }

    public void trace(String format, Object arg1, Object arg2) {
        logger.trace(marker, format, arg1, arg2);
    }

    public void trace(String format, Object... arguments) {
        logger.trace(marker, format, arguments);
    }

    public void trace(String msg, Throwable t) {
        logger.trace(marker, msg, t);
    }

    public boolean isDebugEnabled(){
        return logger.isDebugEnabled(marker);
    }
    public void debug(String msg) {
        logger.debug(marker, msg);
    }

    public void debug(String format, Object arg) {
        logger.debug(marker, format, arg);
    }

    public void debug(String format, Object arg1, Object arg2) {
        logger.debug(marker, format, arg1, arg2);
    }

    public void debug(String format, Object... arguments) {
        logger.debug(marker, format, arguments);
    }

    public void debug(String msg, Throwable t) {
        logger.debug(marker, msg, t);
    }

    public void info(String msg) {
        logger.info(marker, msg);
    }

    public void info(String format, Object arg) {
        logger.info(marker, format, arg);
    }

    public void info(String format, Object arg1, Object arg2) {
        logger.info(marker, format, arg1, arg2);
    }

    public void info(String format, Object... arguments) {
        logger.info(marker, format, arguments);
    }

    public void info(String msg, Throwable t) {
        logger.info(marker, msg, t);
    }

    public void warn(String msg) {
        logger.warn(marker, msg);
    }

    public void warn(String format, Object arg) {
        logger.warn(marker, format, arg);
    }

    public void warn(String format, Object... arguments) {
        logger.warn(marker, format, arguments);
    }

    public void warn(String format, Object arg1, Object arg2) {
        logger.warn(marker, format, arg1, arg2);
    }

    public void warn(String msg, Throwable t) {
        logger.warn(marker, msg, t);
    }
    public boolean isErrorEnabled(){
        return logger.isErrorEnabled(marker);
    }
    public void error(String msg) {
        logger.error(marker, msg);
    }

    public void error(String format, Object arg) {
        logger.error(marker, format, arg);
    }

    public void error(String format, Object arg1, Object arg2) {
        logger.error(marker, format, arg1, arg2);
    }

    public void error(String format, Object... arguments) {
        logger.error(marker, format, arguments);
    }

    public void error(String msg, Throwable t) {
        logger.error(marker, msg, t);
    }

}
