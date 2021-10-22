package com.halo.mail.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Halo
 * @create 2021/10/21 下午 10:55
 * @description RabbitMQ 消息接受测试组件
 */
@Component
public class SpringRabbitListener {
    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueueMessage(String msg) {
        System.out.println("spring 消费者接收到消息：【" + msg + "】");
    }
}
