package com.it.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "gatewayremoveheaderconfig")
public class GatewayRemoveHeaderConfig {

    //路由ID
    private List<String> hello;
    private List<String> productRoute;

    public List<String> getHello() {
        return hello;
    }

    public void setHello(List<String> hello) {
        this.hello = hello;
    }

    public List<String> getProductRoute() {
        return productRoute;
    }

    public void setProductRoute(List<String> productRoute) {
        this.productRoute = productRoute;
    }

    @Override
    public String toString() {
        return "GatewayRemoveHeaderConfig{" +
                "hello=" + hello +
                ", productRoute=" + productRoute +
                '}';
    }
}
