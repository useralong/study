package com.hp.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
@MapperScan(value="com.hp.boot.dao")
public class Application {
	
	/**
	 * Spring Boot Application 启动入口方法
	 * 
	 */
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
	
	


	
	

}
