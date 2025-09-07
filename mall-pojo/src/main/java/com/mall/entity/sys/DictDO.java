package com.mall.entity.sys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 实体
 *
 * @author Tomatos
 * @date 2025-09-03
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DictDO extends CommonDO {
      /**
      * 父字段ID
      */
     private Long parentId;

     /**
      * 字典名称
      */
     private String dictName;

     /**
      * 字典描述
      */
     private String dictDescription;

}
