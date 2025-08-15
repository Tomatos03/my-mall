package com.mall.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.mall.constant.ExcelTitleConst;
import com.mall.domain.page.PageCondition;
import com.mall.dto.DeptDTO;
import com.mall.entity.DeptDO;
import com.mall.entity.condition.DeptConditionDTO;
import com.mall.entity.condition.ResponsePage;
import com.mall.mapper.DeptMapper;
import com.mall.util.ExcelUtil;
import com.mall.util.TimeUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/14
 */
@Service
public class DeptService {
    @Autowired
    private DeptMapper deptMapper;

    public ResponsePage<DeptDO> searchByPage(DeptConditionDTO deptConditionDTO) {
        TimeUtil.fillTimeInterval(deptConditionDTO);
        List<DeptDO> deptDOS = deptMapper.searchByCondition(deptConditionDTO);

        return ResponsePage.build(deptConditionDTO, deptDOS.size(), deptDOS);
    }

    public int deleteByIds(@NotNull List<Long> ids) {
        return deptMapper.deleteByIds(ids);
    }

    public void export(HttpServletResponse response, DeptConditionDTO deptConditionDTO) throws IOException, IOException {
        deptConditionDTO.setPageSize(PageCondition.ALL_PAGE);

        List<DeptDO> deptDOS = deptMapper.searchByCondition(deptConditionDTO);
        ExcelUtil.export(ExcelTitleConst.DEPT_DATE, DeptDO.class, deptDOS, response);
    }

    public int update(DeptDTO deptDTO) {
        DeptDO deptDO = BeanUtil.copyProperties(deptDTO, DeptDO.class);
        return deptMapper.update(deptDO);
    }

    public int insert(DeptDTO deptDTO) {
        DeptDO deptDO = BeanUtil.copyProperties(deptDTO, DeptDO.class);
        return deptMapper.insert(deptDO);
    }

    public List<DeptDTO> searchByTree(DeptConditionDTO deptConditionDTO) {
        DeptDTO deptDTO = new DeptDTO();
        deptDTO.setId(deptConditionDTO.getId());

        return getDeptSubTree(deptDTO);
    }

    private List<DeptDTO> getDeptSubTree(DeptDTO deptDTO) {
        DeptConditionDTO deptConditionDTO = new DeptConditionDTO();
        deptConditionDTO.setPageSize(PageCondition.NO_PAGINATION);
        deptConditionDTO.setPid(deptDTO.getId());

        // 查找是否存在子树, 如果deptDOS为空说明子树不存在
        List<DeptDO> deptDOS = deptMapper.searchByCondition(deptConditionDTO);
        if (CollectionUtil.isEmpty(deptDOS)) {
            deptDTO.setLeaf(true);
            deptDTO.setHasChildren(false);
            return Collections.emptyList();
        }

        deptDTO.setHasChildren(true);
        deptDTO.setSubCount(deptDOS.size());

        // 处理子节点
        List<DeptDTO> deptDTOS = new ArrayList<>();
        for (DeptDO deptDO : deptDOS) {
            DeptDTO newDeptDTO = BeanUtil.copyProperties(deptDO, DeptDTO.class);

            newDeptDTO.setLabel(newDeptDTO.getName());
            deptDTOS.add(newDeptDTO);

            newDeptDTO.setChildren(getDeptSubTree(newDeptDTO));
        }
        return deptDTOS;
    }
}
