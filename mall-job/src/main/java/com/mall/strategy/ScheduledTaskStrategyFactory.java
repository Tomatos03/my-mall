package com.mall.strategy;

import com.mall.enums.TaskType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/29
 */
@Component
public class ScheduledTaskStrategyFactory {
    private final Map<TaskType, ScheduledTaskStrategy> strategyMap = new HashMap<>();

    @Autowired
    public ScheduledTaskStrategyFactory(List<ScheduledTaskStrategy> strategies) {
        for (ScheduledTaskStrategy strategy : strategies) {
            strategyMap.put(strategy.getTaskType(), strategy);
        }
    }

    public ScheduledTaskStrategy getStrategy(TaskType taskType) {
        ScheduledTaskStrategy strategy = strategyMap.get(taskType);
        if (strategy == null) {
            throw new IllegalArgumentException("No strategy found for type: " + taskType);
        }
        return strategy;
    }

    public ScheduledTaskStrategy getStrategy(Integer value) {
        TaskType taskType = TaskType.fromValue(value);
        return getStrategy(taskType);
    }
}
