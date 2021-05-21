package com.hebo.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName Producer
 * @Author hebo
 * @Date 2021/5/17 16:33
 * @deprecated  发布订阅模式
 **/
public class Producer {

    public static void main(String[] args) {

        //1、设置参数，创建链接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("8.140.168.229");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = null;

        try {
            //建立连接
            connection = factory.newConnection();
            //创建通道
            Channel channel = connection.createChannel();

            String exchangeName ="com.hebr.fanout";
            String routingKey = "";
            String message = "send message!!!";
            channel.basicPublish(exchangeName,routingKey,null,message.getBytes());
            System.out.println("发送消息成功");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            if (connection != null){
                try {
                    connection.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
