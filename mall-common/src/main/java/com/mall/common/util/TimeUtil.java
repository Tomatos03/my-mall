package com.mall.common.util;

import com.mall.dto.condition.sys.CommonConditionDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/14
 */
public final class TimeUtil {
    private final static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private TimeUtil(){};

    public static void fillTimeInterval(CommonConditionDTO commonCondition) {
        List<String> betweenTime = commonCondition.getBetweenTime();
        if (betweenTime ==  null)
            return;

        int size = betweenTime.size();

        if (size >= 1)
            commonCondition.setCreateBeginTime(betweenTime.get(0));
        if (size >= 2)
            commonCondition.setCreateEndTime(betweenTime.get(1));
    }

    public static String nowFormatted() {
        return nowFormatted(DEFAULT_FORMAT);
    }

    public static String nowFormatted(String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.now().format(formatter);
    }
}
