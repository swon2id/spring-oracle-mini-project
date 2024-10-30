package com.kh.jdbc_oracle_spring.common;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class TimeUtility {
    public static String getDayOfWeek() {
        return ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
                .getDayOfWeek()
                .getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                .substring(0, 3).toLowerCase();
    }

    public static Timestamp getCurrentTimestamp() {
        return Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
    }
}
