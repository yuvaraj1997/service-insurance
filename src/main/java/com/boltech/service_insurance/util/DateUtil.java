package com.boltech.service_insurance.util;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

public final class DateUtil {
    private DateUtil() {
        // Prevent instantiation
    }

    private static final ZoneOffset ZONE_OFF_SET = ZoneOffset.UTC;

    public static Instant nowDate() {
        return Instant.now();
    }

    public static ZonedDateTime nowZoneDateTime() {
        return Instant.now().atZone(ZONE_OFF_SET);
    }

    public static ZonedDateTime zoneDateTime(Instant value) {
        return value.atZone(ZONE_OFF_SET);
    }

    public static String nowAsString() {
        return nowDate().toString();
    }

    public static Date toDate(Instant instant) {
        return Date.from(instant);
    }

}
