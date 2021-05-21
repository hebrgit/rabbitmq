package com.hebo.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName Producer
 * @Author hebo
 * @Date 2021/5/17 16:32
 * @deprecated  主题模式
 **/
public class Producer {
    public static void main(String[] args) {

        //1、设置参数
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("8.140.168.229");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = null;
        try {
            //2、创建链接
            connection  = factory.newConnection("simple-producer");
            //3、获取通道
            Channel channel = connection.createChannel();
            //4、通过通道创建交换机，声明队列、绑定关系、路由key、发送消息、接收消息
            String exChangeName = "com.hebr.topic";
            String queueName = "hebo.sms";
            //发送消息
            String message = "send message";
            channel.basicPublish(exChangeName,queueName,null,message.getBytes());
            System.out.println("发送消息成功");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }finally {
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
