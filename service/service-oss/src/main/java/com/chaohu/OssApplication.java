package com.chaohu;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.chaohu"})
@EnableDiscoveryClient//注册nacos
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class);
    }
}
