package com.rabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description: 作用描述
 * @Author: Administrator
 * @CreateDate: 2020/6/28 9:41
 */
public class Production {
    private final static String QUEUE_NAME = "first_channel_01";//定义消息队列名称

    public static void main(String[] args) {
        //定义链接的工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("guest");
        factory.setPassword("guest");
        try {
            //获取链接
            Connection connection = factory.newConnection();
            //创建通道
            Channel channel = connection.createChannel();
            //声明（创建）队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //消息内容
            String message = "Hello World!许兆举qq";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
