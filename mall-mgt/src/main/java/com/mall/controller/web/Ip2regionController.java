package com.mall.controller.web;

import com.mall.annotation.NoLogin;
import com.mall.domain.Ip2regionSearcher;
import com.mall.dto.CityDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/28
 */
@Controller
@Tag(name = "ip2region操作", description = "ip2region操作")
@RestController
@RequestMapping("/v1/web/geoip")
@Validated
public class Ip2regionController {
    @Autowired
    private Ip2regionSearcher ip2regionSearcher;

    /**
     * 根据ip获取所在城市
     *
     * @param ip ip地址
     * @return 城市
     */
    @NoLogin
    @Operation(summary = "根据ip获取所在城市", description = "根据ip获取所在城市")
    @GetMapping("/getCity")
    public CityDTO getCity(@RequestParam(value = "ip") String ip) {
        String[] info = ip2regionSearcher.queryIpInfo(ip);

        String city = ip2regionSearcher.getCity(info);
        String country = ip2regionSearcher.getCountry(info);
        String province = ip2regionSearcher.getProvince(info);

        return CityDTO.builder()
                      .city(city)
                      .country(country)
                      .province(province)
                      .build();
    }
}
