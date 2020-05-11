package com.example.springreactor.service;

import com.example.springreactor.model.GreetingRequest;
import com.example.springreactor.model.GreetingResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class GreetingService {

    public Flux<GreetingResponse> greetMany(GreetingRequest request) {
        return Flux
                .fromStream(Stream.generate(() -> greet(request.getName())))
                .delayElements(Duration.ofSeconds(1))
                .subscribeOn(Schedulers.elastic());
    }

    public Mono<GreetingResponse> greet(GreetingRequest request) {
        return Mono.just(greet(request.getName()));
    }

    private GreetingResponse greet(String name) {
        return new GreetingResponse("Hello " + name + " @ " + Instant.now());
    }
}
