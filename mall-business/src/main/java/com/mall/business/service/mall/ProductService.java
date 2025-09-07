package com.mall.business.service.mall;

import cn.hutool.core.bean.BeanUtil;
import com.mall.api.service.mall.IProductService;
import com.mall.business.mapper.mall.*;
import com.mall.common.util.AsserUtil;
import com.mall.dto.condition.mall.*;
import com.mall.dto.condition.sys.PageConditionDTO;
import com.mall.dto.mall.ProductDTO;
import com.mall.entity.mall.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务层
 *
 * @author Tomatos
 * @date 2025-09-07
 */
@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private UnitMapper unitMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private AttributeMapper attributeMapper;
    @Autowired
    private AttributeValueMapper attributeValueMapper;

    @Override
    public int insert(ProductDTO dto) {
        ProductDO entity = BeanUtil.copyProperties(dto, ProductDO.class);
        int affects = productMapper.insert(entity);
        return affects;
    }

    @Override
    public int update(ProductDTO dto) {
        ProductDO entity = BeanUtil.copyProperties(dto, ProductDO.class);
        return productMapper.update(entity);
    }

    @Override
    public int delete(Long id) {
        return productMapper.delete(id);
    }

    @Override
    public List<ProductDTO> searchByCondition(ProductConditionDTO condition) {
        List<ProductDO> entities = productMapper.searchByCondition(condition);
        return entities.stream()
                       .map(entity -> BeanUtil.copyProperties(entity, ProductDTO.class))
                       .toList();
    }

    @Override
    public int deleteByIds(List<Long> ids) {
        return productMapper.deleteByIds(ids);
    }

    @Override
    public List<ProductDTO> generate(List<ProductDTO> productDTOList) {
        checkParams(productDTOList);
        doGenerate(productDTOList);
        return productDTOList;
    }

    private void doGenerate(List<ProductDTO> productDTOList) {

    }

    private void checkParams(List<ProductDTO> productDTOList) {
        checkCategory(productDTOList);
        checkUnit(productDTOList);
        checkBrand(productDTOList);
        checkAttribute(productDTOList);
    }

    private void checkAttribute(List<ProductDTO> productDTOList) {
        // 检查sup
        List<AttributeValueDO> supAttributeValueDOList = productDTOList.stream()
                                                                       .flatMap(
                                                                               productDTO -> productDTO.getSpuAttributeEntityList()
                                                                                                       .stream())
                                                                       .toList();
        AsserUtil.notNull(supAttributeValueDOList, "商品属性值不能够为空");
        checkAttributeValue(supAttributeValueDOList);

        List<AttributeValueDO> skuAttributeValueDOList = productDTOList.stream()
                                                                       .flatMap(
                                                                               productDTO -> productDTO.getSkuAttributeEntityList()
                                                                                                       .stream()).toList();
        // 检查sku
        AsserUtil.notNull(skuAttributeValueDOList, "商品属性值不为空");
        checkAttributeValue(skuAttributeValueDOList);
    }

    private void checkAttributeValue(List<AttributeValueDO> attributeValueDOList) {
        List<Long> attributeValueIds = attributeValueDOList.stream()
                                                           .map(AttributeValueDO::getId)
                                                           .toList();

        // 检查商品属性数否存在
        AttributeValueConditionDTO attributeValueConditionDTO = new AttributeValueConditionDTO();
        attributeValueConditionDTO.setPageSize(PageConditionDTO.ALL_PAGE);
        attributeValueConditionDTO.setIdList(attributeValueIds);
        List<AttributeValueDO> attributeValueDOS = attributeValueMapper.searchByCondition(
                attributeValueConditionDTO);
        AsserUtil.notNull(attributeValueDOS, "商品属性值不能够为空");

        // 检查属性值对应的属性是否存在
        List<Long> attributeIds = attributeValueDOS.stream()
                                                   .map(AttributeValueDO::getAttributeId)
                                                   .distinct()
                                                   .toList();
        AttributeConditionDTO attributeConditionDTO = new AttributeConditionDTO();
        attributeConditionDTO.setPageSize(PageConditionDTO.ALL_PAGE);
        attributeConditionDTO.setIdList(attributeIds);
        List<AttributeDO> attributeDOS = attributeMapper.searchByCondition(attributeConditionDTO);
        AsserUtil.notNull(attributeDOS, "商品属性不能够为空");

        // 设置商品关系映射
    }

    private void checkBrand(List<ProductDTO> productDTOList) {
        List<Long> brandIds = productDTOList.stream()
                                            .map(ProductDTO::getBrandId)
                                            .toList();
        BrandConditionDTO condition = new BrandConditionDTO();
        condition.setIdList(brandIds);
        List<BrandDO> brandDOS = brandMapper.searchByCondition(condition);

        AsserUtil.notNull(brandDOS, "品牌不能够是空");

        List<Long> notFoundList = brandIds.stream()
                                          .filter(id -> brandDOS.stream()
                                                                .noneMatch(
                                                                        brandDO -> brandDO.getId()
                                                                                          .equals(id)))
                                          .toList();
        AsserUtil.isNull(notFoundList, String.format("存在系统中不存在的品牌ID: %s", notFoundList));
    }

    private void checkUnit(List<ProductDTO> productDTOList) {
        List<Long> unitIds = productDTOList.stream()
                                           .map(ProductDTO::getUnitId)
                                           .toList();

        UnitConditionDTO unitConditionDTO = new UnitConditionDTO();
        unitConditionDTO.setPageSize(PageConditionDTO.ALL_PAGE);
        unitConditionDTO.setIdList(unitIds);
        List<UnitDO> unitDOS = unitMapper.searchByCondition(unitConditionDTO);

        AsserUtil.notNull(unitDOS, "单位不能够是空");

        List<Long> notFoundList = unitIds.stream()
                                         .filter(id ->
                                                         unitDOS.stream()
                                                                .noneMatch(unitDO -> unitDO.getId()
                                                                                           .equals(id))
                                         )
                                         .toList();
        AsserUtil.isNull(notFoundList, String.format("存在系统中不存在的单位ID: %s", notFoundList));
    }

    private void checkCategory(List<ProductDTO> productDTOList) {
        List<Long> categoryIds = productDTOList.stream()
                                               .map(ProductDTO::getCategoryId)
                                               .distinct()
                                               .toList();

        // 根据分类id查询所有分类
        CategoryConditionDTO condition = new CategoryConditionDTO();
        condition.setPageSize(PageConditionDTO.ALL_PAGE);
        condition.setIdList(condition.getIdList());
        List<CategoryDO> categories = categoryMapper.searchByCondition(condition);

        AsserUtil.notNull(categories, "分类不能是空");

        // 防止脏数据
        List<Long> notFoundList = categoryIds.stream()
                                             .filter(id ->
                                                             categories.stream()
                                                                       .noneMatch(
                                                                               category -> category.getId()
                                                                                                   .equals(id))
                                             )
                                             .toList();
        AsserUtil.notNull(notFoundList, String.format("存在系统中不存在的分类ID: %s", notFoundList));
    }
}
