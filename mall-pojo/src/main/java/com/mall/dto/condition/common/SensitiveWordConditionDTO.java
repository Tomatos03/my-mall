package com.mall.dto.condition.mall;

import com.mall.dto.condition.sys.CommonConditionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 实体
 *
 * @author Tomatos
 * @date 2025-09-10
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SensitiveWordConditionDTO extends CommonConditionDTO {

    /**
     * 类型 1:广告 2:政治 3：违法 4：色情 5：网址
     */
    private Integer type;

    /**
     * 名称
     */
    private String word;

    /**
     * 系统ID
     */
    private Long id;

    /**
     * 创建人ID
     */
    private Long createUserId;

    /**
     * 创建人名称
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    private Long updateUserId;

    /**
     * 修改人名称
     */
    private String updateUserName;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    private Integer isDel;
}
