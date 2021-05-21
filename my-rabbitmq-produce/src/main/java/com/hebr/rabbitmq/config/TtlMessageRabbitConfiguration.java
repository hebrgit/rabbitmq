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
 * @description  消息设置过期时间 配置类
 **/
@Configuration
public class TtlMessageRabbitConfiguration {

    @Bean
    public FanoutExchange tTLMessageFanoutExchange(){
        return new FanoutExchange("com.exchange.ttlMessageFanout",true,false);
    }

    @Bean
    public Queue ttlMessageQueue(){
        return new Queue("ttlMessage.queue",true,false,false);
    }

    @Bean
    public Binding ttlMessageBinding(){
        return BindingBuilder.bind(ttlMessageQueue()).to(tTLMessageFanoutExchange());
    }

}
