package com.qf.wechat_user;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @user ken
 * @date 2019/8/6 14:15
 */
@Component
public class RabbitMqConfig {

    @Bean
    public FanoutExchange getFanoutExchange(){
        return new FanoutExchange("netty_exchange");
    }
}
