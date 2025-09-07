package com.mall.entity.mall;

import com.mall.entity.sys.CommonDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 单位实体 该项目是知识星球：java突击队 的内部项目
 *
 * @author 苏三
 * @date 2024-05-09 14:43:55
 */
@Schema(description = "单位实体")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UnitDO extends CommonDO {


    /**
     * 单位名称
     */
    @Schema(description = "单位名称")
    private String name;
}
