package com.hebr.order.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName OrderRabbitMqConfiguration
 * @Author hebo
 * @Date 2021/5/20 15:58
 * @desc 订单队列配置类
 **/
@Configuration
public class OrderRabbitMqConfiguration {
    @Bean
    public FanoutExchange fanoutExchange(){
        FanoutExchange fanoutExchange = new FanoutExchange("com.order.exChange.01",true,false);
        return  fanoutExchange;
    }
    @Bean
    public Queue queue1(){
        Map map = new HashMap(2);
        map.put("x-message-ttl",5000);
        map.put("x-dead-letter-exchange","com.exChange.dead.01");
        Queue queue = new Queue("order.queue.01",true,false,false,map);
        return queue;
    }
    @Bean
    public Binding binding (){
        Binding binding = BindingBuilder.bind(queue1()).to(fanoutExchange());
        return binding;
    }
}
