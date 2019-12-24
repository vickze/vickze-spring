package io.vickze.log;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.*;

/**
 * @author vick.zeng
 * date-time: 2018/11/16 15:55
 **/
@Data
@RefreshScope
@ConfigurationProperties("spring.log")
public class LogProperties {

    private boolean enabled;

    private boolean enabledRequest;

    private boolean enabledResponse;

    private Set<String> requestIgnoreContextType = new HashSet<>();

    private Set<String> responseAcceptContextType = new HashSet<>();

    private Set<String> ignoreUrl = new HashSet<>();

    private Map<String, String> custom = new HashMap<>();

}
