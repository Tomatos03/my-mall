package com.mall.api.service;

import com.mall.dto.CityDTO;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/30
 */
public interface IGEOIPService {
    CityDTO getCity(String ip);
}
