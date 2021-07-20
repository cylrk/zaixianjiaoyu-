package com.chaohu.service.serviceTea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.chaohu"})
@EnableDiscoveryClient//注册nacos
@EnableFeignClients//调用
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
