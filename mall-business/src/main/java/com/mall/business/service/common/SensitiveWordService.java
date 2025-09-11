package com.mall.business.service.common;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/10
 */

import cn.hutool.core.collection.CollectionUtil;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.mall.api.service.ISensitiveWordService;
import com.mall.business.mapper.common.SensitiveWordMapper;
import com.mall.common.util.AsserUtil;
import com.mall.constant.JobUserConst;
import com.mall.dto.common.SensitiveWordDTO;
import com.mall.dto.condition.mall.SensitiveWordConditionDTO;
import com.mall.dto.condition.sys.PageConditionDTO;
import com.mall.entity.SensitiveWordDO;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SensitiveWordService implements ISensitiveWordService {
    @Autowired
    private SensitiveWordMapper sensitiveWordMapper;

    private Map<String, Integer> sensitiveWordMap;

    private List<String> sensitiveWordWhiteList = new ArrayList<>();

    @Value("${my-mall.sensitive-word-whiteList.file-path}")
    private String sensitiveWordFilePath;

    @PostConstruct
    public void initSensitiveWordMap() {
        SensitiveWordConditionDTO condition = new SensitiveWordConditionDTO();
        condition.setPageSize(PageConditionDTO.ALL_PAGE);

        List<SensitiveWordDO> SensitiveWordList =
                sensitiveWordMapper.searchByCondition(
                        condition);
        sensitiveWordMap = SensitiveWordList.stream()
                                                  .collect(
                                                          Collectors.toMap(
                                                                  SensitiveWordDO::getWord,
                                                                  SensitiveWordDO::getType,
                                                                  (existing, replacement) -> existing
                                                                  // 如果有重复的key，保留现有的value
                                                          )
                                                  );

        initSensitiveWordWhiteList();
    }

    /**
     * 初始化敏感词
     *
     * @param type     类型
     * @param filePath 文件路径
     */
    public Boolean initSensitiveWord(int type, String filePath) {
        List<SensitiveWordDO> sensitiveWordList = readSensitiveWordsFromFile(type, filePath);
        if (CollectionUtil.isEmpty(sensitiveWordList))
            return Boolean.FALSE;

        // TODO: 没有处理创建人信息, 这先写死
        sensitiveWordMapper.batchInsert(sensitiveWordList);
        return Boolean.TRUE;
    }

    @Override
    public List<SensitiveWordDO> searchByCondition(SensitiveWordConditionDTO condition) {
        return sensitiveWordMapper.searchByCondition(condition);
    }

    private List<SensitiveWordDO> readSensitiveWordsFromFile(int type, String filePath) {
        List<SensitiveWordDO> sensitiveWords = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            String sensitiveWord;
            while ((sensitiveWord = in.readLine()) != null) {
                SensitiveWordDO sensitiveWordDO = new SensitiveWordDO();
                sensitiveWordDO.setType(type);
                sensitiveWordDO.setWord(sensitiveWord);
                sensitiveWordDO.setCreateUserName(JobUserConst.DEFAULT_USER_NAME);
                sensitiveWordDO.setCreateUserId(JobUserConst.DEFAULT_USER_ID);

                sensitiveWords.add(sensitiveWordDO);
            }
            return sensitiveWords;
        } catch (Exception e) {
            log.error("读取敏感词文件失败, 原因: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * 防止敏感词误伤，初始化敏感词白名单
     *
     * @return void
     * @author : Tomatos
     * @date : 2025/9/11 16:18
     * @since : 1.0
     */
    private void initSensitiveWordWhiteList() {
        Resource resource = new ClassPathResource(sensitiveWordFilePath);
        try (Reader reader = new InputStreamReader(resource.getInputStream(),
                                                   StandardCharsets.UTF_8)) {
            BufferedReader in = new BufferedReader(reader);
            String word;
            while ((word = in.readLine()) != null) {
                sensitiveWordWhiteList.add(word);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 校验敏感词
     *
     * @param sensitiveWordDTO 敏感词对象
     */
    @Override
    public void checkSensitiveWord(SensitiveWordDTO sensitiveWordDTO) {
        String word = sensitiveWordDTO.getWord();
        AsserUtil.notNull(sensitiveWordDTO.getWord(), "待校验文本不能为空");

        if (sensitiveWordWhiteList.contains(word)) {
            return;
        }

        List<String> sensitiveList = segment(word).stream()
                                                  .filter(sensitiveWordMap::containsKey)
                                                  .collect(Collectors.toList());

        AsserUtil.isNull(sensitiveList, String.format("您输入的内容，包含敏感词：%s",
                                                      sensitiveList));
    }

    /**
     * 分词
     *
     * @param text 待分词文本
     * @return 分词结果
     */
    private List<String> segment(String text) {
        Segment segment = HanLP.newSegment()
                               .enableCustomDictionary(true);
        return segment.seg(text)
                      .stream()
                      .map(term -> term.word)
                      .collect(Collectors.toList());
    }
}