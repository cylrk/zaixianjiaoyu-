package com.chaohu.service.serviceEduCenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"com.chaohu"})
@EnableDiscoveryClient//注册nacos
@MapperScan("com.chaohu.service.serviceEduCenter.mapper")
public class EduCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduCenterApplication.class, args);
    }
}
