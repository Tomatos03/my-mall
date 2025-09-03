package com.mall.business.service;

import cn.hutool.core.bean.BeanUtil;
import com.mall.api.service.IDictService;
import com.mall.business.mapper.CommonMapper;
import com.mall.business.mapper.DictMapper;
import com.mall.dto.DictDTO;
import com.mall.dto.condition.DictConditionDTO;
import com.mall.entity.DictDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务层
 *
 * @author Tomatos
 * @date 2025-09-03
 */
@Service
public class DictService
        extends CommonService<DictDO, DictDTO, DictConditionDTO>
        implements IDictService {
    @Autowired
    private DictMapper dictMapper;
    //    @Autowired
    //    private DictDetailMapper dictDetailMapper;
    //    @Autowired
    //    private RedisUtil redisUtil;

    public DictService() {
        super(DictDO.class);
    }

    @Override
    protected CommonMapper<DictDO, DictConditionDTO> getMapper() {
        return dictMapper;
    }

    /**
     * 查询数据字典信息
     *
     * @param id 数据字典ID
     * @return 数据字典信息
     */
    public DictDTO findById(Long id) {
        DictDO entity = dictMapper.findById(id);
        return BeanUtil.toBean(entity, DictDTO.class);
    }

//    /**
//     * 从缓存中获取数据字典
//     *
//     * @param dictName 数据字典名称
//     * @return 数据字典
//     */
//    @Cacheable(value = "dict_data", keyGenerator = "dictCacheKeyGenerator")
//    public List<DictDetailEntity> queryDictDetailEntity(String dictName) {
//        List<DictDetailEntity> dataList = getDictDataFromRedis(dictName);
//        if (CollectionUtils.isEmpty(dataList)) {
//            return Collections.emptyList();
//        }
//        return dataList.stream()
//                       .sorted((a, b) -> a.getSort()
//                                          .compareTo(b.getSort()))
//                       .collect(Collectors.toList());
//    }

//    /**
//     * 更新redis中的数据字典
//     */
//    public void refreshDict() {
//        DictConditionEntity dictConditionEntity = new DictConditionEntity();
//        dictConditionEntity.setPageSize(0);
//        List<DictDO> dictEntities = dictMapper.searchByCondition(dictConditionEntity);
//        if (CollectionUtils.isEmpty(dictEntities)) {
//            return;
//        }
//
//        List<Long> dictIdList = dictEntities.stream()
//                                            .map(DictDO::getId)
//                                            .collect(Collectors.toList());
//        DictDetailConditionEntity dictDetailConditionEntity = new DictDetailConditionEntity();
//        dictDetailConditionEntity.setDictIdList(dictIdList);
//        dictDetailConditionEntity.setPageSize(0);
//        List<DictDetailEntity> dictDetailEntities =
//                dictDetailMapper.searchByCondition(dictDetailConditionEntity);
//        Map<Long, List<DictDetailEntity>> dictDetailMap = dictDetailEntities.stream()
//                                                                            .collect(Collectors.groupingBy(DictDetailEntity::getDictId));
//
//        Map<Object, Object> dictMap = new HashMap<>(dictEntities.size());
//        for (DictDO dictDO : dictEntities) {
//            List<DictDetailEntity> detailEntityList = dictDetailMap.get(dictDO.getId());
//            dictMap.put(dictDO.getDictName(), JSONUtil.toJsonStr(detailEntityList));
//        }
//
//        redisUtil.putHashMap(DICT_DATA_KEY, dictMap);
//    }

//    /**
//     * 从redis中获取数据字典数据
//     *
//     * @return 数据字典数据
//     */
//    public List<DictDetailEntity> getDictDataFromRedis(String hashKey) {
//        String json = (String) redisUtil.getHashValue(DICT_DATA_KEY, hashKey);
//        if (!StringUtils.hasLength(json)) {
//            return Collections.emptyList();
//        }
//
//        return JSONUtil.toList(json, DictDetailEntity.class);
//    }
}
