package com.llq.rabbitmq.controller;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.llq.rabbitmq.config.RabbitMQConfig1;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/sendTest1")
public class TestController1 {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/send")
    public String send3() throws UnsupportedEncodingException {
        String uuid = UUID.randomUUID().toString();
        CorrelationData correlationId = new CorrelationData(uuid);
        String jsonStr1 = "{'id':'1','name':'哈哈1'}";
        rabbitTemplate.convertAndSend(RabbitMQConfig1.EXCHANGE, RabbitMQConfig1.ROUTINGKEY1, jsonStr1, correlationId);

        String jsonStr2 = "{'id':'2','name':'哈哈2'}";
        String uuid2 = UUID.randomUUID().toString();
        CorrelationData correlationId2 = new CorrelationData(uuid2);
        rabbitTemplate.convertAndSend(RabbitMQConfig1.EXCHANGE, RabbitMQConfig1.ROUTINGKEY2, jsonStr2, correlationId2);
        return uuid;
    }

}
