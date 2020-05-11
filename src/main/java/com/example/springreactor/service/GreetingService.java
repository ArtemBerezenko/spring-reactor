package com.example.springreactor.service;

import com.example.springreactor.model.GreetingRequest;
import com.example.springreactor.model.GreetingResponse;
import java.time.Instant;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GreetingService {

    public Mono<GreetingResponse> greet(GreetingRequest request) {
        return Mono.just(greet(request.getName()));
    }

    private GreetingResponse greet(String name) {
        return new GreetingResponse("Hello " + name + " @ " + Instant.now());
    }
}
