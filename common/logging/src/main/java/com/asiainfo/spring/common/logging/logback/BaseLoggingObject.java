package com.asiainfo.spring.common.logging.logback;

import com.rain.common.base.RainObject;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class BaseLoggingObject extends RainObject {
    protected final Marker marker = MarkerFactory.getMarker("SPRING_CLOUD");
    protected final Logger logger = new Logger(marker, this.getClass());

    public Logger getLogger() {
        return this.logger;
    }
}
