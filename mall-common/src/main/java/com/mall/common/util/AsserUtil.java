package com.mall.common.util;

import com.mall.common.exception.BusinessException;
import io.jsonwebtoken.lang.Collections;

import java.util.Collection;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/8
 */
public abstract class AsserUtil {
    private AsserUtil() {};

    public static final int ASSERT_ERROR_CODE = 1;

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new BusinessException(ASSERT_ERROR_CODE, message);
        }
    }

    public static void isNull(Collection<?> collection, String message) {
        if (Collections.isEmpty(collection)) {
            throw new BusinessException(ASSERT_ERROR_CODE, message);
        }
    }
}
