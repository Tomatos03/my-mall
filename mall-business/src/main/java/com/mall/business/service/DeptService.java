package com.mall.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.mall.api.service.IDeptService;
import com.mall.dto.DeptDTO;
import com.mall.dto.condition.DeptConditionDTO;
import com.mall.entity.DeptDO;
import com.mall.dto.condition.PageConditionDTO;
import com.mall.business.mapper.CommonMapper;
import com.mall.business.mapper.DeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class DeptService
        extends CommonService<DeptDO, DeptDTO, DeptConditionDTO>
        implements IDeptService {
    @Autowired
    private DeptMapper deptMapper;

    protected DeptService() {
        super(DeptDO.class);
    }

//    /**
//     * 导出Excel到浏览器下载
//     *
//     * @param response
//     * @param deptConditionDTO
//     * @return void
//     * @since : 1.0
//     * @author : Tomatos
//     * @date : 2025/8/31 11:52
//     */
//    public void export(HttpServletResponse response, DeptConditionDTO deptConditionDTO) throws IOException {
//        super.export(deptConditionDTO, response, DeptDO.class, ExcelTitleConst.DEPT_DATE);
//    }

    public List<DeptDTO> searchByTree(DeptConditionDTO deptCondition) {
        Long pid = deptCondition.getPid();
        if (pid == null)
            pid = 0L;

        DeptDTO deptDTO = new DeptDTO();
        deptDTO.setId(pid);

        return getDeptSubTree(deptDTO);
    }

    private List<DeptDTO> getDeptSubTree(DeptDTO deptDTO) {
        DeptConditionDTO deptCondition = new DeptConditionDTO();
        deptCondition.setPageSize(PageConditionDTO.NO_PAGINATION);
        deptCondition.setPid(deptDTO.getId());

        // 查找是否存在子树, 如果deptDOS为空说明子树不存在
        List<DeptDO> deptDOS = deptMapper.searchByCondition(deptCondition);
        if (CollectionUtil.isEmpty(deptDOS)) {
            deptDTO.setLeaf(true);
            deptDTO.setHasChildren(false);
            return Collections.emptyList();
        }

        deptDTO.setHasChildren(true);
        deptDTO.setLeaf(false);
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

    @Override
    protected CommonMapper<DeptDO, DeptConditionDTO> getMapper() {
        return deptMapper;
    }
}
