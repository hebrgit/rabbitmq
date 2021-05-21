package com.hebr.order.entity;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName Order
 * @Author hebo
 * @Date 2021/5/20 18:19
 **/
@Data
public class Order {

    private String id;
    private String userId;
    /**
     * 订单编号
     */
    private String commodityCode;
    private int count;
    private int money;
    /**
     * //重试次数
     */
    private int retryNum;

    private Date date;
}
