package io.vickze.mvc.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author vick.zeng
 * date-time: 2018/11/16 17:19
 **/
@Slf4j
@RestController
public class DemoController {

    @GetMapping("/test")
    public Map<Object, Object> test() {
        log.info("test");
        return new HashMap<>();
    }


    @GetMapping("/error")
    public String error() {
        throw new NullPointerException("Can not be null");
    }
}
