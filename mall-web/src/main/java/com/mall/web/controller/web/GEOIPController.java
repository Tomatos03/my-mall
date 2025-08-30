package com.mall.web.controller.web;

import com.mall.api.service.IGEOIPService;
import com.mall.common.annotation.NoLogin;
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
public class GEOIPController {
    @Autowired
    private IGEOIPService geoIPService;

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
        return geoIPService.getCity(ip);
    }
}
