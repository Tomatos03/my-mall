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
public class CategoryDTO extends CommonDTO {
 
     /**
      * 父分类ID
      */
     private Long parentId;

     /**
      * 分类名称
      */
     private String name;

     /**
      * 层级
      */
     private Integer level;

     /**
      * 是否叶子节点
      */
     private Integer isLeaf;

}