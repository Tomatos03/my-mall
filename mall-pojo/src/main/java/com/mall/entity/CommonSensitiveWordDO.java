package com.mall.entity;

import com.mall.entity.sys.CommonDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 实体
 *
 * @author Tomatos
 * @date 2025-09-10
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommonSensitiveWordDO extends CommonDO {
 
     /**
      * 类型 1:广告 2:政治 3：违法 4：色情 5：网址
      */
     private Integer type;

     /**
      * 名称
      */
     private String word;

}
