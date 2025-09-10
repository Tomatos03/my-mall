package com.mall.business.service.common;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/10
 */

import cn.hutool.core.collection.CollectionUtil;
import com.mall.api.service.ICommonSensitiveWordService;
import com.mall.business.mapper.CommonSensitiveWordMapper;
import com.mall.constant.JobUserConst;
import com.mall.entity.CommonSensitiveWordDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class CommonSensitiveWordService implements ICommonSensitiveWordService {
    @Autowired
    private CommonSensitiveWordMapper commonSensitiveWordMapper;

    /**
     * 初始化敏感词
     *
     * @param type     类型
     * @param filePath 文件路径
     */
    public Boolean initSensitiveWord(int type, String filePath) {
        List<CommonSensitiveWordDO> sensitiveWordList = readSensitiveWordsFromFile(type, filePath);
        if (CollectionUtil.isEmpty(sensitiveWordList))
            return Boolean.FALSE;

        // TODO: 没有处理创建人信息, 这先写死
        commonSensitiveWordMapper.batchInsert(sensitiveWordList);
        return Boolean.TRUE;
    }

    private List<CommonSensitiveWordDO> readSensitiveWordsFromFile(int type, String filePath) {
        List <CommonSensitiveWordDO> sensitiveWords = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            String sensitiveWord;
            while ((sensitiveWord = in.readLine()) != null) {
                CommonSensitiveWordDO commonSensitiveWordDO = new CommonSensitiveWordDO();
                commonSensitiveWordDO.setType(type);
                commonSensitiveWordDO.setWord(sensitiveWord);
                commonSensitiveWordDO.setCreateUserName(JobUserConst.DEFAULT_USER_NAME);
                commonSensitiveWordDO.setCreateUserId(JobUserConst.DEFAULT_USER_ID);

                sensitiveWords.add(commonSensitiveWordDO);
            }
            return sensitiveWords;
        } catch (Exception e) {
            log.error("读取敏感词文件失败, 原因: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}