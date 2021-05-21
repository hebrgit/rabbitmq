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
public class TtlRabbitConfiguration {

    @Bean
    public FanoutExchange tTLfanoutExchange(){
        return new FanoutExchange("com.exchange.ttlFanout",true,false);
    }

    @Bean
    public Queue ttlQueue(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("x-message-ttl",5000);//设置过期时间
        map.put("x-dead-letter-exchange","com.exchange.dead");//绑定死信交换机
        return new Queue("ttl.queue",true,false,false,map);
    }

    @Bean
    public Binding ttlBinding(){
        return BindingBuilder.bind(ttlQueue()).to(tTLfanoutExchange());
    }



}
