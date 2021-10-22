package com.halo.mail.listener;

import com.halo.mail.util.SendMailUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Halo
 * @create 2021/10/21 下午 11:03
 * @description 邮件发送消费者
 */
@Component
public class MailListener {

    @Autowired
    private SendMailUtils sendMailUtils;

    @RabbitListener(queues = "halo.email")
    public void sendMail(Map<String, String> map) {
        if (map != null) {
            sendMailUtils.sendEmail(
                    map.get("subject"),
                    map.get("receiver"),
                    map.get("text")
            );
        }
    }

}
