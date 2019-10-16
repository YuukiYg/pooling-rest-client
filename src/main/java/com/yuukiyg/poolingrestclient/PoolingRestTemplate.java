/**
 * @(#)RestTemplateBean.java
 */
package com.yuukiyg.poolingrestclient;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * [TODO write an overview of the class]<br>
 * <p>
 * [TODO write a specification of the class]
 * </p>
 */
@Configuration
public class PoolingRestTemplate {

    @Bean
    public RestTemplate restTemplate() {
        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();

        // コネクションプールのサイズ。
        // ↓この設定だと、最大同時接続数が10のRestTemplateを作れます。
        poolingConnectionManager.setMaxTotal(10);
        poolingConnectionManager.setDefaultMaxPerRoute(10);

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(poolingConnectionManager);
        HttpClient httpClient = httpClientBuilder.build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);
        return restTemplate;
    }

}
