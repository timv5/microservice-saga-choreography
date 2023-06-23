package com.learning.orderservice.rmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.start.name}")
    private String startQueue;

    @Value("${rabbitmq.queue.complete.name}")
    private String completeQueue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.start.key}")
    private String routingStartKey;

    @Value("${rabbitmq.routing.complete.key}")
    private String routingCompleteKey;

    @Bean
    public Queue startQueue(){
        return new Queue(startQueue);
    }

    @Bean
    public Queue completeQueue(){
        return new Queue(completeQueue);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder
                .bind(completeQueue())
                .to(exchange())
                .with(routingStartKey);
    }

    @Bean
    public Binding jsonBinding(){
        return BindingBuilder
                .bind(completeQueue())
                .to(exchange())
                .with(routingCompleteKey);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

}
