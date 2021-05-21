package com.hebr.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RabbitConfig
 * @Author hebo
 * @Date 2021/5/18 23:39
 **/
@Configuration
public class RabbitConfig {

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("com.exchange.fanout",true,false);
    }

    @Bean
    public Queue queue1(){
        return new Queue("sms.queue",true);
    }

    @Bean
    public Queue queue2(){
        return new Queue("email.queue",true);
    }

    @Bean
    public Queue queue3(){
        return new Queue("wei.queue",true);
    }

    @Bean
    public Binding binding1(){
        return BindingBuilder.bind(queue1()).to(fanoutExchange());
    }

    @Bean
    public Binding binding2(){
        return BindingBuilder.bind(queue2()).to(fanoutExchange());
    }

    @Bean
    public Binding binding3(){
        return BindingBuilder.bind(queue3()).to(fanoutExchange());
    }
}
