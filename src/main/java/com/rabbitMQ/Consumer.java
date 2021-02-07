package com.rabbitMQ;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Description: 作用描述
 * @Author: Administrator
 * @CreateDate: 2020/6/28 10:10
 */
public class Consumer{

    private final static String QUEUE_NAME = "first_channel_01";

    public static void main(String[] argv) throws Exception {
        //定影工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("guest");
        factory.setPassword("guest");
        //获取链接
        Connection connection = factory.newConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        //监听队列
        while (true){
            //参数一：通道名称 参数二：是否自动回执 参数三：消费者
        channel.basicConsume(QUEUE_NAME,false, consumer);
        QueueingConsumer.Delivery delivery = consumer.nextDelivery();
        byte[] body = delivery.getBody();
        String msg=new String(body);
        System.out.println(msg);
        //确认消息 已经收到
        channel.basicAck(delivery.getEnvelope().getDeliveryTag(),true);
//        System.out.println("消息已经收到：======"+msg);
        }

    }
}
class myQueueingConsumer extends DefaultConsumer{
    private Channel channel=null;
    public myQueueingConsumer(Channel channel) {
        super(channel);
        this.channel=channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
       try {
           String msg=new String(body);
           System.out.println(msg);
           channel.basicAck(envelope.getDeliveryTag(),true);
       }catch (Exception e){
           channel.basicAck(envelope.getDeliveryTag(),false);
       }
    }
}
