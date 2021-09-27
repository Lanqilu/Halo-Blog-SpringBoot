package com.halo.mail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@SpringBootTest
class HaloMailApplicationTests {

    @Autowired
    JavaMailSender mailSender;


    @Test
    void SimpleMailMessageTest() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("测试邮件主题");
        message.setText("测试邮件内容, 嘤嘤嘤");
        message.setTo("885240677@qq.com");
        message.setFrom("1379978893@qq.com");
        mailSender.send(message);
    }

    @Test
    void ComplexMailMessageTest() throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("测试邮件主题");
        helper.setText("测试邮件内容, <b>嘤嘤嘤</b>", true);
        helper.setTo("885240677@qq.com");
        helper.setFrom("1379978893@qq.com");
        helper.addAttachment("1.text", new File("D:\\Repository\\HaloBlog\\HaloBlogSpring\\halo_mail\\src\\test\\java\\com\\halo\\mail\\1.txt"));
        mailSender.send(mimeMessage);
    }


}
