package com.example.springreactor;

import com.example.springreactor.model.GreetingRequest;
import com.example.springreactor.model.GreetingResponse;
import com.example.springreactor.service.GreetingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
@EnableTransactionManagement
public class SpringReactorApplication {

    @Bean
    RouterFunction<ServerResponse> routs(GreetingService gs) {
        return route()
                .GET("/greeting/{name}", r ->
                        ok().body(gs.greet(new GreetingRequest(r.pathVariable("name"))), GreetingResponse.class))
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringReactorApplication.class, args);
    }

}