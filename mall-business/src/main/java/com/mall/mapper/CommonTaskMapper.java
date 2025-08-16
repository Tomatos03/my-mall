package com.mall.mapper;

import com.mall.entity.CommonTaskDO;
import com.mall.entity.condition.CommonTaskCondition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/15
 */
@Mapper
public interface CommonTaskMapper extends BaseMapper<CommonTaskDO, CommonTaskCondition> {
    List<CommonTaskDO> searchByCondition(CommonTaskCondition commonTaskCondition);

    void insert(CommonTaskDO excelExportTask);
}