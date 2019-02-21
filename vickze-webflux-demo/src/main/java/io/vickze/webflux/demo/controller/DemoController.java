package io.vickze.webflux.demo.controller;

import io.vickze.webflux.demo.properties.DemoProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-01-18 17:03
 */
@Slf4j
@RefreshScope
@RestController
public class DemoController {

    @Autowired
    private DemoProperties demoProperties;

    @GetMapping("/mono")
    public Mono<Object> mono() {
        log.debug("/request");
        return Mono.just(new HashMap<>());
    }

    @GetMapping("/flux")
    public Flux<Object> flux() {
        return Flux.just(new HashMap<>());
    }

    @GetMapping("/config")
    public Mono<Object> config() {
        return Mono.just(demoProperties.getTest());
    }

}
