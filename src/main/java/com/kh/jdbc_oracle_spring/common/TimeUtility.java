package com.kh.jdbc_oracle_spring.common;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class TimeUtility {
    public final static String MON = "mon";
    public final static String TUE = "tue";
    public final static String WED = "wed";
    public final static String THU = "thu";
    public final static String FRI = "fri";
    public final static String SAT = "sat";
    public final static String SUN = "sun";

    public static int getDayOfWeek() {
        return convertDayOfWeekToInt(ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
                .getDayOfWeek()
                .getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                .substring(0, 3).toLowerCase());
    }

    public static int convertDayOfWeekToInt(String dayOfWeek) {
        switch (dayOfWeek) { // 요일 객체를 사용
            case MON: return 1;    // 요일 enum 사용
            case TUE: return 2;
            case WED: return 3;
            case THU: return 4;
            case FRI: return 5;
            case SAT: return 6;
            case SUN: return 0;
            default: return -1; // 기본값
        }
    }
}
