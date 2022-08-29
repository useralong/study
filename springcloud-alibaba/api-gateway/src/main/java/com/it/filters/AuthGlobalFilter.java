package com.it.filters;

import com.it.config.GatewayRemoveHeaderConfig;
import com.it.config.ResponseData;
import com.it.utils.JsonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 自定义全局过滤器需要实现GlobalFilter和Ordered接口
 * 统一鉴权使用
 */
@Component
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private GatewayRemoveHeaderConfig gatewayRemoveHeaderConfig;

    /**
     * 完成Auth的判断逻辑
     * @param exchange
     * @param chain
     * @return
     */
    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        //判断token是否为空，是否符合要求；拦截token不符合要求和为空情况
        if (StringUtils.isBlank(token) || !StringUtils.equals("admin",token) ) {
            log.info("认证失败！");
            System.out.println("鉴权失败"); exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        //获取当前路由并判断
        Route route = (Route) exchange.getAttributes().get(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        //判断路由是否为空
        if (route == null){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        HttpHeaders headers = exchange.getRequest().getHeaders();
        //针对header进行处理
        Set<Map.Entry<String, List<String>>> entries = removeHeader(headers, route).entrySet();
        Consumer<HttpHeaders> httpHeadersConsumer = header -> {
            //添加新的header和值
            header.add("x-test-header","1");
            //重置header和值
            header.set("x-test-header","2");
            //删除一些header或一个header
            for (Map.Entry<String, List<String>> entrie : entries){
                header.remove(entrie.getKey());
                //header.remove(entrie.getKey(),entrie.getValue());
                System.out.println("多余的header已经删除！");
            }
        };
        //httpHeadersConsumer不为空时把，对header的设置加入exchange中
        if(httpHeadersConsumer != null){
            //header的操作加入到exchange中后就开始对header进行操作了
            ServerHttpRequest build = exchange.getRequest().mutate().headers(httpHeadersConsumer).build();
            exchange = exchange.mutate().request(build).build();
        }
        //针对黑白名单ip的设置
        if(headers.get("ip").toString().equals("127.0.0.1")){
            ServerHttpResponse response = exchange.getResponse();
            //自定义一个相应实体
            ResponseData responseData = new ResponseData();
            responseData.setCode(401);
            responseData.setMessage("非法请求");
            //对象转json
            byte[] bytes = JsonUtil.objectToJsonStr(responseData).getBytes(StandardCharsets.UTF_8);
            //将byte字符数组撞到数据缓冲区
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().add("Content-Type","application/json;charset=UTF-8");
            //Mono API 响应写出（创建指定序列中的元素，发布后自动结束）
            return response.writeWith(Mono.just(buffer));
        }
        //调用chain.filter继续向下游执行
        return chain.filter(exchange);
    }

    /**
     * 顺序,返回的数值越小,优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }


    private HashMap<String, List<String>> removeHeader(HttpHeaders headers, Route route)throws IOException, IllegalAccessException {
        //获取header集合
        HashMap<String, List<String>> hashMap = new HashMap<>();
        Set<Map.Entry<String, List<String>>> entries = headers.entrySet();
        //用对象的反向映射，删除对应的header
        String pendingHeader = null;
        Field[] declaredFields = gatewayRemoveHeaderConfig.getClass().getDeclaredFields();
        for (Field f: declaredFields) {
            f.setAccessible(true); //类中的成员变量为private 故必须进行此操作
            String name = f.getName(); //获取字段的名称
            String id = route.getId(); //获取路由id
            //只有再匹配里找到当前路由id才能进行处理header
            if(StringUtils.equals(id,name)){
                //通过反向映射，得到gatewayconfig中对应路由id在yml文件中的配置，并做一个简单的处理
                pendingHeader = f.get(gatewayRemoveHeaderConfig).toString().replace("[", "").replace("]", "").replace(" ", "");
            }
        }
        for (Map.Entry<String, List<String>> entrie : entries){
            if(entrie.getKey() != null && entrie.getValue() != null){
                String key = entrie.getKey();
                if(pendingHeader != null && pendingHeader.contains(key)){
                    hashMap.put(key,entrie.getValue());
                }
            }
        }
        return hashMap;
    }



}
