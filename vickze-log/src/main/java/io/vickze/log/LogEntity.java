package io.vickze.log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Map;

@Data
public class LogEntity {

    private String httpMethod;

    private String httpUrl;

    private String httpUri;

    private String requestHeader;

    private String requestBody;

    private int responseStatus;

    private String responseHeader;

    private String responseBody;

    @JsonIgnore
    private Exception exception;

    private String start;

    private String end;

    private long duration;

    private Map<String, String> custom;
}
