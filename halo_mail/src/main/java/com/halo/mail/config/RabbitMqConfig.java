package com.halo.mail.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Halo
 * @create 2021/10/21 下午 10:58
 * @description RabbitMQ 队列和交换机配置类
 */
@Configuration
public class RabbitMqConfig {

    public static final String HALO_EMAIL = "halo.email";
    public static final String EXCHANGE_DIRECT = "exchange.halo";
    public static final String ROUTING_KEY_EMAIL = "halo.email";

    /**
     * 声明 Email 队列
     */
    @Bean(HALO_EMAIL)
    public Queue haloEmail() {
        return new Queue(HALO_EMAIL);
    }

    /**
     * 声明交换机
     */
    @Bean(EXCHANGE_DIRECT)
    public Exchange exchangeDirect() {
        // 声明路由交换机，durable:在rabbitmq重启后，交换机还在
        return ExchangeBuilder.directExchange(EXCHANGE_DIRECT).durable(true).build();
    }

    /**
     * halo.email 队列绑定交换机，指定 routingKey
     *
     * @param queue    队列
     * @param exchange 交换机
     * @return 绑定
     */
    @Bean
    public Binding BINDING_QUEUE_INFORM_EMAIL(@Qualifier(HALO_EMAIL) Queue queue, @Qualifier(EXCHANGE_DIRECT) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_EMAIL).noargs();
    }


    /**
     * 测试队列
     */
    @Bean
    public Queue simpleQueue() {
        return new Queue("simple.queue");
    }


}
