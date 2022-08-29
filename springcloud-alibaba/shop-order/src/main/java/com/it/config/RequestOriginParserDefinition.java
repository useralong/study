package com.it.config;

import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class RequestOriginParserDefinition implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest httpServletRequest) {
        String serviceName = httpServletRequest.getParameter("serviceName");
//        if(StringUtils.isEmpty(serviceName)){
//            throw new RuntimeException("serviceName is not empty!");
//        }
        return serviceName;
    }
}
