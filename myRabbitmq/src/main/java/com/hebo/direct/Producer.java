package com.hebo.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName Producer
 * @Author hebo
 * @Date 2021/5/17 16:33
 * @deprecated  路由模式
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
        Channel channel = null;
        try {
            //建立连接
            connection = factory.newConnection();
            //创建通道
             channel = connection.createChannel();

            String exchangeName ="com.hebr.direct";
            String routingKey = "email";
            String message = "send message!!!";
            channel.basicPublish(exchangeName,routingKey,null,message.getBytes());
            System.out.println("发送消息成功");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
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
