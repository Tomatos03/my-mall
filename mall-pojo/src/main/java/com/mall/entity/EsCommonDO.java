package com.mall.entity;

import com.mall.dto.mall.ProductDTO;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/9
 */
@Data
@Builder
public class EsCommonDO {
    private String id;
    private Map<String, Object> data;

    public static EsCommonDO buildFrom(ProductDTO productDTO) {
        return EsCommonDO.builder()
                         .id(String.valueOf(productDTO.getId()))
                         .data(Map.of(
                                 "id", productDTO.getId(),
                             "name", productDTO.getName(),
                             "model", productDTO.getModel(),
                             "quantity", productDTO.getQuantity(),
                             "price", productDTO.getPrice()
                         ))
                         .build();
    }
}
