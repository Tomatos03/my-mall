package com.mall.common.domain.template;

import cn.hutool.core.collection.CollectionUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import com.mall.common.context.SpringBeanHolder;
import com.mall.entity.EsCommonDO;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * ES template
 *
 * @author 苏三，该项目是知识星球：java突击队 的内部项目
 * @date 2024/5/14 下午4:30
 */
@Slf4j
public class EsTemplate {
    private static ElasticsearchClient client() {
        return SpringBeanHolder.getBean(ElasticsearchClient.class);
    }

    /**
     * 添加数据到ES
     *
     * @param indexName 索引名称
     * @param esCommonDO 数据对象
     * @return boolean
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/9/9 12:47
     */
    public static boolean insert(String indexName, EsCommonDO esCommonDO) {
        IndexRequest<Map<String, Object>> indexRequest = IndexRequest.of(builder -> builder
                .index(indexName)
                .id(esCommonDO.getId())
                .document(esCommonDO.getData())
        );

        try {
            IndexResponse response = client().index(indexRequest);
            return response.result() == Result.Created || response.result() == Result.Updated;
        } catch (IOException e) {
            log.error("写入ES失败，原因：{}", e.getMessage());
            return false;
        }
    }

    /**
     * 批量添加数据到ES
     *
     * @param indexName      索引名称
     * @param esCommonList 数据集合
     */
    public static void batchInsert(String indexName, List<EsCommonDO> esCommonList) {
        if (CollectionUtil.isEmpty(esCommonList)) {
            return;
        }

        List<BulkOperation> operations = esCommonList.stream()
                                                     .map(esCommonDO ->
                                                                  BulkOperation.of(
                                                                          builder -> builder
                                                                                  .index(idxBuilder -> idxBuilder
                                                                                          .index(indexName)
                                                                                          .id(esCommonDO.getId())
                                                                                          .document(
                                                                                                  esCommonDO.getData())
                                                                                  )
                                                                  )
                                                     )
                                                     .toList();

        BulkRequest request = BulkRequest.of(builder ->
                                                     builder.operations(operations).
                                                            refresh(Refresh.WaitFor)
        );

        try {
            BulkResponse bulk = client().bulk(request);
        } catch (IOException e) {
            log.error("批量写入ES失败，原因：{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //    /**
    //     * 查询数据
    //     *
    //     * @param idxName index
    //     * @param builder 查询参数
    //     * @param aClass  结果类对象
    //     * @return java.util.List<T>
    //     */
    public <T> List<T> query(String idxName, Class<T> aClass) {
        SearchRequest searchRequest = SearchRequest.of(builder -> builder.index(idxName));
//        client().get()
        //        SearchRequest request = new SearchRequest(idxName);
        //        request.source(builder);
        //        SearchResponse response = restHighLevelClient.search(request, RequestOptions
        //        .DEFAULT);
        //        SearchHit[] hits = response.getHits().getHits();
        //        return Arrays.stream(hits).map(hit -> JSON.parseObject(hit.getSourceAsString(),
        //        aClass)).collect(Collectors.toList());
        return null;
    }
}