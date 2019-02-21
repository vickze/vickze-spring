package io.vickze.zuul.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
public class ZuulSwaggerResourcesProvider implements SwaggerResourcesProvider {
    public static final String API_URI = "/v2/api-docs";

    @Autowired
    private ZuulProperties zuulProperties;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        zuulProperties.getRoutes().values().stream().forEach(route -> resources
                                .add(swaggerResource(route.getServiceId(), route.getPath().replace("/**", API_URI))));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}