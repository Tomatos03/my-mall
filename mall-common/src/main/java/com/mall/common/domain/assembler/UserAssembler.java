package com.mall.common.domain.assembler;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.mall.api.service.sys.IDeptService;
import com.mall.api.service.sys.IUserRoleService;
import com.mall.dto.condition.sys.DeptConditionDTO;
import com.mall.dto.condition.sys.UserRoleConditionDTO;
import com.mall.entity.sys.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/31
 */
@Component
public class UserAssembler {
    @Autowired
    private IDeptService deptService;
    @Autowired
    private IUserRoleService userRoleService;

    public void assemblerDept(List<UserDO> users) {
        if (CollectionUtil.isEmpty(users))
            return;

        List<Long> deptIds = getDeptIds(users);

        if (CollectionUtil.isEmpty(users))
            return;

        List<DeptDO> deptDOS = getDepts(deptIds);
        Map<Long, DeptDO> deptIdMap = getDeptIdToDeptMap(deptDOS);

        users.forEach(user -> {
            DeptDO deptDO = deptIdMap.get(user.getDeptId());
            user.setDeptName(deptDO.getName());
            user.setDept(deptDO);
        });
    }

    private Map<Long, DeptDO> getDeptIdToDeptMap(List<DeptDO> depts) {
        return depts.stream()
                    .collect(
                            Collectors.toMap(
                                    DeptDO::getId,
                                    (deptDO -> deptDO)
                            )
                    );
    }

    private List<DeptDO> getDepts(List<Long> deptIds) {
        DeptConditionDTO condition = new DeptConditionDTO();
        condition.setIdList(deptIds);

        return deptService.searchByCondition(condition);
    }

    private List<Long> getDeptIds(List<UserDO> users) {
        return users.stream()
                    .filter(Objects::nonNull)
                    .map(UserDO::getDeptId)
                    .toList();
    }

    public void assemblerRole(List<UserDO> users) {
        List<Long> userIds = users.stream()
                                  .map(UserDO::getId)
                                  .toList();

        UserRoleConditionDTO condition = new UserRoleConditionDTO();
        condition.setUserIdList(userIds);
        Map<Long, List<UserRoleDO>> userRoleMap = userRoleService.searchByCondition(condition)
                                                                 .stream()
                                                                 .collect(Collectors.groupingBy(UserRoleDO::getUserId));

        users.forEach(user -> {
            List<UserRoleDO> userRoleList = userRoleMap.get(user.getId());
            List<RoleDO> roles = userRoleMap.get(user.getId())
                                            .stream()
                                            .map(userRole -> (RoleDO)RoleDO.builder()
                                                                           .id(userRole.getRoleId())
                                                                           .build())
                                            .toList();
            user.setRoleList(roles);
        });
    }

    public void assemblerJob(List<UserDO> users) {
        users.forEach(user -> {
            user.setJobList(Lists.newArrayList(
                    JobDO.builder()
                         .id(user.getJobId())
                         .build()
            ));
        });
    }
}
