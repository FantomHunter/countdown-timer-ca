package com.codehunter.countdowntimer.ca.util;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public final class DateTimeUtil {
    public static Instant toInstant(ZonedDateTime zonedDateTime) {
        return zonedDateTime.toInstant();
    }

    public static ZonedDateTime toZonedDateTime(Instant instant) {
        return ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
    }
}
