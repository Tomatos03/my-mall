package com.mall.api.service;

import com.mall.dto.condition.mall.CommonSensitiveWordConditionDTO;
import com.mall.entity.CommonSensitiveWordDO;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/10
 */
public interface ICommonSensitiveWordService {
    Boolean initSensitiveWord(int type, String filePath);

    List<CommonSensitiveWordDO> searchByCondition(CommonSensitiveWordConditionDTO condition);

    void checkSensitiveWord(CommonSensitiveWordDO commonSensitiveWord);
}
