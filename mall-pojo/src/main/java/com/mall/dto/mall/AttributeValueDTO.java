package com.mall.dto.mall;

import com.mall.dto.sys.CommonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 实体
 *
 * @author Tomatos
 * @date 2025-09-07
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttributeValueDTO extends CommonDTO {
 
     /**
      * 属性ID
      */
     private Long attributeId;

     /**
      * 属性值
      */
     private String value;

     /**
      * 排序
      */
     private Integer sort;

}