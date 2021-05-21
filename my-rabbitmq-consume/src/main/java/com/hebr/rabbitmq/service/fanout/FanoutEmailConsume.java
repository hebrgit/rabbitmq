package com.hebr.rabbitmq.service.fanout;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName FanoutEmailConsume
 * @Author hebo
 * @Date 2021/5/18 23:55
 **/
@RabbitListener(queues = {"email.queue"})
//@Component
public class FanoutEmailConsume {


    @RabbitHandler
    public void consume(String message){
        System.out.println("email.queue接收到消息："+message);
    }
}
