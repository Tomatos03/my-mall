package com.mall.job;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.mall.dto.NotifyDTO;
import com.mall.entity.NotifyDO;
import com.mall.entity.condition.NotifyCondition;
import com.mall.mapper.CommonNotifyMapper;
import com.mall.service.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/19
 */
@Slf4j
@Component
public class NotifyJob {
    @Autowired
    private CommonNotifyMapper commonNotifyMapper;

    @Scheduled(fixedRate = 5000L)
    public void tryPushNotify() {
        NotifyCondition notifyCondition = NotifyCondition.builder()
                                                         .isPush(0)
                                                         .isDel(0)
                                                         .build();
        List<NotifyDO> notifyDOS = commonNotifyMapper.searchByCondition(notifyCondition);
        if (isNotNotify(notifyDOS))
            return;

        for (NotifyDO notifyDO : notifyDOS) {
            pushNotify(notifyDO);
        }
    }

    private void pushNotify(NotifyDO notifyDO) {
        try {
            NotifyDTO notifyDTO = BeanUtil.copyProperties(notifyDO, NotifyDTO.class);
            WebSocketServer.sendMessage(notifyDTO);

            notifyDO.setIsPush(1);
            commonNotifyMapper.update(notifyDO);
        } catch (IOException e) {
            log.error("推送失败:{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private boolean isNotNotify(List<NotifyDO> notifyDOS) {
        if (CollectionUtil.isEmpty(notifyDOS)) {
            log.info("没有消息推送");
            return true;
        }
        return false;
    }
}
