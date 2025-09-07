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
public class ProductPhotoDTO extends CommonDTO {
 
     /**
      * 商品ID
      */
     private Long productId;

     /**
      * 图片名称
      */
     private String name;

     /**
      * 图片url
      */
     private String url;

     /**
      * 排序
      */
     private Integer sort;

}