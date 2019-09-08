package com.qf.wechat_netty;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
        * @user ken
        * @date 2019/8/6 14:19
        */
@Component
public class RabbitMqConfig {

    @Value("${netty.ip}")
    private String ip;
    @Value("${netty.port}")
    private int port;


    @Bean
    public FanoutExchange getFanoutExchange(){
        return new FanoutExchange("netty_exchange");
    }

    @Bean
    public Queue getQueue(){
        return new Queue("netty_queue_" + ip + "_" + port);
    }

    @Bean
    public Binding getBinding(FanoutExchange getFanoutExchange, Queue getQueue){
        return BindingBuilder.bind(getQueue).to(getFanoutExchange);
    }

}
