package io.vickze.sba;


import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author vick.zeng
 * date-time: 2018/11/16 17:20
 **/
@EnableAdminServer
@SpringBootApplication
public class SbaApplication {
    public static void main(String[] args) {
        SpringApplication.run(SbaApplication.class, args);
    }
}
