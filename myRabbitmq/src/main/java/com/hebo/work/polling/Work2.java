package com.hebo.work.polling;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName Consumer
 * @Author hebo
 * @Date 2021/5/17 16:33
 **/
public class Work2 {

    public static void main(String[] args) {


        //1、设置参数
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("8.140.168.229");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = null;
        Channel channel = null;
        try {
            //2、创建链接
           connection  = factory.newConnection();
           //3、获取通道
             channel = connection.createChannel();
            //4、通过通道创建交换机，声明队列、绑定关系、路由key、发送消息、接收消息
            channel.basicConsume("queue1",true, new DeliverCallback() {
                public void handle(String s, Delivery delivery) throws IOException {
                    System.out.println("接收到消息：" + new String(delivery.getBody()));
                }
            }, new CancelCallback() {
                public void handle(String s) throws IOException {
                    System.out.println("消息接收失败");
                }
            });
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }finally {
            if (channel != null && channel.isOpen()){
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null && connection.isOpen()){
                try {
                    connection.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }


    }
}