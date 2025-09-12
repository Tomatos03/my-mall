package com.mall.common.interceptor;

import cn.hutool.core.text.CharSequenceUtil;
import com.mall.annotation.Desensitization;
import com.mall.enums.DesensitizationTypeEnum;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/12
 */
@Intercepts(
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
        )
)
public class SensitiveInfoInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws InvocationTargetException, IllegalAccessException {
        Object result = invocation.proceed();
        if (result instanceof List<?> list) {
            list.forEach(this::doDesensitize);
        } else if (result instanceof Map<?, ?> map) {
            map.forEach((k, v) -> doDesensitize(v));
        } else {
            doDesensitize(result);
        }
        return result;
    }

    private void doDesensitize(Object obj) {
        try {
            for (Field field : obj.getClass()
                                  .getDeclaredFields()) {
                Desensitization annotation = field.getAnnotation(Desensitization.class);
                if (annotation == null)
                    continue;

                field.setAccessible(true);

                if (field.get(obj) instanceof String data) {
                    DesensitizationTypeEnum type = annotation.value();
                    field.set(obj,
                              type == DesensitizationTypeEnum.CUSTOM
                              ? type.maskSensitiveData(data, this::customMaskData)
                              : type.maskSensitiveData(data)
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String customMaskData(String data) {
        return CharSequenceUtil.hide(data, 1, data.length());
    }
}

