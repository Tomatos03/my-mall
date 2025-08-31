package com.mall.business.service;

import com.mall.api.service.IJobService;
import com.mall.api.service.IMenuService;
import com.mall.business.mapper.CommonMapper;
import com.mall.business.mapper.JobMapper;
import com.mall.dto.JobDTO;
import com.mall.dto.MenuDTO;
import com.mall.dto.condition.JobConditionDTO;
import com.mall.dto.condition.MenuConditionDTO;
import com.mall.entity.JobDO;
import com.mall.entity.MenuDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/31
 */
@Service
public class JobService
        extends CommonService<JobDO, JobDTO, JobConditionDTO>
        implements IJobService {
    @Autowired
    private JobMapper jobMapper;

    protected JobService() {
        super(JobDO.class);
    }

    @Override
    protected CommonMapper<JobDO, JobConditionDTO> getMapper() {
        return this.jobMapper;
    }
}
