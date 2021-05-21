package com.hebr.order.task;

import com.alibaba.fastjson.JSONObject;
import com.hebr.order.config.RabbitMqUtil;
import com.hebr.order.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName MqTask
 * @Author hebo
 * @Date 2021/5/20 21:00
 **/
@Configuration
@EnableScheduling
@Slf4j
public class MqTask {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RabbitMqUtil rabbitMqUtil;


    @Scheduled(cron = "*/5 * * * * ?")
    public void sendMessage(){
        log.info("执行定时任务");
            String stringSql  = "select * from order_tbl_copy1 where retry_num < 3 and status = 1";

            RowMapper<Order> rowMapper = new RowMapper<Order>() {
                @Override
                public Order mapRow(ResultSet resultSet, int i) throws SQLException {

                    String id = resultSet.getString("id");
                    String userId = resultSet.getString("user_id");
                    String commodityCode = resultSet.getString("commodity_code");
                    int count = resultSet.getInt("count");
                    int money = resultSet.getInt("money");
                    int retryNum = resultSet.getInt("retry_num");
                    Order order = new Order();
                    order.setUserId(userId);
                    order.setMoney(money);
                    order.setCommodityCode(commodityCode);
                    order.setCount(count);
                    order.setId(id);
                    order.setRetryNum(retryNum+1);
                    return order;
                }
            };
        List<Order> orders = jdbcTemplate.query(stringSql, rowMapper);

        String updateSql = "update order set retry_num = ? where id = ?";

        log.info("orders:{}",orders);
        if (!CollectionUtils.isEmpty(orders)){
            orders.forEach(x->{
                jdbcTemplate.update(updateSql,x.getRetryNum(),x.getId());
                rabbitMqUtil.sendMessage(x,x.getId());
            });
        }
    }
}
