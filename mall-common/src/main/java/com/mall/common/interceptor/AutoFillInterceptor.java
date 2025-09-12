package com.mall.common.interceptor;

import com.mall.common.config.MockUserConfig;
import com.mall.common.context.SpringBeanHolder;
import com.mall.common.domain.IdGenerateHelp;
import com.mall.common.util.AuthenticatorUtil;
import com.mall.dto.sys.AuthenticatedUserDTO;
import com.mall.entity.sys.CommonDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.time.LocalDateTime;
import java.util.List;

/**
 * MyBatis 自动填充拦截器
 *
 * @author : Tomatos
 * @date : 2025/9/12
 */
@Slf4j
@Intercepts({
        @Signature(
                type = Executor.class,
                method = "update",
                args = {MappedStatement.class, Object.class}
        )
})
public class AutoFillInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.debug("进入 AutoFillInterceptor");
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        SqlCommandType sqlCommandType = ms.getSqlCommandType();

        boolean mockUserEnabled = SpringBeanHolder.getBean(MockUserConfig.class)
                                                  .isEnabled();

        if (mockUserEnabled)
            AuthenticatorUtil.setMockAuthenticatedUser();

        if (args[1] instanceof CommonDO commonDO) {
            fillUserInfo(commonDO, sqlCommandType);
        } else if (args[1] instanceof List<?> list) {
            for (Object item : list) {
                if (item instanceof CommonDO commonDO) {
                    fillUserInfo(commonDO, sqlCommandType);
                }
            }
        }

        if (mockUserEnabled)
            AuthenticatorUtil.clearMockAuthenticatedUser();
        return invocation.proceed();
    }

    private void fillUserInfo(CommonDO commonDO, SqlCommandType sqlCommandType) {
        LocalDateTime now = LocalDateTime.now();
        AuthenticatedUserDTO currentUser = AuthenticatorUtil.getAuthenticatedUser();

        if (sqlCommandType == SqlCommandType.UPDATE) {
            log.info("填充更新用户信息");
            commonDO.setUpdateTime(now);
            commonDO.setUpdateUserId(currentUser.getId());
            commonDO.setUpdateUserName(currentUser.getUsername());
        }

        if (sqlCommandType == SqlCommandType.INSERT) {
            log.info("填充创建用户信息");
            IdGenerateHelp idGenerateHelp = SpringBeanHolder.getBean(IdGenerateHelp.class);

            commonDO.setId(idGenerateHelp.nextId());
            commonDO.setCreateTime(now);
            commonDO.setCreateUserId(currentUser.getId());
            commonDO.setCreateUserName(currentUser.getUsername());
        }
    }
}
