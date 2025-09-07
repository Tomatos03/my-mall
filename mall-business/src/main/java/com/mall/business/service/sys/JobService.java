package com.mall.business.service.sys;

import com.mall.api.service.sys.IJobService;
import com.mall.business.mapper.sys.CommonMapper;
import com.mall.business.mapper.sys.JobMapper;
import com.mall.dto.sys.JobDTO;
import com.mall.dto.condition.sys.JobConditionDTO;
import com.mall.entity.sys.JobDO;
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
