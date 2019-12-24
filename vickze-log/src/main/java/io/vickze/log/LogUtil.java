package io.vickze.log;

import io.vickze.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import static net.logstash.logback.marker.Markers.*;


/**
 * @author vick.zeng
 * date-time: 2018/11/16 15:31
 **/
@Slf4j
public class LogUtil {

    private static ThreadLocal<LogEntity> logInfoThreadLocal = ThreadLocal.withInitial(LogEntity::new);


    public static LogEntity get() {
        LogEntity logEntity = new LogEntity();
        logInfoThreadLocal.set(logEntity);
        return logInfoThreadLocal.get();
    }

    public static void log() {
        LogEntity logEntity = logInfoThreadLocal.get();

        /*String message = MessageFormat.format("HTTP Method {0} ===> Request URI {1} ===> Response status {2} ===> Time cost {3}ms {4}{5}",
                logMap.get("http.method"), logMap.get("request.uri"), logMap.get("response.status"), logMap.get("duration"),
                System.getProperty("line.separator"), JsonUtil.toJson(logMap));*/
        String message = JsonUtil.toJson(logEntity);
        Exception exception = logEntity.getException();
        if (exception != null) {
            log.error(appendFields(logEntity), "request response error", logEntity.getException());
        } else {
            log.debug(appendFields(logEntity), "request response");
        }

        logInfoThreadLocal.remove();
    }
}
