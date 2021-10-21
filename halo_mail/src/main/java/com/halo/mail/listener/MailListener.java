package com.halo.mail.listener;

import com.halo.mail.util.SendMailUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Halo
 * @create 2021/10/21 下午 11:03
 * @description
 */
@Component
public class MailListener {

//    @Resource
//    private RedisTemplate<String, Object> redisTemplate;
//
//    @Resource
//    private JavaMailSender mailSender;

//    public String getAuthCode(String email) {
//        Object o = redisTemplate.opsForValue().get(email);
//        if (o != null) {
//            return o.toString();
//        } else {
//            return "";
//        }
//    }


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

//    public boolean sentAuthMail(String email) {
//        // 先从 Redis 中查询
//        String authCode = getAuthCode(email);
//        // 如果 Redis 中没有，才发送
//        if (Objects.equals(authCode, "")) {
//            Random random = new Random();
//            authCode = String.valueOf(random.nextInt(99999));
//            redisTemplate.opsForValue().set(email, authCode);
//        }
//        // redis 存验证码
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setSubject("Halo 验证码测试邮件");
//            message.setText("验证码是：" + authCode);
//            message.setTo(email);
//            message.setFrom("1379978893@qq.com");
//            mailSender.send(message);
//            System.out.println("邮件发送成功");
//        } catch (Exception e) {
//            System.out.println(e);
//            return false;
//        }
//        return true;
//    }

}
