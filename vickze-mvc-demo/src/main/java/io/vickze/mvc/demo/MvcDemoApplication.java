package io.vickze.mvc.demo;

import io.vickze.log.EnableLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author vick.zeng
 * date-time: 2018/11/16 17:20
 **/
@EnableLog
@SpringBootApplication
public class MvcDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MvcDemoApplication.class, args);
    }
}
