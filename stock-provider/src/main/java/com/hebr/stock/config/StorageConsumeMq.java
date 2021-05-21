package com.hebr.stock.config;

import com.alibaba.fastjson.JSONObject;
import com.hebr.order.entity.Order;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName StorageConsumeMq
 * @Author hebo
 * @Date 2021/5/20 18:09
 **/
@Component
@Slf4j
public class StorageConsumeMq {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @RabbitListener(queues = {"order.queue.01"})
    public void consume(String message, Channel channel, CorrelationData correlationData, @Header(AmqpHeaders.DELIVERY_TAG) long tag){

        try {
            log.info("接收到的消息信息：{}",message);
            Order order = JSONObject.parseObject(message, Order.class);

            String querySql = "select count from storage_tbl where commodity_code = ?";
            Map<String, Object> map = jdbcTemplate.queryForMap(querySql,order.getCommodityCode());
            int count = (int)map.get("count");
            String insertSql = "update storage_tbl set count = ? where commodity_code = ?";

            count = count-order.getCount();
            int result = jdbcTemplate.update(insertSql, count, order.getCommodityCode());
            if (result != 1){
                log.info("减库存失败：{}",order.toString());
            }
            channel.basicAck(tag,false);
        } catch (Exception e) {
            log.info("接收消息失败");
            try {
                channel.basicNack(tag,false,false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}
