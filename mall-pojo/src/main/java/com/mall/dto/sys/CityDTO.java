package com.mall.dto.sys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/28
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CityDTO {

    /**
     * ip
     */
    private String ip;

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 所在城市
     */
    private String city;
}
