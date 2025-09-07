package com.mall.business.mapper.sys;

import com.mall.api.mapper.IAutoFill;
import com.mall.common.annotation.AutoFill;
import com.mall.common.enums.FillTypeEnum;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/8/4
 */
public interface CommonMapper<DO, ConditionDTO> extends IAutoFill<DO> {
    /**
     * 根据条件查询数据的数量
     *
     * @param dto 数据传输对象
     * @return 数量
     */
    int searchCount(ConditionDTO dto);

    /**
     * 根据条件查询数据
     *
     * @param conditionDTO 条件数据传输对象
     * @return List<DO> 数据对象列表
     * @throws DataAccessException 数据访问异常
     */
    List<DO> searchByCondition(ConditionDTO conditionDTO);

    /**
     * 插入数据对象
     *
     * @param entity 数据对象
     * @return int
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/31 16:09
     */
    @AutoFill(FillTypeEnum.INSERT)
    int insert(DO entity);

    /**
     * 删除数据对象
     *
     * @param ids 数据对象
     * @return int
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/31 16:09
     */
    int deleteByIds (List<Long> ids);

    /**
     * 更新数据对象
     *
     * @param entity 数据对象
     * @return int
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/31 16:10
     */
    @AutoFill(FillTypeEnum.UPDATE)
    int update(DO entity);
}
