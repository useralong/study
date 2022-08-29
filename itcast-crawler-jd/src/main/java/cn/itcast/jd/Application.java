package cn.itcast.jd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @SpringBootApplication Springboot启动类启动注解，不加无法启动
 * @EnableScheduling springboot使用定时任务，需要在启动类添加此注册，并开启定时任务
 */
@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
