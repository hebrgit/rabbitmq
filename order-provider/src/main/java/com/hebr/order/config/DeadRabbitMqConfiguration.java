package com.hebr.order.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @ClassName TtlRabbitMqConfiguration
 * @Author hebo
 * @Date 2021/5/20 16:04
 * @desc 死信队列配置信息
 **/
@Configuration
public class DeadRabbitMqConfiguration {

    @Bean
    public FanoutExchange deadExchange(){
        FanoutExchange fanoutExchange = new FanoutExchange("com.exChange.dead.01",true,false);
        return  fanoutExchange;
    }
    @Bean
    public Queue deadQueue(){
        Queue queue = new Queue("dead.queue.01",true);
        return queue;
    }
    @Bean
    public Binding ttlBinding (){
        Binding binding = BindingBuilder.bind(deadQueue()).to(deadExchange());
        return binding;
    }
}
