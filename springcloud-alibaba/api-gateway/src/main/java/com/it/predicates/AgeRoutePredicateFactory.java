package com.it.predicates;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * AbstractRoutePredicateFactory<>泛型
 * 用于接收一个配置类，配置类用于接收中配置文件中的配置
 * 要求：
 * 1、名称必须是 断言名+RoutePredicateFactory
 * 2、必须继承AbstractRoutePredicateFactory<配置类>
 */
//@Component
public class AgeRoutePredicateFactory extends AbstractRoutePredicateFactory<AgeRoutePredicateFactory.Config> {

    /**
     * 构造函数
     */
    public AgeRoutePredicateFactory() {
        super(AgeRoutePredicateFactory.Config.class);
    }

    /**
     * 用于从配置文件中获取参数值赋值到配置类中的属性上
     * @return
     */
    @Override
    public List<String> shortcutFieldOrder() {
        //这里的顺序要跟配置文件中的参数顺序一致
        return Arrays.asList("minAge", "maxAge");
    }

    /**
     * 断言逻辑
     * @param config
     * @return
     */
    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return new Predicate<ServerWebExchange>() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                //从serverWebExchange获取传入的age参数
                String ageStr = serverWebExchange.getRequest().getQueryParams().getFirst("age");
                //不为空时进行路由逻辑判断
                if (StringUtils.isNotEmpty(ageStr)) {
                    int age = Integer.parseInt(ageStr);
                    return age >= config.getMinAge() && age <= config.getMaxAge();
                }
                //为空的时候，默认没有加age参数，让断言放行
                return true;
            }
        };
    }

    @Data
    @NoArgsConstructor  //无参构造
    public static class Config{
        private int minAge;
        private int maxAge;
    }

}
