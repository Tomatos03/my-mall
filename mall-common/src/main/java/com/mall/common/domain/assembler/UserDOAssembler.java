package com.mall.common.domain.assembler;

import cn.hutool.core.collection.CollectionUtil;
import com.mall.api.service.IDeptService;
import com.mall.dto.condition.DeptConditionDTO;
import com.mall.entity.DeptDO;
import com.mall.entity.UserDO;
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
public class UserDOAssembler {
    @Autowired
    private IDeptService deptService;

    public List<UserDO> assemblerDept(List<UserDO> users) {
        if (CollectionUtil.isEmpty(users))
            return users;

        List<Long> deptIds = getDeptIds(users);

        if (CollectionUtil.isEmpty(users))
            return users;

        List<DeptDO> deptDOS = getDepts(deptIds);
        Map<Long, DeptDO> deptIdMap = getDeptIdToDeptMap(deptDOS);

        users.forEach(user -> {
            DeptDO deptDO = deptIdMap.get(user.getDeptId());
            user.setDeptName(deptDO.getName());
            user.setDept(deptDO);
        });

        return users;
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
}
