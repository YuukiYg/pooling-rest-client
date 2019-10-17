/**
 * @(#)SendRequestServiceImpl.java
 */
package com.yuukiyg.poolingrestclient.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * [TODO write an overview of the class]<br>
 * <p>
 * [TODO write a specification of the class]
 * </p>
 */
@Component
public class SendRequestServiceImpl implements SendRequestService{

    @Autowired
    RestTemplate restTemplate;

    @Value("${targetserver.port}")
    int serverPort;

    @Override
    public void execute() {

        // 100並列の非同期実行
        for (int i = 0; i < 100; i++) {
            String threadName = Integer.toString(i);

            new Thread(new Runnable() {
                @Override
                public void run() {

                    URI uri = URI.create("http://localhost:"+serverPort+"/wait");
                    UriComponentsBuilder builder = UriComponentsBuilder.fromUri(uri);
                    MultiValueMap<String, String> urlParameters = new LinkedMultiValueMap<String, String>();
                    urlParameters.add("time", "10000");
                    urlParameters.add("name", threadName);
                    builder.queryParams(urlParameters);
                    RequestEntity<String> request = new RequestEntity<String>(HttpMethod.GET, builder
                            .build().toUri());

                    // ↓最大同時接続数が10に制限されたRestTemplateを使ってREST送信。
                    // 10を超えたものは待ち行列に入るはず。
                    ResponseEntity<String> ret = restTemplate.exchange(request,
                            String.class);
                }
            }).start();
        }
    }

}
