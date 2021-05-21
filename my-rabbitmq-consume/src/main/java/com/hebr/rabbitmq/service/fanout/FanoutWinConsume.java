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

@Component
public class FanoutWinConsume {


    @RabbitListener(queues = {"wei.queue"})
    public void consume(Message message){
        System.out.println("wei.queue 接收到消息："+message);
    }
}
