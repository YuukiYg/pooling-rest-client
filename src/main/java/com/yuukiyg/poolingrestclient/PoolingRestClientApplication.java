package com.yuukiyg.poolingrestclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PoolingRestClientApplication {

    public static void main(String[] args) {
        // SpringApplication.run(PoolingRestClientApplication.class, args);

        ConfigurableApplicationContext context = SpringApplication.run(PoolingRestClientApplication.class, args);
        SendRequestService service = context.getBean(SendRequestService.class);
        service.execute();
    }
}
