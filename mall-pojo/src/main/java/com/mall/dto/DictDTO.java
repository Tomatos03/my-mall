package com.mall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/** 实体
 *
 * @author Tomatos
 * @date 2025-09-03
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DictDTO extends CommonDTO {
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