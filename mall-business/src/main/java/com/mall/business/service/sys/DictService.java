package com.mall.business.service.sys;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.mall.api.service.sys.IDictService;
import com.mall.business.mapper.sys.CommonMapper;
import com.mall.business.mapper.sys.DictDetailMapper;
import com.mall.business.mapper.sys.DictMapper;
import com.mall.common.domain.cache.DictCacher;
import com.mall.dto.sys.DictDTO;
import com.mall.dto.condition.sys.DictConditionDTO;
import com.mall.dto.condition.sys.DictDetailConditionDTO;
import com.mall.dto.condition.sys.PageConditionDTO;
import com.mall.entity.sys.DictDO;
import com.mall.entity.sys.DictDetailDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private DictDetailMapper dictDetailMapper;

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

    @Override
    public void refreshDictCache() {
        List<DictDO> dictDOS = queryAllDict();
        if (CollectionUtils.isEmpty(dictDOS))
            return;

        List<DictDetailDO> dictDetailDOS = queryDictDetails();

        Map<Long, List<DictDetailDO>> dictMap = dictDetailDOS.stream()
                                                             .collect(Collectors.groupingBy(DictDetailDO::getDictId));
        int size = dictMap.size();
        Map<String, String> cacheMap = new HashMap<>(size);
        for (DictDO dictDO : dictDOS) {
            Long id = dictDO.getId();
            List<DictDetailDO> dictDetailList = dictMap.get(id);
            if (CollectionUtils.isEmpty(dictDetailList)) {
                dictDetailList = Collections.emptyList();
            }

            String dictDetailListJson = JSONUtil.toJsonStr(dictDetailList);
            cacheMap.put(dictDO.getDictName(), dictDetailListJson);
        }
        DictCacher.saveMap(cacheMap);
    }

    private List<DictDO> queryAllDict() {
        DictConditionDTO condition = new DictConditionDTO();
        condition.setPageSize(PageConditionDTO.ALL_PAGE);

        return dictMapper.searchByCondition(condition);
    }

    private List<DictDetailDO> queryDictDetails() {
        DictDetailConditionDTO condition = new DictDetailConditionDTO();
        condition.setPageSize(PageConditionDTO.ALL_PAGE);
        return dictDetailMapper.searchByCondition(condition);
    }
}
