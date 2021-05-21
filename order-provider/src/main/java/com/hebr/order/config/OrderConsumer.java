package com.hebr.order.config;

import com.alibaba.fastjson.JSONObject;
import com.hebr.order.entity.Order;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.connection.RabbitUtils;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Header;

import java.io.IOException;

/**
 * @ClassName OrderConsumer
 * @Author hebo
 * @Date 2021/5/20 23:36
 **/
@Configuration
@Slf4j
public class OrderConsumer {

    @Autowired
    private RabbitMqUtil rabbitUtil;
    @RabbitListener(queues = "dead.queue.01")
    public void consume(String message, Channel channel, CorrelationData data, @Header(AmqpHeaders.DELIVERY_TAG)long tag){

        try {
            log.info("重新发送死信队列的消息：{}",message);
            Order order = JSONObject.parseObject(message, Order.class);
            rabbitUtil.sendMessage(message,order.getId());
            channel.basicAck(tag,false);
        } catch (Exception e) {
            //人工干预
            System.out.println("重新发送死信队列的消息异常");
        }
    }
}
