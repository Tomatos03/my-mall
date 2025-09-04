package com.mall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/** 实体
 *
 * @author Tomatos
 * @date 2025-09-04
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleDTO extends CommonDTO {
      /**
      * 名称
      */
     private String name;

     /**
      * 备注
      */
     private String remark;

     /**
      * 数据权限
      */
     private String dataScope;

     /**
      * 角色级别
      */
     private Integer level;

     /**
      * 功能权限
      */
     private String permission;

}