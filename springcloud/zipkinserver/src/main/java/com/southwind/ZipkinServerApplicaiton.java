package com.southwind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.internal.EnableZipkinServer;

/**
 * @SpringBootApplication 启动类注解
 * @EnableZipkinServer 声明启动Zipkin Server注解
 */
@SpringBootApplication
@EnableZipkinServer
public class ZipkinServerApplicaiton {
    public static void main(String[] args) {
        SpringApplication.run(ZipkinServerApplicaiton.class,args);
    }
}
