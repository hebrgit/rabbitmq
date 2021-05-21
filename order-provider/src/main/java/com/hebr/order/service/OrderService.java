package com.hebr.order.service;

import com.hebr.order.config.RabbitMqUtil;
import com.hebr.order.entity.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * @ClassName OrderService
 * @Author hebo
 * @Date 2021/5/20 15:56
 **/
@Service
public class OrderService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private RabbitMqUtil rabbitMqUtil;

    public void order(Order order){

        String id = UUID.randomUUID().toString();
        order.setId(id);
        //定义sql
        String insertSql = "insert into order_tbl(id,user_id,commodity_code,count,money) values (?,?,?,?,?)";

        //添加订单记录
        int num = jdbcTemplate.update(insertSql, id, "zhangsan", order.getCommodityCode(), order.getCount(), order.getMoney());

        //添加到冗余数据库
        insertOrder(order);

        if (num != 1){
            System.out.println("订单创建失败");
        }

        //扣除库存
        rabbitMqUtil.sendMessage(order,order.getId());

    }


    private void insertOrder(Order order){
        Date date = new Date();
        //定义sql
        String insertSql = "insert into order_tbl_copy1(id,user_id,commodity_code,count,money,status,retry_num,date) values (?,?,?,?,?,?,?,?)";
        //添加订单记录
        int num = jdbcTemplate.update(insertSql, order.getId(), "zhangsan", order.getCommodityCode(), order.getCount(), order.getMoney(),2,0,date);

        if (num != 1){
            System.out.println("订单创建失败");
        }
    }

}
