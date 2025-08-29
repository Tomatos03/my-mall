package com.mall.util;

import com.mall.entity.condition.RequestCondition;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/14
 */
public abstract class TimeUtil {
    private final static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static void fillTimeInterval(RequestCondition requestCondition) {
        List<String> betweenTime = requestCondition.getBetweenTime();
        if (betweenTime ==  null)
            return;

        int size = betweenTime.size();

        if (size >= 1)
            requestCondition.setCreateBeginTime(betweenTime.get(0));
        if (size >= 2)
            requestCondition.setCreateEndTime(betweenTime.get(1));
    }

    public static String nowFormatted() {
        return nowFormatted(DEFAULT_FORMAT);
    }

    public static String nowFormatted(String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.now().format(formatter);
    }
}
