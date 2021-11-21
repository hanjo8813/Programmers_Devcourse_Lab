package com.prgrms.devcourse.springsecuritymasterclass.configures;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableJdbcHttpSession
@EnableAsync
@Configuration
public class WebMvcConfigure implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/me").setViewName("me");
        registry.addViewController("/admin").setViewName("admin");
        registry.addViewController("/send").setViewName("send");
    }
}