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
public class UnitDTO extends CommonDTO {
 
     /**
      * 单位名称
      */
     private String name;

}