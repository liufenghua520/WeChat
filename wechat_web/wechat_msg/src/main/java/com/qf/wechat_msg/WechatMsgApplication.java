package com.qf.wechat_msg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@SpringBootApplication(scanBasePackages = "com.qf")
@MapperScan("com.qf.dao")
@EnableFeignClients(basePackages = "com.qf")
public class WechatMsgApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatMsgApplication.class, args);
    }

}
