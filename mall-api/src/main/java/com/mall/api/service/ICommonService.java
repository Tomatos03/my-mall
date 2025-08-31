package com.mall.api.service;

import com.mall.dto.PageDTO;
import com.mall.dto.condition.DeptConditionDTO;
import com.mall.entity.DeptDO;

import java.io.IOException;
import java.util.List;

/**
 * K表示一个DTO, V表示一个conditionDTO
 *
 * @author : Tomatos
 * @date : 2025/8/30
 */
public interface ICommonService<DO, DTO, ConditionDTO> {
    void export(ConditionDTO condition, Class<DO> kClass, String fileName) throws IOException;

    int insert(DTO dto);

    int deleteByIds(List<Long> ids);

    int update(DTO dto);

    List<DO> searchByCondition(ConditionDTO conditionDO);

    PageDTO<DO> searchByPage(ConditionDTO condition);
}
