package com.mall.api.service.sys;

import com.mall.dto.sys.CityDTO;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/30
 */
public interface IGEOIPService {
    CityDTO getCity(String ip);
}
