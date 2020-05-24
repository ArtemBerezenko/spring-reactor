package com.example.springreactor.client;

import com.example.springreactor.model.GreetingResponse;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class Cient {

    private final WebClient webClient;
    private final ReactiveCircuitBreaker circuitBreaker;

    public Cient(WebClient webClient, ReactiveCircuitBreakerFactory cbf) {
        this.webClient = webClient;
        this.circuitBreaker = cbf.create("greeting");
    }

    @EventListener(ApplicationReadyEvent.class)
    public void ready(Cient cient) {

        Flux<String> http = cient.webClient
                .get()
                .uri("/greetings/{name}", "Spring users")
                .retrieve()
                .bodyToFlux(GreetingResponse.class)
                .map(GreetingResponse::getMessage);

        cient.webClient
                .get()
                .uri("/greeting/{name}", "Spring users")
                .retrieve()
                .bodyToMono(GreetingResponse.class)
                .map(GreetingResponse::getMessage)
                .retryBackoff(10, Duration.ofSeconds(1), Duration.ofSeconds(10), 1.3)
                .onErrorMap(throwable -> new IllegalArgumentException("the argument was wrong, exception was" + throwable.toString()))
                .onErrorResume(IllegalArgumentException.class, e -> Mono.just(e.toString()))
                .subscribe(s -> log.info("Mono: " + s));

        this.circuitBreaker
                .run(http, throwable -> Flux.just("EEEK!"))
                .subscribe(s -> log.info("Flux: " + s));


    }
}
