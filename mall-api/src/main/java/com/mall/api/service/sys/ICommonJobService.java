package com.mall.api.service.sys;

import com.mall.api.mapper.IAutoFill;
import com.mall.dto.sys.CommonJobDTO;
import com.mall.dto.condition.sys.CommonJobConditionDTO;
import com.mall.entity.sys.CommonJobDO;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/5
 */
public interface ICommonJobService extends IAutoFill<CommonJobDTO> {
    int insert(CommonJobDTO dto);

    List<CommonJobDO> searchByCondition(CommonJobConditionDTO condition);
}
