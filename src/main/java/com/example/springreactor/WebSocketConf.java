package com.example.springreactor;

import com.example.springreactor.model.GreetingRequest;
import com.example.springreactor.model.GreetingResponse;
import com.example.springreactor.service.GreetingService;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

@Log4j2
@Configuration
public class WebSocketConf {

    @Bean
    SimpleUrlHandlerMapping simpleUrlHandlerMapping(WebSocketHandler wsh) {
        return new SimpleUrlHandlerMapping(Map.of("/ws/greetings", wsh), 10);
    }

    @Bean
    WebSocketHandler webSocketHandler(GreetingService greetingService) {
        return session -> {
            var receive = session
                    .receive()
                    .map(WebSocketMessage::getPayloadAsText)
                    .map(GreetingRequest::new)
                    .flatMap(greetingService::greetMany)
                    .map(GreetingResponse::getMessage)
                    .map(session::textMessage)
                    .doOnEach(signal -> log.info(signal.getType()))
                    .doFinally(signal -> log.info("finally: " + signal.toString()));
            return session.send(receive);
        };
    }

    @Bean
    WebSocketHandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
