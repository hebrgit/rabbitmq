package com.hebo.manual;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName Producer
 * @Author hebo
 * @Date 2021/5/17 16:32
 * @deprecated  代码方式创建 队列 和 交换机
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
        Channel channel = null;
        try {
            //2、创建链接
            connection  = factory.newConnection("simple-producer");
            //3、获取通道
       channel= connection.createChannel();
            //4、通过通道创建交换机，声明队列、绑定关系、路由key、发送消息、接收消息
            String exChangeName = "com.hebr.manual";
            //5、创建交换机
            /**
             * 交换机名称，交换机类型，是否持久化，是否自动删除，是否
             */
            channel.exchangeDeclare(exChangeName,"fanout",true,false,false,null);

            /**
             *queue: 队列的名称
             *
             * durable: 设置是否持久化, true表示队列为持久化, 持久化的队列会存盘, 在服务器重启的时候会保证不丢失相关信息
             *
             * exclusive: 设置是否排他, true表示队列为排他的, 如果一个队列被设置为排他队列, 该队列仅对首次声明它的连接可见, 并在连接断开时自动删除,
             * (这里需要注意三点:1.排他队列是基于连接Connection可见的, 同一个连接的不同信道Channel是可以同时访问同一连接创建的排他队列;"首次"是指如果一个连接己经声明了一个排他队列，其他连接是不允许建立同名的排他队列的，
             * 这个与普通队列不同;即使该队列是持久化的，一旦连接关闭或者客户端退出，该排他队列都会被自动删除，这种队列适用于一个客户端同时发送和读取消息的应用场景)
             *
             * autoDelete: 设置是否自动删除。为true 则设置队列为自动删除。自动删除的前提是, 至少有一个消费者连接到这个队列，之后所有与这个队列连接的消费者都断开时，才会自动删除。不能把这个参数错误地理解为:
             * "当连接到此队列的所有客户端断开时，这个队列自动删除"，因为生产者客户端创建这个队列，或者没有消费者客户端与这个队列连接时，都不会自动删除这个队列。
             *
             * arguments: 设置队列的其他一些参数, 如 x-message-ttl等
             */
            channel.queueDeclare("queue5",true,false,false,null);
            channel.queueDeclare("queue6",true,false,false,null);
            channel.queueDeclare("queue7",true,false,false,null);
            //绑定队列
            channel.queueBind("queue5",exChangeName,"");
            channel.queueBind("queue6",exChangeName,"");
            channel.queueBind("queue7",exChangeName,"");
            //发送消息
            String message = "send message";
            channel.basicPublish(exChangeName,"",null,message.getBytes());
            System.out.println("发送消息成功");
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
