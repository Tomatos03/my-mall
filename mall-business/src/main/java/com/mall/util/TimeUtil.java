package com.mall.util;

import com.mall.entity.condition.RequestCondition;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/14
 */
public class TimeUtil {
    public static void fillTimeInterval(RequestCondition requestCondition) {
        List<String> betweenTime = requestCondition.getBetweenTime();
        int size = betweenTime.size();

        if (size >= 1)
            requestCondition.setCreateBeginTime(betweenTime.get(0));
        if (size >= 2)
            requestCondition.setCreateEndTime(betweenTime.get(1));
    }
}
