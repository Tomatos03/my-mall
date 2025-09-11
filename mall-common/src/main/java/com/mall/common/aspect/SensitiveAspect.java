package com.mall.common.aspect;

import com.mall.annotation.SensitiveWord;
import com.mall.api.service.ISensitiveWordService;
import com.mall.dto.common.SensitiveWordDTO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/11
 */
@Slf4j
@Aspect
@Component
public class SensitiveAspect {
    @Autowired
    private ISensitiveWordService sensitiveWordService;

    @Before("@annotation(com.mall.common.annotation.sensitive.SensitiveWordCheck)")
    public void checkSensitiveWord(JoinPoint point) throws IllegalAccessException {
        log.info("进行敏感词检查");
        for (Object arg : point.getArgs()) {
            for (Field field : getObjectFields(arg)) {
                if (field.getType().equals(String.class) && field.isAnnotationPresent(SensitiveWord.class)) {
                    field.setAccessible(true);

                    SensitiveWordDTO commonSensitiveWord = new SensitiveWordDTO();
                    commonSensitiveWord.setWord((String) field.get(arg));
                    // 包含敏感词时抛出异常
                    sensitiveWordService.checkSensitiveWord(commonSensitiveWord);
                }
            }
        }
    }
    public List<Field> getObjectFields(Object obj) throws IllegalAccessException {
        List<Field> fields = new ArrayList<>();

        Class<?> aClass = obj.getClass();
        while (aClass != null) {
            fields.addAll(List.of(aClass.getDeclaredFields()));
            aClass = aClass.getSuperclass();
        }
        return fields;
    }
}
