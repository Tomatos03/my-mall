package com.mall.business.service.common;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/10
 */

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Maps;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.mall.api.service.ICommonSensitiveWordService;
import com.mall.business.mapper.common.CommonSensitiveWordMapper;
import com.mall.common.util.AsserUtil;
import com.mall.constant.JobUserConst;
import com.mall.dto.condition.mall.CommonSensitiveWordConditionDTO;
import com.mall.dto.condition.sys.PageConditionDTO;
import com.mall.entity.CommonSensitiveWordDO;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommonSensitiveWordService implements ICommonSensitiveWordService {
    @Autowired
    private CommonSensitiveWordMapper commonSensitiveWordMapper;

    private Map<String, Integer> sensitiveWordMap;

    @Value("${my-mall.sensitive-word.file-path}")
    private String sensitiveWordFilePath;

    @PostConstruct
    public void initSensitiveWordMap() {
        CommonSensitiveWordConditionDTO condition = new CommonSensitiveWordConditionDTO();
        condition.setPageSize(PageConditionDTO.ALL_PAGE);

        List<CommonSensitiveWordDO> commonSensitiveWordList =
                commonSensitiveWordMapper.searchByCondition(
                        condition);
        sensitiveWordMap = commonSensitiveWordList.stream()
                                                  .collect(
                                                          Collectors.toMap(
                                                                  CommonSensitiveWordDO::getWord,
                                                                  CommonSensitiveWordDO::getType,
                                                                  (existing, replacement) -> existing
                                                                  // 如果有重复的key，保留现有的value
                                                          )
                                                  );

        initCustomHanLPDictionary();
    }

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

    @Override
    public List<CommonSensitiveWordDO> searchByCondition(CommonSensitiveWordConditionDTO condition) {
        return commonSensitiveWordMapper.searchByCondition(condition);
    }

    private List<CommonSensitiveWordDO> readSensitiveWordsFromFile(int type, String filePath) {
        List<CommonSensitiveWordDO> sensitiveWords = new ArrayList<>();
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

    /**
     * 初始化自定义HanLP词典, 该词典赋值HanLP分词使用
     *
     * @return void
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/9/11 16:18
     */
    private void initCustomHanLPDictionary() {
        Resource resource = new ClassPathResource(sensitiveWordFilePath);
        try (Reader reader = new InputStreamReader(resource.getInputStream(),
                                                  StandardCharsets.UTF_8)) {
            BufferedReader in = new BufferedReader(reader);
            String customWord;
            while ((customWord = in.readLine()) != null) {
                CustomDictionary.add(customWord);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * 校验敏感词
     *
     * @param sensitiveWordDO 敏感词对象
     */
    @Override
    public void checkSensitiveWord(CommonSensitiveWordDO sensitiveWordDO) {
        AsserUtil.notNull(sensitiveWordDO.getWord(), "待校验文本不能为空");

        List<String> matchList = new ArrayList<>();
        segment(sensitiveWordDO.getWord()).forEach((word, nature) -> {
            if (sensitiveWordMap.containsKey(word)) {
                matchList.add(word);
            }
        });

        AsserUtil.isNull(matchList, String.format("您输入的内容，包含敏感词：%s",
                                                  matchList));
    }

    /**
     * 分词
     *
     * @param text 待分词文本
     * @return
     */
    private Map<String, String> segment(String text) {

        Segment segment = HanLP.newSegment()
                               .enableCustomDictionary(true);
        List<Term> termList = segment.seg(text);

        Map<String, String> wordMap = Maps.newHashMap();
        for (Term term : termList) {
            String word = term.toString()
                              .substring(0, term.length()); // 获取词语
            String nature = term.toString()
                                .substring(term.length() + 1); // 获取词性

            if (StringUtils.hasLength(word) && StringUtils.hasLength(nature)) {
                wordMap.put(word, nature);
            }
        }
        return wordMap;
    }
}