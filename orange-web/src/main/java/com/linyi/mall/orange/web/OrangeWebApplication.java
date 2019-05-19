package com.linyi.mall.orange.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@RefreshScope
@MapperScan("com.linyi.mall.orange.web.mapper")
@EnableTransactionManagement
public class OrangeWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrangeWebApplication.class, args);
    }

    @Value("${hello.world}")
    String helloWorld;

    @GetMapping("")
    public String hello(){
        return helloWorld;
    }

}
