package com.mall.business.mapper.sys;

import com.mall.dto.condition.sys.CommonJobConditionDTO;
import com.mall.entity.sys.CommonJobDO;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/5
 */
public interface CommonJobMapper {
    int insert(CommonJobDO jobDO);

    List<CommonJobDO> searchByCondition(CommonJobConditionDTO condition);
}
