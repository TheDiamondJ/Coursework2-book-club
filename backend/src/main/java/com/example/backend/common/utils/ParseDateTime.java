package com.example.backend.common.utils;

import java.time.LocalDateTime;

public class ParseDateTime {
    public static String parse(LocalDateTime time) {
        return time.getYear() + "-" + time.getMonthValue() + "-" + time.getDayOfMonth() + " " + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond();
    }
}
