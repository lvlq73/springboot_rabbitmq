package com.llq.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "fanout.B")
public class FanoutReceiveB {
    @RabbitHandler
    public void process(String message) {
        System.out.println("fanout Receiver B  : " + message);
    }
}
