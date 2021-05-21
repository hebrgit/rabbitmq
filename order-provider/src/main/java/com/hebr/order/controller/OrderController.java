package com.hebr.order.controller;

import com.hebr.order.entity.Order;
import com.hebr.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName OrderController
 * @Author hebo
 * @Date 2021/5/20 22:44
 **/
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/order")
    public String order(){
        Order order = new Order();
        order.setCommodityCode("NS-CS");
        order.setCount(10);
        order.setMoney(100);
        order.setUserId("张三");

        orderService.order(order);

        return "success";
    }

}
