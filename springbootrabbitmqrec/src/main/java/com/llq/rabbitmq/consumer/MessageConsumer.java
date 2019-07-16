package com.llq.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

public class MessageConsumer implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
       try{
           byte[] body = message.getBody();
           System.out.println("收到消息1 : " + new String(body));
           // deliveryTag是消息传送的次数，我这里是为了让消息队列的第一个消息到达的时候抛出异常，处理异常让消息重新回到队列，然后再次抛出异常，处理异常拒绝让消息重回队列
           if (message.getMessageProperties().getDeliveryTag() == 1 || message.getMessageProperties().getDeliveryTag() == 2)
           {
               throw new Exception();
           }
           channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //确认消息成功消费
       } catch (Exception e)
       {
           e.printStackTrace();

           if (message.getMessageProperties().getRedelivered())
           {
               System.out.println("消息已重复处理失败,拒绝再次接收...");
               channel.basicReject(message.getMessageProperties().getDeliveryTag(), true); // 拒绝消息
           }
           else
           {
               System.out.println("消息即将再次返回队列处理...");
               channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true); // requeue为是否重新回到队列
           }
       }
    }
}
