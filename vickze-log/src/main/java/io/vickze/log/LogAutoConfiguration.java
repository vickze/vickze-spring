package io.vickze.log;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author vick.zeng
 * date-time: 2018/11/16 17:11
 **/
@Configuration
@EnableConfigurationProperties(LogProperties.class)
@ConditionalOnProperty(name = {"spring.log.enabled"}, matchIfMissing = true)
public class LogAutoConfiguration {

    @Bean
    public LogFilter logFilter(LogProperties logProperties) {
        return new LogFilter(logProperties);
    }
}
