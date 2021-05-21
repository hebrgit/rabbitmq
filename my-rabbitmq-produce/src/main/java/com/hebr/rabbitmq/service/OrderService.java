package com.hebr.rabbitmq.service;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @ClassName OrderService
 * @Author hebo
 * @Date 2021/5/18 23:37
 **/
@Service
public class OrderService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发布订阅模式 测试方法
     */
    public void order(){

        String uuid = UUID.randomUUID().toString();
        System.out.println("发送消息："+uuid);

        rabbitTemplate.convertAndSend("com.exchange.fanout","",uuid);
        System.out.println("消息发送成功");
    }

    /**
     * 队列设置过期时间 测试方法
     */
    public void order1(){

        String uuid = UUID.randomUUID().toString();
        System.out.println("发送消息："+uuid);

        rabbitTemplate.convertAndSend("com.exchange.ttlFanout","",uuid);
        System.out.println("消息发送成功");
    }

    /**
     * 消息设置过期时间 测试方法
     */
    public void order2(){

        String uuid = UUID.randomUUID().toString();
        System.out.println("发送消息："+uuid);
        MessagePostProcessor properties = message -> {
            message.getMessageProperties().setExpiration("5000");
            return message;
        };
        rabbitTemplate.convertAndSend("com.exchange.ttlMessageFanout","",uuid,properties);
        System.out.println("消息发送成功");
    }
}
