package com.mall.business.service.sys;

import com.mall.api.service.sys.IGEOIPService;
import com.mall.common.util.Ip2regionUtil;
import com.mall.dto.sys.CityDTO;
import org.springframework.stereotype.Service;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/30
 */
@Service
public class GEOIPService implements IGEOIPService {
    @Override
    public CityDTO getCity(String ip) {
        String city = Ip2regionUtil.city(ip);
        String country = Ip2regionUtil.country(ip);
        String province = Ip2regionUtil.province(ip);

        return CityDTO.builder()
                      .ip(ip)
                      .city(city)
                      .country(country)
                      .province(province)
                      .build();
    }
}
