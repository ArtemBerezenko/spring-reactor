package com.example.springreactor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConf {

    @Bean
    WebClient webClient (WebClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8080")
//                .filter(ExchangeFilterFunctions.basicAuthentication())
                .build();
    }
}
