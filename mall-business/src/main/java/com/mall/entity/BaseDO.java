package com.mall.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;



/**
 * @author : Tomatos
 * @date : 2025/8/2
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseDO implements Serializable {
    @ExcelProperty("系统ID")
    private Long id;

    @ExcelProperty("创建人ID")
    private Long createUserId;

    @ExcelProperty("创建人名称")
    private String createUserName;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("修改人ID")
    private Long updateUserId;

    @ExcelProperty("修改人名称")
    private String updateUserName;

    @ExcelProperty("修改时间")
    private LocalDateTime updateTime;

    @ExcelProperty("是否删除")
    private Integer isDel;
}
