package com.mall.common.config;

import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.mall.common.properties.EsProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/8
 */
@Slf4j
@Configuration
public class EsConfig {
    @Autowired
    private EsProperties esProperties;

    private final String DEFAULT_SCHEME = "https";

    @Bean
    public RestClient restClient() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        HttpHost[] hosts = Arrays.stream(esProperties.getHost()
                                                     .split(","))
                                 .map(host -> new HttpHost(host, esProperties.getPort(), DEFAULT_SCHEME))
                                 .toArray(HttpHost[]::new);

        RestClientBuilder builder = RestClient.builder(hosts);

        String username = esProperties.getUsername();
        String password = esProperties.getPassword();
        if (StrUtil.isNotBlank(username)) {
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(
                    AuthScope.ANY,
                    new UsernamePasswordCredentials(username, password));


            // TODO 生产环境请删除 trust-all 逻辑，改用导入证书
            SSLContext sslContext = SSLContexts.custom()
                                               .loadTrustMaterial(null, (chain, authType) -> true)
                                               .build();

            builder.setHttpClientConfigCallback(
                    httpClientBuilder ->
                            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                                             .setSSLContext(sslContext)
                                             .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
            );
        }

        return builder.build();
    }

    @Bean
    public ElasticsearchClient elasticsearchClient(RestClient restClient) {
        ElasticsearchTransport elasticsearchTransport = new RestClientTransport(restClient,
                                                                                new JacksonJsonpMapper());
        return new ElasticsearchClient(elasticsearchTransport);
    }
}
