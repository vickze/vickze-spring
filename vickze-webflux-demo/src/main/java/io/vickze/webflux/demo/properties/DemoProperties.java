package io.vickze.webflux.demo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@RefreshScope
@ConfigurationProperties(prefix = "demo")
@Component
public class DemoProperties {

    private String test;
}
