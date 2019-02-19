package com.asiainfo.spring.common.logging.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.turbo.MatchingFilter;
import ch.qos.logback.core.spi.FilterReply;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class LogBackMarkerFilter extends MatchingFilter {

    Marker markerToMatch;

    @Override
    public void start() {
        if (markerToMatch != null) {
            super.start();
        } else {
            addError("The marker property must be set for [" + getName() + "]");
        }
    }

    @Override
    public FilterReply decide(Marker marker, Logger logger, Level level, String format, Object[] params, Throwable t) {
        if (!isStarted()) {
            return FilterReply.NEUTRAL;
        }
        if(level.levelInt >= 30000){
            return onMatch;
        }
        if (marker == null) {
            return onMismatch;
        }

        if (marker.contains(markerToMatch)) {
            return onMatch;
        } else {
            return onMismatch;
        }
    }

    /**
     * The marker to match in the event.
     *
     * @param markerStr
     */
    public void setMarker(String markerStr) {
        if (markerStr != null) {
            this.markerToMatch = MarkerFactory.getMarker(markerStr);
        }
    }
}
