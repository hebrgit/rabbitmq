package com.hebr.order.config;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;

/**
 * @ClassName RabbitMqConfirmConfiguration
 * @Author hebo
 * @Date 2021/5/20 16:11
 * @desc
 **/
@Configuration
@Slf4j
public class RabbitMqUtil {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @PostConstruct
    public void confirm(){
        rabbitTemplate.setConfirmCallback((correlationData, ack, s) -> {
            log.info("异常信息:{}",s);
            String id = correlationData.getId();
            if (ack){
                //发送成功
                String updateSql = "update order_tbl_copy1 set status = 0 where id = ?";
                jdbcTemplate.update(updateSql,id);
                log.info("生产着发送消息到交换机成功");
            }else {
                String updateSql = "update order_tbl_copy1 set status = 1 where id = ?";
                jdbcTemplate.update(updateSql,id);
                log.info("生产着发送消息到交换机失败");
            }
        });
    }

    public void sendMessage(Object obj,String id){
        CorrelationData data = new CorrelationData(id);
        String jsonString = JSONObject.toJSONString(obj);

        try {
            log.info("发送消息到交换机");
            rabbitTemplate.convertAndSend("com.order.exChange.01","",jsonString,data);
        } catch (AmqpException e) {
            e.printStackTrace();
            log.info("消息发送失败");
        }
    }
}
