package com.mall.api.service;

import com.mall.dto.common.SensitiveWordDTO;
import com.mall.dto.condition.mall.SensitiveWordConditionDTO;
import com.mall.entity.SensitiveWordDO;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/10
 */
public interface ISensitiveWordService {
    Boolean initSensitiveWord(int type, String filePath);

    List<SensitiveWordDO> searchByCondition(SensitiveWordConditionDTO condition);

    void checkSensitiveWord(SensitiveWordDTO dto);
}
