package com.mall.business.service.sys;

import cn.hutool.json.JSONUtil;
import com.mall.api.service.sys.IDictDetailService;
import com.mall.business.mapper.sys.CommonMapper;
import com.mall.business.mapper.sys.DictDetailMapper;
import com.mall.business.mapper.sys.DictMapper;
import com.mall.common.domain.cache.DictCacher;
import com.mall.dto.sys.DictDetailDTO;
import com.mall.dto.sys.PageDTO;
import com.mall.dto.condition.sys.DictConditionDTO;
import com.mall.dto.condition.sys.DictDetailConditionDTO;
import com.mall.entity.sys.DictDO;
import com.mall.entity.sys.DictDetailDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/3
 */
@Service
public class DictDetailService
        extends CommonService<DictDetailDO, DictDetailDTO, DictDetailConditionDTO>
        implements IDictDetailService {
    @Autowired
    private DictDetailMapper dictDetailMapper;
    @Autowired
    private DictMapper dictMapper;

    public DictDetailService() {
        super(DictDetailDO.class);
    }

    @Override
    protected CommonMapper<DictDetailDO, DictDetailConditionDTO> getMapper() {
        return dictDetailMapper;
    }

    @Override
    public int insert(DictDetailDTO dto) {
        dto.setDictId(dto.getDict().getId());
        return super.insert(dto);
    }

    @Override
    public DictDetailDO findById(Long id) {
        return dictDetailMapper.findById(id);
    }

    @Override
    @Cacheable(value = "dict_data", keyGenerator = "dictCacheKeyGenerator")
    public List<DictDetailDO> searchDictDetailFromCache(String dictName) {
        String dictDetailListJson = DictCacher.getDictDetailListJson(dictName);
        return JSONUtil.toList(dictDetailListJson, DictDetailDO.class);
    }

    @Override
    public PageDTO<DictDetailDO> searchByPage(DictDetailConditionDTO condition) {
        DictConditionDTO dictCondition = new DictConditionDTO();
        dictCondition.setDictName(condition.getDictName());

        List<DictDO> dictDOS = dictMapper.searchByCondition(dictCondition);
        condition.setDictId(dictDOS.get(0).getId());

        return super.searchByPage(condition);
    }
}
