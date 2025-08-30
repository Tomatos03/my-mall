package com.mall.api.service;

import com.mall.dto.DeptDTO;
import com.mall.dto.condition.DeptConditionDTO;
import com.mall.entity.DeptDO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;

import java.io.IOException;
import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/29
 */
public interface IDeptService extends ICommonService<DeptDO, DeptConditionDTO> {
    int deleteByIds(@NotNull List<Long> ids);

    void export(HttpServletResponse response, DeptConditionDTO deptConditionDTO) throws IOException;

    int update(DeptDTO deptDTO);

    int insert(DeptDTO deptDTO);

    List<DeptDTO> searchByTree(DeptConditionDTO deptConditionDTO);
}
