package com.halo.mail.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

/**
 * @author Halo
 * @create 2021/10/21 下午 11:40
 * @description
 */
@Slf4j
@Component
public class SendMailUtils {

    @Value(value = "${spring.mail.username}")
    public String sender;

    @Resource
    private JavaMailSenderImpl mailSender;

    /**
     * 发送邮件
     *
     * @param subject  主题
     * @param receiver 接收者
     * @param text     文本
     */
    public void sendEmail(String subject, String receiver, String text) {
        try {
            // 创建一个复杂的消息邮件
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            // multipart:true
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setSubject(subject);

            helper.setText(text, true);
            // 邮件接收人
            helper.setTo(receiver);

            // 邮件发送者
            helper.setFrom(sender);

            mailSender.send(mimeMessage);

            log.info("邮件发送成功");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
