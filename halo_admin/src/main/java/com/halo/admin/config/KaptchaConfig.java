package com.halo.admin.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 自定义验证码样式
 *
 * @author HALO
 */
@Configuration
public class KaptchaConfig {

    @Bean
    DefaultKaptcha producer() {
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.textproducer.font.color", "blue");
        properties.put("kaptcha.textproducer.char.space", "4");
        properties.put("kaptcha.image.height", "40");
        properties.put("kaptcha.image.width", "120");
        properties.put("kaptcha.textproducer.font.size", "30");

        // 点击 Config 查看源码，就可以看到可以配置的项目
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);

        return defaultKaptcha;
    }

}
