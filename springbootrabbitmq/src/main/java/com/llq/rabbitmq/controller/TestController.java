package com.llq.rabbitmq.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/sendTest")
public class TestController {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * hello world 点到点
     * @return
     */
    @RequestMapping("/send")
    public String send(){
        String context = "hello " + new Date();
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("hello", context);
        return "ok";
    }

    /**
     * 一个消息产生者，多个消息的消费者。竞争抢消息
     * @return
     */
    @RequestMapping("/neo")
    public String neo() {
        for (int i=0;i<100;i++){
            // Thread.sleep(10);
            String context = "spirng boot neo queue"+" ****** "+i;
            System.out.println("Sender1 : " + context);
            this.rabbitTemplate.convertAndSend("neo", context);
        }
        return "ok";
    }

    /**
     * 发布订阅模式
     * 生产者将消息不是直接发送到队列，而是发送到X交换机，然后由交换机发送给两个队列，两个消费者各自监听一个队列，来消费消息
     * @return
     */
    @RequestMapping("/fanout")
    public String fanoutSender(){
        String context = "hi, fanout msg ";
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("fanoutExchange","", context);
        return "ok";
    }

    /**
     * 主题模式
     * 发送端不只按固定的routing key发送消息，而是按字符串匹配发送，接收端同样如此
     *符号#匹配一个或多个词，符号*匹配不多不少一个词。
     * @return
     */
    @RequestMapping("/topic")
    public String topic() {
        String context = "hi, i am message all";
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("topicExchange", "topic.1", context);
        return "ok";
    }
    @RequestMapping("/topic1")
    public String topic1() {
        String context = "hi, i am message 1";
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("topicExchange", "topic.message", context);
        return "ok";
    }
    @RequestMapping("/topic2")
    public String topic2() {
        String context = "hi, i am messages 2";
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("topicExchange", "topic.messages", context);
        return "ok";
    }
}
