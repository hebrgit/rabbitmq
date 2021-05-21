package com.hebr.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @ClassName RabbitConfig
 * @Author hebo
 * @Date 2021/5/18 23:39
 * @description  队列设置过期时间 配置类
 **/
@Configuration
public class DeadRabbitConfiguration {

    @Bean
    public FanoutExchange DeadExchange(){
        return new FanoutExchange("com.exchange.dead",true,false);
    }

    @Bean
    public Queue DeadQueue(){
        return new Queue("dead.queue",true);
    }

    @Bean
    public Binding DeadBinging(){
        return BindingBuilder.bind(DeadQueue()).to(DeadExchange());
    }



}
