package com.mall.business.service.es;

import cn.hutool.core.collection.CollectionUtil;
import com.mall.api.service.es.IProductEsSyncService;
import com.mall.api.service.mall.IProductService;
import com.mall.common.domain.template.EsTemplate;
import com.mall.common.properties.EsProperties;
import com.mall.dto.condition.mall.ProductConditionDTO;
import com.mall.dto.mall.ProductDTO;
import com.mall.entity.EsCommonDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/9
 */
@Service
public class ProductEsSyncService implements IProductEsSyncService {
    @Autowired
    private IProductService productService;

    @Autowired
    private EsProperties esProperties;

    private static final String ES_PRODUCT_INDEX = "product";

    @Override
    public void syncProductToEs() {
        ProductConditionDTO condition = new ProductConditionDTO();
        condition.setPageNo(0);
        condition.setPageSize(500);

        List<ProductDTO> products = productService.searchByCondition(condition);
        while (CollectionUtil.isNotEmpty(products)) {
            saveToEs(products);

            condition.setPageNo(condition.getPageNo() + 1);
            products = productService.searchByCondition(condition);
        }
    }

    private void saveToEs(List<ProductDTO> products) {
        List<EsCommonDO> dataList = products.stream()
                                            .map(EsCommonDO::buildFrom)
                                            .toList();

        EsTemplate.batchInsert(esProperties.getIndex().get(ES_PRODUCT_INDEX), dataList);
    }
}
