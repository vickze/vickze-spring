package io.vickze.log;

import io.vickze.common.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author vick.zeng
 * date-time: 2018/11/16 15:53
 **/
@Slf4j
@AllArgsConstructor
public class LogFilter implements Filter {

    private LogProperties logProperties;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {
            throw new ServletException("LogFilter just supports HTTP requests");
        }
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (!logProperties.isEnabled()
                || logProperties.getIgnoreUrl().contains(request.getRequestURI())
                || logProperties.getRequestIgnoreContextType().contains(request.getContentType())) {
            filterChain.doFilter(request, response);
            return;
        }

        LocalDateTime start = LocalDateTime.now();
        LogEntity logEntity = LogUtil.get();
        if (logProperties.isEnabledRequest() && !(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }

        if (logProperties.isEnabledResponse() && !(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }
        logEntity.setHttpMethod(request.getMethod());
        logEntity.setHttpUrl(request.getRequestURL().toString());
        logEntity.setHttpUri(request.getRequestURI());

        if (logProperties.isEnabledRequest()) {
            logEntity.setRequestHeader(extractHeaders(request).toString());
            String requestBody = new String(((ContentCachingRequestWrapper) request).getContentAsByteArray(), request.getCharacterEncoding());
            logEntity.setResponseHeader(StringUtils.isEmpty(requestBody) ? JsonUtil.toJson(request.getParameterMap()) : requestBody);
        }

        try {
            filterChain.doFilter(request, response);
            if (logProperties.isEnabledResponse() && logProperties.getResponseAcceptContextType().contains(response.getContentType())) {
                logEntity.setResponseStatus(response.getStatus());
                logEntity.setResponseHeader(extractHeaders(response).toString());
                String responseBody = new String(((ContentCachingResponseWrapper) response).getContentAsByteArray(),
                        response.getCharacterEncoding());
                logEntity.setResponseBody(responseBody);
            }
        } catch (Exception e) {
            logEntity.setException(e);
            throw e;
        } finally {
            try {
                LocalDateTime end = LocalDateTime.now();
                logEntity.setStart(start.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                logEntity.setEnd(end.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                logEntity.setDuration(Duration.between(start, end).toMillis());
                logEntity.setCustom(logProperties.getCustom());

                LogUtil.log();
            } finally {
                ((ContentCachingResponseWrapper) response).copyBodyToResponse();
            }
        }
    }


    @Override
    public void destroy() {

    }

    private Map<String, List<String>> extractHeaders(HttpServletRequest request) {
        Map<String, List<String>> headers = new LinkedHashMap<>();
        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            headers.put(name, toList(request.getHeaders(name)));
        }
        return headers;
    }

    private Map<String, List<String>> extractHeaders(HttpServletResponse response) {
        Map<String, List<String>> headers = new LinkedHashMap<>();
        Collection<String> names = response.getHeaderNames();
        for (String name : names) {
            headers.put(name, toList(response.getHeaders(name)));
        }
        return headers;
    }

    private List<String> toList(Collection<String> headers) {
        return new ArrayList<>(headers);
    }

    private List<String> toList(Enumeration<String> enumeration) {
        List<String> list = new ArrayList<>();
        while (enumeration.hasMoreElements()) {
            list.add(enumeration.nextElement());
        }
        return list;
    }
}
