package com.it.config;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.fastjson.JSON;
import com.it.domain.ResponseData;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 对各种规则的异常进行设置自定义页面
 */
@Component
public class ExceptionHandlerPage implements UrlBlockHandler {

    /**
     * BlockException 异常接口,包含Sentinel的五个异常4.7 @SentinelResource的使用
     * 在定义了资源点之后，我们可以通过Dashboard来设置限流和降级策略来对资源点进行保护。同时还能通过@SentinelResource来指定出现异常时的处理策略。
     * @SentinelResource 用于定义资源，并提供可选的异常处理和 fallback 配置项。其主要参数如下:
     * FlowException 限流异常
     * DegradeException 降级异常
     * ParamFlowException 参数限流异常
     * AuthorityException 授权异常
     * SystemBlockException 系统负载异常
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @throws IOException
     */
    @Override
    public void blocked(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws IOException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        ResponseData data = null;
        if (e instanceof FlowException) {
            data = new ResponseData(-1, "接口被限流了...");
        } else if (e instanceof DegradeException) {
            data = new ResponseData(-2, "接口被降级了...");
        }
        httpServletResponse.getWriter().write(JSON.toJSONString(data));
    }
}
