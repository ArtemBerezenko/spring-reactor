package com.example.springreactor.controller;

import com.example.springreactor.model.GreetingRequest;
import com.example.springreactor.model.GreetingResponse;
import com.example.springreactor.service.GreetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class GreetingsController {

    private final GreetingService greetingService;

    @GetMapping("/greet/{name}")
    Mono<GreetingResponse> greet(@PathVariable String name) {
        return greetingService.greet(new GreetingRequest(name));
    }
}
