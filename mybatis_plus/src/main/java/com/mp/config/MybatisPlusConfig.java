package com.mp.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.mp.mapper") // 将启动类上的扫描 mapper 目录的注解拿过来
@EnableTransactionManagement // 自动管理事务
@Configuration // 代表配置类
public class MybatisPlusConfig {

    // 注解乐观锁插件（旧版本方式）
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    // 分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页面后操作， true 调回首页阿，false 继续请求
        //paginationInterceptor.setOverflow(false);
        return paginationInterceptor;
    }

    // 逻辑删除组件
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    //SQL 执行效率插件
    @Bean
    @Profile({"dev","test"}) // 设置 dev test 开发、测试环境开启，保证效率
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        // 在工作中，不允许用户等待
        performanceInterceptor.setMaxTime(100); // ms   设置sql 执行的最大时间，超过则不执行
        performanceInterceptor.setFormat(true); // 开启格式化
        return performanceInterceptor;
    }

}
